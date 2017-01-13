package com.xdk.simimmo;

import android.content.Context;
import android.widget.TextView;

public class Utils {

	public static TextView createTableTextView(Context context,String text){
        return createTableCommonTextView(context,text,R.style.frag1Col);
	}
	
	public static TextView createTableHeaderTextView(Context context,String text){
        return createTableCommonTextView(context,text,R.style.frag1HeaderCol);
	}
	
	public static TextView createTableCommonTextView(Context context,String text,int style){
		TextView tv = new TextView(context,null,style);
        tv.setBackgroundResource(R.drawable.tableborder);
        tv.setDuplicateParentStateEnabled(true);
        tv.setText(text);
        return tv;
	}
	
	public static TextView createTextView(Context context,String text){
		TextView tv = new TextView(context);
        tv.setDuplicateParentStateEnabled(true);
        tv.setText(text);
        return tv;
	}
}
