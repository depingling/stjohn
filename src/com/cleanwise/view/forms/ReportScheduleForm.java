/**
 * Title:        ReportScheduleForm
 * Description:  This is the Struts ActionForm class for reprotSchedule.jsp .
 * Purpose:      Report schedule survice .
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the user manager page.
 *
 * @author Tim Besser
 */
public final class ReportScheduleForm extends StorePortalBaseForm {
    private String mLastAction = "";
    //Schedule list
    private String mCategoryFilter = "";
    private String mReportFilter = "";
    private ReportScheduleViewVector mReportSchedules = new ReportScheduleViewVector();
    private String[] mScheduleSelected = new String[0];

    //Add Schedule
    private int mNewScheduleReportId = 0;

    //Schedule Detail
    private ReportScheduleJoinView mReportScheduleJoin = null;


   /////////////////////////////////////


   private String mWeekCycle = "1";
   private int[] mWeekDays = new int[0];  // Sunday - 1, ... Saturday - 6

   private String mMonthDayCycle = "1";
   private int[] mMonthDays = new int[0]; //1 ... 28

   private String mMonthWeekCycle = "1";
   private int mMonthWeekDay = 2; // Sunday - 1, ... Saturday - 7, day - 8
   private int[] mMonthWeeks = new int[0]; //first -1, second -2, third -3, forth -4, last -5

   private String mAlsoDates = "";
   private String mExcludeDates = "";

  //private int mMonthWeekFrequency = 0;

  private GregorianCalendar mCalendar = null;
  private int mLastReqMonth = -1;
  private int mLastReqMonthDay = -1;
  private int mLastReqFirstWeekDay = -1;
  private int mNextScheduleIndex = 0;
  private int mNextScheduleRelMonth = 0;
  private int mNextScheduleMonthDay = 0;
  private List mCalendarScheduleDates = new ArrayList();

  ////////////////////// Sharing /////////////////////////
    //
    private int mUserToAdd = 0;
    private int mGroupToAdd = 0;

    private String mSharingType = RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP;
    private boolean mAssignedFl = true;
    private String[] mGroupSelected = new String[0];
    private String[] mUserSelected = new String[0];
    private String[] mGroupToNotify = new String[0];
    private String[] mUserToNotify = new String[0];

    private int mReportOwner = 0;
    private AccountUIViewVector mAccountFilter = new AccountUIViewVector();
    private SiteViewVector mSiteFilter=new SiteViewVector();
    private ManufacturerDataVector manufFilter = new ManufacturerDataVector();
    private DistributorDataVector distFilter = new DistributorDataVector();
    private ItemViewVector itemFilter = new ItemViewVector();
    private UserDataVector userFilter = new UserDataVector();
    private GroupDataVector groupFilter = new GroupDataVector();
    private String[] mRunForAccounts = new String[0];
    private String mEmailFlag;
  // private SiteViewVector MSiteFilter;

  // -------------------------------------------------------- Interface Methods
    public String getLastAction(){return mLastAction;}
    public void setLastAction(String pLastAction) {mLastAction = pLastAction;}

    public String getCategoryFilter(){return mCategoryFilter;}
    public void setCategoryFilter(String pCategoryFilter) {mCategoryFilter = pCategoryFilter;}

    public String getReportFilter(){return mReportFilter;}
    public void setReportFilter(String pReportFilter) {mReportFilter = pReportFilter;}

    public ReportScheduleViewVector getReportSchedules(){return mReportSchedules;}
    public void setReportSchedules(ReportScheduleViewVector pReportSchedules) {mReportSchedules = pReportSchedules;}

    //private String[] mSelectedSchedules = new String[0];
    public String[] getScheduleSelected(){return mScheduleSelected;}
    public void setScheduleSelected(String[] pScheduleSelected) {mScheduleSelected = pScheduleSelected;}


    public int getNewScheduleReportId(){return mNewScheduleReportId;}
    public void setNewScheduleReportId(int pNewScheduleReportId) {mNewScheduleReportId = pNewScheduleReportId;}


