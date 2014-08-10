/**
 * 
 */
package edu.dartmouth.mhb;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * @author Junior
 * 
 */
public class GoToNumberActivity extends Activity {
	GridView gridView;
	TextView gotoTitle;
	TextView gotoNumber;

	static final String[] numbers = new String[] { "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "< Del", "0", "Go >" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.ContentTheme);
		setContentView(R.layout.activity_gotonumber);

		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);

		gotoTitle = (TextView) findViewById(R.id.textGotoTitle);
		gotoNumber = (TextView) findViewById(R.id.textGotoNumber);
		gridView = (GridView) findViewById(R.id.gridViewDialer);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.goto_grid_item, numbers);
		gridView.setAdapter(adapter);
		
		
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				switch (position) {
					case 9:  //Backspace selected, delete character
						String str = gotoNumber.getText().toString().trim();
						if (str.length() != 0) {
							str = str.substring(0, str.length() - 1);
							gotoNumber.setText(str);
						}
						break;
						
					case 11:
						break;
					default:
						gotoNumber.append(((TextView) view).getText());
				}

			}

		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
