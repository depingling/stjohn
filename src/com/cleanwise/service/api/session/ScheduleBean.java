package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Title:        Schedule
 * Description:  Bean implementation for Schedule Session Bean
 * Purpose:      Schedule system
 *
 * @author Deping.
 */
public class ScheduleBean extends ApplicationServicesAPI {
	private final static String DATE_FORMAT = "MM/dd/yyyy";

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws java.rmi.RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }
    
    /**
	 * 
	 * @param storeId
	 * @return all the Corporate Schedules belong to store: storeId
	 * @throws RemoteException
	 */
    public DeliveryScheduleViewVector getCorporateSchedules(int pStoreId)throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = null;
            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDataAccess.BUS_ENTITY_ID, pStoreId);
            dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD, RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE);
            ScheduleDataVector scheduleDV = ScheduleDataAccess.select(conn, dbc);
            
            DBCriteria dbcSiteSched = new DBCriteria();
            dbcSiteSched.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID, ScheduleDataAccess.getSqlSelectIdOnly(ScheduleDataAccess.SCHEDULE_ID, dbc));
            dbcSiteSched.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                    RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
            ScheduleDetailDataVector sdDV =
                    ScheduleDetailDataAccess.select(conn,dbcSiteSched);
            
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("deleteCorporateSchedule: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }
    }

    /**
	 * 
	 * @param storeId
	 * @param scheduleId
	 * @return Corporate schedule match store StoreId and scheduleId
	 * @throws RemoteException
	 */
	public DeliveryScheduleViewVector getCorporateSchedules(int pStoreId, int pScheduleId)
    throws RemoteException {
		DBCriteria dbc = null;
        dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD, RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE);
		dbc.addEqualTo(ScheduleDataAccess.BUS_ENTITY_ID, pStoreId);
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_ID, pScheduleId);
        return getCorporateSchedules(dbc);
	}
	
	/**
	 * 
	 * @param storeId
	 * @param scheduleName
	 * @param dateFrom
	 * @param dateTo
	 * @param startWithFl
	 * @return return list of Corporate Schedules by search criteria
	 * @throws RemoteException
	 */
	public DeliveryScheduleViewVector getCorporateSchedules(int pStoreId, String pScheduleName, 
			Date pDateFrom, Date pDateTo, boolean pStartWithFl) 
	throws RemoteException {
		DBCriteria dbc = null;
        dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD, RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE);    	
        dbc.addEqualTo(ScheduleDataAccess.BUS_ENTITY_ID, pStoreId);
        if (Utility.isSet(pScheduleName)){        	
        	if (pStartWithFl){
        		dbc.addBeginsWithIgnoreCase(ScheduleDataAccess.SHORT_DESC, pScheduleName);
        	}else{
        		dbc.addContainsIgnoreCase(ScheduleDataAccess.SHORT_DESC, pScheduleName);
        	}
        }
        String dateFromStr = null;
        String dateToStr = null;
        try {
        	if (pDateFrom != null) {
        		dateFromStr = Utility.convertDateToDBString(pDateFrom, false);
        	}
        	if (pDateTo != null) {
        		dateToStr = Utility.convertDateToDBString(pDateTo, false);
        	}
         
	        String condition = null;
	        if (dateFromStr != null && dateToStr != null){
	        	condition = "	and to_date(value,'mm/dd/yyyy') between to_date('" + dateFromStr + "','mm/dd/yyyy') and to_date('" + dateToStr + "','mm/dd/yyyy')";
	        }else if (dateFromStr != null){
	        	condition = "	and to_date(value,'mm/dd/yyyy') >= to_date('" + dateFromStr + "','mm/dd/yyyy') ";
	        }else if (dateToStr != null){
	        	condition = "	and to_date(value,'mm/dd/yyyy') <= to_date('" + dateToStr + "','mm/dd/yyyy') ";
	        }
	        if (condition != null){
	        	condition = "exists (" +
				"	select schedule_id from clw_schedule_detail " +
				"	where schedule_id=CLW_SCHEDULE.SCHEDULE_ID " +
				"   and schedule_detail_cd = 'ALSO_DATE' " + condition + ") ";
	        	dbc.addCondition(condition);
	        }
	        return getCorporateSchedules(dbc);
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RemoteException("getCorporateSchedules: " + e.getMessage());
        } 
    }
	
	/**
	 * Return active schedule for site pSiteId
	 * @param pSiteId
	 * @return
	 * @throws RemoteException
	 */
	public ScheduleData getSchedule(int pSiteId)
    throws RemoteException {
		DBCriteria dbc = null;
		dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID);
        dbc.addEqualTo(ScheduleDetailDataAccess.VALUE, pSiteId+"");
        String siteIdSql = ScheduleDetailDataAccess.getSqlSelectIdOnly(ScheduleDetailDataAccess.SCHEDULE_ID, dbc);
        dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD, RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE);
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_STATUS_CD, RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE);
		dbc.addOneOf(ScheduleDataAccess.SCHEDULE_ID, siteIdSql);        
		ScheduleDataVector scheduleDV = getScheduleDV(dbc);
		if (scheduleDV.isEmpty())
			return null;
		else if (scheduleDV.size()==1){
			return (ScheduleData) scheduleDV.get(0) ;
		} else {
			logError("Found: multiple schedules for site: "+pSiteId);
			return null;
		}
	}
	
	private ScheduleDataVector getScheduleDV(DBCriteria dbc) 
	throws RemoteException {
		ScheduleDataVector scheduleDV = new ScheduleDataVector();
		Connection conn = null;
        try {
            conn = getConnection();
            scheduleDV = ScheduleDataAccess.select(conn, dbc);
            
            return scheduleDV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getCorporateSchedules: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }
    }
    
	
	public DeliveryScheduleViewVector getCorporateSchedules(int pStoreId, int pSiteId, String pScheduleName, boolean pStartWithFl)
    throws RemoteException {
		DBCriteria dbc = null;
		dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID);
        dbc.addEqualTo(ScheduleDetailDataAccess.VALUE, pSiteId+"");
        String siteIdSql = ScheduleDetailDataAccess.getSqlSelectIdOnly(ScheduleDetailDataAccess.SCHEDULE_ID, dbc);
        
        dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD, RefCodeNames.SCHEDULE_TYPE_CD.CORPORATE);
		dbc.addEqualTo(ScheduleDataAccess.BUS_ENTITY_ID, pStoreId);
		if (Utility.isSet(pScheduleName)){        	
        	if (pStartWithFl){
        		dbc.addBeginsWithIgnoreCase(ScheduleDataAccess.SHORT_DESC, pScheduleName);
        	}else{
        		dbc.addContainsIgnoreCase(ScheduleDataAccess.SHORT_DESC, pScheduleName);
        	}
        }
		dbc.addOneOf(ScheduleDataAccess.SCHEDULE_ID, siteIdSql);        
        
        return getCorporateSchedules(dbc, false);
	}
    
	private DeliveryScheduleViewVector getCorporateSchedules(DBCriteria dbc) throws RemoteException {
        return  getCorporateSchedules(dbc, true);
    }

	private DeliveryScheduleViewVector getCorporateSchedules(DBCriteria dbc, boolean cutLongDates)
	throws RemoteException {
		DeliveryScheduleViewVector scheduleList = new DeliveryScheduleViewVector();
		Connection conn = null;
        try {
            conn = getConnection();
            ScheduleDataVector scheduleDV = ScheduleDataAccess.select(conn, dbc);
            if(scheduleDV.isEmpty()){
        		return scheduleList;
        	}
            for (int ii = 0; ii < scheduleDV.size(); ii++) {
                ScheduleData sD = (ScheduleData) scheduleDV.get(ii);
                DeliveryScheduleView dsVv = DeliveryScheduleView.createValue();
                int scheduleId = sD.getScheduleId();
                dsVv.setScheduleId(scheduleId);
                int distId = sD.getBusEntityId();
                dsVv.setBusEntityId(distId);
                dsVv.setScheduleName(sD.getShortDesc());
                dsVv.setScheduleStatus(sD.getScheduleStatusCd());
                dsVv.setNextDelivery(getNextDeliveryDate(scheduleId));
                
                //Details
                ArrayList detailTypes = new ArrayList();
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);

                dbc = new DBCriteria();
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleId);
                dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
                dbc.addOrderBy(ScheduleDetailDataAccess.SCHEDULE_DETAIL_ID);

                String deliveryDates = "";
                int deliveryDatesCount = -1;
                ScheduleDetailDataVector scheduleDetailDV = ScheduleDetailDataAccess.select(conn, dbc);
                for (int jj = 0; jj < scheduleDetailDV.size(); jj++) {
                    ScheduleDetailData sdD = (ScheduleDetailData) scheduleDetailDV.get(jj);
                    String detailCd = sdD.getScheduleDetailCd();
                    if (RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd)) {
                        deliveryDatesCount++;
                        if (deliveryDatesCount == 12 && cutLongDates) {
                            deliveryDates += " ... ";
                            continue;
                        }
                        if (deliveryDatesCount > 0) {
                            deliveryDates += ", ";
                        }
                        deliveryDates += sdD.getValue();
                    }else if (RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME.equals(detailCd)) {
                    	dsVv.setCutoffInfo(sdD.getValue()); ;
                    }
                }
                
                dsVv.setScheduleInfo("On dates: " + deliveryDates);
                scheduleList.add(dsVv);
            }            
            return scheduleList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getCorporateSchedules: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }
    }
    
	/**
     *  Gets a delivery schedule
     *@param  pScheduleId the schedule id
     *@return a set of ScheduleJoinView objects
     *@exception  RemoteException        if an error occurs
     */
    public ScheduleJoinView getScheduleById(int pScheduleId)
             throws RemoteException {
        ScheduleJoinView scheduleJoin = ScheduleJoinView.createValue();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = null;
            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_ID, pScheduleId);
            ScheduleDataVector scheduleDV = ScheduleDataAccess.select(conn, dbc);
            if (scheduleDV.size() == 0) {
                throw new Exception("No schedule found. Schedule id: " + pScheduleId);
            }
            scheduleJoin.setSchedule((ScheduleData) scheduleDV.get(0));

            ArrayList detailTypes = new ArrayList();
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE);

            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
            dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
            ScheduleDetailDataVector scheduleDetailDV =
                    ScheduleDetailDataAccess.select(conn, dbc);

            scheduleJoin.setScheduleDetail(scheduleDetailDV);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getScheduleById: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }

        return scheduleJoin;
    }
    
    /**
     *  Updates existing or inserts new schedule depending on scheduleId
     *  property
     *
     *@param  pSchedule            the schedule
     *@param  pScheduleDetailDV    the set of ScheduleDetail objects. 
     *@param  pUser                the user login name
     *@return                      Description of the Return Value
     *@exception  RemoteException  if an error occurs
     */
    public ScheduleJoinView saveSchedule(ScheduleData pSchedule,
            ScheduleDetailDataVector pScheduleDetailDV, String pUser)
    throws RemoteException {
        ScheduleJoinView scheduleRet = ScheduleJoinView.createValue();
        DBCriteria dbc = null;
        Connection conn = null;
        try {
            conn = getConnection();
            int scheduleId = pSchedule.getScheduleId();
            if (scheduleId != 0) {
                //Update schedule
                ScheduleData schedule = ScheduleDataAccess.select(conn, scheduleId);
                schedule.setBusEntityId(pSchedule.getBusEntityId());
                schedule.setShortDesc(pSchedule.getShortDesc());
                schedule.setCycle(pSchedule.getCycle());
                schedule.setScheduleTypeCd(pSchedule.getScheduleTypeCd());
                schedule.setScheduleStatusCd(pSchedule.getScheduleStatusCd());
                schedule.setScheduleRuleCd(pSchedule.getScheduleRuleCd());
                schedule.setEffDate(pSchedule.getEffDate());
                schedule.setExpDate(pSchedule.getExpDate());
                schedule.setModBy(pUser);
                ScheduleDataAccess.update(conn, schedule);

                //Delete
                ArrayList detailTypes = new ArrayList();
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);
                dbc = new DBCriteria();
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleId);
                dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
                ScheduleDetailDataVector scheduleDetDV =
                        ScheduleDetailDataAccess.select(conn, dbc);

                for (int ii = 0; ii < pScheduleDetailDV.size(); ii++) {
                    ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(ii);
                    nSdD.setScheduleDetailId(0);
                }
                
                for (int ii = 0; ii < scheduleDetDV.size(); ii++) {
                    ScheduleDetailData sdD = (ScheduleDetailData) scheduleDetDV.get(ii);
                    int detailId = sdD.getScheduleDetailId();
                    String type = sdD.getScheduleDetailCd();
                    String val = sdD.getValue();
                    boolean foundFl = false;
                    for (int jj = 0; jj < pScheduleDetailDV.size(); jj++) {
                        ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(jj);
                        if (nSdD.getScheduleDetailId() != 0) {
                            continue;
                        }
                        String nType = nSdD.getScheduleDetailCd();
                        String nVal = nSdD.getValue();
                        if (nType.equals(type)) {
                            if (nVal.equals(val)) {
                                foundFl = true;
                                nSdD.setScheduleDetailId(detailId);
                                break;
                            }
                            sdD.setValue(nVal);
                            sdD.setModBy(pUser);
                            nSdD.setScheduleDetailId(detailId);
                            ScheduleDetailDataAccess.update(conn, sdD);
                            foundFl = true;
                            break;
                        }
                    }
                    if (!foundFl) {
                        ScheduleDetailDataAccess.remove(conn, detailId);
                    }
                }
                //Save the rest details
                for (int ii = 0; ii < pScheduleDetailDV.size(); ii++) {
                    ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(ii);
                    int detailId = nSdD.getScheduleDetailId();
                    if (detailId != 0) {
                        continue;
                    }
                    if (isPhysicalInventoryPeriodScheduleType(nSdD.getScheduleDetailCd())) {
                        continue;
                    }
                    nSdD.setScheduleId(scheduleId);
                    nSdD.setAddBy(pUser);
                    nSdD.setModBy(pUser);
                    ScheduleDetailDataAccess.insert(conn, nSdD);
                }
                ///
                savePhysicalInventoryPeriods(conn, pSchedule, pScheduleDetailDV, pUser);
            } else {
                //Schedule does not exist
                pSchedule.setAddBy(pUser);
                pSchedule.setModBy(pUser);
                pSchedule.setEffDate(new Date());
                pSchedule = ScheduleDataAccess.insert(conn, pSchedule);
                scheduleId = pSchedule.getScheduleId();
                for (int ii = 0; ii < pScheduleDetailDV.size(); ii++) {
                    ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(ii);
                    int detailId = nSdD.getScheduleDetailId();
                    nSdD.setScheduleId(scheduleId);
                    nSdD.setAddBy(pUser);
                    nSdD.setModBy(pUser);
                    ScheduleDetailDataAccess.insert(conn, nSdD);
                }
            }
            //Retreive data
            ScheduleData sD = ScheduleDataAccess.select(conn, scheduleId);
            scheduleRet.setSchedule(sD);
            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleId);
            ScheduleDetailDataVector sdDV = ScheduleDetailDataAccess.select(conn, dbc);
            scheduleRet.setScheduleDetail(sdDV);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("saveDeiverySchedule: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }

        return scheduleRet;
    }
    
    private static boolean isPhysicalInventoryPeriodScheduleType(String type) {
        if (type == null) {
            return false;
        }
        if (type.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE) ||
            type.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE) ||
            type.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE)) {
            return true;
        }
        return false;
    }
    
    private void savePhysicalInventoryPeriods(Connection connection, ScheduleData scheduleData,
    		ScheduleDetailDataVector scheduleDetails, String userName) throws RemoteException {
    	try {
    		PhysicalInventoryPeriodArray periods = new PhysicalInventoryPeriodArray();
    		PhysicalInventoryPeriodArray periodsFromDb = new PhysicalInventoryPeriodArray();
    		ScheduleDetailData detailData = null;

    		///
    		ArrayList detailTypes = new ArrayList();
    		detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE);
    		detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE);
    		detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE);
    		DBCriteria criteria = new DBCriteria();
    		criteria.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleData.getScheduleId());
    		criteria.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
    		ScheduleDetailDataVector scheduleDetailsFromDb = ScheduleDetailDataAccess.select(connection, criteria);

    		loadPhysicalPeriodsFromScheduleDetailDataVector(periods, scheduleDetails);
    		loadPhysicalPeriodsFromScheduleDetailDataVector(periodsFromDb, scheduleDetailsFromDb);

    		if (!PhysicalInventoryPeriodArray.isEquals(periods, periodsFromDb)) {
    			String detailType = null;
    			for (int i = 0; i < scheduleDetailsFromDb.size(); ++i) {
    				detailData = (ScheduleDetailData) scheduleDetailsFromDb.get(i);
    				detailType = detailData.getScheduleDetailCd();
    				if (isPhysicalInventoryPeriodScheduleType(detailType)) {
    					ScheduleDetailDataAccess.remove(connection, detailData.getScheduleDetailId());
    				}
    			}
    			///
    			for (int i = 0; i < scheduleDetails.size(); ++i) {
    				detailData = (ScheduleDetailData) scheduleDetails.get(i);
    				detailType = detailData.getScheduleDetailCd();
    				if (isPhysicalInventoryPeriodScheduleType(detailType)) {
    					detailData.setScheduleId(scheduleData.getScheduleId());
    					detailData.setAddBy(userName);
    					detailData.setModBy(userName);
    					ScheduleDetailDataAccess.insert(connection, detailData);
    				}
    			}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RemoteException("savePhysicalInventoryPeriods: " + ex.getMessage());
    	}
    }
    
    private void loadPhysicalPeriodsFromScheduleDetailDataVector(
    		PhysicalInventoryPeriodArray periods,
    		ScheduleDetailDataVector scheduleDetails) {
    	if (periods == null || scheduleDetails == null) {
    		return;
    	}
    	ScheduleDetailData detailData = null;
    	String detailType = null;
    	String detailValue = null;
    	periods.startLoadingItems();
    	for (int i = 0; i < scheduleDetails.size(); ++i) {
    		detailData = (ScheduleDetailData) scheduleDetails.get(i);
    		detailType = detailData.getScheduleDetailCd();
    		detailValue = detailData.getValue();
    		if (isPhysicalInventoryPeriodScheduleType(detailType)) {
    			periods.addItem(detailType, detailValue);
    		}
    	}
    	boolean isOk = periods.finishLoadingItems();
    	if (!isOk) {
    		logInfo("[ScheduleBean.loadPhysicalPeriodsFromScheduleDetailDataVector] Invalid physical inventory period.");
    	}
    }


    /**
     *  Deletes the delivery schedule
     *
     *@param  pScheduleId          the schedule id
     *@exception  RemoteException  if an error occurs
     */
    public void deleteSchedule(int pScheduleId)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = null;
            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
            ScheduleDetailDataAccess.remove(conn, dbc);
            ScheduleDataAccess.remove(conn, pScheduleId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("deleteSchedule: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }

        return;
    }
    
    /**
     *  Gets configured schedule account ids
     *
     *@param  pScheduleId          the schedule id
     *@param  pAccountIds list of account id to filter or null
     *@returns List of account ids configured to the schedule
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getScheduleAccountIds(int pScheduleId, IdVector pAccountIds)
    throws RemoteException {
    	return getScheduleBusEntityId(pScheduleId, pAccountIds, RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
    }
    
    /**
     *  Updates accounts configrued to the distributor schedule
     *@param  pScheduleId          the schedule id
     *@param  pAcctIdToAdd         the configured accounts
     *@param  pAcctIdToDel         the unconfigured accounts
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    public void updateScheduleAccounts(int pScheduleId,
            IdVector pAcctIdToAdd, IdVector pAcctIdToDel, String pUser)
             throws RemoteException {        
        updateScheduleAcctsOrSites(pScheduleId, pAcctIdToAdd, pAcctIdToDel, RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID, pUser);
    }
    
    public void updateScheduleSites(int pScheduleId,
            IdVector pSiteIdToAdd, IdVector pSiteIdToDel, String pUser)
             throws RemoteException {        
        updateScheduleAcctsOrSites(pScheduleId, pSiteIdToAdd, pSiteIdToDel, RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID, pUser);
    }
    
    /**
     *  Updates accounts or sites configrued to corporate schedule
     *@param  pScheduleId          the schedule id
     *@param  pBusEntIdToAdd       the configured accounts or sites
     *@param  pBusEntIdToDel       the unconfigured accounts or sites
     *@param  pScheduleDetailCd    ACCOUNT_ID or SITE_ID
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    private void updateScheduleAcctsOrSites(int pScheduleId,
    		IdVector pBusEntIdToAdd, IdVector pBusEntIdToDel, String pScheduleDetailCd, String pUser)
    throws RemoteException {
    	Connection conn = null;
    	DBCriteria dbc = null;
    	try {
    		conn = getConnection();
    		SiteBean siteBean= new SiteBean();
    		//Remove
    		if(pBusEntIdToDel!=null && pBusEntIdToDel.size()>0) {
    			// remove site assoc first if ACCOUNT_ID
    			if (pScheduleDetailCd.equals(RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID)){
    				IdVector siteIds = getScheduleSiteIds(pScheduleId, null);
    				if (!siteIds.isEmpty()){
	    				for(Iterator iter=pBusEntIdToDel.iterator(); iter.hasNext();) {
	        				Integer acctId = (Integer) iter.next();
	        				IdVector accountIds = new IdVector();
	    					accountIds.add(acctId);
	    					QueryRequest qr = new QueryRequest();
	    					qr.filterByAccountIds(accountIds);
	    					qr.filterBySiteIdList(siteIds);
	
	    					SiteViewVector sVwV = siteBean.getSiteCollection(qr);
	    					if (!sVwV.isEmpty()){
	    						IdVector siteIdsDel = new IdVector();
	    						for(Iterator it=sVwV.iterator(); it.hasNext();) {
	    							SiteView sv = (SiteView) it.next();
	    							siteIdsDel.add(sv.getId());
	    						}
	    						updateScheduleAcctsOrSites(pScheduleId, null, siteIdsDel, RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID, pUser);
	    					}	
	        			}
    				}
    			}
    			dbc = new DBCriteria();
    			dbc.addOneOf(ScheduleDetailDataAccess.VALUE,Utility.getStringListFromIdVector(pBusEntIdToDel));
    			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, pScheduleDetailCd);
    			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID,pScheduleId);
    			ScheduleDetailDataAccess.remove(conn,dbc);
    		}
    		//Pick existing
    		if(pBusEntIdToAdd!=null && pBusEntIdToAdd.size()>0) {
    			dbc = new DBCriteria();
    			dbc.addOneOf(ScheduleDetailDataAccess.VALUE,Utility.getStringListFromIdVector(pBusEntIdToAdd));
    			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, pScheduleDetailCd);
    			dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID,pScheduleId);
    			IdVector busEntIdExists = ScheduleDetailDataAccess.selectIdOnly(conn, ScheduleDetailDataAccess.VALUE, dbc);
    			//Add
    			dbc = new DBCriteria();
    			dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,pBusEntIdToAdd);
    			if (busEntIdExists.size()>0)
    				dbc.addNotOneOf(BusEntityDataAccess.BUS_ENTITY_ID,busEntIdExists);
    			IdVector newAcctIdV = BusEntityDataAccess.selectIdOnly(conn,dbc);
    			for(Iterator iter = newAcctIdV.iterator(); iter.hasNext();) {
    				Integer idI = (Integer) iter.next();
    				ScheduleDetailData sdD = ScheduleDetailData.createValue();
    				sdD.setScheduleId(pScheduleId);
    				sdD.setScheduleDetailCd(pScheduleDetailCd);
    				sdD.setValue(idI.toString());
    				sdD.setAddBy(pUser);
    				sdD.setModBy(pUser);
    				ScheduleDetailDataAccess.insert(conn,sdD);
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RemoteException("updateScheduleAcctsOrSites: " + e.getMessage());
    	} finally {
    		try {
    			if (conn != null) {
    				conn.close();
    			}
    		} catch (Exception ex) {
    		}
    	}
    	return;
    }
    /**
     *  Gets configured schedule account ids
     *
     *@param  pScheduleId          the schedule id
     *@param  pSiteIds list of account id to filter or null
     *@returns List of account ids configured to the schedule
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getScheduleSiteIds(int pScheduleId, IdVector pSiteIds)
    throws RemoteException {
        return getScheduleBusEntityId(pScheduleId, pSiteIds, RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID);
    }
    
    private IdVector getScheduleBusEntityId(int pScheduleId, IdVector pBusEntityIds, String pBusEntityTypeCd)
    throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
          conn = getConnection();
          dbc = new DBCriteria();
          dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID,pScheduleId);
          if (pBusEntityTypeCd.equals(RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID)){
        	  dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
        			  RefCodeNames.SCHEDULE_DETAIL_CD.SITE_ID);
          }else{
        	  dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
        			  RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
          }
          if(pBusEntityIds!=null) {
            dbc.addOneOf(ScheduleDetailDataAccess.VALUE,Utility.getStringListFromIdVector(pBusEntityIds));
          }
          ScheduleDetailDataVector
                  sdDV = ScheduleDetailDataAccess.select(conn,dbc);
          IdVector configAcctIds = new IdVector();
          for(Iterator iter = sdDV.iterator(); iter.hasNext();) {
            ScheduleDetailData sdD = (ScheduleDetailData) iter.next();
            String acctIdStr = sdD.getValue();
            try {
                configAcctIds.add(new Integer(acctIdStr));
            } catch (Exception exc) {
              logInfo("ScheduleBean. ERROR. Invalid value of accountId: "+acctIdStr);
            }
          }
          return configAcctIds;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getScheduleBusEntityId: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }  
        }
    }
    //---------------------------------------------------------------------------
    /**
     *  Calculates next corporate schedule date for the schedule
     *
     *@param  pScheduleId  of the Schedule identifier
     *@return                      Date object
     *@exception  RemoteException  if an error occurs
     */
    public Date getNextDeliveryDate(int pScheduleId)
             throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            Date currDate = new Date();
            ScheduleProc scheduleProc = createScheduleProc (conn, pScheduleId);
            GregorianCalendar deliveryCal = scheduleProc.getOrderDeliveryDate(currDate,currDate);
            if (deliveryCal != null)
            	return deliveryCal.getTime();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getNextDeliveryDate: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }
    
    private ScheduleProc createScheduleProc (Connection pConn, int pScheduleId)
    throws Exception
    {
    	//get corporate schedule
    	ScheduleJoinView   sjVw = getScheduleById(pScheduleId);

    	ScheduleProc scheduleProc = new ScheduleProc(sjVw,null);
    	scheduleProc.initSchedule();
    	return scheduleProc;
    }

    public void configureAllAccounts(int pScheduleId, String pUser)
	throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
            ScheduleData scheduleD = ScheduleDataAccess.select(conn, pScheduleId);
            int storeId = scheduleD.getBusEntityId();
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            String acctStoreReq =
               BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,acctStoreReq);
            dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            IdVector acctIds = BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID,dbc);
            updateScheduleAccounts(pScheduleId, acctIds, null, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("configureAllAccounts: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }
    
    public void configureAllAccountSites(int pScheduleId, IdVector pAccountIds, String pUser)
	throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
            for (int i = 0; i < pAccountIds.size(); i++){
            	int accountId = ((Integer)(pAccountIds.get(i))).intValue();
            	DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountId);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                String siteAcctReq =
                   BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,siteAcctReq);
                dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                        RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
                IdVector siteIds = BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID,dbc);
                updateScheduleSites(pScheduleId, siteIds, null, pUser);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("configureAllAccountSites: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }
    //STJ-5757.
    public String getCorpScheduleIntervalValue(int scheduleId) throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
	    	ArrayList detailTypes = new ArrayList();
	        detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);
	
	        DBCriteria dbc = new DBCriteria();
	        dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleId);
	        dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
	        dbc.addOrderBy(ScheduleDetailDataAccess.SCHEDULE_DETAIL_ID);
	
	        ScheduleDetailDataVector scheduleDetailDV = ScheduleDetailDataAccess.select(conn, dbc);
	        Iterator iterator = scheduleDetailDV.iterator();
	        while(iterator.hasNext()) {
	        	ScheduleDetailData scheduleDetailData = (ScheduleDetailData)iterator.next();
	        	return scheduleDetailData.getValue();
	        }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("configureAllAccountSites: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return null;
    }
}
