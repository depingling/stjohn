package com.cleanwise.tools;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/* 
 * Tried to make this an Ant task, however it seemed there was a
 * conflict the XML parser used internally by Ant and that required by
 * the Xerces/Xalan libraries for this processor.  So made this a
 * standalone app.
 */
public class XMLTableProcessor2 {

    static final public String NODE_SERIAL_VERSION_DATA_UID = "serialVersionDataUID";
    static final public String NODE_SERIAL_VERSION_VECTOR_UID = "serialVersionVectorUID";
    static final public String NODE_TABLE = "table";
    static final public String NODE_COLUMN = "column";
    static final public String NODE_PRIMARY_KEY = "primaryKey";
    static final public String NODE_FOREIGN_KEY = "fkey";
    static final public String NODE_FOREIGN_KEY_COLUMN_REF  = "fkeyref";
    static final public String PRIMARY_KEY_COLUMN_REF = "columnref";
    static final public String NODE_COLUMN_JAVATYPE = "javatype";
    static final public String NODE_COLUMN_JAVANAME = "javaname";
    static final public String NODE_COLUMN_RSGETTER = "rsgetter";
    static final public String NODE_COLUMN_NAME = "name";
    static final public String NODE_COLUMN_TYPE = "type";

    static final public String NODE_ATTR_FK_TABLE  = "fkTable";
    static final public String NODE_ATTR_FK_COLUMN = "fkColumnName";

    static final public String ATTR_TABLE_NAME = "name";
    static final public String ATTR_COLUMN_TYPE = "type";
    static final public String ATTR_COLUMN_NAME = "name";
    static final public String ATTR_COLUMN_SIZE = "size";
    static final public String ATTR_COLUMN_DIGITS = "digits";
    static final public String ATTR_PRIMARY_KEY_NAME = "name";
    static final public String ATTR_NULLABLE = "nullable";

    static final public String VIRTUAL_NODE_SERIAL_VERSION_DATA_UID = "serialVersionDataUID";
    static final public String VIRTUAL_NODE_SERIAL_VERSION_VECTOR_UID = "serialVersionVectorUID";
    static final public String VIRTUAL_NODE_UID_VALUE = "value";
    static final public String VIRTUAL_NODE_COLUMN_NAME = "name";
    static final public String VIRTUAL_NODE_COLUMN_JAVANAME = "javaname";
    static final public String VIRTUAL_NODE_COLUMN_JAVATYPE = "javatype";
    static final public String VIRTUAL_NODE_COLUMN_JAVARESTYPE = "rsgetter";
    static final public String VIRTUAL_NODE_COLUMN_TYPE = "type";
    static final public String VIRTUAL_ATTR_COLUMN_PK = "primary_key";
    static final public String VIRTUAL_ATTR_COLUMN_FK = "foreign_key";
    static final public String VIRTUAL_ATTR_NULLABLE = "nullable";

    static final public String VIRTUAL_ATTR_TABLE_TYPE = "type";
    static final public String VIRTUAL_ATTR_TABLE_JAVANAME = "javaname";


    public void setTagName(String tagName) {
        _tagName = tagName;
    }

    public void setInput(String input) {
        _input = input;
    }

    public void setStyle(String style) {
        _style = style;
    }

    public void setOutdir(String outdir) {
        _outdir = outdir;
    }

    public void setPrefix(String prefix) {
        _prefix = prefix;
    }

    public void setSuffix(String suffix) {
        _suffix = suffix;
    }

    public void setTableprefix(String prefix) {
        _tableprefix = prefix;
    }

    public void setSingleOutFileName(String singleOutFileName) {
        _singleOutFileName = singleOutFileName;
    }

    public void setJavaName(String javaName) {
        _javaName = javaName;
    }

    public String getJavaName() {
        return _javaName;
    }

    public void setVerbose(boolean verbose) {
        _verbose = verbose;
    }

