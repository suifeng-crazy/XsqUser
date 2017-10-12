package com.example.xsq.Me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.R;
import com.example.xsq.util.BaseActivity;

public class MyChangePhoneActivity extends BaseActivity {

    EditText mEdPhone,mEdPhoneCode;
    Button mBtPhoneCode,mBtSubmit;
    int mItRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_change_phone);

        initUI();
    }


    private void initUI() {
        mEdPhone = findViewById(R.id.ChangePhone_BeforPasE);
        mEdPhoneCode = findViewById(R.id.ChangePhone_PhoneCodeE);
        mBtPhoneCode = findViewById(R.id.ChangePhone_GetPhoneCodeT);
        mBtSubmit = findViewById(R.id.ChangePhone_SubmitT);

        mBtPhoneCode.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ChangePhone_GetPhoneCodeT:

                break;
            case R.id.ChangePhone_SubmitT:
                break;
        }
    }
}
