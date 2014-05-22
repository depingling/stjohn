
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ContactViewVector
 * Description:  Container object for ContactView objects
 * Purpose:      Provides container storage for ContactView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ContactViewVector</code>
 */
public class ContactViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 228772632343765074L;
    /**
     * Constructor.
     */
    public ContactViewVector () {}

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
      ContactView obj1 = (ContactView)o1;
      ContactView obj2 = (ContactView)o2;
      
      if("BusEntityId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBusEntityId();
        int i2 = obj2.getBusEntityId();
        retcode = i1-i2;
      }
      
      if("AddressId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getAddressId();
        int i2 = obj2.getAddressId();
        retcode = i1-i2;
      }
      
      if("ContactId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getContactId();
        int i2 = obj2.getContactId();
        retcode = i1-i2;
      }
      
      if("ContactTypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getContactTypeCd();
        String i2 = obj2.getContactTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("FirstName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getFirstName();
        String i2 = obj2.getFirstName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("LastName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getLastName();
        String i2 = obj2.getLastName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
