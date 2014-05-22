package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.UniversalDAO;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.pdf.PdfDeliverySchedule;
import com.cleanwise.view.utils.ReportWritter;
import com.cleanwise.view.forms.LocateStoreSiteForm;
import com.cleanwise.view.forms.ServiceProviderMgtReportForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.i18n.ClwI18nUtil;


import java.math.*;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Excel classes.
import jxl.*;

import jxl.write.*;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import org.apache.log4j.Logger;


import java.text.DateFormat;
import java.util.Date;
import java.text.ParsePosition;



/**
 * <code>ServiceProviderMgtReportLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author Liang
 */
public class ServiceProviderMgtReportLogic {
  private static final String CUSTOMER_REPORT_CATEGORY = "Customer";

  public static final int MAX_HTML_ROWS = 3000;

  private static final Logger log = Logger.getLogger(ServiceProviderMgtReportLogic.class); // SVC
  //private static SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy"); // SVC

    /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void init(HttpServletRequest request, ActionForm form) throws Exception {
    initConstantList(request);
    //ProfilingMgrLogic.getProfilesForSite(request);
    ServiceProviderMgtReportForm sForm = new ServiceProviderMgtReportForm();
    (request.getSession()).setAttribute("SERIVCE_PROVIDER_REPORT_FORM", sForm);
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

        //ListService listServiceEjb = factory.getListServiceAPI();
        // the current user info
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        //boolean hasAccountReports = appUser.hasAccountReports();

        //add the reports, need to add some of the custom ones that are not in the generic reporting framework
        GenericReportViewVector reports = (GenericReportViewVector) appUser.getAuthorizedRuntimeReports().clone();

        //Site schedule
        //SiteData currentSiteD = appUser.getSite();
        //if (null != currentSiteD &&
        //        null != currentSiteD.getNextDeliveryDate()) {
        //    GenericReportView grd = GenericReportView.createValue();
        //    //XXX this should not be hardcoded, there needs to be a way of getting these reports
        //    //into the generic report framework
        //    grd.setReportName(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE);
        //    grd.setReportCategory(CUSTOMER_REPORT_CATEGORY);
        //    grd.setLongDesc("Displays order cut-off dates and delivery dates (if applicable).");
        //    reports.add(grd);
        //}

        //This is a littly odd, but until there is a standard way of creating custom rendered
        //"reports" and controling access to them we use both the group access stuff and the
        //report listings.
        //if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.REPORT_INVOICE_LISTING)) {
        //    GenericReportView grd = GenericReportView.createValue();
        //    //XXX this should not be hardcoded, there needs to be a way of getting these reports
        //    //into the generic report framework
        //    grd.setReportName(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.INVOICE_LISTING);
        //    grd.setReportCategory(CUSTOMER_REPORT_CATEGORY);
        //    reports.add(grd);
        //}

        //Budget (JCI) reports
        //if (hasAccountReports == true) {
        //    RefCdDataVector typev = listServiceEjb.getRefCodesCollection("ACCOUNT_REPORT_TYPE_CD");
        //    if (typev != null) {
        //        Iterator it = typev.iterator();
        //       while (it.hasNext()) {
        //            RefCdData ref = (RefCdData) it.next();
        //            GenericReportView grd = GenericReportView.createValue();
        //            grd.setReportName(ref.getValue());
        //            //XXX this should not be hardcoded, there needs to be a way of getting these reports
        //            //into the generic report framework
        //            grd.setReportCategory(CUSTOMER_REPORT_CATEGORY + " Account Reports");
        //            if (RefCodeNames.ACCOUNT_REPORT_TYPE_CD.ORDER_INFORMATION_REPORT.equals(ref.getValue())) {
        //                grd.setLongDesc("Display ordering activity for the current budget period.");
        //            }
        //            reports.add(grd);
        //        }
        //    }
        //}

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

        ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;

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
        }
        else {
            //Generic report
            Report reportEjb = factory.getReportAPI();
            GenericReportControlViewVector parameters = reportEjb.getGenericReportControls(null, reportTypeCd);
            initControls(request,parameters);
            sForm.setReportControls(parameters);

        }

