
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.*;

/**
 * Title:        BuildingServicesContractorViewVector
 * Description:  Container object for BuildingServicesContractorView objects
 * Purpose:      Provides container storage for BuildingServicesContractorView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>BuildingServicesContractorViewVector</code>
 */
public class BuildingServicesContractorViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -615506605197435466L;
    /**
     * Constructor.
     */
    public BuildingServicesContractorViewVector () {}

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
      BuildingServicesContractorView obj1 = (BuildingServicesContractorView)o1;
      BuildingServicesContractorView obj2 = (BuildingServicesContractorView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
