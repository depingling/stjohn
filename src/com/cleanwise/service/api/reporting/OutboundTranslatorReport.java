package com.cleanwise.service.api.reporting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Category;

import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
import com.cleanwise.service.api.dao.TradingPartnerAssocDataAccess;
import com.cleanwise.service.api.dao.TradingPartnerDataAccess;
import com.cleanwise.service.api.dao.TradingProfileDataAccess;
import com.cleanwise.service.api.dao.TradingPropertyMapDataAccess;
import com.cleanwise.service.api.session.IntegrationServicesBean;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.TradingPartnerAssocData;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPartnerDescView;
import com.cleanwise.service.api.value.TradingProfileConfigData;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.apps.dataexchange.OutboundTransaction;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;
import com.cleanwise.service.apps.dataexchange.OutboundTranslateReportDriver;

public class OutboundTranslatorReport implements GenericReport {
	private static final Category log = Category.getInstance(OutboundTranslatorReport.class);
	private Date mBeginDate;
	private Date mEndDate;
	private TradingPartnerData mTradingParter;
	private TradingProfileData mTradingProfileData;
	private Connection conn;
	private Integer mOptOrderNum;
	private Integer mOptDistNum;

	/**
	 *Should return a populated GenericReportResultView object. At a minimum
	 * the header should be set so an empty report may be generated to the user.
	 */
	public GenericReportResultView process(ConnectionContainer pCons,
			GenericReportData pReportData, Map pParams) throws Exception {
		conn = pCons.getDefaultConnection();
		mBeginDate = ReportingUtils.getParamAsDate(pParams, "BEG_DATE");
		mEndDate = ReportingUtils.getParamAsDate(pParams, "END_DATE");
		mOptOrderNum = ReportingUtils.getParamAsInteger(pParams, "Order Num");
		mOptDistNum =  ReportingUtils.getParamAsInteger(pParams, "DISTRIBUTOR");
		String setType = (String)ReportingUtils.getParam(pParams, "Set Type");
		String className = (String)ReportingUtils.getParam(pParams, "ClassName");
		Integer tradingProfileId = ReportingUtils.getParamAsInteger(pParams,"TRADING_PROFILE_ID");
		

		OutboundEDIRequestDataVector requests;
		
		mTradingProfileData = TradingProfileDataAccess.select(conn, tradingProfileId);
		mTradingParter = TradingPartnerDataAccess.select(conn, mTradingProfileData.getTradingPartnerId());
		
		String tradingType = mTradingParter.getTradingPartnerTypeCd();
		if (RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR
				.equals(tradingType)) {
			if (RefCodeNames.EDI_TYPE_CD.T850.equalsIgnoreCase(setType)) {
				requests = getDistributorPos();
			} else {
				throw new RemoteException(
						"Unsupported Set Type.  Only 850 supported.");
			}
		} else {
			throw new RemoteException("Unsupported Trading Partner Type.  Only Distributor supported.");
		}
		
		Iterator it = requests.iterator();
		while(it.hasNext()){
			OutboundEDIRequestData ediReq = (OutboundEDIRequestData) it.next();
			ediReq.setIncomingTradingProfileId(mTradingProfileData.getTradingProfileId());
		}
		
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(TradingPropertyMapDataAccess.TRADING_PROFILE_ID,mTradingProfileData.getTradingProfileId());
		dbc.addEqualTo(TradingPropertyMapDataAccess.SET_TYPE, setType);
		dbc.addEqualTo(TradingPropertyMapDataAccess.DIRECTION, "out");//hard coded as reports can only do outbound
		
		TradingPropertyMapDataVector tpmdv = TradingPropertyMapDataAccess.select(conn, dbc);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		OutboundTranslateReportDriver translator = new OutboundTranslateReportDriver();
		translator.setManagingFiles(false);
		
		translator.setTradingPropertyMapDataVector(tpmdv);
		
		translator.setOutboundReqOrderDV(requests);
		translator.setOutputStream(output);
		translator.setPartner(mTradingParter);
		translator.setProfile(mTradingProfileData);
		translator.setSetType(setType);
		translator.setSendParameterMap(null);//??
		
		TradingProfileConfigData config = TradingProfileConfigData.createValue();
		config.setClassname(className);
		config.setDirection("out"); //XXX only works for current setup
		config.setIncomingTradingProfileId(mTradingProfileData.getTradingProfileId());
		config.setSetType(setType);
		config.setTradingProfileId(mTradingProfileData.getTradingProfileId());
		
		TradingPartnerDescView view = new TradingPartnerDescView();
		view.setTradingPartnerData(mTradingParter);
		view.setTradingProfileConfigData(config);
		view.setTradingProfileData(mTradingProfileData);
		view.setTradingPropertyMapDataVector(tpmdv);
		translator.setTradingPartnerDescView(view);
		
		
		translator.translate(requests, null, 0, setType);
		
		String s = new String(output.toByteArray());
		log.info("Result of translation:");
		log.info(s);
		
		GenericReportResultView result = GenericReportResultView.createValue();
		result.setRawOutput(output.toByteArray());
		//result.setRawData
		return result;
	}
	
		

