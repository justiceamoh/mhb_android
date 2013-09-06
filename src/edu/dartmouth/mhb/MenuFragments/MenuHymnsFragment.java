package edu.dartmouth.mhb.MenuFragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.dartmouth.mhb.Globals;
import edu.dartmouth.mhb.Hymn;
import edu.dartmouth.mhb.MainActivity;
import edu.dartmouth.mhb.R;

public class MenuHymnsFragment extends Fragment {
	private ArrayList<Hymn> hymns;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	
	public static Fragment newInstance(Context context) {
		MenuHymnsFragment f = new MenuHymnsFragment();
		return f;
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.fragment_menu_hymns, null);
		
		hymns = MainActivity.hymns;
		
		 List<Fragment> fragments = getFragments();
		 // Instantiate a ViewPager and a PagerAdapter.
		 mPager = (ViewPager) root.findViewById(R.id.pager);
		 mPagerAdapter = new SlidePageAdapter(getFragmentManager(),
		 fragments);
		 mPager.setAdapter(mPagerAdapter);
		 
		 
		 
		 Button goNextButton = (Button) root.findViewById(R.id.button_next);
		    goNextButton.setOnClickListener(new OnClickListener() {

		     @Override
		           public void onClick(View view) {
		               mPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
		           }
		        });
		
		 Button goPrevButton = (Button) root.findViewById(R.id.button_prev);
			 goPrevButton.setOnClickListener(new OnClickListener() {

			  @Override
			       public void onClick(View view) {
			           mPager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
			       }
			    });
   	    		    		    
		return root;
	}
	
	
	
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		int no_hymns = hymns.size();
		for (int i = 0; i < no_hymns; i++) {
			fList.add(SlidePageFragment.newInstance(hymns.get(i)));
			
		}
		return fList;
	}
	
	private int getItem(int i) {
	    int a = mPager.getCurrentItem();
	    i += a;
	    return i;
	}
	
//	public void goToPage(int pageno) {
//	    // Do something in response to button click
//		mPager.setCurrentItem(pageno, true);
//		
////		yourViewPager.setCurrentItem(page, smoothScroll);
//	}
//	
//	

	
	private class SlidePageAdapter extends FragmentStatePagerAdapter {
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
	
	
	
	
	public static class SlidePageFragment extends Fragment {
		
		private Hymn hymn;
	    private Boolean showBar = true;
		
		//Construct a new page for a given hymn
		public static SlidePageFragment newInstance(Hymn hymn){
			SlidePageFragment fragment = new SlidePageFragment();
			Bundle args = new Bundle();
			args.putLong(Globals.KEY_ID, hymn.getId());
			args.putString(Globals.KEY_AUTHOR, hymn.getAuthor());
			args.putString(Globals.KEY_TITLE, hymn.getTitle());
			args.putString(Globals.KEY_LYRICS, hymn.getLyrics());
			fragment.setArguments(args);
			return fragment;
			
		}
		
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        hymn = new Hymn();
	        hymn.setId(getArguments().getLong(Globals.KEY_ID));
	        hymn.setAuthor(getArguments().getString(Globals.KEY_AUTHOR));
	        hymn.setTitle(getArguments().getString(Globals.KEY_TITLE));
	        hymn.setLyrics(getArguments().getString(Globals.KEY_LYRICS));

	    }
		
		
		
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        ViewGroup rootView = (ViewGroup) inflater.inflate(
	                R.layout.fragment_slide_page, container, false);

	        String str_val;
	        
	    	//Set MHB no. from db
	     	str_val = "MHB " + String.valueOf(hymn.getId());        
	        ((TextView) rootView.findViewById(R.id.textNumber)).setText(str_val);

	    	//Set Title from db
	     	str_val = hymn.getTitle();
	    	((TextView) rootView.findViewById(R.id.textTitle)).setText(str_val);
	    
	    	//Set Author from db
	    	str_val = hymn.getAuthor();
	    	((TextView) rootView.findViewById(R.id.textAuthor)).setText(str_val);
	    	
	    	//Set Lyrics from db
	    	str_val = hymn.getLyrics();
	    	((TextView) rootView.findViewById(R.id.textLyrics)).setText(str_val);


	        //TODO move action bar toggling into MainActivity
	        TextView tv = (TextView) rootView.findViewById(R.id.textLyrics);
	        tv.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	                if(showBar) // Toggle action bar visiblity
	                    getActivity().getActionBar().hide();
	                else
	                    getActivity().getActionBar().show();

	                showBar = !showBar;

	            }
	        });


	        return rootView;
	    }
	    
	    	    
	}	
}