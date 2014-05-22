package com.cleanwise.service.api.dao;

/**
 * Title:        UniversalDAO
 * Description:  Database interface class.
 * Purpose:      Processes the SQL provided through the
 * connection provided and
 * returns an array of dbrow objects.
 * 
 * Copyright:    Copyright (c) 2003
 * Company:      CleanWise, Inc.
 * @author       durval
 */

import java.io.Serializable;

import java.sql.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

/**
 * <code>UniversalDAO</code>, provides access to an undelying
 * SQL database.
 * 
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class UniversalDAO
    implements Serializable {

    /**
     * class <code>dbcolumn</code>, structure class to hold the
     * data needed to describe the value in one row's column.
     *
     */
    public final class dbcolumn
        implements Serializable {

        /**
        *  <code>colType</code>, string describing the data
        *  type in the database.
        *
        */
        public String colType;
        /**
        * <code>colVal</code>, string representing the column
        * value.
        *
        */
        public String colVal;
        /**
        * <code>colName</code>, column name as provided
        * in the SQL clause.
        *
        */
        public String colName;

        public String toString() {
            return this.colVal;
        }
    }

    /**
    * <code>dbrow</code> - holds the data for a single row fetched.
    * 
    */
    public final class dbrow
        implements Serializable {

        /**
        * <code>rowNumber</code> here.
        *
        */
        public int rowNumber;
        /**
        * Describe variable <code>rowCols</code> here.
        *
        */
        public ArrayList rowCols = new ArrayList();


        /**
        * Describe <code>getColumnCount</code> method here.
        *
        * @return an <code>int</code> value
        */
        public int getColumnCount() {

            if ( null == rowCols )
                return 0;

            return rowCols.size();
        }
        /**
        * Describe <code>getColumn</code> method here.
        *
        * @param pColumnName a <code>String</code> value
        * @return a <code>dbcolumn</code> value
        */
        public dbcolumn getColumn(String pColumnName) {

            if ( null == rowCols )
                return null;

            for ( int colidx = 0; colidx < rowCols.size(); colidx++ )
            {
                dbcolumn thisCol = (dbcolumn)rowCols.get(colidx);
                if ( thisCol != null &&
                        thisCol.colName.equals(pColumnName) )
                {
                    return thisCol;
                }
            }


            return new dbcolumn();
        }

        public dbcolumn getColumn(int pCol) {

            if ( null == rowCols )
                return null;

            if ( pCol < rowCols.size() ) {
                return (dbcolumn)rowCols.get(pCol);
            }

            return new dbcolumn();
        }


        /**
        * Describe <code>getColumnName</code> method here.
        *
        * @param pCol an <code>int</code> value
        * @return a <code>String</code> value
        */
        public String getColumnName(int pCol) {

            dbcolumn col = (dbcolumn)rowCols.get(pCol);

            return col.colName;
        }

        /**
        * Describe <code>getStringValue</code> method here.
        *
        * @param pCol an <code>int</code> value
        * @return a <code>String</code> value
        */
        public String getStringValue(int pCol) {

            dbcolumn col = (dbcolumn)rowCols.get(pCol);

            return col.colVal;
        }

        /**
        * Describe <code>toString</code> method here.
        *
        * @return a <code>String</code> value
        */
        public String toString() {

            String res = "";
            Iterator itr = rowCols.iterator();
            res += "rowNumber=" + rowNumber;

            while (itr.hasNext())
            {

                dbcolumn col = (dbcolumn)itr.next();
                res += " {" + col.colName + "=" + col.colVal + "}";
            }

            return res;
        }

        /**
        * Describe <code>toHtmlTableRow</code> method here.
        *
        * @return a <code>String</code> value
        */
        public String toHtmlTableRow() {

            String res = "";
            Iterator itr = rowCols.iterator();
            res += "\n<tr>";

            while (itr.hasNext())
            {

                dbcolumn col = (dbcolumn)itr.next();
                res += " <td> " + col.colVal + "</td>";
            }

            res += "</tr>";

            return res;
        }

        /**
        * Describe <code>toHtmlTableHeader</code> method here.
        *
        * @return a <code>String</code> value
        */
        public String toHtmlTableHeader() {

            String res = "";
            Iterator itr = rowCols.iterator();
            res += "\n<tr>";

            while (itr.hasNext())
            {

                dbcolumn col = (dbcolumn)itr.next();
                res += " <td><b>" + col.colName + "</b></td>";
            }

            res += "</tr>";

            return res;
        }

        /**
        * Describe <code>toTSV</code> method here.
        *
        * @return a <code>String</code> value
        */
        public String toTSV() {

            String res = "";
            Iterator itr = rowCols.iterator();

            while (itr.hasNext())
            {

                dbcolumn col = (dbcolumn)itr.next();
                res += col.colVal + "\t";
            }

            return res;
        }

        /**
        * Describe <code>toCSV</code> method here.
        *
        * @return a <code>String</code> value
        */
        public String toCSV() {

            String res = "";
            Iterator itr = rowCols.iterator();

            while (itr.hasNext())
            {

                if (res.length() > 0)
                    res += ", ";

                dbcolumn col = (dbcolumn)itr.next();
                res += col.colVal;
            }

            return res;
        }
    }

    /**
     * Creates a new <code>UniversalDAO</code> instance.
     *
     */
    public UniversalDAO() {
    }

    /**
     * Describe <code>getData</code> method here.
     *
     * @param pCon a <code>Connection</code> value
     * @param pSQL a <code>String</code> value
     * @return an <code>ArrayList</code>, list of dbrow entries.
     * @exception Exception if an error occurs
     */
    public static ArrayList getData(Connection pCon, String pSQL)
    throws Exception {

        UniversalDAO udao = new UniversalDAO();

        return udao.getDbData(pCon, pSQL);
    }

    /**
     * Describe <code>getDbData</code> method here.
     *
     * @param pCon a <code>Connection</code> value
     * @param pSQL a <code>String</code> value
     * @return an <code>ArrayList</code> value
     * @exception Exception if an error occurs
     */
    private ArrayList getDbData(Connection pCon, String pSQL)
    throws Exception {

        ArrayList res = new ArrayList();
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = pCon.createStatement();
            rs = stmt.executeQuery(pSQL);

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            int rownumb = 1;

            while (rs.next())
            {

                dbrow nrow = new dbrow();
                nrow.rowNumber = rownumb++;

                for (int j = 1; j <= cols; j++)
                {

                    dbcolumn ncol = new dbcolumn();
                    ncol.colName = rsmd.getColumnName(j);
                    ncol.colType = rsmd.getColumnTypeName(j);
                    ncol.colVal = rs.getString(j);
                    nrow.rowCols.add(ncol);
                }

                res.add(nrow);
            }
        }
        finally
        {

            if (rs != null)
            {
                rs.close();
            }

            if (stmt != null)
            {
                stmt.close();
            }
        }

        return res;
    }

    private Document getXmlData_p(Connection pCon, String pSQL)
    throws Exception {

        ArrayList res = getDbData(pCon, pSQL);



        DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
        docfactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = docfactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
        }
        //Document xmlres = docBuilder.newDocument();
        Document xmlres = docBuilder.getDOMImplementation().createDocument("", "result-set", null);
        //Document xmlres = new Document();
        //Node root = xmlres.createElement("result-set");
        Element  root = xmlres.getDocumentElement();
        xmlres.appendChild(root);

        for (int j = 1; res != null && j < res.size(); j++)
        {

            dbrow r = (dbrow)res.get(j);
            Element xr = xmlres.createElement("dbrow");
            xr.setAttribute("rowNumber", String.valueOf(r.rowNumber));

            Iterator itr = r.rowCols.iterator();
            while (itr.hasNext())
            {

                dbcolumn col = (dbcolumn)itr.next();
                Element node;
                node = xmlres.createElement(col.colName);
                node.appendChild(xmlres.createTextNode(col.colVal));
                xr.appendChild(node);
            }

            root.appendChild(xr);

        }

        return xmlres;
    }

    /**
     * Describe <code>getXmlData</code> method here.
     * Note: The XmlDocument is not serializable.  It must be
     * converted to 
     *
     * @param pCon a <code>Connection</code> value
     * @param pSQL a <code>String</code> value
     * @return a <code>XmlDocument</code> value
     * @exception Exception if an error occurs
     */
    public static Document getXmlData(Connection pCon, String pSQL)
    throws Exception {
        UniversalDAO udao = new UniversalDAO();

        return udao.getXmlData_p(pCon, pSQL);
    }

    public static dbrow mkEmptyRow() {
        UniversalDAO udao = new UniversalDAO();
        return udao._mkEmptyRow();
    }
    
    public dbrow _mkEmptyRow() {
        dbcolumn ncol = new dbcolumn();
        ncol.colName = "empty";
        ncol.colType = "none";
        ncol.colVal = "";   
             
        dbrow nrow = new dbrow();
        nrow.rowCols.add(ncol);
        
        return nrow;
    }

    public static  UserData getUserByName (Connection conn,
					   String pUserName, 
					   int pStoreId) 
	throws Exception        {

        DBCriteria crit = new DBCriteria();
	crit.addEqualTo(UserDataAccess.USER_NAME, pUserName);

        int max = 1000;
	UserDataVector
        udv = UserDataAccess.select(conn, crit, max);

	if (udv == null || udv.size() == 0 ) {
	    throw new DataNotFoundException
		(" no user found for pUserName="
		 + pUserName );
	}

        UserData ud = (UserData)udv.get(0);
	if ( pStoreId > 0 ) {
	    // verify the store
	    DBCriteria crit2 = new DBCriteria();
	    crit2.addEqualTo(UserAssocDataAccess.USER_ID, ud.getUserId());
	    crit2.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID, pStoreId);
	    IdVector foundids = UserAssocDataAccess.selectIdOnly(conn, crit2);
	    if ( null == foundids || foundids.size()==0 ) {
		throw new DataNotFoundException
		    (" no user found for pUserName="
		     + pUserName + " for store is: " + pStoreId );
	    }
	}

	return ud;
    }

}
