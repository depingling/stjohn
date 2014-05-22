/*
 * ReportingUtils.java
 *
 * Created on February 3, 2003, 10:43 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.MathContext;

//import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *Class that houses some of the common methods used in this package.
 * @author  bstevens
 */
public class ReportingUtils {
	public static final String DEFAULT_REPORT_NAME="Detail";

        public static final String PAGE_TITLE = "PageTitle";
        public static final String PAGE_FOOTER = "PageFooter";
        public static final String TABLE_HEADER = "TableHeader";
        public static final String TABLE_DATA = "TableData";
        public static final String NEGATIVE_NUMBER = "NegativeNumber";
        public static final String GROUP_HEADER = "GroupHeader";
        public static final String GROUP_TOTAL = "GroupFooter";

        public static final String DATE_STYLE = "DateStyle";
        public static final String TIME_STYLE = "TimeStyle";
        public static final String INTEGER_STYLE = "IntegerStyle";
        public static final String INTEGER_SEPARATOR_STYLE = "IntegerSeparatorStyle";
        public static final String FLOAT_STYLE = "FloatStyle";
        public static final String FLOAT_SEPARATOR_STYLE = "FloatSeparatorStyle";
        public static final String ACCOUNTING_STYLE = "AccountingStyle";
        public static final String NEGATIVE_PERCENT_STYLE = "NegativePercentStyle";
        public static final String PERCENT_STYLE = "PercentStyle";

    public interface ALIGN {
          public static final String LEFT = "LEFT",
                                     RIGHT = "RIGHT",
                                     CENTER = "CENTER",
                                     GENERAL = "GENERAL",
                                     JUSTIFY = "JUSTIFY";
        }

        public interface DATA_CATEGORY {
          public static final String DATE = "DATE",
                                     TIME = "TIME",
                                     NUMBER = "NUMBER",
                                     INTEGER = "INTEGER",
                                     FLOAT = "FLOAT",
                                     CARRENCY = "CARRENCY",
                                     ACCOUNTING = "ACCOUNTING",
                                     PERCENTAGE_NEGATIVE = "PERCENTAGE_NEGATIVE",
                                     PERCENTAGE = "PERCENTAGE";
        }

        // hidden report parameter's name which is used
        // to pass Control's attributes (name, label...ets)
        public static String CONTROL_INFO_PARAM =  "-CONTROL_INFO-";

    public static int DEFAULT_COLUMN_WIDTH = 100;
    public static int MAX_DOWLOAD_REPORT_ROWS = 65000;


    /** Creates a new instance of ReportingUtils */
    public ReportingUtils() {
    }

    /**
     *Trys to get the specified key, and the optional form of that key
     */
    public static Object getParam(Map paramMap, String key){
        Object value = paramMap.get(key);
        if(value == null){
            value = (String) paramMap.get(key + "_OPT");
        }
        return value;
    }

    /**
     *Trys to get the specified key, and the optional form of that key and parse it as a Date.
     *@return null if the parameter was not found or the parsed integer object
     *@throws a RemoteException if it could not parse the date with the appropriate UI tokenization included.
     */
    public static Date getParamAsDate(Map paramMap, String key)throws RemoteException{
    	String val = (String)getParam(paramMap, key);
    	if(!Utility.isSet(val)){
    		return null;
    	}
    	if(isValidDate(val)){
    		return parseDate(val);
    	}
    	throw new RemoteException("^clw^Value "+val+" is not valid for "+key+" must be a date value^clw^");
    }

    /**
     *Trys to get the specified key, and the optional form of that key and parse it as an Integer.
     *@return null if the parameter was not found or the parsed integer object
     *@throws a RemoteException if it could not parse the Integer with the appropriate UI tokenization included.
     */
    public static Integer getParamAsInteger(Map paramMap, String key)throws RemoteException{
    	String val = (String)getParam(paramMap, key);
    	if(!Utility.isSet(val)){
    		return null;
    	}
    	try{
    		Integer theInt = new Integer(val.trim());
    		return theInt;
    	}catch(NumberFormatException e){
    		throw new RemoteException("^clw^Value "+val+" is not valid for "+key+" must be an integer value^clw^");
    	}
    }

    static SimpleDateFormat reportingDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    /**
     *Parses out a date in the style that the reporting interface understands
     *@throws java.rmi.RemoteException if the date cannot be parsed
     */
    static Date parseDate(String pDateString) throws java.rmi.RemoteException{
        try{
            return reportingDateFormat.parse(pDateString);
        }catch(ParseException e){
            e.printStackTrace();
//            String msg = "ERROR: "+pDateString+" is not a valid date of the format 'mm/dd/yyyy'";
            String msg = Utility.createI18nErrorMess ("error.badDateFormat", new String[]{pDateString} ) ;
            throw new java.rmi.RemoteException(msg);
        }
    }




    /**
     *Converts a string date to a date in sql suitable for our reports:
     *'1/1/2002' would return TO_DATE('1/1/2002','mm/dd/yyyy')
     */
    static String toSQLDate(String pDate){
        return  "TO_DATE('"+pDate+"','mm/dd/yyyy')";
    }

    /**
     * Converts a date to a date in sql suitable for our reports:
     * '1/1/2002' would return TO_DATE('1/1/2002','mm/dd/yyyy')
     */
    static String toSQLDate(Date pDate) {
        return "TO_DATE('" + reportingDateFormat.format(pDate) + "','" + reportingDateFormat.toPattern() + "')";
    }

    static String format(Date pDate) {
        return reportingDateFormat.format(pDate);
    }

