package com.fileuploader.upload.repositories;

import com.fileuploader.upload.dataclasses.UserData;

public interface UserRepositoryI {
    public UserData createUser(String username, String password);

    public UserData getUserByEmail(String email);
}
