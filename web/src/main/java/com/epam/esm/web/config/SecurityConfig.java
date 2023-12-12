package com.epam.esm.web.config;

import com.epam.esm.model.service.UserService;
import com.epam.esm.web.filter.JwtRequestFilter;
import com.epam.esm.web.utils.UrlParts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

    private final String USER = "USER";

    private final String ADMIN = "ADMIN";

    private UserService userService;

    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    @Lazy
    public SecurityConfig(UserService userService, JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                // sessionManagement() - disable sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //test controllers
                .antMatchers("/greetings/secured").authenticated()
                .antMatchers("/greetings/unsecured").permitAll()
                //admin controller
                .antMatchers(UrlParts.ADMIN+"/**").permitAll()
                // auth controller
                .antMatchers(UrlParts.AUTH+UrlParts.TOKEN).permitAll()
                .antMatchers(UrlParts.AUTH+UrlParts.TOKEN+UrlParts.REFRESH).authenticated()
                .antMatchers(UrlParts.REGISTRATION).permitAll()
                //gift controller
                .antMatchers(UrlParts.GIFTS+UrlParts.READ+"/**").hasAnyRole(USER,ADMIN)
                .antMatchers(UrlParts.GIFTS+"/**").hasRole(ADMIN)
                .antMatchers(UrlParts.GIFTS+UrlParts.CREATE+"/**").hasRole(ADMIN)
//                .antMatchers(UrlParts.GIFTS+UrlParts.UPDATE).hasRole(ADMIN)
                // order controller
                .antMatchers(UrlParts.ORDERS).hasAnyRole(USER,ADMIN)
                // tag controller
                .antMatchers(UrlParts.TAGS).hasRole(ADMIN)
                // user controller
                .antMatchers(UrlParts.USERS+"/**").hasRole(ADMIN)
                .antMatchers("/greetings/info").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                // JWT filter
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //  @Bean
    @Deprecated
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
