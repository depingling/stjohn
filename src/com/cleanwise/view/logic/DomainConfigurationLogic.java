package com.cleanwise.view.logic;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.StringUtils;
import com.cleanwise.view.forms.DomainAdmMgrForm;
import com.cleanwise.view.forms.DomainAdmConfigurationForm;
import com.cleanwise.view.forms.DomainAdmDetailForm;
import com.cleanwise.view.forms.SiteMgrConfigForm;
import com.cleanwise.view.forms.SiteMgrDetailForm;

import java.rmi.RemoteException;
import java.util.Iterator;


public class DomainConfigurationLogic {

    private static String className="DomainAdmMgrLogic";

    public static ActionErrors createNewDomain(HttpServletRequest request, ActionForm dForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

	   	 try {
//		     initFormVectors(request);
	   		 DomainAdmDetailForm sForm = new DomainAdmDetailForm();
		     sForm.setId("0");
		     sForm.setName("");
		     sForm.setLongDescription("");
		     sForm.setSSLName("");
		     sForm.setStatusCd(null);
		     session.setAttribute("DOMAIN_ADM_DETAIL_FORM", sForm);
		 }
   	 
	 catch (Exception e ) {
	    ae.add("domain",
		   new ActionError
		   ("error.simpleGenericError", "error:" + e.getMessage()));
	 }

        return ae;

    }

