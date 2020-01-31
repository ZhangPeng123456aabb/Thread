package com.exam.aspect;

import java.util.UUID;

import com.exam.core.ApplicationBeans;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author dinkfamily
 * @date 2019/5/6 10:44
 * @description: 获取httprequest 范围内的唯一日志标志和用户
 */
@Component
public class LogFlag {
    /**
     * 获取接口级的唯一标识符
     *
     * @return
     */
    public static String getRequestedUniqueFlag() {
        HttpRequestScopeUniqueFlagBean httpRequest = ApplicationBeans.byClass(HttpRequestScopeUniqueFlagBean.class);
        if (httpRequest == null) {
            return "";
        }
        String logUniqueFlag = httpRequest.getLogUniqueFlag();
        return logUniqueFlag == null ? "" : logUniqueFlag;
    }

    @Component
    @Scope(value = WebApplicationContext.SCOPE_REQUEST)
    private class HttpRequestScopeUniqueFlagBean {
        private String logUniqueFlag;

        public HttpRequestScopeUniqueFlagBean() {
            logUniqueFlag = UUID.randomUUID().toString().replace("-", "");
        }

        public String getLogUniqueFlag() {
            return logUniqueFlag;
        }

    }
}
