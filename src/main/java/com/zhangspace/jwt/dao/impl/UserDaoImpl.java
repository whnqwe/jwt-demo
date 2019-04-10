package com.zhangspace.jwt.dao.impl;


import com.zhangspace.jwt.dao.UserDao;
import com.zhangspace.jwt.entity.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl  implements UserDao{
    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public User getUserByUid(Integer uid) {
        return null;
    }

    @Override
    public int insertSelective(User user) {
        return 0;
    }
}
