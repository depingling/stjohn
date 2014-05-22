/**
 * Title:        GCJavaObjectsExpToXmlMain
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */
package com.cleanwise.tools.gencode.logic.spec;

import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCJavaObjects;
import com.cleanwise.tools.gencode.items.GCField;
import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCForeignKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCForeignKeyColumnRefComparator;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCIndexColumnRef;
import com.cleanwise.tools.gencode.items.GCIndexColumnRefComparator;
import com.cleanwise.tools.gencode.items.GCPrimaryKey;
import com.cleanwise.tools.gencode.items.GCPrimaryKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCPrimaryKeyColumnRefComparator;
import com.cleanwise.tools.gencode.items.GCSequence;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToXml;
import com.cleanwise.tools.gencode.items.GCTable;

public class GCJavaObjectsExpToXmlMain implements GCJavaObjectsExpToXml {

    public Document exportToXml(Properties settings, GCJavaObjects javaObjects)
        throws GCException {
        if (javaObjects == null) {
            return null;
        }
        Document document = null;
        try {
            Element nodeRoot = null;

            // Creation of root node in the XML
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
            document = parser.newDocument();
            nodeRoot = document.createElement(GCCodeNames.Xml.Node.DATABASE);
            document.appendChild(nodeRoot);

            // Insertion of the nodes with tables into root node
            if (javaObjects.getTables() != null) {
                for (int i = 0; i < javaObjects.getTables().size(); ++i) {
                    GCTable tableObj = javaObjects.getTables().get(i);
                    insertNodeForTable(nodeRoot, tableObj);
                }
            }

            // Insertion of the nodes with indexes into root node
            if (javaObjects.getIndexes() != null) {
                for (int i = 0; i < javaObjects.getIndexes().size(); ++i) {
                    GCIndex index = javaObjects.getIndexes().get(i);
                    insertNodeForIndex(nodeRoot, index);
                }
            }

            // Insertion of the nodes with foreign keys into root node
            if (javaObjects.getForeignKeys() != null) {
                for (int i = 0; i < javaObjects.getForeignKeys().size(); ++i) {
                    GCForeignKey foreignKey = javaObjects.getForeignKeys().get(i);
                    insertNodeForForeignKey(nodeRoot, foreignKey);
                }
            }

            // Insertion of the nodes with sequences into root node
            if (javaObjects.getSequences() != null) {
                for (int i = 0; i < javaObjects.getSequences().size(); ++i) {
                    GCSequence sequence = javaObjects.getSequences().get(i);
                    insertNodeForSequence(nodeRoot, sequence);
                }
            }
        } catch (GCException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GCException("[GCJavaObjectsExpToXmlMain] Exception occurred. " +
                ex.getMessage());
        }
        return document;
    }

    private void insertNodeForField(Element node, GCField field) throws GCException {
        if (node == null || field == null) {
            return;
        }
        Element nodeField = null;
        nodeField = node.getOwnerDocument().createElement(GCCodeNames.Xml.Node.COLUMN);
        nodeField.setAttribute(GCCodeNames.Xml.Attribute.COLUMN_DB_NAME, field.getName());
        //nodeField.setAttribute(GCCodeNames.Xml.Attribute.COLUMN_SQL_TYPE, field.getSqlType());
        nodeField.setAttribute(GCCodeNames.Xml.Attribute.COLUMN_TYPENAME, field.getType());
        if (field.getSize() > 0) {
            nodeField.setAttribute(GCCodeNames.Xml.Attribute.COLUMN_SIZE, String.valueOf(field.getSize()));
        }
        if (field.getDigits() > 0) {
            nodeField.setAttribute(GCCodeNames.Xml.Attribute.COLUMN_DIGITS, String.valueOf(field.getDigits()));
        }
        if (field.getIsNullable()) {
            nodeField.setAttribute(GCCodeNames.Xml.Attribute.COLUMN_NULLABLE, String.valueOf(field.getIsNullable()));
        }
        if (field.getIsPrimaryKey()) {
            nodeField.setAttribute(GCCodeNames.Xml.Attribute.COLUMN_IS_PRIMARY_KEY, String.valueOf(field.getIsPrimaryKey()));
        }
        node.appendChild(nodeField);
    }

    private void insertNodeForTable(Element node, GCTable table) throws GCException {
        if (node == null || table == null) {
            return;
        }
        Element nodeTable = null;
        nodeTable = node.getOwnerDocument().createElement(GCCodeNames.Xml.Node.TABLE);
        nodeTable.setAttribute(GCCodeNames.Xml.Attribute.TABLE_SV_DATA_UID, table.getSerialVersionDataUID());
        nodeTable.setAttribute(GCCodeNames.Xml.Attribute.TABLE_SV_VECTOR_UID, table.getSerialVersionVectorUID());
        nodeTable.setAttribute(GCCodeNames.Xml.Attribute.TABLE_DB_NAME, table.getName());
        for (int i = 0; i < table.getFieldCount(); ++i) {
            GCField fieldObj = table.getField(i);
            insertNodeForField(nodeTable, fieldObj);
        }
        GCPrimaryKey primaryKey = table.getPrimaryKey();
        if (primaryKey != null) {
            if (primaryKey.getColumnRefCount() > 0) {
                insertNodeForPrimaryKey(nodeTable, primaryKey);
            }
        }
        node.appendChild(nodeTable);
    }

