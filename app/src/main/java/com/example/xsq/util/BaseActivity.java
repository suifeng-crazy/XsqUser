package com.example.xsq.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.xsq.Login.MainActivity;
import com.example.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 所有Activity的基础类， 一些封装的基础方法、少量经常用的数据 会放到这里面。
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener,View.OnFocusChangeListener {
//    public static  BaseApplication mApplication;
   public CustomProgressDialog loading_Dialog;
    //访问网络状态
    protected  static final short STATUS_NUM_NULL = 106; // 数据解析出错
    protected static final short STATUS_CONNECT_TIMEOUT = 109; // 服务器链接超时。
    protected static final short STATUS_OK=100;
    protected static final short STATUS_CHECK=101;
    protected static final short STATUS_UPDATE=102;
    protected static final short STATUS_CLOSE=103;
    protected static final short STATUS_ERROR=104;
    protected static final short STATUS_MORE=105;
    protected static final int SDK_PAY_FLAG = 1;
    protected  static final short STATUS_NOMORE=108;// 刷新没有更多了。
    protected static final short STATUS_NOONE = 107; // 一个也没有了

    //程序信息
    protected PackageInfo packageInfo;

    //储存activity列表
    protected static List<Activity> activities;

    //缓存线程池
    protected static ExecutorService cachedThreadPool;

    protected long mExitTime;


    /**
     * Activity 创建的时候执行的方法。
     */
    @Override
    protected void onStart() {

        super.onStart();

// 展示Dialog 而且不能 点击消失 ， 只有执行操作的时候才能消失。
//        toastMessage(this,"xxxxxxxxxxxxxxxxxxxxxxxx");
        loading_Dialog = new CustomProgressDialog(this, R.style.dialog);
        loading_Dialog.setCancelable(false);//设置进度条是否可以按退回键取消
        //设置点击进度对话框外的区域对话框不消失
        loading_Dialog.setCanceledOnTouchOutside(false);
        //这个是改变Dialog的背景透明度：
        Window wd= loading_Dialog.getWindow();
        WindowManager.LayoutParams lp = wd.getAttributes();
        lp.alpha = 0.5f;
        wd.setAttributes(lp);
//		lp.alpha = 0.5f 设置透明度，值可以自己测试
//		loading_Dialog.setContentView(R.layout.base_dialog);

        // 这个要在后面解放出来， 现在是为了微信 分享后 加载框 测试。
        if(NumberUtil.showDialog){
            loading_Dialog.show();
        }else{
            NumberUtil.showDialog = true;
        }

    }

    /**
     *  显示加载框
     */
    public void showDialog(){
        loading_Dialog.show();
    }

    /**
     * 隐藏加载框
     */
    public void hideDialog(){
         loading_Dialog.dismiss();
    }

    /**
     * Activity 刚开始执行的时候（在 start 后面执行） 执行的方法，
     * 这个执行的灰比较快。
     */
    @Override
    protected void onResume() {
        super.onResume();
//        if(NumberUtil.isMainActivity){
//            NumberUtil.isMainActivity = false;
            loading_Dialog.dismiss();
//        }

    }

    /**
     * Activity 创建方法。
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


//        initBaseUI();
//        if(mApplication==null){
//            mApplication=(BaseApplication)getApplication();
//        }
//
//        //设置版本名以及版本号
//        if(mApplication.currVerCode==0&&mApplication.currVerName==null){
//            try {
//                packageInfo=getPackageManager().getPackageInfo(getPackageName(),0);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            mApplication.currVerCode=(short)packageInfo.versionCode;
//            mApplication.currVerName=packageInfo.packageName;
//        }

        //创建缓存线程池
        if(cachedThreadPool==null){
            cachedThreadPool= Executors.newCachedThreadPool();
        }

        //activity Stack 列表
        if(activities==null){
            activities=new ArrayList<Activity>();
        }

        ActivityCollector.addActivity(this);
    }

    /**
     * Activity 销毁执行的方法。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * 移除Activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        activities.remove(activity);
    }

    /**
     * 退出程序
     */
    public void exitServer(){
        for(Activity activity : activities){
            if(activity!=null){
                activity.finish();
            }
        }
        finish();
        System.exit(0);
    }

    /**
     * activity跳转
     * 默认进入跳转
     * toActivity(context,cls,false,false);
     * @param context
     * @param cls
     */
    public void strActivity(Context context, Class<?> cls){
        strActivity(context, cls, false, false);
    }

    /**
     * 快速跳转
     * 先更改跳转样式在finish
     * @param context
     * @param cls
     * @param closeActivity 是否关闭当前activity
     * @param isOut false 转入   true 转出
     */
    public void strActivity(Context context, Class<?> cls, Boolean closeActivity, Boolean isOut){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        if (closeActivity) {
            finish();
        }
    }

    /**
     * 传参数快速跳转(单参数)
     * @param context
     * @param cls
     * @param closeActivity
     * @param isOut
     * @param flg 参数 接收默认为 "flg" 不可修改
     */
    public void strActivity(Context context, Class<?> cls,
                            Boolean closeActivity, Boolean isOut, String flg) {
        strActivity(context, cls, closeActivity, isOut, flg, "", "");
    }

    /**
     * 传参跳转(双参数)
     * @param context
     * @param cls
     * @param closeActivity
     * @param isOut
     * @param flg 第一个参数，接收为 "flg"
     * @param flg2Name 第二个参数接收名称,名称不能为空否则不传
     * @param flg2Value 第二个参数
     */
    public void strActivity(Context context, Class<?> cls,
                            Boolean closeActivity, Boolean isOut, String flg, String flg2Name,
                            String flg2Value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("flg", flg);
        if(!flg2Name.equals("")) {
            intent.putExtra(flg2Name, flg2Value);
        }
        context.startActivity(intent);
        setGo(isOut);
        if (closeActivity) {
            finish();
        }

    }

    /**
     * 带跳转样式的关闭activity
     */
    public void finishActivity(){
        setGo(true);
        ActivityCollector.finishAll();
    }

    /**
     * 定义了activity的跳转样式
     * 做成公共方法， 然后像 errorMessage 一样，
     */
    public void setGo(boolean isOut) {
        // 设置默认值
        int inEnter = R.anim.zoomin;
        int inExit = R.anim.zoomout;
        int outEnter = R.anim.backin;
        int outExit = R.anim.backout;

        if (!isOut) {
            overridePendingTransition(inEnter, inExit);
        } else {
            overridePendingTransition(outExit, outEnter);
        }
    }

    /**
     * 快速提示
     * 时长 LENGTH_LONG
     * @param context
     * @param str
     */
    protected void toastMessage(Context context, String str) {
        toastMessage(context,str, Toast.LENGTH_LONG);
        if(NumberUtil.strErrorNum == 10007 || NumberUtil.strErrorNum ==105|| NumberUtil.strErrorNum ==109) {
            strActivity(context, MainActivity.class, true, true);
        }
    }

    /**
     * 快速提示
     * @param context
     * @param str
     * @param time 提示时长
     */
    protected void toastMessage(Context context, String str, int time){
        Toast.makeText(context, str, time).show();
    }

    /**
     * 提示错误信息，并且可能跳回登陆页面
     * @param context
     */
    public void toastMessageErrorInfo(Context context){
        toastMessage(context, NumberUtil.strError);
        NumberUtil.strError = "暂无数据";
        if(NumberUtil.strErrorNum == 10007 || NumberUtil.strErrorNum ==105|| NumberUtil.strErrorNum ==109) {
            strActivity(context, MainActivity.class, true, true);
        }
    }


    /**
     * 数据解析出错， 提示错误信息，并且可能跳回登陆页面
     * @param context
     */
    public void toastMessageErrorInfo(Context context , int i ){  // 暂时i 不用区分，只要一个数据即可。 暂时用1
        //if(i == 0){ // 就是数据解析错误。
             NumberUtil.strError = NumberUtil.strJXError;
        toastMessage(context, NumberUtil.strError);
        NumberUtil.strError = "暂无数据";
        if(NumberUtil.strErrorNum == 10007 || NumberUtil.strErrorNum ==105|| NumberUtil.strErrorNum ==109) {
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);
//            startActivity(context,MainActivity.class);
//            strActivity(context, MainActivity.class, true, true);
        }
    }

    public void toastMessageNoNet(Context context) {
        toastMessage(context, "请检查网络连接");
    }

    /**
     * 网络判断
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        toastMessageNoNet(context);
        return false;
    }



    /**
     * 返回按钮finish activity
     * （之前的 按两下返回按钮 才退出系统
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            setGo(false);
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


    /**
     * Activity 里面的 点击事件。
     * @param v
     */
    @Override
    public void onClick(View v) {

    }
    public boolean onDoubleClickBackExit(){
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Object mHelperUtils;
            toastMessage(this, "再按一次退出程序", Toast.LENGTH_SHORT);
            mExitTime = System.currentTimeMillis();
        } else {
//            exitServer();
            ActivityCollector.finishAll();// 按两次 退出键。
        }
        return true;
    }

    /**
     * 报错之后跳转到登陆页面。
     * @param context
     */
    protected void toastMessageError(Context context) {
//	    	toastMessage(context, BaseDao.strError);
//	    	if(BaseDao.strError.equals(ParsingJsonString.STR_LOGIN_OUTTIME)) {
//	            strActivity(context, FirstActivity.class, true, true);
//
//	        }
    }

    /**
     * 鼠标焦点发生变化时执行。
     * @param view
     * @param b
     */
    @Override
    public void onFocusChange(View view, boolean b) {

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /**
     * cgl 数据加载等待动画
     * @return
     */
    public ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("数据加载中...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        return progressDialog;
    }
}
