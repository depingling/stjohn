package com.cleanwise.tools.gencode;

import java.io.StringReader;
import java.util.Properties;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoader;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToSql;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoaderFactory;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToSqlFactory;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCXmlToSql extends Task {
    private String _dbTableSpace;
    private String _dbSchemaPattern;
    private String _inFile;
    private String _outFile;

    public void setSchemaPattern(String schemaPattern) {
        _dbSchemaPattern = schemaPattern.toUpperCase();
    }

    public void setTableSpace(String tableSpace) {
        _dbTableSpace = tableSpace.toUpperCase();
    }

    public void setInfile(String inFile) {
        _inFile = inFile;
    }

    public void setOutfile(String outFile) {
        _outFile = outFile;
    }

    public void execute() throws BuildException {
        System.out.println("#########################################################");
        System.out.println("# [GCXmlToSql] PROPERTIES");
        System.out.println("#   schemaPattern: " + _dbSchemaPattern);
        System.out.println("#      tableSpace: " + _dbTableSpace);
        System.out.println("#          infile: " + _inFile);
        System.out.println("#         outfile: " + _outFile);
        System.out.println("#########################################################");
        
        try {
            GCJavaObjectsLoaderFactory loaderFactory = GCJavaObjectsLoaderFactory.GetInstance();
            GCJavaObjectsExpToSqlFactory expToSqlFactory = GCJavaObjectsExpToSqlFactory.GetInstance();

            GCJavaObjectsLoader loaderFromXml = loaderFactory.getJavaObjectsLoader(GCCodeNames.Loader.FROM_XML);
            GCJavaObjectsExpToSql exportToSqlOra = expToSqlFactory.getJavaObjectsExpToSql(GCCodeNames.ExpToSql.TO_DB_ORACLE);

            Properties settings = new Properties();
            if (_dbSchemaPattern != null) {
                settings.put(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN, _dbSchemaPattern);
            }
            if (_dbTableSpace != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLESPACE, _dbTableSpace);
            }
            if (_inFile != null) {
                settings.put(GCCodeNames.Property.DB_XML_FILE_NAME, _inFile);
            }
            if (_dbTableSpace != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLESPACE, _dbTableSpace);
            }

            loaderFromXml.loadData(settings);
            GCJavaObjects javaObjFromXml = loaderFromXml.getJavaObjects();

            StringReader sql = exportToSqlOra.exportToSql(settings, javaObjFromXml);
            GCUtils.WriteToFile(_outFile, sql);

        }
        catch (GCException ex) {
            System.out.println("GCException: " + ex.getMessage() + 
                ", module: " + ex.GetModule());
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

}