        sForm.setReportTypeDesc(reportTypeDesc);
        sForm.setMultipleLocSelectFlag(multipleLocSelectFlag);

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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;

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

          ProductDataVector productDV = catalogInfoEjb.getTopCatalogProducts(catalogD.getCatalogId(), 0,  SessionTool.getCategoryToCostCenterView(session, 0 , catalogD.getCatalogId()));

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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
    String reportTypeCd = sForm.getReportTypeCd();
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
      for (int ii = 0; ii < controls.size(); ii++) {
        GenericReportControlView control = (GenericReportControlView) controls.get(ii);
        String name = control.getName();
        if ("CUSTOMER".equals(name)) {
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
        } else {
          params.put(name, control.getValue());
        }
      }
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
      // add the date format in the event any user specified
      // dates have to be parsed.  This format is locale specific
      //
      if (pRepRequest != null) {
        params.put("DATE_FMT", pRepRequest.mDateFormat);
      }

      /*** new piece of code by SVC: check passed report dates ***/

      log.info("***********SVC: params = " + params);

      String stringDateFMT = "";
      String stringStartDate = "";
      String stringEndDate = "";

      if (params.containsKey("DATE_FMT")) {
          stringDateFMT = (String) params.get("DATE_FMT");
      }
      if (params.containsKey("BEG_DATE")) {
          stringStartDate= (String) params.get("BEG_DATE");
      }
      if (params.containsKey("END_DATE")) {
          stringEndDate= (String) params.get("END_DATE");
      }
      if (params.containsKey("DATE_FMT") && params.containsKey("BEG_DATE") && params.containsKey("END_DATE"))
      {
         DateFormat df = new SimpleDateFormat(stringDateFMT);
         df.setLenient(false);
         ParsePosition parsePosition;
         parsePosition = new ParsePosition(0);

         try

         {
         Date dateStartDate = df.parse(stringStartDate, parsePosition);
         log.info("dateStartDate = " + dateStartDate.toString());
         // Now check the ParsePosition index
         //log.info("parsePosition.getIndex() = " + parsePosition.getIndex());
         log.info("stringStartDate.length() = " + stringStartDate.length());
         /*
         if(stringDateFMT.trim().length() == stringStartDate.trim().length()){
             // Parsing successful
         }
         else {
              // Error occurred
        	  log.info("Begin Date format is wrong");
              String errorMess = "Error in the format of the Begin Date. The format should be: " + stringDateFMT;
              ae.add("Error",
                     new ActionError("error.simpleGenericError",
                                     errorMess));
              return ae;
         }
         */


         //} catch (ParseException e)
         } catch (Exception e)

         {
    	 log.info("Begin Date format exception");
         e.printStackTrace();
         String errorMess = e.getMessage();
         //errorMess = "Error in the format of the Begin Date. The format should be: " + stringDateFMT;
         errorMess = "Error in the format of the Begin Date. The format should be: " + ClwI18nUtil.getUIDateFormat(request);
         ae.add("Error",
                new ActionError("error.simpleGenericError",
                                errorMess));
         return ae;

         }

         parsePosition = new ParsePosition(0);

         try

         {

         Date dateEndDate = df.parse(stringEndDate, parsePosition);
         //log.info("dateEndDate = " + df.format(dateEndDate));
         log.info("dateEndDate = " + dateEndDate.toString());
         // Now check the ParsePosition index
        /*
         if(stringDateFMT.trim().length() == stringEndDate.trim().length()){
             // Parsing successful
         }
         else {
              // Error occurred
        	  log.info("End Date format is wrong");
              String errorMess = "Error in the format of the End Date. The format should be: " + stringDateFMT;
              ae.add("Error",
                     new ActionError("error.simpleGenericError",
                                     errorMess));
              return ae;
          }
          */
          //} catch (ParseException e)
          } catch (Exception e)

          {
    	  log.info("End Date format exception");
          e.printStackTrace();
          String errorMess = e.getMessage();
          errorMess = "Error in the format of the End Date. The format should be: " + ClwI18nUtil.getUIDateFormat(request);
          ae.add("Error",
                new ActionError("error.simpleGenericError",
                                errorMess));
          return ae;

          }
      }
      /**********************************************************/
      if (params.containsKey("BUDGET_PERIODS_INFO")) {
        params.put("BUDGET_PERIODS_INFO", sForm.getSelectedPeriods());
      }

