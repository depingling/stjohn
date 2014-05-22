package com.cleanwise.service.api.pipeline;



import java.math.BigDecimal;

import java.sql.Connection;

import java.util.HashMap;

import java.util.Iterator;



import javax.servlet.http.HttpServletRequest;



import com.cleanwise.service.api.APIAccess;

import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;

import com.cleanwise.service.api.dao.PropertyDataAccess;

import com.cleanwise.service.api.session.CatalogInformation;

import com.cleanwise.service.api.session.CatalogInformationBean;

import com.cleanwise.service.api.util.DBCriteria;

import com.cleanwise.service.api.util.PipelineException;

import com.cleanwise.service.api.util.PropertyUtil;

import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.service.api.util.Utility;

import com.cleanwise.service.api.value.CatalogCategoryData;

import com.cleanwise.service.api.value.CatalogCategoryDataVector;

import com.cleanwise.service.api.value.InvoiceDistDetailData;

import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;

import com.cleanwise.service.api.value.OrderItemData;

import com.cleanwise.service.api.value.OrderItemDataVector;

import com.cleanwise.service.api.value.OrderItemDescData;

import com.cleanwise.service.api.value.PropertyData;

import com.cleanwise.service.api.value.PropertyDataVector;

import com.cleanwise.view.forms.StoreVendorInvoiceDetailForm;

import com.cleanwise.view.utils.CleanwiseUser;

import com.cleanwise.view.utils.Constants;



public class InvoiceDistGCAGeneralLedgerLogic implements InvoiceDistPipeline {

	Connection mCon;



	private static HashMap storeCatalogIdsCache = new HashMap();



