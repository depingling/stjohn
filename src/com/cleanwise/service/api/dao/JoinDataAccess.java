/*
 * JoinDataAccess.java
 *
 * Created on February 2, 2004, 1:18 PM
 */

package com.cleanwise.service.api.dao;
import com.cleanwise.service.api.util.DBCriteria;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.cleanwise.service.api.framework.DataAccess;
import com.cleanwise.service.api.framework.ValueObject;



import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.IdVector;
/**
 * Preforms table joins on a simple 1 to 1 relationship.
 * @author  bstevens
 */
public class JoinDataAccess {

    /**
     *Returns a 2d list with the outer list representing each row that is returned by
     *the resultset and the inner list representing the parsed value objects.  They will be
     *orderd in the same way that they are added to the DBCriteria object using the
     *addDataAccessForJoin(<DataAccess Object>) method.
     *@param Connection open database connection
     *@param DBCriteria the criteria to use
     */
    public static List select(Connection pCon, DBCriteria pCriteria)
    throws SQLException{
        return select(pCon, pCriteria,0);
    }



    /**
     *Returns a @see String representation of the complete sql that will be used when
     *the @see select method is called.  At least one call to DBCriteria.addDataAccessForJoin
     *must be made or a NullPointerException will be thrown.
     *@param DBCriteria the criteria to use
     */
    public static String getSqlSelect(DBCriteria pCriteria){
        return getSqlSelectIdOnly(null,null,pCriteria);
    }

    /**
     *Returns a @see String representation of the complete sql that will be used when
     *the @see select method is called.  At least one call to DBCriteria.addDataAccessForJoin
     *must be made or a NullPointerException will be thrown.
     *@param DBCriteria the criteria to use
     */
    public static String getSqlSelectIdOnly(String pTableName, String pIdName, DBCriteria pCriteria){
        if(pCriteria.getDataAccessObjectsForJoin() == null || pCriteria.getDataAccessObjectsForJoin().isEmpty()){
            throw new NullPointerException("No Data Access Objects In Criteria For Joining. Must Call addDataAccessForJoin(<Data Access Object>).");
        }
        Iterator it = pCriteria.getDataAccessObjectsForJoin().iterator();
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT ");
        if(pIdName != null){
            if(pTableName != null){
                sqlBuf.append(pTableName);
                sqlBuf.append(".");
            }
            sqlBuf.append(pIdName);
        }else{
            while(it.hasNext()){
                DataAccess da = (DataAccess) it.next();
                sqlBuf.append(da.getSelectColumns());
                if(it.hasNext()){
                    sqlBuf.append(",");
                }
            }
        }
        sqlBuf.append(getSqlFromClause(pCriteria));
        return sqlBuf.toString();
    }

    /**
     *Returns the from and after clause (including order by) as a String.
     */
    public static String getSqlFromClause(DBCriteria pCriteria){
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append(" FROM ");


        Iterator it = pCriteria.getJoinTables().iterator();
        while(it.hasNext()){
            String otherTable = (String) it.next();
            sqlBuf.append(otherTable);
            if(it.hasNext()){
                sqlBuf.append(",");
            }
        }
        String where = pCriteria.getSqlClause();
        if(where != null && where.length() > 0){
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }
        return sqlBuf.toString();
    }

    /**
     *Returns a 2d list with the outer list representing each row that is returned by
     *the resultset and the inner list representing the parsed value objects.  They will be
     *orderd in the same way that they are added to the DBCriteria object using the
     *addDataAccessForJoin(<DataAccess Object>) method.
     *@param Connection open database connection
     *@param DBCriteria the criteria to use
     *@param pMaxRows maximum number of rows to return
     */
    public static List select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
    throws SQLException{
        if(pCon == null){
            throw new NullPointerException("Connection cannot be null");
        }
        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
            stmt.setMaxRows(pMaxRows);
        }

