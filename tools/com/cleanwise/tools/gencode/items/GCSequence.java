/**
 * Title:        GCSequence
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

public class GCSequence {

    public GCSequence() {
        _name= "";
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name == null ? "" : name;
    }

    private String _name;
}
