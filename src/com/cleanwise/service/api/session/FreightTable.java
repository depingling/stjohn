package com.cleanwise.service.api.session;

/**
 * Title:        FreightTable
 * Description:  Remote Interface for FreightTable Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving FreightTable information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.sql.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

public interface FreightTable extends javax.ejb.EJBObject
{

    /**
     *  There are 6 options when getting Stores by name:
     *  name exactly matches, name begins with, name contains
     *  and then each of those options ignoring the case
     */
    public static final int EXACT_MATCH = 0;
    public static final int BEGINS_WITH = 1;
    public static final int CONTAINS = 2;
    public static final int EXACT_MATCH_IGNORE_CASE = 3;
    public static final int BEGINS_WITH_IGNORE_CASE = 4;
    public static final int CONTAINS_IGNORE_CASE = 5;


    /**
     * Describe <code>getFreightTable</code> method here.
     *
     * @param pFreightTableId an <code>int</code> value
     * @return a <code>FreightTableData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public FreightTableData getFreightTable(int pFreightTableId)
	throws RemoteException, DataNotFoundException;

    public FreightTableData getFreightTable(int pFreightTableId, String chargeType)
	   throws RemoteException, DataNotFoundException;

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
	 throws RemoteException;

    public FreightTableData getFreightTable(int pFreightTableId, int pStoreId, String chargeType)
	   throws RemoteException;


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
	throws RemoteException;

    public FreightTableDataVector getFreightTableByName(String pName, int pMatch, int pStoreId, String chargeType)
	   throws RemoteException;

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
	  throws RemoteException;

    public FreightTableDataVector getFreightTableByName(String pName, int pMatch, String chargeType)
        throws RemoteException;

    /**
     * Get all the FreightTables for the store.
     * @param pStoreId the store id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
  public FreightTableDataVector getAllFreightTables(int pStoreId)
	throws RemoteException;

    public FreightTableDataVector getAllFreightTables(int pStoreId, String chargeType)
	   throws RemoteException;

    /**
     * Get all the FreightTables.
     *
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getAllFreightTables()
	throws RemoteException;

    /**
     * Get all the FreightTables of the store
     * @param pStoreId store id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getStoreFreightTables(int pStoreId)
	   throws RemoteException;

    public FreightTableDataVector getStoreFreightTables(int pStoreId, String chargeType)
        throws RemoteException;

    /**
     * Get all the FreightTables of the distributor
     * @param pDistId distributor id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getDistributorFreightTables(int pDistId)
        throws RemoteException;

    /**
     * Get all the FreightTables of the distributor in the store
     * @param pStoreId store id
     * @param pDistId distributor id
     * @return a <code>FreightTableDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getStoreDistributorFreightTables(int pStoreId, int pDistId)
        throws RemoteException;

    public FreightTableDataVector getStoreDistributorFreightTables(int pStoreId, int pDistId, String chargeType)
        throws RemoteException;

    /**
     * Get all the FreightTableCriteria of the distributor in the store
     * @param pStoreId store id
     * @param pDistId distributor id
     * @param pFreightTableId the specific id of the freight handler.  Other criteria still evaluated.
     * @return a <code>FreightTableCriteriaDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDataVector getStoreDistributorFreightTableCriteria(int pStoreId, int pDistId, int pFreightTableId)
        throws RemoteException ;

    /**
     * Describe <code>addFreightTable</code> method here.
     *
     * @param pFreightTableData a <code>FreightTableData</code> value
     * @return a <code>FreightTableData</code> value
     * @exception RemoteException if an error occurs
     */
    public FreightTableData addFreightTable(FreightTableData pFreightTableData)
	throws RemoteException;

    /**
     * Updates the FreightTable information values to be used by the request.
     * @param pFreightTableData  the FreightTableData FreightTable data.
     * @return a <code>FreightTableData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public FreightTableData updateFreightTable(FreightTableData pFreightTableData)
	throws RemoteException;

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
	throws RemoteException;

    /**
     * Get all the FreightTableCriterias.
     *
     * @return a <code>FreightTableCriteriaDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDataVector getAllFreightTableCriterias(int pFreightTableId)
	throws RemoteException;

    /**
     * Get all the FreightTableCriteriaDescs.
     *
     * @return a <code>FreightTableCriteriaDescDataVector</code> with all FreightTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableCriteriaDescDataVector getAllFreightTableCriteriaDescs(int pFreightTableId)
	throws RemoteException;

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
	throws RemoteException;

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
	throws RemoteException;

    /**
     * Updates the FreightTableCriteria information values to be used by the request.
     * @param pFreightTableCriteriaData  the FreightTableCriteriaData FreightTableCriteria data.
     * @param pUser a <code>String</code> value
     * @return a <code>FreightTableCriteriaData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public FreightTableCriteriaData updateFreightTableCriteria(FreightTableCriteriaData pFreightTableCriteriaData, String pUser)
	throws RemoteException;

    /**
     * Updates the FreightTableCriteria information values to be used by the request.
     * @param pFreightTableCriteriaDescData  the FreightTableCriteriaDescData FreightTableCriteriaDesc data.
     * @param pUser a <code>String</code> value
     * @return a <code>FreightTableCriteriaDescData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public FreightTableCriteriaDescData updateFreightTableCriteria(FreightTableCriteriaDescData pFreightTableCriteriaDescData, String pUser)
	throws RemoteException;

    /**
     * <code>removeFreightTableCriteria</code> may be used to remove an 'unused' FreightTableCriteria.
     *
     * @param pFreightTableCriteriaData a <code>FreightTableCriteriaData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeFreightTableCriteria(FreightTableCriteriaData pFreightTableCriteriaData)
	throws RemoteException;

    /**
     * <code>removeFreightTableCriteria</code> may be used to remove an 'unused' FreightTableCriteria.
     *
     * @param pFreightTableCriteriaDescData a <code>FreightTableCriteriaDescData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeFreightTableCriteria(FreightTableCriteriaDescData pFreightTableCriteriaDescData)
	throws RemoteException;

    public FreightTableData getFreightTableByContractId(int pContractId)
    throws RemoteException;
    
    
    //SVC: new for Discount calculation
    
    public  FreightTableCriteriaDataVector getStoreDistributorFreightTableDiscountCriteria(int pStoreId, int pDistId, int pFreightTableId)
    throws RemoteException;

    /**
     * SVC: new for Discount calculation
     * Get all the DiscountTables.
     *
     * @return a <code>FreightTableDataVector</code> with all DiscountTables.
     * @exception RemoteException if an error occurs
     */
    public FreightTableDataVector getAllDiscountTables()
    throws RemoteException;
    
    //SVC: new for Discount calculation
    public FreightTableData getFreightTableByFreightId(int pFrTblId) throws RemoteException;


    public FreightTableCriteriaDataVector getFreightTableCriteriasByChargeCd(int freightTableId, String chargeCd)
        throws RemoteException;

    public FreightTableData getFreightTableByDistributorAndCatalog(int storeId, int catalogId, int distributorId)
        throws RemoteException;

    public FreightTableData getFreightTableByCatalog(int storeId, int catalogId)
        throws RemoteException, SQLException;

    public FreightTableData getDiscountTableByDistributorAndCatalog(int storeId, int catalogId, int distributorId)
        throws RemoteException, SQLException;

    public FreightTableData getDiscountTableByCatalog(int storeId, int catalogId)
        throws RemoteException, SQLException;
    
	public FreightTableView getFreightTableView(int freightTableId,
			int storeId, String chargeType) throws RemoteException, SQLException;
	
	public FreightTableView getFreightTableView(int freightTableId, String chargeType) 
		throws RemoteException, SQLException ;
	
	public FreightTableView getFreightTableView(int freightTableId)
		throws RemoteException, SQLException ;
	
	public FreightTableView getFreightTableView(int freightTableId, int storeId) 
		throws RemoteException, SQLException ;
	
	public FreightTableViewVector getFreightTableViewByName(String name, int match, int storeId, String chargeType, IdVector catalogIds, IdVector distributorIds)
		throws RemoteException, SQLException;
	
	public FreightTableViewVector getFreightTableViewByName(String name, int match, int storeId, IdVector catalogIds, IdVector distributorIds)
		throws RemoteException, SQLException ;

	public FreightTableViewVector getAllFreightTableViews(int storeId, String chargeType, IdVector catalogIds, IdVector distributorIds) 
		throws RemoteException, SQLException ;

	public FreightTableViewVector getAllFreightTableViews(int storeId, IdVector catalogIds, IdVector distributorIds)
		throws RemoteException, SQLException ;
	  public FreightTableData getFreightTableDataByCatalog(int storeId, int catalogId)
	    throws RemoteException, SQLException ;
	  public FreightTableData getDiscountTableDataByCatalog(int storeId, int catalogId)
	    throws RemoteException, SQLException;
}
