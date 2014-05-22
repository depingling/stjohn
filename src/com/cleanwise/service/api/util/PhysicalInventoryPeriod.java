package com.cleanwise.service.api.util;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright:   Copyright (c) 2009
 * Company:     CleanWise, Inc.
 * @author      
 *
 */

public class PhysicalInventoryPeriod implements Serializable{

    public static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";

    public PhysicalInventoryPeriod(Date startDate, Date endDate, Date absoluteFinishDate) {
        _startDate = startDate;
        _endDate = endDate;
        _absoluteFinishDate = absoluteFinishDate;
    }

    public PhysicalInventoryPeriod() {
        this(null, null, null);
    }

    public Date getStartDate() {
        return _startDate;
    }

    public void setStartDate(Date startDate) {
        _startDate = startDate;
    }

    public Date getEndDate() {
        return _endDate;
    }

    public void setEndDate(Date endDate) {
        _endDate = endDate;
    }

    public Date getAbsoluteFinishDate() {
        return _absoluteFinishDate;
    }

    public void setAbsoluteFinishDate(Date absoluteFinishDate) {
        _absoluteFinishDate = absoluteFinishDate;
    }

    public String getStartDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dateFormat.format(_startDate);
    }

    public String getEndDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dateFormat.format(_endDate);
    }

    public String getAbsoluteFinishDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return dateFormat.format(_absoluteFinishDate);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        if (_startDate == null) {
            buffer.append(getDateStringForNull());
        } else {
            buffer.append(dateFormat.format(_startDate));
        }
        buffer.append(",");
        if (_endDate == null) {
            buffer.append(getDateStringForNull());
        } else {
            buffer.append(dateFormat.format(_endDate));
        }
        buffer.append(",");
        if (_absoluteFinishDate == null) {
            buffer.append(getDateStringForNull());
        } else {
            buffer.append(dateFormat.format(_absoluteFinishDate));
        }
        return buffer.toString();
    }

    public static boolean checkData(PhysicalInventoryPeriod dates) {
        if (dates == null) {
            return false;
        }
        if (dates.getStartDate() == null || 
            dates.getEndDate() == null || 
            dates.getAbsoluteFinishDate() == null) {
            return false;
        }
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        calendar1.setTime(dates.getStartDate());
        calendar2.setTime(dates.getEndDate());
        calendar3.setTime(dates.getAbsoluteFinishDate());
        if (calendar1.compareTo(calendar2) > 0) {
            return false;
        }
        if (calendar1.compareTo(calendar3) > 0) {
            return false;
        }
        if (calendar2.compareTo(calendar3) > 0) {
            return false;
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

    public boolean loadFrom(String text, ParsePosition position) {
        if (text == null) {
            return false;
        }
        if (position == null) {
            return false;
        }
        if (position.getIndex() >= text.length()) {
            return false;
        }
        boolean isParsedSuccessfully = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        Date startDate = null;
        Date endDate = null;
        Date absoluteFinishDate = null;
        do
        {
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                isParsedSuccessfully = false;
                break;
            }
            if (text.charAt(position.getIndex()) == ',') {
                position.setIndex(position.getIndex() + 1);
            }
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                isParsedSuccessfully = false;
                break;
            }
            try {
                startDate = dateFormat.parse(text, position);
            } catch (Exception ex) {
                isParsedSuccessfully = false;
                break;
            }
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                isParsedSuccessfully = false;
                break;
            }
            if (text.charAt(position.getIndex()) == ',') {
                position.setIndex(position.getIndex() + 1);
            }
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                isParsedSuccessfully = false;
                break;
            }
            try {
                endDate = dateFormat.parse(text, position);
            } catch (Exception ex) {
                isParsedSuccessfully = false;
                break;
            }
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                isParsedSuccessfully = false;
                break;
            }
            if (text.charAt(position.getIndex()) == ',') {
                position.setIndex(position.getIndex() + 1);
            }
            parseBlankSpaces(text, position);
            if (position.getIndex() >= text.length()) {
                isParsedSuccessfully = false;
                break;
            }
            try {
                absoluteFinishDate = dateFormat.parse(text, position);
            } catch (Exception ex) {
                isParsedSuccessfully = false;
                break;
            }
            if (startDate == null || endDate == null || absoluteFinishDate == null) {
                isParsedSuccessfully = false;
                break;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            if (calendar.get(Calendar.YEAR) < 1000) {
                isParsedSuccessfully = false;
                break;
            }
            calendar.setTime(endDate);
            if (calendar.get(Calendar.YEAR) < 1000) {
                isParsedSuccessfully = false;
                break;
            }
            calendar.setTime(absoluteFinishDate);
            if (calendar.get(Calendar.YEAR) < 1000) {
                isParsedSuccessfully = false;
                break;
            }
            this.setStartDate(startDate);
            this.setEndDate(endDate);
            this.setAbsoluteFinishDate(absoluteFinishDate);
        }
        while (false);
        return isParsedSuccessfully;
    }

    public boolean loadFrom(String text) {
        return loadFrom(text, new ParsePosition(0));
    }

    public static boolean isEquals(PhysicalInventoryPeriod o1, PhysicalInventoryPeriod o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if ((o1 != null && o2 == null) || (o1 == null && o2 != null)) {
            return false;
        }
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(o1.getStartDate());
        calendar2.setTime(o2.getStartDate());
        if (calendar1.compareTo(calendar2) != 0) {
            return false;
        }
        calendar1.setTime(o1.getEndDate());
        calendar2.setTime(o2.getEndDate());
        if (calendar1.compareTo(calendar2) != 0) {
            return false;
        }
        calendar1.setTime(o1.getAbsoluteFinishDate());
        calendar2.setTime(o2.getAbsoluteFinishDate());
        if (calendar1.compareTo(calendar2) != 0) {
            return false;
        }
        return true;
    }

    public static PhysicalInventoryPeriod parse(String text, ParsePosition position) {
        if (text == null) {
            return null;
        }
        if (position == null) {
            return null;
        }
        PhysicalInventoryPeriod period = new PhysicalInventoryPeriod();
        if (period.loadFrom(text, position)) {
            return period;
        }
        return null;
    }

    public static PhysicalInventoryPeriod parse(String text) {
        return parse(text, new ParsePosition(0));
    }

    public static Date parseDate(String text) {
        if (text == null) {
            return null;
        }
        if (text.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        Date date = null;
        try {
            date = dateFormat.parse(text);
        } 
        catch (Exception ex) {
        }
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.YEAR) < 1000) {
            date = null;
        }
        return date;
    }

    private String getDateStringForNull() {
        return "00/00/0000";
    }

    private Date _startDate;
    private Date _endDate;
    private Date _absoluteFinishDate;
}


