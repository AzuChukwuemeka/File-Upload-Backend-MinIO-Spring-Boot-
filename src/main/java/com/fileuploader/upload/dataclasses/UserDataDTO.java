package com.fileuploader.upload.dataclasses;

public class UserDataDTO {
    @Override
    public String toString() {
        return "UserDataDTO{" +
                "email='" + email + '\'' +
                '}';
    }

    public String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDataDTO(String email) {
        this.email = email;
    }
}
