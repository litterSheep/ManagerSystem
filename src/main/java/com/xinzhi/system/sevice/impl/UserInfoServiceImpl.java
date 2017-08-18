package com.xinzhi.system.sevice.impl;

import com.xinzhi.system.dao.UserInfoDao;
import com.xinzhi.system.entity.UserInfo;
import com.xinzhi.system.sevice.UserInfoService;
import com.xinzhi.system.utils.PasswordEncrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }

    @Override
    public void addUser(UserInfo userInfo) {
        userInfoDao.save(PasswordEncrypt.encrypt(userInfo));
    }


}