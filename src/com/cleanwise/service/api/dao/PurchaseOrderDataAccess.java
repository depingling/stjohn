
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PurchaseOrderDataAccess
 * Description:  This class is used to build access methods to the CLW_PURCHASE_ORDER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.PurchaseOrderDataVector;
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
 * <code>PurchaseOrderDataAccess</code>
 */
public class PurchaseOrderDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PurchaseOrderDataAccess.class.getName());

    /** <code>CLW_PURCHASE_ORDER</code> table name */
	/* Primary key: PURCHASE_ORDER_ID */
	
    public static final String CLW_PURCHASE_ORDER = "CLW_PURCHASE_ORDER";
    
    /** <code>PURCHASE_ORDER_ID</code> PURCHASE_ORDER_ID column of table CLW_PURCHASE_ORDER */
    public static final String PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_PURCHASE_ORDER */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ERP_PO_NUM</code> ERP_PO_NUM column of table CLW_PURCHASE_ORDER */
    public static final String ERP_PO_NUM = "ERP_PO_NUM";
    /** <code>PO_DATE</code> PO_DATE column of table CLW_PURCHASE_ORDER */
    public static final String PO_DATE = "PO_DATE";
    /** <code>DIST_ERP_NUM</code> DIST_ERP_NUM column of table CLW_PURCHASE_ORDER */
    public static final String DIST_ERP_NUM = "DIST_ERP_NUM";
    /** <code>LINE_ITEM_TOTAL</code> LINE_ITEM_TOTAL column of table CLW_PURCHASE_ORDER */
    public static final String LINE_ITEM_TOTAL = "LINE_ITEM_TOTAL";
    /** <code>PURCHASE_ORDER_TOTAL</code> PURCHASE_ORDER_TOTAL column of table CLW_PURCHASE_ORDER */
    public static final String PURCHASE_ORDER_TOTAL = "PURCHASE_ORDER_TOTAL";
    /** <code>PURCHASE_ORDER_STATUS_CD</code> PURCHASE_ORDER_STATUS_CD column of table CLW_PURCHASE_ORDER */
    public static final String PURCHASE_ORDER_STATUS_CD = "PURCHASE_ORDER_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PURCHASE_ORDER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PURCHASE_ORDER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PURCHASE_ORDER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PURCHASE_ORDER */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PURCH_ORD_MANIFEST_STATUS_CD</code> PURCH_ORD_MANIFEST_STATUS_CD column of table CLW_PURCHASE_ORDER */
    public static final String PURCH_ORD_MANIFEST_STATUS_CD = "PURCH_ORD_MANIFEST_STATUS_CD";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_PURCHASE_ORDER */
    public static final String STORE_ID = "STORE_ID";
    /** <code>ERP_SYSTEM_CD</code> ERP_SYSTEM_CD column of table CLW_PURCHASE_ORDER */
    public static final String ERP_SYSTEM_CD = "ERP_SYSTEM_CD";
    /** <code>TAX_TOTAL</code> TAX_TOTAL column of table CLW_PURCHASE_ORDER */
    public static final String TAX_TOTAL = "TAX_TOTAL";
    /** <code>ERP_PO_REF_NUM</code> ERP_PO_REF_NUM column of table CLW_PURCHASE_ORDER */
    public static final String ERP_PO_REF_NUM = "ERP_PO_REF_NUM";
    /** <code>ERP_PO_REF_DATE</code> ERP_PO_REF_DATE column of table CLW_PURCHASE_ORDER */
    public static final String ERP_PO_REF_DATE = "ERP_PO_REF_DATE";
    /** <code>OUTBOUND_PO_NUM</code> OUTBOUND_PO_NUM column of table CLW_PURCHASE_ORDER */
    public static final String OUTBOUND_PO_NUM = "OUTBOUND_PO_NUM";

    /**
     * Constructor.
     */
    public PurchaseOrderDataAccess()
    {
    }

    /**
     * Gets a PurchaseOrderData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPurchaseOrderId The key requested.
     * @return new PurchaseOrderData()
     * @throws            SQLException
     */
    public static PurchaseOrderData select(Connection pCon, int pPurchaseOrderId)
        throws SQLException, DataNotFoundException {
        PurchaseOrderData x=null;
        String sql="SELECT PURCHASE_ORDER_ID,ORDER_ID,ERP_PO_NUM,PO_DATE,DIST_ERP_NUM,LINE_ITEM_TOTAL,PURCHASE_ORDER_TOTAL,PURCHASE_ORDER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PURCH_ORD_MANIFEST_STATUS_CD,STORE_ID,ERP_SYSTEM_CD,TAX_TOTAL,ERP_PO_REF_NUM,ERP_PO_REF_DATE,OUTBOUND_PO_NUM FROM CLW_PURCHASE_ORDER WHERE PURCHASE_ORDER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPurchaseOrderId=" + pPurchaseOrderId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPurchaseOrderId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PurchaseOrderData.createValue();
            
            x.setPurchaseOrderId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setErpPoNum(rs.getString(3));
            x.setPoDate(rs.getDate(4));
            x.setDistErpNum(rs.getString(5));
            x.setLineItemTotal(rs.getBigDecimal(6));
            x.setPurchaseOrderTotal(rs.getBigDecimal(7));
            x.setPurchaseOrderStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setPurchOrdManifestStatusCd(rs.getString(13));
            x.setStoreId(rs.getInt(14));
            x.setErpSystemCd(rs.getString(15));
            x.setTaxTotal(rs.getBigDecimal(16));
            x.setErpPoRefNum(rs.getString(17));
            x.setErpPoRefDate(rs.getDate(18));
            x.setOutboundPoNum(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PURCHASE_ORDER_ID :" + pPurchaseOrderId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PurchaseOrderDataVector object that consists
     * of PurchaseOrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PurchaseOrderDataVector()
     * @throws            SQLException
     */
    public static PurchaseOrderDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PurchaseOrderData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PURCHASE_ORDER.PURCHASE_ORDER_ID,CLW_PURCHASE_ORDER.ORDER_ID,CLW_PURCHASE_ORDER.ERP_PO_NUM,CLW_PURCHASE_ORDER.PO_DATE,CLW_PURCHASE_ORDER.DIST_ERP_NUM,CLW_PURCHASE_ORDER.LINE_ITEM_TOTAL,CLW_PURCHASE_ORDER.PURCHASE_ORDER_TOTAL,CLW_PURCHASE_ORDER.PURCHASE_ORDER_STATUS_CD,CLW_PURCHASE_ORDER.ADD_DATE,CLW_PURCHASE_ORDER.ADD_BY,CLW_PURCHASE_ORDER.MOD_DATE,CLW_PURCHASE_ORDER.MOD_BY,CLW_PURCHASE_ORDER.PURCH_ORD_MANIFEST_STATUS_CD,CLW_PURCHASE_ORDER.STORE_ID,CLW_PURCHASE_ORDER.ERP_SYSTEM_CD,CLW_PURCHASE_ORDER.TAX_TOTAL,CLW_PURCHASE_ORDER.ERP_PO_REF_NUM,CLW_PURCHASE_ORDER.ERP_PO_REF_DATE,CLW_PURCHASE_ORDER.OUTBOUND_PO_NUM";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PurchaseOrderData Object.
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
    *@returns a populated PurchaseOrderData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PurchaseOrderData x = PurchaseOrderData.createValue();
         
         x.setPurchaseOrderId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setErpPoNum(rs.getString(3+offset));
         x.setPoDate(rs.getDate(4+offset));
         x.setDistErpNum(rs.getString(5+offset));
         x.setLineItemTotal(rs.getBigDecimal(6+offset));
         x.setPurchaseOrderTotal(rs.getBigDecimal(7+offset));
         x.setPurchaseOrderStatusCd(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setPurchOrdManifestStatusCd(rs.getString(13+offset));
         x.setStoreId(rs.getInt(14+offset));
         x.setErpSystemCd(rs.getString(15+offset));
         x.setTaxTotal(rs.getBigDecimal(16+offset));
         x.setErpPoRefNum(rs.getString(17+offset));
         x.setErpPoRefDate(rs.getDate(18+offset));
         x.setOutboundPoNum(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PurchaseOrderData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a PurchaseOrderDataVector object that consists
     * of PurchaseOrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PurchaseOrderDataVector()
     * @throws            SQLException
     */
    public static PurchaseOrderDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PURCHASE_ORDER_ID,ORDER_ID,ERP_PO_NUM,PO_DATE,DIST_ERP_NUM,LINE_ITEM_TOTAL,PURCHASE_ORDER_TOTAL,PURCHASE_ORDER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PURCH_ORD_MANIFEST_STATUS_CD,STORE_ID,ERP_SYSTEM_CD,TAX_TOTAL,ERP_PO_REF_NUM,ERP_PO_REF_DATE,OUTBOUND_PO_NUM FROM CLW_PURCHASE_ORDER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PURCHASE_ORDER.PURCHASE_ORDER_ID,CLW_PURCHASE_ORDER.ORDER_ID,CLW_PURCHASE_ORDER.ERP_PO_NUM,CLW_PURCHASE_ORDER.PO_DATE,CLW_PURCHASE_ORDER.DIST_ERP_NUM,CLW_PURCHASE_ORDER.LINE_ITEM_TOTAL,CLW_PURCHASE_ORDER.PURCHASE_ORDER_TOTAL,CLW_PURCHASE_ORDER.PURCHASE_ORDER_STATUS_CD,CLW_PURCHASE_ORDER.ADD_DATE,CLW_PURCHASE_ORDER.ADD_BY,CLW_PURCHASE_ORDER.MOD_DATE,CLW_PURCHASE_ORDER.MOD_BY,CLW_PURCHASE_ORDER.PURCH_ORD_MANIFEST_STATUS_CD,CLW_PURCHASE_ORDER.STORE_ID,CLW_PURCHASE_ORDER.ERP_SYSTEM_CD,CLW_PURCHASE_ORDER.TAX_TOTAL,CLW_PURCHASE_ORDER.ERP_PO_REF_NUM,CLW_PURCHASE_ORDER.ERP_PO_REF_DATE,CLW_PURCHASE_ORDER.OUTBOUND_PO_NUM FROM CLW_PURCHASE_ORDER");
                where = pCriteria.getSqlClause("CLW_PURCHASE_ORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PURCHASE_ORDER.equals(otherTable)){
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
        PurchaseOrderDataVector v = new PurchaseOrderDataVector();
        while (rs.next()) {
            PurchaseOrderData x = PurchaseOrderData.createValue();
            
            x.setPurchaseOrderId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setErpPoNum(rs.getString(3));
            x.setPoDate(rs.getDate(4));
            x.setDistErpNum(rs.getString(5));
            x.setLineItemTotal(rs.getBigDecimal(6));
            x.setPurchaseOrderTotal(rs.getBigDecimal(7));
            x.setPurchaseOrderStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setPurchOrdManifestStatusCd(rs.getString(13));
            x.setStoreId(rs.getInt(14));
            x.setErpSystemCd(rs.getString(15));
            x.setTaxTotal(rs.getBigDecimal(16));
            x.setErpPoRefNum(rs.getString(17));
            x.setErpPoRefDate(rs.getDate(18));
            x.setOutboundPoNum(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PurchaseOrderDataVector object that consists
     * of PurchaseOrderData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PurchaseOrderData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PurchaseOrderDataVector()
     * @throws            SQLException
     */
    public static PurchaseOrderDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PurchaseOrderDataVector v = new PurchaseOrderDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PURCHASE_ORDER_ID,ORDER_ID,ERP_PO_NUM,PO_DATE,DIST_ERP_NUM,LINE_ITEM_TOTAL,PURCHASE_ORDER_TOTAL,PURCHASE_ORDER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PURCH_ORD_MANIFEST_STATUS_CD,STORE_ID,ERP_SYSTEM_CD,TAX_TOTAL,ERP_PO_REF_NUM,ERP_PO_REF_DATE,OUTBOUND_PO_NUM FROM CLW_PURCHASE_ORDER WHERE PURCHASE_ORDER_ID IN (");

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
            PurchaseOrderData x=null;
            while (rs.next()) {
                // build the object
                x=PurchaseOrderData.createValue();
                
                x.setPurchaseOrderId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setErpPoNum(rs.getString(3));
                x.setPoDate(rs.getDate(4));
                x.setDistErpNum(rs.getString(5));
                x.setLineItemTotal(rs.getBigDecimal(6));
                x.setPurchaseOrderTotal(rs.getBigDecimal(7));
                x.setPurchaseOrderStatusCd(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setPurchOrdManifestStatusCd(rs.getString(13));
                x.setStoreId(rs.getInt(14));
                x.setErpSystemCd(rs.getString(15));
                x.setTaxTotal(rs.getBigDecimal(16));
                x.setErpPoRefNum(rs.getString(17));
                x.setErpPoRefDate(rs.getDate(18));
                x.setOutboundPoNum(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PurchaseOrderDataVector object of all
     * PurchaseOrderData objects in the database.
     * @param pCon An open database connection.
     * @return new PurchaseOrderDataVector()
     * @throws            SQLException
     */
    public static PurchaseOrderDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PURCHASE_ORDER_ID,ORDER_ID,ERP_PO_NUM,PO_DATE,DIST_ERP_NUM,LINE_ITEM_TOTAL,PURCHASE_ORDER_TOTAL,PURCHASE_ORDER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PURCH_ORD_MANIFEST_STATUS_CD,STORE_ID,ERP_SYSTEM_CD,TAX_TOTAL,ERP_PO_REF_NUM,ERP_PO_REF_DATE,OUTBOUND_PO_NUM FROM CLW_PURCHASE_ORDER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PurchaseOrderDataVector v = new PurchaseOrderDataVector();
        PurchaseOrderData x = null;
        while (rs.next()) {
            // build the object
            x = PurchaseOrderData.createValue();
            
            x.setPurchaseOrderId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setErpPoNum(rs.getString(3));
            x.setPoDate(rs.getDate(4));
            x.setDistErpNum(rs.getString(5));
            x.setLineItemTotal(rs.getBigDecimal(6));
            x.setPurchaseOrderTotal(rs.getBigDecimal(7));
            x.setPurchaseOrderStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setPurchOrdManifestStatusCd(rs.getString(13));
            x.setStoreId(rs.getInt(14));
            x.setErpSystemCd(rs.getString(15));
            x.setTaxTotal(rs.getBigDecimal(16));
            x.setErpPoRefNum(rs.getString(17));
            x.setErpPoRefDate(rs.getDate(18));
            x.setOutboundPoNum(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PurchaseOrderData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PURCHASE_ORDER_ID FROM CLW_PURCHASE_ORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PURCHASE_ORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PURCHASE_ORDER");
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
     * Inserts a PurchaseOrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PurchaseOrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PurchaseOrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PurchaseOrderData insert(Connection pCon, PurchaseOrderData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PURCHASE_ORDER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PURCHASE_ORDER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPurchaseOrderId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PURCHASE_ORDER (PURCHASE_ORDER_ID,ORDER_ID,ERP_PO_NUM,PO_DATE,DIST_ERP_NUM,LINE_ITEM_TOTAL,PURCHASE_ORDER_TOTAL,PURCHASE_ORDER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PURCH_ORD_MANIFEST_STATUS_CD,STORE_ID,ERP_SYSTEM_CD,TAX_TOTAL,ERP_PO_REF_NUM,ERP_PO_REF_DATE,OUTBOUND_PO_NUM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPurchaseOrderId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getOrderId());
        }

        pstmt.setString(3,pData.getErpPoNum());
        pstmt.setDate(4,DBAccess.toSQLDate(pData.getPoDate()));
        pstmt.setString(5,pData.getDistErpNum());
        pstmt.setBigDecimal(6,pData.getLineItemTotal());
        pstmt.setBigDecimal(7,pData.getPurchaseOrderTotal());
        pstmt.setString(8,pData.getPurchaseOrderStatusCd());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setString(13,pData.getPurchOrdManifestStatusCd());
        pstmt.setInt(14,pData.getStoreId());
        pstmt.setString(15,pData.getErpSystemCd());
        pstmt.setBigDecimal(16,pData.getTaxTotal());
        pstmt.setString(17,pData.getErpPoRefNum());
        pstmt.setDate(18,DBAccess.toSQLDate(pData.getErpPoRefDate()));
        pstmt.setString(19,pData.getOutboundPoNum());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   PO_DATE="+pData.getPoDate());
            log.debug("SQL:   DIST_ERP_NUM="+pData.getDistErpNum());
            log.debug("SQL:   LINE_ITEM_TOTAL="+pData.getLineItemTotal());
            log.debug("SQL:   PURCHASE_ORDER_TOTAL="+pData.getPurchaseOrderTotal());
            log.debug("SQL:   PURCHASE_ORDER_STATUS_CD="+pData.getPurchaseOrderStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PURCH_ORD_MANIFEST_STATUS_CD="+pData.getPurchOrdManifestStatusCd());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   TAX_TOTAL="+pData.getTaxTotal());
            log.debug("SQL:   ERP_PO_REF_NUM="+pData.getErpPoRefNum());
            log.debug("SQL:   ERP_PO_REF_DATE="+pData.getErpPoRefDate());
            log.debug("SQL:   OUTBOUND_PO_NUM="+pData.getOutboundPoNum());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setPurchaseOrderId(0);
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
     * Updates a PurchaseOrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PurchaseOrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PurchaseOrderData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PURCHASE_ORDER SET ORDER_ID = ?,ERP_PO_NUM = ?,PO_DATE = ?,DIST_ERP_NUM = ?,LINE_ITEM_TOTAL = ?,PURCHASE_ORDER_TOTAL = ?,PURCHASE_ORDER_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PURCH_ORD_MANIFEST_STATUS_CD = ?,STORE_ID = ?,ERP_SYSTEM_CD = ?,TAX_TOTAL = ?,ERP_PO_REF_NUM = ?,ERP_PO_REF_DATE = ?,OUTBOUND_PO_NUM = ? WHERE PURCHASE_ORDER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        pstmt.setString(i++,pData.getErpPoNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getPoDate()));
        pstmt.setString(i++,pData.getDistErpNum());
        pstmt.setBigDecimal(i++,pData.getLineItemTotal());
        pstmt.setBigDecimal(i++,pData.getPurchaseOrderTotal());
        pstmt.setString(i++,pData.getPurchaseOrderStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getPurchOrdManifestStatusCd());
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getErpSystemCd());
        pstmt.setBigDecimal(i++,pData.getTaxTotal());
        pstmt.setString(i++,pData.getErpPoRefNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getErpPoRefDate()));
        pstmt.setString(i++,pData.getOutboundPoNum());
        pstmt.setInt(i++,pData.getPurchaseOrderId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   PO_DATE="+pData.getPoDate());
            log.debug("SQL:   DIST_ERP_NUM="+pData.getDistErpNum());
            log.debug("SQL:   LINE_ITEM_TOTAL="+pData.getLineItemTotal());
            log.debug("SQL:   PURCHASE_ORDER_TOTAL="+pData.getPurchaseOrderTotal());
            log.debug("SQL:   PURCHASE_ORDER_STATUS_CD="+pData.getPurchaseOrderStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PURCH_ORD_MANIFEST_STATUS_CD="+pData.getPurchOrdManifestStatusCd());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   TAX_TOTAL="+pData.getTaxTotal());
            log.debug("SQL:   ERP_PO_REF_NUM="+pData.getErpPoRefNum());
            log.debug("SQL:   ERP_PO_REF_DATE="+pData.getErpPoRefDate());
            log.debug("SQL:   OUTBOUND_PO_NUM="+pData.getOutboundPoNum());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a PurchaseOrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPurchaseOrderId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPurchaseOrderId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PURCHASE_ORDER WHERE PURCHASE_ORDER_ID = " + pPurchaseOrderId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PurchaseOrderData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PURCHASE_ORDER");
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
     * Inserts a PurchaseOrderData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PurchaseOrderData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PurchaseOrderData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PURCHASE_ORDER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PURCHASE_ORDER_ID,ORDER_ID,ERP_PO_NUM,PO_DATE,DIST_ERP_NUM,LINE_ITEM_TOTAL,PURCHASE_ORDER_TOTAL,PURCHASE_ORDER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PURCH_ORD_MANIFEST_STATUS_CD,STORE_ID,ERP_SYSTEM_CD,TAX_TOTAL,ERP_PO_REF_NUM,ERP_PO_REF_DATE,OUTBOUND_PO_NUM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPurchaseOrderId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getOrderId());
        }

        pstmt.setString(3+4,pData.getErpPoNum());
        pstmt.setDate(4+4,DBAccess.toSQLDate(pData.getPoDate()));
        pstmt.setString(5+4,pData.getDistErpNum());
        pstmt.setBigDecimal(6+4,pData.getLineItemTotal());
        pstmt.setBigDecimal(7+4,pData.getPurchaseOrderTotal());
        pstmt.setString(8+4,pData.getPurchaseOrderStatusCd());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setString(13+4,pData.getPurchOrdManifestStatusCd());
        pstmt.setInt(14+4,pData.getStoreId());
        pstmt.setString(15+4,pData.getErpSystemCd());
        pstmt.setBigDecimal(16+4,pData.getTaxTotal());
        pstmt.setString(17+4,pData.getErpPoRefNum());
        pstmt.setDate(18+4,DBAccess.toSQLDate(pData.getErpPoRefDate()));
        pstmt.setString(19+4,pData.getOutboundPoNum());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PurchaseOrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PurchaseOrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PurchaseOrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PurchaseOrderData insert(Connection pCon, PurchaseOrderData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PurchaseOrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PurchaseOrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PurchaseOrderData pData, boolean pLogFl)
        throws SQLException {
        PurchaseOrderData oldData = null;
        if(pLogFl) {
          int id = pData.getPurchaseOrderId();
          try {
          oldData = PurchaseOrderDataAccess.select(pCon,id);
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
     * Deletes a PurchaseOrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPurchaseOrderId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPurchaseOrderId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PURCHASE_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PURCHASE_ORDER d WHERE PURCHASE_ORDER_ID = " + pPurchaseOrderId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPurchaseOrderId);
        return n;
     }

    /**
     * Deletes PurchaseOrderData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PURCHASE_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PURCHASE_ORDER d ");
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

