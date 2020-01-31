package com.exam.http.base;

/**
 * @author dinkfamily
 * @date 2019/4/10 17:26
 * @description:
 */
public class BaseResp {
    /**
     *返回码
     */
    private String hRet;
    /**
     *返回码描述
     */
    private String retMsg;

    public String gethRet() {
        return hRet;
    }

    public void sethRet(String hRet) {
        this.hRet = hRet;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
