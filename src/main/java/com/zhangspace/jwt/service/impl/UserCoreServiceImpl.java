package com.zhangspace.jwt.service.impl;

import com.zhangspace.jwt.constants.Constants;
import com.zhangspace.jwt.constants.ResponseCodeEnum;
import com.zhangspace.jwt.dao.UserDao;
import com.zhangspace.jwt.dto.*;
import com.zhangspace.jwt.entity.User;
import com.zhangspace.jwt.exception.ExceptionUtil;
import com.zhangspace.jwt.exception.ServiceException;
import com.zhangspace.jwt.exception.ValidateException;
import com.zhangspace.jwt.service.IUserCoreService;
import com.zhangspace.jwt.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("userCoreService")
public class UserCoreServiceImpl implements IUserCoreService{
    @Autowired
    UserDao userMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        UserLoginResponse response=new UserLoginResponse();
        try {
            beforeValidate(request);
            User user=userMapper.getUserByUserName(request.getUserName());
            if(user==null||!user.getPassword().equals(request.getPassword())){
                response.setCode(ResponseCodeEnum.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(ResponseCodeEnum.USERORPASSWORD_ERRROR.getMsg());
                return response;
            }
            Map<String,Object> map=new HashMap<>();
            map.put("uid",user.getId());
            map.put("exp", DateTime.now().plusDays(1).toDate().getTime()/1000);

            response.setToken(JwtTokenUtils.generatorToken(map));

            response.setUid(user.getId());
            response.setAvatar(user.getAvatar());
            response.setCode(ResponseCodeEnum.SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        }catch (Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
        }

        return response;
    }

    @Override
    public CheckAuthResponse validToken(CheckAuthRequest request) {
        CheckAuthResponse response=new CheckAuthResponse();
        try{
            beforeValidateAuth(request);

            Claims claims=JwtTokenUtils.phaseToken(request.getToken());
            response.setUid(claims.get("uid").toString());
            response.setCode(ResponseCodeEnum.SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());

        }catch (ExpiredJwtException e){
            response.setCode(ResponseCodeEnum.TOKEN_EXPIRE.getCode());
            response.setMsg(ResponseCodeEnum.TOKEN_EXPIRE.getMsg());
        }catch (SignatureException e1){
            response.setCode(ResponseCodeEnum.SIGNATURE_ERROR.getCode());
            response.setMsg(ResponseCodeEnum.SIGNATURE_ERROR.getMsg());
        }catch (Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
        }

        return response;
    }

    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {

        UserRegisterResponse response=new UserRegisterResponse();
        try{
            beforeRegisterValidate(userRegisterRequest);

            User user=new User();
            user.setUsername(userRegisterRequest.getUsername());
            user.setPassword(userRegisterRequest.getPassword());
            user.setMobile(userRegisterRequest.getMobile());
            user.setSex(userRegisterRequest.getSex());
            user.setStatus(Constants.NORMAL_USER_STATUS);
            user.setCreateTime(new Date());

            int effectRow=userMapper.insertSelective(user);
            if(effectRow>0){
                response.setCode(ResponseCodeEnum.SUCCESS.getCode());
                response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
                return  response;
            }
            response.setCode(ResponseCodeEnum.SYSTEM_BUSY.getCode());
            response.setMsg(ResponseCodeEnum.SYSTEM_BUSY.getMsg());
            return  response;
        }
//        catch (DuplicateKeyException e){
//            //TODO 用户名重复
//        }
        catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
        }

        return response;
    }

    private void beforeRegisterValidate(UserRegisterRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
        if(StringUtils.isEmpty(request.getMobile())){
            throw new ValidateException("密码为空");
        }
    }

    private void beforeValidateAuth(CheckAuthRequest request){
        if(request==null){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getToken())){
            throw new ValidateException("token信息为空");
        }
    }


    private void beforeValidate(UserLoginRequest request){
        if(request==null){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUserName())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
    }
}
