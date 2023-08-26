package com.codestatus.auth.config;

import com.codestatus.auth.filter.JwtAuthenticationFilter;
import com.codestatus.auth.filter.JwtVerificationFilter;
import com.codestatus.auth.handler.*;
import com.codestatus.auth.jwt.JwtTokenizer;
import com.codestatus.auth.utils.CustomAuthorityUtils;
import com.codestatus.user.repository.UserRepository;
import com.codestatus.user.service.UserService;
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

@Configuration
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;
    private final UserRepository userRepository;
    private String s3 = ""; // front 배포 완료되면 s3 주소 여기에 넣으면 됨

    public SecurityConfig(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils, UserService userService, UserRepository userRepository) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.userService = userService;
        this.userRepository = userRepository;
    }

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
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint()) // 인증 실패 핸들러
                .accessDeniedHandler(new MemberAccessDeniedHandler()) // 인가 실패 핸들러
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
                        .antMatchers(HttpMethod.POST,"/users/signup").permitAll() // 회원가입은 누구나 가능
                        .antMatchers(HttpMethod.PATCH,"/users/**").hasRole("USER") // 회원정보 수정은 USER 권한 필요

                        .antMatchers(HttpMethod.POST,"/logout").hasRole("USER") // logout 은 USER 권한 필요
                        .anyRequest().permitAll() // 나머지 요청은 누구나 가능
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, userService)));
                return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // cors 설정
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:3000", "http://localhost:8080", s3));
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

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, userRepository);

            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}
