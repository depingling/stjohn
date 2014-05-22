/*
 * CatalogProfitReport.java
 *
 * Created on January 31, 2003, 6:26 PM
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.util.Map;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import javax.naming.NamingException;
/**
 *Picks up all catalog items and adds year to date trade infrmation
 *Adapted from the ReportOrderBean to the new GenericReport Framework
 */
public class CatalogProfitReport implements GenericReport{
    

    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception{
        Connection pCon = pCons.getDefaultConnection();
        String errorMess = "No error";
        //CatalogItemProfitViewVector items = new CatalogItemProfitViewVector();
        //ArrayList items = new ArrayList();
        GenericReportResultView results = GenericReportResultView.createValue();
        //create result vector
        ArrayList items1 = new ArrayList();
        int pCatalogId;
        try{
            pCatalogId = Integer.parseInt((String)pParams.get("CATALOG"));
        }catch (RuntimeException e){
            throw new RemoteException("Could not parse Catalog control: "+pParams.get("CATALOG"));
        }
        try {
            DBCriteria dbc;
            
            //pContract
            dbc = new DBCriteria();
            //get pContract id
            dbc.addEqualTo(ContractDataAccess.CATALOG_ID,pCatalogId);
            dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            ContractDataVector pContractDV = ContractDataAccess.select(pCon,dbc);
            CatalogData catalogD = CatalogDataAccess.select(pCon,pCatalogId);
            String catalogName = catalogD.getShortDesc();
            int pContractId=0;
            if(pContractDV.size()>1) {
                String mess = "More than one pContract for catalog. Catalog Id: "+pCatalogId;
                throw new RemoteException(mess);
            }
            ContractData pContractD = null;
            String pContractName = null;
            if(pContractDV.size()==1) {
                pContractD = (ContractData) pContractDV.get(0);
                pContractId = pContractD.getContractId();
                pContractName = pContractD.getShortDesc();
            }
            
            //catalog items
            dbc = new DBCriteria();
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            String catalogItemReq = CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID,dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID,catalogItemReq);
            dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
            ItemDataVector itemDV = ItemDataAccess.select(pCon,dbc);
            
            //pContract items
            dbc = new DBCriteria();
            dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID,pContractId);
            String pContractItemReq = ContractItemDataAccess.getSqlSelectIdOnly(ContractItemDataAccess.ITEM_ID,dbc);
            dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
            ContractItemDataVector pContractItemDV = ContractItemDataAccess.select(pCon,dbc);
            
            
            
