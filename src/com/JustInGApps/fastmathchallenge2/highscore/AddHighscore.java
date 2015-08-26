package com.JustInGApps.fastmathchallenge2.highscore;

import com.JustInGApps.fastmathchallenge2.R;
import com.JustInGApps.fastmathchallenge2.R.drawable;
import com.JustInGApps.fastmathchallenge2.R.id;
import com.JustInGApps.fastmathchallenge2.R.layout;
import com.JustInGApps.fastmathchallenge2.customview.BitmapView;
import com.JustInGApps.fastmathchallenge2.customview.EditTextView;
import com.JustInGApps.fastmathchallenge2.database.DatabaseHandler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class AddHighscore extends Activity {

	LinearLayout LL;
	
	int Height, Width, score, LM, x ,y;
	float scaled;
	boolean leave = false;
	
	Bitmap proceed, newHigh;
	BitmapView proc, title;
	EditTextView etw;
	
	DatabaseHandler db;
	Highscore[] HS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore_form);
		
		LM = 0;
		score = 0;
		
		if (getIntent().getExtras() != null) {
			LM = getIntent().getExtras().getInt("KEY_LM");
			score = getIntent().getExtras().getInt("KEY_SCORE");
		}
		
		db = new DatabaseHandler(this);
		readDataBaseContent();
		
		
		LL = (LinearLayout) findViewById(R.id.ll);
		LL.setBackgroundResource(R.drawable.chalkboard);
		
		Width = getBaseContext().getResources().getDisplayMetrics().widthPixels;
		Height = getBaseContext().getResources().getDisplayMetrics().heightPixels;
		
		newHigh = BitmapFactory.decodeResource(getResources(), R.drawable.newhigh);
		newHigh = ScaleBitmapW(newHigh, 800);
		
		proceed = BitmapFactory.decodeResource(getResources(), R.drawable.proceed);
		proceed = ScaleBitmapW(proceed, 400);
		
		
		title = new BitmapView(this, null);
		title.init(newHigh, getX(100), getY(50), false);
		LL.addView(title);
		
		etw = new EditTextView(this, null);
		etw.init(getX(500), getY(200), getX(250), getY(20));
		etw.setTextSize(getY(60));
		LL.addView(etw);
		
		proc = new BitmapView(this, null);
		proc.init(proceed, getX(300), getY(20), true);
		LL.addView(proc);
		
		
		proc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				addScore();
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();

		if (leave) finish();
		else {
			leave = true;
			
			Toast.makeText(this, "Press once again to leave", Toast.LENGTH_SHORT).show();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					leave = false;
				}
			}).start();
		}
	}

	protected void addScore() {
		
		String name = "Empty";
		int max;
		Highscore temp;
		
		if (HS[4 + LM].getScore() < score){
			HS[4 + LM].setScore(score);
			if ((name = etw.getText().toString()) != "") HS[4 + LM].setName(name);
			else HS[4 + LM].setName("Empty");
			
			// Sort
			for (int i = LM; i < LM + 5; i++){
				max = i;
				for (int j = i + 1; j < LM + 5; j++){
					if (HS[max].getScore() < HS[j].getScore()){
						max = j;
					}
				}
				temp = HS[i];
				HS[i] = HS[max];
				HS[max] = temp;
			}
			
			for (int i = 0; i < 20; i++)
				db.replaceHighscore(HS[i], i);
		}
		
	}

	private void readDataBaseContent() {
		// TODO DATABASE
		
		db = new DatabaseHandler(this);
		
		HS = new Highscore[20];
		
		if (db.getCount() > 0) {
			for (int i = 0; i < 20; i++)
				HS[i] = db.getHighscore(i);
		}
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
