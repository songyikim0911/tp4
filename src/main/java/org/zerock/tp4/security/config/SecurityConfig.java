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
import org.zerock.tp4.security.handler.CustomLoginSuccessHandler;
import org.zerock.tp4.security.service.CustomUserDetailsService;

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

        http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login");//로그인 페이지로 보내주는 역할을 한다.
//loginPage- 로그인페이지 연결 url. , loginProcessingurl - 로그인을 처리할 페이지
        http.csrf().disable();

    }


    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService());

//        auth.inMemoryAuthentication().withUser("member1").password("$2a$10$3ekxex0dPJs4uGsDfrHcSOl33PR3vm3dq/R7dWQDgmafw3RvfzD1q")
//                .roles("MEMBER");
//        auth.inMemoryAuthentication().withUser("admin1").password("$2a$10$3ekxex0dPJs4uGsDfrHcSOl33PR3vm3dq/R7dWQDgmafw3RvfzD1q")
//                .roles("MEMBER","ADMIN");
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }
}
