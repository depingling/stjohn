package com.cleanwise.service.api.session;  

import javax.ejb.*;
import java.sql.*;
import java.rmi.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.*;
import java.math.BigDecimal;


/**
 *Place for misc EJB calls and the managment of very simple entitys that do not
 *have or warrent their own beans.
 *@author Durval Vieira
 */
public class RequestBean extends BusEntityServicesAPI
{

    /**
     *Requiered by EJB
     */
    public void ejbCreate() throws CreateException, RemoteException {}


    /**
     *Lists all of the bus entitys by type.  Will return their customized 
     *aggregate object for known type (BuildingServicesContractorViewVector for example).
     *This method will restrict by the supplied store ids.
     */
    public List listAll(IdVector storeIds, String pBusEntityTypeCd) 
        throws RemoteException
    {
        
        BusEntityDataVector bedv = null;
        Connection con = null;
        try
        {
            con = getConnection();
            if(storeIds != null && storeIds.size() > 0){
                BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
                crit.setStoreBusEntityIds(storeIds);
                bedv = BusEntityDAO.getBusEntityByCriteria(con, crit,pBusEntityTypeCd);
            }else{
                bedv = getAllBusEntities(con,pBusEntityTypeCd);
            }
            if ( pBusEntityTypeCd.equals
                 (RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR)
                 )
            {
                BuildingServicesContractorViewVector resl =
                    new BuildingServicesContractorViewVector();
        
                resl = mkBSCDetail(con, bedv);
                return resl;
            }
        }
        catch(Exception e)
        {
            throw processException(e);
        }
        finally
        {
            closeConnection(con);
        }
        return bedv;
    }

    
    /**
     *updates a given entity in the system.
     *Supported types are:
     *@see RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR
     *<pre>
     *Hashtable req = new Hashtable();
     *req.put("busEntityTypeCd", RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
     *req.put("id",1234);
     *req.put("bscName","the name");
     *req.put("bscDesc","the description");
     *req.put("bscOrderFaxNumber","555-1212");
     *req.put("userName","user doing update");
     *req.put("storeId",new Integer(1));
     *requestEJB.updateInfo(req);
     *</pre>
     *@param pReqTable a Hashtable containing the requested information to update
     */
    public int updateInfo(Hashtable pReqTable) throws RemoteException
    {
        if ( null == pReqTable )
        {
            throw new RemoteException("Null request");
        }
        
        String rexc = "";
        Connection con = null;
        try
        {
            con = getConnection();
        String busEntityType = (String)pReqTable.get("busEntityTypeCd");
        if ( busEntityType != null && 
            busEntityType.equals
        (RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR))
        {
            String
                bid = (String)pReqTable.get("id"),
                bscName = (String)pReqTable.get("bscName"),
                bscDesc = (String)pReqTable.get("bscDesc"),
                bscFaxNumber = (String)pReqTable.get("bscOrderFaxNumber"),
                username = (String)pReqTable.get("userName");
                
            Integer storeId = (Integer)pReqTable.get("storeId");
            
            return setBSCDetail(con, bid, bscName, bscDesc,
                                bscFaxNumber,storeId, username);
        }
        else
        {
            rexc = "Unrecogized request, busEntityType="
            + busEntityType;
        }
               }
        catch(Exception e)
        {
            throw processException(e);
        }
        finally
        {
            closeConnection(con);
        }
        if ( rexc.length() > 0 ) 
        {
            throw new RemoteException(rexc);
        }
 
    return -1;
    }

    /**
     *Return a list of SiteView objects that belong to the entity specified in the
     *supplied Hashtable.
     *Supported types are:
     *bscid
     *<pre>
     *Hashtable req = new Hashtable();
     *req.put("bscid", 1234);
     *requestEJB.getSitesFor(req);
     *</pre>
     */
    public ArrayList getSitesFor(Hashtable pReqTable) throws RemoteException
    {
        if ( null == pReqTable )
        {
            throw new RemoteException("Null request");
        }
        
        Connection con = null;
        try
        {
            con = getConnection();
            String bscid = (String)pReqTable.get("bscid");
            if ( null == bscid )
            {
                throw new RemoteException("Null bscid in request");
            }
            
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, bscid);
            IdVector bids = BusEntityAssocDataAccess.selectIdOnly
                (con,BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
            
            QueryRequest qr = new QueryRequest();
            qr.filterBySiteIdList(bids);

            return BusEntityDAO.getSiteCollection(con, qr);

        }
        catch(Exception e)
        {
            throw processException(e);
        }
        finally
        {
            closeConnection(con);
        }
        
    }
    
