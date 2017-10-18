package com.example.xsq.BaseLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.xsq.util.NumberUtil;
import com.example.xsq.Car.CarMainActivity;
import com.example.xsq.Home.HomeActivity;
import com.example.xsq.My.MyMainActivity;
import com.example.xsq.ServerItem.ServierMainActivity;
import com.example.xsq.ShopMainActivity;

public class BaseBottomLayout extends LinearLayout implements View.OnClickListener{

	LinearLayout firstR,secondR,threeR,fourR,fiveR;
	ImageView first,second,three,four,five;
	TextView firstT,secondT,threeT,fourT,fiveT;
	Intent intent;
	int beforeActivity = 0;
	public BaseBottomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.base_bottom, this);
		firstR = (LinearLayout) findViewById(R.id.baseBottom_HomeR);
		secondR = (LinearLayout) findViewById(R.id.baseBottom_secondR);
		threeR = (LinearLayout) findViewById(R.id.baseBottom_CarR);
		fourR = (LinearLayout) findViewById(R.id.baseBottom_ServerR);
		fiveR = (LinearLayout) findViewById(R.id.baseBottom_MeR);

		first = (ImageView) findViewById(R.id.first_bottom_firstImage);
		second = (ImageView) findViewById(R.id.first_bottom_secondImage);
		three = (ImageView) findViewById(R.id.first_bottom_threeImage);
		four = (ImageView) findViewById(R.id.first_bottom_fourImage);
		five = (ImageView) findViewById(R.id.first_bottom_fiveImage);

		firstT = (TextView) findViewById(R.id.first_bottom_firstT);
		secondT = (TextView) findViewById(R.id.first_bottom_secondT);
		threeT = (TextView) findViewById(R.id.first_bottom_threeT);
		fourT = (TextView) findViewById(R.id.first_bottom_fourT);
		fiveT = (TextView) findViewById(R.id.first_bottom_fiveT);
		
		firstR.setOnClickListener(this);
		secondR.setOnClickListener(this);
		threeR.setOnClickListener(this);
		fourR.setOnClickListener(this);
		fiveR.setOnClickListener(this);

		baseBottonChangeUI(NumberUtil.baseBottomSelectUI);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		beforeActivity =NumberUtil.baseBottomSelectUI;
		switch (v.getId()){
			case R.id.baseBottom_HomeR:
		 		first.setImageResource(R.drawable.firstpage_select);
		 		firstT.setTextColor(getResources().getColor(R.color.colorBlue));
				NumberUtil.baseBottomSelectUI = 1;
				intent= new Intent(getContext(), HomeActivity.class);
		 		break;
		 	case R.id.baseBottom_secondR:
		 		secondT.setTextColor(getResources().getColor(R.color.colorBlue));
				second.setImageResource(R.drawable.first_first_selected);
				NumberUtil.baseBottomSelectUI = 2;
				intent = new Intent(getContext(), ShopMainActivity.class);
		 		break;
		 	case R.id.baseBottom_CarR:
		 		three.setImageResource(R.drawable.selectcar_select);
		 		threeT.setTextColor(getResources().getColor(R.color.colorBlue));
				NumberUtil.baseBottomSelectUI = 3;
		 		 intent = new Intent(getContext(), CarMainActivity.class);
		 		break;
		 	case R.id.baseBottom_ServerR:
		 		four.setImageResource(R.drawable.server_select);
		 		fourT.setTextColor(getResources().getColor(R.color.colorBlue));
				NumberUtil.baseBottomSelectUI = 4;
		 		 intent = new Intent(getContext(), ServierMainActivity.class);

		 		break;
			case R.id.baseBottom_MeR:
		 		five.setImageResource(R.drawable.my_select);
		 		fiveT.setTextColor(getResources().getColor(R.color.colorBlue));
				NumberUtil.baseBottomSelectUI = 5;
				intent = new Intent(getContext(), MyMainActivity.class);
		 		break;
		 }
		if(beforeActivity < NumberUtil.baseBottomSelectUI){
			getContext().startActivity(intent);
			((Activity) getContext()).overridePendingTransition(R.anim.back_out_one,R.anim.back_in_one);
			((Activity)getContext()).finish();
		}else if(beforeActivity == NumberUtil.baseBottomSelectUI){
			// 不执行任何操作。
		}else {
			getContext().startActivity(intent);
			((Activity) getContext()).overridePendingTransition(R.anim.zoom_in_one,R.anim.zoom_out_one);
//			Toast.makeText(getContext(),"xxxxx",Toast.LENGTH_SHORT).show();
			((Activity)getContext()).finish();
		}
	}

	public void baseBottonChangeUI(int i){
		switch (i){
			case 1:
				first.setImageResource(R.drawable.firstpage_select);
				firstT.setTextColor(getResources().getColor(R.color.colorBlue));
				second.setImageResource(R.drawable.first_first_no_selected);
				secondT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				three.setImageResource(R.drawable.selectcar_noselect);
				threeT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				four.setImageResource(R.drawable.server_noselect);
				fourT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				five.setImageResource(R.drawable.my_noselect);
				fiveT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				break;
			case 2:
				first.setImageResource(R.drawable.firstpage_noselect);
				firstT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				second.setImageResource(R.drawable.first_first_no_selected);
				secondT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				three.setImageResource(R.drawable.selectcar_noselect);
				threeT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				four.setImageResource(R.drawable.server_noselect);
				fourT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				five.setImageResource(R.drawable.my_noselect);
				fiveT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				break;
			case 3:
				first.setImageResource(R.drawable.firstpage_noselect);
				firstT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				second.setImageResource(R.drawable.first_first_no_selected);
				secondT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				three.setImageResource(R.drawable.selectcar_select);
				threeT.setTextColor(getResources().getColor(R.color.colorBlue));
				four.setImageResource(R.drawable.server_noselect);
				fourT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				five.setImageResource(R.drawable.my_noselect);
				fiveT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				break;
			case 4:
				first.setImageResource(R.drawable.firstpage_noselect);
				firstT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				second.setImageResource(R.drawable.first_first_no_selected);
				secondT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				three.setImageResource(R.drawable.selectcar_noselect);
				threeT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				four.setImageResource(R.drawable.server_select);
				fourT.setTextColor(getResources().getColor(R.color.colorBlue));
				five.setImageResource(R.drawable.my_noselect);
				fiveT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				break;
			case 5:
				first.setImageResource(R.drawable.firstpage_noselect);
				firstT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				second.setImageResource(R.drawable.first_first_no_selected);
				secondT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				three.setImageResource(R.drawable.selectcar_noselect);
				threeT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				four.setImageResource(R.drawable.server_noselect);
				fourT.setTextColor(getResources().getColor(R.color.text_whitesmoke));
				five.setImageResource(R.drawable.my_select);
				fiveT.setTextColor(getResources().getColor(R.color.colorBlue));
				break;
		}
	}
	
}
