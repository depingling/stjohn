/*
 * Distributor Account Item Report.java
 *
 * Created on August 11, 2004, 9:47 PM
 * Created by Yuriy Kupershmidt
 */


package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.Utility;

import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.math.BigDecimal;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * Picks up distributor items filtered by account and adds year to date trade infrmation
 * @param the distributor identifier (mandatory)
 * @param the account identifier
 * List of Skus or
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class DistAccountItemReport implements GenericReport {



    /** Creates a new instance of DistAccoutItemReport */
    public DistAccountItemReport() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception
    {
        Connection con = pCons.getDefaultConnection();
        DBCriteria dbc;
        String errorMess = "No error";
        ContractItemProfitViewVector items = new ContractItemProfitViewVector();
        GenericReportResultView result = GenericReportResultView.createValue();
        String manuIdS = (String)ReportingUtils.getParam(pParams, "MANUFACTURER");
        String distIdS = (String) ReportingUtils.getParam(pParams, "DISTRIBUTOR"); 
        String skuList = (String) ReportingUtils.getParam(pParams, "SKU_MULTI"); 
        String acctIdListParam = (String) ReportingUtils.getParam(pParams, "ACCOUNT_MULTI");
      //  int storeId = Integer.parseInt((String) pParams.get("STORE_SELECT"));
        //String storeId = ReportingUtils.getParam(pParams, "STORE");
        String storeIdS = (String) pParams.get("STORE_SELECT");
        // Date interval
        String begDateS = null;
        String endDateS = null;
        begDateS = (String) pParams.get("BEG_DATE");
        endDateS = (String) pParams.get("END_DATE");

        if(!ReportingUtils.isValidDate(begDateS) && begDateS.length()!=0){
            String mess = "^clw^\""+begDateS+"\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }
        if(!ReportingUtils.isValidDate(endDateS) && endDateS.length()!=0){
            String mess = "^clw^\""+endDateS+"\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }
        int distId = 0;
        if(Utility.isSet(distIdS)){
            try{
                distId = Integer.parseInt(distIdS);
            }catch(NumberFormatException e){
                throw new NumberFormatException("^clw^Wrong distributor id format: "+distIdS+"^clw^");
            }
        }
        
        int storeId = 0;
        
        if(Utility.isSet(storeIdS)){

            try{

                storeId = Integer.parseInt(storeIdS);

            }catch(NumberFormatException e){

                throw new NumberFormatException("^clw^Wrong distributor id format: "+storeIdS+"^clw^");

            }

        }
        int manuId = 0;
        if(Utility.isSet(manuIdS)){
            try{
            	manuId = Integer.parseInt(manuIdS);
            }catch(NumberFormatException e){
                throw new NumberFormatException("^clw^Wrong manufacturer id format: "+manuIdS+"^clw^");
            }
        }


        String itemIdList = null;
        if(Utility.isSet(skuList)){
          StringTokenizer tok = new StringTokenizer(skuList.trim(),",");

          while(tok.hasMoreTokens()){
            String skuNumS=tok.nextToken().trim();
            dbc = new DBCriteria();
            int skuNum = 0;
            try {
              skuNum = Integer.parseInt(skuNumS);
            } catch (Exception exc){}
            if(skuNum==0) {
              throw new NumberFormatException("^clw^Wrong sku format: "+skuNumS+"^clw^");
            }
            dbc.addEqualTo(ItemDataAccess.SKU_NUM,skuNum);
            dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                                             RefCodeNames.ITEM_TYPE_CD.PRODUCT);
            IdVector itemIdV = ItemDataAccess.selectIdOnly(con,dbc);
            if(itemIdV.size()==0) {
              throw new NumberFormatException("^clw^Wrong sku number: "+skuNum+"^clw^");
            }
            if(itemIdV.size()>1) {
              throw new NumberFormatException("^clw^Muliple items with the same sku number: "+skuNum+"^clw^");
            }
            Integer itemIdI = (Integer) itemIdV.get(0);
            if(itemIdList!=null) {
              itemIdList += ","+itemIdI;
            } else {
              itemIdList = ""+itemIdI;
            }
          }
        }

      ///////////////////////////
        String acctIdList = null;
        if(Utility.isSet(acctIdListParam)){
          StringTokenizer tok = new StringTokenizer(acctIdListParam.trim(),",");
          while(tok.hasMoreTokens()){
            String acctIdS=tok.nextToken().trim();
            dbc = new DBCriteria();
            int acctId = 0;
            try {
              acctId = Integer.parseInt(acctIdS);
            } catch (Exception exc){}
            if(acctId==0) {
              throw new NumberFormatException("^clw^Wrong account id format: "+acctIdS+"^clw^");
            }
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,acctId);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                                             RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            IdVector acctIdV = BusEntityDataAccess.selectIdOnly(con,dbc);
            if(acctIdV.size()==0) {
              throw new NumberFormatException("^clw^Wrong accout id: "+acctId+"^clw^");
            }
            Integer acctIdI = (Integer) acctIdV.get(0);
            if(acctIdList!=null) {
              acctIdList += ","+acctIdI;
            } else {
              acctIdList = ""+acctIdI;
            }
          }
        }
        if(distId==0 && acctIdList==null && itemIdList==null) {
          throw new NumberFormatException("^clw^No parameters found. Request is too broad^clw^");
        }

        String sql = "select cata.bus_entity_id account_id, acct.short_desc acct_name, acct.erp_num acct_erp, \n" +
		"catcon.catalog_id, catcon.contract_id, catcon.contract_name, \n" +
		"catcon.item_id,item.sku_num, item.short_desc item_desc, cat.short_desc cat, \n" +
		"mfg.short_desc mfg_name, imm.item_num mfg_sku, \n" +
		"color, list_price, pack, sizei, uom, \n" +
		"dist.bus_entity_id dist_id, dist.short_desc dist_name, dist.erp_num dist_erp,imd.item_num dist_sku, imd.item_uom dist_uom, imd.item_pack dist_pack, \n" +
		"mfg1.short_desc mfg1_name, imgm.item_num mfg1_sku, \n" +
		"coni.amount, coni.dist_cost, coni.dist_base_cost \n" +
		"from  (\n" +
		"select con.contract_id, con.short_desc contract_name, con.contract_status_cd,   cats.item_id, cats.catalog_id, cats.bus_entity_id \n" +
		"from clw_catalog_structure cats,  clw_contract con \n" +
		"where 1=1 \n" +
		((distId!=0)?(" and cats.bus_entity_id = "+distId):"") +
        ((manuId!=0)?(" and cats.item_id in (select item_id from clw_item_mapping where item_mapping_cd = '"+RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER+"' and bus_entity_Id = "+manuId+")"):"") +
        "and cats.catalog_id = con.catalog_id \n" +
		"and con.contract_status_cd = 'ACTIVE'  \n" +
		((itemIdList!=null)?("and cats.item_id in ("+itemIdList+") "):" ")+
		") catcon, \n" +
		"clw_contract_item coni, \n" +
		"clw_catalog_assoc cata, \n" +
		"(select bus_entity_id, erp_num, short_desc  from clw_bus_entity where bus_entity_id in(select bus_entity1_id from clw_bus_entity_assoc  \n" +
		"where bus_Entity2_id = "+storeId+" \n" +
		((acctIdList!=null)?("and bus_entity1_id in ("+acctIdList+")"):"")+ ")) acct, \n" +
		"clw_bus_entity dist, \n" +
		"clw_item item, clw_item_mapping imm, clw_bus_entity mfg, \n" +
		"(select item_id, clw_value color from clw_item_meta where name_value = 'COLOR') color, \n" +
		"(select item_id, clw_value list_price from clw_item_meta where name_value = 'LIST_PRICE') list_price, \n" +
		"(select item_id, clw_value pack from clw_item_meta where name_value = 'PACK') pack, \n" +
		"(select item_id, clw_value sizei from clw_item_meta where name_value = 'SIZE') sizei, \n" +
		"(select item_id, clw_value uom from clw_item_meta where name_value = 'UOM') uom, \n" +
		"clw_item_mapping imd,clw_item_mapping_assoc ima, clw_item_mapping imgm, clw_bus_entity mfg1, \n" +
		"clw_item cat, clw_item_assoc ia \n" +
		"where catcon.item_id = coni.item_id \n" +
		"and catcon.contract_id = coni.contract_id \n" +
		"and cata.catalog_id = catcon.catalog_id \n" +
		"and cata.catalog_assoc_cd = 'CATALOG_ACCOUNT' \n" +
		"and cata.bus_entity_id = acct.bus_entity_id \n" +
		"and catcon.bus_entity_id = dist.bus_entity_id(+) \n" +
		"and catcon.item_id = item.item_id \n" +
		"and imm.item_mapping_cd = 'ITEM_MANUFACTURER' \n" +
		"and imm.bus_entity_id = mfg.bus_entity_id \n" +
		"and imm.item_id = item.item_id \n" +
		"and color.item_id(+) = item.item_id \n" +
		"and list_price.item_id(+) = item.item_id \n" +
		"and pack.item_id(+) = item.item_id \n" +
		"and sizei.item_id(+) = item.item_id \n" +
		"and uom.item_id(+) = item.item_id \n" +
		"and imd.item_id(+) = catcon.item_id \n" +
		"and imd.bus_entity_id(+) = catcon.bus_entity_id \n" +
		"and imd.item_mapping_cd(+) = 'ITEM_DISTRIBUTOR' \n" +
		"and imd.item_mapping_id = ima.item_mapping1_id(+) \n" +
		"and ima.item_mapping_assoc_cd (+) = 'DIST_GENERIC_MFG' \n" +
		"and ima.item_mapping2_id = imgm.item_mapping_id(+) \n" +
		"and imgm.item_mapping_cd(+) = 'ITEM_GENERIC_MFG' \n" +
		"and mfg1.bus_entity_id(+) = imgm.bus_entity_id \n" +
		"and ia.catalog_id = catcon.catalog_id \n" +
		"and ia.item2_id = cat.item_id \n" +
		"and ia.item1_id = catcon.item_id \n" +
		"and ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY'\n";
		//"order by coni.item_id, catcon.bus_entity_id ";

         Map distinctItemMap = new HashMap();

         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         int   ind = 0;
         LinkedList rowDataLL = new LinkedList();
         while (rs.next() ) {
           ReportRow rr = new ReportRow();
           rr.accountId = rs.getInt("account_id");
           rr.catalogId = rs.getInt("catalog_id");
           rr.distId = rs.getInt("dist_id");
           rr.itemId = rs.getInt("item_id");
           String key = rr.accountId + ":" + rr.catalogId + ":" + rr.distId + ":" + rr.itemId;
           if (distinctItemMap.get(key) != null) // elimilate duplicated row due to duplicated item_mapping or category mapping
        	   continue;
           
           distinctItemMap.put(key, rr);
           rr.accountName = rs.getString("acct_name");           
           rr.accountErpNum = rs.getString("acct_erp");
           rr.accountErpNum = (rr.accountErpNum==null)?"":rr.accountErpNum.trim();
           rr.contractId = rs.getInt("contract_id");
           rr.contractName = rs.getString("contract_name");
           rr.categoryName = rs.getString("cat");
           rr.itemSku = rs.getInt("sku_num");
           rr.itemDesc = rs.getString("item_desc");           
           rr.itemMfgName = rs.getString("mfg_name");
           rr.itemMfgSku = rs.getString("mfg_sku");
           rr.itemUom = rs.getString("uom");
           rr.itemPack = rs.getString("pack");
           rr.itemSize = rs.getString("sizei");
           rr.itemColor = rs.getString("color");
           rr.listPrice = new BigDecimal(rs.getString("list_price"));
           rr.distName = rs.getString("dist_name");
           rr.distErp = rs.getString("dist_erp");
           rr.distErp = (rr.distErp==null)?"":rr.distErp.trim();
           rr.distSku = rs.getString("dist_sku");
           rr.distUom = rs.getString("dist_uom");
           rr.distPack = rs.getString("dist_pack");
           rr.distCost = rs.getBigDecimal("dist_cost");           
           rr.itemMfg1Name = rs.getString("mfg1_name");
           rr.itemMfg1Sku = rs.getString("mfg1_sku");           
           rr.price = rs.getBigDecimal("amount");
           rr.baseCost = rs.getBigDecimal("dist_base_cost");           
           rr.contrDistFl = "Y";
         }
         
         rowDataLL = new LinkedList(distinctItemMap.values());
         rs.close();
         stmt.close();



         //sort to match Lawson
         Object[] rowDataA = rowDataLL.toArray();

         for(int ii=0; ii<rowDataA.length-1; ii++) {
           boolean exitFl = true;
           for(int jj=0; jj<rowDataA.length-ii-1;jj++) {
             ReportRow rr1 = (ReportRow) rowDataA[jj];
             ReportRow rr2 = (ReportRow) rowDataA[jj+1];
             int catalogId1 = rr1.catalogId;
             int catalogId2 = rr2.catalogId;
             if(catalogId1>catalogId2) {
               exitFl = false;
               rowDataA[jj] = rr2;
               rowDataA[jj+1] = rr1;
               continue;
             } else if(catalogId1==catalogId2) {
               int accountId1 = rr1.accountId;
               int accountId2 = rr2.accountId;
               if(accountId1>accountId2) {
                 exitFl = false;
                 rowDataA[jj] = rr2;
                 rowDataA[jj+1] = rr1;
                 continue;
               } else if(accountId1==accountId2) {
                 String distErpNum1 = rr1.distErp;
                 String distErpNum2 = rr2.distErp;
                 int comp = distErpNum1.compareTo(distErpNum2);
                 if(comp>0) {
                   exitFl = false;
                   rowDataA[jj] = rr2;
                   rowDataA[jj+1] = rr1;
                   continue;
                 } else if(comp==0) {
                   int itemSku1 = rr1.itemSku;
                   int itemSku2 = rr2.itemSku;
                   if(itemSku1>itemSku2){
                     exitFl = false;
                     rowDataA[jj] = rr2;
                     rowDataA[jj+1] = rr1;
                     continue;
                   }
                 }
               }
             }
           }
           if(exitFl) {
             break;
           }
         }

        //Get one year back date
        Calendar dateCal = Calendar.getInstance();
        Date curDate = dateCal.getTime();
        dateCal.add(Calendar.MONTH, -12);
        Date dateBeg = dateCal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dateBegS = df.format(dateBeg);
        
        sql="SELECT   SUM (oi.total_quantity_ordered) quantity, \n" +
		        "		 SUM (oi.total_quantity_ordered * oi.dist_item_cost) unit_cost, \n" +
		        "		 MAX (o.original_order_date) last_order_date  \n" +
		        "FROM clw_order_item oi, \n" +
		        "	 clw_order o, \n" +
		        "	 clw_contract con, \n" +
		        "	 clw_catalog_assoc ca, \n" +
		        "	 clw_catalog_structure cs, \n" +
		        "	 clw_bus_entity ba \n" +
		        "WHERE oi.order_id = o.order_id \n" +
		        "AND oi.order_item_status_cd !='" + RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED + "' \n" +
	            "AND o.original_order_date between TO_DATE('"+begDateS+"','mm/dd/yyyy') AND TO_DATE('"+endDateS+"','mm/dd/yyyy') \n" +
	            "AND o.order_status_cd IN " + OrderDAO.kGoodOrderStatusSqlList + " \n" +
		        "AND oi.item_id = ? \n" +
		        "AND o.account_id = ? \n" +
		        "AND cs.catalog_id = ca.catalog_id \n" +
		        "AND cs.item_id = oi.item_id \n" +
		        "AND con.contract_id = o.contract_id \n" +
		        "AND o.account_id = ca.bus_entity_id \n" +
		        "AND ca.catalog_id = con.catalog_id \n" +
		        "AND ca.catalog_assoc_cd  = 'CATALOG_ACCOUNT' \n" +
		        "AND ba.bus_entity_id = o.account_id \n" +
		        "and con.contract_id = ? \n" +
		        "and ca.catalog_id = ? \n" +
		        "GROUP BY ba.bus_entity_id, \n" +
		        "	  ca.catalog_id, \n" +
		        "	  o.contract_id, \n" +
		        "	  oi.item_id \n";
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        //Match now
        for (int ii = 0; ii < rowDataA.length; ii++) {
            ReportRow rr = (ReportRow) rowDataA[ii];
            pstmt.setInt(1, rr.itemId);
            pstmt.setInt(2, rr.accountId);
            pstmt.setInt(3, rr.contractId);            
            pstmt.setInt(4, rr.catalogId);        
            
            rs = pstmt.executeQuery();
            if(rs.next()){
            	rr.ytdQty = rs.getInt("quantity");            	
                rr.lastOrderDate = rs.getDate("last_order_date");                
            }
        }
        rs.close();
        pstmt.close();
        

        result.setTable(new ArrayList());
        for(int ii=0; ii<rowDataA.length; ii++){
           ReportRow rr = (ReportRow) rowDataA[ii];
           ArrayList row = new ArrayList();
           row.add(new Integer(rr.accountId));
           row.add(rr.accountName);
           row.add(rr.accountErpNum);
           row.add(new Integer(rr.catalogId));
           row.add(new Integer(rr.contractId));
           row.add(rr.contractName);
           row.add(new Integer(rr.itemSku));
           row.add(rr.itemDesc);
           row.add(rr.categoryName);
           row.add(rr.itemMfgName);
           row.add(rr.itemMfgSku);
           row.add(rr.itemUom);
           row.add(rr.itemPack);
           row.add(rr.itemSize);
           row.add(rr.itemColor);
           row.add(rr.listPrice);
           row.add(rr.distName);
           //row.add(rr.contrDistFl);
           row.add(rr.distErp);
           row.add(rr.distSku);
           row.add(rr.distUom);
           row.add(rr.distPack);
           row.add(rr.itemMfg1Name);
           row.add(rr.itemMfg1Sku);
           row.add(rr.price);
           row.add(rr.distCost);
           row.add(rr.baseCost);
           row.add(rr.lastOrderDate);
           row.add((rr.ytdQty!=0)?(new Integer(rr.ytdQty)):null);
           row.add(new Integer(rr.itemId));
           result.getTable().add(row);
        }
        result.setHeader(getReportHeader());
        result.setColumnCount(result.getHeader().size());
        return result;
    }


    //We can't just use the meta data here because of all the various sub-queries that
    //take place.
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Account Id",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Erp#",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Catalog Id",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Contract Id",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Contract Name",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","CW SKU",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Desc",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Mfg",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Mfg Sku",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Uom",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Size",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Color",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","List Price",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Name",0,255,"VARCHAR2"));
       //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Contract Fl",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Erp#",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Sku",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Uom",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Pack",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Generic Mfg",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Generic Sku",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Contr Price",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Dist Cost",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Base Cost",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Last Order Date",0,0,"DATE"));
       //header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","12 Months Qty",0,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Report Qty",0,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Item Id",0,32,"NUMBER"));
       return header;
    }

    public class ReportRow {
      int contractId;
      int catalogId;
      String contractName;
      String contractStatus;
      int itemId;
      int itemSku;
      String itemDesc;
      String categoryName;
      int itemMfgId;
      String itemMfgName;
      String itemMfgSku;
      String itemUom;
      String itemPack;
      String itemSize;
      String itemColor;
      BigDecimal listPrice;
      int distId;
      String distName;
      String contrDistFl;
      String distErp;
      String distSku;
      String distUom;
      String distPack;
      String itemMfg1Name;
      String itemMfg1Sku;
      BigDecimal distCost;
      BigDecimal price;
      BigDecimal baseCost;
      String priceDiff;
      String priceDiffPr;
      Date lastOrderDate;
      int ytdQty;
      BigDecimal avgCost;
      int accountId;
      String accountName;
      String accountErpNum;

      public String toString() {
        String  str =
  "contractId=<"+contractId+">"+
  "catalogId=<"+catalogId+">"+
  "contractName=<"+contractName+">"+
  "contractStatus=<"+contractStatus+">"+
  "itemId=<"+itemId+">"+
  "itemSku=<"+itemSku+">"+
  "itemDesc=<"+itemDesc+">"+
  "categoryName=<"+categoryName+">"+
  "itemMfgId=<"+itemMfgId+">"+
  "itemMfgName=<"+itemMfgName+">"+
  "itemMfgSku=<"+itemMfgSku+">"+
  "itemUom=<"+itemUom+">"+
  "itemPack=<"+itemPack+">"+
  "itemSize=<"+itemSize+">"+
  "itemColor=<"+itemColor+">"+
  "listPrice=<"+listPrice+">"+
  "distId=<"+distId+">"+
  "distName=<"+distName+">"+
  "contrDistFl=<"+contrDistFl+">"+
  "distErp=<"+distErp+">"+
  "distSku=<"+distSku+">"+
  "distUom=<"+distUom+">"+
  "distPack=<"+distPack+">"+
  "itemMfg1Name=<"+itemMfg1Name+">"+
  "itemMfg1Sku=<"+itemMfg1Sku+">"+
  "distCost=<"+distCost+">"+
  "price=<"+price+">"+
  "baseCost=<"+baseCost+">"+
  "priceDiff=<"+priceDiff+">"+
  "priceDiffPr=<"+priceDiffPr+">"+
  "lastOrderDate=<"+lastOrderDate+">"+
  "ytdQty=<"+ytdQty+">"+
  "avgCost=<"+avgCost+">"+
  "accountId=<"+accountId+">"+
  "accountName=<"+accountName+">"+
  "accountErpNum=<"+accountErpNum+">";
        return str;
      }
    }

    public class OrderHistory {

      int itemSku;
      String distErp;
      String accountErp;
      int accountId;
      int qty;
      Date lastOrderDate;
      //String siteErp;
      int catalogId;
      //public int siteId;
      public int itemId;
      public double unitCost;
      public int contractId;

      public String getAccountErpKeyMap()
      {
        return  accountErp+"@"+
                catalogId+"@"+contractId+"@"+itemSku;
      }
    }


}
