/**
 * Title:        Remittance
 * Description:  Remote Interface for Remittance Stateless Session Bean
 * Purpose:      Provides access to the services for managing the Remittance information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      Cleanwise, Inc.
 * @author       Brook Stevens, CleanWise, Inc.
 */
package com.cleanwise.service.api.session;

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.RemittanceCriteriaView;
import com.cleanwise.service.api.value.RemittanceData;
import com.cleanwise.service.api.value.RemittanceDataVector;
import com.cleanwise.service.api.value.RemittanceDescView;
import com.cleanwise.service.api.value.RemittanceDescViewVector;
import com.cleanwise.service.api.value.RemittancePropertyData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.RemittanceDetailData;
import com.cleanwise.service.api.value.RemittanceDetailDataVector;
import com.cleanwise.service.api.value.RemittanceDetailDescViewVector;
import com.cleanwise.service.api.value.RemittancePropertyDataVector;
import com.cleanwise.service.api.util.DataNotFoundException;



/**
 * Remote interface for the <code>Remittance</code> stateless session bean.
 * @author  bstevens
 */
public interface Remittance extends javax.ejb.EJBObject {
    
    /**
     * Adds a purchase order to the database
     *
     * @param pRemittanceData a <code>RemittanceData</code> value
     * @param pUser username
     * @return a <code>RemittanceData</code> value
     * @exception RemoteException if an error occurs
     */
    public RemittanceData addRemittance(RemittanceData pRemittanceData, String pUser)
    throws RemoteException;


