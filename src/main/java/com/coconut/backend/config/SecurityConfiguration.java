package com.coconut.backend.config;

import com.coconut.backend.entity.RestBean;
import com.coconut.backend.entity.dto.Account;
import com.coconut.backend.entity.vo.response.AuthorizeVO;
import com.coconut.backend.filter.JwtAuthorizeFilter;
import com.coconut.backend.service.AccountService;
import com.coconut.backend.utlis.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Resource
    JwtUtils jwtUtils;
    @Resource
    JwtAuthorizeFilter jwtAuthorizeFilter;
    @Resource
    AccountService accountService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(conf ->
                                conf
                                        .requestMatchers("/api/upload/**").authenticated()
                                        .requestMatchers("/api/user/**").authenticated()
                                        .requestMatchers("/api/note/load").authenticated()
                                        .anyRequest().permitAll()
//                            .requestMatchers("/api/auth/**").permitAll()
//                            .anyRequest().authenticated();
                )
                .formLogin(conf -> conf
                        .loginProcessingUrl("/api/auth/login")
                        .failureHandler(this::onAuthenticationFailure)
                        .successHandler(this::onAuthenticationSuccess))
                .logout(conf -> conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess))
                .exceptionHandling(conf -> conf
                        .authenticationEntryPoint(this::onUnauthorized)
                        .accessDeniedHandler(this::onAccessDeny)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * 权限不足
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param exception AccessDeniedException
     * @throws IOException IOException
     */
    public void onAccessDeny(HttpServletRequest request,
                             HttpServletResponse response,
                             AccessDeniedException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.forbidden(exception.getMessage()).asJsonString());
    }

    /**
     * 未登录
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param exception AuthenticationException
     * @throws IOException IOException
     */
    public void onUnauthorized(HttpServletRequest request,
                               HttpServletResponse response,
                               AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }

    /**
     * 登录校验成功
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication Authentication
     * @throws IOException IOException
     */
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();
        Account account = accountService.findAccountByNameOrEmail(user.getUsername());
        String token = jwtUtils.createJwt(user, account.getId(), account.getUsername());
        AuthorizeVO vo = new AuthorizeVO(account.getUsername(), account.getRole(), token, jwtUtils.expireTime());
        response.getWriter().write(RestBean.success(vo).asJsonString());
    }

    /**
     * 登录校验失败
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param exception AuthenticationException
     * @throws IOException IOException
     */
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(RestBean.unauthorized(exception.getMessage()).asJsonString());
    }

    /**
     * 退出登录
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication Authentication
     */
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String authorization = request.getHeader("Authorization");
        if (jwtUtils.invalidateJwt(authorization)) {
            writer.write(RestBean.success().asJsonString());
        } else {
            writer.write(RestBean.failure(400, "退出登录失败").asJsonString());
        }
    }

}
