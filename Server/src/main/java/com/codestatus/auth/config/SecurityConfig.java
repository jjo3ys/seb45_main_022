package com.codestatus.auth.config;

import com.codestatus.auth.handler.*;
import com.codestatus.auth.jwt.JwtTokenizer;
import com.codestatus.auth.userdetails.UsersDetailService;
import com.codestatus.auth.utils.CustomAuthorityUtils;
import com.codestatus.auth.utils.JwtResponseUtil;
import com.codestatus.domain.user.service.UserService;
import com.codestatus.auth.filter.JwtAuthenticationFilter;
import com.codestatus.auth.filter.JwtVerificationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UsersDetailService usersDetailService;
    private final UserService userService;
    private final JwtResponseUtil jwtResponseUtil;
    private String s3 = "http://statandus.s3-website.ap-northeast-2.amazonaws.com/"; // front 배포 완료되면 s3 주소 여기에 넣으면 됨

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        // logout 성공 핸들러
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable() // csrf 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
                .and()
                .formLogin().disable() // form login 비활성화
                .httpBasic().disable() // http basic 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint()) // 인증 실패 핸들러
                .accessDeniedHandler(new UserAccessDeniedHandler()) // 인가 실패 핸들러
                .and()
                .apply(new CustomFilterConfigurer()) // 커스텀 필터 적용
                .and()
                .logout() // logout 설정
                .logoutUrl("/logout") // logout url
                .deleteCookies("Refresh") // 쿠키 삭제 : Refresh
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.getWriter().append("Logout successfully");
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .and()
                    .authorizeHttpRequests(authorize -> authorize // 요청에 대한 권한 설정
                        // user 권한 부여
                        .antMatchers(HttpMethod.POST,"/user/signup").permitAll() // 회원가입은 누구나 가능
                        .antMatchers(HttpMethod.POST, "/user/mypage/**").hasRole("USER") // mypage 는 USER 권한 필요
                        .antMatchers(HttpMethod.GET,"/user/mypage/**").hasRole("USER") // mypage 는 USER 권한 필요
                        .antMatchers(HttpMethod.PATCH,"/user/mypage/edit/**").hasRole("USER") // mypage 는 USER 권한 필요
                        .antMatchers(HttpMethod.DELETE ,"/user/mypage/**").hasRole("USER") // mypage 는 USER 권한 필요
                        .antMatchers(HttpMethod.GET, "/user/**").hasRole("USER") // 다른 유저 조회는 USER 권한 필요

                        // feed 권한 부여
                        .antMatchers(HttpMethod.POST, "/feed/**").hasRole("USER") // feed 작성은 USER 권한 필요
                        .antMatchers(HttpMethod.GET, "/feed/{feedId}").hasRole("USER") // feed 상세 조회는 USER 권한 필요
                        .antMatchers(HttpMethod.GET, "/feed/get/{categoryId}").permitAll() // feed 목록 조회는 누구나 가능
                        .antMatchers(HttpMethod.GET, "/feed/weeklybest").permitAll() // weeklybest 목록 조회는 누구나 가능
                        .antMatchers(HttpMethod.GET, "/feed/findByBody/{categoryId}").permitAll() // 쿼리로 검색한 feed 목록 조회는 누구나 가능
                        .antMatchers(HttpMethod.GET, "feed/findByUser/{categoryId}").permitAll() // user 로 검색한 feed 목록 조회는 누구나 가능
                        .antMatchers(HttpMethod.PATCH, "/feed/{feedId}").hasRole("USER") // feed 수정은 USER 권한 필요
                        .antMatchers(HttpMethod.DELETE, "/feed/{feedId}").hasRole("USER") // feed 삭제는 USER 권한 필요
                        .antMatchers(HttpMethod.GET, "/feed/my-post").hasRole("USER") // my-post 조회는 USER 권한 필요

                        // comment 권한 부여
                        .antMatchers(HttpMethod.POST, "/comment/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH, "/comment/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE, "/comment/**").hasRole("USER")
                        // like 권한 부여
                        .antMatchers(HttpMethod.POST, "/feed/like/**").hasRole("USER")

                        // 출석체크 권한 부여
                        .antMatchers(HttpMethod.POST, "/attendance/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "/attendance/**").hasRole("USER")

                        .antMatchers(HttpMethod.POST,"/logout").hasRole("USER") // logout 은 USER 권한 필요
                        .anyRequest().permitAll() // 나머지 요청은 누구나 가능
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2UserSuccessHandler(jwtTokenizer, usersDetailService, userService)));
                return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // cors 설정
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:3000", "http://localhost:8080", "https://seb45-main-022-gz09p0en3-donghun-k.vercel.app/", s3));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Access-Control-Allow-Credentials");
        configuration.addExposedHeader("Access-Control-Allow-Origin");
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PATCH","DELETE","OPTIONS"));
        configuration.addExposedHeader("Authorization");

        configuration.setAllowCredentials(true);
        // setAllowCredentials 를 true 로 설정하면 Access-Control-Allow-Origin 의 값은 ' * ' 를 허용하지 않음
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // cors 설정 적용
        source.registerCorsConfiguration("/**", configuration); // 모든 요청에 대해 위의 설정 적용
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder)  {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, new ObjectMapper(), jwtResponseUtil);

            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, usersDetailService, jwtResponseUtil);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}
