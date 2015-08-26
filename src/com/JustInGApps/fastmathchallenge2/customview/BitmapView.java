package com.JustInGApps.fastmathchallenge2.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class BitmapView extends View{

	boolean t;
	Paint paint;
	
	Bitmap bmp;
	int Height, Width;
	boolean clickable;
	
	public BitmapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		t = false;
		paint = new Paint();	
		clickable = false;
		
        setOnTouchListener(new View.OnTouchListener() {
            @Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (clickable) {
					switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						paint.setAlpha(100);
						break;

					case MotionEvent.ACTION_UP:
						paint.setAlpha(255);
						v.performClick();
						break;
					}
				}
				invalidate();
            	
				return true;
			}
        });
	}
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}

	public void init(Bitmap Bmp, int x, int y, boolean Clickable){
		this.bmp = Bmp;
		this.Height = bmp.getHeight();
		this.Width = bmp.getWidth();
		this.clickable = Clickable;
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Width, Height);
		params.setMargins(x, y, 0, 0);
		
		super.setLayoutParams(params);
		t = true;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		if (t){
			canvas.drawBitmap(bmp, 0, 0, paint);
		}
	}
}
