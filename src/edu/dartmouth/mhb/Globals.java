package edu.dartmouth.mhb;



// All the global constants are put here
public abstract class Globals {

	// Debugging tag for the whole project
	public static final String TAG = "mhb";
	
	
	// Table schema, column names
	public static final String KEY_TABLE ="hymns";
	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_AUTHOR = "author";
	public static final String KEY_URL = "url";
	public static final String KEY_LYRICS = "lyrics";
	
	
	// Sorted Array Codes for Adapter
	public static final int SORT_TITLE = 1;
	public static final int SORT_AUTHOR = 2;
	public static final int SORT_FIRSTLINE = 3;
	
	
	public static final String HYMN_ID_EXTRA= "hymn_id";
}
