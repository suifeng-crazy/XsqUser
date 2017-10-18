package com.example.xsq.My;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.Ent.User;
import com.example.xsq.Login.MainActivity;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyMainActivity extends BaseActivity {

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
        mImHeadImage = findViewById(R.id.Me_headerIm);
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
            case R.id.Me_headerIm: // 点击头像位置，跳转到 个人信息页面。
                if(NumberUtil.token.equals("")){
                    strActivity(MyMainActivity.this, MainActivity.class);
                }else{
                    Intent intent = new Intent(MyMainActivity.this, MyInfoActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.Me_userNameT:
                // 只有在未登录的状态下才会跳转 去登陆页面,否则跳转到 个人信息页面
                if(NumberUtil.token.equals("")){
                    strActivity(MyMainActivity.this, MainActivity.class);
                }else{
                    Intent intent = new Intent(MyMainActivity.this, MyInfoActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.Me_setUpT:
                if(NumberUtil.token.equals("")){
                    strActivity(MyMainActivity.this, MainActivity.class);
                }else{
                    strActivity(MyMainActivity.this,SetUpActivity.class);
                }
                break;
            case R.id.MeMain_toPayL:
                if(NumberUtil.token.equals("")){
                    strActivity(MyMainActivity.this, MainActivity.class);
                }else{
                    strActivity(MyMainActivity.this,OrderListActivity.class);
                }
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
                        toastMessage(MyMainActivity.this,NumberUtil.strError);
                    }else{
                        // 获得数据然后 展示，现在只有 名称。
                        mTvUserName.setText(user.getUserPhone());
                        mTvSumMoney.setText(user.getUserMoney().toString());
                        mTvRedMoney.setText(user.getUserRedMoney().toString());
                        mTvGodMoney.setText(user.getUserGod().toString());
                        NumberUtil.user = user;

                      String   imageURL = user.getUserHeaderImage();
                        Thread t = new LoadPicThread();
                        t.start();

                        // 设置 用户头像展示。 这个需要做优化，
//                        URI mServiceUrl = null;
//                        try {
//                             url = new URL(ConnectionAddress.BASE_Adress+user.getUserHeaderImage());
//
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }
//                        mImHeadImage.setImageURI(url);
                    }

                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==1)//通过请求码(去SActivity)和回传码（回传数据到第一个页面）判断回传的页面
        {
            Boolean content=data.getBooleanExtra("data",false);//字符串content得到data数据
            System.out.println("content:"+content);
           if(content){
               mImHeadImage.setImageBitmap(NumberUtil.UserHeaderImageBitmap);
           }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            return onDoubleClickBackExit();
        }
        return super.onKeyDown(keyCode, event);
    }
    private Handler pic_hdl;

    class LoadPicThread extends Thread {
        @Override
        public void run() {
            String imageURL = user.getUserHeaderImage().toString();
            System.out.println("imageURL:"+user.getUserHeaderImage().toString());
            if(("").equals(imageURL) || imageURL == null){
                return ;
            }
            Bitmap img = getUrlImage(imageURL);
            Message msg = pic_hdl.obtainMessage();
            msg.what = 0;
            msg.obj = img;
            pic_hdl.sendMessage(msg);
        }
    }

    class PicHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //String s = (String)msg.obj;
            //ptv.setText(s);
            Bitmap myimg = (Bitmap) msg.obj;
            mImHeadImage.setImageBitmap(myimg);
        }
    }

    //加载图片
    public Bitmap getUrlImage(String url) {
        Bitmap img = null;
        try {
            URL picurl = new URL(url);
            // 获得连接  �������
            HttpURLConnection conn = (HttpURLConnection) picurl.openConnection();
            conn.setConnectTimeout(60000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存 ��
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流 ������
            img = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }
}