    private void parseArgs(String args[]) {
        int i = 0;
        while (i < args.length) {
            String s = args[i];
            if (s.startsWith("-input")) {
                setInput(s.substring(6).trim());
                i++;
            } else if (s.startsWith("-style")) {
                setStyle(s.substring(6).trim());
                i++;
            } else if (s.startsWith("-outdir")) {
                setOutdir(s.substring(7).trim());
                i++;
            } else if (s.startsWith("-prefix")) {
                setPrefix(s.substring(7).trim());
                i++;
            } else if (s.startsWith("-suffix")) {
                setSuffix(s.substring(7).trim());
                i++;
            } else if (s.startsWith("-tableprefix")) {
                setTableprefix(s.substring("-tableprefix".length()).trim());
                i++;
            } else if (s.equalsIgnoreCase("-verbose")) {
                setVerbose(true);
                i++;
            } else if (s.equalsIgnoreCase("-noverbose")) {
                setVerbose(false);
                i++;
            } else if (s.startsWith("-tagname")) {
                setTagName(s.substring(8).trim());
                i++;
            } else if (s.startsWith("-singleOutFileName")) {
                setSingleOutFileName(s.substring(18).trim());
                i++;
            } else if (s.startsWith("-javaname")) {
                String jn = s.substring("-javaname".length()).trim();
                if ("all".equalsIgnoreCase(jn)) {
                    jn = null;
                }
                setJavaName(jn);
                i++;
            } else {
                System.err.println(args[i]);
                printUsage();
            }
        }
    }

    public static void main(String args[]) {
        XMLTableProcessor2 process = new XMLTableProcessor2();
        process.parseArgs(args);
        process.execute();
    }

    public void log(String msg) {
        if (_verbose) {
            System.out.println(msg);
        }
    }

    public boolean getVerbose() {
        return _verbose;
    }

