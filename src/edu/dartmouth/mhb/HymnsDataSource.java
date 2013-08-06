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
	  
		   try{
			   dbHelper.close();
			   dbHelper.openDataBase();
			   //TODO check also: return myDatabase from dbHelper
			   database = dbHelper.getDatabase();
			   			   
		   }catch(SQLException sqle){
			   throw sqle;
		   }
		      
	  
	  }	  

	  public void close() {
		  dbHelper.close();
	  }
	  


	  public List<Hymn> getAllHymns() {
	  	List<Hymn> hymns = new ArrayList<Hymn>();

	  	Cursor cursor = database.query(MySQLiteHelper.TABLE_HYMNS,
        allColumns, null, null, null, null, null);

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


	  private Hymn cursorToHymn(Cursor cursor) {
	    Hymn hymn = new Hymn();
	    hymn.setId(cursor.getLong(0));
	    hymn.setTitle(cursor.getString(1));
	   // hymn.setAuthor(cursor.getString(2));
	    hymn.setUrl(cursor.getString(3));
	    //hymn.setLyrics(cursor.getString(4));	    	    
	    return hymn;
	  }

	  
	  
}
