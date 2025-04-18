package com.fileuploader.upload.repositories;

import com.fileuploader.upload.dataclasses.UserData;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryI{

    String USER_CREATION_SQL = "INSERT INTO tbl_user (email,password) VALUES (?,?)";
    String GET_USER_SQL = "SELECT * FROM tbl_user where email = ?";
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserData createUser(String email, String password) {
        int update = jdbcTemplate.update(USER_CREATION_SQL, new Object[]{email, password});
        //do what you want with int value lol
        if(update != 1){
            throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(USER_CREATION_SQL,1,update);
        }
        return new UserData(email,passwordEncoder.encode(password));
    }
    @Override
    public UserData getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_SQL,(rs,row) -> {
            return new UserData(rs.getString("email"),rs.getString("password"));
        },email);
    }
}
