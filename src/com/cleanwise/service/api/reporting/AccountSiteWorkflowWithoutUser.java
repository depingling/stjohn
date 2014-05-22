package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.WorkOrderDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.GenericReportControlFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.rmi.RemoteException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 */
public class AccountSiteWorkflowWithoutUser implements GenericReportMulti {
	
	public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
		
        APIAccess factory = new APIAccess();

        Asset assetEjb       = factory.getAssetAPI();
        User  userEjb        = factory.getUserAPI();
        WorkOrder woEjb      = factory.getWorkOrderAPI();
        Warranty warrantyEjb = factory.getWarrantyAPI();
        PropertyService propertyServiceEjb = factory.getPropertyServiceAPI();

        Connection conn = pCons.getMainConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        Object account     = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.ACCOUNT);
        Object userIdStr     = getParamValue(pParams, GenericReportControlFactory.GENERIC_REPORT_CONTROL_CD.CUSTOMER);
        Object userIdString  = getParamValue(pParams, "USER_ID");      
        Object userType      = getParamValue(pParams, "USER_TYPE");
        Object userWOadmin   = getParamValue(pParams, "USER_WO_ADMIN");        

        int userId = 0;
        if (Utility.isSet((String)userIdString)) {
            userId = Integer.parseInt((String)userIdString);
        } else if (Utility.isSet((String)userIdStr)) {
            userId = Integer.parseInt((String) userIdStr);
        }

        if (userId == 0) {
            throw new Exception("Asset Detail Report: Error no user defined!");
        }
        UserData userD = userEjb.getUser(userId);

        String userTypeStr = "";
        if (Utility.isSet((String)userType)) {
            userTypeStr = (String)userType;
        } else {
            userTypeStr = userD.getUserTypeCd();
        }

        RefCdDataVector refDV = userEjb.getAuthorizedFunctions(userId, userTypeStr);
        ArrayList userFunctions = new ArrayList();
        Iterator it = refDV.iterator();
        RefCdData refD;
        while (it.hasNext()) {
            refD = (RefCdData)it.next();
            userFunctions.add(refD.getValue());
        }

        boolean isAuthorizedAdmin = false;
        if (Utility.isSet((String)userWOadmin)) {
            isAuthorizedAdmin = Boolean.parseBoolean((String)userWOadmin);
        } else if (hasReportingAssignAllAcctsProperty(userD)) {
            isAuthorizedAdmin =true;
        } else {
            isAuthorizedAdmin = userFunctions.contains(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_WO_VIEW_ALL_FOR_STORE);
        }	
        
        String Request =
        				" select distinct CBE.BUS_ENTITY_ID, CBE.SHORT_DESC, CBE.BUS_ENTITY_TYPE_CD "+
        				" from CLW_BUS_ENTITY cbe "+
        				" order by CBE.SHORT_DESC";		                        
        
       // if (userId > 0) {
       //     if (isAuthorizedAdmin) {
            	/*String Request = 
            		   " select distinct CBE.BUS_ENTITY_ID, CBE.SHORT_DESC, CBE.BUS_ENTITY_TYPE_CD "+
            		   " from  CLW_BUS_ENTITY cbe, CLW_SITE_WORKFLOW csw, "+
            		   " CLW_WORKFLOW cw,  CLW_WORKFLOW_RULE cwr, CLW_GROUP cg, "+ 
            		   " CLW_GROUP_ASSOC cga "+
            		   " ,(select CBEA.BUS_ENTITY1_ID "+ 
            		   " from CLW_BUS_ENTITY_ASSOC cbea "+
            		   (Utility.isSet((String) assetName)?"where CBEA.BUS_ENTITY2_ID = "+((String)assetName).trim().toLowerCase()+") bea")+
            		   " where CBEA.BUS_ENTITY2_ID = 202875) bea "+
            		   " where CW.WORKFLOW_ID = CSW.WORKFLOW_ID "+
            		   " and CBE.BUS_ENTITY_ID in bea.BUS_ENTITY1_ID "+
            		   " and CSW.SITE_ID = CBE.BUS_ENTITY_ID "+
            		   " and CWR.WORKFLOW_ID = CW.WORKFLOW_ID "+
            		   " and CBE.BUS_ENTITY_TYPE_CD = 'SITE' "+
            		   " and ((CWR.APPROVER_GROUP_ID = cg.GROUP_ID )or(CWR.EMAIL_GROUP_ID = cg.GROUP_ID)) "+
            		   " and CG.GROUP_ID = CGA.GROUP_ID "+
            		   " and CGA.USER_ID is null "+
            		   " order by CBE.SHORT_DESC ";*/
            	
                PreparedStatement pstmt = conn.prepareStatement(Request);

                ResultSet rs = pstmt.executeQuery();
                
                LinkedList ll = new LinkedList();
                while (rs.next()){
                	SiteList ad = new SiteList();
                    ll.add(ad);
                    ad.busEntityId = rs.getInt("BUS_ENTITY_ID");
                    ad.shortDesc = rs.getString("SHORT_DESC");
                    ad.busEntityTypeCD = rs.getString("BUS_ENTITY_TYPE_CD");
                }
                rs.close();
                pstmt.close();           
                
                GenericReportResultView result = GenericReportResultView.createValue();
                GenericReportColumnViewVector header = getReportHeader();
                result.setHeader(header);
                result.setColumnCount(header.size());
                result.setName("Site without workflow e_mail group user");
                ArrayList table = new ArrayList();
                result.setTable(table);
                result.setFreezePositionColumn(1);
                result.setFreezePositionRow(1);

                for(Iterator iter = ll.iterator(); iter.hasNext();) {
                	SiteList ad = (SiteList) iter.next();
                    ArrayList line = new ArrayList();
                    table.add(line);
                    line.add(ad.busEntityId);
                    line.add(ad.shortDesc);
                    line.add(ad.busEntityTypeCD);
                }
                resultV.add(result);


                return resultV;                
                
          //  }
        //}                		
		
	}
	
    private GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "BUS_ENTITY_ID", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SHORT_DESC", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "BUS_ENTITY_TYPE_CD", 0, 255, "VARCHAR2"));

        return header;
    }	
	
    private Object getParamValue(Map pParams, String name) {
        if (pParams.containsKey(name)) {
            return pParams.get(name);
        } else if (pParams.containsKey(name + "_OPT")) {
            return pParams.get(name + "_OPT");
        } else {
            return null;
        }
    }	

    private boolean hasReportingAssignAllAcctsProperty(UserData userD){
        String userRoleCd = userD.getUserRoleCd();
        boolean hasRole = Utility.isSet(userRoleCd) && userRoleCd.contains("RepOA^");
        return hasRole;
      }	
	
    private class SiteList {
    	int busEntityId;
		String shortDesc;
        String busEntityTypeCD;
    }	
	
}