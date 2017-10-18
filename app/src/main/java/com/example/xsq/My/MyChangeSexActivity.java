package com.example.xsq.My;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

/**
 * 修改 性别
 */
public class MyChangeSexActivity extends BaseActivity {

    Button mBtMan,mBtGirl,mBtSubmit;
    ImageView mImLeave;
    int mInSex = 0;
    Boolean isChangeTrue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_change_sex);
        initUI();
        initData();
    }

    private void initData() {
        if(NumberUtil.user.getUserSex() == null ||NumberUtil.user.getUserSex() .equals("") ){
            mBtMan.setBackgroundResource(R.color.colorWhite);
            mBtGirl.setBackgroundResource(R.color.colorWhite);
            mBtMan.setTextColor(getResources().getColor(R.color.textblack));
            mBtGirl.setTextColor(getResources().getColor(R.color.textblack));
        }else if(NumberUtil.user.getUserSex().equals("男")){
            mBtMan.setBackgroundResource(R.color.colorBlue);
            mBtGirl.setBackgroundResource(R.color.colorWhite);
            mBtMan.setTextColor(getResources().getColor(R.color.colorWhite));
            mBtGirl.setTextColor(getResources().getColor(R.color.textblack));
        }else if(NumberUtil.user.getUserSex().equals("女")){
            mBtMan.setBackgroundResource(R.color.colorWhite);
            mBtGirl.setBackgroundResource(R.color.colorBlue);
            mBtMan.setTextColor(getResources().getColor(R.color.textblack));
            mBtGirl.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void initUI() {
        mImLeave = findViewById(R.id.MyChangeSex_topI);
        mBtMan = findViewById(R.id.ChangeSex_man);
        mBtGirl= findViewById(R.id.ChangeSex_girl);
        mBtSubmit= findViewById(R.id.ChangeSex_submit);

        mBtGirl.setOnClickListener(this);
        mBtMan.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);
        mImLeave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.MyChangeSex_topI:
                finish();
                break;
            case R.id.ChangeSex_man:
                mInSex = 0;
                mBtMan.setBackgroundResource(R.color.colorBlue);
                mBtGirl.setBackgroundResource(R.color.colorWhite);
                mBtMan.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtGirl.setTextColor(getResources().getColor(R.color.textblack));
                break;
            case R.id.ChangeSex_girl:
                mInSex = 1;
                mBtMan.setBackgroundResource(R.color.colorWhite);
                mBtGirl.setBackgroundResource(R.color.colorBlue);
                mBtMan.setTextColor(getResources().getColor(R.color.textblack));
                mBtGirl.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.ChangeSex_submit:
                cachedThreadPool.execute(mRunnable);
                break;
        }
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                isChangeTrue = PostParma.isChangeNickName(ConnectionAddress.Base_Get_ChangeNickName, mInSex+"","sex" );

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
                    toastMessage(MyChangeSexActivity.this,"修改成功");
                    Intent data =new Intent();//只是回传数据就不用写跳转对象
                    String userSex;
                    if(mInSex == 0){
                        userSex ="男";
                    }else{
                        userSex ="女";
                    }
                    data.putExtra("data",userSex);//数据放到data里面去
                    setResult(3,data);//返回data，2为result，data为intent对象
                    NumberUtil.user.setUserSex(userSex);
                    finish();
                    break;
                case STATUS_ERROR:
                    toastMessage(MyChangeSexActivity.this,NumberUtil.strError);
                    break;
            }
        }
    };
}
