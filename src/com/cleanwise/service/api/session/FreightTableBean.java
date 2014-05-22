package com.cleanwise.service.api.session;

/**
 * Title:        FreightTableBean
 * Description:  Bean implementation for FreightTable Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving FreightTable information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       CleanWise, Inc.
 */

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.dao.FreightTableCriteriaDataAccess;
import com.cleanwise.service.api.dao.FreightTableDataAccess;
import com.cleanwise.service.api.framework.CatalogServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.FreightTableCriteriaData;
import com.cleanwise.service.api.value.FreightTableCriteriaDataVector;
import com.cleanwise.service.api.value.FreightTableCriteriaDescData;
import com.cleanwise.service.api.value.FreightTableCriteriaDescDataVector;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.FreightTableDataVector;
import com.cleanwise.service.api.value.FreightTableView;
import com.cleanwise.service.api.value.FreightTableViewVector;
import com.cleanwise.service.api.value.IdVector;

public class FreightTableBean extends CatalogServicesAPI
{

	private static final Logger log = Logger.getLogger(FreightTableBean.class);
	/**
     *
     */
    public FreightTableBean() {}


    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     *
    */
    public void ejbCreate(int pFreightTableId) throws CreateException, RemoteException {}



    /**
     * Describe <code>getFreightTable</code> method here.
     *
     * @param pFreightTableId an <code>int</code> value
     * @return a <code>FreightTableData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public FreightTableData getFreightTable(int pFreightTableId)
       throws RemoteException, DataNotFoundException {
        return getFreightTable(pFreightTableId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

    public FreightTableData getFreightTable(int pFreightTableId, String chargeType)
       throws RemoteException, DataNotFoundException {

        FreightTableData freightTable = null;
        Connection conn = null;
        try {
            conn = getConnection();
            if (chargeType == null) {
               freightTable = FreightTableDataAccess.select(conn, pFreightTableId);
            } else {
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_ID, pFreightTableId);
                crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
                FreightTableDataVector ftDV = FreightTableDataAccess.select(conn, crit);
                if (ftDV != null && ftDV.size() > 0) {
                    freightTable = (FreightTableData) ftDV.get(0);
                }
            }
        } catch (DataNotFoundException e) {
            //throw e;
        } catch (Exception e) {
            throw new RemoteException("getFreightTable: "+e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
        return freightTable;
    }

    /**
     * Describe <code>getFreightTable</code> method here.
     *
     * @param pFreightTableId an <code>int</code> value
     * @param pStoreId an <code>int</code> value
     * @return a <code>FreightTableData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public FreightTableData getFreightTable(int pFreightTableId, int pStoreId)
        throws RemoteException {
        return getFreightTable(pFreightTableId, pStoreId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

   public FreightTableData getFreightTable(int pFreightTableId, int pStoreId, String chargeType)
     throws RemoteException {

        FreightTableData freightTable = null;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_ID, pFreightTableId);
            dbc.addEqualTo(FreightTableDataAccess.STORE_ID,pStoreId);
            if (chargeType != null) {
                dbc.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
            }
            FreightTableDataVector ftDV = FreightTableDataAccess.select(conn,dbc);
            if(ftDV.size()==0) {
                String errorMessage = "^clw^No freight table found. Freight table id: "+
                    pFreightTableId+"^clw^";
                throw new Exception(errorMessage);
            }
            freightTable = (FreightTableData) ftDV.get(0);
        } catch (Exception e) {
            throw new RemoteException("getFreightTable: "+e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    
        return freightTable;
    }

    /**
     * Get all FreightTables that match the given name.  The arguments specify
     * whether the name is interpreted as a pattern or exact match.
     *
     * @param pName a <code>String</code> value with FreightTable name or pattern
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
     *        BEGINS_WITH_IGNORE_CASE
     * @param pStoreID a <code>int</code> store id value
     * @return a <code>FreightTableDataVector</code> of matching FreightTables
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getFreightTableByName(String pName, int pMatch, int pStoreId)
        throws RemoteException {
        return getFreightTableByName(pName, pMatch, pStoreId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

  public FreightTableDataVector getFreightTableByName(String pName, int pMatch, int pStoreId, String chargeType)
	throws RemoteException {

  	FreightTableDataVector freightTableVec = new FreightTableDataVector();

	  Connection conn = null;
	  try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(FreightTableDataAccess.STORE_ID, pStoreId);
      switch (pMatch) {
      case FreightTable.EXACT_MATCH:
        crit.addEqualTo(FreightTableDataAccess.SHORT_DESC, pName);
        break;
	    case FreightTable.EXACT_MATCH_IGNORE_CASE:
		    crit.addEqualToIgnoreCase(FreightTableDataAccess.SHORT_DESC,
					  pName);
		    break;
	    case FreightTable.BEGINS_WITH:
		    crit.addLike(FreightTableDataAccess.SHORT_DESC, pName+"%");
		    break;
	    case FreightTable.BEGINS_WITH_IGNORE_CASE:
		    crit.addLikeIgnoreCase(FreightTableDataAccess.SHORT_DESC,
					  pName+"%");
		    break;
	    case FreightTable.CONTAINS:
		    crit.addLike(FreightTableDataAccess.SHORT_DESC, "%"+pName+"%");
		    break;
	    case FreightTable.CONTAINS_IGNORE_CASE:
		    crit.addLikeIgnoreCase(FreightTableDataAccess.SHORT_DESC,
					  "%"+pName+"%");
		   break;
	    default:
		    throw new RemoteException("getFreightTableByName: Bad match specification");
	    }
        if (chargeType != null) {
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
        }
      crit.addOrderBy(FreightTableDataAccess.SHORT_DESC);
	    freightTableVec =
		         FreightTableDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getFreightTableByName: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return freightTableVec;
    }

    /**
     * Get all FreightTables that match the given name.  The arguments specify
     * whether the name is interpreted as a pattern or exact match.
     *
     * @param pName a <code>String</code> value with FreightTable name or pattern
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
     *        BEGINS_WITH_IGNORE_CASE
     * @return a <code>FreightTableDataVector</code> of matching FreightTables
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getFreightTableByName(String pName, int pMatch)
       throws RemoteException {
       return getFreightTableByName(pName, pMatch, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

    public FreightTableDataVector getFreightTableByName(String pName, int pMatch, String chargeType)
	throws RemoteException {

	FreightTableDataVector FreightTableVec = new FreightTableDataVector();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();
	    switch (pMatch) {
	    case FreightTable.EXACT_MATCH:
		crit.addEqualTo(FreightTableDataAccess.SHORT_DESC, pName);
		break;
	    case FreightTable.EXACT_MATCH_IGNORE_CASE:
		crit.addEqualToIgnoreCase(FreightTableDataAccess.SHORT_DESC,
					  pName);
		break;
	    case FreightTable.BEGINS_WITH:
		crit.addLike(FreightTableDataAccess.SHORT_DESC, pName+"%");
		break;
	    case FreightTable.BEGINS_WITH_IGNORE_CASE:
		crit.addLikeIgnoreCase(FreightTableDataAccess.SHORT_DESC,
					  pName+"%");
		break;
	    case FreightTable.CONTAINS:
		crit.addLike(FreightTableDataAccess.SHORT_DESC, "%"+pName+"%");
		break;
	    case FreightTable.CONTAINS_IGNORE_CASE:
		crit.addLikeIgnoreCase(FreightTableDataAccess.SHORT_DESC,
					  "%"+pName+"%");
		break;
	    default:
		throw new RemoteException("getFreightTableByName: Bad match specification");
	    }
        if (chargeType != null) {
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
        }
	    FreightTableVec =
		FreightTableDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getFreightTableByName: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return FreightTableVec;
    }

    /**
     * Get all the FreightTables for the store.
     * @param pStoreId the store id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getAllFreightTables(int pStoreId)
       throws RemoteException {
       return getAllFreightTables(pStoreId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

  public FreightTableDataVector getAllFreightTables(int pStoreId, String chargeType)
	throws RemoteException {

	  FreightTableDataVector freightTableVec = new FreightTableDataVector ();

	  Connection conn = null;
	  try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();
        crit.addEqualTo(FreightTableDataAccess.STORE_ID, pStoreId);
        if (chargeType != null) {
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
        }
        crit.addOrderBy(FreightTableDataAccess.SHORT_DESC);
        freightTableVec =  FreightTableDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getAllFreightTables: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return freightTableVec;
    }

    /**
     * Get all the FreightTables.
     *
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getAllFreightTables()
        throws RemoteException {
        return getAllFreightTables(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

    /**
     * Get all the DiscountTables (entries in the clw_freight_table DB table, 
     * which point to the Discount)
     *
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getAllDiscountTables()
        throws RemoteException {
        return getAllFreightTables(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT);
    }
    public FreightTableDataVector getAllFreightTables(String chargeType)
	throws RemoteException {

	FreightTableDataVector FreightTableVec = new FreightTableDataVector ();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();
        if (chargeType != null) {
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
        }
        FreightTableVec = FreightTableDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getAllFreightTables: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return FreightTableVec;
    }

    /**
     * Get all the FreightTables of the store
     * @param pStoreId store id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getStoreFreightTables(int pStoreId)
        throws RemoteException {
        return getStoreFreightTables(pStoreId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

    public FreightTableDataVector getStoreFreightTables(int pStoreId, String chargeType)
	   throws RemoteException {

	   FreightTableDataVector FreightTableVec = new FreightTableDataVector ();

	  Connection conn = null;
	  try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();
        crit.addEqualTo(FreightTableDataAccess.STORE_ID, pStoreId);
        if (chargeType != null) {
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
        }
        crit.addOrderBy(FreightTableDataAccess.SHORT_DESC);
        FreightTableVec = FreightTableDataAccess.select(conn, crit);

	  } catch (Exception e) {
	    throw new RemoteException("getAllFreightTables: " + e.getMessage());
	  } finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	  }

	  return FreightTableVec;
  }


    /**
     * Get all the FreightTables of the distributor
     * @param pDistId distributor id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getDistributorFreightTables(int pDistId)
        throws RemoteException {

	FreightTableDataVector FreightTableVec = new FreightTableDataVector ();

	Connection conn = null;
	try {
        conn = getConnection();
	    DBCriteria crit = new DBCriteria();
            crit.addEqualTo(FreightTableDataAccess.DISTRIBUTOR_ID, pDistId);

            crit.addOrderBy(FreightTableDataAccess.SHORT_DESC);
            FreightTableVec = FreightTableDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getDistributorFreightTables: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return FreightTableVec;
    }


    /**
     * Get all the FreightTables of the distributor in the store
     * @param pStoreId store id
     * @param pDistId distributor id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getStoreDistributorFreightTables(int pStoreId, int pDistId)
        throws RemoteException {
        return getStoreDistributorFreightTables(pStoreId, pDistId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
    }

    public FreightTableDataVector getStoreDistributorFreightTables(int pStoreId, int pDistId, String chargeType)
        throws RemoteException {

	   FreightTableDataVector FreightTableVec = new FreightTableDataVector ();

	   Connection conn = null;
	   try {
            conn = getConnection();
	       DBCriteria crit = new DBCriteria();
            crit.addEqualTo(FreightTableDataAccess.STORE_ID, pStoreId);
            crit.addEqualTo(FreightTableDataAccess.DISTRIBUTOR_ID, pDistId);
            if (chargeType != null) {
                crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, chargeType);
            }
            crit.addOrderBy(FreightTableDataAccess.SHORT_DESC);
            FreightTableVec = FreightTableDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getStoreDistributorFreightTables: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return FreightTableVec;
    }


     /**
     * Get all the FreightTableCriteria of the distributor in the store
     * @param pStoreId store id
     * @param pDistId distributor id
     * @param pFreightTableId the specific id of the freight handler.  Other criteria still evaluated.
     * @return a <code>FreightTableCriteriaDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDataVector getStoreDistributorFreightTableCriteria(int pStoreId, int pDistId, int pFreightTableId)
        throws RemoteException {

    log.info("*****XXXXXXXXXXXXXX_2: pFreightTableId = " + pFreightTableId);
    
	FreightTableDataVector FreightTableVec = new FreightTableDataVector ();
        FreightTableCriteriaDataVector freightCriteriaVec = new FreightTableCriteriaDataVector();

	Connection conn = null;
	try {
            conn = getConnection();
	        DBCriteria crit = new DBCriteria();
            crit.addEqualTo(FreightTableDataAccess.STORE_ID, pStoreId);
            crit.addEqualTo(FreightTableDataAccess.DISTRIBUTOR_ID, pDistId);
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT); // SVC: FREIGHT ONLY ! (NOT BOTH FREIGHT and DISCOUNT !)
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_STATUS_CD,RefCodeNames.FREIGHT_TABLE_STATUS_CD.ACTIVE); // SVC: ONLY "ACTIVE" DISCOUNT TABLES !
            
            if(pFreightTableId > 0){
            	crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_ID, pFreightTableId);
            }
            crit.addOrderBy(FreightTableDataAccess.SHORT_DESC);
            FreightTableVec = FreightTableDataAccess.select(conn, crit);
            
            log.info("getStoreDistributorFreightTableCriteria() method: FreightTableVec = " + FreightTableVec);

            if (null != FreightTableVec && FreightTableVec.size() > 0) {
                FreightTableData freightTableD = (FreightTableData)FreightTableVec.get(0);
                crit = new DBCriteria();
                crit.addEqualTo(FreightTableCriteriaDataAccess.FREIGHT_TABLE_ID, freightTableD.getFreightTableId());
                crit.addIsNull(FreightTableCriteriaDataAccess.CHARGE_CD);
                crit.addOrderBy(FreightTableCriteriaDataAccess.UI_ORDER);
                crit.addOrderBy(FreightTableCriteriaDataAccess.SHORT_DESC);

                freightCriteriaVec = FreightTableCriteriaDataAccess.select(conn, crit);
                log.info("getStoreDistributorFreightTableCriteria() method: freightCriteriaVec_1 = " + freightCriteriaVec);
            }

	} catch (Exception e) {
	    throw new RemoteException("getStoreDistributorFreightTableCriteria: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
    log.info("getStoreDistributorFreightTableCriteria() method: freightCriteriaVec_2 = " + freightCriteriaVec);
	return freightCriteriaVec;
    }

    /**
     * Get all the FreightTableDiscountCriteria of the distributor in the store
     * @param pStoreId store id
     * @param pDistId distributor id
     * @param pFreightTableId the specific id of the freight handler.  Other criteria still evaluated.
     * @return a <code>FreightTableCriteriaDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDataVector getStoreDistributorFreightTableDiscountCriteria(int pStoreId, int pDistId, int pFreightTableId)
        throws RemoteException {

        log.info ("***SVC: pStoreId = " + pStoreId);
        log.info ("***SVC: pDistId = " + pDistId);
        log.info ("***SVC: pFreightTableId = " + pFreightTableId);
        
    	FreightTableDataVector FreightTableVec = new FreightTableDataVector ();
        FreightTableCriteriaDataVector freightCriteriaDiscountVec = new FreightTableCriteriaDataVector();

	Connection conn = null;
	try {
            conn = getConnection();
	    DBCriteria crit = new DBCriteria();
            crit.addEqualTo(FreightTableDataAccess.STORE_ID, pStoreId);
            crit.addEqualTo(FreightTableDataAccess.DISTRIBUTOR_ID, pDistId);
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT); //SVC
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_STATUS_CD,RefCodeNames.FREIGHT_TABLE_STATUS_CD.ACTIVE);
            
            if(pFreightTableId > 0){ // I think I should not use pFreightTableId here, because it is NOT the same as the
            	                     // Discount Table id (new design of the Freight and Contract DB tables) ???
            	crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_ID, pFreightTableId);
            }
            crit.addOrderBy(FreightTableDataAccess.SHORT_DESC);
            FreightTableVec = FreightTableDataAccess.select(conn, crit);
            
            log.info("***SVC: DiscountTableVec = " + FreightTableVec);
            
            if (null != FreightTableVec && FreightTableVec.size() > 0) {
                FreightTableData freightTableD = (FreightTableData)FreightTableVec.get(0);
                crit = new DBCriteria();
                crit.addEqualTo(FreightTableCriteriaDataAccess.FREIGHT_TABLE_ID, freightTableD.getFreightTableId());
                //crit.addIsNull(FreightTableCriteriaDataAccess.CHARGE_CD);
                crit.addEqualTo( FreightTableCriteriaDataAccess.CHARGE_CD, RefCodeNames.CHARGE_CD.DISCOUNT );
                crit.addOrderBy(FreightTableCriteriaDataAccess.UI_ORDER);
                crit.addOrderBy(FreightTableCriteriaDataAccess.SHORT_DESC);

                freightCriteriaDiscountVec = FreightTableCriteriaDataAccess.select(conn, crit);
            }

	} catch (Exception e) {
	    throw new RemoteException("getStoreDistributorFreightTableDiscountCriteria: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return freightCriteriaDiscountVec;
    }


   /**
     * Describe <code>addFreightTable</code> method here.
     *
     * @param pFreightTableData a <code>FreightTableData</code> value
     * @return a <code>FreightTableData</code> value
     * @exception RemoteException if an error occurs
     */
    public FreightTableData addFreightTable(FreightTableData pFreightTableData)
	throws RemoteException
    {
	return updateFreightTable(pFreightTableData);
    }

