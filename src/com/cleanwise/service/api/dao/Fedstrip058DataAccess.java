
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        Fedstrip058DataAccess
 * Description:  This class is used to build access methods to the CLW_FEDSTRIP_058 table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.Fedstrip058Data;
import com.cleanwise.service.api.value.Fedstrip058DataVector;
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
 * <code>Fedstrip058DataAccess</code>
 */
public class Fedstrip058DataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(Fedstrip058DataAccess.class.getName());

    /** <code>CLW_FEDSTRIP_058</code> table name */
	/* Primary key: FEDSTRIP_058_ID */
	
    public static final String CLW_FEDSTRIP_058 = "CLW_FEDSTRIP_058";
    
    /** <code>FEDSTRIP_058_ID</code> FEDSTRIP_058_ID column of table CLW_FEDSTRIP_058 */
    public static final String FEDSTRIP_058_ID = "FEDSTRIP_058_ID";
    /** <code>FEDSTRIP</code> FEDSTRIP column of table CLW_FEDSTRIP_058 */
    public static final String FEDSTRIP = "FEDSTRIP";
    /** <code>FINANCE</code> FINANCE column of table CLW_FEDSTRIP_058 */
    public static final String FINANCE = "FINANCE";
    /** <code>BA_CODE</code> BA_CODE column of table CLW_FEDSTRIP_058 */
    public static final String BA_CODE = "BA_CODE";
    /** <code>DISTRICT_CODE</code> DISTRICT_CODE column of table CLW_FEDSTRIP_058 */
    public static final String DISTRICT_CODE = "DISTRICT_CODE";
    /** <code>CAG</code> CAG column of table CLW_FEDSTRIP_058 */
    public static final String CAG = "CAG";
    /** <code>ADDRESS_TYPE_CODE</code> ADDRESS_TYPE_CODE column of table CLW_FEDSTRIP_058 */
    public static final String ADDRESS_TYPE_CODE = "ADDRESS_TYPE_CODE";
    /** <code>ADDRESS_LINE1</code> ADDRESS_LINE1 column of table CLW_FEDSTRIP_058 */
    public static final String ADDRESS_LINE1 = "ADDRESS_LINE1";
    /** <code>ADDRESS_LINE2</code> ADDRESS_LINE2 column of table CLW_FEDSTRIP_058 */
    public static final String ADDRESS_LINE2 = "ADDRESS_LINE2";
    /** <code>ADDRESS_LINE3</code> ADDRESS_LINE3 column of table CLW_FEDSTRIP_058 */
    public static final String ADDRESS_LINE3 = "ADDRESS_LINE3";
    /** <code>CITY</code> CITY column of table CLW_FEDSTRIP_058 */
    public static final String CITY = "CITY";
    /** <code>STATE</code> STATE column of table CLW_FEDSTRIP_058 */
    public static final String STATE = "STATE";
    /** <code>ZIP</code> ZIP column of table CLW_FEDSTRIP_058 */
    public static final String ZIP = "ZIP";
    /** <code>NMICS_SITE_CODE</code> NMICS_SITE_CODE column of table CLW_FEDSTRIP_058 */
    public static final String NMICS_SITE_CODE = "NMICS_SITE_CODE";
    /** <code>NMICS_SUBSITE_CODE</code> NMICS_SUBSITE_CODE column of table CLW_FEDSTRIP_058 */
    public static final String NMICS_SUBSITE_CODE = "NMICS_SUBSITE_CODE";
    /** <code>PART_BUY_AUTH_INDICATOR</code> PART_BUY_AUTH_INDICATOR column of table CLW_FEDSTRIP_058 */
    public static final String PART_BUY_AUTH_INDICATOR = "PART_BUY_AUTH_INDICATOR";
    /** <code>PARTS_FINANCE_NUMBER</code> PARTS_FINANCE_NUMBER column of table CLW_FEDSTRIP_058 */
    public static final String PARTS_FINANCE_NUMBER = "PARTS_FINANCE_NUMBER";
    /** <code>CONTACT_PHONE</code> CONTACT_PHONE column of table CLW_FEDSTRIP_058 */
    public static final String CONTACT_PHONE = "CONTACT_PHONE";
    /** <code>CONTACT_FAX</code> CONTACT_FAX column of table CLW_FEDSTRIP_058 */
    public static final String CONTACT_FAX = "CONTACT_FAX";
    /** <code>PARTS_FACILITY_TYPE_CODE</code> PARTS_FACILITY_TYPE_CODE column of table CLW_FEDSTRIP_058 */
    public static final String PARTS_FACILITY_TYPE_CODE = "PARTS_FACILITY_TYPE_CODE";
    /** <code>SCF_CODE</code> SCF_CODE column of table CLW_FEDSTRIP_058 */
    public static final String SCF_CODE = "SCF_CODE";
    /** <code>CUSTOMER_NAME</code> CUSTOMER_NAME column of table CLW_FEDSTRIP_058 */
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    /** <code>DATED_CHANGED</code> DATED_CHANGED column of table CLW_FEDSTRIP_058 */
    public static final String DATED_CHANGED = "DATED_CHANGED";
    /** <code>CHANGE_CODE</code> CHANGE_CODE column of table CLW_FEDSTRIP_058 */
    public static final String CHANGE_CODE = "CHANGE_CODE";
    /** <code>ERROR_CODE</code> ERROR_CODE column of table CLW_FEDSTRIP_058 */
    public static final String ERROR_CODE = "ERROR_CODE";
    /** <code>ERROR_MESSAGE</code> ERROR_MESSAGE column of table CLW_FEDSTRIP_058 */
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    /** <code>FILE_NAME</code> FILE_NAME column of table CLW_FEDSTRIP_058 */
    public static final String FILE_NAME = "FILE_NAME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_FEDSTRIP_058 */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_FEDSTRIP_058 */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_FEDSTRIP_058 */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_FEDSTRIP_058 */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public Fedstrip058DataAccess()
    {
    }

    /**
     * Gets a Fedstrip058Data object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFedstrip058Id The key requested.
     * @return new Fedstrip058Data()
     * @throws            SQLException
     */
    public static Fedstrip058Data select(Connection pCon, int pFedstrip058Id)
        throws SQLException, DataNotFoundException {
        Fedstrip058Data x=null;
        String sql="SELECT FEDSTRIP_058_ID,FEDSTRIP,FINANCE,BA_CODE,DISTRICT_CODE,CAG,ADDRESS_TYPE_CODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,CITY,STATE,ZIP,NMICS_SITE_CODE,NMICS_SUBSITE_CODE,PART_BUY_AUTH_INDICATOR,PARTS_FINANCE_NUMBER,CONTACT_PHONE,CONTACT_FAX,PARTS_FACILITY_TYPE_CODE,SCF_CODE,CUSTOMER_NAME,DATED_CHANGED,CHANGE_CODE,ERROR_CODE,ERROR_MESSAGE,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FEDSTRIP_058 WHERE FEDSTRIP_058_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFedstrip058Id=" + pFedstrip058Id);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFedstrip058Id);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=Fedstrip058Data.createValue();
            
            x.setFedstrip058Id(rs.getInt(1));
            x.setFedstrip(rs.getString(2));
            x.setFinance(rs.getString(3));
            x.setBaCode(rs.getString(4));
            x.setDistrictCode(rs.getString(5));
            x.setCag(rs.getString(6));
            x.setAddressTypeCode(rs.getString(7));
            x.setAddressLine1(rs.getString(8));
            x.setAddressLine2(rs.getString(9));
            x.setAddressLine3(rs.getString(10));
            x.setCity(rs.getString(11));
            x.setState(rs.getString(12));
            x.setZip(rs.getString(13));
            x.setNmicsSiteCode(rs.getString(14));
            x.setNmicsSubsiteCode(rs.getString(15));
            x.setPartBuyAuthIndicator(rs.getString(16));
            x.setPartsFinanceNumber(rs.getString(17));
            x.setContactPhone(rs.getString(18));
            x.setContactFax(rs.getString(19));
            x.setPartsFacilityTypeCode(rs.getString(20));
            x.setScfCode(rs.getString(21));
            x.setCustomerName(rs.getString(22));
            x.setDatedChanged(rs.getString(23));
            x.setChangeCode(rs.getString(24));
            x.setErrorCode(rs.getString(25));
            x.setErrorMessage(rs.getString(26));
            x.setFileName(rs.getString(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setAddBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));
            x.setModBy(rs.getString(31));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FEDSTRIP_058_ID :" + pFedstrip058Id);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a Fedstrip058DataVector object that consists
     * of Fedstrip058Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new Fedstrip058DataVector()
     * @throws            SQLException
     */
    public static Fedstrip058DataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a Fedstrip058Data Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FEDSTRIP_058.FEDSTRIP_058_ID,CLW_FEDSTRIP_058.FEDSTRIP,CLW_FEDSTRIP_058.FINANCE,CLW_FEDSTRIP_058.BA_CODE,CLW_FEDSTRIP_058.DISTRICT_CODE,CLW_FEDSTRIP_058.CAG,CLW_FEDSTRIP_058.ADDRESS_TYPE_CODE,CLW_FEDSTRIP_058.ADDRESS_LINE1,CLW_FEDSTRIP_058.ADDRESS_LINE2,CLW_FEDSTRIP_058.ADDRESS_LINE3,CLW_FEDSTRIP_058.CITY,CLW_FEDSTRIP_058.STATE,CLW_FEDSTRIP_058.ZIP,CLW_FEDSTRIP_058.NMICS_SITE_CODE,CLW_FEDSTRIP_058.NMICS_SUBSITE_CODE,CLW_FEDSTRIP_058.PART_BUY_AUTH_INDICATOR,CLW_FEDSTRIP_058.PARTS_FINANCE_NUMBER,CLW_FEDSTRIP_058.CONTACT_PHONE,CLW_FEDSTRIP_058.CONTACT_FAX,CLW_FEDSTRIP_058.PARTS_FACILITY_TYPE_CODE,CLW_FEDSTRIP_058.SCF_CODE,CLW_FEDSTRIP_058.CUSTOMER_NAME,CLW_FEDSTRIP_058.DATED_CHANGED,CLW_FEDSTRIP_058.CHANGE_CODE,CLW_FEDSTRIP_058.ERROR_CODE,CLW_FEDSTRIP_058.ERROR_MESSAGE,CLW_FEDSTRIP_058.FILE_NAME,CLW_FEDSTRIP_058.ADD_DATE,CLW_FEDSTRIP_058.ADD_BY,CLW_FEDSTRIP_058.MOD_DATE,CLW_FEDSTRIP_058.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated Fedstrip058Data Object.
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
    *@returns a populated Fedstrip058Data Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         Fedstrip058Data x = Fedstrip058Data.createValue();
         
         x.setFedstrip058Id(rs.getInt(1+offset));
         x.setFedstrip(rs.getString(2+offset));
         x.setFinance(rs.getString(3+offset));
         x.setBaCode(rs.getString(4+offset));
         x.setDistrictCode(rs.getString(5+offset));
         x.setCag(rs.getString(6+offset));
         x.setAddressTypeCode(rs.getString(7+offset));
         x.setAddressLine1(rs.getString(8+offset));
         x.setAddressLine2(rs.getString(9+offset));
         x.setAddressLine3(rs.getString(10+offset));
         x.setCity(rs.getString(11+offset));
         x.setState(rs.getString(12+offset));
         x.setZip(rs.getString(13+offset));
         x.setNmicsSiteCode(rs.getString(14+offset));
         x.setNmicsSubsiteCode(rs.getString(15+offset));
         x.setPartBuyAuthIndicator(rs.getString(16+offset));
         x.setPartsFinanceNumber(rs.getString(17+offset));
         x.setContactPhone(rs.getString(18+offset));
         x.setContactFax(rs.getString(19+offset));
         x.setPartsFacilityTypeCode(rs.getString(20+offset));
         x.setScfCode(rs.getString(21+offset));
         x.setCustomerName(rs.getString(22+offset));
         x.setDatedChanged(rs.getString(23+offset));
         x.setChangeCode(rs.getString(24+offset));
         x.setErrorCode(rs.getString(25+offset));
         x.setErrorMessage(rs.getString(26+offset));
         x.setFileName(rs.getString(27+offset));
         x.setAddDate(rs.getTimestamp(28+offset));
         x.setAddBy(rs.getString(29+offset));
         x.setModDate(rs.getTimestamp(30+offset));
         x.setModBy(rs.getString(31+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the Fedstrip058Data Object represents.
    */
    public int getColumnCount(){
        return 31;
    }

    /**
     * Gets a Fedstrip058DataVector object that consists
     * of Fedstrip058Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new Fedstrip058DataVector()
     * @throws            SQLException
     */
    public static Fedstrip058DataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FEDSTRIP_058_ID,FEDSTRIP,FINANCE,BA_CODE,DISTRICT_CODE,CAG,ADDRESS_TYPE_CODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,CITY,STATE,ZIP,NMICS_SITE_CODE,NMICS_SUBSITE_CODE,PART_BUY_AUTH_INDICATOR,PARTS_FINANCE_NUMBER,CONTACT_PHONE,CONTACT_FAX,PARTS_FACILITY_TYPE_CODE,SCF_CODE,CUSTOMER_NAME,DATED_CHANGED,CHANGE_CODE,ERROR_CODE,ERROR_MESSAGE,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FEDSTRIP_058");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FEDSTRIP_058.FEDSTRIP_058_ID,CLW_FEDSTRIP_058.FEDSTRIP,CLW_FEDSTRIP_058.FINANCE,CLW_FEDSTRIP_058.BA_CODE,CLW_FEDSTRIP_058.DISTRICT_CODE,CLW_FEDSTRIP_058.CAG,CLW_FEDSTRIP_058.ADDRESS_TYPE_CODE,CLW_FEDSTRIP_058.ADDRESS_LINE1,CLW_FEDSTRIP_058.ADDRESS_LINE2,CLW_FEDSTRIP_058.ADDRESS_LINE3,CLW_FEDSTRIP_058.CITY,CLW_FEDSTRIP_058.STATE,CLW_FEDSTRIP_058.ZIP,CLW_FEDSTRIP_058.NMICS_SITE_CODE,CLW_FEDSTRIP_058.NMICS_SUBSITE_CODE,CLW_FEDSTRIP_058.PART_BUY_AUTH_INDICATOR,CLW_FEDSTRIP_058.PARTS_FINANCE_NUMBER,CLW_FEDSTRIP_058.CONTACT_PHONE,CLW_FEDSTRIP_058.CONTACT_FAX,CLW_FEDSTRIP_058.PARTS_FACILITY_TYPE_CODE,CLW_FEDSTRIP_058.SCF_CODE,CLW_FEDSTRIP_058.CUSTOMER_NAME,CLW_FEDSTRIP_058.DATED_CHANGED,CLW_FEDSTRIP_058.CHANGE_CODE,CLW_FEDSTRIP_058.ERROR_CODE,CLW_FEDSTRIP_058.ERROR_MESSAGE,CLW_FEDSTRIP_058.FILE_NAME,CLW_FEDSTRIP_058.ADD_DATE,CLW_FEDSTRIP_058.ADD_BY,CLW_FEDSTRIP_058.MOD_DATE,CLW_FEDSTRIP_058.MOD_BY FROM CLW_FEDSTRIP_058");
                where = pCriteria.getSqlClause("CLW_FEDSTRIP_058");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_FEDSTRIP_058.equals(otherTable)){
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
        Fedstrip058DataVector v = new Fedstrip058DataVector();
        while (rs.next()) {
            Fedstrip058Data x = Fedstrip058Data.createValue();
            
            x.setFedstrip058Id(rs.getInt(1));
            x.setFedstrip(rs.getString(2));
            x.setFinance(rs.getString(3));
            x.setBaCode(rs.getString(4));
            x.setDistrictCode(rs.getString(5));
            x.setCag(rs.getString(6));
            x.setAddressTypeCode(rs.getString(7));
            x.setAddressLine1(rs.getString(8));
            x.setAddressLine2(rs.getString(9));
            x.setAddressLine3(rs.getString(10));
            x.setCity(rs.getString(11));
            x.setState(rs.getString(12));
            x.setZip(rs.getString(13));
            x.setNmicsSiteCode(rs.getString(14));
            x.setNmicsSubsiteCode(rs.getString(15));
            x.setPartBuyAuthIndicator(rs.getString(16));
            x.setPartsFinanceNumber(rs.getString(17));
            x.setContactPhone(rs.getString(18));
            x.setContactFax(rs.getString(19));
            x.setPartsFacilityTypeCode(rs.getString(20));
            x.setScfCode(rs.getString(21));
            x.setCustomerName(rs.getString(22));
            x.setDatedChanged(rs.getString(23));
            x.setChangeCode(rs.getString(24));
            x.setErrorCode(rs.getString(25));
            x.setErrorMessage(rs.getString(26));
            x.setFileName(rs.getString(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setAddBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));
            x.setModBy(rs.getString(31));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a Fedstrip058DataVector object that consists
     * of Fedstrip058Data objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for Fedstrip058Data
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new Fedstrip058DataVector()
     * @throws            SQLException
     */
    public static Fedstrip058DataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        Fedstrip058DataVector v = new Fedstrip058DataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FEDSTRIP_058_ID,FEDSTRIP,FINANCE,BA_CODE,DISTRICT_CODE,CAG,ADDRESS_TYPE_CODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,CITY,STATE,ZIP,NMICS_SITE_CODE,NMICS_SUBSITE_CODE,PART_BUY_AUTH_INDICATOR,PARTS_FINANCE_NUMBER,CONTACT_PHONE,CONTACT_FAX,PARTS_FACILITY_TYPE_CODE,SCF_CODE,CUSTOMER_NAME,DATED_CHANGED,CHANGE_CODE,ERROR_CODE,ERROR_MESSAGE,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FEDSTRIP_058 WHERE FEDSTRIP_058_ID IN (");

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
            Fedstrip058Data x=null;
            while (rs.next()) {
                // build the object
                x=Fedstrip058Data.createValue();
                
                x.setFedstrip058Id(rs.getInt(1));
                x.setFedstrip(rs.getString(2));
                x.setFinance(rs.getString(3));
                x.setBaCode(rs.getString(4));
                x.setDistrictCode(rs.getString(5));
                x.setCag(rs.getString(6));
                x.setAddressTypeCode(rs.getString(7));
                x.setAddressLine1(rs.getString(8));
                x.setAddressLine2(rs.getString(9));
                x.setAddressLine3(rs.getString(10));
                x.setCity(rs.getString(11));
                x.setState(rs.getString(12));
                x.setZip(rs.getString(13));
                x.setNmicsSiteCode(rs.getString(14));
                x.setNmicsSubsiteCode(rs.getString(15));
                x.setPartBuyAuthIndicator(rs.getString(16));
                x.setPartsFinanceNumber(rs.getString(17));
                x.setContactPhone(rs.getString(18));
                x.setContactFax(rs.getString(19));
                x.setPartsFacilityTypeCode(rs.getString(20));
                x.setScfCode(rs.getString(21));
                x.setCustomerName(rs.getString(22));
                x.setDatedChanged(rs.getString(23));
                x.setChangeCode(rs.getString(24));
                x.setErrorCode(rs.getString(25));
                x.setErrorMessage(rs.getString(26));
                x.setFileName(rs.getString(27));
                x.setAddDate(rs.getTimestamp(28));
                x.setAddBy(rs.getString(29));
                x.setModDate(rs.getTimestamp(30));
                x.setModBy(rs.getString(31));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a Fedstrip058DataVector object of all
     * Fedstrip058Data objects in the database.
     * @param pCon An open database connection.
     * @return new Fedstrip058DataVector()
     * @throws            SQLException
     */
    public static Fedstrip058DataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FEDSTRIP_058_ID,FEDSTRIP,FINANCE,BA_CODE,DISTRICT_CODE,CAG,ADDRESS_TYPE_CODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,CITY,STATE,ZIP,NMICS_SITE_CODE,NMICS_SUBSITE_CODE,PART_BUY_AUTH_INDICATOR,PARTS_FINANCE_NUMBER,CONTACT_PHONE,CONTACT_FAX,PARTS_FACILITY_TYPE_CODE,SCF_CODE,CUSTOMER_NAME,DATED_CHANGED,CHANGE_CODE,ERROR_CODE,ERROR_MESSAGE,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FEDSTRIP_058";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        Fedstrip058DataVector v = new Fedstrip058DataVector();
        Fedstrip058Data x = null;
        while (rs.next()) {
            // build the object
            x = Fedstrip058Data.createValue();
            
            x.setFedstrip058Id(rs.getInt(1));
            x.setFedstrip(rs.getString(2));
            x.setFinance(rs.getString(3));
            x.setBaCode(rs.getString(4));
            x.setDistrictCode(rs.getString(5));
            x.setCag(rs.getString(6));
            x.setAddressTypeCode(rs.getString(7));
            x.setAddressLine1(rs.getString(8));
            x.setAddressLine2(rs.getString(9));
            x.setAddressLine3(rs.getString(10));
            x.setCity(rs.getString(11));
            x.setState(rs.getString(12));
            x.setZip(rs.getString(13));
            x.setNmicsSiteCode(rs.getString(14));
            x.setNmicsSubsiteCode(rs.getString(15));
            x.setPartBuyAuthIndicator(rs.getString(16));
            x.setPartsFinanceNumber(rs.getString(17));
            x.setContactPhone(rs.getString(18));
            x.setContactFax(rs.getString(19));
            x.setPartsFacilityTypeCode(rs.getString(20));
            x.setScfCode(rs.getString(21));
            x.setCustomerName(rs.getString(22));
            x.setDatedChanged(rs.getString(23));
            x.setChangeCode(rs.getString(24));
            x.setErrorCode(rs.getString(25));
            x.setErrorMessage(rs.getString(26));
            x.setFileName(rs.getString(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setAddBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));
            x.setModBy(rs.getString(31));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * Fedstrip058Data objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FEDSTRIP_058_ID FROM CLW_FEDSTRIP_058");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FEDSTRIP_058");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FEDSTRIP_058");
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
     * Inserts a Fedstrip058Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A Fedstrip058Data object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new Fedstrip058Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static Fedstrip058Data insert(Connection pCon, Fedstrip058Data pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FEDSTRIP_058_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FEDSTRIP_058_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFedstrip058Id(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FEDSTRIP_058 (FEDSTRIP_058_ID,FEDSTRIP,FINANCE,BA_CODE,DISTRICT_CODE,CAG,ADDRESS_TYPE_CODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,CITY,STATE,ZIP,NMICS_SITE_CODE,NMICS_SUBSITE_CODE,PART_BUY_AUTH_INDICATOR,PARTS_FINANCE_NUMBER,CONTACT_PHONE,CONTACT_FAX,PARTS_FACILITY_TYPE_CODE,SCF_CODE,CUSTOMER_NAME,DATED_CHANGED,CHANGE_CODE,ERROR_CODE,ERROR_MESSAGE,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getFedstrip058Id());
        pstmt.setString(2,pData.getFedstrip());
        pstmt.setString(3,pData.getFinance());
        pstmt.setString(4,pData.getBaCode());
        pstmt.setString(5,pData.getDistrictCode());
        pstmt.setString(6,pData.getCag());
        pstmt.setString(7,pData.getAddressTypeCode());
        pstmt.setString(8,pData.getAddressLine1());
        pstmt.setString(9,pData.getAddressLine2());
        pstmt.setString(10,pData.getAddressLine3());
        pstmt.setString(11,pData.getCity());
        pstmt.setString(12,pData.getState());
        pstmt.setString(13,pData.getZip());
        pstmt.setString(14,pData.getNmicsSiteCode());
        pstmt.setString(15,pData.getNmicsSubsiteCode());
        pstmt.setString(16,pData.getPartBuyAuthIndicator());
        pstmt.setString(17,pData.getPartsFinanceNumber());
        pstmt.setString(18,pData.getContactPhone());
        pstmt.setString(19,pData.getContactFax());
        pstmt.setString(20,pData.getPartsFacilityTypeCode());
        pstmt.setString(21,pData.getScfCode());
        pstmt.setString(22,pData.getCustomerName());
        pstmt.setString(23,pData.getDatedChanged());
        pstmt.setString(24,pData.getChangeCode());
        pstmt.setString(25,pData.getErrorCode());
        pstmt.setString(26,pData.getErrorMessage());
        pstmt.setString(27,pData.getFileName());
        pstmt.setTimestamp(28,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(29,pData.getAddBy());
        pstmt.setTimestamp(30,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(31,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FEDSTRIP_058_ID="+pData.getFedstrip058Id());
            log.debug("SQL:   FEDSTRIP="+pData.getFedstrip());
            log.debug("SQL:   FINANCE="+pData.getFinance());
            log.debug("SQL:   BA_CODE="+pData.getBaCode());
            log.debug("SQL:   DISTRICT_CODE="+pData.getDistrictCode());
            log.debug("SQL:   CAG="+pData.getCag());
            log.debug("SQL:   ADDRESS_TYPE_CODE="+pData.getAddressTypeCode());
            log.debug("SQL:   ADDRESS_LINE1="+pData.getAddressLine1());
            log.debug("SQL:   ADDRESS_LINE2="+pData.getAddressLine2());
            log.debug("SQL:   ADDRESS_LINE3="+pData.getAddressLine3());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE="+pData.getState());
            log.debug("SQL:   ZIP="+pData.getZip());
            log.debug("SQL:   NMICS_SITE_CODE="+pData.getNmicsSiteCode());
            log.debug("SQL:   NMICS_SUBSITE_CODE="+pData.getNmicsSubsiteCode());
            log.debug("SQL:   PART_BUY_AUTH_INDICATOR="+pData.getPartBuyAuthIndicator());
            log.debug("SQL:   PARTS_FINANCE_NUMBER="+pData.getPartsFinanceNumber());
            log.debug("SQL:   CONTACT_PHONE="+pData.getContactPhone());
            log.debug("SQL:   CONTACT_FAX="+pData.getContactFax());
            log.debug("SQL:   PARTS_FACILITY_TYPE_CODE="+pData.getPartsFacilityTypeCode());
            log.debug("SQL:   SCF_CODE="+pData.getScfCode());
            log.debug("SQL:   CUSTOMER_NAME="+pData.getCustomerName());
            log.debug("SQL:   DATED_CHANGED="+pData.getDatedChanged());
            log.debug("SQL:   CHANGE_CODE="+pData.getChangeCode());
            log.debug("SQL:   ERROR_CODE="+pData.getErrorCode());
            log.debug("SQL:   ERROR_MESSAGE="+pData.getErrorMessage());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
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
        pData.setFedstrip058Id(0);
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
     * Updates a Fedstrip058Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A Fedstrip058Data object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, Fedstrip058Data pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FEDSTRIP_058 SET FEDSTRIP = ?,FINANCE = ?,BA_CODE = ?,DISTRICT_CODE = ?,CAG = ?,ADDRESS_TYPE_CODE = ?,ADDRESS_LINE1 = ?,ADDRESS_LINE2 = ?,ADDRESS_LINE3 = ?,CITY = ?,STATE = ?,ZIP = ?,NMICS_SITE_CODE = ?,NMICS_SUBSITE_CODE = ?,PART_BUY_AUTH_INDICATOR = ?,PARTS_FINANCE_NUMBER = ?,CONTACT_PHONE = ?,CONTACT_FAX = ?,PARTS_FACILITY_TYPE_CODE = ?,SCF_CODE = ?,CUSTOMER_NAME = ?,DATED_CHANGED = ?,CHANGE_CODE = ?,ERROR_CODE = ?,ERROR_MESSAGE = ?,FILE_NAME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE FEDSTRIP_058_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getFedstrip());
        pstmt.setString(i++,pData.getFinance());
        pstmt.setString(i++,pData.getBaCode());
        pstmt.setString(i++,pData.getDistrictCode());
        pstmt.setString(i++,pData.getCag());
        pstmt.setString(i++,pData.getAddressTypeCode());
        pstmt.setString(i++,pData.getAddressLine1());
        pstmt.setString(i++,pData.getAddressLine2());
        pstmt.setString(i++,pData.getAddressLine3());
        pstmt.setString(i++,pData.getCity());
        pstmt.setString(i++,pData.getState());
        pstmt.setString(i++,pData.getZip());
        pstmt.setString(i++,pData.getNmicsSiteCode());
        pstmt.setString(i++,pData.getNmicsSubsiteCode());
        pstmt.setString(i++,pData.getPartBuyAuthIndicator());
        pstmt.setString(i++,pData.getPartsFinanceNumber());
        pstmt.setString(i++,pData.getContactPhone());
        pstmt.setString(i++,pData.getContactFax());
        pstmt.setString(i++,pData.getPartsFacilityTypeCode());
        pstmt.setString(i++,pData.getScfCode());
        pstmt.setString(i++,pData.getCustomerName());
        pstmt.setString(i++,pData.getDatedChanged());
        pstmt.setString(i++,pData.getChangeCode());
        pstmt.setString(i++,pData.getErrorCode());
        pstmt.setString(i++,pData.getErrorMessage());
        pstmt.setString(i++,pData.getFileName());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getFedstrip058Id());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FEDSTRIP="+pData.getFedstrip());
            log.debug("SQL:   FINANCE="+pData.getFinance());
            log.debug("SQL:   BA_CODE="+pData.getBaCode());
            log.debug("SQL:   DISTRICT_CODE="+pData.getDistrictCode());
            log.debug("SQL:   CAG="+pData.getCag());
            log.debug("SQL:   ADDRESS_TYPE_CODE="+pData.getAddressTypeCode());
            log.debug("SQL:   ADDRESS_LINE1="+pData.getAddressLine1());
            log.debug("SQL:   ADDRESS_LINE2="+pData.getAddressLine2());
            log.debug("SQL:   ADDRESS_LINE3="+pData.getAddressLine3());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE="+pData.getState());
            log.debug("SQL:   ZIP="+pData.getZip());
            log.debug("SQL:   NMICS_SITE_CODE="+pData.getNmicsSiteCode());
            log.debug("SQL:   NMICS_SUBSITE_CODE="+pData.getNmicsSubsiteCode());
            log.debug("SQL:   PART_BUY_AUTH_INDICATOR="+pData.getPartBuyAuthIndicator());
            log.debug("SQL:   PARTS_FINANCE_NUMBER="+pData.getPartsFinanceNumber());
            log.debug("SQL:   CONTACT_PHONE="+pData.getContactPhone());
            log.debug("SQL:   CONTACT_FAX="+pData.getContactFax());
            log.debug("SQL:   PARTS_FACILITY_TYPE_CODE="+pData.getPartsFacilityTypeCode());
            log.debug("SQL:   SCF_CODE="+pData.getScfCode());
            log.debug("SQL:   CUSTOMER_NAME="+pData.getCustomerName());
            log.debug("SQL:   DATED_CHANGED="+pData.getDatedChanged());
            log.debug("SQL:   CHANGE_CODE="+pData.getChangeCode());
            log.debug("SQL:   ERROR_CODE="+pData.getErrorCode());
            log.debug("SQL:   ERROR_MESSAGE="+pData.getErrorMessage());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
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
     * Deletes a Fedstrip058Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFedstrip058Id Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFedstrip058Id)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FEDSTRIP_058 WHERE FEDSTRIP_058_ID = " + pFedstrip058Id;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes Fedstrip058Data objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FEDSTRIP_058");
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
     * Inserts a Fedstrip058Data log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A Fedstrip058Data object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, Fedstrip058Data pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_FEDSTRIP_058 (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "FEDSTRIP_058_ID,FEDSTRIP,FINANCE,BA_CODE,DISTRICT_CODE,CAG,ADDRESS_TYPE_CODE,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,CITY,STATE,ZIP,NMICS_SITE_CODE,NMICS_SUBSITE_CODE,PART_BUY_AUTH_INDICATOR,PARTS_FINANCE_NUMBER,CONTACT_PHONE,CONTACT_FAX,PARTS_FACILITY_TYPE_CODE,SCF_CODE,CUSTOMER_NAME,DATED_CHANGED,CHANGE_CODE,ERROR_CODE,ERROR_MESSAGE,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getFedstrip058Id());
        pstmt.setString(2+4,pData.getFedstrip());
        pstmt.setString(3+4,pData.getFinance());
        pstmt.setString(4+4,pData.getBaCode());
        pstmt.setString(5+4,pData.getDistrictCode());
        pstmt.setString(6+4,pData.getCag());
        pstmt.setString(7+4,pData.getAddressTypeCode());
        pstmt.setString(8+4,pData.getAddressLine1());
        pstmt.setString(9+4,pData.getAddressLine2());
        pstmt.setString(10+4,pData.getAddressLine3());
        pstmt.setString(11+4,pData.getCity());
        pstmt.setString(12+4,pData.getState());
        pstmt.setString(13+4,pData.getZip());
        pstmt.setString(14+4,pData.getNmicsSiteCode());
        pstmt.setString(15+4,pData.getNmicsSubsiteCode());
        pstmt.setString(16+4,pData.getPartBuyAuthIndicator());
        pstmt.setString(17+4,pData.getPartsFinanceNumber());
        pstmt.setString(18+4,pData.getContactPhone());
        pstmt.setString(19+4,pData.getContactFax());
        pstmt.setString(20+4,pData.getPartsFacilityTypeCode());
        pstmt.setString(21+4,pData.getScfCode());
        pstmt.setString(22+4,pData.getCustomerName());
        pstmt.setString(23+4,pData.getDatedChanged());
        pstmt.setString(24+4,pData.getChangeCode());
        pstmt.setString(25+4,pData.getErrorCode());
        pstmt.setString(26+4,pData.getErrorMessage());
        pstmt.setString(27+4,pData.getFileName());
        pstmt.setTimestamp(28+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(29+4,pData.getAddBy());
        pstmt.setTimestamp(30+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(31+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a Fedstrip058Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A Fedstrip058Data object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new Fedstrip058Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static Fedstrip058Data insert(Connection pCon, Fedstrip058Data pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a Fedstrip058Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A Fedstrip058Data object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, Fedstrip058Data pData, boolean pLogFl)
        throws SQLException {
        Fedstrip058Data oldData = null;
        if(pLogFl) {
          int id = pData.getFedstrip058Id();
          try {
          oldData = Fedstrip058DataAccess.select(pCon,id);
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
     * Deletes a Fedstrip058Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFedstrip058Id Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFedstrip058Id, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_FEDSTRIP_058 SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FEDSTRIP_058 d WHERE FEDSTRIP_058_ID = " + pFedstrip058Id;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pFedstrip058Id);
        return n;
     }

    /**
     * Deletes Fedstrip058Data objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_FEDSTRIP_058 SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FEDSTRIP_058 d ");
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

