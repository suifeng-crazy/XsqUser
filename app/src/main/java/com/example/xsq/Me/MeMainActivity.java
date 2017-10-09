package com.example.xsq.Me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.Ent.User;
import com.example.xsq.Login.MainActivity;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.xsq.util.JsonStringMapUtil.checkUserPhone;

public class MeMainActivity extends BaseActivity {

    URL url  = null;
    User user = new User();
    TextView setUpT,mTvUserName,mTvSumMoney,mTvRedMoney,mTvGodMoney;
    LinearLayout toPayV;
    ImageView mImHeadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me_main);

        initUI();
        initData();
    }

    public void initUI(){
        mTvSumMoney = findViewById(R.id.MeMain_sumMoneyT);
        mTvRedMoney = findViewById(R.id.MeMain_redMoneyT);
        mTvGodMoney = findViewById(R.id.MeMain_godMoneyT);
        mTvUserName = findViewById(R.id.Me_userNameT);
        mImHeadImage = findViewById(R.id.Me_touXiangIM);
        toPayV = findViewById(R.id.MeMain_toPayL);
        setUpT = findViewById(R.id.Me_setUpT);
        setUpT.setOnClickListener(this);
        toPayV.setOnClickListener(this);
        mTvUserName.setOnClickListener(this);
        mImHeadImage.setOnClickListener(this);
    }

    public void initData(){
        // 直接提交token 值， 从返回结果判定是否有效登陆， 无效登陆都显示 0 ，
        if(!NumberUtil.token.equals(""))
            cachedThreadPool.execute(mRunnable);

    }


    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.Me_touXiangIM: // 点击头像位置，跳转到 个人信息页面。
                strActivity(MeMainActivity.this, MeInfoActivity.class);
                finish();
                break;
            case R.id.Me_userNameT:
                // 只有在未登录的状态下才会跳转 去登陆页面,否则跳转到 个人信息页面
                if(NumberUtil.token.equals("")){
                    strActivity(MeMainActivity.this, MainActivity.class);
                    finish();
                }else{
                    strActivity(MeMainActivity.this, MeInfoActivity.class);
                    finish();
                }
                break;
            case R.id.Me_setUpT:
                strActivity(MeMainActivity.this,SetUpActivity.class);
                break;
            case R.id.MeMain_toPayL:
                strActivity(MeMainActivity.this,OrderListActivity.class);
                break;

        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {  // 在这里面处理数据。
            try {
                user =   PostParma.getUserInfo(NumberUtil.token);
                mHandler.sendEmptyMessage(STATUS_CHECK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STATUS_CHECK:
                    if(user==null){
                        // user 为空分两种状态， 一种是网络连接为失败， 一种是 登陆过期， 登陆过期的不能报错
                        toastMessage(MeMainActivity.this,NumberUtil.strError);
                    }else{
                        // 获得数据然后 展示，现在只有 名称。
                        mTvUserName.setText(user.getUserPhone());
                        mTvSumMoney.setText(user.getUserMoney().toString());
                        mTvRedMoney.setText(user.getUserRedMoney().toString());
                        mTvGodMoney.setText(user.getUserGod().toString());

                        // 设置 用户头像展示。 这个需要做优化，
//                        try {
//                             url = new URL(ConnectionAddress.BASE_Adress+user.getUserHeaderImage())
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }
//                        mImHeadImage.setImageURI(url);
                    }

                    break;
            }
        }
    };


}
