package edu.dartmouth.mhb;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FormatDialog extends Activity {
	private SeekBar brightBar;
	private int brightness;
	private ContentResolver cResolver;
	private Window window;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title from dialog
		setContentView(R.layout.activity_format);
		
		
		// Brightness Variables
		brightBar = (SeekBar) findViewById(R.id.brightBar);
		cResolver = getContentResolver();
		window = getWindow();
		brightBar.setMax(255);
		brightBar.setKeyProgressIncrement(1);
	
        try
        {
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        } 
        catch (SettingNotFoundException e) 
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
        
        // Set brightness level based on system brightness
        brightBar.setProgress(brightness);
        
        
    	//Register OnSeekBarChangeListener, so it can actually change values
        brightBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
        {
            public void onStopTrackingTouch(SeekBar seekBar) 
            {
                //Set the system brightness using the brightness variable value
                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
                //Get the current window attributes
                LayoutParams layoutpars = window.getAttributes();
                //Set the brightness of this window
                layoutpars.screenBrightness = brightness / (float)255;
                //Apply attribute changes to this window
                window.setAttributes(layoutpars);
            }

            public void onStartTrackingTouch(SeekBar seekBar) 
            {
                //Nothing handled here
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
            {
                //Set the minimal brightness level
                //if seek bar is 20 or any value below
                if(progress<=20)
                {
                    //Set the brightness to 20
                    brightness=20;
                }
                else //brightness is greater than 20
                {
                    //Set brightness variable based on the progress bar 
                    brightness = progress;
                }
            }     
        });
	}	
	
	
	
	
}
