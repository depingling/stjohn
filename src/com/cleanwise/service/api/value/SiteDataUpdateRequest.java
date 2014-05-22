package com.cleanwise.service.api.value;

import java.util.ArrayList;
import java.util.List;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.Utility;

public class SiteDataUpdateRequest extends ValueObject {
	private String entityKey;
	private List siteDataEntries = new ArrayList();
	
	private void addAt(List aList, Object value, int idx){
		while(aList.size() < idx){
			aList.add(null);
		}
		aList.add(idx,value);
	}
	
	/**
	 * Returns an ordered list of the site data entries that need updating
	 */
	public List getSiteDataEntries(){
		return siteDataEntries;
	}
	
	public String getEntityKey() {
		return entityKey;
	}
	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}
	public String getSiteFieldData1() {
		return (String) Utility.safeGetListElement(siteDataEntries,0,null);
	}
	public void setSiteFieldData1(String val) {
		addAt(siteDataEntries,val,0);
	}
	
	public String getSiteFieldData2() {
		return (String) Utility.safeGetListElement(siteDataEntries,1,null);
	}
	public void setSiteFieldData2(String val) {
		addAt(siteDataEntries,val,1);
	}
	public String getSiteFieldData3() {
		return (String) Utility.safeGetListElement(siteDataEntries,2,null);
	}
	public void setSiteFieldData3(String val) {
		addAt(siteDataEntries,val,2);
	}
	public String getSiteFieldData4() {
		return (String) Utility.safeGetListElement(siteDataEntries,3,null);
	}
	public void setSiteFieldData4(String val) {
		addAt(siteDataEntries,val,3);
	}
	public String getSiteFieldData5() {
		return (String) Utility.safeGetListElement(siteDataEntries,4,null);
	}
	public void setSiteFieldData5(String val) {
		addAt(siteDataEntries,val,4);
	}
	public String getSiteFieldData6() {
		return (String) Utility.safeGetListElement(siteDataEntries,5,null);
	}
	public void setSiteFieldData6(String val) {
		addAt(siteDataEntries,val,5);
	}
	public String getSiteFieldData7() {
		return (String) Utility.safeGetListElement(siteDataEntries,6,null);
	}
	public void setSiteFieldData7(String val) {
		addAt(siteDataEntries,val,6);
	}
	public String getSiteFieldData8() {
		return (String) Utility.safeGetListElement(siteDataEntries,7,null);
	}
	public void setSiteFieldData8(String val) {
		addAt(siteDataEntries,val,7);
	}
	public String getSiteFieldData9() {
		return (String) Utility.safeGetListElement(siteDataEntries,8,null);
	}
	public void setSiteFieldData9(String val) {
		addAt(siteDataEntries,val,8);
	}
	public String getSiteFieldData10() {
		return (String) Utility.safeGetListElement(siteDataEntries,9,null);
	}
	public void setSiteFieldData10(String val) {
		addAt(siteDataEntries,val,9);
	}
	
	
}
