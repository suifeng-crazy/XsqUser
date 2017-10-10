package com.example.xsq.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.Home.HomeActivity;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

import static com.example.xsq.util.JsonStringMapUtil.checkUserPhone;

// 直接在 git 上修改。看能否下载下来。
public class MainActivity extends BaseActivity {
    //  longinMath 是登录方法， 1代表用户名密码登录， 2为 短信验证码登录
    // i 代表 执行哪个 runable ， 做区分用即可。
    //  getCodeMath 代表 是因为 登录获取验证码  0，还是注册获取验证码  1。
    int i = 0, resStatus = 3, longinMath = 1, getCodeMath = 0;
    // 获取验证码是否成功      注册验证码
    public Boolean isGetTrue, isGetRegTrue;
    public Button mBtLogin;
    public EditText userNameE, passwordE, login_phoneCodeI;
    public String userName, password, phoneCode;
    public TextView getCodeT, login_loginT, registerT, chang_longinMathT, login_getCodeT, login_verLine,
            login_forgetPasswrod;
    public LinearLayout login_passwordL, login_phoneCodeL;
    public ImageView Login_colseI, mIQQ, mIWX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        mBtLogin.setClickable(true);
    }

    public void initUI() {
        login_phoneCodeI = findViewById(R.id.login_phoneCodeI);
        Login_colseI = findViewById(R.id.Login_colseI);
        login_forgetPasswrod = findViewById(R.id.login_forgetPasswrod);
        mBtLogin = (Button) findViewById(R.id.login_loginB);
        userNameE = (EditText) findViewById(R.id.login_userNameI);
        passwordE = (EditText) findViewById(R.id.login_passwordI);
        getCodeT = findViewById(R.id.login_getCodeT);
        registerT = findViewById(R.id.registerT);
        login_loginT = findViewById(R.id.login_loginT);
        chang_longinMathT = findViewById(R.id.login_changeLoginMath);
        login_passwordL = findViewById(R.id.login_passwordL);
        login_phoneCodeL = findViewById(R.id.login_phoneCodeL);
        login_getCodeT = findViewById(R.id.login_getCodeT);
        login_verLine = findViewById(R.id.login_verLine);
        mIQQ = findViewById(R.id.Login_QQI);
        mIWX = findViewById(R.id.Login_WXI);

        login_forgetPasswrod.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);
        getCodeT.setOnClickListener(this);
        login_loginT.setOnClickListener(this);
        registerT.setOnClickListener(this);
        chang_longinMathT.setOnClickListener(this);
        Login_colseI.setOnClickListener(this);
        mIQQ.setOnClickListener(this);
        mIWX.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login_QQI:
            case R.id.Login_WXI: // 微信、QQ 登陆后绑定注册
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BindPhoneActivity.class);
                intent.putExtra("math", "BindPhone");
                startActivity(intent);
                break;
            case R.id.Login_colseI:
                finish();
                break;
            case R.id.login_forgetPasswrod:  // 忘记密码，
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, BindPhoneActivity.class);
                intent2.putExtra("math", "forgetPassword");
                startActivity(intent2);
                break;
            case R.id.login_loginB:
                if (mBtLogin.getText().toString().equals(getResources().getString(R.string.login))) {
                    if (longinMath == 1) {
                        login();
                    } else {
                        loginByPhone();
                    }

                } else if (mBtLogin.getText().toString().equals(getResources().getString(R.string.xyb))) {
                    // 意味着是注册功能，跳转到注册输入密码页面
                    // 先获取 验证码， 然后点击下一步直接进入  输入密码页面。
                    regValCode();
//                    strActivity(MainActivity.this,LoginPasswordActivity.class);
                }
                break;
            case R.id.login_getCodeT: // 这里判断是 登录还是注册
                if (getCodeMath == 1) {
                    getCodeMeth();  // 读秒
                    getCode(2); // 获取短信验证码 注册用短信验证码
                } else {
                    getCodeMeth();  // 读秒
                    getCode(1); // 获取短信验证码 ,登陆用短信验证码
                }
                break;
            case R.id.login_changeLoginMath:
                if (login_passwordL.getVisibility() == View.VISIBLE) {
                    i = 0;
                    login_passwordL.setVisibility(View.INVISIBLE);
                    login_phoneCodeL.setVisibility(View.VISIBLE);
                    login_getCodeT.setVisibility(View.VISIBLE);
                    login_verLine.setVisibility(View.VISIBLE);
                    longinMath = 2;
                    chang_longinMathT.setText(getResources().getString(R.string.login_zhmmdl));
                } else {
                    i = 1;
                    login_passwordL.setVisibility(View.VISIBLE);
                    login_phoneCodeL.setVisibility(View.INVISIBLE);
                    login_getCodeT.setVisibility(View.INVISIBLE);
                    login_verLine.setVisibility(View.INVISIBLE);
                    longinMath = 1;
                    chang_longinMathT.setText(getResources().getString(R.string.chang_longinMath));
                }
                break;
            case R.id.registerT: // 点击注册事件
                login_forgetPasswrod.setVisibility(View.INVISIBLE);
                getCodeMath = 1;
                registerT.setBackgroundResource(R.drawable.base_text_bottom_hx_blue);
                registerT.setTextColor(getResources().getColor(R.color.colorBlue));
                login_loginT.setBackgroundResource(R.drawable.base_text_bottom_hx_null);
                login_loginT.setTextColor(getResources().getColor(R.color.colorblack));
                chang_longinMathT.setVisibility(View.INVISIBLE);
                login_passwordL.setVisibility(View.INVISIBLE);
                login_phoneCodeL.setVisibility(View.VISIBLE);
                login_getCodeT.setVisibility(View.VISIBLE);
                login_verLine.setVisibility(View.VISIBLE);
                mBtLogin.setText(getResources().getString(R.string.xyb));
