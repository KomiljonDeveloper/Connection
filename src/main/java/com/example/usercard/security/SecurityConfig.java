package com.example.usercard.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public void authenticationManagerBuilder(AuthenticationManagerBuilder builder) throws Exception {
       builder.jdbcAuthentication()
               //.usersByUsernameQuery("select * from auth as users where username = ?")
               .dataSource(dataSource)
               .passwordEncoder(passwordEncoder);
    }

    /*@Autowired  todo : InMemoryAuthentication
    public void authenticationManagerBuilder(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("Komiljon")
                .password(passwordEncoder.encode("root"))
                .roles("Admin")
                .and()
                .withUser("Muhammadyusuf")
                .password(passwordEncoder.encode("yusuf"))
                .roles("Admin")
                .and().passwordEncoder(passwordEncoder);
    }*/


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().build();
    }

}
