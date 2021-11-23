package com.Service;

import com.Entity.User;
import com.Utils.Result;

import java.util.Map;

/**
 * @author 13049
 */
public interface UserService {
    /**
     * 用户账号密码登录
     * */
    Result login(Map<Object,Object> map);

    /**
     * 用户通过手机验证码登录
     * 发送验证码
     * */
    Result verifyCode(String email);

    /**
     * 用户通过手机验证码登录
     * 手机号和验证码与缓存中的数据进行比对
     * */
    Result loginOnMail(String eamil,String verifyCode);
}
