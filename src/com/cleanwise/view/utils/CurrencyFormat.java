package com.cleanwise.view.utils;

/**
 * Title:        CurrencyFormat
 * Description:  This is a utility class to format and parse currency values
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;


/**
 * <code>CurrencyFormat</code> formats and parses currency values.
 *
 * @author <a href="mailto:tbesser@TBESSER"></a>
 */
public class CurrencyFormat {

    /**
     * <code>parse</code> a string representing a currency value and
     * generate the corresponding BigDecimal value.  If default locale
     * is the 'US', examples of valid strings are: "$123.45", "123.45",
     * "$1,234.45".  This method works by firsting attempting to parse
     * the string as a currency, and failing that as a number.
     * @see java.math.BigDecimal
     *
     * @param value a <code>String</code> value
     * @return a <code>BigDecimal</code> value
     * @exception ParseException if an error occurs
     */
    public static BigDecimal parse(String value) throws ParseException {
	BigDecimal decValue;
	try {
	    if (value == null || value.equals("")) {
		decValue = new BigDecimal(0);
	    } else {
		// first try to parse as a plain number (no currency symbol,
		// grouping symbols, etc.)
		NumberFormat nf  = NumberFormat.getInstance();
		Number n = nf.parse(value);
		decValue = new BigDecimal(n.doubleValue());
	    }
	} catch (ParseException pe) {
	    // OK, wasn't just a plain number - try as a currency
	    // Hardcode the US locale until we decide what to do
	    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
	    Number n = nf.parse(value);
	    // changing to a double - potential roundoff issues
	    decValue = new BigDecimal(n.doubleValue());
	}
	return decValue;
    }

    /**
     * <code>format</code> returns a string representing the currency
     * value.  For example, something like: "$1,234,567.89"
     *
     * @param value a <code>BigDecimal</code> value
     * @return a <code>String</code> value
     */
    public static String format(BigDecimal value) {
	// Hardcode the US locale until we decide what to do
	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
    if(value==null) value = new BigDecimal(0);
	return nf.format(value);
    }

    /**
     * <code>formatAsNumber</code> returns a string representing the
     * decimal number value.  For example, something like: "1234567.89"
     *
     * @param value a <code>BigDecimal</code> value
     * @return a <code>String</code> value
     */
    public static String formatAsNumber(BigDecimal value) {
	NumberFormat nf = NumberFormat.getInstance();
	return nf.format(value);
    }

    public static BigDecimal exactParse(String value, Locale locale, BigDecimal maxValue) throws ParseException {
        if (value == null) {
            new BigDecimal(0);
        }

        String valueTemp = value.trim();
        if (valueTemp.equals("")) {
            new BigDecimal(0);
        }

        NumberFormat numberFormat = null;
        if (locale == null) {
            numberFormat = NumberFormat.getInstance();
        } else {
            numberFormat = NumberFormat.getInstance(locale);
        }

        ParsePosition parsePosition = new ParsePosition(0);
        Number number = numberFormat.parse(valueTemp, parsePosition);
        if (parsePosition.getErrorIndex() >= 0) {
            throw new ParseException("Wrong number format", parsePosition.getIndex());
        }
        if (parsePosition.getIndex() < valueTemp.length()) {
            throw new ParseException("Wrong number format", parsePosition.getIndex());
        }

        BigDecimal decValue = null;
        try {
            decValue = new BigDecimal(number.doubleValue());
        } catch (NumberFormatException ex) {
            throw new ParseException("Number value is infinite or NaN", 0);
        }

        if (decValue != null && maxValue != null) {
            if (decValue.compareTo(maxValue) > 0) {
                throw new ParseException("Number value is very big", 0);
            }
        }

        return decValue;
    }

    public static BigDecimal exactParse(String value, Locale locale) throws ParseException {
        return exactParse(value, locale, null);
    }

    public static BigDecimal exactParse(String value, BigDecimal maxValue) throws ParseException {
        BigDecimal decValue = null;
        try {
            decValue = exactParse(value, null, maxValue);
        } catch (ParseException ex) {
            decValue = exactParse(value, Locale.US, maxValue);
        }
        return decValue;
    }

    public static BigDecimal exactParse(String value) throws ParseException {
        BigDecimal decValue = null;
        try {
            decValue = exactParse(value, null, null);
        } catch (ParseException ex) {
            decValue = exactParse(value, Locale.US, null);
        }
        return decValue;
    }

}


