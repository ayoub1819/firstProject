package com.example.projectge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        //auth.inMemoryAuthentication().withUser("admin").password(passwEnc.encode("1234")).roles("USER","ADMIN");

    }

    protected void configure(HttpSecurity http) throws Exception{
        http.exceptionHandling().accessDeniedPage("/accessDenied");
        http.authorizeRequests().antMatchers("/**console/**").permitAll();
        http.authorizeRequests().antMatchers("/login","/signup","/resources/**","/**assets/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();//all other req require an auth
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");
        http.logout().logoutSuccessUrl("/login");
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }
}
