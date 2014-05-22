package com.cleanwise.service.api.session;

/**
 * Title:        EstimationBean
 * Description:  Bean implementation for Estimation Stateless Session Bean
 * Purpose:      Product usage estimation tools
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;

import javax.naming.NamingException;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.APIAccess;
import java.sql.*;
import java.math.BigDecimal;

public class EstimationBean extends ApplicationServicesAPI
{

    /**
     *
     */
    public EstimationBean() {}

    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}

   public CleaningProcDataVector getCleaningProcedures() 
   throws RemoteException
   {
	 Connection con=null;
	 try {
       con = getConnection();
       DBCriteria dbc = new DBCriteria();
       dbc.addCondition("1=1");
       dbc.addOrderBy(CleaningProcDataAccess.ESTIMATOR_PAGE_CD);
       dbc.addOrderBy(CleaningProcDataAccess.SHORT_DESC);
       CleaningProcDataVector cleaningProcDV = 
           CleaningProcDataAccess.select(con,dbc);
       return cleaningProcDV;
     }catch(Exception exc) {
         logError(exc.getMessage());
         exc.printStackTrace();
         throw new RemoteException(exc.getMessage());
     }finally{
         try {
         con.close();
         }catch (SQLException exc) {
         logError(exc.getMessage());
         exc.printStackTrace();
         throw new RemoteException(exc.getMessage());
         }
     }
   }

   public ProdApplJoinViewVector getProcedureProducts(int pCleaningProcId) 
   throws RemoteException
   {
     Connection con=null;
	 try {
      con = getConnection();
      ProdApplJoinViewVector prodApplJoinVwV = new ProdApplJoinViewVector();
      DBCriteria dbc = new DBCriteria();
      int catalogId = 4; //Cleanwise store catalog id
      dbc = new DBCriteria();
      dbc.addEqualTo(ProdApplDataAccess.CLEANING_PROC_ID,pCleaningProcId);
      dbc.addIsNull(ProdApplDataAccess.ESTIMATOR_FACILITY_ID);
      ProdApplDataVector prodApplDV = ProdApplDataAccess.select(con,dbc);
      
      IdVector itemIdV = new IdVector();
      for(Iterator iter=prodApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        int itemId = paD.getItemId();
        itemIdV.add(new Integer(itemId));
      }
      
      
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,itemIdV);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(con,dbc);
      
      
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,itemIdV);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,catalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
         RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,dbc);
      
      IdVector categIdV = new IdVector();
      int categIdPrev = -1;
      for(Iterator iter=itemAssocDV.iterator(); iter.hasNext();) {
        ItemAssocData iaD = (ItemAssocData) iter.next();
        int categId = iaD.getItem2Id();
        if(categIdPrev!=categId) {
          categIdPrev = categId;
          categIdV.add(new Integer(categId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,categIdV);
      ItemDataVector categDV = ItemDataAccess.select(con,dbc);
      
      HashMap itemCategHM = new HashMap();
      ItemAssocData wrkItemAssocD = null;
      for(Iterator iter= itemDV.iterator(),
                   iter1 = itemAssocDV.iterator(); 
                             iter.hasNext();) {
        ItemData iD = (ItemData) iter.next();
        int itemId = iD.getItemId();
        while(wrkItemAssocD!=null || iter1.hasNext()) {
          if(wrkItemAssocD==null) wrkItemAssocD = (ItemAssocData) iter1.next();
          if(itemId>wrkItemAssocD.getItem1Id()) {
            wrkItemAssocD = null;
            continue;
          }
          if(itemId<wrkItemAssocD.getItem1Id()) {
            break;
          }
          int item2Id = wrkItemAssocD.getItem2Id();
          
          for(Iterator iter2 = categDV.iterator(); iter2.hasNext();) {
            ItemData categD = (ItemData) iter2.next();
            if(item2Id==categD.getItemId()) {
              String categName = categD.getShortDesc();
              if(categName!=null) {
                itemCategHM.put(new Integer(itemId),categName);
              }
              break;
            }
          }
          wrkItemAssocD = null;
          break;
        }
      }
      
      dbc = new DBCriteria();
      dbc.addOneOf(ProductUomPackDataAccess.ITEM_ID,itemIdV);
      ProductUomPackDataVector productUomPackDV = 
         ProductUomPackDataAccess.select(con,dbc);
      
      for(Iterator iter=prodApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        ProdApplJoinView prodApplJoinVw = ProdApplJoinView.createValue();
        prodApplJoinVwV.add(prodApplJoinVw);
        prodApplJoinVw.setProdAppl(paD);
        String dilutionRateS = "";
        BigDecimal dilutionRate = paD.getDilutionRate();
        if(dilutionRate!=null) {
          dilutionRate = dilutionRate.setScale(0,BigDecimal.ROUND_HALF_UP);
          dilutionRateS = dilutionRate.toString();
        }
        prodApplJoinVw.setDilutionRate(dilutionRateS);

        String usageRateS = "";
        BigDecimal usageRate = paD.getUsageRate();
        if(usageRate!=null) {
          usageRateS = usageRate.toString();
        }            
        prodApplJoinVw.setUsageRate(usageRateS);
            
        prodApplJoinVw.setUnitCdNumerator(paD.getUnitCdNumerator());
        prodApplJoinVw.setUnitCdDenominator(paD.getUnitCdDenominator());
        prodApplJoinVw.setUnitCdDenominator1(paD.getUnitCdDenominator1());
        prodApplJoinVw.setSharingPercent("");
            
        int itemId = paD.getItemId();
        for(Iterator iter1=productUomPackDV.iterator(); iter1.hasNext();) {
          ProductUomPackData pupD = (ProductUomPackData) iter1.next();
          if(itemId!=pupD.getItemId()) {
            continue;
          }
          prodApplJoinVw.setProductUomPack(pupD);
        }
        for(Iterator iter1=itemDV.iterator(); iter1.hasNext();) {
          ItemData iD = (ItemData) iter1.next();
          if(itemId!=iD.getItemId()) {
            continue;
          }
          prodApplJoinVw.setItem(iD);
          prodApplJoinVw.setItemCategory((String)itemCategHM.get(new Integer(itemId)));
        }
      }       
       return prodApplJoinVwV;
     }catch(Exception exc) {
         logError(exc.getMessage());
         exc.printStackTrace();
         throw new RemoteException(exc.getMessage());
     }finally{
         try {
         con.close();
         }catch (SQLException exc) {
         logError(exc.getMessage());
         exc.printStackTrace();
         throw new RemoteException(exc.getMessage());
         }
     }
   }

   public ProdApplJoinViewVector getProductProcedures(int pItemId) 
   throws RemoteException
   {
     Connection con=null;
	 try {
      con = getConnection();
      ProdApplJoinViewVector prodApplJoinVwV = new ProdApplJoinViewVector();
      DBCriteria dbc = new DBCriteria();
      int catalogId = 4; //Cleanwise store catalog id
      dbc = new DBCriteria();
      dbc.addEqualTo(ProdApplDataAccess.ITEM_ID,pItemId);
      dbc.addIsNull(ProdApplDataAccess.ESTIMATOR_FACILITY_ID);
      ProdApplDataVector prodApplDV = ProdApplDataAccess.select(con,dbc);
      if(prodApplDV.size()==0) {
        ProdApplData paD = ProdApplData.createValue();
        paD.setItemId(pItemId);
        prodApplDV.add(paD);
      }
      
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemDataAccess.ITEM_ID,pItemId);
      ItemDataVector itemDV = ItemDataAccess.select(con,dbc);
      
      
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,pItemId);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,catalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
         RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,dbc);
      
      IdVector categIdV = new IdVector();
      int categIdPrev = -1;
      for(Iterator iter=itemAssocDV.iterator(); iter.hasNext();) {
        ItemAssocData iaD = (ItemAssocData) iter.next();
        int categId = iaD.getItem2Id();
        if(categIdPrev!=categId) {
          categIdPrev = categId;
          categIdV.add(new Integer(categId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,categIdV);
      ItemDataVector categDV = ItemDataAccess.select(con,dbc);
      
      HashMap itemCategHM = new HashMap();
      ItemAssocData wrkItemAssocD = null;
      for(Iterator iter= itemDV.iterator(),
                   iter1 = itemAssocDV.iterator(); 
                             iter.hasNext();) {
        ItemData iD = (ItemData) iter.next();
        int itemId = iD.getItemId();
        while(wrkItemAssocD!=null || iter1.hasNext()) {
          if(wrkItemAssocD==null) wrkItemAssocD = (ItemAssocData) iter1.next();
          if(itemId>wrkItemAssocD.getItem1Id()) {
            wrkItemAssocD = null;
            continue;
          }
          if(itemId<wrkItemAssocD.getItem1Id()) {
            break;
          }
          int item2Id = wrkItemAssocD.getItem2Id();
          
          for(Iterator iter2 = categDV.iterator(); iter2.hasNext();) {
            ItemData categD = (ItemData) iter2.next();
            if(item2Id==categD.getItemId()) {
              String categName = categD.getShortDesc();
              if(categName!=null) {
                itemCategHM.put(new Integer(itemId),categName);
              }
              break;
            }
          }
          wrkItemAssocD = null;
          break;
        }
      }
      
      dbc = new DBCriteria();
      dbc.addEqualTo(ProductUomPackDataAccess.ITEM_ID,pItemId);
      ProductUomPackDataVector productUomPackDV = 
         ProductUomPackDataAccess.select(con,dbc);
      
      for(Iterator iter=prodApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        ProdApplJoinView prodApplJoinVw = ProdApplJoinView.createValue();
        prodApplJoinVwV.add(prodApplJoinVw);
        prodApplJoinVw.setProdAppl(paD);
        String dilutionRateS = "";
        BigDecimal dilutionRate = paD.getDilutionRate();
        if(dilutionRate!=null) {
          dilutionRate = dilutionRate.setScale(0,BigDecimal.ROUND_HALF_UP);
          dilutionRateS = dilutionRate.toString();
        }
        prodApplJoinVw.setDilutionRate(dilutionRateS);

        String usageRateS = "";
        BigDecimal usageRate = paD.getUsageRate();
        if(usageRate!=null) {
          usageRateS = usageRate.toString();
        }            
        prodApplJoinVw.setUsageRate(usageRateS);
            
        prodApplJoinVw.setUnitCdNumerator(paD.getUnitCdNumerator());
        prodApplJoinVw.setUnitCdDenominator(paD.getUnitCdDenominator());
        prodApplJoinVw.setUnitCdDenominator1(paD.getUnitCdDenominator1());
        prodApplJoinVw.setSharingPercent("");
            
        int itemId = paD.getItemId();
        for(Iterator iter1=productUomPackDV.iterator(); iter1.hasNext();) {
          ProductUomPackData pupD = (ProductUomPackData) iter1.next();
          if(itemId!=pupD.getItemId()) {
            continue;
          }
          prodApplJoinVw.setProductUomPack(pupD);
        }
        for(Iterator iter1=itemDV.iterator(); iter1.hasNext();) {
          ItemData iD = (ItemData) iter1.next();
          if(itemId!=iD.getItemId()) {
            continue;
          }
          prodApplJoinVw.setItem(iD);
          prodApplJoinVw.setItemCategory((String)itemCategHM.get(new Integer(itemId)));
        }
      }       
      
       return prodApplJoinVwV;
     }catch(Exception exc) {
         logError(exc.getMessage());
         exc.printStackTrace();
         throw new RemoteException(exc.getMessage());
     }finally{
         try {
         con.close();
         }catch (SQLException exc) {
         logError(exc.getMessage());
         exc.printStackTrace();
         throw new RemoteException(exc.getMessage());
         }
     }
   }

   public ProdUomPackJoinViewVector getEstimatorProducts() 
   throws RemoteException
   {
	Connection con=null;
	try {
	  con = getConnection();
      ProdUomPackJoinViewVector prodUomPackJVwV = 
                                             new ProdUomPackJoinViewVector();
      ProdUomPackJoinView prodUomPackJVw = ProdUomPackJoinView.createValue();
      
      DBCriteria dbc = new DBCriteria();
      dbc.addCondition("1=1");
      dbc.addOrderBy(ProductUomPackDataAccess.ITEM_ID);
      ProductUomPackDataVector prodUomPackDV = 
                           ProductUomPackDataAccess.select(con,dbc);
      IdVector itemIdV = new IdVector();
      for(Iterator iter = prodUomPackDV.iterator(); iter.hasNext();) {
         ProductUomPackData pupD = (ProductUomPackData) iter.next();
         int itemId = pupD.getItemId();
         itemIdV.add(new Integer(itemId));
      }


      //Pick items
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,itemIdV);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(con,dbc);
      

       //Pick up item meta information
       dbc = new DBCriteria();
       dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, itemIdV);

       LinkedList itemPropNames = new LinkedList();
       itemPropNames.add("UOM");
       itemPropNames.add("PACK");
       itemPropNames.add("COLOR");
       itemPropNames.add("SIZE");
       dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, itemPropNames);
       dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);

       ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(con, dbc);

       //Pick up item manufacturers
       dbc = new DBCriteria();
       dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
       dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, 
                      RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
       dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);

       ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(
									       con, dbc);
       IdVector itemMfgIds = new IdVector();

       for (Iterator iter = itemMappingDV.iterator(); iter.hasNext();) {
         ItemMappingData imD = (ItemMappingData) iter.next();
         itemMfgIds.add(new Integer(imD.getBusEntityId()));
       }

       dbc = new DBCriteria();
       dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);

       BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con, 
                                                                       dbc);

       //Pick up item categories
       dbc = new DBCriteria();
       dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, itemIdV);
       dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, 
                      RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
       dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, 4); //Probably should be changed
       dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

       ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,dbc);
       IdVector itemCategoryIds = new IdVector();

       for (Iterator iter = itemAssocDV.iterator(); iter.hasNext();) {
         ItemAssocData iaD = (ItemAssocData) iter.next();
         itemCategoryIds.add(new Integer(iaD.getItem2Id()));
       }

       dbc = new DBCriteria();
       dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryIds);
       dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, 
                      RefCodeNames.ITEM_TYPE_CD.CATEGORY);

       ItemDataVector categories = ItemDataAccess.select(con, dbc);

       //Combine altogether
       int jj = 0;
       int ff = 0;
       int mm = 0;
       int uu = 0;
       int cc = 0;
       ItemData wrkItemD = null;
       for(Iterator iter = prodUomPackDV.iterator(),
                    iter1 = itemDV.iterator(); iter.hasNext();) {
         ProdUomPackJoinView pupJVw = ProdUomPackJoinView.createValue();
         prodUomPackJVwV.add(pupJVw);
         ProductUomPackData pupD = (ProductUomPackData) iter.next();
         pupJVw.setProductUomPack(pupD);
         BigDecimal unitSizeBD = pupD.getUnitSize();
         if(unitSizeBD==null) {
           pupJVw.setUnitSizeInp("");
         } else {
           pupJVw.setUnitSizeInp(unitSizeBD.toString());
         }
         
         String unitCd = pupD.getUnitCd();
         if(unitCd==null) {
           pupJVw.setUnitCdInp("");
         } else {
           pupJVw.setUnitCdInp(unitCd);
         }

         int packQty = pupD.getPackQty();
         if(packQty==0) {
           pupJVw.setPackQtyInp("");
         } else {
           pupJVw.setPackQtyInp(""+packQty);
         }
         
         int itemId = pupD.getItemId();
        
         while(wrkItemD!=null || iter1.hasNext()) {
           if(wrkItemD==null) wrkItemD = (ItemData) iter1.next();
           int wid = wrkItemD.getItemId();
           if(itemId>wid) {
             wrkItemD = null;
             continue;
           }
           if(itemId<wid) {
             break;
           }
           OrderGuideItemDescData ogidD = OrderGuideItemDescData.createValue();
           pupJVw.setOrderGuideItem(ogidD);

           ogidD.setOrderGuideId(0);
           ogidD.setOrderGuideStructureId(0);
           ogidD.setQuantity(0);
           ogidD.setItemId(itemId);

           ogidD.setCwSKU("" +wrkItemD.getSkuNum());
           ogidD.setShortDesc(wrkItemD.getShortDesc());

           //Meta data
           while (uu < itemPropDV.size()) {
             ItemMetaData imD = (ItemMetaData)itemPropDV.get(uu);
             int uuItemId = imD.getItemId();
             if (itemId == uuItemId) {
                if ("UOM".equals(imD.getNameValue())) {
                    ogidD.setUomDesc(imD.getValue());
                } else if ("SIZE".equals(imD.getNameValue())) {
                    ogidD.setSizeDesc(imD.getValue());
                } else if ("PACK".equals(imD.getNameValue())) {
                    ogidD.setPackDesc(imD.getValue());
                } else if ("COLOR".equals(imD.getNameValue())) {
                    ogidD.setColorDesc(imD.getValue());
                }
                uu++;
              } else if(itemId > uuItemId) {
                uu++;
              } else if(itemId < uuItemId) {
                break;
              }
           }

           //Manufacturer data
           for (; ff < itemMappingDV.size(); ff++) {
             ItemMappingData imD = (ItemMappingData)itemMappingDV.get(ff);
             if (imD.getItemId() == itemId) {
                int beId = imD.getBusEntityId();
                ogidD.setManufacturerSKU(imD.getItemNum());

                for (int bb = 0; bb < itemMfgDV.size(); bb++) {
                    BusEntityData beD = (BusEntityData)itemMfgDV.get(bb);
                    if (beD.getBusEntityId() == beId) {
                        ogidD.setManufacturerCd(beD.getShortDesc());
                       break;
                    }
                }
                break;
             }
           }

           //Categories
           String categoryString = "";

           while(cc < itemAssocDV.size()) {
             ItemAssocData iaD = (ItemAssocData)itemAssocDV.get(cc);
             int ccItemId = iaD.getItem1Id();
             if (itemId == ccItemId) {
               int catId = iaD.getItem2Id();
               for (int rr = 0; rr < categories.size(); rr++) {
                 ItemData iD = (ItemData)categories.get(rr);
                 if (catId == iD.getItemId()) {
                   categoryString += iD.getShortDesc() + " ";
                   break;
                 }
               }
               cc++;
               break;
             } else if(itemId > ccItemId) {
               cc++;
             } else if(itemId < ccItemId) {
               break;
             }
           }

           ogidD.setCategoryDesc(categoryString);
           break;
         }
       }
    
       return prodUomPackJVwV;      

    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }
   
   private ProdUomPackJoinView getEstimatorProduct(Connection pCon, int pItemId) 
   throws Exception
   {
      ProdUomPackJoinView prodUomPackJVw = ProdUomPackJoinView.createValue();
      
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ProductUomPackDataAccess.ITEM_ID,pItemId);
      ProductUomPackDataVector prodUomPackDV = 
                           ProductUomPackDataAccess.select(pCon,dbc);
       if(prodUomPackDV.size()<=0) {
         throw new DataNotFoundException("No estimator model item found. Item id: "+pItemId);
       }


      //Pick items
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemDataAccess.ITEM_ID,pItemId);
      ItemDataVector itemDV = ItemDataAccess.select(pCon,dbc);
      if(itemDV.size()<=0) {
        throw new DataNotFoundException("No item found. Item id: "+pItemId);
      }
      

       //Pick up item meta information
       dbc = new DBCriteria();
       dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, pItemId);

       LinkedList itemPropNames = new LinkedList();
       itemPropNames.add("UOM");
       itemPropNames.add("PACK");
       itemPropNames.add("COLOR");
       itemPropNames.add("SIZE");
       dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, itemPropNames);

       ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(pCon, dbc);

       //Pick up item manufacturers
       dbc = new DBCriteria();
       dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, pItemId);
       dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, 
                      RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

       ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(
									       pCon, dbc);
       IdVector itemMfgIds = new IdVector();

       for (Iterator iter = itemMappingDV.iterator(); iter.hasNext();) {
         ItemMappingData imD = (ItemMappingData) iter.next();
         itemMfgIds.add(new Integer(imD.getBusEntityId()));
       }

       dbc = new DBCriteria();
       dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);

       BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(pCon, 
                                                                       dbc);

       //Pick up item categories
       dbc = new DBCriteria();
       dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pItemId);
       dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, 
                      RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
       dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, 4); //Probably should be changed
       dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

       ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(pCon,dbc);
       IdVector itemCategoryIds = new IdVector();

       for (Iterator iter = itemAssocDV.iterator(); iter.hasNext();) {
         ItemAssocData iaD = (ItemAssocData) iter.next();
         itemCategoryIds.add(new Integer(iaD.getItem2Id()));
       }

       dbc = new DBCriteria();
       dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryIds);
       dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, 
                      RefCodeNames.ITEM_TYPE_CD.CATEGORY);

       ItemDataVector categories = ItemDataAccess.select(pCon, dbc);

       //Combine altogether
       ProductUomPackData pupD = (ProductUomPackData) prodUomPackDV.get(0);
       prodUomPackJVw.setProductUomPack(pupD);
       BigDecimal unitSizeBD = pupD.getUnitSize();
       if(unitSizeBD==null) {
         prodUomPackJVw.setUnitSizeInp("");
       } else {
         prodUomPackJVw.setUnitSizeInp(unitSizeBD.toString());
       }
         
       String unitCd = pupD.getUnitCd();
       if(unitCd==null) {
         prodUomPackJVw.setUnitCdInp("");
       } else {
         prodUomPackJVw.setUnitCdInp(unitCd);
       }

       int packQty = pupD.getPackQty();
       if(packQty==0) {
         prodUomPackJVw.setPackQtyInp("");
       } else {
         prodUomPackJVw.setPackQtyInp(""+packQty);
       }
         
       ItemData itemD = (ItemData) itemDV.get(0);
       OrderGuideItemDescData ogidD = OrderGuideItemDescData.createValue();
       prodUomPackJVw.setOrderGuideItem(ogidD);

       ogidD.setOrderGuideId(0);
       ogidD.setOrderGuideStructureId(0);
       ogidD.setQuantity(0);
       ogidD.setItemId(pItemId);

       ogidD.setCwSKU("" +itemD.getSkuNum());
       ogidD.setShortDesc(itemD.getShortDesc());

       //Meta data
       for(Iterator iter=itemPropDV.iterator(); iter.hasNext(); ) {
         ItemMetaData imD = (ItemMetaData) iter.next();
         if ("UOM".equals(imD.getNameValue())) {
           ogidD.setUomDesc(imD.getValue());
         } else if ("SIZE".equals(imD.getNameValue())) {
           ogidD.setSizeDesc(imD.getValue());
         } else if ("PACK".equals(imD.getNameValue())) {
           ogidD.setPackDesc(imD.getValue());
         } else if ("COLOR".equals(imD.getNameValue())) {
           ogidD.setColorDesc(imD.getValue());
         }
       }

       //Manufacturer data
       if(itemMappingDV.size()>0) {
         ItemMappingData imD = (ItemMappingData) itemMappingDV.get(0);
         ogidD.setManufacturerSKU(imD.getItemNum());
         int beId = imD.getBusEntityId();
         for (int bb = 0; bb < itemMfgDV.size(); bb++) {
           BusEntityData beD = (BusEntityData)itemMfgDV.get(bb);
           if (beD.getBusEntityId() == beId) {
             ogidD.setManufacturerCd(beD.getShortDesc());
             break;
           }
         }
       }

       //Categories
       String categoryString = "";

       for(Iterator iter=itemAssocDV.iterator(); iter.hasNext();) {
         ItemAssocData iaD = (ItemAssocData) iter.next();
         int catId = iaD.getItem2Id();
         for (int rr = 0; rr < categories.size(); rr++) {
           ItemData iD = (ItemData)categories.get(rr);
           if (catId == iD.getItemId()) {
             categoryString += iD.getShortDesc() + " ";
             ogidD.setCategoryDesc(categoryString);
             break;
           }
         }
       }
    
       return prodUomPackJVw;      

  }

  public void updateEstimatorProducts(ProductUomPackDataVector pProducts, String pUser) 
   throws RemoteException
   {
	Connection con=null;
	try {
	  con = getConnection();
      for(Iterator iter=pProducts.iterator(); iter.hasNext();) {
        ProductUomPackData pupD = (ProductUomPackData) iter.next();
        int pupId = pupD.getProductUomPackId();
        if(pupId<=0) {
          String errorMess = "No id to update spending estimator product. Item id: "+pupD.getItemId();
          throw new Exception(errorMess);
        }
        pupD.setModBy(pUser);
        ProductUomPackDataAccess.update(con,pupD);
      }
    
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

   public ProdUomPackJoinView addEstimatorProduct(ProductUomPackData pProduct, String pUser) 
   throws RemoteException
   {
	Connection con=null;
	try {
	  con = getConnection();
      int itemId = pProduct.getItemId();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ProductUomPackDataAccess.ITEM_ID, itemId);
      ProductUomPackDataVector pupDV = 
           ProductUomPackDataAccess.select(con,dbc);
      if(pupDV.size()>0) {
        String errorMess = "Spending estimator already has the item. Item id: "+itemId;
        throw new Exception(errorMess);
      }
      pProduct.setAddBy(pUser);
      pProduct.setModBy(pUser);
      ProductUomPackDataAccess.insert(con,pProduct);
      
      ProdUomPackJoinView prodUomPackJVw = getEstimatorProduct(con,itemId);
      return  prodUomPackJVw;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

   public void removeEstimatorProducts(IdVector pItemIds) 
   throws RemoteException
   {
	Connection con=null;
	try {
	  con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ProductUomPackDataAccess.ITEM_ID,pItemIds);
      ProductUomPackDataAccess.remove(con,dbc);
    
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

   public void removeEstimatorProductsCascade(IdVector pItemIds) 
   throws RemoteException
   {
	Connection con=null;
	try {
	  con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.ITEM_ID,pItemIds);
      ProdApplDataAccess.remove(con,dbc);
      
      dbc = new DBCriteria();
      dbc.addOneOf(ProductUomPackDataAccess.ITEM_ID,pItemIds);
      ProductUomPackDataAccess.remove(con,dbc);
    
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public EstimatorFacilityDataVector getUserEstimatorFacilities(int pUserId) 
   throws RemoteException
   {
	EstimatorFacilityDataVector estimatorFacilityDV = 
                                             new EstimatorFacilityDataVector();
	Connection con=null;
	try {
	  con = getConnection();
      String catalogReq = getEstimatorCatalogReq(con,pUserId);
      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(EstimatorFacilityDataAccess.CATALOG_ID,catalogReq);
      dbc.addNotEqualTo(EstimatorFacilityDataAccess.CATALOG_ID,0);
      dbc.addEqualTo(EstimatorFacilityDataAccess.FACILITY_STATUS_CD,
                                         RefCodeNames.FACILITY_STATUS_CD.ACTIVE);
      dbc.addOrderBy(EstimatorFacilityDataAccess.NAME);
      estimatorFacilityDV = EstimatorFacilityDataAccess.select(con,dbc);

    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
    return estimatorFacilityDV;
  }

   public CatalogDataVector getUserEstimatorCatalogs(int pUserId) 
   throws RemoteException
   {
	CatalogDataVector catalogDV = new CatalogDataVector();
	Connection con=null;
	try {
	  con = getConnection();
      String catalogReq = getEstimatorCatalogReq(con,pUserId);
      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID,catalogReq);
      dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      catalogDV = CatalogDataAccess.select(con,dbc);

    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
    return catalogDV;
  }

   public EstimatorFacilityData getDefaultFacilityProfile() 
   throws RemoteException
   {
	Connection con=null;
	try {
	  con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(EstimatorFacilityDataAccess.CATALOG_ID,0);
      EstimatorFacilityDataVector estimatorFacilityDV = EstimatorFacilityDataAccess.select(con,dbc);
      if(estimatorFacilityDV.size()==0) {
        return EstimatorFacilityData.createValue();
      }
      EstimatorFacilityData facility = (EstimatorFacilityData) estimatorFacilityDV.get(0);
      return facility;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

   public EstimatorFacilityJoinView getEstimatiorProfile(int pFacilityId) 
   throws RemoteException
   {
	Connection con=null;
    DBCriteria dbc;
	try {
	  con = getConnection();
      EstimatorFacilityJoinView facilityJoin = 
                                       EstimatorFacilityJoinView.createValue();      
      EstimatorFacilityData facility = 
                 EstimatorFacilityDataAccess.select(con,pFacilityId);
      facilityJoin.setEstimatorFacility(facility);
      
      dbc = new DBCriteria();
      dbc.addEqualTo(FacilityFloorDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      FacilityFloorDataVector floors = FacilityFloorDataAccess.select(con,dbc);
      facilityJoin.setFacilityFloors(floors);
      return facilityJoin;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
   }
   
   public EstimatorFacilityJoinView saveEstimatorProfile(EstimatorFacilityJoinView pFacilityJoin,
                String pUser) 
   throws RemoteException
   {
	Connection con=null;
    DBCriteria dbc;
	try {
	  con = getConnection();
      EstimatorFacilityData facility = pFacilityJoin.getEstimatorFacility();
      int facilityId = facility.getEstimatorFacilityId();
      facility.setModBy(pUser);
      FacilityFloorDataVector floors = null;
      if(facilityId==0) {
        facility.setAddBy(pUser);
        facility.setProcessStep(22222);
        facility = EstimatorFacilityDataAccess.insert(con,facility);
        facilityId =  facility.getEstimatorFacilityId();
        floors = new FacilityFloorDataVector();
      } else {
        EstimatorFacilityData estOld = EstimatorFacilityDataAccess.select(con,facilityId);
        if(estOld.getCatalogId()!=facility.getCatalogId()) {
          int ogId = estOld.getOrderGuideId(); 
          if(ogId>0) {
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, ogId);
            OrderGuideStructureDataAccess.remove(con,dbc);
            OrderGuideDataAccess.remove(con,ogId);
            facility.setOrderGuideId(0);
          }
        }
        
        EstimatorFacilityDataAccess.update(con,facility);
        dbc = new DBCriteria();
        dbc.addEqualTo(FacilityFloorDataAccess.ESTIMATOR_FACILITY_ID, facilityId);
        floors = FacilityFloorDataAccess.select(con,dbc);        
      }
      //Floors
      FacilityFloorDataVector nFloors = pFacilityJoin.getFacilityFloors();
      for(Iterator iter = nFloors.iterator(); iter.hasNext();) {        
        FacilityFloorData nFloorD = (FacilityFloorData) iter.next();
        int nFloorId = nFloorD.getFacilityFloorId();
        boolean addFl = true;
        if(nFloorId!=0) {
          for(Iterator iter1 = floors.iterator(); iter1.hasNext();) {
            FacilityFloorData floorD = (FacilityFloorData) iter1.next();
            int floorId = floorD.getFacilityFloorId();
            if(nFloorId==floorId) {
              addFl = false;
              BigDecimal cleanablePr = nFloorD.getCleanablePercent();
              if(cleanablePr!=null && cleanablePr.doubleValue()>0.0001) {
                iter1.remove();
                nFloorD.setModBy(pUser);
                FacilityFloorDataAccess.update(con,nFloorD);
              }
              break;
            }
          }
        }
        if(addFl) {
          nFloorD.setEstimatorFacilityId(facilityId);
          nFloorD.setAddBy(pUser);
          nFloorD.setModBy(pUser);
          FacilityFloorData ffD = FacilityFloorDataAccess.insert(con,nFloorD);
          nFloorD.setFacilityFloorId(ffD.getFacilityFloorId());
        }
      }
      for(Iterator iter = floors.iterator(); iter.hasNext();) {
        FacilityFloorData floorD = (FacilityFloorData) iter.next();
        int floorId = floorD.getFacilityFloorId();
        FacilityFloorDataAccess.remove(con,floorId);
        iter.remove();
      }
      
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
    return pFacilityJoin;
  }

   public void deleteEstimatorProfile(EstimatorFacilityData pFacility, String pUser) 
   throws RemoteException
   {
	Connection con=null;
	try {
	  con = getConnection();
      int facilityId = pFacility.getEstimatorFacilityId();
      if(facilityId>0) {
        pFacility.setModBy(pUser);
        pFacility.setFacilityStatusCd(RefCodeNames.FACILITY_STATUS_CD.DELETED);
        EstimatorFacilityDataAccess.update(con,pFacility);
      }
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }
   
  private String getEstimatorCatalogReq (Connection pCon, int pUserId) 
  throws Exception
  {
    UserData userD = UserDataAccess.select(pCon,pUserId);
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
        RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR);
    dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
        RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

    String userTypeCd = userD.getUserTypeCd();
    if(RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)) {
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
      crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                             RefCodeNames.USER_ASSOC_CD.ACCOUNT);
      String accountAssocReq = 
          UserAssocDataAccess.
                getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, crit);
      crit = new DBCriteria();
      crit.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID,accountAssocReq);
      crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                          RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
      String catalogAssocReq = 
          CatalogAssocDataAccess.
                getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, crit);
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID,catalogAssocReq);
    } else if (RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||
               RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
               RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)) {

    } else {
      throw new Exception ("The user is not authorized for Spnding Estimation Tool. " +
                           "User id: "+pUserId);
    }
    String catalogReq = 
       CatalogDataAccess.getSqlSelectIdOnly(CatalogDataAccess.CATALOG_ID,dbc);
    return catalogReq;
  }
  private String rtrimZero(String pStr) {
    char[] ccA = pStr.toCharArray();
    for(int ii=ccA.length-1; ii>0; ii--) {
       if(ccA[ii]=='.') {
          ccA[ii]=' ';
          break;
       }
       if(ccA[ii]!='0') break;
       ccA[ii]=' ';
    }
    pStr = (new String(ccA)).trim();
    return pStr;
   
  }

  private void setProcessStep(Connection pCon, int pFacilityId, int pPosition, int pStepNum, String pUser) 
  throws Exception 
  {
     EstimatorFacilityData facilityD = EstimatorFacilityDataAccess.select(pCon,pFacilityId);
     int processStep = facilityD.getProcessStep();
     int[] stepA = new int[pPosition];
     for(int ii=0; ii<pPosition; ii++) {
       stepA[ii] = processStep%10;
       processStep /= 10;
     }     
     processStep /=10;
     processStep = processStep*10 + pStepNum;
     for(int ii=pPosition-1; ii>=0; ii--) {
       processStep = processStep*10 + stepA[ii];
     }
     
     facilityD.setProcessStep(processStep);
     facilityD.setModBy(pUser);
     EstimatorFacilityDataAccess.update(pCon,facilityD);
  }

  public void saveRestroomCleaningSchedules (int pFacilityId, 
     CleaningScheduleJoinViewVector pRestroomCleaningProcProd, String pUser) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();

      
      dbc = new DBCriteria();
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      dbc.addEqualTo(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD,
                           RefCodeNames.CLEANING_SCHEDULE_CD.RESTROOM_CLEANING);
      
      IdVector scheduleIdV = 
        CleaningScheduleDataAccess.selectIdOnly(con,
        CleaningScheduleDataAccess.CLEANING_SCHEDULE_ID,dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID,scheduleIdV);
      IdVector scheduleStructIdV =
        CleaningSchedStructDataAccess.selectIdOnly(con,
              CleaningSchedStructDataAccess.CLEANING_SCHED_STRUCT_ID,dbc);

      dbc = new DBCriteria();
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      dbc.addOneOf(ProdApplDataAccess.CLEANING_SCHED_STRUCT_ID, scheduleStructIdV);
      ProdApplDataAccess.remove(con,dbc);
      
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHED_STRUCT_ID,scheduleStructIdV);
      CleaningSchedStructDataAccess.remove(con,dbc);
      
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_ID,scheduleIdV);
      CleaningScheduleDataAccess.remove(con,dbc);

      int ind = -1;
       for(Iterator iter = pRestroomCleaningProcProd.iterator(); iter.hasNext();) {
         ind ++;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         CleaningScheduleData csD = csjVw.getSchedule();
         csD.setCleaningScheduleId(0);
         csD.setEstimatorFacilityId(pFacilityId);
         csD.setAddBy(pUser);
         csD.setModBy(pUser);
         String frequencyS = csjVw.getFrequency();
         if(!Utility.isSet(frequencyS)) {
           continue;
         }
         
         BigDecimal frequencyBD = new BigDecimal(frequencyS);
         if(frequencyBD.doubleValue()<0.0001) {
           continue;
         }
         csD.setFrequency(frequencyBD);
         String timePeriodCd = csjVw.getTimePeriodCd();
         csD.setTimePeriodCd(timePeriodCd);
         csD = CleaningScheduleDataAccess.insert(con,csD);
         int cleaningScheduleId = csD.getCleaningScheduleId();
         
         CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
         int ind1 = -1;
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           ind1 ++;
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           
           BigDecimal sumPercent = new BigDecimal(0);
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           int ind2 = 0;
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             BigDecimal sharingBD =  new BigDecimal(sharingS);
             if(sharingBD.doubleValue()<0.001) {
               continue;
             }
             sumPercent = sumPercent.add(sharingBD);
             break;
           }
           if(sumPercent.doubleValue()<0.001) {
             continue;
           }
           CleaningSchedStructData cssD = cssjVw.getScheduleStep();
           cssD.setCleaningSchedStructId(0);
           cssD.setCleaningScheduleId(cleaningScheduleId);
           cssD.setAddBy(pUser);
           cssD.setModBy(pUser);
           cssD = CleaningSchedStructDataAccess.insert(con,cssD);
           int cleaningSchedStructId = cssD.getCleaningSchedStructId();
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             BigDecimal sharingBD =  new BigDecimal(sharingS);
             if(sharingBD.doubleValue()<0.001) {
               continue;
             }
             ProdApplData paD = pajVw.getProdAppl();
             paD.setProdApplId(0);
             paD.setEstimatorFacilityId(pFacilityId);
             paD.setAddBy(pUser);
             paD.setModBy(pUser);
             paD.setCleaningSchedStructId(cleaningSchedStructId);
             paD.setSharingPercent(sharingBD);
             
             String dilutionRateS = pajVw.getDilutionRate();
             paD.setDilutionRate(null);
             if(dilutionRateS!=null && dilutionRateS.trim().length()>0) {              
               BigDecimal dilutionRateBD = new BigDecimal(dilutionRateS);
               if(dilutionRateBD.doubleValue()>0.01) {
                 paD.setDilutionRate(dilutionRateBD.setScale(2,BigDecimal.ROUND_HALF_UP));
               }
             } else {
                 paD.setDilutionRate(null);
             }
             
             String usageRateS = pajVw.getUsageRate();
             BigDecimal usageRateBD = new BigDecimal (usageRateS);
             paD.setUsageRate(usageRateBD);
             String numerator = pajVw.getUnitCdNumerator();
             paD.setUnitCdNumerator(numerator);
             String denominator = pajVw.getUnitCdDenominator();
             paD.setUnitCdDenominator(denominator);
             String denominator1 = pajVw.getUnitCdDenominator1();
             paD.setUnitCdDenominator1(denominator1);
             ProdApplDataAccess.insert(con,paD);
           }
         }
       }
       setProcessStep(con, pFacilityId, 2, 3, pUser);
      
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public CleaningScheduleJoinViewVector getCleaningSchedules (int pFacilityId, 
                                                     List pCleaningScheduleTypes) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      EstimatorFacilityData facility = null;
      facility = EstimatorFacilityDataAccess.select(con,pFacilityId);

      IdVector itemIdV = null;
      int  catalogId = facility.getCatalogId();

      dbc = new DBCriteria();
      dbc.addEqualTo(ContractDataAccess.CATALOG_ID, catalogId);
      dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD, 
                                  RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
      IdVector contractIdV = 
            ContractDataAccess.selectIdOnly(con,ContractDataAccess.CONTRACT_ID,dbc);

      int contractId = 0;
      if(contractIdV.size()>1) {
        String errorMess = "There is more then one active contract for the catalog. " +
        "Catalog id: "+catalogId;
        throw new Exception(errorMess);
      } 
      if(contractIdV.size()==1) {
        Integer contractIdI = (Integer) contractIdV.get(0);
        contractId = contractIdI.intValue();
      }

      dbc = new DBCriteria();
      dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID,contractId);
      itemIdV = 
        ContractItemDataAccess.selectIdOnly(con,ContractItemDataAccess.ITEM_ID, dbc);

      int orderGuideId = facility.getOrderGuideId();
      dbc = new DBCriteria();
      dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemIdV);
      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,orderGuideId);

      itemIdV = OrderGuideStructureDataAccess.selectIdOnly(con, 
               OrderGuideStructureDataAccess.ITEM_ID,dbc);      
      
      //Get default schedules
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD, 
                                            pCleaningScheduleTypes);
      dbc.addIsNull(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID);
      dbc.addGreaterOrEqual(CleaningScheduleDataAccess.SEQ_NUM,1);
      dbc.addOrderBy(CleaningScheduleDataAccess.SEQ_NUM);
      CleaningScheduleDataVector cleaningScheduleDV = 
         CleaningScheduleDataAccess.select(con,dbc);
      IdVector cleaningScheduleIdV = new IdVector();
      for(Iterator iter=cleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        cleaningScheduleIdV.add(new Integer(id));
      }
      
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, cleaningScheduleIdV);
      dbc.addGreaterOrEqual(CleaningSchedStructDataAccess.SEQ_NUM,1);
      dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
      CleaningSchedStructDataVector cleaningSchedStructDV = 
         CleaningSchedStructDataAccess.select(con,dbc);

      IdVector cleaningProcIdV = new IdVector();
      for(Iterator iter=cleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int procId = cssD.getCleaningProcId();
        cleaningProcIdV.add(new Integer(procId));
      }
      
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningProcDataAccess.CLEANING_PROC_ID,cleaningProcIdV);
      CleaningProcDataVector cleaningProcDV = CleaningProcDataAccess.select(con,dbc);
      
      dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.CLEANING_PROC_ID,cleaningProcIdV);
      dbc.addOneOf(ProdApplDataAccess.ITEM_ID,itemIdV);
      dbc.addIsNull(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID);
      ProdApplDataVector prodApplDV = ProdApplDataAccess.select(con,dbc);
      
      itemIdV = new IdVector();
      for(Iterator iter=prodApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        int itemId = paD.getItemId();
        itemIdV.add(new Integer(itemId));
      }
      
      
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,itemIdV);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(con,dbc);
      
      
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID,itemIdV);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,catalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
         RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,dbc);
      
      IdVector categIdV = new IdVector();
      int categIdPrev = -1;
      for(Iterator iter=itemAssocDV.iterator(); iter.hasNext();) {
        ItemAssocData iaD = (ItemAssocData) iter.next();
        int categId = iaD.getItem2Id();
        if(categIdPrev!=categId) {
          categIdPrev = categId;
          categIdV.add(new Integer(categId));
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID,categIdV);
      ItemDataVector categDV = ItemDataAccess.select(con,dbc);
      
      HashMap itemCategHM = new HashMap();
      ItemAssocData wrkItemAssocD = null;
      for(Iterator iter= itemDV.iterator(),
                   iter1 = itemAssocDV.iterator(); 
                             iter.hasNext();) {
        ItemData iD = (ItemData) iter.next();
        int itemId = iD.getItemId();
        while(wrkItemAssocD!=null || iter1.hasNext()) {
          if(wrkItemAssocD==null) wrkItemAssocD = (ItemAssocData) iter1.next();
          if(itemId>wrkItemAssocD.getItem1Id()) {
            wrkItemAssocD = null;
            continue;
          }
          if(itemId<wrkItemAssocD.getItem1Id()) {
            break;
          }
          int item2Id = wrkItemAssocD.getItem2Id();
          
          for(Iterator iter2 = categDV.iterator(); iter2.hasNext();) {
            ItemData categD = (ItemData) iter2.next();
            if(item2Id==categD.getItemId()) {
              String categName = categD.getShortDesc();
              if(categName!=null) {
                itemCategHM.put(new Integer(itemId),categName);
              }
              break;
            }
          }
          wrkItemAssocD = null;
          break;
        }
      }
      
      dbc = new DBCriteria();
      dbc.addOneOf(ProductUomPackDataAccess.ITEM_ID,itemIdV);
      ProductUomPackDataVector productUomPackDV = 
         ProductUomPackDataAccess.select(con,dbc);
      
      CleaningScheduleJoinViewVector cleaningScheduleJoinVwV =
                                           new CleaningScheduleJoinViewVector();
      int cleaningScheduleIdPrev = -1;
      for(Iterator iter=cleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        CleaningScheduleJoinView cleaningScheduleJoinVw = 
                            CleaningScheduleJoinView.createValue();
        cleaningScheduleJoinVwV.add(cleaningScheduleJoinVw);
        cleaningScheduleJoinVw.setSchedule(csD);
        CleaningSchedStructJoinViewVector clSchedStrJoinVwV = 
             new CleaningSchedStructJoinViewVector();
        cleaningScheduleJoinVw.setStructure(clSchedStrJoinVwV);

        cleaningScheduleJoinVw.setFrequency("");
        String timePeriodCd = csD.getTimePeriodCd();
        if(timePeriodCd==null) timePeriodCd = "";
        cleaningScheduleJoinVw.setTimePeriodCd(timePeriodCd);
        csD.setTimePeriodCd(null);
        csD.setFrequency(new BigDecimal(0));

        //Schedule Structure
        for(Iterator iter1=cleaningSchedStructDV.iterator(); iter1.hasNext();) {
          CleaningSchedStructData cssD = (CleaningSchedStructData) iter1.next();
          if(id!=cssD.getCleaningScheduleId()) {
            continue;
          }
          CleaningSchedStructJoinView clSchedStrJoinVw = 
                                     CleaningSchedStructJoinView.createValue();
          clSchedStrJoinVwV.add(clSchedStrJoinVw);
          clSchedStrJoinVw.setScheduleStep(cssD);
          ProdApplJoinViewVector prodApplJoinVwV = new ProdApplJoinViewVector();
          clSchedStrJoinVw.setProducts(prodApplJoinVwV);
          int cleaningProcId = cssD.getCleaningProcId();
          
          for(Iterator iter2=cleaningProcDV.iterator(); iter2.hasNext();) {
            CleaningProcData cpD = (CleaningProcData) iter2.next();
            if(cleaningProcId==cpD.getCleaningProcId()) {
              clSchedStrJoinVw.setProcedure(cpD);
              break;
            }
          }
          //Step products
          for(Iterator iter2=prodApplDV.iterator(); iter2.hasNext();) {
            ProdApplData paD = (ProdApplData) iter2.next();
            if(cleaningProcId!=paD.getCleaningProcId()) {
              continue;
            }
            paD = (ProdApplData) paD.clone();
            ProdApplJoinView prodApplJoinVw = ProdApplJoinView.createValue();
            prodApplJoinVwV.add(prodApplJoinVw);
            prodApplJoinVw.setProdAppl(paD);
            String dilutionRateS = "";
            BigDecimal dilutionRate = paD.getDilutionRate();
            if(dilutionRate!=null) {
              dilutionRate = dilutionRate.setScale(0,BigDecimal.ROUND_HALF_UP);
              dilutionRateS = dilutionRate.toString();
            }
            prodApplJoinVw.setDilutionRate(dilutionRateS);

            String usageRateS = "";
            BigDecimal usageRate = paD.getUsageRate();
            if(usageRate!=null) {
              usageRateS = usageRate.toString();
            }            
            prodApplJoinVw.setUsageRate(usageRateS);
            
            prodApplJoinVw.setUnitCdNumerator(paD.getUnitCdNumerator());
            prodApplJoinVw.setUnitCdDenominator(paD.getUnitCdDenominator());
            prodApplJoinVw.setUnitCdDenominator1(paD.getUnitCdDenominator1());
            prodApplJoinVw.setSharingPercent("");
            
            int itemId = paD.getItemId();
            for(Iterator iter3=productUomPackDV.iterator(); iter3.hasNext();) {
              ProductUomPackData pupD = (ProductUomPackData) iter3.next();
              if(itemId!=pupD.getItemId()) {
                continue;
              }
              prodApplJoinVw.setProductUomPack(pupD);
            }
            for(Iterator iter3=itemDV.iterator(); iter3.hasNext();) {
              ItemData iD = (ItemData) iter3.next();
              if(itemId!=iD.getItemId()) {
                continue;
              }
              prodApplJoinVw.setItem(iD);
              prodApplJoinVw.setItemCategory((String)itemCategHM.get(new Integer(itemId)));
            }
          }
        }
        cleaningScheduleJoinVw.setFloorMachine("");
        dbc = new DBCriteria();
        dbc.addEqualTo(CleaningSchedFilterDataAccess.CLEANING_SCHEDULE_ID, id);
        CleaningSchedFilterDataVector csfDV =
                CleaningSchedFilterDataAccess.select(con,dbc);
        cleaningScheduleJoinVw.setFilter(csfDV);
        for(Iterator iter1 = csfDV.iterator(); iter1.hasNext();) {
          CleaningSchedFilterData csfD = (CleaningSchedFilterData) iter1.next();
          if("floorMachine".equalsIgnoreCase(csfD.getPropertyName())) {
            cleaningScheduleJoinVw.setFloorMachine(csfD.getPropertyValue());
          }
        }
      }

      //Overweite with template date
      int templateId = facility.getTemplateFacilityId();
      if(templateId>0) {
        //Get template schedules
        dbc = new DBCriteria();
        dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD, 
                                              pCleaningScheduleTypes);
        dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,templateId);
        CleaningScheduleDataVector templateCleaningScheduleDV = 
           CleaningScheduleDataAccess.select(con,dbc);
        IdVector templateCleaningScheduleIdV = new IdVector();
        for(Iterator iter=templateCleaningScheduleDV.iterator(); iter.hasNext();) {
          CleaningScheduleData csD = (CleaningScheduleData) iter.next();
          int id = csD.getCleaningScheduleId();
          templateCleaningScheduleIdV.add(new Integer(id));
        }

        dbc = new DBCriteria();
        dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
                                                     templateCleaningScheduleIdV);
        dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
        CleaningSchedStructDataVector templateCleaningSchedStructDV = 
                                   CleaningSchedStructDataAccess.select(con,dbc);

        IdVector templateCleaningProcIdV = new IdVector();
        for(Iterator iter=templateCleaningSchedStructDV.iterator(); iter.hasNext();) {
          CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
          int procId = cssD.getCleaningProcId();
          templateCleaningProcIdV.add(new Integer(procId));
        }

        dbc = new DBCriteria();
        dbc.addOneOf(ProdApplDataAccess.CLEANING_PROC_ID,templateCleaningProcIdV);
        dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID,templateId);
        ProdApplDataVector templateProdApplDV = ProdApplDataAccess.select(con,dbc);

        for(Iterator iter=cleaningScheduleJoinVwV.iterator(); iter.hasNext();) {
          CleaningScheduleJoinView cleaningScheduleJoinVw = 
                                           (CleaningScheduleJoinView) iter.next();
          CleaningScheduleData csD = cleaningScheduleJoinVw.getSchedule();
          int seqNum = csD.getSeqNum();
          int templateCleaningScheduleId = 0;
          for(Iterator iter1=templateCleaningScheduleDV.iterator(); iter1.hasNext();) {
            CleaningScheduleData templateCsD = (CleaningScheduleData) iter1.next();
            if(seqNum==templateCsD.getSeqNum()){
              templateCleaningScheduleId = templateCsD.getCleaningScheduleId();
              String frequencyS = "";
              BigDecimal frequency = templateCsD.getFrequency();
              csD.setFrequency(frequency);
              csD.setTimePeriodCd(templateCsD.getTimePeriodCd());
              if(frequency!=null) {
                frequencyS = frequency.toString();
                frequencyS = rtrimZero(frequencyS);
              }
              cleaningScheduleJoinVw.setFrequency(frequencyS);
              cleaningScheduleJoinVw.setTimePeriodCd(templateCsD.getTimePeriodCd());
              break;
            }
          }
          if(templateCleaningScheduleId==0) {
            continue;
          }


          CleaningSchedStructJoinViewVector clSchedStrJoinVwV = 
                                           cleaningScheduleJoinVw.getStructure();
          for(Iterator iter1=clSchedStrJoinVwV.iterator(); iter1.hasNext();) {
             CleaningSchedStructJoinView clSchedStrJoinVw =
                                   (CleaningSchedStructJoinView) iter1.next();

             CleaningSchedStructData cssD = clSchedStrJoinVw.getScheduleStep();
             int schedStrSeqNum = cssD.getSeqNum();

             int templateCleaningSchedStructId = 0;
             for(Iterator iter1a=templateCleaningSchedStructDV.iterator(); iter1a.hasNext();) {
               CleaningSchedStructData templateCssD = (CleaningSchedStructData) iter1a.next();
               if(templateCleaningScheduleId!=templateCssD.getCleaningScheduleId()) {
                 continue;
               }
               if(schedStrSeqNum!=templateCssD.getSeqNum()) {
                 continue;
               }
               templateCleaningSchedStructId = templateCssD.getCleaningSchedStructId();
               break;
             }
             if(templateCleaningSchedStructId==0) {
               continue;
             }
             ProdApplJoinViewVector prodApplJoinVwV = clSchedStrJoinVw.getProducts();
             for(Iterator iter2=prodApplJoinVwV.iterator(); iter2.hasNext();) {
               ProdApplJoinView prodApplJoinVw = (ProdApplJoinView) iter2.next();
               ProdApplData paD = prodApplJoinVw.getProdAppl();
               int cleaningProcId = paD.getCleaningProcId();
               int itemId = paD.getItemId();

               for(Iterator iter3=templateProdApplDV.iterator(); iter3.hasNext();) {
                 ProdApplData templatePaD = (ProdApplData) iter3.next();
                 if(templateCleaningSchedStructId==templatePaD.getCleaningSchedStructId() &&
                    cleaningProcId==templatePaD.getCleaningProcId() &&
                    itemId==templatePaD.getItemId()) {
                    //paD.setEstimatorFacilityId(pFacilityId);
                    //paD.setCleaningSchedStructId(facilityCleaningSchedStructId);
                    String dilutionRateS = "";
                    BigDecimal dilutionRate = templatePaD.getDilutionRate();
                    paD.setDilutionRate(dilutionRate);
                    if(dilutionRate!=null) {
                      dilutionRate = dilutionRate.setScale(0,BigDecimal.ROUND_HALF_UP);
                      dilutionRateS = dilutionRate.toString();
                    }
                    prodApplJoinVw.setDilutionRate(dilutionRateS);

                    String usageRateS = "";
                    BigDecimal usageRate = templatePaD.getUsageRate();
                    paD.setUsageRate(usageRate);
                    if(usageRate!=null) {
                      usageRateS = usageRate.toString();
                    }            
                    prodApplJoinVw.setUsageRate(usageRateS);
                    
                    if(Utility.isSet(templatePaD.getUnitCdNumerator())) {
                      prodApplJoinVw.setUnitCdNumerator(templatePaD.getUnitCdNumerator());
                    }
                    if(Utility.isSet(templatePaD.getUnitCdDenominator())) {                    
                      prodApplJoinVw.setUnitCdDenominator(templatePaD.getUnitCdDenominator());
                    }
                    if(Utility.isSet(templatePaD.getUnitCdDenominator1())) {                    
                      prodApplJoinVw.setUnitCdDenominator1(templatePaD.getUnitCdDenominator1());
                    }
                    //prodApplJoinVw.setUnitCdNumerator(templatePaD.getUnitCdNumerator());
                    //prodApplJoinVw.setUnitCdDenominator(templatePaD.getUnitCdDenominator());

                    break;
                 }
               }             
             }
          }
        }
      }

      //Get facility schedules
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD, 
                                            pCleaningScheduleTypes);
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      CleaningScheduleDataVector facilityCleaningScheduleDV = 
         CleaningScheduleDataAccess.select(con,dbc);
      IdVector facilityCleaningScheduleIdV = new IdVector();
      for(Iterator iter=facilityCleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        facilityCleaningScheduleIdV.add(new Integer(id));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
                                                   facilityCleaningScheduleIdV);
      dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
      CleaningSchedStructDataVector facilityCleaningSchedStructDV = 
                                 CleaningSchedStructDataAccess.select(con,dbc);

      IdVector facilityCleaningProcIdV = new IdVector();
      for(Iterator iter=facilityCleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int procId = cssD.getCleaningProcId();
        facilityCleaningProcIdV.add(new Integer(procId));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.CLEANING_PROC_ID,facilityCleaningProcIdV);
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      ProdApplDataVector facilityProdApplDV = ProdApplDataAccess.select(con,dbc);

      for(Iterator iter=cleaningScheduleJoinVwV.iterator(); iter.hasNext();) {
        CleaningScheduleJoinView cleaningScheduleJoinVw = 
                                         (CleaningScheduleJoinView) iter.next();
        CleaningScheduleData csD = cleaningScheduleJoinVw.getSchedule();
        int seqNum = csD.getSeqNum();
        int facilityCleaningScheduleId = 0;
        for(Iterator iter1=facilityCleaningScheduleDV.iterator(); iter1.hasNext();) {
          CleaningScheduleData facilityCsD = (CleaningScheduleData) iter1.next();
          if(seqNum==facilityCsD.getSeqNum()){
            facilityCleaningScheduleId = facilityCsD.getCleaningScheduleId();
            String frequencyS = "";
            BigDecimal frequency = facilityCsD.getFrequency();
            if(frequency!=null) {
              frequencyS = frequency.toString();
              frequencyS = rtrimZero(frequencyS);
            }
            csD.setEstimatorFacilityId(pFacilityId);
            cleaningScheduleJoinVw.setFrequency(frequencyS);
            cleaningScheduleJoinVw.setTimePeriodCd(facilityCsD.getTimePeriodCd());
            break;
          }
        }
        if(facilityCleaningScheduleId==0) {
          continue;
        }


        CleaningSchedStructJoinViewVector clSchedStrJoinVwV = 
                                         cleaningScheduleJoinVw.getStructure();
        for(Iterator iter1=clSchedStrJoinVwV.iterator(); iter1.hasNext();) {
           CleaningSchedStructJoinView clSchedStrJoinVw =
                                 (CleaningSchedStructJoinView) iter1.next();

           CleaningSchedStructData cssD = clSchedStrJoinVw.getScheduleStep();
           int schedStrSeqNum = cssD.getSeqNum();

           int facilityCleaningSchedStructId = 0;
           for(Iterator iter1a=facilityCleaningSchedStructDV.iterator(); iter1a.hasNext();) {
             CleaningSchedStructData facilityCssD = (CleaningSchedStructData) iter1a.next();
             if(facilityCleaningScheduleId!=facilityCssD.getCleaningScheduleId()) {
               continue;
             }
             if(schedStrSeqNum!=facilityCssD.getSeqNum()) {
               continue;
             }
             facilityCleaningSchedStructId = facilityCssD.getCleaningSchedStructId();
             break;
           }
           if(facilityCleaningSchedStructId==0) {
             continue;
           }
           ProdApplJoinViewVector prodApplJoinVwV = clSchedStrJoinVw.getProducts();
           for(Iterator iter2=prodApplJoinVwV.iterator(); iter2.hasNext();) {
             ProdApplJoinView prodApplJoinVw = (ProdApplJoinView) iter2.next();
             ProdApplData paD = prodApplJoinVw.getProdAppl();
             int cleaningProcId = paD.getCleaningProcId();
             int itemId = paD.getItemId();

             for(Iterator iter3=facilityProdApplDV.iterator(); iter3.hasNext();) {
               ProdApplData facilityPaD = (ProdApplData) iter3.next();
               if(facilityCleaningSchedStructId==facilityPaD.getCleaningSchedStructId() &&
                  cleaningProcId==facilityPaD.getCleaningProcId() &&
                  itemId==facilityPaD.getItemId()) {
                  paD.setEstimatorFacilityId(pFacilityId);
                  paD.setCleaningSchedStructId(facilityCleaningSchedStructId);
                  String dilutionRateS = "";
                  BigDecimal dilutionRate = facilityPaD.getDilutionRate();
                  if(dilutionRate!=null) {
                    dilutionRate = dilutionRate.setScale(0,BigDecimal.ROUND_HALF_UP);
                    dilutionRateS = dilutionRate.toString();
                  }
                  prodApplJoinVw.setDilutionRate(dilutionRateS);

                  String usageRateS = "";
                  BigDecimal usageRate = facilityPaD.getUsageRate();
                  if(usageRate!=null) {
                    usageRateS = usageRate.toString();
                  }            
                  prodApplJoinVw.setUsageRate(usageRateS);
                  if(Utility.isSet(facilityPaD.getUnitCdNumerator())) { 
                    prodApplJoinVw.setUnitCdNumerator(facilityPaD.getUnitCdNumerator());
                  }
                  if(Utility.isSet(facilityPaD.getUnitCdDenominator())) {
                    prodApplJoinVw.setUnitCdDenominator(facilityPaD.getUnitCdDenominator());
                  }
                  if(Utility.isSet(facilityPaD.getUnitCdDenominator1())) {
                    prodApplJoinVw.setUnitCdDenominator1(facilityPaD.getUnitCdDenominator1());
                  }

                  String sharingPercentS = "";
                  BigDecimal sharingPercent = facilityPaD.getSharingPercent();
                  if(sharingPercent!=null) {
                    sharingPercentS = sharingPercent.toString();
                  }
                  prodApplJoinVw.setSharingPercent(sharingPercentS);
                  break;
               }
             }             
           }
        }
      }
      return cleaningScheduleJoinVwV;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public void saveCleaningSchedules (int pFacilityId, 
     CleaningScheduleJoinViewVector pCleaningProcProd, 
     List pCleanigScheduleTypes, int pProcessStepShift, String pUser) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      dbc = new DBCriteria();
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD,
                                                         pCleanigScheduleTypes);
      
      IdVector scheduleIdV = 
        CleaningScheduleDataAccess.selectIdOnly(con,
        CleaningScheduleDataAccess.CLEANING_SCHEDULE_ID,dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID,scheduleIdV);
      IdVector scheduleStructIdV =
        CleaningSchedStructDataAccess.selectIdOnly(con,
              CleaningSchedStructDataAccess.CLEANING_SCHED_STRUCT_ID,dbc);

      dbc = new DBCriteria();
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      dbc.addOneOf(ProdApplDataAccess.CLEANING_SCHED_STRUCT_ID, scheduleStructIdV);
      ProdApplDataAccess.remove(con,dbc);
      
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHED_STRUCT_ID,scheduleStructIdV);
      CleaningSchedStructDataAccess.remove(con,dbc);
      
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_ID,scheduleIdV);
      CleaningScheduleDataAccess.remove(con,dbc);

      int ind = -1;
       for(Iterator iter = pCleaningProcProd.iterator(); iter.hasNext();) {
         ind ++;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         CleaningScheduleData csD = csjVw.getSchedule();
         csD.setCleaningScheduleId(0);
         csD.setEstimatorFacilityId(pFacilityId);
         csD.setAddBy(pUser);
         csD.setModBy(pUser);
         String frequencyS = csjVw.getFrequency();
         if(!Utility.isSet(frequencyS)) {
           continue;
         }
         
         BigDecimal frequencyBD = new BigDecimal(frequencyS);
         if(frequencyBD.doubleValue()<0.0001) {
           continue;
         }
         csD.setFrequency(frequencyBD);
         String timePeriodCd = csjVw.getTimePeriodCd();
         csD.setTimePeriodCd(timePeriodCd);
         csD = CleaningScheduleDataAccess.insert(con,csD);
         int cleaningScheduleId = csD.getCleaningScheduleId();
         
         CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
         int ind1 = -1;
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           ind1 ++;
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           
           BigDecimal sumPercent = new BigDecimal(0);
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           int ind2 = 0;
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             BigDecimal sharingBD =  new BigDecimal(sharingS);
             if(sharingBD.doubleValue()<0.001) {
               continue;
             }
             sumPercent = sumPercent.add(sharingBD);
             break;
           }
           if(sumPercent.doubleValue()<0.001) {
             continue;
           }
           CleaningSchedStructData cssD = cssjVw.getScheduleStep();
           cssD.setCleaningSchedStructId(0);
           cssD.setCleaningScheduleId(cleaningScheduleId);
           cssD.setAddBy(pUser);
           cssD.setModBy(pUser);
           cssD = CleaningSchedStructDataAccess.insert(con,cssD);
           int cleaningSchedStructId = cssD.getCleaningSchedStructId();
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             BigDecimal sharingBD =  new BigDecimal(sharingS);
             if(sharingBD.doubleValue()<0.001) {
               continue;
             }
             ProdApplData paD = pajVw.getProdAppl();
             paD.setProdApplId(0);
             paD.setEstimatorFacilityId(pFacilityId);
             paD.setAddBy(pUser);
             paD.setModBy(pUser);
             paD.setCleaningSchedStructId(cleaningSchedStructId);
             paD.setSharingPercent(sharingBD);
             
             String dilutionRateS = pajVw.getDilutionRate();
             paD.setDilutionRate(null);
             if(dilutionRateS!=null && dilutionRateS.trim().length()>0) {              
               BigDecimal dilutionRateBD = new BigDecimal(dilutionRateS);
               if(dilutionRateBD.doubleValue()>0.01) {
                 paD.setDilutionRate(dilutionRateBD.setScale(2,BigDecimal.ROUND_HALF_UP));
               } else {
                 paD.setDilutionRate(null);
               }
             } else {
                 paD.setDilutionRate(null);
             }
             
             String usageRateS = pajVw.getUsageRate();
             
             BigDecimal usageRateBD = (Utility.isSet(usageRateS))?
                (new BigDecimal (usageRateS)):(new BigDecimal(0));
             paD.setUsageRate(usageRateBD);
             String numerator = pajVw.getUnitCdNumerator();
             paD.setUnitCdNumerator(numerator);
             String denominator = pajVw.getUnitCdDenominator();
             paD.setUnitCdDenominator(denominator);
             String denominator1 = pajVw.getUnitCdDenominator1();
             paD.setUnitCdDenominator1(denominator1);
             paD.setProdApplId(0);
             ProdApplDataAccess.insert(con,paD);
           }
         }
         /*
         dbc = new DBCriteria();
         dbc.addEqualTo(CleaningSchedFilterDataAccess.CLEANING_SCHEDULE_ID,
                 csD.getCleaningScheduleId());
         CleaningSchedFilterDataVector csfDV = csjVw.getFilter();
         if(csfDV==null) csfDV = new CleaningSchedFilterDataVector();
          CleaningSchedFilterDataVector oldCsfDV = 
                           CleaningSchedFilterDataAccess.select(con,dbc);
         for(Iterator iter1=oldCsfDV.iterator(); iter1.hasNext();) {
           boolean removeFl = true;
           CleaningSchedFilterData oldCsfD = (CleaningSchedFilterData) iter1.next();
           for(Iterator iter2=csfDV.iterator(); iter2.hasNext();) {
             CleaningSchedFilterData csfD = (CleaningSchedFilterData) iter2.next();
             if(csfD.getCleaningSchedFilterId()==oldCsfD.getCleaningSchedFilterId()) {
               CleaningSchedFilterDataAccess.update(con,csfD);
               removeFl = false;
             }
           }
           if(removeFl) {
             CleaningSchedFilterDataAccess.remove(con,oldCsfD.getCleaningSchedFilterId());
           }          
         }
         for(Iterator iter1=csfDV.iterator(); iter1.hasNext();) {
           CleaningSchedFilterData csfD = (CleaningSchedFilterData) iter1.next();
           if(csfD.getCleaningSchedFilterId()==0) {
             csfD.setCleaningScheduleId(csD.getCleaningScheduleId());
             csfD.setAddBy(pUser);
             csfD.setModBy(pUser);
             CleaningSchedFilterDataAccess.insert(con,csfD);
           }
         }
          */
       }
       setProcessStep(con, pFacilityId, pProcessStepShift, 3, pUser);
      
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  
  public CleaningScheduleJoinViewVector getDefaultCleaningSchedules (
                                                 List pCleaningScheduleTypes) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();

      dbc = new DBCriteria();
      dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD, 
                                            pCleaningScheduleTypes);
      dbc.addIsNull(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID);
      dbc.addGreaterOrEqual(CleaningScheduleDataAccess.SEQ_NUM,1);
      dbc.addOrderBy(CleaningScheduleDataAccess.SEQ_NUM);
      CleaningScheduleDataVector cleaningScheduleDV = 
             CleaningScheduleDataAccess.select(con,dbc);
      CleaningScheduleJoinViewVector cleaningScheduleJVwV = 
           new CleaningScheduleJoinViewVector();
      IdVector cleaningScheduleIdV = new IdVector();
      
      
      for(Iterator iter=cleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        cleaningScheduleIdV.add(new Integer(id));
        CleaningScheduleJoinView csjVw = CleaningScheduleJoinView.createValue();
        cleaningScheduleJVwV.add(csjVw);
        csjVw.setSchedule(csD);
        BigDecimal frequency = csD.getFrequency();
        String frequencyS = "";
        if(frequency!=null && Math.abs(frequency.doubleValue())>0.00001) {
           frequencyS = ""+frequency;
        }
        csjVw.setFrequency(frequencyS);
        String timePeriodCd = csD.getTimePeriodCd();
        if(timePeriodCd==null) timePeriodCd = "";
        csjVw.setTimePeriodCd(timePeriodCd);
      }
       
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, cleaningScheduleIdV);
      dbc.addGreaterOrEqual(CleaningSchedStructDataAccess.SEQ_NUM,1);
      dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
      CleaningSchedStructDataVector cleaningSchedStructDV = 
         CleaningSchedStructDataAccess.select(con,dbc);

      IdVector cleaningProcIdV = new IdVector();
      for(Iterator iter=cleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int procId = cssD.getCleaningProcId();
        cleaningProcIdV.add(new Integer(procId));
      }
      
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningProcDataAccess.CLEANING_PROC_ID,cleaningProcIdV);
      CleaningProcDataVector cleaningProcDV = CleaningProcDataAccess.select(con,dbc);

      
      for(Iterator iter=cleaningScheduleJVwV.iterator(); iter.hasNext();) {
        CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
        CleaningScheduleData csD = csjVw.getSchedule();
        int id = csD.getCleaningScheduleId();
        CleaningSchedStructJoinViewVector clSchedStrJoinVwV = 
             new CleaningSchedStructJoinViewVector();
        csjVw.setStructure(clSchedStrJoinVwV);

        //Schedule Structure
        for(Iterator iter1=cleaningSchedStructDV.iterator(); iter1.hasNext();) {
          CleaningSchedStructData cssD = (CleaningSchedStructData) iter1.next();
          if(id!=cssD.getCleaningScheduleId()) {
            continue;
          }
          CleaningSchedStructJoinView clSchedStrJoinVw = 
                                     CleaningSchedStructJoinView.createValue();
          clSchedStrJoinVwV.add(clSchedStrJoinVw);
          clSchedStrJoinVw.setScheduleStep(cssD);
          int cleaningProcId = cssD.getCleaningProcId();
          for(Iterator iter2=cleaningProcDV.iterator(); iter2.hasNext();) {
            CleaningProcData cpD = (CleaningProcData)iter2.next();
            if(cleaningProcId==cpD.getCleaningProcId()) {
              clSchedStrJoinVw.setProcedure(cpD);
              break;
            }
          }
        }
        csjVw.setFloorMachine("");
        dbc = new DBCriteria();
        dbc.addEqualTo(CleaningSchedFilterDataAccess.CLEANING_SCHEDULE_ID, id);
        CleaningSchedFilterDataVector csfDV =
                CleaningSchedFilterDataAccess.select(con,dbc);
        csjVw.setFilter(csfDV);
        for(Iterator iter1 = csfDV.iterator(); iter1.hasNext();) {
          CleaningSchedFilterData csfD = (CleaningSchedFilterData) iter1.next();
          if("floorMachine".equalsIgnoreCase(csfD.getPropertyName())) {
            csjVw.setFloorMachine(csfD.getPropertyValue());
          }
        }
      }

      return cleaningScheduleJVwV;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }
  
  
  public void updateDefaultCleaningSchedules (
     CleaningScheduleJoinViewVector pCleaningSchedules, String pUser) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      for(Iterator iter=pCleaningSchedules.iterator(); iter.hasNext();) {
        CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
        CleaningScheduleData csD = csjVw.getSchedule();
        csD.setModBy(pUser);
        CleaningScheduleDataAccess.update(con,csD);
        dbc = new DBCriteria();
        dbc.addEqualTo(CleaningSchedFilterDataAccess.CLEANING_SCHEDULE_ID,
                csD.getCleaningScheduleId());
        CleaningSchedFilterDataVector csfDV = csjVw.getFilter();
        if(csfDV==null) csfDV = new CleaningSchedFilterDataVector();

        CleaningSchedFilterDataVector oldCsfDV = 
                          CleaningSchedFilterDataAccess.select(con,dbc);
        for(Iterator iter1=oldCsfDV.iterator(); iter1.hasNext();) {
          boolean removeFl = true;
          CleaningSchedFilterData oldCsfD = (CleaningSchedFilterData) iter1.next();
          for(Iterator iter2=csfDV.iterator(); iter2.hasNext();) {
            CleaningSchedFilterData csfD = (CleaningSchedFilterData) iter2.next();
            if(csfD.getCleaningSchedFilterId()==oldCsfD.getCleaningSchedFilterId()) {
              CleaningSchedFilterDataAccess.update(con,csfD);
              removeFl = false;
            }
          }
          if(removeFl) {
            CleaningSchedFilterDataAccess.remove(con,oldCsfD.getCleaningSchedFilterId());
          }          
        }
        for(Iterator iter1=csfDV.iterator(); iter1.hasNext();) {
          CleaningSchedFilterData csfD = (CleaningSchedFilterData) iter1.next();
          if(csfD.getCleaningSchedFilterId()==0) {
            csfD.setCleaningScheduleId(csD.getCleaningScheduleId());
            csfD.setAddBy(pUser);
            csfD.setModBy(pUser);
            CleaningSchedFilterDataAccess.insert(con,csfD);
          }
        }
      }
      
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  
  public void updateDefaultProdAppl (
     ProdApplDataVector pProdApplDV, String pUser) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      for(Iterator iter=pProdApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        paD.setModBy(pUser);
        ProdApplDataAccess.update(con,paD);
      }
      
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public ProdApplData insertDefaultProdAppl (
     ProdApplData pProdApplD, String pUser) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      if(pProdApplD.getProdApplId()==0) {
        pProdApplD.setAddBy(pUser); 
        pProdApplD.setModBy(pUser);
        pProdApplD =  ProdApplDataAccess.insert(con,pProdApplD);
      }
      return pProdApplD;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }


  public void deleteDefaultProdAppl(IdVector pProdApplIdsToDelete) 
  throws RemoteException
  {
    Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.PROD_APPL_ID, pProdApplIdsToDelete);
      ProdApplDataAccess.remove(con,dbc);
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public EstimatorProdResultViewVector calcFloorYearProducts (int pFacilityId) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
	  con = getConnection();
      BigDecimal BD100 = new BigDecimal(100);
      EstimatorFacilityData facility = 
                           EstimatorFacilityDataAccess.select(con,pFacilityId);
      
      //int netCleanableFootage = facility.getNetCleanableFootage();
      int grossSquareFootage  = facility.getGrossFootage();
      BigDecimal cleanabeFootagePrBD = Utility.bdNN(facility.getCleanableFootagePercent());
      double cleanableFootageDb = grossSquareFootage*cleanabeFootagePrBD.doubleValue()/100;
      
      BigDecimal netCleanableFootageBD = new BigDecimal(cleanableFootageDb);
      
      BigDecimal baseboardPerBD = facility.getBaseboardPercent();
      double baseboardPerDb = (baseboardPerBD==null)?0:baseboardPerBD.doubleValue();
      int workingDayYearQty = facility.getWorkingDayYearQty();
      dbc = new DBCriteria();
      dbc.addEqualTo(FacilityFloorDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      FacilityFloorDataVector facilityFloorDV = FacilityFloorDataAccess.select(con,dbc);
      HashMap floorsSqF = new HashMap();
      ArrayList floorTypeAL = new ArrayList();

      //Calculate footage of different floors
      for(Iterator iter=facilityFloorDV.iterator(); iter.hasNext();) {
        FacilityFloorData ffD = (FacilityFloorData) iter.next();
        String floorTypeCd = ffD.getFloorTypeCd();
        BigDecimal clPr = ffD.getCleanablePercent();    
        if(clPr==null) continue;
        BigDecimal clFootage = netCleanableFootageBD.multiply(clPr).divide(BD100,2,BigDecimal.ROUND_HALF_UP);
        BigDecimal htPr = ffD.getCleanablePercentHt();
        BigDecimal htFootage = (htPr==null)? null:
         clFootage.multiply(htPr).divide(BD100,2,BigDecimal.ROUND_HALF_UP);
         
        BigDecimal mtPr = ffD.getCleanablePercentMt();
        BigDecimal mtFootage = (mtPr==null)? null:
         clFootage.multiply(mtPr).divide(BD100,2,BigDecimal.ROUND_HALF_UP);
         
        BigDecimal ltPr = ffD.getCleanablePercentLt();
        BigDecimal ltFootage = (ltPr==null)? null:
         clFootage.multiply(ltPr).divide(BD100,2,BigDecimal.ROUND_HALF_UP);

         
        if(htFootage!=null && htFootage.doubleValue()>0.001) {
          if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING,htFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING,htFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING,htFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING,htFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING,htFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING,htFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING);
          }
        }
        if(mtPr!=null && mtPr.doubleValue()>0.001) {
          if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING,mtFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING,mtFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING,mtFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING,mtFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING,mtFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING,mtFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING);
          }
        }
        if(ltPr!=null && ltPr.doubleValue()>0.001) {
          if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING,ltFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING,ltFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING,ltFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING,ltFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING,ltFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING);
          } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
            floorsSqF.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING,ltFootage);
            floorTypeAL.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING);
          }
        }
      }

      String floorTypeListS = null;
      for(Iterator iter = floorTypeAL.iterator(); iter.hasNext();) {
        if(floorTypeListS==null ) floorTypeListS = "'"+iter.next()+"'";
        else floorTypeListS += ",'"+iter.next()+"'";
      }
      
      //Get cleaning schedules
      String sql = 
          "select cs.cleaning_schedule_id, "+
          " cs.name, "+ 
          " cs.estimator_facility_id,  "+
          " cs.cleaning_schedule_cd, "+
          " cs.frequency, "+ 
          " cs.time_period_cd, "+
          " csf.property_value "+
          " from clw_cleaning_schedule cs, " +
          "      clw_cleaning_schedule def, "+
          "     clw_cleaning_sched_filter csf "+
          " where cs.name = def.name "+
          " and cs.cleaning_schedule_cd = def.cleaning_schedule_cd "+
          " and cs.estimator_facility_id = "+pFacilityId+
          " and cs.cleaning_schedule_cd in ("+floorTypeListS+")"+    
          " and def.estimator_facility_id is null "+
          " and def.cleaning_schedule_id = csf.cleaning_schedule_id(+) "+
          " and csf.property_name(+) = 'floorMachine' ";
      
      CleaningScheduleDataVector facilityCleaningScheduleDV = 
          new CleaningScheduleDataVector();
      
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      String floorMachine = facility.getFloorMachine();

      while (rs.next()) {
        String flm = rs.getString("property_value");
        if(Utility.isSet(flm) && !flm.equals(floorMachine)) {
          continue;
        }
        CleaningScheduleData csD = CleaningScheduleData.createValue();
        facilityCleaningScheduleDV.add(csD);
        csD.setEstimatorFacilityId(rs.getInt("estimator_facility_id"));
        csD.setCleaningScheduleId(rs.getInt("cleaning_schedule_id"));
        csD.setCleaningScheduleCd(rs.getString("cleaning_schedule_cd"));
        csD.setTimePeriodCd(rs.getString("time_period_cd"));
        csD.setFrequency(rs.getBigDecimal("frequency"));
      }
      rs.close();
      stmt.close();

      
      IdVector facilityCleaningScheduleIdV = new IdVector();
      for(Iterator iter=facilityCleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        facilityCleaningScheduleIdV.add(new Integer(id));
      }
            
      
      //Get cleaning schedule sturcture
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
                                                   facilityCleaningScheduleIdV);
      dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
      CleaningSchedStructDataVector facilityCleaningSchedStructDV = 
                                 CleaningSchedStructDataAccess.select(con,dbc);

      IdVector facilityCleaningProcIdV = new IdVector();
      IdVector facilityCleaningSchedStructIdV = new IdVector();
      for(Iterator iter=facilityCleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int procId = cssD.getCleaningProcId();
        facilityCleaningProcIdV.add(new Integer(procId));
        facilityCleaningSchedStructIdV.add(new Integer(cssD.getCleaningSchedStructId()));
      }
      
      //Get procedures data
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningProcDataAccess.CLEANING_PROC_ID, facilityCleaningProcIdV);
      CleaningProcDataVector facilityCleaningProcDV = 
                                         CleaningProcDataAccess.select(con,dbc);
      
      //Get product application data
      dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.CLEANING_PROC_ID,facilityCleaningProcIdV);
      dbc.addOneOf(ProdApplDataAccess.CLEANING_SCHED_STRUCT_ID,facilityCleaningSchedStructIdV);
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      dbc.addOrderBy(ProdApplDataAccess.ITEM_ID);
      ProdApplDataVector facilityProdApplDV = ProdApplDataAccess.select(con,dbc);
      
      IdVector itemIdV = new IdVector();

      for(Iterator iter=facilityProdApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        int itemId = paD.getItemId();
        itemIdV.add(new Integer(itemId));
      }
      
      //Get item info
      EstimatorProdResultViewVector estimatorProdResultVwV = 
                                           getResultItemInfo(con, facility, itemIdV);
      //Calculate quanities and prices 
      ProdApplData prodApplD = null;
      for(Iterator iter=estimatorProdResultVwV.iterator(),
                   iter1=facilityProdApplDV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        String skuNum = eprVw.getSkuNum();
        int itemId = eprVw.getItemId();
        // match product applications
        while(prodApplD!=null || iter1.hasNext()) {
          if(prodApplD==null) prodApplD = (ProdApplData) iter1.next();
          if(itemId>prodApplD.getItemId()) {
            prodApplD = null;
            continue;
          }
          if(itemId<prodApplD.getItemId()) {
            break;
          }
          int cleaningSchedStructId = prodApplD.getCleaningSchedStructId();
          
          //match cleaning step
          for(Iterator iter2=facilityCleaningSchedStructDV.iterator(); iter2.hasNext();) {
            CleaningSchedStructData cssD = (CleaningSchedStructData) iter2.next();
            int cssId = cssD.getCleaningSchedStructId();
            if(cssId!=cleaningSchedStructId) {
              continue;
            }
            int cleaningProcId = cssD.getCleaningProcId();
            CleaningProcData cleaningProcD = null;
            for(Iterator iter3=facilityCleaningProcDV.iterator(); iter3.hasNext();) {
              CleaningProcData cpD = (CleaningProcData) iter3.next();
              if(cleaningProcId==cpD.getCleaningProcId()) {
                cleaningProcD = cpD;
                break;
              }
            }
            
            int cleaningScheduleId = cssD.getCleaningScheduleId();
            CleaningScheduleData cleaningScheduleD = null;
            for(Iterator iter3=facilityCleaningScheduleDV.iterator(); iter3.hasNext();) {
              CleaningScheduleData csD = (CleaningScheduleData) iter3.next();
              if(cleaningScheduleId==csD.getCleaningScheduleId()) {
                cleaningScheduleD = csD;
                break;
              }
            }
            ///////////////////// Calculate ////////////////////////////////
            int reiteration = cssD.getReiteration();
            if(reiteration==0) reiteration = 1;
            String procName = cleaningProcD.getShortDesc();
            String    cleaningScheduleCd = cleaningScheduleD.getCleaningScheduleCd();

            BigDecimal floorFootageBD = (BigDecimal) floorsSqF.get(cleaningScheduleCd);
            double floorFootageDb =  floorFootageBD==null?0:floorFootageBD.doubleValue();

            BigDecimal sharingBD = prodApplD.getSharingPercent();
            double sharingDb = (sharingBD==null)?0: sharingBD.doubleValue();
            if(Math.abs(sharingDb)<0.001) {
              continue;
            }

            BigDecimal frequencyDB = cleaningScheduleD.getFrequency();
            double frequencyDb = (frequencyDB==null)?0: frequencyDB.doubleValue();            
            double timesPerYear = 0;
            String  timePeriodCd = cleaningScheduleD.getTimePeriodCd();
            if(RefCodeNames.TIME_PERIOD_CD.WORKING_DAY.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * workingDayYearQty;
            } else if(RefCodeNames.TIME_PERIOD_CD.DAY.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * workingDayYearQty;
            } else if(RefCodeNames.TIME_PERIOD_CD.WEEK.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * 52;
            } else if(RefCodeNames.TIME_PERIOD_CD.MONTH.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * 12;
            } else if(RefCodeNames.TIME_PERIOD_CD.QUARTER.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * 4;
            } else if(RefCodeNames.TIME_PERIOD_CD.YEAR.equals(timePeriodCd)) {
              timesPerYear = frequencyDb;
            }
            
            //Get diluted size of the pack in gallons
            String resUnitCd = "";
            BigDecimal sizeBD = eprVw.getUnitSize();
            double sizeDb = (sizeBD==null)? 0 : sizeBD.doubleValue();
            sizeDb *= eprVw.getPackQty();            
            String unitCd = eprVw.getUnitCd();
            if(RefCodeNames.UNIT_CD.LITER.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.GAL;
              sizeDb /= 3.784;
            } else if(RefCodeNames.UNIT_CD.OZ.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.GAL;
              sizeDb /= 128;
            } else if(RefCodeNames.UNIT_CD.GAL.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.GAL;
            } else if(RefCodeNames.UNIT_CD.LB.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.LB;
            }
            
            BigDecimal dilutionBD = prodApplD.getDilutionRate();
            if(dilutionBD!=null) {
              if(resUnitCd.equals(RefCodeNames.UNIT_CD.GAL)) {
                double dilutionDb = dilutionBD.doubleValue();
                sizeDb += sizeDb*dilutionDb;
              } else {
                String errorMess = "^clw^Sku"+skuNum+". Can't apply dilution to "+
                        unitCd +" unit code^clw^";
                throw new Exception(errorMess);
              }
            }

            
            BigDecimal usageRateBD = prodApplD.getUsageRate();
            double usageRateDb = (usageRateBD==null)? 0 : usageRateBD.doubleValue();
            String unitCdNumerator = prodApplD.getUnitCdNumerator();
            String resultUsageRateCd = "";
            if(RefCodeNames.UNIT_CD.LITER.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.GAL;
              usageRateDb /= 3.784;
            } else if(RefCodeNames.UNIT_CD.OZ.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.GAL;
              usageRateDb /= 128;
            } else if(RefCodeNames.UNIT_CD.GAL.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.GAL;
            } else if(RefCodeNames.UNIT_CD.LB.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.LB;
            } else if(RefCodeNames.UNIT_CD.SQ_F.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.SQ_F;
            } else if(RefCodeNames.UNIT_CD.PACK.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.PACK;
            } else if(RefCodeNames.UNIT_CD.UNIT.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.UNIT;
            }
            
            if(!resultUsageRateCd.equals(RefCodeNames.UNIT_CD.PACK) &&
               !resultUsageRateCd.equals(RefCodeNames.UNIT_CD.UNIT) &&
               !resultUsageRateCd.equals(RefCodeNames.UNIT_CD.SQ_F)) {
              if(!resultUsageRateCd.equals(resUnitCd)) {
                String errorMess = "^clw^Sku"+skuNum+". Can't apply usage rate in "+unitCdNumerator+
                     " to product in " +unitCd +"^clw^";
                throw new Exception(errorMess);
              }
            }

            if(
              !RefCodeNames.UNIT_CD.SQ_F.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.PACK.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.GAL.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.OZ.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.LB.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.UNIT.equals(unitCdNumerator)
            ) {
              String errorMess = "^clw^Sku"+skuNum+". Usage unit must be "+
                      RefCodeNames.UNIT_CD.SQ_F+" or "+
                      RefCodeNames.UNIT_CD.GAL+" or "+
                      RefCodeNames.UNIT_CD.OZ+" or "+
                      RefCodeNames.UNIT_CD.LB+" or "+
                      RefCodeNames.UNIT_CD.PACK+" or "+
                      RefCodeNames.UNIT_CD.UNIT+"^clw^";
              throw new Exception(errorMess);
            }
            
            String unitCdDenominator = prodApplD.getUnitCdDenominator();
            String unitCdDenominator1 = prodApplD.getUnitCdDenominator1();
            if(
              !RefCodeNames.UNIT_CD.GAL.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.LB.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator)
            ) {
              String errorMess = "^clw^Sku"+skuNum+". Usage denominator must be "+
                      RefCodeNames.UNIT_CD.GAL+" or "+
                      RefCodeNames.UNIT_CD.LB+" or "+
                      RefCodeNames.UNIT_CD.PROCEDURE+" or "+
                      RefCodeNames.UNIT_CD.WEEK+" or "+
                      RefCodeNames.UNIT_CD.YEAR+"^clw^";
              throw new Exception(errorMess);
            }
            
            double numberOfUsage = 0;
            if(RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator)) { 
              numberOfUsage = 1;
            } else if(RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator)) {
              numberOfUsage = 365/7;
            } else if(RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator)) { 
              numberOfUsage = timesPerYear;
            }

            //Caluculate number of packs
            double packQtyDb = 0;
            if(RefCodeNames.UNIT_CD.SQ_F.equals(resultUsageRateCd)) {
              if(RefCodeNames.UNIT_CD.GAL.equals(unitCdDenominator)||
                 RefCodeNames.UNIT_CD.LB.equals(unitCdDenominator)) {
                 if(procName.toLowerCase().indexOf("baseboard")>=0) {
                   packQtyDb = 
                   floorFootageDb*baseboardPerDb/100*timesPerYear*reiteration/usageRateDb/sizeDb;
                 } else {
                   packQtyDb = 
                   floorFootageDb*timesPerYear*reiteration/usageRateDb/sizeDb;
                 }
              } else {
                String errorMess = "^clw^Sku"+skuNum+". Wrong usage combination: "+
                        usageRateDb+" "+unitCdNumerator+ " per "+unitCdDenominator+"^clw^";
                throw new Exception(errorMess);
              }
            } else if(RefCodeNames.UNIT_CD.GAL.equals(resultUsageRateCd) ||
               RefCodeNames.UNIT_CD.LB.equals(resultUsageRateCd)) {
               if(RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator)||
                  RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator)||
                  RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator)
                  ) {
                  packQtyDb = numberOfUsage * usageRateDb/sizeDb;
               } else {
                 String errorMess = "^clw^Sku"+skuNum+". Wrong usage combination: "+
                        usageRateDb+" "+unitCdNumerator+ " per "+unitCdDenominator+"^clw^";
                 throw new Exception(errorMess);
               }
            } else if(RefCodeNames.UNIT_CD.PACK.equals(resultUsageRateCd)) {
               if(RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator)||
                  RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator)||
                  RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator)
                  ) {
                  packQtyDb = usageRateDb*numberOfUsage;
               } else {
                 String errorMess = "^clw^Sku"+skuNum+". Wrong usage combination: "+
                        usageRateDb+" "+unitCdNumerator+ " per "+unitCdDenominator+"^clw^";
                 throw new Exception(errorMess);
               }
            } else if(RefCodeNames.UNIT_CD.UNIT.equals(resultUsageRateCd)) {
               if(RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator)||
                  RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator)||
                  RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator)
                  ) {
                  packQtyDb = usageRateDb*numberOfUsage/eprVw.getPackQty();
               } else {
                 String errorMess = "^clw^Sku"+skuNum+". Wrong usage combination: "+
                        usageRateDb+" "+unitCdNumerator+ " per "+unitCdDenominator+"^clw^";
                 throw new Exception(errorMess);
               }
            } else {
              String errorMess = "^clw^Sku"+skuNum+". Unknown usage combination: "+
                     usageRateDb+" "+unitCdNumerator+ " per "+unitCdDenominator+"^clw^";
              throw new Exception(errorMess);
            }
            
            packQtyDb *= sharingDb/100;

            BigDecimal packQtyBD = 
                new BigDecimal(packQtyDb).setScale(2,BigDecimal.ROUND_HALF_UP);

            //Calculate price
            BigDecimal priceBD = eprVw.getProductPrice();
            BigDecimal yearPriceBD = new BigDecimal(0);
            if(priceBD!=null) {
              double priceDb = priceBD.doubleValue();
              double yearPriceDb = priceDb * packQtyDb;
              yearPriceBD = new BigDecimal(yearPriceDb);
            }

            if(eprVw.getYearQty()==null) {
               eprVw.setYearQty(packQtyBD.setScale(2,BigDecimal.ROUND_HALF_UP));
               eprVw.setYearPrice(yearPriceBD.setScale(2,BigDecimal.ROUND_HALF_UP));
            } else {
               eprVw.setYearQty(eprVw.getYearQty().add(packQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
               eprVw.setYearPrice(eprVw.getYearPrice().add(yearPriceBD).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
          }
          prodApplD = null;
          continue;
        }         
      }
      
      int facilityQty = facility.getFacilityQty();
      BigDecimal facilityQtyBD = new BigDecimal(facilityQty);
      for(Iterator iter=estimatorProdResultVwV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        BigDecimal yearQtyBD = eprVw.getYearQty();
        if(yearQtyBD!=null) {
          eprVw.setAllFacilityYearQty(yearQtyBD.multiply(facilityQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        BigDecimal yearPriceBD = eprVw.getYearPrice();
        if(yearPriceBD!=null) {
          eprVw.setAllFacilityYearPrice(yearPriceBD.multiply(facilityQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
      }
      
      return estimatorProdResultVwV;
        
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public EstimatorProdResultViewVector calcRestroomYearProducts (int pFacilityId) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
	  con = getConnection();
      BigDecimal BD100 = new BigDecimal(100);
      EstimatorFacilityData facility = 
                           EstimatorFacilityDataAccess.select(con,pFacilityId);
      int workingDayYearQty = facility.getWorkingDayYearQty();
      int bathroomQty = facility.getBathroomQty();
      int showerQty = facility.getShowerQty();
      BigDecimal toiletBathroomQtyBD = facility.getToiletBathroomQty();
      double toiletBathroomQtyDb = (toiletBathroomQtyBD==null)?0:
                                              toiletBathroomQtyBD.doubleValue(); 
      double toiletQty = bathroomQty * toiletBathroomQtyDb;
      
      //Get cleaning schedules
      dbc = new DBCriteria();
      dbc.addEqualTo(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD,
                                 RefCodeNames.CLEANING_SCHEDULE_CD.RESTROOM_CLEANING);
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      CleaningScheduleDataVector facilityCleaningScheduleDV = 
         CleaningScheduleDataAccess.select(con,dbc);

      IdVector facilityCleaningScheduleIdV = new IdVector();
      for(Iterator iter=facilityCleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        facilityCleaningScheduleIdV.add(new Integer(id));
      }
      
      //Get cleaning schedule sturcture
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
                                                   facilityCleaningScheduleIdV);
      dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
      CleaningSchedStructDataVector facilityCleaningSchedStructDV = 
                                 CleaningSchedStructDataAccess.select(con,dbc);

      IdVector facilityCleaningProcIdV = new IdVector();
      IdVector facilityCleaningSchedStructIdV = new IdVector();
      for(Iterator iter=facilityCleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int procId = cssD.getCleaningProcId();
        facilityCleaningProcIdV.add(new Integer(procId));
        facilityCleaningSchedStructIdV.add(new Integer(cssD.getCleaningSchedStructId()));
      }
      
      //Get procedures data
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningProcDataAccess.CLEANING_PROC_ID, facilityCleaningProcIdV);
      CleaningProcDataVector facilityCleaningProcDV = 
                                         CleaningProcDataAccess.select(con,dbc);
      
      //Get product application data
      dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.CLEANING_PROC_ID,facilityCleaningProcIdV);
      dbc.addOneOf(ProdApplDataAccess.CLEANING_SCHED_STRUCT_ID,facilityCleaningSchedStructIdV);
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      dbc.addOrderBy(ProdApplDataAccess.ITEM_ID);
      ProdApplDataVector facilityProdApplDV = ProdApplDataAccess.select(con,dbc);
      
      IdVector itemIdV = new IdVector();

      for(Iterator iter=facilityProdApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        int itemId = paD.getItemId();
        itemIdV.add(new Integer(itemId));
      }
      
      //Get item info
      EstimatorProdResultViewVector estimatorProdResultVwV = 
                                           getResultItemInfo(con, facility, itemIdV);
      //Calculate quanities and prices 
      ProdApplData prodApplD = null;
      for(Iterator iter=estimatorProdResultVwV.iterator(),
                   iter1=facilityProdApplDV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        int itemId = eprVw.getItemId();
        String skuNum = eprVw.getSkuNum();
        // match product applications
        while(prodApplD!=null || iter1.hasNext()) {
          if(prodApplD==null) prodApplD = (ProdApplData) iter1.next();
          if(itemId>prodApplD.getItemId()) {
            prodApplD = null;
            continue;
          }
          if(itemId<prodApplD.getItemId()) {
            break;
          }
          int cleaningSchedStructId = prodApplD.getCleaningSchedStructId();
          
          //match cleaning step
          for(Iterator iter2=facilityCleaningSchedStructDV.iterator(); iter2.hasNext();) {
            CleaningSchedStructData cssD = (CleaningSchedStructData) iter2.next();
            int cssId = cssD.getCleaningSchedStructId();
            if(cssId!=cleaningSchedStructId) {
              continue;
            }
            int cleaningProcId = cssD.getCleaningProcId();
            CleaningProcData cleaningProcD = null;
            for(Iterator iter3=facilityCleaningProcDV.iterator(); iter3.hasNext();) {
              CleaningProcData cpD = (CleaningProcData) iter3.next();
              if(cleaningProcId==cpD.getCleaningProcId()) {
                cleaningProcD = cpD;
                break;
              }
            }
            
            int cleaningScheduleId = cssD.getCleaningScheduleId();
            CleaningScheduleData cleaningScheduleD = null;
            for(Iterator iter3=facilityCleaningScheduleDV.iterator(); iter3.hasNext();) {
              CleaningScheduleData csD = (CleaningScheduleData) iter3.next();
              if(cleaningScheduleId==csD.getCleaningScheduleId()) {
                cleaningScheduleD = csD;
                break;
              }
            }
            ///////////////////// Calculate ////////////////////////////////
            int reiteration = cssD.getReiteration();
            String procName = cleaningProcD.getShortDesc();
            String    cleaningScheduleCd = cleaningScheduleD.getCleaningScheduleCd();

            //Get number of times per year
            BigDecimal frequencyDB = cleaningScheduleD.getFrequency();
            double frequencyDb = (frequencyDB==null)?0: frequencyDB.doubleValue();            
            double timesPerYear = 0;
            String  timePeriodCd = cleaningScheduleD.getTimePeriodCd();
            if(RefCodeNames.TIME_PERIOD_CD.WORKING_DAY.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * workingDayYearQty;
            } else if(RefCodeNames.TIME_PERIOD_CD.DAY.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * workingDayYearQty;
            } else if(RefCodeNames.TIME_PERIOD_CD.WEEK.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * 52;
            } else if(RefCodeNames.TIME_PERIOD_CD.MONTH.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * 12;
            } else if(RefCodeNames.TIME_PERIOD_CD.QUARTER.equals(timePeriodCd)) {
              timesPerYear = frequencyDb * 4;
            } else if(RefCodeNames.TIME_PERIOD_CD.YEAR.equals(timePeriodCd)) {
              timesPerYear = frequencyDb;
            }

            BigDecimal sharingBD = prodApplD.getSharingPercent();
            double sharingDb = (sharingBD==null)?0: sharingBD.doubleValue();
            
            //Get diluted size of the pack in gallons
            String resUnitCd = "";
            BigDecimal sizeBD = eprVw.getUnitSize();
            double sizeDb = (sizeBD==null)? 0 : sizeBD.doubleValue();
            sizeDb *= eprVw.getPackQty();            
            String unitCd = eprVw.getUnitCd();
            if(RefCodeNames.UNIT_CD.LITER.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.GAL;
              sizeDb /= 3.784;
            } else if(RefCodeNames.UNIT_CD.OZ.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.GAL;
              sizeDb /= 128;
            } else if(RefCodeNames.UNIT_CD.GAL.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.GAL;
            } else if(RefCodeNames.UNIT_CD.LB.equals(unitCd)) {
              resUnitCd = RefCodeNames.UNIT_CD.LB;
            }
            
            BigDecimal dilutionBD = prodApplD.getDilutionRate();
            if(dilutionBD!=null) {
              if(resUnitCd.equals(RefCodeNames.UNIT_CD.GAL)) {
                double dilutionDb = dilutionBD.doubleValue();
                sizeDb += sizeDb*dilutionDb;
              } else {
                String errorMess = "^clw^Sku"+skuNum+". Can't apply dilution to "+
                        unitCd +" unit code^clw^";
                throw new Exception(errorMess);
              }
            }
 
            //Get usage rate
            BigDecimal usageRateBD = prodApplD.getUsageRate();
            double usageRateDb = (usageRateBD==null)? 0 : usageRateBD.doubleValue();
            String unitCdNumerator = prodApplD.getUnitCdNumerator();
            String resultUsageRateCd = "";
            if(RefCodeNames.UNIT_CD.LITER.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.GAL;
              usageRateDb /= 3.784;
            } else if(RefCodeNames.UNIT_CD.OZ.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.GAL;
              usageRateDb /= 128;
            } else if(RefCodeNames.UNIT_CD.GAL.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.GAL;
            } else if(RefCodeNames.UNIT_CD.LB.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.LB;
            } else if(RefCodeNames.UNIT_CD.PACK.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.PACK;
            } else if(RefCodeNames.UNIT_CD.UNIT.equals(unitCdNumerator)) {
              resultUsageRateCd = RefCodeNames.UNIT_CD.UNIT;
            }
            
            if(!resultUsageRateCd.equals(RefCodeNames.UNIT_CD.PACK) &&
               !resultUsageRateCd.equals(RefCodeNames.UNIT_CD.UNIT)) {
              if(!resultUsageRateCd.equals(resUnitCd)) {
                String errorMess = "^clw^ Can't apply usage rate in "+unitCdNumerator+
                     "to product in " +unitCd +"^clw^";
                throw new Exception(errorMess);
              }
            }
            
            if(
              !RefCodeNames.UNIT_CD.OZ.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.GAL.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.LITER.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.LB.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.PACK.equals(unitCdNumerator) &&
              !RefCodeNames.UNIT_CD.UNIT.equals(unitCdNumerator)
            ) {
              String errorMess = "^clw^Usage unit must be "+
                      RefCodeNames.UNIT_CD.OZ+" or "+
                      RefCodeNames.UNIT_CD.GAL+" or "+
                      RefCodeNames.UNIT_CD.LITER+" or "+
                      RefCodeNames.UNIT_CD.LB+" or "+
                      RefCodeNames.UNIT_CD.PACK+" or "+
                      RefCodeNames.UNIT_CD.UNIT+"^clw^";
              throw new Exception(errorMess);
            }

            
            //Number of cleaning objects and number of product usage
            double cleaningObjectQty = 0;
            double numberOfUsage = 0;
            String unitCdDenominator = prodApplD.getUnitCdDenominator();
            String unitCdDenominator1 = prodApplD.getUnitCdDenominator1();
            if(
              !RefCodeNames.UNIT_CD.FACILITY.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.RESTROOM.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.TOILET.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.SHOWER.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.FACILITY.equals(unitCdDenominator1) &&
              !RefCodeNames.UNIT_CD.RESTROOM.equals(unitCdDenominator1) &&
              !RefCodeNames.UNIT_CD.TOILET.equals(unitCdDenominator1) &&
              !RefCodeNames.UNIT_CD.SHOWER.equals(unitCdDenominator1)
            ) {
              String errorMess = "^clw^Usage denominator must be "+
                      RefCodeNames.UNIT_CD.FACILITY+" or "+
                      RefCodeNames.UNIT_CD.RESTROOM+" or "+
                      RefCodeNames.UNIT_CD.TOILET+" or "+
                      RefCodeNames.UNIT_CD.SHOWER+"^clw^";
              throw new Exception(errorMess);
            }
            
            if(
              !RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.DAY.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.WORKING_DAY.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator) &&
              !RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator1) &&
              !RefCodeNames.UNIT_CD.DAY.equals(unitCdDenominator1) &&
              !RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator1) &&
              !RefCodeNames.UNIT_CD.WORKING_DAY.equals(unitCdDenominator1) &&
              !RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator1)
            ) {
              String errorMess = "^clw^Usage denominator must be "+
                      RefCodeNames.UNIT_CD.YEAR+" or "+
                      RefCodeNames.UNIT_CD.DAY+" or "+
                      RefCodeNames.UNIT_CD.WORKING_DAY+" or "+
                      RefCodeNames.UNIT_CD.WEEK+" or "+
                      RefCodeNames.UNIT_CD.PROCEDURE+"^clw^";
              throw new Exception(errorMess);
            }

            
            if(RefCodeNames.UNIT_CD.TOILET.equals(unitCdDenominator) ||
               RefCodeNames.UNIT_CD.TOILET.equals(unitCdDenominator1)) {
              cleaningObjectQty = toiletQty;
            } else if(RefCodeNames.UNIT_CD.SHOWER.equals(unitCdDenominator) ||
                      RefCodeNames.UNIT_CD.SHOWER.equals(unitCdDenominator1)) {
              cleaningObjectQty = showerQty;
            } else if(RefCodeNames.UNIT_CD.RESTROOM.equals(unitCdDenominator) ||
                      RefCodeNames.UNIT_CD.RESTROOM.equals(unitCdDenominator1)) { 
              cleaningObjectQty = bathroomQty;
            } else if(RefCodeNames.UNIT_CD.FACILITY.equals(unitCdDenominator) ||
                      RefCodeNames.UNIT_CD.FACILITY.equals(unitCdDenominator1)) { 
              cleaningObjectQty = 1;
            }

              
              
            if(RefCodeNames.UNIT_CD.DAY.equals(unitCdDenominator) ||
               RefCodeNames.UNIT_CD.DAY.equals(unitCdDenominator1)) { 
             numberOfUsage = 365;
            } else if(RefCodeNames.UNIT_CD.WORKING_DAY.equals(unitCdDenominator) ||
                    RefCodeNames.UNIT_CD.WORKING_DAY.equals(unitCdDenominator1)) { 
              numberOfUsage = workingDayYearQty;
            } else if(RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator) ||
                    RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator1)) { 
              numberOfUsage = 1;
            } else if(RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator) ||
                    RefCodeNames.UNIT_CD.WEEK.equals(unitCdDenominator1)) {
              numberOfUsage = 365/7;
            } else if(RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator) ||
                    RefCodeNames.UNIT_CD.PROCEDURE.equals(unitCdDenominator1)) { 
              numberOfUsage = timesPerYear;
            }
            
            //Caluculate number of packs
            double packQtyDb = 0;
            if(RefCodeNames.UNIT_CD.GAL.equals(resultUsageRateCd) ||
               RefCodeNames.UNIT_CD.LB.equals(resultUsageRateCd)) {
              double neededAmount = 
                numberOfUsage * cleaningObjectQty * usageRateDb * sharingDb /100; 
              packQtyDb = neededAmount/sizeDb;
            } else if(RefCodeNames.UNIT_CD.PACK.equals(resultUsageRateCd)) {
              packQtyDb =  
                numberOfUsage * cleaningObjectQty * usageRateDb * sharingDb /100; 
            } else if(RefCodeNames.UNIT_CD.UNIT.equals(resultUsageRateCd)) {
              packQtyDb =  
                numberOfUsage * cleaningObjectQty * usageRateDb / eprVw.getPackQty() *
                sharingDb /100; 
            } else {
              throw new Exception ("Unknown usage rate numerator: "+unitCdNumerator);
            }
            BigDecimal packQtyBD = 
                  new BigDecimal(packQtyDb).setScale(2,BigDecimal.ROUND_HALF_UP);

            //Calculate price
            BigDecimal priceBD = eprVw.getProductPrice();
            BigDecimal yearPriceBD = new BigDecimal(0);
            if(priceBD!=null) {
              double priceDb = priceBD.doubleValue();
              double yearPriceDb = priceDb * packQtyDb;
              yearPriceBD = new BigDecimal(yearPriceDb);
            }

            if(eprVw.getYearQty()==null) {
               eprVw.setYearQty(packQtyBD.setScale(2,BigDecimal.ROUND_HALF_UP));
               eprVw.setYearPrice(yearPriceBD.setScale(2,BigDecimal.ROUND_HALF_UP));
            } else {
               eprVw.setYearQty(eprVw.getYearQty().add(packQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
               eprVw.setYearPrice(eprVw.getYearPrice().add(yearPriceBD).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
          }
          prodApplD = null;
          continue;
        }         
      }
      
      int facilityQty = facility.getFacilityQty();
      BigDecimal facilityQtyBD = new BigDecimal(facilityQty);
      for(Iterator iter=estimatorProdResultVwV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        BigDecimal yearQtyBD = eprVw.getYearQty();
        if(yearQtyBD!=null) {
          eprVw.setAllFacilityYearQty(yearQtyBD.multiply(facilityQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        BigDecimal yearPriceBD = eprVw.getYearPrice();
        if(yearPriceBD!=null) {
          eprVw.setAllFacilityYearPrice(yearPriceBD.multiply(facilityQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
      }
      
      return estimatorProdResultVwV;
        
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }
  
  public EstimatorProdResultViewVector calcPaperPlusYearProducts (int pFacilityId) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
	  con = getConnection();
      BigDecimal BD100 = new BigDecimal(100);
      EstimatorProdResultViewVector estimatorProdResultVwV = new EstimatorProdResultViewVector();
      EstimatorFacilityData facility = 
                           EstimatorFacilityDataAccess.select(con,pFacilityId);
      int facilityQty = facility.getFacilityQty();
      int workingDayYearQty = facility.getWorkingDayYearQty();
      String facilityTypeCd = facility.getFacilityTypeCd();
      int bathroomQty = facility.getBathroomQty();
      int stationQty = facility.getStationQty();
      int personnelQty = facility.getPersonnelQty();
      int visitorQty = facility.getVisitorQty();
      BigDecimal visitorBathroomPercentDB = facility.getVisitorBathroomPercent();
      double visitorBathroomPercentDb = (visitorBathroomPercentDB==null)? 0:
             visitorBathroomPercentDB.doubleValue();
      BigDecimal visitorToiletTissuePercentDB = facility.getVisitorToiletTissuePercent();
      double visitorToiletTissuePercentDb = (visitorToiletTissuePercentDB==null)? 0:
             visitorToiletTissuePercentDB.doubleValue();
      BigDecimal washHandQtyBD = facility.getWashHandQty();
      double washHandQtyDb = (washHandQtyBD==null)? 0: 
              washHandQtyBD.doubleValue();
              
      BigDecimal tissueUsageQtyBD = facility.getTissueUsageQty();
      double tissueUsageQtyDb = (tissueUsageQtyBD==null)? 0: 
              tissueUsageQtyBD.doubleValue();

      BigDecimal receptacleLinerRatioBD = facility.getReceptacleLinerRatio();
      double receptacleLinerRatioDb = (receptacleLinerRatioBD==null)? 0: 
              receptacleLinerRatioBD.doubleValue();
      String linerRatioBaseCd = facility.getLinerRatioBaseCd();

      //BigDecimal additionalLinerRatioBD = facility.getAdditionalLinerRatio();
      //double additionalLinerRatioDb = (additionalLinerRatioBD==null)? 0: 
      //        additionalLinerRatioBD.doubleValue();
      int commonAreaReceptacleQty = facility.getCommonAreaReceptacleQty();

      BigDecimal toiletLinerRatioBD = facility.getToiletLinerRatio();
      double toiletLinerRatioDb = (toiletLinerRatioBD==null)? 0: 
              toiletLinerRatioBD.doubleValue();

      BigDecimal largeLinerRatioBD = facility.getLargeLinerRatio();
      double largeLinerRatioDb = (largeLinerRatioBD==null)? 0: 
              largeLinerRatioBD.doubleValue();
      
      BigDecimal largeLinerCaLinerQtyBD = facility.getLargeLinerCaLinerQty();
      double largeLinerCaLinerQtyDb = (largeLinerCaLinerQtyBD==null)? 0: 
              largeLinerCaLinerQtyBD.doubleValue();

      //Get cleaning schedules
      dbc = new DBCriteria();
      ArrayList paperPlusTypes = new ArrayList();
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HAND_SOAP_SUPPLY);
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.OFFICE_LINER_SUPPLY);
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.COMMONON_AREA_LINER_SUPPLY);
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.CONTAINER_LINER_SUPPLY);
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.BATHROOM_LINER_SUPPLY);
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.PAPER_TOWEL_SUPPLY);
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.SEAT_COVER_SUPPLY);
      paperPlusTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.TOILET_TISSUE_SUPPLY);
      dbc.addOneOf(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD,
                    paperPlusTypes);
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      CleaningScheduleDataVector facilityCleaningScheduleDV = 
         CleaningScheduleDataAccess.select(con,dbc);
      IdVector facilityCleaningScheduleIdV = new IdVector();
      for(Iterator iter=facilityCleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        facilityCleaningScheduleIdV.add(new Integer(id));
      }
      
      //Get cleaning schedule sturcture
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
                                                   facilityCleaningScheduleIdV);
      dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
      CleaningSchedStructDataVector facilityCleaningSchedStructDV = 
                                 CleaningSchedStructDataAccess.select(con,dbc);

      IdVector facilityCleaningProcIdV = new IdVector();
      IdVector facilityCleaningSchedStructIdV = new IdVector();
      for(Iterator iter=facilityCleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int procId = cssD.getCleaningProcId();
        facilityCleaningProcIdV.add(new Integer(procId));
        facilityCleaningSchedStructIdV.add(new Integer(cssD.getCleaningSchedStructId()));
      }
      
      //Get procedures data
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningProcDataAccess.CLEANING_PROC_ID, facilityCleaningProcIdV);
      CleaningProcDataVector facilityCleaningProcDV = 
                                         CleaningProcDataAccess.select(con,dbc);
      
      //Get product application data
      dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.CLEANING_PROC_ID,facilityCleaningProcIdV);
      dbc.addOneOf(ProdApplDataAccess.CLEANING_SCHED_STRUCT_ID,facilityCleaningSchedStructIdV);
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      dbc.addOrderBy(ProdApplDataAccess.ITEM_ID);
      ProdApplDataVector facilityProdApplDV = ProdApplDataAccess.select(con,dbc);
      
      IdVector itemIdV = new IdVector();

      for(Iterator iter=facilityProdApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        int itemId = paD.getItemId();
        itemIdV.add(new Integer(itemId));
      }

      //Get item info
      estimatorProdResultVwV = getResultItemInfo(con, facility, itemIdV);

      //Calculate quanities and prices 
      double trashReceptacalLinerQty = 0;
      double commonAreaTrashReceptacalLinerQty = 0;
      HashMap containerLinerItemSharingHM = new HashMap();
      
      ProdApplData prodApplD = null;
      for(Iterator iter=estimatorProdResultVwV.iterator(),
                   iter1=facilityProdApplDV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        int itemId = eprVw.getItemId();
        // match product applications
        while(prodApplD!=null || iter1.hasNext()) {
          if(prodApplD==null) prodApplD = (ProdApplData) iter1.next();
          if(itemId>prodApplD.getItemId()) {
            prodApplD = null;
            continue;
          }
          if(itemId<prodApplD.getItemId()) {
            break;
          }
          int cleaningSchedStructId = prodApplD.getCleaningSchedStructId();
          
          //match cleaning step
          for(Iterator iter2=facilityCleaningSchedStructDV.iterator(); iter2.hasNext();) {
            CleaningSchedStructData cssD = (CleaningSchedStructData) iter2.next();
            int cssId = cssD.getCleaningSchedStructId();
            if(cssId!=cleaningSchedStructId) {
              continue;
            }
            int cleaningProcId = cssD.getCleaningProcId();
            CleaningProcData cleaningProcD = null;
            for(Iterator iter3=facilityCleaningProcDV.iterator(); iter3.hasNext();) {
              CleaningProcData cpD = (CleaningProcData) iter3.next();
              if(cleaningProcId==cpD.getCleaningProcId()) {
                cleaningProcD = cpD;
                break;
              }
            }
            
            int cleaningScheduleId = cssD.getCleaningScheduleId();
            CleaningScheduleData cleaningScheduleD = null;
            for(Iterator iter3=facilityCleaningScheduleDV.iterator(); iter3.hasNext();) {
              CleaningScheduleData csD = (CleaningScheduleData) iter3.next();
              if(cleaningScheduleId==csD.getCleaningScheduleId()) {
                cleaningScheduleD = csD;
                break;
              }
            }
            ///////////////////// Calculate ////////////////////////////////
            String estimatorProductCd = prodApplD.getEstimatorProductCd();
            String cleaningScheduleCd = cleaningScheduleD.getCleaningScheduleCd();
            String unitCdNumerator = prodApplD.getUnitCdNumerator();
            String unitCdDenominator = prodApplD.getUnitCdDenominator();
            BigDecimal sizeBD = eprVw.getUnitSize();
            double sizeDb = (sizeBD==null)? 0 : sizeBD.doubleValue();
            sizeDb *= eprVw.getPackQty();            
            String unitCd = eprVw.getUnitCd();
            BigDecimal usageRateBD = prodApplD.getUsageRate();
            double usageRateDb = (usageRateBD==null)? 0 : usageRateBD.doubleValue();
            BigDecimal sharingPercentBD = prodApplD.getSharingPercent();
            double sharingPercentDb = (sharingPercentBD==null)? 0 : 
                    sharingPercentBD.doubleValue();
            BigDecimal productPriceBD = eprVw.getProductPrice();
            double productPriceDb = (productPriceBD==null)? 0 : 
                    productPriceBD.doubleValue();
        
            double quantityDb = 0;
        
            if(RefCodeNames.CLEANING_SCHEDULE_CD.PAPER_TOWEL_SUPPLY.
                                             equals(cleaningScheduleCd)) {
              if(RefCodeNames.UNIT_CD.INCH.equals(unitCd)) {
                sizeDb /= 12;
              }
              if(RefCodeNames.UNIT_CD.INCH.equals(unitCdNumerator)) {
                usageRateDb /= 12;
              }
              double visitorBathroomQtyDb = visitorQty * visitorBathroomPercentDb / 100;

              quantityDb = (personnelQty + visitorBathroomQtyDb)*
                          washHandQtyDb * workingDayYearQty * usageRateDb / sizeDb;
              quantityDb *= sharingPercentDb/100;
          
            } else if(RefCodeNames.CLEANING_SCHEDULE_CD.TOILET_TISSUE_SUPPLY.
                                                      equals(cleaningScheduleCd)) {
              if(RefCodeNames.UNIT_CD.INCH.equals(unitCd)) {
                sizeDb /= 12;
              }
              if(RefCodeNames.UNIT_CD.INCH.equals(unitCdNumerator)) {
                usageRateDb /= 12;
              }
              double visitorBathroomQtyDb = visitorQty * visitorBathroomPercentDb / 100;
              quantityDb = (sizeDb==0 || washHandQtyDb==0)? 0: 
                 (personnelQty + visitorBathroomQtyDb*(visitorToiletTissuePercentDb/100))*
                            tissueUsageQtyDb * workingDayYearQty * usageRateDb / sizeDb;
              quantityDb *= sharingPercentDb/100;

            } else if(RefCodeNames.CLEANING_SCHEDULE_CD.SEAT_COVER_SUPPLY.
                                               equals(cleaningScheduleCd)) {
              double visitorBathroomQtyDb = visitorQty * visitorBathroomPercentDb / 100;
              quantityDb = (sizeDb==0 || washHandQtyDb==0)? 0: 
                 (personnelQty + visitorBathroomQtyDb*(tissueUsageQtyDb/washHandQtyDb))*
                            tissueUsageQtyDb * workingDayYearQty * usageRateDb / sizeDb;
              quantityDb *= sharingPercentDb/100;

            } else if(RefCodeNames.CLEANING_SCHEDULE_CD.HAND_SOAP_SUPPLY.
                                               equals(cleaningScheduleCd)) {
              if(RefCodeNames.UNIT_CD.OZ.equals(unitCd)) {
                sizeDb /= 128;
              }
              if(RefCodeNames.UNIT_CD.OZ.equals(unitCdNumerator)) {
                usageRateDb /= 128;
              }
              double visitorBathroomQtyDb = visitorQty * visitorBathroomPercentDb / 100;
              quantityDb = (sizeDb==0)? 0: 
                 (personnelQty + visitorBathroomQtyDb)*
                washHandQtyDb * workingDayYearQty * usageRateDb / sizeDb;
              quantityDb *= sharingPercentDb/100;

            } else if(RefCodeNames.CLEANING_SCHEDULE_CD.OFFICE_LINER_SUPPLY.
                                                   equals(cleaningScheduleCd)) {
              double linerBaseDb = 0;
              if(RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(facilityTypeCd) ||
                RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(facilityTypeCd)) {
                  linerBaseDb = stationQty;
                } else {
                  linerBaseDb = personnelQty;
                }
              quantityDb = (receptacleLinerRatioDb==0)? 0: 
                  linerBaseDb /
                      receptacleLinerRatioDb * workingDayYearQty;
              quantityDb *= sharingPercentDb/100;

              trashReceptacalLinerQty += quantityDb;
              quantityDb /= sizeDb;                   
            } else if(RefCodeNames.CLEANING_SCHEDULE_CD.COMMONON_AREA_LINER_SUPPLY.
                                                   equals(cleaningScheduleCd)) {
              quantityDb = commonAreaReceptacleQty * workingDayYearQty;
              quantityDb *= sharingPercentDb/100;

              commonAreaTrashReceptacalLinerQty += quantityDb;
              quantityDb /= sizeDb;                   

            } else if(RefCodeNames.CLEANING_SCHEDULE_CD.BATHROOM_LINER_SUPPLY.
                                                   equals(cleaningScheduleCd)) {

              quantityDb = (toiletLinerRatioDb==0)? 0: 
                      bathroomQty / toiletLinerRatioDb * workingDayYearQty;
              quantityDb *= sharingPercentDb/100;
              quantityDb /= sizeDb;
            } else if(RefCodeNames.CLEANING_SCHEDULE_CD.CONTAINER_LINER_SUPPLY.
                                                   equals(cleaningScheduleCd)) {
              quantityDb = 0;                                
              if(sharingPercentDb>0) {
                containerLinerItemSharingHM.put(new Integer(itemId),sharingPercentBD);
              }
            }
            BigDecimal quantityBD = new BigDecimal(quantityDb);

            BigDecimal yearQty = eprVw.getYearQty();
            eprVw.setYearQty(eprVw.getYearQty().add(quantityBD).setScale(2,BigDecimal.ROUND_HALF_UP));
          }
          prodApplD = null;
          continue;
        }         
      }


      
      

      //Second loop to calculate container liners
      for(Iterator iter=estimatorProdResultVwV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        int itemId = eprVw.getItemId();
        BigDecimal sharingPercentBD = 
           (BigDecimal) containerLinerItemSharingHM.get(new Integer(itemId));
        if(sharingPercentBD!=null) {
          double quantityDb = 0;
          if(largeLinerCaLinerQtyDb>=1) {
            quantityDb += commonAreaTrashReceptacalLinerQty / largeLinerCaLinerQtyDb;            
          }
          if(largeLinerRatioDb>=1) {
            quantityDb += trashReceptacalLinerQty / largeLinerRatioDb;
          }
            
          quantityDb = quantityDb * workingDayYearQty / sharingPercentBD.doubleValue();
          quantityDb = quantityDb / eprVw.getPackQty();
          BigDecimal quantityBD = new BigDecimal(quantityDb);
          eprVw.setYearQty(eprVw.getYearQty().add(quantityBD).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
      }
      
      //Third loop to calculate the rest prices and quantities
      BigDecimal facilityQtyBD = new BigDecimal(facilityQty);
      for(Iterator iter=estimatorProdResultVwV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        BigDecimal yearQtyBD = eprVw.getYearQty();
        double yearQtyDb = yearQtyBD.doubleValue();
        BigDecimal priceBD = eprVw.getProductPrice();
        double priceDb = (priceBD==null)? 0 : priceBD.doubleValue();
        double yearPriceDb = priceDb * yearQtyDb;
        double allFacilityYearQtyDb = yearQtyDb * facilityQty;
        double allFacilityYearPriceDb = yearPriceDb * facilityQty;

        eprVw.setYearPrice(new BigDecimal(yearPriceDb).setScale(2,BigDecimal.ROUND_HALF_UP));
        eprVw.setAllFacilityYearQty(new BigDecimal(allFacilityYearQtyDb).setScale(2,BigDecimal.ROUND_HALF_UP));
        eprVw.setAllFacilityYearPrice(new BigDecimal(allFacilityYearPriceDb).setScale(2,BigDecimal.ROUND_HALF_UP));
      }

      return estimatorProdResultVwV;
        
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public EstimatorProdResultViewVector calcOtherYearProducts (int pFacilityId) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
	  con = getConnection();
      BigDecimal BD100 = new BigDecimal(100);
      EstimatorFacilityData facility = 
                           EstimatorFacilityDataAccess.select(con,pFacilityId);
      int workingDayYearQty = facility.getWorkingDayYearQty();
      int bathroomQty = facility.getBathroomQty();
      int showerQty = facility.getShowerQty();
      BigDecimal toiletBathroomQtyBD = facility.getToiletBathroomQty();
      double toiletBathroomQtyDb = (toiletBathroomQtyBD==null)?0:
                                              toiletBathroomQtyBD.doubleValue(); 
      double toiletQty = bathroomQty * toiletBathroomQtyDb;
      

      //Get cleaning schedules
      dbc = new DBCriteria();
      dbc.addEqualTo(CleaningScheduleDataAccess.CLEANING_SCHEDULE_CD,
                                 RefCodeNames.CLEANING_SCHEDULE_CD.OTHER);
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      CleaningScheduleDataVector facilityCleaningScheduleDV = 
         CleaningScheduleDataAccess.select(con,dbc);

      IdVector facilityCleaningScheduleIdV = new IdVector();
      for(Iterator iter=facilityCleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int id = csD.getCleaningScheduleId();
        facilityCleaningScheduleIdV.add(new Integer(id));
      }
      
      //Get cleaning schedule sturcture
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
                                                   facilityCleaningScheduleIdV);
      dbc.addOrderBy(CleaningSchedStructDataAccess.SEQ_NUM);
      CleaningSchedStructDataVector facilityCleaningSchedStructDV = 
                                 CleaningSchedStructDataAccess.select(con,dbc);

      IdVector facilityCleaningProcIdV = new IdVector();
      IdVector facilityCleaningSchedStructIdV = new IdVector();
      for(Iterator iter=facilityCleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int procId = cssD.getCleaningProcId();
        facilityCleaningProcIdV.add(new Integer(procId));
        facilityCleaningSchedStructIdV.add(new Integer(cssD.getCleaningSchedStructId()));
      }
      
      //Get procedures data
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningProcDataAccess.CLEANING_PROC_ID, facilityCleaningProcIdV);
      CleaningProcDataVector facilityCleaningProcDV = 
                                         CleaningProcDataAccess.select(con,dbc);
      
      //Get product application data
      dbc = new DBCriteria();
      dbc.addOneOf(ProdApplDataAccess.CLEANING_PROC_ID,facilityCleaningProcIdV);
      dbc.addOneOf(ProdApplDataAccess.CLEANING_SCHED_STRUCT_ID,facilityCleaningSchedStructIdV);
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      dbc.addOrderBy(ProdApplDataAccess.ITEM_ID);
      ProdApplDataVector facilityProdApplDV = ProdApplDataAccess.select(con,dbc);
      
      IdVector itemIdV = new IdVector();

      for(Iterator iter=facilityProdApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        int itemId = paD.getItemId();
        itemIdV.add(new Integer(itemId));
      }
      
      //Get item info
      EstimatorProdResultViewVector estimatorProdResultVwV = 
                                           getResultItemInfo(con, facility, itemIdV);
      //Calculate quanities and prices 
      ProdApplData prodApplD = null;
      for(Iterator iter=estimatorProdResultVwV.iterator(),
                   iter1=facilityProdApplDV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        int itemId = eprVw.getItemId();
        // match product applications
        while(prodApplD!=null || iter1.hasNext()) {
          if(prodApplD==null) prodApplD = (ProdApplData) iter1.next();
          if(itemId>prodApplD.getItemId()) {
            prodApplD = null;
            continue;
          }
          if(itemId<prodApplD.getItemId()) {
            break;
          }
          int cleaningSchedStructId = prodApplD.getCleaningSchedStructId();
          
          //match cleaning step
          for(Iterator iter2=facilityCleaningSchedStructDV.iterator(); iter2.hasNext();) {
            CleaningSchedStructData cssD = (CleaningSchedStructData) iter2.next();
            int cssId = cssD.getCleaningSchedStructId();
            if(cssId!=cleaningSchedStructId) {
              continue;
            }
            int cleaningProcId = cssD.getCleaningProcId();
            CleaningProcData cleaningProcD = null;
            for(Iterator iter3=facilityCleaningProcDV.iterator(); iter3.hasNext();) {
              CleaningProcData cpD = (CleaningProcData) iter3.next();
              if(cleaningProcId==cpD.getCleaningProcId()) {
                cleaningProcD = cpD;
                break;
              }
            }
            
            int cleaningScheduleId = cssD.getCleaningScheduleId();
            CleaningScheduleData cleaningScheduleD = null;
            for(Iterator iter3=facilityCleaningScheduleDV.iterator(); iter3.hasNext();) {
              CleaningScheduleData csD = (CleaningScheduleData) iter3.next();
              if(cleaningScheduleId==csD.getCleaningScheduleId()) {
                cleaningScheduleD = csD;
                break;
              }
            }
            ///////////////////// Calculate ////////////////////////////////
            int reiteration = cssD.getReiteration();
            String procName = cleaningProcD.getShortDesc();
            String    cleaningScheduleCd = cleaningScheduleD.getCleaningScheduleCd();

            BigDecimal sharingBD = prodApplD.getSharingPercent();
            double sharingDb = (sharingBD==null)?0: sharingBD.doubleValue();
                        
            //Get usage rate
            BigDecimal usageRateBD = prodApplD.getUsageRate();
            double usageRateDb = (usageRateBD==null)? 0 : usageRateBD.doubleValue();
            
            //Get product mesure
            String unitCdNumerator = prodApplD.getUnitCdNumerator();
            if(!RefCodeNames.UNIT_CD.PACK.equals(unitCdNumerator) &&
               !RefCodeNames.UNIT_CD.UNIT.equals(unitCdNumerator)) {
              throw new Exception ("^clw^Usage rate numerator is not: " +
                       RefCodeNames.UNIT_CD.PACK + " or "+
                       RefCodeNames.UNIT_CD.UNIT+"^clw^");
            }
            
            String unitCdDenominator = prodApplD.getUnitCdDenominator();
            String unitCdDenominator1 = prodApplD.getUnitCdDenominator1();
            if(
               !RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator) &&
               !RefCodeNames.UNIT_CD.DAY.equals(unitCdDenominator) &&
               !RefCodeNames.UNIT_CD.WORKING_DAY.equals(unitCdDenominator) &&
               !RefCodeNames.UNIT_CD.YEAR.equals(unitCdDenominator1) &&
               !RefCodeNames.UNIT_CD.DAY.equals(unitCdDenominator1) &&
               !RefCodeNames.UNIT_CD.WORKING_DAY.equals(unitCdDenominator1) 
               )  {
              throw new Exception ("^clw^Usage rate denominator is not: "+
                       RefCodeNames.UNIT_CD.YEAR + " or "+
                       RefCodeNames.UNIT_CD.DAY + " or "+
                       RefCodeNames.UNIT_CD.WORKING_DAY+"^clw^");
            }
            if(
               !RefCodeNames.UNIT_CD.FACILITY.equals(unitCdDenominator) &&
               !RefCodeNames.UNIT_CD.FACILITY.equals(unitCdDenominator1)
            )  {
              throw new Exception ("^clw^Usage rate denominator is not: "+
                       RefCodeNames.UNIT_CD.FACILITY+"^clw^");
            }
            
            
            //Caluculate number of packs per year
            double packQtyDb = usageRateDb;
            if(RefCodeNames.UNIT_CD.UNIT.equals(unitCdNumerator)) {
              packQtyDb *= eprVw.getPackQty();
            }
            if(RefCodeNames.UNIT_CD.DAY.equals(unitCdDenominator)) {
              packQtyDb *= 365;
            }
            if(RefCodeNames.UNIT_CD.WORKING_DAY.equals(unitCdDenominator)) {
              packQtyDb *= workingDayYearQty;
            }
            packQtyDb = packQtyDb * sharingDb / 100;

            BigDecimal packQtyBD = 
                new BigDecimal(packQtyDb).setScale(2,BigDecimal.ROUND_HALF_UP);

            //Calculate price
            BigDecimal priceBD = eprVw.getProductPrice();
            BigDecimal yearPriceBD = new BigDecimal(0);
            if(priceBD!=null) {
              double priceDb = priceBD.doubleValue();
              double yearPriceDb = priceDb * packQtyDb;
              yearPriceBD = new BigDecimal(yearPriceDb);
            }

            if(eprVw.getYearQty()==null) {
               eprVw.setYearQty(packQtyBD.setScale(2,BigDecimal.ROUND_HALF_UP));
               eprVw.setYearPrice(yearPriceBD.setScale(2,BigDecimal.ROUND_HALF_UP));
            } else {
               eprVw.setYearQty(eprVw.getYearQty().add(packQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
               eprVw.setYearPrice(eprVw.getYearPrice().add(yearPriceBD).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
          }
          prodApplD = null;
          continue;
        }         
      }
      
      int facilityQty = facility.getFacilityQty();
      BigDecimal facilityQtyBD = new BigDecimal(facilityQty);
      for(Iterator iter=estimatorProdResultVwV.iterator(); iter.hasNext();) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
        BigDecimal yearQtyBD = eprVw.getYearQty();
        if(yearQtyBD!=null) {
          eprVw.setAllFacilityYearQty(yearQtyBD.multiply(facilityQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        BigDecimal yearPriceBD = eprVw.getYearPrice();
        if(yearPriceBD!=null) {
          eprVw.setAllFacilityYearPrice(yearPriceBD.multiply(facilityQtyBD).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
      }
      
      return estimatorProdResultVwV;
        
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }
 
  
  //Get item info
  private EstimatorProdResultViewVector 
     getResultItemInfo(Connection pCon, EstimatorFacilityData pFacility, IdVector pItemIdV) 
  throws Exception 
  {
   
    EstimatorProdResultViewVector estimatorProdResultVwV = 
                                         new EstimatorProdResultViewVector();

    DBCriteria  dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID,pItemIdV);
    dbc.addOrderBy(ItemDataAccess.ITEM_ID);
    ItemDataVector itemDV = ItemDataAccess.select(pCon,dbc);

    //Get uom,pack info
    dbc = new DBCriteria();
    dbc.addOneOf(ProductUomPackDataAccess.ITEM_ID,pItemIdV);
    dbc.addOrderBy(ProductUomPackDataAccess.ITEM_ID);
    ProductUomPackDataVector productUomPackDV = 
                                ProductUomPackDataAccess.select(pCon,dbc);
    int catalogId = pFacility.getCatalogId();

    //Get cateogory date
    dbc = new DBCriteria();
    dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, pItemIdV);
    dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,catalogId);
    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
       RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
    dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
    
    ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(pCon,dbc);
    IdVector categoryIdV = new IdVector();
    int prevCategoryId = 0;
    for(Iterator iter=itemAssocDV.iterator(); iter.hasNext();) {
       ItemAssocData iaD = (ItemAssocData) iter.next();
       int catId = iaD.getItem2Id();
       if(prevCategoryId!=catId) {
         prevCategoryId = catId;
         categoryIdV.add(new Integer(catId));
       }
    }
    
    dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID, categoryIdV);
    ItemDataVector categoryDV = ItemDataAccess.select(pCon,dbc);
    
    //Get Major Category
    dbc = new DBCriteria();
    dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, categoryIdV);
    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                  RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);
    ItemAssocDataVector categoryAssocDV = ItemAssocDataAccess.select(pCon,dbc);
    
    IdVector majorCategoryIdV = new IdVector();
    int prevMjCatId = 0;
    for(Iterator iter=categoryAssocDV.iterator(); iter.hasNext();) {
      ItemAssocData catAssocD = (ItemAssocData) iter.next();
      int mjCatId = catAssocD.getItem2Id();
      if(prevMjCatId!=mjCatId) {
        prevMjCatId = mjCatId;
        majorCategoryIdV.add(new Integer(mjCatId));
      }
    }
    
    dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID, majorCategoryIdV);
    ItemDataVector majorCategoryDV = ItemDataAccess.select(pCon,dbc);
    

    //Get contract item data
    dbc = new DBCriteria();
    dbc.addEqualTo(ContractDataAccess.CATALOG_ID,catalogId);
    dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                                  RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
    ContractDataVector contractDV = 
      ContractDataAccess.select(pCon,dbc);
    if(contractDV.size()==0) {
      throw new Exception("No active contract for catalog: "+catalogId);
    }

    ContractData contractD = (ContractData) contractDV.get(0);
    int contractId = contractD.getContractId();

    dbc = new DBCriteria();
    dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID,contractId);
    dbc.addOneOf(ContractItemDataAccess.ITEM_ID,pItemIdV);
    dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);

    ContractItemDataVector contractItemDV = 
                                  ContractItemDataAccess.select(pCon,dbc);

    //Gather item, uom, pack and price data alltogether
    ContractItemData contractItemD = null;
    ProductUomPackData productUomPackD = null;
    ItemAssocData itemAssocD = null;
    for(Iterator iter=itemDV.iterator(), 
                 iter1=contractItemDV.iterator(), 
                 iter2=productUomPackDV.iterator(), 
                 iter3=itemAssocDV.iterator();
    
        iter.hasNext();) {
      ItemData itemD = (ItemData) iter.next();
      int itemId = itemD.getItemId();
      EstimatorProdResultView eprVw = EstimatorProdResultView.createValue();
      estimatorProdResultVwV.add(eprVw);
      eprVw.setEstimatorFacilityId(pFacility.getEstimatorFacilityId());
      eprVw.setItemId(itemId);
      eprVw.setSkuNum(""+itemD.getSkuNum());
      eprVw.setProductName(itemD.getShortDesc());
      while(contractItemD!=null || iter1.hasNext()) {
        if(contractItemD==null) contractItemD = (ContractItemData) iter1.next();
        if(itemId>contractItemD.getItemId()) {
           contractItemD = null;
           continue;
        }
        if(itemId<contractItemD.getItemId()) {
          break;
        }
        eprVw.setProductPrice(contractItemD.getAmount());
        contractItemD = null;
        break;
      }
      while(productUomPackD!=null || iter2.hasNext()) {
        if(productUomPackD==null) productUomPackD = 
                       (ProductUomPackData) iter2.next();
        if(itemId>productUomPackD.getItemId()) {
           productUomPackD = null;
           continue;
        }
        if(itemId<productUomPackD.getItemId()) {
          break;
        }
        eprVw.setUnitSize(productUomPackD.getUnitSize());
        eprVw.setUnitCd(productUomPackD.getUnitCd());
        eprVw.setPackQty(productUomPackD.getPackQty());
        eprVw.setYearQty(new BigDecimal(0));
        eprVw.setYearPrice(new BigDecimal(0));
        eprVw.setAllFacilityYearQty(new BigDecimal(0));
        eprVw.setAllFacilityYearQty(new BigDecimal(0));
        productUomPackD = null;
        break;
      }
      while(itemAssocD!=null || iter3.hasNext()) {
        if(itemAssocD==null) itemAssocD = (ItemAssocData) iter3.next();
        int item1Id = itemAssocD.getItem1Id();
        if(itemId>item1Id) {
          itemAssocD=null;
          continue;
        }
        if(itemId<item1Id) {
          break;
        }
        int categoryId = itemAssocD.getItem2Id();
        for(Iterator iter4=categoryDV.iterator(); iter4.hasNext();) {
          ItemData categoryD = (ItemData) iter4.next();
          if(categoryId==categoryD.getItemId()) {
            eprVw.setCategory(categoryD.getShortDesc());
            for(Iterator iter5=categoryAssocDV.iterator(); iter5.hasNext();) {
              ItemAssocData categoryAssocD = (ItemAssocData) iter5.next();
              if(categoryId==categoryAssocD.getItem1Id()) {
                int mjCatId = categoryAssocD.getItem2Id();
                for(Iterator iter6=majorCategoryDV.iterator(); iter6.hasNext();) {
                  ItemData mjCatD = (ItemData) iter6.next();
                  if(mjCatId==mjCatD.getItemId()) {
                    eprVw.setMajorCategory(mjCatD.getShortDesc());
                    break;
                  }
                }
               
                break;
              }
            }
            
            break;
          }
        }
        break;
      }
    }
    return estimatorProdResultVwV;
  }
  
  public AllocatedCategoryViewVector getAllocatedCategories(int pFacilityId) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      AllocatedCategoryViewVector allocatedCategoryVwV = 
                                           new AllocatedCategoryViewVector();
      EstimatorFacilityData facilty = 
                         EstimatorFacilityDataAccess.select(con,pFacilityId);
      dbc = new DBCriteria();
      dbc.addEqualTo(AllocatedCategoryDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      dbc.addOrderBy(AllocatedCategoryDataAccess.SEQ_NUM);
      AllocatedCategoryDataVector allocatedCategoryDV =
                            AllocatedCategoryDataAccess.select(con,dbc);
      if(allocatedCategoryDV.size()==0) {         
        EstimatorFacilityData  facility = getDefaultFacilityProfile();
        dbc = new DBCriteria();
        dbc.addEqualTo(AllocatedCategoryDataAccess.ESTIMATOR_FACILITY_ID, 
                                      facility.getEstimatorFacilityId());
        dbc.addOrderBy(AllocatedCategoryDataAccess.SEQ_NUM);
        allocatedCategoryDV = AllocatedCategoryDataAccess.select(con,dbc);
      } 
      for(Iterator iter = allocatedCategoryDV.iterator(); iter.hasNext();) {
         AllocatedCategoryData acD = (AllocatedCategoryData) iter.next();
         AllocatedCategoryView acVw = AllocatedCategoryView.createValue();
         allocatedCategoryVwV.add(acVw);
         BigDecimal allocatedPercent = acD.getPercent();
         acVw.setAllocatedCategory(acD);
         if(allocatedPercent==null) allocatedPercent = new BigDecimal(0);
         acVw.setAllocatedPercent(""+allocatedPercent);
         acVw.setAmount(null);
      }
      return allocatedCategoryVwV;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }
  
  public void saveAllocatedCategories(int pFacilityId,
     AllocatedCategoryViewVector pAllocatedCategoryVwV, String pUser ) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      AllocatedCategoryViewVector allocatedCategoryVwV = 
                                           new AllocatedCategoryViewVector();
      dbc = new DBCriteria();
      dbc.addEqualTo(AllocatedCategoryDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      AllocatedCategoryDataAccess.remove(con,dbc);
      
      for(Iterator iter = pAllocatedCategoryVwV.iterator(); iter.hasNext();) {
        AllocatedCategoryView acVw = (AllocatedCategoryView) iter.next();
        String allocatedPercentS = acVw.getAllocatedPercent();
        BigDecimal allocatedPercent = new BigDecimal(allocatedPercentS);
        AllocatedCategoryData acD = acVw.getAllocatedCategory();
        acD.setEstimatorFacilityId(pFacilityId);
        acD.setPercent(allocatedPercent);
        acD.setAddBy(pUser);
        acD.setModBy(pUser);
        AllocatedCategoryDataAccess.insert(con,acD);
      }
      setProcessStep(con, pFacilityId, 3, 3, pUser);
           
      
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public EstimatorFacilityData copyModel(int pFacilityId, String pName, String pUser ) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      EstimatorFacilityData efD = EstimatorFacilityDataAccess.select(con,pFacilityId);
      
      //Copy order guide
      int orderGuideId = efD.getOrderGuideId();
      if(orderGuideId>0) {
        OrderGuideData ogD = OrderGuideDataAccess.select(con,orderGuideId);
        APIAccess apiAccess = getAPIAccess();
        OrderGuide orderGuideEjb = apiAccess.getOrderGuideAPI();
        OrderGuideInfoData ogiD = orderGuideEjb.createFromOrderGuide(ogD,pUser);
        int newOrderGuideId = ogiD.getOrderGuideData().getOrderGuideId();
        efD.setOrderGuideId(newOrderGuideId);
      }
      
      //Copy estimator facility  object
      efD.setEstimatorFacilityId(0);
      String name = pName;
      if(!Utility.isSet(name)) {
        name = "Copy of "+efD.getName();
      }
      if(name.length()>255) name = name.substring(0,255);
      efD.setName(name);
      efD.setAddBy(pUser);
      efD.setModBy(pUser);
      efD = EstimatorFacilityDataAccess.insert(con,efD);
      int newFacilityId = efD.getEstimatorFacilityId();
      
      // Copy floor percentage
      dbc = new DBCriteria();
      dbc.addEqualTo(FacilityFloorDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      FacilityFloorDataVector facilityFloorDV = 
                           FacilityFloorDataAccess.select(con,dbc);
      for(Iterator iter=facilityFloorDV.iterator(); iter.hasNext();) {
        FacilityFloorData ffD = (FacilityFloorData) iter.next();
        ffD.setFacilityFloorId(0);
        ffD.setEstimatorFacilityId(newFacilityId);
        ffD.setAddBy(pUser);
        ffD.setModBy(pUser);
        ffD = FacilityFloorDataAccess.insert(con,ffD);
      }

      //Copy cleaning schedules
      dbc = new DBCriteria();
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      CleaningScheduleDataVector cleaningScheduleDV = 
              CleaningScheduleDataAccess.select(con,dbc);

      CleaningScheduleDataVector newCleaningScheduleDV = 
              new  CleaningScheduleDataVector();
      
      IdVector oldCleaningScheduleIdV = new IdVector();
      HashMap cleaningScheduleOldNewHM = new HashMap();
      for(Iterator iter=cleaningScheduleDV.iterator(); iter.hasNext();) {
        CleaningScheduleData csD = (CleaningScheduleData) iter.next();
        int csId = csD.getCleaningScheduleId();
        Integer csIdI = new Integer(csId);
        csD.setEstimatorFacilityId(newFacilityId);
        csD.setCleaningScheduleId(0);
        csD.setAddBy(pUser);
        csD.setModBy(pUser);
        csD = CleaningScheduleDataAccess.insert(con, csD);
        newCleaningScheduleDV.add(csD);
        cleaningScheduleOldNewHM.put(csIdI,csD);
        oldCleaningScheduleIdV.add(csIdI);
      }
           
      //Copy cleaning schedule structure
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
              oldCleaningScheduleIdV);
      CleaningSchedStructDataVector cleaningSchedStructDV = 
          CleaningSchedStructDataAccess.select(con,dbc);
      
      HashMap cleaningSchedStructOldNewHM = new HashMap();
      for(Iterator iter=cleaningSchedStructDV.iterator(); iter.hasNext();) {
        CleaningSchedStructData cssD = (CleaningSchedStructData) iter.next();
        int cssId = cssD.getCleaningSchedStructId();
        Integer cssIdI = new Integer(cssId);
        cssD.setCleaningSchedStructId(0);
        int csId = cssD.getCleaningScheduleId();
        CleaningScheduleData csD = 
           (CleaningScheduleData) cleaningScheduleOldNewHM.get(new Integer(csId));
        if(csD!=null) {
          cssD.setCleaningScheduleId(csD.getCleaningScheduleId());
          cssD = CleaningSchedStructDataAccess.insert(con,cssD);
          cssD.setAddBy(pUser);
          cssD.setModBy(pUser);
          cleaningSchedStructOldNewHM.put(cssIdI,cssD);
        }
      }
      
      //Copy product application records
      dbc = new DBCriteria();
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      ProdApplDataVector prodApplDV = ProdApplDataAccess.select(con,dbc);
      for(Iterator iter=prodApplDV.iterator(); iter.hasNext();) {
        ProdApplData paD = (ProdApplData) iter.next();
        int paId = paD.getProdApplId();
        paD.setProdApplId(0);
        paD.setEstimatorFacilityId(newFacilityId);
        paD.setAddBy(pUser);
        paD.setModBy(pUser);
        int cssId = paD.getCleaningSchedStructId();
        CleaningSchedStructData cssD = 
          (CleaningSchedStructData) cleaningSchedStructOldNewHM.get(new Integer(cssId));
        if(cssD!=null) {
          paD.setCleaningSchedStructId(cssD.getCleaningSchedStructId());
          paD = ProdApplDataAccess.insert(con,paD);
        }
        
      }
      
      //Copy allocated percents 
      dbc = new DBCriteria();
      dbc.addEqualTo(AllocatedCategoryDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      AllocatedCategoryDataVector allocatedCategoryDV = 
         AllocatedCategoryDataAccess.select(con,dbc);
      
      for(Iterator iter=allocatedCategoryDV.iterator(); iter.hasNext();) {
        AllocatedCategoryData acD = (AllocatedCategoryData) iter.next();
        acD.setAllocatedCategoryId(0);
        acD.setEstimatorFacilityId(newFacilityId);
        acD.setAddBy(pUser);
        acD.setModBy(pUser);
        acD = AllocatedCategoryDataAccess.insert(con,acD);
      }
      
      return efD;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }
  
  public EstimatorFacilityData createModelFromTemplate(int pTemplateId, 
                            String pName, String pUser ) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      EstimatorFacilityData efD = copyModel(pTemplateId, pName, pUser );
      efD.setTemplateFl("F");
      efD.setTemplateFacilityId(pTemplateId);
      EstimatorFacilityDataAccess.update(con,efD);
      return efD;
    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  public void deleteModelFromDb(int pFacilityId) 
  throws RemoteException
  {
	Connection con=null;
    DBCriteria dbc;
	try {
      con = getConnection();
      EstimatorFacilityData efD = EstimatorFacilityDataAccess.select(con,pFacilityId);
      
      //Delete order guide
      int orderGuideId = efD.getOrderGuideId();
      if(orderGuideId>0) {
        APIAccess apiAccess = getAPIAccess();
        OrderGuide orderGuideEjb = apiAccess.getOrderGuideAPI();
        orderGuideEjb.removeOrderGuide(orderGuideId);
      }
      
      //Delete allocated percents 
      dbc = new DBCriteria();
      dbc.addEqualTo(AllocatedCategoryDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      AllocatedCategoryDataAccess.remove(con,dbc);

      //Delete product applications
      dbc = new DBCriteria();
      dbc.addEqualTo(ProdApplDataAccess.ESTIMATOR_FACILITY_ID, pFacilityId);
      ProdApplDataAccess.remove(con,dbc);


      //
      dbc = new DBCriteria();
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      IdVector cleaningScheduleIdV = 
              CleaningScheduleDataAccess.selectIdOnly(con,dbc);

      
           
      //Remove cleaning schedule structure
      dbc = new DBCriteria();
      dbc.addOneOf(CleaningSchedStructDataAccess.CLEANING_SCHEDULE_ID, 
              cleaningScheduleIdV);
      CleaningSchedStructDataAccess.remove(con,dbc);
      
      
      // Delete cleanig schedules
      dbc = new DBCriteria();
      dbc.addEqualTo(CleaningScheduleDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      CleaningScheduleDataAccess.remove(con,dbc);
      
      // Delete facility floor
      dbc = new DBCriteria();
      dbc.addEqualTo(FacilityFloorDataAccess.ESTIMATOR_FACILITY_ID,pFacilityId);
      FacilityFloorDataAccess.remove(con,dbc);
      
      //Delete estimator facility  object
      EstimatorFacilityDataAccess.remove(con,pFacilityId);

    }catch(Exception exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException(exc.getMessage());
	}finally{
	    try {
		con.close();
	    }catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException(exc.getMessage());
	    }
    }
  }

  
}

