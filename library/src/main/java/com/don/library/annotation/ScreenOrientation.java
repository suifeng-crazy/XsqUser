package com.don.library.annotation;

import android.content.pm.ActivityInfo;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;

@Retention(RUNTIME)
@Target({TYPE})
@Inherited
public @interface ScreenOrientation {
    int value() default ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
}
