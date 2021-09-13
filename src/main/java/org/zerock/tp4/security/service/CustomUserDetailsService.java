package org.zerock.tp4.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //예외로 id없을때 떤져줌

        log.warn("CustomUserDetailService-----------");
        log.warn("CustomUserDetailService-----------");
        log.warn(username);
        log.warn("CustomUserDetailService-----------");
        log.warn("CustomUserDetailService-----------");

        User result = (User) User.builder()
                .username(username)
                .password("$2a$10$3ekxex0dPJs4uGsDfrHcSOl33PR3vm3dq/R7dWQDgmafw3RvfzD1q")
                .accountExpired(false)
                .accountLocked(false)
                .authorities("ROLE_MEMBER","ROLE_ADMIN")
                .build();

        return result;
    }
}