    /**
     * Updates the FreightTable information values to be used by the request.
     * @param pFreightTableData  the FreightTableData FreightTable data.
     * @return a <code>FreightTableData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public FreightTableData updateFreightTable(FreightTableData pFreightTableData)
	throws RemoteException
    {
	Connection conn = null;

	try {
	    conn = getConnection();

	    FreightTableData freightTable = pFreightTableData;
		if (freightTable.getFreightTableId() == 0) {
		    freightTable = FreightTableDataAccess.insert(conn, freightTable);
		} else {
		    FreightTableDataAccess.update(conn, freightTable);
		}

    	return freightTable;
	} catch (Exception e) {
	    throw new RemoteException("updateFreightTable: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

    }

    /**
     * <code>removeFreightTable</code> may be used to remove an 'unused' FreightTable.
     * An unused FreightTable is a FreightTable with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a FreightTable that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pFreightTableData a <code>FreightTableData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeFreightTable(FreightTableData pFreightTableData)
	throws RemoteException
    {
	Connection conn = null;
	try {
	    conn = getConnection();

	    int FreightTableId = pFreightTableData.getFreightTableId();

	    FreightTableDataAccess.remove(conn, FreightTableId);
	} catch (Exception e) {
	    throw new RemoteException("removeFreightTable: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
    }



    /**
     * Get all the FreightTableCriterias.
     *
     * @return a <code>FreightTableCriteriaDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDataVector getAllFreightTableCriterias(int pFreightTableId)
	throws RemoteException {

	FreightTableCriteriaDataVector freightTableCriteriaVec = new FreightTableCriteriaDataVector ();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

            crit.addEqualTo(FreightTableCriteriaDataAccess.FREIGHT_TABLE_ID, pFreightTableId);
            crit.addOrderBy(FreightTableCriteriaDataAccess.LOWER_AMOUNT);
            freightTableCriteriaVec =
		FreightTableCriteriaDataAccess.select(conn, crit);

	} catch (Exception e) {
	    throw new RemoteException("getAllFreightTableCriterias: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return freightTableCriteriaVec;
    }


    /**
     * Get all the FreightTableCriteriaDescs.
     *
     * @return a <code>FreightTableCriteriaDescDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDescDataVector getAllFreightTableCriteriaDescs(int pFreightTableId)
	throws RemoteException {
        FreightTableCriteriaDescDataVector criteriaDescV = new FreightTableCriteriaDescDataVector();
        FreightTableCriteriaDataVector criteriaV = getAllFreightTableCriterias(pFreightTableId);
        for(int i = 0; i < criteriaV.size(); i++) {
            FreightTableCriteriaData criteriaD = (FreightTableCriteriaData) criteriaV.get(i);
            FreightTableCriteriaDescData criteriaDescD = FreightTableCriteriaDescData.createValue();

            criteriaDescD = fillCriteriaDescData(criteriaD);

            criteriaDescV.add(criteriaDescD);
        }

        return criteriaDescV;
    }

   /**
     * Describe <code>addFreightTableCriteria</code> method here.
     *
     * @param pFreightTableCriteriaData a <code>FreightTableCriteriaData</code> value
     * @param pFreightTableId a <code>int</code> value
     * @param pUser a <code>String</code> value
     * @return a <code>FreightTableCriteriaData</code> value
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaData addFreightTableCriteria(int pFreightTableId, FreightTableCriteriaData pFreightTableCriteriaData, String pUser)
	throws RemoteException
    {
	Connection conn = null;

        FreightTableCriteriaData freightTableCriteria = pFreightTableCriteriaData;
	try {
	    conn = getConnection();

	    if (freightTableCriteria.isDirty()) {
                freightTableCriteria.setModBy(pUser);
                freightTableCriteria.setFreightTableId(pFreightTableId);
		if (freightTableCriteria.getFreightTableCriteriaId() == 0) {
                    freightTableCriteria.setAddBy(pUser);
		    FreightTableCriteriaDataAccess.insert(conn, freightTableCriteria);
		} else {
                    FreightTableCriteriaDataAccess.update(conn, freightTableCriteria);
                }
	    }

	} catch (Exception e) {
	    throw new RemoteException("updateFreightTable: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return freightTableCriteria;
    }


   /**
     * Describe <code>addFreightTableCriteria</code> method here.
     *
     * @param pFreightTableCriteriaDescData a <code>FreightTableCriteriaDescData</code> value
     * @param pFreightTableId a <code>int</code> value
     * @param pUser a <code>String</code> value
     * @return a <code>FreightTableCriteriaDescData</code> value
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDescData addFreightTableCriteria(int pFreightTableId, FreightTableCriteriaDescData pFreightTableCriteriaDescData, String pUser)
	throws RemoteException
    {
        FreightTableCriteriaData criteriaD = FreightTableCriteriaData.createValue();
        criteriaD = fillCriteriaData(pFreightTableCriteriaDescData);

        FreightTableCriteriaData newCriteriaD = FreightTableCriteriaData.createValue();
        newCriteriaD = addFreightTableCriteria(pFreightTableId, criteriaD, pUser);

        FreightTableCriteriaDescData newCriteriaDescD = FreightTableCriteriaDescData.createValue();
        newCriteriaDescD = fillCriteriaDescData(newCriteriaD);

        return newCriteriaDescD;
    }

    /**
     * Updates the FreightTableCriteria information values to be used by the request.
     * @param pFreightTableCriteriaData  the FreightTableCriteriaData FreightTableCriteria data.
     * @param pUser a <code>String</code> value
     * @return a <code>FreightTableCriteriaData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public FreightTableCriteriaData updateFreightTableCriteria(FreightTableCriteriaData pFreightTableCriteriaData, String pUser)
	throws RemoteException
    {
	Connection conn = null;

        FreightTableCriteriaData freightTableCriteria = pFreightTableCriteriaData;
	try {
	    conn = getConnection();

	    if (freightTableCriteria.isDirty()) {
                freightTableCriteria.setModBy(pUser);
                FreightTableCriteriaDataAccess.update(conn, freightTableCriteria);
	    }

	} catch (Exception e) {
	    throw new RemoteException("updateFreightTable: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return freightTableCriteria;
    }


    /**
     * Updates the FreightTableCriteria information values to be used by the request.
     * @param pFreightTableCriteriaDescData  the FreightTableCriteriaDescData FreightTableCriteriaDesc data.
     * @param pUser a <code>String</code> value
     * @return a <code>FreightTableCriteriaDescData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public FreightTableCriteriaDescData updateFreightTableCriteria(FreightTableCriteriaDescData pFreightTableCriteriaDescData, String pUser)
	throws RemoteException
    {
        FreightTableCriteriaData criteriaD = FreightTableCriteriaData.createValue();
        criteriaD = fillCriteriaData(pFreightTableCriteriaDescData);

        FreightTableCriteriaData newCriteriaD = FreightTableCriteriaData.createValue();
        newCriteriaD = updateFreightTableCriteria(criteriaD, pUser);

        FreightTableCriteriaDescData newCriteriaDescD = FreightTableCriteriaDescData.createValue();
        newCriteriaDescD = fillCriteriaDescData(newCriteriaD);

        return newCriteriaDescD;

    }


    /**
     * <code>removeFreightTableCriteria</code> may be used to remove an 'unused' FreightTableCriteria.
     *
     * @param pFreightTableCriteriaData a <code>FreightTableCriteriaData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeFreightTableCriteria(FreightTableCriteriaData pFreightTableCriteriaData)
	throws RemoteException
    {
	Connection conn = null;
	try {
	    conn = getConnection();

	    int freightTableCriteriaId = pFreightTableCriteriaData.getFreightTableCriteriaId();

	    FreightTableCriteriaDataAccess.remove(conn, freightTableCriteriaId);
	} catch (Exception e) {
	    throw new RemoteException("removeFreightTableCriteria: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
    }


    /**
     * <code>removeFreightTableCriteria</code> may be used to remove an 'unused' FreightTableCriteria.
     *
     * @param pFreightTableCriteriaDescData a <code>FreightTableCriteriaDescData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeFreightTableCriteria(FreightTableCriteriaDescData pFreightTableCriteriaDescData)
	throws RemoteException
    {
	Connection conn = null;
	try {
	    conn = getConnection();

	    int freightTableCriteriaId = pFreightTableCriteriaDescData.getFreightTableCriteriaId();

	    FreightTableCriteriaDataAccess.remove(conn, freightTableCriteriaId);
	} catch (Exception e) {
	    throw new RemoteException("removeFreightTableCriteria: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
    }


    private FreightTableCriteriaDescData fillCriteriaDescData(FreightTableCriteriaData criteriaD) {

        NumberFormat nf = NumberFormat.getInstance();

        FreightTableCriteriaDescData criteriaDescD = FreightTableCriteriaDescData.createValue();
        criteriaDescD.setFreightTableCriteriaId(criteriaD.getFreightTableCriteriaId());
        criteriaDescD.setFreightTableId(criteriaD.getFreightTableId());
        criteriaDescD.setAddDate(criteriaD.getAddDate());
        criteriaDescD.setAddBy(criteriaD.getAddBy());
        if (null != criteriaD.getLowerAmount() ) {
            criteriaDescD.setLowerAmount(nf.format(criteriaD.getLowerAmount()));
        }
        else {
            criteriaDescD.setLowerAmount("");
        }

        if (null != criteriaD.getHigherAmount() ) {
            criteriaDescD.setHigherAmount(nf.format(criteriaD.getHigherAmount()));
        }
        else {
            criteriaDescD.setHigherAmount("");
        }

        if (null != criteriaD.getFreightAmount() ) {
            criteriaDescD.setFreightAmount(nf.format(criteriaD.getFreightAmount()));
        }
        else {
            criteriaDescD.setFreightAmount("");
        }

        if (null != criteriaD.getHandlingAmount() ) {
            criteriaDescD.setHandlingAmount(nf.format(criteriaD.getHandlingAmount()));
        }
        else {
            criteriaDescD.setHandlingAmount("");
        }

        if (null != criteriaD.getDiscount() ) {
            criteriaDescD.setDiscount(nf.format(criteriaD.getDiscount()));
        } else {
            criteriaDescD.setDiscount("");
        }

        if (0 != criteriaD.getFreightHandlerId()) {
            criteriaDescD.setFreightHandlerId(String.valueOf(criteriaD.getFreightHandlerId()));
        }
        criteriaDescD.setShortDesc(criteriaD.getShortDesc());
        criteriaDescD.setFreightCriteriaTypeCd(criteriaD.getFreightCriteriaTypeCd());
        criteriaDescD.setRuntimeTypeCd(criteriaD.getRuntimeTypeCd());
        criteriaDescD.setChargeCd(criteriaD.getChargeCd());
        criteriaDescD.setUIOrder(Integer.toString(criteriaD.getUiOrder()));

        return criteriaDescD;

    }


    private FreightTableCriteriaData fillCriteriaData(FreightTableCriteriaDescData criteriaDescD) {

        FreightTableCriteriaData criteriaD = FreightTableCriteriaData.createValue();
        criteriaD.setFreightTableCriteriaId(criteriaDescD.getFreightTableCriteriaId());
        criteriaD.setFreightTableId(criteriaDescD.getFreightTableId());
        criteriaD.setAddDate(criteriaDescD.getAddDate());
        criteriaD.setAddBy(criteriaDescD.getAddBy());
        if (null != criteriaDescD.getLowerAmount() && !"".equals(criteriaDescD.getLowerAmount().trim()) ) {

            BigDecimal decValue = null;
            try {
		NumberFormat nf  = NumberFormat.getInstance();
		Number n = nf.parse(criteriaDescD.getLowerAmount());
		decValue = new BigDecimal(n.doubleValue());
            } catch (ParseException pe) {
                try {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    Number n = nf.parse(criteriaDescD.getLowerAmount());
                    decValue = new BigDecimal(n.doubleValue());
                } catch (ParseException pe2) {
                    decValue = null;
                }
            }
            criteriaD.setLowerAmount(decValue);
        }
        else {
            criteriaD.setLowerAmount(null);
        }

        if (null != criteriaDescD.getHigherAmount() && !"".equals(criteriaDescD.getHigherAmount().trim()) ) {

            BigDecimal decValue = null;
            try {
		NumberFormat nf  = NumberFormat.getInstance();
		Number n = nf.parse(criteriaDescD.getHigherAmount());
		decValue = new BigDecimal(n.doubleValue());
            } catch (ParseException pe) {
                try {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    Number n = nf.parse(criteriaDescD.getHigherAmount());
                    decValue = new BigDecimal(n.doubleValue());
                } catch (ParseException pe2) {
                    decValue = null;
                }
            }
            criteriaD.setHigherAmount(decValue);
        }
        else {
            criteriaD.setHigherAmount(null);
        }

        if (null != criteriaDescD.getFreightAmount() && !"".equals(criteriaDescD.getFreightAmount().trim()) ) {

            BigDecimal decValue = null;
            try {
		NumberFormat nf  = NumberFormat.getInstance();
		Number n = nf.parse(criteriaDescD.getFreightAmount());
		decValue = new BigDecimal(n.doubleValue());
            } catch (ParseException pe) {
                try {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    Number n = nf.parse(criteriaDescD.getFreightAmount());
                    decValue = new BigDecimal(n.doubleValue());
                } catch (ParseException pe2) {
                    decValue = null;
                }
            }
            criteriaD.setFreightAmount(decValue);
        }
        else {
            criteriaD.setFreightAmount(null);
        }

        if (null != criteriaDescD.getHandlingAmount() && !"".equals(criteriaDescD.getHandlingAmount().trim()) ) {

            BigDecimal decValue = null;
            try {
		NumberFormat nf  = NumberFormat.getInstance();
		Number n = nf.parse(criteriaDescD.getHandlingAmount());
		decValue = new BigDecimal(n.doubleValue());
            } catch (ParseException pe) {
                try {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    Number n = nf.parse(criteriaDescD.getHandlingAmount());
                    decValue = new BigDecimal(n.doubleValue());
                } catch (ParseException pe2) {
                    decValue = null;
                }
            }
            criteriaD.setHandlingAmount(decValue);
        }
        else {
            criteriaD.setHandlingAmount(null);
        }

        if (null != criteriaDescD.getDiscount() && !"".equals(criteriaDescD.getDiscount().trim())) {
            BigDecimal decValue = null;
            try {
                NumberFormat nf = NumberFormat.getInstance();
                Number n = nf.parse(criteriaDescD.getDiscount());
                decValue = new BigDecimal(n.doubleValue());
            } catch (ParseException pe) {
                try {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    Number n = nf.parse(criteriaDescD.getDiscount());
                    decValue = new BigDecimal(n.doubleValue());
                } catch (ParseException pe2) {
                    decValue = null;
                }
            }
            criteriaD.setDiscount(decValue);
        } else {
            criteriaD.setDiscount(null);
        }

        try {
            int freightHandlerId = 0;
            freightHandlerId = Integer.parseInt(criteriaDescD.getFreightHandlerId());
            criteriaD.setFreightHandlerId(freightHandlerId);
        }
        catch (Exception e) {

        }
        criteriaD.setShortDesc(criteriaDescD.getShortDesc());
        criteriaD.setFreightCriteriaTypeCd(criteriaDescD.getFreightCriteriaTypeCd());
        criteriaD.setRuntimeTypeCd(criteriaDescD.getRuntimeTypeCd());
        criteriaD.setChargeCd(criteriaDescD.getChargeCd());
        if (Utility.isSet(criteriaDescD.getUIOrder())){
        	criteriaD.setUiOrder(Integer.parseInt(criteriaDescD.getUIOrder()));
        }

        return criteriaD;

    }

    public FreightTableData getFreightTableByContractId(int pContractId)
    throws RemoteException
    {
    	Connection con = null;
    	if(pContractId==0) {
    		return null; //No contract
    	}
    	try {
    		DBCriteria dbc;
    		con = getConnection();
    		dbc = new DBCriteria();
    		dbc.addEqualTo(ContractDataAccess.CONTRACT_ID,pContractId);
    		String freightReq = ContractDataAccess.getSqlSelectIdOnly(ContractDataAccess.FREIGHT_TABLE_ID,dbc);
    		
    		log.info("***********SVC: freightReq = " + freightReq);
    		
    		dbc = new DBCriteria();
    		dbc.addOneOf(FreightTableDataAccess.FREIGHT_TABLE_ID,freightReq);
    		dbc.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_STATUS_CD,RefCodeNames.FREIGHT_TABLE_STATUS_CD.ACTIVE);
    		FreightTableDataVector freightTableDV = FreightTableDataAccess.select(con,dbc);
    		if(freightTableDV.size()==0) {
    			return null;
    		}
    		if (freightTableDV.size()>1) {
    			throw new RemoteException("getFreightTableByContractId(). Too many freight tables");
    		}
    		return (FreightTableData) freightTableDV.get(0);

    	} catch (NamingException exc) {
    		exc.printStackTrace();
    		throw new RemoteException("getFreightTableByContractId() Naming Exception happened");
    	}
    	catch (SQLException exc) {
    		exc.printStackTrace();
    		throw new RemoteException("getFreightTableByContractId() SQL Exception happened");
    	}
    	finally {
    		try { con.close();  }
    		catch (SQLException exc) {}
    	}
    }

    public FreightTableData getFreightTableByFreightId(int pFreightTableId)
    throws RemoteException
    {
    	Connection con = null;
    	if(pFreightTableId == 0) {
    		return null; 
    		//return 0;
    	}
    	try {
    		DBCriteria dbc;
    		con = getConnection();
    		dbc = new DBCriteria();
    		dbc.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_ID, pFreightTableId);
    		FreightTableDataVector freightTableDV = FreightTableDataAccess.select(con,dbc);
       		if(freightTableDV.size()==0) {
    			return null;
    		}
    		return (FreightTableData) freightTableDV.get(0);

    	} catch (NamingException exc) {
    		exc.printStackTrace();
    		throw new RemoteException("getFreightTableByFreightId() Naming Exception happened");
    	}
    	catch (SQLException exc) {
    		exc.printStackTrace();
    		throw new RemoteException("getFreightTableByFreightId() SQL Exception happened");
    	}
    	finally {
    		try { con.close();  }
    		catch (SQLException exc) {}
    	}	
    }

    public FreightTableCriteriaDataVector getFreightTableCriteriasByChargeCd(int freightTableId, String chargeCd)
        throws RemoteException {
        FreightTableCriteriaDataVector freightCriteriaVector = new FreightTableCriteriaDataVector();
        Connection connection = null;
        try {
            connection = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(FreightTableCriteriaDataAccess.FREIGHT_TABLE_ID, freightTableId);
            if (chargeCd != null && chargeCd.length() > 0) {
                crit.addEqualTo(FreightTableCriteriaDataAccess.CHARGE_CD, chargeCd);
            } else {
                crit.addIsNull(FreightTableCriteriaDataAccess.CHARGE_CD);
            }
            crit.addOrderBy(FreightTableCriteriaDataAccess.UI_ORDER);
            crit.addOrderBy(FreightTableCriteriaDataAccess.SHORT_DESC);
            freightCriteriaVector = FreightTableCriteriaDataAccess.select(connection, crit);
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
            }
        }
        return freightCriteriaVector;
    }

    public FreightTableData getFreightTableByDistributorAndCatalog(int storeId, int catalogId, int distributorId)
        throws RemoteException {
        FreightTableData freightTable = null;
        Connection connection = null;
        try {
            connection = getConnection();
            ///
            String sql =
                "SELECT DISTINCT " +
                    "ft.FREIGHT_TABLE_ID, " +
                    "c.CATALOG_ID " +
                "FROM " +
                    "CLW_FREIGHT_TABLE ft LEFT JOIN CLW_CONTRACT c ON c.FREIGHT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
                "WHERE " +
                    "ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                    "AND ft.STORE_ID = ? " +
                    "AND ft.DISTRIBUTOR_ID = ? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
            stmt.setInt(2, storeId);
            stmt.setInt(3, distributorId);
            ResultSet resSet = stmt.executeQuery();
            int nonCatalogFreightTableCount = 0;
            int nonCatalogFreightTableId = 0;
            int catalogFreightTableId = 0;
            while (resSet.next()) {
                int tempFreightTableId = resSet.getInt("FREIGHT_TABLE_ID");
                int tempCatalogId = resSet.getInt("CATALOG_ID");
                if (tempCatalogId == 0) {
                    nonCatalogFreightTableCount++;
                    nonCatalogFreightTableId = tempFreightTableId;
                } else {
                    if (tempCatalogId == catalogId) {
                        catalogFreightTableId = tempFreightTableId;
                    }
                }
            }
            resSet.close();
            stmt.close();
            ///
            if (nonCatalogFreightTableId == 0 && catalogFreightTableId == 0) {
                return null;
            }
            ///
            if (catalogFreightTableId == 0 && nonCatalogFreightTableCount > 1) {
                throw new RemoteException("Multiple freight tables for store id " +
                    storeId + " and distributor id " + distributorId);
            }
            ///
            int freightTableId = catalogFreightTableId;
            if (freightTableId == 0) {
                freightTableId = nonCatalogFreightTableId;
            }
            ///
            freightTable = FreightTableDataAccess.select(connection, freightTableId);
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
            }
        }
        return freightTable;
    }

    public FreightTableData getFreightTableByCatalog(int storeId, int catalogId)
        throws RemoteException, SQLException {
        FreightTableData freightTable = null;
        Connection connection = null;
        try {
            connection = getConnection();
            ///
            String sql =
                "SELECT " +
                    "c.FREIGHT_TABLE_ID " +
                "FROM " +
                    "CLW_FREIGHT_TABLE ft INNER JOIN CLW_CONTRACT c ON c.FREIGHT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
                "WHERE " +
                    "c.CONTRACT_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                    "AND ft.STORE_ID = ? " +
                    "AND c.CATALOG_ID = ? " +
                    "AND (ft.DISTRIBUTOR_ID = 0 OR ft.DISTRIBUTOR_ID IS NULL) ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
            stmt.setInt(2, storeId);
            stmt.setInt(3, catalogId);
            ResultSet resSet = stmt.executeQuery();
            int catalogFreightTableCount = 0;
            int catalogFreightTableId = 0;
            while (resSet.next()) {
                catalogFreightTableId = resSet.getInt("FREIGHT_TABLE_ID");
                catalogFreightTableCount++;
            }
            resSet.close();
            stmt.close();
            ///
            if (catalogFreightTableId == 0) {
                return null;
            }
            ///
            if (catalogFreightTableCount > 1) {
                throw new RemoteException("Multiple freight tables for store id " +
                    storeId + " and catalog id " + catalogId);
            }
            ///
            freightTable = FreightTableDataAccess.select(connection, catalogFreightTableId);
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
            }
        }
        return freightTable;
    } // getFreightTableByCatalog

    public FreightTableData getDiscountTableByDistributorAndCatalog(int storeId, int catalogId, int distributorId)
        throws RemoteException, SQLException {
        FreightTableData freightTable = null;
        Connection connection = null;
        try {
            connection = getConnection();
            ///
            String sql =
                "SELECT DISTINCT " +
                    "ft.FREIGHT_TABLE_ID, " +
                    "c.CATALOG_ID " +
                "FROM " +
                    "CLW_FREIGHT_TABLE ft LEFT JOIN CLW_CONTRACT c ON c.DISCOUNT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
                "WHERE " +
                    "ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                    "AND ft.STORE_ID = ? " +
                    "AND ft.DISTRIBUTOR_ID = ? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT);
            stmt.setInt(2, storeId);
            stmt.setInt(3, distributorId);
            ResultSet resSet = stmt.executeQuery();
            int nonCatalogFreightTableCount = 0;
            int nonCatalogFreightTableId = 0;
            int catalogFreightTableId = 0;
            while (resSet.next()) {
                int tempFreightTableId = resSet.getInt("FREIGHT_TABLE_ID");
                int tempCatalogId = resSet.getInt("CATALOG_ID");
                if (tempCatalogId == 0) {
                    nonCatalogFreightTableCount++;
                    nonCatalogFreightTableId = tempFreightTableId;
                } else {
                    if (tempCatalogId == catalogId) {
                        catalogFreightTableId = tempFreightTableId;
                    }
                }
            }
            resSet.close();
            stmt.close();
            ///
            if (nonCatalogFreightTableId == 0 && catalogFreightTableId == 0) {
                return null;
            }
            ///
            if (catalogFreightTableId == 0 && nonCatalogFreightTableCount > 1) {
                throw new RemoteException("Multiple discount tables for store id " +
                    storeId + " and distributor id " + distributorId);
            }
            ///
            int freightTableId = catalogFreightTableId;
            if (freightTableId == 0) {
                freightTableId = nonCatalogFreightTableId;
            }
            ///
            freightTable = FreightTableDataAccess.select(connection, freightTableId);
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
            }
        }
        return freightTable;
    }

    public FreightTableData getDiscountTableByCatalog(int storeId, int catalogId)
        throws RemoteException, SQLException {
       FreightTableData freightTable = null;
        Connection connection = null;
        try {
            connection = getConnection();
            ///
            String sql =
                "SELECT " +
                    "c.DISCOUNT_TABLE_ID " +
                "FROM " +
                    "CLW_FREIGHT_TABLE ft INNER JOIN CLW_CONTRACT c ON c.DISCOUNT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
                "WHERE " +
                    "c.CONTRACT_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                    "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                    "AND ft.STORE_ID = ? " +
                    "AND c.CATALOG_ID = ? " +
                    "AND (ft.DISTRIBUTOR_ID = 0 OR ft.DISTRIBUTOR_ID IS NULL) ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT);
            stmt.setInt(2, storeId);
            stmt.setInt(3, catalogId);
            ResultSet resSet = stmt.executeQuery();
            int catalogFreightTableCount = 0;
            int catalogFreightTableId = 0;
            while (resSet.next()) {
                catalogFreightTableId = resSet.getInt("DISCOUNT_TABLE_ID");
                catalogFreightTableCount++;
            }
            resSet.close();
            stmt.close();
            ///
            if (catalogFreightTableId == 0) {
                return null;
            }
            ///
            if (catalogFreightTableCount > 1) {
                throw new RemoteException("Multiple discount tables for store id " +
                    storeId + " and catalog id " + catalogId);
            }
            ///
            freightTable = FreightTableDataAccess.select(connection, catalogFreightTableId);
        }
        catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
            }
        }
        return freightTable;
    }

	public FreightTableView getFreightTableView(int freightTableId,
			int storeId, String chargeType) throws RemoteException, SQLException {
		FreightTableView freightTableView = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			String sql = "SELECT f.FREIGHT_TABLE_ID, f.SHORT_DESC AS FREIGHT_TABLE_NAME, "
					+ "f.FREIGHT_TABLE_STATUS_CD, f.FREIGHT_TABLE_TYPE_CD, "
					+ "f.STORE_ID, f.DISTRIBUTOR_ID, e.SHORT_DESC AS DISTRIBUTOR_NAME "
					+ "FROM CLW_FREIGHT_TABLE f "
					+ "LEFT OUTER JOIN CLW_BUS_ENTITY e "
					+ "ON e.BUS_ENTITY_ID = f.DISTRIBUTOR_ID "
					+ "WHERE f.FREIGHT_TABLE_ID = ? " + "AND f.STORE_ID = ? ";
			if (chargeType != null)
				sql += "AND f.FREIGHT_TABLE_CHARGE_CD = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, freightTableId);
			stmt.setInt(2, storeId);
			if (chargeType != null)
				stmt.setString(3, chargeType);
			rs = stmt.executeQuery();
			while (rs.next()) {
				freightTableView = new FreightTableView();
				freightTableView.setFreightTableId(rs.getInt("FREIGHT_TABLE_ID"));
				freightTableView.setShortDesc(rs.getString("FREIGHT_TABLE_NAME"));
				freightTableView.setFreightTableStatusCd(rs.getString("FREIGHT_TABLE_STATUS_CD"));
				freightTableView.setFreightTableTypeCd(rs.getString("FREIGHT_TABLE_TYPE_CD"));
				freightTableView.setStoreId(rs.getInt("STORE_ID"));
				freightTableView.setDistributorId(rs.getInt("DISTRIBUTOR_ID"));
				freightTableView.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
			}
		} catch (Exception e) {
			throw new RemoteException("getFreightTable: " + e.getMessage());
		} finally {
			try {
				if (rs != null)	rs.close();
			} finally {
				try {
					if (stmt != null)stmt.close();
				} finally {
					if (conn != null) conn.close();
				}
			}
		}
		return freightTableView;
	}
	
	public FreightTableView getFreightTableView(int freightTableId, String chargeType) throws RemoteException, SQLException {
		FreightTableView freightTableView = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			String sql = "SELECT f.FREIGHT_TABLE_ID, f.SHORT_DESC AS FREIGHT_TABLE_NAME, "
					+ "f.FREIGHT_TABLE_STATUS_CD, f.FREIGHT_TABLE_TYPE_CD, "
					+ "f.STORE_ID, f.DISTRIBUTOR_ID, e.SHORT_DESC AS DISTRIBUTOR_NAME "
					+ "FROM CLW_FREIGHT_TABLE f "
					+ "LEFT OUTER JOIN CLW_BUS_ENTITY e "
					+ "ON e.BUS_ENTITY_ID = f.DISTRIBUTOR_ID "
					+ "WHERE f.FREIGHT_TABLE_ID = ? " ;
			if (chargeType != null)
				sql += "AND f.FREIGHT_TABLE_CHARGE_CD = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, freightTableId);
			if (chargeType != null)
				stmt.setString(3, chargeType);
			rs = stmt.executeQuery();
			while (rs.next()) {
				freightTableView = new FreightTableView();
				freightTableView.setFreightTableId(rs.getInt("FREIGHT_TABLE_ID"));
				freightTableView.setShortDesc(rs.getString("FREIGHT_TABLE_NAME"));
				freightTableView.setFreightTableStatusCd(rs.getString("FREIGHT_TABLE_STATUS_CD"));
				freightTableView.setFreightTableTypeCd(rs.getString("FREIGHT_TABLE_TYPE_CD"));
				freightTableView.setStoreId(rs.getInt("STORE_ID"));
				freightTableView.setDistributorId(rs.getInt("DISTRIBUTOR_ID"));
				freightTableView.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
			}
		} catch (Exception e) {
			throw new RemoteException("getFreightTable: " + e.getMessage());
		} finally {
			try {
				if (rs != null)	rs.close();
			} finally {
				try {
					if (stmt != null)stmt.close();
				} finally {
					if (conn != null) conn.close();
				}
			}
		}
		return freightTableView;
	}
	
    public FreightTableView getFreightTableView(int freightTableId)throws RemoteException, SQLException {
     return getFreightTableView(freightTableId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
 }
    
    public FreightTableView getFreightTableView(int freightTableId, int storeId) throws RemoteException, SQLException {
    	return getFreightTableView(freightTableId, storeId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
}

    public FreightTableViewVector getFreightTableViewByName(String name, int match, int storeId, String chargeType, IdVector catalogIds, IdVector distributorIds)throws RemoteException, SQLException {
    	FreightTableViewVector freightTableVec = new FreightTableViewVector();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
  	  try {
        conn = getConnection();
        String sql = "SELECT f.FREIGHT_TABLE_ID, f.SHORT_DESC AS FREIGHT_TABLE_NAME, "
				+ "f.FREIGHT_TABLE_STATUS_CD, f.FREIGHT_TABLE_TYPE_CD, "
				+ "f.STORE_ID, f.DISTRIBUTOR_ID, e.SHORT_DESC AS DISTRIBUTOR_NAME, " 
				+ "f.FREIGHT_TABLE_CHARGE_CD " 
				+ "FROM CLW_FREIGHT_TABLE f "
				+ "LEFT OUTER JOIN CLW_BUS_ENTITY e "
				+ "ON e.BUS_ENTITY_ID = f.DISTRIBUTOR_ID ";
        if(catalogIds != null && !catalogIds.isEmpty())	
        	if(chargeType.equals(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT))
		        sql += "INNER JOIN CLW_CONTRACT cnt "
	        		+"ON f.FREIGHT_TABLE_ID = cnt.FREIGHT_TABLE_ID ";
	        else if(chargeType.equals(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT))
	        	sql += "INNER JOIN CLW_CONTRACT cnt "
	        		+"ON f.FREIGHT_TABLE_ID = cnt.DISCOUNT_TABLE_ID ";
				
        sql += "WHERE f.STORE_ID = ? ";
        switch (match) {
        case FreightTable.EXACT_MATCH:
        	sql +=" AND f.SHORT_DESC =' " + name + "' ";
          break;
  	    case FreightTable.EXACT_MATCH_IGNORE_CASE:
  	    	sql +=" AND LOWER(f.SHORT_DESC) = LOWER('" + name +	"') ";
  		    break;
  	    case FreightTable.BEGINS_WITH:
  	    	sql +=" AND f.SHORT_DESC LIKE '" + name + "%' ";
  		    break;
  	    case FreightTable.BEGINS_WITH_IGNORE_CASE:
  	    	sql +=" AND LOWER(f.SHORT_DESC) LIKE LOWER('" + name +"%') ";
  		    break;
  	    case FreightTable.CONTAINS:
  	    	sql +=" AND f.SHORT_DESC = '%" + name + "%' ";
  		    break;
  	    case FreightTable.CONTAINS_IGNORE_CASE:
  	    	sql +=" AND LOWER(f.SHORT_DESC) LIKE LOWER('%" + name + "%') ";
  		   break;
  	    default:
  		    throw new RemoteException("getFreightTableByName: Bad match specification");
  	    }
        if (chargeType != null) 
  			sql += " AND f.FREIGHT_TABLE_CHARGE_CD = ? ";
        if(catalogIds != null && !catalogIds.isEmpty()){
        	sql += "AND cnt.CATALOG_ID IN ( ";
        	Iterator<Integer> iter =  catalogIds.iterator();
        	while(iter.hasNext()){
        		sql += iter.next();
        		if(iter.hasNext())sql+=", ";
        	}
        	sql += " ) ";
        }
        if(distributorIds != null && !distributorIds.isEmpty()){
        	sql += " AND f.DISTRIBUTOR_ID IN ( ";
        	Iterator<Integer> iter =  distributorIds.iterator();
        	while(iter.hasNext()){
        		sql += iter.next();
        		if(iter.hasNext())sql+=", ";
        	}
        	sql += " ) ";
        }
        
        sql += " ORDER BY f.SHORT_DESC ";
        stmt = conn.prepareStatement(sql);
		stmt.setInt(1, storeId);
        if (chargeType != null) 
      			stmt.setString(2, chargeType);
        rs = stmt.executeQuery();
        while(rs.next()){
        	FreightTableView freightTableView = new FreightTableView();
			freightTableView.setFreightTableId(rs.getInt("FREIGHT_TABLE_ID"));
			freightTableView.setShortDesc(rs.getString("FREIGHT_TABLE_NAME"));
			freightTableView.setFreightTableStatusCd(rs.getString("FREIGHT_TABLE_STATUS_CD"));
			freightTableView.setFreightTableTypeCd(rs.getString("FREIGHT_TABLE_TYPE_CD"));
			freightTableView.setStoreId(rs.getInt("STORE_ID"));
			freightTableView.setDistributorId(rs.getInt("DISTRIBUTOR_ID"));
			freightTableView.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
			freightTableVec.add(freightTableView);
        }
  	} catch (Exception e) {
  		e.printStackTrace();
  	    throw new RemoteException("getFreightTableByName: " + e.getMessage());
  	} finally {
		try {
			if (rs != null)	rs.close();
		} finally {
			try {
				if (stmt != null)stmt.close();
			} finally {
				if (conn != null) conn.close();
			}
		}
  	}

  	return freightTableVec;
    }

    public FreightTableViewVector getFreightTableViewByName(String name, int match, int storeId, IdVector catalogIds, IdVector distributorIds)throws RemoteException, SQLException {
    	return getFreightTableViewByName(name, match, storeId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT, catalogIds, distributorIds);
}
    
	public FreightTableViewVector getAllFreightTableViews(int storeId, String chargeType, IdVector catalogIds, IdVector distributorIds) throws RemoteException, SQLException {
		FreightTableViewVector freightTableVec = new FreightTableViewVector();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			String sql = "SELECT f.FREIGHT_TABLE_ID, f.SHORT_DESC AS FREIGHT_TABLE_NAME, "
					+ "f.FREIGHT_TABLE_STATUS_CD, f.FREIGHT_TABLE_TYPE_CD, "
					+ "f.STORE_ID, f.DISTRIBUTOR_ID, e.SHORT_DESC as DISTRIBUTOR_NAME "
					+ "FROM CLW_FREIGHT_TABLE f "
					+ "LEFT OUTER JOIN CLW_BUS_ENTITY e "
					+ "ON e.BUS_ENTITY_ID = f.DISTRIBUTOR_ID ";
			        if(catalogIds != null && !catalogIds.isEmpty())
		        	if(chargeType.equals(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT))
			        sql += "INNER JOIN CLW_CONTRACT cnt "
		        		+"ON f.FREIGHT_TABLE_ID = cnt.FREIGHT_TABLE_ID ";
			        else if(chargeType.equals(RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT))
			        	sql += "INNER JOIN CLW_CONTRACT cnt "
			        		+"ON f.FREIGHT_TABLE_ID = cnt.DISCOUNT_TABLE_ID ";
			        sql += "WHERE f.STORE_ID = ? ";
			if (chargeType != null)
				sql += " AND f.FREIGHT_TABLE_CHARGE_CD = ? ";
			if(catalogIds != null && !catalogIds.isEmpty()){
	        	sql += "AND cnt.CATALOG_ID IN ( ";
	        	Iterator<Integer> iter =  catalogIds.iterator();
	        	while(iter.hasNext()){
	        		sql += iter.next();
	        		if(iter.hasNext())sql+=", ";
	        	}
	        	sql += " )";
	        }		
	        if(distributorIds != null && !distributorIds.isEmpty()){
	        	sql += " AND f.DISTRIBUTOR_ID IN ( ";
	        	Iterator<Integer> iter =  distributorIds.iterator();
	        	while(iter.hasNext()){
	        		sql += iter.next();
	        		if(iter.hasNext())sql+=", ";
	        	}
	        	sql += " ) ";
	        }
			
			sql += " ORDER BY f.SHORT_DESC ";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, storeId);
			if (chargeType != null)
				stmt.setString(2, chargeType);
			rs = stmt.executeQuery();
			while (rs.next()) {
				FreightTableView freightTableView = new FreightTableView();
				freightTableView.setFreightTableId(rs
						.getInt("FREIGHT_TABLE_ID"));
				freightTableView.setShortDesc(rs
						.getString("FREIGHT_TABLE_NAME"));
				freightTableView.setFreightTableStatusCd(rs
						.getString("FREIGHT_TABLE_STATUS_CD"));
				freightTableView.setStoreId(rs
						.getInt("STORE_ID"));
				freightTableView.setFreightTableTypeCd(rs
						.getString("FREIGHT_TABLE_TYPE_CD"));
				freightTableView.setDistributorId(rs
						.getInt("DISTRIBUTOR_ID"));
				freightTableView.setDistributorName(rs
						.getString("DISTRIBUTOR_NAME"));
				freightTableVec.add(freightTableView);
			}
		} catch (Exception e) {
			throw new RemoteException("getFreightTableByName: " + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
			} finally {
				try {
					if (stmt != null)
						stmt.close();
				} finally {
					if (conn != null)
						conn.close();
				}
			}
		}
		return freightTableVec;
	}
    
    public FreightTableViewVector getAllFreightTableViews(int storeId, IdVector catalogIds, IdVector distributorIds) throws RemoteException, SQLException {
    	return getAllFreightTableViews(storeId, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT, catalogIds, distributorIds);
 }
    public FreightTableData getFreightTableDataByCatalog(int storeId, int catalogId)
    throws RemoteException, SQLException {
    FreightTableData freightTable = null;
    Connection connection = null;
    try {
        connection = getConnection();
        ///
        String sql =
            "SELECT " +
                "c.FREIGHT_TABLE_ID " +
            "FROM " +
                "CLW_FREIGHT_TABLE ft INNER JOIN CLW_CONTRACT c ON c.FREIGHT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
            "WHERE " +
                "c.CONTRACT_STATUS_CD = 'ACTIVE' " +
                "AND ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                "AND ft.STORE_ID = ? " +
                "AND c.CATALOG_ID = ? ";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT);
        stmt.setInt(2, storeId);
        stmt.setInt(3, catalogId);
        ResultSet resSet = stmt.executeQuery();
        int catalogFreightTableCount = 0;
        int catalogFreightTableId = 0;
        while (resSet.next()) {
            catalogFreightTableId = resSet.getInt("FREIGHT_TABLE_ID");
            catalogFreightTableCount++;
        }
        resSet.close();
        stmt.close();
        ///
        if (catalogFreightTableId == 0) {
            return null;
        }
        ///
        if (catalogFreightTableCount > 1) {
            throw new RemoteException("Multiple freight tables for store id " +
                storeId + " and catalog id " + catalogId);
        }
        ///
        freightTable = FreightTableDataAccess.select(connection, catalogFreightTableId);
    }
    catch (Exception ex) {
        throw new RemoteException(ex.getMessage());
    }
    finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
        }
    }
    return freightTable;
} // getFreightTableByCatalog
    public FreightTableData getDiscountTableDataByCatalog(int storeId, int catalogId)
    throws RemoteException, SQLException {
   FreightTableData freightTable = null;
    Connection connection = null;
    try {
        connection = getConnection();
        ///
        String sql =
            "SELECT " +
                "c.DISCOUNT_TABLE_ID " +
            "FROM " +
                "CLW_FREIGHT_TABLE ft INNER JOIN CLW_CONTRACT c ON c.DISCOUNT_TABLE_ID = ft.FREIGHT_TABLE_ID " +
            "WHERE " +
                "c.CONTRACT_STATUS_CD = 'ACTIVE' " +
                "AND ft.FREIGHT_TABLE_STATUS_CD = 'ACTIVE' " +
                "AND ft.FREIGHT_TABLE_CHARGE_CD = ? " +
                "AND ft.STORE_ID = ? " +
                "AND c.CATALOG_ID = ? ";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT);
        stmt.setInt(2, storeId);
        stmt.setInt(3, catalogId);
        ResultSet resSet = stmt.executeQuery();
        int catalogFreightTableCount = 0;
        int catalogFreightTableId = 0;
        while (resSet.next()) {
            catalogFreightTableId = resSet.getInt("DISCOUNT_TABLE_ID");
            catalogFreightTableCount++;
        }
        resSet.close();
        stmt.close();
        ///
        if (catalogFreightTableId == 0) {
            return null;
        }
        ///
        if (catalogFreightTableCount > 1) {
            throw new RemoteException("Multiple discount tables for store id " +
                storeId + " and catalog id " + catalogId);
        }
        ///
        freightTable = FreightTableDataAccess.select(connection, catalogFreightTableId);
    }
    catch (Exception ex) {
        throw new RemoteException(ex.getMessage());
    }
    finally {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
        }
    }
    return freightTable;
}
}
