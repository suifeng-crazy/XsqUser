package com.example.xsq.Home;

import android.os.Bundle;
import android.view.Window;

import com.example.R;
import com.example.xsq.util.BaseActivity;

/**
 * 推荐购车页面
 */
public class HomeRecommedBuyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_recommed_buy);
    }
}
