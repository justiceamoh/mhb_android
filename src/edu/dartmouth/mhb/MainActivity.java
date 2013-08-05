package edu.dartmouth.mhb;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	DBHelper myDBHelper;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
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
     	str_val = "sample string";
    	((TextView) findViewById(R.id.editNumber)).setText(str_val);
    	
    	//Set Title from db
     	str_val = "sample string";
    	((TextView) findViewById(R.id.editTitle)).setText(str_val);
    
  
    	//Set Author from db
    	str_val = "sample string";
    	((TextView) findViewById(R.id.editAuthor)).setText(str_val);

    	
    	//Set Hymn text from db
    	str_val = "sample string";
    	((TextView) findViewById(R.id.textHymn)).setText(str_val);
    	
    	// Making a "toast" informing the user the profile is saved.
		Toast.makeText(getApplicationContext(),"Loaded",Toast.LENGTH_SHORT).show();    
    }
    
    
}
