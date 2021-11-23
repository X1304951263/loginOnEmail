package com.Entity;

import java.io.Serializable;

/**
 * @author 徐伟
 * 用户实体
 */

public class User implements Serializable {
    /**
     * 账户id，唯一
     */
    private String UserID;

    /**
     * 个人头像
     */
    private String UserProfile;

    /**
     * 账户密码
     */
    private String PassWord;

    /**
     * 账号昵称
     */
    private String UserName;

    /**
     * 个性签名
     */
    private String UserSignature;

    /**
     * 手机号，唯一
     */
    private String Email;

    /**
     * 账户余额，默认为0
     */
    private int Money;

    /**
     * 角色标志位，0代表用户，1代表商家，默认为0
     */
    private int UserStatus;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(String userProfile) {
        UserProfile = userProfile;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserSignature() {
        return UserSignature;
    }

    public void setUserSignature(String userSignature) {
        UserSignature = userSignature;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public int getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(int userStatus) {
        UserStatus = userStatus;
    }

}
