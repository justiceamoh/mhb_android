package edu.dartmouth.mhb;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SlidePageFragment extends Fragment {
	
	
	private Hymn hymn;
	
	//Construct a new page for a given hymn
	public static SlidePageFragment create(Hymn hymn){
		SlidePageFragment fragment = new SlidePageFragment();
		Bundle args = new Bundle();
		args.putLong(Globals.KEY_ID, hymn.getId());
		args.putString(Globals.KEY_AUTHOR, hymn.getAuthor());
		args.putString(Globals.KEY_TITLE, hymn.getAuthor());
		args.putString(Globals.KEY_LYRICS, hymn.getLyrics());
		fragment.setArguments(args);
		return fragment;
		
	}
	
	public SlidePageFragment(){
		
	}
	
    @Override
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
        ((TextView) rootView.findViewById(android.R.id.text1)).setText(str_val);

    	//Set Title from db
     	str_val = hymn.getTitle();
    	((TextView) rootView.findViewById(R.id.textTitle)).setText(str_val);
    
    	//Set Author from db
    	str_val = hymn.getAuthor();
    	((TextView) rootView.findViewById(R.id.textAuthor)).setText(str_val);
    	
    	//Set Lyrics from db
    	str_val = hymn.getLyrics();
    	((TextView) rootView.findViewById(R.id.textLyrics)).setText(str_val);
             
        return rootView;
    }
    
    
    
}