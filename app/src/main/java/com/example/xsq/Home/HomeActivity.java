package com.example.xsq.Home;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;
import com.example.xsq.util.UpdateManager;

/**
 * 主页
 * 测试别再出错了
 */
public class HomeActivity extends BaseActivity {
    LinearLayout recommedBuyServer;
    UpdateManager mUpdateManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        initUI();
        checkFilePort();//  检查 文件服务器端口
        updateApp();    // 检查更新app
        NumberUtil.baseBottomSelectUI = 1;
    }

    public void initUI(){
        recommedBuyServer = findViewById(R.id.home_server_recommedBuy);
        recommedBuyServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_server_recommedBuy:
                strActivity(HomeActivity.this,HomeRecommedBuyActivity.class);
        }
    }

    // 验证升级
    private void updateApp(){
        if(NumberUtil.checkUpdate){
        boolean flag = true;
        try {
            mUpdateManager=new UpdateManager(this,flag, ConnectionAddress.BASE_CheckEdt);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mUpdateManager.setProgressDialog(getProgressDialog(this));
        mUpdateManager.checkUpdateInfo();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            return onDoubleClickBackExit();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查更新端口
     */
    private void checkFilePort() {
        if (isNetworkConnected(this)) {  // 检查网络链接是否正常。  如果没有那么进行提示 。
                cachedThreadPool.execute(mRunnable);  // 这里没有对 mRunnable 进行标示 ， 都是跳转到 mRunnable
        } else {
            hideDialog();
        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                PostParma.getFileAddress(ConnectionAddress.Base_Get_FileAddress,"FILE_SERVER");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}
