
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AccountViewVector
 * Description:  Container object for AccountView objects
 * Purpose:      Provides container storage for AccountView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AccountViewVector</code>
 */
public class AccountViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 8107546316445778014L;
    /**
     * Constructor.
     */
    public AccountViewVector () {}

    String _sortField = "";
    boolean _ascFl = true;
    /**
     * Sort
     */
    public void sort(String pFieldName) {
       sort(pFieldName,true);     
    }

    public void sort(String pFieldName, boolean pAscFl) {
       _sortField = pFieldName;
       _ascFl = pAscFl;       
       Collections.sort(this,this);
    }

    /*
    *
    */
    public int compare(Object o1, Object o2)
    {
      int retcode = -1;
      AccountView obj1 = (AccountView)o1;
      AccountView obj2 = (AccountView)o2;
      
      if("StoreId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getStoreId();
        int i2 = obj2.getStoreId();
        retcode = i1-i2;
      }
      
      if("AcctId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getAcctId();
        int i2 = obj2.getAcctId();
        retcode = i1-i2;
      }
      
      if("AcctName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAcctName();
        String i2 = obj2.getAcctName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AcctStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAcctStatusCd();
        String i2 = obj2.getAcctStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
