package com.example.xsq.My;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

public class MyChangePhoneActivity extends BaseActivity {

    EditText mEdPhone, mEdPhoneCode;
    Button mBtPhoneCode, mBtSubmit;
    String mStPhone,mStPhoneCode;
    Boolean mBoIsGetPhoneCode,mBoHasGetPhoneCode = false,mBoIsTrueChange;
    int mItRun;
    ImageView mImLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_change_phone);

        initUI();
    }


    private void initUI() {
        mImLeave = findViewById(R.id.ChangePhone_topI);
        mEdPhone = findViewById(R.id.ChangePhone_BeforPasE);
        mEdPhoneCode = findViewById(R.id.ChangePhone_PhoneCodeE);
        mBtPhoneCode = findViewById(R.id.ChangePhone_GetPhoneCodeT);
        mBtSubmit = findViewById(R.id.ChangePhone_SubmitT);

        mBtPhoneCode.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);
        mImLeave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ChangePhone_topI:
                finish();
                break;
            case R.id.ChangePhone_GetPhoneCodeT:
                mStPhone = mEdPhone.getText().toString();
                System.out.println("mStPhone:"+mStPhone);
                if (mStPhone.equals("")) {
                    toastMessageF("请输入正确的手机号");
                } else {
                    mItRun = 1;
                    getCodeMeth();
                    cachedThreadPool.execute(mRunnable);
                }
                break;
            case R.id.ChangePhone_SubmitT:
                mStPhone = mEdPhone.getText().toString();
                mStPhoneCode = mEdPhoneCode.getText().toString();
                if (!mBoHasGetPhoneCode){
                    toastMessageF("请先获取验证码");
                }else if(mStPhoneCode.equals("")){
                    toastMessageF("请输入验证码");
                } else{
                    mItRun = 2;
                    cachedThreadPool.execute(mRunnable);
                }
                break;
        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                switch (mItRun) {
                    case 1: // 获取验证码
                        mBoIsGetPhoneCode = PostParma.baseIsTrueFirstParam(ConnectionAddress.BASE_Get_ChangePhoneGetCode, mStPhone, "phe");
                        if (mBoIsGetPhoneCode) {
                            mHandler.sendEmptyMessage(STATUS_OK);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                    case 2: // 提交修改 手机号
                        mBoIsTrueChange = PostParma.baseIsTrueSecondParam(ConnectionAddress.BASE_Get_ChangePhone,"phe", mStPhone ,"code",mStPhoneCode);
                        if (mBoIsTrueChange) {
                            mHandler.sendEmptyMessage(999);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STATUS_OK:
                    mBoHasGetPhoneCode = true;
                    break;
                case STATUS_ERROR:
                    toastMessageF(NumberUtil.strError);
                    break;
                case 999:
                    toastMessageF("修改成功");
                    Intent data =new Intent();//只是回传数据就不用写跳转对象
                    data.putExtra("data",mStPhone);//数据放到data里面去
                    setResult(2,data);
                    NumberUtil.user.setUserPhone(mStPhone);
                    finish();
                    break;
            }
        }
    };

    public void getCodeMeth() {
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                mBtPhoneCode.setText(millisUntilFinished / 1000 + "秒后重发");
                mBtPhoneCode.setClickable(false);
            }

            @Override
            public void onFinish() {
                mBtPhoneCode.setText("重新获取");
                mBtPhoneCode.setClickable(true);
            }
        }.start();
    }
}
