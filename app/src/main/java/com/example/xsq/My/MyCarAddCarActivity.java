package com.example.xsq.My;

import android.os.Bundle;
import android.view.Window;

import com.example.R;
import com.example.xsq.util.BaseActivity;

/**
 * 添加车辆
 */
public class MyCarAddCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_car_add_car);
    }
}
