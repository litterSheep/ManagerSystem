package com.xinzhi.system.sevice;


import com.xinzhi.system.entity.UserInfo;

public interface UserInfoService {
    /**
     * 通过phone查找用户信息;
     */
    UserInfo findByUsername(String username);

    void addUser(UserInfo userInfo);

}