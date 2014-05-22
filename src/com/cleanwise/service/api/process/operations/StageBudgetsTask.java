package com.cleanwise.service.api.process.operations;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.io.*;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.apps.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.pipeline.PendingOrderNotification;
import javax.transaction.TransactionManager;
import org.apache.log4j.Logger;


public class StageBudgetsTask extends ApplicationServicesAPI {
    private static final Logger log = Logger.getLogger(StageBudgetsTask.class);
     /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public StageBudgetsTask() {
    }
    
	public void execute (String command, Integer modDays, String accountIds)  
	throws Exception {
		Connection conn = null;
        try {
            conn = getConnection();
			//Commands:
			// C - populates calendar table only (just for debugging);
			// S - populates site budgets only (also populates calendar)
			// A - populates account budgets only  (also populates calendar)
			// IF empty does C,S,A
			// modDays - analyzes orders modified after Current Date - modDays (default - 5)
			// accountIds - list of accounts. IF empty does all
			boolean calendarFl = false;
			boolean siteBudgetFl = false;
			boolean accountBudgetFl = false;
			if(!Utility.isSet(command)) {
			    calendarFl = true;
			    siteBudgetFl = true;
			    accountBudgetFl = true;
			} else {
				String[] ssA = command.split(",");
				for(int ii=0; ii<ssA.length; ii++) {
					String ss = ssA[ii]; 
					String ss1 = ss.trim().substring(0,1);
					if(Utility.isSet(ss)) {
						if(ss1.equalsIgnoreCase("C")) {
							calendarFl = true;
						} else if(ss1.equalsIgnoreCase("S")) {
							siteBudgetFl = true;
						} else if(ss1.equalsIgnoreCase("A")) {
							accountBudgetFl = true;
						}
					}
			    }
			}
			if(!calendarFl && !siteBudgetFl && !accountBudgetFl) {
				throw new Exception("Unknown command: "+command);
			}
			
			// create wrk tables if not exists
			createWrkTables(conn);
			createWrkTableIndexes(conn);
			if(calendarFl || siteBudgetFl || accountBudgetFl ) {
				createCalendar(conn,modDays,accountIds);
			}
			if(siteBudgetFl) {
				updateSiteBudgets(conn);
			}
			if(accountBudgetFl) {
				updateAccountBudgets(conn);
			}
			
        } catch (Exception e) {
            e.printStackTrace();
			throw e;
        } finally {
            closeConnection(conn);
        }


    }
////////////////////
	private void updateAccountBudgets (Connection conn) 
	throws Exception{

        String sql = null;
		
		sql = "truncate table tclw_acctbudget_report_wrk";
		executeSql(conn, sql);		    

		sql = 
			"insert into tclw_acctbudget_report_wrk ( \n\r"+
			"	 site_id, site_short_desc, city, state_code, \n\r"+
			"	 postal_code, account_id, cost_center_id, \n\r"+
			"	 cost_center_name, fiscal_calender_id, budget_period,\n\r"+
			"	 budget_year, period_start_date, period_end_date,\n\r"+
			"	bsc_name, cost_center_type_cd, entry_type, amount_spent, amount_allocated)\n\r"+
			" select distinct\n\r"+
			"	null as site_id, null as site_short_desc, \n\r"+
			"	null as city, null as state_province_cd, \n\r"+
			"	null as postal_code, fc.account_id,  cc.cost_center_id, \n\r"+
			"	cc.short_desc as cost_center_name,  fc.fiscal_calendar_id, \n\r"+
			"	fc.period as budget_period, \n\r"+
			"	bud.budget_year as budget_year, \n\r"+
			"	to_char(fc.beg_date,'mm/dd') as period_start_date, \n\r"+
			"	to_char(fc.end_date,'mm/dd') as  period_end_date,\n\r"+
			"	'none' as bsc_name, cc.cost_center_type_cd, 'ACCOUNT' as entry_type, \n\r"+
			"	0 as amount_spent, 0 as amount_allocated\n\r"+
			"  from clw_cost_center cc, clw_budget bud, tclw_fc_budget_report_wrk fc\n\r"+
			" where  bud.budget_year = fc.fiscal_year\n\r"+
			"   and  bud.bus_entity_id = fc.account_id\n\r"+
			"   and  cc.cost_center_id = bud.cost_center_id\n\r"+
			"   and  cc.cost_center_type_cd = 'ACCOUNT BUDGET'\n\r"+
			"   and  cc.cost_center_status_cd = 'ACTIVE' \n\r"+
			"   and (fc.account_id, fc.fiscal_year) in \n\r"+
			"   (select account_id, fiscal_year from tclw_a_budget_report_wrk)";

		executeUpdate(conn, sql);		    

		sql = 
			"update tclw_acctbudget_report_wrk tr  \n\r"+
			"set tr.amount_spent =  \n\r"+
			"( select nvl(sum(amount),0) \n\r"+
			"	from clw_SITE_LEDGER sl, clw_order o\n\r"+
			"   where o.ACCOUNT_ID = tr.account_id\n\r"+
			"	and sl.order_id = o.order_id  \n\r"+
			"	and o.order_status_cd in ('Ordered', 'Invoiced', 'ERP Released') \n\r"+
			"	and (o.order_budget_type_cd not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') or o.order_budget_type_cd is null)\n\r"+
			"   and sl.COST_CENTER_ID = tr.COST_CENTER_ID\n\r"+
			"   and sl.BUDGET_YEAR = tr.BUDGET_YEAR \n\r"+
			"   and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD \n\r"+
			")\n\r"+
			"where  entry_type = 'ACCOUNT'";
		executeUpdate(conn, sql);		    

		sql = 
			"update tclw_acctbudget_report_wrk tr  \n\r"+
			"set  tr.amount_spent =  tr.amount_spent +\n\r"+
			"   (select NVL(sum(amount),0) \n\r"+
			"		  from clw_SITE_LEDGER sl, clw_bus_entity_assoc ba\n\r"+
			"		 where sl.order_id is null \n\r"+
			"			and sl.SITE_ID = ba.bus_entity1_id \n\r"+
			"			and ba.bus_entity2_id = tr.account_id\n\r"+
			"			and ba.bus_entity_assoc_cd = 'SITE OF ACCOUNT'\n\r"+
			"		   and sl.COST_CENTER_ID = tr.COST_CENTER_ID \n\r"+
			"		   and sl.BUDGET_YEAR = tr.BUDGET_YEAR \n\r"+
			"		   and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD \n\r"+
			"		   and sl.entry_type_cd = 'PRIOR PERIOD BUDGET ACTUAL')\n\r"+
			" where entry_type = 'ACCOUNT'\n\r"+
			"	and  account_id in (\n\r"+
			"		select bea.bus_entity2_id \n\r"+
			"         from clw_bus_entity_assoc bea, clw_site_ledger sl\n\r"+
			"		where  sl.entry_type_cd = 'PRIOR PERIOD BUDGET ACTUAL'\n\r"+
			"		  and  sl.site_id = bea.bus_entity1_id\n\r"+
			"	)";
		executeUpdate(conn, sql);		    

		sql = 
			"update tclw_acctbudget_report_wrk tr  \n\r"+
			"set tr.amount_spent = tr.amount_spent +\n\r"+
			"  (select NVL(sum(amount),0) \n\r"+
			"	 from CLW_SITE_LEDGER sl, CLW_ORDER,  CLW_ORDER_ASSOC oa, CLW_ORDER o2 \n\r"+
			"	where  CLW_ORDER.ACCOUNT_ID = tr.account_id \n\r"+
			"	  AND  sl.ORDER_ID = CLW_ORDER.ORDER_ID \n\r"+
			"	  AND CLW_ORDER.ORDER_STATUS_CD = 'Cancelled' \n\r"+
			"	  AND CLW_ORDER.ORDER_TYPE_CD = 'TO_BE_CONSOLIDATED' \n\r"+
			"	  AND CLW_ORDER.ORDER_ID = oa.ORDER1_ID \n\r"+
			"	  AND o2.ORDER_ID = oa.ORDER2_ID \n\r"+
			"	  AND oa.ORDER_ASSOC_CD = 'CONSOLIDATED' \n\r"+
			"	  AND o2.ORDER_STATUS_CD IN \n\r"+
			"        ('Ordered', 'Invoiced', 'ERP Released', 'Process ERP PO') \n\r"+
			"	  AND (CLW_ORDER.ORDER_BUDGET_TYPE_CD not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') \n\r"+
			"		  OR CLW_ORDER.ORDER_BUDGET_TYPE_CD IS NULL) \n\r"+
			"	  AND sl.COST_CENTER_ID = tr.COST_CENTER_ID \n\r"+
			"	  AND sl.BUDGET_YEAR = tr.BUDGET_YEAR \n\r"+
			"	  AND sl.BUDGET_PERIOD = tr.BUDGET_PERIOD) \n\r"+
			" where entry_type = 'ACCOUNT' \n\r"+
			"	and tr.account_id in ( \n\r"+
			"	  select o.account_id \n\r"+
			"		from clw_order o, clw_order_assoc oa \n\r"+
			"		where o.order_id = oa.order1_id)  \n\r"+
			"	";
		executeUpdate(conn, sql);		    
              
		sql = 
			" update tclw_acctbudget_report_wrk tr \n\r"+
			" set tr.amount_allocated =  \n\r"+
			" (select nvl(bd.amount,0) \n\r"+
			"	from clw_budget b, clw_budget_detail bd \n\r"+
			"   where b.bus_entity_ID = tr.account_id \n\r"+
			"	 and b.COST_CENTER_ID = tr.COST_CENTER_ID \n\r"+
			"	 and b.budget_year = tr.budget_year \n\r"+
			"	 and bd.budget_id = b.budget_id \n\r"+
			"	 and bd.period = tr.budget_period \n\r"+
			"	 and b.budget_type_cd = 'ACCOUNT BUDGET' \n\r"+
			"	  and b.budget_status_cd='ACTIVE' \n\r"+
			"	 ) \n\r"+
			"   where  cost_center_type_cd = 'ACCOUNT BUDGET' \n\r"+
			"	and entry_type = 'ACCOUNT'";
		executeUpdate(conn, sql);		    


		sql = 
			"	 update tclw_acctbudget_report_wrk tr \n\r"+
			"	   set bsc_name =  \n\r"+
			"	   (select max(sub.short_desc)  \n\r"+
			"		from clw_bus_entity sub  , clw_bus_entity_assoc ba  \n\r"+
			"		where sub.bus_entity_id = ba.bus_entity1_id  \n\r"+
			"		  and ba.bus_entity2_id  = tr.site_id \n\r"+
			"		  and ba.BUS_ENTITY_ASSOC_CD  = 'BSC FOR SITE'  \n\r"+
			"	  )";
		executeUpdate(conn, sql);	

		sql = "delete from tclw_acctbudget_report \n\r" +
            " where (account_id, budget_year) in \n\r" + 
			" ( select account_id, fiscal_year from tclw_fc_budget_report_wrk) \n\r"+
			" and cost_center_type_cd = 'ACCOUNT BUDGET'";
		executeUpdate(conn, sql);	

		sql = "insert into tclw_acctbudget_report \n\r" +
            " select * from tclw_acctbudget_report_wrk";

		executeUpdate(conn, sql);	

		return;
	}


