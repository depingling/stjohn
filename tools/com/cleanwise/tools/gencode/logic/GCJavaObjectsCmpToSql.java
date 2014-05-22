/**
 * Title:        GCJavaObjectsCmpToSql
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic;

import java.io.StringReader;
import java.util.Properties;

import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCJavaObjects;


public interface GCJavaObjectsCmpToSql {
    StringReader compareAndBuildSql(Properties settings, 
        GCJavaObjects javaObjects1, GCJavaObjects javaObjects2,GCJavaObjects javaLogObjects) 
        throws GCException;
}
