package org.zerock.tp4.security.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//인가받은 유저만 사용 할 수 있게
            .antMatchers("/sample/doAll").permitAll()
            .antMatchers("/sample/doMember").access("hasRole('ROLE_MEMBER')")
                .antMatchers("/sample/doAdmin").access("hasRole('ROLE_ADMIN')")
        ;

        http.formLogin().loginPage("/customLogin");//로그인 페이지로 보내주는 역할을 한다.

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("member1").password("$2a$10$3ekxex0dPJs4uGsDfrHcSOl33PR3vm3dq/R7dWQDgmafw3RvfzD1q")
                .roles("MEMBER");
        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$3ekxex0dPJs4uGsDfrHcSOl33PR3vm3dq/R7dWQDgmafw3RvfzD1q")
                .roles("MEMBER","ADMIN");
    }
}
