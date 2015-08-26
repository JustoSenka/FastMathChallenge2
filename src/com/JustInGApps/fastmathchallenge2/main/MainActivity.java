package com.JustInGApps.fastmathchallenge2.main;

import java.util.Random;

import com.JustInGApps.fastmathchallenge2.R;
import com.JustInGApps.fastmathchallenge2.R.drawable;
import com.JustInGApps.fastmathchallenge2.R.id;
import com.JustInGApps.fastmathchallenge2.R.layout;
import com.JustInGApps.fastmathchallenge2.customview.Number;
import com.JustInGApps.fastmathchallenge2.database.DatabaseHandler;
import com.JustInGApps.fastmathchallenge2.highscore.AddHighscore;
import com.JustInGApps.fastmathchallenge2.highscore.Highscore;
import com.JustInGApps.fastmathchallenge2.highscore.HighscoreActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	CDraw CDraw;
	Button adView;
	LinearLayout LL;
	DatabaseHandler db;
	Highscore[] HS;

	int Height, Width, gameState = -1, numberCount = 0, maxNumberCount = 0, sum, goal, score, time,
			LM, pressed = 0, upMove = 0;

	Random ran;

	Bitmap[] nums, signBmp;
	Bitmap circle, temp, reset;

	Number[] num;

	Typeface typeface;
	float scaled, x, y, x2, y2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LL = (LinearLayout) findViewById(R.id.ll);

		// cdraw = (CDraw) findViewById(R.id.view);
		CDraw = new CDraw(this, null);
		typeface = Typeface.createFromAsset(getAssets(),
				"fonts/ChalkboardFont.ttf");

		CDraw.setTypeface(typeface);

		readDataBaseContent();

		adView = new Button(this, null);

		// adView.setHeight(80);
		// LL.addView(adView);
		LL.addView(CDraw);

		Width = getBaseContext().getResources().getDisplayMetrics().widthPixels;
		Height = getBaseContext().getResources().getDisplayMetrics().heightPixels;// -
																					// 80;

		// Bitmaps

		nums = new Bitmap[10];
		signBmp = new Bitmap[4];
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
		circle = ScaleBitmapW(temp, 160);

		for (int i = 0; i < 10; i++) {
			temp = BitmapFactory.decodeResource(getResources(), R.drawable.num0 + i);
			nums[i] = ScaleBitmapW(temp, 80);
		}

		for (int i = 0; i < 4; i++) {
			temp = BitmapFactory.decodeResource(getResources(), R.drawable.sign0 + i);
			signBmp[i] = ScaleBitmapW(temp, 40);
		}
		temp = BitmapFactory.decodeResource(getResources(), R.drawable.reset);
		reset = ScaleBitmapW(temp, 160);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void readDataBaseContent() {
		// TODO DATABASE

		db = new DatabaseHandler(this);

		HS = new Highscore[20];

		if (db.getCount() == 0) {
			for (int i = 0; i < 20; i++) {
				HS[i] = new Highscore("---", 0);
				db.addHighscore(HS[i]);
			}
		} else {
			for (int i = 0; i < 20; i++)
				HS[i] = db.getHighscore(i);
		}
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();

		if (gameState == -1)
			finish();
		else if (gameState >= 0) {
			time = 0;
			gameState = -1;
			CDraw.setGameState(-1);
			CDraw.setBeforeGameTitle(0);
		}
		CDraw.postInvalidate();
	}

	/*
	 * public boolean onKeyUp(int keyCode, KeyEvent event) { // TODO
	 * Auto-generated method stub if (keyCode == KeyEvent.KEYCODE_BACK) {
	 * 
	 * return true;
	 * 
	 * } else return super.onKeyUp(keyCode, event); }
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// TODO On touch

		Width = getBaseContext().getResources().getDisplayMetrics().widthPixels;
		Height = getBaseContext().getResources().getDisplayMetrics().heightPixels;// -
																					// adView.getHeight();
		x = (int) e.getX();
		y = (int) e.getY();// - adView.getHeight();
		
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			x2 = x;
			y2 = y;
			pressed = -1;
			upMove = -1;

			if (gameState == -1) {
				for (int i = 0; i < 4; i++)
					if (x > getX(i * 190 + (i * 2 + 1) * 30)
							&& x < getX(i * 190 + (i * 2 + 1) * 30 + 190)
							&& y > getY(450) && y < getY(450) + getX(190))
						CDraw.setPressed(i);

				if (x > getX(300) && x < getX(700) && y > getY(490) + getX(190)
						&& y < getY(650) + getX(190))
					CDraw.setPressed(44);
			}

			if (gameState >= 0){
				for (int i = 0; i < CDraw.numberCount; i++)
					if (CDraw.num[i].checkIfPressed((int) x, (int) y)) pressed = i;
				if (x > getX(830) && x < getX(990) && y > getY(280) && y < getY(340) + reset.getHeight()) CDraw.setPressed(99);
			}
			break;

		case MotionEvent.ACTION_MOVE:

			upMove = -1;
			
			if (gameState >= 0 && pressed != -1){
				for (int i = 0; i < CDraw.numberCount; i++){
					if (CDraw.num[i].checkIfUpMove((int) x, (int) y, CDraw.num[pressed]) && i != pressed) upMove = i;
				}
				CDraw.setLine((int)x, (int)y, (int)x2, (int)y2);
			}
			break;

		case MotionEvent.ACTION_UP:

			if (gameState == -1) {
				int i = CDraw.getPressed();

				if (i == 44 && x > getX(300) && x < getX(700)
						&& y > getY(490) + getX(190)
						&& y < getY(650) + getX(190)) {

					this.startActivity(new Intent(MainActivity.this,
							HighscoreActivity.class));

				} else if (x > getX(i * 190 + (i * 2 + 1) * 30)
						&& x < getX(i * 190 + (i * 2 + 1) * 30 + 190)
						&& y > getY(480) && y < getY(480) + getX(190)) {

					gameState = i;
					CDraw.setGameState(i);
					setNumbers(i);
					score = 0;
					CDraw.setScore(score);

					setTimer();
				}
			}
			// Reset pressed
			if (gameState >= 0 && CDraw.getPressed() == 99 && x > getX(830) && x < getX(990) && y > getY(280) && y < getY(340) + reset.getHeight()) 
				CDraw.passNumbers(maxNumberCount, num);
			
			// Up on number
			if (gameState >= 0 && upMove != -1 && pressed != -1) {

				int sign1, sign2, score1, score2;
				
				// Temp vars to simplify things
				sign1 = CDraw.num[pressed].getSign();
				sign2 = CDraw.num[upMove].getSign();
				score1 = CDraw.num[pressed].getScore();
				score2 = CDraw.num[upMove].getScore();
				
				// Bullshit's calculations
				if (sign1 == 0 && sign2 == 0) CDraw.num[upMove].setScore(score1 + score2);
				if (sign1 == 0 && sign2 == 1) CDraw.num[upMove].setScore(score1 - score2);
				if (sign1 == 0 && sign2 == 2) CDraw.num[upMove].setScore(score1 * score2);
				if (sign1 == 0 && sign2 == 3) CDraw.num[upMove].setScore(score1 / score2);
				
				if (sign1 == 1 && sign2 == 0) CDraw.num[upMove].setScore(- score1 + score2);
				if (sign1 == 1 && sign2 == 1) CDraw.num[upMove].setScore(- score1 - score2);
				if (sign1 == 1 && sign2 == 2) CDraw.num[upMove].setScore((- score1) * score2);
				if (sign1 == 1 && sign2 == 3) CDraw.num[upMove].setScore((- score1) / score2);
				
				if (sign1 == 2 && sign2 == 0) CDraw.num[upMove].setScore(score2 * score1);
				if (sign1 == 2 && sign2 == 1) CDraw.num[upMove].setScore((- score2) * score1);

				if (sign1 == 3 && sign2 == 0) CDraw.num[upMove].setScore(score2 / score1);
				if (sign1 == 3 && sign2 == 1) CDraw.num[upMove].setScore((- score2) / score1);
				
				// Alterates sign of merged and left number, positive or negative
				if (CDraw.num[upMove].getScore() < 0) {
					CDraw.num[upMove].setSign(1, signBmp[1]);
					CDraw.num[upMove].setScore(-CDraw.num[upMove].getScore());
				}
				else CDraw.num[upMove].setSign(0, signBmp[0]);
				
				// checks if goal met
				if (CDraw.num[upMove].getScore() == goal && CDraw.num[upMove].getSign() == 0) {
					score++;
					CDraw.setScore(score);
					setNumbers(gameState);
					pressed = -1;
					upMove = -1;
					CDraw.setLine(0, 0, 0, 0);
					
					if (score <= 10) time = 81 - score * 4;
					else if (score <= 15) time = 41 - (score - 10) * 3;
					else if (score <= 20) time = 26 - (score - 15) * 2;
					else if (score <= 30) time = 16 - (score - 20) * 1;
					else if (score <= 35) time = 6;
					else if (score <= 40) time = 5;
					else if (score <= 45) time = 4;
					else if (score <= 50) time = 3;
					else time = 2;
					
					CDraw.setRomm(true);
					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								CDraw.setRomm(false);
							}
						}
					}).start();
					
					break;
				}
				
				// Eliminates already used number
				for (int i = pressed; i < CDraw.numberCount - 1; i++){
					CDraw.num[i] = CDraw.num[i + 1];
				}
				CDraw.numberCount--;
			}
			
			CDraw.setLine(0, 0, 0, 0);
			pressed = -1;
			upMove = -1;
			CDraw.setPressed(-1);
			break;

		}
		
		if (gameState >= 0){
			for (int i = 0; i < CDraw.numberCount; i++)
				CDraw.num[i].setSel(false);
			
			if (pressed != -1) CDraw.num[pressed].setSel(true);
			if (upMove != -1) CDraw.num[upMove].setSel(true);
		}

		CDraw.postInvalidate();
		return true;
	}

	private void setTimer() {
		// TODO set Timer

		time = 90;

		CDraw.setTime(time);
		CDraw.postInvalidate();

		new Thread(new Runnable() {
			public void run() {

				for (int i = 1; i <= 4; i++) {

					CDraw.setBeforeGameTitle(i);
					CDraw.postInvalidate();

					if (time <= 0)
						break;

					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				CDraw.setBeforeGameTitle(0);
				CDraw.postInvalidate();

				while (time > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					time--;
					if (time == 0) {

//						setNumbers(gameState);
//						setTimer();
						LM = gameState * 5;

						gameState = -2;
						CDraw.setGameState(-2);
						CDraw.postInvalidate();

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								checkIfHigh();
							}
						});

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						gameState = -1;
						CDraw.setGameState(-1);
						CDraw.postInvalidate();
						return;
					}

					CDraw.setTime(time);
					CDraw.postInvalidate();
				}

			}
		}).start();
	}

	protected void checkIfHigh() {

		if (HS[4 + LM].getScore() < score) {
			Intent i = new Intent(MainActivity.this, AddHighscore.class);
			i.putExtra("KEY_SCORE", score);
			i.putExtra("KEY_LM", LM);
			this.startActivity(i);

			readDataBaseContent();
		}
	}

	private void setNumbers(int lc) {
		// TODO Set numbers
		
		int sign = 0, temp = 0;
		ran = new Random();
		
		sum = 0;

		if (lc == 0) {
			maxNumberCount = 4;
			numberCount = ran.nextInt(3) + 2;
		} else if (lc == 1) {
			maxNumberCount = 6;
			numberCount = ran.nextInt(4) + 2;
		} else if (lc == 2) {
			maxNumberCount = 8;
			numberCount = ran.nextInt(5) + 2;
		} else if (lc == 3) {
			maxNumberCount = 10;
			numberCount = ran.nextInt(6) + 2;
		}

		num = new Number[maxNumberCount];
		
		goal = 0;
		for (int i = 0; i < numberCount; i++) {

			temp = 0;
			
			// Randomly sets number and sign
			if (goal == 0){
				sign = 0;
				temp = max(ran.nextInt(9) + 1, ran.nextInt(7) + 1);
			}
			else {
				sign = ran.nextInt(4);
				if (sign == 0) temp = max(ran.nextInt(9) + 1, ran.nextInt(7) + 1);
				else if (sign == 1) temp = min(ran.nextInt(9) + 1, ran.nextInt(7) + 3);
				else if (sign == 2) temp = ran.nextInt(5) + 2;
				else if (sign == 3 && goal % 5 == 0) temp = 5;
				else if (sign == 3 && goal % 4 == 0) temp = 4;
				else if (sign == 3 && goal % 3 == 0) temp = 3;
				else if (sign == 3 && goal % 2 == 0) temp = 2;
				
				// Sign was division, but does not divide to anything, so repeat without division now
				else {
					sign = ran.nextInt(3);
					if (sign == 0 || sign == 1) temp = ran.nextInt(9) + 1;
					else temp = ran.nextInt(5) + 2;
				}
			}
			
			// Alterating goal
			if (sign == 0) goal += temp;
			if (sign == 1) goal -= temp;
			if (sign == 2) goal *= temp;
			if (sign == 3) goal /= temp;
			
			if (goal <= 0){
				setNumbers(gameState);
				break;
			}
			
			// Finishing number
			if (temp < 0 || temp > 9) {
				Toast.makeText(this,"Error has accuired while making numbers, please email me with error code 01", Toast.LENGTH_LONG).show();
				temp = ran.nextInt(9) + 1;
			}
			
			num[i] = new Number(temp, sign, typeface, circle, signBmp[sign]);

			setCoordsAlgorithm(i);
		}
		
		for (int i = numberCount; i < maxNumberCount; i++) {

			// Randomly sets number and sign
			
			temp = 0;
			
			sign = ran.nextInt(4);
			if (sign == 0 || sign == 1)temp = ran.nextInt(9) + 1;
			else if (sign == 2)temp = ran.nextInt(5) + 2;
			else if (sign == 3)temp = ran.nextInt(4) + 2;

			// Finishing number
			if (temp < 0 || temp > 9) {
				Toast.makeText(this,"Error has accuired while making numbers, please email me with error code 01", Toast.LENGTH_LONG).show();
				temp = ran.nextInt(9) + 1;
			}
	
			num[i] = new Number(temp, sign, typeface, circle, signBmp[sign]);

			setCoordsAlgorithm(i);
		}
		
		// Passing numbers to CDraw
		CDraw.setGoal(goal);
		CDraw.setSum(0);
		CDraw.passNumbers(maxNumberCount, num);
	}

	private void setCoordsAlgorithm(int i) {
	// TODO set coords algorithm
	
	boolean correct = false;
	int count = 0, bestX = 0, bestY = 0, bestDist = 0, dist = 9999;
	
		while (!correct) {
	
			count++;
			
			while (true){
				num[i].setCoords(ran.nextInt(getX(1000) - circle.getWidth()), ran.nextInt(getY(1000) - circle.getHeight()));
				if (num[i].getX() + circle.getWidth() < getX(800) || num[i].getY() > getY(500)) break;
			}
			
			int xC = num[i].getX() + (circle.getWidth() / 2);
			int yC = num[i].getY() + (circle.getHeight() / 2);

			correct = true;
			for (int j = 0; j < i; j++){
				dist = min(sqr(xC - num[j].getX() - (circle.getWidth() / 2)) + sqr(yC - num[j].getY() - (circle.getHeight() / 2)), dist);
				if (sqr(xC - num[j].getX() - (circle.getWidth() / 2)) + sqr(yC - num[j].getY() - (circle.getHeight() / 2)) < sqr(circle.getWidth()) - getX(20)) {
					correct = false;
					break;
				}
			}
			if (dist > bestDist){
				bestX = num[i].getX();
				bestY = num[i].getY();
			}
			if (count > 1000){
				num[i].setCoords(bestX, bestY);
				correct = true;
			}
		}
	
	return;
}

	private int min(int a, int b) {
		return (a < b) ? a : b;
	}
	
	private int max(int a, int b) {
		return (a < b) ? b : a;
	}

	private int sqr(int a) {
		return a * a;
	}

	// #################################################################################################################

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
