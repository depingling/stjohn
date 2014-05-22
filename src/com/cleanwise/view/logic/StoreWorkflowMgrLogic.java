
package com.cleanwise.view.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.FreightHandlerViewVector;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.GroupSearchCriteriaView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.WorkflowAssocData;
import com.cleanwise.service.api.value.WorkflowAssocDataVector;
import com.cleanwise.service.api.value.WorkflowData;
import com.cleanwise.service.api.value.WorkflowDataVector;
import com.cleanwise.service.api.value.WorkflowDescData;
import com.cleanwise.service.api.value.WorkflowDescDataVector;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
import com.cleanwise.service.api.value.WorkflowRuleJoinView;
import com.cleanwise.service.api.value.WorkflowRuleJoinViewVector;
import com.cleanwise.view.forms.StoreWorkflowDetailForm;
import com.cleanwise.view.forms.StoreWorkflowMgrSearchForm;
import com.cleanwise.view.forms.StoreWorkflowSitesForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;

/**
 *
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 16:08:15
 *
 */
public class StoreWorkflowMgrLogic  {
	
	private static final Logger log = Logger.getLogger(StoreWorkflowMgrLogic.class);

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors search(HttpServletRequest request,
                              ActionForm form)
            throws Exception {
    	
        ActionErrors ae=new ActionErrors();

        StoreWorkflowMgrSearchForm sForm = (StoreWorkflowMgrSearchForm) form;
        APIAccess factory = new APIAccess();
        Workflow wBean = factory.getWorkflowAPI();
        HttpSession session = request.getSession();
        int aid = 0;
        String s = (String) session.getAttribute("Account.id");
        if (null != s)
        {
            aid = Integer.parseInt(s);
        }

        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        WorkflowDataVector dv = new WorkflowDataVector();
        if(fieldValue.length()==0)
            StoreWorkflowMgrLogic.listAll(request,form);
        else  {
            if (fieldSearchType.equals("id"))
            {

                Integer id = null;
                try {
                	id = new Integer(fieldValue);
                }
                catch (NumberFormatException nfe) {
                    ae.add("error",new ActionError("error.simpleGenericError","Invalid workflow id was specified."));
                    return ae;
                }
                WorkflowData w = null;
                try {
                    w = wBean.getWorkflow(id.intValue());
                } catch (Exception e) {
                }
                if (null != w)
                {
                    dv.add(w);
                }

            }
            else if (fieldSearchType.equals("nameBegins"))
            {
                dv = wBean.getWorkflowCollectionByName(fieldValue, aid,
                        Workflow.BEGINS_WITH_IGNORE_CASE);
            }
            else if (fieldSearchType.equals("nameContains"))
            {
                dv = wBean.getWorkflowCollectionByName(fieldValue, aid,
                        Workflow.CONTAINS_IGNORE_CASE);
            }
            else
            {
                dv = wBean.getWorkflowCollectionByEntity(aid);
            }
            session.setAttribute("Workflow.found.vector", dv);

        }
        return ae;
    }




