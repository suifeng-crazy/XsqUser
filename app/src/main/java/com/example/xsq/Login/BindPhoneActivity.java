package com.example.xsq.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.Home.HomeActivity;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

import static com.example.xsq.util.JsonStringMapUtil.checkUserPhone;


// 第三方注册登录后，绑定手机号 操作。但是现在我直接是当做 注册做的了。失败。。。 bu
// 忘记密码也是跳转到这个页面， ，，
public class BindPhoneActivity extends BaseActivity {
    EditText mEdPhone,mEdCode;
    ImageView imageView;
    Button BindPhone_xybB;
    TextView mTvGetCode;
    //                           进入的方式
    String userPhone,mPhoneCode,math;
    Boolean isGetTrue,isTrueCodeB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bind_phone);
        initUI();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        math = intent.getStringExtra("math");
    }

    public void initUI(){
        mEdPhone = findViewById(R.id.BindPhone_PhoneE);
        mEdCode = findViewById(R.id.BindPhone_codeE);
        imageView = (ImageView) findViewById(R.id.BindPhone_closeIM);
        mTvGetCode = findViewById(R.id.BindPhone_getCodeT);
        BindPhone_xybB = findViewById(R.id.BindPhone_xybB);
        imageView.setOnClickListener(this);
        BindPhone_xybB.setOnClickListener(this);

        mTvGetCode.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BindPhone_closeIM:
                finish();
                break;
            case R.id.BindPhone_xybB:
                // 需提前验证 验证码是否正确
                isTrueCode();
//                strActivity(BindPhoneActivity.this,LoginPasswordActivity.class);
                break;
            case R.id.BindPhone_getCodeT:
                // 和后台进行通信
                getCodeMeth();
                getCode();
                break;
        }
    }

    public  void isTrueCode(){
        mPhoneCode = mEdCode.getText().toString();
        if(mPhoneCode.equals("")){
            toastMessage(BindPhoneActivity.this,"请输入验证码");
        }else{
            // 判断验证码是否正确。
            cachedThreadPool.execute(tRunnable);
        }
    }

    Runnable tRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                isTrueCodeB = PostParma.isTruePhoneCode(ConnectionAddress.Base_IsTrue_PhoneCode,userPhone,mPhoneCode);
            }catch (Exception e){
                e.printStackTrace();
            }
           mHandler.sendEmptyMessage(STATUS_MORE);
            return ;
        }
    };

    public void getCodeMeth(){
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                mTvGetCode.setText(millisUntilFinished/1000+"秒后重发");
                mTvGetCode.setClickable(false);

                ;
            }
            @Override
            public void onFinish() {
                mTvGetCode.setText("重新获取");
                mTvGetCode.setClickable(true);
            }
        }.start();
    }

    public  void getCode() {
        if (isNetworkConnected(this)) {  // 检查网络链接是否正常。  如果没有那么进行提示 。
            userPhone = mEdPhone.getText().toString().trim();
            if (! userPhone.equals("")) {
                if (checkUserPhone(userPhone)) {  // 这里应该是 判断手机号是否正确
                    // 这个 cachedThreadPool 就是new Thread 开启的新的子线程， execute 就是线程的执行。
                    // mRunable 就是执行的  Runnable ， 之后就一样了。
                    cachedThreadPool.execute(mRunnable);  // 这里没有对 mRunnable 进行标示 ， 都是跳转到 mRunnable
                } else {
                    hideDialog();
                    toastMessage(this, "手机号格式不正确");
                }
            } else {
                hideDialog();
                toastMessage(this, "请输入手机号");
            }
            mTvGetCode.setText("重新获取");
            mTvGetCode.setClickable(true);
        } else {
            hideDialog();
        }
        mTvGetCode.setText("重新获取");
        mTvGetCode.setClickable(true);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                switch (STATUS_CHECK) {
                    case STATUS_CHECK:
                        // 判断是因为那种情况进入的这个页面
                        if(math.equals("BindPhone")){
                            isGetTrue = PostParma.getPhoneCode(ConnectionAddress.Base_Get_PhoneCode,userPhone);
                        }else{
                            isGetTrue = PostParma.getPhoneCode(ConnectionAddress.Base_Get_ForgotLoginCode,userPhone);
                        }

                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 都要走这个 mHandler 来对UI ？ 进行修改，或者跳转相应的 activity
            mHandler.sendEmptyMessage(STATUS_CHECK);
            return;
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATUS_CONNECT_TIMEOUT:  // 链接超时， 在 请求的时候已经做了提示了。 所以就不用进行进一步的提示了。
                    hideDialog();
                    break;
                case STATUS_CHECK: {  // 这个其实就是  query.getQueryStatus()
                    if (isGetTrue) {
                        // 验证码 倒计时已经开始了， 这里就不在写开始了。
                    } else  {
                        hideDialog();
                        toastMessage(BindPhoneActivity.this, "获取验证码失败");
                    }
                }
                break;
                case STATUS_MORE:{
                    if(isTrueCodeB){
                        Intent intent = new Intent();
                        intent.setClass(BindPhoneActivity.this,LoginPasswordActivity.class);
                        intent.putExtra("userPhone",userPhone);
                        intent.putExtra("code",mPhoneCode);
                        intent.putExtra("math",math);
                        hideDialog();
                        startActivity(intent);
                        setGo(true);
                    }else{
                        toastMessage(BindPhoneActivity.this,"验证码错误");
                    }
                }
            }
//            longinB.setClickable(true);
        }
    };
}
