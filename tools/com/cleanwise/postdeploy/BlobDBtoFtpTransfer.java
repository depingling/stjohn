package com.cleanwise.postdeploy;

import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import com.cleanwise.service.api.value.EventEmailData;
import com.cleanwise.service.api.value.EventPropertyData;
import com.cleanwise.service.api.value.ProcessPropertyData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

public class BlobDBtoFtpTransfer {
    private static final Logger log = Logger.getLogger(BlobDBtoFtpTransfer.class);
    private static final String JAVA_DATE_FORMAT = "MM/dd/yyyy";
    private static final String SQL_DATE_FORMAT = "mm/dd/yyyy";

    private final String _dbDriverName;
    private final String _dbUrl;
    private final String _dbUser;
    private final String _dbPassword;
    private final String _ftpHost;
    private final String _ftpPort;
    private final String _ftpUser;
    private final String _ftpPassword;
    private final String _ftpDir;
    private String _startDate;
    private boolean _doErase;
    
    private String _startDateString = "";
    
    private static String FTP_HOST = "ftp.blob.host";
    private static String FTP_PORT = "ftp.blob.port";
    private static String FTP_USER = "ftp.blob.user";
    private static String FTP_PASSWORD = "ftp.blob.password";
    private static String FTP_DIR = "ftp.blob.dir";
    private static String START_DATE = "start.date";
    private static String DO_ERASE = "do.erase";
    
    private static String PROPERTY_FILE_NAME = "transfer.properties";

    
    public BlobDBtoFtpTransfer (String dbDriverName,
                                String dbUrl,
                                String dbUser,
                                String dbPassword,
                                String ftpHost,
                                String ftpPort,
                                String ftpUser,
                                String ftpPassword,
                                String ftpDir,
                                String startDate,
                                boolean doErase) {
        _dbDriverName = dbDriverName;
        _dbUrl        = dbUrl;
        _dbUser       = dbUser;
        _dbPassword   = dbPassword;
        _ftpHost      = ftpHost;
        _ftpPort      = ftpPort;
        _ftpUser      = ftpUser;
        _ftpPassword  = ftpPassword;
        _ftpDir       = ftpDir;
        _startDate    = startDate;
        _doErase      = doErase;
    }
    
