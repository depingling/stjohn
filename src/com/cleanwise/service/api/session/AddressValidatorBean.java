package com.cleanwise.service.api.session;

/**
 * Title:        AddressValidatorBean
 * Description:  Bean implementation for AddressValidator Stateless Session Bean
 * Purpose:      Provides access to the table-level AddressValidator methods
 * Copyright:    Copyright (c) 2003
 * Company:      Cleanwise, Inc.
 */

import java.sql.Statement;
import javax.ejb.*;
import java.rmi.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.CityPostalCodeData;
import com.cleanwise.service.api.value.CityPostalCodeDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.dao.CityPostalCodeDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;


public class AddressValidatorBean extends UtilityServicesAPI {
    private static final String TO_REMOVE_MOD_BY_FLAG = "to_rem";
    private static final String REPOPULATE_CITY_POSTAL_CODE_DATA_MARK_DATA_SQL = "UPDATE "+
            CityPostalCodeDataAccess.CLW_CITY_POSTAL_CODE+" set "+CityPostalCodeDataAccess.MOD_BY+"="+TO_REMOVE_MOD_BY_FLAG+
            " WHERE "+CityPostalCodeDataAccess.ENTRY_TYPE+"="+RefCodeNames.CITY_POSTAL_ENTRY_TYPE_CD.LOADER;
    private static final String REPOPULATE_CITY_POSTAL_CODE_DATA_UPDATE_ROW_SQL = "UPDATE "+
            CityPostalCodeDataAccess.CLW_CITY_POSTAL_CODE+" set "+CityPostalCodeDataAccess.MOD_BY+"= loader"+
            " WHERE ";
    
    
    public AddressValidatorBean() {}
    
    public void ejbCreate() throws CreateException, RemoteException {}
    
    /**
     *validates the supplied address.  Address are guaranteed to have valid city,
     *stateProvinceCode, and zipOrPostalCode data.  No guaruntee is made about street address
     *@param AddressData to be validated
     *@returns true if the address is valid, and false if it is not or anything is null
     *@throws RemoteException if anything goes wrong
     */
    public boolean validateAddress(AddressData addr) throws RemoteException
    {
        return validateAddress(addr.getCity(),addr.getStateProvinceCd(), addr.getPostalCode(),addr.getCountryCd());
    }
    
    /**
     *validates the supplied address.  Address are guaranteed to have valid city,
     *stateProvinceCode, and zipOrPostalCode data.  No guaruntee is made about street address
     *@param OrderAddressData to be validated
     *@returns true if the address is valid, and false if it is not or anything is null
     *@throws RemoteException if anything goes wrong
     */
    public boolean validateAddress(OrderAddressData addr) throws RemoteException
    {
        return validateAddress(addr.getCity(),addr.getStateProvinceCd(), addr.getPostalCode(), addr.getCountryCd());
    }
    