    //---------------------------------------------------------------------------
    public ReportScheduleJoinView getReportScheduleJoin(){return mReportScheduleJoin;}
    public void setReportScheduleJoin(ReportScheduleJoinView pReportScheduleJoin) {mReportScheduleJoin = pReportScheduleJoin;}

    //Generic controls individual access
    public String getGenericControlValue(int index) {
       GenericReportControlViewVector grcVwV = mReportScheduleJoin.getReportControls();
        if(grcVwV==null || index>=grcVwV.size()) {
            return null;
        }
        GenericReportControlView grcVw =
                           (GenericReportControlView) grcVwV.get(index);
        String value = grcVw.getValue();
        return value ;
    }

    public void setGenericControlValue(int index, String value) {
       GenericReportControlViewVector grcVwV = mReportScheduleJoin.getReportControls();
        if(grcVwV==null || index>=grcVwV.size()) {
            return;
        }
        GenericReportControlView grcVw =
                         (GenericReportControlView) grcVwV.get(index);
        grcVw.setValue(value);

    }

    /*
    public GenericReportControlViewVector getReportControls(){return mReportControls;}
    public void setReportControls(GenericReportControlViewVector pReportControls)
                                    {mReportControls = pReportControls;}

    public ReportScheduleDetailDataVector getSchduleDetails(){return mSchduleDetails;}
    public void setSchduleDetails(ReportScheduleDetailDataVector pSchduleDetails)
                                    {mSchduleDetails = pSchduleDetails;}

    public ReportScheduleDetailDataVector getGroupShare(){return mGroupShare;}
    public void setGroupShare(ReportScheduleDetailDataVector pGroupShare)
                                                  {mGroupShare = pGroupShare;}

    public ReportScheduleDetailDataVector getUserShare(){return mUserShare;}
    public void setUserShare(ReportScheduleDetailDataVector pUserShare)
                                                     {mUserShare = pUserShare;}

    */

//----------------- Report Schedule Calendar
  public int getNextScheduleIndex() {return mNextScheduleIndex;}
  public void setNextScheduleIndex(int pValue) {mNextScheduleIndex = pValue;}

  public int getNextScheduleRelMonth() {return mNextScheduleRelMonth;}
  public void setNextScheduleRelMonth(int pValue) {mNextScheduleRelMonth = pValue;}

  public int getNextScheduleMonthDay() {return mNextScheduleMonthDay;}
  public void setNextScheduleMonthDay(int pValue) {mNextScheduleMonthDay = pValue;}

  public List getCalendarScheduleDates() {return mCalendarScheduleDates;}
  public void setCalendarScheduleDates(List pValue) {mCalendarScheduleDates = pValue;}

  public GregorianCalendar getCalendar() {return mCalendar;}
  public void setCalendar(GregorianCalendar pValue) {mCalendar = pValue;}

