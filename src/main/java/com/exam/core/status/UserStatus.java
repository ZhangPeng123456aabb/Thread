package com.exam.core.status;

/**
 * @author 吴国庆
 * @date 2019/3/14-0:47
 * @description: 状态持有者接口类
 */
public enum UserStatus implements IStatus {

    /** 初始状态 */
    PENDING,
    /** 认证中 */
    AUTHING,
    /** 认证成功 */
    AUTH_SUCCESS,
    /** 认证失败 */
    AUTH_FAILED,
    /** 认证超时 */
    AUTH_EXPIRED,
    ;

    UserStatus() {
    }

    @Override
    public String getName() {
        return this.name();
    }
}
