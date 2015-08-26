package com.JustInGApps.fastmathchallenge2.main;

import com.JustInGApps.fastmathchallenge2.R;
import com.JustInGApps.fastmathchallenge2.R.drawable;
import com.JustInGApps.fastmathchallenge2.customview.Number;
import com.JustInGApps.fastmathchallenge2.highscore.Highscore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CDraw extends View{

	Bitmap temp, back, title, select, highbmp, countto, circle, scorebmp, cloud, reset;
	Bitmap[] nums, levels;
	
	Number[] num;
	
	Paint paint, clear, superClear, textBig, textSmall;
	
	int Width, Height, pressed = -1, gameState = -1, beforeGameTitle = 0, numberCount = 0, score, time, goal, sum;
	float scaled;
	
	int x1, y1, x2, y2;
	
	boolean t = true, showRoom = false;
	Typeface typeface;
	
	Highscore[] HS;
	
	public CDraw(Context context, AttributeSet attrs) {
		super(context);
		// TODO Auto-generated constructor stub
		
		//setWillNotDraw(false);

		// For onKeyUp to work brieffly with onTouchEvent
		//this.requestFocus();
		//this.setFocusableInTouchMode(true);
	}

	public void Initialize(int w, int h) {
		// TODO Auto-generated method stub

		// Basic things

		Width = w;
		Height = h;

		paint = new Paint();
		clear = new Paint();
		superClear = new Paint();
		textBig = new Paint();
		textSmall = new Paint();
		
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(getX(5));
		paint.setTextSize(getY(60));
		
		textBig.setColor(Color.WHITE);
		textBig.setTypeface(typeface);
		textBig.setTextSize(getY(150));

		textSmall.setColor(Color.WHITE);
		textSmall.setTypeface(typeface);
		textSmall.setTextSize(getY(100));

		clear.setAlpha(100);
		superClear.setAlpha(50);
		
		nums = new Bitmap[10];
		levels = new Bitmap[4];
		
		// Creates Bitmaps

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.chalkboard);
		back = ScaleBitmap(temp, 1000, 1000);
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.title);
		title = ScaleBitmapW(temp, 800);

		temp = BitmapFactory.decodeResource(getResources(), R.drawable.selectlevel);
		select = ScaleBitmapW(temp, 400);
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.highbmp);
		highbmp = ScaleBitmapW(temp, 400);
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.countto);
		countto = ScaleBitmapW(temp, 800);
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.scorebmp);
		scorebmp = ScaleBitmapW(temp, 400);
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
		circle = ScaleBitmapW(temp, 140);
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.reset);
		reset = ScaleBitmapW(temp, 160);
		
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
		cloud = ScaleBitmap(temp, 230, 520);

		for (int i = 0; i < 10; i++){
			temp = BitmapFactory.decodeResource(getResources(), R.drawable.num0 + i);
			nums[i] = ScaleBitmapW(temp, 80);
		}
		
		for (int i = 0; i < 4; i++){
			temp = BitmapFactory.decodeResource(getResources(), R.drawable.level1 + i);
			levels[i] = ScaleBitmapW(temp, 190);
		}
		
		t = false;
		invalidate();
	}
	
	
	@Override
	protected void onDraw(Canvas c) {
		// TODO Auto-generated method stub
		super.onDraw(c);
		
		if (t) Initialize(c.getWidth(), c.getHeight());
		Width = c.getWidth();
		Height = c.getHeight();
		
		c.drawBitmap(back, 0, 0, paint);
		
		if (gameState == -1){
			c.drawBitmap(title, getX(100), getY(90), paint);
			c.drawBitmap(select, getX(300), getY(310), paint);
			
			if (pressed != 44) c.drawBitmap(highbmp, getX(300), getY(490) + getX(190), paint);
			else c.drawBitmap(highbmp, getX(300), getY(490) + getX(190), clear);
			
			for (int i = 0; i < 4; i++)
				if (pressed != i)c.drawBitmap(levels[i], getX(i * 190 + (i * 2 + 1) * 30), getY(450), paint);
				else c.drawBitmap(levels[i], getX(i * 190 + (i * 2 + 1) * 30), getY(450), clear);
		}
		
		if (gameState >= 0){
			
			c.drawText("Score: " + score + "         Time left: " + time + " s", getX(10), getY(70), paint);
			
			if (beforeGameTitle > 0) {
				c.drawBitmap(countto, getX(500) - countto.getWidth() / 2, getY(300), paint);
				
				c.drawBitmap(nums[3], getX(400) - nums[0].getWidth() / 2, getY(500), superClear);
				c.drawBitmap(nums[2], getX(500) - nums[0].getWidth() / 2, getY(500), superClear);
				c.drawBitmap(nums[1], getX(600) - nums[0].getWidth() / 2, getY(500), superClear);
				
				if (beforeGameTitle > 1) c.drawBitmap(nums[3], getX(400) - nums[0].getWidth() / 2, getY(500), paint);
				if (beforeGameTitle > 2) c.drawBitmap(nums[2], getX(500) - nums[0].getWidth() / 2, getY(500), paint);
				if (beforeGameTitle > 3) c.drawBitmap(nums[1], getX(600) - nums[0].getWidth() / 2, getY(500), paint);
			}
			else if (showRoom){
				c.drawText("Room: " + score, getX(350), getY(550), textBig);
			}
			else {
				for (int i = 0; i < numberCount; i++) num[i].draw(c);
				
				c.drawText("Goal:", getX(840), getY(130), textBig);
				
				if (goal < 10) c.drawText(goal + "", getX(900), getY(260), textBig);
				else if (goal < 100) c.drawText(goal + "", getX(880), getY(260), textBig);
				else c.drawText(goal + "", getX(860), getY(260), textBig);
				
				if (pressed == 99) c.drawBitmap(reset, getX(830), getY(310), clear);
				else c.drawBitmap(reset, getX(835), getY(310), paint);
				
				c.drawBitmap(cloud, getX(780), 0, paint);
			}
			if (x1 != 0 || y1 != 0 || x2 != 0 || y2 != 0) c.drawLine(x1, y1, x2, y2, paint);
		}
		
		if (gameState == -2){
			c.drawBitmap(scorebmp, getX(400) - scorebmp.getWidth() / 2, getY(400), paint);
			
			if (score >= 100) c.drawBitmap(nums[numeralOf(score, 3)], getX(410) + scorebmp.getWidth() / 2, getY(400) + (scorebmp.getHeight() - nums[0].getHeight()) / 2, paint);
			if (score >= 10) c.drawBitmap(nums[numeralOf(score, 2)], getX(480) + scorebmp.getWidth() / 2, getY(400) + (scorebmp.getHeight() - nums[0].getHeight()) / 2, paint);
			c.drawBitmap(nums[numeralOf(score, 1)], getX(550) + scorebmp.getWidth() / 2, getY(400) + (scorebmp.getHeight() - nums[0].getHeight()) / 2, paint);
		}
	}

	
	private int mod(int arg){
		if (arg > 0) return arg;
		else return -arg;
	}
	
	private int numeralOf(int value, int No){
		int temp = 0;
		if (No == 3) temp = value / 100;
		if (No == 2) temp = (value / 10) % 10;
		if (No == 1) temp = value % 10;
		if (temp > 9 || temp < 0) temp = 0;
		return temp;
	}
	
	public void passNumbers(int NumberCount, Number[] Num){
		this.numberCount = NumberCount;
		this.num = new Number[NumberCount];
		
		for (int i = 0; i < NumberCount; i++){
			this.num[i] = new Number(Num[i].score, Num[i].sign, Num[i].typeface, Num[i].circle, Num[i].signBmp);
			this.num[i].setCoords(Num[i].getX(), Num[i].getY());
		}
	}
	public void setLine(int argX1, int argY1, int argX2, int argY2){
		this.x1 = argX1;
		this.y1 = argY1;
		this.x2 = argX2;
		this.y2 = argY2;
	}
	public void setPressed(int Pressed){
		this.pressed = Pressed;
	}
	public int getPressed(){
		return pressed;
	}
	public void setGameState(int GameState) {
		this.gameState = GameState;
	}
	public int getGameState() {
		return gameState;
	}
	public void setBeforeGameTitle(int BeforeGameTitle) {
		this.beforeGameTitle = BeforeGameTitle;
	}
	public void setScore(int Score) {
		this.score = Score;
	}
	public void setSum(int Sum) {
		this.sum = Sum;
	}
	public void setGoal(int Goal) {
		this.goal = Goal;
	}
	public void setTime(int Time) {
		this.time = Time;
	}
	public void setTypeface(Typeface Typeface) {
		this.typeface = Typeface;
	}
	public void setRomm(boolean ShowRoom){
		this.showRoom = ShowRoom;
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
