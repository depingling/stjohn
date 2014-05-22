package com.cleanwise.tools.gencode;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import com.cleanwise.tools.gencode.items.GCTable;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsCmpToSql;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsCmpToSqlFactory;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoader;
import com.cleanwise.tools.gencode.logic.GCJavaObjectsLoaderFactory;
import com.cleanwise.tools.gencode.utils.GCUtils;

public class GCSqlToUpdateDb extends Task {
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
    private String _dbLogTablePrefix;

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
    
    public void setLogPrefix(String logPrefix) {
        _dbLogTablePrefix = logPrefix.toUpperCase();
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
            GCJavaObjectsLoader loaderFromOra = loaderFactory.getJavaObjectsLoader(GCCodeNames.Loader.FROM_DB_ORACLE);
            GCJavaObjectsLoader logLoaderFromOra = loaderFactory.getJavaObjectsLoader(GCCodeNames.Loader.FROM_DB_ORACLE);
            GCJavaObjectsCmpToSql cmpToSql = cmpToSqlFactory.getJavaObjectsCmpToSql(GCCodeNames.CmpToSql.TO_DB_ORACLE);

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
            
            if (_dbTableOwner != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLE_OWNER, _dbTableOwner);
            }
            /*if (_dbTablePrefix != null) {
                settings.put(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN, _dbTablePrefix + "%");
            }*/
            if (_dbSchemaPattern != null) {
                settings.put(GCCodeNames.Property.DATABASE_SCHEMA_PATTERN, _dbSchemaPattern);
            }
            if (_classVersionUID != null) {
                settings.put(GCCodeNames.Property.CLASS_SERIAL_UID_PROPERTIES, _classVersionUID);
            }
            settings.setProperty(GCCodeNames.Property.USE_CLASS_SERIAL_UID_PROPERTIES, "");
                   
            GCJavaObjects javaLogObjFromOra = null;
            if(_dbLogTablePrefix != null){
            	settings.put(GCCodeNames.Property.LOG_TABLE_PREFIX, _dbLogTablePrefix);
            	settings.put(GCCodeNames.Property.DATABASE_TABLE_PREFIX+"_"+0,_dbLogTablePrefix);
            	settings.put(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN+"_"+0,_dbLogTablePrefix+"%");
            	settings.put(GCCodeNames.Property.NO_OF_DATABASE_TABLE_PREFIXES, "1");
                logLoaderFromOra.loadData(settings);
                javaLogObjFromOra = logLoaderFromOra.getJavaObjects();
            }
            
            if (_dbTablePrefix != null) {
            	//bug # 4535: Modified to support versioning for the temporary tables.
        		StringTokenizer prefixTokens = new StringTokenizer(_dbTablePrefix,",");
        		List<String> tokensList = new ArrayList<String>();
        		while(prefixTokens.hasMoreTokens()) {
        			tokensList.add(prefixTokens.nextToken());
        		}
        		int index=0;
        		for(String token:tokensList) {
        			settings.put(GCCodeNames.Property.DATABASE_TABLE_PREFIX+"_"+index,token);
        			settings.put(GCCodeNames.Property.DATABASE_TABLE_NAME_PATTERN+"_"+index,token+"%");
        			index++;
        		}
        		settings.put(GCCodeNames.Property.NO_OF_DATABASE_TABLE_PREFIXES, String.valueOf(tokensList.size()));
        		
                //settings.put(GCCodeNames.Property.DATABASE_TABLE_PREFIX, _dbTablePrefix);
            }
            loaderFromOra.loadData(settings);
            GCJavaObjects javaObjFromOra = loaderFromOra.getJavaObjects();

            loaderFromXml.loadData(settings);
            GCJavaObjects javaObjFromXml = loaderFromXml.getJavaObjects();

            StringReader stringReader = cmpToSql.compareAndBuildSql(settings, javaObjFromXml, javaObjFromOra, javaLogObjFromOra);
            GCUtils.WriteToFile(_outFile, stringReader);

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
