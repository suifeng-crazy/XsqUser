package com.don.library.annotation;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target({FIELD,
        METHOD})
@Inherited
public @interface OnClick {
    @IdRes int value() default -1;

    @LayoutRes int layoutId() default -1;

    String methodName() default "";
}
