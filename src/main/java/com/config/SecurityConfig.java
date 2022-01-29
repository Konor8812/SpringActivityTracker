package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/registration", "/welcome").not().fullyAuthenticated()
                .and()
                .authorizeRequests().antMatchers("/mainUser/**").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/mainAdmin/**").hasRole("ADMIN")
                .and()
                .authorizeRequests().and().exceptionHandling().accessDeniedPage("/error")
                .and()
                .authorizeRequests().and().formLogin()// Submit URL of login page.
                .loginPage("/welcome")
                .defaultSuccessUrl("/login")
                .failureUrl ("/login")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
