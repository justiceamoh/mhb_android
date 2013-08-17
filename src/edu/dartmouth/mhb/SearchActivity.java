package edu.dartmouth.mhb;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

public class SearchActivity extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {
	private ListView mListView;
	private SearchView searchView;
	private HymnsDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_view);

		
		 // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//		searchView = (SearchView) findViewById(R.id.menu_search);
//		
//		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);

		mListView = (ListView) findViewById(R.id.list);
		datasource = new HymnsDataSource(this);
		datasource.open();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (datasource != null) {
			datasource.close();
		}
	}

	public boolean onQueryTextChange(String newText) {
		showResults(newText + "*");
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		showResults(query + "*");
		return false;
	}

	public boolean onClose() {
		showResults("");
		return false;
	}

	private void showResults(String query) {
		Cursor cursor = datasource.searchHymn((query != null ? query.toString()
				: "@@@@"));

		if (cursor != null) {
			// Specify the columns we want to display in the result
			String[] from = new String[] { Globals.KEY_ID, Globals.KEY_TITLE,
					Globals.KEY_AUTHOR };

			// Specify the Corresponding layout elements where we want the
			// columns to go
			int[] to = new int[] { R.id.snumber, R.id.stitle, R.id.sauthor };

			// TODO find a better way to handle adapter
			// Create a simple cursor adapter for the definitions and apply them
			// to the ListView
			SimpleCursorAdapter hymns = new SimpleCursorAdapter(this,
					R.layout.hymnresult, cursor, from, to);
			mListView.setAdapter(hymns);

			// Define the on-click listener for the list items
			mListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// Get the cursor, positioned to the corresponding row in
					// the result set
					Cursor cursor = (Cursor) mListView
							.getItemAtPosition(position);

					// Get hymn from cursor
					Hymn hymn = datasource.cursorToHymn(cursor);

					// TODO Use hymn to create slide fragment
					Log.d(Globals.TAG, hymn.getTitle());

					searchView.setQuery("", true);
				}
			});

		}

	}

}
