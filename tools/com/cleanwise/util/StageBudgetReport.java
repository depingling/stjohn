import java.sql.*;
import java.util.*;
import java.io.*;

public class StageBudgetReport {

    private static String kUsage = "Usage: -DdbUrl=v -DdbUser=v "
        + "-DdbPwd=v -Dacctid=v"
        + " -Dyear=v2 (optional year)"
        + " -Dperiod=<optional period> "
	+ " -Dcmd=reset_ledger";

    public static void main(String [] args ) {
	StageBudgetReport o = new StageBudgetReport();
	o.exec();
    }

    Connection dbc = null;
    //Statement stmt = null;

    private ArrayList getAllAccountsToRun()
        throws Exception
    {

        // get the accounts that have placed orders and therefore
        // need to have their budget report information updated.

        String sql = " select distinct o.account_id from  " +
          " clw_site_ledger sl, clw_order o " +
          " where o.order_id = sl.order_id and sl.cost_center_id > 0 " +
          " and o.original_order_date >= sysdate - 5 " +
          " and sl.budget_year > 0 " +
          " order by o.account_id ";

Statement stmt = dbc.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
        ArrayList accts = new ArrayList();
        while (rs.next()) {
		accts.add(rs.getString(1));
	}
        rs.close();
        stmt.close();

        return accts;
    }

    boolean mDebug = false;
    private void dblog(String msg) {
	if (mDebug) {
	    System.out.println((new java.util.Date().toString()) + " " + msg);
	}
    }

    private void checkAmountSpent(int pBudgetYear,
                    int pBudgetPeriod, int pAcctId )
        throws Exception {
	String sql = "select entry_type, sum(amount_spent) " +
	    " from tclw_acctbudget_report tr" +
            " where budget_period = " + pBudgetPeriod +
            " and budget_year = " + pBudgetYear +
            " and account_id = " + pAcctId +
	    " group by entry_type";
Statement stmt = dbc.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	while ( rs.next() ) {
	    dblog( "-------    Entry type=" + rs.getString(1)
		  + " amount spent=" + rs.getDouble(2)
		  );
	}
	rs.close();
        stmt.close();


    }


