package com.cleanwise.service.api.reporting;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.ReportShowProperty;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;

/**
 *
 * @author  dling
 */
public class CatalogsItemReport implements GenericReport, ReportShowProperty {
    private List<String> catalogNames = null;
    private static final Logger log = Logger.getLogger(CatalogsItemReport.class);
    
    public com.cleanwise.service.api.value.GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        Map<Integer, Integer> catalogIndexMap = new HashMap<Integer, Integer>();
        Map<Integer, ChildAndParentCategory> childAndParentCategoryMap = new HashMap<Integer, ChildAndParentCategory>();
        Map<String, List<Integer>> catalogItemMap = new HashMap<String, List<Integer>>();
        catalogNames = new ArrayList<String>();
        String strStoreId = (String) pParams.get("STORE");
        String itemFilter = (String) pParams.get("LOCATE_ITEM_OPT");
        if (!Utility.isSet(itemFilter)){
        	itemFilter = (String) pParams.get("ITEM_OPT");
        }

        // get list of shopping catalog selected or all active shopping catalog in store of no selected
        String catalogFilter = (String)ReportingUtils.getParam(pParams,"LOCATE_CATALOG_MULTI_OPT");
        String selCatalog = "SELECT CATALOG_ID, SHORT_DESC FROM CLW_CATALOG \r\n" +
	        "WHERE CATALOG_ID IN ";
        if (Utility.isSet(catalogFilter)){
        	selCatalog += "(" + catalogFilter + ") \r\n";
        }else{
        	selCatalog += "(SELECT DISTINCT CATALOG_ID FROM CLW_CATALOG_ASSOC WHERE BUS_ENTITY_ID IN (" + strStoreId + ") \r\n" +
	        "AND CATALOG_ASSOC_CD = 'CATALOG_STORE') \r\n" +
	        "AND CATALOG_TYPE_CD = 'SHOPPING' \r\n" +
	        "AND CATALOG_STATUS_CD = 'ACTIVE' \r\n";
        }
        selCatalog += "ORDER BY SHORT_DESC";
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(selCatalog);
        int i = 0;
        while (rs.next()){
        	catalogIndexMap.put(new Integer(rs.getInt(1)), new Integer(i++));
        	catalogNames.add(rs.getString(2));
        }
        
        // get list child catalog to parent catalog map from store catalog
        String selChildAndParentCategory = "SELECT ITEM1_ID CHILD_CAT_ID, ITEM2_ID PARENT_CAT_ID, PA.SHORT_DESC PARENT_CAT_NAME \r\n" +
	        "FROM CLW_ITEM_ASSOC IA, CLW_ITEM PA \r\n" +
	        "WHERE IA.CATALOG_ID = ( \r\n" +
	        "  SELECT C.CATALOG_ID FROM CLW_CATALOG C, CLW_CATALOG_ASSOC CA \r\n" +
	        "  WHERE C.CATALOG_ID = CA.CATALOG_ID \r\n" +
	        "  AND C.CATALOG_STATUS_CD = 'ACTIVE' \r\n" +
	        "  AND C.CATALOG_TYPE_CD = 'STORE' \r\n" +
	        "  AND CA.BUS_ENTITY_ID = " + strStoreId + " \r\n" +
	        ") \r\n" +
	        "AND IA.ITEM_ASSOC_CD = 'CATEGORY_PARENT_CATEGORY' \r\n" +
	        "AND IA.ITEM2_ID = PA.ITEM_ID";
        rs = stmt.executeQuery(selChildAndParentCategory);
        while (rs.next()){
        	childAndParentCategoryMap.put(new Integer(rs.getInt("CHILD_CAT_ID")), new ChildAndParentCategory(rs.getInt("CHILD_CAT_ID"), rs.getInt("PARENT_CAT_ID"), rs.getString("PARENT_CAT_NAME")));
        }
        
        // get all the item to shopping catalog information
        String selCatItem = "select cc.catalog_id,cci.item_id,cat.item_id category_id,cci.amount catalog_price \r\n" +
	        "from clw_catalog cc,clw_catalog_assoc cca,clw_contract con, \r\n" +
	        "clw_contract_item cci,clw_item_assoc cia,clw_item cat \r\n" +
	        "where cc.catalog_id = cca.catalog_id \r\n" +
	        "and cca.bus_entity_id = " + strStoreId + " \r\n" +
	        "and cc.catalog_status_cd = 'ACTIVE' \r\n" +
	        "and cc.catalog_type_cd = 'SHOPPING' \r\n" +
	        "and cc.catalog_id = con.catalog_id  \r\n" +
	        "and con.contract_id = cci.contract_id  \r\n" +
	        "and cci.item_id = cia.item1_id \r\n" +
	        "and cia.item2_id = cat.item_id \r\n" +
	        "and cia.catalog_id = cc.catalog_id \r\n";
        if (Utility.isSet(itemFilter))
        	selCatItem += "and cci.item_id in (" + itemFilter + ") \r\n";
        if (Utility.isSet(catalogFilter)){
        	selCatItem += "and cc.catalog_id in (" + catalogFilter + ") \r\n";
        }
        rs = stmt.executeQuery(selCatItem);
        while (rs.next()){
        	String key = getCatalogItemKey(rs.getInt("item_id"), rs.getInt("category_id"), rs.getBigDecimal("catalog_price"));
        	List<Integer> catalogIds = catalogItemMap.get(key);
        	if (catalogIds==null){
        		catalogIds = new ArrayList<Integer>();
        		catalogItemMap.put(key, catalogIds);
        	}
        	catalogIds.add(rs.getInt("catalog_id"));        	
        }
        
