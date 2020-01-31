package com.exam.http.resq;

import java.util.List;

import com.exam.entity.UserDO;
import com.exam.http.base.BaseResp;

public class UserSearchResp extends BaseResp {
    private List<UserDO> userDO;

    public List<UserDO> getUserDO() {
        return userDO;
    }

    public void setUserDO(List<UserDO> userDO) {
        this.userDO = userDO;
    }
}
