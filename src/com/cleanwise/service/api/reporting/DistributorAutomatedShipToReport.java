/*

 * ShipToReport.java

 *

 * Created on February 2, 2003, 6:05 PM

 */



package com.cleanwise.service.api.reporting;



import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.util.DBCriteria;

import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.service.api.util.ConnectionContainer;

import com.cleanwise.service.api.dao.*;

import com.cleanwise.service.api.util.Utility;

import java.util.Date;

import java.util.ArrayList;

import java.util.List;

import java.util.Date;

import java.util.Map;

import java.util.HashMap;

import java.util.Iterator;

import java.sql.*;

import java.rmi.RemoteException;

/**

 *

 * @author  bstevens

 */

public class DistributorAutomatedShipToReport implements GenericReport {

    private HashMap distRefMap;

    private HashMap distIdToTrading;

    private HashMap acctRefMap;

    private HashMap distErpNumToIdMap;

    private ArrayList shipTos;

    String mTradingPartnerSiteIdentiferType = null;

    private static final String EMPTY = "";

    private static final String groupSql =

    	" group by site.short_desc, ca.address1, ca.address2, ca.address3, ca.address4, ca.city, "+

        "ca.state_province_cd, ca.postal_code, "+

        "site.mod_date, site.add_date, ca.mod_date, ca.add_date, "+

        "site.bus_entity_id, orderi.dist_erp_num";

    private static String shiptoSql =

    "SELECT "+

    "DISTINCT "+

    "max(acct.short_desc) as account_name , " +

    "site.short_desc, ca.address1, ca.address2, ca.address3, ca.address4, ca.city, "+

    "ca.state_province_cd, ca.postal_code, "+

    "site.mod_date as smoddate, site.add_date as sadddate, ca.mod_date as addrmoddate, ca.add_date as addradddate, "+

    "site.bus_entity_id as site_id, orderi.dist_erp_num, max(order1.account_id) as account_id "+

    "FROM clw_bus_entity site, clw_address ca,  "+

    "clw_order order1, clw_order_item orderi "+

    " , clw_bus_entity acct " +

    "WHERE  "+

    "site.bus_entity_type_cd = 'SITE'  "+

    "AND  site.bus_entity_status_cd = 'ACTIVE'  "+

    "AND order1.order_status_cd IN ("+Utility.toCommaSting(ReportingUtils.commitedOrderStatusCodes,'\'')+")"+

    "AND  site.bus_entity_id = ca.bus_entity_id  "+

    "AND  order1.order_id = orderi.order_id  "+

    "AND  order1.site_id = site.bus_entity_id "+

    "AND  order1.account_id = acct.bus_entity_id "+

    "AND  ca.address_type_cd = 'SHIPPING'"+

    "AND  order1.original_order_date > sysdate - 10 ";





    private static String shiptoSqlNoOrder =

    "SELECT DISTINCT "+

    "acct.short_desc as account_name , " +

    "site.short_desc, ca.address1, ca.address2, ca.address3, ca.address4, ca.city, "+

    "ca.state_province_cd, ca.postal_code, "+

    "site.mod_date AS smoddate, site.add_date AS sadddate, ca.mod_date AS addrmoddate, ca.add_date AS addradddate, "+

    "site.bus_entity_id AS site_id, bas.bus_entity2_id AS account_id, cadist.bus_entity_id AS dist_id "+

    "FROM  "+

    "CLW_BUS_ENTITY site, "+

    "CLW_BUS_ENTITY acct, "+

    "CLW_ADDRESS ca,  "+

    "clw_catalog_structure cadist,  "+

    "CLW_CATALOG_ASSOC casite,  "+

    "CLW_CATALOG c,  "+

    "CLW_BUS_ENTITY_ASSOC bas   "+

    "WHERE   "+

    "site.bus_entity_type_cd = 'SITE'   "+

    "AND  site.bus_entity_status_cd = 'ACTIVE'   "+

    "AND  site.bus_entity_id = ca.bus_entity_id   "+

