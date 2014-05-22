package com.cleanwise.view.utils;

import java.util.List;

/**
 */
public interface Pager {

    public List getPageItemList(int i);
    public int getMaxPages();
    public int getMaxItems();
    public List getMountedList();
    public void setPageItemList(int i,List<Object> list);
}