  public int getDay(int relativeMonth, int weekNum, int weekDay) {
    GregorianCalendar calendar = (GregorianCalendar) mCalendar.clone();
    if(relativeMonth!=mLastReqMonth) {
      calendar.add(GregorianCalendar.MONTH,relativeMonth);
      mLastReqFirstWeekDay = calendar.get(GregorianCalendar.DAY_OF_WEEK);
      calendar.add(GregorianCalendar.MONTH,1);
      calendar.add(GregorianCalendar.DAY_OF_MONTH,-1);
      mLastReqMonthDay = calendar.get(GregorianCalendar.DAY_OF_MONTH);
    }
    int req = weekNum*7+weekDay;
    int res = req-mLastReqFirstWeekDay+1;
    if(res>mLastReqMonthDay) res = -1;

    if(relativeMonth==mNextScheduleRelMonth && res==mNextScheduleMonthDay) {
      res *= 100;
      mNextScheduleIndex++;
      if(mNextScheduleIndex<mCalendarScheduleDates.size()) {
      GregorianCalendar gc = (GregorianCalendar) mCalendarScheduleDates.get(mNextScheduleIndex);
        mNextScheduleRelMonth =
          gc.get(GregorianCalendar.YEAR)*12+gc.get(GregorianCalendar.MONTH)-
          (mCalendar.get(GregorianCalendar.YEAR)*12+mCalendar.get(GregorianCalendar.MONTH));
        mNextScheduleMonthDay = gc.get(GregorianCalendar.DAY_OF_MONTH);
      } else {
        mNextScheduleRelMonth = -1;
        mNextScheduleMonthDay = 0;
      }
    }
    return res;
  }
  public String getMonth(int relativeMonth){
    int month = mCalendar.get(GregorianCalendar.MONTH);
    month += relativeMonth;
    month %= 12;
    String name = "";
    switch(month) {
      case 0: name = "January"; break;
      case 1: name = "February"; break;
      case 2: name = "March"; break;
      case 3: name = "April"; break;
      case 4: name = "May"; break;
      case 5: name = "June"; break;
      case 6: name = "July"; break;
      case 7: name = "August"; break;
      case 8: name = "September"; break;
      case 9: name = "October"; break;
      case 10: name = "November"; break;
      case 11: name = "December"; break;
    }
    return name;
  }


  //Week schdule
  public String getWeekCycle() {return mWeekCycle;}
  public void setWeekCycle(String pValue) {mWeekCycle = pValue;}

  public int[] getWeekDays() {return mWeekDays;}
  public void setWeekDays(int[] pValue) {mWeekDays = pValue;}

  //Month day schdule
  public String getMonthDayCycle() {return mMonthDayCycle;}
  public void setMonthDayCycle(String pValue) {mMonthDayCycle = pValue;}

  public int[] getMonthDays() {return mMonthDays;}
  public void setMonthDays(int[] pValue) {mMonthDays = pValue;}

  //Month week schdule
  public String getMonthWeekCycle() {return mMonthWeekCycle;}
  public void setMonthWeekCycle(String pValue) {mMonthWeekCycle = pValue;}

  public int getMonthWeekDay() {return mMonthWeekDay;}
  public void setMonthWeekDay(int pValue) {mMonthWeekDay = pValue;}

  public int[] getMonthWeeks() {return mMonthWeeks;}
  public void setMonthWeeks(int[] pValue) {mMonthWeeks = pValue;}

  //Exceptions
  public String getAlsoDates() {return mAlsoDates;}
  public void setAlsoDates(String pValue) {mAlsoDates = pValue;}

  public String getExcludeDates() {return mExcludeDates;}
  public void setExcludeDates(String pValue) {mExcludeDates = pValue;}

  //
  public int getLastReqMonth() {return mLastReqMonth;}
  public void setLastReqMonth(int pValue) {mLastReqMonth = pValue;}

  public int getLastReqMonthDay() {return mLastReqMonthDay;}
  public void setLastReqMonthDay(int pValue) {mLastReqMonthDay = pValue;}

  public int getLastReqFirstWeekDay() {return mLastReqFirstWeekDay;}
  public void setLastReqFirstWeekDay(int pValue) {mLastReqFirstWeekDay = pValue;}

//----------------- Report Schedule Sharing
    public int getUserToAdd(){return mUserToAdd;}
    public void setUserToAdd (int pUserToAdd) {mUserToAdd = pUserToAdd;}

    public int getGroupToAdd(){return mGroupToAdd;}
    public void setGroupToAdd (int pGroupToAdd) {mGroupToAdd = pGroupToAdd;}

    public String getSharingType(){return mSharingType;}
    public void setSharingType (String pSharingType) {mSharingType = pSharingType;}

    public boolean getAssignedFl(){return mAssignedFl;}
    public void setAssignedFl (boolean pAssignedFl) {mAssignedFl = pAssignedFl;}

    public ReportSchedUserShareViewVector getUsers()
    {
      ReportSchedUserShareViewVector userVwV = mReportScheduleJoin.getUsers();
      if(userVwV==null) {
        userVwV = new ReportSchedUserShareViewVector();
        mReportScheduleJoin.setUsers(userVwV);
      }
      return userVwV;
    }

