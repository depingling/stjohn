package com.cleanwise.service.api.session;

import javax.ejb.*;
import java.rmi.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

/**
 *  Interface needed to manipulate Order Guides. There are 6 options when
 *  getting OrderGuides by name: name exactly matches, name begins with, name
 *  contains and then each of those options ignoring the case
 *
 *@author     dvieira
 *@created    August 14, 2001
 */
public interface OrderGuide extends javax.ejb.EJBObject {

  /**
   *  Define for exact match on the order guide name.
   */
  public final static int EXACT_MATCH = 0;
  /**
   *  Define for begins with match on the order guide name.
   */
  public final static int BEGINS_WITH = 1;
  /**
   *  Define for a contain match on the order guide name.
   */
  public final static int CONTAINS = 2;
  /**
   *  Define for exact match on the order guide name.
   */
  public final static int EXACT_MATCH_IGNORE_CASE = 3;
  /**
   *  Define for begins with match on the order guide name.
   */
  public final static int BEGINS_WITH_IGNORE_CASE = 4;
  /**
   *  Define for a contain match on the order guide name.
   */
  public final static int CONTAINS_IGNORE_CASE = 5;

  /*
   * Specific types of order guides that can be requested.
   */
  public static final int TYPE_UNDEFINED = -1;
  public static final int TYPE_ALL = 0;
  public static final int TYPE_BUYER = 1;
  public static final int TYPE_TEMPLATE = 2;
  public static final int TYPE_BUYER_AND_TEMPLATE = 3;
  public static final int TYPE_ADMIN_RELATED = 4;
  public static final int TYPE_BUYER_AND_SITE = 5;


  /**
   *  Gets the OrderGuide object represented by the pOrderGuideId.
   *
   *@param  pOrderGuideId
   *@return                            The OrderGuide value
   *@exception  RemoteException
   *@exception  DataNotFoundException  No entry found for the id.
   */
  public OrderGuideData getOrderGuide(int pOrderGuideId) throws RemoteException, DataNotFoundException;


  /**
   *  Fetch the order guide description information for the id specified.
   *
   *@param  pOrderGuideId              an <code>int</code> value
   *@return                            an <code>OrderGuideDescData</code>
   *      value
   *@exception  RemoteException        if an error occurs
   *@exception  DataNotFoundException  if an error occurs
   */
  public OrderGuideDescData getOrderGuideDesc(int pOrderGuideId) throws RemoteException, DataNotFoundException;


  /**
   *  Gets the OrderGuideInfoData object represented by the pOrderGuideId.
   *
   *@param  pOrderGuideId
   *@return                            The OrderGuideInfoData value
   *@exception  RemoteException
   *@exception  DataNotFoundException  No entry found for the id.
   */
  public OrderGuideInfoData getOrderGuideInfo(int pOrderGuideId) throws RemoteException, DataNotFoundException;


  /**
   *  Update the quantity for a particular item in an order guide.
   *
   *@param  pOrderGuideStructureId
   *@param  pNewQty
   *@param  pModUserName
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public void updateQuantity(int pOrderGuideStructureId,
                             int pNewQty, String pModUserName) throws RemoteException, DataNotFoundException;


  /**
   *  Remove the order guide item specified.
   *
   *@param  pOrderGuideStructureId
   *@throws  RemoteException
   */
  public void removeItem(int pOrderGuideStructureId) throws RemoteException;


