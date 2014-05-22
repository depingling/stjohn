package com.cleanwise.service.apps;
/*
ant -f util.xml compileUserActivityInfo

java -classpath \espendwise\xapp\webapp\stjohn\kit\tools;\espendwise\xapp\webapp\stjohn\tools\bzip2\bzip2.jar;\espendwise\xapp\webapp\stjohn\installation/jdbc/lib/ojdbc14.jar com.cleanwise.useractivity.ProcessUserActivityLogInfo parselog oracle.jdbc.driver.OracleDriver jdbc:oracle:thin:@166.78.67.76:1521:cwdev orca_migration orca_migration \tmp server.log.2013-03-11-16.bz2
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.apache.tools.bzip2.CBZip2InputStream;

public class ProcessUserActivityLogInfo {
	private static final Logger log = Logger.getLogger(ProcessUserActivityLogInfo.class);
    private final static int MAX_PARAMS_LEN = 3500;

	public final static String REQUEST_START_PATTERN = "@@##@@S";
	public final static String REQUEST_END_PATTERN = "@@##@@E";
	public final static String REQUEST_COMPLETE_PATTERN = "@@##@@";
	public final static String ACTION_START_PATTERN = "@@@@S";
	public final static String ACTION_END_PATTERN = "@@@@E";
	public final static String ACTION_COMPLETE_PATTERN = "@@@@";
	
	public final static String LINE_SEPARATOR = System.getProperty("line.separator");
	public final static String USER_REGEX = "User: <(.*?)>";
	public final static String REQUEST_ID_REGEX = "RequestId: <(.*?)>";
	public final static String SESSION_ID_REGEX = "Session id : <(.*?)>";
	public final static String STARTED_AT_REGEX = "Started at: <(.*?)>";
	public final static String REFERER_REGEX = "Referer: <(.*?)>";
	public final static String CLASS_REGEX = "Class: <(.*?)>";
	public final static String ACTION_REGEX = "Action: <(.*?)>";
	public final static String ENDET_AT_REGEX = "Ended at: <(.*?)>";
	public final static String RESULT_REGEX = "Result: <(.*?)>";
	public final static String DURATION_REGEX = "Duration: <(.*?)>";
	public final static String PARAMS_REGEX = "Params: \\{(.*?)\\}";
	
	public static enum Type {
		REQUEST_START,
		REQUEST_END,
		ACTION_START,
		ACTION_END
	}

    private String _serverName;
    private String _dbDriverName;
    private String _dbUrl;
    private String _dbUser;
    private String _dbPassword;
    private String _dbSchema;
    private String _logDir;
    private String _processFile;
	private String _command;
    
    private final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSSS");

    private static String LOG_FILE_PATTERN = "server.log.";
    
    private static String COMMAND_PARSELOG = "parselog";
    private static String COMMAND_UPDATEDB = "updatedb";

    public ProcessUserActivityLogInfo () {
	}

    private void init() {
		_command = System.getProperty("command");
		if(_command==null) _command = COMMAND_PARSELOG; 
        _dbDriverName = System.getProperty("infoDbDriverName");
		if(_dbDriverName==null) _dbDriverName = "oracle.jdbc.driver.OracleDriver"; //Oracle is default choice
        _dbUrl        = System.getProperty("infoDbUrl");
        _dbUser       = System.getProperty("infoDbUser");
        _dbPassword   = System.getProperty("infoDbPassword");
        _logDir       = System.getProperty("jbossLogDir");
        _processFile  = System.getProperty("processFile");
        _dbSchema     = System.getProperty("infoDbSchema");

        try {
            _serverName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            _serverName = System.getenv("HOSTNAME");
        }
    }

    public static void main(String[] args) {
        ProcessUserActivityLogInfo logInfo = new ProcessUserActivityLogInfo();
		logInfo.init();
		logInfo.process();
	
	}
	
	public void process() {
        try {
            if (COMMAND_PARSELOG.equalsIgnoreCase(_command)) {
                File[] files = processFiles();
				if(files.length>0) {
					updateDB();
					renameFiles(files);
				}
				
            } else if (COMMAND_UPDATEDB.equalsIgnoreCase(_command)) {
                updateDB();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("End of ProcessUserActivityLogInfo. Date: " + new Date());
    }

    public File[] processFiles() throws Exception {
    	Connection conn = null;
        boolean saveAutoCommit = true;
        PreparedStatement pstmt = null;

		File dir = new File(_logDir);
		if (!dir.exists() || !dir.isDirectory()) {
			System.err.println("Cannot find directory - " + dir.getCanonicalPath());
			return new File[0];
		}

		String logFilePattern = LOG_FILE_PATTERN;
		if (_processFile != null && _processFile.trim().length() > 0) {
			logFilePattern = _processFile;
		}
		
		
		FileFilterPattern ffp = new FileFilterPattern(logFilePattern);
		log.info("ffp: "+ffp);
		File[] files = dir.listFiles(ffp);
		log.info("logFilePattern: "+logFilePattern);
		log.info("File names start wilth :"+ logFilePattern+". Found "+files.length+" files");
		
		if (files.length > 0) {
			Class clazz = Class.forName(_dbDriverName);
			conn = DriverManager.getConnection(_dbUrl, _dbUser, _dbPassword);
			saveAutoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			
			String dbSchemaStr = (_dbSchema == null || _dbSchema.trim().length() == 0) ? "" : _dbSchema + ".";
			String deleteQuery = 
			   "DELETE " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK where SERVER_NAME = '"+_serverName+"'" ;
			log.info("deleteQuery: "+deleteQuery);
			
			Statement stmt = conn.createStatement();
			stmt.execute(deleteQuery);
			conn.commit();
			stmt.close();
			
			List<LogItem> result;
			String insertQuery = "INSERT INTO " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK" +
								" (" +
								"USER_ACTIVITY_WRK_ID," +
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
								dbSchemaStr + "RPT_USER_ACTIVITY_WRK_SEQ.NEXTVAL," +
								"?,NULL,NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'logInfo',SYSDATE,'logInfo')";
			pstmt = conn.prepareStatement(insertQuery);

			for (File file : files) {
				int recQty = 0;
				log.info("Parsing file:  " + file.getName());

				Map<Type,List<Pair<String,String>>> tokenizedData = getTokenizedData(file);
				result = new ArrayList<LogItem>(getLogItems(tokenizedData));
				Collections.sort(result, LOG_ITEM_COMPARE);

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
					recQty++;
					if(recQty%100 == 0) log.info(recQty+" records inserted");
				}
				log.info("File processed:  " + file.getName()+" Records made: "+recQty);
			}
			pstmt.close();
			conn.commit();

			//Delete duplicated repords
			String deleteDuplRecords = 
				"delete from RPT_USER_ACTIVITY_WRK where user_activity_wrk_id in ( "+
				" select min(USER_ACTIVITY_WRK_ID) " +
				" from RPT_USER_ACTIVITY_WRK "+
				" where server_name = '"+_serverName+"'" +
				" group by session_id, request_id, token_type"+
				" having count(*)>1)";
				          			
			log.info("deleteDuplRecords: "+deleteDuplRecords);
			
			Statement stmtDelDupl = conn.createStatement();
			long delRecQty =0;
			do {
				delRecQty = stmtDelDupl.executeUpdate(deleteDuplRecords);
				log.info("Delete duplicated records: "+delRecQty+" records deleted");
			} while (delRecQty>0);
			
			stmt.close();
			conn.commit();
			log.info(files.length + " files processed");
		}
		return files;
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
			/*
            String insertNew = "INSERT INTO " + dbSchemaStr + "RPT_USER_ACTIVITY " +
                                "(user_activity_id," +
                                " session_id," +
                                " request_id," +
                                " add_date," +
                                " mod_date) " +

                                "SELECT " + dbSchemaStr + "RPT_USER_ACTIVITY_SEQ.NEXTVAL," +
                                "t.session_id," +
                                "t.request_id," +
                                "SYSDATE," +
                                "SYSDATE " +
                                "FROM (SELECT DISTINCT session_id, request_id" +
                                     " FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK WHERE SERVER_NAME = '"+_serverName+"'"+
									 " AND (session_id, request_id)" + 
                                     " NOT IN (SELECT session_id, request_id FROM " + dbSchemaStr + "RPT_USER_ACTIVITY)) t";
									 */
            String insertNew = 									 
					"INSERT INTO " + dbSchemaStr + "RPT_USER_ACTIVITY " +
					"  (user_activity_id, session_id, request_id, server_name, add_date, mod_date) " +
					" select RPT_USER_ACTIVITY_SEQ.NEXTVAL, session_id,request_id, server_name, sysdate, sysdate " +
					" from (" +
					" SELECT  distinct w.session_id, w.request_id, w.server_name, t.request_id t_id" +
					" FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK w, " + dbSchemaStr + "RPT_USER_ACTIVITY t" +
					" WHERE w.SERVER_NAME = '"+_serverName+"' " +
					" AND w.session_id = t.session_id(+)" +
					" and w.request_id = t.request_id(+)" +
					") where t_id is null";
			
            stmt = conn.createStatement();
			log.info("Insert new records sql: "+insertNew);
            int nn = stmt.executeUpdate(insertNew);
			log.info(nn+ " Records inserted");

            String updateHttpStart = "UPDATE " + dbSchemaStr + "RPT_USER_ACTIVITY mt " +
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
                                     "FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK wt " +
                                     "WHERE wt.SERVER_NAME = '"+_serverName+"' AND "+
									 "mt.session_id = wt.session_id AND " +
                                     "mt.request_id = wt.request_id AND " +
                                     "wt.token_type = '@@##@@S') " + 
  								     
									   " WHERE (session_id, request_id)" + 
									   " IN (SELECT session_id, request_id FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK "+
									   " WHERE SERVER_NAME = '"+_serverName+"')";
			log.info("Update Http Start records sql: "+updateHttpStart);
            nn = stmt.executeUpdate(updateHttpStart);
			log.info(nn+ " Records updated");

            String updateActionStart = "UPDATE " + dbSchemaStr + "RPT_USER_ACTIVITY mt " +
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
                                       "FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK wt " +
                                       "WHERE wt.SERVER_NAME = '"+_serverName+"' AND "+
									   "mt.session_id = wt.session_id AND " +
                                       "mt.request_id = wt.request_id AND " +
                                       "wt.token_type = '@@@@S') " +
  								     
									   " WHERE (session_id, request_id)" + 
									   " IN (SELECT session_id, request_id FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK "+
									   " WHERE SERVER_NAME = '"+_serverName+"')";

 		    log.info("Update Action Start records sql: "+updateActionStart);
            nn = stmt.executeUpdate(updateActionStart);
			log.info(nn+ " Records updated");
            
            String updateActionEnd = "UPDATE " + dbSchemaStr + "RPT_USER_ACTIVITY mt " +
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
                                     "FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK wt " +
                                     "WHERE wt.SERVER_NAME = '"+_serverName+"' AND "+
									 "mt.session_id = wt.session_id AND " +
                                     "mt.request_id = wt.request_id AND " +
                                     "wt.token_type = '@@@@E') " +
  								     
									 " WHERE (session_id, request_id)" + 
									   " IN (SELECT session_id, request_id FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK "+
									   " WHERE SERVER_NAME = '"+_serverName+"')";
 		    log.info("Update Action End records sql: "+updateActionEnd);
            nn = stmt.executeUpdate(updateActionEnd);
			log.info(nn+ " Records updated");
            
            String updateHttpEnd = "UPDATE " + dbSchemaStr + "RPT_USER_ACTIVITY mt " +
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
                                   "FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK wt " +
                                   "WHERE wt.SERVER_NAME = '"+_serverName+"' AND "+
								   "mt.session_id = wt.session_id AND " +
                                   "mt.request_id = wt.request_id AND " +
                                   "wt.token_type = '@@##@@E') " +
								   
								   " WHERE (session_id, request_id)" + 
									   " IN (SELECT session_id, request_id FROM " + dbSchemaStr + "RPT_USER_ACTIVITY_WRK "+
									   " WHERE SERVER_NAME = '"+_serverName+"')";

		    log.info("Update Http End records sql: "+updateHttpEnd);
            nn = stmt.executeUpdate(updateHttpEnd);
			log.info(nn+ " Records updated");
			stmt.close();
            conn.commit();
        } catch (ClassNotFoundException e) {
            System.err.println("Error. Could not find the database driver: " + _dbDriverName);
        } catch (SQLException e ) {
			e.printStackTrace();
			throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(saveAutoCommit);
                conn.close();
            }
        }
    }
    
    private void renameFiles(File[] files) {
        for (File file : files){
            File newFile = new File(file.getParent(), "processed_" + file.getName());
            if (file.renameTo(newFile)) {
                log.info("File " + file.getName() + " has been renamed to " + newFile.getName());
            } else {
                System.err.println("Error renaming file - " + file.getName());
            }
        }
    }
    
    private Map<Type,List<Pair<String,String>>> getTokenizedData(File file) throws Exception {
    	InputStream in = null;
        
        try {
            TokenReader tokenReader = new TokenReader();
            tokenReader.addToken(new Token(Type.REQUEST_START,
                                           REQUEST_START_PATTERN,
                                           REQUEST_COMPLETE_PATTERN));
            tokenReader.addToken(new Token(Type.REQUEST_END,
                                           REQUEST_END_PATTERN,
                                           REQUEST_COMPLETE_PATTERN));
            tokenReader.addToken(new Token(Type.ACTION_START,
                                           ACTION_START_PATTERN,
                                           ACTION_COMPLETE_PATTERN));
            tokenReader.addToken(new Token(Type.ACTION_END,
                                           ACTION_END_PATTERN,
                                           ACTION_COMPLETE_PATTERN));
            
            if (file.getAbsolutePath().endsWith(".gz") || file.getAbsolutePath().endsWith(".GZ")) {
                in = new GZIPInputStream(new FileInputStream(file));
            } else if (file.getAbsolutePath().endsWith(".bz2") || file.getAbsolutePath().endsWith(".BZ2")) {
				InputStream fis = new FileInputStream(file);
				fis.read();
				fis.read();
				in = new CBZip2InputStream(fis);
            } else {
                in = new FileInputStream(file);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
			int lineQty = 0;
            while ((line = reader.readLine()) != null) {
                tokenReader.processLine(line, file.getName());
				lineQty++;
				if(lineQty%1000==0) log.info(lineQty+" lines parsed");
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
    
    private Collection<LogItem> getLogItems(Map<Type,List<Pair<String,String>>> tokenizedData) throws Exception {
    	List<LogItem> tokens = new ArrayList<LogItem>();

        parseTokenLine(tokenizedData.get(Type.REQUEST_START), tokens, Type.REQUEST_START);
        parseTokenLine(tokenizedData.get(Type.ACTION_START), tokens, Type.ACTION_START);
        parseTokenLine(tokenizedData.get(Type.ACTION_END), tokens, Type.ACTION_END);
        parseTokenLine(tokenizedData.get(Type.REQUEST_END), tokens, Type.REQUEST_END);

        return tokens;
    }
    
    private void parseTokenLine(List<Pair<String, String>> tokenizedData,
                                       List<LogItem> tokens,
                                       Type tokenType) {
        if (tokenizedData != null) {
            LogItem logItem;
            for (Pair<String, String> tokenInfo : tokenizedData) {
                String infoString = tokenInfo.getObject1();
                String filePath = tokenInfo.getObject2();

                String userName = parseLine(infoString, USER_REGEX);
                if (userName == null) {
                    continue;
                }
                String requestId = parseLine(infoString, REQUEST_ID_REGEX);
                if (requestId == null) {
                    continue;
                }
                String sessionId = parseLine(infoString, SESSION_ID_REGEX);
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
                        logItem.setTokenType(REQUEST_START_PATTERN);
                        logItem.setHttpStartedAtS(parseLine(infoString, STARTED_AT_REGEX));
                        logItem.setHttpStartedAt(getDate(logItem.getHttpStartedAtS()));
                        logItem.setReferer(parseLine(infoString, REFERER_REGEX));
                        break;
                    case ACTION_START:
                        logItem.setTokenType(ACTION_START_PATTERN);
                        logItem.setClassName(parseLine(infoString, CLASS_REGEX));
                        logItem.setAction(parseLine(infoString, ACTION_REGEX));
                        logItem.setActionStartedAtS(parseLine(infoString, STARTED_AT_REGEX));
                        logItem.setActionStartedAt(getDate(logItem.getActionStartedAtS()));
                        logItem.setParams(getLimitString(parseLine(infoString, PARAMS_REGEX), MAX_PARAMS_LEN));
                        break;
                    case ACTION_END:
                        logItem.setTokenType(ACTION_END_PATTERN);
                        logItem.setClassName(parseLine(infoString, CLASS_REGEX));
                        logItem.setAction(parseLine(infoString, ACTION_REGEX));
                        logItem.setActionStartedAtS(parseLine(infoString, STARTED_AT_REGEX));
                        logItem.setActionStartedAt(getDate(logItem.getActionStartedAtS()));
                        logItem.setActionEndedAtS(parseLine(infoString, ENDET_AT_REGEX));
                        logItem.setActionEndedAt(getDate(logItem.getActionEndedAtS()));
                        logItem.setActionResult(parseLine(infoString, RESULT_REGEX));
                        logItem.setActionDuration(getBigDecimal(parseLine(infoString, DURATION_REGEX)));
                        logItem.setParams(getLimitString(parseLine(infoString, PARAMS_REGEX), MAX_PARAMS_LEN));
                        break;
                    case REQUEST_END:
                        logItem.setTokenType(REQUEST_END_PATTERN);
                        logItem.setHttpStartedAtS(parseLine(infoString, STARTED_AT_REGEX));
                        logItem.setHttpStartedAt(getDate(logItem.getHttpStartedAtS()));
                        logItem.setReferer(parseLine(infoString, REFERER_REGEX));
                        logItem.setHttpEndedAtS(parseLine(infoString, ENDET_AT_REGEX));
                        logItem.setHttpEndedAt(getDate(logItem.getHttpEndedAtS()));
                        logItem.setHttpResult(parseLine(infoString, RESULT_REGEX));
                        logItem.setHttpDuration(getBigDecimal(parseLine(infoString, DURATION_REGEX)));
                        break;
                    default: break;
                }
                tokens.add(logItem);
            }
        }
    }
	public String parseLine(String source, String pattern) {
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
		try {
			Matcher m = p.matcher(source);
			if (m.find()) {
				String result = m.group(1);
				return ("null".equals(result)) ? null : result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

//********************************************************************	
	public class TokenReader {
		private List<Token> _tokens;
		private Map<Type,List<Pair<String,String>>> _parsedData;

		public TokenReader(List<Token> tokens) {
			_tokens = tokens;
			_parsedData = new Hashtable<Type, List<Pair<String, String>>>();
		}
		
		public TokenReader() {
			_parsedData = new Hashtable<Type, List<Pair<String, String>>>();
		}

		public void processLine(String line, String filePath) {
			if (_tokens != null) {
				List<Pair<String,String>> parsedLines;
				String userName;
				for (Token token : _tokens) {
					token.processLine(line);
					if (token.isFilled()) {
						userName = token.parseValue(USER_REGEX);
						if (userName != null) {
							parsedLines = _parsedData.get(token.getType());
							if (parsedLines == null) {
								parsedLines = new ArrayList<Pair<String, String>>();
							}
							parsedLines.add(new Pair<String, String>(token.getValue(), filePath));
							_parsedData.put(token.getType(), parsedLines);
						}
						token.clear();
						break;
					}
				}
			}
		}
		
		public void addToken (Token token) {
			if (_tokens == null) {
				_tokens = new ArrayList<Token>();
			}
			_tokens.add(token);
		}

		public Map<Type,List<Pair<String,String>>> getParsedData() {
			return _parsedData;
		}
	}

//********************************************************************	
	public class Token {
		private final Type _type;
		private final String _beginMark;
		private final String _endMark;
		private String _value;
		private boolean _isFilled;

		
		public Token(Type type, String beginMark, String endMark) {
			_type = type;
			_beginMark = beginMark;
			_endMark = endMark;
		}

		public void clear() {
			_value = null;
			_isFilled = false;
		}

		public boolean isFilled() {
			return _isFilled;
		}

		public String getValue() {
			return _value;
		}

		public Type getType() {
			return _type;
		}

		public String processLine(String line) {
			if (line != null && line.length() > 0) {
				int index = line.indexOf(_endMark);
				if (_value == null) {
					index = line.indexOf(_beginMark);
					if (index > -1) {
						int indexStart = index + _beginMark.length();
						int indexEnd = line.indexOf(_endMark, indexStart);
						if (indexEnd > -1) {
							_value = line.substring(indexStart, indexEnd);
							_isFilled = true;
							return line.substring(indexEnd + _endMark.length());
						} else {
							if (indexStart > line.length()){
								_value = LINE_SEPARATOR;
							} else {
								_value = line.substring(indexStart) + LINE_SEPARATOR;
							}
						}
					}
				} else if (index > -1) {
					_value += line.substring(0, index);
					_isFilled = true;
					return line.substring(index + _endMark.length());
				} else {
					_value += line + LINE_SEPARATOR;
				}
			}
			return null;
		}
		
		public String parseValue(String pattern) {
			return parseLine(_value, pattern);
		}
		
	}	
//********************************************************************
	public class LogItem {
		private String _requestId;
		private String _tokenType;
		private String _sessionId;
		private String _userName;
		private String _className;
		private String _endFile;
		private String _action;
		private String _httpStartedAtS;
		private Date   _httpStartedAt;
		private String _actionStartedAtS;
		private Date   _actionStartedAt;
		private String _actionEndedAtS;
		private Date   _actionEndedAt;
		private String _httpEndedAtS;
		private Date   _httpEndedAt;
		private String _actionResult;
		private String _httpResult;
		private BigDecimal _actionDuration;
		private BigDecimal _httpDuration;
		private String _referer;
		private String _params;

		public String getAction() {return _action;}
		public void setAction(String action) {this._action = action;}

		public BigDecimal getActionDuration() {return _actionDuration;}
		public void setActionDuration(BigDecimal actionDuration) {this._actionDuration = actionDuration;}

		public Date getActionEndedAt() {return _actionEndedAt;}
		public void setActionEndedAt(Date actionEndedAt) {this._actionEndedAt = actionEndedAt;}

		public String getActionEndedAtS() {return _actionEndedAtS;}
		public void setActionEndedAtS(String actionEndedAtS) {this._actionEndedAtS = actionEndedAtS;}

		public String getActionResult() {return _actionResult;}
		public void setActionResult(String actionResult) {this._actionResult = actionResult;}

		public Date getActionStartedAt() {return _actionStartedAt;}
		public void setActionStartedAt(Date actionStartedAt) {this._actionStartedAt = actionStartedAt;}

		public String getActionStartedAtS() {return _actionStartedAtS;}
		public void setActionStartedAtS(String actionStartedAtS) {this._actionStartedAtS = actionStartedAtS;}

		public String getClassName() {return _className;}
		public void setClassName(String className) {this._className = className;}

		public String getEndFile() {return _endFile;}
		public void setEndFile(String endFile) {this._endFile = endFile;}

		public BigDecimal getHttpDuration() {return _httpDuration;}
		public void setHttpDuration(BigDecimal httpDuration) {this._httpDuration = httpDuration;}

		public Date getHttpEndedAt() {return _httpEndedAt;}
		public void setHttpEndedAt(Date httpEndedAt) {this._httpEndedAt = httpEndedAt;}

		public String getHttpEndedAtS() {return _httpEndedAtS;}
		public void setHttpEndedAtS(String httpEndedAtS) {this._httpEndedAtS = httpEndedAtS;}

		public String getHttpResult() {return _httpResult;}
		public void setHttpResult(String httpResult) {this._httpResult = httpResult;}

		public Date getHttpStartedAt() {return _httpStartedAt;}
		public void setHttpStartedAt(Date httpStartedAt) {this._httpStartedAt = httpStartedAt;}

		public String getHttpStartedAtS() {return _httpStartedAtS;}
		public void setHttpStartedAtS(String httpStartedAtS) {this._httpStartedAtS = httpStartedAtS;}

		public String getParams() {return _params;}
		public void setParams(String params) {this._params = params;}

		public String getReferer() {return _referer;}
		public void setReferer(String referer) {this._referer = referer;}

		public String getRequestId() {return _requestId;}
		public void setRequestId(String requestId) {this._requestId = requestId;}

		public String getSessionId() {return _sessionId;}
		public void setSessionId(String sessionId) {this._sessionId = sessionId;}

		public String getUserName() {return _userName;}
		public void setUserName(String userName) {this._userName = userName;}

		public String getTokenType() {return _tokenType;}
		public void setTokenType(String tokenType) {this._tokenType = tokenType;}
	}	

//********************************************************************
	public class Pair<O1, O2> implements Serializable {

		public static final String OBJECT2 = "object2";
		public static final String OBJECT1 = "object1";

		private O1 object1;
		private O2 object2;

		public Pair() {}

		public Pair(O1 object1, O2 object2) {
			this.object1 = object1;
			this.object2 = object2;
		}

		public O1 getObject1() {return object1;}
		public void setObject1(O1 object1) {this.object1 = object1;}

		public O2 getObject2() {return object2;}
		public void setObject2(O2 object2) {this.object2 = object2;}
	}
}
