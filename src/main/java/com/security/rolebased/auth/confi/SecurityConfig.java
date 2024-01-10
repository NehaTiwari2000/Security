package com.security.rolebased.auth.confi;

import com.security.rolebased.auth.entity.RoleRouting;
import com.security.rolebased.auth.repo.IRoleRoutingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    private IRoleRoutingRepo roleRoutingRepo;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("data fetched from table for role :: {}", this.roleRoutingRepo.findAll());


        /*
        *    httpSecurity
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/","/user/save-user","/user/get-all-user").permitAll()
                                .requestMatchers("/user/get-logged-in-user-detail").hasRole("ADMIN")
        * */
        /*
         * Both ways of declaring authorizeRequests are valid.
         * The one that accepts a customizer is a way to make your code easier to read because it avoids multiple indentation levels.
         * Using the lambda customizer is recommended.
         * The difference between authorizeRequests and authorizeHttpRequests is explained here. The authorizeHttpRequests uses the new simplified AuthorizationManager API and the AuthorizationFilter, while authorizeRequests uses the AccessDecisionManager and FilterSecurityInterceptor.
         *  The latter will be deprecated in future version of Spring Security.
         * */
//        httpSecurity
//                .authorizeRequests(auth ->
//                        auth
//                                .requestMatchers("/", "/user/save-user", "/user/get-all-user").permitAll());
//                                .requestMatchers("/user/get-logged-in-user-detail").hasRole("ADMIN")
//                                .anyRequest().authenticated());

        List<RoleRouting> roleRoutingList = this.roleRoutingRepo.findAll();
        for (RoleRouting roleRouting : roleRoutingList) {
            log.info("role : {} and routing :{}", roleRouting.getRole(), roleRouting.getRouting());
            httpSecurity.authorizeHttpRequests(auth ->
                    auth.requestMatchers(roleRouting.getRouting()).hasRole(roleRouting.getRole()));
//                            .requestMatchers("/", "/user/save-user", "/user/get-all-user").permitAll());
        }
//        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity
                .authenticationProvider(authenticationProvider())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }


}
