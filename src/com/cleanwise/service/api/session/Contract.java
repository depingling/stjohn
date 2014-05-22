package com.cleanwise.service.api.session;

/**
 * Title:        Contract
 * Description:  Remote Interface for Contract Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving contract information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       CleanWise, Inc.
 */

import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

public interface Contract extends javax.ejb.EJBObject {

  /**
   *  There are 6 options when getting Stores by name:
   *  name exactly matches, name begins with, name contains
   *  and then each of those options ignoring the case
   */
  public static final int EXACT_MATCH = 0;
  public static final int BEGINS_WITH = 1;
  public static final int CONTAINS = 2;
  public static final int EXACT_MATCH_IGNORE_CASE = 3;
  public static final int BEGINS_WITH_IGNORE_CASE = 4;
  public static final int CONTAINS_IGNORE_CASE = 5;


  /**
   * Describe <code>getContract</code> method here.
   *
   * @param pContractId an <code>int</code> value
   * @return a <code>ContractData</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractData getContract(int pContractId) throws RemoteException,
    DataNotFoundException;


  /**
   * Describe <code>getContractDesc</code> method here.
   *
   * @param pContractId an <code>int</code> value
   * @return a <code>ContractData</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDescData getContractDesc(int pContractId) throws
    RemoteException, DataNotFoundException;

  public ContractDescData getContractDesc(int pContractId, int pStoreId) throws
  RemoteException, DataNotFoundException;

  /**
   * Get all contracts that match the given name.  The arguments specify
   * whether the name is interpreted as a pattern or exact match.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @return a <code>ContractDataVector</code> of matching contracts
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getContractByName(String pName, int pMatch) throws
    RemoteException;


  /**
   * Get all contract descs that match the given name.  The arguments specify
   * whether the name is interpreted as a pattern or exact match.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @return a <code>ContractDataVector</code> of matching contracts
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescByName(String pName, int pMatch) throws
    RemoteException;

  public ContractDescDataVector getContractDescByName(String pName,int pStoreId, int pMatch) throws
  RemoteException ;
  /**
   * Get all the contracts.
   *
   * @return a <code>ContractDataVector</code> with all contracts.
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getAllContracts() throws RemoteException;


  /**
   * Get all the contract descs.
   *
   * @return a <code>ContractDescDataVector</code> with all contracts.
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getAllContractDescs() throws RemoteException;

  /**
   * Describe <code>getContractsByCatalog</code> method here.
   *
   * @param pCatalogId an <code>int</code> value
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDataVector getContractsByCatalog(int pCatalogId) throws
    RemoteException;


  /**
   * Describe <code>getContractDescsByCatalog</code> method here.
   *
   * @param pCatalogId an <code>int</code> value
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDescDataVector getContractDescsByCatalog(int pCatalogId) throws
    RemoteException;

  /**
   * Describe <code>getContractDescsByCatalog</code> method here.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @param pCatalogId an <code>int</code> value
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDescDataVector getContractDescsByCatalog(String pName,
    int pMatch,
    int pCatalogId) throws RemoteException;

  /**
   * Gets all contracts for a given account.
   *
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getContractsByAccount(int pAccountId) throws
    RemoteException;

  /**
   * Gets  contract for a given account and contractId.
   *
   * @param pContractId a contract id
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractData getContractByAccount(int pContractId, int pAccountId) throws
    RemoteException;


  /**
   * Gets all contract descs for a given account.
   *
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescsByAccount(int pAccountId) throws
    RemoteException;

  /**
   * Gets all contract descs for a given store.
   *
   * @param pStoreId a store id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescsByStore(int pStoreId) throws
    RemoteException;

  /**
   * Gets contracts by name for a given account.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getContractsByAccount(String pName, int pMatch,
                                                  int pAccountId) throws
    RemoteException;

  /**
   * Gets contract descs by name for a given account.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescsByAccount(String pName,
    int pMatch,
    int pAccountId) throws RemoteException;


  /**
   * Describe <code>addContract</code> method here.
   *
   * @param pContractData a <code>ContractData</code> value
   * @return a <code>ContractData</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractData addContract(ContractData pContractData) throws
    RemoteException;

  /**
   * Updates the contract information values to be used by the request.
   * @param pContractData  the ContractData contract data.
   * @return a <code>ContractData</code> value
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractData updateContract(ContractData pContractData) throws
    RemoteException;

  /**
   * <code>removeContract</code> may be used to remove an 'unused' contract.
   * An unused contract is a contract with no database references other than
   * the default primary address, phone numbers, email addresses and
   * properties.  Attempting to remove a contract that is used will
   * result in a failure initially reported as a SQLException and
   * consequently caught and rethrown as a RemoteException.
   *
   * @param pContractData a <code>ContractData</code> value
   * @return none
   * @exception RemoteException if an error occurs
   */
  public void removeContract(ContractData pContractData) throws RemoteException;