    public void execute() {
        if (getVerbose()) {
            System.out.println("***************************************************");
            System.out.println("* [XMLTableProcessor] PARAMETERS                  *");
            System.out.println("*           tagName: " + _tagName);
            System.out.println("*          javaName: " + _javaName);
            System.out.println("*             input: " + _input);
            System.out.println("*             style: " + _style);
            System.out.println("*            outdir: " + _outdir);
            System.out.println("*            prefix: " + _prefix);
            System.out.println("*            suffix: " + _suffix);
            System.out.println("*       tableprefix: " + _tableprefix);
            System.out.println("* singleOutFileName: " + _singleOutFileName);
            System.out.println("***************************************************");
        }
    
        if (_javaName != null && _javaName.startsWith("${")) {
            String mess = "[XMLTableProcessor] Parameter javaname is missing. Use -Djavaname=all to jenerate all classes";
            System.out.println(mess);
            return;
        }
        if (_input == null || _input.length() == 0 ||
            _style == null || _style.length() == 0) {
            System.err.println("[XMLTableProcessor] No input xml or style sheet specified.");
            printUsage();
        }
        if (_outdir == null) {
            _outdir = ".";
        }
        File fileOutdir = new File(_outdir);
        if (!fileOutdir.exists()) {
            if (getVerbose()) {
                StringBuffer logBuffer = new StringBuffer();
                logBuffer.append("[XMLTableProcessor] Directory '");
                logBuffer.append(fileOutdir);
                logBuffer.append("' does not exist. Attempting to create.");
                System.out.println(logBuffer.toString());
            }
            boolean flag = fileOutdir.mkdirs();
            if (!flag) {
                StringBuffer logBuffer = new StringBuffer();
                logBuffer.append("[XMLTableProcessor] '");
                logBuffer.append(fileOutdir.getAbsolutePath());
                logBuffer.append("' does not exist, and cannot be created.");
                System.out.println(logBuffer.toString());
                System.exit(1);
            }
        }

        try {
            if (getVerbose()) {
                StringBuffer logBuffer = new StringBuffer();
                logBuffer.append("[XMLTableProcessor] Loading of XML from file '");
                logBuffer.append(_input);
                logBuffer.append("'.");
                System.out.println(logBuffer.toString());
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            File fileInput = new File(_input);
            FileInputStream fileInputStreamXml = new FileInputStream(fileInput);
            Document document = documentBuilder.parse(fileInputStreamXml);

            if (getVerbose()) {
                StringBuffer logBuffer = new StringBuffer();
                logBuffer.append("[XMLTableProcessor] Loading of nodes by tag name '");
                logBuffer.append(_tagName);
                logBuffer.append("'.");
                System.out.println(logBuffer.toString());
            }

            String tableName;
            String tableJavaName;

            NodeList nodeList = document.getElementsByTagName(_tagName);
            LinkedList<Element> nodeLinkedList = new LinkedList<Element>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                tableName = elem.getAttribute(ATTR_TABLE_NAME);
                if (IsEmptyString(tableName)) {
                    StringBuffer logBuffer = new StringBuffer();
                    logBuffer.append("[XMLTableProcessor] The attribute '");
                    logBuffer.append(ATTR_TABLE_NAME);
                    logBuffer.append("' doesn't exist.");
                    System.out.println(logBuffer.toString());
                    continue;
                }
                if (_javaName == null) {
                    nodeLinkedList.add(elem);
                }
                else {
                    tableJavaName = GetJavaNameByDbName(tableName, _tableprefix);
                    if (_javaName.equalsIgnoreCase(tableJavaName)) {
                        nodeLinkedList.add(elem);
                    }
                }
            }

            if (nodeLinkedList.size() == 0) {
                System.out.println("[XMLTableProcessor] There are no data for processing. Check the input parameters.");
            }

            HashSet<String> primaryKeysSet = new HashSet<String>();
            HashMap<String,HashSet<String>> foreignKeysMap;
            NodeList listPrimaryKeys;
            NodeList listPrimaryKeyRefs;
            NodeList listColumns;
            String columnName;
            String javaName;
            String javaType;
            String javaResType;
            boolean isPrimaryKey;
            boolean isNullable;
            boolean isForeignKey;
            long size;
            int digits;

            foreignKeysMap = getForeignKeysMap(document);

            for (Element table : nodeLinkedList) {

                tableName = table.getAttribute(ATTR_TABLE_NAME);
                tableJavaName = GetJavaNameByDbName(tableName, _tableprefix);
                HashSet<String> fkColumnsSet = foreignKeysMap.get(tableName);

                Element svDataUIDElement = addElement(document, table, VIRTUAL_NODE_SERIAL_VERSION_DATA_UID);
                Element svVectorUIDElement = addElement(document, table, VIRTUAL_NODE_SERIAL_VERSION_VECTOR_UID);
                addTextElement(document, svDataUIDElement, VIRTUAL_NODE_UID_VALUE, table.getAttribute(NODE_SERIAL_VERSION_DATA_UID));
                addTextElement(document, svVectorUIDElement, VIRTUAL_NODE_UID_VALUE, table.getAttribute(NODE_SERIAL_VERSION_VECTOR_UID));
                table.setAttribute(VIRTUAL_ATTR_TABLE_JAVANAME, tableJavaName);
                table.setAttribute(VIRTUAL_ATTR_TABLE_TYPE, "TABLE");

                /// Loading of the primary keys.
                primaryKeysSet.clear();
                listPrimaryKeys = table.getElementsByTagName(NODE_PRIMARY_KEY);
                if (listPrimaryKeys != null) {
                    for (int pkIndex = 0; pkIndex < listPrimaryKeys.getLength(); ++pkIndex) {
                        Element pkNode = (Element) listPrimaryKeys.item(pkIndex);
                        listPrimaryKeyRefs = pkNode.getElementsByTagName(PRIMARY_KEY_COLUMN_REF);
                        if (listPrimaryKeyRefs != null) {
                            for (int pkRefIndex = 0; pkRefIndex < listPrimaryKeyRefs.getLength(); ++pkRefIndex) {
                                Element pkRefNode = (Element) listPrimaryKeyRefs.item(pkRefIndex);
                                primaryKeysSet.add(pkRefNode.getAttribute(ATTR_PRIMARY_KEY_NAME));
                            }
                        }
                    }
                }

                /// 
                listColumns = table.getElementsByTagName(NODE_COLUMN);
                if (listColumns != null) {
                    for (int colIndex = 0; colIndex < listColumns.getLength(); ++colIndex) {
                        Element column = (Element) listColumns.item(colIndex);
                        if (!column.getNodeName().equalsIgnoreCase(NODE_COLUMN)) {
                            continue;
                        }
                        try {
                            size = Long.parseLong(column.getAttribute(ATTR_COLUMN_SIZE));
                        } catch (Exception ex) {
                            size = 0;
                        }
                        try {
                            digits = Integer.parseInt(column.getAttribute(ATTR_COLUMN_DIGITS));
                        } catch (Exception ex) {
                            digits = 0;
                        }
                        columnName = column.getAttribute(ATTR_COLUMN_NAME);
                        javaName = GetJavaNameByDbName(columnName, _tableprefix);
                        javaType = GetJavaType(_tableprefix, column.getAttribute(ATTR_COLUMN_TYPE), size, digits);
                        javaResType = GetJavaResultType(columnName, _tableprefix, column.getAttribute(ATTR_COLUMN_TYPE), size, digits);
                        isPrimaryKey = primaryKeysSet.contains(columnName);
                        isForeignKey = fkColumnsSet != null && fkColumnsSet.contains(columnName);
                        isNullable = true;
                        if (column.getAttribute(ATTR_NULLABLE) != null) {
                            isNullable = Boolean.parseBoolean(column.getAttribute(ATTR_NULLABLE));
                        }

                        addTextElement(document, column, VIRTUAL_NODE_COLUMN_JAVATYPE, javaType);
                        addTextElement(document, column, VIRTUAL_NODE_COLUMN_JAVANAME, javaName);
                        addTextElement(document, column, VIRTUAL_NODE_COLUMN_JAVARESTYPE, javaResType);
                        addTextElement(document, column, VIRTUAL_NODE_COLUMN_NAME, column.getAttribute(ATTR_COLUMN_NAME));
                        addTextElement(document, column, VIRTUAL_NODE_COLUMN_TYPE, column.getAttribute(ATTR_COLUMN_TYPE));
                        column.setAttribute(VIRTUAL_ATTR_COLUMN_PK, isPrimaryKey ? "true" : "false");
                        column.setAttribute(VIRTUAL_ATTR_COLUMN_FK, isForeignKey ? "true" : "false");
                        column.setAttribute(VIRTUAL_ATTR_NULLABLE, isNullable ? "true" : "false");
                    }
                    table.normalize();
                }

                String outname = "";
                if (_singleOutFileName != null) {
                    outname = _singleOutFileName;
                    if (nodeList.getLength() > 1) {
                        throw new IllegalStateException(
                            "[XMLTableProcessor] Single output file specified with a xml file with more then 1 node named: " + 
                            _tagName);
                    }
                } else {
                    if (_prefix != null) {
                        outname = new StringBuffer(String.valueOf(_prefix)).append(tableJavaName).toString();
                    }
                    if (_suffix != null) {
                        outname = new StringBuffer(tableJavaName).append(_suffix).toString();
                    }
                }
                System.out.println("[XMLTableProcessor] Result file: " + outname);

                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                StreamResult output = new StreamResult(bout);
                TransformerFactory factory = TransformerFactory.newInstance();
                DOMSource xmlSource = new DOMSource(table);

                FileInputStream fileInputStream2 = new FileInputStream(_style);
                StreamSource streamSource = new StreamSource(fileInputStream2);
                Transformer trans = factory.newTransformer(streamSource);
                trans.transform(xmlSource, output);

                File fileResult = new File(fileOutdir, outname);
                if (fileResult.exists()) {
                    fileResult.delete();
                }
                fileResult.createNewFile();
                FileOutputStream fout = new FileOutputStream(fileResult);
                bout.writeTo(fout);
                fout.flush();
                fout.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log("[XMLTableProcessor] Done. An error occurred. " + ex.getMessage());
        } finally {
            log("[XMLTableProcessor] Done.");
        }
    }

    private HashMap<String, HashSet<String>> getForeignKeysMap(Document document) {

        HashMap<String, HashSet<String>> fKeyMap = new HashMap<String, HashSet<String>>();

        NodeList nodes = document.getElementsByTagName(NODE_FOREIGN_KEY);
        for (int i = 0; i < nodes.getLength(); i++) {

            Element fKeyEl = (Element) nodes.item(i);

            String fkTable = fKeyEl.getAttribute(NODE_ATTR_FK_TABLE);
            if (!IsEmptyString(fkTable)) {

                Element refFKeyEl = (Element) fKeyEl.getElementsByTagName(NODE_FOREIGN_KEY_COLUMN_REF).item(0);

                String fkColumnName = refFKeyEl.getAttribute(NODE_ATTR_FK_COLUMN);
                if (!IsEmptyString(fkTable)) {

                    HashSet<String> fkColumnNameSet = fKeyMap.get(fkTable);
                    if (fkColumnNameSet == null) {
                        fkColumnNameSet = new HashSet<String>();
                        fkColumnNameSet.add(fkColumnName);
                        fKeyMap.put(fkTable, fkColumnNameSet);
                    } else {
                        fkColumnNameSet.add(fkColumnName);
                    }

                }
            }
        }
        return fKeyMap;
    }

    static public Element addTextElement(Document doc, Element parent, String elemName,
        String elemValue) {
        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        parent.appendChild(elem);
        return elem;
    }

    static public Element addElement(Document doc, Element parent, String elemName) {
        Element elem = doc.createElement(elemName);
        parent.appendChild(elem);
        return elem;
    }

    static public boolean IsEmptyString(String str) {
        return str == null || str.trim().length() == 0;
    }

    static public String GetJavaNameByDbName(String name, String prefix) {
        String tempName = name;
        String javaName = "";
        if (!IsEmptyString(prefix)) {
            if (name.length() > prefix.length() && name.startsWith(prefix)) {
                tempName = name.substring(prefix.length());
            }
        }
        StringCharacterIterator it = new StringCharacterIterator(tempName);
        char chr = it.first();
        boolean upFlag = true;
        while (chr != CharacterIterator.DONE) {
            if (chr == '_' || chr ==' ' || chr =='/') {
                upFlag = true;
            } 
            else {
                if (upFlag) {
                    javaName += Character.toUpperCase(chr);
                } else {
                    javaName += Character.toLowerCase(chr);
                }
                upFlag = false;
            }
            chr = it.next();
        }
        return javaName;
    }

    static public String GetJavaType(String prefix, String datatype, long size, int digits) {
        if (datatype.equals("VARCHAR2")) {
            return "String";
        } else if (datatype.equals("NUMBER") && size == 1 && digits == 0 && !prefix.equalsIgnoreCase("CLV")) {
            return "boolean";
        } else if (datatype.equals("NUMBER") && digits == 0 && size != 0 && !prefix.equalsIgnoreCase("CLV")) {
            return "int";
        } else if (datatype.equals("NUMBER")) {
            return "java.math.BigDecimal";
        } else if (datatype.equals("DATE")) {
            return "Date";
        } else if (datatype.equals("CHAR")) {
            return "String";
        } else if (datatype.equals("LONG")) {
            return "String";
        } else if (datatype.equals("CLOB")) {
            return "String";
        } else if (datatype.equals("BLOB")) {
            return "byte[]";
        } else {
            return "XXX";
        }
    }

    static public String GetJavaResultType(String colName, String prefix, String datatype, long size, int digits) {
        if (datatype.equals("VARCHAR2")) {
            return "String";
        } else if (datatype.equals("NUMBER") && size == 1 && digits == 0 && !prefix.equalsIgnoreCase("CLV")) {
            return "Boolean";
        } else if (datatype.equals("NUMBER") && digits == 0 && !prefix.equalsIgnoreCase("CLV")) {
            return "Int";
        } else if (datatype.equals("NUMBER")) {
            return "BigDecimal";
        } else if (datatype.equals("DATE")) {
            int l = colName.length();
            if (l > 5 && colName.substring((l - 5), l).equals("_TIME")) {
                return "Timestamp";
            } else {
                if (colName.equals("MOD_DATE") || colName.equals("ADD_DATE") || colName.endsWith("_DT")) {
                    return "Timestamp";
                } else {
                    return "Date";
                }
            }
        } else if (datatype.equals("TIME")) {
            return "Timestamp";
        } else if (datatype.equals("CHAR")) {
            return "String";
        } else if (datatype.equals("LONG")) {
            return "String";
        } else if (datatype.equals("CLOB")) {
            return "String";
        } else if (datatype.equals("BLOB")) {
            return "Bytes";
        } else {
            return "XXX";
        }
    }

    static void printUsage() {
        String string = 
            "XMLTableProcessor -input <inputfile> -styles <stylelist>" + 
            "\n    Options: " + 
            "\n    " +
            "\n    -input <inputfile>: a db.xml file output from DBtoXML" +
            "\n    -style <stylelist>: the style sheet to process" +
            "\n    -outdir <dir>: the directory in which to dump output" +
            "\n    -prefix <filenameprefix>: The filename prefix, prepended to tablename" +
            "\n    -suffix <filenamesuffix>: The filename suffix, apppended to tablename" +
            "\n    -tagname <tagname>: the tag name in the xml file (optional)" +
            "\n    -tableprefix <tableprefix>: the table prefix, which will be excluded at generation of proper java name (optional)" +
            "\n    -table <table>: table name or \"all\" if all tables requested (mandatory)" +
            "\n";
        System.err.println(string);
        System.exit(1);
    }

    private String _input = null;
    private String _style = null;
    private String _outdir = null;
    private String _prefix = null;
    private String _suffix = null;
    private String _tableprefix = null;
    private String _singleOutFileName = null;
    private boolean _verbose = true;
    private String _tagName = NODE_TABLE;
    private String _javaName = null;
}
