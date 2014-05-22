package com.cleanwise.service.apps.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.dao.ReportUserActivityDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ReportUserActivityData;
import com.cleanwise.service.api.value.ReportUserActivityDataVector;
import com.cleanwise.service.apps.ClientServicesAPI;

/**
 * Sends out the "Whine" email that gets sent between the end date and the absolute end date for physical inventory.
 * Requiers that the account has the physical inventory setup.
 */
public class ProcessUserActivityLog extends ClientServicesAPI{	
	private static final Logger log = Logger.getLogger(ProcessUserActivityLog.class);

	private final static int MAX_PARAMS_LEN = 3500;
	private final static String selUserStoreInfo = "select distinct ua.bus_entity_id as store_id, be.short_desc as store_name " +
			"from clw_user u " +
			"left outer join clw_user_assoc ua on u.user_id = ua.user_id " +
			"left outer join clw_bus_entity be on ua.bus_entity_id = be.bus_entity_id " +
			"where u.user_type_cd in ('CUSTOMER','MULTI-SITE BUYER') " +
			"and ua.user_assoc_cd = 'STORE' " +
			"and user_name = ?";

    private final static String LINE_SEPARATOR = System
            .getProperty("line.separator") == null ? "\n" : System
            .getProperty("line.separator");
    private final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(
    "MM/dd/yyyy hh:mm:ss:SSSS");
    private final static int HTTP_START = 0;
    private final static int ACTION_START = 1;
    private final static int ACTION_END = 2;
    private final static int HTTP_END = 3;
    private static final Comparator<LogItem> LOG_ITEM_COMPARE = new Comparator<LogItem>() {
        public int compare(LogItem o1, LogItem o2) {
            String httpStartedAtS1 = o1.httpStartedAtS;
            String httpStartedAtS2 = o2.httpStartedAtS;
            return Utility.compareTo(httpStartedAtS2, httpStartedAtS1);
        }
    };

