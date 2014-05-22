package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ProductInformation;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * @author veronika
 */
public class GreenCertifiedVolumeReport implements GenericReportMulti {
    private static final String PATTERN = "MM/dd/yyyy HH:mm";
    private final static TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();
    private final static String reportTitle = "Green Certified Volume Report";


    protected static final String LOCATE_ACCOUNT_MULTI_S    = "LOCATE_ACCOUNT_MULTI_OPT";
    protected static final String BEG_DATE_S     = "BEG_DATE";
    protected static final String END_DATE_S     = "END_DATE";
    protected static final String DATE_FMT_S     = "DATE_FMT";

    HashMap storeMap = new HashMap();
    HashMap masterItemCategoryMap = new HashMap();
    Connection con;
    private HashMap accountMap = new HashMap();
    private int freezeColumn = 4;
    private int freezeRow = 4;


    BusEntityDataVector certifiedCompanies = new  BusEntityDataVector();

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
            throws Exception {
        TimeZone tzSource = DEFAULT_TIMEZONE;
        APIAccess factory = new APIAccess();
        //Distributor distEjb = factory.getDistributorAPI();

        con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        //Lists of report results
        ArrayList greenCertItems = new ArrayList();

        String begDateS = (String) pParams.get(BEG_DATE_S);
        String endDateS = (String) pParams.get(END_DATE_S);
        String dateFmt = (String) pParams.get(DATE_FMT_S);

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }

