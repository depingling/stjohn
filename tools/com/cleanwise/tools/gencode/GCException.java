/**
 * Title:        GCException
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode;


public class GCException extends Exception {
    private static final long serialVersionUID = -539769100375483720L;
    
    public GCException(String message, String module, int code) {
        super(message);
        _module = module;
        _code = code;
    }

    public GCException(String message, String module) {
        this(message, module, 0);
    }

    public GCException(String message, int code) {
        this(message, null, code);
    }

    public GCException(String message) {
        this(message, null, 0);
    }

    public String GetModule() {
        return _module;
    }

    public int GetCodeError() {
        return _code;
    }

    private int _code;
    private String _module;
}
