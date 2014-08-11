package edu.dartmouth.mhb;

import java.util.Comparator;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Hymn implements Parcelable {
	private long id;
	private String title;
	private String author;
	private String url;
	private String lyrics;

	
	//Constructors for Hymn class
	public Hymn(){
		
		this.id = Long.MIN_VALUE;
		this.title = null;
		this.author=null;
		this.url = null;
		this.lyrics=null;		
	}
	
	public Hymn(Long id, String title, String author, String url, String lyrics){
		
		this.id = id;
		this.title = title;
		this.author=author;
		this.url = url;
		this.lyrics=lyrics;		
	}
	
	/**
     * This will be used only by the MyCreator
     * @param source
     */
    public Hymn(Parcel source){
          /*
           * Reconstruct from the Parcel
           */
          Log.v(Globals.TAG, "ParcelData(Parcel source): time to put back parcel data");
          id = source.readLong();
          title = source.readString();
          author = source.readString();
          url=source.readString();
          lyrics=source.readString();
    }
	
	
	
  	public long getId() {
	    return this.id;
	 }
 
	// Get Functions	 
	 public String getTitle(){
		return this.title;
	 }

	 public String getAuthor(){
		return this.author;
	 }
	 
	 public String getUrl(){
		return this.url;
	 }

	 public String getLyrics(){
		return this.lyrics;
	 }

	 
	 // Set Functions
	 public void setId(long id){
		 this.id = id;
	 }
	 
	 public void setTitle(String val){
		 this.title = val;
	 }

	 public void setAuthor(String val){
		 this.author = val;
	 }
	 
	 public void setUrl(String val){
		 this.lyrics = val;
	 }

	 public void setLyrics(String val){
		 this.lyrics = val;
	 }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	      Log.v(Globals.TAG, "writeToParcel..."+ flags);
	      dest.writeLong(id);
	      dest.writeString(title);
	      dest.writeString(author);
	      dest.writeString(url);
	      dest.writeString(lyrics);
	}	 

	/**
	 * It will be required during un-marshaling data stored in a Parcel
	 */
	public class MyCreator implements Parcelable.Creator<Hymn> {
	      public Hymn createFromParcel(Parcel source) {
	            return new Hymn(source);
	      }
	      public Hymn[] newArray(int size) {
	            return new Hymn[size];
	      }
	}



	// Comparators
	public static Comparator<Hymn> HymnTitleComparator = new Comparator<Hymn>() {
		@Override
		public int compare(Hymn a, Hymn b) {
			return a.getTitle().compareTo(b.getTitle());
		}
	};

	public static Comparator<Hymn> HymnIdComparator = new Comparator<Hymn>() {
		@Override
		public int compare(Hymn a, Hymn b) {
			return (int) (a.getId() - b.getId());
		}
	};

	public static Comparator<Hymn> HymnAuthorComparator = new Comparator<Hymn>() {
		@Override
		public int compare(Hymn a, Hymn b) {
			return a.getAuthor().compareTo(b.getAuthor());
		}
	};	
	
	public static Comparator<Hymn> HymnFirstLineComparator = new Comparator<Hymn>() {
		@Override
		public int compare(Hymn a, Hymn b) {
			return Utilities.getFirstLine(a.getLyrics()).compareTo(
					Utilities.getFirstLine(b.getLyrics()));
		}
	};	
}
