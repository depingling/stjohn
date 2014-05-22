package com.cleanwise.service.api.session;  
import java.rmi.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import com.cleanwise.service.api.value.*;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *Place for misc EJB calls and the managment of very simple entitys that do not
 *have or warrent their own beans.
 *@author Durval Vieira
 */
public interface Request extends javax.ejb.EJBObject
{
    /**
     *Lists all of the bus entitys by type.  Will return their customized 
     *aggregate object for known type (BuildingServicesContractorViewVector for example).
     *This method will restrict by the supplied store ids.
     */
    public List listAll(IdVector storeIds, String pBusEntityTypeCd) 
        throws RemoteException;
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
    public int updateInfo(Hashtable pReqTable) throws RemoteException;
    
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
    public ArrayList getSitesFor(Hashtable pReqTable) throws RemoteException;
    /**
     *Utility method that should return OK: plus some random debug infomation.
     *This method is designed to be used for applications that wish to check that
     *the EJB container is operational before proceeeding.  An example would be if an 
     *application wanted to download a file and then process it, if the EJB container 
     *is not running that the application should stop before downloading the file.
     */
    public String appStatus() throws RemoteException;
    
    /**
     *Updates the database with the new tax information
     */
   // public void updateTaxRateCityPostalCodeData (CityPostalCodeData pPostalCodeData,BigDecimal pTaxRate)throws RemoteException;
    
    /**
     *Retrieves a list of zipcodes ordered by priority in which to process.  Empty first then last modified.
     */
   // public CityPostalCodeDataVector getPostalCodesToProcessSalesTaxRates(int maxNumberToReturn) throws RemoteException;
    
    
    /**
     * Returns the prefered store for the requested domain name (of the form www.example.com)
     * @param domain the domain of the request in the form of www.example.com
     * @return the ApplicationDomainNameData object that is configured to serve this domain or null if it was not found
     * @throws RemoteException
     */
    public ApplicationDomainNameData getApplicationDomain(String domain) throws RemoteException;
    
    /** 
     * @return the default ApplicationDomainNameData object
     * @throws RemoteException
     */
    public ApplicationDomainNameData getDefaultApplicationDomain() throws RemoteException;

    public String getBusEntityType(int pBusEntityId) throws RemoteException;
}
