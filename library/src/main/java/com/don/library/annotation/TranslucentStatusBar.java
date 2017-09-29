package com.don.library.annotation;

import android.support.annotation.ColorRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({TYPE})
@Inherited
public @interface TranslucentStatusBar {

    @ColorRes int value() default -1;

    String color() default "";
}