    private void bp(int pBudgetYear,
                    int pBudgetPeriod, int pAcctId,
                    int maxBudgetPeriod)
        throws Exception {


    PreparedStatement pstmt2 = null;
	PreparedStatement pstmt2a = null;
	PreparedStatement pstmt2_01 = null;
	PreparedStatement pstmt2_1 = null;
	PreparedStatement pstmt2_2 = null;
	PreparedStatement pstmt4 = null;
	PreparedStatement pstmt5 = null;
	PreparedStatement pstmt6 = null;
	PreparedStatement pstmt6a = null;
        PreparedStatement pstmt7 = null;
        PreparedStatement pstmt7a = null;
	PreparedStatement pstmt9 = null;
	String sql = "delete from tclw_acctbudget_report " +
            " where budget_period = " + pBudgetPeriod +
            " and budget_year = " + pBudgetYear +
            " and account_id = " + pAcctId;

	dblog("\\t\t\t  tbp -1:: " + sql);
Statement stmt = dbc.createStatement();
	stmt.executeUpdate(sql);
        stmt.close();
	dbc.commit();

	sql = "insert into tclw_acctbudget_report " +
            " (site_id, site_short_desc, city, state_code, "
            + " postal_code, account_id, cost_center_id, "
            + " cost_center_name, budget_period, "
            + " budget_year,period_start_date, period_end_date, "
	    + " bsc_name, cost_center_type_cd,ENTRY_TYPE) "
	    + " select distinct ba.bus_entity1_id, site.short_desc, "
	    + " sa.city,sa.state_province_cd, "
	    + " sa.postal_code, ba.bus_entity2_id,  cc.cost_center_id, "
	    + " cc.short_desc, "
            + "?" + "," + "?"
	    + ", '1/1', '1/31'  , 'none', cc.cost_center_type_cd, 'SITE'"
            + " from clw_bus_entity_assoc ba, clw_cost_center cc, "
            + " clw_bus_entity site, clw_budget bud ,  clw_address sa "
	    + " where ba.bus_entity2_id = " + "?"
	    + " and  cc.cost_center_id = bud.cost_center_id "
            + " and cc.cost_center_type_cd =	'SITE BUDGET' "
	    + " and ba.bus_entity1_id = bud.bus_entity_id "
        + " and bud.budget_year = " + pBudgetYear
        + " and sa.bus_entity_id = site.bus_entity_id  "
	    + " and sa.address_type_cd = 'SHIPPING'  "
            + " and cc.cost_center_status_cd = 'ACTIVE' "
	    + " and site.bus_entity_id = ba.bus_entity1_id ";
    if(pstmt2 == null){
		pstmt2 = dbc.prepareStatement(sql);
	}
	pstmt2.setInt(1, pBudgetPeriod);
	pstmt2.setInt(2, pBudgetYear);
	pstmt2.setInt(3, pAcctId);

        dblog("\tbp --2:: " + sql);
        int ret = pstmt2.executeUpdate();
        dblog("\tbp --2:: ret " + ret);
        pstmt2.close();
	dbc.commit();

        sql = "insert into tclw_acctbudget_report " +
            " (site_id, site_short_desc, city, state_code, "
            + " postal_code, account_id, cost_center_id, "
            + " cost_center_name, budget_period, " +
            " budget_year,period_start_date, period_end_date, "
            + " bsc_name, cost_center_type_cd,ENTRY_TYPE) " +
            " select distinct null, null,null,null,null, " + "?" + ", " +
	    " cc.cost_center_id, cc.short_desc, " +
	    "?" + "," + "?" +
            " , '1/1', '1/31' " +
            " , 'none', cc.cost_center_type_cd, 'ACCOUNT' "
            + " from clw_cost_center cc , clw_budget bud "
	    + " where bud.bus_entity_id  = " + "?"
            + " and cc.cost_center_id = bud.cost_center_id "
            + " and bud.budget_year = " + pBudgetYear
            + " and cc.cost_center_status_cd = 'ACTIVE' "
            + " and cc.cost_center_type_cd =	'ACCOUNT BUDGET' ";

		if(pstmt2a == null){
			pstmt2a = dbc.prepareStatement(sql);
		}
        pstmt2a.setInt(1, pAcctId);
        pstmt2a.setInt(2, pBudgetPeriod);
        pstmt2a.setInt(3, pBudgetYear);
        pstmt2a.setInt(4, pAcctId);

        dblog("\tbp --2a:: " + sql);
        ret = pstmt2a.executeUpdate();
        dblog("\tbp --2a:: ret " + ret);
	pstmt2a.close();
        dbc.commit();

        sql = " update /*+FULL(TCLW_ACCTBUDGET_REPORT) */TCLW_ACCTBUDGET_REPORT "
	    + " set bsc_name = (select max(sub.short_desc) "
	    + " from clw_bus_entity sub  , clw_bus_entity_assoc ba "
	    + " where sub.bus_entity_id = ba.bus_entity1_id "
	    + " and ba.bus_entity2_id  = site_id "
	    + " and ba.BUS_ENTITY_ASSOC_CD  = 'BSC FOR SITE' )"
	    + " where bsc_name = 'none' "
	    + " and budget_period = ?"
	    + " and budget_year = ?"
	    + " and account_id = ?" ;
        dblog("\tbp --2.01:: " + sql);

		if(pstmt2_01 == null){
			pstmt2_01 = dbc.prepareStatement(sql);
		}
        pstmt2_01.setInt(1,pBudgetPeriod);
        pstmt2_01.setInt(2,pBudgetYear);
		pstmt2_01.setInt(3,pAcctId);

		pstmt2_01.executeUpdate();
        pstmt2_01.close();
	dbc.commit();

	sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
	    " tr.fiscal_calender_id =  " +
            "   ("
            + " select max(fc.fiscal_calender_id) "
            + " from "
            + " clw_fiscal_calender fc where "
            + " fc.bus_entity_id = "  + "?"
            + " and to_char(eff_date, 'yyyy') = " + "?"
            + "   )" +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" ;

        dblog("\tbp --2.1:: " + sql);

		if(pstmt2_1 == null){
			pstmt2_1 = dbc.prepareStatement(sql);
		}
        pstmt2_1.setInt(1,pAcctId);
        pstmt2_1.setInt(2,pBudgetYear);
        pstmt2_1.setInt(3,pBudgetPeriod);
        pstmt2_1.setInt(4,pBudgetYear);
        pstmt2_1.setInt(5,pAcctId);
        pstmt2_1.executeUpdate();
        pstmt2_1.close();
	dbc.commit();

        sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
	    " tr.fiscal_calender_id =  " +
            "   ("
            + " select max(fc.fiscal_calender_id) "
            + " from "
            + " clw_fiscal_calender fc where "
            + " fc.bus_entity_id = "  + "?"
            + "   )"
            + " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and ( tr.fiscal_calender_id = 0 "
            + "     or tr.fiscal_calender_id is null ) "
            + " and account_id = " + "?" ;

        dblog("\tbp --2.2:: " + sql);
		if(pstmt2_2 == null){
			pstmt2_2 = dbc.prepareStatement(sql);
		}
        pstmt2_2.setInt(1,pAcctId);
        pstmt2_2.setInt(2,pBudgetPeriod);
        pstmt2_2.setInt(3,pBudgetYear);
        pstmt2_2.setInt(4,pAcctId);

        pstmt2_2.executeUpdate();
        pstmt2_2.close();
	dbc.commit();

        dblog("maxBudgetPeriod for year: "+pBudgetYear+" and account: "+pAcctId+" = "+maxBudgetPeriod);

	int endBudgetPeriod = pBudgetPeriod + 1;
	if ( endBudgetPeriod >= (maxBudgetPeriod + 1 )) {
	    endBudgetPeriod = 1;
	}

/*sql = " update tclw_acctbudget_report tr" +
            " set period_start_date = ( select " + "?" +
            "    from clw_fiscal_calender fc where fc.bus_entity_id = " +
            "?" + " and fc.fiscal_calender_id = tr.fiscal_calender_id )" +
            ", period_end_date = ( select " + "?" +
            "    from clw_fiscal_calender fc where fc.bus_entity_id = " +
            "?" + " and fc.fiscal_calender_id = tr.fiscal_calender_id )" +
            " where budget_period = " + 		"?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?";
	dblog("\tbp ---3:: " + sql);
	PreparedStatement pstmt3 = dbc.prepareStatement(sql);
	pstmt3.setString(1,startmmdd);
	pstmt3.setInt(2,pAcctId);
	pstmt3.setString(3,endmmdd);
	pstmt3.setInt(4,pAcctId);
	pstmt3.setInt(5,pBudgetPeriod);
	pstmt3.setInt(6,pBudgetYear);
	pstmt3.setInt(7,pAcctId);
	pstmt3.executeUpdate();
        pstmt3.close();*/

	sql = " update tclw_acctbudget_report tr" +
            " set period_start_date = ( select fcd.mmdd " +
            "from clw_fiscal_calender fc, clw_fiscal_calender_detail fcd" +
            " where fc.bus_entity_id = " +  pAcctId +
            " and fc.fiscal_calender_id = tr.fiscal_calender_id" +
            " and fc.fiscal_calender_id = fcd.fiscal_calender_id " +
            " and fcd.period="+pBudgetPeriod+" )" +
            ", period_end_date = ( select fcd.mmdd "+
            " from clw_fiscal_calender fc, clw_fiscal_calender_detail fcd " +
            " where fc.bus_entity_id = " +  pAcctId +
            " and fc.fiscal_calender_id = tr.fiscal_calender_id" +
            " and fc.fiscal_calender_id = fcd.fiscal_calender_id " +
            " and fcd.period="+endBudgetPeriod+" )" +
            " where budget_period = " + 		pBudgetPeriod +
            " and budget_year = " + pBudgetYear +
            " and account_id = " + pAcctId;
	dblog("\tbp ---3:: " + sql);
        stmt = dbc.createStatement();
	stmt.executeUpdate(sql);
        stmt.close();
	dbc.commit();

	sql = " update tclw_acctbudget_report tr " +
            " set period_start_date = " +
            " to_date( period_start_date || '/' || budget_year," +
            "   'mm/dd/yyyy')  " +
            ", period_end_date = " +
            " to_date( period_end_date || '/' || budget_year," +
            "   'mm/dd/yyyy') - 1 " +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" ;
	dblog("\tbp ----4:: " + sql);
	dblog("\tbp ----4::1 "+pBudgetPeriod);
	dblog("\tbp ----4::2 "+pBudgetYear);
	dblog("\tbp ----4::3 "+pAcctId);
	if(pstmt4 == null){
		pstmt4 = dbc.prepareStatement(sql);
	}
	pstmt4.setInt(1,pBudgetPeriod);
	pstmt4.setInt(2,pBudgetYear);
	pstmt4.setInt(3,pAcctId);
	pstmt4.executeUpdate();
        pstmt4.close();

	sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
	    " period_start_date =  " +
	    " to_char(to_date( period_start_date)  , 'mm/dd'), " +
	    " period_end_date =  " +
	    " to_char(to_date( period_end_date), 'mm/dd') " +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" ;
	dblog("\tbp -----5:: " + sql);
	if(pstmt5 == null){
		pstmt5 = dbc.prepareStatement(sql);
	}
	pstmt5.setInt(1,pBudgetPeriod);
	pstmt5.setInt(2,pBudgetYear);
	pstmt5.setInt(3,pAcctId);
	pstmt5.executeUpdate();
        pstmt5.close();
	dbc.commit();

	sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
	    " tr.amount_spent =  " +
	    " ( select sum(amount) from clw_SITE_LEDGER sl " +
	    " , clw_order o " +
	    " where sl.SITE_ID = tr.site_id " +
	    " and sl.order_id = o.order_id  " +
	    " and o.order_status_cd in " +
	    " ('Ordered', 'Invoiced', 'ERP Released') " +
            " and (o.order_budget_type_cd not in ('NON_APPLICABLE', 'REBILL') or o.order_budget_type_cd is null)" +
	    " and sl.COST_CENTER_ID = tr.COST_CENTER_ID " +
	    " and sl.BUDGET_YEAR = tr.BUDGET_YEAR " +
	    " and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD )" +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and entry_type = 'SITE'"   ;

	dblog("\tbp ------6:: " + sql);
	if(pstmt6 == null){
		pstmt6 = dbc.prepareStatement(sql);
	}
	pstmt6.setInt(1,pBudgetPeriod);
	pstmt6.setInt(2,pBudgetYear);
	pstmt6.setInt(3,pAcctId);
	pstmt6.executeUpdate();
        pstmt6.close();
	dbc.commit();
	checkAmountSpent(pBudgetYear,pBudgetPeriod,pAcctId );

        sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
	    " tr.amount_spent =  (NVL(tr.amount_spent,0) + " +
	    " NVL(( select sum(NVL(amount,0)) from clw_SITE_LEDGER sl " +
	    " , clw_bus_entity_assoc ba where ba.bus_entity2_id = "
	    + "?" + " and sl.SITE_ID = tr.site_id " +
	    " and sl.site_id = ba.bus_entity1_id " +
	    " and sl.COST_CENTER_ID = tr.COST_CENTER_ID " +
	    " and sl.BUDGET_YEAR = tr.BUDGET_YEAR " +
	    " and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD " +
	    " and sl.entry_type_cd = 'PRIOR PERIOD BUDGET ACTUAL'),0))" +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and entry_type = 'SITE'"   ;

	dblog("\tbp ------6a:: " + sql);
	if(pstmt6a == null){
		pstmt6a = dbc.prepareStatement(sql);
	}
	pstmt6a.setInt(1,pAcctId);
	pstmt6a.setInt(2,pBudgetPeriod);
	pstmt6a.setInt(3,pBudgetYear);
	pstmt6a.setInt(4,pAcctId);
	pstmt6a.executeUpdate();
        pstmt6a.close();
	dbc.commit();
	checkAmountSpent(pBudgetYear,pBudgetPeriod,pAcctId );

        //--------------reflect consolidated orders  for entry_type = SITE ------------------
        addAmountSpentConsolidatedOrders(pBudgetYear, pBudgetPeriod, pAcctId, "SITE", "6b");
        //-----------------------------------------------------------------------------------

        sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
	    " tr.amount_spent =  " +
	    " ( select sum(amount) from clw_SITE_LEDGER sl " +
	    " , clw_order o " +
	    " where o.ACCOUNT_ID = " + "?" +
	    " and sl.order_id = o.order_id  " +
	    " and o.order_status_cd in " +
	    " ('Ordered', 'Invoiced', 'ERP Released') " +
            " and (o.order_budget_type_cd not in ('NON_APPLICABLE', 'REBILL') or o.order_budget_type_cd is null)" +
	    " and sl.COST_CENTER_ID = tr.COST_CENTER_ID " +
	    " and sl.BUDGET_YEAR = tr.BUDGET_YEAR " +
	    " and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD )" +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and entry_type = 'ACCOUNT'"   ;

	dblog("\tbp ------7:: " + sql);
	if(pstmt7 == null){
		pstmt7 = dbc.prepareStatement(sql);
	}
	pstmt7.setInt(1,pAcctId);
	pstmt7.setInt(2,pBudgetPeriod);
	pstmt7.setInt(3,pBudgetYear);
	pstmt7.setInt(4,pAcctId);
	pstmt7.executeUpdate();
        pstmt7.close();
	dbc.commit();
	checkAmountSpent(pBudgetYear,pBudgetPeriod,pAcctId );

        sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
	    " tr.amount_spent =  (NVL(tr.amount_spent,0) + " +
	    " NVL(( select sum(NVL(amount,0)) from clw_SITE_LEDGER sl " +
	    "  where sl.order_id is null and " +
            "    sl.SITE_ID in ( select bus_entity1_id " +
            "    from clw_bus_entity_assoc ba where " +
            "    ba.bus_entity2_id = " + "?" + " ) " +
	    " and sl.COST_CENTER_ID = tr.COST_CENTER_ID " +
	    " and sl.BUDGET_YEAR = tr.BUDGET_YEAR " +
	    " and sl.BUDGET_PERIOD = tr.BUDGET_PERIOD " +
	    " and sl.entry_type_cd = 'PRIOR PERIOD BUDGET ACTUAL'),0))" +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and entry_type = 'ACCOUNT'"   ;

	dblog("\tbp ------7a:: " + sql);
	if(pstmt7a == null){
		pstmt7a = dbc.prepareStatement(sql);
	}
	pstmt7a.setInt(1,pAcctId);
	pstmt7a.setInt(2,pBudgetPeriod);
	pstmt7a.setInt(3,pBudgetYear);
	pstmt7a.setInt(4,pAcctId);
	pstmt7a.executeUpdate();
        pstmt7a.close();
	dbc.commit();

	checkAmountSpent(pBudgetYear,pBudgetPeriod,pAcctId );

        //--------------reflect consolidated orders for entry_type = ACCOUNT--------------------
        addAmountSpentConsolidatedOrders(pBudgetYear, pBudgetPeriod, pAcctId, "ACCOUNT", "7b");
        //--------------------------------------------------------------------------------------

/*sql = "update tclw_acctbudget_report tr  set " +
	    " tr.amount_allocated =  " +
	    " ( select nvl(max(amount" + "?" + "),0)" +
	    " from clw_budget sb " +
	    " where sb.bus_entity_ID = tr.site_id " +
	    " and sb.COST_CENTER_ID = tr.COST_CENTER_ID )" +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and cost_center_type_cd = 'SITE BUDGET'" +
            " and entry_type = 'SITE'"   ;

	dblog("\tbp -------8:: " + sql);
	PreparedStatement pstmt8 = dbc.prepareStatement(sql);
	pstmt8.setInt(1,pBudgetPeriod));
	pstmt8.setInt(2,pBudgetPeriod));
	pstmt8.setInt(3,pBudgetYear));
	pstmt8.setInt(4,pAcctId));
	ret = pstmt8.executeUpdate();
        pstmt8.close();
	dblog("\tbp -------8:: ret=" + ret );*/

	sql = "update tclw_acctbudget_report tr  set " +
	    " tr.amount_allocated =  " +
	    " ( select nvl(max(sbd.amount),0)" +
	    " from clw_budget sb, clw_budget_detail sbd " +
	    " where sb.bus_entity_ID = tr.site_id  " +
        " and sb.BUDGET_ID = sbd.BUDGET_ID" +
        " and sb.budget_year = " + pBudgetYear +
        " and sb.budget_status_cd='ACTIVE'" +
        " and sb.budget_type_cd = 'SITE BUDGET'"+
        " and sbd.period = "+pBudgetPeriod +
        " and sb.COST_CENTER_ID = tr.COST_CENTER_ID )" +
            " where budget_period = " + pBudgetPeriod +
            " and budget_year = " + pBudgetYear +
            " and account_id = " + pAcctId +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and cost_center_type_cd = 'SITE BUDGET'" +
            " and entry_type = 'SITE'"   ;

	dblog("\tbp -------8:: " + sql);
        stmt = dbc.createStatement();
	ret = stmt.executeUpdate(sql);
        stmt.close();
	dblog("\tbp -------8:: ret=" + ret );

	dbc.commit();


/*
sql = "update tclw_acctbudget_report tr  set " +
	    " tr.amount_allocated =  " +
	    " ( select nvl(max(amount" + "?" + "),0)" +
	    " from clw_budget sb " +
	    " where sb.bus_entity_ID = "+"?"+
	    " and sb.COST_CENTER_ID = tr.COST_CENTER_ID )" +
            " where budget_period = " + "?" +
            " and budget_year = " + "?" +
            " and account_id = " + "?" +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and entry_type = 'ACCOUNT'";

	dblog("\tbp           -------8a:: " + sql);
	PreparedStatement pstmt8a = dbc.prepareStatement(sql);
	pstmt8a.setInt(1,pBudgetPeriod));
	pstmt8a.setInt(2,pAcctId));
	pstmt8a.setInt(3,pBudgetPeriod));
	pstmt8a.setInt(4,pBudgetYear));
	pstmt8a.setInt(5,pAcctId));
	ret = pstmt8.executeUpdate();
        pstmt8a.close();
	*/
        sql = "update tclw_acctbudget_report tr  set " +
	    " tr.amount_allocated =  " +
	    " ( select nvl(max(sbd.amount),0)" +
	    " from clw_budget sb, clw_budget_detail sbd " +
	    " where sb.bus_entity_ID = "+pAcctId+
        " and sb.BUDGET_ID = sbd.BUDGET_ID" +
        " and sb.budget_status_cd='ACTIVE'" +
        " and sb.budget_type_cd = 'ACCOUNT BUDGET'"+
        " and sbd.period = "+pBudgetPeriod +
        " and sb.COST_CENTER_ID = tr.COST_CENTER_ID )" +
            " where budget_period = " + pBudgetPeriod +
            " and budget_year = " + pBudgetYear +
            " and account_id = " + pAcctId +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and entry_type = 'ACCOUNT'";

	dblog("\tbp           -------8a:: " + sql);
        stmt = dbc.createStatement();
	ret = stmt.executeUpdate(sql);
        stmt.close();
	dblog("\tbp           --------8a:: ret=" + ret);

	dbc.commit();

	sql = "delete /*+FULL(tr) */from tclw_acctbudget_report tr " +
	    " where tr.amount_allocated is null  " +
	    " and budget_period = " + "?" +
	    " and budget_year = " + "?" +
            " and trim(period_start_date) is null"
            + " and account_id = " + "?" ;
	dblog("\tbp           --------9:: " + sql);
	if(pstmt9 == null){
		pstmt9 = dbc.prepareStatement(sql);
	}
	pstmt9.setInt(1,pBudgetPeriod);
	pstmt9.setInt(2,pBudgetYear);
	pstmt9.setInt(3,pAcctId);
	ret = pstmt9.executeUpdate();
	dblog("\tbp           --------9:: ret=" + ret);
        pstmt9.close();
	dbc.commit();

    }

