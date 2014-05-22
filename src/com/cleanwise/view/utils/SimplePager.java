package com.cleanwise.view.utils;

import java.util.*;

/**
 */
public class SimplePager implements Pager {
    static final int ITEMS_ON_PAGE = 16;

    List mountedList;
    List<List<Object>> pages;
    Options options;

    public SimplePager(List mountedList) {
        this.mountedList = mountedList;
        this.options = new Options();
        this.pages = createPages(mountedList, this.options);
    }

    public SimplePager() {
       this.mountedList = new ArrayList();
       this.options = new Options();
       this.pages = new ArrayList<List<Object>>();
    }

    public SimplePager(List mountedList, int maxItems, int maxPages) {
        this.mountedList = mountedList;
        this.options = new Options(maxItems, maxPages);
        this.pages = createPages(mountedList, this.options);
    }

    private ArrayList<List<Object>> createPages(List mountedList, Options options) {

        HashMap<Integer, List<Object>> pages = new HashMap<Integer, List<Object>>();

        for (int i = 1, j = 0; i <= mountedList.size(); j = i / options.getMaxItems(), i++) {

            List<Object> pgl = pages.get(new Integer(j));
            if (pgl == null) {
                pgl = new ArrayList<Object>();
            }

            pgl.add(mountedList.get(i - 1));
            pages.put(j, pgl);
        }

        ArrayList<List<Object>> result = new ArrayList<List<Object>>();
        for (int i = 0; i < pages.size(); i++) {
            result.add(pages.get(new Integer(i)));
        }

        return result;
    }

    public List<Object> get(int i) {
        if (pages.size() > 0) {
            return pages.get(i);
        } else {
            return new ArrayList<Object>();
        }
    }

    public List getPageItemList(int i) {
        if (i == -1) {
            return getAll();
        } else {
            return get(i);
        }
    }


    public void setPageItemList(int i, List<Object> itemList) {
        if (itemList != null) {
            if (i > -1 && itemList.size() == ITEMS_ON_PAGE) {
                pages.set(i, itemList);
                Object[] mArray = mountedList.toArray();
                System.arraycopy(itemList.toArray(), 0, mArray, i * ITEMS_ON_PAGE, itemList.size());
                mountedList = Arrays.asList(mArray);
            } else {
                this.mountedList = itemList;
                this.pages = createPages(this.mountedList, this.options);
            }
        }
    }


    public List<Object> getAll() {
        ArrayList<Object> result = new ArrayList<Object>();
        if (pages.size() > 0) {
            for (List<Object> page : pages) {
                result.addAll(page);
            }
        }
        return result;
    }

    public int getMaxPages() {
        return this.options.getMaxPages();
    }

    public int getMaxItems() {
        return getOptions().getMaxItems();
    }

    public List getMountedList() {
        return mountedList;
    }

    public void setMountedList(List mountedList) {
        this.mountedList = mountedList;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public class Options {

        private int maxPages;
        private int maxItems;

        public Options() {
            this.maxPages = 100;
            this.maxItems = ITEMS_ON_PAGE;
        }

        public Options(int maxItems, int maxPages) {
            this.maxPages = maxPages;
            this.maxItems = maxItems;
        }

        public int getMaxPages() {
            return maxPages;
        }

        public void setMaxPages(int maxPages) {
            this.maxPages = maxPages;
        }

        public int getMaxItems() {
            return maxItems;
        }

        public void setMaxItems(int maxItems) {
            this.maxItems = maxItems;
        }
    }
}