//                mBtLogin.setV
                break;
            case R.id.login_loginT:
                getCodeMath = 0;
                longinMath = 1;
                login_forgetPasswrod.setVisibility(View.VISIBLE);
                registerT.setBackgroundResource(R.drawable.base_text_bottom_hx_null);
                login_loginT.setBackgroundResource(R.drawable.base_text_bottom_hx_blue);
                registerT.setTextColor(getResources().getColor(R.color.colorblack));
                login_loginT.setTextColor(getResources().getColor(R.color.colorBlue));
                chang_longinMathT.setVisibility(View.VISIBLE);
                login_passwordL.setVisibility(View.VISIBLE);
                login_phoneCodeL.setVisibility(View.INVISIBLE);
                login_getCodeT.setVisibility(View.INVISIBLE);
                login_verLine.setVisibility(View.INVISIBLE);
                mBtLogin.setText(getResources().getString(R.string.login));
                break;
        }
    }

    // 验证 注册验证码是否正确
    private void regValCode() {
        if (login_phoneCodeI.getText().toString().equals("")) {
            toastMessage(MainActivity.this, "请输入验证码");
        } else {
            // 判断验证码是否正确。
            i = 4;
            cachedThreadPool.execute(mRunnable);
        }
    }

    public void login2() {
        strActivity(MainActivity.this, HomeActivity.class, true, false);
        return;
    }

    //  手机短信登录方法执行
    public void loginByPhone() {
        if (isNetworkConnected(this)) {  // 检查网络链接是否正常。  如果没有那么进行提示 。
            userName = userNameE.getText().toString().trim();
            phoneCode = login_phoneCodeI.getText().toString().trim();
            if (!userName.equals("") && !phoneCode.equals("")) {
                longinMath = 2;
                i = 0;
                cachedThreadPool.execute(mRunnable);  // 这里没有对 mRunnable 进行标示 ， 都是跳转到 mRunnable
            } else {
                hideDialog();
                toastMessage(this, "请输入用户名或验证码。");
            }
            mBtLogin.setClickable(true);
        } else {
            hideDialog();
        }
        mBtLogin.setClickable(true);
    }


    //  账号密码登录方法执行
    public void login() {

        if (isNetworkConnected(this)) {  // 检查网络链接是否正常。  如果没有那么进行提示 。
            userName = userNameE.getText().toString().trim();
            password = passwordE.getText().toString().trim();
            if (!userName.equals("") && !password.equals("")) {
                if (password.length() >= 6) {
                    // 这个 cachedThreadPool 就是new Thread 开启的新的子线程， execute 就是线程的执行。
                    // mRunable 就是执行的  Runnable ， 之后就一样了。
                    i = 0;
                    cachedThreadPool.execute(mRunnable);  // 这里没有对 mRunnable 进行标示 ， 都是跳转到 mRunnable
                } else {
                    hideDialog();
                    toastMessage(this, "密码长度不能小于6位。");
                }
            } else {
                hideDialog();
                toastMessage(this, "请输入用户名或密码。");
            }
            mBtLogin.setClickable(true);
        } else {
            hideDialog();
        }
        mBtLogin.setClickable(true);
    }

    public void initData() {
    }


    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (i == 0) {
                    if (longinMath == 1) {
                        resStatus = PostParma.getToken("_a=" + userName, "_p=" + password);
                    } else {
                        resStatus = PostParma.getToken("_a=" + userName, "_c=" + login_phoneCodeI.getText().toString());
                    }
                    mHandler.sendEmptyMessage(STATUS_CHECK);
                } else if (i == 1) {
                    // 获取短信验证码，登陆用
                    isGetTrue = PostParma.getPhoneCode(ConnectionAddress.Base_Get_PhoneLoginCode, userName);
                    mHandler.sendEmptyMessage(888);
                } else if (i == 2) {  // 获取短信验证码，注册用
                    isGetRegTrue = PostParma.getPhoneCode(ConnectionAddress.Base_Get_PhoneCode, userName);
                    mHandler.sendEmptyMessage(999);
                } else if (i == 4) {
                    isGetRegTrue = PostParma.isTruePhoneCode(ConnectionAddress.Base_IsTrue_PhoneCode, userName, login_phoneCodeI.getText().toString());
                    mHandler.sendEmptyMessage(STATUS_MORE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 888:
                    if (!isGetTrue) {
                        toastMessage(MainActivity.this, NumberUtil.strError);
                    }
                    break;
                case 999:
                    if (!isGetRegTrue) {
                        toastMessage(MainActivity.this, NumberUtil.strError);
                    }
                    break;
                case STATUS_CONNECT_TIMEOUT:  // 链接超时， 在 请求的时候已经做了提示了。 所以就不用进行进一步的提示了。
                    hideDialog();
                    break;
                case STATUS_CHECK: {  // 这个其实就是  query.getQueryStatus()
                    if (resStatus == 1) {
                        // 下面这是保存到自己的数据库中的。
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("userName", userName);
                        editor.putString("loginPassword", password);
                        editor.commit();
                        hideDialog();
                        NumberUtil.baseBottomSelectUI = 1;
                        strActivity(MainActivity.this, HomeActivity.class, true, false);
                        setGo(true);
                    } else if (resStatus == 2) {
                        hideDialog();
                        toastMessage(MainActivity.this, NumberUtil.strError);
                    }
                }
                break;
                case STATUS_MORE: {
                    if (isGetRegTrue) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, LoginPasswordActivity.class);
                        intent.putExtra("userPhone", userName);
                        intent.putExtra("code", login_phoneCodeI.getText().toString());
                        intent.putExtra("math","BindPhone");
                        hideDialog();
                        startActivity(intent);
                        setGo(true);
                    } else {
                        toastMessage(MainActivity.this, "验证码错误");
                    }
                }
            }
            mBtLogin.setClickable(true);
        }
    };


    public void getCodeMeth() {
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                getCodeT.setText(millisUntilFinished / 1000 + "秒后重发");
                getCodeT.setClickable(false);
            }

            @Override
            public void onFinish() {
                getCodeT.setText("重新获取");
                getCodeT.setClickable(true);
            }
        }.start();
    }


    // 获取短信验证码
    public void getCode(int j) {
        if (isNetworkConnected(this)) {  // 检查网络链接是否正常。  如果没有那么进行提示 。
            userName = userNameE.getText().toString().trim();
            if (!userName.equals("")) {
                if (checkUserPhone(userName)) {  // 这里应该是 判断手机号是否正确
                    // 这个 cachedThreadPool 就是new Thread 开启的新的子线程， execute 就是线程的执行。
                    // mRunable 就是执行的  Runnable ， 之后就一样了。
                    i = j;
                    cachedThreadPool.execute(mRunnable);  // 这里没有对 mRunnable 进行标示 ， 都是跳转到 mRunnable
                } else {
                    hideDialog();
                    toastMessage(this, "手机号格式不正确");
                }
            } else {
                hideDialog();
                toastMessage(this, "请输入手机号");
            }
            getCodeT.setText("重新获取");
            getCodeT.setClickable(true);
        } else {
            hideDialog();
        }
        getCodeT.setText("重新获取");
        getCodeT.setClickable(true);
    }
}
