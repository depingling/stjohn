package com.cleanwise.service.api.dao;

/**
 * Helper class <code>DbUtility</code> is a holder for
 * db data manipulation methods.
 *
 * @author <a href="mailto:dvieira@cleanwise.com"></a>
 */
public class DbUtility {

    // Strings with embedded single quotes must be escaped
    // for use in SQL statements.
    public static String fixQuotes(String s) {

	if ( null == s ) return s;

	// In the event the string has already been
	// escaped.
	s = unescapeQuotes(s);
	if ( null == s ) return s;

	return s.replaceAll("'", "''");
    }

    public static String unescapeQuotes(String s) {

	if ( null == s ) return s;

	String retvalue = s.replaceAll("''", "'");
	return retvalue;
    }

}

