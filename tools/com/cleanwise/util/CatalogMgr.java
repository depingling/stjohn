import java.sql.*;
import java.util.List;
import java.io.*;

public class CatalogMgr {

    private static String kUsage = "Usage: -DdbUrl=v -DdbUser=v -DdbPwd=v " +
        "\n -Dcmd=v4, v4=addCatalogDist" +
        "\n -Dcatid=v5 -Ddistid=v6" ;
    public static void main(String [] args ) {
		CatalogMgr o = new CatalogMgr();
		o.exec();
	}

	Connection dbc = null;
	Statement stmt = null;

    private void exec() {
        try {
            String v = System.getProperty("cmd");
            if ( v == null || v.length() == 0 ) {
                System.out.println(kUsage);
                return;
            }

            dbc = DBHelper.getDbConnection();
            stmt = dbc.createStatement();
            System.out.println( "START CatalogMgr: " );
            if ( v.equals("addCatalogDist") ) {
                String v1 = System.getProperty("catid"),
                    v2 = System.getProperty("distid");
                configureCatalogDistributor
                    (Integer.parseInt(v1),
                     Integer.parseInt(v2));
                                            
            }
            else if ( v.equals("addSkuToCatalog") ) {
                String v1 = System.getProperty("sku")
                    , v2 = System.getProperty("fromcatalog_id")
                    , v3 = System.getProperty("tocatalog_id")
                    , v4 = System.getProperty("todist_id")
                    ;
                configureCatalogItem
                    (v1,Integer.parseInt(v2)
                     ,Integer.parseInt(v3)
                     ,Integer.parseInt(v4)
                     );
                                            
            }
            else if ( v.equals("cloneContract") ) {
                String v1 = System.getProperty("contract_id")
                    ;
                cloneContract(Integer.parseInt(v1));
            }
            else if ( v.equals("addSkuToContract") ) {
                String v1 = System.getProperty("contract_id"),
                    v2 = System.getProperty("sku"),
                    v3 = System.getProperty("priceToCust"),
                    v4 = System.getProperty("costFromDist");
                addSkuToContract(Integer.parseInt(v1),
                                 v2,
                                 Double.parseDouble(v3),
                                 Double.parseDouble(v4));
            }
            else {
                System.out.println( "what? cmd=" + v );
            }
            stmt.close();
            dbc.close();
            System.out.println( "END CatalogMgr: " );
        }
        catch (Exception e) {
            System.out.println( "DB error: " );
            e.printStackTrace();
        }
    }