  /**
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(int pCatalogId,
                                              int pItemId) throws RemoteException;

  /**
   *  Description of the Method
   *
   *@param pCon DB connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(Connection pCon, int pCatalogId, int pItemId) throws RemoteException,
    Exception;

  /**
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(int pCatalogId,
                                              IdVector pItemIdV) throws RemoteException;

  /**
   *  Description of the Method
   *
   *@parm pCon DB connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(
    Connection pCon, int pCatalogId, IdVector pItemIdV) throws RemoteException, Exception;

  /**
   *  Removes items from order guides and shopping carts
   *
   *@parm pCon DB connection
   *@param  pCatalogIdV Vector of catalog ids
   *@param  pItemIdV  Vectoir of item ids
   *@param pUser User login name
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(
    Connection pCon, IdVector pCatalogIdV, IdVector pItemIdV, String pUser) throws RemoteException, Exception;

  /**
   *  Get all the order guides for a catalog.
   *
   *@param  pCatalogId
   *@param  pTypeId              The type of order guide (id from Constants)
   *@return                      a <code>OrderGuideDataVector</code> with all
   *      the order guides attached to this catalog.
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideDescDataVector getCollectionByCatalog(int pCatalogId,
    int pType) throws RemoteException;


  /**
   *  Find the order guide list by type.
   *
   *@param  pOrderGuideType
   *@return                      The OrderGuideDataVector value
   *@exception  RemoteException  Description of Exception
   *@see                         RefCodeNames.ORDER_GUIDE_TYPE_CD If it is set
   *      to null, then all types are selected.
   */
  public OrderGuideDescDataVector getCollectionByType
    (String pOrderGuideType) throws RemoteException;


  /**
   *  Gets the vector of order guides by type available to a particular
   *  user.
   *
   *@param  pUserId
   *@param  pTypeId              The type of order guide (id from Constants)
   *@return                      OrderGuideDescDataVector
   *@exception  RemoteException  Description of Exception
   */
  public OrderGuideDescDataVector
    getCollectionByUser(int pUserId, int pTypeId) throws RemoteException;

 /**
     * Gets the OrderGuideDescDataVector
     *
     * @param pUserId     user id
     * @param pStoreId    store id
     * @param pAccountIds acount ids
     * @param pFilter     - order guide name pattern or order guide id
     * @param pFilterType - can be: "id", "nameContains" or "nameBegins" (defalut)
     * @return OrderGuideDataVector
     * @throws RemoteException if error
     */
    public OrderGuideDescDataVector getCollectionByUser(Integer pUserId,
                                                        Integer pStoreId,
                                                        IdVector pAccountIds,
                                                        String pFilter,
                                                        String pFilterType) throws RemoteException;
       /**
    }
   *  Gets the OrderGuideDescDataVector
   *
   *@param  pUserId   user id
   *@param  pStoreId  store id
   * @param pFilter - order guide name pattern or order guide id
   * @param pFilterType - can be: "id", "nameContains" or "nameBegins" (defalut)
   *@return                      OrderGuideDataVector
   *@exception  RemoteException
   */
  public OrderGuideDescDataVector getCollectionByUser(int pUserId, int pStoreId,
    String pFilter, String pFilterType) throws RemoteException;

  /**
   *  Fetch the order guides for a site.
   *
   *@param  pSiteId              an <code>int</code> value
   *@param  pTypeId              The type of order guide (id from Constants)
   *@return                      an <code>OrderGuideDescDataVector</code>
   *      value
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideDescDataVector getCollectionBySite(int pSiteId, int pTypeId) throws RemoteException;

  public OrderGuideDescDataVector getCollectionBySiteUser(int pSiteId, int pUserId,  int pTypeId) throws RemoteException;
  /**
   *  Fetch the order guides for an account.
   *
   *@param  pAccountId           an <code>int</code> value
   *@param  pTypeId              The type of order guide (id from Constants)
   *@return                      an <code>OrderGuideDescDataVector</code>
   *      value
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideDescDataVector getCollectionByAccount(int pAccountId,
    int pTypeId) throws RemoteException;


  /**
   *  Fetch the order guides matching the specified name for an account
   *
   *@param  pName                a String value with order guide name or
   *      pattern
   *@param  pAccountId           an <code>int</code> value
   *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
   *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
   *      CONTAINS_IGNORE_CASE.
   *@param  pTypeId              The type of order guide (id from Constants)
   *@return                      an <code>OrderGuideDescDataVector</code>
   *      value
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideDescDataVector
    getCollectionByNameAndAccount(String pName, int pAccountId, int pMatch, int pTypeId) throws RemoteException;


  /**
   *  Fetch the order guides for the order guide name specified.
   *
   *@param  pMatchType
   *@param  pName
   *@param  pBusEntityId
   *@param  pTypeId              The type of order guide (id from Constants)
   *@return
   *@exception  RemoteException
   */
  public OrderGuideDescDataVector getCollectionByName(String pName,
    int pMatchType, int pBusEntityId, int pTypeId) throws RemoteException;