        // get list of items with unique item id, category and price
        String sql = "select  distinct ci.item_id, \r\n" +
        "ci.sku_num store_sku, \r\n" +
        "cim.item_num dist_sku, \r\n" +
        "cim.item_uom dist_uom, \r\n" +
        "cim.item_pack dist_pack, \r\n" +
        "dist.short_desc distributor, \r\n" +
        "ci.short_desc item_short_desc, \r\n" +
        "ci.long_desc item_long_desc, \r\n" +
        "(select max(clw_value) from clw_item_meta uom where ci.item_id = uom.item_id and uom.name_value = 'UOM') item_uom, \r\n" +
        "(select max(clw_value) from clw_item_meta pack where ci.item_id = pack.item_id and pack.name_value = 'PACK') item_pack, \r\n" +
        "(select max(clw_value) from clw_item_meta isize where ci.item_id = isize.item_id and isize.name_value = 'SIZE') item_size, \r\n" +
        "(select max(clw_value) from clw_item_meta isize where ci.item_id = isize.item_id and isize.name_value = 'MSDS') msds, \r\n" +
        "(select max(clw_value) from clw_item_meta isize where ci.item_id = isize.item_id and isize.name_value = 'IMAGE') image, \r\n" +
        "(select manu.short_desc from clw_bus_entity manu, clw_item_mapping ima where manu.bus_entity_type_cd = 'MANUFACTURER' and ima.item_id = ci.item_id and ima.bus_entity_id = manu.bus_entity_id and ima.item_mapping_cd = 'ITEM_MANUFACTURER' \r\n" +
        ") manufacturer, \r\n" +
        "(select ima.item_num from clw_bus_entity manu, clw_item_mapping ima where manu.bus_entity_type_cd = 'MANUFACTURER' and ima.item_id = ci.item_id and ima.bus_entity_id = manu.bus_entity_id and ima.item_mapping_cd = 'ITEM_MANUFACTURER' \r\n" +
        ") as manu_sku, \r\n" +
        "cat.item_id category_id, \r\n" +
        "cat.short_desc category, \r\n" +
        "(select max(clw_value) from clw_item_meta unspsc where ci.item_id = unspsc.item_id and unspsc.name_value = 'UNSPSC_CD') UNSPSC_cd, \r\n" +
        "cs.customer_sku_num, \r\n" +
        "cci.dist_cost, cci.amount catalog_price \r\n" +
        "from clw_catalog cc,clw_catalog_assoc cca,clw_contract con,clw_bus_entity store,clw_bus_entity dist,clw_catalog_structure cs, \r\n" +
        "clw_contract_item cci,clw_item ci,clw_item_mapping cim,clw_item_assoc cia,clw_item cat \r\n" +
        "where cc.catalog_id = cca.catalog_id \r\n" +
        "and cca.bus_entity_id = store.bus_entity_id \r\n" +
        "and store.bus_entity_id = " + strStoreId + " \r\n" +
        "and cc.catalog_status_cd = 'ACTIVE' \r\n" +
        "and cc.catalog_type_cd = 'SHOPPING' \r\n" +
        "and cs.catalog_id = cc.catalog_id \r\n" +
        "and cs.item_id = ci.item_id \r\n" +
        "and cc.catalog_id = con.catalog_id \r\n" +
        "and con.contract_id = cci.contract_id \r\n" +
        "and cci.item_id = ci.item_id \r\n" +
        "and ci.item_id = cim.item_id \r\n" +
        "and cim.item_mapping_cd = 'ITEM_DISTRIBUTOR' \r\n" +
        "and cim.bus_entity_id = cs.bus_entity_id \r\n" +
        "and dist.bus_entity_type_cd = 'DISTRIBUTOR' \r\n" +
        "and dist.bus_entity_id = cim.bus_entity_id \r\n" +
        "and ci.item_id = cia.item1_id \r\n" +
        "and cia.item2_id = cat.item_id \r\n" +
        "and cia.catalog_id = cc.catalog_id \r\n";
        if (Utility.isSet(itemFilter))
        	sql += "and ci.item_id in (" + itemFilter + ") \r\n";
        sql += "order by ci.item_id \r\n";
        
