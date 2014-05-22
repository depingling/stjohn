package com.cleanwise.service.api.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.cachecos.CacheKey;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.ProductBundle;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterRuleData;
import com.cleanwise.service.api.value.CostCenterRuleDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.PriceListData;
import com.cleanwise.service.api.value.PriceListDetailData;
import com.cleanwise.service.api.value.PriceListDetailDataVector;
import com.cleanwise.service.api.value.PriceValue;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ProductPriceView;
import com.cleanwise.service.api.value.ProductSkuView;


/**
 * <code>ProductDAO</code>
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class ProductDAO {

    private static final Logger log = Logger.getLogger(ProductDAO.class);
    private final static int inClauseSize=500;

    ProductDataVector mPdv = null;
    IdVector mItemIdVector = null;
    AccCategoryToCostCenterView categToCCView = null;

    /**
     * Creates a new <code>ProductDAO</code> instance.
     *
     * @param pConn a <code>Connection</code> value
     * @param pItemId an <code>int</code> value
     */
    public ProductDAO(Connection pConn, int pItemId) {
        lookupItem(pConn, pItemId);
    }

    public void lookupItem(Connection pConn, int pItemId) {
        mItemIdVector = new IdVector();
        mItemIdVector.add(new Integer(pItemId));
        mPdv = constructProductData(pConn, mItemIdVector, null);
    }

    /**
     * Creates a new <code>ProductDAO</code> instance.
     *
     * @param pConn a <code>Connection</code> value
     * @param pItemIdVector an <code>IdVector</code> value
     */
    public ProductDAO(Connection pConn, IdVector pItemIdVector) {
        mItemIdVector = pItemIdVector;
        mPdv = constructProductData(pConn, mItemIdVector, null);
    }

    /**
     * Creates a new <code>ProductDAO</code> instance.
     *
     * @param pConn a <code>Connection</code> value
     * @param pItemIdVector an <code>IdVector</code> value
     * @param retrieveProductdata a boolean to indicate if product data should be retrieved.
     * If true then product data is retrieved, otherwise no product data is retrieved.
     */
    public ProductDAO(Connection pConn, IdVector pItemIdVector, boolean retrieveProductData) {
        mItemIdVector = pItemIdVector;
        if (retrieveProductData) {
        	mPdv = constructProductData(pConn, mItemIdVector, null);
        }
        else {
    		mPdv = new ProductDataVector();
        	if (Utility.isSet(pItemIdVector)) {
        		Iterator<Integer> itemIdIterator = pItemIdVector.iterator();
        		while (itemIdIterator.hasNext()) {
        			ItemData item = ItemData.createValue();
        			item.setItemId(itemIdIterator.next());
                	ProductData product = new ProductData();
                    product.setItemData(item);
                    mPdv.add(product);
        		}
        	}
        }
    }

    ItemDataVector mItemDataVector = null;

    public ProductDAO(Connection pConn, ItemDataVector pItemDataVector) {
        mItemDataVector = pItemDataVector;
        mPdv = constructProductData(pConn, null, pItemDataVector);
    }

    // Pick out the basic product information.
    private ProductDataVector constructProductData(Connection pCon,
            IdVector pItemIdVector,
            ItemDataVector pItemDataV
            ) {

        ProductDataVector pdv = new ProductDataVector();

        try {

            ItemDataVector itemDV = new ItemDataVector();

            if (pItemIdVector == null && pItemDataV == null ) {
                // No items to search.
                return pdv;
            }

            if (pItemIdVector == null) {

                mItemDataVector = pItemDataV;
                itemDV = mItemDataVector;
                pItemIdVector = new IdVector();

                for (int xi = 0; xi < itemDV.size(); xi++) {

                    Integer iid = new
                            Integer(((ItemData)itemDV.get(xi)).getItemId());
                    pItemIdVector.add(iid);
                }
                mItemIdVector = pItemIdVector;

            } else {
                mItemIdVector = pItemIdVector;
                DBCriteria dbC0 = new DBCriteria();
                dbC0.addOneOf(ItemDataAccess.ITEM_ID, pItemIdVector);
                dbC0.addOrderBy(ItemDataAccess.ITEM_ID);
                itemDV = ItemDataAccess.select(pCon, dbC0);
                mItemDataVector = itemDV;
            }

            ItemMetaDataVector itemMetaDV = new ItemMetaDataVector();
            DBCriteria dbC1 = new DBCriteria();
            dbC1.addOneOf(ItemMetaDataAccess.ITEM_ID, pItemIdVector);
            dbC1.addOrderBy(ItemMetaDataAccess.ITEM_ID);
            dbC1.addOrderBy(ItemMetaDataAccess.ITEM_META_ID);
            itemMetaDV = ItemMetaDataAccess.select(pCon, dbC1);


            DBCriteria dbc;
            dbc = new DBCriteria();
            dbc.addOneOf(ItemMappingDataAccess.ITEM_ID,  pItemIdVector);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);
            dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
            ItemMappingDataVector certificatedCompaniesDV = ItemMappingDataAccess.select(pCon, dbc);

            ItemMappingDataVector itemMapDV = new ItemMappingDataVector();
            DBCriteria dbC2 = new DBCriteria();
            dbC2.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                    RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            dbC2.addOneOf(ItemMappingDataAccess.ITEM_ID, pItemIdVector);
            dbC2.addOrderBy(ItemMappingDataAccess.ITEM_ID);
            itemMapDV = ItemMappingDataAccess.select(pCon, dbC2);

            // Add the mapping information.
            for (int ii3 = 0; ii3 < itemMapDV.size(); ii3++) {

                ItemMappingData imd = (ItemMappingData)itemMapDV.get(ii3);
                manufacturers.put(new Integer(imd.getBusEntityId()), null);
            }

            IdVector manIds = new IdVector();
            Iterator citr = manufacturers.keySet().iterator();

            while (citr.hasNext()) {
                manIds.add((Integer)citr.next());
            }

            try {

                BusEntityDataVector beVector =
                        BusEntityDataAccess.select(pCon, manIds);

                for (int bidx = 0; bidx < beVector.size(); bidx++) {

                    BusEntityData be = (BusEntityData)beVector.get(bidx);
                    manufacturers.put(new Integer(be.getBusEntityId()), be);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int ii2 = 0; ii2 < itemDV.size(); ii2++) {

                ProductData pd = new ProductData();
                // Add the basic item information.
                ItemData itemD = (ItemData)itemDV.get(ii2);
                pd.setItemData(itemD);

                for (int itemIdx = 0; itemIdx < certificatedCompaniesDV.size(); itemIdx++) {

                    ItemMappingData imd = (ItemMappingData) certificatedCompaniesDV.get(itemIdx);
                    if (itemD.getItemId() == imd.getItemId()) {
                        pd.addCertCompaniesMapping(imd);
                        BusEntityData certCompany = getCerifiedCompanyBE(pCon, imd.getBusEntityId());
                        pd.addMappedCertCompany(certCompany);
                    }
                }
                // Add the item Meta information.
                for (int imidx = 0; imidx < itemMetaDV.size(); imidx++) {

                    ItemMetaData itemMetaD =
                            (ItemMetaData)itemMetaDV.get(imidx);

                    if (itemMetaD.getItemId() ==
                            pd.getItemData().getItemId()) {
                        String attrname = itemMetaD.getNameValue();
                        pd.setItemMeta(itemMetaD, attrname);
                    }
                }

                for (int ii3 = 0; ii3 < itemMapDV.size(); ii3++) {

                    ItemMappingData imd = (ItemMappingData)itemMapDV.get(ii3);

                    if (imd.getItemId() == pd.getItemData().getItemId()) {

                        Integer tempid = new Integer(imd.getBusEntityId());
                        BusEntityData be = (BusEntityData)manufacturers.get(
                                tempid);
                        pd.setManuMapping(be, imd.getItemNum());

                        // Only one manufacturer.
                        break;
                    }
                }

                pdv.add(pd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdv;
    }

    HashMap manufacturers = new HashMap();
    HashMap distributors = new HashMap();
    HashMap certifiedCompany=new HashMap();
    HashMap costCenters = new HashMap();
    private BusEntityData mBE = null;

    BusEntityData getBE(Connection pCon, int pBeId) {
        if ( mBE != null && pBeId == mBE.getBusEntityId() ) {
            return mBE;
        }

        if ( pBeId <= 0 ) {
            mBE = BusEntityData.createValue();
            return mBE;
        }

        BusEntityData bed = null;
        try {
            bed = BusEntityDataAccess.select(pCon, pBeId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ( null == bed ) {
            bed = BusEntityData.createValue();
        }
        mBE = bed;
        return mBE;
    }

    BusEntityData getDistBE(Connection pCon, int pDistrId) {

        if ( pDistrId == 0 ) {
            return BusEntityData.createValue();
        }

        Integer tid = new Integer(pDistrId);
        BusEntityData distrD = null;

        if (!distributors.containsKey(tid)) {
            try {
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo
                        (BusEntityDataAccess.BUS_ENTITY_ID,
                        pDistrId
                        );
                dbc.addEqualTo
                        (BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR
                        );
                BusEntityDataVector v =
                        BusEntityDataAccess.select(pCon, dbc);
                Iterator it = v.iterator();
                while (it.hasNext()){
                    BusEntityData thisD = (BusEntityData)it.next();
                    distributors.put
                            (new Integer(thisD.getBusEntityId()),
                            thisD);
                }
            } catch (Exception e) {
                e.printStackTrace();
                distrD = BusEntityData.createValue();
            }

        }
        distrD = (BusEntityData)distributors.get(tid);
        if ( null == distrD ) {
            distrD = BusEntityData.createValue();
        }

        return distrD;
    }

    public BusEntityData getCerifiedCompanyBE(Connection pCon, int pCertifiedCompId) {

        if (pCertifiedCompId == 0) {
            return BusEntityData.createValue();
        }

        Integer tid = new Integer(pCertifiedCompId);
        BusEntityData pCertifiedCompD = null;

        if (!certifiedCompany.containsKey(tid)) {
            try {
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, pCertifiedCompId);
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
                BusEntityDataVector v = BusEntityDataAccess.select(pCon, dbc);
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    BusEntityData thisD = (BusEntityData) it.next();
                    certifiedCompany.put(new Integer(thisD.getBusEntityId()), thisD);
                }
            } catch (Exception e) {
                e.printStackTrace();
                pCertifiedCompD = BusEntityData.createValue();
            }
        }
        pCertifiedCompD = (BusEntityData) certifiedCompany.get(tid);
        if (null == pCertifiedCompD) {
            pCertifiedCompD = BusEntityData.createValue();
        }

        return pCertifiedCompD;
    }

    public static ItemMappingData getDistItemMappingForASite
            ( Connection pCon,
            int pSiteId,
            int pItemId ) throws Exception {
        String cond = "select 0 from dual union "
                + " select item_mapping_id from "
                + " clw_item_mapping where "
                + " item_id = " + pItemId
                + " and item_mapping_cd = '"
                + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR
                + "' and bus_entity_id in ( "
                + " select bus_entity_id from clw_catalog_structure "
                + " where catalog_id in ( "
                + "   select catalog_id from clw_catalog_assoc "
                + " where bus_entity_id = " + pSiteId
                + " ) ) and item_id = " + pItemId ;

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, cond);
        ItemMappingDataVector v =
                ItemMappingDataAccess.select(pCon, dbc);
        if ( v== null || v.size() == 0 ) {
            return null;
        }
        if ( v.size() > 1 ) {
            log.info(" getDistItemMappingForASite , "
                    + " pSiteId=" + pSiteId
                    + ", pItemId=" + pItemId + " , "
                    + " multiple distributor mappings defined "
                    + "for the site, item combination") ;
        }

        return (ItemMappingData)v.get(0);
    }
    
    public int setCostCenterOverride(Connection pCon, int acctId, int itemId, String siteRefNum, String facilityType){
    	int costCenterId = 0;
    	java.util.Date currDate = new java.util.Date();

    	try{
    		//get cost center rule for this item and acct    		
    		DBCriteria crit = new DBCriteria();
    		crit.addEqualTo(CostCenterRuleDataAccess.BUS_ENTITY_ID, acctId);
    		crit.addEqualTo(CostCenterRuleDataAccess.ITEM_ID, itemId);
    		crit.addLessOrEqual(CostCenterRuleDataAccess.EFF_DATE, currDate);
    		CostCenterRuleDataVector ccRuleDV = CostCenterRuleDataAccess.select(pCon,crit);

    		IdVector ccRuleDetailV = new IdVector();
    		if(ccRuleDV!=null && ccRuleDV.size()>0){
    			for(int i=0; i<ccRuleDV.size(); i++){
    				CostCenterRuleData ccRuleD = (CostCenterRuleData)ccRuleDV.get(i);
    				int ccRuleId = ccRuleD.getCostCenterRuleId();
    				crit = new DBCriteria();

    				if(Utility.isSet(siteRefNum)){
    					crit.addEqualTo(CostCenterRuleDetailDataAccess.COST_CENTER_RULE_ID, ccRuleId);
    					crit.addEqualTo(CostCenterRuleDetailDataAccess.PARAM_NAME, RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
    					crit.addEqualTo(CostCenterRuleDetailDataAccess.PARAM_VALUE, siteRefNum);
    					ccRuleDetailV = CostCenterRuleDetailDataAccess.selectIdOnly(pCon, crit);
    				}

    				if ((ccRuleDetailV==null || ccRuleDetailV.size()==0) && Utility.isSet(facilityType)){
    					crit = new DBCriteria();
    					crit.addEqualTo(CostCenterRuleDetailDataAccess.COST_CENTER_RULE_ID, ccRuleId);
    					crit.addEqualTo(CostCenterRuleDetailDataAccess.PARAM_NAME, RefCodeNames.PROPERTY_TYPE_CD.FACILITY_TYPE);
    					crit.addEqualTo(CostCenterRuleDetailDataAccess.PARAM_VALUE, facilityType);
    					ccRuleDetailV = CostCenterRuleDetailDataAccess.selectIdOnly(pCon, crit);
    				}

    				if(ccRuleDetailV!=null && ccRuleDetailV.size()>0){
            			costCenterId = ccRuleD.getCostCenterId();
            			return costCenterId;
    				}
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	return costCenterId;
    }

    public boolean updateCatalogInfo(Connection pCon, int pForCatalogId){
    	return updateCatalogInfo(pCon, pForCatalogId,0, null, false);
    }
    public boolean updateCatalogInfo(Connection pCon, int pForCatalogId, AccCategoryToCostCenterView pCategToCostCenterView){
        return updateCatalogInfo(pCon, pForCatalogId,0, pCategToCostCenterView, false);
    }
    public boolean updateCatalogInfo(Connection pCon, int pForCatalogId, int pSiteId) {
      return updateCatalogInfo(pCon, pForCatalogId,0, null, false);
    }
    public boolean updateCatalogInfo(Connection pCon, int pForCatalogId, int pSiteId, AccCategoryToCostCenterView pCategToCostCenterView) {
		return updateCatalogInfo(pCon, pForCatalogId, pSiteId, pCategToCostCenterView, false);
	}
    public boolean updateCatalogInfo(Connection pCon, int pForCatalogId, 
	    int pSiteId, AccCategoryToCostCenterView pCategToCostCenterView, boolean pAcceptCostCentersFl) {

    	log.info("updateCatalogInfo()=> BEGIN");
    	log.info("updateCatalogInfo()=> siteID: " + pSiteId + ", pForCatalogID: " + pForCatalogId);

    	if (pForCatalogId <= 0) {
    		log.info("updateCatalogInfo()=> pForCatalogId <= 0, retCd:false");
    		return false;
    	}

    	if ( mPdv.size() == 0 ) {
    		// No item ids were provided.
    		log.info("updateCatalogInfo()=> No item ids were provided, retCd:false");
    		return false;
    	}

		if(pAcceptCostCentersFl) { 
			categToCCView = pCategToCostCenterView;
		} else {
			updateCategoryToCostCenterView(pCon, pForCatalogId,  pSiteId, pCategToCostCenterView);
		}

    	Map categoryMap = new HashMap();
    	Map distributorMap = new HashMap();
    	Map categoryItemMap = new HashMap();
    	Map catalogStructMap = new HashMap();
    	Map distItemMappingMap  = new HashMap();

    	//build a map of item ids to items
    	Map<String, ProductData> itemIdToItem = new HashMap<String, ProductData>();
		for (int ii2 = 0; ii2 < mPdv.size(); ii2++) {
    		ProductData pd = (ProductData)mPdv.get(ii2);
			itemIdToItem.put(Integer.toString(pd.getItemData().getItemId()), pd);
		}

    	StringBuilder sql = new StringBuilder(200);
    	sql.append("SELECT cs.catalog_structure_id, cs.item_id itemId, im.bus_entity_id distId, ia.item2_id categoryId, im.item_mapping_id distMapping");
    	sql.append(" FROM clw_catalog_structure cs, clw_item_mapping im, clw_item_assoc ia");
    	sql.append(" WHERE cs.catalog_id = ");
    	sql.append(pForCatalogId);
    	sql.append(" and cs.catalog_id = ia.catalog_id");
    	sql.append(" AND (cs.item_id in ");
    	//handle the fact that an in clause can contain at most 1000 items.
    	List<String> itemIds = new ArrayList<String>();
    	itemIds.addAll(itemIdToItem.keySet());
        int itemIdCount = itemIds.size();
    	boolean includeOr = false;
	    for (int i=0; i<itemIdCount; i+=inClauseSize) {
	    	int endIndex = i+inClauseSize;
    		if (endIndex > itemIdCount) {
    			endIndex=itemIdCount;
    		}
    		if (includeOr) {
    			sql.append(" or cs.item_id in ");
    		}
    		sql.append("(");
    		sql.append(Utility.toCommaSting(itemIds.subList(i,endIndex)));
    		sql.append(")");
    		includeOr = true;
    	}
    	sql.append(") and cs.item_id = im.item_id(+)");
    	sql.append(" and cs.item_id = ia.item1_id");
    	sql.append(" and cs.bus_entity_id = im.bus_entity_id(+)");
    	
    	StringBuilder sql2 = new StringBuilder(200);
    	sql2.append("select ccs.item_id categoryId, ccsp.item_id parentCategoryId, ccs.sort_order from clw_item_assoc ia,");
		sql2.append(" (select item_id, sort_order from clw_catalog_structure where catalog_id = ");
		sql2.append(pForCatalogId);
		sql2.append(") ccs,");
		sql2.append(" (select item_id from clw_catalog_structure where catalog_id = ");
		sql2.append(pForCatalogId);
		sql2.append(") ccsp");
		sql2.append(" where ccs.item_id = ia.item1_id(+)");
		sql2.append(" and ia.item2_id = ccsp.item_id(+)");
		sql2.append(" and ia.ITEM_ASSOC_CD(+) = 'CATEGORY_PARENT_CATEGORY'");
		sql2.append(" and ccs.item_id = ?");

    	//STJ-4374 : COST CENTER's name should not be displayed 
    	//if there is NO association (or association is removed) between COST CENTER and ACCOUNT CATALOG.
    	List<Integer> configuredCCIds = null;
    	//Get All COST CENTER ids, if ACCOUNT CATALOG is available. 
		/*
    	if(pCategToCostCenterView!=null) {
    		configuredCCIds = Utility.getConfiguredCostCenterIds(pCon,pCategToCostCenterView.getAccCatalogId());
    	}
		*/
    	if(categToCCView!=null) {
    		configuredCCIds = Utility.getConfiguredCostCenterIds(pCon,categToCCView.getAccCatalogId());
    	}
		log.info("ProductDAO PPPPPPPP configuredCCIds: "+configuredCCIds);
    	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String siteRefNum = null;
		String facilityType = null;
		
		if(pSiteId > 0){
			PropertyUtil pru = new PropertyUtil(pCon);
			try {
				siteRefNum = pru.fetchValue(0, pSiteId, RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
			} catch (Exception e){}
			try {
				facilityType = pru.fetchValue(0, pSiteId, RefCodeNames.PROPERTY_TYPE_CD.FACILITY_TYPE);
			} catch (Exception e){}
		}
    	
    	try {
    		stmt = pCon.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
    		pstmt = pCon.prepareStatement(sql2.toString());
    		Map <Integer, Integer> catalogStrucIdMap = new HashMap<Integer, Integer>();
    		Map <Integer, Integer> distributorIdMap = new HashMap<Integer, Integer>();
    		Map <Integer, Integer> categoryIdMap = new HashMap<Integer, Integer>();
    		Map <Integer, Integer> distMappingIdMap = new HashMap<Integer, Integer>();
    		Map <Integer, Integer> costCenterIdMap = new HashMap<Integer, Integer>();
			while (rs.next()) {
				int itemId = rs.getInt("itemId");
    			catalogStrucIdMap.put(itemId, rs.getInt("catalog_structure_id"));
    			distributorIdMap.put(itemId, rs.getInt("distId"));
    			categoryIdMap.put(itemId, rs.getInt("categoryId"));
    			distMappingIdMap.put(itemId, rs.getInt("distMapping"));
			}
			
			List ids = new ArrayList(catalogStrucIdMap.values());
			DBCriteria dbc = new DBCriteria();
	        dbc.addOneOf(CatalogStructureDataAccess.CATALOG_STRUCTURE_ID, ids);
	        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(pCon, dbc);
	        for (Object o : csDV){
	        	CatalogStructureData csD = (CatalogStructureData) o;
	        	catalogStructMap.put(csD.getCatalogStructureId(), csD);
	        }
	        
	        ids = new ArrayList(distMappingIdMap.values());
			dbc = new DBCriteria();
	        dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, ids);
	        ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(pCon, dbc);
	        for (Object o : itemMappingDV){
	        	ItemMappingData itemMappingD = (ItemMappingData) o;
	        	distItemMappingMap.put(itemMappingD.getItemMappingId(), itemMappingD);
	        }
	        
	        ids = Utility.getUniqueValues(distributorIdMap.values());
			dbc = new DBCriteria();
	        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, ids);
	        BusEntityDataVector distDV = BusEntityDataAccess.select(pCon, dbc);
	        for (Object o : distDV){
	        	BusEntityData distD = (BusEntityData) o;
	        	distributorMap.put(distD.getBusEntityId(), distD);
	        }
	        
	        ids = Utility.getUniqueValues(categoryIdMap.values());
	        for (Iterator it = ids.iterator(); it.hasNext();){
	        	Integer categoryId = (Integer) it.next();
	        	CatalogCategoryDataVector catalogCategoryDV = (CatalogCategoryDataVector) categoryMap.get(categoryId);
				catalogCategoryDV = buildCatalogCategories(pCon, categoryId, pForCatalogId, categoryItemMap, pstmt);
				categoryMap.put(categoryId, catalogCategoryDV);
				int costcenterid = getCostCenterId( catalogCategoryDV);
				costCenterIdMap.put(categoryId, costcenterid);
	        }
	        
	        
	        for (int ii2 = 0; ii2 < mPdv.size(); ii2++) {
	    		ProductData pd = (ProductData)mPdv.get(ii2);
	    		int itemId = pd.getProductId();
	    		pd.setCatalogStructure((CatalogStructureData) catalogStructMap.get(catalogStrucIdMap.get(itemId)));
	    		pd.setCatalogDistrMapping((ItemMappingData)distItemMappingMap.get(distMappingIdMap.get(itemId)));
	    		pd.setCatalogDistributor((BusEntityData)distributorMap.get(distributorIdMap.get(itemId)));
	    		
	    		CatalogCategoryDataVector catalogCategoryDV = (CatalogCategoryDataVector)categoryMap.get(categoryIdMap.get(itemId));
	    		pd.setCatalogCategories(catalogCategoryDV);
				int costcenterid = 0;
				//log.info("ProductDAO PPPPPPPP costCenterIdMap: "+costCenterIdMap);
				//log.info("ProductDAO PPPPPPPP categoryIdMap: "+categoryIdMap);
				
				Integer costCenterIdI = costCenterIdMap.get(categoryIdMap.get(itemId));
				
				log.info("ProductDAO PPPPPPPP costCenterIdI: "+costCenterIdI);
				if(costCenterIdI!=null) {
					costcenterid = costCenterIdI.intValue();
				} else {
					String errorMess = "Not found costCenter for item: "+itemId+ 
					    " in catalog: "+pForCatalogId+" for site: "+pSiteId;
					log.info(errorMess);
				}
				
    			/*if(pSiteId > 0){
    				int cc_override_id = setCostCenterOverride(pCon,itemId,pCategToCostCenterView.getAccountId(), siteRefNum, facilityType);
    				if(cc_override_id!=0){
    					costcenterid = cc_override_id;
    					if(pd.getCatalogStructure() != null){
    						pd.getCatalogStructure().setCostCenterId(costcenterid);
    					}
    				}
    			}*/

			log.info("ProductDAO PPPPPPPP configuredCCIds.contains(costcenterid): "+configuredCCIds.contains(costcenterid));
    			if(pCategToCostCenterView==null || configuredCCIds.contains(costcenterid)){
    				pd.setCostCenterId(costcenterid);
    			}
			log.info("ProductDAO PPPPPPPP pd.getCostCenterId(): "+pd.getCostCenterId());

    			if (costcenterid > 0) {
    				CostCenterData ccd = null;
    				Integer tccid = new Integer(costcenterid);

    				if (costCenters.containsKey(tccid)) {
    					ccd = (CostCenterData)costCenters.get(tccid);
    				} else {
    					try {
    						ccd = CostCenterDataAccess.select(pCon, costcenterid);
    					} catch (Exception e) {
    						e.printStackTrace();
    						ccd = CostCenterData.createValue();
    					}
    					costCenters.put(tccid, ccd);
    				}
    				if(pCategToCostCenterView==null || configuredCCIds.contains(costcenterid)){
    					pd.setCostCenterName(ccd.getShortDesc());
    				}
    			}
			}			

    		setCPMData(pCon, pSiteId, pForCatalogId, 0, mPdv);
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    	}
    	finally {
    		try {
    			if (stmt != null) {
    				stmt.close();
    			}
    			if (pstmt != null) {
    				pstmt.close();
    			}
    			if (rs != null) {
    				rs.close();
    			}
    		}
    		catch (Exception e) {
    			//nothing to do here
    		}
    	}
    	return true;
    }

    public void populateCatalogInfo(Connection pCon, int pForCatalogId, int pSiteId) {

    	//if no catalog id or items were specified, there is nothing to do so return
    	if (pForCatalogId <= 0 || !Utility.isSet(mPdv)) {
    		return;
    	}

    	//build a map of item ids to items
    	Map<Integer, ProductData> productMap = Utility.toMap(mPdv);

    	StringBuilder sql = new StringBuilder(200);
    	sql.append("SELECT cs.catalog_structure_id, cs.item_id itemId, im.bus_entity_id distId, ia.item2_id categoryId, im.item_mapping_id distMapping");
    	sql.append(" FROM clw_catalog_structure cs, clw_item_mapping im, clw_item_assoc ia");
    	sql.append(" WHERE cs.catalog_id = ");
    	sql.append(pForCatalogId);
    	sql.append(" and cs.catalog_id = ia.catalog_id");
    	sql.append(" AND (cs.item_id in ");
    	//handle the fact that an in clause can contain at most 1000 items.
    	List<Integer> itemIds = new ArrayList<Integer>(productMap.keySet());
        int itemIdCount = itemIds.size();
    	boolean includeOr = false;
	    for (int i=0; i<itemIdCount; i+=inClauseSize) {
	    	int endIndex = i+inClauseSize;
    		if (endIndex > itemIdCount) {
    			endIndex=itemIdCount;
    		}
    		if (includeOr) {
    			sql.append(" or cs.item_id in ");
    		}
    		sql.append("(");
    		sql.append(Utility.toCommaSting(itemIds.subList(i,endIndex)));
    		sql.append(")");
    		includeOr = true;
    	}
    	sql.append(") and cs.item_id = im.item_id(+)");
    	sql.append(" and cs.item_id = ia.item1_id");
    	sql.append(" and cs.bus_entity_id = im.bus_entity_id(+)");
    	
    	StringBuilder sql2 = new StringBuilder(200);
    	sql2.append("select ccs.item_id categoryId, ccsp.item_id parentCategoryId, ccs.sort_order from clw_item_assoc ia,");
		sql2.append(" (select item_id, sort_order from clw_catalog_structure where catalog_id = ");
		sql2.append(pForCatalogId);
		sql2.append(") ccs,");
		sql2.append(" (select item_id from clw_catalog_structure where catalog_id = ");
		sql2.append(pForCatalogId);
		sql2.append(") ccsp");
		sql2.append(" where ccs.item_id = ia.item1_id(+)");
		sql2.append(" and ia.item2_id = ccsp.item_id(+)");
		sql2.append(" and ia.ITEM_ASSOC_CD(+) = 'CATEGORY_PARENT_CATEGORY'");
		sql2.append(" and ccs.item_id = ?");
    	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
    	
    	Map<Integer, CatalogCategoryDataVector> categoryMap = new HashMap<Integer, CatalogCategoryDataVector>();
    	Map<Integer, ItemData> categoryItemMap = new HashMap<Integer, ItemData>();
		Map<Integer, Integer> catalogStructureIdToProductIdMap = new HashMap<Integer, Integer>();
    	try {
    		stmt = pCon.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
    		pstmt = pCon.prepareStatement(sql2.toString());
			while (rs.next()) {
				int itemId = rs.getInt("itemId");
    			int catalogStructureId = rs.getInt("catalog_structure_id");
    			int categoryId = rs.getInt("categoryId");
        		ProductData pd = productMap.get(itemId);
    			CatalogCategoryDataVector catalogCategoryDV = (CatalogCategoryDataVector) categoryMap.get(categoryId);
    			if (catalogCategoryDV == null){
    				catalogCategoryDV = buildCatalogCategories(pCon, categoryId, pForCatalogId, categoryItemMap, pstmt);
    				categoryMap.put(categoryId, catalogCategoryDV);
    			}
    			pd.setCatalogCategories(catalogCategoryDV);
    			catalogStructureIdToProductIdMap.put(catalogStructureId, itemId);
			}
			
			//retrieve the catalog structure information and populate the product datas with that information
			CatalogStructureDataVector catalogStructures = new CatalogStructureDataVector();
			List<Integer> catalogStructureIds = new ArrayList(catalogStructureIdToProductIdMap.keySet());
	        int catalogStructureIdCount = catalogStructureIds.size();
		    for (int i=0; i<catalogStructureIdCount; i+=inClauseSize) {
		    	int endIndex = i+inClauseSize;
	    		if (endIndex > catalogStructureIdCount) {
	    			endIndex = catalogStructureIdCount;
	    		}
	    		IdVector catalogStructureIdVector = new IdVector();
	    		catalogStructureIdVector.addAll(catalogStructureIds.subList(i,endIndex));
	    		catalogStructures.addAll(CatalogStructureDataAccess.select(pCon, catalogStructureIdVector));
		    }
			Iterator<CatalogStructureData> catalogStructureIterator = catalogStructures.iterator();
			while (catalogStructureIterator.hasNext()) {
				CatalogStructureData catalogStructure = catalogStructureIterator.next();
				int structureId = catalogStructure.getCatalogStructureId();
				int productId = catalogStructureIdToProductIdMap.get(structureId);
				ProductData pd = productMap.get(productId);
    			pd.setCatalogStructure(catalogStructure);
			}
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    	}
    	finally {
    		try {
    			if (stmt != null) {
    				stmt.close();
    			}
    			if (pstmt != null) {
    				pstmt.close();
    			}
    			if (rs != null) {
    				rs.close();
    			}
    		}
    		catch (Exception e) {
    			//nothing to do here
    		}
    	}
    }

    private CatalogCategoryDataVector buildCatalogCategories(Connection pCon, int pCategoryId, int pCatalogId, Map categoryItemMap, PreparedStatement pstmt) throws Exception {
    	CatalogCategoryDataVector catalogCategoryDV = new CatalogCategoryDataVector();

    	while (pCategoryId > 0){
    		pstmt.setInt(1, pCategoryId);
    		ResultSet rs = pstmt.executeQuery();

    		if (rs.next()){
    			CatalogCategoryData catalogCategory = new CatalogCategoryData();
    			catalogCategory.setSortOrder(rs.getInt("sort_order"));

    			ItemData category = (ItemData) categoryItemMap.get(pCategoryId);
    			if (category == null){
    				category = ItemDataAccess.select(pCon, pCategoryId);
    				categoryItemMap.put(pCategoryId, category);
    			}
    			catalogCategory.setItemData(category);

    			int parentCategoryId = rs.getInt("parentCategoryId");
    			if (parentCategoryId > 0){
	    			ItemData parentCategory = (ItemData) categoryItemMap.get(parentCategoryId);
	    			if (parentCategory == null){
	    				parentCategory = ItemDataAccess.select(pCon, parentCategoryId);
	    				catalogCategory.setParentCategory(parentCategory);
	    				categoryItemMap.put(parentCategoryId, parentCategory);
	    			}
    			}
    			catalogCategoryDV.add(catalogCategory);
                if (rs.next()){
                	throw new Exception("Multiple Catalog Structure Data." +
                            " CatalogID: " + pCatalogId +
                            ", CategoryId: " + pCategoryId);
                }else{
                	pCategoryId = parentCategoryId;
                }
    		}else{
    			break;
    		}
    	}

        return catalogCategoryDV;
    }

    public boolean updateProductDistributors(Connection pCon) {
        return updateProductDistributors(pCon, 0 );
    }

    public boolean updateProductDistributors(Connection pCon, int pItemId) {
        return updateProductDistributors(pCon, pItemId, 0 );
    }
    public boolean updateProductDistributors(Connection pCon, int pItemId,
            int pDistId ) {
        //Get Mapped Distributors
        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, mItemIdVector);
        dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
        if ( pItemId > 0 ) {
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID,pItemId);
        }
        if ( pDistId > 0 ) {
            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID,pDistId);
        }

        ItemMappingDataVector itemMappingDV = null;
        try {
            itemMappingDV = ItemMappingDataAccess.select(pCon, dbc);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (itemMappingDV.size() <= 0) {
            log.info("updateProductDistributors, mapping data missing for "
                    + " mItemIdVector="
                    + IdVector.toCommaString(mItemIdVector)
                    );
            return false;
        }


        for (int ii2 = 0; ii2 < mPdv.size(); ii2++) {

            ProductData pd = (ProductData)mPdv.get(ii2);
            int itemid = pd.getItemData().getItemId();
            boolean foundDistInfo = false;
            for ( int itemIdx = 0; itemIdx < itemMappingDV.size();
            itemIdx++ ) {

                ItemMappingData imd = (ItemMappingData)
                itemMappingDV.get(itemIdx);

                if (itemid == imd.getItemId()) {
                    pd.addDistributorMapping(imd);
                    pd.addMappedDistributor
                            (getDistBE(pCon, imd.getBusEntityId()));
                    foundDistInfo = true;
                }
            }

            if ( foundDistInfo == false) {
                log.info("updateProductDistributors, " +
                        " mapping data missing for itemid=" +
                        itemid );
            }

        }

        return true;
    }

    /**
     * <code>getResultVector</code> return the product
     * information constructed for the item ids procided.
     *
     * @return a <code>ProductDataVector</code> value
     */
    public ProductDataVector getResultVector() {

        if (null == mPdv) {

            return new ProductDataVector();
        }

        return mPdv;
    }

    public boolean updateCertifiedCompanies(Connection pCon, int pItemId, int pCertifiedCompanyId) {

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, mItemIdVector);
        dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);
        if (pItemId > 0) {
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, pItemId);
        }
        if (pCertifiedCompanyId > 0) {
            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pCertifiedCompanyId);
        }
        ItemMappingDataVector itemMappingDV = null;
        try {
            itemMappingDV = ItemMappingDataAccess.select(pCon, dbc);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (itemMappingDV.size() <= 0) {
            log.info("updateCertifiedCompanies, mapping data missing for "
                    + " mItemIdVector="
                    + IdVector.toCommaString(mItemIdVector)
            );
            return false;
        }
        for (int ii2 = 0; ii2 < mPdv.size(); ii2++) {

            ProductData pd = (ProductData) mPdv.get(ii2);
            int itemid = pd.getItemData().getItemId();
            boolean foundCertCompInfo = false;
            for (int itemIdx = 0; itemIdx < itemMappingDV.size();
                 itemIdx++) {

                ItemMappingData imd = (ItemMappingData) itemMappingDV.get(itemIdx);
                if (itemid == imd.getItemId()) {
                    pd.addCertCompaniesMapping(imd);
                    BusEntityData certCompany = getCerifiedCompanyBE(pCon, imd.getBusEntityId());
                    pd.addMappedCertCompany(certCompany);
                    foundCertCompInfo = true;
                }
            }
            if (foundCertCompInfo == false) {
                log.info("updateCertifiedCompanies, " +
                        " mapping data missing for itemid=" +
                        itemid);
            }
        }

        return true;
    }

    public static CacheKey getCacheKey(int catalogId, int productId) {
        PairView catalogPartKey = new PairView(CatalogDataAccess.CATALOG_ID, String.valueOf(catalogId));
        PairView productPartKey = new PairView(ItemDataAccess.ITEM_ID, String.valueOf(productId));
        PairViewVector keyParts = new PairViewVector();
        keyParts.add(catalogPartKey);
        keyParts.add(productPartKey);
        return new CacheKey(ProductData.CACHE_OBJECT_NAME, keyParts);
    }

    public static CacheKey getCacheKey(int pId) {
        PairView productPartKey = new PairView(ItemDataAccess.ITEM_ID, String.valueOf(pId));
        PairViewVector keyParts = new PairViewVector();
        keyParts.add(productPartKey);
        return new CacheKey(ProductData.CACHE_OBJECT_NAME, keyParts);
    }

    public static CacheKey getCacheKey(ProductData pData) {
        if (pData.getCatalogStructure() != null && pData.getCatalogStructure().getCatalogId() > 0) {
            return getCacheKey(pData.getCatalogStructure().getCatalogId(), pData.getItemData().getItemId());
        } else {
            return getCacheKey(pData.getItemData().getItemId());
        }
    }

    public class DistItemInfo {
        public String mUOM, mPack, mSku;
        public java.math.BigDecimal mDistCost;
        public int mItemId, mContractId;
        public boolean mOnStdProductList;
        public DistItemInfo() {	}

        public String toString() {
            return "DistItemInfo { " +
                    "mItemId=" + mItemId +
                    ", mUOM=" + mUOM +
                    ", mPack=" + mPack +
                    ", mSku=" + mSku +
                    ", mDistCost= " + mDistCost +
                    ", mContractId= " + mContractId +
                    " } ";
        }

        private String mErrorMsg = "";
        public String getErrorMsg() {
            isInfoComplete();
            return mErrorMsg;
        }

        public boolean isInfoComplete() {

            mErrorMsg = "";
            if ( 0 == mItemId ) {
                mErrorMsg += " Missing item id.";
                return false;
            }

            if ( null == mUOM || mUOM.length() == 0 ) {
                mErrorMsg += " Missing UOM.";
                return false;
            }

            if ( null == mPack || mPack.length() == 0 ) {
                mErrorMsg += " Missing Pack.";
                return false;
            }

            if ( null == mDistCost ) {
                mErrorMsg += " Missing DistCost.";
                return false;
            }

            if ( 0 == mContractId ) {
                mErrorMsg += " Missing contract id.";
                return false;
            }

            return true;
        }
    }

    public ProductDAO() {}

    public static DistItemInfo getDistInfo( Connection pCon,
            int pItemId,
            int pDistId,
            int pContractId ) {
        ProductDAO pd = new ProductDAO();
        return pd.getDistInfo1(pCon, pItemId,pDistId,pContractId);
    }


    public DistItemInfo getDistInfo1( Connection pCon,
            int pItemId,
            int pDistId,
            int pContractId ) {

        Statement stmt = null;
        try {
            DistItemInfo distInfo = new DistItemInfo();
            distInfo.mItemId = pItemId;
            distInfo.mContractId = pContractId;
            stmt = pCon.createStatement();
            String sql = " select im.item_uom, im.item_pack, " +
                    " im.item_num, im.standard_product_list " +
                    " from clw_item_mapping im " +
                    "    where im.item_id = " + pItemId +
                    "    and im.bus_entity_id = " + pDistId;

            log.debug("1 distInfo SQL: " + sql );
            ResultSet rs = stmt.executeQuery(sql);
            if ( rs.next() ) {
                distInfo.mUOM = rs.getString(1);
                distInfo.mPack = rs.getString(2);
                distInfo.mSku = rs.getString(3);
                distInfo.mOnStdProductList = Utility.isTrue(rs.getString(4));
            }

            sql = " select ci.dist_cost " +
                    " from clw_contract_item ci , " +
                    " clw_contract c, clw_catalog_structure cs " +
                    " where ci.contract_id = " +  pContractId +
                    " and ci.contract_id = c.contract_id " +
                    " and ci.item_id = " + pItemId +
                    " and c.catalog_id = cs.catalog_id " +
                    " and cs.bus_entity_id = " + pDistId +
                    " and cs.item_id = ci.item_id ";

            log.debug("2 distInfo SQL: " + sql );

            rs = stmt.executeQuery(sql);
            if ( rs.next() ) {
                distInfo.mDistCost = rs.getBigDecimal(1);
            }


            log.debug("distInfo is: " + distInfo );
            return distInfo;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {if ( null != stmt ) {stmt.close();}
            } catch (Exception e) {}
        }

        return null;
    }

    public static ItemMetaData saveItemMetaInfo(Connection pCon, int pItemId,
            String user, ItemMetaData pItemMetaD )
            throws Exception {

        try {
            pItemMetaD.setItemId(pItemId);
            pItemMetaD.setAddBy(user);
            pItemMetaD.setModBy(user);
            pItemMetaD = ItemMetaDataAccess.insert(pCon,pItemMetaD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(" ProductDAO.saveItemMetaInfo, error 2005.8.15, " + e.getMessage());
        }

        log.debug(" ProductDAO.saveItemMetaInfo, pItemMetaD=" + pItemMetaD);

        return pItemMetaD;
    }

    public IdVector allCatalogItemIds(Connection con, int pCatId) throws Exception {

        String catalogStructReq = " select distinct item_id " +
                " from clw_catalog_structure where catalog_id = " + pCatId +
                " and " + CatalogStructureDataAccess.CATALOG_STRUCTURE_CD +
                " = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "' ";

        DBCriteria dbcMain = new DBCriteria();
        dbcMain.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
        dbcMain.addOneOf(ItemDataAccess.ITEM_ID, catalogStructReq);

        return ItemDataAccess.selectIdOnly(con, dbcMain);

    }

    public static void setCPMData(Connection pCon,
                                                int pSiteId,
                                                int pCatalogId,
                                                int pContractId,
                                                ProductDataVector pProductList) throws Exception {

        log.info("setCPMData()=> BEGIN");

        String productBundle = ShoppingDAO.getProductBundleValue(pCon, pSiteId);
        if (Utility.isSet(productBundle)) {

            log.info("setCPMData()=> product bundle: " + productBundle);

            Map<Integer, ContractItemData> contractsItemsMap = new HashMap<Integer, ContractItemData>();
            Map<Integer, PriceListDetailData> priceListRank1ItemsMap = new HashMap<Integer, PriceListDetailData>();
            Map<Integer, PriceListDetailData> priceListRank2ItemsMap = new HashMap<Integer, PriceListDetailData>();

            if (pCatalogId != 0) {

                ContractItemDataVector contractItems = new ContractItemDataVector();
                DBCriteria dbc = null;

                // handle in stament size could not exceed 1000
                int size = pProductList.size();
        	    for(int i=0; i<size; i+=inClauseSize) {
	        		int endIndex = i+inClauseSize;
	        		if(endIndex > size) endIndex=size;
	        		dbc = new DBCriteria();

	        		if (pContractId <= 0) {
	                    dbc.addJoinTableEqualTo(ContractDataAccess.CLW_CONTRACT, ContractDataAccess.CATALOG_ID, pCatalogId);
	                    dbc.addJoinTableEqualTo(ContractDataAccess.CLW_CONTRACT, ContractDataAccess.CONTRACT_STATUS_CD, RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
	                    dbc.addJoinCondition(ContractItemDataAccess.CONTRACT_ID, ContractDataAccess.CLW_CONTRACT, ContractDataAccess.CONTRACT_ID);
	                    dbc.addOneOf(ContractItemDataAccess.ITEM_ID, Utility.toIdVector(pProductList.subList(i,endIndex)));
	                    dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
	                } else {
	                    dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
	                    dbc.addOneOf(ContractItemDataAccess.ITEM_ID, Utility.toIdVector(pProductList.subList(i,endIndex)));
	                    dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
	                }

	        		contractItems.addAll(ContractItemDataAccess.select(pCon, dbc));
        	    }

                if (null != contractItems) {
                    for (Object contractItem : contractItems) {
                        ContractItemData cid = (ContractItemData) contractItem;
                        contractsItemsMap.put(cid.getItemId(), cid);
                    }
                }

            }

          {
                PriceListData priceListRank1 = PriceListDAO.getActivePriceList(pCon, pSiteId, 1);
                PriceListData priceListRank2 = PriceListDAO.getActivePriceList(pCon, pSiteId, 2);

                if (priceListRank1 != null) {
                    PriceListDetailDataVector priceListRank1Items = PriceListDAO.getPriceListItems(pCon, priceListRank1.getPriceListId());
                    for (Object oPriceListRank1Item : priceListRank1Items) {
                        PriceListDetailData priceListItem = (PriceListDetailData) oPriceListRank1Item;
                        priceListRank1ItemsMap.put(priceListItem.getItemId(), priceListItem);
                    }
                }

                if (priceListRank2 != null) {
                    PriceListDetailDataVector priceListRank2Items = PriceListDAO.getPriceListItems(pCon, priceListRank2.getPriceListId());
                    for (Object oPriceListRank2Item : priceListRank2Items) {
                        PriceListDetailData priceListItem = (PriceListDetailData) oPriceListRank2Item;
                        priceListRank2ItemsMap.put(priceListItem.getItemId(), priceListItem);
                    }
                }
            }

            for (Object oProduct : pProductList) {

                ProductData product = (ProductData) oProduct;

                ProductPriceView priceView = new ProductPriceView();
                ProductSkuView skuView = new ProductSkuView();

                ContractItemData contractItem = contractsItemsMap.get(product.getProductId());
                PriceListDetailData priceListRank1Item = priceListRank1ItemsMap.get(product.getProductId());
                PriceListDetailData priceListRank2Item = priceListRank2ItemsMap.get(product.getProductId());

                /* fill price view*/
                if (contractItem != null) {
                    priceView.setContractPrice(Utility.bdNN(contractItem.getAmount()));
                    priceView.setDiscount(Utility.bdNN(contractItem.getDiscountAmount()));
                    priceView.setDistCost(Utility.bdNN(contractItem.getDistCost()));
                    priceView.setDistBaseCost(Utility.bdNN(contractItem.getDistBaseCost()));
                }

                if (priceListRank1Item != null) {
                    priceView.setPriceList1(priceListRank1Item.getPrice());
                }

                if (priceListRank2Item != null) {
                    priceView.setPriceList2(priceListRank2Item.getPrice());
                }

                priceView.setListPrice(new BigDecimal(product.getListPrice()));

                /*fill sku view*/

                skuView.setCustomerSkuNum(product.getCustomerSkuNum());
                skuView.setManufacturerSkuNum(product.getManufacturerSku());
                skuView.setStoreSkuNum(String.valueOf(product.getSkuNum()));
                if (product.getCatalogDistrMapping() != null) {
                    skuView.setDistributorSkuNum(product.getCatalogDistrMapping().getItemNum());
                }
                if (priceListRank1Item != null) {
                    skuView.setPriceListRank1SkuNum(priceListRank1Item.getCustomerSkuNum());
                }
                if (priceListRank2Item != null) {
                    skuView.setPriceListRank2SkuNum(priceListRank2Item.getCustomerSkuNum());
                }

                product.setProductPrice(priceView);
                product.setProductSku(skuView);
                product.setIsContractItem(contractItem != null);

            }

        } else {
            log.info("setCPMData()=> product bundle is not set.");
        }

        log.info("setCPMData()=> END.");

    }

    public static boolean isCPMDataMissing(ProductDataVector pProducts) {
        if (pProducts != null) {
            for (Object oProduct : pProducts) {
                if (((ProductData) oProduct).getProductPrice() == null
                        || ((ProductData) oProduct).getProductSku() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static DistItemInfo getDistInfo(Connection pCon, String pStoreType, int pSiteId, int pContractId, ProductData pProductData) {
        ProductDAO productDAO = new ProductDAO();
        return productDAO._getDistInfo(pCon,
                pStoreType,
                pSiteId,
                pContractId,
                pProductData);
    }

    public DistItemInfo _getDistInfo(Connection pCon, String pStoreType, int pSiteId, int pContrzctId, ProductData pProductData) {

        try {
            log.info("getDistInfo()=> BEGIN");

            DistItemInfo distInfo = new DistItemInfo();

            distInfo.mItemId = pProductData.getItemData().getItemId();
            distInfo.mContractId = pContrzctId;

            ItemMappingData catalogDistrMapping = pProductData.getCatalogDistrMapping();
            if (catalogDistrMapping != null) {
                distInfo.mUOM = catalogDistrMapping.getItemUom();
                distInfo.mPack = catalogDistrMapping.getItemPack();
                distInfo.mSku = catalogDistrMapping.getItemNum();
                distInfo.mOnStdProductList = Utility.isTrue(catalogDistrMapping.getStandardProductList());
            }

            ProductDataVector products = new ProductDataVector();
            products.add(pProductData);

            ProductBundle productBundle = ShoppingDAO.getProductBundle(pCon, pStoreType, pSiteId, products);

            log.info("getDistInfo()=> productBundle: " + productBundle);
            PriceValue priceValue = productBundle.getPriceValue(pProductData.getItemData().getItemId());

            log.info("getDistInfo()=> priceValue is: " + priceValue);
            distInfo.mDistCost = priceValue.getValue();

            log.info("getDistInfo()=> distInfo is: " + distInfo);

            log.info("getDistInfo()=> ENB.");

            return distInfo;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }

public void updateCategoryToCostCenterView(Connection pCon, int pSiteCatalogId,  int pSiteId, AccCategoryToCostCenterView pCategToCCView) {
   log.info("updateCategoryToCostCenterView() ==> BEGIN.");
   if (pCategToCCView == null) {
          log.info("updateCategoryToCostCenterView => pCategToCCView is null");
   }
   try {
     Account accEjb = APIAccess.getAPIAccess().getAccountAPI();
     categToCCView = accEjb.refreshCategoryToCostCenterView(pCategToCCView, pSiteId, pSiteCatalogId);
   } catch (Exception e) {
     e.printStackTrace();
   }
   log.info("updateCategoryToCostCenterView() ==> END.");
  //log.info("updateCategoryToCostCenterView() ==> END. categToCostCenterMap =" + ((categToCCView!= null) ? categToCCView.getCategoryToCostCenterMap().toString() : "NULL"));
  return ;
}

public int getCostCenterId( CatalogCategoryDataVector pCatalogCategoryDV) {
    int costCenterId = 0;
    for (int i = 0; i < pCatalogCategoryDV.size(); i++) {
        CatalogCategoryData catalogCategoryD = (CatalogCategoryData)pCatalogCategoryDV.get(i);
        int categoryId = catalogCategoryD.getCatalogCategoryId();
        costCenterId = getCostCenterId( categoryId );
        if (costCenterId > 0){
         break;
       }
    }
   return costCenterId ;
}

 public int getCostCenterId(int pCategoryId) {
   Integer costCenterId = null;
   if (categToCCView != null) {
     HashMap map = categToCCView.getCategoryToCostCenterMap();
     if (map != null) {
       if (pCategoryId > 0) {
         costCenterId = (Integer) map.get(new Integer(pCategoryId));
       }
     }
   }
   return (costCenterId != null) ? costCenterId.intValue() : 0;
 }
 public CatalogStructureDataVector getParCatalogStructure(Connection pCon, int pCategoryId, int pCatalogId) throws Exception {

	 DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
     dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

     dbc.addJoinCondition(CatalogStructureDataAccess.ITEM_ID, ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM2_ID);

     dbc.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM1_ID, pCategoryId);
     dbc.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

     CatalogStructureDataVector catalogStructures = CatalogStructureDataAccess.select(pCon, dbc);

    

     return catalogStructures;
 }


}

