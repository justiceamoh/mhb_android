package edu.dartmouth.mhb;

public class Utilities {
	
	public static String getFirstLine(String str){
		String[] lines = str.split(System.getProperty("line.separator"));
		return lines[0];
	}
}