      params.put("USER_ID", String.valueOf(appUser.getUser().getUserId()));
      params.put("USER_TYPE", appUser.getUser().getUserTypeCd());
      params.put("USER_WO_ADMIN", String.valueOf(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER) ||
                                                 appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)));

      GenericReportResultViewVector results = new GenericReportResultViewVector();
      sForm.setReportResults(results);
      sForm.setGenericReportPageNum(results.size() - 1);
      try {

        results = reportEjb.processGenericReportMulti(null, reportTypeCd, params);

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
      log.info("CustAcctMgtReportLogic RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR results.size: " + results.size());
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();

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
        topProductV = catalogInfoEjb.getTopCatalogProducts(catalogId, 0, SessionTool.getCategoryToCostCenterView(session, 0 , catalogId));
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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;

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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();

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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();

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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;

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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
    String userId = (String) session.getAttribute(Constants.USER_ID);
    String userType = (String) session.getAttribute(Constants.USER_TYPE);

    if (null == userId)
      userId = new String("");

    if (null == userType)
      userType = new String("");

    // search the orders in the current date range
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    IdVector storeIds = appUser.getUserStoreAsIdVector();
    OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData.createValue();
    searchCriteria.setOrderDateRangeBegin(sForm.getBeginDateS());
    searchCriteria.setOrderDateRangeEnd(sForm.getEndDateS());
    searchCriteria.setSiteId(String.valueOf(currentSiteD.getBusEntity().getBusEntityId()));
    searchCriteria.setUserId(userId);
    searchCriteria.setUserTypeCd(userType);

    OrderStatusDescDataVector orderDescV = new OrderStatusDescDataVector();

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
    ServiceProviderMgtReportLogic m = new ServiceProviderMgtReportLogic();
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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
    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

    ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

    ReportWritter.writeExcelReportMulti(reportResults,
                                        res.getOutputStream(),
                                        pRepRequest);
    res.flushBuffer();
  }


  public static void downloadPDFReport(
          HttpServletRequest request,
          HttpServletResponse res,
          ActionForm form, ReportRequest pRepRequest) throws Exception {

          ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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
          ReportWritter.writePDFReportMulti(reportResults, res.getOutputStream(),
                pRepRequest, request);
          res.flushBuffer();
        }

