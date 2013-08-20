package edu.dartmouth.mhb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


public class MainActiivity extends FragmentActivity {
	Context context;
	MySQLiteHelper myDBHelper;
	private HymnsDataSource datasource;
	private List<Hymn> hymns;
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;		    
    private ActionBarDrawerToggle mDrawerToggle;
	
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerMenuTitles;
    
    
    
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	    
		
		setContentView(R.layout.activity_slide_page);

		mTitle = mDrawerTitle = getTitle();
		mDrawerMenuTitles = getResources().getStringArray(R.array.sidebar_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);		
		
        //TODO work on array adapter for drawer items
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_item, mDrawerMenuTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
       
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        
        
        
        
		context = getApplicationContext();
		datasource = new HymnsDataSource(this);
		datasource.open();
		hymns = datasource.getAllHymns();

		List<Fragment> fragments = getFragments();
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new SlidePageAdapter(getFragmentManager(), fragments);
		mPager.setAdapter(mPagerAdapter);

	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		
		int no_hymns = hymns.size();
		for (int i = 0; i < no_hymns; i++) {
			fList.add(SlidePageFragment.newInstance(hymns.get(i)));

		}

		return fList;
	}

	// TODO: menu options and next/previous actions
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		// Do not iconify the widget;expand it by default
		searchView.setIconifiedByDefault(false);

		return super.onCreateOptionsMenu(menu);
	}

	//TODO set for all menu items, to hide when drawer opens
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //TODO Handle action buttons with switch statements
        return super.onOptionsItemSelected(item);

    }	
    
    
    
    
    
	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the
			// system to handle the
			// Back button. This calls finish() on this activity and pops the
			// back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}
	

	private class SlidePageAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments;

		public SlidePageAdapter(FragmentManager fm, List<Fragment> fragments) {
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
	}
	
	
		///////////////////////////////////////
		////  Methods for  DrawerLayout ///////	
		//////////////////////////////////////	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
	    // Create a new fragment and specify the planet to show based on position
	    Fragment fragment = new SidebarMenuFragment();
	    Bundle args = new Bundle();
	    args.putInt(SidebarMenuFragment.ARG_PLANET_NUMBER, position);
	    fragment.setArguments(args);

	    // Insert the fragment by replacing any existing fragment
	    FragmentManager fragmentManager = getFragmentManager();
	    fragmentManager.beginTransaction()
	                   .replace(R.id.content_frame, fragment)
	                   .commit();

	    // Highlight the selected item, update the title, and close the drawer
	    mDrawerList.setItemChecked(position, true);
	    setTitle(mDrawerMenuTitles[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}
	
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
    
    //TODO Create multiple fragments for each sidebar menu
    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class SidebarMenuFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public SidebarMenuFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sidebar, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String sidebar_menu = getResources().getStringArray(R.array.sidebar_array)[i];

            int imageId = getResources().getIdentifier(sidebar_menu.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(sidebar_menu);
            return rootView;
        }
    }
	
	
}