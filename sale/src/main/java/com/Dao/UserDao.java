package com.Dao;

import com.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 13049
 */
@Mapper
public interface UserDao {
    @Results({
            @Result(property = "UserID", column = "UserID"),
            @Result(property = "PassWord", column = "PassWord"),
            @Result(property = "UserProfile", column = "UserProfile"),
            @Result(property = "UserName", column = "UserName"),
            @Result(property = "UserSignature", column = "UserSignature"),
            @Result(property = "Email", column = "Email"),
            @Result(property = "Money", column = "Money"),
            @Result(property = "UserStatus", column = "UserStatus")

    })

    @Select("SELECT * FROM user WHERE id = #{0} and word = #{1}")
    List<User> get(String id, String word);

    /**
     * 将注册的用户信息插入用户表
     * 返回int型整数，1代表成功，0代表失败
     * */
    @Insert("INSERT into User(UserID, PassWord,UserProfile,UserName,UserSignature,PhoneNumber,Money,UserStatus) "   //注册用户信息插入待验证用户表
            + "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7}) ")
    int InsertUser(String UserID, String PassWord, String UserProfile, String UserName, String UserSignature
            , String Email, String Money, String UserStatus);


    /**
     * 用户通过账号密码登录
     * 返回对应的账号密码，如果返回结果为空，需要捕获异常
     * */
    @Select("SELECT PassWord FROM user WHERE UserID = #{0}")
    String login(String UserID);



    @Select("SELECT * FROM zhuce where id = #{0}")
    List<User> getZhuceUser(String id);      //验证用户注册是否重复提交

    @Select("SELECT * FROM zhuce ")
    List<User> getUser();      //获取待验证的注册用户列表

    @Delete("Delete  FROM zhuce WHERE id = #{0}")
    void deleteUser(String id);      //删除允许登录的注册用户

    @Insert("INSERT into user(id,word,name,sex,phone,email,city,account,status,money,grade) "   //允许登录的注册用户信息插入用户表
            + "values(#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},'0','0') ")
    void insertUser(String id,String word,String name,String sex,String phone,String email,String city,String account,String status);

    @Select("SELECT * from user where id = #{0} and word = #{1}" )  //获取用户个人信息
    List<User> getUserinfo(String id,String word);

    @Update("UPDATE user SET name = #{0},phone = #{1},email = #{2},city = #{3},account = #{4}  where id = #{5} and word = #{6}" )  //用户更改个人 信息
    void updateInfo(String name,String phone,String email,String city,String account,String id,String word);

    @Update("UPDATE user SET licence = #{2},card = #{3},status = #{4} where id = #{0} and word = #{1}" )  //用户注册商家
    void shopzhuce(String id,String word,String licence,String card,String status);

    @Select("SELECT id,word,name,sex,phone,email,city,account,licence,card from user where status = #{0} " ) //获取所有注册商家的用户
    List<User> getshopzhuce(String status);

    @Select("SELECT * from user where status = #{0} " )
    List<User> getshoplevel(String status);  //获取商家等级

    @Update("UPDATE user SET status = #{0},level = '5' where id = #{1}" )  //允许注册商家的用户status更改为3
    void allowshopzhuce(String status,String id);

    @Update("UPDATE user SET money = #{1} where id = #{0}" )  //用户下单后扣除相应的钱数
    void pay(String id,String money);

    @Select("SELECT * from user where id = #{0} " ) //获取用户的钱数
    List<User> umoney(String id);

    @Update("UPDATE user SET grade = #{1} where id = #{0}" )  //更新用户积分
    void grade(String id,String grade);

    @Update("UPDATE user SET level = #{1} where id = #{0}" )
    void changelevel(String id,String level); //更新商家等级

    @Update("UPDATE user SET money = #{1} where id = #{0}" )
    void chongzhi(String id,String money); //充值
}
