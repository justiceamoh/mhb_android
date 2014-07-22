package edu.dartmouth.mhb;

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SearchActivity extends Activity {
	private ListView mListView;
	private HymnsDataSource datasource;
	private static Intent returnIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		returnIntent = new Intent(this,MainActivity.class);
		returnIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		
		mListView = (ListView) findViewById(R.id.search_list);
		datasource = new HymnsDataSource(this);
		datasource.open();

		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}
	}

	private void doMySearch(String query) {
		Log.d("Event", query);
		final List<Hymn> hymns = datasource.searchHymn((query != null ? query
				.toString() : "@@@@"));

		if (!(hymns.isEmpty())) {

			HymnArrayAdapter adapter = new HymnArrayAdapter(this, hymns);
			mListView.setAdapter(adapter);

			mListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								final View view, int position, long id) {
							Hymn hymn = (Hymn) parent
									.getItemAtPosition(position);

							// TODO Use hymn to create slide fragment
							Log.d(Globals.TAG, hymn.getTitle());
							int value = (int) hymn.getId()-1;
				            returnIntent.putExtra("hymn_id", value);
				            startActivity(returnIntent);

						}

					});

		}

	}
	

}