    "AND  ca.address_type_cd = 'SHIPPING'  "+

    "AND  c.catalog_id = cadist.catalog_id  "+

    "AND  c.catalog_status_cd = 'ACTIVE'  "+

    "AND  casite.catalog_id = c.catalog_id "+

    "AND  casite.bus_entity_id = site.bus_entity_id   "+

    "AND  casite.catalog_assoc_cd = 'CATALOG_SITE'  "+

    "AND  cadist.catalog_structure_cd = 'CATALOG_PRODUCT'  "+

    "AND  cadist.status_cd = 'ACTIVE'  "+

    "AND  bas.bus_entity2_id = acct.bus_entity_id  "+

    "AND  bas.bus_entity1_id = site.bus_entity_id  "+

    "AND  bas.bus_entity_assoc_cd = 'SITE OF ACCOUNT' ";





    private static String distRefSql =

    "SELECT bus_entity_id, clw_value FROM clw_property WHERE "+

    "property_status_cd != 'INACTIVE' AND short_desc = 'DISTRIBUTORS_COMPANY_CODE'";



    private static String acctRefSql =

    "SELECT bus_entity_id, clw_value FROM clw_property WHERE "+

    "property_status_cd != 'INACTIVE' AND short_desc = 'EDI_SHIP_TO_PREFIX'";



    private static String distErpRefSql =

    "SELECT bus_entity_id, erp_num FROM clw_bus_entity WHERE bus_entity_type_cd = 'DISTRIBUTOR'";



