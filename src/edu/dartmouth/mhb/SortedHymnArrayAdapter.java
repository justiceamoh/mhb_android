package edu.dartmouth.mhb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortedHymnArrayAdapter extends ArrayAdapter<Hymn> implements SectionIndexer{
	private final Context context;
	private final ArrayList<Hymn> hymns;
	private final int sortType;

	HashMap<String, Integer> alphaIndexer;
	String[] sections;

	public SortedHymnArrayAdapter(Context context, ArrayList<Hymn> hymns,
			int sortType) {
		super(context, R.layout.contents_list_item, hymns);
		this.context = context;
		this.hymns = hymns;
		this.sortType = sortType;

		alphaIndexer = new HashMap<String, Integer>();
		int size = hymns.size();
		for (int x = 0; x < size; x++){
			String ch;
			switch (sortType) {
				case Globals.SORT_TITLE:
				default:
					ch = hymns.get(x).getTitle();
					break;
				case Globals.SORT_AUTHOR:
					ch = hymns.get(x).getAuthor();
					break;
				case Globals.SORT_FIRSTLINE:
					ch = Utilities.getFirstLine(hymns.get(x)
							.getLyrics());
					break;
			}	
			ch = ch.substring(0,1).toUpperCase();
			alphaIndexer.put(ch,x);
		}

		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.contents_list_item, parent,
				false);

		TextView text = (TextView) rowView.findViewById(R.id.cItemText);
		TextView number = (TextView) rowView.findViewById(R.id.cItemNumber);
		number.setText(String.valueOf(hymns.get(position).getId()));		
		switch (sortType) {
			case Globals.SORT_TITLE:
			default:
				text.setText(hymns.get(position).getTitle());
				break;
			case Globals.SORT_AUTHOR:
				text.setText(hymns.get(position).getAuthor());
				break;
			case Globals.SORT_FIRSTLINE:
				String temp = Utilities.getFirstLine(hymns.get(position)
						.getLyrics());
				text.setText(temp);
				break;
		}
		


		return rowView;

	}

	@Override
	public int getPositionForSection(int section) {
		return alphaIndexer.get(sections[section]);
	}

	@Override
	public int getSectionForPosition(int position) {
		return 1;
	}

	@Override
	public Object[] getSections() {
		return sections;
	}

}
