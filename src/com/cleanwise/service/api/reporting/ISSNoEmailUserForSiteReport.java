/*
 * ISSNoEmailUserForSiteReport.java
 *
 * Created on September 12, 2008, 4:43 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.reporting.ISSNoEmailUserForSiteReport.ReportView;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import com.cleanwise.service.api.framework.ValueObject;
import java.util.*;
import com.cleanwise.service.api.util.Utility;
import java.math.MathContext;
import org.apache.log4j.Logger;


/**
 * Picks up distributor invoices and agreates it on Account
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 *
 */
public class ISSNoEmailUserForSiteReport  implements GenericReportMulti {

	private static final Logger log = Logger.getLogger(ForecastOrderByItemReport.class); // new statement
   /** Creates a new instance of ISSNoEmailUserForSiteReport */
    public ISSNoEmailUserForSiteReport() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    // define names of the Analitic Report Controls
     protected static final String REPORT_NAME    = "REPORT_NAME";
     protected static final String STORE_S  = "STORE";
     protected static final String LOCATE_ACCOUNT_MULTI_S  = "LOCATE_ACCOUNT_MULTI";
     protected static final String LOCATE_ACCOUNT_MULTI_OPT_S  = "LOCATE_ACCOUNT_MULTI_OPT";
     protected static final String CUSTOMER_S     = "CUSTOMER";

     protected static final String BOLD_STYLE = "BOLD";

     // Column names : Account id, Account Name, Site Id, Site Name,
     //Workflow Id, Workflow Name, Rule Expr, Emeil Group Id,
     //Emeil Group Name
     protected static final String ACCOUNT_ID = "Account Id";
     protected static final String ACCOUNT_NAME = "Account Name";
     protected static final String SITE_ID = "Site Id";
     protected static final String SITE_NAME = "Site Name";
     protected static final String WORKFLOW_ID = "Workflow Id";
     protected static final String WORKFLOW_NAME = "Workflow Name";
     protected static final String RULE_SEQ = "Rule #";
     protected static final String RULE_EXPR = "Rule Type";
     protected static final String EMAIL_GROUP_ID = "Email Group Id";
     protected static final String EMAIL_GROUP_NAME = "Email Group Name";

    protected static final String FONT_NAME = "Arial";
    protected static final int FONT_SIZE = 10;

    protected static final String[] COL_WIDTH = new String[]{"12","25","12","25","12","25","25","12","25"};


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getMainConnection();
        String errorMess = "No error";
        //=========== getting parameters =======================================
        String reportName = (String) pParams.get(REPORT_NAME);
        GenericReportResultView result = GenericReportResultView.createValue();
        result.setTitle( getReportTitle(con, reportName, pParams));
        result.setHeader(getReportHeader());
        result.setColumnCount(getReportHeader().size());
        result.setTable(new ArrayList());

