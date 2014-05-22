package com.cleanwise.service.api.session;

/**
 * Title:        AutoOrderBean
 * Description:  Bean implementation for AutoOrder Session Bean
 * Purpose:      Ejb for scheduled orders processing
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.apache.log4j.Category;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.OrderBatchLogDataAccess;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.cleanwise.service.api.dao.OrderScheduleDataAccess;
import com.cleanwise.service.api.dao.OrderScheduleDetailDataAccess;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.framework.MsdsSpecsAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ContentDetailView;
import com.cleanwise.service.api.value.ContentDetailViewVector;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.CustomerOrderRequestData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderBatchLogData;
import com.cleanwise.service.api.value.OrderBatchLogDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderScheduleData;
import com.cleanwise.service.api.value.OrderScheduleDataVector;
import com.cleanwise.service.api.value.OrderScheduleDetailData;
import com.cleanwise.service.api.value.OrderScheduleDetailDataVector;
import com.cleanwise.service.api.value.OrderScheduleJoin;
import com.cleanwise.service.api.value.OrderScheduleView;
import com.cleanwise.service.api.value.OrderScheduleViewVector;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.WorkOrderContentViewVector;
import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.api.value.WorkOrderDetailViewVector;
import com.cleanwise.service.api.value.WorkOrderSimpleSearchCriteria;
import com.cleanwise.service.api.value.OrderRequestData.ItemEntryVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.utils.Constants;

public class AutoOrderBean extends MsdsSpecsAPI
{
	private static final Logger log = Logger.getLogger(AutoOrderBean.class);
  /**
   *
   */
  public AutoOrderBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}
   //***************************************************************************
  
  
  
  
  /**
   * Get OrderSchedules for a site, within date range
   */
  
  public ArrayList getOrderSchedulesList(int pSiteId, Date pBeginDate, Date pEndDate, String pScheduleCd)
  throws RemoteException, DataNotFoundException{
	  
	  Connection conn = null;
	  ArrayList<OrderScheduleJoin> orderSchedulesList = new ArrayList<OrderScheduleJoin>();
	  
	  try{
		  DBCriteria dbc;
		  conn = getConnection();
		  
		  dbc = new DBCriteria();
          dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
          dbc.addEqualTo(OrderScheduleDataAccess.ORDER_SCHEDULE_CD, pScheduleCd);
       
          dbc.addLessThan(OrderScheduleDataAccess.EFF_DATE, pEndDate);
          dbc.addGreaterThan(OrderScheduleDataAccess.EXP_DATE, pBeginDate);
          
          dbc.addEqualTo(OrderScheduleDataAccess.BUS_ENTITY_ID,pSiteId);
          dbc.addIsNullOr0(OrderScheduleDataAccess.WORK_ORDER_ID);
          dbc.addOrderBy(OrderScheduleDataAccess.ORDER_SCHEDULE_ID);

          IdVector orderScheduleIds = OrderScheduleDataAccess.selectIdOnly(conn, OrderScheduleDataAccess.ORDER_SCHEDULE_ID, dbc);
          
          for (int ii=0; ii<orderScheduleIds.size(); ii++) {
        	  Integer scheduleId = (Integer)orderScheduleIds.get(ii);
        	  OrderScheduleJoin orderSchedule = getOrderSchedule(scheduleId.intValue());
        	  
        	  if(orderSchedule != null){
        		  orderSchedulesList.add(orderSchedule);
        	  }
          }

      }catch (NamingException exc) {
          logError(exc.getMessage());
          exc.printStackTrace();
          throw new RemoteException("Error. AutoOrderBean.getOrderSchedulesList() Naming Exception happened. "+exc.getMessage());
      }catch (SQLException exc) {
          logError(exc.getMessage());
          exc.printStackTrace();
          throw new RemoteException("Error. AutoOrderBean.getOrderSchedulesList() SQL Exception happened. "+exc.getMessage());
      }finally {
          closeConnection(conn);
      }	  
	  return orderSchedulesList;
  }
    /**
     * Gets the Order schedule
     * @param pOrderScheduleId  the order schedule id
     * @return an OrderScheduleJoin object
     * @throws   RemoteException Required by EJB 1.0, DataNotFoundException
     */
  public OrderScheduleJoin getOrderSchedule(int pOrderScheduleId)
  throws RemoteException, DataNotFoundException
  {
    Connection con = null;
  	OrderScheduleJoin orderScheduleJ = null;
	try {
	  DBCriteria dbc;
	  con = getConnection();
      OrderScheduleData orderScheduleD = OrderScheduleDataAccess.select(con,pOrderScheduleId);
      //get user
      dbc = new DBCriteria();
      dbc.addEqualTo(UserDataAccess.USER_ID,orderScheduleD.getUserId());
      UserDataVector userDV = UserDataAccess.select(con,dbc);
      if(userDV.size()==0) {
        throw new RemoteException("No user for the order schedule. Order scheduleId: "+pOrderScheduleId);
      }
      UserData userD = (UserData) userDV.get(0);
      //get site
      dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,orderScheduleD.getBusEntityId());
      BusEntityDataVector siteBusEntityDV = BusEntityDataAccess.select(con,dbc);
      if(siteBusEntityDV.size()==0) {
        throw new RemoteException("No site for the order schedule. Order scheduleId: "+pOrderScheduleId);
      }
      BusEntityData siteBusEntityD = (BusEntityData) siteBusEntityDV.get(0);
      //get account
      dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,siteBusEntityD.getBusEntityId());
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
      String accountAssocReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,accountAssocReq);
      BusEntityDataVector accountBusEntityDV = BusEntityDataAccess.select(con,dbc);
      if(accountBusEntityDV.size()==0) {
        throw new RemoteException("No account for the site. Site id: "+siteBusEntityD.getBusEntityId());
      }
      BusEntityData accountBusEntityD = (BusEntityData) accountBusEntityDV.get(0);
      //get order guide
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_ID,orderScheduleD.getOrderGuideId());
      OrderGuideDataVector orderGuideDV = OrderGuideDataAccess.select(con,dbc);
      if(orderGuideDV.size()==0) {
        throw new RemoteException("No order guide for the order schedule. Order scheduleId: "+pOrderScheduleId);
      }
      OrderGuideData orderGuideD = (OrderGuideData) orderGuideDV.get(0);
      //get order schedule details
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderScheduleDetailDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
      dbc.addEqualTo(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_ID, pOrderScheduleId);
      OrderScheduleDetailDataVector scheduleDetails = OrderScheduleDetailDataAccess.select(con,dbc);
      List elemL = new ArrayList();
	  List alsoL = new ArrayList();
	  List exceptL = new ArrayList();
          String contactName = "";
          String contactPhone = "";
          String contactEmail = "";
	  String scheduleDetailCd = null;
      for(int ii=0; ii<scheduleDetails.size(); ii++) {
        OrderScheduleDetailData osdD = (OrderScheduleDetailData) scheduleDetails.get(ii);
	    String detailCd = osdD.getOrderScheduleDetailCd();
	    String elemS = osdD.getDetail();
	    if(elemS!=null) {
	      elemS = elemS.trim();
          if(elemS.length()>0) {
		    if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ELEMENT.equals(detailCd)) {
		      elemL.add(elemS);
            }
			else if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd)) {
			  alsoL.add(elemS);
			}
			else if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(detailCd)) {
			  exceptL.add(elemS);
			}
                        else if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_NAME.equals(detailCd)) {
			  contactName = elemS;
			}
                        else if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_PHONE.equals(detailCd)) {
			  contactPhone = elemS;
			}
                        else if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_EMAIL.equals(detailCd)) {
			  contactEmail = elemS;
			}
			else {
			  throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Unknown order schedule detail type: "+detailCd);
            }
	      }
		}
      }
      //Create join object
      orderScheduleJ = OrderScheduleJoin.createValue();
      orderScheduleJ.setUserFirstName(userD.getFirstName());
      orderScheduleJ.setUserLastName(userD.getLastName());
      orderScheduleJ.setUserId(userD.getUserId());
      orderScheduleJ.setSiteName(siteBusEntityD.getShortDesc());
      orderScheduleJ.setSiteId(siteBusEntityD.getBusEntityId());
      orderScheduleJ.setAccountName(accountBusEntityD.getShortDesc());
      orderScheduleJ.setAccountId(accountBusEntityD.getBusEntityId());
      orderScheduleJ.setOrderGuideName(orderGuideD.getShortDesc());
      orderScheduleJ.setOrderGuideId(orderGuideD.getOrderGuideId());
      orderScheduleJ.setOrderScheduleId(orderScheduleD.getOrderScheduleId());
      orderScheduleJ.setEffDate(orderScheduleD.getEffDate());
      orderScheduleJ.setExpDate(orderScheduleD.getExpDate());
      orderScheduleJ.setCcEmail(orderScheduleD.getCcEmail());
      orderScheduleJ.setOrderScheduleCd(orderScheduleD.getOrderScheduleCd());
      orderScheduleJ.setCycle(orderScheduleD.getCycle());
      String orderScheduleRuleCd = orderScheduleD.getOrderScheduleRuleCd();
      orderScheduleJ.setOrderScheduleRuleCd(orderScheduleRuleCd);
      int elemSize = elemL.size();
      int[] elements = new int[elemSize];

      for(int ii=0; ii<elemSize; ii++) {
        int stInd = 0;
        int stInd2 = 0;
	String ss = (String) elemL.get(ii);
        String subSt = "";

	 if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(orderScheduleRuleCd)) {

	  stInd = ss.indexOf(':');
          int next = stInd+1;
          subSt = ss.substring(next, ss.length());

          stInd2 = subSt.indexOf(':');

	  if (stInd<0 || stInd2<0) {
	    throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: "+ss+" Order guide schedule id: "+orderScheduleD.getOrderScheduleId());
	  }
	  if(ii==0) {
	    int weekDay = 0;
            int week = 0;
	    try {
	      weekDay = Integer.parseInt(ss.substring(0,stInd));
              week = Integer.parseInt(subSt.substring(0,stInd2));
	    } catch (Exception exc) {
		throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: "+ss+" Order guide schedule id: "+orderScheduleD.getOrderScheduleId());
	    }
	    orderScheduleJ.setMonthWeekDay(weekDay);
            orderScheduleJ.setMonthWeeks(week);
	  }
	  stInd2++;
          //set the index to our latest, for data compatiblity issues
          stInd = stInd2;
          ss = subSt;
	  if(stInd2>=ss.length()) {
	    throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: "+ss+" Order guide schedule id: "+orderScheduleD.getOrderScheduleId());
	  }
	} // end WEEK_MONTH
	try {
	    int elem = Integer.parseInt(ss.substring(stInd));
	    elements[ii]=elem;
	} catch (Exception exc) {
	  throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: "+ss+" Order guide schedule id: "+orderScheduleD.getOrderScheduleId());
	}
      }
	  orderScheduleJ.setElements(elements);
	  DateFormat dateFormat = DateFormat.getInstance();
	  int alsoSize = alsoL.size();
	  Date[] also = new Date[alsoSize];
	  for(int ii=0; ii<alsoSize; ii++) {
	    String ss = (String) alsoL.get(ii);
	    GregorianCalendar dd = createCalendar(ss);
	    if(dd==null) {
		  throw new RemoteException("Error. AutoOrderBean.createOrderScheduleView() Wrong order schedule detail date formar: "+ss+" Order guide schedule id: "+orderScheduleD.getOrderScheduleId());
	    }
	    also[ii] = dd.getTime();
	  }
	  orderScheduleJ.setAlsoDates(also);

          orderScheduleJ.setContactName(contactName);
          orderScheduleJ.setContactPhone(contactPhone);
          orderScheduleJ.setContactEmail(contactEmail);


	  int exceptSize = exceptL.size();
	  Date[] except = new Date[exceptSize];
	  for(int ii=0; ii<exceptSize; ii++) {
	    String ss = (String) exceptL.get(ii);
	    GregorianCalendar dd = createCalendar(ss);
	    if(dd==null) {
          throw new RemoteException("Error. AutoOrderBean.createOrderScheduleView() Wrong order schedule detail date formar: "+ss+" Order guide schedule id: "+orderScheduleD.getOrderScheduleId());
	    }
	    except[ii] = dd.getTime();
	  }
	  orderScheduleJ.setExceptDates(except);
	}
	catch (NamingException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Naming Exception happened. "+exc.getMessage());
	}
	catch (SQLException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() SQL Exception happened. "+exc.getMessage());
	}
	finally {
	    try {
		if(con!=null) con.close();
	    }
	    catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() SQL Exception happened. "+exc.getMessage());
	    }
	}
  	return orderScheduleJ;
  }
  /******************************************************************************************/
  /**
   * Gets the list of Order schedules
   * @param pBusEntityId  the site id
   * @param pUserId the user id
   * @return a list of OrderScheduleView objects
   * @throws   RemoteException Required by EJB 1.0
   */
  public OrderScheduleViewVector getOrderSchedules(int pBusEntityId, int pUserId)
  throws RemoteException
  {
	Connection con = null;
	OrderScheduleViewVector orderSchedules = new OrderScheduleViewVector();
	try {
      DBCriteria dbc;
	  con = getConnection();

      dbc = new DBCriteria();
	  dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
	  dbc.addEqualTo(OrderScheduleDataAccess.BUS_ENTITY_ID,pBusEntityId);
      dbc.addIsNullOr0(OrderScheduleDataAccess.WORK_ORDER_ID);
      //dbc.addEqualTo(OrderScheduleDataAccess.USER_ID,pUserId);
	  dbc.addOrderBy(OrderScheduleDataAccess.ORDER_SCHEDULE_ID);
	  IdVector orderScheduleIds = OrderScheduleDataAccess.selectIdOnly(con,OrderScheduleDataAccess.ORDER_SCHEDULE_ID,dbc);
      String idsString = "";
      for (int ii=0; ii<orderScheduleIds.size(); ii++) {
        if(ii!=0) idsString +=", ";
        Integer idI = (Integer) orderScheduleIds.get(ii);
        idsString += idI;
      }
      if(idsString.length()>0) {
        orderSchedules = getOrderScheduleViewVector(con,idsString);
      }
	}
	catch (NamingException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Naming Exception happened. "+exc.getMessage());
	}
	catch (SQLException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() SQL Exception happened. "+exc.getMessage());
	}
	finally {
	    try {
		if(con!=null) con.close();
	    }
	    catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() SQL Exception happened. "+exc.getMessage());
	    }
	}
	return orderSchedules;
    }

    /******************************************************************************************/
    /**
     * Gets the list of Order schedules
     * @param pUserId the user id
     * @param bDate  Begin Date
     * @param eDate  End Date
     * @return a list of OrderScheduleView objects
     * @throws   RemoteException Required by EJB 1.0
     */
    public OrderScheduleViewVector getOrderSchedules(int pUserId, Date bDate, Date eDate)
            throws RemoteException
    {

        Connection con = null;
        OrderScheduleViewVector orderSchedules = new OrderScheduleViewVector();

        try {

            DBCriteria dbc;
            con = getConnection();

            dbc = new DBCriteria();
            dbc.addEqualTo    (OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
            dbc.addGreaterThan(OrderScheduleDataAccess.EFF_DATE,         bDate);
            dbc.addLessThan   (OrderScheduleDataAccess.EFF_DATE,         eDate);
            dbc.addEqualTo    (OrderScheduleDataAccess.USER_ID,          pUserId);
            dbc.addIsNullOr0     (OrderScheduleDataAccess.WORK_ORDER_ID);
            dbc.addOrderBy    (OrderScheduleDataAccess.ORDER_SCHEDULE_ID);

            IdVector orderScheduleIds = OrderScheduleDataAccess.selectIdOnly(con, OrderScheduleDataAccess.ORDER_SCHEDULE_ID, dbc);
            String idsString = "";

            for (int ii=0; ii<orderScheduleIds.size(); ii++) {
                if(ii!=0) idsString +=", ";
                Integer idI = (Integer) orderScheduleIds.get(ii);
                idsString += idI;
            }

            if(idsString.length()>0) {
                orderSchedules = getOrderScheduleViewVector(con, idsString);
            }

        }
        catch (NamingException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Naming Exception happened. "+exc.getMessage());
        }
        catch (SQLException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() SQL Exception happened. "+exc.getMessage());
        }
        finally {
            closeConnection(con);
        }
        return orderSchedules;
    }
  //***************************************************************************
  /**
  * Gets vector of order schedules
  * @param pAccountId the account identifier
  * @param pSiteId the site identifier
  * @param pSiteName part of the site short description
  * @param pOrderGuideName part of the order guide name
  * @param pOrderScheduleType type of the scheduled aciont (RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER, RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY  or nothing)
  * @param pOrderScheduleRuleType type of schedule rule (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK, RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH, RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST or nothing)
  * @param pDateFor the schedule date, when shceduled action should be performed
  * @param pMatch string matching operaition (SearchCriteria.BEGINS_WITH_IGNORE_CASE or SearchCriteria.CONTAINS_IGNORE_CASE)
   * @throws   RemoteException Required by EJB 1.0
  */
  public OrderScheduleViewVector getOrderSchedules(int pAccountId, int pSiteId,
         String pSiteName, String pOrderGuideName, String pOrderScheduleType,
         String pOrderScheduleRuleType, Date pDateFor, int pMatch)
  throws RemoteException
  {
    OrderScheduleViewVector schedules = new OrderScheduleViewVector();
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbcMain = new DBCriteria();
      DBCriteria dbc;
      boolean searchAll = true;
      if(pAccountId!=0 || pSiteId!=0 || (pSiteName!=null && pSiteName.trim().length()>0)) {
        dbc = new DBCriteria();
        if(pAccountId!=0) {
          DBCriteria dbc1 = new DBCriteria();
          dbc1.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pAccountId);
          dbc1.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
          String siteOfAccountReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc1);
          dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,siteOfAccountReq);
        }
        if(pSiteId!=0) {
          dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,pSiteId);
        }
        if(pSiteName!=null && pSiteName.trim().length()>0) {
          String siteName = pSiteName.trim().toUpperCase();
          switch(pMatch) {
            case SearchCriteria.BEGINS_WITH_IGNORE_CASE:
              dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,siteName+"%");
              break;
            case SearchCriteria.CONTAINS_IGNORE_CASE:
              dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,"%"+siteName+"%");
              break;
            default:
             throw new RemoteException("Illegal match type: "+pMatch);
          }
        }
        String siteReq = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID,dbc);
        dbcMain.addOneOf(OrderScheduleDataAccess.BUS_ENTITY_ID,siteReq);
      }
      if(pOrderGuideName!=null && pOrderGuideName.trim().length()>0) {
        String orderGuideName = pOrderGuideName.trim().toUpperCase();
        dbc = new DBCriteria();
        switch(pMatch) {
          case SearchCriteria.BEGINS_WITH_IGNORE_CASE:
            dbc.addLikeIgnoreCase(OrderGuideDataAccess.SHORT_DESC,orderGuideName+"%");
            break;
          case SearchCriteria.CONTAINS_IGNORE_CASE:
            dbc.addLikeIgnoreCase(OrderGuideDataAccess.SHORT_DESC,"%"+orderGuideName+"%");
            break;
          default:
            throw new RemoteException("Illegal match type: "+pMatch);
        }
        String orderGuideReq = OrderGuideDataAccess.getSqlSelectIdOnly(OrderGuideDataAccess.ORDER_GUIDE_ID,dbc);
        dbcMain.addOneOf(OrderScheduleDataAccess.ORDER_GUIDE_ID,orderGuideReq);
      }
      if(pOrderScheduleType!=null && pOrderScheduleType.trim().length()>0) {
        dbcMain.addEqualTo(OrderScheduleDataAccess.ORDER_SCHEDULE_CD,pOrderScheduleType);
      }
      if(pOrderScheduleRuleType!=null && pOrderScheduleRuleType.trim().length()>0) {
        dbcMain.addEqualTo(OrderScheduleDataAccess.ORDER_SCHEDULE_RULE_CD,pOrderScheduleRuleType);
      }
      dbcMain.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD,RefCodeNames.RECORD_STATUS_CD.VALID);
      dbcMain.addIsNullOr0(OrderScheduleDataAccess.WORK_ORDER_ID);
      OrderScheduleDataVector orderScheduleDV = OrderScheduleDataAccess.select(con,dbcMain);
      if(pDateFor!=null) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFor = sdf.parse(sdf.format(pDateFor));
        for(int ii=0; ii<orderScheduleDV.size();) {
          OrderScheduleData osD = (OrderScheduleData) orderScheduleDV.get(ii);
          int id = verifyOrderScheduleToProcess(con, osD, dateFor);
          if(id==0) {
             orderScheduleDV.remove(ii);
          } else {
            ii++;
          }
        }
      }
      IdVector scheduleIds = new IdVector();
      StringBuffer ids = new StringBuffer();
      for(int ii=0; ii<orderScheduleDV.size(); ii++) {
        OrderScheduleData osD = (OrderScheduleData) orderScheduleDV.get(ii);
        ids.append(osD.getOrderScheduleId());
        if((ii!=0 && ii%500==0) || (ii==orderScheduleDV.size()-1) ) {
          schedules.addAll(getOrderScheduleViewVector(con,ids.toString()));
          ids = new StringBuffer();
        } else {
          ids.append(",");
        }
      }
    }
    catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    }
    finally {
      try {
         if(con!=null) con.close();
      }
      catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException("Error. AutoOrderBean.saveOrderSchedule() SQL Exception happened. "+exc.getMessage());
      }
    }
    return schedules;
  }
  //-----------------------------------------------------------------------------
  private OrderScheduleViewVector getOrderScheduleViewVector(Connection pCon, String pOrderScheduleIds)
    throws SQLException
  {
    StringBuffer sqlBuf = new StringBuffer("select ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.ORDER_SCHEDULE_ID);
    sqlBuf.append(", ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.ORDER_GUIDE_ID);
    sqlBuf.append(", ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.BUS_ENTITY_ID);
    sqlBuf.append(", ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.ORDER_SCHEDULE_CD);
    sqlBuf.append(", ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.ORDER_SCHEDULE_RULE_CD);
    sqlBuf.append(", ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.EFF_DATE);
    sqlBuf.append(", ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.EXP_DATE);
    sqlBuf.append(", ");

    sqlBuf.append("og.");
    sqlBuf.append(OrderGuideDataAccess.SHORT_DESC);
    sqlBuf.append(", ");

    sqlBuf.append("site.");
    sqlBuf.append(BusEntityDataAccess.SHORT_DESC);
    sqlBuf.append(", ");

    sqlBuf.append("account.");
    sqlBuf.append(BusEntityDataAccess.BUS_ENTITY_ID);
    sqlBuf.append(", ");

    sqlBuf.append("account.");
    sqlBuf.append(BusEntityDataAccess.SHORT_DESC);
    sqlBuf.append(", ");

    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.USER_ID);

    sqlBuf.append(" from CLW_ORDER_SCHEDULE os, CLW_ORDER_GUIDE og, CLW_BUS_ENTITY site, CLW_BUS_ENTITY_ASSOC assoc, CLW_BUS_ENTITY account");
    sqlBuf.append(" where ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.ORDER_GUIDE_ID);
    sqlBuf.append("=");
    sqlBuf.append("og.");
    sqlBuf.append(OrderGuideDataAccess.ORDER_GUIDE_ID);

    sqlBuf.append(" and ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.BUS_ENTITY_ID);
    sqlBuf.append("=");
    sqlBuf.append("site.");
    sqlBuf.append(BusEntityDataAccess.BUS_ENTITY_ID);

    sqlBuf.append(" and ");
    sqlBuf.append("site.");
    sqlBuf.append(BusEntityDataAccess.BUS_ENTITY_ID);
    sqlBuf.append("=");
    sqlBuf.append("assoc.");
    sqlBuf.append(BusEntityAssocDataAccess.BUS_ENTITY1_ID);

    sqlBuf.append(" and ");
    sqlBuf.append("assoc.");
    sqlBuf.append(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD);
    sqlBuf.append("='");
    sqlBuf.append(RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
    sqlBuf.append("'");

    sqlBuf.append(" and ");
    sqlBuf.append("assoc.");
    sqlBuf.append(BusEntityAssocDataAccess.BUS_ENTITY2_ID);
    sqlBuf.append("=");
    sqlBuf.append("account.");
    sqlBuf.append(BusEntityDataAccess.BUS_ENTITY_ID);

    sqlBuf.append(" and ");
    sqlBuf.append("os.");
    sqlBuf.append(OrderScheduleDataAccess.ORDER_SCHEDULE_ID);
    sqlBuf.append(" in (");
    sqlBuf.append(pOrderScheduleIds);
    sqlBuf.append(")");

    String sql = sqlBuf.toString();
    Category log = Category.getInstance(BusEntityDataAccess.class.getName());
    if (log.isDebugEnabled()) {
        log.debug("SQL: " + sql);
    }
    PreparedStatement stmt = pCon.prepareStatement(sql);
    ResultSet rs=stmt.executeQuery(sql);
    OrderScheduleViewVector v = new OrderScheduleViewVector();
    OrderScheduleView x=null;
    while (rs.next()) {
      x = OrderScheduleView.createValue();
      x.setOrderScheduleId(rs.getInt(1));
      x.setOrderGuideId(rs.getInt(2));
      x.setSiteId(rs.getInt(3));
      x.setOrderScheduleCd(rs.getString(4));
      x.setOrderScheduleRuleCd(rs.getString(5));
      x.setEffDate(rs.getDate(6));
      x.setExpDate(rs.getDate(7));
      x.setOrderGuideName(rs.getString(8));
      x.setSiteName(rs.getString(9));
      x.setAccountId(rs.getInt(10));
      x.setAccountName(rs.getString(11));
      x.setUserId(rs.getInt(12));
      v.add(x);
    }
    rs.close();
    stmt.close();
    return v;
  }

    //***************************************************************************
    /**
     * Saves to database the order schedule
     * @param pBusEntityId  the site id
     * @param pUserId the user id
     * @param pOrderScheduleJoin the schedule data
     * @param pUser the user logon name
     * @return the order schedule identifier
     * @throws   RemoteException Required by EJB 1.0
     */
    public int saveOrderSchedule(OrderScheduleJoin pOrderSchedule, String pUser)
	throws RemoteException
    {
	Connection con = null;
	int scheduleId = pOrderSchedule.getOrderScheduleId();
	try {
	    DBCriteria dbc;
	    con = getConnection();
	    Date currDate = new Date(System.currentTimeMillis());
	    long currMills = currDate.getTime();
	    Date effDate = pOrderSchedule.getEffDate();
	    if(effDate==null) {
		throw new RemoteException("^clwKey^shop.errors.emptyEffectiveDate^clwKey^");
	    }
	    long effMills = effDate.getTime();
	    long expMills = 0;
	    Date expDate = pOrderSchedule.getExpDate();
	    if(expDate!=null) {
		expMills = expDate.getTime();
	    }

	    dbc = new DBCriteria();
	    dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
	    dbc.addEqualTo(OrderScheduleDataAccess.BUS_ENTITY_ID,pOrderSchedule.getSiteId());
//	    dbc.addEqualTo(OrderScheduleDataAccess.USER_ID,pOrderSchedule.getUserId());
	    dbc.addOrderBy(OrderScheduleDataAccess.MOD_DATE);
	    OrderScheduleDataVector orderScheduleDV = OrderScheduleDataAccess.select(con,dbc);
	    OrderScheduleData orderScheduleD = null;
	    boolean newFlag = true;
      int orderGuideId = pOrderSchedule.getOrderGuideId();
      int workOrderId  = pOrderSchedule.getWorkOrderId();
      int userId = pOrderSchedule.getUserId();
	    for(int ii=0; ii<orderScheduleDV.size(); ii++) {
		OrderScheduleData osD = (OrderScheduleData) orderScheduleDV.get(ii);
		if(scheduleId != osD.getOrderScheduleId()){
        if(orderGuideId!=osD.getOrderGuideId()) {
          continue;
        }
        if(workOrderId!=osD.getWorkOrderId()) {
             continue;
        }
        if(userId!=osD.getUserId()) {
          continue;
        }
		    Date effD = osD.getEffDate();
		    long effM = effD.getTime();
		    long expM = 0;
		    Date expD = osD.getExpDate();
		    if(expD!=null) {
			expM = expD.getTime();
		    }
		    if((expM==0 || expM>=effMills) &&
		       (effM<=expMills || expMills==0)) {
			throw new RemoteException("^clwKey^shop.errors.currentScheduleCrossesWihtSchedule^clwKey^^clwParam^"+
			    osD.getOrderScheduleId()+"^clwParam^^clwParam^"+effD+"^clwParam^^clwParam^"+expD+"^clwParam^");
		    }
		} else { // found record
		    orderScheduleD = osD;
		    long addMills = osD.getAddDate().getTime();
		    if(currMills-addMills>24*3600*1000) { //more then one day old - save old value
			//invalidate old schedule
			osD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.INVALID);
			osD.setModBy(pUser);
			OrderScheduleDataAccess.update(con,osD);
			dbc = new DBCriteria();
			dbc.addEqualTo(OrderScheduleDetailDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
			dbc.addEqualTo(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_ID,scheduleId);
			OrderScheduleDetailDataVector osrDV = OrderScheduleDetailDataAccess.select(con,dbc);
			for(int jj=0; jj<osrDV.size(); jj++) {
			    OrderScheduleDetailData osrD = (OrderScheduleDetailData) osrDV.get(jj);
			    osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.INVALID);
			    osrD.setModBy(pUser);
			    OrderScheduleDetailDataAccess.update(con,osrD);
			}
		    } else {
			newFlag = false;
			//Delete old schedule detail
			dbc = new DBCriteria();
			dbc.addEqualTo(OrderScheduleDetailDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
			dbc.addEqualTo(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_ID,scheduleId);
			OrderScheduleDetailDataAccess.remove(con,dbc);
		    }
		}
	    }
	    //
	    if(orderScheduleD==null) {
		orderScheduleD = OrderScheduleData.createValue();
		orderScheduleD.setUserId(pOrderSchedule.getUserId());
		orderScheduleD.setBusEntityId(pOrderSchedule.getSiteId());
		orderScheduleD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
	    }
	    orderScheduleD.setOrderScheduleRuleCd(pOrderSchedule.getOrderScheduleRuleCd());
	    orderScheduleD.setOrderScheduleCd(pOrderSchedule.getOrderScheduleCd());
	    orderScheduleD.setOrderGuideId(pOrderSchedule.getOrderGuideId());
        orderScheduleD.setWorkOrderId(pOrderSchedule.getWorkOrderId());
        orderScheduleD.setExpDate(pOrderSchedule.getExpDate());
	    orderScheduleD.setEffDate(pOrderSchedule.getEffDate());
	    orderScheduleD.setCycle(pOrderSchedule.getCycle());
	    orderScheduleD.setCcEmail(pOrderSchedule.getCcEmail());
	    orderScheduleD.setModBy(pUser);
	    if(newFlag) {
		orderScheduleD.setOrderScheduleId(0);
		orderScheduleD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
		orderScheduleD.setAddBy(pUser);
		orderScheduleD = OrderScheduleDataAccess.insert(con,orderScheduleD);
		scheduleId = orderScheduleD.getOrderScheduleId();
	    } else {
		OrderScheduleDataAccess.update(con,orderScheduleD);
	    }

	    //Add schedule
	    int elem[] = pOrderSchedule.getElements();
	    String prefix = "";
	    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(pOrderSchedule.getOrderScheduleRuleCd())) {
		int weekDay = pOrderSchedule.getMonthWeekDay();
		prefix = weekDay+":";
                int week = pOrderSchedule.getMonthWeeks();
                prefix += week+":";
	    }
	    for(int ii=0; ii<elem.length; ii++) {
		OrderScheduleDetailData osrD = OrderScheduleDetailData.createValue();
		osrD.setOrderScheduleDetailId(0);
		osrD.setOrderScheduleId(scheduleId);
		osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
		osrD.setOrderScheduleDetailCd(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ELEMENT);
		osrD.setDetail(prefix+elem[ii]);
		osrD.setAddBy(pUser);
		osrD.setModBy(pUser);
		OrderScheduleDetailDataAccess.insert(con,osrD);
	    }

	    Date also[] = pOrderSchedule.getAlsoDates();
	    for(int ii=0; ii<also.length; ii++) {
		OrderScheduleDetailData osrD = OrderScheduleDetailData.createValue();
		osrD.setOrderScheduleDetailId(0);
		osrD.setOrderScheduleId(scheduleId);
		osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
		osrD.setOrderScheduleDetailCd(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ALSO_DATE);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(also[ii]);
		String dd = (cal.get(GregorianCalendar.MONTH)+1)+"/"+cal.get(GregorianCalendar.DAY_OF_MONTH)+"/"+cal.get(GregorianCalendar.YEAR);
		osrD.setDetail(dd);
		osrD.setAddBy(pUser);
		osrD.setModBy(pUser);
		OrderScheduleDetailDataAccess.insert(con,osrD);
	    }

	    Date except[] = pOrderSchedule.getExceptDates();
	    for(int ii=0; ii<except.length; ii++) {
		OrderScheduleDetailData osrD = OrderScheduleDetailData.createValue();
		osrD.setOrderScheduleDetailId(0);
		osrD.setOrderScheduleId(scheduleId);
		osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
		osrD.setOrderScheduleDetailCd(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.EXCEPT_DATE);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(except[ii]);
		String dd = (cal.get(GregorianCalendar.MONTH)+1)+"/"+cal.get(GregorianCalendar.DAY_OF_MONTH)+"/"+cal.get(GregorianCalendar.YEAR);
		osrD.setDetail(dd);
		osrD.setAddBy(pUser);
		osrD.setModBy(pUser);
		OrderScheduleDetailDataAccess.insert(con,osrD);
	    }

            String contactName = pOrderSchedule.getContactName();
	    if(contactName != null && contactName.trim().length() > 0){
	        OrderScheduleDetailData osrD = OrderScheduleDetailData.createValue();
	        osrD.setOrderScheduleDetailId(0);
	        osrD.setOrderScheduleId(scheduleId);
	        osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
		            osrD.setOrderScheduleDetailCd(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_NAME);

  	        osrD.setDetail(contactName);
 	        osrD.setAddBy(pUser);
	        osrD.setModBy(pUser);
	        OrderScheduleDetailDataAccess.insert(con,osrD);
            }


            String contactPhone = pOrderSchedule.getContactPhone();
	    if(contactPhone != null && contactPhone.trim().length() > 0){
	        OrderScheduleDetailData osrD = OrderScheduleDetailData.createValue();
	        osrD.setOrderScheduleDetailId(0);
	        osrD.setOrderScheduleId(scheduleId);
	        osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
		            osrD.setOrderScheduleDetailCd(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_PHONE);

  	        osrD.setDetail(contactPhone);
 	        osrD.setAddBy(pUser);
	        osrD.setModBy(pUser);
	        OrderScheduleDetailDataAccess.insert(con,osrD);
            }

            String contactEmail = pOrderSchedule.getContactEmail();
	    if(contactEmail != null && contactEmail.trim().length() > 0){
	        OrderScheduleDetailData osrD = OrderScheduleDetailData.createValue();
	        osrD.setOrderScheduleDetailId(0);
	        osrD.setOrderScheduleId(scheduleId);
	        osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.VALID);
		            osrD.setOrderScheduleDetailCd(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_EMAIL);

  	        osrD.setDetail(contactEmail);
 	        osrD.setAddBy(pUser);
	        osrD.setModBy(pUser);
	        OrderScheduleDetailDataAccess.insert(con,osrD);
            }

	}
	catch (NamingException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.saveOrderSchedule() Naming Exception happened. "+exc.getMessage());
	}
	catch (SQLException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.saveOrderSchedule() SQL Exception happened. "+exc.getMessage());
	}
	finally {
	    try {
		if(con!=null) con.close();
	    }
	    catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException("Error. AutoOrderBean.saveOrderSchedule() SQL Exception happened. "+exc.getMessage());
	    }
	}
	return scheduleId;
    }
  //***************************************************************************
    /**
     * Deletes to database the order schedule
     * @param pOrderScheduleId the order schedule id
     * @param pUser the user logon name
     * @throws   RemoteException Required by EJB 1.0
     */
    public void deleteOrderSchedule(int pOrderScheduleId, String pUser)
	throws RemoteException
    {
	Connection con = null;
	try {
	    DBCriteria dbc;
	    con = getConnection();
	    dbc = new DBCriteria();
	    dbc.addEqualTo(OrderScheduleDataAccess.ORDER_SCHEDULE_ID, pOrderScheduleId);
	    try {
		OrderScheduleData orderScheduleD = OrderScheduleDataAccess.select(con,pOrderScheduleId);
		orderScheduleD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.INVALID);
		orderScheduleD.setModBy(pUser);
		OrderScheduleDataAccess.update(con,orderScheduleD);
	    } catch (DataNotFoundException exc) {}

	    dbc = new DBCriteria();
	    dbc.addEqualTo(OrderScheduleDetailDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
	    dbc.addEqualTo(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_ID,pOrderScheduleId);
	    OrderScheduleDetailDataVector osrDV = OrderScheduleDetailDataAccess.select(con,dbc);
	    for(int jj=0; jj<osrDV.size(); jj++) {
		OrderScheduleDetailData osrD = (OrderScheduleDetailData) osrDV.get(jj);
		osrD.setRecordStatusCd(RefCodeNames.RECORD_STATUS_CD.INVALID);
		osrD.setModBy(pUser);
		OrderScheduleDetailDataAccess.update(con,osrD);
	    }
	}
	catch (NamingException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.saveOrderSchedule() Naming Exception happened. "+exc.getMessage());
	}
	catch (SQLException exc) {
	    logError(exc.getMessage());
	    exc.printStackTrace();
	    throw new RemoteException("Error. AutoOrderBean.saveOrderSchedule() SQL Exception happened. "+exc.getMessage());
	}
	finally {
	    try {
		if(con!=null) con.close();
	    }
	    catch (SQLException exc) {
		logError(exc.getMessage());
		exc.printStackTrace();
		throw new RemoteException("Error. AutoOrderBean.saveOrderSchedule() SQL Exception happened. "+exc.getMessage());
	    }
	}
    }
  //***************************************************************************
  /**
  * Gets vector of order schedules to be placed or to remaind the user to place the order
  * @param pDateFor the schedule date, for which
   * @throws   RemoteException Required by EJB 1.0
  */
  public IdVector getOrderSchedules(Date pDateFor)
  throws RemoteException
  {
    return getOrderSchedules(pDateFor,null,true);
  }

//***************************************************************************
  /**
  * Gets vector of order schedules to be placed or to remaind the user to place the order
  * @param pDateFor the schedule date, for which
   *@param pAccountIds list of accounts to process or ignore
   * (depending on pForOrExceptFl), if null would process all accounts
   *@param pForOrExceptFl equals true or false
   * (to return schedules for accounts or to return all schedules except accounts provided)
   * @throws   RemoteException Required by EJB 1.0
  */
  public IdVector getOrderSchedules(Date pDateFor, IdVector pAccountIds, boolean pForOrExceptFl)
  throws RemoteException
  {
    IdVector scheduleIds = new IdVector();
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc;
      dbc = new DBCriteria();
      dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
      dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
      IdVector storeIds = BusEntityDataAccess.selectIdOnly(con,dbc);
      for(int ii=0; ii<storeIds.size(); ii++) {
        Integer storeIdI = (Integer) storeIds.get(ii);
        int storeId = storeIdI.intValue();
        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);
        if(pAccountIds!=null) {
            if(pForOrExceptFl) {
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pAccountIds);
            } else {
                dbc.addNotOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pAccountIds);
            }
        }
        String accountReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);
        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
        dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,accountReq);
        dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
        IdVector accountIds = BusEntityDataAccess.selectIdOnly(con,dbc);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFor = sdf.parse(sdf.format(pDateFor));
        for(int jj=0; jj<accountIds.size(); jj++) {
          Integer accountIdI = (Integer) accountIds.get(jj);
          int accountId = accountIdI.intValue();
          dbc = new DBCriteria();
          dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
          dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,accountId);
          String siteAccountReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);
          dbc = new DBCriteria();
          dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
          dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
          dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,siteAccountReq);
          String siteReq = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID,dbc);
          dbc = new DBCriteria();
          dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD,RefCodeNames.RECORD_STATUS_CD.VALID);
          dbc.addOneOf(OrderScheduleDataAccess.BUS_ENTITY_ID,siteReq);
          dbc.addLessOrEqual(OrderScheduleDataAccess.EFF_DATE,dateFor);
          dbc.addIsNullOr0(OrderScheduleDataAccess.WORK_ORDER_ID);
          IdVector siteIds = OrderScheduleDataAccess.selectIdOnly(con,OrderScheduleDataAccess.BUS_ENTITY_ID,dbc);
          if(siteIds.size()>0) {
            scheduleIds.addAll(getAutoOrderSchedules(con,siteIds,dateFor));
          }
        }

      }
    }
    catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    }
    finally {
      try {
         if(con!=null) con.close();
      }
      catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
    return scheduleIds;
  }

  //----------------------------------------------------------------------------------------
  private IdVector getSiteOrders(Connection pCon, int pSiteId, Date pDateFor)
  throws SQLException,RemoteException
  {
    IdVector scheduleIds = new IdVector();
    DBCriteria dbc;
    dbc = new DBCriteria();


dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD,RefCodeNames.RECORD_STATUS_CD.VALID);
    dbc.addEqualTo(OrderScheduleDataAccess.BUS_ENTITY_ID,pSiteId);
    dbc.addLessOrEqual(OrderScheduleDataAccess.EFF_DATE,pDateFor);
    dbc.addIsNullOr0(OrderScheduleDataAccess.WORK_ORDER_ID);
    OrderScheduleDataVector orderScheduleDV = OrderScheduleDataAccess.select(pCon,dbc);
    for(int ii=0; ii<orderScheduleDV.size(); ii++) {
      OrderScheduleData osD = (OrderScheduleData) orderScheduleDV.get(ii);
      int scheduleId = verifyOrderScheduleToProcess(pCon, osD, pDateFor);
      if(scheduleId>0) {
        scheduleIds.add(new Integer(scheduleId));
      }
    }
    return scheduleIds;

  }
  //----------------------------------------------------------------------------------------
  private IdVector getAutoOrderSchedules(Connection pCon, IdVector pSiteIds, Date pDateFor)
  throws SQLException,RemoteException
  {
    IdVector scheduleIds = new IdVector();
    DBCriteria dbc;
    dbc = new DBCriteria();


    dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD,RefCodeNames.RECORD_STATUS_CD.VALID);
    dbc.addOneOf(OrderScheduleDataAccess.BUS_ENTITY_ID,pSiteIds);
    dbc.addIsNullOr0(OrderScheduleDataAccess.WORK_ORDER_ID);
    dbc.addLessOrEqual(OrderScheduleDataAccess.EFF_DATE,pDateFor);
    OrderScheduleDataVector orderScheduleDV = OrderScheduleDataAccess.select(pCon,dbc);
    for(int ii=0; ii<orderScheduleDV.size(); ii++) {
      OrderScheduleData osD = (OrderScheduleData) orderScheduleDV.get(ii);
      int scheduleId = verifyOrderScheduleToProcess(pCon, osD, pDateFor);
      if(scheduleId>0) {
        scheduleIds.add(new Integer(scheduleId));
      }
    }
    return scheduleIds;

  }

  //----------------------------------------------------------------------------------------
  private int verifyOrderScheduleToProcess(Connection pCon, OrderScheduleData pOrderSchedule, Date pDateFor)
  throws SQLException, RemoteException
  {
    int orderScheduleId = 0;
    //Check date interval
    if(false==RefCodeNames.RECORD_STATUS_CD.VALID.equals(pOrderSchedule.getRecordStatusCd())) {
      return 0;
    }
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(pDateFor);
    GregorianCalendar todayGC = new GregorianCalendar(gc.get(GregorianCalendar.YEAR),gc.get(GregorianCalendar.MONTH),gc.get(GregorianCalendar.DATE));
    Date today = todayGC.getTime();
    int todayWeekDay = todayGC.get(GregorianCalendar.DAY_OF_WEEK);
    int todayMonthDay = todayGC.get(GregorianCalendar.DAY_OF_MONTH);
    int todayMonth = todayGC.get(GregorianCalendar.MONTH);
    int todayMonthWeekDayWeek = ((todayMonthDay-1)/7)+1;
    int cycle = pOrderSchedule.getCycle();

    Date effDate = pOrderSchedule.getEffDate();
    if(effDate.after(today)){
      return 0;
    }
    Date expDate = pOrderSchedule.getExpDate();
    if(expDate!=null && today.after(expDate)) {
      return 0;
    }
    String scheduleTypeS = pOrderSchedule.getOrderScheduleRuleCd();
    int scheduleType = 0;
    if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK.equals(scheduleTypeS)) {
      scheduleType = 1;
    }
    else if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH.equals(scheduleTypeS)) {
      scheduleType = 2;
    }
    else if(RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(scheduleTypeS)) {
      scheduleType = 3;
    }

    orderScheduleId = pOrderSchedule.getOrderScheduleId();
    //Get detail
    DBCriteria dbc;
    dbc = new DBCriteria();
    dbc.addEqualTo(OrderScheduleDetailDataAccess.RECORD_STATUS_CD,RefCodeNames.RECORD_STATUS_CD.VALID);
    dbc.addEqualTo(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_ID,orderScheduleId);
    OrderScheduleDetailDataVector orderScheduleDetailDV = OrderScheduleDetailDataAccess.select(pCon,dbc);
    //Analyze rule
    GregorianCalendar startDate = new GregorianCalendar();
    startDate.setTime(effDate);
    boolean applyFlag = true;
    if (cycle > 1) {
    	if (scheduleType == 1) {//week schedule
    		long diffW = todayGC.getTime().getTime() - startDate.getTime().getTime();
    		diffW /= 1000*3600*24*7;
    		if(diffW%cycle != 0) {
    			applyFlag = false;
    		}
    	} else if (scheduleType == 2) {//day month schedule
    		int diffM = (todayGC.get(GregorianCalendar.YEAR)-startDate.get(GregorianCalendar.YEAR))*12+
    				todayGC.get(GregorianCalendar.MONTH)-startDate.get(GregorianCalendar.MONTH);
    		if (diffM%cycle != 0)
    			applyFlag = false;
    	}
    }

    boolean placeOrder = false;
    for(int ii=0; ii<orderScheduleDetailDV.size(); ii++) {
      OrderScheduleDetailData osdD = (OrderScheduleDetailData) orderScheduleDetailDV.get(ii);
      String detailTypeS = osdD.getOrderScheduleDetailCd();
      if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(detailTypeS)) {
        gc = createCalendar(osdD.getDetail());
        if(gc!=null) {
          if(today.equals(gc.getTime())){
            return 0;
          }

        }
      }
      else if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailTypeS)) {
        gc = createCalendar(osdD.getDetail());
        if(gc!=null) {
          if(today.equals(gc.getTime())){
            placeOrder = true;
            break;
          }
        }
      }
      else if(RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ELEMENT.equals(detailTypeS)) {
        switch(scheduleType) {
          case 1: //week schedule
            int wd = 0;
            try{
              wd = Integer.parseInt(osdD.getDetail());
            }catch (NumberFormatException exc) {}
            if(wd ==todayWeekDay && applyFlag) {
              placeOrder = true;
            }
            break;
          case 2: //day of month
            int day = 0;
            try{
              day = Integer.parseInt(osdD.getDetail());
            }catch (NumberFormatException exc) {}
            if(day==todayMonthDay && applyFlag) {
              placeOrder = true;
            }
            else if(day==32 && applyFlag) {
              GregorianCalendar gc1 = (GregorianCalendar) todayGC.clone();
              gc1.add(GregorianCalendar.DATE,1);
              if(gc1.get(GregorianCalendar.MONTH)!=todayGC.get(GregorianCalendar.MONTH)) {
                placeOrder = true;
              }
            }
            break;
          case 3: //week of month

            String detS = osdD.getDetail();
            String subSt = null;

            if(applyFlag && detS!=null) {
              detS = detS.trim();
              int pos = detS.indexOf(':');
              subSt = detS.substring(pos+1, detS.length());
              int pos2 = subSt.indexOf(':');
              pos2 += 1+pos; //position of second : in detS

              if((pos>0 && (pos+1)<detS.length()) &&
                 (pos2>0 && (pos2+1)<detS.length())) {

                wd = 0;
                int week = 0;
                int month = 0;
                try {
                  wd = Integer.parseInt(detS.substring(0,pos));
                  week = Integer.parseInt(detS.substring(pos+1,pos2));
                  month = Integer.parseInt(detS.substring(pos2+1));
                }
                catch(NumberFormatException exc) {}
                if(wd==todayWeekDay && todayMonthWeekDayWeek==week && month==todayMonth) {
                  placeOrder = true;
                } else if(wd==todayWeekDay && week==5) {
                  GregorianCalendar gc2 = (GregorianCalendar) todayGC.clone();
                  gc2.add(GregorianCalendar.DATE,7);
                  if(gc2.get(GregorianCalendar.MONTH)!=todayGC.get(GregorianCalendar.MONTH)) {
                    placeOrder = true;
                  }
                }
              }
            }
            break;
        }
      }
    }
    if(!placeOrder) {
      return 0; //processOrderQueueElement(pCon, pOrderSchedule.getOrderScheduleId(), pDateFor, pUser);
    }
    return orderScheduleId;
  }
  //***************************************************************************
  /**
  * Processes order for order schedule without date checking. Logs result to clw_order_batch_log table
  * @param pOrderScheduleId the order schedule id
  * @param pDateFor the processing date
  * @param pUser the user logon name
  * @return eMail message to the custormer
  * @throws   RemoteException Required by EJB 1.0
  */
  public String placeAutoOrder(int pOrderScheduleId, Date pDateFor, String pUser)
  throws RemoteException
  {
    Connection con = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      Date dateFor = sdf.parse(sdf.format(pDateFor));
      con = getConnection();
      String emailMessage = processOrderQueueElement(con, pOrderScheduleId, dateFor, pUser);
      return emailMessage;
    }
    catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    }
    finally {
      try {
         if(con!=null) con.close();
      }
      catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
  }

  //-------------------------------------------------------------------------------
  private String processOrderQueueElement(Connection pCon, int pOrderScheduleId, Date pDateFor, String pUser)
  throws Exception
  {
    return processOrderQueueElement(pCon, pOrderScheduleId, pDateFor, pUser, null);
  }
  private String processOrderQueueElement(Connection pCon, int pOrderScheduleId, Date pDateFor, String pUser, AccCategoryToCostCenterView pCategToCostCenterView)
  throws Exception
  {
    DBCriteria dbc = new DBCriteria();
    OrderScheduleData  orderScheduleD = null;
    try {
      orderScheduleD = OrderScheduleDataAccess.select(pCon, pOrderScheduleId);
    } catch (DataNotFoundException exc) {
      return null;
    }
    dbc = new DBCriteria();
    dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_BATCH_TYPE_CD,RefCodeNames.ORDER_BATCH_TYPE_CD.SCHEDULE_ORDER);
    ArrayList success = new ArrayList();
    success.add(RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS);
    success.add(RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS_NO_EMAIL);
    dbc.addOneOf(OrderBatchLogDataAccess.ORDER_BATCH_STATUS_CD,success);
    dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_FOR_DATE,pDateFor);
    dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_SOURCE_ID,pOrderScheduleId);
    OrderBatchLogDataVector orderBatchDV = OrderBatchLogDataAccess.select(pCon,dbc);
    if(orderBatchDV.size()>0) {
      log.info("Found batch log entries, returning without doing anything.");
      return null;
    }
    if(RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER.equals(orderScheduleD.getOrderScheduleCd())) {
      String emailText =  placeOrder(pCon,orderScheduleD,pDateFor, pUser, pCategToCostCenterView);
      return emailText;
    }
    else if(RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY.equals(orderScheduleD.getOrderScheduleCd())) {
      sendReminder(pCon,orderScheduleD,pDateFor, pUser);
      return null;
    }
    return null;
  }
  //-------------------------------------------------------------------------------
	private String placeOrder(Connection pCon, OrderScheduleData pOrderSchedule, Date pDateFor, String pUser) throws Exception {
		return placeOrder(pCon, pOrderSchedule, pDateFor, pUser, null);
	}

	private String placeOrder(Connection pCon, OrderScheduleData pOrderSchedule, Date pDateFor, String pUser, AccCategoryToCostCenterView pCategToCostCenterView)
			throws Exception {
		int siteId = pOrderSchedule.getBusEntityId();
		int orderScheduleId = pOrderSchedule.getOrderScheduleId();
		int userId = pOrderSchedule.getUserId();
		int orderGuideId = pOrderSchedule.getOrderGuideId();

		// factory
		APIAccess factory = null;
		try {
			factory = APIAccess.getAPIAccess();
		} catch (Exception exc) {
			String mess = "^clw^No API access.^clw^";
			throw new Exception(mess);
		}
		// User
		User userEjb = null;
		try {
			userEjb = factory.getUserAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No user Ejb pointer^clw^";
			throw new Exception(mess);
		}

		UserData user = null;
		UserInfoData userInfo = null;
		try {
			user = userEjb.getUser(userId);
			userInfo = userEjb.getUserContact(userId);
		} catch (Exception exc) {
			String mess = exc.getMessage();
			throw new Exception(mess);
		}
		if (user == null || !RefCodeNames.USER_STATUS_CD.ACTIVE.equals(user.getUserStatusCd())) {
			String mess = "^clw^Could not find active user for user id: " + userId + "^clw^";
			throw new Exception(mess);
		}
//		String eMailAddress = userInfo.getEmailData().getEmailAddress();
//		if (eMailAddress == null || eMailAddress.trim().length() == 0) {
//			String mess = "^clw^User has no eMail adderss. User id: " + userId + "^clw^";
//			throw new Exception(mess);
//		}

		logDebug("user::" + user.getUserName());
		logDebug("user::" + user.getUserTypeCd());
		if (user.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.CUSTOMER)) {
			logDebug("checking date");
			Date userEffDate = user.getEffDate();
			Date userExpDate = user.getExpDate();
			String userStatusCd = user.getUserStatusCd();
			if (pDateFor.before(userEffDate) || (userExpDate != null && pDateFor.after(userExpDate))
					|| RefCodeNames.USER_STATUS_CD.INACTIVE.equals(userStatusCd)) {
				String mess = "^clw^User is not valid. User id: " + userId + "^clw^";
				throw new Exception(mess);
			}
		}

		String userRole = user.getUserRoleCd();
		boolean contractOnlyFlag = (userRole.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY) >= 0) ? true : false;
		boolean browseOnlyFlag = (userRole.indexOf(Constants.UserRole.BROWSE_ONLY) >= 0) ? true : false;
		if (browseOnlyFlag) {
			String mess = "^clw^User has no permission to make purchases. User id: " + userId + "^clw^";
			throw new Exception(mess);
		}
		boolean onAccountFlag = (userRole.indexOf(Constants.UserRole.ON_ACCOUNT) >= 0) ? true : false;
		if (!onAccountFlag) {
			String mess = "^clw^User is not on account. Can not accept credit cart for auto order. User id: " + userId + "^clw^";
			throw new Exception(mess);
		}
		boolean poNumRequiredFlag = (userRole.indexOf(Constants.UserRole.PO_NUM_REQUIRED) >= 0) ? true : false;
		if (!onAccountFlag) {
			String mess = "^clw^PO number required for the user. User id: " + userId + "^clw^";
			throw new Exception(mess);
		}

		// Site
		Site siteEjb = null;
		try {
			siteEjb = factory.getSiteAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No site Ejb pointer^clw^";
			throw new Exception(mess);
		}
		SiteData site = null;
		try {
			site = siteEjb.getSite(siteId, 0);
		} catch (Exception exc) {
			String mess = exc.getMessage();
			throw new Exception(mess);
		}
		if (site == null || !site.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)) {
			String mess = "^clw^Site it not valid for site id: " + siteId + "^clw^";
			throw new Exception(mess);
		}

		// Account
		int accountId = site.getAccountBusEntity().getBusEntityId();
		Account accountEjb = null;
		try {
			accountEjb = factory.getAccountAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No account Ejb pointer^clw^";
			throw new Exception(mess);
		}
		AccountData account = null;
		try {
			account = accountEjb.getAccount(accountId, 0);
		} catch (Exception exc) {
			String mess = exc.getMessage();
			throw new Exception(mess);
		}
		if (account == null || !account.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)) {
			String mess = "^clw^account it not valid for account id: " + accountId + "^clw^";
			throw new Exception(mess);
		}

		// Store
		Store storeEjb = null;
		try {
			storeEjb = factory.getStoreAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No store Ejb pointer^clw^";
			throw new Exception(mess);
		}
		int storeId = account.getStoreAssoc().getBusEntity2Id();
		StoreData store = null;
		try {
			store = storeEjb.getStore(storeId);
		} catch (Exception exc) {
			String mess = exc.getMessage();
			throw new Exception(mess);
		}
		if (store == null || !store.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)) {
			String mess = "^clw^Store it not valid for store id: " + storeId + "^clw^";
			throw new Exception(mess);
		}

		// OrderGuide
		OrderGuide orderGuideEjb = null;
		try {
			orderGuideEjb = factory.getOrderGuideAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No order guide Ejb pointer^clw^";
			throw new Exception(mess);
		}
		OrderGuideData orderGuide = null;
		try {
			orderGuide = orderGuideEjb.getOrderGuide(orderGuideId);
		} catch (Exception exc) {
			String mess = exc.getMessage();
			throw new Exception(mess);
		}

		// Catalog
		CatalogInformation catalogInfEjb = null;
		try {
			catalogInfEjb = factory.getCatalogInformationAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No shopping services Ejb pointer^clw^";
			throw new Exception(mess);
		}

		List<CatalogData> catalogV = null;
		catalogV = catalogInfEjb.getCatalogsCollectionByBusEntity(siteId);
		if (catalogV == null || catalogV.size() == 0) {
			catalogV = catalogInfEjb.getCatalogsCollectionByBusEntity(accountId);
			if (catalogV == null || catalogV.size() == 0) {
				String mess = "^clw^No catalog found^clw^";
				throw new Exception(mess);
			}
		}

		CatalogData catalog = null;
		log.info("placeOrder -> catalogDataVector = " + catalogV);
		for(CatalogData cat : catalogV){
			if(cat.getCatalogStatusCd().equals(RefCodeNames.CATALOG_STATUS_CD.ACTIVE))
				if(catalog == null) catalog = cat;
				else throw new Exception("^clw^Multiple active catalogs found^clw^");
		}
		
		if (catalog == null || !RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalog.getCatalogStatusCd())) {
			String mess = "^clw^No active catalog found^clw^";
			throw new Exception(mess);
		}

		int catalogId = catalog.getCatalogId();
		logDebug(">>>>>>>>>>>>catalogID: " + catalogId);

		// Contract
		ContractInformation contractInfEjb = null;
		try {
			contractInfEjb = factory.getContractInformationAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No shopping services Ejb pointer^clw^";
			throw new Exception(mess);
		}
		ContractDataVector contractV = null;
		contractV = contractInfEjb.getContractsCollectionByCatalog(catalogId);
		int contractId = 0;
		ContractData contract = null;
		if (contractV.size() >= 1) {
			int active = 0;
			for (int ele = 0; ele < contractV.size(); ele++) {
				ContractData temp = (ContractData) contractV.get(ele);
				logDebug("Anylizing contract: " + temp.getContractId());
				String status = temp.getContractStatusCd();
				if (status.equals(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE)) {
					contract = temp;
					logInfo("using contract: " + contract);
					active++;
				}
			}

			if (active != 1) {
				String mess = "^clw^" + contractV.size() + " contracts found^clw^";
				throw new Exception(mess);
			}

		}

		contractId = contract.getContractId();
		if (contractId == 0 && contractOnlyFlag) {
			String mess = "^clw^User must buy on contract only and no contract active found. " + "User id: " + userId + "^clw^";
			throw new Exception(mess);
		}

		int accountCatalogId = account.getAccountCatalogId();
		if (store.isAllowSpecialPermission() && accountCatalogId == 0) {
			String mess = "^clw^" + "There is no accaunt catalog found" + "^clw^";
			throw new Exception(mess);
		}

		ShoppingServices shoppingServEjb = null;
		try {
			shoppingServEjb = factory.getShoppingServicesAPI();
		} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
			String mess = "^clw^No shopping services Ejb pointer^clw^";
			throw new Exception(mess);
		}

		ShoppingCartItemDataVector shoppingCartDV;
		try {

			ShoppingItemRequest shoppingItemRequest = ShoppingDAO.createShoppingItemRequest(pCon, site.getSiteId(), accountCatalogId, catalogId, contractId,
					site.getPriceListRank1Id(), site.getPriceListRank2Id(), site.getProprietaryPriceListIds(), site.getAvailableTemplateOrderGuideIds(), site
							.getProductBundle(), user);

			shoppingCartDV = shoppingServEjb.getOrderGuidesItems(store.getStoreType().getValue(), site, pOrderSchedule.getOrderGuideId(), shoppingItemRequest,
					Constants.ORDER_BY_NAME, pCategToCostCenterView);

			if (shoppingCartDV.size() == 0) {
				String mess = "^clw^No items found for the order^clw^";
				throw new Exception(mess);
			}

			CustomerOrderRequestData orderReq = new CustomerOrderRequestData();
			// XXX - need to add the cost center
			// to an order which is automatically
			// placed.
			orderReq.setCostCenterId(-1);
			orderReq.setUserId(userId);

			UserData ud = null;
			try {
				ud = userEjb.getUser(userId);
			} catch (DataNotFoundException de) {
			}

			orderReq.setUserName(ud.getUserName());
			orderReq.setSiteId(siteId);
			orderReq.setAccountId(accountId);
			orderReq.setContractId(contractId);
			orderReq.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.SCHEDULER);

			AutoOrder autoOrderBean = null;
			try {
				autoOrderBean = factory.getAutoOrderAPI();
			} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
				String mess = "^clw^No AutoOrder API Access^clw^";
				throw new Exception(mess);
			}

			OrderScheduleJoin osj = null;
			String contactName = "";
			String contactPhone = "";
			String contactEmail = "";

			osj = autoOrderBean.getOrderSchedule(orderScheduleId);

			contactName = osj.getContactName();
			contactPhone = osj.getContactPhone();
			contactEmail = osj.getContactEmail();
			if (contactName != null && contactName.trim().length() > 0) {
				orderReq.setOrderContactName(contactName);
			}
			if (contactPhone != null && contactPhone.trim().length() > 0) {
				orderReq.setOrderTelephoneNumber(contactPhone);
			}
			if (contactEmail != null && contactEmail.trim().length() > 0) {
				orderReq.setOrderEmail(contactEmail);
			}

			for (int ii = 0; ii < shoppingCartDV.size(); ii++) {
				ShoppingCartItemData cartItem = (ShoppingCartItemData) shoppingCartDV.get(ii);
				int itemId = cartItem.getProduct().getItemData().getItemId();
				int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();
				int qty = cartItem.getQuantity();
				double price = cartItem.getPrice();
				if (qty != 0) {
					orderReq.addItemEntry(ii + 1, itemId, clw_skunum, qty, price, cartItem.getProduct().getUom(), cartItem.getProduct().getShortDesc(),
							cartItem.getProduct().getPack());
				}
			}

			IntegrationServices isvcEjb = null;
			try {
				isvcEjb = factory.getIntegrationServicesAPI();
			} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
				String mess = "^clw^No IntegrationServices API Access^clw^";
				throw new Exception(mess);
			}
			ProcessOrderResultData orderRes = null;

			ItemEntryVector itemEntries = orderReq.getEntriesCollection();
			if (itemEntries.size() == 0) {
				String mess = "^clw^Cannot process empty order^clw^";
				throw new Exception(mess);
			} else {
				orderRes = isvcEjb.processOrderRequest(orderReq);
			}
			Order orderEjb = null;
			try {
				orderEjb = factory.getOrderAPI();
			} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
				String mess = "^clw^No Order API Access^clw^";
				throw new Exception(mess);
			}

			if (orderRes == null) {
				String mess = "^clw^No responce for the order request^clw^";
				throw new Exception(mess);
			}
			OrderData theOrder = null;
			try {
				theOrder = orderEjb.getOrderStatus(orderRes.getOrderId());
			} catch (DataNotFoundException de) {
			}

			NumberFormat numberFormatter;
			String amountOut;
			// Split the locale into language and country.
			StringTokenizer st = new StringTokenizer(theOrder.getLocaleCd(), "_");
			String lang = "", country = "";
			for (int i = 0; st.hasMoreTokens() && i < 2; i++) {
				if (i == 0)
					lang = st.nextToken();
				if (i == 1)
					country = st.nextToken();
			}
			
			Locale locale = new Locale(lang, country);			
			numberFormatter = NumberFormat.getCurrencyInstance(new Locale(lang, country));
			amountOut = numberFormatter.format(theOrder.getTotalPrice());
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Object[] emailArgs = { amountOut, theOrder.getOrderNum(), dateFormat.format(theOrder.getOriginalOrderDate()),
					timeFormat.format(theOrder.getOriginalOrderTime()), site.getBusEntity().getShortDesc(), site.getSiteAddress().getAddress1(),
					site.getSiteAddress().getCity(), site.getSiteAddress().getStateProvinceCd(), site.getSiteAddress().getPostalCode(), amountOut };
			
			String emailMessage  = I18nUtil.getMessage(locale,
					"shop.auto.order.email", emailArgs);		
		
			if (emailMessage != null) {
				String message = ("Order was placed. Message: " + emailMessage);
				logSuccessRecord(pCon, RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS, siteId, orderScheduleId, theOrder.getOrderNum(), message, pDateFor, pUser);
			}
			return emailMessage;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
	}

