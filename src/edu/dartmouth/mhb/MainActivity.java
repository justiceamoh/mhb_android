package edu.dartmouth.mhb;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onLoadClicked(View v){
    	String str_val;
    	
    	//Set MHB no. from db
     	str_val = String.valueOf(hymns.get(index).getId());
    	((TextView) findViewById(R.id.editNumber)).setText(str_val);
    	
    	//Set Title from db
     	str_val = hymns.get(index).getTitle();
    	((TextView) findViewById(R.id.editTitle)).setText(str_val);
    
  
    	//Set Author from db
    	str_val = hymns.get(index).getAuthor();
    	((TextView) findViewById(R.id.editAuthor)).setText(str_val);
    	
    	//Set Lyrics from db
    	str_val = hymns.get(index).getLyrics();
    	((TextView) findViewById(R.id.textLyrics)).setText(str_val);
    	
    	// Making a "toast" informing the user the profile is saved.
		Toast.makeText(getApplicationContext(),"Loaded",Toast.LENGTH_SHORT).show(); 
		
		index = ++index%10;
		
    }
    
    
}
