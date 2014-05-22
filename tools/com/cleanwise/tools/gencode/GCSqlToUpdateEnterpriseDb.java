package com.cleanwise.tools.gencode;

import java.util.Properties;
import java.io.StringReader;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import com.cleanwise.tools.gencode.logic.GCJavaObjectsCmpToSql;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsCmpToSql1;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsCmpToSqlFactory;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoader;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoaderFactory;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsCmpToSqlEnterpriseDB;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCSqlToUpdateEnterpriseDb extends Task {
    private String _dbDriver;
    private String _dbUrl;
    private String _dbLogin;
    private String _dbPassword;
    private String _dbTableOwner;
    private String _dbTablePrefix;
    private String _dbTableSpace;
    private String _dbSchemaPattern;
    private String _classVersionUID;
    private String _outFile;
    private String _dbXmlFile;
    private Path _classpath;

    public void setDriver(String driver) {
        _dbDriver = driver;
    }

    public void setDbXmlFile(String dbXmlFile) {
        _dbXmlFile = dbXmlFile;
    }

    public void setUrl(String url) {
        _dbUrl = url;
    }

    public void setUserid(String userid) {
        _dbLogin = userid.trim();
    }

    public void setPassword(String password) {
        _dbPassword = password.trim();
    }

    public void setOwner(String owner) {
        _dbTableOwner = owner.toUpperCase();
    }

    public void setPrefix(String prefix) {
        _dbTablePrefix = prefix.toUpperCase();
    }

    public void setTableSpace(String tableSpace) {
        _dbTableSpace = tableSpace.toUpperCase();
    }

    public void setSchemaPattern(String schemaPattern) {
        _dbSchemaPattern = schemaPattern.toUpperCase();
    }

    public void setOutfile(String outFile) {
        _outFile = outFile;
    }

    public void setClasspath(Path classpath) {
        if (_classpath == null) {
            _classpath = classpath;
        } else {
            _classpath.append(classpath);
        }
    }

    public Path createClasspath() {
        if (_classpath == null) {
            _classpath = new Path(project);
        }
        return _classpath.createPath();
    }

    public void setClasspathRef(Reference r) {
        createClasspath().setRefid(r);
    }

    public void setClassVersionUID(String classVersionUID) {
        _classVersionUID = classVersionUID;
    }

    public void execute() throws BuildException {
        System.out.println("#########################################################");
        System.out.println("# [GCSqlToUpdateDb] PROPERTIES");
        System.out.println("#           dbXmlFile: " + _dbXmlFile);
        System.out.println("#              driver: " + _dbDriver);
        System.out.println("#                 url: " + _dbUrl);
        System.out.println("#              userid: " + _dbLogin);
        System.out.println("#               owner: " + _dbTableOwner);
        System.out.println("#              prefix: " + _dbTablePrefix);
        System.out.println("#          tableSpace: " + _dbTableSpace);
        System.out.println("#       schemaPattern: " + _dbSchemaPattern);
        System.out.println("#             outfile: " + _outFile);
        System.out.println("#     classVersionUID: " + _classVersionUID);
        System.out.println("#########################################################");

        try {
            try {
                AntClassLoader loader = new AntClassLoader(project, _classpath, false);
                Class.forName(_dbDriver);
            }
            catch (Exception ex) {
                throw new BuildException("ClassNotFoundException: " + ex.getMessage(), ex, location);
            }

            GCJavaObjectsLoaderFactory loaderFactory = GCJavaObjectsLoaderFactory.GetInstance();
            GCJavaObjectsCmpToSqlFactory cmpToSqlFactory = GCJavaObjectsCmpToSqlFactory.GetInstance();

            GCJavaObjectsLoader loaderFromXml = loaderFactory.getJavaObjectsLoader(GCCodeNames.Loader.FROM_XML);
            GCJavaObjectsLoader loaderFromOra = null;
            GCJavaObjectsCmpToSql cmpToSql = null;   // Oracle
            GCJavaObjectsCmpToSql1 cmpToSql1 = null; //Enterprise DB
            if ("com.edb.Driver".equals(_dbDriver)) {
                loaderFromOra = loaderFactory.getJavaObjectsLoader(GCCodeNames.Loader.FROM_DB_ENTERPRISEDB);
                cmpToSql1 = cmpToSqlFactory.getJavaObjectsCmpToSql1(GCCodeNames.CmpToSql.TO_DB_ENTERPRISEDB);
            } else {
                loaderFromOra = loaderFactory.getJavaObjectsLoader(GCCodeNames.Loader.FROM_DB_ORACLE);
                cmpToSql = cmpToSqlFactory.getJavaObjectsCmpToSql(GCCodeNames.CmpToSql.TO_DB_ORACLE);
            }
            Properties settings = new Properties();
            if (_dbXmlFile != null) {
                settings.put(GCCodeNames.Property.DB_XML_FILE_NAME, _dbXmlFile);
            }
            if (_dbTableSpace != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLESPACE, _dbTableSpace);
            }
            if (_dbDriver != null) {
                settings.put(GCCodeNames.Property.DATABASE_DRIVER, _dbDriver);
            }
            if (_dbUrl != null) {
                settings.put(GCCodeNames.Property.DATABASE_URL, _dbUrl);
            }
            if (_dbLogin != null) {
                settings.put(GCCodeNames.Property.DATABASE_LOGIN, _dbLogin);
            }
            if (_dbPassword != null) {
                settings.put(GCCodeNames.Property.DATABASE_PASSWORD, _dbPassword);
            }
            if (_dbTablePrefix != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLE_PREFIX, _dbTablePrefix);
            }
            if (_dbTableOwner != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLE_OWNER, _dbTableOwner);
            }
            if (_dbTablePrefix != null) {
                String[] _dbTablePrefixes = _dbTablePrefix.split(",");
                StringBuilder buffer = new StringBuilder();
                for (String item : _dbTablePrefixes) {
                    if (buffer.length() > 0) {
                        buffer.append(",");
                    }
                    buffer.append(item);
                }
                settings.put(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN,
                        buffer.toString());
            }
            if (_dbSchemaPattern != null) {
                settings.put(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN, _dbSchemaPattern);
            }
            if (_classVersionUID != null) {
                settings.put(GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES, _classVersionUID);
            }
            settings.setProperty(GCCodeNames.Property.USE_CLASS_SERIAL_UID_PROPERTIES, "");

            loaderFromOra.loadData(settings);
            GCJavaObjects javaObjFromOra = loaderFromOra.getJavaObjects();

            loaderFromXml.loadData(settings);
            GCJavaObjects javaObjFromXml = loaderFromXml.getJavaObjects();
System.out.println("GCSqlToUpdateDb GGGGGGGGGG cmpToSql1: "+cmpToSql1);
            StringReader stringReader = cmpToSql1.compareAndBuildSql(settings, javaObjFromXml, javaObjFromOra);
            GCUtils.WriteToFile(_outFile, stringReader);

        }
        catch (GCException ex) {
            ex.printStackTrace();
            System.out.println("GCException: " + ex.getMessage() +
                ", module: " + ex.GetModule());
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

}
