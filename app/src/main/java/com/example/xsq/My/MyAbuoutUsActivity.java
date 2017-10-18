package com.example.xsq.My;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.ConnectionAddress;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.util.PostParma;

public class MyAbuoutUsActivity extends BaseActivity {

    ImageView mImLeave;
    WebView mWeAboutUs;
    String mStInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_abuout_us);

        mImLeave = findViewById(R.id.MyAboutUs_topI);
        mImLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWeAboutUs = findViewById(R.id.MyAboutUs_Web);
        mWeAboutUs.setWebViewClient(new WebViewClient(){
            //WebViewClient帮助WebView去处理一些页面控制和请求通知，该类还有其他方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url)
            {
            //返回值为true时，控制网页在WebView中打开；为false时，调用系统或第三方浏览器打开。
            //return super.shouldOverrideUrlLoading(view,url);
                view.loadUrl(url);
                return true;
            }
        });
        cachedThreadPool.execute(mRunnable);

    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mStInfo = PostParma.baseGetBaseInfo(ConnectionAddress.BASE_Get_AboutUs,"ABOUT_US");

                mHandler.sendEmptyMessage(STATUS_OK);

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
                    System.out.println(mWeAboutUs);
                    mWeAboutUs.getSettings().setJavaScriptEnabled(true);
                    mWeAboutUs.loadDataWithBaseURL(null, mStInfo, "text/html", "utf-8", null);
//                    mWeAboutUs.setBackgroundColor(0);
//                    mWeAboutUs.getBackground().setAlpha(2);
//                    mWeAboutUs.getSettings().setJavaScriptEnabled(false);
//                    mWeAboutUs.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//                    mWeAboutUs.setVerticalScrollBarEnabled(false);
//                    mWeAboutUs.setHorizontalScrollBarEnabled(false);
//                    mWeAboutUs.getSettings().setUseWideViewPort(true);//设置webview显示屏幕宽度 不能滑动
//                    mWeAboutUs.getSettings().setLoadWithOverviewMode(true);
//                    mWeAboutUs.loadDataWithBaseURL(null, mStInfo, "text/html", "utf-8", null);
                    break;
                case STATUS_ERROR:
                    toastMessageF(NumberUtil.strError);
                    break;
            }
        }
    };
}