            //items
            for(int ii=0,jj=0; ii<itemDV.size(); ii++) {
                ItemData iD = (ItemData) itemDV.get(ii);
                CatalogItemProfitView itemProf = CatalogItemProfitView.createValue();
                itemProf.setCatalogId(pCatalogId);
                itemProf.setCatalogName(catalogName);
                itemProf.setContractId(pContractId);
                itemProf.setContractName(pContractName);
                itemProf.setItemSku(iD.getSkuNum());
                itemProf.setItemDesc(iD.getShortDesc());
                int itemId = iD.getItemId();
                itemProf.setItemId(itemId);
                boolean pContractFl = false;
                if(jj<pContractItemDV.size()) {
                    ContractItemData ciD = (ContractItemData) pContractItemDV.get(jj);
                    int itemId1 = ciD.getItemId();
                    if(itemId1<itemId) {
                        String mess = "^clw^pContract has item, which doesn't exist in catalog. Item id: "
                        +itemId1+" pContract Id: "+pContractId+" Catalog Id: "+pCatalogId+"^clw^";
                        throw new RemoteException(mess);
                    }
                    if(itemId1==itemId) {
                        jj++;
                        pContractFl = true;
                        BigDecimal cost = ciD.getDistCost();
                        itemProf.setDistCost(cost);
                        BigDecimal price = ciD.getAmount();
                        itemProf.setContractPrice(price);
                        if(price!=null) {
                            BigDecimal diff = price.subtract(ciD.getDistCost());
                            itemProf.setPriceDiff(diff);
                            if(diff!=null && price.compareTo(new BigDecimal(0.001))>0) {
                                itemProf.setPriceDiffPr(diff.divide(price,4,BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                }
                itemProf.setContrDistFl(pContractFl?"Yes":"No");
                items1.add(itemProf);
            }
            
            
            //mfg
            String sql =
            "select "+
            " item_id, be.bus_entity_id mfg_id, be.short_desc mfg_name,"+
            " item_num mfg_sku"+
            " from clw_item_mapping map, clw_bus_entity be"+
            " where map.item_mapping_cd = 'ITEM_MANUFACTURER'"+
            " and map.bus_entity_id = be.bus_entity_id"+
            " and item_id in ("+catalogItemReq+") "+
            "  order by item_id";
            
            
            Statement stmt = pCon.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int ind = 0;
            while (rs.next() ) {
                int itemId = rs.getInt("item_id");
                int mfgId = rs.getInt("mfg_id");
                String mfgName = rs.getString("mfg_name");
                String mfgSku = rs.getString("mfg_sku");
                for(;ind<items1.size();ind++) {
                    CatalogItemProfitView itemProf = (CatalogItemProfitView) items1.get(ind);
                    int itemIdBase = itemProf.getItemId();
                    if(itemId<itemIdBase) {
                        break;
                    }
                    if(itemId>itemIdBase) {
                        continue;
                    }
                    itemProf.setItemMfgId(mfgId);
                    itemProf.setItemMfgName(mfgName);
                    itemProf.setItemMfgSku(mfgSku);
                    ind++;
                    break;
                }
            }
            
            //Meta data
            sql =
            "select item_id,  name_value, clw_value "+
            " from clw_item_meta "+
            " where name_value in ('COLOR','LIST_PRICE','PACK','SIZE','UOM','OTHER_DESC') "+
            " and item_id in ("+catalogItemReq+") "+
            " order by item_id";
            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);
            ind = 0;
            while (rs.next() ) {
                int itemId = rs.getInt("item_id");
                String name = rs.getString("name_value");
                String value = rs.getString("clw_value");
                for(;ind<items1.size();ind++) {
                    CatalogItemProfitView itemProf = (CatalogItemProfitView) items1.get(ind);
                    int itemIdBase = itemProf.getItemId();
                    if(itemId<itemIdBase) {
                        break;
                    }
                    if(itemId>itemIdBase) {
                        continue;
                    }
                    if("UOM".equals(name)) {
                        itemProf.setItemUom(value);
                    }else if ("PACK".equals(name)) {
                        itemProf.setItemPack(value);
                    }else if ("SIZE".equals(name)) {
                        itemProf.setItemSize(value);
                    }else if ("COLOR".equals(name)) {
                        itemProf.setItemColor(value);
                    }else if ("OTHER_DESC".equals(name)) {
                        itemProf.setOtherDesc(value);
                    }else if ("LIST_PRICE".equals(name)) {
                        BigDecimal listPrice = null;
                        try {
                            listPrice = new BigDecimal(value);
                        } catch(Exception exc) {
                            listPrice = new BigDecimal(-99999999);
                        }
                        itemProf.setListPrice(listPrice);
                    }
                    break;
                }
            }
            sql =
            "select cs.item_id, be.bus_entity_id dist_id, "+
            " be.short_desc dist_name, be.erp_num dist_erp, "+
            " map.standard_product_list as spl, item_num dist_sku, item_uom dist_uom, item_pack dist_pack "+
            " from clw_catalog_structure cs, clw_bus_entity be, clw_item_mapping map "+
            "  where cs.catalog_id =  "+pCatalogId+
            "    and cs.bus_entity_id = be.bus_entity_id "+
            "    and cs.item_id = map.item_id(+) "+
            "    and cs.catalog_structure_cd = 'CATALOG_PRODUCT' "+
            "    and map.item_mapping_cd(+) = 'ITEM_DISTRIBUTOR' "+
            "    and map.bus_entity_id(+) =cs.bus_entity_id "+
            "    and cs.item_id in ("+catalogItemReq+") "+
            "   order by cs.item_id";
            
            
            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);
            ind = 0;
            while (rs!=null && rs.next() ) {
                int itemId = rs.getInt("item_id");
                int distId = rs.getInt("dist_id");
                String spl = rs.getString("spl");
                String distName = rs.getString("dist_name");
                String distErp = rs.getString("dist_erp");
                String distSku = rs.getString("dist_sku");
                String distUom = rs.getString("dist_uom");
                String distPack = rs.getString("dist_pack");
                for(;ind<items1.size();ind++) {
                    CatalogItemProfitView itemProf = (CatalogItemProfitView) items1.get(ind);
                    int itemIdBase = itemProf.getItemId();
                    if(itemId<itemIdBase) {
                        break;
                    }
                    if(itemId>itemIdBase) {
                        continue;
                    }
                    itemProf.setDistId(distId);
                    itemProf.setDistName(distName);
                    itemProf.setDistErp(distErp);
                    itemProf.setDistSku(distSku);
                    itemProf.setDistUom(distUom);
                    itemProf.setDistPack(distPack);
                    itemProf.setStandardProductList(Boolean.toString(Utility.isTrue(spl)));
                    break;
                }
            }
            //Get catalog account
            sql =
            "select be.erp_num, be.bus_entity_id "+
            " from clw_catalog_assoc ca, clw_bus_entity be "+
            " where ca.bus_entity_id = be.bus_entity_id "+
            "   and ca.catalog_assoc_cd = 'CATALOG_ACCOUNT' "+
            "   and ca.catalog_id = " + pCatalogId;
            
            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);
            ind = 0;
            LinkedList customerErpV = new LinkedList();
            LinkedList customerIdV = new LinkedList();
            while (rs.next() ) {
                String customerErp = rs.getString("erp_num");
                int customerId = rs.getInt("bus_entity_id");
                customerErpV.add(customerErp);
                customerIdV.add(new Integer(customerId));
            }
            //Fist of January date
            Calendar dateCal = Calendar.getInstance();
            Date curDate = dateCal.getTime();
            dateCal.add(Calendar.MONTH, -12);
            Date dateBeg = dateCal.getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM,yyyy");
            String dateBegS = df.format(dateBeg);
            for(int ii=0; ii<customerErpV.size(); ii++) {
                String customerErp = (String) customerErpV.get(ii);
                Integer customerIdI = (Integer) customerIdV.get(ii);
                sql =
                "select item, vendor, sum(quantity) quantity, "+
                " sum(ent_unit_cst*quantity) unit_cost, "+
                " max(co.order_date) last_order_date "+
                " from law_poline pol, law_custorder co,clw_catalog_assoc ca, "+
                " clw_bus_entity be, clw_bus_entity_assoc bea "+
                " where trim(translate(pol.item,'0987654321','          ')) is null "+
                " and pol.order_nbr = co.order_nbr "+
                " and co.customer = '"+customerErp+"' "+
                " and trim(translate(be.erp_num,'0987654321','          ')) is null "+
                " and ship_to = be.erp_num "+
                " and ca.bus_entity_id = be.bus_entity_id "+
                " and ca.catalog_assoc_cd = 'CATALOG_SITE' "+
                " and ca.catalog_id = "+pCatalogId+
                " and co.order_date >= '"+dateBegS+"' "+
                " and pol.quantity !=0 "+
                " and bea.bus_entity2_id = "+customerIdI+ " "+
                " and bea.bus_entity1_id = be.bus_entity_id "+
                " and bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' "+
                " group by item, vendor "+
                " order by to_number(pol.item),vendor";
                                
                stmt = pCon.createStatement();
                rs = stmt.executeQuery(sql);
                
                ind = 0;
                while (rs!=null && rs.next() ) {
                    String itemString = rs.getString("item");
                    int itemSku = 0;
                    try {
                        if ( null != itemString ) {
                            itemSku = Integer.parseInt(itemString.trim());
                        }
                    }
                    catch (Exception e) {
                    }
                    String distErp = rs.getString("vendor");
                    distErp = (distErp==null)?"":distErp.trim();
                    Date lastOrderDate = rs.getDate("last_order_date");
                    int qty = rs.getInt("quantity");
                    BigDecimal sumCost = rs.getBigDecimal("unit_cost");
                    for(ind=0 ;ind<items1.size();ind++) {
                        CatalogItemProfitView itemProf = (CatalogItemProfitView) items1.get(ind);
                        int itemSkuBase = itemProf.getItemSku();
                        if(itemSku!=itemSkuBase) {
                            continue;
                        }
                        String distErpBase = itemProf.getDistErp();
                        if(distErpBase!=null) distErpBase = distErpBase.trim();
                        Date lastOrderDate1 = itemProf.getLastOrderDate();
                        if(lastOrderDate1 == null || lastOrderDate1.before(lastOrderDate)) {
                            itemProf.setLastOrderDate(lastOrderDate);
                        }
                        int qtySum = qty+itemProf.getYtdQty();
                        itemProf.setYtdQty(qtySum);
                        if(distErp.equals(distErpBase) && qty>0 && sumCost!=null) {
                            int ytdqM = itemProf.getYtdQtyMainDist();
                            ytdqM += qty;
                            itemProf.setYtdQtyMainDist(ytdqM);
                            BigDecimal ytdcM = itemProf.getYtdCostMainDist();
                            if(ytdcM==null) {
                                ytdcM = sumCost;
                            } else {
                                ytdcM = ytdcM.add(sumCost);
                            }
                            itemProf.setYtdCostMainDist(ytdcM);
                            BigDecimal avgCost = ytdcM.divide(new BigDecimal(ytdqM),2,BigDecimal.ROUND_HALF_UP);
                            itemProf.setAvgCost(avgCost);
                        }
                        break;
                    }
                }
            }
            
            final String catalogNameSql1 = "SELECT distinct cat.short_desc FROM clw_item_assoc assoc, clw_item cat WHERE assoc.catalog_id = ";
            final String catalogNameSql2 = " AND assoc.item1_id = ";
            final String catalogNameSql3 = " AND assoc.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY' AND cat.item_id = assoc.item2_id";
            java.util.Iterator it = items1.iterator();
            while(it.hasNext()){    
                CatalogItemProfitView itm = (CatalogItemProfitView) it.next();
                StringBuffer catalogNameSql = new StringBuffer(catalogNameSql1);
                catalogNameSql.append(Integer.toString(itm.getCatalogId()));
                catalogNameSql.append(catalogNameSql2);
                catalogNameSql.append(Integer.toString(itm.getItemId()));
                catalogNameSql.append(catalogNameSql3);
                Statement st = pCon.createStatement();
                ResultSet rs2 = st.executeQuery(catalogNameSql.toString());
                int ct = 0;
                if(rs2.next()){
                    itm.setCategoryName(rs2.getString(1));
                }
                if(rs2.next()){
                    itm.setCategoryName("*"+itm.getCategoryName());
                }
                st.close();
            }
            
        } catch (DataNotFoundException exc) {
            exc.printStackTrace();
            errorMess = "No data found";
            throw new RemoteException(errorMess);
        }
        catch (SQLException exc) {
            exc.printStackTrace();
            errorMess = "Error. Report.getCatalogProfit() SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            errorMess = "Error. Report.getCatalogProfit() Api Service Access Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        //convert the items vector into a list in the proper order according to the columns
        ArrayList items = new ArrayList();
        for(int i=0;i<items1.size();i++){
            CatalogItemProfitView currRow = (CatalogItemProfitView) items1.get(i);
            ArrayList row = new ArrayList();
            row.add(new Integer(currRow.getCatalogId()));
            row.add(currRow.getCatalogName());
            row.add(new Integer(currRow.getContractId()));
            row.add(currRow.getContractName());
            row.add(new Integer(currRow.getItemSku()));
            row.add(currRow.getItemDesc());
            row.add(currRow.getOtherDesc());
            row.add(currRow.getCategoryName());
            row.add(currRow.getItemMfgName());
            row.add(currRow.getItemMfgSku());
            row.add(currRow.getItemUom());
            row.add(currRow.getItemPack());
            row.add(currRow.getItemSize());
            row.add(currRow.getItemColor());
            row.add(currRow.getListPrice());
            row.add(currRow.getDistName());
            row.add(currRow.getContrDistFl());
            row.add(currRow.getDistErp());
            row.add(currRow.getDistSku());
            row.add(currRow.getDistUom());
            row.add(currRow.getDistPack());
            row.add(currRow.getStandardProductList());
            row.add(currRow.getDistCost());
            row.add(currRow.getContractPrice());
            row.add(currRow.getPriceDiff());
            row.add(currRow.getPriceDiffPr());
            row.add(currRow.getLastOrderDate());
            row.add(new Integer(currRow.getYtdQty()));
            row.add(currRow.getAvgCost());
            
            items.add(row);
        }
        results.setTable(items);
        results.setHeader(getReportHeader());
        results.setColumnCount(results.getHeader().size());
        return results;
    }
    
    //We can't just use the meta data here because of all the various sub-queries that
    //take place.
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Catalog Id",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Catalog Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Contract Id",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Contract Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","CW SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Desc",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Other Desc",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Mfg",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Mfg Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Uom",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Size",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Color",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","List Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Contract Fl",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Erp#",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Uom",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Std Product List",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Dist Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Contr Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","$ Difference",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","% Difference",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Last Order Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","12 Months Qty",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Avg Cost",2,20,"NUMBER"));
        return header;
    }
    
    
    
}
