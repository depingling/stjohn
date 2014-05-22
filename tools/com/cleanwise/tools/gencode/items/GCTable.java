/**
 * Title:        GCTable
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.ArrayList;

import com.cleanwise.tools.gencode.GCCodeNames.Xml.Attribute;


public class GCTable {

    public GCTable(String dataUID, String vectorUID, boolean temporary) {
        _serialVersionDataUID = dataUID;
        _serialVersionVectorUID = vectorUID;
        _temporary = temporary;
        _name = null;
        _fields = null;
        _primaryKey = null;
    }
    
    public GCTable(String dataUID, String vectorUID) {
        _serialVersionDataUID = dataUID;
        _serialVersionVectorUID = vectorUID;
        _name = null;
        _fields = null;
        _primaryKey = null;
        _log = null;
    }

    public GCTable() {
        this("0", "0");
    }

    public String getSerialVersionDataUID() {
        return _serialVersionDataUID;
    }

    public void setSerialVersionDataUID(String serialVersionDataUID) {
        _serialVersionDataUID = serialVersionDataUID;
    }

    public String getSerialVersionVectorUID() {
        return _serialVersionVectorUID;
    }

    public void setSerialVersionVectorUID(String serialVersionVectorUID) {
        _serialVersionVectorUID = serialVersionVectorUID;
    }
    
    public boolean isTemporary() {
        return _temporary;
    }

    public void setTemporary(String temporary) {
    	if(temporary!=null && temporary.trim().equalsIgnoreCase(Attribute.TABLE_TEMPORARY_VALUE_TRUE)) {
    		_temporary = true;
    	} else {
    		_temporary = false;
    	}
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getFieldCount() {
        if (_fields == null) {
            return 0;
        }
        return _fields.size();
    }

    public void addField(GCField field) {
        if (_fields == null) {
            _fields = new ArrayList<GCField>();
        }
        _fields.add(field);
    }

    public GCField getField(int indexField) {
        if (_fields == null) {
            return null;
        }
        if (indexField < 0 && indexField >= _fields.size()) {
            return null;
        }
        return (GCField)_fields.get(indexField);
    }

    public void setPrimaryKey(GCPrimaryKey primaryKey) {
        _primaryKey = primaryKey;
    }

    public GCPrimaryKey getPrimaryKey() {
        return _primaryKey;
    }

    public boolean isColumnPrimaryKey(String columnName) {
        if (columnName == null) {
            return false;
        }
        if (_primaryKey == null) {
            return false;
        }
        for (int i = 0; i < _primaryKey.getColumnRefCount(); ++i) {
            GCPrimaryKeyColumnRef columnRef = _primaryKey.getColumnRef(i);
            if (columnRef.getColumnName().equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }

    public String getLog() {
        return _log;
    }

    public void setLog(String log) {
        _log = log;
    }
    
    private String _serialVersionDataUID;
    private String _serialVersionVectorUID;
    //bug # 4535: Added below variable to support versioning for the temporary tables.
    private boolean _temporary = false;
    private String _name;
    private ArrayList<GCField> _fields;
    private GCPrimaryKey _primaryKey;
    private String _log;
}
