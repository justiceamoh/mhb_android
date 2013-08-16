package edu.dartmouth.mhb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HymnsDataSource {
	// Database Strings
	public static final String TABLE_HYMNS = "hymns";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_AUTHOR = "author";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_LYRICS = "lyrics";
    public static final String KEY_SEARCH = "searchData";
	
	 // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { COLUMN_ID, 
	      COLUMN_TITLE, COLUMN_AUTHOR,
	      COLUMN_URL, COLUMN_LYRICS };
	  
	  
	 
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
	  
	  //TODO check search database method
	   public Cursor searchHymn(String inputText) throws SQLException {        
	        String query = "SELECT * FROM " + TABLE_HYMNS + 
	        " WHERE " + TABLE_HYMNS + " MATCH '" + inputText + "';";	        	        
	        Cursor mCursor = database.rawQuery(query,null);
	 
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;	 
	    }	  
	   

	  public List<Hymn> getAllHymns() {
	  	List<Hymn> hymns = new ArrayList<Hymn>();

	  	Cursor cursor = database.query(TABLE_HYMNS,
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
	    hymn.setAuthor(cursor.getString(2));
	    hymn.setUrl(cursor.getString(3));
	    hymn.setLyrics(cursor.getString(4));	    	    
	    return hymn;
	  }

	  
	  
}
