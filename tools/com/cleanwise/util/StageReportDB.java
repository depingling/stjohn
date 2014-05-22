import java.sql.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;

public class StageReportDB {
    private static final int PROCCESS_RECORD_MAX_AMOUNT = 250; //just the max amount it will process per iteration. 
    
    private static String kUsage = "Usage: -DdbUrl=<db url> -DdbUser=<db user> " + 
    "-DdbPwd=<db password> -Dstartid=<opt start invoice cust detail id> -Dendid=<opt end invoice cust detail id> "+
    "-Dmaxrows=<opt max rows to select> "+
    "-Dcmd=<opt command (manu_upd:updates rows where manu info is empty | synch:updates missing rows in the si_invoice_cust_detail (non existant) | category:updates rows where category info is empty)>";

    public static void main(String[] args) {
        StageReportDB o = new StageReportDB();

        o.exec();
    }

    Connection dbc = null;

    Statement stmt = null;

    int lastIdLoaded = 0; // 614781;

    int startId = -1, endId = -1, maxrows = -1;

    int totalLoaded = 0;

    String vCmd = null;

    private int loadStagedInfo() throws Exception {
        String s = System.getProperty("startid");
        if (null != s) {
            startId = Integer.parseInt(s);
            System.out.println("  ** startId=" + startId);
        }
        s = System.getProperty("endid");
        if (null != s) {
            endId = Integer.parseInt(s);
            System.out.println("  ** endId=" + endId);
        }
        s = System.getProperty("maxrows");
        if (null != s) {
            maxrows = Integer.parseInt(s);
            System.out.println("  ** maxrows=" + maxrows);
        }
        vCmd = System.getProperty("cmd");

        String sql = "select invoice_cust_detail_id " + " from clw_invoice_cust_detail icd"
                + " , clw_invoice_cust ic where ";
        sql += " ic.invoice_cust_id = icd.invoice_cust_id ";

        if (lastIdLoaded > 0) {
            sql += " and invoice_cust_detail_id  < " + lastIdLoaded;
        }
        if (startId > -1) {
            sql += " and invoice_cust_detail_id  >= " + startId;
        }
        if (endId > 0) {
            sql += " and invoice_cust_detail_id  <= " + endId;
        }

        if (endId <= 0 && startId <= 0) {
            sql += " and ic.invoice_date >= sysdate - 7 ";
        }

        sql += "  order by invoice_cust_detail_id desc";

        if (vCmd != null && vCmd.equals("manu_upd")) {
            sql = " select invoice_cust_detail_id from " + " si_invoice_cust_detail where manu_id is null ";
        } else if (vCmd != null && vCmd.equals("synch")) {
            sql = " select icd.invoice_cust_detail_id " + " from clw_invoice_cust_detail icd  where  "
                    + " not exists (select * from si_invoice_cust_detail si  where "
                    + " si.invoice_cust_detail_id = icd.invoice_cust_detail_id) ";
            if (lastIdLoaded > 0) {
                sql += " and invoice_cust_detail_id  < " + lastIdLoaded;
            }
            sql += " and invoice_cust_detail_id  > 1 ";
            sql += "  order by invoice_cust_detail_id desc";
        } else if (vCmd != null  && vCmd.equals("category")) {
            sql = " select invoice_cust_detail_id from " + " si_invoice_cust_detail where category is null ";
        }

        stmt.setMaxRows(PROCCESS_RECORD_MAX_AMOUNT);
        //System.out.println(" sql=" + sql);
        ResultSet rs = executeQuery(stmt,sql);
        ArrayList toLoadIds = new ArrayList();
        int ct = 0;
        while (rs.next()) {
            lastIdLoaded = rs.getInt(1);
            toLoadIds.add(new Integer(lastIdLoaded));
            ++ct;

            if (maxrows > 0 && totalLoaded >= maxrows) {
                break;
            }
        }

        loadit(toLoadIds);
        System.out.println(" ct=" + ct + " lastIdLoaded=" + lastIdLoaded + " totalLoaded=" + totalLoaded);
        rs.close();

        return ct;
    }

