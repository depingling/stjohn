package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
/**
 * Title:        CatalogCategoryDataVector
 * Description:  Container object for CatalogCategoryData objects
 * Purpose:      Provides container storage for CatalogCategoryData objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

public class CatalogCategoryDataVector extends java.util.ArrayList implements  Comparator
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -7714124446349538486L;

  /**
   *
   */
   public CatalogCategoryDataVector() {

   }
    String _sortField = "";
    /**
     * Sort
     */
    public void sort(String pFieldName) {
       _sortField = pFieldName;
       Collections.sort(this,this);
    }

    /*
    *
    */
    public int compare(Object o1, Object o2)
    {
      int retcode = -1;
      CatalogCategoryData obj1 = (CatalogCategoryData)o1;
      CatalogCategoryData obj2 = (CatalogCategoryData)o2;

      if("CatalogCategoryShortDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogCategoryShortDesc();
        String i2 = obj2.getCatalogCategoryShortDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      return retcode;
    }

}
