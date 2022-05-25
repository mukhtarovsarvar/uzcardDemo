package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {


    @Override   // Authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("admin").and()
                .withUser("profile").password("{noop}profile").roles("profile");

    }


    @Override     // Authorization
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/category/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("admin")
                .antMatchers("/profile/**").hasAnyRole("profile")
                .anyRequest().authenticated().and().httpBasic();

        http.csrf().disable().cors().disable();
        // .and.formLogin();
    }

}
