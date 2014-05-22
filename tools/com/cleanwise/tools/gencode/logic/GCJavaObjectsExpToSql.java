/**
 * Title:        GCJavaObjectsExpToSql
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic;

import java.io.StringReader;

import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCJavaObjects;
import java.util.Properties;


public interface GCJavaObjectsExpToSql {
    StringReader exportToSql(Properties settings, GCJavaObjects javaObjects) 
        throws GCException;
}
