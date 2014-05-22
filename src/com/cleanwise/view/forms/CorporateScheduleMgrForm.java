package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.PhysicalInventoryPeriodArray;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.DeliveryScheduleViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ScheduleData;
import com.cleanwise.service.api.value.ScheduleDetailData;
import com.cleanwise.service.api.value.ScheduleJoinView;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;

/**
 *  Form bean for the Corporate scheduler Store manager configuration page.
 *
 */
public final class CorporateScheduleMgrForm extends ActionForm {
   //Page control
   private String _contentPage = "";
   public void setContentPage(String pValue) {_contentPage = pValue;}
   public String getContentPage(){return _contentPage;}
   
   //Main page fields
   private int _storeId = 0;
   public void setStoreId(int pValue) {_storeId = pValue;}
   public int getStoreId(){return _storeId;}

   private String _searchName = "";
   public void setSearchName(String pValue) {_searchName = pValue;}
   public String getSearchName(){return _searchName;}

   private String _searchType = "nameBegins";
   public void setSearchType(String pVal) {this._searchType = pVal;}
   public String getSearchType() {return (this._searchType);}   
   
   private String _searchDateFrom;
   public String getSearchDateFrom() {return _searchDateFrom;}
   public void setSearchDateFrom(String searchDateFrom) {this._searchDateFrom = searchDateFrom;}
   
   private String _searchDateTo;
   public String getSearchDateTo() { return _searchDateTo; }
   public void setSearchDateTo(String searchDateTo) { this._searchDateTo = searchDateTo; }

   //Search result
   private DeliveryScheduleViewVector _scheduleList = null;
   public void setScheduleList(DeliveryScheduleViewVector pValue) {_scheduleList = pValue;}
   public DeliveryScheduleViewVector getScheduleList(){return _scheduleList;}


   //Detail data
   private ScheduleJoinView _scheduleJoinData = null;
   public void setScheduleJoinData(ScheduleJoinView pValue) {_scheduleJoinData = pValue;}
   public ScheduleJoinView getScheduleJoinData(){return _scheduleJoinData;}

   public ScheduleData getScheduleData(){
     if(_scheduleJoinData==null) return null;
     return _scheduleJoinData.getSchedule();
   }

   private String _cutoffTime="13:00";
   public void setCutoffTime(String pValue) {_cutoffTime = pValue;}
   public String getCutoffTime(){return _cutoffTime;}

   private String _alsoDates = "";
   public String getAlsoDates() {return _alsoDates;}
   public void setAlsoDates(String pValue) {_alsoDates = pValue;}

   ///////// Account Specifig
   private String _acctSearchField = "";
   public void setAcctSearchField (String pVal) {_acctSearchField = pVal;}
   public String getAcctSearchField() {return _acctSearchField;}

   private String _acctSearchType = "nameBegins";
   public void setAcctSearchType (String pVal) {_acctSearchType = pVal;}
   public String getAcctSearchType() {return _acctSearchType;}
      
   private String _searchGroupId = "";
   public void setSearchGroupId (String pVal) {_searchGroupId = pVal;}
   public String getSearchGroupId() {return _searchGroupId;}
   
   private boolean _confAcctOnlyFl = false;
   public void setConfAcctOnlyFl (boolean pVal) {_confAcctOnlyFl = pVal;}
   public boolean getConfAcctOnlyFl() {return _confAcctOnlyFl;}

   private BusEntityDataVector _acctBusEntityList = null;
   public void setAcctBusEntityList (BusEntityDataVector pVal) {_acctBusEntityList = pVal;}
   public BusEntityDataVector getAcctBusEntityList() {return _acctBusEntityList;}
   
   private int[] _acctSelected = new int[0];
   public void setAcctSelected (int[] pVal) {_acctSelected = pVal;}
   public int[] getAcctSelected() {return _acctSelected;}
    
   private String _invCartAccessInterval ="";
   public String getInvCartAccessInterval() {return this._invCartAccessInterval;}
   public void setInvCartAccessInterval(String pValue) {this._invCartAccessInterval = pValue;}

