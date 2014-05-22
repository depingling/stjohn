package com.cleanwise.service.api.util;

/**
 * This is a class for database access utilities
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

public class DBAccess {
    /**
     * Convert a java.util.Date into a java.sql.Date
     *
     * @param value
     *  java.util.Date to convert into a java.sql.Date object.
     * @return
     *  java.sql.Date corresponding to the input java.util.Date object.
     *  Returns null if input value is null.
     */
    public static java.sql.Date toSQLDate (java.util.Date value)
    {
	java.sql.Date result = null;
	if (null != value) {
	    result = new java.sql.Date(value.getTime());
	}
	return result;
    }

    /**
     * Convert a java.util.Date into a java.sql.Date
     *
     * @param value
     *  java.util.Date to convert into a java.sql.Date object.
     * @return
     *  java.sql.Date corresponding to the input java.util.Date object.
     *  Returns null if input value is null.
     */
    public static java.sql.Timestamp toSQLTimestamp (java.util.Date value)
    {
	java.sql.Timestamp result = null;
	if (null != value) {
	    result = new java.sql.Timestamp(value.getTime());
	}
	return result;
    }

    /**
     * Convert a java.util.Date into a java.sql.Time
     *
     * @param value
     *  java.util.Date to convert into a java.sql.Time object.
     * @return
     *  java.sql.Time corresponding to the input java.util.Date object.
     *  Returns null if input value is null.
     */
    public static java.sql.Time toSQLTime (java.util.Date value)
    {
	java.sql.Time result = null;
	if (null != value) {
	    result = new java.sql.Time(value.getTime());
	}
	return result;
    }

    /**
     * <code>toQuoted</code> returns the quoted representation of the
     * given string.  e.g.
     * xyz   :  'xyz'
     * it's  :  'it''s'
     *
     * @param str a <code>String</code> value to be quoted
     * @return a <code>String</code> representing the givend string quoted
     */
    public static String toQuoted (String str)
    {
	StringBuffer sb = new StringBuffer("'");
	sb.append(DBAccess.escapeQuotes(str));
	sb.append('\'');
	return sb.toString();
    }

    /**
     * <code>escapeQuotes</code> takes a string and returns the string with
     * any quotes being escaped.  Escaping quotes will change each single
     * quote character into two single quote characters
     *
     * @param str a <code>String</code> value for which quotes are escaped
     * @return a <code>String</code> value with the string escaped
     */
    public static String escapeQuotes (String str)
    {
	if (str == null) {
	    return "";
	}

	int i = str.indexOf('\'');
	if (i == -1) {
	    return str;
	}

	char c;
	StringBuffer sb = new StringBuffer(str.substring(0,i));
	while (i < str.length()) {
	    c = str.charAt(i);
	    if (c == '\'') {
		sb.append('\'');
	    }
	    sb.append(c);
	    i++;
	}
	return sb.toString();
    }
    
    /**
     *Returns a date object formated as something that can be compared with a sql
     *date value down to the second.  Uses data bases default start date for 
     *date portion, only compares the time
     */
    public static String getSQLDateTimeExpr(java.util.Date theDate){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(theDate);
        StringBuffer buf = new StringBuffer();
        buf.append("TO_DATE('");
        buf.append("1900-1-1");
        buf.append(" ");
        buf.append(cal.get(java.util.Calendar.HOUR_OF_DAY));
        buf.append(":");
        buf.append(cal.get(java.util.Calendar.MINUTE));
        buf.append(":");
        buf.append(cal.get(java.util.Calendar.SECOND));
        buf.append("','YYYY-MM-DD HH24:MI:SS')");
        return buf.toString();
    }
}
