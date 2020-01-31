package com.exam.services;

import com.exam.entity.UserDO;

public interface UserService {
    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    UserDO findUserById(Long id);

    /**
     * 根据登录名查询用户
     *
     * @param loginName
     * @return
     */
    UserDO findUserByLoginName(String loginName);

    /**
     * 新增用户
     * @param userDO
     */
    void insertUser(UserDO userDO);
}
