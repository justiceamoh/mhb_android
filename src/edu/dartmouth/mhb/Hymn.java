package edu.dartmouth.mhb;

public class Hymn {
	private long id;
	private String title;
	private String author;
	private String url;
	private String lyrics;

	
	//Constructor for Hymn class
	public Hymn(){
		
		id = Long.MIN_VALUE;
		title = null;
		author=null;
		url = null;
		lyrics=null;		
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

	
}
