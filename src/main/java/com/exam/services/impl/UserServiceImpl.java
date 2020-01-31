package com.exam.services.impl;

import com.exam.dao.UserDao;
import com.exam.entity.UserDO;
import com.exam.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDO findUserById(Long id) {
        if (null == id) {
            return null;
        }
        return userDao.findUserById(id);
    }

    @Override
    public UserDO findUserByLoginName(String loginName) {
        if(StringUtils.isBlank(loginName)) {
            return null;
        }
        return userDao.findUserByLoginName(loginName);
    }

    @Override
    public void insertUser(UserDO userDO) {
        if(null == userDO) {
            return;
        }
        userDao.insertUser(userDO);
    }
}
