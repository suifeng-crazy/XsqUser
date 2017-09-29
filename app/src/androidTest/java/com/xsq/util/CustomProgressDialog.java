package com.xsq.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.suifeng.xsq.R;

/**
 * 等待加载类 12.08
 * Created by Administrator on 2016/7/14.
 */
public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context) {
        super(context);
    }
    public CustomProgressDialog(Context context, int theme) {
        super(context,theme);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_dialog);

//  setContentView(android.R.layout.alert_dialog_progress);

    }
    public static CustomProgressDialog show (Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context, R.style.dialog);
        dialog.show();
        return dialog;
    }

}