    private String toCommaList(List l){
        Iterator it = l.iterator();
        StringBuffer sb = new StringBuffer();
        while(it.hasNext()){
            sb.append(it.next());
            if(it.hasNext()){
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    private void loadit(ArrayList pInvCustDetailIdList) throws Exception {

        Statement stmt2 = dbc.createStatement();
        PreparedStatement selectOneRow = null;
        String invCustDetailIdList = toCommaList(pInvCustDetailIdList);
        if(null == invCustDetailIdList || "".equals(invCustDetailIdList)){
            return;
        }
        
        if(selectOneRow == null){
            String sql = " select "
                    + " icd.invoice_cust_detail_id, "
                    + " act.short_desc as customer_name, "
                    + " oi.item_id, i.sku_num , i.short_desc item_desc, "
                    + " (select bsc.short_desc from  clw_bus_entity_assoc ba, clw_bus_entity bsc where ba.bus_entity_assoc_cd = 'BSC FOR SITE' and ba.bus_entity1_id = bsc.bus_entity_id and bsc.bus_entity_type_cd = 'BUILDING_SVC_CONTRACTOR' and ba.bus_entity2_id=ic.site_id) bsc,"
                    + " (select uom_meta.clw_value from " + "   clw_item_meta uom_meta where "
                    + "   uom_meta.name_value = 'UOM'  " + "   and i.item_id = uom_meta.item_id ) uom ,"
                    + " (select pack_meta.clw_value from " + "   clw_item_meta pack_meta where "
                    + "   pack_meta.name_value = 'PACK'  " + "   and i.item_id = pack_meta.item_id) pack, "
                    + " (select size_meta.clw_value from " + "   clw_item_meta size_meta where "
                    + "   size_meta.name_value = 'SIZE'  " + "   and i.item_id = size_meta.item_id) ITEM_SIZE, "
                    + " oi.dist_item_cost " + " , manu.short_desc manu_name " + " , manu.bus_entity_id manu_id "
                    + " , mapmanu.item_num manu_num " + " , dist.bus_entity_id as vendor_id "
                    + " , dist.erp_num as vendor_num " + " , dist.short_desc as vendor_name "
                    + " , (SELECT man.short_desc FROM CLW_ITEM_MAPPING m , "
                    + "     clw_bus_entity man   WHERE m.ITEM_MAPPING_ID IN " + "     (SELECT MAX(IMA.ITEM_MAPPING2_ID)  "
                    + "      FROM CLW_ITEM_MAPPING_ASSOC IMA, " + "      CLW_ITEM_MAPPING IM "
                    + "      WHERE IM.ITEM_MAPPING_ID = IMA.ITEM_MAPPING1_ID "
                    + "      AND IM.bus_entity_id = dist.bus_entity_id " + "      and im.item_id = oi.item_id "
                    + "      and im.item_mapping_cd = 'ITEM_DISTRIBUTOR') "
                    + "      and m.item_mapping_cd = 'ITEM_GENERIC_MFG' "
                    + "      and m.bus_entity_id = man.bus_entity_id) as " + "         dist_manu_name "
                    + " , (SELECT m.item_num FROM CLW_ITEM_MAPPING m " + "    WHERE m.ITEM_MAPPING_ID IN "
                    + "    (SELECT MAX(IMA.ITEM_MAPPING2_ID) "
                    + "     FROM CLW_ITEM_MAPPING_ASSOC IMA, CLW_ITEM_MAPPING IM"
                    + "     WHERE IM.ITEM_MAPPING_ID = IMA.ITEM_MAPPING1_ID "
                    + "     AND IM.bus_entity_id = dist.bus_entity_id " + "     and im.item_id = oi.item_id "
                    + "     and im.item_mapping_cd = 'ITEM_DISTRIBUTOR') "
                    + "     and m.item_mapping_cd = 'ITEM_GENERIC_MFG' " + " ) as dist_manu_num  "
                    + ", act.erp_num as customer_number " + ",       ( select max(xic.short_desc) "
                    + "  from clw_item xic, clw_item_assoc xia, clw_item xi, "
                    + "  clw_catalog xcat, clw_catalog_assoc xcata  " + "  where 1=1 "
                    + "  and xic.item_id = xia.item2_id " + "  and xia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY' "
                    + "  and xia.item1_id = xi.item_id and xi.item_id = oi.item_id"
                    + "  and xia.catalog_id = xcata.catalog_id " + "  and xcat.catalog_type_cd = 'ACCOUNT' "
                    + "  and xcat.catalog_id = xcata.catalog_id " + "  and xcata.catalog_assoc_cd = 'CATALOG_ACCOUNT'"
                    + "  and xcata.bus_entity_id = act.bus_entity_id ) category " + ",       ( select max(xic.item_id) "
                    + "  from clw_item xic, clw_item_assoc xia, clw_item xi, "
                    + "  clw_catalog xcat, clw_catalog_assoc xcata  " + "  where 1=1 "
                    + "  and xic.item_id = xia.item2_id " + "  and xia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY' "
                    + "  and xia.item1_id = xi.item_id and xi.item_id = oi.item_id"
                    + "  and xia.catalog_id = xcata.catalog_id " + "  and xcat.catalog_type_cd = 'ACCOUNT' "
                    + "  and xcat.catalog_id = xcata.catalog_id " + "  and xcata.catalog_assoc_cd = 'CATALOG_ACCOUNT'"
                    + "  and xcata.bus_entity_id = act.bus_entity_id ) category_id " + " from clw_invoice_cust ic, "
                    + " clw_invoice_cust_detail icd, clw_order_item oi " + " , clw_item i, clw_bus_entity act "
                    + " , clw_item_mapping mapmanu " + " , clw_bus_entity manu  " + " , clw_bus_entity  dist  "
                    + " where icd.invoice_cust_detail_id in  ("+ toCommaList(pInvCustDetailIdList)+") "
                    + " and icd.invoice_cust_id = ic.invoice_cust_id " + " and ic.account_id = act.bus_entity_id "
                    + " and i.item_id = oi.item_id " + " and icd.order_item_id = oi.order_item_id  "
                    + " and mapmanu.item_mapping_cd = 'ITEM_MANUFACTURER' and mapmanu.item_id = i.item_id  "
                    + " and mapmanu.bus_entity_id = manu.bus_entity_id and dist.erp_num = oi.dist_erp_num "
                    + " and dist.bus_entity_type_cd = 'DISTRIBUTOR' ";
    
            selectOneRow = dbc.prepareStatement(sql);
            System.out.println("select 1 sql =" + sql);
        }
        
          
         

        //selectOneRow.setArray(1,pInvCustDetailIdList.toArray());
        ResultSet rs = executePreparedStatementQuery(selectOneRow);
        while (rs.next()) {
            int invCustDetailId = rs.getInt("invoice_cust_detail_id");
            String customer_name = rs.getString("CUSTOMER_NAME"), manu_name = rs.getString("MANU_NAME"), vendor_num = rs
                    .getString("VENDOR_NUM"), manu_num = rs.getString("MANU_NUM"), vendor_name = rs
                    .getString("VENDOR_NAME"), dist_manu_name = rs.getString("DIST_MANU_NAME"), dist_manu_num = rs
                    .getString("DIST_MANU_NUM"), customer_number = rs.getString("CUSTOMER_NUMBER"), category_name = rs
                    .getString("CATEGORY"), item_desc = rs.getString("ITEM_DESC"), item_pack = rs.getString("PACK"), item_uom = rs
                    .getString("UOM"), item_size = rs.getString("ITEM_SIZE"), dist_item_cost = rs
                    .getString("DIST_ITEM_COST"), bsc = rs.getString("BSC");

            customer_name = customer_name.replaceAll("'", "''");
            item_desc = item_desc.replaceAll("'", "''");
            if (null != dist_manu_name) {
                dist_manu_name = dist_manu_name.replaceAll("'", "''");
            } else {
                dist_manu_name = ".";
            }
            if (null == dist_manu_num) {
                dist_manu_num = ".";
            }
            if (null == manu_num) {
                manu_num = ".";
            } else {
                manu_num = manu_num.replaceAll("'", "''");
            }
            if (null == item_size) {
                item_size = ".";
            } else {
                item_size = item_size.replaceAll("'", "''");
            }

            if (null == category_name) {
                category_name = ".";
            }

            long item_id = rs.getLong("ITEM_ID"), manu_id = rs.getLong("MANU_ID"), category_id = rs
                    .getLong("CATEGORY_ID"), sku_num = rs.getLong("SKU_NUM"), vendor_id = rs.getLong("VENDOR_ID");

            String usql = "update si_invoice_cust_detail set " + " customer_name = '"
                    + sanitizeStringForSQL(customer_name) + "'" + ", item_id = " + item_id + ", item_desc = '"
                    + sanitizeStringForSQL(item_desc) + "'" + ", pack = '" + sanitizeStringForSQL(item_pack) + "'"
                    + ", uom = '" + sanitizeStringForSQL(item_uom) + "'" + ", item_size = '"
                    + sanitizeStringForSQL(item_size) + "'" + ", sku_num = " + sku_num + " , manu_name = '"
                    + sanitizeStringForSQL(manu_name) + "'" + " , manu_num = '" + sanitizeStringForSQL(manu_num) + "'"
                    + " , manu_id = " + manu_id + " , vendor_id =" + vendor_id + " , vendor_num = '"
                    + sanitizeStringForSQL(vendor_num) + "'" + " , vendor_name ='" + sanitizeStringForSQL(vendor_name)
                    + "'" + " , dist_manu_name ='" + sanitizeStringForSQL(dist_manu_name) + "'"
                    + " , dist_manu_num  = '" + sanitizeStringForSQL(dist_manu_num) + "'" + ",  dist_item_cost = "
                    + sanitizeStringForSQLNumeric(dist_item_cost) + " , category  = '" + sanitizeStringForSQL(category_name) + "'"
                    + " , category_id  = " + category_id + " , customer_number  = '"
                    + sanitizeStringForSQL(customer_number) + "'" + " , contractor = '" + sanitizeStringForSQL(bsc)
                    + "'" + " where invoice_cust_detail_id = " + invCustDetailId;

            // System.out.println( " usql=" + usql);

            
            int updct = executeUpdate(stmt2,usql);
            totalLoaded++;
            
             System.out.print( "\n pInvCustDetailId=" + invCustDetailId + 
             "totalLoaded=" + totalLoaded );
              System.out.println("updct = "+updct);
            if (updct > 1) {
                stmt2.close();
                throw new Exception("multiple recs, usql=" + usql);
            }

            if (updct == 0) {
                String isql = "insert into si_invoice_cust_detail ( " + " invoice_cust_detail_id " + ", customer_name "
                        + ", item_id " + ", item_desc " + ", pack " + ", uom " + ", item_size " + ", sku_num "
                        + " , manu_name " + " , manu_id " + " , manu_num " + " , vendor_id " + " , vendor_num "
                        + " ,  vendor_name " + " ,  dist_manu_name " + " , dist_manu_num, dist_item_cost, category "
                        + " , category_id, customer_number, contractor " + " ) values ( " + invCustDetailId + ", '"
                        + sanitizeStringForSQL(customer_name) + "'" + ", " + item_id + ", '"
                        + sanitizeStringForSQL(item_desc) + "'" + ", '" + sanitizeStringForSQL(item_pack) + "'" + ", '"
                        + sanitizeStringForSQL(item_uom) + "'" + ", '" + sanitizeStringForSQL(item_size) + "'" + ", "
                        + sku_num + " , '" + sanitizeStringForSQL(manu_name) + "'" + " , " + manu_id + " , '"
                        + sanitizeStringForSQL(manu_num) + "'" + " ," + vendor_id + " ,'"
                        + sanitizeStringForSQL(vendor_num) + "'" + " ,'" + sanitizeStringForSQL(vendor_name) + "'"
                        + " ,'" + sanitizeStringForSQL(dist_manu_name) + "'" + " ,'"
                        + sanitizeStringForSQL(dist_manu_num) + "'" + " ,'" + sanitizeStringForSQLNumeric(dist_item_cost) + "'" + " ,'"
                        + sanitizeStringForSQL(category_name) + "'" + " ," + category_id + " ,'"
                        + sanitizeStringForSQL(customer_number) + "'" + " ,'" + sanitizeStringForSQL(bsc) + "'" + " ) ";
                        
                        
                System.out.println("doing insert="+isql);
                if (executeUpdate(stmt2,isql) != 1) {
                    stmt2.close();
                    throw new Exception("failed on isql=" + isql);
                }

                if ((totalLoaded % 10) == 0) {
                    System.out.print("i");
                }
            } else {
                if ((totalLoaded % 10) == 0) {
                    System.out.print("u");
                }
            }
            String majorCatUpdate = " update si_invoice_cust_detail s set " + " s.major_category = "
                    + " (select i.short_desc from clw_item_assoc ia , "
                    + "  clw_item i where item1_id = s.category_id "
                    + " and ia.item_assoc_cd = 'CATEGORY_MAJOR_CATEGORY' " + " and i.item_id = ia.item2_id) "
                    + " , s.major_category_id =   " + " (select item2_id from clw_item_assoc ia "
                    + " where item1_id = s.category_id and " + " ia.item_assoc_cd = 'CATEGORY_MAJOR_CATEGORY') "
                    + " where s.invoice_cust_detail_id = " + invCustDetailId;

            if (executeUpdate(stmt2,majorCatUpdate) != 1) {
                stmt2.close();
                throw new Exception("failed on majorCatUpdate=" + majorCatUpdate);
            }
        }
        rs.close();
        stmt2.close();

    }
    
    private int executeUpdate(Statement stmt, String sql) throws Exception{
        try{
            return stmt.executeUpdate(sql);
        }catch(Exception e){
            System.out.println("ERROR SQL=");
            System.out.println(sql);
            throw e;
        }
    }
    
    private ResultSet executePreparedStatementQuery(PreparedStatement stmt) throws Exception{
        return stmt.executeQuery();
    }
    
    private ResultSet executeQuery(Statement stmt, String sql) throws Exception{
        try{
            return stmt.executeQuery(sql);
        }catch(Exception e){
            System.out.println("ERROR SQL=");
            System.out.println(sql);
            throw e;
        }
    }
    
    private String sanitizeStringForSQLNumeric(String pVal)throws Exception {
        pVal = sanitizeStringForSQL(pVal);
        if(pVal == null){
            return "0";
        }
        pVal = pVal.trim();
        if(pVal.equals(".") || pVal.equals("")){
            return "0";
        }
        try{
            new BigDecimal(pVal);
        }catch(Exception e){
            System.out.println("Not a number: ["+pVal+"]");
            throw e;
        }
        return pVal;
    }

    private String sanitizeStringForSQL(String pVal) {
        if (pVal == null || pVal.equalsIgnoreCase("null")) {
            return "";
        }
        pVal = pVal.replaceAll("''", "'");
        return pVal.replaceAll("'", "''");
    }

    private void exec() {
        try {
            dbc = DBHelper.getDbConnection();
            stmt = dbc.createStatement();
            int lastLoadedIdOnLastRun = 0;
            int recursionCheck = 0;
            while (loadStagedInfo() > 0) {
                dbc.commit();
                java.lang.Thread.sleep(1000);
                if (maxrows > 0 && totalLoaded >= maxrows) {
                    System.out.println("  totalLoaded=" + totalLoaded + " > maxrows=" + maxrows);
                    break;
                }
                if(lastIdLoaded == lastLoadedIdOnLastRun){
                    recursionCheck ++;
                    if(recursionCheck > 5){//something reasonable...probebly not necessary
                        break;
                    }
                }else{
                    recursionCheck = 0; //reset recursion check
                }
                lastLoadedIdOnLastRun = lastIdLoaded;
            }
            System.out.println("\n  totalLoaded=" + totalLoaded);
            stmt.close();
            dbc.commit();
            dbc.close();
        } catch (Exception e) {
            System.out.println("DB error: ");
            e.printStackTrace();
        }
    }

}

/*
 * 
 * select ic.invoice_num , icd.ITEM_QUANTITY , icd.cust_contract_price ,
 * icd.line_total , o.order_num , o.original_order_date ,ic.SHIPPING_NAME
 * ,ic.SHIPPING_ADDRESS_1 ,ic.SHIPPING_ADDRESS_2 ,ic.SHIPPING_ADDRESS_3
 * ,ic.SHIPPING_ADDRESS_4 ,ic.SHIPPING_CITY ,ic.SHIPPING_STATE
 * ,ic.SHIPPING_POSTAL_CODE ,ic.erp_po_num cw_po_num ,ic.invoice_date
 * ,ic.account_id , a.short_desc as customer_name , oi.item_id , i.sku_num
 * ,i.short_desc as item_desc , manu.short_desc manu_name , mapmanu.item_num
 * manu_num , dist.bus_entity_id as vendor_id , dist.erp_num as vendor_num ,
 * dist.short_desc as vendor_name , (SELECT man.short_desc FROM CLW_ITEM_MAPPING
 * m , clw_bus_entity man WHERE m.ITEM_MAPPING_ID IN (SELECT
 * MAX(IMA.ITEM_MAPPING2_ID) FROM CLW_ITEM_MAPPING_ASSOC IMA, CLW_ITEM_MAPPING
 * IM WHERE IM.ITEM_MAPPING_ID = IMA.ITEM_MAPPING1_ID AND IM.bus_entity_id =
 * dist.bus_entity_id and im.item_id = oi.item_id and im.item_mapping_cd =
 * 'ITEM_DISTRIBUTOR') and m.item_mapping_cd = 'ITEM_GENERIC_MFG' and
 * m.bus_entity_id = man.bus_entity_id) as dist_manu_name , (SELECT m.item_num
 * FROM CLW_ITEM_MAPPING m WHERE m.ITEM_MAPPING_ID IN (SELECT
 * MAX(IMA.ITEM_MAPPING2_ID) FROM CLW_ITEM_MAPPING_ASSOC IMA, CLW_ITEM_MAPPING
 * IM WHERE IM.ITEM_MAPPING_ID = IMA.ITEM_MAPPING1_ID AND IM.bus_entity_id =
 * dist.bus_entity_id and im.item_id = oi.item_id and im.item_mapping_cd =
 * 'ITEM_DISTRIBUTOR') and m.item_mapping_cd = 'ITEM_GENERIC_MFG' ) as
 * dist_manu_num from clw_invoice_cust ic, clw_invoice_cust_detail icd ,
 * clw_order o , clw_order_item oi, clw_bus_entity a , clw_item i,
 * clw_item_mapping mapmanu, clw_bus_entity manu , (select bus_entity_id,
 * erp_num, short_desc from clw_bus_entity where bus_entity_type_cd =
 * 'DISTRIBUTOR') dist where ic.account_id in (#ACCOUNT_MULTI_OPT#) and
 * ic.invoice_date between to_date('#BEG_DATE#', 'mm/dd/yyyy') and
 * to_date('#END_DATE#', 'mm/dd/yyyy') and icd.invoice_cust_id =
 * ic.invoice_cust_id and oi.order_item_id = icd.order_item_id and o.order_id =
 * oi.order_id and a.bus_entity_id = ic.account_id and oi.item_id = i.item_id
 * and mapmanu.item_mapping_cd = 'ITEM_MANUFACTURER' and mapmanu.item_id =
 * i.item_id and mapmanu.bus_entity_id = manu.bus_entity_id and dist.erp_num =
 * oi.dist_erp_num
 * 
 * 
 */
