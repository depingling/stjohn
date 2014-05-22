package com.cleanwise.tools.gencode;

import java.util.Properties;
import org.w3c.dom.Document;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoader;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToXml;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoaderFactory;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsExpToXmlFactory;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCDbToXml extends Task {
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
    private String _tag;
    private Path _classpath;

    public void setDriver(String driver) {
        _dbDriver = driver;
    }

    public void setUrl(String url) {
        _dbUrl = url;
    }

    public void setUserid(String userid) {
        _dbLogin = userid.toUpperCase();
    }

    public void setPassword(String password) {
        _dbPassword = password.toUpperCase();
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

    public void setTag(String tag) {
        _tag = tag.toUpperCase();
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

    @Override
    public void execute() throws BuildException {
        System.out.println("#########################################################");
        System.out.println("# [GCDbToXml] PROPERTIES");
        System.out.println("#          driver: " + _dbDriver);
        System.out.println("#             url: " + _dbUrl);
        System.out.println("#          userid: " + _dbLogin);
        System.out.println("#           owner: " + _dbTableOwner);
        System.out.println("#          prefix: " + _dbTablePrefix);
        System.out.println("#      tableSpace: " + _dbTableSpace);
        System.out.println("#   schemaPattern: " + _dbSchemaPattern);
        System.out.println("#         outfile: " + _outFile);
        System.out.println("# classVersionUID: " + _classVersionUID);
        System.out.println("#             tag: " + _tag);
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
            GCJavaObjectsExpToXmlFactory expToXmlFactory = GCJavaObjectsExpToXmlFactory.GetInstance();

            GCJavaObjectsLoader loaderFromOra = loaderFactory.getJavaObjectsLoader(GCCodeNames.Loader.FROM_DB_ORACLE);
            GCJavaObjectsExpToXml exportToXml = expToXmlFactory.getJavaObjectsExpToXml(GCCodeNames.ExpToXml.MAIN_XML);

            Properties settings = new Properties();
            if (_dbSchemaPattern != null) {
                settings.put(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN, _dbSchemaPattern);
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
            if (_dbTablePrefix != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN, _dbTablePrefix + "%");
            }
            if (_dbTableOwner != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLE_OWNER, _dbTableOwner);
            }
            if (_classVersionUID != null) {
                settings.put(GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES, _classVersionUID);
            }
            if (_tag != null) {
                settings.put(GCCodeNames.Property.TAG, _tag);
            }
            settings.setProperty(GCCodeNames.Property.USE_CLASS_SERIAL_UID_PROPERTIES, "true");

            loaderFromOra.loadData(settings);
            GCJavaObjects javaObjFromOra = loaderFromOra.getJavaObjects();

            Document doc = exportToXml.exportToXml(settings, javaObjFromOra);
            GCUtils.WriteToFile(_outFile, doc);

        }
        catch (GCException ex) {
            System.out.println("GCException: " + ex.getMessage() + 
                ", module: " + ex.GetModule());
        }
        catch (Exception ex) {
        	ex.printStackTrace();
            System.out.println("Exception: " + ex.getMessage());
        }
    }

}