	private void updateSiteBudgets (Connection conn) 
	throws Exception{

        String sql = null;
		
		sql = "truncate table tclw_acctbudget_report_wrk";
		executeSql(conn, sql);		    

		sql = 
			"insert into tclw_acctbudget_report_wrk ( \n\r"+
			"   site_id, site_short_desc, city, state_code,  \n\r"+
			"	 postal_code, account_id, cost_center_id,  \n\r"+
			"	 cost_center_name, fiscal_calender_id, budget_period, \n\r"+
			"	 budget_year, period_start_date, period_end_date, \n\r"+
			"	bsc_name, cost_center_type_cd, entry_type, amount_spent,  \n\r"+
			"	amount_allocated) \n\r"+
			"select distinct \n\r"+
			"	ba.bus_entity1_id, site.short_desc, \n\r"+
			"	sa.city,sa.state_province_cd,  \n\r"+
			"	sa.postal_code, ba.bus_entity2_id,  cc.cost_center_id,  \n\r"+
			"	cc.short_desc as cost_center_name,  fc.fiscal_calendar_id,  \n\r"+
			"	fc.period as budget_period,  bud.budget_year as budget_year,  \n\r"+
			"	to_char(fc.beg_date,'mm/dd') as period_start_date,  \n\r"+
			"	to_char(fc.end_date,'mm/dd') as  period_end_date, \n\r"+
			"	'none' as bsc_name, cc.cost_center_type_cd, 'SITE' as entry_type,  \n\r"+
			"	0 as amount_spent, 0 as amount_allocated \n\r"+
			"  from clw_bus_entity_assoc ba, clw_cost_center cc,  \n\r"+
			"	   clw_bus_entity site, clw_budget bud ,  clw_address sa ,  \n\r"+
			"	   tclw_fc_budget_report_wrk fc \n\r"+
			" where  ba.bus_entity2_id = fc. account_id \n\r"+
			"   and  bud.budget_year = fc.fiscal_year \n\r"+
			"   and  cc.cost_center_id = bud.cost_center_id \n\r"+
			"	and  cc.cost_center_type_cd = 'SITE BUDGET' \n\r"+
			"   and ba.bus_entity1_id = bud.bus_entity_id  \n\r"+
			"	 and sa.bus_entity_id = site.bus_entity_id  \n\r"+
			"	and sa.address_type_cd = 'SHIPPING'  \n\r"+
			"	 and cc.cost_center_status_cd = 'ACTIVE'  \n\r"+
			"	and site.bus_entity_id = ba.bus_entity1_id  \n\r"+
			"	and fc.account_id  = ba.bus_entity2_id \n\r"+
			"	and fc.fiscal_year  = bud.budget_year \n\r"+
			"	and (fc.account_id, fc.fiscal_year) in  \n\r"+
			"	   (select account_id, fiscal_year from tclw_a_budget_report_wrk)";

		executeUpdate(conn, sql);		    

		sql = 
			"	update tclw_acctbudget_report_wrk tr   \n\r"+
			"	set  tr.amount_spent =   \n\r"+
			"	 ( select nvl(sum(amount),0)  \n\r"+
			"		 from clw_SITE_LEDGER sl, clw_order o  \n\r"+
			"		where sl.SITE_ID = tr.site_id  \n\r"+
			"		  and sl.order_id = o.order_id   \n\r"+
			"		  and o.order_status_cd in  \n\r"+
			"		 ('Ordered', 'Invoiced', 'ERP Released')  \n\r"+
			"			and (o.order_budget_type_cd not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') or o.order_budget_type_cd is null) \n\r"+
			"		   and sl.COST_CENTER_ID = tr.COST_CENTER_ID  \n\r"+
			"		   and sl.BUDGET_YEAR = tr.BUDGET_YEAR  \n\r"+
			"		   and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD ) \n\r"+
			"	 where entry_type = 'SITE'";
		executeUpdate(conn, sql);		    

		sql = 
			"	 update tclw_acctbudget_report_wrk tr   \n\r"+
			"	 set  tr.amount_spent =  (NVL(tr.amount_spent,0) + \n\r"+
			"	  NVL(( select sum(NVL(amount,0))  \n\r"+
			"			  from clw_SITE_LEDGER sl \n\r"+
			 			"where 1=1  \n\r"+
			"			  and sl.SITE_ID = tr.site_id  \n\r"+
			"			  and sl.COST_CENTER_ID = tr.COST_CENTER_ID  \n\r"+
			"			  and sl.BUDGET_YEAR = tr.BUDGET_YEAR \n\r"+
			"			  and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD  \n\r"+
			"			  and sl.entry_type_cd = 'PRIOR PERIOD BUDGET ACTUAL'),0)) \n\r"+
			"	  where  entry_type = 'SITE' \n\r"+
			"		and  site_id in ( \n\r"+
			"			select sl.site_id from clw_site_ledger sl \n\r"+
			"			where  sl.entry_type_cd = 'PRIOR PERIOD BUDGET ACTUAL' \n\r"+
			"		)";
		executeUpdate(conn, sql);		    

		sql = 
			"	  update tclw_acctbudget_report_wrk tr   \n\r"+
			"	  set tr.amount_spent = ( NVL (tr.amount_spent, 0) +  \n\r"+
			"	  NVL((select sum(NVL(sl.amount,0))  \n\r"+
			 			"from CLW_SITE_LEDGER sl, CLW_ORDER o,  CLW_ORDER_ASSOC oa, CLW_ORDER o2 \n\r"+
			"			where sl.SITE_ID = tr.site_id  \n\r"+
			"			  and sl.ORDER_ID = o.ORDER_ID \n\r"+
			"			  and o.ORDER_STATUS_CD = 'Cancelled' \n\r"+
			"			  and o.ORDER_TYPE_CD = 'TO_BE_CONSOLIDATED' \n\r"+
			"			  and o.ORDER_ID = oa.ORDER1_ID \n\r"+
			"			  and o2.ORDER_ID = oa.ORDER2_ID \n\r"+
			"			  and oa.ORDER_ASSOC_CD = 'CONSOLIDATED' \n\r"+
			"			  and o2.ORDER_STATUS_CD IN ('Ordered', 'Invoiced', 'ERP Released', 'Process ERP PO') \n\r"+
			"			  and (o.ORDER_BUDGET_TYPE_CD not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') \n\r"+
			"				   OR o.ORDER_BUDGET_TYPE_CD IS NULL)   \n\r"+
			"			  and sl.COST_CENTER_ID = tr.COST_CENTER_ID \n\r"+
			"			  and sl.BUDGET_YEAR = tr.BUDGET_YEAR \n\r"+
			"			  and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD) ,0))  \n\r"+
			"		  where entry_type = 'SITE'    \n\r"+
			"			and tr.site_id in ( \n\r"+
			"			  select o.site_id \n\r"+
			"				from clw_order o, clw_order_assoc oa \n\r"+
			"				where (o.order_id = oa.order1_id) \n\r"+
			"			)";
		executeUpdate(conn, sql);		    
              
		sql = 
			"	 update tclw_acctbudget_report_wrk tr   \n\r"+
			"	 set tr.amount_allocated =   \n\r"+
			"	 (select nvl(bd.amount,0) \n\r"+
			"		from clw_budget b, clw_budget_detail bd  \n\r"+
			"	   where b.bus_entity_ID = tr.site_id  \n\r"+
			"		 and b.COST_CENTER_ID = tr.COST_CENTER_ID  \n\r"+
			"		 and b.budget_year = tr.budget_year \n\r"+
			"		 and bd.budget_id = b.budget_id  \n\r"+
			"		 and bd.period = tr.budget_period \n\r"+
			"		 and b.budget_type_cd = 'SITE BUDGET' \n\r"+
			"		  and b.budget_status_cd='ACTIVE' \n\r"+
			"		 ) \n\r"+
			"	   where  cost_center_type_cd = 'SITE BUDGET' \n\r"+
			"		and entry_type = 'SITE'";
		executeUpdate(conn, sql);		    

 

		sql = 
			"	 update tclw_acctbudget_report_wrk tr \n\r"+
			"	   set bsc_name =  \n\r"+
			"	   (select max(sub.short_desc)  \n\r"+
			"		from clw_bus_entity sub  , clw_bus_entity_assoc ba  \n\r"+
			"		where sub.bus_entity_id = ba.bus_entity1_id  \n\r"+
			"		  and ba.bus_entity2_id  = tr.site_id \n\r"+
			"		  and ba.BUS_ENTITY_ASSOC_CD  = 'BSC FOR SITE'  \n\r"+
			"	  )";
		executeUpdate(conn, sql);	

		sql = "delete from tclw_acctbudget_report \n\r" +
            " where (account_id, budget_year) in \n\r" + 
			" ( select account_id, fiscal_year from tclw_fc_budget_report_wrk) \n\r"+
			" and cost_center_type_cd = 'SITE BUDGET'";
		executeUpdate(conn, sql);	

		sql = "insert into tclw_acctbudget_report \n\r" +
            " select * from tclw_acctbudget_report_wrk";
		executeUpdate(conn, sql);	

		return;
	}


