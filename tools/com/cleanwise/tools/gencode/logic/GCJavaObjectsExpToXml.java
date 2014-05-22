/**
 * Title:        GCJavaObjectsExpToXml
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic;

import java.util.Properties;
import org.w3c.dom.Document;

import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCJavaObjects;


public interface GCJavaObjectsExpToXml {
    public Document exportToXml(Properties settings, GCJavaObjects javaObjects) 
        throws GCException;
}
