package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;

public class CorporateOrderBean
    extends BusEntityServicesAPI {
    private static final Logger log = Logger.getLogger(CorporateOrderBean.class);

    public void ejbCreate()
             throws CreateException, RemoteException {
    }
	
	public String placeCorporateOrder(CorpOrderCacheView pCorpOrderCache) 
	throws RemoteException {
    	Connection conn = null;
    	try {
    		conn = getConnection();
			CorporateOrderManager corpOrderManager = new CorporateOrderManager();
			corpOrderManager.init(conn, pCorpOrderCache);
			String retCode = corpOrderManager.placeCorporateOrder(); 
			return retCode;
		} catch (Exception e) {
			e.printStackTrace();
            throw processException(e);
        } finally {
            closeConnection(conn);
        }		
	}
	
	public List<DeliveryScheduleView> getCorpSchedules(Date beginDate, Date endDate, IdVector scheduleIdV) 
	throws RemoteException, ParseException
	{
    	Connection conn = null;
    	try {
			conn = getConnection();
			String sql =  
			"select store_id, schedule_id, short_desc, last_processed_dt, max(cutoff) cutoff from (\r\n"+
			"select \r\n"+
			"  s.schedule_id, s.short_desc, s.bus_entity_id as store_id,  \r\n"+
			"  to_date(trim(sdate.value)||' '||trim(stime.value), 'MM/DD/YYYY HH24:MI') as cutoff, last_processed_dt \r\n"+
			"from clw_schedule s, clw_schedule_detail sdate, clw_schedule_detail stime \r\n"+
			"where s.schedule_id = sdate.schedule_id  \r\n"+
			"  and sdate.schedule_detail_cd = 'ALSO_DATE' \r\n"+
			"  and s.schedule_id = stime.schedule_id  \r\n"+
			"  and stime.schedule_detail_cd = 'CUTOFF_TIME' \r\n"+
			"  and s.schedule_type_cd = 'CORPORATE' \r\n"+
			"  and s.schedule_rule_cd = 'DATE_LIST' \r\n"+
			"  and s.eff_date < sysdate \r\n"+
			"  and nvl(exp_date,sysdate) >= sysdate \r\n"+
			"  and s.schedule_status_cd = 'ACTIVE' \r\n"+
			(scheduleIdV.size()>0?("  and s.schedule_id in ("+IdVector.toCommaString(scheduleIdV)+") \r\n"):(""))+
			"  and s.schedule_type_cd = 'CORPORATE' \r\n"+
			") a \r\n"+
			"where 1=1 \r\n"+
			(beginDate!=null ? "and cutoff >= ? \r\n" : "") +
			(endDate!=null ? "and cutoff <= ? \r\n" : "") +
			"and (last_processed_dt is null or (cutoff > last_processed_dt)) \r\n"+
			"group by store_id, schedule_id, short_desc, last_processed_dt \r\n"+
			"order by store_id, schedule_id, last_processed_dt";
			
			log.info("Pick corporate schedules: "+sql);

			PreparedStatement stmt = conn.prepareStatement(sql);
			int ix = 1;
			if (beginDate!=null){
				stmt.setTimestamp(ix++, DBAccess.toSQLTimestamp(beginDate));
			}
			if (endDate!=null){
				stmt.setTimestamp(ix++, DBAccess.toSQLTimestamp(endDate));
			}
			
			ResultSet rs=stmt.executeQuery();
			ArrayList<DeliveryScheduleView> schVwV = new ArrayList<DeliveryScheduleView>();
            while (rs.next()) {
				Date cutoffD = rs.getTimestamp("cutoff");			

				Date lastProcessedDt = rs.getTimestamp("last_processed_dt");

				DeliveryScheduleView schVw = new DeliveryScheduleView();
				int scheduleId = rs.getInt("schedule_id");
				int storeId = rs.getInt("store_id");
				String schName = rs.getString("short_desc");
				schVw.setNextDelivery(cutoffD);
				schVw.setBusEntityId(storeId);
				schVw.setScheduleId(scheduleId);
				schVw.setScheduleName(schName);
				schVw.setLastProcessedDt(lastProcessedDt);
				schVwV.add(schVw);				
			}
			rs.close();
			stmt.close();
			return schVwV;
		} catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }		
	}
	
	public HashMap<Integer,HashMap> getAccountCatalogSiteMap (int pScheduleId) 
	throws RemoteException	{
    	Connection conn = null;
    	try {
    		conn = getConnection();
			HashMap<Integer,HashMap> accountHM = new HashMap<Integer,HashMap>();
			DBCriteria dbc = new DBCriteria();
			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID,pScheduleId);
			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID);
			ScheduleDetailDataVector sdDV = 
				 ScheduleDetailDataAccess.select(conn,dbc);
			if(sdDV.size()==0) {
				return accountHM;
			}
            //Prepare site filter
			int count = 0;
			ArrayList<String> siteAL = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			for(Iterator iter=sdDV.iterator(); iter.hasNext();) {
				ScheduleDetailData sD = (ScheduleDetailData) iter.next();
				String siteIdS = sD.getValue();
				try{ 
					Integer.parseInt(siteIdS);//Validate the format
					count++;
					if(count==999) {
						siteAL.add(new String(sb));
						sb = new StringBuffer();
						count = 0;
					}
					if(count>1) sb.append(", ");
					sb.append(siteIdS);
				} catch (NumberFormatException exc) {
					log.info("Wrong Site Id format: "+ siteIdS);
					exc.printStackTrace();
					continue;
				}				
			}
			siteAL.add(new String(sb));
			
			StringBuffer siteFilterSB = new StringBuffer();
			siteFilterSB.append("(");
			for(int ii=0; ii<siteAL.size(); ii++) {
			   if(ii>0) siteFilterSB.append("\n\r or \r\n");
			   siteFilterSB.append("be.bus_entity_id in (");
			   siteFilterSB.append((String) siteAL.get(ii));
			   siteFilterSB.append(")");
			}
			siteFilterSB.append(")");
			String siteFilter = new String(siteFilterSB);
			
						
			String sql = 
				"select c.catalog_id, be.bus_entity_id as site_id, acct.bus_entity_id as account_id \r\n"+
				" from clw_catalog c, clw_catalog_assoc ca, clw_bus_entity be,  \r\n"+
				"	clw_bus_entity_assoc bea, clw_bus_entity acct  \r\n"+
				" where 1=1 \r\n"+
				" and ca.CATALOG_ASSOC_CD = 'CATALOG_SITE' \r\n"+
				" and c.catalog_id = ca.catalog_id \r\n"+
				" and c.catalog_type_cd = 'SHOPPING' \r\n"+
				" and c.catalog_status_cd = 'ACTIVE' \r\n"+
				" and "+siteFilter+" \r\n"+
				" and ca.bus_entity_id = be.bus_entity_id  \r\n"+
				" and be.bus_entity_type_cd = 'SITE' \r\n"+
				" and be.bus_entity_status_cd = 'ACTIVE'  \r\n"+
				" and bea.bus_entity1_id = be.bus_entity_id \r\n"+
				" and bea.bus_entity2_id = acct.bus_entity_id \r\n"+
				" and acct.bus_entity_type_cd = 'ACCOUNT' \r\n"+
				" and acct.bus_entity_status_cd = 'ACTIVE' \r\n"+
 				" order by account_id, catalog_id";

			log.info("Pick sites, account and catalogs  : "+sql);

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs=stmt.executeQuery();
			
			int prevAccountId = -777; //Any not existing key (id)
			int prevCatalogId = -777;
			HashMap<Integer,ArrayList> catalogHM = null;
			ArrayList<Integer> wrkSiteAL = null;
			while (rs.next()) {
				int accountId = rs.getInt("account_id");
				int catalogId = rs.getInt("catalog_id");
				int siteId = rs.getInt("site_id");
				if(prevAccountId!=accountId) {
				    prevAccountId = accountId;
					prevCatalogId = catalogId;
					catalogHM = new HashMap<Integer,ArrayList>();
					accountHM.put(accountId,catalogHM);
					wrkSiteAL =  new ArrayList<Integer>();					
					catalogHM.put(catalogId,wrkSiteAL);
				} else if (prevCatalogId!=catalogId) {
					prevCatalogId = catalogId;
					wrkSiteAL =  new ArrayList<Integer>();
					catalogHM.put(catalogId,wrkSiteAL);
				}
				wrkSiteAL.add(siteId);			
			}
			rs.close();
			stmt.close();
			
			return accountHM;
			
		} catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }		
	}
	
	public HashMap<Integer,ProductData> getContractProducts(int pCatalogId,  
	                                    AccCategoryToCostCenterView pAccCategToCostCenterVw) 
	throws RemoteException	{
    	Connection conn = null;
    	try {
    		conn = getConnection();
            String sql = 
			" select cs.item_id, con.contract_id \r\n"+
			" from clw_catalog_structure cs, clw_contract con, clw_contract_item coni \r\n"+
			" where cs.catalog_id = " + pCatalogId + " \r\n"+
			" and cs.catalog_structure_cd = 'CATALOG_PRODUCT' \r\n"+
			" and cs.catalog_id = con.catalog_id \r\n"+
			" and con.contract_status_cd = 'ACTIVE' \r\n"+
			" and con.contract_id = coni.contract_id \r\n"+
			" and coni.item_Id = cs.item_id ";

			log.info("Pick catalog items  : "+sql);

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs=stmt.executeQuery();
			IdVector itemIdV = new IdVector();

			while (rs.next()) {
				int itemId = rs.getInt("item_id");
				itemIdV.add(new Integer(itemId));
			}
			rs.close();
			stmt.close();
			ProductDAO pDAO = new ProductDAO(conn, itemIdV);
			pDAO.updateCatalogInfo(conn, pCatalogId,0,pAccCategToCostCenterVw, true);
		    ProductDataVector pdV = pDAO.getResultVector();
			HashMap<Integer,ProductData> productHM = new HashMap<Integer,ProductData>();
			for(Iterator iter = pdV.iterator(); iter.hasNext();) {
				ProductData pD = (ProductData) iter.next();
				int itemId = pD.getProductId();
				productHM.put(itemId,pD);
			}
			return productHM;

		} catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }		
	}
	
	public HashMap<Integer,Integer> getInventoryOGIds(List pSiteIdV) 
	throws RemoteException	{
    	Connection conn = null;
    	try {
    		conn = getConnection();
			DBCriteria dbc = new DBCriteria();
			dbc.addOneOf(OrderGuideDataAccess.BUS_ENTITY_ID,pSiteIdV);
			dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
				RefCodeNames.ORDER_GUIDE_TYPE_CD.INVENTORY_CART);

			OrderGuideDataVector ogDV = OrderGuideDataAccess.select(conn,dbc);
			HashMap<Integer,Integer> siteOrderGuideHM = new HashMap<Integer,Integer>();
			
			for(Iterator iter = ogDV.iterator(); iter.hasNext();) {
				OrderGuideData ogD = (OrderGuideData) iter.next();
				int siteId = ogD.getBusEntityId();
				int orderGuideId = ogD.getOrderGuideId();
				siteOrderGuideHM.put(siteId,orderGuideId);
			}
			return siteOrderGuideHM;

		} catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }		
	}
	/*
    private HashMap calculateSiteBudgetDates(int pAccountId, Date pCutoffDate) throws Exception {    	
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

    */
	
