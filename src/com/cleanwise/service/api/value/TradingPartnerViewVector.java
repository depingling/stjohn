package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;

/**
 * Title:        TradingPartnerViewVector
 * Description:  Container object for TradingPartnerView objects
 * Purpose:      Provides container storage for SiteView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */

public class TradingPartnerViewVector
extends java.util.ArrayList
implements Comparator
{
    private String _sortField = "id";
    /**
     *
     */
    public TradingPartnerViewVector() {}
    /*
    *
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
      if("id".equals(_sortField)) {
        TradingPartnerView obj1 = (TradingPartnerView)o1;
        TradingPartnerView obj2 = (TradingPartnerView)o2;
    		int name1 = obj1.getId();
        int name2 = obj2.getId();
     		return name1-name2;
	    }
      if("busEntityShortDesc".equals(_sortField)) {
        TradingPartnerView obj1 = (TradingPartnerView)o1;
        TradingPartnerView obj2 = (TradingPartnerView)o2;
    		String name1 = obj1.getBusEntityShortDesc();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getBusEntityShortDesc();
        if( null == name2 ) name2 = "";
     		return name1.compareTo(name2);
	    }
      if("shortDesc".equals(_sortField)) {
        TradingPartnerView obj1 = (TradingPartnerView)o1;
        TradingPartnerView obj2 = (TradingPartnerView)o2;
    		String name1 = obj1.getShortDesc();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getShortDesc();
        if( null == name2 ) name2 = "";
     		return name1.compareTo(name2);
	    }
      if("type".equals(_sortField)) {
        TradingPartnerView obj1 = (TradingPartnerView)o1;
        TradingPartnerView obj2 = (TradingPartnerView)o2;
    		String name1 = obj1.getType();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getType();
        if( null == name2 ) name2 = "";
     		return name1.compareTo(name2);
	    }
      if("status".equals(_sortField)) {
        TradingPartnerView obj1 = (TradingPartnerView)o1;
        TradingPartnerView obj2 = (TradingPartnerView)o2;
    		String name1 = obj1.getStatus();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getStatus();
        if( null == name2 ) name2 = "";
     		return name1.compareTo(name2);
	    }
      if("traidingTypeCD".equals(_sortField)) {
          TradingPartnerView obj1 = (TradingPartnerView)o1;
          TradingPartnerView obj2 = (TradingPartnerView)o2;
      		String name1 = obj1.gettraidingTypeCD();
          if( null == name1 ) name1 = "";
          String name2 = obj2.gettraidingTypeCD();
          if( null == name2 ) name2 = "";
       		return name1.compareTo(name2);
  	    }      
      return 0;
    }

}
