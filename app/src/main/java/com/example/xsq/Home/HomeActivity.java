package com.example.xsq.Home;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
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
        updateApp();// 检查更新app

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
