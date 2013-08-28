package edu.dartmouth.mhb;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentsActivity extends FragmentActivity {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    ContentsPagerAdapter mContentsPagerAdapter;
    ViewPager mViewPager;
    List<Fragment> fList;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
                
        //Create tab fragment lists
        fList = new ArrayList<Fragment>();
        Fragment f = new ContentsTitlesFragment();
        fList.add(f);
        
        f = new ContentsNumbersFragment();
        fList.add(f);
        
        f = new ContentsAuthorsFragment();
        fList.add(f);
        
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mContentsPagerAdapter = new ContentsPagerAdapter(getSupportFragmentManager(),
        		fList);
        mViewPager = (ViewPager) findViewById(R.id.tab_pager);
        mViewPager.setAdapter(mContentsPagerAdapter);
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
        	String str_val="";
        	switch(position){
        	case 0:
        		str_val = "Titles";
        		break;
        	case 1:
        		str_val = "MHB Nos.";
        		break;
        	case 2:
        		str_val = "Authors";
        		break;
        	}
        	
            return str_val;
        }
    }

    // Instances of this class are fragments representing a single
    // object in our collection.
//    public static class ContentsFragment extends Fragment {
//        public static final String ARG_OBJECT = "object";
//
//        @Override
//        public View onCreateView(LayoutInflater inflater,
//                ViewGroup container, Bundle savedInstanceState) {
//            // The last two arguments ensure LayoutParams are inflated
//            // properly.
//            View rootView = inflater.inflate(
//                    R.layout.fragment_collection_object, container, false);
//            Bundle args = getArguments();
//            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                    Integer.toString(args.getInt(ARG_OBJECT)));
//            return rootView;
//        }
//    }

    
    
    public static class ContentsTitlesFragment extends Fragment{
    	
    }
    
    public static class ContentsNumbersFragment extends Fragment{
    	
    }    
    
    public static class ContentsAuthorsFragment extends Fragment{
    	
    }
    

}