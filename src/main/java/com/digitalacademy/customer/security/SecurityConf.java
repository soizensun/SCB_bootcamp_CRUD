package com.digitalacademy.customer.security;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.validation.Valid;




@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

//    @Value("$(spring.security.user.name)")
//    private String username;
//    @Value("$(spring.security.user.password)")
//    private String password;
//    @Value("$(spring.security.user.roles)")
//    private String roles;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().anyRequest().authenticated().and().csrf().disable().httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("zen")
            .password("password").roles("ADMIN", "USER").build()
        );
        manager.createUser(users.username("noon")
                .password("password").roles("USER").build()
        );
        return manager;
    }
}