  /**
   *  Adds an order guide entry.
   *
   *@param  pOrderGuide          The OrderGuide to be created.
   *@return                      An Order guide with the order guide id
   *      populated.
   *@exception  RemoteException  Description of Exception
   */
  public OrderGuideData addOrderGuide(OrderGuideData pOrderGuide) throws RemoteException;


  /**
   *  Create an order guide from a catalog. The order guide will then contain
   *  all the items in the catalog. The quantity is set to zero.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The
   *      catalog id and the add by values must be set.
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromCatalog
    (OrderGuideData pOrderGuideData) throws RemoteException, DataNotFoundException;


  /**
   *  Create an order guide from a contract. The order guide will then contain
   *  all the items in the contract. The quantity is set to zero.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The
   *      catalog id and the add by values must be set.
   *@param  pContractId
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromContract
    (OrderGuideData pOrderGuideData, int pContractId) throws RemoteException, DataNotFoundException;


  /**
   *  Create an order guide from another order guide. The order guide created
   *  will then contain all the items in the originating order guide.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The order
   *      guide id must be set to the original order guide and the add by must
   *      be set to the username of the user requesting the new order guide.
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromOrderGuide
    (OrderGuideData pOrderGuideData) throws RemoteException, DataNotFoundException;

  /**
   *  Create an order guide from another order guide. The order guide created
   *  will then contain all the items in the originating order guide.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The order
   *      guide id must be set to the original order guide and the add by must
   *      be set to the username of the user requesting the new order guide.
   * @param pAdmin name of the user who requested the action
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromOrderGuide(OrderGuideData pOrderGuideData, String pAdmin) throws RemoteException,
    DataNotFoundException;

  /**
   *  Updates an existing order guide entry.
   *
   *@param  pOrderGuide
   *@return
   *@exception  RemoteException
   */
  public OrderGuideData updateOrderGuide(OrderGuideData pOrderGuide) throws RemoteException;


  /**
   *  Remove an order guide.
   *
   *@param  pOrderGuideId
   *@exception  RemoteException
   */
  public void removeOrderGuide(int pOrderGuideId) throws RemoteException;


  /**
   *  Add an item to the order guide.
   *
   *@param  pOrderGuideId              an <code>int</code> value identifying
   *      the order guide db entry.
   *@param  pItemId                    an <code>int</code> value
   *@param  pItemQty                   an <code>int</code> value, defines the
   *      number of items in the order guide entry.
   *@return                            <code>OrderGuideItemDescData</code>
   *      value
   *@exception  RemoteException        if an error occurs
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideItemDescData addItem
    (int pOrderGuideId, int pItemId, int pItemQty) throws RemoteException, DataNotFoundException;
  public OrderGuideItemDescData addItem
    (int pOrderGuideId, int pItemId, int pItemQty, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException;

  /**
   *  Add an item to the order guide.
   *
   *@param  pOrderGuideId              an <code>int</code> value identifying
   *      the order guide db entry.
   *@param  pItemIds    set of item ids
   *@param  pItems   set of item quantities corresponging to item ids
   *@return      an <code>OrderGuideItemDescData</code>
   *      value
   *@exception  RemoteException        if an error occurs
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideItemDescDataVector addItems(int pOrderGuideId,
                                               OrderGuideItemDescDataVector pItems, String pAdmin) throws RemoteException,
    DataNotFoundException;

  /**
   *  Fetch the items listed in the catalog associated with this order guide.
   *  This method is needed to add items to an order guide.
   *
   *@param  pOrderGuideId
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideInfoData getCatalogItems
    (int pOrderGuideId) throws RemoteException, DataNotFoundException;


  /**
   *  Gets the vector or order guide templates available to a particular
   *  user.
   *
   *@param  pUserId
   *@return                      OrderGuideDescDataVector
   *@exception  RemoteException  Description of Exception
   */
  public OrderGuideDescDataVector
    getTemplateCollectionByUser(int pUserId) throws RemoteException;

