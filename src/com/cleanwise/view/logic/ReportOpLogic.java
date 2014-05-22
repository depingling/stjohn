
package com.cleanwise.view.logic;

import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.math.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.OrderOpSearchForm;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.forms.OrderOpItemDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.forms.ReportOpForm;
import com.cleanwise.view.utils.Constants;
import jxl.*;
import jxl.write.*;
import java.text.SimpleDateFormat;


/**
 * <code>ReportOpLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author durval
 */
public class ReportOpLogic {
    
    
    private static final String sDATE = "DATE";
    private static final String sBEG_DATE = "BEG_DATE";
    private static final String sEND_DATE = "END_DATE";
    private static final String sACCOUNT = "ACCOUNT";
    private static final String sACCOUNT_OPT = "ACCOUNT_OPT";
    private static final String sAMOUNT = "AMOUNT";
    private static final String sACCOUNT_LIST= "ACCOUNT_LIST";
    private static final String sCONTRACT= "CONTRACT";
    private static final String sDISTRIBUTOR = "DISTRIBUTOR";
    private static final String sSITE = "SITE";
    private static final String sSITE_LIST= "SITE_LIST";
    private static final String sMANUFACTURER = "MANUFACTURER";
    private static final String sMANUFACTURER_LIST = "MANUFACTURER_LIST";
    private static final String sITEM = "ITEM";
    private static final String sITEM_OPT = "ITEM_OPT";
    private static final String  sITEM_LIST ="ITEM_LIST";
    private static final String sINTERVAL = "INTERVAL";
    private static final String sDATE_TYPE_GROUPING = "DATE_TYPE_GROUPING";
    private static final String sSHIPMENT = "SHIPMENT";
    private static final String sINCLUSIVE = "INCLUSIVE";
    private static final String sSTATE_OPT = "STATE_OPT";
    private static final String sCOUNTY_OPT = "COUNTY_OPT";
    private static final String sDISTRIBUTOR_OPT = "DISTRIBUTOR_OPT";
    private static final String sCATALOG = "CATALOG";
    
    
    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
    ActionForm form)
    throws Exception {
        // init the report name and type arrays
        ArrayList optsType = new ArrayList();
        ArrayList opts = new ArrayList();
        // create a  empty vector so no reports appear until a type is
        // selected
        request.getSession().setAttribute("Report.name.vector", opts);
        request.getSession().setAttribute("Report.type.vector", optsType);
        //Generic report categories
        APIAccess factory = new APIAccess();
        Report reportEjb = factory.getReportAPI();
        ArrayList genericReportCategories = reportEjb.getGenericReportCategories();
        for(int ii=0; ii<genericReportCategories.size(); ii++) {
            String category = (String) genericReportCategories.get(ii);
            if(category==null) continue;
            category = category.trim();
            if(category.length()==0) continue;
            int jj=0;
            for(;jj<optsType.size(); jj++) {
                FormArrayElement fae = (FormArrayElement) optsType.get(jj);
                String categoryTaken = fae.getValue();
                if(categoryTaken.equalsIgnoreCase(category)) break;
            }
            if(jj==optsType.size()) {
                optsType.add(new FormArrayElement(category,category));
            }
        }
        //remove all fields from screen
        initFields(request,form);
    }
    
    
    public static void initReportList(HttpServletRequest request,
    ActionForm form)
    throws Exception {
        
        HttpSession session = request.getSession();
        CleanwiseUser clwUsr = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        ArrayList opts = new ArrayList();
        String rType = (String)request.getParameter("rtype");
        
        //Generic report names
        APIAccess factory = new APIAccess();
        Report reportEjb = factory.getReportAPI();
        ArrayList genericReportNames = reportEjb.getGenericReportNames(rType);
        for(int ii=0; ii<genericReportNames.size(); ii++) {
            String name = (String) genericReportNames.get(ii);
            if(clwUsr.isAuthorizedForReport(name)){
                opts.add(new FormArrayElement(name,name));
            }
        }
        request.getSession().setAttribute("Report.name.vector", opts);
        return;
    }
    
    
    /**
     * init fields let's you build a parameter of constant
     * strings to send to the createFields method.  createFields
     * tokenizes the string you give it and
     * makes beans for all the fields you want to show up on the page
     * using <logic:present name="myFieldBean" checks to see if it
     * exists.  If it does not the field stays blank.
     * @param the request
     * @param the form
     * @exception if the is an exception there will be an error
     */
    public static void initFields(HttpServletRequest request, ActionForm form)
    throws Exception {
        ReportOpForm pForm = (ReportOpForm) form ;
        pForm.setGenericControls(null);
        request.getSession().removeAttribute(sDATE);
        request.getSession().removeAttribute(sACCOUNT);
        request.getSession().removeAttribute(sACCOUNT_OPT);
        request.getSession().removeAttribute(sAMOUNT);
        request.getSession().removeAttribute(sACCOUNT_LIST);
        request.getSession().removeAttribute(sCONTRACT);
        request.getSession().removeAttribute(sDISTRIBUTOR);
        request.getSession().removeAttribute(sSITE);
        request.getSession().removeAttribute(sSITE_LIST);
        request.getSession().removeAttribute(sMANUFACTURER);
        request.getSession().removeAttribute(sMANUFACTURER_LIST);
        request.getSession().removeAttribute(sITEM);
        request.getSession().removeAttribute(sITEM_LIST);
        request.getSession().removeAttribute(sINTERVAL);
        request.getSession().removeAttribute(sDATE_TYPE_GROUPING);
        request.getSession().removeAttribute(sSHIPMENT);
        request.getSession().removeAttribute(sINCLUSIVE);
        request.getSession().removeAttribute(sSTATE_OPT);
        request.getSession().removeAttribute(sCOUNTY_OPT);
        request.getSession().removeAttribute(sDISTRIBUTOR_OPT);
        request.getSession().removeAttribute(sCATALOG);
        request.getSession().removeAttribute(sBEG_DATE);
        request.getSession().removeAttribute(sEND_DATE);
        String fAction = (String)request.getParameter("f_action");
        String rName = (String)request.getParameter("rname");
        if(rName == null){return;}
        if(fAction == null){return;}
        // we don't want any fields to show up if only the report type has been selected
        if (fAction.equals("select")) {
            return;
        } else {
            //Generic Report Parameters
            APIAccess factory = new APIAccess();
            Report reportEjb = factory.getReportAPI();
            
            String rType = (String)request.getParameter("rtype");
            GenericReportControlViewVector parameters = reportEjb.getGenericReportControls(rType,rName);
            parameters.sort("Priority");
            pForm.setGenericControls(parameters);
        }
        return;
    }
    
    private static void createFields(String  list, HttpServletRequest request) {
        
        StringTokenizer st = new StringTokenizer(list,",");
        while (st.hasMoreTokens()) {
            String fieldName = st.nextToken();
            request.getSession().setAttribute(fieldName,fieldName);
        }
    }
    //----------------------------------------------------------------------------
    public static ActionErrors genericReport(HttpServletRequest request, HttpServletResponse res, ReportOpForm pForm)
    //throws Exception
    {
        ActionErrors errors = new ActionErrors();
        try {
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            APIAccess factory = new APIAccess();
            Report reportEjb = factory.getReportAPI();
            Map params = new HashMap();
            String rType = (String)request.getParameter("rtype");
            String rName = (String)request.getParameter("rname");
            //check if they are authorized,  this report should not be displayed to user,
            //so this really shouldn't happen
            if(!(appUser.isAuthorizedForReport(rName))){
                errors.add(rName, new ActionError("error.systemError", "NOT AUTHORIZED"));
                return errors;
            }
            GenericReportControlViewVector grcVV = pForm.getGenericControls();
            for(int ii=0; ii<grcVV.size(); ii++) {
                GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
                String name = grc.getName();
                String label = grc.getLabel();
                if(label==null && label.trim().length()==0) label = name;
                String mf = grc.getMandatoryFl();
                if(mf!=null) mf = mf.toUpperCase();
                boolean mandatoryFl = true;
                if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)) {
                    mandatoryFl = false;
                }
                if(name!=null && name.trim().toUpperCase().endsWith("_OPT")) {
                    mandatoryFl = false;
                }
                String value = grc.getValue();
                if("CUSTOMER".equalsIgnoreCase(name)) {
                    mandatoryFl = false;
                    if(value==null ||value.trim().length()==0) {
                      value = ""+appUser.getUser().getUserId();
                    }
                }
                if(mandatoryFl && (value==null || value.trim().length()==0)) {
                    errors.add("beginDate", new ActionError("variable.empty.error",name));
                }
                String type = grc.getType();
                if(type!=null && value!=null && value.trim().length()>0) {
                    if("DATE".equalsIgnoreCase(type) || "BEG_DATE".equalsIgnoreCase(name) || "END_DATE".equalsIgnoreCase(name)) {
                        if(!isDate(value)) {
                            errors.add(name, new ActionError("error.badDateFormat", label));
                        }
                    } else if("INT".equalsIgnoreCase(type) ||
                    "ACCOUNT".equalsIgnoreCase(name) || "ACCOUNT_OPT" .equalsIgnoreCase(name) || 
                    "CONTRACT".equalsIgnoreCase(name) || "DISTRIBUTOR".equalsIgnoreCase(name) ||
                    "MANUFACTURER".equalsIgnoreCase(name) ||"ITEM".equalsIgnoreCase(name) ||
                    "CATALOG".equalsIgnoreCase(name) ||"ITEM_OPT".equalsIgnoreCase(name) ||
                    "STORE".equalsIgnoreCase(name) ||"STORE_OPT".equalsIgnoreCase(name)||
                    "CUSTOMER".equalsIgnoreCase(name))
                    {
                        if(!isInt(value)) {
                            errors.add(name, new ActionError("variable.integer.format.error", label));
                        }
                    } else if("NUMBER".equalsIgnoreCase(type)) {
                        if(!isNumber(value)) {
                            errors.add(name, new ActionError("error.invalidNumberAmount", label));
                        }
                    }
                }
                if(errors.size()==0) {
                    params.put(grc.getSrcString(), value);
                }
            }
            if(errors.size()>0) {
                return errors;
            }

            String fileName = rName;
            fileName+=".xls";
            fileName = fileName.replaceAll(" ", "-");
            fileName = fileName.replaceAll("/", "-"); //problem with IE6
            res.setContentType("application/x-excel");
            String browser = (String)  request.getHeader("User-Agent");
            boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
            if (!isMSIE6){
	            res.setHeader("extension", "xls");
            }
            if(isMSIE6){
            	res.setHeader("Pragma", "public");
            	res.setHeader("Expires", "0");
            	res.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            	res.setHeader("Cache-Control", "public");      	
        	}
            res.setHeader("Content-disposition", "attachment; filename="+fileName);
            ReportWritter.generateExcelReport(reportEjb, rType, rName, params, res.getOutputStream());
            res.flushBuffer();
        }catch (Exception exc) {
            exc.printStackTrace();
            errors.add("system error", new ActionError("error.systemError", exc.getMessage()));
        }
        return errors;
    }
    
    
    
    public static boolean isDate(String pDate) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            df.parse(pDate);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    public static boolean isInt(String pInt) {
        try {
            Integer.parseInt(pInt);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    public static boolean isNumber(String pDouble) {
        try {
            Double.parseDouble(pDouble);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    
    
    //------------------------------------------------------------------------
    private static String getName(BusEntityDataVector pEntities, String pErpNum) {
        int size = pEntities.size();
        String name = "";
        int beg = 0;
        int end = size-1;
        int middle = size/2;
        while (true) {
            BusEntityData beD = (BusEntityData) pEntities.get(middle);
            if(pErpNum.compareTo(beD.getErpNum())==0) {
                name = beD.getShortDesc();
                break;
            }
            if(beg == end) {
                break;
            }
            if(end-beg==1) {
                if(middle == beg) {
                    beD = (BusEntityData) pEntities.get(end);
                }else {
                    beD = (BusEntityData) pEntities.get(beg);
                }
                if(pErpNum.compareTo(beD.getErpNum())==0) {
                    name = beD.getShortDesc();
                }
                break;
            }
            if(pErpNum.compareTo(beD.getErpNum())>0) {
                beg = middle;
            } else {
                end = middle;
            }
            middle = (beg+end)/2;
        }
        return name;
    }
    
    
}
