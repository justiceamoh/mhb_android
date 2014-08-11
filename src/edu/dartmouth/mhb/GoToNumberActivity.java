/**
 * 
 */
package edu.dartmouth.mhb;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
	ArrayList<Hymn> hymns;

	private static Intent returnIntent;

	static final String[] numbers = new String[] { "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "<Del", "0", "Go>" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.GoToTheme);
		setContentView(R.layout.activity_gotonumber);

		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);

		returnIntent = new Intent(this, MainActivity.class);
		returnIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		actionbar.setTitle(Globals.GOTONUMBER_TITLE);
		
		hymns = HymnArraySingleton.getInstance().getHymns();

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
				case 9: // Backspace selected, delete character
					String str = gotoNumber.getText().toString().trim();
					if (str.length() != 0) {
						str = str.substring(0, str.length() - 1);
						gotoNumber.setText(str);
					}
					break;

				case 11: // Go To Hymn Number
					int index = Integer
							.valueOf(gotoNumber.getText().toString());
					setReturnIntent(index);
					break;
				default:
					//prevent first character from being zero
					if (!(gotoNumber.getText().toString().isEmpty() && position == 10)) { 																							// zero
						if (gotoNumber.getText().toString().length() < 3) {
							gotoNumber.append(((TextView) view).getText());
						}
					}
				}
				updateUI();
			}

		});

	}

	private void updateUI() {
		if (!gotoNumber.getText().toString().isEmpty()) {
			int number = Integer.valueOf(gotoNumber.getText().toString());

			if (number > 0 && number <= hymns.size()) {
				String title = hymns.get(number-1).getTitle();
				gotoTitle.setText(title);
			} else {
				gotoTitle.setText(null);
				// TODO make open not valid
			}
		} else {
			gotoTitle.setText(null);
		}

	}

	private void setReturnIntent(int hymn_number) {
		returnIntent.putExtra(Globals.HYMN_ID_EXTRA, hymn_number);
		startActivity(returnIntent);
		finish();
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
