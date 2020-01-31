package com.exam.http.req;

import javax.validation.constraints.NotNull;

import com.exam.http.base.BaseReq;

public class UserIdSearchReq extends BaseReq {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
