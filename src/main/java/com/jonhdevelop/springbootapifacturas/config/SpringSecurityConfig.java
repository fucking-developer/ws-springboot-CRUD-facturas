package com.jonhdevelop.springbootapifacturas.config;

import com.jonhdevelop.springbootapifacturas.auth.handler.LoginSuccessHandler;
import com.jonhdevelop.springbootapifacturas.auth.service.JWTService;
import com.jonhdevelop.springbootapifacturas.service.JpaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JpaUserDetailService jpaUserDetailService;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private JWTService jwtService;


    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(jpaUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale").permitAll()
                .anyRequest().authenticated()
                .and()
                /*.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());*/
                .formLogin().loginPage("/login").successHandler(loginSuccessHandler).permitAll()
                .and()
                .logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/error_403");
        return http.build();
    }

}