// Functions needed for the Order Information Report.
// It has not been changed to use the generic reports infrastructure.
// durval 5/5/5
  public static ActionErrors exportOrderReport(HttpServletRequest request,
                                               HttpServletResponse res,
                                               ServiceProviderMgtReportForm pForm) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    String action = request.getParameter("action");
    String fileName = action + "-" + pForm.getReportTypeCd() + ".xls";

    try {
      res.setContentType("application/x-excel");
      res.setHeader("extension", "xls");
      res.setHeader("Content-disposition",
                    "attachment; filename=" + fileName);

      WritableWorkbook workbook = Workbook.createWorkbook(res.getOutputStream());
      WritableSheet sheet = workbook.createSheet(pForm.getReportTypeCd(), 0);
      WritableFont times10font = new WritableFont(WritableFont.TIMES, 10,
                                                  WritableFont.BOLD,
                                                  false);
      WritableCellFormat headerFormat = new WritableCellFormat(
        times10font);
      WritableCellFormat amtfmt = new
                                  WritableCellFormat(NumberFormats.ACCOUNTING_FLOAT);
      WritableCellFormat intfmt = new
                                  WritableCellFormat(NumberFormats.INTEGER);

      int colDate = 0, colStore = 1, colYtdActual = 2,
        colRank = 3, colNsf = 4, colBsc = 5, colRequested = 6,
        colRemainingBudget = 7, colApproved = 8,
                                              colCommitted = 9, colApprovalDate = 10;

      setHeader(sheet, colDate, "Order Date", headerFormat);
      setHeader(sheet, colStore, "Store", headerFormat);
      setHeader(sheet, colYtdActual, "YTD Actual", headerFormat);
      setHeader(sheet, colRank, "Rank", headerFormat);
      setHeader(sheet, colNsf, "NSF", headerFormat);
      setHeader(sheet, colBsc, "BSC", headerFormat);
      setHeader(sheet, colRequested, "Requested",
                headerFormat);
      setHeader(sheet, colRemainingBudget, "YTD Remaining Budget", headerFormat);
      setHeader(sheet, colApproved, "Approved",
                headerFormat);
      setHeader(sheet, colCommitted, "Committed",
                headerFormat);
      setHeader(sheet, colApprovalDate, "Date Approved", headerFormat);

      int idx = 0;
      ArrayList od = pForm.getOrderReport();
      for (int ctidx = 0;
                       od != null && ctidx < od.size();
                       ctidx++) {

        UniversalDAO.dbrow dbr = (UniversalDAO.dbrow) od.get(ctidx);

        setCellNoNull(sheet, colDate, idx + 1, dbr.getStringValue(colDate), null);
        setCellNoNull(sheet, colStore, idx + 1, dbr.getStringValue(colStore),
                      null);
        BigDecimal ytdact = new BigDecimal(dbr.getStringValue(colYtdActual));
        setCellNoNull(sheet, colYtdActual, idx + 1, ytdact, amtfmt);
        setCellNoNull(sheet, colRank, idx + 1,
                      dbr.getStringValue(colRank), intfmt);
        setCellNoNull(sheet, colNsf, idx + 1,
                      dbr.getStringValue(colNsf), null);
        setCellNoNull(sheet, colBsc, idx + 1,
                      dbr.getStringValue(colBsc), null);

        BigDecimal reqamt = new BigDecimal(dbr.getStringValue(colRequested));
        setCellNoNull(sheet, colRequested, idx + 1, reqamt, amtfmt);

        BigDecimal remain = new BigDecimal(dbr.getStringValue(colRemainingBudget));
        setCellNoNull(sheet, colRemainingBudget, idx + 1, remain, amtfmt);

        BigDecimal comm =
          new BigDecimal(dbr.getColumn("COMMITTED").toString()),
          appamt =
            new BigDecimal(dbr.getColumn("APPROVED_AMT").toString())
            ;
        setCellNoNull(sheet, colApproved, idx + 1, appamt, amtfmt);
        setCellNoNull(sheet, colCommitted, idx + 1, comm, amtfmt);

        String appdate = "";
        if (comm.doubleValue() > 0) {
          appdate = dbr.getColumn("REVISED_DATE").toString();
          if (appdate == null || appdate.length() == 0) {
            // This order was "approved" at the time of purchase.
            appdate = dbr.getStringValue(colDate);
          }

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
    ServiceProviderMgtReportForm pForm = (ServiceProviderMgtReportForm) form;
    if (RefCodeNames.RETURN_LOCATE_TYPE_CD.SITE.equals(pForm.getReturnLocateTypeCd())) {
      ae = returnSelectedSites(request, pForm);
    }
    return ae;
  }

  public static ActionErrors returnSelectedSites(HttpServletRequest request,
                                                 ServiceProviderMgtReportForm pForm) throws Exception {
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

  public static ActionErrors clearSites(HttpServletRequest request,
                                        ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    session.removeAttribute("Report.parameter.sites");
    return ae;
  }

    public static int getReportMaxRowCount(ActionForm form) {
        int maxRowCount = 0;
        ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) form;
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

        ServiceProviderMgtReportForm sForm = (ServiceProviderMgtReportForm) pForm;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "response", null);
        Element root = doc.getDocumentElement();
        Element body = doc.createElement("body");

        Element errors = doc.createElement("errors");
        Iterator it = ae.get();
        while (it.hasNext()) {

            ActionError oErr = (ActionError) it.next();

            Element error = doc.createElement("error");
            error.setAttribute("key", oErr.getKey());

            for (int j = 0; j < oErr.getValues().length; j++) {

                Object oVal = oErr.getValues()[j];

                Element errorstring = doc.createElement("errorstring");
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

}
