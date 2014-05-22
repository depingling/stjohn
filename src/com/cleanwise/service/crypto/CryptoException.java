package com.cleanwise.service.crypto;
/**
 * Extends java.lang.Exception to provide some exception reporting 
 * for the Cryptographic package. Grew out of the need to eliminate
 * the dependency on cwLoaderLogger when wanting to create cwSigner
 * components - the class needed to return an exception rather than
 * log an error.
 * 
 * @author Ken Mawhinney
 * @since Mar 3, 2001
 */
public class CryptoException extends Exception {

    /** Default exception object */
    public CryptoException () {
	super();
    }

    public CryptoException (String msg) {
	super(msg);
    }
}
