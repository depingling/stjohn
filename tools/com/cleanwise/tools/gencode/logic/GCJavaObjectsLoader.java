/**
 * Title:        GCJavaObjectsLoader 
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic;

import com.cleanwise.tools.gencode.GCException;
import java.util.Properties;


public interface GCJavaObjectsLoader extends GCJavaObjectsProvider {
    boolean loadData(Properties settings) throws GCException;
}
