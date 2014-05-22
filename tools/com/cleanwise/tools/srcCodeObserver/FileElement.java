/**
 * Title:        SourceCodeObserver
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.srcCodeObserver;

import java.util.Date;

public class FileElement {

    private String _name;
    private long _size;
    private Date _modificationDate;

    public FileElement(String name, int size, Date modificationDate) {
        _name = name;
        _size = size;
        _modificationDate = modificationDate;
    }

    public FileElement(String name) {
        this(name, -1, null);
    }

    public FileElement() {
        this(null, -1, null);
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setSize(long size) {
        _size = size;
    }

    public long getSize() {
        return _size;
    }

    public void setModificationDate(Date modificationDate) {
        _modificationDate = modificationDate;
    }

    public Date getModificationDate() {
        return _modificationDate;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("[FileElement: ");
        buff.append("Name=").append(_name).append(",");
        buff.append("ModificationDate=").append(_modificationDate).append(",");
        buff.append("Size=").append(_size);
        buff.append("]");
        return buff.toString();
    }

}


