package com.example.xsq.util;
/**
 * 与服务器链接使用地址 公共类。
 * @author Administrator
 *
 */
public class ConnectionAddress {
    // 基础IP 地址
    public static final String BASE_Adress="http://192.168.1.191";
    // 基础 文件地址
    public static  String BASE_FileAdress = "";

    // 基础二级分类地址
    public static final String BASE_SecondAdress = "/app/hm";

    //  版本升级
    public static final String BASE_CheckEdt = BASE_Adress+"/app/hm/inx/apv/cav?plat=android";

    // 登录使用接口地址
    public static final String BASE_GET_TOKEN = BASE_Adress+"/app/hm/lg/reg/dlg";
    // 获取注册验证码
    public static final String Base_Get_PhoneCode= BASE_Adress+BASE_SecondAdress+"/lg/reg/src";
    // 获取登陆验证码
    public static final String Base_Get_PhoneLoginCode= BASE_Adress+BASE_SecondAdress+"/lg/reg/slg";
    // 获取忘记登录密码验证码
    public static final String Base_Get_ForgotLoginCode= BASE_Adress+BASE_SecondAdress+"/lg/reg/sfp";

    //验证短信验证码是否正确
    public static final String Base_IsTrue_PhoneCode= BASE_Adress+BASE_SecondAdress+"/lg/reg/scc";
    // 设置新登录密码
    public static final String Base_newreg= BASE_Adress+BASE_SecondAdress+"/lg/reg/dsnl";
    // 用户申请注册
    public static final String Base_reg= BASE_Adress+BASE_SecondAdress+"/lg/reg/dreg";

    //个人中心
    public static final String Base_MeMain= BASE_Adress+"/app/mg/inx/ucc/inf";
    //获取 文件基础地址
    public static final String Base_Get_FileAddress= BASE_Adress+"/app/hm/inx/apv/cnf";
}
