package com.exam.core.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.exam.core.status.IStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 吴国庆
 * @date 2019/3/13-23:13
 * @description: 状态处理类工厂
 */
@Component
public class StatusHandlerFactory implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(StatusHandlerFactory.class);

    /**
     * 上下文对象实例
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 存储状态处理器
     */
    private static Map<IStatus, com.exam.core.handler.IStatusHandler> statusHandlerMap = new HashMap<>();

    /**
     * 加载所有状态处理器，并存储
     */
    @PostConstruct
    public void loadStatusHandler() {
        /**
         *  实现对所有被StatusHandler注解为状态处理器的类的加载，存储到静态变量statusHandlerMap
         */
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(StatusHandler.class);
        if (beans != null) {
            for (Object bean : beans.values()) {
                StatusHandler statusHandler = bean.getClass().getAnnotation(StatusHandler.class);
                if (bean instanceof IStatusHandler) {
                    statusHandlerMap.put(statusHandler.status(), (IStatusHandler)bean);
                } else {
                    logger.error("class:{%s} has StatusHandler annotation but is not IStatusHandler",
                        bean.getClass().getName());
                }
            }
        }
    }

    /**
     * 根据状态获取响应的状态处理器实例
     *
     * @param status
     * @return
     */
    public static com.exam.core.handler.IStatusHandler instance(IStatus status) {
        return statusHandlerMap.get(status);
    }
}