    public com.cleanwise.service.api.value.GenericReportResultView process(com.cleanwise.service.api.util.ConnectionContainer pCons, com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams) throws Exception {

        Connection con = pCons.getDefaultConnection();

        java.util.Date theDate = null;

        String begDate = (String)pParams.get("BEG_DATE");

        if(!Utility.isSet(begDate)){

            begDate = (String)pParams.get("BEG_DATE_OPT");

        }

        String distId = (String)pParams.get("DISTRIBUTOR");

        if(!Utility.isSet(distId)){

            distId = (String)pParams.get("DISTRIBUTOR_OPT");

        }

        String groupId = (String)pParams.get("GROUP");

        if(!Utility.isSet(groupId)){

            groupId = (String)pParams.get("GROUP_OPT");

        }



        String updateLogTableStr = (String)pParams.get("ALLOW_UPDATES");

        boolean updateLogTable = false;

        if(Utility.isTrue(updateLogTableStr)){



            updateLogTable = true;

        }


        StringBuffer shipToSqlBuf = new StringBuffer(shiptoSql);

        StringBuffer shipToSqlBuf2 = new StringBuffer(shiptoSqlNoOrder);

        StringBuffer distRefSqlBuf = new StringBuffer(distRefSql);

        StringBuffer acctRefSqlBuf = new StringBuffer(acctRefSql);

        distErpNumToIdMap = new HashMap();





        if(Utility.isSet(begDate)) {

            theDate = ReportingUtils.parseDate(begDate);

        }

        ArrayList distributorErps = null;

        ArrayList distributorIds = null;

        if(Utility.isSet(distId)) {

            distributorErps = new ArrayList();

            distributorIds = new ArrayList();

            String distErpRefSqlTmp = distErpRefSql + " AND bus_entity_id = ?";

            PreparedStatement stmt = con.prepareStatement(distErpRefSqlTmp.toString());
            stmt.setInt(1, Integer.parseInt(distId));

            ResultSet rs = stmt.executeQuery();



            if(rs.next()){

                String erp = rs.getString("ERP_NUM");

                Integer id = new Integer(rs.getInt("BUS_ENTITY_ID"));

                distributorIds.add(id);

                distributorErps.add(erp);

                distErpNumToIdMap.put(erp,id);

            }

            distRefSqlBuf.append("AND bus_entity_id = "+distId);

        }

        if(Utility.isSet(groupId)) {

            String distErpRefSqlTmp = distErpRefSql +

            " AND bus_entity_id IN (SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = ?)";

            distRefSqlBuf.append("AND bus_entity_id IN (SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = ?)");


            PreparedStatement stmt = con.prepareStatement(distErpRefSqlTmp.toString());
            stmt.setInt(1, Integer.parseInt(groupId));

            ResultSet rs = stmt.executeQuery();

            if(distributorErps == null){

                distributorErps = new ArrayList();

            }

            if(distributorIds == null){

                distributorIds = new ArrayList();

            }

            while(rs.next()){

                String erp = rs.getString("ERP_NUM");

                Integer id = new Integer(rs.getInt("BUS_ENTITY_ID"));

                distributorErps.add(erp);

                distributorIds.add(id);

                distErpNumToIdMap.put(erp,id);

            }

            if (distributorIds.size() == 0) {
                String mess = "^clw^ Distributors not found for group " + groupId+ " ^clw^";
                throw new Exception(mess);

            }

        }



        if(distributorErps != null){

            Iterator it = distributorErps.iterator();

            StringBuffer inClause = new StringBuffer();

            while(it.hasNext()){

                String erpNum = (String) it.next();

                inClause.append("'");

                inClause.append(erpNum);

                inClause.append("'");

                if(it.hasNext()){

                    inClause.append(",");

                }

            }


            if (inClause.length() > 0) {
                shipToSqlBuf.append("and  orderi.dist_erp_num in ( "+inClause+") ");
            }

        }

        shipToSqlBuf.append(groupSql);


        if(distributorIds != null){

            Iterator it = distributorIds.iterator();

            StringBuffer inClause = new StringBuffer();

            while(it.hasNext()){

                Integer id = (Integer) it.next();

                inClause.append("'");

                inClause.append(id);

                inClause.append("'");

                if(it.hasNext()){

                    inClause.append(",");

                }

            }

            if (inClause.length() > 0) {
                shipToSqlBuf2.append("and  cadist.bus_entity_id in ( "+inClause+") ");
            }

        }





        PreparedStatement stmt = con.prepareStatement(distRefSqlBuf.toString());
        if(Utility.isSet(groupId)) {
            stmt.setInt(1, Integer.parseInt(groupId));
        }

        ResultSet rs = stmt.executeQuery();

        distRefMap = new HashMap();

        while (rs.next() ) {

            Integer key = new Integer(rs.getInt("bus_entity_id"));

            String  value = rs.getString("clw_value");

            if(Utility.isSet(value)){

                distRefMap.put(key, value);

            }else{

                distRefMap.put(key, null);

            }

        }

        rs.close();

        stmt.close();


        stmt = con.prepareStatement(acctRefSql);

        rs = stmt.executeQuery();

        acctRefMap = new HashMap();

        while (rs.next() ) {

            Integer key = new Integer(rs.getInt("bus_entity_id"));

            acctRefMap.put(key, rs.getString("clw_value"));

        }

        rs.close();

        stmt.close();




        distIdToTrading = new HashMap();

        shipTos = new ArrayList();



        stmt = con.prepareStatement(shipToSqlBuf.toString());

        rs = stmt.executeQuery();

        while (rs.next() ) {

            processesResultSet(con,rs,true);

        }

        stmt.close();

        rs.close();


        stmt = con.prepareStatement(shipToSqlBuf2.toString());

        rs = stmt.executeQuery();

        while (rs.next() ) {

            processesResultSet(con,rs,false);

        }

        stmt.close();

        rs.close();


        java.util.Date now = new java.util.Date();


        ReportExchangeLogDataVector logUpdates = new ReportExchangeLogDataVector();


        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(ReportExchangeLogDataAccess.GENERIC_REPORT_ID,pReportData.getGenericReportId());

        if(Utility.isSet(distId)) {
            crit.addLike(ReportExchangeLogDataAccess.RECORD_KEY,distId+"::%");
        } else {
            if (distributorIds!= null && distributorIds.size() > 0) {
                Iterator i = distributorIds.iterator();
                StringBuffer cond = new StringBuffer();
                while (i.hasNext()) {
                    int iDistId = ((Integer)i.next()).intValue();
                    if (cond.length() > 0) cond.append(" OR ");
                    cond.append(ReportExchangeLogDataAccess.RECORD_KEY + " like '" + iDistId+"::%'");
                }
                if (cond.length() > 0) {
                    cond.insert(0,"(").append(")");
                    crit.addCondition(cond.toString());
                }
            }
        }

        ReportExchangeLogDataVector logV = ReportExchangeLogDataAccess.select(con,crit);

        Iterator repIt = shipTos.iterator();

        while(repIt.hasNext()){

            RowHolder row = (RowHolder) repIt.next();

            boolean found = false;

            boolean doneRemove = false;

            Iterator it = logV.iterator();

            String key = row.distId+"::"+row.siteId;

            String val = rowToString(row.row);

            while(it.hasNext()){

                ReportExchangeLogData log = (ReportExchangeLogData) it.next();

                if(key.equals(log.getRecordKey())){

                    found = true;

                    if(val.equals(log.getValue())){

                        if(!doneRemove){

                            repIt.remove();

                            doneRemove = true;

                        }

                    }else{

                        row.row.set(0,"C");

                        log.setSentDate(now);

                        log.setValue(val);

                        logUpdates.add(log);

                    }

                }

            }

            if(!found){

                row.row.set(0,"A");

                ReportExchangeLogData newEntry = ReportExchangeLogData.createValue();

                newEntry.setGenericReportId(pReportData.getGenericReportId());

                newEntry.setRecordKey(key);

                newEntry.setSentDate(now);

                newEntry.setValue(val);

                logUpdates.add(newEntry);

            }

        }


        if(updateLogTable){

            Iterator it = logUpdates.iterator();

            while(it.hasNext()){

                ReportExchangeLogData log = (ReportExchangeLogData) it.next();

                if(log.getReportExchangeLogId() == 0){

                    ReportExchangeLogDataAccess.insert(con, log);

                }else{

                    ReportExchangeLogDataAccess.update(con, log);

                }

            }

        }



        GenericReportResultView ret = GenericReportResultView.createValue();

        ret.setTable(toRow(shipTos));

        ret.setHeader(getReportHeader());

        ret.setColumnCount(ret.getHeader().size());


        return ret;

    }