    private void insertNodeForPrimaryKey(Element node, GCPrimaryKey primaryKey) throws GCException {
        if (node == null || primaryKey == null) {
            return;
        }
        Element nodePrimaryKey = null;
        nodePrimaryKey = node.getOwnerDocument().createElement(GCCodeNames.Xml.Node.PRIMARY_KEY);
        nodePrimaryKey.setAttribute(GCCodeNames.Xml.Attribute.PRIMARY_KEY_NAME, primaryKey.getName());
        final int columnRefCount = primaryKey.getColumnRefCount();
        if (columnRefCount > 1) {
            primaryKey.sortColumns(new GCPrimaryKeyColumnRefComparator(GCPrimaryKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
        }
        for (int i = 0; i < columnRefCount; ++i) {
            GCPrimaryKeyColumnRef columnRef = primaryKey.getColumnRef(i);
            Element nodeColumnRef = null;
            nodeColumnRef = nodePrimaryKey.getOwnerDocument().createElement(GCCodeNames.Xml.Node.PRIMARY_KEY_COLUMN_REF);
            nodeColumnRef.setAttribute(GCCodeNames.Xml.Attribute.PRIMARY_KEY_COLUMN_NAME, columnRef.getColumnName());
            if (columnRefCount > 1) {
                nodeColumnRef.setAttribute(GCCodeNames.Xml.Attribute.PRIMARY_KEY_KEYSEQ, String.valueOf(columnRef.getKeySeq()));
            }
            nodePrimaryKey.appendChild(nodeColumnRef);
        }
        node.appendChild(nodePrimaryKey);
    }

    private void insertNodeForForeignKey(Element node, GCForeignKey foreignKey) throws GCException {
        if (node == null || foreignKey == null) {
            return;
        }
        Element nodeForeignKey = null;
        nodeForeignKey = node.getOwnerDocument().createElement(GCCodeNames.Xml.Node.FKEY);
        nodeForeignKey.setAttribute(GCCodeNames.Xml.Attribute.FKEY_FKNAME, foreignKey.getName());
        nodeForeignKey.setAttribute(GCCodeNames.Xml.Attribute.FKEY_PKNAME, foreignKey.getPkName());
        nodeForeignKey.setAttribute(GCCodeNames.Xml.Attribute.FKEY_FKTABLE, foreignKey.getFkTable());
        nodeForeignKey.setAttribute(GCCodeNames.Xml.Attribute.FKEY_PKTABLE, foreignKey.getPkTable());
        final int columnRefCount = foreignKey.getColumnRefCount();
        if (columnRefCount > 1) {
            foreignKey.sortColumns(new GCForeignKeyColumnRefComparator(GCForeignKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
        }
        for (int i = 0; i < columnRefCount; ++i) {
            GCForeignKeyColumnRef columnRef = foreignKey.getColumnRef(i);
            Element nodeColumnRef = null;
            nodeColumnRef = nodeForeignKey.getOwnerDocument().createElement(GCCodeNames.Xml.Node.FKEY_COLUMN_REF);
            nodeColumnRef.setAttribute(GCCodeNames.Xml.Attribute.FKEY_FKCOLUMN, columnRef.getFkColumnName());
            nodeColumnRef.setAttribute(GCCodeNames.Xml.Attribute.FKEY_PKCOLUMN, columnRef.getPkColumnName());
            if (columnRefCount > 1) {
                nodeColumnRef.setAttribute(GCCodeNames.Xml.Attribute.FKEY_KEYSEQ, String.valueOf(columnRef.getKeySeq()));
            }
            nodeForeignKey.appendChild(nodeColumnRef);
        }
        node.appendChild(nodeForeignKey);
    }

    private void insertNodeForIndex(Element node, GCIndex index) throws GCException {
        if (node == null || index == null) {
            return;
        }
        Element nodeIndex = null;
        nodeIndex = node.getOwnerDocument().createElement(GCCodeNames.Xml.Node.INDEX);
        nodeIndex.setAttribute(GCCodeNames.Xml.Attribute.INDEX_TABLE, index.getTable());
        nodeIndex.setAttribute(GCCodeNames.Xml.Attribute.INDEX_NAME, index.getName());
        final int columnRefCount = index.getColumnRefCount();
        if (columnRefCount > 1) {
            index.sortColumns(new GCIndexColumnRefComparator(GCIndexColumnRefComparator.COMPARE_BY_KEYSEQ));
        }
        for (int i = 0; i < columnRefCount; ++i) {
            GCIndexColumnRef columnRef = index.getColumnRef(i);
            Element nodeColumnRef = null;
            nodeColumnRef = nodeIndex.getOwnerDocument().createElement(GCCodeNames.Xml.Node.INDEX_COLUMN_REF);
            nodeColumnRef.setAttribute(GCCodeNames.Xml.Attribute.INDEX_COLUMN_NAME, columnRef.getColumnName());
            if (columnRefCount > 1) {
                nodeColumnRef.setAttribute(GCCodeNames.Xml.Attribute.INDEX_COLUMN_KEYSEQ, String.valueOf(columnRef.getKeySeq()));
            }
            nodeIndex.appendChild(nodeColumnRef);
        }
        node.appendChild(nodeIndex);
    }

    private void insertNodeForSequence(Element node, GCSequence sequence) throws GCException {
        if (node == null || sequence == null) {
            return;
        }
        Element nodeSequence = null;
        nodeSequence = node.getOwnerDocument().createElement(GCCodeNames.Xml.Node.SEQUENCE);
        nodeSequence.setAttribute(GCCodeNames.Xml.Attribute.SEQUENCE_NAME, sequence.getName());
        node.appendChild(nodeSequence);
    }
}
