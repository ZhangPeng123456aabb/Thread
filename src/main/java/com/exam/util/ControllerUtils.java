package com.exam.util;

import com.exam.enums.ReturnCode;
import com.exam.http.base.BaseResp;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author dinkfamily
 * @date 2019/4/14 16:04
 * @description:
 */
public class ControllerUtils {
    public static boolean getErrorMessage(BindingResult bindingResult, BaseResp respModel)
    {
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                stringBuilder.append(error.getField() + "-" + error.getDefaultMessage() + "\r\n");
            }
            respModel.sethRet(ReturnCode.PARAM_ERROR.getCode());
            respModel.setRetMsg(ReturnCode.PARAM_ERROR.getMessage() + "\r\n" + stringBuilder.toString());
            return true;
        }
        return false;
    }
}
