package com.cleanwise.service.api.util;

import java.io.Serializable;
/**
 * The <code>CustInvoiceKey</code> class encapsulates the
 * data needed as a key into the customer invoice table,
 * consisting of an invoice number (a non-negative int)
 * and an invoice type (a String whose possible values
 * are "IN" and "CR").
 *
 * @author Eugenia Harris
 */

  public class CustInvoiceKey implements Serializable
  {
    private int _number = -1;
    private int _origInvNumber = -1;
    private String _type = null;
    final static String INVOICE = RefCodeNames.INVOICE_TYPE_CD.IN;
    final static String CREDIT = RefCodeNames.INVOICE_TYPE_CD.CR;

    /**
     * Inaccessible null constructor.
     */
    private CustInvoiceKey()
    {
    }

    public void setOriginalInvoiceNum(String origInvNum)
    throws ArithmeticException
    {
        if(origInvNum != null){
            origInvNum = origInvNum.trim();
        }
        _origInvNumber = Integer.parseInt(origInvNum);
    }
    
    public int getOriginalInvoiceNum(){
        return _origInvNumber;
    }
    
    /**
     * Constructor that builds a new entry from the input arguments 
     * @param pNumber the invoice number (positive int)
     * @param pType the invoice type (String)
     * @exception IllegalArgumentException
     */
    public CustInvoiceKey (int pNumber, String pType)
    throws IllegalArgumentException 
    {
        initialize(pNumber, pType);
    }

    /**
     * Constructor that builds a new entry from the input arguments.
     * In this version of the constructor, the invoice number argument
     * is a String, which must be convertible to a positive integer.
     * @param pNumber the invoice number (String)
     * @param pType the invoice type (String)
     * @exception IllegalArgumentException
     */
    public CustInvoiceKey (String pNumber, String pType)
    throws IllegalArgumentException, NumberFormatException
    {
        String errorMess = null;
        int number = -1;
        if (Utility.isSet(pNumber)) {
            try {
                number = Integer.parseInt(pNumber);
            } catch (NumberFormatException e) {
                errorMess = "Non-numeric invoice number: " + _number;
            }
        } else {
            errorMess = "Null or empty invoice number";
        }

        if (errorMess == null) {
            initialize(number, pType);
        } else {
            throw new IllegalArgumentException(errorMess);
        }
    }

    /**
     * Return the number.
     */
    public int getNumber() {
        return _number;
    }

    /**
     * Return the type.
     */
    public String getType() {
        return _type;
    }

    /**
     * Return true if this key represents a credit invoice
     */
    public boolean isCredit() {
        return _type.equals(CREDIT);
    }

    /**
     * Returns true if this key equals the key in the argument
     */
    public boolean equals(CustInvoiceKey pKey) {
        if (pKey != null) {
            if (_number == pKey.getNumber() && _type.equals(pKey.getType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a one-line string representation of the number/type.
     */
    public String toString()
    {
	    StringBuffer sb = new StringBuffer("CustInvoiceKey: ");
	    sb.append("number=" + _number);
	    sb.append(", ");
	    sb.append("type=" + _type);
	    return sb.toString();
    }

//**************************************************************************//

    private void initialize(int pNumber, String pType)
    throws IllegalArgumentException
    {
        String errorMess = null;
        _number = pNumber;
        if (_number < 0) {
            errorMess = "Illegal negative invoice number: " + _number;
        }
        _type = new String(pType.trim());
        if (!_type.equals(INVOICE) && !_type.equals(CREDIT)) {
            errorMess = "Illegal invoice type: " + _type;
        }
        if (errorMess != null) {
            throw new IllegalArgumentException(errorMess);
        }
    }
  } 
