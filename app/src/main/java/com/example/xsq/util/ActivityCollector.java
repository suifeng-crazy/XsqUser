package com.example.xsq.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity 管理类， 在程序里面管理Activity 回收
 */
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();
	
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	public static void removeActivity(Activity activity){
		activities.remove(activity);
	}
	public static void finishAll( ){
		for(Activity activity:activities){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
}