        if (!ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if (!ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }


        String dateWhere = getDateWhere(begDateS, endDateS, dateFmt, tzSource, DEFAULT_TIMEZONE);
        String accountIdS = (String) pParams.get(LOCATE_ACCOUNT_MULTI_S);

        Iterator it3 = pParams.keySet().iterator();
        String repParams = "";
        while (it3.hasNext()) {
            Object o = it3.next();
            repParams += " " + o + "::" + pParams.get(o);
        }

        ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserAccessDescription userDesc = repUtil.getUserAccessDescription(pParams, con);

        UserRightsTool urt = new UserRightsTool(userDesc.getUserData());

        ProductInformation productInfoEjb = factory.getProductInformationAPI();
        BusEntitySearchCriteria bcr = new BusEntitySearchCriteria();
        bcr.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        certifiedCompanies = productInfoEjb.getBusEntityByCriteria(bcr,
                      RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);

        String accountCond = "";
        if (null != accountIdS && accountIdS.trim().length() > 0) {
                accountCond += " AND o.account_id in (" + accountIdS + ") \n";
                freezeRow += 1;
        }

        String storeIdStr = (String) pParams.get("STORE");
        String storeCondSql = "";
        if (Utility.isSet(storeIdStr)) {
            storeCondSql = " and o.store_id = " + storeIdStr + "\n";
        }

        String userAccessCondSql = "";
        if (!userDesc.hasAccessToAll()) {
          	if (userDesc.hasStoreAccess()) {
          	    userAccessCondSql += " and o.store_id in (" + userDesc.getAuthorizedSql() + ") ";
          	} else if (userDesc.hasAccountAccess()) {
                userAccessCondSql += " and o.account_id in (" + userDesc.getAuthorizedSql() + ") ";
            } else if (userDesc.hasSiteAccess()) {
                userAccessCondSql += " and o.site_id in (" + userDesc.getAuthorizedSql() + ") ";
            }
        }


        String greenCertItemsSql =
                    "select distinct \n" +
                            "item.item_id,\n" +
                            "o.account_id as accountId,\n" +
                            "acc.short_desc as accountName,\n" +
                            "o.site_id as siteId,\n" +
                            "site.short_desc as siteName, \n" +
                            "siteRefNum.Clw_Value as siteBudgetRefNum,\n" +
                            "distSiteRefNum.Clw_Value as distRefNum,\n" +
                            "address1, \n" +
                            "address2,\n" +
                            "city, \n" +
                            "state_province_cd as state, \n" +
                            "postal_code as postalCode, \n" +
                            "item.item_sku_num as storeSku, \n" +
                            "item.dist_item_sku_num as distSku,\n" +
                            "item.item_short_desc as itemDescr, \n" +
                            "item.manu_item_short_desc as manuf,\n" +
                            "item.manu_item_sku_num as manufSku,\n" +
                            "item.item_uom as uom,\n" +
                            "item.item_pack as pack,\n" +
                            "original_order_date as origOrderDate,\n" +
                            "total_quantity_ordered as qty, \n" +
                            "cust_contract_price as catalogPrice\n" +
                            " \n" +
                   "from clw_order o, clw_order_item item, clw_bus_entity site, clw_address address, clw_bus_entity acc,\n" +
                            "clw_property siteRefNum, clw_property distSiteRefNum, \n" +
                            "(select item_id,count(item_id) as counts from clw_item_mapping  " +
                            "   where item_mapping_cd='" +RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY + "' group by item_id) certcomp\n" +
                            "\n" +
                   "where o.order_id = item.order_id\n" +
                            "and site.bus_entity_id= o.site_id\n" +
                            "and address.bus_entity_id = o.site_id\n" +
                            "and ("+ dateWhere +") \n" +
                            "and (order_status_cd = '" + RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED + "'\n" +
                            "     or order_status_cd = '" + RefCodeNames.ORDER_STATUS_CD.ORDERED + "'\n " +
                            "     or order_status_cd = '" + RefCodeNames.ORDER_STATUS_CD.INVOICED + "'\n " +
                            "     or order_status_cd = '" + RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO + "'\n " +
                            ")\n" +
                            "and order_item_status_cd != '" + RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED + "'\n" +
                            "and acc.bus_entity_id = o.account_id\n" +
                            "and siteRefNum.Bus_Entity_Id(+) = o.site_id \n" +
                            "and siteRefNum.Short_Desc(+) = '"+ RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+ "'\n" +
                            "and distSiteRefNum.Bus_Entity_Id(+) = o.site_id \n" +
                            "and distSiteRefNum.Short_Desc(+) = '"+ RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER+"'\n" +
                            "and certcomp.item_id = item.item_id\n" +
                            "and certcomp.counts > 0\n" +
                            accountCond +
                            storeCondSql +
                            userAccessCondSql +
                    "order by accountName, siteName, distSku";


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(greenCertItemsSql);
            while (rs.next()) {
                GreenCertifiedVolumeReport.GreenCertItem record = new GreenCertifiedVolumeReport.GreenCertItem();

                record.itemId = rs.getInt("item_id");
                record.accountId = rs.getInt("accountId");
                record.accountName = rs.getString("accountName");
                record.siteId = rs.getInt("siteId");
                record.siteName = rs.getString("siteName");
                record.siteBudgetRefNum = rs.getString("siteBudgetRefNum");
                record.distRefNum = rs.getString("distRefNum");
                record.address1 = rs.getString("address1");
                record.address2 = rs.getString("address2");
                record.city = rs.getString("city");
                record.state = rs.getString("state");
                record.postalCode = rs.getString("postalCode");
                record.storeSku = rs.getString("storeSku");
                record.distSku = rs.getString("distSku");
                record.itemDescr = rs.getString("itemDescr");
                record.manuf = rs.getString("manuf");
                record.manufSku = rs.getString("manufSku");
                record.uom = rs.getString("uom");
                record.pack = rs.getString("pack");
                record.origOrderDate = rs.getDate("origOrderDate");
                record.qty = rs.getInt("qty");
                record.catalogPrice = rs.getBigDecimal("catalogPrice");

                greenCertItems.add(record);
            }
            rs.close();
            stmt.close();

            // get cert for every item
            Iterator i = greenCertItems.iterator();
            String itemCertCompSql =
                "select im.bus_entity_id\n" +
                        " from clw_item_mapping im \n" +
                        "where im.item_mapping_cd='ITEM_CERTIFIED_COMPANY' \n" +
                        "and im.item_id = ?";
            PreparedStatement pstmt = con.prepareStatement(itemCertCompSql);
            while (i.hasNext()) {
                GreenCertItem item = (GreenCertItem)i.next();
                int itemId = item.itemId;
                pstmt.setInt(1, itemId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    item.itemCertCompanies.add(new Integer(rs.getInt(1)));
                }
                rs.close();
                pstmt.clearParameters();
            }


            GenericReportColumnViewVector title = getReportTitle(pParams);
            processList(greenCertItems, resultV, "Green Cert Items", getReportHeader(), title, true);

        for (int j = 0; j < resultV.size(); j++) {
            GenericReportResultView report = (GenericReportResultView) resultV.get(j);
            report.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            report.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            report.setFreezePositionRow(freezeRow);
            report.setFreezePositionColumn(freezeColumn);
        }
        return resultV;
    }

