
package com.cleanwise.view.logic;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Schedule;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PhysicalInventoryPeriod;
import com.cleanwise.service.api.util.PhysicalInventoryPeriodArray;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountView;
import com.cleanwise.service.api.value.AccountViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.DeliveryScheduleView;
import com.cleanwise.service.api.value.DeliveryScheduleViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ScheduleData;
import com.cleanwise.service.api.value.ScheduleDetailData;
import com.cleanwise.service.api.value.ScheduleDetailDataVector;
import com.cleanwise.service.api.value.ScheduleJoinView;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.CorporateScheduleMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 * <code>CorporateScheduleLogic</code> implements the logic needed to
 * place orders scheduled for automatic processing.
 *
 *@author Veronika Denega
 */
public class CorporateScheduleMgrLogic {

	private final static String DATE_FORMAT = "MM/dd/yyyy";

	public static ActionErrors initSearch(HttpServletRequest request,
			CorporateScheduleMgrForm pForm)
	{
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		int storeId = Integer.parseInt((String) session.getAttribute("Store.id"));
		pForm.setStoreId(storeId);
		return ae;
	}

	//***********************************************************************
	public static ActionErrors clearDetail(HttpServletRequest request,
			CorporateScheduleMgrForm pForm)
	{
		ActionErrors ae = new ActionErrors();
		pForm.setAcctBusEntityList(null);
		return ae;
	}
	//***********************************************************************
	public static ActionErrors searchSchedule(HttpServletRequest request,
			CorporateScheduleMgrForm pForm) throws Exception
			{
		ActionErrors ae = new ActionErrors();        
		checkDate(ae, pForm.getSearchDateFrom(), "From Date");
		checkDate(ae, pForm.getSearchDateTo(), "To Date");
		if (ae.size() > 0) {
			return ae;
		}

		HttpSession session = request.getSession();
		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

		Schedule scheduleEjb = null;
		try {
			scheduleEjb = factory.getScheduleAPI();
		} catch(com.cleanwise.service.api.APIServiceAccessException exc) {
			exc.printStackTrace();
			ae.add("error",new ActionError("error.systemError","No Schedule Ejb access"));
			return ae;
		}

		int scheduleId = -1;
		String searchName = Utility.isSet(pForm.getSearchName()) ? pForm.getSearchName().trim() : null;  
		Date dateFrom = null;
		Date dateTo = null;
		boolean startWithFl = false;


		String searchType = pForm.getSearchType();        
		if ("id".equals(searchType) && searchName != null) {
			try {
				scheduleId = Integer.parseInt(searchName);
			} catch(NumberFormatException exc) {}
			if(scheduleId<=0) {
				ae.add("error",new ActionError("error.systemError","Wrong scheduleId format: "+searchName));
				return ae;
			}
		} else {
			if ("nameBegins".equals(searchType)) {
				startWithFl = true;
			}
			if (Utility.isSet(pForm.getSearchDateFrom())){
				dateFrom = Utility.parseDate(pForm.getSearchDateFrom(), DATE_FORMAT, false);
			}

			if (Utility.isSet(pForm.getSearchDateTo())){
				dateTo = Utility.parseDate(pForm.getSearchDateTo(), DATE_FORMAT, false);
			}

		}  

		pForm.setScheduleList(null);
		try {
			DeliveryScheduleViewVector dsVv = null;
			if (scheduleId > 0){
				dsVv = scheduleEjb.getCorporateSchedules(pForm.getStoreId(), scheduleId);
			}else{
				dsVv = scheduleEjb.getCorporateSchedules(pForm.getStoreId(), searchName, dateFrom, dateTo, startWithFl);
			}
			pForm.setScheduleList(dsVv);
		} catch (Exception exc) {
			ae.add("error",new ActionError("error.systemError",exc.getMessage()));
			return ae;
		}

		return ae;
			}

	private static void checkDate(ActionErrors errors, String dateValue, String dateParamName) {
		if(!Utility.isSet(dateValue)){
			return;
		}
		Date val = null;
		try {
			val = Utility.parseDate(dateValue, DATE_FORMAT, true);
		} catch (Exception e) {
			errors.add("Checking parameters", new ActionMessage("error.badDateFormat", dateParamName));
		}
	}

	//***********************************************************************
	public static ActionErrors detail(HttpServletRequest request,
			CorporateScheduleMgrForm pForm)
	{
		ActionErrors ae = detail(request, pForm,0);
		return ae;
	}

