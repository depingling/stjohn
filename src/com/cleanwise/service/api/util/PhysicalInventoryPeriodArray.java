package com.cleanwise.service.api.util;

import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Copyright:   Copyright (c) 2009
 * Company:     CleanWise, Inc.
 * @author      
 *
 */

public class PhysicalInventoryPeriodArray {

    public PhysicalInventoryPeriodArray(ArrayList<PhysicalInventoryPeriod> periods) {
		_periods = periods;
        _rawPeriods = null;
        _isErrorsInRawPeriods = false;
    }

    public PhysicalInventoryPeriodArray() {
		this(null);
	}

    public ArrayList<PhysicalInventoryPeriod> getPeriods() {
        return _periods;
    }

    public void setPeriods(ArrayList<PhysicalInventoryPeriod> periods) {
        _periods = periods;
    }

    public void clear() {
        _periods = null;
        _rawPeriods = null;
        _isErrorsInRawPeriods = false;
    }

    public boolean isEmpty() {
        if (_periods == null) {
            return true;
        }
        if (_periods.size() == 0) {
            return true;
        }
        return false;
    }

    public void startLoadingItems() {
        _rawPeriods = new ArrayList<DateItemInfo>();
        _isErrorsInRawPeriods = false;
    }

    public boolean addItem(String dateType, String dateValue) {
        if (dateType == null || dateValue == null) {
            return false;
        }
        if (dateType == null || dateValue == null) {
            return false;
        }
        int dateTypeCode = DateItemInfo.PHYSICAL_INV_UNKNOWN;
        if (RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE.equalsIgnoreCase(dateType)) {
            dateTypeCode = DateItemInfo.PHYSICAL_INV_START_DATE;
        } 
        else if (RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE.equalsIgnoreCase(dateType)) {
            dateTypeCode = DateItemInfo.PHYSICAL_INV_END_DATE;
        } 
        else if (RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE.equalsIgnoreCase(dateType)) {
            dateTypeCode = DateItemInfo.PHYSICAL_INV_FINAL_DATE;
        }
        if (dateTypeCode != DateItemInfo.PHYSICAL_INV_START_DATE && 
            dateTypeCode != DateItemInfo.PHYSICAL_INV_END_DATE && 
            dateTypeCode != DateItemInfo.PHYSICAL_INV_FINAL_DATE) {
            _isErrorsInRawPeriods = true;
            return false;
        }
        Date date = PhysicalInventoryPeriod.parseDate(dateValue);
        if (date == null) {
            _isErrorsInRawPeriods = true;
            return false;
        }
        if (_rawPeriods == null) {
            return false;
        }
        _rawPeriods.add(new DateItemInfo(dateTypeCode, date));
        return true;
    }

    public boolean finishLoadingItems() {
        if (_rawPeriods == null) {
            return false;
        }
        if (_isErrorsInRawPeriods) {
            return false;
        }
        if (_rawPeriods.size() == 0) {
            return true;
        }
        Collections.sort(_rawPeriods, new DateItemInfoComparator());
        int dateTypePrev = DateItemInfo.PHYSICAL_INV_FINAL_DATE;
        boolean isOk = true;
        boolean isComplete = false;
        for (int i = 0; i < _rawPeriods.size(); ++i) {
            int dateTypeCurr = _rawPeriods.get(i).getDateType();
            if (i == 0) {
                if (dateTypeCurr != DateItemInfo.PHYSICAL_INV_START_DATE) {
                    isOk = false;
                    break;
                }
            }
            if (i == _rawPeriods.size() - 1) {
                if (dateTypeCurr != DateItemInfo.PHYSICAL_INV_FINAL_DATE) {
                    isOk = false;
                    break;
                }
            }
            switch (dateTypeCurr)
            {
                case DateItemInfo.PHYSICAL_INV_START_DATE:
                    isComplete = false;
                    if (dateTypePrev != DateItemInfo.PHYSICAL_INV_FINAL_DATE) {
                        isOk = false;
                    }
                    break;
                case DateItemInfo.PHYSICAL_INV_END_DATE:
                    isComplete = false;
                    if (dateTypePrev != DateItemInfo.PHYSICAL_INV_START_DATE) {
                        isOk = false;
                    }
                    break;
                case DateItemInfo.PHYSICAL_INV_FINAL_DATE:
                    isComplete = true;
                    if (dateTypePrev != DateItemInfo.PHYSICAL_INV_END_DATE) {
                        isOk = false;
                    }
                    break;
            }
            if (!isOk) {
                break;
            }
            dateTypePrev = dateTypeCurr;
        }
        if (isOk && isComplete) {
            int index = 0;
            _periods = new ArrayList<PhysicalInventoryPeriod>();
            while (index < _rawPeriods.size()) 
            {
                _periods.add(new PhysicalInventoryPeriod(
                    _rawPeriods.get(index).getDateValue(),
                    _rawPeriods.get(index + 1).getDateValue(),
                    _rawPeriods.get(index + 2).getDateValue()));
                index += 3;
            }
        }
        return (isOk && isComplete);
    }

