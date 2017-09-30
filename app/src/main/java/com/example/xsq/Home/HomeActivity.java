package com.example.xsq.Home;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.R;
import com.example.xsq.util.BaseActivity;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity {
    LinearLayout recommedBuyServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        initUI();
    }

    public void initUI(){
        recommedBuyServer = findViewById(R.id.home_server_recommedBuy);
        recommedBuyServer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_server_recommedBuy:
                strActivity(HomeActivity.this,HomeRecommedBuyActivity.class);
        }
    }
}
