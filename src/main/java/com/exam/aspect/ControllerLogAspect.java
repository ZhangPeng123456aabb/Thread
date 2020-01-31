package com.exam.aspect;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.exam.enums.ReturnCode;
import com.exam.http.base.BaseResp;
import com.exam.util.IPWorker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ：dinkfamily
 * @version :  1.0.0$
 * @date ：Created in 2019/4/6 11:25
 * @description ：日志拦截器
 * @modified By  ：
 */
@Aspect
@Component
public class ControllerLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    private final String HTTP_POINT_CUT = "@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
        "|| @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web"
        + ".bind.annotation.GetMapping)"
        +
        "|| @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web"
        + ".bind.annotation.DeleteMapping)"
        +
        "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)";

    @Pointcut(HTTP_POINT_CUT)
    public void controllerLog() {
        logger.info("为了安全扫描而加的打印");
    }

    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws IOException {

        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = proceedingJoinPoint.getArgs();
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;

        Map<String, Object> argMaps = new LinkedHashMap<>();
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse
                || args[i] instanceof BindingResult) {
                continue;
            }
            argMaps.put(parameters[i].getName(), args[i]);
        }

        String inArgs = JSON.toJSONString(argMaps);
        logger.info("{}-url:{},method:{},ip:{},请求参数：{}", LogFlag.getRequestedUniqueFlag(), request.getRequestURI(),
            proceedingJoinPoint.getSignature().toShortString(), IPWorker.address(request), inArgs);
        Object result = null;
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            result = proceedingJoinPoint.proceed();
            stopWatch.stop();
            logger.info("{}- 花费：{}毫秒，url:{},method:{},ip:{},请求参数：{},结果：{}", LogFlag.getRequestedUniqueFlag(),
                stopWatch.getTotalTimeMillis(), request.getRequestURI(),
                proceedingJoinPoint.getSignature().toShortString(), IPWorker.address(request), inArgs,
                JSON.toJSONString(result));
            //一定要有return,否则controller不返回数据
            return result;
        } catch (Throwable e) {
            logger.error("{}-url:{},method:{},ip:{},请求参数：{}", LogFlag.getRequestedUniqueFlag(), request.getRequestURI(),
                proceedingJoinPoint.getSignature().toShortString(), IPWorker.address(request), inArgs, e);
            BaseResp respModel = new BaseResp();
            respModel.sethRet(ReturnCode.OTHER_ERROR.getCode());
            respModel.setRetMsg(ReturnCode.OTHER_ERROR.getMessage());
            HttpServletResponse res = attributes.getResponse();
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(JSONObject.toJSONString(respModel));
            return null;
        }
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null) {
            String[] addresses = ip.split(",");
            return addresses[0];
        } else {
            return null;
        }
    }

}
