package com.cleanwise.view.logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.UniversalDAO;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.CustAcctMgtReportForm;
import com.cleanwise.view.forms.LocateReportAccountForm;
import com.cleanwise.view.forms.LocateReportItemForm;
import com.cleanwise.view.forms.LocateStoreSiteForm;
import com.cleanwise.view.forms.LocateReportDistributorForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.view.utils.GenericReportControlFactory;
import com.cleanwise.view.utils.ReportRequest;
import com.cleanwise.view.utils.ReportWritter;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.pdf.PdfDeliverySchedule;
import com.cleanwise.view.utils.pdf.PdfInvoiceDist;

/**
 * <code>CustAcctMgtReportLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author Liang
 */
public class CustAcctMgtReportLogic {
  private static final String CUSTOMER_REPORT_CATEGORY = "Customer";

  public static final int MAX_HTML_ROWS = 3000;

  private static final Logger log = Logger.getLogger(CustAcctMgtReportLogic.class);

    /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void init(HttpServletRequest request, ActionForm form) throws Exception {
    initConstantList(request);
    ProfilingMgrLogic.getProfilesForSite(request);
    CustAcctMgtReportForm sForm = new CustAcctMgtReportForm();
    (request.getSession()).setAttribute("CUSTOMER_REPORT_FORM", sForm);
    clearSites(request, form);
    clearAccounts(request, form);
    clearItems(request, form);
    clearDistributors(request, form);

    return;
  }

  private class KeepOpen extends Thread {
    HttpServletResponse mResponse;
    public KeepOpen(HttpServletResponse pResp) {
      mResponse = pResp;
    }

    private boolean keepGoing = true;
    public void run() {
      try {
        java.io.PrintWriter pw = mResponse.getWriter();

        pw.println("<html><head>");
        pw.println("<title>Loading...</title>");
        pw.println("</head>");
        pw.println("<body><h1>Loading");

        int idx = 0;
        do {
          pw.println(".");
          sleep(1000);

        } while (keepGoing);

        pw.println("</h1></body></html>");
        mResponse.sendRedirect(mURL);
        pw.flush();
        pw.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private String mURL;
    public void sendTo(String pURL) {
      keepGoing = false;
      mURL = pURL;
    }

  }


    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */
    public static void initConstantList(HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();
        // the current user info
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        boolean hasAccountReports = appUser.hasAccountReports();

        //add the reports, need to add some of the custom ones that are not in the generic reporting framework
        GenericReportViewVector reports = (GenericReportViewVector) appUser.getAuthorizedRuntimeReports().clone();

        //Site schedule
        SiteData currentSiteD = appUser.getSite();
        if (null != currentSiteD &&
                null != currentSiteD.getNextDeliveryDate()) {
            GenericReportView grd = GenericReportView.createValue();
            //XXX this should not be hardcoded, there needs to be a way of getting these reports
            //into the generic report framework
            grd.setReportName(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE);
            grd.setReportCategory(CUSTOMER_REPORT_CATEGORY);
            grd.setLongDesc("Displays order cut-off dates and delivery dates (if applicable).");
            reports.add(grd);
        }
        
        //Budget (JCI) reports
        if (hasAccountReports == true) {
            RefCdDataVector typev = listServiceEjb.getRefCodesCollection("ACCOUNT_REPORT_TYPE_CD");
            if (typev != null) {
                Iterator it = typev.iterator();
                while (it.hasNext()) {
                    RefCdData ref = (RefCdData) it.next();
                    GenericReportView grd = GenericReportView.createValue();
                    grd.setReportName(ref.getValue());
                    //XXX this should not be hardcoded, there needs to be a way of getting these reports
                    //into the generic report framework
                    grd.setReportCategory(CUSTOMER_REPORT_CATEGORY + " Account Reports");
                    if (RefCodeNames.ACCOUNT_REPORT_TYPE_CD.ORDER_INFORMATION_REPORT.equals(ref.getValue())) {
                        grd.setLongDesc("Display ordering activity for the current budget period.");
                    }
                    reports.add(grd);
                }
            }
        }

        AnalyticReportLogic.sortByCategoryName(reports);

        session.setAttribute("CustomerReport.type.vector", reports);

        if (null == appUser.getSite() || null == appUser.getSite().getAccountBusEntity()) {
            return;
        }

        ArrayList opts = new ArrayList();

        // set the months list
        opts = new ArrayList();
        opts.add(new FormArrayElement("1", "January"));
        opts.add(new FormArrayElement("2", "February"));
        opts.add(new FormArrayElement("3", "March"));
        opts.add(new FormArrayElement("4", "April"));
        opts.add(new FormArrayElement("5", "May"));
        opts.add(new FormArrayElement("6", "June"));
        opts.add(new FormArrayElement("7", "July"));
        opts.add(new FormArrayElement("8", "August"));
        opts.add(new FormArrayElement("9", "September"));
        opts.add(new FormArrayElement("10", "October"));
        opts.add(new FormArrayElement("11", "November"));
        opts.add(new FormArrayElement("12", "December"));
        request.getSession().setAttribute("Report.month.vector", opts);

        {
            // set the manufacturer list, keeps seperate cached lists for each store the user rus report on
            // so that if the user crosses store they can run the report differently for the stor they are currently on
            //but the UI only needs to know about one.
            String sessionKey = "Report.all.manufacturer.for.store.id." + appUser.getUserStore().getStoreId();
            BusEntityDataVector mans = (BusEntityDataVector) request.getSession().getAttribute(sessionKey);
            if (mans == null || mans.isEmpty()) {
                mans = factory.getManufacturerAPI().getManufacturerByUserId(String.valueOf(appUser.getUser().getUserId()));
                request.getSession().setAttribute(sessionKey, mans);
            }
            request.getSession().setAttribute("Report.all.manufacturer.for.store", mans);
        }

        // set the years list
        opts = new ArrayList();

        GregorianCalendar calendar = new GregorianCalendar();
        int currentYear = calendar.get(Calendar.YEAR);
        currentYear += 2;

        for (int i = currentYear; i >= 2000; i--) {
            opts.add(new FormArrayElement(String.valueOf(i), String.valueOf(i)));
        }
        request.getSession().setAttribute("Report.year.vector", opts);
        String currAcctId = (String) request.getSession().getAttribute("Report.budget.periods.id");

        int acctid = appUser.getSite().getAccountBusEntity().getBusEntityId();

        if ((request.getSession().getAttribute("Report.budget.periods") == null)
                || currAcctId == null
                || currAcctId.equals(String.valueOf(acctid)) == false) {

            ArrayList fps = factory.getSiteAPI().getAccountReportPeriods(acctid);

            if (fps == null || fps.size() == 0) {

                UniversalDAO.dbrow nrow = UniversalDAO.mkEmptyRow();
                UniversalDAO.dbcolumn ncol = nrow.getColumn(0);
                ncol.colName = "budget_year";
                ncol.colType = "number";
                Calendar cal = new GregorianCalendar();
                Date timeNow = new Date();
                cal.setTime(timeNow);
                ncol.colVal = String.valueOf(cal.get(Calendar.YEAR));

                fps = new ArrayList();
                fps.add(nrow);
            }
            request.getSession().setAttribute("Report.budget.periods", fps);
            request.getSession().setAttribute("Report.budget.periods.id", String.valueOf(acctid));

        }
    }

  /**
   *
   * @param request a <code>HttpServletRequest</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */


  public static ActionErrors checkHasCustReport(HttpServletRequest request) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("selectlocdate",
             new ActionError("error.simpleGenericError",
                             new String("Can't find application user information in session")));

      return ae;
    }

    if (appUser.isNoReporting()) {
      ae.add("selectlocdate",
             new ActionError("error.simpleGenericError",
                             new String("This user doesn't have the ability to see reports")));

      return ae;
    }

    return ae;
  }

