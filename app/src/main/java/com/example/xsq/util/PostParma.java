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
            NumberUtil.userID = jsonObject.optJSONObject("data").optString("ID");
            return 1;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return 0;
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
            user.setUserGod(jsonData.optDouble("GOLD_BALANCE"));
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
    public static Boolean regUser(String userPhone, String mPasswrodF, String code,String math) throws Exception{
        JSONObject jsonObject;
        String newData = "";
        String request = "";
        System.out.println("phe="+userPhone+"&code="+code+"&pwd="+mPasswrodF);
        if(math.equals("BindPhone")){
            newData = "phe="+userPhone+"&code="+code+"&pwd="+mPasswrodF;
            request = HttpGetOrPost.getJsonHttpGetLin(ConnectionAddress.Base_reg, newData);
        }else{
            newData = "phe="+userPhone+"&code="+code+"&loginPwd="+mPasswrodF;
            request = HttpGetOrPost.getJsonHttpGetLin(ConnectionAddress.Base_newreg, newData);
        }

        System.out.println("用户注册:"+request);
        if(request == null ||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return false;
        }
        jsonObject = new JSONObject(request);
        if(jsonObject.optBoolean("success")){
            if(math.equals("BindPhone")) {
                NumberUtil.token = jsonObject.optJSONObject("data").optString("LOGIN_TOKEN");
                NumberUtil.sellerId = jsonObject.optJSONObject("data").optString("CODE");
            }
            return true;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return false;
        }
    }

    /**
     * 获取 文件存储位置
     * @param base_get_fileAddress
     * @param file_server
     */
    public static void getFileAddress(String base_get_fileAddress, String file_server) throws Exception {
        JSONObject jsonObject;
        String newData = "key="+file_server;
        String request = HttpGetOrPost.getJsonHttpGetLin(base_get_fileAddress, newData);
        System.out.println("获取文件地址:"+request);
        if(request == null ||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return ;
        }
        jsonObject = new JSONObject(request);
        if(jsonObject.optBoolean("success")){
            ConnectionAddress.BASE_FileAdress = jsonObject.optString("data");
            return ;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return ;
        }
    }

    /**
     * 使用原密码修改登录密码
     * @param base_isTrue_phoneCode
     * @param sBeforePas
     * @param sNewPas
     * @return
     * @throws Exception
     */
    public static Boolean isChangeLoginPasByBeforPas(String base_isTrue_phoneCode, String sBeforePas, String sNewPas)throws Exception {
        JSONObject jsonObject;
        String newData = "npd="+sNewPas+"&lpd="+sBeforePas+"&_t="+NumberUtil.token;
        System.out.println("isChangeLoginPasByBeforPas newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(base_isTrue_phoneCode, newData);
        System.out.println("获取文件地址:"+request);
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
     * 使用手机号修改登录密码
     * @param base_isTrue_phoneCode
     * @param phoneCode
     * @param sNewPas
     * @return
     * @throws Exception
     */
    public static Boolean isChangeLoginPasByPhone(String base_isTrue_phoneCode, String phoneCode, String sNewPas)throws Exception {
        JSONObject jsonObject;
        String newData = "npd="+sNewPas+"&code="+phoneCode+"&_t="+NumberUtil.token;
        System.out.println("isChangeLoginPasByPhone newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(base_isTrue_phoneCode, newData);
        System.out.println("isChangeLoginPasByPhone request:"+request);
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
     * 使用手机号修改登录密码获取手机验证码
     * @param base_isTrue_phoneCode
     * @return
     * @throws Exception
     */
    public static Boolean isChangeLoginPasGetPhoneCode(String base_isTrue_phoneCode)throws Exception {
        JSONObject jsonObject;
        String newData = "_t="+NumberUtil.token;
        System.out.println("isChangeLoginPasGetPhoneCode newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(base_isTrue_phoneCode, newData);
        System.out.println("isChangeLoginPasGetPhoneCode 获取手机验证码:"+request);
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
     * 修改个人信息 ，（昵称、真实姓名 通用这一个。）
     * @param base_get_changeLoginPasByBeforPas
     * @param sNickeName
     * @param nickName    接口需要的 键值
     * @return
     * @throws Exception
     */
    public static Boolean isChangeNickName(String base_get_changeLoginPasByBeforPas, String sNickeName,String nickName) throws Exception {
        JSONObject jsonObject;
        String newData = "_t="+NumberUtil.token+"&"+nickName+"="+sNickeName;
        System.out.println("isChangeLoginPasGetPhoneCode newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(base_get_changeLoginPasByBeforPas, newData);
        System.out.println("isChangeLoginPasGetPhoneCode 获取手机验证码:"+request);
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
     *  设置支付密码
     * @param base_get_setPayPas
     * @param sNewPas
     * @return
     * @throws Exception
     */
    public static Boolean isSetPayPas(String base_get_setPayPas, String sNewPas) throws Exception {
        JSONObject jsonObject;
        String newData = "_t="+NumberUtil.token+"&npd="+sNewPas    ;
        System.out.println("isChangeLoginPasGetPhoneCode newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(base_get_setPayPas, newData);
        System.out.println("isChangeLoginPasGetPhoneCode 获取手机验证码:"+request);
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


    public static int isTrueOnlyToken(String base_get_checkIsSetPayPas) throws Exception {
        JSONObject jsonObject;
        String newData = "_t="+NumberUtil.token;
        System.out.println("isChangeLoginPasGetPhoneCode newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(base_get_checkIsSetPayPas, newData);
        System.out.println("isChangeLoginPasGetPhoneCode 获取手机验证码:"+request);
        if(request == null ||request.equals("")){
            ParsingJsonString.nowConnectBase();
            return 0;
        }
        jsonObject = new JSONObject(request);
        if(jsonObject.optBoolean("success")){
            return 1;
        }else {
            NumberUtil.strError = jsonObject.optString("message");
            return 2;
        }
    }

    /**
     *  基础 方法，一个参数， 返回是否正确 的功能。
     * @param base_get_changePhoneGetCode  请求接口路径
     * @param mStPhone      传递的值
     * @param phe           接口需要键值
     * @return
     * @throws Exception
     */
    public static Boolean baseIsTrueFirstParam(String base_get_changePhoneGetCode, String mStPhone, String phe)throws Exception {

        JSONObject jsonObject;
        String newData = "_t="+NumberUtil.token+"&"+phe+"="+mStPhone    ;
        System.out.println("isChangeLoginPasGetPhoneCode newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(base_get_changePhoneGetCode, newData);
        System.out.println("isChangeLoginPasGetPhoneCode 获取手机验证码:"+request);
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
     * 基础 方法，两个参数， 返回是否正确 的功能。
     * @param connAddress 请求接口路径
     * @param firstKey
     * @param firstValue
     * @param secondKey
     * @param secondValue
     * @return
     */
    public static Boolean baseIsTrueSecondParam(String connAddress, String firstKey, String firstValue, String secondKey, String secondValue) throws  Exception {
        JSONObject jsonObject;
        String newData = "_t="+NumberUtil.token+"&"+firstKey+"="+firstValue+"&"+secondKey+"="+secondValue;
        System.out.println("isChangeLoginPasGetPhoneCode newData:"+newData);
        String request = HttpGetOrPost.getJsonHttpGetLin(connAddress, newData);
        System.out.println("isChangeLoginPasGetPhoneCode 获取手机验证码:"+request);
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
}
