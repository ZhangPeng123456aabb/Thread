package com.exam.enums;

/**
 * @author dinkfamily
 * @date 2019/4/10 17:29
 * @description:
 */
public enum ReturnCode {
    SUCCESS("1","成功"),
    FAIL("0","失败"),
    PARAM_ERROR("9000001","参数错误"),
    SIGN_ERROR("9000002","签名错误"),
    SERVICE_INTERFACE_ERROR("9100001","依赖服务接口错误"),
    SYSTEM_MAINTENANCE("9100002","系统维护"),
    SYSTEM_LIMIT("9100003","系统限流，请稍后再试"),
    SERVICE_INTERFACE_TIMEOUT("9100004","依赖服务接口超时"),
    USERID_NOTEXIST_ERROR("9200021","没有找到用户信息"),
    TOKEN_ERROR_OR_EXPIRE("9401002","Token校验失败或无效"),
    OTHER_ERROR("9999999","其他错误");

    private String code;
    private String message;

    ReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return this.code;
    }


    public String getMessage() {
        return this.message;
    }
    /**
     * 信息描述
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.getMessage() + "〔信息代码：" + this.getCode() + "〕";
    }
}
