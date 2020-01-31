package com.exam.core.event.impl;

import com.exam.core.event.IStatusEvent;

/**
 * @author 吴国庆
 * @date 2019/3/14-2:05
 * @description: 实名认证失败事件
 */
public class AuthFailedEvent implements IStatusEvent {

    private final String statusHolderId;

    public AuthFailedEvent(String statusHolderId) {
        this.statusHolderId = statusHolderId;
    }

    @Override
    public String getStateHolderId() {
        return this.statusHolderId;
    }
}