    /**
     * <code>initLocDateSelect</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors initLocDateSelect(HttpServletRequest request,
                                                 ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        // the current user info
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (null == appUser) {
            ae.add("selectlocdate",
                    new ActionError("error.simpleGenericError",
                            new String("Can't find application user information in session")));

            return ae;
        }

        CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;

        // the user selected shipping address site
        SiteData currentSiteD = appUser.getSite();
        String[] selectedSites = {
                String.valueOf(currentSiteD.getBusEntity().getBusEntityId())
        };
        sForm.setSelectedSites(selectedSites);

        // all the sites the user can use
        SiteDataVector siteDV = (SiteDataVector) session.getAttribute(
                Constants.APP_USER_SITES);

        if (null == siteDV || 0 == siteDV.size()) {

            if (null != currentSiteD) {
                siteDV = new SiteDataVector();
                siteDV.add(currentSiteD);
            } else {
                ae.add("selectlocdate",
                        new ActionError("error.simpleGenericError",
                                new String("Can't find locations for this user")));

                return ae;
            }
        }

        String reportTypeCd = sForm.getReportTypeCd();

        log.info("************SVC: reportTypeCd = " + reportTypeCd);

        String reportTypeDesc = new String("");
        boolean multipleLocSelectFlag = false;

        if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.TOTAL_VOLUME.equals(reportTypeCd)) {
            reportTypeDesc = new String("This report will combine all purchases for all locations.  It does not reflect tax, handling, miscellaneous charges or credits.");
            multipleLocSelectFlag = false;
        }
        else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.TOTAL_VOLUME_BY_LOCATION.equals(reportTypeCd)) {
            reportTypeDesc = new String("This report displays all purchases by individual location.  It does not reflect tax, handling, miscellaneous charges or credits.");
            multipleLocSelectFlag = true;
        }
        else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_CATEGORY.equals(reportTypeCd)) {
            reportTypeDesc = new String("This report displays selected category purchases by location.");
            multipleLocSelectFlag = false;
        }
        else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM.equals(reportTypeCd)) {
            reportTypeDesc = new String("This report displays selected item purchases by location.");
            multipleLocSelectFlag = false;
        }
        else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.AVERAGE_ORDER_SIZE.equals(reportTypeCd)) {
            reportTypeDesc = new String("This report displays total dollar volume divided by the average # of orders by location.  It does not reflect tax, handling, miscellaneous charges or credits.");
            multipleLocSelectFlag = true;
        }
        else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE.equals(reportTypeCd)) {

            GregorianCalendar wrkCal = new GregorianCalendar();
            wrkCal.set(Calendar.DATE, 1);
            wrkCal.add(Calendar.MONTH, -2);
            Date wrkDate = wrkCal.getTime();
            SimpleDateFormat sdfMonth = new SimpleDateFormat("M");

            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
            sForm.setBeginMonth("" + sdfMonth.format(wrkDate));
            sForm.setBeginYear("" + sdfYear.format(wrkDate));
            wrkCal.add(Calendar.MONTH, 11);
            wrkDate = wrkCal.getTime();
            sForm.setEndMonth("" + sdfMonth.format(wrkDate));
            sForm.setEndYear("" + sdfYear.format(wrkDate));
            multipleLocSelectFlag = false;
        }
        else if (RefCodeNames.ACCOUNT_REPORT_TYPE_CD.ORDER_INFORMATION_REPORT.equals(reportTypeCd)) {
        } else {
            //Generic report
            Report reportEjb = factory.getReportAPI();
            GenericReportControlViewVector parameters = reportEjb.getGenericReportControls(null, reportTypeCd);
            initControls(request,parameters);
            sForm.setReportControls(parameters);

        }

        sForm.setReportTypeDesc(reportTypeDesc);
        sForm.setMultipleLocSelectFlag(multipleLocSelectFlag);

        // Set the locations array
        ArrayList locations = new ArrayList();

        if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.TOTAL_VOLUME.equals(
                reportTypeCd) || 1 == siteDV.size()) {
            sForm.setSelectLocationFlag(false);
            locations.add(new FormArrayElement(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()),
                    currentSiteD.getBusEntity().getShortDesc() + ". " +
                            currentSiteD.getSiteAddress()
                                    .getAddress1()));
        } else {
            sForm.setSelectLocationFlag(true);

            for (int i = 0; i < siteDV.size(); i++) {

                SiteData siteD = (SiteData) siteDV.get(i);
                locations.add(new FormArrayElement(String.valueOf(siteD.getBusEntity().getBusEntityId()),
                        siteD.getBusEntity().getShortDesc() + ". " +
                                siteD.getSiteAddress().getAddress1()));
            }
        }

        sForm.setSiteIdList(locations);

        //iterate throught the controls and lazy intialize any that requier it
        if (sForm.getReportControls() != null) {
            Iterator it = sForm.getReportControls().iterator();
            while (it.hasNext()) {
                GenericReportControlView cntrl = (GenericReportControlView) it.next();
                if ("DISTRIBUTOR".equalsIgnoreCase(cntrl.getName()) || "DISTRIBUTOR_OPT".equalsIgnoreCase(cntrl.getName())) {
                    // set the distributor list, finds all distributors this user is associated with
                    String sessionKey = "Report.distributor.vector";
                    BusEntityDataVector dists = (BusEntityDataVector) request.getSession().getAttribute(sessionKey);
                    if (dists == null || dists.isEmpty()) {
                        dists = factory.getUserAPI().getDistributorBusEntitiesForReports(appUser.getUserId());
                        request.getSession().setAttribute(sessionKey, dists);
                    }
                }
            }
        }

        return ae;
    }


    private static void initControls(HttpServletRequest request, GenericReportControlViewVector grcVwV) throws Exception {
        if (!grcVwV.isEmpty()) {
            Iterator it = grcVwV.iterator();
            GenericReportControlFactory controlFactory = new GenericReportControlFactory();
            while (it.hasNext()) {
                GenericReportControlView control = (GenericReportControlView) it.next();
                try {
                    controlFactory.initControl(request, control);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

  /**
   * <code>checkLocationSelect</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors checkLocationSelect(HttpServletRequest request,
                                                 ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String[] selectedSites = sForm.getSelectedSites();

    if (0 == selectedSites.length) {
      ae.add("selectlocdate",
             new ActionError("error.simpleGenericError",
                             new String("No location is selected, please select location(s)")));

      return ae;
    }

    return ae;
  }

  /**
   * <code>getNextMappingOfLocDateSelect</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return a <code>String</code> value
   * @exception Exception if an error occurs
   */
  public static String getNextMappingOfLocDateSelect(HttpServletRequest request,
    ActionForm form) throws Exception {

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String reportTypeCd = sForm.getReportTypeCd();
    String target = new String("result");

    if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_CATEGORY.equals(reportTypeCd) ||
        RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM.equals(reportTypeCd)) {
      target = new String("next");
    } else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE.equals(reportTypeCd)) {
      target = new String("back");
    }

    return target;
  }

  /**
   * <code>initCategoryItemSelect</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors initCategoryItemSelect(HttpServletRequest request,
    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
    Contract contractEjb = factory.getContractAPI();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't find application user information in session")));

      return ae;
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;

    // the user selected shipping address sites for report
    // for category and item reports, we will have only one site
    String[] selectedSites = sForm.getSelectedSites();

    if (0 == selectedSites.length) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("No location is selected, please select a location")));

      return ae;
    } else if (1 < selectedSites.length) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("More than one location are selected, please select only one location")));

      return ae;
    }

    // all the sites the user can use
    SiteDataVector siteDV = (SiteDataVector) session.getAttribute(
      Constants.APP_USER_SITES);

    if (null == siteDV || 0 == siteDV.size()) {

      SiteData currentSiteD = appUser.getSite();

      if (null != currentSiteD) {
        siteDV = new SiteDataVector();
        siteDV.add(currentSiteD);
      } else {
        ae.add("selectcategory",
               new ActionError("error.simpleGenericError",
                               new String("Can't find locations for this user")));

        return ae;
      }
    }

    // get the site the user selected for reporting
    // for category and item reports, we will only have one site
    SiteDataVector selectedSiteList = new SiteDataVector();

    for (int i = 0; i < selectedSites.length; i++) {

      boolean foundSiteFlag = false;

      for (int j = 0; j < siteDV.size(); j++) {

        SiteData siteD = (SiteData) siteDV.get(j);

        if (Integer.parseInt(selectedSites[i]) == siteD.getBusEntity().getBusEntityId()) {
          selectedSiteList.add(siteD);
          foundSiteFlag = true;

          break;
        }
      }

      if (false == foundSiteFlag) {
        ae.add("selectcategory",
               new ActionError("error.simpleGenericError",
                               new String("Can't find site info for selected location[" + selectedSites[i] +
                                          "]")));

        return ae;
      }
    }

    sForm.setSelectedSiteList(selectedSiteList);

    // set the date range
    ae = initDateRange(request, form);

    if (ae.size() > 0) {

      return ae;
    }

    // get the catalogList and contractList
    // for category and item reports, we will have only one catalog
    // and one contract since we only have one selected site
    ArrayList catalogIdList = new ArrayList();
    ArrayList contractIdList = new ArrayList();
    CatalogDataVector catalogList = new CatalogDataVector();

    for (int i = 0; i < selectedSiteList.size(); i++) {

      SiteData siteD = (SiteData) selectedSiteList.get(i);

      try {

        CatalogData catalogD = catalogInfoEjb.getSiteCatalog(siteD.getBusEntity().getBusEntityId());
        catalogList.add(catalogD);
      } catch (Exception e) {
        log.info(
          "Can't find the catalog for site[siteId=" +
          siteD.getBusEntity().getBusEntityId() + "]");
      }
    }

    if (null == catalogList || 0 == catalogList.size()) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't get the catalogs according to the selected locations")));

      return ae;
    }

    sForm.setCatalogList(catalogList);

    ContractDataVector contractList = new ContractDataVector();

    for (int i = 0; i < catalogList.size(); i++) {

      CatalogData catalogD = (CatalogData) catalogList.get(i);

      try {

        ContractDataVector contractDV = contractEjb.getContractsByCatalog(catalogD.getCatalogId());

        if (null != contractDV && 0 < contractDV.size()) {
          contractList.addAll(contractDV);
        }
      } catch (Exception e) {
        log.info(
          "Can't get the contracts for the catalog[catalogId=" +
          catalogD.getCatalogId() + "]");
      }
    }

    if (null == contractList || 0 == contractList.size()) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't get the contracts according to the selected locations")));

      return ae;
    }

    sForm.setContractList(contractList);

    // set the top categories for all catalog
    // for categor and item reports, we will only have one catalog
    CatalogCategoryDataVector topCategoryList = new CatalogCategoryDataVector();

    for (int i = 0; i < catalogList.size(); i++) {

      CatalogData catalogD = (CatalogData) catalogList.get(i);

      try {

        CatalogCategoryDataVector categoryDV = catalogInfoEjb.getTopCatalogCategories(catalogD.getCatalogId());

        if (null != categoryDV && 0 < categoryDV.size()) {
          topCategoryList.addAll(categoryDV);
        }
      } catch (Exception e) {
        log.info(
          "Can't find top categories for catalog[catalogId=" +
          catalogD.getCatalogId() + "]");
      }
    }

    if (null == topCategoryList || 0 == topCategoryList.size()) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't get the categories according to the selected locations")));

      return ae;
    }

    sForm.setTopCategoryList(topCategoryList);

    String reportTypeCd = sForm.getReportTypeCd();

    //to get the items according to this catagory
    if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM.equals(
      reportTypeCd)) {

      ProductDataVector productList = new ProductDataVector();

      for (int i = 0; i < catalogList.size(); i++) {

        CatalogData catalogD = (CatalogData) catalogList.get(i);

        try {

          ProductDataVector productDV = catalogInfoEjb.getTopCatalogProducts(catalogD.getCatalogId(), 0, SessionTool.getCategoryToCostCenterView(session, 0 , catalogD.getCatalogId()));

          if (null != productDV && 0 < productDV.size()) {
            productList.addAll(productDV);
          }
        } catch (Exception e) {
          log.info(
            "Can't find top products for catalog[catalogId=" +
            catalogD.getCatalogId() + "]");
        }
      }

      sForm.setProductList(productList);
    }

    return ae;
  }

  /**
   * <code>initCategoryItemSelect</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors initDateRange(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    Date beginDate = null;
    Date endDate = null;
    String beginDateS = new String("");
    String endDateS = new String("");
    GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(sForm.getBeginYear()),
      Integer.parseInt(sForm.getBeginMonth()) - 1,
      1);
    beginDate = calendar.getTime();

    if (null == beginDate) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't set the from date")));

      return ae;
    }

    endDate = calendar.getTime();
    if (null == endDate) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't set the end date")));

      return ae;
    }
    int lastDay = 0;
    beginDateS = String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" +
                 String.valueOf(calendar.get(Calendar.DATE)) + "/" +
                 String.valueOf(calendar.get(Calendar.YEAR));
    calendar = new GregorianCalendar(Integer.parseInt(sForm.getEndYear()),
                                     Integer.parseInt(sForm.getEndMonth()) - 1,
                                     1);

    lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    calendar = new GregorianCalendar(Integer.parseInt(sForm.getEndYear()),
                                     Integer.parseInt(sForm.getEndMonth()) - 1,
                                     lastDay);

    endDateS = String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" +
               String.valueOf(calendar.get(Calendar.DATE)) + "/" +
               String.valueOf(calendar.get(Calendar.YEAR));

    sForm.setBeginDate(beginDate);
    sForm.setEndDate(endDate);
    sForm.setBeginDateS(beginDateS);
    sForm.setEndDateS(endDateS);

    // set the dates a year before
    Date beginDateBefore = null;
    Date endDateBefore = null;
    String beginDateBeforeS = new String("");
    String endDateBeforeS = new String("");
    calendar = new GregorianCalendar(
      Integer.parseInt(sForm.getBeginYear()) - 1,
      Integer.parseInt(sForm.getBeginMonth()) - 1, 1);
    beginDateBefore = calendar.getTime();

    if (null == beginDateBefore) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't set the from date of the compared period")));

      return ae;
    }

    beginDateBeforeS = String.valueOf(calendar.get(Calendar.MONTH) + 1) +
                       "/" +
                       String.valueOf(calendar.get(Calendar.DATE)) +
                       "/" +
                       String.valueOf(calendar.get(Calendar.YEAR));
    calendar = new GregorianCalendar(
      Integer.parseInt(sForm.getEndYear()) - 1,
      Integer.parseInt(sForm.getEndMonth()) - 1, 1);
    lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    calendar = new GregorianCalendar(
      Integer.parseInt(sForm.getEndYear()) - 1,
      Integer.parseInt(sForm.getEndMonth()) - 1, lastDay);
    endDateBefore = calendar.getTime();

    if (null == endDateBefore) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't set the end date of the compared period")));

      return ae;
    }

    endDateBeforeS = String.valueOf(calendar.get(Calendar.MONTH) + 1) +
                     "/" + String.valueOf(calendar.get(Calendar.DATE)) +
                     "/" + String.valueOf(calendar.get(Calendar.YEAR));
    sForm.setBeginDateBefore(beginDateBefore);
    sForm.setEndDateBefore(endDateBefore);
    sForm.setBeginDateBeforeS(beginDateBeforeS);
    sForm.setEndDateBeforeS(endDateBeforeS);

    return ae;
  }

  /**
   * <code>initSubCategoryOrItem</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors initSubCategoryOrItem(HttpServletRequest request,
    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
    Contract contractEjb = factory.getContractAPI();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't find application user information in session")));

      return ae;
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String categoryLevel = (String) request.getParameter("level");

    if (null == categoryLevel || "".equals(categoryLevel)) {
      categoryLevel = "0";
    }

    CatalogDataVector catalogList = sForm.getCatalogList();

    if (null == catalogList || 0 == catalogList.size()) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't get the catalogs according to the selected locations")));

      return ae;
    }

    ArrayList childCategoryIdList = sForm.getChildCategoryIdList();
    ArrayList childCategoryListList = sForm.getChildCategoryListList();
    String changedCategoryId = new String("");

    if ("0".equals(categoryLevel)) {
      changedCategoryId = sForm.getSelectedTopCategoryId();

      // if top category is changed, we need clear all child categories
      if (null != childCategoryIdList &&
          0 < childCategoryIdList.size()) {
        childCategoryIdList = new ArrayList();
        childCategoryListList = new ArrayList();
      }
    } else {

      if (null == childCategoryIdList ||
          0 == childCategoryIdList.size()) {
        ae.add("selectcategory",
               new ActionError("error.simpleGenericError",
                               new String("Can't get the child categories")));

        return ae;
      }

      changedCategoryId = (String) childCategoryIdList.get(
        Integer.parseInt(categoryLevel) - 1);

      if (Integer.parseInt(categoryLevel) != childCategoryIdList.size()) {

        for (int i = Integer.parseInt(categoryLevel);
                     i < childCategoryIdList.size(); ) {
          childCategoryIdList.remove(i);
          childCategoryListList.remove(i);
        }
      }
    }

    // set the cjild categories for all catalog
    CatalogCategoryDataVector childCategoryList = new CatalogCategoryDataVector();

    for (int i = 0; i < catalogList.size(); i++) {

      CatalogData catalogD = (CatalogData) catalogList.get(i);

      try {

        CatalogCategoryDataVector categoryDV = catalogInfoEjb.getCatalogChildCategories(catalogD.getCatalogId(),
          Integer.parseInt(
            changedCategoryId));

        if (null != categoryDV && 0 < categoryDV.size()) {
          childCategoryList.addAll(categoryDV);
        }
      } catch (Exception e) {
        log.info(
          "Can't find child categories for catalog[catalogId=" +
          catalogD.getCatalogId() + "parentId=" +
          changedCategoryId + "]");
      }
    }

    if (null != childCategoryList && 0 < childCategoryList.size()) {

      // append the new childCategoryList
      childCategoryIdList.add(new String(""));
      childCategoryListList.add(childCategoryList);
    }

    sForm.setChildCategoryIdList(childCategoryIdList);
    sForm.setChildCategoryListList(childCategoryListList);

    String reportTypeCd = sForm.getReportTypeCd();

    //to get the items according to this catagory
    if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM.equals(
      reportTypeCd)) {

      ProductDataVector productList = new ProductDataVector();

      for (int i = 0; i < catalogList.size(); i++) {

        CatalogData catalogD = (CatalogData) catalogList.get(i);

        try {

          ProductDataVector productDV = new ProductDataVector();

          if ("0".equals(categoryLevel) &&
              "".equals(changedCategoryId)) {
            productDV = catalogInfoEjb.getTopCatalogProducts(catalogD.getCatalogId(), 0, SessionTool.getCategoryToCostCenterView(session, 0 , catalogD.getCatalogId()));
          } else {

            if ("".equals(changedCategoryId)) {
              // will get the upper level category id
              int index = Integer.parseInt(categoryLevel) - 2;

              if (0 <= index) {
                changedCategoryId = (String) childCategoryIdList.get(
                  index);
              } else {
                changedCategoryId = sForm.getSelectedTopCategoryId();
              }
            }

            productDV = catalogInfoEjb.getCatalogChildProducts(catalogD.getCatalogId(),
              Integer.parseInt(
                changedCategoryId));
          }

          if (null != productDV && 0 < productDV.size()) {
            productDV = filterProductsByContract(request, form,
                                                 catalogD.getCatalogId(),
                                                 productDV);
            productList.addAll(productDV);
          }
        } catch (Exception e) {
          log.info(
            "Can't find products for catalog[catalogId=" +
            catalogD.getCatalogId() + "parentId=" +
            changedCategoryId + "]");
        }
      }

      sForm.setProductList(productList);
    }

    return ae;
  }

  /**
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @param catalogId an <code>int</code> value
   * @param productDV a <code>ProductDataVector</code> value
   * @return a <code>ProductDataVector</code> value
   * @exception Exception if an error occurs
   */
  public static ProductDataVector filterProductsByContract(HttpServletRequest request,
    ActionForm form,
    int catalogId,
    ProductDataVector productDV) throws Exception {

    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      return productDV;
    }

    if (false == appUser.isUserOnContract()) {
      return productDV;
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    ContractDataVector contractList = sForm.getContractList();

    if (0 == catalogId || null == contractList ||
        0 == contractList.size()) {
      return productDV;
    }

    boolean foundContractFlag = false;
    ContractData filterContractD = null;

    for (int i = 0; i < contractList.size(); i++) {

      ContractData contractD = (ContractData) contractList.get(i);

      if (catalogId == contractD.getCatalogId()) {
        foundContractFlag = true;
        filterContractD = contractD;

        break;
      }
    }

    if (false == foundContractFlag) {
      return productDV;
    }

    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    Contract contractEjb = factory.getContractAPI();
    ContractItemDataVector contractItemV = new ContractItemDataVector();

    try {
      contractItemV = contractEjb.getItems(filterContractD.getContractId());
    } catch (Exception e) {
      log.info(
        "Can't find contract items [contractId=" +
        filterContractD.getContractId() + "]");

      return productDV;
    }

    if (null == contractItemV || 0 == contractItemV.size()) {
      log.info(
        "Can't find contract items [contractId=" +
        filterContractD.getContractId() + "]");

      return productDV;
    }

    ProductDataVector newProductV = new ProductDataVector();

    for (int i = 0; i < productDV.size(); i++) {

      ProductData productD = (ProductData) productDV.get(i);

      for (int j = 0; j < contractItemV.size(); j++) {

        ContractItemData contractItemD = (ContractItemData) contractItemV.get(
          j);

        if (contractItemD.getItemId() == productD.getProductId()) {
          newProductV.add(productD);

          break;
        }
      }
    }

    return newProductV;
  }

  /**
   * <code>getCustomerReport</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors getCustomerReport(HttpServletRequest request,
                                               HttpServletResponse response,
                                               ActionForm form,
                                               ReportRequest pRepRequest
    ) throws Exception {
    return getCustomerReport_p(request, response, form, pRepRequest);
  }

  public static ActionErrors getCustomerReport_p
    (HttpServletRequest request,
     HttpServletResponse response,
     ActionForm form,
     ReportRequest pRepRequest
    ) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    Site siteEjb = factory.getSiteAPI();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);
    if (null == appUser) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             Constants.NO_APP_USER));

      return ae;
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String reportTypeCd = sForm.getReportTypeCd();
    String reportDbName = sForm.getReportDbName();
    String uaction = request.getParameter("action");
    if (uaction != null && uaction.equals("get_delivery_sched")) {
      reportTypeCd = RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE;
    }

    //call methods according to the report type
    if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.TOTAL_VOLUME.equals(
      reportTypeCd)) {
      ae = initDateRange(request, form);

      if (ae.size() > 0) {

        return ae;
      }

      ae = getReportWithTotalVolume(request, form);
    } else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.TOTAL_VOLUME_BY_LOCATION.equals(
      reportTypeCd)) {
      ae = initDateRange(request, form);

      if (ae.size() > 0) {

        return ae;
      }

      ae = getReportWithTotalVolumeByLocation(request, form);
    } else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_CATEGORY.equals(
      reportTypeCd)) {
      ae = getReportWithVolumeByCategory(request, form);
    } else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM.equals(
      reportTypeCd)) {
      ae = getReportWithVolumeByItem(request, form);
    } else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.AVERAGE_ORDER_SIZE.equals(
      reportTypeCd)) {
      ae = initDateRange(request, form);

      if (ae.size() > 0) {

        return ae;
      }

      ae = getReportWithAverageOrderSize(request, form);
    } else if (RefCodeNames.ACCOUNT_REPORT_TYPE_CD.ORDER_INFORMATION_REPORT.equals(reportTypeCd)) {
      sForm.setReportTypeCd
        (RefCodeNames.ACCOUNT_REPORT_TYPE_CD.ORDER_INFORMATION_REPORT);
      ae = getOrderInfoReport(request, response, form);
    } else if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE.equals(reportTypeCd)) {
      response.setContentType("application/pdf");
      String browser = (String)  request.getHeader("User-Agent");
      boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
      if (!isMSIE6){
    	  response.setHeader("extension", "pdf");
    	  response.setHeader("Content-disposition", "attachment; filename=" + reportTypeCd+".pdf");
      }
      PdfDeliverySchedule pdfDel = new PdfDeliverySchedule();
      java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
      StoreData storeD = appUser.getUserStore();
      String storeDir = ClwCustomizer.getStoreDir();
      String imgPath = ClwCustomizer.getLogoPathForPrinterDisplay(storeD);
      int beginYear = Integer.parseInt(request.getParameter("beginYear"));
      int beginMonth = Integer.parseInt(request.getParameter("beginMonth")) - 1;
      GregorianCalendar beginDateC = new GregorianCalendar(beginYear, beginMonth, 1);
      int endYear = Integer.parseInt(request.getParameter("endYear"));
      int endMonth = Integer.parseInt(request.getParameter("endMonth")) - 1;
      GregorianCalendar endDateC = new GregorianCalendar(endYear, endMonth, 1);
      int siteId = appUser.getSite().getBusEntity().getBusEntityId();
      int accountId = appUser.getSite().getAccountId();
      DistributorData distD = null;
      try {
        distD = siteEjb.getMajorSiteDist(siteId);
      } catch (Exception exc) {}
      ArrayList scheduleOrderDatesVector = null;
      try {
        scheduleOrderDatesVector =
          siteEjb.getOrderScheduleDates(siteId, accountId, beginDateC.getTime(), endDateC.getTime());
      } catch (Exception exc) {}
      pdfDel.generatePdf(appUser, distD, beginDateC, endDateC, scheduleOrderDatesVector, imgPath, out);
//            ae = exportDeliverySchedule(request, response, (CustAcctMgtReportForm) form);
      response.setContentLength(out.size());
      out.writeTo(response.getOutputStream());
      response.flushBuffer();
      response.getOutputStream().close();
    } else {
      //Generic report
      Report reportEjb = factory.getReportAPI();
      GenericReportControlViewVector controls = sForm.getReportControls();
      HashMap params = new HashMap();
      int userId = appUser.getUser().getUserId();
      int storeId = appUser.getUserStore().getStoreId();
      Date date1970 = new Date(0);
      Date tmpDate;
      
      if (pRepRequest != null) {
    	  params.put("DATE_FMT", pRepRequest.mDateFormat);
    	  log.info("TTTTTTTTTT: pRepRequest.mDateFormat = " + pRepRequest.mDateFormat);
      }

      String stringDateFMT = "";
      if (params.containsKey("DATE_FMT")) {
    	  stringDateFMT = (String) params.get("DATE_FMT");
      }
            
      for (int ii = 0; ii < controls.size(); ii++) {
        GenericReportControlView control = (GenericReportControlView) controls.get(ii);
        SiteData currentSiteD = appUser.getSite();
        int siteId = currentSiteD.getBusEntity().getBusEntityId();
        int accountId = currentSiteD.getAccountBusEntity().getBusEntityId();
        String name = control.getName();
        if ("DATE_FMT".equals(name)) {
        	if (!Utility.isSet(stringDateFMT)) {        
        		params.put(name, control.getValue());
        		stringDateFMT = control.getValue();
        	}
        } else if ("ACCOUNT".equals(name)) {
          params.put(name, "" + accountId);
        } else if ("LOCATE_ACCOUNT_MULTI_OPT".equals(name) || "LOCATE_ACCOUNT_MULTI".equals(name) ) {
          boolean isRequired = !(name.toUpperCase().contains("_OPT"));
          AccountUIViewVector accVwV = (AccountUIViewVector) session.getAttribute("Report.parameter.accounts");
          String accIdList = null;
          if (accVwV != null && !accVwV.isEmpty()) {
           for (Iterator iter = accVwV.iterator(); iter.hasNext(); ) {
             AccountUIView aVw = (AccountUIView) iter.next();
             int aId = aVw.getBusEntity().getBusEntityId();
             if (accIdList == null) {
               accIdList = String.valueOf(aId);
             } else {
               accIdList += ", " + aId; ;
             }
           }
         } else if (isRequired){
           String errorMess = ClwI18nUtil.getMessage(request, "report.error.fieldAccountsIsEmpty", null);
           ae.add("Error",  new ActionError("error.simpleGenericError", errorMess));
           return ae;
         }
          params.put(name, accIdList);
        } else if ("SITE".equals(name)) {
          params.put(name, "" + siteId);
        } else if ("SITES".equals(name) || "SITE_MULTI".equals(name) || "SITE_MULTI_OPT".equals(name)|| "LOCATE_SITE_MULTI_OPT".equals(name)
                || "LOCATE_SITE_MULTI".equals(name)) {
          SiteViewVector siteVwV =
            (SiteViewVector) session.getAttribute("Report.parameter.sites");
          String siteIdList = null;
          if (siteVwV != null && !siteVwV.isEmpty()) {
            for (Iterator iter = siteVwV.iterator(); iter.hasNext(); ) {
              SiteView sVw = (SiteView) iter.next();
              int sId = sVw.getId();
              if (siteIdList == null) {
                siteIdList = String.valueOf(sId);
              } else {
                siteIdList += ", " + sId; ;
              }
            }
          } else if (Utility.isSet(name)&& !name.contains("_OPT")) {
            String errorMess = ClwI18nUtil.getMessage(request, "report.error.fieldSitesIsEmpty", null);
            ae.add("Error",
                   new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }

          params.put(name, siteIdList);

        } else if ("LOCATE_DISTRIBUTOR_MULTI_OPT".equals(name) ||
                   "DISTRIBUTOR_MULTI_OPT".equals(name)) {
          DistributorDataVector distDV =
            (DistributorDataVector) session.getAttribute("Report.parameter.distributors");
          String distIdList = null;
          if (distDV != null && !distDV.isEmpty()) {
            for (Iterator iter = distDV.iterator(); iter.hasNext(); ) {
              DistributorData dD = (DistributorData) iter.next();
              int dId = dD.getBusEntity().getBusEntityId();
              if (distIdList == null) {
                distIdList = String.valueOf(dId);
              } else {
                distIdList += ", " + dId; ;
              }
            }
          } else if (Utility.isSet(name)&& !name.contains("_OPT")) {
            String errorMess = ClwI18nUtil.getMessage(request, "report.error.fieldDistributorIsEmpty", null);
            ae.add("Error",
                   new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }

          params.put(name, distIdList);

        } else if ("LOCATE_ITEM_MULTI_OPT".equals(name)) {
          ItemViewVector itemVwV =
            (ItemViewVector) session.getAttribute("Report.parameter.items");
          String itemIdList = null;
          if (itemVwV != null && !itemVwV.isEmpty()) {
            for (Iterator iter = itemVwV.iterator(); iter.hasNext(); ) {
              ItemView iVw = (ItemView) iter.next();
              int iId = iVw.getItemId();
              if (itemIdList == null) {
                itemIdList = String.valueOf(iId);
              } else {
                itemIdList += ", " + iId; ;
              }
            }
          } else if (Utility.isSet(name)&& !name.contains("_OPT")) {
            String errorMess = ClwI18nUtil.getMessage(request, "report.error.fieldItemsIsEmpty", null);
            ae.add("Error",
                   new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }

          params.put(name, itemIdList);

        } else if ("CUSTOMER".equals(name)) {
          params.put(name, "" + userId);
        } else if ("STORE".equals(name) || "STORE_OPT".equals(name) || "STORE_SELECT".equals(name)) {
          params.put(name, "" + storeId);
        } else if ("BEG_DATE_ACTUAL_OPT".equals(name)) {
            if (Utility.isSet(control.getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, control.getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    params.put(name, control.getValue());
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualBeginDate");
                    if (property == null) {
                        property = "ActualBeginDate";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, control.getValue()}, true);
                    ae.add("actualBeginDate", new ActionError("error.simpleError", mess));
                    return ae;
                }
            }
        } else if ("END_DATE_ACTUAL_OPT".equals(name)) {
            if (Utility.isSet(control.getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, control.getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    params.put(name, control.getValue());
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.actualEndDate");
                    if (property == null) {
                        property = "ActualEndDate";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, control.getValue()}, true);
                    ae.add("actualEndDate", new ActionError("error.simpleError", mess));
                    return ae;
                }
            }
        } else if ("BEG_DATE_ESTIMATE_OPT".equals(name)) {
            if (Utility.isSet(control.getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, control.getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    params.put(name, control.getValue());
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.estimateBeginDate");
                    if (property == null) {
                        property = "EstimateBeginDate";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, control.getValue()}, true);
                    ae.add("estimateBeginDate", new ActionError("error.simpleError", mess));
                    return ae;
                }
            }
        } else if ("END_DATE_ESTIMATE_OPT".equals(name)) {
            if (Utility.isSet(control.getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, control.getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    params.put(name, control.getValue());
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.estimateEndDate");
                    if (property == null) {
                        property = "EstimateEndDate";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, control.getValue()}, true);
                    ae.add("estimateEndDate", new ActionError("error.simpleError", mess));
                    return ae;
                }
            }
        } else if ("RECEIVED_DATE_FROM_OPT".equals(name)) {
            if (Utility.isSet(control.getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, control.getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    params.put(name, control.getValue());
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.receivedDateFrom");
                    if (property == null) {
                        property = "ReveivedDateFrom";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, control.getValue()}, true);
                    ae.add("ReveivedDateFrom", new ActionError("error.simpleError", mess));
                    return ae;
                }
            }
        } else if ("RECEIVED_DATE_TO_OPT".equals(name)) {
            if (Utility.isSet(control.getValue())) {
                try {
                    tmpDate = ClwI18nUtil.parseDateInp(request, control.getValue());
                    if (date1970.after(tmpDate)) {
                        throw new Exception();
                    }
                    params.put(name, control.getValue());
                } catch (Exception e) {
                    String property = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.receivedDateTo");
                    if (property == null) {
                        property = "ReceivedDateTo";
                    }
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{property, control.getValue()}, true);
                    ae.add("receivedDateTo", new ActionError("error.simpleError", mess));
                    return ae;
                }
            }
        } else if ("INVOICE_STATUS_SELECT_OPT".equals(name) || "INVOICE_STATUS_SELECT".equals(name)) {
        	if (sForm.getInvoiceStatus() != null){
        		String value = "";
                if(sForm.getInvoiceStatus().trim().equals("-Select-")) {
                } else {
             	   value = sForm.getInvoiceStatus();
                }
				log.info("***DDDDDDDDDD: INVOICE_STATUS_SELECT_OPT or INVOICE_STATUS_SELECT value = " + value);
				control.setValue(value);
            }
        	params.put(name, control.getValue());
        } else if("STATE_OPT".equals(name)) {
			//STJ-4681 : STATE_OPT should be case insensitive.
			String stateOpt = control.getValue();
			if(Utility.isSet(stateOpt)) {
				stateOpt = stateOpt.toUpperCase();
			}
			params.put(name, stateOpt);
        } else if ("ACCOUNT".equals(name)) {
            params.put(name, "" + accountId);
        } else if ("LOCATE_CATALOG_MULTI_OPT".equals(name) || "LOCATE_CATALOG_MULTI".equals(name) ) {
            boolean isRequired = !(name.toUpperCase().contains("_OPT"));
            AccountUIViewVector accVwV = (AccountUIViewVector) session.getAttribute("Report.parameter.catalogs");
            String catalogIdList = null;
            if (accVwV != null && !accVwV.isEmpty()) {
             for (Iterator iter = accVwV.iterator(); iter.hasNext(); ) {
               AccountUIView aVw = (AccountUIView) iter.next();
               int aId = aVw.getBusEntity().getBusEntityId();
               if (catalogIdList == null) {
                 catalogIdList = String.valueOf(aId);
               } else {
                 catalogIdList += ", " + aId; ;
               }
             }
           } else if (isRequired){
             String errorMess = ClwI18nUtil.getMessage(request, "report.error.fieldAccountsIsEmpty", null);
             ae.add("Error",  new ActionError("error.simpleGenericError", errorMess));
             return ae;
           }
            params.put(name, catalogIdList);
        } else {
          params.put(name, control.getValue());
        }
        
        // added validation to DATE type, checkInputDate() method do not catch some of the date problem. 
        String type = control.getType();
        String value = control.getValue();
        if(value != null){
        	value = value.trim();
        }
        if(type!=null && value!=null && value.trim().length()>0) {
        	if("DATE".equalsIgnoreCase(type) ||
        			"BEG_DATE".equalsIgnoreCase(name) ||
        			"END_DATE".equalsIgnoreCase(name) ||
        			"DW_BEGIN_DATE".equalsIgnoreCase(name) ||
        			"DW_END_DATE".equalsIgnoreCase(name)
        	) {
        		String label = control.getLabel();
        		if(label==null) label=name;
        		Object errorMessage=
        			reportEjb.checkInputDate(value, label, stringDateFMT);
        		String eMessage = errorMessage.toString();
        		log.info("******************** eMessage = " + eMessage);
        		if (!eMessage.equals("")) {
        		    // STJ-6092: Kohls: Reports: Inappropriate error message is displayed when user tried to generate report by providing invalid data in 'Begin Date' & 'End Date' text field.
        			//ae.add(label, new ActionError("error.simpleError", eMessage));
                    String mess = ClwI18nUtil.getMessage(request, "error.invalidDate", new Object[]{label, control.getValue()}, true);
                    ae.add(label, new ActionError("error.simpleError", mess));
        		}
        	}
        }
      }
        
      if (!ae.isEmpty())
    	  return ae;
      // Add parameters from the query string.
      String tempv = request.getParameter("year");
      if (null != tempv) {
        params.put("YEAR", tempv);
      }
      tempv = request.getParameter("budget_period");
      if (null != tempv) {
        params.put("BUDGET_PERIOD", tempv);
      }
      tempv = request.getParameter("start_date");
      if (null != tempv) {
        params.put("START_DATE", tempv);
      }
      tempv = request.getParameter("end_date");
      if (null != tempv) {
        params.put("END_DATE", tempv);
      }

      tempv = request.getParameter("DATE_FMT");
      int dateFmtFl = 0;
      if (null != tempv) {
    	  dateFmtFl = 1;
      }
      /***
      int dateFmtFl = 0;
      if (null != tempv) {
          dateFmtFl = 1;
      }
      ***/
      log.info("QQQQQQQQQQQ: tempv = " + tempv);

      // add the date format in the event any user specified
      // dates have to be parsed.  This format is Locale specific
      //


      /*** new piece of code by SVC: check passed report dates ***/

      log.info("***********SVC: params1 = " + params);

      String stringStartDate = "";
      String stringEndDate = "";
      String stringStartDateActualOpt = "";
      String stringEndDateActualOpt = "";
      String stringStartDateEstimateOpt = "";
      String stringEndDateEstimateOpt = "";
      String stringDwStartDate = "";
      String stringDwEndDate = "";
      boolean convertDateFmt = false;

      if (params.containsKey("BEG_DATE")) {
          stringStartDate= (String) params.get("BEG_DATE");
      }
      if (params.containsKey("END_DATE")) {
          stringEndDate= (String) params.get("END_DATE");
      }
      if (params.containsKey("BEG_DATE_OPT")) {
          stringStartDate= (String) params.get("BEG_DATE_OPT");
      }
      if (params.containsKey("END_DATE_OPT")) {
          stringEndDate= (String) params.get("END_DATE_OPT");
      }
      if (params.containsKey("BEG_DATE_ACTUAL_OPT")) {
          stringStartDateActualOpt= (String) params.get("BEG_DATE_ACTUAL_OPT");
      }
      if (params.containsKey("END_DATE_ACTUAL_OPT")) {
          stringEndDateActualOpt= (String) params.get("END_DATE_ACTUAL_OPT");
      }
      if (params.containsKey("BEG_DATE_ESTIMATE_OPT")) {
          stringStartDateEstimateOpt= (String) params.get("BEG_DATE_ESTIMATE_OPT");
      }
      if (params.containsKey("END_DATE_ESTIMATE_OPT")) {
          stringEndDateEstimateOpt= (String) params.get("END_DATE_ESTIMATE_OPT");
      }
      if (params.containsKey("DW_BEGIN_DATE")) {
          stringDwStartDate= (String) params.get("DW_BEGIN_DATE");
      }
      if (params.containsKey("DW_END_DATE")) {
          stringDwEndDate = (String) params.get("DW_END_DATE");
      }
      if (params.containsKey("DATE_FMT") && params.containsKey("BEG_DATE") && params.containsKey("END_DATE"))
      {
         checkInputDate(request, ae, stringDateFMT, stringStartDate, stringEndDate, false);
         if (ae.size() > 0) {
        	 return ae;
         }
    	 if (dateFmtFl == 0 && !stringDateFMT.equals(Constants.SIMPLE_DATE_PATTERN)) {

        	 // DATE_FMT was not passed as a parameter => date in the report is hard coded in US locale date format (by default)
      		 // Convert passed date into US locale date format
      		 String convertedBeginDate = convertDateToAnotherFormat(request, stringStartDate, "Begin Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
            	 return ae;
             }
      		 String convertedEndDate = convertDateToAnotherFormat(request, stringEndDate, "End Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
            	 return ae;
             }
     		 // replace BEG_DATE and END_DATE values in the "params" HashTable
             params.put("BEG_DATE", convertedBeginDate);
             params.put("END_DATE", convertedEndDate);
             convertDateFmt = true;

     	 }
      }
      if (params.containsKey("DATE_FMT") && params.containsKey("BEG_DATE_OPT") && params.containsKey("END_DATE_OPT"))
      {
         checkInputDate(request, ae, stringDateFMT, stringStartDate, stringEndDate, true);
         if (ae.size() > 0) {
        	 return ae;
         }
    	 if (dateFmtFl == 0 && !stringDateFMT.equals(Constants.SIMPLE_DATE_PATTERN)) {

        	 // DATE_FMT was not passed as a parameter => date in the report is hard coded in US locale date format (by default)
      		 // Convert passed date into US locale date format
      		 String convertedBeginDateOpt = "";
             if (Utility.isSet(stringStartDate)) {
                 convertedBeginDateOpt = convertDateToAnotherFormat(request, stringStartDate, "Begin Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
                 if (ae.size() > 0) {
                     return ae;
                 }
             }
      		 String convertedEndDateOpt = "";
             if (Utility.isSet(stringEndDate)) {
                convertedEndDateOpt = convertDateToAnotherFormat(request, stringEndDate, "End Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
                 if (ae.size() > 0) {
                     return ae;
                 }
             }

              // replace BEG_DATE_OPT and END_DATE_OPT values in the "params" HashTable
             params.put("BEG_DATE_OPT", convertedBeginDateOpt);
             params.put("END_DATE_OPT", convertedEndDateOpt);
             convertDateFmt = true;

     	 }
      }
      if (params.containsKey("DATE_FMT") && params.containsKey("BEG_DATE_ACTUAL_OPT") && params.containsKey("END_DATE_ACTUAL_OPT"))
      {
    	 checkInputDate(request, ae, stringDateFMT, stringStartDateActualOpt, stringEndDateActualOpt, true);
         if (ae.size() > 0) {
        	 return ae;
         }
    	 if (dateFmtFl == 0 && !stringDateFMT.equals(Constants.SIMPLE_DATE_PATTERN)) {

        	 // DATE_FMT was not passed as a parameter => date in the report is hard coded in US locale date format (by default)
      		 // Convert passed date into US locale date format
      		 String convertedBeginDateActualOpt = convertDateToAnotherFormat(request, stringStartDateActualOpt, "Actual Begin Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
            	 return ae;
             }
      		 String convertedEndDateActualOpt = convertDateToAnotherFormat(request, stringEndDateActualOpt, "Actual End Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
            	 return ae;
             }
     		 // replace BEG_DATE_ACTUAL_OPT and END_DATE_ACTUAL_OPT values in the "params" HashTable
             params.put("BEG_DATE_ACTUAL_OPT", convertedBeginDateActualOpt);
             params.put("END_DATE_ACTUAL_OPT", convertedEndDateActualOpt);
             convertDateFmt = true;

     	 }
      }
      if (params.containsKey("DATE_FMT") && params.containsKey("BEG_DATE_ESTIMATE_OPT") && params.containsKey("END_DATE_ESTIMATE_OPT"))
      {
    	 checkInputDate(request, ae, stringDateFMT, stringStartDateEstimateOpt, stringEndDateEstimateOpt, true);
         if (ae.size() > 0) {
        	 return ae;
         }
    	 if (dateFmtFl == 0 && !stringDateFMT.equals(Constants.SIMPLE_DATE_PATTERN)) {

        	 // DATE_FMT was not passed as a parameter => date in the report is hard coded in US locale date format (by default)
      		 // Convert passed date into US locale date format
      		 String convertedBeginDateEstimateOpt = convertDateToAnotherFormat(request, stringStartDateEstimateOpt, "Estimate Begin Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
            	 return ae;
             }
      		 String convertedEndDateEstimateOpt = convertDateToAnotherFormat(request, stringEndDateEstimateOpt, "Estimate End Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
            	 return ae;
             }
     		 // replace BEG_DATE_ESTIMATE_OPT and END_DATE_ESTIMATE_OPT values in the "params" HashTable
             params.put("BEG_DATE_ESTIMATE_OPT", convertedBeginDateEstimateOpt);
             params.put("END_DATE_ESTIMATE_OPT", convertedEndDateEstimateOpt);
             convertDateFmt = true;

     	 }
      }
      if (params.containsKey("DATE_FMT") && params.containsKey("DW_BEGIN_DATE") && params.containsKey("DW_END_DATE")) {
         checkInputDate(request, ae, stringDateFMT, stringDwStartDate, stringDwEndDate, false);
         if (ae.size() > 0) {
             return ae;
         }
         if (dateFmtFl == 0 && !stringDateFMT.equals(Constants.SIMPLE_DATE_PATTERN)) {
             // DATE_FMT was not passed as a parameter => date in the report is hard coded in US locale date format (by default)
             // Convert passed date into US locale date format
             String convertedDwStartDate = convertDateToAnotherFormat(request, stringDwStartDate, "Begin Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
                 return ae;
             }
             String convertedDwEndDate = convertDateToAnotherFormat(request, stringDwEndDate, "End Date", ae, stringDateFMT, Constants.SIMPLE_DATE_PATTERN);
             if (ae.size() > 0) {
                 return ae;
             }
             // replace BEG_DATE_ESTIMATE_OPT and END_DATE_ESTIMATE_OPT values in the "params" HashTable
             params.put("DW_BEGIN_DATE", convertedDwStartDate);
             params.put("DW_END_DATE", convertedDwEndDate);
             convertDateFmt = true;
         }
      }


      if (convertDateFmt == true) {
    	  // Date(s) were converted into default US locale date format
    	  // Replace passed date format by default US locale date format
    	  params.put("DATE_FMT", Constants.SIMPLE_DATE_PATTERN);
      }
      log.info("***********SVC: params2 = " + params);

      /**********************************************************/

      if (params.containsKey("BUDGET_PERIODS_INFO")) {
        params.put("BUDGET_PERIODS_INFO", sForm.getSelectedPeriods());
      }

      params.put("USER_ID", String.valueOf(appUser.getUser().getUserId()));
      params.put("USER_TYPE", appUser.getUser().getUserTypeCd());
      params.put("USER_WO_ADMIN", String.valueOf(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_WO_VIEW_ALL_FOR_STORE)));

      GenericReportResultViewVector results = new GenericReportResultViewVector();
      sForm.setReportResults(results);
      sForm.setGenericReportPageNum(results.size() - 1);
      try {

        if(reportDbName!=null && reportDbName.trim().length()>0){
        	results = reportEjb.processGenericReportMulti(null, reportTypeCd, params,reportDbName);
        	GenericReportResultView result = (GenericReportResultView)results.get(0);
        	sForm.setReportTypeCd(result.getName());
        }else{
        	results = reportEjb.processGenericReportMulti(null, reportTypeCd, params);
        }

      } catch (Exception exc) {
        String errorMess = exc.getMessage();
        String repMarker = "^clw";
        if (errorMess != null) {
          int pos = errorMess.indexOf(repMarker);
          if (pos >= 0) {
              String err = ClwI18nUtil.formatEjbError(request, errorMess);
              ae.add("Error", new ActionError("error.simpleGenericError", err));
              return ae;
          }
        }
        exc.printStackTrace();
        errorMess = "Error happened. Please contact customer service";
        ae.add("Error",
               new ActionError("error.simpleGenericError",
                               errorMess));
        return ae;
      }
      sForm.setReportResults(results);
      sForm.setGenericReportPageNum(results.size() - 1);
      sForm.setReportMaxRowCount(getReportMaxRowCount(sForm));
    }

    return ae;
  }
  
  private static Date convertReportDateParam(GenericReportControlView pControl, ReportRequest pRepRequest) throws
    ParseException {
    if (Utility.isSet(pControl.getValue())) {
      SimpleDateFormat frmter;
      if (pRepRequest == null) {
        //should never happen
        frmter = new SimpleDateFormat("MM/dd/yyyy");
      } else {
        frmter = new SimpleDateFormat(pRepRequest.mDateFormat);
      }
      return frmter.parse(pControl.getValue());
    }
    return null;
  }

  /**
   * <code>getReportWithTotalVolume</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors getReportWithTotalVolume(HttpServletRequest request,
    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             Constants.NO_APP_USER));

      return ae;
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String reportTypeCd = sForm.getReportTypeCd();

    // the user selected shipping address site
    SiteData currentSiteD = appUser.getSite();

    if (null == currentSiteD) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             new String("Can't find location the user selected")));

      return ae;
    }

    // get the catalogId for this site
    Integer catalogIdI = (Integer) session.getAttribute(
      Constants.CATALOG_ID);

    if (null == catalogIdI) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("Can't get the catalog Id for this site")));

      return ae;
    }

    int catalogId = catalogIdI.intValue();

    // set the reportResultList
    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();
    reportResultList = getLocationReportResultList(request, form,
      currentSiteD, catalogId);
    sForm.setResultList(reportResultList);

    BigDecimal totalAmountNow = new BigDecimal(0);
    BigDecimal totalAmountBefore = new BigDecimal(0);
    BigDecimal totalAmountChange = new BigDecimal(0);

    if (null != reportResultList && 0 < reportResultList.size()) {

      for (int i = 0; i < reportResultList.size(); i++) {

        CustomerReportResultData resultD = (CustomerReportResultData) reportResultList.get(
          i);
        totalAmountNow = totalAmountNow.add(resultD.getAmountNow());
        totalAmountBefore = totalAmountBefore.add(resultD.getAmountBefore());
        totalAmountChange = totalAmountChange.add(resultD.getAmountChange());
      }
    }

    sForm.setTotalAmount(totalAmountNow);
    sForm.setTotalAmountBefore(totalAmountBefore);
    sForm.setTotalAmountChange(totalAmountChange);

    return ae;
  }

  /**
   * <code>getLocationReportResultList</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @param currentSiteD a <code>SiteData</code> value
   * @param catalogId an <code>int</code> value
   * @return a <code>CustomerReportResultDataVector</code> value
   * @exception Exception if an error occurs
   */
  public static CustomerReportResultDataVector getLocationReportResultList(HttpServletRequest request,
    ActionForm form,
    SiteData currentSiteD,
    int catalogId) throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    Order orderEjb = factory.getOrderAPI();
    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();

    try {
      orderDescV = orderEjb.getOrderStatusDescCollection(searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      orderDescV = new OrderStatusDescDataVector();
    }

    // search the orders according to the compared date range
    searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateBeforeS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateBeforeS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector oldOrderDescV = new OrderStatusDescDataVector();

    try {
      oldOrderDescV = orderEjb.getOrderStatusDescCollection(
        searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      oldOrderDescV = new OrderStatusDescDataVector();
    }

    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();

    if ((null != orderDescV && 0 < orderDescV.size()) ||
        (null != oldOrderDescV && 0 < oldOrderDescV.size())) {

      // determine the items belong to which top category
      // by creating a Hashtable in which itemId is the key,
      // and the categoryShortDesc is the value
      Hashtable categoryHash = new Hashtable();
      CatalogCategoryDataVector topCategoryV = new CatalogCategoryDataVector();

      try {
        topCategoryV = catalogInfoEjb.getTopCatalogCategories(
          catalogId);
      } catch (Exception e) {
        log.info(
          "Can't get the top categories [catalogId=" +
          catalogId + "]");
        topCategoryV = new CatalogCategoryDataVector();
      }

      ProductDataVector topProductV = new ProductDataVector();

      try {
        topProductV = catalogInfoEjb.getTopCatalogProducts(catalogId, 0,SessionTool.getCategoryToCostCenterView(session, 0 , catalogId) );
      } catch (Exception e) {
        log.info(
          "Can't get the top products [catalogId=" +
          catalogId + "]");
        topProductV = new ProductDataVector();
      }

      if (null != topProductV && 0 < topProductV.size()) {

        for (int i = 0; i < topProductV.size(); i++) {

          ProductData productD = (ProductData) topProductV.get(i);
          categoryHash.put(String.valueOf(productD.getProductId()),
                           "TOP");
        }
      }

      if (null != topCategoryV && 0 < topCategoryV.size()) {

        for (int i = 0; i < topCategoryV.size(); i++) {

          CatalogCategoryData topCategoryD = (CatalogCategoryData) topCategoryV.get(
            i);

          try {
            categoryHash = findProductTopCategory(catalogId,
                                                  topCategoryD.getCatalogCategoryId(),
                                                  topCategoryD.getCatalogCategoryShortDesc(),
                                                  categoryHash,
                                                  catalogInfoEjb);
          } catch (Exception e) {
            log.info(
              "Can't construct category Hashtable (top)");
          }
        }
      }

      // determine the orderItems category
      // by creating a Hashtable in wich the categoryShortDesc is the key,
      // and the categoryAmount is the value
      Hashtable orderItemCategoryHash = new Hashtable();
      orderItemCategoryHash = findOrderItemCategoryAmount(orderDescV,
        categoryHash,
        orderItemCategoryHash);

      // determine the old orderItems category
      // by creating a Hashtable in wich the categoryShortDesc is the key,
      // and the categoryAmount is the value
      Hashtable oldOrderItemCategoryHash = new Hashtable();
      oldOrderItemCategoryHash = findOrderItemCategoryAmount(
        oldOrderDescV, categoryHash,
        oldOrderItemCategoryHash);

      // set the reportResultList
      reportResultList = setSiteReportResultList(currentSiteD,
                                                 orderItemCategoryHash,
                                                 oldOrderItemCategoryHash);
    }

    return reportResultList;
  }

  /**
   * <code>findProductTopCategory</code> method.
   *
   * @param catalogId an <code>int</code> value
   * @param parentCategoryId an <code>int</code> value
   * @param topCategoryDesc a <code>String</code> value
   * @param categoryHash a <code>Hashtable</code> value
   * @param catalogInfoEjb a <code>CatalogInformation</code> value
   * @return a <code>Hashtable</code> value
   * @exception Exception if an error occurs
   */
  public static Hashtable findProductTopCategory(int catalogId,
                                                 int parentCategoryId,
                                                 String topCategoryDesc,
                                                 Hashtable categoryHash,
                                                 CatalogInformation catalogInfoEjb) throws Exception {

    if (null == categoryHash) {
      categoryHash = new Hashtable();
    }

    CatalogCategoryDataVector childCategoryV = new CatalogCategoryDataVector();

    try {
      childCategoryV = catalogInfoEjb.getCatalogChildCategories(
        catalogId, parentCategoryId);
    } catch (Exception e) {
      log.info(
        "Can't get the child categories [catalogId=" +
        catalogId + ", parentId=" + parentCategoryId + "]");
      childCategoryV = new CatalogCategoryDataVector();
    }

    ProductDataVector childProductV = new ProductDataVector();

    try {
      childProductV = catalogInfoEjb.getCatalogChildProducts(catalogId,
        parentCategoryId);
    } catch (Exception e) {
      log.info(
        "Can't get the child products [catalogId=" + catalogId +
        ", parentId=" + parentCategoryId + "]");
      childProductV = new ProductDataVector();
    }

    if (null != childProductV && 0 < childProductV.size()) {

      for (int i = 0; i < childProductV.size(); i++) {

        ProductData productD = (ProductData) childProductV.get(i);
        categoryHash.put(String.valueOf(productD.getProductId()),
                         topCategoryDesc);
      }
    }

    // call the method itself to get the subChild category
    if (null != childCategoryV && 0 < childCategoryV.size()) {

      for (int i = 0; i < childCategoryV.size(); i++) {

        CatalogCategoryData categoryD = (CatalogCategoryData) childCategoryV.get(
          i);

        try {
          categoryHash = findProductTopCategory(catalogId,
                                                categoryD.getCatalogCategoryId(),
                                                topCategoryDesc,
                                                categoryHash,
                                                catalogInfoEjb);
        } catch (Exception e) {
          log.info(
            "Can't construct category Hashtable [catalogId=" +
            catalogId + ", parentId=" +
            categoryD.getCatalogCategoryId() + "]");
        }
      }
    }

    return categoryHash;
  }

  /**
   * <code>findOrderItemCategoryAmount</code> method.
   *
   * @param orderDescV an <code>OrderStatusDescDataVector</code> value
   * @param categoryHash a <code>Hashtable</code> value
   * @param orderItemCategoryHash a <code>Hashtable</code> value
   * @return a <code>Hashtable</code> value
   * @exception Exception if an error occurs
   */
  public static Hashtable findOrderItemCategoryAmount(OrderStatusDescDataVector orderDescV,
    Hashtable categoryHash,
    Hashtable orderItemCategoryHash) throws Exception {

    if (null == orderItemCategoryHash) {
      orderItemCategoryHash = new Hashtable();
    }

    if (null == categoryHash) {
      categoryHash = new Hashtable();
    }

    // determine the orderItems category
    // by creating a Hashtable in wich the categoryShortDesc is the key,
    // and the categoryAmount is the value
    if (null != orderDescV && 0 < orderDescV.size()) {

      for (int i = 0; i < orderDescV.size(); i++) {

        OrderItemDataVector orderItemV = ((OrderStatusDescData) orderDescV.get(
          i)).getOrderItemList();

        if (null != orderItemV && 0 < orderItemV.size()) {

          for (int j = 0; j < orderItemV.size(); j++) {

            OrderItemData orderItemD = (OrderItemData) orderItemV.get(
              j);
            BigDecimal itemCost = new BigDecimal(0);

            if (null != orderItemD &&
                null != orderItemD.getCustContractPrice()) {
              itemCost = orderItemD.getCustContractPrice().multiply(new BigDecimal(orderItemD.getTotalQuantityOrdered()));
            }

            String categoryCode = (String) categoryHash.get(String.valueOf(orderItemD.getItemId()));

            if (null == categoryCode ||
                "".equals(categoryCode)) {
              log.info(
                "Item can't find category, set to UNKNOWN, itemId = " +
                orderItemD.getItemId() + " orderId = " +
                orderItemD.getOrderId());
              categoryCode = new String("UNKNOWN");
            }

            BigDecimal categoryAmount = (BigDecimal) orderItemCategoryHash.get(
              categoryCode);

            if (null == categoryAmount) {
              categoryAmount = new BigDecimal(0);
            }

            categoryAmount = categoryAmount.add(itemCost);
            orderItemCategoryHash.put(categoryCode, categoryAmount);

          }
        } // end of if orderItemV exists
      }
    } // end of if orderDescV exists

    return orderItemCategoryHash;
  }

  /**
   * <code>setSiteReportResultList</code> method.
   *
   * @param currentSiteD a <code>SiteData</code> value
   * @param orderItemCategoryHash a <code>Hashtable</code> value
   * @param oldOrderItemCategoryHash a <code>Hashtable</code> value
   * @return a <code>CustomerReportResultDataVector</code> value
   * @exception Exception if an error occurs
   */
  public static CustomerReportResultDataVector setSiteReportResultList(SiteData currentSiteD,
    Hashtable orderItemCategoryHash,
    Hashtable oldOrderItemCategoryHash) throws Exception {

    // set the reportResultList
    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();

    if (true != orderItemCategoryHash.isEmpty() ||
        true != oldOrderItemCategoryHash.isEmpty()) {

      // combine the itemCategoryCd
      ArrayList keySetList = new ArrayList();

      if (true != orderItemCategoryHash.isEmpty()) {

        Set keySet = orderItemCategoryHash.keySet();

        if (null != keySet && 0 < keySet.size()) {
          Object[] keyArray = keySet.toArray();

          for (int i = 0; i < keyArray.length; i++) {
            keySetList.add((String) keyArray[i]);
          }
        }
      }

      if (true != oldOrderItemCategoryHash.isEmpty()) {

        Set oldKeySet = oldOrderItemCategoryHash.keySet();

        if (null != oldKeySet && 0 < oldKeySet.size()) {
          Object[] oldKeyArray = oldKeySet.toArray();

          for (int i = 0; i < oldKeyArray.length; i++) {

            if (false == keySetList.contains(
              (String) oldKeyArray[i])) {
              keySetList.add(oldKeyArray[i]);
            }
          }
        }
      }

      if (null != keySetList && 0 < keySetList.size()) {
        sortItemCategory(keySetList);

        for (int i = 0; i < keySetList.size(); i++) {

          String categoryCode = (String) keySetList.get(i);

          BigDecimal categoryAmount = (BigDecimal) orderItemCategoryHash.get(
            categoryCode);

          if (null == categoryAmount) {
            categoryAmount = new BigDecimal(0);
          }

          CustomerReportResultData resultD = CustomerReportResultData.createValue();
          resultD.setSiteId(currentSiteD.getBusEntity().getBusEntityId());
          resultD.setSiteName(
            currentSiteD.getBusEntity().getShortDesc() +
            ". " +
            currentSiteD.getSiteAddress().getAddress1());
          resultD.setMainCategoryDesc(categoryCode);
          resultD.setAmountNow(categoryAmount);

          BigDecimal oldCategoryAmount = (BigDecimal) oldOrderItemCategoryHash.get(
            categoryCode);

          if (null == oldCategoryAmount) {
            oldCategoryAmount = new BigDecimal(0);
          }

          resultD.setAmountBefore(oldCategoryAmount);
          resultD.setAmountChange(categoryAmount.subtract(
            oldCategoryAmount));
          reportResultList.add(resultD);
        }
      }
    }

    return reportResultList;
  }

  static final Comparator ITEM_CATEGORY_CD = new Comparator() {
    public int compare(Object o1, Object o2) {

      String desc1 = "";

      if (null != (String) o1) {
        desc1 = (String) o1;

        if ("UNKNOWN".equals(desc1)) {

          return 1;
        }
      }

      String desc2 = "";

      if (null != (String) o2) {
        desc2 = (String) o2;

        if ("UNKNOWN".equals(desc2)) {

          return -1;
        }
      }

      return desc1.compareTo(desc2);
    }
  };

  /**
   * Describe <code>sortItemCategory</code> method here.
   *
   * @param v an <code>ArrayList</code> value
   * @exception Exception if an error occurs
   */
  public static void sortItemCategory(ArrayList v) throws Exception {
    Collections.sort(v, ITEM_CATEGORY_CD);
  }

  /**
   * <code>getReportWithTotalVolumeByLocation</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors getReportWithTotalVolumeByLocation(HttpServletRequest request,
    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             new String("Can't find application user information in session")));

      return ae;
    }

    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;

    // the user selected shipping address sites for report
    // for category and item reports, we will have only one site
    String[] selectedSites = sForm.getSelectedSites();

    if (0 == selectedSites.length) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("No location is selected, please select a location")));

      return ae;
    }

    // all the sites the user can use
    SiteDataVector siteDV = (SiteDataVector) session.getAttribute(
      Constants.APP_USER_SITES);

    if (null == siteDV || 0 == siteDV.size()) {

      SiteData currentSiteD = appUser.getSite();

      if (null != currentSiteD) {
        siteDV = new SiteDataVector();
        siteDV.add(currentSiteD);
      } else {
        ae.add("selectcategory",
               new ActionError("error.simpleGenericError",
                               new String("Can't find locations for this user")));

        return ae;
      }
    }

    // get the site the user selected for reporting
    // for category and item reports, we will only have one site
    SiteDataVector selectedSiteList = new SiteDataVector();

    for (int i = 0; i < selectedSites.length; i++) {

      boolean foundSiteFlag = false;

      for (int j = 0; j < siteDV.size(); j++) {

        SiteData siteD = (SiteData) siteDV.get(j);

        if (Integer.parseInt(selectedSites[i]) == siteD.getBusEntity().getBusEntityId()) {
          selectedSiteList.add(siteD);
          foundSiteFlag = true;

          break;
        }
      }

      if (false == foundSiteFlag) {
        ae.add("selectcategory",
               new ActionError("error.simpleGenericError",
                               new String("Can't find site info for selected location[" + selectedSites[i] +
                                          "]")));

        return ae;
      }
    }

    sForm.setSelectedSiteList(selectedSiteList);

    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();

    for (int i = 0; i < selectedSiteList.size(); i++) {

      SiteData siteD = (SiteData) selectedSiteList.get(i);
      CatalogData catalogD = null;

      try {
        catalogD = catalogInfoEjb.getSiteCatalog(siteD.getBusEntity().getBusEntityId());
      } catch (Exception e) {
        log.info(
          "Can't find the catalog for site[siteId=" +
          siteD.getBusEntity().getBusEntityId() + "]");
      }

      if (null != catalogD) {

        CustomerReportResultDataVector siteReportResultList =
          new CustomerReportResultDataVector();
        siteReportResultList = getLocationReportResultList(request,
          form, siteD,
          catalogD.getCatalogId());

        if (null != siteReportResultList &&
            0 < siteReportResultList.size()) {
          reportResultList.addAll(siteReportResultList);
        }
      }
    }

    sForm.setResultList(reportResultList);

    BigDecimal totalAmountNow = new BigDecimal(0);
    BigDecimal totalAmountBefore = new BigDecimal(0);
    BigDecimal totalAmountChange = new BigDecimal(0);

    if (null != reportResultList && 0 < reportResultList.size()) {

      for (int i = 0; i < reportResultList.size(); i++) {

        CustomerReportResultData resultD = (CustomerReportResultData) reportResultList.get(
          i);
        totalAmountNow = totalAmountNow.add(resultD.getAmountNow());
        totalAmountBefore = totalAmountBefore.add(resultD.getAmountBefore());
        totalAmountChange = totalAmountChange.add(resultD.getAmountChange());
      }
    }

    sForm.setTotalAmount(totalAmountNow);
    sForm.setTotalAmountBefore(totalAmountBefore);
    sForm.setTotalAmountChange(totalAmountChange);

    return ae;
  }

  /**
   * <code>getReportWithVolumeByCategory</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors getReportWithVolumeByCategory(HttpServletRequest request,
    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             new String("Can't find application user information in session")));

      return ae;
    }

    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    SiteDataVector selectedSiteList = sForm.getSelectedSiteList();
    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();
    String categoryTreeDesc = new String("");
    ArrayList childCategoryIdList = new ArrayList();
    ArrayList childCategoryListList = new ArrayList();
    childCategoryIdList = sForm.getChildCategoryIdList();
    childCategoryListList = sForm.getChildCategoryListList();

    String topCategoryIdS = sForm.getSelectedTopCategoryId();
    int topCategoryId = 0;

    if (null != topCategoryIdS && !"".equals(topCategoryIdS)) {

      try {
        topCategoryId = Integer.parseInt(topCategoryIdS);
      } catch (Exception e) {
        log.info("Can't parse the selected top categoryId");
      }
    }

    String topCategoryName = new String("");
    CatalogCategoryDataVector topCategoryList = new CatalogCategoryDataVector();
    topCategoryList = sForm.getTopCategoryList();

    if (null == topCategoryList || 0 == topCategoryList.size()) {
      sForm.setResultList(reportResultList);

      return ae;
    }

    boolean matchTopCategory = false;
    int finalCategoryId = topCategoryId;
    String finalCategoryName = topCategoryName;

    for (int i = 0; i < topCategoryList.size(); i++) {

      CatalogCategoryData topCategoryD = (CatalogCategoryData) topCategoryList.get(
        i);

      if (topCategoryId == topCategoryD.getCatalogCategoryId()) {
        matchTopCategory = true;
        topCategoryName = topCategoryD.getCatalogCategoryShortDesc();
        finalCategoryName = topCategoryName;
        categoryTreeDesc += topCategoryD.getCatalogCategoryShortDesc();

        break;
      }
    }

    if (false == matchTopCategory) {
      sForm.setResultList(reportResultList);

      return ae;
    }

    if (null == childCategoryIdList || null == childCategoryListList ||
        0 == childCategoryIdList.size() ||
        0 == childCategoryListList.size() ||
        childCategoryIdList.size() != childCategoryListList.size()) {
      log.info(
        "the childCategoryIdList and childCategoryIdList are not existing or not match");
    } else {

      for (int i = 0; i < childCategoryIdList.size(); i++) {

        String childCategoryIdS = (String) childCategoryIdList.get(i);
        CatalogCategoryDataVector childCategoryList =
          (CatalogCategoryDataVector) childCategoryListList.get(i);

        if (null != childCategoryIdS &&
            !"".equals(childCategoryIdS) &&
            null != childCategoryList &&
            0 < childCategoryList.size()) {

          boolean matchChildCategoryFlag = false;

          for (int j = 0; j < childCategoryList.size(); j++) {

            CatalogCategoryData childCategoryD =
              (CatalogCategoryData) childCategoryList.get(j);

            if (null != childCategoryD &&
                childCategoryIdS.equals(String.valueOf(childCategoryD.getCatalogCategoryId()))) {
              matchChildCategoryFlag = true;
              finalCategoryId = childCategoryD.getCatalogCategoryId();
              finalCategoryName = childCategoryD.getCatalogCategoryShortDesc();
              categoryTreeDesc += " >> " + childCategoryD.getCatalogCategoryShortDesc();

              break;
            }
          }

          if (false == matchChildCategoryFlag) {

            break;
          }
        }
      } // end of for-loop for childCategoryIdList
    }

    sForm.setFinalCategoryId(finalCategoryId);
    sForm.setCategoryTreeDesc(categoryTreeDesc);
    sForm.setTopCategoryName(topCategoryName);
    sForm.setFinalCategoryName(finalCategoryName);

    for (int i = 0; i < selectedSiteList.size(); i++) {

      SiteData siteD = (SiteData) selectedSiteList.get(i);
      CatalogData catalogD = null;

      try {
        catalogD = catalogInfoEjb.getSiteCatalog(siteD.getBusEntity().getBusEntityId());
      } catch (Exception e) {
        log.info(
          "Can't find the catalog for site[siteId=" +
          siteD.getBusEntity().getBusEntityId() + "]");
      }

      if (null != catalogD) {

        CustomerReportResultDataVector categoryReportResultList =
          new CustomerReportResultDataVector();
        ProductDataVector productList = new ProductDataVector();

        try {
          productList = findProductsForCategory(catalogD.getCatalogId(),
                                                finalCategoryId,
                                                productList,
                                                catalogInfoEjb);
        } catch (Exception e) {
          productList = new ProductDataVector();
          log.info(
            "Can't get products for catalogId=" +
            catalogD.getCatalogId() + " categoryId=" +
            finalCategoryId);
        }

        if (null != productList) {
          categoryReportResultList = getCategoryReportResultList(
            request, form, siteD,
            productList);

          if (null != categoryReportResultList &&
              0 < categoryReportResultList.size()) {
            reportResultList.addAll(categoryReportResultList);
          }
        }
      }
    }

    sForm.setResultList(reportResultList);

    BigDecimal totalAmountNow = new BigDecimal(0);
    BigDecimal totalAmountBefore = new BigDecimal(0);
    BigDecimal totalAmountChange = new BigDecimal(0);

    if (null != reportResultList && 0 < reportResultList.size()) {

      for (int i = 0; i < reportResultList.size(); i++) {

        CustomerReportResultData resultD = (CustomerReportResultData) reportResultList.get(
          i);
        totalAmountNow = totalAmountNow.add(resultD.getAmountNow());
        totalAmountBefore = totalAmountBefore.add(resultD.getAmountBefore());
        totalAmountChange = totalAmountChange.add(resultD.getAmountChange());
      }
    }

    sForm.setTotalAmount(totalAmountNow);
    sForm.setTotalAmountBefore(totalAmountBefore);
    sForm.setTotalAmountChange(totalAmountChange);

    return ae;
  }

  /**
   * <code>findProductsForCategory</code> method.
   *
   * @param catalogId an <code>int</code> value
   * @param parentCategoryId an <code>int</code> value
   * @param totalProductList a <code>ProductDataVector</code> value
   * @param catalogInfoEjb a <code>CatalogInformation</code> value
   * @return a <code>ProductDataVector</code> value
   * @exception Exception if an error occurs
   */
  public static ProductDataVector findProductsForCategory(int catalogId,
    int parentCategoryId,
    ProductDataVector totalProductList,
    CatalogInformation catalogInfoEjb) throws Exception {

    if (null == totalProductList) {
      totalProductList = new ProductDataVector();
    }

    CatalogCategoryDataVector childCategoryV = new CatalogCategoryDataVector();

    try {
      childCategoryV = catalogInfoEjb.getCatalogChildCategories(
        catalogId, parentCategoryId);
    } catch (Exception e) {
      log.info(
        "Can't get the child categories [catalogId=" +
        catalogId + ", parentId=" + parentCategoryId + "]");
      childCategoryV = new CatalogCategoryDataVector();
    }

    ProductDataVector childProductV = new ProductDataVector();

    try {
      childProductV = catalogInfoEjb.getCatalogChildProducts(catalogId,
        parentCategoryId);
    } catch (Exception e) {
      log.info(
        "Can't get the child products [catalogId=" + catalogId +
        ", parentId=" + parentCategoryId + "]");
      childProductV = new ProductDataVector();
    }

    if (null != childProductV && 0 < childProductV.size()) {
      totalProductList.addAll(childProductV);
    }

    // call the method itself to get the subChild category
    if (null != childCategoryV && 0 < childCategoryV.size()) {

      for (int i = 0; i < childCategoryV.size(); i++) {

        CatalogCategoryData categoryD = (CatalogCategoryData) childCategoryV.get(
          i);

        try {
          totalProductList = findProductsForCategory(catalogId,
            categoryD.getCatalogCategoryId(),
            totalProductList,
            catalogInfoEjb);
        } catch (Exception e) {
          log.info(
            "Can't find child products [catalogId=" +
            catalogId + ", parentId=" +
            categoryD.getCatalogCategoryId() + "]");
        }
      }
    }

    return totalProductList;
  }

  /**
   * <code>getCategoryReportResultList</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @param currentSiteD a <code>SiteData</code> value
   * @param productList a <code>ProductDataVector</code> value
   * @return a <code>CustomerReportResultDataVector</code> value
   * @exception Exception if an error occurs
   */
  public static CustomerReportResultDataVector getCategoryReportResultList(HttpServletRequest request,
    ActionForm form,
    SiteData currentSiteD,
    ProductDataVector productList) throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    Order orderEjb = factory.getOrderAPI();
    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();

    try {
      orderDescV = orderEjb.getOrderStatusDescCollection(searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      orderDescV = new OrderStatusDescDataVector();
    }

    // search the orders according to the compared date range
    searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateBeforeS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateBeforeS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector oldOrderDescV = new OrderStatusDescDataVector();

    try {
      oldOrderDescV = orderEjb.getOrderStatusDescCollection(
        searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      oldOrderDescV = new OrderStatusDescDataVector();
    }

    BigDecimal categoryAmount = findOrderItemCategoryAmount(orderDescV,
      productList);
    BigDecimal oldCategoryAmount = findOrderItemCategoryAmount(
      oldOrderDescV, productList);

    // set the reportResultList
    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();
    CustomerReportResultData resultD = CustomerReportResultData.createValue();
    resultD.setSiteId(currentSiteD.getBusEntity().getBusEntityId());
    resultD.setSiteName(
      currentSiteD.getBusEntity().getShortDesc() + ". " +
      currentSiteD.getSiteAddress().getAddress1());
    resultD.setMainCategoryDesc(sForm.getTopCategoryName());

    if (sForm.getFinalCategoryName().equals(sForm.getTopCategoryName())) {
      resultD.setSubCategoryDesc(new String(""));
    } else {
      resultD.setSubCategoryDesc(sForm.getFinalCategoryName());
    }

    resultD.setAmountNow(categoryAmount);
    resultD.setAmountBefore(oldCategoryAmount);
    resultD.setAmountChange(categoryAmount.subtract(oldCategoryAmount));
    reportResultList.add(resultD);

    return reportResultList;
  }

  /**
   * <code>findOrderItemCategoryAmount</code> method.
   *
   * @param orderDescV an <code>OrderStatusDescDataVector</code> value
   * @param productList a <code>ProductDataVector</code> value
   * @return a <code>BigDecimal</code> value
   * @exception Exception if an error occurs
   */
  public static BigDecimal findOrderItemCategoryAmount(OrderStatusDescDataVector orderDescV,
    ProductDataVector productList) throws Exception {

    BigDecimal categoryAmount = new BigDecimal(0);

    // determine the orderItems category
    if (null != orderDescV && 0 < orderDescV.size()) {

      for (int i = 0; i < orderDescV.size(); i++) {

        OrderItemDataVector orderItemV = ((OrderStatusDescData) orderDescV.get(
          i)).getOrderItemList();

        if (null != orderItemV && 0 < orderItemV.size()) {

          for (int j = 0; j < orderItemV.size(); j++) {

            OrderItemData orderItemD = (OrderItemData) orderItemV.get(
              j);
            boolean matchItemFlag = false;

            for (int k = 0; k < productList.size(); k++) {

              ProductData productD = (ProductData) productList.get(
                k);

              if (productD.getProductId() == orderItemD.getItemId()) {
                matchItemFlag = true;

                break;
              }
            }

            if (true == matchItemFlag) {

              BigDecimal itemCost = new BigDecimal(0);

              if (null != orderItemD &&
                  null != orderItemD.getCustContractPrice()) {
                itemCost = orderItemD.getCustContractPrice().multiply(new BigDecimal(orderItemD.getTotalQuantityOrdered()));

              }

              categoryAmount = categoryAmount.add(itemCost);
            }
          } // end of loop for orderItemV
        } // end of if orderItemV exists
      }
    } // end of if orderDescV exists

    return categoryAmount;
  }

  /**
   * <code>getReportWithVolumeByItem</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors getReportWithVolumeByItem(HttpServletRequest request,
    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             new String("Can't find application user information in session")));

      return ae;
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    SiteDataVector selectedSiteList = sForm.getSelectedSiteList();

    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();
    String[] selectedItems = sForm.getSelectedItems();
    ProductDataVector productList = sForm.getProductList();
    ProductDataVector selectedProductList = new ProductDataVector();

    for (int i = 0; i < selectedItems.length; i++) {

      String selectedItemIdS = selectedItems[i];

      for (int j = 0; j < productList.size(); j++) {

        ProductData productD = (ProductData) productList.get(j);

        if (selectedItemIdS.equals(String.valueOf(productD.getProductId()))) {
          selectedProductList.add(productD);

          break;
        }
      }
    }


    for (int i = 0; i < selectedSiteList.size(); i++) {

      SiteData siteD = (SiteData) selectedSiteList.get(i);

      if (null != selectedProductList &&
          0 < selectedProductList.size()) {

        CustomerReportResultDataVector itemReportResultList =
          new CustomerReportResultDataVector();
        itemReportResultList = getItemReportResultList(request, form,
          siteD,
          selectedProductList);

        if (null != itemReportResultList &&
            0 < itemReportResultList.size()) {
          reportResultList.addAll(itemReportResultList);
        }
      }
    }

    sForm.setResultList(reportResultList);

    BigDecimal totalAmountNow = new BigDecimal(0);
    BigDecimal totalAmountBefore = new BigDecimal(0);
    BigDecimal totalAmountChange = new BigDecimal(0);

    if (null != reportResultList && 0 < reportResultList.size()) {

      for (int i = 0; i < reportResultList.size(); i++) {

        CustomerReportResultData resultD = (CustomerReportResultData) reportResultList.get(
          i);
        totalAmountNow = totalAmountNow.add(resultD.getAmountNow());
        totalAmountBefore = totalAmountBefore.add(resultD.getAmountBefore());
        totalAmountChange = totalAmountChange.add(resultD.getAmountChange());
      }
    }

    sForm.setTotalAmount(totalAmountNow);
    sForm.setTotalAmountBefore(totalAmountBefore);
    sForm.setTotalAmountChange(totalAmountChange);

    return ae;
  }

  /**
   * <code>getItemReportResultList</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @param currentSiteD a <code>SiteData</code> value
   * @param productList a <code>ProductDataVector</code> value
   * @return a <code>CustomerReportResultDataVector</code> value
   * @exception Exception if an error occurs
   */
  public static CustomerReportResultDataVector getItemReportResultList(HttpServletRequest request,
    ActionForm form,
    SiteData currentSiteD,
    ProductDataVector productList) throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    Order orderEjb = factory.getOrderAPI();
    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();
    try {
      orderDescV = orderEjb.getOrderStatusDescCollection(searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      orderDescV = new OrderStatusDescDataVector();
    }

    // search the orders according to the compared date range
    searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateBeforeS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateBeforeS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector oldOrderDescV = new OrderStatusDescDataVector();

    try {
      oldOrderDescV = orderEjb.getOrderStatusDescCollection(
        searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      oldOrderDescV = new OrderStatusDescDataVector();
    }

    // set the reportResultList
    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();

    for (int i = 0; i < productList.size(); i++) {

      ProductData productD = (ProductData) productList.get(i);
      BigDecimal itemAmount = findOrderItemAmount(orderDescV, productD);
      BigDecimal oldItemAmount = findOrderItemAmount(oldOrderDescV,
        productD);
      CustomerReportResultData resultD = CustomerReportResultData.createValue();
      resultD.setSiteId(currentSiteD.getBusEntity().getBusEntityId());
      resultD.setSiteName(
        currentSiteD.getBusEntity().getShortDesc() + ". " +
        currentSiteD.getSiteAddress().getAddress1());
      resultD.setProductName(productD.getShortDesc());
      resultD.setItemId(productD.getProductId());
      resultD.setAmountNow(itemAmount);
      resultD.setAmountBefore(oldItemAmount);
      resultD.setAmountChange(itemAmount.subtract(oldItemAmount));
      reportResultList.add(resultD);
    }

    return reportResultList;
  }

  /**
   * <code>findOrderItemAmount</code> method.
   *
   * @param orderDescV an <code>OrderStatusDescDataVector</code> value
   * @param productD a <code>ProductData</code> value
   * @return a <code>BigDecimal</code> value
   * @exception Exception if an error occurs
   */
  public static BigDecimal findOrderItemAmount(OrderStatusDescDataVector orderDescV,
                                               ProductData productD) throws Exception {

    BigDecimal itemAmount = new BigDecimal(0);

    // determine the orderItems category
    if (null != orderDescV && 0 < orderDescV.size()) {

      for (int i = 0; i < orderDescV.size(); i++) {

        OrderItemDataVector orderItemV = ((OrderStatusDescData) orderDescV.get(
          i)).getOrderItemList();

        if (null != orderItemV && 0 < orderItemV.size()) {

          for (int j = 0; j < orderItemV.size(); j++) {

            OrderItemData orderItemD = (OrderItemData) orderItemV.get(
              j);

            if (productD.getProductId() == orderItemD.getItemId()) {

              BigDecimal itemCost = new BigDecimal(0);

              if (null != orderItemD &&
                  null != orderItemD.getCustContractPrice()) {
                itemCost = orderItemD.getCustContractPrice().multiply(new BigDecimal(orderItemD.getTotalQuantityOrdered()));

              }

              itemAmount = itemAmount.add(itemCost);
            }
          } // end of loop for orderItemV
        } // end of if orderItemV exists
      }
    } // end of if orderDescV exists

    return itemAmount;
  }

  /**
   * <code>getReportWithAverageOrderSize</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors getReportWithAverageOrderSize(HttpServletRequest request,
    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             new String("Can't find application user information in session")));

      return ae;
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;

    // the user selected shipping address sites for report
    // for category and item reports, we will have only one site
    String[] selectedSites = sForm.getSelectedSites();

    if (0 == selectedSites.length) {
      ae.add("selectcategory",
             new ActionError("error.simpleGenericError",
                             new String("No location is selected, please select a location")));

      return ae;
    }

    // all the sites the user can use
    SiteDataVector siteDV = (SiteDataVector) session.getAttribute(
      Constants.APP_USER_SITES);

    if (null == siteDV || 0 == siteDV.size()) {

      SiteData currentSiteD = appUser.getSite();

      if (null != currentSiteD) {
        siteDV = new SiteDataVector();
        siteDV.add(currentSiteD);
      } else {
        ae.add("selectcategory",
               new ActionError("error.simpleGenericError",
                               new String("Can't find locations for this user")));

        return ae;
      }
    }

    // get the site the user selected for reporting
    // for category and item reports, we will only have one site
    SiteDataVector selectedSiteList = new SiteDataVector();

    for (int i = 0; i < selectedSites.length; i++) {

      boolean foundSiteFlag = false;

      for (int j = 0; j < siteDV.size(); j++) {

        SiteData siteD = (SiteData) siteDV.get(j);

        if (Integer.parseInt(selectedSites[i]) == siteD.getBusEntity().getBusEntityId()) {
          selectedSiteList.add(siteD);
          foundSiteFlag = true;

          break;
        }
      }

      if (false == foundSiteFlag) {
        ae.add("selectcategory",
               new ActionError("error.simpleGenericError",
                               new String("Can't find site info for selected location[" + selectedSites[i] +
                                          "]")));

        return ae;
      }
    }

    sForm.setSelectedSiteList(selectedSiteList);

    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();

    for (int i = 0; i < selectedSiteList.size(); i++) {

      SiteData siteD = (SiteData) selectedSiteList.get(i);
      CustomerReportResultDataVector orderSizeReportResultList =
        new CustomerReportResultDataVector();
      orderSizeReportResultList = getAvgOrderSizeReportResultList(
        request, form, siteD);

      if (null != orderSizeReportResultList &&
          0 < orderSizeReportResultList.size()) {
        reportResultList.addAll(orderSizeReportResultList);
      }
    }

    sForm.setResultList(reportResultList);

    BigDecimal totalAmountNow = new BigDecimal(0);
    BigDecimal totalAmountBefore = new BigDecimal(0);
    BigDecimal totalAmountChange = new BigDecimal(0);
    int totalOrderNumNow = 0;
    int totalOrderNumBefore = 0;
    int totalOrderNumChange = 0;
    BigDecimal avgOrderSizeNow = new BigDecimal(0);
    BigDecimal avgOrderSizeBefore = new BigDecimal(0);
    BigDecimal avgOrderSizeChange = new BigDecimal(0);

    if (null != reportResultList && 0 < reportResultList.size()) {

      for (int i = 0; i < reportResultList.size(); i++) {

        CustomerReportResultData resultD = (CustomerReportResultData) reportResultList.get(
          i);
        totalAmountNow = totalAmountNow.add(resultD.getAmountNow());
        totalAmountBefore = totalAmountBefore.add(resultD.getAmountBefore());
        totalAmountChange = totalAmountChange.add(resultD.getAmountChange());
        totalOrderNumNow += resultD.getOrderNumNow();
        totalOrderNumBefore += resultD.getOrderNumBefore();
        totalOrderNumChange += resultD.getOrderNumChange();
      }
    }

    if (0 != totalOrderNumNow) {
      avgOrderSizeNow = totalAmountNow.divide((new BigDecimal(
        totalOrderNumNow)),
                                              BigDecimal.ROUND_HALF_UP);
    }

    if (0 != totalOrderNumBefore) {
      avgOrderSizeBefore = totalAmountBefore.divide((new BigDecimal(
        totalOrderNumBefore)),
        BigDecimal.ROUND_HALF_UP);
    }

    avgOrderSizeChange = avgOrderSizeNow.subtract(avgOrderSizeBefore);
    sForm.setTotalAmount(totalAmountNow);
    sForm.setTotalAmountBefore(totalAmountBefore);
    sForm.setTotalAmountChange(totalAmountChange);
    sForm.setTotalOrderNum(totalOrderNumNow);
    sForm.setTotalOrderNumBefore(totalOrderNumBefore);
    sForm.setTotalOrderNumChange(totalOrderNumChange);
    sForm.setAvgOrderSize(avgOrderSizeNow);
    sForm.setAvgOrderSizeBefore(avgOrderSizeBefore);
    sForm.setAvgOrderSizeChange(avgOrderSizeChange);

    return ae;
  }

  /**
   * <code>getAvgOrderSizeReportResultList</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @param currentSiteD a <code>SiteData</code> value
   * @return a <code>CustomerReportResultDataVector</code> value
   * @exception Exception if an error occurs
   */
  public static CustomerReportResultDataVector getAvgOrderSizeReportResultList(HttpServletRequest request,
    ActionForm form,
    SiteData currentSiteD) throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    Order orderEjb = factory.getOrderAPI();
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();
    try {
      orderDescV = orderEjb.getOrderStatusDescCollection(searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      orderDescV = new OrderStatusDescDataVector();
    }

    // search the orders according to the compared date range
    searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateBeforeS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateBeforeS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector oldOrderDescV = new OrderStatusDescDataVector();

    try {
      oldOrderDescV = orderEjb.getOrderStatusDescCollection(
        searchCriteria, storeIds);
    } catch (Exception e) {
      log.info(
        "Can't find the orders according to the criteria");
      oldOrderDescV = new OrderStatusDescDataVector();
    }

    // set the reportResultList
    CustomerReportResultDataVector reportResultList = new CustomerReportResultDataVector();
    CustomerReportResultData resultD = CustomerReportResultData.createValue();
    BigDecimal orderAmount = findTotalOrderAmount(orderDescV);
    BigDecimal oldOrderAmount = findTotalOrderAmount(oldOrderDescV);
    resultD.setSiteId(currentSiteD.getBusEntity().getBusEntityId());
    resultD.setSiteName(
      currentSiteD.getBusEntity().getShortDesc() + ". " +
      currentSiteD.getSiteAddress().getAddress1());
    resultD.setAmountNow(orderAmount);
    resultD.setAmountBefore(oldOrderAmount);
    resultD.setAmountChange(orderAmount.subtract(oldOrderAmount));
    resultD.setOrderNumNow(orderDescV.size());

    if (0 < orderDescV.size()) {
      resultD.setAvgOrderSizeNow(resultD.getAmountNow().divide((new BigDecimal(orderDescV.size())),
        BigDecimal.ROUND_HALF_UP));
    }

    resultD.setOrderNumBefore(oldOrderDescV.size());

    if (0 < oldOrderDescV.size()) {
      resultD.setAvgOrderSizeBefore(resultD.getAmountBefore().divide((new BigDecimal(oldOrderDescV.size())),
        BigDecimal.ROUND_HALF_UP));
    }

    resultD.setAvgOrderSizeChange(resultD.getAvgOrderSizeNow().subtract(resultD.getAvgOrderSizeBefore()));

    int orderNumChange = orderDescV.size() - oldOrderDescV.size();
    resultD.setOrderNumChange(orderNumChange);
    reportResultList.add(resultD);

    return reportResultList;
  }

  /**
   * <code>findOrderAmount</code> method.
   *
   * @param orderDescV an <code>OrderStatusDescDataVector</code> value
   * @return a <code>BigDecimal</code> value
   * @exception Exception if an error occurs
   */
  public static BigDecimal findTotalOrderAmount(OrderStatusDescDataVector orderDescV) throws Exception {

    BigDecimal orderAmount = new BigDecimal(0);

    // determine the orderItems category
    if (null != orderDescV && 0 < orderDescV.size()) {

      for (int i = 0; i < orderDescV.size(); i++) {

        OrderItemDataVector orderItemV = ((OrderStatusDescData) orderDescV.get(
          i)).getOrderItemList();

        if (null != orderItemV && 0 < orderItemV.size()) {

          for (int j = 0; j < orderItemV.size(); j++) {

            OrderItemData orderItemD = (OrderItemData) orderItemV.get(
              j);
            BigDecimal itemCost = new BigDecimal(0);

            if (null != orderItemD &&
                null != orderItemD.getCustContractPrice()) {
              itemCost = orderItemD.getCustContractPrice().multiply(new BigDecimal(orderItemD.getTotalQuantityOrdered()));

            }

            orderAmount = orderAmount.add(itemCost);
          } // end of loop for orderItemV
        } // end of if orderItemV exists
      }
    } // end of if orderDescV exists

    return orderAmount;
  }


  private static void setHeader(WritableSheet pSheet, int pCol,
                                String pColName,
                                WritableCellFormat pHeaderFormat) throws Exception {

    if (pCol < 0)

      return;

    Label label;
    label = new Label(pCol, 0, pColName, pHeaderFormat);
    pSheet.addCell(label);
  }

  private static void setCell(WritableSheet pSheet, int pCol, int pRow,
                              Object pValue, WritableCellFormat pFormat) throws Exception {

    if (pCol < 0)

      return;

    jxl.write.Label label;
    jxl.write.Number number;
    jxl.write.DateTime date;

    if (pValue instanceof Date && pValue != null) {

      java.util.Date dateVal = (java.util.Date) pValue;

      if (pFormat != null)
        date = new DateTime(pCol, pRow, dateVal, pFormat);
      else
        date = new DateTime(pCol, pRow, dateVal);

      pSheet.addCell(date);
    } else if (pValue instanceof BigDecimal && pValue != null) {

      BigDecimal amt = (BigDecimal) pValue;

      if (pFormat != null) {
        number = new jxl.write.Number(pCol, pRow, amt.doubleValue(),
                                      pFormat);
      } else {
        number = new jxl.write.Number(pCol, pRow, amt.doubleValue());
      }

      pSheet.addCell(number);
    } else if (pValue instanceof Integer && pValue != null) {

      Integer amt = (Integer) pValue;

      if (pFormat != null) {
        number = new jxl.write.Number(pCol, pRow, amt.doubleValue(),
                                      pFormat);
      } else {
        number = new jxl.write.Number(pCol, pRow, amt.doubleValue());
      }

      pSheet.addCell(number);
    } else {
      label = new Label(pCol, pRow, String.valueOf(pValue));
      pSheet.addCell(label);
    }
  }

  private static void setCellNoNull(WritableSheet pSheet, int pCol, int pRow,
                                    Object pValue,
                                    WritableCellFormat pFormat) throws Exception {

    if (pValue == null)

      return;

    setCell(pSheet, pCol, pRow, pValue, pFormat);
  }

  public static ActionErrors getOrderInfoReport
    (
      HttpServletRequest request,
      HttpServletResponse pResp,
      ActionForm form) throws Exception {
    CustAcctMgtReportLogic m = new CustAcctMgtReportLogic();
    return m.getOrderInfoReport_p
      (request, pResp, form);

  }

  private ActionErrors getOrderInfoReport_p
    (
      HttpServletRequest request,
      HttpServletResponse pResp,
      ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
      Constants.APP_USER);

    if (null == appUser) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             Constants.NO_APP_USER));

      return ae;
    }

    APIAccess factory = (APIAccess) session.getAttribute(
      Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    SiteData currentSiteD = appUser.getSite();

    if (null == currentSiteD) {
      ae.add("custreport",
             new ActionError("error.simpleGenericError",
                             new String("Can't find location the user selected")));

      return ae;
    }

    Site siteEjb = factory.getSiteAPI();
    java.util.ArrayList v = null;

    int byear = Integer.parseInt(request.getParameter("year")),
                bperiod = Integer.parseInt(request.getParameter("budget_period"));

    if (appUser.isaMSB() || appUser.isaCustomer()) {
      // Limit the information to those sites
      // tied to the user.
      v = siteEjb.getOrderReport(RefCodeNames.USER_TYPE_CD.MSB,
                                 appUser.getUser().getUserId(),
                                 byear,
                                 bperiod);
    } else {
      v = siteEjb.getOrderReport
          (
            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,
            currentSiteD.getAccountBusEntity().getBusEntityId(),
            byear, bperiod);
    }

    sForm.setOrderReport(v);

    return ae;
  }


  static public ActionErrors getGenericReportPage
    (HttpServletRequest request,
     ActionForm form, String pPageName) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    // the current user info
    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    GenericReportResultViewVector reportResults = sForm.getReportResults();
    boolean foundFl = false;
    for (int ii = 0; ii < reportResults.size(); ii++) {
      GenericReportResultView page = (GenericReportResultView) reportResults.get(ii);
      String name = page.getName();
      if (pPageName.equals(name)) {
        foundFl = true;
        sForm.setGenericReportPageNum(ii);
        break;
      }
    }
    if (!foundFl) {
      ae.add("error", new ActionError("error.simpleGenericError",
                                      "Unknown request: " + pPageName));

      return ae;
    }
    return ae;
  }

  public static void downloadGenericReport(
    HttpServletRequest request,
    HttpServletResponse res,
    ActionForm form) throws Exception {
    downloadGenericReport(request, res, form, null);
  }

  //Download generic report
  public static void downloadGenericReport(
    HttpServletRequest request,
    HttpServletResponse res,
    ActionForm form, ReportRequest pRepRequest) throws Exception {

    CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
    GenericReportResultViewVector reportResults = sForm.getReportResults();
    String fileName = sForm.getReportTypeCd();
    String reportFormat = null ;
    if (reportResults!=null && reportResults.get(0)!=null){
      reportFormat = ((GenericReportResultView)reportResults.get(0)).getReportFormat();
    }
    fileName += ((reportFormat != null && reportFormat.contains("xlsx"))) ?".xlsx" :".xls";

    // Replace spaces so that the client application
    // will recognize the full file name and not just
    // the first work of the name specified.
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
	res.setHeader("Content-disposition", "attachment; filename=" + fileName);
	File tmpFile = File.createTempFile("report", ".xls");
    OutputStream fout = new BufferedOutputStream(new FileOutputStream(tmpFile));
    ReportWritter.writeExcelReportMulti(reportResults,fout,pRepRequest);
    fout.flush();
    fout.close();
    res.setHeader("Content-Length", String.valueOf(tmpFile.length()));
    IOUtilities.copyStream(new FileInputStream(tmpFile), res.getOutputStream());
    res.flushBuffer();
    try{
    	tmpFile.delete();
    }catch(Exception e){
    	//non-fatal keep going.
    	log.error("Could not delete report temp file.");
    	e.printStackTrace();
    }
  }


  public static void downloadPDFReport(
          HttpServletRequest request,
          HttpServletResponse res,
          ActionForm form, ReportRequest pRepRequest) throws Exception {

          CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
          GenericReportResultViewVector reportResults = sForm.getReportResults();

          String fileName = sForm.getReportTypeCd();
          fileName += ".pdf";

          // Replace spaces so that the client application
          // will recognize the full file name and not just
          // the first work of the name specified.
          fileName = fileName.replaceAll(" ", "-");
          fileName = fileName.replaceAll("/", "-");//problem with IE6

          res.setContentType("application/pdf");

          String browser = (String)  request.getHeader("User-Agent");
          boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
          if (!isMSIE6){
        	  res.setHeader("extension", "pdf");
              res.setHeader("Content-disposition", "attachment; filename=" + fileName);
          }
          File tmpFile = File.createTempFile("report", ".pdf");
          OutputStream fout = new BufferedOutputStream(new FileOutputStream(tmpFile));
          ReportWritter.writePDFReportMulti(reportResults, fout,
                pRepRequest, request);
          res.setHeader("Content-Length", String.valueOf(tmpFile.length()));
          IOUtilities.copyStream(new FileInputStream(tmpFile), res.getOutputStream());
          res.flushBuffer();
          try{
            tmpFile.delete();
          }catch(Exception e){
            //non-fatal keep going.
            log.error("Could not delete report temp file.");
            e.printStackTrace();
          }
        }