	private OutboundEDIRequestDataVector getDistributorPos() throws Exception{
		IntegrationServicesBean intEjb = new IntegrationServicesBean();
		String setType = RefCodeNames.EDI_TYPE_CD.T850;
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,
				mTradingParter.getTradingPartnerId());
		
		IdVector busEntIds;
		if(mOptDistNum == null || mOptDistNum.intValue() == 0){
			busEntIds = TradingPartnerAssocDataAccess.selectIdOnly(conn,
				TradingPartnerAssocDataAccess.BUS_ENTITY_ID, dbc);
		}else{
			busEntIds = new IdVector();
			busEntIds.add(mOptDistNum);
		}
		BusEntityDataVector dists = BusEntityDataAccess.select(conn,busEntIds);
		


		Iterator it = dists.iterator();
		OutboundEDIRequestDataVector outboundEDIReqDV = new OutboundEDIRequestDataVector();
		while (it.hasNext()) {
			BusEntityData dist = (BusEntityData) it.next();
			String erpNum = dist.getErpNum();
			dbc = new DBCriteria();
			// get distirbutors associated with trading partner:
			// get list of order ids that status is not all PENDING_FULFILLMENT
			// and CANCELED
			List orderItemStatus = new ArrayList();
			orderItemStatus.add(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
			orderItemStatus
					.add(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
			dbc.addEqualTo(OrderItemDataAccess.DIST_ERP_NUM, erpNum);
			dbc.addNotOneOf(OrderItemDataAccess.ORDER_ITEM_STATUS_CD,
					orderItemStatus);
			if(!(mOptOrderNum != null && mOptOrderNum.intValue() != 0)){
				dbc.addGreaterOrEqual(OrderItemDataAccess.ERP_PO_DATE, mBeginDate);
				dbc.addLessOrEqual(OrderItemDataAccess.ERP_PO_DATE, mEndDate);
			}

			String sqlStr = OrderItemDataAccess.getSqlSelectIdOnly(
					OrderItemDataAccess.ORDER_ID, dbc);

			// get list of order that has order status = ERP_RELEASED
			// and distributor's erp number = :erpNum
			// and all the line item has status = PENDING_FULFILLMENT or
			// CANCELED
			dbc = new DBCriteria();
			List orderStatus = new ArrayList();
			orderStatus.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
			orderStatus.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
			orderStatus.add(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM);
			orderStatus.add(RefCodeNames.ORDER_STATUS_CD.SHIPPED);
			dbc.addOneOf(OrderDataAccess.ORDER_STATUS_CD, orderStatus);

			if(mOptOrderNum != null && mOptOrderNum.intValue() != 0){
				dbc.addOneOf(OrderDataAccess.ORDER_NUM, mOptOrderNum.toString());
			}
			dbc.addOneOf(OrderDataAccess.ORDER_ID, sqlStr);

			// dbc.addOrderBy(OrderDataAccess.INCOMING_TRADING_PROFILE_ID);
			log.debug(OrderDataAccess.getSqlSelectIdOnly("*", dbc));
			OrderDataVector orderDV = OrderDataAccess.select(conn, dbc);

			// ???What does this do?
			// orderDV = pickOutOrdersReadyToBeSent(orderDV,conn);
			outboundEDIReqDV.addAll(intEjb.populateOutboundTransactionDataFromOrderDataVector(orderDV, erpNum, conn, setType,true));

			log.debug("SIZE:" + outboundEDIReqDV.size());
			
		}// end iterat over distErpNums

		return outboundEDIReqDV;
	}
}
