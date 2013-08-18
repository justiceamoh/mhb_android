package edu.dartmouth.mhb;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HymnArrayAdapter extends ArrayAdapter<Hymn> {
	private final Context context;
	private final List<Hymn> hymns;

	public HymnArrayAdapter(Context context, List<Hymn> hymns) {
		super(context, R.layout.hymnresult, hymns);
		this.context = context;
		this.hymns = hymns;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.hymnresult, parent, false);
		TextView number = (TextView) rowView.findViewById(R.id.snumber);
		TextView title = (TextView) rowView.findViewById(R.id.stitle);
		TextView author = (TextView) rowView.findViewById(R.id.sauthor);

		number.setText("MHB " + String.valueOf(hymns.get(position).getId()));
		author.setText(hymns.get(position).getAuthor());
		title.setText(hymns.get(position).getTitle());

		return rowView;
	}
}