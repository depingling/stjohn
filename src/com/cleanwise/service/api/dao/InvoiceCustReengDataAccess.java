
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InvoiceCustReengDataAccess
 * Description:  This class is used to build access methods to the CLW_INVOICE_CUST_REENG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InvoiceCustReengData;
import com.cleanwise.service.api.value.InvoiceCustReengDataVector;
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
 * <code>InvoiceCustReengDataAccess</code>
 */
public class InvoiceCustReengDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InvoiceCustReengDataAccess.class.getName());

    /** <code>CLW_INVOICE_CUST_REENG</code> table name */
	/* Primary key: INVOICE_CUST_REENG_ID */
	
    public static final String CLW_INVOICE_CUST_REENG = "CLW_INVOICE_CUST_REENG";
    
    /** <code>INVOICE_CUST_REENG_ID</code> INVOICE_CUST_REENG_ID column of table CLW_INVOICE_CUST_REENG */
    public static final String INVOICE_CUST_REENG_ID = "INVOICE_CUST_REENG_ID";
    /** <code>FILE_NAME</code> FILE_NAME column of table CLW_INVOICE_CUST_REENG */
    public static final String FILE_NAME = "FILE_NAME";
    /** <code>SENDER</code> SENDER column of table CLW_INVOICE_CUST_REENG */
    public static final String SENDER = "SENDER";
    /** <code>RECEIVER</code> RECEIVER column of table CLW_INVOICE_CUST_REENG */
    public static final String RECEIVER = "RECEIVER";
    /** <code>SET_NUM</code> SET_NUM column of table CLW_INVOICE_CUST_REENG */
    public static final String SET_NUM = "SET_NUM";
    /** <code>INVOICE_NUM</code> INVOICE_NUM column of table CLW_INVOICE_CUST_REENG */
    public static final String INVOICE_NUM = "INVOICE_NUM";
    /** <code>REF_INVOICE_NUM</code> REF_INVOICE_NUM column of table CLW_INVOICE_CUST_REENG */
    public static final String REF_INVOICE_NUM = "REF_INVOICE_NUM";
    /** <code>INVOICE_DATE</code> INVOICE_DATE column of table CLW_INVOICE_CUST_REENG */
    public static final String INVOICE_DATE = "INVOICE_DATE";
    /** <code>LINE_NUM</code> LINE_NUM column of table CLW_INVOICE_CUST_REENG */
    public static final String LINE_NUM = "LINE_NUM";
    /** <code>CW_SKU_NUM</code> CW_SKU_NUM column of table CLW_INVOICE_CUST_REENG */
    public static final String CW_SKU_NUM = "CW_SKU_NUM";
    /** <code>CUST_SKU_NUM</code> CUST_SKU_NUM column of table CLW_INVOICE_CUST_REENG */
    public static final String CUST_SKU_NUM = "CUST_SKU_NUM";
    /** <code>ITEM_UOM</code> ITEM_UOM column of table CLW_INVOICE_CUST_REENG */
    public static final String ITEM_UOM = "ITEM_UOM";
    /** <code>ITEM_QTY</code> ITEM_QTY column of table CLW_INVOICE_CUST_REENG */
    public static final String ITEM_QTY = "ITEM_QTY";
    /** <code>ITEM_PRICE</code> ITEM_PRICE column of table CLW_INVOICE_CUST_REENG */
    public static final String ITEM_PRICE = "ITEM_PRICE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVOICE_CUST_REENG */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVOICE_CUST_REENG */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVOICE_CUST_REENG */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVOICE_CUST_REENG */
    public static final String MOD_BY = "MOD_BY";
    /** <code>CHARGE_TYPE</code> CHARGE_TYPE column of table CLW_INVOICE_CUST_REENG */
    public static final String CHARGE_TYPE = "CHARGE_TYPE";
    /** <code>INVOICE_TYPE</code> INVOICE_TYPE column of table CLW_INVOICE_CUST_REENG */
    public static final String INVOICE_TYPE = "INVOICE_TYPE";

    /**
     * Constructor.
     */
    public InvoiceCustReengDataAccess()
    {
    }

    /**
     * Gets a InvoiceCustReengData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInvoiceCustReengId The key requested.
     * @return new InvoiceCustReengData()
     * @throws            SQLException
     */
    public static InvoiceCustReengData select(Connection pCon, int pInvoiceCustReengId)
        throws SQLException, DataNotFoundException {
        InvoiceCustReengData x=null;
        String sql="SELECT INVOICE_CUST_REENG_ID,FILE_NAME,SENDER,RECEIVER,SET_NUM,INVOICE_NUM,REF_INVOICE_NUM,INVOICE_DATE,LINE_NUM,CW_SKU_NUM,CUST_SKU_NUM,ITEM_UOM,ITEM_QTY,ITEM_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CHARGE_TYPE,INVOICE_TYPE FROM CLW_INVOICE_CUST_REENG WHERE INVOICE_CUST_REENG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInvoiceCustReengId=" + pInvoiceCustReengId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInvoiceCustReengId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InvoiceCustReengData.createValue();
            
            x.setInvoiceCustReengId(rs.getInt(1));
            x.setFileName(rs.getString(2));
            x.setSender(rs.getString(3));
            x.setReceiver(rs.getString(4));
            x.setSetNum(rs.getString(5));
            x.setInvoiceNum(rs.getString(6));
            x.setRefInvoiceNum(rs.getString(7));
            x.setInvoiceDate(rs.getDate(8));
            x.setLineNum(rs.getInt(9));
            x.setCwSkuNum(rs.getInt(10));
            x.setCustSkuNum(rs.getString(11));
            x.setItemUom(rs.getString(12));
            x.setItemQty(rs.getInt(13));
            x.setItemPrice(rs.getBigDecimal(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setAddBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setModBy(rs.getString(18));
            x.setChargeType(rs.getString(19));
            x.setInvoiceType(rs.getString(20));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVOICE_CUST_REENG_ID :" + pInvoiceCustReengId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InvoiceCustReengDataVector object that consists
     * of InvoiceCustReengData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InvoiceCustReengDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustReengDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InvoiceCustReengData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVOICE_CUST_REENG.INVOICE_CUST_REENG_ID,CLW_INVOICE_CUST_REENG.FILE_NAME,CLW_INVOICE_CUST_REENG.SENDER,CLW_INVOICE_CUST_REENG.RECEIVER,CLW_INVOICE_CUST_REENG.SET_NUM,CLW_INVOICE_CUST_REENG.INVOICE_NUM,CLW_INVOICE_CUST_REENG.REF_INVOICE_NUM,CLW_INVOICE_CUST_REENG.INVOICE_DATE,CLW_INVOICE_CUST_REENG.LINE_NUM,CLW_INVOICE_CUST_REENG.CW_SKU_NUM,CLW_INVOICE_CUST_REENG.CUST_SKU_NUM,CLW_INVOICE_CUST_REENG.ITEM_UOM,CLW_INVOICE_CUST_REENG.ITEM_QTY,CLW_INVOICE_CUST_REENG.ITEM_PRICE,CLW_INVOICE_CUST_REENG.ADD_DATE,CLW_INVOICE_CUST_REENG.ADD_BY,CLW_INVOICE_CUST_REENG.MOD_DATE,CLW_INVOICE_CUST_REENG.MOD_BY,CLW_INVOICE_CUST_REENG.CHARGE_TYPE,CLW_INVOICE_CUST_REENG.INVOICE_TYPE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InvoiceCustReengData Object.
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
    *@returns a populated InvoiceCustReengData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InvoiceCustReengData x = InvoiceCustReengData.createValue();
         
         x.setInvoiceCustReengId(rs.getInt(1+offset));
         x.setFileName(rs.getString(2+offset));
         x.setSender(rs.getString(3+offset));
         x.setReceiver(rs.getString(4+offset));
         x.setSetNum(rs.getString(5+offset));
         x.setInvoiceNum(rs.getString(6+offset));
         x.setRefInvoiceNum(rs.getString(7+offset));
         x.setInvoiceDate(rs.getDate(8+offset));
         x.setLineNum(rs.getInt(9+offset));
         x.setCwSkuNum(rs.getInt(10+offset));
         x.setCustSkuNum(rs.getString(11+offset));
         x.setItemUom(rs.getString(12+offset));
         x.setItemQty(rs.getInt(13+offset));
         x.setItemPrice(rs.getBigDecimal(14+offset));
         x.setAddDate(rs.getTimestamp(15+offset));
         x.setAddBy(rs.getString(16+offset));
         x.setModDate(rs.getTimestamp(17+offset));
         x.setModBy(rs.getString(18+offset));
         x.setChargeType(rs.getString(19+offset));
         x.setInvoiceType(rs.getString(20+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InvoiceCustReengData Object represents.
    */
    public int getColumnCount(){
        return 20;
    }

    /**
     * Gets a InvoiceCustReengDataVector object that consists
     * of InvoiceCustReengData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InvoiceCustReengDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustReengDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVOICE_CUST_REENG_ID,FILE_NAME,SENDER,RECEIVER,SET_NUM,INVOICE_NUM,REF_INVOICE_NUM,INVOICE_DATE,LINE_NUM,CW_SKU_NUM,CUST_SKU_NUM,ITEM_UOM,ITEM_QTY,ITEM_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CHARGE_TYPE,INVOICE_TYPE FROM CLW_INVOICE_CUST_REENG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVOICE_CUST_REENG.INVOICE_CUST_REENG_ID,CLW_INVOICE_CUST_REENG.FILE_NAME,CLW_INVOICE_CUST_REENG.SENDER,CLW_INVOICE_CUST_REENG.RECEIVER,CLW_INVOICE_CUST_REENG.SET_NUM,CLW_INVOICE_CUST_REENG.INVOICE_NUM,CLW_INVOICE_CUST_REENG.REF_INVOICE_NUM,CLW_INVOICE_CUST_REENG.INVOICE_DATE,CLW_INVOICE_CUST_REENG.LINE_NUM,CLW_INVOICE_CUST_REENG.CW_SKU_NUM,CLW_INVOICE_CUST_REENG.CUST_SKU_NUM,CLW_INVOICE_CUST_REENG.ITEM_UOM,CLW_INVOICE_CUST_REENG.ITEM_QTY,CLW_INVOICE_CUST_REENG.ITEM_PRICE,CLW_INVOICE_CUST_REENG.ADD_DATE,CLW_INVOICE_CUST_REENG.ADD_BY,CLW_INVOICE_CUST_REENG.MOD_DATE,CLW_INVOICE_CUST_REENG.MOD_BY,CLW_INVOICE_CUST_REENG.CHARGE_TYPE,CLW_INVOICE_CUST_REENG.INVOICE_TYPE FROM CLW_INVOICE_CUST_REENG");
                where = pCriteria.getSqlClause("CLW_INVOICE_CUST_REENG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_CUST_REENG.equals(otherTable)){
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
        InvoiceCustReengDataVector v = new InvoiceCustReengDataVector();
        while (rs.next()) {
            InvoiceCustReengData x = InvoiceCustReengData.createValue();
            
            x.setInvoiceCustReengId(rs.getInt(1));
            x.setFileName(rs.getString(2));
            x.setSender(rs.getString(3));
            x.setReceiver(rs.getString(4));
            x.setSetNum(rs.getString(5));
            x.setInvoiceNum(rs.getString(6));
            x.setRefInvoiceNum(rs.getString(7));
            x.setInvoiceDate(rs.getDate(8));
            x.setLineNum(rs.getInt(9));
            x.setCwSkuNum(rs.getInt(10));
            x.setCustSkuNum(rs.getString(11));
            x.setItemUom(rs.getString(12));
            x.setItemQty(rs.getInt(13));
            x.setItemPrice(rs.getBigDecimal(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setAddBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setModBy(rs.getString(18));
            x.setChargeType(rs.getString(19));
            x.setInvoiceType(rs.getString(20));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InvoiceCustReengDataVector object that consists
     * of InvoiceCustReengData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InvoiceCustReengData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InvoiceCustReengDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustReengDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InvoiceCustReengDataVector v = new InvoiceCustReengDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_CUST_REENG_ID,FILE_NAME,SENDER,RECEIVER,SET_NUM,INVOICE_NUM,REF_INVOICE_NUM,INVOICE_DATE,LINE_NUM,CW_SKU_NUM,CUST_SKU_NUM,ITEM_UOM,ITEM_QTY,ITEM_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CHARGE_TYPE,INVOICE_TYPE FROM CLW_INVOICE_CUST_REENG WHERE INVOICE_CUST_REENG_ID IN (");

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
            InvoiceCustReengData x=null;
            while (rs.next()) {
                // build the object
                x=InvoiceCustReengData.createValue();
                
                x.setInvoiceCustReengId(rs.getInt(1));
                x.setFileName(rs.getString(2));
                x.setSender(rs.getString(3));
                x.setReceiver(rs.getString(4));
                x.setSetNum(rs.getString(5));
                x.setInvoiceNum(rs.getString(6));
                x.setRefInvoiceNum(rs.getString(7));
                x.setInvoiceDate(rs.getDate(8));
                x.setLineNum(rs.getInt(9));
                x.setCwSkuNum(rs.getInt(10));
                x.setCustSkuNum(rs.getString(11));
                x.setItemUom(rs.getString(12));
                x.setItemQty(rs.getInt(13));
                x.setItemPrice(rs.getBigDecimal(14));
                x.setAddDate(rs.getTimestamp(15));
                x.setAddBy(rs.getString(16));
                x.setModDate(rs.getTimestamp(17));
                x.setModBy(rs.getString(18));
                x.setChargeType(rs.getString(19));
                x.setInvoiceType(rs.getString(20));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InvoiceCustReengDataVector object of all
     * InvoiceCustReengData objects in the database.
     * @param pCon An open database connection.
     * @return new InvoiceCustReengDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustReengDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVOICE_CUST_REENG_ID,FILE_NAME,SENDER,RECEIVER,SET_NUM,INVOICE_NUM,REF_INVOICE_NUM,INVOICE_DATE,LINE_NUM,CW_SKU_NUM,CUST_SKU_NUM,ITEM_UOM,ITEM_QTY,ITEM_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CHARGE_TYPE,INVOICE_TYPE FROM CLW_INVOICE_CUST_REENG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InvoiceCustReengDataVector v = new InvoiceCustReengDataVector();
        InvoiceCustReengData x = null;
        while (rs.next()) {
            // build the object
            x = InvoiceCustReengData.createValue();
            
            x.setInvoiceCustReengId(rs.getInt(1));
            x.setFileName(rs.getString(2));
            x.setSender(rs.getString(3));
            x.setReceiver(rs.getString(4));
            x.setSetNum(rs.getString(5));
            x.setInvoiceNum(rs.getString(6));
            x.setRefInvoiceNum(rs.getString(7));
            x.setInvoiceDate(rs.getDate(8));
            x.setLineNum(rs.getInt(9));
            x.setCwSkuNum(rs.getInt(10));
            x.setCustSkuNum(rs.getString(11));
            x.setItemUom(rs.getString(12));
            x.setItemQty(rs.getInt(13));
            x.setItemPrice(rs.getBigDecimal(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setAddBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setModBy(rs.getString(18));
            x.setChargeType(rs.getString(19));
            x.setInvoiceType(rs.getString(20));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InvoiceCustReengData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_CUST_REENG_ID FROM CLW_INVOICE_CUST_REENG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_CUST_REENG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_CUST_REENG");
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
     * Inserts a InvoiceCustReengData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustReengData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InvoiceCustReengData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceCustReengData insert(Connection pCon, InvoiceCustReengData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVOICE_CUST_REENG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVOICE_CUST_REENG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInvoiceCustReengId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVOICE_CUST_REENG (INVOICE_CUST_REENG_ID,FILE_NAME,SENDER,RECEIVER,SET_NUM,INVOICE_NUM,REF_INVOICE_NUM,INVOICE_DATE,LINE_NUM,CW_SKU_NUM,CUST_SKU_NUM,ITEM_UOM,ITEM_QTY,ITEM_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CHARGE_TYPE,INVOICE_TYPE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInvoiceCustReengId());
        pstmt.setString(2,pData.getFileName());
        pstmt.setString(3,pData.getSender());
        pstmt.setString(4,pData.getReceiver());
        pstmt.setString(5,pData.getSetNum());
        pstmt.setString(6,pData.getInvoiceNum());
        pstmt.setString(7,pData.getRefInvoiceNum());
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setInt(9,pData.getLineNum());
        pstmt.setInt(10,pData.getCwSkuNum());
        pstmt.setString(11,pData.getCustSkuNum());
        pstmt.setString(12,pData.getItemUom());
        pstmt.setInt(13,pData.getItemQty());
        pstmt.setBigDecimal(14,pData.getItemPrice());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(16,pData.getAddBy());
        pstmt.setTimestamp(17,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(18,pData.getModBy());
        pstmt.setString(19,pData.getChargeType());
        pstmt.setString(20,pData.getInvoiceType());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_CUST_REENG_ID="+pData.getInvoiceCustReengId());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   SENDER="+pData.getSender());
            log.debug("SQL:   RECEIVER="+pData.getReceiver());
            log.debug("SQL:   SET_NUM="+pData.getSetNum());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   REF_INVOICE_NUM="+pData.getRefInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   LINE_NUM="+pData.getLineNum());
            log.debug("SQL:   CW_SKU_NUM="+pData.getCwSkuNum());
            log.debug("SQL:   CUST_SKU_NUM="+pData.getCustSkuNum());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_QTY="+pData.getItemQty());
            log.debug("SQL:   ITEM_PRICE="+pData.getItemPrice());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CHARGE_TYPE="+pData.getChargeType());
            log.debug("SQL:   INVOICE_TYPE="+pData.getInvoiceType());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInvoiceCustReengId(0);
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
     * Updates a InvoiceCustReengData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustReengData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceCustReengData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVOICE_CUST_REENG SET FILE_NAME = ?,SENDER = ?,RECEIVER = ?,SET_NUM = ?,INVOICE_NUM = ?,REF_INVOICE_NUM = ?,INVOICE_DATE = ?,LINE_NUM = ?,CW_SKU_NUM = ?,CUST_SKU_NUM = ?,ITEM_UOM = ?,ITEM_QTY = ?,ITEM_PRICE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,CHARGE_TYPE = ?,INVOICE_TYPE = ? WHERE INVOICE_CUST_REENG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getFileName());
        pstmt.setString(i++,pData.getSender());
        pstmt.setString(i++,pData.getReceiver());
        pstmt.setString(i++,pData.getSetNum());
        pstmt.setString(i++,pData.getInvoiceNum());
        pstmt.setString(i++,pData.getRefInvoiceNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setInt(i++,pData.getLineNum());
        pstmt.setInt(i++,pData.getCwSkuNum());
        pstmt.setString(i++,pData.getCustSkuNum());
        pstmt.setString(i++,pData.getItemUom());
        pstmt.setInt(i++,pData.getItemQty());
        pstmt.setBigDecimal(i++,pData.getItemPrice());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getChargeType());
        pstmt.setString(i++,pData.getInvoiceType());
        pstmt.setInt(i++,pData.getInvoiceCustReengId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   SENDER="+pData.getSender());
            log.debug("SQL:   RECEIVER="+pData.getReceiver());
            log.debug("SQL:   SET_NUM="+pData.getSetNum());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   REF_INVOICE_NUM="+pData.getRefInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   LINE_NUM="+pData.getLineNum());
            log.debug("SQL:   CW_SKU_NUM="+pData.getCwSkuNum());
            log.debug("SQL:   CUST_SKU_NUM="+pData.getCustSkuNum());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_QTY="+pData.getItemQty());
            log.debug("SQL:   ITEM_PRICE="+pData.getItemPrice());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CHARGE_TYPE="+pData.getChargeType());
            log.debug("SQL:   INVOICE_TYPE="+pData.getInvoiceType());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InvoiceCustReengData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceCustReengId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceCustReengId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVOICE_CUST_REENG WHERE INVOICE_CUST_REENG_ID = " + pInvoiceCustReengId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InvoiceCustReengData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVOICE_CUST_REENG");
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
     * Inserts a InvoiceCustReengData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustReengData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InvoiceCustReengData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVOICE_CUST_REENG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVOICE_CUST_REENG_ID,FILE_NAME,SENDER,RECEIVER,SET_NUM,INVOICE_NUM,REF_INVOICE_NUM,INVOICE_DATE,LINE_NUM,CW_SKU_NUM,CUST_SKU_NUM,ITEM_UOM,ITEM_QTY,ITEM_PRICE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CHARGE_TYPE,INVOICE_TYPE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInvoiceCustReengId());
        pstmt.setString(2+4,pData.getFileName());
        pstmt.setString(3+4,pData.getSender());
        pstmt.setString(4+4,pData.getReceiver());
        pstmt.setString(5+4,pData.getSetNum());
        pstmt.setString(6+4,pData.getInvoiceNum());
        pstmt.setString(7+4,pData.getRefInvoiceNum());
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setInt(9+4,pData.getLineNum());
        pstmt.setInt(10+4,pData.getCwSkuNum());
        pstmt.setString(11+4,pData.getCustSkuNum());
        pstmt.setString(12+4,pData.getItemUom());
        pstmt.setInt(13+4,pData.getItemQty());
        pstmt.setBigDecimal(14+4,pData.getItemPrice());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(16+4,pData.getAddBy());
        pstmt.setTimestamp(17+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(18+4,pData.getModBy());
        pstmt.setString(19+4,pData.getChargeType());
        pstmt.setString(20+4,pData.getInvoiceType());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InvoiceCustReengData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustReengData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InvoiceCustReengData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceCustReengData insert(Connection pCon, InvoiceCustReengData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InvoiceCustReengData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustReengData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceCustReengData pData, boolean pLogFl)
        throws SQLException {
        InvoiceCustReengData oldData = null;
        if(pLogFl) {
          int id = pData.getInvoiceCustReengId();
          try {
          oldData = InvoiceCustReengDataAccess.select(pCon,id);
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
     * Deletes a InvoiceCustReengData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceCustReengId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceCustReengId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVOICE_CUST_REENG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_CUST_REENG d WHERE INVOICE_CUST_REENG_ID = " + pInvoiceCustReengId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInvoiceCustReengId);
        return n;
     }

    /**
     * Deletes InvoiceCustReengData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVOICE_CUST_REENG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_CUST_REENG d ");
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

