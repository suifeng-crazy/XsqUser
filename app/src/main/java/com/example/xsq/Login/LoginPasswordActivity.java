package com.example.xsq.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.R;
import com.example.xsq.Home.HomeActivity;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.PostParma;

/**
 * 输入密码页面 ，
 */
public class LoginPasswordActivity extends BaseActivity {

    Boolean isRegSuccess;
    EditText mEtpasswordFirst, mEtpasswordSecond;
    String mPasswrodF, mPasswrodS, userPhone, code;
    ImageView imageView;
    Button AgainPassword_OKB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_password);

        initUI();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        userPhone = intent.getStringExtra("userPhone");
        code = intent.getStringExtra("code");
    }

    public void initUI() {
        mEtpasswordFirst = findViewById(R.id.loginPassword_passwordI);
        mEtpasswordSecond = findViewById(R.id.loginPassword_passwordAgainI);
        AgainPassword_OKB = findViewById(R.id.AgainPassword_OKB);
        imageView = (ImageView) findViewById(R.id.AgainPassword_closeIm);
        imageView.setOnClickListener(this);
        AgainPassword_OKB.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AgainPassword_closeIm:
                finish();
                break;
            case R.id.AgainPassword_OKB:
                checkReg();

                break;
        }
    }


    public void checkReg() {
        mPasswrodF = mEtpasswordFirst.getText().toString();
        mPasswrodS = mEtpasswordSecond.getText().toString();

        if (mPasswrodS.equals(mPasswrodF)) {
            // 验证 密码格式是否正确， 现在缺少
            cachedThreadPool.execute(mRunnable);
            // 将密码 、 手机号一起上传，进行注册
        } else {
            toastMessage(LoginPasswordActivity.this, "两次输入的密码不一致");
        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                // 这里是一个常量 ， 可以在跳转 mRunnable 之前设置一遍， 这样跳转过来之后就知道应该走哪一个 case 了。
                // 在这里的case 里面进行网络链接， 访问服务器， 然后获取数据，
                switch (STATUS_CHECK) {
                    case STATUS_CHECK:
                        // 判断登陆是否成功？？ 这个里面有 post 、get 方式链接服务器， 并且得到返回数据。
                        // 往里面放 map 的数据类型， 然后在方法里面将数据类型组装起来。
                        isRegSuccess = PostParma.regUser(userPhone, mPasswrodF, code);
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
                case STATUS_CHECK:
                    if (isRegSuccess) {
                        strActivity(LoginPasswordActivity.this, HomeActivity.class);
                        finish();
                    }
                    break;
            }
        }
    };
}
