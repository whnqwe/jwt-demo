package com.zhangspace.jwt.dto;



import com.zhangspace.jwt.abs.AbstractRequest;

import java.io.Serializable;


public class UserQueryRequest extends AbstractRequest implements Serializable{
    private static final long serialVersionUID = 7306023298178530119L;

    private Integer uid;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


}
