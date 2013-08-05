package edu.dartmouth.mhb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	// Default system path of app database
	private static String DB_PATH = "/data/data/edu.dartmouth.mhb/databases/";
	private static String DB_NAME = "mhb.db";

	  public static final String TABLE_HYMNS = "hymns";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_TITLE = "title";
	  public static final String COLUMN_AUTHOR = "author";
	  public static final String COLUMN_URL = "url";
	  public static final String COLUMN_LYRICS = "lyrics";
	
	
	private final Context myContext;
	private SQLiteDatabase myDataBase;
	
	public MySQLiteHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}
	
	
	
	public void createDataBase() throws IOException{
		boolean dbExist = checkDataBase();
		
		if (dbExist){
			
		}else{
			try {
					copyDataBase();
			} catch(IOException e){
				throw new Error("Error copying database");
			}
		}		
	}
	
	
//Check if 	db exists to avoid re-copying file each time app is opened
// returns true if exist, false otherwise	
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		
		try{
				String myPath = DB_PATH + DB_NAME;
				checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException e){
			//database doesnt exist yet!
		}
		
		if(checkDB != null){
			checkDB.close();
		}
		return checkDB !=null ? true : false;
	}
	
	
// Copies external db from assets-folder to the just created empty	database in the system folder
// Copying is done by transferring bytestream
	private void copyDataBase() throws IOException{
		
		//Open local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		
		//Path to the just created empty db
		String outFileName = DB_PATH  + DB_NAME;
		
		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while((length = myInput.read(buffer))>0){
				myOutput.write(buffer, 0, length);
		}
		
		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	
	
	private void openDataBase() throws SQLException{
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		//TODO check whether myDataBase is supposed to be a field
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}


	@Override
	public synchronized void close() {
		if(myDataBase != null)
			myDataBase.close();
		super.close();
	}


	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
		
	
	
}