	private static final HashMap CATEGORY_SUB_ACCOUNT_MAP = new HashMap();
	// this is hard coded and also duplicated in file OutboundGCAJDEdwardsInvoice and 
	// GCAViewLogic. Need to update all three place if changed	
	static{
		CATEGORY_SUB_ACCOUNT_MAP.put("specialty foodservice chemicals","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor equipment","2030");
		CATEGORY_SUB_ACCOUNT_MAP.put("concrete care","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor and window tools","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("waste receptacles","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("safety supplies","10"); // not a top category any more, will leave it as it is
		CATEGORY_SUB_ACCOUNT_MAP.put("glass cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("mops and handles","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("uniforms","(It's own account)");
		CATEGORY_SUB_ACCOUNT_MAP.put("insecticides","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("facial tissue","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("sanitizers","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("vacuums","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("general cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("spray bottles","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("towels","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("miscellaneous accessories","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("mopping equipment","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("wipers","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor care","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("chemical management systems","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("drain cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("hand soap and dispensers","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("toilet tissue","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("laundry","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("carpet care","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("sweepers","2030");
		CATEGORY_SUB_ACCOUNT_MAP.put("dusting accessories","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("matting","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("tape and dispensers","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("polishes","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("disinfectants","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("brooms and brushes","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("window cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor pads","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("feminine hygiene products","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("kitchen cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("janitor carts","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("deodorants and dispensers","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("washroom cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("carpet extractors","2030");
		CATEGORY_SUB_ACCOUNT_MAP.put("liners","20");
		CATEGORY_SUB_ACCOUNT_MAP.put("spin bonnets","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("hand pads and sponges","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("cups, plates, napkins, & plastic utensils","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("seat covers and dispensers","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("industrial cleaners/degreasers","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("vacuum accessories","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("dishwashing","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("clean room supplies","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("lighting","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("safety - required","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("safety supplies (other)","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("gloves and glasses","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("shoes and booties","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("other mandatory safety","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("material handling","10");
	}

	private static boolean isGCAStore(InvoiceDistPipelineBaton pBaton, Connection pCon) {

		try {

			PropertyUtil pru = new PropertyUtil(pCon);

			int storeId = pBaton.getInvoiceDistData().getStoreId();

			if(storeId == 0 && pBaton.getOrder() != null){

				storeId = pBaton.getOrder().getStoreId();

			}

			//String storePrefix = pru.fetchValueIgnoreMissing(0, pBaton.getInvoiceDistData().getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_CODE);

			DBCriteria crit = new DBCriteria();

			crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_CODE);

			crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);

			PropertyDataVector pdv = PropertyDataAccess.select(pCon, crit);

			if(pdv != null && !pdv.isEmpty()){

				PropertyData pd = (PropertyData) pdv.get(0);

				String storePrefix = pd.getValue();

				return "GCA".equals(storePrefix);

			}

			return false;



		} catch (Exception e) {

			e.printStackTrace();

			return false;

		}

	}



	public void process(InvoiceDistPipelineBaton pBaton, Connection pCon,APIAccess pFactory) throws PipelineException {

		try {

			if(pBaton.isInvoiceApproved()){

				//someone has looked at this and the logic layer of the invoice matching will do this categroization so it should

				//already be correct if it went into review.

				return;

			}

			if(!isGCAStore(pBaton, pCon)){

				return;

			}

			InvoiceDistDetailDataVector invItems = pBaton

					.getInvoiceDistDetailDataVector();

			CatalogInformationBean catInfEjb = new CatalogInformationBean();

			int storeId = pBaton.getInvoiceDistData().getStoreId();

			OrderItemDataVector oItms = pBaton.getOrderItems(pCon);

			categorizeInvoiceItems(invItems, catInfEjb, oItms, storeId);







		} catch (Exception e) {

			e.printStackTrace();

			throw new PipelineException(e.getMessage());

		}

	}



	private OrderItemData getOrderItem(InvoiceDistDetailData invItem,

			OrderItemDataVector orderItems) {

		if (orderItems != null && invItem.getOrderItemId() != 0) {

			Iterator it = orderItems.iterator();

			while (it.hasNext()) {

				OrderItemData oi = (OrderItemData) it.next();

				if (oi.getOrderItemId() == invItem.getOrderItemId()) {

					return oi;

				}

			}

		}

		return null;

	}



	private void categorizeInvoiceItems(InvoiceDistDetailDataVector invItems,

			CatalogInformationBean catInfEjb, OrderItemDataVector orderItems,

			int storeId) {

		Iterator it = invItems.iterator();

		while (it.hasNext()) {

			InvoiceDistDetailData iItm = (InvoiceDistDetailData) it.next();

			//iItm.setModBy("gca-"+iItm.getModBy());//XXX delete this line

			OrderItemData ordItm = getOrderItem(iItm, orderItems);

			StringBuffer currErpAccount;

			if (iItm.getErpAccountCode() == null) {

				currErpAccount = new StringBuffer();

			} else {

				currErpAccount = new StringBuffer(iItm.getErpAccountCode());

			}

			char[] allowedTokens = new char[1];

			allowedTokens[0] = '.';

			String newErpAccount = Utility.getFirstToken(currErpAccount,

					allowedTokens);

			if (ordItm != null && ordItm.getOrderItemId() != 0

					&& newErpAccount.indexOf('.') < 0) {

				// user has not set a sub account



				/*

				 * Integer storeId = new

				 * Integer(sForm.getInvoice().getOrderData().getStoreId());

				 * Integer storeCatalogId = (Integer)

				 * storeCatalogIdsCache.get(storeId); if(storeCatalogId ==

				 * null){ storeCatalogId = new

				 * Integer(catInfEjb.getStoreCatalogId(storeId.intValue())); }

				 */

				try {



					// CatalogCategoryDataVector categs =

					// catInfEjb.getCatalogParentCategory(storeCatalogId.intValue(),oItm.getOrderItem().getItemId());

					int itemId = ordItm.getItemId();

					CatalogCategoryDataVector categs = catInfEjb

							.getStoreProduct(storeId, itemId)

							.getCatalogCategories();

					// catInfEjb.getCatalogParentCategoryogChildCategories()

					if (categs != null && !categs.isEmpty()) {

						CatalogCategoryData firstCat = (CatalogCategoryData) categs

								.get(0);

						String[] accountDef = null;

						if (firstCat != null) {

							BigDecimal cost = Utility

									.getBestCostFromInvoiceDistDetail(iItm,

											(BigDecimal) null);

							if (cost == null) {

								cost = ordItm.getDistItemCost();

							}

							if (cost == null) {

								cost = new BigDecimal(0);

							}

							accountDef = getSubAccount(cost, firstCat

									.getCatalogCategoryShortDesc(),

									newErpAccount);

						}



						if (accountDef != null && accountDef[0] != null

								&& accountDef[1] != null) {

							// build the String backwards first

							currErpAccount.insert(0, ' ');

							currErpAccount.insert(0, accountDef[1]);

							currErpAccount.insert(0, '.');

							currErpAccount.insert(0, accountDef[0]);

							iItm.setErpAccountCode(currErpAccount.toString());

						} else {

							// if only the master account is set, but

							// the working invoice doesn't have anything

							// set

							if (accountDef != null && accountDef[0] != null

									&& currErpAccount.length() == 0) {

								iItm.setErpAccountCode(accountDef[0]);

							}

						}

					}

				} catch (Exception e) {

					e.printStackTrace();

				}



			}

		}

	}



	private static String[] getSubAccount(BigDecimal unitPrice,

			String categoryName, String erpAccount) {

		String[] toReturn = new String[2];

		toReturn[0] = null;

		toReturn[1] = null;

		if ("Vacuums".equalsIgnoreCase(categoryName)

				&& unitPrice.compareTo(new BigDecimal(200)) >= 0) {

			// capex...for now do nothing need to know sub account

			erpAccount = "2030";

		} else if ("Vacuums".equalsIgnoreCase(categoryName)) {

			erpAccount = "6310";

		}

		if (categoryName != null) {

			categoryName = categoryName.toLowerCase();

		}

		String capExCheck = (String) CATEGORY_SUB_ACCOUNT_MAP.get(categoryName);

		if ("2030".equals(capExCheck)) {

			erpAccount = capExCheck;

		}

		if (!Utility.isSet(erpAccount)) {

			toReturn[0] = erpAccount;

			return toReturn;

		} else if (erpAccount.indexOf("6310") < 0) {

			toReturn[0] = erpAccount;

			return toReturn;

		}

		toReturn[0] = erpAccount;

		toReturn[1] = (String) CATEGORY_SUB_ACCOUNT_MAP.get(categoryName);

		return toReturn;

	}



}

