package com.JustInGApps.fastmathchallenge2.highscore;

import com.JustInGApps.fastmathchallenge2.R;
import com.JustInGApps.fastmathchallenge2.R.drawable;
import com.JustInGApps.fastmathchallenge2.R.id;
import com.JustInGApps.fastmathchallenge2.R.layout;
import com.JustInGApps.fastmathchallenge2.database.DatabaseHandler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class HighscoreActivity extends Activity {

	LinearLayout LL;
	
	HighscoreView Hv;
	Highscore[] HS;
	
	DatabaseHandler db;
	
	int Width, Height;
	float scaled;
	
	Bitmap back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore_view);
		
		readDataBaseContent();
		
		LL = (LinearLayout) findViewById(R.id.ll);
		
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/ChalkboardFont.ttf");
		
		Hv = new HighscoreView(this, null);
		db = new DatabaseHandler(this);
		
		Hv.setTypeface(typeface);
		Hv.passHighscore(HS);
		Hv.setBackgroundResource(R.drawable.chalkboard);
		
		Width = getBaseContext().getResources().getDisplayMetrics().widthPixels;
		Height = getBaseContext().getResources().getDisplayMetrics().heightPixels;
		
		back = BitmapFactory.decodeResource(getResources(), R.drawable.back);
		back = ScaleBitmapW(back, 260);
		
		LL.addView(Hv);
	}
	
	
	
	private void readDataBaseContent() {
		// TODO DATABASE
		
		db = new DatabaseHandler(this);
		
		HS = new Highscore[20];
		
		if (db.getCount() != 0) {
			for (int i = 0; i < 20; i++)
				HS[i] = db.getHighscore(i);
		}
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		
		int x, y;
		
		x = (int) e.getX();
		y = (int) e.getY();
		
		for (int i = 0; i < 4; i++)
			if (!Hv.isPressed() && x > getX(30) + i * getX(160) && x < getX(30) + i * getX(160) + getX(130) && y > getY(700) && y < getY(700) + getX(130))
				Hv.setLM(i * 5);
		
		switch (e.getAction()) {

		case MotionEvent.ACTION_DOWN:

			if (x > getX(980) - back.getWidth() && x < getX(980) && y > getY(980) - back.getHeight() && y < getY(980))
				Hv.setPressed(true);
			break;

		case MotionEvent.ACTION_UP:

			if (Hv.isPressed() && x > getX(980) - back.getWidth() && x < getX(980) && y > getY(980) - back.getHeight() && y < getY(980))
				finish();
			
			Hv.setPressed(false);
			
			break;
		}
		
		Hv.postInvalidate();
		return true;
	}

	
	
	
	
	
	
	
	
	
	// ###################################################################################################################

	private Bitmap ScaleBitmap(Bitmap bmp, int w, int h) {
		// TODO Auto-generated method stub

		Bitmap rbmp;
		rbmp = Bitmap.createScaledBitmap(bmp, (int) (Width * 1.00f * w / 1000),
				(int) (Height * 1.00f * h / 1000), false);

		return rbmp;
	}

	private Bitmap ScaleBitmapW(Bitmap bmp, int w) {
		// TODO Auto-generated method stub

		Bitmap rbmp;

		scaled = (Width * 1.00f * w / 1000) / bmp.getWidth();

		rbmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * scaled),
				(int) (bmp.getHeight() * scaled), false);

		return rbmp;
	}

	private Bitmap ScaleBitmapH(Bitmap bmp, int h) {
		// TODO Auto-generated method stub

		Bitmap rbmp;

		scaled = (Height * 1.00f * h / 1000) / bmp.getHeight();

		rbmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * scaled),
				(int) (bmp.getHeight() * scaled), false);

		return rbmp;
	}

	// Converts from 1000 system to pixels

	private int getX(int arg) {

		arg = (arg * Width) / 1000;

		return arg;
	}

	private int getY(int arg) {

		arg = (arg * Height) / 1000;

		return arg;
	}	
}
