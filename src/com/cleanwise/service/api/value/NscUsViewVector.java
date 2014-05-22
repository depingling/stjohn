
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        NscUsViewVector
 * Description:  Container object for NscUsView objects
 * Purpose:      Provides container storage for NscUsView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>NscUsViewVector</code>
 */
public class NscUsViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public NscUsViewVector () {}

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
      NscUsView obj1 = (NscUsView)o1;
      NscUsView obj2 = (NscUsView)o2;
      
      if("UserName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserName();
        String i2 = obj2.getUserName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CustomerNumber".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCustomerNumber();
        String i2 = obj2.getCustomerNumber();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ContactName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getContactName();
        String i2 = obj2.getContactName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogName();
        String i2 = obj2.getCatalogName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("LocationNumber".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getLocationNumber();
        String i2 = obj2.getLocationNumber();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("MemberNumber".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMemberNumber();
        String i2 = obj2.getMemberNumber();
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
      
      if("UserId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getUserId();
        int i2 = obj2.getUserId();
        retcode = i1-i2;
      }
      
      if("UserAction".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserAction();
        String i2 = obj2.getUserAction();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("StoreId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getStoreId();
        int i2 = obj2.getStoreId();
        retcode = i1-i2;
      }
      
      if("StoreAssocId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getStoreAssocId();
        int i2 = obj2.getStoreAssocId();
        retcode = i1-i2;
      }
      
      if("StoreAssocAction".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getStoreAssocAction();
        String i2 = obj2.getStoreAssocAction();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AccountId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getAccountId();
        int i2 = obj2.getAccountId();
        retcode = i1-i2;
      }
      
      if("AccountAssocId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getAccountAssocId();
        int i2 = obj2.getAccountAssocId();
        retcode = i1-i2;
      }
      
      if("AccountAssocAction".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAccountAssocAction();
        String i2 = obj2.getAccountAssocAction();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SiteId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSiteId();
        int i2 = obj2.getSiteId();
        retcode = i1-i2;
      }
      
      if("SiteAssocId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSiteAssocId();
        int i2 = obj2.getSiteAssocId();
        retcode = i1-i2;
      }
      
      if("SiteAssocAction".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSiteAssocAction();
        String i2 = obj2.getSiteAssocAction();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("EmailId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getEmailId();
        int i2 = obj2.getEmailId();
        retcode = i1-i2;
      }
      
      if("EmailAction".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getEmailAction();
        String i2 = obj2.getEmailAction();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("MemberId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getMemberId();
        int i2 = obj2.getMemberId();
        retcode = i1-i2;
      }
      
      if("CatalogId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCatalogId();
        int i2 = obj2.getCatalogId();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