   private String _physicalInventoryPeriods = "";
   public String getPhysicalInventoryPeriods() {return _physicalInventoryPeriods;}
   public void setPhysicalInventoryPeriods(String periods) {_physicalInventoryPeriods = periods;}

   //=================== Configuration ==============================
   private String mConfFunction = "";
   private boolean mConifiguredOnlyFl = false;
   private boolean mConfShowInactiveFl = false;
   private boolean mRemoveSiteAssocFl = false;
   private String mConfSearchField = "";
   private String mConfSearchType = "nameBegins";
   private String mConfCity = "";
   private String mConfState = "";
   private IdVector mConfPermAccountIds = null;
   private boolean mConfPermAcctFilterFl = false;
   private boolean mShowAllAcctFl = false;
   private boolean mMergeAccountPermFl = true;
   private LinkedList mConfPermKeys = null;
   private HashMap mConfPermissions = null;
   private HashMap mConfPermAccounts = null;
   private String[] mConfAsocSiteIds = null;
   private String[] mConfSelectIds = null;
   private String[] mConfDisplayIds = null;
   private boolean isEditableForUserFl;

   public String getConfFunction() {return mConfFunction;}
   public void setConfFunction(String pVal) {mConfFunction = pVal; }

   public boolean getConifiguredOnlyFl() {return mConifiguredOnlyFl;}
   public void setConifiguredOnlyFl(boolean pVal) {mConifiguredOnlyFl = pVal; }

   public boolean getConfShowInactiveFl() {return mConfShowInactiveFl;}
   public void setConfShowInactiveFl(boolean pVal) {mConfShowInactiveFl = pVal; }

   public boolean getRemoveSiteAssocFl() {return mRemoveSiteAssocFl;}
   public void setRemoveSiteAssocFl(boolean pVal) {mRemoveSiteAssocFl = pVal; }

   public String getConfSearchField() {return mConfSearchField;}
   public void setConfSearchField(String pVal) {mConfSearchField = pVal; }

   public String getConfSearchType() {return mConfSearchType;}
   public void setConfSearchType(String pVal) {mConfSearchType = pVal; }

   public String getConfCity() {return mConfCity;}
   public void setConfCity(String pVal) {mConfCity = pVal; }

   public String getConfState() {return mConfState;}
   public void setConfState(String pVal) {mConfState = pVal; }

   public IdVector getConfPermAccountIds() {return mConfPermAccountIds;}
   public void setConfPermAccountIds(IdVector pVal) {mConfPermAccountIds = pVal; }

   public boolean getConfPermAcctFilterFl() {return mConfPermAcctFilterFl;}
   public void setConfPermAcctFilterFl(boolean pVal) {mConfPermAcctFilterFl = pVal; }

   public boolean getShowAllAcctFl() {return mShowAllAcctFl;}
   public void setShowAllAcctFl(boolean pVal) {mShowAllAcctFl = pVal; }

   public boolean getMergeAccountPermFl() {return mMergeAccountPermFl;}
   public void setMergeAccountPermFl(boolean pVal) {mMergeAccountPermFl = pVal; }

   public LinkedList getConfPermKeys() {return mConfPermKeys;}
   public void setConfPermKeys(LinkedList pVal) {mConfPermKeys = pVal; }

   public HashMap getConfPermissions() {return mConfPermissions;}
   public void setConfPermissions(HashMap pVal) {mConfPermissions = pVal; }
   public void setConfPermForm(UserRightsForm pUrf, int pInd) {
     if(pInd!=0) {
       mConfPermissions.put(new Integer(pInd),pUrf);
     }
   }

   public HashMap getConfPermAccounts() {return mConfPermAccounts;}
   public void setConfPermAccounts(HashMap pVal) {mConfPermAccounts = pVal; }

   public String[] getConfAsocSiteIds() {return mConfAsocSiteIds;}
   public void setConfAsocSiteIds(String[] pVal) {mConfAsocSiteIds = pVal; }

   public String[] getConfSelectIds() {return mConfSelectIds;}
   public void setConfSelectIds(String[] pVal) {mConfSelectIds = pVal; }

   public String[] getConfDisplayIds() {return mConfDisplayIds;}
   public void setConfDisplayIds(String[] pVal) {mConfDisplayIds = pVal; }

