package com.JustInGApps.fastmathchallenge2.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EditTextView extends EditText{

	public EditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void init(int Width, int Height, int marginX, int marginY){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Width, Height);
		params.setMargins(marginX, marginY, 0, 0);
		super.setLayoutParams(params);
		invalidate();
	}
}
