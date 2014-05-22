package com.cleanwise.service.api.session;

/**
 * Title:        Troubleshooter
 * Description:  Remote Interface for Troubleshooter Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Troubleshooter information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

public interface Troubleshooter extends javax.ejb.EJBObject
{

    /**
     * Get call data vector according to the callSearchCriteriaData
     * @param pCallSearchCriteriaData the criteria data to search the calls
     * @throws            RemoteException
     */
    public CallDataVector getCallCollection(CallSearchCriteriaData pCallSearchCriteria)
        throws RemoteException;
    
    /**
     * Get call desc data vector according to the callSearchCriteriaData
     * @param pCallSearchCriteriaData the criteria data to search the calls
     * @throws            RemoteException
     */
    public CallDescDataVector getCallDescCollection(CallSearchCriteriaData pCallSearchCriteria)
        throws RemoteException;
    
    /**
     * Get call desc data according to the pCallId
     * @param pCallId the call Id to search the call
     * @throws            RemoteException
     */
    public CallDescData getCallDesc(int pCallId)
        throws RemoteException, DataNotFoundException;
    
    /**
     * Describe <code>addCall</code> method here.
     *
     * @param pCallData a <code>CallData</code> value
     * @return a <code>CallData</code> value
     * @exception RemoteException if an error occurs
     */
    public CallData addCall(CallData pCallData)
	throws RemoteException;
    
    /**
     * Updates the Call information values to be used by the request.
     * @param pCallData  the OrderItemDetailData .
     * @return a <code>CallData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public CallData updateCall(CallData pCallData)
	throws RemoteException;
    
    
    /**
     * Add a property to a particular call
     * @param pCallPropertyData the call property data to set
     * @return an <code>CallPropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public CallPropertyData addCallProperty(CallPropertyData pCallPropertyData)
	throws RemoteException;
    
    /**
     * Get call property data according to the callPropertyId
     * @param pCallPropertyId the call property Id to search the call property
     * @return an <code>CallPropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public CallPropertyData getCallProperty(int pCallPropertyId)
        throws RemoteException, DataNotFoundException;
    
    /**
     * Get call property data vector according to the orderStatusId
     * @param pCallId the call Id to search the call property
     * @param pCallPropertyTypeCd the property type code
     * @return an <code>CallPropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public CallPropertyDataVector getCallPropertyCollection(int pCallId,
                                                            String pCallPropertyTypeCd)
        throws RemoteException, DataNotFoundException;
    

    /**
     * Get knowledge data vector according to the knowledgeSearchCriteriaData
     * @param pKnowledgeSearchCriteriaData the criteria data to search the knowledges
     * @throws            RemoteException
     */
    public KnowledgeDataVector getKnowledgeCollection(KnowledgeSearchCriteriaData pKnowledgeSearchCriteria)
        throws RemoteException;
    
    
    /**
     * Get knowledge desc data vector according to the knowledgeSearchCriteriaData
     * @param pKnowledgeSearchCriteriaData the criteria data to search the knowledges
     * @throws            RemoteException
     */
    public KnowledgeDescDataVector getKnowledgeDescCollection(KnowledgeSearchCriteriaData pKnowledgeSearchCriteria)
        throws RemoteException;

    /**
     * Get Knowledge data according to the pKnowledgeId
     * @param pKnowledgeId the Knowledge Id to search the Knowledge
     * @throws            RemoteException
     */
    public KnowledgeData getKnowledge(int pCallId)
        throws RemoteException, DataNotFoundException;

    /**
     * Describe <code>addKnowledge</code> method here.
     *
     * @param pKnowledgeData a <code>KnowledgeData</code> value
     * @return a <code>KnowledgeData</code> value
     * @exception RemoteException if an error occurs
     */
    public KnowledgeData addKnowledge(KnowledgeData pKnowledgeData)
	throws RemoteException;
    
    /**
     * Updates the Knowledge information values to be used by the request.
     * @param pKnowledgeData  the OrderItemDetailData .
     * @return a <code>KnowledgeData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public KnowledgeData updateKnowledge(KnowledgeData pKnowledgeData)
	throws RemoteException;
    
    /**
     * Add a property to a particular knowledge
     * @param pKnowledgePropertyData the knowledge property data to set
     * @return an <code>KnowledgePropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgePropertyData addKnowledgeProperty(KnowledgePropertyData pKnowledgePropertyData)
	throws RemoteException;
    
    /**
     * Get knowledge property data according to the knowledgePropertyId
     * @param pKnowledgePropertyId the knowledge property Id to search the knowledge property
     * @return an <code>KnowledgePropertyData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgePropertyData getKnowledgeProperty(int pKnowledgePropertyId)
        throws RemoteException, DataNotFoundException;
    
    /**
     * Get knowledge property data vector according to the orderStatusId
     * @param pKnowledgeId the knowledge Id to search the knowledge property
     * @param pKnowledgePropertyTypeCd the property type code
     * @return an <code>KnowledgePropertyDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgePropertyDataVector getKnowledgePropertyCollection(int pKnowledgeId,
                                                            String pKnowledgePropertyTypeCd)
        throws RemoteException, DataNotFoundException;
            

    /**
     * Add a content to a particular knowledge
     * @param pKnowledgeContentData the knowledge content data to set
     * @return an <code>KnowledgeContentData</code> value
     * @exception RemoteException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgeContentData addKnowledgeContent(KnowledgeContentData pKnowledgeContentData)
	throws RemoteException;
    
    /**
     * Get knowledge content data according to the knowledgeContentId
     * @param pKnowledgeContentId the knowledge content Id to search the knowledge content
     * @return an <code>KnowledgeContentData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgeContentData getKnowledgeContent(int pKnowledgeContentId)
        throws RemoteException, DataNotFoundException;
    
    /**
     * Get knowledge content data vector according to the orderStatusId
     * @param pKnowledgeId the knowledge Id to search the knowledge content
     * @param pKnowledgeContentTypeCd the content type code
     * @return an <code>KnowledgeContentDataVector</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     * @exception            RemoteException, DataNotFoundException
     */
    public KnowledgeContentDataVector getKnowledgeContentCollection(int pKnowledgeId)
        throws RemoteException, DataNotFoundException;
    
    
    
}
