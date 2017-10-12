package com.example.xsq.Me;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

public class MyRealNameActivity extends BaseActivity {
    Button mBtCancel,mBtSave;
    EditText mEtNickeName;
    SpannableString s;
    String sNickeName;
    Boolean isChangeTrue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_real_name);

        initUI();
        initData();
    }

    private void initData() {
        if(NumberUtil.user.getUserRealName() == null  ){
            s = new SpannableString("请设置昵称");//这里输入自己想要的提示文字
        }else{
            s = new SpannableString(NumberUtil.user.getUserRealName());
        }

        mEtNickeName.setHint(s);
    }

    private void initUI() {
        mBtCancel = findViewById(R.id.ChangeRealName_cancel);
        mBtSave= findViewById(R.id.ChangeRealName_save);
        mEtNickeName= findViewById(R.id.ChangeRealName_newRealNameE);

        mBtCancel.setOnClickListener(this);
        mBtSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ChangeRealName_cancel:
                finish();
                break;
            case R.id.ChangeRealName_save:
                sNickeName = mEtNickeName.getText().toString();
                if(sNickeName.equals("")){
                    finish();
                }else{
                    cachedThreadPool.execute(mRunnable);
                }
                break;
        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                isChangeTrue = PostParma.isChangeNickName(ConnectionAddress.Base_Get_ChangeNickName, sNickeName,"nickName" );

                if (isChangeTrue) {
                    mHandler.sendEmptyMessage(STATUS_OK);
                } else {
                    mHandler.sendEmptyMessage(STATUS_ERROR);
                }
            } catch (Exception e) {
                mHandler.sendEmptyMessage(STATUS_ERROR);
                e.printStackTrace();
            }
        }
    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STATUS_OK:
                    toastMessage(MyRealNameActivity.this,"修改成功");
                    NumberUtil.user.setUserRealName(sNickeName);
                    finish();
                    break;
                case STATUS_ERROR:
                    toastMessage(MyRealNameActivity.this,NumberUtil.strError);
                    break;
            }
        }
    };
}
