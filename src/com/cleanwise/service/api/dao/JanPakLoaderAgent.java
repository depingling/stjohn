package com.cleanwise.service.api.dao;

import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;


public class JanPakLoaderAgent {

    private static final Logger log = Logger.getLogger(JanPakLoaderAgent.class);

    public interface JD_UOM_CD {
        public static final String CA = "CA";
        public static final String CT = "CT";
        public static final String CS = "CS";
        public static final String EA = "EA";
    }

    private static final String EMPTY = "";
    private static final int WRK_BUF_LEN = 1000;
    public static final String DEFAULT_MFG_SKU_VALUE = "NA";
    public static final String DEFAULT_PACK_VALUE = "1";
    public static final String DEFAULT_UOM_VALUE = RefCodeNames.ITEM_UOM_CD.UOM_EA;

    public static final String UOM = "UOM";
    public static final String PACK = "PACK";

    private static final char DEFAULT_DELIMITER = ',';
    private char delimiter;

    public  JanPakLoaderAgent () {
      this(DEFAULT_DELIMITER);
    }
    public  JanPakLoaderAgent (char pDelimiter) {
      delimiter = pDelimiter;
    }

    private String toString(String[] params) {
        StringBuffer buff = new StringBuffer();
        for (String param : params) {
            buff.append("[").append(param).append("]");
        }
        return buff.toString();
    }

    private String[] parseLine(String line) throws Exception {

        ArrayList<String> params = new ArrayList<String>();
        String pBuf = "";
        StringBuffer sBuf = new StringBuffer(line);
        for (int i = 0; i < sBuf.length(); i++) {
            char c = sBuf.charAt(i);
            if (c == delimiter || isLastCharacter(sBuf, i)) {

                if (c != delimiter && isLastCharacter(sBuf, i)) {
                    pBuf += c;
                }

                if (pBuf.startsWith("\"") && pBuf.endsWith("\"")) {
                    params.add(pBuf.substring(1, pBuf.length() - 1));
                    pBuf = EMPTY;
                    if (c == delimiter && isLastCharacter(sBuf, i)) {
                        params.add(EMPTY);
                    }
                } else if (!pBuf.startsWith("\"")) {
                    params.add(pBuf);
                    pBuf = EMPTY;
                    if (c == delimiter && isLastCharacter(sBuf, i)) {
                        params.add(EMPTY);
                    }
                } else {
                    pBuf += c;
                }

            } else {
                pBuf += c;
            }
        }

        return params.toArray(new String[params.size()]);
    }

    public void accept(Connection conn,
                       byte[] pData,
                       String pTable,
                       String pFileName,
                       java.sql.Date pModDate,
                       java.util.Date pCurrentDate,
                       String pUser) throws Exception {

        if (pData == null) {
           log.info("accept()=> Skipping: No data.");
           return;
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(pData);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(bais));

        String line;
        int lineNum = 0;
        int fieldQty = 0;
        StringBuffer insertStr = new StringBuffer();
        PreparedStatement pstmt = null;
        while ((line = bReader.readLine()) != null) {

            String[] params = parseLine(line);
            if (lineNum == 0) {

                fieldQty = params.length;
                insertStr.append("INSERT /*+ APPEND */ INTO ");
                insertStr.append(pTable);
                insertStr.append(" VALUES (?,?");
                for (int i = 0; i < fieldQty; i++) {
                    insertStr.append(",?");
                }
                insertStr.append(",?,?,?,?");
                insertStr.append(")");
                pstmt = conn.prepareStatement(insertStr.toString());


            } else if (params.length == fieldQty) {

                int i = 0;
                pstmt.setString(++i, pFileName);
                pstmt.setDate(++i, pModDate);

                for (String param : params) {
                    pstmt.setString(++i, param);
                }

                pstmt.setTimestamp(++i, DBAccess.toSQLTimestamp(pCurrentDate));
                pstmt.setString(++i, pUser);
                pstmt.setTimestamp(++i, DBAccess.toSQLTimestamp(pCurrentDate));
                pstmt.setString(++i, pUser);

                try {
                    pstmt.addBatch();
                } catch (Exception exc) {
                    logError("accept => exc: " + exc.getMessage());
                    throw exc;
                }

                if (lineNum % WRK_BUF_LEN == 0) {
                    logInfo("accept => execute batch started.");
                    pstmt.executeBatch();
                    logInfo("accept => count: " + lineNum);
                }
            } else {
                if (params.length < fieldQty) {
                    throw new Exception("Parse error.Not enough values.line:" + lineNum + " values:" + toString(params));
                } else {
                    throw new Exception("Parse error.Many values.line:" + lineNum + " values:" + toString(params));
                }
            }
            lineNum++;
        }

        if (pstmt != null) {
            logInfo("accept => execute batch started.");
            pstmt.executeBatch();
            logInfo("accept => count: " + lineNum);
            logInfo("accept => execute batch finished.");
            pstmt.close();
        }

        logInfo("accept => END.lines count:" + lineNum);

    }

    private boolean isLastCharacter(StringBuffer sBuf, int i) {
        return i == sBuf.length() - 1;
    }

    public void executeUpdate(Connection conn, String sql) throws SQLException {
        Statement stmt;
        logInfo("executeUpdate = > SQL:" + sql);
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }


    public void dropTable(Connection conn, String pTableName) throws SQLException {
        String sql = "DROP TABLE " + pTableName + " PURGE";
        executeUpdate(conn, sql);
    }

    public boolean existTable(Connection conn, String tableName) throws SQLException {

        String sql = "SELECT TABLE_NAME FROM USER_TABLES WHERE TABLE_NAME = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, tableName.toUpperCase());

        ResultSet rs = pstmt.executeQuery();

        return rs.next();
    }

    private void logInfo(String s) {
        log.info(s);
    }

    private void logError(String s) {
        log.error(s);
    }
}
