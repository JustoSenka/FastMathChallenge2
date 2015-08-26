package com.JustInGApps.fastmathchallenge2.customview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Number{

	public Bitmap circle, signBmp;
	public int x = 0, y = 0, width, height, score, sign;
	public boolean sel = false, com = false;;
	public Typeface typeface;
	public Paint paint, textBig, textMed, textSmall;
	
	public Number(int Score, int Sign, Typeface tp, Bitmap Circle, Bitmap SignBmp){
		
		this.score = Score;
		this.typeface = tp;
		this.circle = Circle;
		this.signBmp = SignBmp;
		this.sign = Sign;
		
		paint = new Paint();
		paint.setTypeface(typeface);
		
		
		textBig = new Paint();
		textMed = new Paint();
		textSmall = new Paint();
		
		textBig.setColor(Color.WHITE);
		textBig.setTypeface(typeface);
		textBig.setTextSize(circle.getHeight() * 100 / 100);

		textMed.setColor(Color.WHITE);
		textMed.setTypeface(typeface);
		textMed.setTextSize(circle.getHeight() * 70 / 100);
		
		textSmall.setColor(Color.WHITE);
		textSmall.setTypeface(typeface);
		textSmall.setTextSize(circle.getHeight() * 60 / 100);
		
		width = circle.getWidth();
		height = circle.getHeight();
	}

	public Number(Number num) {
		this.score = num.score;
		this.typeface = num.typeface;
		this.circle = num.circle;
		this.signBmp = num.signBmp;
		this.sign = num.sign;
	}

	public boolean checkIfPressed(int argX, int argY){
		if (argX > x && argX < x + width && argY > y && argY < y + height) {
			return (this.sel = true);
		}
		else return false;
	}
	
	public boolean checkIfUpMove(int argX, int argY, Number num) {
		if (argX > x && argX < x + width && argY > y && argY < y + height && (sign <= 1 || num.getSign() <= 1) && (sign != 3 || num.getScore() % score == 0) && (num.getSign() != 3 || score % num.getScore() == 0)) {
			return (this.sel = true);
		}
		else return false;
	}
	/*
	public boolean checkIfPressed(int argX, int argY, int sum){
		if (argX > x && argX < x + width && argY > y && argY < y + height && (sign <= 1 || sum != 0) && (sign != 3 || sum % score == 0)) {
			this.sel = true;
			paint.setAlpha(100);
			return true;
		}
		else return false;
	}
	*/
	public int getScore(){
		return this.score;
	}
	public int getSign(){
		return this.sign;
	}
	public void setSign(int Sign, Bitmap SignBmp){
		this.sign = Sign;
		this.signBmp = SignBmp;
	}
	public boolean isSel(){
		return this.sel;
	}
	public void setSel(boolean Sel){
		this.sel = Sel;
	}
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}
	public void setCoords(int X, int Y){
		this.x = X;
		this.y = Y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public void setScore(int Score){
		this.score = Score;
	}
	
	public void draw(Canvas c) {
		
		if (sel) c.drawBitmap(circle, x, y, paint);
		c.drawBitmap(signBmp, x + 10, y + (circle.getHeight() - signBmp.getHeight()) / 2, paint);
		if (score < 10) c.drawText(score + "", x + 10 + signBmp.getWidth(), y + textBig.getTextSize() * 8 / 10 - (textBig.getTextSize() - circle.getHeight()) / 2, textBig);
		else if (score < 100) c.drawText(score + "", x + 10 + signBmp.getWidth(), y + textMed.getTextSize() * 8 / 10 - (textMed.getTextSize() - circle.getHeight()) / 2, textMed);
		else c.drawText(score + "", x + signBmp.getWidth(), y + textSmall.getTextSize() * 8 / 10 - (textSmall.getTextSize() - circle.getHeight()) / 2, textSmall);
	}
}
