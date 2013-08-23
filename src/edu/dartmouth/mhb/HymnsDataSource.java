package edu.dartmouth.mhb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class HymnsDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_AUTHOR,
			MySQLiteHelper.COLUMN_URL, MySQLiteHelper.COLUMN_LYRICS };

	public HymnsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {

		try {
			dbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			dbHelper.close();
			dbHelper.openDataBase();
			// TODO check also: return myDatabase from dbHelper
			database = dbHelper.getDatabase();

		} catch (SQLException sqle) {
			throw sqle;
		}

	}

	public void close() {
		dbHelper.close();
	}

	// TODO check search database method
//	public Cursor searchHymn(String inputText) throws SQLException {
//
//		String query = "SELECT * FROM " + Globals.KEY_TABLE + " WHERE "
//				+ Globals.KEY_TABLE + " MATCH '" + inputText + "';";
//		Cursor mCursor = database.rawQuery(query, null);
//
//		if (mCursor != null) {
//			mCursor.moveToFirst();
//		}
//
//		return mCursor;
//	}
	
	public List<Hymn> searchHymn(String inputText) throws SQLException {
		List<Hymn> hymns = new ArrayList<Hymn>();
		
		
		String query = "SELECT * FROM " + Globals.KEY_TABLE + " WHERE "
				+ Globals.KEY_TABLE + " MATCH '" + inputText + "';";
		Cursor cursor = database.rawQuery(query, null);

		if (cursor != null) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Hymn hymn = cursorToHymn(cursor);
				hymns.add(hymn);
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		return hymns;
	}

	public ArrayList<Hymn> getAllHymns() {
		ArrayList<Hymn> hymns = new ArrayList<Hymn>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_HYMNS, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Hymn hymn = cursorToHymn(cursor);
			hymns.add(hymn);
			cursor.moveToNext();
		}

		// Closing the cursor
		cursor.close();
		return hymns;
	}

	public Hymn cursorToHymn(Cursor cursor) {
		Hymn hymn = new Hymn();
		hymn.setId(cursor.getLong(0));
		hymn.setTitle(cursor.getString(1));
		hymn.setAuthor(cursor.getString(2));
		hymn.setUrl(cursor.getString(3));
		hymn.setLyrics(cursor.getString(4));
		return hymn;
	}

}