	public static ActionErrors detail(HttpServletRequest request,
			CorporateScheduleMgrForm pForm, int scheduleId)
	{
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		pForm.setAcctBusEntityList(null);
		if(scheduleId==0) {
			String scheduleIdS = request.getParameter("scheduleId");
			if (scheduleIdS == null)
				scheduleIdS = pForm.getScheduleData().getScheduleId()+"";
			try {
				scheduleId = Integer.parseInt(scheduleIdS);
			} catch(NumberFormatException exc) {}
			if(scheduleId<=0) {
				ae.add("error",new ActionError("error.systemError","Wrong scheduleId format: "+scheduleIdS));
				return ae;
			}
		}
		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		if(factory==null) {
			ae.add("error",new ActionError("error.systemError","No Ejb access"));
			return ae;
		}

		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		if(appUser == null) {
			String errorMessage = "No " + Constants.APP_USER + " session object found";
			ae.add("error", new ActionError("error.systemError", errorMessage));
			return ae;
		}

		Schedule scheduleEjb = null;
		try {
			scheduleEjb = factory.getScheduleAPI();
		} catch(com.cleanwise.service.api.APIServiceAccessException exc) {
			exc.printStackTrace();
			ae.add("error",new ActionError("error.systemError","No Schedule Ejb access"));
			return ae;
		}
		ScheduleJoinView scheduleJoin = null;
		try {
			scheduleJoin = scheduleEjb.getScheduleById(scheduleId);
		} catch (Exception exc) {
			ae.add("error",new ActionError("error.systemError",exc.getMessage()));
			return ae;
		}
		pForm.setScheduleJoinData(scheduleJoin);
		ae = prepareScheduele(pForm);
		session.setAttribute("CORPORATE_SCHEDULE_FORM",pForm);
		if(ae.size()>0) return ae;
		return ae;
	}
//	--------------------------------------------------------------------------------
	private static ActionErrors prepareScheduele(CorporateScheduleMgrForm pForm)
	{
		ActionErrors ae = new ActionErrors();
		ScheduleJoinView scheduleJoin = pForm.getScheduleJoinData();
		ScheduleData sD = scheduleJoin.getSchedule();
		ScheduleDetailDataVector scheduleDetailDV = scheduleJoin.getScheduleDetail();
		PhysicalInventoryPeriodArray physicalInvPeriods = null;
		String alsoDates = "";
		String cutOffDay = "";
		String cutOffTime = "";
		String invCartAccessInterval="";
		physicalInvPeriods = new PhysicalInventoryPeriodArray();
		physicalInvPeriods.startLoadingItems();

		for(int ii=0; ii<scheduleDetailDV.size(); ii++) {
			ScheduleDetailData sdD = (ScheduleDetailData) scheduleDetailDV.get(ii);
			String detType = sdD.getScheduleDetailCd();
			String value = sdD.getValue();
			if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE)){
				if(alsoDates.length()>0) alsoDates += ", ";
				alsoDates += value;
			} else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY)){
				if(cutOffDay.length()>0) {
					ae.add("error",new ActionError("error.simpleGenericError","Found extra cut off day: "+value));
				} else {
					cutOffDay = value;
				}
			} else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME)){
				if(cutOffTime.length()>0) {
					ae.add("error",new ActionError("error.simpleGenericError","Found extra cut off time: "+value));
				} else {
					cutOffTime = value;
				}
			} else if(detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL)){
				if(invCartAccessInterval.length()>0) {
					ae.add("error.systemError",new ActionError("error.simpleGenericError","Found extra "+value));
				} else {
					invCartAccessInterval = value;
				}
			} else if (detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE) || 
					detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE) || 
					detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE)) {
				if (physicalInvPeriods != null) {
					physicalInvPeriods.addItem(detType, value);
				}
			}
		}

		if (physicalInvPeriods != null) {
			if (physicalInvPeriods.finishLoadingItems()) {
				pForm.setPhysicalInventoryPeriods(physicalInvPeriods.toString());
			} else {
				String errorMessage = "The data with the physical inventory period is invalid. ";
				ae.add("error", new ActionError("error.systemError", errorMessage));
			}
		}

		pForm.setInvCartAccessInterval(invCartAccessInterval);
		pForm.setCutoffTime(cutOffTime);
		pForm.setAlsoDates(alsoDates);
		return ae;
	}
	//******************************************************************************
	private static ActionErrors prepareSchedule (HttpServletRequest request, CorporateScheduleMgrForm pForm)
	{
		ActionErrors ae = new ActionErrors();
		return ae;
	}
	//-------------------------------------------------------------------------------
	private static void initFields(CorporateScheduleMgrForm pForm) {
		pForm.setCutoffTime("13:00");
		pForm.setInvCartAccessInterval("");
		pForm.setAlsoDates("");
		pForm.setPhysicalInventoryPeriods("");
	}

	//***********************************************************************
	public static ActionErrors save(HttpServletRequest request,
			CorporateScheduleMgrForm pForm)
	{

		ActionErrors ae = new ActionErrors();
		//Save shcedule
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		if(appUser==null) {
			ae.add("error",new ActionError("error.systemError","No " + Constants.APP_USER+"session object found"));
			return ae;
		}
		UserData user = appUser.getUser();

		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		if(factory==null) {
			ae.add("error",new ActionError("error.systemError","No Ejb access"));
			return ae;
		}

		Schedule scheduleEjb = null;
		try {
			scheduleEjb = factory.getScheduleAPI();
		} catch(com.cleanwise.service.api.APIServiceAccessException exc) {
			exc.printStackTrace();
			ae.add("error",new ActionError("error.systemError","No Schedule Ejb access"));
			return ae;
		}

		ScheduleData schedule = pForm.getScheduleData();    
		ScheduleDetailDataVector scheduleDetailDV = new ScheduleDetailDataVector();

		ScheduleDetailData sdD = ScheduleDetailData.createValue();
		sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);
		sdD.setValue(pForm.getInvCartAccessInterval());
		scheduleDetailDV.add(sdD);

		/// Physical inventory period
		if (pForm.getPhysicalInventoryPeriods() != null && pForm.getPhysicalInventoryPeriods().trim().length() > 0) {
			PhysicalInventoryPeriodArray periodsObj = 
				PhysicalInventoryPeriodArray.parse(pForm.getPhysicalInventoryPeriods());
			ArrayList<PhysicalInventoryPeriod> periods = periodsObj.getPeriods();
			ScheduleDetailData scheduleDetail = null;
			for (int i = 0; i < periods.size(); ++i) {
				PhysicalInventoryPeriod period = periods.get(i);
				if (period == null) {
					continue;
				}
				scheduleDetail = ScheduleDetailData.createValue();
				scheduleDetail.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE);
				scheduleDetail.setValue(period.getStartDateAsString());
				scheduleDetailDV.add(scheduleDetail);

				scheduleDetail = ScheduleDetailData.createValue();
				scheduleDetail.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE);
				scheduleDetail.setValue(period.getEndDateAsString());
				scheduleDetailDV.add(scheduleDetail);

				scheduleDetail = ScheduleDetailData.createValue();
				scheduleDetail.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE);
				scheduleDetail.setValue(period.getAbsoluteFinishDateAsString());
				scheduleDetailDV.add(scheduleDetail);
			}
		}

		//Cutoff day
		sdD = ScheduleDetailData.createValue();
		sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY);
		sdD.setValue("0");
		scheduleDetailDV.add(sdD);

		sdD = ScheduleDetailData.createValue();
		sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
		sdD.setValue(pForm.getCutoffTime());
		scheduleDetailDV.add(sdD);


		schedule.setScheduleRuleCd(RefCodeNames.SCHEDULE_RULE_CD.DATE_LIST);
		schedule.setCycle(1);

		//Additional days
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String alsoDates = pForm.getAlsoDates();
		if(alsoDates!=null && alsoDates.trim().length()>0) {
			int commaInd = 0;
			while(commaInd>=0) {
				int nextCommaInd = alsoDates.indexOf(',',commaInd);
				String dateS = null;
				if(nextCommaInd>0){
					dateS = alsoDates.substring(commaInd,nextCommaInd).trim();
					commaInd = nextCommaInd+1;
				} else {
					dateS = alsoDates.substring(commaInd).trim();
					commaInd = -1;
				}
				GregorianCalendar gc = Constants.createCalendar(dateS);
				
				// check if weekend  
				Date d = gc.getTime();
				int dayOfWeek = d.getDay() + 1;				
				sdD = ScheduleDetailData.createValue();
				sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
				sdD.setValue(sdf.format(gc.getTime()));
				scheduleDetailDV.add(sdD);
			}
		}
		try {
			ScheduleJoinView scheduleJ = scheduleEjb.saveSchedule(schedule,scheduleDetailDV,user.getUserName());
			pForm.setScheduleJoinData(scheduleJ);
			schedule = scheduleJ.getSchedule();
		} catch (RemoteException exc) {
			ae.add("error",new ActionError("error.simpleGenericError",exc.getMessage()));
			return ae;
		}
		ae = detail(request,pForm,schedule.getScheduleId());
		if(ae.size()>0) return ae;
		if(ae.size()>0) {
			return ae;
		}
		return ae;
	}
	//***********************************************************************
	public static ActionErrors createNew(HttpServletRequest request,
			CorporateScheduleMgrForm pForm)
	{
		ActionErrors ae = new ActionErrors();
		pForm.setAcctBusEntityList(null);
		pForm.setScheduleJoinData(null);
		HttpSession session = request.getSession();
		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		if(factory==null) {
			ae.add("error",new ActionError("error.systemError","No Ejb access"));
			return ae;
		}
		int storeId = Integer.parseInt((String) session.getAttribute("Store.id"));

		initFields(pForm);
		ScheduleData schedule = ScheduleData.createValue();
		schedule.setBusEntityId(storeId);
		schedule.setScheduleTypeCd(RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE);
		schedule.setScheduleRuleCd(RefCodeNames.SCHEDULE_RULE_CD.DATE_LIST);
		schedule.setScheduleStatusCd(RefCodeNames.SCHEDULE_STATUS_CD.LIMITED);
		ScheduleJoinView scheduleJ = ScheduleJoinView.createValue();
		scheduleJ.setSchedule(schedule);
		pForm.setScheduleJoinData(scheduleJ);

		return ae;
	}
	//***********************************************************************
	public static ActionErrors delete(HttpServletRequest request,
			CorporateScheduleMgrForm pForm) throws Exception
			{
		ActionErrors ae = new ActionErrors();
		//Save shcedule
		HttpSession session = request.getSession();

		APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
		if(factory==null) {
			ae.add("error",new ActionError("error.systemError","No Ejb access"));
			return ae;
		}
		Schedule scheduleEjb = null;
		try {
			scheduleEjb = factory.getScheduleAPI();
		} catch(com.cleanwise.service.api.APIServiceAccessException exc) {
			exc.printStackTrace();
			ae.add("error",new ActionError("error.systemError","No Schedule Ejb access"));
			return ae;
		}
		ScheduleData schedule = pForm.getScheduleData();
		int scheduleId = schedule.getScheduleId();
		if(scheduleId >0 ){
			IdVector siteIds = scheduleEjb.getScheduleSiteIds(scheduleId, null);
			if (!siteIds.isEmpty()){
				ae.add("error",new ActionError("error.systemError","Sites are configured to this Corporate Schedule.  You must remove the Site configuration before deleting the Corporate Schedule."));
				return ae;
			}
			IdVector accountIds = scheduleEjb.getScheduleAccountIds(scheduleId, null);
			if (!accountIds.isEmpty()){
				ae.add("error",new ActionError("error.systemError","Accounts are configured to this Corporate Schedule.  You must remove the Account configuration before deleting the Corporate Schedule."));
				return ae;
			}

			scheduleEjb.deleteSchedule(scheduleId);
			pForm.setScheduleJoinData(null);
			DeliveryScheduleViewVector dsVV = pForm.getScheduleList();
			for(int ii=0; ii<dsVV.size(); ii++) {
				DeliveryScheduleView dsV = (DeliveryScheduleView) dsVV.get(ii);
				if(dsV.getScheduleId()==scheduleId){
					dsVV.remove(dsV);
					break;
				}
			}
			pForm.setScheduleList(dsVV);
			pForm.setAcctBusEntityList(null);
		}
		return ae;
			}
	//***********************************************************************
	public static ActionErrors sort(HttpServletRequest request,
			CorporateScheduleMgrForm pForm)
	{
		ActionErrors ae = new ActionErrors();
		String fieldName = (String) request.getParameter("sortField");
		if(fieldName==null) {
			ae.add("error",new ActionError("error.systemError","No sort field name found"));
			return ae;
		}
		DeliveryScheduleViewVector dsVV = pForm.getScheduleList();
		dsVV.sort(fieldName);
		pForm.setScheduleList(dsVV);

		return ae;
	}

	public static ActionErrors accountSearch(HttpServletRequest request, ActionForm form)
	throws Exception
	{
		ActionErrors ae=new ActionErrors();
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		CorporateScheduleMgrForm sForm = (CorporateScheduleMgrForm) form;
		APIAccess factory = new APIAccess();
		Account acctountBean = factory.getAccountAPI();
		Schedule scheduleEjb = factory.getScheduleAPI();
		String fieldValue = sForm.getAcctSearchField();
		String fieldSearchType = sForm.getAcctSearchType();
		String searchGroupId = sForm.getSearchGroupId();
		ScheduleJoinView sjVw = sForm.getScheduleJoinData();
		BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
		//crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);

		IdVector schedAcctIdV = 
			scheduleEjb.getScheduleAccountIds(sjVw.getSchedule().getScheduleId(),null);

		if(!appUser.isaSystemAdmin() ) {
			crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
		}
		if(fieldValue!=null && fieldValue.trim().length()>0) {
			fieldValue = fieldValue.trim();
			if ("id".equals(fieldSearchType)) {
				crit.setSearchId(fieldValue);
			} else {
				if ("nameBegins".equals(fieldSearchType)) {
					crit.setSearchName(fieldValue);
					crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
				} else {
					crit.setSearchName(fieldValue);
					crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
				}
			}
		}
		if(searchGroupId !=null && searchGroupId.trim().length()>0 &&
				Integer.parseInt(searchGroupId) > 0 )             {
			crit.setSearchGroupId(searchGroupId.trim());
		}
		crit.setSearchForInactive(false);
		crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

		if(!sForm.getConfAcctOnlyFl()) {
			BusEntityDataVector acctBusEntDV = acctountBean.getAccountBusEntByCriteria(crit);        
			sForm.setAcctBusEntityList(acctBusEntDV);
			HashSet schAcctHS = new HashSet();
			for(Iterator iter=schedAcctIdV.iterator(); iter.hasNext();) {
				Integer idI = (Integer) iter.next();
				schAcctHS.add(idI);
			}
			int size = (acctBusEntDV.size()>schAcctHS.size())? schAcctHS.size():acctBusEntDV.size();
			int acctSelected[] = new int[size];
			int ind = 0;
			for(Iterator iter=acctBusEntDV.iterator(); iter.hasNext();) {
				BusEntityData beD = (BusEntityData) iter.next();
				int acctId = beD.getBusEntityId();
				Integer acctIdI = new Integer(acctId);
				if(schAcctHS.contains(acctIdI)) {
					acctSelected[ind++] = acctId;
				}
			}
			int[] acctSelected1 = new int[ind];
			for(int ii=0; ii<ind; ii++) {
				acctSelected1[ii] = acctSelected[ii]; 
			}
			sForm.setAcctSelected(acctSelected1);
		} else {//configured only
			BusEntityDataVector acctBusEntDV = null;
		if(schedAcctIdV.size()>0) {
			crit.setAccountBusEntityIds(schedAcctIdV);
			acctBusEntDV = acctountBean.getAccountBusEntByCriteria(crit);  
		} else {
			acctBusEntDV = new BusEntityDataVector();
		}
		sForm.setAcctBusEntityList(acctBusEntDV);
		int[] acctSelected = new int[acctBusEntDV.size()];
		int ind = 0;
		for(Iterator iter=acctBusEntDV.iterator(); iter.hasNext();) {
			BusEntityData beD = (BusEntityData) iter.next();
			int acctId = beD.getBusEntityId();
			acctSelected[ind++] = acctId;
		}
		sForm.setAcctSelected(acctSelected);
		}
		if(ae.size()>0) return ae;

		return ae;
	}

	public static ActionErrors updateAcctConfigured(HttpServletRequest request, ActionForm form) 
	throws Exception
	{
		ActionErrors ae=new ActionErrors();
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		String userName = appUser.getUser().getUserName();
		CorporateScheduleMgrForm sForm = (CorporateScheduleMgrForm) form;
		APIAccess factory = new APIAccess();
		Schedule scheduleEjb = factory.getScheduleAPI();

		ScheduleJoinView sjVw = sForm.getScheduleJoinData();
		int scheduleId = sjVw.getSchedule().getScheduleId();

		BusEntityDataVector acctBusEntDV = sForm.getAcctBusEntityList();
		HashSet schAcctHS = new HashSet();
		IdVector acctConfig = new IdVector();
		int[] acctSelected = sForm.getAcctSelected();
		for(int ii=0; ii<acctSelected.length; ii++) {
			Integer acctIdI = new Integer(acctSelected[ii]);
			schAcctHS.add(acctIdI);
			acctConfig.add(acctIdI);
		}

		IdVector acctNotConfig = new IdVector();
		for(Iterator iter=acctBusEntDV.iterator(); iter.hasNext();) {
			BusEntityData beD = (BusEntityData) iter.next();
			Integer acctIdI = new Integer(beD.getBusEntityId());
			if(!schAcctHS.contains(acctIdI)) {
				acctNotConfig.add(acctIdI);
			}
		}
		scheduleEjb.updateScheduleAccounts(scheduleId, acctConfig, acctNotConfig,userName); 
		ae = accountSearch(request, form);
		if(ae.size()>0) return ae;
		return ae;
	}

	public static void initConfig(HttpServletRequest request, CorporateScheduleMgrForm pForm) {
		pForm.setConfFunction("acctConfig");
		initAcctConfig(request,pForm);
	}	

	public static void initAcctConfig(HttpServletRequest request,
			CorporateScheduleMgrForm pForm) {
		HttpSession session = request.getSession();
		session.setAttribute("schedule.account.vector", null);
		pForm.setConfSearchField("");
		pForm.setConfSearchType("nameBegins");
		pForm.setConifiguredOnlyFl(false);
		pForm.setConfShowInactiveFl(false);
		pForm.setRemoveSiteAssocFl(false);		
	}

	public static void initSiteConfig(HttpServletRequest request,
			CorporateScheduleMgrForm pForm) {
		HttpSession session = request.getSession();
		session.setAttribute("schedule.site.vector", null);
		pForm.setConfSearchField("");
		pForm.setConfSearchType("nameBegins");
		pForm.setSearchRefNum("");
		pForm.setSearchRefNumType("nameBegins");
		pForm.setConfCity("");
		pForm.setConfState("");
	}

	public static ActionErrors searchSitesToConfig(HttpServletRequest request,
			CorporateScheduleMgrForm theForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		int storeId = theForm.getStoreId();
		int scheduleId = theForm.getScheduleData().getScheduleId();


		String fieldValue = theForm.getConfSearchField();
		String fieldSearchRefNum = theForm.getSearchRefNum().trim();
		String city = theForm.getConfCity();
		String state = theForm.getConfState();

		int userId = appUser.getUserId();

		APIAccess factory = new APIAccess();
		Account acctBean = factory.getAccountAPI();
		Site siteBean = factory.getSiteAPI();
		User userBean = factory.getUserAPI();
		Schedule scheduleEjb = factory.getScheduleAPI();
		boolean showInactiveF1 = theForm.getConfShowInactiveFl();

		// get all accounts associated with schedule (active and inactive)
		IdVector accountIds = scheduleEjb.getScheduleAccountIds(scheduleId, null);
		// get all sites associated with schedule		
		IdVector configuredSiteIds = scheduleEjb.getScheduleSiteIds(scheduleId, null);	
		// Reset the results vector.
		List<SiteView> dv = new ArrayList<SiteView>();
		session.setAttribute("schedule.site.vector", dv);

		if (accountIds.size() == 0 || (theForm.getConifiguredOnlyFl() && configuredSiteIds.size() ==0)){
			return ae;
		}
		if (accountIds.size() == 0){
			accountIds.add(new Integer(-1));
		}

		String searchType = theForm.getConfSearchType();
		String searchRefNumType = theForm.getSearchRefNumType();
		fieldValue.trim();

		QueryRequest qr = new QueryRequest();
		qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
		qr.filterByStoreIds(Utility.toIdVector(storeId));

		String f = "";

		if (searchType.equals("id") && fieldValue.length() > 0) {
			try{
				int id = Integer.parseInt(fieldValue);
				qr.filterBySiteId(id);
				qr.filterByAccountIds(accountIds);
				dv = siteBean.getSiteCollection(qr);
			}catch(NumberFormatException e){
				ae.add("error",new ActionError("error.simpleGenericError","Wrong Site Id number format: "+fieldValue));
				return ae;
			}
		} else {
			if (searchType.equals("nameBegins") && fieldValue.length() > 0) {		
				qr.filterByOnlySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
			} else if (searchType.equals("nameContains") && fieldValue.length() > 0) {
				qr.filterByOnlySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
			}
	
			if (fieldSearchRefNum != null && fieldSearchRefNum.length() > 0) {
				if (searchRefNumType.equals("nameBegins")) {
					qr.filterByRefNum(fieldSearchRefNum, QueryRequest.BEGINS_IGNORE_CASE);
				} else if (searchRefNumType.equals("nameContains")) {
					qr.filterByRefNum(fieldSearchRefNum, QueryRequest.CONTAINS_IGNORE_CASE);
				}
			}
	
			f = city.trim();
			if (f.length() > 0) {
				qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
			}
	
			f = state.trim();
			if (f.length() > 0) {
				qr.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
			}
	
			if (!showInactiveF1) {
				qr.filterBySiteStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
			}
	
			if (theForm.getConifiguredOnlyFl()){
				qr.filterBySiteIdList(configuredSiteIds);
			}

			// get sites for accounts configured to the schedule
			qr.filterByAccountIds(accountIds);
			qr.orderBySiteName(true);
			try{
				dv = siteBean.getSiteCollection(qr);
			}catch(SQLException e){
				ae.add("UserErros", new ActionError("user.bad.search.criteria"));
				return ae;
			}
		}
		int selectedSize = dv.size() > configuredSiteIds.size() ? configuredSiteIds.size() : dv.size();
		String[] selectIds = new String[selectedSize];
		if (selectedSize > 0){
			int indexSel = 0;
			for (int i = 0; i < dv.size(); i++) {
				SiteView sview = (SiteView) dv.get(i);				
				if (configuredSiteIds.contains(sview.getId())) {
					selectIds[indexSel++] = sview.getId()+"";
				}
			}
		}
		theForm.setConfSelectIds(selectIds);
		session.setAttribute("schedule.site.vector", dv);

		return ae;
	}

	public static ActionErrors searchAcctToConfig(HttpServletRequest request,
			CorporateScheduleMgrForm theForm) throws Exception {
		ActionErrors ae = new ActionErrors();		

		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		int storeId = theForm.getStoreId();
		int scheduleId = theForm.getScheduleData().getScheduleId();

		APIAccess factory = new APIAccess();
		Account acctBean = factory.getAccountAPI();
		Schedule scheduleEjb = factory.getScheduleAPI();
		IdVector configuredAcctIds = scheduleEjb.getScheduleAccountIds(scheduleId, null);		

		String searchType = theForm.getConfSearchType();
		String fieldValue = theForm.getConfSearchField();

		AccountViewVector accountVwV = new AccountViewVector();
		HashSet userAcctIdHS = new HashSet();		
		
		BusEntitySearchCriteria besc = new BusEntitySearchCriteria();    	
		IdVector al = new IdVector();
		al.add(new Integer(storeId));
		besc.setSearchForInactive(theForm.getConfShowInactiveFl());
		besc.setStoreBusEntityIds(al);

		if (searchType.equals("id") && Utility.isSet(fieldValue) ) {
			int id = 0;
			try{
				id = Integer.parseInt(fieldValue);
			}catch(NumberFormatException e){
				ae.add("error",new ActionError("error.simpleGenericError","Wrong Account Id number format: "+fieldValue));
			}
			if (id > 0){
				try {
					AccountData accountD = acctBean.getAccount(id, storeId);
					if (accountD != null){
						IdVector accountIds = new IdVector();
						accountIds.add(id);
						besc.setAccountBusEntityIds(accountIds);
						accountVwV = acctBean.getAccountsViewList(besc);
					}
				}catch (DataNotFoundException e){}
			}
		} else {
			if (!theForm.getConifiguredOnlyFl() || !configuredAcctIds.isEmpty()){
				if(Utility.isSet(fieldValue)) {
					besc.setSearchName(fieldValue);
					if(searchType.equals("nameBegins")) {
						besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
					}
					if(searchType.equals("nameContains")) {
						besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
					}
				}
	
				if (theForm.getConifiguredOnlyFl() && !configuredAcctIds.isEmpty()){
					besc.setAccountBusEntityIds(configuredAcctIds);
				}
				besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
				accountVwV = acctBean.getAccountsViewList(besc);
			}
		}

		String[] selectIdA = new String[accountVwV.size()];
		String[] displIdA = new String[accountVwV.size()];

		int indexSel = 0;
		int indexDisp = 0;
		for(Iterator iter=accountVwV.iterator(); iter.hasNext();) {
			AccountView aVw = (AccountView) iter.next();
			String acountId = String.valueOf(aVw.getAcctId());
			displIdA[indexDisp++] = acountId;
			if(configuredAcctIds.contains(aVw.getAcctId())){
				selectIdA[indexSel++] = acountId;
			}
		}

		session.setAttribute("schedule.account.vector", accountVwV);	    
		theForm.setConfSelectIds(selectIdA);
		theForm.setConfDisplayIds(displIdA);

		return ae;
	}

	public static ActionErrors updateAccountConfig(HttpServletRequest request,
			CorporateScheduleMgrForm theForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();

		APIAccess factory = new APIAccess();
		User userBean = factory.getUserAPI();

		AccountViewVector accountVwV =
			(AccountViewVector) session.getAttribute("schedule.account.vector");

		String[] displayed = new String[accountVwV.size()];
		int index = 0;
		for(Iterator iter = accountVwV.iterator(); iter.hasNext();) {
			AccountView aVw = (AccountView) iter.next();
			int acctId = aVw.getAcctId();
			displayed[index++] = String.valueOf(acctId);
		}
		String[] selected = theForm.getConfSelectIds();


		ae = updateConfig(displayed,
				selected,
				RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID,
				factory,request, theForm);
		return ae;
	}

	public static ActionErrors updateSiteConfig(HttpServletRequest request,
			CorporateScheduleMgrForm theForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();

		APIAccess factory = new APIAccess();
		User userBean = factory.getUserAPI();

		SiteViewVector siteVwV =
			(SiteViewVector) session.getAttribute("schedule.site.vector");

		String[] displayed = new String[siteVwV.size()];
		int index = 0;
		for(Iterator iter = siteVwV.iterator(); iter.hasNext();) {
			SiteView sVw = (SiteView) iter.next();
			int siteId = sVw.getId();
			displayed[index++] = String.valueOf(siteId);
		}
		String[] selected = theForm.getConfSelectIds();


		ae = updateConfig(displayed,
				selected,
				RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID,
				factory,request, theForm);
		return ae;
	}

	private static ActionErrors updateConfig
	(String[] displayed,String[] selected,
			String scheduleDetailCd,
			APIAccess factory,HttpServletRequest request, CorporateScheduleMgrForm theForm)
	throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		String userName = appUser.getUser().getUserName();
		int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
		int scheduleId = theForm.getScheduleData().getScheduleId();

		User userBean = factory.getUserAPI();
		Site siteBean = factory.getSiteAPI();
		Schedule scheduleEjb = factory.getScheduleAPI();
		PropertyService propertyBean = factory.getPropertyServiceAPI();

		IdVector assingedBusEntIds = null;
		if(RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID.equals(scheduleDetailCd)){
			assingedBusEntIds = scheduleEjb.getScheduleAccountIds(scheduleId, null);
		}else{
			assingedBusEntIds = scheduleEjb.getScheduleSiteIds(scheduleId, null);
		}

		IdVector busEntIdsToAdd = new IdVector();
		IdVector busEntIdsToRemove = new IdVector();
		for (int i = 0; i < displayed.length; i++) {
			String did = displayed[i];
			boolean foundId = false;
			Integer id = new Integer(did);

			for (int j = 0; j < selected.length; j++) {	
				if (did.equals(selected[j])) {
					foundId = true;
					break;
				}
			}

			if (foundId) {
				if (!assingedBusEntIds.contains(id)){
					busEntIdsToAdd.add(id);
				}
			} else if(!assingedBusEntIds.contains(id)){
				continue; //nothing to remove
			} else {
				// we need to remove the association				
				if (RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID.equals(scheduleDetailCd) &&
						!theForm.getRemoveSiteAssocFl()) {

					//Check assigned account related sites
					IdVector assignedSiteIds = scheduleEjb.getScheduleSiteIds(scheduleId, null);
					if (!assignedSiteIds.isEmpty()){
						IdVector siteByAcct =siteBean.getAllSiteIds(id);
						int configuredCount = 0;
						IdVector sitesFound = new IdVector();
						for (int ii = 0; ii < siteByAcct.size() && configuredCount < 3; ii++){
							if (assignedSiteIds.contains(siteByAcct.get(ii))){
								sitesFound.add(siteByAcct.get(ii));
								configuredCount++;
							}
						}
						if (configuredCount > 0){
							QueryRequest qr = new QueryRequest();				
							qr.filterBySiteIdList(sitesFound);

							SiteViewVector sVwV = siteBean.getSiteCollection(qr);
							if(sVwV.size()>0) {		
								String errorMess = "Can't remove account association. Account "+id+" has configured sites: ";
								for(Iterator iter = sVwV.iterator(); iter.hasNext();) {
									SiteView sVw = (SiteView) iter.next();
									errorMess += " ["+sVw.getName()+"] ";

								}
								ae.add("error",new ActionError("error.simpleGenericError",errorMess));
								return ae;
							}
						}
					}					
				}
				busEntIdsToRemove.add(id);
			}

		}

		if (!busEntIdsToAdd.isEmpty() || !busEntIdsToRemove.isEmpty()){
			if(RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID.equals(scheduleDetailCd)){
				scheduleEjb.updateScheduleAccounts(scheduleId, busEntIdsToAdd, busEntIdsToRemove, userName);
			}else{
				scheduleEjb.updateScheduleSites(scheduleId, busEntIdsToAdd, busEntIdsToRemove, userName);
			}
		}
		return ae;
	}

	public static ActionErrors configureAllAccounts(HttpServletRequest request,
			CorporateScheduleMgrForm theForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		APIAccess factory = new APIAccess();
		Schedule scheduleEjb = factory.getScheduleAPI();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		String userName = appUser.getUser().getUserName();
		int scheduleId = theForm.getScheduleData().getScheduleId();

		scheduleEjb.configureAllAccounts(scheduleId,userName);
		ae = searchAcctToConfig(request, theForm);
		return ae;
	}

	public static ActionErrors configureAllAccountSites(
			HttpServletRequest request, CorporateScheduleMgrForm theForm) throws Exception {
		ActionErrors ae = new ActionErrors();
		ae = updateAccountConfig(request,theForm);
		if(ae.size()>0) {
			return ae;
		}
		HttpSession session = request.getSession();
		APIAccess factory = new APIAccess();
		Schedule scheduleEjb = factory.getScheduleAPI();
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		String userName = appUser.getUser().getUserName();
		int scheduleId = theForm.getScheduleData().getScheduleId();

		IdVector accountIds = new IdVector(); 
		String[] accountIdA = theForm.getConfSelectIds();
		for ( int ii = 0; ii<accountIdA.length; ii++) {
			accountIds.add(Integer.parseInt(accountIdA[ii]));
		}
		scheduleEjb.configureAllAccountSites(scheduleId,accountIds,userName);

		return ae;
	}

}