  /**
   *  Description of the Method
   *
   *@param  pContractData        Description of Parameter
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public ContractData createFromCatalog(ContractData pContractData) throws
    RemoteException;


  /**
   *  Description of the Method
   *
   *@param  pContractData        Description of Parameter
   *@param  pParentContractId    Description of Parameter
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public ContractData createFromContract(ContractData pContractData,
                                         int pParentContractId) throws
    RemoteException;


  public ContractItemData copyItem(int pContractId,
                                   ContractItemData pItemd) throws
    RemoteException;


  public ContractItemData addItem(int pContractId,
                                  int pItemId) throws RemoteException;


  public ContractItemData addItem(int pContractId,
                                  int pCatalogId,
                                  int pItemId, String pUder) throws RemoteException;

  public ContractItemDataVector getItems(int pContractId) throws
    RemoteException, DataNotFoundException;

  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId               Description of Parameter
   *@param  pItemId                   Description of Parameter
   *@return                            The ContractItem value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDataVector getContractItemCollectionByItem(int pContractId,
    int pItemId) throws RemoteException, DataNotFoundException;

  /**
   *  Gets the ItemIds attribute of the ContractBean object
   *
   *@param  pContractId               Description of Parameter
   *@return                            The ItemIds value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public IdVector getItemIdCollectionByContract(int pContractId) throws
    RemoteException, DataNotFoundException;


  /**
   *  Gets the Item's distributor Id attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@param  pItemId                    Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public int getItemDistributorId(int pContractId, int pItemId) throws
    RemoteException, DataNotFoundException;


  /**
   *  Gets the Item's distributor Id attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemPriceViewVector getItemDistributorIdCollection(int
    pContractId) throws RemoteException, DataNotFoundException;

  /**
   *  Gets the Item's distributor Id attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@param  pItemIdV                   Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemPriceViewVector getItemDistributorIdCollection(int
    pContractId,
    IdVector pItemIdV) throws RemoteException, DataNotFoundException;


  /**
   *  Gets the distributor collection for the contract attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public BusEntityDataVector getContractDistCollection(int pContractId) throws
    RemoteException, DataNotFoundException;

  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId               Description of Parameter
   *@param  pItemIdCollection         Description of Parameter
   *@return                            The ContractItem value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDataVector getContractItemCollectionByItem(int pContractId,
    IdVector pItemIdCollection) throws RemoteException, DataNotFoundException;

  /**
   *  Gets the ContractItemDataVector for the supplied contract id
   *
   *@param  pContractId                The contract id to filter on
   *@return                            The ContractItemsVector for the supplied contract
   *@exception  RemoteException        On any error
   */
  public ContractItemDataVector getContractItems(int pContractId) throws
    RemoteException;

  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId              Description of Parameter
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDescDataVector getContractItemsDesc
    (int pContractId) throws RemoteException, DataNotFoundException;

  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId              Description of Parameter
   *@param  pCatalogOnlyFl          Filters out non catalog items if true
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDescDataVector getContractItemsDesc
    (int pContractId, boolean pCatalogOnlyFl) throws RemoteException,
    DataNotFoundException;


