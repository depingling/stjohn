package com.cleanwise.service.api.value;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Title:        IdVector
 * Description:  Container object for Ids
 * Purpose:      Provides container storage for Id Integers.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser
 */

/**
 * <code>IdVector</code>
 */
public class IdVector extends java.util.ArrayList implements Comparator {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5697641017434132132L;
    /**
     * Constructor.
     */
    public IdVector() {}
    
    public static String toCommaString(List pV) {
        StringBuffer ids = new StringBuffer();
        
 		boolean needQuote = false;
		if(pV==null || pV.size()==0) {
		  return "";
		}
		Object obj = pV.get(0);
		if(obj instanceof String) {
		  needQuote = true;
		  ids.append("'");
		}
		ids.append(obj.toString());
		
    for ( int i = 1; i < pV.size(); i++ ) {
      if (needQuote) {
        ids.append("','");
      } else {
        ids.append(",");
      }
			obj = pV.get(i);
      ids.append(obj.toString());
    }
    if(needQuote) {
		  ids.append("'");
    }        
    return ids.toString();        
  }

    /**
     * Sort, assumes Integers as objects in the list
     */
    public void sort() {
        Collections.sort(this,this);
    }
    
    /*
     *compare method
     */
    public int compare(Object o1, Object o2) {
        Integer obj1 = (Integer)o1;
        Integer obj2 = (Integer)o2;
        return obj1.intValue() - obj2.intValue();
    }
}
