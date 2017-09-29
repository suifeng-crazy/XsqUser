package com.don.library.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected abstract
    @LayoutRes
    int getContentView();

    protected abstract void bindViews();

    protected abstract void bindListener();

    protected abstract void init();

    protected Activity mActivity;
    protected Fragment mFragment;
    protected Context mContext;
    protected View mView;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mContext = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        mContext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragment = this;
        mView = inflater.inflate(getContentView(), container, false);
        bindViews();
        bindListener();
        init();
        return mView;
    }

    protected View findViewById(int id) {
        if (mView == null) {
            return null;
        }
        return mView.findViewById(id);
    }

    public View getView() {
        return mView;
    }
}
