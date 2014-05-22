/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.OrderTotalException;
import com.cleanwise.service.api.util.BudgetRuleException;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.NumberFormat;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;
/**
 * Updates MSDS, SPEC and DED closets after with order data
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class UpdateDocCloset  implements OrderPipeline
{
    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton, 
                OrderPipelineActor pActor, 
                Connection pCon, 
                APIAccess pFactory) 
    throws PipelineException
    {
    try{
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      OrderData orderD = pBaton.getOrderData();
      int orderId = orderD.getOrderId();
      int userId = orderD.getUserId();
      int siteId = orderD.getSiteId();
      String userName = pBaton.getUserName();

      if(orderId<=0 || userId<=0 || siteId<=0) {
        return pBaton;
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,siteId);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, 
                                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
      String catReq = CatalogAssocDataAccess.
                    getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID,dbc);

      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, 
                                      RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                                     RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID,catReq);
      
      IdVector catalogIds = CatalogDataAccess.
                          selectIdOnly(pCon,CatalogDataAccess.CATALOG_ID,dbc);
      if(catalogIds.size()==0 || catalogIds.size()>1) {
         return pBaton;
      }
      int catalogId = ((Integer)catalogIds.get(0)).intValue();
      OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
      IdVector itemIds = new IdVector();
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        itemIds.add(new Integer(oiD.getItemId()));
      }
      
      //Store items for MSDS, SPEC and DED closet
      saveDocCloset(pCon, catalogId, userId, itemIds, "MSDS",
                          RefCodeNames.ORDER_GUIDE_TYPE_CD.MSDS_CLOSET, userName);
      saveDocCloset(pCon, catalogId, userId, itemIds,"SPEC", 
                          RefCodeNames.ORDER_GUIDE_TYPE_CD.SPEC_CLOSET, userName);
      saveDocCloset(pCon, catalogId, userId, itemIds, "DED",
                          RefCodeNames.ORDER_GUIDE_TYPE_CD.DED_CLOSET, userName);
      //Return
      return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    }
  //**************************************************************
  private void saveDocCloset(Connection pCon, int pCatalogId, 
                                   int pUserId, IdVector pItemIds,
                                   String pPropertyName,
                                   String pDocClosetType, 
                                   String pUser)
    {
     try{
       DBCriteria dbc;
       //Create clw_order_guide record if necessary
       dbc = new DBCriteria();
       dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
       dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
       dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, 
                           pDocClosetType);

       IdVector vect = OrderGuideDataAccess.selectIdOnly(pCon, dbc);
       int closetId = 0;

       if (vect.size() > 0) {
          Integer closetIdI = (Integer)vect.get(0);
          closetId = closetIdI.intValue();
       } else {
          OrderGuideData ogD = OrderGuideData.createValue();
          ogD.setCatalogId(pCatalogId);
          ogD.setUserId(pUserId);
          ogD.setShortDesc(pDocClosetType);
          ogD.setOrderGuideTypeCd(pDocClosetType);
          ogD.setAddBy(pUser);
          ogD.setModBy(pUser);
          ogD = OrderGuideDataAccess.insert(pCon, ogD);
          closetId = ogD.getOrderGuideId();
       }

       //Check, which items have documents of this type
       dbc = new DBCriteria();
       dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, pItemIds);
       dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, pPropertyName);
       dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);

       ItemMetaDataVector imDV = ItemMetaDataAccess.select(pCon, dbc);
       IdVector itemIds = new IdVector();

       for (int ii = 0; ii < imDV.size(); ii++) {
          ItemMetaData imD = (ItemMetaData)imDV.get(ii);
          String value = imD.getValue();
          if (value != null && value.trim().length() > 0) {
            itemIds.add(new Integer(imD.getItemId()));
          }
       }

       //Check, which items are already stored
       dbc = new DBCriteria();
       dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, 
                          closetId);
       dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemIds);
       dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

       IdVector storedIds = OrderGuideStructureDataAccess.selectIdOnly(
                                         pCon, 
                                         OrderGuideStructureDataAccess.ITEM_ID, 
                                         dbc);

       //Add if do not have yet
       for (int ii = 0, jj = 0; ii < itemIds.size(); ii++) {
         Integer itemIdI = (Integer)itemIds.get(ii);
         Integer storedIdI = new Integer(0);

         if (jj < storedIds.size()) {
            storedIdI = (Integer)storedIds.get(jj);
         }

         int compSign = itemIdI.compareTo(storedIdI);
         if (compSign == 0) {
           jj++;
         } else { //compSign<0 or storedId == 0
           OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();
           ogsD.setItemId(itemIdI.intValue());
           ogsD.setOrderGuideId(closetId);
           ogsD.setAddBy(pUser);
           ogsD.setModBy(pUser);
           ogsD.setQuantity(0);
           OrderGuideStructureDataAccess.insert(pCon, ogsD);
         }
       }
     }catch(SQLException exc) {
       exc.printStackTrace();
     }
  }
}
