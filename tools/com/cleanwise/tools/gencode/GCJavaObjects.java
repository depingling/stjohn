/**
 * Title:        GCJavaObjects
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode;

import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCSequence;
import java.util.ArrayList;


public class GCJavaObjects {

    public GCJavaObjects() {
        _tables = null;
        _foreignKeys = null;
        _indexes = null;
        _sequences = null;
    }

    public ArrayList<GCForeignKey> getForeignKeys() {
        return _foreignKeys;
    }

    public void setForeignKeys(ArrayList<GCForeignKey> foreignKeys) {
        this._foreignKeys = foreignKeys;
    }

    public ArrayList<GCTable> getTables() {
        return _tables;
    }

    public void setTables(ArrayList<GCTable> tables) {
        _tables = tables;
    }

    public ArrayList<GCIndex> getIndexes() {
        return _indexes;
    }

    public void setIndexes(ArrayList<GCIndex> indexes) {
        _indexes = indexes;
    }

    public ArrayList<GCSequence> getSequences() {
        return _sequences;
    }

    public void setSequences(ArrayList<GCSequence> sequences) {
        _sequences = sequences;
    }

    private ArrayList<GCTable> _tables;
    private ArrayList<GCForeignKey> _foreignKeys;
    private ArrayList<GCIndex> _indexes;
    private ArrayList<GCSequence> _sequences;

}
