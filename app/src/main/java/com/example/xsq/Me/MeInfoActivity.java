package com.example.xsq.Me;

import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.don.library.activity.BindActivityLib;
import com.don.library.activity.LibBaseActivity;
import com.don.library.annotation.Id;
import com.don.library.annotation.OnClick;
import com.example.R;
import com.example.xsq.util.BaseActivity;

public class MeInfoActivity extends BindActivityLib implements View.OnClickListener {

    @Id(R.id.MyInfo_phoneT)@OnClick
    TextView mTvPhone;

    @Override
    protected int getContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_me_info;
    }

    @Override
    protected void bindListener() {
        mTvPhone.setOnClickListener(this);
    }

    @Override
    protected void init() {
        mTvPhone.setText("SSSSSSSSSSSSSSSS");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.MyInfo_phoneT:
                mTvPhone.setText("zzzzzzzzzzzzz");
                break;
        }
    }
}
