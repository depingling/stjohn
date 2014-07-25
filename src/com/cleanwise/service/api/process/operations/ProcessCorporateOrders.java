package com.cleanwise.service.api.process.operations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CorporateOrder;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.CorpOrderCacheView;
import com.cleanwise.service.api.value.DeliveryScheduleView;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InventoryItemDataJoin;
import com.cleanwise.service.api.value.InventoryItemsData;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.utils.Constants;

public class ProcessCorporateOrders extends ApplicationServicesAPI {	
	
	public void processCorporateOrders(String begDate, String endDate, String siteIds, String scheduleIds) 
	throws Exception {
		Date upToDate = new Date(); //When the process started  or the endDate parameter;
		Date fromDate = null; //Null or begDate parameter;
		int errorCount = 0;
		Boolean specificDatesFl = false;
        UserData invUser;
		
		HashSet<Integer> siteIdList = new HashSet<Integer>();
		int poolSize = 1;
	    ExecutorService service = Executors.newFixedThreadPool(poolSize);
	    
	    String checkDupInvCartSql = "select bus_entity_id, max(order_guide_id) as max_og_id, min(order_guide_id) as min_og_id \r\n" +
	    		"from clw_order_guide where order_guide_type_cd = 'INVENTORY_CART' \r\n" +
	    		"group by bus_entity_id \r\n" +
	    		"having count(*) >1";
	    
	    String insertScheduleSql = "insert into dl_schedules \r\n" +
	    		"select \r\n" +
	    		"s.schedule_id, \r\n" +
	    		"s.short_desc as schedule_name, \r\n" +
	    		"sday.value as order_date, \r\n" +
	    		"stime.value as order_time, \r\n" +
	    		"sday.schedule_detail_cd, \r\n" +
	    		"sd.schedule_detail_id, \r\n" +
	    		"ss.short_desc as site_name," +
	    		"to_number(sd.value) site_id, \r\n" +
	    		"bea.bus_entity2_id account_id, \r\n" +
	    		"s.bus_entity_id store_id, schedule_rule_cd, \r\n" +
	    		"sprop.clw_value, sysdate \r\n" +
	    		"from clw_schedule_detail sd, clw_schedule s, clw_bus_entity_assoc bea,  \r\n" +
	    		"     clw_bus_entity ss, clw_schedule_detail sday, clw_schedule_detail stime, \r\n" +
	    		"     clw_property sprop \r\n" +
	    		"where s.schedule_id = ? \r\n" +
	    		"and schedule_type_cd = 'CORPORATE' \r\n" +
	    		"and sd.schedule_id = s.schedule_id \r\n" +
	    		"and ss.bus_entity_id = to_number(sd.value) \r\n" +
	    		"and sd.schedule_detail_cd = 'SITE_ID' \r\n" +
	    		"and ss.bus_entity_status_cd = 'ACTIVE' \r\n" +
	    		"and sprop.bus_entity_id(+) = ss.bus_entity_id \r\n" +
	    		"and sprop.short_desc(+) = 'ALLOW_CORPORATE_SCHED_ORDER' \r\n" +
	    		"and lower(sprop.clw_value(+)) = 'true' \r\n" +
	    		"and sday.schedule_id = s.schedule_id \r\n" +
	    		"and sday.schedule_detail_cd = 'ALSO_DATE' \r\n" +
	    		"and ss.bus_entity_id = bea.bus_entity1_id  \r\n" +
	    		"and bea.bus_entity_assoc_cd= 'SITE OF ACCOUNT' \r\n" +
	    		//"and to_date(sday.value,'mm/dd/yyyy') = trunc(sysdate) \r\n" +
	    		"and sday.value = ? \r\n" +
	    		"and stime.schedule_id(+) = s.schedule_id \r\n" +
	    		"and stime.schedule_detail_cd(+) = 'CUTOFF_TIME'";
	    
	    String insertInvCartSql = "insert into dl_inv_cart \r\n" +
	    		"select tm.order_date, tm.order_time, sch_site, order_guide_id, catalog_id, og_item_id,  quantity, sysdate \r\n" +
	    		"from   \r\n" +
	    		"( \r\n" +
	    		"select to_number(value) as sch_site, schedule_id \r\n" +
	    		"from clw_schedule_detail sd \r\n" +
	    		"where schedule_detail_cd = 'SITE_ID' \r\n" +
	    		")sch, \r\n" +
	    		"( \r\n" +
	    		"select distinct schedule_id, order_date, order_time from dl_schedules \r\n" +
	    		") tm, \r\n" +
	    		"( \r\n" +
	    		"select c.catalog_id, ca.bus_entity_id as cat_site_id, cs.item_id as cat_item_id \r\n" +
	    		"from clw_catalog_assoc ca, clw_catalog c, clw_catalog_structure cs \r\n" +
	    		"where 1=1 \r\n" +
	    		"and ca.catalog_id = c.catalog_id \r\n" +
	    		"and c.catalog_type_cd = 'SHOPPING' \r\n" +
	    		"and c.catalog_status_cd = 'ACTIVE' \r\n" +
	    		"and c.catalog_id = cs.catalog_id \r\n" +
	    		") cat, \r\n" +
	    		"( \r\n" +
	    		"select og.order_guide_id, og.bus_entity_id as og_site_id, ogs.item_id  as og_item_id, ogs.quantity \r\n" +
	    		"from clw_order_guide og, clw_order_guide_structure ogs \r\n" +
	    		"where og.order_guide_id = ogs.order_guide_id (+) \r\n" +
	    		"and og.user_id  is null \r\n" +
	    		"and og.order_guide_type_cd = 'INVENTORY_CART' \r\n" +
	    		") og \r\n" +
	    		"where sch.schedule_id = ? \r\n" +
	    		"and sch.schedule_id = tm.schedule_id \r\n" +
	    		"and tm.order_date = ? \r\n" +
	    		"and tm.order_time = ? \r\n" +
	    		"and og_site_id = cat_site_id(+) \r\n" +
	    		"and og_item_id = cat_item_id(+) \r\n" +
	    		"and og_site_id(+) = sch.sch_site";
	    		
		logInfo("@@@@@@@ process corportate orders START @@@@@@@@@ " + new Date());
		Connection conn = null;

		try {
			APIAccess factory = new APIAccess();
			conn = this.getConnection();
			Statement stmt = conn.createStatement();
			logInfo("Sql: " + checkDupInvCartSql);
			ResultSet rs = stmt.executeQuery(checkDupInvCartSql);
			if (rs.next()){
				throw new Exception("Some Inventory cart are not unique");
			}
			
			logInfo("Sql: " + insertScheduleSql);
			PreparedStatement pstmt1 = conn.prepareStatement(insertScheduleSql);
			logInfo("Sql: " + insertInvCartSql);
			PreparedStatement pstmt2 = conn.prepareStatement(insertInvCartSql);

			PropertyService propertyEjb = factory.getPropertyServiceAPI();
			Site siteEjb = factory.getSiteAPI();
			Account accountEjb = factory.getAccountAPI();
			User userEjb = factory.getUserAPI();
			CorporateOrder corporateOrderEjb = factory.getCorporateOrderAPI();
            invUser = userEjb.getUserByName("inv_order",0);

			//Get date range
			if (Utility.isSet(begDate)) {				
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmm");
				try {
					fromDate = sdf1.parse(begDate);
				} catch (Exception e) {
					throw new Exception("The begDate format is invalid. It must use format yyyyMMddHHmm begDate="+begDate);
				}
				if(Utility.isSet(endDate)) {
					try {
						upToDate = sdf1.parse(endDate);
					} catch (Exception e) {
						throw new Exception("The endDate format is invalid. It must use format yyyyMMddHHmm endDate= "+endDate);
					}
				} else {
					throw new Exception("endDate is requred if begDate present");
				}
				specificDatesFl = true;
			} /* else {
				logInfo("No dates specified. Running normal date logic");
				String lastStartTime = null;
				try {
					lastStartTime = propertyEjb.getProperty(Constants.CORP_SCHED_PROCESS_START_TIME);
				} catch (Exception e) {
					logError(e.getMessage());
					throw new Exception("CORP_SCHED_PROCESS_START_TIME property was not found");
				}	
				try {
					SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					runDate = sdf2.parse(lastStartTime);
				} catch (Exception e) {
					logError(e.getMessage());
					String errorMess = "The CORP_SCHED_PROCESS_START_TIME property has invalid format. "+
							"The format should be MM/dd/yyyy HH:mm:ss. The found value is: "+lastStartTime;
					throw new Exception(errorMess);
				}				
			} */

			IdVector scheduleIdV = new IdVector();
			if( Utility.isSet(scheduleIds)) {
				StringTokenizer st = new StringTokenizer(scheduleIds, ",");
				int schedId = 0;
				String schedIdS = null;
				try {
					while (st.hasMoreElements()) {
						schedIdS = (String) st.nextElement();
						if(Utility.isSet(schedIdS)) {
							schedId = Integer.parseInt(schedIdS.trim());
							scheduleIdV.add(new Integer(schedId));
						}
					}
				} catch (NumberFormatException e) {
					String errorMess = "Impossible to parse schedule id(s): " + schedIdS;
					logInfo(errorMess);
					throw new Exception(errorMess);
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			if(scheduleIdV.size()>0) {
				logInfo("Requested schedules: "+IdVector.toCommaString(scheduleIdV));
			}

            //Get schedules
			List<DeliveryScheduleView> schedules = corporateOrderEjb.getCorpSchedules(fromDate, upToDate, scheduleIdV);
			if(schedules.size()==0) {
				logInfo("No corporate schedules found for the dates");
				return; //Nothing to do
			}
			
			// gets ids
			if (Utility.isSet(siteIds)) {
				StringTokenizer st = new StringTokenizer(siteIds, ",");
				try {
					while (st.hasMoreElements()) {
						siteIdList.add(new Integer((String) st.nextElement()));
					}
				} catch (NumberFormatException e) {
					throw new Exception("it's impossible to parse a site ids," +
							" the reason is \"Not correct input string :  [" + siteIds + "]\"" +
					" Input string must use format <siteId1,siteId2,siteId3...>");
				}
			}
			for(DeliveryScheduleView schVw: schedules) {
				int schId = schVw.getScheduleId();				
				Date cutoffDate = schVw.getNextDelivery();
				Date lastProcessedDt = schVw.getLastProcessedDt();

				String logMess = "Processing schedule "+schVw.getScheduleName()+
				                 " ("+schId+") "+
								 " The cutoff date: "+sdf.format(schVw.getNextDelivery()) +
								 " Last processed date: "+
								 (lastProcessedDt!=null?sdf.format(lastProcessedDt):" Undefined");								 
				logInfo(logMess);
				Date schStartDate = new Date();
				String scheduledDate = sdf.format(cutoffDate).substring(0,10);
				String scheduledTime = sdf.format(cutoffDate).substring(11);
				// insert temp table dl_schedules
				pstmt1.setInt(1, schId);
				pstmt1.setString(2, scheduledDate);
				pstmt1.execute();
				// insert temp table dl_inv_cart
				pstmt2.setInt(1, schId);
				pstmt2.setString(2, scheduledDate);
				pstmt2.setString(3, scheduledTime);
				pstmt2.execute();
				
				//Get account - contact map for the schedule
				HashMap<Integer,HashMap> acctCatalogSiteHM = 
					corporateOrderEjb.getAccountCatalogSiteMap(schId);
				
				if(siteIdList.size()>0) { //Filter out not mentioned sites
					acctCatalogSiteHM = filterRequestedSites(acctCatalogSiteHM,siteIdList);
				}	
                if(acctCatalogSiteHM==null) {
					continue;
                }				
                int siteQty = calcSiteQty(acctCatalogSiteHM);			
				int count = 0;

				Set<Integer> accounts = acctCatalogSiteHM.keySet();				
				for(Integer accountIdI: accounts) {
				    //Account Variables to Set
					AccountData accountD = null;
					Boolean usePhysicalInventoryCart = false;
					Integer storeId = 0;
					String storeType = null;		
					BigDecimal autoOrderFactor = new BigDecimal(0);					
					FiscalCalenderView fiscalCalendar = null;
					Integer inventoryPeriod = null;
					HashMap accountInventoryItems;
 
                    //Get variable values
					//Account
					accountD = accountEjb.getAccount(accountIdI.intValue(),0); //Do not care about the store
					
					// Cost Center Mapping
					AccCategoryToCostCenterView accCategToCostCenterVw =
						accountEjb.getCategoryToCostCenterViewByAccount(accountIdI.intValue());		

					//Inventory Items
					accountInventoryItems = new HashMap();
					List invItems = accountD.getInventoryItemsData();
					for(Iterator iter=invItems.iterator(); iter.hasNext();) {
					    InventoryItemDataJoin iidj = (InventoryItemDataJoin) iter.next();
						InventoryItemsData iiD = iidj.getInventoryItemsData();
						accountInventoryItems.put(iiD.getItemId(),iiD);
					}
					
					//Store Id
					BusEntityAssocData storeAssoc = accountD.getStoreAssoc();
					storeId = storeAssoc.getBusEntity2Id();
					
					//Store Type
					storeType = propertyEjb.getBusEntityProperty(storeId, 
								RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);				

					//Physical Cart			
					usePhysicalInventoryCart = false;
					try {
						String physInvS = propertyEjb.getBusEntityProperty(accountIdI.intValue(), 
								RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
						usePhysicalInventoryCart = Utility.isTrue(physInvS);
					} catch (DataNotFoundException exc) {}

					//Auto Order Factor
					String autoOrderFactorS = null;
					try {
						autoOrderFactorS = propertyEjb.getBusEntityProperty(accountIdI.intValue(), 
								RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR);
						if(Utility.isSet(autoOrderFactorS)) {
							double autoOrderFactorDb = Double.parseDouble(autoOrderFactorS);
							autoOrderFactor = new BigDecimal(autoOrderFactorDb);
							autoOrderFactor = autoOrderFactor.setScale(2,BigDecimal.ROUND_HALF_UP);							
						}								
					} catch (DataNotFoundException exc) {
					} catch (NumberFormatException exc) {
						String errorMess = "Invalid Auto Order Factor format for account "+
							accountIdI+" Auto Order Factor found = "+ autoOrderFactorS;
						logInfo(errorMess);
						continue;									
					}
					
                    //Fiscal calendar
					fiscalCalendar = accountEjb.getCurrentFiscalCalenderV(accountIdI.intValue());
					
					//Current fiscal period
                    FiscalCalendarInfo fci = new FiscalCalendarInfo(fiscalCalendar);
				    HashMap calendarPeriodHM = fci.getBudgetPeriodsAsHashMap();
					Collection periods = calendarPeriodHM.values();
					Date prevDate = null;
					int periodNum = 0;
					for(Iterator iter=periods.iterator(); iter.hasNext();) {
					//check only begin date because the end date may be incorrect
						FiscalCalendarInfo.BudgetPeriodInfo period = (FiscalCalendarInfo.BudgetPeriodInfo) iter.next();
						Date periodStart = period.getStartDate();
						if(periodStart!=null) {
							if(!cutoffDate.before(periodStart)) {
								if(prevDate==null || !periodStart.before(prevDate)) {
									periodNum = period.getBudgetPeriod();
									prevDate = periodStart;									
								}
							}
						}
					}
					inventoryPeriod = periodNum;	
					
	    
					HashMap<Integer,List> catalogSiteHM = (HashMap<Integer,List>) acctCatalogSiteHM.get(accountIdI);
					Set<Integer> catalogs = catalogSiteHM.keySet();

					
					for(Integer catalogIdI: catalogs) {
					    //Catalog variables to set
						Integer contractIdI;//????????????????????????????????????????????
						HashMap catalogProducts = null;
						//Set values
						catalogProducts = 
						   corporateOrderEjb.getContractProducts(catalogIdI.intValue(),accCategToCostCenterVw);
						
						
						List siteIdV = catalogSiteHM.get(catalogIdI);		
	                    HashMap<Integer,Integer> orderGuideIdHM = 
												corporateOrderEjb.getInventoryOGIds(siteIdV);

						Set<Future<String>> set = new LinkedHashSet<Future<String>>();
						PairViewVector failedMessages = new PairViewVector();

						for(Iterator iter=siteIdV.iterator(); iter.hasNext();) {
							Integer siteIdI = (Integer) iter.next();
							//SiteData siteData = siteEjb.getSite(siteIdI.intValue());
							Integer orderGuideIdI = orderGuideIdHM.get(siteIdI);
							if(orderGuideIdI==null) orderGuideIdI = 0;
                            CorpOrderCacheView cocVw = new CorpOrderCacheView();

							cocVw.setSpecificDatesFl(specificDatesFl);
							cocVw.setCutoffDate(cutoffDate);
							cocVw.setInventoryPeriod(inventoryPeriod);
							cocVw.setUsePhysicalInventoryCart(usePhysicalInventoryCart);
							cocVw.setStoreId(storeId);
							cocVw.setStoreType(storeType); 
							cocVw.setAccount(accountD);
							cocVw.setFiscalCalendar(fiscalCalendar);
							cocVw.setAutoOrderFactor(autoOrderFactor);
							cocVw.setSiteId(siteIdI);
							cocVw.setOrderGuideId(orderGuideIdI);
							cocVw.setUser(invUser); 
							cocVw.setAccountInventoryItems(accountInventoryItems);
							cocVw.setCatalogProducts(catalogProducts);
							Callable<String> callable = new PlaceCorporateOrderNew(cocVw);

							Future<String> future = service.submit(callable);
							set.add(future);				
						} //End of Site Loop
						int ii = 0;
						for (Future<String> future : set) {
							int siteId = (Integer)siteIdV.get(ii);
							try {
								String res = future.get();
								logInfo(siteIdV.size()
										+ " -> " + (ii+1)
										+ " processing result: "
										+ res + "\n");
								count++;
								String tracMess = "Processing "+count+" site of "+siteQty+ " of schedule "+schVw.getScheduleName();
								logInfo(tracMess);

								ii++;
							} catch (Exception e) {
								//e.printStackTrace();
								errorCount++;
								failedMessages.add(new PairView(new Integer(siteId), "siteId: " + siteId + "- " + e.getMessage()));
								logInfo(siteIdV.size()
										+ " -> " + (ii+1)
										+ " processing result: Error - placeInventoryOrder: pSiteId=" + siteId + " - "
										+ e.getMessage() +  "\n");
							}
						}

						logInfo("result: processed "
								+ (siteIdV.size()-errorCount) + " of " + siteIdList.size() + ".Error count: " + errorCount);			
						if (errorCount > 0) {
							String mess = "process_corporate_orders problems.\n";
							mess += failedMessages.toString();
							logInfo(mess);
						}

					} //End of Catalog Loop					
				} //End of Account Loop

				Callable<String> callable = new UpdateLastProcessedDate(schId, schStartDate);
				Future<String> future = service.submit(callable);
				future.get();

			} //End of Schedule Loop

			if (errorCount > 0) {
				throw new Exception("Some Orders Failed");
			}
			if(!specificDatesFl) {
				Callable<String> callable = new UpdateTimeProperties(upToDate);
				Future<String> future = service.submit(callable);
				future.get();
			}
			
			
		} catch (Exception e) {
                        e.printStackTrace();
			throw new Exception("processScheduledOrders:: caught error: "+e.getMessage());            
		} finally {
			service.shutdownNow();
			closeConnection(conn);
		}

		logInfo("@@@@@@@ process scheduled orders DONE @@@@@@@@@ " + new Date());
	}
	private int calcSiteQty(HashMap pAcctCatalogSiteHM) {
		int qty =0;
	    Collection catalogHMColl = pAcctCatalogSiteHM.values();
		for(Iterator iter=catalogHMColl.iterator(); iter.hasNext();) {
			HashMap catalogHM = (HashMap) iter.next();
			Collection siteVColl = catalogHM.values();
			for(Iterator iter1=siteVColl.iterator(); iter1.hasNext();) {
				List sitesL = (List) iter1.next();
				qty += sitesL.size();
			}	
			
		}
		return qty;
	}
               				
	
	private HashMap<Integer,HashMap>    
				filterRequestedSites(HashMap<Integer,HashMap> acctCatalogSiteHM,
									 HashSet filterSiteIdV ) {
		HashMap<Integer,HashMap> filteredAcctCatalogSiteHM  = null;
		Set<Integer> accounts = acctCatalogSiteHM.keySet();
		for(Integer accountIdI: accounts) {
			HashMap<Integer,List> catalogSiteHM = (HashMap<Integer,List>) acctCatalogSiteHM.get(accountIdI);
			HashMap<Integer,List> filteredCatalogSiteHM = null;
			Set<Integer> catalogs = catalogSiteHM.keySet();
			for(Integer catalogIdI: catalogs) {
				List siteIdV = catalogSiteHM.get(catalogIdI);
				siteIdV = filterRequestedSites(siteIdV,filterSiteIdV);
				if(siteIdV.size()>0) {
					if(filteredCatalogSiteHM==null) {
						filteredCatalogSiteHM = new HashMap<Integer,List>();
						if(filteredAcctCatalogSiteHM==null) {
							filteredAcctCatalogSiteHM = new HashMap<Integer,HashMap>();									
						}
						filteredAcctCatalogSiteHM.put(accountIdI,filteredCatalogSiteHM);
					}
					filteredCatalogSiteHM.put(catalogIdI,siteIdV);
				} 
			} //End of Catalog Loop					
		} //End of Account Loop

		return filteredAcctCatalogSiteHM;
	} 
			
	
	private List filterRequestedSites(List siteIdV, HashSet requestedSiteIds) {
		ArrayList<Integer> filteredSiteIdV = new ArrayList<Integer>();
		for(Iterator iter=siteIdV.iterator();iter.hasNext();) {
			Integer siteIdI = (Integer) iter.next();
			if(requestedSiteIds.contains(siteIdI))filteredSiteIdV.add(siteIdI);  
		}
		return filteredSiteIdV;
    }
		
	}

class PlaceCorporateOrderNew implements Callable<String> {

	private CorpOrderCacheView mCorpOrderCache;	

	PlaceCorporateOrderNew(CorpOrderCacheView pCorpOrderCache){
		mCorpOrderCache = pCorpOrderCache;
	}
	
    public String call() throws Exception {
    	String result = null;
    	try {
			APIAccess factory = new APIAccess();

			CorporateOrder corpOrderEjb = factory.getCorporateOrderAPI();
			result = corpOrderEjb.placeCorporateOrder(mCorpOrderCache);
        } 
        catch(final Exception ex) 
        {
            ex.printStackTrace();
            throw ex;
        } 
		return result;
    } 
	
}

class UpdateLastProcessedDate implements Callable<String> {
	Date schStartDate = null;
	int scheduleId = 0;

	public UpdateLastProcessedDate(int scheduleId, Date schStartDate) {
		this.schStartDate = schStartDate;
		this.scheduleId = scheduleId;
	}

    public String call() throws Exception {
    	APIAccess factory = new APIAccess();
		CorporateOrder corpOrderEjb = factory.getCorporateOrderAPI();
		corpOrderEjb.updateLastProccessedDt(scheduleId, schStartDate);
		return null;
    }
}

class UpdateTimeProperties implements Callable<String> {
	Date startDate = null;

	public UpdateTimeProperties(Date startDate) {
		this.startDate = startDate;
	}

    public String call() throws Exception {
    	APIAccess factory = new APIAccess();
		PropertyService propertyEjb = factory.getPropertyServiceAPI();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		propertyEjb.setProperty(Constants.CORP_SCHED_PROCESS_START_TIME,sdf.format(startDate));
		propertyEjb.setProperty(Constants.CORP_SCHED_PROCESS_FINISH_TIME,sdf.format(new Date()));
		return null;
    }
}

	
