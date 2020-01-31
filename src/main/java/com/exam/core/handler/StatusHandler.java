package com.exam.core.handler;

import com.exam.core.status.UserStatus;

import java.lang.annotation.*;

/**
 * @author 吴国庆
 * @date 2019/3/14-8:47
 * @description: 状态处理器注解
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StatusHandler {
    UserStatus status();
}