/**
 * Sends email about successfully placed order
 * @param  pOrderScheduleId order schedule id
 * @param  pEmailMessage message to be sent
 * @param pUser the user logon name
 * @throws   RemoteException Required by EJB 1.0
 */
public void sendOrderNotification(int pOrderScheduleId,
                                  String pEmailMessage,
                                  String pUser)
throws RemoteException
{
     //factory
    APIAccess factory = null;
    Connection conn = null;
    try {
      conn = getConnection();
      factory = APIAccess.getAPIAccess();
      //User
      User userEjb = null;
      userEjb = factory.getUserAPI();

      OrderScheduleData orderSchD = OrderScheduleDataAccess.select(conn,pOrderScheduleId);
      int userId = orderSchD.getUserId();
      UserData user = null;
      UserInfoData userInfo = null;
      user = userEjb.getUser(userId);
      userInfo = userEjb.getUserContact(userId);
      String eMailAddress = userInfo.getEmailData().getEmailAddress();
      if (Utility.isSet(eMailAddress)) {
          EmailClient emailClientEjb = null;
          emailClientEjb = factory.getEmailClientAPI();

          String emailTitle = "Scheduled order was placed";
          try {
            int storeId = 0;
            int acctId = BusEntityDAO.getAccountForSite(conn,orderSchD.getBusEntityId());
            if(acctId > 0){
                storeId = BusEntityDAO.getStoreForAccount(conn,acctId);
            }
    //        String from = BusEntityDAO.getOutboundFromEmailAddress(conn,acctId,storeId);
            String from = factory.getAccountAPI().getDefaultEmail(acctId, storeId);
            emailClientEjb.send(eMailAddress,from,
                orderSchD.getCcEmail(),
                emailTitle,
                pEmailMessage,
                null,
                orderSchD.getOrderScheduleId(),
                RefCodeNames.EMAIL_TRACKING_CD.ORDER_SCHEDULE,
                0,
                userId,
                pUser);
          } catch(Exception exc){}
      }
    } catch (Exception exc) {
      throw new RemoteException(exc.getMessage());
    } finally {
      if(conn!=null) {
        try {
          conn.close();
        } catch (Exception exc1) {
          exc1.printStackTrace();

        }
      }
    }

}

  //-------------------------------------------------------------------------------
  private void sendReminder(Connection pCon, OrderScheduleData pOrderSchedule, Date pDateFor, String pUser)
  throws RemoteException, SQLException
  {
     int orderScheduleId = pOrderSchedule.getOrderScheduleId();
     int siteId = pOrderSchedule.getBusEntityId();
     int userId = pOrderSchedule.getUserId();
     int orderGuideId = pOrderSchedule.getOrderGuideId();

     //factory
     APIAccess factory = null;
     try {
         factory = APIAccess.getAPIAccess();
     }
     catch (Exception exc) {
       String mess = "^clw^No API access^clw^";
       throw new RemoteException(mess);
     }
     //User
     User userEjb = null;
     try {
       userEjb = factory.getUserAPI();
     } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
       String mess = "^clw^No user Ejb pointer^clw^";
       throw new RemoteException(mess);
     }
     UserData user = null;
     UserInfoData userInfo = null;
     try {
       user = userEjb.getUser(userId);
       userInfo = userEjb.getUserContact(userId);
     } catch(Exception exc) {
       String mess = exc.getMessage();
       throw new RemoteException(mess);
     }
     if(!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(user.getUserStatusCd())) {
       String mess = "^clw^User is not active. User id: "+userId+"^clw^";
       throw new RemoteException(mess);
     }
     String eMailAddress = userInfo.getEmailData().getEmailAddress();
     if(eMailAddress==null || eMailAddress.trim().length()==0) {
       //String mess = "^clw^User has no eMail adderss. User id: "+userId+"^clw^";
       //throw new RemoteException(mess);
       // ignore user without email address
       return;
     }

     Date userEffDate = user.getEffDate();
     Date userExpDate = user.getExpDate();
     String userStatusCd = user.getUserStatusCd();
     if(pDateFor.before(userEffDate) ||
        (userExpDate!=null && pDateFor.after(userExpDate)) ||
        RefCodeNames.USER_STATUS_CD.INACTIVE.equals(userStatusCd)) {
       String mess = "^clw^User is not legitimate. User id: "+userId+"^clw^";
       throw new RemoteException(mess);
     }

     //Site
     Site siteEjb = null;
     try {
       siteEjb = factory.getSiteAPI();
     } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
       String mess = "^clw^No site Ejb pointer^clw^";
       throw new RemoteException(mess);
     }
     SiteData site = null;
     try {
       site = siteEjb.getSite(siteId,0);
     } catch(Exception exc) {
       String mess = exc.getMessage();
       throw new RemoteException(mess);
     }
     //OrderGuide
     OrderGuide orderGuideEjb = null;
     try {
       orderGuideEjb = factory.getOrderGuideAPI();
     } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
       String mess = "^clw^No order guide Ejb pointer^clw^";
       throw new RemoteException(mess);
     }
     OrderGuideData orderGuide = null;
     try {
       orderGuide = orderGuideEjb.getOrderGuide(orderGuideId);
     } catch(Exception exc) {
       String mess = exc.getMessage();
       throw new RemoteException(mess);
     }

    String emailTitle = "Order scheduler reminder";
    String emailMessage = "Dear "+user.getFirstName()+" "+user.getLastName()+
                          ". Order scheduler would like to remind you to place "+
                          orderGuide.getShortDesc()+
                          " order on "+
                          date2string(pDateFor)+
                          " for the site "+site.getBusEntity().getShortDesc();
    try {
      EmailClient emailClientEjb = null;
      emailClientEjb = factory.getEmailClientAPI();
      int storeId = 0;
      int acctId = BusEntityDAO.getAccountForSite(pCon,pOrderSchedule.getBusEntityId());
      if(acctId > 0){
          storeId = BusEntityDAO.getStoreForAccount(pCon,acctId);
      }
//      String from = BusEntityDAO.getOutboundFromEmailAddress(pCon,acctId,storeId);
      String from = factory.getAccountAPI().getDefaultEmail(acctId, storeId);
      emailClientEjb.send(eMailAddress,from,
          pOrderSchedule.getCcEmail(),
          emailTitle,
          emailMessage,
          null,
          orderScheduleId,
          RefCodeNames.EMAIL_TRACKING_CD.ORDER_SCHEDULE,
          0,
          userId,
          pUser);
    } catch (Exception exc) {
      String mess = "^clw^Failed to send eMail. "+exc.getMessage()+"^clw^";
      throw new RemoteException(mess);
    }
    String message = ("Reminder was sent: "+emailMessage);
    logSuccessRecord(pCon, RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS, siteId, orderScheduleId, "", message, pDateFor, pUser);
    return;
  }
  //-------------------------------------------------------------------------------
  private void logSuccessRecord(Connection pCon, String type, int pSiteId, int pOrderScheduleId, String pOrderNum, String pMess, Date pDateFor, String pUser)
  throws SQLException
  {
    if(pMess.length()>2000) pMess = pMess.substring(0,2000);
    Date curDate = new Date(System.currentTimeMillis());
    OrderBatchLogData orderBatchLogD = OrderBatchLogData.createValue();
    orderBatchLogD.setOrderBatchLogId(0);
    orderBatchLogD.setBusEntityId(pSiteId);
    orderBatchLogD.setOrderSourceId(pOrderScheduleId);
    orderBatchLogD.setOrderBatchStatusCd(type);
    orderBatchLogD.setOrderBatchTypeCd(RefCodeNames.ORDER_BATCH_TYPE_CD.SCHEDULE_ORDER);
    orderBatchLogD.setOrderNum(pOrderNum);
    orderBatchLogD.setOrderForDate(pDateFor);
    orderBatchLogD.setOrderDate(curDate);
    orderBatchLogD.setMessage(pMess);
    orderBatchLogD.setAddBy(pUser);
    orderBatchLogD.setModBy(pUser);
    OrderBatchLogDataAccess.insert(pCon,orderBatchLogD);
   }
  //-------------------------------------------------------------------------------
  /**
  * Saves error message into CLW_ORDER_BACTCH_LOG table
  * @param pOrderScheduleId the order schedule id
  * @param pDateFor the processing date
  * @param pUser the user logon name
  * @throws   RemoteException Required by EJB 1.0
  */
  public void logErrorRecord(int pOrderScheduleId, String pMess, Date pDateFor, String pUser)
  throws RemoteException
  {

    Connection conn = null;
    try {
      conn = getConnection();
      OrderScheduleData orderSchD =
                          OrderScheduleDataAccess.select(conn,pOrderScheduleId);
      if(pMess.length()>2000) pMess = pMess.substring(0,2000);
      Date curDate = new Date();
      OrderBatchLogData orderBatchLogD = OrderBatchLogData.createValue();
      orderBatchLogD.setOrderBatchLogId(0);
      orderBatchLogD.setBusEntityId(orderSchD.getBusEntityId());
      orderBatchLogD.setOrderSourceId(pOrderScheduleId);
      orderBatchLogD.setOrderBatchStatusCd(RefCodeNames.ORDER_BATCH_STATUS_CD.FAILURE);
      orderBatchLogD.setOrderBatchTypeCd(RefCodeNames.ORDER_BATCH_TYPE_CD.SCHEDULE_ORDER);
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      Date dateFor = sdf.parse(sdf.format(pDateFor));
      orderBatchLogD.setOrderForDate(dateFor);
      orderBatchLogD.setOrderDate(curDate);
      String mess = pMess;
      if(mess==null) mess="";
      int ind1 = mess.indexOf("^clw^");
      if(ind1>0 && ind1+5<mess.length()){
        int ind2 = mess.indexOf("^clw^",ind1+5);
        if(ind2>0) {
          mess = mess.substring(ind1+5,ind2);
        }
      }
      orderBatchLogD.setMessage(mess);
      orderBatchLogD.setAddBy(pUser);
      orderBatchLogD.setModBy(pUser);
      OrderBatchLogDataAccess.insert(conn,orderBatchLogD);
    } catch (Exception exc) {
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    }finally {
      try {
         if(conn!=null) conn.close();
      }
      catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
  }

  //-------------------------------------------------------------------------------
  private GregorianCalendar createCalendar(String pDateS){
    if(pDateS==null) return null;
    char[] dateA = pDateS.toCharArray();
    int ii=0;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa=='/') break;
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==0 || ii==dateA.length) return null;
    String monthS = new String(dateA,0,ii);
    ii++;
    int ii1=ii;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa=='/') break;
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==ii1 || ii==dateA.length) return null;
    String dayS = new String(dateA,ii1,ii-ii1);
    ii++;
    ii1 = ii;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==ii1) return null;
    String yearS = new String(dateA,ii1,ii-ii1);
    int month = 0;
    int day = 0;
    int year = 0;
    try{
      month = Integer.parseInt(monthS);
      month--;
      day = Integer.parseInt(dayS);
      year = Integer.parseInt(yearS);
      if(year<100) year += 2000;
    } catch(NumberFormatException exc) {
      return null;
    }
    GregorianCalendar calendar = null;
    try{
      calendar = new GregorianCalendar(year,month,day);
    } catch(Exception exc) {
      return null;
    }
    return calendar;
  }
  //------------------------------------------------------------------------------
  private String date2string (Date pDate) {
	GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(pDate);
    int year = cal.get(GregorianCalendar.YEAR);
    String yearS = ""+year;
    if(year>=2000 && year<2100) yearS = yearS.substring(2,4);
    String ss = (cal.get(GregorianCalendar.MONTH)+1)+"/"+cal.get(GregorianCalendar.DAY_OF_MONTH)+"/"+yearS;
    return ss;
    }
  //***************************************************************************
  /**
  * Gets order log records for the date
  * @param pDateFor the processing date
  * @param pUser the user logon name
  * @return List of the log records
  * @throws   RemoteException Required by EJB 1.0
  */
  public OrderBatchLogDataVector getOrderBatchLog(Date pDateFor)
  throws RemoteException
  {
    OrderBatchLogDataVector orderBatchDV1 = new OrderBatchLogDataVector();
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_BATCH_TYPE_CD,RefCodeNames.ORDER_BATCH_TYPE_CD.SCHEDULE_ORDER);
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      Date dateFor = sdf.parse(sdf.format(pDateFor));
      dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_FOR_DATE,dateFor);
      dbc.addOrderBy(OrderBatchLogDataAccess.BUS_ENTITY_ID);
      dbc.addOrderBy(OrderBatchLogDataAccess.ORDER_SOURCE_ID);
      dbc.addOrderBy(OrderBatchLogDataAccess.ORDER_BATCH_LOG_ID);
      OrderBatchLogDataVector orderBatchDV = OrderBatchLogDataAccess.select(con,dbc);
      OrderBatchLogData prevOblD = null;
      for(int ii=0; ii<orderBatchDV.size(); ii++) {
        OrderBatchLogData oblD = (OrderBatchLogData) orderBatchDV.get(ii);
        if(ii==orderBatchDV.size()-1) {
          orderBatchDV1.add(oblD);
        }
        else {
          if(RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS.equals(oblD.getOrderBatchStatusCd())) {
            orderBatchDV1.add(oblD);
          } else if (RefCodeNames.ORDER_BATCH_STATUS_CD.FAILURE.equals(oblD.getOrderBatchStatusCd())) {
            if(ii!=0) {
              if(prevOblD.getOrderSourceId()!=oblD.getOrderSourceId() &&
                 !RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS.equals(prevOblD.getOrderBatchStatusCd())) {
                orderBatchDV1.add(prevOblD);
              }
            }
          }
        }
        prevOblD = oblD;
      }
    }
    catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    }
    finally {
      try {
         if(con!=null) con.close();
      }
      catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
    return orderBatchDV1;
  }


    /**
     * Processes order for  work order schedule without date checking.
     * Logs result to clw_order_batch_log table
     *
     * @param pOrderScheduleId the order schedule id
     * @param pDateFor         the processing date
     * @param pUser            the user logon name
     * @return eMail message to the customer
     * @throws RemoteException Required by EJB 1.0
     */
    public String placeAutoWorkOrder(int pOrderScheduleId, Date pDateFor, String pUser) throws RemoteException {
        Connection con = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date dateFor = sdf.parse(sdf.format(pDateFor));
            con = getConnection();
            return processWorkOrderQueueElement(con, pOrderScheduleId, dateFor, pUser);
        }
        catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        }
        finally {
            closeConnection(con);
        }
    }

    private String processWorkOrderQueueElement(Connection pCon, int pOrderScheduleId, Date pDateFor, String pUser) throws Exception {

        DBCriteria dbc;
        OrderScheduleData orderScheduleD;

        try {
            orderScheduleD = OrderScheduleDataAccess.select(pCon, pOrderScheduleId);
        } catch (DataNotFoundException exc) {
            return null;
        }

        dbc = new DBCriteria();

        dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_BATCH_TYPE_CD, RefCodeNames.ORDER_BATCH_TYPE_CD.SCHEDULE_ORDER);
        ArrayList success = new ArrayList();
        success.add(RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS);
        success.add(RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS_NO_EMAIL);
        dbc.addOneOf(OrderBatchLogDataAccess.ORDER_BATCH_STATUS_CD, success);
        dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_FOR_DATE, pDateFor);
        dbc.addEqualTo(OrderBatchLogDataAccess.ORDER_SOURCE_ID, pOrderScheduleId);

        OrderBatchLogDataVector orderBatchDV = OrderBatchLogDataAccess.select(pCon, dbc);

        if (!orderBatchDV.isEmpty()) {
            logInfo("Process cancelled for Schedule: " + pOrderScheduleId);
            return null;
        }

        if (RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER.equals(orderScheduleD.getOrderScheduleCd())) {
            return placeWorkOrder(pCon, orderScheduleD, pDateFor, pUser);
        } else if (RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY.equals(orderScheduleD.getOrderScheduleCd())) {
            sendWorkOrderReminder(pCon, orderScheduleD, pDateFor, pUser);
            return null;
        }
        return null;
    }

    private String placeWorkOrder(Connection pCon, OrderScheduleData orderSchedule, Date pDateFor, String pUser) throws Exception {

        int siteId = orderSchedule.getBusEntityId();
        int orderScheduleId = orderSchedule.getOrderScheduleId();
        int userId = orderSchedule.getUserId();
        int woTemplateId = orderSchedule.getWorkOrderId();

        if (woTemplateId <= 0) {
            throw new Exception("Template Work Order Id <= 0");
        }
        //factory
        APIAccess factory = null;
        try {
            factory = APIAccess.getAPIAccess();
        } catch (Exception exc) {
            String mess = "^clw^No API access.^clw^";
            throw new Exception(mess);
        }

        //User
        User userEjb = null;
        try {
            userEjb = factory.getUserAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No user Ejb pointer^clw^";
            throw new Exception(mess);
        }

        UserData user = null;
        UserInfoData userInfo = null;
        try {
            user = userEjb.getUser(userId);
            userInfo = userEjb.getUserContact(userId);
        } catch (Exception exc) {
            String mess = exc.getMessage();
            throw new Exception(mess);
        }

        if (user == null || !RefCodeNames.USER_STATUS_CD.ACTIVE.equals(user.getUserStatusCd())) {
            String mess = "^clw^Could not find active user for user id: " + userId + "^clw^";
            throw new Exception(mess);
        }

        String eMailAddress = userInfo.getEmailData().getEmailAddress();
        if (eMailAddress == null || eMailAddress.trim().length() == 0) {
            String mess = "^clw^User has no eMail address. User id: " + userId + "^clw^";
            throw new Exception(mess);
        }

        logInfo("placeWorkOrder => UserName: " + user.getUserName());
        logInfo("placeWorkOrder => UserType: " + user.getUserTypeCd());

        if (user.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.CUSTOMER)) {
            logInfo("placeWorkOrder => checking date");
            Date userEffDate = user.getEffDate();
            Date userExpDate = user.getExpDate();
            String userStatusCd = user.getUserStatusCd();
            if (pDateFor.before(userEffDate) ||
                    (userExpDate != null && pDateFor.after(userExpDate)) || RefCodeNames.USER_STATUS_CD.INACTIVE.equals(userStatusCd)) {
                String mess = "^clw^User is not valid. User id: " + userId + "^clw^";
                throw new Exception(mess);
            }
        }

        //Site
        Site siteEjb = null;
        try {
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No site Ejb pointer^clw^";
            throw new Exception(mess);
        }

        SiteData site = null;
        try {
            site = siteEjb.getSite(siteId, 0);
        } catch (Exception exc) {
            String mess = exc.getMessage();
            throw new Exception(mess);
        }

        if (site == null || !site.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)) {
            String mess = "^clw^Site it not valid for site id: " + siteId + "^clw^";
            throw new Exception(mess);
        }

        //Account
        int accountId = site.getAccountBusEntity().getBusEntityId();
        Account accountEjb = null;
        try {
            accountEjb = factory.getAccountAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No account Ejb pointer^clw^";
            throw new Exception(mess);
        }

        AccountData account = null;
        try {
            account = accountEjb.getAccount(accountId, 0);
        } catch (Exception exc) {
            String mess = exc.getMessage();
            throw new Exception(mess);
        }

        if (account == null || !account.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)) {
            String mess = "^clw^account it not valid for account id: " + accountId + "^clw^";
            throw new Exception(mess);
        }

        //Store
        Store storeEjb = null;
        try {
            storeEjb = factory.getStoreAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No store Ejb pointer^clw^";
            throw new Exception(mess);
        }

        int storeId = account.getStoreAssoc().getBusEntity2Id();
        StoreData store = null;
        try {
            store = storeEjb.getStore(storeId);
        } catch (Exception exc) {
            String mess = exc.getMessage();
            throw new Exception(mess);
        }

        if (store == null || !store.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)) {
            String mess = "^clw^Store it not valid for store id: " + storeId + "^clw^";
            throw new Exception(mess);
        }

        WorkOrder wrkEjb = null;
        try {
            wrkEjb = factory.getWorkOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No WorkOrder Ejb pointer^clw^";
            throw new Exception(mess);
        }

        WorkOrderSimpleSearchCriteria criteria = new WorkOrderSimpleSearchCriteria();

        criteria.setWorkOrderId(woTemplateId);
        IdVector siteIds = new IdVector();
        siteIds.add(new Integer(siteId));
        criteria.setSiteIds(siteIds);

        WorkOrderDetailViewVector woTemplate = wrkEjb.getWorkOrderDetailCollection(criteria);

        if (woTemplate == null || woTemplate.isEmpty() || woTemplate.size() > 1) {
            throw new Exception("^clw^Template Work Order it not valid for site: " + siteId + "^clw^");
        }

        WorkOrderDetailView newWoData = null;

        if (woTemplate.get(0) != null) {
              WorkOrderUtil woUtil = new WorkOrderUtil();
              newWoData = woUtil.clone((WorkOrderDetailView)woTemplate.get(0));
        }

        newWoData.getWorkOrder().setActionCd(RefCodeNames.WORK_ORDER_ACTION_CD.SERVICE);
        newWoData.getWorkOrder().setStatusCd(RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST);

        try {
            newWoData = wrkEjb.updateWorkOrderDetail(newWoData, user);
        } catch (RemoteException e1) {
            e1.printStackTrace();
            throw new RemoteException(e1.getMessage());
        }


        String message = ("Work Order was placed. Message: " + newWoData);
        logSuccessRecord(pCon, RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS, siteId, orderScheduleId,
                String.valueOf(newWoData.getWorkOrder().getWorkOrderId()), message, pDateFor, pUser);

        return null;

    }


    private void sendWorkOrderReminder(Connection pCon,
                                       OrderScheduleData pOrderSchedule,
                                       Date pDateFor,
                                       String pUser) throws RemoteException, SQLException {

        int orderScheduleId = pOrderSchedule.getOrderScheduleId();
        int siteId = pOrderSchedule.getBusEntityId();
        int userId = pOrderSchedule.getUserId();
        int workOrderId = pOrderSchedule.getWorkOrderId();

        //factory
        APIAccess factory = null;
        try {
            factory = APIAccess.getAPIAccess();
        } catch (Exception exc) {
            String mess = "^clw^No API access^clw^";
            throw new RemoteException(mess);
        }

        //User
        User userEjb = null;
        try {
            userEjb = factory.getUserAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No user Ejb pointer^clw^";
            throw new RemoteException(mess);
        }

        //Content
        Content contentEjb = null;
        try {
            contentEjb = factory.getContentAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No content Ejb pointer^clw^";
            throw new RemoteException(mess);
        }

        //WorkOrder
        WorkOrder workOrderEjb = null;
        try {
            workOrderEjb = factory.getWorkOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No work order Ejb pointer^clw^";
            throw new RemoteException(mess);
        }

        //Site
        Site siteEjb = null;
        try {
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No site Ejb pointer^clw^";
            throw new RemoteException(mess);
        }

        //Account
        Account accountEjb = null;
        try {
        	accountEjb = factory.getAccountAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            String mess = "^clw^No account Ejb pointer^clw^";
            throw new RemoteException(mess);
        }

        UserData user;
        UserInfoData userInfo;
        try {
            user = userEjb.getUser(userId);
            userInfo = userEjb.getUserContact(userId);
        } catch (Exception exc) {
            String mess = exc.getMessage();
            throw new RemoteException(mess);
        }

        if (!RefCodeNames.USER_STATUS_CD.ACTIVE.equals(user.getUserStatusCd())) {
            String mess = "^clw^User is not active. User id: " + userId + "^clw^";
            throw new RemoteException(mess);
        }

        String userFirstName = user.getFirstName();
        String userLastName = user.getLastName();

        String eMailAddress = userInfo.getEmailData().getEmailAddress();
        if (eMailAddress == null || eMailAddress.trim().length() == 0) {
            String mess = "^clw^User has no eMail address. User id: " + userId + "^clw^";
            throw new RemoteException(mess);
        }

        Date userEffDate = user.getEffDate();
        Date userExpDate = user.getExpDate();
        String userStatusCd = user.getUserStatusCd();
        if (pDateFor.before(userEffDate) ||
                (userExpDate != null && pDateFor.after(userExpDate)) ||
                RefCodeNames.USER_STATUS_CD.INACTIVE.equals(userStatusCd)) {
            String mess = "^clw^User is not legitimate. User id: " + userId + "^clw^";
            throw new RemoteException(mess);
        }

        SiteData site;
        try {
            site = siteEjb.getSite(siteId, 0);
        } catch (Exception exc) {
            String mess = exc.getMessage();
            throw new RemoteException(mess);
        }

        int acctId;
        try {
            acctId = BusEntityDAO.getAccountForSite(pCon, pOrderSchedule.getBusEntityId());
        } catch (SQLException e) {
            String mess = e.getMessage();
            throw new RemoteException(mess);
        }

        int storeId;
        try {
            storeId = BusEntityDAO.getStoreForAccount(pCon, acctId);
        } catch (SQLException e) {
            String mess = e.getMessage();
            throw new RemoteException(mess);
        }

        WorkOrderDetailView workOrder;
        try {
            WorkOrderSimpleSearchCriteria wrkCriteria = new WorkOrderSimpleSearchCriteria();
            wrkCriteria.setWorkOrderId(workOrderId);
            workOrder = workOrderEjb.getWorkOrderDetailView(workOrderId);
        } catch (Exception exc) {
            String mess = exc.getMessage();
            throw new RemoteException(mess);
        }

        String contactEmail = WorkOrderUtil.getPropertyValue(workOrder.getProperties(),
                RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO,
                RefCodeNames.WORK_ORDER_CONTACT.EMAIL);

        if (Utility.isSet(contactEmail)) {
            eMailAddress = contactEmail;
            userFirstName = WorkOrderUtil.getPropertyValue(workOrder.getProperties(),
                    RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO,
                    RefCodeNames.WORK_ORDER_CONTACT.FIRST_NAME);
            userLastName = WorkOrderUtil.getPropertyValue(workOrder.getProperties(),
                    RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO,
                    RefCodeNames.WORK_ORDER_CONTACT.LAST_NAME);
        } else {
            logInfo("User has no eMail contact address");
        }

        ByteArrayOutputStream pdfout = null;
        WorkOrderUtil woUtil = new WorkOrderUtil();
        try {
            Locale localeCd = new Locale(user.getPrefLocaleCd());
            pdfout = new ByteArrayOutputStream();
            woUtil.writeWoPdfToStream(workOrder, pdfout, localeCd);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String from = BusEntityDAO.getOutboundFromEmailAddress(pCon, acctId, storeId);
        String from = accountEjb.getDefaultEmail(acctId, storeId);
        String emailTitle = "Work Order scheduler reminder";
        String emailMessage = "Dear " + userFirstName + " " + userLastName +
                ".Work Order scheduler would like to remind you to place " +
                workOrder.getWorkOrder().getShortDesc() +
                " service on " +
                date2string(pDateFor) +
                " for the site " + site.getBusEntity().getShortDesc();

        EventEmailDataView eventEmail = new EventEmailDataView();

        eventEmail.setToAddress(eMailAddress);
        eventEmail.setCcAddress(pOrderSchedule.getCcEmail());
        eventEmail.setSubject(emailTitle);
        eventEmail.setText(emailMessage);
        eventEmail.setFromAddress(from);

        if (pdfout != null) {

            String name = "Work Order#" + workOrder.getWorkOrder().getWorkOrderId();
            String fileName = name + ".pdf";

            ArrayList attachArray = new ArrayList();
            byte[] outbytes = pdfout.toByteArray();
            FileAttach pdf = new FileAttach(fileName, outbytes, "application/pdf", outbytes.length);
            attachArray.add(pdf);

            WorkOrderContentViewVector woiContent = WorkOrderUtil.getContentOnly(workOrder.getWorkOrderItems());
            IdVector contentIds = WorkOrderUtil.getContentIds(woiContent);

            ContentDetailViewVector contentDetailVV = contentEjb.getContentDetailViewVector(contentIds);

            if (!contentDetailVV.isEmpty()) {
                Iterator it = contentDetailVV.iterator();
                while (it.hasNext()) {
                    ContentDetailView contentDetail = (ContentDetailView) it.next();
                    byte[] contentData = contentDetail.getData();
                    if (contentData.length > 0) {
                        FileAttach contentAtt = new FileAttach(contentDetail.getPath(),
                                contentData,
                                contentDetail.getContentTypeCd(),
                                contentData.length);
                        attachArray.add(contentAtt);
                    }
                }
            }

            eventEmail.setAttachments((FileAttach[]) attachArray.toArray(new FileAttach[attachArray.size()]));
        }

        try {
            eventEmail.setEmailStatusCd(Event.STATUS_READY);
            new ApplicationsEmailTool().createEvent(eventEmail);
        } catch (Exception e) {
            String mess = "^clw^Failed to send eMail. " + e.getMessage() + "^clw^";
            throw new RemoteException(mess);
        }

        String message = ("Reminder was sent: " + emailMessage);
        logSuccessRecord(pCon, RefCodeNames.ORDER_BATCH_STATUS_CD.SUCCESS, siteId, orderScheduleId, "", message, pDateFor, pUser);
    }


    //----------------------------------------------------------------------------------------
    private IdVector getAutoWorkOrderSchedules(Connection pCon, IdVector pSiteIds, Date pDateFor) throws SQLException, RemoteException {

        IdVector scheduleIds = new IdVector();

        DBCriteria dbc;
        dbc = new DBCriteria();

        dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
        dbc.addOneOf(OrderScheduleDataAccess.BUS_ENTITY_ID, pSiteIds);
        dbc.addLessOrEqual(OrderScheduleDataAccess.EFF_DATE, pDateFor);
        dbc.addIsNull(OrderScheduleDataAccess.ORDER_GUIDE_ID);
        dbc.addIsNotNull(OrderScheduleDataAccess.WORK_ORDER_ID);
        dbc.addGreaterThan(OrderScheduleDataAccess.WORK_ORDER_ID, 0);

        OrderScheduleDataVector orderScheduleDV = OrderScheduleDataAccess.select(pCon, dbc);

        for (int ii = 0; ii < orderScheduleDV.size(); ii++) {
            OrderScheduleData osD = (OrderScheduleData) orderScheduleDV.get(ii);
            int scheduleId = verifyOrderScheduleToProcess(pCon, osD, pDateFor);
            if (scheduleId > 0) {
                logInfo("getAutoWorkOrderSchedules site: " + osD.getBusEntityId());
                logInfo("getAutoWorkOrderSchedules id: " + osD.getOrderScheduleId());
                logInfo("getAutoWorkOrderSchedules work order id: " + osD.getWorkOrderId());
                scheduleIds.add(new Integer(scheduleId));
            }
        }
        return scheduleIds;
    }

    /**
     * Gets the list of Work Order schedules
     *
     * @param pBusEntityId the site id
     * @param workOrderIds optional work order identifiers
     * @return a list of OrderScheduleDataobjects
     * @throws RemoteException Required by EJB 1.0
     */
    public OrderScheduleDataVector getWorkOrderSchedules(int pBusEntityId, IdVector workOrderIds) throws RemoteException {

        Connection con = null;
        OrderScheduleDataVector orderSchedules = new OrderScheduleDataVector();

        try {

            DBCriteria dbc;
            con = getConnection();

            dbc = new DBCriteria();

            dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
            dbc.addEqualTo(OrderScheduleDataAccess.BUS_ENTITY_ID, pBusEntityId);
            dbc.addIsNull(OrderScheduleDataAccess.ORDER_GUIDE_ID);
            dbc.addGreaterThan(OrderScheduleDataAccess.WORK_ORDER_ID,0);
            dbc.addOrderBy(OrderScheduleDataAccess.ORDER_SCHEDULE_ID);

            if (!workOrderIds.isEmpty()) {
                if (workOrderIds.size() == 1) {
                    dbc.addEqualTo(OrderScheduleDataAccess.WORK_ORDER_ID, ((Integer) workOrderIds.get(0)).intValue());
                } else {
                    dbc.addOneOf(OrderScheduleDataAccess.WORK_ORDER_ID, workOrderIds);
                }
            } else {
                dbc.addGreaterThan(OrderScheduleDataAccess.WORK_ORDER_ID, 0);
            }

            orderSchedules = OrderScheduleDataAccess.select(con, dbc);

        } catch (NamingException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getWorkOrderSchedules() Naming Exception happened. " + exc.getMessage());
        } catch (SQLException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getWorkOrderSchedules() SQL Exception happened. " + exc.getMessage());
        } finally {
            closeConnection(con);

        }
        return orderSchedules;
    }

    /**
     * Gets the list of Order schedules
     *
     * @param siteIds  the site ids
     * @param pDateFor the schedule date
     * @return a ids list
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector getWorkOrderScheduleIds(IdVector siteIds, Date pDateFor) throws RemoteException {
        Connection con = null;
        IdVector resultIds = new IdVector();
        try {

            DBCriteria dbc;
            con = getConnection();

            IdVector orderScheduleIds = getAutoWorkOrderSchedules(con, siteIds, pDateFor);

            dbc = new DBCriteria();
            dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
            dbc.addOneOf(OrderScheduleDataAccess.ORDER_SCHEDULE_ID, orderScheduleIds);
            dbc.addIsNull(OrderScheduleDataAccess.ORDER_GUIDE_ID);
            dbc.addIsNotNull(OrderScheduleDataAccess.WORK_ORDER_ID);
            dbc.addGreaterThan(OrderScheduleDataAccess.WORK_ORDER_ID, 0);
            dbc.addOrderBy(OrderScheduleDataAccess.ORDER_SCHEDULE_ID);

            orderScheduleIds = OrderScheduleDataAccess.selectIdOnly(con, OrderScheduleDataAccess.ORDER_SCHEDULE_ID, dbc);
            resultIds.addAll(orderScheduleIds);

        } catch (NamingException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getWorkOrderScheduleIds Naming Exception happened. " + exc.getMessage());
        } catch (SQLException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getWorkOrderScheduleIds SQL Exception happened. " + exc.getMessage());
        } finally {
            closeConnection(con);
        }
        return resultIds;
    }


    /**
     * Gets the Work Order schedule
     *
     * @param pOrderScheduleId the order schedule id
     * @return an OrderScheduleJoin object
     * @throws RemoteException Required by EJB 1.0
     * @throws com.cleanwise.service.api.util.DataNotFoundException
     *                         if schedule not found
     */
    public OrderScheduleJoin getWorkOrderSchedule(int pOrderScheduleId) throws RemoteException, DataNotFoundException {

        Connection con = null;
        OrderScheduleJoin orderScheduleJ = null;

        try {

            DBCriteria dbc;
            con = getConnection();

            // gets schedule
            OrderScheduleData orderScheduleD = OrderScheduleDataAccess.select(con, pOrderScheduleId);

            //get user
            UserData userD;
            dbc = new DBCriteria();
            dbc.addEqualTo(UserDataAccess.USER_ID, orderScheduleD.getUserId());
            UserDataVector userDV = UserDataAccess.select(con, dbc);
            if (userDV.size() == 0) {
                throw new RemoteException("No user for the order schedule. Order scheduleId: " + pOrderScheduleId);
            }
            userD = (UserData) userDV.get(0);

            //get site
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, orderScheduleD.getBusEntityId());
            BusEntityDataVector siteBusEntityDV = BusEntityDataAccess.select(con, dbc);
            if (siteBusEntityDV.size() == 0) {
                throw new RemoteException("No site for the order schedule. Order scheduleId: " + pOrderScheduleId);
            }
            BusEntityData siteBusEntityD = (BusEntityData) siteBusEntityDV.get(0);

            //get account
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteBusEntityD.getBusEntityId());
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            String accountAssocReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, accountAssocReq);
            BusEntityDataVector accountBusEntityDV = BusEntityDataAccess.select(con, dbc);
            if (accountBusEntityDV.size() == 0) {
                throw new RemoteException("No account for the site. Site id: " + siteBusEntityD.getBusEntityId());
            }
            BusEntityData accountBusEntityD = (BusEntityData) accountBusEntityDV.get(0);

            //get order schedule details
            dbc = new DBCriteria();
            dbc.addEqualTo(OrderScheduleDetailDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);
            dbc.addEqualTo(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_ID, pOrderScheduleId);
            OrderScheduleDetailDataVector scheduleDetails = OrderScheduleDetailDataAccess.select(con, dbc);
            List elemL = new ArrayList();
            List alsoL = new ArrayList();
            List exceptL = new ArrayList();
            String contactName = "";
            String contactPhone = "";
            String contactEmail = "";
            String scheduleDetailCd = null;
            for (int ii = 0; ii < scheduleDetails.size(); ii++) {
                OrderScheduleDetailData osdD = (OrderScheduleDetailData) scheduleDetails.get(ii);
                String detailCd = osdD.getOrderScheduleDetailCd();
                String elemS = osdD.getDetail();
                if (elemS != null) {
                    elemS = elemS.trim();
                    if (elemS.length() > 0) {
                        if (RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ELEMENT.equals(detailCd)) {
                            elemL.add(elemS);
                        } else if (RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd)) {
                            alsoL.add(elemS);
                        } else if (RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(detailCd)) {
                            exceptL.add(elemS);
                        } else if (RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_NAME.equals(detailCd)) {
                            contactName = elemS;
                        } else if (RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_PHONE.equals(detailCd)) {
                            contactPhone = elemS;
                        } else if (RefCodeNames.ORDER_SCHEDULE_DETAIL_CD.CONTACT_EMAIL.equals(detailCd)) {
                            contactEmail = elemS;
                        } else {
                            throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Unknown order schedule detail type: " + detailCd);
                        }
                    }
                }
            }
            //Create join object
            orderScheduleJ = OrderScheduleJoin.createValue();
            orderScheduleJ.setUserFirstName(userD.getFirstName());
            orderScheduleJ.setUserLastName(userD.getLastName());
            orderScheduleJ.setUserId(userD.getUserId());
            orderScheduleJ.setSiteName(siteBusEntityD.getShortDesc());
            orderScheduleJ.setSiteId(siteBusEntityD.getBusEntityId());
            orderScheduleJ.setAccountName(accountBusEntityD.getShortDesc());
            orderScheduleJ.setAccountId(accountBusEntityD.getBusEntityId());
            orderScheduleJ.setWorkOrderId(orderScheduleD.getWorkOrderId());
            orderScheduleJ.setOrderScheduleId(orderScheduleD.getOrderScheduleId());
            orderScheduleJ.setEffDate(orderScheduleD.getEffDate());
            orderScheduleJ.setExpDate(orderScheduleD.getExpDate());
            orderScheduleJ.setCcEmail(orderScheduleD.getCcEmail());
            orderScheduleJ.setOrderScheduleCd(orderScheduleD.getOrderScheduleCd());
            orderScheduleJ.setCycle(orderScheduleD.getCycle());
            String orderScheduleRuleCd = orderScheduleD.getOrderScheduleRuleCd();
            orderScheduleJ.setOrderScheduleRuleCd(orderScheduleRuleCd);
            int elemSize = elemL.size();
            int[] elements = new int[elemSize];

            for (int ii = 0; ii < elemSize; ii++) {
                int stInd = 0;
                int stInd2 = 0;
                String ss = (String) elemL.get(ii);
                String subSt = "";

                if (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH.equals(orderScheduleRuleCd)) {

                    stInd = ss.indexOf(':');
                    int next = stInd + 1;
                    subSt = ss.substring(next, ss.length());

                    stInd2 = subSt.indexOf(':');

                    if (stInd < 0 || stInd2 < 0) {
                        throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: " + ss + " Order guide schedule id: " + orderScheduleD.getOrderScheduleId());
                    }
                    if (ii == 0) {
                        int weekDay = 0;
                        int week = 0;
                        try {
                            weekDay = Integer.parseInt(ss.substring(0, stInd));
                            week = Integer.parseInt(subSt.substring(0, stInd2));
                        } catch (Exception exc) {
                            throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: " + ss + " Order guide schedule id: " + orderScheduleD.getOrderScheduleId());
                        }
                        orderScheduleJ.setMonthWeekDay(weekDay);
                        orderScheduleJ.setMonthWeeks(week);
                    }
                    stInd2++;
                    //set the index to our latest, for data compatiblity issues
                    stInd = stInd2;
                    ss = subSt;
                    if (stInd2 >= ss.length()) {
                        throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: " + ss + " Order guide schedule id: " + orderScheduleD.getOrderScheduleId());
                    }
                } // end WEEK_MONTH
                try {
                    int elem = Integer.parseInt(ss.substring(stInd));
                    elements[ii] = elem;
                } catch (Exception exc) {
                    throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Wrong order schedule detail format: " + ss + " Order guide schedule id: " + orderScheduleD.getOrderScheduleId());
                }
            }
            orderScheduleJ.setElements(elements);
            DateFormat dateFormat = DateFormat.getInstance();
            int alsoSize = alsoL.size();
            Date[] also = new Date[alsoSize];
            for (int ii = 0; ii < alsoSize; ii++) {
                String ss = (String) alsoL.get(ii);
                GregorianCalendar dd = createCalendar(ss);
                if (dd == null) {
                    throw new RemoteException("Error. AutoOrderBean.createOrderScheduleView() Wrong order schedule detail date format: " + ss + " Order guide schedule id: " + orderScheduleD.getOrderScheduleId());
                }
                also[ii] = dd.getTime();
            }
            orderScheduleJ.setAlsoDates(also);

            orderScheduleJ.setContactName(contactName);
            orderScheduleJ.setContactPhone(contactPhone);
            orderScheduleJ.setContactEmail(contactEmail);


            int exceptSize = exceptL.size();
            Date[] except = new Date[exceptSize];
            for (int ii = 0; ii < exceptSize; ii++) {
                String ss = (String) exceptL.get(ii);
                GregorianCalendar dd = createCalendar(ss);
                if (dd == null) {
                    throw new RemoteException("Error. AutoOrderBean.createOrderScheduleView() Wrong order schedule detail date formart: " + ss + " Order guide schedule id: " + orderScheduleD.getOrderScheduleId());
                }
                except[ii] = dd.getTime();
            }
            orderScheduleJ.setExceptDates(except);
        }
        catch (NamingException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() Naming Exception happened. " + exc.getMessage());
        }
        catch (SQLException exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. AutoOrderBean.getOrderSchedule() SQL Exception happened. " + exc.getMessage());
        }
        finally {
            closeConnection(con);
        }
        return orderScheduleJ;
    }
  /*
    public String placeAutoOrdersForToday(Date pDateFor, String pUser)
        throws RemoteException
    {
        String res = "Placed 0 orders.";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Connection con = null;
        try {
          Date dateFor = sdf.parse(sdf.format(pDateFor));
          IdVector osch = getOrderSchedules(dateFor);
            con = getConnection();
            res = "\nProcessing : " + osch.size() +
                " auto orders for date: " + dateFor + "\n";

            for ( int i = 0; i < osch.size(); i++ ) {
                Integer id = (Integer)osch.get(i);
                processOrderQueueElement(con, id.intValue(), dateFor, pUser);
                res += "\n id: " + id;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logError("placeAutoOrdersForToday: error" + e);
            res += "\nException: " + e;
        }
        finally {
            closeConnection(con);
        }

        return res;
    }
*/

}
