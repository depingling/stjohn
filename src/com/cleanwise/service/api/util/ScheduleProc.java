package com.cleanwise.service.api.util;

import java.util.Collection;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
//import java.io.Serializable;


public class ScheduleProc {
    int WEEKLY = 1;
    int DAY_MONTHLY = 2;
    int WEEK_MONTHLY = 3;
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    SimpleDateFormat timeFormat24hour = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
    Calendar setupCal = Calendar.getInstance();
    
    //Schedule related
    ScheduleData _scheduleHeader;
    ScheduleDetailDataVector _scheduleDetails;
    SiteDeliveryScheduleView _siteDeliverySchedule;
    int[] _weekFilter;
    //int[] _weekFilter2;
    boolean _applyWeekFilter;
    int _scheduleId;
    int _scheduleRule;
    Date _effDate;
    GregorianCalendar _effDateWkBegCal = null;
    Date _expDate;
    Date[] _alsoDates;
    Date[] _exceptDates;
    Date[] _holidayDates;
    int _cycle;
    int[] _deliveryDays;
    int _weekDayInd;
    int _cutoffDay;
    private Date _cutoffTime;
    int _accountCutoffDays;
    boolean _initialisedFlag;
    String _errorMessage;
    
    //Changing parameters
    Date _curAlsoDate;
    int _curAlsoInd;
    Date _curExceptDate;
    int _curExceptInd;
    Date _curHolidayDate;
    int _curHolidayInd;
    GregorianCalendar _begCal;
    GregorianCalendar _wkBegCal;
    GregorianCalendar _endCal;
    Date _begTime;
    GregorianCalendar _curDeliveryCal;
    int _curCalendarIndex;
    GregorianCalendar _curBegWeekCal; //for weekly schedule
    boolean _exceptionFlag;
    GregorianCalendar _firstWeekMonthCal; //for week-monthly schedule
    private int[][] _siteDeliveryWeekFilter;
    private int _currMonth=-1;


    public ScheduleProc(ScheduleJoinView pSchedule,
            SiteDeliveryScheduleView pSiteDeliverySchedule,
            int pAccountCutoffDays) {
        _scheduleHeader = pSchedule.getSchedule();
        _scheduleDetails = pSchedule.getScheduleDetail();
        _siteDeliverySchedule = pSiteDeliverySchedule;
        _initialisedFlag = false;
        _errorMessage = null;
        _accountCutoffDays = pAccountCutoffDays;
    }
    
    public ScheduleProc(ScheduleJoinView pSchedule, SiteDeliveryScheduleView pSiteDeliverySchedule) {
        _scheduleHeader = pSchedule.getSchedule();
        _scheduleDetails = pSchedule.getScheduleDetail();
        _siteDeliverySchedule = pSiteDeliverySchedule;
        _initialisedFlag = false;
        _errorMessage = null;
        _accountCutoffDays = 0;
    }
    
    int _weekOfDel = 0;
    
