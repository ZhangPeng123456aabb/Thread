package com.exam.core.holder;

import com.exam.core.status.IStatus;

/**
 * @author 吴国庆
 * @date 2019/3/14-0:47
 * @description: 状态持有者接口类
 */
public interface IStatusHolder {
    IStatus getStatus();
    void setStatus(IStatus status);
}