        ResultSet rs=stmt.executeQuery(getSqlSelect(pCriteria));
        ArrayList returnList = new ArrayList();
        int len=pCriteria.getDataAccessObjectsForJoin().size();
        //int ct = 0;
        //long timer = System.currentTimeMillis();
        while (rs.next()) {
            ArrayList valList = new ArrayList();
            returnList.add(valList);
            //it = pCriteria.getDataAccessObjectsForJoin().iterator();

            int offset = 0;
            //while(it.hasNext()){
            for(int i=0;i<len;i++){
                //DataAccess da = (DataAccess) it.next();
                DataAccess da = (DataAccess) pCriteria.getDataAccessObjectsForJoin().get(i);
                ValueObject val = da.parseResultSet(rs,offset);
                valList.add(val);
                offset += da.getColumnCount();
            }
            //ct++;
            //if(ct%100 == 0){
            //    long time = (System.currentTimeMillis() - timer) / 1000;
            //    timer = System.currentTimeMillis();
            //}
        }
        rs.close();
        stmt.close();
        return returnList;
    }

    /**
     *Same as @see selectIdOnly but will select non numeric values as well
     *@param Connection open database connection
     *@param table the table to look for the column in
     *@param the column with the data to be returned
     *@param DBCriteria the criteria to use
     *@param pMaxRows maximum number of rows to return
     */
    public static List selectColumnOnly(Connection pCon, String table, String column, DBCriteria pCriteria, int pMaxRows)
    throws SQLException{

        if(pCon == null){
            throw new NullPointerException("Connection cannot be null");
        }
        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
            stmt.setMaxRows(pMaxRows);
        }

        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("SELECT DISTINCT ");
        sqlBuf.append(table);
        sqlBuf.append(".");
        sqlBuf.append(column);
        sqlBuf.append(getSqlFromClause(pCriteria));

        ResultSet rs=stmt.executeQuery(sqlBuf.toString());
        ArrayList returnList = new ArrayList();
        while (rs.next()) {
            returnList.add(rs.getObject(1));
        }
        rs.close();
        stmt.close();
        return returnList;
    }

    /**
     *Returns an IdVector populated with Integer values for the specifed table and id
     *addDataAccessForJoin(<DataAccess Object>) method.
     *@param Connection open database connection
     *@param DBCriteria the criteria to use
     *@param pMaxRows maximum number of rows to return
     */
    public static IdVector selectIdOnly(Connection pCon, String table, String column, DBCriteria pCriteria, int pMaxRows)
    throws SQLException{

        if(pCon == null){
            throw new NullPointerException("Connection cannot be null");
        }
        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
            stmt.setMaxRows(pMaxRows);
        }

        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("SELECT DISTINCT ");
        sqlBuf.append(table);
        sqlBuf.append(".");
        sqlBuf.append(column);
        sqlBuf.append(getSqlFromClause(pCriteria));

        ResultSet rs=stmt.executeQuery(sqlBuf.toString());
        IdVector returnList = new IdVector();
        while (rs.next()) {
            returnList.add(new Integer(rs.getInt(1)));
        }
        rs.close();
        stmt.close();
        return returnList;
    }

    /**
     *Selects a single tables data into the passed in list so that you dont have to
     *iterate over the 2d list produced by the standard select when you are only dealing
     *with a single table of data.
     */
    public static void selectTableInto(DataAccess pDataAccessObjectsForJoin, List intoList, Connection pCon, DBCriteria pCriteria, int pMaxRows)
    throws SQLException{
    	if(pCriteria.getDataAccessObjectsForJoin() != null && pCriteria.getDataAccessObjectsForJoin().size() != 0){
            throw new SQLException ("Multiple data access objects selected for return: "+pCriteria.getDataAccessObjectsForJoin().size());
        }
    	pCriteria.addDataAccessForJoin(pDataAccessObjectsForJoin);

    	List l1 = select(pCon, pCriteria, pMaxRows);
        Iterator it = l1.iterator();
        while(it.hasNext()){
            List l2 = (List) it.next();
            intoList.add(l2.get(0));
        }
        pCriteria.getDataAccessObjectsForJoin().clear();
    }


    public static void main(String[] a) throws Exception{
        int busEntityId = 53;
        DBCriteria crit = new DBCriteria();
        DBCriteria orCrit=new DBCriteria();
        orCrit.addEqualTo(InvoiceCustDetailDataAccess.REBATE_STATUS_CD,
        RefCodeNames.REBATE_STATUS_CD.PENDING);
        DBCriteria rebCon2 = new DBCriteria();
        rebCon2.addIsNull(InvoiceCustDetailDataAccess.REBATE_STATUS_CD);
        orCrit.addOrCriteria(rebCon2);

        crit.addIsolatedCriterita(orCrit);

        crit.addJoinCondition(InvoiceCustDetailDataAccess.CLW_INVOICE_CUST_DETAIL,
        InvoiceCustDetailDataAccess.ORDER_ITEM_ID,
        OrderItemDataAccess.CLW_ORDER_ITEM,
        OrderItemDataAccess.ORDER_ITEM_ID);
        crit.addJoinCondition(ItemMappingDataAccess.CLW_ITEM_MAPPING,
        ItemMappingDataAccess.ITEM_ID,
        OrderItemDataAccess.CLW_ORDER_ITEM,
        OrderItemDataAccess.ITEM_ID);
        crit.addJoinTableEqualTo(ItemMappingDataAccess.CLW_ITEM_MAPPING,
        ItemMappingDataAccess.BUS_ENTITY_ID,
        Integer.toString(busEntityId));

        crit.addDataAccessForJoin(new ItemMappingDataAccess());
        crit.addDataAccessForJoin(new InvoiceCustDetailDataAccess());

        select(null, crit, 0);
    }
}
