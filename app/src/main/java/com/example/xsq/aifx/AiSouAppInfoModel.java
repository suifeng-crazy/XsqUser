package com.example.xsq.aifx;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by S_Y_H on 2016/11/13.18:13
 */
public final class AiSouAppInfoModel {

    @SuppressLint("StaticFieldLeak")
    private static Context sApplicationContext;
    private final static AtomicReference<AiSouAppInfoModel> AI_SOU_APP_INFO_MODEL_ATOMIC_REFERENCE = new AtomicReference<>();

    /**
     * 交流中心 登录成功
     */
    public final static int STATE_CHAT_LOGIN_SUCCESSFUL = 0xA;
    /**
     * 交流中心 聊天账号 被移除
     */
    public final static int STATE_CHAT_REMOVE = 0xB;
    /**
     * 交流中心 登录失败
     */
    public final static int STATE_CHAT_LOGIN_FAIL = 0xC;
    /**
     * 交流中心 别地登录
     */
    public final static int STATE_CHAT_CONFLICT = 0xD;

    /**
     * 默认行为
     */
    public final static int STATE_CHAT_DEFAULT = 0xE;

    /**
     * 启动标记
     */
    public final static String KEY_FLAG = "K_FG";

    //uid
    public final static String KEY_UID = "k_U";
    //极光推送 上次为成功设置的tag
    public final static String KEY_J_PUSH_LAST_TAGS = "k_JPT";
    //登录的手机号
    public final static String KEY_USER_PHONE = "k_P";
    //token
    public final static String KEY_TOKEN = "k_T";
    //文件名
    public final static String NAME_SHARED_PREFERENCES = "AS_AS";

    /**
     * 崩溃文件目录
     */
    public final static String CRASH_FOLDER_NAME = "ASC";

    /**
     * 是否忽略了升级
     */
    public final static String KEY_HINT_UPDATE = "k_h_u";

    /**
     * 用户信息
     */
//    private AiSouUserBean mAiSouUserBean = new AiSouUserBean();

    /**
     * 位置信息
     */
//    private AiSouLocationBean mAiSouLocationBean = new AiSouLocationBean();

    /**
     * 是否有极光推送未设置成功的tag
     */
    private boolean hasJPushFailTags;

    /**
     * 显示 消息的提示点
     */
    private boolean hasMessageHint;

    /**
     * 未设置成功的tags
     */
    private String mJPushTags;

    /**
     * 请求移除当前账号
     */
    private boolean mRequestRemoveAccount;

    /**
     * 交流中心的登录状态
     */
    private int isChatLoginState;
    /**
     * 环信未成功登录的描述信息
     */
    private String mChatLoginDescription;

    /**
     * 是否登录
     */
    private boolean isLogin;

    private AiSouAppInfoModel(Context application) {
        if (application != null && !(application instanceof Application)) {
            sApplicationContext = application.getApplicationContext();
        } else {
            sApplicationContext = application;
        }
    }

//    public static AiSouAppInfoModel getInstance(Context application) {
//        for (; ; ) {
//            AiSouAppInfoModel aiSouAppInfoModel = AI_SOU_APP_INFO_MODEL_ATOMIC_REFERENCE.get();
//            if (null != aiSouAppInfoModel) {
//                return aiSouAppInfoModel;
//            }
//            aiSouAppInfoModel = new AiSouAppInfoModel(application);
//
//            if (AI_SOU_APP_INFO_MODEL_ATOMIC_REFERENCE.compareAndSet(null, aiSouAppInfoModel)) {
//                return aiSouAppInfoModel;
//            }
//        }
//    }

    /**
     * 对应application 的onCreate方法
     */
    public void onCreate() {
    }

//    public static AiSouAppInfoModel getInstance() {

//        return AI_SOU_APP_INFO_MODEL_ATOMIC_REFERENCE.get();
//    }

    public static Context getAppContext() {
        return sApplicationContext;
    }

    public static void initFirstComponents() {
//        AiSouSDKModel.initFirstComponents(sApplicationContext, AppConfig.IS_DEBUG);
    }

    public static void initThirdComponents(Context context) {
//        AiSouSDKModel.initThirdComponent(null == sApplicationContext ? context.getApplicationContext()
//                : sApplicationContext, AppConfig.IS_DEBUG);
    }

    public static void initSecondComponents(Context context) {
//        AiSouSDKModel.initSecondComponents(null == sApplicationContext ?
//                context.getApplicationContext() : sApplicationContext, AppConfig.IS_DEBUG);

    }


//    @NonNull
//    public AiSouUserBean getAiSouUserBean() {
//        return this.mAiSouUserBean;
//    }

//    @NonNull
//    public AiSouLocationBean getAiSouLocationBean() {
//        return this.mAiSouLocationBean;
//    }

    public boolean isHasMessageHint() {
        return this.hasMessageHint;
    }

    @ChatLoginState
    public int getIsChatLoginState() {
        return this.isChatLoginState;
    }

    public boolean isChatLogin() {
        return AiSouAppInfoModel.STATE_CHAT_LOGIN_SUCCESSFUL == this.isChatLoginState;
    }

    public String getChatLoginDescription() {
        return this.mChatLoginDescription;
    }

    public void setChatLoginDescription(String des) {
        this.mChatLoginDescription = des;
    }

    @Retention(SOURCE)
    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @IntDef({STATE_CHAT_LOGIN_SUCCESSFUL, STATE_CHAT_DEFAULT, STATE_CHAT_REMOVE, STATE_CHAT_LOGIN_FAIL, STATE_CHAT_CONFLICT})
    @interface ChatLoginState {
    }

    public void setChatLogin(@ChatLoginState int state) {
        this.isChatLoginState = state;
    }

    public void setHasMessageHint(boolean hasMessageHint) {
        this.hasMessageHint = hasMessageHint;
    }

    public void setHasJPushFailTags(boolean has) {
        this.hasJPushFailTags = has;
    }

    public boolean getHasJPushFailTags() {
        return this.hasJPushFailTags;
    }

    public String getJPushTags() {
        return this.mJPushTags;
    }

    public void setJPushTags(String jPushTags) {
        this.mJPushTags = jPushTags;
    }

    public void setRequestRemoveAccount(boolean requestRemoveAccount) {
        if (isLogin) {
            this.mRequestRemoveAccount = requestRemoveAccount;
        } else if (mRequestRemoveAccount) {
            mRequestRemoveAccount = false;
        }
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    boolean getRequestRemoveAccount() {
        return this.mRequestRemoveAccount;
    }

//    @Deprecated
//    public String getUID() {
//        return mAiSouUserBean.getAiSouID();
//    }
}
