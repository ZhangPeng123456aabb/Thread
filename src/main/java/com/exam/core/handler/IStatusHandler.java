package com.exam.core.handler;

import com.exam.core.event.IStatusEvent;
import com.exam.core.holder.IStatusHolder;

/**
 * @author 吴国庆
 * @date 2019/3/13-23:00
 * @description: 状态处理器接口类
 * @param <S> 状态持有者实例
 */
public interface IStatusHandler<S extends IStatusHolder> {
    S handle(S stateHolder, IStatusEvent event) throws Exception;
}
