package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

/**
 * Title:        OrderScheduleViewVector
 * Description:  Container object for OrderScheduleView objects
 * Purpose:      Provides container storage for SiteView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */

public class OrderScheduleViewVector
extends java.util.ArrayList
implements Comparator
{
    private String _sortField = "id";
    /**
     *
     */
    public OrderScheduleViewVector() {}
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
      if("orderScheduleId".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
    		int name1 = obj1.getOrderScheduleId();
        int name2 = obj2.getOrderScheduleId();
     		return name1-name2;
	    }
      if("orderGuideId".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
    		int name1 = obj1.getOrderGuideId();
        int name2 = obj2.getOrderGuideId();
     		return name1-name2;
	    }
      if("siteId".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
    		int name1 = obj1.getSiteId();
        int name2 = obj2.getSiteId();
     		return name1-name2;
	    }
      if("accountId".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
    		int name1 = obj1.getAccountId();
        int name2 = obj2.getAccountId();
     		return name1-name2;
	    }

      if("orderGuideName".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
    		String name1 = obj1.getOrderGuideName();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getOrderGuideName();
        if( null == name2 ) name2 = "";
        int compRes = name1.compareTo(name2);
        if(compRes!=0) {
       		return compRes;
        }
        Date date1 = obj1.getEffDate();
        if(date1==null)  date1 = new Date(0);
        Date date2 = obj2.getEffDate();
        if(date2==null)  date2 = new Date(0);
        if(date1.before(date2)) {
          compRes=-1;
        } else if(date1.after(date2)) {
          compRes=1;
        }
        return compRes;
	    }

      if("effDate".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
        int compRes = 0;
        Date date1 = obj1.getEffDate();
        if(date1==null)  date1 = new Date(0);
        Date date2 = obj2.getEffDate();
        if(date2==null)  date2 = new Date(0);
        if(date1.before(date2)) {
          compRes=-1;
        } else if(date1.after(date2)) {
          compRes=1;
        }
        return compRes;
	    }

      if("expDate".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
        int compRes = 0;
        Date date1 = obj1.getExpDate();
        if(date1==null)  date1 = new Date(1000L*365*24*3600*1000);
        Date date2 = obj2.getExpDate();
        if(date2==null)  date2 = new Date(1000L*365*24*3600*1000);
        if(date1.before(date2)) {
          compRes=-1;
        } else if(date1.after(date2)) {
          compRes=1;
        }
        return compRes;
	    }

      if("siteName".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
        String name1 = obj1.getSiteName();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getSiteName();
        if( null == name2 ) name2 = "";
        return name1.compareTo(name2);
	    }

      if("accountName".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
    		String name1 = obj1.getAccountName();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getAccountName();
        if( null == name2 ) name2 = "";
        int compRes = name1.compareTo(name2);
        if(compRes!=0) {
       		return compRes;
        }
        name1 = obj1.getSiteName();
        if( null == name1 ) name1 = "";
        name2 = obj2.getSiteName();
        if( null == name2 ) name2 = "";
        return name1.compareTo(name2);
	    }

      if("orderScheduleCd".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
        String name1 = obj1.getOrderScheduleCd();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getOrderScheduleCd();
        if( null == name2 ) name2 = "";
        return name1.compareTo(name2);
	    }

      if("orderScheduleRuleCd".equals(_sortField)) {
        OrderScheduleView obj1 = (OrderScheduleView)o1;
        OrderScheduleView obj2 = (OrderScheduleView)o2;
        String name1 = obj1.getOrderScheduleRuleCd();
        if( null == name1 ) name1 = "";
        String name2 = obj2.getOrderScheduleRuleCd();
        if( null == name2 ) name2 = "";
        return name1.compareTo(name2);
	    }
      return 0;
    }

}
