/**
 * Title:        GCJavaObjectsLoaderFromXml 
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic.spec;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.GCException;
import com.cleanwise.tools.gencode.GCJavaObjects;
import com.cleanwise.tools.gencode.items.GCField;
import com.cleanwise.tools.gencode.items.GCForeignKey;
import com.cleanwise.tools.gencode.items.GCForeignKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCIndex;
import com.cleanwise.tools.gencode.items.GCIndexColumnRef;
import com.cleanwise.tools.gencode.items.GCPrimaryKey;
import com.cleanwise.tools.gencode.items.GCPrimaryKeyColumnRef;
import com.cleanwise.tools.gencode.items.GCSequence;
import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoader;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCJavaObjectsLoaderFromXml implements GCJavaObjectsLoader {

    public GCJavaObjectsLoaderFromXml() {
        _javaObjects = null;
    }

    public GCJavaObjects getJavaObjects() {
        return _javaObjects;
    }

    public boolean loadData(Properties settings) throws GCException {
        String fileName = "";
        if (settings != null) {
            fileName = (String) settings.get(GCCodeNames.Property.DB_XML_FILE_NAME);
        }
        if (GCUtils.IsEmptyString(fileName)) {
            throw new GCException("File name with xml is not defined, property with name '" + 
                GCCodeNames.Property.DB_XML_FILE_NAME + "' is not defined", 
                "GCJavaObjectsLoaderFromXml.loadData");
        }

        System.out.println("#########################################################");
        System.out.println("# [GCJavaObjectsLoaderFromXml] PROPERTIES");
        System.out.println("# " + GCCodeNames.Property.DB_XML_FILE_NAME + ": " + fileName);
        System.out.println("#########################################################");

        InputStream inputStream = getInputStreamFromFile(fileName);
        Element rootNode = loadXmlDocument(inputStream);

        _javaObjects = new GCJavaObjects();
        _javaObjects.setTables(new ArrayList<GCTable>());
        _javaObjects.setForeignKeys(new ArrayList<GCForeignKey>());
        _javaObjects.setIndexes(new ArrayList<GCIndex>());
        _javaObjects.setSequences(new ArrayList<GCSequence>());

        loadTables(rootNode);
        loadForeignKeys(rootNode);
        loadIndexes(rootNode);
        loadSequences(rootNode);

        return true;
    }

    private void loadField(Element node, GCTable table) throws GCException {
        GCField field = new GCField();
        boolean isNullable = false;
        boolean isPrimaryKey = false;
        long size = -1;
        int digits = 0;
        try {
            isNullable = Boolean.parseBoolean(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_NULLABLE));
        } catch (Exception ex) {
            isNullable = false;
        }
        try {
            isPrimaryKey = Boolean.parseBoolean(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_IS_PRIMARY_KEY));
        } catch (Exception ex) {
            isPrimaryKey = false;
        }
        try {
            size = Long.parseLong(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_SIZE));
        } catch (Exception ex) {
            size = -1;
        }
        try {
            digits = Integer.parseInt(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_DIGITS));
        } catch (Exception ex) {
            digits = 0;
        }
        field.setName(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_DB_NAME));
        field.setSqlType(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_SQL_TYPE));
        field.setType(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_TYPENAME));
        field.setIsNullable(isNullable);
        field.setIsPrimaryKey(isPrimaryKey);
        field.setDigits(digits);
        field.setSize(size);
        field.setDefaultValue(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_DEFAULT_VALUE));
        field.setComments(node.getAttribute(GCCodeNames.Xml.Attribute.COLUMN_COMMENTS));
        table.addField(field);
    }

    private void loadFields(Element node, GCTable table) throws GCException {
        Element nodeField;
        NodeList listFields;
        listFields = node.getElementsByTagName(GCCodeNames.Xml.Node.COLUMN);
        if (listFields == null) {
            return;
        }
        for (int i = 0; i < listFields.getLength(); ++i) {
            nodeField = (Element) listFields.item(i);
            if (nodeField == null) {
                continue;
            }
            loadField(nodeField, table);
        }
    }

    private void loadPrimaryKey(Element node, GCTable table) throws GCException {
        NodeList listPrimaryKeys;
        NodeList listColumns;
        Element nodePrimaryKey;
        listPrimaryKeys = node.getElementsByTagName(GCCodeNames.Xml.Node.PRIMARY_KEY);
        if (listPrimaryKeys == null) {
            return;
        }
        if (listPrimaryKeys.getLength() == 0) {
            return;
        }
        GCPrimaryKey primaryKey = new GCPrimaryKey();
        primaryKey.setName("");
        nodePrimaryKey = (Element) listPrimaryKeys.item(0);
        if (nodePrimaryKey.getAttribute(GCCodeNames.Xml.Attribute.PRIMARY_KEY_NAME) != null) {
            primaryKey.setName(nodePrimaryKey.getAttribute(GCCodeNames.Xml.Attribute.PRIMARY_KEY_NAME));
        }
        listColumns = node.getElementsByTagName(GCCodeNames.Xml.Node.PRIMARY_KEY_COLUMN_REF);
        if (listColumns != null) {
            Element nodeColumn;
            final int columnRefCount = listColumns.getLength();
            for (int i = 0; i < columnRefCount; ++i) {
                nodeColumn = (Element) listColumns.item(i);
                if (nodeColumn == null) {
                    continue;
                }
                GCPrimaryKeyColumnRef columnRef = new GCPrimaryKeyColumnRef();
                columnRef.setColumnName(nodeColumn.getAttribute(GCCodeNames.Xml.Attribute.PRIMARY_KEY_COLUMN_NAME));
                try {
                    columnRef.setKeySeq(Integer.parseInt(nodeColumn.getAttribute(GCCodeNames.Xml.Attribute.PRIMARY_KEY_KEYSEQ)));
                } catch (Exception ex) {
                    if (columnRefCount > 1) {
                        System.out.println("[GCJavaObjectsLoaderFromXml] Erroneous number for " +
                            GCCodeNames.Xml.Attribute.PRIMARY_KEY_KEYSEQ + " in primary key " +
                            primaryKey.getName() + ". " + ex.getMessage());
                    }
                }
                primaryKey.addColumnRef(columnRef);
            }
        }
        table.setPrimaryKey(primaryKey);
    }

    private void loadTable(Element node) throws GCException {
        String serialVersionDataUID = "0";
        String serialVersionVectorUID = "0";

        String log = null;
        //bug # 4535: Added below variable to support versioning for the temporary tables.
        String temporary = GCCodeNames.Xml.Attribute.TABLE_TEMPORARY_VALUE_FALSE;
        GCTable table = new GCTable();
        serialVersionDataUID = node.getAttribute(GCCodeNames.Xml.Attribute.TABLE_SV_DATA_UID);
        serialVersionVectorUID = node.getAttribute(GCCodeNames.Xml.Attribute.TABLE_SV_VECTOR_UID);
        log = node.getAttribute(GCCodeNames.Xml.Attribute.TABLE_LOG);
        if(log != null && log.trim().length()>0){
        	table.setLog(log);
        }
        temporary = node.getAttribute(GCCodeNames.Xml.Attribute.TABLE_TEMPORARY);
        table.setName(node.getAttribute(GCCodeNames.Xml.Attribute.TABLE_DB_NAME));
        table.setSerialVersionDataUID(serialVersionDataUID);
        table.setSerialVersionVectorUID(serialVersionVectorUID);
        table.setTemporary((temporary==null || temporary.trim().equals(""))? GCCodeNames.Xml.Attribute.TABLE_TEMPORARY_VALUE_FALSE : temporary.trim());
        loadFields(node, table);
        loadPrimaryKey(node, table);
        _javaObjects.getTables().add(table);
    }

    private void loadTables(Element node) throws GCException {
        Element nodeTable;
        NodeList listTables;
        listTables = node.getElementsByTagName(GCCodeNames.Xml.Node.TABLE);
        if (listTables == null) {
            return;
        }
        for (int i = 0; i < listTables.getLength(); ++i) {
            nodeTable = (Element) listTables.item(i);
            if (nodeTable == null) {
                continue;
            }
            loadTable(nodeTable);
        }
    }

    private void loadForeignKey(Element node) throws GCException {
        GCForeignKey foreignKey = new GCForeignKey();
        foreignKey.setName(node.getAttribute(GCCodeNames.Xml.Attribute.FKEY_FKNAME));
        foreignKey.setPkName(node.getAttribute(GCCodeNames.Xml.Attribute.FKEY_PKNAME));
        foreignKey.setFkTable(node.getAttribute(GCCodeNames.Xml.Attribute.FKEY_FKTABLE));
        foreignKey.setPkTable(node.getAttribute(GCCodeNames.Xml.Attribute.FKEY_PKTABLE));
        Element nodeColumnRef;
        NodeList listColumnRefs;
        listColumnRefs = node.getElementsByTagName(GCCodeNames.Xml.Node.FKEY_COLUMN_REF);
        if (listColumnRefs != null) {
            final int columnRefCount = listColumnRefs.getLength();
            for (int i = 0; i < columnRefCount; ++i) {
                nodeColumnRef = (Element) listColumnRefs.item(i);
                GCForeignKeyColumnRef columnRef = new GCForeignKeyColumnRef();
                columnRef.setFkColumnName(nodeColumnRef.getAttribute(GCCodeNames.Xml.Attribute.FKEY_FKCOLUMN));
                columnRef.setPkColumnName(nodeColumnRef.getAttribute(GCCodeNames.Xml.Attribute.FKEY_PKCOLUMN));
                try {
                    columnRef.setKeySeq(Integer.parseInt(nodeColumnRef.getAttribute(GCCodeNames.Xml.Attribute.FKEY_KEYSEQ)));
                } catch (Exception ex) {
                    if (columnRefCount > 1) {
                        System.out.println("[GCJavaObjectsLoaderFromXml] Erroneous number for " +
                            GCCodeNames.Xml.Attribute.FKEY_KEYSEQ + " in foreign key " +
                            foreignKey.getName() + ". " + ex.getMessage());
                    }
                }
                foreignKey.addColumnRef(columnRef);
            }
        }
        _javaObjects.getForeignKeys().add(foreignKey);
    }

    private void loadForeignKeys(Element node) throws GCException {
        Element nodeForeignKey;
        NodeList listForeignKeys;
        listForeignKeys = node.getElementsByTagName(GCCodeNames.Xml.Node.FKEY);
        if (listForeignKeys == null) {
            return;
        }
        for (int i = 0; i < listForeignKeys.getLength(); ++i) {
            nodeForeignKey = (Element) listForeignKeys.item(i);
            if (nodeForeignKey == null) {
                continue;
            }
            loadForeignKey(nodeForeignKey);
        }
    }

    private void loadIndex(Element node) throws GCException {
        GCIndex index = new GCIndex();
        index.setTable(node.getAttribute(GCCodeNames.Xml.Attribute.INDEX_TABLE));
        index.setName(node.getAttribute(GCCodeNames.Xml.Attribute.INDEX_NAME));
        Element nodeColumnRef;
        NodeList listColumnRefs;
        String columnName = "";
        String columnKeySeq = "";        
        listColumnRefs = node.getElementsByTagName(GCCodeNames.Xml.Node.INDEX_COLUMN_REF);
        if (listColumnRefs != null) {
            final int columnRefCount = listColumnRefs.getLength();
            for (int i = 0; i < listColumnRefs.getLength(); ++i) {
                nodeColumnRef = (Element) listColumnRefs.item(i);
                GCIndexColumnRef columnRef = new GCIndexColumnRef();
                columnName = nodeColumnRef.getAttribute(GCCodeNames.Xml.Attribute.INDEX_COLUMN_NAME);
                columnKeySeq = nodeColumnRef.getAttribute(GCCodeNames.Xml.Attribute.INDEX_COLUMN_KEYSEQ);
                columnRef.setColumnName(columnName);
                try {
                    columnRef.setKeySeq(Integer.parseInt(columnKeySeq));
                } catch (Exception ex) {
                    if (columnRefCount > 1) {
                        System.out.println("[GCJavaObjectsLoaderFromXml] Erroneous number for " +
                            GCCodeNames.Xml.Attribute.INDEX_COLUMN_KEYSEQ + " in index " +
                            index.getName() + ". " + ex.getMessage());
                    }
                }
                index.addColumnRef(columnRef);
            }
        }
        _javaObjects.getIndexes().add(index);
    }

    private void loadIndexes(Element node) throws GCException {
        Element nodeIndex;
        NodeList listIndexes;
        listIndexes = node.getElementsByTagName(GCCodeNames.Xml.Node.INDEX);
        if (listIndexes == null) {
            return;
        }
        for (int i = 0; i < listIndexes.getLength(); ++i) {
            nodeIndex = (Element) listIndexes.item(i);
            if (nodeIndex == null) {
                continue;
            }
            loadIndex(nodeIndex);
        }
    }

    private void loadSequence(Element node) throws GCException {
        GCSequence sequence = new GCSequence();
        sequence.setName(node.getAttribute(GCCodeNames.Xml.Attribute.SEQUENCE_NAME));
        _javaObjects.getSequences().add(sequence);
    }

    private void loadSequences(Element node) throws GCException {
        Element nodeSequence;
        NodeList listSequences;
        listSequences = node.getElementsByTagName(GCCodeNames.Xml.Node.SEQUENCE);
        if (listSequences == null) {
            return;
        }
        for (int i = 0; i < listSequences.getLength(); ++i) {
            nodeSequence = (Element) listSequences.item(i);
            if (nodeSequence == null) {
                continue;
            }
            loadSequence(nodeSequence);
        }
    }

    private InputStream getInputStreamFromFile(String xmlFileName) throws GCException {
        if (xmlFileName == null || xmlFileName.trim().length() == 0) {
            throw new IllegalArgumentException(
                "[getInputStreamFromFile] Error : The 'xmlFileName' is not defined.");
        }
        InputStream inputStream = null;
        try {
            File file = new File(xmlFileName);
            if (!file.exists()) {
                throw new GCException("[GCJavaObjectsLoaderFromXml] File with name " +
                    xmlFileName + " is not found");
            }
            inputStream = new FileInputStream(file);
        } catch (Exception ex) {
            throw new GCException("[GCJavaObjectsLoaderFromXml] Exception occurred. " +
                ex.getMessage());
        }
        return inputStream;
    }

    private static Element loadXmlDocument(InputStream inputStream) throws
        GCException {
        if (inputStream == null) {
            return null;
        }
        Document document = null;
        Element rootNode = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
            document = parser.parse(new InputSource(inputStream));
            rootNode = document.getDocumentElement();
            if (rootNode != null) {
                rootNode.normalize();
            }
        } catch (Exception ex) {
            throw new GCException("[GCJavaObjectsLoaderFromXml] Exception occurred. " +
                ex.getMessage());
        }
        return rootNode;
    }

    private GCJavaObjects _javaObjects;
}
