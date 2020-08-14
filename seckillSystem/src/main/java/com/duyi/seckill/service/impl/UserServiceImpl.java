package com.duyi.seckill.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duyi.seckill.dao.UserDao;
import com.duyi.seckill.entity.User;
import com.duyi.seckill.exception.UserException;
import com.duyi.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public User getUser(int id) {
        // 接口.调用方法，内容的执行逻辑
        return userDao.getUser(id);
    }

    public boolean login(String phone, String password) {

        User dbUser = userDao.getUserByPhone(phone);

        // 用户不存在，会出现空指针
        if (dbUser == null) {
            return false;
        }
        if (!phone.equals(dbUser.getPhone())) {
            return false;
        }
        if (!dbUser.getPassword().equals(DigestUtil.md5Hex(password))) {
            return false;
        }
        return true;
    }

    public void regist(User user) {
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        userDao.insert(user);
    }

}