    public static void main(String[] args) throws Exception{
    	Date runForDate = new Date();
    	log.info("Start of ProcessUserActivityLog. Date: "+runForDate);
        try {
	    	String logDir = System.getProperty("logDir");
	        if (logDir == null)
	        	throw new Exception("parameter logDir is needed.");
	        //String fileName = "server.log.2010-09-02-11";
	        //String fileName = "server.log.2010-09-07-16";
	        //String fileName = "server.log.2010-09-16-14";
	        String fileName = "server.log.2010-09-16-13.gz";
	        
	        File dir = new File(logDir);
	        if (!dir.exists() || !dir.isDirectory()){
	        	throw new Exception("Cannot find directory - " + dir.getCanonicalPath());
	        }
	        File[] files = dir.listFiles(new FileFilterPattern("server.log."));        
	        if (files.length > 0){
		        ProcessUserActivityLog process = new ProcessUserActivityLog();
		        process.process(files);
		        process.renameFiles(files);
	        }
	        log.info(files.length + " files processed");
        } catch (Exception e){
        	e.printStackTrace();
        }
        log.info("End of ProcessUserActivityLog. Date: "+new Date());
        

    }
    public void process(File[] files) throws Exception {     
    	Connection conn = null;  
        Connection reportConn = null;
        PreparedStatement pstmt = null;
        Map<String, String[]> userStoreMap = new TreeMap<String, String[]>();
        try {
        	conn = getConnection();
        	reportConn = getReportConnection(); 
        	pstmt = conn.prepareStatement(selUserStoreInfo);
        	for (File file : files){
        		log.info("process file: " + file.getName());
        		List<LogItem> result = getLogItems(getLogItems(file), pstmt, userStoreMap);
        		String serverName=java.net.InetAddress.getLocalHost().getHostName();
        		
        		for (LogItem logItem : result){
            		ReportUserActivityData userActivity = ReportUserActivityData.createValue();
            		String[] vals = userStoreMap.get(logItem.userName);
                    if (vals == null) {
                    	pstmt.setString(1, logItem.userName);
                    	ResultSet rs = pstmt.executeQuery();
                    	if (rs.next()){
                    		String storeId = rs.getString("store_id");
                            String storeName = rs.getString("store_name");
                            vals = new String[] { storeId, storeName };                    
                    	}else{
                    		vals = new String[] { null, null };
                    	}
                    	userStoreMap.put(logItem.userName, vals);
                    }
                    if (vals != null && vals[0] != null) {
                    	userActivity.setStoreId(new Integer(vals[0]).intValue());
                    	userActivity.setStoreName(vals[1]);
                    } 
                    
                    if (logItem.httpStartedAt == null){
                    	DBCriteria crit = new DBCriteria();
        	        	crit.addEqualTo(ReportUserActivityDataAccess.SESSION_ID,logItem.sessionId);
        	        	crit.addEqualTo(ReportUserActivityDataAccess.REQUEST_ID,logItem.requestId);
        	        	ReportUserActivityDataVector ruaVector = ReportUserActivityDataAccess.select(reportConn, crit);
        	        	if (ruaVector.size() > 0){
        	        		userActivity = (ReportUserActivityData) ruaVector.get(0);
        	        		if (userActivity.getActionStartTime() == null){
        	        			userActivity.setActionClass(logItem.className);
                                userActivity.setAction(logItem.action);
                                userActivity.setActionStartTime(logItem.actionStartedAt);
                                userActivity.setParams(logItem.params);
        	        		}
                                          	                
        	        		if (userActivity.getActionEndTime() == null){
        	        			userActivity.setActionEndTime(logItem.actionEndedAt);   
                                userActivity.setActionResult(logItem.actionResult);
                                userActivity.setActionDuration(logItem.actionDuration);  
        	        		}
        	        		if (userActivity.getHttpEndTime() == null){
        	        			userActivity.setHttpEndTime(logItem.httpEndedAt);
        	        			userActivity.setHttpResult(logItem.httpResult);
        	        			userActivity.setHttpDuration(logItem.httpDuration);                                
        	        		}
        	        		
                            ReportUserActivityDataAccess.update(reportConn, userActivity);
                            continue;
        	        	}        	        	
                    }
                    
                    userActivity.setSessionId(logItem.sessionId);
                    userActivity.setUserName(logItem.userName);                
                    userActivity.setActionClass(logItem.className);
                    userActivity.setAction(logItem.action);
                    userActivity.setHttpStartTime(logItem.httpStartedAt);
                    userActivity.setActionStartTime(logItem.actionStartedAt);
                    userActivity.setActionEndTime(logItem.actionEndedAt);
                    userActivity.setHttpEndTime(logItem.httpEndedAt);
                    userActivity.setActionResult(logItem.actionResult);
                    userActivity.setHttpResult(logItem.httpResult);
                    userActivity.setActionDuration(logItem.actionDuration);
                    userActivity.setHttpDuration(logItem.httpDuration);
                    userActivity.setReferer(logItem.referer);
                    userActivity.setParams(logItem.params);
                    userActivity.setFinishFile(logItem.endFile);  
                    userActivity.setServerName(serverName);
                    userActivity.setRequestId(logItem.requestId);
                    ReportUserActivityDataAccess.insert(reportConn, userActivity);
            	}
        	}
        	pstmt.close();
        }catch(Exception e){
        	if (reportConn != null)
        		reportConn.rollback();  
        	throw e;
        } finally {
        	closeConnection(conn);
        	closeConnection(reportConn);
        }
    }
        
	public void renameFiles(File[] files) {
		for (File file : files){
			File newFile = new File(file.getParent(), "processed_" + file.getName());

			if (file.renameTo(newFile)) {
				log.info("File " + file.getName() + " has been renamed to " + newFile.getName());
			} else {
				log.info("Error renmaing file - " + file.getName());
			}
		}
	}
    
    private static class  FileFilterPattern implements FileFilter{
    	String dirFilterName;
    	public FileFilterPattern(String pDirFilterName){
    		dirFilterName = pDirFilterName;
    	}
        public boolean accept(File pathname) {        	
        	if (pathname.isFile() && pathname.getName().startsWith(dirFilterName)) {
                return true;
            }
            return false;
        }
    }
    
    public static List<String[]>[] getLogItems(File file) throws IOException {
    	InputStream in = null;
    	if (file.getAbsolutePath().endsWith(".gz") || file.getAbsolutePath().endsWith(".GZ")){
    		in = new GZIPInputStream(new FileInputStream(file));
    	}else{
    		in = new FileInputStream(file);
    	}
    	BufferedReader br = new BufferedReader(new InputStreamReader(in));
        BufferedReader reader = new BufferedReader(br);
        String line = null;
        Token httpStart = new Token("@@##@@S", "@@##@@");
        Token httpEnd = new Token("@@##@@E", "@@##@@");
        Token actionStart = new Token("@@@@S", "@@@@");
        Token actionEnd = new Token("@@@@E", "@@@@");
        TokenReader tokenReader = new TokenReader(new Token[] { httpStart, actionStart, actionEnd, httpEnd });
        do {
            line = reader.readLine();
            tokenReader.processLine(line, file);
        } while (line != null);
        in.close();
        return tokenReader.getData();
    }
    
