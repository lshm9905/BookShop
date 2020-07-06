package com.book_web.dao;

import com.book_web.pojo.User;

public class UserDao {
    @Mapper
    public interface UserDao {
        User userLogin(String username, String password);
    }
}
