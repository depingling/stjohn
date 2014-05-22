/**
 * Title:        GCCodeNames
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode;


public interface GCCodeNames {
    public interface Property {
        public static final String DATABASE_DRIVER = "DATABASE_DRIVER";
        public static final String DATABASE_URL = "DATABASE_URL";
        public static final String DATABASE_LOGIN = "DATABASE_LOGIN";
        public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";
        public static final String DATABASE_TABLESPACE = "DATABASE_TABLESPACE";
        public static final String DATABASE_TABLE_OWNER = "DATABASE_TABLE_OWNER";
        public static final String DATABASE_TABLE_PREFIX = "DATABASE_TABLE_PREFIX";
        public static final String DATABASE_SCHEMA_PATTERN = "DATABASE_SCHEMA_PATTERN";
        public static final String DATABASE_TABLE_NAME_PATTERN = "DATABASE_TABLE_NAME_PATTERN";
        public static final String DB_XML_FILE_NAME = "DB_XML_FILE_NAME";
        public static final String CLASS_SERIAL_UID_PROPERTIES = "CLASS_SERIAL_UID_PROPERTIES";
        public static final String USE_CLASS_SERIAL_UID_PROPERTIES = "USE_CLASS_SERIAL_UID_PROPERTIES";
        public static final String TAG = "TAG";
        public static final String NO_OF_DATABASE_TABLE_PREFIXES = "NO_OF_DATABASE_TABLE_PREFIXES";
        public static final String LOG_TABLE_PREFIX = "LOG_TABLE_PREFIX";
    }

    public interface Xml {
        public interface Node {
            public static final String DATABASE                = "database";
            public static final String TABLE                   = "table";
            public static final String COLUMN                  = "column";
            public static final String JAVATYPE                = "javatype";
            public static final String JAVANAME                = "javaname";
            public static final String NAME                    = "name";
            public static final String TYPE                    = "type";
            public static final String FKEY                    = "fkey";
            public static final String FKEY_COLUMN_REF         = "fkeyref";
            public static final String INDEX                   = "index";
            public static final String INDEX_COLUMN_REF        = "columnref";
            public static final String PRIMARY_KEY             = "primaryKey";
            public static final String PRIMARY_KEY_COLUMN_REF  = "columnref";
            public static final String SEQUENCE                = "sequence";
        }
        public interface Attribute {
            public static final String TABLE_DB_NAME            	= "name";
            public static final String TABLE_JAVA_NAME          	= "javaname";
            public static final String TABLE_SV_DATA_UID        	= "serialVersionDataUID";
            public static final String TABLE_SV_VECTOR_UID      	= "serialVersionVectorUID";
            public static final String COLUMN_DB_NAME           	= "name";
            public static final String COLUMN_JAVA_NAME         	= "javaname";
            public static final String COLUMN_SQL_TYPE          	= "sqltype";
            public static final String COLUMN_TYPENAME          	= "type";
            public static final String COLUMN_NULLABLE          	= "nullable";
            public static final String COLUMN_SIZE              	= "size";
            public static final String COLUMN_MAX_SIZE          	= "maxsize";
            public static final String COLUMN_DIGITS            	= "digits";
            public static final String COLUMN_IS_PRIMARY_KEY    	= "isPrimaryKey";
            public static final String COLUMN_DEFAULT_VALUE     	= "default";
            public static final String COLUMN_COMMENTS          	= "comments";
            public static final String PRIMARY_KEY              	= "primaryKey";
            public static final String FKEY_FKNAME              	= "name";
            public static final String FKEY_PKNAME              	= "pkName"; // should I preserve it for Postgres or comment it ?
            public static final String FKEY_FKTABLE             	= "fkTable";
            public static final String FKEY_PKTABLE            		= "pkTable";
            public static final String FKEY_KEYSEQ              	= "keyseq";
            public static final String FKEY_UPDATE_RULE         	= "updateRule";
            public static final String FKEY_DELETE_RULE         	= "deleteRule";
            public static final String FKEY_FKCOLUMN           	 	= "fkColumnName";
            public static final String FKEY_PKCOLUMN            	= "pkColumnName";
            public static final String INDEX_TABLE              	= "table";
            public static final String INDEX_NAME               	= "name";
            public static final String INDEX_SORT_TYPE          	= "sort";
            public static final String INDEX_IS_UNIQUE          	= "isunique";
            public static final String INDEX_TYPE               	= "type";
            public static final String INDEX_COLUMN_NAME        	= "name";
            public static final String INDEX_COLUMN_KEYSEQ      	= "keyseq";
            public static final String PRIMARY_KEY_NAME         	= "name";
            public static final String PRIMARY_KEY_COLUMN_NAME  	= "name";
            public static final String PRIMARY_KEY_KEYSEQ       	= "keyseq";
            public static final String SEQUENCE_NAME            	= "name";
            public static final String TABLE_TEMPORARY				= "temporary";
            public static final String TABLE_TEMPORARY_VALUE_TRUE 	= "true";
            public static final String TABLE_TEMPORARY_VALUE_FALSE 	= "false";
            public static final String TABLE_LOG 	= "log";
        }
    }

    public interface Loader {
        public static final String FROM_XML                  = "FROM_XML";
        public static final String FROM_DB_ORACLE            = "FROM_DB_ORACLE";
        public static final String FROM_DB_MYSQL             = "FROM_DB_MYSQL";
        public static final String FROM_DB_ENTERPRISEDB      = "FROM_DB_ENTERPRISEDB";
    }

    public interface CmpToSql {
        public static final String TO_DB_ORACLE            = "TO_DB_ORACLE";
        public static final String TO_DB_MYSQL             = "TO_DB_MYSQL";
        public static final String TO_DB_ENTERPRISEDB      = "TO_DB_ENTERPRISEDB";
    }

    public interface ExpToXml {
        public static final String MAIN_XML     = "MAIN_XML";
    }

    public interface ExpToSql {
        public static final String TO_DB_ORACLE            = "TO_DB_ORACLE";
        public static final String TO_DB_MYSQL             = "TO_DB_MYSQL"; 
        public static final String TO_DB_ENTERPRISEDB      = "TO_DB_ENTERPRISEDB";
    }

}