    private static List<LogItem> getLogItems(List<String[]>[] data, PreparedStatement pstmt, Map<String, String[]> userStoreMap) throws Exception {
    	Map<String, LogItem> actionTokensByReqId = new HashMap<String, LogItem>();
        
        // parsing the HTTP_START line
        parseLine(data[HTTP_START], actionTokensByReqId, HTTP_START);
        // parsing the HTTP_START line
        parseLine(data[ACTION_START], actionTokensByReqId, ACTION_START);
        // parsing the HTTP_START line
        parseLine(data[ACTION_END], actionTokensByReqId, ACTION_END);
        // parsing the HTTP_START line
        parseLine(data[HTTP_END], actionTokensByReqId, HTTP_END);
        List list = new ArrayList(actionTokensByReqId.values());
        Collections.sort(list, LOG_ITEM_COMPARE);
        return list;
    }
	private static void parseLine(List<String[]> data,
			Map<String, LogItem> actionTokensByReqId, int actionType) {
		for (String[] ii : data) {
            String i = ii[0];
            String userName = parse(i, "User: <(.*?)>");
            if (userName == null) {
                continue;
            }
            String requestId = parse(i, "RequestId: <(.*?)>");
            if (requestId == null) {
                continue;
            }
            
            LogItem logItem = actionTokensByReqId.get(requestId);
            if (logItem == null){
            	logItem = new LogItem();            
	            logItem.endFile = ii[1];
	            logItem.userName = userName;
	            logItem.requestId = requestId;
	            logItem.sessionId = parse(i, "Session id : <(.*?)>");
	            if (actionType == HTTP_START){
	            	logItem.httpStartedAtS = parse(i, "Started at: <(.*?)>");
	                logItem.httpStartedAt = getDate(logItem.httpStartedAtS);
	                logItem.referer = parse(i, "Referer: <(.*?)>");
	            }else if (actionType == ACTION_START){
	            	logItem.className = parse(i, "Class: <(.*?)>");
	                logItem.action = parse(i, "Action: <(.*?)>");
	                logItem.actionStartedAtS = parse(i, "Started at: <(.*?)>");
	                logItem.actionStartedAt = getDate(logItem.actionStartedAtS);
	                logItem.params = getLimitString(parse(i, "Params: \\{(.*?)\\}"),
	                        MAX_PARAMS_LEN);
	            }else if (actionType == ACTION_END){
	            	logItem.className = parse(i, "Class: <(.*?)>");
	                logItem.action = parse(i, "Action: <(.*?)>");
	                logItem.actionStartedAtS = parse(i, "Started at: <(.*?)>");
	                logItem.actionStartedAt = getDate(logItem.actionStartedAtS);
	                logItem.actionEndedAtS = parse(i, "Ended at: <(.*?)>");
	                logItem.actionEndedAt = getDate(logItem.actionEndedAtS);
	                logItem.actionResult = parse(i, "Result: <(.*?)>");
	                logItem.actionDuration = getBigDecimal(parse(i, "Duration: <(.*?)>"));
	                logItem.params = getLimitString(parse(i, "Params: \\{(.*?)\\}"),
	                        MAX_PARAMS_LEN);                
	            }else if (actionType == HTTP_END){
	            	logItem.httpStartedAtS = parse(i, "Started at: <(.*?)>");
	                logItem.httpStartedAt = getDate(logItem.httpStartedAtS);
	                logItem.referer = parse(i, "Referer: <(.*?)>");
	                logItem.httpEndedAtS = parse(i, "Ended at: <(.*?)>");
	                logItem.httpEndedAt = getDate(logItem.httpEndedAtS);
	                logItem.httpResult = parse(i, "Result: <(.*?)>");
	                logItem.httpDuration = getBigDecimal(parse(i, "Duration: <(.*?)>"));
	            }
	            actionTokensByReqId.put(requestId, logItem);
            }else{
            	if (actionType == ACTION_START){
	            	logItem.className = parse(i, "Class: <(.*?)>");
	                logItem.action = parse(i, "Action: <(.*?)>");
	                logItem.actionStartedAtS = parse(i, "Started at: <(.*?)>");
	                logItem.actionStartedAt = getDate(logItem.actionStartedAtS);
	                logItem.params = getLimitString(parse(i, "Params: \\{(.*?)\\}"),
	                        MAX_PARAMS_LEN);
	            }else if (actionType == ACTION_END){
	            	logItem.actionEndedAtS = parse(i, "Ended at: <(.*?)>");
	                logItem.actionEndedAt = getDate(logItem.actionEndedAtS);
	                logItem.actionResult = parse(i, "Result: <(.*?)>");
	                logItem.actionDuration = getBigDecimal(parse(i, "Duration: <(.*?)>"));
	            }else if (actionType == HTTP_END){
	            	logItem.httpEndedAtS = parse(i, "Ended at: <(.*?)>");
	                logItem.httpEndedAt = getDate(logItem.httpEndedAtS);
	                logItem.httpResult = parse(i, "Result: <(.*?)>");
	                logItem.httpDuration = getBigDecimal(parse(i, "Duration: <(.*?)>"));
	            }
            }
        }
	}