   public boolean isIsEditableForUserFl() { return isEditableForUserFl; }
   public void setIsEditableForUserFl(boolean isEditableForUserFl) { this.isEditableForUserFl = isEditableForUserFl; }
   
   private String _searchRefNum = "";
   public void setSearchRefNum(String pVal) {this._searchRefNum = pVal;}
   public String getSearchRefNum() {return (this._searchRefNum);}

   private String _searchRefNumType = "nameBegins";
   public void setSearchRefNumType(String pVal) {this._searchRefNumType = pVal;}
   public String getSearchRefNumType() {return (this._searchRefNumType);}
   
    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setConfAcctOnlyFl(false);
        setAcctSelected(new int[0]);
        
        mShowAllAcctFl = false;
        mConifiguredOnlyFl = false;
        mConfShowInactiveFl = false;
        mRemoveSiteAssocFl = false;
        mConfSelectIds = new String[0];
        mConfDisplayIds = new String[0];
    }



    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
    	ActionErrors errors = new ActionErrors();
    	ScheduleData schedule = _scheduleJoinData.getSchedule();
    	if(!Utility.isSet(schedule.getShortDesc())) {
        	errors.add("error",new ActionError("error.simpleGenericError","Empty Schedule Name "));
        }
        
        if(!Utility.isSet(getInvCartAccessInterval())) {
        	errors.add("error",new ActionError("error.simpleGenericError","Empty Interval Hours"));
        }else{
        	try {
        		Integer.parseInt(getInvCartAccessInterval());
        	}catch(NumberFormatException e){
        		errors.add("error",new ActionError("error.simpleGenericError","Wrong Interval Hours number format: "+getInvCartAccessInterval()));
        	}
        }
        
        String cutoffTimeS = getCutoffTime();        
        if(!Utility.isSet(cutoffTimeS)) {
        	errors.add("error",new ActionError("error.simpleGenericError","Empty Cutoff Time"));        	
        }else{
        	SimpleDateFormat sdfTime = new SimpleDateFormat(Constants.SIMPLE_TIME24_PATTERN1);
            Date cutoffTime = null;
            String exp = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
            Pattern datePatternExp = Pattern.compile(exp,Pattern.CASE_INSENSITIVE); 	
        	ActionError ae = null;
            if (!datePatternExp.matcher(cutoffTimeS).matches()) {
        		Object[] param = new Object[]{cutoffTimeS};
                String errorMess = ClwI18nUtil.getMessage(request,"admin.corporateSchedule.error.cutoffTimeFormat",param);
                ae = new ActionError("error.simpleGenericError",errorMess);
        	}
        	if (ae != null) {
        		errors.add("error", ae);
        	} else {	
	            try{
	              cutoffTime = sdfTime.parse(cutoffTimeS);
	            }catch(Exception exc){}
	            if(cutoffTime==null) {
	            	errors.add("error",new ActionError("error.simpleGenericError","Wrong Cutoff Time format: "+cutoffTimeS));
	            }else{
		            cutoffTimeS = sdfTime.format(cutoffTime);
		            setCutoffTime(cutoffTimeS);
	            }
        	} 
        }
        
        PhysicalInventoryPeriodArray periodsObj = 
			PhysicalInventoryPeriodArray.parse(getPhysicalInventoryPeriods());
		if (periodsObj == null) {
			String errorMessage = "The text with the physical inventory period is written with errors";
			errors.add("error", new ActionError("error.simpleGenericError", errorMessage));
		}
		
		String alsoDates = getAlsoDates();		
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
				if(gc==null) {
					errors.add("error",new ActionError("error.simpleGenericError","Wrong also place order on date format: "+dateS));
				}else{
					// check if weekend  
					Date d = gc.getTime();
					int dayOfWeek = d.getDay() + 1;
					if ( dayOfWeek == gc.SUNDAY || dayOfWeek == gc.SATURDAY) {
						errors.add("error",new ActionError("error.simpleGenericError","Delivery at the weekend not allowed: " + dateS));
					}
				}
			}
		}
        
        return errors;
    }
}

