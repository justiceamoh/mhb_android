package edu.dartmouth.mhb;

import java.util.ArrayList;
import java.util.Collections;

public class HymnArraySingleton {
	private static HymnArraySingleton instance = null;
	private int currentHymnId;
	public static ArrayList<Hymn> hymns;
	public static ArrayList<Hymn> sortedbyAuthor;
	public static ArrayList<Hymn> sortedbyFirstLine;
	public static ArrayList<Hymn> sortedbyTitle;

	
	
	private HymnsDataSource datasource;
	
	
	private HymnArraySingleton() {
		// Constructor hidden because this is a singleton
		datasource = new HymnsDataSource(MainActivity.getContext());
		datasource.open();
		hymns = datasource.getAllHymns();
		
		sortedbyTitle = (ArrayList<Hymn>) hymns.clone();
		sortedbyFirstLine = (ArrayList<Hymn>) hymns.clone();
		sortedbyAuthor = (ArrayList<Hymn>) hymns.clone();
		
		Collections.sort(sortedbyAuthor,Hymn.HymnAuthorComparator);
		Collections.sort(sortedbyFirstLine,Hymn.HymnFirstLineComparator);
		Collections.sort(sortedbyTitle,Hymn.HymnTitleComparator);
	}
	
	public static HymnArraySingleton getInstance() {
		if (instance == null) {
			// Create the instance if it doesn't exist
			instance = new HymnArraySingleton();
		}
		return instance;
	}


	public ArrayList<Hymn> getHymns() {
		return hymns;
	}
	
	public ArrayList<Hymn> getSortedByAuthor(){
		return sortedbyAuthor;
	}	

	public ArrayList<Hymn> getSortedByTitle(){
		return sortedbyTitle;
	}
	
	public ArrayList<Hymn> getSortedById(){
		return hymns;
	}

	public ArrayList<Hymn> getSortedByFirstLine(){
		return sortedbyFirstLine;
	}
	
	public int getCurrentHymnId(){
		return this.currentHymnId;
	}
	
	public void setCurrentHymnId(int hymnId){
		this.currentHymnId = hymnId;
	}
	
	
	public void incrementId(){
		if(this.currentHymnId <= hymns.size()) this.currentHymnId++;
	}
	
	public void decrementId(){
		if(this.currentHymnId > 1) this.currentHymnId--;
	}
}