    /**
     *Utility method that should return OK: plus some random debug infomation.
     *This method is designed to be used for applications that wish to check that
     *the EJB container is operational before proceeeding.  An example would be if an 
     *application wanted to download a file and then process it, if the EJB container 
     *is not running that the application should stop before downloading the file.
     */
    public String appStatus() throws RemoteException
    {
	String st = "OK: " + new java.util.Date() + " : "
	    + java.lang.Thread.currentThread().getName();
	return st;
    }
    
    /**
     *Updates the database with the new tax information
     */
   // public void updateTaxRateCityPostalCodeData (CityPostalCodeData pPostalCodeData,BigDecimal pTaxRate)throws RemoteException{
   //     Connection conn = null;
   //     try{
   //         conn = getConnection();
   //         //TaxUtil.updateTaxRateCityPostalCodeData(conn,pPostalCodeData, pTaxRate);
   //         TaxUtilAvalara.updateTaxRateCityPostalCodeData(conn,pPostalCodeData, pTaxRate);
   //     }catch(Exception e){
   //         throw processException(e);
   //     }finally{
   //         closeConnection(conn);
   //     }
   // }
    
     /**
     *Retrieves a list of zipcodes ordered by priority in which to process.  Empty first then last modified.
     */
   // public CityPostalCodeDataVector getPostalCodesToProcessSalesTaxRates(int maxNumberToReturn) throws RemoteException
   // {
   //     Connection conn = null;
   //     try{
   //         conn = getConnection();
   //         //return TaxUtil.getPostalCodesToProcessSalesTaxRates(conn,maxNumberToReturn);
   //         return TaxUtilAvalara.getPostalCodesToProcessSalesTaxRates(conn,maxNumberToReturn);
   //     }catch(Exception e){
   //         throw processException(e);
   //     }finally{
   //        closeConnection(conn);
   //     }
   // }
    
    
    /**
     * Returns the prefered store for the requested domain name (of the form www.example.com)
     * @param domain the domain of the request in the form of www.example.com
     * @return the ApplicationDomainNameData object that is configured to serve this domain or null if it was not found
     * @throws RemoteException
     */
    public ApplicationDomainNameData getApplicationDomain(String domain) throws RemoteException{
    	Connection conn = null;
    	try{
    		conn = getConnection();
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            crit.setSearchForInactive(false);
            crit.setSearchName(domain);
            crit.setSearchNameType(BusEntitySearchCriteria.EXACT_MATCH);
            BusEntityDataVector domains = BusEntityDAO.getBusEntityByCriteria(conn,crit,RefCodeNames.BUS_ENTITY_TYPE_CD.DOMAIN_NAME);
            if(domains == null || domains.isEmpty()){
            	return null;
            }
            return BusEntityDAO.getApplicationDomainNameData(conn,(BusEntityData)domains.get(0));
    	}catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    /** 
     * @return the default ApplicationDomainNameData object
     * @throws RemoteException
     */
    public ApplicationDomainNameData getDefaultApplicationDomain() throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            crit.setSearchForInactive(false);
            crit.addPropertyCriteria(RefCodeNames.PROPERTY_TYPE_CD.DEFAULT,Boolean.TRUE.toString());
            BusEntityDataVector domains = BusEntityDAO.getBusEntityByCriteria(conn,crit,RefCodeNames.BUS_ENTITY_TYPE_CD.DOMAIN_NAME);
            if(domains == null || domains.isEmpty()){
                return null;
            }
            return BusEntityDAO.getApplicationDomainNameData(conn,(BusEntityData)domains.get(0));
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public String getBusEntityType(int pBusEntityId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityData busEntity = BusEntityDataAccess.select(conn, pBusEntityId);
            return busEntity.getBusEntityTypeCd();
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }
}