/*
public getInventoryItems(int accountId) 
select acct.bus_entity_id as account_id, c.catalog_id, contr.contract_id, c.catalog_id, site.site_id
from clw_catalog c, clw_catalog_assoc ca, clw_contract contr, clw_bus_entity_assoc sacct, clw_bus_entity acct,
(
                     SELECT distinct site.BUS_ENTITY_ID as site_id
				 FROM CLW_BUS_ENTITY site, CLW_PROPERTY inv_prop 
				 WHERE site.BUS_ENTITY_ID = inv_prop.BUS_ENTITY_ID 
				 AND site.BUS_ENTITY_TYPE_CD = 'SITE' 
				 AND site.BUS_ENTITY_STATUS_CD = 'ACTIVE' 
				 AND inv_prop.PROPERTY_TYPE_CD = 'ALLOW_CORPORATE_SCHED_ORDER' 
				 AND inv_prop.CLW_VALUE = 'true' 
				 AND exists (select 1 from clw_schedule_detail sd 
					where sd.schedule_id = 6383 
					and sd.schedule_detail_cd = 'SITE_ID'  
				   and trim(sd.VALUE) = TO_CHAR(site.BUS_ENTITY_ID)) 
) site
where site.site_id = ca.bus_entity_id
and c.catalog_id = ca.catalog_id				 
and c.catalog_type_cd = 'SHOPPING'
and c.catalog_id = contr.catalog_id
and c.catalog_status_cd = 'ACTIVE'
and contr.contract_status_cd = 'ACTIVE'
and sacct.bus_entity1_id = site.site_id
and sacct.bus_entity_assoc_cd = 'SITE OF ACCOUNT'
and sacct.bus_entity2_id = acct.bus_entity_id
and acct.bus_entity_type_cd = 'ACCOUNT'
and acct.bus_entity_status_cd = 'ACTIVE'
*/
	public List<Integer> getScheduleSites(int scheduleId) 
	throws RemoteException 
	{
        Connection  conn = null;
        ArrayList<Integer> siteIds = new ArrayList<Integer>();
    	try {
    		conn = getConnection();
			String sql = "SELECT distinct site.BUS_ENTITY_ID \r\n"+
				" FROM CLW_BUS_ENTITY site, CLW_PROPERTY inv_prop \r\n"+
				" WHERE site.BUS_ENTITY_ID = inv_prop.BUS_ENTITY_ID \r\n"+
				" AND site.BUS_ENTITY_TYPE_CD = 'SITE' \r\n"+
				" AND site.BUS_ENTITY_STATUS_CD = 'ACTIVE' \r\n"+
				" AND inv_prop.PROPERTY_TYPE_CD = 'ALLOW_CORPORATE_SCHED_ORDER' \r\n"+
				" AND inv_prop.CLW_VALUE = 'true' \r\n"+
				" AND exists(select 1 from clw_schedule_detail sd \r\n"+
				"	where sd.schedule_id = "+scheduleId+" \r\n"+
				"	and sd.schedule_detail_cd = 'SITE_ID' \r\n"+ 
				"   and trim(sd.VALUE) = TO_CHAR(site.BUS_ENTITY_ID)) \r\n"+
				" order by bus_entity_id";

			log.info("Get sites for a corporate schedule: "+sql);

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
            throw processException(e);
        } finally {
            closeConnection(conn);
        }		
	}
	
	public void updateLastProccessedDt( int scheduleId, Date schStartDate) 
	throws RemoteException	{
    	Connection conn = null;
    	try {
    		conn = getConnection();
			ScheduleData sD = ScheduleDataAccess.select(conn, scheduleId);
			sD.setLastProcessedDt(schStartDate);
			ScheduleDataAccess.update(conn, sD);

		} catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }		
	}
	
