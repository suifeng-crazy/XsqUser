package com.example.xsq.util;
/**
 * 与服务器链接使用地址 公共类。
 * @author Administrator
 *
 */
public class ConnectionAddress {
    // 基础IP 地址
    public static final String BASE_Adress="http://192.168.1.191";
    // 基础 文件地址 (被保存下来的。不可修改。)
    public static  String BASE_FileAdress = "";
    // 基础 文件地址 (被保存下来的。不可修改。)
    public static final String BASE_Image_FileAdress = "http://192.168.1.191:8081";


    // 基础二级分类地址
    public static final String BASE_SecondAdress = "/app/hm";

    //  版本升级
    public static final String BASE_CheckEdt = BASE_Adress+"/app/hm/inx/apv/cav?plat=android";
    // 获取关于我们H5 页面地址
    public static final String BASE_Get_AboutUs = BASE_Adress+"/app/hm/inx/apv/au";

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
    //使用原密码修改登录密码
    public static final String Base_Get_ChangeLoginPasByBeforPas = BASE_Adress+"/app/mg/inx/ucc/uppd";
    //使用手机号修改登录密码获取手机验证码
    public static final String Base_Get_ChangeLoginPasGetPhoneCode = BASE_Adress+"/app/mg/inx/ucc/sulpc";
    //使用手机号修改登录密码
    public static final String Base_Get_ChangeLoginPasByPhone = BASE_Adress+"/app/mg/inx/ucc/ulpbs";
    // 保存会员基本信息(头像,昵称,性别,个性签名等)
    public static final String  Base_Get_ChangeNickName = BASE_Adress+"/app/mg/inx/ucc/sbm";
    // 设置支付密码
    public static final String Base_Get_SetPayPas = BASE_Adress+"/app/mg/inx/ucc/spp";
    // 检测是否设置过支付密码
    public static final String  Base_Get_CheckIsSetPayPas = BASE_Adress+"/app/mg/inx/ucc/hpp";
    //使用原密码修改支付密码
    public static final String Base_Get_ChangePayPasByBeforPas = BASE_Adress+"/app/mg/inx/ucc/upp";
    //使用手机号修改支付密码获取手机验证码
    public static final String Base_Get_ChangePayPasGetPhoneCode = BASE_Adress+"/app/mg/inx/ucc/suppc";
    //使用手机号修改支付密码
    public static final String Base_Get_ChangePayPasByPhone = BASE_Adress+"/app/mg/inx/ucc/uppsms";
    // 获取修改手机号的验证码
    public static final String  BASE_Get_ChangePhoneGetCode = BASE_Adress+"/app/mg/inx/ucc/supc";
    // 修改手机号
    public static final String  BASE_Get_ChangePhone = BASE_Adress+"/app/mg/inx/ucc/up";

    // 头像上传接口
    public static final String  BASE_Upload_UserHeaderImage = BASE_Image_FileAdress+"/fileMg/up";
}
