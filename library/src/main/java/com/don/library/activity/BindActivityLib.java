package com.don.library.activity;

import com.don.library.util.BindUtil;

public abstract class BindActivityLib extends LibBaseActivity {

    @Override
    protected void bindViews() {
        BindUtil.bind(this);
    }
}