  /**
   *  Description of the Method
   *
   *@param  pContractItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeItem(int pContractItemId) throws RemoteException;

  /**
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(int pCatalogId,
                                            int pItemId) throws RemoteException;

  /**
   *  Description of the Method
   *
   *@param pCon DB connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(Connection pCon,
                                            int pCatalogId,
                                            int pItemId) throws RemoteException,
    Exception;

  /**
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(int pCatalogId,
                                            IdVector pItemIdV) throws
    RemoteException;


  /**
   *  Description of the Method
   *
   *@param  pCon DB Connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(Connection pCon,
                                            int pCatalogId,
                                            IdVector pItemIdV) throws
    RemoteException, Exception;


  /**
   *  Removes items from contracts
   *
   *@param  pCon DB Connection
   *@param  pCatalogIdV Vector of catalog ids
   *@param  pItemIdV  Vector of item ids
   *@param  pUser User login name
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(Connection pCon,
                                            IdVector pCatalogIdV,
                                            IdVector pItemIdV,
                                            String pUser) throws
    RemoteException, Exception;

  /**
   *  Gets the CatalogItems attribute of the ContractBean object
   *
   *@param  pContractId              Description of Parameter
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDescDataVector getCatalogItems
    (int pContractId) throws RemoteException, DataNotFoundException;


  /**
   *  Description of the Method
   *
   *@param  pContractItemId           Description of Parameter
   *@param  pNew                      Description of Parameter
   *@param  pModUserName               Description of Parameter
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public void updateItem(int pContractItemId,
                         ContractItemData pNew, String pModUserName) throws
    RemoteException, DataNotFoundException;


  /**
   *  Description of the Method
   *
   *@param  pNew                      CotrtractItemDescData object
   *@param  pModUserName              the user login name
   *@exception  RemoteException
   *@exception  DataNotFoundException thrown if contract item does not exist
   */
  public void updateItem(ContractItemDescData pNew, String pModUserName) throws
    RemoteException, DataNotFoundException;

  /**
   * Adds the contract information values to be used by the request.
   * @param pContract  the contract data.
   * @param request  the contract request data.
   * @return new ContractRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractRequestData addContract(ContractData pContract,
                                         ContractRequestData request) throws
    RemoteException;

  /**
   * Updates the contract information values to be used by the request.
   * @param pUpdateContractData  the contract data.
   * @param pContractId the contract identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateContract(ContractData pUpdateContractData,
                             int pContractId) throws RemoteException;

  /**
   * Adds the contract Item information values to be used by the request.
   * @param pContract  the contract item data.
   * @param request  the contract item request data.
   * @return new ContractItemRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemRequestData addContractItem(ContractItemData pContract,
                                                 ContractItemRequestData
                                                 request) throws
    RemoteException;

  /**
   * Updates the contract item information values to be used by the request.
   * @param pUpdateContractItemData  the contract item data.
   * @param pContractId the contract identifier.
   * @param pItemId the contract item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateContractItem(ContractItemData pUpdateContractItemData,
                                 int pContractId, int pItemId) throws
    RemoteException;

  /**
   * Gets substitutions for the contract.
   * @param pItemIds  the vector of item identifiers.
   * @param pContractId the contract identifier.
   * @param pIncludeNull the flag, which indicates to include contract item with no substitutions
   * @return vector of ContractItemSubstView object
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemSubstViewVector getSubstitutions(IdVector pItemIds,
    int pContractId, boolean pIncludeNull) throws RemoteException;

  /**
   * Gets substitutions for the contract.
   * @param pItemIds  the vector of item identifiers.
   = @param pContracts the conllection of ContractData objects
   * @param pIncludeNull the flag, which indicates to include contract item with no substitutions
   * @return vector of ContractItemSubstView object
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemSubstViewVector getSubstitutions(IdVector pItemIds,
    ContractDataVector pContracts, boolean pIncludeNull) throws RemoteException;

  /**
   * Creates new  substitutions for the contract.
   * @param pItemIds  the vector of item identifiers.
   * @param pSubstItemIds  the vector of substitution item identifiers.
   * @param pContractId the contract identifier.
   * @param pUser the user login name
   * @throws            RemoteException Required by EJB 1.0
   */
  public void addSubstitutions(IdVector pItemIds, IdVector pSubstItemIds,
                               int pContractId, String pUser) throws
    RemoteException;

