package com.don.library.fragment;

import com.don.library.util.BindUtil;

public abstract class BindFragment extends BaseFragment {

    @Override
    protected void bindViews() {
        BindUtil.bind(this);
    }
}
