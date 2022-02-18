package com.config;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder encoder) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/welcome", "/registration", "/login").not().fullyAuthenticated()
                .and()
                .authorizeRequests().antMatchers("/user/**").hasRole("user")
                .and()
                .authorizeRequests().antMatchers("/admin/**").hasRole("admin")
                .and()
                .authorizeRequests().and().exceptionHandling().accessDeniedPage("/error")
                .and()
                .authorizeRequests().and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/successfulLogin")
                .failureUrl ("/login?error=true")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

}
