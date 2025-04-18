package com.fileuploader.upload.controllers;

import com.fileuploader.upload.dataclasses.UserData;
import com.fileuploader.upload.dataclasses.UserDataDTO;
import com.fileuploader.upload.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/createUser")
    public UserData createUser(@RequestBody UserData userData){
        return userService.createUser(userData.email,userData.password);
    };
}
