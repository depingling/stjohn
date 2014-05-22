/*
 * ContractProfitReport.java
 *
 * Created on February 3, 2003, 9:47 PM
 */

package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.dao.ContractItemDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.ContractItemProfitView;
import com.cleanwise.service.api.value.ContractItemProfitViewVector;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;

/**
 * Picks up all contract items and adds year to date trade infrmation
 * @param the contract identifier
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class ContractProfitReport implements GenericReport {
	
	/** Creates a new instance of ContractProfitReport */
	public ContractProfitReport() {
	}

	/** Should return a populated GenericReportResultView object.  At a minimum the header should
	 * be set so an empty report may be generated to the user.
	 *
	 */

	private class CWReport {

		java.util.HashMap mItemsMap = new java.util.HashMap(100);

		private String mFilterDistErpNum = "";

		private int mFilterManuId = 0;

		public void setDistributorFilter(String pDistErpNum) {
			mFilterDistErpNum = pDistErpNum;
		}

		public void setManufacturerFilter(int manuIdReq) {
			mFilterManuId = manuIdReq;
		}

		public void addItem(ContractItemProfitView pItem) {

			Integer k = new Integer(pItem.getItemId());
			if (!mItemsMap.containsKey(k)) {
				mItemsMap.put(k, pItem);
			}
		}

		private ContractItemProfitView getMyItem(Integer k) {
			ContractItemProfitView v = (ContractItemProfitView) mItemsMap
					.get(k);
			return v;
		}

		private ContractItemProfitView getMyItemBySku(int pSkuNum) {
			Iterator it = mItemsMap.values().iterator();
			while (it.hasNext()) {
				ContractItemProfitView t = (ContractItemProfitView) it.next();
				if (t.getItemSku() == pSkuNum) {
					return t;
				}
			}
			return null;
		}

		public Collection toCollection() {
			return mItemsMap.values();
		}

		public ContractItemProfitViewVector toVector() {
			Iterator it = toCollection().iterator();
			ContractItemProfitViewVector v = new ContractItemProfitViewVector();
			while (it.hasNext()) {
				v.add((ContractItemProfitView) it.next());
			}
			return v;
		}

		public void setItemInfo1(ItemData pItemData) {
			ContractItemProfitView itemProf = getMyItem(new Integer(pItemData
					.getItemId()));
			if (null != itemProf) {
				itemProf.setItemDesc(pItemData.getShortDesc());
				itemProf.setItemSku(pItemData.getSkuNum());
			}
		}

		public void setMfgInfo(int pItemId, int pMfgId, String pMfgName,
				String pMfgSku) {
			ContractItemProfitView itemProf = getMyItem(new Integer(pItemId));
			if (null != itemProf) {
				itemProf.setItemMfgId(pMfgId);
				itemProf.setItemMfgName(pMfgName);
				itemProf.setItemMfgSku(pMfgSku);
			}
		}

		public void setDistInfo(int pItemId, int distId, String distName,
				String distErp, String distSku, String distUom,
				String distPack, String mfg1Sku, int mfg1Id, String mfg1Name,
				String spl, Date itemAddedDate) {

			ContractItemProfitView itemProf = getMyItem(new Integer(pItemId));
			if (null != itemProf) {
				itemProf.setDistId(distId);
				itemProf.setDistName(distName);
				itemProf.setDistErp(distErp);
				itemProf.setDistSku(distSku);
				itemProf.setDistUom(distUom);
				itemProf.setDistPack(distPack);
				itemProf.setItemMfg1Sku(mfg1Sku);
				itemProf.setItemMfg1Id(mfg1Id);
				itemProf.setItemMfg1Name(mfg1Name);
				itemProf.setSPL(Utility.isTrue(spl));
				itemProf.setItemAddedDate(itemAddedDate);
			}
		}

		public void setMetaInfo(int pItemId, String name, String value) {
			ContractItemProfitView itemProf = getMyItem(new Integer(pItemId));

			if (null != itemProf) {

				if ("UOM".equals(name)) {
					itemProf.setItemUom(value);
				} else if ("PACK".equals(name)) {
					itemProf.setItemPack(value);
				} else if ("SIZE".equals(name)) {
					itemProf.setItemSize(value);
				} else if ("COLOR".equals(name)) {
					itemProf.setItemColor(value);
				} else if ("LIST_PRICE".equals(name)) {
					BigDecimal listPrice = null;
					try {
						listPrice = new BigDecimal(value);
					} catch (Exception exc) {
						listPrice = new BigDecimal(-99999999);
					}
					itemProf.setListPrice(listPrice);
				} else if ("COST_PRICE".equals(name)) {
					BigDecimal cPrice = null;
					try {
						cPrice = new BigDecimal(value);
					} catch (Exception exc) {
						cPrice = new BigDecimal(-99999999);
					}
					itemProf.setCostPriceMFCP(cPrice);
				}
			}

		}

		public void setProfitInfo(int pItemId, String distErp,
				Date lastOrderDate, int qty, BigDecimal sumCost) {
			ContractItemProfitView itemProf = getMyItem(new Integer(pItemId));

			if (null == itemProf)
				return;
			/*			
			 if ( mFilterDistErpNum != null &&  
			 mFilterDistErpNum.length() > 0 &&
			 (! mFilterDistErpNum.equals(distErp)) ) {
			 return;
			 }
			 
			 if ( mFilterManuId > 0 && mFilterManuId != itemProf.getItemMfgId() ) {
			 return;
			 }
			 */
			Date lastOrderDate1 = itemProf.getLastOrderDate();
			if (lastOrderDate1 == null || lastOrderDate1.before(lastOrderDate)) {
				itemProf.setLastOrderDate(lastOrderDate);
			}

			int qtySum = qty + itemProf.getYtdQty();
			itemProf.setYtdQty(qtySum);

			int ytdqM = itemProf.getYtdQtyMainDist();
			ytdqM += qty;
			itemProf.setYtdQtyMainDist(ytdqM);
			BigDecimal ytdcM = itemProf.getYtdCostMainDist();
			if (null == sumCost)
				sumCost = new BigDecimal(0);
			if (ytdcM == null) {
				ytdcM = sumCost;
			} else {
				ytdcM = ytdcM.add(sumCost);
			}
			itemProf.setYtdCostMainDist(ytdcM);
			BigDecimal avgCost = ytdcM.divide(new BigDecimal(ytdqM), 2,
					BigDecimal.ROUND_HALF_UP);
			itemProf.setAvgCost(avgCost);
		}
	}

	public GenericReportResultView process(ConnectionContainer pCons,
			GenericReportData pReportData, Map pParams) throws Exception {
		Connection con = pCons.getDefaultConnection();
		String errorMess = "No error";
		ContractItemProfitViewVector items = new ContractItemProfitViewVector();
		GenericReportResultView result = GenericReportResultView.createValue();
		String itemIdS = (String) pParams.get("ITEM_OPT");
		String catIdS = (String) pParams.get("CATALOG");
		String distIdS = (String)ReportingUtils.getParam(pParams, "DISTRIBUTOR");
		String manuIdS = (String)ReportingUtils.getParam(pParams, "MANUFACTURER");
		int catalogId = 0;
		int pItemId = 0;
		if (Utility.isSet(catIdS)) {
			try {
				catalogId = Integer.parseInt(catIdS);
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"trouble parsing CONTRACT out of params: "
								+ e.getMessage());
			}
		} else {
			throw new RemoteException(
					"CONTACT param was not supplied, or was not set");
		}
		int distIdReq = 0;
		if (Utility.isSet(distIdS)) {
			try {
				distIdReq = Integer.parseInt(distIdS);

			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"trouble parsing DISTRIBUTOR out of params: "
								+ e.getMessage());
			}
		}
		int manuIdReq = 0;
		if (Utility.isSet(manuIdS)) {
			try {
				manuIdReq = Integer.parseInt(manuIdS);
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"trouble parsing MANUFACTURER out of params: "
								+ e.getMessage());
			}
		}

		if (Utility.isSet(itemIdS)) {
			try {
				pItemId = Integer.parseInt(itemIdS);
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
						"trouble parsing ITEM out of params: " + e.getMessage());
			}
		}

		try {
			DBCriteria dbc;

			String distErpBase = "";
			// distributor ERP num
			if (distIdReq > 0) {
				dbc = new DBCriteria();
				BusEntityData distbe = BusEntityDataAccess.select(con,
						distIdReq);
				distErpBase = distbe.getErpNum();
			}
			//contract
			dbc = new DBCriteria();
			List l = new ArrayList();
			//get pContract id
            dbc.addEqualTo(ContractDataAccess.CATALOG_ID,catalogId);
            l.add(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            //dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            dbc.addOneOf(ContractDataAccess.CONTRACT_STATUS_CD,l);
            ContractDataVector pContractDV = ContractDataAccess.select(con,dbc);
            int pContractId=0;
            if(pContractDV.size()>1) {
            	String mess = "More than one Active pContract for catalog. Catalog Id: "+catalogId;
                throw new RemoteException(mess);
            }
            ContractData pContractD = null;
            String contractName = null;
            
            l.add(RefCodeNames.CONTRACT_STATUS_CD.LIVE);
            dbc.addOneOf(ContractDataAccess.CONTRACT_STATUS_CD,l);
            pContractDV = ContractDataAccess.select(con,dbc);
            
            if(pContractDV.size()>1) {
            	dbc = new DBCriteria();
            	dbc.addEqualTo(ContractDataAccess.CATALOG_ID,catalogId);
                dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
                pContractDV = ContractDataAccess.select(con,dbc);
                pContractD = (ContractData) pContractDV.get(0);
                pContractId = pContractD.getContractId();
                contractName = pContractD.getShortDesc();                
            }
            
            if(pContractDV.size()==1) {
                pContractD = (ContractData) pContractDV.get(0);
                pContractId = pContractD.getContractId();
                contractName = pContractD.getShortDesc();
            }
//			ContractData contractD = ContractDataAccess
//					.select(con, pContractId);
//			String contractName = contractD.getShortDesc();
//			int catalogId = contractD.getCatalogId();
			//contract items
			dbc = new DBCriteria();
			dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
			if (pItemId > 0) {
				dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, pItemId);
			}

			String itemsReq = ContractItemDataAccess.getSqlSelectIdOnly(
					ContractItemDataAccess.ITEM_ID, dbc);
			dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
			ContractItemDataVector contractItemDV = ContractItemDataAccess
					.select(con, dbc);
			//create result vector
			CWReport items1 = new CWReport();

			for (int ii = 0; ii < contractItemDV.size(); ii++) {
				ContractItemData ciD = (ContractItemData) contractItemDV
						.get(ii);
				ContractItemProfitView itemProf = ContractItemProfitView
						.createValue();
				itemProf.setContractId(pContractId);
				itemProf.setContractName(contractName);
				itemProf.setItemId(ciD.getItemId());
				itemProf.setContrDistFl("Y");
				BigDecimal cost = ciD.getDistCost();
				if (cost == null) {
					errorMess = "^clw^Item has no distributor cost (not set, zero would be acceptable) .Item id: "
							+ ciD.getItemId() + "^clw^";
					throw new Exception(errorMess);
				}
				itemProf.setDistCost(cost);
				itemProf.setDistBaseCost(ciD.getDistBaseCost());
				BigDecimal price = ciD.getAmount();
				itemProf.setPrice(price);
				if (price != null) {
					BigDecimal diff = price.subtract(ciD.getDistCost());
					itemProf.setPriceDiff(diff);
					if (diff != null
							&& price.compareTo(new BigDecimal(0.001)) > 0) {
						itemProf.setPriceDiffPr(diff.divide(price, 4,
								BigDecimal.ROUND_HALF_UP));
					}
				}
				items1.addItem(itemProf);
			}

			//items
			dbc = new DBCriteria();
			dbc.addOneOf(ItemDataAccess.ITEM_ID, itemsReq);
			dbc.addOrderBy(ItemDataAccess.ITEM_ID);
			ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

			for (int ii = 0; ii < itemDV.size(); ii++) {
				ItemData iD = (ItemData) itemDV.get(ii);
				items1.setItemInfo1(iD);
			}

			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				//mfg
				String sql = "select "
						+ " item_id, be.bus_entity_id mfg_id, be.short_desc mfg_name,"
						+ " item_num mfg_sku"
						+ " from clw_item_mapping map, clw_bus_entity be"
						+ " where map.item_mapping_cd = '"
						+ RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'"
						+ " and map.bus_entity_id = be.bus_entity_id"
						+ " and item_id in (" + itemsReq + ") "
						+ "  order by item_id";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
	
				while (rs.next()) {
					int itemId = rs.getInt("item_id");
					int mfgId = rs.getInt("mfg_id");
					String mfgName = rs.getString("mfg_name");
					String mfgSku = rs.getString("mfg_sku");
	
					items1.setMfgInfo(itemId, mfgId, mfgName, mfgSku);
				}
			}
			finally {
				try {
					rs.close();
					stmt.close();
				}
				catch (Exception ignore) {
					//ignore
				}
			}

			try {
				//Meta data
				String sql = "select item_id,  name_value, clw_value "
						+ " from clw_item_meta "
						+ " where name_value in ('COLOR','LIST_PRICE','PACK','SIZE','UOM','COST_PRICE') "
						+ " and item_id in (" + itemsReq + ") "
						+ " order by item_id";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int itemId = rs.getInt("item_id");
					String name = rs.getString("name_value");
					String value = rs.getString("clw_value");
					items1.setMetaInfo(itemId, name, value);
	
				}
			}
			finally {
				try {
					rs.close();
					stmt.close();
				}
				catch (Exception ignore) {
					//ignore
				}
			}
			
			try {
				String sql = "select cs.item_id, be.bus_entity_id dist_id, "
						+ " be.short_desc dist_name, be.erp_num dist_erp, "
						+ " map.item_num dist_sku, map.item_uom dist_uom, map.item_pack dist_pack, "
						+ " map1.item_num mfg1_sku, map1.bus_entity_id mfg1_id, be1.short_desc mfg1_name, "
						+ " map.standard_product_list SPL, cs.add_date item_add_date"
						+ " from clw_catalog_structure cs, clw_bus_entity be, clw_item_mapping map, "
						+ " clw_item_mapping_assoc ima, clw_item_mapping map1, clw_bus_entity be1 "
						+ "  where cs.catalog_id =  "
						+ catalogId
						+ "    and cs.bus_entity_id = be.bus_entity_id "
						+ "    and cs.item_id = map.item_id(+) "
						+ "    and cs.catalog_structure_cd = '" 
						+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "' "
						+ "    and map.item_mapping_cd(+) = '"
						+ RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR	+ "' "
						+ "    and map.bus_entity_id(+) =cs.bus_entity_id "
						+ "    and cs.item_id in ("
						+ itemsReq
						+ ") "
						+ "    and map.item_mapping_id = ima.item_mapping1_id(+) "
						+ "    and ima.item_mapping_assoc_cd (+) = '"
						+ RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG+ "' "
						+ "    and ima.item_mapping2_id = map1.item_mapping_id(+) "
						+ "    and map1.item_mapping_cd(+) = '"
						+ RefCodeNames.ITEM_MAPPING_CD.ITEM_GENERIC_MFG+"' "
						+ "    and be1.bus_entity_id(+) = map1.bus_entity_id "
						+ "   order by cs.item_id";
	
				System.out
						.println("ContractProfitReport CCCCCCCCCCCCCCCCCCCCCC sql: "
								+ sql);
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
	
				while (rs.next()) {
					int itemId = rs.getInt("item_id");
					int distId = rs.getInt("dist_id");
					String distName = rs.getString("dist_name");
					String distErp = rs.getString("dist_erp");
					String distSku = rs.getString("dist_sku");
					String distUom = rs.getString("dist_uom");
					String distPack = rs.getString("dist_pack");
					String mfg1Sku = rs.getString("mfg1_sku");
					String spl = rs.getString("SPL");
					int mfg1Id = rs.getInt("mfg1_id");
					String mfg1Name = rs.getString("mfg1_name");
					Date itemAddedDate = rs.getDate("item_add_date");
	
					items1.setDistInfo(itemId, distId, distName, distErp, distSku,
							distUom, distPack, mfg1Sku, mfg1Id, mfg1Name, spl, itemAddedDate);
				}
			}
			finally {
				try {
					rs.close();
					stmt.close();
				}
				catch (Exception ignore) {
					//ignore
				}
			}

			try {
				//Get catalog account
				String sql = "select distinct be.bus_entity_id "
						+ " from clw_catalog_assoc ca, clw_bus_entity be "
						+ " where ca.bus_entity_id = be.bus_entity_id "
						+ "   and ca.catalog_assoc_cd = '"
						+ RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+ "' "
						+ "   and ca.catalog_id = " + catalogId;
	
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
			
				LinkedList customerIdV = new LinkedList();
				while (rs.next()) {
					int customerId = rs.getInt("bus_entity_id");
					customerIdV.add(new Integer(customerId));
				}
				if (customerIdV.size() == 0) {
					throw new RemoteException("No account found. Contract id: "
							+ pContractId + " Catalog id: " + catalogId);
				}

				//First of January date
				Calendar dateCal = Calendar.getInstance();
				dateCal.add(Calendar.MONTH, -12);
				/*
				 dateCal.set(Calendar.MONTH,0);
				 dateCal.set(Calendar.DAY_OF_MONTH,1);
				 */
				Date dateBeg = dateCal.getTime();
				String dtefmt = "yyyy-MM-dd";
				SimpleDateFormat df = new SimpleDateFormat(dtefmt);
				String dateBegS = df.format(dateBeg);
				items1.setDistributorFilter(distErpBase);
				items1.setManufacturerFilter(manuIdReq);

				for (int ii = 0; ii < customerIdV.size(); ii++) {
					Integer customerIdI = (Integer) customerIdV.get(ii);
	
					sql = " SELECT oi.item_id, oi.item_sku_num, oi.dist_erp_num, "
							+ " Sum(oi.total_quantity_ordered) quantity, "
							+ " Sum(oi.total_quantity_ordered*oi.dist_item_cost) unit_cost "
							+ " , Max(o.original_order_date) last_order_date "
							+ " FROM clw_order o, clw_order_item oi, clw_catalog_assoc ca "
							+ " WHERE ca.catalog_id = "
							+ catalogId
							+ " and o.account_id = "
							+ customerIdI
							+ " AND o.site_id = ca.bus_entity_id "
							+ " AND ca.catalog_assoc_cd = '"
							+ RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE +"'  "
							+ " AND o.order_id = oi.order_id    "
							+ " AND oi.order_item_status_cd != '"
							+ RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED + "' "
							+ " AND o.original_order_date  >= to_date('"
							+ dateBegS
							+ "', '"
							+ dtefmt
							+ "') "
							+ " AND o.order_status_cd IN "
							+ OrderDAO.kGoodOrderStatusSqlList
							+ " and item_id in ("
							+ itemsReq
							+ ") "
							+ " GROUP BY oi.item_id, oi.dist_erp_num, oi.item_sku_num ";
	
					// Order items by sku
					// items1.sort("ItemSku");
					Statement stmt1 = null;
					ResultSet rs1 = null;
					try {
						stmt1 = con.createStatement();
						rs1 = stmt1.executeQuery(sql);
		
						while (rs1.next()) {
							int itemid = rs1.getInt("item_id");
							String distErp = rs1.getString("dist_erp_num");
							distErp = (distErp == null) ? "" : distErp.trim();
							Date lastOrderDate = rs1.getDate("last_order_date");
							int qty = rs1.getInt("quantity");
							if (qty > 0) {
							    BigDecimal sumCost = rs1.getBigDecimal("unit_cost");
		
							    items1.setProfitInfo(itemid, distErp, lastOrderDate, qty,
									sumCost);
							}
						}
					}
					finally {
						try {
							rs1.close();
							stmt1.close();
						}
						catch (Exception ignore) {
							//ignore
						}
					}
				}
			}
			finally {
				try {
					rs.close();
					stmt.close();
				}
				catch (Exception ignore) {
					//ignore
				}
			}

			final String catalogNameSql1 = "SELECT distinct cat.short_desc, " +
					"NVL(cat.long_desc,' ') "+
					"FROM clw_item_assoc assoc, clw_item cat WHERE assoc.catalog_id = ";
			final String catalogNameSql2 = " AND assoc.item1_id = ";
			final String catalogNameSql3 = " AND assoc.item_assoc_cd = '"
				+ RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY + "' "
				+ " AND cat.item_id = assoc.item2_id";
			final String fcond = " AND cat.short_desc is not null ";
			java.util.Iterator it = items1.toCollection().iterator();
			while (it.hasNext()) {
				ContractItemProfitView itm = (ContractItemProfitView) it.next();
				StringBuffer catalogNameSql = new StringBuffer(catalogNameSql1);
				catalogNameSql.append(Integer.toString(catalogId));
				catalogNameSql.append(catalogNameSql2);
				catalogNameSql.append(Integer.toString(itm.getItemId()));
				catalogNameSql.append(catalogNameSql3);
				catalogNameSql.append(fcond);
				Statement st = null;
				ResultSet rs2 = null;
				try {
					st = con.createStatement();
					rs2 = st.executeQuery(catalogNameSql.toString());
					if (rs2.next()) {
						if(rs2.getString(2)!=null && rs2.getString(2).trim().length()>0){
							String fullCatName = rs2.getString(1)+" ("+rs2.getString(2)+")";
							itm.setCategoryName(fullCatName);
							
						}else{
							itm.setCategoryName(rs2.getString(1));
						}
					}
					if (rs2.next()) {
						itm.setCategoryName("*" + itm.getCategoryName());
					}
				}
				finally {
					try {
						rs2.close();
						st.close();
					}
					catch (Exception ignore) {
						//ignore
					}
				}
			}

			items = items1.toVector();
			items.sort("ItemSku");
		} catch (Exception exc) {
			errorMess = "Error. Report.getContractProfit() Api Service Access Exception happened. "
					+ exc.getMessage();
			exc.printStackTrace();
			throw new RemoteException(errorMess);
		}
		result.setTable(new ArrayList());
		for (int i = 0; i < items.size(); i++) {
			ContractItemProfitView itm = (ContractItemProfitView) items.get(i);
			if (manuIdReq != 0) {
				if (manuIdReq != itm.getItemMfgId()) {
					continue;
				}
			}
			if (distIdReq != 0) {
				if (distIdReq != itm.getDistId()) {
					continue;
				}
			}

			ArrayList row = new ArrayList();
			row.add(new Integer(itm.getContractId()));
			row.add(itm.getContractName());
			row.add(new Integer(itm.getItemSku()));
			row.add(itm.getItemDesc());
			row.add(itm.getCategoryName());
			row.add(itm.getItemMfgName());
			row.add(itm.getItemMfgSku());
			row.add(itm.getItemUom());
			row.add(itm.getItemPack());
			row.add(itm.getItemSize());
			row.add(itm.getItemColor());
			row.add(itm.getListPrice());
			row.add(itm.getDistName());
			row.add(itm.getContrDistFl());
			row.add(itm.getDistErp());
			row.add(itm.getDistSku());
			row.add(itm.getDistUom());
			row.add(itm.getDistPack());
			row.add(itm.getItemMfg1Name());
			row.add(itm.getItemMfg1Sku());
			row.add(itm.getDistCost());
			row.add(itm.getDistBaseCost());
			row.add(itm.getPrice());
			row.add(itm.getPriceDiff());
			row.add(itm.getPriceDiffPr());
			row.add(itm.getLastOrderDate());
			row.add(new Integer(itm.getYtdQty()));
			row.add(itm.getAvgCost());
			// Request from Paula 1/4/2006.  
			row.add(itm.getCostPriceMFCP());
			if (itm.getSPL()) {
				row.add("Y");
			} else {
				row.add("N");
			}
			row.add(itm.getItemAddedDate());			
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
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.Integer", "Contract Id", 0, 32, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Contract Name", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "CW SKU", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Item Desc", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Category", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Mfg", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Mfg Sku", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Uom", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Pack", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Item Size", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Color", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "List Price", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Dist Name", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Contract Fl", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Dist Erp#", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Dist Sku", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Dist Uom", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Dist Pack", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Generic Mfg", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "Generic Sku", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "Dist Cost", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "Dist Base Cost", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "Contr Price", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "$ Difference", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "% Difference", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.sql.Timestamp", "Last Order Date", 0, 0, "DATE"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "12 Months Qty", 0, 20, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "Avg Cost", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.math.BigDecimal", "MFCP", 2, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.lang.String", "SPL", 2, 20, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
				"java.sql.Timestamp", "Item Added Date", 0, 0, "DATE"));
		return header;
	}
}
