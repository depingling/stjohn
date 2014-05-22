package com.cleanwise.service.api.session;

/**
 * Title:        OrderBean
 * Description:  Bean implementation for MsdsSpecs Session Bean
 * Purpose:      Provides access to the methods for msds & specs documents
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import java.util.GregorianCalendar;
import javax.ejb.*;
import java.rmi.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import javax.naming.NamingException;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBAccess;
import java.sql.Connection;
import java.sql.SQLException;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.dao.*;
import java.sql.*;
import org.apache.log4j.Category;

public class MsdsSpecsBean extends MsdsSpecsAPI
{
  /**
   *
   */
  public MsdsSpecsBean() {}

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
   * Adds item document information to user's document closet, if it was not there
   * @param pCatalogId the catalog identifier
   * @param pUserId the user identifier
   * @param pItemIds the vector of item identifiers
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @param pUser the user login name
   * @throws            RemoteException Required by EJB 1.0
   */
  public void addItemsToDocCloset(int pCatalogId, int pUserId, IdVector pItemIds, String pDocClosetType, String pUser)
      throws RemoteException
  {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc;
      //Select Item Property Name
      int propertySwitch = 0;
      String propertyName = "";
      if("MSDS".equals(pDocClosetType)) {
        propertySwitch = 1;
        propertyName = "MSDS";
      }
      else if("DED".equals(pDocClosetType)) {
        propertySwitch = 2;
        propertyName = "DED";
      }
      else if("SPEC".equals(pDocClosetType)) {
        propertySwitch = 3;
        propertyName = "SPEC";
      }
      else {
        throw new RemoteException("Unknown document type: "+pDocClosetType);
      }
      String docClosetType = pDocClosetType+"_CLOSET";

      //Create clw_order_guide record if necessary
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
      dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, docClosetType);
      IdVector vect = OrderGuideDataAccess.selectIdOnly(con,dbc);
      int closetId = 0;
      if(vect.size()>0) {
        Integer closetIdI = (Integer) vect.get(0);
        closetId = closetIdI.intValue();
      } else {
        OrderGuideData ogD = OrderGuideData.createValue();
        ogD.setCatalogId(pCatalogId);
        ogD.setUserId(pUserId);
        ogD.setShortDesc(pDocClosetType);
        ogD.setOrderGuideTypeCd(docClosetType);
        ogD.setAddBy(pUser);
        ogD.setModBy(pUser);
        ogD = OrderGuideDataAccess.insert(con,ogD);
        closetId = ogD.getOrderGuideId();
      }
      //Check, which items have documents of this type
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,pItemIds);
      dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,propertyName);
      dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);
      ItemMetaDataVector imDV = ItemMetaDataAccess.select(con,dbc);
      IdVector itemIds = new IdVector();
      for(int ii=0; ii<imDV.size(); ii++) {
        ItemMetaData imD = (ItemMetaData) imDV.get(ii);
        String value = imD.getValue();
        if(value!=null && value.trim().length()>0) {
          itemIds.add(new Integer(imD.getItemId()));
        }
      }
      //Check, which items are already stored
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,closetId);
      dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID,itemIds);
      dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);
      IdVector storedIds = OrderGuideStructureDataAccess.selectIdOnly(con,OrderGuideStructureDataAccess.ITEM_ID,dbc);
      //Add if do not have yet
      for(int ii=0,jj=0; ii<itemIds.size(); ii++) {
        Integer itemIdI = (Integer) itemIds.get(ii);
        Integer storedIdI = new Integer(0);
        if(jj<storedIds.size()) {
          storedIdI = (Integer) storedIds.get(jj);
        }
        int compSign = itemIdI.compareTo(storedIdI);
        if(compSign==0){
          jj++;
        } else { //compSign<0 or storedId == 0
          OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();
          ogsD.setItemId(itemIdI.intValue());
          ogsD.setOrderGuideId(closetId);
          ogsD.setAddBy(pUser);
          ogsD.setModBy(pUser);
          ogsD.setQuantity(0);
          OrderGuideStructureDataAccess.insert(con, ogsD);
        }
      }
    }catch (Exception exc) {
      throw processException(exc);
    }finally {
      closeConnection(con);
    }
  }
  //*******************************************************************************************
  /**
   * Removes item document information to user's document closet, if it was not there
   * @param pCatalogId the catalog identifier
   * @param pUserId the user identifier
   * @param pItemIds the vector of item identifiers
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @param pUser the user login name
   * @throws            RemoteException Required by EJB 1.0
   */
  public void removeItemsFromDocCloset(int pCatalogId, int pUserId, IdVector pItemIds, String pDocClosetType, String pUser)
      throws RemoteException
  {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc;
      //Select Item Property Name
      int propertySwitch = 0;
      String propertyName = "";
      if("MSDS".equals(pDocClosetType)) {
        propertySwitch = 1;
        propertyName = "MSDS";
      }
      else if("DED".equals(pDocClosetType)) {
        propertySwitch = 2;
        propertyName = "DED";
      }
      else if("SPEC".equals(pDocClosetType)) {
        propertySwitch = 3;
        propertyName = "SPEC";
      }
      else {
        throw new RemoteException("Unknown document type: "+pDocClosetType);
      }
      String docClosetType = pDocClosetType+"_CLOSET";

      //Find clw_order_guide record if necessary
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
      if ( pUserId > 0 )
      {
          dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
      }
      dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, docClosetType);
      IdVector vect = OrderGuideDataAccess.selectIdOnly(con,dbc);
      int closetId = 0;
      if(vect.size()>0) {
        Integer closetIdI = (Integer) vect.get(0);
        closetId = closetIdI.intValue();
        //Remove items are already stored
        dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,closetId);
        dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID,pItemIds);
        OrderGuideStructureDataAccess.remove(con,dbc);
      }
    }
    catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. MsdsSpecsBean.addItemsToDocCloset() Naming Exception happened. "+exc.getMessage());
    }
    catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. MsdsSpecsBean.addItemsToDocCloset() SQL Exception happened. "+exc.getMessage());
    }
    finally {
        closeConnection(con);
    }
  }
  //*******************************************************************************************
  /**
   * Gets items, which are in document closet
   * @param pCatalogId the catalog identifier
   * @param pUserId the user identifier
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @return item ids, which where stored in users's documdt closet
   * @throws            RemoteException Required by EJB 1.0
   */
  public IdVector getItemsDocCloset(int pCatalogId, int pUserId, String pDocClosetType)
      throws RemoteException
  {
    Connection con = null;
    IdVector itemIds = new IdVector();
    try {
      con = getConnection();
      DBCriteria dbc;
      //Select Item Property Name
      int propertySwitch = 0;
      String propertyName = "";
      if("MSDS".equals(pDocClosetType)) {
        propertySwitch = 1;
        propertyName = "MSDS";
      }
      else if("DED".equals(pDocClosetType)) {
        propertySwitch = 2;
        propertyName = "DED";
      }
      else if("SPEC".equals(pDocClosetType)) {
        propertySwitch = 3;
        propertyName = "SPEC";
      }
      else {
        throw new RemoteException("Unknown document type: "+pDocClosetType);
      }
      String docClosetType = pDocClosetType+"_CLOSET";

      //Create clw_order_guide record if necessary
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
      if ( pUserId > 0 )
      {
          dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
      }
      dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, docClosetType);
      IdVector vect = OrderGuideDataAccess.selectIdOnly(con,dbc);
      int closetId = 0;
      if(vect.size()>0) {
        Integer closetIdI = (Integer) vect.get(0);
        closetId = closetIdI.intValue();
        
        dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,closetId);
        dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);
        itemIds = OrderGuideStructureDataAccess.selectIdOnly(con,OrderGuideStructureDataAccess.ITEM_ID,dbc);
      }
    }
    catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. MsdsSpecsBean.addItemsToDocCloset() Naming Exception happened. "+exc.getMessage());
    }
    catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. MsdsSpecsBean.addItemsToDocCloset() SQL Exception happened. "+exc.getMessage());
    }
    finally {
        closeConnection(con);
      }
    
    return itemIds;
  }

  //*******************************************************************************************
  /**
   * Gets items, which were ordered during last year for the site and with have documents of the type
   * @param pSiteId the site identifier
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @return item ids
   * @throws            RemoteException Required by EJB 1.0
   */
  public IdVector getItemsDocCloset(int pSiteId, String pDocClosetType)
      throws RemoteException
  {
    Connection con = null;
    IdVector itemIds = new IdVector();
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderDataAccess.SITE_ID,pSiteId);
      GregorianCalendar gCal = new GregorianCalendar();
      Date endDate = gCal.getTime();
      gCal.add(GregorianCalendar.YEAR,-1);
      Date begDate = gCal.getTime();
      dbc.addGreaterOrEqual(OrderDataAccess.ADD_DATE,begDate);
      dbc.addLessOrEqual(OrderDataAccess.ADD_DATE,endDate);
      IdVector orderIdV = 
          OrderDataAccess.selectIdOnly(con,OrderDataAccess.ORDER_ID,dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(OrderItemDataAccess.ORDER_ID,orderIdV);
      IdVector itemIdV = 
           OrderItemDataAccess.selectIdOnly(con,OrderItemDataAccess.ITEM_ID,dbc);
      
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,pDocClosetType);
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,itemIdV);
      itemIds = ItemMetaDataAccess.selectIdOnly(con,ItemMetaDataAccess.ITEM_ID,dbc);
    }
    catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. MsdsSpecsBean.addItemsToDocCloset() Naming Exception happened. "+exc.getMessage());
    }
    catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. MsdsSpecsBean.addItemsToDocCloset() SQL Exception happened. "+exc.getMessage());
    }
    finally {
        closeConnection(con);
      }
    
    return itemIds;
  }
}
