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
import java.util.HashMap;

public class DirElement {

    private String _name;
    private Date _modificationDate;
    HashMap<String, FileElement> _files;
    HashMap<String, DirElement> _directories;

    public DirElement(String name, Date modificationDate) {
        _name = name;
        _modificationDate = modificationDate;
        _files = new HashMap<String, FileElement>();
        _directories = new HashMap<String, DirElement>();
    }

    public DirElement(String name) {
        this(name, null);
    }

    public DirElement() {
        this(null, null);
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setModificationDate(Date modificationDate) {
        _modificationDate = modificationDate;
    }

    public Date getModificationDate() {
        return _modificationDate;
    }

    public HashMap<String, FileElement> getFiles() {
        return _files;
    }

    public HashMap<String, DirElement> getDirectories() {
        return _directories;
    }

}
