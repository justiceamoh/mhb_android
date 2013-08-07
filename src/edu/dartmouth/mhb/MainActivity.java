package edu.dartmouth.mhb;


import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	MySQLiteHelper myDBHelper;
	private HymnsDataSource datasource;
	private int index;
	private List<Hymn> hymns;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        datasource = new HymnsDataSource(this);
        datasource.open();
        
        index = 0;
        hymns = datasource.getAllHymns();
    }

    @Override
    protected void onResume(){
    	datasource.open();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	datasource.close();
    	super.onPause();
    }
    
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
       //TODO check API level for getAction
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_author:
                return true;
                
             case R.id.action_title:
                return true;
                
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
    
    
    
    
    public void onLoadClicked(View v){
    	String str_val;
    	
    	//Set MHB no. from db
     	str_val = String.valueOf(hymns.get(index).getId());
    	((TextView) findViewById(R.id.textNumber)).setText("MHB "+str_val);
    	
    	//Set Title from db
     	str_val = hymns.get(index).getTitle();
    	((TextView) findViewById(R.id.textTitle)).setText(str_val);
    
  
    	//Set Author from db
    	str_val = hymns.get(index).getAuthor();
    	((TextView) findViewById(R.id.textAuthor)).setText(str_val);
    	
    	//Set Lyrics from db
    	str_val = hymns.get(index).getLyrics();
    	((TextView) findViewById(R.id.textLyrics)).setText(str_val);
    	
    	// Making a "toast" informing the user the profile is saved.
		Toast.makeText(getApplicationContext(),"Next",Toast.LENGTH_SHORT).show(); 
		
		index = ++index%10;
		
    }
    
    
}
