
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        BillToVector
 */

/**
 * <code>BillToVector</code>
 */
public class BillToVector extends java.util.ArrayList implements Comparator
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -6633081957096617912L;
    /**
     * Constructor.
     */
    public BillToVector () {}

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
      BillToData obj1 = (BillToData)o1;
      BillToData obj2 = (BillToData)o2;
            
      if("Name".equalsIgnoreCase(_sortField)) {

        String i1 = obj1.getBillToName();
        String i2 = obj2.getBillToName();

        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
            
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
