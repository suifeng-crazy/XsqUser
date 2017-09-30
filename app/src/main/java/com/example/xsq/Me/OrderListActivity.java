package com.example.xsq.Me;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.util.BaseActivity;

/**
 * 订单列表
 */
public class OrderListActivity extends BaseActivity {

    TextView allOrderT,toPayT,toAssessT,inServerT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_list);

        initUI();
    }
    public void initUI(){
        allOrderT = findViewById(R.id.OrderList_allOrderT);
        toPayT = findViewById(R.id.OrderList_toPayT);
        toAssessT = findViewById(R.id.OrderList_toAssess);
        inServerT = findViewById(R.id.OrderList_inServerT);
        allOrderT.setOnClickListener(this);
        toPayT.setOnClickListener(this);
        toAssessT.setOnClickListener(this);
        inServerT.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.OrderList_allOrderT:
                allOrderT.setTextAppearance(this, R.style.orderList_top_text_select);
                toPayT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                toAssessT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                inServerT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                break;
            case R.id.OrderList_toPayT:
                allOrderT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                toPayT.setTextAppearance(this, R.style.orderList_top_text_select);
                toAssessT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                inServerT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                break;
            case R.id.OrderList_toAssess:
                allOrderT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                toPayT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                toAssessT.setTextAppearance(this, R.style.orderList_top_text_select);
                inServerT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                break;
            case R.id.OrderList_inServerT:
                allOrderT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                toPayT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                toAssessT.setTextAppearance(this, R.style.orderList_top_text_noselect);
                inServerT.setTextAppearance(this, R.style.orderList_top_text_select);
                break;
        }
    }
}
