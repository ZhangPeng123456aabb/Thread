package com.exam.core.handler.impl;

import com.exam.core.event.IStatusEvent;
import com.exam.core.event.impl.AuthExpireEvent;
import com.exam.core.event.impl.AuthFailedEvent;
import com.exam.core.event.impl.AuthSuccessEvent;
import com.exam.core.handler.IStatusHandler;
import com.exam.core.handler.StatusHandler;
import com.exam.core.holder.UserStatusHolder;
import com.exam.core.status.UserStatus;
import org.springframework.stereotype.Component;

/**
 * @author 吴国庆
 * @date 2019/3/14-0:54
 * @description: 认证中状态处理器
 */
@Component
@StatusHandler(status = UserStatus.AUTHING)
public class AuthingStatusHandlerImpl implements IStatusHandler<UserStatusHolder> {
    @Override
    public UserStatusHolder handle(UserStatusHolder stateHolder, IStatusEvent event) throws Exception {
        if (event instanceof AuthExpireEvent) {
            //认证超时
            stateHolder.setStatus(UserStatus.AUTH_EXPIRED);
        } else if (event instanceof AuthFailedEvent) {
            //认证失败
            stateHolder.setStatus(UserStatus.AUTH_FAILED);
        } else if (event instanceof AuthSuccessEvent) {
            //认证成功
            stateHolder.setStatus(UserStatus.AUTH_SUCCESS);
        } else {
            throw new Exception("");
        }

        return stateHolder;
    }
}