    HashMap numberOfBudgetPeriodsMap = new HashMap();

    /**
     *Based off the fiscal calendar defined for the account and the year
     *will return the number of budget periods.
     */
    private int getNumberOfBudgetPeriods(int pYear, int pAccountId) throws SQLException{
        String key = pYear + "." + pAccountId;
        Integer numberOfBudgetPeriods = (Integer) numberOfBudgetPeriodsMap.get(key);
        if(numberOfBudgetPeriods != null){
            return numberOfBudgetPeriods.intValue();
        }

        List mmddList = new ArrayList();

        String sqlbase = "SELECT MMDD FROM CLW_FISCAL_CALENDER_DETAIL WHERE FISCAL_CALENDER_ID = (\n" +
                             "SELECT FISCAL_CALENDER_ID FROM CLW_FISCAL_CALENDER WHERE ";
        sqlbase = sqlbase+ " BUS_ENTITY_ID = "+pAccountId;

        String sqla = sqlbase +  " AND fiscal_year = "+pYear+") ORDER BY PERIOD";
        String sqlb = sqlbase +  " AND (fiscal_year IS NULL or fiscal_year = 0)) ORDER BY PERIOD";

        Statement stmt = dbc.createStatement();

        dblog(sqla);
        ResultSet rs = stmt.executeQuery(sqla);
        while (rs.next()) {
             mmddList.add(rs.getString(1));
        }

        if(mmddList.isEmpty()){

            dblog(sqlb);
            rs = stmt.executeQuery(sqlb);
            while (rs.next()) {
                mmddList.add(rs.getString(1));
            }

            if(mmddList.isEmpty()){
                dblog("Error, no fiscal calendar for year: "+pYear+" and account: "+pAccountId);
                //default to 12 fiscal periods
		        rs.close();
                stmt.close();
                return 12;
            }
        }

        int ct = 0;
        Iterator it = mmddList.iterator();
        while (it.hasNext()) {
            String mmdd = (String) it.next();
            if (mmdd != null) {
                ct++;
            }
        }

        numberOfBudgetPeriodsMap.put(key,new Integer(ct));
	    rs.close();
        stmt.close();

        return ct;
    }