    public void setUsers (ReportSchedUserShareViewVector pUsers) {
      mReportScheduleJoin.setUsers(pUsers);
    }

    public ReportSchedGroupShareViewVector getGroups(){
      ReportSchedGroupShareViewVector groupVwV = mReportScheduleJoin.getGroups();
      if(groupVwV==null) {
        groupVwV = new ReportSchedGroupShareViewVector();
        mReportScheduleJoin.setGroups(groupVwV);
      }
      return groupVwV;
    }

    public void setGroups (ReportSchedGroupShareViewVector pGroups) {
      mReportScheduleJoin.setGroups(pGroups);
    }


    public String[] getGroupSelected(){return mGroupSelected;}
    public void setGroupSelected (String[] pGroupSelected) {mGroupSelected = pGroupSelected;}

    public String[] getUserSelected(){return mUserSelected;}
    public void setUserSelected (String[] pUserSelected) {mUserSelected = pUserSelected;}

    public String[] getGroupToNotify(){return mGroupToNotify;}
    public void setGroupToNotify (String[] pGroupToNotify) {mGroupToNotify = pGroupToNotify;}

    public String[] getUserToNotify(){return mUserToNotify;}
    public void setUserToNotify (String[] pUserToNotify) {mUserToNotify = pUserToNotify;}

    public int getReportOwner(){return mReportOwner;}
    public void setReportOwner (int pReportOwner) {mReportOwner = pReportOwner;}


  ////////////////////////////////////////////////////////
    /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mLastAction = "";
      mNewScheduleReportId = 0;
      mScheduleSelected = new String[0];
      mWeekDays = new int[0];
      mMonthDays = new int[0];
      mMonthWeeks = new int[0];
      mGroupSelected = new String[0];
      mUserSelected = new String[0];
      mGroupToNotify = new String[0];
      mUserToNotify = new String[0];
      mUserToAdd = 0;
      mGroupToAdd = 0;
      mEmailFlag = "";
      mRunForAccounts = new String[0];

    }

    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {

        // Perform the run validation.
        ActionErrors errors = new ActionErrors();
        return errors;
    }

  public void setAccountFilter(AccountUIViewVector pAccountFilter) {
    this.mAccountFilter = pAccountFilter;
  }

  public AccountUIViewVector getAccountFilter() {
    return mAccountFilter;
  }

  public ItemViewVector getItemFilter() {
    return itemFilter;
  }

  public DistributorDataVector getDistFilter() {
    return distFilter;
  }

  public ManufacturerDataVector getManufFilter() {
    return manufFilter;
  }

  public SiteViewVector getSiteFilter() {
    return mSiteFilter;
  }

  public void setItemFilter(ItemViewVector itemFilter) {
    this.itemFilter = itemFilter;
  }

  public void setDistFilter(DistributorDataVector distFilter) {
    this.distFilter = distFilter;
  }

  public void setManufFilter(ManufacturerDataVector manufFilter) {
    this.manufFilter = manufFilter;
  }

  public void setSiteFilter(SiteViewVector pSiteFilter) {
    this.mSiteFilter = pSiteFilter;
  }
  public UserDataVector getUserFilter() {
      return userFilter;
  }

  public GroupDataVector getGroupFilter() {
    return groupFilter;
  }

  public String getEmailFlag() {
    return mEmailFlag;
  }

  public void setUserFilter(UserDataVector pUserFilter) {
      this.userFilter = pUserFilter;
  }

  public void setGroupFilter(GroupDataVector groupFilter) {
    this.groupFilter = groupFilter;
  }

  public void setEmailFlag(String pEmailFlag) {
    this.mEmailFlag = pEmailFlag;
  }
  public String[] getRunForAccounts() {
       return this.mRunForAccounts;
   }

   public void setRunForAccounts(String[] pRunForAccounts) {
       this.mRunForAccounts = pRunForAccounts;
   }


}
