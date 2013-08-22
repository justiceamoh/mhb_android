package edu.dartmouth.mhb;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.dartmouth.mhb.R;

public class MenuAboutFragment extends Fragment {
 
    public static Fragment newInstance(Context context) {
    	MenuAboutFragment f = new MenuAboutFragment();
 
        return f;
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_menu_about, null);
        
        
    	//Set test string
    	String str_val = "About";
    	((TextView) root.findViewById(R.id.textView1)).setText(str_val);
        
        
        return root;
    }
 
}