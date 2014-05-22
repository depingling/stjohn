
package com.cleanwise.service.apps.dataexchange; // SVC 2/24/2009

import com.cleanwise.view.utils.*; //SVC 2/24/2009

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 *  <code>DisplayListSortMyFax</code> Sort display lists of value object vectors
 *
 *@author     tbesser
 *@created    October 15, 2001
 *
 *@author scher
 *slightly changed February 24, 2009
 */
public class DisplayListSortMyFax {
    private static final Logger log = Logger.getLogger(DisplayListSortMyFax.class);
    
	static HttpServletRequest request = null;
	static String storePrefix = null;
	public static void setRequestObject(HttpServletRequest pRequest){
		request = pRequest;
		SessionTool st = new SessionTool(request);
		CleanwiseUser appUser = st.getUserData();
		storePrefix  = null;
		if(appUser.getUserStore().getPrefix()!=null){
			storePrefix = appUser.getUserStore().getPrefix().getValue();
		}
	}

	static final Comparator REFCD_VALUE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((RefCdData)o1).getValue();
			String name2 = ((RefCdData)o2).getValue();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};
	static final Comparator CATALOG_CATEGORY_NAME_COMPARE = new Comparator(){
		public int compare(Object o1, Object o2)
		{
			String name1 = ((CatalogCategoryData)o1).getCatalogCategoryShortDesc();
			String name2 = ((CatalogCategoryData)o2).getCatalogCategoryShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	private static final Comparator WORK_ORDER_ITEM_SEQUENCE = new Comparator() {
		public int compare(Object o1, Object o2) {
			WorkOrderItemData woi1 = ((WorkOrderItemDetailView) o1).getWorkOrderItem();
			WorkOrderItemData woi2 = ((WorkOrderItemDetailView) o2).getWorkOrderItem();
			if (woi1 != null && woi2 != null) {
				int seq1 = ((WorkOrderItemDetailView) o1).getWorkOrderItem().getSequence();
				int seq2 = ((WorkOrderItemDetailView) o2).getWorkOrderItem().getSequence();
				return seq1 - seq2;
			}
			return 0;
		}
	};

	public static void sort(RefCdDataVector v, String sortField) throws Exception {

		if("value".equals(sortField))
		{
			Collections.sort(v,REFCD_VALUE_COMPARE);
		}
	}

	static final Comparator SITE_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((SiteData)o1).getBusEntity().getBusEntityId();
			int id2 = ((SiteData)o2).getBusEntity().getBusEntityId();
			return id1 - id2;
		}
	};
	static final Comparator ASSET_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((AssetView)o1).getAssetName();
			String name2 = ((AssetView)o2).getAssetName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};
	static final Comparator ASSET_NUMBER_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String number1 = ((AssetView)o1).getAssetNumber();
			String number2 = ((AssetView)o2).getAssetNumber();
			return Utility.compareToIgnoreCase(number1, number2);
		}
	};
	static final Comparator ASSET_SERIAL_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String serial1 = ((AssetView)o1).getSerialNumber();
			String serial2 = ((AssetView)o2).getSerialNumber();
			return Utility.compareToIgnoreCase(serial1, serial2);
		}
	};
        static final Comparator ASSET_CATEGORY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String category1 = ((AssetView)o1).getAssetCategory();
			String category2 = ((AssetView)o2).getAssetCategory();
			return Utility.compareToIgnoreCase(category1, category2);
		}
	};
        static final Comparator ASSET_DATE_IN_SERVICE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			Date dateInService1 = Utility.parseDate(((AssetView)o1).getDateInService());
                        if (dateInService1 == null) {
                            dateInService1 = new Date(0);
                        }
			Date dateInService2 = Utility.parseDate(((AssetView)o2).getDateInService());
                        if (dateInService2 == null) {
                            dateInService2 = new Date(0);
                        }
			return dateInService1.compareTo(dateInService2);
		}
	};
        static final Comparator ASSET_SITE_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{       
                        String siteName1 = "";
                        ArrayList siteNames1 = (ArrayList)((AssetView)o1).getSiteInfo().get(AssetView.SITE_INFO.SITE_NAMES);
                        if (siteNames1 != null && siteNames1.size() > 0) {
                            siteName1 = (String)siteNames1.get(0);
                        }
                        String siteName2 = "";
                        ArrayList siteNames2 = (ArrayList)((AssetView)o2).getSiteInfo().get(AssetView.SITE_INFO.SITE_NAMES);
                        if (siteNames2 != null && siteNames2.size() > 0) {
                            siteName2 = (String)siteNames2.get(0);
                        }
			
			return Utility.compareToIgnoreCase(siteName1, siteName2);
		}
	};
	static final Comparator  ASSET_SHORT_DESC_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((AssetData)o1).getShortDesc();
			String name2 = ((AssetData)o2).getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};
	static final Comparator SITE_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((SiteData)o1).getBusEntity().getShortDesc();
			String name2 = ((SiteData)o2).getBusEntity().getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator SITE_ACCOUNT_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((SiteData)o1).getAccountBusEntity().getShortDesc();
			String name2 =
				((SiteData)o2).getAccountBusEntity().getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator SITE_ADDRESS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String address1 =
				((SiteData)o1).getSiteAddress().getAddress1();
			String address2 =
				((SiteData)o2).getSiteAddress().getAddress1();
			return Utility.compareToIgnoreCase(address1, address2);
		}
	};

	static final Comparator SITE_CITY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String city1 = ((SiteData)o1).getSiteAddress().getCity();
			String city2 = ((SiteData)o2).getSiteAddress().getCity();
			return Utility.compareToIgnoreCase(city1, city2);
		}
	};

	static final Comparator SITE_STATE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String state1 =
				((SiteData)o1).getSiteAddress().getStateProvinceCd();
			String state2 =
				((SiteData)o2).getSiteAddress().getStateProvinceCd();
			return Utility.compareToIgnoreCase(state1, state2);
		}
	};

	static final Comparator SITE_COUNTY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String v1 =
				((SiteData)o1).getSiteAddress().getCountyCd();
			String v2 =
				((SiteData)o2).getSiteAddress().getCountyCd();
			return Utility.compareToIgnoreCase(v1, v2);
		}
	};

	static final Comparator SITE_ZIP_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String zip1 =
				((SiteData)o1).getSiteAddress().getPostalCode();
			String zip2 =
				((SiteData)o2).getSiteAddress().getPostalCode();
			return Utility.compareToIgnoreCase(zip1, zip2);
		}
	};

	static final Comparator SITE_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((SiteData)o1).getBusEntity().getBusEntityStatusCd();
			String status2 =
				((SiteData)o2).getBusEntity().getBusEntityStatusCd();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator SITE_VIEW_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((SiteView)o1).getId();
			int id2 = ((SiteView)o2).getId();
			return id1 - id2;
		}
	};

	static final Comparator SITE_VIEW_RANK_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((SiteView)o1).getTargetFacilityRank();
			int id2 = ((SiteView)o2).getTargetFacilityRank();
			return id1 - id2;
		}
	};

	static final Comparator SITE_VIEW_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((SiteView)o1).getName();
			String name2 = ((SiteView)o2).getName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator SITE_VIEW_ACCOUNT_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((SiteView)o1).getAccountName();
			String name2 =
				((SiteView)o2).getAccountName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator SITE_VIEW_ADDRESS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String address1 =
				((SiteView)o1).getAddress();
			String address2 =
				((SiteView)o2).getAddress();
			return Utility.compareToIgnoreCase(address1, address2);
		}
	};

	static final Comparator SITE_VIEW_CITY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String city1 = ((SiteView)o1).getCity();
			String city2 = ((SiteView)o2).getCity();
			return Utility.compareToIgnoreCase(city1, city2);
		}
	};

	static final Comparator SITE_VIEW_STATE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String state1 = ((SiteView)o1).getState();
			String state2 = ((SiteView)o2).getState();
			return Utility.compareToIgnoreCase(state1, state2);
		}
	};

	static final Comparator SITE_VIEW_ZIP_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String zip1 =
				((SiteView)o1).getPostalCode();
			String zip2 =
				((SiteView)o2).getPostalCode();
			return Utility.compareToIgnoreCase(zip1, zip2);
		}
	};

	static final Comparator SITE_VIEW_COUNTY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String v1 =	((SiteView)o1).getCounty();
			String v2 = ((SiteView)o2).getCounty();
			return Utility.compareToIgnoreCase(v1, v2);
		}
	};

	static final Comparator SITE_VIEW_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((SiteView)o1).getStatus();
			String status2 =
				((SiteView)o2).getStatus();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator ACCOUNT_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((AccountData)o1).getBusEntity().getBusEntityId();
			int id2 = ((AccountData)o2).getBusEntity().getBusEntityId();
			return id1 - id2;
		}
	};

	static final Comparator ACCOUNT_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((AccountData)o1).getBusEntity().getShortDesc();
			String name2 = ((AccountData)o2).getBusEntity().getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ACCOUNT_CITY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String city1 = ((AccountData)o1).getPrimaryAddress().getCity();
			String city2 = ((AccountData)o2).getPrimaryAddress().getCity();
			return Utility.compareToIgnoreCase(city1, city2);
		}
	};

	static final Comparator ACCOUNT_STATE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String state1 =
				((AccountData)o1).getPrimaryAddress().getStateProvinceCd();
			String state2 =
				((AccountData)o2).getPrimaryAddress().getStateProvinceCd();
			return Utility.compareToIgnoreCase(state1, state2);
		}
	};

	static final Comparator ACCOUNT_ZIP_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String zip1 =
				((AccountData)o1).getPrimaryAddress().getPostalCode();
			String zip2 =
				((AccountData)o2).getPrimaryAddress().getPostalCode();
			return Utility.compareToIgnoreCase(zip1, zip2);
		}
	};

	static final Comparator ACCOUNT_TYPE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String type1 = ((AccountData)o1).getAccountType().getValue();
			String type2 = ((AccountData)o2).getAccountType().getValue();
			return Utility.compareToIgnoreCase(type1, type2);
		}
	};

	static final Comparator ACCOUNT_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((AccountData)o1).getBusEntity().getBusEntityStatusCd();
			String status2 =
				((AccountData)o2).getBusEntity().getBusEntityStatusCd();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator ACCOUNT_VIEW_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String name1 = ((AccountView)o1).getAcctName();
			String name2 = ((AccountView)o2).getAcctName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ACCOUNT_VIEW_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String name1 = ((AccountView)o1).getAcctStatusCd();
			String name2 = ((AccountView)o2).getAcctStatusCd();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ACCOUNT_VIEW_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			int id1 = ((AccountView)o1).getAcctId();
			int id2 = ((AccountView)o2).getAcctId();
			return id1 - id2;
		}
	};

	static final Comparator STORE_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((StoreData)o1).getBusEntity().getBusEntityId();
			int id2 = ((StoreData)o2).getBusEntity().getBusEntityId();
			return id1 - id2;
		}
	};

	static final Comparator STORE_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((StoreData)o1).getBusEntity().getShortDesc();
			String name2 = ((StoreData)o2).getBusEntity().getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator STORE_CITY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String city1 = ((StoreData)o1).getPrimaryAddress().getCity();
			String city2 = ((StoreData)o2).getPrimaryAddress().getCity();
			return Utility.compareToIgnoreCase(city1, city2);
		}
	};

	static final Comparator STORE_STATE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String state1 =
				((StoreData)o1).getPrimaryAddress().getStateProvinceCd();
			String state2 =
				((StoreData)o2).getPrimaryAddress().getStateProvinceCd();
			return Utility.compareToIgnoreCase(state1, state2);
		}
	};

	static final Comparator STORE_ZIP_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String zip1 =
				((StoreData)o1).getPrimaryAddress().getPostalCode();
			String zip2 =
				((StoreData)o2).getPrimaryAddress().getPostalCode();
			return Utility.compareToIgnoreCase(zip1, zip2);
		}
	};

	static final Comparator STORE_TYPE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String type1 = ((StoreData)o1).getStoreType().getValue();
			String type2 = ((StoreData)o2).getStoreType().getValue();
			return Utility.compareToIgnoreCase(type1, type2);
		}
	};

	static final Comparator CONTRACT_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((ContractDescData)o1).getContractId();
			int id2 = ((ContractDescData)o2).getContractId();
			return id1 - id2;
		}
	};

	static final Comparator CONTRACT_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((ContractDescData)o1).getContractName();
			String name2 = ((ContractDescData)o2).getContractName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator CONTRACT_CATALOG_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String type1 = ((ContractDescData)o1).getCatalogName();
			String type2 = ((ContractDescData)o2).getCatalogName();
			return Utility.compareToIgnoreCase(type1, type2);
		}
	};

	static final Comparator CONTRACT_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((ContractDescData)o1).getStatus();
			String status2 =
				((ContractDescData)o2).getStatus();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator USER_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			int id1 = ((UserData)o1).getUserId();
			int id2 = ((UserData)o2).getUserId();
			return id1 - id2;
		}
	};

	static final Comparator USER_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String name1 = ((UserData)o1).getUserName();
			String name2 = ((UserData)o2).getUserName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator USER_FIRST_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String name1 = ((UserData)o1).getFirstName();
			String name2 = ((UserData)o2).getFirstName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator USER_LAST_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String name1 = ((UserData)o1).getLastName();
			String name2 = ((UserData)o2).getLastName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator USER_TYPE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String type1 = ((UserData)o1).getUserTypeCd();
			String type2 = ((UserData)o2).getUserTypeCd();
			return Utility.compareToIgnoreCase(type1, type2);
		}
	};

	static final Comparator USER_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String status1 = ((UserData)o1).getUserStatusCd();
			String status2 = ((UserData)o2).getUserStatusCd();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator USER_WORKFLOW_ROLE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String s1 = ((UserData)o1).getWorkflowRoleCd();
			String s2 = ((UserData)o2).getWorkflowRoleCd();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};


	static final Comparator DISTRIBUTOR_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 =
				((DistributorData)o1).getBusEntity().getBusEntityId();
			int id2 =
				((DistributorData)o2).getBusEntity().getBusEntityId();
			return id1 - id2;
		}
	};

	static final Comparator DISTRIBUTOR_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((DistributorData)o1).getBusEntity().getShortDesc();
			String name2 =
				((DistributorData)o2).getBusEntity().getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator DISTRIBUTOR_ERP_NUM_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String v1 =
				((DistributorData)o1).getBusEntity().getErpNum();
			String v2 =
				((DistributorData)o2).getBusEntity().getErpNum();
			return Utility.compareToIgnoreCase(v1, v2);
		}
	};

	static final Comparator DISTRIBUTOR_ADDRESS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String address1 =
				((DistributorData)o1).getPrimaryAddress().getAddress1();
			String address2 =
				((DistributorData)o2).getPrimaryAddress().getAddress1();
			return Utility.compareToIgnoreCase(address1, address2);
		}
	};

	static final Comparator DISTRIBUTOR_CITY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String city1 =
				((DistributorData)o1).getPrimaryAddress().getCity();
			String city2 =
				((DistributorData)o2).getPrimaryAddress().getCity();
			return Utility.compareToIgnoreCase(city1, city2);
		}
	};

	static final Comparator DISTRIBUTOR_STATE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String state1 =
				((DistributorData)o1).getPrimaryAddress().getStateProvinceCd();
			String state2 =
				((DistributorData)o2).getPrimaryAddress().getStateProvinceCd();
			return Utility.compareToIgnoreCase(state1, state2);
		}
	};

	static final Comparator DISTRIBUTOR_ZIP_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String zip1 =
				((DistributorData)o1).getPrimaryAddress().getPostalCode();
			String zip2 =
				((DistributorData)o2).getPrimaryAddress().getPostalCode();
			return Utility.compareToIgnoreCase(zip1, zip2);
		}
	};

	static final Comparator DISTRIBUTOR_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((DistributorData)o1).getBusEntity().getBusEntityStatusCd();
			String status2 =
				((DistributorData)o2).getBusEntity().getBusEntityStatusCd();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator MANUFACTURER_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 =
				((ManufacturerData)o1).getBusEntity().getBusEntityId();
			int id2 =
				((ManufacturerData)o2).getBusEntity().getBusEntityId();
			return id1 - id2;
		}
	};

	static final Comparator MANUFACTURER_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((ManufacturerData)o1).getBusEntity().getShortDesc();
			String name2 =
				((ManufacturerData)o2).getBusEntity().getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator MANUFACTURER_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((ManufacturerData)o1).getBusEntity().getBusEntityStatusCd();
			String status2 =
				((ManufacturerData)o2).getBusEntity().getBusEntityStatusCd();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator SERVICE_PROVIDER_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 =
				((ServiceProviderData)o1).getBusEntity().getBusEntityId();
			int id2 =
				((ServiceProviderData)o2).getBusEntity().getBusEntityId();
			return id1 - id2;
		}
	};

	static final Comparator SERVICE_PROVIDER_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((ServiceProviderData)o1).getBusEntity().getShortDesc();
			String name2 =
				((ServiceProviderData)o2).getBusEntity().getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator SERVICE_PROVIDER_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((ServiceProviderData)o1).getBusEntity().getBusEntityStatusCd();
			String status2 =
				((ServiceProviderData)o2).getBusEntity().getBusEntityStatusCd();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

        static final Comparator SERVICE_PROVIDER_ADDRESS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String addressA =
				((ServiceProviderData)o1).getPrimaryAddress().getAddress1();
			String addressB =
				((ServiceProviderData)o2).getPrimaryAddress().getAddress1();
			return Utility.compareToIgnoreCase(addressA, addressB);
		}
	};

        static final Comparator SERVICE_PROVIDER_CITY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String cityA =
				((ServiceProviderData)o1).getPrimaryAddress().getCity();
			String cityB =
				((ServiceProviderData)o2).getPrimaryAddress().getCity();
			return Utility.compareToIgnoreCase(cityA, cityB);
		}
	};

        static final Comparator SERVICE_PROVIDER_PROVINCE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String provinceA =
				((ServiceProviderData)o1).getPrimaryAddress().getStateProvinceCd();
			String provinceB =
				((ServiceProviderData)o2).getPrimaryAddress().getStateProvinceCd();
			return Utility.compareToIgnoreCase(provinceA, provinceB);
		}
	};

	static final Comparator CATALOG_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((CatalogData)o1).getCatalogId();
			int id2 = ((CatalogData)o2).getCatalogId();
			return id1 - id2;
		}
	};

	static final Comparator CATALOG_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((CatalogData)o1).getShortDesc();
			String name2 = ((CatalogData)o2).getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator CATALOG_TYPE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String type1 = ((CatalogData)o1).getCatalogTypeCd();
			String type2 = ((CatalogData)o2).getCatalogTypeCd();
			return Utility.compareToIgnoreCase(type1, type2);
		}
	};

	static final Comparator CATALOG_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((CatalogData)o1).getCatalogStatusCd();
			String status2 =
				((CatalogData)o2).getCatalogStatusCd();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator ORDER_GUIDE_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 =
				((OrderGuideDescData)o1).getOrderGuideId();
			int id2 =
				((OrderGuideDescData)o2).getOrderGuideId();
			return id1 - id2;
		}
	};

	static final Comparator ORDER_GUIDE_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((OrderGuideDescData)o1).getOrderGuideName();
			String name2 =
				((OrderGuideDescData)o2).getOrderGuideName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ORDER_GUIDE_SHORT_DESC_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((OrderGuideData)o1).getShortDesc();
			String name2 =
				((OrderGuideData)o2).getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ORDER_GUIDE_CATALOG_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((OrderGuideDescData)o1).getCatalogName();
			String name2 =
				((OrderGuideDescData)o2).getCatalogName();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ORDER_GUIDE_CATALOG_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			int id1 =
				((OrderGuideDescData)o1).getCatalogId();
			int id2 =
				((OrderGuideDescData)o2).getCatalogId();
			return id1-id2;
		}
	};

	static final Comparator ORDER_GUIDE_STATUS_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String status1 =
				((OrderGuideDescData)o1).getStatus();
			String status2 =
				((OrderGuideDescData)o2).getStatus();
			return Utility.compareToIgnoreCase(status1, status2);
		}
	};

	static final Comparator ORDER_GUIDE_TYPE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2) {
			String name1 =
				((OrderGuideDescData)o1).getOrderGuideTypeCd();
			String name2 =
				((OrderGuideDescData)o2).getOrderGuideTypeCd();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((OrderGuideItemDescData)o1).getShortDesc();
			String name2 =
				((OrderGuideItemDescData)o2).getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_AMOUNT_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			BigDecimal amt1 =
				((OrderGuideItemDescData)o1).getAmount();
			BigDecimal amt2 =
				((OrderGuideItemDescData)o2).getAmount();
			return amt1.compareTo(amt2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_CSKU_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getCwSKU();
			String s2 =
				((OrderGuideItemDescData)o2).getCwSKU();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_SIZE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getSizeDesc();
			String s2 =
				((OrderGuideItemDescData)o2).getSizeDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_PACK_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getPackDesc();
			String s2 =
				((OrderGuideItemDescData)o2).getPackDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_UOM_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getUomDesc();
			String s2 =
				((OrderGuideItemDescData)o2).getUomDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_COLOR_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getColorDesc();
			String s2 =
				((OrderGuideItemDescData)o2).getColorDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_MANUF_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getManufacturerCd();
			String s2 =
				((OrderGuideItemDescData)o2).getManufacturerCd();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_MSKU_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getManufacturerSKU();
			String s2 =
				((OrderGuideItemDescData)o2).getManufacturerSKU();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_CATEGORY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((OrderGuideItemDescData)o1).getCategoryDesc();
			String s2 =
				((OrderGuideItemDescData)o2).getCategoryDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_PRICE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			BigDecimal p1 =
				((OrderGuideItemDescData)o1).getPrice();
			BigDecimal p2 =
				((OrderGuideItemDescData)o2).getPrice();
			return p1.compareTo(p2);
		}
	};

	static final Comparator ORDER_GUIDE_ITEM_QUANTITY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int qty1 =
				((OrderGuideItemDescData)o1).getQuantity();
			int qty2 =
				((OrderGuideItemDescData)o2).getQuantity();
			return qty1 - qty2;
		}
	};

	static final Comparator CONTRACT_ITEM_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 =
				((ContractItemDescData)o1).getShortDesc();
			String name2 =
				((ContractItemDescData)o2).getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator CONTRACT_ITEM_CSKU_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getCwSKU();
			String s2 =
				((ContractItemDescData)o2).getCwSKU();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_SIZE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getSizeDesc();
			String s2 =
				((ContractItemDescData)o2).getSizeDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_PACK_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getPackDesc();
			String s2 =
				((ContractItemDescData)o2).getPackDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_UOM_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getUomDesc();
			String s2 =
				((ContractItemDescData)o2).getUomDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_COLOR_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getColorDesc();
			String s2 =
				((ContractItemDescData)o2).getColorDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_MANUF_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getManufacturerCd();
			String s2 =
				((ContractItemDescData)o2).getManufacturerCd();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_MSKU_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getManufacturerSKU();
			String s2 =
				((ContractItemDescData)o2).getManufacturerSKU();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_CATEGORY_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String s1 =
				((ContractItemDescData)o1).getCategoryDesc();
			String s2 =
				((ContractItemDescData)o2).getCategoryDesc();
			return Utility.compareToIgnoreCase(s1, s2);
		}
	};

	static final Comparator CONTRACT_ITEM_PRICE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			BigDecimal p1 =
				((ContractItemDescData)o1).getPrice();
			BigDecimal p2 =
				((ContractItemDescData)o2).getPrice();
			return p1.compareTo(p2);
		}
	};

	static final Comparator
	CONTRACT_ITEM_DIST_COST_COMPARE =
		new Comparator() {
		public int compare(Object o1, Object o2)
		{
			BigDecimal a1 = ((ContractItemDescData)
					o1).getDistributorCost();
			BigDecimal a2 = ((ContractItemDescData)
					o2).getDistributorCost();
			return a1.compareTo(a2);
		}
	};

	static final Comparator CONTRACT_ITEM_AMOUNT_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			try {
				BigDecimal a1 =
					CurrencyFormat.parse(((ContractItemDescData)o1).getAmountS());
				BigDecimal a2 =
					CurrencyFormat.parse(((ContractItemDescData)o2).getAmountS());
				return a1.compareTo(a2);
			} catch (ParseException pe) {
				return 0;
			}
		}
	};

	static final Comparator COST_CENTER_ID_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			int id1 = ((CostCenterData)o1).getCostCenterId();
			int id2 = ((CostCenterData)o2).getCostCenterId();
			return id1 - id2;
		}
	};

	static final Comparator COST_CENTER_NAME_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String name1 = ((CostCenterData)o1).getShortDesc();
			String name2 = ((CostCenterData)o2).getShortDesc();
			return Utility.compareToIgnoreCase(name1, name2);
		}
	};

	static final Comparator COST_CENTER_TYPE_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String type1 = ((CostCenterData)o1).getCostCenterTypeCd();
			String type2 = ((CostCenterData)o2).getCostCenterTypeCd();
			return Utility.compareToIgnoreCase(type1, type2);
		}
	};

	static final Comparator COST_CENTER_TAX_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String tax1 = ((CostCenterData)o1).getCostCenterTaxType();
			String tax2 = ((CostCenterData)o2).getCostCenterTaxType();
			return Utility.compareToIgnoreCase(tax1, tax2);
		}
	};
	static final Comparator COST_CENTER_FREIGHT_COMPARE = new Comparator() {
		public int compare(Object o1, Object o2)
		{
			String freight1 = ((CostCenterData)o1).getAllocateFreight();
			String freight2 = ((CostCenterData)o2).getAllocateFreight();
			return Utility.compareToIgnoreCase(freight1, freight2);
		}
	};

		static final Comparator COST_CENTER_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String status1 = ((CostCenterData)o1).getCostCenterStatusCd();
				String status2 = ((CostCenterData)o2).getCostCenterStatusCd();
				return Utility.compareToIgnoreCase(status1, status2);
			}
		};

		static final Comparator SPENT_COST_CENTER_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2) {
				String status1 = ((BudgetSpendView) o1).getCostCenterName();
				String status2 = ((BudgetSpendView) o2).getCostCenterName();
				return Utility.compareToIgnoreCase(status1, status2);
			}
		};

		static final Comparator PRODUCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((ProductData)o1).getShortDesc();
				String name2 = ((ProductData)o2).getShortDesc();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator PRODUCT_ID_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int id1 = ((ProductData)o1).getProductId();
				int id2 = ((ProductData)o2).getProductId();
				return id1 - id2;
			}
		};

		static final Comparator PRODUCT_SKU_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int sku1 = ((ProductData)o1).getSkuNum();
				int sku2 = ((ProductData)o2).getSkuNum();
				return sku1 - sku2;
			}
		};

		static final Comparator PRODUCT_SIZE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String size1 = ((ProductData)o1).getSize();
				String size2 = ((ProductData)o2).getSize();
				return Utility.compareToIgnoreCase(size1, size2);
			}
		};

		static final Comparator PRODUCT_PACK_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String pack1 = ((ProductData)o1).getPack();
				String pack2 = ((ProductData)o2).getPack();
				return Utility.compareToIgnoreCase(pack1, pack2);
			}
		};

		static final Comparator PRODUCT_UOM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String uom1 = ((ProductData)o1).getUom();
				String uom2 = ((ProductData)o2).getUom();
				return Utility.compareToIgnoreCase(uom1, uom2);
			}
		};

		static final Comparator PRODUCT_COLOR_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String color1 = ((ProductData)o1).getColor();
				String color2 = ((ProductData)o2).getColor();
				return Utility.compareToIgnoreCase(color1, color2);
			}
		};

		static final Comparator PRODUCT_MANUFACTURER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String manufacturer1 = ((ProductData)o1).getManufacturerName();
				String manufacturer2 = ((ProductData)o2).getManufacturerName();
				return Utility.compareToIgnoreCase(manufacturer1, manufacturer2);
			}
		};

		static final Comparator PRODUCT_MSKU_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String msku1 = ((ProductData)o1).getManufacturerSku();
				String msku2 = ((ProductData)o2).getManufacturerSku();
				return Utility.compareToIgnoreCase(msku1, msku2);
			}
		};

		static final Comparator PRODUCT_DISTID_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int distId1 = 0;
				BusEntityData dist1 =
					((ProductData)o1).getCatalogDistributor();
				if (dist1 != null) {
					distId1 = dist1.getBusEntityId();
				}
				int distId2 = 0;
				BusEntityData dist2 =
					((ProductData)o2).getCatalogDistributor();
				if (dist2 != null) {
					distId2 = dist2.getBusEntityId();
				}
				return distId1 - distId2;
			}
		};


		static final Comparator FREIGHT_TABLE_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((FreightTableData)o1).getShortDesc();
				String name2 = ((FreightTableData)o2).getShortDesc();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator FREIGHT_TABLE_TYPE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((FreightTableData)o1).getFreightTableTypeCd();
				String name2 = ((FreightTableData)o2).getFreightTableTypeCd();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator FREIGHT_TABLE_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((FreightTableData)o1).getFreightTableStatusCd();
				String name2 = ((FreightTableData)o2).getFreightTableStatusCd();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator FREIGHT_TABLE_ID_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int id1 = ((FreightTableData)o1).getFreightTableId();
				int id2 = ((FreightTableData)o2).getFreightTableId();
				return id1 - id2;
			}
		};

		static final Comparator ORDER_STATUS_ACCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String acctName1 = ((OrderData)o1).getAccountErpNum();
				String acctName2 = ((OrderData)o2).getAccountErpNum();
				return Utility.compareToIgnoreCase(acctName1, acctName2);
			}
		};

		static final Comparator ORDER_STATUS_ERP_ORDER_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int erpOrderNum1 = ((OrderData)o1).getErpOrderNum();
				int erpOrderNum2 = ((OrderData)o2).getErpOrderNum();
				return erpOrderNum1 - erpOrderNum2;
			}
		};

		static final Comparator ORDER_STATUS_WEB_ORDER_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String webOrderNum1 = ((OrderData)o1).getOrderNum();
				String webOrderNum2 = ((OrderData)o2).getOrderNum();
				return Utility.compareToIgnoreCase(webOrderNum1, webOrderNum2);
			}
		};

		static final Comparator ORDER_STATUS_ORDER_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date orderDate1 = ((OrderData)o1).getOriginalOrderDate();
				Date orderDate2 = ((OrderData)o2).getOriginalOrderDate();
				return orderDate1.compareTo(orderDate2);
			}
		};

		static final Comparator ORDER_STATUS_SITE_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String siteName1 = ((OrderData)o1).getOrderSiteName();
				String siteName2 = ((OrderData)o2).getOrderSiteName();
				return Utility.compareToIgnoreCase(siteName1, siteName2);
			}
		};

		static final Comparator ORDER_STATUS_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String status1 = ((OrderData)o1).getOrderStatusCd();
				String status2 = ((OrderData)o2).getOrderStatusCd();
				return Utility.compareToIgnoreCase(status1, status2);
			}
		};

		static final Comparator ORDER_STATUS_METHOD_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String method1 = ((OrderData)o1).getOrderSourceCd();
				String method2 = ((OrderData)o2).getOrderSourceCd();
				return Utility.compareToIgnoreCase(method1, method2);
			}
		};



		static final Comparator ORDER_STATUS_DESC_ACCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String acctName1 = ((OrderStatusDescData)o1).getAccountName();
				String acctName2 = ((OrderStatusDescData)o2).getAccountName();
				return Utility.compareToIgnoreCase(acctName1, acctName2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_DIST_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((OrderStatusDescData)o1).getDistName();
				String name2 = ((OrderStatusDescData)o2).getDistName();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_REF_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String n1 = ((OrderStatusDescData)o1).getOrderDetail().getRefOrderNum();
				String n2 = ((OrderStatusDescData)o2).getOrderDetail().getRefOrderNum();
				return Utility.compareToIgnoreCase(n1, n2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_ERP_ORDER_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int erpOrderNum1 = ((OrderStatusDescData)o1).getOrderDetail().getErpOrderNum();
				int erpOrderNum2 = ((OrderStatusDescData)o2).getOrderDetail().getErpOrderNum();
				return erpOrderNum1 - erpOrderNum2;
			}
		};

		static final Comparator ORDER_STATUS_DESC_WEB_ORDER_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String webOrderNum1 = ((OrderStatusDescData)o1).getOrderDetail().getOrderNum();
				String webOrderNum2 = ((OrderStatusDescData)o2).getOrderDetail().getOrderNum();
				return Utility.compareToIgnoreCase(webOrderNum1, webOrderNum2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_ORDER_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date orderDate1 = ((OrderStatusDescData)o1).getOrderDetail().getOriginalOrderDate();
				Date orderDate2 = ((OrderStatusDescData)o2).getOrderDetail().getOriginalOrderDate();
				return orderDate1.compareTo(orderDate2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_DATE_DESC_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date orderDate1 = ((OrderStatusDescData)o1).getOrderDetail().getOriginalOrderDate();
				Date orderDate2 = ((OrderStatusDescData)o2).getOrderDetail().getOriginalOrderDate();
				if(orderDate1.before(orderDate2)) return 1;
				else if(orderDate1.after(orderDate2)) return -1;
				return 0;
			}
		};

		static final Comparator ORDER_STATUS_DESC_SHIP_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String shipDate1 = ((OrderStatusDescData)o1).getRequestedShipDate();
				String shipDate2 = ((OrderStatusDescData)o2).getRequestedShipDate();
				return shipDate1.compareToIgnoreCase(shipDate2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_CUST_PO_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((OrderStatusDescData)o1).getOrderDetail().getRequestPoNum();
				String desc2 = ((OrderStatusDescData)o2).getOrderDetail().getRequestPoNum();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_SITE_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String siteName1 = ((OrderStatusDescData)o1).getOrderDetail().getOrderSiteName();
				String siteName2 = ((OrderStatusDescData)o2).getOrderDetail().getOrderSiteName();
				return Utility.compareToIgnoreCase(siteName1, siteName2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_CITY_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String city1 = ((OrderStatusDescData)o1).getShipTo().getCity();
				String city2 = ((OrderStatusDescData)o2).getShipTo().getCity();
				return Utility.compareToIgnoreCase(city1, city2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_STATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((OrderStatusDescData)o1).getShipTo().getStateProvinceCd();
				String desc2 = ((OrderStatusDescData)o2).getShipTo().getStateProvinceCd();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_ZIP_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String zip1 = ((OrderStatusDescData)o1).getShipTo().getPostalCode();
				String zip2 = ((OrderStatusDescData)o2).getShipTo().getPostalCode();
				return Utility.compareToIgnoreCase(zip1, zip2);
			}
		};

		static final Comparator  ORDER_STATUS_DESC_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String status1 = ShopTool.xlateStatus((OrderStatusDescData)o1, request, storePrefix);
				String status2 = ShopTool.xlateStatus((OrderStatusDescData)o2, request, storePrefix);
				return Utility.compareToIgnoreCase(status1, status2);
			}
		};

                static final Comparator  ORDER_STATUS_DESC_STATUS_ADMIN_COMPARE = new Comparator() {
                        public int compare(Object o1, Object o2)
                        {
                                String status1 = ((OrderStatusDescData)o1).getOrderDetail().getOrderStatusCd();
                                String status2 = ((OrderStatusDescData)o2).getOrderDetail().getOrderStatusCd();
                                return Utility.compareToIgnoreCase(status1, status2);
                        }
                };

		static final Comparator ORDER_STATUS_DESC_ADDRESS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2) {
				String address1 = ((OrderStatusDescData) o1).getShipTo().getAddress1();
				String address2 = ((OrderStatusDescData) o2).getShipTo().getAddress1();
				return Utility.compareToIgnoreCase(address1, address2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_METHOD_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String method1 = ((OrderStatusDescData)o1).getOrderDetail().getOrderSourceCd();
				String method2 = ((OrderStatusDescData)o2).getOrderDetail().getOrderSourceCd();
				return Utility.compareToIgnoreCase(method1, method2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_PLACEDBY_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String addBy1 = ((OrderStatusDescData)o1).getOrderDetail().getAddBy();
				String addBy2 = ((OrderStatusDescData)o2).getOrderDetail().getAddBy();
				return Utility.compareToIgnoreCase(addBy1, addBy2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_EXCEPTION_TYPE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((OrderStatusDescData)o1).getExceptionTypeCd();
				String name2 = ((OrderStatusDescData)o2).getExceptionTypeCd();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_ORDER_TYPE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{

				String source1 = ((OrderStatusDescData)o1).getOrderDetail().getOrderSourceCd();
				String source2 = ((OrderStatusDescData)o2).getOrderDetail().getOrderSourceCd();
				return Utility.compareToIgnoreCase(source1, source2);
			}
		};

		static final Comparator ORDER_STATUS_DESC_AMOUNT_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				BigDecimal amount1 = ((OrderStatusDescData)o1).getOrderDetail().getTotalPrice();
				if (null == amount1) amount1 = new BigDecimal(0);
				BigDecimal amount2 = ((OrderStatusDescData)o2).getOrderDetail().getTotalPrice();
				if (null == amount2) amount2 = new BigDecimal(0);
				return amount1.compareTo(amount2);
			}
		};


		static final Comparator PURCHASE_ORDER_STATUS_DESC_DISTRIBUTOR_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((PurchaseOrderStatusDescDataView)o1).getDistributorBusEntityData().getShortDesc();
				String name2 = ((PurchaseOrderStatusDescDataView)o2).getDistributorBusEntityData().getShortDesc();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_ACCOUNT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((PurchaseOrderStatusDescDataView)o1).getPoAccountName();
				String name2 = ((PurchaseOrderStatusDescDataView)o2).getPoAccountName();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_ERP_ORDER_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int int1 = ((PurchaseOrderStatusDescDataView)o1).getOrderData().getErpOrderNum();
				int int2 = ((PurchaseOrderStatusDescDataView)o2).getOrderData().getErpOrderNum();
				return int1 - int2;
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_WEB_ORDER_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String webOrderNum1 = ((PurchaseOrderStatusDescDataView)o1).getOrderData().getOrderNum();
				String webOrderNum2 = ((PurchaseOrderStatusDescDataView)o2).getOrderData().getOrderNum();
				return Utility.compareToIgnoreCase(webOrderNum1, webOrderNum2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_PO_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date orderDate1 = ((PurchaseOrderStatusDescDataView)o1).getPurchaseOrderData().getPoDate();
				Date orderDate2 = ((PurchaseOrderStatusDescDataView)o2).getPurchaseOrderData().getPoDate();
				return orderDate1.compareTo(orderDate2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_ERP_PO_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String val1 = ((PurchaseOrderStatusDescDataView)o1).getPurchaseOrderData().getErpPoNum();
				String val2 = ((PurchaseOrderStatusDescDataView)o2).getPurchaseOrderData().getErpPoNum();
				return Utility.compareToIgnoreCase(val1, val2);
			}
		};



		static final Comparator PURCHASE_ORDER_STATUS_DESC_ERP_PO_REF_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date orderDate1 = ((PurchaseOrderStatusDescDataView)o1).getPurchaseOrderData().getErpPoRefDate();
				Date orderDate2 = ((PurchaseOrderStatusDescDataView)o2).getPurchaseOrderData().getErpPoRefDate();
				return orderDate1.compareTo(orderDate2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_ERP_PO_REF_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String val1 = ((PurchaseOrderStatusDescDataView)o1).getPurchaseOrderData().getErpPoRefNum();
				String val2 = ((PurchaseOrderStatusDescDataView)o2).getPurchaseOrderData().getErpPoRefNum();
				return Utility.compareToIgnoreCase(val1, val2);
			}
		};


		static final Comparator PURCHASE_ORDER_STATUS_DESC_SITE_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String val1 = ((PurchaseOrderStatusDescDataView)o1).getShipToAddress().getShortDesc();
				String val2 = ((PurchaseOrderStatusDescDataView)o2).getShipToAddress().getShortDesc();
				return Utility.compareToIgnoreCase(val1, val2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_POSTAL_CODE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String val1 = ((PurchaseOrderStatusDescDataView)o1).getShipToAddress().getPostalCode();
				String val2 = ((PurchaseOrderStatusDescDataView)o2).getShipToAddress().getPostalCode();
				return Utility.compareToIgnoreCase(val1, val2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_PURCHASE_ORDER_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String val1 = ((PurchaseOrderStatusDescDataView)o1).getPurchaseOrderData().getPurchaseOrderStatusCd();
				String val2 = ((PurchaseOrderStatusDescDataView)o2).getPurchaseOrderData().getPurchaseOrderStatusCd();
				return Utility.compareToIgnoreCase(val1, val2);
			}
		};

		static final Comparator PURCHASE_ORDER_STATUS_DESC_METHOD_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String val1 = ((PurchaseOrderStatusDescDataView)o1).getOrderData().getOrderSourceCd();
				String val2 = ((PurchaseOrderStatusDescDataView)o2).getOrderData().getOrderSourceCd();
				return Utility.compareToIgnoreCase(val1, val2);
			}
		};

		static final Comparator ORDER_ITEM_ERP_PO_LINE_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int val1 = ((OrderItemData)o1).getErpPoLineNum();
				int val2 = ((OrderItemData)o2).getErpPoLineNum();
				return val1 - val2;
			}
		};

		static final Comparator ORDER_ITEM_ORDER_LINE_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int val1 = ((OrderItemData)o1).getOrderLineNum();
				int val2 = ((OrderItemData)o2).getOrderLineNum();
				return val1 - val2;
			}
		};

		static final Comparator ORDER_STATUS_DESC_ORDER_DATE_WITH_REVISION_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date orderDate1 = ((OrderStatusDescData)o1).getOrderDetail().getRevisedOrderDate()==null?
						((OrderStatusDescData)o1).getOrderDetail().getOriginalOrderDate():
							((OrderStatusDescData)o1).getOrderDetail().getRevisedOrderDate();
						Date orderDate2 = ((OrderStatusDescData)o2).getOrderDetail().getRevisedOrderDate()==null?
								((OrderStatusDescData)o2).getOrderDetail().getOriginalOrderDate():
									((OrderStatusDescData)o2).getOrderDetail().getRevisedOrderDate();
								return orderDate1.compareTo(orderDate2);
			}
		};

		static final Comparator ORDER_ITEM_DESC_DIST_ORDER_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((OrderItemDescData)o1).getOrderItem().getDistOrderNum();
				String desc2 = ((OrderItemDescData)o2).getOrderItem().getDistOrderNum();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator ORDER_ITEM_DESC_ERP_PO_NUM_COMPARE = new Comparator() {
			// Include the po line numbers for easy comparison
			// to the POs sent to distributors.
			public int compare(Object o1, Object o2)
			{
				OrderItemDescData t1 = (OrderItemDescData)o1,
				t2 = (OrderItemDescData)o2;
				String epln = "", oln = "";
				if( t1.getOrderItem().getErpPoLineNum() < 10 ) {
					epln = "00";
				} else if( t1.getOrderItem().getErpPoLineNum() < 100 ) {
					epln = "0";
				}
				epln += t1.getOrderItem().getErpPoLineNum();

				if( t1.getOrderItem().getOrderLineNum() < 10 ) {
					oln = "00";
				} else if( t1.getOrderItem().getOrderLineNum() < 100 ) {
					oln = "0";
				}
				oln += t1.getOrderItem().getOrderLineNum();

				String erpPoNum1 =
					t1.getOrderItem().getErpPoNum() + ":" + epln + ":" + oln;

				epln = ""; oln = "";
				if( t2.getOrderItem().getErpPoLineNum() < 10 ) {
					epln = "00";
				} else if( t2.getOrderItem().getErpPoLineNum() < 100 ) {
					epln = "0";
				}
				epln += t2.getOrderItem().getErpPoLineNum();

				if( t2.getOrderItem().getOrderLineNum() < 10 ) {
					oln = "00";
				} else if( t2.getOrderItem().getOrderLineNum() < 100 ) {
					oln = "0";
				}
				oln += t2.getOrderItem().getOrderLineNum();

				String erpPoNum2 =
					t2.getOrderItem().getErpPoNum() + ":" + epln + ":" + oln;

				return Utility.compareToIgnoreCase(erpPoNum1, erpPoNum2);
			}
		};

		static final Comparator ORDER_ITEM_DESC_OUTBOUND_PO_NUM_COMPARE = new Comparator() {
			// Include the po line numbers for easy comparison
			// to the POs sent to distributors.
			public int compare(Object o1, Object o2)
			{
				OrderItemDescData t1 = (OrderItemDescData)o1,
				t2 = (OrderItemDescData)o2;
				String epln = "", oln = "";
				if( t1.getOrderItem().getErpPoLineNum() < 10 ) {
					epln = "00";
				} else if( t1.getOrderItem().getErpPoLineNum() < 100 ) {
					epln = "0";
				}
				epln += t1.getOrderItem().getErpPoLineNum();

				if( t1.getOrderItem().getOrderLineNum() < 10 ) {
					oln = "00";
				} else if( t1.getOrderItem().getOrderLineNum() < 100 ) {
					oln = "0";
				}
				oln += t1.getOrderItem().getOrderLineNum();

				String erpPoNum1 =
					t1.getOrderItem().getOutboundPoNum() + ":" + epln + ":" + oln;

				epln = ""; oln = "";
				if( t2.getOrderItem().getErpPoLineNum() < 10 ) {
					epln = "00";
				} else if( t2.getOrderItem().getErpPoLineNum() < 100 ) {
					epln = "0";
				}
				epln += t2.getOrderItem().getErpPoLineNum();

				if( t2.getOrderItem().getOrderLineNum() < 10 ) {
					oln = "00";
				} else if( t2.getOrderItem().getOrderLineNum() < 100 ) {
					oln = "0";
				}
				oln += t2.getOrderItem().getOrderLineNum();

				String erpPoNum2 =
					t2.getOrderItem().getOutboundPoNum() + ":" + epln + ":" + oln;

				return Utility.compareToIgnoreCase(erpPoNum1, erpPoNum2);
			}
		};

		static final Comparator ORDER_ITEM_DESC_ERP_PO_LINE_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int val1 = ((OrderItemDescData)o1).getOrderItem().getErpPoLineNum();
				int val2 = ((OrderItemDescData)o2).getOrderItem().getErpPoLineNum();
				return val1 - val2;
			}
		};

		static final Comparator ORDER_ITEM_DESC_CW_SKU_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int cwSkuNum1 = ((OrderItemDescData)o1).getOrderItem().getItemSkuNum();
				int cwSkuNum2 = ((OrderItemDescData)o2).getOrderItem().getItemSkuNum();
				return cwSkuNum1 - cwSkuNum2;
			}
		};

		static final Comparator ORDER_ITEM_DESC_DIST_SKU_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String distSkuNum1 = ((OrderItemDescData)o1).getOrderItem().getDistItemSkuNum();
				String distSkuNum2 = ((OrderItemDescData)o2).getOrderItem().getDistItemSkuNum();
				return Utility.compareToIgnoreCase(distSkuNum1, distSkuNum2);
			}
		};

		static final Comparator ORDER_ITEM_DESC_PRODUCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String productName1 = ((OrderItemDescData)o1).getOrderItem().getItemShortDesc();
				String productName2 = ((OrderItemDescData)o2).getOrderItem().getItemShortDesc();
				return Utility.compareToIgnoreCase(productName1, productName2);
			}
		};



		static final Comparator INVOICE_DIST_DETAIL_DESC_DIST_ORDER_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceDistDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o1).getOrderItem().getDistOrderNum() ) {
					desc1 = ((InvoiceDistDetailDescData)o1).getOrderItem().getDistOrderNum();
				}
				String desc2 = "";
				if( null != ((InvoiceDistDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o2).getOrderItem().getDistOrderNum() ) {
					desc2 = ((InvoiceDistDetailDescData)o2).getOrderItem().getDistOrderNum();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator INVOICE_DIST_DETAIL_DESC_ERP_PO_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceDistDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o1).getOrderItem().getErpPoNum() ) {
					desc1 = ((InvoiceDistDetailDescData)o1).getOrderItem().getErpPoNum();
				}
				String desc2 = "";
				if( null != ((InvoiceDistDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o2).getOrderItem().getErpPoNum() ) {
					desc2 = ((InvoiceDistDetailDescData)o2).getOrderItem().getErpPoNum();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator INVOICE_DIST_DETAIL_DESC_CW_SKU_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int num1 = 0;
				if( null != ((InvoiceDistDetailDescData)o1).getOrderItem() ) {
					num1 = ((InvoiceDistDetailDescData)o1).getOrderItem().getItemSkuNum();
				}
				int num2 = 0;
				if( null != ((InvoiceDistDetailDescData)o2).getOrderItem() ) {
					num2 = ((InvoiceDistDetailDescData)o2).getOrderItem().getItemSkuNum();
				}
				return num1 - num2;
			}
		};

		static final Comparator INVOICE_DIST_DETAIL_DESC_DIST_SKU_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceDistDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o1).getOrderItem().getDistItemSkuNum() ) {
					desc1 = ((InvoiceDistDetailDescData)o1).getOrderItem().getDistItemSkuNum();
				}
				String desc2 = "";
				if( null != ((InvoiceDistDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o2).getOrderItem().getDistItemSkuNum() ) {
					desc2 = ((InvoiceDistDetailDescData)o2).getOrderItem().getDistItemSkuNum();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator INVOICE_DIST_DETAIL_DESC_PRODUCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceDistDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o1).getOrderItem().getItemShortDesc() ) {
					desc1 = ((InvoiceDistDetailDescData)o1).getOrderItem().getItemShortDesc();
				}
				String desc2 = "";
				if( null != ((InvoiceDistDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceDistDetailDescData)o2).getOrderItem().getItemShortDesc() ) {
					desc2 = ((InvoiceDistDetailDescData)o2).getOrderItem().getItemShortDesc();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};



		static final Comparator INVOICE_CUST_DETAIL_DESC_DIST_ORDER_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceCustDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o1).getOrderItem().getDistOrderNum() ) {
					desc1 = ((InvoiceCustDetailDescData)o1).getOrderItem().getDistOrderNum();
				}
				String desc2 = "";
				if( null != ((InvoiceCustDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o2).getOrderItem().getDistOrderNum() ) {
					desc2 = ((InvoiceCustDetailDescData)o2).getOrderItem().getDistOrderNum();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator INVOICE_CUST_DETAIL_DESC_ERP_PO_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceCustDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o1).getOrderItem().getErpPoNum() ) {
					desc1 = ((InvoiceCustDetailDescData)o1).getOrderItem().getErpPoNum();
				}
				String desc2 = "";
				if( null != ((InvoiceCustDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o2).getOrderItem().getErpPoNum() ) {
					desc2 = ((InvoiceCustDetailDescData)o2).getOrderItem().getErpPoNum();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator INVOICE_CUST_DETAIL_DESC_CW_SKU_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int num1 = 0;
				if( null != ((InvoiceCustDetailDescData)o1).getOrderItem() ) {
					num1 = ((InvoiceCustDetailDescData)o1).getOrderItem().getItemSkuNum();
				}
				int num2 = 0;
				if( null != ((InvoiceCustDetailDescData)o2).getOrderItem() ) {
					num2 = ((InvoiceCustDetailDescData)o2).getOrderItem().getItemSkuNum();
				}
				return num1 - num2;
			}
		};

		static final Comparator INVOICE_CUST_DETAIL_DESC_DIST_SKU_NUMBER_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceCustDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o1).getOrderItem().getDistItemSkuNum() ) {
					desc1 = ((InvoiceCustDetailDescData)o1).getOrderItem().getDistItemSkuNum();
				}
				String desc2 = "";
				if( null != ((InvoiceCustDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o2).getOrderItem().getDistItemSkuNum() ) {
					desc2 = ((InvoiceCustDetailDescData)o2).getOrderItem().getDistItemSkuNum();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator INVOICE_CUST_DETAIL_DESC_PRODUCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = "";
				if( null != ((InvoiceCustDetailDescData)o1).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o1).getOrderItem().getItemShortDesc() ) {
					desc1 = ((InvoiceCustDetailDescData)o1).getOrderItem().getItemShortDesc();
				}
				String desc2 = "";
				if( null != ((InvoiceCustDetailDescData)o2).getOrderItem()
						&& null != ((InvoiceCustDetailDescData)o2).getOrderItem().getItemShortDesc() ) {
					desc2 = ((InvoiceCustDetailDescData)o2).getOrderItem().getItemShortDesc();
				}
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};


		static final Comparator ORDER_PROPERTY_DATA_PROPERTY_ID_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int propertyId1 = ((OrderPropertyData)o1).getOrderPropertyId();
				int propertyId2 = ((OrderPropertyData)o2).getOrderPropertyId();
				return propertyId1 - propertyId2;
			}
		};


		static final Comparator INVOICE_CUST_DETAIL_DIST_LINE_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int int1 = ((InvoiceCustDetailData)o1).getLineNumber();
				int int2 = ((InvoiceCustDetailData)o2).getLineNumber();
				return int1 - int2;
			}
		};

		static final Comparator CALL_DESC_ACCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String acctName1 = ((CallDescData)o1).getAccountName();
				String acctName2 = ((CallDescData)o2).getAccountName();
				return Utility.compareToIgnoreCase(acctName1, acctName2);
			}
		};

		static final Comparator CALL_DESC_SITE_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String siteName1 = ((CallDescData)o1).getSiteName();
				String siteName2 = ((CallDescData)o2).getSiteName();
				return Utility.compareToIgnoreCase(siteName1, siteName2);
			}
		};

		static final Comparator CALL_DESC_CONTACT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String contactName1 = ((CallDescData)o1).getCallDetail().getContactName();
				String contactName2 = ((CallDescData)o2).getCallDetail().getContactName();
				return Utility.compareToIgnoreCase(contactName1, contactName2);
			}
		};

		static final Comparator CALL_DESC_LONG_DESC_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((CallDescData)o1).getCallDetail().getLongDesc();
				String desc2 = ((CallDescData)o2).getCallDetail().getLongDesc();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator CALL_DESC_SITE_CITY_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String siteCity1 = ((CallDescData)o1).getSiteCity();
				String siteCity2 = ((CallDescData)o2).getSiteCity();
				return Utility.compareToIgnoreCase(siteCity1, siteCity2);
			}
		};

		static final Comparator CALL_DESC_SITE_STATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String siteState1 = ((CallDescData)o1).getSiteState();
				String siteState2 = ((CallDescData)o2).getSiteState();
				return Utility.compareToIgnoreCase(siteState1, siteState2);
			}
		};

		static final Comparator CALL_DESC_SITE_ZIP_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String siteZip1 = ((CallDescData)o1).getSiteZip();
				String siteZip2 = ((CallDescData)o2).getSiteZip();
				return Utility.compareToIgnoreCase(siteZip1, siteZip2);
			}
		};

		static final Comparator CALL_DESC_OPENED_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date date1 = ((CallDescData)o1).getCallDetail().getAddDate();
				if(null == date1) date1 = new Date();
				Date date2 = ((CallDescData)o2).getCallDetail().getAddDate();
				if(null == date2) date2 = new Date();
				return date1.compareTo(date2);
			}
		};

		static final Comparator CALL_DESC_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String status1 = ((CallDescData)o1).getCallDetail().getCallStatusCd();
				String status2 = ((CallDescData)o2).getCallDetail().getCallStatusCd();
				return Utility.compareToIgnoreCase(status1, status2);
			}
		};

		static final Comparator CALL_DESC_OPENED_BY_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((CallDescData)o1).getCallDetail().getAddBy();
				String name2 = ((CallDescData)o2).getCallDetail().getAddBy();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator CALL_DESC_ASSIGNED_TO_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String name1 = ((CallDescData)o1).getAssignedTo();
				String name2 = ((CallDescData)o2).getAssignedTo();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};




		static final Comparator KNOWLEDGE_DESC_CATEGORY_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((KnowledgeDescData)o1).getKnowledgeDetail().getKnowledgeCategoryCd();
				String desc2 = ((KnowledgeDescData)o2).getKnowledgeDetail().getKnowledgeCategoryCd();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator KNOWLEDGE_DESC_PRODUCT_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((KnowledgeDescData)o1).getProductName();
				String desc2 = ((KnowledgeDescData)o2).getProductName();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator KNOWLEDGE_DESC_SKU_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((KnowledgeDescData)o1).getSkuNum();
				String desc2 = ((KnowledgeDescData)o2).getSkuNum();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator KNOWLEDGE_DESC_LONG_DESC_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((KnowledgeDescData)o1).getKnowledgeDetail().getLongDesc();
				String desc2 = ((KnowledgeDescData)o2).getKnowledgeDetail().getLongDesc();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator KNOWLEDGE_DESC_OPENED_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date date1 = ((KnowledgeDescData)o1).getKnowledgeDetail().getAddDate();
				if(null == date1) date1 = new Date();
				Date date2 = ((KnowledgeDescData)o2).getKnowledgeDetail().getAddDate();
				if(null == date2) date2 = new Date();
				return date1.compareTo(date2);
			}
		};

		static final Comparator KNOWLEDGE_DESC_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((KnowledgeDescData)o1).getKnowledgeDetail().getKnowledgeStatusCd();
				String desc2 = ((KnowledgeDescData)o2).getKnowledgeDetail().getKnowledgeStatusCd();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator KNOWLEDGE_DESC_USER_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((KnowledgeDescData)o1).getKnowledgeDetail().getAddBy();
				String desc2 = ((KnowledgeDescData)o2).getKnowledgeDetail().getAddBy();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};


		static final Comparator REMITTNCE_TRANS_REF_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDescView)o1).getRemittanceData().getTransactionReference();
				String desc2 = ((RemittanceDescView)o2).getRemittanceData().getTransactionReference();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDescView)o1).getRemittanceData().getRemittanceStatusCd();
				String desc2 = ((RemittanceDescView)o2).getRemittanceData().getRemittanceStatusCd();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_ERP_ACCOUNT_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDescView)o1).getRemittanceData().getPayeeErpAccount();
				String desc2 = ((RemittanceDescView)o2).getRemittanceData().getPayeeErpAccount();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_PAY_REF_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDescView)o1).getRemittanceData().getPaymentReferenceNumber();
				String desc2 = ((RemittanceDescView)o2).getRemittanceData().getPaymentReferenceNumber();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_TRANS_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date date1 = ((RemittanceDescView)o1).getRemittanceData().getTransactionDate();
				if (date1 == null){
					return 0;
				}
				Date date2 = ((RemittanceDescView)o2).getRemittanceData().getTransactionDate();
				if (date2 == null){
					return 1;
				}
				return date1.compareTo(date2);
			}
		};

		static final Comparator REMITTNCE_PAYMENT_POST_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date date1 = ((RemittanceDescView)o1).getRemittanceData().getPaymentPostDate();
				if (date1 == null){
					return 0;
				}
				Date date2 = ((RemittanceDescView)o2).getRemittanceData().getPaymentPostDate();
				if (date2 == null){
					return 1;
				}
				return date1.compareTo(date2);
			}
		};

		static final Comparator REMITTNCE_ADD_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date date1 = ((RemittanceDescView)o1).getRemittanceData().getAddDate();
				if (date1 == null){
					return 0;
				}
				Date date2 = ((RemittanceDescView)o2).getRemittanceData().getAddDate();
				if (date2 == null){
					return 1;
				}
				return date1.compareTo(date2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_ADD_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date date1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getAddDate();
				if (date1 == null){
					return 0;
				}
				Date date2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getAddDate();
				if (date2 == null){
					return 1;
				}
				return date1.compareTo(date2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_MOD_DATE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date date1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getModDate();
				if (date1 == null){
					return 0;
				}
				Date date2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getModDate();
				if (date2 == null){
					return 1;
				}
				return date1.compareTo(date2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_INVOICE_TYPE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getInvoiceType();
				String desc2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getInvoiceType();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_INVOICE_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getInvoiceNumber();
				String desc2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getInvoiceNumber();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_SITE_REF_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getSiteReference();
				String desc2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getSiteReference();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getRemittanceDetailStatusCd();
				String desc2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getRemittanceDetailStatusCd();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_ADD_BY_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getAddBy();
				String desc2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getAddBy();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_MOD_BY_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String desc1 = ((RemittanceDetailDescView)o1).getRemittanceDetailData().getModBy();
				String desc2 = ((RemittanceDetailDescView)o2).getRemittanceDetailData().getModBy();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};


		static final Comparator REMITTNCE_DETAIL_DESC_NET_AMOUNT_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				BigDecimal amt1 =
					((RemittanceDetailDescView)o1).getRemittanceDetailData().getNetAmount();
				BigDecimal amt2 =
					((RemittanceDetailDescView)o2).getRemittanceDetailData().getNetAmount();
				return amt1.compareTo(amt2);
			}
		};

		static final Comparator REMITTNCE_DETAIL_DESC_REMIT_DETAIL_ID_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int amt1 =
					((RemittanceDetailDescView)o1).getRemittanceDetailData().getRemittanceDetailId();
				int amt2 =
					((RemittanceDetailDescView)o2).getRemittanceDetailData().getRemittanceDetailId();
				return amt1 - amt2;
			}
		};

		static final Comparator MESSAGE_RESOURCE_DATA_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String val1 =
					((MessageResourceData)o1).getName();
				String val2 =
					((MessageResourceData)o2).getName();
				return Utility.compareToIgnoreCase(val1, val2);
			}
		};

		static final Comparator ORDER_ITEM_DESC_ORDER_LINE_NUM_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int val1 = ((OrderItemDescData)o1).getOrderItem().getOrderLineNum();
				int val2 = ((OrderItemDescData)o2).getOrderItem().getOrderLineNum();
				return val1 - val2;
			}
		};

		static final Comparator BSC_SHORT_DESC_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2) {
				String name1 = ((BuildingServicesContractorView) o1).getBusEntityData().getShortDesc();
				String name2 = ((BuildingServicesContractorView) o2).getBusEntityData().getShortDesc();
				return Utility.compareToIgnoreCase(name1, name2);
			}
		};

		static final Comparator BUS_ENTITY_SHORT_DESC_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2) {
				String desc1 = ((BusEntityData) o1).getShortDesc();
				String desc2 = ((BusEntityData) o2).getShortDesc();
				return Utility.compareToIgnoreCase(desc1, desc2);
			}
		};
                static final Comparator WORK_ORDER_SITE_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            String name1 = ((WorkOrderSiteNameView)o1).getSiteName();
                            String name2 = ((WorkOrderSiteNameView)o2).getSiteName();;
                            return Utility.compareToIgnoreCase(name1, name2);
                    }
                };
                static final Comparator WORK_ORDER_NUMBER_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            String name1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getWorkOrderNum();
                            String name2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getWorkOrderNum();
                            return Utility.compareToIgnoreCase(name1, name2);
                    }
                };
                static final Comparator WORK_ORDER_PO_NUMBER_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            String name1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getPoNumber();
                            String name2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getPoNumber();
                            return Utility.compareToIgnoreCase(name1, name2);
                    }
                };
                static final Comparator WORK_ORDER_REQUESTED_SERVICE_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            String name1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getShortDesc();
                            String name2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getShortDesc();
                            return Utility.compareToIgnoreCase(name1, name2);
                    }
                };
                static final Comparator WORK_ORDER_TYPE_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            String name1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getTypeCd();
                            String name2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getTypeCd();
                            return Utility.compareToIgnoreCase(name1, name2);
                    }
                };
                static final Comparator WORK_ORDER_PRIORITY_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            String name1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getPriority();
                            String name2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getPriority();
                            return Utility.compareToIgnoreCase(name1, name2);
                    }
                };
                static final Comparator WORK_ORDER_STATUS_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            String name1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getStatusCd();
                            String name2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getStatusCd();
                            return Utility.compareToIgnoreCase(name1, name2);
                    }
                };
                static final Comparator WORK_ORDER_ACTUAL_START_DATE_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            Date date1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getActualStartDate();
                            Date date2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getActualStartDate();
                            if (date1 == null ) {
                                date1 = new Date(0);
                            }
                            if (date2 == null ) {
                                date2 = new Date(0);
                            }
                            return date1.compareTo(date2);
                    }
                };
                static final Comparator WORK_ORDER_ACTUAL_FINISH_DATE_COMPARE = new Comparator() {
                    public int compare(Object o1, Object o2)
                    {
                            Date date1 = ((WorkOrderSiteNameView)o1).getWorkOrder().getActualStartDate();
                            Date date2 = ((WorkOrderSiteNameView)o2).getWorkOrder().getActualStartDate();
                            if (date1 == null ) {
                                date1 = new Date(0);
                            }
                            if (date2 == null ) {
                                date2 = new Date(0);
                            }
                            return date1.compareTo(date2);
                    }
                };

		/**
		 *  <code>sort</code>
		 *
		 *@param  sites        a <code>SiteDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(MessageResourceDataVector resources, String sortField)
		throws Exception {
			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(resources, MESSAGE_RESOURCE_DATA_NAME_COMPARE);
			} else {
				throw new IllegalArgumentException("Sort field: " + sortField);
			}
		}


		/**
		 *  <code>sort</code>
		 *
		 *@param  sites        a <code>SiteDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(SiteDataVector sites, String sortField)
		throws Exception {
			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(sites, SITE_NAME_COMPARE);
			} else if (sortField.equals("account")) {
				Collections.sort(sites, SITE_ACCOUNT_COMPARE);
			} else if (sortField.equals("city")) {
				Collections.sort(sites, SITE_CITY_COMPARE);
			} else if (sortField.equals("state")) {
				Collections.sort(sites, SITE_STATE_COMPARE);
			} else if (sortField.equals("county")) {
				Collections.sort(sites, SITE_COUNTY_COMPARE);
			} else if (sortField.equals("zipcode")) {
				Collections.sort(sites, SITE_ZIP_COMPARE);
			} else if (sortField.equals("address")) {
				Collections.sort(sites, SITE_ADDRESS_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(sites, SITE_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(sites, SITE_ID_COMPARE);
			} else {
				throw new IllegalArgumentException("Sort field: " + sortField);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  sites        a <code>SiteViewVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(SiteViewVector sites, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(sites, SITE_VIEW_NAME_COMPARE);
			} else if (sortField.equals("account")) {
				Collections.sort(sites, SITE_VIEW_ACCOUNT_COMPARE);
			} else if (sortField.equals("city")) {
				Collections.sort(sites, SITE_VIEW_CITY_COMPARE);
			} else if (sortField.equals("state")) {
				Collections.sort(sites, SITE_VIEW_STATE_COMPARE);
			} else if (sortField.equals("zipcode")) {
				Collections.sort(sites, SITE_VIEW_ZIP_COMPARE);
			} else if (sortField.equals("county")) {
				Collections.sort(sites, SITE_VIEW_COUNTY_COMPARE);
			} else if (sortField.equals("address")) {
				Collections.sort(sites, SITE_VIEW_ADDRESS_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(sites, SITE_VIEW_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(sites, SITE_VIEW_ID_COMPARE);
			} else if (sortField.equals("rank")) {
				Collections.sort(sites, SITE_VIEW_RANK_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  accounts     an <code>AccountDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(AccountDataVector accounts, String sortField)
		{

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(accounts, ACCOUNT_NAME_COMPARE);
			} else if (sortField.equals("city")) {
				Collections.sort(accounts, ACCOUNT_CITY_COMPARE);
			} else if (sortField.equals("state")) {
				Collections.sort(accounts, ACCOUNT_STATE_COMPARE);
			} else if (sortField.equals("zipcode")) {
				Collections.sort(accounts, ACCOUNT_ZIP_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(accounts, ACCOUNT_TYPE_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(accounts, ACCOUNT_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(accounts, ACCOUNT_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  accounts     an <code>AccountViewVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(AccountViewVector accounts, String sortField) {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(accounts, ACCOUNT_VIEW_NAME_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(accounts, ACCOUNT_VIEW_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(accounts, ACCOUNT_VIEW_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  stores     a <code>StoreDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(StoreDataVector stores, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(stores, STORE_NAME_COMPARE);
			} else if (sortField.equals("city")) {
				Collections.sort(stores, STORE_CITY_COMPARE);
			} else if (sortField.equals("state")) {
				Collections.sort(stores, STORE_STATE_COMPARE);
			} else if (sortField.equals("zipcode")) {
				Collections.sort(stores, STORE_ZIP_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(stores, STORE_TYPE_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(stores, STORE_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  contracts     a <code>ContractDescDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(ContractDescDataVector contracts, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(contracts, CONTRACT_NAME_COMPARE);
			} else if (sortField.equals("catalog")) {
				Collections.sort(contracts, CONTRACT_CATALOG_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(contracts, CONTRACT_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(contracts, CONTRACT_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  users     a <code>UserDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(UserDataVector users, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(users, USER_NAME_COMPARE);
			} else if (sortField.equals("firstName")) {
				Collections.sort(users, USER_FIRST_NAME_COMPARE);
			} else if (sortField.equals("lastName")) {
				Collections.sort(users, USER_LAST_NAME_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(users, USER_TYPE_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(users, USER_STATUS_COMPARE);
			} else if (sortField.equals("workflowRoleCd")) {
				Collections.sort(users, USER_WORKFLOW_ROLE_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(users, USER_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  distributors a <code>DistributorDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(DistributorDataVector distributors,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(distributors, DISTRIBUTOR_NAME_COMPARE);
			} else if (sortField.equals("erpNum")) {
				Collections.sort(distributors, DISTRIBUTOR_ERP_NUM_COMPARE);
			} else if (sortField.equals("address")) {
				Collections.sort(distributors, DISTRIBUTOR_ADDRESS_COMPARE);
			} else if (sortField.equals("city")) {
				Collections.sort(distributors, DISTRIBUTOR_CITY_COMPARE);
			} else if (sortField.equals("state")) {
				Collections.sort(distributors, DISTRIBUTOR_STATE_COMPARE);
			} else if (sortField.equals("zipcode")) {
				Collections.sort(distributors, DISTRIBUTOR_ZIP_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(distributors, DISTRIBUTOR_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(distributors, DISTRIBUTOR_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  manufacturers a <code>ManufacturerDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(ManufacturerDataVector manufacturers,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(manufacturers, MANUFACTURER_NAME_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(manufacturers, MANUFACTURER_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(manufacturers, MANUFACTURER_ID_COMPARE);
			}
		}

		public static void sort(ServiceProviderDataVector sp,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(sp, SERVICE_PROVIDER_NAME_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(sp, SERVICE_PROVIDER_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(sp, SERVICE_PROVIDER_ID_COMPARE);
			} else if (sortField.equals("city")) {
				Collections.sort(sp, SERVICE_PROVIDER_CITY_COMPARE);
			} else if (sortField.equals("state")) {
				Collections.sort(sp, SERVICE_PROVIDER_PROVINCE_COMPARE);
			} else if (sortField.equals("address")) {
				Collections.sort(sp, SERVICE_PROVIDER_ADDRESS_COMPARE);
			}
		}
		/**
		 *  <code>sort</code>
		 *
		 *@param  catalogs a <code>CatalogDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(CatalogDataVector catalogs,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(catalogs, CATALOG_NAME_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(catalogs, CATALOG_TYPE_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(catalogs, CATALOG_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(catalogs, CATALOG_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  orderguides a <code>OrderGuideDescDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderGuideDescDataVector orderguides,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(orderguides, ORDER_GUIDE_NAME_COMPARE);
			} else if (sortField.equals("catalog")) {
				Collections.sort(orderguides, ORDER_GUIDE_CATALOG_COMPARE);
			} else if (sortField.equals("catalogId")) {
				Collections.sort(orderguides, ORDER_GUIDE_CATALOG_ID_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(orderguides, ORDER_GUIDE_STATUS_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(orderguides, ORDER_GUIDE_TYPE_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(orderguides, ORDER_GUIDE_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  orderguides a <code>OrderGuideDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderGuideDataVector orderguides,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("short_desc")) {
				Collections.sort(orderguides, ORDER_GUIDE_SHORT_DESC_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  orderguideItems a <code>OrderGuideItemDescDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderGuideItemDescDataVector orderguideItems,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_NAME_COMPARE);
			} else if(sortField.equals("amount")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_AMOUNT_COMPARE);
			} else if(sortField.equals("cwSKU")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_CSKU_COMPARE);
			} else if(sortField.equals("size")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_SIZE_COMPARE);
			} else if(sortField.equals("pack")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_PACK_COMPARE);
			} else if(sortField.equals("uom")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_UOM_COMPARE);
			} else if(sortField.equals("color")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_COLOR_COMPARE);
			} else if(sortField.equals("manufacturer")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_MANUF_COMPARE);
			} else if(sortField.equals("manufSKU")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_MSKU_COMPARE);
			} else if(sortField.equals("category")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_CATEGORY_COMPARE);
			} else if(sortField.equals("price")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_PRICE_COMPARE);
			} else if(sortField.equals("quantity")) {
				Collections.sort(orderguideItems, ORDER_GUIDE_ITEM_QUANTITY_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  contractItems a <code>ContractItemDescDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(ContractItemDescDataVector contractItems,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(contractItems, CONTRACT_ITEM_NAME_COMPARE);
			} else if(sortField.equals("cwSKU")) {
				Collections.sort(contractItems, CONTRACT_ITEM_CSKU_COMPARE);
			} else if(sortField.equals("size")) {
				Collections.sort(contractItems, CONTRACT_ITEM_SIZE_COMPARE);
			} else if(sortField.equals("pack")) {
				Collections.sort(contractItems, CONTRACT_ITEM_PACK_COMPARE);
			} else if(sortField.equals("uom")) {
				Collections.sort(contractItems, CONTRACT_ITEM_UOM_COMPARE);
			} else if(sortField.equals("color")) {
				Collections.sort(contractItems, CONTRACT_ITEM_COLOR_COMPARE);
			} else if(sortField.equals("manufacturer")) {
				Collections.sort(contractItems, CONTRACT_ITEM_MANUF_COMPARE);
			} else if(sortField.equals("manufSKU")) {
				Collections.sort(contractItems, CONTRACT_ITEM_MSKU_COMPARE);
			} else if(sortField.equals("category")) {
				Collections.sort(contractItems, CONTRACT_ITEM_CATEGORY_COMPARE);
			} else if(sortField.equals("price")) {
				Collections.sort(contractItems, CONTRACT_ITEM_PRICE_COMPARE);
			} else if(sortField.equals("amount")) {
				Collections.sort(contractItems, CONTRACT_ITEM_AMOUNT_COMPARE);
			} else if(sortField.equals("dist_cost")) {
				Collections.sort(contractItems, CONTRACT_ITEM_DIST_COST_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  costcenters a <code>CostCenterDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(CostCenterDataVector costcenters,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(costcenters, COST_CENTER_NAME_COMPARE);
				//} else if (sortField.equals("amount")) {
				//    Collections.sort(costcenters, COST_CENTER_AMOUNT_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(costcenters, COST_CENTER_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(costcenters, COST_CENTER_ID_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(costcenters, COST_CENTER_TYPE_COMPARE);
			} else if (sortField.equals("tax")) {
				Collections.sort(costcenters, COST_CENTER_TAX_COMPARE);
			} else if (sortField.equals("freight")) {
				Collections.sort(costcenters, COST_CENTER_FREIGHT_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  products a <code>ProductDataVector</code> list
		 *@param  sortField  String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(ProductDataVector products,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(products, PRODUCT_NAME_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(products, PRODUCT_ID_COMPARE);
			} else if (sortField.equals("sku")) {
				Collections.sort(products, PRODUCT_SKU_COMPARE);
			} else if (sortField.equals("size")) {
				Collections.sort(products, PRODUCT_SIZE_COMPARE);
			} else if (sortField.equals("pack")) {
				Collections.sort(products, PRODUCT_PACK_COMPARE);
			} else if (sortField.equals("uom")) {
				Collections.sort(products, PRODUCT_UOM_COMPARE);
			} else if (sortField.equals("color")) {
				Collections.sort(products, PRODUCT_COLOR_COMPARE);
			} else if (sortField.equals("manufacturer")) {
				Collections.sort(products, PRODUCT_MANUFACTURER_COMPARE);
			} else if (sortField.equals("msku")) {
				Collections.sort(products, PRODUCT_MSKU_COMPARE);
			} else if (sortField.equals("distid")) {
				Collections.sort(products, PRODUCT_DISTID_COMPARE);
			}
		}


		/*
		 * Sorting multi products by size
		 */
		public static ShoppingCartItemDataVector sortMultiItems(ShoppingCartItemDataVector items, String sortField){

			ShoppingCartItemDataVector result = new ShoppingCartItemDataVector();

			ShoppingCartItemDataVector sizeS = new ShoppingCartItemDataVector();
			ShoppingCartItemDataVector sizeN = new ShoppingCartItemDataVector();
			ShoppingCartItemDataVector sizeOther = new ShoppingCartItemDataVector();

			Map hm = new HashMap();
			hm.put( "XS",new Integer(1));
			hm.put("S",new Integer(2));
			hm.put("SM",new Integer(3));
			hm.put("Small",new Integer(4));
			hm.put("M",new Integer(5));
			hm.put("MD",new Integer(6));
			hm.put("Medium",new Integer(7));
			hm.put("L",new Integer(8));
			hm.put("LG",new Integer(9));
			hm.put("Large",new Integer(10));
			hm.put("LT",new Integer(11));
			hm.put("XL",new Integer(12));
			hm.put("XLT",new Integer(13));
			hm.put("XXL",new Integer(14));

			if(items != null && items.size() > 0){

				for(int i=0; i<items.size(); i++){
					boolean isSizeString = false;
					ShoppingCartItemData sciD = (ShoppingCartItemData)items.get(i);

					String size = sciD.getProduct().getSize();

					if(hm.containsKey(size)){
						sizeS.add(sciD);
						isSizeString = true;
					}

					if(!isSizeString){
						if(Utility.isNumeric(size)){
							sizeN.add(sciD);
						}else{
							sizeOther.add(sciD);
						}
					}
				}

				if(sizeS != null && sizeS.size() > 0){

					for(int ii=sizeS.size()-1; ii>1; ii--){
						for(int jj=0; jj<ii; jj++){

							String s1 = ((ShoppingCartItemData)sizeS.get(jj)).getProduct().getSize();
							String s2 = ((ShoppingCartItemData)sizeS.get(jj+1)).getProduct().getSize();

							int val1 = ((Integer)hm.get(s1)).intValue();
							int val2 = ((Integer)hm.get(s2)).intValue();

							if(val1 > val2){
								ShoppingCartItemData t = (ShoppingCartItemData)sizeS.get(jj);
								sizeS.set(jj, sizeS.get(jj+1));
								sizeS.set(jj+1, t);
							}
						}
					}

					for(int s=0; s<sizeS.size(); s++){
						result.add((ShoppingCartItemData)sizeS.get(s));
					}

				}
				if(sizeN != null && sizeN.size() > 0){

					Collections.sort(sizeN, NUMERIC_SIZE_COMPARATOR);
					for(int s=0; s<sizeN.size(); s++){
						result.add((ShoppingCartItemData)sizeN.get(s));
					}
				}
				if(sizeOther != null && sizeOther.size() > 0){

					Collections.sort(sizeOther, GENERIC_SIZE_COMPARATOR);
					for(int s=0; s<sizeOther.size(); s++){
						result.add((ShoppingCartItemData)sizeOther.get(s));
					}
				}
			}
			return result;
		}


		private static final Comparator GENERIC_SIZE_COMPARATOR = new Comparator() {

			public int compare(Object o1, Object o2){
				ShoppingCartItemData sc1 = ((ShoppingCartItemData)o1);
				ShoppingCartItemData sc2 = ((ShoppingCartItemData)o2);

				String val1 = "";
				String val2 = "";
				if(sc1 != null && sc2 != null){
					val1= sc1.getProduct().getSize();
					val2= sc2.getProduct().getSize();
				}
				return val1.compareTo(val2);

			}
		};

		private static final Comparator NUMERIC_SIZE_COMPARATOR = new Comparator() {

			public int compare(Object o1, Object o2){
				ShoppingCartItemData sc1 = ((ShoppingCartItemData)o1);
				ShoppingCartItemData sc2 = ((ShoppingCartItemData)o2);

				String val1 = "";
				String val2 = "";
				int n1=0;
				int n2=0;
				if(sc1 != null && sc2 != null){
					val1= sc1.getProduct().getSize();
					val2= sc2.getProduct().getSize();
				}

				String num1 = Utility.extractNum(val1);
				String num2 = Utility.extractNum(val2);

				if(num1 != null && num2 != null){
					n1 = Integer.parseInt(num1);
					n2 = Integer.parseInt(num2);
				}
				if( n1 < n2){
					return -1;
				}else if(n1 > n2){
					return 1;
				}else{
					return 0;
				}

			}

		};

		public static void sort(ShoppingCartItemDataVector shopItems,
				String sortField)
		throws Exception {
			DisplayListSortMyFax dls = new DisplayListSortMyFax();
			dls.doSHOPPING_CART_UI_COMPARATOR(shopItems,sortField);
		}

		private void doSHOPPING_CART_UI_COMPARATOR(ShoppingCartItemDataVector shopItems,String sortField){
			SHOPPING_CART_UI_COMPARATOR c = new SHOPPING_CART_UI_COMPARATOR();
			c.setField(sortField);
			Collections.sort(shopItems, c);
		}

		private  class SHOPPING_CART_UI_COMPARATOR implements Comparator{
			String field;
			void setField(String val){field = val;}
			public int compare(Object o1, Object o2)
			{
				if(field == null){
					return 0;
				}
				ShoppingCartItemData sc1 = ((ShoppingCartItemData)o1);
				ShoppingCartItemData sc2 = ((ShoppingCartItemData)o2);



				String val1;
				String val2;
				if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.ACTUAL_SKU.equals(field)){
					val1= sc1.getActualSkuNum();
					val2= sc2.getActualSkuNum();
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.CATALOG_PRODUCT_SHORT_DESC.equals(field)){
					val1= sc1.getProduct().getCatalogProductShortDesc();
					val2= sc2.getProduct().getCatalogProductShortDesc();
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_FLAG.equals(field)){
					int v1= sc1.getDistInventoryQty();
					int v2= sc2.getDistInventoryQty();
					return v1-v2;
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.DIST_INVENTORY_QTY.equals(field)){
					int v1= sc1.getDistInventoryQty();
					int v2= sc2.getDistInventoryQty();
					return v1-v2;
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_NAME.equals(field)){
					val1= sc1.getProduct().getManufacturerName();
					val2= sc2.getProduct().getManufacturerName();
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MANU_SKU.equals(field)){
					val1= sc1.getProduct().getManufacturerSku();
					val2= sc2.getProduct().getManufacturerSku();
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.MAX_ORDER_QTY.equals(field)){
					int v1= sc1.getMaxOrderQty();
					int v2= sc2.getMaxOrderQty();
					return v1-v2;
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PAR_VALUE.equals(field)){
					int v1= sc1.getInventoryParValue();
					int v2= sc2.getInventoryParValue();
					return v1-v2;
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.PRICE.equals(field)){
					BigDecimal v1= new BigDecimal(sc1.getPrice());
					BigDecimal v2= new BigDecimal(sc2.getPrice());
					return v1.compareTo(v2);
				}else if(RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.SPL.equals(field)){
					val1= sc1.getProduct().getCatalogDistrMapping().getStandardProductList();
					val2= sc2.getProduct().getCatalogDistrMapping().getStandardProductList();
				}else{
					val1 = sc1.getProduct().getProductAttribute(field);
					val2 = sc2.getProduct().getProductAttribute(field);
				}

				return Utility.compareToIgnoreCase(val1, val2);
			}
		}



		/**
		 *  <code>sort</code>
		 *
		 *@param  freightTables        a <code>FreightTableDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(FreightTableDataVector freightTables, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(freightTables, FREIGHT_TABLE_NAME_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(freightTables, FREIGHT_TABLE_TYPE_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(freightTables, FREIGHT_TABLE_STATUS_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(freightTables, FREIGHT_TABLE_ID_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  orderStatusList        a <code>OrderDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderDataVector orderStatusList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("acctname")) {
				Collections.sort(orderStatusList, ORDER_STATUS_ACCT_NAME_COMPARE);
			} else if (sortField.equals("distname")) {

			} else if (sortField.equals("erpordernum")) {
				Collections.sort(orderStatusList, ORDER_STATUS_ERP_ORDER_NUMBER_COMPARE);
			} else if (sortField.equals("webordernum")) {
				Collections.sort(orderStatusList, ORDER_STATUS_WEB_ORDER_NUMBER_COMPARE);
			} else if (sortField.equals("orderdate")) {
				Collections.sort(orderStatusList, ORDER_STATUS_ORDER_DATE_COMPARE);
			} else if (sortField.equals("sitename")) {
				Collections.sort(orderStatusList, ORDER_STATUS_SITE_NAME_COMPARE);
//				*//       } else if (sortField.equals("zipcode")) {
//				*//	    Collections.sort(orderStatusList, ORDER_STATUS_ZIP_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(orderStatusList, ORDER_STATUS_STATUS_COMPARE);
			} else if (sortField.equals("method")) {
				Collections.sort(orderStatusList, ORDER_STATUS_METHOD_COMPARE);
			}
		}


		/**
		 *  <code>sort</code>
		 *
		 *@param  orderStatusList        a <code>OrderStatusDescDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderStatusDescDataVector orderStatusList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("acctname")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_ACCT_NAME_COMPARE);
			} else if (sortField.equals("distname")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_DIST_NAME_COMPARE);
			} else if (sortField.equals("erpordernum")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_ERP_ORDER_NUMBER_COMPARE);
			} else if (sortField.equals("refordernum")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_REF_NUM_COMPARE);
			} else if (sortField.equals("webordernum")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_WEB_ORDER_NUMBER_COMPARE);
			} else if (sortField.equals("orderdate")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_ORDER_DATE_COMPARE);
			} else if (sortField.equals("shipdate")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_SHIP_DATE_COMPARE);
			} else if (sortField.equals("custponum")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_CUST_PO_NUMBER_COMPARE);
			} else if (sortField.equals("sitename")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_SITE_NAME_COMPARE);
			} else if (sortField.equals("zipcode")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_ZIP_COMPARE);
			} else if (sortField.equals("sitecity")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_CITY_COMPARE);
			} else if (sortField.equals("sitestate")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_STATE_COMPARE);
			} else if (sortField.equals("status")) {
				throw new Exception("Sort by status is not supported in this method. Use method sort(request, orderStatusList, sortField)");
			} else if (sortField.equals("address")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_ADDRESS_COMPARE);
			} else if (sortField.equals("method")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_METHOD_COMPARE);
			} else if (sortField.equals("exceptiontype")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_EXCEPTION_TYPE_COMPARE);
			} else if (sortField.equals("ordertype")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_ORDER_TYPE_COMPARE);
			} else if (sortField.equals("amount")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_AMOUNT_COMPARE);
			}else if (sortField.equals("placedby")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_PLACEDBY_COMPARE);
			} else if (sortField.equals("orderdateRevision")) {
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_ORDER_DATE_WITH_REVISION_COMPARE);
			}
		}


                 /**
                 *  <code>sort</code>
                 *
                 *@param  orderStatusList        a <code>OrderStatusDescDataVector</code> list
                 *@param  sortField    String specifying field to sort on
                 *@exception  Exception  if an error occurs
                 */
                public static synchronized void sortAdmin(HttpServletRequest request, OrderStatusDescDataVector orderStatusList, String sortField)
                throws Exception {

                        if (sortField == null) {
                                return;
                        }
                        if (sortField.equals("status")) {
                                DisplayListSortMyFax.setRequestObject(request);
                                Collections.sort(orderStatusList, ORDER_STATUS_DESC_STATUS_ADMIN_COMPARE);
                        }else if(sortField.equals("orderdateDesc")){
                                Collections.sort(orderStatusList,ORDER_STATUS_DESC_DATE_DESC_COMPARE);
                        } else {
                                sort(orderStatusList, sortField);
                        }
                }

		/**
		 *  <code>sort</code>
		 *
		 *@param  orderStatusList        a <code>OrderStatusDescDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static synchronized void sort(HttpServletRequest request, OrderStatusDescDataVector orderStatusList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}
			if (sortField.equals("status")) {
				DisplayListSortMyFax.setRequestObject(request);
				Collections.sort(orderStatusList, ORDER_STATUS_DESC_STATUS_COMPARE);
			}else if(sortField.equals("orderdateDesc")){
				Collections.sort(orderStatusList,ORDER_STATUS_DESC_DATE_DESC_COMPARE);
			} else {
				sort(orderStatusList, sortField);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  list  a <code>PoStatusDescDataViewVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(PurchaseOrderStatusDescDataViewVector list, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}
			if (sortField.equals("acctname") || sortField.equals("accountname")) {
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_ACCOUNT_NAME_COMPARE);
			} else if (sortField.equals("distributorname")) {
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_DISTRIBUTOR_NAME_COMPARE);
			} else if (sortField.equals("erpordernum")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_ERP_ORDER_NUM_COMPARE);
			} else if (sortField.equals("webordernum")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_WEB_ORDER_NUM_COMPARE);
			} else if (sortField.equals("podate")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_PO_DATE_COMPARE);
			} else if (sortField.equals("erpponum")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_ERP_PO_NUM_COMPARE);
			} else if (sortField.equals("erp_porefnum")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_ERP_PO_REF_NUM_COMPARE);
			} else if (sortField.equals("erp_porefdate")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_ERP_PO_REF_DATE_COMPARE);
			} else if (sortField.equals("sitename")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_SITE_NAME_COMPARE);
			} else if (sortField.equals("zipcode")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_POSTAL_CODE_COMPARE);
			} else if (sortField.equals("status")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_PURCHASE_ORDER_STATUS_COMPARE);
			} else if (sortField.equals("method")){
				Collections.sort(list, PURCHASE_ORDER_STATUS_DESC_METHOD_COMPARE);
			} else {
				log.info("DisplayListSortMyFax: Unsupported sort: " + sortField +
				" PurchaseOrderStatusDescDataViewVector sort");
			}
		}



		/**
		 *  <code>sort</code>
		 *
		 *@param  orderItemStatusList        a <code>OrderItemDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderItemDataVector orderItemStatusList, String sortField)
		{

			if (sortField == null) {
				return;
			}

			if (sortField.equals("erpPoLineNum")) {
				Collections.sort(orderItemStatusList, ORDER_ITEM_ERP_PO_LINE_NUM_COMPARE);
			} else if (sortField.equals("orderLineNum")) {
				Collections.sort(orderItemStatusList, ORDER_ITEM_ORDER_LINE_NUM_COMPARE);
			} else{
				log.info("unsupported sort field " + sortField);
			}
		}


		/**
		 *  <code>sort</code>
		 *
		 *@param  orderItemDescList        a <code>OrderItemDescDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderItemDescDataVector orderItemDescList, String sortField)
		{

			if (sortField == null) {
				return;
			}

			if (sortField.equals("distOrderNum")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_DIST_ORDER_NUM_COMPARE);
			} else if (sortField.equals("erpPoNum")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_ERP_PO_NUM_COMPARE);
			} else if (sortField.equals("outboundPoNum")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_OUTBOUND_PO_NUM_COMPARE);
			} else if (sortField.equals("erpPoLineNum")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_ERP_PO_LINE_NUM_COMPARE);
			} else if (sortField.equals("cwSKU")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_CW_SKU_NUMBER_COMPARE);
			} else if (sortField.equals("distSKU")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_DIST_SKU_NUMBER_COMPARE);
			} else if (sortField.equals("name")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_PRODUCT_NAME_COMPARE);
			} else if (sortField.equals("orderLineNum")) {
				Collections.sort(orderItemDescList, ORDER_ITEM_DESC_ORDER_LINE_NUM_COMPARE);
			} else {
				log.info("Unsupported sort field: " + sortField);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  invoiceDistDetailDescList        a <code>InvoiceDistDetailDescDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(InvoiceDistDetailDescDataVector invoiceDistDetailDescList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("distOrderNum")) {
				Collections.sort(invoiceDistDetailDescList, INVOICE_DIST_DETAIL_DESC_DIST_ORDER_NUM_COMPARE);
			} else if (sortField.equals("erpPoNum")) {
				Collections.sort(invoiceDistDetailDescList, INVOICE_DIST_DETAIL_DESC_ERP_PO_NUM_COMPARE);
			} else if (sortField.equals("cwSKU")) {
				Collections.sort(invoiceDistDetailDescList, INVOICE_DIST_DETAIL_DESC_CW_SKU_NUMBER_COMPARE);
			} else if (sortField.equals("distSKU")) {
				Collections.sort(invoiceDistDetailDescList, INVOICE_DIST_DETAIL_DESC_DIST_SKU_NUMBER_COMPARE);
			} else if (sortField.equals("name")) {
				Collections.sort(invoiceDistDetailDescList, INVOICE_DIST_DETAIL_DESC_PRODUCT_NAME_COMPARE);
			}
		}


		/**
		 *  <code>sort</code>
		 *
		 *@param  list        a <code>InvoiceCustDetailDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(InvoiceCustDetailDataVector list, String sortField){
			if (sortField == null) {
				return;
			}

			if (sortField.equals("lineNum")) {
				Collections.sort(list, INVOICE_CUST_DETAIL_DIST_LINE_NUM_COMPARE);
			}

		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  invoiceCustDetailDescList        a <code>InvoiceCustDetailDescDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(InvoiceCustDetailDescDataVector invoiceCustDetailDescList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("distOrderNum")) {
				Collections.sort(invoiceCustDetailDescList, INVOICE_CUST_DETAIL_DESC_DIST_ORDER_NUM_COMPARE);
			} else if (sortField.equals("erpPoNum")) {
				Collections.sort(invoiceCustDetailDescList, INVOICE_CUST_DETAIL_DESC_ERP_PO_NUM_COMPARE);
			} else if (sortField.equals("cwSKU")) {
				Collections.sort(invoiceCustDetailDescList, INVOICE_CUST_DETAIL_DESC_CW_SKU_NUMBER_COMPARE);
			} else if (sortField.equals("distSKU")) {
				Collections.sort(invoiceCustDetailDescList, INVOICE_CUST_DETAIL_DESC_DIST_SKU_NUMBER_COMPARE);
			} else if (sortField.equals("name")) {
				Collections.sort(invoiceCustDetailDescList, INVOICE_CUST_DETAIL_DESC_PRODUCT_NAME_COMPARE);
			}
		}


		/**
		 *  <code>sort</code>
		 *
		 *@param  orderPropertyDataList       a <code>OrderPropertyDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderPropertyDataVector orderPropertyDataList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("propertyId")) {
				Collections.sort(orderPropertyDataList, ORDER_PROPERTY_DATA_PROPERTY_ID_COMPARE);
			}
		}


		/**
		 *  <code>sort</code>
		 *
		 *@param  orderHistoryData        a <code>OrderHistoryDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(OrderHistoryDataVector orderHistoryData, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			/*
	  if (sortField.equals("dateordered")) {
          Collections.sort(orderHistoryData, ORDER_HISTORY_ORDER_DATE_COMPARE);
	  }else if (sortField.equals("erpordernum")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_ERP_PO_NUMBER_COMPARE);
      }else if (sortField.equals("locname")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_SITE_NAME_COMPARE);
      }else if (sortField.equals("city")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_CITY_COMPARE);
      }else if (sortField.equals("state")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_STATE_COMPARE);
      }else if (sortField.equals("zipcode")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_ZIP_COMPARE);
      }else if (sortField.equals("cwsku")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_CW_SKU_NUMBER_COMPARE);
      }else if (sortField.equals("distname")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_DIST_NAME_COMPARE);
      }else if (sortField.equals("distsku")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_DIST_SKU_NUMBER_COMPARE);
      }else if (sortField.equals("lastshipdate")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_SHIP_DATE_COMPARE);
      }else if (sortField.equals("acctname")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_ACCT_NAME_COMPARE);
      }else if (sortField.equals("erpponum")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_ERP_PO_NUMBER_COMPARE);
      }else if (sortField.equals("webordernum")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_WEB_ORDER_NUMBER_COMPARE);
      }else if (sortField.equals("custponum")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_CUST_PO_NUMBER_COMPARE);
      }else if (sortField.equals("zipcode")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_ZIP_COMPARE);
      }else if (sortField.equals("method")) {
           Collections.sort(orderHistoryData, ORDER_HISTORY_METHOD_COMPARE);
      }
			 */

		}

		// Workflow sorting.
		static final Comparator WORKFLOW_NAME_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String n1 =((WorkflowData)o1).getShortDesc();
				String n2 =((WorkflowData)o2).getShortDesc();
				return Utility.compareToIgnoreCase(n1, n2);
			}
		};

		static final Comparator WORKFLOW_TYPE_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String v1 = ((WorkflowData)o1).getWorkflowTypeCd();
				String v2 = ((WorkflowData)o2).getWorkflowTypeCd();
				return Utility.compareToIgnoreCase(v1, v2);
			}
		};

		static final Comparator WORKFLOW_STATUS_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String v1 = ((WorkflowData)o1).getWorkflowStatusCd();
				String v2 = ((WorkflowData)o2).getWorkflowStatusCd();
				return Utility.compareToIgnoreCase(v1, v2);
			}
		};

		static final Comparator WORKFLOW_ID_COMPARE = new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int id1 = ((WorkflowData)o1).getWorkflowId();
				int id2 = ((WorkflowData)o2).getWorkflowId();
				return id1 - id2;
			}
		};


		public static void sort(WorkflowDataVector v,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("name")) {
				Collections.sort(v, WORKFLOW_NAME_COMPARE);
			} else if (sortField.equals("id")) {
				Collections.sort(v, WORKFLOW_ID_COMPARE);
			} else if (sortField.equals("type")) {
				Collections.sort(v, WORKFLOW_TYPE_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(v, WORKFLOW_STATUS_COMPARE);
			}
		}



		/**
		 *  <code>sort</code>
		 *
		 *@param  callDescList        a <code>CallDescDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(CallDescDataVector callDescList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("acctname")) {
				Collections.sort(callDescList, CALL_DESC_ACCT_NAME_COMPARE);
			} else if (sortField.equals("sitename")) {
				Collections.sort(callDescList, CALL_DESC_SITE_NAME_COMPARE);
			} else if (sortField.equals("contactname")) {
				Collections.sort(callDescList, CALL_DESC_CONTACT_NAME_COMPARE);
			} else if (sortField.equals("calldesc")) {
				Collections.sort(callDescList, CALL_DESC_LONG_DESC_COMPARE);
			} else if (sortField.equals("city")) {
				Collections.sort(callDescList, CALL_DESC_SITE_CITY_COMPARE);
			} else if (sortField.equals("state")) {
				Collections.sort(callDescList, CALL_DESC_SITE_STATE_COMPARE);
			} else if (sortField.equals("zipcode")) {
				Collections.sort(callDescList, CALL_DESC_SITE_ZIP_COMPARE);
			} else if (sortField.equals("opendate")) {
				Collections.sort(callDescList, CALL_DESC_OPENED_DATE_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(callDescList, CALL_DESC_STATUS_COMPARE);
			} else if (sortField.equals("openedbyname")) {
				Collections.sort(callDescList, CALL_DESC_OPENED_BY_COMPARE);
			} else if (sortField.equals("assignedtoname")) {
				Collections.sort(callDescList, CALL_DESC_ASSIGNED_TO_COMPARE);
			}
		}


		/**
		 *  <code>sort</code>
		 *
		 *@param  KnowledgeDescList        a <code>KnowledgeDescDataVector</code> list
		 *@param  sortField    String specifying field to sort on
		 *@exception  Exception  if an error occurs
		 */
		public static void sort(KnowledgeDescDataVector knowledgeDescList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("category")) {
				Collections.sort(knowledgeDescList, KNOWLEDGE_DESC_CATEGORY_COMPARE);
			} else if (sortField.equals("productname")) {
				Collections.sort(knowledgeDescList, KNOWLEDGE_DESC_PRODUCT_NAME_COMPARE);
			} else if (sortField.equals("sku")) {
				Collections.sort(knowledgeDescList, KNOWLEDGE_DESC_SKU_NUM_COMPARE);
			} else if (sortField.equals("desc")) {
				Collections.sort(knowledgeDescList, KNOWLEDGE_DESC_LONG_DESC_COMPARE);
			} else if (sortField.equals("date")) {
				Collections.sort(knowledgeDescList, KNOWLEDGE_DESC_OPENED_DATE_COMPARE);
			} else if (sortField.equals("status")) {
				Collections.sort(knowledgeDescList, KNOWLEDGE_DESC_STATUS_COMPARE);
			} else if (sortField.equals("openedby")) {
				Collections.sort(knowledgeDescList, KNOWLEDGE_DESC_USER_NAME_COMPARE);
			}
		}

		/**
		 *  <code>sort</code>
		 *
		 *@param  callDescList a <code>GenericReportDataVector</code> list
		 *@param  sortField String specifying field to sort on
		 *@exception  Exception if an error occurs
		 */
		public static void sort(GenericReportDataVector pList, String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("id")) {
				Collections.sort(pList, GENERIC_REPORT_ID_COMPARE);
			}else if (sortField.equals("category")) {
				Collections.sort(pList, GENERIC_REPORT_CATEGORY_COMPARE);
			}else if (sortField.equals("name")) {
				Collections.sort(pList, GENERIC_REPORT_NAME_COMPARE);
			}
		}


		static final Comparator OS_ORDER_NUM_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getOrderNum();
				String s2 = ((OrderSiteSummaryData)o2).getOrderNum();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator OS_ORDER_DATE_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date v1 = ((OrderSiteSummaryData)o1).getOrderDate();
				if( null == v1 ) v1 = new Date();
				Date v2 = ((OrderSiteSummaryData)o2).getOrderDate();
				if( null == v2 ) v2 = new Date();
				return v1.compareTo(v2);
			}
		};

		static final Comparator OS_SITE_DESC_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getSiteDesc();
				String s2 = ((OrderSiteSummaryData)o2).getSiteDesc();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator OS_SITE_CITY_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getSiteCity();
				String s2 = ((OrderSiteSummaryData)o2).getSiteCity();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator OS_SITE_STATE_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getSiteState();
				String s2 = ((OrderSiteSummaryData)o2).getSiteState();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator OS_SITE_ZIP_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getSitePostalCode();
				String s2 = ((OrderSiteSummaryData)o2).getSitePostalCode();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator OS_PO_NUM_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getPoNum();
				String s2 = ((OrderSiteSummaryData)o2).getPoNum();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator OS_COST_CENTER_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getCostCenterDesc();
				String s2 = ((OrderSiteSummaryData)o2).getCostCenterDesc();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator OS_AMOUNT_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				java.math.BigDecimal v1 =
					((OrderSiteSummaryData)o1).getAmount();
				java.math.BigDecimal v2 =
					((OrderSiteSummaryData)o2).getAmount();

				return v1.compareTo(v2);
			}
		};

		static final Comparator OS_COMMENTS_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((OrderSiteSummaryData)o1).getComments();
				String s2 = ((OrderSiteSummaryData)o2).getComments();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};


		public static void sort(OrderSiteSummaryDataVector v,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("orderNum")) {
				Collections.sort(v, OS_ORDER_NUM_COMPARE);
			} else if (sortField.equals("orderDate")) {
				Collections.sort(v, OS_ORDER_DATE_COMPARE);
			} else if (sortField.equals("siteDesc")) {
				Collections.sort(v, OS_SITE_DESC_COMPARE);
			} else if (sortField.equals("siteCity")) {
				Collections.sort(v, OS_SITE_CITY_COMPARE);
			} else if (sortField.equals("siteState")) {
				Collections.sort(v, OS_SITE_STATE_COMPARE);
			} else if (sortField.equals("siteZip")) {
				Collections.sort(v, OS_SITE_ZIP_COMPARE);
			} else if (sortField.equals("poNum")) {
				Collections.sort(v, OS_PO_NUM_COMPARE);
			} else if (sortField.equals("costCenterDesc")) {
				Collections.sort(v, OS_COST_CENTER_COMPARE);
			} else if (sortField.equals("amount")) {
				Collections.sort(v, OS_AMOUNT_COMPARE);
			} else if (sortField.equals("comments")) {
				Collections.sort(v, OS_COMMENTS_COMPARE);
			}

		}

		static final Comparator INV_DD_ERP_PO_NUM_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((InvoiceDistData)o1).getErpPoNum();
				String s2 = ((InvoiceDistData)o2).getErpPoNum();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};
		static final Comparator INV_DD_INV_NUM_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((InvoiceDistData)o1).getInvoiceNum();
				String s2 = ((InvoiceDistData)o2).getInvoiceNum();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator INV_DD_INV_DATE_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date v1 = ((InvoiceDistData)o1).getInvoiceDate();
				if( null == v1 ) v1 = new Date();
				Date v2 = ((InvoiceDistData)o2).getInvoiceDate();
				if( null == v2 ) v2 = new Date();
				return v1.compareTo(v2);
			}
		};

		static final Comparator INV_DD_ADD_DATE_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date v1 = ((InvoiceDistData)o1).getAddDate();
				if( null == v1 ) v1 = new Date();
				Date v2 = ((InvoiceDistData)o2).getAddDate();
				if( null == v2 ) v2 = new Date();
				return v1.compareTo(v2);
			}
		};

		static final Comparator INV_DD_INV_STATUS_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((InvoiceDistData)o1).getInvoiceStatusCd();
				String s2 = ((InvoiceDistData)o2).getInvoiceStatusCd();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator INV_DD_INV_BUS_ENTITY_ID_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int id1 = ((InvoiceDistData)o1).getBusEntityId();
				int id2 = ((InvoiceDistData)o2).getBusEntityId();
				return id1 - id2;
			}
		};

		static final Comparator INV_DD_INV_SUBTOTAL_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				BigDecimal amt1 = ((InvoiceDistData)o1).getSubTotal();
				BigDecimal amt2 = ((InvoiceDistData)o2).getSubTotal();
				if(amt1 == null){amt1=new BigDecimal(0);}
				if(amt2 == null){amt2=new BigDecimal(0);}
				return amt1.compareTo(amt2);
			}
		};

		static final Comparator INV_CD_CIT_TRANSACTION_DATE_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date v1 = ((InvoiceCustData)o1).getCitTransactionDate();
				if( null == v1 ) v1 = new Date();
				Date v2 = ((InvoiceCustData)o2).getCitTransactionDate();
				if( null == v2 ) v2 = new Date();
				return v1.compareTo(v2);
			}
		};
		static final Comparator INV_CD_CIT_ASSIGNMENT_NUM_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ""+((InvoiceCustData)o1).getCitAssignmentNumber();
				String s2 = ""+((InvoiceCustData)o2).getCitAssignmentNumber();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator INV_CD_INV_NUM_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((InvoiceCustData)o1).getInvoiceNum();
				String s2 = ((InvoiceCustData)o2).getInvoiceNum();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator INV_CD_INV_DATE_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				Date v1 = ((InvoiceCustData)o1).getInvoiceDate();
				if( null == v1 ) v1 = new Date();
				Date v2 = ((InvoiceCustData)o2).getInvoiceDate();
				if( null == v2 ) v2 = new Date();
				return v1.compareTo(v2);
			}
		};

		static final Comparator INV_CD_CIT_STATUS_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((InvoiceCustData)o1).getCitStatusCd();
				String s2 = ((InvoiceCustData)o2).getCitStatusCd();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator GENERIC_REPORT_ID_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				int i1 = ((GenericReportData)o1).getGenericReportId();
				int i2 = ((GenericReportData)o2).getGenericReportId();
				return i1 - i2;
			}
		};

		static final Comparator GENERIC_REPORT_NAME_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((GenericReportData)o1).getName();
				String s2 = ((GenericReportData)o2).getName();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		static final Comparator GENERIC_REPORT_CATEGORY_COMPARE =
			new Comparator() {
			public int compare(Object o1, Object o2)
			{
				String s1 = ((GenericReportData)o1).getCategory();
				String s2 = ((GenericReportData)o2).getCategory();
				return Utility.compareToIgnoreCase(s1, s2);
			}
		};

		public static void sort(InvoiceDistDataVector v,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("erpPoNum")) {
				Collections.sort(v, INV_DD_ERP_PO_NUM_COMPARE);
			}
			else if (sortField.equals("invoiceNum")) {
				Collections.sort(v, INV_DD_INV_NUM_COMPARE);
			}
			else if (sortField.equals("invoiceDate")) {
				Collections.sort(v, INV_DD_INV_DATE_COMPARE);
			}
			else if (sortField.equals("addDate")) {
				Collections.sort(v, INV_DD_ADD_DATE_COMPARE);
			}
			else if (sortField.equals("invoiceStatus")) {
				Collections.sort(v, INV_DD_INV_STATUS_COMPARE);
			}
			else if (sortField.equals("subtotal")) {
				Collections.sort(v, INV_DD_INV_SUBTOTAL_COMPARE);
			}
			else if (sortField.equals("busEntityId")) {
				Collections.sort(v, INV_DD_INV_BUS_ENTITY_ID_COMPARE);
			}
		}

		public static void sort(InvoiceCustDataVector v,
				String sortField)
		throws Exception {
			if (sortField == null) {
				return;
			}

			if (sortField.equals("citTransactionDate")) {
				Collections.sort(v, INV_CD_CIT_TRANSACTION_DATE_COMPARE);
			}
			else if (sortField.equals("citAssignmentNumber")) {
				Collections.sort(v, INV_CD_CIT_ASSIGNMENT_NUM_COMPARE);
			}
			else if (sortField.equals("citStatusCd")) {
				Collections.sort(v, INV_CD_CIT_STATUS_COMPARE);
			}
			else if (sortField.equals("invoiceNum")) {
				Collections.sort(v, INV_CD_INV_NUM_COMPARE);
			}
			else if (sortField.equals("invoiceDate")) {
				Collections.sort(v, INV_CD_INV_DATE_COMPARE);
			}

		}


		public static void sort(RemittanceDescViewVector v,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("transactionReference")) {
				Collections.sort(v,REMITTNCE_TRANS_REF_COMPARE);
			}else if(sortField.equals("remittanceStatus")){
				Collections.sort(v,REMITTNCE_STATUS_COMPARE);
			}else if(sortField.equals("erpAccount")){
				Collections.sort(v,REMITTNCE_ERP_ACCOUNT_COMPARE);
			}else if(sortField.equals("paymentReferenceNumber")){
				Collections.sort(v,REMITTNCE_PAY_REF_NUM_COMPARE);
			}else if(sortField.equals("transactionDate")){
				Collections.sort(v,REMITTNCE_TRANS_DATE_COMPARE);
			}else if(sortField.equals("paymentPostDate")){
				Collections.sort(v,REMITTNCE_PAYMENT_POST_COMPARE);
			}else if(sortField.equals("addDate")){
				Collections.sort(v,REMITTNCE_ADD_DATE_COMPARE);
			}else{
				throw new IllegalArgumentException(sortField+" not a valid sort option.");
			}
		}

		public static void sort(RemittanceDetailDescViewVector v,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

			if (sortField.equals("remitDetailId")) {
				Collections.sort(v,REMITTNCE_DETAIL_DESC_REMIT_DETAIL_ID_COMPARE);
			}else if(sortField.equals("invoiceType")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_INVOICE_TYPE_COMPARE);
			}else if(sortField.equals("invoiceNum")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_INVOICE_NUM_COMPARE);
			}else if(sortField.equals("siteRefNum")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_SITE_REF_NUM_COMPARE);
			}else if(sortField.equals("netAmount")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_NET_AMOUNT_COMPARE);
			}else if(sortField.equals("status")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_STATUS_COMPARE);
			}else if(sortField.equals("addDate")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_ADD_DATE_COMPARE);
			}else if(sortField.equals("addBy")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_ADD_BY_COMPARE);
			}else if(sortField.equals("modDate")){
				Collections.sort(v,REMITTNCE_DETAIL_DESC_MOD_DATE_COMPARE);
			}else if(sortField.equals("modBy")){

			}else{
				throw new IllegalArgumentException(sortField+" not a valid sort option.");
			}
		}


		public static void sort(InvoiceDistDetailDataVector v,
				String sortField)
		throws Exception {

			if (sortField == null) {
				return;
			}

		}

		public static void sort(AssetViewVector v, String sortField) {
			if (sortField == null) {
				return;
			}
			if (sortField.equals("assetname")) {
				Collections.sort(v, ASSET_NAME_COMPARE);
			} else if (sortField.equals("assetserial")) {
				Collections.sort(v, ASSET_SERIAL_COMPARE);
			} else if (sortField.equals("assetnumber")) {
				Collections.sort(v, ASSET_NUMBER_COMPARE);
			} else if (sortField.equals("assetcategory")) {
				Collections.sort(v, ASSET_CATEGORY_COMPARE);
			} else if (sortField.equals("assetdateinservice")) {
				Collections.sort(v, ASSET_DATE_IN_SERVICE_COMPARE);
			} else if (sortField.equals("assetsitename")) {
				Collections.sort(v, ASSET_SITE_NAME_COMPARE);
			} 
		}

		public static void sort(CatalogCategoryDataVector v, String sortField) {
			if (sortField == null) {
				return;
			}
			if (sortField.equals("short_desc")) {
				Collections.sort(v, CATALOG_CATEGORY_NAME_COMPARE);
			}
		}

		public static void sort(WorkOrderItemDetailViewVector v,String sortField) {
			if (sortField == null) {
				return;
			}

			if (sortField.equals("sequence")) {
				Collections.sort(v, WORK_ORDER_ITEM_SEQUENCE);
			}
		}

		public static void sort(AssetDataVector v, String sortField ) {
			if (sortField == null) {
				return;
			}
			if (sortField.equals("short_desc")) {
				Collections.sort(v, ASSET_SHORT_DESC_COMPARE);
			}
		}

		public static void sort(BuildingServicesContractorViewVector v, String sortField) {
			if (sortField == null) {
				return;
			}
			if (sortField.equals("short_desc")) {
				Collections.sort(v, BSC_SHORT_DESC_COMPARE);
			}
		}

		public static void sort(BudgetSpendViewVector v, String sortField) {
			if (sortField == null) {
				return;
			}
			if (sortField.equals("cost_center_name")) {
				Collections.sort(v, SPENT_COST_CENTER_NAME_COMPARE);
			}
		}

		public static void sort(BusEntityDataVector v, String sortField) {
			if (sortField == null) {
				return;
			}
			if (sortField.equals("short_desc")) {
				Collections.sort(v, BUS_ENTITY_SHORT_DESC_COMPARE);
			}
		}
                
                public static void sort(WorkOrderSiteNameViewVector v, String sortField) {
			if (sortField == null) {
				return;
			}
                        if (sortField.equals("woSite")) {
                            Collections.sort(v, WORK_ORDER_SITE_COMPARE);
                        } else if (sortField.equals("woNumber")) {
                            Collections.sort(v, WORK_ORDER_NUMBER_COMPARE);
			} else if (sortField.equals("woPoNumber")) {
                            Collections.sort(v, WORK_ORDER_PO_NUMBER_COMPARE);
                        } else if (sortField.equals("woRequestedService")) {
                            Collections.sort(v, WORK_ORDER_REQUESTED_SERVICE_COMPARE);
                        } else if (sortField.equals("woType")) {
                            Collections.sort(v, WORK_ORDER_TYPE_COMPARE);
                        } else if (sortField.equals("woPriority")) {
                            Collections.sort(v, WORK_ORDER_PRIORITY_COMPARE);
                        } else if (sortField.equals("woStatus")) {
                            Collections.sort(v, WORK_ORDER_STATUS_COMPARE);
                        } else if (sortField.equals("woActualStartDate")) {
                            Collections.sort(v, WORK_ORDER_ACTUAL_START_DATE_COMPARE);
                        } else if (sortField.equals("woActualFinishDate")) {
                            Collections.sort(v, WORK_ORDER_ACTUAL_FINISH_DATE_COMPARE);
                        }
		}

}