    public static void setupAccountInfo(HttpServletRequest request,
                                        int pAccountId)
            throws Exception {
        request.getSession().setAttribute
                ("Account.id", String.valueOf(pAccountId));
        request.getSession().setAttribute("Account.name", "---");
        request.getSession().setAttribute("Store.id", "0");
        request.getSession().setAttribute("Store.name", "---");
        try
        {
            // Get the Account Name
            APIAccess factory = new APIAccess();
            Account acctBean = factory.getAccountAPI();
            AccountData ad = acctBean.getAccount(pAccountId, 0);
            request.getSession().setAttribute
                    ("Account.name", ad.getBusEntity().getShortDesc());

            // Get the Store Name and Id.
            request.getSession().setAttribute
                    ("Store.id",
                            String.valueOf(ad.getStoreAssoc().getBusEntity2Id())
                    );

            Store sBean = factory.getStoreAPI();
            StoreData sd = sBean.getStore
                    (ad.getStoreAssoc().getBusEntity2Id());
            request.getSession().setAttribute
                    ("Store.name", sd.getBusEntity().getShortDesc());
        }
        catch (Exception e)
        {
        }
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initFormVectors(HttpServletRequest request)
            throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();

        // Set up the workflow type list.
        if (null == session.getAttribute("Workflow.type.vector"))
        {
            RefCdDataVector v =
                    lsvc.getRefCodesCollection("WORKFLOW_TYPE_CD");
            session.setAttribute("Workflow.type.vector", v);
        }

        // Set up the status list.
        if (null == session.getAttribute("Workflow.status.vector"))
        {
            RefCdDataVector v =
                    lsvc.getRefCodesCollection("WORKFLOW_STATUS_CD");
            session.setAttribute("Workflow.status.vector", v);
        }

        //if (null != session.getAttribute("STORE_WORKFLOW_DETAIL_FORM"))
        {
            // Reset the detail information.
            session.setAttribute("STORE_WORKFLOW_DETAIL_FORM",
                    new StoreWorkflowDetailForm());
        }
        session.removeAttribute("Workflow.rulesjoin.vector");
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void listAll(HttpServletRequest request,
                               ActionForm form)
            throws Exception {

        APIAccess factory = new APIAccess();
        Workflow wkfl = factory.getWorkflowAPI();
        HttpSession session = request.getSession();
        int aid = 0;

        String s = (String) session.getAttribute("Account.id");
        if (null != s)
        {
            aid = Integer.parseInt(s);

            session.setAttribute
                    ("Workflow.found.vector",
                            wkfl.getWorkflowCollectionByEntity(aid)
                    );
        }
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void locateListAll(HttpServletRequest request,
                                     ActionForm form)
            throws Exception {

        HttpSession session = request.getSession();
        StoreWorkflowMgrSearchForm sForm = (StoreWorkflowMgrSearchForm) form;

        String storeIdS = request.getParameter("storeid");
        int storeId = 0;
        if (null == storeIdS ||  "".equals(storeIdS))
        {
            if ("store".equals(sForm.getSearchBusEntityType())) {
                storeIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("store");
            sForm.setBusEntityIdS(storeIdS);
        }
        if (null != storeIdS && ! "".equals(storeIdS))
        {
            try
            {
                storeId = Integer.parseInt(storeIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse storeIdS (" + storeIdS + ")");
                storeId = 0;
            }
        }

        String accountIdS = request.getParameter("accountid");
        int accountId = 0;
        if (null == accountIdS ||  "".equals(accountIdS))
        {
            if ("account".equals(sForm.getSearchBusEntityType())) {
                accountIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("account");
            sForm.setBusEntityIdS(accountIdS);
        }
        if (null != accountIdS && ! "".equals(accountIdS))
        {
            try
            {
                accountId = Integer.parseInt(accountIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse accountIdS (" + accountIdS + ")");
                accountId = 0;
            }
        }

        int busEntityId = storeId;
        if (0 == busEntityId)
        {
            busEntityId = accountId;
        }

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }
        Workflow wkfl = factory.getWorkflowAPI();

        sForm.setWorkflowDescList(wkfl.getWorkflowDescCollectionByEntity(busEntityId, false));

    }
    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void locateByBusEntity(HttpServletRequest request,
                                         ActionForm form)
            throws Exception {

        HttpSession session = request.getSession();
        StoreWorkflowMgrSearchForm sForm = (StoreWorkflowMgrSearchForm) form;

        String storeIdS = request.getParameter("storeid");
        int storeId = 0;
        if (null == storeIdS ||  "".equals(storeIdS))
        {
            if("store".equals(sForm.getSearchBusEntityType())) {
                storeIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("store");
            sForm.setBusEntityIdS(storeIdS);
        }
        if (null != storeIdS && ! "".equals(storeIdS))
        {
            try
            {
                storeId = Integer.parseInt(storeIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse storeIdS (" + storeIdS + ")");
                storeId = 0;
            }
        }

        String accountIdS = request.getParameter("accountid");
        int accountId = 0;
        if (null == accountIdS ||  "".equals(accountIdS))
        {
            if("account".equals(sForm.getSearchBusEntityType())) {
                accountIdS = sForm.getBusEntityIdS();
            }
        }
        else
        {
            sForm.setSearchBusEntityType("account");
            sForm.setBusEntityIdS(accountIdS);
        }
        if (null != accountIdS && ! "".equals(accountIdS))
        {
            try
            {
                accountId = Integer.parseInt(accountIdS);
            }
            catch(Exception e)
            {
                log.info("can't parse accountIdS (" + accountIdS + ")");
                accountId = 0;
            }
        }


        int busEntityId = storeId;
        if (0 == busEntityId)
        {
            busEntityId = accountId;
        }

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory)
        {
            throw new Exception("Without APIAccess.");
        }
        Workflow wBean = factory.getWorkflowAPI();


        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        WorkflowDescDataVector dv = new WorkflowDescDataVector();

        if (fieldSearchType.equals("id"))
        {
            Integer id = new Integer(fieldValue);
            WorkflowDescData w = wBean.getWorkflowDesc(id.intValue());
            if (null != w)
            {
                if ( 0 != busEntityId )
                {
                    if (w.getWorkflow().getBusEntityId() == busEntityId)
                    {
                        dv.add(w);
                    }
                }
                else
                {
                    dv.add(w);
                }
            }
        }
        else if (fieldSearchType.equals("nameBegins"))
        {
            dv = wBean.getWorkflowDescCollectionByName(fieldValue, busEntityId,
                    Workflow.BEGINS_WITH_IGNORE_CASE, false);
        }
        else if (fieldSearchType.equals("nameContains"))
        {
            dv = wBean.getWorkflowDescCollectionByName(fieldValue, busEntityId,
                    Workflow.CONTAINS_IGNORE_CASE, false);
        }
        else
        {
            dv = wBean.getWorkflowDescCollectionByEntity(busEntityId, false);
        }
        sForm.setWorkflowDescList(dv);
    }



    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void init(HttpServletRequest request,
                            ActionForm form)
            throws Exception {

        initFormVectors(request);
        HttpSession session = request.getSession();
        int storeId = getStoreId(request);
        GroupSearchCriteriaView gcrit = GroupSearchCriteriaView.createValue();
		CleanwiseUser appUser = (CleanwiseUser) session
				.getAttribute(Constants.APP_USER);
		if (appUser.isaStoreAdmin() == true) {
			gcrit.setStoreId(storeId);
		}
        gcrit.setGroupStatus(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
        gcrit.setGroupType(RefCodeNames.GROUP_TYPE_CD.USER);
        APIAccess factory = new APIAccess();
        Group groupEjb = factory.getGroupAPI();
        Distributor distEjb = factory.getDistributorAPI();

        GroupDataVector gDV = groupEjb.getGroups(gcrit,Group.NAME_CONTAINS_IGNORE_CASE,Group.ORDER_BY_NAME);

        BusEntitySearchCriteria freightCriteria = new BusEntitySearchCriteria();
        IdVector storeIds = new IdVector();
        storeIds.add(new Integer(storeId));
        freightCriteria.setStoreBusEntityIds(storeIds);
        FreightHandlerViewVector freightHandlers = distEjb.getFreightHandlersByCriteria(freightCriteria);

        session.setAttribute("FreightHandlers.found.vector",freightHandlers);
        session.setAttribute("UserGroups",gDV);
        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initLocate(HttpServletRequest request,
                                  ActionForm form)
            throws Exception {

        StoreWorkflowMgrSearchForm sForm = (StoreWorkflowMgrSearchForm)form;

        sForm.setSearchBusEntityType("store");
        sForm.setBusEntityIdS("");

        String storeIdS = request.getParameter("storeid");
        if (null != storeIdS && ! "".equals(storeIdS))
        {
            sForm.setSearchBusEntityType("store");
            sForm.setBusEntityIdS(storeIdS);
        }

        String accountIdS = request.getParameter("accountid");
        if (null != accountIdS && ! "".equals(accountIdS))
        {
            sForm.setSearchBusEntityType("account");
            sForm.setBusEntityIdS(accountIdS);
        }

        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void sortWorkflows(HttpServletRequest request,
                                     ActionForm form)
            throws Exception {
        HttpSession session = request.getSession();
        WorkflowDataVector v =
                (WorkflowDataVector) session.getAttribute
                        ("Workflow.found.vector");
        if (v == null)
        {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(v, sortField);

        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
	public static void sortSites(HttpServletRequest request, ActionForm form) throws Exception {
		HttpSession session = request.getSession();
		SiteViewVector v = (SiteViewVector) session.getAttribute("Workflow.sites.vector");
		if (v == null) return;		
		String sortField = request.getParameter("sortField");
		DisplayListSort.sort(v, sortField);
		return;
	}


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors delete(HttpServletRequest request,
                                      ActionForm pform)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm form = (StoreWorkflowDetailForm) pform;
        String s = form.getId();
        if (null == s)
        {
            ae.add("workflow",
                    new ActionError
                            ("Delete workflow Error: the workflow id was not specified."));
        }

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        int wid = Integer.parseInt(s);

        try
        {
            IdVector currentSites = wkflBean.getWorkflowSiteIds(wid);
            if (currentSites.size() > 0)
            {
                ae.add("workflow",
                        new ActionError("This Workflow cannot be deleted" +
                                "since there are still some sites using it."));
                return ae;
            }

            wkflBean.deleteWorkflow(wid);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                    new ActionError("Workflow delete error: " + e));
        }

        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pform          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors linkSites(HttpServletRequest request,
                                         ActionForm pform)
            throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreWorkflowSitesForm form = (StoreWorkflowSitesForm) pform;
        String s = form.getId();
        if (null == s)
        {
            ae.add("workflow",
                    new ActionError
                            ("Link Sites Error: the workflow id was not specified."));
        }

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            form.setBusEntityId(w.getBusEntityId());
            form.setName(w.getShortDesc());
            form.setStatusCd(w.getWorkflowStatusCd());
            form.setId(String.valueOf(w.getWorkflowId()));
            String[] displayids = form.getDisplayIds();
            // Foreach id displayed. Assign or unasign the
            // site depending on whether the id is in
            // the select list.
            for (int i1 = 0; i1 < displayids.length; i1++)
            {
                String cursiteid = displayids[i1];
                String[] selectids = form.getSelectIds();
                boolean addsite = false;
                for (int i = 0; i < selectids.length; i++)
                {
                    if (cursiteid.equals(selectids[i]))
                    {
                        addsite = true;
                        break;
                    }
                }
                if (addsite)
                {
                    SessionTool lses = new SessionTool(request);

                    wkflBean.assignWorkflow
                            (wid, Integer.parseInt(cursiteid),
                                    lses.getLoginName());
                }
                else
                {
                    wkflBean.unassignWorkflow
                            (wid, Integer.parseInt(cursiteid));
                }
            }
        }
        catch (Exception e)
        {
            ae.add("workflow",
                    new ActionError("Workflow was not found." +
                            " Error: " + e));
        }

        return ae;
    }
    private static void resetSiteList
            (HttpServletRequest request) {
        request.getSession().removeAttribute("Workflow.sites.vector");
    }

    /**
     * Describe <code>assignAllSites</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pform an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors assignAllSites
            (HttpServletRequest request,
             ActionForm pform)
            throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreWorkflowSitesForm form = (StoreWorkflowSitesForm) pform;
        String s = form.getId();
        if (null == s)
        {
            ae.add("workflow",
                    new ActionError
                            ("Assign All Sites Error: " +
                                    "The workflow id was not specified."));
            return ae;
        }

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            // Assign the workflow to all sites in this account.
            int acctid = w.getBusEntityId();
            log.info("assigning all sites of account " + acctid + " to wkfl " + wid);
            SessionTool lses = new SessionTool(request);
            wkflBean.assignWorkflowToAccountSites
                    (wid, acctid, lses.getLoginName());
            resetSiteList(request);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                    new ActionError("Workflow Error: " + e));
        }

        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pform          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors initSites(HttpServletRequest request,
                                         ActionForm pform)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowSitesForm form = new StoreWorkflowSitesForm();

        HttpSession session = request.getSession();
        String s = request.getParameter("workflowId");
		if (null == s) {
			ae.add("workflow", new ActionError("Init Sites Error: the workflow id was not specified."));
		}

		SiteViewVector oldData=(SiteViewVector)session.getAttribute("Workflow.sites.vector");
        if(oldData!=null)
            session.setAttribute("Workflow.sites.vector", new SiteViewVector());
        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            form.setBusEntityId(w.getBusEntityId());
            form.setName(w.getShortDesc());
            form.setStatusCd(w.getWorkflowStatusCd());
            form.setId(String.valueOf(w.getWorkflowId()));
            IdVector sites = wkflBean.getWorkflowSiteIds(wid);
            String[] currsites = new String[sites.size()];
            for (int idx = 0; idx < currsites.length; idx++)
            {
                Integer id = (Integer) sites.get(idx);
                currsites[idx] = new String(id.toString());
            }
            form.setSelectIds(currsites);
            session.setAttribute("STORE_WORKFLOW_SITES_FORM", form);
            request.setAttribute("STORE_WORKFLOW_SITES_FORM", form);
        }
        catch (Exception e)
        {
            ae.add("workflow",
                    new ActionError("Workflow was not found." +
                            " Error: " + e));
        }

        return ae;
    }


    /**
     * Description of the Method
     *
     * @param  request        Description of Parameter
     * @param pForm an <code>ActionForm</code> value
     * @return                Description of the Returned Value
     * @exception  Exception  Description of Exception
     */
	public static ActionErrors fetchCurrentSites(HttpServletRequest request, ActionForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        String workflowIdStr = request.getParameter("workflowId");
		if (!Utility.isSet(workflowIdStr)) {
			ae.add("workflow", new ActionError("List Sites Error: the workflow id was not specified."));
			return ae;
		}
        HttpSession session = request.getSession();
        StoreWorkflowSitesForm form = (StoreWorkflowSitesForm) pForm;

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        int wid = Integer.parseInt(workflowIdStr);

		try {
			QueryRequest qr = new QueryRequest();
			qr.filterByWorkflowId(wid);
			qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
			qr.orderBySiteName(true);
			
			List<SiteView> sdv = siteBean.getSiteCollection(qr);
			String[] currsites = new String[sdv.size()];
			for (int idx = 0; idx < sdv.size(); idx++) {
				SiteView siteView = sdv.get(idx);
				currsites[idx] = String.valueOf(siteView.getId());
			}
			form.setSelectIds(currsites);
			session.setAttribute("Workflow.sites.vector", sdv);
		} catch (Exception e) {
			ae.add("workflow", new ActionError("Problem getting the sites for Workflow:" + wid + " Error: " + e));
		}
		return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pform          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
	public static ActionErrors searchForSites(HttpServletRequest request, ActionForm form) throws Exception {
		ActionErrors ae = new ActionErrors();

		APIAccess factory = new APIAccess();
		Site siteEjb = factory.getSiteAPI();
		Workflow wkflBean = factory.getWorkflowAPI();

		StoreWorkflowSitesForm pForm = (StoreWorkflowSitesForm) form;
		QueryRequest qr = new QueryRequest();
		HttpSession session = request.getSession();

		String fieldValue = pForm.getSearchField().trim();
		String searchType = pForm.getSearchType();

		SiteViewVector sites = new SiteViewVector();
		session.setAttribute("Workflow.sites.vector", sites);
		qr.filterByAccountId(pForm.getBusEntityId());

		if (searchType.equalsIgnoreCase(Constants.ID) && fieldValue.length() > 0) {
			Integer id = new Integer(fieldValue);
			qr.filterBySiteId(id);
		} else {
			qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);

			if (searchType.equals(Constants.NAME_BEGINS) && fieldValue.length() > 0) {
				qr.filterBySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
			} else if (fieldValue.length() > 0 || pForm.getCity().length() > 0 || pForm.getState().length() > 0) {
				qr.filterBySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
			}
			if (Utility.isSet(pForm.getCity())) {
				qr.filterByCity(pForm.getCity().trim(), QueryRequest.BEGINS_IGNORE_CASE);
			}
			if (Utility.isSet(pForm.getState())) {
				qr.filterByState(pForm.getState().trim(), QueryRequest.BEGINS_IGNORE_CASE);
			}
			if (Utility.isSet(pForm.getPostalCode())) {
				qr.filterByZip(pForm.getPostalCode().trim(), QueryRequest.BEGINS_IGNORE_CASE);
			}
			if (Utility.isSet(pForm.getCounty())) {
				qr.filterByCounty(pForm.getCounty().trim(), QueryRequest.BEGINS_IGNORE_CASE);
			}
		}

		if (!pForm.getShowInactiveFl()) {
			List<String> statusList = new ArrayList<String>();
			statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
			statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
			qr.filterBySiteStatusCdList(statusList);
		}

		sites = siteEjb.getSiteCollection(qr);

		List<Integer> siteIds = wkflBean.getWorkflowSiteIds(pForm.getIntId());
		log.info("got the sites: " + siteIds);

		String[] currsites = new String[siteIds.size()];
		pForm.setSelectIds(currsites);
		
		int idx = 0;
		for(Integer siteId : siteIds){
			currsites[idx] = siteId.toString();
			idx++;
		}
		
		pForm.setSelectIds(currsites);
		session.setAttribute("Workflow.sites.vector", sites);

		return ae;
	}
    
    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pForm          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors update(HttpServletRequest request,
                                      ActionForm pForm)
            throws Exception {

        StoreWorkflowDetailForm form = (StoreWorkflowDetailForm) pForm;
        ActionErrors ae = new ActionErrors();

        ae = checkFormAttribute(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();

        WorkflowData w = form.getOriginalData();
        w.setWorkflowId(form.getIntId());
        w.setShortDesc(form.getName());
        w.setWorkflowTypeCd(form.getTypeCd());
        w.setWorkflowStatusCd(form.getStatusCd());
        w.setBusEntityId(form.getBusEntityId());
        SessionTool lses = new SessionTool(request);
        w.setModBy(lses.getLoginName());
        Date date = new Date(System.currentTimeMillis());
        w.setModDate(date);
		if (form.getId().equals("0")) {
			w.setAddBy(lses.getLoginName());
			w.setAddDate(date);
		}

		if (w.getWorkflowId() == 0) {
			try {
				w = wkflBean.createWorkflow(w);
				form.setOriginalData(w);
				form.setId(String.valueOf(w.getWorkflowId()));
			} catch (Exception e) {
				ae.add("workflow", new ActionError("Workflow could not be created: " + e));
			}

		} else {
			try {
				wkflBean.updateWorkflow(w);
			} catch (Exception e) {
				ae.add("workflow", new ActionError("Workflow could not be updated: " + e));
			}
		}
        return ae;
    }

    private static ActionErrors checkFormAttribute(HttpServletRequest request, StoreWorkflowDetailForm mgrForm) throws Exception {
        ActionErrors ae = new ActionErrors();

        if (!Utility.isSet(mgrForm.getTypeCd())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "storeAccountWorkflow.text.WorkFlowType");
            if (field == null) {
                field = "Workflow Type";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("WorkflowType", new ActionError("error.simpleGenericError", mess));
        }
        if (!Utility.isSet(mgrForm.getName())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "storeAccountWorkflow.text.WorkFlowName");
            if (field == null) {
                field = "Workflow Name";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("WorkflowName", new ActionError("error.simpleGenericError", mess));
        }
        if (!Utility.isSet(mgrForm.getStatusCd())) {
            String field = ClwI18nUtil.getMessageOrNull(request, "storeAccountWorkflow.text.WorkFlowStatus");
            if (field == null) {
                field = "Workflow Status";
            }
            String mess = ClwI18nUtil.getMessage(request, "variable.empty.error", new Object[]{field}, true);
            mess = mess == null ? ("Field  " + field + " requires information:") : mess;
            ae.add("WorkflowStatus", new ActionError("error.simpleGenericError", mess));
        }

        return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pForm          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
	public static ActionErrors detail(HttpServletRequest request, ActionForm pForm) throws Exception {

        // Make sure that the needed session variables are in place.
        init(request, pForm);

        StoreWorkflowDetailForm form = new StoreWorkflowDetailForm();
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        String s = request.getParameter("searchField");
		if (s == null) {
			ae.add("workflow", new ActionError("Error: the workflow id was not specified."));
			return ae;
		}
		int wid = Integer.parseInt(s);

        WorkflowData w = null;
        try
        {
            w = wkflBean.getWorkflow(wid);
            form.setOriginalData(w);
            form.setBusEntityId(w.getBusEntityId());
            // Set the needed account info.
            setupAccountInfo(request, w.getBusEntityId());

            form.setName(w.getShortDesc());
            form.setTypeCd(w.getWorkflowTypeCd());
            form.setStatusCd(w.getWorkflowStatusCd());
            form.setId(String.valueOf(w.getWorkflowId()));
            session.setAttribute("STORE_WORKFLOW_DETAIL_FORM", form);
            request.setAttribute("STORE_WORKFLOW_DETAIL_FORM", form);

            // Get the rules for this workflow.
            WorkflowRuleJoinViewVector wrjVwV =
                    wkflBean.getWorkflowRules
                            (wid);
            session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
        }
        catch (Exception e) {
			ae.add("workflow", new ActionError("Workflow was not found." + " Error: " + e));
		}
		return ae;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pForm          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
	public static ActionErrors rmRule(HttpServletRequest request, ActionForm pForm) throws Exception {

		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();

		APIAccess factory = new APIAccess();
		Workflow wkflBean = factory.getWorkflowAPI();
		String s = request.getParameter("workflowRuleId");
		if (null == s) {
			ae.add("workflow", new ActionError("Error: the workflow rule id was not specified."));
			return ae;
		}
		int wrid = Integer.parseInt(s);
		s = request.getParameter("workflowId");
		if (null == s) {
			ae.add("workflow", new ActionError("Error: the workflow id was not specified."));
			return ae;
		}
		int wid = Integer.parseInt(s);

		// check if rule in the queue
		OrderDataVector queueV = wkflBean.getOrdersWorkflowQueueV(wrid);
		if (queueV.size() > 0) {
			String msg = "Error. There are unprocessed workflow actions related to the rule.";
			log.info(msg);
			ae.add("error", new ActionError("error.simpleGenericError", msg));
			return ae;
		}

		try {
			wkflBean.deleteWorkflowRule(wrid, true);
			WorkflowRuleJoinViewVector wrjVwV = wkflBean.getWorkflowRules(wid);
			session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
		} catch (Exception e) {
			ae.add("error", new ActionError("Workflow was not found." + " Error: " + e));
		}
		return ae;
	}

    /**
     * Describe <code>moveRuleUp</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pForm an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors moveRuleUp(HttpServletRequest request,
                                          ActionForm pForm)
            throws Exception {
        return moveRule(request, pForm,Workflow.MOVE_RULE_UP);
    }

    /**
     * Describe <code>moveRuleDown</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pForm an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors moveRuleDown(HttpServletRequest request,
                                            ActionForm pForm)
            throws Exception {
        return moveRule(request, pForm,Workflow.MOVE_RULE_DOWN);
    }

    /**
     * Describe <code>moveRule</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param pForm an <code>ActionForm</code> value
     * @param pMvCmd an <code>int</code> value
     * @return an <code>ActionErrors</code> value
     * @exception Exception if an error occurs
     */
	public static ActionErrors moveRule(HttpServletRequest request, ActionForm pForm, int pMvCmd) throws Exception {

		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();

		APIAccess factory = new APIAccess();
		Workflow wkflBean = factory.getWorkflowAPI();
		String s = request.getParameter("workflowRuleId");
		if (null == s) {
			ae.add("workflow", new ActionError("Error: the workflow rule id was not specified."));
			return ae;
		}
		int wrid = Integer.parseInt(s);
		s = request.getParameter("workflowId");
		if (null == s) {
			ae.add("workflow", new ActionError("Error: the workflow id was not specified."));
			return ae;
		}
		int wid = Integer.parseInt(s);

		try {
			wkflBean.updateWorkflowRuleSeq(wrid, pMvCmd);
			// Get the updated rules.
			WorkflowRuleJoinViewVector wrjVwV = wkflBean.getWorkflowRules(wid);
			session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
		} catch (Exception e) {
			ae.add("workflow", new ActionError("Workflow was not found." + " Error: " + e));
		}
		return ae;
	}

	public static ActionErrors setCWSkuCheck(HttpServletRequest request, ActionForm pForm)

	throws Exception {

		ActionErrors ae = new ActionErrors();

		String s = request.getParameter("bus_entity_id");

		if (null == s || s.trim().length() == 0) {
			ae.add("workflow", new ActionError("Error: the business entity id was not specified."));
			return ae;
		}

		int beid = Integer.parseInt(s);
		log.info("bus_entity_id 2: " + beid);

		String cwSkuFVal = "off";
		s = request.getParameter("cw_sku_workflow_flag");
		if (null != s) {
			cwSkuFVal = s;
		} else {
			cwSkuFVal = "off";
		}
		boolean skuwkfl = cwSkuFVal.equals("on") ? true : false;

		try {
			log.info("wkfl set beid: " + beid + " flag: " + skuwkfl);
			StoreMgrLogic.setSkuWorkflowSetting(beid, skuwkfl);
		} catch (Exception e) {
			ae.add("workflow", new ActionError("CW SKU Workflow was not set for " + "business entity id: " + beid + " Error: " + e));
		}
		return ae;
	}

	public static ActionErrors orderVelocityRule(HttpServletRequest request, ActionForm pForm) throws Exception {

		ActionErrors ae = new ActionErrors();
		StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
		HttpSession session = request.getSession();
		String ruleSeqS = request.getParameter("ruleSeq");
		int ruleSeq = Integer.parseInt(ruleSeqS);
		sForm.setRuleSeq(ruleSeq);
		sForm.setApplyRuleGroupId(0);
		if (ruleSeq == 0) {
			sForm.setTimespanInDays("");
			sForm.setRuleGroup("");
			sForm.setRuleAction("");
			// sForm.setVelocityAction("");
			return ae;
		}
		WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
		for (int ii = 0; ii < wrjVwV.size(); ii++) {
			WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
			if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
				setRuleAssoc(request, sForm, wrjVw);
				WorkflowRuleDataVector wrDV = wrjVw.getRules();
				WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
				// sForm.setVelocityAction(wrD.getRuleAction());
				sForm.setRuleAction(wrD.getRuleAction());
				sForm.setTimespanInDays(wrD.getRuleExpValue());
				sForm.setApproverGroupId(wrD.getApproverGroupId());
				sForm.setEmailGroupId(wrD.getEmailGroupId());
				sForm.setRuleGroup(wrD.getRuleGroup());
				sForm.setWarningMessage(wrD.getWarningMessage());
				parseNextRule(request, sForm, wrD.getNextActionCd());
				break;
			}
		}
		return ae;
	}

	public static ActionErrors breakPointRule(HttpServletRequest request, ActionForm pForm) throws Exception {

		ActionErrors ae = new ActionErrors();
		StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
		HttpSession session = request.getSession();
		String ruleSeqS = request.getParameter("ruleSeq");
		int ruleSeq = Integer.parseInt(ruleSeqS);
		sForm.setRuleSeq(ruleSeq);
		sForm.setApplyRuleGroupId(0);
		if (ruleSeq == 0) {
			sForm.setTotalExp("");
			sForm.setTotalValue("");
			// sForm.setTotalAction("");
			sForm.setRuleAction("");
			sForm.setRuleGroup("");
			return ae;
		}
		WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
		int approverGroupId = 0;
		int emailGroupId = 0;
		for (int ii = 0; ii < wrjVwV.size(); ii++) {
			WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
			if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
				setRuleAssoc(request, sForm, wrjVw);
				WorkflowRuleDataVector wrDV = wrjVw.getRules();
				WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
				sForm.setTotalExp("-");
				sForm.setTotalValue("-");
				sForm.setRuleAction("-");
				sForm.setApproverGroupId(0);
				sForm.setEmailGroupId(0);
				sForm.setRuleGroup("");
				sForm.setWarningMessage(wrD.getWarningMessage());
				parseNextRule(request, sForm, wrD.getNextActionCd());
				break;
			}
		}
		getRuleUsers(request, sForm);
		return ae;
	}

    public static ActionErrors orderTotalRule(HttpServletRequest request,
                                              ActionForm pForm)
            throws Exception
    {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            //sForm.setTotalAction("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }
        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(wrjVw.getRuleTypeCd()) &&
                    ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setTotalExp(wrD.getRuleExp());
                sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request,sForm,wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }

    public static ActionErrors workOrderBudgetRule(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();

        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);

        if (ruleSeq == 0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");

        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setTotalExp(wrD.getRuleExp());
                sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }

    public static ActionErrors workOrderBudgetSpendingForCostCenterRule(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();

        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);

        if (ruleSeq == 0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");

        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setTotalExp(wrD.getRuleExp());
                sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }


       public static ActionErrors workOrderTotalRule(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();

        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);

        if (ruleSeq == 0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");

        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_TOTAL.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setTotalExp(wrD.getRuleExp());
                sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }

    public static ActionErrors userLimitRule(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();

        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);

        if (ruleSeq == 0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");

        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setTotalExp(wrD.getRuleExp());
                sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }

    public static ActionErrors orderBudgetPeriodChanged(HttpServletRequest request, ActionForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();

        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);

        if (ruleSeq == 0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }
        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED
                    .equals(wrjVw.getRuleTypeCd())
                    && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                //sForm.setTotalExp(wrD.getRuleExp());
                //sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }


    public static ActionErrors workOrderAnyRule(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();

        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);

        if (ruleSeq == 0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");

        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_ANY.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setTotalExp(wrD.getRuleExp());
                sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }

    public static ActionErrors budgetYTDRule(HttpServletRequest request,
                                             ActionForm pForm)
            throws Exception
    {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String workflowIdS = request.getParameter("workflowId");
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
            sForm.setBudgetExp("");
            sForm.setBudgetValue("");
            //sForm.setBudgetAction("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }
        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(wrjVw.getRuleTypeCd()) &&
                    ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                log.info("<=======StoreWorkflowMgrLogic informs : wrD ="+wrD+"=============>");
                sForm.setBudgetExp(wrD.getRuleExp());
                sForm.setBudgetValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request,sForm,wrD.getNextActionCd());
                break;
            }
        }
        return ae;

    }

    public static ActionErrors budgetRemainingPerCCRule(HttpServletRequest request,
                                                        ActionForm pForm)
            throws Exception
    {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
            sForm.setBudgetExp("");
            sForm.setBudgetValue("");
            //sForm.setBudgetAction("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(wrjVw.getRuleTypeCd()) &&
                    ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

                sForm.setBudgetExp(wrD.getRuleExp());
                sForm.setBudgetValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request,sForm,wrD.getNextActionCd());
                break;
            }
        }
        //sForm.setBudgetAction("");
        return ae;

    }

    public static ActionErrors orderSkuRule(HttpServletRequest request,
                                            ActionForm pForm)
            throws Exception
    {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
            sForm.setOrderSku("");
            //sForm.setSkuAction("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for(int ii=0; ii<wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(wrjVw.getRuleTypeCd()) &&
                    ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

                sForm.setOrderSku(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request,sForm,wrD.getNextActionCd());
                break;
            }
        }
        //sForm.setSkuAction("");
        return ae;

    }

    public static ActionErrors orderSkuQtyRule(HttpServletRequest request,
                                               ActionForm pForm)
            throws Exception
    {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);
        if(ruleSeq==0) {
            sForm.setOrderSku("");
            sForm.setTotalValue("");
            //sForm.setSkuAction("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        String skuList = "";
        String valueS = "";
        String compS = RefCodeNames.RULE_EXPRESSION.LESS;
        boolean nextRuleFl = false;
        for(int jj=0; jj<wrjVwV.size(); jj++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(jj);
            if(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(wrjVw.getRuleTypeCd()) &&
                    ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);

                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                for(int ii=0; ii<wrDV.size(); ii++) {
                    WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(ii);
                    if(ii==0) {
                        sForm.setRuleAction(wrD.getRuleAction());
                    }
                    String expr = wrD.getRuleExp();
                    if(!nextRuleFl) {
                        sForm.setRuleGroup(wrD.getRuleGroup());
                        parseNextRule(request,sForm,wrD.getNextActionCd());
                        nextRuleFl = true;
                    }
                    if(RefCodeNames.RULE_EXPRESSION.SKU_NUM.equals(expr)) {
                        if(skuList.length()>0) skuList += ", ";
                        skuList += wrD.getRuleExpValue();
                    } else if (RefCodeNames.RULE_EXPRESSION.LESS.equals(expr)) {
                        valueS = wrD.getRuleExpValue();
                        compS = RefCodeNames.RULE_EXPRESSION.LESS;
                    } else if (RefCodeNames.RULE_EXPRESSION.GREATER.equals(expr)) {
                        valueS = wrD.getRuleExpValue();
                        compS = RefCodeNames.RULE_EXPRESSION.GREATER;
                    }
                    sForm.setWarningMessage(wrD.getWarningMessage());                    
                }
                break;
            }
        }
        sForm.setOrderSku(skuList);
        sForm.setTotalValue(valueS);
        sForm.setTotalExp(compS);
        //sForm.setSkuAction("");
        return ae;

    }

/////////////////////////////////////
	public static ActionErrors itemCategoryRule(HttpServletRequest request, ActionForm pForm) throws Exception {

		ActionErrors ae = new ActionErrors();
		StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
		int accountId = sForm.getBusEntityId();
		log.info("StoreWorkflowMgrLogic WLLLLLLLLL accountId: " + accountId);
		// !!!!!!!!!!!!!!!!!!!!!
		HttpSession session = request.getSession();
		APIAccess factory = new APIAccess();
		CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
		Account accountEjb = factory.getAccountAPI();
		CatalogData acctCat = accountEjb.getAccountCatalog(accountId);
		log.info("StoreWorkflowMgrLogic WLLLLLLLLL acctCat: " + acctCat);
		IdVector catalogIdV = new IdVector();
		catalogIdV.add(new Integer(acctCat.getCatalogId()));
		ItemDataVector categories = catalogInfEjb.getCatalogCategories(catalogIdV);
		log.info("StoreWorkflowMgrLogic WLLLLLLLLL categories: " + categories);
		sForm.setCategoryList(categories);

		String ruleSeqS = request.getParameter("ruleSeq");
		int ruleSeq = Integer.parseInt(ruleSeqS);
		sForm.setRuleSeq(ruleSeq);
		sForm.setApplyRuleGroupId(0);
		if (ruleSeq == 0) {
			sForm.setItemCategoryId(0);
			sForm.setTotalValue("");
			sForm.setRuleAction("");
			sForm.setRuleGroup("");
			return ae;
		}

		WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
		int categId = 0;
		String valueS = "";
		String compS = RefCodeNames.RULE_EXPRESSION.GREATER;
		boolean nextRuleFl = false;
		for (int jj = 0; jj < wrjVwV.size(); jj++) {
			WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(jj);
			if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
				setRuleAssoc(request, sForm, wrjVw);

				WorkflowRuleDataVector wrDV = wrjVw.getRules();
				for (int ii = 0; ii < wrDV.size(); ii++) {
					WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(ii);
					if (ii == 0) {
						sForm.setRuleAction(wrD.getRuleAction());
					}
					String expr = wrD.getRuleExp();
					if (!nextRuleFl) {
						sForm.setRuleGroup(wrD.getRuleGroup());
						sForm.setWarningMessage(wrD.getWarningMessage());
						parseNextRule(request, sForm, wrD.getNextActionCd());
						nextRuleFl = true;
					}
					if (RefCodeNames.RULE_EXPRESSION.CATEGORY_ID.equals(expr)) {
						String categIdS = wrD.getRuleExpValue();
						if (Utility.isSet(categIdS)) {
							try {
								categId = Integer.parseInt(categIdS);
							} catch (Exception exc) {
							}
						}
					} else if (RefCodeNames.RULE_EXPRESSION.LESS.equals(expr)) {
						valueS = wrD.getRuleExpValue();
						compS = RefCodeNames.RULE_EXPRESSION.LESS;
					} else if (RefCodeNames.RULE_EXPRESSION.GREATER.equals(expr)) {
						valueS = wrD.getRuleExpValue();
						compS = RefCodeNames.RULE_EXPRESSION.GREATER;
					} else if (RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL.equals(expr)) {
						valueS = wrD.getRuleExpValue();
						compS = RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL;
					} else if (RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL.equals(expr)) {
						valueS = wrD.getRuleExpValue();
						compS = RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL;
					}
				}
				break;
			}
		}
		sForm.setItemCategoryId(categId);
		sForm.setTotalValue(valueS);
		sForm.setTotalExp(compS);
		// sForm.setSkuAction("");
		return ae;

	}
