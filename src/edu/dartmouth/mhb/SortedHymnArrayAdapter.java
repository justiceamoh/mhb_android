package edu.dartmouth.mhb;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SortedHymnArrayAdapter extends ArrayAdapter<Hymn> {
	private final Context context;
	private final ArrayList<Hymn> hymns;
	private final int sortType;

	public SortedHymnArrayAdapter(Context context, ArrayList<Hymn> hymns,
			int sortType) {
		super(context, R.layout.contents_list_item, hymns);
		this.context = context;
		this.hymns = hymns;
		this.sortType = sortType;
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

}
