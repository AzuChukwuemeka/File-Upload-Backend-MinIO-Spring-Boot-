package com.fileuploader.upload.services;

import com.fileuploader.upload.dataclasses.UserData;
import com.fileuploader.upload.dataclasses.UserDataDTO;
import com.fileuploader.upload.repositories.UserRepositoryI;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepositoryI userRepositoryI;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepositoryI userRepositoryI, PasswordEncoder passwordEncoder) {
        this.userRepositoryI = userRepositoryI;
        this.passwordEncoder = passwordEncoder;
    }

    public UserData createUser(String email, String password){
        return userRepositoryI.createUser(email,passwordEncoder.encode(password));
    }
    public UserDataDTO getUserByEmail(String email) {
        UserData userByEmail = userRepositoryI.getUserByEmail(email);
        return new UserDataDTO(userByEmail.getEmail());
    }
}
