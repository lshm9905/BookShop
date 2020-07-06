package com.book_web.service.impl;


import com.book_web.dao.UserDao;
import com.book_web.pojo.User;
import com.book_web.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao.UserDao userDao;

    @Override
    public User userLogin(User user) {
        user = userDao.userLogin(user.getUsername(), user.getPassword());
        return user;
    }
}