        //====  Request for values ==== //
        try {
 
          GenericReportResultViewVector resultV = getReportData (result, getResultOfQuery(con, pParams));
          
          result.setFreezePositionColumn(getFreezeColumns());
          //result.setFreezePositionRow(result.getTitle().size()+2);
          return resultV;
        }
        catch (SQLException exc) {
          errorMess = " SQL Exception happened. " +  exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
          errorMess = " Exception happened. " +  exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }

    }

    protected int getFreezeColumns() {
      return 1;
    }
    protected int getFreezeRows() {
        return 3;
      }

    private String getParamValue (Map pParams, String pControlName){
      String value = null;
      if (pParams.containsKey(pControlName) &&
          Utility.isSet( (String) pParams.get(pControlName))) {
        value = (String) pParams.get(pControlName);
      }
      return value;
    }

    private GenericReportResultViewVector getReportData(GenericReportResultView result , List repList ){
        ArrayList table = new ArrayList();
        result.setTable(table);
        result.setFreezePositionColumn(0);
        result.setFreezePositionRow(3);

        for(Iterator iter = repList.iterator(); iter.hasNext();) {
            ReportView rv = (ReportView) iter.next();
            ArrayList row = new ArrayList();
            table.add(row);
            
            row.add(rv.mAccountId);
            row.add(rv.mAccountName);
            row.add(rv.mSiteId);
            row.add(rv.mSiteName);
            row.add(rv.mWorkflowId);
            row.add(rv.mWorkflowName);
            row.add(rv.mRuleSeq);
            row.add(rv.mRuleExpr);
            row.add(rv.mEmailGroupId);
            row.add(rv.mEmailGroupName);

        }
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        resultV.add(result);
        return resultV;
    }
    
 
      protected GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.ACCOUNT_ID, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.ACCOUNT_NAME, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.SITE_ID, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.SITE_NAME, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.WORKFLOW_ID, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.WORKFLOW_NAME, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.RULE_SEQ, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.RULE_EXPR, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.EMAIL_GROUP_ID, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.EMAIL_GROUP_NAME, 0, 255, "VARCHAR2"));

        return header;
    }


    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();

      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",pTitle,0,255,"VARCHAR2"));

      String controlLabel = ReportingUtils.getControlLabel(STORE_S, pParams, "Store");
      //title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", controlLabel +  getListOfNames(con, STORE_S , pParams),0,255,"VARCHAR2"));

      controlLabel = ReportingUtils.getControlLabel(CUSTOMER_S, pParams, "Customer");
      //title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", controlLabel +  getListOfNames(con, CUSTOMER_S , pParams),0,255,"VARCHAR2"));

      String controlName = LOCATE_ACCOUNT_MULTI_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Accounts");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        //title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }
      return title;
    }
    protected String  getListOfNames (Connection con, String controlName, Map pParams){
        String sql = "";
    	Object listOfIds = pParams.get(controlName);
        IdVector ids = new IdVector();
        String idsS = null;
        if (listOfIds instanceof List) {
          ids = (IdVector) ( (ArrayList) listOfIds).clone();
          idsS = IdVector.toCommaString(ids);
        }
        if (listOfIds instanceof String) {
          idsS = (String) listOfIds;
        }
        StringBuffer nms = new StringBuffer();
        String typeDim = "";
        String tableName = "";
        if (controlName.equals(STORE_S)){
          sql="select SHORT_DESC from clw_bus_entity where bus_entity_id in (" + idsS + ")";
        } else if (controlName.equals(this.LOCATE_ACCOUNT_MULTI_S)|| controlName.equals(this.LOCATE_ACCOUNT_MULTI_OPT_S)) {
            sql="select SHORT_DESC from clw_bus_entity where bus_entity_id in (" + idsS + ")";
        } else if (controlName.equals(this.CUSTOMER_S)) {
            sql="select USER_NAME from clw_user where user_id in (" + idsS + ")";
        }

        if (idsS != null && idsS.length() != 0) {
          try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
              String be = new String(rs.getString(1));
              if (nms.length() == 0) {
                nms.append(be);
              }
              else {
                nms.append(", " + be);
              }
            }
            stmt.close();
          }
          catch (SQLException exc) {
            exc.printStackTrace();
          }
        }
        return nms.toString();
      }
 
    protected List getResultOfQuery(Connection conn, Map pParams) throws Exception{
      String selectSql = getQuerySql(pParams);
log.info("SQL:" + selectSql);
	  List ll = new LinkedList();
	  if (conn != null) {
	      PreparedStatement pstmt = conn.prepareStatement(selectSql);
	
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()){
	        ReportView el = new ReportView ();
	        el.mAccountId = rs.getInt("ACCOUNT_ID");
	        el.mAccountName = (rs.getString("ACCOUNT_NAME") != null) ? rs.getString("ACCOUNT_NAME") : "";
	        el.mSiteId = rs.getInt("SITE_ID");
	        el.mSiteName = (rs.getString("SITE_NAME") != null) ? rs.getString("SITE_NAME") : "";
	        el.mWorkflowId = rs.getInt("WORKFLOW_ID");
	        el.mWorkflowName = (rs.getString("WORKFLOW_NAME") != null) ? rs.getString("WORKFLOW_NAME") : "";
	        el.mRuleSeq = rs.getInt("RULE_SEQ");
	        el.mRuleExpr = (rs.getString("RULE_EXPR") != null) ? rs.getString("RULE_EXPR") : "";
	        el.mEmailGroupId = rs.getInt("EMAIL_GROUP_ID");
	        el.mEmailGroupName = (rs.getString("EMAIL_GROUP_NAME") != null) ? rs.getString("EMAIL_GROUP_NAME") : "";
	       ll.add(el);
	      }
	      pstmt.close();
	      rs.close();
	  }
      return ll;

    }

 
    protected String getQuerySql(Map pParams) throws Exception {

 //     String filterCond = createFilterCondition(pParams);
 //     String accountStr = (String) pParams.get(LOCATE_ACCOUNT_MULTI_S);
 //     String customerStr = (String) pParams.get(CUSTOMER_S);


      String sql=
      "select  distinct  bea.bus_entity_id ACCOUNT_ID, \n" +
      "  bea.short_desc ACCOUNT_NAME, \n" +
      "  bes.bus_entity_id SITE_ID,  bes.short_desc SITE_NAME, \n" +
      "  CW.WORKFLOW_ID,  CW.SHORT_DESC WORKFLOW_NAME, \n" +
      "  CWR.RULE_SEQ, CWR.RULE_TYPE_CD RULE_EXPR, \n" +
      "  CWR.EMAIL_GROUP_ID, \n" +
      "  CG.SHORT_DESC EMAIL_GROUP_NAME \n" +
      "from clw_bus_entity beA, \n" +
      "  clw_bus_entity beS, \n" +
      "  clw_bus_entity_assoc account, \n" +
      "  clw_bus_entity_assoc  site, \n" +
      "  CLW_SITE_WORKFLOW csw, \n" +
      "  CLW_WORKFLOW cw, \n" +
      "  CLW_WORKFLOW_RULE cwr, \n" +
      "  CLW_GROUP cg \n" +
      "where \n" +
	    " account.BUS_ENTITY1_ID = SITE.BUS_ENTITY2_ID \n" +
	    " and BES.BUS_ENTITY_ID = SITE.BUS_ENTITY1_ID  \n" +   
	    " and BEA.BUS_ENTITY_ID = SITE.BUS_ENTITY2_ID  \n" +   
	    " and SITE.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n" +
	    " and BES.BUS_ENTITY_TYPE_CD = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.SITE+"' \n" +
	    " and BES.BUS_ENTITY_STATUS_CD = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' \n" +
	    " and BEA.BUS_ENTITY_TYPE_CD = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"' \n" +
	    " and BEA.BUS_ENTITY_STATUS_CD = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' \n" +
	    
	    " and CW.BUS_ENTITY_ID=  BEA.BUS_ENTITY_ID \n" +
	    " and CSW.SITE_ID= BES.BUS_ENTITY_ID \n" +
	    
	    " and cw.WORKFLOW_ID = csw.WORKFLOW_ID \n" +
	    " and cwr.WORKFLOW_ID = cw.WORKFLOW_ID \n" +
	    
	   " and cwr.EMAIL_GROUP_ID = CG.GROUP_ID \n" +

       " and cw.WORKFLOW_STATUS_CD = '" + RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE +"' \n" +
	   " and  cwr.EMAIL_GROUP_ID not in \n" +
	   " (select group_id \n" +
	   "   from clw_group_assoc ga, \n" +
	   "        CLW_USER_ASSOC ua,   \n" +
	   "        CLW_USER u \n" +
	   "   where \n" +
	   "        GA.USER_ID = UA.USER_ID \n" +
	   "       and UA.BUS_ENTITY_ID = BES.BUS_ENTITY_ID \n" +
	   "       and U.USER_ID = GA.USER_ID \n" +
	   "       and GA.GROUP_ASSOC_CD = '" + RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP +"' \n" +
	   "       and UA.USER_ASSOC_CD = '" +RefCodeNames.USER_ASSOC_CD.SITE+"' \n" +
	   "       and U.USER_STATUS_CD = '" + RefCodeNames.USER_STATUS_CD.ACTIVE +"' \n" +
	   "    group by group_id ) \n" +
	   
	   createFilterCondition(pParams) +
	   " order by bes.bus_entity_id, CWR.RULE_SEQ";
   
    	  
        return sql;
    }
