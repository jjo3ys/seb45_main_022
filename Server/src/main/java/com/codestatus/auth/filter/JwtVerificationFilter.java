package com.codestatus.auth.filter;

import com.codestatus.auth.dto.PrincipalDto;
import com.codestatus.auth.jwt.JwtTokenizer;
import com.codestatus.auth.userdetails.UsersDetailService;
import com.codestatus.auth.utils.CustomAuthorityUtils;
import com.codestatus.auth.utils.JwtResponseUtil;
import com.codestatus.domain.user.entity.User;
import com.codestatus.global.exception.AccessTokenExpiredException;
import com.codestatus.global.exception.RefreshTokenExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UsersDetailService userService;
    private final JwtResponseUtil jwtResponseUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            Map<String, Object> claims = verifyAccessJWS(request);
            setAuthenticationToContext(claims);

        } catch (SignatureException e) {
            request.setAttribute("exception", e);
        } catch (ExpiredJwtException e) {
            try {
                String email = verifyRefreshJWS(request);
                User user = userService.loadUserByEmail(email);
                String generatedToken = jwtResponseUtil.getResponseTokenString(user);

                request.setAttribute("exception", new AccessTokenExpiredException());
                request.setAttribute("token", generatedToken);
            } catch (ExpiredJwtException refreshEx) {
                request.setAttribute("exception", new RefreshTokenExpiredException());
            } catch (BadJwtException | JwtException jwtException) {
                request.setAttribute("exception", jwtException);
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyAccessJWS(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ","");
        return jwtTokenizer.getClaimsFromJws(jws).getBody();
    }

    private String verifyRefreshJWS(HttpServletRequest request) {
        String jws = "";
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElseThrow(() -> new BadJwtException("RefreshToken doesn't exists"));
        for(Cookie cookie:cookies) {
            if(cookie.getName().equals("Refresh")){
                jws = cookie.getValue();
                break;
            }
        }
        return jwtTokenizer.getClaimsFromJws(jws).getBody().getSubject();
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {

        String email = (String) claims.get("username");
        Integer id = (Integer) claims.get("id");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List<String>) claims.get("roles"));

        PrincipalDto principal = new PrincipalDto(id,email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
