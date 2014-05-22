package com.cleanwise.service.api.session;

/**
 * Title:        TradingPartnerBean
 * Description:  Bean implementation for TradingPartner Session Bean
 * Purpose:      Ejb for scheduled orders processing
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.TradingPartnerAssocDataAccess;
import com.cleanwise.service.api.dao.TradingPartnerDataAccess;
import com.cleanwise.service.api.dao.TradingPartnerPropDataAccess;
import com.cleanwise.service.api.dao.TradingProfileConfigDataAccess;
import com.cleanwise.service.api.dao.TradingProfileDataAccess;
import com.cleanwise.service.api.dao.TradingPropertyMapDataAccess;
import com.cleanwise.service.api.framework.MsdsSpecsAPI;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.transformer.Java2ReportItemTransformer;
import com.cleanwise.service.api.tree.transformer.ReportItem2JavaTransformer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PhoneDataVector;
import com.cleanwise.service.api.value.TradingPartnerAssocData;
import com.cleanwise.service.api.value.TradingPartnerAssocDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPartnerDataVector;
import com.cleanwise.service.api.value.TradingPartnerDescView;
import com.cleanwise.service.api.value.TradingPartnerFullDescView;
import com.cleanwise.service.api.value.TradingPartnerInfo;
import com.cleanwise.service.api.value.TradingPartnerPropData;
import com.cleanwise.service.api.value.TradingPartnerPropDataVector;
import com.cleanwise.service.api.value.TradingPartnerView;
import com.cleanwise.service.api.value.TradingPartnerViewVector;
import com.cleanwise.service.api.value.TradingProfileConfigData;
import com.cleanwise.service.api.value.TradingProfileConfigDataVector;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingProfileDataVector;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;

import org.apache.log4j.Logger;

public class TradingPartnerBean extends MsdsSpecsAPI {
    private static final Logger log = Logger.getLogger(TradingPartnerBean.class);
    /**
     *
     */
    public TradingPartnerBean() {}

    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * Gets trading partner object by its id.
     * @param pTradingPartnerId the trading partner id
     * @return  TradingPartnerData object
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerData getTradingPartner(int pTradingPartnerId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        TradingPartnerData tradingPartner = null;
        try {
            con = getConnection();
            tradingPartner =TradingPartnerDataAccess.select(con,pTradingPartnerId);
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.getTradingPartner() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.getTradingPartner() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return tradingPartner;
    }

    /**
     * Gets busness enitity ids associated with the trading partner
     * @param pTradingPartnerId the trading partner id
     * @param pAssociationCd the association code (ACCOUNT or DISTRIBUTOR so far 10/07/2003)
     * @return  array of int values
     * @throws   RemoteException Required by EJB 1.0
     */
    public int[] getBusEntityIds(int pTradingPartnerId, String pAssociationCd)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        try {
            con = getConnection();
            dbc = new DBCriteria();
            dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,pTradingPartnerId);
            dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, pAssociationCd);
            TradingPartnerAssocDataVector tpaDV =TradingPartnerAssocDataAccess.select(con,dbc);
            int ids[] = new int[tpaDV.size()];
            for(int ii=0; ii<tpaDV.size(); ii++) {
                TradingPartnerAssocData tpaD = (TradingPartnerAssocData) tpaDV.get(ii);
                ids[ii] = tpaD.getBusEntityId();
            }
            return ids;
        } catch (Exception exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.getBusEntityIds() Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
    }

    /**
     * Gets trading partner object for the application if defined.
     * @return  TradingPartnerData object
     * @throws   RemoteException Required by EJB 1.0
     * @throws   DataNotFoundException if there is not one defined
     */
    public TradingPartnerData getApplicationTradingPartner()
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc = new DBCriteria();
        TradingPartnerData tradingPartner = null;
        try {
            con = getConnection();
            dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_TYPE_CD,RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION);
            //dbc.addEqualTo(TradingPartnerDataAccess.BUS_ENTITY_ID,0);
            TradingPartnerDataVector tpdv =TradingPartnerDataAccess.select(con,dbc);
            if (tpdv.size() == 0){
                throw new DataNotFoundException("No Application Trading Partner Defined");
            }else if (tpdv.size() > 1){
                throw new RemoteException("Multiple Application Trading Partner Defined");
            }else{
                tradingPartner = (TradingPartnerData) tpdv.get(0);
            }
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.getTradingPartner() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.getTradingPartner() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return tradingPartner;
    }

    public TradingPartnerInfo getTradingPartnerInfo(int pTradingPartnerId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        TradingPartnerData tradingPartner = null;
        TradingPartnerInfo tradingPartnerInfo = new TradingPartnerInfo();
        try {
            con = getConnection();
            tradingPartner = TradingPartnerDataAccess.select(con,pTradingPartnerId);

            boolean lookup = true;
            dbc = new DBCriteria();
            dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,pTradingPartnerId);
            if(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER.equals(tradingPartner.getTradingPartnerTypeCd())) {
                dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,
                        RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
            } else if(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR.equals(tradingPartner.getTradingPartnerTypeCd())) {
                //dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,
                //        RefCodeNames.TRADING_PARTNER_ASSOC_CD.DISTRIBUTOR);
                ArrayList list = new ArrayList();
                list.add(RefCodeNames.TRADING_PARTNER_ASSOC_CD.DISTRIBUTOR);
                list.add(RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
                dbc.addOneOf(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, list);
            } else if(RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER.equals(tradingPartner.getTradingPartnerTypeCd())) {
                dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,
                        RefCodeNames.TRADING_PARTNER_ASSOC_CD.MANUFACTURER);
            } else if(RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE.equals(tradingPartner.getTradingPartnerTypeCd())) {
            	if (tradingPartner.getTradingTypeCd().equals(RefCodeNames.TRADING_TYPE_CD.PUNCHOUT)){
            		ArrayList list = new ArrayList();
                    list.add(RefCodeNames.TRADING_PARTNER_ASSOC_CD.STORE);
                    list.add(RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
                    dbc.addOneOf(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, list);
            	}else{
            		dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,
                        RefCodeNames.TRADING_PARTNER_ASSOC_CD.STORE);
            	}
            } else if(RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION.equals(tradingPartner.getTradingPartnerTypeCd())) {
                lookup = false;
            } else {
                throw new RemoteException("Unknown trading partner type: "+tradingPartner.getTradingPartnerTypeCd());
            }
            dbc.addOrderBy(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, false);
            dbc.addOrderBy(TradingPartnerAssocDataAccess.BUS_ENTITY_ID);

            TradingPartnerAssocDataVector tpaDV;
            if(lookup){
                tpaDV = TradingPartnerAssocDataAccess.select(con,dbc);
            } else{
                tpaDV = new TradingPartnerAssocDataVector();
            }

            Properties tpProps = new Properties();
            Properties props = getTradingPartnerProperties(con,
                    pTradingPartnerId);
            addProp(tpProps, kValCustItemDesc, props
                    .getProperty(kValCustItemDesc));
            addProp(tpProps, kEmailFrom, props
                    .getProperty(kEmailFrom));       
            addProp(tpProps, kEmailTo, props
                    .getProperty(kEmailTo));     
            addProp(tpProps, kEmailBodyTemplate, props
                    .getProperty(kEmailBodyTemplate));      
            addProp(tpProps, kEmailSubject, props
                    .getProperty(kEmailSubject));              
            addProp(tpProps, kValCustSkuNum, props.getProperty(kValCustSkuNum));
            addProp(tpProps, kProcessInvoiceCredit, props
                    .getProperty(kProcessInvoiceCredit));
            addProp(tpProps, kValCustRefOrderNum, props
                    .getProperty(kValCustRefOrderNum));
            addProp(tpProps, kCheckAddress, props.getProperty(kCheckAddress));
            addProp(tpProps, kCheckUOM, props.getProperty(kCheckUOM));
            addProp(tpProps, kFromContactName, props
                    .getProperty(kFromContactName));
            addProp(tpProps, kToContactName, props.getProperty(kToContactName));
            addProp(tpProps, kPurchaseOrderFreightTerms, props
                    .getProperty(kPurchaseOrderFreightTerms));
            addProp(tpProps, kPurchaseOrderDueDays, props
                    .getProperty(kPurchaseOrderDueDays));
            addProp(tpProps, kAllow856Flag, props.getProperty(kAllow856Flag));
            addProp(tpProps, kAllow856Email, props.getProperty(kAllow856Email));
            addProp(tpProps, kUseInboundAmtForCostAndPrice, props
                    .getProperty(kUseInboundAmtForCostAndPrice));
            addProp(tpProps, kUsePoLineNumForInvoiceMatch, props
                    .getProperty(kUsePoLineNumForInvoiceMatch));
            addProp(tpProps, kRelaxValidateDuplInvoiceNum, props
                    .getProperty(kRelaxValidateDuplInvoiceNum));
            IdVector busEntityIdV = new IdVector();
            for (int ii = 0; ii < tpaDV.size(); ii++) {
                TradingPartnerAssocData tpaD = (TradingPartnerAssocData) tpaDV
                        .get(ii);
                int beId = tpaD.getBusEntityId();
                busEntityIdV.add(new Integer(beId));
            }
            tradingPartnerInfo.setTradingPartnerData(tradingPartner);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,busEntityIdV);
            dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, false);
            dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
            BusEntityDataVector busEntDV = BusEntityDataAccess.select(con,dbc);

            tradingPartnerInfo.setBusEntities(busEntDV);
            tradingPartnerInfo.setTPAssocies(tpaDV);

            //gets the po fax number out of the database
            PhoneDataAccess pdao = new PhoneDataAccess();
            DBCriteria phoneCrit = new DBCriteria();
            phoneCrit.addEqualTo(PhoneDataAccess.PHONE_TYPE_CD,RefCodeNames.PHONE_TYPE_CD.POFAX);
            phoneCrit.addEqualTo(PhoneDataAccess.PHONE_STATUS_CD,RefCodeNames.PHONE_STATUS_CD.ACTIVE);
            phoneCrit.addOneOf(PhoneDataAccess.BUS_ENTITY_ID,busEntityIdV);
            phoneCrit.addEqualTo(PhoneDataAccess.PRIMARY_IND, true);
            PhoneDataVector poFaxes = pdao.select(con,phoneCrit);
            PhoneData poFax = null;
            if (poFaxes.size() == 0){
                poFax = PhoneData.createValue();
            } else {
                for(int ii=0; ii<poFaxes.size(); ii++) {
                    PhoneData  phD = (PhoneData) poFaxes.get(ii);
                    if(poFax!=null) {
                        String country = phD.getPhoneCountryCd();
                        if(country==null) country="";
                        country = country.trim();
                        String area = phD.getPhoneAreaCode();
                        if(area==null) area = "";
                        area = area.trim();
                        String num = phD.getPhoneNum();
                        if(num==null) num="";
                        num = num.trim();
                        String country1 = poFax.getPhoneCountryCd();
                        if(country1==null) country1="";
                        country1 = country1.trim();
                        String area1 = poFax.getPhoneAreaCode();
                        if(area1==null) area1 = "";
                        area1 = area1.trim();
                        String num1 = poFax.getPhoneNum();
                        if(num1==null) num1="";
                        num1 = num1.trim();
                        if(!(country.equals(country1) && area.equals(area1) && num.equals(num1))) {
                            String errorMess = "Multiple fax numbers for trading the partner. "+
                                    "Trading partner id = "+pTradingPartnerId;
                            throw new Exception(errorMess);
                        }
                    } else {
                        poFax = phD;
                    }
                }
            }
            tradingPartnerInfo.setPurchaseOrderFaxNumber(poFax);

            String v;

            v = tpProps.getProperty( kValCustItemDesc, "false" );
            tradingPartnerInfo.setValidateCustomerItemDesc(Boolean.valueOf(v).booleanValue());

            v = tpProps.getProperty( kValCustSkuNum, "false"  );
            tradingPartnerInfo.setValidateCustomerSkuNum(Boolean.valueOf(v).booleanValue());

            v = tpProps.getProperty( kProcessInvoiceCredit, "false"  );
            tradingPartnerInfo.setProcessInvoiceCredit(Boolean.valueOf(v).booleanValue());

            v = tpProps.getProperty( kValCustRefOrderNum, "false"  );
            tradingPartnerInfo.setValidateRefOrderNum(Boolean.valueOf(v).booleanValue());

            v = tpProps.getProperty( kCheckAddress, "false"  );
            tradingPartnerInfo.setCheckAddress(Boolean.valueOf(v).booleanValue());

            v = tpProps.getProperty( kCheckUOM, "false"  );
            tradingPartnerInfo.setCheckUOM(Boolean.valueOf(v).booleanValue());

            v = tpProps.getProperty( kFromContactName,"" );
            tradingPartnerInfo.setFromContactName(v);
            
            v = tpProps.getProperty( kEmailFrom,"" );
            tradingPartnerInfo.setEmailFrom(v);       
            
            v = tpProps.getProperty( kEmailTo,"" );
            tradingPartnerInfo.setEmailTo(v);     
            
            v = tpProps.getProperty( kEmailBodyTemplate,"" );
            tradingPartnerInfo.setEmailBodyTemplate(v);   
            
            v = tpProps.getProperty( kEmailSubject,"" );
            tradingPartnerInfo.setEmailSubject(v);        

            v = tpProps.getProperty( kToContactName,"" );
            tradingPartnerInfo.setToContactName(v);

            v = tpProps.getProperty( kPurchaseOrderFreightTerms,"" );
            tradingPartnerInfo.setPurchaseOrderFreightTerms(v);

            v = tpProps.getProperty( kPurchaseOrderDueDays,"" );
            tradingPartnerInfo.setPurchaseOrderDueDays(v);

            v = tpProps.getProperty( kAllow856Flag, "false"  );
            tradingPartnerInfo.setAllow856Flag(Boolean.valueOf(v).booleanValue());

            v = tpProps.getProperty( kAllow856Email, ""  );
            tradingPartnerInfo.setAllow856Email(v);
            
            v = tpProps.getProperty( kProcessInvoiceCredit, "false"  );
            tradingPartnerInfo.setProcessInvoiceCredit(Boolean.valueOf(v).booleanValue());
            
            v = tpProps.getProperty( kUseInboundAmtForCostAndPrice, "false"  );
            tradingPartnerInfo.setUseInboundAmountForCostAndPrice(Boolean.valueOf(v).booleanValue()); 
            v = tpProps.getProperty( kUsePoLineNumForInvoiceMatch, "false"  );
            tradingPartnerInfo.setUsePoLineNumForInvoiceMatch(Boolean.valueOf(v).booleanValue());
            v = tpProps.getProperty( kRelaxValidateDuplInvoiceNum, "false"  );
            tradingPartnerInfo.setRelaxValidateInboundDuplInvoiceNum(Boolean.valueOf(v).booleanValue());
            

        } catch (Exception exc) {
            String msg = "getTradingPartnerInfo: error: "+exc;
            logError(msg);
            throw new RemoteException(msg);
        } finally {
        	closeConnection(con);
        }

        return tradingPartnerInfo;
    }

    /*----------------------------------------------------------------------------------------*/
    private void addProp(Properties pProps, String pKey, String pValue)
    throws Exception {
        if(pValue==null) return;
        String oldValue = (String) pProps.get(pKey);
        if(oldValue==null) {
            pProps.put(pKey,pValue);
            return;
        }
		if(oldValue == null || pValue ==null){
			return;
		}
        if(oldValue.equals(pValue)) return;
        String errorMess = "Bad trading partner property compliance. Property name = "+
                pKey+" Value1 = "+oldValue+" Value2 = "+pValue
                + " property info=" + oldValue ;
        throw new Exception(errorMess);
    }


    public static void main(String[] args){
        //String s = "budgets2005.XLS";


        Pattern p = Pattern.compile("taxrates",Pattern.CASE_INSENSITIVE+Pattern.DOTALL);
        Matcher m = p.matcher("taxrates_20060227.csv");

        //Pattern p = Pattern.compile("^bud");
        //Matcher m = p.matcher("budgets2005.XLS");
        //Matcher m = p.matcher("budg");

    }

    /**
     * Returns the TradingPartnerDescView object which would handle the pattern supplied.
     * A file name would be one example of a pattern.  For example:
     *  jwporder_0101200.txt
     * @param    the identifier (or null) to use to limit the scope of our search
     * @param    pPattern the pattern to parse
     * @throws   DataNotFoundException if the pattern could not be parsed
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerDescView getTradingPartnerInfoByPattern(String pTradingProfileByIdentifer,String pUnParsedPattern)
    throws RemoteException, DataNotFoundException{
        Connection conn = null;

        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            if(Utility.isSet(pTradingProfileByIdentifer)){
                DBCriteria subCrit = new DBCriteria();
                subCrit.addDataAccessForJoin(new TradingProfileDataAccess());                
                subCrit.addJoinCondition(TradingProfileDataAccess.CLW_TRADING_PROFILE, TradingProfileDataAccess.TRADING_PARTNER_ID, TradingPartnerDataAccess.CLW_TRADING_PARTNER, TradingPartnerDataAccess.TRADING_PARTNER_ID);
                subCrit.addJoinTableEqualTo(TradingProfileDataAccess.CLW_TRADING_PROFILE, TradingProfileDataAccess.GROUP_RECEIVER,pTradingProfileByIdentifer);
                subCrit.addJoinTableNotEqualTo(TradingPartnerDataAccess.CLW_TRADING_PARTNER, TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD, RefCodeNames.TRADING_PARTNER_STATUS_CD.INACTIVE);
                String sql = JoinDataAccess.getSqlSelectIdOnly(TradingProfileDataAccess.CLW_TRADING_PROFILE, TradingProfileDataAccess.TRADING_PROFILE_ID, subCrit);
                crit.addOneOf(TradingProfileConfigDataAccess.TRADING_PROFILE_ID,sql);
            }
            crit.addIsNotNull(TradingProfileConfigDataAccess.PATTERN);
            TradingProfileConfigDataVector tpcdv = TradingProfileConfigDataAccess.select(conn,crit);
            if(tpcdv.size() == 0){
                throw new DataNotFoundException("Could not find any pattern configured TradingProfiles");
            }
            Iterator it = tpcdv.iterator();
            TradingProfileConfigData config = null;
            while(it.hasNext()){
                TradingProfileConfigData tmpConfig = (TradingProfileConfigData) it.next();
                Pattern pattern = Pattern.compile(tmpConfig.getPattern(),Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(pUnParsedPattern);
                if(matcher.find()){
                    if(config != null){
                        throw new RemoteException("Found multiple mappings to pattern: " + pUnParsedPattern);
                    }
                    config = tmpConfig;
                }
            }
            if(config == null){
                throw new DataNotFoundException("Could not find any TradingProfiles matching pattern: " + pUnParsedPattern);
            }
            TradingPartnerDescView tpdv = TradingPartnerDescView.createValue();
            tpdv.setTradingProfileConfigData(config);
            TradingProfileData tpd = TradingProfileDataAccess.select(conn,config.getTradingProfileId());
            tpdv.setTradingProfileData(tpd);
            TradingPartnerData tprd = TradingPartnerDataAccess.select(conn,tpd.getTradingPartnerId());
            tpdv.setTradingPartnerData(tprd);
            crit = new DBCriteria();
            crit.addEqualTo(TradingPropertyMapDataAccess.DIRECTION,config.getDirection());
            if(config.getPattern() == null){
                crit.addIsNull(TradingPropertyMapDataAccess.PATTERN);
            }else{
                crit.addEqualTo(TradingPropertyMapDataAccess.PATTERN,config.getPattern());
            }
            crit.addEqualTo(TradingPropertyMapDataAccess.SET_TYPE,config.getSetType());
            crit.addEqualTo(TradingPropertyMapDataAccess.TRADING_PROFILE_ID,config.getTradingProfileId());
            tpdv.setTradingPropertyMapDataVector(TradingPropertyMapDataAccess.select(conn, crit));
            return tpdv;

        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }    
    
    /**
     * Gets trading partner object by busness entity id. If found more than one, returns the first object
     * @param pBusEntityId the busness entity id
     * @return  TradingPartnerData object
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerData getTradingPartnerByBusEntity(int pBusEntityId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        TradingPartnerData tradingPartner = null;
        try {
            con = getConnection();
            BusEntityData beD = BusEntityDataAccess.select(con,pBusEntityId);
            dbc = new DBCriteria();
            dbc.addEqualTo(TradingPartnerAssocDataAccess.BUS_ENTITY_ID,pBusEntityId);
            dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, beD.getBusEntityTypeCd());
            String tradingPartnerReq =
                    TradingPartnerAssocDataAccess.getSqlSelectIdOnly(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(TradingPartnerDataAccess.TRADING_PARTNER_ID,tradingPartnerReq);
            dbc.addNotEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD,
                    RefCodeNames.TRADING_PARTNER_STATUS_CD.INACTIVE);

            TradingPartnerDataVector tPartnerDV = TradingPartnerDataAccess.select(con,dbc);
            if(tPartnerDV.size()==0) {
                throw new DataNotFoundException("No trading partner object found for business entity. Business entinty id: "+pBusEntityId);
            }
            if(tPartnerDV.size()>1) {
				tradingPartner =(TradingPartnerData) tPartnerDV.get(0);
                //throw new DataNotFoundException("Multiple trading partner objects found for business entity. Business entinty id: "+pBusEntityId);
            }
            tradingPartner =(TradingPartnerData) tPartnerDV.get(0);
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.getTradingPartnerByBusEntity() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.getTradingPartnerByBusEntity() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return tradingPartner;
    }

    /**
     * Gets trading profile object by its id.
     * @param pTradingProfileId the trading profile id
     * @return  TradingProfileData object
     * @throws   RemoteException Required by EJB 1.0, DataNotFoundException
     */
    public TradingProfileData getTradingProfile(int pTradingProfileId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        TradingProfileData tradingProfile = null;
        try {
            con = getConnection();
            tradingProfile =TradingProfileDataAccess.select(con,pTradingProfileId);
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.getTradingProfile() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.getTradingProfile() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return tradingProfile;
    }

    public void deleteProfile(int pTradingProfileId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        try {
            con = getConnection();
            TradingProfileDataAccess.remove(con,pTradingProfileId);
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("deleteProfile() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("deleteProfile() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return ;
    }

    /**
     * Gets trading profile object by trading partner id.
     * @param pTradingParntnerId the trading partner id
     * @return  TradingProfileData object (the first one if more than one found) or null if profile does not exist
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingProfileDataVector
            getTradingProfileByPartnerId(int pTradingPartnerId)
            throws RemoteException {
        Connection con = null;
        DBCriteria dbc;
        TradingProfileDataVector tradingProfileDV = null;
        try {
            con = getConnection();
            dbc = new DBCriteria();
            dbc.addEqualTo(TradingProfileDataAccess.TRADING_PARTNER_ID,pTradingPartnerId);
            tradingProfileDV =TradingProfileDataAccess.select(con,dbc);

        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.getTradingProfileByPartnerId() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.getTradingProfileByPartnerId() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return tradingProfileDV;
    }


    private String getTradingProfilesForGroupSenderSql(String pSender){
      String sql = "select tp.trading_profile_id from clw_trading_profile tp, clw_trading_partner_assoc tpa "+
      "where "+
      "tpa.trading_partner_id (+)= tp.trading_partner_id and "+
      "(tp.group_sender = '"+pSender+"' or tpa.group_sender_override = '"+pSender+"') ";
      return sql;
    }

    public TradingProfileData getTradingProfileByGroupSenderAndReceiver(
            String pSender, String pReceiver,String pDirection, String pSetType, String pTestIndicator)
            throws RemoteException,DataNotFoundException{
    	
    	return getTradingProfileByGroupSenderAndReceiver(pSender, pReceiver, pDirection, pSetType,
    			pTestIndicator, null);
    }

    /**
     * Gets trading profile object by trading partner group sendor and group receiver id.
     * @param pSendorId the trading partner group sender id
     * @param pSendorId the trading partner group receiver id
     * @param pTestIndicator either p for production or t for test
     * @return  TradingProfileData object
     * @throws   RemoteException Required by EJB 1.0
     * @throws   DataNotFoundException if it could not find exactly one Trading Profile
     */
    public TradingProfileData getTradingProfileByGroupSenderAndReceiver(
            String pSender, String pReceiver,String pDirection, String pSetType, String pTestIndicator,
            String domain)
            throws RemoteException,DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        TradingProfileData tradingProfile = null;
        String lastSQL = null;
        try {
            con = getConnection();
            dbc = new DBCriteria();
            dbc.addOneOf(TradingProfileDataAccess.TRADING_PROFILE_ID,getTradingProfilesForGroupSenderSql(pSender));
            dbc.addEqualTo(TradingProfileDataAccess.GROUP_RECEIVER,pReceiver);
            dbc.addEqualToIgnoreCase(TradingProfileDataAccess.TEST_INDICATOR,pTestIndicator);
            if(domain!=null){
            	dbc.addEqualToIgnoreCase(TradingProfileDataAccess.INTERCHANGE_SENDER_QUALIFIER, domain);
            }
            DBCriteria inCrit = new DBCriteria();
            inCrit.addEqualTo(TradingProfileConfigDataAccess.DIRECTION,pDirection);
            inCrit.addEqualTo(TradingProfileConfigDataAccess.SET_TYPE,pSetType);
            String inSql =
                    TradingProfileConfigDataAccess.getSqlSelectIdOnly(
                    TradingProfileConfigDataAccess.TRADING_PROFILE_ID,inCrit);
            dbc.addOneOf(TradingProfileDataAccess.TRADING_PROFILE_ID,inSql);
            
            inCrit = new DBCriteria();
            inCrit.addNotEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD,RefCodeNames.TRADING_PARTNER_STATUS_CD.INACTIVE);
            inSql = TradingPartnerDataAccess.getSqlSelectIdOnly(
            			TradingPartnerDataAccess.TRADING_PARTNER_ID,inCrit);
            dbc.addOneOf(TradingProfileDataAccess.TRADING_PARTNER_ID,inSql);

            lastSQL = TradingProfileDataAccess.getSqlSelectIdOnly("*",dbc);
			TradingProfileDataVector tradingProfileDV = TradingProfileDataAccess.select(con,dbc);

            if(tradingProfileDV.size()==1) {
                tradingProfile = (TradingProfileData) tradingProfileDV.get(0);
            } else if (tradingProfileDV.size()==0){
                dbc = new DBCriteria();
                dbc.addOneOf(TradingProfileDataAccess.TRADING_PROFILE_ID,getTradingProfilesForGroupSenderSql(pSender));
                dbc.addEqualTo(TradingProfileDataAccess.GROUP_RECEIVER,pReceiver);
                dbc.addEqualToIgnoreCase(TradingProfileDataAccess.TEST_INDICATOR,pTestIndicator);
                lastSQL = TradingProfileDataAccess.getSqlSelectIdOnly("*",dbc);
                tradingProfileDV = TradingProfileDataAccess.select(con,dbc);
                if(tradingProfileDV.size()==1) {
                    tradingProfile = (TradingProfileData) tradingProfileDV.get(0);
                }else if (tradingProfileDV.size()>1){
                  throw new DataNotFoundException("More than 1 partner profile ("+tradingProfileDV.size()+") is set up for Group Sender Id = "
                            + pSender + " and Group Receiver Id = " + pReceiver + " and Directionr = " +pDirection +
                            " and SetType = "+pSetType+ " and TestIndicator = "+pTestIndicator);
                }else{
                    throw new DataNotFoundException("Trading Partner profile is not set up for Group Sender Id = "
                            + pSender + " and Group Receiver Id = " + pReceiver + " and Directionr = " +pDirection +
                            " and SetType = "+pSetType+ " and TestIndicator = "+pTestIndicator);
                }
            } else if (tradingProfileDV.size()>1){
                throw new DataNotFoundException("More than 1 partner profile ("+tradingProfileDV.size()+") is set up for Group Sender Id = "
                        + pSender + " and Group Receiver Id = " + pReceiver + " and Directionr = " +pDirection +
                        " and SetType = "+pSetType+ " and TestIndicator = "+pTestIndicator);
            }
        } catch (Exception exc) {
            throw processException(exc);
        }finally {
            closeConnection(con);
        }
        //explicietly set the group sender and reciever ids in case there
        //were some overides specified.
        tradingProfile.setGroupSender(pSender);
        tradingProfile.setGroupReceiver(pReceiver);
        return tradingProfile;
    }

    public TradingProfileData getTradingProfileByDomainIdentifiers(
            HashMap<String,String> toDomainIds, HashMap<String,String> fromDomainIds, String pDirection, 
            String pSetType, String pTestIndicator)throws RemoteException,DataNotFoundException {

        TradingProfileData tradingProfile = null;
        try {
        	
        	int toSegments = toDomainIds.entrySet().size();
        	int fromSegments = fromDomainIds.entrySet().size();
        	
        	if(toSegments==1 && fromSegments==1){
        		
        		String fromIdentity = "";
        		String toIdentity = "";
        		
        		for (Map.Entry<String, String> entry: toDomainIds.entrySet()) {
                	toIdentity = entry.getValue(); 	
        		}
        		for(Map.Entry<String, String> entry2: fromDomainIds.entrySet()){
            		fromIdentity = entry2.getValue();
        		}
        		
        		tradingProfile = getTradingProfileByGroupSenderAndReceiver(fromIdentity,toIdentity,
    					pDirection,pSetType,pTestIndicator);
        		
        	}else{
            //get trading profile with specific domain name
            for (Map.Entry<String, String> entry: toDomainIds.entrySet()) {
            	String toDomain = entry.getKey();
            	String toIdentity = entry.getValue(); 	

            	for(Map.Entry<String, String> entry2: fromDomainIds.entrySet()){
            		String fromDomain = entry2.getKey();
            		String fromIdentity = entry2.getValue();

            		if(toDomain.equalsIgnoreCase(fromDomain)){
            			try{
            			tradingProfile = getTradingProfileByGroupSenderAndReceiver(fromIdentity,toIdentity,
            					pDirection,pSetType,pTestIndicator,toDomain);
            			
            			}catch(Exception exc){
            				log.info("Trading profile is not set for domain "+toDomain);
            			}
            		}
            	}
            	
            }
            
        	}
        } catch (Exception exc) {
            throw processException(exc);
        }
        return tradingProfile;
    }
    
    public int saveTradingPartner(TradingPartnerData pTradingPartner,
            String pUser)
            throws RemoteException {
        return storeTradingPartnerInfo(pTradingPartner, null, pUser,false);
    }

    private static String kValCustItemDesc = "kValCustItemDesc";
    private static String kValCustSkuNum   = "kValCustSkuNum";
    private static String kValCustRefOrderNum   = "kValCustRefOrderNum";
    private static String kCheckAddress   = "kCheckAddress";
    private static String kCheckUOM   = "kCheckUOM";
    private static String kToContactName   = "kToContactName";
    private static String kFromContactName   = "kFromContactName";
    private static String kPurchaseOrderDueDays   = RefCodeNames.PROPERTY_TYPE_CD.PURCH_ORDER_DUE_DAYS;
    private static String kPurchaseOrderFreightTerms   = RefCodeNames.PROPERTY_TYPE_CD.PURCH_ORDER_FREIGHT_TERMS;
    private static String kProcessInvoiceCredit = "kProcessInvoiceCredit";
    private static String kAllow856Flag = "kAllow856Flag";
    private static String kAllow856Email = "kAllow856Email";
    private static String kUseInboundAmtForCostAndPrice = "kUseInboundAmtForCostAndPrice";
    private static String kUsePoLineNumForInvoiceMatch = "kUsePoLineNumForInvoiceMatch";
    private static String kRelaxValidateDuplInvoiceNum = "kRelaxValidateDuplInvoiceNum";
    private static String kEmailFrom = "kEmailFrom";
    private static String kEmailTo = "kEmailTo";    
    private static String kEmailBodyTemplate = "kEmailBodyTemplate";      
    private static String kEmailSubject = "kEmailSubject";      

    public int saveTradingPartnerInfo
            (TradingPartnerInfo pTradingPartner, String pUser)
            throws RemoteException {
        int tpid = storeTradingPartnerInfo
                (pTradingPartner.getTradingPartnerData(), null, pUser,pTradingPartner.isInitializeExistingPos());
        Connection conn = null;
        DBCriteria dbc;
        try{
            conn = getConnection();
            PhoneData poFax = pTradingPartner.getPurchaseOrderFaxNumber();
            int tpId = pTradingPartner.getTradingPartnerData().getTradingPartnerId();

            BusEntityDataVector  tpBeDV = pTradingPartner.getBusEntities();


            //Save bus entity assoc
            dbc = new DBCriteria();
            dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,tpId);
            TradingPartnerAssocDataVector tpAssocDV = TradingPartnerAssocDataAccess.select(conn,dbc);
            TradingPartnerAssocDataVector tpANewDV = pTradingPartner.getTPAssocies();

            for (int ii=0; ii<tpANewDV.size(); ii++) {
              TradingPartnerAssocData tpaNewD = (TradingPartnerAssocData)tpANewDV.get(ii);
              boolean foundFl = false;
              for(int jj=0; jj<tpAssocDV.size(); jj++) {
                TradingPartnerAssocData tpaD = (TradingPartnerAssocData) tpAssocDV.get(jj);
                foundFl = true;
                if (tpaNewD.getBusEntityId() == tpaD.getBusEntityId()) {
                  // update if group sender changed
                  String newGroupSenderValue = tpaNewD.getGroupSenderOverride();
                  String oldGroupSenderValue = tpaD.getGroupSenderOverride();
                  if (tpaNewD.getTradingPartnerAssocCd().equals(RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT) &&
                      !Utility.isEqual(newGroupSenderValue,oldGroupSenderValue)
                    )
                  {
                    tpaNewD.setModBy(pUser);
                    TradingPartnerAssocDataAccess.update(conn, tpaNewD);
                  }
                  tpAssocDV.remove(jj);
                  break;
                }
              }
              // insert if it is new
              if(!foundFl) {
                  if (tpaNewD.getTradingPartnerId() == 0) {
                      tpaNewD.setTradingPartnerId(tpId);
                  }
                 TradingPartnerAssocDataAccess.insert(conn,tpaNewD);
              }
            }
            // remove the rest
            for(int jj=0; jj<tpAssocDV.size(); jj++) {
                TradingPartnerAssocData tpaD = (TradingPartnerAssocData) tpAssocDV.get(jj);
                dbc = new DBCriteria();
                dbc.addEqualTo(TradingPartnerAssocDataAccess.BUS_ENTITY_ID, tpaD.getBusEntityId());
                dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID, tpaD.getTradingPartnerId());
                dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, tpaD.getTradingPartnerAssocCd());
                TradingPartnerAssocDataAccess.remove(conn,dbc);
            }


