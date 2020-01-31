package com.exam.dao;

import com.exam.entity.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    UserDO findUserById(@Param("id") Long id);
    UserDO findUserByLoginName(@Param("loginName") String loginName);
    Integer insertUser(UserDO userDO);
}