    private void processesResultSet(Connection con, ResultSet rs,boolean distErpNum) throws Exception {

        List row = new ArrayList();

        //add null temporarily, we will be changing this later

        row.add(null);



        Integer currDistId;

        if(distErpNum){

            String distErp = rs.getString("DIST_ERP_NUM");

            currDistId = (Integer) distErpNumToIdMap.get(distErp);

        }else{

            currDistId = new Integer(rs.getInt("DIST_ID"));

        }

        if (currDistId == null) {
            currDistId = new Integer(0);
        }


        String distRef = (String)distRefMap.get(currDistId);

        if(distRef == null){
            distRef = currDistId.toString();
        }

        row.add(distRef);


        TradingPartnerData tpd = null;

        if(distIdToTrading.containsKey(currDistId)){

            tpd = (TradingPartnerData) distIdToTrading.get(currDistId);

        }else{

            DBCriteria crit = new DBCriteria();

            crit.addCondition("trading_partner_id IN (select trading_partner_id from clw_trading_partner_assoc where bus_entity_id = "+currDistId+")");

            TradingPartnerDataVector tpdv = TradingPartnerDataAccess.select(con,crit);

            if(tpdv.size() > 0){

                tpd = (TradingPartnerData) tpdv.get(0);

            }

            distIdToTrading.put(currDistId,tpd);

        }


        if(mTradingPartnerSiteIdentiferType == null){
            if(tpd!=null){
                mTradingPartnerSiteIdentiferType = tpd.getSiteIdentifierTypeCd();
            }

        }else {

            if(tpd!=null){

                if(!mTradingPartnerSiteIdentiferType.equals(tpd.getSiteIdentifierTypeCd())){
                    String mess = "^clw^ Multiple Site Identifier Type configs for theese distributors ^clw^";
                    throw new Exception(mess);
                    //throw new RemoteException("Multiple Site Identifier Type configs for these dists");

                }

            }

        }

        Integer key = new Integer(rs.getInt("ACCOUNT_ID"));

        Integer siteId = new Integer(rs.getInt("SITE_ID"));



        String EDIAccountId = (String) acctRefMap.get(key);

        if(!Utility.isSet(EDIAccountId)){

            EDIAccountId = key.toString();

        }

        //create the row

        if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_ACCOUNT_IN_REF.equals(mTradingPartnerSiteIdentiferType)){

            row.add(EDIAccountId);

            row.add(siteId);

        }else{

            if(tpd!=null){

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(TradingPropertyMapDataAccess.TRADING_PROFILE_ID,tpd.getTradingPartnerId());

            TradingPropertyMapDataVector tradingPropertyMapData = TradingPropertyMapDataAccess.select(con, crit);

            row.add(Utility.getConcatonatedIdentifier(EDIAccountId,siteId.toString(),

                        tradingPropertyMapData,RefCodeNames.EDI_TYPE_CD.T850,"OUT"));

            }

            else

            {

                String mess = "^clw^ Identifiers can't be processed.Trading Partner Data is not set. ^clw^";
                throw new Exception(mess);
             //throw new RemoteException("Identifiers can't be processed.Trading Partner Data is not set");

            }

        }
        row.add(rs.getString("ACCOUNT_NAME"));

