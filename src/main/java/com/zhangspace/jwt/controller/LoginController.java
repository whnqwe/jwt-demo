package com.zhangspace.jwt.controller;


import com.zhangspace.jwt.annotation.Anoymous;
import com.zhangspace.jwt.constants.GpmallWebConstant;
import com.zhangspace.jwt.controller.support.ResponseData;
import com.zhangspace.jwt.dto.UserLoginRequest;
import com.zhangspace.jwt.dto.UserLoginResponse;
import com.zhangspace.jwt.service.IUserCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController  extends BaseController{

    @Autowired
    IUserCoreService userCoreService;

    @GetMapping("index")
    public String index(){
        return "index";
    }

    @Anoymous
    @PostMapping("/login")
    public ResponseData doLogin(String username, String password,
                                HttpServletResponse response){
        ResponseData data=new ResponseData();
        UserLoginRequest request=new UserLoginRequest();
        request.setPassword(password);
        request.setUserName(username);
        UserLoginResponse userLoginResponse=userCoreService.login(request);
        response.addHeader("Set-Cookie",
                "access_token="+userLoginResponse.getToken()+";Path=/;HttpOnly");

        data.setMessage(userLoginResponse.getMsg());
        data.setCode(userLoginResponse.getCode());
        data.setData(GpmallWebConstant.GPMALL_ACTIVITY_ACCESS_URL);
        return data;
    }
}
