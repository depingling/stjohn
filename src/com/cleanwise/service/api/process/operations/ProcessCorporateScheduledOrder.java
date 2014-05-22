package com.cleanwise.service.api.process.operations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.util.Utility;

public class ProcessCorporateScheduledOrder extends ApplicationServicesAPI {	
	/**
	 * 
	 * This method will pass in 3 optional parameters for process corporate scheduled order. 
	 * 	1.  If no parameter passed in, will fetch last process time(CORP_SCHED_PROCESS_START_TIME) 
	 * 		for start date and process all the scheduled order for all sites of all stores 
	 * 		with cutoff time between start date and process time.
	 *  2.  If start date and end date both passed in, will process orders scheduled cutoff date 
	 *  	between start and end date.
	 *  3.  If either start date or end date passed in, both dates are required
	 * It is here as a reminder that there are rows in the CLW_TASK table that make reference to this class, 
	 * so we cannot change this method without updating the templates.xml file used to populate 
	 * the CLW_TASK table.
	 * @param begDate		 
	 * @param endDate
	 * @param siteIdsStr	list site ids where delimiter is comma
	 */
	public void processScheduledOrders(String begDate, String endDate, String siteIds) throws Exception {
		Date startDate = new Date();
		Date runDate = null;
		int errorCount = 0;
		IdVector siteIdList = new IdVector();
		int poolSize = 1;
	    ExecutorService service = Executors.newFixedThreadPool(poolSize);
	    
		logInfo("@@@@@@@ process scheduled orders START @@@@@@@@@ " + new Date());

		try {
			APIAccess factory = new APIAccess();

			PropertyService propertyEjb = factory.getPropertyServiceAPI();
			boolean specOrderCutOffDateFl = false;
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
			//gets date range
			if (Utility.isSet(begDate) || Utility.isSet(endDate)) {
				if (Utility.isSet(begDate) && Utility.isSet(endDate)) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
						runDate = sdf.parse(begDate);
						startDate = sdf.parse(endDate);
						specOrderCutOffDateFl = true;
					} catch (Exception e) {
						throw new Exception("The data to be converted to date format was incorrect. Date must use format yyyyMMddHHmm begDate="+begDate+ " endDate= "+endDate);
					}
				} else if (!Utility.isSet(begDate)) {
					throw new Exception("begDate is requred if endDate present");
				} else {
					throw new Exception("endDate is requred if begDate present");
				}
			} else {
				logInfo("No begin date specified running normal date logic");

				String lastStartTime = null;
				try {
					lastStartTime = propertyEjb.getProperty(Constants.CORP_SCHED_PROCESS_START_TIME);
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					runDate = sdf.parse(lastStartTime);
				} catch (Exception e) {
					logError(e.getMessage());
					throw new Exception("start time was not found for the scheduled process");
				}				
			}

			logInfo("processing initiated: " + startDate + " runDate: " + runDate);

			Site sBean = factory.getSiteAPI();

			if (siteIdList.isEmpty()) {
				siteIdList = sBean.getCorpInventorySiteCollection();
			}

		    Set<Future<String>> set = new LinkedHashSet<Future<String>>();
		    PairViewVector failedMessages = new PairViewVector();
		    
			Iterator it = siteIdList.iterator();
			while (it.hasNext()) {
				int siteId = ((Integer) it.next()).intValue();
				logInfo("Process scheduled for Site Id: "+siteId);
				Callable<String> callable = new PlaceScheduledOrder(siteId, runDate, startDate,specOrderCutOffDateFl);					
				Future<String> future = service.submit(callable);
				set.add(future);				
			}
			
			int i = 0;
			for (Future<String> future : set) {
				int siteId = (Integer)siteIdList.get(i);
				try {
					String res = future.get();
					logInfo(siteIdList.size()
							+ " -> " + (i+1)
							+ " processing result: "
							+ res + "\n");

					i++;
				} catch (Exception e) {
					//e.printStackTrace();
					errorCount++;
					failedMessages.add(new PairView(new Integer(siteId), "siteId: " + siteId + "- " + e.getMessage()));
					logInfo(siteIdList.size()
							+ " -> " + (i+1)
							+ " processing result: Error - placeInventoryOrder: pSiteId=" + siteId + " - "
							+ e.getMessage() +  "\n");
				}
			}

			logInfo("result: processed "
					+ (siteIdList.size()-errorCount) + " of " + siteIdList.size() + ".Error count: " + errorCount);			
			
			if (errorCount > 0) {
				String mess = "process_scheduled_orders problems.\n";
				mess += failedMessages.toString();
				throw new Exception(mess);
			}
			if(!specOrderCutOffDateFl) {
				Callable<String> callable = new UpdateTimeProperties2(startDate);
				Future<String> future = service.submit(callable);
				future.get();				
			}

		} catch (Exception e) {
                        e.printStackTrace();
			throw new Exception("processScheduledOrders:: caught error: "+e.getMessage());            
		} finally {
			service.shutdownNow();
		}

		logInfo("@@@@@@@ process scheduled orders DONE @@@@@@@@@ " + new Date());
	}
}


class PlaceScheduledOrder implements Callable<String> {
	int siteId = 0;
	Date runForDate;
	Date startDate;
	boolean specOrderCutOffDateFl;
	
	PlaceScheduledOrder(int siteId, Date runForDate, Date startDate, boolean specOrderCutOffDateFl){
		this.siteId=siteId;
		this.runForDate = runForDate;
		this.startDate = startDate;
		this.specOrderCutOffDateFl = specOrderCutOffDateFl;
	}
	
    public String call() throws Exception {
    	String result = null;
    	try {
			APIAccess factory = new APIAccess();

			Site sBean = factory.getSiteAPI();
			result = sBean.placeScheduledOrder(siteId, runForDate, startDate, specOrderCutOffDateFl);
        } 
        catch(final Exception ex) 
        {
            ex.printStackTrace();
            throw ex;
        } 
 
        return result; 
    } 
}
	
class UpdateTimeProperties2 implements Callable<String> {
	Date startDate = null;

	public UpdateTimeProperties2(Date startDate) {
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
