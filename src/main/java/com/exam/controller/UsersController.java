package com.exam.controller;

import com.exam.entity.UserDO;
import com.exam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dinkfamily
 * @date 2019/4/14 17:02
 * @description:
 */

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    // TODO 实现用户注册接口


    @RequestMapping
    public void insert(UserDO userDO){
        UserDO userByLoginName = userService.findUserByLoginName(userDO.getLoginName());
        if(userService.findUserByLoginName(userDO.getLoginName()).equals(userByLoginName.getLoginName())&&userService.findUserByLoginName(userDO.getLoginName()).equals(userByLoginName.getLoginName())){
            System.out.println("此用户名已注册");

        }else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String format = simpleDateFormat.format(date);
            userDO.setGmtCreate(new Date(format));
            userService.insertUser(userDO);

        }



    }

    public void login(HttpSession session,String loginname){

        UserDO userDO=userService.findUserByLoginName(loginname);
        if(userDO!=null){
            session.setAttribute("userDO",userDO);
            System.out.println("登陆成功");
            return;
        }else {
            System.out.println("登陆失败");
        }

    }














    // TODO 实现用户登录接口

}
