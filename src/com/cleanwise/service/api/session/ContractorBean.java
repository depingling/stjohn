package com.cleanwise.service.api.session;

/**
 * Title:        ContractorBean
 * Description:  Bean implementation for Contractor Stateless Session Bean
 * Purpose:      Provides access to the services for managing the Contractor information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 *
 */

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * <code>Contractor</code> stateless session bean.
 *
 * @author <a href="mailto:tbesser@cleanwise.com"></a>
 */
public class ContractorBean extends BusEntityServicesAPI
{
    /**
     * Creates a new <code>ContractorBean</code> instance.
     *
     */
    public ContractorBean() {}

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}

     /**
     * Get all Contractors that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the Contractor
     * @return a <code>ContractorDataVector</code> of matching Contractors
     * @exception RemoteException if an error occurs
     */
    
    public BuildingServicesContractorViewVector getContractorByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException {
	BuildingServicesContractorViewVector contractorVec = new BuildingServicesContractorViewVector();
	Connection conn = null;
	try {
	    conn = getConnection();

	    BusEntityDataVector busEntityVec =
                BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
	    Iterator iter = busEntityVec.iterator();
	    
	    
	    contractorVec = mkBSCDetail(conn, busEntityVec);
        return contractorVec;
	    
	   
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
	//return ContractorVec;
    }
    
    


    public List listAll(IdVector storeIds, String pBusEntityTypeCd,
    		String fieldValue, String fieldSearchType, boolean showInactiveFl) 
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
                if (fieldSearchType.equals("id")) {
                    Integer id = new Integer(fieldValue);
                    crit.setSearchId(fieldValue);
                  } else {
                    crit.setSearchName(fieldValue);
                    // Lookup by name.  Two assumptions are made: 1) user may
                    // have entered the whole name or the initial part of the
                    // name; 2) the search is case insensitive.
                    if (fieldSearchType.equals("nameBegins")) {
                      crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                    } else { // nameContains
                      crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                    }
                  }
                  crit.setSearchForInactive(showInactiveFl);
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
     * Gets the Contractor information values to be used by the request.
     * @param pContractorId an <code>int</code> value
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return ContractorData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if Contractor with pContractorId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    
    public BuildingServicesContractorView getContractorForStore(int pContractorId, IdVector pStoreIds, boolean pInactiveFl)
                                                                 throws RemoteException, DataNotFoundException {

        if (pStoreIds!=null&&pStoreIds.size()>0&&pContractorId > 0) {
            BusEntitySearchCriteria pCrit = new BusEntitySearchCriteria();
            pCrit.setStoreBusEntityIds(pStoreIds);
            pCrit.setSearchId(pContractorId);
            pCrit.setSearchForInactive(pInactiveFl);
            BuildingServicesContractorViewVector contractorDV = getContractorByCriteria(pCrit);
            if (contractorDV != null && contractorDV.size() > 0) {
                if (contractorDV.size() == 1) {
                    return (BuildingServicesContractorView) contractorDV.get(0);
                } else {
                    throw new RemoteException("Multiple Contractors for store id : " + pStoreIds);
                }
            }
        }
        throw new DataNotFoundException("Contractor is not found");
    }

    

    /**
     *updates a given entity in the system.
     *Supported types are:
     *@see RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR
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
                throw new RemoteException("Null contractor id in request");
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
    

}



