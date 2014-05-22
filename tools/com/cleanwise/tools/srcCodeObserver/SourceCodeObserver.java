/**
 * Title:        SourceCodeObserver
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.srcCodeObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;


import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;


public class SourceCodeObserver extends Task {

    static Logger LOGGER = Logger.getLogger("SourceCodeObserver");

    private String _serverName1;
    private String _serverName2;
    private String _port1;
    private String _port2;
    private String _insecure1;
    private String _insecure2;
    private String _ftpModeActive1;
    private String _ftpModeActive2;
    private String _userName1;
    private String _userName2;
    private String _password1;
    private String _password2;
    private String _path1;
    private String _path2;
    private String _includeFiles;
    private String _excludeDirs;
    private String _excludeFiles;
    private String _resultFile;
    private String _logFile;
    private Path _classpath;

    public void setServerName1(String serverName1) {
        _serverName1 = serverName1;
    }

    public void setServerName2(String serverName2) {
        _serverName2 = serverName2;
    }

    public void setPort1(String port1) {
        _port1 = port1;
    }

    public void setPort2(String port2) {
        _port2 = port2;
    }

    public void setInsecure1(String insecure1) {
        _insecure1 = insecure1;
    }

    public void setInsecure2(String insecure2) {
        _insecure2 = insecure2;
    }

    public void setFtpModeActive1(String ftpModeActive1) {
        _ftpModeActive1 = ftpModeActive1;
    }

    public void setFtpModeActive2(String ftpModeActive2) {
        _ftpModeActive2 = ftpModeActive2;
    }

    public void setUserName1(String userName1) {
        _userName1 = userName1;
    }

    public void setUserName2(String userName2) {
        _userName2 = userName2;
    }

    public void setPassword1(String password1) {
        _password1 = password1;
    }

    public void setPassword2(String password2) {
        _password2 = password2;
    }

    public void setPath1(String path1) {
        _path1 = path1;
    }

    public void setPath2(String path2) {
        _path2 = path2;
    }

    public void setIncludeFiles(String includeFiles) {
        _includeFiles = includeFiles;
    }

    public void setExcludeDirs(String excludeDirs) {
        _excludeDirs = excludeDirs;
    }

    public void setExcludeFiles(String excludeFiles) {
        _excludeFiles = excludeFiles;
    }

    public void setResultFile(String resultFile) {
        _resultFile = resultFile;
    }

    public void setLogFile(String logFile) {
        _logFile = logFile;
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

    public void execute() throws BuildException {
        System.out.println("#########################################################");
        System.out.println("#     serverName1: " + _serverName1);
        System.out.println("#     serverName2: " + _serverName2);
        System.out.println("#           port1: " + _port1);
        System.out.println("#           port2: " + _port2);
        System.out.println("#       insecure1: " + _insecure1);
        System.out.println("#       insecure2: " + _insecure2);
        System.out.println("#  ftpModeActive1: " + _ftpModeActive1);
        System.out.println("#  ftpModeActive2: " + _ftpModeActive2);
        System.out.println("#       userName1: " + _userName1);
        System.out.println("#       userName2: " + _userName2);
        System.out.println("#       password1: " + _password1);
        System.out.println("#       password2: " + _password2);
        System.out.println("#           path1: " + _path1);
        System.out.println("#           path2: " + _path2);
        System.out.println("#    includeFiles: " + _includeFiles);
        System.out.println("#     excludeDirs: " + _excludeDirs);
        System.out.println("#    excludeFiles: " + _excludeFiles);
        System.out.println("#      resultFile: " + _resultFile);
        System.out.println("#         logFile: " + _logFile);
        System.out.println("#########################################################");

        try {
            try {
                AntClassLoader loader = new AntClassLoader(project, _classpath, false);
            } 
            catch (Exception ex) {
                throw new BuildException("ClassNotFoundException: " + ex.getMessage(), ex, location);
            }

            LoggerHandler loggerHandler = new LoggerHandler(_logFile);
            LOGGER.setLevel(Level.ALL);
            Handler[] handlers = LOGGER.getHandlers();
            if (handlers != null) {
                for (int i = 0; i < handlers.length; ++i) {
                    Handler handler = (Handler)handlers[i];
                    LOGGER.removeHandler(handler);
                }
            }
            LOGGER.addHandler(loggerHandler);

            Map<String, String> properties = new HashMap<String, String>();
            properties.put(SourceCodeLoader.Properties.SERVER_NAME_1,     _serverName1);
            properties.put(SourceCodeLoader.Properties.PORT_NUMBER_1,     _port1);
            properties.put(SourceCodeLoader.Properties.INSECURE_1,        _insecure1);
            properties.put(SourceCodeLoader.Properties.FTP_MODE_ACTIVE_1, _ftpModeActive1);
            properties.put(SourceCodeLoader.Properties.USER_NAME_1,       _userName1);
            properties.put(SourceCodeLoader.Properties.PASSWORD_1,        _password1);
            properties.put(SourceCodeLoader.Properties.PATH_1,            _path1);

            properties.put(SourceCodeLoader.Properties.SERVER_NAME_2,     _serverName2);
            properties.put(SourceCodeLoader.Properties.PORT_NUMBER_2,     _port2);
            properties.put(SourceCodeLoader.Properties.INSECURE_2,        _insecure2);
            properties.put(SourceCodeLoader.Properties.FTP_MODE_ACTIVE_2, _ftpModeActive2);
            properties.put(SourceCodeLoader.Properties.USER_NAME_2,       _userName2);
            properties.put(SourceCodeLoader.Properties.PASSWORD_2,        _password2);
            properties.put(SourceCodeLoader.Properties.PATH_2,            _path2);

            properties.put(SourceCodeLoader.Properties.INCLUDE_FILES,     _includeFiles);
            properties.put(SourceCodeLoader.Properties.EXCLUDE_DIRS,      _excludeDirs);
            properties.put(SourceCodeLoader.Properties.EXCLUDE_FILES,     _excludeFiles);
            properties.put(SourceCodeLoader.Properties.RESULT_FILE_NAME,  _resultFile);
            properties.put(SourceCodeLoader.Properties.LOG_FILE_NAME,     _logFile);

            //properties.put(SourceCodeLoader.Properties.INCLUDE_FILES, ".*\\.java||.*\\.jsp||.*\\.html||.*\\.xml||.*\\.css||.*\\.js");
            //properties.put(SourceCodeLoader.Properties.EXCLUDE_DIRS, "CVS");
            //properties.put(SourceCodeLoader.Properties.EXCLUDE_FILES, "\\.#.*");

            SourceCodeLoader sourceCodeLoader = new SourceCodeLoader(properties);
            try {
                sourceCodeLoader.loadData();
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            try {
                DefaultWriter resultWriter = new DefaultWriter(_resultFile);
                sourceCodeLoader.compareLoadedData(resultWriter);
                resultWriter.flush();
                resultWriter.close();
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

}
