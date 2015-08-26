package com.JustInGApps.fastmathchallenge2.highscore;

import com.JustInGApps.fastmathchallenge2.R;
import com.JustInGApps.fastmathchallenge2.R.drawable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class HighscoreView extends View{

	int Height, Width, LM;
	float scaled;
	
	Paint paint, clear, superClear, textBig, textSmall;
	
	Bitmap[] levels;
	Bitmap temp, back;
	
	boolean t = true, pressed;
	Typeface typeface;
	Highscore[] HS;
	
	String[] spaceBNS;
	
	public HighscoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LM = 0;
		pressed = false;
	}
	
	public void Initialize(Canvas c) {
		// TODO Auto-generated method stub

		Width = c.getWidth();
		Height = c.getHeight();
		
		// Basic things

		paint = new Paint();
		clear = new Paint();
		superClear = new Paint();
		textBig = new Paint();
		textSmall = new Paint();
		
		paint.setColor(Color.RED);
		paint.setStrokeWidth(getX(40));
		paint.setTextSize(getY(60));
		
		textBig.setColor(Color.WHITE);
		textBig.setTypeface(typeface);
		textBig.setTextSize(getY(150));

		textSmall.setColor(Color.WHITE);
		textSmall.setTypeface(typeface);
		textSmall.setTextSize(getY(100));

		clear.setAlpha(100);
		superClear.setAlpha(50);
		
		levels = new Bitmap[4];
		
		// Creates Bitmaps
		
		for (int i = 0; i < 4; i++){
			temp = BitmapFactory.decodeResource(getResources(), R.drawable.level1 + i);
			levels[i] = ScaleBitmapW(temp, 130);
		}
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.back);
		back = ScaleBitmapW(temp, 260);
		
		t = false;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		if (t) Initialize(c);
		
		c.drawText("1. " + HS[0 + LM].getName(), getX(20), getY(150), textBig);
		c.drawText("2. " + HS[1 + LM].getName(), getX(20), getY(300), textSmall);
		c.drawText("3. " + HS[2 + LM].getName(), getX(20), getY(410), textSmall);
		c.drawText("4. " + HS[3 + LM].getName(), getX(20), getY(520), textSmall);
		c.drawText("5. " + HS[4 + LM].getName(), getX(20), getY(630), textSmall);
		
		c.drawText(spaceBNS[0 + LM] + HS[0 + LM].getScore(), getX(770), getY(150), textBig);
		c.drawText(spaceBNS[1 + LM] + HS[1 + LM].getScore(), getX(800), getY(300), textSmall);
		c.drawText(spaceBNS[2 + LM] + HS[2 + LM].getScore(), getX(800), getY(410), textSmall);
		c.drawText(spaceBNS[3 + LM] + HS[3 + LM].getScore(), getX(800), getY(520), textSmall);
		c.drawText(spaceBNS[4 + LM] + HS[4 + LM].getScore(), getX(800), getY(630), textSmall);
		
		for (int i = 0; i < 4; i++)
			if (LM / 5 == i) c.drawBitmap(levels[i], getX(30) + i * (levels[i].getWidth() + getX(30)), getY(700), paint);
			else c.drawBitmap(levels[i], getX(30) + i * (levels[i].getWidth() + getX(30)), getY(700), clear);
		
		if (pressed) c.drawBitmap(back, getX(980) - back.getWidth(), getY(980) - back.getHeight(), clear);
		else c.drawBitmap(back, getX(980) - back.getWidth(), getY(980) - back.getHeight(), paint);
	}
	
	
	public void setTypeface(Typeface Typeface) {
		this.typeface = Typeface;
	}
	public void passHighscore(Highscore[] hs){
		
		HS = new Highscore[20];
		spaceBNS = new String[20];
		
		for (int i = 0; i < 20; i++){
			this.HS[i] = hs[i];
			if (hs[i].getScore() >= 100) spaceBNS[i] = " ";
			else if (hs[i].getScore() >= 10) spaceBNS[i] = "  ";
			else spaceBNS[i] = "   ";
		}
	}
	public void setLM(int lm){
		this.LM = lm;
	}
	public void setPressed(boolean Pressed){
		this.pressed = Pressed;
	}
	public boolean isPressed(){
		return this.pressed;
	}
	
	
	// ###################################################################################################################

	private Bitmap ScaleBitmapW(Bitmap bmp, int w) {
		// TODO Auto-generated method stub

		Bitmap rbmp;

		scaled = (Width * 1.00f * w / 1000) / bmp.getWidth();

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
