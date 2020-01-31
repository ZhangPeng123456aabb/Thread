package com.exam.core.handler.impl;


import com.exam.core.event.IStatusEvent;
import com.exam.core.event.impl.AuthBeginEvent;
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
@StatusHandler(status = UserStatus.PENDING)
public class PendingStatusHandlerImpl implements IStatusHandler<UserStatusHolder> {
    @Override
    public UserStatusHolder handle(UserStatusHolder stateHolder, IStatusEvent event) throws Exception {
        if (event instanceof AuthBeginEvent) {
            //认证中
            stateHolder.setStatus(UserStatus.AUTHING);
        } else {
            throw new Exception("状态事件不可处理！");
        }

        return stateHolder;
    }
}
