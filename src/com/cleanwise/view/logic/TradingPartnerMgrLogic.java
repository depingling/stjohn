package com.cleanwise.view.logic;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.TradingPartnerAssocData;
import com.cleanwise.service.api.value.TradingPartnerAssocDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPartnerFullDescView;
import com.cleanwise.service.api.value.TradingPartnerInfo;
import com.cleanwise.service.api.value.TradingPartnerView;
import com.cleanwise.service.api.value.TradingPartnerViewVector;
import com.cleanwise.service.api.value.TradingProfileConfigData;
import com.cleanwise.service.api.value.TradingProfileConfigDataVector;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingProfileDataVector;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.apps.dataexchange.UIConfiguration;
import com.cleanwise.view.forms.TradingPartnerMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 * 
 * <code>TradingPartnerMgrLogic</code> implements the logic needed to
 * 
 * to manipulate the information objects needed to define a
 * 
 * trading partner.
 * 
 * 
 * 
 * @author Yuriy Kupershmidt
 */

public class TradingPartnerMgrLogic {

	private static final Logger log = Logger
			.getLogger(TradingPartnerMgrLogic.class);

	/**
	 * 
	 * <code> init</code> method.
	 * 
	 * 
	 * 
	 * @param request
	 *            a <code>HttpServletRequest</code> value
	 * 
	 * @param pForm
	 *            an <code>ActionForm</code> value
	 * 
	 * @exception Exception
	 *                if an error occurs
	 */

	public static ActionErrors init(HttpServletRequest request,
			TradingPartnerMgrForm pForm) throws Exception {

		HttpSession session = request.getSession();

		APIAccess factory = new APIAccess();

		ListService lsvc = factory.getListServiceAPI();

		if (session.getAttribute("entity.property.type.vector") == null) {

			RefCdDataVector statusv = lsvc
					.getRefCodesCollection("ENTITY_PROPERTY_TYPE");

			session.setAttribute("entity.property.type.vector", statusv);

		}

		if (session.getAttribute("property.type.cd.vector") == null) {

			RefCdDataVector statusv = lsvc
					.getRefCodesCollection("PROPERTY_TYPE_CD");

			session.setAttribute("property.type.cd.vector", statusv);

		}

		if (session.getAttribute("trading.property.map.cd.vector") == null) {

			RefCdDataVector statusv = lsvc
					.getRefCodesCollection("TRADING_PROPERTY_MAP_CD");

			session.setAttribute("trading.property.map.cd.vector", statusv);

		}

		ActionErrors ae = new ActionErrors();

		return ae;

	}

	/**
	 * 
	 * Adds an option to the list in the form of a RefCdData object. Will not
	 * add it if
	 * 
	 * it is in the passed in @see HashSet pAdded.
	 */

	private static void addOption(List pList, String pOption, HashSet pAdded) {

		if (pAdded == null || pAdded.add(pOption)) {

			RefCdData ref = RefCdData.createValue();

			ref.setRefCd(pOption);

			ref.setValue(pOption);

			pList.add(ref);

		}

	}

	/**
	 * 
	 * Initiailizes the forms TradingProfile property for creating a new profile
	 */

	public static void initNewProfile(HttpServletRequest request,
			TradingPartnerMgrForm pForm) {

		pForm.getTradingProfile().setTradingProfileId(0);

	}

