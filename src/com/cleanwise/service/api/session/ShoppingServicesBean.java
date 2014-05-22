package com.cleanwise.service.api.session;

/**
 * Title:        ShoppingServicesBean
 * Description:  Bean implementation for ShoppingServices
 *  Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval
 * methods associated with the shopping cart.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ContractItemDataAccess;
import com.cleanwise.service.api.dao.FreightTableCriteriaDataAccess;
import com.cleanwise.service.api.dao.FreightTableDataAccess;
import com.cleanwise.service.api.dao.InventoryItemsDataAccess;
import com.cleanwise.service.api.dao.InventoryLevelDAO;
import com.cleanwise.service.api.dao.InventoryLevelDataAccess;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.PreOrderAddressDataAccess;
import com.cleanwise.service.api.dao.PreOrderItemDataAccess;
import com.cleanwise.service.api.dao.PriceListDetailDataAccess;
import com.cleanwise.service.api.dao.PriceRuleDataAccess;
import com.cleanwise.service.api.dao.PriceRuleDetailDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.ShoppingControlDataAccess;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.ShoppingInfoDataAccess;
import com.cleanwise.service.api.dto.CostCenterCartData;
import com.cleanwise.service.api.framework.ShoppingServicesAPI;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.util.CacheManager;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PadSkuFreight;
import com.cleanwise.service.api.util.ProductBundle;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.SkuFlatFreight;
import com.cleanwise.service.api.util.SkuPriceFreight;
import com.cleanwise.service.api.util.SkuQuantityFreight;
import com.cleanwise.service.api.util.TotalAmountFreight;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.ConsolidatedCartView;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.FreightTableCriteriaData;
import com.cleanwise.service.api.value.FreightTableCriteriaDataVector;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InventoryItemsData;
import com.cleanwise.service.api.value.InventoryItemsDataVector;
import com.cleanwise.service.api.value.InventoryLevelData;
import com.cleanwise.service.api.value.InventoryLevelDataVector;
import com.cleanwise.service.api.value.InventoryLevelView;
import com.cleanwise.service.api.value.InventoryLevelViewVector;
import com.cleanwise.service.api.value.ItemAssocData;
import com.cleanwise.service.api.value.ItemAssocDataVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.ItemPriceRuleData;
import com.cleanwise.service.api.value.ItemPriceRuleDataVector;
import com.cleanwise.service.api.value.ItemShoppingHistoryData;
import com.cleanwise.service.api.value.JanitorClosetItemData;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAddressDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.OrderHandlingDetailView;
import com.cleanwise.service.api.value.OrderHandlingDetailViewVector;
import com.cleanwise.service.api.value.OrderHandlingItemView;
import com.cleanwise.service.api.value.OrderHandlingItemViewVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PreOrderAddressData;
import com.cleanwise.service.api.value.PreOrderAddressDataVector;
import com.cleanwise.service.api.value.PreOrderItemData;
import com.cleanwise.service.api.value.PreOrderItemDataVector;
import com.cleanwise.service.api.value.PriceRuleData;
import com.cleanwise.service.api.value.PriceRuleDataVector;
import com.cleanwise.service.api.value.PriceRuleDescView;
import com.cleanwise.service.api.value.PriceRuleDescViewVector;
import com.cleanwise.service.api.value.PriceRuleDetailData;
import com.cleanwise.service.api.value.PriceRuleDetailDataVector;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.ServiceData;
import com.cleanwise.service.api.value.ServiceDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingCartServiceData;
import com.cleanwise.service.api.value.ShoppingCartServiceDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.ShoppingInfoData;
import com.cleanwise.service.api.value.ShoppingInfoDataVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryConfigView;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.service.api.value.SiteInventoryInfoViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.wrapper.InventoryLevelViewWrapper;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

public class ShoppingServicesBean extends ShoppingServicesAPI
{
	  private static final Logger log = Logger.getLogger(ShoppingServicesBean.class);
  /**
   *
   */
  public ShoppingServicesBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   * Adds the item information values into shopping cart.
   * @param pCart  the shopping cart data.
   * @param pItemId  the item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void addItem(ShoppingCartData pCart, int pItemId)
      throws RemoteException
  {

  }

  /**
   * buildss the janitor closet item information values.
   * @param pJanitorClosetId  the janitor closet identifier.
   * @param pBusEntityId  the customer identifier.
   * @param pItemId  the item identifier.
   * @return JanitorClosetItemData
   * @throws            RemoteException Required by EJB 1.0
   */
  public JanitorClosetItemData buildJanitorClosetItem(int pJanitorClosetId,
            int pBusEntityId, int pItemId)
      throws RemoteException
  {
    return new JanitorClosetItemData();
  }

  /**
   * Calculates the shopping cart total values.
   * @param pCart  the shopping cart data.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void calculateCartTotal(ShoppingCartData pCart)
      throws RemoteException
  {

  }

  /**
   * Clears the shopping cart.
   * @param pCart  the shopping cart data.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void clearCart(ShoppingCartData pCart)
      throws RemoteException
  {

  }

  /**
   * Gets the item price rule vector information.
   * @param pItemId  the item identifier.
   * @return ItemPriceRuleDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ItemPriceRuleDataVector getItemPriceRulesCollection(int pItemId)
      throws RemoteException
  {
    return new ItemPriceRuleDataVector();
  }

  /**
   * Gets the item price rule information.
   * @param pItemId  the item identifier.
   * @param pPriceRuleId  the price rule identifier.
   * @return ItemPriceRuleData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ItemPriceRuleData getItemPriceRule(int pItemId,
            int pPriceRuleId)
      throws RemoteException
  {
    return new ItemPriceRuleData();
  }

  /**
   * Removes the item information values from the shopping cart.
   * @param pCart  the shopping cart data.
   * @param pItemId  the item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void removeItem(ShoppingCartData pCart, int pItemId)
      throws RemoteException
  {

  }

  /**
   * Calculates the shopping cart locale total values.
   * @param pCart  the shopping cart data.
   * @param pLocaleCd the locale code.
   * @param pCurrencyCd the currency code.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void calculateCartLocaleTotal(ShoppingCartData pCart,
                String pLocaleCd, String pCurrencyCd)
      throws RemoteException
  {

  }

  /**
   * Saves the shopping cart .
   * @param pCart  the shopping cart data.
   * @param pRequestShortDesc  the request short description.
   * @param pRefRequestNum  the ref request number.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void saveCart(ShoppingCartData pCart,
                String pRequestShortDesc, String pRefRequestNum)
      throws RemoteException
  {

  }

    /**
     * Picks up catalog item ids, which match to condition. The result is ordered by short description
     *
     * @param pPattern           the substring of item name
     * @param pMatchType         six different types of matching could be applied (see SearchCreteria)
     * @param pShoppingItemRequest specific user criteria
     * @return collection of item ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector searchCustormerItemsByName(String pPattern,
                                               int pMatchType,
                                               ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        return searchCustomerItems(pPattern, 2, pMatchType, pShoppingItemRequest);
    }

   /**
     * Picks up catalog item ids, which match to condition. The result is ordered by short description
     *
     * @param pPattern           the substring of item name
     * @param pMatchType         six different types of matching could be applied (see SearchCreteria)
     * @param pShoppingItemRequest specific user criteria
     * @return collection of item ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector searchCustormerItemsBySkuNum(String pPattern,
                                                 int pMatchType,
                                                 ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        return searchCustomerItems(pPattern, 1, pMatchType, pShoppingItemRequest);
    }


    /**
     * @param pSearchType indicator: 1-sku number, 2-short description
     */
    /******************************************************************************/
    private IdVector searchCustomerItems(String pPattern,
                                         int pSearchType,
                                         int pMatchType,
                                         ShoppingItemRequest pShoppingItemRequest)  throws RemoteException {
        Connection con = null;
        IdVector itemIdV = new IdVector();

        try {

            con = getConnection();

            String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            IdVector priceListItemIds = null;

            if (pSearchType == 1) {

                String productBundle = ShoppingDAO.getProductBundleValue(con, pShoppingItemRequest.getSiteId());

                if (Utility.isSet(productBundle)) {

                    IdVector priceListIds = Utility.toIdVector(
                            pShoppingItemRequest.getPriceListRank1Id(),
                            pShoppingItemRequest.getPriceListRank2Id()
                    );

                    // try to find in a price lists
                    DBCriteria dbc = new DBCriteria();
                    dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
                    dbc.addOneOf(PriceListDetailDataAccess.ITEM_ID, itemReq);
                    switch (pMatchType) {
                        case SearchCriteria.EXACT_MATCH: dbc.addEqualTo(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, pPattern); break;
                        case SearchCriteria.BEGINS_WITH: dbc.addLike(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, pPattern + "%"); break;
                        case SearchCriteria.CONTAINS: dbc.addLike(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, "%" + pPattern + "%"); break;
                        case SearchCriteria.EXACT_MATCH_IGNORE_CASE: dbc.addEqualToIgnoreCase(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, pPattern.toUpperCase()); break;
                        case SearchCriteria.BEGINS_WITH_IGNORE_CASE: dbc.addLikeIgnoreCase(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, pPattern.toUpperCase() + "%");  break;
                        case SearchCriteria.CONTAINS_IGNORE_CASE: dbc.addLikeIgnoreCase(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, "%" + pPattern.toUpperCase() + "%"); break;
                        default: throw new RemoteException("searchCustormerItems(). Unknown match type: " + pMatchType);
                    }

                    priceListItemIds = PriceListDetailDataAccess.selectIdOnly(con, PriceListDetailDataAccess.ITEM_ID, dbc);
        	    	
                    //---- STJ-6114: Performance Improvements - Optimize Pollock 
        	        log.info("searchCustomerItems() ===>Optimize Pollock BEGIN: priceListItemIds.size() =" + priceListItemIds.size());
        	        ShoppingDAO.filterByProductBundle(con, priceListItemIds, pShoppingItemRequest);
        	    	log.info("searchCustomerItems() ===>Optimize Pollock END: priceListItemIds.size() =" + priceListItemIds.size());
        	        //----

                }
            }

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemReq);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            dbc.addOrderBy(CatalogStructureDataAccess.SHORT_DESC);

            String searchField = (pSearchType == 1) ? CatalogStructureDataAccess.CUSTOMER_SKU_NUM : CatalogStructureDataAccess.SHORT_DESC;
            switch (pMatchType) {
                case SearchCriteria.EXACT_MATCH: dbc.addEqualTo(searchField, pPattern); break;
                case SearchCriteria.BEGINS_WITH: dbc.addLike(searchField, pPattern + "%"); break;
                case SearchCriteria.CONTAINS: dbc.addLike(searchField, "%" + pPattern + "%"); break;
                case SearchCriteria.EXACT_MATCH_IGNORE_CASE: dbc.addEqualToIgnoreCase(searchField, pPattern.toUpperCase()); break;
                case SearchCriteria.BEGINS_WITH_IGNORE_CASE: dbc.addLikeIgnoreCase(searchField, pPattern.toUpperCase() + "%");  break;
                case SearchCriteria.CONTAINS_IGNORE_CASE: dbc.addLikeIgnoreCase(searchField, "%" + pPattern.toUpperCase() + "%"); break;
                default: throw new RemoteException("searchCustormerItems(). Unknown match type: " + pMatchType);
            }

            CatalogStructureDataVector catalogStructureDV = CatalogStructureDataAccess.select(con, dbc);

            if (priceListItemIds != null) {
                for(Object oCatStr:catalogStructureDV) {
                   priceListItemIds.add(((CatalogStructureData)oCatStr).getItemId());
                }
                dbc = new DBCriteria();
                dbc.addOneOf(ItemDataAccess.ITEM_ID, priceListItemIds);
                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
                dbc.addOrderBy(CatalogStructureDataAccess.SHORT_DESC);

                catalogStructureDV = CatalogStructureDataAccess.select(con, dbc);
            }

            //Now the same thing from clw_item
            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID, itemReq);
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);
            searchField = (pSearchType == 1) ? ItemDataAccess.SKU_NUM : ItemDataAccess.SHORT_DESC;
            switch (pMatchType) {
                case SearchCriteria.EXACT_MATCH: dbc.addEqualTo(searchField, pPattern); break;
                case SearchCriteria.BEGINS_WITH: dbc.addLike(searchField, pPattern + "%"); break;
                case SearchCriteria.CONTAINS: dbc.addLike(searchField, "%" + pPattern + "%");  break;
                case SearchCriteria.EXACT_MATCH_IGNORE_CASE: dbc.addEqualToIgnoreCase(searchField, pPattern.toUpperCase()); break;
                case SearchCriteria.BEGINS_WITH_IGNORE_CASE: dbc.addLikeIgnoreCase(searchField, pPattern.toUpperCase() + "%"); break;
                case SearchCriteria.CONTAINS_IGNORE_CASE: dbc.addLikeIgnoreCase(searchField, "%" + pPattern.toUpperCase() + "%"); break;
                default:throw new RemoteException("searchCustormerItems(). Unknown match type: " + pMatchType);
            }

            ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

            //Pick up catalog structure elemets to filter out duplictaions from item table
            dbc = new DBCriteria();
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemReq);
            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, Utility.toIdVector(itemDV));
            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            CatalogStructureDataVector catalogStructureFltDV = CatalogStructureDataAccess.select(con, dbc);

            //Filter out
            if (catalogStructureFltDV.size() != itemDV.size()) {
                throw new RemoteException("Error. No consitency between catalog_structure and item. Catalog id: " + pShoppingItemRequest.getShoppingCatalogId());
            }

            ItemDataVector itemResDV = new ItemDataVector();
            for (int ii = 0; ii < itemDV.size(); ii++) {
                CatalogStructureData csD = (CatalogStructureData) catalogStructureFltDV.get(ii);
                ItemData iD = (ItemData) itemDV.get(ii);
                if (csD.getItemId() != iD.getItemId()) {
                    throw new RemoteException("Error. No consitency between catalog_structure and item. Catalog id: " + pShoppingItemRequest.getShoppingCatalogId());
                }
                String shortDesc = csD.getShortDesc();
                if (shortDesc == null || shortDesc.trim().length() == 0) {
                    itemResDV.add(iD);
                }
            }

            //Order by short descreption
            Object[] items = itemResDV.toArray();
            for (int ii = 0; ii < items.length - 1; ii++) {
                boolean exit = true;
                for (int jj = 1; jj < items.length - ii; jj++) {
                    ItemData itemD1 = (ItemData) items[jj - 1];
                    ItemData itemD2 = (ItemData) items[jj];
                    if (itemD1.getShortDesc().compareToIgnoreCase(itemD2.getShortDesc()) > 0) {
                        items[jj - 1] = itemD2;
                        items[jj] = itemD1;
                        exit = false;
                    }
                }
                if (exit) break;
            }

            //Create result vector
            for (int ii = 0, jj = 0; ii < items.length || jj < catalogStructureDV.size();) {
                if (ii < items.length && jj < catalogStructureDV.size()) {
                    ItemData itemD = (ItemData) items[ii];
                    CatalogStructureData catStD = (CatalogStructureData) catalogStructureDV.get(jj);
                    if (itemD.getShortDesc().compareToIgnoreCase(catStD.getShortDesc()) < 0) {
                        itemIdV.add(new Integer(itemD.getItemId()));
                        ii++;
                    } else {
                        itemIdV.add(new Integer(catStD.getItemId()));
                        jj++;
                    }
                    continue;
                }
                if (ii < items.length && jj >= catalogStructureDV.size()) {
                    ItemData itemD = (ItemData) items[ii];
                    itemIdV.add(new Integer(itemD.getItemId()));
                    ii++;
                }
                if (ii >= items.length && jj < catalogStructureDV.size()) {
                    CatalogStructureData catStD = (CatalogStructureData) catalogStructureDV.get(jj);
                    itemIdV.add(new Integer(catStD.getItemId()));
                    jj++;
                }
            }
        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() SQL Exception happened");
        } finally {
            closeConnection(con);
        }

        return itemIdV;

    }

 /******************************************************************************/
    /**
     * Picks up all catalog item ids
     *
     * @param pStoreTypeCd        the store type. Rules, which sku number is used
     * @param pSortBy             requested item sorting (possible values are in Constants class)
     * @param pShoppingItemRequest  shopping user right
     * @return collection of item ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector getAllCatalogItems(String pStoreTypeCd, ShoppingItemRequest pShoppingItemRequest, int pSortBy) throws RemoteException {

        log.info("getAllCatalogItems()=> BEGIN");

        Connection con = null;
        IdVector itemIdV = new IdVector();

        try {

            con = getConnection();

            String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            itemIdV = sortItems(con, pStoreTypeCd, pShoppingItemRequest.getShoppingCatalogId(), null, itemReq, pSortBy);
            //---- STJ-6114: Performance Improvements - Optimize Pollock 
	        log.info("getAllCatalogItems() ===>Optimize Pollock BEGIN: priceListItemIds.size() =" + itemIdV.size());
	        ShoppingDAO.filterByProductBundle(con, itemIdV, pShoppingItemRequest);
	    	log.info("getAllCatalogItems() ===>Optimize Pollock END: priceListItemIds.size() =" + itemIdV.size());
	        //----

        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() SQL Exception happened");
        } finally {
            logInfo("getAllCatalogItems close start");
            closeConnection(con);
            logInfo("getAllCatalogItems close end");
        }

        log.info("getAllCatalogItems()=> END.");

        return itemIdV;
    }

   /******************************************************************************/
    /**
     * Prepares collection of ShoppingCartItemDataVector. Assigns contract price if exists, othewise assigns list price.
     * If product blongs to more than one category, takes the first one
     *
     * @param pStoreTypeCd       the store type. Rules, which sku number is used
     * @param pSiteData          site data
     * @param pSkuNums           the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getShoppingItemsBySku(String pStoreTypeCd,
                                                            SiteData pSiteData,
                                                            List pSkuNums,
                                                            ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException {
      return getShoppingItemsBySku(pStoreTypeCd, pSiteData, pSkuNums,pShoppingItemRequest, null);

    }
    public ShoppingCartItemDataVector getShoppingItemsBySku(String pStoreTypeCd,
                                                            SiteData pSiteData,
                                                            List pSkuNums,
                                                            ShoppingItemRequest pShoppingItemRequest,
                                                            AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException {


        Connection con = null;
        ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();

        pSkuNums = Utility.truncateEmptyValues(pSkuNums);
        if (pSkuNums.size() == 0) {
            return itemList;
        }

        try {

            con = getConnection();

            String itemsReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);
           log.info("getShoppingItemsBySku()=> itemsReq: "+itemsReq);

           log.info("getShoppingItemsBySku()=> pStoreTypeCd: "+pStoreTypeCd);

            pStoreTypeCd = Utility.strNN(pStoreTypeCd);
            IdVector itemIds;
            if (pStoreTypeCd.equals(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR)) {

                IdVector priceListItemIds = new IdVector();
                String itemsWithSku = null;

                if (pSiteData.isUseProductBundle()) {

                    IdVector priceListIds = Utility.toIdVector(
                            pShoppingItemRequest.getPriceListRank1Id(),
                            pShoppingItemRequest.getPriceListRank2Id()
                    );

                    // try to find in a price lists
                    DBCriteria dbc = new DBCriteria();
                    dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
                    dbc.addOneOf(PriceListDetailDataAccess.ITEM_ID, itemsReq);
                    dbc.addOneOfIgnoreCase(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, pSkuNums);

                    priceListItemIds = PriceListDetailDataAccess.selectIdOnly(con, PriceListDetailDataAccess.ITEM_ID, dbc);

                    dbc = new DBCriteria();
                    dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
                    dbc.addIsNotNull("TRIM(" + PriceListDetailDataAccess.CUSTOMER_SKU_NUM + ")");

                    itemsWithSku = PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, dbc);

                }

                // Check if skuNum is Customer Sku Num
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
                dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,  itemsReq);
                dbc.addOneOfIgnoreCase(CatalogStructureDataAccess.CUSTOMER_SKU_NUM, pSkuNums);
                if (pSiteData.isUseProductBundle()) {
                    dbc.addNotOneOf(CatalogStructureDataAccess.ITEM_ID, itemsWithSku);
                }

                IdVector custItemIds = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);

                if(pSiteData.isUseProductBundle()){

                    dbc = new DBCriteria();
                    dbc.addIsNotNull("TRIM(" + CatalogStructureDataAccess.CUSTOMER_SKU_NUM + ")");
                    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
                    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
                    dbc.addNotOneOf(CatalogStructureDataAccess.ITEM_ID,  itemsWithSku);

                    itemsWithSku = CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, dbc);
                }

                String cim = ItemMappingDataAccess.CLW_ITEM_MAPPING;
                String ccs = CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE;
                String dist = BusEntityDataAccess.CLW_BUS_ENTITY;

                dbc = new DBCriteria();
                dbc.addJoinTableEqualTo(cim, ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
                dbc.addJoinTableOneOf(cim, ItemMappingDataAccess.ITEM_ID, itemsReq);
                dbc.addJoinTableOneOfIgnoreCase(cim, ItemMappingDataAccess.ITEM_NUM, pSkuNums);
                dbc.addJoinTableOrderBy(cim, ItemMappingDataAccess.ITEM_ID);
                dbc.addJoinCondition(cim, ItemMappingDataAccess.ITEM_ID, ccs, CatalogStructureDataAccess.ITEM_ID);
                dbc.addJoinCondition(dist, BusEntityDataAccess.BUS_ENTITY_ID, cim, ItemMappingDataAccess.BUS_ENTITY_ID);
                dbc.addJoinTableEqualTo(dist, BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

                if (pSiteData.isUseProductBundle()) {
                    dbc.addJoinTableNotOneOf(ccs, CatalogStructureDataAccess.ITEM_ID, itemsWithSku);
                } else{
                    dbc.addJoinTableIsNullOrSpace(ccs, CatalogStructureDataAccess.CUSTOMER_SKU_NUM);
                }

                dbc.addJoinTableEqualTo(ccs, CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
log.info("sql: "+ ItemMappingDataAccess.getSqlSelectIdOnly("*",dbc));
                itemIds = JoinDataAccess.selectIdOnly(con, cim, ItemMappingDataAccess.ITEM_ID, dbc, 10000);
                itemIds.addAll(custItemIds);
                itemIds.addAll(priceListItemIds);
    	    	//---- STJ-6114: Performance Improvements - Optimize Pollock 
    	        log.info("getShoppingItemsBySku() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
    	        ShoppingDAO.filterByProductBundle(con, itemIds, pShoppingItemRequest);
    	    	log.info("getShoppingItemsBySku() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
    	        //----

                if (pSiteData.isUseProductBundle()) {
                    itemList = prepareShoppingItemsNew(con,
                            pStoreTypeCd,
                            pShoppingItemRequest.getShoppingCatalogId(),
                            pShoppingItemRequest.getContractId(),
                            itemIds,
                            pSiteData.getSiteId(),
                            pCategToCostCenterView);
                } else {
                    itemList = prepareShoppingItems(con,
                            pShoppingItemRequest.getShoppingCatalogId(),
                            pShoppingItemRequest.getContractId(),
                            itemIds, pCategToCostCenterView);
                }

                itemList = ShoppingDAO.setActualSkus(pStoreTypeCd, itemList);

                //Check all skus presence
                String errorNums = detectInvalidSkus(itemList, pSkuNums);
                if (errorNums.length() > 0) {
                    throw new DataNotFoundException("^clwKey^shop.errors.cannotFindSkuNumbers^clwKey^^clwParam^" + errorNums + "^clwParam^");
                }

                if (pSiteData.hasModernInventoryShopping()) {
                    itemList = setupItemInventoryInfo(con,
                            pSiteData,
                            itemList,
                            pCategToCostCenterView);
                } else {
                    itemList = setupItemInventoryInfo(con,
                            pSiteData.getAccountId(),
                            itemList,
                            pSiteData.getSiteInventory(),
                            null,
                            pCategToCostCenterView);
                }

                return itemList;

            } else {

                IdVector priceListItemIds = new IdVector();
                String itemsWithSku = null;

                if (pSiteData.isUseProductBundle()) {

                    IdVector priceListIds = Utility.toIdVector(
                            pShoppingItemRequest.getPriceListRank1Id(),
                            pShoppingItemRequest.getPriceListRank2Id()
                    );

                    // try to find in a price lists
                    DBCriteria dbc = new DBCriteria();
                    dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
                    dbc.addOneOf(PriceListDetailDataAccess.ITEM_ID, itemsReq);
                    dbc.addOneOfIgnoreCase(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, pSkuNums);

                    priceListItemIds = PriceListDetailDataAccess.selectIdOnly(con, PriceListDetailDataAccess.ITEM_ID, dbc);

                    dbc = new DBCriteria();
                    dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
                    dbc.addIsNotNull("TRIM(" + PriceListDetailDataAccess.CUSTOMER_SKU_NUM + ")");

                    itemsWithSku = PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, dbc);

                }

                 // Check if skuNum is Customer Sku Num
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
                dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,  itemsReq);
                dbc.addOneOfIgnoreCase(CatalogStructureDataAccess.CUSTOMER_SKU_NUM, pSkuNums);
                if (pSiteData.isUseProductBundle()) {
                    dbc.addNotOneOf(CatalogStructureDataAccess.ITEM_ID, itemsWithSku);
                }


                dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

                itemIds = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);

                itemIds.addAll(priceListItemIds);

                if(pSiteData.isUseProductBundle()){

                    dbc = new DBCriteria();
                    dbc.addIsNotNull("TRIM(" + CatalogStructureDataAccess.CUSTOMER_SKU_NUM + ")");
                    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
                    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
                    dbc.addNotOneOf(CatalogStructureDataAccess.ITEM_ID,  itemsWithSku);

                    itemsWithSku = CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, dbc);
                }

                // Filter numeric skus
                ArrayList numericSkus = new ArrayList();
                for (int ctr = 0; ctr < pSkuNums.size(); ctr++) {
                    int isku = 0;
                    String s = (String) pSkuNums.get(ctr);
                    try {
                        isku = Integer.parseInt(s);
                    } catch (Exception e) {
                    }
                    if (isku > 0) {
                        numericSkus.add(s);
                    }
                }
                log.info(" getShoppingItemsBySku()=> numericSkus:"+numericSkus);

                if (numericSkus.size() > 0) {
                    dbc = new DBCriteria();
                    dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
                    dbc.addOneOf(ItemDataAccess.SKU_NUM, numericSkus);
                    dbc.addOneOf(ItemDataAccess.ITEM_ID, itemsReq);
                    if (pSiteData.isUseProductBundle()) {
                        dbc.addNotOneOf(ItemDataAccess.ITEM_ID, itemsWithSku);
                    }
                    dbc.addOrderBy(ItemDataAccess.ITEM_ID);
                    itemIds.addAll(ItemDataAccess.selectIdOnly(con, ItemDataAccess.ITEM_ID, dbc));
                }
    	    	//---- STJ-6114: Performance Improvements - Optimize Pollock 
    	        log.info("getShoppingItemsBySku() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
    	        ShoppingDAO.filterByProductBundle(con, itemIds, pShoppingItemRequest);
    	    	log.info("getShoppingItemsBySku() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
    	        //----
           }

            //Find overwriting catalog records
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIds);
            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
            IdVector itemIds1 = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);

            if (pSiteData.isUseProductBundle()) {
                itemList = prepareShoppingItemsNew(con,
                        pStoreTypeCd,
                        pShoppingItemRequest.getShoppingCatalogId(),
                        pShoppingItemRequest.getContractId(),
                        itemIds,
                        pSiteData.getSiteId(),
                        pCategToCostCenterView);
            } else {
                itemList = prepareShoppingItems(con,
                        pShoppingItemRequest.getShoppingCatalogId(),
                        pShoppingItemRequest.getContractId(),
                        itemIds1, pCategToCostCenterView);
            }

            itemList = ShoppingDAO.setActualSkus(pStoreTypeCd, itemList);

            //Check all skus presence
            String errorNums = detectInvalidSkus(itemList, pSkuNums);
            if (errorNums.length() > 0) {
                throw new DataNotFoundException("^clwKey^shop.errors.cannotFindSkuNumbers^clwKey^^clwParam^" + errorNums + "^clwParam^");
            }

            // Set the inventory info for each item.
            if (pSiteData.hasModernInventoryShopping()) {
                itemList = setupItemInventoryInfo(con,
                        pSiteData,
                        itemList,
                        pCategToCostCenterView);
            } else {
                itemList = setupItemInventoryInfo(con,
                        pSiteData.getAccountId(),
                        itemList,
                        pSiteData.getSiteInventory(),
                        null,
                        pCategToCostCenterView);
            }

            itemList = setupMaxOrderQtyValues(pSiteData, itemList);

        } catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(con);
        }

        return itemList;
    }

    private String detectInvalidSkus(ShoppingCartItemDataVector pItemList, List pSkuNums) {

        String errorNums = "";

        for (int ii = 0; ii < pSkuNums.size(); ii++) {
            String skuNum = (String) pSkuNums.get(ii);
            int jj = 0;
            for (; jj < pItemList.size(); jj++) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) pItemList.get(jj);
                if (skuNum.equalsIgnoreCase(sciD.getActualSkuNum())) {
                    break;
                }
            }
            if (jj == pItemList.size()) {
                log.info("detectInvalidSkus()=> Invalid sku detected: " + skuNum);
                if (errorNums.length() > 0) {
                    errorNums += ", ";
                }
                errorNums += skuNum;
            }
        }

        return errorNums;
    }

  /******************************************************************************/
    /**
     * Prepares collection of ShoppingCartItemDataVector. Assigns contract price if exists, othewise assigns list price.
     * If product blongs to more than one category, takes the first one
     *
     * @param pSiteData          site data
     * @param pSkuNums           the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getShoppingItemsByMfgSku(SiteData pSiteData,
                                                               List pSkuNums,
                                                               ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException {
      return getShoppingItemsByMfgSku( pSiteData, pSkuNums, pShoppingItemRequest, null);

    }
    public ShoppingCartItemDataVector getShoppingItemsByMfgSku(SiteData pSiteData,
                                                               List pSkuNums,
                                                               ShoppingItemRequest pShoppingItemRequest,
                                                               AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException {

        Connection con = null;
        ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();

        pSkuNums = Utility.truncateEmptyValues(pSkuNums);
        if (pSkuNums.size() == 0) {
            return itemList;
        }

        try {

            con = getConnection();

            String itemsReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemsReq);
            dbc.addOneOf(ItemMappingDataAccess.ITEM_NUM, pSkuNums);

            String errorNums = "";
            ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con, dbc);
            for (int ii = 0; ii < pSkuNums.size(); ii++) {
                String skuNum = (String) pSkuNums.get(ii);
                int jj = 0;
                for (; jj < itemMappingDV.size(); jj++) {
                    ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(jj);
                    if (skuNum.equalsIgnoreCase(itemMappingD.getItemNum())) {
                        break;
                    }
                }
                if (jj == itemMappingDV.size()) {
                    if (errorNums.length() > 0) {
                        errorNums += ", ";
                    }
                    errorNums += skuNum;
                }
            }

            if (errorNums.length() > 0) {
                throw new DataNotFoundException("^clwKey^shop.errors.cannotFindSkuNumbers^clwKey^^clwParam^" + errorNums + "^clwParam^");
            }

            IdVector items = new IdVector();
            for (int ii = 0; ii < itemMappingDV.size(); ii++) {
                ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);
                items.add(new Integer(itemMappingD.getItemId()));
            }
          
             
	    	//---- STJ-6114: Performance Improvements - Optimize Pollock 
	        log.info("getShoppingItemsByMfgSku() ===>Optimize Pollock BEGIN: items.size() =" + items.size());
	        ShoppingDAO.filterByProductBundle(con, items, pShoppingItemRequest);
	    	log.info("getShoppingItemsByMfgSku() ===>Optimize Pollock END: items.size() =" + items.size());
	        //----
            
            if (pSiteData.isUseProductBundle()) {
                //set price,actual sku, category ...
                itemList = prepareShoppingItemsNew(con,
                        null,
                        pShoppingItemRequest.getShoppingCatalogId(),
                        pShoppingItemRequest.getContractId(),
                        items,
                        pSiteData.getSiteId(),
                        pCategToCostCenterView);
            } else {
                itemList = prepareShoppingItems(con,
                        pShoppingItemRequest.getShoppingCatalogId(),
                        pShoppingItemRequest.getContractId(),
                        items, pCategToCostCenterView);
            }
            // Set the inventory info for each item.
            if (pSiteData.hasModernInventoryShopping()) {
                itemList = setupItemInventoryInfo(con, pSiteData, itemList,pCategToCostCenterView);
            } else {
                itemList = setupItemInventoryInfo(con,
                        pSiteData.getAccountId(),
                        itemList,
                        pSiteData.getSiteInventory(),
                        null,pCategToCostCenterView);
            }
            itemList = setupMaxOrderQtyValues(pSiteData, itemList);
        }
        catch (DataNotFoundException e) {
            e.printStackTrace();
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(con);
        }
        return itemList;
    }
  /******************************************************************************/
  /**
   * Prepares collection of ShoppingCartItemDataVector. Assigns contract price if exists, othewise assigns list price.
   * If product blongs to more than one category, takes the first one
   * @param pStoreTypeCd  the store type. Rules, which sku number is used
   * @param pCatalogId  the catalog identificator
   * @param pContractId the contract identificator or 0 if doesn't appliy
   * @param pItems the list of item is or the list of product object
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException Required by EJB 1.0
   */
  public ShoppingCartItemDataVector  prepareShoppingItems
      (String pStoreTypeCd,  SiteData pSiteData, int pCatalogId,
       int pContractId, List pItems)
    throws RemoteException
  {
    return prepareShoppingItems( pStoreTypeCd, pSiteData,pCatalogId,pContractId, pItems, null  );
  }
  
  public ShoppingCartItemDataVector prepareShoppingItems (String pStoreTypeCd,  SiteData pSiteData, int pCatalogId,
       int pContractId, List pItems, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException  {
	  
    ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();
    
    if (pItems.size() == 0) {
      return itemList;
    }
    
    Connection con = null;
    try {
        con = getConnection();
        if (pSiteData.isUseProductBundle()) {
            itemList = prepareShoppingItemsNew(con, pStoreTypeCd, pCatalogId, pContractId, pItems,
                    pSiteData.getSiteId(), pCategToCostCenterView);
        } else {
            itemList = prepareShoppingItems(con, pCatalogId, pContractId, pItems,
                    pSiteData.getSiteId(), pCategToCostCenterView);
        }
        itemList = ShoppingDAO.setActualSkus(pStoreTypeCd, itemList);
        // Set the inventory info for each item.
        if (pSiteData.hasModernInventoryShopping()) {
            itemList = setupItemInventoryInfo(con, pSiteData, itemList, pCategToCostCenterView);
        } 
        else {
            itemList = setupItemInventoryInfo(con, pSiteData.getAccountId(), itemList,
                                              pSiteData.getSiteInventory(), null, pCategToCostCenterView);
        }
        itemList = setupMaxOrderQtyValues(pSiteData, itemList);
    }
    catch (Exception exc) {
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    }
    finally {
        closeConnection(con);
    }
    return itemList;
  }

  private ShoppingCartItemDataVector  prepareShoppingItems(Connection pCon, int pCatalogId, int pContractId, List pItems, AccCategoryToCostCenterView pCategToCostCenterView)
       throws RemoteException    {

           return prepareShoppingItems(pCon, pCatalogId, pContractId,pItems,0, pCategToCostCenterView);
   }

    private ShoppingCartItemDataVector  prepareShoppingItems(Connection pCon, int pCatalogId, int pContractId, List pItems,
    		int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

    	ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();
    	if(pItems==null || pItems.size()==0){
    		return itemList;
    	}
    	
    	try {
    		DBCriteria dbc;
    		//prepare Ids
    		IdVector idList = new IdVector();
    		//Check type
    		Object itemO = pItems.get(0);
    		ProductDataVector productDV = null;
    		if (itemO instanceof Integer){
    			productDV = getProducts(pCon,pCatalogId, pItems,siteId,pCategToCostCenterView);
    		}
    		else if (itemO instanceof ProductData) {
    			productDV = new ProductDataVector();
    			for (int ii=0; ii<pItems.size(); ii++) {
    				productDV.add ((ProductData) pItems.get(ii));
    			}
    		}
    		else if (itemO instanceof ShoppingCartItemData) {
    			return (ShoppingCartItemDataVector) pItems;
    		}
    		else {
    			throw new RemoteException("prepareShoppingItems() Unknown type of requested element: "+itemO.getClass().getName());
    		}

    		Hashtable contractsHashtable = new Hashtable(1);

    		//Pickup contract information
    		if (pContractId != 0) {
    			ContractItemDataVector contractItemDV = new ContractItemDataVector();
    			for (int ii=0; ii<productDV.size(); ii++) {
    				ProductData pD = (ProductData) productDV.get(ii);
    				idList.add(new Integer(pD.getProductId()));
    			}
    			dbc = new DBCriteria();
    			dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID,pContractId);
    			dbc.addOneOf(ContractItemDataAccess.ITEM_ID,idList);
    			dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
    			contractItemDV = ContractItemDataAccess.select(pCon,dbc);
    			if ( null != contractItemDV ) {
    				java.util.Iterator it = contractItemDV.iterator();
    				while (it.hasNext()) {
    					ContractItemData cid = (ContractItemData)it.next();
    					contractsHashtable.put(new Integer(cid.getItemId()), cid);
    				}
    			}
    		}

    		for (int ii=0; ii<productDV.size(); ii++) {
    			ProductData pD = (ProductData) productDV.get(ii);
    			ShoppingCartItemData shoppingCartItemD = new ShoppingCartItemData();
    			shoppingCartItemD.setProduct(pD);
    			CatalogCategoryDataVector ccDV = pD.getCatalogCategories();
    			if (ccDV != null && ccDV.size() > 0) {
    				shoppingCartItemD.setCategory((CatalogCategoryData)ccDV.get(0));
    			} else {
    				shoppingCartItemD.setCategory(null);
    			}

    			int productId = pD.getProductId();
    			ContractItemData ciD = (ContractItemData)contractsHashtable.get(new Integer(productId));
    			if(ciD != null) {
    				double price = 0;
    				if (ciD.getAmount() != null ) {
    					price = ciD.getAmount().doubleValue();
    				}
    				shoppingCartItemD.setPrice(price);
    				shoppingCartItemD.setContractFlag(true);
    			}
    			else {
    				shoppingCartItemD.setPrice(pD.getListPrice());
    				shoppingCartItemD.setContractFlag(false);
    			}

    			itemList.add(shoppingCartItemD);
    		}
    	}
    	catch (SQLException exc) {
    		exc.printStackTrace();
    		throw new RemoteException("prepareShoppingItems, error 2005.1.14");
    	}
        return itemList;
    }
   
   /**
   * Prepares collection of ShoppingCartServiceDataVector. Assigns contract price if exists, othewise assigns list price.
   * If servbice blongs to more than one category, takes the first one
   * @param pStoreTypeCd  the store type. Rules, which sku number is used
   * @param pCatalogId  the catalog identificator
   * @param pContractId the contract identificator or 0 if doesn't appliy
   * @return collection of ShoppingCartServiceData objects
   * @throws            RemoteException Required by EJB 1.0
   */
     public ShoppingCartServiceDataVector prepareShoppingServices(String pStoreTypeCd, SiteData pSiteData,
                                                                  int pCatalogId, int pContractId,AssetData pAsset, ServiceDataVector pServices)
                                                                 throws RemoteException {
         Connection con = null;
         ShoppingCartServiceDataVector serviceList = new ShoppingCartServiceDataVector();
         if (pServices.size() == 0) {
             return serviceList;
         }
         try {
             con = getConnection();
             serviceList = prepareShoppingServices(con, pCatalogId, pContractId,pAsset, pServices);
         }
         catch (NamingException exc) {
             exc.printStackTrace();
             throw new RemoteException("prepareShoppingServices() Naming Exception happened");
         } catch (SQLException exc) {
             exc.printStackTrace();
             throw new RemoteException("prepareShoppingServices() SQL Exception happened");
         }  finally {
             closeConnection(con);
         }

         return serviceList;
     }
    //*******************************************************************************
    public ShoppingCartServiceDataVector prepareShoppingServices(Connection pCon, int pCatalogId, int pContractId, AssetData pAsset,ServiceDataVector pServices)
            throws RemoteException {

        logDebug(" prepareShoppingpServices " +
                 "pCatalogId=" + pCatalogId
               + " pContractId=" + pContractId
               + " pServices.size=" + pServices.size());

        ShoppingCartServiceDataVector serviceList = new ShoppingCartServiceDataVector();
        if (pServices == null || pServices.size() == 0) {
            return serviceList;
        }
        try {

            Hashtable contractsHashtable = new Hashtable();
            IdVector idList = new IdVector();
            //Pickup contract information
            if (pContractId != 0) {

                ContractItemDataVector contractItemDV = new ContractItemDataVector();
                for (int ii = 0; ii < pServices.size(); ii++) {
                    ServiceData servicData = (ServiceData) pServices.get(ii);
                    idList.add(new Integer(servicData.getItemData().getItemId()));
                }
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
                dbc.addOneOf(ContractItemDataAccess.ITEM_ID, idList);
                dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
                contractItemDV = ContractItemDataAccess.select(pCon, dbc);
                if (null != contractItemDV) {
                    java.util.Iterator it = contractItemDV.iterator();
                    while (it.hasNext()) {
                        ContractItemData cid = (ContractItemData) it.next();
                        contractsHashtable.put
                                (new Integer(cid.getItemId()), cid);
                    }
                }
            }

            for (int ii = 0; ii < pServices.size(); ii++) {
                ServiceData serviceData = (ServiceData) pServices.get(ii);
                logDebug(" adding  prepareShoppingServicess " +
                        "pCatalogId=" + pCatalogId
                        + " pContractId=" + pContractId
                        + " serviceData=" + serviceData
                );

                ShoppingCartServiceData shoppingCartServiceD = new ShoppingCartServiceData();
                shoppingCartServiceD.setService(serviceData);

                shoppingCartServiceD.setAssetData(pAsset);
                CatalogCategoryDataVector ccDV = serviceData.getCatalogCategoryDV();
                if (ccDV != null && ccDV.size() > 0) {
                    shoppingCartServiceD.setCategory((CatalogCategoryData) ccDV.get(0));
                } else {
                    shoppingCartServiceD.setCategory(null);
                }

                int serviceId = serviceData.getItemData().getItemId();
                ContractItemData ciD = (ContractItemData) contractsHashtable.get(new Integer(serviceId));
                if (ciD != null) {
                    double price = 0;
                    if (ciD.getAmount() != null) {
                        price = ciD.getAmount().doubleValue();
                    }
                    shoppingCartServiceD.setPrice(price);
                    shoppingCartServiceD.setContractFlag(true);
                } else {
                    Hashtable meta = serviceData.getItemMeta();
                    int priceList = 0;
                    if (meta != null) {
                        ItemMetaData itm = (ItemMetaData) meta.get(ServiceData.ITEM_META.LIST_PRICE);
                        if (itm != null) {
                            try {
                                priceList = Integer.parseInt(itm.getValue());
                            } catch (NumberFormatException e) {

                            }
                        }
                    }
                    shoppingCartServiceD.setPrice(priceList);
                    shoppingCartServiceD.setContractFlag(false);
                }

                logDebug(" adding  prepareShoppingItems " +
                         "pCatalogId=" + pCatalogId
                       + " pContractId=" + pContractId
                       + " shoppingCartItemD=" + shoppingCartServiceD);

                serviceList.add(shoppingCartServiceD);
            }
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("prepareShoppingServicess, error");
        }
        logDebug(" returning from prepareShoppingServicess " +
                "pCatalogId=" + pCatalogId
                + " pContractId=" + pContractId
                + " pServices.size=" + pServices.size());

        return serviceList;
    }

    private ProductDataVector getProducts(Connection con, int pCatalogId, List itemIds,
            AccCategoryToCostCenterView pCategToCostCenterView) throws SQLException, RemoteException{
        return getProducts(con, pCatalogId, itemIds, 0, pCategToCostCenterView );
    }

    private ProductDataVector getProducts(Connection con, int pCatalogId, List itemIds, int siteId, 
    		AccCategoryToCostCenterView pCategToCostCenterView) throws SQLException, RemoteException {

    	//only get items that are in the catalog
    	DBCriteria dbc2 = new DBCriteria();
    	dbc2.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
    	dbc2.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
    	CatalogStructureDataVector catItemDV = CatalogStructureDataAccess.select(con, dbc2);

    	for (int i = 0; i<itemIds.size(); i++) {
    		Integer id = (Integer)itemIds.get(i);
    		boolean found = false;
    		for (int j=0; j<catItemDV.size() && found==false; j++) {
    			CatalogStructureData cat = (CatalogStructureData)catItemDV.get(j);
    			Integer catId = new Integer(cat.getItemId());
    			if (id.equals(catId)) {
    				found = true;
                }
            }
            if(!found) {
                 itemIds.remove(i);
                 i--;
             }
        }
    	ProductDataVector pdv = ShoppingDAO.getProductsByItemId(con, pCatalogId, itemIds,siteId, pCategToCostCenterView); 
    	return pdv;
    }
    
    /******************************************************************************/
    /**
     * Prepares array of category ids for the contract
     * @param pShoppingItemRequest criteria
     * @return int array sorted in increasing order
     * @throws RemoteException if an errors
     */
    public int[] getShoppingCategoryIds(ShoppingItemRequest pShoppingItemRequest) throws RemoteException {

    Connection con = null;
    int[] contractCategoryIds = new int[0];

    try {

      con = getConnection();

      String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

      //empty catalog, just return
      if(!Utility.isSet(itemReq)){
    	  return contractCategoryIds;
      }

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, itemReq);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

      IdVector item1Ids = ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM1_ID,dbc);

      //---- STJ-6114: Performance Improvements - Optimize Pollock 
      log.info("getShoppingCategoryIds() ===>Optimize Pollock BEGIN: item1Ids.size() =" + item1Ids.size());
      ShoppingDAO.filterByProductBundle(con, item1Ids, pShoppingItemRequest);
      log.info("getShoppingCategoryIds() ===>Optimize Pollock END: item1Ids.size() =" + item1Ids.size());
      //----
      if (!Utility.isSet(item1Ids)){
    	item1Ids = new IdVector(); 
    	item1Ids.add(-1);
      }	
	  dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, item1Ids);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM2_ID);

      IdVector resultIdV = ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM2_ID,dbc);

      IdVector wrkVector = new IdVector();
      wrkVector.addAll(resultIdV);
      //
      boolean newCategories = true;
      while(newCategories) {
        dbc = new DBCriteria();
        dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,wrkVector);
        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
        dbc.addOrderBy(ItemAssocDataAccess.ITEM2_ID);
        wrkVector = ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM2_ID,dbc);
        newCategories = false;
        for(int ii=0,jj=0; ii<wrkVector.size(); ii++) {
          Integer newIdI = (Integer)wrkVector.get(ii);
          int newId = newIdI.intValue();
          for(;jj<resultIdV.size();) {
            int existId = ((Integer)resultIdV.get(ii)).intValue();
            if(newId==existId) {
              jj++;
              break;
            }
            if(newId>existId) {
              jj++;
              continue;
            }
            if(newId<existId) {
              resultIdV.add(jj,newIdI);
              newCategories = true;
              break;
            }
          }
          if(jj==resultIdV.size()) {
            resultIdV.add(newIdI);
            newCategories = true;
          }
        }
      }

      if (!resultIdV.isEmpty()) {

          dbc = new DBCriteria();

          dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, resultIdV);
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

          resultIdV = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);
      }

      int size = resultIdV.size();
      contractCategoryIds = new int[size];
      for (int ii = 0; ii < size; ii++) {
          contractCategoryIds[ii] = (Integer) resultIdV.get(ii);
      }

    } catch (NamingException exc) {
      exc.printStackTrace();
      throw new RemoteException("getContractCategoryIds() Naming Exception happened");
    } catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException("getContractCategoryIds() SQL Exception happened");
    } finally {
       closeConnection(con);
    }
      return contractCategoryIds;
  }

    /******************************************************************************/
    /**
     * Prepares array of item ids for the contract
     *
     * @param pShoppingItemRequest criteria
     * @return int array sorted in increasing order
     * @throws RemoteException if an errors
     */
    public int[] getShoppingItemIds(ShoppingItemRequest pShoppingItemRequest) throws RemoteException {

        Connection con = null;

        int[] shoppingItemIds = new int[0];
        try {

            con = getConnection();

            IdVector itemIdV = ShoppingDAO.getShoppingItemIds(con, pShoppingItemRequest);

            int size = itemIdV.size();
            shoppingItemIds = new int[size];
            for (int ii = 0; ii < size; ii++) {
                shoppingItemIds[ii] = ((Integer) itemIdV.get(ii));
            }

        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("getContractCategoryIds() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("getContractCategoryIds() SQL Exception happened");
        } finally {
            closeConnection(con);
        }

        return shoppingItemIds;

    }

    /******************************************************************************/
    /**
     * Picks up all catalog item manufacturers
     *
     * @param pShoppingItemRequest the specific criteria
     * @return collection of BusEntityData objects manufacturer type
     * @throws RemoteException if an errors
     */
    public BusEntityDataVector getCatalogManufacturers(ShoppingItemRequest pShoppingItemRequest) throws RemoteException {

        Connection con = null;
        BusEntityDataVector manuBusEntityDV = null;

        try {

            con = getConnection();

            String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);
            
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemReq);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

            String itemMappingReq = ItemMappingDataAccess.getSqlSelectIdOnly(ItemMappingDataAccess.BUS_ENTITY_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMappingReq);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
            dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);

            manuBusEntityDV = BusEntityDataAccess.select(con, dbc);
             

        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("getCatalogManufacturers() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("getCatalogManufacturers() SQL Exception happened");
        } finally {
            closeConnection(con);
        }

        return manuBusEntityDV;

    }

    /******************************************************************************/
    /**
     * Picks up all catalog item categorie
     * @param pShoppingItemRequest the specific criteria
     * @return collection of ItemData objects
     * @throws RemoteException if an errors
     */
    public ItemDataVector getCatalogCategories(ShoppingItemRequest pShoppingItemRequest) throws RemoteException {

        Connection con = null;
        ItemDataVector itemDV = null;

        try {

            con = getConnection();

            String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, itemReq);
            dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

            String itemAssocReq = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID, itemAssocReq);
            dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
            //dbc.addOrderBy(ItemDataAccess.SHORT_DESC); //---- STJ-6114
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);

            itemDV = ItemDataAccess.select(con, dbc);
            //---- STJ-6114: Performance Improvements - Optimize Pollock 
            log.info("getCatalogCategories() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemDV.size());
            ShoppingDAO.filterByProductBundle(con, itemDV, pShoppingItemRequest);
            log.info("getCatalogCategories() ===>Optimize Pollock END: itemIds.size() =" + itemDV.size());
            Collections.sort(itemDV, new Comparator() {
        		public int compare(Object o1, Object o2)
        		{
        			String name1 = ((ItemData)o1).getShortDesc();
        			String name2 = ((ItemData)o2).getShortDesc();
        			return Utility.compareToIgnoreCase(name1, name2);
        		}
            });
            //----
            
        }catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("getCatalogCategories() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("getCatalogCategories() SQL Exception happened");
        } finally {
            closeConnection(con);
        }

        return itemDV;

    }

 /**
     * Picks up all catalog items, which match criteria. Returns empty collection if all filter parameters are empty.
     * Ignores filter parameter if it is empty. Applies contains ignore case match type
     *
     * @param pStoreTypeCd        the store type. Rules, which sku number is used
     * @param pCustSku            - custormer sku number filter. Applies catalog sku number if exsits, otherwise applies item sku number
     * @param pMfgSku             - manufacturer sku number filter
     * @param pName               - item short description filter. Applies catalog short description if exsits, otherwise applies item short description
     * @param pDesc               - item long description filter
     * @param pCategory           - item lowest level category filter
     * @param pSize               - item size property filter
     * @param pMfgId              - manufacturer Id filter. Unlike other fiters it demands exact equal match type
     * @param pSortBy             - requested sorting
     * @param pGreenCertifiedFlag item certified filter
     * @param pUPCNum             - upc num
     * @param pShoppingItemRequest  -  soecific criteria
     * @return collection of item ids sorted by short description
     * @throws RemoteException if an errors
     */
    public IdVector searchShoppingItems(String pStoreTypeCd,
                                        String pCustSku,
                                        String pMfgSku,
                                        String pName,
                                        String pDesc,
                                        String pCategory,
                                        String pSize,
                                        int pMfgId,
                                        int pSortBy,
                                        boolean pGreenCertifiedFlag,
                                        String pUPCNum,
                                        ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        Connection con = null;
        IdVector itemIdV = null;
        try {

            con = getConnection();

            itemIdV = searchShoppingItems(con,
                    pStoreTypeCd,
                    pCustSku,
                    pMfgSku,
                    pName,
                    pDesc,
                    pCategory,
                    0,
                    pSize,
                    pMfgId,
                    null,
                    pSortBy, pGreenCertifiedFlag,
                    pUPCNum,
                    pShoppingItemRequest);

        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() SQL Exception happened");
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() Exception happened");
        } finally {
            closeConnection(con);
        }

        return itemIdV;
    }

    /******************************************************************************/
    /**
     * Picks up all catalog items, which match criteria. Returns empty collection if all filter parameters are empty.
     * Ignores filter parameter if it is empty. Applies contains ignore case match type
     *
     * @param pStoreTypeCd       the store type. Rules, which sku number is used
     * @param pCustSku           - custormer sku number filter. Applies catalog sku number if exsits, otherwise applies item sku number
     * @param pMfgSku            - manufacturer sku number filter
     * @param pName              - item short description filter. Applies catalog short description if exsits, otherwise applies item short description
     * @param pDesc              - item long description filter
     * @param pCategoryId        - item lowest level category id filter
     * @param pDocType           - document type (MSDS, SPEC, DED)
     * @param pMfgId             - manufacturer Id filter. Unlike other fiters it demands exact equal match type
     * @param pSortBy            - requested sorting
     * @param pShoppingItemRequest -  soecific criteria
     * @return collection of item ids sorted by short description
     * @throws RemoteException if an errors
     */
    public IdVector searchItemDocs(String pStoreTypeCd,
                                   String pCustSku,
                                   String pMfgSku,
                                   String pName,
                                   String pDesc,
                                   int pCategoryId,
                                   String pDocType,
                                   int pMfgId,
                                   int pSortBy,
                                   ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        Connection con = null;
        IdVector itemIdV = null;
        try {
            con = getConnection();
            itemIdV = searchShoppingItems(con,
                    pStoreTypeCd,
                    pCustSku,
                    pMfgSku,
                    pName,
                    pDesc,
                    "",
                    pCategoryId,
                    null,
                    pMfgId,
                    pDocType,
                    pSortBy,
                    false,
                    null,
                    pShoppingItemRequest);
        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchCustormerItems() SQL Exception happened");
        } finally {
            closeConnection(con);
        }
        return itemIdV;
    }

//****************************************************************************
  private IdVector searchShoppingItems(Connection con,
                                       String pStoreTypeCd,
                                       String pCustSku,
                                       String pMfgSku,
                                       String pName,
                                       String pDesc,
                                       String pCategory,
                                       int pCategoryId,
                                       String pSize,
                                       int pMfgId,
                                       String pDocType,
                                       int pSortBy,
                                       boolean pGreenCertifiedFlag,
                                       String pUPCNum,
                                       ShoppingItemRequest pShoppingItemRequest)   throws RemoteException, SQLException
  {

    log.info("searchShoppingItems()=> BEGIN");

    IdVector itemIdV = new IdVector();
    //IdVector itemMsdsPluginDV = new IdVector();
    IdVector itemIdMsdsPluginV = new IdVector();
    int storeType = 0;
    if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(pStoreTypeCd)) {
      storeType = 1;
    }

    log.info("searchShoppingItems()=> pCustSku: "+pCustSku+", storeType: "+storeType);

    String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

    log.info("SVCSVCSVC itemReq="+itemReq);
    boolean conditionsFlag = false;
    DBCriteria dbcItem = new DBCriteria();
    DBCriteria dbcItemMsdsPlugin = new DBCriteria(); //Msds Plug-in: new stmt
    DBCriteria dbc = new DBCriteria();
    new DBCriteria();
    dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemReq);
    dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,itemReq); //Msds Plug-in: new stmt
    dbc.addOneOf(ItemDataAccess.ITEM_ID,itemReq);
    //Description
    if(pDesc!=null && pDesc.trim().length()>0) {
      conditionsFlag = true;
      dbcItem.addLikeIgnoreCase(ItemDataAccess.LONG_DESC,"%"+pDesc+"%");
      dbcItemMsdsPlugin.addLikeIgnoreCase(ItemDataAccess.LONG_DESC,"%"+pDesc+"%"); //Msds Plug-in: new stmt
    }
    //Manufacturer Mapping
    if(pMfgId!=0 ||(pMfgSku!=null && pMfgSku.trim().length()>0)){
      conditionsFlag = true;
      dbc = new DBCriteria();
      if(pMfgId!=0) {
        dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID,pMfgId);
      }
      if(pMfgSku!=null && pMfgSku.trim().length()>0) {
        // STJ-5985 adding search by mfg sku in NewUI
        dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM,"%"+pMfgSku.toUpperCase()+"%");
    	//  dbc.addEqualToIgnoreCase(ItemMappingDataAccess.ITEM_NUM, pMfgSku.toUpperCase());
        //dbc.addLike(ItemMappingDataAccess.BUS_ENTITY_ID, "%"+pMfgSku.toUpperCase()+"%");
      }
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      String mappingReq = ItemMappingDataAccess.getSqlSelectIdOnly(ItemMappingDataAccess.ITEM_ID,dbc);
      log.info("Mfg mappingReq= "+mappingReq);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID,mappingReq);
      dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,mappingReq); //Msds Plug-in: new stmt
    }
    //Customer Mapping added new
    /*
    if(pCustSku!=null && pCustSku.trim().length()>0){
    	if(storeType!=0) {
	        conditionsFlag = true;
	        dbc = new DBCriteria();

	        //dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM,"%"+pCustSku.toUpperCase()+"%");
	        dbc.addEqualToIgnoreCase(ItemMappingDataAccess.ITEM_NUM, pCustSku.toUpperCase());
	        String mappingReq = ItemMappingDataAccess.getSqlSelectIdOnly(ItemMappingDataAccess.ITEM_ID,dbc);
	        dbcItem.addOneOf(ItemDataAccess.ITEM_ID,mappingReq);
	        dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,mappingReq); //Msds Plug-in: new stmt
    	}
    }
    */
    //Category
    if(pCategory!=null && pCategory.trim().length()>0 || pCategoryId>0) {
      conditionsFlag = true;
      dbc = new DBCriteria();
      if(pCategory!=null && pCategory.trim().length()>0) {
        dbc.addLikeIgnoreCase(ItemDataAccess.SHORT_DESC,"%"+pCategory.toUpperCase()+"%");
      }
      if(pCategoryId>0) {
        dbc.addEqualTo(ItemDataAccess.ITEM_ID,pCategoryId);
      }
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      String categoryReq = ItemDataAccess.getSqlSelectIdOnly(ItemDataAccess.ITEM_ID,dbc);
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,pShoppingItemRequest.getShoppingCatalogId());
      dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID,categoryReq);
      String itemCategoryReq = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM1_ID,dbc);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemCategoryReq);
      dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,itemCategoryReq);
    }
    
    //Size
    if(pSize!=null && pSize.trim().length()>0) {
      conditionsFlag = true;
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,itemReq);
      dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,ProductData.SIZE);
      dbc.addLikeIgnoreCase(ItemMetaDataAccess.CLW_VALUE,"%"+pSize.toUpperCase()+"%");
      String itemSizeReq = ItemMetaDataAccess.getSqlSelectIdOnly(ItemMetaDataAccess.ITEM_ID,dbc);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemSizeReq);
      dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,itemSizeReq);
    }

    // UPC Num
    if (pUPCNum != null && pUPCNum.trim().length() > 0) {
        conditionsFlag = true;
        dbc = new DBCriteria();
        dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,itemReq);
        dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,ProductData.UPC_NUM);
        dbc.addLikeIgnoreCase(ItemMetaDataAccess.CLW_VALUE,"%"+pUPCNum.toUpperCase()+"%");
        String itemSizeReq = ItemMetaDataAccess.getSqlSelectIdOnly(ItemMetaDataAccess.ITEM_ID,dbc);
        dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemSizeReq);
        dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,itemSizeReq);
    }

    //Documents: SPEC & DED
    //if("MSDS".equalsIgnoreCase(pDocType)||"SPEC".equalsIgnoreCase(pDocType)||"DED".equalsIgnoreCase(pDocType)) { //old stmt
    if("SPEC".equalsIgnoreCase(pDocType)||"DED".equalsIgnoreCase(pDocType)) {  // Msds Plug-in: new stmt    
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,itemReq);
      dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,pDocType);
      dbc.addCondition("TRIM(clw_value) IS NOT NULL");
      String itemSizeReq = ItemMetaDataAccess.getSqlSelectIdOnly(ItemMetaDataAccess.ITEM_ID,dbc);
      log.info("SVCSVCSVC itemSizeReq="+itemSizeReq);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemSizeReq);
      dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,itemSizeReq);
      
      log.info("SVCSVCSVC dbcItemRequest: "+ItemDataAccess.getSqlSelectIdOnly("*",dbcItem));
    }  
      
    //Documents: MSDS
    if("MSDS".equalsIgnoreCase(pDocType)) {  // Msds Plug-in: new stmt    
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,itemReq);
      dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,pDocType);
      dbc.addCondition("TRIM(clw_value) IS NOT NULL");
      String itemSizeReq = ItemMetaDataAccess.getSqlSelectIdOnly(ItemMetaDataAccess.ITEM_ID,dbc);
      log.info("SVCSVCSVC itemSizeReq="+itemSizeReq);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemSizeReq);
      
      log.info("SVCSVCSVC dbcItemRequest: "+ItemDataAccess.getSqlSelectIdOnly("*",dbcItem));
      
      /*** Enhancement STJ-3778 (getting MSDS via JohnsonDiversey Web Services): Begin ***/
      
      // Find in the DB product items, that have MSDS Plug-in SET
      
      String itemIdsMsdsPluginSQL =     	
        	  
              " select i.item_id " + 
        	  
        	  " from clw_item i, clw_item_mapping im, clw_property p " +
        	  
        	  " where i.item_id = im.item_id " +
        	  
        	  " and im.item_mapping_cd = '"+RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER+"' " +      	  

        	  " and i.item_status_cd = '"+RefCodeNames.STATUS_CD.ACTIVE+"' " +     	  
        	  
        	  " and im.bus_entity_id = p.bus_entity_id " + /* item Manufacturer */
        	  
        	  " and p.short_desc = '"+RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN+"' " +
        	  
        	  " and p.clw_value = '"+RefCodeNames.MSDS_PLUGIN_CD.DIVERSEY_WEB_SERVICES+"' " +
        	  
        	  " and p.property_type_cd = '"+RefCodeNames.PROPERTY_TYPE_CD.EXTRA+"'";
      
      log.info("SVCSVCSVC itemIdsMsdsPluginSQL = " + itemIdsMsdsPluginSQL);
      
      dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID, itemIdsMsdsPluginSQL);
      
      /*** Enhancement STJ-3778 (getting MSDS via JohnsonDiversey Web Services): End ***/         
    }
    //Item Name or Sku Number

    if (pName!=null)
      pName = pName.trim();
    boolean nameFlag = (pName!=null && pName.trim().length()>0)?true:false;

    //boolean	skuFlag = (pMfgSku!=null && pMfgSku.trim().length()>0)?true:false;
    boolean	skuFlag = (pCustSku!=null && pCustSku.trim().length()>0)?true:false;

    log.info("nameFlag="+nameFlag+" skuFlag="+skuFlag);
    if(nameFlag || skuFlag) {
      //Item  Name (ShortDesc)
      IdVector itemSkuNameIds = null;
      IdVector itemNameIds = null;
      if(nameFlag) {
        conditionsFlag = true;
        itemNameIds = new IdVector();
        dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID,itemReq);
        dbc.addLikeIgnoreCase(ItemDataAccess.SHORT_DESC,"%"+pName+"%");
        dbc.addOrderBy(ItemDataAccess.ITEM_ID);
        IdVector itemIds = ItemDataAccess.selectIdOnly(con,ItemDataAccess.ITEM_ID,dbc);

        dbc = new DBCriteria();

        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,itemIds);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pShoppingItemRequest.getShoppingCatalogId());

        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con,dbc);
        int size = itemIds.size();
        if(size!=csDV.size()) {
          throw new RemoteException("No consitency in Item - Catalog Structure for name substring: "+pName+"." +
                  " Catalog Id: "+pShoppingItemRequest.getShoppingCatalogId());
        }
        for(int ii=0; ii<size; ii++) {
          Integer idI = (Integer) itemIds.get(ii);
          int id = idI.intValue();
          CatalogStructureData csD = (CatalogStructureData) csDV.get(ii);
          if(csD.getItemId()!=id){
            throw new RemoteException("No consitency in Item - Catalog Structure for name substring: "+pName+"." +
                    " Catalog Id: "+pShoppingItemRequest.getShoppingCatalogId()+"." +
                    " Clw_item item id: "+id+"." +
                    " Clw_catalog_structure item id: "+csD.getItemId());
          }
          String name = csD.getShortDesc();
          if(name==null || name.trim().length()==0) {
            itemNameIds.add(idI);
          }
        }
      }
      //Item Sku Number
      IdVector itemSkuIds = null;
      String itemsWithSku = null;
      IdVector priceListItemIds = new IdVector();
      if(skuFlag) {

        conditionsFlag = true;
        itemSkuIds = new IdVector();
        IdVector itemIds = null;

        String productBundle = ShoppingDAO.getProductBundleValue(con, pShoppingItemRequest.getSiteId());

          if (Utility.isSet(productBundle)) {

              IdVector priceListIds = Utility.toIdVector(
                      pShoppingItemRequest.getPriceListRank1Id(),
                      pShoppingItemRequest.getPriceListRank2Id()
              );

              // try to find in a price lists
              DBCriteria pldDbc = new DBCriteria();
              pldDbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
              pldDbc.addOneOf(PriceListDetailDataAccess.ITEM_ID, itemReq);
              pldDbc.addLikeIgnoreCase(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, "%" + pCustSku + "%");

              priceListItemIds = PriceListDetailDataAccess.selectIdOnly(con, PriceListDetailDataAccess.ITEM_ID, pldDbc);

              pldDbc = new DBCriteria();
              pldDbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
              pldDbc.addIsNotNull("TRIM(" + PriceListDetailDataAccess.CUSTOMER_SKU_NUM + ")");

              itemsWithSku = PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, pldDbc);

          }

        if(storeType==0) {
          //MLA or Other store
          dbc = new DBCriteria();
          dbc.addOneOf(ItemDataAccess.ITEM_ID,itemReq);

          dbc.addLikeIgnoreCase(ItemDataAccess.SKU_NUM,"%"+pCustSku+"%");
          //dbc.addLikeIgnoreCase(ItemDataAccess.SKU_NUM,"%"+pMfgSku+"%");
          dbc.addOrderBy(ItemDataAccess.ITEM_ID);
          itemIds = ItemDataAccess.selectIdOnly(con,ItemDataAccess.ITEM_ID,dbc);

        }

          if (storeType == 1) {

              //Distributor store
              //dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM,"%"+pMfgSku+"%");
              if (Utility.isSet(productBundle)) {
                  dbc = new DBCriteria();

                  dbc.addJoinTableEqualTo(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
                  dbc.addJoinTableOneOf(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID, itemReq);
                  dbc.addJoinTableLikeIgnoreCase(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_NUM, "%" + pCustSku + "%");
                  dbc.addJoinCondition(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID, CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID);
                  dbc.addJoinTableIsNullOrSpace(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CUSTOMER_SKU_NUM);
                  dbc.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
                  dbc.addJoinTableOrderBy(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID);
                  if (itemsWithSku != null) {
                      dbc.addJoinTableNotOneOf(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID, itemsWithSku);
                  }

                  itemIds = JoinDataAccess.selectIdOnly(con, ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID, dbc, 0);

              } else {

                  dbc = new DBCriteria();

                  dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
                  dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemReq);
                  dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM, "%" + pCustSku + "%");

                  String distrCondition =
                          "item_id = (select item_id from CLW_CATALOG_STRUCTURE " +
                                  " where item_id=CLW_ITEM_MAPPING.item_id " +
                                  " and (CLW_CATALOG_STRUCTURE.customer_sku_num is null " +
                                  " or CLW_CATALOG_STRUCTURE.customer_sku_num = ' ') " +
                                  " and CLW_CATALOG_STRUCTURE.catalog_id = " + pShoppingItemRequest.getShoppingCatalogId() + ")";

                  dbc.addCondition(distrCondition);
                  dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);

                  itemIds = ItemMappingDataAccess.selectIdOnly(con, ItemMappingDataAccess.ITEM_ID, dbc);

              }
          }

        if(storeType==2) {
          //Manufacturer store
          dbc = new DBCriteria();
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
          dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,itemReq);

          //dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM,"%"+pCustSku+"%");
          dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM,"%"+pMfgSku+"%");
          dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
          itemIds = ItemMappingDataAccess.selectIdOnly(con,ItemMappingDataAccess.ITEM_ID,dbc);
        }

        dbc = new DBCriteria();
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,itemIds);

        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pShoppingItemRequest.getShoppingCatalogId());
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
        //log.info("ShoppingServicesBean SSSSSSSSSSSSSSSSSS cat structure request: "+CatalogStructureDataAccess.getSqlSelectIdOnly("*",dbc));
        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con,dbc);
        int size = itemIds.size();
        if(size!=csDV.size()) {
          throw new RemoteException("No consitency in Item - Catalog Structure for sku number substring: "+pCustSku+"." +
                  " Catalog Id: "+pShoppingItemRequest.getShoppingCatalogId());
        }
        for(int ii=0; ii<size; ii++) {
          Integer idI = (Integer) itemIds.get(ii);
          int id = idI.intValue();
          CatalogStructureData csD = (CatalogStructureData) csDV.get(ii);
          if(csD.getItemId()!=id){
            throw new RemoteException("No consitency in Item - Catalog Structure for sku number substring: "+pCustSku+"." +
                    " Catalog Id: "+pShoppingItemRequest.getShoppingCatalogId()+"." +
                    " Clw_item item id: "+id+"." +
                    " Clw_catalog_structure item id: "+csD.getItemId());
          }
          String sku = csD.getCustomerSkuNum();
          if(sku==null || sku.trim().length()==0) {
            itemSkuIds.add(idI);
          }
        }
      }
      if(itemNameIds!=null && itemSkuIds!=null) {
        itemSkuNameIds = new IdVector();
        int size1 = itemNameIds.size();
        int size2 = itemSkuIds.size();
        for(int ii=0,jj=0; ii<size1 && jj<size2; ) {
          Integer idI1 = (Integer) itemNameIds.get(ii);
          Integer idI2 = (Integer) itemSkuIds.get(jj);
          int id1 = idI1.intValue();
          int id2 = idI2.intValue();
          if(id1==id2) {
            itemSkuNameIds.add(idI1);
            ii++;
            jj++;
          }
          else if(id1<id2) {
            ii++;
          }
          else{
            jj++;
          }
        }
      }
      else if(itemNameIds!=null) {
        itemSkuNameIds = itemNameIds;
      }
      else if(itemSkuIds!=null) {
        itemSkuNameIds = itemSkuIds;
      }
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pShoppingItemRequest.getShoppingCatalogId());
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,itemReq);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      if(nameFlag) {
        dbc.addLikeIgnoreCase(CatalogStructureDataAccess.SHORT_DESC,"%"+pName+"%");
      }
      if(skuFlag) {
        dbc.addLikeIgnoreCase(CatalogStructureDataAccess.CUSTOMER_SKU_NUM,"%"+pCustSku+"%");
        if(itemsWithSku!= null){
            dbc.addNotOneOf(CatalogStructureDataAccess.ITEM_ID, itemsWithSku);
        }
        //dbc.addLikeIgnoreCase(CatalogStructureDataAccess.CUSTOMER_SKU_NUM,"%"+pMfgSku+"%");
      }
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      //log.info("ShoppingServicesBean SSSSSSSSSSSSSSSSSS cat structure request1: "+CatalogStructureDataAccess.getSqlSelectIdOnly("*",dbc));
      IdVector itemIds = CatalogStructureDataAccess.selectIdOnly(con,CatalogStructureDataAccess.ITEM_ID,dbc);
      itemSkuNameIds.addAll(itemIds);
      itemSkuNameIds.addAll(priceListItemIds);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID,itemSkuNameIds);
      dbcItemMsdsPlugin.addOneOf(ItemDataAccess.ITEM_ID,itemSkuNameIds);
    }
    if(pGreenCertifiedFlag)
    {
        dbcItem.addJoinTable("(Select item_id,count(item_id) " +
                " as counts from clw_item_mapping  where item_mapping_cd='" +
               RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY+"'"+
                "                   group by item_id) certcomp");
        dbcItemMsdsPlugin.addJoinTable("(Select item_id,count(item_id) " +
                " as counts from clw_item_mapping  where item_mapping_cd='" +
               RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY+"'"+
                "                   group by item_id) certcomp");
        dbcItem.addCondition("item_id = certcomp.item_id and certcomp.counts>0");
        dbcItemMsdsPlugin.addCondition("item_id = certcomp.item_id and certcomp.counts>0");
        conditionsFlag=true;
    }
    log.info("SSSSSSSSSSSSSSSSSS conditionsFlag: "+conditionsFlag);
    log.info("SSSSSSSSSSSSSSSSSS pGreenCertifiedFlag: "+pGreenCertifiedFlag);
    if(conditionsFlag) {
       log.info("SVCSVCSVC itemRequest: "+ItemDataAccess.getSqlSelectIdOnly("*",dbcItem));
       log.info("SVCSVCSVC itemRequestMsdsPlugin: "+ItemDataAccess.getSqlSelectIdOnly("*",dbcItemMsdsPlugin));
          if(!pGreenCertifiedFlag)
          {
              itemIdV=ItemDataAccess.selectIdOnly(con,ItemDataAccess.ITEM_ID,dbcItem);
              itemIdMsdsPluginV=ItemDataAccess.selectIdOnly(con,ItemDataAccess.ITEM_ID,dbcItemMsdsPlugin);
              //for(int ii=0; ii<itemIdV.size(); ii++){
              //	  log.info("************** "+itemIdV.get(ii));
              //}
              log.info("************** itemIdMsdsPluginV = " + itemIdMsdsPluginV);
          }
          else
          {
              ItemDataVector itemDV = ItemDataAccess.select(con,dbcItem);
              itemIdV=getItemIds(itemDV);                            
              //for(int ii=0; ii<itemIdV.size(); ii++){
              //	  log.info("############## "+itemIdV.get(ii));
              //}
              ItemDataVector itemMsdsPluginDV = ItemDataAccess.select(con,dbcItemMsdsPlugin);
              itemIdMsdsPluginV=getItemIds(itemMsdsPluginDV);
              log.info("############## itemIdMsdsPluginV = " + itemIdMsdsPluginV);
          }
    }    

    if("MSDS".equalsIgnoreCase(pDocType) && itemIdMsdsPluginV.size() > 0) { // Msds Plug-in: new stmt
    	// remove product items from itemIdMsdsPluginV for which MSDS plug-in IS SET;
    	// reason: if a product item has MSDS Plug-in SET and simulteneously has a PDF file on the server,
    	// PDF file has a preference (= PDF file rules)
    	
    	//log.info("inside");
        for (int i1=0; i1 < itemIdMsdsPluginV.size(); i1++)
        {	
        	//log.info("inside the loop 1");
        	for (int i2=0; i2 < itemIdV.size(); i2++)
        	{	       		
               //log.info("inside the loop 2");
               //log.info("1 " + itemIdMsdsPluginV.get(i1));
               //log.info("2 " + itemIdV.get(i2));
        	   if (itemIdMsdsPluginV.get(i1).equals(itemIdV.get(i2)))
        	   {   
        		  //remove element from itemIdMsdsPluginV ArrayList
        		  //log.info("item will be removed: " + itemIdMsdsPluginV.get(i1));
        		  itemIdMsdsPluginV.remove(i1);
        		  //log.info("%%%%%%%%%%%% itemIdMsdsPluginV " + itemIdMsdsPluginV);
        	   }
            }
        } 
        //log.info("SVCSVCSVC itemIdV_1 = " + itemIdV);    
        //log.info("SVCSVCSVC itemIdMsdsPluginV_1 = " + itemIdMsdsPluginV);
    
        if (itemIdMsdsPluginV.size() != 0) {    	
           itemIdV.addAll(itemIdMsdsPluginV); 
        }
    }
    //log.info("SVCSVCSVC itemIdV_2 = " + itemIdV);
    
    /*** Enhancement STJ-3778 (getting MSDS via JohnsonDiversey Web Services): End ***/     
    
 	IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
	IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
    applyPollockFilter(itemIdV, excludeItemIds, includeItemIds, true);
    itemIdV = sortItems(con, pStoreTypeCd, pShoppingItemRequest.getShoppingCatalogId(), itemIdV, null, pSortBy);

    //log.info("SVCSVCSVC itemIdV_3 = " + itemIdV);
    
    log.info("searchShoppingItems()=> END.");

    return itemIdV;
  }
  
  ///////////////////
  //---- STJ-6114: Performance Improvements - Optimize Pollock 
  private void applyPollockFilter(IdVector pItemIds, IdVector pExcludeItemIds, IdVector pIncludeItemIds, boolean toSortFl) {
		if(pItemIds==null) return;
		if(pExcludeItemIds==null && pIncludeItemIds==null) return;
		if(toSortFl && pItemIds.size()>1) pItemIds.sort();
  
		int exclItemId = 0;
		int inclItemId = 0;
		Iterator iterExcl = null;
		if(pExcludeItemIds!=null) iterExcl = pExcludeItemIds.iterator();
		Iterator iterIncl = null;
		if(pIncludeItemIds!=null) iterIncl = pIncludeItemIds.iterator();
		for(Iterator iter = pItemIds.iterator(); iter.hasNext();){
			int itemId = (Integer) iter.next();
//logInfo("applyPollockFilter. Trying item: " + itemId );
			while(iterExcl!=null && iterExcl.hasNext()) {
				if(exclItemId < itemId) {
					exclItemId = (Integer) iterExcl.next();
				} else {
					break;
				}
			}
			if(itemId == exclItemId) {
	 			logInfo("applyPollockFilter. Skipping proprietary item. ItemId: " + itemId );
				iter.remove();
				continue;
			}
			if(iterIncl!=null) {
				while(iterIncl.hasNext()) {
					if(inclItemId < itemId) {
						inclItemId = (Integer) iterIncl.next();				
					} else {
						break;
					}
				}
				if(itemId != inclItemId) {
					logInfo("applyPollockFilter. Skipping not list item. ItemId: " + itemId );
					iter.remove();
					continue;
				}
			}
			//logInfo("applyPollockFilter. Item accepted. ItemId: " + itemId );
		}
	}

  ////////////////////

    private IdVector getItemIds(ItemDataVector itemDV) {
        IdVector itemIds = new IdVector();
        if (itemDV != null && itemDV.size() > 0) {
            Iterator iter = itemDV.iterator();
            while (iter.hasNext()) {
                itemIds.add(new Integer(((ItemData) iter.next()).getItemId()));
            }
        }
        return itemIds;
    }

    /******************************************************************************/
  private class IdNamePair {
    private int _id;
    private String _name;
    IdNamePair(int pId, String pName) {
      _id = pId;
      _name = pName;
      if ( _name == null ) {
    _name = "";
      }
    }
    int getId() {return _id;}
    String getName() {return _name;}
  }

  public class ServiceFeeDetail{

	  public int itemId;
	  public String code;
	  public BigDecimal amount;
	  public int ruleId;

	  public ServiceFeeDetail(){

	  }
	  public ServiceFeeDetail(int item, String c, BigDecimal amt, int rId){
		  itemId = item;
		  code = c;
		  amount = amt;
		  ruleId = rId;
	  }

	  public String toString(){
		  String str =
		  " [Item id]:"+itemId+
		  " [Code]:"+code+
		  " [Amount]:"+amount+
		  " [Rule Id]:"+ruleId;
		  return str;
	  }

	  public String getCode(){
		  return code;
	  }
	  public void setCode(String c){
		  this.code = c;
	  }

	  public int getRuleId(){
		  return ruleId;
	  }
	  public void setRuleId(int rId){
		  this.ruleId = rId;
	  }

	  public BigDecimal getAmount(){
		  return amount;
	  }
	  public void setAmount(BigDecimal amt){
		  this.amount= amt;
	  }
  }
  /******************************************************************************/
  /**
   * Sorts items.
   * @param pStoreType  the store type. Rules, which sku number is used
   * @param pCatalogId  the catalog identificator
   * @param pItemsId the collection of items
   * @param pSortBy determins sort order (is sort by short_dscription or sku number, takes first catalog value, and if it is null, takes item value
   * @return collection of item ids sorted by short description
   * @throws            RemoteException
   */
  public IdVector sortItems(String pStoreType, int pCatalogId, IdVector pItemIds, int pSortBy)
    throws RemoteException
  {
    Connection con = null;
    try {
      con = getConnection();
      pItemIds = sortItems(con, pStoreType, pCatalogId, pItemIds, null, pSortBy);
      }
    catch (NamingException exc) {
      exc.printStackTrace();
      throw new RemoteException("orderByShortDesc() Naming Exception happened");
    }
    catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException("orderByShortDesc() SQL Exception happened");
    }
    finally {
  logInfo("sortItems close start");
      try {
         con.close();
      }
      catch (SQLException exc) {
    logInfo("sortItems close exception ");
    exc.printStackTrace();
        throw new RemoteException("orderByShortDesc() SQL Exception happened");
      }
      logInfo("sortItems close end");

    }
    return pItemIds;
  }
  //************************************************************************************
  private  IdVector sortItems(Connection pCon, String pStoreType, int pCatalogId, IdVector pItemIds, String pItemReq, int pSortBy)
    throws RemoteException, SQLException
  {
    switch(pSortBy) {
      case Constants.ORDER_BY_CATEGORY:
        pItemIds = sortByCategory(pCon, pCatalogId, pItemIds, pItemReq);
        break;
      case Constants.ORDER_BY_CUST_SKU:
        pItemIds = sortBySkuNum(pCon, pStoreType, pCatalogId, pItemIds, pItemReq);
        break;
      case Constants.ORDER_BY_NAME:

        pItemIds = sortByShortDesc(pCon, pCatalogId, pItemIds, pItemReq);
        break;
      case Constants.ORDER_BY_MFG_NAME:
        pItemIds = sortByMfgName(pCon, pItemIds, pItemReq);
        break;
      case Constants.ORDER_BY_MFG_SKU:
        pItemIds = sortByMfgSku(pCon, pItemIds, pItemReq);
        break;
      default:
    String m = " Unknown sort request type SortBy=" + pSortBy;
    logError(m);
        throw new RemoteException(m);
    }
    return pItemIds;
  }

  /**
   * Sorts items by short description. Takes short description from clw_catalog_structure table or if it does not exist from clw_item tabe
   * @param pCon the database connection
   * @param pCatalogId  the catalog identificator
   * @param pItemsId the collection of items
   * @return collection of item ids sorted by short description
   * @throws            RemoteException
   */
  private IdVector sortByShortDesc(Connection pCon, int pCatalogId, IdVector pItemIds, String pItemReq)
    throws RemoteException, SQLException
  {
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      if(pItemIds!=null) {
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,pItemIds);
      }
      if(pItemReq!=null) {
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,pItemReq);
      }
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      CatalogStructureDataVector catalogStructureDV = CatalogStructureDataAccess.select(pCon,dbc);

      dbc = new DBCriteria();
      if(pItemIds!=null) {
        dbc.addOneOf(ItemDataAccess.ITEM_ID,pItemIds);
      }
      if(pItemReq!=null) {
        dbc.addOneOf(ItemDataAccess.ITEM_ID,pItemReq);
      }
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemDataVector itemDV = ItemDataAccess.select(pCon,dbc);

      if(catalogStructureDV.size()!=itemDV.size()) {
        throw new RemoteException("orderByShortDesc.1. clw_catalog_structure table does not match clw_item table. Catalog id = "+pCatalogId);
      }
      //Create pair vector
      int itemSize = itemDV.size();
      IdNamePair[] pairs = new IdNamePair[itemSize];
      for(int ii=0; ii<itemSize; ii++ ) {
        CatalogStructureData csD = (CatalogStructureData) catalogStructureDV.get(ii);
        ItemData iD = (ItemData) itemDV.get(ii);
        int csId = csD.getItemId();
        int iId = iD.getItemId();
        if(csId!=iId) {
          throw new RemoteException("orderByShortDesc.2. clw_catalog_structure table does not match clw_item table. Catalog id = "+pCatalogId);
        }
        String name = csD.getShortDesc();
        if(name==null || name.trim().length()==0) {
          name = iD.getShortDesc();
        }
        String sku = csD.getCustomerSkuNum();
        if(sku==null || sku.trim().length()==0) {
          sku = ""+iD.getSkuNum();
        }
        String ss = (name==null)?"":name;
        ss+=(sku==null)?"":sku;
        pairs[ii] = new IdNamePair(iId,ss);
      }
      pItemIds = orderPairs(pairs);

    return pItemIds;
  }
  /******************************************************************************/
  /**
   * Sorts items by sku number. Takes sku number from clw_catalog_structure table or if it does not exist from clw_item tabe
   * @param pStoreType (store type defined in RefCodeNames interface)
   * @param pCon the database connection
   * @param pCatalogId  the catalog identificator
   * @param pItemsId the collection of items
   * @return collection of item ids sorted by short description
   * @throws            RemoteException
   */
  private IdVector sortBySkuNum(Connection pCon, String pStoreType,
      int pCatalogId, IdVector pItemIds, String pItemReq)
      throws RemoteException, SQLException {

    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
        RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
    if (pItemIds != null) {
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, pItemIds);
    }
    if (pItemReq != null) {
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, pItemReq);
    }
    dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
    CatalogStructureDataVector catalogStructureDV = CatalogStructureDataAccess
        .select(pCon, dbc);

    ItemMappingDataVector itemMappingDV = null;
    ItemDataVector itemDV = null;
    if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(pStoreType)) {
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
          RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
      if (pItemIds != null) {
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, pItemIds);
      }
      if (pItemReq != null) {
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, pItemReq);
      }
      String distrCond = "bus_entity_id = (SELECT bus_entity_id FROM clw_catalog_structure WHERE item_id = clw_item_mapping.item_id AND catalog_id = "
          + pCatalogId
          + " AND catalog_structure_cd = '"
          + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "')";
      dbc.addCondition(distrCond);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
      itemMappingDV = ItemMappingDataAccess.select(pCon, dbc);
    } else {
      dbc = new DBCriteria();
      if (pItemIds != null) {
        dbc.addOneOf(ItemDataAccess.ITEM_ID, pItemIds);
      }
      if (pItemReq != null) {
        dbc.addOneOf(ItemDataAccess.ITEM_ID, pItemReq);
      }
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      itemDV = ItemDataAccess.select(pCon, dbc);
    }

    // Create pair vector for sku sorting. only include those items
    // with skus. this is important for distributor stores. since the
    // distributor sku is used to shop from these stores, this is the
    // sku that must be sorted.
    int size = catalogStructureDV.size();
    Hashtable skustab = new Hashtable();

    if (itemMappingDV != null) {

      for (int ii = 0; ii < size; ii++) {
        CatalogStructureData csD = (CatalogStructureData) catalogStructureDV
            .get(ii);
        int csId = csD.getItemId();

        for (int jj = 0; jj < itemMappingDV.size(); jj++) {
          ItemMappingData imD = (ItemMappingData) itemMappingDV
              .get(jj);
          int iId = imD.getItemId();
          if (csId == iId) {
            String sku = csD.getCustomerSkuNum();
            if (sku == null || sku.trim().length() == 0) {
              sku = "" + imD.getItemNum();
            }
            String ss = (sku == null) ? "" : sku;
            skustab.put(new Integer(iId), ss);
            break;
          }
        }
      }

    } else {

      for (int ii = 0; ii < size; ii++) {
        CatalogStructureData csD = (CatalogStructureData) catalogStructureDV
            .get(ii);
        int csId = csD.getItemId();

        for (int jj = 0; jj < itemDV.size(); jj++) {
          ItemData iD = (ItemData) itemDV.get(jj);
          int iId = iD.getItemId();
          if (csId == iId) {

            String name = csD.getShortDesc();
            if (name == null || name.trim().length() == 0) {
              name = iD.getShortDesc();
            }
            String sku = csD.getCustomerSkuNum();
            if (sku == null || sku.trim().length() == 0) {
              sku = "" + iD.getSkuNum();
            }
            String ss = (sku == null) ? "" : sku;
            ss += (name == null) ? "" : name;
            skustab.put(new Integer(iId), ss);
            break;
          }
        }
      }
    }

    size = skustab.size();
    IdNamePair[] pairs = new IdNamePair[size];
    Enumeration keys = skustab.keys();
    int fct = 0;
    while (keys.hasMoreElements()) {
      Integer k = (Integer) keys.nextElement();
      pairs[fct] = new IdNamePair(k.intValue(), (String) skustab.get(k));
      fct++;
    }
    pItemIds = orderPairs(pairs);

    return pItemIds;
  }
  /** *************************************************************************** */
  /**
   * Sorts items by category. One item should have only one category in the
   * catalog. If item has more than one category, the first one would be used
   *
   * @param pCon
   *            the database connection
   * @param pCatalogId
   *            the catalog identificator
   * @param pItemsId
   *            the collection of items
   * @return collection of item ids sorted by short description
   * @throws RemoteException
   */
  private IdVector sortByCategory(Connection pCon, int pCatalogId, IdVector pItemIds, String pItemReq)
    throws RemoteException, SQLException
  {
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      if(pItemIds!=null) {
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,pItemIds);
      }
      if(pItemReq!=null) {
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID,pItemReq);
      }
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      CatalogStructureDataVector catalogStructureDV = CatalogStructureDataAccess.select(pCon,dbc);


      dbc = new DBCriteria();
      if(pItemIds!=null) {
        dbc.addOneOf(ItemDataAccess.ITEM_ID,pItemIds);
      }
      if(pItemReq!=null) {
        dbc.addOneOf(ItemDataAccess.ITEM_ID,pItemReq);
      }
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(pCon,dbc);

      dbc = new DBCriteria();
      if(pItemIds!=null) {
         dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,pItemIds);
      }
      if(pItemReq!=null) {
         dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,pItemReq);
      }
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,pCatalogId);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM2_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(pCon,dbc);

      IdVector categoryIdV = new IdVector();
      int assocSize = itemAssocDV.size();
      for(int ii=0; ii<assocSize; ii++) {
        ItemAssocData itemAssocD = (ItemAssocData) itemAssocDV.get(ii);
        categoryIdV.add(new Integer(itemAssocD.getItem2Id()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,categoryIdV);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector categoryDV = ItemDataAccess.select(pCon,dbc);

      if(catalogStructureDV.size()!=itemDV.size()) {
        throw new RemoteException("orderByCategory.7. clw_catalog_structure table does not match clw_item table. Catalog id = "+pCatalogId);
      }
      int itemSize = itemDV.size();
      int categorySize = categoryDV.size();
      //Return if no categories found
      if(categorySize==0) {
        IdVector ids = new IdVector();
        for(int ii=0; ii<itemSize; ii++) {
          ItemData iD = (ItemData) itemDV.get(ii);
          ids.add(new Integer(iD.getItemId()));
        }
        return ids;
      }
      //Create pairs
      IdNamePair[] pairs = new IdNamePair[itemSize];
      int ii=0;
      int jj=0;
      while(ii<itemSize && jj<assocSize) {
        ItemData itemD = (ItemData)itemDV.get(ii);
        ItemAssocData assocD = (ItemAssocData) itemAssocDV.get(jj);
        int itemId = itemD.getItemId();
        int itemAssocId = assocD.getItem1Id();
        if(itemId==itemAssocId) {
          int catId = assocD.getItem2Id();
          int nn1 = 0;
          int nn2 = categorySize;
          int nn = (nn1+nn2)/2;
          boolean foundFlag = false;
          ItemData categoryD = null;
          while(true) {
            categoryD = (ItemData) categoryDV.get(nn);
            int nnId = categoryD.getItemId();
            if(catId==nnId) {
              foundFlag = true;
              break;
            }
            if(nn2-nn1>1) {
              if(catId<nnId) nn2 = nn; else nn1 =nn;
              nn = (nn1+nn2)/2;
            }else {
              categoryD = (ItemData) categoryDV.get(nn2);
              nnId = categoryD.getItemId();
              if(catId==nnId) {
                foundFlag = true;
              }
              break;
            }
          }
          if(!foundFlag) {
            throw new RemoteException("orderByCategory() No category consistance. Catalog id = "+pCatalogId+" Item id = "+itemId);
          } else {
            String ss = categoryD.getShortDesc();
            if(ss==null) ss = "";
            String name = itemD.getShortDesc();
            if(name!=null) ss+=name;
            pairs[ii] = new IdNamePair(itemId,ss);
          }
          ii++; //only one category for item assumed
          jj++;
        } else if(itemId<itemAssocId) {
          pairs[ii] = new IdNamePair(itemId,"");
          ii++;
        } else if(itemId>itemAssocId) {
          jj++;
        }
      }
      pItemIds = orderPairs(pairs);
    return pItemIds;
  }
  /**
   * Sorts items by manufacturer's name + manufacturer sku number
   * @param pCon the database connection
   * @param pItemsId the collection of items
   * @return collection of item ids sorted by manufacturer short description
   * @throws            RemoteException
   */
  private IdVector sortByMfgName(Connection pCon, IdVector pItemIds, String pItemReq)
    throws RemoteException, SQLException
  {
      DBCriteria dbc = new DBCriteria();
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      if(pItemIds!=null) {
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,pItemIds);
      }
      if(pItemReq!=null) {
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,pItemReq);
      }
      dbc.addOrderBy(ItemMappingDataAccess.BUS_ENTITY_ID);
      ItemMappingDataVector imDV = ItemMappingDataAccess.select(pCon,dbc);
      IdVector mfgIds = new IdVector();
      int idPrev = 0;
      int idNext = 0;
      for(int ii=0; ii<imDV.size(); ii++) {
        ItemMappingData imD = (ItemMappingData) imDV.get(ii);
        idNext = imD.getBusEntityId();
        if(idPrev!=idNext) {
          mfgIds.add(new Integer(idNext));
          idPrev = idNext;
        }
      }

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,mfgIds);
      dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);

      BusEntityDataVector beDV = BusEntityDataAccess.select(pCon,dbc);

      //Create pair vector
      int itemSize = imDV.size();
      IdNamePair[] pairs = new IdNamePair[itemSize];
      int mfgBeId = 0;
      BusEntityData beD = null;
      for(int ii=0,jj=0; ii<itemSize; ii++ ) {
        ItemMappingData imD = (ItemMappingData) imDV.get(ii);
        int itemId = imD.getItemId();
        int mfgId = imD.getBusEntityId();
        if(mfgId!=mfgBeId) {
          beD = (BusEntityData) beDV.get(jj++);
          mfgBeId = beD.getBusEntityId();
        }
        if(mfgId!=mfgBeId) {
          throw new RemoteException("orderByMfgName(). Programm error. Item id: "+itemId+" Manufacturer id: "+mfgId);
        }
        String name = beD.getShortDesc();
        if(name==null || name.trim().length()==0) {
          name = "";
        }
        name+=imD.getItemNum();
        pairs[ii] = new IdNamePair(itemId,name);
      }
      pItemIds = orderPairs(pairs);
      return pItemIds;
  }
  /**
   * Sorts items by manufacturer sku number
   * @param pCon the database connection
   * @param pItemsId the collection of items
   * @return collection of item ids sorted by manufacturer short description
   * @throws            RemoteException
   */
  private IdVector sortByMfgSku(Connection pCon, IdVector pItemIds, String pItemReq)
    throws RemoteException, SQLException
  {
      DBCriteria dbc = new DBCriteria();
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      if(pItemIds!=null) {
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,pItemIds);
      }
      if(pItemReq!=null) {
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,pItemReq);
      }
      ItemMappingDataVector imDV = ItemMappingDataAccess.select(pCon,dbc);
      //Create pair vector
      int itemSize = imDV.size();
      IdNamePair[] pairs = new IdNamePair[itemSize];
      int mfgBeId = 0;
      BusEntityData beD = null;
      for(int ii=0,jj=0; ii<itemSize; ii++ ) {
        ItemMappingData imD = (ItemMappingData) imDV.get(ii);
        int itemId = imD.getItemId();
        String name = imD.getItemNum();
        if(name==null || name.trim().length()==0) {
          name = "";
        }
        pairs[ii] = new IdNamePair(itemId,name);
      }
      pItemIds = orderPairs(pairs);
    return pItemIds;
  }
  /******************************************************************************/
   private IdVector orderPairs(IdNamePair[] pPairs)
   {
     int size = pPairs.length;
     log.info("SVCSVCSVC SIZE="+size);

     for(int ii=0; ii<size-1; ii++) {
       boolean exit = true;
       for(int jj=1; jj<size-ii; jj++) {
         IdNamePair p1 = pPairs[jj-1];
         IdNamePair p2= pPairs[jj];

         if ( p1 == null || p2 == null ) {
        	 exit = true;
         }
         else {
        	 if(p1.getName().compareToIgnoreCase(p2.getName())>0) {

        		 pPairs[jj-1]=p2;
        		 pPairs[jj]=p1;
        		 exit = false;
        	 }
         }
       }
       if(exit) break;
     }
     IdVector itemIdV = new IdVector();
     for(int ii=0; ii<size; ii++) {
    	 if ( pPairs[ii] != null ) {
    		 itemIdV.add(new Integer(pPairs[ii].getId()));
    	 }
     }
     return itemIdV;
   }
  /******************************************************************************/
  /**
   * Picks up user order quides
   * @param pUserId  the user identificator
   * @param pCatalogId  the catalog identificator
   * @param pSiteId  the site identificator
   * @return collection of BusEntityData objects manufacturer type
   * @throws            RemoteException
   */
  public OrderGuideDataVector getUserOrderGuides (int pUserId, int pCatalogId, int pSiteId)
      throws RemoteException
  {
    Connection con = null;
    OrderGuideDataVector orderGuideDV = null;
    try {
      con = getConnection();

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID,pCatalogId);
      if ( pUserId > 0 ) {
    dbc.addEqualTo(OrderGuideDataAccess.USER_ID,pUserId);
      }
      dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID,pSiteId);
      dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
      dbc.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
      orderGuideDV = OrderGuideDataAccess.select(con,dbc);
    }
    catch (NamingException exc) {
      exc.printStackTrace();
      throw new RemoteException("getUserOrderGuides() Naming Exception happened");
    }
    catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException("getUserOrderGuides() SQL Exception happened");
    }
    finally {
      try {
         con.close();
      }
      catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("getUserOrderGuides() SQL Exception happened");
      }
    }
    return orderGuideDV;
  }


    /**
     * Picks up user order quides
     *
     * @param pUserId
     *            the user identificator
     * @param pSiteId
     *            the site identificator
     * @return collection of BusEntityData objects manufacturer type
     * @throws RemoteException
     */
    public OrderGuideDataVector getUserOrderGuides(int pUserId, int pSiteId)
            throws RemoteException {
        Connection con = null;
        OrderGuideDataVector orderGuideDV = null;
        try {
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            if (pUserId > 0) {
                dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
            }
            dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pSiteId);
            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
                    RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
            dbc.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
            orderGuideDV = OrderGuideDataAccess.select(con, dbc);
        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException(
                    "getUserOrderGuides() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException(
                    "getUserOrderGuides() SQL Exception happened");
        } finally {
            try {
                con.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
                throw new RemoteException(
                        "getUserOrderGuides() SQL Exception happened");
            }
        }
        return orderGuideDV;
    }

  /******************************************************************************/
  /**
   * Picks up template order guides
   * @param pCatalogId  the catalog identificator
   * @param pSiteId  the site identificator or 0 if does not apply
   * @return collection of BusEntityData objects manufacturer type
   * @throws            RemoteException
   */
  public OrderGuideDataVector getTemplateOrderGuides(int pCatalogId, int pSiteId) throws RemoteException {
      Connection con = null;
      try {
          con = getConnection();
          return ShoppingDAO.getTemplateOrderGuides(con, pCatalogId, pSiteId);
      } catch (NamingException exc) {
          exc.printStackTrace();
          throw new RemoteException("getTemplateOrderGuides() Naming Exception happened");
      } catch (SQLException exc) {
          exc.printStackTrace();
          throw new RemoteException("getTemplateOrderGuides() SQL Exception happened");
      } finally {
          closeConnection(con);
      }
  }

    public OrderGuideDataVector getCustomOrderGuides(int pAccountId, int pSiteId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return ShoppingDAO.getCustomOrderGuides(con, pAccountId, pSiteId);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(con);
        }
    }

    /******************************************************************************/
    /**
     * Picks up order guide items
     * If product blongs to more than one category, takes the first one (ignores order guide category)
     *
     * @param pStoreTypeCd         the store type. Rules, which sku number is used
     * @param pSiteData            the site data
     * @param pShoppingItemRequest the shopping criteria
     * @param pOrderGuideId        the order guide identificator
     * @param pOrder               the order items to be returned (Constants.ORDER_BY_CATEGORY, Constants.ORDER_BY_NAME, etc)
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException if an errors
     */
    public ShoppingCartItemDataVector getOrderGuidesItems(String pStoreTypeCd,
                                                          SiteData pSiteData,
                                                          int pOrderGuideId,
                                                          ShoppingItemRequest pShoppingItemRequest,
                                                          int pOrder) throws RemoteException {
      return getOrderGuidesItems( pStoreTypeCd, pSiteData, pOrderGuideId,pShoppingItemRequest, pOrder, null);

    }
    public ShoppingCartItemDataVector getOrderGuidesItems(String pStoreTypeCd,
                                                          SiteData pSiteData,
                                                          int pOrderGuideId,
                                                          ShoppingItemRequest pShoppingItemRequest,
                                                          int pOrder,
                                                          AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

        Connection con = null;
        ShoppingCartItemDataVector itemList;
        InventoryLevelDataVector currentInventory = null;

        try {

            con = getConnection();

            String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideId);
            dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemReq);
            dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

            OrderGuideStructureDataVector orderStructureDV = OrderGuideStructureDataAccess.select(con, dbc);
            //---- STJ-6114: Performance Improvements - Optimize Pollock 
            log.info("getOrderGuidesItems() ===>Optimize Pollock BEGIN: orderStructureDV.size() =" + orderStructureDV.size());
            ShoppingDAO.filterByProductBundle(con, orderStructureDV, pShoppingItemRequest);
            log.info("getOrderGuidesItems() ===>Optimize Pollock END: orderStructureDV.size() =" + orderStructureDV.size());
            //----

            IdVector itemIds = new IdVector();
            if (orderStructureDV.size() == 0) {
                return new ShoppingCartItemDataVector();
            }
            for (Object oOrderStructure : orderStructureDV) {
                OrderGuideStructureData ogsD = (OrderGuideStructureData) oOrderStructure;
                itemIds.add(ogsD.getItemId());
            }

            //Prepare Shopping cart items
            if (pSiteData.isUseProductBundle()) {
                itemList = prepareShoppingItemsNew(con,
                        pStoreTypeCd,
                        pShoppingItemRequest.getShoppingCatalogId(),
                        pShoppingItemRequest.getContractId(),
                        itemIds,
                        pSiteData.getSiteId(), pCategToCostCenterView);
            } else {
                itemList = prepareShoppingItems(con,
                        pShoppingItemRequest.getShoppingCatalogId(),
                        pShoppingItemRequest.getContractId(),
                        itemIds,
                        pSiteData.getSiteId(), pCategToCostCenterView);
            }

            //Assign categories and quantities
            for (int ii = 0; ii < itemList.size(); ii++) {
                OrderGuideStructureData ogsD = (OrderGuideStructureData) orderStructureDV.get(ii);
                int qty = ogsD.getQuantity();
                ShoppingCartItemData sciD = (ShoppingCartItemData) itemList.get(ii);
                sciD.setQuantity(qty);
                if (qty > 0) {
                    sciD.setQuantityString(String.valueOf(qty));
                } else {
                    sciD.setQuantityString("");
                }
                CatalogCategoryDataVector ccDV = sciD.getProduct().getCatalogCategories();
                if (ccDV.size() == 0) {
                    sciD.setCategory(null);
                } else {
                    CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
                    sciD.setCategory(ccD);
                }
            }

////////////////////////////
            for (int ii = 0; ii < itemList.size(); ii++) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) itemList.get(ii);
                log.info("getOrderGuidesItems()=> item sku: " + sciD.getActualSkuNum() +
                        " qtyS: <" + sciD.getQuantityString() + ">" +
                        " qty: " + sciD.getQuantity());
            }
////////////////////////////
            if (pSiteData.hasInventoryShoppingOn() == true) {
                // Add any inventory items.
                DBCriteria dbcii = new DBCriteria();
                dbcii.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, pSiteData.getSiteId());
                //dbcii.addOneOf(InventoryLevelDataAccess.ITEM_ID, itemReq);
                dbcii.addIsNotNull(InventoryLevelDataAccess.QTY_ON_HAND);
                currentInventory = InventoryLevelDataAccess.select(con, dbcii);
            }

            itemList = ShoppingDAO.setActualSkus(pStoreTypeCd, itemList);

            log.info("getOrderGuidesItems()=> pOrder: " + pOrder);

            itemList = sortShoppingCardItems(pOrder, itemList);

            // Set the inventory info for each item.
            if (pSiteData.hasModernInventoryShopping()) {
                itemList = setupItemInventoryInfo(con, pSiteData, itemList,pCategToCostCenterView);
            } else {
                itemList = setupItemInventoryInfo(con, pSiteData.getAccountId(), itemList,
                        pSiteData.getSiteInventory(),
                        currentInventory,pCategToCostCenterView);
            }

            itemList = setupMaxOrderQtyValues(pSiteData, itemList);

////////////////////////////
            for (int ii = 0; ii < itemList.size(); ii++) {
                ShoppingCartItemData sciD = (ShoppingCartItemData) itemList.get(ii);
                log.info("getOrderGuidesItems()=> item sku: " + sciD.getActualSkuNum() +
                        " qtyS: <" + sciD.getQuantityString() + ">" +
                        " qty: " + sciD.getQuantity());
            }
////////////////////////////

            return itemList;


        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }

    }
 public ShoppingCartItemDataVector getOrderGuidesItemsAvailable
        (String pStoreTypeCd, SiteData pSiteData, int pCatalogId, int pContractId, boolean pContractOnly, int pOrderGuideId, int pOrder)
         throws RemoteException
     {
       return getOrderGuidesItemsAvailable
        (pStoreTypeCd, pSiteData, pCatalogId, pContractId, pContractOnly, pOrderGuideId, pOrder, null) ;
     }


 public ShoppingCartItemDataVector getOrderGuidesItemsAvailable
     (String pStoreTypeCd, SiteData pSiteData, int pCatalogId, int pContractId, boolean pContractOnly, int pOrderGuideId, int pOrder,
      AccCategoryToCostCenterView pCategToCostCenterView)
      throws RemoteException
  {
      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;
      ShoppingCartItemDataVector itemList = null;

      try {
    con = getConnection();
    stmt = con.createStatement();

    logDebug( " pOrderGuideId=" + pOrderGuideId
        + " pOrder by=" + pOrder );

    // Find the items in the catalog that are not currently
    // in this order guide.  Filter on the contract for items
    // based on the contract flag.
    String sql = "select item_id from clw_catalog_structure cs "
        + " where catalog_id=" + pCatalogId
        + " and cs.item_id not in ( select item_id from "
        + " clw_order_guide_structure ogs "
        + "   where ogs.order_guide_id = " + pOrderGuideId + " )";
    if ( pContractOnly ) {
        sql += " and cs.item_id in ( select item_id "
      + " from clw_contract_item ci where "
      + " ci.contract_id = " + pContractId + ") " ;
    }
    logDebug( "sql=" + sql);


    IdVector itemIds = new IdVector();
    rs = stmt.executeQuery(sql);

    while ( null != rs && rs.next() ) {
        itemIds.add(new Integer(rs.getInt("ITEM_ID")));
    }
    rs.close();
    stmt.close();

          //Prepare Shopping cart items
          if (pSiteData.isUseProductBundle()) {
              itemList = prepareShoppingItemsNew(con,
                      pStoreTypeCd,
                      pCatalogId,
                      pContractId,
                      itemIds,
                      pSiteData.getSiteId(),
                      pCategToCostCenterView);
          } else {
              itemList = prepareShoppingItems(con,
                      pCatalogId,
                      pContractId,
                      itemIds,
                      pSiteData.getSiteId(), pCategToCostCenterView);
          }
 /*   for(int ii=0; ii<itemList.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData) itemList.get(ii);
        CatalogCategoryDataVector ccDV = sciD.getProduct().getCatalogCategories();
        if(ccDV.size()==0) {
      sciD.setCategory(null);
        }
        else {
      CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
      sciD.setCategory(ccD);
        }
    }*/

      }catch (Exception exc) {
    throw processException(exc);
      }
      finally {
    closeConnection(con);
      }

      try{
    itemList = ShoppingDAO.setActualSkus(pStoreTypeCd, itemList);
    itemList = sortShoppingCardItems(pOrder, itemList);
    return itemList;
      }catch (Exception exc) {
    throw processException(exc);
      }
  }

  //********************************************************************
  private ShoppingCartItemDataVector orderByCategoryName(ShoppingCartItemDataVector pShoppingCartDV) {
      ShoppingCartData.orderByCategory(pShoppingCartDV);
      return pShoppingCartDV;
  }

    //********************************************************************
    private ShoppingCartItemDataVector orderByCustomCategory(ShoppingCartItemDataVector pShoppingCartDV) {
        ShoppingCartData.orderByCustomCategory(pShoppingCartDV);
        return pShoppingCartDV;
    }

    //********************************************************************
  private ShoppingCartItemDataVector orderByCategoryAndSku(ShoppingCartItemDataVector pShoppingCartDV) {
    int size = pShoppingCartDV.size();
    if(size<=1) return pShoppingCartDV;
    ShoppingCartItemData[] items = new ShoppingCartItemData[size];
    for(int ii=0; ii<size; ii++) {
      items[ii] = (ShoppingCartItemData) pShoppingCartDV.get(ii);
    }
    for(int ii=0; ii<size-1; ii++) {
      boolean exit = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData item1 = items[jj-1];
        ShoppingCartItemData item2 = items[jj];
        String name1 = "";
        String name2 = "";
        CatalogCategoryData ccD1 = item1.getCategory();
        if(ccD1!=null) {
          name1 = ccD1.getCatalogCategoryShortDesc();
          if(name1==null) name1 = "";
        }
        String ss = item1.getActualSkuNum();
        if(ss!=null) name1+=ss;
        CatalogCategoryData ccD2 = item2.getCategory();
        if(ccD2!=null) {
          name2 = ccD2.getCatalogCategoryShortDesc();
          if(name1==null) name1 = "";
        }
        ss = item2.getActualSkuNum();
        if(ss!=null) name2+=ss;
        if(name1.compareToIgnoreCase(name2)>0) {
          exit = false;
          items[jj-1] = item2;
          items[jj] = item1;
        }
      }
      if(exit) break;
    }
    ShoppingCartItemDataVector retItemDV = new ShoppingCartItemDataVector();
    for(int ii=0; ii<size; ii++) {
      retItemDV.add(items[ii]);
    }
    return retItemDV;
  }

  //********************************************************************
  private ShoppingCartItemDataVector orderByNameSku(ShoppingCartItemDataVector pShoppingCartDV) {
    int size = pShoppingCartDV.size();
    if(size<=1) return pShoppingCartDV;
    ShoppingCartItemData[] items = new ShoppingCartItemData[size];
    for(int ii=0; ii<size; ii++) {
      items[ii] = (ShoppingCartItemData) pShoppingCartDV.get(ii);
    }
    for(int ii=0; ii<size-1; ii++) {
      boolean exit = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData item1 = items[jj-1];
        ShoppingCartItemData item2 = items[jj];
        String name1 = "";
        String name2 = "";
        String ss = item1.getProduct().getCatalogProductShortDesc();
        if(ss!=null) name1+=ss;
        ss = item1.getActualSkuNum();
        if(ss!=null) name1+=ss;
        ss = item2.getProduct().getCatalogProductShortDesc();
        if(ss!=null) name2+=ss;
        ss = item2.getActualSkuNum();
        if(ss!=null) name2+=ss;

        if(name1.compareToIgnoreCase(name2)>0) {
          exit = false;
          items[jj-1] = item2;
          items[jj] = item1;
        }
      }
      if(exit) break;
    }
    ShoppingCartItemDataVector retItemDV = new ShoppingCartItemDataVector();
    for(int ii=0; ii<size; ii++) {
      retItemDV.add(items[ii]);
    }
    return retItemDV;
  }
  //********************************************************************
  private ShoppingCartItemDataVector orderBySkuName(ShoppingCartItemDataVector pShoppingCartDV) {
    int size = pShoppingCartDV.size();
    if(size<=1) return pShoppingCartDV;
    ShoppingCartItemData[] items = new ShoppingCartItemData[size];
    for(int ii=0; ii<size; ii++) {
      items[ii] = (ShoppingCartItemData) pShoppingCartDV.get(ii);
    }
    for(int ii=0; ii<size-1; ii++) {
      boolean exit = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData item1 = items[jj-1];
        ShoppingCartItemData item2 = items[jj];
        String name1 = "";
        String name2 = "";
        String ss = item1.getActualSkuNum();
        if(ss!=null) name1+=ss;
        ss = item1.getProduct().getCatalogProductShortDesc();
        if(ss!=null) name1+=ss;
        ss = item2.getActualSkuNum();
        if(ss!=null) name2+=ss;
        ss = item2.getProduct().getCatalogProductShortDesc();
        if(ss!=null) name2+=ss;

        if(name1.compareToIgnoreCase(name2)>0) {
          exit = false;
          items[jj-1] = item2;
          items[jj] = item1;
        }
      }
      if(exit) break;
    }
    ShoppingCartItemDataVector retItemDV = new ShoppingCartItemDataVector();
    for(int ii=0; ii<size; ii++) {
      retItemDV.add(items[ii]);
    }
    return retItemDV;
  }
  //********************************************************************
  private ShoppingCartItemDataVector orderByMfgName(ShoppingCartItemDataVector pShoppingCartDV) {
    int size = pShoppingCartDV.size();
    if(size<=1) return pShoppingCartDV;
    ShoppingCartItemData[] items = new ShoppingCartItemData[size];
    for(int ii=0; ii<size; ii++) {
      items[ii] = (ShoppingCartItemData) pShoppingCartDV.get(ii);
    }
    for(int ii=0; ii<size-1; ii++) {
      boolean exit = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData item1 = items[jj-1];
        ShoppingCartItemData item2 = items[jj];
        String name1 = "";
        String name2 = "";
        String ss = item1.getProduct().getManufacturerName();
        if(ss!=null) name1+=ss;
        ss = item1.getActualSkuNum();
        if(ss!=null) name1+=ss;
        ss = item2.getProduct().getManufacturerName();
        if(ss!=null) name2+=ss;
        ss = item2.getActualSkuNum();
        if(ss!=null) name2+=ss;

        if(name1.compareToIgnoreCase(name2)>0) {
          exit = false;
          items[jj-1] = item2;
          items[jj] = item1;
        }
      }
      if(exit) break;
    }
    ShoppingCartItemDataVector retItemDV = new ShoppingCartItemDataVector();
    for(int ii=0; ii<size; ii++) {
      retItemDV.add(items[ii]);
    }
    return retItemDV;
  }
  //********************************************************************
  private ShoppingCartItemDataVector orderByMfgSku(ShoppingCartItemDataVector pShoppingCartDV) {
    int size = pShoppingCartDV.size();
    if(size<=1) return pShoppingCartDV;
    ShoppingCartItemData[] items = new ShoppingCartItemData[size];
    for(int ii=0; ii<size; ii++) {
      items[ii] = (ShoppingCartItemData) pShoppingCartDV.get(ii);
    }
    for(int ii=0; ii<size-1; ii++) {
      boolean exit = true;
      for(int jj=1; jj<size-ii; jj++) {
        ShoppingCartItemData item1 = items[jj-1];
        ShoppingCartItemData item2 = items[jj];
        String name1 = "";
        String name2 = "";
        String ss = item1.getProduct().getManufacturerSku();
        if(ss!=null) name1+=ss;
        ss = item2.getProduct().getManufacturerSku();
        if(ss!=null) name2+=ss;
        if(name1.compareToIgnoreCase(name2)>0) {
          exit = false;
          items[jj-1] = item2;
          items[jj] = item1;
        }
      }
      if(exit) break;
    }
    ShoppingCartItemDataVector retItemDV = new ShoppingCartItemDataVector();
    for(int ii=0; ii<size; ii++) {
      retItemDV.add(items[ii]);
    }
    return retItemDV;
  }

  /******************************************************************************/
  /**
   * Picks up order guide items
   * @param pShoppingCart  the ShoppingCartData object
   * @param pCatalogId inentifys catalog, which was used creating the shopping cart
   * @param pUser user login name
   * @throws            RemoteException
   */
 public ShoppingCartData saveShoppingCart
     (ShoppingCartData pShoppingCart, int pCatalogId, String pUser )
      throws RemoteException  {
     return saveShoppingCart
         ( pShoppingCart, pCatalogId, pUser, null, null);
  }
  public ShoppingCartData saveShoppingCart
  (ShoppingCartData pShoppingCart, int pCatalogId, String pUser,
  ProcessOrderResultData pOrderResult,
  List pScartItemPurchased )
       throws RemoteException
   {
     return saveShoppingCart
         ( pShoppingCart, pCatalogId, pUser, null, null, null);
   }
 public ShoppingCartData saveShoppingCart
 (ShoppingCartData pShoppingCart, int pCatalogId, String pUser,
 ProcessOrderResultData pOrderResult,
 List pScartItemPurchased, AccCategoryToCostCenterView pCategToCostCenterView )
      throws RemoteException
  {

     //Do nothing if cart is not persistent
     if(pShoppingCart instanceof ConsolidatedCartView ||
      pShoppingCart.getIsPersistent() == false) {
      return pShoppingCart;
     }
     Connection con = null;
     try {
           con = getConnection();
           if(pShoppingCart.getPrevOrderData()!=null) {
               ShoppingCartData shoppingCartD =
                   adjustShoppingCart(con,pShoppingCart, pCatalogId, pUser,
                          pOrderResult, pScartItemPurchased );
               return shoppingCartD;
           }
           ShoppingCartData shoppingCartD =
               savePersistantShoppingCart(con,pShoppingCart, pCatalogId, pUser,
                      pOrderResult, pScartItemPurchased, pCategToCostCenterView );
           return shoppingCartD;
      }
      catch (Exception exc) {
          exc.printStackTrace();
          throw new RemoteException(exc.getMessage());
      }
      finally {
          if(con!=null) {
             closeConnection(con);
          }
      }

 }

 private ShoppingCartData adjustShoppingCart
   (Connection pCon, ShoppingCartData pShoppingCart, int pCatalogId, String pUser,
    ProcessOrderResultData pOrderResult,
    List pScartItemPurchased )
    throws Exception
  {
      DBCriteria dbc = null;
      UserData user = pShoppingCart.getUser();
      if(user==null){
          throw new RemoteException("saveShoppingCart() ShoppingCartData object doesn't have user information");
      }

      // If the user is configured for browse only,
      // don't save the cart.
      UserRightsTool urt = new UserRightsTool(user);
      if ( urt.isBrowseOnly() ) {
          return pShoppingCart;
      }

      Date currDate = new Date();

      SiteData site = pShoppingCart.getSite();
      if(site==null) {
          throw new Exception("ShoppingCartData object doesn't have site information");
      }
      if (!site.hasInventoryShoppingOn()) {
          return pShoppingCart;
      }
      int siteId = site.getBusEntity().getBusEntityId();

      OrderData srcOrderD = pShoppingCart.getPrevOrderData();
      int srcOrderId = (srcOrderD==null)? 0 :srcOrderD.getOrderId();

      // Difference
      OrderItemDataVector srcOrderItemDV = null;
      if(srcOrderId>0) {
          dbc = new DBCriteria();
          dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,srcOrderId);
          dbc.addOrderBy(OrderItemDataAccess.ITEM_ID);
          srcOrderItemDV = OrderItemDataAccess.select(pCon,dbc);
      }
      if(srcOrderItemDV==null) srcOrderItemDV = new OrderItemDataVector();
      ShoppingCartItemDataVector shoppingCartItemDV = pShoppingCart.getItems();
      List shoppingInfoDV = pShoppingCart.getItemHistory();
      for(Iterator iter=shoppingInfoDV.iterator(); iter.hasNext();) {
          ShoppingInfoData siD = (ShoppingInfoData) iter.next();
          if(!ShoppingCartData.CUSTOMER_CART_COMMENTS.equals(siD.getShortDesc())){
              iter.remove();
          }
      }

      for(Iterator iter = shoppingCartItemDV.iterator(); iter.hasNext();) {
          ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
          int qty = sciD.getQuantity();
          if(qty==0) {
              iter.remove();
          }
      }

      for(Iterator iter = shoppingCartItemDV.iterator(); iter.hasNext();) {
          ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
          int itemId = sciD.getProduct().getProductId();
          int qty = sciD.getQuantity();
          boolean foundFl = false;
          for(Iterator iter1=srcOrderItemDV.iterator(); iter1.hasNext();) {
              OrderItemData oiD = (OrderItemData) iter1.next();
              int srcQty = oiD.getTotalQuantityOrdered();
              if(oiD.getItemId()==itemId) {
                  foundFl = true;
                  if(srcQty != qty) {
                       //generate modified
                       OrderGuideStructureData srcOgsD =
                            OrderGuideStructureData.createValue();
                       srcOgsD.setItemId(itemId);
                       srcOgsD.setQuantity(srcQty);
                       srcOgsD.setOrderGuideId(0);
                       srcOgsD.setOrderGuideStructureId(0);

                       OrderGuideStructureData ogsD =
                                OrderGuideStructureData.createValue();
                       ogsD.setItemId(itemId);
                       ogsD.setQuantity(qty);
                       ogsD.setOrderGuideId(0);
                       ogsD.setOrderGuideStructureId(0);
                       ShoppingInfoData siD =
                          ShoppingDAO.generateAuditEntry(pCon,
                                            site.getBusEntity().getBusEntityId(),
                                            0,
                                            srcOgsD, ogsD, 0, pUser);
                       siD.setAddDate(currDate);
                       siD.setModDate(currDate);
                       shoppingInfoDV.add(siD);
                  }
                  iter1.remove();
              }
          }

          if(!foundFl) {
              //generate add message
              OrderGuideStructureData ogsD =
                    OrderGuideStructureData.createValue();
              ogsD.setItemId(itemId);
              ogsD.setQuantity(qty);
              ogsD.setOrderGuideId(0);
              ogsD.setOrderGuideStructureId(0);
              ShoppingInfoData siD = ShoppingDAO.generateAuditEntry(pCon,
                                    site.getBusEntity().getBusEntityId(),
                                    0,
                                    null, ogsD, 0, pUser);

              siD.setAddDate(currDate);
              siD.setModDate(currDate);
              shoppingInfoDV.add(siD);
          }
      }
      for(Iterator iter=srcOrderItemDV.iterator(); iter.hasNext();) {
          OrderItemData oiD = (OrderItemData) iter.next();
          //generate remove
          OrderGuideStructureData ogsD =
                    OrderGuideStructureData.createValue();
          ogsD.setItemId(oiD.getItemId());
          ogsD.setQuantity(oiD.getTotalQuantityOrdered());
          ogsD.setOrderGuideId(0);
          ogsD.setOrderGuideStructureId(0);
          ShoppingInfoData siD = ShoppingDAO.generateAuditEntry(pCon,
                                site.getBusEntity().getBusEntityId(),0,
                                ogsD, null, 0, pUser);
          siD.setAddDate(currDate);
          siD.setModDate(currDate);
          shoppingInfoDV.add(siD);
      }
      //Get previous order shopping info
      dbc = new DBCriteria();
      dbc.addEqualTo(ShoppingInfoDataAccess.ORDER_ID,srcOrderId);
      dbc.addNotEqualTo(ShoppingInfoDataAccess.SHORT_DESC,
          ShoppingCartData.CUSTOMER_CART_COMMENTS );
      ShoppingInfoDataVector siDV = ShoppingInfoDataAccess.select(pCon,dbc);
      shoppingInfoDV.addAll(siDV);
      pShoppingCart.setShoppingInfo(shoppingInfoDV);
      List prodl = removedItemInfo(pCon, pShoppingCart);
      if(prodl!=null) {
          HashSet prodToShow = new HashSet();
          for(Iterator iter=prodl.iterator(); iter.hasNext();) {
              ShoppingCartData.ItemChangesEntry prod =
                  (ShoppingCartData.ItemChangesEntry) iter.next();
              ShoppingInfoData siD = prod.getShoppingInfoData();
              if(siD.getOrderId()==0) {
                  prodToShow.add(prod.getSku());
              }
          }
          for(Iterator iter=prodl.iterator(); iter.hasNext();) {
              ShoppingCartData.ItemChangesEntry prod =
                  (ShoppingCartData.ItemChangesEntry) iter.next();
              if(!prodToShow.contains(prod.getSku())) {
                  iter.remove();
              }
          }
      }
      if(prodl!=null && prodl.size()>1) {
          ShoppingCartData.ItemChangesEntry[] logA =
                 new ShoppingCartData.ItemChangesEntry[prodl.size()];

          for(int ii=0; ii<logA.length; ii++) {
              logA[ii] = (ShoppingCartData.ItemChangesEntry) prodl.get(ii);
          }

          for(int ii=0; ii<logA.length-1; ii++) {
              boolean exitFl = true;
              for(int jj=0; jj<logA.length-ii-1;jj++) {
                  ShoppingCartData.ItemChangesEntry l1 = logA[jj];
                  ShoppingCartData.ItemChangesEntry l2 = logA[jj+1];
                  int comp = l1.getSku().compareTo(l2.getSku());
                  if(comp>0) {
                      exitFl = false;
                      logA[jj] = l2;
                      logA[jj+1] = l1;
                  } else if(comp==0) {
                      Date addDate1 = l1.getShoppingInfoData().getAddDate();
                      if(addDate1==null) addDate1 = currDate;
                      Date addDate2 = l2.getShoppingInfoData().getAddDate();
                      if(addDate2==null) addDate2 = currDate;
                      comp = addDate1.compareTo(addDate2);
                      if(comp<0) {
                          exitFl = false;
                          logA[jj] = l2;
                          logA[jj+1] = l1;
                      }
                  }
              }
              if(exitFl) {
                  break;
              }
          }
          prodl.clear();
          for(int ii=0; ii<logA.length; ii++) {
              prodl.add(logA[ii]);
          }
      }
      pShoppingCart.setRemovedProductInfo(prodl);
      return pShoppingCart;
  }

 private ShoppingCartData savePersistantShoppingCart
   (Connection pCon, ShoppingCartData pShoppingCart, int pCatalogId, String pUser,
    ProcessOrderResultData pOrderResult,
    List pScartItemPurchased, AccCategoryToCostCenterView pCategToCostCenterView )
    throws Exception
  {
          UserData user = pShoppingCart.getUser();
          if(user==null){
              throw new RemoteException("saveShoppingCart() ShoppingCartData object doesn't have user information");
          }

          // If the user is configured for browse only,
          // don't save the cart.
          UserRightsTool urt = new UserRightsTool(user);
          if ( urt.isBrowseOnly() ) {
              return pShoppingCart;
          }

          SiteData site = pShoppingCart.getSite();
          if(site==null) {
              throw new RemoteException("saveShoppingCart() ShoppingCartData object doesn't have site information");
          }

          OrderGuideData orderGuideD = null;
          if (site.hasInventoryShoppingOn() == false ||(site.hasInventoryShoppingOn()&&site.hasModernInventoryShopping())) {
              orderGuideD = ShoppingDAO.getCartWithUserUniqueName(pCon, user.getUserId(), site.getSiteId(),pUser, pShoppingCart.getUserName2());
          }else{
              orderGuideD = ShoppingDAO.getCartWithUserUniqueName(pCon, 0, site.getSiteId(),pUser, pShoppingCart.getUserName2());
          }

          //OrderGuideStructureDataVector prevOgsdv = ShoppingDAO.clearCart(pCon, site, user.getUserId());

          int ogId = orderGuideD.getOrderGuideId();

          OrderGuideStructureDataVector prevOgsdv =  ShoppingDAO.getCartItems(pCon, ogId);

          //OrderGuideStructureDataVector newOgsdv = new OrderGuideStructureDataVector();

          //Create new order guide object
          /*
          String desc = pShoppingCart.getSite().
              getBusEntity().getShortDesc() + ":" + pUser;
          if(desc.length()>30) desc = desc.substring(0,30);

          DBCriteria dbc = new DBCriteria();
          orderGuideD =  OrderGuideData.createValue();
          orderGuideD.setShortDesc(desc);
          orderGuideD.setCatalogId(pCatalogId);
          orderGuideD.setBusEntityId
              (pShoppingCart.getSite().getSiteId());
          orderGuideD.setUserId
              (pShoppingCart.getUser().getUserId());
          orderGuideD.setOrderGuideTypeCd
              (RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
          orderGuideD.setAddBy(pUser);
          orderGuideD.setModBy(pUser);

          orderGuideD = OrderGuideDataAccess.insert(pCon,orderGuideD);
          */

          HashSet idHS = new HashSet();
          List items = pShoppingCart.getItems();
          for(int ii=0; ii<items.size(); ii++) {
              ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
              boolean inventoryQtyFl =
                  RefCodeNames.ORDER_ITEM_QTY_SOURCE.INVENTORY.equals(sciD.getQtySourceCd());
              int itemId = sciD.getItemId();
              int categoryId = 0;
              CatalogCategoryData ccD = sciD.getCategory();
              if(ccD!=null) categoryId = ccD.getCatalogCategoryId();
              int qty = sciD.getQuantity();
              /*
              int qty = 0;
              if ( sciD.getQuantityString() != null ) {
                  qty = sciD.getQuantity();
              } else {
                  qty = sciD.getInventoryOrderQty();
                  sciD.setQuantity(qty);
                  sciD.setQuantityString(String.valueOf(qty));
              }
              */
              boolean foundFl = false;
              for(Iterator iter=prevOgsdv.iterator(); iter.hasNext();) {
                  OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();
                  if(ogsD.getItemId()==itemId) {
                      foundFl = true;
                      if(qty>0) {
                          idHS.add(new Integer(ogsD.getOrderGuideStructureId()));
                          if(!inventoryQtyFl ||
                              (inventoryQtyFl && qty != sciD.getInventoryOrderQty())) {
                              if(qty != ogsD.getQuantity()) {
                                  OrderGuideStructureData mOgsD =
                                               (OrderGuideStructureData) ogsD.clone();
                                  mOgsD.setOrderGuideStructureId(ogsD.getOrderGuideStructureId());
                                  mOgsD.setQuantity(qty);
                                  mOgsD.setModBy(pUser);
                                  OrderGuideStructureDataAccess.update(pCon,mOgsD);
                              }
                          }
                      }
                      break;
                  }
              }
              if(!foundFl) {
                  boolean invQtyIsSetFl = sciD.getInventoryQtyIsSet();
                  if(qty>0 //&&
                     //(!inventoryQtyFl ||
					 //  !invQtyIsSetFl ||
                     //  (invQtyIsSetFl && qty!=sciD.getInventoryOrderQty() && inventoryQtyFl)
                     //    ||site.hasModernInventoryShopping())
							 ) {
                      OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();
                      ogsD.setOrderGuideId(ogId);
                      ogsD.setItemId(sciD.getItemId());
                      ogsD.setCategoryItemId(categoryId);
                      ogsD.setQuantity(qty);
                      ogsD.setAddBy(pUser);
                      ogsD.setModBy(pUser);
                      ogsD = OrderGuideStructureDataAccess.insert(pCon,ogsD);
                  }
              }
          }

          //Delete removed items from order guide
          for(Iterator iter=prevOgsdv.iterator(); iter.hasNext();) {
              OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();
              int ogsId = ogsD.getOrderGuideStructureId();
              if(!idHS.contains(new Integer(ogsId))) {
                  OrderGuideStructureDataAccess.remove(pCon,ogsId);
              }
          }


          //Update order guide (just for the fact it was modified)
          if(!Utility.isEqual(orderGuideD.getModBy(),pUser)){
        	  orderGuideD.setModBy(pUser);
        	  OrderGuideDataAccess.update(pCon,orderGuideD);
          }

          OrderGuideStructureDataVector newOgsdv =  ShoppingDAO.getCartItems(pCon, ogId);
          if ( site.hasInventoryShoppingOn() == true ) {
              if ( pScartItemPurchased != null ) {
                  for (int idx = 0; idx < pScartItemPurchased.size(); idx++) {
                      logInfo("--- order was placed, pOrderResult=" + pOrderResult);
                      ShoppingCartItemData scid =
                          (ShoppingCartItemData) pScartItemPurchased.get(idx);
                      ShoppingDAO.setOrderHistory
                          (pCon,
                           site.getBusEntity().getBusEntityId(),
                           pOrderResult.getOrderId(),ogId,
                           scid.getItemId());
                  }
              clearRemovedItemsEntries(pCon,site.getBusEntity().getBusEntityId());
              }
              else if ( pOrderResult != null )
              {
                  logInfo("--- order was placed, oldCartId=" + ogId);
                  ShoppingDAO.setOrderHistory
                      (pCon,
                       site.getBusEntity().getBusEntityId(),
                       pOrderResult.getOrderId(), ogId, 0);
              }
              else
              {
                  auditCartChanges(pCon, ogId,
                     orderGuideD, prevOgsdv, newOgsdv, pUser);
              }
          } else{ // Inventory is on check.
              //if this was not an order save any shopping cart props
              if(pScartItemPurchased ==null && pOrderResult==null){
                  moveShoppingCartInfo(pCon, orderGuideD, ogId);
                  if( site.hasBudgets()){
                      ShoppingInfoData sid =
                          ShoppingDAO.getCartHistoryEntry(pCon, ogId, ShoppingCartData.ORDER_BUDGET_TYPE_CD);

                      if(sid == null){
                          sid = pShoppingCart.getHistoryValue(ShoppingCartData.ORDER_BUDGET_TYPE_CD);
                      }else{
                          sid.setValue(pShoppingCart.getOrderBudgetTypeCd());
                      }
                      if(sid != null){
                          //indicates that the property is not in the db, and was not set, so we can leave it unset in db
                          sid.setOrderGuideId(ogId);
                          if(sid.getShoppingInfoId() == 0){
                            ShoppingInfoDataAccess.insert(pCon,sid);
                          }else{
                            ShoppingInfoDataAccess.update(pCon,sid);
                          }
                      }
                  }
              }else if ( pOrderResult != null ) {
                  //remove the shopping info
                  ShoppingInfoData sid =
                      ShoppingDAO.getCartHistoryEntry(pCon, ogId, ShoppingCartData.ORDER_BUDGET_TYPE_CD);
                  if(sid != null){
                      ShoppingInfoDataAccess.remove(pCon,sid.getShoppingInfoId());
                  }
              }
          }
          Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
          siteEjb.updateBudgetSpendingInfo(pShoppingCart.getSite());
          return refreshShoppingCart(pCon, pShoppingCart, pCategToCostCenterView );
  }

  public void createShoppingInfo (ShoppingCartData pShoppingCart,
      String pUser,
      ProcessOrderResultData pOrderResult,
      List pScartItemPurchased )
      throws RemoteException
  {
     //Do nothing if cart is not persistent
     if(pShoppingCart instanceof ConsolidatedCartView ||
      pShoppingCart.getIsPersistent() == false ) {
        return;
     }

     Connection con = null;
     try {
          con = getConnection();
          UserData user = pShoppingCart.getUser();
          if(user==null){
              throw new RemoteException("createShoppingInfo() ShoppingCartData object doesn't have user information");
          }

          SiteData site = pShoppingCart.getSite();
          if(site==null) {
              throw new RemoteException("createShoppingInfo ShoppingCartData object doesn't have site information");
          }

          OrderGuideData oldCart = null;
          OrderGuideStructureDataVector newOgsdv = new OrderGuideStructureDataVector();
          OrderGuideData orderGuideD = null;

          //Create new order guide object

          DBCriteria dbc = new DBCriteria();
          if ( site.hasInventoryShoppingOn() == true ) {
              if ( pScartItemPurchased != null && pOrderResult != null) {
                  for (int idx = 0; idx < pScartItemPurchased.size(); idx++) {
                      ShoppingCartItemData sciD = (ShoppingCartItemData)
                            pScartItemPurchased.get(idx);
                      OrderGuideStructureData ogsD =
                            OrderGuideStructureData.createValue();
                      ogsD.setItemId(sciD.getItemId());
                      ogsD.setQuantity(sciD.getQuantity());
                      ogsD.setOrderGuideId(0);
                      ogsD.setOrderGuideStructureId(0);
                      ShoppingDAO.enterAuditEntry(con,
                                            site.getBusEntity().getBusEntityId(),0,
                                            null, ogsD, pOrderResult.getOrderId(), pUser);
                  }
              }
          }
      }
      catch (Exception exc) {
          exc.printStackTrace();
          throw new RemoteException(exc.getMessage());
      }
      finally {
          closeConnection(con);
      }
  }


  public void saveModifiedOrderShoppingInfo (ShoppingCartData pShoppingCart,
      String pUser,
      ProcessOrderResultData pOrderResult)
      throws RemoteException
  {
     //Do nothing if cart is not persistent
     if(pShoppingCart instanceof ConsolidatedCartView ||
      pShoppingCart.getIsPersistent() == false ) {
        return;
     }
     SiteData site = pShoppingCart.getSite();
     if(site==null) {
         throw new RemoteException("createShoppingInfo ShoppingCartData object doesn't have site information");
     }
     if ( !site.hasInventoryShoppingOn()) {
         return;
     }

     Connection con = null;
     DBCriteria dbc = null;
     try {
          con = getConnection();
          int orderId = pOrderResult.getOrderId();
          int siteId = pOrderResult.getSiteId();

          ShoppingInfoDataVector siDV = pShoppingCart.getCustomerComments();
          if(siDV==null) siDV = new ShoppingInfoDataVector();
          List itemHist = pShoppingCart.getItemHistory();
          if(itemHist != null) {
              for(Iterator iter=itemHist.iterator(); iter.hasNext();) {
                  Object histObj = iter.next();
                  if(histObj instanceof ShoppingInfoData) {
                      ShoppingInfoData siD = (ShoppingInfoData) histObj;
                      if(siD.getOrderId()!=0) {//copy old information only
                         siDV.add(siD);
                      }
                  }
              }
          }

          for(Iterator iter=siDV.iterator(); iter.hasNext();) {
              ShoppingInfoData siD = (ShoppingInfoData) iter.next();
              siD.setOrderId(orderId);
              siD.setOrderGuideId(0);
              siD.setSiteId(siteId);
              Date addDate = siD.getAddDate();
              int oldSiId = siD.getItemId();
              siD.setShoppingInfoStatusCd(RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE);
              siD = ShoppingInfoDataAccess.insert(con,siD);
              if(oldSiId>0 && addDate!=null) {
                  siD.setAddDate(addDate);
                  ShoppingInfoDataAccess.update(con,siD);
              }
          }

          // Difference
          OrderItemDataVector srcOrderItemDV = null;
          OrderData srcOrderD = pShoppingCart.getPrevOrderData();
          if(srcOrderD!=null) {
              int srcOrderId = srcOrderD.getOrderId();
              dbc = new DBCriteria();
              dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,srcOrderId);
              dbc.addOrderBy(OrderItemDataAccess.ITEM_ID);
              srcOrderItemDV = OrderItemDataAccess.select(con,dbc);
          }
          if(srcOrderItemDV==null) srcOrderItemDV = new OrderItemDataVector();

          OrderData newOrder = OrderDataAccess.select(con,orderId);
          int preOrderId = newOrder.getPreOrderId();
          dbc = new DBCriteria();
          dbc.addEqualTo(PreOrderItemDataAccess.PRE_ORDER_ID,preOrderId);
          dbc.addOrderBy(PreOrderItemDataAccess.ITEM_ID);
          PreOrderItemDataVector preOrderItemDV = PreOrderItemDataAccess.select(con,dbc);
          OrderItemData wrkOiD = null;
          Iterator srcOrderIter =  srcOrderItemDV.iterator();
          for(Iterator iter=preOrderItemDV.iterator(); iter.hasNext();) {
              PreOrderItemData poiD = (PreOrderItemData) iter.next();
              int itemId = poiD.getItemId();
              int qty = poiD.getQuantity();
              while(srcOrderIter.hasNext() || wrkOiD!=null) {
                  if(wrkOiD==null) wrkOiD = (OrderItemData) srcOrderIter.next();
                  int wrkQty = wrkOiD.getTotalQuantityOrdered();
                  if(wrkOiD.getItemId()==itemId) {
                      if(wrkQty != qty) {
                          //generate modified
                          OrderGuideStructureData srcOgsD =
                                OrderGuideStructureData.createValue();
                          srcOgsD.setItemId(itemId);
                          srcOgsD.setQuantity(wrkQty);
                          srcOgsD.setOrderGuideId(0);
                          srcOgsD.setOrderGuideStructureId(0);

                         OrderGuideStructureData ogsD =
                                OrderGuideStructureData.createValue();
                          ogsD.setItemId(itemId);
                          ogsD.setQuantity(qty);
                          ogsD.setOrderGuideId(0);
                          ogsD.setOrderGuideStructureId(0);
                          ShoppingDAO.enterAuditEntry(con,
                                            site.getBusEntity().getBusEntityId(),0,
                                            srcOgsD, ogsD, orderId, pUser);
                      }
                      wrkOiD = null;
                      break;
                  }
                  if(wrkOiD.getItemId()<itemId) {
                      //generate removed message
                      OrderGuideStructureData ogsD =
                            OrderGuideStructureData.createValue();
                      ogsD.setItemId(wrkOiD.getItemId());
                      ogsD.setQuantity(wrkOiD.getTotalQuantityOrdered());
                      ogsD.setOrderGuideId(0);
                      ogsD.setOrderGuideStructureId(0);
                      ShoppingDAO.enterAuditEntry(con,
                                        site.getBusEntity().getBusEntityId(),0,
                                        ogsD, null, orderId, pUser);
                      wrkOiD = null;
                      continue;
                  }
                  //generate add message
                  OrderGuideStructureData ogsD =
                        OrderGuideStructureData.createValue();
                  ogsD.setItemId(itemId);
                  ogsD.setQuantity(qty);
                  ogsD.setOrderGuideId(0);
                  ogsD.setOrderGuideStructureId(0);
                  ShoppingDAO.enterAuditEntry(con,
                                        site.getBusEntity().getBusEntityId(),0,
                                        null, ogsD, orderId, pUser);
                  break;
              }
          }
          while(srcOrderIter.hasNext() || wrkOiD!=null) {
              if(wrkOiD==null) wrkOiD = (OrderItemData) srcOrderIter.next();
              //generate remove
              OrderGuideStructureData ogsD =
                    OrderGuideStructureData.createValue();
              ogsD.setItemId(wrkOiD.getItemId());
              ogsD.setQuantity(wrkOiD.getTotalQuantityOrdered());
              ogsD.setOrderGuideId(0);
              ogsD.setOrderGuideStructureId(0);
              ShoppingDAO.enterAuditEntry(con,
                                site.getBusEntity().getBusEntityId(),0,
                                ogsD, null, orderId, pUser);
              wrkOiD = null;
              continue;
          }
      }
      catch (Exception exc) {
          exc.printStackTrace();
          throw new RemoteException(exc.getMessage());
      }
      finally {
          closeConnection(con);
      }
  }

  private void auditCartChanges(Connection con,
                                  int oldCartId,
                                  OrderGuideData orderGuideD,
                                  OrderGuideStructureDataVector prevOgsdv,
                                  OrderGuideStructureDataVector newOgsdv,
                                  String pUser)
        throws Exception
    {
        if ( prevOgsdv == null )
        {prevOgsdv = new OrderGuideStructureDataVector();}

        for (int idx = 0;
             newOgsdv != null
                 && idx < newOgsdv.size();
             idx++ )
        {
            OrderGuideStructureData ogd1 = (OrderGuideStructureData)
                newOgsdv.get(idx);
            boolean isNew = true;
            for (int innerIdx = 0; innerIdx < prevOgsdv.size();  innerIdx++)
            {
                OrderGuideStructureData ogd2 = (OrderGuideStructureData)
                prevOgsdv.get(innerIdx);
                if ( ogd2.getItemId() == ogd1.getItemId() )
                {
                    // Log qty updates.
                    isNew = false;
                    if ( ogd2.getQuantity() !=  ogd1.getQuantity()) {
                        ShoppingDAO.enterAuditEntry(con,
                                                    orderGuideD.getBusEntityId(),
                                                    orderGuideD.getOrderGuideId(),
                                                    ogd2, ogd1, pUser);
                    }
                    break;
                }
            }
            if ( isNew )
            {
                // Log New items.
                ShoppingDAO.enterAuditEntry(con,orderGuideD.getBusEntityId(),
                                            orderGuideD.getOrderGuideId(),
                                            null, ogd1, pUser);

            }
        }

        for (int idx = 0;
             newOgsdv != null
                 && idx < prevOgsdv.size();
             idx++ )
        {
            OrderGuideStructureData ogd1 = (OrderGuideStructureData)
                prevOgsdv.get(idx);
            boolean wasDropped = true;
            for (int innerIdx = 0; innerIdx < newOgsdv.size();  innerIdx++)
            {
                OrderGuideStructureData ogd2 = (OrderGuideStructureData)
                newOgsdv.get(innerIdx);
                if ( ogd2.getItemId() == ogd1.getItemId() )
                {
                    wasDropped = false;
                    break;
                }
            }
            if ( wasDropped )
            {
                // Log items dropped.
                ShoppingDAO.enterAuditEntry(con,
                                            orderGuideD.getBusEntityId(),
                                            orderGuideD.getOrderGuideId(),
                                            ogd1, null, pUser);
            }
        }
        moveShoppingCartInfo(con, orderGuideD,oldCartId);
    }

    // Move any saved properties to the new cart
    private void moveShoppingCartInfo(Connection con, OrderGuideData orderGuideD, int oldCartId)
    throws SQLException{
        if ( orderGuideD.getOrderGuideId() != oldCartId )
        {
            // Move any saved properties to the new cart.
            String updCartSql =
                " update clw_shopping_info set order_guide_id = "
                + orderGuideD.getOrderGuideId()
                + " where order_guide_id = " + oldCartId;
            Statement stmt1 = con.createStatement();
            logInfo("ShoppingServicesBean.moveShoppingCartInfo SQL:" + updCartSql);
            stmt1.executeUpdate(updCartSql);
            stmt1.close();
        }
    }

    public void clearRemovedItemsEntries(Connection con,
           int pSiteId)
  throws SQLException {

  String sql = "delete from clw_shopping_info si where order_id > 0 "
      + " and site_id = " + pSiteId
      + " and (select count(*) from clw_order_item oi where oi.order_id = si.order_id and oi.item_id = si.item_id) = 0 ";
  Statement stmt1 = con.createStatement();
  logInfo("ShoppingServicesBean. Delete removed item history SQL:" + sql);
  stmt1.executeUpdate(sql);
  stmt1.close();
    }

    public void clearRemovedItemsEntries(Connection con, int pSiteId, int pOrderGuideId) throws SQLException {

        String sql = "delete from clw_shopping_info si where order_id > 0 "
                + " and site_id = " + pSiteId + " and order_guide_id = " + pOrderGuideId + " "
                + " and (select count(*) from clw_order_item oi where oi.order_id = si.order_id and oi.item_id = si.item_id) = 0 ";
        Statement stmt1 = con.createStatement();
        logInfo("ShoppingServicesBean. Delete removed item history SQL:" + sql);
        stmt1.executeUpdate(sql);
        stmt1.close();
    }

    private ShoppingCartData refreshShoppingCart(Connection pCon, ShoppingCartData pCart, AccCategoryToCostCenterView pCategToCostCenterView )
          throws RemoteException
    {
        ShoppingCartData ncart = getShoppingCart
            (pCon,
            pCart.getStoreType(),
             pCart.getUser(),
             pCart.getUserName2(),
             pCart.getSite(),
             pCart.getCatalogId(),
             pCart.getContractId(),
             pCategToCostCenterView
             );

        ncart.setOrderBy(pCart.getOrderBy());
        ncart.setNewItems(pCart.getNewItems());
        ncart.setPrevOrderData(pCart.getPrevOrderData());

        return ncart;

    }

  /******************************************************************************/
  /**
   * Picks up order guide items
   * @param pStoreType (store type defined in RefCodeNames interface)
   * @param pUser the user object
   * @param pSite the site object
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @throws            RemoteException
   */
  public ShoppingCartData getShoppingCart(String pStoreType,
                                          UserData pUser,
                                          SiteData pSite,
                                          int pCatalogId,
                                          int pContractId
      )
       throws RemoteException
   {
     return getShoppingCart( pStoreType,pUser,pSite, pCatalogId, pContractId , null);
   }
  
  public ShoppingCartData getShoppingCart(String pStoreType,
          UserData pUser,
          SiteData pSite,
          int pCatalogId,
          int pContractId,
AccCategoryToCostCenterView pCategToCostCenterView )
throws RemoteException
{
	  return getShoppingCart(pStoreType,  pUser, null, pSite, pCatalogId, pContractId, pCategToCostCenterView ); 
}
 public ShoppingCartData getShoppingCart(String pStoreType,
                                         UserData pUser,
                                         String userName2,                                         
                                         SiteData pSite,
                                         int pCatalogId,
                                         int pContractId,
    AccCategoryToCostCenterView pCategToCostCenterView )
      throws RemoteException
  {
    Connection con = null;
    try {
      con = getConnection();
      return getShoppingCart(con,  pStoreType,  pUser, userName2,
      pSite, pCatalogId,  pContractId, pCategToCostCenterView );
          }
    catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException("getShoppingCart() Exception. "+exc.getMessage());
    }
    finally {
        closeConnection(con);
    }


  }
  private ShoppingCartData getShoppingCart(Connection con,
             String pStoreType,
                                          UserData pUser,
                                          SiteData pSite,
                                          int pCatalogId,
                                          int pContractId )
       throws RemoteException
   {

     return getShoppingCart(con, pStoreType, pUser, pSite, pCatalogId, pContractId, null );

   }

  private ShoppingCartData getShoppingCart(Connection con,
          String pStoreType,
                                       UserData pUser,
                                       SiteData pSite,
                                       int pCatalogId,
                                       int pContractId,
 AccCategoryToCostCenterView pCategToCostCenterView )
    throws RemoteException
{
	  return getShoppingCart(con, pStoreType, pUser, null,  pSite, pCatalogId, pContractId, pCategToCostCenterView );
}
 private ShoppingCartData getShoppingCart(Connection con,
            String pStoreType,
                                         UserData pUser,
                                         String userName2,      
                                         SiteData pSite,
                                         int pCatalogId,
                                         int pContractId,
   AccCategoryToCostCenterView pCategToCostCenterView )
      throws RemoteException
  {

      try {

        ShoppingCartData shoppingCartD = new ShoppingCartData();
        shoppingCartD.setUser(pUser);
        shoppingCartD.setSite(pSite);
        shoppingCartD.setUserName2(userName2);

        if ( pContractId <= 0 ) {
            if ( pSite.getContractData() == null ) {
                logError ("CONTRACT MISSING pSite=" + pSite);
            } else {
                pContractId = pSite.getContractData().getContractId();
            }
        }

        shoppingCartD.setContractId(pContractId);
        shoppingCartD.setStoreType(pStoreType);
        if ( pCatalogId > 0 ) {
            shoppingCartD.setCatalogId(pCatalogId);
        } else {
            shoppingCartD.setCatalogId(pSite.getSiteCatalogId());
            pCatalogId = pSite.getSiteCatalogId();
        }

        int siteId = pSite.getBusEntity().getBusEntityId();
        String userName = pUser.getUserName();


        ShoppingCartItemDataVector items = new ShoppingCartItemDataVector();
        OrderGuideStructureDataVector ogsdv = null;


        int effectiveUserId = (pSite.hasInventoryShoppingOn()&&!pSite.hasModernInventoryShopping())?0:pUser.getUserId();
        OrderGuideData orderGuideD =
              ShoppingDAO.getCartWithUserUniqueName(con, effectiveUserId,
                      pSite.getBusEntity().getBusEntityId(), pUser.getUserName(), userName2);

        int id = orderGuideD.getOrderGuideId();

        id = orderGuideD.getOrderGuideId();
        shoppingCartD.setOrderGuideId(id);
        shoppingCartD.setModBy(orderGuideD.getModBy());
        shoppingCartD.setModDate(orderGuideD.getModDate());

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,id);

       ShoppingItemRequest shoppingItemRequest = null;
       String shoppingItemIds = null;

          if (pSite.isUseProductBundle()) {

              shoppingItemRequest = ShoppingDAO.createShoppingItemRequest(con,
                      pSite.getSiteId(),
                      pSite.getAccountId(),
                      pCatalogId,
                      pContractId,
                      pSite.getPriceListRank1Id(),
                      pSite.getPriceListRank2Id(),
                      pSite.getProprietaryPriceListIds(),
                      pSite.getAvailableTemplateOrderGuideIds(),
                      pSite.getProductBundle(),
                      pUser);
              shoppingItemIds = ShoppingDAO.getShoppingItemIdsRequest(con, shoppingItemRequest);
              dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, shoppingItemIds);
              ogsdv = OrderGuideStructureDataAccess.select(con, dbc);
 
          } else {

              DBCriteria dbc1 = new DBCriteria();
              dbc1.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
              dbc1.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
              String catalogItemsReq =  CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, dbc1);
              dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, catalogItemsReq);

              if (pUser.getUserRoleCd().indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY) >= 0) {
                  dbc1 = new DBCriteria();
                  dbc1.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
                  String contractItemsReq = ContractItemDataAccess.getSqlSelectIdOnly(ContractItemDataAccess.ITEM_ID, dbc1);
                  dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, contractItemsReq);
              }
              ogsdv = OrderGuideStructureDataAccess.select(con, dbc);
          }

          IdVector itemIds = new IdVector();
        for(Iterator iter=ogsdv.iterator(); iter.hasNext();) {
          OrderGuideStructureData ogsD = (OrderGuideStructureData)iter.next();
          /*
          if(ogsD.getQuantity()==0) {
              OrderGuideStructureDataAccess.remove(con,ogsD.getOrderGuideStructureId());
              if ( pSite.hasInventoryShoppingOn()) {
                  ShoppingDAO.enterAuditEntry(con,siteId,ogsD,null,0,"adjustment");
              }
              iter.remove();
              continue;
          }
          */
          itemIds.add(new Integer(ogsD.getItemId()));
        }

        InventoryLevelDataVector currentInventory = null;
        if (pSite.hasInventoryShoppingOn() == true &&
        !pSite.hasModernInventoryShopping() ) {

            // Add any inventory items.
            // if !pSite.isUseProductBundle()
            if (shoppingItemRequest == null || shoppingItemIds == null) {
                shoppingItemRequest = ShoppingDAO.createShoppingItemRequest(con,
                        pSite.getSiteId(),
                        pSite.getAccountId(),
                        pCatalogId,
                        pContractId,
                        pSite.getPriceListRank1Id(),
                        pSite.getPriceListRank2Id(),
                        pSite.getProprietaryPriceListIds(),
                        pSite.getAvailableTemplateOrderGuideIds(),
                        pSite.getProductBundle(),
                        pUser);
                shoppingItemIds = ShoppingDAO.getShoppingItemIdsRequest(con, shoppingItemRequest);
            }

            DBCriteria dbc1 = new DBCriteria();
            dbc1.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, siteId);
            dbc1.addIsNotNull(InventoryLevelDataAccess.QTY_ON_HAND);
            dbc1.addOneOf(InventoryLevelDataAccess.ITEM_ID, shoppingItemIds);

            currentInventory = InventoryLevelDataAccess.select(con,dbc1);
            for ( int c2 = 0; c2 < currentInventory.size(); c2++) {
                InventoryLevelData ild = (InventoryLevelData)currentInventory.get(c2);
                itemIds.add(new Integer(ild.getItemId()));
            }
            
         }

          if (pSite.isUseProductBundle()) {
			//---- STJ-6114: Performance Improvements - Optimize Pollock 
			log.info("getOrderGuidesItems() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
			ShoppingDAO.filterByProductBundle(con, itemIds, shoppingItemRequest);
			log.info("getOrderGuidesItems() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
			//----
              items = prepareShoppingItemsNew(con,
                      pStoreType,
                      pCatalogId,
                      pContractId,
                      itemIds,
                      pSite.getSiteId(),
                      pCategToCostCenterView);
          } else {
              items = prepareShoppingItems(con, pCatalogId, pContractId, itemIds, pSite.getSiteId(), pCategToCostCenterView);
          }

        items = ShoppingDAO.setActualSkus(pStoreType, (ShoppingCartItemDataVector) items);

        for(int ii=0; ii<ogsdv.size(); ii++) {
          OrderGuideStructureData ogsD = (OrderGuideStructureData) ogsdv.get(ii);
          for ( int iii=0; iii < items.size(); iii++ ) {
              ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(iii);
              if ( ogsD.getItemId() == sciD.getItemId() ) {
                  sciD.setQuantity(ogsD.getQuantity());
                  sciD.setQuantityString(String.valueOf(ogsD.getQuantity()));
                  sciD.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.SHOPPING_CART);
                  break;
              }
          }
      }

          // Set the inventory info for each item.
      ShoppingCartItemDataVector finalitems0 = items;
      if (pSite.hasInventoryShoppingOn() == true ) {
          if (pSite.hasModernInventoryShopping()) {
              finalitems0 = setupItemInventoryInfo(con, pSite, items, pCategToCostCenterView );
          } else {
              finalitems0 = setupItemInventoryInfo(con,pSite.getAccountId(),items,
                                                   pSite.getSiteInventory(),
                                                   currentInventory, pCategToCostCenterView);
          }
      }
      finalitems0 = setupMaxOrderQtyValues(pSite, finalitems0);

      // Now go though the final items and pick out
      // those not being ordered.
      ShoppingCartItemDataVector finalitems1 =
          new ShoppingCartItemDataVector();
      for (int fidx = 0; fidx < finalitems0.size(); fidx++ )
      {
          ShoppingCartItemData scid =
              (ShoppingCartItemData)finalitems0.get(fidx);
          //
          //if ( scid.getQuantity() > 0 )
          //{
          if(!RefCodeNames.ORDER_ITEM_QTY_SOURCE.INVENTORY.equals(scid.getQtySourceCd())||
              (scid.getInventoryParValue() > 0&&!pSite.hasModernInventoryShopping())) {
              finalitems1.add(scid);
          }
      }
      shoppingCartD.setItems(finalitems1);
      //


      //Adjust shopping cart order guide
      /*
      if ( pSite.hasInventoryShoppingOn()) {
          if(id==0) {//Create new OG
              OrderGuideData ogD = OrderGuideData.createValue();
              ogD.setUserId(0);
              ogD.setBusEntityId(pSite.getBusEntity().getBusEntityId());
              String siteName = pSite.getBusEntity().getShortDesc();
              if(siteName.length()>30) siteName = siteName.substring(0,30);
              ogD.setShortDesc(siteName);
              ogD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
              ogD.setCatalogId(pCatalogId);
              ogD.setModBy("adjustment");
              ogD.setAddBy("adjustment");
              ogD = OrderGuideDataAccess.insert(con,ogD);
              id = ogD.getOrderGuideId();
          }
          if(ogsdv==null) ogsdv = new OrderGuideStructureDataVector();
          for(Iterator iter=finalitems1.iterator(); iter.hasNext();) {
              ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
              int qty = sciD.getQuantity();
              int itemId = sciD.getItemId();
              boolean foundFl = false;
              for(Iterator iter1 = ogsdv.iterator(); iter1.hasNext(); ) {
                  OrderGuideStructureData ogsD = (OrderGuideStructureData) iter1.next();
                  if(itemId==ogsD.getItemId()) {
                      foundFl = true;
                      if(qty!=ogsD.getQuantity()) {
                          OrderGuideStructureData oldOgsD =
                                    (OrderGuideStructureData) ogsD.clone();
                          ogsD.setQuantity(qty);
                          ogsD.setModBy("adjustment");
                          OrderGuideStructureDataAccess.update(con,ogsD);
                          ShoppingDAO.enterAuditEntry(con,siteId,oldOgsD,ogsD,0,"adjustment");
                      }
                      iter1.remove();
                      break;
                  }
              }
              if(!foundFl) { //insert into shoppingCart
                  OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();
                  ogsD.setOrderGuideId(id);
                  ogsD.setItemId(itemId);
                  ogsD.setQuantity(qty);
                  ogsD.setAddBy("adjustment");
                  ogsD.setModBy("adjustment");
                  ogsD.setCategoryItemId(sciD.getCategory().getCatalogCategoryId());
                  OrderGuideStructureDataAccess.insert(con,ogsD);
                  ShoppingDAO.enterAuditEntry(con,siteId,null,ogsD,0,"adjustment");
              }
          }
          //Delete not included to shopping cart
          for(Iterator iter=ogsdv.iterator();iter.hasNext();) {
              OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();
              OrderGuideStructureDataAccess.remove(con,ogsD.getOrderGuideStructureId());
              ShoppingDAO.enterAuditEntry(con,siteId,ogsD,null,0,"adjustment");
          }
      }
*/
      // pull up any saved po number or comments.
      // and shopping history.
      if ( id > 0 )
      {
          shoppingCartD.setShoppingInfo
              (updateShoppingInfo(con,siteId,id,null));

          // get product info for items removed in the past.
          List prodl = removedItemInfo(con, shoppingCartD);
          shoppingCartD.setRemovedProductInfo(prodl);
      }

    return shoppingCartD;
    }
    catch (Exception e)
    {
        e.printStackTrace();
        throw new RemoteException(e.getMessage());
    }
  }


    private List removedItemInfo(Connection con,
                                 ShoppingCartData shoppingCartD)
        throws Exception
    {
        ArrayList rmItems = new ArrayList();
        java.util.List itemsRemoved = shoppingCartD.getItemsRemovedHistory();
        IdVector itemIds = new IdVector();
        for ( int idx = 0; itemsRemoved != null && idx <
                  itemsRemoved.size(); idx++ )
        {
            ShoppingInfoData sid = (ShoppingInfoData)itemsRemoved.get(idx);
            ShoppingCartData.ItemChangesEntry n =
                shoppingCartD.mkItemChangesEntry(sid);
            n.setSku("-");
            n.setProductDesc("-");
            rmItems.add(n);
            itemIds.add(new Integer(sid.getItemId()));
        }

        int catalogId = shoppingCartD.getCatalogId();

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIds);
        ItemDataVector idv =
            ItemDataAccess.select(con, dbc);
        for ( int idx = 0; idv != null &&
                  idx < idv.size(); idx++)
        {
            ItemData itemd = (ItemData)idv.get(idx);
            for ( int innerIdx = 0; innerIdx < rmItems.size(); innerIdx++ )
            {
                ShoppingCartData.ItemChangesEntry ichanges =
                    (ShoppingCartData.ItemChangesEntry)rmItems.get(innerIdx);
                if ( itemd.getItemId() ==
                     ichanges.getShoppingInfoData().getItemId() )
                {
                    ichanges.setSku(String.valueOf(itemd.getSkuNum()));
                    ichanges.setProductDesc(itemd.getShortDesc());
                }
            }
        }


        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,
                       catalogId);
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIds);
        CatalogStructureDataVector csdv =
            CatalogStructureDataAccess.select(con, dbc);
        for ( int idx = 0; csdv != null &&
                  idx < csdv.size(); idx++)
        {
            CatalogStructureData csd = (CatalogStructureData)csdv.get(idx);
            for ( int innerIdx = 0; innerIdx < rmItems.size(); innerIdx++ )
            {
                ShoppingCartData.ItemChangesEntry ichanges =
                    (ShoppingCartData.ItemChangesEntry)rmItems.get(innerIdx);
                if ( csd.getItemId() == ichanges.getShoppingInfoData().getItemId() )
                {
                    if ( csd.getCustomerSkuNum() != null &&
                         csd.getCustomerSkuNum().length() > 0 )
                    {
                        ichanges.setSku(csd.getCustomerSkuNum());
                    }
                     if ( csd.getShortDesc() != null &&
                         csd.getShortDesc().length() > 0 )
                    {
                        ichanges.setProductDesc(csd.getShortDesc());
                    }

                }
            }
        }

        return rmItems;

    }

    private ShoppingCartItemDataVector setupItemInventoryInfo
        (Connection con, int pAccountId, ShoppingCartItemDataVector pCartItems,
         List pInventoryInfo,InventoryLevelDataVector pCurrentInventory,
         AccCategoryToCostCenterView pCategToCostCenterView)
    throws Exception
    {

        if ( null == pCartItems  || 0 == pCartItems.size() )
        {
            return pCartItems;
        }
        if ( null == pInventoryInfo || 0 == pInventoryInfo.size())
        {
            return pCartItems;
        }


        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, pAccountId);
        dbc.addEqualToIgnoreCase(InventoryItemsDataAccess.ENABLE_AUTO_ORDER,"Y");
        InventoryItemsDataVector invItemsDV = InventoryItemsDataAccess.select(con,dbc);
        BigDecimal autoOrderFactor = new BigDecimal(0.5);
        if(invItemsDV.size()>0) {
            dbc = new DBCriteria();
            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,pAccountId);
            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,
                RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR);
            PropertyDataVector propDV = PropertyDataAccess.select(con,dbc);
            if(propDV.size()>0) {
                PropertyData propD = (PropertyData) propDV.get(0);
                String vvS = propD.getValue();
                if(Utility.isSet(vvS))
                {
                    try {
                        double vv = Double.parseDouble(vvS);
                        if(vv<=0) vv = 0;
                        if(vv>1) vv = 1;
                        autoOrderFactor =  new BigDecimal(vv).setScale(2,BigDecimal.ROUND_HALF_DOWN);
                    } catch (Exception ex) {
                        //Print stack and use default value;
                        logInfo("setupItemInventoryInfo => invalid auto order factor: "+vvS);
                    }
                }
            }
        }


        for ( int scidx = 0; scidx < pCartItems.size(); scidx++ ) {
            ShoppingCartItemData scid =
                (ShoppingCartItemData)pCartItems.get(scidx);
            int parValue = 0;
            int invQty = 0;
            if(pInventoryInfo != null) {
                for ( int i = 0; i < pInventoryInfo.size(); i++ ) {
                    SiteInventoryInfoView siiv = (SiteInventoryInfoView)pInventoryInfo.get(i);
                    if ( scid.isSameItem(siiv.getItemId())) {
                        parValue = siiv.getParValue();
                        scid.setInventoryParValue(parValue);
                        scid.setInventoryParValuesSum(siiv.getSumOfAllParValues());
                        //scid.setMaxOrderQty(13);
                        String t = siiv.getQtyOnHand();
                        if ( t == null || t.length() == 0 ) t = "";
                        t = t.trim();
                        scid.setIsaInventoryItem(true);

                        if ( t.length() == 0 ) {
                            scid.setInventoryQtyOnHand(0);
                            scid.setInventoryQtyIsSet(false);
                        } else {
                            scid.setInventoryQtyOnHand(Integer.parseInt(t));
                            scid.setInventoryQtyIsSet(true);
                            invQty = parValue - scid.getInventoryQtyOnHand();
                        }
                        scid.setInventoryQtyOnHandString(t);
                        break;
                    }
                }
            }
            if (pCurrentInventory != null) {
                 for ( int i = 0; i < pCurrentInventory.size(); i++ ) {
                    InventoryLevelData ild =
                        (InventoryLevelData)pCurrentInventory.get(i);

                    if ( scid.isSameItem(ild.getItemId())) {

                        String t = ild.getQtyOnHand();
                        if ( t == null || t.length() == 0 ) t = "";
                        t = t.trim();
                        scid.setIsaInventoryItem(true);

                        if ( t.length() == 0 ) {
                            scid.setInventoryQtyOnHand(0);
                            scid.setInventoryQtyIsSet(false);
                        } else {
                            scid.setInventoryQtyOnHand(Integer.parseInt(t));
                            scid.setInventoryQtyIsSet(true);
                            invQty = parValue - scid.getInventoryQtyOnHand();
                        }
                        scid.setInventoryQtyOnHandString(t);
                        break;
                    }
                }
            }

            if(scid.getIsaInventoryItem()) {
                int autoQty = 0;
                for(Iterator iter=invItemsDV.iterator(); iter.hasNext();) {
                    InventoryItemsData iiD = (InventoryItemsData) iter.next();
                    if(iiD.getItemId()==scid.getItemId()) {
                        scid.setAutoOrderFactor(autoOrderFactor);
                        scid.setAutoOrderEnable(true);
                    }
                }

                int finalQty = scid.getQuantity();
                if (finalQty <= 0) {
                    scid.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.INVENTORY);
                    finalQty = scid.getInventoryOrderQty();
                    scid.setQuantity(finalQty);
                    
                   // scid.setQuantityString(String.valueOf(finalQty));
                    if(finalQty>0) {
                    	scid.setQuantityString(String.valueOf(finalQty));
                    } else {
                    	scid.setQuantityString("");
                    }

                }  else {
                    scid.setQuantity(finalQty);
                    scid.setQuantityString(String.valueOf(finalQty));
                    scid.setInventoryQtyIsSet(true);
                }
            }
        }

        return pCartItems;
    }


    private ShoppingCartItemDataVector setupItemInventoryInfo(Connection con,
                                                              SiteData pSite,
                                                              ShoppingCartItemDataVector pCartItems,
                                                              AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

        if (null == pCartItems || 0 == pCartItems.size()) {
            return pCartItems;
        }

        if (pSite == null || pSite.getSiteId() <= 0
                || pSite.getAccountId() <= 0
                || !pSite.hasInventoryShoppingOn()) {
            return pCartItems;
        }

        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();

        BigDecimal autoOrderFactor = getAutoOrderFactor(con, pSite.getAccountId());

        ProductDataVector productDV = new ProductDataVector();
        for (int scidx = 0; scidx < pCartItems.size(); scidx++) {
            ShoppingCartItemData scid  = (ShoppingCartItemData) pCartItems.get(scidx);
            productDV.add(scid.getProduct());
        }
        
        List inventoryItems = siteEjb.lookupSiteInventory(pSite.getSiteId(),productDV,pCategToCostCenterView);
        HashMap invInfoItemsMap;
        SiteInventoryInfoViewVector invInfoVV = new SiteInventoryInfoViewVector();
        invInfoVV.addAll(inventoryItems);
        invInfoItemsMap = Utility.toMapByItemId(invInfoVV);

        for (int scidx = 0; scidx < pCartItems.size(); scidx++) {

            ShoppingCartItemData scid  = (ShoppingCartItemData) pCartItems.get(scidx);
            SiteInventoryInfoView siiv = (SiteInventoryInfoView) invInfoItemsMap.get(new Integer(scid.getItemId()));

            if (siiv != null) {

                scid.setInventoryParValue(siiv.getParValue());
                scid.setInventoryParValuesSum(siiv.getSumOfAllParValues());
                scid.setIsaInventoryItem(true);
                scid.setMonthlyOrderQtyString(siiv.getOrderQty());

                String qtyOnHandStr = Utility.strNN(siiv.getQtyOnHand());
                String orderQtyStr = Utility.strNN(siiv.getOrderQty());
                qtyOnHandStr = qtyOnHandStr.trim();
                orderQtyStr   = orderQtyStr.trim();

                logInfo("setupItemInventoryInfo => orderQtyStr: " + orderQtyStr + " qtyOnHandStr " + qtyOnHandStr);

                int qtyOnHandInt = 0;
                int orderQtyInt = 0;
                boolean orderQtyIsSet = false;
                boolean onHandQtyIsSet = false;

                try {
                    qtyOnHandInt = Integer.parseInt(qtyOnHandStr);
                    onHandQtyIsSet = true;
                } catch (NumberFormatException exc) {
                    onHandQtyIsSet = false;
                }

                try {
                    orderQtyInt = Integer.parseInt(orderQtyStr);
                    orderQtyIsSet = true;
                } catch (NumberFormatException exc) {
                    orderQtyIsSet = false;
                }

                if (Utility.isTrue(siiv.getAutoOrderItem())) {
                    scid.setAutoOrderFactor(autoOrderFactor);
                    scid.setAutoOrderEnable(true);
                }


                if (pSite.hasModernInventoryShopping()) {
                    logInfo("setupItemInventoryInfo => modern inventory shopping");
                    int finalQty = scid.getQuantity();

                    if (onHandQtyIsSet) {
                        scid.setInventoryQtyOnHand(qtyOnHandInt);
                        scid.setInventoryQtyOnHandString(String.valueOf(qtyOnHandInt));
                    }

                    if (finalQty <= 0) {

                        scid.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.INVENTORY);

                        if (onHandQtyIsSet) {
                            scid.setInventoryQtyIsSet(true);
                        } else {
                            scid.setInventoryQtyIsSet(false);
                        }
                    } else {
                        scid.setQuantity(finalQty);
                        scid.setQuantityString(String.valueOf(finalQty));
                        scid.setInventoryQtyIsSet(true);
                    }
                } else { // classic inventory shopping shopping
                    logInfo("setupItemInventoryInfo => classic inventory shopping shopping");
                    int finalQty = scid.getQuantity();
                    if (finalQty <= 0) {

                        scid.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.INVENTORY);

                        if (onHandQtyIsSet) {
                            scid.setInventoryQtyOnHand(qtyOnHandInt);
                            scid.setInventoryQtyOnHandString(String.valueOf(qtyOnHandInt));
                            scid.setInventoryQtyIsSet(true);
                            scid.setQuantity(scid.getInventoryOrderQty());
                            scid.setQuantityString(String.valueOf(scid.getInventoryOrderQty()));
                        } else {
                            scid.setInventoryQtyIsSet(false);
                        }
                    } else {
                        scid.setQuantity(finalQty);
                        scid.setQuantityString(String.valueOf(finalQty));
                        scid.setInventoryQtyIsSet(true);
                    }
                }
            }
        }
        return pCartItems;
    }


    public ShoppingCartItemDataVector setupMaxOrderQtyValues
      (SiteData pSite, ShoppingCartItemDataVector pCartItems) throws RemoteException {
      if (null == pCartItems || 0 == pCartItems.size()) {
        return pCartItems;
      }
      java.util.Hashtable ctrlv = pSite.getShoppingControlsMap();
      if (null == ctrlv || ctrlv.size() <= 0) {

        return pCartItems;
      }

      for ( int scidx = 0; scidx < pCartItems.size(); scidx++ ) {
        ShoppingCartItemData scid = (ShoppingCartItemData)pCartItems.get(scidx);

        Integer k = new Integer(scid.getItemId());
        ShoppingControlData ctrl = (ShoppingControlData) ctrlv.get(k);

        if (ctrl != null && scid.isSameItem(ctrl.getItemId())) {
          int maxQtyAllowed = ctrl.getMaxOrderQty();
          scid.setMaxOrderQty(maxQtyAllowed);

          int rDays = ctrl.getRestrictionDays();
          scid.setRestrictionDays(rDays);

          if(!(rDays < 0)){

        	  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

          Date expDate = ctrl.getExpDate();
          Date current = new Date(System.currentTimeMillis());
          String ctrlDate = null;
          if(expDate != null){
        	  ctrlDate = sdf.format(expDate);
          }
          String thisDate = sdf.format(current);

          int histQty = 0;
          Connection con = null;

          // check if the ctrls are valid for today
          if(ctrlDate!=null && ctrlDate.equals(thisDate) ){
        	  //scid.setMaxOrderQty(ctrl.getActualMaxQty());
          }
          else{
          try {
            con = getConnection();

            List status = ReportingUtils.getValidOrderStatusCodes();
            //status.add(RefCodeNames.ORDER_STATUS_CD.RECEIVED);

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderDataAccess.ACCOUNT_ID, pSite.getAccountId());
            dbc.addEqualTo(OrderDataAccess.SITE_ID, pSite.getSiteId());
      	  	dbc.addCondition(OrderDataAccess.ORIGINAL_ORDER_DATE+" > trunc(sysdate)-"+(rDays+1));
      	  	dbc.addOneOf(OrderDataAccess.ORDER_STATUS_CD,status);

      	  	String condn= OrderDataAccess.getSqlSelectIdOnly(OrderDataAccess.ORDER_ID, dbc);

      	  	dbc = new DBCriteria();
      	  	dbc.addEqualTo(OrderItemDataAccess.ITEM_ID, scid.getItemId());
      	  	dbc.addOneOf(OrderItemDataAccess.ORDER_ID, condn);

      	  	OrderItemDataVector oiDV = OrderItemDataAccess.select(con, dbc);

      	  	for(int i=0; i<oiDV.size(); i++){
      	  		OrderItemData oid = (OrderItemData)oiDV.get(i);

      	  		histQty += oid.getTotalQuantityOrdered();

      	  	}

      	  	if(histQty == 0){
      	  		ctrl.setHistoryOrderQty(0);
      	  		ctrl.setActualMaxQty(scid.getMaxOrderQty());
      	  		ctrl.setExpDate(current);
      	  	}else{
      	  		int newQty = scid.getMaxOrderQty() - histQty;
      	  		if(newQty > 0){
      	  			ctrl.setActualMaxQty(newQty);
      	  		}else{
      	  			ctrl.setActualMaxQty(0);
      	  		}
      	  		ctrl.setHistoryOrderQty(histQty);
      	  		ctrl.setExpDate(current);
      	  	}

      	  	if (ctrl.getShoppingControlId() > 0)
      	  		ShoppingControlDataAccess.update(con, ctrl);
      	//scid.setMaxOrderQty(ctrl.getActualMaxQty());

          }
          catch (Exception exc) {
              exc.printStackTrace();
              throw new RemoteException(exc.getMessage());
          }
          finally {
        	  try {
        		  con.close();
        	  }
        	  catch (Exception exc) {
        		  exc.printStackTrace();
        		  throw new RemoteException(exc.getMessage());
        	  }
          }


          }

          }

        }
      }
      return pCartItems;
    }


  /******************************************************************************/
  /**
   * Picks up order guide items
   * @param pOrderGuide  the OrderGuide object
   * @param pItems list of ShoppingCartItemData objects
   * @param pUser user login name
   * @return orderGuideId
   * @throws            RemoteException, DataNotFoundException
   */
 public int saveUserOrderGuide(OrderGuideData pOrderGuide, List pItems, String pUser )
      throws RemoteException, DataNotFoundException
  {
    Connection con = null;
    int id=0;
    try {
      con = getConnection();
      int userId = pOrderGuide.getUserId();
      int siteId = pOrderGuide.getBusEntityId();
      int catalogId = pOrderGuide.getCatalogId();
      String name = pOrderGuide.getShortDesc();
      id = pOrderGuide.getOrderGuideId();
      //Check if exists
      OrderGuideData oldOrderGuide = null;
      if(id!=0) {
        oldOrderGuide = OrderGuideDataAccess.select(con,id);
        int oldUserId = oldOrderGuide.getUserId();
        if(oldUserId!=userId){
          throw new RemoteException("saveUserOrderGuide(). New user does not match to old one");
        }
        int oldSiteId = oldOrderGuide.getBusEntityId();
        if(oldSiteId!=siteId){
          throw new RemoteException("saveUserOrderGuide(). New site does not match to old one");
        }
        oldOrderGuide.setCatalogId(catalogId);
        oldOrderGuide.setShortDesc(name);
        oldOrderGuide.setModBy(pUser);
        oldOrderGuide.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
        pOrderGuide = oldOrderGuide;
        OrderGuideDataAccess.update(con,oldOrderGuide);
        //Remove all existing items
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,id);
        OrderGuideStructureDataAccess.remove(con,dbc);
      } else {
        pOrderGuide.setAddBy(pUser);
        pOrderGuide.setModBy(pUser);
        pOrderGuide.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
        pOrderGuide = OrderGuideDataAccess.insert(con,pOrderGuide);
        id = pOrderGuide.getOrderGuideId();
      }
      //Save items
      if(pItems!=null) {
        for(int ii=0; ii<pItems.size(); ii++) {
          ShoppingCartItemData sciD = (ShoppingCartItemData) pItems.get(ii);
          int itemId = sciD.getProduct().getProductId();
          int categoryId = 0;
          CatalogCategoryData category = sciD.getCategory();
          if(category!=null) {
            categoryId = category.getCatalogCategoryId();
          } else {
            //Pickup any category
            CatalogCategoryDataVector ccDV = sciD.getProduct().getCatalogCategories();
            if(ccDV!=null && ccDV.size()>0) {
              category = (CatalogCategoryData) ccDV.get(0);
              categoryId = category.getCatalogCategoryId();
            }
          }

          OrderGuideStructureData orderGuideStructureD =
              OrderGuideStructureData.createValue();
          orderGuideStructureD.setOrderGuideId(id);
          orderGuideStructureD.setItemId(itemId);
          orderGuideStructureD.setCategoryItemId(categoryId);
          orderGuideStructureD.setQuantity(sciD.getQuantity());
          orderGuideStructureD.setAddBy(pUser);
          orderGuideStructureD.setModBy(pUser);

          OrderGuideStructureDataAccess.insert(con,orderGuideStructureD);
        }
      }
    }
    catch (NamingException exc) {
      exc.printStackTrace();
      throw new RemoteException("saveUserOrderGuide() Naming Exception happened");
    }
    catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException("saveUserOrderGuide() SQL Exception happened");
    }
    finally {
      try {
         con.close();
      }
      catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("saveUserOrderGuide() SQL Exception happened");
      }
    }
    return id;
   }
   //******************************************************************************************
  /**
   * Picks up janitor's closet items
   * @param pStoreType (store type defined in RefCodeNames interface)
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return collection of ShoppingCartItemDataVector
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getJanitorCloset(String pStoreType,
     int pCatalogId, int pContractId, boolean pContractOnly,
     int pUserId, int pSiteId) throws RemoteException {
    return getJanitorCloset(pStoreType, pCatalogId, pContractId, pContractOnly, pUserId, pSiteId, null);

  }
   public ShoppingCartItemDataVector getJanitorCloset(String pStoreType,
      int pCatalogId, int pContractId, boolean pContractOnly,
      int pUserId, int pSiteId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      return ShoppingDAO.getJanitorCloset(pStoreType, pCatalogId,
          pContractId, pContractOnly, pUserId, pSiteId, con, pCategToCostCenterView );
    } catch (Exception exc) {
      exc.printStackTrace();
      throw new RemoteException("getJanitorsCloset() Exception happened");
    } finally {
      closeConnection(con);
    }
  }
   // *************************************************************************************
  /**
   * Picks up last order information
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return OrderData object
   * @throws            RemoteException, DataNotFoundException
   */
   public OrderData getLastOrder(int pUserId, int pSiteId)
      throws RemoteException, DataNotFoundException
   {
    Connection con = null;
    OrderData orderD = null;
    try {
      DBCriteria dbc;
      con = getConnection();
      //Pick up last order
      String maxId = OrderDataAccess.ORDER_ID + " = (SELECT MAX( " +
      OrderDataAccess.ORDER_ID + ") FROM " +
      OrderDataAccess.CLW_ORDER +
    " WHERE " + OrderDataAccess.SITE_ID + " = " + pSiteId ;
      if (pUserId > 0)
      {
          maxId +=
          " AND " + OrderDataAccess.USER_ID + " = " + pUserId ;
      }
      maxId +=
          " AND " + OrderDataAccess.ORDER_STATUS_CD + " IN ('" + RefCodeNames.ORDER_STATUS_CD.ORDERED + "', '" +
          RefCodeNames.ORDER_STATUS_CD.INVOICED + "', '" +
          RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO + "', '" +
          RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED + "')" +
//          " AND NVL(" + OrderDataAccess.ORDER_TYPE_CD + ",' ') != '" + RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED + "'" +  //bug 3270
          ")";

      dbc = new DBCriteria();
      dbc.addCondition(maxId);
      OrderDataVector orderDV = OrderDataAccess.select(con,dbc);
      logDebug(" found orders: " + orderDV);
      if (orderDV!= null && orderDV.size()!=0) {
        orderD = (OrderData) orderDV.get(0);
      }
      //-------Pick up last original order from being consolidated-------
       dbc = new DBCriteria();
       String maxOriginalId = OrderDataAccess.ORDER_ID + " = (" +
           " SELECT MAX (orig.ORDER_ID) " +
           " FROM CLW_ORDER_ASSOC oa, CLW_ORDER cons, CLW_ORDER orig " +
           " WHERE   orig.ORDER_TYPE_CD = '" + RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED +"' " +
           " AND orig.ORDER_STATUS_CD = '" + RefCodeNames.ORDER_STATUS_CD.CANCELLED +"' " +
           " AND orig.site_Id = " + pSiteId +
           ((pUserId > 0) ? " AND orig.user_id = " + pUserId : "") +
           " AND orig.order_id = oa.ORDER1_ID " +
           " AND oa.ORDER_ASSOC_CD = '" + RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED + "' " +
           " AND oa.ORDER2_ID = cons.ORDER_ID " +
           " AND cons.ORDER_STATUS_CD IN " + OrderDAO.kGoodOrderStatusSqlList +")";
       dbc.addCondition(maxOriginalId);
       OrderDataVector orderDV1 = OrderDataAccess.select(con,dbc);
       if(orderDV1 != null && orderDV1.size()!=0) {
         OrderData orderD1 = (OrderData) orderDV1.get(0);
         if (orderD1 != null ) {
           if (orderD == null || orderD1.getOrderId() > orderD.getOrderId()) {
             orderD = orderD1;
           }
         }
       }

      //-----------------------------------------------------------------------
      if(orderD == null ) {
        throw new DataNotFoundException
      ("No order found for user: " + pUserId +
       " at site " + pSiteId );
      }

      logDebug("getLastOrder, found: " + orderD);

    }
    catch (DataNotFoundException e) {
      e.printStackTrace();
      throw new DataNotFoundException(e.getMessage());
    }
    catch (NamingException exc) {
      exc.printStackTrace();
      throw new RemoteException("getLastOrder() Naming Exception happened");
    }
    catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException("getLastOrder() SQL Exception happened");
    }
    finally {
        closeConnection(con);
    }
    return orderD;
   }

  /**
   * Picks up last order items
   * @param pStoreType (store type defined in RefCodeNames interface)
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return collection of ShoppingCartItemDataVector
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getLastOrderItems
      (String pStoreType,  SiteData pSiteData, int pCatalogId, OrderData pLastOrder )
     throws RemoteException
  {
    return getLastOrderItems
      ( pStoreType,  pSiteData, pCatalogId, pLastOrder, null );
  }
   public ShoppingCartItemDataVector getLastOrderItems
       (String pStoreType,  SiteData pSiteData, int pCatalogId, OrderData pLastOrder, AccCategoryToCostCenterView pCategToCostCenterView )
      throws RemoteException
   {
    Connection con = null;
    ShoppingCartItemDataVector shoppingCartItemDV =
  new ShoppingCartItemDataVector();
    try {
      DBCriteria dbc;
      con = getConnection();

      dbc = new DBCriteria();
      dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,
         pLastOrder.getOrderId());
      dbc.addOrderBy(OrderItemDataAccess.ITEM_ID);

      OrderItemDataVector orderItemDV = OrderItemDataAccess.select(con,dbc);
      IdVector itemIds = new IdVector();
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        itemIds.add(new Integer(oiD.getItemId()));
      }

        //Create shopping cart items
        if (pSiteData.isUseProductBundle()) {
            shoppingCartItemDV = prepareShoppingItemsNew(con,
                    pStoreType,
                    pCatalogId,
                    pLastOrder.getContractId(),
                    itemIds,
                    pSiteData.getSiteId(),
                    pCategToCostCenterView);
        } else {
            shoppingCartItemDV = prepareShoppingItems(con,
                    pCatalogId,
                    pLastOrder.getContractId(),
                    itemIds, pCategToCostCenterView);
        }
      logDebug ( "got cart items: " + shoppingCartItemDV );

      for(int ii=0, jj=0; ii<shoppingCartItemDV.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData)
      shoppingCartItemDV.get(ii);
        int id = sciD.getProduct().getProductId();
        ItemShoppingHistoryData historyD = new ItemShoppingHistoryData();
        while(jj<orderItemDV.size()) {
          OrderItemData oiD = (OrderItemData) orderItemDV.get(jj);
          if(oiD.getItemId() < id) {
            jj++;
            continue;
          } else if(oiD.getItemId()==id) {
            Date addDate = oiD.getAddDate();
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(addDate);
            GregorianCalendar cal1 =
               new GregorianCalendar(cal.get(GregorianCalendar.YEAR),cal.get(GregorianCalendar.MONTH),cal.get(GregorianCalendar.DAY_OF_MONTH));
            historyD.setLastDate(cal1.getTime());
            historyD.setLastQty(oiD.getTotalQuantityOrdered());
            historyD.setLastPrice(oiD.getCustContractPrice().doubleValue());
            jj++;
            continue;
          } else {
            break;
          }
        }
        sciD.setShoppingHistory(historyD);
      }
      shoppingCartItemDV = ShoppingDAO.setActualSkus(pStoreType, shoppingCartItemDV);

      // Set the inventory info for each item.
        if (pSiteData.hasModernInventoryShopping()) {
            shoppingCartItemDV = setupItemInventoryInfo(con, pSiteData, shoppingCartItemDV,pCategToCostCenterView);
        } else {
            shoppingCartItemDV = setupItemInventoryInfo(con,pSiteData.getAccountId(),shoppingCartItemDV,
                                                        pSiteData.getSiteInventory(),
                                                        null,pCategToCostCenterView);
        }

      shoppingCartItemDV = setupMaxOrderQtyValues(pSiteData, shoppingCartItemDV);
      return shoppingCartItemDV;
    }
    catch (Exception exc) {
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    }
    finally {
        closeConnection(con);
    }

   }


  //*************************************************************************************
  /**
   * Calculates freight amount. Does not apply if pContractId=0 or no active freight table found for the contact
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pAmount  purchase amount
   * @param pWeight purchase weigh
   * @return freight charge amount.
   * @throws            RemoteException
   */

   public BigDecimal getFreightAmt(int pContractId, BigDecimal pAmount, OrderHandlingView pOrder)
      throws RemoteException
   {

	log.info("************SVC: pAmount = " + pAmount);

    Connection con = null;
    BigDecimal freightAmt = new BigDecimal(0);
    if(pContractId==0) {
      return freightAmt; //No contract - no charge !!!!!!!!!!
    }
    try {
        con = getConnection();
        FreightTableData freightTableData = getFreightTableByContract(con, pContractId);
        if (freightTableData == null) {
            return freightAmt; //No table - no charge !!!!!!!!!!
        }
        String freightType = freightTableData.getFreightTableTypeCd();
        FreightTableCriteriaDataVector intervals =
            getFreightTableCriteriasByChargeCd(con, freightTableData.getFreightTableId(), null,
                    true, false);
        if (intervals == null) {
            return freightAmt;
        }

      if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS) ||
         freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE) ) {
        BigDecimal val = new BigDecimal(0);
        for(int ii=0; ii<intervals.size(); ii++) {
          FreightTableCriteriaData interval = (FreightTableCriteriaData) intervals.get(ii);
          BigDecimal lowerAmt = interval.getLowerAmount();
          BigDecimal higherAmt = interval.getHigherAmount();
          logDebug("getFreightAmt, lowerAmt=" + lowerAmt + ", higherAmt=" + higherAmt);

          if(lowerAmt==null) {
            continue;
          }
          if(lowerAmt.compareTo(pAmount)<=0 &&
             (higherAmt==null || higherAmt.doubleValue()<0.001 || higherAmt.compareTo(pAmount)>=0)) {
            val = interval.getFreightAmount();
            if(val==null) {
              val = new BigDecimal(0);
            }
            break;
          }
        }
        if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS)) {
          freightAmt = val;
        } else {
          val = val.movePointLeft(2);
          freightAmt = pAmount.multiply(val);
        }
        log.info("****SVC: getFreightAmt, freightAmt = " + freightAmt);
      }

      // freight by weight
      else if ( freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME) ||
                freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND)) {

        BigDecimal orderWeight = getOrderWeight(con, pOrder, freightType);
        for(int ii=0; ii<intervals.size(); ii++) {
          FreightTableCriteriaData interval = (FreightTableCriteriaData) intervals.get(ii);
          BigDecimal lowerWeight = interval.getLowerAmount();
          BigDecimal higherWeight = interval.getHigherAmount();
          logDebug("lowerWeight, lowerAmt=" + lowerWeight + ", higherWeight=" + higherWeight);
          if(lowerWeight==null) {
            lowerWeight = new BigDecimal(0);
          }
          if(lowerWeight.compareTo(orderWeight)<=0 &&
             (higherWeight==null || higherWeight.doubleValue()<0.001 || higherWeight.compareTo(orderWeight)>=0)) {
            freightAmt = interval.getFreightAmount();
            if(freightAmt==null) {
              freightAmt = new BigDecimal(0);
            }
            //break;
          }
        }
      }
      else {
         throw new RemoteException("getFreightAmt(). Unknown freight table type: "+freightType);
      }

    } catch (NamingException exc) {
        exc.printStackTrace();
        throw new RemoteException("getFreightAmt() Naming Exception happened");
    } catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("getFreightAmt() SQL Exception happened");
    } finally {
        try { con.close();  }
        catch (SQLException exc) {}
    }

    return freightAmt;
   }

   /**
    * Created by SVC 6/15/2009
    * Calculates discount amount. Does not apply if pContractId=0 or no active discount table found for the contact
    * @param pContractId the contract identifier to filter out extra items, if not 0
    * @param pAmount  purchase amount
    * @param pOrder
    * @return discount charge amount.
    * @throws            RemoteException
    */
   public BigDecimal getDiscountAmt(int pContractId, BigDecimal pAmount, OrderHandlingView pOrder)
   throws RemoteException
   {
    Connection con = null;
    BigDecimal discountAmt = new BigDecimal(0);
    if(pContractId==0) {
      return discountAmt;//No contract - no discount
    }
    try {
        con = getConnection();
        FreightTableData freightTableData = getDiscountTableByContract(con, pContractId);
        if (freightTableData == null) {
            return discountAmt; //No table - no charge !!!!!!!!!!
        }
        String freightType = freightTableData.getFreightTableTypeCd();
        FreightTableCriteriaDataVector intervals =
            getFreightTableCriteriasByChargeCd(con, freightTableData.getFreightTableId(), RefCodeNames.CHARGE_CD.DISCOUNT);

      if(intervals.size()>0) {
      if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS) ||
        freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE) ) {
        BigDecimal val = new BigDecimal(0);
        for(int ii=0; ii<intervals.size(); ii++) {
          FreightTableCriteriaData interval = (FreightTableCriteriaData) intervals.get(ii);
          BigDecimal lowerAmt = interval.getLowerAmount();
          BigDecimal higherAmt = interval.getHigherAmount();
          logDebug("getDiscountAmt(), lowerAmt=" + lowerAmt + ", higherAmt=" + higherAmt);
          log.info("getDiscountAmt(), lowerAmt=" + lowerAmt + ", higherAmt=" + higherAmt);

          if(lowerAmt==null) {
            continue;
          }
          if(lowerAmt.compareTo(pAmount)<=0 &&
             (higherAmt==null || higherAmt.doubleValue()<0.001 || higherAmt.compareTo(pAmount)>=0)) {
            val = interval.getDiscount();
            if(val==null) {
              val = new BigDecimal(0);
            }
            break;
          } // endif
        } // end for (int ii
        if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS)) {
          discountAmt = val;
        } else { //PERCENTAGE-DOLLARS; calculate value in percentage
          val = val.movePointLeft(2);
          discountAmt = pAmount.multiply(val);
        } // endif
      } // if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS)...
      else {
         throw new RemoteException("getDiscountAmt(). Unknown freight table type: "+freightType);
      } // if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS)...
	  }

    } catch (NamingException exc) {
        exc.printStackTrace();
        throw new RemoteException("getDiscountAmt() Naming Exception happened");
    } catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("getDiscountAmt() SQL Exception happened");
    } finally {
        try { con.close();  }
        catch (SQLException exc) {}
    }

    return discountAmt;
  }
  /******************************************************************************************/

   private BigDecimal getOrderWeight(Connection con, OrderHandlingView pOrder, String pWeightUnit)
   throws RemoteException {
     BigDecimal result = new BigDecimal(0);
     OrderHandlingItemViewVector items = pOrder.getItems();
     ArrayList al = new ArrayList();
     al.add("SHIP_WEIGHT");
     al.add("WEIGHT_UNIT");

     for (int i=0; i<items.size(); i++) {
       OrderHandlingItemView item = (OrderHandlingItemView)items.get(i);
       int qty = item.getQty();
       // get item weight and weight unit
       BigDecimal itemWeight = null;
       String itemWeightUnit = null;
       try {
         DBCriteria dbc = new DBCriteria();
         dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, item.getItemId());
         dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, al);
         ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(con, dbc);
         for (int j = 0; j < itemMetaDV.size(); j++) {
           ItemMetaData itemMetaD = (ItemMetaData) itemMetaDV.get(j);
           if (itemMetaD.getNameValue().equals("SHIP_WEIGHT")) {
             try {
               itemWeight = new BigDecimal(itemMetaD.getValue());
             } catch (Exception e) {
               itemWeight = new BigDecimal(0);
             }
           } else if (itemMetaD.getNameValue().equals("WEIGHT_UNIT")) {
             itemWeightUnit = itemMetaD.getValue();
           }
         }
       }
         catch (SQLException exc) {
           exc.printStackTrace();
           throw new RemoteException("getHandlingAmt() SQL Exception happened");
         }

       if (itemWeight != null && itemWeightUnit != null) {
         itemWeight = itemWeight.multiply(new BigDecimal(qty));
         BigDecimal weightConv = Utility.getWeight(itemWeight, itemWeightUnit, pWeightUnit);
         result = result.add(weightConv);
       }
     }

     return result;
   }



   //*************************************************************************************
  /**
   * Calculates handling amount. Does not apply if pContractId=0 or no active freight table found for the contact
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pAmount  purchase amount
   * @param pWeight purchase weigh
   * @return handling charge amount.
   * @throws            RemoteException
   */

   public BigDecimal getHandlingAmt(int pContractId, BigDecimal pAmount, OrderHandlingView pOrder)
      throws RemoteException
   {
	log.info("***SVC: getHandlingAmt(), pAmount = " + pAmount);
    Connection con = null;
    BigDecimal handlingAmt = new BigDecimal(0);
    if(pContractId==0) {
      return handlingAmt; //No contract - no charge !!!!!!!!!!
    }
    try {
        con = getConnection();
        FreightTableData freightTableData = getFreightTableByContract(con, pContractId);
        if (freightTableData == null) {
            return handlingAmt; //No freight table - no charge !!!!!!!!!!
        }
        String freightType = freightTableData.getFreightTableTypeCd();
        FreightTableCriteriaDataVector intervals =
            getFreightTableCriteriasByChargeCd(con, freightTableData.getFreightTableId(), null,
                    false, true);
        if (intervals == null) {
            return handlingAmt;
        }

      if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS) ||
         freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE) ) {
        BigDecimal val = new BigDecimal(0);
        for(int ii=0; ii<intervals.size(); ii++) {
          FreightTableCriteriaData interval = (FreightTableCriteriaData) intervals.get(ii);
          BigDecimal lowerAmt = interval.getLowerAmount();
          BigDecimal higherAmt = interval.getHigherAmount();
          logDebug("getHandlingAmt, lowerAmt=" + lowerAmt + ", higherAmt=" + higherAmt);
          if(lowerAmt==null) {
            continue;
          }
          if(lowerAmt.compareTo(pAmount)<=0 &&
             (higherAmt==null || higherAmt.doubleValue()<0.001 || higherAmt.compareTo(pAmount)>=0)) {
            val = interval.getHandlingAmount();
            if(val==null) {
              val = new BigDecimal(0);
            }
            break;
          }
        }
        if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS)) {
          handlingAmt = val;
        } else {
          val = val.movePointLeft(2);
          handlingAmt = pAmount.multiply(val);
        }
        logDebug("getHandlingAmt, handlingAmt=" + handlingAmt);
        log.info("****SVC: getHandlingAmt, handlingAmt=" + handlingAmt);
      }
      // else if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.WEIGHT)) {
      //   throw new RemoteException("getHandlingAmt(). Do not process freight table type: "+freightType);
      //} else if(freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.WEIGHT_PERCENTAGE)) {
      //   throw new RemoteException("getHandlingAmt(). Do not process freight table type: "+freightType);
      //}
      // handling by weight
      else if ( freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME) ||
                freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND)) {

        BigDecimal orderWeight = getOrderWeight(con, pOrder, freightType);
        for(int ii=0; ii<intervals.size(); ii++) {
          FreightTableCriteriaData interval = (FreightTableCriteriaData) intervals.get(ii);
          BigDecimal lowerWeight = interval.getLowerAmount();
          BigDecimal higherWeight = interval.getHigherAmount();
          logDebug("lowerWeight, lowerAmt=" + lowerWeight + ", higherWeight=" + higherWeight);
          if(lowerWeight==null) {
            lowerWeight = new BigDecimal(0);
          }
          if(lowerWeight.compareTo(orderWeight)<=0 &&
             (higherWeight==null || higherWeight.doubleValue()<0.001 || higherWeight.compareTo(orderWeight)>=0)) {
            handlingAmt = interval.getHandlingAmount();
            if(handlingAmt==null) {
              handlingAmt = new BigDecimal(0);
            }
            break;
          }
        }
      }
      else {
         throw new RemoteException("getHandlingAmt(). Unknown freight table type: "+freightType);
      }

    } catch (NamingException exc) {
        exc.printStackTrace();
        throw new RemoteException("getHandlingAmt() Naming Exception happened");
    }
    catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("getHandlingAmt() SQL Exception happened");
    } finally {
        try { con.close();  }
        catch (SQLException exc) {}
    }

    return handlingAmt;
   }



   //*************************************************************
    private Date getCurrentDate() {
  GregorianCalendar cal = new GregorianCalendar();
  cal.setTime(new Date(System.currentTimeMillis()));
  cal = new GregorianCalendar
      (cal.get(Calendar.YEAR),
       cal.get(Calendar.MONTH),
       cal.get(Calendar.DAY_OF_MONTH));

  return cal.getTime();
    }

    private static Date getYearBegin() {
  GregorianCalendar cal = new GregorianCalendar();
  cal.setTime(new Date(System.currentTimeMillis()));
  cal = new GregorianCalendar(cal.get(Calendar.YEAR),Calendar.JANUARY,1);
  return cal.getTime();
    }
   //*************************************************************************************



     //*************************************************************************************
  /**
   * Calculates freight and handling amount for the order.
   * @param pOrder ejb interfacfe object to calculate freight and handling amounts
   * @return the parameter pOrder with polulated totalFraight and totalHandiling fields
   * @throws            RemoteException
   */
   public OrderHandlingView calcTotalFreightAndHandlingAmount(OrderHandlingView pOrder)
   throws RemoteException
   {
     int contractId = pOrder.getContractId();
     OrderHandlingDetailViewVector handlingDetVwV = new OrderHandlingDetailViewVector();
    /*
    * What we need for today
     * - contract id
     * - account id
     * - total applied price
     * - total applied weight
     * - list of item ids
     * - list of applied item prices
     * - list of ordered quanities
    */
     //BigDecimal freightAmt = getFreightAmt(contractId, amount, weight);
     //pOrder.setTotalFreight(freightAmt);
     //BigDecimal handlingAmt = getHandlingAmt(contractId, amount, weight);
     //pOrder.setTotalHandling (handlingAmt);

     Connection conn = null;
     try {
      DBCriteria dbc = new DBCriteria();
      conn = getConnection();
      dbc.addEqualTo(PriceRuleDataAccess.PRICE_RULE_TYPE_CD,"FREIGHT");
      dbc.addEqualTo(PriceRuleDataAccess.PRICE_RULE_STATUS_CD,"ACTIVE");
      dbc.addOrderBy(PriceRuleDataAccess.PRICE_RULE_ID);
      java.util.Date curDate = new java.util.Date();
      PriceRuleDataVector ruleDV = PriceRuleDataAccess.select(conn,dbc);
      PriceRuleDataVector activeRuleDV = new PriceRuleDataVector();
      IdVector activeRuleIds = new IdVector();

      for(int ii=0; ii<ruleDV.size(); ii++) {
        PriceRuleData ruleD = (PriceRuleData) ruleDV.get(ii);
        java.util.Date effDate = ruleD.getEffDate();
        java.util.Date expDate = ruleD.getExpDate();
        if((effDate!=null && effDate.after(curDate))
          ||(expDate!=null && expDate.before(curDate)))
        {
          continue; // rule is incative
        }
        activeRuleDV.add(ruleD);
        activeRuleIds.add(new Integer(ruleD.getPriceRuleId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(PriceRuleDetailDataAccess.PRICE_RULE_ID,activeRuleIds);
      dbc.addOrderBy(PriceRuleDetailDataAccess.PRICE_RULE_ID);
      PriceRuleDetailDataVector priceRuleDetails =
                            PriceRuleDetailDataAccess.select(conn,dbc);
      ArrayList priceCalculateObjects = new ArrayList();
      PriceRuleDetailDataVector details = new PriceRuleDetailDataVector();

      //Prepare freight calculate objects
      for(int ii=0, jj=0; ii<activeRuleDV.size(); ii++) {
        PriceRuleData ruleD = (PriceRuleData) ruleDV.get(ii);
        int ruleId = ruleD.getPriceRuleId();
        for(;jj<priceRuleDetails.size();jj++){
          PriceRuleDetailData prdD = (PriceRuleDetailData)  priceRuleDetails.get(jj);
          int rId = prdD.getPriceRuleId();
          if(ruleId<rId) {
            break;
          }
          if(ruleId==rId) {
            details.add(prdD);
          }
        }
        String ruleName = ruleD.getShortDesc();
        boolean processFl = false;
        if("PadSkuFreight".equalsIgnoreCase(ruleName)) {
          PadSkuFreight cpf = new PadSkuFreight(ruleD,details);
          priceCalculateObjects.add(cpf);
          processFl = true;
        }
        else if("SkuFlatFreight".equalsIgnoreCase(ruleName)) {
          SkuFlatFreight sff = new SkuFlatFreight(ruleD,details);
          priceCalculateObjects.add(sff);
          processFl = true;
        }
        else if("SkuQuantityFreight".equalsIgnoreCase(ruleName)) {
          SkuQuantityFreight sqf = new SkuQuantityFreight(ruleD,details);
          priceCalculateObjects.add(sqf);
          processFl = true;
        }
        else if("SkuPriceFreight".equalsIgnoreCase(ruleName)) {
          SkuPriceFreight spf = new SkuPriceFreight(ruleD,details);
          priceCalculateObjects.add(spf);
          processFl = true;
        }
        else if("TotalAmountFreight".equalsIgnoreCase(ruleName)) {
          TotalAmountFreight spf = new TotalAmountFreight(ruleD,details);
          priceCalculateObjects.add(spf);
          processFl = true;
        }
        if(processFl) {
          OrderHandlingDetailView ohDVw = OrderHandlingDetailView.createValue();
          handlingDetVwV.add(ohDVw);
          ohDVw.setPriceRuleId(ruleId);
          ohDVw.setRuleShortDesc(ruleName);
          ohDVw.setChargeTypeCd(RefCodeNames.CHARGE_TYPE_CD.SPECIAL_FREIGHT_RULE);
        }
        details = new  PriceRuleDetailDataVector();
      }
      //Initialize fregight calculate objects
      ArrayList excludeItems = new ArrayList();
      Iterator iter = handlingDetVwV.iterator();
      for(int ii=0; ii<priceCalculateObjects.size(); ii++) {
        OrderHandlingDetailView ohdVw = (OrderHandlingDetailView) iter.next();
        ArrayList elV = null;
        Object calcObj = priceCalculateObjects.get(ii);
        if(calcObj instanceof SkuFlatFreight){
          SkuFlatFreight sff = (SkuFlatFreight) calcObj;
          elV = sff.initOrderItems(conn, pOrder);
          if(elV.size()>0 && sff.excludeFromGeneralRule()) {
            excludeItems.addAll(elV);
          }
        } else if (calcObj instanceof SkuQuantityFreight){
          SkuQuantityFreight sqf = (SkuQuantityFreight) calcObj;
          elV = sqf.initOrderItems(conn, pOrder);
          if(elV.size()>0 && sqf.excludeFromGeneralRule()) {
            excludeItems.addAll(elV);
          }
        } else if (calcObj instanceof SkuPriceFreight){
          SkuPriceFreight spf = (SkuPriceFreight) calcObj;
          elV = spf.initOrderItems(conn, pOrder);
          if(elV.size()>0 && spf.excludeFromGeneralRule()) {
            excludeItems.addAll(elV);
          }
        } else if (calcObj instanceof PadSkuFreight){
          PadSkuFreight psf = (PadSkuFreight) calcObj;
          elV = psf.initOrderItems(conn, pOrder);
          if(elV.size()>0 && psf.excludeFromGeneralRule()) {
            excludeItems.addAll(elV);
          }
        } else if (calcObj instanceof TotalAmountFreight){
          TotalAmountFreight psf = (TotalAmountFreight) calcObj;
          elV = psf.initOrderItems(conn, pOrder);
          if(elV.size()>0 && psf.excludeFromGeneralRule()) {
            excludeItems.addAll(elV);
          }
        }
        ohdVw.setItemIdVector(elV);

      }
      //Calculate order  freight using general method
      BigDecimal amount = getOrderAmt(pOrder.getItems(), excludeItems);
      //amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
      amount = amount.setScale(2, java.math.RoundingMode.HALF_UP);
      //BigDecimal freightAmt = getFreightAmt(contractId, amount, new BigDecimal(0));
      log.info("*****SVC: calcTotalFreightAndHandlingAmount(), amount = " + amount);
      BigDecimal freightAmt = getFreightAmt(contractId, amount, pOrder);

      pOrder.setTotalFreight(freightAmt);
      BigDecimal handlingAmt = getHandlingAmt(contractId, amount, pOrder);
      pOrder.setTotalHandling (handlingAmt);

      //Calculate additional freight charges
      iter = handlingDetVwV.iterator();
      BigDecimal extraHandling = new BigDecimal(0);
      for(int ii=0; ii<priceCalculateObjects.size(); ii++) {
        OrderHandlingDetailView ohdVw = (OrderHandlingDetailView) iter.next();
        ArrayList elV = null;
        Object calcObj = priceCalculateObjects.get(ii);
        if(calcObj instanceof SkuFlatFreight){
          SkuFlatFreight sff = (SkuFlatFreight) calcObj;
          BigDecimal freight = sff.calcFreight();
          ohdVw.setAmount(freight);
          extraHandling = extraHandling.add(freight);
        } else if (calcObj instanceof SkuQuantityFreight){
          SkuQuantityFreight sqf = (SkuQuantityFreight) calcObj;
          BigDecimal freight = sqf.calcFreight();
          ohdVw.setAmount(freight);
          extraHandling = extraHandling.add(freight);
        } else if (calcObj instanceof SkuPriceFreight){
          SkuPriceFreight spf = (SkuPriceFreight) calcObj;
          BigDecimal freight = spf.calcFreight();
          ohdVw.setAmount(freight);
          extraHandling = extraHandling.add(freight);
        } else if (calcObj instanceof PadSkuFreight){
          PadSkuFreight psf = (PadSkuFreight) calcObj;
          BigDecimal freight = psf.calcFreight();
          ohdVw.setAmount(freight);
          extraHandling = extraHandling.add(freight);
        } else if (calcObj instanceof TotalAmountFreight){
          TotalAmountFreight psf = (TotalAmountFreight) calcObj;
          BigDecimal freight = psf.calcFreight();
          ohdVw.setAmount(freight);
          extraHandling = extraHandling.add(freight);
        }
      }
      BigDecimal totalFreight = pOrder.getTotalFreight();
      if(totalFreight==null) totalFreight = new BigDecimal(0);
      BigDecimal totalHandling = pOrder.getTotalHandling();
      if(totalHandling==null) totalHandling = new BigDecimal(0);

      if(totalFreight.add(totalHandling).abs().doubleValue()>0.001) {
        OrderHandlingDetailView ohDVw = OrderHandlingDetailView.createValue();
        handlingDetVwV.add(0,ohDVw);
        ohDVw.setPriceRuleId(0);
        ohDVw.setRuleShortDesc("");
        ohDVw.setAmount(totalFreight.add(totalHandling));
        ohDVw.setChargeTypeCd(RefCodeNames.CHARGE_TYPE_CD.TOTAL_AMOUNT_FREIGTH);
        ohDVw.setItemIdVector(excludeItems);
      }
      totalHandling = totalHandling.add(extraHandling);
      pOrder.setTotalHandling(totalHandling);

      for (iter = handlingDetVwV.iterator(); iter.hasNext();) {
        OrderHandlingDetailView ohdVw = (OrderHandlingDetailView) iter.next();
        amount = ohdVw.getAmount();
        if(amount==null || amount.abs().doubleValue()<0.001) iter.remove();
      }
      pOrder.setDetail(handlingDetVwV);

     } catch (Exception exc) {
      exc.printStackTrace();
      throw new RemoteException("calcTotalFreightAndHandlingAmount() Exception happened");
     }
     finally {
      try { conn.close();  }
      catch (SQLException exc) {}
     }


     return pOrder;
   }

   public HashMap calculateServiceFee(int contractId, List cartItems,int accId)
   throws RemoteException{


	   String serviceFeeS = "";
	   BigDecimal serviceFee = new BigDecimal(0.0);
	   HashMap sfDetail = new HashMap();
	   HashMap item_codes = new HashMap();

	   Hashtable fees = new Hashtable();

	   IdVector ids = new IdVector();
	   List allCodes = new ArrayList();

	   for (int f = 0; f < cartItems.size();f++) {
		   ShoppingCartItemData sData = (ShoppingCartItemData)cartItems.get(f);
		   ids.add(new Integer(sData.getItemId()));
	   }

	   ContractItemDataVector contractItems = new ContractItemDataVector();

	   try{
		   Contract contractEjb = APIAccess.getAPIAccess().getContractAPI();
		   contractItems = contractEjb.getContractItemCollectionByItem(contractId, ids);
	   }catch (NamingException exc) {
		   exc.printStackTrace();
		   throw new RemoteException("calculateServiceFee() Exception happened");
	   }catch(APIServiceAccessException exc){
		   exc.printStackTrace();
		   throw new RemoteException("calculateServiceFee() Exception happened");
	   }catch(DataNotFoundException exc){
		   exc.printStackTrace();
		   throw new RemoteException("calculateServiceFee() Exception happened");
	   }

	   //Get all service codes for items
	   for(int i=0; i<contractItems.size(); i++){
		   ContractItemData cid = (ContractItemData)contractItems.get(i);
		   if(cid.getServiceFeeCode()!=null){
			   item_codes.put(new Integer(cid.getItemId()),
					   new ServiceFeeDetail(cid.getItemId(),cid.getServiceFeeCode(),new BigDecimal(0),0));

			   if(!allCodes.contains(cid.getServiceFeeCode())){
				   allCodes.add(cid.getServiceFeeCode());
			   }
		   }
	   }

	   Connection conn = null;
	   try {
		   DBCriteria dbc = new DBCriteria();
		   conn = getConnection();

		   dbc.addEqualTo(PriceRuleDataAccess.PRICE_RULE_TYPE_CD,RefCodeNames.PRICE_RULE_TYPE_CD.SERVICE_FEE);
		   dbc.addEqualTo(PriceRuleDataAccess.BUS_ENTITY_ID, accId);
		   dbc.addEqualTo(PriceRuleDataAccess.PRICE_RULE_STATUS_CD,"ACTIVE");
		   dbc.addOrderBy(PriceRuleDataAccess.PRICE_RULE_ID);

		   IdVector ruleIds = PriceRuleDataAccess.selectIdOnly(conn, dbc);

		   IdVector selectedRuleIds = new IdVector();
		   dbc = new DBCriteria();
		   dbc.addOneOf(PriceRuleDetailDataAccess.PRICE_RULE_ID, ruleIds);
		   dbc.addOneOf(PriceRuleDetailDataAccess.PARAM_VALUE, allCodes);

		   PriceRuleDetailDataVector selectedRules = PriceRuleDetailDataAccess.select(conn, dbc);

		   //populate service fee price rule
		   Iterator it = item_codes.keySet().iterator();
		   while(it.hasNext()){
			   ServiceFeeDetail sfd = (ServiceFeeDetail)item_codes.get(it.next());

			   for(int jj=0; jj<selectedRules.size(); jj++){
				   PriceRuleDetailData ruleD = (PriceRuleDetailData)selectedRules.get(jj);
				   if(sfd.getCode().equals(ruleD.getParamValue())){
					   sfd.setRuleId(ruleD.getPriceRuleId());
				   }

				   if(!selectedRuleIds.contains(new Integer(ruleD.getPriceRuleId()))){
					   selectedRuleIds.add(new Integer(ruleD.getPriceRuleId()));
				   }
			   }
		   }

		   dbc = new DBCriteria();
		   dbc.addOneOf(PriceRuleDetailDataAccess.PRICE_RULE_ID, selectedRuleIds);
		   dbc.addEqualTo(PriceRuleDetailDataAccess.PARAM_NAME, RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_AMOUNT);

		   PriceRuleDetailDataVector details = PriceRuleDetailDataAccess.select(conn, dbc);

		   // populate service fee amount
		   Iterator iter = item_codes.keySet().iterator();
		   while(iter.hasNext()){
			   ServiceFeeDetail sfd = (ServiceFeeDetail)item_codes.get(iter.next());
			   int thisRuleId = sfd.getRuleId();

			   for(int p=0; p<details.size(); p++){
				   PriceRuleDetailData prd = (PriceRuleDetailData)details.get(p);

				   if(thisRuleId == prd.getPriceRuleId()){
					   sfd.setAmount(new BigDecimal(prd.getParamValue()));
				   }
			   }
		   }

		   //Populate service fee detail obj
		   for (int s = 0;s < cartItems.size();s++) {
			   ShoppingCartItemData sData = (ShoppingCartItemData)cartItems.get(s);
			   int qty = sData.getQuantity();
			   Integer item = new Integer(sData.getItemId());

			   ServiceFeeDetail finalDet = (ServiceFeeDetail)item_codes.get(item);
			   if(finalDet!=null){
				   /*BigDecimal amt = finalDet.getAmount();
				   BigDecimal totAmt = amt.multiply(new BigDecimal(qty));
				   serviceFee = serviceFee.add(totAmt);*/
				   sfDetail.put(item, finalDet);
			   }

		   }

	   } catch (Exception exc) {
		   exc.printStackTrace();
		   throw new RemoteException("calculateServiceFee() Exception happened");
	   }
	   finally {
		   try { conn.close();  }
		   catch (SQLException exc) {}
	   }
	   return sfDetail;

   }

   //---------------------------------------------------------------------------
   private BigDecimal getOrderAmt(OrderHandlingItemViewVector pHandlingItems, ArrayList pExcludeItems)
      throws Exception
   {
    BigDecimal amount = new BigDecimal(0);
    Object[] exclA = new Object[0];
    if(pExcludeItems!=null) {
      exclA = pExcludeItems.toArray();
    }
    for(int ii=0; ii<pHandlingItems.size(); ii++) {
      OrderHandlingItemView oiVw = (OrderHandlingItemView) pHandlingItems.get(ii);
      int itemId = oiVw.getItemId();
      boolean validFl = true;
        for(int jj=0; jj<exclA.length; jj++) {
          Integer idI = (Integer) exclA[jj];
          if(idI.intValue()==itemId){
            validFl = false;
            break;
          }
        }
        if(validFl) {
          BigDecimal price = oiVw.getPrice();
          int qty = oiVw.getQty();
          if(price!=null && qty!=0) {
            amount = amount.add(price.multiply(new BigDecimal(qty)));
          }
        }
      }

    return amount;
   }

   //*************************************************************************************
   /*
   private BigDecimal calcPadSkuFreight (Connection conn, int pRuleId, OrderHandlingView pOrder)
   throws SQLException,  RemoteException
   {
      BigDecimal padSkuFreight = new BigDecimal(0);
      BigDecimal currFrAndHandle = pOrder.getTotalFreight();
      if(currFrAndHandle ==null) currFrAndHandle = new BigDecimal(0);
      BigDecimal currHandle = pOrder.getTotalHandling();
      if(currHandle ==null) currHandle = new BigDecimal(0);
      currFrAndHandle = currFrAndHandle.add(currHandle);
      if(currFrAndHandle.compareTo(new BigDecimal(0))==0) {
        return new BigDecimal(0); //rule does not work if no freigt or handling charges applied
      }

      int siteId = pOrder.getSiteId();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteId);
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
      String acctReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
      dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,acctReq);
      BusEntityDataVector acctDV = BusEntityDataAccess.select(conn,dbc);
      if(acctDV.size()==0) {
        throw new RemoteException("No active account for the site. Site Id: "+siteId);
      }
      if(acctDV.size()>1) {
        throw new RemoteException("More than 1 active account for the site. Site Id: "+siteId);
      }
      BusEntityData acctD = (BusEntityData) acctDV.get(0);
      int accountId = acctD.getBusEntityId();
      int contractId = pOrder.getContractId();
      dbc = new DBCriteria();
      dbc.addEqualTo(PriceRuleDetailDataAccess.PRICE_RULE_ID,pRuleId);
      PriceRuleDetailDataVector paramDV = PriceRuleDetailDataAccess.select(conn,dbc);
      LinkedList ruleItems = new LinkedList();
      int ruleAccountId = -1;
      boolean contractFlag = false;
      int ruleQty = -1;
      BigDecimal freightRate = null;
      for(int ii=0; ii<paramDV.size(); ii++) {
        PriceRuleDetailData paramD = (PriceRuleDetailData) paramDV.get(ii);
        String paramName = paramD.getParamName();
        String paramValue = paramD.getParamValue();
        String errorMsg =
          "Wrong "+paramName+" parameter format for price rule. Rule Id: "+pRuleId+" Param: "+paramName+" Value: "+paramValue;
        if("Account".equalsIgnoreCase(paramName)) {
          ruleAccountId = str2int(paramValue, errorMsg);
        } else if ("Contract".equalsIgnoreCase(paramName)) {
          int ruleContractId =  str2int(paramValue, errorMsg);
          if(ruleContractId==contractId) contractFlag = true;
        } else if ("Quantity".equalsIgnoreCase(paramName)) {
          ruleQty =  str2int(paramValue, errorMsg);
        } else if ("Rate".equalsIgnoreCase(paramName)) {
          freightRate =  str2bd(paramValue, errorMsg);
        } else if ("Item".equalsIgnoreCase(paramName)) {
          int itemId =  str2int(paramValue, errorMsg);
          ruleItems.add(new Integer(itemId));
        } else {
          throw new RemoteException("Unexpected price rule parameter: "+paramName+" Rule id: "+pRuleId);
        }
      }
      //Check parameters
      if(ruleAccountId<=0) {
        throw new RemoteException(
          "No or wrong Account parameter for price rule. Rule Id: "+pRuleId);
      }
      if(ruleQty<0) {
        throw new RemoteException(
          "No or wrong Quantity parameter for price rule. Rule Id: "+pRuleId);
      }

      if(freightRate==null) {
        throw new RemoteException(
          "No Rate parameter for price rule. Rule Id: "+pRuleId);
      }
      //Check account
        if(ruleAccountId!=accountId) {
          return new BigDecimal(0); //Rule does not work
        }
        if(contractFlag==false) {
          return new BigDecimal(0); //Rule does not work
        }
        int[] ruleItemA = new int[ruleItems.size()];
        for(int ii=0; ii<ruleItems.size(); ii++) {
          Integer itemIdI = (Integer) ruleItems.get(ii);
          ruleItemA[ii] = itemIdI.intValue();
        }
        //Check items
        int iQty = 0;
        BigDecimal iPrice = new BigDecimal(0);
        OrderHandlingItemViewVector items = pOrder.getItems();
        mmm:
        for(int ii=0; ii<items.size(); ii++) {
          OrderHandlingItemView item = (OrderHandlingItemView) items.get(ii);
          int itemId = item.getItemId();
          for(int jj=0; jj<ruleItemA.length; jj++) {
             if(itemId==ruleItemA[jj]) {
               int qty = item.getQty();
               iQty += qty;
               if(iQty>=ruleQty) break mmm;
               BigDecimal price = item.getPrice();
               if(price==null) price = new BigDecimal(0);
               iPrice = iPrice.add(price.multiply(new BigDecimal(qty)));
               break;
             }
          }
        }
        if(iQty==0 || iQty>=ruleQty) {
          return new BigDecimal(0); //Do not apply rule if no special items or there are many of them
        }
        padSkuFreight = iPrice.multiply(freightRate);
      return padSkuFreight;
   }
    */
   //*******************************************************************************************
   private int str2int(String pValue, String ErrorMsg)
   throws RemoteException
   {
      int result = 0;
      try {
        result = Integer.parseInt(pValue);
      } catch (Exception exc) {
        throw new RemoteException(ErrorMsg);
      }

      return result;
   }

   private BigDecimal str2bd(String pValue, String ErrorMsg)
   throws RemoteException
   {
      BigDecimal result = null;
      try {
        result = new BigDecimal(pValue);
      } catch (Exception exc) {
        throw new RemoteException(ErrorMsg);
      }

      return result;
   }
 /******************************************************************************/
    /**
     * Prepares collection of ShoppingCartItemDataVector. Uses Jd rules to calculate list and discount price
     *
     * @param pPriceCode         the site price code. Rules, which prices to use
     * @param pSkuNums           the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getJdShoppingItemsBySku(String pPriceCode,
                                                              List pSkuNums,
                                                              ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException {
      return getJdShoppingItemsBySku(pPriceCode, pSkuNums, pShoppingItemRequest, null);

    }
    public ShoppingCartItemDataVector getJdShoppingItemsBySku(String pPriceCode,
                                                              List pSkuNums,
                                                              ShoppingItemRequest pShoppingItemRequest,
                                                              AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException {
        Connection con = null;
        ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();

        pSkuNums = Utility.truncateEmptyValues(pSkuNums);
        if (pSkuNums.size() == 0) {
            return itemList;
        }

        try {
            con = getConnection();

            //Contract/catalog items
            String itemsReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);;

            IdVector itemIds;
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(CatalogStructureDataAccess.CUSTOMER_SKU_NUM, pSkuNums);
            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemsReq);
            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            itemIds = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);
 
            // Filter numeric skus
            ArrayList numericSkus = new ArrayList();
            for (int ctr = 0; ctr < pSkuNums.size(); ctr++) {
                int isku = 0;
                String s = (String) pSkuNums.get(ctr);
                try {
                    isku = Integer.parseInt(s);
                } catch (Exception e) {
                }
                if (isku > 0) {
                    numericSkus.add(s);
                }
            }

            if (numericSkus.size() > 0) {
                dbc = new DBCriteria();
                dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
                dbc.addOneOf(ItemDataAccess.SKU_NUM, numericSkus);
                dbc.addOneOf(ItemDataAccess.ITEM_ID, itemsReq);
                dbc.addOrderBy(ItemDataAccess.ITEM_ID);
                itemIds.addAll(ItemDataAccess.selectIdOnly(con, ItemDataAccess.ITEM_ID, dbc));
            }
            //---- STJ-6114: Performance Improvements - Optimize Pollock 
            log.info("getJdShoppingItemsBySku() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
            ShoppingDAO.filterByProductBundle(con, itemIds, pShoppingItemRequest);
            log.info("getJdShoppingItemsBySku() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
            //----

            //Find overwriting catalog records
            dbc = new DBCriteria();
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIds);
            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            IdVector itemIds1 = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);

            itemList = prepareJdShoppingItems(con, pPriceCode, pShoppingItemRequest.getShoppingCatalogId(), pShoppingItemRequest.getContractId(), itemIds1, pCategToCostCenterView);
            itemList = setJdActualSkus(itemList);
            //Check all skus presence
            String errorNums = "";
            for (int ii = 0; ii < pSkuNums.size(); ii++) {
                String skuNum = (String) pSkuNums.get(ii);
                int jj = 0;
                for (; jj < itemList.size(); jj++) {
                    ShoppingCartItemData sciD = (ShoppingCartItemData) itemList.get(jj);
                    if (skuNum.equalsIgnoreCase(sciD.getActualSkuNum())) {
                        break;
                    }
                }
                if (jj == itemList.size()) {
                    if (errorNums.length() > 0) {
                        errorNums += ", ";
                    }
                    errorNums += skuNum;
                }
            }
            if (errorNums.length() > 0) {
                throw new DataNotFoundException("Error. Can not find sku numbers: " + errorNums);
            }
        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new DataNotFoundException("Exception. " + exc.getMessage());
        } finally {
            closeConnection(con);
        }

        return itemList;

    }
  /******************************************************************************/
    /**
     * Prepares collection of ShoppingCartItemDataVector. Uses Jd rules to calculate list and discount price
     * If product blongs to more than one category, takes the first one
     *
     * @param pPriceCode         the site price code. Rules, which prices to use
    * @param pSkuNums           the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getJdShoppingItemsByMfgSku(String pPriceCode,
                                                                 List pSkuNums,
                                                                 ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException {
      return getJdShoppingItemsByMfgSku( pPriceCode, pSkuNums, pShoppingItemRequest, null) ;

    }
    public ShoppingCartItemDataVector getJdShoppingItemsByMfgSku(String pPriceCode,
                                                                 List pSkuNums,
                                                                 ShoppingItemRequest pShoppingItemRequest,
                                                                 AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException {

        Connection con = null;
        ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();

        pSkuNums = Utility.truncateEmptyValues(pSkuNums);
        if (pSkuNums.size() == 0) {
            return itemList;
        }

        try {

            con = getConnection();

            String itemsReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemsReq);
            dbc.addOneOf(ItemMappingDataAccess.ITEM_NUM, pSkuNums);

            ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con, dbc);

            String errorNums = "";
            for (Object oSkuNum : pSkuNums) {
                String skuNum = (String) oSkuNum;
                int jj = 0;
                for (; jj < itemMappingDV.size(); jj++) {
                    ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(jj);
                    if (skuNum.equalsIgnoreCase(itemMappingD.getItemNum())) {
                        break;
                    }
                }
                if (jj == itemMappingDV.size()) {
                    if (errorNums.length() > 0) {
                        errorNums += ", ";
                    }
                    errorNums += skuNum;
                }
            }

            if (errorNums.length() > 0) {
                throw new DataNotFoundException("Error. Can not find sku numbers: " + errorNums);
            }

            IdVector items = new IdVector();
            for (Object oItemMapping : itemMappingDV) {
                ItemMappingData itemMappingD = (ItemMappingData) oItemMapping;
                items.add(itemMappingD.getItemId());
            }
            //---- STJ-6114: Performance Improvements - Optimize Pollock 
            log.info("getJdShoppingItemsByMfgSku() ===>Optimize Pollock BEGIN: items.size() =" + items.size());
            ShoppingDAO.filterByProductBundle(con, items, pShoppingItemRequest);
            log.info("getJdShoppingItemsByMfgSku() ===>Optimize Pollock END: items.size() =" + items.size());
            //----

            itemList = prepareJdShoppingItems(con, pPriceCode, pShoppingItemRequest.getShoppingCatalogId(), pShoppingItemRequest.getContractId(), items, pCategToCostCenterView);

        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("getJdShoppingItemsByMfgSku(). Exception. " + exc.getMessage());
        } finally {
            closeConnection(con);
        }

        return itemList;
    }
  //*******************************************************************************
  private ShoppingCartItemDataVector  setJdActualSkus(ShoppingCartItemDataVector pItems)
  {
    int size = pItems.size();
    for(int ii=0; ii<size; ii++) {
      ShoppingCartItemData item = (ShoppingCartItemData) pItems.get(ii);
      String custSku = item.getProduct().getCustomerSkuNum();
      if(custSku!=null && custSku.trim().length()>0) {
        item.setActualSkuNum(custSku);
        item.setActualSkuType(ShoppingCartItemData.CATALOG_SKU);
      }
      else {
        item.setActualSkuNum(""+item.getProduct().getSkuNum());
        item.setActualSkuType(ShoppingCartItemData.CLW_SKU);
      }
    }
    return pItems;
  }
  /******************************************************************************/
  /**
   * Prepares collection of ShoppingCartItemDataVector. Uses Jd rules to assign prices
   * If product blongs to more than one category, takes the first one
   * @param pPriceCode the site price code
   * @param pCatalogId  the catalog identificator
   * @param pContractId the contract identificator or 0 if doesn't appliy
   * @param pItems the list of item is or the list of product object
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException Required by EJB 1.0
   */
  public ShoppingCartItemDataVector  prepareJdShoppingItems
      (String pPriceCode,  int pCatalogId,
       int pContractId, List pItems)
    throws RemoteException
  {
    return prepareJdShoppingItems (pPriceCode, pCatalogId, pContractId, pItems, null);

  }
    public ShoppingCartItemDataVector  prepareJdShoppingItems
      (String pPriceCode,  int pCatalogId,
       int pContractId, List pItems, AccCategoryToCostCenterView pCategToCostCenterView)
    throws RemoteException
  {
    Connection con = null;
    ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();
    if(pItems.size()==0) {
      return itemList;
    }
    try {
      con = getConnection();
      itemList = prepareJdShoppingItems(con, pPriceCode, pCatalogId, pContractId, pItems, pCategToCostCenterView );
    }
    catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException("prepareJdShoppingItems() Exception. "+exc.getMessage());
    }
    finally {
      try {
         con.close();
      }
      catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException("prepareJdShoppingItems(). Can't close the connection. "+exc.getMessage());
      }
    }
    itemList = setJdActualSkus(itemList);
    return itemList;
  }
  //*******************************************************************************
  private ShoppingCartItemDataVector  prepareJdShoppingItems
    (Connection pCon, String pPriceCode, int pCatalogId, int pContractId, List pItems, AccCategoryToCostCenterView pCategToCostCenterView)
    throws RemoteException
  {
    ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();
    if(pItems==null || pItems.size()==0){
      return itemList;
    }
    try{
      DBCriteria dbc;
      String contractItemReq = null;
      //prepare Ids
      IdVector idList = new IdVector();
      //Check type
      Object itemO = pItems.get(0);
      ProductDataVector productDV = null;
      if(itemO instanceof Integer){
        productDV = getProducts(pCon,pCatalogId, pItems,pCategToCostCenterView);
      }
      else if(itemO instanceof ProductData) {
        productDV = new ProductDataVector();
        for(int ii=0; ii<pItems.size(); ii++) {
          productDV.add ((ProductData) pItems.get(ii));
        }
      }
      else if(itemO instanceof ShoppingCartItemData) {
        return (ShoppingCartItemDataVector) pItems;
      }
      else {
        throw new RemoteException("prepareShoppingItems() Unknown type of requested elemen: "+itemO.getClass().getName());
      }
      //Pickup contract information
      ContractItemDataVector contractItemDV = new ContractItemDataVector();
      if(pContractId!=0) {
         for(int ii=0; ii<productDV.size(); ii++) {
           ProductData pD = (ProductData) productDV.get(ii);
           idList.add(new Integer(pD.getProductId()));
         }
         dbc = new DBCriteria();
         dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID,pContractId);
         dbc.addOneOf(ContractItemDataAccess.ITEM_ID,idList);
         dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
         contractItemDV = ContractItemDataAccess.select(pCon,dbc);
      }
      for(int ii=0; ii<productDV.size(); ii++) {
        ProductData pD = (ProductData) productDV.get(ii);
        ShoppingCartItemData shoppingCartItemD = new ShoppingCartItemData();
        shoppingCartItemD.setProduct(pD);
        CatalogCategoryDataVector ccDV = pD.getCatalogCategories();
        if(ccDV!=null && ccDV.size()>0) {
          shoppingCartItemD.setCategory((CatalogCategoryData)ccDV.get(0));
        } else {
          shoppingCartItemD.setCategory(null);
        }

        int productId = pD.getProductId();
        double listPrice = 0;
        double discPrice = 0;
        boolean listPriceFlag = false;
        boolean discPriceFlag = false;
if(pPriceCode.indexOf("US")>=0) {
          String valS = pD.getProductAttribute("LIST_US_PRICE");
          try {
            listPrice = Double.parseDouble(valS);
            listPriceFlag = true;
          } catch(Exception exc) {
            String mess ="Wrong LIST_US_PRICE: "+valS+" Sku: "+pD.getSkuNum();
            throw new RemoteException(mess);
          }
          valS = pD.getProductAttribute("FTL_US_PRICE");
          try {
            discPrice = Double.parseDouble(valS);
            discPriceFlag = true;
          } catch(Exception exc) {
            String mess ="Wrong FTL_US_PRICE: "+valS+" Sku: "+pD.getSkuNum();
            throw new RemoteException(mess);
          }
        }
        if(pPriceCode.indexOf("CAS70")>=0) {
          String valS = pD.getProductAttribute("CAS70_LIST_PRICE");
          try {
            listPrice = Double.parseDouble(valS);
            listPriceFlag = true;
          } catch(Exception exc) {
            String mess ="Wrong CAS70_LIST_PRICE: "+valS+" Sku: "+pD.getSkuNum();
            throw new RemoteException(mess);
          }
          valS = pD.getProductAttribute("CAS70_FTL_PRICE");
          try {
            discPrice = Double.parseDouble(valS);
            discPriceFlag = true;
          } catch(Exception exc) {
            String mess ="Wrong CAS70_FTL_PRICE: "+valS+" Sku: "+pD.getSkuNum();
            throw new RemoteException(mess);
          }
        }
        if(pPriceCode.indexOf("CAS80")>=0) {
          String valS = pD.getProductAttribute("CAS80_LIST_PRICE");
          try {
            listPrice = Double.parseDouble(valS);
            listPriceFlag = true;
          } catch(Exception exc) {
            String mess ="Wrong CAS80_LIST_PRICE: "+valS+" Sku: "+pD.getSkuNum();
            throw new RemoteException(mess);
          }
          valS = pD.getProductAttribute("CAS80_FTL_PRICE");
          try {
            discPrice = Double.parseDouble(valS);
            discPriceFlag = true;
          } catch(Exception exc) {
            String mess ="Wrong CAS80_FTL_PRICE: "+valS+" Sku: "+pD.getSkuNum();
            throw new RemoteException(mess);
          }
        }
        if(!listPriceFlag) {
          String mess ="No list price found. Sku: "+pD.getSkuNum()+" Price code: "+pPriceCode;
          throw new RemoteException(mess);
        }
        if(!discPriceFlag) {
          String mess ="No discount price found. Sku: "+pD.getSkuNum()+" Price code: "+pPriceCode;
          throw new RemoteException(mess);
        }
        shoppingCartItemD.setPrice(listPrice);
        if(pContractId==0) {
          shoppingCartItemD.setDiscPrice(discPrice);
        } else {
          int jj=0;
          for(; jj<contractItemDV.size();jj++) {
            ContractItemData ciD = (ContractItemData) contractItemDV.get(jj);
            if(ciD.getItemId()==productId) {
                double price = 0;
                if (ciD.getAmount() != null ) {
                    price = ciD.getAmount().doubleValue();
                }
              shoppingCartItemD.setDiscPrice(price);
              shoppingCartItemD.setContractFlag(true);
              break;
            }
          }
          if(jj==contractItemDV.size()) {
            shoppingCartItemD.setDiscPrice(listPrice);
            shoppingCartItemD.setContractFlag(false);
          }
        }
        itemList.add(shoppingCartItemD);
      }
    }
    catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException("prepareShoppingItems() SQL Exception happened");
    }
    return itemList;
  }
  /******************************************************************************/
  /**
   * Picks up order guide items
   * If product blongs to more than one category, takes the first one (ignores order guide category)
   * @param pPriceCode  the site price code. Rules, which prices to use
   * @param pCatalogId  the catalog identificator
   * @param pContractId  the contract identificator
   * @param pContractOnly boolean flag, which indicates user permition to buy off contact
   * @param pOrderGuideId  the order guide identificator
   * @param pOrder  the order items to be returned (Constants.ORDER_BY_CATEGORY, Constants.ORDER_BY_NAME, etc)
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getJdOrderGuidesItems (String pPriceCode, int pCatalogId, int pContractId, boolean pContractOnly, int pOrderGuideId, int pOrder)
       throws RemoteException
   {
     return getJdOrderGuidesItems (pPriceCode, pCatalogId, pContractId, pContractOnly, pOrderGuideId, pOrder, null);
   }

 public ShoppingCartItemDataVector getJdOrderGuidesItems (String pPriceCode, int pCatalogId, int pContractId, boolean pContractOnly, int pOrderGuideId, int pOrder, AccCategoryToCostCenterView pCategToCostCenterView)
      throws RemoteException
  {
    Connection con = null;
    ShoppingCartItemDataVector itemList = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,pOrderGuideId);
      if(pContractOnly) {
        DBCriteria dbc1 = new DBCriteria();
        dbc1.addEqualTo(ContractItemDataAccess.CONTRACT_ID,pContractId);
        String contractItemsReq = ContractItemDataAccess.getSqlSelectIdOnly(ContractItemDataAccess.ITEM_ID,dbc1);
        dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID,contractItemsReq);
      }

      dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);
      OrderGuideStructureDataVector orderStructureDV = OrderGuideStructureDataAccess.select(con,dbc);

      // get the items from the catalog
          // make sure all items in the contract are in the catalog
          // if not, remove it from list and don't show it

          DBCriteria dbc2 = new DBCriteria();
          dbc2.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
          dbc2.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
          CatalogStructureDataVector catItemDV = CatalogStructureDataAccess.select(con, dbc2);

          for(int i = 0; i<orderStructureDV.size(); i++){
              OrderGuideStructureData og = (OrderGuideStructureData)orderStructureDV.get(i);
              boolean found = false;
              for(int j=0; j<catItemDV.size() && found==false; j++){
                  CatalogStructureData cat = (CatalogStructureData)catItemDV.get(j);
                  if(cat.getItemId() == og.getItemId()){
                      found = true;
                  }
              }
              if(!found){
                  orderStructureDV.remove(i);
              }
          }
      IdVector itemIds = new IdVector();
      if(orderStructureDV.size()==0) {
        return new ShoppingCartItemDataVector();
      }
      for(int ii=0; ii<orderStructureDV.size(); ii++) {
        OrderGuideStructureData ogsD = (OrderGuideStructureData) orderStructureDV.get(ii);
        itemIds.add(new Integer(ogsD.getItemId()));
      }

      //Prepare Shopping cart items
      itemList = prepareJdShoppingItems(con,pPriceCode,pCatalogId,pContractId,itemIds, pCategToCostCenterView);

      //Assign categries and quantities
      for(int ii=0; ii<itemList.size(); ii++) {
        OrderGuideStructureData ogsD = (OrderGuideStructureData) orderStructureDV.get(ii);
        int qty = ogsD.getQuantity();
        ShoppingCartItemData sciD = (ShoppingCartItemData) itemList.get(ii);
        sciD.setQuantity(qty);
        CatalogCategoryDataVector ccDV = sciD.getProduct().getCatalogCategories();
        if(ccDV.size()==0) {
          sciD.setCategory(null);
        }
        else {
            CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
            sciD.setCategory(ccD);
        }
      }
    }
    catch (Exception exc) {
      exc.printStackTrace();
      throw new RemoteException("getJdOrderGuidesItems(). Exception. "+exc.getMessage());
    }
    finally {
      try {
         con.close();
      }
      catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("getJdOrderGuidesItems(). Can't close the connection. "+exc.getMessage());
      }
    }
    itemList = setJdActualSkus(itemList);
    //Order items
    itemList = sortShoppingCardItems(pOrder, itemList);
    return itemList;
  }
   //******************************************************************************************

public ShoppingCartItemDataVector sortShoppingCardItems(int pOrder,
		ShoppingCartItemDataVector itemList) throws RemoteException {
	switch(pOrder) {
      case Constants.ORDER_BY_CATEGORY:
        itemList = orderByCategoryName(itemList);
        break;
      case Constants.ORDER_BY_CUSTOM_CATEGORY:
        itemList = orderByCustomCategory(itemList);
        break;
      case Constants.ORDER_BY_CUST_SKU:
        itemList = orderBySkuName(itemList);
        break;
      case Constants.ORDER_BY_NAME:
        itemList = orderByNameSku(itemList);
        break;
      case Constants.ORDER_BY_MFG_NAME:
        itemList = orderByMfgName(itemList);
        break;
      case Constants.ORDER_BY_MFG_SKU:
        itemList = orderByMfgSku(itemList);
        break;
      case Constants.ORDER_BY_CATEGORY_AND_SKU:
    	  itemList = orderByCategoryAndSku(itemList);
    	  break;
    }
	return itemList;
}

  /**
   * Picks up last order items
   * @param pPriceCode  the site price code. Rules, which prices to use
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return collection of ShoppingCartItemDataVector
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getJdLastOrderItems
      (String pPriceCode, int pCatalogId, OrderData pLastOrder )
     throws RemoteException
  {
    return getJdLastOrderItems( pPriceCode, pCatalogId, pLastOrder, null );
  }
   public ShoppingCartItemDataVector getJdLastOrderItems
       (String pPriceCode, int pCatalogId, OrderData pLastOrder, AccCategoryToCostCenterView pCategToCostCenterView )
      throws RemoteException
   {
    Connection con = null;
    ShoppingCartItemDataVector shoppingCartItemDV =
  new ShoppingCartItemDataVector();
    try {
      DBCriteria dbc;
      con = getConnection();

      dbc = new DBCriteria();
      dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,
         pLastOrder.getOrderId());
      dbc.addOrderBy(OrderItemDataAccess.ITEM_ID);

      OrderItemDataVector orderItemDV = OrderItemDataAccess.select(con,dbc);
      IdVector itemIds = new IdVector();
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        itemIds.add(new Integer(oiD.getItemId()));
      }

      //Create shopping cart items
      shoppingCartItemDV = prepareJdShoppingItems
    (con,pPriceCode, pCatalogId, pLastOrder.getContractId(),itemIds, pCategToCostCenterView);
      logDebug ( "got cart items: " + shoppingCartItemDV );

      for(int ii=0, jj=0; ii<shoppingCartItemDV.size(); ii++) {
        ShoppingCartItemData sciD = (ShoppingCartItemData)
      shoppingCartItemDV.get(ii);
        int id = sciD.getProduct().getProductId();
        ItemShoppingHistoryData historyD = new ItemShoppingHistoryData();
        while(jj<orderItemDV.size()) {
          OrderItemData oiD = (OrderItemData) orderItemDV.get(jj);
          if(oiD.getItemId() < id) {
            jj++;
            continue;
          } else if(oiD.getItemId()==id) {
            Date addDate = oiD.getAddDate();
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(addDate);
            GregorianCalendar cal1 =
               new GregorianCalendar(cal.get(GregorianCalendar.YEAR),cal.get(GregorianCalendar.MONTH),cal.get(GregorianCalendar.DAY_OF_MONTH));
            historyD.setLastDate(cal1.getTime());
            historyD.setLastQty(oiD.getTotalQuantityOrdered());
            sciD.setQuantity(oiD.getTotalQuantityOrdered());
            historyD.setLastPrice(oiD.getCustContractPrice().doubleValue());

            jj++;
            continue;
          } else {
            break;
          }
        }
        sciD.setShoppingHistory(historyD);
      }
    }
    catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException("getJdLastOrderItems(). Exception. "+exc.getMessage());
    }
    finally {
      try {
         con.close();
      }
      catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("getJdLastOrderItems(). Can't close the connection. "+exc.getMessage());
      }
    }
    shoppingCartItemDV = setJdActualSkus(shoppingCartItemDV);
    return shoppingCartItemDV;
   }

    public java.util.List updateShoppingInfo(int pSiteId,
                                             int pOrderGuideId,
                                             java.util.List pShoppingInfoVector) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return updateShoppingInfo(con,pSiteId,pOrderGuideId,pShoppingInfoVector );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    private java.util.List updateShoppingInfo( Connection con,int pSiteId,int pOrderGuideId,
                                    java.util.List pShoppingInfoVector )
        throws RemoteException
    {
        try {

            for (int i = 0; null != pShoppingInfoVector && i < pShoppingInfoVector.size(); i++) {
                ShoppingInfoData sid = (ShoppingInfoData) pShoppingInfoVector.get(i);
                if (pOrderGuideId == sid.getOrderGuideId()){
                    sid.setShoppingInfoStatusCd(RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE);
                    ShoppingInfoDataAccess.insert(con, sid);
                }
            }

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ShoppingInfoDataAccess.SITE_ID,pSiteId);
            dbc.addEqualTo(ShoppingInfoDataAccess.ORDER_GUIDE_ID,pOrderGuideId);

            // Only get the history for those items which have
            // not been purchased.
            dbc.addIsNull(ShoppingInfoDataAccess.ORDER_ID);
            String statusCond = "NVL("+ShoppingInfoDataAccess.SHOPPING_INFO_STATUS_CD+"," +
                    "'"+RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE+"')="+
                    "'"+RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE+"'";
            dbc.addCondition(statusCond);
            //dbc.addEqualTo(ShoppingInfoDataAccess.SHOPPING_INFO_STATUS_CD,
            //        RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE);
            dbc.addOrderBy(ShoppingInfoDataAccess.ITEM_ID);
            dbc.addOrderBy(ShoppingInfoDataAccess.SHOPPING_INFO_ID,false);
            return (List)ShoppingInfoDataAccess.select(con, dbc);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

  /******************************************************************************/
  /**
   * Picks up order guide items
   * If product blongs to more than one category, takes the first one (ignores order guide category)
   * @param pStoreTypeCd  the store type. Rules, which sku number is used
   * @param pOrderGuideId  the order guide identificator
   * @param pOrder  the order items to be returned (Constants.ORDER_BY_CATEGORY, Constants.ORDER_BY_NAME, etc)
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getOrderGuideItems(String pStoreTypeCd,
                                                        SiteData pSiteData,
                                                        int pOrderGuideId,
                                                        ShoppingItemRequest pShoppingItemRequest,
                                                        int pOrder) throws RemoteException {
    return getOrderGuideItems( pStoreTypeCd, pSiteData, pOrderGuideId, pShoppingItemRequest,pOrder, null);

  }

 public ShoppingCartItemDataVector getOrderGuideItems(String pStoreTypeCd,
                                                       SiteData pSiteData,
                                                       int pOrderGuideId,
                                                       ShoppingItemRequest pShoppingItemRequest,
                                                       int pOrder,
                                                       AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
	 Connection con = null;
	 ShoppingCartItemDataVector shopCartItemDV = new ShoppingCartItemDataVector();
	 Map shoppingCardItemMap = new HashMap();
	 InventoryLevelDataVector currentInventory = null;

	 try {
		 con = getConnection();
		 String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

		//---- STJ-6114: Performance Improvements - Optimize Pollock 
		if (Utility.isSet(pShoppingItemRequest.getProductBundle())){
    		ShoppingDAO.addProductBundleFilter(con, pShoppingItemRequest);
    	}	
 		IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
 		logInfo("getOrderGuideItems()=> excludeItemIds: "+ ((excludeItemIds!= null) ? excludeItemIds : "0"));
		IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
		logInfo("getOrderGuideItems()=> includeItemIds: "+ ((includeItemIds!= null) ? includeItemIds : "0"));
		//---------
		
    	String sql = "select ci.item_id, ogs.cust_category, ogs.sort_order, ci.dist_cost,  ci.amount, ogs.quantity " +
		 "from clw_contract_item ci, clw_order_guide_structure ogs " +
		 "where ci.contract_id = " + pShoppingItemRequest.getContractId() + " " +
		 "and ogs.order_guide_id = " + pOrderGuideId + " " +
		 "and ci.item_id = ogs.item_id " +
		 "and ogs.item_id in ("+itemReq+")"+   //requirement
		 "order by ci.item_id";

		 logInfo("getOrderGuideItems()=> sql: "+sql);
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(sql);
		 int itemIdPrev = -1;
		 CatalogCategoryDataVector categDV = null;
		 IdVector itemIdV = new IdVector();
		 ProductDataVector  productDV = new ProductDataVector();
//YK beg
		int exclItemId = 0;
		int inclItemId = 0;
		Iterator iterExcl = null;
		if(excludeItemIds!=null) iterExcl = excludeItemIds.iterator();
		Iterator iterIncl = null;
		if(includeItemIds!=null) iterIncl = includeItemIds.iterator();
//YK End		 
		 
		 while (rs.next()){
			int itemId = rs.getInt("item_id");
			
	    	//---- STJ-6114: Performance Improvements - Optimize Pollock 
//logInfo("getOrderGuideItems()=> trying item" + itemId );
			while(iterExcl!=null && iterExcl.hasNext()) {
				if(exclItemId < itemId) {
					exclItemId = (Integer) iterExcl.next();
//logInfo("getOrderGuideItems()=> exclude item" + exclItemId );
				} else {
					break;
				}
			}
			if(itemId == exclItemId) {
	 			logInfo("getOrderGuideItems()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping proprietary itemId = " + itemId );
				continue;
			}
			if(iterIncl!=null) {
				while(iterIncl.hasNext()) {
					if(inclItemId < itemId) {
						inclItemId = (Integer) iterIncl.next();				
//logInfo("getOrderGuideItems()=> must item" + inclItemId );
					} else {
						break;
					}
				}
				if(itemId != inclItemId) {
					logInfo("getOrderGuideItems()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping not price list itemId = " + itemId );
					continue;
				}
			}
			/*
	    	if (Utility.isSet(pShoppingItemRequest.getProductBundle())){
	    		Integer id = new Integer(itemId);
	 			if (excludeItemIds!= null &&  excludeItemIds.contains(id) ||
	 				includeItemIds!= null && !includeItemIds.contains(id) ){
	 				logInfo("getOrderGuideItems()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping itemId = " + itemId );
	 		        continue;
	 			}
	    	} 
			*/
	    	//---------
//logInfo("getOrderGuideItems()=> item accepted" + itemId );
	    	
	    	if(itemId==itemIdPrev) {
				 throw new Exception("Duplication in order_guide or/and contract. Item id: "+itemId +
						 " Order guide id: "+pOrderGuideId +
						 " Contract id: "+pShoppingItemRequest.getContractId());
			 }
			 itemIdPrev = itemId;
			 itemIdV.add(new Integer(itemId));
			 double price = rs.getDouble("amount");
			 double cost = rs.getDouble("dist_cost");
			 int qty = rs.getInt("quantity");
			 int customSortOrder = rs.getInt("sort_order");
			 String customCategoryName = rs.getString("cust_category");
			 ShoppingCartItemData sciD = new ShoppingCartItemData();

			 sciD.setQuantity(qty);
			 if(qty>0) {
				 sciD.setQuantityString(""+qty);
			 } else {
				 sciD.setQuantityString("");
			 }
			 sciD.setDiscPrice(cost);
			 sciD.setPrice(price);
			 sciD.setIsaInventoryItem(false);
			 sciD.setCustomCategoryName(customCategoryName);
			 sciD.setCustomSortOrder(customSortOrder);

			 shopCartItemDV.add(sciD);
			 shoppingCardItemMap.put(itemId, sciD);
		 }
		 rs.close();
		 stmt.close();

		 // get catalog path
		 ProductDAO tpdao = new ProductDAO(con, itemIdV);
		 tpdao.updateCatalogInfo(con, pShoppingItemRequest.getShoppingCatalogId(),pSiteData.getSiteId(), pCategToCostCenterView);
		 ProductDataVector prDV = tpdao.getResultVector();
		 Iterator i = prDV.iterator();
		 while (i.hasNext()) {
			 ProductData pD = (ProductData)i.next();
			 ShoppingCartItemData sciD = (ShoppingCartItemData) shoppingCardItemMap.get(pD.getItemData().getItemId());
			 sciD.setProduct(pD);

			 if(pD.getCatalogCategories().size()==0) {
				 sciD.setCategory(null);
			 }
			 else {
				 sciD.setCategory((CatalogCategoryData)pD.getCatalogCategories().get(0));
			 }
			 pD.setCustomerSkuNum(pD.getCatalogStructure().getCustomerSkuNum());
			 pD.setCustomerProductShortDesc(pD.getCatalogStructure().getShortDesc());

			 String custSkuNum = pD.getActualCustomerSkuNum();
			 if(custSkuNum!=null && custSkuNum.trim().length()>0) {
				 sciD.setActualSkuNum(custSkuNum);
				 sciD.setActualSkuType(ShoppingCartItemData.CATALOG_SKU);
			 } else {
				 sciD.setActualSkuNum(""+pD.getSkuNum());
				 sciD.setActualSkuType(ShoppingCartItemData.CLW_SKU);
			 }
		 }


		 if (pSiteData.isUseProductBundle()) {
			 shopCartItemDV = prepareShoppingItemsNew(con,
					 pStoreTypeCd,
					 pShoppingItemRequest.getShoppingCatalogId(),
					 pShoppingItemRequest.getContractId(),
					 shopCartItemDV,
					 pShoppingItemRequest.getSiteId(),
                                         pCategToCostCenterView);
		 }

		 // Set the inventory info for each item.
		 if (pSiteData.hasInventoryShoppingOn() == true ) {
			 // Add any inventory items.
			 DBCriteria dbcii = new DBCriteria();
			 dbcii.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID,
					 pSiteData.getSiteId());
			 dbcii.addIsNotNull(InventoryLevelDataAccess.QTY_ON_HAND);
			 currentInventory = InventoryLevelDataAccess.select(con,dbcii);
			 if (pSiteData.hasModernInventoryShopping()) {
				 shopCartItemDV = setupItemInventoryInfo(con, pSiteData, shopCartItemDV,pCategToCostCenterView);
			 } else {
				 shopCartItemDV = setupItemInventoryInfo(con,pSiteData.getAccountId(),shopCartItemDV,
						 pSiteData.getSiteInventory(),
						 currentInventory,pCategToCostCenterView);
			 }
		 }
		 shopCartItemDV = setupMaxOrderQtyValues(pSiteData, shopCartItemDV);

		 shopCartItemDV = ShoppingDAO.setActualSkus(pStoreTypeCd, shopCartItemDV);
		 //Order items

		 shopCartItemDV = sortShoppingCardItems(pOrder, shopCartItemDV);
		 return shopCartItemDV;

	 } catch (Exception exc) {
		 logError(exc.getMessage());
		 exc.printStackTrace();
		 throw new RemoteException(exc.getMessage());
	 } finally {
		 closeConnection(con);
	 }
 }
 
 public int getOrderGuideItemCount(int pOrderGuideId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
	 int count = 0;
	 Connection con = null; 

	 try {
		 con = getConnection();
		 String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

		 if (Utility.isSet(pShoppingItemRequest.getProductBundle())){
			 ShoppingDAO.addProductBundleFilter(con, pShoppingItemRequest);
		 }	
		 IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
		 logInfo("getOrderGuideItems()=> excludeItemIds: "+ ((excludeItemIds!= null) ? excludeItemIds : "0"));
		 IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
		 logInfo("getOrderGuideItems()=> includeItemIds: "+ ((includeItemIds!= null) ? includeItemIds : "0"));

		 String sql = "select count(ogs.item_id) " +
		 "from clw_order_guide_structure ogs " +
		 "where ogs.order_guide_id = " + pOrderGuideId + " " +
		 "and ogs.item_id in ("+itemReq+")";  //requirement

		 logInfo("getOrderGuideItemCount()=> sql: "+sql);
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(sql); 			 

		 if (rs.next()){
			 count = rs.getInt(1);
		 }
		 return count;	 
	 } catch (Exception exc) {
		 logError(exc.getMessage());
		 exc.printStackTrace();
		 throw new RemoteException(exc.getMessage());
	 } finally {
		 closeConnection(con);
	 }
 }

    private CacheManager mCacheManager =
     new CacheManager("ShoppingServicesBean");

    public ShoppingCartItemDataVector getItemControlInfoForAccount
    (int pAccountId, int pCatalogId) throws RemoteException {
    	return getItemControlInfoForAccount(pAccountId, pCatalogId, null);
    }
    
    public ShoppingCartItemDataVector getItemControlInfoForAccount
            (int pAccountId, int pCatalogId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

        Connection con = null;
        ShoppingCartItemDataVector itemList = null;

        logDebug("getItemControlInfo, pAccountId=" + pAccountId);
        try {
            con = getConnection();
            BusEntityDAO bdao = mCacheManager.getBusEntityDAO();
            int storeId = bdao.getStoreForAccount(con, pAccountId);
            String storeTypeCd = bdao.getStoreTypeCd(con, storeId);

            // get all items in the account catalog.
            ProductDAO pdao = mCacheManager.getProductDAO();
            IdVector productsAvailable = pdao.allCatalogItemIds(con, pCatalogId);
            logDebug("productsAvailable.size="+productsAvailable.size()
            + " for pAccountId="+pAccountId
                    + " pCatalogId="+pCatalogId);

            itemList = prepareShoppingItems
                    (con,pCatalogId,
                    0,productsAvailable, pCategToCostCenterView);

            logDebug("getItemControlInfoForAccount itemList.size="+itemList.size());

        }catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
        return itemList;
    }

 public ShoppingCartItemDataVector getItemControlInfo
        (String pStoreTypeCd, SiteData pSiteData)
         throws RemoteException
     {
       return getItemControlInfo
        ( pStoreTypeCd,  pSiteData, null);
     }

 public ShoppingCartItemDataVector getItemControlInfo
     (String pStoreTypeCd, SiteData pSiteData, AccCategoryToCostCenterView pCategToCostCenterView)
      throws RemoteException
  {
    Connection con = null;
    ShoppingCartItemDataVector itemList = null;

    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();

      IdVector itemIds = new IdVector();
      ShoppingControlDataVector scdv = pSiteData.getShoppingControls();
      logDebug("getItemControlInfo, pStoreTypeCd=" + pStoreTypeCd +
         " pSiteData=" + pSiteData.getBusEntity() +
         " for accountid=" + pSiteData.getAccountId() +
         " has " + scdv.size() + " items in shopping control."
       );
      final Set<Integer> buffer = new HashSet<Integer>();
      for(int ii=0; ii<scdv.size(); ii++) {
    ShoppingControlData siteItemControl =
        (ShoppingControlData)scdv.get(ii);
//    itemIds.add(new Integer(siteItemControl.getItemId()));
        buffer.add(siteItemControl.getItemId());
      }
      itemIds.addAll(buffer);
      logDebug("Final itemIds size:" + itemIds.size());
        //Prepare Shopping cart items
        if (pSiteData.isUseProductBundle()) {
            itemList = prepareShoppingItemsNew(con,
                    pStoreTypeCd,
                    pSiteData.getSiteCatalogId(),
                    pSiteData.getContractData().getContractId(),
                    itemIds,
                    pSiteData.getSiteId(),
                    pCategToCostCenterView);
        } else {
            itemList = prepareShoppingItems(con,
                    pSiteData.getSiteCatalogId(),
                    pSiteData.getContractData().getContractId(),
                    itemIds,
                    pSiteData.getSiteId(), pCategToCostCenterView);
        }
    }catch (Exception exc) {
        throw processException(exc);
    }
    finally {
      closeConnection(con);
    }

    try{
        itemList = ShoppingDAO.setActualSkus(pStoreTypeCd, itemList);
  logDebug("getItemControlInfo, itemList.size=" + itemList.size());
        return itemList;
    }catch (Exception exc) {
        throw processException(exc);
    }
  }

    public AddressDataVector getUserBillTos(int pUserId,int pSiteId)
  throws RemoteException {

  String sql = " select max(order_address_id) "
      + " from clw_order_address oa, clw_order o, "
      + " clw_pre_order preo where  "
      + "  preo.user_id = " + pUserId
      + "  and preo.site_id = " + pSiteId
      + "  and o.pre_order_id = preo.pre_order_id   "
      + "  and oa.address_type_cd = '"
      + RefCodeNames.ADDRESS_TYPE_CD.BILLING + "' "
      + "  and length(address1) > 0 "
      + "  and oa.order_id = o.order_id  "
      + "  order by o.order_id desc" ;

  logDebug("SQL: " + sql);

  Connection con = null;
  AddressDataVector adv = new AddressDataVector();

  try {

      con = getConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      IdVector addrIdV = new IdVector();
      while (rs.next()){
    if ( rs.getInt(1) > 0 ) {
        addrIdV.add(new Integer(rs.getInt(1)));
        logDebug("000 added " + rs.getInt(1));
    }
      }
      rs.close();
      if ( addrIdV != null && addrIdV.size() > 0 ) {
    OrderAddressDataVector oadv =
        OrderAddressDataAccess.select(con, addrIdV);

    Iterator it = oadv.iterator();
    while ( null != it && it.hasNext() ) {
        adv.add(Utility.toAddress((OrderAddressData)it.next()));
    }
      }

      if ( addrIdV == null || addrIdV.size() == 0 ) {
    // Find bill to addresses previously used
    // by this user for this site.
    sql = "select max(preo_ad.pre_order_address_id) "
        + " from clw_pre_order_address preo_ad, "
        + " clw_pre_order preo where "
        + "  preo.user_id = " + pUserId
        + "  and preo.site_id = " + pSiteId
        + "  and preo.pre_order_id = preo_ad.pre_order_id "
        + "  and preo_ad.address_type_cd = '"
        + RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING + "' "
        + " and length(address1) > 0 "
        + " order by preo.pre_order_id desc";
    logDebug("SQL: " + sql);
    ResultSet rs2 = stmt.executeQuery(sql);
    addrIdV = new IdVector();
    while (rs2.next()){
        if ( rs2.getInt(1) > 0 ) {
      addrIdV.add(new Integer(rs2.getInt(1)));
      logDebug("000111 added " + rs2.getInt(1));
        }
    }
    rs2.close();

    PreOrderAddressDataVector poadv =
        PreOrderAddressDataAccess.select(con, addrIdV);

    Iterator it = poadv.iterator();
    while ( null != it && it.hasNext() ) {
        adv.add(Utility.toAddress((PreOrderAddressData)it.next()));
    }
      }

      if ( addrIdV == null || addrIdV.size() == 0 ) {
    // Find bill to addresses previously
    // by this user.
    sql = "select max(preo_ad.pre_order_address_id) "
        + " from clw_pre_order_address preo_ad, "
        + " clw_pre_order preo where "
        + "  preo.user_id = " + pUserId
        + "  and preo.pre_order_id = preo_ad.pre_order_id "
        + "  and preo_ad.address_type_cd = '"
        + RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING + "' "
        + " and length(address1) > 0 "
        + " order by preo.pre_order_id desc";

    logDebug("SQL: " + sql);
    ResultSet rs3 = stmt.executeQuery(sql);
    addrIdV = new IdVector();
    while (rs3.next()){
        if ( rs3.getInt(1) > 0 ) {
      addrIdV.add(new Integer(rs3.getInt(1)));
      logDebug("000111222 added " + rs3.getInt(1));
        }
    }
    rs3.close();

    PreOrderAddressDataVector poadv =
        PreOrderAddressDataAccess.select(con, addrIdV);

    Iterator it = poadv.iterator();
    while ( null != it && it.hasNext() ) {
        adv.add(Utility.toAddress((PreOrderAddressData)it.next()));
    }
      }

      stmt.close();
      return adv;

  }
  catch (Exception e) {
      e.printStackTrace();
      logError("ERROR, getUserBillTos( pUserId=" + pUserId
         + " pSiteId=" + pSiteId + " )"
         );
  }
  finally {
      closeConnection(con);
  }

  return null;
    }


 public ShoppingInfoDataVector getOrderShoppingInfo(int pOrderId)
      throws RemoteException
  {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ShoppingInfoDataAccess.ORDER_ID,pOrderId);
      dbc.addOrderBy(ShoppingInfoDataAccess.ADD_DATE,false);
      ShoppingInfoDataVector siDV = ShoppingInfoDataAccess.select(con,dbc);
      return siDV;
    }catch (Exception exc) {
        throw processException(exc);
    }
    finally {
      closeConnection(con);
    }
 }

 /******************************************************************************/
    /**
     * Picks up all order guide items, which match criteria. Returns empty collection if all filter parameters are empty.
     * Ignores filter parameter if it is empty. Applies contains ignore case match type
     *
     * @param pStoreTypeCd        the store type. Rules, which sku number is used
     * @param pOrderGuideV        the order guide identificators
     * @param pCustSku            - custormer sku number filter. Applies catalog sku number if exsits, otherwise applies item sku number
     * @param pMfgSku             - manufacturer sku number filter
     * @param pName               - item short description filter. Applies catalog short description if exsits, otherwise applies item short description
     * @param pDesc               - item long description filter
     * @param pCategory           - item lowest level category filter
     * @param pSize               - item size property filter
     * @param pMfgId              - manufacturer Id filter. Unlike other fiters it demands exact equal match type
     * @param pGreenCertifiedFlag item certified filter
     * @param pShoppingItemRequest spec criteria
     * @return collection of item ids sorted by short description
     * @throws java.rmi.RemoteException if an errors
     */
    public IdVector searchOrderGuideItems(String pStoreTypeCd,
                                          IdVector pOrderGuideV,
                                          String pCustSku,
                                          String pMfgSku,
                                          String pName,
                                          String pDesc,
                                          String pCategory,
                                          String pSize,
                                          int pMfgId,
                                          boolean pGreenCertifiedFlag,
                                          ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        Connection con = null;
        IdVector itemIdV = null;

        try {

            con = getConnection();

            itemIdV = searchOrderGuideItems(con,
                    pStoreTypeCd,
                    pOrderGuideV,
                    pCustSku,
                    pMfgSku,
                    pName,
                    pDesc,
                    pCategory,
                    pSize,
                    pMfgId,
                    pGreenCertifiedFlag,
                    pShoppingItemRequest);

        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchOrderGuideItems() Naming Exception happened");
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("searchOrderGuideItems() SQL Exception happened");
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException("searchOrderGuideItems() Exception happened");
        } finally {
            closeConnection(con);
        }

        return itemIdV;

    }



 //****************************************************************************
    private IdVector searchOrderGuideItems(Connection con,
                                           String pStoreTypeCd,
                                           IdVector pOrderGuideV,
                                           String pCustSku,
                                           String pMfgSku,
                                           String pName,
                                           String pDesc,
                                           String pCategory,
                                           String pSize,
                                           int pMfgId,
                                           boolean pGreenCertifiedFlag,
                                           ShoppingItemRequest pShoppingItemRequest) throws RemoteException, SQLException {
    IdVector itemIdV = new IdVector();

    int storeType = 0;
    if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(pStoreTypeCd)) {
      storeType = 1;
    }

    DBCriteria dbcItem = new DBCriteria();
    DBCriteria dbc = new DBCriteria();

 // items only from orger guide
    IdVector items = getOrderGuidesItemVector(pOrderGuideV, pShoppingItemRequest);

    dbcItem.addOneOf(ItemDataAccess.ITEM_ID, items);
    dbc.addOneOf(ItemDataAccess.ITEM_ID, items);

    //Description
    if (pDesc != null && pDesc.trim().length() > 0) {
      dbcItem.addLikeIgnoreCase(ItemDataAccess.LONG_DESC, "%" + pDesc + "%");
    }
    //Manufacturer Mapping
    if (pMfgId != 0 || (pMfgSku != null && pMfgSku.trim().length() > 0)) {
      dbc = new DBCriteria();
      if (pMfgId != 0) {
        dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pMfgId);
      }
      if (pMfgSku != null && pMfgSku.trim().length() > 0) {
        dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM, "%" + pMfgSku.toUpperCase() + "%");
      }
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      String mappingReq = ItemMappingDataAccess.getSqlSelectIdOnly(ItemMappingDataAccess.ITEM_ID, dbc);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID, mappingReq);
    }
    //Category
    if (pCategory != null && pCategory.trim().length() > 0 ) {
      dbc = new DBCriteria();
      if (pCategory != null && pCategory.trim().length() > 0) {
        dbc.addLikeIgnoreCase(ItemDataAccess.SHORT_DESC, "%" + pCategory.toUpperCase() + "%");
      }
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      String categoryReq = ItemDataAccess.getSqlSelectIdOnly(ItemDataAccess.ITEM_ID, dbc);
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
      dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID, categoryReq);
      String itemCategoryReq = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM1_ID, dbc);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryReq);
    }
    //Size
    if (pSize != null && pSize.trim().length() > 0) {
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, items);
      dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, ProductData.SIZE);
      dbc.addLikeIgnoreCase(ItemMetaDataAccess.CLW_VALUE, "%" + pSize.toUpperCase() + "%");
      String itemSizeReq = ItemMetaDataAccess.getSqlSelectIdOnly(ItemMetaDataAccess.ITEM_ID, dbc);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID, itemSizeReq);
    }
    //Item Name or Sku Number
    boolean nameFlag = (pName != null && pName.trim().length() > 0);
    boolean skuFlag = (pCustSku != null && pCustSku.trim().length() > 0);
    if (nameFlag || skuFlag) {
      //Item  Name (ShortDesc)
      IdVector itemSkuNameIds = null;
      IdVector itemNameIds = null;
      if (nameFlag) {
        itemNameIds = new IdVector();
        dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, items);
        dbc.addLikeIgnoreCase(ItemDataAccess.SHORT_DESC, "%" + pName + "%");
        dbc.addOrderBy(ItemDataAccess.ITEM_ID);
        IdVector itemIds = ItemDataAccess.selectIdOnly(con, ItemDataAccess.ITEM_ID, dbc);
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIds);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con, dbc);
        int size = itemIds.size();
        if (size != csDV.size()) {
            throw new RemoteException("No consitency in Item - Catalog Structure for name substring: " + pName + "." +
                    " Catalog Id: " + pShoppingItemRequest.getShoppingCatalogId());
        }
        for (int ii = 0; ii < size; ii++) {
          Integer idI = (Integer) itemIds.get(ii);
          int id = idI.intValue();
          CatalogStructureData csD = (CatalogStructureData) csDV.get(ii);
          if (csD.getItemId() != id) {
              throw new RemoteException("No consitency in Item - Catalog Structure for name substring: " + pName + "." +
                      " Catalog Id: " + pShoppingItemRequest.getShoppingCatalogId() + "." +
                      " Clw_item item id: " + id + "." +
                      " Clw_catalog_structure item id: " + csD.getItemId());
          }
          String name = csD.getShortDesc();
          if (name == null || name.trim().length() == 0) {
            itemNameIds.add(idI);
          }
        }
      }
      //Item Sku Number
      IdVector itemSkuIds = null;
      String itemsWithSku = null;
      IdVector priceListItemIds = new IdVector();

      if (skuFlag) {

        itemSkuIds = new IdVector();
        IdVector itemIds = null;

        String productBundle = ShoppingDAO.getProductBundleValue(con, pShoppingItemRequest.getSiteId());

          if (Utility.isSet(productBundle)) {

              IdVector priceListIds = Utility.toIdVector(
                      pShoppingItemRequest.getPriceListRank1Id(),
                      pShoppingItemRequest.getPriceListRank2Id()
              );

              // try to find in a price lists
              DBCriteria pldDbc = new DBCriteria();
              pldDbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
              pldDbc.addOneOf(PriceListDetailDataAccess.ITEM_ID, items);
              pldDbc.addLikeIgnoreCase(PriceListDetailDataAccess.CUSTOMER_SKU_NUM, "%" + pCustSku + "%");

              priceListItemIds = PriceListDetailDataAccess.selectIdOnly(con, PriceListDetailDataAccess.ITEM_ID, pldDbc);

              pldDbc = new DBCriteria();
              pldDbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListIds);
              pldDbc.addIsNotNull("TRIM(" + PriceListDetailDataAccess.CUSTOMER_SKU_NUM + ")");

              itemsWithSku = PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, pldDbc);

          }

        if (storeType == 0) {
          //MLA or Other store
          dbc = new DBCriteria();
          dbc.addOneOf(ItemDataAccess.ITEM_ID, items);
          dbc.addLikeIgnoreCase(ItemDataAccess.SKU_NUM, "%" + pCustSku + "%");
          dbc.addOrderBy(ItemDataAccess.ITEM_ID);
          itemIds = ItemDataAccess.selectIdOnly(con, ItemDataAccess.ITEM_ID, dbc);
        }
        if (storeType == 1) {
          //Distributor store

            if (Utility.isSet(productBundle)) {

                dbc.addJoinTableEqualTo(ItemMappingDataAccess.CLW_ITEM_MAPPING,ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
                dbc.addJoinTableOneOf(ItemMappingDataAccess.CLW_ITEM_MAPPING,ItemMappingDataAccess.ITEM_ID, items);
                dbc.addJoinTableLikeIgnoreCase(ItemMappingDataAccess.CLW_ITEM_MAPPING,ItemMappingDataAccess.ITEM_NUM, "%" + pCustSku + "%");
                dbc.addJoinCondition(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID, CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID);
                dbc.addJoinTableIsNullOrSpace(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CUSTOMER_SKU_NUM);
                dbc.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
                dbc.addJoinTableOrderBy(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID);
                if (itemsWithSku != null) {
                    dbc.addJoinTableNotOneOf(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID, itemsWithSku);
                }

                itemIds = JoinDataAccess.selectIdOnly(con, ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID, dbc, 0);

            } else {
                dbc = new DBCriteria();
                dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
                dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, items);
                dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM, "%" + pCustSku + "%");
                String distrCondition =
                        "item_id = (select item_id from CLW_CATALOG_STRUCTURE where item_id=CLW_ITEM_MAPPING.item_id and catalog_id = " +
                                pShoppingItemRequest.getShoppingCatalogId() + ")";
                dbc.addCondition(distrCondition);
                dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
                itemIds = ItemMappingDataAccess.selectIdOnly(con, ItemMappingDataAccess.ITEM_ID, dbc);

            }
        }
        if (storeType == 2) {
          //Manufacturer store
          dbc = new DBCriteria();
          dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
          dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, items);
          dbc.addLikeIgnoreCase(ItemMappingDataAccess.ITEM_NUM, "%" + pCustSku + "%");
          dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
          itemIds = ItemMappingDataAccess.selectIdOnly(con, ItemMappingDataAccess.ITEM_ID, dbc);
        }

        dbc = new DBCriteria();
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIds);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con, dbc);
        int size = itemIds.size();
        if (size != csDV.size()) {
            throw new RemoteException("No consitency in Item - Catalog Structure for sku number substring: " + pCustSku + "." +
                    " Catalog Id: " + pShoppingItemRequest.getShoppingCatalogId());
        }
        for (int ii = 0; ii < size; ii++) {
          Integer idI = (Integer) itemIds.get(ii);
          int id = idI.intValue();
          CatalogStructureData csD = (CatalogStructureData) csDV.get(ii);
          if (csD.getItemId() != id) {
              throw new RemoteException("No consitency in Item - Catalog Structure for sku number substring: " + pCustSku + "." +
                      " Catalog Id: " + pShoppingItemRequest.getShoppingCatalogId() + "." +
                      " Clw_item item id: " + id + "." +
                      " Clw_catalog_structure item id: " + csD.getItemId());
          }
          String sku = csD.getCustomerSkuNum();
          if (sku == null || sku.trim().length() == 0) {
            itemSkuIds.add(idI);
          }
        }
      }
      if (itemNameIds != null && itemSkuIds != null) {
        itemSkuNameIds = new IdVector();
        int size1 = itemNameIds.size();
        int size2 = itemSkuIds.size();
        for (int ii = 0, jj = 0; ii < size1 && jj < size2; ) {
          Integer idI1 = (Integer) itemNameIds.get(ii);
          Integer idI2 = (Integer) itemSkuIds.get(jj);
          int id1 = idI1.intValue();
          int id2 = idI2.intValue();
          if (id1 == id2) {
            itemSkuNameIds.add(idI1);
            ii++;
            jj++;
          } else if (id1 < id2) {
            ii++;
          } else {
            jj++;
          }
        }
      } else if (itemNameIds != null) {
        itemSkuNameIds = itemNameIds;
      } else if (itemSkuIds != null) {
        itemSkuNameIds = itemSkuIds;
      }
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, items);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      if (nameFlag) {
        dbc.addLikeIgnoreCase(CatalogStructureDataAccess.SHORT_DESC, "%" + pName + "%");
      }
      if (skuFlag) {
          dbc.addLikeIgnoreCase(CatalogStructureDataAccess.CUSTOMER_SKU_NUM, "%" + pCustSku + "%");
          if(itemsWithSku!= null){
              dbc.addNotOneOf(CatalogStructureDataAccess.ITEM_ID, itemsWithSku);
          }
      }
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      IdVector itemIds = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);
      itemSkuNameIds.addAll(itemIds);
      itemSkuNameIds.addAll(priceListItemIds);
      dbcItem.addOneOf(ItemDataAccess.ITEM_ID, itemSkuNameIds);
    }
    if (pGreenCertifiedFlag) {
      dbcItem.addJoinTable("(Select item_id,count(item_id) " +
                           " as counts from clw_item_mapping  where item_mapping_cd='" +
                           RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY + "'" +
                           "                   group by item_id) certcomp");
      dbcItem.addCondition("item_id = certcomp.item_id and certcomp.counts>0");
    }
    if (!pGreenCertifiedFlag) {
      itemIdV = ItemDataAccess.selectIdOnly(con, ItemDataAccess.ITEM_ID, dbcItem);
    } else {
      ItemDataVector itemDV = ItemDataAccess.select(con, dbcItem);
      itemIdV = getItemIds(itemDV);
    }
    return itemIdV;
  }

    private IdVector getOrderGuidesItemVector(IdVector pOrderGuideV, ShoppingItemRequest pShoppingItemRequest) throws RemoteException, SQLException {

        Connection con = null;

        try {

            con = getConnection();

            String itemReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideV);
            dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemReq);
            dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

            log.info("getOrderGuidesItemVector()=> sql:" + OrderGuideStructureDataAccess.getSqlSelectIdOnly(OrderGuideStructureDataAccess.ITEM_ID, dbc));
            
            IdVector itemIds = OrderGuideStructureDataAccess.selectIdOnly(con, dbc);
            //---- STJ-6114: Performance Improvements - Optimize Pollock 
            log.info("getOrderGuidesItemVector() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
            ShoppingDAO.filterByProductBundle(con, itemIds, pShoppingItemRequest);
            log.info("getOrderGuidesItemVector() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
            //----
            return itemIds;

        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
    }

public List orderList(List pList, int pOrderBy) throws   RemoteException {
  try {
    ShoppingCartItemDataVector list = (ShoppingCartItemDataVector) pList;
    switch (pOrderBy) {
    case Constants.ORDER_BY_CATEGORY:
      pList = orderByCategoryName(list);
      break;
    case Constants.ORDER_BY_CUST_SKU:
      pList = orderBySkuName(list);
      break;
    case Constants.ORDER_BY_NAME:
      pList = orderByNameSku(list);
      break;
    case Constants.ORDER_BY_MFG_NAME:
      pList = orderByMfgName(list);
      break;
    case Constants.ORDER_BY_MFG_SKU:
      pList = orderByMfgSku(list);
      break;
    }
  } catch (Exception e) {
  }
  return pList;
}

    private ShoppingCartItemDataVector  prepareShoppingItems(Connection pCon, String pStoreType, int pCatalogId, int pContractId, Set pItemIds)
        throws RemoteException    {
        return prepareShoppingItems(pCon, pStoreType, pCatalogId,pContractId, pItemIds, 0, null);
    }
    private ShoppingCartItemDataVector  prepareShoppingItems(Connection pCon, String pStoreType, int pCatalogId, int pContractId, Set pItemIds, AccCategoryToCostCenterView pCategToCostCenterView)
        throws RemoteException    {
            return prepareShoppingItems(pCon, pStoreType, pCatalogId,pContractId, pItemIds, 0, pCategToCostCenterView);
    }

    //*******************************************************************************
     private ShoppingCartItemDataVector prepareShoppingItems(Connection pCon,
                                                            String pStoreType,
                                                            int pCatalogId,
                                                            int pContractId,
                                                            Set pItemIds,
                                                            int siteId)
             throws RemoteException {
           return prepareShoppingItems(pCon, pStoreType, pCatalogId,pContractId, pItemIds, siteId,null);
     }
    private ShoppingCartItemDataVector prepareShoppingItems(Connection pCon,
                                                           String pStoreType,
                                                           int pCatalogId,
                                                           int pContractId,
                                                           Set pItemIds,
                                                           int siteId,
                                                          AccCategoryToCostCenterView pCategToCostCenterView )
            throws RemoteException {

        try {
            ShoppingCartItemDataVector shoppingcartItems = new ShoppingCartItemDataVector();
            if (pItemIds != null && pItemIds.size() > 0) {
                ArrayList itemList = new ArrayList();
                itemList.addAll(pItemIds);
                if (ShoppingDAO.isUseProductBundle(pCon, siteId)) {
                    shoppingcartItems  = prepareShoppingItemsNew(pCon,
                            pStoreType,
                            pCatalogId,
                            pContractId,
                            itemList,
                            siteId,
                            pCategToCostCenterView);
                } else {
                    shoppingcartItems = prepareShoppingItems(pCon, pCatalogId, pContractId, itemList, siteId, pCategToCostCenterView);
                }
                shoppingcartItems = ShoppingDAO.setActualSkus(pStoreType, (ShoppingCartItemDataVector) shoppingcartItems);
            }

            return shoppingcartItems;
        } catch (Exception e) {
            throw  new RemoteException(e.getMessage());
        }
    }


    public ShoppingCartData getInvShoppingCart(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite,
                                               boolean pInventorySiteDataItems,
                                               AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException{

        Connection conn = null;
        try {
            conn = getConnection();
            return getInvShoppingCart(conn, pStoreType, pUser, pSite,pInventorySiteDataItems, pCategToCostCenterView);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public ShoppingCartData getInvShoppingCart(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite) throws RemoteException {
        return getInvShoppingCart(pStoreType, pUser, pSite, false);
    }
    public ShoppingCartData getInvShoppingCart(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite,
                                               boolean pInventorySiteDataItems) throws RemoteException{
      return getInvShoppingCart(pStoreType, pUser, pSite, pInventorySiteDataItems, null);
    }

    public ShoppingCartData getInventoryCartIL(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite,
                                               boolean pInventorySiteDataItems) throws RemoteException{
      return getInventoryCartIL(pStoreType, pUser, pSite,  pInventorySiteDataItems, null);

    }
    public ShoppingCartData getInventoryCartIL(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite,
                                               boolean pInventorySiteDataItems,
                                               AccCategoryToCostCenterView pCategToCostCenterView ) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return getInventoryCartIL(conn, pStoreType, pUser, pSite,pInventorySiteDataItems, pCategToCostCenterView);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }

    }
    public ShoppingCartData getInventoryCartIL(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite) throws RemoteException {
        return getInventoryCartIL(pStoreType,pUser,pSite,false, null);
    }


    public ShoppingCartData getInventoryCartIL(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
        return getInventoryCartIL(pStoreType,pUser,pSite,false, pCategToCostCenterView);
    }

    private ShoppingCartData getInvShoppingCart(Connection pCon,
                                                String pStoreType,
                                                UserData pUser,
                                                SiteData pSite,
                                                boolean pInventorySiteDataItems,
                                                AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

        ShoppingCartData invLevelCart = getInventoryCartIL(pCon, pStoreType, pUser, pSite, pInventorySiteDataItems);
        ShoppingCartData invOgCart    = getInventoryCartOG(pCon, pUser, pSite, pStoreType, pCategToCostCenterView);
        ShoppingCartData resultCart   = mergeCart(invLevelCart, invOgCart);

        logInfo("getInventoryShoppingCart => resultCart: " + resultCart);

        return resultCart;

    }

    private ShoppingCartData mergeCart(ShoppingCartData invLevelCart, ShoppingCartData invOgCart) throws Exception {

        ShoppingCartData resultCart = new ShoppingCartData();

        if (isCompatibleCarts(invLevelCart, invOgCart)) {
            if (invLevelCart != null && invOgCart != null) {

                resultCart = invLevelCart;
                resultCart.addItems(invOgCart.getItems());
                resultCart.setCustomerComments(invOgCart.getCustomerComments());
                resultCart.addRemovedProductInfo(invOgCart.getRemovedProductInfo());

            } else if (invLevelCart != null) {
                resultCart = invLevelCart;
            } else if (invOgCart != null) {
                resultCart = invOgCart;
            }
        } else {
            //templorary
            throw new Exception("can't be merge");
        }
        return resultCart;
    }

    private PairView splitCarts(ShoppingCartData mergedCart) throws Exception {

        ShoppingCartData invLevelCart = new ShoppingCartData();
        ShoppingCartData invOgCart = new ShoppingCartData();

        invLevelCart.setItems(mergedCart.getInventoryItemsOnly());
        invLevelCart.setSite(mergedCart.getSite());
        invLevelCart.setUser(mergedCart.getUser());
        invLevelCart.setCatalogId(mergedCart.getSite().getContractData().getCatalogId());
        invLevelCart.setContractId(mergedCart.getSite().getContractData().getContractId());
        invLevelCart.setStoreType(mergedCart.getStoreType());

        invOgCart.setItems(mergedCart.getRegularItemsOnly());
        invOgCart.setCustomerComments(mergedCart.getCustomerComments());
        invOgCart.setSite(mergedCart.getSite());
        invOgCart.setUser(mergedCart.getUser());
        invOgCart.setCatalogId(mergedCart.getSite().getContractData().getCatalogId());
        invOgCart.setContractId(mergedCart.getSite().getContractData().getContractId());
        invOgCart.setStoreType(mergedCart.getStoreType());


        return new PairView(invOgCart, invLevelCart);

    }




    private boolean isCompatibleCarts(ShoppingCartData invLevelCart, ShoppingCartData invOgCart) {

        if(invLevelCart==null||invOgCart==null)  {
            return true;
        }
        if(invLevelCart.getSite()==null || invOgCart.getSite()==null) {
            return false;
        }
        if(invLevelCart.getSite().getSiteId()!=invOgCart.getSite().getSiteId()) {
            return false;
        }
        return true;
    }
    public ShoppingCartData getInventoryCartIL(Connection pCon,
                                               String pStoreType,
                                               UserData pUser,
                                               SiteData pSite,
                                               boolean pInventorySiteDataItems) throws Exception {
      return getInventoryCartIL( pCon, pStoreType, pUser, pSite, pInventorySiteDataItems, null);

    }

    public ShoppingCartData getInventoryCartIL(Connection pCon,
                                               String pStoreType,
                                               UserData pUser,
                                               SiteData pSite,
                                               boolean pInventorySiteDataItems,
                                               AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {


        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        ShoppingCartData inventoryCart = new ShoppingCartData();

        if (isInventoryCartAvailable(pUser, pSite)) {

            inventoryCart.setUser(pUser);
            inventoryCart.setSite(pSite);
            inventoryCart.setContractId(pSite.getContractData().getContractId());
            inventoryCart.setCatalogId(pSite.getSiteCatalogId());
            inventoryCart.setStoreType(pStoreType);

            HashMap invInfoItemsMap;
            SiteInventoryInfoViewVector inventoryItems = new SiteInventoryInfoViewVector();

            if (pInventorySiteDataItems) {
                inventoryItems.addAll(pSite.getSiteInventory());
            } else {
                inventoryItems.addAll(siteEjb.lookupSiteInventory(pSite.getSiteId(),pCategToCostCenterView));
            }

            IdVector availableItemIds = getAvailableItemIdsOnly(pUser, pSite, Utility.toIdVector(inventoryItems));
            Iterator it = inventoryItems.iterator();
            while (it.hasNext()) {
                SiteInventoryInfoView siiv = (SiteInventoryInfoView) it.next();
                if (!availableItemIds.contains(siiv.getItemId())) {
                    it.remove();
                }
            }

            invInfoItemsMap = Utility.toMapByItemId(inventoryItems);
            /*ShoppingCartItemDataVector invShoppingItems = prepareShoppingItems(pCon,
            pStoreType,
            inventoryCart.getCatalogId(),
            inventoryCart.getContractId(),
            invInfoItemsMap.keySet());*/

            ShoppingCartItemDataVector invShoppingItems = prepareShoppingItems(pCon,
                    pStoreType,
                    inventoryCart.getCatalogId(),
                    inventoryCart.getContractId(),
                    invInfoItemsMap.keySet(), pSite.getSiteId(), pCategToCostCenterView);

            invShoppingItems = setupModernInventoryInfo(pCon, pSite, invShoppingItems, invInfoItemsMap);
            invShoppingItems = setupMaxOrderQtyValues(pSite, invShoppingItems);

            inventoryCart.setItems(invShoppingItems);
            inventoryCart.setShoppingInfo(updateShoppingInfo(pCon, pSite.getSiteId(), 0, null));
            List prodl = removedItemInfo(pCon, inventoryCart);
            inventoryCart.setRemovedProductInfo(prodl);

        }
        return inventoryCart;
    }

    public ShoppingCartData getInventoryCartIL(Connection pCon,
                                               String pStoreType,
                                               UserData pUser,
                                               SiteData pSite) throws Exception {
        return getInventoryCartIL(pCon, pStoreType, pUser, pSite, false);
    }


    private ShoppingCartItemDataVector setupModernInventoryInfo(Connection pCon,
                                                                SiteData pSite,
                                                                ShoppingCartItemDataVector invShoppingItems,
                                                                HashMap invInfoItemsMap) throws Exception {

        if (invShoppingItems != null) {
            BigDecimal autoOrderFactor = getAutoOrderFactor(pCon, pSite.getAccountId());
            Iterator it = invShoppingItems.iterator();
            while (it.hasNext()) {
                ShoppingCartItemData shoppingItem = (ShoppingCartItemData) it.next();
                SiteInventoryInfoView invInfoData = (SiteInventoryInfoView) invInfoItemsMap.get(new Integer(shoppingItem.getItemId()));
                if (invInfoData != null) {

                    shoppingItem.setIsaInventoryItem(true);
                    shoppingItem.setInventoryParValue(invInfoData.getParValue());
                    shoppingItem.setInventoryParValuesSum(invInfoData.getSumOfAllParValues());
                    shoppingItem.setMonthlyOrderQtyString(invInfoData.getOrderQty());

                    String qtyOnHandStr = Utility.strNN(invInfoData.getQtyOnHand());
                    String orderQtyStr  = Utility.strNN(invInfoData.getOrderQty());
                    qtyOnHandStr = qtyOnHandStr.trim();
                    orderQtyStr  = orderQtyStr.trim();

                    //logInfo("setupModernInventoryInfo => orderQtyStr: " + orderQtyStr + " qtyOnHandStr " + qtyOnHandStr);

                    int qtyOnHandInt = 0;
                    int orderQtyInt = 0;
                    boolean orderQtyIsSet = false;
                    boolean onHandQtyIsSet = false;

                    try {
                        qtyOnHandInt = Integer.parseInt(qtyOnHandStr);
                        shoppingItem.setInventoryQtyOnHand(qtyOnHandInt);
                        shoppingItem.setInventoryQtyOnHandString(qtyOnHandStr);
                        onHandQtyIsSet = true;
                    } catch (NumberFormatException exc) {
                        shoppingItem.setInventoryQtyOnHand(0);
                        shoppingItem.setInventoryQtyOnHandString(qtyOnHandStr);
                        onHandQtyIsSet = false;
                    }

                    try {
                        orderQtyInt = Integer.parseInt(orderQtyStr);
                        shoppingItem.setQuantity(orderQtyInt);
                        shoppingItem.setQuantityString(orderQtyStr);
                        orderQtyIsSet = true;
                    } catch (NumberFormatException exc) {
                        shoppingItem.setQuantity(0);
                        shoppingItem.setQuantityString(orderQtyStr);
                        orderQtyIsSet = false;
                    }

                    shoppingItem.setAutoOrderFactor(autoOrderFactor);
                    shoppingItem.setAutoOrderEnable(Utility.isTrue(invInfoData.getAutoOrderItem()));

                    if (orderQtyIsSet) {
                        shoppingItem.setInventoryQtyIsSet(true);
                    } else if (onHandQtyIsSet) {
                        shoppingItem.setInventoryQtyIsSet(true);
                    } else {
                        shoppingItem.setInventoryQtyIsSet(false);
                    }
                    shoppingItem.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.INVENTORY);
                }
            }
        }
        return invShoppingItems;
    }

    public ShoppingCartData getInventoryCartOG(UserData pUser, SiteData pSite, String pStoreType) throws RemoteException {
      return getInventoryCartOG( pUser,  pSite,  pStoreType, null);
    }

    public ShoppingCartData getInventoryCartOG(UserData pUser, SiteData pSite, String pStoreType,
                                               AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
         Connection conn = null;
        try {
            conn = getConnection();
            return getInventoryCartOG(conn,pUser,pSite,pStoreType, pCategToCostCenterView);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }
    public ShoppingCartData getInventoryCartOG(Connection pCon, UserData pUser, SiteData pSite, String pStoreType) throws Exception {
      return getInventoryCartOG( pCon,  pUser,  pSite, pStoreType, null);
    }

    public ShoppingCartData getInventoryCartOG(Connection pCon, UserData pUser, SiteData pSite, String pStoreType, AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

        ShoppingCartData shoppingCartD = new ShoppingCartData();
        try {
            OrderGuide ogEjb = APIAccess.getAPIAccess().getOrderGuideAPI();

            if (isInventoryCartAvailable(pUser, pSite)) {

                int siteId = pSite.getBusEntity().getBusEntityId();
                ShoppingCartItemDataVector cartItems;
                OrderGuideStructureDataVector ogsdv;

                OrderGuideData orderGuideD = ShoppingDAO.getCart(pCon, 0, siteId, pUser.getUserName(), RefCodeNames.ORDER_GUIDE_TYPE_CD.INVENTORY_CART);

                int id = orderGuideD.getOrderGuideId();

                shoppingCartD.setUser(pUser);
                shoppingCartD.setSite(pSite);
                shoppingCartD.setContractId(pSite.getContractData().getContractId());
                shoppingCartD.setCatalogId(pSite.getContractData().getCatalogId());
                shoppingCartD.setStoreType(pStoreType);
                shoppingCartD.setOrderGuideId(id);
                shoppingCartD.setModBy(orderGuideD.getModBy());
                shoppingCartD.setModDate(orderGuideD.getModDate());

                ShoppingItemRequest shoppingItemRequest = ShoppingDAO.createShoppingItemRequest(pCon, pUser, pSite);
                ogsdv = ogEjb.getAvailableOrderGuideItems(id, shoppingItemRequest);

                IdVector itemIds = new IdVector();
                for (Iterator iter = ogsdv.iterator(); iter.hasNext();) {
                    OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();
                    itemIds.add(new Integer(ogsD.getItemId()));
                }

                /*cartItems = prepareShoppingItems(pCon, pSite.getContractData().getCatalogId(),
                                                 pSite.getContractData().getContractId(), itemIds);*/
                if (pSite.isUseProductBundle()) {
                    cartItems = prepareShoppingItemsNew(pCon,
                            pStoreType,
                            pSite.getContractData().getCatalogId(),
                            pSite.getContractData().getContractId(),
                            itemIds,
                            pSite.getSiteId(),
                            pCategToCostCenterView);
                } else {
                    cartItems = prepareShoppingItems(pCon,
                            pSite.getContractData().getCatalogId(),
                            pSite.getContractData().getContractId(),
                            itemIds,
                            pSite.getSiteId(), pCategToCostCenterView);
                }

                if(pSite.getContractData().getCatalogId()>0 && cartItems !=null){

                	ProductDAO pdao = new ProductDAO(pCon,itemIds);
                	pdao.updateCatalogInfo(pCon,pSite.getContractData().getCatalogId(),pSite.getSiteId(), pCategToCostCenterView);
                	ProductDataVector pdV = pdao.getResultVector();
                }

                cartItems = ShoppingDAO.setActualSkus(pStoreType, cartItems);

                for (int i = 0; i < ogsdv.size(); i++) {
                    OrderGuideStructureData ogsD = (OrderGuideStructureData) ogsdv.get(i);
                    for (int j = 0; j < cartItems.size(); j++) {
                        ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(j);
                        if (ogsD.getItemId() == sciD.getItemId()) {
                            sciD.setQuantity(ogsD.getQuantity());
                            if (ogsD.getQuantity() > 0) {
                            	sciD.setQuantityString(String.valueOf(ogsD.getQuantity()));
                            } else {
                                sciD.setQuantityString("");
                            }
                            
                            sciD.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.SHOPPING_CART);
                            CatalogCategoryDataVector ccDV = sciD.getProduct().getCatalogCategories();
                            if (ccDV.size() == 0) {
                                sciD.setCategory(null);
                            } else {
                                CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
                                sciD.setCategory(ccD);
                            }
                            break;
                        }

                    }
                }

                cartItems = setupMaxOrderQtyValues(pSite, cartItems);
                shoppingCartD.setItems(cartItems);

                if (id > 0) {

                    shoppingCartD.setShoppingInfo(updateShoppingInfo(pCon, siteId,id, null));
                    List prodl = removedItemInfo(pCon, shoppingCartD);
                    shoppingCartD.setRemovedProductInfo(prodl);

                }
            }
            return shoppingCartD;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }
    private ShoppingCartItemDataVector prepareShoppingItemsNew(Connection pCon,
                                                               String pStoreType,
                                                               int pCatalogId,
                                                               int pContractId,
                                                               List pItems,
                                                               int pSiteId) throws Exception {
      return prepareShoppingItemsNew(pCon, pStoreType, pCatalogId, pContractId, pItems, pSiteId, null);

    }

    private ShoppingCartItemDataVector prepareShoppingItemsNew(Connection pCon,
                                                               String pStoreType,
                                                               int pCatalogId,
                                                               int pContractId,
                                                               List pItems,
                                                               int pSiteId,
                                                               AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {
      return ShoppingDAO.prepareShoppingItemsNew(pCon, pStoreType, pSiteId, pCatalogId, pContractId, pItems,pCategToCostCenterView );

    }

    private boolean isInventoryCartAvailable(UserData pUser,SiteData pSite) throws RemoteException {

        if(pUser==null){
            throw new RemoteException("isInventoryCartAvailable => ShoppingCartData object doesn't have user information");
        }

        if(pSite==null) {
            throw new RemoteException("isInventoryCartAvailable =>  ShoppingCartData object doesn't have site information");
        }

        if(pSite.getContractData()==null)        {
            throw new RemoteException("isInventoryCartAvailable =>  ShoppingCartData object doesn't have contract information");
        }

        if(pSite.getContractData().getCatalogId()<=0)        {
            logInfo("isInventoryCartAvailable => ERROR.catalogId: "+pSite.getContractData().getCatalogId());
            return false;
        }

        if(pSite.getContractData().getContractId()<=0)        {
            logInfo("isInventoryCartAvailable => ERROR.contractId: "+pSite.getContractData().getContractId());
            return false;
        }

        if(!pSite.hasModernInventoryShopping())        {
            logInfo("isInventoryCartAvailable => ERROR.site has not setup modernInventoryShopping");
            return false;
        }
        return true;
    }

    public BigDecimal getAutoOrderFactor(Connection conn, int pAccountId) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, pAccountId);
        dbc.addEqualToIgnoreCase(InventoryItemsDataAccess.ENABLE_AUTO_ORDER, "Y");
        InventoryItemsDataVector invItemsDV = InventoryItemsDataAccess.select(conn, dbc);
        BigDecimal autoOrderFactor = new BigDecimal(0.5);
        if (invItemsDV.size()> 0){
        dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);
        dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR);
        PropertyDataVector propDV = PropertyDataAccess.select(conn, dbc);
        if (propDV.size() > 0) {
            PropertyData propD = (PropertyData) propDV.get(0);
            String factorValStr = propD.getValue();
            if (Utility.isSet(factorValStr)) {
                try {

                    double factorValDbl = Double.parseDouble(factorValStr);

                    if (factorValDbl <= 0) factorValDbl = 0;
                    if (factorValDbl > 1) factorValDbl = 1;

                    autoOrderFactor =
                            new BigDecimal(factorValDbl).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                } catch (Exception ex) {
                    //Print stack and use default value;
                    log.info("ShoppingServicesBean::getAutoOrderFactor" +
                            "invalid auto order factor: " + factorValStr);
                }
            }
        }
    }
        return autoOrderFactor;
    }

    public void saveInventoryCartOG(ShoppingCartData pShoppingCart) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            saveInventoryCartOG(conn, pShoppingCart, null, null);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public void saveInventoryCartOG(ShoppingCartData pShoppingCart,
                                    ProcessOrderResultData pOrderResult,
                                    ShoppingCartItemDataVector pScartItemPurchased) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            saveInventoryCartOG(conn, pShoppingCart, pOrderResult, pScartItemPurchased);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public void saveInventoryShoppingCart(ShoppingCartData mergedCart, 
			boolean generateInvAuditEntry) throws RemoteException{
    	saveInventoryShoppingCart(mergedCart, null, null, false, generateInvAuditEntry);
    }
    
    public void saveInventoryShoppingCart(ShoppingCartData pShoppingCartOG,
                                          ShoppingCartData pShoppingCartIL) throws RemoteException {
        saveInventoryShoppingCart(pShoppingCartOG, pShoppingCartIL, false);

    }


    public void saveInventoryShoppingCart(ShoppingCartData pShoppingCartOG,
                                          ShoppingCartData pShoppingCartIL,
                                          boolean pUpdateFromPhysCart) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            saveInventoryCartOG(conn, pShoppingCartOG, null, null);
            saveInventoryCartIL(conn, pShoppingCartIL, null, null, pUpdateFromPhysCart);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public void saveInventoryShoppingCart(ShoppingCartData mergedCart,
            ProcessOrderResultData pProcessOrderResult,
            ShoppingCartItemDataVector pScartItemPurchased) throws RemoteException {
       saveInventoryShoppingCart(mergedCart, pProcessOrderResult, pScartItemPurchased, false);
    }

    public void saveInventoryShoppingCart(ShoppingCartData mergedCart,
            ProcessOrderResultData pProcessOrderResult,
            ShoppingCartItemDataVector pScartItemPurchased,
            boolean pUpdateFromPhysCart) throws RemoteException {
    	saveInventoryShoppingCart(mergedCart, pProcessOrderResult, pScartItemPurchased, false, true);
    }

    public void saveInventoryShoppingCart(ShoppingCartData mergedCart,
            ProcessOrderResultData pProcessOrderResult,
            ShoppingCartItemDataVector pScartItemPurchased,
            boolean pUpdateFromPhysCart, boolean generateInvAuditEntry) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            PairView carts = splitCarts(mergedCart);
            saveInventoryCartOG(conn, (ShoppingCartData) carts.getObject1(), pProcessOrderResult,pScartItemPurchased);
            if (generateInvAuditEntry)
            	saveInventoryCartIL(conn, (ShoppingCartData) carts.getObject2(), pProcessOrderResult,pScartItemPurchased, pUpdateFromPhysCart);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }
    public void saveInventoryCartIL(ShoppingCartData inventoryCart ) throws RemoteException {
      saveInventoryCartIL( inventoryCart,false, null );
    }

    public void saveInventoryCartIL(ShoppingCartData inventoryCart, boolean pUpdateFromPhysCart) throws RemoteException {
        saveInventoryCartIL(inventoryCart, pUpdateFromPhysCart, null);
    }

    public void saveInventoryCartIL(ShoppingCartData inventoryCart, boolean pUpdateFromPhysCart, AccCategoryToCostCenterView pCategToCostCenterView ) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            saveInventoryCartIL(conn, inventoryCart, null,null, pUpdateFromPhysCart, pCategToCostCenterView);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }
    public void saveInventoryCartIL(Connection pCon,
                                    ShoppingCartData pShoppingCart,
                                    ProcessOrderResultData pOrderResult,
                                    ShoppingCartItemDataVector pScartItemPurchased) throws Exception {
      saveInventoryCartIL(pCon, pShoppingCart, pOrderResult,pScartItemPurchased, false, null);

    }

    public void saveInventoryCartIL(Connection pCon,
                                    ShoppingCartData pShoppingCart,
                                    ProcessOrderResultData pOrderResult,
                                    ShoppingCartItemDataVector pScartItemPurchased,
                                    boolean pUpdateFromPhysCart) throws Exception {
      saveInventoryCartIL(pCon, pShoppingCart, pOrderResult,pScartItemPurchased, pUpdateFromPhysCart,null);
	}

    public void saveInventoryCartIL(Connection pCon,
                                    ShoppingCartData pShoppingCart,
                                    ProcessOrderResultData pOrderResult,
                                    ShoppingCartItemDataVector pScartItemPurchased,
                                    boolean pUpdateFromPhysCart,
                                    AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

        logInfo("saveInventoryCartIL => begin");
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        if (isInventoryCartReadyForSaving(pShoppingCart)) {

            SiteInventoryConfigViewVector oldConfig = siteEjb.lookupInventoryConfig(pShoppingCart.getSite().getSiteId(),false, pCategToCostCenterView);

            ShoppingCartItemDataVector cartItems = pShoppingCart.getItems();

            HashMap changes = getChangesSiteInvConfigs(pShoppingCart.getSite().getSiteId(),
                    oldConfig,
                    cartItems,
                    pShoppingCart.getUser().getUserName(), pUpdateFromPhysCart);


            SiteInventoryConfigViewVector deleteConfigs = (SiteInventoryConfigViewVector) changes.get(RefCodeNames.CHANGE_STATUS.DELETE);
            SiteInventoryConfigViewVector updateConfigs = (SiteInventoryConfigViewVector) changes.get(RefCodeNames.CHANGE_STATUS.UPDATE);
            SiteInventoryConfigViewVector insertConfigs = (SiteInventoryConfigViewVector) changes.get(RefCodeNames.CHANGE_STATUS.INSERT);

            logInfo("saveInventoryCartIL => deleteConfigs:" + deleteConfigs.size());
            logInfo("saveInventoryCartIL => updateConfigs:" + updateConfigs.size());
            logInfo("saveInventoryCartIL => insertConfigs:" + insertConfigs.size());
            logInfo("saveInventoryCartIL => siteId: " + pShoppingCart.getSite().getSiteId());

            SiteInventoryConfigViewVector updatedConfig;
            updatedConfig = siteEjb.updateInventoryConfigMod(pShoppingCart.getSite().getSiteId(), updateConfigs, pUpdateFromPhysCart, pCategToCostCenterView);

           inventoryCartILHistotyProcess(pCon, pShoppingCart.getSite(), oldConfig, updatedConfig,pOrderResult,
                pScartItemPurchased,pShoppingCart.getUser(), pUpdateFromPhysCart);

        }

    }



    private HashMap getChangesSiteInvConfigs(int siteId,
                                             SiteInventoryConfigViewVector oldItems,
                                             ShoppingCartItemDataVector newItems,
                                             String pUser, boolean updateFromPhysCart) throws Exception {

        HashMap changesMap = new HashMap();
        SiteInventoryConfigViewVector oldConfigs = new SiteInventoryConfigViewVector();
        ShoppingCartItemDataVector cartItems = new ShoppingCartItemDataVector();

        oldConfigs.addAll(oldItems);
        cartItems.addAll(newItems);

        SiteInventoryConfigViewVector insert = new SiteInventoryConfigViewVector();
        SiteInventoryConfigViewVector update = new SiteInventoryConfigViewVector();
        SiteInventoryConfigViewVector delete = new SiteInventoryConfigViewVector();

        changesMap.put(RefCodeNames.CHANGE_STATUS.DELETE, delete);
        changesMap.put(RefCodeNames.CHANGE_STATUS.INSERT, insert);
        changesMap.put(RefCodeNames.CHANGE_STATUS.UPDATE, update);


        if (oldConfigs.size() == 0 && cartItems.size() > 0) {

            Iterator it = cartItems.iterator();
            while (it.hasNext()) {
                insert.add(prepareSiteInvConfigView(siteId, (ShoppingCartItemData) it.next(), pUser));
            }
        } else if (oldConfigs.size() > 0 && cartItems.size() == 0) {

            delete.addAll(oldConfigs);

        } else {

            Iterator it = oldConfigs.iterator();
            while (it.hasNext()) {
                SiteInventoryConfigView oldConfig = (SiteInventoryConfigView) it.next();
                Iterator it1 = cartItems.iterator();
                while (it1.hasNext()) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it1.next();
                    if (oldConfig.getItemId() == cartItem.getItemId()) {
                        SiteInventoryConfigView newConfig = oldConfig.copy();
                        String cartOrderQtyStr = Utility.strNN(cartItem.getQuantityString()).trim();
                        String cartQtyOnHandStr = Utility.strNN(cartItem.getInventoryQtyOnHandString()).trim();
                        String newConfigQty = Utility.strNN(newConfig.getOrderQty()).trim();
                        String newConfigQtyonHand = Utility.strNN(newConfig.getQtyOnHand()).trim();
                        //Should save OnHandQty regardless changed quantities; !!!!!!!!! Yuriy Kup.
                        // Should save OnHandQty regardless of changed onHandQty if Physical cart update
                        if (updateFromPhysCart || !cartQtyOnHandStr.equals(newConfigQtyonHand)) {
                            newConfig.setQtyOnHand(cartQtyOnHandStr);
                            newConfig.setInitialQtyOnHand(cartQtyOnHandStr);
                            update.add(newConfig);
                        }
                        if (!cartOrderQtyStr.equals(newConfigQty)) {

                            newConfig.setOrderQty(cartOrderQtyStr);
                            update.add(newConfig);

                        //} else {
                        //    if (!cartQtyOnHandStr.equals(newConfigQtyonHand)) {
                        //        newConfig.setQtyOnHand(cartQtyOnHandStr);
                        //        update.add(newConfig);
                        //    }
                        }
                        it1.remove();
                        it.remove();
                        break;
                    }
                }
            }
            if (!cartItems.isEmpty()) {
                it = cartItems.iterator();
                while (it.hasNext()) {
                    insert.add(prepareSiteInvConfigView(siteId, (ShoppingCartItemData) it.next(), pUser));
                }
            }
            delete.addAll(oldConfigs);
        }
        return changesMap;
    }

    private void auditInventoryChanges(Connection con,
        int siteId, SiteInventoryConfigViewVector oldItems,
            SiteInventoryConfigViewVector newItems,
                UserData user, boolean pUpdateFromPhysCart) throws Exception {

        auditInventoryChanges(con, siteId, oldItems, newItems,
            user, ShoppingCartData.CART_ITEM_UPDATE, pUpdateFromPhysCart);
    }


    private void auditInventoryChanges(Connection pCon, int siteId,
                                          SiteInventoryConfigViewVector oldItems,
                                          SiteInventoryConfigViewVector newItems,
                                          UserData user,
                                          String historyShortDesc,
                                          boolean pUpdateFromPhysCart) throws Exception {

        SiteInventoryConfigViewVector oldConfigs = new SiteInventoryConfigViewVector();
        ShoppingCartItemDataVector newConfigs = new ShoppingCartItemDataVector();

        oldConfigs.addAll(oldItems);
        newConfigs.addAll(newItems);

       if (oldConfigs.size() == 0 && newConfigs.size() > 0) {

            Iterator it = newConfigs.iterator();
            while (it.hasNext()) {
                SiteInventoryConfigView newConfig = (SiteInventoryConfigView) it.next();
                ShoppingInfoData shoppingInfo = ShoppingDAO.generateInvAuditEntry(pCon, siteId, null, newConfig, 0, user.getUserName(), historyShortDesc, pUpdateFromPhysCart);
                if (shoppingInfo != null) {
                    ShoppingInfoDataAccess.insert(pCon, shoppingInfo);
                }
            }
        } else if (oldConfigs.size() > 0 && newConfigs.size() == 0) {

            Iterator it = oldConfigs.iterator();
            while (it.hasNext()) {
                SiteInventoryConfigView oldConfig = (SiteInventoryConfigView) it.next();
                ShoppingInfoData shoppingInfo = ShoppingDAO.generateInvAuditEntry(pCon, siteId, oldConfig, null, 0, user.getUserName(), historyShortDesc, pUpdateFromPhysCart);
                if (shoppingInfo != null) {
                    ShoppingInfoDataAccess.insert(pCon, shoppingInfo);
                }
            }

        } else {

            Iterator it = oldConfigs.iterator();
            while (it.hasNext()) {
                SiteInventoryConfigView oldConfig = (SiteInventoryConfigView) it.next();
                Iterator it1 = newConfigs.iterator();
                while (it1.hasNext()) {
                    SiteInventoryConfigView newConfig = (SiteInventoryConfigView) it1.next();
                    if (oldConfig.getItemId() == newConfig.getItemId()) {
                        ShoppingInfoData shoppingInfo = ShoppingDAO.generateInvAuditEntry(pCon, siteId, oldConfig, newConfig, 0, user.getUserName(), historyShortDesc, pUpdateFromPhysCart);
                        if (shoppingInfo != null) {
                            ShoppingInfoDataAccess.insert(pCon, shoppingInfo);
                        }
                        it1.remove();
                        it.remove();
                        break;
                    }
                }
            }

            if (!newConfigs.isEmpty()) {
                it = newConfigs.iterator();
                while (it.hasNext()) {
                    SiteInventoryConfigView newConfig = (SiteInventoryConfigView) it.next();
                    ShoppingInfoData shoppingInfo = ShoppingDAO.generateInvAuditEntry(pCon, siteId, null, newConfig, 0, user.getUserName(), historyShortDesc, pUpdateFromPhysCart);
                    if (shoppingInfo != null) {
                        ShoppingInfoDataAccess.insert(pCon, shoppingInfo);
                    }
                }
            }

            if (!oldConfigs.isEmpty()) {
                it = oldConfigs.iterator();
                while (it.hasNext()) {
                    SiteInventoryConfigView oldConfig = (SiteInventoryConfigView) it.next();
                    ShoppingInfoData shoppingInfo = ShoppingDAO.generateInvAuditEntry(pCon, siteId, oldConfig, null, 0, user.getUserName(), historyShortDesc, pUpdateFromPhysCart);
                    if (shoppingInfo != null) {
                        ShoppingInfoDataAccess.insert(pCon, shoppingInfo);
                    }
                }
            }
        }


    }

    private HashMap getOrderGuideChanges(int orderGuideId,
                                         OrderGuideStructureDataVector oldItems,
                                         ShoppingCartItemDataVector newItems,
                                         String pUser,
                                         IdVector availableItemIds) throws Exception {

        HashMap changesMap = new HashMap();
        HashSet availableKeys = null;
        OrderGuideStructureDataVector oldCartItems = new OrderGuideStructureDataVector();
        ShoppingCartItemDataVector cartItems = new ShoppingCartItemDataVector();

        oldCartItems.addAll(oldItems);
        cartItems.addAll(newItems);

        if (availableItemIds != null) {
            availableKeys = new HashSet(availableItemIds);
        }
        OrderGuideStructureDataVector insert = new OrderGuideStructureDataVector();
        OrderGuideStructureDataVector update = new OrderGuideStructureDataVector();
        OrderGuideStructureDataVector delete = new OrderGuideStructureDataVector();

        changesMap.put(RefCodeNames.CHANGE_STATUS.DELETE, delete);
        changesMap.put(RefCodeNames.CHANGE_STATUS.INSERT, insert);
        changesMap.put(RefCodeNames.CHANGE_STATUS.UPDATE, update);


        if (oldCartItems.size() == 0 && cartItems.size() > 0) {
            Iterator it = cartItems.iterator();
            while (it.hasNext()) {
                ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();
                Integer key = new Integer(cartItem.getItemId());
                if (availableKeys == null || availableKeys.contains(key)) {
                    insert.add(prepareOrderGuideStructure(orderGuideId, cartItem, pUser));
                }
            }
        } else if (oldCartItems.size() > 0 && cartItems.size() == 0) {

            Iterator it = oldCartItems.iterator();
            while (it.hasNext()) {
                OrderGuideStructureData ogItem = (OrderGuideStructureData) it.next();
                Integer key = ogItem.getItemId();
                if (availableKeys == null || availableKeys.contains(key)) {
                    delete.add(ogItem);
                }
            }

        } else {

            Iterator it = oldCartItems.iterator();
            while (it.hasNext()) {
                OrderGuideStructureData oldCartItem = (OrderGuideStructureData) it.next();
                Iterator it1 = cartItems.iterator();
                while (it1.hasNext()) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it1.next();
                    if (oldCartItem.getItemId() == cartItem.getItemId()) {
                        Integer key = new Integer(cartItem.getItemId());
                        if (availableKeys == null || availableKeys.contains(key)) {
                            OrderGuideStructureData newitem = (OrderGuideStructureData) oldCartItem.clone();
                            newitem.setOrderGuideStructureId(oldCartItem.getOrderGuideStructureId());
                            int qty = cartItem.getQuantity();
                            if (qty != newitem.getQuantity()) {
                                newitem.setQuantity(qty);
                                update.add(newitem);
                            }
                        }
                        it1.remove();
                        it.remove();
                        break;
                    }
                }
            }

            if (!cartItems.isEmpty()) {
                it = cartItems.iterator();
                while (it.hasNext()) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();
                    Integer key = new Integer(cartItem.getItemId());
                    if (availableKeys == null || availableKeys.contains(key)) {
                        insert.add(prepareOrderGuideStructure(orderGuideId, cartItem, pUser));
                    }
                }
            }

            if (!oldCartItems.isEmpty()) {
                it = oldCartItems.iterator();
                while (it.hasNext()) {
                    OrderGuideStructureData ogItem = (OrderGuideStructureData) it.next();
                    Integer key = ogItem.getItemId();
                    if (availableKeys == null || availableKeys.contains(key)) {
                        delete.add(ogItem);
                    }
                }
            }
        }
        return changesMap;
    }

    public void saveInventoryCartOG(Connection pCon,
                                    ShoppingCartData pShoppingCart,
                                    ProcessOrderResultData pOrderResult,
                                    ShoppingCartItemDataVector pScartItemPurchased) throws Exception {

        logInfo("saveInventoryCartOG => begin");

        OrderGuide ogEjb = APIAccess.getAPIAccess().getOrderGuideAPI();
        if (isInventoryCartReadyForSaving(pShoppingCart)) {

            OrderGuideData orderGuideD;
            SiteData site = pShoppingCart.getSite();
            UserData user = pShoppingCart.getUser();
            orderGuideD = ShoppingDAO.getCart(pCon, 0, site.getSiteId(), user.getUserName(), RefCodeNames.ORDER_GUIDE_TYPE_CD.INVENTORY_CART);
            int ogId = orderGuideD.getOrderGuideId();
            OrderGuideStructureDataVector ogItems = ShoppingDAO.getCartItems(pCon, ogId);

            IdVector availableItemIds = ShoppingDAO.getShoppingItemIds(pCon, ShoppingDAO.createShoppingItemRequest(pCon, user, site));

            HashMap changes = getOrderGuideChanges(ogId, ogItems, pShoppingCart.getItems(), user.getUserName(), availableItemIds);

            OrderGuideStructureDataVector updateItems = (OrderGuideStructureDataVector) changes.get(RefCodeNames.CHANGE_STATUS.UPDATE);
            OrderGuideStructureDataVector insertItems = (OrderGuideStructureDataVector) changes.get(RefCodeNames.CHANGE_STATUS.INSERT);
            OrderGuideStructureDataVector deleteItems = (OrderGuideStructureDataVector) changes.get(RefCodeNames.CHANGE_STATUS.DELETE);

            updateItems.addAll(insertItems);

            //Delete removed items from order guide
            for (Iterator iter = deleteItems.iterator(); iter.hasNext();) {
                OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();
                int ogsId = ogsD.getOrderGuideStructureId();
                OrderGuideStructureDataAccess.remove(pCon, ogsId);
            }

            ogEjb.updateOgStructureCollection(updateItems, user.getUserName());
            OrderGuideStructureDataVector newOgsdv = ShoppingDAO.getCartItems(pCon, ogId);
            inventoryCartOGHistotyProcess(pCon, site, ogId, orderGuideD, ogItems, newOgsdv, pOrderResult, pScartItemPurchased, user.getUserName());

            logInfo("saveInventoryCartOG => end");
        }

    }


    private void inventoryCartOGHistotyProcess(Connection pCon,
                                             SiteData site,
                                             int ogId,
                                             OrderGuideData orderGuideD,
                                             OrderGuideStructureDataVector ogItems,
                                             OrderGuideStructureDataVector newOgsdv,
                                             ProcessOrderResultData pOrderResult,
                                             ShoppingCartItemDataVector pScartItemPurchased, String userName) throws Exception {

        if (pScartItemPurchased != null) {
            for (int idx = 0; idx < pScartItemPurchased.size(); idx++) {
                logInfo("inventoryHistotyProcess => order was placed, pOrderResult=" + pOrderResult);
                ShoppingCartItemData scid = (ShoppingCartItemData) pScartItemPurchased.get(idx);
                ShoppingDAO.setOrderHistory(pCon,
                        site.getBusEntity().getBusEntityId(),
                        pOrderResult.getOrderId(),ogId,
                        scid.getItemId());
            }
            clearRemovedItemsEntries(pCon, site.getBusEntity().getBusEntityId(),ogId);
        } else if (pOrderResult != null) {
            logInfo("inventoryHistotyProcess => order was placed.");
            ShoppingDAO.setOrderHistory(pCon, site.getBusEntity().getBusEntityId(), pOrderResult.getOrderId(), ogId, 0);
        } else {
            auditCartChanges(pCon, ogId, orderGuideD, ogItems, newOgsdv, userName);
        }
    }

    private void inventoryCartILHistotyProcess(Connection pCon,
                                               SiteData site,
                                               SiteInventoryConfigViewVector oldItems,
                                               SiteInventoryConfigViewVector newItems,
                                               ProcessOrderResultData pOrderResult,
                                               ShoppingCartItemDataVector pScartItemPurchased, UserData user) throws Exception {
        inventoryCartILHistotyProcess(pCon,site,oldItems,newItems,pOrderResult,pScartItemPurchased, user, false);
    }

    private void inventoryCartILHistotyProcess(Connection pCon,
                                               SiteData site,
                                               SiteInventoryConfigViewVector oldItems,
                                               SiteInventoryConfigViewVector newItems,
                                               ProcessOrderResultData pOrderResult,
                                               ShoppingCartItemDataVector pScartItemPurchased, UserData user,
                                               boolean pUpdateFromPhysCart) throws Exception {

        if (pScartItemPurchased != null) {
            for (int idx = 0; idx < pScartItemPurchased.size(); idx++) {
                logInfo("inventoryCartILHistotyProcess => order was placed, pOrderResult=" + pOrderResult);
                ShoppingCartItemData scid = (ShoppingCartItemData) pScartItemPurchased.get(idx);
                ShoppingDAO.setOrderHistory(pCon,
                        site.getBusEntity().getBusEntityId(),
                        pOrderResult.getOrderId(), 0,
                        scid.getItemId());
            }
            clearRemovedItemsEntries(pCon, site.getBusEntity().getBusEntityId(), 0);
        } else if (pOrderResult != null) {
            logInfo("inventoryCartILHistotyProcess => order was placed.");
            ShoppingDAO.setOrderHistory(pCon, site.getBusEntity().getBusEntityId(), pOrderResult.getOrderId(), 0, 0);
        } else {
            auditInventoryChanges(pCon, site.getSiteId(), oldItems, newItems, user, pUpdateFromPhysCart);
        }
    }


    public SiteInventoryConfigView prepareSiteInvConfigView(int siteId, ShoppingCartItemData cartItem, String pUser) {

        SiteInventoryConfigView newConfig = SiteInventoryConfigView.createValue();
        newConfig.setAutoOrderItem(cartItem.getAutoOrderEnable() ? "Y" : "N");
        newConfig.setItemId(cartItem.getItemId());
        newConfig.setItemDesc(cartItem.getProduct().getShortDesc());
        newConfig.setItemSku(cartItem.getProduct().getSkuNum());
        newConfig.setProductData(cartItem.getProduct());
        newConfig.setItemPack(cartItem.getProduct().getPack());
        newConfig.setItemUom(cartItem.getProduct().getUom());
        newConfig.setQtyOnHand(cartItem.getInventoryQtyOnHandString());
        newConfig.setOrderQty(String.valueOf(cartItem.getQuantity()));
        newConfig.setSiteId(siteId);
        newConfig.setSumOfAllParValues(cartItem.getInventoryParValuesSum());
        newConfig.setModBy(pUser);
        return newConfig;
    }


    public OrderGuideStructureData prepareOrderGuideStructure(int orderGuideId, ShoppingCartItemData cartItem, String pUser) {

        OrderGuideStructureData newOgItem = OrderGuideStructureData.createValue();
        newOgItem.setItemId(cartItem.getItemId());
        newOgItem.setOrderGuideId(orderGuideId);
        newOgItem.setQuantity(cartItem.getQuantity());

        int categoryId = 0;
        CatalogCategoryData category = cartItem.getCategory();
        if (category != null) {
            categoryId = category.getCatalogCategoryId();
        } else {
            //Pickup any category
            CatalogCategoryDataVector ccDV = cartItem.getProduct().getCatalogCategories();
            if (ccDV != null && ccDV.size() > 0) {
                category = (CatalogCategoryData) ccDV.get(0);
                categoryId = category.getCatalogCategoryId();
            }
        }
        newOgItem.setCategoryItemId(categoryId);
        newOgItem.setModBy(pUser);
        newOgItem.setAddBy(pUser);
        return newOgItem;
    }


    private boolean isInventoryCartReadyForSaving(ShoppingCartData pShoppingCart) throws RemoteException {

        UserData user = pShoppingCart.getUser();

        if (user == null) {
            throw new RemoteException("isInventoryCartReadyForSaving => ShoppingCartData object doesn't have user information");
        }

        // If the user is configured for browse only,
        // don't save the cart.
        UserRightsTool urt = new UserRightsTool(user);
        if (urt.isBrowseOnly()) {
            return false;
        }

        SiteData site = pShoppingCart.getSite();
        if (site == null) {
            throw new RemoteException("isInventoryCartReadyForSaving =>  ShoppingCartData object doesn't have site information");
        }

        if (!site.hasInventoryShoppingOn() || !site.hasModernInventoryShopping()) {
            return false;
        }

        return true;

    }

    public OrderGuideDataVector getCartsForReminderAction() throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getCartsForReminderAction(conn);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }


    private OrderGuideDataVector getCartsForReminderAction(Connection conn) throws Exception {

        OrderGuideDataVector resultOgs = new OrderGuideDataVector();
        String cart_sql =
                "SELECT og.BUS_ENTITY_ID,og.CATALOG_ID,og.ORDER_GUIDE_ID,og.USER_ID," +
                        "og.ADD_BY,og.ADD_DATE,og.MOD_BY,og.MOD_DATE," +
                        "og.ORDER_GUIDE_TYPE_CD,og.SHORT_DESC FROM clw_order_guide og," +
                        " (SELECT user_id  FROM clw_user_assoc " +
                        "     WHERE bus_entity_id = ? " +
                        "     AND user_assoc_cd='" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "') u" +
                        " WHERE og.user_id = u.user_id " +
                        " AND og.ORDER_GUIDE_TYPE_CD='" + RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART + "'" +
                        " AND og.mod_date < ? " +
                        " AND exists(Select * FROM clw_order_guide_structure WHERE order_guide_id=og.order_guide_id )";


        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
        dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.CART_REMINDER_INTERVAL);
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        dbc.addIsNotNull(PropertyDataAccess.CLW_VALUE);

        PropertyDataVector remIntervals = PropertyDataAccess.select(conn, dbc);

        if (remIntervals != null) {

            Date currentlyDate = new Date();
            PreparedStatement pstm = conn.prepareStatement(cart_sql);
            Iterator it = remIntervals.iterator();

            while (it.hasNext()) {
                PropertyData propInterval = (PropertyData) it.next();
                GregorianCalendar remDate;
                try {
                    int interval = Integer.parseInt(propInterval.getValue());
                    remDate = new GregorianCalendar();
                    remDate.setTime(currentlyDate);
                    remDate.add(Calendar.DATE, -interval);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    continue;
                }

                pstm.setInt(1, propInterval.getBusEntityId());
                pstm.setTimestamp(2, DBAccess.toSQLTimestamp(remDate.getTime()));

                ResultSet rs = pstm.executeQuery();

                OrderGuideData x = null;
                while (rs.next()) {

                    x = OrderGuideData.createValue();
                    x.setBusEntityId(rs.getInt(1));
                    x.setCatalogId(rs.getInt(2));
                    x.setOrderGuideId(rs.getInt(3));
                    x.setUserId(rs.getInt(4));
                    x.setAddBy(rs.getString(5));
                    x.setAddDate(rs.getTimestamp(6));
                    x.setModBy(rs.getString(7));
                    x.setModDate(rs.getTimestamp(8));
                    x.setOrderGuideTypeCd(rs.getString(9));
                    x.setShortDesc(rs.getString(10));

                    resultOgs.add(x);

                }
                rs.close();
            }
            pstm.close();
        }
        return resultOgs;
    }

  public void invalidateInvetoryShoppingHistory(int pSiteId, String pUser)
  throws RemoteException {

        ShoppingCartData shoppingCartD = new ShoppingCartData();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID,pSiteId);
            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
                    RefCodeNames.ORDER_GUIDE_TYPE_CD.INVENTORY_CART);
            IdVector orderGuideIdV =
                    OrderGuideDataAccess.selectIdOnly(conn,OrderGuideDataAccess.ORDER_GUIDE_ID,dbc);
            orderGuideIdV.add(new Integer(0)); //inventory items;

            dbc = new DBCriteria();
            dbc.addEqualTo(ShoppingInfoDataAccess.SITE_ID,pSiteId);
            dbc.addOneOf(ShoppingInfoDataAccess.ORDER_GUIDE_ID, orderGuideIdV);
            dbc.addIsNull(ShoppingInfoDataAccess.ORDER_ID);
            ShoppingInfoDataVector shoppingInfoDV =
                    ShoppingInfoDataAccess.select(conn,dbc);

            for(Iterator iter = shoppingInfoDV.iterator(); iter.hasNext();) {
                ShoppingInfoData siD = (ShoppingInfoData) iter.next();
                siD.setShoppingInfoStatusCd(RefCodeNames.SHOPPING_INFO_STATUS_CD.INACTIVE);
                siD.setModBy(pUser);
                ShoppingInfoDataAccess.update(conn,siD);
            }
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(),e);
        }
        finally {
            closeConnection(conn);
        }
    }

    public ShoppingCartItemDataVector prepareShoppingGroupItems(String pStoreType,
                                                                SiteData pSite,
                                                                int pItemGroupId,
                                                                ShoppingItemRequest pShoppingItemRequest) throws RemoteException {



        Connection conn = null;
        try {

            conn = getConnection();

            IdVector groupItemIds = getGroupItemIds(conn, pItemGroupId, pShoppingItemRequest);

            return prepareShoppingItems(pStoreType,
                    pSite,
                    pShoppingItemRequest.getShoppingCatalogId(),
                    pShoppingItemRequest.getContractId(),
                    groupItemIds);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }

    }

    private IdVector getGroupItemIds(Connection conn,  int itemGroupId, ShoppingItemRequest pShoppingItemRequest) throws SQLException {

        IdVector groupItemIds;
        DBCriteria dbc = new DBCriteria();

        String itemReq = ShoppingDAO.getShoppingItemIdsRequest(conn, pShoppingItemRequest);

        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_GROUP_ID, itemGroupId);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemReq);
        if (pShoppingItemRequest.getShoppingCatalogId() > 0) {
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
        }

        groupItemIds = CatalogStructureDataAccess.selectIdOnly(conn, CatalogStructureDataAccess.ITEM_ID, dbc);
        //---- STJ-6114: Performance Improvements - Optimize Pollock 
        log.info("getGroupItemIds() ===>Optimize Pollock BEGIN: groupItemIds.size() =" + groupItemIds.size());
        ShoppingDAO.filterByProductBundle(conn, groupItemIds, pShoppingItemRequest);
        log.info("getGroupItemIds() ===>Optimize Pollock END: groupItemIds.size() =" + groupItemIds.size());
        //----

        return groupItemIds;
    }

    public ProductDataVector integratedSearch(String storeType,
                                              ShoppingItemRequest pShoppingItemRequest,
                                              String searchString) throws RemoteException {
      return integratedSearch(storeType, pShoppingItemRequest, searchString, null);

    }
    public ProductDataVector integratedSearch(String storeType,
                                              ShoppingItemRequest pShoppingItemRequest,
                                              String searchString,
                                              AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
        Connection conn = null;

        try {

            conn = getConnection();

            ProductDataVector foundProducts = new ProductDataVector();
            if(!RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)) {
                return foundProducts; //Works for DISTRIBUTOR stores only (so far)
            }

            if(!Utility.isSet(searchString)) {
                return foundProducts;
            }

            searchString = searchString.toLowerCase();



/* If we needed in the future
IF(store_type == 'MLA')

SELECT cs_shop.item_id, COALESCE(cs_shop.CUSTOMER_SKU_NUM, cs_st.CUSTOMER_SKU_NUM) act_sku_num, cs_shop.CUSTOMER_SKU_NUM, cs_st.CUSTOMER_SKU_NUM
FROM clw_catalog_structure cs_st
JOIN clw_catalog_structure cs_shop ON cs_st.item_id = cs_shop.item_id
WHERE cs_st.catalog_id = 6507
AND cs_shop.catalog_id = 7490
AND (cs_st.customer_sku_num LIKE '%0%' OR cs_shop.customer_sku_num LIKE '%0%')
;
*/
            //Break search string by tokens
            String[] searchArray = Utility.parseStringToArray(searchString, " ");
            IdVector resultIdV = null;
            for(int ii=0; ii<searchArray.length; ii++) {
                String searchWord = searchArray[ii];
                if(!Utility.isSet(searchWord)) {
                    continue;
                }
                logInfo("Search Item. Catalog id = "+pShoppingItemRequest.getShoppingCatalogId());
                logInfo("Search Item. SearchWord = "+searchWord);
                logInfo("Search Item. Contract id = "+pShoppingItemRequest.getContractId());
                logInfo("Search Item. Shopping Item Request = "+pShoppingItemRequest);
                //Find item by custormer (distributor sku) num
                String sql =  "Select item_id from"+
                    "(SELECT cs_shop.item_id as item_id"+ //, COALESCE(cs_shop.CUSTOMER_SKU_NUM, im.ITEM_NUM) act_sku_num, cs_shop.CUSTOMER_SKU_NUM
                    " FROM clw_catalog_structure cs_shop "+
                    " LEFT JOIN clw_item_mapping im  "+
                     "  ON im.item_id = cs_shop.item_id " +
                     "  AND im.bus_entity_id = cs_shop.bus_entity_id "+
                     "  AND im.item_mapping_cd = '"+RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR+"' "+
                     "  WHERE cs_shop.catalog_id = ? "+ //catalog_id
                     "  AND (lower(cs_shop.customer_sku_num) LIKE ? OR " + //searchWord
                     "       cs_shop.customer_sku_num is null AND lower(im.item_num) LIKE ?) " + //searchWord, searchWord
                // Find item by short desc
                     " union "+
                     " SELECT i.item_id as item_id"+ //, COALESCE(cs_shop.CUSTOMER_SKU_NUM, i.short_desc) act_sku_num, cs_shop.CUSTOMER_SKU_NUM, i.short_desc
                     " FROM clw_item i "+
                     " JOIN clw_catalog_structure cs_shop "+
                     " ON i.item_id = cs_shop.item_id "+
                     " WHERE cs_shop.catalog_id = ?"+ //catalog id
                     " AND (lower(cs_shop.short_desc) LIKE ? OR" + //searchWord
                     "      cs_shop.short_desc is null AND lower(i.short_desc) LIKE ?) "+ //searchWord, searchWord
                // Manufacturer SKU number
                     " union "+
                     " SELECT cs_shop.item_id as item_id "+ //, im.ITEM_NUM act_sku_num, im.item_num, null
                     " FROM clw_catalog_structure cs_shop "+
                     " LEFT JOIN clw_item_mapping im "+
                     " ON im.item_id = cs_shop.item_id " +
                     " AND im.item_mapping_cd = '"+RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER+"' "+
                     " WHERE cs_shop.catalog_id = ?"+  //catalog id
                     " AND lower(im.item_num) LIKE ? "+ //SearchWord
                     ") where item_id in("+ShoppingDAO.getShoppingItemIdsRequest(conn, pShoppingItemRequest)+") ORDER BY item_id ";
                  logInfo("Search Item Sql = "+sql);

                  PreparedStatement pstm = conn.prepareStatement(sql);
                  searchWord = '%'+searchWord+'%';
                  // Sku serarch
                  pstm.setInt(1, pShoppingItemRequest.getShoppingCatalogId());
                  pstm.setString(2, searchWord);
                  pstm.setString(3, searchWord);
                  // Short dessc serach
                  pstm.setInt(4, pShoppingItemRequest.getShoppingCatalogId());
                  pstm.setString(5, searchWord);
                  pstm.setString(6, searchWord);
                  // Manuf searc
                  pstm.setInt(7, pShoppingItemRequest.getShoppingCatalogId());
                  pstm.setString(8, searchWord);

                  ResultSet rs = pstm.executeQuery();
                  if(resultIdV==null) { //first word
                      resultIdV = new IdVector();
                      while (rs.next()) {
                          int itemId = rs.getInt(1);
                          resultIdV.add(new Integer(itemId));
                      }
                  } else {
                      IdVector newResultIdV = new IdVector();
                      Iterator iter = resultIdV.iterator();
                      Integer wrkItemIdI = null;
                      while (rs.next()) {
                          int itemId = rs.getInt(1);
                          while(wrkItemIdI!=null || iter.hasNext()) {
                              if(wrkItemIdI==null) {
                                  wrkItemIdI = (Integer) iter.next();
                              }
                              int wrkItemId = wrkItemIdI.intValue();
                              if(wrkItemId==itemId) {
                                  newResultIdV.add(new Integer(itemId));
                                  wrkItemIdI = null;
                                  break;
                              }
                              if(wrkItemId < itemId) {
                                  wrkItemIdI = null;
                                  continue;
                              }
                              break;
                          }
                      }
                      resultIdV = newResultIdV;
                  }
                  rs.close();
                  pstm.close();
                  if(resultIdV.isEmpty()) {
                      break;
                  }
            }
            //---- STJ-6114: Performance Improvements - Optimize Pollock 
            log.info("integratedSearch() ===>Optimize Pollock BEGIN: resultIdV.size() =" + resultIdV.size());
            ShoppingDAO.filterByProductBundle(conn, resultIdV, pShoppingItemRequest);
            log.info("integratedSearch() ===>Optimize Pollock END: resultIdV.size() =" + resultIdV.size());
            //----


            if(!resultIdV.isEmpty()) {
                //ProductDAO productDAO = new ProductDAO(conn, resultIdV);
                //foundProducts = productDAO.getResultVector();
                foundProducts = getCatalogClwProductCollection(conn, pShoppingItemRequest.getShoppingCatalogId(), resultIdV, pCategToCostCenterView );
            }

            return foundProducts;



/* May need some time
-- LONG DESC
SELECT i.item_id,  i.long_desc, null, i.long_desc
FROM clw_item i
JOIN clw_catalog_structure cs_shop ON i.item_id = cs_shop.item_id
WHERE 1=1
AND cs_shop.catalog_id = 7490
AND (i.long_desc LIKE '%0%')
;
*/



        } catch (Exception e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }

    }
  public ProductDataVector getCatalogClwProductCollection
    ( int pCatalogId, IdVector pIds, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
      Connection conn = null;

      try {

          conn = getConnection();
          return getCatalogClwProductCollection ( conn,  pCatalogId, pIds, pCategToCostCenterView );
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException(e.getMessage(), e);
	  } finally {
	      closeConnection(conn);
	  }
 
  } 	
  public ProductDataVector getCatalogClwProductCollection
        (Connection con, int pCatalogId, IdVector pIds) throws RemoteException, DataNotFoundException {
      return getCatalogClwProductCollection ( con,  pCatalogId, pIds, null );
  }

  public ProductDataVector getCatalogClwProductCollection
      (Connection con, int pCatalogId, IdVector pIds, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException {

    ProductDataVector productDV = new ProductDataVector();
    try {
      ProductDAO tpdao = new ProductDAO(con, pIds);
      if (pCatalogId > 0) {
        tpdao.updateCatalogInfo(con, pCatalogId, pCategToCostCenterView);
      }
      productDV = tpdao.getResultVector();

    }
    catch (Exception e) {
      e.printStackTrace();
      logError("getCatalogClwProductCollection: " + e);
    }

  return productDV;
}

    /**
     * processParLoaderRequest
     */
    public void updateInventoryParValues(String pUserName,
                                         int pSiteId,
                                         int pCurrentInventoryPeriod,
                                         ShoppingCartItemDataVector cartItems) throws RemoteException {

        logInfo("updateInventoryParValues => BEGIN");

        Connection conn = null;

        //Update parValues
        try {

            conn = getConnection();

            //get the par record if it exists
            IdVector itemIdVector = new IdVector();
            HashMap<Integer, String> parMap = new HashMap<Integer, String>();
            if (cartItems != null && cartItems.size() > 0) {
                for (Object oCartItem : cartItems) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) oCartItem;
                    Integer itemId = cartItem.getItemId();
                    itemIdVector.add(itemId);
                    parMap.put(itemId, cartItem.getQuantityString());
                }
            }

            InventoryLevelViewVector ilVV = InventoryLevelDAO.getInvLevelViewCollections(conn, pSiteId, itemIdVector);

            logInfo("updateInventoryParValues => currPeriodNo = " + pCurrentInventoryPeriod);
            for (Object oInvLevelView : ilVV) {

                InventoryLevelView ilView = (InventoryLevelView) oInvLevelView;

                InventoryLevelViewWrapper ilViewWrapper = new InventoryLevelViewWrapper(ilView);
                int newParValue = Integer.parseInt(parMap.get(ilView.getInventoryLevelData().getItemId()));
                ilViewWrapper.setParValue(pCurrentInventoryPeriod, newParValue);

                logInfo("updateInventoryParValues => newParValue = " + newParValue);

                ilViewWrapper.setParsModBy(pUserName);
                ilViewWrapper.setParsModDate(new java.util.Date(System.currentTimeMillis()));

                InventoryLevelDAO.updateInventoryLevelView(conn, ilViewWrapper.getInventoryLevelView(), pUserName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }

        logInfo("updateInventoryParValues => END.");

    }


    //*************************************************************************************
    /**
     * Calculates charge amount. Does not apply if pContractId=0 or no active freight table found for the contact
     *
     * @param pContractId the contract identifier to filter out extra items, if not 0
     * @param pAmount     purchase amount
     * @param pOrder      order
     * @param chargeCd    charge code
     * @return charge amount.
     * @throws RemoteException if an errors
     */

    public BigDecimal getChargeAmtByCode(int pContractId,
                                   BigDecimal pAmount,
                                   OrderHandlingView pOrder,
                                   String chargeCd) throws RemoteException {
        logDebug("getChargeAmt => begin");
        Connection con = null;
        BigDecimal chargeAmt = new BigDecimal(0);
        if (pContractId == 0) {
            return chargeAmt; //No contract - no charge !!!!!!!!!!
        }
        try {
            con = getConnection();
            FreightTableData freightTableData = getFreightTableByContract(con, pContractId);
            if (freightTableData == null) {
                return chargeAmt; //No table - no charge !!!!!!!!!!
            }
            String freightType = freightTableData.getFreightTableTypeCd();
            FreightTableCriteriaDataVector intervals =
                getFreightTableCriteriasByChargeCd(con, freightTableData.getFreightTableId(), chargeCd);
            if (intervals == null) {
                return chargeAmt;
            }

            if (freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS) ||
                    freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE)) {
                BigDecimal val = new BigDecimal(0);
                for (int ii = 0; ii < intervals.size(); ii++) {
                    FreightTableCriteriaData interval = (FreightTableCriteriaData) intervals.get(ii);
                    BigDecimal lowerAmt = interval.getLowerAmount();
                    BigDecimal higherAmt = interval.getHigherAmount();
                    logDebug("getFreightAmt, lowerAmt=" + lowerAmt + ", higherAmt=" + higherAmt);

                    if (lowerAmt == null) {
                        continue;
                    }
                    if (lowerAmt.compareTo(pAmount) <= 0 &&
                            (higherAmt == null || higherAmt.doubleValue() < 0.001 || higherAmt.compareTo(pAmount) >= 0)) {
                        val = interval.getHandlingAmount();
                        if (val == null) {
                            val = new BigDecimal(0);
                        }
                        break;
                    }
                }
                if (freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS)) {
                    chargeAmt = val;
                } else {
                    val = val.movePointLeft(2);
                    chargeAmt = pAmount.multiply(val);
                }
            }

            // freight by weight
            else if (freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME) ||
                    freightType.equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND)) {

                BigDecimal orderWeight = getOrderWeight(con, pOrder, freightType);
                for (int ii = 0; ii < intervals.size(); ii++) {
                    FreightTableCriteriaData interval = (FreightTableCriteriaData) intervals.get(ii);
                    BigDecimal lowerWeight = interval.getLowerAmount();
                    BigDecimal higherWeight = interval.getHigherAmount();
                    logDebug("lowerWeight, lowerAmt=" + lowerWeight + ", higherWeight=" + higherWeight);
                    if (lowerWeight == null) {
                        lowerWeight = new BigDecimal(0);
                    }
                    if (lowerWeight.compareTo(orderWeight) <= 0 &&
                            (higherWeight == null || higherWeight.doubleValue() < 0.001 || higherWeight.compareTo(orderWeight) >= 0)) {
                        chargeAmt = interval.getFreightAmount();
                        if (chargeAmt == null) {
                            chargeAmt = new BigDecimal(0);
                        }
                        //break;
                    }
                }
            } else {
                throw new RemoteException("getChargeAmt(). Unknown freight table type: " + freightType);
            }

        } catch (NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("getChargeAmt() Naming Exception happened");
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("getChargeAmt() SQL Exception happened");
        } finally {
            closeConnection(con);
        }

        return chargeAmt;
    }

    private static int getParValueFromSiteInventoryConfigView(SiteInventoryConfigView sicv, int currInvPeriod) {
        if (sicv == null) {
            return 0;
        } else {
            return Utility.getIntValueNN(sicv.getParValues(), currInvPeriod);
        }
    }

    private static ShoppingInfoData generateAutoDistroShoppingInfo(
        Connection conn, String newQuantity, String oldQuantity,
            int siteId, int itemId, int orderId, String userName)
                throws SQLException {

        if (conn == null || newQuantity == null || oldQuantity == null || userName == null) {
            return null;
        }
        if (newQuantity.equals(oldQuantity)) {
            return null;
        }
        String desc = "";
        String key = null;
        String arg0 = null;
        String arg0TypeCd = null;
        String arg1 = null;
        String arg1TypeCd = null;
        String arg2 = null;
        String arg2TypeCd = null;

        ShoppingInfoData sinfo = ShoppingInfoData.createValue();
        sinfo.setSiteId(siteId);
        if (orderId > 0) {
            sinfo.setOrderId(orderId);
        }
        sinfo.setAddBy(userName);
        sinfo.setModBy(userName);
        sinfo.setItemId(itemId);

        String nothing = ShoppingDAO.translateMessage(conn,
            "shoppingMessages.text.noThing", null, null, null, null);

        if (oldQuantity.trim().length() == 0 || oldQuantity.trim().equals("0")) {
            key = "shoppingMessages.text.distroQtySet";
            arg0 = newQuantity;
            arg0 = arg0.trim().length() == 0 ? nothing : arg0;
            arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;
            desc = ShoppingDAO.translateMessage(conn, key, arg0, null, null, null);
        }
        else {
            key = "shoppingMessages.text.distroQtyModified";
            arg0 = oldQuantity;
            arg0 = arg0.trim().length() == 0 ? nothing : arg0;
            arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;
            arg1 = newQuantity;
            arg1 = arg1.trim().length() == 0 ? nothing : arg1;
            arg1TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;
            desc = ShoppingDAO.translateMessage(conn, key, arg0, arg1, null, null);
        }

        if (desc == null) {
            return null;
        }

        sinfo.setShortDesc(ShoppingCartData.AUTO_DISTRO_CART_ITEM_UPDATE);
        sinfo.setValue(desc);
        if (key != null) {
            sinfo.setMessageKey(key);
        }
        if (arg0 != null) {
            sinfo.setArg0(arg0);
            sinfo.setArg0TypeCd(arg0TypeCd);
        }
        if (arg1 != null) {
            sinfo.setArg1(arg1);
            sinfo.setArg1TypeCd(arg1TypeCd);
        }
        sinfo.setShoppingInfoStatusCd(RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE);
        return sinfo;
    }

    public void saveAutoDistroInvShopHistory(UserData user, int siteId,
        int currInvPeriod, ShoppingCartItemDataVector cartItems)
            throws RemoteException {
          saveAutoDistroInvShopHistory( user,  siteId, currInvPeriod, cartItems, null);
    }
    public void saveAutoDistroInvShopHistory(UserData user, int siteId,
        int currInvPeriod, ShoppingCartItemDataVector cartItems, AccCategoryToCostCenterView pCategToCostCenterView)
            throws RemoteException {

        if (user == null) {
            return;
        }
        if (cartItems == null) {
            return;
        }
        Connection conn = null;
        String newQuantity = null;
        String oldQuantity = null;
        try {
            conn = getConnection();
            Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
            SiteInventoryConfigViewVector oldConfigs =
                siteEjb.lookupInventoryConfig(siteId, false, pCategToCostCenterView);

            for (int i = 0; i < cartItems.size(); ++i) {
                ShoppingCartItemData scid = (ShoppingCartItemData) cartItems.get(i);
                newQuantity = Utility.strNN(scid.getQuantityString());
                for (int j = 0; j < oldConfigs.size(); ++j) {
                    SiteInventoryConfigView sicv = (SiteInventoryConfigView) oldConfigs.get(j);
                    if (scid.getItemId() == sicv.getItemId()) {
                        oldQuantity = "" + getParValueFromSiteInventoryConfigView(sicv, currInvPeriod);
                        ShoppingInfoData shopInfo = generateAutoDistroShoppingInfo(conn,
                            newQuantity, oldQuantity, siteId, scid.getItemId(), 0, user.getUserName());
                        if (shopInfo != null) {
                            ShoppingInfoDataAccess.insert(conn, shopInfo);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            throw processException(ex);
        }
        finally {
            closeConnection(conn);
        }
    }







     public PriceRuleDescView updatePriceRuleDesc(PriceRuleDescView pPriceRule, String pUserName) throws RemoteException {
         Connection con = null;
         Date current = new Date();
         try {
             con = getConnection();
             PriceRuleData priceRuleD = pPriceRule.getPriceRule();

             priceRuleD.setModBy(pUserName);
             priceRuleD.setModDate(current);

             if (priceRuleD.getPriceRuleId() == 0) {
                priceRuleD.setAddBy(pUserName);
                priceRuleD.setAddDate(current);
                priceRuleD = PriceRuleDataAccess.insert(con, priceRuleD);
             }  else {
                PriceRuleDataAccess.update(con, priceRuleD);
             }

             PriceRuleDetailDataVector details = pPriceRule.getPriceRuleDetails();
             Iterator i = details.iterator();
             while (i.hasNext()) {
                PriceRuleDetailData detail = (PriceRuleDetailData)i.next();

                detail.setModBy(pUserName);
                detail.setModDate(current);
                if (detail.getPriceRuleDetailId() == 0) {
                    detail.setAddBy(pUserName);
                    detail.setAddDate(current);
                    detail.setPriceRuleId(priceRuleD.getPriceRuleId());
                    detail.setBusEntityId(priceRuleD.getBusEntityId());

                    detail = PriceRuleDetailDataAccess.insert(con, detail);
                } else {
                    PriceRuleDetailDataAccess.update(con, detail);
                }
             }

         } catch (Exception ex) {
             throw processException(ex);
         } finally {
             closeConnection(con);
         }

         return pPriceRule;
     }


    public PriceRuleDescViewVector getServiceFeeDescVector(int pBusEntityId) throws RemoteException {
        PriceRuleDescViewVector result = new PriceRuleDescViewVector();
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PriceRuleDataAccess.BUS_ENTITY_ID, pBusEntityId);
            dbc.addEqualTo(PriceRuleDataAccess.SHORT_DESC, "ServiceFee");

            PriceRuleDataVector priceRuleDataV = PriceRuleDataAccess.select(con, dbc);

            Iterator i = priceRuleDataV.iterator();
            while (i.hasNext()) {
                PriceRuleData priceRuleD = (PriceRuleData)i.next();
                dbc = new DBCriteria();
                dbc.addEqualTo(PriceRuleDetailDataAccess.PRICE_RULE_ID, priceRuleD.getPriceRuleId());
                PriceRuleDetailDataVector details = PriceRuleDetailDataAccess.select(con, dbc);

                PriceRuleDescView priceRuleView = new PriceRuleDescView();
                priceRuleView.setPriceRule(priceRuleD);
                priceRuleView.setPriceRuleDetails(details);
                result.add(priceRuleView);
            }
        } catch (Exception ex) {
            throw processException(ex);
        } finally {
            closeConnection(con);
        }

        return result;
    }

    public ArrayList getAllServiceFeeCodesForStore(int pStoreId) throws RemoteException{
    	ArrayList allCodes = new ArrayList();
    	Connection con = null;
    	try{
    		con = getConnection();
    		DBCriteria dbc = new DBCriteria();
    		IdVector acctIds = new IdVector();
    		dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
        	dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
        			RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        	acctIds = BusEntityAssocDataAccess.selectIdOnly(con,
        			BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

        	for(int i=0; i<acctIds.size(); i++){
        		int acctId = ((Integer)acctIds.get(i)).intValue();

        		PriceRuleDescViewVector priceRuleVec = getServiceFeeDescVector(acctId);
        		for(int j=0; j<priceRuleVec.size(); j++){
        			PriceRuleDescView pRuleDesc = (PriceRuleDescView)priceRuleVec.get(j);
        			PriceRuleDetailDataVector pRuleDetailV = pRuleDesc.getPriceRuleDetails();
        			for(int k=0; k<pRuleDetailV.size(); k++){
        				PriceRuleDetailData pRuleDD = (PriceRuleDetailData)pRuleDetailV.get(k);
        				if(pRuleDD.getParamName().equalsIgnoreCase(
        						RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_ITEM_NUM)){
        					if(!allCodes.contains(pRuleDD.getParamValue())){
        						allCodes.add(pRuleDD.getParamValue());
        					}
        				}
        			}

        		}
        	}

    	} catch (Exception ex) {
            throw processException(ex);
        } finally {
            closeConnection(con);
        }
        return allCodes;
    }

    private FreightTableCriteriaDataVector getFreightTableCriteriasByChargeCd(Connection connection, int freightTableId, String chargeCd
            ) throws RemoteException {
        return getFreightTableCriteriasByChargeCd(connection, freightTableId, chargeCd, false, false);
    }

    private FreightTableCriteriaDataVector getFreightTableCriteriasByChargeCd(Connection connection, int freightTableId, String chargeCd,
            boolean pFreightAmtIsNotNull, boolean pHandlingAmtIsNotNull)
        throws RemoteException {
        FreightTableCriteriaDataVector freightCriteriaVector = new FreightTableCriteriaDataVector();
        try {
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(FreightTableCriteriaDataAccess.FREIGHT_TABLE_ID, freightTableId);
            if (chargeCd != null && chargeCd.length() > 0) {
                crit.addEqualTo(FreightTableCriteriaDataAccess.CHARGE_CD, chargeCd);
            } else {
                crit.addIsNull(FreightTableCriteriaDataAccess.CHARGE_CD);
            }
            if (pFreightAmtIsNotNull == true) {
                crit.addIsNotNull(FreightTableCriteriaDataAccess.FREIGHT_AMOUNT);
            }
            if (pHandlingAmtIsNotNull == true) {
                crit.addIsNotNull(FreightTableCriteriaDataAccess.HANDLING_AMOUNT);
            }
            crit.addOrderBy(FreightTableCriteriaDataAccess.UI_ORDER);
            crit.addOrderBy(FreightTableCriteriaDataAccess.SHORT_DESC);
            freightCriteriaVector = FreightTableCriteriaDataAccess.select(connection, crit);
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        return freightCriteriaVector;
    }

    private FreightTableData getFreightTableByContract(Connection connection, int contractId)
        throws RemoteException {
        FreightTableData freightTable = null;
        try {
            String sql =
                "SELECT " +
                    "c.FREIGHT_TABLE_ID " +
                "FROM " +
                    "CLW_FREIGHT_TABLE ft INNER JOIN CLW_CONTRACT c ON c.FREIGHT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
                "WHERE " +
                    "c.CONTRACT_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                    "AND c.CONTRACT_ID = ? " +
                    "AND (ft.DISTRIBUTOR_ID = 0 OR ft.DISTRIBUTOR_ID IS NULL) ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
            stmt.setInt(2, contractId);
            ResultSet resSet = stmt.executeQuery();
            int contractFreightTableCount = 0;
            int contractFreightTableId = 0;
            while (resSet.next()) {
                contractFreightTableId = resSet.getInt("FREIGHT_TABLE_ID");
                contractFreightTableCount++;
            }
            resSet.close();
            stmt.close();
            ///
            if (contractFreightTableId == 0) {
                return null;
            }
            ///
            if (contractFreightTableCount > 1) {
                throw new RemoteException("Multiple freight tables for contract id " + contractId);
            }
            ///
            freightTable = FreightTableDataAccess.select(connection, contractFreightTableId);
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        return freightTable;
    }

    private FreightTableData getDiscountTableByContract(Connection connection, int contractId)
        throws RemoteException {
        FreightTableData freightTable = null;
        try {
            String sql =
                "SELECT " +
                    "c.DISCOUNT_TABLE_ID " +
                "FROM " +
                    "CLW_FREIGHT_TABLE ft INNER JOIN CLW_CONTRACT c ON c.DISCOUNT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
                "WHERE " +
                    "c.CONTRACT_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                    "AND c.CONTRACT_ID = ? " +
                    "AND (ft.DISTRIBUTOR_ID = 0 OR ft.DISTRIBUTOR_ID IS NULL) ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT);
            stmt.setInt(2, contractId);
            ResultSet resSet = stmt.executeQuery();
            int contractFreightTableCount = 0;
            int contractFreightTableId = 0;
            while (resSet.next()) {
                contractFreightTableId = resSet.getInt("DISCOUNT_TABLE_ID");
                contractFreightTableCount++;
            }
            resSet.close();
            stmt.close();
            ///
            if (contractFreightTableId == 0) {
                return null;
            }
            ///
            if (contractFreightTableCount > 1) {
                throw new RemoteException("Multiple discount tables for contract id " + contractId);
            }
            ///
            freightTable = FreightTableDataAccess.select(connection, contractFreightTableId);
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        return freightTable;
    }
    public ProductDataVector getTopCatalogProducts(int pSiteId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException {
      return getTopCatalogProducts( pSiteId,  pShoppingItemRequest, null);
    }

    public ProductDataVector getTopCatalogProducts(int pSiteId, ShoppingItemRequest pShoppingItemRequest, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException {

        Connection con = null;
        ProductDataVector productDV = new ProductDataVector();

        try {

            con = getConnection();

            IdVector productIds = ShoppingDAO.getShoppingItemIds(con, pShoppingItemRequest);

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, productIds);
            dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
            dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD,
                    Utility.getAsList(
                            RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY,
                            RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT));
            dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

            IdVector subProductIds = ItemAssocDataAccess.selectIdOnly(con, ItemAssocDataAccess.ITEM1_ID, dbc);

            int subSize = subProductIds.size();
            IdVector topProductIds = null;
            if (subSize == 0) {
                topProductIds = productIds;
            } else {
                topProductIds = new IdVector();
                int ii = 0;
                int jj = 0;
                Integer prodId;
                Integer subProdId;
                for (; ii < productIds.size(); ii++) {
                    prodId = (Integer) productIds.get(ii);
                    if (jj == subSize) {
                        topProductIds.add(prodId);
                    } else {
                        subProdId = (Integer) subProductIds.get(jj);
                        if (prodId.equals(subProdId)) {
                            jj++;
                        } else if (prodId.compareTo(subProdId) < 0) {
                            topProductIds.add(prodId);
                        } else if (prodId.compareTo(subProdId) > 0) {
                            throw new RemoteException("Error. ShoppingServicesBean.getTopCatalogProducts() Program logic error");
                        }
                    }
                }
            }

            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID, topProductIds);
            ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

            ProductDAO tpdao = new ProductDAO(con, itemDV);
            tpdao.updateCatalogInfo(con, pShoppingItemRequest.getShoppingCatalogId(), pSiteId, pCategToCostCenterView);

            productDV = tpdao.getResultVector();

        } catch (Exception exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("ShoppingServicesBean.getTopCatalogProducts: Exception");
        } finally {
            closeConnection(con);
        }

        return productDV;
    }

    public ProductDataVector getCatalogChildProducts(int pSiteId,
                                                     int pParentId,
                                                     ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException {
      return getCatalogChildProducts( pSiteId, pParentId, pShoppingItemRequest, null);

    }
    public ProductDataVector getCatalogChildProducts(int pSiteId,
                                                     int pParentId,
                                                     ShoppingItemRequest pShoppingItemRequest,
                                                     AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException {
        Connection con = null;
        ProductDataVector productDV = new ProductDataVector();
        try {
            con = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
            dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pParentId);
            dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, Utility.getAsList(
                    RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY,
                    RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT));

            String subProductReq = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM1_ID, dbc);

            String shoppingReq = ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest);

            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID, subProductReq);
            dbc.addOneOf(ItemDataAccess.ITEM_ID, shoppingReq);
            dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

            IdVector itemIds = ItemDataAccess.selectIdOnly(con, dbc);

            //---- STJ-6114: Performance Improvements - Optimize Pollock 
	        log.info("getCatalogChildProducts() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
	        ShoppingDAO.filterByProductBundle(con, itemIds, pShoppingItemRequest);
	    	log.info("getCatalogChildProducts() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
	        //----

            
            log.info("getCatalogChildProducts()=> pCatalogId: " + pShoppingItemRequest.getShoppingCatalogId() +
                    " thisCatIds: " + itemIds +
                    " siteId: " + pSiteId);

            ProductDAO tpdao = new ProductDAO(con, itemIds);
            if (pShoppingItemRequest.getShoppingCatalogId() > 0) {
                log.info("getCatalogChildProducts()=> catalog id > 0");
                tpdao.updateCatalogInfo(con, pShoppingItemRequest.getShoppingCatalogId(), pSiteId, pCategToCostCenterView);
            }

            productDV = tpdao.getResultVector();


        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: Naming Exception happened");
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: SQL Exception happened");
        } finally {
            closeConnection(con);
        }

        return productDV;

    }

    public IdVector getSpecialPermssionItemIds(int pAccounCatalogId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return ShoppingDAO.getSpecialPermssionItemIds(con, pAccounCatalogId);
        } catch (Exception ex) {
            throw processException(ex);
        } finally {
            closeConnection(con);
        }
    }

    public ProductDataVector getCategoryChildProducts(int pCategoryId, IdVector pAvailableCategoryIds, ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        return getCategoryChildProducts(0, pCategoryId, pAvailableCategoryIds, pShoppingItemRequest);
    }

    public ProductDataVector getCategoryChildProducts(int pCategoryId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        return getCategoryChildProducts(0, pCategoryId, null, pShoppingItemRequest);
    }

    public ProductDataVector getCategoryChildProducts(int pSiteId, int pCategoryId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        return getCategoryChildProducts(0, pCategoryId, null, pShoppingItemRequest);
    }

    public ProductDataVector getCategoryChildProducts(int pSiteId,
                                                      int pCategoryId,
                                                      IdVector pAvailableCategoryIds,
                                                      ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
      return getCategoryChildProducts(pSiteId, pCategoryId, pAvailableCategoryIds,pShoppingItemRequest, null);

    }
    
    /*
     * get category child product without using recusive call
     */
    public ProductDataVector getCategoryChildProducts(int pStoreCatalogId,
    												  int pSiteId,
                                                      int pCategoryId,
                                                      ShoppingItemRequest pShoppingItemRequest,
                                                      AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

    	ProductDataVector products = new ProductDataVector();

    	Connection con = null;
    	PreparedStatement pstmt;
    	try {
    		con = getConnection();
    		String sqlCategories = "select " + pCategoryId + " from dual " +
                    "union " +
                    "select ITEM1_ID CHILD from " +
                    "(select ITEM2_ID,ITEM1_ID, ITEM_ASSOC_ID " +
                    "from CLW_ITEM_ASSOC " +
                    "where CATALOG_ID = ? " + // store catalog id
                    "and ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY') a " +
                    "connect by PRIOR ITEM1_ID = ITEM2_ID " +
                    "start with ITEM2_ID = ? ";
            String sql = "SELECT i.ITEM_ID FROM CLW_ITEM i, CLW_ITEM_ASSOC ia, CLW_CATALOG_STRUCTURE cs " +
                    "WHERE i.ITEM_ID  = ia.ITEM1_ID " +
                    "AND ia.CATALOG_ID = " + pShoppingItemRequest.getShoppingCatalogId() + " " +
                    "AND ia.ITEM2_ID in (" + sqlCategories  + ") " +
                    "AND ia.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY' " +
                    "and i.ITEM_ID = cs.ITEM_ID " +
                    "AND cs.CATALOG_ID = ia.CATALOG_ID " +
                    "AND cs.ITEM_ID IN (" + ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest) + ") " +
                    "AND cs.CATALOG_STRUCTURE_CD = 'CATALOG_PRODUCT' " +
                    "ORDER BY i.ITEM_ID ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, pStoreCatalogId);
            pstmt.setInt(2, pCategoryId);
            IdVector itemIds = new IdVector();
    		ResultSet rs = pstmt.executeQuery();
//<<<<<< STJ-6114: Performance Improvements - Optimize Pollock Beg
/*
    		while (rs.next()){    			
    			itemIds.add(new Integer(rs.getInt(1)));
    		}
*/			
			IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
			IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
			int exclItemId = 0;
			int inclItemId = 0;
			Iterator iterExcl = null;
			if(excludeItemIds!=null) iterExcl = excludeItemIds.iterator();
			Iterator iterIncl = null;
			if(includeItemIds!=null) iterIncl = includeItemIds.iterator();
			while (rs.next()){
				int itemId = rs.getInt("item_id");
//logInfo("getCategoryChildProducts()=> trying item" + itemId );
				while(iterExcl!=null && iterExcl.hasNext()) {
					if(exclItemId < itemId) {
						exclItemId = (Integer) iterExcl.next();
	//logInfo("getOrderGuideItems()=> exclude item" + exclItemId );
					} else {
						break;
					}
				}
				if(itemId == exclItemId) {
					logInfo("getCategoryChildProducts()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping proprietary itemId = " + itemId );
					continue;
				}
				if(iterIncl!=null) {
					while(iterIncl.hasNext()) {
						if(inclItemId < itemId) {
							inclItemId = (Integer) iterIncl.next();				
	//logInfo("getOrderGuideItems()=> must item" + inclItemId );
						} else {
							break;
						}
					}
					if(itemId != inclItemId) {
						logInfo("getCategoryChildProducts()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping not price list itemId = " + itemId );
						continue;
					}
				}
//logInfo("getCategoryChildProducts()=> item accepted" + itemId );
				itemIds.add(itemId);
			}
//>>>>>> STJ-6114: Performance Improvements - Optimize Pollock END

    		
    		if (!itemIds.isEmpty()){
    			int catalogId = pShoppingItemRequest.getShoppingCatalogId();
    			log.info("getCatalogChildProducts()=> pCatalogId: " + catalogId +
    					" thisCatIds: " + itemIds +
    					" siteId: " + pSiteId);

    			ProductDAO tpdao = new ProductDAO(con, itemIds);
    			if (catalogId > 0) {
    				log.info("getCatalogChildProducts()=> catalog id > 0");
    				tpdao.updateCatalogInfo(con, catalogId, pSiteId, pCategToCostCenterView);
    			}
    			products = tpdao.getResultVector();
    		}
    		pstmt.close();
            return products;
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: Naming Exception happened");
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: SQL Exception happened");
        } catch (Exception e) {
        	logError("exc.getMessage");
            e.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: Exception happened");
		} finally {
            closeConnection(con);
        }
    }

    public ProductDataVector getCategoryChildProducts(int pSiteId,
                                                      int pCategoryId,
                                                      IdVector pAvailableCategoryIds,
                                                      ShoppingItemRequest pShoppingItemRequest,
                                                      AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

    	ProductDataVector products = new ProductDataVector();

    	Connection con = null;
    	PreparedStatement pstmt;
    	try {
    		con = getConnection();
            String sql = "SELECT i.ITEM_ID FROM CLW_ITEM i, CLW_ITEM_ASSOC ia, CLW_CATALOG_STRUCTURE cs " +
                    "WHERE i.ITEM_ID  = ia.ITEM1_ID " +
                    "AND ia.CATALOG_ID = " + pShoppingItemRequest.getShoppingCatalogId() + " " +
                    "AND ia.ITEM2_ID = ? " +
                    "AND ia.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY' " +
                    "and i.ITEM_ID = cs.ITEM_ID " +
                    "AND cs.CATALOG_ID = ia.CATALOG_ID " +
                    "AND cs.ITEM_ID IN (" + ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest) + ") " +
                    "AND cs.CATALOG_STRUCTURE_CD = 'CATALOG_PRODUCT' " +
                    "ORDER BY i.SHORT_DESC ASC";
            pstmt = con.prepareStatement(sql);
            products = getCategoryChildProducts(pSiteId, pCategoryId, pAvailableCategoryIds, pShoppingItemRequest, con, pstmt,pCategToCostCenterView );
            pstmt.close();
            return products;
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: Naming Exception happened");
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: SQL Exception happened");
        } catch (Exception e) {
        	logError("exc.getMessage");
            e.printStackTrace();
            throw new RemoteException("getCatalogChildProducts: Exception happened");
		} finally {
            closeConnection(con);
        }
    }

    private ProductDataVector getCategoryChildProducts(int pSiteId,
    		int pCategoryId,
    		IdVector pAvailableCategoryIds,
    		ShoppingItemRequest pShoppingItemRequest,
    		Connection con, PreparedStatement pstmt,
                AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {
    	ProductDataVector products = new ProductDataVector();
    	int catalogId = pShoppingItemRequest.getShoppingCatalogId();
    	IdVector itemIds = new IdVector();
		pstmt.setInt(1, pCategoryId);
		ResultSet rs = pstmt.executeQuery();
		//<<<<<< STJ-6114: Performance Improvements - Optimize Pollock Beg
		/*
		    		while (rs.next()){    			
		    			itemIds.add(new Integer(rs.getInt(1)));
		    		}
		*/			
		IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
		IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
		int exclItemId = 0;
		int inclItemId = 0;
		Iterator iterExcl = null;
		if(excludeItemIds!=null) iterExcl = excludeItemIds.iterator();
		Iterator iterIncl = null;
		if(includeItemIds!=null) iterIncl = includeItemIds.iterator();
		while (rs.next()){
			int itemId = rs.getInt("item_id");
//			logInfo("getCategoryChildProducts()=> trying item" + itemId );
			while(iterExcl!=null && iterExcl.hasNext()) {
				if(exclItemId < itemId) {
					exclItemId = (Integer) iterExcl.next();
				} else {
					break;
				}
			}
			if(itemId == exclItemId) {
				logInfo("getCategoryChildProducts()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping proprietary itemId = " + itemId );
				continue;
			}
			if(iterIncl!=null) {
				while(iterIncl.hasNext()) {
					if(inclItemId < itemId) {
						inclItemId = (Integer) iterIncl.next();				
					} else {
						break;
					}
				}
				if(itemId != inclItemId) {
					logInfo("getCategoryChildProducts()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping not price list itemId = " + itemId );
					continue;
				}
			}
//			logInfo("getCategoryChildProducts()=> item accepted" + itemId );
			itemIds.add(itemId);
		}
		//>>>>>> STJ-6114: Performance Improvements - Optimize Pollock END

		
		
		if (!itemIds.isEmpty()){
			log.info("getCatalogChildProducts()=> pCatalogId: " + catalogId +
					" thisCatIds: " + itemIds +
					" siteId: " + pSiteId);

			ProductDAO tpdao = new ProductDAO(con, itemIds);
			if (catalogId > 0) {
				log.info("getCatalogChildProducts()=> catalog id > 0");
				tpdao.updateCatalogInfo(con, catalogId, pSiteId, pCategToCostCenterView);
			}
			products = tpdao.getResultVector();
		}

    	if (pAvailableCategoryIds == null) {
    		pAvailableCategoryIds = Utility.toIdVector(getCatalogCategories(pShoppingItemRequest));
    	}

    	List childCatalogCats = Utility.filter(
    			getChildCategories(catalogId, pCategoryId),
    			pAvailableCategoryIds);

    	if (!childCatalogCats.isEmpty()) {
    		for (Object oChildCatalogCat : childCatalogCats) {
    			products.addAll(getCategoryChildProducts(pSiteId,
    					((CatalogCategoryData) oChildCatalogCat).getCatalogCategoryId(),
    					pAvailableCategoryIds,
    					pShoppingItemRequest, con, pstmt,pCategToCostCenterView));
    		}
    	}
    	return products;
    }

    public CatalogCategoryDataVector getChildCategories(int pCatalogId, int pParentId) throws RemoteException {

        Connection con = null;
        CatalogCategoryDataVector catalogCategoryDV = new CatalogCategoryDataVector();

        try {
            con = getConnection();

            DBCriteria dbc = new DBCriteria();

            dbc.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                    CatalogStructureDataAccess.CATALOG_ID,
                    pCatalogId);

            dbc.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                    CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            dbc.addJoinCondition(ItemAssocDataAccess.ITEM1_ID,
                    CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                    CatalogStructureDataAccess.ITEM_ID);

            dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pParentId);
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

            ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);

            IdVector categoryIds = new IdVector();
            Iterator it = itemAssocDV.iterator();
            while (it.hasNext()) {
                categoryIds.add(new Integer(((ItemAssocData) it.next()).getItem1Id()));
            }


            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID, categoryIds);
            dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
            dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

            ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

            for (int i = 0; i < itemDV.size(); i++) {
                ItemData itemD = (ItemData) itemDV.get(i);
                CatalogCategoryData ccD = new CatalogCategoryData();
                ccD.setItemData(itemD);
                catalogCategoryDV.add(ccD);
            }

            assignMajorCategory(con, catalogCategoryDV);

        } catch (Exception exc) {
            processException(exc);
        } finally {
            closeConnection(con);
        }

        return catalogCategoryDV;

    }

    private void assignMajorCategory(Connection pCon, CatalogCategoryDataVector pCategories) throws SQLException {


        IdVector catIdV = new IdVector();
        Iterator iter = pCategories.iterator();
        while (iter.hasNext()) {
            CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
            catIdV.add(new Integer(ccD.getItemData().getItemId()));
        }

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, catIdV);
        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

        String majorAssocReq = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID, dbc);

        dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

        ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(pCon, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, majorAssocReq);
        dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);
        dbc.addOrderBy(ItemDataAccess.ITEM_ID);

        ItemDataVector majorCatDV = ItemDataAccess.select(pCon, dbc);

        iter = pCategories.iterator();
        while (iter.hasNext()) {
            CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
            int cId = ccD.getItemData().getItemId();
            Iterator iter1 = itemAssocDV.iterator();
            int majCatId = 0;
            mmm:
            while (iter1.hasNext()) {
                ItemAssocData iaD = (ItemAssocData) iter1.next();
                int acId = iaD.getItem1Id();
                if (acId > cId) {
                    break;
                }
                if (acId == cId) {
                    int amId = iaD.getItem2Id();
                    Iterator iter2 = majorCatDV.iterator();
                    while (iter2.hasNext()) {
                        ItemData iD = (ItemData) iter2.next();
                        int mId = iD.getItemId();
                        if (mId > amId) {
                            break mmm;
                        }
                        if (mId == amId) {
                            if (majCatId == 0) {
                                majCatId = mId;
                                ccD.setMajorCategory(iD);
                            } else if (majCatId != mId) {
                                ccD.setMajorCategory(null);
                                logError("Category has more than one major category. Category id = " + cId);
                                break mmm;
                            }
                        }
                    }
                }
            }
        }
    }

    public IdVector getAvailableItemIdsOnly(UserData pUser, SiteData pSite, IdVector pItemIds) {

        log.info("getAvailableItemIdsOnly()=> BEGIN");

        Connection con = null;

        try {

            con = getConnection();

            ShoppingItemRequest shoppingItemRequest = ShoppingDAO.createShoppingItemRequest(con, pUser, pSite);

            log.info("getAvailableItemIdsOnly()=> shoppingItemRequest: " + shoppingItemRequest);
            IdVector availabeShoppingItems = ShoppingDAO.getShoppingItemIds(con, shoppingItemRequest);

            Iterator it = pItemIds.iterator();
            while (it.hasNext()) {
                if (!availabeShoppingItems.contains(it.next())) {
                    it.remove();
                }
            }

        } catch (Exception exc) {
            processException(exc);
        } finally {
            closeConnection(con);
        }

        log.info("getAvailableItemIdsOnly()=> END.");

        return pItemIds;

    }

    public ProductBundle getProductBundle(String pStoreType, int pSiteId, ProductDataVector pSiteProducts) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return ShoppingDAO.getProductBundle(con, pStoreType, pSiteId, pSiteProducts);
        } catch (Exception exc) {
           throw processException(exc);
        } finally {
            closeConnection(con);
        }
    }
    public ProductBundle getProductBundle(String pStoreType, int pSiteId, int pCatalogId, IdVector pProductIds) throws RemoteException {
      return getProductBundle( pStoreType, pSiteId,  pCatalogId,  pProductIds, null);
    }
    public ProductBundle getProductBundle(String pStoreType, int pSiteId, int pCatalogId, IdVector pProductIds, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            ProductDAO dao = new ProductDAO(con, pProductIds);
            dao.updateCatalogInfo(con, pCatalogId, pSiteId, pCategToCostCenterView);
            return ShoppingDAO.getProductBundle(con, pStoreType, pSiteId, dao.getResultVector());
        } catch (Exception exc) {
           throw processException(exc);
        } finally {
            closeConnection(con);
        }
    }

    public BigDecimal getContractItemPrice(int pStoreId, int pSiteId, int pContractId, int pCatalogId, int pItemId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return ShoppingDAO.getContractItemPrice(con, pStoreId, pSiteId, pContractId, pCatalogId, pItemId);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
    }


    public String getProductBundleValue(int pSiteId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return ShoppingDAO.getProductBundleValue(con, pSiteId);
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
    }
    
    /**
     * prepares all the data needed to calculate the total amount in a shopping cart
     * pertaining to a cost center
     * @param con
     * @param pStoreType
     * @param pUser
     * @param pSite
     * @param pCatalogId
     * @param pContractId
     * @param pCategToCostCenterView
     * @return
     * @throws RemoteException
     */
    private  CostCenterCartData prepareShoppingCart(Connection con,
            String pStoreType,
                                         UserData pUser,
                                         SiteData pSite,
                                         int pCatalogId,
                                         int pContractId,
   AccCategoryToCostCenterView pCategToCostCenterView )
      throws RemoteException
  {
    	 ProductDataVector items = new ProductDataVector();
    	 CostCenterCartData costCenterCartData =new CostCenterCartData();
      try {

        ShoppingCartData shoppingCartD = new ShoppingCartData();
        shoppingCartD.setUser(pUser);
        shoppingCartD.setSite(pSite);

        if ( pContractId <= 0 ) {
            if ( pSite.getContractData() == null ) {
                logError ("CONTRACT MISSING pSite=" + pSite);
            } else {
                pContractId = pSite.getContractData().getContractId();
            }
        }

        shoppingCartD.setContractId(pContractId);
        shoppingCartD.setStoreType(pStoreType);
        if ( pCatalogId > 0 ) {
            shoppingCartD.setCatalogId(pCatalogId);
        } else {
            shoppingCartD.setCatalogId(pSite.getSiteCatalogId());
            pCatalogId = pSite.getSiteCatalogId();
        }

        int siteId = pSite.getBusEntity().getBusEntityId();
        String userName = pUser.getUserName();


       
        OrderGuideStructureDataVector ogsdv = null;


        int effectiveUserId = (pSite.hasInventoryShoppingOn()&&!pSite.hasModernInventoryShopping())?0:pUser.getUserId();
        OrderGuideData orderGuideD =
              ShoppingDAO.getCart(con, effectiveUserId,
                      pSite.getBusEntity().getBusEntityId(), pUser.getUserName());

        int id = orderGuideD.getOrderGuideId();

        id = orderGuideD.getOrderGuideId();
        shoppingCartD.setOrderGuideId(id);
        shoppingCartD.setModBy(orderGuideD.getModBy());
        shoppingCartD.setModDate(orderGuideD.getModDate());

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,id);

       ShoppingItemRequest shoppingItemRequest = null;
       String shoppingItemIds = null;

          if (pSite.isUseProductBundle()) {

              shoppingItemRequest = ShoppingDAO.createShoppingItemRequest(con,
                      pSite.getSiteId(),
                      pSite.getAccountId(),
                      pCatalogId,
                      pContractId,
                      pSite.getPriceListRank1Id(),
                      pSite.getPriceListRank2Id(),
                      pSite.getProprietaryPriceListIds(),
                      pSite.getAvailableTemplateOrderGuideIds(),
                      pSite.getProductBundle(),
                      pUser);
              shoppingItemIds = ShoppingDAO.getShoppingItemIdsRequest(con, shoppingItemRequest);
              dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, shoppingItemIds);
              ogsdv = OrderGuideStructureDataAccess.select(con, dbc);

          } else {

              DBCriteria dbc1 = new DBCriteria();
              dbc1.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
              dbc1.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
              String catalogItemsReq =  CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, dbc1);
              dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, catalogItemsReq);

              if (pUser.getUserRoleCd().indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY) >= 0) {
                  dbc1 = new DBCriteria();
                  dbc1.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
                  String contractItemsReq = ContractItemDataAccess.getSqlSelectIdOnly(ContractItemDataAccess.ITEM_ID, dbc1);
                  dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, contractItemsReq);
              }
              ogsdv = OrderGuideStructureDataAccess.select(con, dbc);
          }

          IdVector itemIds = new IdVector();
        for(Iterator iter=ogsdv.iterator(); iter.hasNext();) {
          OrderGuideStructureData ogsD = (OrderGuideStructureData)iter.next();
         
          itemIds.add(new Integer(ogsD.getItemId()));
        }

        InventoryLevelDataVector currentInventory = null;
        if (pSite.hasInventoryShoppingOn() == true &&
        !pSite.hasModernInventoryShopping() ) {

            // Add any inventory items.
            // if !pSite.isUseProductBundle()
            if (shoppingItemRequest == null || shoppingItemIds == null) {
                shoppingItemRequest = ShoppingDAO.createShoppingItemRequest(con,
                        pSite.getSiteId(),
                        pSite.getAccountId(),
                        pCatalogId,
                        pContractId,
                        pSite.getPriceListRank1Id(),
                        pSite.getPriceListRank2Id(),
                        pSite.getProprietaryPriceListIds(),
                        pSite.getAvailableTemplateOrderGuideIds(),
                        pSite.getProductBundle(),
                        pUser);
                shoppingItemIds = ShoppingDAO.getShoppingItemIdsRequest(con, shoppingItemRequest);
            }

            DBCriteria dbc1 = new DBCriteria();
            dbc1.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, siteId);
            dbc1.addIsNotNull(InventoryLevelDataAccess.QTY_ON_HAND);
            dbc1.addOneOf(InventoryLevelDataAccess.ITEM_ID, shoppingItemIds);

            currentInventory = InventoryLevelDataAccess.select(con,dbc1);
            for ( int c2 = 0; c2 < currentInventory.size(); c2++) {
                InventoryLevelData ild = (InventoryLevelData)currentInventory.get(c2);
                itemIds.add(new Integer(ild.getItemId()));
            }
        }
        //---- STJ-6114: Performance Improvements - Optimize Pollock 
        log.info("prepareShoppingCart() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
        ShoppingDAO.filterByProductBundle(con, itemIds, shoppingItemRequest);
    	log.info("prepareShoppingCart() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
        //----

          
              items = prepareShoppingCartItems(con, pCatalogId, pContractId, itemIds, pSite.getSiteId(), pCategToCostCenterView);
              
              
              
              costCenterCartData.setOrderGuidesdv(ogsdv);
              costCenterCartData.setProductDV(items);
              con.close();
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
      return costCenterCartData;
  }
    
    /** This method gets the items,
     * which is internally used by  getProductDataVector method 
     * @param pCon
     * @param pCatalogId
     * @param pContractId
     * @param pItems
     * @param siteId
     * @param pCategToCostCenterView
     * @return
     * @throws RemoteException
     */
    
    public  ProductDataVector  prepareShoppingCartItems(Connection pCon, int pCatalogId, int pContractId, List pItems,
            int siteId, AccCategoryToCostCenterView pCategToCostCenterView)
     throws RemoteException    {
    	ProductDataVector productDV = null;
			ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();
			if(pItems==null || pItems.size()==0){
			   return productDV;
			}
			try{
			
			   DBCriteria dbc;
			   String contractItemReq = null;
			   //prepare Ids
			   IdVector idList = new IdVector();
			   //Check type
			   Object itemO = pItems.get(0);
			   
			   if(itemO instanceof Integer){
			
			 	  productDV = getProducts(pCon,pCatalogId, pItems,siteId,pCategToCostCenterView);
			   }
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return productDV;
			   }
   
    
    /** Gets the data needed to calculate the shopping cart amount specific to a cost center  
    *  
    * @param storeType
    * @param user
    * @param site
    * @param siteCatalogId
    * @param contractId
    * @param accCategoryToCostCenterView
    * @return CostCenterCartData 
    */
    
    public CostCenterCartData getProductDataVector(String storeType, UserData user, SiteData site, int siteCatalogId, int contractId,AccCategoryToCostCenterView accCategoryToCostCenterView)
     throws RemoteException{
    	Connection conn =null;
    	try{
    		conn=getConnection();
    	return prepareShoppingCart(conn,storeType, user, site, siteCatalogId, contractId, accCategoryToCostCenterView);
    	}
    	catch(Exception e){
    		
    	}
    	return null;
    }
    //---- STJ-6114: Performance Improvements - Optimize Pollock 
    public void populateProductBundleFilter(ShoppingItemRequest pShoppingItemRequest)   throws RemoteException{
	   	Connection conn =null;
	   	try{
	   		conn=getConnection();
			ShoppingDAO.addProductBundleFilter(conn, pShoppingItemRequest);
	   	} catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }
    //------------

	public List<Integer> populateMSDSInformation(ShoppingCartItemDataVector shoppingCartItems, String pUserLocale, 
			  String pCountryCode, String pStoreLocale) throws SQLException, RemoteException {
  		Connection con = null;
  		try {
  			con = getConnection();
  			return ShoppingDAO.populateMSDSInformation(con, shoppingCartItems, pUserLocale, pCountryCode, pStoreLocale);
  		}
  		catch (Exception exc) {
  			exc.printStackTrace();
  			throw new RemoteException(exc.getMessage());
  		}
  		finally {
  			closeConnection(con);
  		}
	}

}