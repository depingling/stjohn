package com.cleanwise.service.api.value;

import java.util.HashMap;
import java.util.Iterator;

import java.util.ArrayList;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

public class ApplicationDomainNameData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -6922606558987832641L;
	private BusEntityData applicationDomainName;
	private BusEntityData defaultStore;
	private HashMap myProperties = new HashMap();
	
	/**
	 * domain name that this applicationDomainNameData object encompuses
	 * Shortcut to getApplicationDomainName.getShortDesc()
	 */
	public String getDomainName(){
		return applicationDomainName.getShortDesc();
	}
	
//	public ArrayList getProperties() {
//		return new ArrayList(myProperties.values());
//	}
	
//	public PropertyDataVector getProperties() {
//		ArrayList a = new ArrayList(myProperties.values());
//		return (PropertyDataVector)a;
//	}
	public PropertyDataVector getProperties() {
		return (PropertyDataVector)myProperties.values();
	}
	
	public void setProperties(PropertyDataVector properties) {
		Iterator it = properties.iterator();
		while(it.hasNext()){
			PropertyData aProp = (PropertyData) it.next();
			myProperties.put(aProp.getPropertyTypeCd(),aProp);
		}
	}
	public BusEntityData getDefaultStore() {
		return defaultStore;
	}
	public void setDefaultStore(BusEntityData defaultStore) {
		this.defaultStore = defaultStore;
	}
	public BusEntityData getApplicationDomainName() {
		return applicationDomainName;
	}
	public void setApplicationDomainName(BusEntityData applicationDomainName) {
		this.applicationDomainName = applicationDomainName;
	}
	//worker method to deal with the underlyingh hashmap of properties	
	private String getProperty(String name){
		PropertyData aProp = (PropertyData) myProperties.get(name);
		if(aProp == null){
			return null;
		}
		return aProp.getValue();
	}
	//worker method to deal with the underlyingh hashmap of properties
	private void setProperty(String name, Object value){
		PropertyData aProp = (PropertyData) myProperties.get(name);
		if(aProp == null){
			aProp = PropertyData.createValue();
			aProp.setAddBy(applicationDomainName.getModBy());
			aProp.setModBy(applicationDomainName.getModBy());
			aProp.setBusEntityId(applicationDomainName.getBusEntityId());
			aProp.setShortDesc(name);
			aProp.setPropertyTypeCd(name);
			aProp.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			myProperties.put(name,aProp);
		}
		aProp.setValue(value.toString());
	}
	
	public String getSslDomainNam() {
		return getProperty(RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME);
	}
	public void setSslDomainName(String sslDomain) {
		setProperty(RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME,sslDomain);
	}
    
    public boolean isDefaultDomain() {
        return Utility.isTrue(getProperty(RefCodeNames.PROPERTY_TYPE_CD.DEFAULT));
    }
    public void setDefaultDomain(boolean pDefaultDomain) {
        if(pDefaultDomain){
            setProperty(RefCodeNames.PROPERTY_TYPE_CD.DEFAULT,Boolean.TRUE.toString());
        }else{
            setProperty(RefCodeNames.PROPERTY_TYPE_CD.DEFAULT,Boolean.FALSE.toString());
        }
    }
	
	//private constructor
	private ApplicationDomainNameData(){}

	public static ApplicationDomainNameData createValue(){
		return new ApplicationDomainNameData();
	}
}