// Functions needed for the Order Information Report.
// It has not been changed to use the generic reports infrastructure.
// durval 5/5/5
	public static ActionErrors exportOrderReport(HttpServletRequest request, HttpServletResponse res, CustAcctMgtReportForm pForm) throws Exception {

		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();

		String action = request.getParameter("action");
		String fileName = action + "-" + pForm.getReportTypeCd() + ".xls";

		try {
			res.setContentType("application/x-excel");
			res.setHeader("extension", "xls");
			res.setHeader("Content-disposition", "attachment; filename=" + fileName);

			WritableWorkbook workbook = Workbook.createWorkbook(res.getOutputStream());
			WritableSheet sheet = workbook.createSheet(pForm.getReportTypeCd(), 0);
			WritableFont times10font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);

			WritableCellFormat headerFormat = new WritableCellFormat(times10font);

			WritableCellFormat amtfmt = new WritableCellFormat(NumberFormats.ACCOUNTING_FLOAT);
			WritableCellFormat intfmt = new WritableCellFormat(NumberFormats.INTEGER);

			int colDate = 0, colStore = 1, colYtdActual = 2, colRank = 3, colNsf = 4, colBsc = 5, colRequested = 6, colRemainingBudget = 7, colApproved = 8, colCommitted = 9, colApprovalDate = 10;

			setHeader(sheet, colDate, "Order Date", headerFormat);
			setHeader(sheet, colStore, "Store", headerFormat);
			setHeader(sheet, colYtdActual, "YTD Actual", headerFormat);
			setHeader(sheet, colRank, "Rank", headerFormat);
			setHeader(sheet, colNsf, "NSF", headerFormat);
			setHeader(sheet, colBsc, "BSC", headerFormat);
			setHeader(sheet, colRequested, "Requested", headerFormat);
			setHeader(sheet, colRemainingBudget, "YTD Remaining Budget", headerFormat);
			setHeader(sheet, colApproved, "Approved", headerFormat);
			setHeader(sheet, colCommitted, "Committed", headerFormat);
			setHeader(sheet, colApprovalDate, "Date Approved", headerFormat);

			int idx = 0;
			ArrayList od = pForm.getOrderReport();
			for (int ctidx = 0; od != null && ctidx < od.size(); ctidx++) {

				UniversalDAO.dbrow dbr = (UniversalDAO.dbrow) od.get(ctidx);

				String colDateStr = dbr.getStringValue(colDate);

				colDateStr = ClwI18nUtil.formatDateInp(request, new Date(colDateStr));

				setCellNoNull(sheet, colDate, idx + 1, colDateStr, null);
				setCellNoNull(sheet, colStore, idx + 1, dbr.getStringValue(colStore), null);
				BigDecimal ytdact = new BigDecimal(dbr.getStringValue(colYtdActual));
				setCellNoNull(sheet, colYtdActual, idx + 1, ytdact, amtfmt);
				setCellNoNull(sheet, colRank, idx + 1, dbr.getStringValue(colRank), intfmt);
				setCellNoNull(sheet, colNsf, idx + 1, dbr.getStringValue(colNsf), null);
				setCellNoNull(sheet, colBsc, idx + 1, dbr.getStringValue(colBsc), null);

				BigDecimal reqamt = new BigDecimal(dbr.getStringValue(colRequested));
				setCellNoNull(sheet, colRequested, idx + 1, reqamt, amtfmt);

				BigDecimal remain = new BigDecimal(dbr.getStringValue(colRemainingBudget));
				setCellNoNull(sheet, colRemainingBudget, idx + 1, remain, amtfmt);

				BigDecimal comm = new BigDecimal(dbr.getColumn("COMMITTED").toString()), appamt = new BigDecimal(dbr.getColumn("APPROVED_AMT").toString());
				setCellNoNull(sheet, colApproved, idx + 1, appamt, amtfmt);
				setCellNoNull(sheet, colCommitted, idx + 1, comm, amtfmt);

				String appdate = "";
				if (comm.doubleValue() > 0) {
					appdate = dbr.getColumn("REVISED_DATE").toString();
					if (appdate == null || appdate.length() == 0) {
						// This order was "approved" at the time of purchase.
						appdate = dbr.getStringValue(colDate);
					}
					appdate = ClwI18nUtil.formatDateInp(request, new Date(appdate));
				}
				setCellNoNull(sheet, colApprovalDate, idx + 1, appdate, null);

				idx++;
			}

			workbook.write();
			workbook.close();
			res.flushBuffer();
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new Exception(exc.getMessage());
		}

		return ae;
	}

  public static ActionErrors returnSelected(HttpServletRequest request,
                                            ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    CustAcctMgtReportForm pForm = (CustAcctMgtReportForm) form;
    if (RefCodeNames.RETURN_LOCATE_TYPE_CD.SITE.equals(pForm.getReturnLocateTypeCd())) {
      ae = returnSelectedSites(request, pForm);
    } else if (RefCodeNames.RETURN_LOCATE_TYPE_CD.ACCOUNT.equals(pForm.getReturnLocateTypeCd())){
      ae = returnSelectedAccounts(request, pForm);
    } else if (RefCodeNames.RETURN_LOCATE_TYPE_CD.ITEM.equals(pForm.getReturnLocateTypeCd())){
      ae = returnSelectedItems(request, pForm);
    } else if (RefCodeNames.RETURN_LOCATE_TYPE_CD.DISTRIBUTOR.equals(pForm.getReturnLocateTypeCd())){
      ae = returnSelectedDistributors(request, pForm);
    }
    return ae;
  }

  public static ActionErrors returnSelectedSites(HttpServletRequest request,
                                                 CustAcctMgtReportForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    LocateStoreSiteForm locateStoreSiteForm = pForm.getLocateStoreSiteForm();
    SiteViewVector reqSiteVwV = locateStoreSiteForm.getSitesToReturn();
    SiteViewVector siteVwV = (SiteViewVector) session.getAttribute("Report.parameter.sites");
    SiteViewVector joinSiteVwV = null;
    if (reqSiteVwV != null) {
      reqSiteVwV.sort("name");
    }
    if (siteVwV != null) {
      joinSiteVwV = new SiteViewVector();

      SiteView wrkSiteVw = null;
      for (Iterator iter = siteVwV.iterator(),
                           iter1 = reqSiteVwV.iterator(); iter.hasNext(); ) {
        SiteView siteVw = (SiteView) iter.next();
        String siteName = siteVw.getName();
        while (iter1.hasNext() || wrkSiteVw != null) {
          if (wrkSiteVw == null) wrkSiteVw = (SiteView) iter1.next();
          String sn = wrkSiteVw.getName();
          int comp = sn.compareTo(siteName);
          if (comp == 0) {
            wrkSiteVw = null;
            break;
          }
          if (comp < 0) {
            joinSiteVwV.add(wrkSiteVw);
            wrkSiteVw = null;
            continue;
          }
          break;
        }
        joinSiteVwV.add(siteVw);
      }
    } else {
      joinSiteVwV = reqSiteVwV;
    }
    session.setAttribute("Report.parameter.sites", joinSiteVwV);
    return ae;
  }
  public static ActionErrors returnSelectedAccounts(HttpServletRequest request,
                                                 CustAcctMgtReportForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    LocateReportAccountForm locateReportAccountForm = pForm.getLocateReportAccountForm();
    AccountUIViewVector reqAccVwV = locateReportAccountForm.getAccountsToReturn();
    AccountUIViewVector accVwV = (AccountUIViewVector) session.getAttribute("Report.parameter.accounts");
    AccountUIViewVector joinAccVwV = null;
    if (reqAccVwV != null) {
      reqAccVwV.sort("name");
    }
    if (accVwV != null) {
      joinAccVwV = new AccountUIViewVector();

      AccountUIView wrkAccVw = null;
      for (Iterator iter = accVwV.iterator(),
                           iter1 = reqAccVwV.iterator(); iter.hasNext(); ) {
        AccountUIView accVw = (AccountUIView) iter.next();
        String accName = accVw.getBusEntity().getShortDesc();
        while (iter1.hasNext() || wrkAccVw != null) {
          if (wrkAccVw == null) wrkAccVw = (AccountUIView) iter1.next();
          String sn = wrkAccVw.getBusEntity().getShortDesc();
          int comp = sn.compareTo(accName);
          if (comp == 0) {
            wrkAccVw = null;
            break;
          }
          if (comp < 0) {
            joinAccVwV.add(wrkAccVw);
            wrkAccVw = null;
            continue;
          }
          break;
        }
        joinAccVwV.add(accVw);
      }
    } else {
      joinAccVwV = reqAccVwV;
    }
    session.setAttribute("Report.parameter.accounts", joinAccVwV);
    return ae;
  }

  public static ActionErrors returnSelectedItems(HttpServletRequest request,
                                                 CustAcctMgtReportForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    LocateReportItemForm locateReportItemForm = pForm.getLocateReportItemForm();
    ItemViewVector reqItemVwV = locateReportItemForm.getItemsToReturn();
    ItemViewVector itemVwV = (ItemViewVector) session.getAttribute("Report.parameter.items");
    ItemViewVector joinItemVwV = null;
    if (reqItemVwV != null) {
      reqItemVwV.sort("name");
    }
    if (itemVwV != null) {
      joinItemVwV = new ItemViewVector();

      ItemView wrkItemVw = null;
      for (Iterator iter = itemVwV.iterator(),
                           iter1 = reqItemVwV.iterator(); iter.hasNext(); ) {
        ItemView itemVw = (ItemView) iter.next();
        String itemName = itemVw.getName();
        while (iter1.hasNext() || wrkItemVw != null) {
          if (wrkItemVw == null) wrkItemVw = (ItemView) iter1.next();
          String sn = wrkItemVw.getName();
          int comp = sn.compareTo(itemName);
          if (comp == 0) {
            wrkItemVw = null;
            break;
          }
          if (comp < 0) {
            joinItemVwV.add(wrkItemVw);
            wrkItemVw = null;
            continue;
          }
          break;
        }
        joinItemVwV.add(itemVw);
      }
    } else {
      joinItemVwV = reqItemVwV;
    }
    session.setAttribute("Report.parameter.items", joinItemVwV);
    return ae;
  }

    public static ActionErrors returnSelectedDistributors(HttpServletRequest request,
                                                   CustAcctMgtReportForm pForm) throws Exception {
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();

      LocateReportDistributorForm locateReportDistributorForm = pForm.getLocateReportDistributorForm();
      DistributorDataVector reqDistDV = locateReportDistributorForm.getDistributorsToReturn();
      DistributorDataVector distDV = (DistributorDataVector) session.getAttribute("Report.parameter.distributors");
      DistributorDataVector joinDistDV = null;
      if (distDV != null) {
        joinDistDV = new DistributorDataVector();

        DistributorData wrkDistD = null;
        for (Iterator iter = distDV.iterator(),
                             iter1 = reqDistDV.iterator(); iter.hasNext(); ) {
          DistributorData distD = (DistributorData) iter.next();
          String distName = distD.getBusEntity().getShortDesc();
          while (iter1.hasNext() || wrkDistD != null) {
            if (wrkDistD == null) wrkDistD = (DistributorData) iter1.next();
            String dn = wrkDistD.getBusEntity().getShortDesc();
            int comp = dn.compareTo(distName);
            if (comp == 0) {
              wrkDistD = null;
              break;
            }
            if (comp < 0) {
              joinDistDV.add(wrkDistD);
              wrkDistD = null;
              continue;
            }
            break;
          }
          joinDistDV.add(distD);
        }
      } else {
        joinDistDV = reqDistDV;
      }
      session.setAttribute("Report.parameter.distributors", joinDistDV);
      return ae;
    }

  public static ActionErrors clearSites(HttpServletRequest request,
                                        ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    session.removeAttribute("Report.parameter.sites");
    return ae;
  }
  public static ActionErrors clearAccounts(HttpServletRequest request,
                                        ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    session.removeAttribute("Report.parameter.accounts");
    return ae;
  }

  public static ActionErrors clearItems(HttpServletRequest request,
                                        ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    session.removeAttribute("Report.parameter.items");
    return ae;
  }

  public static ActionErrors clearDistributors(HttpServletRequest request,
                                          ActionForm form) throws Exception {

      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      session.removeAttribute("Report.parameter.distributors");
      return ae;
    }

    public static int getReportMaxRowCount(ActionForm form) {
        int maxRowCount = 0;
        CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
        if (sForm.getReportResults() != null) {
            for (Object o : sForm.getReportResults()) {
                GenericReportResultView genericReport = (GenericReportResultView) o;
                if (genericReport.getTable().size() > maxRowCount) {
                    maxRowCount = genericReport.getTable().size();
                }
            }
        }
        return maxRowCount;
    }

    public static Element generateReportXmlResponse(HttpServletRequest request, ActionErrors ae, ActionForm pForm) {

        CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) pForm;
        String command = request.getParameter("command");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        //Document doc = docBuilder.newDocument();
        Document doc = docBuilder.getDOMImplementation().createDocument("", "response", null);
        Element root =  doc.getDocumentElement();
        root.setAttribute("command", command);
        Element body = doc.createElement("body");

        Element errors = doc.createElement("errors");
        Iterator it = ae.get();
        while (it.hasNext()) {

            ActionError oErr = (ActionError) it.next();

            Element error = doc.createElement("error");
            error.setAttribute("key", oErr.getKey());

            for (int j = 0; j < oErr.getValues().length; j++) {

                Object oVal = oErr.getValues()[j];

                Element errorstring =  doc.createElement("errorstring");
                errorstring.appendChild(doc.createTextNode(oVal.toString()));
                error.appendChild(errorstring);
            }
            errors.appendChild(error);
        }

        Element maxrowcount = doc.createElement("reportmaxrowcount");
        int rowCount = sForm.getReportMaxRowCount();
        maxrowcount.appendChild(doc.createTextNode(String.valueOf(rowCount)));
        root.appendChild(body);

        body.appendChild(errors);
        body.appendChild(maxrowcount);
        root.appendChild(body);

        return root;

    }

    public static void getReportState(HttpServletRequest request, HttpServletResponse response, String sessionAttrName, String errorMessageKey) {
       final String unLocked = "0";
       String errorMess = null;
       HttpSession session = request.getSession();

       try {
         synchronized (session) {
           String lockValue = (String) session.getAttribute(sessionAttrName);
           if (lockValue != null && !lockValue.equals(unLocked)) {
             errorMess = ClwI18nUtil.getMessage(request, errorMessageKey,
                                                new String[] {lockValue});

           }
         }

         Element rootEl = createXmlResponse(request, errorMess);
         response.setContentType("application/xml");
         response.setHeader("Cache-Control", "no-cache");
         response.getWriter().write(rootEl.toString());
       }
       catch (IOException ex) {
         ex.printStackTrace();
       }
     }
     private static Element createXmlResponse(HttpServletRequest request, String errMessage){

         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setNamespaceAware(true);
         DocumentBuilder docBuilder = null;

         try {
             docBuilder = factory.newDocumentBuilder();
         } catch (ParserConfigurationException ex) {


         }
         Document doc = docBuilder.newDocument();

         Element root = doc.createElement("State");
         String key = ((errMessage != null) ? "@in progress@" : "");
         root.setAttribute("Error", key+Utility.strNN(errMessage));
         return root;
    }
    //method convertDateToAnotherFormat() converts Date from one Date Format to another Date Format
    private static String convertDateToAnotherFormat(HttpServletRequest pRequest, String pDate,
                                                     String dateType, ActionErrors errors,
                                                     String pInDateFmt, String pOutDateFmt) {
     	SimpleDateFormat inFmt = new SimpleDateFormat(pInDateFmt);
     	SimpleDateFormat outFmt = new SimpleDateFormat(pOutDateFmt);
     	String out = "";
     	try {
     	     out = outFmt.format(inFmt.parse(pDate));
     	} catch(Exception e) {
              log.info("Error converting " + pDate + " from " + pInDateFmt + " Date Format to " + pOutDateFmt + " Date Format.");
              //String mesg = "Error converting " + pDate + " from " + pInDateFmt + " Date Format to " + pOutDateFmt + " Date Format.";
              //String mesg = "Date " + pDate + " is wrong.";

              String errorMess = e.getMessage();
              errorMess = "Error in the format of the " + dateType + ". The format should be: " + ClwI18nUtil.getUIDateFormat(pRequest);
              errors.add("Error",
                    new ActionError("error.simpleGenericError",
                                    errorMess));
     	}
     	return out;
    }

    private static void checkInputDate(HttpServletRequest request, ActionErrors ae,
                                       String dateFMT, String startDate, String endDate,
                                       boolean optional) {
    	
    	//STJ-4528 : (New UI) Modified to set empty string if the dates are set to user's locale.
    	//NOTE: Calendar controls in New UI allows user's locale as default date.
    	String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
		if(Utility.isSet(defaultDateFormat) && (Utility.isSet(startDate) || Utility.isSet(endDate))) {
			if(defaultDateFormat.equalsIgnoreCase(startDate)) {
				startDate="";
			}
			if(defaultDateFormat.equalsIgnoreCase(endDate)) {
				endDate="";
			}
		}
		
        if (optional && !Utility.isSet(startDate) && !Utility.isSet(endDate)) {
            return;
        }
         DateFormat df = new SimpleDateFormat(dateFMT);
         df.setLenient(false);
         Date dateStartDate = null;
         Date dateEndDate = null;

         if (! optional && !Utility.isSet(startDate)) {
             try  {
                 dateStartDate = df.parse(startDate);//, parsePosition);
                log.info("dateStartDate = " + ((dateStartDate != null) ? dateStartDate.toString(): "NULL"));
                // Now check the ParsePosition index
                log.info("startDate.length() = " + startDate.length());
             } catch (Exception e) {

                 log.info("Begin Date format exception");
                 e.printStackTrace();
                 Object[] param = new Object[1];
                 param[0] = ClwI18nUtil.getMessage(request,"report.text.BeginDate",null);
                 String errorMess = ClwI18nUtil.getMessage(request,"error.badDateFormat",param);
                 ae.add("Error",
                        new ActionError("error.simpleGenericError",errorMess));
             }
         }
         if (! optional && !Utility.isSet(endDate)) {
             try   {
                 dateEndDate = df.parse(endDate);//, parsePosition);
                 log.info("dateEndDate = " + ((dateEndDate != null) ? dateEndDate.toString(): "NULL"));
                 // Now check the ParsePosition index
             } catch (Exception e) {
                  log.info("End Date format exception");
                  e.printStackTrace();
                  Object[] param = new Object[1];
                  param[0] = ClwI18nUtil.getMessage(request,"report.text.EndDate",null);
                  String errorMess = ClwI18nUtil.getMessage(request,"error.badDateFormat",param);
                  ae.add("Error",
                        new ActionError("error.simpleGenericError",errorMess));
             }
         }

         if (dateStartDate != null && dateEndDate != null && dateEndDate.compareTo(dateStartDate) < 0) {
                String errorMess = ClwI18nUtil.getMessage(request,
                        "shop.errors.endDateBeforeStartDate", new String[] {
                                endDate, startDate });
                ae.add("Error", new ActionError("error.simpleGenericError",
                        errorMess));
              }
          }
    
    public static void downloadInvoicePDF(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		GenericReportResultViewVector results, GenericReportControlViewVector controls) throws Exception {
    	String supressNotes = "No";
    	for (int ii = 0; ii < controls.size(); ii++) {
    		GenericReportControlView control = (GenericReportControlView) controls.get(ii);
    		String name = control.getName();
    		if ("SUPPRESS_NOTES".equals(name) || "SUPPRESS_NOTES_OPT".equals(name)) {
    			supressNotes = control.getValue();
    		}
    	}
    	java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
    	APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
    	Order orderEjb = factory.getOrderAPI();
    	Distributor distEjb = factory.getDistributorAPI();
    	PurchaseOrder purchaseOrderEjb = factory.getPurchaseOrderAPI();
    	GenericReportResultView result = (GenericReportResultView) results.get(0);
    	PdfInvoiceDist pdfInv = new PdfInvoiceDist();
    	ArrayList invoiceToRender = new ArrayList();
    	HashMap distMap = new HashMap();
    	boolean showNotes = !"Yes".equals(supressNotes);
    	for (Iterator it = result.getTable().iterator(); it.hasNext();){
    		PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) it.next();
    		OrderItemDescDataVector itms = purchaseOrderEjb.getDistriutorInvoiceItemDetailLightWeight(po.getInvoiceDist().
    				getInvoiceDistId(), 0);
    		int orderId = 0;
    		if (po.getOrderData() != null) {
    			orderId = po.getOrderData().getOrderId();
    		}
    		Integer distKey = new Integer(po.getInvoiceDist().getBusEntityId());
    		DistributorData dist;
    		if (po.getInvoiceDist().getBusEntityId() == 0) {
    			dist = null;
    		} else {
    			if (distMap.containsKey(distKey)) {
    				dist = (DistributorData) distMap.get(distKey);
    			} else {
    				dist = distEjb.getDistributor(po.getInvoiceDist().getBusEntityId());
    				distMap.put(distKey, dist);
    			}
    		}
    		OrderPropertyDataVector notes = null;
    		if (showNotes) {
    			notes = StoreVendorInvoiceLogic.
    			getInvoiceDistNotesForDisplay(po.getInvoiceDist().getInvoiceDistId(), orderId, orderEjb);
    		}
    		invoiceToRender.add(pdfInv.createPdfInvoiceDistRequest(po, itms, notes, dist));
    	}

    	pdfInv.constructPdf(invoiceToRender, out);
    	Date repDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String repDateS = sdf.format(repDate);
        String fileName = "Invoice Listing"+repDateS+".pdf";
        
    	response.setContentType("application/pdf");
    	response.setHeader("extension", "pdf");
    	response.setHeader("Content-disposition", "attachment; filename="+fileName);    	
    	response.setContentLength(out.size());
    	out.writeTo(response.getOutputStream());
    	response.flushBuffer();
    	response.getOutputStream().close();
    }

}