    /**
     * Updates the Remittance information values.
     * @param pRemittanceData  the RemittanceData.
     * @param pUser username
     * @return a <code>RemittanceData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public RemittanceData updateRemittance(RemittanceData pRemittanceData,String pUser)
    throws RemoteException;
    
    /**
     *Return remittanceProperty objects that have a remittance, but no remittance
     *detail associations.  i.e. the remittanceDetailId = 0 and the 
     *remittanceId = pRemittanceId
     *@param pRemittanceId the remittance id
     *@param pPropertyType the property type you want to filter for, may be left null
     *      to return all properties.
     *@return a <code>RemittancePropertyDataVector</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittancePropertyDataVector getRemittanceOnlyPropertiesByRemittanceId(int pRemittanceId,String pPropertyType)
    throws RemoteException;
    
    /**
     *Return a populated RemittanceDescViewVector object
     *@param pRemittanceCriteria a RemittanceCriteriaView object
     *@param pPopulateDetail if true the RemittanceDetailDataVector propertie
     *  will be populated, otherwise it will be left as a zero length list.
     *@return a <code>RemittanceDescViewVector</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDescViewVector getRemittanceDescList
    (RemittanceCriteriaView pRemittanceCriteria,boolean pPopulateDetail)
    throws RemoteException;
    
    /**
     *Return a populated RemittanceDescView object
     *@param pRemittanceId a remittance id
     *@return a <code>RemittanceDescView</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDescView getRemittanceDescById(int pRemittanceId)
    throws RemoteException,DataNotFoundException;
    
     /**
     *Returns a populated IdVector based on some supplied criteria
     *@param pRemittanceCriteria
     *@return a <code>IdVector</code>
     *@throws            RemoteException Required by EJB 1.0
     */
    public IdVector getRemittanceIds(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException;
    
     /**
     *Returns populated RemittanceData objects based on some supplied criteria
     *@param pRemittanceCriteria
     *@return a <code>RemittanceDataVector</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDataVector getRemittanceData(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException;
    
    /**
     *Returns populated RemittanceData object.
     *@param pRemittanceId 
     *@return a <code>RemittanceData</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceData getRemittanceDataById(int pRemittanceId)
    throws RemoteException,DataNotFoundException;
    
     /**
     *Updates the RemittanceDetail information values to be used by the request.
     *@param RemittanceDetailData to update or insert
     *@param pUser
     *@return a <code>RemittanceDetailData</code> value
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDetailData updateRemittanceDetail(RemittanceDetailData pRemittanceDetailData,String pUser)
    throws RemoteException;
    
     /**
     * Adds a Remittance Detail to the database
     *
     * @param pRemittanceDetailData a <code>RemittanceDetailData</code> value
     * @param pUser username
     * @return a <code>RemittanceDetailData</code> value
     * @exception RemoteException if an error occurs
     */
    public RemittanceDetailData addRemittanceDetail(RemittanceDetailData pRemittanceDetailData, String pUser)
    throws RemoteException;
    
     /**
     *Returns populated RemittanceDetailData objects based on the supplied criteria
     *@param pRemittanceCriteria search criteria
     *@return a <code>RemittanceDetailDataVector</code> list
     *@throws            RemoteException Required by EJB 1.0
     */
    public RemittanceDetailDataVector getRemittanceDetailData(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException;
    
    /**
     *Returns populated RemittanceDetailDescView objects based on the supplied criteria
     *@param pRemittanceCriteria search criteria
     *@return a <code>RemittanceDetailDescViewVector</code> list
     *@throws RemoteException Required by EJB 1.0
     */
    public RemittanceDetailDescViewVector getRemittanceDetailDescList(RemittanceCriteriaView pRemittanceCriteria)
    throws RemoteException;
    
    /**
     *Adds or updates a RemittanceProperty object
     *@returns the updated object
     *@param pProperty a <code>RemittancePropertyData</code> object.  If the 
     *  remittancePropertyId property is 0 it will be inserted, otherwise it 
     *  will be updated.
     *@param pUser username
     *@throws RemoteException on any error
     */
    public RemittancePropertyData addUpdateRemittanceProperty(RemittancePropertyData pProperty, String pUser)
    throws RemoteException;
    
    /**
     *Returns a list of all of the RemittancePropertyData where the 
     *remittanceDetailId = 0, remittanceId = 0 and the type is an error.
     *This indicates a line that was not able to be processed by the loader.
     *@throws RemoteException on any error
     */
    public RemittancePropertyDataVector getUnparsableRemittanceData()
    throws RemoteException;
    
    /**
     *Reprocess a given remittance detail record.  Does any validation it
     *can, resets all of the type codes in the remittance properties that
     *were in an error state, and resets the state of the remittance detail
     *to be ready to send to lawson on the next load (either through the 
     *erp integration or the system batch processing)
     *@param pRemittanceDetailId the remittance detail id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceDetailId supplied was invalid
     */
    public void reprocessRemittanceDetail(int pRemittanceDetailId,String pUserName)
    throws RemoteException,DataNotFoundException;
    
    /**
     *clears all errors for a given remittance record.  Does any validation it
     *can, resets all of the type codes in the remittance properties that
     *were in an error state, and resets the state of the remittance detail
     *objects to be sent to lawson 
     *@param pRemittanceId the remittance id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceId supplied was invalid
     */
    public void clearRemittanceErrors(int pRemittanceId,String pUserName)
    throws RemoteException,DataNotFoundException;
    
    /**
     *Updates the status of a remittance and it's detail to be processed through lawson
     *the next time the remittance loader is run.
     *@param pRemittanceId the remittance id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceId supplied was invalid
     */
    public void reprocessRemittance(int pRemittanceId,String pUserName)
    throws RemoteException,DataNotFoundException;
    
    
    /**
     *A remittance can only be deleted if it is ina pending state and has no detail 
     *records associated with it.  This type of invoice may be deleted because of the
     *unique way that Factoring works, and CIT in particular.  A payment will come in 
     *and will be applied to an exsisting payment in the system.  This exsisting payment
     *then looses it's significance, as for remittance processing, and accounting processing
     *it has no meaning.
     *@param pRemittanceId the remittance id to reprocess
     *@throws RemoteException on any error
     *@throws DataNotFoundException if the pRemittanceId supplied was invalid
     */
    public void deleteRemittance(int pRemittanceId)
    throws RemoteException,DataNotFoundException;
}
