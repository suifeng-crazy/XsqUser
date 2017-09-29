package com.example.xsq.util;

import com.example.xsq.Ent.User;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import aes.WXBizMsgCrypt;

/**
 * Created by suifeng on 2017/9/11.
 * 所有链接 服务器数据的加密、返回数据的解密 等操作工具
 */
public class PostParma {

    /**
     *  登录获取token ，
     * @param userName
     * @param passwrod
     * @return          返回int 类型， 1 代表成功，2代表失败，message 代表错误信息，
     * @throws Exception
     */
    public static int getToken(String userName ,String passwrod) throws Exception {
        System.out.println("1");
        String request = null;
//        WXBizMsgCrypt wxcpt ;
        JSONObject jsonObject;
//        wxcpt = new WXBizMsgCrypt(NumberUtil.sToken, NumberUtil.sEncodingAESKey, NumberUtil.sCorpID);
        JSONObject json= JsonStringMapUtil.MapToJSONObject(userName, passwrod);
        System.out.println(json);
        String newData = userName+"&"+passwrod;
        request = HttpGetOrPost.getJsonHttpGetLin(ConnectionAddress.BASE_GET_TOKEN, newData); // query.toGetToken() 是 用户登陆的信息。
        System.out.println("登录返回："+request);
        if(request == null ||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return 0;
        }
//        JSONArray array=new JSONArray(request);
//        String sMsg = wxcpt.DecryptMsg(array.getString(0), array.getString(1), array.getString(2), array.getString(3));
        jsonObject = new JSONObject(request);
//        System.out.println(sMsg);

        if(jsonObject.optBoolean("success")){
            NumberUtil.token = jsonObject.optJSONObject("data").optString("LOGIN_TOKEN");
            NumberUtil.sellerId =jsonObject.optJSONObject("data").optString("CODE");
            return 1;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return 2;
        }
    }

    /**
     *  获取手机验证码是否成功
     * @param url       请求地址
     * @param phone    电话号码
     * @return          Boolean 类型是否获取成功
     */
    public static Boolean getPhoneCode(String url,String phone) throws  Exception {
        JSONObject jsonObject;
        String newData = "phe="+phone;
        String request = HttpGetOrPost.getJsonHttpGetLin(url, newData);
        System.out.println("获取验证码返回:"+request);
        if(request == null||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return false;
        }
        jsonObject = new JSONObject(request);
        if(jsonObject.optBoolean("success")){
            return true;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return false;
        }
    }

    /**
     *  验证手机验证码
     * @param url
     * @param userPhone
     * @param mPhoneCode
     * @return
     * @throws Exception
     */
    public static Boolean isTruePhoneCode(String url,String userPhone, String mPhoneCode) throws Exception {
        JSONObject jsonObject;
        String newData = "phe="+userPhone+"&code="+mPhoneCode;
        String request = HttpGetOrPost.getJsonHttpGetLin(url, newData);
        System.out.println("验证手机验证码:"+request);
        if(request == null ||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return false;
        }
        jsonObject = new JSONObject(request);
        if(jsonObject.optBoolean("success")){
            return true;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return false;
        }
    }

    /**
     * 个人中心页面获取 个人基本信息
     * @param token
     */
    public static User getUserInfo(String token) throws Exception {
        JSONObject jsonObject;
        String newData = "_t="+token;
        String request = HttpGetOrPost.getJsonHttpGetLin(ConnectionAddress.Base_MeMain, newData);
        System.out.println("个人基本信息:"+request);
        if(request == null ||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return null;
        }
        jsonObject = new JSONObject(request);
        if(jsonObject.optBoolean("success")){
            User user = new User();
            JSONObject jsonData = jsonObject.optJSONObject("data");
            user.setUserPhone(jsonData.optString("PHONE"));
            user.setUserSex(jsonData.optString("SEX"));
            user.setUserRealName(jsonData.optString("REAL_NAME"));
            user.setUserMoney(jsonData.optDouble("PURSE_BALANCE"));
            user.setUserGod(jsonData.optInt("GOLD_BALANCE"));
            user.setUserHeaderImage(jsonData.optString("AUTOGRAPH"));
            user.setUserRedMoney(jsonData.getDouble("VOUCHER_BALANCE"));
            return user;
        }else {
            NumberUtil.token = "";
            NumberUtil.strError = jsonObject.optString("message");
            return null;
        }
    }

    /**
     *  用户注册
     * @param userPhone
     * @param mPasswrodF        密码
     * @param code              验证码
     * @return
     */
    public static Boolean regUser(String userPhone, String mPasswrodF, String code) throws Exception{
        JSONObject jsonObject;
        String newData = "phe="+userPhone+"&code="+code+"&pwd="+mPasswrodF;
        String request = HttpGetOrPost.getJsonHttpGetLin(ConnectionAddress.Base_reg, newData);
        System.out.println("用户注册:"+request);
        if(request == null ||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return false;
        }
        jsonObject = new JSONObject(request);
        if(jsonObject.optBoolean("success")){
            NumberUtil.token = jsonObject.optJSONObject("data").optString("LOGIN_TOKEN");
            NumberUtil.sellerId =jsonObject.optJSONObject("data").optString("CODE");
            return true;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return false;
        }
    }
}