/*
            for(int ii=0; ii<tpBeDV.size(); ii++) {
                BusEntityData beD = (BusEntityData) tpBeDV.get(ii);
                int beId = beD.getBusEntityId();
                boolean foundFl = false;
                for(int jj=0; jj<tpAssocDV.size(); jj++) {
                    TradingPartnerAssocData tpaD = (TradingPartnerAssocData) tpAssocDV.get(jj);
                    if(beId==tpaD.getBusEntityId()) {
                        foundFl = true;
                        tpAssocDV.remove(jj);
                        break;
                    }
                }
                if(!foundFl) {
                    TradingPartnerAssocData tpaD = TradingPartnerAssocData.createValue();
                    tpaD.setBusEntityId(beId);
                    tpaD.setTradingPartnerId(tpId);
                    tpaD.setTradingPartnerAssocCd(beD.getBusEntityTypeCd());
                    tpaD.setAddBy(pUser);
                    tpaD.setModBy(pUser);
                    TradingPartnerAssocDataAccess.insert(conn,tpaD);
                }
            }
            for(int jj=0; jj<tpAssocDV.size(); jj++) {
                TradingPartnerAssocData tpaD = (TradingPartnerAssocData) tpAssocDV.get(jj);
                dbc = new DBCriteria();
                dbc.addEqualTo(TradingPartnerAssocDataAccess.BUS_ENTITY_ID, tpaD.getBusEntityId());
                dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID, tpaD.getTradingPartnerId());
                dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, tpaD.getTradingPartnerAssocCd());
                TradingPartnerAssocDataAccess.remove(conn,dbc);
            }
 */

            //save the poFax number
            if (poFax.isDirty()) {
                for(int ii=0; ii<tpBeDV.size(); ii++) {
                    BusEntityData tpBeD   = (BusEntityData) tpBeDV.get(ii);
                    int beId = tpBeD.getBusEntityId();
                    dbc = new DBCriteria();
                    dbc.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID,beId);
                    dbc.addEqualTo(PhoneDataAccess.PHONE_TYPE_CD,RefCodeNames.PHONE_TYPE_CD.POFAX);
                    dbc.addEqualTo(PhoneDataAccess.PRIMARY_IND,true);
                    PhoneDataVector faxes = PhoneDataAccess.select(conn,dbc);
                    if(faxes.size()>0) {
                        for(int jj=0; jj<faxes.size(); jj++) {
                            PhoneData fax = (PhoneData) faxes.get(jj);
                            fax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                            fax.setPhoneCountryCd(poFax.getPhoneCountryCd());
                            fax.setPhoneAreaCode(poFax.getPhoneAreaCode());
                            fax.setPhoneNum(poFax.getPhoneNum());
                            fax.setModBy(pUser);
                            PhoneDataAccess.update(conn, fax);
                        }
                    }else {
                        poFax.setPhoneId(0);
                        poFax.setBusEntityId(beId);
                        poFax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.POFAX);
                        poFax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                        poFax.setPrimaryInd(true);
                        poFax.setAddBy(pUser);
                        poFax.setAddBy(pUser);
                        poFax = PhoneDataAccess.insert(conn, poFax);
                    }
                }
            }


            // Now save the other parameters.
            Properties tpProps = new Properties();
            tpProps.setProperty
                    (kValCustItemDesc,
                    new Boolean(pTradingPartner.isValidateCustomerItemDesc()).toString()
                    );
            tpProps.setProperty
                    ( kValCustSkuNum   ,
                    new Boolean(pTradingPartner.isValidateCustomerSkuNum()).toString());
            tpProps.setProperty
                    ( kProcessInvoiceCredit   ,
                    new Boolean(pTradingPartner.isProcessInvoiceCredit()).toString());
            tpProps.setProperty
                    ( kValCustRefOrderNum ,
                    new Boolean(pTradingPartner.isValidateRefOrderNum()).toString());
            tpProps.setProperty
                    ( kCheckAddress   ,
                    new Boolean(pTradingPartner.isCheckAddress()).toString());
            tpProps.setProperty
                    ( kCheckUOM   ,
                    new Boolean(pTradingPartner.isCheckUOM()).toString());
            tpProps.setProperty
            		( kEmailFrom, pTradingPartner.getEmailFrom());
            tpProps.setProperty
    				( kEmailTo, pTradingPartner.getEmailTo());            
            tpProps.setProperty
    				( kEmailBodyTemplate, pTradingPartner.getEmailBodyTemplate());         
            tpProps.setProperty
    				( kEmailSubject, pTradingPartner.getEmailSubject());              
            tpProps.setProperty
                    ( kFromContactName, pTradingPartner.getFromContactName());
            tpProps.setProperty
                    ( kToContactName, pTradingPartner.getToContactName());
            tpProps.setProperty
                    ( kPurchaseOrderFreightTerms, pTradingPartner.getPurchaseOrderFreightTerms());
            tpProps.setProperty
                    ( kPurchaseOrderDueDays, pTradingPartner.getPurchaseOrderDueDays());
            tpProps.setProperty
                    ( kAllow856Flag   ,
                    new Boolean(pTradingPartner.isAllow856()).toString());
            tpProps.setProperty
                    ( kAllow856Email   , pTradingPartner.getAllow856Email());
            tpProps.setProperty
                    ( kUseInboundAmtForCostAndPrice, new Boolean(pTradingPartner.isUseInboundAmountForCostAndPrice()).toString());
            tpProps.setProperty
            		( kUsePoLineNumForInvoiceMatch, new Boolean(pTradingPartner.isUsePoLineNumForInvoiceMatch()).toString());
            tpProps.setProperty
            		( kRelaxValidateDuplInvoiceNum, new Boolean(pTradingPartner.isRelaxValidateInboundDuplInvoiceNum()).toString());
            
            setTradingPartnerProperties(conn, pTradingPartner
                    .getTradingPartnerData().getTradingPartnerId(), pUser,
                    tpProps);
        }catch (Exception e) {
            throw new RemoteException("saveTradingPartnerInfo: " + e.getMessage());
        }finally {
        	closeConnection(conn);
        }
        return tpid;
    }

    /**
     *@returns the trading profile id
     */
    public int saveTradingProfile
            (TradingPartnerData pTradingPartner,
            TradingProfileData pTradingProfile, String pUser)
            throws RemoteException {
        return storeTradingPartnerInfo(pTradingPartner, pTradingProfile, pUser,false);
    }

    /**
     * Inserts new or updates trading partner and trading profile objects
     * @param pTradingPartner the trading partner object
     * @param pTradingPartner the trading profile object
     * @param pUser the user logon name
     * @throws   RemoteException Required by EJB 1.0
     */
    private int storeTradingPartnerInfo
            (TradingPartnerData pTradingPartner,
            TradingProfileData pTradingProfile, String pUser,
            boolean pInitializeExistingPos)
            throws RemoteException {
        int tradingPartnerId = pTradingPartner.getTradingPartnerId();
        Connection con = null;
        DBCriteria dbc;
        try {
            con = getConnection();

            DBCriteria busCrit = new DBCriteria();
/*
            int busEntityId = pTradingPartner.getBusEntityId();
            if(pTradingPartner.getTradingPartnerTypeCd().equalsIgnoreCase(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR))
            {
                busCrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            }else if(pTradingPartner.getTradingPartnerTypeCd().equalsIgnoreCase(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER))
            {
                busCrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            }else if(pTradingPartner.getTradingPartnerTypeCd().equalsIgnoreCase(RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION))
            {
                //nothing
            }else{
                throw new RemoteException("Invalid trading partner type code: " + pTradingPartner.getTradingPartnerTypeCd());
            }
            BusEntityData busEntDat = null;
            if(!pTradingPartner.getTradingPartnerTypeCd().equalsIgnoreCase(RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION)){
                busCrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,busEntityId);
                BusEntityDataVector busEntDataVec = BusEntityDataAccess.select(con,busCrit);
                if (busEntDataVec.size() == 1){
                    busEntDat = (BusEntityData) busEntDataVec.get(0);
                }else if(busEntDataVec.size() == 0){
                    throw new RemoteException("BusEntity id "+busEntityId+" does not exsist for trading " +
                    "type of: " +pTradingPartner.getTradingPartnerTypeCd() +". " +
                    "Trading partner *MUST* reference a valid bus entity");
                }else{
                    throw new RemoteException("BusEntity id "+busEntityId+" exsists: " + busEntDataVec.size() +
                    " times for trading type: " +pTradingPartner.getTradingPartnerTypeCd() +". " +
                    "Trading partner *MUST* reference ONE valid bus entity");
                }
            }
 */
            TradingPartnerData tradingPartner = null;
/*
            if(busEntityId==0 && !pTradingPartner.getTradingPartnerTypeCd().equalsIgnoreCase(RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION)) {
                throw new RemoteException("TradingPartnerBean.storeTradingPartner(). No BusEntityId provided");
            } else {
                dbc = new DBCriteria();
                dbc.addEqualTo(TradingPartnerDataAccess.BUS_ENTITY_ID,busEntityId);
                TradingPartnerDataVector tPartnerDV = TradingPartnerDataAccess.select(con,dbc);
                if(tPartnerDV.size()>1) {
                    throw new RemoteException("Error. TradingPartnerBean.storeTradingPartner(). Too many trading partners for business entity. BusEntityId: "+busEntityId);
                }
                if(tPartnerDV.size()==1) {
                    tradingPartner =(TradingPartnerData) tPartnerDV.get(0);
                    if(tradingPartnerId!=tradingPartner.getTradingPartnerId()) {
                        throw new RemoteException("Error. TradingPartnerBean.storeTradingPartner(). Business entity already has trading partner object. BusEntityId: "+busEntityId);
                    }
                }
            }
 */
            TradingProfileData tradingProfile = null;
            if(tradingPartnerId!=0 && tradingPartner==null) {
                try {
                    tradingPartner = TradingPartnerDataAccess.select(con,tradingPartnerId);
                } catch (DataNotFoundException exc) {};
            }
            if(tradingPartner!=null) {
                pTradingPartner.setAddBy(tradingPartner.getAddBy());
                pTradingPartner.setAddDate(tradingPartner.getAddDate());
                pTradingPartner.setModBy(pUser);
                TradingPartnerDataAccess.update(con,pTradingPartner);
            } else {
                pTradingPartner.setAddBy(pUser);
                pTradingPartner.setModBy(pUser);
                pTradingPartner = TradingPartnerDataAccess.insert(con,pTradingPartner);
                tradingPartnerId = pTradingPartner.getTradingPartnerId();
                //now update the state of all items and pos associated with this
                //trading partner
                if (pInitializeExistingPos){
                    //find me
                    if (pTradingPartner.getTradingPartnerTypeCd().equalsIgnoreCase(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR)){
                        /*
                        DBCriteria oicrit = new DBCriteria();
                        oicrit.addEqualTo(OrderItemDataAccess.ORDER_ITEM_STATUS_CD,
                            RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT);
                        oicrit.addEqualTo(OrderItemDataAccess.DIST_ERP_NUM,busEntDat.getErpNum());
                        OrderItemDataVector oidv = OrderItemDataAccess.select(con,oicrit);
                        for (int i=0,len=oidv.size();i<len;i++){
                            OrderItemData oid = (OrderItemData) oidv.get(i);
                            oid.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
                            OrderItemDataAccess.update(con,oid);
                        }
                        DBCriteria pocrit = new DBCriteria();
                        pocrit.addEqualTo(PurchaseOrderDataAccess.PURCHASE_ORDER_STATUS_CD,
                            RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT);
                        pocrit.addEqualTo(PurchaseOrderDataAccess.DIST_ERP_NUM,busEntDat.getErpNum());
                        PurchaseOrderDataVector podv = PurchaseOrderDataAccess.select(con,pocrit);
                        for (int i=0,len=podv.size();i<len;i++){
                            PurchaseOrderData po = (PurchaseOrderData) podv.get(i);
                            po.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER);
                            PurchaseOrderDataAccess.update(con,po);
                        }
                         */
                    }
                }
            }

            if(pTradingProfile!=null) {
                pTradingProfile.setTradingPartnerId(tradingPartnerId);
                if(pTradingProfile.getTradingProfileId() > 0) {
                    pTradingProfile.setModBy(pUser);
                    TradingProfileDataAccess.update(con,pTradingProfile);
                } else {
                    pTradingProfile.setAddBy(pUser);
                    pTradingProfile.setModBy(pUser);
                    TradingProfileDataAccess.insert(con,pTradingProfile);
                }
            }
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.storeTradingPartner() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.storeTradingPartner() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return tradingPartnerId;
    }


    /**
     * Gets list of trading partner brief descriptions
     * @param pTradingPartnerType could be CUSTOMER or DISTRIBUTOR or empty
     * @param pBusEntityName a parth of ACCOUNT and/or DISTRIBUTOR short description or empty
     * @param pTradingPartnerName a part of trading partner short descripton or empty
     * @param pTradingType could be PAPER, EDI or XML or empty
     * @param pTradingStatus could be ACTIVE or INACTIVE or LIMITED or empty
     * @param pMatch could be SearchCriteria.BEGINS_WITH_IGNORE_CASE or SearchCriteria.CONTAINS_IGNORE_CASE
     * @return  TradingPartnerViewVector object
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerViewVector
            getTradingPartners(String pTradingPartnerType,
            String pBusEntityName,
            int pTradingPartnerId,
            String pTradingPartnerName,
            String pTradingType,
            String pTradingStatus,
            String pTraidingTypeCD,
            int pMatch)
            throws RemoteException {
        Connection con = null;
        DBCriteria dbc;
        TradingPartnerViewVector tradingPartnerVV = new TradingPartnerViewVector();
        try {
            con = getConnection();
            //Create business partner condition
            String busEntityReq = null;
            boolean getAllFlag = true;
            if(pBusEntityName!=null && pBusEntityName.trim().length()>0) {
                getAllFlag = false;
                dbc = new DBCriteria();
                switch (pMatch) {
                    case SearchCriteria.BEGINS_WITH_IGNORE_CASE:
                        dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, pBusEntityName.trim().toUpperCase() + "%");
                        break;
                    case SearchCriteria.CONTAINS_IGNORE_CASE:
                        dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, "%" +pBusEntityName.trim().toUpperCase() + "%");
                        break;
                    default:
                        throw new RemoteException("Error. CTradingPartner.getTradingPartners()(). Unexpected match type: " + pMatch);
                }
                busEntityReq = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID,dbc);
            }
            //Create main conditions
            dbc = new DBCriteria();
            if (pTradingPartnerId > 0) {
                getAllFlag = false;
                dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_ID, pTradingPartnerId);
            }
            if(pTradingPartnerType!=null && pTradingPartnerType.trim().length()>0) {
                getAllFlag = false;
                dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_TYPE_CD,pTradingPartnerType);
            }
            if(pTradingPartnerName!=null && pTradingPartnerName.trim().length()>0) {
                getAllFlag = false;
                switch (pMatch) {
                    case SearchCriteria.BEGINS_WITH_IGNORE_CASE:
                        dbc.addLikeIgnoreCase(TradingPartnerDataAccess.SHORT_DESC,pTradingPartnerName.trim().toUpperCase() + "%");
                        break;
                    case SearchCriteria.CONTAINS_IGNORE_CASE:
                        dbc.addLikeIgnoreCase(TradingPartnerDataAccess.SHORT_DESC,"%"+pTradingPartnerName.trim().toUpperCase() + "%");
                        break;
                }
            }
            if(pTradingType!=null && pTradingType.trim().length()>0) {
                getAllFlag = false;
                dbc.addEqualTo(TradingPartnerDataAccess.TRADING_TYPE_CD,pTradingType);
            }
            if(pTradingStatus!=null && pTradingStatus.trim().length()>0) {
                getAllFlag = false;
                dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD,pTradingStatus);
            }
            if(pTraidingTypeCD!=null && pTraidingTypeCD.trim().length()>0) {
                getAllFlag = false;
                dbc.addEqualTo(TradingPartnerDataAccess.TRADING_TYPE_CD,pTraidingTypeCD);
            }            
            if(busEntityReq!=null) {
                DBCriteria dbcTpAssoc = new DBCriteria();
                dbcTpAssoc.addOneOf(TradingPartnerAssocDataAccess.BUS_ENTITY_ID, busEntityReq);
                String tpAssocReq = TradingPartnerAssocDataAccess.
                        getSqlSelectIdOnly(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,dbcTpAssoc);
                getAllFlag = false;
                dbc.addOneOf(TradingPartnerDataAccess.TRADING_PARTNER_ID,tpAssocReq);
            }
            if(getAllFlag) {
                dbc.addCondition("1=1");
            }
            String tradingPartnerReq = TradingPartnerDataAccess.getSqlSelectIdOnly(
                    TradingPartnerDataAccess.TRADING_PARTNER_ID, dbc);
            dbc.addOrderBy(TradingPartnerDataAccess.TRADING_PARTNER_ID);
            //Select trading partners
            TradingPartnerDataVector tradingPartnerDV = TradingPartnerDataAccess.select(con,dbc);
            //Crete parner view data
            for(int ii=0; ii<tradingPartnerDV.size(); ii++) {
                TradingPartnerView tPartnerVw = TradingPartnerView.createValue();
                TradingPartnerData tPartnerD = (TradingPartnerData) tradingPartnerDV.get(ii);
                int id = tPartnerD.getTradingPartnerId();
                String shortDesc = tPartnerD.getShortDesc();
                String type = tPartnerD.getTradingPartnerTypeCd();
                String status = tPartnerD.getTradingPartnerStatusCd();
                String traidingTypeCD = tPartnerD.getTradingTypeCd();
                tPartnerVw.setId(id);
                tPartnerVw.setShortDesc(shortDesc);
                //tPartnerVw.setBusEntityId(bid);
                //tPartnerVw.setBusEntityShortDesc(busEntityShortDesc);
                tPartnerVw.setType(type);
                tPartnerVw.setStatus(status);
                tPartnerVw.settraidingTypeCD(traidingTypeCD);
                tradingPartnerVV.add(tPartnerVw);
            }
            //Select busEntity data
            String tpBusEntitySql =
                    "select tpa.TRADING_PARTNER_ID, be.BUS_ENTITY_ID, be.SHORT_DESC "+
                    "from clw_trading_partner_assoc tpa, clw_bus_entity be "+
                    " where tpa.bus_entity_id = be.bus_entity_id "+
                    "   and tpa.trading_partner_id in ("+tradingPartnerReq+") "+
                    "   order by tpa.TRADING_PARTNER_ID ";
            Statement stmt = con.createStatement();
            ResultSet tpBusEntityRS = stmt.executeQuery(tpBusEntitySql);
            int ind = 0;
            while (tpBusEntityRS.next() ) {
                int tpId = tpBusEntityRS.getInt("TRADING_PARTNER_ID");
                int beId =tpBusEntityRS.getInt("BUS_ENTITY_ID");
                String beName = tpBusEntityRS.getString("SHORT_DESC");
                for(;ind<tradingPartnerVV.size(); ind++) {
                    TradingPartnerView tpVw = (TradingPartnerView)tradingPartnerVV.get(ind);
                    int tpId1 = tpVw.getId();
                    if(tpId1==tpId) {
                        if(tpVw.getBusEntityShortDesc()==null) {
                            tpVw.setBusEntityId(beId);
                            tpVw.setBusEntityShortDesc(beName);
                        } else {
                            tpVw.setBusEntityId(0);
                            String names = tpVw.getBusEntityShortDesc();
                            if(names.length()>0) names += ", ";
                            tpVw.setBusEntityShortDesc(names + beName);
                        }
                        break;
                    }
                    if(tpId1>tpId) break;
                }
            }
            tpBusEntityRS.close();
            stmt.close();
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.getTradingPartners() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.getTradingPartners() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
        return tradingPartnerVV;
    }

    /**
     * Deletes trading partner and its profile. It is up to database to control relational integrity
     * @param pTradingPartnerId the trading partner id
     * @throws   RemoteException Required by EJB 1.0
     */
    public void deleteTradingPartner(int pTradingPartnerId)
    throws RemoteException {
        Connection con = null;
        DBCriteria dbc;
        try {
            con = getConnection();
            //delete trading profile
            dbc = new DBCriteria();
            dbc.addEqualTo(TradingProfileDataAccess.TRADING_PARTNER_ID,pTradingPartnerId);
            TradingProfileDataAccess.remove(con,dbc);
            dbc = new DBCriteria();
            dbc.addEqualTo(TradingPartnerPropDataAccess.TRADING_PARTNER_ID,
                    pTradingPartnerId);
            TradingPartnerPropDataAccess.remove(con, dbc);
            //delete trading partner
            TradingPartnerDataAccess.remove(con,pTradingPartnerId);
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartnerBean.deleteTradingPartner() Naming Exception happened. "+exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. TradingPartner.deleteTradingPartner() SQL Exception happened. "+exc.getMessage());
        } finally {
        	closeConnection(con);
        }
    }

    /**
     * <code>getNextInterchangeControlNum</code> gets the next interchange
     * control number to use.
     *
     * @param pTradingProfileId the trading profile id
     * @return an <code>int</code> value with the control number
     * @exception RemoteException if an error occurs
     */
    public int getNextInterchangeControlNum(int pTradingProfileId)
    throws RemoteException, DataNotFoundException {

        int n = 0;

        Connection conn = null;
        try {
            conn = getConnectionNoTx();
            n = BusEntityDAO.getNextTradingProfileControlNum
                    (conn, pTradingProfileId,
                    TradingProfileDataAccess.INTERCHANGE_CONTROL_NUM, true);
        } catch (Exception e) {
            throw new RemoteException("getNextInterchangeControlNum: "
                    + e.getMessage());
        } finally {
        	closeConnection(conn);
        }

        return n;
    }

    /**
     * <code>getNextGroupControlNum</code> gets the next group
     * control number to use.
     *
     * @param pTradingProfileId the trading profile id
     * @return an <code>int</code> value with the control number
     * @exception RemoteException if an error occurs
     */
    public int getNextGroupControlNum(int pTradingProfileId)
    throws RemoteException, DataNotFoundException {

        int n = 0;

        Connection conn = null;
        try {
            conn = getConnectionNoTx();
            n = BusEntityDAO.getNextTradingProfileControlNum
                    (conn, pTradingProfileId,
                    TradingProfileDataAccess.GROUP_CONTROL_NUM, true);
        } catch (Exception e) {
            throw new RemoteException("getNextGroupControlNum: "
                    + e.getMessage());
        } finally {
        	closeConnection(conn);
        }

        return n;
    }

    /**
     *Defines a single data exchange represented by a TradingProfileConfigData
     */
    public void defineDataExchange(TradingProfileConfigData pDataExchange, String pUser)
    throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            pDataExchange.setModBy(pUser);
            if(pDataExchange.getTradingProfileConfigId() == 0){
                pDataExchange.setAddBy(pUser);
                TradingProfileConfigDataAccess.insert(conn, pDataExchange);
            }else{
                TradingProfileConfigDataAccess.update(conn, pDataExchange);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }

    }

    public TradingProfileConfigDataVector
            fetchDataExchanges
            (int pTradingPartnerId )
            throws RemoteException {

        String findSql =
                TradingProfileConfigDataAccess.TRADING_PROFILE_CONFIG_ID +
                " in ( select distinct " +
                TradingProfileConfigDataAccess.TRADING_PROFILE_CONFIG_ID +
                " from " +
                TradingProfileConfigDataAccess.CLW_TRADING_PROFILE_CONFIG +
                " cfg , " +
                TradingProfileDataAccess.CLW_TRADING_PROFILE +
                " prf where prf." +
                TradingProfileDataAccess.TRADING_PARTNER_ID +
                " = " + pTradingPartnerId +
                " and cfg." +
                TradingProfileConfigDataAccess.TRADING_PROFILE_ID +
                " = prf." +
                TradingProfileDataAccess.TRADING_PROFILE_ID + " ) " ;

        Connection conn = null;
        String errmsg = null;
        try {
            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addCondition(findSql);
            dbc.addOrderBy(TradingProfileConfigDataAccess.
                    INCOMING_TRADING_PROFILE_ID);
            dbc.addOrderBy(TradingProfileConfigDataAccess.
                    DIRECTION);
            dbc.addOrderBy(TradingProfileConfigDataAccess.
                    SET_TYPE);
            return TradingProfileConfigDataAccess.select
                    (conn, dbc);
        } catch (Exception e) {
            errmsg = "fetchDataExchanges, error: " + e;
            logError( errmsg );
        } finally {
        	closeConnection(conn);
        }

        if ( null != errmsg ) {
            throw new RemoteException(errmsg);
        }

        return null;
    }

    public void deleteDataExchange(int pTradingProfileConfigId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        DBCriteria dbc;
        try {
            con = getConnection();
            TradingProfileConfigDataAccess.remove
                    (con,pTradingProfileConfigId);
        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException
                    ("deleteDataExchange() Naming Exception happened. "+
                    exc.getMessage());
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException
                    ("deleteDataExchange() SQL Exception happened. "+
                    exc.getMessage());
        } finally {
            closeConnection(con);
        }
    }


    /**
     *Returns all of the tradingProfileMappings for the supplied id
     */
    public TradingPropertyMapDataVector getTradingPropertyMapDataCollection(int tradingProfileId)
    throws RemoteException{
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(TradingPropertyMapDataAccess.TRADING_PROFILE_ID,tradingProfileId);
            crit.addOrderBy(TradingPropertyMapDataAccess.DIRECTION);
            crit.addOrderBy(TradingPropertyMapDataAccess.SET_TYPE);
            crit.addOrderBy(TradingPropertyMapDataAccess.PATTERN);
            crit.addOrderBy(TradingPropertyMapDataAccess.ORDER_BY);
            crit.addOrderBy(TradingPropertyMapDataAccess.TRADING_PROPERTY_MAP_ID);

            return TradingPropertyMapDataAccess.select(con,crit);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }


    /**
     *save the supplied TradingPropertyMapDataVector (adds updates) and returns a new TradingPropertyMapDataVector with any changes.
     */
    public TradingPropertyMapDataVector updateTradingPropertyMapDataCollection(TradingPropertyMapDataVector tpmdv)
    throws RemoteException{
        TradingPropertyMapDataVector updatedList = new TradingPropertyMapDataVector();
        Connection con = null;
        try {
            con = getConnection();
            Iterator it = tpmdv.iterator();
            while(it.hasNext()){
                TradingPropertyMapData tpmd = (TradingPropertyMapData) it.next();
                if(tpmd.getTradingPropertyMapId() > 0){
                    TradingPropertyMapDataAccess.update(con, tpmd);
                }else{
                    tpmd = TradingPropertyMapDataAccess.insert(con,  tpmd);
                }
                updatedList.add(tpmd);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
        return updatedList;
    }

    /**
     * Fetches all of the associations that are associated with this trading partner.
     * This would include the overides as well as the direct applicable associations.
     * @param pTradingPartnerId the trading partner id to fetch the associations for
     * @return a list of TradingPartnerAssocData objects that are associated with this
     * 		trading partner
     */
    public TradingPartnerAssocDataVector getTradingPartnerAssocDataVectorForPartnerAll(int pTradingPartnerId)
    throws RemoteException{
      Connection con = null;
      try{
        con = getConnection();
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,pTradingPartnerId);
        return TradingPartnerAssocDataAccess.select(con,crit);
      }catch(Exception e){
        throw processException(e);
      }finally{
        closeConnection(con);
      }
    }

    /**
     * Fetches the associations that are associated with this trading partner.
     * This would NOT include the overides.  Only the associations intended for
     * trading are included (if store trading partner only store associations are returned etc.).
     * @param pTradingPartnerId the trading partner id to fetch the associations for
     * @return a list of TradingPartnerAssocData objects that are associated with this
     * 		trading partner
     */
    public TradingPartnerAssocDataVector getTradingPartnerAssocDataVectorForPartnerForTrading(int pTradingPartnerId)
    throws RemoteException{
      Connection con = null;
      try{
        con = getConnection();
        TradingPartnerData tp = TradingPartnerDataAccess.select(con,pTradingPartnerId);
        String assocCd = null;
        if(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER.equals(tp.getTradingPartnerTypeCd())){
          assocCd = RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT;
        }else if(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR.equals(tp.getTradingPartnerTypeCd())){
          assocCd = RefCodeNames.TRADING_PARTNER_ASSOC_CD.DISTRIBUTOR;
        }else if(RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER.equals(tp.getTradingPartnerTypeCd())){
          assocCd = RefCodeNames.TRADING_PARTNER_ASSOC_CD.MANUFACTURER;
        }else if(RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE.equals(tp.getTradingPartnerTypeCd())){
          assocCd = RefCodeNames.TRADING_PARTNER_ASSOC_CD.STORE;
        }else{
          throw new RemoteException("Unkown trading partner type cd: "+tp.getTradingPartnerTypeCd());
        }

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,pTradingPartnerId);
        crit.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,assocCd);
        return TradingPartnerAssocDataAccess.select(con,crit);
      }catch(Exception e){
        throw processException(e);
      }finally{
        closeConnection(con);
      }
    }
    public void deletePropertyMappingData(int pTradingPropertyMappingId) throws RemoteException{
        Connection con = null;
        try{
            con = getConnection();
            if(pTradingPropertyMappingId>0)
            {
                TradingPropertyMapDataAccess.remove(con,pTradingPropertyMappingId);
            }
        }
        catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    public HashMap getMapTradingPartnerAssocIds(int tradingPartnerId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCrit = new DBCriteria();
            String tpat = TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC;
            String bet = BusEntityDataAccess.CLW_BUS_ENTITY;
            dbCrit.addJoinTableEqualTo(tpat, TradingPartnerAssocDataAccess.TRADING_PARTNER_ID, tradingPartnerId);
            dbCrit.addJoinCondition(tpat, TradingPartnerAssocDataAccess.BUS_ENTITY_ID, bet, BusEntityDataAccess.BUS_ENTITY_ID);
            BusEntityDataVector beds = new BusEntityDataVector();
            JoinDataAccess.selectTableInto(new BusEntityDataAccess(), beds, conn, dbCrit, 500);

            Iterator it = beds.iterator();
            IdVector acctIds  = new IdVector();
            IdVector distIds  = new IdVector();
            IdVector storeIds = new IdVector();
            IdVector manufIds = new IdVector();
            while (it.hasNext()) {
                BusEntityData b = (BusEntityData) it.next();
                if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(b.getBusEntityTypeCd())) {
                    acctIds.add(new Integer(b.getBusEntityId()));
                    int storeId = BusEntityDAO.getStoreForAccount(conn,b.getBusEntityId());
                    if(storeId > 0){
                    	Integer storeIdI = new Integer(storeId);
                    	if(!storeIds.contains(storeIdI)){
                    		storeIds.add(storeIdI);
                    	}
                    }
                } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(b.getBusEntityTypeCd())) {
                    distIds.add(new Integer(b.getBusEntityId()));
                    int storeId = BusEntityDAO.getStoreForDistributor(conn,b.getBusEntityId());
                    if(storeId > 0){
                    	Integer storeIdI = new Integer(storeId);
                    	if(!storeIds.contains(storeIdI)){
                    		storeIds.add(storeIdI);
                    	}
                    }
                } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(b.getBusEntityTypeCd())) {
                    storeIds.add(new Integer(b.getBusEntityId()));
                } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER.equals(b.getBusEntityTypeCd())) {
                    manufIds.add(new Integer(b.getBusEntityId()));
                    int storeId = BusEntityDAO.getStoreForManufacturer(conn,b.getBusEntityId());
                    if(storeId > 0){
                    	Integer storeIdI = new Integer(storeId);
                    	if(!storeIds.contains(storeIdI)){
                    		storeIds.add(storeIdI);
                    	}
                    }
                } else {
                }
            }

            //now finish up populating the store ids by getting
            //the accounts as dist parent bus entity ids
            //start with accounts
            it = acctIds.iterator();
            while (it.hasNext()) {
                int id = ((Integer) it.next()).intValue();
                int storeId = BusEntityDAO.getStoreForAccount(conn, id);
                if (storeId > 0) {
                    Integer storeIdInt = new Integer(storeId);
                    if (!storeIds.contains(storeIdInt)) {
                        storeIds.add(storeIdInt);
                    }
                }
            }

            //now do distributors
            it = distIds.iterator();
            while (it.hasNext()) {
                int id = ((Integer) it.next()).intValue();
                int storeId = BusEntityDAO.getStoreForDistributor(conn, id);
                if (storeId > 0) {
                    Integer storeIdInt = new Integer(storeId);
                    if (!storeIds.contains(storeIdInt)) {
                        storeIds.add(storeIdInt);
                    }
                }
            }

            //now do manufacturer
            it = manufIds.iterator();
            while (it.hasNext()) {
                int id = ((Integer) it.next()).intValue();
                int storeId = BusEntityDAO.getStoreForManufacturer(conn, id);
                if (storeId > 0) {
                    Integer storeIdInt = new Integer(storeId);
                    if (!storeIds.contains(storeIdInt)) {
                        storeIds.add(storeIdInt);
                    }
                }
            }

            HashMap trAssoc = new HashMap();
            trAssoc.put(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, acctIds);
            trAssoc.put(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE, storeIds);
            trAssoc.put(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR, distIds);
            trAssoc.put(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER, manufIds);

            logInfo("getMapTradingPartnerAssocIds => trading parner assoc: "+trAssoc);

            return trAssoc;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public int getPartnerId(String erpNum,int busEntityId,String entityType,boolean appTradingPartner,
                            boolean outbound,String optSetType) throws RemoteException {

        DBCriteria dbc;
        Connection conn = null;
        try {
            conn = getConnection();
            dbc = new DBCriteria();
            if (appTradingPartner) {
                dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_TYPE_CD, RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION);
                dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD, RefCodeNames.TRADING_PARTNER_STATUS_CD.ACTIVE);
            } else {
                if (busEntityId == 0) {
                    DBCriteria bedbc = new DBCriteria();
                    bedbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, entityType);
                    if (outbound) {
                        bedbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                    }
                    bedbc.addEqualTo(BusEntityDataAccess.ERP_NUM, erpNum);
                    String busEntReq = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, bedbc);
                    dbc.addOneOf(TradingPartnerAssocDataAccess.BUS_ENTITY_ID, busEntReq);
                } else {
                    dbc.addEqualTo(TradingPartnerAssocDataAccess.BUS_ENTITY_ID, busEntityId);
                }

                String tpAssocReq = TradingPartnerAssocDataAccess.getSqlSelectIdOnly(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(TradingPartnerDataAccess.TRADING_PARTNER_ID, tpAssocReq);
                dbc.addNotEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD,
                        RefCodeNames.TRADING_PARTNER_STATUS_CD.INACTIVE);

                //If the set type is speciied only return trading partners that support said set type
                if (Utility.isSet(optSetType)) {
                    DBCriteria stdbc = new DBCriteria();
                    String tpc = TradingProfileConfigDataAccess.CLW_TRADING_PROFILE_CONFIG;
                    String tp = TradingProfileDataAccess.CLW_TRADING_PROFILE;
                    stdbc.addJoinTableEqualTo(tpc, TradingProfileConfigDataAccess.SET_TYPE, optSetType);
                    stdbc.addJoinCondition(tpc, TradingProfileConfigDataAccess.TRADING_PROFILE_ID, tp, TradingProfileDataAccess.TRADING_PROFILE_ID);
                    stdbc.addDataAccessForJoin(new TradingPartnerDataAccess());
                    stdbc.addDataAccessForJoin(new TradingProfileConfigDataAccess());
                    String stsql = JoinDataAccess.getSqlSelectIdOnly(tp, TradingProfileDataAccess.TRADING_PARTNER_ID, stdbc);
                    dbc.addOneOf(TradingPartnerDataAccess.TRADING_PARTNER_ID, stsql);
                }
            }

            Integer partnerId = null;
            if (partnerId == null) {
                IdVector tpIds = TradingPartnerDataAccess.selectIdOnly(conn, dbc);
                if (tpIds.size() == 0) {
                    logError("No Tranding partner set up for entity type = " +
                            entityType + " and erp num = " + erpNum);
                    //throw new RemoteException("Error. IntegrationServicesBean.getPartnerId() throw Exception.");
                    return 0;
                } else if (tpIds.size() > 1) {
                    logError("More than one Tranding partner found for entity type = " +
                            entityType + " and erp num = " + erpNum);
                    throw new RemoteException("More than one Tranding partner found for entity type = " +
                            entityType + " and erp num = " + erpNum);
                }
                partnerId = (Integer) tpIds.get(0);

                logDebug("found partnerId=" + partnerId + " for erpNum=" + erpNum);
            }

            return partnerId.intValue();

        } catch (Exception exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error." +
                    "getPartnerId() SQL Exception happened. " +
                    exc.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public ReportItem getReportItem(int pTradingPartnerId)
            throws RemoteException {
        DBCriteria dbc;
        Connection conn = null;
        try {
            conn = getConnection();
            dbc = new DBCriteria();
            TradingPartnerData tradingPartner = getTradingPartner(pTradingPartnerId);
            final ReportItem tradingPartnerItem = Java2ReportItemTransformer
                    .transform(tradingPartner);
            dbc.addEqualTo(TradingProfileDataAccess.TRADING_PARTNER_ID, pTradingPartnerId);
            TradingProfileDataVector tradingProfileDataVector = TradingProfileDataAccess.select(conn, dbc);
            if (tradingProfileDataVector != null
                    && tradingProfileDataVector.size() > 0) {
                ReportItem tradingProfileDataVectorItem = ReportItem
                        .createValue(Java2ReportItemTransformer
                                .getSimpleName(tradingProfileDataVector
                                        .getClass()));
                tradingPartnerItem.addChild(tradingProfileDataVectorItem);
                for (int i = 0; i < tradingProfileDataVector.size(); i++) {
                    TradingProfileData tradingProfileData = (TradingProfileData) tradingProfileDataVector
                            .get(i);
                    ReportItem tradingProfileDataItem = Java2ReportItemTransformer
                            .transform(tradingProfileData);
                    tradingProfileDataVectorItem
                            .addChild(tradingProfileDataItem);
                    dbc = new DBCriteria();
                    dbc.addEqualTo(
                            TradingProfileConfigDataAccess.TRADING_PROFILE_ID,
                            tradingProfileData.getTradingProfileId());
                    TradingProfileConfigDataVector tradingProfileConfigDataVector = TradingProfileConfigDataAccess
                            .select(conn, dbc);
                    if (tradingProfileConfigDataVector != null
                            && tradingProfileConfigDataVector.size() > 0) {
                        ReportItem tradingProfileConfigDataVectorItem = ReportItem
                                .createValue(Java2ReportItemTransformer
                                        .getSimpleName(tradingProfileConfigDataVector
                                                .getClass()));
                        tradingProfileDataItem.addChild(tradingProfileConfigDataVectorItem);
                        for (int k = 0; k < tradingProfileConfigDataVector.size(); k++) {
                            tradingProfileConfigDataVectorItem.addChild(Java2ReportItemTransformer
                                    .transform(tradingProfileConfigDataVector.get(k)));
                        }
                    }
                    dbc = new DBCriteria();
                    dbc.addEqualTo(
                            TradingPropertyMapDataAccess.TRADING_PROFILE_ID,
                            tradingProfileData.getTradingProfileId());
                    TradingPropertyMapDataVector tradingPropertyMapDataVector = TradingPropertyMapDataAccess
                            .select(conn, dbc);
                    if (tradingPropertyMapDataVector != null
                            && tradingPropertyMapDataVector.size() > 0) {

                        ReportItem tradingPropertyMapDataVectorItem = ReportItem
                                .createValue(Java2ReportItemTransformer
                                        .getSimpleName(tradingPropertyMapDataVector
                                                .getClass()));
                        tradingProfileDataItem.addChild(tradingPropertyMapDataVectorItem);
                        for (int k = 0; k < tradingPropertyMapDataVector.size(); k++) {
                            tradingPropertyMapDataVectorItem.addChild(Java2ReportItemTransformer
                                    .transform(tradingPropertyMapDataVector.get(k)));
                        }
                    }
                }
            }
            return tradingPartnerItem;
        } catch (NamingException ne) {
            throw new RemoteException("NamingException:"
                    + ne.getMessage(), ne);
        } catch (SQLException sqle) {
            throw new RemoteException("SQLE Exception:"
                    + sqle.getMessage(), sqle);
        } catch (DataNotFoundException e) {
            throw new RemoteException("Generate Report Exception:"
                    + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public void setReportItem(ReportItem reportItem, String user)
            throws RemoteException {
        DBCriteria dbc;
        Connection conn = null;
        try {
            conn = getConnection();
            TradingPartnerData tradingPartner = TradingPartnerData
                    .createValue();
            ReportItem2JavaTransformer.fill(tradingPartner, reportItem);
            tradingPartner.setTradingPartnerId(0);
            DBCriteria dbcr = new DBCriteria();
            dbcr.addEqualTo(TradingPartnerDataAccess.SHORT_DESC, tradingPartner
                    .getShortDesc());
            IdVector ids = TradingPartnerDataAccess.selectIdOnly(conn, dbcr);
            if (ids.size() > 0) {
                throw new RuntimeException("TradingProfile with name '"
                        + tradingPartner.getShortDesc() + "' already used!");
            }
            saveTradingPartner(tradingPartner, user);
            List list = reportItem.getChildren();
            for (int i = 0; list != null && i < list.size(); i++) {
                ReportItem child = (ReportItem) list.get(i);
                if (TradingProfileDataVector.class.getName().endsWith(child.getName())) {
                    List list2 = child.getChildren();
                    for (int k = 0; k < list2.size(); k++) {
                        ReportItem child2 = (ReportItem) list2.get(k);
                        TradingProfileData tradingProfile = TradingProfileData
                                .createValue();
                        ReportItem2JavaTransformer.fill(tradingProfile, child2);
                        tradingProfile.setTradingProfileId(0);
                        tradingProfile.setTradingPartnerId(tradingPartner
                                .getTradingPartnerId());
                        saveTradingProfile(tradingPartner, tradingProfile, user);
                        List list3 = child2.getChildren();
                        for (int a = 0; a < list3.size(); a++) {
                            ReportItem child3 = (ReportItem) list3.get(a);
                            if (TradingProfileConfigDataVector.class.getName().endsWith(child3.getName())) {
                                List list4 = child3.getChildren();
                                for (int b = 0; b < list4.size(); b++) {
                                    TradingProfileConfigData tradingProfileConfig =  TradingProfileConfigData.createValue();
                                    ReportItem2JavaTransformer.fill(
                                            tradingProfileConfig, (ReportItem) list4
                                                    .get(b));
                                    tradingProfileConfig
                                            .setTradingProfileConfigId(0);
                                    tradingProfileConfig
                                            .setTradingProfileId(tradingProfile
                                                    .getTradingProfileId());
                                    defineDataExchange(tradingProfileConfig,
                                            user);
                                 }
                            }
                            if (TradingPropertyMapDataVector.class.getName().endsWith(child3.getName())) {
                                TradingPropertyMapDataVector vector = new TradingPropertyMapDataVector();
                                List list4 = child3.getChildren();
                                for (int b = 0; b < list4.size(); b++) {
                                    vector.clear();
                                    TradingPropertyMapData tradingPropertyMap =  TradingPropertyMapData.createValue();
                                    ReportItem2JavaTransformer.fill(
                                            tradingPropertyMap, (ReportItem) list4
                                                    .get(b));
                                    tradingPropertyMap.setTradingPropertyMapId(0);
                                    tradingPropertyMap.setTradingProfileId(tradingProfile
                                                    .getTradingProfileId());
                                    vector.add(tradingPropertyMap);
                                    updateTradingPropertyMapDataCollection(vector);
                                }
                            }

                        }
                    }
                }
            }
        } catch (NamingException ne) {
            throw new RemoteException("NamingException:" + ne.getMessage(), ne);
        } catch (SQLException sqle) {
            throw new RemoteException("SQLE Exception:" + sqle.getMessage(),
                    sqle);
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     * Returns the TradingPartnerDescView object 
     * @param    tradingPartnerId the key id
     * @throws   DataNotFoundException if trading partner object not found for id tradingPartnerId
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerFullDescView getTradingPartnerInfoById(int tradingPartnerId)
    throws RemoteException, DataNotFoundException{
        Connection conn = null;

        try{
            conn = getConnection();

            TradingPartnerFullDescView tpdv = TradingPartnerFullDescView.createValue();
            TradingPartnerData partnerD = TradingPartnerDataAccess.select(conn,tradingPartnerId);
            tpdv.setTradingPartnerData(partnerD);
            
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(TradingProfileDataAccess.TRADING_PARTNER_ID,tradingPartnerId);
            TradingProfileDataVector tradingProfileDV =TradingProfileDataAccess.select(conn,dbc);
            String sqlProfileIds = TradingProfileDataAccess.getSqlSelectIdOnly(TradingProfileDataAccess.TRADING_PROFILE_ID, dbc);
            if (tradingProfileDV.size() > 0){
            	tpdv.setTradingProfileDataVector(tradingProfileDV); 
            	dbc = new DBCriteria();
            	dbc.addOneOf(TradingProfileConfigDataAccess.TRADING_PROFILE_ID, sqlProfileIds);
            	dbc.addOrderBy(TradingProfileConfigDataAccess.INCOMING_TRADING_PROFILE_ID);
                dbc.addOrderBy(TradingProfileConfigDataAccess.DIRECTION);
                dbc.addOrderBy(TradingProfileConfigDataAccess.SET_TYPE);
            	tpdv.setTradingProfileConfigDataVector(TradingProfileConfigDataAccess.select(conn, dbc));
            	dbc = new DBCriteria();
            	dbc.addOneOf(TradingPropertyMapDataAccess.TRADING_PROFILE_ID, sqlProfileIds); 
            	dbc.addOrderBy(TradingPropertyMapDataAccess.TRADING_PROFILE_ID);
            	tpdv.setTradingPropertyMapDataVector(TradingPropertyMapDataAccess.select(conn, dbc));
            }
                        
            TradingPartnerInfo tpInfo = getTradingPartnerInfo(tradingPartnerId);
            tpInfo.setTradingPartnerData(partnerD);
            tpdv.setTradingPartnerInfo(tpInfo);
            return tpdv;

        }catch (NamingException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (SQLException e){
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    public TradingPartnerFullDescView createNewTradingPartner(TradingPartnerFullDescView tpdv)
    	throws RemoteException {
    	Connection conn = null;
    	final String  ADD_BY = "imported";
        try {
        	conn = getConnection();
            TradingPartnerData partnerD = tpdv.getTradingPartnerData();
            TradingProfileDataVector profileDV = tpdv.getTradingProfileDataVector();
            String desc = "Copy of " + partnerD.getShortDesc();
            if (desc.length()> 30){
            	desc = desc.substring(0,30);
            }
            partnerD.setShortDesc(desc);
            partnerD.setTradingPartnerStatusCd(RefCodeNames.TRADING_PARTNER_STATUS_CD.INACTIVE);
            partnerD.setAddBy(ADD_BY);
            partnerD.setModBy(ADD_BY);
            TradingPartnerDataAccess.insert(conn, partnerD);
            int tradingPartnerId = partnerD.getTradingPartnerId();
            if (profileDV != null){
            	Map oldNewTProfileIdMap = new HashMap();
            	for (Iterator iter = profileDV.iterator(); iter.hasNext();){
            		TradingProfileData profileD = (TradingProfileData) iter.next();
            		int oldProfileId = profileD.getTradingProfileId();
                	profileD.setTradingPartnerId(tradingPartnerId);
    	            profileD.setInterchangeControlNum(0);
    	            profileD.setGroupControlNum(0);
    	            profileD.setTestIndicator("T");
    	            profileD.setAddBy(ADD_BY);
    	            profileD.setModBy(ADD_BY);
    	            TradingProfileDataAccess.insert(conn, profileD);
    	            int tradingProfileId = profileD.getTradingProfileId();
    	            oldNewTProfileIdMap.put(oldProfileId, tradingProfileId);
            	}
            	
            	for (int i = 0; i < tpdv.getTradingProfileConfigDataVector().size(); i++){
                	TradingProfileConfigData tpcd = (TradingProfileConfigData) tpdv.getTradingProfileConfigDataVector().get(i);
                	int tradingProfileId = ((Integer)oldNewTProfileIdMap.get(tpcd.getTradingProfileId())).intValue();
                	tpcd.setTradingProfileId(tradingProfileId);
                	tradingProfileId = ((Integer)oldNewTProfileIdMap.get(tpcd.getIncomingTradingProfileId())).intValue();
                	tpcd.setIncomingTradingProfileId(tradingProfileId);
                	tpcd.setAddBy(ADD_BY);
                	tpcd.setModBy(ADD_BY);
                	TradingProfileConfigDataAccess.insert(conn, tpcd);
                }
                
                for (int i = 0; i < tpdv.getTradingPropertyMapDataVector().size(); i++){
                	TradingPropertyMapData tpmd = (TradingPropertyMapData) tpdv.getTradingPropertyMapDataVector().get(i);
                	int tradingProfileId = ((Integer)oldNewTProfileIdMap.get(tpmd.getTradingProfileId())).intValue();
                	tpmd.setTradingProfileId(tradingProfileId);
                	tpmd.setAddBy(ADD_BY);
                	tpmd.setModBy(ADD_BY);
                	TradingPropertyMapDataAccess.insert(conn, tpmd);
                }
            }
            if (tpdv.getTradingPartnerInfo() != null){
            	tpdv.getTradingPartnerInfo().setTradingPartnerData(partnerD);
            }
            return tpdv;            
        } catch (NamingException ne) {
            throw new RemoteException("NamingException:" + ne.getMessage(), ne);
        } catch (SQLException sqle) {
            throw new RemoteException("SQLE Exception:" + sqle.getMessage(),
                    sqle);
        } finally {
            closeConnection(conn);
        }
    }

    private Properties getTradingPartnerProperties(Connection pConn,
            int pTradingPartnerId) throws Exception {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(TradingPartnerPropDataAccess.TRADING_PARTNER_ID,
                pTradingPartnerId);
        Properties result = new Properties();
        TradingPartnerPropData item;
        TradingPartnerPropDataVector items = TradingPartnerPropDataAccess
                .select(pConn, crit);
        for (Iterator iter = items.iterator(); iter.hasNext();) {
            item = (TradingPartnerPropData) iter.next();
            String tname = item.getShortDesc();
            if (tname == null) {
                continue;
            }
            String tval = item.getValue();
            if (tval == null) {
                tval = "";
            }
            if (!result.containsKey(tname)) {
                result.put(tname, tval);
            }
        }
        return result;
    }

    private void setTradingPartnerProperties(Connection pConn,
            int pTradingPartnerId, String pModBy, Properties props)
            throws Exception {
        if (props == null || props.isEmpty()) {
            return;
        }
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            try {
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(TradingPartnerPropDataAccess.SHORT_DESC,
                        key);
                crit.addEqualTo(
                        TradingPartnerPropDataAccess.PROPERTY_TYPE_CD, key);
                crit.addEqualTo(
                        TradingPartnerPropDataAccess.TRADING_PARTNER_ID,
                        pTradingPartnerId);
                TradingPartnerPropDataVector items = null;
                try {
                    items = TradingPartnerPropDataAccess.select(pConn, crit);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                if (items == null || items.size() == 0) {
                    TradingPartnerPropData item = TradingPartnerPropData
                            .createValue();
                    item.setTradingPartnerId(pTradingPartnerId);
                    item.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                    item.setPropertyTypeCd(key);
                    item.setShortDesc(key);
                    item.setValue(val);
                    item.setModBy(pModBy);
                    item.setAddBy(pModBy);
                    TradingPartnerPropDataAccess.insert(pConn, item);
                } else {
                    TradingPartnerPropData item = (TradingPartnerPropData) items
                            .get(0);
                    item.setValue(val);
                    item.setModDate(null);
                    item.setModBy(pModBy);
                    TradingPartnerPropDataAccess.update(pConn, item);
                }
            } catch (Exception e) {
                e.printStackTrace();
                String msg = "setTradingPartnerProperties error: "
                        + e.getMessage();
                throw new Exception(msg);
            }
        }
    }
}
