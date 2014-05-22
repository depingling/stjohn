package com.cleanwise.service.api.session;

/**
 * Title:        ContractInformation
 * Description:  Remote Interface for ContractInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating contract information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.*;
import com.cleanwise.service.api.value.*;

public interface ContractInformation extends javax.ejb.EJBObject
{

  /**
   * Gets the array-like contract vector values to be used by the request.
   * @param pBusEntityId  the business entity identifier
   * @return ContractDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractDataVector getContractsCollectionByBusEntity(int pBusEntityId)
      throws RemoteException;
      
  /**
   * Gets the array-like contract vector values to be used by the request.
   * @param pCatalogId  
   * @return ContractDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractDataVector getContractsCollectionByCatalog(int pCatalogId)
      throws RemoteException;

  
    /**
     *  Gets the array-like contract vector values to be used by the request.
     *
     *@param  pUserId      the customer identifier
     *@return                   CatalogDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDataVector getContractsCollectionByUser(int pUserId)
        throws RemoteException;
  
    /**
     *  Gets the array-like contract description vector values to be used by the request.
     *
     *@param  pUserId      the customer identifier
     *@return                   CatalogDescDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDescDataVector getContractDescsCollectionByUser(int pUserId)
        throws RemoteException ;
    
    /**
     *  Gets the array-like contract description vector values to be used by the request.
     *
     * @param pName a <code>String</code> value with contract name or pattern
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
     *        BEGINS_WITH_IGNORE_CASE
     *@param  pUserId      the customer identifier
     *@return                   CatalogDescDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDescDataVector getContractDescsCollectionByUser(String pName, int pMatch,
						    int pUserId)
	throws RemoteException;
    
    
  /**
   * Gets the array-like contract vector values to be used by the request
   * (Search by contract type).
   * @param pContractTypeCd  the contract type code
   * @return ContractDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractDataVector getContractsCollectionByType(String pContractTypeCd)
      throws RemoteException;

  /**
   * Gets contract information values to be used by the request.
   * @param pContractId  the contract identifier
   * @return ContractData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractData getContract(int pContractId)
      throws RemoteException;

  /**
   * Gets the array-like contract item vector values to be used by the request
   * @param pContractTypeCd  the contract type code
   * @return ContractItemDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemDataVector getContractItemsCollection(int pContractId)
      throws RemoteException;

  /**
   * Gets contract item information values to be used by the request.
   * @param pContractId  the contract identifier
   * @param pContractItemId  the contract item identifier
   * @return ContractItemData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemData getContractItem(int pContractId,
            int pContractItemId)
      throws RemoteException;


  /**
   * determines if the contract effective date is within the current date.
   * @param pContractId  the contract identifier
   * @param pEffDate  the user effective date
   * @param pNow  the current date
   * @return true if the contract effective date is equal to or after the current date.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean checkContractEffDate(int pContractId, Date pEffDate, Date pNow)
      throws RemoteException;

  /**
   * determines if the contract expiration date is within the current date.
   * @param pContractId  the contract identifier
   * @param pExpDate  the user expiration date
   * @param pNow  the current date
   * @return true if the contract expiration date is equal to or before the current date.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean checkContractExpDate(int pContractId, Date pExpDate, Date pNow)
      throws RemoteException;

  /**
   * determines if the contract quote expiration date is within the current date.
   * @param pContractId  the contract identifier
   * @param pExpDate  the contract quote expiration date
   * @param pNow  the current date
   * @return true if the contract expiration date is equal to or before the current date.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean checkContractQuoteExpDate(int pContractId, Date pExpDate, Date pNow)
      throws RemoteException;

  /**
   * determines if the contract acceptance date is within the current date.
   * @param pContractId  the contract identifier
   * @param pAcceptanceDate  the contract acceptance date
   * @param pNow  the current date
   * @return true if the contract acceptance date is equal to or before the current date.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean checkContractAcceptanceDate(int pContractId, 
            Date pAcceptanceDate, Date pNow)
      throws RemoteException;

  /**
   * determines if the contract item effective date is within the current date.
   * @param pContractId  the contract identifier
   * @param pContractItemId  the contract item identifier
   * @param pEffDate  the contract item effective date
   * @param pNow  the current date
   * @return true if the contract item effective date is equal to or after the current date.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean checkContractItemEffDate(int pContractId, 
            int pContractItemId, Date pEffDate, Date pNow)
      throws RemoteException;

  /**
   * determines if the contract item expiration date is within the current date.
   * @param pContractId  the contract identifier
   * @param pContractItemId  the contract item identifier
   * @param pExpDate  the contract item  expiration date
   * @param pNow  the current date
   * @return true if the contract item expiration date is equal to or before the current date.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean checkContractItemExpDate(int pContractId, 
            int pContractItemId, Date pExpDate, Date pNow)
      throws RemoteException;

 /**
   * determines if the contract is hide price.
   * @param pContractId  the contract identifier
   * @return true if the HidePriceInd equals true
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean isHidePrice(int pContractId)
      throws RemoteException;

 /**
   * determines if contractItemsOnlyInd is true.
   * @param pContractId  the contract identifier
   * @return true if the ContractItemsOnlyInd equals true
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean isContractItemsOnly(int pContractId)
      throws RemoteException;

   

  
}
