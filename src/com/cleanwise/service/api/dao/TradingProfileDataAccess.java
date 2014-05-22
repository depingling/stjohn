
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        TradingProfileDataAccess
 * Description:  This class is used to build access methods to the CLW_TRADING_PROFILE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingProfileDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>TradingProfileDataAccess</code>
 */
public class TradingProfileDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(TradingProfileDataAccess.class.getName());

    /** <code>CLW_TRADING_PROFILE</code> table name */
	/* Primary key: TRADING_PROFILE_ID */
	
    public static final String CLW_TRADING_PROFILE = "CLW_TRADING_PROFILE";
    
    /** <code>TRADING_PROFILE_ID</code> TRADING_PROFILE_ID column of table CLW_TRADING_PROFILE */
    public static final String TRADING_PROFILE_ID = "TRADING_PROFILE_ID";
    /** <code>TRADING_PARTNER_ID</code> TRADING_PARTNER_ID column of table CLW_TRADING_PROFILE */
    public static final String TRADING_PARTNER_ID = "TRADING_PARTNER_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_TRADING_PROFILE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>AUTHORIZATION_QUALIFIER</code> AUTHORIZATION_QUALIFIER column of table CLW_TRADING_PROFILE */
    public static final String AUTHORIZATION_QUALIFIER = "AUTHORIZATION_QUALIFIER";
    /** <code>AUTHORIZATION_</code> AUTHORIZATION_ column of table CLW_TRADING_PROFILE */
    public static final String AUTHORIZATION_ = "AUTHORIZATION_";
    /** <code>SECURITY_INFO_QUALIFIER</code> SECURITY_INFO_QUALIFIER column of table CLW_TRADING_PROFILE */
    public static final String SECURITY_INFO_QUALIFIER = "SECURITY_INFO_QUALIFIER";
    /** <code>SECURITY_INFO</code> SECURITY_INFO column of table CLW_TRADING_PROFILE */
    public static final String SECURITY_INFO = "SECURITY_INFO";
    /** <code>INTERCHANGE_SENDER_QUALIFIER</code> INTERCHANGE_SENDER_QUALIFIER column of table CLW_TRADING_PROFILE */
    public static final String INTERCHANGE_SENDER_QUALIFIER = "INTERCHANGE_SENDER_QUALIFIER";
    /** <code>INTERCHANGE_SENDER</code> INTERCHANGE_SENDER column of table CLW_TRADING_PROFILE */
    public static final String INTERCHANGE_SENDER = "INTERCHANGE_SENDER";
    /** <code>INTERCHANGE_RECEIVER_QUALIFIER</code> INTERCHANGE_RECEIVER_QUALIFIER column of table CLW_TRADING_PROFILE */
    public static final String INTERCHANGE_RECEIVER_QUALIFIER = "INTERCHANGE_RECEIVER_QUALIFIER";
    /** <code>INTERCHANGE_RECEIVER</code> INTERCHANGE_RECEIVER column of table CLW_TRADING_PROFILE */
    public static final String INTERCHANGE_RECEIVER = "INTERCHANGE_RECEIVER";
    /** <code>INTERCHANGE_STANDARDS_ID</code> INTERCHANGE_STANDARDS_ID column of table CLW_TRADING_PROFILE */
    public static final String INTERCHANGE_STANDARDS_ID = "INTERCHANGE_STANDARDS_ID";
    /** <code>INTERCHANGE_VERSION_NUM</code> INTERCHANGE_VERSION_NUM column of table CLW_TRADING_PROFILE */
    public static final String INTERCHANGE_VERSION_NUM = "INTERCHANGE_VERSION_NUM";
    /** <code>INTERCHANGE_CONTROL_NUM</code> INTERCHANGE_CONTROL_NUM column of table CLW_TRADING_PROFILE */
    public static final String INTERCHANGE_CONTROL_NUM = "INTERCHANGE_CONTROL_NUM";
    /** <code>ACKNOWLEDGMENT_REQUESTED</code> ACKNOWLEDGMENT_REQUESTED column of table CLW_TRADING_PROFILE */
    public static final String ACKNOWLEDGMENT_REQUESTED = "ACKNOWLEDGMENT_REQUESTED";
    /** <code>TEST_INDICATOR</code> TEST_INDICATOR column of table CLW_TRADING_PROFILE */
    public static final String TEST_INDICATOR = "TEST_INDICATOR";
    /** <code>SEGMENT_TERMINATOR</code> SEGMENT_TERMINATOR column of table CLW_TRADING_PROFILE */
    public static final String SEGMENT_TERMINATOR = "SEGMENT_TERMINATOR";
    /** <code>ELEMENT_TERMINATOR</code> ELEMENT_TERMINATOR column of table CLW_TRADING_PROFILE */
    public static final String ELEMENT_TERMINATOR = "ELEMENT_TERMINATOR";
    /** <code>SUB_ELEMENT_TERMINATOR</code> SUB_ELEMENT_TERMINATOR column of table CLW_TRADING_PROFILE */
    public static final String SUB_ELEMENT_TERMINATOR = "SUB_ELEMENT_TERMINATOR";
    /** <code>GROUP_SENDER</code> GROUP_SENDER column of table CLW_TRADING_PROFILE */
    public static final String GROUP_SENDER = "GROUP_SENDER";
    /** <code>GROUP_RECEIVER</code> GROUP_RECEIVER column of table CLW_TRADING_PROFILE */
    public static final String GROUP_RECEIVER = "GROUP_RECEIVER";
    /** <code>GROUP_CONTROL_NUM</code> GROUP_CONTROL_NUM column of table CLW_TRADING_PROFILE */
    public static final String GROUP_CONTROL_NUM = "GROUP_CONTROL_NUM";
    /** <code>RESPONSIBLE_AGENCY_CODE</code> RESPONSIBLE_AGENCY_CODE column of table CLW_TRADING_PROFILE */
    public static final String RESPONSIBLE_AGENCY_CODE = "RESPONSIBLE_AGENCY_CODE";
    /** <code>VERSION_NUM</code> VERSION_NUM column of table CLW_TRADING_PROFILE */
    public static final String VERSION_NUM = "VERSION_NUM";
    /** <code>TIME_ZONE</code> TIME_ZONE column of table CLW_TRADING_PROFILE */
    public static final String TIME_ZONE = "TIME_ZONE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_TRADING_PROFILE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_TRADING_PROFILE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_TRADING_PROFILE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_TRADING_PROFILE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public TradingProfileDataAccess()
    {
    }

    /**
     * Gets a TradingProfileData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pTradingProfileId The key requested.
     * @return new TradingProfileData()
     * @throws            SQLException
     */
    public static TradingProfileData select(Connection pCon, int pTradingProfileId)
        throws SQLException, DataNotFoundException {
        TradingProfileData x=null;
        String sql="SELECT TRADING_PROFILE_ID,TRADING_PARTNER_ID,SHORT_DESC,AUTHORIZATION_QUALIFIER,AUTHORIZATION_,SECURITY_INFO_QUALIFIER,SECURITY_INFO,INTERCHANGE_SENDER_QUALIFIER,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER_QUALIFIER,INTERCHANGE_RECEIVER,INTERCHANGE_STANDARDS_ID,INTERCHANGE_VERSION_NUM,INTERCHANGE_CONTROL_NUM,ACKNOWLEDGMENT_REQUESTED,TEST_INDICATOR,SEGMENT_TERMINATOR,ELEMENT_TERMINATOR,SUB_ELEMENT_TERMINATOR,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUM,RESPONSIBLE_AGENCY_CODE,VERSION_NUM,TIME_ZONE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PROFILE WHERE TRADING_PROFILE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pTradingProfileId=" + pTradingProfileId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pTradingProfileId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=TradingProfileData.createValue();
            
            x.setTradingProfileId(rs.getInt(1));
            x.setTradingPartnerId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setAuthorizationQualifier(rs.getString(4));
            x.setAuthorization(rs.getString(5));
            x.setSecurityInfoQualifier(rs.getString(6));
            x.setSecurityInfo(rs.getString(7));
            x.setInterchangeSenderQualifier(rs.getString(8));
            x.setInterchangeSender(rs.getString(9));
            x.setInterchangeReceiverQualifier(rs.getString(10));
            x.setInterchangeReceiver(rs.getString(11));
            x.setInterchangeStandardsId(rs.getString(12));
            x.setInterchangeVersionNum(rs.getString(13));
            x.setInterchangeControlNum(rs.getInt(14));
            x.setAcknowledgmentRequested(rs.getString(15));
            x.setTestIndicator(rs.getString(16));
            x.setSegmentTerminator(rs.getString(17));
            x.setElementTerminator(rs.getString(18));
            x.setSubElementTerminator(rs.getString(19));
            x.setGroupSender(rs.getString(20));
            x.setGroupReceiver(rs.getString(21));
            x.setGroupControlNum(rs.getInt(22));
            x.setResponsibleAgencyCode(rs.getString(23));
            x.setVersionNum(rs.getString(24));
            x.setTimeZone(rs.getString(25));
            x.setAddDate(rs.getTimestamp(26));
            x.setAddBy(rs.getString(27));
            x.setModDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("TRADING_PROFILE_ID :" + pTradingProfileId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a TradingProfileDataVector object that consists
     * of TradingProfileData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new TradingProfileDataVector()
     * @throws            SQLException
     */
    public static TradingProfileDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a TradingProfileData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_TRADING_PROFILE.TRADING_PROFILE_ID,CLW_TRADING_PROFILE.TRADING_PARTNER_ID,CLW_TRADING_PROFILE.SHORT_DESC,CLW_TRADING_PROFILE.AUTHORIZATION_QUALIFIER,CLW_TRADING_PROFILE.AUTHORIZATION_,CLW_TRADING_PROFILE.SECURITY_INFO_QUALIFIER,CLW_TRADING_PROFILE.SECURITY_INFO,CLW_TRADING_PROFILE.INTERCHANGE_SENDER_QUALIFIER,CLW_TRADING_PROFILE.INTERCHANGE_SENDER,CLW_TRADING_PROFILE.INTERCHANGE_RECEIVER_QUALIFIER,CLW_TRADING_PROFILE.INTERCHANGE_RECEIVER,CLW_TRADING_PROFILE.INTERCHANGE_STANDARDS_ID,CLW_TRADING_PROFILE.INTERCHANGE_VERSION_NUM,CLW_TRADING_PROFILE.INTERCHANGE_CONTROL_NUM,CLW_TRADING_PROFILE.ACKNOWLEDGMENT_REQUESTED,CLW_TRADING_PROFILE.TEST_INDICATOR,CLW_TRADING_PROFILE.SEGMENT_TERMINATOR,CLW_TRADING_PROFILE.ELEMENT_TERMINATOR,CLW_TRADING_PROFILE.SUB_ELEMENT_TERMINATOR,CLW_TRADING_PROFILE.GROUP_SENDER,CLW_TRADING_PROFILE.GROUP_RECEIVER,CLW_TRADING_PROFILE.GROUP_CONTROL_NUM,CLW_TRADING_PROFILE.RESPONSIBLE_AGENCY_CODE,CLW_TRADING_PROFILE.VERSION_NUM,CLW_TRADING_PROFILE.TIME_ZONE,CLW_TRADING_PROFILE.ADD_DATE,CLW_TRADING_PROFILE.ADD_BY,CLW_TRADING_PROFILE.MOD_DATE,CLW_TRADING_PROFILE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated TradingProfileData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs) throws SQLException{
         return parseResultSet(rs,0);
    }

    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@param int the offset to use which is useful when using 1 query to populate multiple objects
    *@returns a populated TradingProfileData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         TradingProfileData x = TradingProfileData.createValue();
         
         x.setTradingProfileId(rs.getInt(1+offset));
         x.setTradingPartnerId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setAuthorizationQualifier(rs.getString(4+offset));
         x.setAuthorization(rs.getString(5+offset));
         x.setSecurityInfoQualifier(rs.getString(6+offset));
         x.setSecurityInfo(rs.getString(7+offset));
         x.setInterchangeSenderQualifier(rs.getString(8+offset));
         x.setInterchangeSender(rs.getString(9+offset));
         x.setInterchangeReceiverQualifier(rs.getString(10+offset));
         x.setInterchangeReceiver(rs.getString(11+offset));
         x.setInterchangeStandardsId(rs.getString(12+offset));
         x.setInterchangeVersionNum(rs.getString(13+offset));
         x.setInterchangeControlNum(rs.getInt(14+offset));
         x.setAcknowledgmentRequested(rs.getString(15+offset));
         x.setTestIndicator(rs.getString(16+offset));
         x.setSegmentTerminator(rs.getString(17+offset));
         x.setElementTerminator(rs.getString(18+offset));
         x.setSubElementTerminator(rs.getString(19+offset));
         x.setGroupSender(rs.getString(20+offset));
         x.setGroupReceiver(rs.getString(21+offset));
         x.setGroupControlNum(rs.getInt(22+offset));
         x.setResponsibleAgencyCode(rs.getString(23+offset));
         x.setVersionNum(rs.getString(24+offset));
         x.setTimeZone(rs.getString(25+offset));
         x.setAddDate(rs.getTimestamp(26+offset));
         x.setAddBy(rs.getString(27+offset));
         x.setModDate(rs.getTimestamp(28+offset));
         x.setModBy(rs.getString(29+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the TradingProfileData Object represents.
    */
    public int getColumnCount(){
        return 29;
    }

    /**
     * Gets a TradingProfileDataVector object that consists
     * of TradingProfileData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new TradingProfileDataVector()
     * @throws            SQLException
     */
    public static TradingProfileDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT TRADING_PROFILE_ID,TRADING_PARTNER_ID,SHORT_DESC,AUTHORIZATION_QUALIFIER,AUTHORIZATION_,SECURITY_INFO_QUALIFIER,SECURITY_INFO,INTERCHANGE_SENDER_QUALIFIER,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER_QUALIFIER,INTERCHANGE_RECEIVER,INTERCHANGE_STANDARDS_ID,INTERCHANGE_VERSION_NUM,INTERCHANGE_CONTROL_NUM,ACKNOWLEDGMENT_REQUESTED,TEST_INDICATOR,SEGMENT_TERMINATOR,ELEMENT_TERMINATOR,SUB_ELEMENT_TERMINATOR,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUM,RESPONSIBLE_AGENCY_CODE,VERSION_NUM,TIME_ZONE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PROFILE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_TRADING_PROFILE.TRADING_PROFILE_ID,CLW_TRADING_PROFILE.TRADING_PARTNER_ID,CLW_TRADING_PROFILE.SHORT_DESC,CLW_TRADING_PROFILE.AUTHORIZATION_QUALIFIER,CLW_TRADING_PROFILE.AUTHORIZATION_,CLW_TRADING_PROFILE.SECURITY_INFO_QUALIFIER,CLW_TRADING_PROFILE.SECURITY_INFO,CLW_TRADING_PROFILE.INTERCHANGE_SENDER_QUALIFIER,CLW_TRADING_PROFILE.INTERCHANGE_SENDER,CLW_TRADING_PROFILE.INTERCHANGE_RECEIVER_QUALIFIER,CLW_TRADING_PROFILE.INTERCHANGE_RECEIVER,CLW_TRADING_PROFILE.INTERCHANGE_STANDARDS_ID,CLW_TRADING_PROFILE.INTERCHANGE_VERSION_NUM,CLW_TRADING_PROFILE.INTERCHANGE_CONTROL_NUM,CLW_TRADING_PROFILE.ACKNOWLEDGMENT_REQUESTED,CLW_TRADING_PROFILE.TEST_INDICATOR,CLW_TRADING_PROFILE.SEGMENT_TERMINATOR,CLW_TRADING_PROFILE.ELEMENT_TERMINATOR,CLW_TRADING_PROFILE.SUB_ELEMENT_TERMINATOR,CLW_TRADING_PROFILE.GROUP_SENDER,CLW_TRADING_PROFILE.GROUP_RECEIVER,CLW_TRADING_PROFILE.GROUP_CONTROL_NUM,CLW_TRADING_PROFILE.RESPONSIBLE_AGENCY_CODE,CLW_TRADING_PROFILE.VERSION_NUM,CLW_TRADING_PROFILE.TIME_ZONE,CLW_TRADING_PROFILE.ADD_DATE,CLW_TRADING_PROFILE.ADD_BY,CLW_TRADING_PROFILE.MOD_DATE,CLW_TRADING_PROFILE.MOD_BY FROM CLW_TRADING_PROFILE");
                where = pCriteria.getSqlClause("CLW_TRADING_PROFILE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PROFILE.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        TradingProfileDataVector v = new TradingProfileDataVector();
        while (rs.next()) {
            TradingProfileData x = TradingProfileData.createValue();
            
            x.setTradingProfileId(rs.getInt(1));
            x.setTradingPartnerId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setAuthorizationQualifier(rs.getString(4));
            x.setAuthorization(rs.getString(5));
            x.setSecurityInfoQualifier(rs.getString(6));
            x.setSecurityInfo(rs.getString(7));
            x.setInterchangeSenderQualifier(rs.getString(8));
            x.setInterchangeSender(rs.getString(9));
            x.setInterchangeReceiverQualifier(rs.getString(10));
            x.setInterchangeReceiver(rs.getString(11));
            x.setInterchangeStandardsId(rs.getString(12));
            x.setInterchangeVersionNum(rs.getString(13));
            x.setInterchangeControlNum(rs.getInt(14));
            x.setAcknowledgmentRequested(rs.getString(15));
            x.setTestIndicator(rs.getString(16));
            x.setSegmentTerminator(rs.getString(17));
            x.setElementTerminator(rs.getString(18));
            x.setSubElementTerminator(rs.getString(19));
            x.setGroupSender(rs.getString(20));
            x.setGroupReceiver(rs.getString(21));
            x.setGroupControlNum(rs.getInt(22));
            x.setResponsibleAgencyCode(rs.getString(23));
            x.setVersionNum(rs.getString(24));
            x.setTimeZone(rs.getString(25));
            x.setAddDate(rs.getTimestamp(26));
            x.setAddBy(rs.getString(27));
            x.setModDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a TradingProfileDataVector object that consists
     * of TradingProfileData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for TradingProfileData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new TradingProfileDataVector()
     * @throws            SQLException
     */
    public static TradingProfileDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        TradingProfileDataVector v = new TradingProfileDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PROFILE_ID,TRADING_PARTNER_ID,SHORT_DESC,AUTHORIZATION_QUALIFIER,AUTHORIZATION_,SECURITY_INFO_QUALIFIER,SECURITY_INFO,INTERCHANGE_SENDER_QUALIFIER,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER_QUALIFIER,INTERCHANGE_RECEIVER,INTERCHANGE_STANDARDS_ID,INTERCHANGE_VERSION_NUM,INTERCHANGE_CONTROL_NUM,ACKNOWLEDGMENT_REQUESTED,TEST_INDICATOR,SEGMENT_TERMINATOR,ELEMENT_TERMINATOR,SUB_ELEMENT_TERMINATOR,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUM,RESPONSIBLE_AGENCY_CODE,VERSION_NUM,TIME_ZONE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PROFILE WHERE TRADING_PROFILE_ID IN (");

        if ( pIdVector.size() > 0 ) {
            sqlBuf.append(pIdVector.get(0).toString());
            int vecsize = pIdVector.size();
            for ( int idx = 1; idx < vecsize; idx++ ) {
                sqlBuf.append("," + pIdVector.get(idx).toString());
            }
            sqlBuf.append(")");


            String sql = sqlBuf.toString();
            if (log.isDebugEnabled()) {
                log.debug("SQL: " + sql);
            }

            Statement stmt = pCon.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            TradingProfileData x=null;
            while (rs.next()) {
                // build the object
                x=TradingProfileData.createValue();
                
                x.setTradingProfileId(rs.getInt(1));
                x.setTradingPartnerId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setAuthorizationQualifier(rs.getString(4));
                x.setAuthorization(rs.getString(5));
                x.setSecurityInfoQualifier(rs.getString(6));
                x.setSecurityInfo(rs.getString(7));
                x.setInterchangeSenderQualifier(rs.getString(8));
                x.setInterchangeSender(rs.getString(9));
                x.setInterchangeReceiverQualifier(rs.getString(10));
                x.setInterchangeReceiver(rs.getString(11));
                x.setInterchangeStandardsId(rs.getString(12));
                x.setInterchangeVersionNum(rs.getString(13));
                x.setInterchangeControlNum(rs.getInt(14));
                x.setAcknowledgmentRequested(rs.getString(15));
                x.setTestIndicator(rs.getString(16));
                x.setSegmentTerminator(rs.getString(17));
                x.setElementTerminator(rs.getString(18));
                x.setSubElementTerminator(rs.getString(19));
                x.setGroupSender(rs.getString(20));
                x.setGroupReceiver(rs.getString(21));
                x.setGroupControlNum(rs.getInt(22));
                x.setResponsibleAgencyCode(rs.getString(23));
                x.setVersionNum(rs.getString(24));
                x.setTimeZone(rs.getString(25));
                x.setAddDate(rs.getTimestamp(26));
                x.setAddBy(rs.getString(27));
                x.setModDate(rs.getTimestamp(28));
                x.setModBy(rs.getString(29));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a TradingProfileDataVector object of all
     * TradingProfileData objects in the database.
     * @param pCon An open database connection.
     * @return new TradingProfileDataVector()
     * @throws            SQLException
     */
    public static TradingProfileDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT TRADING_PROFILE_ID,TRADING_PARTNER_ID,SHORT_DESC,AUTHORIZATION_QUALIFIER,AUTHORIZATION_,SECURITY_INFO_QUALIFIER,SECURITY_INFO,INTERCHANGE_SENDER_QUALIFIER,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER_QUALIFIER,INTERCHANGE_RECEIVER,INTERCHANGE_STANDARDS_ID,INTERCHANGE_VERSION_NUM,INTERCHANGE_CONTROL_NUM,ACKNOWLEDGMENT_REQUESTED,TEST_INDICATOR,SEGMENT_TERMINATOR,ELEMENT_TERMINATOR,SUB_ELEMENT_TERMINATOR,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUM,RESPONSIBLE_AGENCY_CODE,VERSION_NUM,TIME_ZONE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PROFILE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        TradingProfileDataVector v = new TradingProfileDataVector();
        TradingProfileData x = null;
        while (rs.next()) {
            // build the object
            x = TradingProfileData.createValue();
            
            x.setTradingProfileId(rs.getInt(1));
            x.setTradingPartnerId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setAuthorizationQualifier(rs.getString(4));
            x.setAuthorization(rs.getString(5));
            x.setSecurityInfoQualifier(rs.getString(6));
            x.setSecurityInfo(rs.getString(7));
            x.setInterchangeSenderQualifier(rs.getString(8));
            x.setInterchangeSender(rs.getString(9));
            x.setInterchangeReceiverQualifier(rs.getString(10));
            x.setInterchangeReceiver(rs.getString(11));
            x.setInterchangeStandardsId(rs.getString(12));
            x.setInterchangeVersionNum(rs.getString(13));
            x.setInterchangeControlNum(rs.getInt(14));
            x.setAcknowledgmentRequested(rs.getString(15));
            x.setTestIndicator(rs.getString(16));
            x.setSegmentTerminator(rs.getString(17));
            x.setElementTerminator(rs.getString(18));
            x.setSubElementTerminator(rs.getString(19));
            x.setGroupSender(rs.getString(20));
            x.setGroupReceiver(rs.getString(21));
            x.setGroupControlNum(rs.getInt(22));
            x.setResponsibleAgencyCode(rs.getString(23));
            x.setVersionNum(rs.getString(24));
            x.setTimeZone(rs.getString(25));
            x.setAddDate(rs.getTimestamp(26));
            x.setAddBy(rs.getString(27));
            x.setModDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * TradingProfileData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PROFILE_ID FROM CLW_TRADING_PROFILE");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of requested
     * objects in the database.
     * @param pCon An open database connection.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, String pIdName, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PROFILE");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }


    /**
     * Gets an sql statement to request ids of
     * objects in the database.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return String
     */
    public static String getSqlSelectIdOnly(String pIdName, DBCriteria pCriteria)
    {
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PROFILE");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a TradingProfileData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new TradingProfileData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingProfileData insert(Connection pCon, TradingProfileData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_TRADING_PROFILE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_TRADING_PROFILE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setTradingProfileId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_TRADING_PROFILE (TRADING_PROFILE_ID,TRADING_PARTNER_ID,SHORT_DESC,AUTHORIZATION_QUALIFIER,AUTHORIZATION_,SECURITY_INFO_QUALIFIER,SECURITY_INFO,INTERCHANGE_SENDER_QUALIFIER,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER_QUALIFIER,INTERCHANGE_RECEIVER,INTERCHANGE_STANDARDS_ID,INTERCHANGE_VERSION_NUM,INTERCHANGE_CONTROL_NUM,ACKNOWLEDGMENT_REQUESTED,TEST_INDICATOR,SEGMENT_TERMINATOR,ELEMENT_TERMINATOR,SUB_ELEMENT_TERMINATOR,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUM,RESPONSIBLE_AGENCY_CODE,VERSION_NUM,TIME_ZONE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getTradingProfileId());
        pstmt.setInt(2,pData.getTradingPartnerId());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getAuthorizationQualifier());
        pstmt.setString(5,pData.getAuthorization());
        pstmt.setString(6,pData.getSecurityInfoQualifier());
        pstmt.setString(7,pData.getSecurityInfo());
        pstmt.setString(8,pData.getInterchangeSenderQualifier());
        pstmt.setString(9,pData.getInterchangeSender());
        pstmt.setString(10,pData.getInterchangeReceiverQualifier());
        pstmt.setString(11,pData.getInterchangeReceiver());
        pstmt.setString(12,pData.getInterchangeStandardsId());
        pstmt.setString(13,pData.getInterchangeVersionNum());
        pstmt.setInt(14,pData.getInterchangeControlNum());
        pstmt.setString(15,pData.getAcknowledgmentRequested());
        pstmt.setString(16,pData.getTestIndicator());
        pstmt.setString(17,pData.getSegmentTerminator());
        pstmt.setString(18,pData.getElementTerminator());
        pstmt.setString(19,pData.getSubElementTerminator());
        pstmt.setString(20,pData.getGroupSender());
        pstmt.setString(21,pData.getGroupReceiver());
        pstmt.setInt(22,pData.getGroupControlNum());
        pstmt.setString(23,pData.getResponsibleAgencyCode());
        pstmt.setString(24,pData.getVersionNum());
        pstmt.setString(25,pData.getTimeZone());
        pstmt.setTimestamp(26,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(27,pData.getAddBy());
        pstmt.setTimestamp(28,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(29,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PROFILE_ID="+pData.getTradingProfileId());
            log.debug("SQL:   TRADING_PARTNER_ID="+pData.getTradingPartnerId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   AUTHORIZATION_QUALIFIER="+pData.getAuthorizationQualifier());
            log.debug("SQL:   AUTHORIZATION_="+pData.getAuthorization());
            log.debug("SQL:   SECURITY_INFO_QUALIFIER="+pData.getSecurityInfoQualifier());
            log.debug("SQL:   SECURITY_INFO="+pData.getSecurityInfo());
            log.debug("SQL:   INTERCHANGE_SENDER_QUALIFIER="+pData.getInterchangeSenderQualifier());
            log.debug("SQL:   INTERCHANGE_SENDER="+pData.getInterchangeSender());
            log.debug("SQL:   INTERCHANGE_RECEIVER_QUALIFIER="+pData.getInterchangeReceiverQualifier());
            log.debug("SQL:   INTERCHANGE_RECEIVER="+pData.getInterchangeReceiver());
            log.debug("SQL:   INTERCHANGE_STANDARDS_ID="+pData.getInterchangeStandardsId());
            log.debug("SQL:   INTERCHANGE_VERSION_NUM="+pData.getInterchangeVersionNum());
            log.debug("SQL:   INTERCHANGE_CONTROL_NUM="+pData.getInterchangeControlNum());
            log.debug("SQL:   ACKNOWLEDGMENT_REQUESTED="+pData.getAcknowledgmentRequested());
            log.debug("SQL:   TEST_INDICATOR="+pData.getTestIndicator());
            log.debug("SQL:   SEGMENT_TERMINATOR="+pData.getSegmentTerminator());
            log.debug("SQL:   ELEMENT_TERMINATOR="+pData.getElementTerminator());
            log.debug("SQL:   SUB_ELEMENT_TERMINATOR="+pData.getSubElementTerminator());
            log.debug("SQL:   GROUP_SENDER="+pData.getGroupSender());
            log.debug("SQL:   GROUP_RECEIVER="+pData.getGroupReceiver());
            log.debug("SQL:   GROUP_CONTROL_NUM="+pData.getGroupControlNum());
            log.debug("SQL:   RESPONSIBLE_AGENCY_CODE="+pData.getResponsibleAgencyCode());
            log.debug("SQL:   VERSION_NUM="+pData.getVersionNum());
            log.debug("SQL:   TIME_ZONE="+pData.getTimeZone());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setTradingProfileId(0);
        exceptionMessage=e.getMessage();
        }
        finally{
        pstmt.close();
        }

        if(exceptionMessage!=null) {
                 throw new SQLException(exceptionMessage);
        }

        return pData;
    }

    /**
     * Updates a TradingProfileData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingProfileData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_TRADING_PROFILE SET TRADING_PARTNER_ID = ?,SHORT_DESC = ?,AUTHORIZATION_QUALIFIER = ?,AUTHORIZATION_ = ?,SECURITY_INFO_QUALIFIER = ?,SECURITY_INFO = ?,INTERCHANGE_SENDER_QUALIFIER = ?,INTERCHANGE_SENDER = ?,INTERCHANGE_RECEIVER_QUALIFIER = ?,INTERCHANGE_RECEIVER = ?,INTERCHANGE_STANDARDS_ID = ?,INTERCHANGE_VERSION_NUM = ?,INTERCHANGE_CONTROL_NUM = ?,ACKNOWLEDGMENT_REQUESTED = ?,TEST_INDICATOR = ?,SEGMENT_TERMINATOR = ?,ELEMENT_TERMINATOR = ?,SUB_ELEMENT_TERMINATOR = ?,GROUP_SENDER = ?,GROUP_RECEIVER = ?,GROUP_CONTROL_NUM = ?,RESPONSIBLE_AGENCY_CODE = ?,VERSION_NUM = ?,TIME_ZONE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE TRADING_PROFILE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getTradingPartnerId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getAuthorizationQualifier());
        pstmt.setString(i++,pData.getAuthorization());
        pstmt.setString(i++,pData.getSecurityInfoQualifier());
        pstmt.setString(i++,pData.getSecurityInfo());
        pstmt.setString(i++,pData.getInterchangeSenderQualifier());
        pstmt.setString(i++,pData.getInterchangeSender());
        pstmt.setString(i++,pData.getInterchangeReceiverQualifier());
        pstmt.setString(i++,pData.getInterchangeReceiver());
        pstmt.setString(i++,pData.getInterchangeStandardsId());
        pstmt.setString(i++,pData.getInterchangeVersionNum());
        pstmt.setInt(i++,pData.getInterchangeControlNum());
        pstmt.setString(i++,pData.getAcknowledgmentRequested());
        pstmt.setString(i++,pData.getTestIndicator());
        pstmt.setString(i++,pData.getSegmentTerminator());
        pstmt.setString(i++,pData.getElementTerminator());
        pstmt.setString(i++,pData.getSubElementTerminator());
        pstmt.setString(i++,pData.getGroupSender());
        pstmt.setString(i++,pData.getGroupReceiver());
        pstmt.setInt(i++,pData.getGroupControlNum());
        pstmt.setString(i++,pData.getResponsibleAgencyCode());
        pstmt.setString(i++,pData.getVersionNum());
        pstmt.setString(i++,pData.getTimeZone());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getTradingProfileId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PARTNER_ID="+pData.getTradingPartnerId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   AUTHORIZATION_QUALIFIER="+pData.getAuthorizationQualifier());
            log.debug("SQL:   AUTHORIZATION_="+pData.getAuthorization());
            log.debug("SQL:   SECURITY_INFO_QUALIFIER="+pData.getSecurityInfoQualifier());
            log.debug("SQL:   SECURITY_INFO="+pData.getSecurityInfo());
            log.debug("SQL:   INTERCHANGE_SENDER_QUALIFIER="+pData.getInterchangeSenderQualifier());
            log.debug("SQL:   INTERCHANGE_SENDER="+pData.getInterchangeSender());
            log.debug("SQL:   INTERCHANGE_RECEIVER_QUALIFIER="+pData.getInterchangeReceiverQualifier());
            log.debug("SQL:   INTERCHANGE_RECEIVER="+pData.getInterchangeReceiver());
            log.debug("SQL:   INTERCHANGE_STANDARDS_ID="+pData.getInterchangeStandardsId());
            log.debug("SQL:   INTERCHANGE_VERSION_NUM="+pData.getInterchangeVersionNum());
            log.debug("SQL:   INTERCHANGE_CONTROL_NUM="+pData.getInterchangeControlNum());
            log.debug("SQL:   ACKNOWLEDGMENT_REQUESTED="+pData.getAcknowledgmentRequested());
            log.debug("SQL:   TEST_INDICATOR="+pData.getTestIndicator());
            log.debug("SQL:   SEGMENT_TERMINATOR="+pData.getSegmentTerminator());
            log.debug("SQL:   ELEMENT_TERMINATOR="+pData.getElementTerminator());
            log.debug("SQL:   SUB_ELEMENT_TERMINATOR="+pData.getSubElementTerminator());
            log.debug("SQL:   GROUP_SENDER="+pData.getGroupSender());
            log.debug("SQL:   GROUP_RECEIVER="+pData.getGroupReceiver());
            log.debug("SQL:   GROUP_CONTROL_NUM="+pData.getGroupControlNum());
            log.debug("SQL:   RESPONSIBLE_AGENCY_CODE="+pData.getResponsibleAgencyCode());
            log.debug("SQL:   VERSION_NUM="+pData.getVersionNum());
            log.debug("SQL:   TIME_ZONE="+pData.getTimeZone());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a TradingProfileData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingProfileId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingProfileId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_TRADING_PROFILE WHERE TRADING_PROFILE_ID = " + pTradingProfileId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes TradingProfileData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_TRADING_PROFILE");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
    }

    /**
     * Inserts a TradingProfileData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, TradingProfileData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_TRADING_PROFILE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "TRADING_PROFILE_ID,TRADING_PARTNER_ID,SHORT_DESC,AUTHORIZATION_QUALIFIER,AUTHORIZATION_,SECURITY_INFO_QUALIFIER,SECURITY_INFO,INTERCHANGE_SENDER_QUALIFIER,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER_QUALIFIER,INTERCHANGE_RECEIVER,INTERCHANGE_STANDARDS_ID,INTERCHANGE_VERSION_NUM,INTERCHANGE_CONTROL_NUM,ACKNOWLEDGMENT_REQUESTED,TEST_INDICATOR,SEGMENT_TERMINATOR,ELEMENT_TERMINATOR,SUB_ELEMENT_TERMINATOR,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUM,RESPONSIBLE_AGENCY_CODE,VERSION_NUM,TIME_ZONE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getTradingProfileId());
        pstmt.setInt(2+4,pData.getTradingPartnerId());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getAuthorizationQualifier());
        pstmt.setString(5+4,pData.getAuthorization());
        pstmt.setString(6+4,pData.getSecurityInfoQualifier());
        pstmt.setString(7+4,pData.getSecurityInfo());
        pstmt.setString(8+4,pData.getInterchangeSenderQualifier());
        pstmt.setString(9+4,pData.getInterchangeSender());
        pstmt.setString(10+4,pData.getInterchangeReceiverQualifier());
        pstmt.setString(11+4,pData.getInterchangeReceiver());
        pstmt.setString(12+4,pData.getInterchangeStandardsId());
        pstmt.setString(13+4,pData.getInterchangeVersionNum());
        pstmt.setInt(14+4,pData.getInterchangeControlNum());
        pstmt.setString(15+4,pData.getAcknowledgmentRequested());
        pstmt.setString(16+4,pData.getTestIndicator());
        pstmt.setString(17+4,pData.getSegmentTerminator());
        pstmt.setString(18+4,pData.getElementTerminator());
        pstmt.setString(19+4,pData.getSubElementTerminator());
        pstmt.setString(20+4,pData.getGroupSender());
        pstmt.setString(21+4,pData.getGroupReceiver());
        pstmt.setInt(22+4,pData.getGroupControlNum());
        pstmt.setString(23+4,pData.getResponsibleAgencyCode());
        pstmt.setString(24+4,pData.getVersionNum());
        pstmt.setString(25+4,pData.getTimeZone());
        pstmt.setTimestamp(26+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(27+4,pData.getAddBy());
        pstmt.setTimestamp(28+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(29+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a TradingProfileData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new TradingProfileData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingProfileData insert(Connection pCon, TradingProfileData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a TradingProfileData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingProfileData pData, boolean pLogFl)
        throws SQLException {
        TradingProfileData oldData = null;
        if(pLogFl) {
          int id = pData.getTradingProfileId();
          try {
          oldData = TradingProfileDataAccess.select(pCon,id);
          } catch(DataNotFoundException exc) {}
        }
        int n = update(pCon,pData);
        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, oldData, millis, "U", "O");
          insertLog(pCon, pData, millis, "U", "N");
        }
        return n;
    }

    /**
     * Deletes a TradingProfileData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingProfileId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingProfileId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_TRADING_PROFILE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PROFILE d WHERE TRADING_PROFILE_ID = " + pTradingProfileId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pTradingProfileId);
        return n;
     }

    /**
     * Deletes TradingProfileData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria, boolean pLogFl)
        throws SQLException {
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          StringBuffer sqlBuf =
             new StringBuffer("INSERT INTO LCLW_TRADING_PROFILE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PROFILE d ");
          String where = pCriteria.getSqlClause();
          sqlBuf.append(" WHERE ");
          sqlBuf.append(where);

          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlBuf.toString());
          stmt.close();
        }
        int n = remove(pCon,pCriteria);
        return n;
    }
///////////////////////////////////////////////
}