//------------------
    protected String getTitle() {
       String title = "Email approval groups that do not have a user configured to the site" ;
       return title;
    }

  protected String createFilterCondition(Map pParams) throws Exception {
	  
    String storeFilter = getParamValue(pParams, STORE_S);
    String accountFilter = getParamValue(pParams, LOCATE_ACCOUNT_MULTI_S);
    if (!Utility.isSet(accountFilter)) {
    	accountFilter = getParamValue(pParams, LOCATE_ACCOUNT_MULTI_OPT_S);
    }
    
    String customerFilter = getParamValue(pParams, CUSTOMER_S);

    boolean accountFl = Utility.isSet(accountFilter);
    boolean storeFl = Utility.isSet(storeFilter);
    boolean customerFl = Utility.isSet(customerFilter);

    String filterCond = getAccountsOfStoreCondition (storeFilter, customerFilter);
    filterCond += (accountFl?" and  ACCOUNT.BUS_ENTITY1_ID  in ( " + accountFilter + ") \n": "" );
 
    return filterCond;
  }
  
  private String getAccountsOfStoreCondition(String store, String customer) throws Exception{
	  StringBuffer filter = new StringBuffer();
      int storeId = (store != null && store instanceof String ? Integer.parseInt((String)store) : 0);
      int userId =0;
      try {
          userId = Integer.parseInt((String) customer);
      } catch (Exception e) {
           userId = 0;
      }
      if (userId == 0) {
          //throw new Exception("Asset Detail Report: Error no user defined!");
           throw new RemoteException("^clwKey^report.text.userIdParameterInvalid^clwKey^");
      }
      UserData userD = APIAccess.getAPIAccess().getUserAPI().getUser(userId);
      String userTypeStr = userD.getUserTypeCd();
      
      if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeStr) || 
    	  RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeStr)	  ) {
    	  filter.append(" and account.BUS_ENTITY2_ID = " + store) ; 
    	  filter.append(" and account.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+ "'") ;
      } else {
    	  //TODO throw exception 
    	  filter.append(" and 1=2 "); //
      }
	  return filter.toString();
  }

  protected class ReportView extends ValueObject {
    private int mAccountId;
    private String mAccountName;
    private int mSiteId;
    private String mSiteName;
    private int mWorkflowId;
    private String mWorkflowName;
    private int mRuleSeq;
    private String mRuleExpr;
    private int mEmailGroupId;
    private String mEmailGroupName;


    /**
     * Constructor.
     */
    public ReportView ()
    {
        mAccountId = 0;
        mAccountName="";
        mSiteId=0;
        mSiteName="";
        mWorkflowId = 0;
        mWorkflowName = "";
        mRuleSeq =0;
        mRuleExpr ="";
        mEmailGroupId=0;
        mEmailGroupName="";
    }

 
  }

}
