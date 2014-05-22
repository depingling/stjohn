package com.cleanwise.service.api.util;

public class XMLUtil {
	
	/**
	 * Removes DTD from XML
	 * @param xml 
	 * @return
	 */	
	public static String removeDTD(String xml){
		String result;
		int fromIndex = xml.indexOf("<!DOCTYPE");
		int toIndex = xml.indexOf(">", fromIndex);
		
		result = xml.substring(0, fromIndex);
		result = result + xml.substring(toIndex + 1);
		return result;	
	}
}
