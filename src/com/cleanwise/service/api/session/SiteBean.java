package com.cleanwise.service.api.session;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.text.DateFormat;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BlanketPoNumAssocDataAccess;
import com.cleanwise.service.api.dao.BudgetDAO;
import com.cleanwise.service.api.dao.BudgetDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ContractItemDataAccess;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.dao.DbUtility;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.Fedstrip058DataAccess;
import com.cleanwise.service.api.dao.InventoryItemsDataAccess;
import com.cleanwise.service.api.dao.InventoryLevelDAO;
import com.cleanwise.service.api.dao.InventoryLevelDataAccess;
import com.cleanwise.service.api.dao.InventoryOrderLogDataAccess;
import com.cleanwise.service.api.dao.InventoryOrderQtyDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PostalCodeDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.ScheduleDataAccess;
import com.cleanwise.service.api.dao.ScheduleDetailDataAccess;
import com.cleanwise.service.api.dao.ShoppingControlDataAccess;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.SiteLedgerDataAccess;
import com.cleanwise.service.api.dao.SiteWorkflowDataAccess;
import com.cleanwise.service.api.dao.UniversalDAO;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.ShoppingServicesBean.ServiceFeeDetail;
import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.util.CacheManager;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.FiscalCalendarUtility;
import com.cleanwise.service.api.util.PhysicalInventoryPeriodArray;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ScheduleProc;
import com.cleanwise.service.api.util.ShoppingRestrictionsViewComparatorByItem;
import com.cleanwise.service.api.util.ShoppingRestrictionsViewComparatorBySku;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.InventoryLevelViewWrapper;
import com.cleanwise.view.utils.Constants;


/**

 *  <Code>Site</code> stateless session bean.

 *

 *@author     <a href="mailto:tbesser@cleanwise.com"></a>

 *@created    October 29, 2001


 */

public class SiteBean

         extends BusEntityServicesAPI {
    private static final Logger log = Logger.getLogger(SiteBean.class);
    
	HashMap scheduleJoinViewMap = new HashMap();

	private final int NO_BUDGET_YEAR_REQUIRED = -100;

    /**

     *  Creates a new <code>SiteBean</code> instance.

     */

    public SiteBean() { }

    /**
     *  Gets the site information values to be used by the request.  Does not include
     *  inactive sites.
     *
     *@param  pSiteId                    an <code>int</code> value
     *@param  pAccountId                 the id of the site account. If nonzero
     *      will only return an Site that belongs to that account. i.e. if the
     *      Site belongs to a different account, it won't be returned
     *@return                            a <code>SiteData</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */

    public SiteData getSite(int pSiteId, int pAccountId)
             throws RemoteException, DataNotFoundException {
    	return getSite(pSiteId, pAccountId, false);

    }

    /**
     *  Gets the site information values to be used by the request.
     *
     *@param  pSiteId                    an <code>int</code> value
     *@param  pAccountId                 the id of the site account. If nonzero
     *      will only return an Site that belongs to that account. i.e. if the
     *      Site belongs to a different account, it won't be returned
     *@return                            a <code>SiteData</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public SiteData getSite(int pSiteId, int pAccountId, boolean pShowInactiveFl)
             throws RemoteException, DataNotFoundException { 
           return getSite(pSiteId, pAccountId, pShowInactiveFl, null);
    }

    public SiteData getSite(int pSiteId, int pAccountId, boolean pShowInactiveFl, AccCategoryToCostCenterView  pCategToCostCenterView)
    		throws RemoteException, DataNotFoundException {
    	 return getSiteByBudgetYear(pSiteId, pAccountId, pShowInactiveFl, pCategToCostCenterView,NO_BUDGET_YEAR_REQUIRED);
    }
	 
    /**
     * Gets the side information with budgets by given parameters.
     * @param pSiteId 					- the site identifier.
     * @param pAccountId				- the id of the site account. If nonzero
     *      							  will only return an Site that belongs to that account. i.e. if the
     *      							  Site belongs to a different account, it won't be returned
     * @param pShowInactiveFl			- Will return data even if the site is inactive
     * @param pCategToCostCenterView	- Category To Cost Center View
     * @param budgetYear				- Budget year is used to get the the site budgets details by specified year. 
     * 									  If the budgetYear value is -1 then site budgets will be fetched based on current
     * 									  fiscal year.
     * @return
     * @throws RemoteException			- If any error occurs.
     * @throws DataNotFoundException	- If any error occurs.
     */
    public SiteData getSiteByBudgetYear(int pSiteId, int pAccountId, boolean pShowInactiveFl, AccCategoryToCostCenterView  pCategToCostCenterView,int pBudgetYear)
		throws RemoteException, DataNotFoundException {
		SiteData site = null;
		Connection conn = null;
		try {
		   conn = getConnection();
		   BusEntityData busEntity = null;
		   DBCriteria crit = new DBCriteria();
		   if(!pShowInactiveFl){
		   	crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
		   }
		   crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, pSiteId);
		  // busEntity = BusEntityDataAccess.select(conn, pSiteId);
		
		   BusEntityDataVector beDV = BusEntityDataAccess.select(conn, crit);
		   if(beDV!=null && beDV.size()>0){
		   	busEntity = (BusEntityData)beDV.get(0);
		   	if (!busEntity.getBusEntityTypeCd().equals(
		   			RefCodeNames.BUS_ENTITY_TYPE_CD.SITE)) {
		   		throw new DataNotFoundException("Bus Entity not site");
		
		   	}
		   }
		   if (pAccountId != 0) {
		       // need to check that this site is associated to account
		       crit = new DBCriteria();
		       crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pSiteId);
		       crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pAccountId);
		       crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
		       BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(conn, crit);
		       if (assocVec.size() == 0) {
		           throw new DataNotFoundException("BUS_ENTITY_ID :" +pSiteId);
		       }
		   }
		
		   if(busEntity!=null){
			   if(pBudgetYear==NO_BUDGET_YEAR_REQUIRED) {
				   site = getSiteDetails(busEntity, pCategToCostCenterView);
			   }else {
				   site = getSiteDetailsByBudgetYear(busEntity, pCategToCostCenterView,pBudgetYear);
			   }
		   }
		} catch (DataNotFoundException e) {
		   e.printStackTrace();
		   throw e;
		} catch (Exception e) {
		   e.printStackTrace();
		   throw new RemoteException("getSite: " + e.getMessage());
		} finally {
			scheduleJoinViewMap = null;
			closeConnection(conn);
		}
		
		return site;
	}





    /**

     *  Gets the site information.

     *

     *@param  pSiteId                    the site identifier.

     *@return                            SiteData

     *@exception  DataNotFoundException  Description of Exception

     *@throws  RemoteException           Required by EJB 1.0

     *      DataNotFoundException if site with pSiteId doesn't exist

     */

    public SiteData getSite(int pSiteId)

             throws RemoteException, DataNotFoundException {



        return getSite(pSiteId, 0);

    }





    /**

     *  Get all sites that match the given name. The arguments specify whether

     *  the name is interpreted as a pattern or exact match.

     *

     *@param  pName                a <code>String</code> value with site name or

     *      pattern

     *@param  pAccountId           the id of the site account. If nonzero will

     *      only return a Sites that belongs to that account. Otherwise will

     *      return all matching Sites.

     *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH,

     *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE

     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME

     *@return                      a <code>SiteDataVector</code> of matching

     *      sites

     *@exception  RemoteException  if an error occurs

     */
    public SiteDataVector getSiteByName(String pName, int pAccountId,
            int pMatch, int pOrder)

             throws RemoteException {
           return getSiteByName( pName, pAccountId, pMatch,  pOrder, null);
         }

    public SiteDataVector getSiteByName(String pName, int pAccountId,

            int pMatch, int pOrder, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {



        SiteDataVector siteVec = new SiteDataVector();

        Connection conn = null;



        try {

            conn = getConnection();



            DBCriteria crit = new DBCriteria();



            switch (pMatch) {



                case Site.EXACT_MATCH:

                    crit.addEqualTo(BusEntityDataAccess.SHORT_DESC, pName);



                    break;

                case Site.EXACT_MATCH_IGNORE_CASE:

                    crit.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC,

                            pName);



                    break;

                case Site.BEGINS_WITH:

                    crit.addLike(BusEntityDataAccess.SHORT_DESC, pName +

                            "%");



                    break;

                case Site.BEGINS_WITH_IGNORE_CASE:

                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,

                            pName + "%");



                    break;

                case Site.CONTAINS:

                    crit.addLike(BusEntityDataAccess.SHORT_DESC,

                            "%" + pName + "%");



                    break;

                case Site.CONTAINS_IGNORE_CASE:

                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,

                            "%" + pName + "%");



                    break;

                default:

                    throw new RemoteException("getSiteByName: Bad match specification");

            }



            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

                    RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);



            if (pAccountId != 0) {



                StringBuffer buf = new StringBuffer();

                buf.append(BusEntityDataAccess.BUS_ENTITY_ID);

                buf.append(" IN (SELECT ");

                buf.append(BusEntityAssocDataAccess.BUS_ENTITY1_ID);

                buf.append(" FROM ");

                buf.append(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC);

                buf.append(" WHERE ");

                buf.append(BusEntityAssocDataAccess.BUS_ENTITY2_ID);

                buf.append(" = ");

                buf.append(pAccountId);

                buf.append(" AND ");

                buf.append(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD);

                buf.append(" = '");

                buf.append(RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                buf.append("')");

                crit.addCondition(buf.toString());

            }



            switch (pOrder) {



                case Site.ORDER_BY_ID:

                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);



                    break;

                case Site.ORDER_BY_NAME:

                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);



                    break;

                default:

                    throw new RemoteException("getSiteByName: Bad order specification");

            }



            BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn,

                    crit);

            Iterator iter = busEntityVec.iterator();



            while (iter.hasNext()) {

                siteVec.add(getSiteDetails((BusEntityData) iter.next(), pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }



        return siteVec;

    }



    /**

     *  Gets all sites that match the given site property.

     *

     *@param  pName                property Short_desc value

     *@param  pAccountId           the id of the site account

     *@return                      a set of SiteData objects ordered by ids

     *      sites

     *@exception  RemoteException  if an error occurs

     */
    public SiteDataVector getSiteByProperty(String pName, String pValue, int pAccountId)

        throws RemoteException {
      return getSiteByProperty( pName, pValue, pAccountId, null);
    }

    public SiteDataVector getSiteByProperty(String pName, String pValue, int pAccountId, AccCategoryToCostCenterView pCategToCostCenterView)

        throws RemoteException {



        SiteDataVector siteVec = new SiteDataVector();

        Connection conn = null;

        try {

            conn = getConnection();

            IdVector siteIdV = getSiteIdsByProperty(pName, pValue, pAccountId, conn);

            for(Iterator iter=siteIdV.iterator(); iter.hasNext();) {

                Integer siteIdI = (Integer) iter.next();

                BusEntityData siteBeD = BusEntityDataAccess.select(conn,siteIdI.intValue());

                siteVec.add(getSiteDetails(siteBeD, pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }



        return siteVec;

    }





    /**

     *  Gets all sites that match the given site property.

     *

     *@param  pName                property Short_desc value

     *@return                      a set of SiteData objects ordered by ids

     *      sites

     *@exception  RemoteException  if an error occurs

     */
    public SiteDataVector getSiteByPropertyForStore(String pName, String pValue, int pStoreId)

        throws RemoteException {
      return getSiteByPropertyForStore(pName, pValue, pStoreId, null);
    }

    public SiteDataVector getSiteByPropertyForStore(String pName, String pValue, int pStoreId, AccCategoryToCostCenterView pCategToCostCenterView)

        throws RemoteException {



        SiteDataVector siteVec = new SiteDataVector();

        Connection conn = null;

        try {

            conn = getConnection();

            IdVector siteIdV = getSiteIdsByPropertyForStore(pName, pValue, pStoreId, conn);

            for(Iterator iter=siteIdV.iterator(); iter.hasNext();) {

                Integer siteIdI = (Integer) iter.next();

                BusEntityData siteBeD = BusEntityDataAccess.select(conn,siteIdI.intValue());

                siteVec.add(getSiteDetails(siteBeD, pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }



        return siteVec;

    }



    /**

     *  Gets all site ids that match the given site property.

     *

     *@param  pName                property Short_desc value

     *@param  pAccountId           the id of the site account

     *@return                      a set of SiteData objects ordered by ids

     *      sites

     *@exception  RemoteException  if an error occurs

     */

    public IdVector getSiteIdsByProperty(String pName, String pValue, int pAccountId)

    throws RemoteException {

    	Connection conn = null;

    	IdVector siteIdV = new IdVector();

    	try {

    		conn = getConnection();

    		return getSiteIdsByProperty(pName, pValue, pAccountId, conn);

    	} catch (Exception e) {

    		throw processException(e);

    	} finally {

    		closeConnection(conn);

    	}

    }



    private IdVector getSiteIdsByProperty(String pName, String pValue, int pAccountId, Connection conn)

    throws Exception {



    	IdVector siteIdV = new IdVector();

		String sql =

			"SELECT bea.bus_entity1_id FROM clw_bus_entity_assoc bea JOIN clw_property pr "+

			" ON bea.bus_entity1_id = pr.bus_entity_id " +

			"  AND pr.short_desc = ? "+

			"  AND pr.clw_value = ? "+

			"  WHERE bea.bus_entity2_id = ?"+

			"  ORDER BY bea.bus_entity1_id ";

		PreparedStatement stmt = conn.prepareStatement(sql);



		stmt.setString(1,pName);

		stmt.setString(2,pValue);

		stmt.setInt(3,pAccountId);

		ResultSet rs=stmt.executeQuery();

		while (rs.next()) {

			int siteId =rs.getInt(1);

			siteIdV.add(new Integer(siteId));

		}

		rs.close();

		stmt.close();



    	return siteIdV;

    }

    /**
     * Gets all ACTIVE site ids that match the given site property.
     * 
     *@param pName
     *            property Short_desc value
     *@param pAccountId
     *            the id of the site account
     *@return a ordered set of site ids
     *@exception RemoteException
     *                if an error occurs
     */
    public IdVector getActiveSiteIdsByProperty(String pName, String pValue,
            int pAccountId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getActiveSiteIdsByProperty(pName, pValue, pAccountId, conn);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private IdVector getActiveSiteIdsByProperty(String pName, String pValue,
            int pAccountId, Connection conn) throws Exception {
        IdVector siteIdV = new IdVector();
        String sql = "SELECT DISTINCT bea.bus_entity1_id FROM clw_bus_entity_assoc bea JOIN clw_property pr "
                + " ON bea.bus_entity1_id = pr.bus_entity_id "
                + "  AND pr.short_desc = ? "
                + "  AND pr.clw_value = ? "
                + " JOIN clw_bus_entity be ON be.bus_entity_id = pr.bus_entity_id"
                + "  AND be.bus_entity_status_cd = ?"
                + "  AND be.bus_entity_type_cd = ? "
                + "  WHERE bea.bus_entity2_id = ?"
                + "  ORDER BY bea.bus_entity1_id ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pName);
        stmt.setString(2, pValue);
        stmt.setString(3, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        stmt.setString(4, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
        stmt.setInt(5, pAccountId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int siteId = rs.getInt(1);
            siteIdV.add(new Integer(siteId));
        }
        rs.close();
        stmt.close();
        return siteIdV;
    }

    private IdVector getSiteIdsByPropertyForStore(String pName, String pValue, int pStoreId, Connection conn)

    throws Exception {



    	IdVector siteIdV = new IdVector();

		String sql =

			"SELECT ba1.bus_entity1_id FROM clw_bus_entity_assoc ba1, clw_bus_entity_assoc ba2, clw_property p "+

			"WHERE "+

			"ba1.bus_entity2_id = ba2.bus_entity1_id AND "+

			"ba2.bus_entity2_id = ? AND "+

			"ba1.bus_entity1_id = p.bus_entity_id AND "+

			"p.short_desc = ? AND "+

			"p.clw_value = ? "+

			"ORDER BY ba1.bus_entity1_id ";

		PreparedStatement stmt = conn.prepareStatement(sql);



		stmt.setInt(1,pStoreId);

		stmt.setString(2,pName);

		stmt.setString(3,pValue);


		ResultSet rs=stmt.executeQuery();

		while (rs.next()) {

			int siteId =rs.getInt(1);

			siteIdV.add(new Integer(siteId));

		}

		rs.close();

		stmt.close();



    	return siteIdV;

    }





    /** DEPRECATED

     *  Gets all sites that match the given name. The arguments specify whether

     *  the name is interpreted as a pattern or exact match.

     *

     *@param  pName                a <code>String</code> value with site name or

     *      pattern

     *@param  pAccountId           the id of the site account. Will not apply if zero or less

     *@param  pStoreId             the id of the site store. Will not apply if zero or less

     *@param  pGetInactive         filters out inactive sites if false

     *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH,

     *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE

     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME

     *@return                      a <code>SiteDataVector</code> of matching

     *      sites

     *@exception  RemoteException  if an error occurs

     */

    public SiteViewVector getSiteByNameDeprecated(String pName, int pAccountId, int pStoreId,

           boolean pGetInactive, int pMatch, int pOrder)

             throws RemoteException {



      Connection conn = null;



      try {

        conn = getConnection();



        //String sqlSiteName = "select be.bus_entity_id, be.short_desc " +

        String sqlSiteName = "select be.bus_entity_id " +

           "from clw_bus_entity be, " +

                "clw_bus_entity_assoc bea1, " +

                "clw_bus_entity_assoc bea2 " +

           " where bus_entity_type_cd = 'SITE'"+

           " and be.bus_entity_id = bea1.bus_entity1_id "+

           " and bea1.bus_entity2_id = bea2.bus_entity1_id ";



        //String sqlSiteRef = "select be.bus_entity_id, be.short_desc " +

        String sqlSiteRef = "select be.bus_entity_id " +

           "from clw_bus_entity be, " +

                "clw_bus_entity_assoc bea1, " +

                "clw_bus_entity_assoc bea2, " +

                "clw_property pr "+

           " where bus_entity_type_cd = 'SITE'"+

           " and be.bus_entity_id = bea1.bus_entity1_id "+

           " and bea1.bus_entity2_id = bea2.bus_entity1_id "+

           " and pr.bus_entity_id = bea1.bus_entity1_id "+

           " and pr.short_desc =  '"+ RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+"' ";





            String beCond = "";
            String refCond = "";

            switch (pMatch) {

                case Site.EXACT_MATCH:
                  beCond = " and be.short_desc = ? ";
                  refCond = " and pr.clw_value = ? ";

                  break;

                case Site.EXACT_MATCH_IGNORE_CASE:

                  beCond = " and lower(be.short_desc) = lower(?) ";
                  refCond = " and lower(pr.clw_value) = lower(?) ";

                  break;

                case Site.BEGINS_WITH:
                case Site.CONTAINS:

                  beCond = " and be.short_desc like ? ";
                  refCond = " and pr.clw_value like ? ";

                  break;

                case Site.BEGINS_WITH_IGNORE_CASE:
                case Site.CONTAINS_IGNORE_CASE:

                  beCond = " and lower(be.short_desc) like lower(?) ";
                  refCond = " and lower(pr.clw_value) like lower(?) ";

                  break;

                default:

                    throw new RemoteException("getSiteByName: Bad match specification");

            }

            String acctStoreInactCond = "";

            if(pAccountId >0) {

              acctStoreInactCond += " and bea1.bus_entity2_id = "+pAccountId;

            }

            if(pStoreId >0) {

              acctStoreInactCond += " and bea2.bus_entity2_id = "+pStoreId;

            }

            if(!pGetInactive) {

              acctStoreInactCond += " and be.bus_entity_status_cd != '"+

                          RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE+"'";

            }



            sqlSiteName += acctStoreInactCond + beCond;

            sqlSiteRef += acctStoreInactCond + refCond;



            // select by name

            PreparedStatement stmt = conn.prepareStatement(sqlSiteName);

            switch (pMatch) {
                case Site.EXACT_MATCH:
                case Site.EXACT_MATCH_IGNORE_CASE:
                  stmt.setString(1, pName);
                  break;

                case Site.BEGINS_WITH:
                case Site.BEGINS_WITH_IGNORE_CASE:
                  stmt.setString(1, pName + "%");
                  break;

                case Site.CONTAINS:
                case Site.CONTAINS_IGNORE_CASE:
                  stmt.setString(1, "%"+pName+"%");
                  break;

            }


            ResultSet ids_name = stmt.executeQuery();

            IdVector siteIdV_name = new IdVector();

            while (ids_name.next()) {

              int id = ids_name.getInt(1);

              siteIdV_name.add(new Integer(id));

            }

            ids_name.close();
            stmt.close();


            // select by ref
            stmt = conn.prepareStatement(sqlSiteRef);
            switch (pMatch) {
                case Site.EXACT_MATCH:
                case Site.EXACT_MATCH_IGNORE_CASE:
                  stmt.setString(1, pName);
                  break;

                case Site.BEGINS_WITH:
                case Site.BEGINS_WITH_IGNORE_CASE:
                  stmt.setString(1, pName + "%");
                  break;

                case Site.CONTAINS:
                case Site.CONTAINS_IGNORE_CASE:
                  stmt.setString(1, "%"+pName+"%");
                  break;

            }

            ResultSet ids_ref = stmt.executeQuery();

            IdVector siteIdV_ref = new IdVector();

            while (ids_ref.next()) {

              int id = ids_ref.getInt(1);

              siteIdV_ref.add(new Integer(id));

            }

            ids_ref.close();



            stmt.close();



            if(siteIdV_name.size() == 0 && siteIdV_ref.size() == 0) {

              return new SiteViewVector();

            }



            // union

            if (siteIdV_ref.size() > 0) {

              Iterator i = siteIdV_name.iterator();

              while (i.hasNext()) {

                int site_id = ((Integer)i.next()).intValue();

                for (int j=0; j<siteIdV_ref.size(); j++) {

                   if (site_id == ((Integer)siteIdV_ref.get(j)).intValue()) {

                     siteIdV_ref.remove(j);

                     break;

                   }

                }

              }

            }

            for (int j=0; j<siteIdV_ref.size(); j++) {

                siteIdV_name.add((Integer)siteIdV_ref.get(j));

            }





            QueryRequest qr = new QueryRequest();

            qr.filterBySiteIdList(siteIdV_name);



            // sorting

            switch (pOrder) {

              case Site.ORDER_BY_ID:

                 qr.orderBySiteId(true);

                 break;

              case Site.ORDER_BY_NAME:

                 qr.orderBySiteName(true);

                 break;

            }



            SiteViewVector siteVwV = getSiteCollection(qr);

            return siteVwV;



        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

    }





    /**

     *  Get all sites that match the given address.

     *

     *@param  pAccountId           the id of the site account. If nonzero will

     *      only return a Sites that belongs to that account. Otherwise will

     *      return all matching Sites.

     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME

     *@param  pAddress             Description of the Parameter

     *@return                      a <code>SiteDataVector</code> of matching

     *      sites

     *@exception  RemoteException  if an error occurs

     */
    public SiteDataVector getSitesByAddress(AddressData pAddress, int pAccountId, int pOrder) throws RemoteException {
      return getSitesByAddress( pAddress,  pAccountId,  pOrder, null);
    }

    public SiteDataVector getSitesByAddress(AddressData pAddress, int pAccountId, int pOrder, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

        Connection conn = null;

        try {

            SiteDataVector siteVec = new SiteDataVector();

            String addrTab = AddressDataAccess.CLW_ADDRESS;

            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            if (pAccountId > 0) {

                crit.addJoinCondition(addrTab, AddressDataAccess.BUS_ENTITY_ID, BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID);

                crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountId);

            }

            boolean critSet = false;

            if (Utility.isSet(pAddress.getAddress1())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.ADDRESS1, pAddress.getAddress1());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getAddress2())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.ADDRESS2, pAddress.getAddress2());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getAddress3())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.ADDRESS3, pAddress.getAddress3());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getAddress4())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.ADDRESS4, pAddress.getAddress4());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getAddressStatusCd())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.ADDRESS_STATUS_CD, pAddress.getAddressStatusCd());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getAddressTypeCd())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.ADDRESS_TYPE_CD, pAddress.getAddressTypeCd());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getCity())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.CITY, pAddress.getCity());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getStateProvinceCd())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.STATE_PROVINCE_CD, pAddress.getStateProvinceCd());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getPostalCode())) {

                crit.addJoinTableLike(addrTab, AddressDataAccess.POSTAL_CODE, pAddress.getPostalCode() + "%");

                critSet = true;

            }

            if (Utility.isSet(pAddress.getCountryCd())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.COUNTRY_CD, pAddress.getCountryCd());

                critSet = true;

            }

            if (Utility.isSet(pAddress.getCountyCd())) {

                crit.addJoinTableEqualTo(addrTab, AddressDataAccess.COUNTY_CD, pAddress.getCountyCd());

                critSet = true;

            }

            if (!critSet) {

                throw new RemoteException("Address criteria was empty!");

            }

            crit.addJoinCondition(addrTab, AddressDataAccess.BUS_ENTITY_ID, BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID);

            crit.addDataAccessForJoin(new BusEntityDataAccess());

            List results = JoinDataAccess.select(conn, crit);

            Iterator it = results.iterator();

            while (it.hasNext()) {

                siteVec.add(getSiteDetails((BusEntityData) ((List) it.next()).get(0), pCategToCostCenterView));

            }

            return siteVec;

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

    }



    public IdVector getAllSiteIds(int pAccountId)throws RemoteException {

    	IdVector sites = new IdVector();

    	Connection conn = null;



        try {

            conn = getConnection();



            DBCriteria crit = new DBCriteria();

            if (pAccountId == 0) {

                crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

                        RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

            } else {

                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pAccountId);

                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);



                sites = BusEntityAssocDataAccess.selectIdOnly(conn,

                        BusEntityAssocDataAccess.BUS_ENTITY1_ID,crit);

            }

        }catch (Exception e) {

            throw new RemoteException("getAllSites: " + e.getMessage());

        } finally {



            try {



                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }

        return sites;

    }



    /**

     *  Get all the sites or all sites for a given account.

     *

     *@param  pAccountId           the Id of the sites account. If zero, all

     *      sites without regard to the account, will be returned

     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME

     *@return                      a <code>SiteDataVector</code> with all sites.

     *@exception  RemoteException  if an error occurs

     */
    public SiteDataVector getAllSites(int pAccountId, int pOrder)
             throws RemoteException {
           return getAllSites( pAccountId, pOrder, null);
    }

    public SiteDataVector getAllSites(int pAccountId, int pOrder, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {



        SiteDataVector siteVec = new SiteDataVector();

        Connection conn = null;



        try {

            conn = getConnection();



            DBCriteria crit = new DBCriteria();



            if (pAccountId == 0) {

                crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

                        RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

            } else {

                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,

                        pAccountId);

                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);



                IdVector ids = BusEntityAssocDataAccess.selectIdOnly(conn,

                        BusEntityAssocDataAccess.BUS_ENTITY1_ID,

                        crit);



                if (ids.size() == 0) {



                    // no sites for this account

                    return siteVec;

                }



                crit = new DBCriteria();

                crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, ids);

            }



            switch (pOrder) {



                case Site.ORDER_BY_ID:

                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);



                    break;

                case Site.ORDER_BY_NAME:

                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);



                    break;

                default:

                    throw new RemoteException("getAllSites: Bad order specification");

            }



            BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn,

                    crit, 250);



            for (int i = 0; i < busEntityVec.size(); i++) {

                siteVec.add(getSiteDetails((BusEntityData) busEntityVec.get(i), pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw new RemoteException("getAllSites: " + e.getMessage());

        } finally {



            try {



                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }



        return siteVec;

    }





    /**

     *  Describe <code>ejbCreate</code> method here.

     *

     *@exception  CreateException  if an error occurs

     *@exception  RemoteException  if an error occurs

     */

    public void ejbCreate()

             throws CreateException, RemoteException {

    }





    /**

     *  Describe <code>addSite</code> method here.

     *

     *@param  pSiteData                   a <code>SiteData</code> value

     *@param  pAccountId                  the id of the site account.

     *@return                             an <code>SiteData</code> value

     *@exception  RemoteException         if an error occurs

     *@exception  DuplicateNameException  Description of Exception

     */

    public SiteData addSite(SiteData pSiteData, int pAccountId)

             throws RemoteException, DuplicateNameException {



        BusEntityAssocData accountAssoc = pSiteData.getAccountAssoc();

  if ( pAccountId != accountAssoc.getBusEntity2Id() ) {

      accountAssoc.setBusEntity1Id(0);

      accountAssoc.setBusEntity2Id(pAccountId);

      // Make a new association for the site.

      accountAssoc.setBusEntityAssocId(0);

      pSiteData.getAccountBusEntity().setBusEntityId(pAccountId);

  }



        return updateSite(pSiteData);

    }





    public SiteData updateSiteAddress(SiteData pSiteData)

             throws RemoteException {



        Connection conn = null;

        String addUserName = "";

        String modUserName = "";

        boolean newSiteFl = false;



        try {

            conn = getConnection();



            AddressData ad = pSiteData.getSiteAddress();

            AddressDataAccess.update(conn, ad);

            AddressData finalad = AddressDataAccess.select(conn, ad.getAddressId());

            pSiteData.setSiteAddress(finalad);



        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }



        return pSiteData;

    }



    /**

     *  Updates the site information values to be used by the request.

     *

     *@param  pSiteData                   the SiteData site data.

     *@return                             an <code>SiteData</code> value

     *@exception  DuplicateNameException  Description of Exception

     *@throws  RemoteException            Required by EJB 1.0

     */

    public SiteData updateSite(SiteData pSiteData)

             throws RemoteException, DuplicateNameException {



        Connection conn = null;

        String addUserName = "";

        String modUserName = "";

        boolean newSiteFl = false;



        try {

            conn = getConnection();

            BusEntityData busEntity = pSiteData.getBusEntity();

            addUserName = busEntity.getAddBy();

            modUserName = busEntity.getModBy();



            if (busEntity.isDirty()) {



    String tname = DbUtility.fixQuotes(busEntity.getShortDesc());

                PropertyData refNumProp =

                        pSiteData.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);

                String refNum = (refNumProp!=null)?refNumProp.getValue():null;



                if(!Utility.isSet(refNum)) {

                    refNum = null;

                } else {

                    refNum = refNum.trim();

                }



                // check that name is unique

                String sql = "SELECT b1." +

                        BusEntityDataAccess.BUS_ENTITY_ID +

                        " FROM " +

                        BusEntityDataAccess.CLW_BUS_ENTITY + " b1, " +

                        BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +" ba1 " +

                        (refNum!=null? (", "+PropertyDataAccess.CLW_PROPERTY+" p "):"")+

                        " WHERE " +

                        //" b1." + BusEntityDataAccess.SHORT_DESC + " = '" + tname + "' "+
                        " b1." + BusEntityDataAccess.SHORT_DESC + " = ? "+

                        (refNum!=null? (" AND p."+PropertyDataAccess.SHORT_DESC+ " = '"+

                                RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+"'"):"")+

                        (refNum!=null? (" AND p."+PropertyDataAccess.BUS_ENTITY_ID+ " = " +

                                "b1."+BusEntityDataAccess.BUS_ENTITY_ID):"")+

                        //(refNum!=null? (" AND p."+PropertyDataAccess.CLW_VALUE+ " = '" + refNum+"'"):"")+
                        (refNum!=null? (" AND p."+PropertyDataAccess.CLW_VALUE+ " = ? "):"")+

                        "";



                if (busEntity.getBusEntityId() != 0) {


                    // This is an update to an existing site.

                    sql += " and b1." + BusEntityDataAccess.BUS_ENTITY_ID + " <> " + busEntity.getBusEntityId();

                }


                sql += " AND " + BusEntityDataAccess.BUS_ENTITY_TYPE_CD + " = " + "'" +
                    RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "' " +
                    " AND ba1." + BusEntityAssocDataAccess.BUS_ENTITY1_ID + " = b1." + BusEntityDataAccess.BUS_ENTITY_ID +
                    " and ba1. " + BusEntityAssocDataAccess.BUS_ENTITY2_ID + " = " + pSiteData.getAccountAssoc().getBusEntity2Id() +
                    " and ba1." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD + " = " + "'" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tname);
                if (refNum != null) {
                    stmt.setString(2, refNum);
                }

                ResultSet dups = stmt.executeQuery();



                if (dups.next()) {

                    logError("Duplicate site name: " +

                            busEntity.getShortDesc() + " for account id: " +

                            pSiteData.getAccountAssoc().getBusEntity2Id());



                    // found a duplicate.

                    throw new DuplicateNameException(BusEntityDataAccess.SHORT_DESC);

                }

                dups.close();

                stmt.close();



                int accountId = pSiteData.getAccountId();

                if(accountId<=0) {

                  throw new Exception("^clw^No account set for new site^clw^");

                }

                sql = "select p.clw_value "+

                 " from clw_bus_entity_assoc bea, clw_property p "+

                 " where bea.bus_entity_assoc_cd = ? "+

                 " and bea.bus_entity1_id = ? "+

                 " and p.bus_entity_id = bea.bus_entity2_id "+

                 " and p.property_type_cd = ?";



                stmt = conn.prepareStatement(sql);
                stmt.setString(1, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                stmt.setInt(2, accountId);
                stmt.setString(3, RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);

                ResultSet erpRes = stmt.executeQuery();

                String erpSystem = null;

                while (erpRes.next()) {

                  if(erpSystem==null) {

                    erpSystem = erpRes.getString(1);

                  } else if(!erpSystem.equals(erpRes.getString(1))) {

                    throw new Exception("^clw^Account "+accountId+" has multiple ERP systems^clw^");

                  }

                }

                if(erpSystem==null) {

                  throw new Exception("^clw^Account "+accountId+" doesn't have ERP systems^clw^");

                }

                erpRes.close();

                stmt.close();



                if (busEntity.getBusEntityId() == 0) {

                  busEntity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

                  busEntity = BusEntityDataAccess.insert(conn, busEntity);

                  pSiteData.setBusEntity(busEntity);

                  newSiteFl = true;

                }

                busEntity.setErpNum("#" + busEntity.getBusEntityId());

                BusEntityDataAccess.update(conn, busEntity);

            }



            int siteId = pSiteData.getBusEntity().getBusEntityId();

            BusEntityAssocData accountAssoc = pSiteData.getAccountAssoc();



      logDebug( "updateSite, accountAssoc=" + accountAssoc);

            if (accountAssoc.isDirty()) {



    logDebug( "updateSite, accountAssoc isDirty");

                if (accountAssoc.getBusEntityAssocId() == 0) {

                    accountAssoc.setBusEntity1Id(siteId);

                    accountAssoc.setBusEntityAssocCd(

                            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

        logDebug( "updateSite, accountAssoc insert");

                    BusEntityAssocDataAccess.insert(conn, accountAssoc);

                } else {

        logDebug( "updateSite, accountAssoc update");

                    BusEntityAssocDataAccess.update(conn, accountAssoc);

                }

            }



            AddressData address = pSiteData.getSiteAddress();

            String county = address.getCountyCd();



            if (county == null) {

                county = "";

            }



            if (county.trim().length() == 0) {



                DBCriteria dbc = new DBCriteria();

                dbc.addEqualTo(PostalCodeDataAccess.COUNTRY_CD, address.getCountryCd());

                dbc.addEqualTo(PostalCodeDataAccess.POSTAL_CODE, address.getPostalCode());



                PostalCodeDataVector pcDV = PostalCodeDataAccess.select(conn, dbc);



                if (pcDV.size() == 1) {

                    PostalCodeData pcD = (PostalCodeData) pcDV.get(0);

                    address.setCountyCd(pcD.getCountyCd());

                }

            }



            if (address.isDirty()) {



                if (address.getAddressId() == 0) {

                    address.setBusEntityId(siteId);

                    AddressDataAccess.insert(conn, address);

                } else {

                    AddressDataAccess.update(conn, address);

                }

            }



            PhoneDataVector phones = pSiteData.getPhones();

            Iterator phoneIter = phones.iterator();



            while (phoneIter.hasNext()) {



                PhoneData phone = (PhoneData) phoneIter.next();



                if (phone.isDirty()) {



                    if (phone.getPhoneId() == 0) {

                        phone.setBusEntityId(siteId);

                        PhoneDataAccess.insert(conn, phone);

                    } else {

                        PhoneDataAccess.update(conn, phone);

                    }

                }

            }



            BudgetViewVector budgets = pSiteData.getBudgets();

            Iterator budgetIter = budgets.iterator();

            while (budgetIter.hasNext()) {

                BudgetView budget = (BudgetView) budgetIter.next();

                budget = BudgetDAO.updateBudget(conn, budget, modUserName);

            }



            setBSCForSite(conn, pSiteData);





            DBCriteria bpoCrit = new DBCriteria();

            bpoCrit.addEqualTo(BlanketPoNumAssocDataAccess.BUS_ENTITY_ID,siteId);

            if(pSiteData.getBlanketPoNum() == null || pSiteData.getBlanketPoNum().getBlanketPoNumId() == 0){

                //remove the current associations

                BlanketPoNumAssocDataAccess.remove(conn,bpoCrit);

            }else{

                BlanketPoNumAssocDataVector bpoavec = BlanketPoNumAssocDataAccess.select(conn,bpoCrit);

                BlanketPoNumAssocData currbpoa = null;

                if(bpoavec.size() > 1){

                    //do some cleanup

                    BlanketPoNumAssocDataAccess.remove(conn,bpoCrit);

                }else if(bpoavec.size() == 1){

                    currbpoa = (BlanketPoNumAssocData) bpoavec.get(0);

                    currbpoa.setModBy(modUserName);

                }

                if(currbpoa == null){

                    currbpoa = BlanketPoNumAssocData.createValue();

                    currbpoa.setAddBy(modUserName);  //intentional

                    currbpoa.setModBy(modUserName);

                }

                currbpoa.setBlanketPoNumId(pSiteData.getBlanketPoNum().getBlanketPoNumId());

                currbpoa.setBusEntityId(siteId);

                if(currbpoa.getBlanketPoNumAssocId() == 0){

                    currbpoa = BlanketPoNumAssocDataAccess.insert(conn, currbpoa);

                }else{

                    BlanketPoNumAssocDataAccess.update(conn, currbpoa);

                }

            }



            String userName = pSiteData.getBusEntity().getModBy();

            PropertyUtil p = new PropertyUtil(conn);



            String tfr = "";

            if (pSiteData.getTargetFacilityRank() != null) {

                tfr = pSiteData.getTargetFacilityRank().toString();

            }

            p.saveValue(0, siteId,

                    RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK, RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK,

                    tfr);





            PropertyData taxableIndicator = pSiteData.getTaxableIndicator();

            if (taxableIndicator.isDirty()) {

                taxableIndicator.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR);

                taxableIndicator.setPropertyStatusCd(

                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

                taxableIndicator.setPropertyTypeCd(

                        RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR);

                taxableIndicator.setBusEntityId(siteId);



                if (taxableIndicator.getPropertyId() == 0) {

                    PropertyDataAccess.insert(conn, taxableIndicator);

                } else {

                    PropertyDataAccess.update(conn, taxableIndicator);

                }

            }



            PropertyData invShop = pSiteData.getInventoryShopping();

            logDebug("invShop=" + invShop);

            if (invShop != null && invShop.isDirty()) {

                invShop.setShortDesc

                        (RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING);



                invShop.setPropertyStatusCd

                        (RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

                invShop.setPropertyTypeCd

                        (RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING);

                invShop.setBusEntityId(siteId);



                if (invShop.getPropertyId() == 0) {

                    PropertyDataAccess.insert(conn, invShop);

                } else {

                    PropertyDataAccess.update(conn, invShop);

                }

            }
            
            //STJ-4384
            PropertyData reBillProperty = pSiteData.getReBill();
            logDebug("reBillProperty=" + reBillProperty);
            if (reBillProperty != null && reBillProperty.isDirty()) {
            	reBillProperty.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER);
            	reBillProperty.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            	reBillProperty.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER);
            	reBillProperty.setBusEntityId(siteId);
                if (reBillProperty.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, reBillProperty);
                } else {
                    PropertyDataAccess.update(conn, reBillProperty);
                }
            }

            PropertyData invShopType = pSiteData.getInventoryShoppingType();

            logDebug("invShopType=" + invShopType);

            if (invShopType != null && invShopType.isDirty()) {

                invShopType.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING_TYPE);

                invShopType.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

                invShopType.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING_TYPE);

                invShopType.setBusEntityId(siteId);

                if (invShopType.getPropertyId() == 0) {

                    PropertyDataAccess.insert(conn, invShopType);

                } else {

                    PropertyDataAccess.update(conn, invShopType);

                }

            }



            PropertyData invShopHold = pSiteData.getInventoryShoppingHoldOrderUntilDeliveryDate();

            if (invShopHold != null && invShopHold.isDirty()) {

              invShopHold.setShortDesc

                        (RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOP_HOLD_DEL_DATE);



              invShopHold.setPropertyStatusCd

                        (RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

              invShopHold.setPropertyTypeCd

                        (RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOP_HOLD_DEL_DATE);

              invShopHold.setBusEntityId(siteId);



                if (invShopHold.getPropertyId() == 0) {

                    PropertyDataAccess.insert(conn, invShopHold);

                } else {

                    PropertyDataAccess.update(conn, invShopHold);

                }

            }

            PropertyData allowCorpSchedOrder = pSiteData.getAllowCorpSchedOrder();
            logDebug("allowCorpSchedOrder=" + allowCorpSchedOrder);
            if (allowCorpSchedOrder != null && allowCorpSchedOrder.isDirty()) {
            	allowCorpSchedOrder.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER);
            	allowCorpSchedOrder.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            	allowCorpSchedOrder.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER);
            	allowCorpSchedOrder.setBusEntityId(siteId);
                if (allowCorpSchedOrder.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, allowCorpSchedOrder);
                } else {
                    PropertyDataAccess.update(conn, allowCorpSchedOrder);
                }
            }

            PropertyDataVector miscProperties = pSiteData.getMiscProperties();

            Iterator propIter = miscProperties.iterator();



            while (propIter.hasNext()) {



                PropertyData prop = (PropertyData) propIter.next();



                if (prop.isDirty()) {



                    if (prop.getPropertyId() == 0) {

                        if (prop.getPropertyTypeCd().equals("")) {

                          prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);

                        }

                        if (prop.getPropertyStatusCd().equals("")) {

                            prop.setPropertyStatusCd(

                                    RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

                        }



                        prop.setBusEntityId(siteId);

                        PropertyDataAccess.insert(conn, prop);

                    } else {

                        PropertyDataAccess.update(conn, prop);

                    }

                }

            }



            saveFieldsData(conn, siteId, pSiteData.getDataFieldProperties());



        } catch (DuplicateNameException de) {

            throw de;

        } catch (Exception e) {

            throw processException(e);

        } finally {

            // notify of updates should any sites be cached.

            CacheManager.rollCacheVersion();

            closeConnection(conn);

        }



        return pSiteData;

    }





    /*

     *  The approach taken is to remove the old fields and

     *  insert the new.  This simplifies the logic in the

     *  cases where some fields may not longer be needed.

     */

    /**

     *  Description of the Method

     *

     *@param  pDbConn           Description of the Parameter

     *@param  pSiteId           Description of the Parameter

     *@param  pFieldProps       Description of the Parameter

     *@exception  SQLException  Description of the Exception

     */

    private void saveFieldsData(Connection pDbConn, int pSiteId,

            PropertyDataVector pFieldProps)

             throws SQLException {

        if (pFieldProps != null){

	        DBCriteria crit = new DBCriteria();

	        crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pSiteId);

	        crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,

	                RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);

	        PropertyDataAccess.remove(pDbConn, crit);

	        Iterator propIter2 = pFieldProps.iterator();



	        while (propIter2.hasNext()) {



	            PropertyData prop = (PropertyData) propIter2.next();

	            prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);

	            prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

	            prop.setBusEntityId(pSiteId);

	            PropertyDataAccess.insert(pDbConn, prop);

	        }

        }

    }





    /**

     *  Description of the Method

     *

     *@param  pSiteId              Description of the Parameter

     *@param  pFieldProps          Description of the Parameter

     *@exception  RemoteException  Description of the Exception

     */

    public void saveSiteFields(int pSiteId, PropertyDataVector pFieldProps)

             throws RemoteException {



        Connection conn = null;



        try {

            conn = getConnection();

            saveFieldsData(conn, pSiteId, pFieldProps);

        } catch (Exception e) {

            throw new RemoteException("saveSiteFields: " + e.getMessage());

        } finally {



            try {



                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }

    }









    /**

     *  <code>removeSite</code> may be used to remove an 'unused' site. An

     *  unused site is a site with no database references other than the default

     *  primary address, phone numbers, email addresses, properties and the

     *  site/account association. Attempting to remove a site that is used will

     *  result in a failure initially reported as a SQLException and

     *  consequently caught and rethrown as a RemoteException.

     *

     *@param  pSiteData            a <code>SiteData</code> value

     *@exception  RemoteException  if an error occurs

     */

    public void removeSite(SiteData pSiteData)

             throws RemoteException {



        Connection conn = null;



        try {

            conn = getConnection();



            int siteId = pSiteData.getBusEntity().getBusEntityId();

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, siteId);

            PropertyDataAccess.remove(conn, crit);

            PhoneDataAccess.remove(conn, crit);

            BudgetDataAccess.remove(conn, crit);

            EmailDataAccess.remove(conn, crit);

            AddressDataAccess.remove(conn, crit);

            CatalogAssocDataAccess.remove(conn, crit);

            crit = new DBCriteria();

            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteId);

            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

            BusEntityAssocDataAccess.remove(conn, crit);

            BusEntityDataAccess.remove(conn, siteId);

        } catch (Exception e) {

            throw new RemoteException("removeSite: " + e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }





    /**

     *  Find all sites related to this order guide. The sites listed be limited

     *  to the sites configured for a user. This relationship is rather complex

     *  due to the default account catalog relationship.

     *

     *@param  pOrderGuideId

     *@param  pUserId              Description of Parameter

     *@return                      Description of the Returned Value

     *@exception  RemoteException  Description of Exception

     */
    public SiteDataVector fetchSitesForOrderGuide(int pOrderGuideId, int pUserId)
             throws RemoteException {
           return fetchSitesForOrderGuide( pOrderGuideId, pUserId, null);
   }

    public SiteDataVector fetchSitesForOrderGuide(int pOrderGuideId,

            int pUserId, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {



        SiteDataVector siteVec = new SiteDataVector();

        Connection conn = null;



        try {

            conn = getConnection();



            OrderGuideData ogd = OrderGuideDataAccess.select(conn,

                    pOrderGuideId);

            String ogType = ogd.getOrderGuideTypeCd();



            if (RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE.equals(

                    ogType)) {



                // The user id is ignored, since a buyer order

                // guide must belong to a user.  Get the sites

                // configured for this user.

                BusEntityData busEntity = null;

                busEntity = BusEntityDataAccess.select(conn,

                        ogd.getBusEntityId());

                siteVec.add(getSiteDetails(busEntity, pCategToCostCenterView));



                return siteVec;

            } else if (RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE.equals(

                    ogType)) {



                BusEntityDataVector busEntityVec = new BusEntityDataVector();



                // Get the catalog.

                CatalogData catd = CatalogDataAccess.select(conn,

                        ogd.getCatalogId());

                String catType = catd.getCatalogTypeCd();

                IdVector siteids = new IdVector();

                IdVector userSiteids = new IdVector();

                DBCriteria crit = new DBCriteria();



                // Limit the sites listed for the order guide.

                // Fetch the sites for this user.

                crit.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);

                crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,

                        RefCodeNames.USER_ASSOC_CD.SITE);

                userSiteids = UserAssocDataAccess.selectIdOnly(conn,

                        UserAssocDataAccess.BUS_ENTITY_ID,

                        crit);



                if (userSiteids.size() <= 0) {



                    // no sites configured for this user, return an empty list.

                    return siteVec;

                }



                if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(catType)) {



                    // Get all sites directly tied to this catalog.

                    siteids = fetchShoppingCatalogSiteids(ogd.getCatalogId(),

                            userSiteids, conn);

                } else if (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catType)) {

                    siteids = fetchAccountCatalogSiteids(ogd.getCatalogId(),

                            userSiteids, conn);

                }



                busEntityVec = BusEntityDataAccess.select(conn, siteids);



                for (int i = 0; i < busEntityVec.size(); i++) {

                    siteVec.add(getSiteDetails(

                            (BusEntityData) busEntityVec.get(i), pCategToCostCenterView));

                }

            } else {



                String msg = "Site.fetchSitesForOrderGuide: Error: " +

                        "unknown order guide type: " + ogType;

                logError(msg);

                throw new RemoteException(msg);

            }

        } catch (Exception e) {

            throw new RemoteException("Site.fetchSitesForOrderGuide: " +

                    e.getMessage());

        } finally {



    closeConnection(conn);

        }



        return siteVec;

    }





    /**

     *  Describe <code>getSiteDetails</code> method here.

     *

     *@param  pBusEntity           a <code>BusEntityData</code> value

     *@return                      a <code>SiteData</code> value

     *@exception  RemoteException  if an error occurs

     */

    private SiteData getSiteDetails(BusEntityData pBusEntity, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {
    	//Gets the site budgets based on the current fiscal year. 
    	return getSiteDetailsByBudgetYear(pBusEntity,pCategToCostCenterView,NO_BUDGET_YEAR_REQUIRED);
    }

    /**
     * Gets the site data with budgets by specified budget year. 
     * If Budget year value is -1 (by default) then site budgets will be fetched based on the current fiscal year. 
     * If Budget year value is not equals to -1 then the budgets will be fetched based on the specified budget year.
     * @param pBusEntity
     * @param pCategToCostCenterView
     * @param budgetYear
     * @return
     * @throws RemoteException
     */
    private SiteData getSiteDetailsByBudgetYear(BusEntityData pBusEntity, AccCategoryToCostCenterView pCategToCostCenterView , int pBudgetYear)

        throws RemoteException {


        if (pBusEntity.getErpNum() == null) {

            pBusEntity.setErpNum("");

        }

        Connection conn = null;

        SiteData siteD = null;

  BusEntityDAO bedao = mBDAO;



        try {

            Workflow wkflBean = APIAccess.getAPIAccess().getWorkflowAPI();

            conn = getConnection();
            if(pBudgetYear==NO_BUDGET_YEAR_REQUIRED) {
            	// Get the site budgets by current fiscal year.
            	siteD = bedao.getSiteData(conn, pBusEntity.getBusEntityId());
            } else {
            	//Get the site budgets by specified fiscal/budget year.
            	siteD = bedao.getSiteData(conn, pBusEntity.getBusEntityId(),pBudgetYear);
            }

            logDebug("SD: " + siteD.getBusEntity());


            //Jd begin

            siteD = setThresholdAmounts(conn, siteD);

            //Jd end



      // set the current budget and inventory periods



      siteD.setCurrentInventoryPeriod

    (bedao.getCurrentSiteInventoryPeriod(conn, siteD.getSiteId()));



      siteD.setCurrentBudgetPeriod

    (bedao.getCurrentSiteBudgetPeriod(conn, siteD.getSiteId()));



            // get the budget periods

            siteD.setBudgetPeriods(new Hashtable(calculateSiteBudgetDates

                    (conn, siteD.getSiteId())));



            setBudgetSpendingInfo(conn, siteD);





            // Get any inventory configured items.

            siteD.setSiteInventory

                    (lookupSiteInventory(conn, siteD.getSiteId(),pCategToCostCenterView));



            siteD.setShoppingControls

    (lookupSiteShoppingControls(conn, siteD.getSiteId(),

              siteD.getAccountId()));



            ScheduleOrderDates sod = calculateNextOrderDates

                    (conn, siteD.getBusEntity().getBusEntityId(), siteD.getAccountId(),1);



            if (sod != null) {

                // Scheduled order dates configured.

                logDebug("Scheduled order information: " + sod);

                siteD.setNextDeliveryDate(sod.getNextOrderDeliveryDate());

                siteD.setNextOrdercutoffDate(sod.getNextOrderCutoffDate());

                siteD.setNextOrdercutoffTime(sod.getNextOrderCutoffTime());

                if(siteD.getContractData()!=null){

                    siteD.setInventoryCartAccessInterval(sod.getInventoryCartAccessInterval());

                }

                siteD.setPhysicalInventoryPeriods(sod.getPhysicalInventoryPeriods());

            }

            // Add any workflow related message.



            /*siteD.setWorkflowMessage(wkflBean.getSiteWorkflowMessage

                    (siteD.getBusEntity().getBusEntityId(), RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW)

                    );*/



            String wflwMess = wkflBean.getSiteWorkflowMessage

            (siteD.getBusEntity().getBusEntityId(), RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW);

            if(wflwMess != null){
            	siteD.setWorkflowMessage(wflwMess);
            }



            try {

                siteD.setBSC(

                        getBSCForSite(conn, siteD.getSiteId())

                        );

            } catch (DataNotFoundException dnf) {

                logDebug("NO BSC set for site id=" + siteD.getSiteId());

            }


        } catch (Exception e) {

          e.printStackTrace();

            logError("SiteBean error (" + e.getMessage() + ") for siteD=" + siteD);

            throw new RemoteException("getSiteDetails: " + e.getMessage());

        } finally {

            closeConnection(conn);

        }



        return siteD;

    }



	private void setBudgetSpendingInfo(Connection conn, SiteData siteD) throws Exception {
		
		if (siteD.hasBudgets()) {
			PropertyUtil p = new PropertyUtil(conn);
            String accrualCd = p.fetchValueIgnoreMissing(0,siteD.getAccountId(),RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD);

		    // calculate the most recent budget numbers.
		    logDebug("-- calculate budget for siteid=" +siteD.getSiteId());
		    BudgetUtil bu = new BudgetUtil(conn);
		    BudgetSpendViewVector sv = bu.getAllBudgetSpentForSite(siteD.getAccountId(), siteD.getSiteId(),accrualCd);
		    logDebug("sv.size=" + sv.size());
		    siteD.setSpendingInfo(sv);
		    logDebug("-- DONE with calculate budget for siteid=" +
		            siteD.getSiteId() +
		            " alloc=" + siteD.getTotalBudgetAllocated() +
		            " spent=" + siteD.getTotalBudgetSpent());
		}
	}


    /**

     *  Description of the Method

     *

     *@param  pCon     Description of the Parameter

     *@param  pSiteId  Description of the Parameter

     *@return          Description of the Return Value

     */

    private int lookupSiteCatalogId(Connection pCon, int pSiteId) {

        DBCriteria dbc = new DBCriteria();

        try {

            dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,

                    pSiteId);

            dbc.addOrderBy(CatalogAssocDataAccess.CATALOG_ID, false);

            CatalogAssocDataVector v =

                    CatalogAssocDataAccess.select(pCon, dbc);

            if (v != null && v.size() > 0) {

                return ((CatalogAssocData) v.get(0)).getCatalogId();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return 0;

    }





    /**

     * Description of the Method

     *

     * @param pCon    Description of the Parameter

     * @param pAccountId Description of the Parameter

     * @return Description of the Return Value

     */

    private InventoryItemsDataVector lookupAvailableSiteInventoryItems(Connection pCon, int pAccountId, IdVector itemIds) {

        try {



            DBCriteria dbc = new DBCriteria();



            dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, pAccountId);

            dbc.addEqualTo(InventoryItemsDataAccess.STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            if (itemIds != null && !itemIds.isEmpty())
            	dbc.addOneOf(InventoryItemsDataAccess.ITEM_ID, itemIds);

            return InventoryItemsDataAccess.select(pCon, dbc);



        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }



    /*

    * Retrieves inventory Items associated with a catalog.

    */

    private InventoryItemsDataVector lookupAvailableSiteInventoryItemsInCatalog(Connection pCon, int pAccountId, int pCatalogId, IdVector itemIds) {

        try {



            DBCriteria dbc = new DBCriteria();



            dbc.addJoinCondition(InventoryItemsDataAccess.CLW_INVENTORY_ITEMS,

                    InventoryItemsDataAccess.ITEM_ID,

                    CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,

                    CatalogStructureDataAccess.ITEM_ID);



            dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, pAccountId);

            dbc.addEqualTo(InventoryItemsDataAccess.STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            dbc.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

            if (itemIds != null && !itemIds.isEmpty()) {
            	//STJ-5943
            	if (itemIds.size() < DBCriteria.MAX_SQL_ITEMS) {
            		dbc.addOneOf(InventoryItemsDataAccess.ITEM_ID, itemIds);
            	}
            	else {
            		DBCriteria dbc2 = new DBCriteria();
            		dbc2.addOneOf(InventoryItemsDataAccess.CLW_INVENTORY_ITEMS + "." + InventoryItemsDataAccess.ITEM_ID, itemIds);
            		dbc.addCondition(dbc2.getWhereClause(), false);
            	}
            }

            return InventoryItemsDataAccess.select(pCon, dbc);



        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }



    /**

     *  Description of the Method

     *

     *@param  v                    Description of the Parameter

     *@return                      Description of the Return Value

     *@exception  RemoteException  Description of the Exception

     */
    public SiteInventoryConfigViewVector updateInventoryConfig(SiteInventoryConfigViewVector v)
             throws RemoteException {
        return    updateInventoryConfig(v, null) ;
    }

    public SiteInventoryConfigViewVector

  updateInventoryConfig(SiteInventoryConfigViewVector v, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {

        int siteid = 0;

        Connection con = null;

        try {

            con = getConnection();

            for (int i = 0; v != null && i < v.size(); i++) {

                SiteInventoryConfigView sicv =

                        (SiteInventoryConfigView) v.get(i);

                storeInventoryConfigData(con, sicv);



                if (sicv.getSiteId() > 0) {

                    siteid = sicv.getSiteId();

                }

            }

            if (siteid == 0) {

                return null;

            }

            return _lookupSiteInventoryConfig(con, siteid, false, pCategToCostCenterView);

        } catch (Exception e) {

            logError(e.getMessage());

            e.printStackTrace();

        } finally {

            closeConnection(con);

        }



        return null;

    }


    public void storeInventoryConfig(SiteInventoryConfigViewVector v) throws RemoteException {

        Connection con = null;

        if (v != null) {

            try {

                con = getConnection();

                for (Object o : v) {

                    SiteInventoryConfigView sicv = (SiteInventoryConfigView) o;

                    updateInventoryConfigMod(con, sicv);

                }

            } catch (Exception e) {

                logError(e.getMessage());

                e.printStackTrace();

            } finally {

                closeConnection(con);

            }

        }

    }



    /**

     *  Description of the Method

     *

     *@param  v                    Description of the Parameter

     *@param  pUser                Description of the Parameter

     *@return                      Description of the Return Value

     *@exception  RemoteException  Description of the Exception

     */
    public SiteInventoryConfigViewVector

  updateInventory(SiteInventoryConfigViewVector v,  UserData pUser)

  throws RemoteException {
    return updateInventory( v, pUser, null);

}

    public SiteInventoryConfigViewVector

  updateInventory(SiteInventoryConfigViewVector v,

      UserData pUser, AccCategoryToCostCenterView pCategToCostCenterView)

  throws RemoteException {

        return doUpdateInventory(v, false, pUser, pCategToCostCenterView);

    }





    /**

     *  Description of the Method

     *

     *@param  v                    Description of the Parameter

     *@param  pUser                Description of the Parameter

     *@return                      Description of the Return Value

     *@exception  RemoteException  Description of the Exception

     */
    public SiteInventoryConfigViewVector updateAndResetInventory(SiteInventoryConfigViewVector v,  UserData pUser)
             throws RemoteException {
           return updateAndResetInventory( v,  pUser, null);
         }

    public SiteInventoryConfigViewVector

  updateAndResetInventory(SiteInventoryConfigViewVector v,

            UserData pUser, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {

        return doUpdateInventory(v, true, pUser, pCategToCostCenterView);

    }





    /**

     *  Description of the Method

     *

     *@param  v                    Description of the Parameter

     *@param  pUpdateNullValues    Description of the Parameter

     *@param  pUser                Description of the Parameter

     *@return                      Description of the Return Value

     *@exception  RemoteException  Description of the Exception

     */

    private SiteInventoryConfigViewVector

  doUpdateInventory(SiteInventoryConfigViewVector v,

        boolean pUpdateNullValues,

        UserData pUser, AccCategoryToCostCenterView pCategToCostCenterView)

  throws RemoteException {



        // If the user is configured got browse only,

        // don't save the cart.

        UserRightsTool urt = new UserRightsTool(pUser);

        if (urt.isBrowseOnly()) {

            return v;

        }



        int siteid = 0;

        Connection con = null;

        try {

            con = getConnection();

            for (int i = 0; v != null && i < v.size(); i++) {

                SiteInventoryInfoView sicv =

                        (SiteInventoryInfoView) v.get(i);

                // on hand qty

                storeInventoryOnhand(con, sicv, pUser.getUserName() );



                if (sicv.getSiteId() > 0) {

                    siteid = sicv.getSiteId();

                }

            }

            if (siteid == 0) {

                return null;

            }



            if (pUpdateNullValues) {

                resetSiteInventory(con, siteid);

            }

            // Update the quantities in the cart for this site.



            return lookupSiteInventory(con, siteid, pCategToCostCenterView);

        } catch (Exception e) {

            logError(e.getMessage());

            e.printStackTrace();

        } finally {

            closeConnection(con);

        }



        return null;

    }





    /**

     * Description of the Method

     *

     * @param pCon       Description of the Parameter

     * @param pInventory Description of the Parameter

     * @throws SQLException Description of the Exception

     */

    private void storeInventoryConfigData(Connection pCon, SiteInventoryConfigView pInventory) throws Exception {



        InventoryLevelViewVector ilvv = InventoryLevelDAO.getInvLevelViewCollections(pCon,

                pInventory.getSiteId(),

                Utility.toIdVector(pInventory.getItemId()));



        if (ilvv.isEmpty()) {



            InventoryLevelData newild = InventoryLevelData.createValue();

            InventoryLevelDetailDataVector parValues = new InventoryLevelDetailDataVector();



            InventoryLevelViewWrapper ilWrapper = new InventoryLevelViewWrapper(new InventoryLevelView(newild, parValues));



            ilWrapper.setBusEntityId(pInventory.getSiteId());

            ilWrapper.setItemId(pInventory.getItemId());



            if (pInventory.getParValues() != null) {

                Set periods = pInventory.getParValues().keySet();

                for (Object oPeriod : periods) {

                    int period = (Integer) oPeriod;

                    Integer value = (Integer) pInventory.getParValues().get(oPeriod);

                    ilWrapper.setParValue(period, value);

                }

            }



            ilWrapper.setParsModBy(pInventory.getModBy());

            ilWrapper.setParsModDate(new java.util.Date(System.currentTimeMillis()));



            InventoryLevelDAO.updateInventoryLevelView(pCon, ilWrapper.getInventoryLevelView(), pInventory.getModBy());



        } else {



            for (Object oilv : ilvv) {



                InventoryLevelView ilv = (InventoryLevelView) oilv;



                if (ilv.getInventoryLevelData().getItemId() == pInventory.getItemId() &&

                        ilv.getInventoryLevelData().getBusEntityId() == pInventory.getSiteId()) {



                    InventoryLevelViewWrapper ilWrapper = new InventoryLevelViewWrapper(ilv);

                    HashMap<Integer, Integer> ilParValueMap = Utility.getParValueMap(ilWrapper.getParValues());



                    if (Utility.mapNotEqual(ilParValueMap, pInventory.getParValues())) {



                        if (pInventory.getParValues() != null) {

                            ilWrapper.setParValues(new InventoryLevelDetailDataVector());

                            Set periods = pInventory.getParValues().keySet();

                            for (Object oPeriod : periods) {

                                int period = (Integer) oPeriod;

                                Integer value = (Integer) pInventory.getParValues().get(oPeriod);

                                ilWrapper.setParValue(period, value);

                            }

                        }





                        ilWrapper.setParsModBy(pInventory.getModBy());

                        ilWrapper.setParsModDate(new Date(System.currentTimeMillis()));



                        InventoryLevelDAO.updateInventoryLevelView(pCon, ilWrapper.getInventoryLevelView(), pInventory.getModBy());



                    }

                }

            }

        }

    }



    public SiteInventoryConfigViewVector updateInventoryConfigMod(int pSiteId, SiteInventoryConfigViewVector v, boolean pUpdateFromPhysCart) throws RemoteException {
      return updateInventoryConfigMod( pSiteId,  v, pUpdateFromPhysCart, null) ;
    }
    public SiteInventoryConfigViewVector updateInventoryConfigMod(int pSiteId, SiteInventoryConfigViewVector v, boolean pUpdateFromPhysCart, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {



        Connection con = null;

        SiteInventoryConfigViewVector result = null;

        if (v != null) {

            try {



                con = getConnection();


                for (Object o : v) {

                    SiteInventoryConfigView sicv = (SiteInventoryConfigView) o;
                    if (pSiteId== sicv.getSiteId()) {

                        updateInventoryConfigMod(con, sicv, pUpdateFromPhysCart);

                    }

                }

                result = _lookupSiteInventoryConfig(con, pSiteId,false, pCategToCostCenterView);


            } catch (Exception e) {
                logError(e.getMessage());

                e.printStackTrace();

            } finally {

                closeConnection(con);

            }

        }



        return result;

    }

    private void updateInventoryConfigMod(Connection pCon, SiteInventoryConfigView pInventory) throws Exception {
        updateInventoryConfigMod(pCon, pInventory, false);

    }


    private void updateInventoryConfigMod(Connection pCon, SiteInventoryConfigView pInventory, boolean pUpdateFromPhysCart) throws Exception {



        InventoryLevelViewVector ilvv = InventoryLevelDAO.getInvLevelViewCollections(pCon, pInventory.getSiteId(), null);

        if (ilvv == null || ilvv.isEmpty()) {



            InventoryLevelData newild = InventoryLevelData.createValue();

            InventoryLevelDetailDataVector parValues = new InventoryLevelDetailDataVector();



            InventoryLevelViewWrapper ilWrapper = new InventoryLevelViewWrapper(new InventoryLevelView(newild, parValues));



            ilWrapper.setBusEntityId(pInventory.getSiteId());

            ilWrapper.setOrderQty(pInventory.getOrderQty());

            ilWrapper.setQtyOnHand(pInventory.getQtyOnHand());

            ilWrapper.setItemId(pInventory.getItemId());



            if (pInventory.getParValues() != null) {

                Set periods = pInventory.getParValues().keySet();

                for (Object oPeriod : periods) {

                    int period = (Integer) oPeriod;

                    Integer value = (Integer) pInventory.getParValues().get(oPeriod);

                    ilWrapper.setParValue(period, value);

                }

            }



            ilWrapper.setParsModBy(pInventory.getModBy());

            ilWrapper.setParsModDate(new java.util.Date(System.currentTimeMillis()));



            InventoryLevelDAO.updateInventoryLevelView(pCon, ilWrapper.getInventoryLevelView(), pInventory.getModBy());



        } else {



            for (Object oilv : ilvv) {



                InventoryLevelView ilv = (InventoryLevelView) oilv;



                if (ilv.getInventoryLevelData().getItemId() == pInventory.getItemId() &&

                        ilv.getInventoryLevelData().getBusEntityId() == pInventory.getSiteId()) {



                    InventoryLevelViewWrapper ilWrapper = new InventoryLevelViewWrapper(ilv);



                    String newOrderQtyStr  = Utility.strNN(pInventory.getOrderQty());

                    String oldOrderQtyStr  = Utility.strNN(ilWrapper.getOrderQty());

                    String newQtyOnHandStr = Utility.strNN(pInventory.getQtyOnHand());

                    String oldQtyOnHandStr = Utility.strNN(ilWrapper.getQtyOnHand());



                    boolean changeFl = false;



                    if (!newOrderQtyStr.trim().equals(oldOrderQtyStr.trim())) {

                        ilWrapper.setOrderQty(newOrderQtyStr);

                        changeFl = true;

                    }


                    if (pUpdateFromPhysCart || !newQtyOnHandStr.trim().equals(oldQtyOnHandStr.trim())) {

                        ilWrapper.setQtyOnHand(newQtyOnHandStr);

                        ilWrapper.setInitialQtyOnHand(newQtyOnHandStr);

                        changeFl = true;

                    }



                    if (Utility.mapNotEqual(Utility.getParValueMap(ilv.getParValues()), pInventory.getParValues())) {



                        if (pInventory.getParValues() != null) {

                            ilWrapper.setParValues(new InventoryLevelDetailDataVector());

                            Set periods = pInventory.getParValues().keySet();

                            for (Object oPeriod : periods) {

                                int period = (Integer) oPeriod;

                                Integer value = (Integer) pInventory.getParValues().get(oPeriod);

                                ilWrapper.setParValue(period, value);

                            }

                        }



                        ilWrapper.setParsModBy(pInventory.getModBy());

                        ilWrapper.setParsModDate(new java.util.Date(System.currentTimeMillis()));



                        changeFl = true;

                    }



                    if (changeFl) {

                        InventoryLevelDAO.updateInventoryLevelView(pCon, ilWrapper.getInventoryLevelView(), pInventory.getModBy());

                    }

                }

            }

        }

    }



    /**

     *  Description of the Method

     *

     *@param  pCon     Description of the Parameter

     *@param  pSiteId  Description of the Parameter

     */

    private void

            resetSiteInventory(Connection pCon, int pSiteId) {



        logDebug("resetSiteInventory 0");



        try {

            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID,

                    pSiteId);

            dbc.addIsNull(InventoryLevelDataAccess.QTY_ON_HAND);



            InventoryLevelDataVector ildv =

                    InventoryLevelDataAccess.select(pCon, dbc);

            if (ildv == null || ildv.size() == 0) {

                logDebug("resetSiteInventory 10");

                return;

            }

            logDebug("resetSiteInventory 20");



            for (int i1 = 0; ildv != null && i1 < ildv.size(); i1++) {

                InventoryLevelData ild = (InventoryLevelData) ildv.get(i1);

                ild.setQtyOnHand("0");

                logDebug("resetSiteInventory 30, ild=" + ild);

                InventoryLevelDataAccess.update(pCon, ild);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }



        return;

    }

    private SiteInventoryConfigViewVector
    lookupSiteInventory(Connection pCon, int pSiteId, AccCategoryToCostCenterView pCategToCostCenterView) {
    	return lookupSiteInventory(pCon, pSiteId, null, pCategToCostCenterView);
    }




    /**

     *  Description of the Method

     *

     *@param  pCon     Description of the Parameter

     *@param  pSiteId  Description of the Parameter

     *@return          Description of the Return Value

     */

    private SiteInventoryConfigViewVector

            lookupSiteInventory(Connection pCon, int pSiteId, ProductDataVector productDV, AccCategoryToCostCenterView pCategToCostCenterView) {



  SiteInventoryConfigViewVector

      invItems = new SiteInventoryConfigViewVector();



        try {

      SiteInventoryConfigViewVector v =

                    _lookupSiteInventoryConfig(pCon, pSiteId, productDV, true, pCategToCostCenterView);



            // now find out what budget period we are in

            // and set the appropriate par value



            int thisbp = mBDAO.getCurrentSiteInventoryPeriod(pCon, pSiteId);



            logDebug("lookupSiteInventory for budget period=" + thisbp);

            for (int i = 0; v != null && i < v.size(); i++) {

                SiteInventoryInfoView sii =

                        SiteInventoryInfoView.createValue();

                SiteInventoryConfigView sic =

                        (SiteInventoryConfigView) v.get(i);

                sii.setItemId(sic.getItemId());

                sii.setSiteId(pSiteId);

                sii.setItemSku(sic.getItemSku());

                sii.setItemDesc(sic.getItemDesc());

                sii.setItemUom(sic.getItemUom());

                sii.setItemPack(sic.getItemPack());

                sii.setQtyOnHand(sic.getQtyOnHand());

                sii.setOrderQty(sic.getOrderQty());

                sii.setAutoOrderItem(sic.getAutoOrderItem());

                sii.setProductData(sic.getProductData());

                sii.setParValue(Utility.getIntValueNN(sic.getParValues(),thisbp));

                sii.setSumOfAllParValues(sic.getSumOfAllParValues());


                invItems.add(sii);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return invItems;

    }





    /**

     *  Description of the Method

     *

     *@param  pSiteId              Description of the Parameter

     *@return                      Description of the Return Value

     *@exception  RemoteException  Description of the Exception

     */

    public SiteInventoryConfigViewVector

            lookupInventoryConfig(int pSiteId)

             throws RemoteException {

    	return lookupInventoryConfig(pSiteId, false);

    }

    /**

     * @param inCatalog - if true, returns only items assigned to catalog.

     */
    public SiteInventoryConfigViewVector
        lookupInventoryConfig(int pSiteId, boolean inCatalog)   throws RemoteException {
       return lookupInventoryConfig( pSiteId, inCatalog, null);
    }

    public SiteInventoryConfigViewVector

    	lookupInventoryConfig(int pSiteId, boolean inCatalog, AccCategoryToCostCenterView pCategToCostCenterView)

     throws RemoteException {

		Connection con = null;

		try {

		    con = getConnection();

		    //logDebug("lookupInventoryConfig: pSiteId=" + pSiteId);

		    return _lookupSiteInventoryConfig(con, pSiteId, inCatalog, pCategToCostCenterView);

		} catch (Exception e) {

		    logError(e.getMessage());

		    e.printStackTrace();

		} finally {

		    closeConnection(con);

		}

		return null;

	}





    /**

     *  Description of the Method

     *

     *@param  pCon     Description of the Parameter

     *@param  pSiteId  Description of the Parameter

     *@return          Description of the Return Value

     */

    private SiteInventoryConfigViewVector

            _lookupSiteInventoryConfig(Connection pCon, int pSiteId) {

        return _lookupSiteInventoryConfig(pCon, pSiteId, false, null);

    }

    private SiteInventoryConfigViewVector _lookupSiteInventoryConfig(Connection pCon, int pSiteId, boolean pInCatalog) {
      return _lookupSiteInventoryConfig(pCon, pSiteId, pInCatalog, null);
    }

    private SiteInventoryConfigViewVector _lookupSiteInventoryConfig(Connection pCon, int pSiteId, boolean pInCatalog, AccCategoryToCostCenterView pCategToCostCenterView ) {
    	return _lookupSiteInventoryConfig(pCon, pSiteId, null, pInCatalog, pCategToCostCenterView );
    }

    /**

     * @param pCon       - connection.

     * @param pSiteId    - site identifier.

     * @param pInCatalog - if true, returns only items assigned to catalog.

     * @return SiteInventoryConfigViewVector

     */

    private SiteInventoryConfigViewVector _lookupSiteInventoryConfig(Connection pCon, int pSiteId, ProductDataVector productDV, boolean pInCatalog, AccCategoryToCostCenterView pCategToCostCenterView ) {



        logInfo("_lookupSiteInventoryConfig => Start. pSiteId:"+pSiteId+", pInCatalog:"+pInCatalog);



        SiteInventoryConfigViewVector invItems = new SiteInventoryConfigViewVector();



        try {



            BusEntityData site = BusEntityDataAccess.select(pCon, pSiteId);
            int accountId = BusEntityDAO.getAccountForSite(pCon, site.getBusEntityId());
            int catid = lookupSiteCatalogId(pCon, pSiteId);
            Map<Integer, ProductData> productMap = new HashMap();
            IdVector itemIds = new IdVector();
            if (productDV != null){            	
            	for (int i = 0; i < productDV.size(); i++){
            		ProductData productD = (ProductData)productDV.get(i);
            		productMap.put(productD.getProductId(), productD);
            		itemIds.add(productD.getProductId());
            	}
            }



            InventoryItemsDataVector availableItems;

            if (pInCatalog) {

                availableItems = lookupAvailableSiteInventoryItemsInCatalog(pCon, accountId, catid, itemIds);

            } else {

                availableItems = lookupAvailableSiteInventoryItems(pCon, accountId, itemIds);

            }



            IdVector items = new IdVector();

            for (int i = 0; availableItems != null && i < availableItems.size(); i++) {

                InventoryItemsData iid = (InventoryItemsData) availableItems.get(i);

                int thisItemId = iid.getItemId();
                if (productDV != null && productMap.get(thisItemId) == null)// skip if inventory item not in the product list
                	continue;

                SiteInventoryConfigView siiv = SiteInventoryConfigView.createValue();

                siiv.setItemId(thisItemId);

                siiv.setSiteId(pSiteId);

                siiv.setAutoOrderItem(iid.getEnableAutoOrder());

                invItems.add(siiv);

                items.add(thisItemId);

            }

            if (invItems.isEmpty())
            	return invItems;

            FiscalCalenderView fiscalCalendar = new BusEntityDAO().getCurrentFiscalCalenderV(pCon, accountId);



            int storeId = BusEntityDAO.getStoreForAccount(pCon, accountId);

            String storeType = BusEntityDAO.getStoreTypeCd(pCon, storeId);



            InventoryLevelSearchCriteria ilSearchCriteria = new InventoryLevelSearchCriteria();

            ilSearchCriteria.setSiteIds(Utility.toIdVector(pSiteId));

            ilSearchCriteria.setNumPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(fiscalCalendar));
            
            if (productDV != null){
            	ilSearchCriteria.setItems(itemIds);
            }

            InventoryLevelViewVector ilvv = InventoryLevelDAO.getInvLevelViewCollections(pCon, ilSearchCriteria);


            IdVector invLevelItems = new IdVector();

            for (Object oilv : ilvv) {

                InventoryLevelView ilv = (InventoryLevelView) oilv;

                invLevelItems.add(ilv.getInventoryLevelData().getItemId());

            }            
            
            ProductDataVector pdv = new ProductDataVector();
            if (productDV == null){
	            ProductDAO pdao = new ProductDAO(pCon, items);	
	            if (catid > 0) {	
	                pdao.updateCatalogInfo(pCon, catid, pSiteId, pCategToCostCenterView);	
	            } else {	
	                logError("No catalog available for pSiteId=" + pSiteId);	
	            }
	            pdv = pdao.getResultVector();
            }else{
            	for (int i = 0; i < items.size(); i++){
            		Integer itemId = (Integer) items.get(i);
            		pdv.add(productMap.get(itemId));
            	}
            }            

            for (int j = 0; pdv != null && j < pdv.size(); j++) {

                ProductData pd = (ProductData) pdv.get(j);

                for (Object oInvItem : invItems) {

                    SiteInventoryConfigView inv = (SiteInventoryConfigView) oInvItem;

                    if (inv.getItemId() == pd.getItemData().getItemId()) {

                        inv.setItemSku(pd.getSkuNum());

                        inv.setActualSku(getActualSkuNumber(pd, storeType));

                        inv.setItemDesc(pd.getShortDesc());

                        inv.setItemUom(pd.getUom());

                        inv.setItemPack(pd.getPack());

                        inv.setProductData(pd);

                    }

                    //logDebug( "=== inv item=" + inv);

                }

            }



            //Add to inv level when new site is created

            if (ilvv == null || ilvv.isEmpty()) {

                for (Object oInvItem : invItems) {



                    SiteInventoryConfigView inv = (SiteInventoryConfigView) oInvItem;



                    InventoryLevelView newilv = new InventoryLevelView(InventoryLevelData.createValue(), new InventoryLevelDetailDataVector());

                    InventoryLevelViewWrapper ilWrapper = new InventoryLevelViewWrapper(newilv);



                    ilWrapper.setBusEntityId(inv.getSiteId());

                    ilWrapper.setOrderQty(inv.getOrderQty());

                    ilWrapper.setQtyOnHand(inv.getQtyOnHand());

                    ilWrapper.setItemId(inv.getItemId());



                    if (inv.getParValues() != null) {

                        Set periods = inv.getParValues().keySet();

                        for (Object oPeriod : periods) {

                            int period = (Integer) oPeriod;

                            Integer value = (Integer) inv.getParValues().get(oPeriod);

                            ilWrapper.setParValue(period, value);

                        }

                    }



                    ilWrapper.setParsModBy(inv.getModBy());

                    ilWrapper.setParsModDate(new Date(System.currentTimeMillis()));



                    InventoryLevelView ilView = InventoryLevelDAO.updateInventoryLevelView(pCon, ilWrapper.getInventoryLevelView(), inv.getModBy());

                    ilvv.add(ilView);

                }

            } else {



                //check for newly added items

                for (Object oInvItem : invItems) {

                    SiteInventoryConfigView inv = (SiteInventoryConfigView) oInvItem;

                    if (!invLevelItems.contains(new Integer(inv.getItemId()))) {



                        InventoryLevelView newilv = new InventoryLevelView(InventoryLevelData.createValue(), new InventoryLevelDetailDataVector());

                        InventoryLevelViewWrapper ilWrapper = new InventoryLevelViewWrapper(newilv);



                        ilWrapper.setBusEntityId(inv.getSiteId());

                        ilWrapper.setOrderQty(inv.getOrderQty());

                        ilWrapper.setQtyOnHand(inv.getQtyOnHand());

                        ilWrapper.setItemId(inv.getItemId());



                        if (inv.getParValues() != null) {

                            Set periods = inv.getParValues().keySet();

                            for (Object oPeriod : periods) {

                                int period = (Integer) oPeriod;

                                Integer value = (Integer) inv.getParValues().get(oPeriod);

                                ilWrapper.setParValue(period, value);

                            }

                        }



                        ilWrapper.setParsModBy(inv.getModBy());

                        ilWrapper.setParsModDate(new Date(System.currentTimeMillis()));



                        InventoryLevelView ilView = InventoryLevelDAO.updateInventoryLevelView(pCon, ilWrapper.getInventoryLevelView(), inv.getModBy());

                        ilvv.add(ilView);



                    }

                }

            }



            for (Object oInvItem : invItems) {



                SiteInventoryConfigView inv = (SiteInventoryConfigView) oInvItem;

                logInfo("_lookupSiteInventoryConfig => SiteInventoryConfigView Item Id " + inv.getItemId());



                for (Object oilv : ilvv) {



                    InventoryLevelView ilv = (InventoryLevelView) oilv;



                    if (inv.getItemId() == ilv.getInventoryLevelData().getItemId()) {



                        logInfo("_lookupSiteInventoryConfig => inv.getItemId() == ilv.getItemId() ");



                        inv.setParValues(Utility.getParValueMap(ilv.getParValues()));

                        inv.setQtyOnHand(ilv.getInventoryLevelData().getQtyOnHand());

                        inv.setOrderQty(ilv.getInventoryLevelData().getOrderQty());



                        int sumOfPars = 0;

                        for (Object oVal : inv.getParValues().values()) {

                            sumOfPars += Utility.intNN((Integer) oVal);

                        }



                        inv.setSumOfAllParValues(sumOfPars);

                        inv.setModBy(ilv.getInventoryLevelData().getParsModBy());

                        inv.setModDate(ilv.getInventoryLevelData().getParsModDate());



                        logInfo("_lookupSiteInventoryConfig => Quantity " + inv.getOrderQty());

                        logInfo("_lookupSiteInventoryConfig => OnHandQuantity " + inv.getQtyOnHand());

                        logInfo("_lookupSiteInventoryConfig => ParValue " + inv.getParValues());



                    }

                }

            }



            // Sort the inventory items by SKU.

            invItems.sort("ItemSku");



        } catch (Exception e) {

            e.printStackTrace();

        }



        //logDebug("_lookupSiteInventoryConfig: invItems.size=" + invItems.size());

        logInfo("lookupSiteInventoryConfig => Done");



        return invItems;



    }





    public ShoppingControlDataVector getSiteShoppingControls(int pSiteId, int pAccountId)

    throws RemoteException {



        Connection conn = null;

        try {



          conn = getConnection();



          return lookupSiteShoppingControls(conn, pSiteId, pAccountId);

        }

        catch (Exception e) {

          e.printStackTrace();

          logDebug("updateShoppingControls, error " + e);

        }

        finally {

          closeConnection(conn);

        }

        return null;





    }



    public ShoppingControlDataVector getSiteShoppingControlsForItem(IdVector pSites, int pItemId)

    throws RemoteException{

    	 Connection conn = null;

         try {

           conn = getConnection();

           DBCriteria crit = new DBCriteria();

           //crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, 0);

           crit.addOneOf(ShoppingControlDataAccess.SITE_ID, pSites);

           crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, pItemId);



           ShoppingControlDataVector scDV = ShoppingControlDataAccess.select(conn, crit);

           if(scDV!=null && scDV.size()>0){

        	   return scDV;

           }

         }

         catch (Exception e) {

           e.printStackTrace();

           logDebug("getSiteShoppingControlsForItem, error " + e);

         }

         finally {

           closeConnection(conn);

         }

         return null;

    }



    public Map<Integer, Set<Integer>> getSitesForItems(IdVector pItemIds,

            IdVector pSiteIds) throws RemoteException {

        Connection conn = null;

        Map<Integer, Set<Integer>> result = new TreeMap<Integer, Set<Integer>>();

        if (pSiteIds != null && pSiteIds.size() > 0 && pItemIds != null

                && pItemIds.size() > 0) {

            try {

                DBCriteria cr = new DBCriteria();

                cr.addOneOf("t1.bus_entity_id", pSiteIds);

                cr.addOneOf("t2.item_id", pItemIds);

                conn = getConnection();

                String sql = "SELECT DISTINCT\n";

                sql += "t1.BUS_ENTITY_ID, t2.ITEM_ID\n";

                sql += "FROM CLW_CATALOG_ASSOC t1\n";

                sql += "INNER JOIN CLW_CATALOG_STRUCTURE t2\n";

                sql += "ON t1.catalog_id = t2.catalog_id AND t2.catalog_structure_cd = '"

                        + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT

                        + "'\n";

                sql += "WHERE t1.catalog_assoc_cd = '"

                        + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE + "' AND "

                        + cr.getWhereClause();

                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {

                    int siteId = resultSet.getInt(1);

                    int itemId = resultSet.getInt(2);

                    Set<Integer> sites = result.get(itemId);

                    if (sites == null) {

                        sites = new TreeSet<Integer>();

                        result.put(itemId, sites);

                    }

                    sites.add(siteId);

                }

                resultSet.close();

                statement.close();

            } catch (Exception e) {

                e.printStackTrace();

                processException(e);

            } finally {

                closeConnection(conn);

            }

        }

        return result;

    }



    /*

     * Returns Acct Ctrls when siteId=0

     * Returns Site Ctrls otherwise

     * This method inserts a new record for site ctrl

     *  for an item with only acct ctrl defined

     */

    private ShoppingControlDataVector lookupSiteShoppingControls

  (Connection pCon, int pSiteId, int pAccountId) {

    	if (pSiteId == 0 && pAccountId == 0)
    		return null;

  try {



      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, pAccountId);

      if(pSiteId != 0){

		  dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID, 0);

	  }

      dbc.addOrderBy(ShoppingControlDataAccess.ITEM_ID);

      ShoppingControlDataVector actscdv =

    ShoppingControlDataAccess.select(pCon,dbc);

      if(pSiteId == 0){

    	  return actscdv;

      }



      if ( null == actscdv ) actscdv = new ShoppingControlDataVector();

      logDebug ("lookupSiteShoppingControls actscdv.size=" +

          actscdv.size());



      dbc = new DBCriteria();

      dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID, pSiteId);

      dbc.addOrderBy(ShoppingControlDataAccess.ITEM_ID);

      ShoppingControlDataVector sitescdv =

    ShoppingControlDataAccess.select(pCon, dbc);

      if ( null == sitescdv ) sitescdv = new ShoppingControlDataVector();

      logDebug ("lookupSiteShoppingControls sitescdv.size=" +

          sitescdv.size());



      if ( sitescdv.size() == 0 ) {

    // There are no site specific controls.

    // Return the account controls found.

    logDebug ("lookupSiteShoppingControls sitescdv.size=" +

        sitescdv.size() +

        " return account controls"

        );

    //return actscdv;

    //return null;

      }



      for (int i = 0; i < actscdv.size(); i++) {

    	  boolean foundThisOne = false;

    	  ShoppingControlData actscd = (ShoppingControlData) actscdv.get(i);

    	  for (int j = 0; j < sitescdv.size(); j++) {

    		  ShoppingControlData sitescd = (ShoppingControlData) sitescdv.get(j);



    		  if (sitescd.getItemId() == actscd.getItemId()) {

    			  // This item control is defined at the site.

    			  foundThisOne = true;

    			  logDebug(" sitescd=" + sitescd + " actscd=" + actscd);

    			  break;

    		  }



    	  }



    	  if (foundThisOne == false) {

    		  // This item control is only defined at the account
    		  // create a cache record for the site in memory 
    		  // add it to the site control list;

    		  ShoppingControlData scd = (ShoppingControlData) actscd.clone(); // create a cache record in memory

    		  scd.setSiteId(pSiteId);
    		  
    	      //ShoppingControlDataAccess.insert(pCon, scd);

    		  sitescdv.add(scd);



    		  logDebug(" adding actscd=" + actscd );

    	  }

      }



      logDebug ("lookupSiteShoppingControls return sitescdv.size=" +

          sitescdv.size() );



      return sitescdv;



  }

  catch (Exception e) {

      e.printStackTrace();

      logDebug("lookupSiteShoppingControls, error " + e);

  }



  return null;

    }

    /*

     * Returns Acct Ctrls when siteId=0

     * Returns Site Ctrls otherwise

     */

    public ShoppingControlDataVector lookupSiteShoppingControls2(int pSiteId, int pAccountId)
    
    throws RemoteException {


    Connection pCon = null;

    try {

	  pCon = getConnection();

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, pAccountId);

      if(pSiteId != 0){

		  dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID, 0);

	  }

      dbc.addOrderBy(ShoppingControlDataAccess.ITEM_ID);

      ShoppingControlDataVector actscdv =

      ShoppingControlDataAccess.select(pCon,dbc);
      
      // account Shopping Control record(s) is/are pulled out of the database: one or two records

      if(pSiteId == 0){

    	  return actscdv;

      }



      if ( null == actscdv ) actscdv = new ShoppingControlDataVector();

          logInfo ("lookupSiteShoppingControls2 actscdv.size=" +

          actscdv.size());



      dbc = new DBCriteria();

      dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID, pSiteId);

      dbc.addOrderBy(ShoppingControlDataAccess.ITEM_ID);

      ShoppingControlDataVector sitescdv =

      ShoppingControlDataAccess.select(pCon, dbc);

      if ( null == sitescdv ) sitescdv = new ShoppingControlDataVector();

      logInfo ("lookupSiteShoppingControls2 sitescdv.size=" +

          sitescdv.size());



      if ( sitescdv.size() == 0 ) {

         // There are no site specific controls.

         // Return the account controls found.
    	  
         logInfo("lookupSiteShoppingControls2 sitescdv.size=" +

         sitescdv.size() +

         " return account controls"

         );

         //return actscdv;

         //return null;

      }



      for (int i = 0; i < actscdv.size(); i++) {

    	  boolean foundThisOne = false;

    	  ShoppingControlData actscd = (ShoppingControlData) actscdv.get(i);

    	  for (int j = 0; j < sitescdv.size(); j++) {

    		  ShoppingControlData sitescd = (ShoppingControlData) sitescdv.get(j);



    		  if (sitescd.getItemId() == actscd.getItemId()) {

    			  // This item control is defined at the site level.

    			  foundThisOne = true;

    			  break;

    		  }



    	  }



    	  if (foundThisOne == false) {

    		  // This item control is only defined at the account level

    		  // add it to the site control list;

    		  //sitescdv.add(actscd);

    		  //create new record...add to list



    		  ShoppingControlData scd = actscd;

    		  scd.setSiteId(pSiteId);
    	      
    		  sitescdv.add(scd);

    	  }

      }

      logInfo ("lookupSiteShoppingControls2 return sitescdv.size=" +

          sitescdv.size() );



      return sitescdv;



  }

  catch (Exception e) {

      e.printStackTrace();

      logDebug("lookupSiteShoppingControls2, error " + e);


  } finally {

        closeConnection(pCon);

  }


  return null;

    }

    /*

     * Returns only Site Ctrls for the
     * 
     * account and site, when both Control records for one item
     * 
     * exist in the Database (does not return orphans => records from the clw_shopping_control table
     * 
     * where site controls exist and account controls for the same item do not
     *
     */
    public ShoppingControlDataVector lookupSiteShoppingControlsNew(int pSiteId, int pAccountId)
    
    throws RemoteException {


    Connection pCon = null;
    
    ShoppingControlDataVector sitescdv = new ShoppingControlDataVector();

    try {

	  pCon = getConnection();
	  
	  /***
	  select shopping_control_id
	  from clw_shopping_control sc1
	  where sc1.site_id = 564323
	  and exists 
	  (select *
	  from clw_shopping_control sc2
	  where sc1.item_id = sc2.item_id
	  and sc2.account_id = 564322
	  and sc2.site_id = 0
	  )
	  ***/

      String sql = "select shopping_control_id" +

                   " from clw_shopping_control sc1" +

                   " where sc1.site_id = ?" +

                   " and exists" +
                   
                   " (select * from clw_shopping_control sc2" +
                   
                   " where sc1.item_id = sc2.item_id" +
                   
                   " and sc2.account_id = ?" +
                   
             	   " and sc2.site_id = 0)";
      
      //logInfo("sql = " + sql);
                   
      PreparedStatement prepStatement = pCon.prepareStatement(sql);

      prepStatement.setInt(1, pSiteId);

      prepStatement.setInt(2, pAccountId);

      ResultSet resultSet = prepStatement.executeQuery();
      
      IdVector ctrlIds = new IdVector();

      while (resultSet.next()) {      

          ctrlIds.add(resultSet.getInt(1));

      }           
      
      if (ctrlIds.size() != 0) {
    	  
         sitescdv =

              ShoppingControlDataAccess.select(pCon, ctrlIds);
        		  
      }
	  
    } catch (Exception e) {

    		e.printStackTrace();

    		logDebug("lookupSiteShoppingControlsNew, error " + e);
    
    } finally {

        closeConnection(pCon);
    }
    
    return sitescdv;
    
    }
    
    /*
     * Returns only Site Ctrls for the account and site
     *
     */
    public ShoppingControlDataVector lookupSiteShoppingControlsAcctAdmPortal(int pSiteId, int pAccountId)    
    throws RemoteException {
    	IdVector siteIds = new IdVector();
    	siteIds.add(pSiteId);
        return lookupSiteShoppingControlsAcctAdmPortal(siteIds, null);    
    }
    
    /*
     * Returns only Site Ctrls for the account, site. 
     * Filter the item with item id list if itemIds is not empty
     *
     */
    public ShoppingControlDataVector lookupSiteShoppingControlsAcctAdmPortal(IdVector siteIds, IdVector itemIds)    
    throws RemoteException {
    	Connection pCon = null;    
    	ShoppingControlDataVector sitescdv = new ShoppingControlDataVector();

    	try {
    		pCon = getConnection();	  
    		DBCriteria dbc = new DBCriteria();
    		//dbc.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, 0);
    		dbc.addOneOf(ShoppingControlDataAccess.SITE_ID, siteIds);
    		if (itemIds != null)
    			dbc.addOneOf(ShoppingControlDataAccess.ITEM_ID, itemIds);      
    		dbc.addOrderBy(ShoppingControlDataAccess.ITEM_ID);
    		sitescdv = ShoppingControlDataAccess.select(pCon,dbc);
    	} catch (Exception e) {
    		e.printStackTrace();
    		logDebug("lookupSiteShoppingControlsAcctAdmPortal, error " + e);    
    	} finally {
    		closeConnection(pCon);
    	}    
    	return sitescdv;
    }
    
    /*
     * Returns Site Ctrls for the giving site id 
     */
    public ShoppingControlDataVector lookupSiteShoppingControlsByItemId(int accountId, int itemId)
	throws RemoteException{
    	Connection pCon = null;    
    	ShoppingControlDataVector sitescdv = new ShoppingControlDataVector();

    	try {
    		pCon = getConnection();
    		DBCriteria crit = new DBCriteria();
            String scTable = ShoppingControlDataAccess.CLW_SHOPPING_CONTROL;
            String beaTab = BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC;
            String caTab = CatalogAssocDataAccess.CLW_CATALOG_ASSOC;
            String csTab = CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE;
            crit.addJoinTableEqualTo(scTable,ShoppingControlDataAccess.ACCOUNT_ID, 0);
            crit.addJoinTableEqualTo(scTable,ShoppingControlDataAccess.ITEM_ID, itemId);
            crit.addJoinCondition(scTable,ShoppingControlDataAccess.SITE_ID, beaTab, BusEntityAssocDataAccess.BUS_ENTITY1_ID);
            crit.addJoinTableEqualTo(beaTab,BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,"SITE OF ACCOUNT");
            crit.addJoinTableEqualTo(beaTab,BusEntityAssocDataAccess.BUS_ENTITY2_ID,accountId);
            crit.addJoinCondition(caTab,CatalogAssocDataAccess.CATALOG_ID,csTab, CatalogStructureDataAccess.CATALOG_ID);
            crit.addJoinTableEqualTo(caTab,CatalogAssocDataAccess.CATALOG_ASSOC_CD,"CATALOG_SITE");
            crit.addJoinTableEqualTo(csTab,CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,"CATALOG_PRODUCT");
            crit.addJoinCondition(scTable,ShoppingControlDataAccess.SITE_ID,caTab, CatalogAssocDataAccess.BUS_ENTITY_ID);
            crit.addJoinCondition(scTable,ShoppingControlDataAccess.ITEM_ID,csTab, CatalogStructureDataAccess.ITEM_ID);
            crit.addOrderBy(scTable+"." + ShoppingControlDataAccess.SITE_ID);
            
            JoinDataAccess.selectTableInto(new ShoppingControlDataAccess(),sitescdv,pCon,crit,0);
            
    	} catch (Exception e) {
    		e.printStackTrace();
    		logDebug("lookupSiteShoppingControlsAcctAdmPortal, error " + e);    
    	} finally {
    		closeConnection(pCon);
    	}    
    	return sitescdv;

    }
    
    
    /**

     *  Gets the CatalogSiteids attribute of the SiteBean object

     *

     *@param  pCatalogId     Description of Parameter

     *@param  pIncludeSites  Description of Parameter

     *@param  pConn          Description of the Parameter

     *@return                The CatalogSiteids value

     *@exception  Exception  Description of Exception

     */

    private IdVector fetchShoppingCatalogSiteids(int pCatalogId,

            IdVector pIncludeSites,

            Connection pConn)

             throws Exception {



        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

        crit.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, pIncludeSites);



        return CatalogAssocDataAccess.selectIdOnly(pConn,

                CatalogAssocDataAccess.BUS_ENTITY_ID,

                crit);

    }





    /**

     *  Description of the Method

     *

     *@param  pCatalogId     Description of Parameter

     *@param  pIncludeSites  Description of Parameter

     *@param  pConn          Description of the Parameter

     *@return                Description of the Returned Value

     *@exception  Exception  Description of Exception

     */

    private IdVector fetchAccountCatalogSiteids(int pCatalogId,

            IdVector pIncludeSites,

            Connection pConn)

             throws Exception {



        IdVector siteids = new IdVector();



        // Get all sites tied to this catalog

        // through the account they belong to.

        Statement stmt = pConn.createStatement();

        String query = "SELECT distinct " +

                BusEntityAssocDataAccess.BUS_ENTITY1_ID + " FROM " +

                BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +

                " where " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +

                " in ( SELECT distinct " +

                CatalogAssocDataAccess.BUS_ENTITY_ID + " from " +

                CatalogAssocDataAccess.CLW_CATALOG_ASSOC + " where " +

                CatalogAssocDataAccess.CATALOG_ID + " = " +

                pCatalogId + " and " +

                CatalogAssocDataAccess.CATALOG_ASSOC_CD + " = \'" +

                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT + "\'";



        // Limit the sites for the user specified.

        query += " and " + BusEntityAssocDataAccess.BUS_ENTITY1_ID + " in ( ";

        query += pIncludeSites.get(0).toString();



        int vecsize = pIncludeSites.size();



        for (int idx = 1; idx < vecsize; idx++) {

            query += "," + pIncludeSites.get(idx).toString();

        }



        // This next bit is confusing.  Hopefully we

        // can rethink our default catalog to site

        // relationships.  The idea is to find

        // those sites belonging to this user and

        // account which do not have their own site

        // catalogs and do not point to the account

        // catalog.

        query += " ) and " + BusEntityAssocDataAccess.BUS_ENTITY1_ID + " not in ( SELECT distinct " + CatalogAssocDataAccess.BUS_ENTITY_ID + " from " + CatalogAssocDataAccess.CLW_CATALOG_ASSOC + " where " + CatalogAssocDataAccess.BUS_ENTITY_ID + " = " + BusEntityAssocDataAccess.BUS_ENTITY1_ID + " and " + CatalogAssocDataAccess.CATALOG_ID + " != " + pCatalogId + " and " + CatalogAssocDataAccess.CATALOG_ASSOC_CD + " = \'" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE + "\' )";

        query += " )";



        ResultSet rs = stmt.executeQuery(query);



        while (rs.next()) {



            int id = rs.getInt(1);

            siteids.add(new Integer(id));

        }



        return siteids;

    }





    /**

     *  Description of the Method

     *

     *@param  pWorkflowId          Description of the Parameter

     *@return                      Description of the Return Value

     *@exception  RemoteException  Description of the Exception

     */
    public SiteDataVector fetchSitesForWorkflow(int pWorkflowId)
             throws RemoteException {
          return fetchSitesForWorkflow(pWorkflowId, null);
    }

    public SiteDataVector fetchSitesForWorkflow(int pWorkflowId, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {



        SiteDataVector siteVec = new SiteDataVector();

        Connection conn = null;



        try {

            conn = getConnection();



            IdVector siteids = new IdVector();

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(SiteWorkflowDataAccess.WORKFLOW_ID, pWorkflowId);

            siteids = SiteWorkflowDataAccess.selectIdOnly(conn,

                    SiteWorkflowDataAccess.SITE_ID,

                    crit);



            if (siteids.size() <= 0) {



                // no sites configured for this workflow.

                return siteVec;

            }



            if (siteids.size() > 1000) {



                IdVector small = new IdVector();



                for (int i = 0; i < 1000; i++) {

                    small.add(siteids.get(i));

                }



                siteids = small;

            }



            BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn,

                    siteids);



            //reverse the vector so its in order by id

            for (int i = busEntityVec.size() - 1; i >= 0; i--) {

                siteVec.add(getSiteDetails((BusEntityData) busEntityVec.get(i), pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw new RemoteException("Site.fetchSitesForWorkflow: " +

                    e.getMessage());

        } finally {



            try {



                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

                ex.printStackTrace();

            }

        }



        return siteVec;

    }

























    /**

     *  Gets the allBudgetSpent2 attribute of the SiteBean object

     *

     *@param  pSiteId              Description of the Parameter

     *@return                      The allBudgetSpent2 value

     *@exception  RemoteException  Description of the Exception

     */

    public BudgetSpendViewVector getAllBudgetSpent2(int pSiteId)

             throws RemoteException {

        Connection conn = null;



        try {

            conn = getConnection();

            BudgetUtil bu = new BudgetUtil(conn);

            return bu.getAllBudgetSpentForSite(pSiteId);

        } catch (Exception e) {

            e.printStackTrace();

            logError("getAllBudgetSpent2: " + e);

        } finally {

            closeConnection(conn);

        }

        return null;

    }











    /**

     *  Gets the budgetYtd attribute of the SiteBean object

     *

     *@param  pSiteId              Description of the Parameter

     *@param  pBudgetData          Description of the Parameter

     *@return                      The budgetYtd value

     *@exception  RemoteException  Description of the Exception

     */

    public BudgetSpendView getBudgetSpendView(int pSiteId) throws RemoteException {

        logDebug("YTD budget is: pSiteId=" + pSiteId);

        return getBudgetSpent(pSiteId, 0);

    }





    /**
     *Calculates the budget spent for the supplied site.  This depends greatly
     *upon the account configuration and the budget configuration.  If the budget is
     *setup at the account level then this is taken into account for example.  The
     *design is that the caller does not need to know this, but in the context of the
     *current site this will return the amount spent against the budget.
     *@param  pSiteId              the site id to operate off of
     *@param  pCostCenterId        optional cost center id
     *@return                      The budgetSpent2 value
     *@exception  RemoteException  Description of the Exception
     */

    public BudgetSpendView getBudgetSpent(int pSiteId, int pCostCenterId)
             throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BudgetUtil bu = new BudgetUtil(conn);
            return bu.getBudgetSpentForSite(pSiteId, pCostCenterId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public CostCenterDataVector getCostCentersForSite(int pSiteId) throws Exception, RemoteException{
    	Connection conn = null;
    	try{
    		conn = getConnection();
    		BudgetUtil budgetUtil = new BudgetUtil(conn);
    		return budgetUtil.getCostCentersForSite(pSiteId);
    		
    	} catch (Exception e) {
 		   throw processException(e);
 		} finally {
 		   closeConnection(conn);
 		}
    }
    
    public HashMap getCostCentersForSitesList(ArrayList siteIds) throws Exception, RemoteException{
    	
    	HashMap ccMap = new HashMap();
    	
    	for( int i=0; i<siteIds.size(); i++){
    		int siteId = ((Integer) siteIds.get(i)).intValue();
    		CostCenterDataVector ccDV  = getCostCentersForSite(siteId);
    		if(ccDV!=null){
    			ccMap.put(siteId, ccDV);
    		}
    	}
    	
    	return ccMap;
    	
    }
    
    public ArrayList getCostCentersForSites(ArrayList siteIds) throws Exception, RemoteException{
    	ArrayList ccIds = new ArrayList();
    	
    	for( int i=0; i<siteIds.size(); i++){
    		int siteId = ((Integer) siteIds.get(i)).intValue();
    		CostCenterDataVector ccDV  = getCostCentersForSite(siteId);
    		
    		for(int j=0; j<ccDV.size(); j++){
    			CostCenterData ccD = (CostCenterData)ccDV.get(j);
    			if(!ccIds.contains(ccD.getCostCenterId())){
    				ccIds.add(ccD.getCostCenterId());
    			}
    		}
    	}
    	return ccIds;
    }
    
    
    public BudgetSpendView getBudgetSpent(int pSiteId, CostCenterData pCostCenter, int pPeriod, int pYear)
    throws RemoteException {
		Connection conn = null;
		BudgetSpendView budget = BudgetSpendView.createValue();
		
		try {
		   conn = getConnection();
		   BudgetUtil budgetUtil = new BudgetUtil(conn);
		   PropertyUtil propUtil= new PropertyUtil(conn);
		   
		   int pAccountId = BusEntityDAO.getAccountForSite(conn,pSiteId);
	    	
		   String pBudgetAccrualTypeCd = propUtil.fetchValueIgnoreMissing(0,pAccountId,RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD);
	    
		   if (pCostCenter == null) {
		        budget.setAllocateFreight(false);
		        budget.setCostCenterName("NONE");
		    } else {
		        budget.setAllocateFreight(Utility.isTrue(pCostCenter.getAllocateFreight()));
		        budget.setCostCenterTaxType(pCostCenter.getCostCenterTaxType());
		        budget.setCostCenterName(pCostCenter.getShortDesc());
		        budget.setCostCenterId(pCostCenter.getCostCenterId());
		    }
		
		    if (budget.getCostCenterTaxType() == null) {
		        budget.setCostCenterTaxType(RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX);
		    }
		    
		    budget.setBusEntityId(pSiteId);
		    budget.setCostCenterId(pCostCenter.getCostCenterId());
	        
		    return budgetUtil.getBudgetSpendView(pSiteId, pAccountId, pCostCenter.getCostCenterId(), 
		    		budget, pPeriod, pYear, pBudgetAccrualTypeCd);

		   
		} catch (Exception e) {
		   throw processException(e);
		} finally {
		   closeConnection(conn);
		}
		
    }


    /**

     *  <code>getSiteCollection</code> returns a vector of SiteView's meeting

     *  the criteria of the QueryRequest

     *

     *@param  req                  a <code>QueryRequest</code> filtering on any

     *      of: SITE_NAME, SITE_ID, ACCOUNT_NAME, ACCOUNT_ID, CITY, STATE, ZIP

     *@return                      a <code>SiteViewVector</code> value

     *@exception  RemoteException  if an error occurs

     */

	public SiteViewVector getSiteCollection(QueryRequest req) throws RemoteException, SQLException {
		SiteViewVector siteVec = new SiteViewVector();
		Connection conn = null;
		try {
			conn = getConnection();
			siteVec = BusEntityDAO.getSiteCollection(conn, req);
		} catch (SQLException e) {
			throw new SQLException(e);
		} catch (Exception e) {
			throw processException(e);
		} finally {
			closeConnection(conn);
		}
		return siteVec;
	}


    /**

     *  <code>getSiteCollection</code> returns a vector of SiteView's for the user

     *  and site filter

     *

     *@param  pUserId user id

     *@param  pSiteId site id (active if > 0)

     *@param  pNameTempl  site name (active if the parameter has some value)

     *@param  pNameBeginsFl affects search

     *  If it is true site name should start with pNameTeml

     *  It it is false site name should contain pNameTempl

     *@param  pCityTempl city name (active if it has some vale).

     * City of the sites should start wite pCityTempl

     *@param pState site state (active if it has some value)

     *@param pAccountIds list of accout id (active if it is not null)

     *@param pGetIncativeFl ignores inactive sites if true;

     *@param pResultLimit limits number of sites (if >0)

     *@return      a <code>SiteViewVector</code> value

     *@exception  RemoteException  if an error occurs

     */

    public SiteViewVector getUserSites(int pStoreId,int pUserId, int pSiteId, String pNameTempl,

                           boolean pNameBeginsFl, String pRefNum, boolean pRefNumNameBeginsF1,

                           String pCityTempl, String pState,

                           IdVector pAccountIds, boolean pGetIncativeFl, int pResultLimit)

        throws RemoteException {

        SiteViewVector siteVec = new SiteViewVector();

        IdVector storeIdV = new IdVector();

        Connection conn = null;

        try {

           conn = getConnection();

           QueryRequest req = new QueryRequest();

           /*if(Utility.isSet(pNameTempl)) {

             if(pNameBeginsFl) {

               req.filterBySiteName(pNameTempl,QueryRequest.BEGINS_IGNORE_CASE);

             } else {

               req.filterBySiteName(pNameTempl,QueryRequest.CONTAINS_IGNORE_CASE);

             }

           }*/

           if(Utility.isSet(pNameTempl)) {

        	   if(pNameBeginsFl) {

        		   req.filterByOnlySiteName(pNameTempl,QueryRequest.BEGINS_IGNORE_CASE);

        	   } else {

        		   req.filterByOnlySiteName(pNameTempl,QueryRequest.CONTAINS_IGNORE_CASE);

        	   }

           }

           if(Utility.isSet(pRefNum)) {

        	   if(pRefNumNameBeginsF1) {

        		   req.filterByRefNum(pRefNum,QueryRequest.BEGINS_IGNORE_CASE);

        	   } else {

        		   req.filterByRefNum(pRefNum,QueryRequest.CONTAINS_IGNORE_CASE);

        	   }

           }

            if(pStoreId>0)

            {

              storeIdV.add(new Integer(pStoreId));

            }

           if(pSiteId>0) {

             req.filterBySiteId(pSiteId);

           }

           if(Utility.isSet(pCityTempl)) {

             req.filterByCity(pCityTempl,QueryRequest.BEGINS_IGNORE_CASE);

           }

           if(Utility.isSet(pState)) {

             req.filterByState(pState,QueryRequest.EXACT_IGNORE_CASE);

           }

           if(!pGetIncativeFl) {

             ArrayList al = new ArrayList();

             al.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

             al.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);

             req.filterBySiteStatusCdList(al);

           }

           if(pResultLimit>0) {

             req.setResultLimit(pResultLimit);

           }

           UserData userD = UserDataAccess.select(conn,pUserId);

           String userTypeCd = userD.getUserTypeCd();

           if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||

              RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd) ||

              RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||

              RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)) {

             DBCriteria dbc = new DBCriteria();

             dbc.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);

             dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,RefCodeNames.USER_ASSOC_CD.STORE);



             if(storeIdV.size()==0)

             storeIdV =

                 UserAssocDataAccess.selectIdOnly(conn,UserAssocDataAccess.BUS_ENTITY_ID,dbc);

             req.filterByStoreIds(storeIdV);

             if(pAccountIds!=null) {

               req.filterByAccountIds(pAccountIds);

             }

           } else if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userTypeCd)) {

             DBCriteria dbc = new DBCriteria();

             dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

             if(storeIdV.size()==0)

                 storeIdV = BusEntityDataAccess.selectIdOnly(conn,dbc);

             req.filterByStoreIds(storeIdV);

             if(pAccountIds!=null) {

               req.filterByAccountIds(pAccountIds);

             }

           } else if(RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)) {

             DBCriteria dbc = new DBCriteria();

             dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

             if(storeIdV.size()==0)

                 storeIdV = BusEntityDataAccess.selectIdOnly(conn,dbc);

             req.filterByStoreIds(storeIdV);

             if(pAccountIds!=null) {

               req.filterByAccountIds(pAccountIds);

             }

/*             dbc.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);

             dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.STORE);

             if(storeIdV.size()==0)

                storeIdV = UserAssocDataAccess.selectIdOnly(conn,UserAssocDataAccess.BUS_ENTITY_ID,dbc);

             req.filterByStoreIds(storeIdV);



             dbc = new DBCriteria();

             dbc.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);

             dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.ACCOUNT);

             if(pAccountIds!=null) {

               dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,pAccountIds);

             }

             IdVector accountIdV =

                 UserAssocDataAccess.selectIdOnly(conn,UserAssocDataAccess.BUS_ENTITY_ID,dbc);

             req.filterByAccountIds(accountIdV);

 */

           } else {

             DBCriteria dbc = new DBCriteria();

             dbc.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);

             dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,

                     RefCodeNames.USER_ASSOC_CD.STORE);

             if(storeIdV.size()==0)

                 storeIdV = UserAssocDataAccess.selectIdOnly(conn,UserAssocDataAccess.BUS_ENTITY_ID,dbc);

             req.filterByStoreIds(storeIdV);

             req.filterByUserId(pUserId);

           }

           req.orderBySiteName(true);

            siteVec = BusEntityDAO.getSiteCollection(conn, req);

        } catch (Exception e) {

          e.printStackTrace();

          throw processException(e);

        } finally {

          closeConnection(conn);

        }



        return siteVec;

    }



    /**

     *  Gets the site based on the account erp number and the site Name

     *

     *@param  pAccountErpNum              Description of the Parameter

     *@param  pSiteName                   Description of the Parameter

     *@return                             an <code>SiteData</code> value

     *@exception  DuplicateNameException  Description of Exception

     *@exception  DataNotFoundException   Description of the Exception

     *@throws  RemoteException            Required by EJB 1.0

     */

    public SiteData getSiteByAcctErp(String pAccountErpNum, String pSiteName)

             throws RemoteException, DataNotFoundException,

            DuplicateNameException {



        SiteData site = null;

        SiteDataVector sdVec = new SiteDataVector();

        Connection conn = null;



        try {

            conn = getConnection();



            if (pAccountErpNum.trim().length() > 0 &&

                    !pAccountErpNum.trim().equals("")) {



                // need to check that this site is associated to account

                DBCriteria crit = new DBCriteria();

                crit.addEqualTo(BusEntityDataAccess.ERP_NUM,

                        String.valueOf(pAccountErpNum));

                crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

                        RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);



                BusEntityDataVector bedVec = BusEntityDataAccess.select(conn,

                        crit);



                if (bedVec.size() == 0) {

                    throw new DataNotFoundException("ERP_NUM :" +

                            pAccountErpNum);

                }



                if (bedVec.size() > 1) {

                    throw new DuplicateNameException("non unique value for account erp number : " +

                            pAccountErpNum);

                }



                BusEntityData bed = (BusEntityData) bedVec.get(0);

                int accountId = bed.getBusEntityId();

                sdVec = getSiteByName(pSiteName, accountId,

                        Site.EXACT_MATCH_IGNORE_CASE,

                        Site.ORDER_BY_ID);

                site = (SiteData) sdVec.get(0);

            }

        } catch (DataNotFoundException de) {

            throw de;

        } catch (DuplicateNameException de) {

            throw de;

        } catch (Exception e) {

            throw new RemoteException("getSite: " + e.getMessage());

        } finally {



            try {



                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }



        return site;

    }











    java.util.Comparator mBudgetCmp =

        new java.util.Comparator() {

            public int compare(Object o1, Object o2) {

                int p1 = ((BudgetSpendView) o1).getBudgetPeriod();

                int p2 = ((BudgetSpendView) o2).getBudgetPeriod();

                if (p1 == p2) {

                    return 0;

                }

                if (p1 > p2) {

                    return 1;

                }

                return -1;

            }

        };





    //Jd begin

    /**

     *  Sets the thresholdAmounts attribute of the SiteBean object

     *

     *@param  pCon                 The new thresholdAmounts value

     *@param  pSite                The new thresholdAmounts value

     *@return                      Description of the Return Value

     *@exception  SQLException     Description of the Exception

     *@exception  RemoteException  Description of the Exception

     */

    private SiteData setThresholdAmounts(Connection pCon, SiteData pSite)

             throws SQLException, RemoteException {

        BusEntityData accountBED = pSite.getAccountBusEntity();



        boolean weightThresholdFlag = false;

        boolean priceThresholdFlag = false;

        boolean contractThresholdFlag = false;

        String sql =

                " select cont.contract_id " +

                "  from clw_contract cont, clw_catalog cat, clw_catalog_assoc cata " +

                " where cont.contract_status_cd = 'ACTIVE' " +

                " and cat.catalog_id = cont.catalog_id " +

                " and cat.catalog_status_cd = 'ACTIVE' " +

                " and cata.catalog_id = cat.catalog_id " +

                " and cata.bus_entity_id =  " + pSite.getBusEntity().getBusEntityId();

        Statement stmt = pCon.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {

            contractThresholdFlag = true;

        } else {

            weightThresholdFlag = true;

            String priceCode = pSite.getPriceCode();

            if (priceCode.indexOf("US") >= 0) {

                priceThresholdFlag = true;

            }

        }

        rs.close();

        stmt.close();



        String priceCode = pSite.getPriceCode();

        if (priceCode.indexOf("US") >= 0) {

            priceThresholdFlag = true;

        }



        BigDecimal weightThreshold = null;

        BigDecimal priceThreshold = null;

        BigDecimal contractThreshold = null;

        if (accountBED != null) {

            int accountId = accountBED.getBusEntityId();

            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, accountId);

            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,

                    RefCodeNames.PROPERTY_TYPE_CD.EXTRA);

            PropertyDataVector properties = PropertyDataAccess.select(pCon, dbc);

            for (int ii = 0; ii < properties.size(); ii++) {

                PropertyData prop = (PropertyData) properties.get(ii);

                String shortDesc = prop.getShortDesc();

                if ("Weight Threshold".equalsIgnoreCase(shortDesc) && weightThresholdFlag) {

                    try {

                        weightThreshold = new BigDecimal(prop.getValue());

                    } catch (Exception exc) {

                        throw new RemoteException("Wrong Weight Threshold value: " + prop.getValue());

                    }

                } else if ("Price Threshold".equalsIgnoreCase(shortDesc) && priceThresholdFlag) {

                    try {

                        priceThreshold = new BigDecimal(prop.getValue());

                    } catch (Exception exc) {

                        throw new RemoteException("Wrong Price Threshold value: " + prop.getValue());

                    }

                } else if ("Contract Threshold".equalsIgnoreCase(shortDesc) && contractThresholdFlag) {

                    try {

                        contractThreshold = new BigDecimal(prop.getValue());

                    } catch (Exception exc) {

                        throw new RemoteException("Wrong Contract Threshold value: " + prop.getValue());

                    }

                }

            }

            pSite.setWeightThreshold(weightThreshold);

            pSite.setPriceThreshold(priceThreshold);

            pSite.setContractThreshold(contractThreshold);

        }

        return pSite;

    }



    //Jd end



    /**

     *  Gets the orderReport attribute of the SiteBean object

     *

     *@param  pIdType              Description of the Parameter

     *@param  pId                  Description of the Parameter

     *@param  pBudgetYear          Description of the Parameter

     *@param  pBudgetPeriod        Description of the Parameter

     *@return                      The orderReport value

     *@exception  RemoteException  Description of the Exception

     */

    public ArrayList getOrderReport(String pIdType,

            int pId,

            int pBudgetYear,

            int pBudgetPeriod)

             throws RemoteException {



        Connection con = null;



        try {

            con = getConnection();



            String subquery = "";



            String siteq = "";

            if (pIdType.equals(RefCodeNames.USER_TYPE_CD.MSB) ||

                    pIdType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER)) {

                siteq = " select distinct ua.bus_entity_id as site_id" +

                        " from clw_user_assoc ua where " +

                        " ua.user_id = " + pId +

                        " and ua.bus_entity_id is not null";

            } else {

                BusEntityData busEntity = BusEntityDataAccess.select

                        (con, pId);

                if (busEntity.getBusEntityTypeCd().equals

                        (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)) {

                    siteq = " select ba.bus_entity1_id as site_id" +

                            " from clw_bus_entity_assoc ba where " +

                            " ba.bus_entity2_id = " + pId;

                }

            }



            if (siteq.length() > 0) {

                subquery += " ( " + siteq + " ) ";

            } else {

                subquery += " ( " + pId + " ) ";

            }



            String q =

                    " SELECT " +

                    " to_char(o.original_order_date ,'mm/dd/yy') as order_date " +

                    " , site.short_desc as order_site_name" +

                    " , 0 as ytd_actual " +

                    " ,  'none ' as rank" +

                    " ,  'none ' as net_sq_footage" +

                    " ,  nvl(sub.short_desc, '-') as BSC" +

                    " , o.original_amount AS requested" +

                    " , 0 as   remaing_budget_pre_commit" +

                    " , 0 as committed " +

                    " , 0 as remaining_budget_post_commit" +

                    " , o.order_status_cd" +

                    " , sl.site_id" +

                    " , sl.budget_period" +

                    " , o.order_id, o.account_id, o.ref_order_id " +

                    " , to_char(o.revised_order_date,'mm/dd/yy') as revised_date " +

                    " , o.order_status_cd " +

                    " , o.original_amount AS approved_amt" +

                    " , o.currency_cd AS currency_cd" +

                    " FROM clw_site_ledger sl,clw_order o , clw_bus_entity site " +

                    " , clw_bus_entity sub, clw_bus_entity_assoc ba2" +

                    " WHERE " +

                    " sl.budget_year = " + pBudgetYear

                     + "  and sub.bus_entity_id (+) = ba2.bus_entity1_id "

                     + "  and ba2.bus_entity2_id (+) = sl.site_id "

                     + "  and ba2.BUS_ENTITY_ASSOC_CD (+) = 'BSC FOR SITE' "

                     + " AND sl.budget_period = " + pBudgetPeriod +

                    " AND sl.order_id = o.order_id" +

                    " AND o.site_id = sl.site_id" +

                    " AND o.site_id = site.bus_entity_id" +

                    " AND o." + ReportingUtils.getValidOrderSQL()

                     + " and o.order_status_cd not in (" + Utility.toCommaSting(ReportingUtils.getInternalOrdersStatusList(),'\'')+")"

                     + " AND o.original_amount > 0";

            q += " and sl.site_id in " + subquery;



            q += " GROUP BY " +

                    " o.original_order_date " +

                    " , o.revised_order_date, o.order_status_cd " +

                    ", o.original_amount" +

                    " ,o.currency_cd" +

                    " , o.order_status_cd" +

                    " , site.short_desc " +

                    " , sl.site_id" +

                    " , sl.budget_period " +

                    " , o.order_id, o.account_id , o.ref_order_id" +

                    " , sub.short_desc "

                     + " order by o.original_order_date desc ";



            Date repdate = new Date();



            logDebug("SQL: " + q);


            ArrayList resl = UniversalDAO.getData(con, q);



            int i = 0;



            for (i = 0; resl != null && i < resl.size(); i++) {

                UniversalDAO.dbrow thisRow = (UniversalDAO.dbrow) resl.get(i);

                int siteId = Integer.parseInt

                        (thisRow.getColumn("SITE_ID").colVal);

                String orderStatusCd = thisRow.getColumn("ORDER_STATUS_CD").colVal;

                if (ReportingUtils.isOrderInAnApprovedStatus(orderStatusCd)) {

                    thisRow.getColumn("COMMITTED").colVal =

                            thisRow.getColumn("REQUESTED").colVal;

                    thisRow.getColumn("REMAINING_BUDGET_POST_COMMIT").colVal =

                            thisRow.getColumn("REMAING_BUDGET_PRE_COMMIT").colVal;

                }

                if (!ReportingUtils.isOrderCommitted(orderStatusCd)) {

                    thisRow.getColumn("APPROVED_AMT").colVal = "0";

                }



                String refoid = thisRow.getColumn("REF_ORDER_ID").colVal;

                if (refoid != null && (!refoid.equals("0"))) {

                    // Check to see if this order is related

                    // to another cancelled order.

                    String q2 = " select original_amount from clw_order where " +

                            " order_id = " +

                            thisRow.getColumn("REF_ORDER_ID").colVal +

                            " and account_id = " +

                            thisRow.getColumn("ACCOUNT_ID").colVal +

                            " and order_status_cd = '" +

                            RefCodeNames.ORDER_STATUS_CD.CANCELLED + "'" +

                            " and  original_amount is not null";



                    logDebug("SQL: " + q2);

                    ArrayList oresl = UniversalDAO.getData(con, q2);

                    if (oresl != null && oresl.size() == 1) {

                        UniversalDAO.dbrow prevOrderRow = (UniversalDAO.dbrow) oresl.get(0);

                        thisRow.getColumn("REQUESTED").colVal =

                                prevOrderRow.getColumn("ORIGINAL_AMOUNT").colVal;



                    }

                }

            }



            String q3 = " SELECT sum(sl.amount)  as ytd_actual" +

                    " , sl.site_id " +

                    " FROM clw_site_ledger sl,clw_order o " +

                    " WHERE " +

                    " sl.budget_year = " + pBudgetYear +

                    " AND sl.budget_period >= 1 " +

                    " AND sl.cost_center_id >= 1 " +

                    " AND sl.order_id = o.order_id " +

                    " AND o.order_status_cd IN ( " +

                    " 'Process ERP PO', 'Ordered', 'ERP Released', 'Invoiced', 'Pending ERP PO' " +

                    " ) " +

                    " AND o.original_amount > 0  and  sl.site_id in " + subquery;

            q3 += "  GROUP BY sl.site_id ";



            if (i > 0) {

                logDebug("SQL: " + q3);

                ArrayList or2 = UniversalDAO.getData(con, q3);

                logDebug(" or2 size=" + or2.size());

                for (int ii = 0; or2 != null && ii < or2.size(); ii++) {

                    UniversalDAO.dbrow totalrow = (UniversalDAO.dbrow) or2.get(ii);

                    for (int j = 0; resl != null && j < resl.size(); j++) {

                        UniversalDAO.dbrow thisrow = (UniversalDAO.dbrow) resl.get(j);

                        if (thisrow.getColumn("SITE_ID").colVal.equals

                                (totalrow.getColumn("SITE_ID").colVal)) {

                            thisrow.getColumn("YTD_ACTUAL").colVal =

                                    totalrow.getColumn("YTD_ACTUAL").colVal;

                        }

                    }

                }

            }



            String q4 = " select s.bus_entity_id, " +

                    " (select nvl(clw_value, ' ') from clw_property where " +

                    " short_desc = 'Rank Index' and bus_entity_id = s.bus_entity_id) as rank, " +

                    " (select nvl(clw_value, ' ') from clw_property where " +

                    " short_desc = 'Net Square Footage:' " +

                    " and bus_entity_id = s.bus_entity_id) as net_sq_footage " +

                    " from clw_bus_entity s where s.bus_entity_id in " + subquery;

            if (i > 0) {

                logDebug("siteAttr SQL: " + q4);

                // Set those site attributes needed if they are configured.

                ArrayList siteAttr = UniversalDAO.getData(con, q4);

                logDebug(" siteAttr size=" + siteAttr.size());

                for (int ii2 = 0; siteAttr != null && ii2 < siteAttr.size(); ii2++) {

                    UniversalDAO.dbrow srow = (UniversalDAO.dbrow) siteAttr.get(ii2);

                    for (int j = 0; resl != null && j < resl.size(); j++) {

                        UniversalDAO.dbrow thisrow = (UniversalDAO.dbrow) resl.get(j);

                        //logDebug(" thisrow: " + thisrow + " \n srow: " + srow );



                        if (thisrow.getColumn("SITE_ID").colVal.equals

                                (srow.getColumn("BUS_ENTITY_ID").colVal)) {

                            String v = srow.getColumn("RANK").colVal;

                            if (null == v) {

                                v = "-";

                            }

                            thisrow.getColumn("RANK").colVal = v;



                            v = srow.getColumn("NET_SQ_FOOTAGE").colVal;

                            if (null == v) {

                                v = "-";

                            }

                            thisrow.getColumn("NET_SQ_FOOTAGE").colVal = v;

                        }

                    }

                }

            }



            String q5 = "SELECT site_id , Sum(amount_allocated) " +

                    " as amount_allocated " +

                    "FROM tclw_acctbudget_report "

                     + "WHERE budget_period <= " + pBudgetPeriod +

                    " and site_id in " + subquery +

                    " and budget_year = " + pBudgetYear +

                    " GROUP BY site_id";



            if (i > 0) {

                logDebug("SQL: " + q5);

                ArrayList amtalloc = UniversalDAO.getData(con, q5);

                logDebug("amtalloc  size=" + amtalloc.size());

                for (int ii = 0; amtalloc != null &&

                        ii < amtalloc.size(); ii++) {

                    UniversalDAO.dbrow alrow = (UniversalDAO.dbrow) amtalloc.get(ii);

                    for (int j = 0; resl != null && j < resl.size(); j++) {

                        UniversalDAO.dbrow thisrow = (UniversalDAO.dbrow) resl.get(j);

                        if (thisrow.getColumn("SITE_ID").colVal.equals

                                (alrow.getColumn("SITE_ID").colVal)) {



                            String t = alrow.getColumn("AMOUNT_ALLOCATED").colVal;

                            if (t == null || t.length() == 0) {

                                t = "0";

                            }

                            BigDecimal ytdalloc = new BigDecimal(t);



                            t = thisrow.getColumn("YTD_ACTUAL").colVal;

                            if (t == null || t.length() == 0) {

                                t = "0";

                            }

                            BigDecimal ytdspend = new BigDecimal(t);



                            thisrow.getColumn("REMAING_BUDGET_PRE_COMMIT").colVal =

                                    ytdalloc.subtract(ytdspend).toString();

                            String orderStatusCd = thisrow.getColumn("ORDER_STATUS_CD").colVal;

                            if (ReportingUtils.isOrderInAnApprovedStatus(orderStatusCd)) {

                                thisrow.getColumn("REMAINING_BUDGET_POST_COMMIT").colVal =

                                        thisrow.getColumn("REMAING_BUDGET_PRE_COMMIT").colVal;

                            }

                        }

                    }

                }

            }



            logDebug("getOrderReport, return size=" + resl.size());

            return resl;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(con);

        }

    }





    private static int mPrevAcctid = 0;

    private static HashMap mPrevBudgetDates = null;





    /**

     *  Description of the Method

     *

     *@param  pCon           Description of the Parameter

     *@param  pSiteId        Description of the Parameter

     *@return                Description of the Return Value

     *@exception  Exception  Description of the Exception

     */

    private HashMap calculateSiteBudgetDates(Connection pCon,int pSiteId) throws Exception {
    	
        BusEntityDAO bdao = new BusEntityDAO();

        int acctid = bdao.getAccountForSite(pCon, pSiteId);

        FiscalCalendarInfo fci = new FiscalCalendarInfo(bdao.getFiscalCalenderV(pCon,acctid, new java.util.Date()));

        if (acctid != mPrevAcctid || mPrevBudgetDates == null) {

            mPrevBudgetDates = fci.getBudgetPeriodsAsHashMap();

            mPrevAcctid = acctid;

            logDebug("calculateSiteBudgetDates for mPrevAcctid=" + mPrevAcctid);

        }

        return mPrevBudgetDates;

    }

















    /**

     *  Gets the accountReportPeriods attribute of the SiteBean object

     *

     *@param  pAccountId           Description of the Parameter

     *@return                      The accountReportPeriods value

     *@exception  RemoteException  Description of the Exception

     */

    public ArrayList getAccountReportPeriods(int pAccountId)

             throws RemoteException {



        Connection con = null;



        try {

            con = getConnection();

            BudgetUtil bu = new BudgetUtil(con);

            IdVector ccids = Utility.toIdVector(bu.getCostCentersForAccount(pAccountId));



      if ( null == ccids || ccids.size() == 0 ) {

    return new ArrayList();

      }



            String q = " SELECT distinct SL.budget_year, " +

                    " SL.budget_period, " +

                    " 'none' as period_start_date, " +

                    " 'none' as period_end_date " +

                    " FROM  clw_site_ledger sl  " +

                    " where sl.cost_center_id in ( "+IdVector.toCommaString(ccids)+" ) " +

                    " AND budget_year IS NOT null " +

                    " and sl.cost_center_id > 0 " +

                    " order by 1 desc, 2 desc";



            logDebug("SQL: " + q);

            ArrayList bps = UniversalDAO.getData(con, q);

            logDebug(" bps size=" + bps.size());



            // Now calculate the start and end dates.

            BusEntityDAO bdao = new BusEntityDAO();

            FiscalCalendarInfo fci = new FiscalCalendarInfo(bdao.getCurrentFiscalCalenderV(con, pAccountId));

            HashMap binfo = fci.getBudgetPeriodsAsHashMap();

            for (int i = 0; bps != null && i < bps.size(); i++) {

                logDebug("loop index="+i);

                UniversalDAO.dbrow thisrow = (UniversalDAO.dbrow) bps.get(i);

                FiscalCalendarInfo.BudgetPeriodInfo bi = (FiscalCalendarInfo.BudgetPeriodInfo) binfo.get(new Integer(thisrow.getColumn("BUDGET_PERIOD").colVal));

                if(bi == null){

                    logError("No budget period info for loop index "+i);

                }else{

                    if(thisrow.getColumn("PERIOD_END_DATE") != null){

                        thisrow.getColumn("PERIOD_END_DATE").colVal = bi.getEndDateMmdd();

                    }

                    if(thisrow.getColumn("PERIOD_START_DATE") != null){

                        thisrow.getColumn("PERIOD_START_DATE").colVal = bi.getStartDateMmdd();

                    }

                }

            }

            return bps;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(con);

        }



    }





    /**

     *  Description of the Method

     *

     *@param  pCon           Description of the Parameter

     *@param  pSiteId        Description of the Parameter

     *@param  pMaxAge        Description of the Parameter

     *@return                Description of the Return Value

     *@exception  Exception  Description of the Exception

     */

    /*private ScheduleOrderDates checkForCachedOrderDates(Connection pCon, int pSiteId, int pMaxAge)

            throws Exception {

        PropertyUtil pu = new PropertyUtil(pCon);

        try {



            Date nextDeliv  = pu.getAsDate(0, pSiteId,RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_DELIVERY_DATE, pMaxAge);

            Date nextCutoff = pu.getAsDate(0, pSiteId,RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE, pMaxAge);

            Date nextCutoffTime = pu.getAsDate(0,

                                               pSiteId,

                                               RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME,

                                               ScheduledOrderManager.TIME_FORMAT,

                                               pMaxAge);



            ScheduleOrderDates sods = new ScheduleOrderDates(nextDeliv, nextCutoff, nextCutoffTime);



            logInfo("checkForCachedOrderDates 202 (found) pSiteId=" + pSiteId);

            logInfo("checkForCachedOrderDates ScheduleOrderDates: " + sods);



            return sods;

        } catch (DataNotFoundException e) {

//		logInfo("checkForCachedOrderDates 203 (NOT found) pSiteId=" + pSiteId);

            return null;

        }



    }*/





    /**

     *  Adds a feature to the CachedOrderDates attribute of the SiteBean object

     *

     *@param  pCon           The feature to be added to the CachedOrderDates

     *      attribute

     *@param  pSiteId        The feature to be added to the CachedOrderDates

     *      attribute

     *@param  pSods          The feature to be added to the CachedOrderDates

     *      attribute

     *@exception  Exception  Description of the Exception

     */

    private void addCachedOrderDates(Connection pCon,

            int pSiteId,

            ScheduleOrderDates pSods)

             throws Exception {

        logDebug("addCachedOrderDates 100 pSiteId=" + pSiteId);

        PropertyUtil pu = new PropertyUtil(pCon);
		
		ArrayList typeAL = new ArrayList();
		typeAL.add(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_DELIVERY_DATE);
		typeAL.add(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE);
		typeAL.add(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME);

		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pSiteId);
		dbc.addOneOf(PropertyDataAccess.PROPERTY_TYPE_CD, typeAL);
		log.info("Cached order dates sql: "+PropertyDataAccess.getSqlSelectIdOnly("*", dbc));
		PropertyDataVector pDV = PropertyDataAccess.select(pCon,dbc);
		boolean nextDeliveryFl = true;
		boolean nextCutOffDateFl = true;
		boolean nextCutOffTimeFl = true;
		for(Iterator iter = pDV.iterator(); iter.hasNext();) {
			PropertyData pD = (PropertyData) iter.next();
			if(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_DELIVERY_DATE.equals(pD.getPropertyTypeCd())) {
				String val = pD.getValue();
				Date nextDel = pSods.getNextOrderDeliveryDate();
				DateFormat df = DateFormat.getDateInstance();
				String newVal = df.format(nextDel);
				log.info("NextOrderDeliveryDate. Old value: "+val+" New value: "+newVal);
				try {
					if(newVal.equals(val)) {
						nextDeliveryFl = false;
					}
				} catch (Exception exc){}
			}

			if(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE.equals(pD.getPropertyTypeCd())) {
				String val = pD.getValue();
				Date nextCuoffDate = pSods.getNextOrderCutoffDate();
				DateFormat df = DateFormat.getDateInstance();
				String newVal = df.format(nextCuoffDate);
				log.info("NextCutoffDate. Old value: "+val+" New value: "+newVal);
				try {
					if(newVal.equals(val)) {
						nextCutOffDateFl = false;
					}
				} catch (Exception exc){}
			}
			
			if(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME.equals(pD.getPropertyTypeCd())) {
				String val = pD.getValue();
				Date nextCuoffTime = pSods.getNextOrderCutoffTime();
				SimpleDateFormat df = new SimpleDateFormat(ScheduledOrderManager.TIME_FORMAT);
				String newVal = df.format(nextCuoffTime);
				log.info("NextCutoffDate. Old value: "+val+" New value: "+newVal);
				try {
					if(newVal.equals(val)) {
						nextCutOffTimeFl = false;
					}
				} catch (Exception exc){}
			}
		}
		
		if(nextDeliveryFl) {
			pu.saveDateValue
                (0, pSiteId,
                RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_DELIVERY_DATE,
                RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_DELIVERY_DATE,
                pSods.getNextOrderDeliveryDate()
                );
		}

		if(nextCutOffDateFl) {
			pu.saveDateValue
                (0, pSiteId,
                RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE,
                RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE,
                pSods.getNextOrderCutoffDate()
                );
		}

		if(nextCutOffTimeFl) {
			pu.saveDateValue
                (0, pSiteId,
                RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME,
                RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME,
                pSods.getNextOrderCutoffTime(),
                ScheduledOrderManager.TIME_FORMAT,
                null
                );
		}

        logDebug("addCachedOrderDates 200 pSiteId=" + pSiteId);

    }

    private ScheduleJoinView getDeliveryScheduleById(ScheduleData schedD)throws Exception{
    	String scheduleIdStr = Integer.toString(schedD.getScheduleId());
    	ScheduleJoinView sjv= null;
    	if((scheduleJoinViewMap != null) && (scheduleJoinViewMap.containsKey(scheduleIdStr))){
    		logInfo("SiteBean.java getDeliveryScheduleById() scheduleJoinViewMap object EXISTS");
    		sjv = (ScheduleJoinView) scheduleJoinViewMap.get(scheduleIdStr);
    	}else {
    		logInfo("SiteBean.java getDeliveryScheduleById() CREATING scheduleJoinViewMap object ");
    		if(scheduleJoinViewMap == null){
    			scheduleJoinViewMap = new HashMap();
    		}

    		if (schedD.getScheduleTypeCd().equals(RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE)){
    			Schedule schedBean = APIAccess.getAPIAccess().getScheduleAPI();
    			sjv = schedBean.getScheduleById(schedD.getScheduleId());
    		}else{
    			Distributor distBean = APIAccess.getAPIAccess().getDistributorAPI();
        		sjv = distBean.getDeliveryScheduleById(schedD.getScheduleId(), false);
    		}

    		scheduleJoinViewMap.put(scheduleIdStr, sjv);
     	}
    	return sjv;
    }



    /**

     * Description of the Method

     *

     * @param pCon    Description of the Parameter

     * @param pSiteId Description of the Parameter

     * @param pMaxAge Description of the Parameter

     * @return Description of the Return Value

     */

    private ScheduleOrderDates calculateNextOrderDates

            (Connection pCon, int pSiteId, int pAccountId, int pMaxAge) {

        return calculateNextOrderDates(pCon, pSiteId, pAccountId, pMaxAge, null);

    }



    private ScheduleOrderDates calculateNextOrderDates(Connection pCon, int pSiteId, int pAccountId, int pMaxAge, GregorianCalendar odate) {
        Statement stmt = null;
        DBCriteria dbc;
        try {

            // Check for a cached entry.
            /*ScheduleOrderDates cachedDates =
                    checkForCachedOrderDates(pCon, pSiteId, pMaxAge);
            if (null != cachedDates && pMaxAge > 0) {
                // Ignore the cached dates to recalculate the "Physical Inventory" periods.
            	//return cachedDates;
            }*/
            // check for Corporate Schedule for site            
            boolean allowCorpSchedOrder = false;            
            try{            	
            	PropertyUtil pru = new PropertyUtil(pCon);
            	allowCorpSchedOrder = Utility.isTrue(pru.fetchValue(0, pSiteId, RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER));
            }catch(DataNotFoundException e){}
            
            ScheduleData scheduleD = null;
            SiteDeliveryScheduleView sds = null;
            int accountCutoffDays = 0;
            
            if (allowCorpSchedOrder){
            	Schedule scheduleBean = APIAccess.getAPIAccess().getScheduleAPI();
            	scheduleD = scheduleBean.getSchedule(pSiteId);
            }else{
            	//Get account cutoff
                int accountId = pAccountId;
                if(accountId==0) {
                  dbc = new DBCriteria();
                  dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pSiteId);
                  dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                                   RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                  IdVector aIdV = BusEntityAssocDataAccess.selectIdOnly(pCon,
                                    BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);
                  if(aIdV.size()>0) {
                    Integer aIdI = (Integer) aIdV.get(0);
                    accountId = aIdI.intValue();
                  }
                }

                dbc = new DBCriteria();
                dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, accountId);
                dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,
                          RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);
                PropertyDataVector propertyDV = PropertyDataAccess.select(pCon,dbc);
                if(propertyDV.size()==1) {
                  PropertyData pD = (PropertyData) propertyDV.get(0);
                  String ss = pD.getValue();
                  try {
                    accountCutoffDays = Integer.parseInt(ss);
                  } catch(Exception exc) {
                    logError("Illegal account schedule cutoff days value: "+ss+" Account id: "+accountId);
                  }
                } else if(propertyDV.size()>1) {
                  logError("Multiple account schedule cutoff days for the account. Account id: "+accountId);
                }


                // Get the shopping catalog for
                // the site.
                dbc = new DBCriteria();
                dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pSiteId);
                dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                        RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
                IdVector catids = CatalogAssocDataAccess.selectIdOnly
                        (pCon, CatalogAssocDataAccess.CATALOG_ID, dbc);

                if (catids == null || catids.size() == 0) {
                    return null;
                }
                Integer catid = (Integer) catids.get(0);
                logDebug("Site id: " + pSiteId +
                        " tied to catalog id: " + catid);

                AddressDataVector
                        addresses = BusEntityDAO.getSiteAddresses
                        (pCon, pSiteId,
                        RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

                String zipCode = "";

                if (addresses.size() > 0) {
                    AddressData address = ((AddressData) addresses.get(0));
                    zipCode = address.getPostalCode();

                    if (zipCode == null) {
                        zipCode = "";
                    } else {
                        if((address.getCountryCd()!=null)&&(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(address.getCountryCd()))){
                            if (zipCode.length() > 5) {
                                zipCode = zipCode.substring(0, 5);
                            }
                        }
                    }

                    logDebug("114 zipCode=" + zipCode);

                } else {
                    logDebug("113 no shiping address for site id:" + pSiteId);
                    return null;
                }

                // Get the major distributor for the
                // catalog.
                dbc = new DBCriteria();
                dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID,catid);
                dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                        RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
                IdVector distIdV =
                   CatalogAssocDataAccess.selectIdOnly(pCon,CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
                int distId = 0;
                if(distIdV.size()==1) {
                  Integer distIdI = (Integer) distIdV.get(0);
                  distId = distIdI.intValue();
                }
                if(distId==0) {
                  String q = "select cs.bus_entity_id, count(cs.item_id) qty " +
                             " from clw_catalog_structure cs " +
                             " where catalog_id = " + catid +
                             " group by cs.bus_entity_id " +
                             " order by qty desc ";
                  stmt = pCon.createStatement();
                  ResultSet rs = stmt.executeQuery(q);
                  if(rs.next()) {
                    distId = rs.getInt(1);
                  }
                  rs.close();
                  stmt.close();
                }
                if(distId>0) {
                    SiteDeliveryScheduleViewVector delivSchdv =
                            BusEntityDAO.getDeliverySchs(pCon, 0, pSiteId);
                    sds = delivSchdv != null && delivSchdv.size() > 0 ?
                            (SiteDeliveryScheduleView) delivSchdv.get(0) :
                            null;

                    if (sds == null) {
                        logDebug("1 No schedule delivery setup for site=" +
                                pSiteId);
                        return null;
                    }
                    String siteScheduleType = Utility.strNN(sds.getSiteScheduleType());
                    if(siteScheduleType.startsWith("Any")) {
                     //All right. Go ahead
                    } else if(sds.getSiteScheduleType().startsWith("Spe")) {
                       if(sds.getIntervWeek() == null) {
                          logDebug("2.1 No schedule delivery setup for site=" +
                                   pSiteId);
                          return null;
                        }
                    } else {
                        if (sds.getWeek1ofMonth() == false &&
                            sds.getWeek2ofMonth() == false &&
                            sds.getWeek3ofMonth() == false &&
                            sds.getWeek4ofMonth() == false &&
                            sds.getLastWeekofMonth() == false
                            ) {
                          logDebug("2 No schedule delivery setup for site=" +
                                pSiteId);
                          return null;
                        }
                    }

                    Distributor distBean = APIAccess.getAPIAccess().getDistributorAPI();

                    scheduleD = distBean.getScheduleForZipCode(distId, zipCode, pAccountId, null);
            }         
            }
            
                if(scheduleD==null) {
                   return null;
                }
                if(odate==null)  {
                    odate=new GregorianCalendar();
                }
                
                int scheduleId = scheduleD.getScheduleId();
                ScheduleJoinView sjVw = getDeliveryScheduleById(scheduleD);
                ScheduleProc scheduleProc = new ScheduleProc(sjVw, sds, accountCutoffDays);
                scheduleProc.initSchedule();
                ScheduleOrderDates sods = scheduleProc.getOrderDeliveryDates(odate.getTime(), odate.getTime());
                if (null == sods) {
                    logDebug(" ScheduleOrderDates not calculated for scheduleId="
                             + scheduleId
                             + " odate.getTime=" + odate.getTime());
                    sods = new ScheduleOrderDates(null, null);
                    sods.setNextOrderCutoffTime(scheduleProc.getCutoffTime());
                } else {
                	sods.setNextOrderCutoffTime(scheduleProc.getCutoffTime());
                	addCachedOrderDates(pCon, pSiteId, sods);// will not need to set this some day. Right now new UI use the properties
                }

                /// Loading of physical inventory periods, Physical InvCartAccess Interval
                if (sjVw != null) {
                    ScheduleDetailDataVector scheduleDetails = sjVw.getScheduleDetail();
                    if (scheduleDetails != null) {
                        PhysicalInventoryPeriodArray physicalInvPeriods = new PhysicalInventoryPeriodArray();
                        physicalInvPeriods.startLoadingItems();
                        for(int i = 0; i < scheduleDetails.size(); ++i) {
                            ScheduleDetailData scheduleDetail = (ScheduleDetailData) scheduleDetails.get(i);
                            String detType = scheduleDetail.getScheduleDetailCd();
                            String detValue = scheduleDetail.getValue();
                            if (detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE) ||
                                detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE) ||
                                detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE)) {
                                physicalInvPeriods.addItem(detType, detValue);
                            }else if (detType.equals(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL)){
                            	sods.setInventoryCartAccessInterval(detValue);                                
                            }
                        }
                        if (physicalInvPeriods.finishLoadingItems()) {
                            sods.setPhysicalInventoryPeriods(physicalInvPeriods.getPeriods());
                        } else {
                            logError("Invalid Physical Inventory Periods for schedule with id: " +
                                scheduleId + ", site id: " + pSiteId );
                        }
                    }
                }

                return sods;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }


    //******************************************************************************

    /**

     *  Gets a set of cutoff and delivery pairs for the site

     *

     *@param  pSiteId           Site indentifier

     *@param  pAccountId        Account indentifier

     *@param  pBegDate          the start month of the interval

     *@param  pEndDate          the end month of the interval

     *@return                   a set of ScheduleOrderDates

     *@throws  RemoteException

     */



    public ArrayList getOrderScheduleDates(int pSiteId, int pAccountId, Date pBegDate, Date pEndDate)

             throws RemoteException {

        ArrayList scheduleDatesVector = new ArrayList();

        try {

           scheduleDatesVector = getOrderScheduleDatesNoExc(pSiteId, pAccountId, pBegDate, pEndDate);

           if(scheduleDatesVector!=null && scheduleDatesVector.size()>0) {

             Object oo = scheduleDatesVector.get(0);

             if(oo instanceof String) {

               throw new Exception ((String)oo);

             }

           }

           return scheduleDatesVector;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("SiteBean.getOrderScheduleDates: " + e.getMessage());

        }

    }





    //******************************************************************************

    /**

     *  Gets a set of cutoff and delivery pairs for the site.

     * It doesn't throw Exception when error found but puts error message as first element of reurned list.

     *

     *@param  pSiteId           Site indentifier

     *@param  pAccountId        Account indentifier

     *@param  pBegDate          the start month of the interval

     *@param  pEndDate          the end month of the interval

     *@return                   a set of ScheduleOrderDates

     *@throws  RemoteException

     */



    public ArrayList getOrderScheduleDatesNoExc(int pSiteId, int pAccountId, Date pBegDate, Date pEndDate)

             throws RemoteException {

        Connection conn = null;

        ArrayList resultVector = new ArrayList();

        try {

            if (pEndDate.before(pBegDate)) {

                String errorMess = "Incorrect  getOrderScheduleDates request. " +

                        "End Date could not be before Start Date ";

               resultVector.clear();

               resultVector.add(errorMess);

               return resultVector;

            }

            //set pEnd date to the end of month

            GregorianCalendar endCal = new GregorianCalendar();

            endCal.setTime(pEndDate);

            endCal.set(Calendar.DATE, 1);

            endCal.add(Calendar.MONTH, 1);

            endCal.add(Calendar.DATE, -1);

            pEndDate = endCal.getTime();

            conn = getConnection();

            //Get site delivery week(s)

            SiteDeliveryScheduleViewVector delivSchdv =

                    BusEntityDAO.getDeliverySchs(conn, 0, pSiteId);

            if (delivSchdv.size() == 0) {

                String errorMess = "No delivery week property for the site. Site id: " + pSiteId;

               resultVector.clear();

               resultVector.add(errorMess);

               return resultVector;

            }

            if (delivSchdv.size() > 1) {

                String errorMess = "More than 1 delivery week property for the site. Site id: " + pSiteId;

                //throw new Exception(errorMess);

                logInfo(errorMess);

            }

            SiteDeliveryScheduleView sds = (SiteDeliveryScheduleView) delivSchdv.get(0);

            boolean siteDeliveryScheduleFl = true;

            if (!sds.getWeek1ofMonth() && !sds.getWeek2ofMonth() &&

                    !sds.getWeek3ofMonth() && !sds.getWeek4ofMonth() &&

                    !sds.getLastWeekofMonth() &&

                    sds.getIntervWeek() == null ) {

                siteDeliveryScheduleFl = false;

                String errorMess = "No scheduled delivery week for the site. Site id: " + pSiteId;

                resultVector.clear();

                resultVector.add(errorMess);

                return resultVector;

            }

            //Get site zip code

            AddressDataVector addresses =

                    BusEntityDAO.getSiteAddresses(conn, pSiteId,

                    RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

            if (addresses.size() == 0) {

               String errorMess = "No shipping address for the site. Site id: " + pSiteId;

               resultVector.clear();

               resultVector.add(errorMess);

               return resultVector;

            }

            if (delivSchdv.size() > 1) {

               String errorMess = "More than 1 shipping address for the site. Site id: " + pSiteId;

               resultVector.clear();

               resultVector.add(errorMess);

               return resultVector;

            }

            AddressData addressD = (AddressData) addresses.get(0);

            String zipCode = addressD.getPostalCode();

            if (zipCode == null) {

               String errorMess = "Shipping address does not have zip code. Site id: " + pSiteId;

               resultVector.clear();

               resultVector.add(errorMess);

               return resultVector;

            }

            zipCode = zipCode.trim();

            if((addressD.getCountryCd()!=null)&&(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(addressD.getCountryCd()))){

                if (zipCode.length() > 5) {

                    zipCode = zipCode.substring(0, 5);

                }

            }



            //Get major distributor

            int distId = getMajorDist(conn, pSiteId);

            //Get distributor delivery schedule

            Distributor distBean =

                    APIAccess.getAPIAccess().getDistributorAPI();

            Hashtable zipFilter = new Hashtable();

            zipFilter.put("POSTAL_CD", zipCode);

            DeliveryScheduleViewVector deliverySchedVwV = distBean.getDeliverySchedules(distId, zipFilter, true);

            ScheduleJoinView scheduleJ = null;



            int count = 0;

            int scheduleId = 0;

            for (int ii = 0; ii < deliverySchedVwV.size(); ii++) {

                DeliveryScheduleView deliverySchedVw =

                        (DeliveryScheduleView) deliverySchedVwV.get(ii);

                if (

                        RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE.equals(deliverySchedVw.getScheduleStatus())) {

                    count++;

                    if (count > 1) {

                        String errorMess = "More than 1 active delivery scheduels for distributor=" + distId +

                                " and zip code=" + zipCode;

                        resultVector.clear();

                        resultVector.add(errorMess);

                        return resultVector;

                    }

                    scheduleId = deliverySchedVw.getScheduleId();

                    scheduleJ = distBean.getDeliveryScheduleById(scheduleId);

                }

            }

            if (scheduleJ == null) {

               String errorMess = "No active delivery scheduels for distributor=" + distId +

                        " and zip code=" + zipCode;

               resultVector.clear();

               resultVector.add(errorMess);

               return resultVector;

            }



            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);

            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,

                      RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);

            PropertyDataVector propertyDV = PropertyDataAccess.select(conn,dbc);

            int accountCutoffDays = 0;

            if(propertyDV.size()==1) {

              PropertyData pD = (PropertyData) propertyDV.get(0);

              String ss = pD.getValue();

              try {

                accountCutoffDays = Integer.parseInt(ss);

              } catch(Exception exc) {

                logError("Illegal account schedule cutoff days value: "+ss+" Account id: "+pAccountId);

              }

            } else if(propertyDV.size()>1) {

              logError("Multiple account schedule cutoff days for the account. Account id: "+pAccountId);

            }



            ScheduleProc scheduleProc = new ScheduleProc(scheduleJ, sds, accountCutoffDays);

            scheduleProc.initSchedule();



            ScheduleOrderDates orderDates = scheduleProc.getFirstOrderDeliveryDates(pBegDate, pBegDate, true);

            while (orderDates != null

                     && !orderDates.getNextOrderDeliveryDate().after(pEndDate)) {

                resultVector.add(orderDates);

                orderDates = scheduleProc.getNextOrderDeliveryDates();

            }



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("SiteBean.getOrderScheduleDates: " + e.getMessage());

        } finally {

            try {

                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }

        resultVector = sortScheduleOrderDates(resultVector);

        return resultVector;

    }


    public ArrayList getOrderScheduleDatesForMainDistributor(int pSiteId, int pAccountId, Date pBegDate, Date pEndDate)
    throws RemoteException {

    	Connection conn = null;
    	ArrayList resultVector = new ArrayList();

    	try {

    		if (pEndDate.before(pBegDate)) {

    			String errorMess = "Incorrect  getOrderScheduleDates request. " +
    								"End Date could not be before Start Date ";

    			resultVector.clear();
    			resultVector.add(errorMess);
    			return resultVector;
    		}

    		//set pEnd date to the end of month

    		GregorianCalendar endCal = new GregorianCalendar();
    		endCal.setTime(pEndDate);
    		endCal.set(Calendar.DATE, 1);
    		endCal.add(Calendar.MONTH, 1);
    		endCal.add(Calendar.DATE, -1);		
    		pEndDate = endCal.getTime();		
    		conn = getConnection();

    		//Get site delivery week(s)

    		SiteDeliveryScheduleViewVector delivSchdv =BusEntityDAO.getDeliverySchs(conn, 0, pSiteId);

    		if (delivSchdv.size() == 0) {
		
    			String errorMess = "No delivery week property for the site. Site id: " + pSiteId;		
    			resultVector.clear();		
    			resultVector.add(errorMess);		
    			return resultVector;		
    		}

    		if (delivSchdv.size() > 1) {
		
    			String errorMess = "More than 1 delivery week property for the site. Site id: " + pSiteId;
    			logInfo(errorMess);	
    		}

    		SiteDeliveryScheduleView sds = (SiteDeliveryScheduleView) delivSchdv.get(0);
    		boolean siteDeliveryScheduleFl = true;

    		if (!sds.getWeek1ofMonth() && !sds.getWeek2ofMonth() &&
    				!sds.getWeek3ofMonth() && !sds.getWeek4ofMonth() &&
    				!sds.getLastWeekofMonth() &&
    				sds.getIntervWeek() == null ) {

    			siteDeliveryScheduleFl = false;
    			String errorMess = "No scheduled delivery week for the site. Site id: " + pSiteId;
    			resultVector.clear();
    			resultVector.add(errorMess);
    			return resultVector;
    		}

    		//Get site zip code

    		AddressDataVector addresses =BusEntityDAO.getSiteAddresses(conn, pSiteId,RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

    		if (addresses.size() == 0) {

    			String errorMess = "No shipping address for the site. Site id: " + pSiteId;
    			resultVector.clear();
    			resultVector.add(errorMess);
    			return resultVector;
    		}

    		if (delivSchdv.size() > 1) {

    			String errorMess = "More than 1 shipping address for the site. Site id: " + pSiteId;
    			resultVector.clear();
    			resultVector.add(errorMess);
    			return resultVector;
    		}

    		AddressData addressD = (AddressData) addresses.get(0);

    		String zipCode = addressD.getPostalCode();

    		if (zipCode == null) {

    			String errorMess = "Shipping address does not have zip code. Site id: " + pSiteId;
    			resultVector.clear();
    			resultVector.add(errorMess);
    			return resultVector;
    		}

    		zipCode = zipCode.trim();

    		if((addressD.getCountryCd()!=null)&&(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(addressD.getCountryCd()))){

    			if (zipCode.length() > 5) {
    				zipCode = zipCode.substring(0, 5);
    			}
    		}

    		//Get main distributor
    		
            CatalogInformation catEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
            Distributor distBean =APIAccess.getAPIAccess().getDistributorAPI();
        	// Get Catalog id
            CatalogDataVector cdv = catEjb.getCatalogsCollectionByBusEntity(pSiteId,RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
            int catId = ((CatalogData)cdv.get(0)).getCatalogId();
            
    		int distId = distBean.getMajorDistforCatalog(catId);
    		//Get distributor delivery schedule

    		Hashtable zipFilter = new Hashtable();
    		zipFilter.put("POSTAL_CD", zipCode);

    		DeliveryScheduleViewVector deliverySchedVwV = distBean.getDeliverySchedules(distId, zipFilter, true);

    		ScheduleJoinView scheduleJ = null;

    		int count = 0;
    		int scheduleId = 0;

    		for (int ii = 0; ii < deliverySchedVwV.size(); ii++) {

    			DeliveryScheduleView deliverySchedVw =(DeliveryScheduleView) deliverySchedVwV.get(ii);

    			if (RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE.equals(deliverySchedVw.getScheduleStatus())) {

    				count++;

    				if (count > 1) {

    					String errorMess = "More than 1 active delivery scheduels for distributor=" + distId +
    							" and zip code=" + zipCode;

    					resultVector.clear();
    					resultVector.add(errorMess);
    					return resultVector;
    				}

    				scheduleId = deliverySchedVw.getScheduleId();
    				scheduleJ = distBean.getDeliveryScheduleById(scheduleId);
    			}
    		}

    		if (scheduleJ == null) {

    			String errorMess = "No active delivery scheduels for distributor=" + distId +
    					" and zip code=" + zipCode;

    			resultVector.clear();
    			resultVector.add(errorMess);
    			return resultVector;
    		}

    		DBCriteria dbc = new DBCriteria();
    		dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);
    		dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);

    		PropertyDataVector propertyDV = PropertyDataAccess.select(conn,dbc);

    		int accountCutoffDays = 0;

    		if(propertyDV.size()==1) {

    			PropertyData pD = (PropertyData) propertyDV.get(0);
    			String ss = pD.getValue();
    			try {
    				accountCutoffDays = Integer.parseInt(ss);

    			} catch(Exception exc) {
    				logError("Illegal account schedule cutoff days value: "+ss+" Account id: "+pAccountId);
    			}

    		} else if(propertyDV.size()>1) {
    			logError("Multiple account schedule cutoff days for the account. Account id: "+pAccountId);

    		}

    		ScheduleProc scheduleProc = new ScheduleProc(scheduleJ, sds, accountCutoffDays);
    		scheduleProc.initSchedule();

    		ScheduleOrderDates orderDates = scheduleProc.getFirstOrderDeliveryDates(pBegDate, pBegDate, true);

    		while (orderDates != null && !orderDates.getNextOrderDeliveryDate().after(pEndDate)) {

    			resultVector.add(orderDates);
    			orderDates = scheduleProc.getNextOrderDeliveryDates();
    		}



    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RemoteException("SiteBean.getOrderScheduleDates: " + e.getMessage());	
    	} finally {
    		try {
    			if (conn != null) {
    				conn.close();
    			}
    		} catch (Exception ex) {
    		}

    	}

    	resultVector = sortScheduleOrderDates(resultVector);

    	return resultVector;

    }



    /**

     *  Description of the Method

     *

     *@param  pDate          Description of the Parameter

     *@param  pCutoffDays    Description of the Parameter

     *@param  pExceptDates   Description of the Parameter

     *@param  pHolidayDates  Description of the Parameter

     *@return                Description of the Return Value

     */

    private Date calcCutoffDate(Date pDate, int pCutoffDays,

            ArrayList pExceptDates, ArrayList pHolidayDates) {

        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(pDate);

        int count = pCutoffDays;

        while (count > 0) {

            cal.add(Calendar.DATE, -1);

            Date calDate = cal.getTime();

            int wd = cal.get(Calendar.DAY_OF_WEEK);

            if (wd == 1 || wd == 7) {

                continue;

            }

            if (isDateInList(calDate, pExceptDates) ||

                    isDateInList(calDate, pHolidayDates)) {

                continue;

            }

            count--;

        }

        return cal.getTime();

    }





    /**

     *  Gets the nearst date inside interval

     *

     *@param  pDate       Description of the Parameter

     *@param  pDateList   Description of the Parameter

     *@param  pDaysAfter

     *@return             The dateInList value

     */

    private Date getDateSubstitution(Date pDate, ArrayList pDateList, int pDaysAfter) {

        boolean ret = false;

        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(pDate);

        GregorianCalendar calAfter = (GregorianCalendar) cal.clone();

        calAfter.add(Calendar.DATE, pDaysAfter);

        Date dateAfter = calAfter.getTime();

        Date dateSubst = null;

        long diff = 0;

        for (int ii = 0; ii < pDateList.size(); ii++) {

            Date dd = (Date) pDateList.get(ii);

            if (!dd.before(pDate) &&

                    !dd.after(dateAfter)) {

                if (dateSubst == null || dd.getTime() - pDate.getTime() < diff) {

                    dateSubst = dd;

                }

            }

        }

        return dateSubst;

    }





    /**

     *  Gets the dateInList attribute of the DistributorBean object

     *

     *@param  pDate      Description of the Parameter

     *@param  pDateList  Description of the Parameter

     *@return            The dateInList value

     */

    private boolean isDateInList(Date pDate, ArrayList pDateList) {

        boolean ret = false;

        for (int ii = 0; ii < pDateList.size(); ii++) {

            Date dd = (Date) pDateList.get(ii);

            if (dd.equals(pDate)) {

                ret = true;

                break;

            }

        }

        return ret;

    }





    /**

     *  Gets the major distributor for the site

     *

     *@param  pSiteId           Description of the Parameter

     *@return                   DistributorData object

     *@throws  RemoteException

     */

    public DistributorData getMajorSiteDist(int pSiteId)

             throws RemoteException {

        DistributorData distD = null;

        Connection conn = null;

        try {

            conn = getConnection();

            int distId = getMajorDist(conn, pSiteId);

            if (distId == 0) {

                String errorMess = "No primary distributor for the site. Site id: " + pSiteId;

                throw new Exception(errorMess);

            }

            Distributor distBean = APIAccess.getAPIAccess().getDistributorAPI();

            distD = distBean.getDistributor(distId);



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("SiteBean.getMajorSiteDist: " + e.getMessage());

        } finally {

            try {

                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }

        return distD;

    }





    /**

     *Returns all of the distributors that are servicing this site.

     *@param pSiteId the id of the site that we want to find rlated distributors

     *@returns a populated DistributorDataVector

     *@throws RemoteException if an error occurs

     */

    public DistributorDataVector getAllDistributorsForSite(int pSiteId) throws RemoteException,DataNotFoundException{

        Connection con=null;

        try{

            con = getConnection();

            int catId;

            try{

                catId = getShoppigCatalogIdForSite(pSiteId,con);

            }catch(DataNotFoundException e){

                return new DistributorDataVector();

            }

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,catId);

            IdVector distIds = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.BUS_ENTITY_ID, crit);

            HashSet distIdsSet = new HashSet();

            distIdsSet.addAll(distIds);

            DistributorDataVector dv = new DistributorDataVector();

            DistributorBean distBean = new DistributorBean();

            Iterator it = distIdsSet.iterator();

            while(it.hasNext()){

                Integer id = (Integer) it.next();

                if(id != null && id.intValue() != 0){

                    BusEntityData dist = BusEntityDataAccess.select(con,id.intValue());

                    dv.add(distBean.getDistributorDetails(con,dist));

                }

            }

            return dv;

        }catch(Exception e){

            throw processException(e);

        }finally{

            closeConnection(con);

        }

    }



    /**

     *Returns the catalog id for the supplied site.  If 0 or more than 1 exist an exception is throwm (0 throws a DataNotFoundException).

     */

    private int getShoppigCatalogIdForSite(int pSiteId, Connection pCon) throws Exception, DataNotFoundException{

        DBCriteria dbc = new DBCriteria();

        String catT = CatalogDataAccess.CLW_CATALOG;

        String catAT = CatalogAssocDataAccess.CLW_CATALOG_ASSOC;

        // get the catalog for the site

        dbc.addJoinTableEqualTo(catAT,CatalogAssocDataAccess.BUS_ENTITY_ID, pSiteId);

        dbc.addJoinTableEqualTo(catAT,CatalogAssocDataAccess.CATALOG_ASSOC_CD,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

        dbc.addJoinTableEqualTo(catT,CatalogDataAccess.CATALOG_TYPE_CD,RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

        dbc.addJoinTableEqualTo(catT,CatalogDataAccess.CATALOG_STATUS_CD,RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

        dbc.addJoinCondition(catT,CatalogDataAccess.CATALOG_ID,catAT,CatalogAssocDataAccess.CATALOG_ID);

        IdVector idv = JoinDataAccess.selectIdOnly( pCon,catT, CatalogDataAccess.CATALOG_ID, dbc, 2);

        if (idv.size() > 1) {

            String errorMess = "More than 1 active shopping catalog for the site. Site id: " + pSiteId;

            throw new Exception(errorMess);

        }

        if (idv.size() == 0) {

            String errorMess = "No active shopping catalog for the site. Site id: " + pSiteId;

            throw new DataNotFoundException(errorMess);

        }



        return ((Integer)idv.get(0)).intValue();

    }



    /**

     *Returns the catalog id for the supplied site.  If 0 or more than 1 exist an exception is throwm (0 throws a DataNotFoundException).

     */

    public int getShoppigCatalogIdForSite(int pSiteId) throws RemoteException, DataNotFoundException{

    	Connection con=null;

        try{

            con = getConnection();

            return getShoppigCatalogIdForSite(pSiteId,con);

        }catch(DataNotFoundException de){

        	throw de;

        }catch(Exception e){

            throw new RemoteException("getShoppigCatalogIdForSite - " + e);

        }finally{

            closeConnection(con);

        }

    }



    /**

     *  Gets the majorDist attribute of the SiteBean object

     *

     *@param  pCon           Description of the Parameter

     *@param  pSiteId        Description of the Parameter

     *@return                The majorDist value

     *@exception  Exception  Description of the Exception

     */

    private int getMajorDist(Connection pCon, int pSiteId) throws Exception {

        int catalogId = getShoppigCatalogIdForSite(pSiteId,pCon);



        String sql = "select s.bus_entity_id, count(cs.item_id) rank  " +

                " from clw_catalog_structure cs, clw_bus_entity s " +

                " where catalog_id = " + catalogId +

                " and s.bus_entity_id = cs.bus_entity_id " +

                " group by s.bus_entity_id " +

                " order by rank desc ";



        Statement stmt = pCon.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        int distId = 0;

        if (rs.next()) {

            distId = rs.getInt("bus_entity_id");

        }

        if (distId == 0) {

            String errorMess =

                    "No distributor for the site. Site id: " + pSiteId + " Catalog id: " + catalogId;

            throw new Exception(errorMess);

        }

        return distId;

    }





    /**

     *  Description of the Method

     *

     *@param  pScheduleOrderDatesVector  Description of the Parameter

     *@return                            Description of the Return Value

     */

    private ArrayList sortScheduleOrderDates(ArrayList pScheduleOrderDatesVector) {

        if (pScheduleOrderDatesVector == null ||

                pScheduleOrderDatesVector.size() <= 1) {

            return pScheduleOrderDatesVector;

        }

        Object[] scheduleOrderDatesA = pScheduleOrderDatesVector.toArray();

        for (int ii = 0; ii < scheduleOrderDatesA.length - 1; ii++) {

            boolean endFl = true;

            for (int jj = 0; jj < scheduleOrderDatesA.length - ii - 1; jj++) {

                ScheduleOrderDates sod1 = (ScheduleOrderDates) scheduleOrderDatesA[jj];

                ScheduleOrderDates sod2 = (ScheduleOrderDates) scheduleOrderDatesA[jj + 1];

                Date dd1 = sod1.getNextOrderDeliveryDate();

                Date dd2 = sod2.getNextOrderDeliveryDate();

                if (dd1.after(dd2)) {

                    endFl = false;

                    scheduleOrderDatesA[jj] = sod2;

                    scheduleOrderDatesA[jj + 1] = sod1;

                }

            }

            if (endFl) {

                break;

            }

        }

        pScheduleOrderDatesVector.clear();

        for (int ii = 0; ii < scheduleOrderDatesA.length; ii++) {

            pScheduleOrderDatesVector.add(scheduleOrderDatesA[ii]);


            //   ((ScheduleOrderDates) scheduleOrderDatesA[ii]).getNextOrderDeliveryDate());

        }

        return pScheduleOrderDatesVector;

    }



    //******************************************************************************



    /**

     *  Removes records from clw_fedstrip058 placed by the USPS fedstrip file

     *  processing

     *

     *@param  pFileName            the fedstrip file name

     *@exception  RemoteException  if an error occurs

     */

    public void cleanFedstrip058(String pFileName)

             throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(Fedstrip058DataAccess.FILE_NAME, pFileName);

            Fedstrip058DataAccess.remove(conn, dbc);

            return;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("cleanFedstrip058: " + e.getMessage());

        } finally {

            try {

                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }



    }





    /**

     *  Adds record to clw_fedstrip058 also checks errors

     *

     *@param  pFedstrip            The feature to be added to the Fedstrip058

     *      attribute

     *@param  pUser                The feature to be added to the Fedstrip058

     *      attribute

     *@return                      sourse Fedstrip058Data object with id and

     *      error message

     *@exception  RemoteException  if an error occurs

     */

    public Fedstrip058Data addFedstrip058(Fedstrip058Data pFedstrip, String pUser)

             throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            pFedstrip.setAddBy(pUser);

            pFedstrip.setModBy(pUser);

            String changeCode = pFedstrip.getChangeCode();

            String errorMess = "";

            String errorCode = "";

            if (!"C".equals(changeCode) && !"A".equals(changeCode) && !"D".equals(changeCode)) {

                errorMess += "Unknown change code: " + changeCode + ".";

                errorCode = "E";

            }

            String addressType = pFedstrip.getAddressTypeCode();

            if (!"MAILING".equalsIgnoreCase(addressType) &&

                    !"FREIGHT".equalsIgnoreCase(addressType) &&

                    !"BILLING".equalsIgnoreCase(addressType)) {

                errorMess += "Unknown address type: " + addressType + ".";

                errorCode = "E";

            }

            String date = pFedstrip.getDatedChanged();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            try {

                sdf.parse(date);

            } catch (Exception exc) {

                errorMess += "Wrong date of change format: " + date + ".";

                errorCode = "E";

            }

            if ("C".equals(changeCode) || "D".equals(changeCode)) {

                DBCriteria dbc = new DBCriteria();

                dbc.addEqualTo(Fedstrip058DataAccess.FEDSTRIP, pFedstrip.getFedstrip());

                dbc.addEqualTo(Fedstrip058DataAccess.ADDRESS_TYPE_CODE, addressType);

                dbc.addOrderBy(Fedstrip058DataAccess.FEDSTRIP_058_ID);

                Fedstrip058DataVector f058DV = Fedstrip058DataAccess.select(conn, dbc);

                boolean notExistFl = false;

                if (f058DV.size() == 0) {

                    notExistFl = true;

                } else {

                    Fedstrip058Data fd = (Fedstrip058Data) f058DV.get(f058DV.size() - 1);

                    if ("D".equals(fd.getChangeCode())) {

                        notExistFl = true;

                    }

                }

                if (notExistFl) {

                    errorMess += " " + addressType + " address does not exist for fedstrip "

                             + pFedstrip.getFedstrip() + ".";

                    errorCode = "E";

                }

            }

            if (errorCode.length() > 0) {

                pFedstrip.setErrorCode(errorCode);

                if (errorMess.length() > 255) {

                    errorMess = errorMess.substring(0, 255);

                }

                pFedstrip.setErrorMessage(errorMess);

            }

            pFedstrip = Fedstrip058DataAccess.insert(conn, pFedstrip);

            return pFedstrip;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("cleanFedstrip058: " + e.getMessage());

        } finally {

            try {

                if (conn != null) {

                    conn.close();

                }

            } catch (Exception ex) {

            }

        }

    }





    private BusEntityDAO mBDAO = new BusEntityDAO();





    /**

     *  Description of the Method

     *

     *@param  pCon              Description of the Parameter

     *@param  pInventory        Description of the Parameter

     *@exception  SQLException  Description of the Exception

     */

    private void storeInventoryOnhand(Connection pCon,

            SiteInventoryInfoView pInventory, String pUserName)

             throws SQLException {



        storeInventoryOnhand(pCon, pInventory, false, pUserName);

    }



    private void storeInventoryOnhand(Connection pCon,

            SiteInventoryInfoView pInventory,

            boolean pOnlyUpdateIfOnHandIsNull,

            String pUserName)

             throws SQLException {



        boolean mkAuditEntry = false;

        String auditMsgKey = "shoppingMessages.text.onHandQtySet";//On hand quantity set to {0}.

        String arg0 = "";

        String arg1 = null;

        String arg2 = null;



        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID,

                pInventory.getSiteId());

        dbc.addEqualTo(InventoryLevelDataAccess.ITEM_ID,

                pInventory.getItemId());



        InventoryLevelDataVector ildv =

                InventoryLevelDataAccess.select(pCon, dbc);



        if (ildv == null || ildv.size() == 0) {

            InventoryLevelData newild = InventoryLevelData.createValue();

            newild.setBusEntityId(pInventory.getSiteId());

            newild.setQtyOnHand(pInventory.getQtyOnHand());

            newild.setItemId(pInventory.getItemId());

            newild.setAddBy(pUserName);

            newild.setModBy(pUserName);

            InventoryLevelDataAccess.insert(pCon, newild);

            if (pInventory.getParValue() > 0) {

                mkAuditEntry = true;

            }

        } else {

            for (int i1 = 0; ildv != null && i1 < ildv.size(); i1++) {

                InventoryLevelData ild = (InventoryLevelData) ildv.get(i1);

                if (ild.getItemId() == pInventory.getItemId() &&

                    ild.getBusEntityId() == pInventory.getSiteId()) {

                    if ( pOnlyUpdateIfOnHandIsNull == true && ild.getQtyOnHand() != null ) {

                        return;

                    }



                    if (ild.getQtyOnHand() != pInventory.getQtyOnHand()) {

                        ild.setQtyOnHand(pInventory.getQtyOnHand());

                        ild.setModBy(pUserName);

                        InventoryLevelDataAccess.update(pCon, ild);



                        if (pInventory.getParValue() > 0 ||

                           (pInventory.getQtyOnHand() != null &&

                            pInventory.getQtyOnHand().trim().length() > 0)

                        ) {

                           mkAuditEntry = true;

                        }

                    }

                }

            }

        }



        // Update any shopping cart entry for this item.

        int finalqty =

            pInventory.getParValue() - Integer.parseInt(pInventory.getQtyOnHand());

        if ( finalqty < 0 ) finalqty = 0;

        /*

        // !!! YK We do not care about distributor at that point

        if ( null != pInventory.getProductData() ) {

        // Get the distributor mapping for this item.

        ItemMappingData imd = pInventory.getProductData().getCatalogDistrMapping();

        if (   imd != null

            && imd.getUomConvMultiplier() != null

            && imd.getUomConvMultiplier().intValue() != 0 ) {



            BigDecimal qtyOnHand = new BigDecimal(pInventory.getQtyOnHand());

            qtyOnHand = qtyOnHand.divide(imd.getUomConvMultiplier(),

            0, BigDecimal.ROUND_DOWN);

            int iqtyonhand = qtyOnHand.intValue();

            finalqty = pInventory.getParValue() - iqtyonhand;

            if (imd.getUomConvMultiplier().intValue() != 1 && iqtyonhand > 0) {

                auditMsgKey = "shoppingMessages.text.onHandQtySetItemShipped"; //On hand quantity set to {0}. Item shipped in pack of {1} order quantity adjusted to {2}.

                arg0 = "" + iqtyonhand;

                arg1 = imd.getItemPack();

                arg2 = "" + finalqty;

                //auditMsg = "Item shipped in pack of "

                //+ imd.getItemPack() + " order quantity adjusted to "

                //+ finalqty + ".";

            }

            else {

                if ( finalqty > 0 ) {

                   auditMsgKey = "shoppingMessages.text.onHandQtySetItemAdded"; //On hand quantity set to {0}.  Item added with a quantity of {1}.

                   arg0 = "" + iqtyonhand;

                   arg1 = "" + finalqty;

                   // auditMsg = " Item added with a quantity of "

                   // + finalqty + ".";

                }

            }

        }

        else {



            if ( finalqty > 0 ) {

               auditMsgKey = "shoppingMessages.text.onHandQtySetItemAdded"; //On hand quantity set to {0}.  Item added with a quantity of {1}.

               arg0 = "" + pInventory.getQtyOnHand();

               arg1 = "" + finalqty;

               // auditMsg = " Item added with a quantity of "

               // + finalqty + ".";

            }

        }



        }

        else {

        */

         //   if ( finalqty > 0 ) {

               auditMsgKey = "shoppingMessages.text.onHandQtySetItemSet"; //On hand quantity set to {0}. Order item quantity set to {1}.

               arg0 = "" + pInventory.getQtyOnHand();

               arg1 = "" + finalqty;

         //   }

        //}





        OrderGuideData sc = ShoppingDAO.getCart ( pCon,  0, pInventory.getSiteId());

        OrderGuideStructureData ogsd =

            ShoppingDAO.setCartItemQty(pCon, sc, pInventory.getItemId(), finalqty);

        if ( mkAuditEntry ) {

            ShoppingDAO.enterInventoryAuditEntry

            (pCon, pInventory.getSiteId(),sc.getOrderGuideId(), pInventory.getItemId(),

            pInventory.getParValue(),

            auditMsgKey, arg0, arg1, arg2,

            pInventory.getUpdateUser());

        }



  logInfo

    ( " mkAuditEntry=" + mkAuditEntry +

      " \npInventory.getSiteId()=" + pInventory.getSiteId() +

      " \npInventory.getItemId()=" + pInventory.getItemId() +

      " \npInventory.getParValue()=" + pInventory.getParValue() +

      " \npInventory.getQtyOnHand()=" + pInventory.getQtyOnHand() +

      " \npInventory.getUpdateUser()=" + pInventory.getUpdateUser() +

      " \nauditMsgKey=" + auditMsgKey +

      " \ncart entry is now=" + ogsd

      );

    }





    /**
     *  Gets the inventorySiteCollection attribute of the SiteBean object
     *
     *@return                      The inventorySiteCollection value
     *@exception  java.rmi.RemoteException  Description of the Exception
     */
    public IdVector getInventorySiteCollection() throws RemoteException {
        return getInventorySiteCollection(false, false);
    }

    public IdVector getCorpInventorySiteCollection() throws RemoteException {
        return getInventorySiteCollection(true, true);
    }
    
    public IdVector getInventorySiteCollection(boolean modernFl)
    throws RemoteException {
    	return getInventorySiteCollection(modernFl, false);
    }
    
    private IdVector getInventorySiteCollection(boolean modernFl, boolean isCorporateInventory)
            throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            IdVector siteIds = new IdVector();
            String sql;

            if (isCorporateInventory){
            	sql = "SELECT distinct site.BUS_ENTITY_ID " +
            		" FROM CLW_BUS_ENTITY site, CLW_PROPERTY inv_prop  " +
                    " WHERE site.BUS_ENTITY_ID = inv_prop.BUS_ENTITY_ID " +
                    " AND site.BUS_ENTITY_TYPE_CD = 'SITE' " +
                    " AND site.BUS_ENTITY_STATUS_CD = 'ACTIVE' " +
                    " AND inv_prop.PROPERTY_TYPE_CD = 'ALLOW_CORPORATE_SCHED_ORDER' " +
                    " AND inv_prop.CLW_VALUE = 'true' " +
                    " AND exists(select VALUE from clw_schedule s, clw_schedule_detail sd " +
                    "	where s.schedule_id=sd.schedule_id  and s.schedule_status_cd='ACTIVE' " +
                    "	and sd.schedule_detail_cd = 'SITE_ID' AND sd.VALUE = TO_CHAR(site.BUS_ENTITY_ID)) " +
                " order by bus_entity_id";
            }else if (modernFl) {
                sql = "SELECT inv_prop.BUS_ENTITY_ID " +
                        " FROM CLW_PROPERTY inv_prop, CLW_PROPERTY inv_prop_type " +
                        " WHERE inv_prop.PROPERTY_TYPE_CD = 'INVENTORY_SHOPPING' " +
                        " AND inv_prop.CLW_VALUE = 'on' " +
                        " AND inv_prop_type.PROPERTY_TYPE_CD = 'INVENTORY_SHOPPING_TYPE' " +
                        " AND inv_prop_type.CLW_VALUE = 'on' " +
                        " AND inv_prop.BUS_ENTITY_ID = inv_prop_type.BUS_ENTITY_ID " +
                        " order by bus_entity_id";
            } else {
                sql = "SELECT inv_prop.BUS_ENTITY_ID" +
                        " FROM CLW_PROPERTY inv_prop, CLW_PROPERTY inv_prop_type, CLW_BUS_ENTITY site " +
                        " WHERE inv_prop.PROPERTY_TYPE_CD = 'INVENTORY_SHOPPING' " +
                        " AND inv_prop.CLW_VALUE = 'on' " +
                        " AND inv_prop_type.PROPERTY_TYPE_CD(+) = 'INVENTORY_SHOPPING_TYPE' " +
                        " AND nvl(inv_prop_type.CLW_VALUE,'off') != 'on' " +
                        " AND inv_prop.BUS_ENTITY_ID =inv_prop_type.BUS_ENTITY_ID (+) and inv_prop.BUS_ENTITY_ID = site.BUS_ENTITY_ID and site.bus_entity_status_cd = 'ACTIVE' " +
                        "order by bus_entity_id";
           }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int siteId = rs.getInt(1);
                siteIds.add(new Integer(siteId));
            }
            rs.close();
            stmt.close();
            return siteIds;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getInventorySiteCollection: "
                    + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }





    /**

     *  Description of the Class

     *

     *@author     Dvieira

     *@created    September 3, 2004

     */

    private class InventoryMgr {

        Connection mCon = null;

        SiteData mSite = null;

        UserData userData = null;

        ShoppingServices scartBean = null;





        /**

         *  Constructor for the InventoryMgr object

         *

         *@param  pCon   Description of the Parameter

         *@param  pSite  Description of the Parameter

         */

        public InventoryMgr(Connection pCon, SiteData pSite) {

            mCon = pCon;

            mSite = pSite;

            userData = UserData.createValue();

            userData.setUserName(kInvUser);

            String t = "^" + Constants.UserRole.CONTRACT_ITEMS_ONLY;

            userData.setUserRoleCd(t);

            try {

                scartBean =

                        APIAccess.getAPIAccess().getShoppingServicesAPI();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }





        /**

         *  Description of the Method

         *

         *@return                            Description of the Return Value

         *@exception  RemoteException        Description of the Exception

         *@exception  DataNotFoundException  Description of the Exception

         *@exception  Exception              Description of the Exception

         */

        public boolean hasAutoOrderItems()

        throws RemoteException, DataNotFoundException, Exception

        {

            return mSite.hasInventoryAutoOrderItem();

        }



        public boolean setupAutoOrder()

        throws RemoteException, DataNotFoundException, Exception

        {

            String invuser = "inv_auto_order";

            ArrayList siteInvConfVwV = mSite.getSiteInventory();

            if(siteInvConfVwV != null) {

                for (Iterator iter=siteInvConfVwV.iterator(); iter.hasNext();) {

                    SiteInventoryInfoView siiv = (SiteInventoryInfoView) iter.next();

                    if (siiv.getAutoOrderItem() != null &&

                        siiv.getAutoOrderItem().equalsIgnoreCase("Y") ) {

                            BigDecimal calonh = new BigDecimal(siiv.getParValue());

                            if ((calonh.intValue() > 0 ) ) {

                                // For those locations setup for monthly

                                // orders, set the in hand value of the item

                                // to half the par value.  This will result

                                // in a half sized order for inventory items.

                                // This was a JCP request.

                                if ( mSite.isSetupForMonthlyOrder() ) {

                                    calonh = calonh.divide(new BigDecimal(2),

                                    BigDecimal.ROUND_DOWN);

                                } else {

                                    // This is a site set up for orders for

                                    // a specified weekly interval.  If the on hand

                                    // value is not set, then set it to zero to

                                    // force an order for the par value configured

                                    // for the current order cycle.

                                    calonh = new BigDecimal(0);

                                }

                            }

                                siiv.setQtyOnHand(calonh.toString());

                        } else {

                            siiv.setQtyOnHand(String.valueOf(siiv.getParValue()));

                        }

                    siiv.setUpdateUser(invuser);

                    storeInventoryOnhand(mCon, siiv, true, invuser);

                }

            }

      // These are items that are no longer

      // in the inventory program.

            DBCriteria dbc1 = new DBCriteria();

            dbc1.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID,

                    mSite.getBusEntity().getBusEntityId());

            dbc1.addIsNull(InventoryLevelDataAccess.QTY_ON_HAND);

      InventoryLevelDataAccess.remove(mCon, dbc1);





            logDebug("DONE  setupAutoOrder for siteid=" + mSite.getSiteId());

            return true;

        }





        /**

         *  Description of the Method

         *

         *@return                            Description of the Return Value

         *@exception  RemoteException        Description of the Exception

         *@exception  DataNotFoundException  Description of the Exception

         *@exception  Exception              Description of the Exception

         */

        public boolean checkOnHandValues()

                 throws RemoteException, DataNotFoundException, Exception {

            DBCriteria dbc1 = new DBCriteria();

            dbc1.addEqualTo(ContractItemDataAccess.CONTRACT_ID,

                    mSite.getContractData().getContractId()

                    );

            IdVector contractItems = ContractItemDataAccess.selectIdOnly

                    (mCon, ContractItemDataAccess.ITEM_ID, dbc1);

            if (contractItems == null || contractItems.size() == 0) {

                logInfo("SiteBean.InventoryMgr.checkOnHandValues. No contract items found"

                         + " for site=" + mSite.getSiteId());

            }



            dbc1 = new DBCriteria();

            dbc1.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID,

                    mSite.getBusEntity().getBusEntityId());

            dbc1.addIsNull(InventoryLevelDataAccess.QTY_ON_HAND);

            if (contractItems != null && contractItems.size() > 0) {

                dbc1.addOneOf(InventoryLevelDataAccess.ITEM_ID, contractItems);

            }



            IdVector invItems = InventoryLevelDataAccess.selectIdOnly

                    (mCon, InventoryLevelDataAccess.ITEM_ID, dbc1);

            if (invItems.size() == 0) {

                return true;

            }



            String msg =

                    " SiteBean.InventoryMgr.checkOnHandValues." +

                    " On hand values not set for inventory items ("

                     + IdVector.toCommaString(invItems)

                     + ") for site=" + mSite.getSiteId();

            logInfo(msg);

            return false;

        }





        /**

         *  Gets the inventoryCart attribute of the InventoryMgr object

         *

         *@return                      The inventoryCart value

         *@exception  RemoteException  Description of the Exception

         */

        private ShoppingCartData getInventoryCart()

                 throws RemoteException {

            return scartBean.getShoppingCart

                    (null,

                    userData,

                    mSite,

                    mSite.getContractData().getCatalogId(),

                    mSite.getContractData().getContractId()

                    );

        }



        private boolean canOrderBePlaced()

                 throws RemoteException, DataNotFoundException, Exception {



            // Get the current cart

            ShoppingCartData sd = getInventoryCart();

            if (sd == null) {

                logError(" canOrderBePlaced, No shoppingcart " +

       " available for inventory order for site=" + mSite);

                return false;

            }



            java.util.List cartItems = sd.getItems();

            // See if there are any non-inventory items in the cart.

            for (int ii = 0; ii < cartItems.size(); ii++) {

                ShoppingCartItemData cartItem =

                        (ShoppingCartItemData) cartItems.get(ii);

                if (cartItem.getIsaInventoryItem() == false) {

                    // This is a non-inventory item.

                    // Place the order.

        logDebug("canOrderBePlaced, non inventory item in cart.");

                    return true;

                }

            }



            // All items in the cart are inventory items.

            // Check to see if an order has been placed for this location

            // in the last 7 days.  If so, do not place another order.

            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(OrderDataAccess.SITE_ID, mSite.getSiteId());

            dbc.addCondition( " add_date >= sysdate - 7 " );

            OrderDataVector odv = OrderDataAccess.select(mCon, dbc);

            if ( null != odv && odv.size() > 0 ) {

                logInfo( " canOrderBePlaced, siteId=" +

       mSite.getSiteId() + " has placed "

       + odv.size() + " order(s) in the last 7 days.  "

       + " No order placed." );

                return false;

            }



            logDebug ("  canOrderBePlaced, siteId=" +

          mSite.getSiteId() +

          " is ready to place an inventory order");

            return true;

        }



        /**

         *  Description of the Method

         *

         *@return                            Description of the Return Value

         *@exception  RemoteException        Description of the Exception

         *@exception  DataNotFoundException  Description of the Exception

         *@exception  Exception              Description of the Exception

         */

        public ProcessOrderResultData placeOrder()

                 throws RemoteException, DataNotFoundException, Exception {



            // Get the current cart

            ShoppingCartData sd = getInventoryCart();

            if (sd == null) {

                logError(" No shoppingcart available for inventory order for site=" + mSite);

                return null;

            }



            java.util.List cartItems = sd.getItems();

            BigDecimal freightAmt = new BigDecimal(0);

            BigDecimal

            handlingAmt = new BigDecimal(0);



            ProcessOrderResultData orderRes =

            ProcessOrderResultData.createValue();



            if (canOrderBePlaced() == false) {

                String msg = "site is not ready for the next order";

                logInfo ( " siteId=" + mSite.getSiteId() + " " + msg);

                orderRes.setOrderNum(msg);

                orderRes.setOrderStatusCd("NO_ORDER_PLACED");

                orderRes.setOrderId(-11);

                return orderRes;

            }



            //Freight

            OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

            for (int ii = 0; ii < cartItems.size(); ii++) {

                ShoppingCartItemData cartItem = (ShoppingCartItemData)

                        cartItems.get(ii);

                OrderHandlingItemView frItem = OrderHandlingItemView.createValue();

                frItem.setItemId(cartItem.getProduct().getProductId());

                BigDecimal priceBD = new BigDecimal(cartItem.getPrice());

                priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);

                frItem.setPrice(priceBD);

                frItem.setQty(cartItem.getQuantity());

                BigDecimal weight = null;

                String weightS = cartItem.getProduct().getShipWeight();

                try {

                    weight = new BigDecimal(weightS);

                } catch (Exception exc) {}

                frItem.setWeight(weight);

                frItems.add(frItem);

            }

            OrderHandlingView frOrder = OrderHandlingView.createValue();

            frOrder.setTotalHandling(new BigDecimal(0));

            frOrder.setTotalFreight(new BigDecimal(0));

            frOrder.setContractId(mSite.getContractData().getContractId());

            frOrder.setAccountId(mSite.getAccountId());

            frOrder.setSiteId(mSite.getSiteId());

            frOrder.setWeight(new BigDecimal(0));

            frOrder.setItems(frItems);



            try {

                frOrder =

                        scartBean.calcTotalFreightAndHandlingAmount(frOrder);

                freightAmt = frOrder.getTotalFreight();

                handlingAmt = frOrder.getTotalHandling();

            } catch (RemoteException exc) {

                exc.printStackTrace();

            }

            freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);

            handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);



            // Construct an order request.

            CustomerOrderRequestData orderReq =

                    new CustomerOrderRequestData();



            orderReq.setContractId

                    (mSite.getContractData().getContractId());

            orderReq.setUserName(kInvUser);

            orderReq.setSiteId(mSite.getSiteId());

            orderReq.setAccountId(mSite.getAccountId());

            orderReq.setOrderSourceCd(RefCodeNames.SYS_ORDER_SOURCE_CD.INVENTORY);

            orderReq.setFreightCharge(freightAmt.doubleValue());

            orderReq.setHandlingCharge(handlingAmt.doubleValue());


            for (int ii = 0; ii < cartItems.size(); ii++) {

                ShoppingCartItemData cartItem =

                        (ShoppingCartItemData) cartItems.get(ii);

                int itemId = cartItem.getProduct().getItemData().getItemId();

                int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();

                if (cartItem.getIsaInventoryItem()) {

                    String t2 = cartItem.getInventoryQtyOnHandString();

                    if ((t2 == null || t2.length() == 0)

                             && cartItem.getInventoryParValue() > 0) {

                        // This item has been designated as an

                        // inventory item for this site, but

                        // the inventory on hand value has not

                        // been updated.

                        String msg =

          " On Hand value not set for inventory item: "

          + cartItem.description();

                        logInfo(msg);

                        orderReq.addOrderNote(msg);

                    }

                }



                int qty = cartItem.getQuantity();

                if (qty == 0) {

                    continue;

                }

                // 0 qty line



                double price = cartItem.getPrice();



                orderReq.addItemEntry(ii + 1, itemId, clw_skunum,

                        qty, price,

                        cartItem.getProduct().getUom(),

                        cartItem.getProduct().getShortDesc(),

                        cartItem.getProduct().getPack(),

                        cartItem.getIsaInventoryItem(),

                        cartItem.getInventoryParValue(),

                        cartItem.getInventoryQtyOnHandString(),

                        cartItem.getReSaleItem());

           }



            // Place an order.

            IntegrationServices isvcEjb =

                    APIAccess.getAPIAccess().getIntegrationServicesAPI();

            if (orderReq.getEntriesCollection().size() == 0) {

                logError("Cannot process empty order.");

                orderRes.setOrderNum("empty order");

                orderRes.setOrderStatusCd("NO_ORDER_PLACED");

                // The cart is intentionally empty.

                orderRes.setOrderId(-10);

                return orderRes;

            }



            orderRes = isvcEjb.processOrderRequest(orderReq);



            // Now clear out the cart for the site.

            sd.setItems(null);

            boolean resetInventoryFields = true;

            scartBean.saveShoppingCart

                    (sd, mSite.getContractData().getCatalogId(),

                    userData.getUserName(), orderRes, null);





      // Reset the inventory information.

      ShoppingDAO.resetOnHandValues(mCon, mSite.getSiteId());



            return orderRes;

        }

    }





    private String kInvUser = "inv_order";





    /**

     *  Description of the Method

     *

     *@param  pSite                      Description of the Parameter

     *@param  pRunForDate                Description of the Parameter

     *@return                            Description of the Return Value

     *@exception  RemoteException        Description of the Exception

     *@exception  DataNotFoundException  Description of the Exception

     */

    private String checkInventoryCart(SiteData pSite, java.util.Date pRunForDate)

             throws RemoteException, DataNotFoundException {



        int siteId = pSite.getBusEntity().getBusEntityId();



        String res = "checkInventoryCart for pSiteId="

                 + siteId

                 + " name= " + pSite.getBusEntity().getShortDesc();



        Connection conn = null;

        try {

            conn = getConnection();



            DBCriteria critH = new DBCriteria();

            String ordTab = OrderDataAccess.CLW_ORDER;

            String ordPropTab = OrderPropertyDataAccess.CLW_ORDER_PROPERTY;

            critH.addJoinCondition(ordTab,OrderDataAccess.ORDER_ID,ordPropTab,OrderPropertyDataAccess.ORDER_ID);

            critH.addJoinTableEqualTo(ordTab,OrderDataAccess.SITE_ID,siteId);

            critH.addJoinTableEqualTo(ordTab,OrderDataAccess.ORDER_STATUS_CD,RefCodeNames.ORDER_STATUS_CD.WAITING_FOR_ACTION);

            critH.addJoinTableEqualTo(ordPropTab,OrderPropertyDataAccess.SHORT_DESC,RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVENTORY_ORDER_HOLD);



            OrderDataVector ordersToProcess = new OrderDataVector();

            JoinDataAccess.selectTableInto(new OrderDataAccess(),ordersToProcess,conn,critH,0);

          Date today = Utility.truncateDateByDay(new Date());


            if(ordersToProcess != null){

              Iterator it = ordersToProcess.iterator();

              while(it.hasNext()){

                OrderData ord = (OrderData) it.next();

                critH = new DBCriteria();

                critH.addEqualTo(InventoryOrderLogDataAccess.ORDER_ID,ord.getOrderId());

                InventoryOrderLogDataVector invDLV = InventoryOrderLogDataAccess.select(conn,critH);

                if(invDLV.isEmpty()){

                   throw new RemoteException("no inventory order log entry for order id: "+ord.getOrderId());

                }else{

                  InventoryOrderLogData invDL = (InventoryOrderLogData) invDLV.get(0);

                  if(today.compareTo(invDL.getOrderDeliveryDate()) >= 0){

                    ord.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED);

                    OrderDataAccess.update(conn,ord);

                  }else{

                    log.info(today +"SiteBean.checkInventoryCart. " +

                        "Is before delivery date: "+invDL.getOrderDeliveryDate());

                  }

                }

              }

            }





            if (pSite.getContractData() == null ||

                    pSite.getContractData().getContractId() <= 0) {

                res += " [ NO CONTRACT FOUND ] ";

                return res;

            }



            ScheduleOrderDates sod =

                calculateNextOrderDates(conn, siteId, pSite.getAccountId(), 0);



            if (sod != null) {

                // Scheduled order dates configured.

                pSite.setNextDeliveryDate(sod.getNextOrderDeliveryDate());

                pSite.setNextOrdercutoffDate(sod.getNextOrderCutoffDate());

            }



            Date nextOrderCutoff = pSite.getNextOrdercutoffDate();

            Date nextDeliveryDate = pSite.getNextDeliveryDate();

            if (null == nextOrderCutoff) {

                res += " [ NO ORDER CUTOFF DEFINED ] ";

                return res;

            }

            if (null == nextDeliveryDate) {

                res += " [ NO ORDER DELIVERY DATE DEFINED ] ";

                return res;

            }



            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(InventoryOrderLogDataAccess.SITE_ID,siteId);

            dbc.addEqualTo(InventoryOrderLogDataAccess.ORDER_CUTOFF_DATE,

                    nextOrderCutoff);



            InventoryOrderLogDataVector ioldv =

                    InventoryOrderLogDataAccess.select(conn, dbc);

            // Check the queue for an order entry for this site.

            if (ioldv.size() == 0) {

                // If no order entry is found for this cutoff,

                // delete any entries that are pending and add

                // an entry for this date.



                dbc = new DBCriteria();

                dbc.addEqualTo(InventoryOrderLogDataAccess.SITE_ID, siteId);

                dbc.addEqualTo(InventoryOrderLogDataAccess.ORDER_ID, 0);

                dbc.addCondition

                        (InventoryOrderLogDataAccess.ORDER_CUTOFF_DATE + " > sysdate ");

                InventoryOrderLogDataAccess.remove(conn, dbc);



                InventoryOrderLogData iolD =

                        InventoryOrderLogData.createValue();

                iolD.setAddBy(kInvUser);

                iolD.setSiteId(siteId);

                iolD.setOrderCutoffDate(nextOrderCutoff);

                iolD.setOrderDeliveryDate(nextDeliveryDate);

                InventoryOrderLogDataAccess.insert(conn, iolD);

                res += " added an entry for OrderCutoff="

                         + nextOrderCutoff

                         + " DeliveryDate=" + nextOrderCutoff;

                return res;

            }



            InventoryMgr invmgr = new InventoryMgr(conn, pSite);

            boolean hasAutoOrderItems = invmgr.hasAutoOrderItems();



            dbc = new DBCriteria();

            dbc.addEqualTo(InventoryOrderLogDataAccess.SITE_ID, siteId);

            dbc.addEqualTo(InventoryOrderLogDataAccess.ORDER_CUTOFF_DATE,

                    nextOrderCutoff);

            dbc.addEqualTo(InventoryOrderLogDataAccess.ORDER_ID, 0);

            boolean orderAscending = true;

            dbc.addOrderBy(InventoryOrderLogDataAccess.ORDER_CUTOFF_DATE

            , orderAscending);





            ioldv = InventoryOrderLogDataAccess.select(conn, dbc);

            GregorianCalendar runForDateCal = new GregorianCalendar();

            if (ioldv.size() > 0) {

                InventoryOrderLogData iold = (InventoryOrderLogData) ioldv.get(0);

                if (invmgr.checkOnHandValues() == false) {

                    res += " checkOnHandValues failed: ";

                    // if the cutoff date is within 5 days,

                    // send a reminder email.

                    GregorianCalendar cutoffRem = new GregorianCalendar();

                    // if the cutoff date is within 5 days, (12 hrs before cut-off for xpedx)

                    // send a reminder email.



                    cutoffRem.setTime(nextOrderCutoff);

                    cutoffRem.add(Calendar.DATE, -5);

                    runForDateCal.setTime(pRunForDate);



                    if (runForDateCal.after(cutoffRem)) {

                        res += " [Inventory reminder email sent, no order placed.]\n\n ";

                        res += sendInventoryOrderEmail("On Hand Values Missing",

                                pSite, iold,conn);

                    } else {

                        res += " [No reminder sent, reminder is due > "

                        + cutoffRem.getTime() + " for this site.]"

                        + " Currently " + runForDateCal.getTime();

                    }



                    if (!hasAutoOrderItems) {

                        return res;

                    }

                }

            }



            // Process the cart for the previous cutoff entry

            // without an order id.

            dbc = new DBCriteria();

            dbc.addEqualTo(InventoryOrderLogDataAccess.SITE_ID,siteId);

            dbc.addLessThan(InventoryOrderLogDataAccess.ORDER_CUTOFF_DATE,

                   nextOrderCutoff); //pRunForDate debugging

            dbc.addEqualTo(InventoryOrderLogDataAccess.ORDER_ID, 0);

            ioldv = InventoryOrderLogDataAccess.select(conn, dbc);

            if (ioldv.size() == 0) {

                res += " no pending inventory orders"

                         + " nextOrderCutoff=" + nextOrderCutoff;

                return res;

            }



            InventoryOrderLogData iold = (InventoryOrderLogData) ioldv.get(0);



            GregorianCalendar ncutoff = new GregorianCalendar();

            ncutoff.setTime(iold.getOrderCutoffDate());



            //Check stuff in the shopping cart

            dbc = new DBCriteria();

            dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID,siteId);

            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,

                      RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);

            String ogIdS =

                OrderGuideDataAccess.getSqlSelectIdOnly(OrderGuideDataAccess.ORDER_GUIDE_ID,dbc);

            dbc = new DBCriteria();

            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,ogIdS);

            OrderGuideStructureDataVector ogStructDV =

                         OrderGuideStructureDataAccess.select(conn,dbc);

            if(ogStructDV.size()==0) {

                if (hasAutoOrderItems && invmgr.checkOnHandValues() == false) {

                    // This location has auto order and some onhand qty is null

                    // Set the on hand value(s).

                    if (runForDateCal.after(ncutoff)) {

                        res += "[On hand values missing.  Auto-order is on."

                        + "  Constructing the next order.";

                        invmgr.setupAutoOrder();

                    }

                    return res;

                } else if ( invmgr.checkOnHandValues() == false ){

                    res += " No items ready for order.";

                    return res;

                }

            }

            ProcessOrderResultData ores = invmgr.placeOrder();

            if (ores == null) {

                res += " [Inventory not set, no order placed]\n\n ";

                res += sendInventoryOrderEmail("On Hand Values Missing",

                        pSite, iold,conn);

                return res;

            }



            InventoryOrderLogData resInvData =(InventoryOrderLogData) ioldv.get(0);

            if(pSite.getInventoryShoppingHoldOrderUntilDeliveryDate()!=null && Utility.isTrue(pSite.getInventoryShoppingHoldOrderUntilDeliveryDate().getValue())){

              int orderId = ores.getOrderId();

            Date runForScratch = Utility.truncateDateByDay(runForDateCal.getTime());

            Date deliveryDate = Utility.truncateDateByDay(resInvData.getOrderDeliveryDate());

              if(orderId < 0 && runForScratch.compareTo(deliveryDate) < 0){

                orderId = 0;

              }

              resInvData.setOrderId(orderId);

            }else{

              resInvData.setOrderId(ores.getOrderId());

            }

            resInvData.setModBy(kInvUser);

            InventoryOrderLogDataAccess.update(conn, resInvData);

            res += "  :: Processed order in queue, OrderId=" + ores.toString();



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("checkInventoryCart: "

                     + e.getMessage());

        } finally {

            closeConnection(conn);

        }

        return res;

    }





    /**

     *  Description of the Method

     *

     *@param  pSite                      Description of the Parameter

     *@param  pRunForDate                Description of the Parameter

     *@return                            Description of the Return Value

     *@exception  RemoteException        Description of the Exception

     *@exception  DataNotFoundException  Description of the Exception

     */

    private String checkOldInventoryEntries(SiteData pSite,

            java.util.Date pRunForDate)

             throws RemoteException, DataNotFoundException {

        String res = "checkOldInventoryEntries for pSiteId="

                 + pSite.getBusEntity().getBusEntityId()

                 + " name= " + pSite.getBusEntity().getShortDesc();



        Connection conn = null;

        try {

            conn = getConnection();



            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(InventoryOrderLogDataAccess.SITE_ID,

                    pSite.getBusEntity().getBusEntityId()

                    );

            dbc.addCondition

                    (InventoryOrderLogDataAccess.ORDER_DELIVERY_DATE

                     + " < sysdate -1 ");

            dbc.addEqualTo(InventoryOrderLogDataAccess.ORDER_ID, 0);



            InventoryOrderLogDataVector ioldv =

                    InventoryOrderLogDataAccess.select(conn, dbc);

            if (ioldv == null || ioldv.size() == 0) {

                res += " no old entries.";

                return res;

            }



            InventoryOrderLogData iold =

                    (InventoryOrderLogData) ioldv.get(0);



            logInfo("Missed Delivery Date iold=" + iold);

            // This pending order has missed the order cycle.

            if (pSite.isActive()) {

                sendInventoryOrderEmail("Missed Delivery Date",

                        pSite, iold,conn);

            }

            iold.setOrderId(-2);

            iold.setModBy(kInvUser);

            InventoryOrderLogDataAccess.update(conn, iold);

            res += " order date missed, OrderCutoff="

                     + iold.getOrderCutoffDate()

                     + " DeliveryDate=" + iold.getOrderDeliveryDate();

            return res;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("checkOldInventoryEntries: "

                     + e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }





    /**

     *  Description of the Method

     *

     *@param  pSiteId                    Description of the Parameter

     *@param  pRunForDate                Description of the Parameter

     *@return                            Description of the Return Value

     *@exception  RemoteException        Description of the Exception

     *@exception  DataNotFoundException  Description of the Exception

     */

    public String placeInventoryOrder(int pSiteId, Date pRunForDate)

             throws RemoteException, DataNotFoundException {

        String res = "placeInventoryOrder: pSiteId="

                 + pSiteId + " pRunForDate=" + pRunForDate;

        SiteData sd = getSite(pSiteId, 0);

        if (!sd.isActive()) {

            res += " Site is not in an active status."

                     + " pSiteId=" + pSiteId;

        } else if (!sd.hasInventoryShoppingOn()) {

            res += " Inventory shopping is off for"

                     + " pSiteId=" + pSiteId;

        } else {

            res += " [ " + checkInventoryCart(sd, pRunForDate) + " ]";

        }



        // Check any old inventory order entries.

        res += " [ " + checkOldInventoryEntries(sd, pRunForDate) + " ]";

        logDebug(res);

        return res;

    }





    /**

     *  Description of the Method

     *

     *@param  pEmailType  Description of the Parameter

     *@param  pSite       Description of the Parameter

     *@param  pIold       Description of the Parameter

     *@return             Description of the Return Value

     */

    private String sendInventoryOrderEmail

            (String pEmailType, SiteData pSite, InventoryOrderLogData pIold,Connection pCon) {

        if (pEmailType.equals

                ("On Hand Values Missing")) {

            return sendInventoryMissingEmail

                    (pSite, pIold.getOrderCutoffDate(),pCon);

        } else if (pEmailType.equals

                ("Missed Delivery Date")) {

            return sendMissedDeliveryDateEmail

                    (pSite, pIold,pCon);

        }



        return "Unknown pEmailType=" + pEmailType;

    }





    /**

     *  Description of the Method

     *

     *@param  pSite  Description of the Parameter

     *@param  pIold  Description of the Parameter

     *@return        Description of the Return Value

     */

    private String sendMissedDeliveryDateEmail

            (SiteData pSite,

            InventoryOrderLogData pIold,Connection pCon) {



        APIAccess factory = null;

        EmailClient emailClientEjb = null;

        User userBean = null;



        try {

            factory = APIAccess.getAPIAccess();

            emailClientEjb = factory.getEmailClientAPI();

            userBean = factory.getUserAPI();



        } catch (Exception exc) {



            String mess = "sendMissedDeliveryDateEmail. No API access";

            logError(mess);



            return mess;

        }

        Date deliveryDate = pIold.getOrderDeliveryDate();

        Date

                cufoffDate = pIold.getOrderCutoffDate();

        try {

            int storeId = BusEntityDAO.getStoreForAccount(pCon,pSite.getAccountId());

            UserDataVector uv = userBean.getUsersCollectionByBusEntity

                    (pSite.getSiteId(), null);



            String lSubject = " -- "

                     + pSite.getBusEntity().getShortDesc()

                     + " Missed delivery date: " + deliveryDate;



            String[] args1 = {

                    pSite.getBusEntity().getShortDesc(),

                    cufoffDate.toString(),

                    deliveryDate.toString()

                    };





            String lMsg =

                    java.text.MessageFormat.format

                    (getProperty("mail.inventory_missed_date.msg.header",storeId,pCon),

                    args1)

                     + pSite.getInventorySummary() + "\n" +

                    getProperty("mail.msg.footer",storeId,pCon);



            String emailsSentMsg = "";

            for (int idx = 0; idx < uv.size(); idx++) {



                UserData ud = (UserData) uv.get(idx);

                if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(ud.getUserStatusCd())) {

                    continue;

                }

                UserInfoData u = userBean.getUserContact(ud.getUserId());

                String lToEmailAddress = u.getEmailData().getEmailAddress();



                String usub = UserInfoData.USER_GETS_EMAIL_ORDER_WAS_REJECTED;

                if (u.subscribesTo(usub, pSite.getAccountId()) == false) {



                    continue;

                }



                if (emailClientEjb.wasThisEmailSent

                        (lSubject, lToEmailAddress) == false) {

                    emailClientEjb.send(lToEmailAddress,

          emailClientEjb.getDefaultEmailAddress(),

          lSubject, lMsg,

                            Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, ud.getUserId());

                }



                emailsSentMsg += "\nMissedDeliveryDate "

                         + "\n[ Sent email to: " + lToEmailAddress +

                        "\n  Subject:" + lSubject

                         + "\n Username:" + ud.getUserName() + "]";

            }

            return emailsSentMsg;

        } catch (Exception exc) {



            String mess = "Site.sendMissedDeliveryDateEmail: " +

                    exc.getMessage();

            logError(mess);

        }

        return " No email sent.";

    }



    private String getProperty(String pName, int storeId,Connection pConn) {

            PropertyUtil pru = new PropertyUtil(pConn);

            String retVal;

            try{

                 retVal = pru.fetchValueIgnoreMissing(0,storeId, pName);

                 if(retVal != null){

                     StringBuffer retValBuf = new StringBuffer(retVal);

                     Utility.replaceString(retValBuf, "\\n","\n");

                     retVal = retValBuf.toString();

                 }

            }catch(Exception e){

                e.printStackTrace();

                return null;

            }



            return retVal;





    }



    /**

     * Returns modified email subject/msg property if set, else return empty string

     */

    public String getEmailProperty(AccountData accD, SiteData siteD, String propertyName) throws Exception{

    	String msg = "";

    	Connection conn=null;

    	try{

    		conn = getConnection();

    		if(propertyName.equals(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT)){

    			msg = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);



    		}else if(propertyName.equals(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG)){

    			msg = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);



    		}

    		if(msg!=null && msg.trim().length()>0){



    			int ind1 = msg.indexOf("{SITE}");

    			if(ind1 >0){

    				//substitute Site name

    				String siteName = siteD.getBusEntity().getShortDesc();

    				msg = msg.replaceAll("\\{SITE\\}", siteName);

    			}



    			int ind2 = msg.indexOf("{REF_NUM}");

    			if(ind2 >0){

    				//substitute Site Ref Num

    				PropertyData siteProp = siteD.getMiscProp(

    						RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);

    				if(siteProp!=null){

    					String siteRefNum = siteProp.getValue();

    					msg = msg.replaceAll("\\{REF_NUM\\}", siteRefNum);

    				}

    			}

    		}



    	} catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

        return msg;

    }



    /**

     *  Description of the Method

     *

     *@param  pSite         Description of the Parameter

     *@param  pOrderCutoff  Description of the Parameter

     *@return               Description of the Return Value

     */

    private String sendInventoryMissingEmail(SiteData pSite,

            Date pOrderCutoff,Connection pCon) {



        APIAccess factory = null;

        EmailClient emailClientEjb = null;

        User userBean = null;

        Account accBean = null;

        PropertyService propBean = null;

        boolean isEmailSubjNull = false;
        boolean isEmailMsgNull = false;

        try {

            factory = APIAccess.getAPIAccess();

            emailClientEjb = factory.getEmailClientAPI();

            userBean = factory.getUserAPI();

            accBean = factory.getAccountAPI();

            propBean = factory.getPropertyServiceAPI();



        } catch (Exception exc) {



            String mess = "sendInventoryMissingEmail. No API access";

            logError(mess);



            return mess;

        }



        try {

            int storeId = BusEntityDAO.getStoreForAccount(pCon,pSite.getAccountId());



            UserDataVector uv = userBean.getUsersCollectionByBusEntity

                    (pSite.getSiteId(), null);



            /*String lSubject = " -- "

                     + pSite.getBusEntity().getShortDesc()

                     + " Cutoff date: " + pOrderCutoff

                     + " On hand quantities required to place order";*/



            AccountData accD = accBean.getAccountForSite(pSite.getBusEntity().getBusEntityId());



            //String lSubject = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);

            String lSubject = getEmailProperty(accD, pSite,RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);

            if(lSubject==null || !(lSubject.trim().length()>0)){

            	isEmailSubjNull = true;

            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            String fD = sdf.format(pOrderCutoff);



            lSubject = lSubject + " Cut-off: "+fD;



            String[] args1 = {pSite.getBusEntity().getShortDesc(),

                    pOrderCutoff.toString()

                    };



            /*String lMsg =

                    java.text.MessageFormat.format

                    (getProperty("mail.inventory_missing.msg.header",storeId,pCon),

                    args1)

                     + pSite.getInventorySummary() + "\n" +

                    getProperty("mail.msg.footer",storeId,pCon);*/



            //String lMsg = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);

            String lMsg = getEmailProperty(accD, pSite,RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);
            
            if (!Utility.isSet(lMsg)) {
            	isEmailMsgNull = true;
            }
            
            //bug 4558 - if either the email subject or message is null, return a message
            //to that effect.
            if (isEmailSubjNull || isEmailMsgNull) {
            	StringBuilder builder = new StringBuilder(100);
            	builder.append("Site.sendInventoryMissingEmail: no messages sent because");
            	boolean includeAnd = false;
            	if (isEmailSubjNull) {
            		builder.append(" email subject is null");
            		includeAnd = true;
            	}
            	if (isEmailMsgNull) {
            		if (includeAnd) {
            			builder.append(" and");
            		}
            		builder.append(" email message is null");
            	}
            	builder.append(".");
            	return builder.toString();
            }

            //String siteName = pSite.getBusEntity().getShortDesc();

            //String actualMsg = lMsg +"\n"+"\n"+ "Site Name: " +siteName;

            String emailsSentMsg = "";



            /*

             * The check for order approver and order rejected notification should be removed.

             */

            for (int idx = 0; idx < uv.size(); idx++) {



                UserData ud = (UserData) uv.get(idx);



                UserInfoData u = userBean.getUserContact(ud.getUserId());

                String lToEmailAddress = u.getEmailData().getEmailAddress();

                if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(ud.getUserStatusCd())) {

                    continue;

                }

                // Only those users with the ability to place orders should

                // receive emails.

                UserRightsTool cwuser = new UserRightsTool

                        (ud, u.getRightsForAccount(pSite.getAccountId()));

                if (cwuser.canApprovePurchases() == true) {

                    continue;

                }

                String usub = UserInfoData.USER_GETS_EMAIL_ORDER_WAS_REJECTED;



                if (u.subscribesTo(usub, pSite.getAccountId()) == false) {

                    continue;

                }



                //if user is a corporate user - only set the property, do not sent this email

                String isCorporateUser = "false";

                try{

                	isCorporateUser =  propBean.getUserProperty(

                		ud.getUserId(), RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER);

                }catch (Exception e) {

                    logInfo("SiteBean => Property not found: " + e.getMessage());

                }

                if(isCorporateUser.equalsIgnoreCase("true")){

                	//set property to send email to corporate user for this site

                    int siteId = pSite.getBusEntity().getBusEntityId();



                    if(!(isEmailSubjNull) || lMsg!=null){

                    	propBean.setBusEntityProperty(siteId,

                    			RefCodeNames.PROPERTY_TYPE_CD.SEND_EMAIL_TO_CORPORATE,

                    	"true");

                    }

                }else{

                	// Send an email to this person since they

                    // are responsible for ordering for the site.

                    if (emailClientEjb.wasThisEmailSent

                            (lSubject, lToEmailAddress) == false) {

    	                    emailClientEjb.send(lToEmailAddress,

    	                    		emailClientEjb.getDefaultFromAddress(accD.getAccountId()),

    	                    		lSubject, lMsg,

    	                    		Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, ud.getUserId());



                    }else{

                    	emailsSentMsg += "\nThis email has already been sent! "

                    		+ "\n[ Sent email to: " + lToEmailAddress +

                    		"\n  Subject:" + lSubject

                    		+ "\n Username:" + ud.getUserName() + "]";

                    }



                }

                emailsSentMsg += "\nInventoryMissing "

                         + "\n[ Sent email to: " + lToEmailAddress +

                        "\n  Subject:" + lSubject

                         + "\n Username:" + ud.getUserName() + "]";



            }

            /*

             * Send inventory missing email without checking for

             * approver or rejected email notification rights

             */



            for (int idx = 0; idx < uv.size(); idx++) {



                UserData ud = (UserData) uv.get(idx);



                UserInfoData u = userBean.getUserContact(ud.getUserId());

                String lToEmailAddress = u.getEmailData().getEmailAddress();

                if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(ud.getUserStatusCd())) {

                    continue;

                }



                String canReceiveEmails = null;

                try {

                    canReceiveEmails = propBean.getUserProperty(

                    		ud.getUserId(), RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL);

                }catch(DataNotFoundException exc) {}



                if(canReceiveEmails==null || canReceiveEmails.equalsIgnoreCase("false")){

                	continue;

                }



                //if user is a corporate user - only set the property, do not sent this email

                String isCorporateUser =  propBean.getUserProperty(

                		ud.getUserId(), RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER);

                if(isCorporateUser.equalsIgnoreCase("true")){

                	//set property to send email to corporate user for this site

                    int siteId = pSite.getBusEntity().getBusEntityId();



                    if(!(isEmailSubjNull) || lMsg!=null){

                    	propBean.setBusEntityProperty(siteId,

                    			RefCodeNames.PROPERTY_TYPE_CD.SEND_EMAIL_TO_CORPORATE,

                    	"true");

                    }

                }else{

                	// Send an email to this person since they

                    // are responsible for ordering for the site.

                    if (emailClientEjb.wasThisEmailSent

                            (lSubject, lToEmailAddress) == false) {

    	                    emailClientEjb.send(lToEmailAddress,

    	                    		emailClientEjb.getDefaultFromAddress(accD.getAccountId()),

    	                    		lSubject, lMsg,

    	                    		Constants.EMAIL_FORMAT_PLAIN_TEXT, 0, ud.getUserId());





                    }else{

                    	emailsSentMsg += "\nThis email has already been sent! "

                    		+ "\n[ Sent email to: " + lToEmailAddress +

                    		"\n  Subject:" + lSubject

                    		+ "\n Username:" + ud.getUserName() + "]";

                    }

                }



                emailsSentMsg += "\nInventoryMissing "

                         + "\n[ Sent email to: " + lToEmailAddress +

                        "\n  Subject:" + lSubject

                         + "\n Username:" + ud.getUserName() + "]";



            }



            return emailsSentMsg;

        } catch (Exception exc) {



            String mess = "Site.sendInventoryMissingEmail: " +

                    exc.getMessage();

            logError(mess);

        }

        return " No email sent.";

    }







    /**

     *  Gets a list of SiteData objects based off the supplied search criteria

     *  object

     *

     *@param  pCrit                    Description of the Parameter

     *@return                          a set of SiteData objects

     *@exception  RemoteException      if an error occurs

     */
    public SiteDataVector getSitesByCriteria(BusEntitySearchCriteria pCrit)

            throws RemoteException {
          return getSitesByCriteria(pCrit, null);
    }

    public SiteDataVector getSitesByCriteria(BusEntitySearchCriteria pCrit, AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {

        SiteDataVector dv = new SiteDataVector();

        Connection conn = null;

        try {

            conn = getConnection();

            BusEntityDataVector busEntityVec =

                    BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

            Iterator iter = busEntityVec.iterator();

            while (iter.hasNext()) {

                dv.add(getSiteDetails((BusEntityData) iter.next(), pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

        return dv;

    }

    /**
     * Retrieves a list of SiteData objects based off the supplied search criteria object and sets the
     * list of matching SiteData objects on the searchCriteria
     * @param 		LocationSearchDto searchCriteria the search criteria
     * @exception     RemoteException  if an error occurs
     */
    public void getLocationsByCriteria(LocationSearchDto searchCriteria) throws RemoteException {
    	getLocationsByCriteria(searchCriteria, 0);
    }

    /**
     * Retrieves a list of SiteData objects based off the supplied search criteria object and sets the
     * list of matching SiteData objects on the searchCriteria
     * @param 		LocationSearchDto searchCriteria the search criteria
     * @param 		int maxLocations the maximum number of locations to return
     * @exception     RemoteException  if an error occurs
     */
    public void getLocationsByCriteria(LocationSearchDto searchCriteria, int maxLocations) throws RemoteException {
        
        //create the default query
        StringBuilder query = new StringBuilder(500);
        query.append("SELECT BE.BUS_ENTITY_ID AS BUS_ENTITY_ID, BE.SHORT_DESC AS SHORT_DESC,\n");
        query.append("       AD.ADDRESS1 AS ADDRESS1, AD.CITY AS CITY, AD.STATE_PROVINCE_CD AS STATE_PROVINCE_CD, AD.POSTAL_CODE AS POSTAL_CODE,AD.COUNTRY_CD AS COUNTRY_CD,\n");
        query.append("       PR.CLW_VALUE AS SITE_REFERENCE_NUMBER,\n");
        query.append("       AC.LAST_USER_VISIT_DATE_TIME AS LAST_USER_VISIT_DATE_TIME\n");
        query.append("FROM CLW_BUS_ENTITY BE LEFT OUTER JOIN CLW_PROPERTY PR\n");
        query.append("                      ON (BE.BUS_ENTITY_ID = PR.BUS_ENTITY_ID\n");
        query.append("                          AND PR.USER_ID IS NULL\n");
        query.append("                          AND PR.PROPERTY_TYPE_CD = '");
        query.append(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
        query.append("'\n");
        query.append("                          AND PR.SHORT_DESC = '");
        query.append(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
        query.append("'\n");
        query.append("                          AND PR.PROPERTY_STATUS_CD = '");
        query.append(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        query.append("'),\n");
        query.append("     CLW_ADDRESS AD, CLW_USER_ASSOC AC\n");
        query.append("WHERE BE.BUS_ENTITY_TYPE_CD = '");
        query.append(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
        query.append("'\n");
        if (searchCriteria.isSearchInactive() == false) {
            query.append("AND BE.BUS_ENTITY_STATUS_CD <> '");
            query.append(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
            query.append("'\n");
        }
        query.append("AND BE.BUS_ENTITY_ID IN (SELECT DISTINCT BUS_ENTITY_ID FROM CLW_USER_ASSOC WHERE USER_ID = ? AND USER_ASSOC_CD = '");
        query.append(RefCodeNames.USER_ASSOC_CD.SITE);
        query.append("')\n");
        query.append("AND BE.BUS_ENTITY_ID = AD.BUS_ENTITY_ID\n");
        query.append("AND AD.ADDRESS_TYPE_CD = '");
        query.append(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        query.append("'\n");
        query.append("AND BE.BUS_ENTITY_ID = AC.BUS_ENTITY_ID\n");
        query.append("AND AC.USER_ID = ?\n");
        query.append("AND AC.USER_ASSOC_CD = '");
        query.append(RefCodeNames.USER_ASSOC_CD.SITE);
        query.append("'\n");
        
        String escapeClause = " ESCAPE '\\'";
        
        //Note: filter values are parameterized to prevent SQL injection attacks.
        //add filtering criteria
        List<String> filterCriteria = new ArrayList<String>();
        //filter on keyword (name/reference number.  Name filter is "contains", 
        //reference number filter is "starts with").
        if (Utility.isSet(searchCriteria.getKeyword())) {
        	String value = searchCriteria.getKeyword().trim().toUpperCase();
        	if (doesSearchCriteriaContainWildcard(value)) {
        		value = escapeLocationSearchCriteria(value);
            	query.append("AND (UPPER(BE.SHORT_DESC) LIKE '%'||?||'%'");
            	query.append(escapeClause);
            	query.append(" OR UPPER(PR.CLW_VALUE) LIKE ?||'%'");
            	query.append(escapeClause);
            	query.append(")\n");
        	}
        	else {
            	query.append("AND (UPPER(BE.SHORT_DESC) LIKE '%'||?||'%' OR UPPER(PR.CLW_VALUE) LIKE ?||'%')\n");
        	}
        	filterCriteria.add(value);
        	filterCriteria.add(value);
        }
        //filter on city (filter is "starts with")
        if (Utility.isSet(searchCriteria.getCity())) {
        	String value = searchCriteria.getCity().trim().toUpperCase();
        	if (doesSearchCriteriaContainWildcard(value)) {
        		value = escapeLocationSearchCriteria(value);
        		query.append("AND UPPER(AD.CITY) LIKE ?||'%'");
        		query.append(escapeClause);
        		query.append("\n");
        	}
        	else {
        		query.append("AND UPPER(AD.CITY) LIKE ?||'%'\n");
        	}
        	filterCriteria.add(value);
        }
        //filter on state (filter is "starts with")
        if (Utility.isSet(searchCriteria.getState())) {
        	String value = searchCriteria.getState().trim().toUpperCase();
        	if (doesSearchCriteriaContainWildcard(value)) {
        		value = escapeLocationSearchCriteria(value);
        		query.append("AND UPPER(AD.STATE_PROVINCE_CD) LIKE ?||'%'");
        		query.append(escapeClause);
        		query.append("\n");
        	}
        	else {
        		query.append("AND UPPER(AD.STATE_PROVINCE_CD) LIKE ?||'%'\n");
        	}
        	filterCriteria.add(value);
        }
        //filter on postal code (filter is "starts with")
        if (Utility.isSet(searchCriteria.getPostalCode())) {
        	String value = searchCriteria.getPostalCode().trim().toUpperCase();
        	if (doesSearchCriteriaContainWildcard(value)) {
        		value = escapeLocationSearchCriteria(value);
        		query.append("AND UPPER(AD.POSTAL_CODE) LIKE ?||'%'");
        		query.append(escapeClause);
        		query.append("\n");
        	}
        	else {
        		query.append("AND UPPER(AD.POSTAL_CODE) LIKE ?||'%'\n");
        	}
        	filterCriteria.add(value);
        }
        
        //add sorting criteria
        if (Utility.isSet(searchCriteria.getSortField()) && Utility.isSet(searchCriteria.getSortOrder())) {
        	query.append("ORDER BY ");
        	//sort by location name
	        if (Constants.LOCATION_SORT_FIELD_NAME.equalsIgnoreCase(searchCriteria.getSortField().trim())) {
	        	query.append("UPPER(BE.SHORT_DESC)");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC");
	        	}
	        	else {
	        		query.append(" ASC");
	        	}
	        }
	        //sort by location address
	        if (Constants.LOCATION_SORT_FIELD_ADDRESS.equalsIgnoreCase(searchCriteria.getSortField().trim())) {
	        	query.append("UPPER(AD.ADDRESS1)");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC");
	        	}
	        	else {
	        		query.append(" ASC");
	        	}
	        	query.append(", UPPER(AD.CITY)");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC");
	        	}
	        	else {
	        		query.append(" ASC");
	        	}
	        	query.append(", UPPER(AD.STATE_PROVINCE_CD)");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC");
	        	}
	        	else {
	        		query.append(" ASC");
	        	}
	        	query.append(", UPPER(AD.POSTAL_CODE)");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC");
	        	}
	        	else {
	        		query.append(" ASC");
	        	}
	        	query.append(", UPPER(AD.COUNTRY_CD)");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC");
	        	}
	        	else {
	        		query.append(" ASC");
	        	}
	        	query.append(", UPPER(BE.SHORT_DESC) ASC");
	        }
	        //sort by reference number
	        if (Constants.LOCATION_SORT_FIELD_NUMBER.equalsIgnoreCase(searchCriteria.getSortField().trim())) {
	        	query.append("UPPER(PR.CLW_VALUE)");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC NULLS LAST");
	        	}	        	
	        	else {
	        		query.append(" ASC NULLS FIRST");
	        	}
	        	query.append(", UPPER(BE.SHORT_DESC) ASC");
	        }
	        //sort by last visited date
	        if (Constants.LOCATION_SORT_FIELD_LAST_VISIT.equalsIgnoreCase(searchCriteria.getSortField().trim())) {
	        	query.append("AC.LAST_USER_VISIT_DATE_TIME");
	        	if (Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(searchCriteria.getSortOrder().trim())) {
	        		query.append(" DESC NULLS LAST");
	        	}	        	
	        	else {
	        		query.append(" ASC NULLS FIRST");
	        	}
	        	query.append(", UPPER(BE.SHORT_DESC) ASC");
	        }
        	query.append("\n");
        }
        
        if (maxLocations > 0 ){
        	query.insert(0, "SELECT * FROM (\n");
        	query.append(") WHERE rownum <= ?\n");
        }
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
        	conn = getConnection();
   			stmt = conn.prepareStatement(query.toString());
   			int index = 1;
   			stmt.setInt(index++, Integer.parseInt(searchCriteria.getUserId().trim()));
   			stmt.setInt(index++, Integer.parseInt(searchCriteria.getUserId().trim()));
   			for (int i=0; i<filterCriteria.size(); i++) {
   				stmt.setString(index++, filterCriteria.get(i));
   			}
   			if (maxLocations > 0) {
   				stmt.setInt(index++, maxLocations);
   			}
   			rs = stmt.executeQuery();
   			while (rs.next()) {
                SiteData siteData = SiteData.createValue();
                searchCriteria.getMatchingLocations().add(siteData);
                BusEntityData busEntity = BusEntityData.createValue();
                busEntity.setBusEntityId(rs.getInt("BUS_ENTITY_ID"));
                busEntity.setShortDesc(rs.getString("SHORT_DESC"));
                siteData.setBusEntity(busEntity);
                AddressData address = AddressData.createValue();
                address.setAddress1(rs.getString("ADDRESS1"));
                address.setCity(rs.getString("CITY"));
                address.setStateProvinceCd(rs.getString("STATE_PROVINCE_CD"));
                address.setPostalCode(rs.getString("POSTAL_CODE"));
                address.setCountryCd(rs.getString("COUNTRY_CD"));//STJ-4689.
                siteData.setSiteAddress(address);
                PropertyData property = PropertyData.createValue();
                property.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
                property.setValue(rs.getString("SITE_REFERENCE_NUMBER"));
                siteData.getMiscProperties().add(property);
                siteData.setLastUserVisitDate(rs.getTimestamp("LAST_USER_VISIT_DATE_TIME"));
   			}
        } catch (Exception e) {
            throw processException(e);
        } finally {
        	try {
        		if (rs != null) {
        			rs.close();
        		}
        		if (stmt != null) {
        			stmt.close();
        		}
        		closeConnection(conn);
        	}
        	catch (Exception e) {
        		//nothing to do here
        	}
        }
    }
    
    private boolean doesSearchCriteriaContainWildcard(String criteria) {
    	boolean returnValue = (criteria.contains("%") || criteria.contains("_"));
		return returnValue;
	}
    	
    
    private String escapeLocationSearchCriteria(String criteria) {
    	String returnValue = null;
    	if (Utility.isSet(criteria)) {
			returnValue = criteria.replaceAll("%", "\\\\%");
			returnValue = returnValue.replaceAll("_", "\\_");
    	}
    	else {
    		returnValue = criteria;
    	}
    	return returnValue;
    }

    /**

     *  Gets a list of SiteData objects related to users accounts based off the supplied search criteria

     *  object

     *

     *@param  pCrit                   Description of the Parameter

     *@param pUserAssignedFl          picks sites assinged to the users if true;

     *@return                          a set of SiteData objects

     *@exception  RemoteException      if an error occurs

     */
    public SiteDataVector getUserAccountSitesByCriteria(BusEntitySearchCriteria pCrit, boolean pUserAssignedFl)

             throws RemoteException {
           return getUserAccountSitesByCriteria( pCrit, pUserAssignedFl, null);
    }

    public SiteDataVector getUserAccountSitesByCriteria(BusEntitySearchCriteria pCrit, boolean pUserAssignedFl,AccCategoryToCostCenterView pCategToCostCenterView)

             throws RemoteException {

        SiteDataVector dv = new SiteDataVector();

        Connection conn = null;

        try {

            conn = getConnection();

            IdVector userIdV = pCrit.getUserIds();

            if(userIdV==null) userIdV = new IdVector();

            IdVector storeIdV = pCrit.getStoreBusEntityIds();

            if(storeIdV==null) storeIdV = new IdVector();



            DBCriteria dbc = new DBCriteria();

            dbc.addOneOf(UserAssocDataAccess.USER_ID,userIdV);

            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,

                    RefCodeNames.USER_ASSOC_CD.ACCOUNT);

            String acctUserIdReq =

               UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID,dbc);

            dbc = new DBCriteria();

            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeIdV);

            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, acctUserIdReq);

            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

            String acctUserStoreReq =

               BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);



            dbc = new DBCriteria();

            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,acctUserStoreReq);

            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

            dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,

                    RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);



            IdVector acctIdV =

                    BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID,dbc);



            pCrit.setStoreBusEntityIds(null);

            pCrit.setParentBusEntityIds(acctIdV);

            if(!pUserAssignedFl) {

              pCrit.setUserIds(null);

            }



            BusEntityDataVector busEntityVec =

                    BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

            Iterator iter = busEntityVec.iterator();

            while (iter.hasNext()) {

                dv.add(getSiteDetails((BusEntityData) iter.next(), pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

        return dv;

    }



    public void updateShoppingControl(ShoppingControlData scd)

    throws RemoteException{

    	Connection conn = null;

        try {

        	conn = getConnection();

        	DBCriteria crit = new DBCriteria();



        	if(scd.getShoppingControlId()>0){

        		//site - edit: update Shopping Control

        		ShoppingControlData sc = ShoppingControlDataAccess.select(conn, scd.getShoppingControlId());

        		sc.setAccountId(scd.getAccountId());

            	sc.setSiteId(scd.getSiteId());

            	sc.setMaxOrderQty(scd.getMaxOrderQty());

            	sc.setRestrictionDays(scd.getRestrictionDays());

            	sc.setModBy(scd.getModBy());

            	ShoppingControlDataAccess.update(conn, sc);



        	}else{

        		//account-site edit

        		crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, scd.getItemId());

            	crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, scd.getSiteId());

            	//crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, scd.getAccountId());// expect account id = 0



            	ShoppingControlDataVector scDV = ShoppingControlDataAccess.select(conn, crit);

            	if(scDV!=null && scDV.size()>0){

            		ShoppingControlData sdata = (ShoppingControlData)scDV.get(0);

            		sdata.setMaxOrderQty(scd.getMaxOrderQty());

            		sdata.setRestrictionDays(scd.getRestrictionDays());

            		sdata.setModBy(scd.getModBy());

            		ShoppingControlDataAccess.update(conn, sdata);

            	}else{
            		ShoppingControlDataAccess.insert(conn, scd);
            	}



        	}





        }catch (Exception e) {

        	e.printStackTrace();

        	logDebug("updateShoppingControl, error " + e);

        }

        finally {

        	closeConnection(conn);

        }

    }

    public void updateShoppingControlNewXpedx(ShoppingControlData scd)

    throws RemoteException{

    	Connection conn = null;

        try {

        	conn = getConnection();

        	DBCriteria crit = new DBCriteria();



        	if(scd.getShoppingControlId()>0){

        		//site - edit: update Shopping Control

        		ShoppingControlData sc = ShoppingControlDataAccess.select(conn, scd.getShoppingControlId());

        		sc.setAccountId(0); // update SITE Shopping Control record in the clw_shopping_control table

            	sc.setSiteId(scd.getSiteId());

            	sc.setMaxOrderQty(scd.getMaxOrderQty());

            	sc.setRestrictionDays(scd.getRestrictionDays());

            	sc.setModBy(scd.getModBy());
            	
            	ShoppingControlDataAccess.update(conn, sc);



        	}else{

        		//account-site edit

        		crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, scd.getItemId());

            	crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, scd.getSiteId());

            	//crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, scd.getAccountId());



            	ShoppingControlDataVector scDV = ShoppingControlDataAccess.select(conn, crit);

            	if(scDV!=null && scDV.size()>0){

            		ShoppingControlData sdata = (ShoppingControlData)scDV.get(0);

            		sdata.setMaxOrderQty(scd.getMaxOrderQty());

            		sdata.setRestrictionDays(scd.getRestrictionDays());

            		sdata.setModBy(scd.getModBy());
            	
            		ShoppingControlDataAccess.update(conn, sdata);

            	}else{
            		ShoppingControlDataAccess.insert(conn, scd);
            	}



        	}





        }catch (Exception e) {

        	e.printStackTrace();

        	logDebug("updateShoppingControlNewXpedx, error " + e);

        }

        finally {

        	closeConnection(conn);

        }

    }

    public void deleteAllSiteControlsForItem(int pAcctId, int pItemId)

    throws RemoteException{

    	Connection conn = null;

        try {

        	conn = getConnection();

        	DBCriteria crit = new DBCriteria();



        	crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAcctId);

        	crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

        	String sitesSql = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);



        	crit = new DBCriteria();

        	crit.addOneOf(ShoppingControlDataAccess.SITE_ID, sitesSql);

        	crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, pItemId);



        	ShoppingControlDataAccess.remove(conn, crit);



        }catch (Exception e) {

        	e.printStackTrace();

        	logDebug("deleteShoppingControl, error " + e);

        }

        finally {

        	closeConnection(conn);

        }

    }



    public void deleteShoppingControl(int pSiteId, int pItemId)

    throws RemoteException{

    	Connection conn = null;

        try {

        	conn = getConnection();

        	DBCriteria crit = new DBCriteria();

        	crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, pItemId);

        	crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, pSiteId);



        	ShoppingControlDataAccess.remove(conn, crit);



        }catch (Exception e) {

        	e.printStackTrace();

        	logDebug("deleteShoppingControl, error " + e);

        }

        finally {

        	closeConnection(conn);

        }

    }

    public void deleteShoppingControlNewXpedx(int pSiteId, int pItemId)

    throws RemoteException{

    	Connection conn = null;

        try {

        	conn = getConnection();

        	DBCriteria crit = new DBCriteria();

        	crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, pItemId);

        	crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, pSiteId);
        	
        	ShoppingControlDataAccess.remove(conn, crit);



        }catch (Exception e) {

        	e.printStackTrace();

        	logDebug("deleteShoppingControlNewXpedx, error " + e);

        }

        finally {

        	closeConnection(conn);

        }

    }


    public String getSiteNameById(int pSiteId) throws RemoteException{

    	Connection conn = null;

    	String siteName= "";

        try {

        	conn = getConnection();



        	BusEntityData bed = BusEntityDataAccess.select(conn, pSiteId);

        	if(bed!=null){

        		siteName = bed.getShortDesc();

        	}



        }catch (Exception e) {

        	e.printStackTrace();

        	logDebug("getSiteNameById, error " + e);

        }

        finally {

        	closeConnection(conn);

        }

        return siteName;

    }



    public ShoppingControlDataVector updateShoppingControls

    (ShoppingControlDataVector pShopCtrlv, boolean returnUpdated)

    throws RemoteException {



      if ( null == pShopCtrlv || pShopCtrlv.size() <=0 ) {

        return pShopCtrlv;

      }

      logInfo("[SiteBean.updateShoppingControls] pShopCtrlv = " + pShopCtrlv);
      
      ShoppingControlDataVector updatedControls = new ShoppingControlDataVector();

      Connection conn = null;

      try {

        int thisSiteId = 0, thisAccountId = 0, thisItemId=0;

        conn = getConnection();

        Iterator it = pShopCtrlv.iterator();

        while (it.hasNext()){

        	thisSiteId=0;

        	thisAccountId=0;

          ShoppingControlData scd = (ShoppingControlData)it.next();

          thisItemId = scd.getItemId();

          if(scd.getMaxOrderQty() == (-999)){



	        	  DBCriteria crit = new DBCriteria();

	        	  crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, scd.getItemId());

	        	  crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, scd.getSiteId());

	        	  //crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, 0);



	        	  ShoppingControlDataAccess.remove(conn, crit);



	        	  //remove duplicates

	        	  crit = new DBCriteria();

	        	  crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, scd.getItemId());

	        	  crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, scd.getSiteId());

	        	  crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID,scd.getAccountId());



	        	  ShoppingControlDataAccess.remove(conn, crit);



	        	  crit = new DBCriteria();

	        	  crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, scd.getItemId());

	        	  crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, scd.getAccountId());

	        	  crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, 0);

	        	  ShoppingControlDataVector scDV = ShoppingControlDataAccess.select(conn,crit);



	        	  //ShoppingControlData newscd = ShoppingControlData.createValue();



	        	  if(scDV != null && scDV.size() > 0){

	        		  ShoppingControlData newscd = (ShoppingControlData)scDV.get(0);

	        		  newscd.setSiteId(scd.getSiteId());

	        		  ShoppingControlDataAccess.update(conn, newscd);

	        	  }

	        	  thisAccountId = scd.getAccountId();

	        	  thisSiteId = scd.getSiteId();



          }else{
        	  if (scd.getAccountId() > 0 && scd.getSiteId() > 0) // cache record
        		  continue;

	          if ( thisSiteId == 0 && scd.getSiteId() > 0 ) {

	            thisSiteId = scd.getSiteId();

	          }

	          if ( thisAccountId == 0 && scd.getAccountId() > 0 ) {

	            thisAccountId = scd.getAccountId();

	          }





	          DBCriteria crit = new DBCriteria();

	          crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID,scd.getItemId());

	          crit.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID,0);

	          crit.addEqualTo(ShoppingControlDataAccess.SITE_ID, scd.getSiteId());

	          ShoppingControlDataVector scDV = ShoppingControlDataAccess.select(conn, crit);



	          if(scDV!=null && scDV.size()>0){
	        	  ShoppingControlDataAccess.update(conn, scd);
	          }else{

	        	  //add new site control

	        	  scd.setAccountId(0);
	    	      
	        	  ShoppingControlDataAccess.insert(conn, scd);

	          }



          }

        }
        if (returnUpdated)
        	return lookupSiteShoppingControls(conn, thisSiteId, thisAccountId);
        else
        	return null;
      }

      catch (Exception e) {

        e.printStackTrace();

        logDebug("updateShoppingControls, error " + e);

      }

      finally {

        closeConnection(conn);

      }

      return null;

    }



    /**

     *fetches the sites identified by the supplied site ids

     *@param pSiteIds pSite ids

     *@returns the populated SiteDataVector

     *@throws DataNotFoundException if ANY of the sites in the id vector are not found

     *@exception  RemoteException  if an error occurs*

     */
    public SiteDataVector getSiteCollection(IdVector pSiteIds) throws DataNotFoundException, RemoteException{
        return getSiteCollection( pSiteIds, null);
    }

    public SiteDataVector getSiteCollection(IdVector pSiteIds, AccCategoryToCostCenterView pCategToCostCenterView) throws DataNotFoundException, RemoteException{

        SiteDataVector dv = new SiteDataVector();

        Connection conn = null;

        try {

            conn = getConnection();

            Iterator iter = pSiteIds.iterator();

            while (iter.hasNext()) {

                Integer id = (Integer) iter.next();

                BusEntityData bed = BusEntityDataAccess.select(conn, id.intValue());

                dv.add(getSiteDetails(bed, pCategToCostCenterView));

            }

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

        return dv;

    }





    // Clone a site
    public SiteData copySite(CopySiteRequest pSiteCopyReq)  throws DataNotFoundException, RemoteException {
      return copySite( pSiteCopyReq, null);
  }

    public SiteData copySite(CopySiteRequest pSiteCopyReq, AccCategoryToCostCenterView pCategToCostCenterView)

  throws DataNotFoundException, RemoteException {



        SiteData site = null;

        Connection conn = null;



        try {

            conn = getConnection();



            BusEntityData busEntity = null;

            busEntity = BusEntityDataAccess.select

    (conn, pSiteCopyReq.getSourceSiteId() );



            if (!busEntity.getBusEntityTypeCd().equals(

                    RefCodeNames.BUS_ENTITY_TYPE_CD.SITE)) {

                throw new DataNotFoundException("Bus Entity not site");

            }



            site = getSiteDetails(busEntity, pCategToCostCenterView);

      site.getBusEntity().setBusEntityId(0);

      site.getBusEntity().setErpNum("");



      String kCopyName = "site.copy";

      String t = site.getBusEntity().getShortDesc();



      if ( t.indexOf(' ') > -1 ) {

    // pick out the first token from the name

    t = t.substring(0, t.indexOf(' ') );

      }

      t = pSiteCopyReq.getNewSiteNamePrefix() + t;



      site.getBusEntity().setShortDesc(t);



      // Lookup the new site by name.

      SiteDataVector v = getSiteByName

    (t, pSiteCopyReq.getToAccountId(),

     Site.EXACT_MATCH, Site.ORDER_BY_ID);



      if ( v == null || v.size() == 0 ) {

    AddressData ad = site.getSiteAddress();

    if ( null != ad ) {

        ad.setAddressId(0);

        site.setSiteAddress(ad);

        logDebug (" --1.11 copySite, created address=" + ad);

    }

    logDebug (" --1.0 copySite, new name=" + t +

        " creating site=" + site );

      } else if (v.size() == 1 ) {

    // update the existing site information.

    AddressData sourceAddr = site.getSiteAddress();

    site = (SiteData)v.get(0);



    AddressData currad = site.getSiteAddress();

    sourceAddr.setAddressId(currad.getAddressId());

    sourceAddr.setBusEntityId(currad.getBusEntityId());

    sourceAddr.setModBy(kCopyName);

    site.setSiteAddress(sourceAddr);



    logDebug (" --1.01 copySite, name=" + t +

        " updating site=" + site.getSiteId()

        + " sourceAddr=" + sourceAddr );



      } else {

    String m = " Multiple sites found for site name=" + t

        +" in account=" + pSiteCopyReq.getToAccountId();

    logInfo(m);

    throw new RemoteException(m);

      }





      site = addSite(site,pSiteCopyReq.getToAccountId());



      // Configure the catalog.

      DBCriteria	dbc = new DBCriteria();

            dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,

         site.getSiteId());

            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

      CatalogAssocDataVector cats

    = CatalogAssocDataAccess.select(conn, dbc);



            if (cats == null || cats.size() == 0) {

    // Create an entry

    CatalogAssocData cad = CatalogAssocData.createValue();

    cad.setCatalogId(pSiteCopyReq.getCatalogIdForSite());

    cad.setBusEntityId(site.getSiteId());

    cad.setCatalogAssocCd

        (RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

    cad.setAddBy(kCopyName);

    cad.setModBy(kCopyName);

    CatalogAssocDataAccess.insert(conn,cad);

            } else if (cats.size() == 1) {

    CatalogAssocData cad = (CatalogAssocData)cats.get(0);

    cad.setCatalogId(pSiteCopyReq.getCatalogIdForSite());

    CatalogAssocDataAccess.update(conn,cad);

      } else {

    String m = " Multiple catalogs found for site id=" +

        site.getSiteId();

    logInfo(m);

    throw new RemoteException(m);

      }





      // Configure the users if requested.

      dbc = new DBCriteria();

            dbc.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,

         pSiteCopyReq.getSourceSiteId());



      UserAssocDataVector users

    = UserAssocDataAccess.select(conn, dbc);



      for ( int i = 0; pSiteCopyReq.isCopyUsersFlag() &&

          null != users && i < users.size(); i++ ) {



    UserAssocData uad = (UserAssocData)users.get(i);

    dbc = new DBCriteria();

    dbc.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,

             site.getSiteId());

    dbc.addEqualTo(UserAssocDataAccess.USER_ID,

             uad.getUserId());



    UserAssocDataVector u2

        = UserAssocDataAccess.select(conn, dbc);

    if ( null == u2 || u2.size() == 0 ) {

        uad.setBusEntityId(site.getSiteId());

        uad.setAddBy(kCopyName);

        uad.setModBy(kCopyName);

        uad.setUserAssocId(0);

        UserAssocDataAccess.insert(conn, uad);

    }



    // Update the role for this user for this account.

    String tnewrole = pSiteCopyReq.getUserRoleForAccount();

    BusEntityDAO.updateUserAccountRights

        (conn,uad.getUserId(), pSiteCopyReq.getToAccountId(),

         tnewrole,kCopyName);

      }



      logDebug (" --1.1 copySite, new name=" + t +

          " created site=" + site.getSiteId() );



        } catch (DataNotFoundException e) {

            e.printStackTrace();

            throw e;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("copySite: " + e.getMessage());

        } finally {

      closeConnection(conn);

        }

        return site;

    }



   public SiteLedgerDataVector getSiteLedgerCollection(int siteId, int costCenterId)

            throws DataNotFoundException, RemoteException{

        SiteLedgerDataVector siteLedgerDataVector=null;

        DBCriteria  dbCrit=new DBCriteria();

        Connection conn = null;

        try {

            if(siteId>0&&costCenterId>=0){

                conn = getConnection();

                dbCrit.addEqualTo(SiteLedgerDataAccess.SITE_ID,siteId);

                dbCrit.addEqualTo(SiteLedgerDataAccess.COST_CENTER_ID,costCenterId);

                siteLedgerDataVector=SiteLedgerDataAccess.select(conn,dbCrit);

                return siteLedgerDataVector;

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

        return null;

    }



    public SiteLedgerDataVector getValidSiteLedgerCollection(int siteId, int costCenterId) throws DataNotFoundException, RemoteException {



        SiteLedgerDataVector siteLedgerDataVector;

        DBCriteria dbCrit;

        Connection conn = null;

        try {

            if (siteId > 0 && costCenterId >= 0) {



                conn = getConnection();



                dbCrit = new DBCriteria();



                dbCrit.addJoinTable(OrderDataAccess.CLW_ORDER);



                dbCrit.addEqualTo(SiteLedgerDataAccess.SITE_ID, siteId);

                dbCrit.addEqualTo(SiteLedgerDataAccess.COST_CENTER_ID, costCenterId);



                DBCriteria isolCrit = new DBCriteria();

                isolCrit.addCondition(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.ORDER_ID + "=" + OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ORDER_ID);

                isolCrit.addCondition(OrderDataAccess.ORDER_STATUS_CD + " in " + OrderDAO.kGoodOrderStatusSqlList);

                isolCrit.addCondition("(" + OrderDataAccess.ORDER_BUDGET_TYPE_CD + " not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') OR " + OrderDataAccess.ORDER_BUDGET_TYPE_CD + " IS NULL)");



                dbCrit.addIsolatedCriterita(isolCrit);



                siteLedgerDataVector = SiteLedgerDataAccess.select(conn, dbCrit);



                dbCrit = new DBCriteria();



                dbCrit.addEqualTo(SiteLedgerDataAccess.SITE_ID, siteId);

                dbCrit.addEqualTo(SiteLedgerDataAccess.COST_CENTER_ID, costCenterId);

                dbCrit.addIsNull(SiteLedgerDataAccess.ORDER_ID);

                dbCrit.addEqualTo(SiteLedgerDataAccess.ENTRY_TYPE_CD, RefCodeNames.LEDGER_ENTRY_TYPE_CD.ADJUSTMENT);



                SiteLedgerDataVector siteLedgerAdjDataVector = SiteLedgerDataAccess.select(conn, dbCrit);



                siteLedgerDataVector.addAll(siteLedgerAdjDataVector);



                return siteLedgerDataVector;

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

        return null;

    }



    public SiteLedgerDataVector getSiteLedgerCollection(int siteId)

            throws DataNotFoundException, RemoteException{

        SiteLedgerDataVector siteLedgerDataVector=null;

        DBCriteria  dbCrit=new DBCriteria();

        Connection conn = null;

        try {

            if(siteId>0){

                conn = getConnection();

                dbCrit.addEqualTo(SiteLedgerDataAccess.SITE_ID,siteId);

                siteLedgerDataVector=SiteLedgerDataAccess.select(conn,dbCrit);

                return siteLedgerDataVector;

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

        return null;

    }





    public void adjustSiteLedgerCollection(SiteLedgerDataVector data) throws RemoteException {



        Connection conn = null;

        try {

            if (data != null) {

                conn = getConnection();

                Iterator iterator = data.iterator();

                while (iterator.hasNext()) {

                    SiteLedgerData siteLD = (SiteLedgerData) iterator.next();

                    SiteLedgerDataVector usedSiteLDV = getSiteLedgerCollection(siteLD.getSiteId(), siteLD.getBudgetYear(), siteLD.getBudgetPeriod(), siteLD.getCostCenterId(), RefCodeNames.LEDGER_ENTRY_TYPE_CD.ADJUSTMENT);

                    if (usedSiteLDV != null) {

                        if (usedSiteLDV.size() == 0) {

                            if (siteLD.getAmount().doubleValue() != 0) {

                                SiteLedgerDataAccess.insert(conn, siteLD);

                            }

                        } else {

                            SiteLedgerData usedLD = (SiteLedgerData) usedSiteLDV.get(0);

                            if (usedLD != null && (usedLD.getAmount().subtract(siteLD.getAmount())).doubleValue() != 0) {

                                usedLD.setAmount(siteLD.getAmount());

                                usedLD.setModBy(siteLD.getAddBy());

                                usedLD.setComments(siteLD.getComments());

                                SiteLedgerDataAccess.update(conn, usedLD);

                            }

                        }

                    }

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }



    public void updateSiteLedgerCollection(SiteLedgerDataVector data) throws RemoteException {



        Connection conn = null;

        try {

            if (data != null) {

                conn = getConnection();

                Iterator iterator = data.iterator();

                while (iterator.hasNext()) {

                    SiteLedgerData siteLD = (SiteLedgerData) iterator.next();

                    if (siteLD.getSiteLedgerId() > 0) {

                        SiteLedgerDataAccess.update(conn, siteLD);

                    } else {

                        SiteLedgerDataAccess.insert(conn, siteLD);

                    }

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }



    private SiteLedgerDataVector getSiteLedgerCollection(int siteId, int budgetYear, int budgetPeriod, int costCenterId, String ledgerEntryTypeCd) throws RemoteException {

        SiteLedgerDataVector siteLedgerDataVector=null;

        DBCriteria  dbCrit=new DBCriteria();

        Connection conn = null;

        try {

            if(siteId>0&&budgetYear>0&&budgetPeriod>0&&costCenterId>0){

                conn = getConnection();

                dbCrit.addEqualTo(SiteLedgerDataAccess.SITE_ID,siteId);

                dbCrit.addEqualTo(SiteLedgerDataAccess.BUDGET_YEAR,budgetYear);

                dbCrit.addEqualTo(SiteLedgerDataAccess.BUDGET_PERIOD,budgetPeriod);

                dbCrit.addEqualTo(SiteLedgerDataAccess.COST_CENTER_ID,costCenterId);

                if(ledgerEntryTypeCd!=null)

                dbCrit.addEqualTo(SiteLedgerDataAccess.ENTRY_TYPE_CD,ledgerEntryTypeCd);

                siteLedgerDataVector = SiteLedgerDataAccess.select(conn, dbCrit);



            }

           return siteLedgerDataVector;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }



  public IdVector  getSiteIdsOnlyForAccount(int busEntityId) throws RemoteException {

      Connection conn=null;

       try {

            conn = getConnection();

            DBCriteria crit=new DBCriteria();

            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,busEntityId);

            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);



           IdVector ids = BusEntityAssocDataAccess.selectIdOnly(conn,

                          BusEntityAssocDataAccess.BUS_ENTITY1_ID,crit);

            return ids;

       }

      catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

}



    /**

        * Gets the site information values to be used by the request.

        * @param pSiteId an <code>int</code> value

        * @param pStoreIds an <code>IdVector</code> value

        * @param pInactiveFl an <code>boolean</code> value

        * @return SiteData

        * @exception RemoteException Required by EJB 1.0

        * DataNotFoundException if site with pSiteId

        * doesn't exist

        * @exception DataNotFoundException if an error occurs

        */
       public SiteData getSiteForStore(int pSiteId, IdVector pStoreIds, boolean pInactiveFl)
               throws RemoteException, DataNotFoundException {
             return getSiteForStore( pSiteId,  pStoreIds,  pInactiveFl, null);
      }

       public SiteData getSiteForStore(int pSiteId, IdVector pStoreIds, boolean pInactiveFl, AccCategoryToCostCenterView pCategToCostCenterView)

               throws RemoteException, DataNotFoundException {



           if (pStoreIds!=null&& pStoreIds.size()>0&& pSiteId > 0) {

               BusEntitySearchCriteria pCrit = new BusEntitySearchCriteria();

               pCrit.setStoreBusEntityIds(pStoreIds);

               pCrit.setSearchId(pSiteId);

               pCrit.setSearchForInactive(pInactiveFl);

               SiteDataVector siteDV = getSitesByCriteria(pCrit,pCategToCostCenterView);

               if (siteDV != null && siteDV.size() > 0) {

                   if (siteDV.size() == 1) {

                       return (SiteData) siteDV.get(0);

                   } else {

                       throw new RemoteException("Multiple sites for store id : " + pStoreIds);

                   }

               }

           }

           throw new DataNotFoundException("Site is not found");

       }

    /**

     *  Gets next delivery date for the site

     *@param  pSiteId  Site Id

     *@param  pAccountId Account Id or 0 (will be determined from DB)

     *@param  pMaxAge  Days for cached data (0 if disregard cache)

     *@return          Next cutoff and delivery dates

     */

    public ScheduleOrderDates calculateNextOrderDates(int pSiteId, int pAccountId, int pMaxAge)

    throws RemoteException {

        Connection con = null;

        try  {

            con = getConnection();

            int accountId = pAccountId;

            if(accountId <=0) {

                DBCriteria dbc  = new DBCriteria();

                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pSiteId);

                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                IdVector acctIdV =

                    BusEntityAssocDataAccess.selectIdOnly(con,BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);

                Integer acctIdI = (Integer) acctIdV.get(0);

                accountId = acctIdI.intValue();

            }

            ScheduleOrderDates sod =

                calculateNextOrderDates(con,pSiteId,accountId, pMaxAge);

            return sod;



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            if(con!=null) {

                closeConnection(con);

            }

        }

    }



    /**

     *  Returns site inventory level info

     *

     *@param  pSiteId  Site Id

     *@return       set of SiteInventoryConfigView objects

     */
    public SiteInventoryConfigViewVector lookupSiteInventory(int pSiteId) throws RemoteException {
      return lookupSiteInventory(pSiteId, null) ;
    }
    
    public SiteInventoryConfigViewVector lookupSiteInventory(int pSiteId, AccCategoryToCostCenterView pCategToCostCenterView)
    throws RemoteException {
    	return lookupSiteInventory(pSiteId, null, pCategToCostCenterView);
    }

    public SiteInventoryConfigViewVector lookupSiteInventory(int pSiteId, ProductDataVector productDV, AccCategoryToCostCenterView pCategToCostCenterView)

    throws RemoteException {

        Connection con = null;

        try  {

            con = getConnection();

            return lookupSiteInventory(con, pSiteId, productDV, pCategToCostCenterView);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            if(con!=null) {

                closeConnection(con);

            }

        }

    }



    /**

     * gets tax rate

     *

     * @param siteId site id

     * @return tax rate

     * @throws RemoteException if an errors

     */

    public BigDecimal getTaxRate(int siteId) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            //return TaxUtil.getTaxRate(conn, siteId);
            return TaxUtilAvalara.getTaxRate(conn, siteId);

        }

        catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }

    /**
     * gets get "Ship To" Address
     *
     * @param siteId site id
     * @return "Ship To" Address
     * @throws RemoteException if an errors
     */
    public AddressData getShipToAddress(int siteId) throws DataNotFoundException, RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return TaxUtilAvalara.getShipTo(conn, siteId);
        }
		catch (DataNotFoundException e) {
            e.printStackTrace();
			throw e;
		}
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Error :" + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private static boolean isUsedPhysicalInventoryCart(Connection pCon, int accountId) {
    	boolean usePhysicalInventory = false;            
        try{            	
        	PropertyUtil pru = new PropertyUtil(pCon);
        	usePhysicalInventory = Utility.isTrue(pru.fetchValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY));
        }catch(Exception e){}  
        return usePhysicalInventory;
    }


    public String placeScheduledOrder(int pSiteId, Date pRunForDate,
        Date startDate, boolean specificDatesFl) throws RemoteException {
      return placeScheduledOrder( pSiteId,pRunForDate, startDate,specificDatesFl, null);

    }

    private String placeScheduledOrder(int pSiteId, Date pRunForDate,

        Date startDate, boolean specificDatesFl, 
        AccCategoryToCostCenterView pCategToCostCenterView)

        throws RemoteException {

    	SimpleDateFormat sdf = new SimpleDateFormat(ScheduledOrderManager.DATE_FORMAT);

        String processLog = "";

        Connection conn = null;

        try {

            conn = getConnection();
            SiteData sd = getSite(pSiteId, 0);
            if (sd == null){
            	throw new Exception("Failed to find site for siteId=" + pSiteId);
            }
            if (sd.getNextOrdercutoffTime()==null){
            	throw new Exception("No active corporate schedule for siteId=" + pSiteId);
            }
            boolean usePhysicalInventoryCart = isUsedPhysicalInventoryCart(conn, sd.getAccountId());

            boolean checkOk = false;
            if (sd != null){
	            try {
	            	checkOk = checkSiteDataforInventoryOrder(sd, processLog);
	            } catch (Exception e){
	            	if (sd.isAllowCorpSchedOrder()){
		            	processLog = "placeInventoryOrder: pSiteId=" + pSiteId + " pRunForDate=" + sdf.format(pRunForDate);
		            	processLog += e.getMessage();
	            	}else{
	            		throw e;
	            	}
	            }
            }

            if (checkOk) {
                ScheduledOrderManager invManager = new ScheduledOrderManager(sd);

                GregorianCalendar runForDateCal = invManager.getRunDateCalforScheduleProcess(pRunForDate);

                GregorianCalendar cutoff = invManager.calculateCutoffDate(conn, sd, runForDateCal.getTime());
                if (cutoff == null) {
                	processLog = "placeInventoryOrder: pSiteId=" + pSiteId + " pRunForDate=" + sdf.format(pRunForDate);
	            	processLog += "[ NO NEXT ORDER CUTOFF DEFINED ]";
	            	return processLog;
                }

                //GregorianCalendar cutoffRem = invManager.getRemCutoffCalendar(conn,sd.getNextOrdercutoffDate(),sd.getAccountId());

                GregorianCalendar cutoffRem = invManager.getRemCutoffCalendar(conn,sd.getNextOrdercutoffDate(),sd.getNextOrdercutoffTime(),sd.getAccountId());

                GregorianCalendar startDateCal = new GregorianCalendar();

                startDateCal.setTime(startDate);

                logInfo("cutoff: " + sdf.format(cutoff.getTime()));

                logInfo("startDate: " + sdf.format(startDate));

                logInfo("runForDateCal: " + sdf.format(runForDateCal.getTime()));

                if(cutoffRem!=null){

                  logInfo("placeScheduledOrder => cutoffRem: " + sdf.format(cutoffRem.getTime()));

                }



                processLog = "placeInventoryOrder: pSiteId=" + pSiteId + " pRunForDate=" + sdf.format(runForDateCal.getTime());

                processLog += " OrdercutoffDate =" + sdf.format(cutoff.getTime());



                if (cutoffRem != null && runForDateCal.after(cutoffRem)

                        && !(runForDateCal.getTime().before(cutoff.getTime()) && startDate.after(cutoff.getTime()))) {

                    //Inventory reminder email sent.;

                    String emailTxt = invManager.checkReadyProcess(conn);

                    logInfo("placeScheduledOrder => emailTxt: " + emailTxt);

                    if (emailTxt.trim().length() > 0) {

                        String sendRes = sendInventoryMissingEmail(sd, cutoff.getTime(), conn);

                        logInfo("placeScheduledOrder => sendRes: " + sendRes);

                    }

                    return processLog;



                } else if (!(runForDateCal.getTime().before(cutoff.getTime()) && startDate.after(cutoff.getTime()))) {



                    String remTime =null;

                    if (cutoffRem != null) {

                        remTime = cutoffRem.getTime().toString();

                    }

                    remTime=remTime==null?"-":remTime;

                    processLog += " [No reminder sent, reminder is due > "

                            + remTime + " for this site.]"

                            + " Currently " + runForDateCal.getTime();



                } else if (runForDateCal.getTime().before(cutoff.getTime()) && startDate.after(cutoff.getTime())) {

                    if (!invManager.isProcessed(conn, sd)) {

                        invManager.process(conn, specificDatesFl, usePhysicalInventoryCart, pCategToCostCenterView);

                    } else {

                        processLog += " [Duplicated order]";

                    }

                }

            }

        } catch (Exception e) {

            //e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

        return processLog;

    }



    public CustomerOrderRequestData constructOrderRequest(SiteData siteData, ShoppingCartData cartData, UserData user) throws  RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            ScheduledOrderManager manager = new ScheduledOrderManager(siteData, user, cartData);

            if (Utility.isFail(manager.checkQtyValues(conn))) {

                if (manager.hasAutoOrderItems()) {

                    manager.setupAutoOrder(conn);

                }

            }

            return manager.prepareOrderRequest(siteData, cartData, user);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }
    public ProcessOrderResultData inventoryEarlyRelease(SiteData site,
                                                        UserData user,
                                                        ShoppingCartData cart,
                                                        CustomerOrderRequestData orderRequest) throws RemoteException {
      return inventoryEarlyRelease( site, user, cart,orderRequest, null);

    }



    public ProcessOrderResultData inventoryEarlyRelease(SiteData site,
                                                        UserData user,
                                                        ShoppingCartData cart,
                                                        CustomerOrderRequestData orderRequest,
                                                        AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {



        String processLog = "";

        Connection conn = null;

        try {

            conn = getConnection();

            if (!Utility.isFail(checkSiteDataforInventoryOrder(site, processLog))) {



                ScheduledOrderManager invManager = new ScheduledOrderManager(site, user, cart,orderRequest.getInventoryOrderQtyLog());



                GregorianCalendar runForDateCal = invManager.getRunDateCalforScheduleProcess(new Date());

                GregorianCalendar cutoff = invManager.calculateCutoffDate(conn, site, runForDateCal.getTime());



                SimpleDateFormat sdf = new SimpleDateFormat(ScheduledOrderManager.DATE_FORMAT);



                logInfo("inventoryEarlyRelease => cutoff: " + sdf.format(cutoff.getTime()));

                logInfo("inventoryEarlyRelease => runForDateCal: " + sdf.format(runForDateCal.getTime()));

                processLog = "placeInventoryOrder: pSiteId=" + site.getSiteId() + " pRunForDate=" + sdf.format(runForDateCal.getTime());

                processLog += " OrdercutoffDate =" + sdf.format(cutoff.getTime());

                logInfo(processLog);



                return invManager.earlyRelease(conn, orderRequest, pCategToCostCenterView);



            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException("Error :" + e.getMessage());

        } finally {

            closeConnection(conn);

        }

        return null;

    }





    private boolean checkSiteDataforInventoryOrder(SiteData pSite, String processLog) throws Exception {



        if (!pSite.isActive()) {

            throw new Exception("Site is not in an active status.pSiteId=" + pSite.getSiteId());

        } else if (!pSite.hasInventoryShoppingOn()) {

            throw new Exception("Inventory shopping is off for pSiteId=" + pSite.getSiteId());

        } else if (pSite.getContractData() == null || pSite.getContractData().getContractId() <= 0) {

            throw new Exception("[ NO CONTRACT FOUND ]");

        } else if (null == pSite.getNextOrdercutoffDate() && !pSite.isAllowCorpSchedOrder()) {

            throw new Exception("[ NO ORDER CUTOFF DEFINED ]");

        } else if (null == pSite.getNextDeliveryDate() && !pSite.isAllowCorpSchedOrder()) {

            throw new Exception("NO ORDER DELIVERY DATE DEFINED ]");

        }

        return true;

    }



    public class  ScheduledOrderManager {



        private SiteData         siteData;

        private UserData         userData;

        private ShoppingCartData cartData;

        private InventoryOrderQtyLogOperator orderQtyLog;



        private static final String LAST_CUTOFF_DATE = "LAST_CUTOFF_DATE";

        public  static final int DATE_RANGE = 0; // day

        public  static final int BEGIN_NEXT_PROCESS_DAY = 6;// hour

        private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

        private static final String TIME_FORMAT = "hh:mm a";

        private static final String Y = "Y";

        private static final String N = "N";

        private static final int EARLY_RELEASE_RANGE = 7; //Range in days. If in, the order will affect the sceduled monthly inventory order.



        public ScheduledOrderManager(SiteData siteData) throws Exception {

            UserData user         = getUserByName(kInvUser);

            ShoppingCartData cart = getShoppingCartData(user,siteData);

            init(siteData,user,cart,constructQtyLogCollection(siteData.getSiteId(),cart.getItems()));

        }



        public ScheduledOrderManager(SiteData site, UserData user, ShoppingCartData cart, Collection orderQtyLogCollection) throws Exception {

           init(site,user,cart,orderQtyLogCollection);

        }



        public ScheduledOrderManager(SiteData siteData, UserData user, ShoppingCartData cart) throws Exception {

            init(siteData,user,cart,constructQtyLogCollection(siteData.getSiteId(),cart.getItems()));

        }



        private boolean isCorrect(SiteData siteData) throws Exception {

            if (siteData == null)

                throw new Exception("ScheduledOrderManager : site data is null");

            if (siteData.getSiteInventory() == null) {

                throw new Exception("ScheduledOrderManager : inventory item collection can't be null");

            }

            return true;

        }



        private void init(SiteData siteData, UserData userData, ShoppingCartData cart, Collection orderQtyLogCollection) throws Exception {

            if(isCorrect(siteData)){

                this.cartData = cart;

                this.userData = userData;

                this.siteData = siteData;

               this.orderQtyLog = new InventoryOrderQtyLogOperator(orderQtyLogCollection);

            }

        }



        private Collection constructQtyLogCollection(int siteId,ShoppingCartItemDataVector items) {

            InventoryOrderQtyDataVector result  = new InventoryOrderQtyDataVector();

            if (items != null) {

                Iterator it = items.iterator();

                while (it.hasNext()) {

                    ShoppingCartItemData item = (ShoppingCartItemData) it.next();

                    InventoryOrderQtyData log = InventoryOrderQtyData.createValue();

                    log.setItemId(item.getItemId());

                    log.setAutoOrderApplied(N);

                    log.setItemType(item.getIsaInventoryItem() ?

                            RefCodeNames.PRODUCT_TYPE_CD.INVENTORY :

                            RefCodeNames.PRODUCT_TYPE_CD.REGULAR);

                    log.setBusEntityId(siteId);

                    if (item.getIsaInventoryItem()) {

                        log.setEnableAutoOrder(item.getAutoOrderEnable()?Y:N);

                        log.setPar(item.getInventoryParValue());

                        log.setQtyOnHand(item.getInventoryQtyOnHandString());

                        log.setInventoryQty(item.getMonthlyOrderQtyString());

                        log.setPrice(new BigDecimal(item.getPrice()));

                        log.setCategory(item.getCategoryName());

                        log.setCostCenter(item.getProduct().getCostCenterName());

                        if(item.getProduct().getCatalogDistrMapping() != null){

                        	log.setDistItemNum(item.getProduct().getCatalogDistrMapping().getItemNum());

                        }

                    }

                    result.add(log);



                }

            }

            return result;

        }



        private boolean isProcessed(Connection pCon, SiteData pSite) throws Exception {



            try {

                if (!existInventoryOrderedfor(pCon, pSite)) {

                    try {

                        logInfo("inventory_order not found.checking inventory_log");

                        boolean retCd= checkInventoryLog(pCon, pSite);

                        logInfo("checking inventory_log successfully.returned cd is "+retCd);

                        return retCd;

                    } catch (Exception e) {

                        logInfo("Wrong inventory log or log not found");

                    }

                } else{

                    logInfo("inventory_order found.The processing will be rejected");

                    return true;

                }

            } catch (Exception e) {

                try {

                        logInfo("inventory_order exception.message "+e.getMessage());

                        logInfo("checking inventory_log");

                        boolean retCd= checkInventoryLog(pCon, pSite);

                        logInfo("checking inventory_log successfully.returned cd is "+retCd);

                        return retCd;



                } catch (Exception exc) {

                    logInfo("Wrong inventory log or log not found");

                }

            }

            logInfo("checking of the order process is fully failed.Default value of the retCd is false.");

            return false;

        }



        private GregorianCalendar setBeginDay(GregorianCalendar cal) {

            cal.set(Calendar.HOUR_OF_DAY, 0);

            cal.set(Calendar.MINUTE, 0);

            cal.set(Calendar.SECOND, 0);

            cal.set(Calendar.MILLISECOND, 0);

            return cal;

        }



        public boolean existInventoryOrderedfor(Connection pCon, SiteData pSite) throws SQLException {



            Calendar cal = Calendar.getInstance();

            cal.setTime(pSite.getNextOrdercutoffDate());

            Calendar begTimeRange = ((Calendar) cal.clone());

            Calendar endTimeRange = ((Calendar) cal.clone());

            begTimeRange.add(Calendar.DATE, -ScheduledOrderManager.DATE_RANGE);

            endTimeRange.add(Calendar.DATE, 7); //+week

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



            String sql = "SELECT count(o.order_id) FROM CLW_ORDER o WHERE SITE_ID = " + pSite.getSiteId() +

                              " AND ADD_DATE >= TO_DATE('" + sdf.format(begTimeRange.getTime()) + "','YYYY-MM-DD') " +

                              " AND ADD_DATE <= TO_DATE('" + sdf.format(endTimeRange.getTime()) + "','YYYY-MM-DD') " +

                              " AND (ORDER_SOURCE_CD='" + RefCodeNames.ORDER_SOURCE_CD.INVENTORY + "'" +

                              " OR  exists(select 1 from clw_pre_order pro  where" +

                              " o.pre_order_id = pro.pre_order_id" +

                              " and pro.ORDER_SOURCE_CD='" + RefCodeNames.ORDER_SOURCE_CD.INVENTORY + "'))";



            logInfo("SQL:"+sql);

            Statement stmt = pCon.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            rs.next();

            int orderCount = rs.getInt(1);

            rs.close();

            stmt.close();

            return orderCount != 0;



        }



        public boolean checkInventoryLog(Connection pCon, SiteData pSite) throws Exception, ParseException {



            DBCriteria dbc = new DBCriteria();



            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pSite.getSiteId());

            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG);

            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, ScheduledOrderManager.LAST_CUTOFF_DATE);

            dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            dbc.addIsNotNull(PropertyDataAccess.CLW_VALUE);



            PropertyDataVector props = PropertyDataAccess.select(pCon, dbc);

            if (props.size() == 1) {



                PropertyData property = (PropertyData) props.get(0);

                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);



                Date cuttofDate = sdf.parse(property.getValue());



                GregorianCalendar propCutoffCal = new GregorianCalendar();

                GregorianCalendar currCutoffCal = new GregorianCalendar();

                propCutoffCal.setTime(cuttofDate);

                propCutoffCal = setBeginDay(propCutoffCal);

                currCutoffCal.setTime(pSite.getNextOrdercutoffDate());

                currCutoffCal = setBeginDay(currCutoffCal);


                return propCutoffCal.getTime().equals(currCutoffCal.getTime());

            }

            else if (props.size() > 1)           {

                throw new Exception("Multiple property("+RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG+") for site_id :"+pSite.getSiteId());

            }

            logInfo("not exists data at inventory log");

            return false;

        }



        public GregorianCalendar getRunDateCalforScheduleProcess(Date pRunDate) throws Exception{

            GregorianCalendar runForDateCal = new GregorianCalendar();

            runForDateCal.setTime(pRunDate);

            return runForDateCal;

        }



        public boolean checkContractForInventoryItems(Connection mCon) throws Exception {

            if (siteData.getSiteInventory().size() == 0) {

                return true;

            }

            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, siteData.getContractData().getContractId());

            IdVector contractItems = ContractItemDataAccess.selectIdOnly(mCon, ContractItemDataAccess.ITEM_ID, dbc);

            if (contractItems == null || contractItems.size() == 0) {

                logInfo("SiteBean.InventoryMgr.checkContractForInventoryItems. No contract items found"

                        + " for site=" + siteData.getSiteId());

                return false;

            }

            return checkEquals(contractItems, cartData.getItems());



        }


        private ShoppingCartData getShoppingCartData(UserData user, SiteData siteData) throws Exception {
          return  getShoppingCartData( user, siteData, null);
        }

        private ShoppingCartData getShoppingCartData(UserData user, SiteData siteData, AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

            ShoppingServices shopBean = APIAccess.getAPIAccess().getShoppingServicesAPI();

            return shopBean.getInvShoppingCart(null,

                    user,

                    siteData, false,pCategToCostCenterView );

        }



        private UserData getUserByName(String userName) throws Exception {

            User userEjb = APIAccess.getAPIAccess().getUserAPI();

            return userEjb.getUserByName(userName, 0);

        }



        private boolean checkEquals(IdVector contractItems, ShoppingCartItemDataVector siteInventory) throws Exception {



            HashSet contractHashIds = getHashIds(contractItems);

            Iterator it = siteInventory.iterator();

            while (it.hasNext()) {

                ShoppingCartItemData siteInvInfo = (ShoppingCartItemData) it.next();

                if (!contractHashIds.contains(new Integer(siteInvInfo.getItemId()))) {

                    return false;

                }

            }

            return true;

        }



        private HashSet getHashIds(IdVector contractItems) {

            HashSet hashIds = new HashSet();

            if (contractItems != null) {

                Iterator it = contractItems.iterator();

                while (it.hasNext()) {

                    hashIds.add((Integer) it.next());

                }

            }

            return hashIds;

        }



        private HashMap getHashMap(ShoppingCartItemDataVector cartItems) throws Exception {

            HashMap hashData = new HashMap();

            if (cartItems != null) {

                Iterator it = cartItems.iterator();

                while (it.hasNext()) {

                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();

                    if (!hashData.containsKey(String.valueOf(cartItem.getItemId()))) {

                        hashData.put(String.valueOf(cartItem.getItemId()), cartItem);

                    } else {

                        throw new Exception("Multiple item in the cart");

                    }

                }

            }

            return hashData;

        }



        public boolean checkQtyValues(Connection mCon) throws RemoteException, DataNotFoundException, Exception {



            IdVector invItems = getInvItemsWhereNotSetupQtyVal(mCon, getIds(cartData.getItems()));



            if (invItems.size() == 0) {

                return false;

            }



            String msg =

                    " SiteBean.InventoryMgr.checkQtyValues." +

                            " Qty values not set for inventory items ("

                            + IdVector.toCommaString(invItems)

                            + ") for site=" + siteData.getSiteId();



            logInfo(msg);

            return true;

        }



        private List getIds(ShoppingCartItemDataVector items) {



            IdVector ids = new IdVector();

            if (items != null) {

                Iterator it = items.iterator();

                while (it.hasNext()) {

                    ids.add(new Integer(((ShoppingCartItemData) it.next()).getItemId()));

                }

            }

            return ids;

        }



        private IdVector getInvItemsWhereNotSetupQtyVal(Connection mCon, List itemIds) throws Exception {



        	int siteCatId = siteData.getSiteCatalogId();

        	int acctId = siteData.getAccountId();



        	DBCriteria crit = new DBCriteria();



        	//get site cat items

        	crit = new DBCriteria();

        	crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, siteCatId);

        	
        	IdVector siteCatItems = CatalogStructureDataAccess.selectIdOnly(mCon,

        			CatalogStructureDataAccess.ITEM_ID, crit);
//-------------
                //get account catalog items witch have 'special_permission' = true
                logInfo("getInvItemsWhereNotSetupQtyVal() ====>get items with 'special_permission' = true for acctId="+acctId);

                crit = new DBCriteria();
                crit.addJoinCondition(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
                crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, acctId);
                crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
                crit.addEqualTo(CatalogStructureDataAccess.STATUS_CD, RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
                crit.addEqualToIgnoreCase(CatalogStructureDataAccess.SPECIAL_PERMISSION, "TRUE");
                CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(mCon, crit);
                IdVector specCatItems = new IdVector();
                for (int i = 0; csDV!=null && i < csDV.size(); i++) {
                  CatalogStructureData csD = (CatalogStructureData)csDV.get(i);
                  if (csD != null){
                    Integer specCatItem = new Integer(csD.getItemId());
                    specCatItems.add(specCatItem);
                  }
                }
                logInfo("getInvItemsWhereNotSetupQtyVal() ====> special_permission items:" + ((specCatItems!=null)? specCatItems.toString():"null"));
//---------------

        	//check inventory item

        	crit = new DBCriteria();

        	crit.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, acctId);

        	IdVector invItems = InventoryItemsDataAccess.selectIdOnly(mCon,

        			InventoryItemsDataAccess.ITEM_ID, crit);


                logInfo("getInvItemsWhereNotSetupQtyVal() ====> inventory items:" + ((invItems!=null)? invItems.toString():"null"));

        	//check only for inventory item which belongs to site catalog

        	List newItemIds = new ArrayList();

        	for(Iterator it=itemIds.iterator(); it.hasNext();){

        		Integer item = (Integer)it.next();

//        		if(invItems.contains(item) && siteCatItems.contains(item)){
                        if(invItems.contains(item) && siteCatItems.contains(item) && !specCatItems.contains(item)){

        			newItemIds.add(item);

        		}

        	}
                logInfo("getInvItemsWhereNotSetupQtyVal() ====> inventory items which belongs to site catalog without spec items:" + ((newItemIds!=null)? newItemIds.toString():"null"));

            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, siteData.getBusEntity().getBusEntityId());

            dbc.addIsNull(InventoryLevelDataAccess.QTY_ON_HAND);

            dbc.addIsNull(InventoryLevelDataAccess.ORDER_QTY);

            if(newItemIds != null && newItemIds.size()>0){

            	dbc.addOneOf(InventoryLevelDataAccess.ITEM_ID, newItemIds);

            }else{

            	dbc.addOneOf(InventoryLevelDataAccess.ITEM_ID, itemIds);

            }
            IdVector  retItemIds = InventoryLevelDataAccess.selectIdOnly(mCon, InventoryLevelDataAccess.ITEM_ID, dbc);
            logInfo("getInvItemsWhereNotSetupQtyVal() ====>return Items where Qty on Hand are null:" + ((retItemIds!=null)? retItemIds.toString():"null"));

            return retItemIds;


        }



        public void process(Connection conn, boolean specificDatesFl)

            throws Exception {

            process(conn, specificDatesFl, false);

        }


        public void process(Connection conn, boolean specificDatesFl,

            boolean usePhysicalInventoryCart) throws Exception {
          process( conn, specificDatesFl, usePhysicalInventoryCart, null);
        }

        public void process(Connection conn, boolean specificDatesFl,

            boolean usePhysicalInventoryCart,
            AccCategoryToCostCenterView pCategToCostCenterView
      ) throws Exception {

            if (!Utility.isFail(checkContractForInventoryItems(conn))) {

                if (checkQtyValues(conn)) {

                    if (hasAutoOrderItems()) {

                        setupAutoOrder(conn);

                    }

                }

                if (canOrderBePlaced(conn)) {

                    placeOrder(conn, specificDatesFl, usePhysicalInventoryCart, pCategToCostCenterView);

                }

            }

        }



        private ProcessOrderResultData placeOrder(Connection conn,

                boolean specificDatesFl,
                AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

        	return placeOrder(conn, specificDatesFl, false, pCategToCostCenterView);

        }



        private ProcessOrderResultData placeOrder(Connection conn,

        boolean specificDatesFl, boolean usePhysicalInventoryCart,
        AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {

        	CustomerOrderRequestData orderReq = prepareOrderRequest(siteData, cartData, userData);

            return placeOrder(conn, siteData, cartData, orderReq,

                specificDatesFl, usePhysicalInventoryCart, pCategToCostCenterView);

        }



        private ProcessOrderResultData placeOrder(Connection conn,

            SiteData siteData, ShoppingCartData cartData,

            CustomerOrderRequestData orderReq, boolean specificDatesFl,
             AccCategoryToCostCenterView pCategToCostCenterView)

            throws Exception {

            return placeOrder(conn, siteData, cartData, orderReq,

                specificDatesFl, false, pCategToCostCenterView);

        }



        private ProcessOrderResultData placeOrder(Connection conn,

                                                  SiteData siteData,

                                                  ShoppingCartData cartData,

                                                  CustomerOrderRequestData orderReq,

                                                  boolean specificDatesFl,

                                                  boolean usePhysicalInventoryCart,

                                                  AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {


            IntegrationServices isvcEjb     = APIAccess.getAPIAccess().getIntegrationServicesAPI();

            Order orderEjb                  = APIAccess.getAPIAccess().getOrderAPI();

            ShoppingServices scartEjb       = APIAccess.getAPIAccess().getShoppingServicesAPI();

            ProcessOrderResultData orderRes = ProcessOrderResultData.createValue();

            Account acctEjb = APIAccess.getAPIAccess().getAccountAPI();



            if (orderReq == null || orderReq.getEntriesCollection().size() == 0) {

                logError("Cannot process empty order.");

                orderRes.setOrderNum("empty order");

                orderRes.setOrderStatusCd("NO_ORDER_PLACED");

                orderRes.setOrderId(0);

                scartEjb.invalidateInvetoryShoppingHistory(siteData.getBusEntity().getBusEntityId(),"SiteBean.placeOrder");

                // return orderRes;

            } else {

                orderRes = isvcEjb.processOrderRequest(orderReq);

            }



            if(orderRes!=null){



                OrderItemDataVector orderedItems = orderEjb.getOrderItemCollection(orderRes.getOrderId());

                addToOrderQtyLog(orderedItems,InventoryOrderQtyDataAccess.ORDER_ID,new Integer(orderRes.getOrderId()));





                //Calculate Service Fee if applicable

                AccountData acctD = acctEjb.getAccountForSite(siteData.getSiteId());

                String addServiceFee = acctD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);

                BigDecimal serviceFee = new BigDecimal(0);

                if(addServiceFee.equals("true") && orderReq.getContractId() > 0){

                	List cartItems = cartData.getItems();

                	HashMap serviceFeeDetMap = scartEjb.calculateServiceFee(orderReq.getContractId(),

                			cartItems,orderReq.getAccountId());

                	if(serviceFeeDetMap!=null && serviceFeeDetMap.size()>0){



            			//Calculate total service fee

            			for (int s = 0;s < cartItems.size();s++) {

            				ShoppingCartItemData sData = (ShoppingCartItemData)cartItems.get(s);



            				Integer item = new Integer(sData.getItemId());



            				ServiceFeeDetail finalDet = (ServiceFeeDetail)serviceFeeDetMap.get(item);

            				if(finalDet!=null){

            					BigDecimal itemServiceFee = finalDet.getAmount();



            	                for(int i=0; i<orderedItems.size(); i++){

            	                	OrderItemData oitem = (OrderItemData)orderedItems.get(i);

            	                	if(oitem.getItemId() == sData.getItemId()){

            	                		oitem.setServiceFee(itemServiceFee);

            	                		OrderItemDataAccess.update(conn, oitem);

            	                	}

            	                }



            				}

            			}



            		}

                }

            }





            addToOrderQtyLog(cartData.getItems(),InventoryOrderQtyDataAccess.CUTOFF_DATE,siteData.getNextOrdercutoffDate());



            //loop through the items and add price

            for(int ii=0; ii< cartData.getItems().size(); ii++){



            	ShoppingCartItemData sciD = cartData.getItem(ii);

            	this.orderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.PRICE, new BigDecimal(sciD.getPrice()));

            	this.orderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.CATEGORY, sciD.getCategoryName());

            	//this.orderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.COST_CENTER, sciD.getProduct().getCostCenterName());

            	if(sciD.getProduct().getCatalogDistrMapping()!=null){

            		this.orderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.DIST_ITEM_NUM, sciD.getProduct().getCatalogDistrMapping().getItemNum());

            	}

            }

            //adding cost center override

            if(orderRes!=null){



                OrderItemDataVector orderedItems = orderEjb.getOrderItemCollection(orderRes.getOrderId());

                for(int ii=0; ii<orderedItems.size(); ii++){

                	int itemId = ((OrderItemData)orderedItems.get(ii)).getItemId();

                	int ccId = ((OrderItemData)orderedItems.get(ii)).getCostCenterId();

                	if(ccId > 0){

                		CostCenterData ccData = CostCenterDataAccess.select(conn, ccId);

                		String ccName = ccData.getShortDesc();

                		this.orderQtyLog.addTo(itemId, InventoryOrderQtyDataAccess.COST_CENTER,ccName);


                	}

                }

            }



            // Now clear out the cart for the site.

            cartData.setItems(null);

            scartEjb.saveInventoryShoppingCart(cartData, orderRes, null);

            orderEjb.saveInventoryOrderQtyLog(getOrderQtyLog().values(), this.userData);



            // Reset the inventory information.

            if (usePhysicalInventoryCart) {

                ///

                SiteInventoryConfigViewVector configViewVector =

                    lookupSiteInventory(conn, siteData.getSiteId(), pCategToCostCenterView);

                ///

                DBCriteria dbCriteria = new DBCriteria();

                dbCriteria.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID,

                    siteData.getSiteId());

                InventoryLevelDataVector invLevelVector =

                    InventoryLevelDataAccess.select(conn, dbCriteria);

                ///

                if (invLevelVector != null) {

                    if (configViewVector == null) {

                        ShoppingDAO.resetInventoryQty(conn, siteData.getSiteId(), 0);

                    } else {

                        boolean isInventoryInfo = false;

                        int parValue = 0;

                        for (int i = 0; i < invLevelVector.size(); ++i) {

                            isInventoryInfo = false;

                            parValue = 0;

                            InventoryLevelData invLevel = (InventoryLevelData) invLevelVector.get(i);

                            for (int j = 0; j < configViewVector.size(); ++j) {

                                if (configViewVector.get(j) instanceof SiteInventoryInfoView) {

                                    SiteInventoryInfoView invInfo = (SiteInventoryInfoView)configViewVector.get(j);

                                    if (invLevel.getItemId() == invInfo.getItemId()) {

                                        isInventoryInfo = true;

                                        parValue = invInfo.getParValue();

                                        break;

                                    }

                                }

                            }

                            if (isInventoryInfo) {

                                int physicalQtyOnHand =

                                    recalculatePhysicalQtyOnHand(

                                        invLevel.getQtyOnHand(), parValue);

                                invLevel.setQtyOnHand((physicalQtyOnHand >= 0) ? String.valueOf(physicalQtyOnHand) : null);

                                invLevel.setOrderQty(null);

                                InventoryLevelDataAccess.update(conn, invLevel);

                            } else {

                                invLevel.setQtyOnHand(null);

                                invLevel.setOrderQty(null);

                                InventoryLevelDataAccess.update(conn, invLevel);

                            }

                        }

                    }

                }

            } else {

                ShoppingDAO.resetInventoryQty(conn, siteData.getSiteId(), 0);

            }

            if (!specificDatesFl) {

                Date cutoffDate = siteData.getNextOrdercutoffDate();
                
                // following method do not make sense, so comment it out by DL
                /*if ( isInEarlyReleaseRange(new Date(), cutoffDate, EARLY_RELEASE_RANGE)) {

                    cutoffDate = new Date();

                }*/

                updateLastCutoffDate(conn, siteData.getSiteId(), cutoffDate);

            }

            return orderRes;

        }



        private int recalculatePhysicalQtyOnHand(String qtyOnHandStr, int parValue) {

            int physicalQtyOnHand = -1;

            int qtyOnHand = 0;

            if (qtyOnHandStr != null) {
                physicalQtyOnHand = 0;
                if (qtyOnHandStr.trim().length() > 0) {

                    try {

                        qtyOnHand = Integer.parseInt(qtyOnHandStr.trim());

                    } catch (Exception ex) {

                    }

                }

            }

            if (qtyOnHand > parValue) {

                physicalQtyOnHand = qtyOnHand - parValue;

            }

            return physicalQtyOnHand;

        }



        private CustomerOrderRequestData prepareOrderRequest(SiteData siteData, ShoppingCartData cartData, UserData user) throws Exception {



            ShoppingServices scartEjb = APIAccess.getAPIAccess().getShoppingServicesAPI();

            User userEjb = APIAccess.getAPIAccess().getUserAPI();

            Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();



            CustomerOrderRequestData orderReq = new CustomerOrderRequestData();

            ShoppingCartItemDataVector cartItems = cartData.getItems();

            BigDecimal freightAmt;

            BigDecimal handlingAmt;



            //Freight

            OrderHandlingView frOrder = prepareFreight(scartEjb, siteData, cartData);



            freightAmt  = frOrder.getTotalFreight();

            handlingAmt = frOrder.getTotalHandling();

            freightAmt  = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);

            handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);



            int userId = -2;

            String userName = kInvUser;

            if (user == null) {

                try {

                    user = userEjb.getUserByName(kInvUser, 0);

                    userId = user.getUserId();

                    userName = user.getUserName();

                } catch (DataNotFoundException e) {

                }

            } else {

                userId = user.getUserId();

                userName = user.getUserName();

            }



            orderReq.setContractId(siteData.getContractData().getContractId());

            orderReq.setUserName(userName);

            orderReq.setUserId(userId);

            orderReq.setSiteId(siteData.getSiteId());

            orderReq.setAccountId(siteData.getAccountId());

            orderReq.setOrderSourceCd(RefCodeNames.SYS_ORDER_SOURCE_CD.INVENTORY);

            orderReq.setFreightCharge(freightAmt.doubleValue());

            orderReq.setHandlingCharge(handlingAmt.doubleValue());



            for (int ii = 0; ii < cartItems.size(); ii++) {

                ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);

                int itemId = cartItem.getProduct().getItemData().getItemId();

                int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();



                int qty = 0;

                if (cartItem.getIsaInventoryItem()) {

                    String t2 = cartItem.getInventoryQtyOnHandString();

                    if ((t2 == null || t2.length() == 0)

                            && cartItem.getInventoryParValue() > 0

                            && !Utility.isSet(cartItem.getQuantityString())) {

                        // This item has been designated as an

                        // inventory item for this site, but

                        // the inventory on hand value has not

                        // been updated.

                        String msg =

                                " Qty value not set for inventory item: "

                                        + cartItem.description();

                        logInfo(msg);

                        orderReq.addOrderNote(msg);

                    }

                    qty = cartItem.getInventoryOrderQty();

                } else {

                    qty = cartItem.getQuantity();

                }



                logInfo(" Item  : " + cartItem.getItemId());

                logInfo(" qty  : " + qty);



                if (qty == 0) {

                    continue;

                }



                addToOrderQtyLog(cartItem.getItemId(),

                        InventoryOrderQtyDataAccess.ORDER_QTY,

                        new Integer(qty));





                double price = cartItem.getPrice();

                orderReq.addItemEntry(ii + 1, itemId, clw_skunum,

                        qty, price,

                        cartItem.getProduct().getUom(),

                        cartItem.getProduct().getShortDesc(),

                        cartItem.getProduct().getPack(),

                        cartItem.getIsaInventoryItem(),

                        cartItem.getInventoryParValue(),

                        cartItem.getInventoryQtyOnHandString(),

                        cartItem.getReSaleItem());

            }



            orderReq.setInventotyOrderQtyLog(getOrderQtyLog().values());



            return orderReq;

        }



        private OrderHandlingView prepareFreight(ShoppingServices scartEjb, SiteData siteData, ShoppingCartData cartData) throws Exception {



            OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

            ShoppingCartItemDataVector cartItems = cartData.getItems();

            OrderHandlingView frOrder = null;

            if (cartItems != null) {

                for (int ii = 0; ii < cartItems.size(); ii++) {



                    ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);

                    OrderHandlingItemView frItem = OrderHandlingItemView.createValue();

                    frItem.setItemId(cartItem.getProduct().getProductId());

                    BigDecimal priceBD = new BigDecimal(cartItem.getPrice());

                    priceBD = priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);

                    frItem.setPrice(priceBD);

                    int qty = 0;

                    if (cartItem.getIsaInventoryItem()) {

                        qty = cartItem.getInventoryOrderQty();

                    } else {

                        qty = cartItem.getQuantity();

                    }

                    frItem.setQty(qty);

                    BigDecimal weight = null;

                    String weightS = cartItem.getProduct().getShipWeight();

                    try {

                        weight = new BigDecimal(weightS);

                    } catch (Exception exc) {

                        log.info("WARNINIG: " + exc.getMessage());

                    }

                    frItem.setWeight(weight);

                    frItems.add(frItem);

                }



                frOrder = OrderHandlingView.createValue();

                frOrder.setTotalHandling(new BigDecimal(0));

                frOrder.setTotalFreight(new BigDecimal(0));

                frOrder.setContractId(siteData.getContractData().getContractId());

                frOrder.setAccountId(siteData.getAccountId());

                frOrder.setSiteId(siteData.getSiteId());

                frOrder.setWeight(new BigDecimal(0));

                frOrder.setItems(frItems);

                try {

                    frOrder = scartEjb.calcTotalFreightAndHandlingAmount(frOrder);

                } catch (RemoteException exc) {

                    exc.printStackTrace();

                }

            }

            return frOrder;

        }



        private void updateLastCutoffDate(Connection conn, int siteId,Date cutoffDate) {



            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,siteId);

            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG);

            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,LAST_CUTOFF_DATE);

            dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            dbc.addIsNotNull(PropertyDataAccess.CLW_VALUE);





            try {

                PropertyDataVector props = PropertyDataAccess.select(conn, dbc);

                if(props.size()>0)

                {

                    PropertyData property = (PropertyData)props.get(0);

                    SimpleDateFormat sdf= new SimpleDateFormat(DATE_FORMAT);

                    property.setValue(sdf.format(cutoffDate));

                    property.setModBy("ScheduledOrderManager");

                    PropertyDataAccess.update(conn,property);

                }

                else

                {

                  SimpleDateFormat sdf= new SimpleDateFormat(DATE_FORMAT);

                  PropertyData property= PropertyData.createValue();

                  property.setBusEntityId(siteId);

                  property.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG);

                  property.setShortDesc(LAST_CUTOFF_DATE);

                  property.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

                  property.setValue(sdf.format(cutoffDate));

                  property.setAddBy("ScheduledOrderManager");

                  property.setModBy("ScheduledOrderManager");

                  PropertyDataAccess.insert(conn,property);

                }

            } catch (Exception e) {

               e.printStackTrace();

            }

        }



        private boolean isInEarlyReleaseRange(Date first, Date second, int range) {



            Calendar firstC = Calendar.getInstance();

            Calendar secondC = Calendar.getInstance();



            firstC.setTime(first);

            secondC.setTime(second);



            if (firstC.equals(secondC) || firstC.after(secondC)) {

                return true;

            }



            for (int i = 0; i < range; i++) {

                firstC.add(Calendar.DATE, 1);

            }



            if (firstC.after(secondC) || firstC.equals(secondC)) {

                return true;

            } else {

                return false;

            }

        }



        private boolean hasAutoOrderItems() {



            Iterator it = cartData.getItems().iterator();

            while (it.hasNext()) {

                ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();

                if (cartItem.getAutoOrderEnable()) return true;

            }

            return false;

        }



        private boolean canOrderBePlaced(Connection conn) throws  Exception {



            // Get the current cart

            ShoppingCartData sd = cartData;

            if (sd == null) {

                logError(" canOrderBePlaced, No shoppingcart " +

                        " available for inventory order for site=" + siteData);

                return false;

            }



            //checking  the placed order if INVENTORY_CHECK_PLACED_ORDER property is set for this site

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.EXTRA);

            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, sd.getSite().getAccountId());

            crit.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_CHECK_PLACED_ORDER);

            PropertyDataVector pv = PropertyDataAccess.select(conn, crit);

            String val = null;

            int valInt = 0;

            if (pv != null && pv.size() > 0) {

                val = ((PropertyData) pv.get(0)).getValue();

            }

            if (Utility.isSet(val)) {

                try {

                    valInt = Integer.parseInt(val);

                    if (existOrder(conn, sd.getSite().getSiteId(), sd.getSite().getNextOrdercutoffDate(), valInt)) {

                        logInfo("canOrderBePlaced, siteId="+sd.getSite().getSiteId()+" has placed " +

                                " order(s) in the last "+valInt+" days.No order placed.");

                        return false;

                    }

                }

                catch (NumberFormatException e) {

                    logInfo("canOrderBePlaced => " + e.getMessage());

                    return false;

                }

            }



            logInfo("canOrderBePlaced, siteId=" + siteData.getSiteId() + " is ready to place an inventory order");

            return true;

        }



        public boolean setupAutoOrder(Connection mCon) throws DataNotFoundException, Exception {





            IdVector ids = getInvItemsWhereNotSetupQtyVal(mCon, getIds(cartData.getItems()));

            HashMap hashCartItemData = getHashMap(cartData.getItems());

            for (Iterator iter = ids.iterator(); iter.hasNext();) {

                Integer id = (Integer) iter.next();

                ShoppingCartItemData cartItemData = (ShoppingCartItemData) hashCartItemData.get(id.toString());

                if (cartItemData == null) throw new Exception("Item not found in the cart");



                addToOrderQtyLog(cartItemData.getItemId(),

                        InventoryOrderQtyDataAccess.AUTO_ORDER_APPLIED,Y);





                if (cartItemData.getAutoOrderEnable()) {

                    int quantity = -1;

                    BigDecimal parVal = new BigDecimal(cartItemData.getInventoryParValue());



                    addToOrderQtyLog(cartItemData.getItemId(),

                            InventoryOrderQtyDataAccess.AUTO_ORDER_FACTOR,

                            cartItemData.getAutoOrderFactor());



                    if (parVal.intValue() > 0) {

                        // For those locations setup for monthly

                        // orders, set the in hand value of the item

                        // to half the par value.  This will result

                        // in a half sized order for inventory items.

                        // This was a JCP request.

                        if (cartItemData.getAutoOrderFactor() != null) {

                        	//The following logic rounds half up!

                        	//adding the .500001 will increase the value over a whole number:

                        	//.5 + .500001 = 1.000001

                        	//casting that to an int truncates (not rounds) the .000001...so

                        	//0 + .500001 = .500001 which truncates to 0.

                        	//.25 + .500001 = .75000001 which truncates to 0.

                            double quantityDb =  (parVal.doubleValue() * cartItemData.getAutoOrderFactor().doubleValue());

                            quantity = (int) (quantityDb + 0.500001); //rounding to upper integer

                            cartItemData.setInventoryQtyOnHand((int) (parVal.doubleValue() - quantity));

                            cartItemData.setInventoryQtyOnHandString(String.valueOf(cartItemData.getInventoryQtyOnHand()));

                            cartItemData.setInventoryQtyIsSet(true);

                            cartItemData.setQuantity(cartItemData.getInventoryOrderQty());

                            cartItemData.setQuantityString(String.valueOf(cartItemData.getQuantity()));

                        } else {

                            // This is a site set up for orders for

                            // a specified weekly interval.  If the on hand

                            // value is not set, then set it to zero to

                            // force an order for the par value configured

                            // for the current order cycle.

                            quantity = 0;

                            cartItemData.setQuantity(quantity);

                        }





                    }

                }

            }

            return true;

        }



        public String checkReadyProcess(Connection conn) throws Exception {

            String res = "";

            try {

                //qty

                boolean qtyMissing = checkQtyValues(conn);

                if (qtyMissing) {

                    res += "On Hand Values Missing";

                }



            } catch (Exception e) {

                e.printStackTrace();

                return e.getMessage();

            }

            return res;

        }



        public GregorianCalendar getRemCutoffCalendar(Connection conn, Date nextOrdercutoffDate, Date nextOrdercutoffTime, int pAccountId) throws SQLException {



            logInfo("getRemCutoffCalendar => begin for account id: "+pAccountId);

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.EXTRA);

            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);

            crit.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION);

            PropertyDataVector pv = PropertyDataAccess.select(conn, crit);

            String val = null;

            int valInt = 0;

            if (pv != null && pv.size() > 0) {

                val = ((PropertyData) pv.get(0)).getValue();

            }

            logInfo("getRemCutoffCalendar => val: "+val);



            GregorianCalendar cutoffRem = null;

            if (val != null) {

                try {



                    valInt = Integer.parseInt(val);



                    /*cutoffRem = new GregorianCalendar();

                    GregorianCalendar cutoff = new GregorianCalendar();

                    cutoff.setTime(nextOrdercutoffDate);



                    cutoff.set(Calendar.HOUR_OF_DAY, 0);

                    cutoff.set(Calendar.MINUTE, 0);

                    cutoff.set(Calendar.SECOND, 0);

                    cutoff.set(Calendar.MILLISECOND, 0);



                    cutoffRem = (GregorianCalendar) cutoff.clone();





                    cutoffRem.add(Calendar.DATE, -valInt);*/



                    /*

                     * Make new calendar obj with next cutoff date and time

                     * Make calendar obj with this time - value, and subtract 10 mins (comment)

                     * cutoff = cal.getTime()

                     * currentDate = new Date

                     * make sure that current date < cutoff (cutoff has to be in future)

                     *

                     *

                     */



                    Date currentDate = new Date();



                    Date cloneD = (Date)nextOrdercutoffDate.clone();

                    GregorianCalendar cutoffD = new GregorianCalendar();

                    cutoffD.setTime(cloneD);



                    GregorianCalendar cutoffT = new GregorianCalendar();

                    cutoffT.setTime(nextOrdercutoffTime);



                    cutoffD.set(Calendar.HOUR_OF_DAY, cutoffT.get(Calendar.HOUR_OF_DAY));

                    cutoffD.set(Calendar.MINUTE, cutoffT.get(Calendar.MINUTE));

                    cutoffD.set(Calendar.SECOND, cutoffT.get(Calendar.SECOND));



                    cutoffRem = (GregorianCalendar)cutoffD.clone();



                    cutoffRem.add(Calendar.HOUR_OF_DAY, -valInt);

                    //Subtract 10 mins to have a buffer between schedules

                    cutoffRem.add(Calendar.MINUTE, -10);



                    if(currentDate.after(cutoffD.getTime())){

                    	return null;

                    }



                }

                catch (NumberFormatException e) {

                    logInfo("getRemCutoffCalendar => " + e.getMessage());

                }

            }

            logInfo("getRemCutoffCalendar => cutoffRem:"+cutoffRem);

            logInfo("getRemCutoffCalendar => end.");

            return cutoffRem;

        }



        public GregorianCalendar calculateCutoffDate(Connection conn, SiteData sd, Date pRunForDate) throws Exception {



            Calendar cal = Calendar.getInstance();

            Calendar cutoffTime = Calendar.getInstance();



            cutoffTime.setTime(sd.getNextOrdercutoffTime());



            cal.setTime(pRunForDate);

            cal.set(Calendar.HOUR_OF_DAY, cutoffTime.get(Calendar.HOUR_OF_DAY));

            cal.set(Calendar.MINUTE, cutoffTime.get(Calendar.MINUTE));

            cal.set(Calendar.SECOND, cutoffTime.get(Calendar.SECOND));

            cal.set(Calendar.MILLISECOND,cutoffTime.get(Calendar.MILLISECOND));



            ScheduleOrderDates sod =

                    calculateNextOrderDates(conn,

                            sd.getSiteId(),

                            sd.getAccountId(), 0,

                            new GregorianCalendar(cal.get(Calendar.YEAR),

                                    cal.get(Calendar.MONTH),

                                    cal.get(Calendar.DAY_OF_MONTH)

                                    ,cal.get(Calendar.HOUR_OF_DAY)

                                    ,cal.get(Calendar.MINUTE)

                                    ,cal.get(Calendar.SECOND)));



            if (sod.getNextOrderCutoffDate() == null) {
            	return null;
            }

            // Scheduled order dates configured.

            sd.setNextDeliveryDate(sod.getNextOrderDeliveryDate());

            sd.setNextOrdercutoffDate(sod.getNextOrderCutoffDate());           
            	
        	GregorianCalendar cutoff = new GregorianCalendar();

        	cutoff.setTime(sod.getNextOrderCutoffDate());

            cutoff.set(Calendar.HOUR_OF_DAY, cutoffTime.get(Calendar.HOUR_OF_DAY));

            cutoff.set(Calendar.MINUTE, cutoffTime.get(Calendar.MINUTE));

            cutoff.set(Calendar.SECOND, cutoffTime.get(Calendar.SECOND));

            cutoff.set(Calendar.MILLISECOND,cutoffTime.get(Calendar.MILLISECOND));
            
            return cutoff;

        }



        private boolean existOrder(Connection pCon, int pSiteId, Date nextOrdercutoffDate, int valInt) throws Exception {



            Calendar cal = Calendar.getInstance();

            cal.setTime(nextOrdercutoffDate);

            Calendar begTimeRange = ((Calendar) cal.clone());

            Calendar endTimeRange = ((Calendar) cal.clone());

            begTimeRange.add(Calendar.DATE, -valInt);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



            String sql = "SELECT count(o.order_id) FROM CLW_ORDER o WHERE SITE_ID = " + pSiteId +

                    " AND ADD_DATE >= TO_DATE('" + sdf.format(begTimeRange.getTime()) + "','YYYY-MM-DD') " +

                    " AND ADD_DATE <= TO_DATE('" + sdf.format(endTimeRange.getTime()) + "','YYYY-MM-DD') " +

                    " AND exists(select 1 from clw_pre_order pro  where" +

                    " o.pre_order_id = pro.pre_order_id)";



            logInfo("SQL:" + sql);

            Statement stmt = pCon.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            rs.next();

            int orderCount = rs.getInt(1);

            rs.close();

            stmt.close();

            return orderCount != 0;

        }



        public ProcessOrderResultData earlyRelease(Connection conn, CustomerOrderRequestData orderReq, AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {



            ProcessOrderResultData orderRes = null;

            if (!Utility.isFail(checkContractForInventoryItems(conn))) {

                orderRes = placeOrder(conn, siteData, cartData, orderReq, false, pCategToCostCenterView);

                if (orderRes != null && orderRes.getOrderId() > 0) {

                    String message = "Inventory order scheduled cutoff date " + siteData.getNextOrdercutoffDate();

                    OrderDAO.addOrderNote(conn, orderRes.getOrderId(), message, userData.getUserName());

                }

            }



            return orderRes;

        }



        public ProcessOrderResultData earlyRelease(Connection conn, AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {



            ProcessOrderResultData orderRes = null;

            if (!Utility.isFail(checkContractForInventoryItems(conn))) {

                if (checkQtyValues(conn)) {

                    if (hasAutoOrderItems()) {

                        setupAutoOrder(conn);

                    }

                }

                orderRes = placeOrder(conn, false, pCategToCostCenterView);

                if (orderRes != null && orderRes.getOrderId() > 0) {

                    String message = "Inventory order scheduled cutoff date " + siteData.getNextOrdercutoffDate();

                    OrderDAO.addOrderNote(conn, orderRes.getOrderId(), message, userData.getUserName());

                }

            }



            return orderRes;

        }



        private void addToOrderQtyLog(ShoppingCartItemDataVector items, String to, Object value) {

            this.orderQtyLog.addTo(items,to,value);

        }



        private void addToOrderQtyLog(int itemId, String to, Object value) {

            this.orderQtyLog.addTo(itemId,to,value);

        }



        private HashMap getOrderQtyLog() {

            return  this.orderQtyLog.getLog();

        }



        private void addToOrderQtyLog(OrderItemDataVector items, String to, Object value) {

            this.orderQtyLog.addTo(items,to,value);

        }



        private class InventoryOrderQtyLogOperator {



            private HashMap qtyLog;



            public InventoryOrderQtyLogOperator(HashMap qtyLog) {

                this.qtyLog = qtyLog;

            }



            public InventoryOrderQtyLogOperator() {

                this.qtyLog = new HashMap();

            }



            public InventoryOrderQtyLogOperator(Collection orderQtyLogCollection) {

                init(orderQtyLogCollection);

            }



            private void init(Collection orderQtyLogCollection) {

                this.qtyLog = new HashMap();

                if (orderQtyLogCollection != null) {

                    Iterator it = orderQtyLogCollection.iterator();

                    while (it.hasNext()) {

                        InventoryOrderQtyData log = (InventoryOrderQtyData) it.next();

                        this.qtyLog.put(new Integer(log.getItemId()), log);

                    }

                }

            }



            public InventoryOrderQtyData getLog(int itemId) {

                return (InventoryOrderQtyData) this.qtyLog.get(new Integer(itemId));

            }



            public void put(int itemId,InventoryOrderQtyData data){

                this.qtyLog.put(new Integer(itemId),data);

            }



            public void addTo(int itemId, String receiver, Object value) {

                InventoryOrderQtyData log = getItemLog(new Integer(itemId),true);

                if (InventoryOrderQtyDataAccess.AUTO_ORDER_APPLIED.equals(receiver)) {

                    log.setAutoOrderApplied((String) value);

                } else if (InventoryOrderQtyDataAccess.AUTO_ORDER_FACTOR.equals(receiver)) {

                    log.setAutoOrderFactor((BigDecimal) value);

                } else if (InventoryOrderQtyDataAccess.ENABLE_AUTO_ORDER.equals(receiver)) {

                    log.setEnableAutoOrder((String) value);

                } else if (InventoryOrderQtyDataAccess.BUS_ENTITY_ID.equals(receiver)) {

                    log.setBusEntityId(((Integer) value).intValue());

                } else if (InventoryOrderQtyDataAccess.CUTOFF_DATE.equals(receiver)) {

                    log.setCutoffDate((Date) value);

                } else if (InventoryOrderQtyDataAccess.INVENTORY_ORDER_QTY_ID.equals(receiver)) {

                    log.setInventoryOrderQtyId(((Integer) value).intValue());

                } else if (InventoryOrderQtyDataAccess.ORDER_ID.equals(receiver)) {

                    log.setOrderId(((Integer) value).intValue());

                } else if (InventoryOrderQtyDataAccess.ITEM_ID.equals(receiver)) {

                    log.setItemId(((Integer) value).intValue());

                } else if (InventoryOrderQtyDataAccess.ITEM_TYPE.equals(receiver)) {

                    log.setItemType((String) value);

                } else if (InventoryOrderQtyDataAccess.INVENTORY_QTY.equals(receiver)) {

                    log.setInventoryQty((String) value);

                } else if (InventoryOrderQtyDataAccess.ORDER_QTY.equals(receiver)) {

                    log.setOrderQty(((Integer) value).intValue());

                } else if (InventoryOrderQtyDataAccess.PAR.equals(receiver)) {

                    log.setPar(((Integer) value).intValue());

                } else if (InventoryOrderQtyDataAccess.QTY_ON_HAND.equals(receiver)) {

                    log.setQtyOnHand((String) value);

                } else if (InventoryOrderQtyDataAccess.ADD_BY.equals(receiver)) {

                    log.setAddBy((String) value);

                } else if (InventoryOrderQtyDataAccess.ADD_DATE.equals(receiver)) {

                    log.setAddDate((Date) value);

                } else if (InventoryOrderQtyDataAccess.MOD_DATE.equals(receiver)) {

                    log.setModDate((Date) value);

                } else if (InventoryOrderQtyDataAccess.MOD_BY.equals(receiver)) {

                    log.setModBy((String) value);

                }else if (InventoryOrderQtyDataAccess.PRICE.equals(receiver)) {

                    log.setPrice((BigDecimal) value);

                }else if (InventoryOrderQtyDataAccess.CATEGORY.equals(receiver)) {

                    log.setCategory((String) value);

                }else if (InventoryOrderQtyDataAccess.COST_CENTER.equals(receiver)) {

                    log.setCostCenter((String) value);

                }else if (InventoryOrderQtyDataAccess.DIST_ITEM_NUM.equals(receiver)) {

                    log.setDistItemNum((String) value);

                }

            }



            private InventoryOrderQtyData getItemLog(Integer id){

                return getItemLog(id,false);

            }

            private InventoryOrderQtyData getItemLog(Integer id,boolean createInNotExist) {

                InventoryOrderQtyData log = (InventoryOrderQtyData) this.qtyLog.get(id);

                if(log == null && createInNotExist){

                    log = InventoryOrderQtyData.createValue();

                }

                return log;

            }



            public HashMap getLog() {

                return  qtyLog;

            }



            public InventoryOrderQtyData getItemLog(int id) {

                return getItemLog(new Integer(id));

            }



            public void addTo(ShoppingCartItemDataVector items, String receiver, Object value) {

                if (items != null && !items.isEmpty()) {

                    Iterator it = items.iterator();

                    while (it.hasNext()) {

                        int itemId = ((ShoppingCartItemData) (it.next())).getItemId();

                        addTo(itemId, receiver, value);

                    }

                }

            }



            public void addTo(IdVector itemIds, String receiver, Object value) {

                if (itemIds != null && !itemIds.isEmpty()) {

                    Iterator it = itemIds.iterator();

                    while (it.hasNext()) {

                        int itemId = ((Integer) it.next()).intValue();

                        addTo(itemId, receiver, value);

                    }

                }

            }



            public void addTo(OrderItemDataVector items, String receiver, Object value) {

                if (items != null && !items.isEmpty()) {

                    Iterator it = items.iterator();

                    while (it.hasNext()) {

                        int itemId = ((OrderItemData) (it.next())).getItemId();

                        addTo(itemId, receiver, value);

                    }

                }

            }

        }

    }



    private String getZipCode(Connection pCon, SiteData siteData) throws Exception {

        return getZipCode(pCon, siteData.getSiteId());

    }



    private String getZipCode(Connection pCon, int siteId) throws Exception {



        AddressDataVector addresses;

        addresses = BusEntityDAO.getSiteAddresses(pCon, siteId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);



        String zipCode = "";

        if (addresses.size() > 0) {



            AddressData address = ((AddressData) addresses.get(0));

            zipCode = address.getPostalCode();



            if (zipCode == null) {

                zipCode = "";

            } else {

                if ((address.getCountryCd() != null) && (RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(address.getCountryCd()))) {

                    if (zipCode.length() > 5) {

                        zipCode = zipCode.substring(0, 5);

                    }

                }

            }



            logInfo("zipCode=" + zipCode);

            return zipCode;

        } else {

            throw new Exception("no shiping address for site id:" + siteId);

        }

    }



    private String extractCutoffTime(ScheduleJoinView sjVw) {

        String cutOffTime = "";

        ScheduleDetailDataVector scheduleDetails = sjVw.getScheduleDetail();

        if (scheduleDetails != null) {

            Iterator it = scheduleDetails.iterator();

            while (it.hasNext()) {

                ScheduleDetailData detail = (ScheduleDetailData) it.next();

                String valueTypeCd = detail.getScheduleDetailCd();

                if (valueTypeCd.equals(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME)) {

                    String value = detail.getValue();

                    if (cutOffTime.length() > 0) {

                        logInfo("Found extra cutoff time: " + value);

                        return null;

                    } else {

                        cutOffTime = value;

                    }

                }

            }

        } else {

            logInfo("ScheduleDetail is null");

            return null;

        }

        return cutOffTime;

    }



    public Date getCutoffTime(Connection pCon, SiteData pSite) throws Exception {



        return getCutoffTime(pCon, pSite.getAccountId(), pSite.getSiteId(), pSite.getContractData().getCatalogId());



    }



    public Date getCutoffTime(Connection pCon, int accountId, int siteId, int catalogId) throws Exception {
        Date cutoffTime;
        try {
        	
        	boolean allowCorpSchedOrder = false;            
            try{            	
            	PropertyUtil pru = new PropertyUtil(pCon);
            	allowCorpSchedOrder = Utility.isTrue(pru.fetchValue(0, siteId, RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER));
            }catch(DataNotFoundException e){}
            
            ScheduleData scheduleD = null;            
            if (allowCorpSchedOrder){
            	Schedule scheduleBean = APIAccess.getAPIAccess().getScheduleAPI();
            	scheduleD = scheduleBean.getSchedule(siteId);
            }else{
	            Distributor distrEjb = APIAccess.getAPIAccess().getDistributorAPI();
	            int distrId = distrEjb.getMajorDistforCatalog(catalogId);
	
	            if (distrId > 0) {
	
	                String zipCode = getZipCode(pCon, siteId);
	                scheduleD = distrEjb.getScheduleForZipCode(distrId, zipCode, accountId, null);
	
	                if (scheduleD == null) {
	                    throw new Exception("schedule data is null for distId: "+distrId+" zip code: "+zipCode+" and account id: "+accountId);
	                }
	            } else {
	                throw new Exception("major distributor not found for siteId :" + siteId
	                        + " catalogId :" + catalogId);
	            }
            }

            ScheduleJoinView sjVw = getDeliveryScheduleById(scheduleD);
            String timeStr = extractCutoffTime(sjVw);
            cutoffTime = Utility.parseDate(timeStr, ScheduledOrderManager.TIME_FORMAT, true);
        } catch (Exception e) {
            logInfo(e.getMessage());
            throw new Exception("Can't gets cutoff time.Details :" + e.getMessage());
        }

        return cutoffTime;
    }

    public int getSiteIdByOrderId(int orderId) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            DBCriteria dbCriteria = new DBCriteria();

            dbCriteria.addEqualTo(OrderDataAccess.ORDER_ID, orderId);

            IdVector ids = OrderDataAccess.selectIdOnly(conn, OrderDataAccess.SITE_ID, dbCriteria);

            if (ids.size() > 1) {

                throw new Exception("Multiple site_id for order_id : " + orderId);

            } else if (ids.size() == 0) {

                return 0;

            } else {

                return ((Integer) ids.get(0)).intValue();

            }

        } catch (Exception e) {

            e.printStackTrace();

            logDebug("getSitetIdByOrderId, error " + e);

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

    }



    private String getActualSkuNumber(ProductData pd, StoreData storeD) {

        return getActualSkuNumber(pd, storeD.getStoreType().getValue());

    }



    private String getActualSkuNumber(ProductData pd, String pStoreTypeCd) {



        if (Utility.isSet(pd.getActualCustomerSkuNum())) {

            return pd.getActualCustomerSkuNum();

        }



        if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(pStoreTypeCd)) {

            if (pd.getCatalogDistrMapping() == null) {

                return pd.getManufacturerSku();

            } else {

                return pd.getCatalogDistrMapping().getItemNum();

            }

        }



        //default to the standard system sku

        return Integer.toString(pd.getSkuNum());



    }



    public BusEntityDataVector getSiteByErpNum(int accountId, String siteNumber) throws RemoteException {

        Connection conn = null;

        try {



            conn = getConnection();



            String q = "select s.bus_entity_id from clw_bus_entity s, clw_bus_entity a, " +

                    " clw_bus_entity_assoc ba " +

                    " where " +

                    " s.erp_num = '" + siteNumber + "' " +

                    " and a.bus_entity_id = " + accountId  +

                    " and a.bus_entity_id = ba.bus_entity2_id " +

                    " and s.bus_entity_id = ba.bus_entity1_id ";



            DBCriteria dbc = new DBCriteria();

            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, q);

            BusEntityDataVector bedv = BusEntityDataAccess.select(conn, dbc);



            return bedv;



        } catch (Exception e) {

            e.printStackTrace();

            logDebug("getSiteByErpNum, error " + e);

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

    }

/*

    //Bulk site loading

    public void bulkSiteLoad(SiteDataVector pSites) {

        if(pSites==null) {

            return;

        }

        if(pDefaults==null) {

            pDefaults = new HashMap();

        }

        HashMap siteHM = new HashMap();

        ArrayList errors = new ArrayList();

        for(Iterator iter = pSites.iterator(); iter.hasNext();) {

            SiteData siteD = (SiteData) iter.next();

            String siteRefNum = null;

            String siteName = null;

            String addr1 = null;

            String addr2 = null;

            String addr3 = null;

            String addr4 = null;

            String city = null;

            String state = null;

            String country = null;

            String zip = null;

            String taxable = null;



            PropertyData siteRefNumPD =

                    siteD.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);

            if(siteRefNumPD!=null) {

                siteRefNum = siteRefNumPD.getValue();

            }

            BusEntityData siteBeD = siteD.getBusEntity();

            if(siteBeD!=null) {

                siteName = siteBeD.getShortDesc();

            }

            AddressData addressD = siteD.getSiteAddress();

            if(addressD!=null) {

                address1 = addressD.getAddress1();

                address2 = addressD.getAddress2();

                address3 = addressD.getAddress3();

                address4 = addressD.getAddress4();

                city = addressD.getCity();

                state = addressD.getStateProvinceCd();

                zip = addressD.getPostalCode();

                country = addressD.getCountryCd();

            }



            if(!Utility.isSet(siteRefNumPD)) {

                String errorMess = "Site doesn't have site reference number. Site name: "+ siteName;

                errors.add(errorMess);

                continue;

            }

            if(!Utility.isSet(siteName)) {

                String errorMess = "Site doesn't have site name set. Site reference number: " + siteRefNum;

                continue;

            }

            if(!Utility.isSet(address1)) {

                String errorMess = "Street address is not set for the site. Site reference number: " + siteRefNum;

                continue;

            }

            if(!Utility.isSet(city)) {

                String errorMess = "Site city is not set. Site reference number: " + siteRefNum;

                continue;

            }

            if(!Utility.isSet(state)) {

                String errorMess = "Site state is not set. Site reference number: " + siteRefNum;

                continue;

            }

            if(!Utility.isSet(zip)) {

                String errorMess = "Site postal code is not set. Site reference number: " + siteRefNum;

                continue;

            }

            if(!Utility.isSet(country)) {

                String errorMess = "Site country is not set. Site reference number: " + siteRefNum;

                continue;

            }



            if(!Utility.isSet(taxable)) {

                String errorMess = "Site taxable code is not set. Site reference number: " + siteRefNum;

                continue;

            } else if(!Utility.isBoolean(taxable)) {



            }





            BusEntityData acctBeD = siteD.getAccountBusEntity();

            int accountId = acctBeD.getBusEntityId();

            if(accountId<=0) {

                String errorMess = "No account for the site found. "



            }

        }

    }

    */



/**

     *  Calculates pairs cutoff and delivery dates to sites of one account

     *

     *@param  pSiteIds                 set of site ids or null if whole account

     *@param  pAccountId               account id

     *@return HashMap object. Keys are siteIds. Values are ScheduleOrderDates objects

     *@exception  RemoteException        if an error occurs

     */



    public HashMap calculateNextOrderDates(IdVector pSiteIds, int pAccountId)

    throws RemoteException

    {

    Connection conn = null;

    try {

        conn = getConnection();

        return calculateNextOrderDates(conn, pSiteIds, pAccountId);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage(),e);

        } finally {

            closeConnection(conn);

        }

    }



    private HashMap calculateNextOrderDates(Connection pCon, IdVector pSiteIds, int pAccountId)

    throws Exception

    {

        Statement stmt = null;

        DBCriteria dbc;



            Distributor distBean = APIAccess.getAPIAccess().getDistributorAPI();

            Date currdate = new Date();

            int accountId = pAccountId;

            //Get account cutoff

            if(pSiteIds == null) {

                dbc = new DBCriteria();

                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountId);

                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                pSiteIds = BusEntityAssocDataAccess.selectIdOnly(pCon,BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);

            }



            dbc = new DBCriteria();

            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, accountId);

            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,

                      RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);

            PropertyDataVector propertyDV = PropertyDataAccess.select(pCon,dbc);

            int accountCutoffDays = 0;

            if(propertyDV.size()==1) {

              PropertyData pD = (PropertyData) propertyDV.get(0);

              String ss = pD.getValue();

              try {

                accountCutoffDays = Integer.parseInt(ss);

              } catch(Exception exc) {

                logError("Illegal account schedule cutoff days value: "+ss+" Account id: "+accountId);

              }

            } else if(propertyDV.size()>1) {

              logError("Multiple account schedule cutoff days for the account. Account id: "+accountId);

            }





            // Get catalogs

            dbc = new DBCriteria();

            dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, pSiteIds);

            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

            dbc.addOrderBy(CatalogAssocDataAccess.CATALOG_ID);

            CatalogAssocDataVector siteCataAssocDV =

                    CatalogAssocDataAccess.select(pCon, dbc);

            HashMap siteCatalogHM = new HashMap();

            IdVector catalogIdV = new IdVector();

            int prevCatalogId =0;

            for(Iterator iter=siteCataAssocDV.iterator(); iter.hasNext();) {

                CatalogAssocData caD = (CatalogAssocData) iter.next();

                siteCatalogHM.put(new Integer(caD.getBusEntityId()), caD);

                int catalogId = caD.getCatalogId();

                if(prevCatalogId!=catalogId)  {

                    prevCatalogId = catalogId;

                    catalogIdV.add(new Integer(catalogId));

                }

            }



            //Get site zip codes

            dbc = new DBCriteria();

            dbc.addOneOf(AddressDataAccess.BUS_ENTITY_ID, pSiteIds);

            dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,

                    RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

            dbc.addEqualTo(AddressDataAccess.PRIMARY_IND,1);

            dbc.addOrderBy(AddressDataAccess.POSTAL_CODE);

            AddressDataVector addressDV = AddressDataAccess.select(pCon,dbc);

            IdVector zipCodes = new IdVector();

            String prevZipCode = "-1";

            HashMap siteAddrHM = new HashMap();

            for(Iterator iter=addressDV.iterator(); iter.hasNext(); ) {

                AddressData aD = (AddressData) iter.next();

                String zipCode = aD.getPostalCode();

                if (zipCode == null) {

                    zipCode = "";

                } else {

                    zipCode = zipCode.trim();

                    if(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(aD.getCountryCd())){

                        if (zipCode.length() > 5) {

                            zipCode = zipCode.substring(0, 5);

                        }

                    }

                }

                aD.setPostalCode(zipCode);

                siteAddrHM.put(new Integer(aD.getBusEntityId()), aD);

                if(!prevZipCode.equals(zipCode)) {

                    prevZipCode = zipCode;

                    zipCodes.add(zipCode);

                }

            }



            //Get main distributors for catalogs

            dbc = new DBCriteria();

            dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID,catalogIdV);

            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);

            dbc.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);

            CatalogAssocDataVector catAssocDV =

               CatalogAssocDataAccess.select(pCon, dbc);

             /*

              String q = "select cs.bus_entity_id, count(cs.item_id) qty " +

                         " from clw_catalog_structure cs " +

                         " where catalog_id = " + catid +

                         " group by cs.bus_entity_id " +

                         " order by qty desc ";

              stmt = pCon.createStatement();

              ResultSet rs = stmt.executeQuery(q);

              if(rs.next()) {

                distId = rs.getInt(1);

              }

              rs.close();

              stmt.close();

              */

            HashMap catalogDistHM = new HashMap();

            IdVector distIdV = new IdVector();

            int prevDistId = 0;

            for(Iterator iter=catAssocDV.iterator(); iter.hasNext();) {

                CatalogAssocData caD = (CatalogAssocData) iter.next();

                catalogDistHM.put(new Integer(caD.getCatalogId()),caD);

                int distId = caD.getBusEntityId();

                if(prevDistId!=distId) {

                    prevDistId = distId;

                    distIdV.add(new Integer(distId));

                }

            }



/////////////////////////////////////////////////



            // dist schedules

            dbc = new DBCriteria();

            dbc.addOneOf(ScheduleDataAccess.BUS_ENTITY_ID,distIdV);

            dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_STATUS_CD,

                    RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE);

            dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD,

                    RefCodeNames.SCHEDULE_TYPE_CD.DELIVERY);

            ScheduleDataVector scheduleDV =

                    ScheduleDataAccess.select(pCon,dbc);

            IdVector scheduleIdV = new IdVector();

            HashMap schedDistHM = new HashMap();

            for(Iterator iter=scheduleDV.iterator(); iter.hasNext();) {

                ScheduleData sD = (ScheduleData) iter.next();

                scheduleIdV.add(new Integer(sD.getScheduleId()));

                schedDistHM.put(new Integer(sD.getScheduleId()), sD);

            }





            dbc = new DBCriteria();

            dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleIdV);

            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,

                    RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);

            dbc.addOneOf(ScheduleDetailDataAccess.VALUE,zipCodes);



            ScheduleDetailDataVector scheduleDetailDV =

                ScheduleDetailDataAccess.select(pCon,dbc);

            HashMap distZipSchedHM = new HashMap();



            for(Iterator iter=scheduleDetailDV.iterator(); iter.hasNext();) {

                ScheduleDetailData sdD = (ScheduleDetailData) iter.next();

                String country = sdD.getCountryCd();

                if(country==null) country = RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES;

                Integer schedIdI = new Integer(sdD.getScheduleId());

                ScheduleData sD = (ScheduleData) schedDistHM.get(schedIdI);

                int distId = sD.getBusEntityId();

                String key = distId + country + sdD.getValue();

                if(!distZipSchedHM.containsKey(key)) {

                    distZipSchedHM.put(key, schedIdI);

                }

            }

            //YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY



            //Get site shedules

            SiteDeliveryScheduleViewVector siteDeliverySchedDV =

                    BusEntityDAO.getDeliverySchs(pCon, pAccountId,0);



            HashMap scheduleJoinHM = new HashMap();

            HashMap integratedSchedHM = new HashMap();

            HashMap siteScheduleHM = new HashMap();

            for(Iterator iter=siteDeliverySchedDV.iterator(); iter.hasNext();) {

                SiteDeliveryScheduleView siteDelivSchVw = (SiteDeliveryScheduleView) iter.next();

                int siteId = siteDelivSchVw.getSiteId();

                Integer siteIdI = new Integer(siteId);

                AddressData aD  = (AddressData) siteAddrHM.get(siteIdI);

                CatalogAssocData siteCatalogCaD = (CatalogAssocData) siteCatalogHM.get(siteIdI);

                if(siteCatalogCaD==null) {

                    continue;

                }

                int catalogId = siteCatalogCaD.getCatalogId();

                Integer catalogIdI = new Integer(catalogId);

                CatalogAssocData catalogDistCaD = (CatalogAssocData) catalogDistHM.get(catalogIdI);

                if(catalogDistCaD==null) {

                    continue;

                }

                int distId = catalogDistCaD.getBusEntityId();

                String distSchedKey = distId + aD.getCountryCd() + aD.getPostalCode();

                Integer scheduleIdI = (Integer) distZipSchedHM.get(distSchedKey);

                if(scheduleIdI==null) {

                    continue;

                }

                int scheduleId = scheduleIdI.intValue();

                ScheduleJoinView sjVw = (ScheduleJoinView) scheduleJoinHM.get(scheduleIdI);

                if(sjVw==null) {

                    sjVw = distBean.getDeliveryScheduleById(scheduleId);

                    scheduleJoinHM.put(scheduleIdI,sjVw);

                }

                String siteScheduleKey = siteDelivSchVw.getSiteScheduleType()+

                        siteDelivSchVw.getIntervWeek()+

                        (siteDelivSchVw.getWeek1ofMonth()?"1":"")+

                        (siteDelivSchVw.getWeek2ofMonth()?"2":"")+

                        (siteDelivSchVw.getWeek3ofMonth()?"3":"")+

                        (siteDelivSchVw.getWeek4ofMonth()?"4":"")+

                        (siteDelivSchVw.getLastWeekofMonth()?"5":"");



                String integratedKey = siteScheduleKey + scheduleId;

                ScheduleOrderDates sods = (ScheduleOrderDates) integratedSchedHM.get(integratedKey);

                if(sods==null) {

                    ScheduleProc scheduleProc = new ScheduleProc(sjVw, siteDelivSchVw, accountCutoffDays);

                    scheduleProc.initSchedule();

                    sods = scheduleProc.getOrderDeliveryDates(currdate,currdate);

                    if(sods!=null) {

                        integratedSchedHM.put(integratedKey,sods);

                    }

                    else {

                        continue;

                    }

                }

                siteScheduleHM.put(siteIdI,sods);

            }

            return siteScheduleHM;

    }







    public SiteDataVector addSites(SiteDataVector sites) throws RemoteException, DuplicateNameException {

        Iterator i = sites.iterator();

        try {

            while (i.hasNext()) {

             SiteData sd = (SiteData)i.next();

             int accountId = sd.getAccountBusEntity().getBusEntityId();

             sd = addSite(sd, accountId);

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(e.getMessage());

        }

        return sites;

    }



    public int addDistributorAssoc(int siteId, int distId, String userName,

            ErrorHolderViewVector errorHolder) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            Site siteAPI = APIAccess.getAPIAccess().getSiteAPI();

            Distributor distributorAPI = APIAccess.getAPIAccess()

                    .getDistributorAPI();

            SiteData site = siteAPI.getSite(siteId);

            if (site == null) {

                errorHolder.add(new ErrorHolderView("ERROR", "Not found site:"

                        + siteId));

            }

            DistributorData distributor = distributorAPI.getDistributor(distId);

            if (distributor == null) {

                errorHolder.add(new ErrorHolderView("ERROR",

                        "Not found distributor:" + distId));

            }

            DBCriteria cr = new DBCriteria();

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, siteId);

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_SITE);

            BusEntityAssocDataVector result = BusEntityAssocDataAccess.select(conn, cr);

            BusEntityAssocData beaData = null;

            if (result != null && result.size() > 1) {

                errorHolder.add(new ErrorHolderView("ERROR",

                        "Was found more than one BUS_ENTITY_ASSOC type DISTRIBUTOR_SITE for site:"

                                + siteId));

            }

            int oldDistId = -1;

            if (errorHolder.size() == 0) {

                if (result == null || result.size() == 0) {

                    beaData = BusEntityAssocData.createValue();

                    beaData.setBusEntity2Id(siteId);

                    beaData.setBusEntity1Id(distId);

                    beaData.setAddBy(userName);

                    beaData.setModBy(userName);

                    beaData

                            .setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_SITE);

                    BusEntityAssocDataAccess.insert(conn, beaData);

                } else {

                    beaData = (BusEntityAssocData) result.get(0);

                    oldDistId = beaData.getBusEntity1Id();

                    beaData.setBusEntity1Id(distId);

                    beaData.setModBy(userName);

                    BusEntityAssocDataAccess.update(conn, beaData);

                }

            }

            return oldDistId;

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            closeConnection(conn);

        }

    }





    public ErrorHolderViewVector removeDistributorAssoc(int siteId, int distId, String userName) throws RemoteException {

        Connection conn = null;

        ErrorHolderViewVector errorHolder = new ErrorHolderViewVector();

        try {

            conn = getConnection();

            Site siteAPI = APIAccess.getAPIAccess().getSiteAPI();

            Distributor distributorAPI = APIAccess.getAPIAccess().getDistributorAPI();

            SiteData site = siteAPI.getSite(siteId);

            if (site == null) {

                errorHolder.add(new ErrorHolderView("ERROR", "Not found site:" + siteId));

            }

            DistributorData distributor = distributorAPI.getDistributor(distId);

            if (distributor == null) {

                errorHolder.add(new ErrorHolderView("ERROR", "Not found distributor:" + distId));

            }

            DBCriteria cr = new DBCriteria();

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, siteId);

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_SITE);

            BusEntityAssocDataVector dV = BusEntityAssocDataAccess.select(conn, cr);

            if (dV != null && dV.size() > 0) {

               BusEntityAssocData beaData = (BusEntityAssocData)dV.get(0);

               BusEntityAssocDataAccess.remove(conn, beaData.getBusEntityAssocId());

            } else {

              errorHolder.add(new ErrorHolderView("ERROR", "Not found associated site " + siteId + " for distributor " + distId));

            }

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            closeConnection(conn);

        }

        return errorHolder;

    }



     /**

     * Getting delivery date information assocated for site.

     */

 /* Commented temporrary because returns cutoff Time instead of cutoff Date+Time object

    public SiteDeliveryDataVector getSiteDeliveryDateCollection(int siteId)

           throws DataNotFoundException, RemoteException{

       SiteDeliveryDataVector siteDeliveryDataVector=null;

       DBCriteria  dbCrit=new DBCriteria();

       Connection conn = null;

       try {

           if(siteId>0){

               conn = getConnection();

               dbCrit.addEqualTo(SiteDeliveryDataAccess.BUS_ENTITY_ID,siteId);

               dbCrit.addEqualTo(SiteDeliveryDataAccess.DELIVERY_FLAG,1);

               dbCrit.addEqualTo(SiteDeliveryDataAccess.STATUS_CD,"ACTIVE");

               dbCrit.addGreaterThan(SiteDeliveryDataAccess.CUTOFF_DAY,0);

               //dbCrit.addGreaterThan(SiteDeliveryDataAccess.CUTOFF_SYSTEM_TIME,new Date());

               dbCrit.addCondition(" CUTOFF_SYSTEM_TIME > SYSDATE ");

               dbCrit.addOrderBy(SiteDeliveryDataAccess.CUTOFF_SYSTEM_TIME);

               siteDeliveryDataVector=SiteDeliveryDataAccess.select(conn,dbCrit);

               return siteDeliveryDataVector;

           }

       } catch (Exception e) {

           e.printStackTrace();

           throw new RemoteException("Error :" + e.getMessage());

       } finally {

           closeConnection(conn);

       }

       return null;

   }

*/

   public SiteDeliveryDataVector getSiteDeliveryDateCollection(int siteId)

          throws DataNotFoundException, RemoteException{

      SiteDeliveryDataVector siteDeliveryDataVector=new SiteDeliveryDataVector();

      SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATETIME_PATTERN);



      Connection conn = null;

      try {

        if (siteId > 0) {

          conn = getConnection();

          String sql =

              "select " +

              "BUS_ENTITY_ID,SITE_DELIVERY_ID,CUTOFF_SITE_TIME,CUTOFF_SYSTEM_TIME,DELIVERY_DAY,WEEK,YEAR,DELIVERY_DATE " +

              "from CLW_SITE_DELIVERY " +

              "where BUS_ENTITY_ID =" + siteId + " and " +

              "DELIVERY_FLAG = 1 and " +

              "STATUS_CD = 'ACTIVE' and " +

              "CUTOFF_DAY > 0 and " +

              "CUTOFF_SYSTEM_TIME > TO_DATE('"+sdf.format(new Date())+"', 'mm/dd/yyyy HH:mi AM') " +

//              "CUTOFF_SYSTEM_TIME > SYSDATE " +

              "ORDER BY CUTOFF_SYSTEM_TIME ASC";



          Statement stmt = conn.createStatement();

          ResultSet pcRS = stmt.executeQuery(sql);

          while (pcRS.next()) {

            SiteDeliveryData deliveryData = SiteDeliveryData.createValue();

            deliveryData.setBusEntityId(pcRS.getInt("BUS_ENTITY_ID"));

            deliveryData.setSiteDeliveryId(pcRS.getInt("SITE_DELIVERY_ID"));

            deliveryData.setCutoffSiteTime(Utility.getDateTime(pcRS.getDate( "CUTOFF_SITE_TIME"), pcRS.getTime("CUTOFF_SITE_TIME")));

            deliveryData.setCutoffSystemTime(Utility.getDateTime(pcRS.getDate("CUTOFF_SYSTEM_TIME"), pcRS.getTime("CUTOFF_SYSTEM_TIME")));

            deliveryData.setDeliveryDay(pcRS.getInt("DELIVERY_DAY"));

            deliveryData.setWeek(pcRS.getInt("WEEK"));

            deliveryData.setYear(pcRS.getInt("YEAR"));
            deliveryData.setDeliveryDate(pcRS.getDate("DELIVERY_DATE"));

            siteDeliveryDataVector.add(deliveryData);

          }

          stmt.close();

         } else {

           return null;

         }

        }

        catch (Exception e) {

          throw processException(e);

        }

        finally {

          closeConnection(conn);

        }

        return siteDeliveryDataVector;

   }





   public SiteDeliveryDataVector getNextSiteDeliveryData(int pSiteId)

            throws RemoteException {

       SiteDeliveryDataVector siteDeliveryDataVector=new SiteDeliveryDataVector();

       SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATETIME_PATTERN);

       Connection conn = null;

       try {

           conn = getConnection();

           String sql =

               "select " +

               "CUTOFF_SITE_TIME,CUTOFF_SYSTEM_TIME,DELIVERY_DAY,WEEK,YEAR,DELIVERY_DATE " +

               "from (select  * from CLW_SITE_DELIVERY " +

                      "where BUS_ENTITY_ID =" + pSiteId + " and " +

                      "DELIVERY_FLAG = 1 and " +

                      "STATUS_CD = 'ACTIVE' and " +

                      "CUTOFF_DAY > 0 and " +

                      "CUTOFF_SYSTEM_TIME > TO_DATE('"+sdf.format(new Date())+"', 'mm/dd/yyyy HH:mi AM') " +

                      "ORDER BY CUTOFF_SYSTEM_TIME ASC) where ROWNUM <= 2";



           Statement stmt = conn.createStatement();

           ResultSet pcRS = stmt.executeQuery(sql);

           while (pcRS.next()) {

               SiteDeliveryData deliveryData = SiteDeliveryData.createValue();

               deliveryData.setCutoffSiteTime(Utility.getDateTime(pcRS.getDate("CUTOFF_SITE_TIME"), pcRS.getTime("CUTOFF_SITE_TIME") ));

               deliveryData.setCutoffSystemTime(Utility.getDateTime(pcRS.getDate("CUTOFF_SYSTEM_TIME"), pcRS.getTime("CUTOFF_SYSTEM_TIME") ));

               deliveryData.setDeliveryDay(pcRS.getInt("DELIVERY_DAY"));

               deliveryData.setWeek(pcRS.getInt("WEEK"));

               deliveryData.setYear(pcRS.getInt("YEAR"));
               deliveryData.setDeliveryDay(pcRS.getInt("DELIVERY_DAY"));

               siteDeliveryDataVector.add(deliveryData);



           }

           stmt.close();



       } catch (Exception e) {

           throw processException(e);

       } finally {

           closeConnection(conn);

       }

       return siteDeliveryDataVector;

  }





  public void addDeliveryDateForSite(String deliveryDate, int pOrderId, String pUserName )  throws RemoteException

  {

    Connection conn = null;



    try {

      conn = getConnection();

      OrderMetaData omD = OrderMetaData.createValue();

      omD.setOrderId(pOrderId);

      omD.setName(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE);

      omD.setValue(deliveryDate);

      omD.setAddBy(pUserName);

      omD.setModBy(pUserName);

      omD = OrderMetaDataAccess.insert(conn, omD);

    }

    catch (Exception exc) {

      exc.printStackTrace();

      throw new RemoteException("Error. " + exc.getMessage());

    }

    finally {

       closeConnection(conn);

    }

  }





  public SiteViewVector getSiteCollectionByDistributor(String pName, int pMatch, int pDistId) throws RemoteException

  {

    Connection conn = null;

    SiteViewVector result =  new SiteViewVector();

    try {

          conn = getConnection();

          DBCriteria crit = new DBCriteria();

          switch (pMatch) {

              case Site.EXACT_MATCH:

                  crit.addEqualTo(BusEntityDataAccess.SHORT_DESC, pName);

                  break;

              case Site.EXACT_MATCH_IGNORE_CASE:

                  crit.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC,pName);

                  break;

              case Site.BEGINS_WITH:

                  crit.addLike(BusEntityDataAccess.SHORT_DESC, pName + "%");

                  break;

              case Site.BEGINS_WITH_IGNORE_CASE:

                  crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,pName + "%");

                  break;

              case Site.CONTAINS:

                  crit.addLike(BusEntityDataAccess.SHORT_DESC, "%" + pName + "%");

                  break;

              case Site.CONTAINS_IGNORE_CASE:

                  crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, "%" + pName + "%");

                  break;

              default:

                  throw new RemoteException("getSiteByName: Bad match specification");

          }

          crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

          if (pDistId != 0) {

              StringBuffer buf = new StringBuffer();

              buf.append(BusEntityDataAccess.BUS_ENTITY_ID);

              buf.append(" IN (SELECT ");

              buf.append(BusEntityAssocDataAccess.BUS_ENTITY2_ID);

              buf.append(" FROM ");

              buf.append(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC);

              buf.append(" WHERE ");

              buf.append(BusEntityAssocDataAccess.BUS_ENTITY1_ID);

              buf.append(" = ");

              buf.append(pDistId);

              buf.append(" AND ");

              buf.append(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD);

              buf.append(" = '");

              buf.append(RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_SITE);

              buf.append("')");

              crit.addCondition(buf.toString());

          }

          crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);

          IdVector busEntityVec = BusEntityDataAccess.selectIdOnly(conn,crit);

          if (busEntityVec != null && busEntityVec.size() > 0) {

            QueryRequest qr = new QueryRequest();

            qr.filterBySiteIdList(busEntityVec);

            result = getSiteCollection(qr);

          }

    } catch (Exception e) {

        throw processException(e);

    } finally {

        closeConnection(conn);

    }

    return result;

  }





    public SiteViewVector getSiteCollectionByDistributor(QueryRequest pQueryRequest, int pDistId) throws RemoteException

    {

        Connection conn = null;

        SiteViewVector result = new SiteViewVector();

        try {

            conn = getConnection();

            DBCriteria cr = new DBCriteria();

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pDistId);

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_SITE);

            BusEntityAssocDataVector sites = BusEntityAssocDataAccess.select(conn, cr);



            if (sites != null && sites.size() > 0) {

                IdVector siteIds = new IdVector();

                for (int i=0; i<sites.size(); i++) {

                    BusEntityAssocData ba = (BusEntityAssocData)sites.get(i);

                    siteIds.add(new Integer(ba.getBusEntity2Id()));

                }

                pQueryRequest.filterBySiteIdList(siteIds);

                result = getSiteCollection(pQueryRequest);

            }

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            closeConnection(conn);

        }

        return result;



    }



    public SiteViewVector getSiteCollectionByServiceProvider(QueryRequest pQueryRequest, int pSpId) throws RemoteException

    {

        Connection conn = null;

        SiteViewVector result = new SiteViewVector();

        try {

            conn = getConnection();

            DBCriteria cr = new DBCriteria();

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pSpId);

            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE);

            BusEntityAssocDataVector sites = BusEntityAssocDataAccess.select(conn, cr);



            if (sites != null && sites.size() > 0) {

                IdVector siteIds = new IdVector();

                for (int i = 0; i < sites.size(); i++) {

                    BusEntityAssocData ba = (BusEntityAssocData)sites.get(i);

                    siteIds.add(new Integer(ba.getBusEntity2Id()));

                }

                pQueryRequest.filterBySiteIdList(siteIds);

                result = getSiteCollection(pQueryRequest);

            }

        } catch (Exception e) {

            throw new RuntimeException(e);

        } finally {

            closeConnection(conn);

        }

        return result;



    }



    /**

     * Get all states supported for sites of this user.

     *

     * @param  user id

     * @return set of string objects

     * @throws RemoteException Required by EJB 1.0

     */

    public List getSiteStatesForUserDesc(int userId) throws RemoteException {



        Connection conn = null;

        ArrayList result = new ArrayList();



        try {

            if (userId > 0) {



                conn = getConnection();



                String sql = "SELECT MIN(ADDRESS_ID), STATE_PROVINCE_CD FROM CLW_ADDRESS A " +

                        " WHERE  bus_entity_id IN (select bus_entity_id from clw_user_assoc where user_assoc_cd = 'SITE' and user_id = "+userId+")" +

                        " GROUP BY STATE_PROVINCE_CD ";



                Statement stmt = conn.createStatement();



                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {

                    result.add(rs.getString(2));

                }



                rs.close();

                stmt.close();

            }

            return result;



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }



    /**

     * Get all states supported for sites.

     *

     * @param siteIds site ids

     * @return set of string objects

     * @throws RemoteException Required by EJB 1.0

     */

    public List getSiteStates(IdVector siteIds) throws RemoteException {



        Connection conn = null;

        HashSet result = new HashSet();



        try {

            if (siteIds != null && !siteIds.isEmpty()) {



                conn = getConnection();

                String sql;

                int pack = 1000;



                for (int j = 0; j <= (siteIds.size() / pack); j++) {



                    IdVector ids = new IdVector();

                    for (int i = 0; i < (j == siteIds.size() / pack ? (siteIds.size() % pack) : pack); i++) {

                        ids.add(siteIds.get((j * pack) + i));

                    }



                    sql = "SELECT MIN(ADDRESS_ID), STATE_PROVINCE_CD FROM CLW_ADDRESS A " +

                            " WHERE BUS_ENTITY_ID IN ( " + Utility.getAsString(ids) + ")" +

                            " AND STATE_PROVINCE_CD is not null" +

                            " GROUP BY STATE_PROVINCE_CD ";



                    Statement stmt = conn.createStatement();



                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {

                        result.add(rs.getString(2));

                    }



                    rs.close();

                    stmt.close();



                }

            }



            return Arrays.asList(result.toArray());



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }



    public SiteData updateBudgetSpendingInfo(SiteData siteD) throws RemoteException {

    	Connection conn = null;

        try {

        	conn = getConnection();

        	setBudgetSpendingInfo(conn, siteD);

        	return siteD;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }

    public HashMap getAcrossAccountsSiteRefNumberMapConfigOnly(int storeId, int userId) throws RemoteException {
        Connection conn = null;
        try {
            HashMap siteBudgetRefNums = new HashMap();
            conn = getConnection();
            String sql =
                "select distinct ap.clw_value||'/'||sp.clw_value, site_acc.bus_entity1_id\n" +
                "from \n" +
                    "clw_bus_entity_assoc site_acc, \n" +
                    "clw_bus_entity_assoc acc_store, \n" +
                    "clw_user_assoc user_as, \n" +
                    "clw_property sp, \n" +
                    "clw_property ap \n" +
                "where \n" +
                    "site_acc.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n" +
                    "and acc_store.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "' \n" +
                    "and site_acc.bus_entity2_id=acc_store.bus_entity1_id \n" +
                    "and acc_store.bus_entity2_id=" + storeId + " \n" +
                    "and user_as.user_id=" + userId + " \n" +
                    "and user_as.user_assoc_cd='" + RefCodeNames.USER_ASSOC_CD.SITE + "' \n" +
                    "and user_as.bus_entity_id is not null \n" +
                    "and user_as.bus_entity_id=site_acc.bus_entity1_id \n" +
                    "and sp.short_desc='SITE_REFERENCE_NUMBER' \n" +
                    "and sp.clw_value is not null \n" +
                    "and sp.bus_entity_id=site_acc.bus_entity1_id \n" +
                    "and ap.short_desc='CUST_MAJ' \n" +
                    "and ap.clw_value is not null \n" +
                    "and ap.bus_entity_id=site_acc.bus_entity2_id\n";
            logInfo("[getAcrossAccountsSiteRefNumberMapConfigOnly] sql-1: " + sql);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            	siteBudgetRefNums.put(rs.getString(1), rs.getInt(2));
            }
            rs.close();
            stmt.close();

            logInfo("[getAcrossAccountsSiteRefNumberMapConfigOnly] siteBudgetRefNums.size: " + siteBudgetRefNums.size());
            return siteBudgetRefNums;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public HashMap getAcrossAccountsSiteRefNumberMap(int storeId, int userId, List custMajSiteRefNums) throws RemoteException {
        Connection conn = null;
        HashMap result = new HashMap();
        try {
            if (custMajSiteRefNums != null && !custMajSiteRefNums.isEmpty()) {
                TreeSet setOfSiteRefNums = new TreeSet(custMajSiteRefNums);
                TreeSet setOfUserAccounts = new TreeSet();
                conn = getConnection();
                Statement stmt = conn.createStatement();
                
                String sql = "Select distinct be2.bus_entity_id,ap.clw_value||'/'||sp.clw_value from clw_bus_entity be1,\n" +
                          "clw_bus_entity be2,\n" +
                          "clw_bus_entity_assoc beas,\n" +
                          "clw_bus_entity_assoc bea,\n" +
                          "clw_property sp,\n" +
                          "clw_property ap \n" +
                          " where ap.bus_entity_id=be1.bus_entity_id \n" +
                          " and ap.short_desc='CUST_MAJ' \n" +
                          " and be1.bus_entity_id=bea.bus_entity2_id\n" +
                          " and bea.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'\n" +
                          " and be2.bus_entity_id=bea.bus_entity1_id\n" +
                          " and beas.BUS_ENTITY_ASSOC_CD ='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "'\n" +
                          " and beas.bus_entity1_id=bea.bus_entity2_id\n" +
                          " and beas.bus_entity2_id = " + storeId + "\n" +
                          " and sp.short_desc='" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "'" + "\n" +
                          " and sp.bus_entity_id = be2.bus_entity_id " +
                          " and ap.clw_value||'/'||sp.clw_value in (" + Utility.toCommaSting(custMajSiteRefNums, '\'') +  ") \n";
                logInfo("[getAcrossAccountsSiteRefNumberMap] sql-1: " + sql);
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String siteRef = rs.getString(2);
                    result.put(siteRef, new Integer(rs.getInt(1)));
                }
                rs.close();
                stmt.close();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }



    public NscSiteViewVector getAllNscSites(int accountId, String custMaj) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            String sql = "select be2.bus_entity_id,be2.short_desc,be2.bus_entity_status_cd,p2.clw_value, be1.bus_entity_id," + custMaj + ", a.address_id,a.bus_entity_id,a.user_id,address1,a.address2,a.address3,a.address4,a.address_status_cd,a.address_type_cd,a.add_by,a.add_date,a.city,country_cd,a.county_cd,a.mod_by,a.mod_date,a.name1,a.name2,a.postal_code,a.primary_ind,a.state_province_cd \n" +
                    " from clw_bus_entity be1 ,\n" +
                    "        clw_bus_entity be2 left join clw_address a on a.bus_entity_id = be2.bus_entity_id and address_type_cd='" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "' and address_status_cd='" + RefCodeNames.ADDRESS_STATUS_CD.ACTIVE + "',\n" +
                    "        clw_bus_entity_assoc bea,\n" +
                    "        clw_property p2\n" +
                    "        where be1.bus_entity_id=?\n" +
                    "        and   be1.bus_entity_id=bea.bus_entity2_id\n" +
                    "        and   bea.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'\n" +
                    "        and   be2.bus_entity_id=bea.bus_entity1_id\n" +
                    "        and   p2.short_desc='" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "'\n" +
                    "        and   p2.bus_entity_id = be2.bus_entity_id";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            ResultSet rs = pstmt.executeQuery();
            NscSiteViewVector v = new NscSiteViewVector();
            while (rs.next()) {
                NscSiteView x = NscSiteView.createValue();

                x.setSiteId(rs.getInt(1));

                x.setSiteName(rs.getString(2));

                x.setStatus(rs.getString(3));

                x.setSiteReferenceNumber(rs.getString(4));

                x.setAccountId(rs.getInt(5));

                x.setCustMaj(rs.getString(6));

                AddressData xa = AddressData.createValue();

                xa.setAddressId(rs.getInt(6 + 1));

                xa.setBusEntityId(rs.getInt(6 + 2));

                xa.setUserId(rs.getInt(6 + 3));

                xa.setAddress1(rs.getString(6 + 4));

                xa.setAddress2(rs.getString(6 + 5));

                xa.setAddress3(rs.getString(6 + 6));

                xa.setAddress4(rs.getString(6 + 7));

                xa.setAddressStatusCd(rs.getString(6 + 8));

                xa.setAddressTypeCd(rs.getString(6 + 9));

                xa.setAddBy(rs.getString(6 + 10));

                xa.setAddDate(rs.getTimestamp(6 + 11));

                xa.setCity(rs.getString(6 + 12));

                xa.setCountryCd(rs.getString(6 + 13));

                xa.setCountyCd(rs.getString(6 + 14));

                xa.setModBy(rs.getString(6 + 15));

                xa.setModDate(rs.getTimestamp(6 + 16));

                xa.setName1(rs.getString(6 + 17));

                xa.setName2(rs.getString(6 + 18));

                xa.setPostalCode(rs.getString(6 + 19));

                xa.setPrimaryInd(rs.getBoolean(6 + 20));

                xa.setStateProvinceCd(rs.getString(6 + 21));



                x.setAddress(xa);



                v.add(x);

            }

            rs.close();

            pstmt.close();



            return v;



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }





    public void updateNscSites(NscSiteViewVector nscSites, String user) throws RemoteException {

        Connection conn = null;

        ArrayList errors = new ArrayList();

        try {

            if (nscSites != null) {

                conn = getConnection();



                Iterator it = nscSites.iterator();

                while (it.hasNext()) {

                    NscSiteView nscSite = (NscSiteView) it.next();

                    try {

                        if (nscSite.getSiteId() > 0) {



                            String updateBeSql = "update " + BusEntityDataAccess.CLW_BUS_ENTITY +

                                    " set " + BusEntityDataAccess.BUS_ENTITY_STATUS_CD + "= ? " +

                                    ", " + BusEntityDataAccess.SHORT_DESC + "= ? " +

                                    ", " + BusEntityDataAccess.MOD_BY + "= ? " +

                                    ", " + BusEntityDataAccess.MOD_DATE + "= ? " +

                                    " where " + BusEntityDataAccess.BUS_ENTITY_ID + " = ? ";





                            PreparedStatement pstmt = conn.prepareStatement(updateBeSql);



                            pstmt.setString(1, nscSite.getStatus());

                            pstmt.setString(2, nscSite.getSiteName());

                            pstmt.setString(3, user);

                            pstmt.setTimestamp(4, DBAccess.toSQLTimestamp(new java.util.Date(System.currentTimeMillis())));

                            pstmt.setInt(5, nscSite.getSiteId());



                            pstmt.executeUpdate();



                            pstmt.close();



                            AddressDataAccess.update(conn, nscSite.getAddress());



                            PropertyUtil propUtil = new PropertyUtil(conn);

                            propUtil.saveValue(0, nscSite.getSiteId(),

                                    RefCodeNames.PROPERTY_TYPE_CD.EXTRA,

                                    RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER,

                                    nscSite.getSiteReferenceNumber(),null);



                            if(nscSite.getProperties()!=null && nscSite.getProperties().size()>0 ){

                            	propUtil.saveCollection(nscSite.getProperties());

                            }

                        } else {



                            SiteData siteData = SiteData.createValue();



                            BusEntityData bed = BusEntityData.createValue();

                            bed.setShortDesc(nscSite.getSiteName());

                            bed.setBusEntityStatusCd(nscSite.getStatus());

                            bed.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);

                            bed.setLocaleCd("unk");



                            PropertyData nprop = PropertyData.createValue();

                            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);

                            nprop.setValue(nscSite.getSiteReferenceNumber());

                            nprop.setAddBy(user);

                            nprop.setModBy(user);



                            siteData.setBusEntity(bed);

                            siteData.setSiteAddress(nscSite.getAddress());

                            siteData.getMiscProperties().add(nprop);

                            siteData.setDataFieldProperties(nscSite.getProperties());



                            addSite(siteData, nscSite.getAccountId());

                        }

                    } catch (Exception e) {

                        errors.add("NSCSiteView:" + nscSite + " Error message:" + e.getMessage());

                        e.printStackTrace();

                    }

                }

                if (errors.size() > 0) {

                    throw new RemoteException("Updates errors:" + errors);

                }



            }



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }

    public void populateNscSiteProperties( NscSiteViewVector nscSites)throws RemoteException{

    	 Connection conn = null;

    	 PropertyDataVector propDV = new PropertyDataVector();

         try {

            conn = getConnection();

	    	if (nscSites != null && nscSites.size()>0){

	            int accountId  = ((NscSiteView)nscSites.get(0)).getAccountId();



	            // getting additional properties

	            DBCriteria dbc1 = new DBCriteria();

	            dbc1.addEqualTo( BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

	            dbc1.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountId);

	            String subSql1 = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc1);

	            DBCriteria dbc2 = new DBCriteria();

	            dbc2.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, subSql1);

	            String subSql2 = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbc2);

	            DBCriteria dbc = new DBCriteria();

	     //       dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

	            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);

	            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, "NSC Location Number");

	            dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, subSql2);

	            propDV = PropertyDataAccess.select(conn, dbc);

	            if (propDV != null && propDV.size()>0){

	            	for (Object oSite: nscSites){

	            		NscSiteView site = (NscSiteView)oSite;

		            	PropertyDataVector sitePropDV = new PropertyDataVector();

		            	for (Object oProp: propDV ){

		            		PropertyData siteProp= (PropertyData)oProp;

		            		if (siteProp.getBusEntityId()==site.getSiteId()){

		            			sitePropDV.add(siteProp);

		            		}

		            	}

		            	site.setProperties(sitePropDV);

	            	}

	            }

	    	}

    	} catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }



    public PairViewVector getSiteIdAndName(int pUserId,

                                           int pSiteId,

                                           String pCountry,

                                           String pState,

                                           String pStatus,

                                           int pMaxRows) throws RemoteException {



        Connection conn = null;

        PairViewVector result = new PairViewVector();



        try {

            if (pUserId > 0) {



                conn = getConnection();



                String sql = "SELECT BE.BUS_ENTITY_ID, BE.SHORT_DESC FROM CLW_BUS_ENTITY BE,CLW_USER_ASSOC UA,  CLW_ADDRESS A\n" +

                        "    WHERE  BE.BUS_ENTITY_TYPE_CD = '" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "' \n" +

                        "      AND BE.BUS_ENTITY_ID = A.BUS_ENTITY_ID \n" +

                        "      AND  A.ADDRESS_TYPE_CD IN ('" + RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING + "','" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "')\n" +

                        //(Utility.isSet(pState) ? "      AND UPPER(A.STATE_PROVINCE_CD) = '" + pState.toUpperCase() + "' \n" : "") +
                        (Utility.isSet(pState) ? "      AND UPPER(A.STATE_PROVINCE_CD) = upper(?) \n" : "") +

                        //(Utility.isSet(pCountry) ? "      AND UPPER(A.COUNTRY_CD) = '" + pCountry.toUpperCase() + "' \n" : "") +
                        (Utility.isSet(pCountry) ? "      AND UPPER(A.COUNTRY_CD) = ? \n" : "") +

                        "      AND BE.BUS_ENTITY_ID = UA.BUS_ENTITY_ID\n" +

                        "      AND UA.USER_ASSOC_CD='" + RefCodeNames.USER_ASSOC_CD.SITE + "'\n" +

                        "      AND UA.USER_ID=" + pUserId + "\n" +

                        //(Utility.isSet(pStatus) ? "      AND BE.BUS_ENTITY_STATUS_CD = '" + pStatus + "' \n" : "") +
                        (Utility.isSet(pStatus) ? "      AND BE.BUS_ENTITY_STATUS_CD = ? \n" : "") +

                        (pSiteId > 0 ? "      AND BE.BUS_ENTITY_ID = " + pSiteId + " \n" : "") +

                        "    ORDER BY BE.SHORT_DESC ASC";





                PreparedStatement stmt = conn.prepareStatement(sql);
                int i = 1;
                if (Utility.isSet(pState)) {
                    stmt.setString(i++, pState);
                }
                if (Utility.isSet(pCountry)) {
                    stmt.setString(i++, pCountry);
                }
                if (Utility.isSet(pStatus)) {
                    stmt.setString(i++, pStatus);
                }


                if (pMaxRows > 0) {

                    stmt.setMaxRows(pMaxRows);

                }

                logDebug("getSiteIdAndName sql => " + sql);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {

                    PairView x = new PairView();



                    x.setObject1(new Integer(rs.getInt(1)));

                    x.setObject2(rs.getString(2));



                    result.add(x);

                }



                rs.close();

                stmt.close();

            }



            return result;



        } catch (Exception e) {

            e.printStackTrace();

            throw new RemoteException(e.getMessage());

        } finally {

            closeConnection(conn);

        }

    }





    static final Comparator SITE_VIEW_NAME_COMPARE = new Comparator() {

        public int compare(Object o1, Object o2)

        {

            String name1 = ((SiteView)o1).getName();

            String name2 = ((SiteView)o2).getName();

            return Utility.compareToIgnoreCase(name1, name2);

        }

    };



    static final Comparator SITE_VIEW_ID_COMPARE = new Comparator() {

        public int compare(Object o1, Object o2)

        {

            int id1 = ((SiteView)o1).getId();

            int id2 = ((SiteView)o2).getId();

            return id1 - id2;

        }

    };



    private ShoppingRestrictionsViewVector getShoppingRestrictionsBySiteAndAccount(

        int accountId, int siteId) throws RemoteException {

        ShoppingRestrictionsViewVector restrictions = new ShoppingRestrictionsViewVector();

        Connection connection = null;

        try {

            connection = getConnection();

            String sql =

                "SELECT " +

                    "s.SHOPPING_CONTROL_ID, " +

                    "s.ITEM_ID, " +

                    "a.ACCOUNT_ID, " +

                    "s.SITE_ID, " +

                    "s.MAX_ORDER_QTY S_MAX_ORDER_QTY, " +

                    "a.MAX_ORDER_QTY A_MAX_ORDER_QTY, " +

                    "s.RESTRICTION_DAYS, " +

                    "s.CONTROL_STATUS_CD, " +

                    "s.MOD_BY, " +

                    "s.MOD_DATE, " +

                    "a.MOD_BY, " +

                    "a.MOD_DATE " +

                "FROM " +

                    "CLW_SHOPPING_CONTROL s LEFT JOIN CLW_SHOPPING_CONTROL a " +

                    "ON " +

                        "s.ITEM_ID = a.ITEM_ID AND " +

                        "(a.SITE_ID = 0 OR a.SITE_ID IS NULL) AND " +

                        "a.CONTROL_STATUS_CD = '" + RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE + "' AND " +

                        "a.account_id = ? " +

                "WHERE " +

                    "s.SITE_ID = ? AND " +

                    "s.CONTROL_STATUS_CD = '" + RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE + "'";



            PreparedStatement prepStatement = connection.prepareStatement(sql);

            prepStatement.setInt(1, accountId);

            prepStatement.setInt(2, siteId);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {

                ShoppingRestrictionsView restriction = ShoppingRestrictionsView.createValue();

                restriction.setShoppingControlId(resultSet.getInt(1));

                restriction.setItemId(resultSet.getInt(2));

                restriction.setAccountId(resultSet.getInt(3));

                restriction.setSiteId(resultSet.getInt(4));

                restriction.setSiteMaxOrderQty(resultSet.getString(5));

                restriction.setAccountMaxOrderQty(resultSet.getString(6));

                restriction.setRestrictionDays(resultSet.getString(7));

                restriction.setControlStatusCd(resultSet.getString(8));

                restriction.setSiteControlModBy(resultSet.getString(9));

                restriction.setSiteControlModDate(resultSet.getDate(10));

                restriction.setAcctControlModBy(resultSet.getString(11));

                restriction.setAcctControlModDate(resultSet.getDate(12));

                restriction.setTag(0);

                restrictions.add(restriction);

            }

            resultSet.close();

            prepStatement.close();

        }

        catch (Exception ex) {

            ex.printStackTrace();

            throw new RemoteException(ex.getMessage());

        }

        finally {

            try {

                closeConnection(connection);

            }

            catch (Exception ex) {

            }

        }

        return restrictions;

    }



    public ShoppingRestrictionsViewVector getShoppingRestrictionsByAccountOnly(

        int accountId) throws RemoteException {

        ShoppingRestrictionsViewVector restrictions = new ShoppingRestrictionsViewVector();

        Connection connection = null;

        try {

            connection = getConnection();

            String sql =

                "SELECT " +

                    "SHOPPING_CONTROL_ID, " +

                    "ITEM_ID, " +

                    "ACCOUNT_ID, " +

                    "SITE_ID, " +

                    "null S_MAX_ORDER_QTY, " +

                    "MAX_ORDER_QTY A_MAX_ORDER_QTY, " +

                    "RESTRICTION_DAYS, " +

                    "CONTROL_STATUS_CD, " +

                    "MOD_BY, " +

                    "MOD_DATE " +

                "FROM " +

                    "CLW_SHOPPING_CONTROL " +

                "WHERE " +

                    "ACCOUNT_ID = ? AND " +

                    "CONTROL_STATUS_CD = '" + RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE + "' AND " +

                    "(SITE_ID = 0 OR SITE_ID IS NULL)";



            PreparedStatement prepStatement = connection.prepareStatement(sql);

            prepStatement.setInt(1, accountId);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()) {

                ShoppingRestrictionsView restriction = ShoppingRestrictionsView.createValue();

                restriction.setShoppingControlId(resultSet.getInt(1));

                restriction.setItemId(resultSet.getInt(2));

                restriction.setAccountId(resultSet.getInt(3));

                restriction.setSiteId(resultSet.getInt(4));

                restriction.setSiteMaxOrderQty(resultSet.getString(5));

                restriction.setAccountMaxOrderQty(resultSet.getString(6));

                restriction.setRestrictionDays(resultSet.getString(7));

                restriction.setControlStatusCd(resultSet.getString(8));

                restriction.setAcctControlModBy(resultSet.getString(9));

                restriction.setAcctControlModDate(resultSet.getDate(10));

                restriction.setTag(0);

                restrictions.add(restriction);

            }

            resultSet.close();

            prepStatement.close();

        }

        catch (Exception ex) {

            ex.printStackTrace();

            throw new RemoteException(ex.getMessage());

        }

        finally {

            try {

                closeConnection(connection);

            }

            catch (Exception ex) {

            }

        }

        return restrictions;

    }

    public ShoppingRestrictionsViewVector getShoppingRestrictionsByAccountAndItem(

            int accountId, int itemId) throws RemoteException {

            ShoppingRestrictionsViewVector restrictions = new ShoppingRestrictionsViewVector();

            Connection connection = null;

            try {

                connection = getConnection();

                String sql =

                    "SELECT " +

                        "SHOPPING_CONTROL_ID, " +

                        "ITEM_ID, " +

                        "ACCOUNT_ID, " +

                        "SITE_ID, " +

                        "null S_MAX_ORDER_QTY, " +

                        "MAX_ORDER_QTY A_MAX_ORDER_QTY, " +

                        "RESTRICTION_DAYS, " +

                        "CONTROL_STATUS_CD, " +

                        "MOD_BY, " +

                        "MOD_DATE " +

                    "FROM " +

                        "CLW_SHOPPING_CONTROL " +

                    "WHERE " +

                        "ACCOUNT_ID = ? AND " +

                        "CONTROL_STATUS_CD = '" + RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE + "' AND " +

                        "(SITE_ID = 0 OR SITE_ID IS NULL) AND " +
                        
                        "ITEM_ID = ?";



                PreparedStatement prepStatement = connection.prepareStatement(sql);

                prepStatement.setInt(1, accountId);
                
                prepStatement.setInt(2, itemId);

                ResultSet resultSet = prepStatement.executeQuery();

                while (resultSet.next()) {

                    ShoppingRestrictionsView restriction = ShoppingRestrictionsView.createValue();

                    restriction.setShoppingControlId(resultSet.getInt(1));

                    restriction.setItemId(resultSet.getInt(2));

                    restriction.setAccountId(resultSet.getInt(3));

                    restriction.setSiteId(resultSet.getInt(4));

                    restriction.setSiteMaxOrderQty(resultSet.getString(5));

                    restriction.setAccountMaxOrderQty(resultSet.getString(6));

                    restriction.setRestrictionDays(resultSet.getString(7));

                    restriction.setControlStatusCd(resultSet.getString(8));

                    restriction.setAcctControlModBy(resultSet.getString(9));

                    restriction.setAcctControlModDate(resultSet.getDate(10));

                    restriction.setTag(0);

                    restrictions.add(restriction);

                }

                resultSet.close();

                prepStatement.close();

            }

            catch (Exception ex) {

                ex.printStackTrace();

                throw new RemoteException(ex.getMessage());

            }

            finally {

                try {

                    closeConnection(connection);

                }

                catch (Exception ex) {

                }

            }

            return restrictions;

        }

    private ShoppingRestrictionsViewVector getShoppingRestrictionsBySiteOnly(

            int siteId) throws RemoteException {

            ShoppingRestrictionsViewVector restrictions = new ShoppingRestrictionsViewVector();

            Connection connection = null;

            try {

                connection = getConnection();

                String sql =

                    "SELECT " +

                        "SHOPPING_CONTROL_ID, " +

                        "ITEM_ID, " +

                        "ACCOUNT_ID, " +

                        "SITE_ID, " +

                        "MAX_ORDER_QTY S_MAX_ORDER_QTY, " +

                        "null A_MAX_ORDER_QTY, " +

                        "RESTRICTION_DAYS, " +

                        "CONTROL_STATUS_CD, " +

                        "MOD_BY, " +

                        "MOD_DATE " +

                    "FROM " +

                        "CLW_SHOPPING_CONTROL " +

                    "WHERE " +

                        "SITE_ID = ? AND " +

                        "CONTROL_STATUS_CD = '" + RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE + "' AND " +

                        "(ACCOUNT_ID = 0 OR ACCOUNT_ID IS NULL)";



                PreparedStatement prepStatement = connection.prepareStatement(sql);

                prepStatement.setInt(1, siteId);

                ResultSet resultSet = prepStatement.executeQuery();

                while (resultSet.next()) {

                    ShoppingRestrictionsView restriction = ShoppingRestrictionsView.createValue();

                    restriction.setShoppingControlId(resultSet.getInt(1));

                    restriction.setItemId(resultSet.getInt(2));

                    restriction.setAccountId(resultSet.getInt(3));

                    restriction.setSiteId(resultSet.getInt(4));

                    restriction.setSiteMaxOrderQty(resultSet.getString(5));

                    restriction.setAccountMaxOrderQty(resultSet.getString(6));

                    restriction.setRestrictionDays(resultSet.getString(7));

                    restriction.setControlStatusCd(resultSet.getString(8));

                    restriction.setSiteControlModBy(resultSet.getString(9));

                    restriction.setSiteControlModDate(resultSet.getDate(10));

                    restriction.setTag(0);

                    restrictions.add(restriction);

                }

                resultSet.close();

                prepStatement.close();

            }

            catch (Exception ex) {

                ex.printStackTrace();

                throw new RemoteException(ex.getMessage());

            }

            finally {

                try {

                    closeConnection(connection);

                }

                catch (Exception ex) {

                }

            }

            return restrictions;

        }



    public ShoppingRestrictionsViewVector getAllShoppingRestrictions(

        int accountId, int catalogId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) 
    throws RemoteException {

        return getShoppingRestrictions(accountId, catalogId, siteId, pCategToCostCenterView,true);

    }

    public ShoppingRestrictionsViewVector getAllShoppingRestrictions(

            int accountId, int catalogId, int siteId) throws RemoteException {

            return getShoppingRestrictions(accountId, catalogId, siteId, true);

    }

    public ShoppingRestrictionsViewVector getShoppingRestrictions(

        int accountId, int catalogId, int siteId) throws RemoteException {

        return getShoppingRestrictions(accountId, catalogId, siteId, false);

    }

    public ShoppingRestrictionsViewVector getShoppingRestrictions(
    		int accountId, int catalogId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) 
    throws RemoteException {

            return getShoppingRestrictions(accountId, catalogId, siteId,pCategToCostCenterView,false);

    }



    private ShoppingRestrictionsViewVector getShoppingRestrictionsForAcctSiteCat(int accountId, int catalogId, int siteId, boolean allRestrictions)

    throws RemoteException {

    	ShoppingRestrictionsViewVector restrictions = new ShoppingRestrictionsViewVector();

    	Connection connection = null;

    	try {

            APIAccess factory = APIAccess.getAPIAccess();



            connection = getConnection();

            String sql =

            	"SELECT " +

            	"s.SHOPPING_CONTROL_ID, " +

            	"a.ITEM_ID, " +

            	"(CASE WHEN s.SHOPPING_CONTROL_ID is null THEN a.ACCOUNT_ID ELSE 0 END) ACCOUNT_ID, " +

            	"(CASE WHEN s.SHOPPING_CONTROL_ID is null THEN 0 ELSE s.SITE_ID END) SITE_ID, " +

            	"s.MAX_ORDER_QTY S_MAX_ORDER_QTY, " +

            	"a.MAX_ORDER_QTY A_MAX_ORDER_QTY, " +

            	"(CASE WHEN s.SHOPPING_CONTROL_ID is null THEN a.RESTRICTION_DAYS ELSE s.RESTRICTION_DAYS END) RESTRICTION_DAYS, " +

            	"s.CONTROL_STATUS_CD, " +

            	"s.MOD_BY S_MOD_BY, " +

            	"s.MOD_DATE S_MOD_DATE, " +

            	"a.MOD_BY A_MOD_BY, " +

            	"a.MOD_DATE A_MOD_DATE " +

            	"FROM " +

            	"(select * from clw_shopping_control where account_id=? " +

            	"and CONTROL_STATUS_CD = '" + RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE + "'" +

            	" AND (SITE_ID = 0 OR SITE_ID IS NULL))a, "+

            	"(select * from clw_shopping_control where site_id= ? " +

            	"AND CONTROL_STATUS_CD = '" + RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE + "'" + ")s "+

                "WHERE " +

                "s.item_id(+)=a.item_id ";
            if (!allRestrictions){
            	sql += "and (s.SHOPPING_CONTROL_ID is not null OR a.MAX_ORDER_QTY>=0) ";
            }

            sql += "and a.item_id IN "+

                "(select item_id from clw_catalog_structure "+

                "where catalog_id= ? "+

                "and catalog_structure_cd='CATALOG_PRODUCT') ";

            

            PreparedStatement prepStatement = connection.prepareStatement(sql);

            prepStatement.setInt(1, accountId);

            prepStatement.setInt(2, siteId);

            prepStatement.setInt(3, catalogId);

            ResultSet resultSet = prepStatement.executeQuery();



            while (resultSet.next()) {

                ShoppingRestrictionsView restriction = ShoppingRestrictionsView.createValue();

                restriction.setShoppingControlId(resultSet.getInt(1));

                restriction.setItemId(resultSet.getInt(2));

                restriction.setAccountId(resultSet.getInt(3));

                restriction.setSiteId(resultSet.getInt(4));

                restriction.setSiteMaxOrderQty(resultSet.getString(5));

                restriction.setAccountMaxOrderQty(resultSet.getString(6));

                restriction.setRestrictionDays(resultSet.getString(7));

                restriction.setControlStatusCd(resultSet.getString(8));

                restriction.setSiteControlModBy(resultSet.getString("S_MOD_BY"));

                restriction.setSiteControlModDate(resultSet.getDate("S_MOD_DATE"));

                restriction.setAcctControlModBy(resultSet.getString("A_MOD_BY"));

                restriction.setAcctControlModDate(resultSet.getDate("A_MOD_DATE"));

                restriction.setTag(0);

                restrictions.add(restriction);

            }

            resultSet.close();

            prepStatement.close();



    	}catch (Exception ex) {

            ex.printStackTrace();

            throw new RemoteException(ex.getMessage());

        }finally {

            try {

                closeConnection(connection);

            }

            catch (Exception ex) {

            }

        }

    	Collections.sort(restrictions, new ShoppingRestrictionsViewComparatorBySku());

        return restrictions;

    }
   
    public ShoppingRestrictionsViewVector getShoppingRestrictions(
            int accountId, int catalogId, int siteId, boolean allRestrictions) throws RemoteException{
    	
    	return getShoppingRestrictions(accountId, catalogId, siteId, null, allRestrictions);
    }

    private ShoppingRestrictionsViewVector getShoppingRestrictions(

        int accountId, int catalogId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView,
        boolean allRestrictions) throws RemoteException {

        ShoppingRestrictionsViewVector restrictions = new ShoppingRestrictionsViewVector();

        try {

            APIAccess factory = APIAccess.getAPIAccess();

            boolean canAddItem = true;



            ///

            ShoppingServices shoppingServices = factory.getShoppingServicesAPI();

            ShoppingCartItemDataVector shoppingCartItems =

                shoppingServices.getItemControlInfoForAccount(accountId, catalogId, pCategToCostCenterView);



            /// 'Site restrictions' loading

            {
                ShoppingRestrictionsViewVector restrictionsBySiteAndAccount =

                	getShoppingRestrictionsForAcctSiteCat(accountId, catalogId , siteId, allRestrictions);

                Collections.sort(restrictionsBySiteAndAccount, new ShoppingRestrictionsViewComparatorByItem());



                for (int i = 0; i < shoppingCartItems.size(); ++i) {



                    ShoppingCartItemData item = (ShoppingCartItemData)shoppingCartItems.get(i);

                    ShoppingRestrictionsView shopView = new ShoppingRestrictionsView();

                    shopView.setItemId(item.getItemId());



                    int foundIndex = Collections.binarySearch(restrictionsBySiteAndAccount,

                        shopView, new ShoppingRestrictionsViewComparatorByItem());

                    if (foundIndex >= 0) {

                        ShoppingRestrictionsView foundShopView =

                            (ShoppingRestrictionsView)restrictionsBySiteAndAccount.get(foundIndex);




                            shopView.setItemId(item.getItemId());

                            shopView.setItemSkuNum(String.valueOf(item.getProduct().getSkuNum()));

                            shopView.setItemShortDesc(item.getProduct().getShortDesc());

                            shopView.setItemSize(item.getProduct().getSize());

                            shopView.setItemUOM(item.getProduct().getUom());

                            shopView.setItemPack(item.getProduct().getPack());



                            shopView.setShoppingControlId(foundShopView.getShoppingControlId());

                            shopView.setAccountId(foundShopView.getAccountId());

                            shopView.setSiteId(foundShopView.getSiteId());



                            shopView.setSiteMaxOrderQty(foundShopView.getSiteMaxOrderQty());

                            shopView.setAccountMaxOrderQty(foundShopView.getAccountMaxOrderQty());

                            shopView.setRestrictionDays(foundShopView.getRestrictionDays());

                            shopView.setControlStatusCd(foundShopView.getControlStatusCd());

                            shopView.setSiteControlModBy(foundShopView.getSiteControlModBy());

                            shopView.setSiteControlModDate(foundShopView.getSiteControlModDate());

                            shopView.setAcctControlModBy(foundShopView.getAcctControlModBy());

                            shopView.setAcctControlModDate(foundShopView.getAcctControlModDate());

                            shopView.setTag(foundShopView.getTag());

                            restrictions.add(shopView);

                        }
                        
                    }

                }


        }

        catch (Exception ex) {

            ex.printStackTrace();

            throw new RemoteException(ex.getMessage());

        }

        Collections.sort(restrictions, new ShoppingRestrictionsViewComparatorBySku());

        return restrictions;

    }



    public void updateShoppingRestrictions(

        ShoppingRestrictionsViewVector shoppingRestrictions) throws RemoteException {

        log.info("[Site.updateShoppingRestrictions] This method is not implemented still.");

    }



    public SiteViewVector getUserServiceProviderSites( int pUserId,

                                                       int pStoreId,

                                                       String pSearchField, String pSearchType,

                                                       String pRefNum, boolean pRefNumNameBeginsFl,

                                                       String pCity,

                                                       String pState,

                                                       boolean pGetInactiveFl,

                                                       int pResultLimit) throws RemoteException {

       SiteViewVector sitesVV = new SiteViewVector();





       Connection conn = null;

        try {

            conn = getConnection();



        String siteSearchQuery =

                 "SELECT DISTINCT be.bus_entity_id AS site_id, \n\r" +

                         "be.short_desc AS site_name, \n\r" +

                         "be_account.bus_entity_id AS account_id, \n\r" +

                         "be_account.short_desc AS account_name, \n\r" +

                         "addr.address1 AS street, \n\r" +

                         "addr.city AS city, \n\r" +

                         "addr.state_province_cd AS state, \n\r" +

                         "addr.postal_code AS postal_code, \n\r" +

                         "be.bus_entity_status_cd AS status, \n\r" +

                         "addr.county_cd AS county, \n\r" +

                         "props.clw_value AS facility_rank \n\r" +

                 "FROM clw_bus_entity be \n\r" +



                "JOIN clw_bus_entity_assoc bea \n\r" +

                    "ON be.bus_entity_id = bea.bus_entity2_id \n\r" +

                    "AND bea.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE + "' \n\r" +



                "JOIN clw_user_assoc ua \n\r" +

                    "ON  bea.bus_entity1_id = ua.bus_entity_id \n\r" +

                    "AND ua.user_id = " + pUserId + " \n\r" +

                    "AND ua.user_assoc_cd = '" + RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER + "' \n\r" +



                "JOIN clw_bus_entity_assoc bea1 \n\r" +

                    "ON  ua.bus_entity_id = bea1.bus_entity1_id \n\r" +

                    "AND bea1.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE + "' \n\r" +

                    "AND bea1.BUS_ENTITY2_ID = " + pStoreId + " \n\r" +



                "JOIN clw_bus_entity_assoc bea_account \n\r" +

                    "ON be.bus_entity_id = bea_account.bus_entity1_id \n\r" +

                    "AND bea_account.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' \n\r" +



                "JOIN clw_bus_entity be_account \n\r" +

                    "ON be_account.bus_entity_id = bea_account.bus_entity2_id \n\r" +



                "LEFT JOIN clw_address addr \n\r" +

                    "ON be.bus_entity_id = addr.bus_entity_id \n\r" +

                    "AND addr.address_type_cd = '" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "' \n\r" +



                "LEFT JOIN clw_property props \n\r" +

                    "ON be.bus_entity_id = props.bus_entity_id \n\r" +

                    "AND props.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK + "' \n\r";



                if (Utility.isSet(pRefNum)) {

                    siteSearchQuery += "JOIN clw_property props_1 \n\r" +

                                            "ON be.bus_entity_id = props_1.bus_entity_id \n\r" +

                                            "AND props_1.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "' \n\r";

                    siteSearchQuery += "AND Lower(props_1.clw_value) LIKE Lower(?) \n\r";

                    /*if (pRefNumNameBeginsFl) {
                        siteSearchQuery += "AND Lower(props_1.clw_value) LIKE Lower('" + pRefNum + "%') \n\r";
                    } else {
                        siteSearchQuery += "AND Lower(props_1.clw_value) LIKE Lower('%" + pRefNum + "%') \n\r";
                    } */

                }



                siteSearchQuery += "WHERE 1=1 \n\r";



                if (Utility.isSet(pSearchField)) {

                    if ("id".equals(pSearchType)) {

                        try {

                            siteSearchQuery += "AND be.bus_entity_id = " + Integer.parseInt(pSearchField) + "\n\r";

                        } catch (NumberFormatException e) {

                        }

                    /*} else if ("nameBegins".equals(pSearchType)) {
                        siteSearchQuery += "AND Lower(be.short_desc) LIKE Lower('" + pSearchField +"%') \n\r";
                    } else {
                        siteSearchQuery += "AND Lower(be.short_desc) LIKE Lower('%" + pSearchField +"%') \n\r";
                    } */

                    } else {
                        siteSearchQuery += "AND Lower(be.short_desc) LIKE Lower(?) \n\r";
                    }

                }



                if (Utility.isSet(pCity)) {
                    //siteSearchQuery += "AND Lower(addr.city) LIKE Lower('" + pCity + "%') \n\r";
                    siteSearchQuery += "AND Lower(addr.city) LIKE Lower(?) \n\r";
                }


                if (Utility.isSet(pState)) {
                    //siteSearchQuery += "AND Lower(addr.state_province_cd) LIKE Lower('" + pState + "%') \n\r";
                    siteSearchQuery += "AND Lower(addr.state_province_cd) LIKE Lower(?) \n\r";
                }

                if (!pGetInactiveFl) {
                    siteSearchQuery += "AND be.bus_entity_status_cd <> 'INACTIVE' \n\r";
                }

                siteSearchQuery += "ORDER BY Lower(site_name)";





            PreparedStatement stmt = conn.prepareStatement(siteSearchQuery);
            int i=1;
            if (Utility.isSet(pRefNum)) {
                if (pRefNumNameBeginsFl) {
                    stmt.setString(i++, pRefNum + "%");
                } else {
                    stmt.setString(i++, "%" + pRefNum + "%");
                }
            }
            if (Utility.isSet(pSearchField)) {
                if ("nameBegins".equals(pSearchType)) {
                    stmt.setString(i++, pSearchField +"%");
                } else if ("nameContains".equals(pSearchType)) {
                    stmt.setString(i++, "%" + pSearchField +"%");
                }
            }
            if (Utility.isSet(pCity)) {
                stmt.setString(i++, pCity + "%");
            }
            if (Utility.isSet(pState)) {
                stmt.setString(i++, pState + "%");
            }


            if ( pResultLimit > 0 ) {

                  stmt.setMaxRows(pResultLimit);

            }

            ResultSet rs = stmt.executeQuery();

            int rank;

            SiteView sw;

            while (rs.next()) {

                sw = SiteView.createValue();

                sw.setId(rs.getInt("site_id"));

                sw.setName(rs.getString("site_name"));

                sw.setAccountId(rs.getInt("account_id"));

                sw.setAccountName(rs.getString("account_name"));

                sw.setAddress(rs.getString("street"));

                sw.setCity(rs.getString("city"));

                sw.setState(rs.getString("state"));

                sw.setPostalCode(rs.getString("postal_code"));

                sw.setStatus(rs.getString("status"));

                sw.setCounty(rs.getString("county"));

                try {

                    rank = Integer.parseInt(rs.getString("facility_rank"));

                } catch (NumberFormatException e) {

                    rank = 0;

                }

                sw.setTargetFacilityRank(rank);

                sitesVV.add(sw);

            }

        } catch (Exception e) {

            e.printStackTrace();

            logDebug("getUserServiceProviderSites, error " + e.toString());

            throw new RemoteException(e.toString());

        } finally {

            closeConnection(conn);

            return sitesVV;

        }

    }



    /**

     * Return date for cutoff remainder.

     */

    public GregorianCalendar getRemCutoffCalendar(int pAccountId,

        Date nextOrdercutoffDate, Date nextOrdercutoffTime) throws RemoteException {

        GregorianCalendar cutoffRem = null;

        Connection connection = null;

        try {

            logInfo("[Site.getRemCutoffCalendar] begin for account id: " + pAccountId);



            connection = getConnection();

            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.EXTRA);

            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);

            crit.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION);

            PropertyDataVector pv = PropertyDataAccess.select(connection, crit);

            String val = null;

            int valInt = 0;

            if (pv != null && pv.size() > 0) {

                val = ((PropertyData) pv.get(0)).getValue();

            }

            logInfo("[Site.getRemCutoffCalendar] val: " + val);



            if (val != null) {

                try {

                    valInt = Integer.parseInt(val);



                    Date currentDate = new Date();



                    Date cloneD = (Date)nextOrdercutoffDate.clone();

                    GregorianCalendar cutoffD = new GregorianCalendar();

                    cutoffD.setTime(cloneD);



                    GregorianCalendar cutoffT = new GregorianCalendar();

                    cutoffT.setTime(nextOrdercutoffTime);



                    cutoffD.set(Calendar.HOUR_OF_DAY, cutoffT.get(Calendar.HOUR_OF_DAY));

                    cutoffD.set(Calendar.MINUTE, cutoffT.get(Calendar.MINUTE));

                    cutoffD.set(Calendar.SECOND, cutoffT.get(Calendar.SECOND));



                    cutoffRem = (GregorianCalendar)cutoffD.clone();



                    cutoffRem.add(Calendar.HOUR_OF_DAY, -valInt);

                    //Subtract 10 mins to have a buffer between schedules

                    cutoffRem.add(Calendar.MINUTE, -10);



                    if(currentDate.after(cutoffD.getTime())){

                    	return null;

                    }

                }

                catch (NumberFormatException e) {

                    logInfo("[Site.getRemCutoffCalendar] " + e.getMessage());

                }

            }

            logInfo("[Site.getRemCutoffCalendar] cutoffRem:" + cutoffRem);

            logInfo("[Site.getRemCutoffCalendar] end.");

        } catch (Exception ex) {

            throw new RemoteException("[Site.getRemCutoffCalendar] Error: " + ex.getMessage());

        } finally {

            closeConnection(connection);

        }

        return cutoffRem;

    }



    /**

     * Returns true if corporate emails should be sent

     */

    public boolean getSendCorporateEmail(int pSiteId, int pAcctId,

    		Date runDate, Date startDate) throws RemoteException {

    	Connection conn = null;

    	boolean sendCorporateEmail = false;

        try {

        	conn = getConnection();

            SiteData siteData = getSite(pSiteId, 0);



            ScheduledOrderManager invManager = new ScheduledOrderManager(siteData);

            GregorianCalendar runDateCal =

                invManager.getRunDateCalforScheduleProcess(runDate);

            GregorianCalendar cutoffCal =

                invManager.calculateCutoffDate(conn, siteData, runDateCal.getTime());

            GregorianCalendar cutoffRemCal =

                invManager.getRemCutoffCalendar(conn, siteData.getNextOrdercutoffDate(),

                siteData.getNextOrdercutoffTime(), pAcctId);



            GregorianCalendar startDateCal = new GregorianCalendar();

            startDateCal.setTime(startDate);



            if (cutoffRemCal != null && runDateCal.after(cutoffRemCal)) {

                if (!(runDateCal.getTime().before(cutoffCal.getTime()) && startDateCal.after(cutoffCal.getTime()))) {

                	sendCorporateEmail = true;

                }

            }



        } catch (Exception ex) {

            throw new RemoteException("[Site.sendInventoryMissingEmail] Error: " + ex.getMessage());

        } finally {

            closeConnection(conn);

        }

        return sendCorporateEmail;

    }



    /**

     * Sends emails to notify about missing of inventory items.

     */

    public void sendInventoryMissingEmail(int siteId, Date runDate, Date startDate)

        throws RemoteException {

        Connection connection = null;

        try {

        	logInfo("");

        	logInfo("");

        	logInfo("");

        	logInfo("************ Starting SiteBean.sendInventoryMissingEmail for siteId: "+siteId);

            String processLog = "";

            connection = getConnection();

            SiteData siteData = getSite(siteId, 0);

            if (!siteData.isActive()) {

                logInfo("Site is not in an active status.pSiteId=" + siteData.getSiteId());

                return;

            } else if (!siteData.hasInventoryShoppingOn()) {

            	logInfo("Inventory shopping is off for pSiteId=" + siteData.getSiteId());

            	return;

            }



            Account accBean = APIAccess.getAPIAccess().getAccountAPI();

            boolean isPhysicalInv = false;

            AccountData accData = accBean.getAccountForSite(siteId);

            isPhysicalInv = isUsedPhysicalInventoryCart(connection, siteData.getAccountId());

            ScheduledOrderManager invManager = new ScheduledOrderManager(siteData);

            GregorianCalendar runDateCal =

            	invManager.getRunDateCalforScheduleProcess(runDate);

            GregorianCalendar cutoffCal =

            	invManager.calculateCutoffDate(connection, siteData, runDateCal.getTime());

            GregorianCalendar cutoffRemCal =

            	invManager.getRemCutoffCalendar(connection, siteData.getNextOrdercutoffDate(),

            			siteData.getNextOrdercutoffTime(), siteData.getAccountId());



            GregorianCalendar startDateCal = new GregorianCalendar();

            startDateCal.setTime(startDate);



            SimpleDateFormat sdf = new SimpleDateFormat(ScheduledOrderManager.DATE_FORMAT);

            logInfo("[Site.sendInventoryMissingEmail]    cutoff: " + sdf.format(cutoffCal.getTime()));

            logInfo("[Site.sendInventoryMissingEmail] startDate: " + sdf.format(startDate));

            logInfo("[Site.sendInventoryMissingEmail]   runDate: " + sdf.format(runDateCal.getTime()));

            if (cutoffRemCal != null) {

            	logInfo("[Site.sendInventoryMissingEmail] cutoffRem: " + sdf.format(cutoffRemCal.getTime()));

            }else{

            	logInfo("[Site.sendInventoryMissingEmail] cutoffRem is null ");

            }



            if (cutoffRemCal != null && runDateCal.after(cutoffRemCal)) {

            	if (!(runDateCal.getTime().before(cutoffCal.getTime()) && startDateCal.after(cutoffCal.getTime()))) {

            		String emailTxt = invManager.checkReadyProcess(connection);

            		logInfo("[Site.sendInventoryMissingEmail] emailTxt: " + emailTxt);



            		//if physical inventory is on - send email without checking null on hand

            		if(isPhysicalInv){

            			String sendRes = sendInventoryMissingEmail(siteData, cutoffCal.getTime(), connection);

            			logInfo("[Site.sendInventoryMissingEmail-PhysicalInventory] sendRes: " + sendRes);

            		}else if (emailTxt.trim().length() > 0) {

            			String sendRes = sendInventoryMissingEmail(siteData, cutoffCal.getTime(), connection);

            			logInfo("[Site.sendInventoryMissingEmail] sendRes: " + sendRes);

            		}

            	}else{

            		logInfo("[Site.sendInventoryMissingEmail] run date "+runDateCal+" after cutoff "+cutoffCal+

            				"or start date"+startDateCal+" before cutoff");

            	}

            }else{

            	logInfo("[Site.sendInventoryMissingEmail] run date "+runDateCal+" is not after cut off reminder "+cutoffRemCal);

            }



        } catch (Exception ex) {

        	ex.printStackTrace();

        	logInfo("************ END SiteBean.sendInventoryMissingEmail for siteId: "+siteId + " ENDED WITH ERROR");

            throw new RemoteException("[Site.sendInventoryMissingEmail] Error: " + ex.getMessage());

        } finally {

            closeConnection(connection);

        }

        logInfo("************ END SiteBean.sendInventoryMissingEmail for siteId: "+siteId);

    }

    public IdVector getSiteIdsByCriteria(BusEntitySearchCriteria pCrit) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            BusEntityDataVector sites = BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

            return Utility.toIdVector(sites);

        } catch (Exception e) {

            throw processException(e);

        } finally {

            closeConnection(conn);

        }

    }
    
    /**
     * Gets all the sites for an account.
     * @param qr				-	Conditions that are passed to query.
     * @return SiteDataVector	- 	List of all sites.
     * @throws RemoteException	-	If an error occurs.
     */
    public SiteDataVector getSitesForAccount(QueryRequest qr)
	throws RemoteException { 
    	
        SiteDataVector siteVec = new SiteDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            SiteViewVector siteViewVec = mBDAO.getSiteCollection(conn, qr);
            for (int i = 0; i < siteViewVec.size(); i++) {
                SiteView siteView = (SiteView) siteViewVec.get(i);
                SiteData siteD = mBDAO.getSiteData(conn, siteView.getId());
                siteVec.add(siteD);
            }
        } catch (Exception e) {
            throw new RemoteException("getSitesForAccount: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return siteVec;
    }
    
    /**
     * Gets all the sites of an account for specified fiscal year. 
     * @param qr				-	Conditions that are passed to query.
     * @param pAccountId		-	Account Id.
     * @param pBudgetYear		-	Budget Year.
     * @return SiteDataVector	- 	List of all sites.
     * @throws RemoteException	-	If an error occurs.
     */
    public SiteDataVector getSitesForAccountWithBudgets(QueryRequest qr, int pAccountId, int pBudgetYear)
    		throws RemoteException { 
		
    	SiteDataVector siteVec = getSitesForAccount(qr,pBudgetYear);
        Connection conn = null;
        try {
            conn = getConnection();
            FiscalCalendarInfo fci = new FiscalCalendarInfo(mBDAO.getFiscalCalenderVForYear(conn, pAccountId,pBudgetYear));
            for (int i=0; i<siteVec.size(); i++) {
                SiteData siteD = (SiteData)siteVec.get(i);
                // get the budget periods
                 mPrevBudgetDates = fci.getBudgetPeriodsAsHashMap();
                 mPrevAcctid = pAccountId;
                siteD.setBudgetPeriods(new Hashtable(mPrevBudgetDates));
            }
        } catch (Exception e) {
            throw new RemoteException("getSitesForAccountWithBudgets: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return siteVec;
    }
    
    /**
     * Gets all the sites for an account.
     * @param qr				-	Conditions that are passed to query.
     * @param pBudgetYear		-	Budget Year.
     * @return SiteDataVector	- 	List of all sites.
     * @throws RemoteException	-	If an error occurs.
     */
    private SiteDataVector getSitesForAccount(QueryRequest qr, int budgetYear)
	throws RemoteException { 
    	
        SiteDataVector siteVec = new SiteDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            SiteViewVector siteViewVec = mBDAO.getSiteCollection(conn, qr);
            for (int i = 0; i < siteViewVec.size(); i++) {
                SiteView siteView = (SiteView) siteViewVec.get(i);
                SiteData siteD = mBDAO.getSiteData(conn, siteView.getId(),budgetYear);
                siteVec.add(siteD);
            }
        } catch (Exception e) {
            throw new RemoteException("getSitesForAccount: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return siteVec;
    }
    
    
    // Location Budget
    /**
     * getCurrentPeriodAmount returns the Budget's current period amount,
     * to find whether an account has unlimited budget or not
     * @param accountId      - Account Id
     * @param siteId         -  Site Id   
     * @param costCenterId   -  Cost Center Id.
     * @param currentPeriod     - current period 
     * @return BigDecimal value  -  current period amount.
     * @throws RemoteException  -	If an error occurs.
     */
    public BigDecimal getCurrentPeriodAmount(int accountId,int siteId,int costCenterId,int currentPeriod)
    throws RemoteException {
        Connection conn = null;
        BigDecimal currentPeriodAmount = new BigDecimal(-1);
        try {
            conn = getConnection();
            BudgetUtil budgetUtil = new BudgetUtil(conn);
            currentPeriodAmount = budgetUtil.getBudgetPeriodAmount(accountId, siteId, costCenterId, currentPeriod);
            
            
        } catch (Exception e) {
            throw new RemoteException("getCurrentPeriodAmount: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	throw new RemoteException("getCurrentPeriodAmount: " + e.getMessage());
            }
        }
  
    	return currentPeriodAmount;
    }
    
    public BusEntityDataVector getSiteBusEntityByCriteria(DBCriteria crit) throws
    RemoteException{
    	Connection conn = null;
    	try {
    		conn = getConnection();
    		return BusEntityDataAccess.select(conn, crit);
    	}catch (Exception e){
    		throw processException(e);
    	}finally{
    		closeConnection(conn);
    	}    
    }

    /**
     * Retrieves information for 1-n locations.
     * @param  pLocationIds  - a <code>IdVector</code> of location ids.
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocations(IdVector pLocationIds) throws RemoteException, DataNotFoundException {
    	SiteDataVector returnValue = null;
    	if (!Utility.isSet(pLocationIds)) {
    		throw new IllegalArgumentException("No location ids specified.");
    	}
    	else {
        	IdVector accountIds = new IdVector();
        	for (int i=0; i<pLocationIds.size(); i++) {
        		accountIds.add(0);
        	}
            returnValue = getLocations(pLocationIds, accountIds);
    	}
    	return returnValue;
    }

    /**
     * Retrieves information for 1-n locations.
     * @param  pLocationIds  - a <code>IdVector</code> of location ids.
     * @param  pAccountIds  - a <code>IdVector</code> of account ids.
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocations(IdVector pLocationIds, IdVector pAccountIds) 
    			throws RemoteException, DataNotFoundException {
    	SiteDataVector returnValue = null;
    	if (!Utility.isSet(pLocationIds)) {
    		throw new IllegalArgumentException("No location ids specified.");
    	}
    	else if (!Utility.isSet(pAccountIds)) {
    		throw new IllegalArgumentException("No account ids specified.");
    	}
    	else if (pLocationIds.size() != pAccountIds.size()) {
    		throw new IllegalArgumentException("Location id list size does not match account id list size.");
    	}
    	else {
            returnValue = getLocations(pLocationIds, pAccountIds, false);
    	}
    	return returnValue;
    }

    /**
     * Retrieves information for 1-n locations.
     * @param  pLocationIds  - a <code>IdVector</code> of location ids.
     * @param  pAccountIds  - a <code>IdVector</code> of account ids.
     * @param  pShowInactive - a boolean indicating if inactive locations 
     * 			should be returned.
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocations(IdVector pLocationIds, IdVector pAccountIds, 
    		boolean pShowInactive) throws RemoteException, DataNotFoundException {
    	SiteDataVector returnValue = null;
    	if (!Utility.isSet(pLocationIds)) {
    		throw new IllegalArgumentException("No location ids specified.");
    	}
    	else if (!Utility.isSet(pAccountIds)) {
    		throw new IllegalArgumentException("No account ids specified.");
    	}
    	else if (pLocationIds.size() != pAccountIds.size()) {
    		throw new IllegalArgumentException("Location id list size does not match account id list size.");
    	}
    	else {
    		List<Integer> budgetYears = new ArrayList<Integer>();
        	for (int i=0; i<pLocationIds.size(); i++) {
        		budgetYears.add(NO_BUDGET_YEAR_REQUIRED);
        	}
            returnValue = getLocationsByBudgetYear(pLocationIds, pAccountIds, pShowInactive, budgetYears);
    	}
    	return returnValue;
    }

    /**
     * Retrieves information for 1-n locations.
     * @param  pSiteIds  - a <code>IdVector</code> of location ids.
     * @param  pAccountIds  - a <code>IdVector</code> of account ids.
     * @param  pShowInactive - a boolean indicating if inactive locations 
     * 			should be returned.
     * @param  pCategToCostCenterViews - a <code>List</code> of <code>AccCategoryToCostCenterView</code>
     * @param  budgetYears  - a <code>List</code> of <code>Integers</code> used to get the the location 
     * 			budgets details by specified year. 
     * @return  SiteDataVector - a <code>SiteDataVector</code> containing the location information.
     * @throws  RemoteException, DataNotFoundException
     */
    public SiteDataVector getLocationsByBudgetYear(IdVector pLocationIds, IdVector pAccountIds, 
    		boolean pShowInactive, List<Integer> pBudgetYears) throws RemoteException, DataNotFoundException {
    	if (!Utility.isSet(pLocationIds)) {
    		throw new IllegalArgumentException("No location ids specified.");
    	}
    	else if (!Utility.isSet(pAccountIds)) {
    		throw new IllegalArgumentException("No account ids specified.");
    	}
    	else if (!Utility.isSet(pBudgetYears)) {
    		throw new IllegalArgumentException("No budget years specified.");
    	}
    	else if (pLocationIds.size() != pAccountIds.size()) {
    		throw new IllegalArgumentException("Location id list size does not match account id list size.");
    	}
    	else if (pLocationIds.size() != pBudgetYears.size()) {
    		throw new IllegalArgumentException("Location id list size does not match budget years list size.");
    	}
    	SiteDataVector returnValue = new SiteDataVector();
		Connection conn = null;
		try {
			conn = getConnection();
			//get the sites
			DBCriteria crit = new DBCriteria();
			if (!pShowInactive){
				crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
			}
			crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, pLocationIds);
			BusEntityDataVector beDV = BusEntityDataAccess.select(conn, crit);
		   
			//make sure that each business entity retrieved is a location
			if (Utility.isSet(beDV)) {
				for (int i=0; i<beDV.size(); i++) {
					BusEntityData busEntity = (BusEntityData)beDV.get(i);
					if (!RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(busEntity.getBusEntityTypeCd())) {
						throw new DataNotFoundException("Specified bus entity id (" + 
				   				busEntity.getBusEntityId() + ") does not correspond to a location.");
					}
				}
			}
		   
			//verify that locations belong to specified accounts (if any)
			IdVector locationIds = new IdVector();
			IdVector accountIds = new IdVector();
			Map<Integer,Integer> locationIdToAccountId = new HashMap<Integer,Integer>();
			for (int i=0; i<pAccountIds.size(); i++) {
				if ((Integer)pAccountIds.get(i) > 0) {
					locationIds.add(pLocationIds.get(i));
					accountIds.add(pAccountIds.get(i));
					locationIdToAccountId.put((Integer)pLocationIds.get(i), (Integer)pAccountIds.get(i));
				}
			}
			if (Utility.isSet(accountIds)) {
				crit = new DBCriteria();
				crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, locationIds);
				crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountIds);
				crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
				BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(conn, crit);
				if (assocVec.size() != accountIds.size()) {
					throw new DataNotFoundException("One or more locations do not belong to specified accounts.");
				}
				else {
					for (int i=0; i<assocVec.size(); i++) {
						BusEntityAssocData association = (BusEntityAssocData)assocVec.get(i);
						int locationId = association.getBusEntity1Id();
						int accountId = locationIdToAccountId.get(locationId);
						if (accountId != association.getBusEntity2Id()) {
							throw new DataNotFoundException("Location " + locationId + " does not belong to account " + accountId + ".");
						}
					}
				}
			}
		
			if (Utility.isSet(beDV)) {
				//create/reset some maps we will need
				locationIdToAccountId = new HashMap<Integer,Integer>();
				Map<Integer, BusEntityAssocData> locationIdToAccountAssoc = new HashMap<Integer, BusEntityAssocData>();
				Map<Integer,BusEntityData> accountIdToAccount = new HashMap<Integer,BusEntityData>();
				
				//get the account association for each location
				DBCriteria assocCrit = new DBCriteria();
				assocCrit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,  pLocationIds);
				assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,  RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
				BusEntityAssocDataVector associations = BusEntityAssocDataAccess.select(conn, assocCrit);

				//each location should have a single account association - if not, that's a problem
				if (associations.size() != pLocationIds.size()) {
					throw new DataNotFoundException("Unable to find exact match of locations to accounts.");
				}

				//get the accounts associated with the locations
				accountIds = new IdVector();
				Iterator<BusEntityAssocData> associationIterator = associations.iterator();
				while (associationIterator.hasNext()) {
					BusEntityAssocData association = associationIterator.next();
					accountIds.add(association.getBusEntity2Id());
					locationIdToAccountId.put(association.getBusEntity1Id(), association.getBusEntity2Id());
					locationIdToAccountAssoc.put(association.getBusEntity1Id(), association);
				}
				BusEntityDataVector accounts = BusEntityDataAccess.select(conn, accountIds);
				Iterator<BusEntityData> accountIterator = accounts.iterator();
				while (accountIterator.hasNext()) {
	    		   	BusEntityData account = accountIterator.next();
	    		   	accountIdToAccount.put(account.getBusEntityId(), account);
				}

				//now create the SiteData objects we will return
				Iterator<BusEntityData> siteIterator = beDV.iterator();
				while (siteIterator.hasNext()) {
					BusEntityData siteData = siteIterator.next();
					Integer siteId = siteData.getBusEntityId();
		        	Integer accountId = locationIdToAccountId.get(siteId);
		        	BusEntityData accountData = accountIdToAccount.get(accountId);
		        	BusEntityAssocData associationData = locationIdToAccountAssoc.get(siteId);
			        Integer budgetYear = BudgetDAO.getCurrentBudgetYear(conn, accountId);
					BudgetViewVector budgets = null;
			        if (budgetYear != null) {
			        	budgets = BudgetDAO.getBudgetsForSite(conn, accountId, siteData.getBusEntityId(), 0, budgetYear);
			        }
			        SiteData site = new SiteData(siteData, accountData, associationData, 
			        		null, null, budgets, null, 0, null, null, null, null, null, 
			        		null, null, null);
			        site = updateBudgetSpendingInfo(site);
			        returnValue.add(site);
		       }			   
		   }
		}
	   catch (Exception e) {
		   throw new RemoteException(e.getLocalizedMessage());
	   }
	   finally {
			closeConnection(conn);
		}
		return returnValue;
	}

    /**
     * Gets the shopping controls for an account/site. 
     * @param accountId - the id of the account for which the controls are being retrieved.
     * @param siteId - the id of the site for which the controls are being retrieved.
     * @param budgetYear
     * @return
     * @throws RemoteException
     */
    public ShoppingControlDataVector getShoppingControls(int accountId, int siteId) throws RemoteException
    {
    	ShoppingControlDataVector returnValue = null;
        Connection conn = null;
        try {
            conn = getConnection();
            returnValue = lookupSiteShoppingControls(conn, siteId, accountId);
        } 
        catch (Exception e) {
        	e.printStackTrace();
            logError("SiteBean error (" + e.getMessage() + ") for site =" + siteId);
            throw new RemoteException("getShoppingControls: " + e.getMessage());
        } 
        finally {
            closeConnection(conn);
        }
        return returnValue;
    }
}
