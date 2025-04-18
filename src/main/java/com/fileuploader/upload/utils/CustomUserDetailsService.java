package com.fileuploader.upload.utils;

import com.fileuploader.upload.dataclasses.UserData;
import com.fileuploader.upload.repositories.UserRepositoryI;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepositoryI userRepository;
    public CustomUserDetailsService(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userByEmail = userRepository.getUserByEmail(username);
        return new User(
                userByEmail.getEmail(),
                userByEmail.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
