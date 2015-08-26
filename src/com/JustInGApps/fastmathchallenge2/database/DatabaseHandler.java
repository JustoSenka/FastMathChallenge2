package com.JustInGApps.fastmathchallenge2.database;

import com.JustInGApps.fastmathchallenge2.highscore.Highscore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 11;

	// Database Name
	private static final String DATABASE_NAME = "FastMathChallengeDB";

	// Table names
	private static final String TABLE_SCORE = "highscores";

	// Table Columns names
	private static final String KEY_ID = "id";

	private static final String KEY_NAME = "name";
	private static final String KEY_SCORE = "score";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_SCORE + " INTEGER" + ")";
		db.execSQL(CREATE_EVENTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

		// Create tables again
		onCreate(db);
	}

	public void addHighscore(Highscore h) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, h.getName());
		values.put(KEY_SCORE, h.getScore());

		// Inserting Row
		db.insert(TABLE_SCORE, null, values);
		db.close(); // Closing database connection
	}

	public Highscore getHighscore(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_SCORE, new String[] { KEY_ID, KEY_NAME,
				KEY_SCORE }, KEY_ID + "=?",
				new String[] { String.valueOf(id + 1) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Highscore h = new Highscore(cursor.getString(1), Integer.parseInt(cursor.getString(2)));
		return h;
	}

	public int replaceHighscore(Highscore h, int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, h.getName());
		values.put(KEY_SCORE, h.getScore());

		// updating row
		return db.update(TABLE_SCORE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(id + 1) });
	}

	
	// Getting Count of rows
	public int getCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SCORE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();
		return count;
	}
}