/*
		           FiscalCalenderView fiscalCalendar = new BusEntityDAO().getCurrentFiscalCalenderV(pCon, accountId);


    public BigDecimal getAutoOrderFactor(Connection conn, int pAccountId) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, pAccountId);
        dbc.addEqualToIgnoreCase(InventoryItemsDataAccess.ENABLE_AUTO_ORDER, "Y");
        InventoryItemsDataVector invItemsDV = InventoryItemsDataAccess.select(conn, dbc);
        BigDecimal autoOrderFactor = new BigDecimal(0.5);
        if (invItemsDV.size()> 0){
        dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);
        dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR);
        PropertyDataVector propDV = PropertyDataAccess.select(conn, dbc);
        if (propDV.size() > 0) {
            PropertyData propD = (PropertyData) propDV.get(0);
            String factorValStr = propD.getValue();
            if (Utility.isSet(factorValStr)) {
                try {

                    double factorValDbl = Double.parseDouble(factorValStr);

                    if (factorValDbl <= 0) factorValDbl = 0;
                    if (factorValDbl > 1) factorValDbl = 1;

                    autoOrderFactor =
                            new BigDecimal(factorValDbl).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                } catch (Exception ex) {
                    //Print stack and use default value;
                    log.info("ShoppingServicesBean::getAutoOrderFactor" +
                            "invalid auto order factor: " + factorValStr);
                }
            }
        }
    }
        return autoOrderFactor;
    }
*/

}