        row.add(rs.getString("SHORT_DESC"));

        row.add(rs.getString("ADDRESS1"));

        row.add(rs.getString("ADDRESS2"));

        row.add(rs.getString("ADDRESS3"));

        row.add(rs.getString("CITY"));

        row.add(rs.getString("STATE_PROVINCE_CD"));

        row.add(rs.getString("POSTAL_CODE"));

        RowHolder holder = new RowHolder(row, currDistId,siteId);

        if(!shipTos.contains(holder)){

            shipTos.add(holder);

        }

    }



    private GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Code",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Dist Code",0,255,"VARCHAR2"));

        if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_ACCOUNT_IN_REF.equals(mTradingPartnerSiteIdentiferType)){

            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account",0,255,"VARCHAR2"));

            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship_To",0,255,"VARCHAR2"));

        }else{

            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship_To",0,255,"VARCHAR2"));

        }

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address_Name",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address1",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address2",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Address3",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","City",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Postal_Cd",0,255,"VARCHAR2"));

        return header;

    }



    private static char SEPERATOR = '\t';

    private String rowToString(List row){

        StringBuffer retVal = new StringBuffer();

        Iterator it = row.iterator();

        it.next(); //skip the first as it is the "code" value.

        it.next(); //skip second as it is the dist reference, and is contained in the key.

        //if the dist reference has changed they don't care, it's their reference

        while(it.hasNext()){

            Object o = it.next();

            retVal.append(SEPERATOR);

            if(o != null){

                retVal.append(o.toString());

            }

        }

        return retVal.toString();

    }



    private ArrayList toRow(List rowHolderList){

        Iterator it = rowHolderList.iterator();

        ArrayList newList = new ArrayList();

        while(it.hasNext()){

            newList.add(((RowHolder)it.next()).row);

        }

        return newList;

    }



    private class RowHolder{

        List row;

        Integer distId;

        Integer siteId;

        RowHolder(List pRow, Integer pDistId, Integer pSiteId){

            row = pRow;

            distId = pDistId;

            siteId = pSiteId;

        }



        public boolean equals(Object o){

            boolean equal = false;

            if(o instanceof RowHolder){

                RowHolder rh = (RowHolder) o;

                if(rh.distId == this.distId && rh.siteId == this.siteId){

                    equal = true;

                }

            }

            return equal;

        }

    }



}

