package com.cleanwise.service.api.value;

import java.util.HashMap;
import java.util.Iterator;

import java.util.ArrayList;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

public class DomainData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2869279504047505438L;
	private BusEntityData mBusEntity;;
	private PropertyData mSSLName;
    private BusEntityAssocData mStoreAssoc;
    private BusEntityAssocData mDefaultStoreAssoc;
    private String mDefaultStoreName;
    
	public DomainData(BusEntityData pBusEntity, PropertyData pSSLName, BusEntityAssocData pStoreAssoc,
			BusEntityAssocData pDefaultStoreAssoc, String pDefaultStoreName) {
        this.mBusEntity = (pBusEntity != null)
        	? pBusEntity : BusEntityData.createValue();
        this.mSSLName = (pSSLName != null)
        	? pSSLName : PropertyData.createValue();
        this.mStoreAssoc = (pStoreAssoc != null)
        	? pStoreAssoc : BusEntityAssocData.createValue();
        this.mDefaultStoreAssoc = (pDefaultStoreAssoc != null)
        	? pDefaultStoreAssoc : BusEntityAssocData.createValue();
        this.mDefaultStoreName = (pDefaultStoreName != null)
    		? pDefaultStoreName : "";
	}

	private DomainData() {
		
	}
	
    public BusEntityData getBusEntity() {
        return mBusEntity;
    }

    public void setBusEntity(BusEntityData businessEntityData) {
        this.mBusEntity = businessEntityData;
    }
    
    public PropertyData getSSLName() {
        return mSSLName;
    } 
    
    public BusEntityAssocData getStoreAssoc() {
        return mStoreAssoc;
    }
    
    public BusEntityAssocData getDefaultStoreAssoc() {
        return mDefaultStoreAssoc;
    }
    
    public String getDefaultStoreName() {
        return mDefaultStoreName;
    }

    public void setDefaultStoreName(String defaultStoreName) {
        this.mDefaultStoreName = defaultStoreName;
    }
    
    //	/**
//	 * domain name that this applicationDomainNameData object encompuses
//	 * Shortcut to getApplicationDomainName.getShortDesc()
//	 */
//	public String getDomainName(){
//		return applicationDomainName.getShortDesc();
//	}
	
//	public ArrayList getProperties() {
//		return new ArrayList(myProperties.values());
//	}
	
//	public PropertyDataVector getProperties() {
//		ArrayList a = new ArrayList(myProperties.values());
//		return (PropertyDataVector)a;
//	}
//	public PropertyDataVector getProperties() {
//		return (PropertyDataVector)myProperties.values();
//	}
	
//	public void setProperties(PropertyDataVector properties) {
//		Iterator it = properties.iterator();
//		while(it.hasNext()){
//			PropertyData aProp = (PropertyData) it.next();
//			myProperties.put(aProp.getPropertyTypeCd(),aProp);
//		}
//	}
//	public BusEntityData getDefaultStore() {
//		return defaultStore;
//	}
//	public void setDefaultStore(BusEntityData defaultStore) {
//		this.defaultStore = defaultStore;
//	}
//	public BusEntityData getApplicationDomainName() {
//		return applicationDomainName;
//	}
//	public void setApplicationDomainName(BusEntityData applicationDomainName) {
//		this.applicationDomainName = applicationDomainName;
//	}
	//worker method to deal with the underlyingh hashmap of properties	
//	private String getProperty(String name){
//		PropertyData aProp = (PropertyData) myProperties.get(name);
//		if(aProp == null){
//			return null;
//		}
//		return aProp.getValue();
//	}
	//worker method to deal with the underlyingh hashmap of properties
//	private void setProperty(String name, Object value){
//		PropertyData aProp = (PropertyData) myProperties.get(name);
//		if(aProp == null){
//			aProp = PropertyData.createValue();
//			aProp.setAddBy(applicationDomainName.getModBy());
//			aProp.setModBy(applicationDomainName.getModBy());
//			aProp.setBusEntityId(applicationDomainName.getBusEntityId());
//			aProp.setShortDesc(name);
//			aProp.setPropertyTypeCd(name);
//			aProp.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//			myProperties.put(name,aProp);
//		}
//		aProp.setValue(value.toString());
//	}
	
//	public String getSslDomainNam() {
//		return getProperty(RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME);
//	}
//	public void setSslDomainName(String sslDomain) {
//		setProperty(RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME,sslDomain);
//	}
//    
//
//    public void setDefaultDomain(boolean pDefaultDomain) {
//        if(pDefaultDomain){
//            setProperty(RefCodeNames.PROPERTY_TYPE_CD.DEFAULT,Boolean.TRUE.toString());
//        }else{
//            setProperty(RefCodeNames.PROPERTY_TYPE_CD.DEFAULT,Boolean.FALSE.toString());
//        }
//    }
	

	public static DomainData createValue(){
		return new DomainData();
	}
}
