package com.example.xsq.My;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

/**
 * 个人中心 - 设置 - 修改登录密码页面
 */
public class MyChangeLoginPasswordActivity extends BaseActivity {

    LinearLayout mLiPhone;
    EditText mEtBeforPas, mEtNewPas, mEtNewAginPas, mEtPhoneCode;
    String sBeforePas, sNewPas, sNewAginPas, sChangeMath = "ChangeByPas";
    int iRun;
    Button mBtSub, mBtChangeByPhone, mBtChangeByPas, mBtGetPhoneCode;
    Boolean isChangeTrue, isGetPhoneCode;
    TextView mTxPhone;
    ImageView mImLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_change_login_password);

        initUI();
    }


    private void initUI() {
        mLiPhone = findViewById(R.id.ChangeLoginPas_PhoneCodeL);
        mImLeave = findViewById(R.id.ChangeLoginPas_topI);
        mBtChangeByPhone = findViewById(R.id.ChangeLoginPas_ChangeByPhone);
        mBtChangeByPas = findViewById(R.id.ChangeLoginPas_ChangeByPas);
        mBtGetPhoneCode = findViewById(R.id.ChangeLoginPas_GetPhoneCodeT);
        mTxPhone = findViewById(R.id.ChangeLoginPas_PhoneE);
        mEtPhoneCode = findViewById(R.id.ChangeLoginPas_PhoneCodeE);
        mEtBeforPas = findViewById(R.id.ChangeLoginPas_BeforPasE);
        mEtNewPas = findViewById(R.id.ChangeLoginPas_NewPasE);
        mEtNewAginPas = findViewById(R.id.ChangeLoginPas_NewAginPasE);
        mBtSub = findViewById(R.id.ChangeLoginPas_SubmitT);

        mLiPhone.setVisibility(View.GONE);
        mTxPhone.setVisibility(View.GONE);

        mBtSub.setOnClickListener(this);
        mBtChangeByPhone.setOnClickListener(this);
        mBtChangeByPas.setOnClickListener(this);
        mBtGetPhoneCode.setOnClickListener(this);
        mImLeave.setOnClickListener(this);

        mTxPhone.setText(NumberUtil.user.getUserPhone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ChangeLoginPas_topI:
                finish();
                break;
            case R.id.ChangeLoginPas_ChangeByPhone:
                sChangeMath = "ChangeByPhone";
                mLiPhone.setVisibility(View.VISIBLE);
                mTxPhone.setVisibility(View.VISIBLE);
                mEtBeforPas.setVisibility(View.GONE);

                mBtChangeByPas.setBackgroundResource(R.drawable.mxml_left_fill_white);
                mBtChangeByPhone.setBackgroundResource(R.drawable.mxml_right_fill_hot);
                mBtChangeByPhone.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtChangeByPas.setTextColor(getResources().getColor(R.color.textblack));
                break;
            case R.id.ChangeLoginPas_GetPhoneCodeT:
                // 开始读秒，
                getCodeMeth();
                iRun = 3;
                cachedThreadPool.execute(mRunnable);
                break;
            case R.id.ChangeLoginPas_ChangeByPas:
                sChangeMath = "ChangeByPas";
                mLiPhone.setVisibility(View.GONE);
                mTxPhone.setVisibility(View.GONE);
                mEtBeforPas.setVisibility(View.VISIBLE);

                mBtChangeByPas.setBackgroundResource(R.drawable.mxml_left_fill_hot);
                mBtChangeByPhone.setBackgroundResource(R.drawable.mxml_right_fill_white);
                mBtChangeByPhone.setTextColor(getResources().getColor(R.color.textblack));
                mBtChangeByPas.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.ChangeLoginPas_SubmitT:  // 提交修改
                sNewPas = mEtNewPas.getText().toString();
                sNewAginPas = mEtNewAginPas.getText().toString();
                if (sChangeMath.equals("ChangeByPas")) {
                    sBeforePas = mEtBeforPas.getText().toString();
                    if (sBeforePas.equals("")) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "请输入原密码");
                    } else if (sNewAginPas.equals("")) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "请输入新密码");
                    } else if (sNewPas.equals("")) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "请输入新密码");
                    } else if (!sNewAginPas.equals(sNewPas)) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "两次输入的密码不一致");
                    } else if(sNewAginPas.length()<6 || sNewAginPas.length() >11){
                        toastMessage(MyChangeLoginPasswordActivity.this, "密码长度需6-11位");
                    } else {
                        iRun = 1;
                        cachedThreadPool.execute(mRunnable);
                    }

                } else if (sChangeMath.equals("ChangeByPhone")) {
                    if (mEtPhoneCode.getText().toString().equals("")) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "请输入验证码");
                    } else if (sNewAginPas.equals("")) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "请输入新密码");
                    } else if (sNewPas.equals("")) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "请输入新密码");
                    } else if (!sNewAginPas.equals(sNewPas)) {
                        toastMessage(MyChangeLoginPasswordActivity.this, "两次输入的密码不一致");
                    } else if(sNewAginPas.length()<6 || sNewAginPas.length() >11){
                        toastMessage(MyChangeLoginPasswordActivity.this, "密码长度需6-11位");
                    } else {
                        iRun = 2;
                        cachedThreadPool.execute(mRunnable);
                    }
                }
                break;
        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                switch (iRun) {
                    case 1:  // 原密码修改登录密码
                        isChangeTrue = PostParma.isChangeLoginPasByBeforPas(ConnectionAddress.Base_Get_ChangeLoginPasByBeforPas, sBeforePas, sNewPas);
                        if (isChangeTrue) {
                            mHandler.sendEmptyMessage(STATUS_OK);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                    case 2: // 手机号修改登录密码
                        isChangeTrue = PostParma.isChangeLoginPasByPhone(ConnectionAddress.Base_Get_ChangeLoginPasByPhone, mEtPhoneCode.getText().toString(), sNewPas);
                        if (isChangeTrue) {
                            mHandler.sendEmptyMessage(STATUS_OK);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                    case 3: // 获取手机验证码
                        isGetPhoneCode = PostParma.isChangeLoginPasGetPhoneCode(ConnectionAddress.Base_Get_ChangeLoginPasGetPhoneCode);
                        if (isGetPhoneCode) {
//                            mHandler.sendEmptyMessage(999);
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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATUS_OK:
                    toastMessage(MyChangeLoginPasswordActivity.this, "修改成功");
                    finish();
                    break;
                case STATUS_ERROR:
                    toastMessage(MyChangeLoginPasswordActivity.this, NumberUtil.strError);
                    break;
                case 999:

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
                mBtGetPhoneCode.setText(millisUntilFinished / 1000 + "秒后重发");
                mBtGetPhoneCode.setClickable(false);
            }

            @Override
            public void onFinish() {
                mBtGetPhoneCode.setText("重新获取");
                mBtGetPhoneCode.setClickable(true);
            }
        }.start();
    }
}