    public static ActionErrors saveNewDomain(HttpServletRequest request, ActionForm dForm) throws Exception {
        DomainAdmDetailForm sForm = (DomainAdmDetailForm)dForm;
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        try {
//            initFormVectors(request);
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            if (sForm != null) {
                // Verify the form information submitted.
                if (sForm.getName().length() == 0) {
                    ae.add("name",
    				  new ActionError("variable.empty.error",
    						  "Name"));
                }
                if (sForm.getStatusCd().length() == 0) {
                    ae.add("statusCd",
    				  new ActionError("variable.empty.error",
    						  "Status"));
                }
            }            
            if (ae.size() > 0) {
                // Report the errors to allow for edits.
                return ae;
            }    
            APIAccess factory = new APIAccess();
            Store storeBean = factory.getStoreAPI();
            
            BusEntityData busEntData = BusEntityData.createValue();
            int sid = Integer.parseInt(sForm.getId());
        	DomainData someCurrentDomainData = null;
            if (sid == 0) {
                busEntData.setAddBy(appUser.getUserName());            	
            } else {
            	someCurrentDomainData = storeBean.getDomain(sid); 
                busEntData.setAddBy(someCurrentDomainData.getBusEntity().getAddBy());
                busEntData.setAddDate(someCurrentDomainData.getBusEntity().getAddDate());
            }
            busEntData.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.DOMAIN_NAME); 
            busEntData.setLocaleCd("unk");                
            busEntData.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
            
            busEntData.setBusEntityId(Integer.parseInt(sForm.getId()));
            busEntData.setBusEntityStatusCd(sForm.getStatusCd());
            busEntData.setLongDesc(sForm.getLongDescription());
            busEntData.setModBy(appUser.getUserName());
            busEntData.setShortDesc(sForm.getName());

            PropertyData SSLName = PropertyData.createValue();
            SSLName.setValue(sForm.getSSLName());
            PropertyData someCurrentSSLNamePropertyData = null;
            if (sid != 0) {
            	someCurrentSSLNamePropertyData = storeBean.getSSLName(sid);
            	SSLName.setAddBy(someCurrentSSLNamePropertyData.getAddBy());
            	SSLName.setAddDate(someCurrentSSLNamePropertyData.getAddDate());
            	SSLName.setPropertyId(someCurrentSSLNamePropertyData.getPropertyId());
            }
            
            BusEntityAssocData busEntAssocData = BusEntityAssocData.createValue();
            String defaultStoreName = "";
            
            DomainData domainData = new DomainData(busEntData, SSLName, busEntAssocData, busEntAssocData, defaultStoreName);
            
            domainData = storeBean.updateDomain(domainData);
            sForm.setId(domainData.getBusEntity().getBusEntityId());
            
            initDetails(request, sForm);
            //remove old and add new one to make sure our lists in sync
            DomainDataVector ddV = new DomainDataVector();
            ddV.add(domainData);
            session.setAttribute("domain.data.vector", ddV);
            return ae;

         } catch (Exception e ) {
            e.printStackTrace();
            ae.add("domain",
                    new ActionError
                    ("error.simpleGenericError", "error:" + e.getMessage()));
        }
        return ae;
    
    }


    public static ActionErrors editDomain(HttpServletRequest request, ActionForm dForm, String domId) throws Exception {
        return null; ///ae;
    }    

    
    public static ActionErrors getAllFreightHandlers(HttpServletRequest request, ActionForm form)   {

        HttpSession session = request.getSession();
        session.removeAttribute("freight_handler.vector");
        request.removeAttribute("freight_handler.vector");
        ActionErrors ae = new ActionErrors();
        try {
            APIAccess factory = new APIAccess();
            Distributor distBean = factory.getDistributorAPI();
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            }
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
            FreightHandlerViewVector fhv = distBean.getFreightHandlersByCriteria(crit);

            if ( null == fhv) { fhv = new FreightHandlerViewVector(); }
            session.setAttribute("freight_handler.vector", fhv);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ae;
   }

    public static ActionErrors init(HttpServletRequest request, ActionForm dForm) throws Exception {

        BusEntityDataVector domainOptions;
        PropertyDataVector domainVector;
        ApplicationDomainNameData domainData;

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

         if (!(dForm instanceof DomainAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "init not supporting form class:  " + dForm.getClass()));
            return ae;
        }

        dForm=new DomainAdmMgrForm();

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
        }

        ListService lsvc = factory.getListServiceAPI();
        if (lsvc == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Api access"));
            return ae;
        }

        try {
            Store storeBean = factory.getStoreAPI();
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_ID);
            DomainDataVector ddV = storeBean.getAllDomains(Account.ORDER_BY_ID);

            if (session.getAttribute("domain.status.vector") == null) {
            	RefCdDataVector statusv =
                    lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
    	    session.setAttribute("domain.status.vector", statusv);
    	}            

            session.setAttribute("domain.data.vector", ddV);
            
        	RefCdDataVector domainStatucCds = lsvc.getRefCodesCollection("DOMAIN_STATUS_CD");
            session.setAttribute("Domain.status.cds", domainStatucCds);
            session.setAttribute("DOMAIN_ADM_CONFIG_FORM", dForm);
        } catch (RemoteException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
                return ae;
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("error.simpleGenericError", "Problem init the domain for adminportal:" +
                                " Error: " + e));

            }
        }
        return ae;
    }

    public static ActionErrors configurateDomain(HttpServletRequest request, ActionForm dForm) throws Exception {
    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        DomainAdmConfigurationForm configForm = (DomainAdmConfigurationForm)dForm;
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
//        APIAccess factory = new APIAccess();
        DomainAdmDetailForm detailForm = (DomainAdmDetailForm)request.getSession().getAttribute("DOMAIN_ADM_DETAIL_FORM");
//        int accountId = Integer.parseInt(detailForm.getAccountId());
//        int siteId = Integer.parseInt(detailForm.getId());
        int domainId = detailForm.getIntId();
        
        
        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
        }
        ListService lsvc = factory.getListServiceAPI();
        if (lsvc == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Api access"));
            return ae;
        }
        try {
            Store storeBean = factory.getStoreAPI();

//            User userBean = factory.getUserAPI();

            //Collects all users eligible
//        UserDataVector uv =
//        	storeBean.getStoresCollectionByBusEntity(accountId, null);
//        request.getSession().setAttribute("site.users.vector", uv);

            //Collects all already configured users
//        UserDataVector users =
//        userBean.getUsersCollectionByBusEntity(siteId, null);            
            
            DomainDataVector ddV = (DomainDataVector)session.getAttribute("domain.data.vector"); 
            	
            session.setAttribute("DOMAIN_ADM_CONFIGURATION_FORM", dForm);
        } catch (Exception e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
                return ae;
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("error.simpleGenericError", "Problem configurateDomain for adminportal:" +
                                " Error: " + e));
            }
        }
        return ae;
    }

    public static void getAllConfig(HttpServletRequest request,
		    ActionForm form)
