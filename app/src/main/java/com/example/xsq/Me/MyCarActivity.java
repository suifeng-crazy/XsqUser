package com.example.xsq.Me;

import android.os.Bundle;
import android.view.Window;

import com.example.R;
import com.example.xsq.util.BaseActivity;

/**
 * 我的车
 */
public class MyCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_car);
    }
}
