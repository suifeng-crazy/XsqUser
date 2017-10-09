package com.example.xsq.Me;

/**
 * 设置页面
 */

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.util.BaseActivity;
import com.example.xsq.util.NumberUtil;

public class SetUpActivity extends BaseActivity {

    TextView mTvOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set_up);

        initUI();
    }

    private void initUI() {
        mTvOut = findViewById(R.id.setUp_outT);
        mTvOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.setUp_outT:
               NumberUtil.token = "";
               strActivity(SetUpActivity.this,MeMainActivity.class);
               finish();
               break;
       }
    }
}