    public static void main (String[] args) {
        if (args.length < 4) {
            log.info("Usage:");
            log.info("BlobDBtoFtpTransfer <dbDriverName> <dbUrl> <dbUser> <dbPassword> [<propertyFileName>]");
            return;
        }
        
        String propertyFileName = PROPERTY_FILE_NAME;
        if (args.length > 4) {
            propertyFileName = args[4];
        }
        log.info("Loading properties from: " + propertyFileName);
        
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propertyFileName));
        } catch (FileNotFoundException e) {
            log.error("Can't find property file: " + propertyFileName);
        } catch (IOException e) {
            log.error("Error loading properties from: " + propertyFileName);
            return;
        }
        
        String ftpHost = props.getProperty(FTP_HOST);
        if (ftpHost == null || ftpHost.trim().isEmpty()) {
            log.error("Required property value missing: " + FTP_HOST);
            return;
        }
        String ftpPort = props.getProperty(FTP_PORT);
        String ftpUser = props.getProperty(FTP_USER);
        if (ftpUser == null || ftpUser.trim().isEmpty()) {
            log.error("Required property value missing: " + FTP_USER);
            return;
        }
        String ftpPassword = props.getProperty(FTP_PASSWORD);
        if (ftpPassword == null || ftpPassword.trim().isEmpty()) {
            log.error("Required property value missing: " + FTP_PASSWORD);
            return;
        }

        String ftpDir = props.getProperty(FTP_DIR);
        String startDate = props.getProperty(START_DATE);
        if (startDate != null && !startDate.trim().isEmpty()) {
            DateFormat format = new SimpleDateFormat(JAVA_DATE_FORMAT);
            try {
                format.parse(startDate);
            } catch (ParseException e) {
                log.error("Expected Date format (" + SQL_DATE_FORMAT +") value (omitted).  Property: " + START_DATE + " Value: " + startDate);
            }
        }

        boolean doErase = Boolean.valueOf(props.getProperty(DO_ERASE));

        BlobDBtoFtpTransfer transfer = new BlobDBtoFtpTransfer(args[0],
                                                               args[1],
                                                               args[2],
                                                               args[3],
                                                               ftpHost,
                                                               ftpPort,
                                                               ftpUser,
                                                               ftpPassword,
                                                               ftpDir,
                                                               startDate,
                                                               doErase);
        transfer.execute();
    }
    
    public void execute () {
        Connection conn = null;
        BlobStorageAccess bsa = null;
        try {
            Class.forName(_dbDriverName);
            conn = DriverManager.getConnection(_dbUrl, _dbUser, _dbPassword);
            
            bsa = new BlobStorageAccess();
            if (!bsa.initializeFtpStorage(_ftpHost, _ftpPort, _ftpDir, _ftpUser, _ftpPassword)) {
                log.error("Error by FTP Storage initialization");
                return;
            }
            
            _startDateString = (_startDate != null && !_startDate.trim().isEmpty()) ? " AND add_date >= TO_DATE ('" + _startDate + "', '" + SQL_DATE_FORMAT + "')" : "";
            
            transferBlobProcessPropertyTable(conn, bsa);
            transferBlobEventPropertyTable(conn, bsa);
            transferBlobEventEmailTable(conn, bsa);
            
        } catch (ClassNotFoundException e) {
            log.error("Error. Could not find the database driver: " + _dbDriverName);
            return;
        } catch (SQLException e) {
            log.error("SQL error occured: " + e.getMessage());
            return;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
    
    private void transferBlobProcessPropertyTable (Connection conn, BlobStorageAccess bsa) {
        boolean saveAutoCommit = true;
        log.info("[transferBlobProcessPropertyTable] BEGIN");
        try {
            // read DB
            String sqlQuery = "SELECT * FROM " + BlobStorageAccess.TABLE_PROCESS_PROPERTY + 
                              " WHERE " + BlobStorageAccess.FIELD_VAR_VALUE + " IS NOT NULL" + _startDateString;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            List<ProcessPropertyData> processPropertyDV = new ArrayList<ProcessPropertyData>();
            ProcessPropertyData processPropertyD;
            while (rs.next()) {
                processPropertyD = new ProcessPropertyData();
                processPropertyD.setProcessPropertyId(rs.getInt(1));
                processPropertyD.setProcessId(rs.getInt(2));
                processPropertyD.setTaskVarName(rs.getString(3));
                processPropertyD.setPropertyTypeCd(rs.getString(4));
                processPropertyD.setVarValue(rs.getBytes(5));
                processPropertyD.setVarClass(rs.getString(6));
                processPropertyD.setAddBy(rs.getString(7));
                processPropertyD.setAddDate(rs.getTimestamp(8));
                processPropertyD.setModBy(rs.getString(9));
                processPropertyD.setModDate(rs.getTimestamp(10));
                processPropertyD.setNumberVal(rs.getInt(11));
                processPropertyD.setStringVal(rs.getString(12));
                processPropertyD.setDateVal(rs.getTimestamp(13));
                processPropertyD.setBinaryDataServer(rs.getString(14));
                processPropertyD.setBlobValueSystemRef(rs.getString(15));
                processPropertyD.setStorageTypeCd(rs.getString(16));
                processPropertyDV.add(processPropertyD);
            }

            rs.close();
            stmt.close();
            
            // store on FTP
            if (!processPropertyDV.isEmpty()) {
                String ftpFileName;
                boolean saveOK = true;
                for (ProcessPropertyData property : processPropertyDV) {
                    ftpFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_PROCESS_PROPERTY,
                                                                    BlobStorageAccess.FIELD_VAR_VALUE,
                                                                    property.getProcessId());
                    bsa.storeBlobFtpOnly(property.getVarValue(), ftpFileName);
                    if (BlobStorageAccess.STORAGE_FTP.equals(bsa.getStoredToStorageType())) {
                        property.setBlobValueSystemRef(ftpFileName);
                    } else {
                        log.error("[transferBlobProcessPropertyTable] Error transferring blob. File: " + ftpFileName + " FTP error. Stopping.");
                        saveOK = false;
                        break;
                    }
                }
                if (saveOK) {
                    // update DB
                    PreparedStatement pstmt = null;
                    try {
                        saveAutoCommit = conn.getAutoCommit();
                        conn.setAutoCommit(false);

                        String updateQuery = "UPDATE " + BlobStorageAccess.TABLE_PROCESS_PROPERTY +
                                             " SET BINARY_DATA_SERVER = '" + _ftpHost + "'" +
                                             ", BLOB_VALUE_SYSTEM_REF = ?" +
                                             ", STORAGE_TYPE_CD = '" + BlobStorageAccess.STORAGE_FTP + "'" +
                                             (_doErase ? (" ," + BlobStorageAccess.FIELD_VAR_VALUE + " = NULL") : "") +
                                             " WHERE PROCESS_PROPERTY_ID = ?";

                        pstmt = conn.prepareStatement(updateQuery);
                        for (ProcessPropertyData property : processPropertyDV) {
                            pstmt.setString(1, property.getBlobValueSystemRef());
                            pstmt.setInt(2, property.getProcessPropertyId());
                            pstmt.executeUpdate();
                            conn.commit();
                        }
                    } catch (SQLException e ) {
                        if (conn != null) {
                            try {
                                log.error("SQL Error: " + e.getMessage());
                                log.error("[transferBlobProcessPropertyTable] Update transaction is being rolled back");
                                e.printStackTrace();
                                conn.rollback();
                            } catch(SQLException ex) {}
                        }
                    } finally {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                        conn.setAutoCommit(saveAutoCommit);
                    }
                }
            }
        } catch (Exception e) {
            log.error("[transferBlobProcessPropertyTable] Failure while reading DB: " + e.getMessage());
        }
        log.info("[transferBlobProcessPropertyTable] END");
    }
    
    private void transferBlobEventPropertyTable (Connection conn, BlobStorageAccess bsa) {
        boolean saveAutoCommit = true;
        log.info("[transferBlobEventPropertyTable] BEGIN");
        try {
            // read DB
            String sqlQuery = "SELECT * FROM " + BlobStorageAccess.TABLE_EVENT_PROPERTY + 
                              " WHERE " + BlobStorageAccess.FIELD_BLOB_VALUE + " IS NOT NULL" + _startDateString;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            List<EventPropertyData> eventPropertyDV = new ArrayList<EventPropertyData>();
            EventPropertyData eventPropertyD;
            while (rs.next()) {
                eventPropertyD = new EventPropertyData();
                eventPropertyD.setEventPropertyId(rs.getInt(1));
                eventPropertyD.setEventId(rs.getInt(2));
                eventPropertyD.setShortDesc(rs.getString(3));
                eventPropertyD.setType(rs.getString(4));
                eventPropertyD.setBlobValue(rs.getBytes(5));
                eventPropertyD.setNum(rs.getInt(6));
                eventPropertyD.setAddBy(rs.getString(7));
                eventPropertyD.setAddDate(rs.getTimestamp(8));
                eventPropertyD.setModBy(rs.getString(9));
                eventPropertyD.setModDate(rs.getTimestamp(10));
                eventPropertyD.setNumberVal(rs.getInt(11));
                eventPropertyD.setStringVal(rs.getString(12));
                eventPropertyD.setDateVal(rs.getTimestamp(13));
                eventPropertyD.setVarClass(rs.getString(14));
                eventPropertyD.setBinaryDataServer(rs.getString(15));
                eventPropertyD.setBlobValueSystemRef(rs.getString(16));
                eventPropertyD.setStorageTypeCd(rs.getString(17));
                eventPropertyDV.add(eventPropertyD);
            }

            rs.close();
            stmt.close();
            
            // store on FTP
            if (!eventPropertyDV.isEmpty()) {
                String ftpFileName;
                boolean saveOK = true;
                for (EventPropertyData property : eventPropertyDV) {
                    ftpFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_PROPERTY,
                                                                    BlobStorageAccess.FIELD_BLOB_VALUE,
                                                                    property.getEventId());
                    bsa.storeBlobFtpOnly(property.getBlobValue(), ftpFileName);
                    if (BlobStorageAccess.STORAGE_FTP.equals(bsa.getStoredToStorageType())) {
                        property.setBlobValueSystemRef(ftpFileName);
                    } else {
                        log.error("[transferBlobEventPropertyTable] Error transferring blob. File: " + ftpFileName + " FTP error. Stopping.");
                        saveOK = false;
                        break;
                    }
                }
                if (saveOK) {
                    // update DB
                    PreparedStatement pstmt = null;
                    try {
                        saveAutoCommit = conn.getAutoCommit();
                        conn.setAutoCommit(false);

                        String updateQuery = "UPDATE " + BlobStorageAccess.TABLE_EVENT_PROPERTY +
                                             " SET BINARY_DATA_SERVER = '" + _ftpHost + "'" +
                                             ", BLOB_VALUE_SYSTEM_REF = ?" +
                                             ", STORAGE_TYPE_CD = '" + BlobStorageAccess.STORAGE_FTP + "'" +
                                             (_doErase ? (" ," + BlobStorageAccess.FIELD_BLOB_VALUE + " = NULL") : "") +
                                             " WHERE EVENT_PROPERTY_ID = ?";
                        pstmt = conn.prepareStatement(updateQuery);
                        for (EventPropertyData property : eventPropertyDV) {
                            pstmt.setString(1, property.getBlobValueSystemRef());
                            pstmt.setInt(2, property.getEventPropertyId());
                            pstmt.executeUpdate();
                            conn.commit();
                        }
                    } catch (SQLException e ) {
                        if (conn != null) {
                            try {
                                e.printStackTrace();
                                log.error("SQL Error: " + e.getMessage());
                                log.error("[transferBlobEventPropertyTable] Update transaction is being rolled back");
                                conn.rollback();
                            } catch(SQLException ex) {}
                        }
                    } finally {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                        conn.setAutoCommit(saveAutoCommit);
                    }
                }
            }
        } catch (Exception e) {
            log.error("[transferBlobEventPropertyTable] Failure while reading DB: " + e.getMessage());
        }
        log.info("[transferBlobEventPropertyTable] END");
    }
    
    private void transferBlobEventEmailTable (Connection conn, BlobStorageAccess bsa) {
        boolean saveAutoCommit = true;
        log.info("[transferBlobEventEmailTable] BEGIN");
        try {
            // read DB
            String sqlQuery = "SELECT * FROM " + BlobStorageAccess.TABLE_EVENT_EMAIL + 
                              " WHERE (" + BlobStorageAccess.FIELD_ATTACHMENTS + " IS NOT NULL" +
                              " OR " + BlobStorageAccess.FIELD_LONG_TEXT + " IS NOT NULL)" +
                              _startDateString;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            List<EventEmailData> eventEmailDV = new ArrayList<EventEmailData>();
            EventEmailData eventEmailD;
            while (rs.next()) {
                eventEmailD = new EventEmailData();
                eventEmailD.setEventEmailId(rs.getInt(1));
                eventEmailD.setEventId(rs.getInt(2));
                eventEmailD.setToAddress(rs.getString(3));
                eventEmailD.setCcAddress(rs.getString(4));
                eventEmailD.setSubject(rs.getString(5));
                eventEmailD.setText(rs.getString(6));
                eventEmailD.setImportance(rs.getString(7));
                eventEmailD.setEmailStatusCd(rs.getString(8));
                eventEmailD.setAddDate(rs.getTimestamp(9));
                eventEmailD.setAddBy(rs.getString(10));
                eventEmailD.setModDate(rs.getTimestamp(11));
                eventEmailD.setModBy(rs.getString(12));
                eventEmailD.setAttachments(rs.getBytes(13));
                eventEmailD.setFromAddress(rs.getString(14));
                eventEmailD.setLongText(rs.getBytes(15));
                eventEmailD.setBinaryDataServer(rs.getString(16));
                eventEmailD.setAttachmentsSystemRef(rs.getString(17));
                eventEmailD.setLongTextSystemRef(rs.getString(18));
                eventEmailD.setStorageTypeCd(rs.getString(19));
                eventEmailDV.add(eventEmailD);
            }

            rs.close();
            stmt.close();
            
            // store on FTP
            if (!eventEmailDV.isEmpty()) {
                String ftpFileName;
                boolean saveOK = true;
                for (EventEmailData email : eventEmailDV) {
                    if (email.getAttachments() != null) {
                        ftpFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                        BlobStorageAccess.FIELD_ATTACHMENTS,
                                                                        email.getEventId());
                        bsa.storeBlobFtpOnly(email.getAttachments(), ftpFileName);
                        if (BlobStorageAccess.STORAGE_FTP.equals(bsa.getStoredToStorageType())) {
                            email.setAttachmentsSystemRef(ftpFileName);
                        } else {
                            log.error("[transferBlobEventEmailTable] Error transferring blob. File: " + ftpFileName + " FTP error. Stopping.");
                            saveOK = false;
                            break;
                        }
                    }
                    if (email.getLongText() != null) {
                        ftpFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                        BlobStorageAccess.FIELD_LONG_TEXT,
                                                                        email.getEventId());
                        bsa.storeBlobFtpOnly(email.getLongText(), ftpFileName);
                        if (BlobStorageAccess.STORAGE_FTP.equals(bsa.getStoredToStorageType())) {
                            email.setLongTextSystemRef(ftpFileName);
                        } else {
                            log.error("[transferBlobEventEmailTable] Error transferring blob. File: " + ftpFileName + " FTP error. Stopping.");
                            saveOK = false;
                            break;
                        }
                    }
                }
                if (saveOK) {
                    // update DB
                    stmt = conn.createStatement();
                    try {
                        saveAutoCommit = conn.getAutoCommit();
                        conn.setAutoCommit(false);

                        StringBuilder updateQuery = new StringBuilder();               
                        for (EventEmailData email : eventEmailDV) {
                            updateQuery.setLength(0);
                            updateQuery.append("UPDATE ");
                            updateQuery.append(BlobStorageAccess.TABLE_EVENT_EMAIL);
                            updateQuery.append(" SET BINARY_DATA_SERVER = '");
                            updateQuery.append(_ftpHost);
                            updateQuery.append("'");
                            updateQuery.append(", STORAGE_TYPE_CD = '");
                            updateQuery.append(BlobStorageAccess.STORAGE_FTP);
                            updateQuery.append("'");
                            if (email.getAttachments() != null) {
                                updateQuery.append(", ATTACHMENTS_SYSTEM_REF = '");
                                updateQuery.append(email.getAttachmentsSystemRef());
                                updateQuery.append("'");
                                updateQuery.append(_doErase ? (" ," + BlobStorageAccess.FIELD_ATTACHMENTS + " = NULL") : "");
                            }
                            if (email.getLongText() != null) {
                                updateQuery.append(", LONG_TEXT_SYSTEM_REF = '");
                                updateQuery.append(email.getLongTextSystemRef());
                                updateQuery.append("'");
                                updateQuery.append(_doErase ? (" ," + BlobStorageAccess.FIELD_LONG_TEXT + " = NULL") : "");
                            }
                            updateQuery.append(" WHERE EVENT_EMAIL_ID = ");
                            updateQuery.append(email.getEventEmailId());

                            stmt.executeUpdate(updateQuery.toString());
                            conn.commit();
                        }
                    } catch (SQLException e ) {
                        if (conn != null) {
                            try {
                                log.error("SQL Error: " + e.getMessage());
                                log.error("[transferBlobEventEmailTable] Update transaction is being rolled back");
                                conn.rollback();
                            } catch(SQLException ex) {}
                        }
                    } finally {
                        if (stmt != null) {
                            stmt.close();
                        }
                        conn.setAutoCommit(saveAutoCommit);
                    }
                }
            }
        } catch (Exception e) {
            log.error("[transferBlobEventEmailTable] Failure while reading DB: " + e.getMessage());
        }
        log.info("[transferBlobEventEmailTable] END");
    }
}