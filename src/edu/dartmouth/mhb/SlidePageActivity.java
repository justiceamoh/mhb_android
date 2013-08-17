package edu.dartmouth.mhb;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class SlidePageActivity extends FragmentActivity {
	
	MySQLiteHelper myDBHelper;
	private HymnsDataSource datasource;
	private List<Hymn> hymns;
	
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_page);
        
        datasource = new HymnsDataSource(this);
        datasource.open();        
        hymns = datasource.getAllHymns();
        
        
        List<Fragment> fragments = getFragments();        
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SlidePageAdapter(getFragmentManager(), fragments);
        mPager.setAdapter(mPagerAdapter);

        //Reset action bar for each page
//        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                invalidateOptionsMenu();
//            }
//        });        
        

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
       
    
    private List<Fragment> getFragments(){
    	List<Fragment> fList = new ArrayList<Fragment>();
    	  for(int i=0; i<5; i++){
    		   	fList.add(SlidePageFragment.newInstance(hymns.get(i)));

          }
     	
    	
    	return fList;
    }
    
// TODO: menu options and next/previous actions    
    
    
    
           
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
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
}