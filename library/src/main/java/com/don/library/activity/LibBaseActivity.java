package com.don.library.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.don.library.annotation.FullScreen;
import com.don.library.annotation.ScreenOrientation;
import com.don.library.annotation.TranslucentStatusBar;
import com.don.library.util.DisplayUtil;
import com.don.library.util.EmptyUtil;
import com.don.library.util.ExitUtil;
import com.don.library.util.StatusBarUtil;

public abstract class LibBaseActivity extends AppCompatActivity {

    protected abstract
    @LayoutRes
    int getContentView();

    protected abstract void bindViews();

    protected abstract void bindListener();

    protected abstract void init();

    protected AppCompatActivity mActivity;
    protected Context mContext;
    private View mContentView;
    private View mStatusBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity = LibBaseActivity.this;
        mContext = LibBaseActivity.this;
        mContentView = View.inflate(mContext, getContentView(), null);
        ExitUtil.getInstance().addActivity(this);
        setFullScreen();
        setScreenOrientation();
        setStatusBarTranslucent();
        setContentView(mContentView);
        bindViews();
        bindListener();
        init();
    }

    @Override
    protected void onDestroy() {
        ExitUtil.getInstance().removeActivity(this);
        super.onDestroy();
        mActivity = null;
        mContext = null;
    }

    private void setFullScreen() {
        boolean isFullScreen = getClass().isAnnotationPresent(FullScreen.class);
        if (isFullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void setScreenOrientation() {
        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        if (getClass().isAnnotationPresent(
                ScreenOrientation.class)) {
            ScreenOrientation screenOrientation = getClass()
                    .getAnnotation(ScreenOrientation.class);
            orientation = screenOrientation.value();
        }
        setRequestedOrientation(orientation);
    }

    private void setStatusBarTranslucent() {
        boolean isFullScreen = getClass().isAnnotationPresent(FullScreen.class);
        boolean isTranslucent = getClass().isAnnotationPresent(TranslucentStatusBar.class);
        boolean isArrowTranslucent = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (!isFullScreen && isTranslucent && isArrowTranslucent) {
            StatusBarUtil.initBar(mActivity);
            LinearLayout layout = new LinearLayout(mContext);
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layout.setOrientation(LinearLayout.VERTICAL);
            mStatusBar = new View(mContext);
            layout.addView(mStatusBar, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mStatusBar
                    .getLayoutParams();
            int statusBarHeight = StatusBarUtil.getStatusBarHeight(mContext);
            params.height = statusBarHeight == 0 ? DisplayUtil.dip2px(mContext,
                    25) : statusBarHeight;
            mStatusBar.setLayoutParams(params);
            TranslucentStatusBar translucentStatusBar = LibBaseActivity.this
                    .getClass().getAnnotation(TranslucentStatusBar.class);
            if (translucentStatusBar.value() != -1) {
                mStatusBar.setBackgroundResource(translucentStatusBar.value());
            } else if (!EmptyUtil.isEmpty(translucentStatusBar.color())) {
                mStatusBar.setBackgroundColor(Color
                        .parseColor(translucentStatusBar.color()));
            } else {
                mStatusBar.setBackgroundColor(Color.BLACK);
            }
            View v = View.inflate(mContext, getContentView(), null);
            layout.addView(v, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layout.setBackgroundDrawable(v.getBackground());
            mContentView = layout;
        }
    }

    protected void exit() {
        ExitUtil.getInstance().exit();
    }

    protected void changeStatusBarColor(@ColorRes int color) {
        if (mStatusBar == null) {
            return;
        }
        mStatusBar.setBackgroundResource(color);
    }

    protected void changeStatusBarColor(String color) {
        if (mStatusBar == null) {
            return;
        }
        mStatusBar.setBackgroundColor(Color.parseColor(color));
    }

    protected void changeStatusBarVisibility(int visibility) {
        if (mStatusBar == null) {
            return;
        }
        mStatusBar.setVisibility(visibility);
    }
}
