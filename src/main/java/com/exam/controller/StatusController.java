package com.exam.controller;

import com.exam.core.event.impl.AuthBeginEvent;
import com.exam.core.handler.StatusHandlerFactory;
import com.exam.core.holder.UserStatusHolder;
import com.exam.core.status.UserStatus;
import com.exam.enums.ReturnCode;
import com.exam.http.base.BaseReq;
import com.exam.http.base.BaseResp;
import com.exam.util.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dinkfamily
 * @date 2019/4/14 18:43
 * @description:
 */
@RestController()
@RequestMapping(value = "status")
public class StatusController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "auth")
    public BaseResp startAuth(@Valid @RequestBody BaseReq req, BindingResult bindingResult) {
        BaseResp resp = new BaseResp();
        //入参格式验证
        if (ControllerUtils.getErrorMessage(bindingResult, resp)) {
            return resp;
        }
        UserStatusHolder user = new UserStatusHolder(UserStatus.PENDING);
        AuthBeginEvent authBeginEvent = new AuthBeginEvent(user.getUserId());
        try {
            StatusHandlerFactory.instance(user.getStatus()).handle(user, authBeginEvent);
        } catch (Exception e) {
            log.error(req.getLogUniqueFlag()+"状态机报错", e);
            resp.sethRet(ReturnCode.FAIL.getCode());
            resp.setRetMsg(ReturnCode.FAIL.getMessage());
            return resp;
        }
        resp.sethRet(ReturnCode.SUCCESS.getCode());
        resp.setRetMsg(ReturnCode.SUCCESS.getMessage());
        return resp;
    }
}
