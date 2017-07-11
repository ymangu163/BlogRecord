package com.common.utils;

import android.util.Log;

public class DebugLog {
	
	public static boolean isDubug = true;
	
	public static void enableDebug(boolean isEnable){
		isDubug = isEnable;
	}
	
	public static void d(String tag, String msg){
		if(isDubug){
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg){
		if(isDubug){
			Log.e(tag, msg);
		}
	}
	
	public static void i(String tag, String msg){
		if(isDubug){
			Log.i(tag, msg);
		}
	}
	
	public static void v(String tag, String msg){
		if(isDubug){
			Log.v(tag, msg);
		}
	}
	
	public static void w(String tag, String msg){
		if(isDubug){
			Log.w(tag, msg);
		}
	}
}
