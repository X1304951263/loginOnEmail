package com.Service.Impl;

import com.Dao.UserDao;
import com.Service.UserService;
import com.Utils.GetCode;
import com.Utils.Result;
import com.Utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 13049
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    public UserDao userDao;

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    JavaMailSender mailSender;
    //默认编码
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 用户通过账号密码登录
     * 数据库搜索对应账号的密码与用户输入的密码进行比对
     * 查询报错说明没有此账号
     * */
    @Override
    public Result login(Map<Object,Object> map) {
        Result result = new Result();
        try {
            String userId = (String) map.get("UserID");
            String word = userDao.login(userId);
            if(word.equals(map.get("PassWord"))){
                String token = TokenUtil.sign(userId);
                result.setCode(200);
                result.setMessage("登录成功！");
                result.setData(token);
            }else {
                result.setCode(400);
                result.setMessage("密码错误！");
            }
        }catch (Exception e){
            result.setCode(400);
            result.setMessage("账号错误！");
        }
        return result;
    }

    /**
     * 手机号验证码登录
     * 用户发送验证码
     * 得到用户手机号，调用阿里云短信服务发送短信
     * 把用户手机号和验证码存入缓存并设置过期时间
     * 以及设置手机号发送验证码的次数和过期时间
     * */
    @Override
    public Result verifyCode(String email) {
        Result result = new Result();
        try {
            //生成6位验证码
            String code = GetCode.getVerifyCode();
            //存入redis
            if(redisTemplate.hasKey(email + "1") &&
                    Integer.valueOf(String.valueOf(redisTemplate.opsForValue().get(email + "1"))) < 5){
                redisTemplate.opsForValue().set(email,code,300,TimeUnit.SECONDS);
                redisTemplate.opsForValue().increment(email + "1",1);
                //发送邮件
                mailSender.send(GetCode.sendMes(email,code));
            }else if(!redisTemplate.hasKey(email + "1")){
                redisTemplate.opsForValue().set(email,code,300, TimeUnit.SECONDS);
                redisTemplate.opsForValue().set(email + "1","0",3600,TimeUnit.SECONDS);
                mailSender.send(GetCode.sendMes(email,code));
            }else{
                result.setCode(300);
                result.setMessage("一小时之内已发送5次，请一小时之后再试！");
                result.setData(null);
                return result;
            }
            result.setCode(200);
            result.setMessage("发送成功！");
        }catch (Exception e){
            result.setCode(400);
            result.setMessage("发送失败，请重试！");
        }

        return result;
    }

    /**
     * 手机号验证码登录
     * 接收用户手机号和密码
     * 取出缓存中该手机号的验证码与之比对
     * */
    @Override
    public Result loginOnMail(String email, String verifyCode) {
        Result result = new Result();
        try{
            if(redisTemplate.hasKey(email)){
                if(String.valueOf(redisTemplate.opsForValue().get(email)).equals(verifyCode)){
                    redisTemplate.delete(email);
                    result.setCode(200);
                    result.setMessage("验证成功！");
                    return result;
                }
            }
        }catch (Exception e){
            result.setCode(400);
            result.setMessage("验证码错误！");
        }
        return result;
    }
}
