package com.example.xsq.Me;

/**
 * 设置页面
 */

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.UpdateManager;

public class SetUpActivity extends BaseActivity {
    UpdateManager mUpdateManager;
    TextView mTvOut;
    RelativeLayout mReSetUp;
    ImageView mImOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_up);

        initUI();
    }

    private void initUI() {
        mTvOut = findViewById(R.id.setUp_outT);
        mReSetUp = findViewById(R.id.setUp_editR);
        mImOut = findViewById(R.id.setUp_topI);

        mTvOut.setOnClickListener(this);
        mReSetUp.setOnClickListener(this);
        mImOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.setUp_editR:
               updateApp();// 检查更新app
               break;
           case R.id.setUp_outT:
               NumberUtil.token = "";
               strActivity(SetUpActivity.this,MeMainActivity.class);
               finish();
               break;
           case R.id.setUp_topI:
               finish();
               break;
       }
    }

    // 验证升级
    private void updateApp(){
            boolean flag = false;
            try {
                mUpdateManager=new UpdateManager(this,flag, ConnectionAddress.BASE_CheckEdt);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            mUpdateManager.setProgressDialog(getProgressDialog(this));
            mUpdateManager.checkUpdateInfo();
        }
}