    private void exec() {
		try {
			String accountId = System.getProperty("acctid");
                        String  year = System.getProperty("year");
                        String  debug = System.getProperty("debug");
                        String cmd = System.getProperty("cmd");
			String optPeriod = System.getProperty("period");


			dbc = DBHelper.getDbConnection();
			Statement stmt = dbc.createStatement();

			if (null == cmd)
				cmd = "";

			System.out.println("cmd=" + cmd);
			if (cmd.equals("reset_ledger")) {
				// resetLedger();
				throw new RuntimeException("Run reset ledger disabled");
			} else {
				if (year == null || year.length() == 0) {
					year = "";
				}
				if (debug == null || debug.length() == 0) {
					mDebug = true;
				} else {
					mDebug = debug.equals("y");
				}

				ArrayList accts = new ArrayList();
				if (accountId == null || accountId.length() == 0) {
					// Find all accounts in the cost center table.
					accts = getAllAccountsToRun();
				} else {
					accts.add(accountId);
				}

				boolean remakeIdx = false;
				for (int acctIdx = 0; accts != null && acctIdx < accts.size(); acctIdx++) {
					String acctId = (String) accts.get(acctIdx);
					String acctBudgetYear = "";
					if (year.length() == 0) {
						// no budget year was specified.
						// compute the latest budget year for the
						// account.
						int currBudgetYear = getCurrentBudgetYear(acctId);
						if (currBudgetYear > 0) {
							acctBudgetYear = String.valueOf(currBudgetYear);
						}
					} else { // parameter "year" (budget year) was passed as an argument from the command line 
						
						//int currBudgetYear = getCurrentBudgetYear(acctId); 
						//acctBudgetYear = String.valueOf(currBudgetYear);  
						acctBudgetYear = year;  
					}

					if(optPeriod != null){
						int bpi = Integer.parseInt(optPeriod);
						remakeIdx = true;
						System.out.println("\nSTART === Account id=" + acctId + " budget period=" + bpi + " year=" + acctBudgetYear + " " + new java.util.Date());
                        int numberOfBudgetPeriods = getNumberOfBudgetPeriods(Integer.parseInt(acctBudgetYear), Integer.parseInt(acctId));
                        bp(Integer.parseInt(acctBudgetYear), bpi, Integer.parseInt(acctId),numberOfBudgetPeriods);
						System.out.println("END   === Account id=" + acctId+ " budget period=" + bpi + " year="+ acctBudgetYear + " " + new java.util.Date());
					}else{
                        int numberOfBudgetPeriods = getNumberOfBudgetPeriods(Integer.parseInt(acctBudgetYear), Integer.parseInt(acctId));
                        for (int bpi = 1; acctBudgetYear.trim().length() > 0 && Integer.parseInt(acctBudgetYear) > 0 && bpi <= numberOfBudgetPeriods; bpi++) {
                            remakeIdx = true;
                            System.out.println("\nSTART === Account id=" + acctId + " budget period=" + bpi + " year=" + acctBudgetYear + " " + new java.util.Date());
                            bp(Integer.parseInt(acctBudgetYear), bpi, Integer.parseInt(acctId),numberOfBudgetPeriods);
                            System.out.println("END   === Account id=" + acctId+ " budget period=" + bpi + " year="+ acctBudgetYear + " " + new java.util.Date());
                        }
					}
				}

				if (remakeIdx) {
					recreateIndexes();
				}

			}
			stmt.close();


                        String delNoBudgetSql = "delete from TCLW_ACCTBUDGET_REPORT where COST_CENTER_ID in (select COST_CENTER_ID from CLW_COST_CENTER where NO_BUDGET = 'true')";
                        stmt = dbc.createStatement();
                        dblog(delNoBudgetSql);
                        stmt.executeUpdate(delNoBudgetSql);
                        stmt.close();
                        dbc.commit();

			dbc.close();
		} catch (Exception e) {
			System.out.println("DB error: ");
			e.printStackTrace();
		}
	}

