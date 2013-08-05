package edu.dartmouth.mhb;

import android.content.Context;
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
		    database = dbHelper.getWritableDatabase();
	  }	  

	  public void close() {
		  dbHelper.close();
	  }
	  
	  
	  
	  
	  
	  
}
