package edu.dartmouth.mhb;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ContentsActivity extends FragmentActivity implements
		ActionBar.TabListener {
	// When requested, this adapter returns a DemoObjectFragment,
	// representing an object in the collection.
	ContentsPagerAdapter mContentsPagerAdapter;
	ViewPager mViewPager;
	List<Fragment> fList;
	
	private static Intent returnIntent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.ContentTheme);
		setContentView(R.layout.activity_contents);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Sorted By:");

		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		
		returnIntent = new Intent(this,MainActivity.class);
		returnIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Create tab fragment lists
		// ViewPager and its adapters use support library
		// fragments, so use getSupportFragmentManager.
		fList = getContentFragments();
		mContentsPagerAdapter = new ContentsPagerAdapter(
				getSupportFragmentManager(), fList);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.tab_pager);
		mViewPager.setAdapter(mContentsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						getActionBar().setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mContentsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mContentsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#55000000")));

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


	public ArrayList<Fragment> getContentFragments() {
		ArrayList<Fragment> frags = new ArrayList<Fragment>();
		Fragment f = new ContentsTitlesFragment();
		frags.add(f);

		f = new ContentsNumbersFragment();
		frags.add(f);

		f = new ContentsAuthorsFragment();
		frags.add(f);

		return frags;
	}

	// Since this is an object collection, use a FragmentStatePagerAdapter,
	// and NOT a FragmentPagerAdapter.
	private class ContentsPagerAdapter extends FragmentStatePagerAdapter {
		private List<Fragment> fragments;

		public ContentsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String str_val = "";
			switch (position) {
			case 0:
				str_val = "Titles";
				break;
			case 1:
				str_val = "First Lines";
				break;
			case 2:
				str_val = "Authors";
				break;
			}

			return str_val;
		}
	}

	public class ContentsTitlesFragment extends Fragment {
		// TODO Sort titles in alphabetical order
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_contents_list,
					container, false);

			// Setup layout here.
			final ListView listview = (ListView) rootView
					.findViewById(R.id.contents_list);

			// Get hymns sorted by titles
			ArrayList<Hymn> hymns = HymnArraySingleton.getInstance()
					.getSortedByTitle();

			SortedHymnArrayAdapter adapter = new SortedHymnArrayAdapter(
					MainActivity.getContext(), hymns, Globals.SORT_TITLE);

			listview.setAdapter(adapter);

			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, final View view,
						int position, long id) {
					Hymn hymn = (Hymn) parent.getItemAtPosition(position);
					setReturnIntent((int) hymn.getId());
				}

			});

			return rootView;
		}
	}

	public class ContentsAuthorsFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_contents_list,
					container, false);
			
			// Setup layout here.
			final ListView listview = (ListView) rootView
					.findViewById(R.id.contents_list);

			// Get hymns sorted by authors
			ArrayList<Hymn> hymns = HymnArraySingleton.getInstance()
					.getSortedByAuthor();

			SortedHymnArrayAdapter adapter = new SortedHymnArrayAdapter(
					MainActivity.getContext(), hymns, Globals.SORT_AUTHOR);

			listview.setAdapter(adapter);

			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, final View view,
						int position, long id) {
					Hymn hymn = (Hymn) parent.getItemAtPosition(position);
					setReturnIntent((int) hymn.getId());
				}

			});

			return rootView;
		}
	}

	public class ContentsNumbersFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_contents_list,
					container, false);
			
			// Setup layout here.
			final ListView listview = (ListView) rootView
					.findViewById(R.id.contents_list);
			
			// Get hymns sorted by authors
			ArrayList<Hymn> hymns = HymnArraySingleton.getInstance()
					.getSortedByFirstLine();

			SortedHymnArrayAdapter adapter = new SortedHymnArrayAdapter(
					MainActivity.getContext(), hymns, Globals.SORT_FIRSTLINE);

			listview.setAdapter(adapter);

			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, final View view,
						int position, long id) {
					Hymn hymn = (Hymn) parent.getItemAtPosition(position);
					setReturnIntent((int) hymn.getId());
				}
			});

			return rootView;
		}
	}
	
	private void setReturnIntent(int value){
		returnIntent.putExtra(Globals.HYMN_ID_EXTRA, value);
		startActivity(returnIntent);
		finish();
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

}