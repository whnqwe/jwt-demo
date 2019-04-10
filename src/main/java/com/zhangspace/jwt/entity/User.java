package com.zhangspace.jwt.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;
    private String username;
    private String password;
    private String realname;
    private String avatar;

    private String mobile;
    private String sex;
    private int status;
    private Date createTime;
}