throws Exception {
//initConfig(request, form);
HttpSession session = request.getSession();
SiteMgrConfigForm configForm = (SiteMgrConfigForm)form;
String configType = configForm.getConfigType();
String fieldValue = configForm.getSearchField();
String fieldSearchType = configForm.getSearchType();
APIAccess factory = new APIAccess();
SiteMgrDetailForm detailForm = (SiteMgrDetailForm)
request.getSession().getAttribute("SITE_DETAIL_FORM");
int accountId = Integer.parseInt(detailForm.getAccountId());
int siteId = Integer.parseInt(detailForm.getId());

if (configType.equals("Users")) {
User userBean = factory.getUserAPI();

    //Collects all users eligible
UserDataVector uv =
userBean.getUsersCollectionByBusEntity(accountId, null);
request.getSession().setAttribute("site.users.vector", uv);

    //Collects all already configured users
UserDataVector users =
userBean.getUsersCollectionByBusEntity(siteId, null);


String[] selectIds = new String[users.size()];
IdVector userIds = new IdVector();

Iterator userI = users.iterator();
int i = 0;
while (userI.hasNext()) {
Integer id =
    new Integer(((UserData)userI.next()).getUserId());
userIds.add(id);
selectIds[i++] = new String(id.toString());
}

// the currently associated users are checked/selected
configForm.setSelectIds(selectIds);

// list of all associated user ids, in the update this
// will be compared with the selected
session.setAttribute("site.assoc.user.ids", userIds);

} else if (configType.equals("Catalog")) {
CatalogInformation cati = factory.getCatalogInformationAPI();

// Get the current site catalog
CatalogData siteCatalog = cati.getSiteCatalog(siteId);
if (siteCatalog != null) {
String catalogId = String.valueOf(siteCatalog.getCatalogId());
configForm.setCatalogId(catalogId);
configForm.setOldCatalogId(catalogId);
} else {
configForm.setCatalogId("0");
configForm.setOldCatalogId("0");
}

CatalogDataVector catalogs =
cati.getCatalogsByAccountId(accountId);

session.setAttribute("site.catalogs.vector", catalogs);
}

return;
}    
    
   
    public static ActionErrors initDetails(HttpServletRequest request,ActionForm dForm) throws Exception {
    	
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

    	try {
//   	     initFormVectors(request);
   	     String rid = request.getParameter("searchField");
   	     if ( rid == null ) {
   		 ae.add("domain",
   			new ActionError
   			("error.simpleGenericError", "error: id missing"));
   		 return ae;
   	     }
   	     int did = Integer.parseInt(rid);
   	     DomainDataVector dDV = (DomainDataVector)
   		     session.getAttribute("domain.data.vector");
   	     if ( null == dDV) {
   		    ae.add("domain",
   			   new ActionError
   			      ("error.simpleGenericError", "error: no domains"));
   		    return ae;
   	     }
   	     Iterator itr = dDV.iterator();
   	     while (itr.hasNext()) {
   	    	DomainData domain = (DomainData) itr.next();
                    if ( domain.getBusEntity().getBusEntityId() == did ) {
                    	DomainAdmDetailForm domAdmDetailForm = (DomainAdmDetailForm) dForm;
   		     domAdmDetailForm.setId(rid);
   		     domAdmDetailForm.setName(domain.getBusEntity().getShortDesc());
   		     domAdmDetailForm.setStatusCd(domain.getBusEntity().getBusEntityStatusCd());
   		     domAdmDetailForm.setLongDescription(domain.getBusEntity().getLongDesc());
   		     domAdmDetailForm.setSSLName(domain.getSSLName().getValue());
   	         session.setAttribute("DOMAIN_ADM_DETAIL_FORM", domAdmDetailForm);
   	         
   		     return ae;
   		 }
   	     }

	   	 }
	   	 catch (Exception e ) {
	   	    ae.add("distributor",
	   		   new ActionError
	   		   ("error.simpleGenericError", "error:" + e.getMessage()));
	   	 }

        return ae;
    }

    private static TaskDetailViewVector getFilteredTaskByTypeCd(TaskDetailViewVector taskDetails,String typeCd) {
        TaskDetailViewVector filteredTasks= new TaskDetailViewVector();
        if(!taskDetails.isEmpty()&&typeCd!=null){
            Iterator  it=taskDetails.iterator();
            while(it.hasNext()){
                TaskDetailView task = (TaskDetailView) it.next();
                if(typeCd.equals(task.getTaskData().getTaskTypeCd())){
                    filteredTasks.add(task);
                }
            }
        }
        return filteredTasks;
    }


}