	public static void initFormVariables(HttpServletRequest request,
			TradingPartnerMgrForm pForm) {
		
		ArrayList outClassList = new ArrayList();

		ArrayList inClassList = new ArrayList();

		ArrayList outTransactionOptions = new ArrayList();

		ArrayList inTransactionOptions = new ArrayList();

		String type = pForm.getTradingPartner().getTradingPartnerTypeCd();

		String tradingType = pForm.getTradingPartner().getTradingTypeCd();

		HashSet addedOutOption = new HashSet();

		HashSet addedInOption = new HashSet();
		if (RefCodeNames.TRADING_TYPE_CD.PUNCHOUT.equals(tradingType)){
			addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T850,
					addedInOption);
			addOption(inTransactionOptions,
					RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_LOGON,
					addedInOption);
			addOption(outTransactionOptions,
					RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT,
					addedOutOption);
			
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundUniccoCXmlPunchOutLogon",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundOrangeLakeCXmlPunchOutLogon",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundInmarCXmlPunchOutLogon",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundGCACXmlPunchOutLogon",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundcXMLPunchOutLogon",
					addedInOption);
			
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundcXMLOrder",
					addedInOption);			
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundcXMLPunchOutOrder",
					addedInOption);			
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundInmarcXMLOrder",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundOrangeLakecXMLOrder",
					addedInOption);
			
			addOption(
					outClassList,
					"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrderCXML",
					addedOutOption);
			addOption(
					outClassList,
					"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrderCXMLPollock",
					addedOutOption);
			addOption(
					outClassList,
					"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrderCXMLUnicco",
					addedOutOption);
		}else{
			// non type specific
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundIgnore",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundFlatFile",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundAccountTxt",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundSiteTxt",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundUserTxt",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundItemTxt",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundNSCMasterCatalogLoader",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundNSCMasterAcctLoader",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundNSCSapAccountLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundNSCSapSiteLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundNSCSapCatalogLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundNSCSapPricingLoader",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundDMSIPurchaseOrder",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundDMSISite",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundItemLoaderDMSI",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundXpedxSite",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundXpedxUser",
					addedInOption);
	
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundKranzSiteTxt",
					addedInOption);
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundKranzUserTxt",
					addedInOption);
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundKranzItemTxt",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InvoiceNetworkServices",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.NetworkServicesSiteDelivery",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockSiteDelivery",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockSiteDelivery2",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockZipLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.IPollockOrderGuideLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockBuyListLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockPricingListLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockProprietaryListLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockCatalogAssocLoader",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockCatalogLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockItemLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockUserLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockAccountLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockSiteLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundPollockOrderGuideLoader",
					addedInOption);
	
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundAssetImagesZipLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundJCPenneyParLoader",
					addedInOption);
			addOption(
					inClassList,
					"com.cleanwise.service.apps.dataexchange.Inbound855_Kroger_Peyton",
					addedInOption);
			addOption(inClassList,
					"com.cleanwise.service.apps.dataexchange.InboundcXML856",
					addedInOption);
	
			addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.TFLAT_FILE_IN,
					addedInOption);
	
			addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.TXML_FILE_IN,
					addedInOption);
		}

		if (RefCodeNames.TRADING_TYPE_CD.XML.equals(tradingType)
				|| RefCodeNames.TRADING_TYPE_CD.OTHER.equals(tradingType)) {

			// type specific, Customer

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER.equals(type)

			|| RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE.equals(type)) {

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundcXMLPunchOutLogon",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundUniccoCXmlPunchOutLogon",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundOrangeLakeCXmlPunchOutLogon",
						addedInOption);
				
				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundGCACXmlPunchOutLogon",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundPPGCXmlPunchOutLogon",
						addedInOption);
				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundSodexoCXmlPunchOutLogon",
						addedInOption);
				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundInmarCXmlPunchOutLogon",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundcXMLPunchOutOrder",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundcXMLOrder",
						addedInOption);
				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundInmarcXMLOrder",
						addedInOption);
				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundOrangeLakecXMLOrder",
						addedInOption);
				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundBatchOrder",
						addedInOption);

				addOption(inTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_LOGON,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T850,
						addedInOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrderCXML",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrderCXMLPollock",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrderCXMLUnicco",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPurchaseOrder_DMSI",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundOrderTotalsDiversey",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundProcurementAccountDataXML",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundProcurementSiteDataXML",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundOnHandValueData",
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT,
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TORDER_SEND_TO_EXT_CUST_SYS,
						addedOutOption);
				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TORDER_TOTALS_OUT,
						addedOutOption);
				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TACCOUNT_OUT, addedOutOption);
				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TSITE_OUT, addedOutOption);
				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TON_HAND_VALUE_OUT, addedOutOption);

			}

			// type specific, Store

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE.equals(type)) {

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundRedcoatsInvoice",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundHierarchicalUserAssoc",
						addedInOption);

			}

			// type specific, Distributor

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR.equals(type)) {

				// For inbound

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundXML997",
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T997,
						addedInOption);

				// For outbound

				// addOption(outClassList,
				// "com.cleanwise.service.apps.dataexchange.OutboundPurchaseOrder_DMSI",
				// addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundStanpakPurchaseOrder",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxPurchaseOrder",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundRMSOrderlineXpedxPurchaseOrder",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundWinnDixiePurchaseOrder",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPurchaseOrder50029",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxOrderDashboardCXMLPurchaseOrderCleanwiseFull",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxOrderDashboardCXMLPurchaseOrderFull",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundJd850Xml",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundNetwork850CsvTabPurchaseOrder",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundNetwork850Handler",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundNetwork850Handler2",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundGenericOrderPipeDelimitedV1",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundGenericOrderPipeDelimitedV1_2",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundGenericOrderPipeDelimitedV2",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPollockPurchaseOrder",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundGenericOrderPipeDelimitedCCV2",
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T850,
						addedOutOption);

			}

		}

		if (RefCodeNames.TRADING_TYPE_CD.OTHER.equals(tradingType)) {

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR.equals(type)) {

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundRedcoatsInvoice",
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T810,
						addedInOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundRedcoatsOrder",
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T850,
						addedOutOption);

			}

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE.equals(type)) {

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound810_Xanitos",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundRedcoatsInvoice",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPurchaseOrder_FBG",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundInvoice_FBG",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPurchaseOrder_DMSI",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound856_DMSI",
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T810,
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TORDER_REPORT_XML_OUT,
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TINVOICE_REPORT_XML_OUT,
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TORDER_SEND_TO_EXT_CUST_SYS,
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.T856_SENT, addedOutOption);

			}

		}

		if (RefCodeNames.TRADING_TYPE_CD.EDI.equals(tradingType)
				|| RefCodeNames.TRADING_TYPE_CD.OTHER.equals(tradingType)) {

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE.equals(type)) {

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundHierarchicalUserAssoc",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundHHSBudget",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound810",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound810_JanPak",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.edi.Inbound810NetworkServices",
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T101,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T843,
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundHHSInvoice",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundGCAJDEdwardsInvoice",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound997",
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T810,
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T997,
						addedOutOption);

			}

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION.equals(type)) {

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound850_JD",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundManifest_ParcelDirect",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxOrderDashboardCXMLPurchaseOrderCleanwiseFull",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxOrderDashboardCXMLPurchaseOrderFull",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundJd850Xml",
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TORDER_PROCESSING_OUT,
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TMANIFEST_OUT, addedOutOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.ParcelDirectManifestLoader",
						addedInOption);

				addOption(inTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.MANIFEST_IN, addedInOption);

			}

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER.equals(type)) {

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound850_USPS",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound850_JCP",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.InboundEDIStubEmailer",
						addedInOption);
				addOption(inClassList,
						"com.cleanwise.service.apps.edi.InboundEDIStubFtpFile",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound997",
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T812,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T824,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T850,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T860,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T997,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T101,
						addedInOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound810_CIT",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound810_JCP",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound810_JCP_v4030",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound810_JCI",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound810_USPS",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound810",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound855",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound856",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound856_JCP",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound997",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrder_JCP",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundPunchOutOrderCXML",
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T810,
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T824,
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T855,
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T856,
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T997,
						addedOutOption);

				addOption(outTransactionOptions,
						RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT,
						addedOutOption);

			} else if (RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR
					.equals(type)) {

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound810",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.edi.Inbound810NetworkServices",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound810_FBG",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound810_XPEDX",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound810_JanPak",
						addedInOption);
				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound810_NSC",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound855",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound856",
						addedInOption);
				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound856_V2",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.InboundEDIStubEmailer",
						addedInOption);
				addOption(inClassList,
						"com.cleanwise.service.apps.edi.InboundEDIStubFtpFile",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundXML810",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundXML855",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundXML856",
						addedInOption);

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound997",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.Inbound855_Wegman",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundXpedxCatalogItemLoader",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundXpedxCatalogSiteLoader",
						addedInOption);

				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.Inbound855_StopShop",
						addedInOption);
				addOption(
						inClassList,
						"com.cleanwise.service.apps.dataexchange.InboundAccountInvoicePipeDelimitedV1",
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T810,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T812,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T824,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T855,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T856,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T860,
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T997,
						addedInOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound850",
						addedOutOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound997",
						addedOutOption);

				// STJ-3822
				addOption(
						outClassList,
						"com.cleanwise.service.apps.edi.Outbound850DistributorV2",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.edi.Outbound850DistributorV2_NSC",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound850Pdf",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound850Xls",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound850Clw",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound850Xls_JD_FB",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound850XlsTarget",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxOrderDashboardCXMLPurchaseOrderCleanwiseFull",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxOrderDashboardCXMLPurchaseOrderFull",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundJd850Xml",
						addedOutOption);

				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.Outbound850TxtSafeway",
						addedOutOption);
				addOption(
						outClassList,
						"com.cleanwise.service.apps.dataexchange.OutboundXpedxProMotionOrders",
						addedInOption);

				if (RefCodeNames.TRADING_TYPE_CD.OTHER.equals(tradingType)) {

					addOption(
							outClassList,
							"com.cleanwise.service.apps.dataexchange.OutboundStanpakPurchaseOrder",
							addedOutOption);

				}

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T850,
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T824,
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T997,
						addedOutOption);

			}

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER.equals(type)) {

				addOption(inClassList,
						"com.cleanwise.service.apps.edi.Inbound997",
						addedInOption);

				addOption(inTransactionOptions, RefCodeNames.EDI_TYPE_CD.T997,
						addedInOption);

				addOption(outClassList,
						"com.cleanwise.service.apps.edi.Outbound867",
						addedOutOption);

				addOption(outTransactionOptions, RefCodeNames.EDI_TYPE_CD.T867,
						addedOutOption);

			}

		}

		request.getSession().setAttribute("trading.partner.generator",
				outClassList);

		request.getSession()
				.setAttribute("trading.partner.parser", inClassList);

		request.getSession().setAttribute("trading.partner.outTransactionType",
				outTransactionOptions);

		request.getSession().setAttribute("trading.partner.inTransactionType",
				inTransactionOptions);

	}

	// ***********************************************************************

	public static ActionErrors clearDetail(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		TradingPartnerData tradingPartnerD = TradingPartnerData.createValue();

		tradingPartnerD.setSkuTypeCd(RefCodeNames.SKU_TYPE_CD.CLW);

		tradingPartnerD.setTradingPartnerTypeCd

		(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER);

		tradingPartnerD.setTradingPartnerStatusCd

		(RefCodeNames.TRADING_PARTNER_STATUS_CD.LIMITED);

		tradingPartnerD.setTradingTypeCd(RefCodeNames.TRADING_TYPE_CD.FAX);

		pForm.setPurchaseOrderFaxNumber("");

		pForm.setToContactName("");

		pForm.setFromContactName("CRCFAX");

		pForm.setPurchaseOrderDueDays("");

		pForm.setPurchaseOrderFreightTerms("");

		pForm.setTradingPartner(tradingPartnerD);

		pForm.setNewBusEntityIdString("");

		pForm.setNewBusEntityDesc("");

		pForm.setBusEntities(new BusEntityDataVector());

		pForm.setTPAssocies(new TradingPartnerAssocDataVector());

		pForm.setInitializeExistingPos(null);

		TradingProfileData tradingProfileD = TradingProfileData.createValue();

		tradingProfileD.setAuthorizationQualifier("00");

		tradingProfileD.setSecurityInfoQualifier("00");

		tradingProfileD.setInterchangeSenderQualifier("ZZ");

		tradingProfileD.setInterchangeReceiverQualifier("ZZ");

		tradingProfileD.setInterchangeStandardsId("U");

		tradingProfileD.setInterchangeVersionNum("00401");

		tradingProfileD.setInterchangeControlNum(0);

		tradingProfileD.setAcknowledgmentRequested("0");

		pForm.setSegmentTerminator("CR");

		pForm.setElementTerminator("*");

		pForm.setSubElementTerminator("~");

		pForm.setAcknowledgment("");

		tradingProfileD.setTestIndicator("T");

		tradingProfileD.setSegmentTerminator("\n");

		tradingProfileD.setElementTerminator("*");

		tradingProfileD.setSubElementTerminator("~");

		tradingProfileD.setGroupControlNum(0);

		tradingProfileD.setResponsibleAgencyCode("X");

		tradingProfileD.setVersionNum("4010");

		tradingProfileD.setTimeZone("GMT");

		pForm.setTradingProfile(tradingProfileD);

		pForm.setTradingDataExchanges(null);

		pForm.setEmailBodyTemplate("");

		pForm.setEmailFrom("");

		pForm.setEmailSubject("");

		pForm.setEmailTo("");

		String[] ids = TimeZone.getAvailableIDs();

		pForm.setTimeZoneIDs(ids);

		if (request.getSession().getAttribute

		("trading.profile.vector") != null) {

			request.getSession().removeAttribute("trading.profile.vector");

		}

		pForm.setTradingPartnerInfo(new TradingPartnerInfo());

		return ae;

	}

	// ***********************************************************************

	public static ActionErrors clearBusEntity(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		pForm.setBusEntities(new BusEntityDataVector());

		pForm.setTPAssocies(new TradingPartnerAssocDataVector());

		pForm.setNewBusEntityIdString("");

		pForm.setNewBusEntityDesc("");

		return ae;

	}

	// ***********************************************************************

	public static ActionErrors search(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		clearDetail(request, pForm);

		HttpSession session = request.getSession();

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError("error.systemError",
					"No trading partner Ejb pointer"));

			return ae;

		}

		int searchType = pForm.getSearchType();

		String searchPartnerType = pForm.getSearchPartnerType();

		String searchTradingPartnerIdS = pForm.getSearchPartnerId();

		String searchPartnerName = pForm.getSearchPartnerName();

		String searchBusEntityName = pForm.getSearchBusEntityName();

		String searchPartnerStatus = pForm.getSearchPartnerStatus();
		
		String searchTraidingTypeCD = pForm.getSearchTraidingTypeCD();
				
		String searchTradingType = pForm.getSearchTradingType();

		TradingPartnerViewVector tPartnerVV = null;

		// check partner id search string
		int searchPartnerId = 0;
		if (Utility.isSet(searchTradingPartnerIdS)) {
			//STJ-6316 - trim whitespace
			searchTradingPartnerIdS = searchTradingPartnerIdS.trim();
            try {
                searchPartnerId = Integer.parseInt(searchTradingPartnerIdS, 10);
    			//STJ-6315 - make sure value is a positive integer
                if (searchPartnerId <= 0) {
                	throw new Exception("Trading Partner Id must be a positive integer.");
                }
            } catch (Exception e) {
			    ae.add("error",new ActionError("variable.integer.format.error","Trading Partner Id"));
    			return ae;
            }
        }

		try {

			tPartnerVV = tradingPartnerEjb.getTradingPartners(
					searchPartnerType,

					searchBusEntityName,

					searchPartnerId,

					searchPartnerName,

					searchTradingType,

					searchPartnerStatus,
					
					searchTraidingTypeCD,

					searchType);

		} catch (RemoteException exc) {

			ae.add("error",
					new ActionError("error.systemError", exc.getMessage()));

			return ae;

		}

		pForm.setTradingPartners(tPartnerVV);

		return ae;

	}

	// ***********************************************************************

	public static ActionErrors getDetail(HttpServletRequest request,
			TradingPartnerMgrForm pForm) {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		String partnerIdS = request.getParameter("partnerId");
		int partnerId = 0;

		if (null == partnerIdS) {
			partnerId = pForm.getTradingPartner().getTradingPartnerId();
		} else {
			try {
				partnerId = Integer.parseInt(partnerIdS);
			} catch (NumberFormatException exc) {
			}

			if (partnerId <= 0) {
				ae.add("error", new ActionError("error.systemError",
						"Wrong partnerId format: " + partnerIdS));
				return ae;
			}
		}

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);
		if (factory == null) {
			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));
			return ae;
		}

		TradingPartner tradingPartnerEjb = null;
		try {
			tradingPartnerEjb = factory.getTradingPartnerAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			exc.printStackTrace();
			ae.add("error", new ActionError("error.systemError",
					"No trading partner Ejb pointer"));
			return ae;
		}

		TradingPartnerInfo tPartnerI = null;
		TradingProfileDataVector tProfileDV = null;
		TradingProfileConfigDataVector tProfileConfigDV = null;
		try {
			tPartnerI = tradingPartnerEjb.getTradingPartnerInfo(partnerId);
			tProfileConfigDV = tradingPartnerEjb.fetchDataExchanges(partnerId);
			tProfileDV = tradingPartnerEjb
					.getTradingProfileByPartnerId(partnerId);
		} catch (Exception exc) {
			ae.add("error",
					new ActionError("error.systemError", exc.getMessage()));
			return ae;
		}
		setDetail(request, pForm, tPartnerI, tProfileDV, tProfileConfigDV);
		return setDetail(request, pForm, tPartnerI, tProfileDV,
				tProfileConfigDV);
	}

	private static ActionErrors setDetail(HttpServletRequest request,
			TradingPartnerMgrForm pForm, TradingPartnerInfo tPartnerI,
			TradingProfileDataVector tProfileDV,
			TradingProfileConfigDataVector tProfileConfigDV) {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		session.setAttribute("trading.profile.vector", tProfileDV);
		pForm.setTradingDataExchanges(tProfileConfigDV);

		TradingProfileData tProfileD;
		if (tProfileDV.size() > 0) {
			tProfileD = (TradingProfileData) tProfileDV.get(0);
		} else {
			tProfileD = TradingProfileData.createValue();
		}

		pForm.setTradingPartnerInfo(tPartnerI);
		pForm.setTradingPartner(tPartnerI.getTradingPartnerData());
		pForm.setCheckUOM(tPartnerI.isCheckUOM());
		pForm.setCheckAddress(tPartnerI.isCheckAddress());
		pForm.setValidateRefOrderNum(tPartnerI.isValidateRefOrderNum());
		pForm.setValidateCustomerSkuNum(tPartnerI.isValidateCustomerSkuNum());
		pForm.setValidateCustomerItemDesc(tPartnerI
				.isValidateCustomerItemDesc());
		pForm.setProcessInvoiceCredit(tPartnerI.isProcessInvoiceCredit());

		if (Utility.isSet(tPartnerI.getTradingPartnerData()
				.getValidateContractPrice())) {
			pForm.setValidateContractPrice(Utility.isTrue(tPartnerI
					.getTradingPartnerData().getValidateContractPrice()));
		} else {
			pForm.setValidateContractPrice(true);
		}

		pForm.setEmailBodyTemplate(tPartnerI.getEmailBodyTemplate());
		pForm.setEmailFrom(tPartnerI.getEmailFrom());
		pForm.setEmailSubject(tPartnerI.getEmailSubject());
		pForm.setEmailTo(tPartnerI.getEmailTo());
		pForm.setPurchaseOrderFaxNumber(tPartnerI.getPurchaseOrderFaxNumber()
				.getPhoneNum());
		pForm.setToContactName(tPartnerI.getToContactName());
		pForm.setFromContactName(tPartnerI.getFromContactName());
		pForm.setPurchaseOrderDueDays(tPartnerI.getPurchaseOrderDueDays());
		pForm.setPurchaseOrderFreightTerms(tPartnerI
				.getPurchaseOrderFreightTerms());
		pForm.setAllow856Flag(tPartnerI.isAllow856());
		pForm.setAllow856Email(tPartnerI.getAllow856Email());
		pForm.setUseInboundAmountForCostAndPrice(tPartnerI
				.isUseInboundAmountForCostAndPrice());
		pForm.setUsePoLineNumForInvoiceMatch(tPartnerI
				.isUsePoLineNumForInvoiceMatch());
		pForm.setRelaxValidateInboundDuplInvoiceNum(tPartnerI
				.isRelaxValidateInboundDuplInvoiceNum());

		pForm.setTradingProfile(tProfileD);
		BusEntityDataVector busEntities = tPartnerI.getBusEntities();
		TradingPartnerAssocDataVector tpAssocies = tPartnerI.getTPAssocies();
		pForm.setBusEntities(busEntities);
		pForm.setTPAssocies(tpAssocies);

		pForm.setSegmentTerminator(visualizeTerminator(tProfileD
				.getSegmentTerminator()));
		pForm.setElementTerminator(visualizeTerminator(tProfileD
				.getElementTerminator()));
		pForm.setSubElementTerminator(visualizeTerminator(tProfileD
				.getSubElementTerminator()));
		
		if ("1".equals(tProfileD.getAcknowledgmentRequested())) {
			pForm.setAcknowledgment("on");
		} else {
			pForm.setAcknowledgment("");
		}
		return ae;
	}

	public static ActionErrors addBlankProfileMapping(
			HttpServletRequest request,

			TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);

		if (appUser == null) {

			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.APP_USER + " session object"));

			return ae;

		}

		UserData user = appUser.getUser();

		TradingPropertyMapData newMap = TradingPropertyMapData.createValue();

		newMap.setAddBy(user.getUserName());

		pForm.addTradingPropertyMapping(newMap);

		return ae;

	}

	public static ActionErrors updateProfileMapping(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		int profileId = pForm.getTradingProfile().getTradingProfileId();

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);

		if (appUser == null) {

			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.APP_USER + " session object"));

			return ae;

		}

		UserData user = appUser.getUser();

		TradingPropertyMapDataVector selectedCollection =

		getSelectedTradingPropMapCollection(pForm.getTradingPropertyMappings(),
				pForm.getSelectedPropertyMappingIds());

		Iterator it = selectedCollection.iterator();

		while (it.hasNext()) {

			TradingPropertyMapData tpm = (TradingPropertyMapData) it.next();

			if (!Utility.isSet(tpm.getEntityProperty())) {

				if (!RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY
						.equals(tpm.getTradingPropertyMapCode())) {

					String fieldId = "Entity Property";

					ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"variable.empty.error", fieldId));

				}

			} else {

				if (!tpm.getEntityProperty().equals(
						RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE)) {

					if (!Utility.isSet(tpm.getPropertyTypeCd())) {

						String fieldId = "Property";

						if (!tpm.getEntityProperty().equals(
								RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_COLUMN)) {

							fieldId = "Order Column";

						}

						if (!RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY
								.equals(tpm.getTradingPropertyMapCode())) {

							ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
									"variable.empty.error", fieldId));

						}

					}

				}

			}

			if (!Utility.isSet(tpm.getTradingPropertyMapCode())) {

				String fieldId = "Mapping Code";

				ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"variable.empty.error", fieldId));

			}

			if (!Utility.isSet(tpm.getDirection())) {

				String fieldId = "Direction";

				ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"variable.empty.error", fieldId));

			}

			if (!Utility.isSet(tpm.getUseCode())) {

				if (!RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(tpm
						.getTradingPropertyMapCode())) {

					if (!RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY
							.equals(tpm.getTradingPropertyMapCode())) {

						String fieldId = "Use Code";

						ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								"variable.empty.error", fieldId));

					}

				}

			}

			if (!Utility.isSet(tpm.getSetType())) {

				String fieldId = "Set Type";

				ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"variable.empty.error", fieldId));

			}

			/*
			 * if(!Utility.isSet(tpm.getQualifierCode())){
			 * 
			 * String fieldId = "Qualifier";
			 * 
			 * ae.add(ActionErrors.GLOBAL_ERROR,new
			 * ActionError("variable.empty.error",fieldId));
			 * 
			 * }
			 */

			if (!Utility.isSet(tpm.getMandatory())) {

				String fieldId = "Mandatory";

				ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"variable.empty.error", fieldId));

			}

			// ///////////////////////////////////////////

			if (RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY
					.equals(tpm.getTradingPropertyMapCode()))

			{

				if (RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_TOTAL_LENGTH
						.equals(tpm.getEntityProperty()))

				{

					if (Utility.isSet(tpm.getHardValue()))

					{

						try {

							int hardValue = Integer
									.parseInt(tpm.getHardValue());

							if (hardValue <= 1 && hardValue != 0)

							{

								ae.add(ActionErrors.GLOBAL_ERROR,

								new ActionError("error.simpleGenericError",
										"Value must be greater or equal 0 "));

							}

						} catch (NumberFormatException e) {

							String fieldId = "Value";

							ae.add(ActionErrors.GLOBAL_ERROR,

							new ActionError("error.simpleGenericError",
									"Invalid value : " + tpm.getHardValue()));

						}

					}

					else

					{

						String fieldId = "Value";

						ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								"variable.empty.error", fieldId));

					}

				}

				if (RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_CHAR.equals(tpm
						.getEntityProperty()))

				{

					if (tpm.getHardValue().length() > 1)

					{

						ae.add(ActionErrors.GLOBAL_ERROR,

						new ActionError("error.simpleGenericError",
								"The Value must consists of one symbol."));

					}

					else if (Utility.isSet(tpm.getHardValue()))

					{

						try {

							int hardValue = Integer
									.parseInt(tpm.getHardValue());

							if (hardValue >= 1 && hardValue <= 9)

							{

								ae.add(ActionErrors.GLOBAL_ERROR,

										new ActionError(
												"error.simpleGenericError",
												"The Value must consists of one symbol except digits from 1 to 9 "));

							}

						} catch (NumberFormatException e) {

						}

					}

				}

			}

			// //////////////////////////////////////

			tpm.setModBy(user.getUserName());

			tpm.setTradingProfileId(profileId);

		}

		if (!(ae.size() == 0)) {

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

			tradingPartnerEjb
					.updateTradingPropertyMapDataCollection(selectedCollection);

			pForm.setTradingPropertyMappings(tradingPartnerEjb
					.getTradingPropertyMapDataCollection(profileId));

		} catch (Exception exc) {

			exc.printStackTrace();

			ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.systemError", exc.getMessage()));

		}

		return ae;

	}

	/**
	 * 
	 * Converts a list of strings into a refcode data vector suitable for
	 * 
	 * UI display interchangably with a ref code style display vecotr of options
	 */

	private static RefCdDataVector toRefCdDataVector(List list) {

		RefCdDataVector dv = new RefCdDataVector();

		if (list != null) {

			Iterator it2 = list.iterator();

			while (it2.hasNext()) {

				String s = (String) it2.next();

				RefCdData r = RefCdData.createValue();

				r.setValue(s);

				r.setRefCd(s);

				dv.add(r);

			}

		}

		return dv;

	}

	/**
	 * 
	 * Initializes any seesion variables necessary for the configuration of the
	 * 
	 * profile mappings.
	 */

	public static void initProfileMapping(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		HttpSession session = request.getSession();

		String key = "order.data.properties";

		if (session.getAttribute(key) == null) {

			List props = Utility.getJavaBeanProperties(OrderData.class);

			RefCdDataVector dv = toRefCdDataVector(props);

			session.setAttribute(key, dv);

		}

		TradingProfileConfigDataVector configs = pForm
				.getTradingDataExchanges();

		if (configs == null) {

			return;

		}

		Iterator it = configs.iterator();

		while (it.hasNext()) {

			TradingProfileConfigData data = (TradingProfileConfigData) it
					.next();

			if (!Utility.isSet(data.getClassname())) {

				continue;

			}

			try {

				Object obj = Class.forName(data.getClassname()).newInstance();

				if (obj instanceof UIConfiguration) {

					UIConfiguration config = (UIConfiguration) obj;

					List props = Utility.getJavaBeanProperties(config
							.getConfigurableBean());

					RefCdDataVector dv = toRefCdDataVector(props);

					session.setAttribute("properties." + data.getClassname(),
							dv);

				} else if (obj instanceof com.cleanwise.service.apps.dataexchange.InboundFlatFile) {

					if (pForm.getTradingPropertyMapDataVector() == null) {

						continue;

					}

					Iterator mapit = pForm.getTradingPropertyMapDataVector()
							.iterator();

					while (mapit.hasNext()) {

						TradingPartnerMgrForm.TradingPropertyMapDescData aMap = (TradingPartnerMgrForm.TradingPropertyMapDescData) mapit
								.

								next();

						if (aMap.getTradingConfigIdInt() == data
								.getTradingProfileConfigId()
								&&

								RefCodeNames.ENTITY_PROPERTY_TYPE.VALUE_OBJECT_CLASSNAME
										.equals(aMap
												.getTradingPropertyMapData().

												getEntityProperty())) {

							try {

								Class clazz = Class.forName(aMap
										.getTradingPropertyMapData()
										.getHardValue());

								List props = Utility
										.getJavaBeanProperties(clazz);

								RefCdDataVector dv = toRefCdDataVector(props);

								String attrKey = data
										.getTradingProfileConfigId()
										+ ".properties." + data.getClassname();

								session.setAttribute(attrKey, dv);

							} catch (Exception e) {

								log.info("Error: could not setup options for properties of class: "
										+

										aMap.getTradingPropertyMapData()
												.getHardValue());

							}

						}

					}

				}

			} catch (ClassNotFoundException e) {

				log.info("Caught ClassNotFoundException exception for class: "
						+ data.getClassname());

			} catch (InstantiationException e) {

				log.info("Caught InstantiationException exception for class: "
						+ data.getClassname());

			} catch (IllegalAccessException e) {

				log.info("Caught IllegalAccessException exception for class: "
						+ data.getClassname());

			} catch (NoClassDefFoundError e) {

				log.info("Caught NoClassDefFoundError exception for class: "
						+ data.getClassname());

			}

		}

	}

	public static ActionErrors getProfileMapping(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		String s = request.getParameter("profileId");

		int profileId = 0;

		try {

			profileId = Integer.parseInt(s);

		} catch (NumberFormatException exc) {
		}

		if (profileId <= 0) {

			ae.add("error", new ActionError("error.systemError",
					"Wrong profileId format: " + s));

			return ae;

		}

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

			pForm.setTradingPropertyMappings(tradingPartnerEjb
					.getTradingPropertyMapDataCollection(profileId));

			pForm.setTradingProfile(tradingPartnerEjb
					.getTradingProfile(profileId));

		} catch (Exception exc) {

			exc.printStackTrace();

			ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.systemError", exc.getMessage()));

		}

		return ae;

	}

	public static ActionErrors getProfile(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		String s = request.getParameter("profileId");

		int profileId = 0;

		try {

			profileId = Integer.parseInt(s);

		} catch (NumberFormatException exc) {
		}

		if (profileId <= 0) {

			ae.add("error", new ActionError("error.systemError",
					"Wrong profileId format: " + s));

			return ae;

		}

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError("error.systemError",
					"No trading partner Ejb pointer"));

			return ae;

		}

		TradingProfileData tProfileD = null;

		try {

			tProfileD = tradingPartnerEjb.getTradingProfile(profileId);

		} catch (Exception exc) {

			ae.add("error",
					new ActionError("error.systemError", exc.getMessage()));

			return ae;

		}

		pForm.setTradingProfile(tProfileD);

		pForm.setSegmentTerminator(visualizeTerminator(tProfileD
				.getSegmentTerminator()));

		pForm.setElementTerminator(visualizeTerminator(tProfileD
				.getElementTerminator()));

		pForm.setSubElementTerminator(visualizeTerminator(tProfileD
				.getSubElementTerminator()));

		if ("1".equals(tProfileD.getAcknowledgmentRequested())) {

			pForm.setAcknowledgment("on");

		} else {

			pForm.setAcknowledgment("");

		}

		return ae;

	}

	public static ActionErrors deleteProfileMapping(HttpServletRequest request,
			TradingPartnerMgrForm pForm) throws APIServiceAccessException {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);

		if (appUser == null) {

			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.APP_USER + " session object"));

			return ae;

		}

		if (pForm == null)

		{

			ae.add("error", new ActionError("error.systemError",
					"No TradingPartnerMgrForm session object"));

			return ae;

		}

		int[] selectedIds = pForm.getSelectedPropertyMappingIds();

		TradingPartner tradingPartnerBean = factory.getTradingPartnerAPI();

		TradingPropertyMapDataVector collection = pForm
				.getTradingPropertyMappings();

		TradingPropertyMapDataVector selectedCollection = getSelectedTradingPropMapCollection(
				collection, selectedIds);

		Iterator it = selectedCollection.iterator();

		try {

			while (it.hasNext())

			{

				TradingPropertyMapData tpmd = (TradingPropertyMapData) it
						.next();

				tradingPartnerBean.deletePropertyMappingData(tpmd
						.getTradingPropertyMapId());

			}

		} catch (Exception exc) {

			// exc.printStackTrace();

			ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.systemError", exc.getMessage()));

		}

		try {

			int profileId = pForm.getTradingProfile().getTradingProfileId();

			pForm.setTradingPropertyMappings(tradingPartnerBean
					.getTradingPropertyMapDataCollection(profileId));

		} catch (Exception exc) {

			// exc.printStackTrace();

			ae.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"error.systemError", exc.getMessage()));

		}

		return ae;

	}

	private static TradingPropertyMapDataVector getSelectedTradingPropMapCollection(
			TradingPropertyMapDataVector collection, int[] selectedIds) {

		TradingPropertyMapDataVector resultCollection = new TradingPropertyMapDataVector();

		for (int i = 0; i < selectedIds.length; i++)

		{

			Iterator it = collection.iterator();

			while (it.hasNext())

			{

				TradingPropertyMapData tpmd = ((TradingPropertyMapData) it
						.next());

				if (tpmd.getTradingPropertyMapId() == selectedIds[i])

				{

					resultCollection.add(tpmd);

					break;

				}

			}

		}

		return resultCollection;

	}

	public static ActionErrors deleteProfile(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		String s = request.getParameter("profileId");

		int profileId = 0;

		try {

			profileId = Integer.parseInt(s);

		} catch (NumberFormatException exc) {
		}

		if (profileId <= 0) {

			ae.add("error", new ActionError("error.systemError",
					"Wrong profileId format: " + s));

			return ae;

		}

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError("error.systemError",
					"No trading partner Ejb pointer"));

			return ae;

		}

		TradingProfileData tProfileD = null;

		try {
			int tradingPartnerId = pForm.getTradingPartner().getTradingPartnerId();
			TradingProfileConfigDataVector profileConfigs = tradingPartnerEjb.fetchDataExchanges(tradingPartnerId);
			for (int i = 0; i < profileConfigs.size(); i++){
				TradingProfileConfigData config = (TradingProfileConfigData) profileConfigs.get(i);
				if (config.getTradingProfileId()==profileId){
					ae.add("error",
							new ActionError("error.simpleGenericError", "Unable to delete trading profile -- there are data exchanges linked to this profile"));
					return ae;
	            }
			}
			tradingPartnerEjb.deleteProfile(profileId);

		} catch (Exception exc) {

			ae.add("error",
					new ActionError("error.systemError", exc.getMessage()));

			return ae;

		}

		return ae;

	}

	public static ActionErrors deleteDataExchange

	(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		String s = request.getParameter("exchangeId");

		int exchangeId = 0;

		try {

			exchangeId = Integer.parseInt(s);

		} catch (NumberFormatException exc) {
		}

		if (exchangeId <= 0) {

			ae.add("error", new ActionError

			("error.systemError",

			"Wrong exchangeId format: " + s));

			return ae;

		}

		APIAccess factory = (APIAccess)

		session.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",

			"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError

			("error.systemError",

			"No trading partner Ejb pointer"));

			return ae;

		}

		TradingProfileData tProfileD = null;

		try {

			tradingPartnerEjb.deleteDataExchange(exchangeId);

		} catch (Exception exc) {

			ae.add("error", new ActionError("error.systemError",

			exc.getMessage()));

			return ae;

		}

		return ae;

	}

	public static ActionErrors update(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		boolean makeNewProfile = false;

		boolean savePartnerInfo = true;

		if (pForm.getNewBusEntityIdString() != null
				&& pForm.getNewBusEntityIdString().trim().length() > 0) {

			ae = addBusEntity(request, pForm);

			if (ae.size() > 0)
				return ae;

		}

		return updateInfo(request, pForm, savePartnerInfo, makeNewProfile);

	}

	// ***********************************************************************

	public static ActionErrors updateInfo(HttpServletRequest request,

	TradingPartnerMgrForm pForm,

	boolean pSavePartnerInfo,

	boolean pMakeNewProfile) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		APIAccess factory = (APIAccess)

		session.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",

			"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError("error.systemError",

			"No trading partner Ejb pointer"));

			return ae;

		}

		CleanwiseUser appUser = (CleanwiseUser)

		session.getAttribute(Constants.APP_USER);

		if (appUser == null) {

			ae.add("error", new ActionError("error.systemError",

			"No " + Constants.APP_USER +

			" session object"));

			return ae;

		}

		UserData user = appUser.getUser();

		TradingProfileData tradingProfile = null;

		TradingPartnerData tradingPartner = pForm.getTradingPartner();

		int tradingPartnerId = tradingPartner.getTradingPartnerId();

		// validate data based on type codes

		if (RefCodeNames.TRADING_TYPE_CD.FAX.equals(tradingPartner
				.getTradingTypeCd())) {

			if (pForm.getPurchaseOrderFaxNumber() == null ||

			pForm.getPurchaseOrderFaxNumber().trim().equals("")) {

				ae.add("poFax",

				new ActionError("variable.empty.error", "poFax"));

			}

			if (pForm.getToContactName() == null ||

			pForm.getToContactName().trim().equals("")) {

				ae.add("toContactName",

				new ActionError("variable.empty.error", "toContactName"));

			}

			if (pForm.getFromContactName() == null ||

			pForm.getFromContactName().trim().equals("")) {

				ae.add("fromContactName",

				new ActionError("variable.empty.error", "fromContactName"));

			}

			if (pForm.getPurchaseOrderDueDays() == null ||

			pForm.getPurchaseOrderDueDays().trim().equals("")) {

				ae.add("purchaseOrderDueDays",

				new ActionError("variable.empty.error", "purchaseOrderDueDays"));

			}

			if (pForm.getPurchaseOrderFreightTerms() == null ||

			pForm.getPurchaseOrderFreightTerms().trim().equals("")) {

				ae.add("purchaseOrderFreightTerms",

				new ActionError("variable.empty.error",
						"purchaseOrderFreightTerms"));

			}

			if (ae.size() > 0) {

				return ae;

			}

		}

		if (RefCodeNames.TRADING_TYPE_CD.EMAIL.equals(tradingPartner
				.getTradingTypeCd())) {

			if (pForm.getEmailFrom() == null ||

			pForm.getEmailFrom().trim().equals("")) {

				ae.add("emailFrom",

				new ActionError("variable.empty.error", "emailFrom"));

			}

			if (pForm.getEmailTo() == null ||

			pForm.getEmailTo().trim().equals("")) {

				ae.add("emailTo",

				new ActionError("variable.empty.error", "emailTo"));

			}

			if (pForm.getEmailSubject() == null ||

			pForm.getEmailSubject().trim().equals("")) {

				ae.add("emailSubject",

				new ActionError("variable.empty.error", "emailSubject"));

			}

			if (pForm.getEmailBodyTemplate() == null ||

			pForm.getEmailBodyTemplate().trim().equals("")) {

				ae.add("emailBodyTemplate",

				new ActionError("variable.empty.error", "emailBodyTemplate"));

			}

			if (ae.size() > 0) {

				return ae;

			}

		}

		if (RefCodeNames.TRADING_TYPE_CD.EDI.equals(tradingPartner
				.getTradingTypeCd())) {

			if (pForm.getAllow856Flag()
					&& !Utility.isSet(pForm.getAllow856Email())) {

				ae.add("allow856EMail",

				new ActionError("variable.empty.error", "EMail Address"));

			}

			if (ae.size() > 0) {

				return ae;

			}

		}

		if (pForm.isValidateContractPrice()
				&& pForm.isUseInboundAmountForCostAndPrice()) {
			ae.add("validateContractPrice",
					new ActionError(
							"error.simpleGenericError",
							"Cannot select both 'Validate Received Amount' AND 'Use inbound 850 amount for order cost and price'.  Choose one or the other."));
			return ae;
		}

		String busEntityShortDesc = null;

		String shortDesc = tradingPartner.getShortDesc();

		if (shortDesc == null || shortDesc.trim().length() == 0) {

			tradingPartner.setShortDesc(busEntityShortDesc);

		}
				

		// Profile

		boolean initializeExistingPos = false;

		// reset exsisting pos (on create only)

		if ((pForm.getTradingPartner() == null || pForm.getTradingPartner()
				.getTradingPartnerId() == 0) && pSavePartnerInfo) {

			if (Utility.isSet(pForm.getInitializeExistingPos())) {

				if (pForm.getInitializeExistingPos().equalsIgnoreCase("true")) {

					initializeExistingPos = true;

				} else if (pForm.getInitializeExistingPos().equalsIgnoreCase(
						"false")) {

					initializeExistingPos = false;

				} else {

					ae.add("error",

					new ActionError("error.simpleGenericError",

					"Invalid selection criteria for field: Reset Exsisting Pos ("
							+

							pForm.getInitializeExistingPos() + ")"));

				}

			} else {

				if (RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR
						.equals(pForm.getTradingPartner()
								.getTradingPartnerTypeCd())) {

					ae.add("error", new ActionError("variable.empty.error",
							"Reset Exsisting Pos"));

				}

			}

		}

		if (RefCodeNames.TRADING_TYPE_CD.EDI.equals(tradingPartner
				.getTradingTypeCd())
				|| RefCodeNames.TRADING_TYPE_CD.OTHER.equals(tradingPartner
						.getTradingTypeCd())
				|| RefCodeNames.TRADING_TYPE_CD.XML.equals(tradingPartner
						.getTradingTypeCd())
				|| RefCodeNames.TRADING_TYPE_CD.PUNCHOUT.equals(tradingPartner
						.getTradingTypeCd())) {

			tradingProfile = pForm.getTradingProfile();

			// test indicatior depends on partner status

			if (!pSavePartnerInfo) {

				if (RefCodeNames.TRADING_PARTNER_STATUS_CD.ACTIVE
						.equals(tradingPartner.getTradingPartnerStatusCd())) {

					tradingProfile.setTestIndicator("P");

				} else {

					tradingProfile.setTestIndicator("T");

				}
				

				// authorization

				String authorization = tradingProfile.getAuthorization();

				if (!"00".equals(tradingProfile.getAuthorizationQualifier())) {

					if (authorization == null || authorization.trim().length() == 0) {

						ae.add("error",

								new ActionError("error.simpleGenericError",
										"Authorization qualifier contradicts authorization field"));

						return ae;

					}

				} else {

					if (authorization != null && authorization.trim().length() > 0) {

						ae.add("error",

								new ActionError("error.simpleGenericError",
										"Authorization qualifier contradicts authorization field"));

						return ae;

					}

				}

				// Security info

				String password = tradingProfile.getSecurityInfo();

				if (!"00".equals(tradingProfile.getSecurityInfoQualifier())) {

					if (password == null || password.trim().length() == 0) {

						ae.add("error",

								new ActionError("error.simpleGenericError",
										"Security info qualifier contradicts security info field"));

						return ae;

					}

				} else {

					if (password != null && password.trim().length() > 0) {

						ae.add("error",

								new ActionError("error.simpleGenericError",
										"Security info qualifier contradicts security info field"));

						return ae;

					}

				}

				// Terminators

				// segment

				String segmentTerminator = pForm.getSegmentTerminator();

				String term = convertTerminator(segmentTerminator);

				if (term == null) {

					// a null (blank) terminator is allowed,

					// the edi logic will interpret it as CRLF.

					term = "";

				}

				tradingProfile.setSegmentTerminator(term);

				// element

				String elementTerminator = pForm.getElementTerminator();

				term = convertTerminator(elementTerminator);

				if (term == null) {

					ae.add("error", new ActionError("error.simpleGenericError",
							"Wrong element terminator: " + elementTerminator));

					return ae;

				}

				tradingProfile.setElementTerminator(term);

				// sub-element

				String subElementTerminator = pForm.getSubElementTerminator();

				term = convertTerminator(subElementTerminator);

				if (term == null) {

					ae.add("error", new ActionError("error.simpleGenericError",
							"Wrong sub element terminator: " + elementTerminator));

					return ae;

				}

				tradingProfile.setSubElementTerminator(term);

				// acknowledgment

				if ("on".equals(pForm.getAcknowledgment())) {

					tradingProfile.setAcknowledgmentRequested("1");

				} else {

					tradingProfile.setAcknowledgmentRequested("0");

				}
			}
		}

		// update group senders

		if (pSavePartnerInfo) {

			BusEntityDataVector beDV = pForm.getBusEntities();

			TradingPartnerAssocDataVector tpaDV = pForm.getTPAssocies();

			for (int ii = 0; ii < beDV.size(); ii++) {

				BusEntityData beD = (BusEntityData) beDV.get(ii);

				TradingPartnerAssocData tpaD = (TradingPartnerAssocData) tpaDV
						.get(ii);

				String groupSender = request.getParameter("groupSender["
						+ beD.getBusEntityId() + "]");

				if ((RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR.

				equals(pForm.getTradingPartner().getTradingPartnerTypeCd()) &&

				beD.getBusEntityTypeCd().equals(
						RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT))
						&&

						(!Utility.isSet(groupSender))

				) {

					ae.add("error", new ActionError("variable.empty.error",
							"Group Sender"));

					return ae;

				}

				tpaD.setGroupSenderOverride(groupSender);

			}

		}

		if (ae.size() > 0) {

			return ae;

		}

		try {

			if (pSavePartnerInfo) {

				TradingPartnerInfo tpi = null;

				tpi = pForm.getTradingPartnerInfo();

				if (tpi == null) {

					tpi = new TradingPartnerInfo();

				}

				tpi.setTradingPartnerData(tradingPartner);

				tpi.setCheckUOM(pForm.isCheckUOM());

				tpi.setCheckAddress(pForm.isCheckAddress());

				tpi.setValidateRefOrderNum(pForm.isValidateRefOrderNum());

				tpi.setValidateCustomerSkuNum(pForm.isValidateCustomerSkuNum());

				tpi.setValidateCustomerItemDesc(pForm
						.isValidateCustomerItemDesc());

				if (pForm.isValidateContractPrice()) {

					tpi.getTradingPartnerData()
							.setValidateContractPrice("true");

				} else {

					tpi.getTradingPartnerData().setValidateContractPrice(
							"false");

				}

				tpi.setProcessInvoiceCredit(pForm.isProcessInvoiceCredit());

				tpi.getPurchaseOrderFaxNumber().setPhoneNum(
						pForm.getPurchaseOrderFaxNumber());

				tpi.setEmailFrom(pForm.getEmailFrom());

				tpi.setEmailBodyTemplate(pForm.getEmailBodyTemplate());

				tpi.setEmailSubject(pForm.getEmailSubject());

				tpi.setEmailTo(pForm.getEmailTo());

				tpi.setToContactName(pForm.getToContactName());

				tpi.setFromContactName(pForm.getFromContactName());

				tpi.setPurchaseOrderFreightTerms(pForm
						.getPurchaseOrderFreightTerms());

				tpi.setPurchaseOrderDueDays(pForm.getPurchaseOrderDueDays());

				tpi.setInitializeExistingPos(initializeExistingPos);

				tpi.setBusEntities(pForm.getBusEntities());

				tpi.setTPAssocies(pForm.getTPAssocies());

				tpi.setAllow856Flag(pForm.getAllow856Flag());

				tpi.setAllow856Email(pForm.getAllow856Email() == null ? ""
						: pForm.getAllow856Email());

				tpi.setUseInboundAmountForCostAndPrice(pForm
						.isUseInboundAmountForCostAndPrice());
				tpi.setUsePoLineNumForInvoiceMatch(pForm
						.isUsePoLineNumForInvoiceMatch());
				tpi.setRelaxValidateInboundDuplInvoiceNum(pForm
						.isRelaxValidateInboundDuplInvoiceNum());

				tradingPartnerId = tradingPartnerEjb.saveTradingPartnerInfo

				(tpi, user.getUserName());

				tradingPartner.setTradingPartnerId(tradingPartnerId);				

				// get most recent trading profiles and only update testIndicator 
				// if trading partner status changed. The reason not get it from 
				// session is the profile control numbers might have been updated 
				// by edi process during the time select trading partner to update 
				// trading partner
				TradingProfileDataVector tProfileDV =
					tradingPartnerEjb.getTradingProfileByPartnerId
					(tradingPartner.getTradingPartnerId());
				
				if (tProfileDV != null) {
					Iterator it = tProfileDV.iterator();
					while (it.hasNext()) {
						TradingProfileData tp = (TradingProfileData) it.next();
						String testIndicator = RefCodeNames.TRADING_PARTNER_STATUS_CD.ACTIVE.equals(tradingPartner.getTradingPartnerStatusCd()) ? "P" : "T";
						if (!testIndicator.equals(tp.getTestIndicator())) {							
							tp.setTestIndicator(testIndicator);
							tradingPartnerEjb.saveTradingProfile(tradingPartner,
								tp,
								user.getUserName());
						}
					}
				}
			}

			if (pMakeNewProfile) {
				tradingProfile.setTradingProfileId(0);
			}

			if (!pSavePartnerInfo) {

				int newProfileId = tradingPartnerEjb.saveTradingProfile(
						tradingPartner,

						tradingProfile,

						user.getUserName());

				tradingProfile.setTradingProfileId(newProfileId);

			}

			TradingProfileDataVector tProfileDV =

			tradingPartnerEjb.getTradingProfileByPartnerId

			(tradingPartner.getTradingPartnerId());

			session.setAttribute("trading.profile.vector", tProfileDV);

		} catch (RemoteException exc) {

			ae.add("error",
					new ActionError("error.systemError", exc.getMessage()));

			return ae;

		}

		// Find in the list and change name and statue

		TradingPartnerViewVector tPartnerVV = pForm.getTradingPartners();

		if (tPartnerVV != null) {

			for (int ii = 0; ii < tPartnerVV.size(); ii++) {

				TradingPartnerView tPartnerV = (TradingPartnerView) tPartnerVV
						.get(ii);

				if (tradingPartnerId == tPartnerV.getId()) {

					tPartnerV.setShortDesc(tradingPartner.getShortDesc());

					tPartnerV.setStatus(tradingPartner
							.getTradingPartnerStatusCd());

					break;

				}

			}

		}

		return ae;

	}

	public static ActionErrors addProfile(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		boolean makeNewProfile = true;

		boolean savePartnerInfo = false;

		return updateInfo(request, pForm, savePartnerInfo, makeNewProfile);

	}

	public static ActionErrors updateProfile(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		boolean makeNewProfile = false;

		boolean savePartnerInfo = false;

		return updateInfo(request, pForm, savePartnerInfo, makeNewProfile);

	}

	public static ActionErrors updateDataExchange(HttpServletRequest request,
			TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		// get the trading partner EJB

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError("error.systemError",
					"No trading partner Ejb pointer"));

			return ae;

		}

		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);

		if (appUser == null) {

			ae.add("error", new ActionError("error.systemError", "No "
					+ Constants.APP_USER + " session object"));

			return ae;

		}

		UserData user = appUser.getUser();

		int inProfileId = 0;

		int outProfileId = 0;

		if (pForm.getDataExchangeInProfileId() != null) {

			inProfileId = pForm.getDataExchangeInProfileId().intValue();

		}

		if (pForm.getDataExchangeOutProfileId() != null) {

			outProfileId = pForm.getDataExchangeOutProfileId().intValue();

		}

		if (!Utility.isSet(pForm.getDataExchangeClassName())) {

			ae.add("dataExchangeClassName", new ActionError(
					"variable.empty.error", "dataExchangeClassName"));

		}

		if (pForm.getDataExchangeInProfileId() == null) {

			ae.add("dataExchangeInProfileId", new ActionError(
					"variable.empty.error", "dataExchangeInProfileId"));

		} else {

			try {

				inProfileId = pForm.getDataExchangeInProfileId().intValue();

			} catch (NumberFormatException e) {

				ae.add("dataExchangeInProfileId", new ActionError(
						"variable.integer.format.error",
						"dataExchangeInProfileId"));

			}

		}

		if (pForm.getDataExchangeOutProfileId() != null) {

			try {

				outProfileId = pForm.getDataExchangeOutProfileId().intValue();

			} catch (NumberFormatException e) {

				ae.add("dataExchangeOutProfileId", new ActionError(
						"variable.integer.format.error",
						"dataExchangeOutProfileId"));

			}

		}

		if (!Utility.isSet(pForm.getDataExchangeTransactionType())) {

			ae.add("dataExchangeTransactionType", new ActionError(
					"variable.empty.error", "dataExchangeTransactionType"));

		}

		if (!Utility.isSet(pForm.getDataExchangeDirection())) {

			ae.add("dataExchangeDirection", new ActionError(
					"variable.empty.error", "dataExchangeDirection"));

		} else if (!(pForm.getDataExchangeDirection().equals("OUT") || pForm
				.getDataExchangeDirection().equals("IN"))) {

			ae.add("error", new ActionError("error.systemError",
					"Direction must be IN or OUT"));

		}

		if (ae.size() != 0) {

			return ae;

		}

		TradingProfileConfigData config = TradingProfileConfigData
				.createValue();

		config.setClassname(pForm.getDataExchangeClassName());

		config.setDirection(pForm.getDataExchangeDirection());

		config.setIncomingTradingProfileId(inProfileId);

		config.setPattern(pForm.getDataExchangePattern());

		config.setSetType(pForm.getDataExchangeTransactionType());

		if ("IN".equals(pForm.getDataExchangeDirection())) {

			config.setTradingProfileId(inProfileId);

		} else if ("OUT".equals(pForm.getDataExchangeDirection())) {

			config.setTradingProfileId(outProfileId);

		}

		try {

			tradingPartnerEjb.defineDataExchange(config, user.getUserName());

		} catch (Exception e) {

			ae.add("error",
					new ActionError("error.systemError", e.getMessage()));

		}

		// clear out the form variables if everything went well

		if (ae.size() == 0) {

			pForm.setDataExchangeClassName("");

			pForm.setDataExchangeDirection("");

			pForm.setDataExchangeInProfileId(new Integer(0));

			pForm.setDataExchangeOutProfileId(new Integer(0));

			pForm.setDataExchangePattern("");

			pForm.setDataExchangeTransactionType("");

			pForm.setDataExchangeDirection("");

		}

		return ae;

	}

	private static String convertTerminator(String pTerm) {

		if (pTerm == null) {

			return null;

		}

		pTerm = pTerm.trim();

		if (pTerm.length() == 0) {

			return null;

		}

		if (pTerm.length() == 1) {

			return pTerm;

		}

		if (pTerm.equalsIgnoreCase("CR")) {

			return "\r";

		}

		if (pTerm.equalsIgnoreCase("LF")) {

			return "\n";

		}

		if (pTerm.equalsIgnoreCase("TAB")) {

			return "\t";

		}

		if (!pTerm.substring(0, 1).equals("\\") || pTerm.length() == 1) {

			return null;

		}

		String codeS = pTerm.substring(1);

		int code = 0;

		try {

			code = Integer.parseInt(codeS);

		} catch (NumberFormatException exc) {

			return null;

		}

		if (code <= 0) {

			return null;

		}

		char termC = (char) code;

		return "" + termC;

	}

	private static String visualizeTerminator(String pTerm) {

		if (pTerm == null || pTerm.length() == 0) {

			return "";

		}

		String first = pTerm.substring(0, 1);

		char ch = first.toCharArray()[0];

		if (ch > 32) {

			return first;

		}

		if (ch == '\n') {

			return "LF";

		}

		if (ch == '\r') {

			return "CR";

		}

		if (ch == '\t') {

			return "TAB";

		}

		int chi = ch;

		return "\\" + chi;

	}

	// ***********************************************************************

	public static ActionErrors delete(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		TradingPartner tradingPartnerEjb = null;

		try {

			tradingPartnerEjb = factory.getTradingPartnerAPI();

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError("error.systemError",
					"No trading partner Ejb pointer"));

			return ae;

		}

		TradingPartnerData tPartnerD = pForm.getTradingPartner();

		if (tPartnerD == null) {

			ae.add("error", new ActionError("error.systemError",
					"No loaded trading partner data found"));

			return ae;

		}

		int tradingPartnerId = tPartnerD.getTradingPartnerId();

		try {
			TradingProfileConfigDataVector profileConfigs = tradingPartnerEjb.fetchDataExchanges(tradingPartnerId);
			if (profileConfigs!=null && profileConfigs.size()>0){
				ae.add("error",
						new ActionError("error.simpleGenericError", "Unable to delete trading partner -- there are data exchanges linked to this partner"));
				return ae;
            }
			tradingPartnerEjb.deleteTradingPartner(tradingPartnerId);

		} catch (RemoteException exc) {

			ae.add("error",
					new ActionError("error.systemError", exc.getMessage()));

			return ae;

		}

		TradingPartnerViewVector tPartnerVV = pForm.getTradingPartners();

		for (int ii = 0; ii < tPartnerVV.size(); ii++) {

			TradingPartnerView tPartnerV = (TradingPartnerView) tPartnerVV
					.get(ii);

			if (tradingPartnerId == tPartnerV.getId()) {

				tPartnerVV.remove(ii);

				break;

			}

		}

		clearDetail(request, pForm);

		return ae;

	}

	// ***********************************************************************

	public static ActionErrors sort(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		String fieldName = (String) request.getParameter("sortField");

		if (fieldName == null) {

			ae.add("error", new ActionError("error.systemError",
					"No sort field name found"));

			return ae;

		}

		TradingPartnerViewVector tPartnerVV = pForm.getTradingPartners();

		tPartnerVV.sort(fieldName);

		pForm.setTradingPartners(tPartnerVV);

		return ae;

	}

	// ***********************************************************************

	public static ActionErrors addBusEntity(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		APIAccess factory = (APIAccess) session
				.getAttribute(Constants.APIACCESS);

		if (factory == null) {

			ae.add("error", new ActionError("error.systemError",
					"No Ejb access"));

			return ae;

		}

		String busEntityIdS = pForm.getNewBusEntityIdString();

		String type = pForm.getTradingPartner().getTradingPartnerTypeCd();

		BusEntityData busEntityD = null;

		int busEntityId = 0;

		try {

			busEntityId = Integer.parseInt(busEntityIdS);

		} catch (Exception exc) {

			String errorMess = "Incorrect Id format: " + busEntityIdS;

			ae.add("error", new ActionError("error.simpleGenericError",
					errorMess));

			return ae;

		}

		try {

			if (RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER.equals(type)) {

				Account accountEjb = null;

				accountEjb = factory.getAccountAPI();

				AccountData acctD = accountEjb.getAccount(busEntityId, 0);

				busEntityD = acctD.getBusEntity();

			} else if (RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR
					.equals(type)) {

				// String busEntityTypeS = pForm.getNewBusEntityTypeString();

				try {

					Distributor distEjb = null;

					distEjb = factory.getDistributorAPI();

					DistributorData distD = distEjb.getDistributor(busEntityId);

					busEntityD = distD.getBusEntity();

				} catch (Exception e) {

					Account accountEjb = null;

					accountEjb = factory.getAccountAPI();

					AccountData acctD = accountEjb.getAccount(busEntityId, 0);

					busEntityD = acctD.getBusEntity();

				}

			} else if (RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER
					.equals(type)) {

				Manufacturer manEjb = null;

				manEjb = factory.getManufacturerAPI();

				ManufacturerData manD = manEjb.getManufacturer(busEntityId);

				busEntityD = manD.getBusEntity();

			} else if (RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE.equals(type)) {
				try {
					Store storeEjb = null;	
					storeEjb = factory.getStoreAPI();	
					StoreData storeD = storeEjb.getStore(busEntityId);	
					busEntityD = storeD.getBusEntity();					
				} catch (Exception e) {	
					int storeId = Integer.parseInt((String) session.getAttribute("Store.id"));
					Account accountEjb = null;	
					accountEjb = factory.getAccountAPI();	
					AccountData acctD = accountEjb.getAccount(busEntityId, storeId);	
					busEntityD = acctD.getBusEntity();
				}
			}

		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {

			exc.printStackTrace();

			ae.add("error", new ActionError("error.systemError",
					"No Ejb pointer.  Trading partner type: " + type));

			return ae;

		} catch (Exception exc) {

			String errorMess = "No bus entity found. Id: " + busEntityIdS
					+ ".  Trading partner type: " + type;

			ae.add("error", new ActionError("error.simpleGenericError",
					errorMess));

			return ae;

		}

		if (busEntityD == null) {

			String errorMess = "No bus entity. Bus entity id: " + busEntityIdS
					+ ".  Trading partner type: " + type;

			ae.add("error", new ActionError("error.systemError", errorMess));

			return ae;

		}

		BusEntityDataVector beDV = pForm.getBusEntities();

		TradingPartnerAssocDataVector tpaDV = pForm.getTPAssocies();

		boolean foundFl = false;

		for (int ii = 0; ii < beDV.size(); ii++) {

			BusEntityData beD = (BusEntityData) beDV.get(ii);

			if (beD.getBusEntityId() == busEntityD.getBusEntityId()) {

				foundFl = true;

				break;

			}

		}

		if (!foundFl) {

			beDV.add(busEntityD);

			TradingPartnerAssocData tpaD = TradingPartnerAssocData
					.createValue();

			tpaD.setTradingPartnerId(pForm.getTradingPartner()
					.getTradingPartnerId());

			tpaD.setBusEntityId(busEntityD.getBusEntityId());

			tpaD.setTradingPartnerAssocCd(busEntityD.getBusEntityTypeCd());

			String groupSender = request.getParameter("groupSender["
					+ busEntityD.getBusEntityId() + "]");

			tpaD.setGroupSenderOverride(groupSender);

			CleanwiseUser appUser = (CleanwiseUser) session
					.getAttribute(Constants.APP_USER);

			tpaD.setAddBy(appUser.getUserName());

			tpaD.setModBy(appUser.getUserName());

			tpaDV.add(tpaD);

		}

		pForm.setNewBusEntityDesc("");

		pForm.setNewBusEntityIdString("");

		return ae;

	}

	public static ActionErrors deleteBusEntity(HttpServletRequest request,

	TradingPartnerMgrForm pForm) {

		ActionErrors ae = new ActionErrors();

		HttpSession session = request.getSession();

		BusEntityDataVector beDV = pForm.getBusEntities();

		TradingPartnerAssocDataVector tpaDV = pForm.getTPAssocies();

		String idToRemoveS = pForm.getBusEntityIdToDelete();

		int beId = Integer.parseInt(idToRemoveS);

		for (int ii = 0; ii < beDV.size(); ii++) {

			BusEntityData beD = (BusEntityData) beDV.get(ii);

			if (beId == beD.getBusEntityId()) {

				beDV.remove(ii);

				tpaDV.remove(ii);

				break;

			}

		}

		return ae;

	}

	public static ActionErrors exportTradingPartner(HttpServletRequest request,
			HttpServletResponse response, TradingPartnerMgrForm pForm)
			throws Exception {
		ActionErrors ae = new ActionErrors();
		String partnerIdS = request.getParameter("partnerId");

		int partnerId = 0;

		try {
			partnerId = Integer.parseInt(partnerIdS);
		} catch (NumberFormatException exc) {
		}
		if (partnerId <= 0) {
			ae.add("error", new ActionError("error.systemError",
					"Wrong partnerId format: " + partnerIdS));
			return ae;
		}

		APIAccess factory = new APIAccess();
		TradingPartner partnerSvc = factory.getTradingPartnerAPI();
		TradingPartnerFullDescView tpdv = null;
		tpdv = partnerSvc.getTradingPartnerInfoById(partnerId);

		String fileName = "TradingPartnerFullDescView_" + partnerId + ".xml";

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		XMLEncoder encode = new XMLEncoder(response.getOutputStream());
		encode.setPersistenceDelegate(java.sql.Timestamp.class,
				encode.getPersistenceDelegate(java.util.Date.class));
		encode.writeObject(tpdv);
		encode.flush();
		encode.close();
		return null;
	}

	public static ActionErrors importTradingPartner(HttpServletRequest request,
			TradingPartnerMgrForm theForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		FormFile file = theForm.getImportFile();
		if (file == null || !Utility.isSet(file.getFileName())) {
			ae.add("error", new ActionError("error.simpleGenericError",
					"FileName is not set for import"));
			return ae;
		}
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(
				file.getInputStream()));
		TradingPartnerFullDescView tpdv = null;
		try {
			tpdv = (TradingPartnerFullDescView) decoder.readObject();
		} catch (Exception e) {
			ae.add("error", new ActionError("error.simpleGenericError",
					"Failed to read trading partner data from file."));
			return ae;
		} finally {
			decoder.close();
		}

		APIAccess factory = new APIAccess();
		TradingPartner partnerSvc = factory.getTradingPartnerAPI();
		partnerSvc.createNewTradingPartner(tpdv);

		TradingPartnerInfo tpInfo = tpdv.getTradingPartnerInfo();
		tpInfo.setBusEntities(new BusEntityDataVector());
		tpInfo.setTPAssocies(new TradingPartnerAssocDataVector());
		PhoneData faxNumber = tpInfo.getPurchaseOrderFaxNumber();
		if (faxNumber != null) {
			faxNumber.setPhoneId(0);
			faxNumber.setBusEntityId(0);
		}
		ae = setDetail(request, theForm, tpdv.getTradingPartnerInfo(),
				tpdv.getTradingProfileDataVector(),
				tpdv.getTradingProfileConfigDataVector());
		if (ae.size() == 0)
			theForm.setContentPage("tradingPartnerMgrDetail.jsp");
		return ae;
	}
}