    public static String parse(String source, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
                + Pattern.DOTALL);
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
    
    public static class LogItem {
        public String requestId;
        public String sessionId;
        public String userName;
        public String className;
        public String action;
        public String httpStartedAtS;
        public Date httpStartedAt;
        public String actionStartedAtS;
        public Date actionStartedAt;
        public String actionEndedAtS;
        public Date actionEndedAt;
        public String httpEndedAtS;
        public Date httpEndedAt;
        public String actionResult;
        public String httpResult;
        public BigDecimal actionDuration;
        public BigDecimal httpDuration;
        public String referer;
        public String params;
        public String endFile;
    }

    public static class TokenReader {
        private Token[] tokens;

        private List<String[]>[] data;

        public TokenReader(Token[] tokens) {
            this.tokens = tokens;
            data = new ArrayList[tokens.length];
            for (int i = 0; i < data.length; i++) {
                data[i] = new ArrayList<String[]>();
            }
        }

        public void processLine(String line, File file) {
            for (int i = 0; i < tokens.length; i++) {
                tokens[i].processLine(line);
                if (tokens[i].isFilled()) {
                	String userName = ProcessUserActivityLog.parse(tokens[i].getValue(), "User: <(.*?)>");
                	if (userName != null) {
                		String requestId = ProcessUserActivityLog.parse(tokens[i].getValue(), "RequestId: <(.*?)>");
                    	data[i].add(new String[] { tokens[i].getValue(), file.getAbsolutePath() });	                    
                    } 
                	tokens[i].clear();
                    break;
                }
            }
        }

        public List<String[]>[] getData() {
            return data;
        }
    }

    public static class Token {
        private final String beginMark;
        private final String endMark;
        private String value;
        private boolean isFilled;

        public Token(String beginMark, String endMark) {
            this.beginMark = beginMark;
            this.endMark = endMark;
        }

        public void clear() {
            value = null;
            isFilled = false;
        }

        public boolean isFilled() {
            return isFilled;
        }

        public String getValue() {
            return value;
        }

        public String processLine(String line) {
            if (line != null && line.length() > 0) {
                int index = line.indexOf(endMark);
                if (value == null) {
                    index = line.indexOf(beginMark);
                    if (index > -1) {
                        int indexStart = index + beginMark.length();
                        int indexEnd = line.indexOf(endMark, indexStart);
                        if (indexEnd > -1) {
                            value = line.substring(indexStart, indexEnd);
                            isFilled = true;
                            return line.substring(indexEnd + endMark.length());
                        } else {
                        	if (indexStart > line.length()){
                        		value = LINE_SEPARATOR;
                        	}else{
                        		value = line.substring(indexStart);
                        		value += LINE_SEPARATOR;
                        	}
                        }
                    }
                } else if (index > -1) {
                    value += line.substring(0, index);
                    isFilled = true;
                    return line.substring(index + endMark.length());
                } else {
                    value += line;
                    value += LINE_SEPARATOR;
                }
            }
            return null;
        }
    }
    
    public static class TokenKey {
        public String sessionId;

        public String startedAt;

        public TokenKey(String sessionId, String startedAt) {
            this.sessionId = sessionId;
            this.startedAt = startedAt;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TokenKey) {
                TokenKey other = (TokenKey) obj;
                if (other.sessionId != null
                        && other.sessionId.equals(sessionId)
                        && other.startedAt != null
                        && other.startedAt.equals(startedAt)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            if (sessionId != null) {
                hash = sessionId.hashCode();
            }
            if (startedAt != null) {
                hash += 31 * hash + startedAt.hashCode();
            }
            return hash;
        }

    }

    
}