/////////////////////////////////////

	public static ActionErrors itemPriceRule(HttpServletRequest request, ActionForm pForm) throws Exception {

		ActionErrors ae = new ActionErrors();
		StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
		HttpSession session = request.getSession();
		String workflowIdS = request.getParameter("workflowId");
		String ruleSeqS = request.getParameter("ruleSeq");
		int ruleSeq = Integer.parseInt(ruleSeqS);
		sForm.setRuleSeq(ruleSeq);
		sForm.setApplyRuleGroupId(0);
		if (ruleSeq == 0) {
			sForm.setTotalExp("");
			sForm.setTotalValue("");
			// sForm.setTotalAction("");
			sForm.setRuleAction("");
			sForm.setRuleGroup("");
			return ae;
		}
		WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
		for (int ii = 0; ii < wrjVwV.size(); ii++) {
			WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
			if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
				setRuleAssoc(request, sForm, wrjVw);
				WorkflowRuleDataVector wrDV = wrjVw.getRules();
				WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
				sForm.setTotalExp(wrD.getRuleExp());
				sForm.setTotalValue(wrD.getRuleExpValue());
				sForm.setRuleAction(wrD.getRuleAction());
				sForm.setApproverGroupId(wrD.getApproverGroupId());
				sForm.setEmailGroupId(wrD.getEmailGroupId());
				sForm.setRuleGroup(wrD.getRuleGroup());
				sForm.setWarningMessage(wrD.getWarningMessage());
				parseNextRule(request, sForm, wrD.getNextActionCd());
				break;
			}
		}
		getRuleUsers(request, sForm);
		return ae;
	}

	public static ActionErrors nonOrderGuideItemRule(HttpServletRequest request, ActionForm pForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
		String ruleSeqS = request.getParameter("ruleSeq");
		int ruleSeq = Integer.parseInt(ruleSeqS);
		sForm.setApplyRuleGroupId(0);
		sForm.setRuleSeq(ruleSeq);
		// vvv 4283
		if (ruleSeq == 0) {
			sForm.setDistId("");
			sForm.setDistName("");
			sForm.setStateList("");
			sForm.setRuleAction("");
			sForm.setRuleGroup("");
			return ae;
		}

		WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
		for (int ii = 0; ii < wrjVwV.size(); ii++) {
			WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
			if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
				setRuleAssoc(request, sForm, wrjVw);
				WorkflowRuleDataVector wrDV = wrjVw.getRules();
				WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

				sForm.setRuleAction(wrD.getRuleAction());
				sForm.setRuleGroup(wrD.getRuleGroup());
				sForm.setWarningMessage(wrD.getWarningMessage());
				parseNextRule(request, sForm, wrD.getNextActionCd());
				break;
			}
		}
		return ae;

	}

	public static ActionErrors everyOrderRule(HttpServletRequest request, ActionForm pForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
		HttpSession session = request.getSession();
		// String workflowIdS = request.getParameter("workflowId");
		String ruleSeqS = request.getParameter("ruleSeq");
		int ruleSeq = Integer.parseInt(ruleSeqS);
		sForm.setRuleSeq(ruleSeq);
		sForm.setApplyRuleGroupId(0);
		sForm.setTotalExp("");
		sForm.setTotalValue("");

		// vvv 4283
		if (ruleSeq == 0) {
			sForm.setDistId("");
			sForm.setDistName("");
			sForm.setStateList("");
			sForm.setRuleAction("");
			sForm.setRuleGroup("");
			return ae;
		}

		WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
		for (int ii = 0; ii < wrjVwV.size(); ii++) {
			WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
			if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
				setRuleAssoc(request, sForm, wrjVw);
				WorkflowRuleDataVector wrDV = wrjVw.getRules();
				WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);

				sForm.setRuleAction(wrD.getRuleAction());
				sForm.setTotalExp(Utility.strNN(wrD.getRuleExp()));
				sForm.setTotalValue(Utility.strNN(wrD.getRuleExpValue()));
				sForm.setRuleGroup(wrD.getRuleGroup());
				sForm.setWarningMessage(wrD.getWarningMessage());
				parseNextRule(request, sForm, wrD.getNextActionCd());
				break;
			}
		}
		return ae;

	}

    public static void parseNextRule(HttpServletRequest request,
                                     StoreWorkflowDetailForm pForm, String pNextAction)
    {
        try {

            if (pNextAction==null) {
                return;
            }
            if(pNextAction.trim().startsWith("After")) {
                int ind1 = pNextAction.indexOf("days");
                if(ind1>6) {
                    String intervalS = pNextAction.substring(6,ind1).trim();
                    int interval = Integer.parseInt(intervalS);
                    pForm.setDaysUntilNextAction(interval);
                }
                if(pNextAction.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER) != -1) {
                    pForm.setNextActionCd(RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER);
                }
                else if(pNextAction.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER) != -1) {
                    pForm.setNextActionCd(RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER);
                }
            } else if (pNextAction.trim().startsWith("To:")) {
                int ind3 = pNextAction.indexOf("Id:");
                int ind0 = ind3;
                int ind2="To:".length();
                if(ind0>-1) {
                    ind0 += "Id:".length();
                    int ind1 = pNextAction.indexOf(".",ind0);
                    if(ind1>ind0) {
                        String userIdS = pNextAction.substring(ind0,ind1).trim();
                        int userId = Integer.parseInt(userIdS);
                        pForm.setEmailUserId(userId);
                        pForm.setEmailUserName(pNextAction.substring(ind2+1,ind3));
                    }
                }
            }

        } catch(Exception exc) {} //Dump exception - nothing serious
    }

    /**
     *  Adds a feature to the Rule attribute of the WorkflowMgrLogic class
     *
     *@param  request        The feature to be added to the Rule attribute
     *@param  pForm          The feature to be added to the Rule attribute
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors saveRule(HttpServletRequest request,
                                        ActionForm pForm)
            throws Exception {
        StoreWorkflowDetailForm form = (StoreWorkflowDetailForm) pForm;
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String ruleSeqOrigS = request.getParameter("ruleSeqOrig");
        if(ruleSeqOrigS==null) {
            String errorMess = "No rule number found. Call techincal support";
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        int ruleSeqOrig = Integer.parseInt(ruleSeqOrigS);
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = 0;
        try {
            ruleSeq = Integer.parseInt(ruleSeqS);
        } catch (Exception exc) {
            String errorMess = "Wrong rule number format: "+ruleSeqS;
            ae.add("error",
                    new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        APIAccess factory = new APIAccess();
        Workflow wkflEjb = factory.getWorkflowAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int wid = form.getIntId();
        WorkflowRuleDataVector wrDV = new WorkflowRuleDataVector();
        String ruleAction = "";
        try
        {
            SessionTool lses = new SessionTool(request);
            String user = lses.getLoginName();
            String rtype = form.getRuleTypeCd();
            log.info("rule type: " + rtype);
            ruleAction = form.getRuleAction();
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals
                    (rtype)) {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getBudgetExp());
                wrD.setRuleExpValue(form.getBudgetValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getBudgetAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals
                    (rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp("-");
                wrD.setRuleExpValue("-");
                wrD.setApproverGroupId(0);
                wrD.setEmailGroupId(0);
                //ruleAction = form.getBudgetAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals
                    (rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getBudgetExp());
                wrD.setRuleExpValue(form.getBudgetValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getBudgetAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals
                    (rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp("<");
                wrD.setRuleExpValue(form.getTimespanInDays());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getVelocityAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals
                    (rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp("==");
                wrD.setRuleExpValue(form.getOrderSku());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getSkuAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals
                    (rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getTotalExp());
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getTotalAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE.equals
                    (rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getTotalExp());
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getTotalAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getTotalExp());
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getTotalAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_TOTAL.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getTotalExp());
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getTotalAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getTotalExp());
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(" ");
                wrD.setRuleExpValue(" ");
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_ANY.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getTotalExp());
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(form.getTotalExp());
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getTotalAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals
                    (rtype))
            {
                //ruleAction = form.getSkuAction();
                String qtyS = form.getTotalValue();
                String compS = form.getTotalExp();
                if(!(RefCodeNames.RULE_EXPRESSION.LESS.equals(compS) ||
                        RefCodeNames.RULE_EXPRESSION.GREATER.equals(compS)))
                {
                    String errorMess = "Wrong expression value: "+compS;
                    ae.add("error",
                            new ActionError("error.simpleGenericError",errorMess));
                    return ae;
                }
                int qty = 0;
                try{
                    qty = Integer.parseInt(qtyS);
                }catch(Exception exc) {
                    String errorMess = "Wrong quatity value: "+qtyS;
                    ae.add("error",
                            new ActionError("error.simpleGenericError",errorMess));
                    return ae;
                }
                String skuList = form.getOrderSku();
                ArrayList skus = new ArrayList();
                char[] skuA = skuList.toCharArray();
                String skuNumS = "";
                for(int ii=0; ii<skuA.length; ii++) {
                    char cc = skuA[ii];
                    if(Character.isDigit(cc)) {
                        skuNumS += cc;
                        if(ii<skuA.length-1) {
                            continue;
                        }
                    } else if(Character.isLetter(cc)) {
                        String errorMess = "Wrong character in sku number: "+cc;
                        ae.add("error",
                                new ActionError("error.simpleGenericError",errorMess));
                        return ae;
                    }
                    if(skuNumS.length()>0) {
                        ArrayList critAL = new ArrayList();
                        SearchCriteria searchCriteria = new SearchCriteria();
                        searchCriteria.operator = SearchCriteria.EXACT_MATCH;
                        searchCriteria.value = skuNumS;
                        searchCriteria.name = SearchCriteria.CLW_SKU_NUMBER;
                        critAL.add(searchCriteria);
                        IdVector itemIdV = catalogInfEjb.searchProducts(critAL);
                        if(itemIdV.size()==0) {
                            String errorMess = "Wrong sku number: "+skuNumS;
                            ae.add("error",
                                    new ActionError("error.simpleGenericError",errorMess));
                            return ae;
                        }
                        skus.add(skuNumS);
                        skuNumS = "";
                    }
                }
                for(int ii=0; ii<skus.size(); ii++) {
                    WorkflowRuleData wrD = WorkflowRuleData.createValue();
                    wrDV.add(wrD);
                    wrD.setRuleExp(RefCodeNames.RULE_EXPRESSION.SKU_NUM);
                    wrD.setRuleExpValue((String) skus.get(ii));
                    wrD.setApproverGroupId(form.getApproverGroupId());
                    wrD.setEmailGroupId(form.getEmailGroupId());
                }
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(compS);
                wrD.setRuleExpValue(qtyS);
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
            }
//////////////////////
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY.equals
                    (rtype))
            {
                //ruleAction = form.getSkuAction();
                int categId = form.getItemCategoryId();
                String amountS = form.getTotalValue();
                String compS = form.getTotalExp();
                if(!(RefCodeNames.RULE_EXPRESSION.LESS.equals(compS) ||
                     RefCodeNames.RULE_EXPRESSION.GREATER.equals(compS) ||
                     RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL.equals(compS) ||
                     RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL.equals(compS)))
                {
                    String errorMess = "Wrong expression value: "+compS;
                    ae.add("error",
                            new ActionError("error.simpleGenericError",errorMess));
                    return ae;
                }

                try{
                    double amt = Double.parseDouble(amountS);
                }catch(Exception exc) {
                    String errorMess = "Wrong item price value: "+amountS;
                    ae.add("error",
                            new ActionError("error.simpleGenericError",errorMess));
                    return ae;
                }
                ItemDataVector categList = form.getCategoryList();
                boolean foundFl = false;
                if(categList!=null) {
                    for(Iterator iter=categList.iterator(); iter.hasNext();) {
                        ItemData iD = (ItemData) iter.next();
                        if(iD.getItemId()==categId) {
                            foundFl = true;
                            break;
                        }
                    }
                }
                if(!foundFl) {
                    String errorMess = "Selected category is not in the list. Category Id: "+categId;
                    ae.add("error",
                            new ActionError("error.simpleGenericError",errorMess));
                    return ae;
                }
                WorkflowRuleData wrD = new WorkflowRuleData();
                wrDV.add(wrD);
                wrD.setRuleExp(RefCodeNames.RULE_EXPRESSION.CATEGORY_ID);
                wrD.setRuleExpValue(String.valueOf(categId));
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());

                WorkflowRuleData wrD1 = new WorkflowRuleData();
                wrDV.add(wrD1);
                wrD1.setRuleExp(compS);
                wrD1.setRuleExpValue(amountS);
                wrD1.setApproverGroupId(form.getApproverGroupId());
                wrD1.setEmailGroupId(form.getEmailGroupId());
            }else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM);
                wrD.setRuleExpValue("0");
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getNonOrderGuideItemAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER);
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getNonOrderGuideItemAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER.equals(rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER);
                wrD.setRuleExpValue(form.getTotalValue());
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                //ruleAction = form.getNonOrderGuideItemAction();
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals
                    (rtype))
            {
                WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(Utility.strNN(form.getTotalExp()));
                wrD.setRuleExpValue(Utility.strNN(form.getTotalValue()));
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                ruleAction = form.getRuleAction();
            }
            //STJ-5014
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET.equals(rtype))
            {
            	//workOrderExcludedFromBudget(request, form);

            	WorkflowRuleData wrD = WorkflowRuleData.createValue();
                wrDV.add(wrD);
                wrD.setRuleExp(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET);
                wrD.setRuleExpValue("0");
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                ruleAction = form.getRuleAction();
            }
            else {
				String msg = "Unknown workflow rule type " + rtype;
				log.info(msg);
				ae.add("workflow", new ActionError(msg));
				return ae;
			}

            String nextAction = form.getNextActionCd();
			if (ruleAction == null || ruleAction.trim().length() == 0) {
				String errorMess = "No rule action found";
				ae.add("error", new ActionError("error.simpleGenericError", errorMess));
				return ae;
			}
            String finalAction = "Done";

            if (ruleAction.equals(RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL) ||
                    ruleAction.equals(RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER))
            {
                finalAction = " After " +
                        form.getDaysUntilNextAction() + " days ";
                finalAction += nextAction + ". ";
            }
            else if(ruleAction.equals(RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL))
            {
                finalAction = " To: " +
                        form.getEmailUserName() + " Id: " +
                        form.getEmailUserId() + ". ";
            }
            
            for(int ii=0; ii<wrDV.size(); ii++) {
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(ii);
                wrD.setModBy(user);
                wrD.setAddBy(user);
                wrD.setWorkflowId(wid);
                wrD.setRuleSeq(form.getRuleSeq());
                wrD.setRuleGroup(form.getRuleGroup());
                wrD.setRuleAction(ruleAction);
                wrD.setNextActionCd(finalAction);
                wrD.setShortDesc(String.valueOf(wid));
                wrD.setWorkflowRuleStatusCd("ACTIVE");
                wrD.setRuleTypeCd(rtype);
                wrD.setApproverGroupId(form.getApproverGroupId());
                wrD.setEmailGroupId(form.getEmailGroupId());
                wrD.setWarningMessage(form.getWarningMessage());
            }

            WorkflowAssocDataVector waDV = null;
            int applyGroupId = form.getApplyRuleGroupId();
            if(applyGroupId>0) {
                waDV = new WorkflowAssocDataVector();
                WorkflowAssocData waD = WorkflowAssocData.createValue();
                waDV.add(waD);
                waD.setAddBy(user);
                waD.setModBy(user);
                waD.setGroupId(applyGroupId);
                waD.setWorkflowId(wid);
                if(form.getApplySkipFl()) {
                    waD.setWorkflowAssocCd(RefCodeNames.WORKFLOW_ASSOC_CD.APPLY_FOR_GROUP_USERS);
                } else {
                    waD.setWorkflowAssocCd(RefCodeNames.WORKFLOW_ASSOC_CD.SKIP_FOR_GROUP_USERS);
                }
            }

            wkflEjb.saveWorkflowRule(ruleSeqOrig, wrDV,waDV);

            WorkflowRuleJoinViewVector wrjVwV =
                    wkflEjb.getWorkflowRules(wid);
            session.setAttribute("Workflow.rulesjoin.vector", wrjVwV);
        } catch (Exception e) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));
        } finally {
            // Reset the rule type.
            form.setRuleTypeCd("---");
        }

        return ae;
    }

    private static UserDataVector getGroupUsers(int groupId, int storeId)
            throws Exception
    {
        APIAccess factory = new APIAccess();
        Group groupEjb = factory.getGroupAPI();
        GroupSearchCriteriaView gcrit = GroupSearchCriteriaView.createValue();
        gcrit.setStoreId(storeId);
        gcrit.setGroupId(groupId);
        UserDataVector userDV = groupEjb.getUsersForGroup(gcrit, 0,0);
        if(userDV.size()>1) {
            DisplayListSort.sort(userDV, "lastName");
        }
        return userDV;
    }
    public static ActionErrors setDistFilter(HttpServletRequest request,
                                             ActionForm form)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm pForm = (StoreWorkflowDetailForm) form;
        pForm.setDistFilter(pForm.getLocateStoreDistForm().getDistributors());
        return ae;
    }

    private static int getStoreId(HttpServletRequest request)
            throws Exception
    {
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getBusEntity().getBusEntityId();
        return storeId;
    }

    private static void setRuleAssoc(HttpServletRequest request,StoreWorkflowDetailForm pForm,
                                     WorkflowRuleJoinView pWorkflowRuleJoin)
    {
        WorkflowAssocDataVector waDV = pWorkflowRuleJoin.getAssociations();
        pForm.setApplyRuleGroupId(0);
        for(Iterator iter=waDV.iterator(); iter.hasNext();) {
            WorkflowAssocData waD = (WorkflowAssocData) iter.next();
            pForm.setApplyRuleGroupId(waD.getGroupId());
            if(RefCodeNames.WORKFLOW_ASSOC_CD.APPLY_FOR_GROUP_USERS.equals(waD.getWorkflowAssocCd())) {
                pForm.setApplySkipFl(true);
            } else {
                pForm.setApplySkipFl(false);
            }
        }
    }

    private static void getRuleUsers(HttpServletRequest request, StoreWorkflowDetailForm pForm)
            throws Exception
    {
        int storeId = getStoreId(request);
        int approverGroupId = pForm.getApproverGroupId();
        if(approverGroupId>0) {
            UserDataVector approvers = getGroupUsers(approverGroupId, storeId);
            pForm.setApproverUsers(approvers);
        } else {
            pForm.setApproverUsers(null);
        }

        int emailGroupId = pForm.getEmailGroupId();
        if(emailGroupId>0) {
            UserDataVector emailUsers = getGroupUsers(emailGroupId, storeId);
            pForm.setEmailUsers(emailUsers);
        } else {

            pForm.setEmailUsers(null);
        }
    }

    public static void setSelectedDist(HttpServletRequest request, StoreWorkflowDetailForm sForm) {
        String distrId = request.getParameter("locateStoreDistForm.selected'");
        sForm.setDistrId(distrId);


    }
    public static ActionErrors clearSelectBox(HttpServletRequest request,
                                              ActionForm form)
            throws Exception
    {
        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm  pForm = (StoreWorkflowDetailForm ) form;
        pForm.setSelectedLines(new String[0]);
        return ae;
    }

    public static void setLastRuleAction(StoreWorkflowDetailForm sForm, String action) {
        sForm.setLastRuleAction(action);
    }
    public static void setLastAction(StoreWorkflowDetailForm sForm, String action) {
        sForm.setLastAction(action);
    }
    public static ActionErrors setSelectedEmailUser(HttpServletRequest request, StoreWorkflowDetailForm sForm) {
        ActionErrors ae = new ActionErrors();
        String  emailUserName="";
        int emailUserID=0;
        StoreWorkflowDetailForm pForm = (StoreWorkflowDetailForm) sForm;
        UserDataVector userDV=pForm.getUserFilter();

        if(userDV!=null &&userDV.size()>0)
        {

            emailUserName=((UserData)userDV.get(0)).getFirstName()+" "+((UserData)userDV.get(0)).getLastName()+" "+((UserData)userDV.get(0)).getUserTypeCd();
            emailUserID=((UserData)userDV.get(0)).getUserId();

        }

        {
            pForm.setEmailUserId(emailUserID);
            pForm.setEmailUserName(emailUserName);
        }
        return ae;
    }


    public static void resetEmailUser(HttpServletRequest request, StoreWorkflowDetailForm sForm) {
        sForm.setEmailUserId(0);
        sForm.setEmailUserName("");
    }

    public static ActionErrors initAccountAndGetWorkflow(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session=request.getSession();
        ActionErrors ae=new ActionErrors();
        String wId=request.getParameter("searchField");
        String aId=request.getParameter("accountId");

        if(wId==null)   ae.add("site",new ActionError("Site Error: the workflow id was not specified."));
        if(aId==null)   ae.add("site",new ActionError("Site Error: the account id was not specified."));
        if (ae.size()>0) return ae;

       StoreAccountMgrLogic.getDetail(request,null);
       ae= detail(request,null);
       return ae;

    }

    public static ActionErrors freightHandlertRule(HttpServletRequest request, ActionForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setApplyRuleGroupId(0);
        sForm.setRuleSeq(ruleSeq);
        sForm.setTotalValue("");

        // vvv 4283
        if(ruleSeq==0) {
            sForm.setDistId("");
            sForm.setDistName("");
            sForm.setStateList("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV =
                (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER.equals(wrjVw.getRuleTypeCd())
                    && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setTotalValue(Utility.strNN(wrD.getRuleExpValue()));
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        return ae;
    }

    public static ActionErrors rushOrderRule(HttpServletRequest request, ActionForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);

        sForm.setApplyRuleGroupId(0);
        sForm.setRuleSeq(ruleSeq);
        sForm.setTotalValue("");

        // vvv 4283
        if(ruleSeq==0) {
            sForm.setDistId("");
            sForm.setDistName("");
            sForm.setStateList("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");
        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER.equals(wrjVw.getRuleTypeCd())
                    && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setTotalValue(Utility.strNN(wrD.getRuleExpValue()));
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        return ae;
    }
    
    public static ActionErrors workOrderExcludedFromBudget(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) pForm;
        HttpSession session = request.getSession();

        String ruleSeqS = request.getParameter("ruleSeq");
        int ruleSeq = Integer.parseInt(ruleSeqS);
        sForm.setRuleSeq(ruleSeq);
        sForm.setApplyRuleGroupId(0);

        if (ruleSeq == 0) {
            sForm.setTotalExp("");
            sForm.setTotalValue("");
            sForm.setRuleAction("");
            sForm.setRuleGroup("");
            return ae;
        }

        WorkflowRuleJoinViewVector wrjVwV = (WorkflowRuleJoinViewVector) session.getAttribute("Workflow.rulesjoin.vector");

        for (int ii = 0; ii < wrjVwV.size(); ii++) {
            WorkflowRuleJoinView wrjVw = (WorkflowRuleJoinView) wrjVwV.get(ii);
            if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET.equals(wrjVw.getRuleTypeCd()) && ruleSeq == wrjVw.getRuleSeq()) {
                setRuleAssoc(request, sForm, wrjVw);
                WorkflowRuleDataVector wrDV = wrjVw.getRules();
                WorkflowRuleData wrD = (WorkflowRuleData) wrDV.get(0);
                sForm.setTotalExp(wrD.getRuleExp());
                sForm.setTotalValue(wrD.getRuleExpValue());
                sForm.setRuleAction(wrD.getRuleAction());
                sForm.setApproverGroupId(wrD.getApproverGroupId());
                sForm.setEmailGroupId(wrD.getEmailGroupId());
                sForm.setRuleGroup(wrD.getRuleGroup());
                sForm.setWarningMessage(wrD.getWarningMessage());
                parseNextRule(request, sForm, wrD.getNextActionCd());
                break;
            }
        }
        getRuleUsers(request, sForm);
        return ae;
    }
}