    private void recreateIndexes()
        throws Exception
    {
        dropIndexes();
        mkIndexes();
    }

    private int getCurrentBudgetYear(String pAccountId)
        throws Exception
    {
        String sql = " select nvl(fiscal_year, "
	    + " to_number(to_char(sysdate, 'yyyy'))) "
	    + " from clw_fiscal_calender "
	    + " where eff_date < sysdate "
	    + " and bus_entity_id = " + pAccountId + " order by eff_date desc";

	int ret = 0;
Statement stmt = dbc.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) 	    {
	    ret = rs.getInt(1);
	}
    if(ret == 0){
        //default to the current year
        //java.text.SimpleDateFormat frmt = new java.text.SimpleDateFormat("yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        ret = cal.get(java.util.Calendar.YEAR);
    }
	System.out.println( "------- getCurrentBudgetYear =" + ret
			    + " pAccountId=" + pAccountId );
	rs.close();
        stmt.close();
	return ret;
    }

    private void dropIndexes()
        throws Exception
    {
Statement stmt = dbc.createStatement();
        String sql = " select distinct index_name from "
            + " all_ind_columns where "
            + " table_name = 'TCLW_ACCTBUDGET_REPORT'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next())
	    {
                try{
                    String dropidx = " drop index " + rs.getString(1);
 //                   dblog( "  dropidx=" + dropidx );
                    System.out.println("  dropidx=" + dropidx );
                    Statement s1 = dbc.createStatement();
                    s1.executeUpdate(dropidx);
                    s1.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
	    }
        stmt.close();
        rs.close();
    }

    private void mkIndexes()
        throws Exception
    {
Statement  stmt = dbc.createStatement();
        String createIdx = " create index "
            + " T_SITE_ID_I "
            + " on tclw_acctbudget_report "
            + " (site_id) ";
        dblog( "  createIdx=" + createIdx );
        stmt.executeUpdate(createIdx);
        stmt.close();


        createIdx = " create index "
            + " T_BUDGET_PERIOD_I "
            + " on tclw_acctbudget_report "
            + " (budget_period) ";
        dblog( "  createIdx=" + createIdx );
        stmt = dbc.createStatement();
        stmt.executeUpdate(createIdx);
        stmt.close();


        createIdx = " create index "
            + " T_BUDGET_YEAR_I "
            + " on tclw_acctbudget_report "
            + " (budget_year) ";
        dblog( "  createIdx=" + createIdx );
        stmt = dbc.createStatement();
        stmt.executeUpdate(createIdx);
        stmt.close();

        return;
    }
    //--------------reflect ammount for consolidated orders --------------------
    private void addAmountSpentConsolidatedOrders(int pBudgetYear, int pBudgetPeriod,int pAcctId, String pEntry, String pStep) throws Exception {
        PreparedStatement pstmt = null;  //to reflect consolidated orders (entry = 'SITE')

        String sql = "update /*+FULL(tr) */tclw_acctbudget_report tr  set " +
            " tr.amount_spent = ( NVL (tr.amount_spent, 0) + NVL( ( select sum(NVL(amount,0)) " +
            " FROM CLW_SITE_LEDGER sl, CLW_ORDER," +
            "      CLW_ORDER_ASSOC oa, CLW_ORDER o2" +
            " WHERE  " +
            (("SITE".equals(pEntry)) ? (" sl.SITE_ID = tr.site_id ") : ("CLW_ORDER.ACCOUNT_ID = " + pAcctId) )+
            "     AND   sl.ORDER_ID = CLW_ORDER.ORDER_ID" +
            "     AND CLW_ORDER.ORDER_STATUS_CD = 'Cancelled'" +
            "     AND CLW_ORDER.ORDER_TYPE_CD = 'TO_BE_CONSOLIDATED'" +
            "     AND CLW_ORDER.ORDER_ID = oa.ORDER1_ID" +
            "     AND o2.ORDER_ID = oa.ORDER2_ID" +
            "     AND oa.ORDER_ASSOC_CD = 'CONSOLIDATED'" +
            "     AND o2.ORDER_STATUS_CD IN ('Ordered', 'Invoiced', 'ERP Released', 'Process ERP PO')" +
            "     AND (CLW_ORDER.ORDER_BUDGET_TYPE_CD not in ('NON_APPLICABLE', 'REBILL') " +
            "          OR CLW_ORDER.ORDER_BUDGET_TYPE_CD IS NULL)  " +
            "                AND sl.COST_CENTER_ID = tr.COST_CENTER_ID" +
            "                AND sl.BUDGET_YEAR = tr.BUDGET_YEAR" +
            "      AND sl.BUDGET_PERIOD = tr.BUDGET_PERIOD) ,0)) " +
            " where budget_period = " + pBudgetPeriod +
            " and budget_year = " + pBudgetYear +
            " and account_id = " + pAcctId +
            " and trim(period_end_date) is not null" +
            " and trim(period_start_date) is not null" +
            " and entry_type = '" + pEntry + "'"   ;

        dblog("\tbp ------" + pStep+":: " + sql);
        pstmt = dbc.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        dbc.commit();
        checkAmountSpent(pBudgetYear,pBudgetPeriod,pAcctId );
      //-----------------------------------------------------------------

     }

}

