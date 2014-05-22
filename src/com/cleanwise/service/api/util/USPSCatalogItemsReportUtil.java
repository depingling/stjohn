package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.TradingPartnerAssocDataAccess;
import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.value.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class USPSCatalogItemsReportUtil {
    private static int ACCOUNT_CATALOG_ID = 57445; //57527;
    private static int STORE_ID = 1;
    private static int ACCOUNT_ID = 100;
	private static int STORE_CATALOG_ID = 4;
	//private static int PRICE_CONTRACT_ID = 1520;
	private static String TRADING_PARTNER = "157793733";
    private static String SUPPLIER_URL = "http://www.cleanwise.com/cleanwise/store/ex_product_info.do?";
    BusEntityFieldsData fieldsData;



public ArrayList createResult(ConnectionContainer pCons, Map pParams, boolean pSpecialFl) throws Exception {
    
    Connection con = pCons.getMainConnection();
    ArrayList items = new ArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    int storeId = STORE_ID;
    int catalogId;
    try{
        catalogId = Integer.parseInt((String)pParams.get("CATALOG"));
    }catch (RuntimeException e){
        throw new RemoteException("Could not parse Catalog control: "+pParams.get("CATALOG"));
    }

    int tradingPartnerId = 0;
    try {
        DBCriteria dbc;
        // store
		/*
        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, accountId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        IdVector storeIdV = BusEntityAssocDataAccess.selectIdOnly(con, BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
        if (storeIdV.size() == 0) {
            throw new DataNotFoundException("No store found for account. Account Id: " + accountId);
        }
        if (storeIdV.size() > 1) {
            throw new Exception("More than one store found for account. Accont Id: " + accountId);
        }
        storeId = ((Integer) storeIdV.get(0)).intValue();
        */

        // data fields
        APIAccess factory = new APIAccess();
        PropertyService psvcBean = factory.getPropertyServiceAPI();
        fieldsData = psvcBean.fetchMasterItemFieldsData(storeId);
        boolean needGettingDataFields = fieldsData != null && PropertyFieldUtil.isShowInAdmin(fieldsData);

        // trading partner id
		/*
        dbc = new DBCriteria();
        dbc.addEqualTo(TradingPartnerAssocDataAccess.BUS_ENTITY_ID, accountId);
        IdVector trPartnerV = TradingPartnerAssocDataAccess.selectIdOnly(con, dbc);
        if (trPartnerV.size() == 1) {
            tradingPartnerId = ((Integer)trPartnerV.get(0)).intValue();
        }
        */
        //pContract
        dbc = new DBCriteria();
        //get pContract id
        dbc.addEqualTo(ContractDataAccess.CATALOG_ID,catalogId);
        ArrayList contractStatuses = new ArrayList();
        contractStatuses.add(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
        contractStatuses.add(RefCodeNames.CONTRACT_STATUS_CD.LIVE);

        dbc.addOneOf(ContractDataAccess.CONTRACT_STATUS_CD,contractStatuses);
        ContractDataVector pContractDV = ContractDataAccess.select(con,dbc);

        int contractId=0;
        if(pContractDV.size()>1) {
            String mess = "More than one pContract for catalog. Catalog Id: "+catalogId;
            throw new RemoteException(mess);
        }
        ContractData pContractD = null;
        String pContractName = null;
        if(pContractDV.size()==1) {
            pContractD = (ContractData) pContractDV.get(0);
            contractId = pContractD.getContractId();
            pContractName = pContractD.getShortDesc();
        }

        //catalog items
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,catalogId);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        String catalogItemReq = CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID,dbc);

        String sql =    "SELECT ci.item_id, amount as item_price, cats.customer_sku_num as sku_num, ci.contract_id, \n\r"+
						" cat.loader_field eff_date, null exp_date, i.short_desc, \n\r" +
                        " imap.bus_entity_id manuf_id, imap.item_num manuf_sku, be.short_desc manuf_name, \n\r" +
						"  Nvl(service_fee_code,'C') change_cd \n\r" +
                        " FROM clw_contract_item ci, clw_contract c, clw_item i, \n\r" +
                        "    clw_catalog cat, clw_item_mapping imap, clw_bus_entity be, \n\r" +
                        "    clw_catalog_structure cats \n\r" +
                        " WHERE ci.contract_id = c.contract_id \n\r" +
                        " AND i.item_type_cd ='" + RefCodeNames.ITEM_TYPE_CD.PRODUCT + "' \n\r" +
                        " AND i.item_id in ( \n\r" + 
						   catalogItemReq + ") \n\r" +
                        " AND i.item_id = ci.item_id \n\r" +
                        " AND c.catalog_id = cat.catalog_id \n\r"+
						" AND cats.catalog_id = "+ STORE_CATALOG_ID +"\n\r"+
						" AND cats.item_id = i.item_id \n\r" +
                        " AND i.item_id = imap.item_id \n\r" +
                        " AND imap.item_mapping_cd = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "' \n\r" +
                        " AND imap.bus_entity_id = be.bus_entity_id \n\r" +
						" AND cat.catalog_id = "+catalogId +"\n\r"+
                        " order by ci.item_id \n\r";

						
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){

            ItemView item = new ItemView();

            item.itemId = rs.getInt("item_id");

            item.tradingParnterId = TRADING_PARTNER;
            item.transactionSetId = "832";
            item.recordId = "01";
            item.itemSku = rs.getInt("sku_num");
            item.itemDesc = "";
            item.manufSku = rs.getString("manuf_sku");
            item.manufName = rs.getString("manuf_name");
            item.itemPrice = rs.getBigDecimal("item_price");
            item.listPrice = new BigDecimal(0);
            item.businessClass = "L";
            item.womenOwned = "N";
            item.minorityBusiness = "N";
            item.jwodBusiness = getJWODBusiness(rs.getString("manuf_name"));
            item.otherBusiness = "N";
            item.contractId = "2CMROS-11-B-1033";
            item.contractMandatory = "N";
            item.supplierUrl = getSupplierUrl(rs.getInt("item_id"), ACCOUNT_ID);
            item.itemStatus = "A";
            item.changeCd = rs.getString("change_cd");
			String effDateS = rs.getString("eff_date");
            if (effDateS != null) {
				try {
					item.effDate = sdf1.format(sdf.parse(effDateS));
				} catch(Exception exc) {
					String errorMess = "^clw^Effective day invalid value: "+effDateS+". Please set it as catalog property Special Catalog Code using format: mm/dd/yyyy^clw^";
					throw new Exception(errorMess);
				}
            } else {
				String errorMess = "^clw^Effective day is not set. Please set it as catalog property Special Catalog Code^clw^";
				throw new Exception(errorMess);
			}
            item.expDate = "";
			/*
            if (needGettingDataFields) {
                ArrayList dataFList = new ArrayList();
                for (int i=1; i<=15; i++) {
                    if (PropertyFieldUtil.getShowInAdmin(fieldsData, i)) {
                        dataFList.add("");
                    }
                }
                item.dataFields = dataFList;
            }
			*/

            items.add(item);
        }
       rs.close();
       pstmt.close();

        // get meta info
        sql = "select uom.item_id, uom.uom, color.color,item_size.item_size,pack.pack,"+
		      "unspsc.unspsc,list_price.list_price,psn.psn,image.image, other_desc.other_desc," +
		      "MIN_LINE_QTY.MIN_LINE_QTY," +
		      "MAX_LINE_QTY.MAX_LINE_QTY," +
		      "ISSUE_INCREMENT.ISSUE_INCREMENT," +
		      "GREEN_PROD.GREEN_PROD," +
		      "ENERGY_STAR.ENERGY_STAR," +
		      "BIO_PREFERRED.BIO_PREFERRED," +
		      "MSDS_REQ.MSDS_REQ," +
		      "RECYCLED.RECYCLED," +
		      "EPEAT_RATING.EPEAT_RATING," +
		      "NEMA_STD.NEMA_STD," +
		      "EPA_WATERSENSE.EPA_WATERSENSE," +
		      "USEPA_PRI_CHEM_FREE.USEPA_PRI_CHEM_FREE," +
		      "RENEWABLE_ENERGY_RESOURCE.RENEWABLE_ENERGY_RESOURCE," +
		      "POPUP_FILE_NAME.POPUP_FILE_NAME," +
		      "mst.LONG_DESC "+
                " from" +
                " (SELECT item_id, clw_value as UOM" +
                " FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='UOM'" +
                " ) uom," +
                " (SELECT item_id, clw_value as color" +
                "   FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='COLOR'" +
                " ) color," +
                " (SELECT item_id, clw_value as item_size" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='SIZE'" +
                " ) item_size," +
                " (SELECT item_id, clw_value as pack" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='PACK'" +
                " ) pack," +
                " (SELECT item_id, clw_value as unspsc" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='UNSPSC_CD'" +
                " ) unspsc," +
                " (SELECT item_id, clw_value as list_price" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='LIST_PRICE'" +
                " ) list_price," +
                " (SELECT item_id, clw_value as psn" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='PSN'" +
                " ) psn," +
                " (SELECT item_id, clw_value as other_desc" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='OTHER_DESC'" +
                " ) other_desc," +
                " (SELECT item_id, SUBSTR(CLW_VALUE, INSTR(CLW_VALUE,'/',-1)+1) as image" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='IMAGE'" +
                " ) image," +

                " (SELECT item_id, clw_value as MIN_LINE_QTY" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='MIN-LINE-QTY'" +
                " ) MIN_LINE_QTY," +
		
                " (SELECT item_id, clw_value as MAX_LINE_QTY" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='MAX-LINE-QTY'" +
                " ) MAX_LINE_QTY," +
		
                " (SELECT item_id, clw_value as ISSUE_INCREMENT" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='ISSUE-INCREMENT'" +
                " ) ISSUE_INCREMENT," +
		
                " (SELECT item_id, clw_value as GREEN_PROD" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='GREEN-PROD'" +
                " ) GREEN_PROD," +
		
                " (SELECT item_id, clw_value as ENERGY_STAR" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='ENERGY-STAR'" +
                " ) ENERGY_STAR," +
		
                " (SELECT item_id, clw_value as BIO_PREFERRED" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='BIO-PREFERRED'" +
                " ) BIO_PREFERRED," +
		
                " (SELECT item_id, clw_value as MSDS_REQ" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='MSDS-REQ'" +
                " ) MSDS_REQ," +
		
                " (SELECT item_id, clw_value as RECYCLED" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='RECYCLED'" +
                " ) RECYCLED," +
		
                " (SELECT item_id, clw_value as EPEAT_RATING" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='EPEAT-RATING'" +
                " ) EPEAT_RATING," +
		
                " (SELECT item_id, clw_value as NEMA_STD" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='NEMA-STD'" +
                " ) NEMA_STD," +
		
                " (SELECT item_id, clw_value as EPA_WATERSENSE" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='EPA-WATERSENSE'" +
                " ) EPA_WATERSENSE," +
		
                " (SELECT item_id, clw_value as USEPA_PRI_CHEM_FREE" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='USEPA-PRI-CHEM-FREE'" +
                " ) USEPA_PRI_CHEM_FREE," +
		
                " (SELECT item_id, clw_value as RENEWABLE_ENERGY_RESOURCE" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='RENEWABLE-ENERGY-RESOURCE'" +
                " ) RENEWABLE_ENERGY_RESOURCE," +
		
                " (SELECT item_id, clw_value as POPUP_FILE_NAME" +
                "                          FROM CLW_ITEM_META WHERE " +
                "   item_id in (" + catalogItemReq + ")" +
                "   and NAME_VALUE='POPUP FILE NAME'" +
                " ) POPUP_FILE_NAME, " +

                " (SELECT item_id, LONG_DESC " +
                " FROM CLW_ITEM i " +
                " where item_id in (" + catalogItemReq + ")" +
                ") mst" +
                
                " where uom.item_id = color.item_id(+) and   " +
                "      uom.item_id = item_size.item_id(+) and" +
                "      uom.item_id = pack.item_id(+) and " +
                "      uom.item_id = unspsc.item_id(+) and" +
                "      uom.item_id = list_price.item_id(+) and" +
                "      uom.item_id = psn.item_id(+) and" +
                "      uom.item_id = other_desc.item_id(+) and" +
                "      uom.item_id = image.item_id(+) and" +
                "      uom.item_id = MIN_LINE_QTY.item_id(+) and" +
                
                "	   uom.item_id = mst.item_id(+) and" +
                
                "      uom.item_id = mst.ITEM_ID(+) and" +
                
                "      uom.item_id = MAX_LINE_QTY.item_id(+) and" +
		
                "      uom.item_id = ISSUE_INCREMENT.item_id(+) and" +
		
                "      uom.item_id = GREEN_PROD.item_id(+) and" +
		
                "      uom.item_id = ENERGY_STAR.item_id(+) and" +
		
                "      uom.item_id = BIO_PREFERRED.item_id(+) and" +
		
                "      uom.item_id = MSDS_REQ.item_id(+) and" +
		
                "      uom.item_id = RECYCLED.item_id(+) and" +
		
                "      uom.item_id = EPEAT_RATING.item_id(+) and" +
		
                "      uom.item_id = NEMA_STD.item_id(+) and" +
		
                "      uom.item_id = EPA_WATERSENSE.item_id(+) and" +
		
                "      uom.item_id = USEPA_PRI_CHEM_FREE.item_id(+) and" +
		
                "      uom.item_id = RENEWABLE_ENERGY_RESOURCE.item_id(+) and" +
	
                "      uom.item_id = POPUP_FILE_NAME.item_id(+)";
		

        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while (rs.next()){
            int item_id = rs.getInt("item_id");

            for (int i=0; i<items.size(); i++) {
                ItemView item = (ItemView)items.get(i);
                if (item.itemId == item_id) {
                    // set meta info
                    item.itemUom = rs.getString("uom");
                    item.listPrice = new BigDecimal(rs.getString("list_price"));
                    item.unspecCd = rs.getString("unspsc");
                    item.imageFileName = rs.getString("image");
					item.psn = rs.getString("psn");
					item.itemDesc = rs.getString("other_desc");
					item.minLineQty = rs.getString("MIN_LINE_QTY");
					item.maxLineQty = rs.getString("MAX_LINE_QTY");
					item.issueIncrement = rs.getString("ISSUE_INCREMENT");
					item.greenProd = rs.getString("GREEN_PROD");
					item.energyStar = rs.getString("ENERGY_STAR");
					item.bioPreferred = rs.getString("BIO_PREFERRED");
					item.msdsReq = rs.getString("MSDS_REQ");
					item.recycled = rs.getString("RECYCLED");
					item.epeatRating = rs.getString("EPEAT_RATING");
					item.nemaStd = rs.getString("NEMA_STD");
					item.epaWatersense = rs.getString("EPA_WATERSENSE");
					item.usepaPriChemFree = rs.getString("USEPA_PRI_CHEM_FREE"); 
					item.renewableEnergyResource = rs.getString("RENEWABLE_ENERGY_RESOURCE");
					item.popupFileName = rs.getString("POPUP_FILE_NAME");

//                    StringBuffer notes = new StringBuffer();
//                    if (Utility.isSet(rs.getString("item_size"))) {
//                        notes.append("SIZE=").append(rs.getString("item_size")).append(" ");
//                    }
//                    if (Utility.isSet(rs.getString("pack"))) {
//                        notes.append("Pack=").append(rs.getString("pack")).append(" ");
//                    }
//                    if (Utility.isSet(rs.getString("uom"))) {
//                        notes.append("UOM=").append(rs.getString("uom")).append(" ");
//                    }
//                    if (Utility.isSet(rs.getString("color"))) {
//                        notes.append("Color=").append(rs.getString("color"));
//                    }
//                    item.notes = notes.toString();
					item.notes=rs.getString("LONG_DESC");
                    break;
                }
            }
        }
        rs.close();
        pstmt.close();

        // category id, Suplier hierarchy
        sql = "select categ.item_id, i.short_desc, categ.categ_id from clw_item i, \n\r" +
                " (select ia.item1_id item_id, MIN(categ.item_id) categ_id \n\r" +
                "   FROM clw_item categ, clw_item_assoc ia \n\r" +
                "   WHERE item_assoc_cd = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' \n\r" +
                "      AND categ.item_id = ia.item2_id \n\r" +
                "      AND ia.catalog_id = "+ACCOUNT_CATALOG_ID+ "\n\r"+
                "      AND ia.item1_id in ( " + catalogItemReq  +") \n\r" +
                "   group by ia.item1_id \n\r" +
                ") categ \n\r" +
                " where categ.categ_id = i.item_id \n\r" +
                " order by categ.item_id \n\r";

        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while (rs.next()){
            int item_id = rs.getInt("item_id");

            for (int i=0; i<items.size(); i++) {
                ItemView item = (ItemView)items.get(i);
                if (item.itemId == item_id) {
					if(pSpecialFl) {
						item.supplierHierarchy = rs.getString("categ_id");
					} else {
						item.supplierHierarchy = rs.getString("short_desc");
					}
                    break;
                }
            }
        }
        rs.close();
        pstmt.close();


        // add fields data if exists
		/*
        if (needGettingDataFields) {
            StringBuffer sqlSelectBuff = new StringBuffer("select ");
            StringBuffer sqlFromBuff = new StringBuffer(" from ");
            StringBuffer sqlWhereBuff = new StringBuffer(" where ");
            int k = 1;
            String whereStr = "";
            for (int i=1; i<=15; i++) {
                if (PropertyFieldUtil.getShowInAdmin(fieldsData, i)) {
                    String fieldName = PropertyFieldUtil.getTag(fieldsData, i);
                    String fN = "f"+i;
                    if (Utility.isSet(fieldName)) {
                        if (k == 1) {
                            sqlSelectBuff.append(fN + ".item_id, ");
                            whereStr =  fN + ".item_id";
                        }
                        if (k > 2) sqlWhereBuff.append(" and ");
                        if (k > 1) {
                            sqlSelectBuff.append(", ");
                            sqlFromBuff.append(", ");
                            sqlWhereBuff.append(whereStr).append("=").append(fN+".item_id").append("(+)");
                        }
                        sqlSelectBuff.append(fN+"."+fN);
                        sqlFromBuff.append(" (SELECT item_id, clw_value as " + fN +
                                " FROM CLW_ITEM_META WHERE " +
                                "   item_id in (" + catalogItemReq + ")" +
                                "   and NAME_VALUE='" +  fieldName + "'" +
                                " ) " + fN);

                        k++;
                    }
                }
            }
            sql = sqlSelectBuff.toString() + sqlFromBuff.toString() + sqlWhereBuff.toString();

            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                int item_id = rs.getInt("item_id");
                for (int i=0; i<items.size(); i++) {
                    ItemView item = (ItemView)items.get(i);
                    if (item.itemId == item_id) {
                        ArrayList dataFValues = new ArrayList();
                        for (int j=1; j<=15; j++) {
                            if (PropertyFieldUtil.getShowInAdmin(fieldsData, j)) {
                                String fieldValue = rs.getString("f"+j);
                                if (fieldValue == null) fieldValue = "";
                                dataFValues.add(fieldValue);
                            }
                        }
                        item.dataFields = dataFValues;
                        break;
                    }
                }
            }
            rs.close();
            pstmt.close();
        }
		*/

    }
    catch (SQLException exc) {
        exc.printStackTrace();
        throw new RemoteException("Error. Report.getCatalogProfit() SQL Exception happened. "+exc.getMessage());
    }
    catch (Exception exc) {
        exc.printStackTrace();
        throw new RemoteException("Error. Report.getCatalogProfit() Api Service Access Exception happened. "+exc.getMessage());
    }


    ArrayList table = new ArrayList();

    for(Iterator iter = items.iterator(); iter.hasNext();) {
        ArrayList line = new ArrayList();
        table.add(line);
        ItemView item = (ItemView)iter.next();
        line.add(item.tradingParnterId);
        line.add(item.transactionSetId);
        line.add(item.recordId);
        line.add(item.itemSku);
        line.add(item.itemDesc);
        line.add(item.supplierItemNo);
        line.add(item.manufSku);
        line.add(item.unspecCd);
        line.add(item.supplierHierarchy);
        line.add(item.itemPrice);
        line.add(item.listPrice);
        line.add(item.itemUom);
        line.add(item.businessClass);
        line.add(item.womenOwned);
        line.add(item.minorityBusiness);
        line.add(item.jwodBusiness);
        line.add(item.otherBusiness);
        line.add(item.contractId);
        line.add(item.contractLine);
        line.add(item.contractMandatory);
        line.add(item.clinType);
        line.add(item.catalogPage);
        line.add(item.supplierUrl);
        line.add(item.imageFileName);
        line.add(item.manufName);
        line.add(item.manufUrl);
        line.add(item.itemStatus);
        line.add(item.notes);
        line.add(item.changeCd);
        line.add(item.effDate);
        line.add(item.expDate);
        line.add(item.minLineQty);
        line.add(item.maxLineQty);
        line.add(item.issueIncrement);
        line.add(item.greenProd);
        line.add(item.energyStar);
        line.add(item.bioPreferred);
        line.add(item.msdsReq);
        line.add(item.recycled);
        line.add(item.epeatRating);
        line.add(item.nemaStd);
        line.add(item.epaWatersense);
        line.add(item.usepaPriChemFree);
        line.add(item.renewableEnergyResource);
        line.add(item.psn);
        line.add(item.popupFileName);

		/*
		MIN-LINE-QTY
		MAX-LINE-QTY
		ISSUE-INCREMENT
		GREEN-PROD
		ENERGY-STAR
		BIO-PREFERRED
		MSDS-REQ
		RECYCLED
		EPEAT-RATING
		NEMA-STD
		EPA-WATERSENSE
		USEPA-PRI-CHEM-FREE
		RENEWABLE-ENERGY-RESOURCE
		line.add(item.psn);
		POPUP FILE NAME
		*/

        // additional field data
		/*
        if (item.dataFields != null) {
            Iterator i = item.dataFields.iterator();
            while (i.hasNext()) {
                line.add((String)i.next());
            }
        }
		*/


    }

    return table;
}

    private String getSupplierUrl(int pItemId, int pAccountId) {

        String newItemIdS = "" + pItemId;

        if (newItemIdS.length() < 4) {
            String nulls = "0000";
            newItemIdS = nulls.substring(0, 4-newItemIdS.length()) + newItemIdS;
        }
        String result = SUPPLIER_URL + "itemId=" + newItemIdS + "&accountId=" + pAccountId;
        return result;
    }

    private String getJWODBusiness(String pManufName) {
        if (Utility.isSet(pManufName)) {
          pManufName = pManufName.replaceAll("JWOD", "");
          if (Utility.isSet(pManufName)) {
              return "N";
          } else {
              return "Y1";
          }
        } else {
          return "U";
        }
    }


    public GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Trading Partner Id",0,10,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Transaction Set Id",0,3,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Record Id",0,2,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item No",0,30,"VARCHAR2")); //item sku
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Description",0,255,"VARCHAR2"));       // item desc
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Supplier Item No",0,30,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manuf Item No",0,30,"VARCHAR2")); // manuf sku
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UNSPSC-CD",0,10,"VARCHAR2")); // UNSPEC_CD
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Supplier Hierarchy",0,20,"VARCHAR2")); // UNSPEC_CD
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Item Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","List Price",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Uom",0,2,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Business Class",0,1,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Women Owned",0,1,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Minority Business",0,2,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","JWOD Business",0,2,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Other Business",0,2,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Contract No",0,16,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Contract Line",0,8,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Contract Mandatory",0,1,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Clin Type",0,10,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Catalog Page",0,6,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Supplier Url",0,200,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Image File Name",0,40,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manuf Name",0,30,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manuf Url",0,200,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Status",0,1,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Notes",0,1000,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Change Cd",0,1,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Effective Date",0,8,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Expire Date",0,8,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MIN-LINE-QTY",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MAX-LINE-QTY",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ISSUE-INCREMENT",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","GREEN-PROD",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","ENERGY-STAR",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","BIO-PREFERRED",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MSDS-REQ",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","RECYCLED",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","EPEAT-RATING",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","NEMA-STD",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","EPA-WATERSENSE",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","USEPA-PRI-CHEM-FREE",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","RENEWABLE-ENERGY-RESOURCE",0,128,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PSN",0,10,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","POPUP FILE NAME",0,128,"VARCHAR2"));
		
		// add fields data headers
		/*
        if (fieldsData != null) {
            for (int i=1; i<=15; i++) {
                if (PropertyFieldUtil.getShowInAdmin(fieldsData, i)) {
                    String fieldName = PropertyFieldUtil.getTag(fieldsData, i);
                    header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",fieldName,0,128,"VARCHAR2"));
                }
            }
        }
		*/

       return header;
    }


    public class ItemView {
        int itemId = 0;
        String tradingParnterId = "";
        String transactionSetId = "";
        String recordId = "";
        int itemSku = 0 ;
        String itemDesc = "";
        String supplierItemNo = "";
        String manufSku = "";
        String unspecCd = "";
        String supplierHierarchy = "";
        BigDecimal itemPrice = new BigDecimal(0);
        BigDecimal listPrice = new BigDecimal(0);
        String itemUom = "";
        String businessClass = "";
        String womenOwned = "";
        String minorityBusiness = "";
        String jwodBusiness = "";
        String otherBusiness = "";
        String contractId = "";
        String contractLine = "";
        String contractMandatory = "";
        String clinType = "";
        String catalogPage = "";
        String supplierUrl = "";
        String imageFileName = "";
        String manufName = "";
        String manufUrl = "";
        String itemStatus = "";
        String notes = "";
        String changeCd = "";
        String effDate = "";
        String expDate = "";
		String changeCode = "";

		String minLineQty = "";
		String maxLineQty = "";
		String issueIncrement = "";
		String greenProd = "";
		String energyStar = "";
		String bioPreferred = "";
		String msdsReq = "";
		String recycled = "";
		String epeatRating = "";
		String nemaStd = "";
		String epaWatersense = "";
		String usepaPriChemFree = "";
		String renewableEnergyResource = "";
		String psn = "";
		String popupFileName = "";

//        ArrayList dataFields;
    }



}