    public String toString() {
        if (_periods == null) {
            return "";
        }
        if (_periods.size() == 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        PhysicalInventoryPeriod period = null;
        for (int i = 0; i < _periods.size(); ++i) {
            period = _periods.get(i);
            if (period == null) {
                continue;
            }
            buffer.append("(");
            buffer.append(period.toString());
            buffer.append(") ");
        }
        return buffer.toString();
    }

    public static boolean checkData(PhysicalInventoryPeriodArray dates) {
        if (dates == null) {
            return false;
        }
        if (dates.getPeriods() == null) {
            return false;
        }
        PhysicalInventoryPeriod period;
        for (int i = 0; i < dates.getPeriods().size(); ++i) {
            period = (PhysicalInventoryPeriod)dates.getPeriods().get(i);
            if (!PhysicalInventoryPeriod.checkData(period)) {
                return false;
            }
        }
        return true;
    }

    private static void parseBlankSpaces(String text, ParsePosition position) {
        if (text == null || position == null) {
            return;
        }
        while (position.getIndex() < text.length() && 
               (text.charAt(position.getIndex()) == ' ' || 
                text.charAt(position.getIndex()) == '\r' || 
                text.charAt(position.getIndex()) == '\n')) {
            position.setIndex(position.getIndex() + 1);
        }
    }

    private static PhysicalInventoryPeriod loadOnePeriod(String text, ParsePosition position) {
        if (text == null) {
            return null;
        }
        if (position == null) {
            return null;
        }
        if (position.getIndex() >= text.length()) {
            return null;
        }
        PhysicalInventoryPeriod period = null;
        do
        {
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                break;
            }
            if (text.charAt(position.getIndex()) != '(') {
                break;
            } else {
                position.setIndex(position.getIndex() + 1);
            }
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                break;
            }
            period = PhysicalInventoryPeriod.parse(text, position);
            if (period == null) {
                break;
            }
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                period = null;
                break;
            }
            if (text.charAt(position.getIndex()) != ')') {
                period = null;
                break;
            } else {
                position.setIndex(position.getIndex() + 1);
            }
        }
        while (false);
        return period;
    }

    public boolean loadFrom(String text) {
        if (text == null) {
            return false;
        }
        ArrayList<PhysicalInventoryPeriod> periods = 
            new ArrayList<PhysicalInventoryPeriod>();
        boolean isParsedSuccessfully = true;
        ParsePosition position = new ParsePosition(0);
        PhysicalInventoryPeriod period = null;
        while (position.getIndex() < text.length())
        {
            period = loadOnePeriod(text, position);
            if (period == null) {
                isParsedSuccessfully = false;
                break;
            }
            periods.add(period);
            parseBlankSpaces(text, position);
            if (position.getIndex() < text.length() && 
                text.charAt(position.getIndex()) == ',') {
                position.setIndex(position.getIndex() + 1);
            }
            parseBlankSpaces(text, position);
        }
        if (isParsedSuccessfully) {
            setPeriods(periods);
        }
        return isParsedSuccessfully;
    }

    public boolean containsDate(Calendar calendarTemplate, Calendar calendarToCmp) {
        if (_periods == null) {
            return false;
        }
        if (_periods.size() == 0) {
            return false;
        }
        if (calendarTemplate == null || calendarToCmp == null) {
            return false;
        }
        Calendar calendar1 = (Calendar)calendarTemplate.clone();
        Calendar calendar2 = (Calendar)calendarTemplate.clone();
        for (int i = 0; i < getPeriods().size(); ++i) {
            PhysicalInventoryPeriod period = getPeriods().get(i);
            calendar1.setTime(period.getStartDate());
            calendar2.setTime(period.getAbsoluteFinishDate());
            if (calendarToCmp.compareTo(calendar1) >= 0 && calendarToCmp.compareTo(calendar2) <= 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the current physical inventory period if it exists.  If not it will return null.
     * @param calendarTemplate
     * @param calendarToCmp
     * @return
     */
    public PhysicalInventoryPeriod getCurrentPhysicalInventoryPeriod(Calendar calendarTemplate, Calendar calendarToCmp) {
        if (_periods == null) {
            return null;
        }
        if (_periods.size() == 0) {
            return null;
        }
        if (calendarTemplate == null || calendarToCmp == null) {
            return null;
        }
        Calendar calendar1 = (Calendar)calendarTemplate.clone();
        Calendar calendar2 = (Calendar)calendarTemplate.clone();
        for (int i = 0; i < getPeriods().size(); ++i) {
            PhysicalInventoryPeriod period = getPeriods().get(i);
            calendar1.setTime(period.getStartDate());
            calendar2.setTime(period.getAbsoluteFinishDate());
            if (calendarToCmp.compareTo(calendar1) >= 0 && calendarToCmp.compareTo(calendar2) <= 0) {
                return period;
            }
        }
        return null;
    }

    public boolean containsDate(Calendar calendarTemplate, Date dateToCmp) {
        if (calendarTemplate == null || dateToCmp == null) {
            return false;
        }
        Calendar calendarToCmp = (Calendar)calendarTemplate.clone();
        calendarToCmp.setTime(dateToCmp);
        return containsDate(calendarTemplate, calendarToCmp);
    }

    public static boolean isEquals(PhysicalInventoryPeriodArray o1, PhysicalInventoryPeriodArray o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if ((o1 != null && o2 == null) || (o1 == null && o2 != null)) {
            return false;
        }
        if (o1.getPeriods() == null && o2.getPeriods() == null) {
            return true;
        }
        if (o1.getPeriods() != null && o2.getPeriods() == null) {
            return false;
        }
        if (o1.getPeriods() == null && o2.getPeriods() != null) {
            return false;
        }
        final int size = o1.getPeriods().size();
        if (size != o2.getPeriods().size()) {
            return false;
        }
        PhysicalInventoryPeriod period1 = null;
        PhysicalInventoryPeriod period2 = null;
        for (int i = 0; i < size; ++i) {
            period1 = o1.getPeriods().get(i);
            period2 = o2.getPeriods().get(i);
            if (!PhysicalInventoryPeriod.isEquals(period1, period2)) {
                return false;
            }
        }
        return true;
    }

    public static PhysicalInventoryPeriodArray parse(String text) {
        if (text == null) {
            return null;
        }
        PhysicalInventoryPeriodArray periods = new PhysicalInventoryPeriodArray();
        if (periods.loadFrom(text)) {
            return periods;
        }
        return null;
    }

    private class DateItemInfo {
        public final static int PHYSICAL_INV_UNKNOWN    = 0;
        public final static int PHYSICAL_INV_START_DATE = 1;
        public final static int PHYSICAL_INV_END_DATE   = 2;
        public final static int PHYSICAL_INV_FINAL_DATE = 3;
        public DateItemInfo(int dateType, Date dateValue) {
            _dateType = dateType;
            _dateValue = dateValue;
        }
        public int getDateType() {
            return _dateType;
        }
        public Date getDateValue() {
            return _dateValue;
        }
        private int _dateType;
        private Date _dateValue;
    }

    private class DateItemInfoComparator implements Comparator<DateItemInfo> {
        public DateItemInfoComparator() {
        }
        public int compare(DateItemInfo o1, DateItemInfo o2) {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null && o2 != null)
                return -1;
            if (o1 != null && o2 == null)
                return 1;
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(o1.getDateValue());
            calendar2.setTime(o2.getDateValue());
            int result =  calendar1.compareTo(calendar2);
            if(result != 0){
            	return result;
            }else{
            	int type1 = o1.getDateType();
          		int type2 = o2.getDateType();
              return  type1 - type2;
            }
        }
        public boolean equals(Object obj) {
            if (obj instanceof DateItemInfo) {
                return true;
            }
            return false;
        }
    }

    private ArrayList<PhysicalInventoryPeriod> _periods;
    private ArrayList<DateItemInfo> _rawPeriods;
    private boolean _isErrorsInRawPeriods;
}


