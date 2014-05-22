/**
 * Title:        SourceCodeObserver
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.srcCodeObserver;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Logger;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.ssh.SSHFTPClient;


public class SourceCodeLoader {

    static public SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

    public interface Properties {
        static public String SERVER_NAME_1     = "server_name_1";
        static public String SERVER_NAME_2     = "server_name_2";
        static public String PORT_NUMBER_1     = "port_1";
        static public String PORT_NUMBER_2     = "port_2";
        static public String INSECURE_1        = "insecure_1";
        static public String INSECURE_2        = "insecure_2";
        static public String FTP_MODE_ACTIVE_1 = "ftp_mode_active_1";
        static public String FTP_MODE_ACTIVE_2 = "ftp_mode_active_2";
        static public String USER_NAME_1       = "user_name_1";
        static public String USER_NAME_2       = "user_name_2";
        static public String PASSWORD_1        = "password_1";
        static public String PASSWORD_2        = "password_2";
        static public String PATH_1            = "path_1";
        static public String PATH_2            = "path_2";
        static public String INCLUDE_FILES     = "include_files";
        static public String EXCLUDE_DIRS      = "exclude_dirs";
        static public String EXCLUDE_FILES     = "exclude_files";
        static public String RESULT_FILE_NAME  = "result_file";
        static public String LOG_FILE_NAME     = "log_file";
    }

    public class ConnectionSettings {
        private String  _serverName;
        private int     _port;
        private boolean _insecure;
        private boolean _ftpModeActive;
        private String  _userName;
        private String  _password;
        ConnectionSettings(String serverName, int port, boolean insecure, 
                boolean ftpModeActive, String userName, String password) {
            _serverName = serverName;
            _port = port;
            _insecure = insecure;
            _ftpModeActive = ftpModeActive;
            _userName = userName;
            _password = password;
        }
        public String getServerName() {
            return _serverName;
        }
        public int getPort() {
            return _port;
        }
        public boolean getInsecure() {
            return _insecure;
        }
        public boolean getFtpModeActive() {
            return _ftpModeActive;
        }
        public String getUserName() {
            return _userName;
        }
        public String getPassword() {
            return _password;
        }
    }

    static Logger LOGGER = Logger.getLogger("SourceCodeObserver");

    private ConnectionSettings _connectionSettings1;
    private ConnectionSettings _connectionSettings2;
    private String _path1;
    private String _path2;
    private String _includeFiles;
    private String _excludeDirs;
    private String _excludeFiles;
    private DirElement _elements1;
    private DirElement _elements2;

    private SSHFTPClient _sftpClient;
    private FTPClient _ftpClient;

    private int _counter;

    public SourceCodeLoader(Map<String, String> properties) {
        _path1 = null;
        _path2 = null;
        _includeFiles = null;
        _excludeDirs = null;
        _excludeFiles = null;
        _elements1 = new DirElement();
        _elements2 = new DirElement();
        _sftpClient = null;
        _ftpClient = null;

        String serverName1 = null;
        String serverName2 = null;
        int port1 = 0;
        int port2 = 0;
        boolean insecure1 = false;
        boolean insecure2 = false;
        boolean ftpModeActive1 = false;
        boolean ftpModeActive2 = false;
        String userName1 = null;
        String userName2 = null;
        String password1 = null;
        String password2 = null;

        _counter = 0;

        if (properties != null) {
            if (properties.get(SourceCodeLoader.Properties.SERVER_NAME_1) != null) {
                serverName1 = (String)properties.get(SourceCodeLoader.Properties.SERVER_NAME_1);
            }
            if (properties.get(SourceCodeLoader.Properties.SERVER_NAME_2) != null) {
                serverName2 = (String)properties.get(SourceCodeLoader.Properties.SERVER_NAME_2);
            }
            if (properties.get(SourceCodeLoader.Properties.PORT_NUMBER_1) != null) {
                port1 = parsePortNumber("Port Number 1", false, 
                        (String)properties.get(SourceCodeLoader.Properties.PORT_NUMBER_1));
            }
            if (properties.get(SourceCodeLoader.Properties.PORT_NUMBER_2) != null) {
                port2 = parsePortNumber("Port Number 2", false, 
                        (String)properties.get(SourceCodeLoader.Properties.PORT_NUMBER_2));
            }
            if (properties.get(SourceCodeLoader.Properties.INSECURE_1) != null) {
                insecure1 = parseBool("insecurity of connection 1", false, 
                        (String)properties.get(SourceCodeLoader.Properties.INSECURE_1));
            }
            if (properties.get(SourceCodeLoader.Properties.INSECURE_2) != null) {
                insecure2 = parseBool("insecurity of connection 2", false, 
                        (String)properties.get(SourceCodeLoader.Properties.INSECURE_2));
            }
            if (properties.get(SourceCodeLoader.Properties.FTP_MODE_ACTIVE_1) != null) {
                ftpModeActive1 = parseBool("active ftp mode 1", false, 
                        (String)properties.get(SourceCodeLoader.Properties.FTP_MODE_ACTIVE_1));
            }
            if (properties.get(SourceCodeLoader.Properties.FTP_MODE_ACTIVE_2) != null) {
                ftpModeActive2 = parseBool("active ftp mode 2", false, 
                        (String)properties.get(SourceCodeLoader.Properties.FTP_MODE_ACTIVE_2));
            }
            if (properties.get(SourceCodeLoader.Properties.USER_NAME_1) != null) {
                userName1 = (String)properties.get(SourceCodeLoader.Properties.USER_NAME_1);
            }
            if (properties.get(SourceCodeLoader.Properties.USER_NAME_2) != null) {
                userName2 = (String)properties.get(SourceCodeLoader.Properties.USER_NAME_2);
            }
            if (properties.get(SourceCodeLoader.Properties.PASSWORD_1) != null) {
                password1 = (String)properties.get(SourceCodeLoader.Properties.PASSWORD_1);
            }
            if (properties.get(SourceCodeLoader.Properties.PASSWORD_2) != null) {
                password2 = (String)properties.get(SourceCodeLoader.Properties.PASSWORD_2);
            }
            if (properties.get(SourceCodeLoader.Properties.PATH_1) != null) {
                _path1 = (String)properties.get(SourceCodeLoader.Properties.PATH_1);
            }
            if (properties.get(SourceCodeLoader.Properties.PATH_2) != null) {
                _path2 = (String)properties.get(SourceCodeLoader.Properties.PATH_2);
            }
            if (properties.get(SourceCodeLoader.Properties.INCLUDE_FILES) != null) {
                _includeFiles = (String)properties.get(SourceCodeLoader.Properties.INCLUDE_FILES);
            }
            if (properties.get(SourceCodeLoader.Properties.EXCLUDE_DIRS) != null) {
                _excludeDirs = (String)properties.get(SourceCodeLoader.Properties.EXCLUDE_DIRS);
            }
            if (properties.get(SourceCodeLoader.Properties.EXCLUDE_FILES) != null) {
                _excludeFiles = (String)properties.get(SourceCodeLoader.Properties.EXCLUDE_FILES);
            }
        }

        _connectionSettings1 = new ConnectionSettings(serverName1, port1, insecure1, ftpModeActive1, userName1, password1);
        _connectionSettings2 = new ConnectionSettings(serverName2, port2, insecure2, ftpModeActive2, userName2, password2);
    }

    public void loadData() throws Exception {
        if (!validateParameters()) {
            ///
            return;
        }

        ///
        connect(_connectionSettings1);
        LOGGER.info("Start of data loading from server 1");
        loadElements(_connectionSettings1.getInsecure(), _path1, _elements1);
        LOGGER.info("Finish of data loading from server 1");
        disconnect();

        ///
        connect(_connectionSettings2);
        LOGGER.info("Start of data loading from server 2");
        loadElements(_connectionSettings2.getInsecure(), _path2, _elements2);
        LOGGER.info("Finish of data loading from server 2");
        disconnect();       
    }

    public void compareLoadedData(Writer writer) throws Exception {
        if (writer == null) {
            LOGGER.severe("[compareLoadedData] Output stream is not defined!");
            return;
        }
        LOGGER.info("Start of data comparison");
        DirsComparator dirComparator = new DirsComparator(writer);
        dirComparator.compareDirElements(_path1, _elements1, _path2, _elements2);
        LOGGER.info("Finish of data comparison");
    }

    private boolean validateParameters() {
        boolean isOk = true;
        if (isStringEmpty(_path1)) {
            isOk = false;
            LOGGER.severe("Path 1 is not defined.");
        }
        if (isStringEmpty(_path2)) {
            isOk = false;
            LOGGER.severe("Path 2 is not defined.");
        }
        if (isStringEmpty(_connectionSettings1.getServerName())) {
            isOk = false;
            LOGGER.severe("Server name 1 is not defined.");
        }
        if (isStringEmpty(_connectionSettings2.getServerName())) {
            isOk = false;
            LOGGER.severe("Server name 2 is not defined.");
        }
        return isOk;
    }

    private void connect(ConnectionSettings connectionSettings) throws Exception {
        StringBuilder buff = new StringBuilder();
        buff.append("Try to connect.\r\n");
        buff.append("        Server: '").append(connectionSettings.getServerName()).append("',\r\n");
        buff.append("        Port: '").append(connectionSettings.getPort()).append("',\r\n");
        buff.append("        Insecure: '").append(connectionSettings.getInsecure()).append("',\r\n");
        buff.append("        UserName: '").append(connectionSettings.getUserName()).append("'.\r\n");
        LOGGER.info(buff.toString());

        if (connectionSettings.getInsecure()) {
            try{
                _ftpClient = new FTPClient();
                _ftpClient.setRemoteHost(connectionSettings.getServerName());
                if (connectionSettings.getPort() > 0) {
                    _ftpClient.setRemotePort(connectionSettings.getPort());
                }
                _ftpClient.connect();
                _ftpClient.login(connectionSettings.getUserName(), connectionSettings.getPassword());
                if (connectionSettings.getFtpModeActive()) {
                    _ftpClient.setConnectMode(FTPConnectMode.ACTIVE);
                } else {
                    _ftpClient.setConnectMode(FTPConnectMode.PASV);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.severe("An error occurred at connection. " + ex.getMessage());
                throw ex;
            }
        } else {
            try{
                _sftpClient = new SSHFTPClient();
                _sftpClient.setRemoteHost(connectionSettings.getServerName());
                if (connectionSettings.getPort() > 0) {
                    _sftpClient.setRemotePort(connectionSettings.getPort());
                }
                _sftpClient.setAuthentication(connectionSettings.getUserName(), connectionSettings.getPassword());
                _sftpClient.getValidator().setHostValidationEnabled(false);
                _sftpClient.setType(FTPTransferType.ASCII); 
                _sftpClient.connect();
            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.severe("An error occurred at connection. " + ex.getMessage());
                throw ex;
            }
        }
        LOGGER.info("Connection was established");
    }

    private void disconnect() {
        if (_ftpClient != null) {
            if (_ftpClient.connected()) {
                try {
                    _ftpClient.quitImmediately();
                } catch (Exception ex) {
                }
            }
        }
        if (_sftpClient != null) {
            if (_sftpClient.connected()) {
                try {
                    _sftpClient.quitImmediately();
                } catch (Exception ex) {
                }
            }
        }
    }

    private void loadElements(boolean insecure, String path, DirElement dirElement) throws Exception {
        try{
            FTPFile[] files = null;
            if (insecure) {
                files = _ftpClient.dirDetails(path);
            } else {
                files = _sftpClient.dirDetails(path);
            }
            if (files != null) {
                loadFileElements(insecure, path, dirElement);
            }
        } catch (Exception ex) {
            LOGGER.severe("An error occurred at elements loading. " + ex.getMessage());
            throw ex;
        }
    }

    private void loadFileElements(boolean insecure, String path, DirElement dirElement) throws Exception {
        if (_counter == 0 || _counter == 35) {
            _counter = 1;
            LOGGER.info("Start of loading of the directory: " + path);
        } else {
            _counter++;
        }
        try {
            FTPFile[] files = null;
            if (insecure) {
                files = _ftpClient.dirDetails(path);
            } else {
                files = _sftpClient.dirDetails(path);
            }
            if (files != null) {
                for (int i = 0; i < files.length; ++i) {
                    FTPFile file = files[i];
                    String fileName = file.getName();
                    if (file.isDir()) {
                        if (fileName.trim().equals(".") || fileName.trim().equals("..")) {
                            continue;
                        }
                        if (_excludeDirs != null) {
                            if (fileName.matches(_excludeDirs)) {
                                continue;
                            }
                        }
                        DirElement subDirElement = FTPFile2DirElement(file);
                        if (subDirElement != null) {
                            String subPath = path + "/" + fileName;
                            loadFileElements(insecure, subPath, subDirElement);
                            dirElement.getDirectories().put(fileName, subDirElement);
                        }
                    } else {
                        if (!file.isLink()) {
                            if (_excludeFiles != null) {
                                if (fileName.matches(_excludeFiles)) {
                                    continue;
                                }
                            }
                            if (_includeFiles != null) {
                                if (!fileName.matches(_includeFiles)) {
                                    continue;
                                }
                            }
                            FileElement fileElement = FTPFile2FileElement(file);
                            if (fileElement != null) {
                                dirElement.getFiles().put(fileName, fileElement);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.severe("An error occurred at elements loading. Path: " + path + ". " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    private FileElement FTPFile2FileElement(FTPFile file) {
        if (file == null) {
            return null;
        }
        FileElement fileElement = new FileElement();
        fileElement.setName(file.getName());
        fileElement.setSize(file.size());
        fileElement.setModificationDate(file.lastModified());
        return fileElement;
    }

    private DirElement FTPFile2DirElement(FTPFile file) {
        if (file == null) {
            return null;
        }
        DirElement dirElement = new DirElement();
        dirElement.setName(file.getName());
        dirElement.setModificationDate(file.lastModified());
        return dirElement;
    }

    private boolean isStringEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    private int parsePortNumber(String itemName, boolean required, String itemValue) {
        int port = 0;
        if (itemValue == null) {
            if (required && LOGGER != null) {
                LOGGER.severe("Value of '" + itemName + "' is not defined");
            }
            return port;
        }
        if (itemValue.trim().length() == 0) {
            if (required && LOGGER != null) {
                LOGGER.severe("Value of '" + itemName + "' is empy");
            }
            return port;
        }
        try {
            port = Integer.parseInt(itemValue);
        } catch (Exception ex) {
            port = 0;
            if (LOGGER != null) {
                LOGGER.severe("Erroneous number for '" + itemName + "'. " + ex.getMessage());
            }
        }
        if (port != 0) {
            if (port < 0 || port > 65535) {
                if (LOGGER != null) {
                    LOGGER.severe("Invalid number for '" + itemName + "'.");
                }
            }
        }
        return port;
    }

    private boolean parseBool(String itemName, boolean required, String itemValue) {
        boolean value = false;
        if (required && itemValue == null) {
            if (LOGGER != null) {
                LOGGER.severe("Value of '" + itemName + "' is not defined");
            }
            return value;
        }
        if (required && itemValue.trim().length() == 0) {
            if (LOGGER != null) {
                LOGGER.severe("Value of '" + itemName + "' is empy");
            }
            return value;
        }
        if ("true".equalsIgnoreCase(itemValue.trim()) ||
            "yes".equalsIgnoreCase(itemValue.trim()) ||
            "ok".equalsIgnoreCase(itemValue.trim())) {
            value = true;
        }
        return value;
    }

}
