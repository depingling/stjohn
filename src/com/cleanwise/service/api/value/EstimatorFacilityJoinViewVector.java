
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        EstimatorFacilityJoinViewVector
 * Description:  Container object for EstimatorFacilityJoinView objects
 * Purpose:      Provides container storage for EstimatorFacilityJoinView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EstimatorFacilityJoinViewVector</code>
 */
public class EstimatorFacilityJoinViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2178817006743660903L;
    /**
     * Constructor.
     */
    public EstimatorFacilityJoinViewVector () {}

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
      EstimatorFacilityJoinView obj1 = (EstimatorFacilityJoinView)o1;
      EstimatorFacilityJoinView obj2 = (EstimatorFacilityJoinView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