    private GenericReportColumnViewVector getReportTitle(Map pParams) {
        GenericReportColumnViewVector title = new GenericReportColumnViewVector();
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",reportTitle,0,255,"VARCHAR2"));


       String accountIdS = (String) pParams.get(LOCATE_ACCOUNT_MULTI_S);
       String controlLabel = ReportingUtils.getControlLabel(LOCATE_ACCOUNT_MULTI_S, pParams, "Account Name");

        if (Utility.isSet(accountIdS)) {
            StringBuffer accountNamesStr = new StringBuffer();
            try {
              String sql ="select short_desc from CLW_BUS_ENTITY where bus_entity_id in ( " + accountIdS + ")";
              Statement stmt = con.createStatement();
              ResultSet rs = stmt.executeQuery(sql);
              while (rs.next()) {
                String be = new String(rs.getString(1));
                if (accountNamesStr.length() == 0) {
                  accountNamesStr.append(be);
                }
                else {
                  accountNamesStr.append(", " + be);
                }
              }
              stmt.close();
            }
            catch (SQLException exc) {
              exc.printStackTrace();
            }

            title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + accountNamesStr.toString(),0,255,"VARCHAR2"));

        }
        String begDateS = (String) pParams.get(BEG_DATE_S);
        String endDateS = (String) pParams.get(END_DATE_S);
        String datePeriodSelected = "Date Begin: " + begDateS + "; End: " + endDateS; 

        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", datePeriodSelected ,0,255,"VARCHAR2"));

        return title;
    }

    protected void processList
            (List toProcess,
             GenericReportResultViewVector resultV,
             String name, GenericReportColumnViewVector header,
             GenericReportColumnViewVector title,
             boolean alwaysCreate) {

        Iterator it = toProcess.iterator();
        if (alwaysCreate || it.hasNext()) {
            GenericReportResultView result =
                    GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            while (it.hasNext()) {
                result.getTable().add(((GreenCertifiedVolumeReport.aRecord) it.next()).toList());
            }
            result.setColumnCount(header.size());
            result.setTitle(title);
            result.setHeader(header);
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            result.setName(name);
            resultV.add(result);
        }
    }


    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Account Id", 0, 38, "NUMBER", "10", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2", "30", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Site Id", 0, 38, "NUMBER", "10", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2", "30", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Budget Ref Num", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Ref Num", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 1", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 2", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Postal Code", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Store SKU", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist SKU", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Description", 0, 255, "VARCHAR2", "40", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2", "30", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Mfg SKU", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Original Order Date", 0, 0, "DATE", "20", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Quantity", 0, 38, "NUMBER", "10", false));
      	header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Catalog Price", 2,20, "NUMBER", "10", false));

        Iterator j = certifiedCompanies.iterator();
        while (j.hasNext()) {
           BusEntityData certCompany = (BusEntityData)j.next();
           header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", certCompany.getShortDesc(), 0, 255, "VARCHAR2", "20", false));
        }       
        return header;
    }

    private interface aRecord {
        ArrayList toList();
    }


    protected class GreenCertItem implements aRecord {
        int itemId;
        int accountId;
        String accountName;
        int siteId;
        String siteName;
        String siteBudgetRefNum;
        String distRefNum;
        String address1;
        String address2;
        String city;
        String state;
        String postalCode;
        String storeSku;
        String distSku;
        String itemDescr;
        String manuf;
        String manufSku;
        String uom;
        String pack;
        Date origOrderDate;
        int qty;
        BigDecimal catalogPrice;

        ArrayList itemCertCompanies = new ArrayList();

        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(accountId);
            list.add(accountName);
            list.add(siteId);
            list.add(siteName);
            list.add(siteBudgetRefNum);
            list.add(distRefNum);
            list.add(address1);
            list.add(address2);
            list.add(city);
            list.add(state);
            list.add(postalCode);
            list.add(storeSku);
            list.add(distSku);
            list.add(itemDescr);
            list.add(manuf);
            list.add(manufSku);
            list.add(uom);
            list.add(pack);
            list.add(origOrderDate);
            list.add(qty);
            list.add(catalogPrice);


            Iterator j = certifiedCompanies.iterator();
            while (j.hasNext()) {
                BusEntityData certCompany = (BusEntityData)j.next();
                String val = "";
                Iterator i = itemCertCompanies.iterator();
                while (i.hasNext()) {
                    int companyId = ((Integer)i.next()).intValue();
                    if (companyId == certCompany.getBusEntityId()) {
                        val = "X";
                    }
                }
                list.add(val);
            }
            return list;
        }
    }




    public final static String getDateWhere(String begDateS, String endDateS,
            String dateFmt, TimeZone tzSource, TimeZone tzTarget)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        Date startDate = sdf.parse(begDateS);
        Date endDateOrig = sdf.parse(endDateS);
        Date endDate = addDays(endDateOrig,1);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(PATTERN);
        Date startDateTarget = convertDate(startDate, tzSource,
                tzTarget);
        Date endDateTarget = convertDate(endDate, tzSource,
                tzTarget);
        String startDateTargetS = sdfTarget.format(startDateTarget);
        String endDateTargetS = sdfTarget.format(endDateTarget);
        return " TO_DATE(TO_CHAR(o.original_order_date, 'MM/dd/yyyy') || TO_CHAR(o.original_order_time, 'HH24:MI'), 'MM/dd/yyyyHH24:MI') "
                + " BETWEEN TO_DATE('"
                + startDateTargetS
                + "','MM/dd/yyyy HH24:MI') AND TO_DATE('"
                + endDateTargetS
                + "','MM/dd/yyyy HH24:MI') ";
    }

    private final static int ONE_SECOND = 1000;

    private final static int ONE_MINUTE = ONE_SECOND * 60;

    private final static int ONE_HOUR = ONE_MINUTE * 60;

    public final static Date convertDate(Date source,
            TimeZone tzSource, TimeZone tzTarget) {
        long timeInMillis = source.getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(source);
        int millis = c.get(Calendar.HOUR_OF_DAY) * ONE_HOUR
                + c.get(Calendar.MINUTE) * ONE_MINUTE;
        long delta1 = tzSource.getOffset(c.get(Calendar.ERA), c
                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
                millis);
        int delta2 = tzTarget.getOffset(c.get(Calendar.ERA), c
                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
                millis);
        return new Date(timeInMillis + (delta2 - delta1));
    }

    private final static SimpleDateFormat SDF_DATE = new SimpleDateFormat("MM/dd/yyyy");
    private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
    private final static SimpleDateFormat SDF_FULL = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private final static Date getDate(Date date,
            Timestamp time) throws Exception {
    	try{
    		return SDF_FULL.parse(SDF_DATE.format(date) + " " + SDF_TIME.format(time));
    	}catch(Exception e){
    		return date;
    	}
    }

    /**
     * Add nDays to input date.
     *
     * @param nDays  Number of days to add. May be negative.
     * @return  Date as requested.
     */
    public static Date addDays(Date date, int nDays)
    {
    	if (date == null)
        throw new IllegalArgumentException("Date is null in GreenCertifiedVolumeReport.addDays()");
      // Create a calendar based on given date
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      // Add/subtract the specified number of days
      calendar.add(Calendar.DAY_OF_MONTH, nDays);
      return calendar.getTime();
    }



}
