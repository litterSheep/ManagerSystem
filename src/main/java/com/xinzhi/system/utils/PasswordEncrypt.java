package com.xinzhi.system.utils;

import com.xinzhi.system.entity.UserInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by ly on 2017/7/20 9:54.
 */
public class PasswordEncrypt {

    //随机数生成器
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    //指定散列算法为md5
    public static final String algorithmName = "MD5";
    //散列迭代次数
    public static final int hashIterations = 3;

    /**
     * 生成随机盐值对密码进行加密
     *
     * @return
     */
    public static UserInfo encrypt(UserInfo userInfo) {
        userInfo.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(algorithmName, userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()), hashIterations).toHex();

        userInfo.setPassword(newPassword);
        return userInfo;
    }

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword("123456");
        userInfo.setUsername("admin");
        UserInfo encrypt = PasswordEncrypt.encrypt(userInfo);

        System.out.println("加密后password：" + encrypt.getPassword() + " salt:" + encrypt.getCredentialsSalt());
    }
}