        Map<String, List<Integer>> itemMap = new TreeMap<String, List<Integer>>();
                
        rs = stmt.executeQuery(sql);
        while (rs.next()){
        	List row = new ArrayList();
        	Integer itemId = new Integer(rs.getInt("item_id"));
        	Integer categoryId = new Integer(rs.getInt("category_id"));
        	BigDecimal catalogPrice = rs.getBigDecimal("catalog_price");
        	String key = getCatalogItemKey(itemId, categoryId, catalogPrice);
        	if (itemMap.containsKey(key)){
        		log.info(">>>>>>>>>>>>>>>>> Multiple item matched on key: " + key.toString());
        		continue;
        	}
        	
        	int idx = 0;
        	row.add(idx++, itemId);
        	row.add(idx++, new Integer(rs.getInt("store_sku")));
            row.add(idx++, rs.getString("dist_sku"));
            row.add(idx++, rs.getString("dist_uom"));
            row.add(idx++, rs.getString("dist_pack"));            
            row.add(idx++, rs.getString("distributor"));
            row.add(idx++, rs.getString("item_short_desc"));
            row.add(idx++, rs.getString("item_long_desc"));
            row.add(idx++, rs.getString("item_uom"));
            row.add(idx++, rs.getString("item_pack"));
            row.add(idx++, rs.getString("item_size"));
            if (Utility.isSet(rs.getString("msds")))
            	row.add(idx++, "TRUE");
            else
            	row.add(idx++, "FALSE");
            
            if (Utility.isSet(rs.getString("image")))
            	row.add(idx++, "TRUE");
            else
            	row.add(idx++, "FALSE");
            row.add(idx++, rs.getString("manufacturer"));
            row.add(idx++, rs.getString("manu_sku"));
            row.add(idx++, "");// category 1
            row.add(idx++, "");// category 2
            row.add(idx++, "");// category 3
            row.add(idx++, rs.getString("category"));// category 4
            // set parent category leve 3, 2, 1
            setParentCategory(idx-2, row, categoryId, childAndParentCategoryMap);
            row.add(idx++, rs.getString("UNSPSC_cd"));
            row.add(idx++, rs.getString("customer_sku_num"));            
            row.add(idx++, rs.getBigDecimal("dist_cost"));
            row.add(idx++, catalogPrice);
            for (String catalogName : catalogNames){
            	row.add("");
            }
            
            List<Integer> catalogIds = catalogItemMap.get(key);
            if (catalogIds != null){
	            for (int catalogId : catalogIds){
	            	int catIdx = catalogIndexMap.get(catalogId);
	            	row.set(idx+catIdx, "X");
	            }
            }
            itemMap.put(key, row);            
        }
        ArrayList catalogsItem = new ArrayList();
        Iterator it = itemMap.keySet().iterator();
        while (it.hasNext()){
        	catalogsItem.add(itemMap.get(it.next()));
        }
        GenericReportResultView ret = GenericReportResultView.createValue();
        ret.setTable(catalogsItem);
        ret.setHeader(getReportHeader());
        ret.setColumnCount(ret.getHeader().size());
        
        return ret;
    }
    
    private void setParentCategory(int colIndex, List row, int categoryId, Map<Integer, ChildAndParentCategory> childAndParentCategoryMap){
    	ChildAndParentCategory cAndPCategory = childAndParentCategoryMap.get(categoryId);
        if (cAndPCategory != null){
        	row.set(colIndex, cAndPCategory.parentCategoryName);
        	setParentCategory(colIndex-1, row, cAndPCategory.parentCategoryId, childAndParentCategoryMap);
        }
    }
    
    private GenericReportColumnViewVector getReportHeader() {
    	GenericReportColumnViewVector header = new GenericReportColumnViewVector();
    	header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Item ID",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Store SKU",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Short Desc",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Item Long Desc",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Size",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","MSDS",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Image",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufacturer",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manuf SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category 1",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category 2",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category 3",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category 4",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UNSPSC Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Dist Cost",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Catalog Price",2,20,"NUMBER"));
        for (String catalogName : catalogNames){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",catalogName,0,255,"VARCHAR2"));
        }
        
        return header;
    }    

	@Override
	public boolean showOnlyDownloadReportButton() {
		return true;
	}
	
	private String getCatalogItemKey(Integer itemId, Integer categoryId, BigDecimal catalogPrice){
		return itemId+"|"+categoryId+"|"+catalogPrice;
	}
	
	private class ChildAndParentCategory {
		int childCategoryId;
		int parentCategoryId;
		String parentCategoryName;
		
		public ChildAndParentCategory(int childCategoryId, int parentCategoryId, String parentCategoryName){
			this.childCategoryId = childCategoryId;
			this.parentCategoryId = parentCategoryId;
			this.parentCategoryName = parentCategoryName;
		}
	}
	
	
}