  public OrderGuideStructureDataVector getOrderGuideStructure(int pOrderGuideId) throws RemoteException,
      DataNotFoundException;
  /**
   *  Gets order guide item descriptions
   *
   *@param  pCatalogId              the catalog identifier
   *@param  pOrderGuideId           the order guide identifier
   *@return                         collection of OrderGuideItemDescData objects
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideItemDescDataVector getOrderGuideItemsDesc(int pCatalogId, int pOrderGuideId) throws RemoteException,
    DataNotFoundException;

  /**
   *  Gets order guide item descriptions for all catalog items
   *
   *@param  pCatalogId              the catalog identifier
   *@param  pOrderGuideId           the order guide identifier
   *@return                         collection of OrderGuideItemDescData objects
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideItemDescDataVector getEstimatorItems(int pCatalogId,
    int pOrderGuideId) throws RemoteException, DataNotFoundException;

  /**
   *  Gets item descriptions, which do not belong to the order guide
   *
   *@param  pCatalogId              the catalog identifier
   *@param  pOrderGuideId           the order guide identifier
   *@return                         collection of OrderGuideItemDescData objects
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideItemDescDataVector getItemsToAdd(int pCatalogId, int pOrderGuideId) throws RemoteException,
    DataNotFoundException;

  public OrderGuideDescDataVector getCollectionByCatalogName
    (String pCatalogName, int pMatchType, int pType) throws RemoteException;

  /**
   * Sends emails when items deleted from shopping carts and/or personal order guides
   * @exception RemoteException
   */
  public void sendItemDeleteEmail() throws RemoteException;

  public OrderGuideDescDataVector getCollectionByBusEntityId(int pSiteId, int pTypeId) throws RemoteException;

  public OrderGuideStructureDataVector updateOgStructureCollection(OrderGuideStructureDataVector collection,
                                                                   String pUser) throws RemoteException;

  public OrderGuideStructureDataVector getAvailableOrderGuideItems(int pOrderGuideId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

  public OrderGuideData getOrderGuide(int pUserId,
                                      int pSiteId,
                                      String ogType) throws RemoteException;
  public void updateOrderGuideByCatalogAndBusEntity(int pCatalogId, int pBusEntityId, String pUser) throws RemoteException;

    public OrderGuideDescDataVector getCollectionByStore(IdVector storeIds, String filterValue,
        String filterType, List orderGuideTypes) throws RemoteException;

  public int[] removeOrderGuides(List pOrderGuides) throws RemoteException;

  public int removeOrderGuideStructureItems(List pOrderGuideStructureIds) throws RemoteException;

  public List<OrderGuideStructureData> updateItems(Integer pOrderGuideId, List<OrderGuideStructureData> pItems, String pUser) throws RemoteException;

  public OrderGuideData updateOrderGuideData(OrderGuideData pOrderGuide) throws RemoteException;
  //shopping list
  public void removeItemFromOrderGuide(IdVector pItemIdV,int orderGuideId) throws RemoteException, Exception ;
  public OrderGuideStructureDataVector getOrderGuideItems(
          int pOrderGuideId) throws RemoteException ;
  
  public OrderGuideInfoData getOrderGuideInfoWithEstimatedTotal(int pOrderGuideId, int pContractId) throws RemoteException,
  DataNotFoundException;

  public OrderGuideStructureDataVector getOrderGuideItems(IdVector pOrderGuideIds) throws RemoteException ;

  public OrderScheduleDataVector findOrderGuideSchedules(int orderGuideId, int siteId) throws RemoteException ;
}

