package com.Controller;

import com.Entity.User;
import com.Service.Impl.UserServiceImp;
import com.Utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author 13049
 */
@RestController
@Slf4j
@CrossOrigin
public class UserController {
    //测试邮箱 1983680732@qq.com
    @Autowired
    public UserServiceImp userServiceImp;


    @RequestMapping(value = "/test")
    @ResponseBody
    public Result login(){
        Result result = new Result();
        result.setCode(200);
        return result;
    }

    @RequestMapping(value = "/demo")
    public Result demo(){
        return userServiceImp.verifyCode("cuichenyao@126.com");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody Map<Object,Object> map){
        System.out.println(map.toString());
        return userServiceImp.login(map);
    }

    @RequestMapping(value = "/sendMes",method = RequestMethod.POST)
    @ResponseBody
    public Result sendMes(@RequestBody Map<Object,Object> map){
        System.out.println(map.get("email"));
        return userServiceImp.verifyCode((String)map.get("email"));
    }

    @RequestMapping(value = "/loginOnEmail",method = RequestMethod.POST)
    @ResponseBody
    public Result loginOnEmail(@RequestBody Map<Object,Object> map){
        System.out.println((String)map.get("email") + (String)map.get("code"));
        return userServiceImp.loginOnMail((String)map.get("email"),(String)map.get("code"));
    }


}