    /**
     *validates the supplied address.  Address are guaranteed to have valid city,
     *stateProvinceCode, and zipOrPostalCode data.  No guaruntee is made about street address
     *@param city to be validated
     *@param stateProvinceCode to be validated
     *@param postalCode to be validated
     *@param country country if null this is not added to search criteria
     *@returns true if the address is valid, and false if it is not or anything is null
     *@throws RemoteException if anything goes wrong
     */
    private boolean validateAddress(String city, String stateProvinceCode, String zipOrPostalCode, String country)
    throws RemoteException
    {
        
        if(!(Utility.isSet(country) && RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equalsIgnoreCase(country))){
            logInfo("*******NOT VALIDATING THIS ADDRESS DUE TO COUNTRY: " + country);
            return true;
        }
        Connection conn = null;
        try{
            conn = getConnection();
            
            //allows for disabling address validation if necessary.  This may eventually
            //want to be taken out, or moved to a more static context, but at least initially
            //this seems to be a useful feature to have.
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,"validateAddress.enabled");
            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            PropertyDataVector pdv = PropertyDataAccess.select(conn,crit);
            if(pdv.size() > 0){
                PropertyData prop = (PropertyData) pdv.get(0);
                if (prop.getValue().equals("false")){
                    return true;
                }
            }
            int maxLen = 9;
            int currLen = zipOrPostalCode.length();
            if(currLen > 5 && zipOrPostalCode.charAt(5) == '-'){
                maxLen = 10;
            }
            if(currLen == 5 || currLen == maxLen){
                zipOrPostalCode = zipOrPostalCode.substring(0,5);
            }else{
                return false;
            }
            
            crit = new DBCriteria();
            //The data that is loaded only goes as high as 20 chars in length, further more
            //a 20 char match is almost certainly good enough to get a positive match. 
            //This will alow for positive results of:
            //Truth Or Consequenceasdfasdfasdf
            //where "Truth Or Consequence" is a valid city (which it is in New Mexico)
            if (city != null && city.length() > 20 ){
                city = city.substring(0,20);
            }
            crit.addEqualToIgnoreCase(CityPostalCodeDataAccess.CITY,city);
            crit.addEqualToIgnoreCase(CityPostalCodeDataAccess.STATE_PROVINCE_CD,stateProvinceCode);
            //use ignore case in case of canadian zipcodes...seems like the vertex data uses
            //the first couple charachters of the zipcode so we will use like.  This has the
            //advantage of validating a 5 digit zipcode against 9 digit zipcode data
            crit.addCondition(CityPostalCodeDataAccess.POSTAL_CODE + "||'%' LIKE '"+zipOrPostalCode.toUpperCase()+"%'");
            //if country is supplied use it, otherwise ignore
            if(Utility.isSet(country)){
                crit.addEqualToIgnoreCase(CityPostalCodeDataAccess.COUNTRY_CD,country);
            }
            logInfo("Where clause evaluating address data:"+crit.getWhereClause());
            return(CityPostalCodeDataAccess.selectIdOnly(conn,crit).size() > 0);
        }catch (Exception e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Will repopulate the address validation records with the supplied data.  This method completely
     *replaces the existing data, and is ment to be used with a bulk loader.
     *@param CityPostalCodeDataVector to populate
     *@throws RemoteException if anything goes wrong
     */
    public void repopulateCityPostalCodeData(CityPostalCodeDataVector data) throws RemoteException
    {
        Connection conn = null;
        try{
            conn = getConnection();
            Statement stmt = conn.createStatement();
            logInfo("Marking data to remove");
            stmt.executeUpdate(REPOPULATE_CITY_POSTAL_CODE_DATA_MARK_DATA_SQL);
            logDebug("data marked");
            //CityPostalCodeDataAccess.remove(conn,crit);
            //logDebug("removed old data loaded by the loader");
            for (int i=0,len=data.size();i<len;i++){
                CityPostalCodeData pc = (CityPostalCodeData) data.get(i);
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(CityPostalCodeDataAccess.CITY,pc.getCity());
                crit.addEqualTo(CityPostalCodeDataAccess.COUNTRY_CD,pc.getCountryCd());
                crit.addEqualTo(CityPostalCodeDataAccess.COUNTY_CD,pc.getCountyCd());
                crit.addEqualTo(CityPostalCodeDataAccess.POSTAL_CODE,pc.getPostalCode());
                crit.addEqualTo(CityPostalCodeDataAccess.STATE_PROVINCE_CD,pc.getStateProvinceCd());
                crit.addEqualTo(CityPostalCodeDataAccess.STATE_PROVINCE_NAM,pc.getStateProvinceNam());
                crit.addEqualTo(CityPostalCodeDataAccess.ENTRY_TYPE,RefCodeNames.CITY_POSTAL_ENTRY_TYPE_CD.LOADER);
                String updSql = REPOPULATE_CITY_POSTAL_CODE_DATA_UPDATE_ROW_SQL + crit.getWhereClause();
                int updCt = stmt.executeUpdate(updSql); //update our modby which we are using to track what to delete
                if(updCt == 0){
                    CityPostalCodeDataAccess.insert(conn, pc);
                }
                if (i%5000==0){
                    logInfo("Address Validation Records processed: "+i);
                }
            }
            logInfo("Removing data that was not in the update");
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CityPostalCodeDataAccess.ENTRY_TYPE,RefCodeNames.CITY_POSTAL_ENTRY_TYPE_CD.LOADER);
            crit.addEqualTo(CityPostalCodeDataAccess.MOD_BY,TO_REMOVE_MOD_BY_FLAG);
            CityPostalCodeDataAccess.remove(conn,crit);
            logDebug("removed old data loaded by the loader");
            logInfo("Done processing " + data.size() + " Address Validation Records");
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
}