	private void createCalendar (Connection conn, Integer daysModified, String accountIds) 
	throws Exception{
		String sql = null;
		
		sql = "truncate table tclw_fy_budget_report_wrk";
		executeSql (conn, sql) ;
		
		sql = "truncate table tclw_a_budget_report_wrk";
		executeSql (conn, sql) ;

		sql = "truncate table tclw_fc_budget_report_wrk";
		executeSql (conn, sql) ;
		
		if(daysModified==null) {
			daysModified = 5;
		}

        //Pick accounts and years
		sql =
			"insert into tclw_a_budget_report_wrk \n\r"+
			" select distinct o.account_id, budget_year from  \n\r"+
			" clw_site_ledger sl, clw_order o  \n\r"+
			" where o.order_id = sl.order_id and sl.cost_center_id > 0 \n\r"+
			" and o.mod_date >= trunc(sysdate) - "+daysModified +"\n\r"+
			(Utility.isSet(accountIds)?" and o.account_id in ("+accountIds+") \n\r":"")+
			" and sl.budget_year > 0";
		executeUpdate (conn, sql) ;

        //Pick yaers
		sql =
			"insert into tclw_fy_budget_report_wrk \n\r"+
			"select distinct fiscal_year from tclw_a_budget_report_wrk ";
		executeUpdate (conn, sql) ;

        //Insert one extra year
		sql =
			"insert into tclw_fy_budget_report_wrk \n\r"+
			"select fiscal_year+1 from tclw_fy_budget_report_wrk \n\r"+
 			"where fiscal_year+1 not in  \n\r"+
			" (select fiscal_year from tclw_fy_budget_report_wrk) ";
		executeUpdate (conn, sql) ;

		//Insert regular calendars
		sql =
			"insert into tclw_fc_budget_report_wrk \n\r"+
			"select  \n\r"+
			"fc.fiscal_calender_id, fc.bus_entity_id, fc.fiscal_year, fcd.period,  \n\r"+
			"to_date(fcd.mmdd||'/'||to_char(eff_date,'yyyy'),'mm/dd/yyyy'),  \n\r"+
			"null, fc.eff_date, fcd.mmdd, fc.fiscal_year, null \n\r"+
			"from clw_fiscal_calender fc, clw_fiscal_calender_detail fcd,  \n\r"+
			"     tclw_fy_budget_report_wrk fy \n\r"+
			"where fc.fiscal_calender_id = fcd.fiscal_calender_id \n\r"+
			"and fc.fiscal_year = fy.fiscal_year \n\r"+
			"and trim(fcd.mmdd) is not null  \n\r"+
			"and fc.bus_entity_id in ( \n\r"+
   			"select distinct account_id \n\r"+
			"     from tclw_a_budget_report_wrk \n\r"+
			")";
		executeUpdate (conn, sql) ;

		//Insert 0 fical year calendars
		sql =
			"insert into tclw_fc_budget_report_wrk \n\r"+
			"select  \n\r"+
			"aa.fiscal_calender_id, aa.bus_entity_id, aa.fiscal_year, fcd.period,  \n\r"+
			"to_date(fcd.mmdd||'/'||aa.fiscal_year,'mm/dd/yyyy'),  \n\r"+
			"null, aa.eff_date, fcd.mmdd, 0, null \n\r"+
			"from ( \n\r"+
			"select fc.fiscal_calender_id, fy.fiscal_year, fc.bus_entity_id, fc.eff_date \n\r"+
			"from clw_fiscal_calender fc, tclw_fy_budget_report_wrk fy \n\r"+
			"where fc.fiscal_year = 0  \n\r"+
			"and fc.bus_entity_id in ( \n\r"+
			"   select distinct account_id \n\r"+
			"     from tclw_a_budget_report_wrk \n\r"+
			")) aa, clw_fiscal_calender_detail fcd \n\r"+
			"where aa.eff_date = ( \n\r"+
			"         select max(eff_date)  \n\r"+
			"           from clw_fiscal_calender c \n\r"+
			"          where aa.bus_entity_id = c.bus_entity_id \n\r"+
			"           and to_date(aa.fiscal_year,'yyyy') >= c.eff_date) \n\r"+
			"  and (aa.fiscal_year, aa.bus_entity_id ) not in  \n\r"+
			"     (select fiscal_year, account_id from tclw_fc_budget_report_wrk) \n\r"+
			"  and fcd.fiscal_calender_id = aa.fiscal_calender_id \n\r"+
			"  and fcd.mmdd is not null";
		executeUpdate (conn, sql) ;


	//Loop move a period year
		sql =
			"update tclw_fc_budget_report_wrk set beg_date = add_months(beg_date,12)  \n\r"+
			"where (fiscal_calendar_id, period) in ( \n\r"+
			"select c.fiscal_calendar_id, c.period from tclw_fc_budget_report_wrk c, \n\r"+
			"( \n\r"+
			"select c2.fiscal_calendar_id,  c2.period   \n\r"+
			" from tclw_fc_budget_report_wrk c1, tclw_fc_budget_report_wrk c2 \n\r"+
			" where c1.fiscal_calendar_id = c2.fiscal_calendar_id \n\r"+
			" and c1.period+1 = c2.period \n\r"+
			" and c1.beg_date > c2.beg_date \n\r"+
			" and c1.fiscal_year_orig != 0 \n\r"+
			") b \n\r"+
			"where b.fiscal_calendar_id = c.fiscal_calendar_id  \n\r"+
			" and c.period >= b.period \n\r"+
			")";

		int count =0;
		for(;count<5; count++) {
		  int ii = executeUpdate (conn, sql) ;
		  if(ii==0) break;
		}
		if(count==5) {
			throw new Exception ("Too long calendar. To check use sql: "+sql);
		}



		//Set end_date for the calendar
		sql =
			"update tclw_fc_budget_report_wrk c1  \n\r"+
			"set end_date = \n\r"+
			"( \n\r"+
			"select c2.beg_date-1  from tclw_fc_budget_report_wrk c2 \n\r"+
			"where c1.fiscal_calendar_id = c2.fiscal_calendar_id \n\r"+
			"and c1.fiscal_year = c2.fiscal_year \n\r"+
			"and c1.account_id = c2.account_id \n\r"+
			"and c1.period+1 = c2.period \n\r"+
			"and c2.beg_date is not null \n\r"+
			") ";
		executeUpdate (conn, sql) ;

        //set end date for 0 fiscal year calendars
		sql =
			"update tclw_fc_budget_report_wrk c1  \n\r"+
			"  set end_date = to_date('12/31'||fiscal_year, 'mm/dd/yyyy') \n\r"+
			"where fiscal_year_orig = 0 \n\r"+
			"and period =  \n\r"+
			"    (select max(c2.period)  \n\r"+
			"       from tclw_fc_budget_report_wrk c2  \n\r"+
			"       where c1.fiscal_year = c2.fiscal_year  \n\r"+
			"         and c1.account_id = c2.account_id  \n\r"+
			"         and c2.beg_date is not null) \n\r"+
			"and end_date is null";
		executeUpdate (conn, sql) ;

/*
update tclw_fc_budget_report_wrk c1 
set c1.end_date = 
(
select c3.beg_date-1 
	from tclw_fc_budget_report_wrk c3 
	where c3.account_id = c1.account_id
	and c3.fiscal_year = c1.fiscal_year+1
	and c3.period = 1
     and c1.period = 
     (select max(c2.period) from tclw_fc_budget_report_wrk c2 where c1.fiscal_year = c2.fiscal_year and c1.account_id = c2.account_id and c2.beg_date is not null)
     and c1.end_date is null
     and c1.fiscal_year_orig > 0
)	
where c1.fiscal_year_orig > 0
and c1.period = (select max(c2.period) from tclw_fc_budget_report_wrk c2 where c1.fiscal_year = c2.fiscal_year and c1.account_id = c2.account_id and c2.beg_date is not null)
and c1.end_date is null
and c1.orca_fl = 'false'
*/

		//set end date as a beginning of the next calendar (minus 1)
		sql =
			"update tclw_fc_budget_report_wrk c1 set end_date = \n\r"+
			"(select c3.beg_date-1 from tclw_fc_budget_report_wrk c3  \n\r"+
			"where c3.account_id = c1.account_id \n\r"+
			"and c3.fiscal_year = c1.fiscal_year+1 \n\r"+
			"and c3.period = 1 	 \n\r"+
			"and c1.fiscal_year_orig > 0 \n\r"+
			"and c1.period =  \n\r"+
			"  (select max(c2.period)  \n\r"+
			"    from tclw_fc_budget_report_wrk c2  \n\r"+
			"	where c1.fiscal_year = c2.fiscal_year  \n\r"+
			"	and c1.account_id = c2.account_id  \n\r"+
			"	and c2.beg_date is not null) \n\r"+
			"and c1.end_date is null \n\r"+
			") \n\r"+
			"where c1.end_date is null";
		executeUpdate (conn, sql) ;


	//set end date for calendars with year gap
		sql =
			"update tclw_fc_budget_report_wrk fc set end_date =  \n\r"+
			"(select min(c.eff_date-1) \n\r"+
			"  from tclw_fc_budget_report_wrk c  \n\r"+
			"  where fc.account_id = c.account_id \n\r"+
			"    and to_number(fc.fiscal_year) < to_number(c.fiscal_year) \n\r"+
			") \n\r"+
			"where fc.end_date is null \n\r"+
			"and fc.period =  \n\r"+
			"  (select max(fc1.period)  \n\r"+
			"     from tclw_fc_budget_report_wrk fc1 \n\r"+
			"     where fc.account_id = fc1.account_id \n\r"+
			"       and fc.fiscal_year = fc1.fiscal_year \n\r"+
			"  )";
		executeUpdate (conn, sql) ;

	//set end date for calenars with no next calendar
		sql =
			"update tclw_fc_budget_report_wrk fc set end_date =  \n\r"+
			"(select add_months(f.beg_date,12) from tclw_fc_budget_report_wrk f  \n\r"+
			"  where f.fiscal_calendar_id = fc.fiscal_calendar_id and f.period=1) \n\r"+
			"where fc.end_date is null \n\r"+
			"and fc.fiscal_year =  \n\r"+
			" (select max(f.fiscal_year)  \n\r"+
			"    from tclw_fc_budget_report_wrk f  \n\r"+
			"   where f.account_id = fc.account_id) \n\r"+
			"and fc.period =  \n\r"+
			"  (select max(f.period)  \n\r"+
			"     from  tclw_fc_budget_report_wrk f  \n\r"+
			"    where f.fiscal_calendar_id = fc.fiscal_calendar_id) ";
		executeUpdate (conn, sql) ;


  	//===========  Testing ========
	
		sql = 
		   "select count(*) from tclw_fc_budget_report_wrk \n\r"+
			"where end_date is null";

		log.info("Exectute sql: \n\r"+sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int cc = rs.getInt(1);
		rs.close();
		pstmt.close();
		if(cc>0) {
			throw new Exception("Some calendars do not have end date. Use sql to check: "+sql);
		}

		return;
	
	}


	private boolean getSemaphore() {
	    //Add code to get semaphore
		return true;
	}
	
	private void createWrkTables(Connection conn) 
	throws Exception{
		String userTables = 
		  "select table_name from user_tables \n\r"+
		  " where table_name in (\n\r"+
		  "'tclw_acctbudget_report_wrk',\n\r"+
		  "'tclw_a_budget_report_wrk',\n\r"+
		  "'tclw_fc_budget_report_wrk',\n\r"+
		  "'tclw_fy_budget_report_wrk'\n\r"+
		  ")";        
		
		Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(userTables.toUpperCase());
		Boolean acctbudgetFl = false;
		Boolean aBudgetFl = false;
		Boolean fcBudgetFl = false;
		Boolean fyBudgetFl = false;
		while ( rs.next() ) {
			String tName = rs.getString(1);		
		    if("tclw_acctbudget_report_wrk".equalsIgnoreCase(tName)) acctbudgetFl = true;
		    if("tclw_a_budget_report_wrk".equalsIgnoreCase(tName)) aBudgetFl = true;
		    if("tclw_fc_budget_report_wrk".equalsIgnoreCase(tName)) fcBudgetFl = true;
		    if("tclw_fy_budget_report_wrk".equalsIgnoreCase(tName)) fyBudgetFl = true;
		}
        if(!acctbudgetFl) {
			String sql = 
				"create table tclw_acctbudget_report_wrk \n\r"+
				" as select * from tclw_acctbudget_report where 1=2 \n\r";
			executeSql(conn, sql);		    
		} 
        if(!aBudgetFl) {
			String sql = 
				"create table tclw_a_budget_report_wrk \n\r"+
				"(account_id number(38), fiscal_year number(4))";
			executeSql(conn, sql);		    
		}
        if(!fcBudgetFl) {
			String sql = 
				"create table tclw_fc_budget_report_wrk \n\r"+
				"(fiscal_calendar_id number(38), account_id number(38),  \n\r"+
				"fiscal_year number(4), period number(3),  \n\r"+
				"beg_date date, end_date date, eff_date date,  \n\r"+
				"beg_mmdd varchar2(10), fiscal_year_orig number(4),  \n\r"+
				"orca_fl varchar2(10)) ";
			executeSql(conn, sql);		    
		}

        if(!fyBudgetFl) {
			String sql = 
				"create table tclw_fy_budget_report_wrk (fiscal_year number(4))";			
			executeSql(conn, sql);		    
		}

		return;
	}
	
	private void createWrkTableIndexes(Connection conn) 
	throws Exception{
		String userTables = 
		  "select index_name from user_indexes \n\r"+
		  " where index_name in (\n\r"+
		  "'tclw_acctbudget_report_wrk_i',\n\r"+
		  "'tclw_a_budget_report_wrk_i',\n\r"+
		  "'tclw_fc_budget_report_wrk_i',\n\r"+
		  "'tclw_fy_budget_report_wrk_i'\n\r"+
		  ")";        
		
		Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery(userTables.toUpperCase());
		Boolean acctbudgetFl = false;
		Boolean aBudgetFl = false;
		Boolean fcBudgetFl = false;
		Boolean fyBudgetFl = false;
		while ( rs.next() ) {
			String tName = rs.getString(1);		
		    if("tclw_acctbudget_report_wrk_i".equalsIgnoreCase(tName)) acctbudgetFl = true;
		    if("tclw_a_budget_report_wrk_i".equalsIgnoreCase(tName)) aBudgetFl = true;
		    if("tclw_fc_budget_report_wrk_i".equalsIgnoreCase(tName)) fcBudgetFl = true;
		    if("tclw_fy_budget_report_wrk_i".equalsIgnoreCase(tName)) fyBudgetFl = true;
		}
        if(!acctbudgetFl) {
			String sql = 
            "create index tclw_acctbudget_report_wrk_i on \n\r"+ 
            "tclw_acctbudget_report_wrk \n\r"+ 
            "(account_id, budget_year, budget_period, site_id)";
			executeSql(conn, sql);		    
		} 
        if(!aBudgetFl) {
			String sql = 
            "create index tclw_a_budget_report_wrk_i on  \n\r"+
            "tclw_a_budget_report_wrk (account_id, fiscal_year)";
			executeSql(conn, sql);		    
		}
        if(!fcBudgetFl) {
			String sql = 
            "create index tclw_fc_budget_report_wrk_i on  \n\r"+
            "tclw_fc_budget_report_wrk (account_id, fiscal_year)";
			executeSql(conn, sql);		    
		}

        if(!fyBudgetFl) {
			String sql = 
            "create index tclw_fy_budget_report_wrk_i on  \n\r"+
            "tclw_fy_budget_report_wrk (fiscal_year)";
			executeSql(conn, sql);		    
		}

		return;
	}
	
	
	
	private void executeSql (Connection conn, String sql) 
	throws Exception {
		log.info("Exectute sql: \n\r"+sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.execute();
		pstmt.close();
		return;
	}
		  
	private int executeUpdate (Connection conn, String sql) 
	throws Exception {
		log.info("Exectute sql: \n\r"+sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int ii = pstmt.executeUpdate();
		pstmt.close();
		log.info("Number of affected records: "+ii);
		return ii;
	}


}