    static boolean isValidDate(String pString){
        try{
            StringTokenizer tok = new StringTokenizer(pString,"/");
            String day = tok.nextToken();
            String month = tok.nextToken();
            String year = tok.nextToken();
            int dayI = Integer.parseInt(day);
            int monthI = Integer.parseInt(month);
            int yearI = Integer.parseInt(year);
            Calendar cal = Calendar.getInstance();
            cal.set(yearI,monthI,dayI);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    static boolean isValidDate(String pDateStr, String pFormat){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(pFormat);
            Date date = sdf.parse(pDateStr);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            String yearS = String.valueOf(gc.get(GregorianCalendar.YEAR));
            if(pDateStr.indexOf(yearS)<0) return false;
            String dayS = String.valueOf(gc.get(GregorianCalendar.DAY_OF_MONTH));
            if(pDateStr.indexOf(dayS)<0) return false;
            String monthS = String.valueOf(gc.get(GregorianCalendar.MONTH)+1);
            if(pDateStr.indexOf(monthS)<0) return false;
            return true;
        }catch(Exception e){
            return false;
        }

    }

    private int mUserId = 0;
    private UserData mUserD = null;
    private void setupUserData(java.util.Map pParams, java.sql.Connection con)
    throws Exception{

        if ( mUserId != 0 && mUserD != null ) {
            return;
        }

        String userIdS = (String) pParams.get("CUSTOMER");
        if(userIdS==null || userIdS.trim().length()==0){
          String mess = "^clw^No user provided^clw^";
          throw new Exception(mess);
        }
        int userId = 0;
        try {
          userId = Integer.parseInt(userIdS);
        }
        catch (Exception exc1) {
          String mess = "^clw^Wrong user id format^clw^";
          throw new Exception(mess);
        }
        try {
          mUserD = UserDataAccess.select(con,userId);
        } catch(Exception exc) {
          String mess = "^clw^No user information found. User id: "+userIdS+"^clw^";
          throw new Exception(mess);
        }
        mUserId = userId;
    }

    UserSitesDesciption getUserSitesDesciption(java.util.Map pParams, java.sql.Connection con)
    throws Exception{
        setupUserData(pParams, con);

        if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals
           (mUserD.getUserTypeCd()) ||
           RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals
           (mUserD.getUserTypeCd())){
           return new UserSitesDesciption(null,true);
        }

        String sql = "select bus_entity_id from clw_user_assoc where user_id = "
            +mUserId+" and user_assoc_cd = 'SITE'";
        return new UserSitesDesciption(sql,false);

    }

    UserAccessDescription getUserAccessDescription
        (java.util.Map pParams, java.sql.Connection con)
    throws Exception{
        setupUserData(pParams, con);
        return new UserAccessDescription(mUserD);

    }

    class UserAccessDescription{

    	/**
    	 *Returns the raw user data object used for this userAccessDescription
    	 */
    	public UserData getUserData(){
    		return mUserD;
    	}
        String authorizedSql;
        public String getAuthorizedSql(){
            return authorizedSql;
        }
        public boolean hasAccountAccess(){
            return mAccountLevelAccess;
        }
        public boolean hasSiteAccess(){
            return mSiteLevelAccess;
        }
        public boolean hasStoreAccess(){
            return mStoreLevelAccess;
        }
        public boolean hasAccessToAll(){
            return mAllAccess;
        }
        
        private boolean mAccountLevelAccess = false,
            mSiteLevelAccess = false,
            mAllAccess = false,
            mStoreLevelAccess = false;
            ;
        public UserAccessDescription(UserData pUserData){

            if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals
                (mUserD.getUserTypeCd()) ||
            RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals
            (mUserD.getUserTypeCd()) ||
            RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals
            (mUserD.getUserTypeCd()) ||
               RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals
               (mUserD.getUserTypeCd())){
                authorizedSql = null;
                mAllAccess = true;
                return;
            }

            if(RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals
               (mUserD.getUserTypeCd())) {
                authorizedSql = "select bus_entity_id from clw_user_assoc "
                    + " where user_id = "
                    + mUserId+ " and user_assoc_cd = 'ACCOUNT'";
                mAccountLevelAccess = true;
                return;
            }
            
            if(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals
            (mUserD.getUserTypeCd())) {
              authorizedSql = "select bus_entity_id from clw_user_assoc "
                   + " where user_id = "
                   + mUserId+ " and user_assoc_cd = 'STORE'";
              mStoreLevelAccess = true;
              return;
            }

            mSiteLevelAccess = true;
            authorizedSql = "select bus_entity_id from clw_user_assoc "
                + " where user_id = "
                + mUserId+ " and user_assoc_cd = 'SITE'";

