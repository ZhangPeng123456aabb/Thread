package com.exam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.exam.entity.UserDO;
import com.exam.enums.ReturnCode;
import com.exam.http.req.UserIdSearchReq;
import com.exam.http.resq.UserSearchResp;
import com.exam.services.UserService;
import com.exam.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhangjinlin@chinamobile.com">张锦林</a> 2019/8/30 19:41
 */
@RestController
public class CheckController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户ID获取用户信息
     */
    @GetMapping(value = "/user/find")
    public UserSearchResp getUser(@Valid UserIdSearchReq req, BindingResult bindingResult) {
        UserSearchResp resp = new UserSearchResp();
        //入参格式验证
        if (ControllerUtils.getErrorMessage(bindingResult, resp)) {
            return resp;
        }
        List<UserDO> userDOS = new ArrayList<>();
        resp.setUserDO(userDOS);

        UserDO userDO = userService.findUserById(req.getId());
        if (userDO != null) {
            userDOS.add(userDO);
        }

        resp.sethRet(ReturnCode.SUCCESS.getCode());
        resp.setRetMsg(ReturnCode.SUCCESS.getMessage());
        return resp;
    }

    @GetMapping("/ok")
    @ResponseBody
    public String ok() {
        return "ok!";
    }

    @GetMapping("/error")
    @ResponseBody
    public String error() {
        return "error!";
    }
}