  /**
   * Creates new  substitutions for the contract.
   * @param pSubstitutions  the collection of ContractItemSubstData objects, with populated itemId, contractId, substItemId fields
   * @param pUser the user login name
   * @throws            RemoteException Required by EJB 1.0
   */
  public void addSubstitutions(ContractItemSubstDataVector pSubstitutions,
                               String pUser) throws RemoteException;

  /**
   * Creates new  substitutions for the contract.
   * @param pContractItemSubstIds  the vector of contract item substitution identifiers.
   * @param pUser the user login name (not in use now)
   * @throws            RemoteException Required by EJB 1.0
   */
  public void removeSubstitutions(IdVector pContractItemSubstIds, String pUser) throws
    RemoteException;

  /**
   * Gets substitutions for the contract.
   * @param pItemIds  the vector of item identifiers.
   = @param pContracts the conllection of ContractData objects
   * @return list of ContractItemPriceView objects
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemPriceViewVector getPriceItems(IdVector pItemIds,
    ContractDataVector pContracts) throws RemoteException;

  /**
   * Gets substitutions for the contract.
   * @param pPriceItems  the list of ContractItemPriceView object.
   = @param pUser the user login name
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateContractItemCosts(ContractItemPriceViewVector pPriceItems,
                                      String pUser) throws RemoteException;

  public void updateContractItemCustData
    (ContractItemPriceViewVector pPriceItems, String pUser) throws
    RemoteException;

  /**
   * Gets contract item ids, which are not a part of the catalog
   = @param pContractId the contract id
   * @return vector of contract item ids
   * @throws            RemoteException Required by EJB 1.0
   */
  public IdVector getNonCatalogItems(int pContractId) throws RemoteException;

  /**
   * Gets contract item cost for account contracts  (all contracts
   * if pDistId = 0 and pDsitErpNum ==null
   * or filetred by distributor)
      = @param pAccoujntId the account identifier
   * @param pItemSkuNum the item sku number
   * @param pDistId the distributor id
   * @param pDistErpNum the distributor erp number (ignores if pDistId is not 0)
   * @return vector of ItemContractCostView objects
   * @throws            RemoteException Required by EJB 1.0
   */
  public ItemContractCostViewVector getItemContractCost(int pAccountId,
    int pItemSkuNum, int pDistId, String pDistErpNum) throws RemoteException;


    public BusEntityDataVector getItemAccounts(int itemId) throws RemoteException;

    public BusEntityDataVector getItemAccounts(int itemId, int storeId) throws RemoteException;

  public void updateContractItemCosts(ContractItemPriceView pPriceItem,
                                      int pContractId,
                                      String pUser) throws RemoteException;

  public int getServiceDistributorId(int pContractId, int pItemId) throws  RemoteException, DataNotFoundException ;

  public void updateContractByCatalog(int pCatalogId, String pUser, Date effDate) throws RemoteException;
  public void updateContractByCatalog(int pCatalogId, String pUser, Date effDate, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException ;

    public ContractDescDataVector getContractDescsByFreight(int freightId) throws RemoteException;

    public void addCatalogFreightRelationship(String userName, int freightId, int catalogId) throws RemoteException;

    public void deleteCatalogFreightRelationship(String userName, int contractId) throws RemoteException;

    public ContractDescDataVector getContractDescsByDiscountFreight(int freightId) throws RemoteException;

    public void addCatalogAndDiscountFreightRelationship(String userName, int freightId, int catalogId) throws RemoteException;

    public void deleteCatalogAndDiscountFreightRelationship(String userName, int contractId) throws RemoteException;

    /**
     * Calculates the amount of items in cart or pending approval
     * status pertaining to a cost center 
     * @param contractId
     * @param idVector -   List of Item Ids and Quantities.
     * @return BigDecimal 
     */
    public BigDecimal getAmountPerCostCenter(int contractId,IdVector idVector) throws RemoteException;
}
