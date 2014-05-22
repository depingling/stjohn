
package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.FreightTable;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.value.FreightTableCriteriaDescData;
import com.cleanwise.service.api.value.FreightTableCriteriaDescDataVector;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.FreightTableDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.view.forms.ContractMgrDetailForm;
import com.cleanwise.view.forms.FreightTableMgrDetailForm;
import com.cleanwise.view.forms.FreightTableMgrSearchForm;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;

/**
 * <code>FreightTableMgrLogic</code> implements the logic needed to
 * manipulate freightTable records.
 *
 * @author durval
 */
public class FreightTableMgrLogic {

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

	searchAll(request, form);    
	return;
    }
  
  
    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void search(HttpServletRequest request,
			      ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	FreightTableMgrSearchForm sForm = (FreightTableMgrSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
	FreightTable freightTableEjb   = factory.getFreightTableAPI();

	String searchField = sForm.getSearchField();
	String searchType = sForm.getSearchType();
    
	FreightTableDataVector freightTables = new FreightTableDataVector();
	if (null != searchType && "id".equals(searchType) && null != searchField && ! "".equals(searchField)) {
            freightTables = new FreightTableDataVector();
            FreightTableData freightTableData = freightTableEjb.getFreightTable(Integer.parseInt(searchField));	    
            if(null != freightTableData) {
                freightTables.add(freightTableData);
            }
	}
	else if (null != searchType && "nameContains".equals(searchType) && null != searchField && ! "".equals(searchField)) {
	    freightTables = freightTableEjb.getFreightTableByName(searchField, FreightTable.CONTAINS_IGNORE_CASE);            
	}
	else if (null != searchType && "nameBegins".equals(searchType) && null != searchField && ! "".equals(searchField)) {
	    freightTables = freightTableEjb.getFreightTableByName(searchField, FreightTable.BEGINS_WITH_IGNORE_CASE);            
	}
    
	else {
	    freightTables  = freightTableEjb.getAllFreightTables();
	}
    
	sForm.setResultList(freightTables);
	sForm.setSearchField(searchField);
	sForm.setSearchType(searchType);
    }

  
    /**
     * <code>searchAll</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchAll(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	FreightTableMgrSearchForm sForm = (FreightTableMgrSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
	FreightTable freightTableEjb   = factory.getFreightTableAPI();

	FreightTableDataVector freightTables = new FreightTableDataVector();
	freightTables  = freightTableEjb.getAllFreightTables();
        
	sForm.setResultList(freightTables);
    }
  
    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	FreightTableMgrSearchForm sForm = (FreightTableMgrSearchForm)form;
	if (sForm == null) {
	    return;
	}
	FreightTableDataVector freightTables = 
	    (FreightTableDataVector)sForm.getResultList();
	if (freightTables == null) {
	    return;
	}

	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(freightTables, sortField);
    }


    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */    
    public static void initConstantList(HttpServletRequest request)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }    

        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == session.getAttribute("FreightTable.status.vector")) {
	    RefCdDataVector statusv =
		listServiceEjb.getRefCodesCollection("FREIGHT_TABLE_STATUS_CD");
	    session.setAttribute("FreightTable.status.vector", statusv);
	}
        
        if (null == session.getAttribute("FreightTable.type.vector")) {
	    RefCdDataVector typev =
		listServiceEjb.getRefCodesCollection("FREIGHT_TABLE_TYPE_CD");
	    session.setAttribute("FreightTable.type.vector", typev);
	}
        
        
    }
        

    /**
     * <code>AddFreightTable</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */        
    public static void addFreightTable(
				   HttpServletRequest request,
				   ActionForm form)
	throws Exception {

	FreightTableMgrDetailForm freightTable = (FreightTableMgrDetailForm) form;

	freightTable = new FreightTableMgrDetailForm();
    
	HttpSession session = request.getSession();
        
        FreightTableCriteriaDescDataVector newCriterias = new FreightTableCriteriaDescDataVector();
        for (int i = 0; i < freightTable.getCriteriaLength(); i++) {
                newCriterias.add(FreightTableCriteriaDescData.createValue());
        }
        freightTable.setCriteriaList(newCriterias);                

	session.setAttribute("FREIGHT_TABLE_DETAIL_FORM", freightTable);
	session.removeAttribute("FreightTable.id");
        
	//initialize the comstants lists for states and contries
	initConstantList(request);    

    }
 
  
    /**
     * <code>editFreightTable</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param freightTableId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static void editFreightTable(
				    HttpServletRequest request,
				    ActionForm form,
				    String freightTableId)
	throws Exception {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
	FreightTable freightTableEjb = factory.getFreightTableAPI();
	if( null == freightTableId || "".equals(freightTableId)) {
	    freightTableId = (String)session.getAttribute("FreightTable.id");
	}
	FreightTableData detail = freightTableEjb.getFreightTable(Integer.parseInt(freightTableId));
        FreightTableCriteriaDescDataVector criterias = 
                                  freightTableEjb.getAllFreightTableCriteriaDescs(Integer.parseInt(freightTableId));  
        
	FreightTableMgrDetailForm detailForm = (FreightTableMgrDetailForm) form;
	detailForm.setDetail(detail);
        detailForm.setOrgCriteriaList(criterias);        
        detailForm.setCriteriaLength(10);        
        
        FreightTableCriteriaDescDataVector newCriterias = new FreightTableCriteriaDescDataVector();
        for(int i = 0; i < criterias.size(); i++) {
            FreightTableCriteriaDescData oldCriD = (FreightTableCriteriaDescData)criterias.get(i);
            FreightTableCriteriaDescData newCriD = FreightTableCriteriaDescData.createValue();
            newCriD.setFreightTableId(oldCriD.getFreightTableId());
            newCriD.setFreightTableCriteriaId(oldCriD.getFreightTableCriteriaId());
            newCriD.setLowerAmount(oldCriD.getLowerAmount());
            newCriD.setHigherAmount(oldCriD.getHigherAmount());
            newCriD.setFreightAmount(oldCriD.getFreightAmount());
            newCriD.setHandlingAmount(oldCriD.getHandlingAmount());
            newCriD.setAddBy(oldCriD.getAddBy());
            newCriD.setAddDate(oldCriD.getAddDate());
            newCriterias.add(newCriD);            
        }
        
        if(criterias.size() < detailForm.getCriteriaLength()) {
            for (int i = criterias.size(); i < detailForm.getCriteriaLength(); i++) {
                newCriterias.add(FreightTableCriteriaDescData.createValue());
            }            
        }
        else {
            detailForm.setCriteriaLength(newCriterias.size());
        }
        detailForm.setCriteriaList(newCriterias);                
        
	session.setAttribute("FreightTable.id", freightTableId);
    
	//initialize the comstants lists for states and contries
	initConstantList(request);
    
    }


    
    /**
     * <code>AddMoreCriteria</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */        
    public static void addMoreCriteria(
				   HttpServletRequest request,
				   ActionForm form)
	throws Exception {

	FreightTableMgrDetailForm freightTable = (FreightTableMgrDetailForm) form;
    
	HttpSession session = request.getSession();

        freightTable.setCriteriaLength(freightTable.getCriteriaLength() + 5 );
        FreightTableCriteriaDescDataVector newCriterias = freightTable.getCriteriaList();
        
        for(int i = newCriterias.size(); i < freightTable.getCriteriaLength(); i++) {
            newCriterias.add(FreightTableCriteriaDescData.createValue());
        }
        
        freightTable.setCriteriaList(newCriterias);                

    }
 
  
    
    /**
     * <code>saveFreightTable</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */  
    public static ActionErrors saveFreightTable(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
            
	FreightTableMgrDetailForm detailForm = (FreightTableMgrDetailForm) form;

	int freightTableId = detailForm.getDetail().getFreightTableId();

	// Get a reference to the admin facade.
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
	FreightTable freightTableEjb = factory.getFreightTableAPI();

	FreightTableData detail = detailForm.getDetail();

        FreightTableData newDetail = FreightTableData.createValue();    
	if(0 != detail.getFreightTableId()) {        
	    newDetail = freightTableEjb.addFreightTable(detail);
            session.setAttribute("FreightTable.id", String.valueOf(newDetail.getFreightTableId()));
	}
	else {
            newDetail = freightTableEjb.addFreightTable(detail);
	    session.setAttribute("FreightTable.id", String.valueOf(newDetail.getFreightTableId()));
            detailForm.setDetail(newDetail);
	}

        lUpdateErrors = updateCriterias(request, detailForm, newDetail.getFreightTableId());
        return lUpdateErrors;
    }
  

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors updateCriterias
	(HttpServletRequest request, ActionForm form, int pFreightTableId)
	throws Exception {

        ActionErrors lErrors = new ActionErrors();

        HttpSession session = request.getSession();

        FreightTableData fcd = null;
        FreightTableMgrDetailForm sForm =
	    (FreightTableMgrDetailForm)form;

        if (sForm != null) {
            fcd = sForm.getDetail();
        }

        if (fcd == null || lErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lErrors;
        }

        String cuser = (String) session.getAttribute(Constants.USER_NAME);
	// Get a reference to the admin facade.
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    

        FreightTable freightTableEjb = factory.getFreightTableAPI();

        int id = fcd.getFreightTableId();
        
        // Get the updated information.
        FreightTableCriteriaDescDataVector idv = sForm.getCriteriaList();        
        FreightTableCriteriaDescDataVector idvorig = sForm.getOrgCriteriaList();
        
        FreightTableCriteriaDescDataVector existedCriterias = new FreightTableCriteriaDescDataVector();
        FreightTableCriteriaDescDataVector newCriterias = new FreightTableCriteriaDescDataVector();
        for(int i = 0; i < idv.size(); i++ ) {
            FreightTableCriteriaDescData criD = (FreightTableCriteriaDescData)idv.get(i);
            if( 0 != criD.getFreightTableCriteriaId()) {
                existedCriterias.add(criD);
            }
            else {
                newCriterias.add(criD);
            }            
        }
        
        
        for (int i = 0 ; i < idvorig.size(); i++) {
            FreightTableCriteriaDescData oldo = (FreightTableCriteriaDescData) idvorig.get(i);
            FreightTableCriteriaDescData newo = null;
            for(int j = 0; j < existedCriterias.size(); j++) {
                FreightTableCriteriaDescData existedD = (FreightTableCriteriaDescData) existedCriterias.get(j);
                if(existedD.getFreightTableCriteriaId() == oldo.getFreightTableCriteriaId()) {
                    newo = existedD;
                    break;
                }                
            }

            if ( null != newo ) {
                if ( "".equals(newo.getLowerAmount().trim()) && "".equals(newo.getHigherAmount().trim()) && 
                    "".equals(newo.getFreightAmount().trim()) && "".equals(newo.getHandlingAmount().trim())) {
                    freightTableEjb.removeFreightTableCriteria(newo);        
                }
                else if ( !(newo.getLowerAmount().trim()).equals(oldo.getLowerAmount()) ||
                    !(newo.getHigherAmount().trim()).equals(oldo.getHigherAmount()) ||
                    !(newo.getFreightAmount().trim()).equals(oldo.getFreightAmount()) || !(newo.getHandlingAmount().trim()).equals(oldo.getHandlingAmount()) ) {
                    freightTableEjb.updateFreightTableCriteria(newo, cuser);
                }
            }
        }
        
        for (int i = 0; i < newCriterias.size(); i++) {
            FreightTableCriteriaDescData newo = (FreightTableCriteriaDescData) newCriterias.get(i);
            if( !"".equals(newo.getLowerAmount().trim()) || !"".equals(newo.getHigherAmount().trim()) || 
                    !"".equals(newo.getFreightAmount().trim()) || !"".equals(newo.getHandlingAmount().trim())) {
                freightTableEjb.addFreightTableCriteria(pFreightTableId, newo, cuser);    
            }
        }
        
        return lErrors;
    }

    
    /**
     * <code>resetFromForm</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */        
    public static void resetFromForm(
				   HttpServletRequest request,
				   ActionForm form)
	throws Exception {

	FreightTableMgrDetailForm freightTable = (FreightTableMgrDetailForm) form;

	HttpSession session = request.getSession();
        ContractMgrDetailForm contractDetailForm = (ContractMgrDetailForm)session.getAttribute("CONTRACT_DETAIL_FORM");
        
        if (null != contractDetailForm ) {
            contractDetailForm.setFreightTableId(String.valueOf(freightTable.getDetail().getFreightTableId()));
            contractDetailForm.setFreightTableName(freightTable.getDetail().getShortDesc());
            
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            
            FreightTable freightTableEjb = factory.getFreightTableAPI();
        
            FreightTableDataVector freightTableList = freightTableEjb.getAllFreightTables();
            session.setAttribute("FreightTable.vector", freightTableList);
        }
        
    }
 
       
}