    private void configureCatalogItem( String pSku,
                                       int pFromCatId,
                                       int pToCatId,
                                       int pToDistId) {
        
        System.out.println("configureCatalogItem: " +
                           "pSku=" + pSku +
                           ", pFromCatId=" + pFromCatId +
                           ", pToCatId=" + pToCatId +
                           ", pToDistId=" + pToDistId
                           ) ;
        
        String sql = "select ia.item1_id, ia.item2_id, i.short_desc " +
            " from clw_item_assoc ia, clw_item i " +
            " where ia.catalog_id = " + pFromCatId +
            " and ia.item1_id in (select item_id from " +
            " clw_item where sku_num = '" + pSku + "')" +
            " and i.item_id = ia.item2_id";
		System.out.println("\t 2:: " + sql);
        ResultSet rs = null, rs2 = null;
        try {
            rs = stmt.executeQuery(sql);
            while ( rs.next() == true ) {
                int item_id = rs.getInt(1);
                String categoryName = rs.getString(3);
                System.out.println("\t 2.1:: " +
                                   "item_id=" + item_id +
                                   " categoryName=" + categoryName
                                   );
                // Is this item already in the catalog.
                sql = "select catalog_structure_id from clw_catalog_structure " +
                    " where catalog_id = " + pToCatId +
                    " and catalog_structure_cd = 'CATALOG_PRODUCT' " +
                    " and item_id = " + item_id;
                
                System.out.println("\t 2.11:: " + sql);
                rs2 = stmt.executeQuery(sql);
                if ( rs2.next() == true ) {
                    // Update the existing entry.
                    sql = "update clw_catalog_structure set bus_entity_id = " +
                        pToDistId +
                        ", mod_by = ' a 2.111' " +
                        " where catalog_structure_id = " +
                        rs2.getInt(1);
                    System.out.println("\t 2.111:: " + sql);
                    stmt.executeUpdate(sql);
                }
                else {
                    // Make a new entry
                    sql = " select clw_catalog_structure_seq.nextval from dual";
                    System.out.println("\t 2.3:: " + sql);
                    rs2 = stmt.executeQuery(sql);
                    if ( rs2.next() == true ) {
                        int nid = rs2.getInt(1);
                        sql = " insert into clw_catalog_structure " +
                            "(CATALOG_STRUCTURE_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_STRUCTURE_CD,ITEM_ID,EFF_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY )" +
                            " values ( " +
                            nid + "," + pToCatId + "," + pToDistId + ",'CATALOG_PRODUCT'," + item_id + ",sysdate ,'ACTIVE', sysdate, 'durval', sysdate, 'durval')";
                        System.out.println("\t 2.4:: " + sql);
                        stmt.executeUpdate(sql);
                    }
                }     
                // Get the category id for this catalog.
                sql = "select item_id from clw_catalog_structure " +
                    " where catalog_id = " + pToCatId +
                    " and catalog_structure_cd = 'CATALOG_CATEGORY' " +
                    " and item_id in (select item_id from clw_item " +
                    " where short_desc = '" + categoryName + "')";
                System.out.println("\t 2.41:: " + sql);
                rs2 = stmt.executeQuery(sql);
                if ( rs2.next() == true ) {
                    int catgFound = rs2.getInt(1);
                    System.out.println("\t 2.414:: catgFound=" +
                                       catgFound);
                    // Now see if an item association exists.
                     sql = "select ia.item1_id, ia.item2_id " +
                         " from clw_item_assoc ia " +
                         " where ia.catalog_id = " + pToCatId +
                         " and ia.item1_id = " + item_id +
                         " and ia.item2_id = " + catgFound +
                         " and item_assoc_cd ='PRODUCT_PARENT_CATEGORY'";
                     
                     System.out.println("\t 2.415:: " + sql);
                     rs2 = stmt.executeQuery(sql);
                     if ( rs2.next() == true ) {
                         System.out.println
                             ("The item already belongs to this category.");
                     }
                     else {
                         System.out.println("Add an item assoc entry.");
                         sql = " select clw_item_assoc_seq.nextval from dual";
                         System.out.println("\t 2.42:: " + sql);
                         rs2 = stmt.executeQuery(sql);
                         if ( rs2.next() == true ) {
                             int nid = rs2.getInt(1);
                             sql = "insert into clw_item_assoc ( " +
                                 "ITEM_ASSOC_ID, " +
                                 "ITEM1_ID, " +
                                 "ITEM2_ID, " +
                                 "CATALOG_ID, " +
                                 "ITEM_ASSOC_CD, " +
                                 "ADD_DATE, " +
                                 "ADD_BY, " +
                                 "MOD_DATE, " +
                                 "MOD_BY ) " +
                                 "values ( " +
                                 nid + "," + item_id + "," +
                                 catgFound + ","	+ pToCatId +
                                 ",'PRODUCT_PARENT_CATEGORY', sysdate," +
                                 " 'durval', sysdate,	'durval')";
                             System.out.println("\t 2.421:: " + sql);
                             stmt.executeUpdate(sql);
                         }
                     }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( rs != null ) rs.close(); 
                if ( rs2 != null ) rs2.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\t 2.5:: finally ");
        }
    }
    
    private void configureCatalogDistributor( int pCatId, int pDistId) {
        System.out.println("configureCatalogDistributor: " +
                           "pCatId=" + pCatId +
                           ", pDistId=" + pDistId ) ;
        String sql = "select count(*) from clw_catalog_assoc " +
            " where bus_entity_id = " + pDistId +
            " and catalog_id = " + pCatId +
            " and catalog_assoc_cd = 'CATALOG_DISTRIBUTOR'";
		System.out.println("\t 1:: " + sql);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            if ( rs.next() == true ) {
                int foundAssoc = rs.getInt(1);
                System.out.println("\t 1.2:: foundAssoc=" + foundAssoc);
                if (foundAssoc == 0 ) {
                    sql = " select clw_catalog_assoc_seq.nextval from dual";
                    System.out.println("\t 1.3:: " + sql);
                    rs = stmt.executeQuery(sql);
                    if ( rs.next() == true ) {
                        int nid = rs.getInt(1);
                        sql = " insert into clw_catalog_assoc " +
                            "( CATALOG_ASSOC_ID, CATALOG_ID,BUS_ENTITY_ID,CATALOG_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) values ( " +
                            nid + "," + pCatId + "," + pDistId + ",'CATALOG_DISTRIBUTOR', sysdate, 'durval', sysdate, 'durval')";
                        System.out.println("\t 1.4:: " + sql);
                        stmt.executeUpdate(sql);

                    }
                    
                }
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( rs != null ) rs.close(); }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\t 1.5:: finally ");
        }
    }

    private void addSkuToContract(int pContractId, String pSku,
                                  double pPriceToCust,
                                  double pCostFromDistributor) {
        
        System.out.println("addSkuToContract: " +
                           "pContractId=" + pContractId +
                           " pSku=" + pSku
                           + " pPriceToCust=" + pPriceToCust
                           + " pCostFromDistributor=" + pCostFromDistributor
                           ) ;
        
        ResultSet rs = null;
        try {
            int item_id = 0;
            
            String sql = "select i.item_id from clw_item i, " 
                + " clw_contract cont, clw_catalog_structure cs " 
                + " where i.sku_num = '" + pSku + "'"
                + " and cs.item_id = i.item_id "
                + " and cs.catalog_id = cont.catalog_id "
                + " and cont.contract_id = " +  pContractId            
                ;
            System.out.println("\t 4.1:: " + sql);
            
            rs = stmt.executeQuery(sql);
            if ( !rs.next() ) {
                System.out.println("\t 4.11:: ITEM NOT FOUND" );
                return;
            }
            item_id = rs.getInt(1);
            
            addItemToContract( pContractId, item_id,
                               pPriceToCust,
                               pCostFromDistributor);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( rs != null ) rs.close(); 
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\t 4.5:: finally ");
        }
    }

        private void addItemToContract(int pContractId, int pItemId,
                                       double pPriceToCust,
                                       double pCostFromDist) {
            
        System.out.println("addItemToContract: " +
                           "pContractId=" + pContractId +
                           " pItemId=" + pItemId 
                           + " pPriceToCust=" + pPriceToCust
                           + " pCostFromDist=" + pCostFromDist
                           ) ;
        
        ResultSet rs = null;
        Statement stmt2 = null;
        
        try {
            int item_id = pItemId;
            stmt2 = dbc.createStatement();

            String sql = " select count(*) from clw_contract_item where" +
                " contract_id = " + pContractId +
                " and item_id = " + item_id;
            
            System.out.println("\t 4.2:: " + sql);
            rs = stmt2.executeQuery(sql);
            if ( rs.next() ) {
                int cf = rs.getInt(1);
                if ( cf > 0 ) {
                    System.out.println("\t 4.21:: item_id=" + item_id +
                                       " is already in contract_id=" +
                                       pContractId );
                    sql = " update clw_contract_item "
                        + " set amount=" + pPriceToCust
                        + ", dist_cost=" + pCostFromDist
                        + " where" +
                        " contract_id = " + pContractId +
                        " and item_id = " + item_id;
            
                    System.out.println("\t 4.22:: " + sql);
                    stmt2.executeUpdate(sql);
                    
                    return;
                }
            }

            sql = " select clw_contract_item_seq.nextval from dual";
            System.out.println("\t 4.3:: " + sql);
            rs = stmt2.executeQuery(sql);
            rs.next();
            int nid = rs.getInt(1);
                
            sql = " insert into clw_contract_item " +              
                " ( contract_item_id, CONTRACT_ID, item_id, "
                + " amount, dist_cost, "
                + " EFF_DATE, currency_cd , ADD_DATE, ADD_BY, MOD_DATE, MOD_BY ) " +
                " values (" +
                nid + ","+ pContractId +"," +  item_id + ","
                + pPriceToCust + "," + pCostFromDist +
                ", sysdate,'USD'," +
                " sysdate, 'durval' ,sysdate, 'durval') "
                ;
                
            System.out.println("\t 4.4:: " + sql);
            stmt2.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( rs != null ) rs.close(); 
                if ( stmt2 != null ) {
                    stmt2.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\t 4.5:: finally ");
        }
    }

    
    private void cloneContract(int pSrcContractId) {
        
        System.out.println("configureCatalogItem: " +
                           "pSrcContractId=" + pSrcContractId
                           ) ;
        
        ResultSet rs = null, rs2 = null;
        try {
            String sql1 = " select short_desc, CATALOG_ID, CONTRACT_STATUS_CD, CONTRACT_TYPE_CD, EFF_DATE, CONTRACT_ITEMS_ONLY_IND, HIDE_PRICING_IND, LOCALE_CD, RANK_WEIGHT, FREIGHT_TABLE_ID from clw_contract where" +
                " contract_id = " + pSrcContractId;

            System.out.println("\t 3.1:: " + sql1);
            rs2 = stmt.executeQuery(sql1);
            if ( rs2.next() ) {
                String sql = " select clw_contract_seq.nextval from dual";
                System.out.println("\t 3.2:: " + sql);
                rs = stmt.executeQuery(sql);
                rs.next();
                int nid = rs.getInt(1);
                
                System.out.println("\t 3.201:: nid=" + nid);
                rs2 = stmt.executeQuery(sql1);
                rs2.next();
                
                String nConName = rs2.getString(1);
                System.out.println("\t 3.201:: nConName=" + nConName);
                java.util.StringTokenizer st =
                    new java.util.StringTokenizer(nConName);
                String lasttok = "";
                int tc = st.countTokens(), i = 1;
                String newname = "";
                while ( st.hasMoreTokens()) {
                    lasttok = st.nextToken();
                    if ( i < tc ) { newname += lasttok + " "; }
                    i++;
                }
                System.out.println("\t 3.21:: lasttok=" + lasttok +
                                   " newname=" + newname );
                int nextConVer = 1;
                try {
                    nextConVer = Integer.parseInt(lasttok);
                    nextConVer ++;
                }
                catch (Exception e) {}
                newname += "" + nextConVer;
                System.out.println("\t 3.211:: lasttok=" + lasttok +
                                   " newname=" + newname );
                sql = " select contract_id from clw_contract where" +
                    " short_desc = '" + newname + "'";
                System.out.println("\t 3.2111:: sql=" + sql);
                rs2 = stmt.executeQuery(sql);
                int destcontract_id = 0;
                if ( rs2.next() ) {
                    destcontract_id = rs2.getInt(1) ;
                }
                System.out.println(" == :: 1" );
                if ( destcontract_id > 0 ) {
                    System.out.println("\t contract already there:: " +
                                       " newname=" + newname);
                } else {
                System.out.println(" == :: 2" );
                destcontract_id = nid;
                System.out.println("\t 3.31:: " + sql1);
                rs2 = stmt.executeQuery(sql1);
                rs2.next();
                
                sql = " insert into clw_contract " +              
                    " ( CONTRACT_ID, REF_CONTRACT_NUM, SHORT_DESC, CATALOG_ID, CONTRACT_STATUS_CD, CONTRACT_TYPE_CD, EFF_DATE, CONTRACT_ITEMS_ONLY_IND, HIDE_PRICING_IND, LOCALE_CD, RANK_WEIGHT , ADD_DATE, ADD_BY, MOD_DATE, MOD_BY ) " +
                    " values (" +
                    nid + ",0,'" +  newname + "'," +
                    rs2.getInt("CATALOG_ID") + "," +
                    " 'LIMITED' ,'" +
                    rs2.getString("CONTRACT_TYPE_CD") + "'," +
                    " sysdate," +
                    rs2.getInt("CONTRACT_ITEMS_ONLY_IND") + "," +
                    rs2.getInt("HIDE_PRICING_IND") + ",'" +
                    rs2.getString("LOCALE_CD") + "'," +
                    rs2.getInt("RANK_WEIGHT") + "," +
                    " sysdate, 'durval' ,sysdate, 'durval') "
                    ;
                
                System.out.println("\t 3.32:: " + sql);
                stmt.executeUpdate(sql);
                }
                // Now copy all the items.
                sql = " select * from clw_contract_item where contract_id = "
                    + pSrcContractId;
                System.out.println("\t 3.4:: " + sql);
                rs = stmt.executeQuery(sql);

                while ( rs.next() ) {
                    int oitem_id = rs.getInt("ITEM_ID");
                    System.out.println("\t 3.5:: pSrcContractId=" + pSrcContractId
                                       + " destcontract_id=" + destcontract_id
                                       + " oitem_id=" + oitem_id);
                    addItemToContract(destcontract_id, oitem_id,
                                      rs.getDouble("AMOUNT"),
                                      rs.getDouble("DIST_COST")
                                      );
                                      
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( rs != null ) rs.close(); 
                if ( rs2 != null ) rs2.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\t 3.5:: finally ");
        }
    }
    
}    

    
