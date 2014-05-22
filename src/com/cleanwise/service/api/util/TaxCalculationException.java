/*
 * TaxCalculationException.java
 *
 * Created on September 2, 2005, 10:17 PM
 *
 * Thrown to indicate a problem occured during tax calculation
 */

package com.cleanwise.service.api.util;
import java.rmi.RemoteException;
/**
 * Thrown to indicate a problem occured during tax calculation
 * @author bstevens
 */
public class TaxCalculationException extends RemoteException{
    
    /** Thrown to indicate a problem occured during tax calculation */
    public TaxCalculationException(String msg) {
	super(msg);
    }
    
}
