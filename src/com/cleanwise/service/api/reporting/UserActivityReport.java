package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserActivityReport implements GenericReportMulti {
    private final static int MAX_PARAMS_LEN = 4000;

    public final static String LINE_SEPARATOR = System
            .getProperty("line.separator") == null ? "\n" : System
            .getProperty("line.separator");

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "MM/dd/yyyy");

    public GenericReportResultViewVector process(ConnectionContainer pCons,
            GenericReportData pReportData, Map pParams) throws Exception {
        String pStartDateS = (String) pParams.get("BEG_DATE_OPT");
        String pEndDateS = (String) pParams.get("END_DATE_OPT");
        Connection conn = pCons.getDefaultConnection();
        final Map<String, String[]> approvedUsers = getApprovedUsersMap(conn);
        final Date startDate = getDate(pStartDateS, DATE_FORMAT);
        final Date endDate;
        Date bufferDate = getDate(pEndDateS, DATE_FORMAT);
        if (bufferDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(bufferDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            endDate = c.getTime();
        } else {
            endDate = null;
        }
        String ffnn = ".";
        File ffN = new File(ffnn);
        System.out
                .println("UserActivityReport WWWWWWWWWWWWWWWW current directory: "
                        + ffN.getAbsolutePath());

        String pFileName = (String)ReportingUtils.getParam(pParams, "LOG_FILE_OR_DIR");
        String dirFilterName = (String)ReportingUtils.getParam(pParams, "LOG_DIR_FILTER_NAME");
        if (Utility.isSet(pFileName) == false) {
        	pFileName = "../server/defst/log/";
        }
        if(Utility.isSet(dirFilterName) == false){
    		//server.log.2009-07-30-07
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		dirFilterName = "server.log."+sdf.format(new Date()); //hard coded to today
    	}
        final Acceptor acceptor = new Acceptor() {
            public boolean accept(LogItem logItem) {
                // if (logItem.userName == null
                // || approvedUsers.containsKey(logItem.userName) == false) {
                // return false;
                // }
                if (logItem.httpStartedAt != null) {
                    if (startDate != null
                            && startDate.compareTo(logItem.httpStartedAt) > 0) {
                        return false;
                    }
                    if (endDate != null
                            && endDate.compareTo(logItem.httpStartedAt) <= 0) {
                        return false;
                    }
                }
                return true;
            }
        };
        int hoursInterval = 3;
        List dataRows = getLogItems(pFileName, dirFilterName,acceptor, DEFAULT_CONVERTER);
        List dataLists = new ArrayList();
        for (int i = 0; i < dataRows.size(); i++) {
            LogItem row = (LogItem) dataRows.get(i);
            dataLists.add(row.toList(approvedUsers));
        }
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        GenericReportResultView pageGeneral = GenericReportResultView
                .createValue();
        pageGeneral.setTable(getGroupData(dataRows, hoursInterval,
                approvedUsers));
        pageGeneral.setHeader(getGroupHeader(hoursInterval));
        pageGeneral.setColumnCount(pageGeneral.getHeader().size());
        pageGeneral.setName("Group");
        GenericReportResultView pageDetail = GenericReportResultView
                .createValue();
        pageDetail.setTable(new ArrayList(dataLists));
        pageDetail.setHeader(getDetailHeader());
        pageDetail.setColumnCount(pageDetail.getHeader().size());
        pageDetail.setName("Details");
        resultV.add(pageDetail);
        resultV.add(pageGeneral);
        return resultV;
    }

    private static GenericReportColumnViewVector getGroupHeader(
            int hoursInterval) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "User Name", 0, 255, "VARCHAR2"));
        List<Integer> hours = getStartHours(hoursInterval);
        for (int i : hours) {
            header.add(ReportingUtils.createGenericReportColumnView(
                    "java.math.BigDecimal", "Action " + i, 5, 20, "NUMBER"));
            header.add(ReportingUtils.createGenericReportColumnView(
                    "java.math.BigDecimal", "HTTP " + i, 5, 20, "NUMBER"));
        }
        return header;
    }

    private GenericReportColumnViewVector getDetailHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Store Id", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Store Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Session Id", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "User Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action Class", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "HTTP Start Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action Start Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action End Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "HTTP End Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action Result", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "HTTP Result", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.math.BigDecimal", "Action Dur", 5, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.math.BigDecimal", "HTTP Dur", 5, 20, "NUMBER"));
        header
                .add(ReportingUtils.createGenericReportColumnView(
                        "java.lang.String", "Referer", 500, MAX_PARAMS_LEN,
                        "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Params", 0, MAX_PARAMS_LEN, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Finish File", 0, 255, "VARCHAR2"));
        return header;
    }

    
    private static class  FileFilterPattern implements FileFilter{
    	String dirFilterName;
    	public FileFilterPattern(String pDirFilterName){
    		dirFilterName = pDirFilterName;
    	}
        public boolean accept(File pathname) {
            if (pathname.getName().equals("server.log")) {
                return true;
            }
            if (pathname.getName().contains(dirFilterName)
                    && pathname.getName().endsWith(".gz") == false) {
                return true;
            }
            return false;
        }
    }
    
    public static List<LogItem> getLogItems(String fileName, String dirFilterName,  Acceptor acceptor,
            Converter converter) throws Exception {
    	
        List<LogItem> result = new ArrayList<LogItem>();
        File file = new File(fileName);
        File[] files = null;
        if (file.exists() == false) {
        } else if (file.isFile()) {
            files = new File[] { new File(fileName) };
        } else if (file.isDirectory()) {
            files = file.listFiles(new FileFilterPattern(dirFilterName));
        }
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File file1, File file2) {
                if (file1.getName().equals("server.log")) {
                    return Integer.MAX_VALUE;
                }
                return file1.getName().compareTo(file2.getName());
            }
        });
        if (files == null || files.length == 0) {
            String mess = "File does not exist: " + fileName;
            throw new Exception(mess);
        }

        try {
            for (int i = 0; i < files.length; i++) {
                result.addAll(getTokenGroup(getLogItems2(files[i]), acceptor));
            }
        } catch (FileNotFoundException exc) {
            String mess = "File does not exist: " + fileName;
            throw new Exception(mess);
        }
        return result;
    }

    // public static List getLogItems(BufferedReader reader) throws IOException
    // {
    // return getLogItems(reader, null, null);
    // }

    // public static List getLogItems(BufferedReader reader, Acceptor acceptor,
    // Converter converter) throws IOException {
    // List result = new ArrayList();
    // String line = null;
    // TokenGroup record = null;
    // do {
    // if (record == null) {
    // record = new TokenGroup(new Token[] {
    // new Token("@@##@@S", "@@##@@"),
    // new Token("@@@@S", "@@@@"), new Token("@@@@E", "@@@@"),
    // new Token("@@##@@E", "@@##@@") });
    // }
    // line = reader.readLine();
    // record.processLine(line);
    // if (record.isFilled()) {
    // if (acceptor == null || acceptor.accept(record)) {
    // Object item = record;
    // if (converter != null) {
    // item = converter.convert(record);
    // }
    // result.add(item);
    // }
    // record = null;
    // }
    // } while (line != null);
    // return result;
    // }

    public static List<String[]>[] getLogItems2(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = null;
        // Token httpStart = new Token("@@##@@S", "@@##@@");
        Token httpEnd = new Token("@@##@@E", "@@##@@");
        // Token actionStart = new Token("@@@@S", "@@@@");
        Token actionEnd = new Token("@@@@E", "@@@@");
        TokenReader tokenReader = new TokenReader(new Token[] { httpEnd,
                actionEnd });
        do {
            line = reader.readLine();
            tokenReader.processLine(line, file);
        } while (line != null);
        return tokenReader.getData();
    }

    public static List<LogItem> getTokenGroup(List<String[]>[] data,
            Acceptor acceptor) {
        Map<TokenKey, LogItem> httpTokens = new HashMap<TokenKey, LogItem>();
        Map<String, LogItem> httpTokensByReqId = new HashMap<String, LogItem>();
        for (String[] ii : data[0]) {
            String i = ii[0];
            LogItem logItem = new LogItem();
            logItem.endFile = ii[1];
            logItem.userName = parse(i, "User: <(.*?)>");
            if (logItem.userName == null) {
                continue;
            }
            logItem.requestId = parse(i, "RequestId: <(.*?)>");
            logItem.sessionId = parse(i, "Session id : <(.*?)>");
            logItem.httpStartedAtS = parse(i, "Started at: <(.*?)>");
            logItem.httpStartedAt = getDate(logItem.httpStartedAtS);
            logItem.referer = parse(i, "Referer: <(.*?)>");
            logItem.httpEndedAtS = parse(i, "Ended at: <(.*?)>");
            logItem.httpEndedAt = getDate(logItem.httpEndedAtS);
            logItem.httpResult = parse(i, "Result: <(.*?)>");
            logItem.httpDuration = getBigDecimal(parse(i, "Duration: <(.*?)>"));
            logItem.referer = parse(i, "Referer: <(.*?)>");
            TokenKey key = new TokenKey(logItem.sessionId,
                    logItem.httpStartedAtS);
            LogItem exist = httpTokens.put(key, logItem);
            if (logItem.requestId != null) {
                LogItem exist2 = httpTokensByReqId.put(logItem.requestId,
                        logItem);
            }
            // if (exist != null) {
            // RuntimeException e = new RuntimeException(
            // "HTTPLog with SessionId:" + key.sessionId
            // + " and StartedAt:" + key.startedAt
            // + " already exist!");
            // System.err.println(e.getMessage());
            // throw e;
            // }
        }
        List<LogItem> result = new ArrayList<LogItem>();
        for (String[] ii : data[1]) {
            String i = ii[0];
            LogItem logItem = new LogItem();
            logItem.userName = parse(i, "User: <(.*?)>");
            if (logItem.userName == null) {
                continue;
            }
            logItem.endFile = ii[1];
            logItem.requestId = parse(i, "RequestId: <(.*?)>");
            logItem.sessionId = parse(i, "Session id : <(.*?)>");
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
            List<LogItem> httpLogItems = findHttpLogItem(httpTokens.values(),
                    logItem, httpTokensByReqId.get(logItem.requestId));
            if (httpLogItems.size() == 0) {
                System.out
                        .println("\tNot found HTTPLog for ActionLog with SessionId:<"
                                + logItem.sessionId
                                + "> and StartedAt:<"
                                + logItem.actionStartedAtS + "> ");
            } else {
                if (httpLogItems.size() > 1) {
                    System.out
                            .println("\tWas found "
                                    + httpLogItems.size()
                                    + " HTTPLog(MUST BE ONE???) for ActionLog with SessionId:<"
                                    + logItem.sessionId + "> and StartedAt:<"
                                    + logItem.actionStartedAtS + ">");
                }
                LogItem httpLogItem = httpLogItems.get(0);
                httpLogItem.className = logItem.className;
                httpLogItem.action = logItem.action;
                httpLogItem.actionStartedAtS = logItem.actionStartedAtS;
                httpLogItem.actionStartedAt = logItem.actionStartedAt;
                httpLogItem.actionEndedAtS = logItem.actionEndedAtS;
                httpLogItem.actionEndedAt = logItem.actionEndedAt;
                httpLogItem.actionResult = logItem.actionResult;
                httpLogItem.actionDuration = logItem.actionDuration;
                httpLogItem.params = logItem.params;
                if (acceptor == null || acceptor.accept(httpLogItem)) {
                    result.add(httpLogItem);
                }
            }
        }
        return result;
    }

    private static List<LogItem> findHttpLogItem(
            Iterable<LogItem> httpLogItems, LogItem actionLogItem,
            LogItem logByReqId) {
        List<LogItem> list = new ArrayList<LogItem>();
        if (logByReqId != null) {
            list.add(logByReqId);
        } else {
            for (LogItem li : httpLogItems) {
                if (li.sessionId.equals(actionLogItem.sessionId)
                        && li.httpStartedAt
                                .compareTo(actionLogItem.actionStartedAt) <= 0
                        && li.httpEndedAt
                                .compareTo(actionLogItem.actionEndedAt) >= 0) {
                    list.add(li);
                }
            }
        }
        return list;
    }

    public static class DateRangeKey {
        public String sessionId;

        public Date startedAt;

        public Date endedAt;

        public String startedAtS;
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

    public static interface Converter {
        Object convert(TokenGroup source);
    }

    public final static Converter DEFAULT_CONVERTER = new Converter() {
        public Object convert(TokenGroup record) {
            LogItem logItem = new LogItem();
            logItem.sessionId = parse(record.getTokens()[3].getValue(),
                    "Session id : <(.*?)>");
            logItem.userName = parse(record.getTokens()[0].getValue(),
                    "User: <(.*?)>");
            logItem.className = parse(record.getTokens()[1].getValue(),
                    "Class: <(.*?)>");
            logItem.action = parse(record.getTokens()[1].getValue(),
                    "Action: <(.*?)>");
            logItem.httpStartedAtS = parse(record.getTokens()[3].getValue(),
                    "Started at: <(.*?)>");
            logItem.httpStartedAt = getDate(logItem.httpStartedAtS);
            logItem.actionStartedAtS = parse(record.getTokens()[2].getValue(),
                    "Started at: <(.*?)>");
            logItem.actionStartedAt = getDate(logItem.actionStartedAtS);
            logItem.actionEndedAtS = parse(record.getTokens()[2].getValue(),
                    "Ended at: <(.*?)>");
            logItem.actionEndedAt = getDate(logItem.actionEndedAtS);
            logItem.httpEndedAtS = parse(record.getTokens()[3].getValue(),
                    "Ended at: <(.*?)>");
            logItem.httpEndedAt = getDate(logItem.httpEndedAtS);
            logItem.actionResult = parse(record.getTokens()[2].getValue(),
                    "Result: <(.*?)>");
            logItem.httpResult = parse(record.getTokens()[3].getValue(),
                    "Result: <(.*?)>");
            logItem.actionDuration = getBigDecimal(parse(record.getTokens()[2]
                    .getValue(), "Duration: <(.*?)>"));
            logItem.httpDuration = getBigDecimal(parse(record.getTokens()[3]
                    .getValue(), "Duration: <(.*?)>"));
            logItem.referer = parse(record.getTokens()[0].getValue(),
                    "Referer: <(.*?)>");
            logItem.params = getLimitString(parse(record.getTokens()[1]
                    .getValue(), "Params: \\{(.*?)\\}"), MAX_PARAMS_LEN);
            return logItem;
        }
    };

    public final static Converter CONVERTER_TO_LIST = new Converter() {
        public Object convert(TokenGroup record) {
            final List list = new ArrayList();
            list.add(parse(record.getTokens()[3].getValue(),
                    "Session id : <(.*?)>"));
            list.add(parse(record.getTokens()[0].getValue(), "User: <(.*?)>"));
            list.add(parse(record.getTokens()[1].getValue(), "Class: <(.*?)>"));
            list
                    .add(parse(record.getTokens()[1].getValue(),
                            "Action: <(.*?)>"));
            list.add(parse(record.getTokens()[3].getValue(),
                    "Started at: <(.*?)>"));
            list.add(parse(record.getTokens()[2].getValue(),
                    "Started at: <(.*?)>"));
            list.add(parse(record.getTokens()[2].getValue(),
                    "Ended at: <(.*?)>"));
            list.add(parse(record.getTokens()[3].getValue(),
                    "Ended at: <(.*?)>"));
            list
                    .add(parse(record.getTokens()[2].getValue(),
                            "Result: <(.*?)>"));
            list
                    .add(parse(record.getTokens()[3].getValue(),
                            "Result: <(.*?)>"));
            list.add(getBigDecimal(parse(record.getTokens()[2].getValue(),
                    "Duration: <(.*?)>")));
            list.add(getBigDecimal(parse(record.getTokens()[3].getValue(),
                    "Duration: <(.*?)>")));
            list
                    .add(parse(record.getTokens()[0].getValue(),
                            "Referer: <(.*?)>"));
            list.add(getLimitString(parse(record.getTokens()[1].getValue(),
                    "Params: {.*?)}"), MAX_PARAMS_LEN));
            return list;
        }
    };

    public static interface Acceptor {
        boolean accept(LogItem record);
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

        public List toList(Map<String, String[]> userStoreMap) {
            List list = new ArrayList();
            String[] vals = userStoreMap.get(this.userName);
            if (vals != null) {
                list.add(vals[0]);
                list.add(vals[1]);
            } else {
                list.add(null);
                list.add(null);
            }
            list.add(this.sessionId);
            list.add(this.userName);
            list.add(this.className);
            list.add(this.action);
            list.add(this.httpStartedAtS);
            list.add(this.actionStartedAtS);
            list.add(this.actionEndedAtS);
            list.add(this.httpEndedAtS);
            list.add(this.actionResult);
            list.add(this.httpResult);
            list.add(this.actionDuration);
            list.add(this.httpDuration);
            list.add(this.referer);
            list.add(this.params);
            list.add(this.endFile);
            return list;
        }
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
                    data[i].add(new String[] { tokens[i].getValue(),
                            file.getAbsolutePath() });
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
                            value = line.substring(indexStart);
                            value += LINE_SEPARATOR;
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

    public static class TokenGroup {
        private final Token[] tokens;

        public TokenGroup(Token[] tokens) {
            this.tokens = tokens;
        }

        public Token[] getTokens() {
            return tokens;
        }

        public boolean isFilled() {
            for (int i = 0; tokens != null && i < tokens.length; i++) {
                if (tokens[i].isFilled() == false) {
                    return false;
                }
            }
            return true;
        }

        public String processLine(String line) {
            for (int i = 0; tokens != null && i < tokens.length; i++) {
                if (tokens[i].isFilled() == false) {
                    return tokens[i].processLine(line);
                }
            }
            return null;
        }
    }

    private static String parse(String source, String pattern) {
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

    private final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(
            "MM/dd/yyyy hh:mm:ss:SSSS");

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

    private Map<String, String[]> getApprovedUsersMap(Connection conn)
            throws SQLException {
        String sql = "select u.user_name as user_name, ua.bus_entity_id as store_id, be.short_desc as store_name";
        sql += " from clw_user u";
        sql += " left outer join clw_user_assoc ua on u.user_id = ua.user_id";
        sql += " left outer join clw_bus_entity be on ua.bus_entity_id = be.bus_entity_id";
        sql += " where u.user_type_cd in ('CUSTOMER','MULTI-SITE BUYER')";
        sql += " and ua.user_assoc_cd = 'STORE'";
        Statement s = null;
        ResultSet rs = null;
        Map<String, String[]> map = new TreeMap<String, String[]>();
        try {
            s = conn.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                String userName = rs.getString("user_name");
                String storeId = rs.getString("store_id");
                String storeName = rs.getString("store_name");
                map.put(userName, new String[] { storeId, storeName });
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
        return map;

    }

    private Set getApprovedUsersZZZ(Connection conn) throws SQLException {
        DBCriteria cr = new DBCriteria();
        List userTypes = new ArrayList();
        userTypes.add(RefCodeNames.USER_TYPE_CD.CUSTOMER);
        userTypes.add(RefCodeNames.USER_TYPE_CD.MSB);
        cr.addOneOf(UserDataAccess.USER_TYPE_CD, userTypes);
        long start = System.currentTimeMillis();
        UserDataVector users = UserDataAccess.select(conn, cr);
        long finish = System.currentTimeMillis();
        final Set approvedUsers = new HashSet();
        for (int i = 0; i < users.size(); i++) {
            UserData ud = (UserData) users.get(i);
            approvedUsers.add(ud.getUserName());
        }
        return approvedUsers;
    }

    private static ArrayList getGroupData(List records, int hoursInterval,
            Map<String, String[]> approvedUsers) {
        Map<Date, Map<Integer, List<LogItem>>> data = new TreeMap<Date, Map<Integer, List<LogItem>>>();
        for (int i = 0; i < records.size(); i++) {
            LogItem row = (LogItem) records.get(i);
            Date key1 = getDay(row.httpStartedAt);
            Map<Integer, List<LogItem>> value1 = data.get(key1);
            if (value1 == null) {
                value1 = new TreeMap<Integer, List<LogItem>>();
                data.put(key1, value1);
            }
            int key2 = getStartHour(row.httpStartedAt, hoursInterval);
            List<LogItem> value2 = value1.get(key2);
            if (value2 == null) {
                value2 = new ArrayList<LogItem>();
                value1.put(key2, value2);
            }
            value2.add(row);
        }
        ArrayList list = new ArrayList();
        for (Map.Entry<Date, Map<Integer, List<LogItem>>> i : data.entrySet()) {
            Date key1 = i.getKey();
            Map<Integer, List<LogItem>> val1 = i.getValue();
            list.add(getDayHeader(key1, hoursInterval));
            list.addAll(getDayData(val1, getStartHours(hoursInterval),
                    approvedUsers));
        }
        return list;
    }

    private static int getStartHour(Date date, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int i = 0;
        for (; i <= hour; i += interval) {
        }
        hour = i - interval;
        return hour >= 0 && hour <= 23 ? hour : 0;
    }

    private static List getDayHeader(Date date, int interval) {
        List list = new ArrayList();
        list.add(DATE_FORMAT.format(date));
        return list;
    }

    private static List getDayData(Map<Integer, List<LogItem>> data,
            List<Integer> intervals, Map<String, String[]> approvedUsers) {
        Map<String, Map<Integer, BigDecimal[]>> map = new TreeMap<String, Map<Integer, BigDecimal[]>>();
        for (Map.Entry<Integer, List<LogItem>> e : data.entrySet()) {
            int key1 = e.getKey();
            List<LogItem> val1 = e.getValue();
            for (LogItem r : val1) {
                if (r.userName == null) {
                    continue;
                }
                Map<Integer, BigDecimal[]> val2 = map.get(r.userName);
                if (val2 == null) {
                    val2 = new TreeMap<Integer, BigDecimal[]>();
                    map.put(r.userName, val2);
                }
                BigDecimal[] val3 = val2.get(key1);
                if (val3 == null) {
                    val3 = new BigDecimal[] { r.actionDuration, r.httpDuration };
                    val2.put(key1, val3);
                } else {
                    val3[0] = average(val3[0], r.actionDuration);
                    val3[1] = average(val3[1], r.httpDuration);
                }
            }
        }
        List result = new ArrayList();
        for (Map.Entry<String, Map<Integer, BigDecimal[]>> e : map.entrySet()) {
            List row = new ArrayList();
            String store = "";
            String[] vals = approvedUsers.get(e.getKey());
            if (vals != null) {
                store = vals[0] + "/" + vals[1] + " ";
            }
            row.add(store + e.getKey());
            for (Integer i : intervals) {
                BigDecimal[] avgs = e.getValue().get(i);
                if (avgs != null) {
                    row.add(avgs[0]);
                    row.add(avgs[1]);
                } else {
                    row.add(null);
                    row.add(null);
                }
            }
            result.add(row);
        }
        return result;
    }

    private static BigDecimal average(BigDecimal p1, BigDecimal p2) {
        if (p1 != null && p2 != null) {
            return p1.add(p2).divide(new BigDecimal(2),
                    BigDecimal.ROUND_HALF_UP);
        } else if (p1 != null) {
            return p1;
        } else if (p2 != null) {
            return p2;
        }
        return null;
    }

    private static List<Integer> getStartHours(int hoursInterval) {
        final List<Integer> startHours = new ArrayList<Integer>();
        for (int i = 0; i <= 23; i += hoursInterval) {
            startHours.add(i);
        }
        return startHours;
    }

    private static Date getDay(Date date) {
        Calendar source = Calendar.getInstance();
        source.setTime(date);
        Calendar c = Calendar.getInstance();
        c.set(source.get(Calendar.YEAR), source.get(Calendar.MONTH), source
                .get(Calendar.DATE), 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    private static String getLimitString(String source, int maxLength) {
        if (source != null && source.length() > maxLength) {
            return source.substring(0, maxLength);
        }
        return source;
    }
}
