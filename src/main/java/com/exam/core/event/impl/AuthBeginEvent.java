package com.exam.core.event.impl;

import com.exam.core.event.IStatusEvent;

/**
 * @author 吴国庆
 * @date 2019/3/14-2:05
 * @description: 实名认证开始事件
 */
public class AuthBeginEvent implements IStatusEvent {

    private final String statusHolderId;

    public AuthBeginEvent(String statusHolderId) {
        this.statusHolderId = statusHolderId;
    }

    @Override
    public String getStateHolderId() {
        return this.statusHolderId;
    }
}
