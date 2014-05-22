package com.cleanwise.useractivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.UnknownHostException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class ProcessUserActivityLogInfo {
    private final static int MAX_PARAMS_LEN = 3500;

    private String _serverName;
    private final String _dbDriverName;
    private final String _dbUrl;
    private final String _dbUser;
    private final String _dbPassword;
    private final String _dbSchema;
    private final String _logDir;
    private final String _processFile;
    
    private final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSSS");

    private static String LOG_FILE_PATTERN = "server.log.";
    
    private static String COMMAND_PARSELOG = "parselog";
    private static String COMMAND_UPDATEDB = "updatedb";

    public ProcessUserActivityLogInfo (String dbDriverName,
                                       String dbUrl,
                                       String dbUser,
                                       String dbPassword,
                                       String logDir,
                                       String processFile,
                                       String dbSchema) {
        _dbDriverName = dbDriverName;
        _dbUrl        = dbUrl;
        _dbUser       = dbUser;
        _dbPassword   = dbPassword;
        _logDir       = logDir;
        _processFile  = processFile;
        _dbSchema     = dbSchema;

        try {
            _serverName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            _serverName = System.getenv("HOSTNAME");
        }
    }

    public static void main(String[] args) {
        System.out.println("Start of ProcessUserActivityLogInfo. Date: " + new Date());
        
        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println("ProcessUserActivityLogInfo parselog|updatedb <dbDriverName> <dbUrl> <dbUser> <dbPassword> <logDirectory> [<processFile>] [<dbSchema>]");
            return;
        }

        String command = args[0];
        if (COMMAND_UPDATEDB.equalsIgnoreCase(command)) {
            if (args.length < 5) {
                System.out.println("Usage:");
                System.out.println("ProcessUserActivityLogInfo parselog|updatedb <dbDriverName> <dbUrl> <dbUser> <dbPassword> [<logDirectory>] [<processFile>] [<dbSchema>]");
                return;
            }
        } else if (COMMAND_PARSELOG.equalsIgnoreCase(command)) {
            if (args.length < 6) {
                System.out.println("Usage:");
                System.out.println("ProcessUserActivityLogInfo parselog|updatedb <dbDriverName> <dbUrl> <dbUser> <dbPassword> <logDirectory> [<processFile>] [<dbSchema>]");
                return;
            }
        }
        
        String logDir = null;
        if (args.length > 5) {
            logDir = args[5];
        }
        
        String processFile = null;
        if (args.length > 6) {
            processFile = args[6];
        }

        String dbSchema = null;
        if (args.length > 7) {
            dbSchema = args[7];
        }
        
        ProcessUserActivityLogInfo logInfo = new ProcessUserActivityLogInfo(args[1],
                                                                            args[2],
                                                                            args[3],
                                                                            args[4],
                                                                            logDir,
                                                                            processFile,
                                                                            dbSchema);
        try {
            if (COMMAND_PARSELOG.equalsIgnoreCase(command)) {
                logInfo.process();
            } else if (COMMAND_UPDATEDB.equalsIgnoreCase(command)) {
                logInfo.updateDB();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("End of ProcessUserActivityLogInfo. Date: " + new Date());
    }

    public void process() throws Exception {
    	Connection conn = null;
        boolean saveAutoCommit = true;
        PreparedStatement pstmt = null;
        try {
            File dir = new File(_logDir);
            if (!dir.exists() || !dir.isDirectory()) {
                System.err.println("Cannot find directory - " + dir.getCanonicalPath());
                return;
            }

            String logFilePattern = LOG_FILE_PATTERN;
            if (_processFile != null && _processFile.trim().length() > 0) {
                logFilePattern = _processFile;
            }
            
            File[] files = dir.listFiles(new FileFilterPattern(logFilePattern));

            if (files.length > 0) {
                try {
                    Class.forName(_dbDriverName);
                    conn = DriverManager.getConnection(_dbUrl, _dbUser, _dbPassword);
                    saveAutoCommit = conn.getAutoCommit();
                    conn.setAutoCommit(false);
                    
                    String dbSchemaStr = (_dbSchema == null || _dbSchema.trim().length() == 0) ? "" : _dbSchema + ".";
                    String deleteQuery = "DELETE " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY";
                    
                    Statement stmt = conn.createStatement();
                    stmt.execute(deleteQuery);
                    conn.commit();
                    stmt.close();
                    
                    List<LogItem> result;
                    for (File file : files) {
                        System.out.println("Parsing file << " + file.getName() + " >>");

                        Map<Token.Type,List<Pair<String,String>>> tokenizedData = getTokenizedData(file);
                        result = new ArrayList<LogItem>(getLogItems(tokenizedData));
                        Collections.sort(result, LOG_ITEM_COMPARE);

                        String insertQuery = "INSERT INTO " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY" +
                                            " (" +
                                            "REPORT_USER_ACTIVITY_ID," +
                                            "TOKEN_TYPE," +
                                            "STORE_ID," +
                                            "STORE_NAME," +
                                            "SESSION_ID," +
                                            "USER_NAME," +
                                            "ACTION_CLASS," +
                                            "ACTION," +
                                            "HTTP_START_TIME," +
                                            "ACTION_START_TIME," +
                                            "HTTP_END_TIME," +
                                            "ACTION_END_TIME," +
                                            "ACTION_RESULT," +
                                            "HTTP_RESULT," +
                                            "ACTION_DURATION," +
                                            "HTTP_DURATION," +
                                            "REFERER," +
                                            "PARAMS," +
                                            "FINISH_FILE," +
                                            "REQUEST_ID," +
                                            "SERVER_NAME," +
                                            "ADD_DATE," +
                                            "ADD_BY," +
                                            "MOD_DATE," +
                                            "MOD_BY" +
                                            ") " +
                                            "VALUES (" +
                                            dbSchemaStr + "RPT_WORK_USER_ACTIVITY_SEQ.NEXTVAL," +
                                            "?,NULL,NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'logInfo',SYSDATE,'logInfo')";

                        pstmt = conn.prepareStatement(insertQuery);
                        for (LogItem logItem : result) {
                            pstmt.setString(1, logItem.getTokenType());                             // TOKEN_TYPE
                            pstmt.setString(2, logItem.getSessionId());                             // SESSION_ID
                            pstmt.setString(3, logItem.getUserName());                              // USER_NAME
                            pstmt.setString(4, logItem.getClassName());                             // ACTION_CLASS
                            pstmt.setString(5, logItem.getAction());                                // ACTION
                            pstmt.setTimestamp(6, toSQLTimestamp(logItem.getHttpStartedAt()));      // HTTP_START_TIME
                            pstmt.setTimestamp(7, toSQLTimestamp(logItem.getActionStartedAt()));    // ACTION_START_TIME
                            pstmt.setTimestamp(8, toSQLTimestamp(logItem.getHttpEndedAt()));        // HTTP_END_TIME
                            pstmt.setTimestamp(9, toSQLTimestamp(logItem.getActionEndedAt()));      // ACTION_END_TIME
                            pstmt.setString(10, logItem.getActionResult());                         // ACTION_RESULT
                            pstmt.setString(11, logItem.getHttpResult());                           // HTTP_RESULT
                            pstmt.setBigDecimal(12, logItem.getActionDuration());                   // ACTION_DURATION
                            pstmt.setBigDecimal(13, logItem.getHttpDuration());                     // HTTP_DURATION
                            pstmt.setString(14, logItem.getReferer());                              // REFERER
                            pstmt.setString(15, logItem.getParams());                               // PARAMS
                            pstmt.setString(16, logItem.getEndFile());                              // FINISH_FILE
                            pstmt.setString(17, logItem.getRequestId());                            // REQUEST_ID
                            pstmt.setString(18, _serverName);                                       // SERVER_NAME

                            pstmt.executeUpdate();
                            conn.commit();
                        }
                    }
                    renameFiles(files);
                    System.out.println(files.length + " files processed");
                } catch (ClassNotFoundException e) {
                    System.err.println("Error. Could not find the database driver: " + _dbDriverName);
                } catch (SQLException e ) {
                    if (conn != null) {
                        try {
                            System.err.println("SQL Error: " + e.getMessage());
                            System.err.println("[ProcessUserActivityLogInfo.process()] Insert transaction is being rolled back");
                            e.printStackTrace();
                            conn.rollback();
                        } catch(SQLException ex) {}
                    }
                } finally {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        } catch(SQLException e) {}
                    }
                    if (conn != null) {
                        conn.setAutoCommit(saveAutoCommit);
                        try {
                            conn.close();
                        } catch(SQLException e) {}
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateDB() throws Exception {
    	Connection conn = null;
        boolean saveAutoCommit = true;
        Statement stmt = null;
        try {
            Class.forName(_dbDriverName);
            conn = DriverManager.getConnection(_dbUrl, _dbUser, _dbPassword);
            saveAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            String dbSchemaStr = (_dbSchema == null || _dbSchema.trim().length() == 0) ? "" : _dbSchema + ".";
            String insertNew = "INSERT INTO " + dbSchemaStr + "RPT_MAIN_USER_ACTIVITY " +
                                "(report_user_activity_id," +
                                " session_id," +
                                " request_id," +
                                " add_date," +
                                " mod_date) " +

                                "SELECT " + dbSchemaStr + "RPT_MAIN_USER_ACTIVITY_SEQ.NEXTVAL," +
                                "t.session_id," +
                                "t.request_id," +
                                "SYSDATE," +
                                "SYSDATE " +
                                "FROM (SELECT DISTINCT session_id, request_id" +
                                     " FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY WHERE (session_id, request_id)" + 
                                     " NOT IN (SELECT session_id, request_id FROM " + dbSchemaStr + "RPT_MAIN_USER_ACTIVITY)) t";
            stmt = conn.createStatement();
            stmt.executeUpdate(insertNew);
            conn.commit();

            String updateHttpStart = "UPDATE " + dbSchemaStr + "RPT_MAIN_USER_ACTIVITY mt " +
                                     "SET " +
                                     "(HTTP_START_TIME," +
                                     "REFERER," +
                                     "USER_NAME," +
                                     "FINISH_FILE," +
                                     "SERVER_NAME," +
                                     "ADD_BY," +
                                     "MOD_BY) = " +

                                     "(SELECT wt.HTTP_START_TIME," +
                                     "wt.REFERER," +
                                     "wt.USER_NAME," +
                                     "wt.FINISH_FILE," +
                                     "wt.SERVER_NAME," +
                                     "wt.ADD_BY," +
                                     "wt.MOD_BY " +
                                     "FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                     "WHERE mt.session_id = wt.session_id AND " +
                                     "mt.request_id = wt.request_id AND " +
                                     "wt.token_type = '@@##@@S') " + 
                                     "WHERE (SELECT COUNT(*) FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                     "WHERE mt.session_id = wt.session_id AND " +
                                     "mt.request_id = wt.request_id AND " + 
                                     "wt.token_type = '@@##@@S') > 0";
            stmt.executeUpdate(updateHttpStart);
            conn.commit();

            String updateActionStart = "UPDATE " + dbSchemaStr + "RPT_MAIN_USER_ACTIVITY mt " +
                                       "SET " +
                                       "(ACTION_CLASS," +
                                       "ACTION," +
                                       "ACTION_START_TIME," +
                                       "PARAMS," +
                                       "USER_NAME," +
                                       "FINISH_FILE," +
                                       "SERVER_NAME," +
                                       "ADD_BY," +
                                       "MOD_BY) = " +

                                       "(SELECT wt.ACTION_CLASS," +
                                       "wt.ACTION," +
                                       "wt.ACTION_START_TIME," +
                                       "wt.PARAMS," +
                                       "wt.USER_NAME," +
                                       "wt.FINISH_FILE," +
                                       "wt.SERVER_NAME," +
                                       "wt.ADD_BY," +
                                       "wt.MOD_BY " +
                                       "FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                       "WHERE mt.session_id = wt.session_id AND " +
                                       "mt.request_id = wt.request_id AND " +
                                       "wt.token_type = '@@@@S') " +
                                       "WHERE (SELECT COUNT(*) FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                       "WHERE mt.session_id = wt.session_id AND " +
                                       "mt.request_id = wt.request_id AND " +
                                       "wt.token_type = '@@@@S') > 0";
            stmt.executeUpdate(updateActionStart);
            conn.commit();
            
            String updateActionEnd = "UPDATE " + dbSchemaStr + "RPT_MAIN_USER_ACTIVITY mt " +
                                     "SET " +
                                     "(ACTION_CLASS," +
                                     "ACTION," +
                                     "ACTION_START_TIME," +
                                     "ACTION_END_TIME," +
                                     "ACTION_RESULT," +
                                     "ACTION_DURATION," +
                                     "PARAMS," +
                                     "USER_NAME," +
                                     "FINISH_FILE," +
                                     "SERVER_NAME," +
                                     "ADD_BY," +
                                     "MOD_BY) = " +

                                     "(SELECT wt.ACTION_CLASS," +
                                     "wt.ACTION," +
                                     "wt.ACTION_START_TIME," +
                                     "wt.ACTION_END_TIME," +
                                     "wt.ACTION_RESULT," +
                                     "wt.ACTION_DURATION," +
                                     "wt.PARAMS," +
                                     "wt.USER_NAME," +
                                     "wt.FINISH_FILE," +
                                     "wt.SERVER_NAME," +
                                     "wt.ADD_BY," +
                                     "wt.MOD_BY " +
                                     "FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                     "WHERE mt.session_id = wt.session_id AND " +
                                     "mt.request_id = wt.request_id AND " +
                                     "wt.token_type = '@@@@E') " +
                                     "WHERE (SELECT COUNT(*) FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                     "WHERE mt.session_id = wt.session_id AND " +
                                     "mt.request_id = wt.request_id AND " +
                                     "wt.token_type = '@@@@E') > 0";
            stmt.executeUpdate(updateActionEnd);
            conn.commit();
            
            String updateHttpEnd = "UPDATE " + dbSchemaStr + "RPT_MAIN_USER_ACTIVITY mt " +
                                   "SET " +
                                   "(HTTP_START_TIME," +
                                   "HTTP_END_TIME," +
                                   "HTTP_RESULT," +
                                   "HTTP_DURATION," +
                                   "REFERER," +
                                   "USER_NAME," +
                                   "FINISH_FILE," +
                                   "SERVER_NAME," +
                                   "ADD_BY," +
                                   "MOD_BY) = " +

                                   "(SELECT wt.HTTP_START_TIME," +
                                   "wt.HTTP_END_TIME," +
                                   "wt.HTTP_RESULT," +
                                   "wt.HTTP_DURATION," +
                                   "wt.REFERER," +
                                   "wt.USER_NAME," +
                                   "wt.FINISH_FILE," +
                                   "wt.SERVER_NAME," +
                                   "wt.ADD_BY," +
                                   "wt.MOD_BY " +
                                   "FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                   "WHERE mt.session_id = wt.session_id AND " +
                                   "mt.request_id = wt.request_id AND " +
                                   "wt.token_type = '@@##@@E') " +
                                   "WHERE (SELECT COUNT(*) FROM " + dbSchemaStr + "RPT_WORK_USER_ACTIVITY wt " +
                                   "WHERE mt.session_id = wt.session_id AND " +
                                   "mt.request_id = wt.request_id AND " +
                                   "wt.token_type = '@@##@@E') > 0";
            stmt.executeUpdate(updateHttpEnd);
            conn.commit();
        } catch (ClassNotFoundException e) {
            System.err.println("Error. Could not find the database driver: " + _dbDriverName);
        } catch (SQLException e ) {
            if (conn != null) {
                try {
                    System.err.println("SQL Error: " + e.getMessage());
                    System.err.println("[ProcessUserActivityLogInfo.process()] Transaction is being rolled back");
                    e.printStackTrace();
                    conn.rollback();
                } catch(SQLException ex) {}
            }
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch(SQLException e) {}
            }
            if (conn != null) {
                conn.setAutoCommit(saveAutoCommit);
                try {
                    conn.close();
                } catch(SQLException e) {}
            }
        }
    }
    
    private void renameFiles(File[] files) {
        for (File file : files){
            File newFile = new File(file.getParent(), "processed_" + file.getName());
            if (file.renameTo(newFile)) {
                System.out.println("File " + file.getName() + " has been renamed to " + newFile.getName());
            } else {
                System.err.println("Error renaming file - " + file.getName());
            }
        }
    }
    
    public static Map<Token.Type,List<Pair<String,String>>> getTokenizedData(File file) throws Exception {
    	InputStream in = null;
        
        try {
            TokenReader tokenReader = new TokenReader();
            tokenReader.addToken(new Token(Token.Type.REQUEST_START,
                                           Token.REQUEST_START_PATTERN,
                                           Token.REQUEST_COMPLETE_PATTERN));
            tokenReader.addToken(new Token(Token.Type.REQUEST_END,
                                           Token.REQUEST_END_PATTERN,
                                           Token.REQUEST_COMPLETE_PATTERN));
            tokenReader.addToken(new Token(Token.Type.ACTION_START,
                                           Token.ACTION_START_PATTERN,
                                           Token.ACTION_COMPLETE_PATTERN));
            tokenReader.addToken(new Token(Token.Type.ACTION_END,
                                           Token.ACTION_END_PATTERN,
                                           Token.ACTION_COMPLETE_PATTERN));
            
            if (file.getAbsolutePath().endsWith(".gz") || file.getAbsolutePath().endsWith(".GZ")) {
                in = new GZIPInputStream(new FileInputStream(file));
            } else {
                in = new FileInputStream(file);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                tokenReader.processLine(line, file.getAbsolutePath());
            }
            return tokenReader.getParsedData();
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {}
            }
        }
    }
    
    private static Collection<LogItem> getLogItems(Map<Token.Type,List<Pair<String,String>>> tokenizedData) throws Exception {
    	List<LogItem> tokens = new ArrayList<LogItem>();

        parseTokenLine(tokenizedData.get(Token.Type.REQUEST_START), tokens, Token.Type.REQUEST_START);
        parseTokenLine(tokenizedData.get(Token.Type.ACTION_START), tokens, Token.Type.ACTION_START);
        parseTokenLine(tokenizedData.get(Token.Type.ACTION_END), tokens, Token.Type.ACTION_END);
        parseTokenLine(tokenizedData.get(Token.Type.REQUEST_END), tokens, Token.Type.REQUEST_END);

        return tokens;
    }
    
    private static void parseTokenLine(List<Pair<String, String>> tokenizedData,
                                       List<LogItem> tokens,
                                       Token.Type tokenType) {
        if (tokenizedData != null) {
            LogItem logItem;
            for (Pair<String, String> tokenInfo : tokenizedData) {
                String infoString = tokenInfo.getObject1();
                String filePath = tokenInfo.getObject2();

                String userName = Token.parse(infoString, Token.USER_REGEX);
                if (userName == null) {
                    continue;
                }
                String requestId = Token.parse(infoString, Token.REQUEST_ID_REGEX);
                if (requestId == null) {
                    continue;
                }
                String sessionId = Token.parse(infoString, Token.SESSION_ID_REGEX);
                if (sessionId == null) {
                    continue;
                }

                logItem = new LogItem();
                logItem.setEndFile(filePath);
                logItem.setUserName(userName);
                logItem.setRequestId(requestId);
                logItem.setSessionId(sessionId);

                switch (tokenType) {
                    case REQUEST_START:
                        logItem.setTokenType(Token.REQUEST_START_PATTERN);
                        logItem.setHttpStartedAtS(Token.parse(infoString, Token.STARTED_AT_REGEX));
                        logItem.setHttpStartedAt(getDate(logItem.getHttpStartedAtS()));
                        logItem.setReferer(Token.parse(infoString, Token.REFERER_REGEX));
                        break;
                    case ACTION_START:
                        logItem.setTokenType(Token.ACTION_START_PATTERN);
                        logItem.setClassName(Token.parse(infoString, Token.CLASS_REGEX));
                        logItem.setAction(Token.parse(infoString, Token.ACTION_REGEX));
                        logItem.setActionStartedAtS(Token.parse(infoString, Token.STARTED_AT_REGEX));
                        logItem.setActionStartedAt(getDate(logItem.getActionStartedAtS()));
                        logItem.setParams(getLimitString(Token.parse(infoString, Token.PARAMS_REGEX), MAX_PARAMS_LEN));
                        break;
                    case ACTION_END:
                        logItem.setTokenType(Token.ACTION_END_PATTERN);
                        logItem.setClassName(Token.parse(infoString, Token.CLASS_REGEX));
                        logItem.setAction(Token.parse(infoString, Token.ACTION_REGEX));
                        logItem.setActionStartedAtS(Token.parse(infoString, Token.STARTED_AT_REGEX));
                        logItem.setActionStartedAt(getDate(logItem.getActionStartedAtS()));
                        logItem.setActionEndedAtS(Token.parse(infoString, Token.ENDET_AT_REGEX));
                        logItem.setActionEndedAt(getDate(logItem.getActionEndedAtS()));
                        logItem.setActionResult(Token.parse(infoString, Token.RESULT_REGEX));
                        logItem.setActionDuration(getBigDecimal(Token.parse(infoString, Token.DURATION_REGEX)));
                        logItem.setParams(getLimitString(Token.parse(infoString, Token.PARAMS_REGEX), MAX_PARAMS_LEN));
                        break;
                    case REQUEST_END:
                        logItem.setTokenType(Token.REQUEST_END_PATTERN);
                        logItem.setHttpStartedAtS(Token.parse(infoString, Token.STARTED_AT_REGEX));
                        logItem.setHttpStartedAt(getDate(logItem.getHttpStartedAtS()));
                        logItem.setReferer(Token.parse(infoString, Token.REFERER_REGEX));
                        logItem.setHttpEndedAtS(Token.parse(infoString, Token.ENDET_AT_REGEX));
                        logItem.setHttpEndedAt(getDate(logItem.getHttpEndedAtS()));
                        logItem.setHttpResult(Token.parse(infoString, Token.RESULT_REGEX));
                        logItem.setHttpDuration(getBigDecimal(Token.parse(infoString, Token.DURATION_REGEX)));
                        break;
                    default: break;
                }
                tokens.add(logItem);
            }
        }
    }
    
    private static BigDecimal getBigDecimal(String source) {
        try {
            return new BigDecimal(source);
        } catch (Exception e) {
            return null;
        }
    }    

    private static Date getDate(String source) {
    	return getDate(source, DEFAULT_SDF);
    }

    private static Date getDate(String source, SimpleDateFormat sdf) {
    	try {
            return (sdf == null) ? null : sdf.parse(source);
    	} catch (Exception e) {
            return null;
    	}
    }

    private static String getLimitString(String source, int maxLength) {
    	if (source != null && source.length() > maxLength) {
            return source.substring(0, maxLength);
    	}
    	return source;
    }

    private static final Comparator<LogItem> LOG_ITEM_COMPARE = new Comparator<LogItem>() {
        public int compare(LogItem o1, LogItem o2) {
            String httpStartedAtS1 = o1.getHttpStartedAtS();
            String httpStartedAtS2 = o2.getHttpStartedAtS();
            httpStartedAtS1 = (httpStartedAtS1 != null) ? httpStartedAtS1 : "";
            httpStartedAtS2 = (httpStartedAtS2 != null) ? httpStartedAtS2 : "";

            return httpStartedAtS2.compareTo(httpStartedAtS1);
        }
    };
    
    public static java.sql.Timestamp toSQLTimestamp (Date value) {
	Timestamp result = null;
	if (null != value) {
	    result = new Timestamp(value.getTime());
	}
	return result;
    }

    private static class FileFilterPattern implements FileFilter {
    	String dirFilterName;

    	public FileFilterPattern(String filterString) {
            dirFilterName = filterString;
    	}
        public boolean accept(File pathName) {        	
            if (pathName.isFile() && pathName.getName().startsWith(dirFilterName)) {
                return true;
            }
            return false;
        }
    }
}
