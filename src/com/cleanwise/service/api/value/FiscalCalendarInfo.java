package com.cleanwise.service.api.value;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.FiscalCalendarUtility;

public class FiscalCalendarInfo implements Serializable{
	FiscalCalenderView mFiscalCalenderView;
	HashMap mBudgetPeriodDates;
	int mNumberOfBudgetPeriods = 0;
	
	/**
	 * Creates a new FiscalCalendarInfo object based off the supplied 
	 * FiscalCalenderData.
	 * @param pFiscalCalenderView to use as the underlying data element
	 */
	public FiscalCalendarInfo(FiscalCalenderView pFiscalCalenderView){
		mFiscalCalenderView = pFiscalCalenderView;
		mBudgetPeriodDates = this.calculateBudgetDates();
		if(mBudgetPeriodDates ==null){
			mBudgetPeriodDates= new HashMap();
		}
	}
	
	/**
	 * @return the underlying FiscalCalenderData object used to constuct this object
	 */
	public FiscalCalenderView getFiscalCalenderView(){
		return mFiscalCalenderView;
	}
	
	/**
	 * Used for historical purposes.
	 * @return a populated HahMap using the period as the key (1-13)
	 * and containing a FiscalCalendarInfo.BudgetPeriodInfo as the value
	 */
	public HashMap getBudgetPeriodsAsHashMap(){
		return mBudgetPeriodDates;
	}
	
	/**
	 * Returns the budget period info for the underlying calendar's supplied period
	 * @param pFiscalPeriod the period to fetch
	 * @return
	 */
	public FiscalCalendarInfo.BudgetPeriodInfo getBudgetPeriod(int pFiscalPeriod){
		if(pFiscalPeriod == 0){
			Thread.currentThread().dumpStack();
			return null;
		}
		return (FiscalCalendarInfo.BudgetPeriodInfo) mBudgetPeriodDates.get(new Integer(pFiscalPeriod));
	}
	
	
    public int getNumberOfBudgetPeriods(){
		return mNumberOfBudgetPeriods;
    }
    private void setNumberOfBudgetPeriods(int numberOfPeriods){
		mNumberOfBudgetPeriods = numberOfPeriods;
    }
    
    /**
     * Gets the Mmdd value from the underlying fiscal calender.  Will be of the form
     * 01/01 for January 1.
     */
    private String getMmdd(int period) {
        String toReturn = null;
        if (mFiscalCalenderView != null) {
            try {
                return FiscalCalendarUtility.getMmdd(mFiscalCalenderView.getFiscalCalenderDetails(), period);
            } catch (Exception e) {
            }
        }
        return toReturn;
    }
    	
    
    /**
     *initializes the budget period calculations
     */
    private HashMap calculateBudgetDates(){
        HashMap binfo = new HashMap();
	    try {
			SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy"); 
			
			
			if(mFiscalCalenderView == null){
				return binfo;
			}
			GregorianCalendar cal = new GregorianCalendar();
			int year = cal.get(Calendar.YEAR); //current year
			int fiscalYear = mFiscalCalenderView.getFiscalCalender().getFiscalYear();
			String mmdd1 =  getMmdd(1);
			if(!Utility.isSet(mmdd1)) {
				return binfo;
			}
			if(fiscalYear>0) {
				if(janFirstFl(mmdd1)) {
					year = fiscalYear;
				} else {
					Date effDate = mFiscalCalenderView.getFiscalCalender().getEffDate();
					cal.setTime(effDate);
					year = cal.get(Calendar.YEAR);
				}
			}
			//int numPeriods = getNumberOfBudgetPeriods();
			Date wrkDate = formater.parse(mmdd1 + "/"+year);
			int periodNum = 1;
			while(true){
				String mmdd = getMmdd(periodNum+1);        		
				if(!Utility.isSet(mmdd)) {
					break;
				}
				Date periodDate = formater.parse(mmdd + "/"+year);
				if(!wrkDate.before(periodDate)) {
					year++;
					periodDate = formater.parse(mmdd + "/"+year);
				}	
				cal.setTime(periodDate);
				cal.add(Calendar.DAY_OF_MONTH,-1);
				BudgetPeriodInfo period = new BudgetPeriodInfo(periodNum,wrkDate,cal.getTime());
				binfo.put(periodNum,period);
				wrkDate = periodDate;
				periodNum++;
			}
			// Get the last period. Pretty stupid way to calc end date. Should analyze the next calendar but better to use the experation date
			Date endDate = formater.parse(mmdd1 + "/"+year);
			if(!wrkDate.before(endDate)) {
				year++;
				endDate = formater.parse(mmdd1 + "/"+year);
			}			
			
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_MONTH,-1);
			BudgetPeriodInfo period = new BudgetPeriodInfo(periodNum,wrkDate,cal.getTime());
			binfo.put(periodNum,period); 
			setNumberOfBudgetPeriods(periodNum);
		} catch (Exception exc) {
			exc.printStackTrace();
			binfo = new HashMap();
		}
        return binfo;
    }
	private boolean janFirstFl(String mmdd) {
		String[] aa = mmdd.split("/");
		boolean retFl = true;
		for(int ii=0; ii<aa.length; ii++) {
			int jj = Integer.parseInt(aa[ii].trim());
			if(jj!=1)  {
			   retFl = false;
			   break;
			}
		}
		return retFl;
		
	}
    
    /**
     * Represents a single budget period
     */
    public class BudgetPeriodInfo implements Serializable  {
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd"); 
    	SimpleDateFormat sdfY = new SimpleDateFormat("MM/dd/yyyy"); 
        private int mBudgetPeriod;
        private Date mStartDate;
        private Date mEndDate;
        
        /**
         * initializes a new BudgetPeriodInfo object
         * @param pBudgetPeriod the budget period this object represents
         * @param startDate the start date of this period
         * @param endDate the end date of this period
         */
        private BudgetPeriodInfo (int pBudgetPeriod,Date startDate, Date endDate){
        	mBudgetPeriod = pBudgetPeriod;
        	mStartDate = startDate;
        	mEndDate = endDate;
        }

        /**
         * @return the budget period that this object represents
         */
        public int getBudgetPeriod(){
        	return mBudgetPeriod;
        }
        
        /**
         * Gets the start date formated as it is stored in the db, and suitable
         * for use accross years (mm/dd)
         */
        public String getStartDateMmdd() {
        	if(mStartDate == null){
        		return null;
        	}
        	return sdf.format(mStartDate); 
        }
        
        /**
         * Gets the end date formated as it is stored in the db, and suitable
         * for use accross years (mm/dd)
         */
        public String getEndDateMmdd() {
        	if(mEndDate == null){
        		return null;
        	}
        	return sdf.format(mEndDate); 
        }
        
        
        public Date getStartDate(){return mStartDate;}
        public Date getEndDate(){return mEndDate;}
        
        public String toString() {
            String s = "[ BInfo " +
                       " [ mBudgetPeriod=" + mBudgetPeriod + ", " +
                       "mStartDate=" + sdfY.format(mStartDate) + ", " +
                       "mEndDate=" + sdfY.format(mEndDate) + " ]]";
            return s;
        }

    }
}