            return;
        }
    }

    class UserSitesDesciption{
        String authorizedSitesSql;
        boolean seesAllSites;
        public String getAuthorizedSitesSql(){
            return authorizedSitesSql;
        }
        public boolean isSeesAllSites(){
            return seesAllSites;
        }
        public UserSitesDesciption(String pAuthorizedSitesSql,boolean pSeesAllSites){
            authorizedSitesSql = pAuthorizedSitesSql;
            seesAllSites = pSeesAllSites;
        }
    }

    /**
     *Returns true if this user is authorized (in the loosest sense) to talk with this distributor.  The current implementation
     *really only checks that they are both part of the same store
     */
    public static boolean isUserAuthorizedForDistributor(java.sql.Connection pConn, int distId, int userId)
    throws SQLException{
        BusEntitySearchCriteria storeCrit = new BusEntitySearchCriteria();
        storeCrit.addUserId(userId);
        BusEntityDataVector stores = BusEntityDAO.getBusEntityByCriteria(pConn, storeCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        if(stores == null){
            return false;
        }
        //verifiy that this distributor is one of throws store distributors for this user
        int distStoreId = BusEntityDAO.getStoreForDistributor(pConn, distId);
        Iterator it = stores.iterator();
        while(it.hasNext()){
            BusEntityData bed = (BusEntityData) it.next();
            if(bed.getBusEntityId() == distStoreId){
                return true;
            }
        }
        return false;
    }

    /**
     *Returns true if this user is authorized for the specified site
     */
    public static boolean isUserAuthorizedForSite(java.sql.Connection pConn, int siteId, int userId)
    throws SQLException, DataNotFoundException{
    	UserData u = UserDataAccess.select(pConn,userId);
    	if(RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(u.getUserTypeCd())||
    			RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(u.getUserTypeCd())||
    			RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(u.getUserTypeCd())){
    		return true;
    	}
    	if(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(u.getUserTypeCd())){
    		int storeId = BusEntityDAO.getStoreForAccount(pConn,BusEntityDAO.getAccountForSite(pConn,siteId));
    		UserSearchCriteriaData uCrit = new UserSearchCriteriaData();
            uCrit.setUserId(Integer.toString(userId));
            uCrit.setStoreId(storeId);
            UserDataVector udv = UserDAO.getUsersCollectionByCriteria(pConn,uCrit);
            if(udv.isEmpty()){
            	return false;
            }
            return true;
    	}
        BusEntitySearchCriteria storeCrit = new BusEntitySearchCriteria();
        storeCrit.addUserId(userId);
        storeCrit.setSearchId(siteId);
        BusEntityDataVector bedv = BusEntityDAO.getBusEntityByCriteria(pConn, storeCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
        if(bedv == null){
            return false;
        }
        return true;
    }



    private static final ArrayList validOrderStatusCodes = new ArrayList();
    static{
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.WAITING_FOR_ACTION);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.READY_TO_SEND_TO_CUST_SYS);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP);
        validOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO);
    }

    private static final ArrayList approvedOrderStatusCodes = new ArrayList();
    static{
        approvedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        approvedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR);
        approvedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
        approvedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
        approvedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO);
        approvedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP);
    }

    public static final ArrayList commitedOrderStatusCodes = new ArrayList();
    static{
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED_PO_ERROR);
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO);
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP);
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.READY_TO_SEND_TO_CUST_SYS);
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM) ;
        commitedOrderStatusCodes.add(RefCodeNames.ORDER_STATUS_CD.WAITING_FOR_ACTION) ;
    }


    private static final ArrayList internalOrdersStatusList = new ArrayList();
    static{
        internalOrdersStatusList.add(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW);
        internalOrdersStatusList.add(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
        internalOrdersStatusList.add(RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED);
    }

    public static String getValidOrderSQL(){
        return "ORDER_STATUS_CD IN ("+Utility.toCommaSting(validOrderStatusCodes,'\'')+")";
    }

    public static List getValidOrderStatusCodes(){
        return validOrderStatusCodes;
    }

    public static final List getInternalOrdersStatusList(){
        return internalOrdersStatusList;
    }

    public static List getCommitedOrderStatusCodes(){
        return commitedOrderStatusCodes;
    }

    public static boolean isOrderCommitted(String pOrderStatusCd) {
	if ( pOrderStatusCd != null && pOrderStatusCd.length() > 0 ) {
	    return commitedOrderStatusCodes.contains(pOrderStatusCd);
	}
	return false;
    }

    public static boolean isOrderInAnApprovedStatus(String pOrderStatusCd) {
	if ( pOrderStatusCd != null && pOrderStatusCd.length() > 0 ) {
	    return approvedOrderStatusCodes.contains(pOrderStatusCd);
	}
	return false;
    }

    /**
     *Returns a SQL fragment suitable for valid orders
     */
    static String getValidOrdersSQL(String pOptionalOrderAlias){
        if(pOptionalOrderAlias != null){
            return pOptionalOrderAlias + "." + getValidOrderSQL();
        }
        return getValidOrderSQL();
    }

    public static String getAccessToSitesSQLSnippet(String schemaCode,String pToken){

    	if(schemaCode == null){
    		schemaCode = RefCodeNames.REPORT_SCHEMA_CD.MAIN;
    	}

    	String userAssoc;
    	String busEnt;
    	String user;
    	if(schemaCode.equals(RefCodeNames.REPORT_SCHEMA_CD.REPORT)){
    		userAssoc = "crs_user_assoc";
    		busEnt = "crs_bus_entity";
    		user = "crs_user";
    	}else{
    		userAssoc = UserAssocDataAccess.CLW_USER_ASSOC;
    		busEnt = BusEntityDataAccess.CLW_BUS_ENTITY;
    		user = UserDataAccess.CLW_USER;
    	}

    	String accountAccessSQL =
            " ( select bus_entity_id as account_id from "+userAssoc+" ua, "
            + " "+user+" u where u.user_id = ua.user_id "
            + " and  u.user_id = "+pToken+"CUSTOMER"+pToken+" "
            + " and user_assoc_cd = 'ACCOUNT' "
            + " union    select bus_entity_id from "+busEnt+"  where"
            + " bus_entity_type_cd = 'ACCOUNT'"
            + " and exists ( select u.user_id  from "+user+" u "
            + "  where  u.user_id = "+pToken+"CUSTOMER"+pToken+" and  "
            + "  user_type_cd in ('ADMINISTRATOR' ,   'SYSTEM_ADMINISTRATOR',"
            + "   'CRC_MANAGER', 'CUSTOMER SERVICE','DISTRIBUTOR') )  "
            + " )  ";

        return accountAccessSQL;
    }

    public static String getAccessToSitesSQLSnippet()
    {
    	return getAccessToSitesSQLSnippet(RefCodeNames.REPORT_SCHEMA_CD.MAIN,"#");
    }



    public static String replaceAccessToSitesSQL(String pSQL,String pToken, String pReportSchemaCode){
    	if ( null == pSQL || pSQL.length() <= 0 ) {
            return "";
        }
        String reptoken = "<cw>ACCOUNT_ACCESS</cw>",
            repSQL = getAccessToSitesSQLSnippet(pReportSchemaCode,pToken);

        if ( pSQL.indexOf(reptoken) >= 0 ) {
            String s = pSQL.replaceAll(reptoken, repSQL);
            return s;
        }

        return pSQL;
    }

    /**
     *Creates a GenericReportColumnView from the suplied input.  This is a convinience method
     *to create this object in one line as opposed to 5
     */
    public static GenericReportColumnView createGenericReportColumnView(
    String pColumnClass, String pColumnName, int pColumnPrecision, int pColumnScale,String pColumnType){
        return createGenericReportColumnView(pColumnClass, pColumnName,
					     pColumnPrecision, pColumnScale,pColumnType,"*", false, null);
    }
    public static GenericReportColumnView createGenericReportColumnView(
    String pColumnClass, String pColumnName, int pColumnPrecision, int pColumnScale,String pColumnType,
      String pColumnWidth, boolean pTotalRequestFlag){
        return createGenericReportColumnView(pColumnClass, pColumnName,
					pColumnPrecision, pColumnScale,pColumnType, pColumnWidth, pTotalRequestFlag, null);
    }
    public static GenericReportColumnView createGenericReportColumnView(
    String pColumnClass, String pColumnName, int pColumnPrecision, int pColumnScale,String pColumnType,
      String pColumnWidth, boolean pTotalRequestFlag, String pColumnFormat){
      return createGenericReportColumnView(pColumnClass, pColumnName,
                                      pColumnPrecision, pColumnScale,pColumnType, pColumnWidth, pTotalRequestFlag, pColumnFormat,null,null);

    }

    public static GenericReportColumnView createGenericReportColumnView(
    String pColumnName, String pColumnHeaderStyle, String pColumnDataStyle, String pColumnWidth ){
        return createGenericReportColumnView( null, pColumnName,
               0,0,null,pColumnWidth,false, null,  pColumnHeaderStyle, pColumnDataStyle );
    }

    /**
     *Creates a GenericReportColumnView from the suplied input.  This is a convinience method
     *to create this object in one line as opposed to 5
     */
    public static GenericReportColumnView createGenericReportColumnView(
    String pColumnClass, String pColumnName, int pColumnPrecision, int pColumnScale,String pColumnType,
      String pColumnWidth, boolean pTotalRequestFlag, String pColumnFormat, String pColumnHeaderStyle, String pColumnDataStyle){
        GenericReportColumnView vw = GenericReportColumnView.createValue();
        vw.setColumnClass(pColumnClass);
        vw.setColumnName(pColumnName);
        vw.setColumnPrecision(pColumnPrecision);
        vw.setColumnScale(pColumnScale);
        vw.setColumnType(pColumnType);
        vw.setColumnWidth(pColumnWidth);
        vw.setTotalRequestFlag(pTotalRequestFlag);
        vw.setColumnFormat(pColumnFormat);
        vw.setColumnHeaderStyleName(pColumnHeaderStyle);
        vw.setColumnDataStyleName(pColumnDataStyle);
        return vw;
    }

    /**
     *can create a simplistic report header without all the definition that goes into specifying them
     *manually.
     */
    public static GenericReportColumnViewVector createGenericReportColumnView(java.util.List def){
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        java.util.Iterator it = def.iterator();
        while(it.hasNext()){
            SimpleHeaderDef hd = (SimpleHeaderDef) it.next();
            String name = hd.getName();
            String type = hd.getType();
            if(type.endsWith("String")){
                header.add(createGenericReportColumnView("java.lang.String",name,0,255,"VARCHAR2"));
            }else if(type.endsWith("BigDecimal")){
                header.add(createGenericReportColumnView("java.math.BigDecimal",name,2,20,"NUMBER"));
            }else if(type.endsWith("Integer")){
                header.add(createGenericReportColumnView("java.lang.Integer",name,0,20,"NUMBER"));
            }else if(type.endsWith("Date")){
                header.add(createGenericReportColumnView("java.sql.Timestamp",name,0,0,"DATE"));
            }else{
                throw new RuntimeException("Unknown Java Type For Column Header: "+ type);
            }
        }
        return header;
    }

    /*
     *Creates the report.  Assumes the record list passed in has object implementing Record
     */
    static GenericReportResultView createReport(List recordList, GenericReportColumnViewVector header,String name,Comparator comp){
        if(comp != null){
            Collections.sort(recordList,comp);
        }
        GenericReportResultView results = GenericReportResultView.createValue();
        {
            Iterator it = recordList.iterator();
            results.setTable(new ArrayList());
            while(it.hasNext()) {
                Object obj = it.next();
                List l;
                if(obj instanceof Record){
                    Record r = (Record) obj;
                    l = r.toList();
                }else{
                    l = (List) obj;
                }
                results.getTable().add(l);
            }
            results.setColumnCount(header.size());
            results.setHeader(header);
            results.setName(name);
        }
        return results;
    }

    /*
     *Creates the report.  Assumes the record list passed in has object implementing Record
     */
    static GenericReportResultView createReport(List recordList, java.util.List headerDef,String name,Comparator comp)
    throws Exception{
        return createReport(recordList, createGenericReportColumnView(headerDef), name, comp);
    }

    //*****************************************************************************
    DistCustInvoiceViewVector invoicesMatch(
    DistCustInvoiceViewVector pCustInvoices,
    CustInvoiceItemViewVector pCustItems,
    DistCustInvoiceViewVector pVenInvoices,
    VenInvoiceItemViewVector pVenItems) {
        //Vendor invoice total match
        DistCustInvoiceViewVector matches = new DistCustInvoiceViewVector();
        for(int ii=0; ii<pCustInvoices.size(); ii++) {
            DistCustInvoiceView custDci = (DistCustInvoiceView) pCustInvoices.get(ii);
            int orderNum = custDci.getOrderNbr();
            for(int jj=0; jj<pVenInvoices.size(); jj++) {
                DistCustInvoiceView venDci = (DistCustInvoiceView) pVenInvoices.get(jj);
                boolean custDoneFl = true;
                DistCustInvoiceView dci = null;
                int totalVenQty = 0;
                for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                    VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                    totalVenQty += venCii.getQty();
                }
                mmm:
                    for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                        CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                        String custItem = custCii.getItem();
                        int custQty = custCii.getResidual();
                        if(custItem==null) custItem = "";
                        for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                            VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                            String venItem = venCii.getItem();
                            int venQty = venCii.getResidual();
                            if(custItem.equals(venItem)) {
                                if(custQty>0 && venQty==custQty) {
                                    venCii.setResidual(0);
                                    totalVenQty -= venQty;
                                    custCii.setResidual(-custQty);
                                    if(dci == null) {
                                        dci = custDci.copy();
                                        int custJoinCount = custDci.getCustJoinCount()+1;
                                        custDci.setCustJoinCount(custJoinCount);
                                        if(custJoinCount>1) {
                                            dci.setCustMisc(new BigDecimal(0));
                                            dci.setCustGoods(new BigDecimal(0));
                                            dci.setCustTax(new BigDecimal(0));
                                            dci.setCustTotalPrice(new BigDecimal(0));
                                        }
                                        dci.setVendor(venDci.getVendor());
                                        dci.setVenItems(venDci.getVenItems());
                                        dci.setVenInvoiceNum(venDci.getVenInvoiceNum());
                                        dci.setVenInvoiceDate(venDci.getVenInvoiceDate());
                                        dci.setPoNumber(venDci.getPoNumber());
                                        dci.setCustCommonPrice(new BigDecimal(0));
                                        dci.setVenCommonCost(new BigDecimal(0));
                                        int venJoinCount = venDci.getVenJoinCount()+1;
                                        venDci.setVenJoinCount(venJoinCount);
                                        if(venJoinCount==1) {
                                            dci.setVenTotalCost(venDci.getVenTotalCost());
                                            dci.setVenTax(venDci.getVenTax());
                                            dci.setVenAdditionalCharges(venDci.getVenAdditionalCharges());
                                            dci.setVenGoodsCost(venDci.getVenGoodsCost());
                                        } else {
                                            dci.setVenTotalCost(new BigDecimal(0));
                                            dci.setVenTax(new BigDecimal(0));
                                            dci.setVenAdditionalCharges(new BigDecimal(0));
                                            dci.setVenGoodsCost(new BigDecimal(0));
                                        }
                                    }
                                    BigDecimal commonQtyBD = new BigDecimal(custQty);
                                    dci.setCustCommonPrice(dci.getCustCommonPrice().add(custCii.getItemPrice().multiply(commonQtyBD)));
                                    dci.setVenCommonCost(dci.getVenCommonCost().add(venCii.getItemCost().multiply(commonQtyBD)));
                                }
                                else {
                                    custDoneFl = false;
                                    break mmm;
                                }
                            }
                        }
                        if(custQty>0) {
                            custDoneFl = false;
                        }
                    }
                    if(totalVenQty==0) {
                        for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                            CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                            if(custCii.getResidual()<0) custCii.setResidual(0);
                        }
                    } else {
                        if(dci!=null){
                            dci=null;
                            custDci.setCustJoinCount(custDci.getCustJoinCount()-1);
                            venDci.setVenJoinCount(venDci.getVenJoinCount()-1);
                            for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                                CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                                int residual = custCii.getResidual();
                                if(residual<0) custCii.setResidual(-residual);
                            }
                            for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                                VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                                venCii.setResidual(venCii.getQty());
                            }
                        }
                    }
                    if(dci!=null) {
                        matches.add(dci);
                    } else {
                    }
                    if(custDoneFl) {
                        break;
                    }
            }
        }
        //Match other invoices
        //DistCustInvoiceViewVector matches = new DistCustInvoiceViewVector();
        for(int ii=0; ii<pCustInvoices.size(); ii++) {
            boolean matchFl = false;
            DistCustInvoiceView custDci = (DistCustInvoiceView) pCustInvoices.get(ii);
            int orderNum = custDci.getOrderNbr();
            for(int jj=0; jj<pVenInvoices.size(); jj++) {
                DistCustInvoiceView venDci = (DistCustInvoiceView) pVenInvoices.get(jj);
                boolean custDoneFl = true;
                DistCustInvoiceView dci = null;
                for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                    CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                    String custItem = custCii.getItem();
                    int custQty = custCii.getResidual();
                    if(custItem==null) custItem = "";
                    for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                        VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                        String venItem = venCii.getItem();
                        int venQty = venCii.getResidual();
                        if(custItem.equals(venItem) && custQty>0 && venQty>0) {
                            int commonQty = Math.min(custQty,venQty);
                            venCii.setResidual(venQty-commonQty);
                            custQty = custQty-commonQty;
                            custCii.setResidual(custQty);
                            if(custCii.getResidual()>0) custDoneFl = false;
                            matchFl = true;
                            if(dci == null) {
                                dci = custDci.copy();
                                int custJoinCount = custDci.getCustJoinCount()+1;
                                custDci.setCustJoinCount(custJoinCount);
                                if(custJoinCount>1) {
                                    dci.setCustMisc(new BigDecimal(0));
                                    dci.setCustGoods(new BigDecimal(0));
                                    dci.setCustTax(new BigDecimal(0));
                                    dci.setCustTotalPrice(new BigDecimal(0));
                                }
                                dci.setVendor(venDci.getVendor());
                                dci.setVenItems(venDci.getVenItems());
                                dci.setVenInvoiceNum(venDci.getVenInvoiceNum());
                                dci.setVenInvoiceDate(venDci.getVenInvoiceDate());
                                dci.setPoNumber(venDci.getPoNumber());
                                dci.setCustCommonPrice(new BigDecimal(0));
                                dci.setVenCommonCost(new BigDecimal(0));
                                int venJoinCount = venDci.getVenJoinCount()+1;
                                venDci.setVenJoinCount(venJoinCount);
                                if(venJoinCount==1) {
                                    dci.setVenTotalCost(venDci.getVenTotalCost());
                                    dci.setVenTax(venDci.getVenTax());
                                    dci.setVenAdditionalCharges(venDci.getVenAdditionalCharges());
                                    dci.setVenGoodsCost(venDci.getVenGoodsCost());
                                } else {
                                    dci.setVenTotalCost(new BigDecimal(0));
                                    dci.setVenTax(new BigDecimal(0));
                                    dci.setVenAdditionalCharges(new BigDecimal(0));
                                    dci.setVenGoodsCost(new BigDecimal(0));
                                }
                            }
                            BigDecimal commonQtyBD = new BigDecimal(commonQty);
                            dci.setCustCommonPrice(dci.getCustCommonPrice().add(custCii.getItemPrice().multiply(commonQtyBD)));
                            dci.setVenCommonCost(dci.getVenCommonCost().add(venCii.getItemCost().multiply(commonQtyBD)));
                        }
                    }
                    if(custQty>0) {
                        custDoneFl = false;
                    }
                }
                if(dci!=null) {
                    matches.add(dci);
                }
                if(custDoneFl) {
                    break;
                }
            }
            if(custDci.getCustJoinCount()==0) {
                matches.add(custDci.copy());
            }
        }
        for(int ii=0; ii<pVenInvoices.size(); ii++) {
            DistCustInvoiceView venDci = (DistCustInvoiceView) pVenInvoices.get(ii);
            if(venDci.getVenJoinCount()==0) {
                matches.add(venDci.copy());
            }
        }
        return matches;
    }



    //*****************************************************************************
    DistCustInvoiceViewVector invoicesMatch(
    DistCustInvoiceViewVector pCustInvoices,
    CustInvoiceItemViewVector pCustItems,
    DistCustInvoiceViewVector pVenInvoices,
    VenInvoiceItemViewVector pVenItems,
    Date pStartDate,
    Date pEndDate) {
        //Order customer invoices by Date
        pCustInvoices.sort("CustInvoiceDate");

        //Order vendor invoices by Date
        pVenInvoices.sort("VenInvoiceDate");
        //Vendor invoice total match
        DistCustInvoiceViewVector matches = new DistCustInvoiceViewVector();
        for(int ii=0; ii<pCustInvoices.size(); ii++) {
            DistCustInvoiceView custDci = (DistCustInvoiceView) pCustInvoices.get(ii);
            int orderNum = custDci.getOrderNbr();
            Date custInvcD = custDci.getCustInvoiceDate();
            if(custInvcD.before(pStartDate) || custInvcD.after(pEndDate)) {
                custDci.setCustJoinCount(-1000);
            }
            for(int jj=0; jj<pVenInvoices.size(); jj++) {
                DistCustInvoiceView venDci = (DistCustInvoiceView) pVenInvoices.get(jj);
                Date venInvcD = venDci.getVenInvoiceDate();
                boolean custDoneFl = true;
                DistCustInvoiceView dci = null;
                int totalVenQty = 0;
                for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                    VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                    totalVenQty += venCii.getQty();
                }
                mmm:
                    for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                        CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                        String custItem = custCii.getItem();
                        int custQty = custCii.getResidual();
                        if(custItem==null) custItem = "";
                        for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                            VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                            String venItem = venCii.getItem();

                            int venQty = venCii.getResidual();
                            if(custItem.equals(venItem)) {
                                if(custQty>0 && venQty==custQty) {
                                    venCii.setResidual(0);
                                    totalVenQty -= venQty;
                                    custCii.setResidual(-custQty);
                                    if(dci == null) {
                                        dci = custDci.copy();
                                        int custJoinCount = custDci.getCustJoinCount()+1;
                                        custDci.setCustJoinCount(custJoinCount);
                                        dci.setCustJoinCount(custJoinCount);
                                        if(custJoinCount>1) {
                                            dci.setCustMisc(new BigDecimal(0));
                                            dci.setCustGoods(new BigDecimal(0));
                                            dci.setCustTax(new BigDecimal(0));
                                            dci.setCustTotalPrice(new BigDecimal(0));
                                        }
                                        dci.setVendor(venDci.getVendor());
                                        dci.setVenItems(venDci.getVenItems());
                                        dci.setVenInvoiceNum(venDci.getVenInvoiceNum());
                                        dci.setVenInvoiceDate(venDci.getVenInvoiceDate());
                                        dci.setPoNumber(venDci.getPoNumber());
                                        dci.setCustCommonPrice(new BigDecimal(0));
                                        dci.setVenCommonCost(new BigDecimal(0));
                                        int venJoinCount = venDci.getVenJoinCount();
                                        if(custJoinCount>=0) {
                                            venJoinCount++;
                                            venDci.setVenJoinCount(venJoinCount);
                                        }
                                        if(venJoinCount==1) {
                                            dci.setVenTotalCost(venDci.getVenTotalCost());
                                            dci.setVenTax(venDci.getVenTax());
                                            dci.setVenAdditionalCharges(venDci.getVenAdditionalCharges());
                                            dci.setVenGoodsCost(venDci.getVenGoodsCost());
                                        } else {
                                            dci.setVenTotalCost(new BigDecimal(0));
                                            dci.setVenTax(new BigDecimal(0));
                                            dci.setVenAdditionalCharges(new BigDecimal(0));
                                            dci.setVenGoodsCost(new BigDecimal(0));
                                        }
                                    }
                                    BigDecimal commonQtyBD = new BigDecimal(custQty);
                                    dci.setCustCommonPrice(dci.getCustCommonPrice().add(custCii.getItemPrice().multiply(commonQtyBD)));
                                    dci.setVenCommonCost(dci.getVenCommonCost().add(venCii.getItemCost().multiply(commonQtyBD)));
                                }
                                else {
                                    custDoneFl = false;
                                    break mmm;
                                }
                            }
                        }
                        if(custQty>0) {
                            custDoneFl = false;
                        }
                    }
                    if(totalVenQty==0) {
                        for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                            CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                            if(custCii.getResidual()<0) custCii.setResidual(0);
                        }
                    } else {
                        if(dci!=null){
                            dci=null;
                            custDci.setCustJoinCount(custDci.getCustJoinCount()-1);
                            venDci.setVenJoinCount(venDci.getVenJoinCount()-1);
                            for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                                CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                                int residual = custCii.getResidual();
                                if(residual<0) custCii.setResidual(-residual);
                            }
                            for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                                VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                                venCii.setResidual(venCii.getQty());
                            }
                        }
                    }
                    if(dci!=null) {
                        if(dci.getCustJoinCount()>=0) {
                            matches.add(dci);
                        }
                    }
                    if(custDoneFl) {
                        break;
                    }
            }
        }
        //Match other invoices
        //DistCustInvoiceViewVector matches = new DistCustInvoiceViewVector();
        for(int ii=0; ii<pCustInvoices.size(); ii++) {
            boolean matchFl = false;
            DistCustInvoiceView custDci = (DistCustInvoiceView) pCustInvoices.get(ii);
            int orderNum = custDci.getOrderNbr();
            for(int jj=0; jj<pVenInvoices.size(); jj++) {
                DistCustInvoiceView venDci = (DistCustInvoiceView) pVenInvoices.get(jj);
                boolean custDoneFl = true;
                DistCustInvoiceView dci = null;
                for(int iii = custDci.getCustItemInd1(); iii<=custDci.getCustItemInd2(); iii++) {
                    CustInvoiceItemView custCii = (CustInvoiceItemView) pCustItems.get(iii);
                    String custItem = custCii.getItem();
                    int custQty = custCii.getResidual();
                    if(custItem==null) custItem = "";
                    for(int jjj = venDci.getVenItemInd1(); jjj<=venDci.getVenItemInd2(); jjj++) {
                        VenInvoiceItemView venCii = (VenInvoiceItemView) pVenItems.get(jjj);
                        String venItem = venCii.getItem();
                        int venQty = venCii.getResidual();
                        if(custItem.equals(venItem) && custQty>0 && venQty>0) {
                            int commonQty = Math.min(custQty,venQty);
                            venCii.setResidual(venQty-commonQty);
                            custQty = custQty-commonQty;
                            custCii.setResidual(custQty);
                            if(custCii.getResidual()>0) custDoneFl = false;
                            matchFl = true;
                            if(dci == null) {
                                dci = custDci.copy();
                                int custJoinCount = custDci.getCustJoinCount()+1;
                                custDci.setCustJoinCount(custJoinCount);
                                if(custJoinCount>1) {
                                    dci.setCustMisc(new BigDecimal(0));
                                    dci.setCustGoods(new BigDecimal(0));
                                    dci.setCustTax(new BigDecimal(0));
                                    dci.setCustTotalPrice(new BigDecimal(0));
                                }
                                dci.setVendor(venDci.getVendor());
                                dci.setVenItems(venDci.getVenItems());
                                dci.setVenInvoiceNum(venDci.getVenInvoiceNum());
                                dci.setVenInvoiceDate(venDci.getVenInvoiceDate());
                                dci.setPoNumber(venDci.getPoNumber());
                                dci.setCustCommonPrice(new BigDecimal(0));
                                dci.setVenCommonCost(new BigDecimal(0));
                                int venJoinCount = venDci.getVenJoinCount();
                                if(custJoinCount>=0) {
                                    venJoinCount++;
                                    venDci.setVenJoinCount(venJoinCount);
                                }
                                if(venJoinCount==1) {
                                    dci.setVenTotalCost(venDci.getVenTotalCost());
                                    dci.setVenTax(venDci.getVenTax());
                                    dci.setVenAdditionalCharges(venDci.getVenAdditionalCharges());
                                    dci.setVenGoodsCost(venDci.getVenGoodsCost());
                                } else {
                                    dci.setVenTotalCost(new BigDecimal(0));
                                    dci.setVenTax(new BigDecimal(0));
                                    dci.setVenAdditionalCharges(new BigDecimal(0));
                                    dci.setVenGoodsCost(new BigDecimal(0));
                                }
                            }
                            BigDecimal commonQtyBD = new BigDecimal(commonQty);
                            dci.setCustCommonPrice(dci.getCustCommonPrice().add(custCii.getItemPrice().multiply(commonQtyBD)));
                            dci.setVenCommonCost(dci.getVenCommonCost().add(venCii.getItemCost().multiply(commonQtyBD)));
                        }
                    }
                    if(custQty>0) {
                        custDoneFl = false;
                    }
                }
                if(dci!=null) {
                    if(dci.getCustJoinCount()>=0) {
                        matches.add(dci);
                    }
                }
                if(custDoneFl) {
                    break;
                }
            }
            if(custDci.getCustJoinCount()==0) {
                matches.add(custDci.copy());
            }
        }
        return matches;
    }


    // Column name based methods.
    public static boolean isColumnForMoney(String pColName) {
	if ( pColName.toLowerCase().endsWith("_money") ||
	     pColName.endsWith("$") ) {
	    return true;
	}
	return false;
    }
    public static boolean isColumnForPercent(String pColName) {
	if ( pColName.toLowerCase().endsWith("_pct") ||
	     pColName.endsWith("%") ) {
	    return true;
	}
	return false;
    }
    public static boolean isColumnForTime(String pColName) {
	if ( pColName.toLowerCase().endsWith("time")) {
	    return true;
	}
	return false;
    }
    public static String extractColumnName(String pColNameIn) {
	if ( pColNameIn.toLowerCase().endsWith("_pct") ) {
	    pColNameIn = pColNameIn.substring(0, pColNameIn.length()-4);
	}
	if ( pColNameIn.toLowerCase().endsWith("_money") ) {
	    pColNameIn = pColNameIn.substring(0, pColNameIn.length()-6);
	}
	if ( pColNameIn.endsWith("$") || pColNameIn.endsWith("%") ) {
	    pColNameIn = pColNameIn.substring(0, pColNameIn.length()-1);
	}
	return pColNameIn;
    }

    public static String makeColumnKey(String pColName) {
	return "report.column." + pColName;
    }
    public static String makeTabKey(String pColName) {
	return "report.tab." + pColName;
    }
    public static String makeReportNameKey(String pName) {
	return "report.name." + pName;
    }
    public static String makeReportCategoryKey(String pV) {
	return "report.category." + pV;
    }
    public static String makeReportDescriptionKey(String pV) {
	return "report.description." + pV;
    }
    public static Object validPercent(Object val ){
      final BigDecimal EXTREM = new BigDecimal(999999999);
      return (val == null || (val != null && val.equals(EXTREM))) ? "" : val;
    }

    public static boolean isParamSet ( Object obj ){
      boolean ok = false;
      if (obj == null) {
        ok = false;
      }
      if (obj instanceof String) {
        ok = ( (String) obj).length() > 0;
      }
      if (obj instanceof IdVector) {
        ok = ( (IdVector) obj).size() > 0;
      }
      return ok;
    }

   public static String convertParamToString ( Object obj ){
     String str = "%";
     if (obj != null && obj instanceof String) {
       str = (String) obj;
     }
     if (obj != null && obj instanceof IdVector) {
       str = IdVector.toCommaString( (IdVector) obj);
     }
     return str;
   }

   /**
  *Creates a GenericReportStyleView from the suplied input.  This is a convinience method
  *to create this object in one line
  */
 public static GenericReportStyleView createGenericReportStyleView(
   String pStyleName, String pDataCategory, String pDataFormat,
   String pAlignment, String pFillColor, String pFontName, String pFontColor,
   short pFontSize, String pFontType, int[] pMergeRegion ){
     GenericReportStyleView vw = GenericReportStyleView.createValue();
     vw.setStyleName(pStyleName);
     vw.setDataCategory(pDataCategory);
     vw.setDataFormat(pDataFormat);
     vw.setAlignment(pAlignment);
     vw.setFillColor(pFillColor);
     vw.setFontName(pFontName);
     vw.setFontColor(pFontColor);
     vw.setFontSize(pFontSize);
     vw.setFontType(pFontType);
     vw.setMergeRegion(pMergeRegion);

     return vw;
 }

  public static Map createStyles( HashMap userStyleDesc){
   Map newStyles = new HashMap();
//   GenericReportStyleView style = null;
   if (userStyleDesc != null) {
     Map.Entry entry = null;
     Iterator it = userStyleDesc.entrySet().iterator();
     while (it.hasNext()) {
       entry = (Map.Entry) it.next();
       GenericReportStyleView styleView = (GenericReportStyleView)entry.getValue();
       if (styleView != null){
         String dataCategory = styleView.getDataCategory();
         String dataFormat = styleView.getDataFormat();

 //        style = styleView;//GenericReportStyleView.createValue();
         String fmt = null;
         if (!Utility.isSet(styleView.getDataClass())) {
           if (Utility.isSet(dataCategory)) {
             if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.DATE) ||
                 dataCategory.equals(ReportingUtils.DATA_CATEGORY.TIME)) {
               styleView.setDataClass("java.sql.Timestamp");
             }
             else if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.INTEGER)) {
               styleView.setDataClass("java.lang.Integer");
             }
             else if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.FLOAT)) {
               styleView.setDataClass("java.math.BigDecimal");
             }
             else if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.PERCENTAGE)) {
               styleView.setDataClass("java.math.BigDecimal");
               fmt = "0.00%";
             }
             else if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.ACCOUNTING)) {
               styleView.setDataClass("java.math.BigDecimal");
               fmt = "$##,##0.00";
             }
             else {
               styleView.setDataClass("java.lang.String");
             }
           }
           else {
             styleView.setDataClass("java.lang.String");
           }
           if (fmt != null){
             styleView.setDataFormat((Utility.isSet(dataFormat) ? dataFormat : fmt));
           }
         }
         newStyles.put(entry.getKey(), styleView);
       }
     }
   }
   return addDefaultStyles( newStyles );
 }

 private static Map addDefaultStyles(Map pStyles){
   GenericReportStyleView style = null;
   if (pStyles == null) {
     pStyles = new HashMap();
   }
   if (!pStyles.containsKey(ReportingUtils.PAGE_TITLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.lang.String");
     pStyles.put(ReportingUtils.PAGE_TITLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.TABLE_HEADER)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.lang.String");
     pStyles.put(ReportingUtils.TABLE_HEADER, style);
   }

   if (!pStyles.containsKey(ReportingUtils.DATE_STYLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.sql.Timestamp");
     style.setDataFormat( "MM/dd/yyyy");
     pStyles.put(ReportingUtils.DATE_STYLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.TIME_STYLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.sql.Timestamp");
     style.setDataFormat("H:mm");
     pStyles.put(ReportingUtils.TIME_STYLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.INTEGER_STYLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.lang.Integer");
     style.setScale(0);
     pStyles.put(ReportingUtils.INTEGER_STYLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.INTEGER_SEPARATOR_STYLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.lang.Integer.Separator");
     style.setScale(0);
     style.setDataFormat("#,##0");
     pStyles.put(ReportingUtils.INTEGER_SEPARATOR_STYLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.PERCENT_STYLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.math.BigDecimal");
     style.setDataFormat("0.00%");
     pStyles.put(ReportingUtils.PERCENT_STYLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.FLOAT_STYLE)) {
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.math.BigDecimal");
     style.setScale(2);
     pStyles.put(ReportingUtils.FLOAT_STYLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.FLOAT_SEPARATOR_STYLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.math.BigDecimal.Separator");
     style.setDataFormat("#,##0.00");
     pStyles.put(ReportingUtils.FLOAT_SEPARATOR_STYLE, style);
   }

   if (!pStyles.containsKey(ReportingUtils.ACCOUNTING_STYLE)){
     style= GenericReportStyleView.createValue();
     style.setDataClass("java.math.BigDecimal");
     style.setDataFormat("$#,##0.00");
     pStyles.put(ReportingUtils.ACCOUNTING_STYLE, style);
   }

   return pStyles;
 }

    public static void verifyDates(Map pParams) throws RemoteException {
        String begDateS = (String)ReportingUtils.getParam(pParams,"BEG_DATE");
        String endDateS = (String)ReportingUtils.getParam(pParams,"END_DATE");

        if (endDateS == null) {
            endDateS = "";
        }
        if (begDateS == null) {
            begDateS = "";
        }
        if ((endDateS.length() + begDateS.length()) == 0) {
            return;
        }

        if(!ReportingUtils.isValidDate(begDateS) && begDateS.length() > 0){
            throw new RemoteException("^clwKey^report.text.beginDateInvalid^clwKey^");
        }
        if(!ReportingUtils.isValidDate(endDateS) && endDateS.length() > 0){
            throw new RemoteException("^clwKey^report.text.endDateInvalid^clwKey^");
        }

        if ( endDateS.length() > 0  && begDateS.length() == 0) {
            throw new RemoteException("^clwKey^report.text.enterBeginDate^clwKey^");
        }

        if ( begDateS.length() > 0  && endDateS.length() == 0) {
            throw new RemoteException("^clwKey^report.text.enterEndDate^clwKey^");
        }


        Date begDate = ReportingUtils.parseDate(begDateS);
        Date endDate = ReportingUtils.parseDate(endDateS);
        Date currDate = new Date();

        if (begDate.after(currDate)) {
            throw new RemoteException("^clwKey^report.text.beginDateGreaterCurrent^clwKey^");
        }
        if (begDate.after(endDate)) {
            throw new RemoteException("^clwKey^report.text.beginDateGreaterEndDate^clwKey^");
        }


    }

    public static String getControlLabel(String pControlName, Map pParams, String pDefaultLabel ){
      HashMap controlInfo = (HashMap)pParams.get(CONTROL_INFO_PARAM);
      String label  = pDefaultLabel;
      if (controlInfo != null) {
        GenericReportControlView grc = (GenericReportControlView) controlInfo.get(pControlName) ;
        if (grc != null) {
          label = (Utility.isSet(grc.getLabel())) ? grc.getLabel().replace(":", "" ) : pDefaultLabel;
        }
      }
      return label + " : ";
    }


    public static String convertPOIColor(String poiColorCode) {
        String color = "GREY_25_PERCENT";
        if (color.equals(poiColorCode)) {
            return Constants.HTML_COLORS.GRAY_25_PERCENT;
        } else {
            return "";
        }
    }
    /* Stop the report processing if row count greater then 65000 */
    /* param reportResult - report result data                    */
    public static boolean verifyReportPageSize(GenericReportResultViewVector reportResults){
      boolean error = false;
      final int MAX_REPORT_PAGE_SIZE = 65000;
      if (reportResults !=null && !reportResults.isEmpty()) {
        Iterator it = reportResults.iterator();
        while (it.hasNext()) {
         GenericReportResultView repRes = (GenericReportResultView)it.next();
         if ( repRes != null && repRes.getTable() != null &&
              repRes.getTable().size() > MAX_REPORT_PAGE_SIZE) {
           error = true;
         }
       }
     }
     return error;
   }
    
    public static Object getInstanceReport(String pClassName) throws Exception {

        if (!Utility.isSet(pClassName)) {
            return null;
        }

        ReportsClassLoader loader = ReportsClassLoader.getInstance();

        Class clazz = loader.loadClass(pClassName);
        Object ret = clazz.newInstance();

        return ret;

    }

    public static void restrictNotSupportedReports(List pReports) {
        if (pReports != null) {
            Iterator it = pReports.iterator();
            while (it.hasNext()) {
                try {
                    getInstanceReport(((GenericReportView)it.next()).getReportClass());
                } catch (Exception e) {
                    it.remove();
                }
            }
        }
    }

}
