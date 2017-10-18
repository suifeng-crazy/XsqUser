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
 *  个人中心 - 设置 - 修改支付密码页面
 */
public class MyChangePayPasswordActivity extends BaseActivity {

    LinearLayout mLiPhone ,mLiChangeMath;
    EditText mEtBeforPas, mEtNewPas, mEtNewAginPas, mEtPhoneCode;
    String sBeforePas, sNewPas, sNewAginPas, sChangeMath = "ChangeByPas";
    int iRun,isFirstSetPayPas = 1;
    Button mBtSub, mBtChangeByPhone, mBtChangeByPas, mBtGetPhoneCode;
    Boolean isChangeTrue, isGetPhoneCode,isSetPayPas;
    TextView mTxPhone;
    ImageView mImLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_change_pay_password);

        initUI();
        initData();
    }


    private void initUI() {
        mLiChangeMath = findViewById(R.id.ChangePayPas_ChangeMathL);
        mLiPhone = findViewById(R.id.ChangePayPas_PhoneCodeL);
        mImLeave = findViewById(R.id.ChangePayPas_topI);
        mBtChangeByPhone = findViewById(R.id.ChangePayPas_ChangeByPhone);
        mBtChangeByPas = findViewById(R.id.ChangePayPas_ChangeByPas);
        mBtGetPhoneCode = findViewById(R.id.ChangePayPas_GetPhoneCodeT);
        mTxPhone = findViewById(R.id.ChangePayPas_PhoneE);
        mEtPhoneCode = findViewById(R.id.ChangePayPas_PhoneCodeE);
        mEtBeforPas = findViewById(R.id.ChangePayPas_BeforPasE);
        mEtNewPas = findViewById(R.id.ChangePayPas_NewPasE);
        mEtNewAginPas = findViewById(R.id.ChangePayPas_NewAginPasE);
        mBtSub = findViewById(R.id.ChangePayPas_SubmitT);

        mLiPhone.setVisibility(View.GONE);
        mTxPhone.setVisibility(View.GONE);

        mBtSub.setOnClickListener(this);
        mBtChangeByPhone.setOnClickListener(this);
        mBtChangeByPas.setOnClickListener(this);
        mBtGetPhoneCode.setOnClickListener(this);
        mImLeave.setOnClickListener(this);

        mTxPhone.setText(NumberUtil.user.getUserPhone());
    }
    private void initData() {
        // 判断有没有设置过支付密码， 没有设置过就是设置支付密码，设置过就是修改支付密码
        iRun = 5;
        cachedThreadPool.execute(mRunnable);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ChangePayPas_topI:
                finish();
                break;
            case R.id.ChangePayPas_ChangeByPhone:
                sChangeMath = "ChangeByPhone";
                mLiPhone.setVisibility(View.VISIBLE);
                mTxPhone.setVisibility(View.VISIBLE);
                mEtBeforPas.setVisibility(View.GONE);

                mBtChangeByPas.setBackgroundResource(R.drawable.mxml_left_fill_white);
                mBtChangeByPhone.setBackgroundResource(R.drawable.mxml_right_fill_hot);
                mBtChangeByPhone.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtChangeByPas.setTextColor(getResources().getColor(R.color.textblack));
                break;
            case R.id.ChangePayPas_GetPhoneCodeT:
                // 开始读秒，
                getCodeMeth();
                // 判断是否输入了电话号码

                iRun = 3;
                cachedThreadPool.execute(mRunnable);
                break;
            case R.id.ChangePayPas_ChangeByPas:
                sChangeMath = "ChangeByPas";
                mLiPhone.setVisibility(View.GONE);
                mTxPhone.setVisibility(View.GONE);
                mEtBeforPas.setVisibility(View.VISIBLE);

                mBtChangeByPas.setBackgroundResource(R.drawable.mxml_left_fill_hot);
                mBtChangeByPhone.setBackgroundResource(R.drawable.mxml_right_fill_white);
                mBtChangeByPhone.setTextColor(getResources().getColor(R.color.textblack));
                mBtChangeByPas.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.ChangePayPas_SubmitT:  // 提交修改
                sNewPas = mEtNewPas.getText().toString();
                sNewAginPas = mEtNewAginPas.getText().toString();
                if (sChangeMath.equals("ChangeByPas")) {
                    sBeforePas = mEtBeforPas.getText().toString();
                    if (sNewAginPas.equals("")) {
                        toastMessage(MyChangePayPasswordActivity.this, "请输入新密码");
                    } else if (sNewPas.equals("")) {
                        toastMessage(MyChangePayPasswordActivity.this, "请输入新密码");
                    } else if (!sNewAginPas.equals(sNewPas)) {
                        toastMessage(MyChangePayPasswordActivity.this, "两次输入的密码不一致");
                    } else if(sNewAginPas.length()<6 || sNewAginPas.length() >11){
                        toastMessage(MyChangePayPasswordActivity.this, "密码长度需6-11位");
                    } else if(isFirstSetPayPas == 2){
                        iRun = 4;
                        cachedThreadPool.execute(mRunnable);
                    }else if (sBeforePas.equals("")) {
                        toastMessage(MyChangePayPasswordActivity.this, "请输入原密码");
                    } else  {
                        iRun = 1;
                        cachedThreadPool.execute(mRunnable);
                    }

                } else if (sChangeMath.equals("ChangeByPhone")) {
                    if (mEtPhoneCode.getText().toString().equals("")) {
                        toastMessage(MyChangePayPasswordActivity.this, "请输入验证码");
                    } else if (sNewAginPas.equals("")) {
                        toastMessage(MyChangePayPasswordActivity.this, "请输入新密码");
                    } else if (sNewPas.equals("")) {
                        toastMessage(MyChangePayPasswordActivity.this, "请输入新密码");
                    } else if (!sNewAginPas.equals(sNewPas)) {
                        toastMessage(MyChangePayPasswordActivity.this, "两次输入的密码不一致");
                    } else if(sNewAginPas.length()<6 || sNewAginPas.length() >11){
                        toastMessage(MyChangePayPasswordActivity.this, "密码长度需6-11位");
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
                        isChangeTrue = PostParma.isChangeLoginPasByBeforPas(ConnectionAddress.Base_Get_ChangePayPasByBeforPas, sBeforePas, sNewPas);
                        if (isChangeTrue) {
                            mHandler.sendEmptyMessage(STATUS_OK);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                    case 2: // 手机号修改登录密码
                        isChangeTrue = PostParma.isChangeLoginPasByPhone(ConnectionAddress.Base_Get_ChangePayPasByPhone, mEtPhoneCode.getText().toString(), sNewPas);
                        if (isChangeTrue) {
                            mHandler.sendEmptyMessage(STATUS_OK);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                    case 3: // 获取手机验证码
                        isGetPhoneCode = PostParma.isChangeLoginPasGetPhoneCode(ConnectionAddress.Base_Get_ChangePayPasGetPhoneCode);
                        if (isGetPhoneCode) {
//                            mHandler.sendEmptyMessage(999);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                    case 4:// 第一次设置支付密码
                        isSetPayPas = PostParma.isSetPayPas(ConnectionAddress.Base_Get_SetPayPas,sNewPas);
                        if (isSetPayPas) {
                            mHandler.sendEmptyMessage(STATUS_OK);
                        } else {
                            mHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                        break;
                    case 5:// 判断是否设置过支付密码
                        isFirstSetPayPas = PostParma.isTrueOnlyToken(ConnectionAddress.Base_Get_CheckIsSetPayPas);
                        if (isFirstSetPayPas == 1) {
                            mHandler.sendEmptyMessage(777);
                        } else if(isFirstSetPayPas == 2){ // 没有设置过支付密码，第一次设置
                            mHandler.sendEmptyMessage(888);
                        } else{
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
                    toastMessage(MyChangePayPasswordActivity.this, "修改成功");
                    finish();
                    break;
                case STATUS_ERROR:
                    toastMessage(MyChangePayPasswordActivity.this, NumberUtil.strError);
                    break;
                case 888: // 没有设置过支付密码，第一次设置
                    mLiChangeMath.setVisibility(View.GONE);
                    mLiPhone.setVisibility(View.GONE);
                    mTxPhone.setVisibility(View.GONE);
                    mEtBeforPas.setVisibility(View.GONE);

                    break;
                case 777: // 已经设置过了
                    mLiChangeMath.setVisibility(View.VISIBLE);
                    mEtBeforPas.setVisibility(View.VISIBLE);
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

