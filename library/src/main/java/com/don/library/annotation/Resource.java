package com.don.library.annotation;

import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD})
@Inherited
public @interface Resource {
    @ColorRes int color() default -1;

    @DrawableRes int drawable() default -1;

    @StringRes int string() default -1;

    @DimenRes int dimen() default -1;
}
