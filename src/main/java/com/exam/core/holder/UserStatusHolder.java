package com.exam.core.holder;

import com.exam.core.status.IStatus;
import com.exam.core.status.UserStatus;

/**
 * @author 吴国庆
 * @date 2019/4/14-15:11
 * @description: 用户状态持有实体
 */
public class UserStatusHolder implements IStatusHolder {

    private String userId;

    private UserStatus status;

    public UserStatusHolder (UserStatus status) {
        this.status = status;
    }

    @Override
    public IStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(IStatus status) {
        System.out.println("用户变更前状态：" + this.status);
        this.status = (UserStatus) status;
        System.out.println("用户变更后状态：" + this.status);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
