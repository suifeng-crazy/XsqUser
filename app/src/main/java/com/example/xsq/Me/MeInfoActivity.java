package com.example.xsq.Me;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.don.library.activity.BindActivityLib;
import com.don.library.activity.LibBaseActivity;
import com.don.library.annotation.Id;
import com.don.library.annotation.OnClick;
import com.example.R;
import com.example.xsq.util.BaseActivity;

public class MeInfoActivity extends BaseActivity {

    RelativeLayout mReChangePhone,mReNickName,mReRealName;
    ImageView mImLeave;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me_info);

        initUI();
    }

    private void initUI() {
        mImLeave = findViewById(R.id.MeInfo_topI);
        mReRealName = findViewById(R.id.myInfo_realNameR);
        mReNickName = findViewById(R.id.myInfo_nickNameR);
        mReChangePhone = findViewById(R.id.myInfo_phoneR);
        mReChangePhone.setOnClickListener(this);
        mReNickName.setOnClickListener(this);
        mReRealName.setOnClickListener(this);
        mImLeave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.MeInfo_topI:
                finish();
                break;
            case R.id.myInfo_realNameR:
                strActivity(MeInfoActivity.this,MyRealNameActivity.class);
                break;
            case R.id.myInfo_phoneR:
                strActivity(MeInfoActivity.this,MyChangePhoneActivity.class);
                break;
            case R.id.myInfo_nickNameR:
                strActivity(MeInfoActivity.this,MyChangeNiceNameActivity.class);
                break;
        }
    }
}
