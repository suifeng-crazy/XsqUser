package com.example.xsq.Ent;

/**
 * Created by suifeng on 2017/9/28.
 */

public class User {
    public String userName ;
    public String userPhone;
    public Double userMoney;
    public Double userRedMoney;
    public Double userGod;
    public String userNickName;
    public String userSex;
    public String userRealName;
    public String userSign; // 个性签名

    public String userAddress;
    public String userRecommedMen; // 推荐人编码（推荐他的人）
    public String userRecommedCode; // 推荐编码
    public String userHeaderImage;
    public String userID;

    public User() {
        this.userName = "";
        this.userPhone = "";
        this.userMoney = 0.00;
        this.userRedMoney = 0.00;
        this.userGod = 0.00;
        this.userNickName = "";
        this.userSex = "";
        this.userRealName = "";
        this.userSign = "";
        this.userAddress = "";
        this.userRecommedMen = "";
        this.userRecommedCode = "";
        this.userHeaderImage = "";
        this.userID = "";
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserHeaderImage() {
        return userHeaderImage;
    }

    public void setUserHeaderImage(String userHeaderImage) {
        this.userHeaderImage = userHeaderImage;
    }

    public Double getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(Double userMoney) {
        this.userMoney = userMoney;
    }

    public Double getUserRedMoney() {
        return userRedMoney;
    }

    public void setUserRedMoney(Double userRedMoney) {
        this.userRedMoney = userRedMoney;
    }

    public Double getUserGod() {
        return userGod;
    }

    public void setUserGod(Double userGod) {
        this.userGod = userGod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserRecommedMen() {
        return userRecommedMen;
    }

    public void setUserRecommedMen(String userRecommedMen) {
        this.userRecommedMen = userRecommedMen;
    }

    public String getUserRecommedCode() {
        return userRecommedCode;
    }

    public void setUserRecommedCode(String userRecommedCode) {
        this.userRecommedCode = userRecommedCode;
    }

}