    public void initSchedule()
    throws Exception {
        _scheduleId = _scheduleHeader.getScheduleId();
        _cycle = _scheduleHeader.getCycle();
        String scheduleRule = _scheduleHeader.getScheduleRuleCd();

        _effDate = _scheduleHeader.getEffDate();
        Calendar effCall = GregorianCalendar.getInstance();
        effCall.setTime(_effDate);
        int effDateWeekDay = effCall.get(Calendar.DAY_OF_WEEK);
        _effDateWkBegCal = (GregorianCalendar) effCall.clone();
        _effDateWkBegCal.add(Calendar.DAY_OF_MONTH,1-effDateWeekDay);

        if(_cycle==2 && RefCodeNames.SCHEDULE_RULE_CD.WEEK.equals(scheduleRule)) {
            //if is year is current 
            int effYear = effCall.get(GregorianCalendar.YEAR);
            _weekOfDel = effCall.get(Calendar.WEEK_OF_YEAR);
        } else if(_cycle!=1) {
            _errorMessage = "Can't process schedule if schedule cycle is not 1. Schedule id = "+_scheduleId;
            throw new Exception(_errorMessage);
        }
        //_weekOfDel = effCall.get(Calendar.WEEK_OF_YEAR);
        
        _expDate = _scheduleHeader.getExpDate();
        if(_siteDeliverySchedule==null) {
            _applyWeekFilter = false;
            //_errorMessage = "No site delivery schedule provided";
            //throw new Exception(_errorMessage);
        } else if (_siteDeliverySchedule.getSiteScheduleType().startsWith("Any")) {
            _applyWeekFilter = false;          
        } else {
            //Week filter
            _weekFilter = new int[5];
            _weekFilter[0] = (_siteDeliverySchedule.getWeek1ofMonth())? 0:-1;
            _weekFilter[1] = (_siteDeliverySchedule.getWeek2ofMonth())? 0:-1;
            //if ( _cycle == 2 && _siteDeliverySchedule.getWeek1ofMonth() ) {
            //    _weekFilter[1] = 0;
            //}
            _weekFilter[2] = (_siteDeliverySchedule.getWeek3ofMonth())? 0:-1;
            //if ( _cycle == 2 && _siteDeliverySchedule.getWeek2ofMonth() ) {
            //    _weekFilter[2] = 0;
            //}
            _weekFilter[3] = (_siteDeliverySchedule.getWeek4ofMonth())? 0:-1;
            //if ( _cycle == 2 && _siteDeliverySchedule.getWeek3ofMonth() ) {
            //    _weekFilter[3] = 0;
            //}
            _weekFilter[4] = (_siteDeliverySchedule.getLastWeekofMonth())? 0:-1;
            //if ( _cycle == 2 && _siteDeliverySchedule.getWeek4ofMonth() ) {
            //    _weekFilter[4] = 0;
            //}
            

            _applyWeekFilter = false;
            for(int ii=0; ii<5; ii++) {
                if(_weekFilter[ii]==-1) {
                    _applyWeekFilter = true;
                    break;
                }
            }
        }
        
        if(RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleRule)) {
            _scheduleRule = DAY_MONTHLY;
        } else if(RefCodeNames.SCHEDULE_RULE_CD.WEEK.equals(scheduleRule)) {
            _scheduleRule = WEEKLY;
        } else if(RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleRule)) {
            _scheduleRule = WEEK_MONTHLY;
        }
        
        //Details
        ArrayList deliveryDays = new ArrayList();
        ArrayList alsoDates = new ArrayList();
        ArrayList exceptDates = new ArrayList();
        ArrayList holidayDates = new ArrayList();
        for(int ii=0; ii<_scheduleDetails.size(); ii++) {
            ScheduleDetailData sDetD = (ScheduleDetailData) _scheduleDetails.get(ii);
            String code = sDetD.getScheduleDetailCd();
            String value = sDetD.getValue();
            if(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK.equals(code) &&
                    _scheduleRule==WEEK_MONTHLY) {
                int weekInd = Integer.parseInt(value);
                if(weekInd<=5) {
                    deliveryDays.add(new Integer(weekInd));
                }
            } else if(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY.equals(code) &&
                    _scheduleRule==WEEK_MONTHLY) {
                _weekDayInd = Integer.parseInt(value);
            } else if(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY.equals(code) &&
                    _scheduleRule==WEEKLY) {
                int weekDayInd = Integer.parseInt(value);
                deliveryDays.add(new Integer(weekDayInd));
            } else if(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_DAY.equals(code) &&
                    _scheduleRule==DAY_MONTHLY) {
                int monthDayInd = Integer.parseInt(value);
                deliveryDays.add(new Integer(monthDayInd));
            } else if(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY.equals(code)) {
                _cutoffDay = Integer.parseInt(value);
                _cutoffDay += _accountCutoffDays;
            }
            
            else if(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME.equals(code)) {
                if (_scheduleHeader.getScheduleTypeCd().equals(RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE))
                	_cutoffTime = timeFormat24hour.parse(value);
                else 
                	_cutoffTime = timeFormat.parse(value);
            } else if(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE.equals(code)) {
                Date valueDate = dayFormat.parse(value);
                alsoDates.add(valueDate);
            } else if(RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(code)) {
                Date valueDate = dayFormat.parse(value);
                exceptDates.add(valueDate);
            } else if(RefCodeNames.SCHEDULE_DETAIL_CD.HOLIDAY.equals(code)) {
                Date valueDate = dayFormat.parse(value);
                holidayDates.add(valueDate);
            }
        }
        //Arrange arrays
        //delivery days
        _deliveryDays = sortInt(deliveryDays);
        //dates
        _alsoDates = sortDates(alsoDates);
        _curAlsoInd = 0;
        _exceptDates = sortDates(exceptDates);
        _curExceptInd = 0;
        _holidayDates = sortDates(holidayDates);
        _curHolidayInd = 0;

        //return
        _initialisedFlag = true;
        return;
    }
    //Account cutoff days
    public void setAccountCutoffDays(int pVal) {
        _accountCutoffDays = pVal;
    }
    public int getAccountCutoffDays() {
        return _accountCutoffDays;
    }
    //---------------------------------------------------------------------------
    //Get first date
    public GregorianCalendar getFirstDeliveryDate(Date pBegDate, Date pEndDate)
    throws Exception {
        calcFirstDeliveryDate(pBegDate,pEndDate);
        return _curDeliveryCal;
    }
    //Get first date
    public GregorianCalendar getFirstDeliveryDate(Date pBegDate, Date pEndDate, boolean pMoveToEffDateFl)
    throws Exception {
        Date begDate = pBegDate;
        if(pMoveToEffDateFl) {
            if(_effDate!=null && _effDate.after(pBegDate)) {
                begDate = _effDate;
            }
        }
        calcFirstDeliveryDate(begDate,pEndDate);
        return _curDeliveryCal;
    }
    //Get next date
    public GregorianCalendar getNextDeliveryDate()
    throws Exception {
        calcNextDeliveryDate();
        return _curDeliveryCal;
    }
    //get order delivery date
    public GregorianCalendar getOrderDeliveryDate(Date pOrderDate, Date pOrderTime)
    throws Exception {
        calcOrderDeliveryDate(pOrderDate, pOrderTime);
        return _curDeliveryCal;
    }
    
    //get order delivery pair
    public ScheduleOrderDates getFirstOrderDeliveryDates(Date pOrderDate, Date pOrderTime, boolean pMoveToEffDateFl)
    throws Exception {
        Date begDate = pOrderDate;
        Date begTime = pOrderTime;
        if(_effDate!=null && _effDate.after(pOrderDate)){
            begDate = _effDate;
            begTime = timeFormat.parse("0:0 AM");
        }
        calcOrderDeliveryDate(begDate, begTime);
        if(_curDeliveryCal==null) return null;
        Date cutoffDate = calcCutoffDate();
        ScheduleOrderDates orderPair = new ScheduleOrderDates(_curDeliveryCal.getTime(),cutoffDate);
        return orderPair;
    }
    
    //get order delivery pair
    public ScheduleOrderDates getOrderDeliveryDates(Date pOrderDate, Date pOrderTime)
    throws Exception {
        calcOrderDeliveryDate(pOrderDate, pOrderTime);
        if(_curDeliveryCal==null) {
            return null;
        }
        Date cutoffDate = calcCutoffDate();
        ScheduleOrderDates orderPair = new ScheduleOrderDates(_curDeliveryCal.getTime(),cutoffDate);
        return orderPair;
    }
    
    //get order delivery pair
    public ScheduleOrderDates getNextOrderDeliveryDates()
    throws Exception {
        calcNextDeliveryDate();
        if(_curDeliveryCal==null) return null;
        Date cutoffDate = calcCutoffDate();
        ScheduleOrderDates orderPair = new ScheduleOrderDates(_curDeliveryCal.getTime(),cutoffDate);
        return orderPair;
    }
    
    //-----------------------------------------------------------------------------
    private Date calcCutoffDate()
    throws Exception {
        if(_curDeliveryCal==null) return null;
        GregorianCalendar cutoffCal = (GregorianCalendar) _curDeliveryCal.clone();
        if(_cutoffDay==0) {
            return cutoffCal.getTime();
        }
        int days = 0;
        mmm:
            for(int ii=0; ii<_cutoffDay; ) {
                int wd = cutoffCal.get(Calendar.DAY_OF_WEEK);
                Date dd = cutoffCal.getTime();
                if(wd==1 || wd==7){
                    cutoffCal.add(Calendar.DATE,-1);
                    continue;
                }
                for(int holidayInd = _curHolidayInd;
                holidayInd>=0 && holidayInd<_holidayDates.length;
                holidayInd--) {
                    int cr = dd.compareTo(_holidayDates[holidayInd]);
                    if(cr==0) {
                        cutoffCal.add(Calendar.DATE,-1);
                        continue mmm;
                    }
                    if(cr>0) break;
                }
                for(int exceptInd = _curExceptInd;
                exceptInd>=0 && exceptInd<_exceptDates.length;
                exceptInd--) {
                    int cr = dd.compareTo(_exceptDates[exceptInd]);
                    if(cr==0) {
                        cutoffCal.add(Calendar.DATE,-1);
                        continue mmm;
                    }
                    if(cr>0) break;
                }
                cutoffCal.add(Calendar.DATE,-1);
                ii++;
            }
            //Scroll if not working day
            mmm1:
                while (true){
                    int wd = cutoffCal.get(Calendar.DAY_OF_WEEK);
                    Date dd = cutoffCal.getTime();
                    if(wd==1 || wd==7){
                        cutoffCal.add(Calendar.DATE,-1);
                        continue;
                    }
                    for(int holidayInd = _curHolidayInd;
                    holidayInd>=0 && holidayInd<_holidayDates.length;
                    holidayInd--) {
                        int cr = dd.compareTo(_holidayDates[holidayInd]);
                        if(cr==0) {
                            cutoffCal.add(Calendar.DATE,-1);
                            continue mmm1;
                        }
                        if(cr>0) break;
                    }
                    for(int exceptInd = _curExceptInd;
                    exceptInd>=0 && exceptInd<_exceptDates.length;
                    exceptInd--) {
                        int cr = dd.compareTo(_exceptDates[exceptInd]);
                        if(cr==0) {
                            cutoffCal.add(Calendar.DATE,-1);
                            continue mmm1;
                        }
                        if(cr>0) break;
                    }
                    break;
                }
                return cutoffCal.getTime();
    }
    
    //-----------------------------------------------------------------------------
    private void calcOrderDeliveryDate(Date pOrderDate, Date pOrderTime)
    throws Exception {
        Date orderDate = dayFormat.parse(dayFormat.format(pOrderDate));
        Date orderTime = timeFormat.parse(timeFormat.format(pOrderTime));
        GregorianCalendar earliestDateCal = new GregorianCalendar();
        earliestDateCal.setTime(orderDate);
        if(orderTime.after(_cutoffTime)) earliestDateCal.add(Calendar.DATE,1);
        scrollHolidayDates(earliestDateCal.getTime());
        scrollExceptDates(earliestDateCal.getTime());
        for(int ii=0; ii<_cutoffDay; ) {
            int wd = earliestDateCal.get(Calendar.DAY_OF_WEEK);
            Date dd = earliestDateCal.getTime();
            if(wd==1 || wd==7 || isHolidayDate(dd) || isExceptDate(dd) ) {
                earliestDateCal.add(Calendar.DATE,1);
                continue;
            }
            earliestDateCal.add(Calendar.DATE,1);
            ii++;
        }
        while(true) {
            int wd = earliestDateCal.get(Calendar.DAY_OF_WEEK);
            Date dd = earliestDateCal.getTime();
            if(wd==1 || wd==7 || isHolidayDate(dd) || isExceptDate(dd) ) {
                earliestDateCal.add(Calendar.DATE,1);
                continue;
            }
            break;
        }
        calcFirstDeliveryDate(earliestDateCal.getTime(),null);
    }
    
    //------------------------------------------------------------------------------
    private void calcFirstDeliveryDate(Date pBegDate, Date pEndDate)
    throws Exception {
        ScheduleOrderDates scheduleDates = null;
        Date caltFirstDelD = (_curDeliveryCal==null)?null:_curDeliveryCal.getTime();

        if(pBegDate==null) {
            _curDeliveryCal = null;
            return;
        }
        if(_effDate!=null && _effDate.after(pBegDate)) {
            _curDeliveryCal = null;
            return;
        } else {
            setupCal.setTime(pBegDate);
        }
        _begCal =
                new GregorianCalendar(setupCal.get(Calendar.YEAR),
                setupCal.get(Calendar.MONTH),
                setupCal.get(Calendar.DAY_OF_MONTH));

        _begCal.setFirstDayOfWeek(Calendar.SUNDAY);
        _begTime = timeFormat.parse(timeFormat.format(setupCal.getTime()));

        if(pEndDate==null && _expDate!=null ) {
            setupCal.setTime(_expDate);
        } else if(pEndDate==null) {
            setupCal.add(Calendar.YEAR, 10);
        } else if(_expDate!=null &&_expDate.before(pEndDate)) {
            setupCal.setTime(_expDate);
        } else {
            setupCal.setTime(pEndDate);
        }
        _endCal =
                new GregorianCalendar(setupCal.get(Calendar.YEAR),
                setupCal.get(Calendar.MONTH),
                setupCal.get(Calendar.DAY_OF_MONTH));
        _endCal.setFirstDayOfWeek(Calendar.SUNDAY);
        
        if(_endCal.before(_begCal)) {
            return;
        }
        
        //Setup exceptions
        Date begDate = _begCal.getTime();
        scrollAlsoDates(begDate);
        scrollExceptDates(begDate);
        scrollHolidayDates(begDate);
        GregorianCalendar wrkCal = new GregorianCalendar();
        
        //Create schedule dates
        boolean foundFl = false;
        if(_scheduleRule==WEEKLY){
            int weekDay = _begCal.get(Calendar.DAY_OF_WEEK);
            _curBegWeekCal = (GregorianCalendar) _begCal.clone();
            _curBegWeekCal.add(Calendar.DATE, 1-weekDay);
            mmm:
                for(int wkCount=0; foundFl == false && wkCount<=6; wkCount++) {
                    if(wkCount!=0) _curBegWeekCal.add(Calendar.DATE, 7);
                    for(_curCalendarIndex=0; _curCalendarIndex<_deliveryDays.length; _curCalendarIndex++) {
                        if(wkCount>0 || _deliveryDays[_curCalendarIndex]>=weekDay){
                            wrkCal = (GregorianCalendar) _curBegWeekCal.clone();
                            wrkCal.add(Calendar.DATE,_deliveryDays[_curCalendarIndex]-1);
                            foundFl = testScheduleDate(wrkCal);
                            if(foundFl) break mmm;
                        }
                    }
                }
        } else if(_scheduleRule==WEEK_MONTHLY){
            //  _weekDayInd
            _firstWeekMonthCal = (GregorianCalendar) _begCal.clone();
            int day = _begCal.get(Calendar.DAY_OF_MONTH);
            _firstWeekMonthCal.set(Calendar.DAY_OF_MONTH,1);
            mmm:
                for(int mntCount=0; mntCount<3; mntCount++) {
                    if(mntCount!=0) {
                        _firstWeekMonthCal.set(Calendar.DAY_OF_MONTH,1);
                        _firstWeekMonthCal.add(Calendar.MONTH,1);
                    }
                    int weekDay = _firstWeekMonthCal.get(Calendar.DAY_OF_WEEK);
                    int month = _firstWeekMonthCal.get(Calendar.MONTH);
                    if(weekDay<_weekDayInd) _firstWeekMonthCal.add(Calendar.DATE, _weekDayInd-weekDay);
                    if(weekDay>_weekDayInd) _firstWeekMonthCal.add(Calendar.DATE, 7+(_weekDayInd-weekDay));

                    for(_curCalendarIndex=0; _curCalendarIndex<_deliveryDays.length; _curCalendarIndex++) {
                        wrkCal = (GregorianCalendar) _firstWeekMonthCal.clone();
                        int weekNum = _deliveryDays[_curCalendarIndex]-1;
                        if(weekNum>0) {
                            wrkCal.add(Calendar.DATE,weekNum*7);
                        }
                        if(weekNum>=4 && month!=wrkCal.get(Calendar.MONTH)) {
                            wrkCal.add(Calendar.DATE,-7);
                        }
                        if(wrkCal.get(Calendar.DAY_OF_MONTH)<day) continue;
                        foundFl = testScheduleDate(wrkCal);
                        if(foundFl) break mmm;
                    }
                }
        }

        caltFirstDelD = (_curDeliveryCal==null)?null:_curDeliveryCal.getTime();
        //Check also dates
        foundFl |= assignAlsoDate();
        //check the end of the interval
        if(foundFl) {
            if(_curDeliveryCal.after(_endCal)){
                foundFl = false;
                _curDeliveryCal = null;
            }
        }else {
            _curDeliveryCal = null;
        }
        if (_curDeliveryCal != null) {
            _currMonth = _curDeliveryCal.get(Calendar.MONTH);
        }
        return;
    }
    
    //Get next date
    private void calcNextDeliveryDate()
    throws Exception {
        if(_curDeliveryCal==null) return;
        GregorianCalendar wrkCal = new GregorianCalendar();
        
        //Cretate schedule dates
        boolean foundFl = false;
        if(_scheduleRule==WEEKLY){
            if(!_exceptionFlag) _curCalendarIndex++;
            mmm:
                for(int wkCount=0; wkCount<50; wkCount++) {
                    if(wkCount!=0) _curBegWeekCal.add(Calendar.DATE, 7);
                    for(; _curCalendarIndex<_deliveryDays.length; _curCalendarIndex++) {
                        wrkCal = (GregorianCalendar) _curBegWeekCal.clone();
                        wrkCal.add(Calendar.DATE,_deliveryDays[_curCalendarIndex]-1);

                        if (_currMonth==-1||_currMonth - wrkCal.get(Calendar.MONTH) != 0) {

                            _currMonth = wrkCal.get(Calendar.MONTH);
                            initSiteDevileryWeekFilter();
                        }
                        foundFl = testScheduleDate(wrkCal);
                        if(foundFl) break mmm;
                    }
                    _curCalendarIndex = 0;
                }
        } else if(_scheduleRule==WEEK_MONTHLY){
            if(!_exceptionFlag) _curCalendarIndex++;
            mmm:
                for(int mntCount=0; mntCount<3; mntCount++) {
                    int month = _firstWeekMonthCal.get(Calendar.MONTH);
                    for(; _curCalendarIndex<_deliveryDays.length; _curCalendarIndex++) {
                        wrkCal = (GregorianCalendar) _firstWeekMonthCal.clone();
                        int weekNum = _deliveryDays[_curCalendarIndex]-1;
                        if(weekNum>0) {
                            wrkCal.add(Calendar.DATE,weekNum*7);
                        }
                        if(weekNum>=4 && month!=wrkCal.get(Calendar.MONTH)) {
                            wrkCal.add(Calendar.DATE,-7);
                        }
                        foundFl = testScheduleDate(wrkCal);
                        if(foundFl) break mmm;
                    }
                    _curCalendarIndex = 0;
                    _firstWeekMonthCal.set(Calendar.DAY_OF_MONTH,1);
                    _firstWeekMonthCal.add(Calendar.MONTH,1);
                    int weekDay = _firstWeekMonthCal.get(Calendar.DAY_OF_WEEK);
                    if(weekDay<_weekDayInd) _firstWeekMonthCal.add(Calendar.DATE, _weekDayInd-weekDay);
                    if(weekDay>_weekDayInd) _firstWeekMonthCal.add(Calendar.DATE, 7+(_weekDayInd-weekDay));
                }
        } else {
            _curDeliveryCal = null;
        }
        foundFl |= assignAlsoDate();
        //check the end of the interval
        if(foundFl) {
            if(_curDeliveryCal.after(_endCal)){
                foundFl = false;
                _curDeliveryCal = null;
            }
        }else{
            _curDeliveryCal = null;
        }
        return;
    }

    private void initSiteDevileryWeekFilter() {

        _siteDeliveryWeekFilter = new int[7][5];
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 5; j++)
                _siteDeliveryWeekFilter[i][j] = -1;
    }

    private boolean testScheduleDate(GregorianCalendar pCal) {
         if(isWeekOk(pCal)) {
            if(isHolidayDate(pCal.getTime()) ||
                    isExceptDate(pCal.getTime())) {
                                pCal = getDateSubst(pCal);
                if(pCal!=null) {
                    _curDeliveryCal = (GregorianCalendar) pCal.clone();
                    _exceptionFlag = false;
                    return true;
                }
            }else{ 
                _curDeliveryCal = (GregorianCalendar) pCal.clone();
                _exceptionFlag = false;
                int thisWeek = _curDeliveryCal.get(Calendar.WEEK_OF_YEAR);                
                if ( 2 == _cycle ) {
                    if(_scheduleRule==WEEKLY) {
                        long effMills = _effDateWkBegCal.getTime().getTime();
                        long currMills = _curDeliveryCal.getTime().getTime();
                        long diffWeeks = (currMills-effMills)/(1000*3600*24*7);
                        if((diffWeeks % _cycle)==0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    /*
                    if (( _weekOfDel % 2 ) == 0 &&
                            (thisWeek  % 2 ) != 0 ) {
                        // Even weeks configured.
                        // This is not an even week.
                        return false;
                    }
                    if (( _weekOfDel % 2 ) != 0 &&
                            (thisWeek  % 2 ) == 0 ) {
                        // Odd weeks configured.
                        // This is not an odd week.
                        return false;
                    }
                     */
                }
                return true;
            }
        }
        _curDeliveryCal = null;
        return false;
    }
    
    //----------------------------------------------------------------------------
    //Check also dates
    private boolean assignAlsoDate() {
        boolean assignedFl = false;
        GregorianCalendar wrkCal = new GregorianCalendar();
        while (_curAlsoDate!=null) {
            if(_curDeliveryCal!=null && !_curAlsoDate.before(_curDeliveryCal.getTime())) break;
            wrkCal.setTime(_curAlsoDate);
            if(isWeekOk(wrkCal)) {
                _curDeliveryCal = new GregorianCalendar();
                _curDeliveryCal.setTime(_curAlsoDate);
                _exceptionFlag = true;
                nextAlsoDate();
                assignedFl = true;
                break;
            }
            nextAlsoDate();
        }
        return assignedFl;
    }
    //----------------------------------------------------------------------------
    private boolean isWeekOk(GregorianCalendar pCal) {
        if (isHolidayDate(pCal.getTime()) || isExceptDate(pCal.getTime())) {
            return false;
        }

        if(_siteDeliverySchedule!=null) {
            String intervalS = _siteDeliverySchedule.getIntervWeek();
            int interval = 0;
            if(Utility.isSet(intervalS)) {
                try {
                    interval = Integer.parseInt(intervalS);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
            if (interval>0) {
                long curMills = pCal.getTime().getTime();
                long begMills = _effDateWkBegCal.getTime().getTime();
                long diffDays = (curMills - begMills)/(1000*60*60*24);
                long diffWeeks = diffDays/7;
                long daysInWeek = (diffDays%7)-1;
                if((diffWeeks%interval)==0) {
                    return true;
                }

                //        + " _weekFilter2[weekOfYr-1]=" + _weekFilter2[weekOfYr-1]);
                //if (_weekFilter2[weekOfYr-1] == -1 ) {
                  //  return true;
                //}
            }
        }
        if(!_applyWeekFilter) return true;

        int day = pCal.get(Calendar.DAY_OF_MONTH); 

        int week = 0;
        if(day<=7) {
            week = 0;
        } else if(day<=14) {
            week = 1;
        } else if(day<=21) {
            week = 2;
        } else if(day<=28) {
            week = 3;
        } else {
            week = 4;
        }

        if(_weekFilter[week]>=0) {
            return true;
        } 

        if(_weekFilter[4]>=0) {
            int month = pCal.get(Calendar.MONTH);
            GregorianCalendar wrkCal = (GregorianCalendar) pCal.clone();            
            wrkCal.add(Calendar.DATE, 7);
            if(month!=wrkCal.get(Calendar.MONTH)) {
                return true;
            }            
        }
        
        if(_cycle==2 && _scheduleRule==WEEKLY) {
            if(week>=1 && _weekFilter[week-1]>=0) {
                return true;
            }            
            if(week==0 && _weekFilter[4]>=0) {
                return true;
            }
            if(week==0 && _weekFilter[3]>=0) {
                GregorianCalendar wrkCal = (GregorianCalendar) pCal.clone();            
                wrkCal.add(Calendar.DATE, -7);
                int dd = wrkCal.get(Calendar.DAY_OF_MONTH); 
                if(dd<=28) {
                    return true;
                }
            }
        }
        
        /*
        if(_siteDeliveryWeekFilter!=null
                &&_cycle>=1&&week>=_cycle
                &&_scheduleRule == WEEKLY)
        {
            return isSiteDevilery(week,pCal);
        }
        */
        return false;

    }

    public boolean isSiteDevilery(int week, GregorianCalendar pCal) {
        for (int i = _cycle; i <= week; i = i + _cycle) {
            if (_weekFilter[week - i] == 0 && _siteDeliveryWeekFilter[pCal.get(Calendar.DAY_OF_WEEK) - 1][week - i] != 0) {

                if (!(isHolidayDate(pCal.getTime()) || isExceptDate(pCal.getTime()))) {
                    _siteDeliveryWeekFilter[pCal.get(Calendar.DAY_OF_WEEK) - 1][week - i] = 0;
                    return true;
                }
           }
        }
        return false;
    }
    //---------------------------------------------------------------------------
    private GregorianCalendar getDateSubst(GregorianCalendar pCal) {
        return null;
    }
    
    //---------------------------------------------------------------------------
    private void scrollAlsoDates(Date pEarlyDate){
        _curAlsoDate = null;
        for(; _curAlsoInd<_alsoDates.length; _curAlsoInd++) {
            if(!pEarlyDate.after(_alsoDates[_curAlsoInd])) {
                _curAlsoDate = _alsoDates[_curAlsoInd];
                _curAlsoInd++;
                break;
            }
        }
    }
    private void nextAlsoDate(){
        _curAlsoDate = null;
        if(_curAlsoInd<_alsoDates.length) {
            _curAlsoDate = _alsoDates[_curAlsoInd];
            _curAlsoInd++;
        }
    }
    //---------------------------------------------------------------------------
    private boolean isExceptDate(Date pCurDate) {
        if(_curExceptDate==null) return false;
        if(_curExceptDate.before(pCurDate)) {
            scrollExceptDates(pCurDate);
        }
        if(_curExceptDate!=null && _curExceptDate.equals(pCurDate)) return true;
        return false;
    }
    //---------------------------------------------------------------------------
    private void scrollExceptDates(Date pEarlyDate){
        if (_curExceptInd==0)_curExceptDate = null;
        for(; _curExceptInd<_exceptDates.length; _curExceptInd++) {
            if(!pEarlyDate.after(_exceptDates[_curExceptInd])) {
                _curExceptDate = _exceptDates[_curExceptInd];
                _curExceptInd++;
                break;
            }
        }
    }
    
    //---------------------------------------------------------------------------
    private boolean isHolidayDate(Date pCurDate) {
        if(_curHolidayDate==null) return false;
        if(_curHolidayDate.before(pCurDate)) {
            scrollHolidayDates(pCurDate);
        }
        if(_curHolidayDate!=null && _curHolidayDate.equals(pCurDate)) return true;
        return false;
    }
    //---------------------------------------------------------------------------
    private void scrollHolidayDates(Date pEarlyDate){
        _curHolidayDate = null;
        for(; _curHolidayInd<_holidayDates.length; _curHolidayInd++) {
            if(!pEarlyDate.after(_holidayDates[_curHolidayInd])) {
                _curHolidayDate = _holidayDates[_curHolidayInd];
                break;
            }
        }
    }
    //---------------------------------------------------------------------------
    private Date[] sortDates(ArrayList pDates) {
        if(pDates==null)  return new Date[0];
        Date[] dateA = new Date[pDates.size()];
        for(int ii=0; ii<dateA.length; ii++) {
            dateA[ii] = (Date) pDates.get(ii);
        }
        if(dateA.length<=1) return dateA;
        for(int ii=0; ii<dateA.length-1;ii++) {
            boolean endFl = true;
            for(int jj=0; jj<dateA.length-ii-1; jj++) {
                Date dd1 = dateA[jj];
                Date dd2 = dateA[jj+1];
                if(dd1.after(dd2)) {
                    endFl = false;
                    dateA[jj] = dd2;
                    dateA[jj+1] = dd1;
                }
            }
            if(endFl) break;
        }
        return dateA;
    }
    
    private int[] sortInt(ArrayList pValues) {
        if(pValues==null)  return new int[0];
        int[] intA = new int[pValues.size()];
        for(int ii=0; ii<intA.length; ii++){
            Integer valI = (Integer) pValues.get(ii);
            intA[ii] = valI.intValue();
        }
        if(intA.length<=1) return intA;
        for(int ii=0; ii<intA.length-1;ii++) {
            boolean endFl = true;
            for(int jj=0; jj<intA.length-ii-1; jj++) {
                int dd1 = intA[jj];
                int dd2 = intA[jj+1];
                if(dd1>dd2) {
                    endFl = false;
                    intA[jj] = dd2;
                    intA[jj+1] = dd1;
                }
            }
            if(endFl) break;
        }
        return intA;
    }
    
    
    
    
    //////////////////////////////////////////////////////////////////////////////
  /*
   }
     int[] weeks = new int[5];
     if(pWeekFilter==null) {
       for(int ii=0; ii<5; ii++) {
         weeks[ii]=0;
       }
     } else {
       for(int ii=0; ii<5; ii++) {
         weeks[ii]=-1;
       }
       for(int ii=0; ii<pWeekFilter.length; ii++){
         int wf = pWeekFilter[ii];
         if(wf>=0 && wf<=4) weeks[wf]=0;
       }
     }
     boolean matchFl = false;
     int weekDay = 0;
     int cutOffDay = 0;
     String cutOffTime = "";
     ArrayList alsoDates = new ArrayList();
     ArrayList exceptDates = new ArrayList();
     ArrayList holidayDates = new ArrayList();
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     if(RefCodeNames.SCHEDULE_RULE_CD.WEEK.equals(scheduleRule)) {
         matchFl = true;
         for(int ii=0; ii<5; ii++) {
           if(weeks[ii]==0) weeks[ii]=1;
         }
       }
       for(int ii=0; ii<scheduleDetDV.size(); ii++) {
         ScheduleDetailData sDetD = (ScheduleDetailData) scheduleDetDV.get(ii);
         String code = sDetD.getScheduleDetailCd();
         String value = sDetD.getValue();
         if(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK.equals(code)) {
           int weekInd = Integer.parseInt(value)-1;
           if(weekInd<5 && (weeks[weekInd]==0 || siteDeliveryScheduleFl==false)) {
             weeks[weekInd]=1;
             matchFl = true;
           }
         }
         else if(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY.equals(code)) {
           weekDay = Integer.parseInt(value);
         }
         else if(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY.equals(code)) {
           cutOffDay = Integer.parseInt(value);
         }
         else if(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME.equals(code)) {
           cutOffTime = value;
         }
         else if(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE.equals(code)) {
           Date valueDate = sdf.parse(value);
           if(!valueDate.before(pBegDate) && !valueDate.after(pEndDate)) {
             alsoDates.add(valueDate);
           }
         }
         else if(RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(code)) {
           Date valueDate = sdf.parse(value);
           if(!valueDate.before(pBegDate) && !valueDate.after(pEndDate)) {
             exceptDates.add(valueDate);
           }
         }
         else if(RefCodeNames.SCHEDULE_DETAIL_CD.HOLIDAY.equals(code)) {
           Date valueDate = sdf.parse(value);
           if(!valueDate.before(pBegDate) && !valueDate.after(pEndDate)) {
             holidayDates.add(valueDate);
           }
         }
       }
       if(!matchFl) {
         String errorMess = "Site schedule (site id = "+pSiteId+") "+
         "does not match to distrubutor schedule (schedule id = "+scheduleId+")";
         throw new Exception(errorMess);
       }
       if(weekDay==0 || weekDay>7) {
         String errorMess = "Schedule does not have week day attribute. "+
            "Schedule id: "+scheduleId;
         throw new Exception(errorMess);
       }
       if(cutOffDay<=0) {
         String errorMess = "Schedule does not have cut off day attribute. "+
            "Schedule id: "+scheduleId;
         throw new Exception(errorMess);
       }
   */
    //;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
/*
     //Cretate schedule dates
       SimpleDateFormat sdfMonth = new SimpleDateFormat("M");
       String begMonthS = sdfMonth.format(pBegDate);
       int begMonth = Integer.parseInt(begMonthS)-1;
       String endMonthS = sdfMonth.format(pEndDate);
       int endMonth = Integer.parseInt(endMonthS)-1;
       int begCycleMonth = begMonth;
       int endCycleMonth = 11;
       for(int year=begYear; year<=endYear; year++) {
         if(year==endYear) endCycleMonth = endMonth;
         for(int month = begCycleMonth; month<=endCycleMonth; month++) {
           for(int jj=0; jj<5; jj++) {
             if(weeks[jj]!=1) continue;
             GregorianCalendar wrkDateCal = null;
             if(jj<4) {
               wrkDateCal = new GregorianCalendar(year,month,1);
               int firstWeekDay = wrkDateCal.get(Calendar.DAY_OF_WEEK);
               int day = 0;
                 if(firstWeekDay<=weekDay) {
                   day = jj*7-firstWeekDay+1+weekDay;
                 }  else {
                   day = (jj+1)*7-firstWeekDay+1+weekDay;
                 }
               wrkDateCal = new GregorianCalendar(year,month,day);
             }else{//last week
               wrkDateCal = new GregorianCalendar(year,month+1,1);
               wrkDateCal.add(Calendar.DATE, -1);
               int lastWeekDay = wrkDateCal.get(Calendar.DAY_OF_WEEK);
               int dayAdd = 0;
               if(lastWeekDay>=weekDay) {
                dayAdd = weekDay-lastWeekDay;
               } else{
                dayAdd = weekDay-(lastWeekDay+7);
               }
               wrkDateCal.add(Calendar.DATE, dayAdd);
             }
             Date deliveryDate = wrkDateCal.getTime();
             if(deliveryDate != null &&
                (schEffDate != null && schEffDate.after(deliveryDate) ||
                schExpDate != null && !schExpDate.after(deliveryDate))) {
               deliveryDate = null;
             }
 
 
             if(deliveryDate!= null &&
                (isDateInList(deliveryDate, exceptDates) ||
                isDateInList(deliveryDate, holidayDates))){
                //Try to find sustitution date
                if(siteDeliveryScheduleFl) {
                  deliveryDate = getDateSubstitution(deliveryDate, alsoDates, 5);
                }else{//we will add all additional delivery dates
                  deliveryDate = null;
                }
 
             }
             if(deliveryDate!=null) {
                Date cutoffDate =
                  calcCutoffDate(deliveryDate, cutOffDay, exceptDates, holidayDates);
                ScheduleOrderDates scheduleOrderDates =
                    new  ScheduleOrderDates( deliveryDate, cutoffDate);
                scheduleDatesVector.add(scheduleOrderDates);
             }
           }
         }
         begCycleMonth=0;
       }
       if(!siteDeliveryScheduleFl) {//Add all also date
         for(int ii=0; ii<alsoDates.size(); ii++) {
            Date deliveryDate = (Date) alsoDates.get(ii);
            Date cutoffDate =
                  calcCutoffDate(deliveryDate, cutOffDay, exceptDates, holidayDates);
                ScheduleOrderDates scheduleOrderDates =
                    new  ScheduleOrderDates( deliveryDate, cutoffDate);
                scheduleDatesVector.add(scheduleOrderDates);
 
         }
       }
     } catch (Exception e) {
         e.printStackTrace();
         throw new RemoteException("SiteBean.getOrderScheduleDates: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
     scheduleDatesVector =  sortScheduleOrderDates(scheduleDatesVector);
     return scheduleDatesVector;
   }
 */
    
    
    private Date calcCutoffDate(Date pDate, int pCutoffDays,
            ArrayList pExceptDates, ArrayList pHolidayDates) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(pDate);
        int count = pCutoffDays;
        while(count>0) {
            cal.add(Calendar.DATE,-1);
            Date calDate = cal.getTime();
            int wd = cal.get(Calendar.DAY_OF_WEEK);
            if(wd==1 || wd==7) continue;
            if(isDateInList(calDate,pExceptDates) ||
                    isDateInList(calDate,pHolidayDates)) continue;
            count--;
        }
        return cal.getTime();
        
    }
    
    /**
     *  Gets the nearst date inside interval
     *
     *@param  pDate      Description of the Parameter
     *@param  pDateList  Description of the Parameter
     *@param  pDaysAfter
     *@return            The dateInList value
     */
    private Date getDateSubstitution(Date pDate, ArrayList pDateList, int pDaysAfter) {
        boolean ret = false;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(pDate);
        GregorianCalendar calAfter = (GregorianCalendar) cal.clone();
        calAfter.add(Calendar.DATE, pDaysAfter);
        Date dateAfter = calAfter.getTime();
        Date dateSubst = null;
        long diff = 0;
        for (int ii = 0; ii < pDateList.size(); ii++) {
            Date dd = (Date) pDateList.get(ii);
            if(!dd.before(pDate) &&
                    !dd.after(dateAfter)) {
                if(dateSubst==null || dd.getTime()-pDate.getTime() < diff) {
                    dateSubst = dd;
                }
            }
        }
        return dateSubst;
    }
    
    
    /**
     *  Gets the dateInList attribute of the DistributorBean object
     *
     *@param  pDate      Description of the Parameter
     *@param  pDateList  Description of the Parameter
     *@return            The dateInList value
     */
    private boolean isDateInList(Date pDate, ArrayList pDateList) {
        boolean ret = false;
        for (int ii = 0; ii < pDateList.size(); ii++) {
            Date dd = (Date) pDateList.get(ii);
            if (dd.equals(pDate)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

	public Date getCutoffTime() {
		return _cutoffTime;
	}
    
}
