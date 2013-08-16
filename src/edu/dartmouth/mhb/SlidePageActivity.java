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
	
	//    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_page);
        
        List<Fragment> fragments = getFragments();
        
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SlidePageAdapter(getFragmentManager(), fragments);
        mPager.setAdapter(mPagerAdapter);
        
        
        //TODO: reset action bar for each page fragment?
    }
    
    private List<Fragment> getFragments(){
    	Hymn hymn = new Hymn();
    	List<Fragment> fList = new ArrayList<Fragment>();
    	fList.add(SlidePageFragment.newInstance(hymn));
    	
    	
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

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
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