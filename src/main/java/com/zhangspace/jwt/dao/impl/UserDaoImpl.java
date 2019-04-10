package com.zhangspace.jwt.dao.impl;


import com.zhangspace.jwt.dao.UserDao;
import com.zhangspace.jwt.entity.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl  implements UserDao{

    private User user(){
        User user = new User();
        user.setId(1);
        user.setPassword("12345678");
        user.setUsername("admin");
        user.setMobile("13888888888");
        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        if("admin".equals(userName)){
            return  user();
        }
        return null;
    }

    @Override
    public User getUserByUid(Integer uid) {
       if(uid.equals(1)){
         return  user();
       }
        return null;
    }

    @Override
    public int insertSelective(User user) {
        return 1;
    }
}
