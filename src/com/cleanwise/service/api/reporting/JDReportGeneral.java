/**
 * JDReportGeneral.java
 */
package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.Collections;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.util.Utility;
/**
 * @author Ssharma
 *
 */
public class JDReportGeneral implements GenericReportMulti {

	public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
    throws Exception
    {

    	Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        ArrayList jdGeneral = new ArrayList();
        ArrayList jdGeneralWithDist = new ArrayList();
        HashMap generalMap = new HashMap();
        HashMap generalWithDistMap = new HashMap();


        String forYear = (String) pParams.get("FOR_YEAR");
        int curYear = Integer.parseInt(forYear);
        int prevYear= curYear-1;

        String jdGeneralWithDistSql=
        	"SELECT "+
        	"oi.dist_item_short_desc as dist, "+
        	"b.short_desc as acc, "+
        	"NVL(prop.clw_value,b.short_desc) AS endUserGrp, "+
        	"SUM(oi.manu_item_msrp)*0.455 as price,"+
        	"TO_CHAR(o.original_order_date,'MM') as theMonth, "+
        	"TO_CHAR(o.original_order_date,'YYYY') as theYear "+
        	"FROM "+
        	"(SELECT clw_value,bus_entity_id FROM clw_property WHERE short_desc ='Super Account Name')prop,"+
        	"CLW_BUS_ENTITY b,CLW_ORDER o, CLW_ORDER_ITEM oi,clw_item i "+
        	"WHERE "+
        	"o.store_id=1 "+
        	"AND b.bus_entity_id=prop.bus_entity_id(+) "+
        	"AND TO_CHAR(o.original_order_date,'YYYY') in ('"+curYear+"','"+prevYear+"') "+
        	"AND o.account_id=b.bus_entity_id "+
        	"AND o.order_id=oi.order_id "+
        	"AND o.order_status_cd IN ('ERP Released','Invoiced') "+
        	"AND i.item_status_cd='ACTIVE' "+
        	"AND oi.item_id=i.item_id "+
        	"AND oi.order_item_status_cd NOT IN ('CANCELLED') "+
        	"AND oi.item_id IN "+
        	"(SELECT item_id FROM CLW_GROUP_ASSOC ga, CLW_ITEM_MAPPING im WHERE "+
        	"im.bus_entity_Id =ga.bus_entity_Id AND ga.group_id = 412 "+
        	"AND im.item_mapping_cd='ITEM_MANUFACTURER') "+
        	"GROUP BY TO_CHAR(o.original_order_date,'MM'),TO_CHAR(o.original_order_date,'YYYY'),NVL(prop.clw_value,b.short_desc),"+
        	"oi.dist_item_short_desc,b.short_desc";


        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(jdGeneralWithDistSql);

        JDGeneralDetail totRecPrev1 = new JDGeneralDetail();
        JDGeneralDetail totRecCurr1 = new JDGeneralDetail();
        JDGeneralWithDistDetail totRecPrev2 = new JDGeneralWithDistDetail();
        JDGeneralWithDistDetail totRecCurr2 = new JDGeneralWithDistDetail();

        while(rs.next()){

        	String distributor = rs.getString("dist");
        	String account = rs.getString("acc");
        	String grp = rs.getString("endUserGrp");
        	BigDecimal price = rs.getBigDecimal("price");
        	String myMonth = rs.getString("theMonth");
        	String myYear = rs.getString("theYear");
        	int y = Integer.parseInt(myYear);

        	// General with Dist

        	String key = distributor+":"+account+":"+grp+":"+myYear;
        	JDGeneralWithDistDetail val1 = (JDGeneralWithDistDetail)generalWithDistMap.get(key);
        	if(val1 == null){
        		val1 = new JDGeneralWithDistDetail();
        		val1.distName = distributor;
        		val1.accName = account;
        		val1.groupName = grp;
        		val1.year = myYear;
        		generalWithDistMap.put(key, val1);
        	}

       //	mapping exists
        	if(myMonth.startsWith("01")){
        		val1.price1 = Utility.addAmt(val1.price1, price);
        		if(y == prevYear){
        			totRecPrev2.price1 = Utility.addAmt(totRecPrev2.price1, price);
        		}else{
        			totRecCurr2.price1 = Utility.addAmt(totRecCurr2.price1, price);
        		}
        	}else if(myMonth.startsWith("02")){
        		val1.price2 = Utility.addAmt(val1.price2, price);
        		if(y == prevYear){
        			totRecPrev2.price2 = Utility.addAmt(totRecPrev2.price2, price);
        		}else{
        			totRecCurr2.price2 = Utility.addAmt(totRecCurr2.price2, price);
        		}
        	}else if(myMonth.startsWith("03")){
        		val1.price3 = Utility.addAmt(val1.price3, price);
        		if(y == prevYear){
        			totRecPrev2.price3 = Utility.addAmt(totRecPrev2.price3, price);
        		}else{
        			totRecCurr2.price3 = Utility.addAmt(totRecCurr2.price3, price);
        		}
        	}else if(myMonth.startsWith("04")){
        		val1.price4 = Utility.addAmt(val1.price4, price);
        		if(y == prevYear){
        			totRecPrev2.price4 = Utility.addAmt(totRecPrev2.price4, price);
        		}else{
        			totRecCurr2.price4 = Utility.addAmt(totRecCurr2.price4, price);
        		}
        	}else if(myMonth.startsWith("05")){
        		val1.price5 = Utility.addAmt(val1.price5, price);
        		if(y == prevYear){
        			totRecPrev2.price5 = Utility.addAmt(totRecPrev2.price5, price);
        		}else{
        			totRecCurr2.price5 = Utility.addAmt(totRecCurr2.price5, price);
        		}
        	}else if(myMonth.startsWith("06")){
        		val1.price6 = Utility.addAmt(val1.price6, price);
        		if(y == prevYear){
        			totRecPrev2.price6 = Utility.addAmt(totRecPrev2.price6, price);
        		}else{
        			totRecCurr2.price6 = Utility.addAmt(totRecCurr2.price6, price);
        		}
        	}else if(myMonth.startsWith("07")){
        		val1.price7 = Utility.addAmt(val1.price7, price);
        		if(y == prevYear){
        			totRecPrev2.price7 = Utility.addAmt(totRecPrev2.price7, price);
        		}else{
        			totRecCurr2.price7 = Utility.addAmt(totRecCurr2.price7, price);
        		}
        	}else if(myMonth.startsWith("08")){
        		val1.price8 = Utility.addAmt(val1.price8, price);
        		if(y == prevYear){
        			totRecPrev2.price8 = Utility.addAmt(totRecPrev2.price8, price);
        		}else{
        			totRecCurr2.price8 = Utility.addAmt(totRecCurr2.price8, price);
        		}
        	}else if(myMonth.startsWith("09")){
        		val1.price9 = Utility.addAmt(val1.price9, price);
        		if(y == prevYear){
        			totRecPrev2.price9 = Utility.addAmt(totRecPrev2.price9, price);
        		}else{
        			totRecCurr2.price9 = Utility.addAmt(totRecCurr2.price9, price);
        		}
        	}else if(myMonth.startsWith("10")){
        		val1.price10 = Utility.addAmt(val1.price10, price);
        		if(y == prevYear){
        			totRecPrev2.price10 = Utility.addAmt(totRecPrev2.price10, price);
        		}else{
        			totRecCurr2.price10 = Utility.addAmt(totRecCurr2.price10, price);
        		}
        	}else if(myMonth.startsWith("11")){
        		val1.price11 = Utility.addAmt(val1.price11, price);
        		if(y == prevYear){
        			totRecPrev2.price11 = Utility.addAmt(totRecPrev2.price11, price);
        		}else{
        			totRecCurr2.price11 = Utility.addAmt(totRecCurr2.price11, price);
        		}
        	}else if(myMonth.startsWith("12")){
        		val1.price12 = Utility.addAmt(val1.price12, price);
        		if(y == prevYear){
        			totRecPrev2.price12 = Utility.addAmt(totRecPrev2.price12, price);
        		}else{
        			totRecCurr2.price12 = Utility.addAmt(totRecCurr2.price12, price);
        		}
        	}
        	generalWithDistMap.put(key, val1);


        	// General with EndUserGrp
        	String key2 = grp+":"+myYear;
        	JDGeneralDetail val2 = (JDGeneralDetail)generalMap.get(key2);
        	if(val2 == null){
        		val2 = new JDGeneralDetail();

        		val2.groupName = grp;
        		val2.year = myYear;
        		generalMap.put(key2, val2);
        	}

        	//	mapping exists
        	if(myMonth.startsWith("01")){
        		val2.price1 = Utility.addAmt(val2.price1, price);
        		if(y == prevYear){
        			totRecPrev1.price1 = Utility.addAmt(totRecPrev1.price1, price);
        		}else{
        			totRecCurr1.price1 = Utility.addAmt(totRecCurr1.price1, price);
        		}
        	}else if(myMonth.startsWith("02")){
        		val2.price2 = Utility.addAmt(val2.price2, price);
        		if(y == prevYear){
        			totRecPrev1.price2 = Utility.addAmt(totRecPrev1.price2, price);
        		}else{
        			totRecCurr1.price2 = Utility.addAmt(totRecCurr1.price2, price);
        		}
        	}else if(myMonth.startsWith("03")){
        		val2.price3 = Utility.addAmt(val2.price3, price);
        		if(y == prevYear){
        			totRecPrev1.price3 = Utility.addAmt(totRecPrev1.price3, price);
        		}else{
        			totRecCurr1.price3 = Utility.addAmt(totRecCurr1.price3, price);
        		}
        	}else if(myMonth.startsWith("04")){
        		val2.price4 = Utility.addAmt(val2.price4, price);
        		if(y == prevYear){
        			totRecPrev1.price4 = Utility.addAmt(totRecPrev1.price4, price);
        		}else{
        			totRecCurr1.price4 = Utility.addAmt(totRecCurr1.price4, price);
        		}
        	}else if(myMonth.startsWith("05")){
        		val2.price5 = Utility.addAmt(val2.price5, price);
        		if(y == prevYear){
        			totRecPrev1.price5 = Utility.addAmt(totRecPrev1.price5, price);
        		}else{
        			totRecCurr1.price5 = Utility.addAmt(totRecCurr1.price5, price);
        		}
        	}else if(myMonth.startsWith("06")){
        		val2.price6 = Utility.addAmt(val2.price6, price);
        		if(y == prevYear){
        			totRecPrev1.price6 = Utility.addAmt(totRecPrev1.price6, price);
        		}else{
        			totRecCurr1.price6 = Utility.addAmt(totRecCurr1.price6, price);
        		}
        	}else if(myMonth.startsWith("07")){
        		val2.price7 = Utility.addAmt(val2.price7, price);
        		if(y == prevYear){
        			totRecPrev1.price7 = Utility.addAmt(totRecPrev1.price7, price);
        		}else{
        			totRecCurr1.price7 = Utility.addAmt(totRecCurr1.price7, price);
        		}
        	}else if(myMonth.startsWith("08")){
        		val2.price8 = Utility.addAmt(val2.price8, price);
        		if(y == prevYear){
        			totRecPrev1.price8 = Utility.addAmt(totRecPrev1.price8, price);
        		}else{
        			totRecCurr1.price8 = Utility.addAmt(totRecCurr1.price8, price);
        		}
        	}else if(myMonth.startsWith("09")){
        		val2.price9 = Utility.addAmt(val2.price9, price);
        		if(y == prevYear){
        			totRecPrev1.price9 = Utility.addAmt(totRecPrev1.price9, price);
        		}else{
        			totRecCurr1.price9 = Utility.addAmt(totRecCurr1.price9, price);
        		}
        	}else if(myMonth.startsWith("10")){
        		val2.price10 = Utility.addAmt(val2.price10, price);
        		if(y == prevYear){
        			totRecPrev1.price10 = Utility.addAmt(totRecPrev1.price10, price);
        		}else{
        			totRecCurr1.price10 = Utility.addAmt(totRecCurr1.price10, price);
        		}
        	}else if(myMonth.startsWith("11")){
        		val2.price11 = Utility.addAmt(val2.price11, price);
        		if(y == prevYear){
        			totRecPrev1.price11 = Utility.addAmt(totRecPrev1.price11, price);
        		}else{
        			totRecCurr1.price11 = Utility.addAmt(totRecCurr1.price11, price);
        		}
        	}else if(myMonth.startsWith("12")){
        		val2.price12 = Utility.addAmt(val2.price12, price);
        		if(y == prevYear){
        			totRecPrev1.price12 = Utility.addAmt(totRecPrev1.price12, price);
        		}else{
        			totRecCurr1.price12 = Utility.addAmt(totRecCurr1.price12, price);
        		}
        	}
        	generalMap.put(key2, val2);

        }

        rs.close();
        stmt.close();

        // get all values from the maps

        Collection col1 = generalMap.values();
        Iterator it1 = col1.iterator();
        while(it1.hasNext()){

        	JDGeneralDetail record1 = new JDGeneralDetail();
        	record1 = (JDGeneralDetail)it1.next();

        	jdGeneral.add(record1);

        }

        Collections.sort(jdGeneral,JDGENERAL_COMPARE );
        totRecPrev1.groupName= prevYear+" Totals";
        jdGeneral.add(totRecPrev1);
        totRecCurr1.groupName= curYear+" Totals";
        jdGeneral.add(totRecCurr1);

        Collection col = generalWithDistMap.values();
        Iterator it = col.iterator();
        while(it.hasNext()){

        	JDGeneralWithDistDetail recordWithDist = new JDGeneralWithDistDetail();
        	recordWithDist = (JDGeneralWithDistDetail)it.next();

        	jdGeneralWithDist.add(recordWithDist);

        }
        Collections.sort(jdGeneralWithDist,JDGENERALWITHDIST_COMPARE );
        totRecPrev2.groupName = prevYear+" Totals";
        jdGeneralWithDist.add(totRecPrev2);
        totRecCurr2.groupName= curYear+" Totals";
        jdGeneralWithDist.add(totRecCurr2);

//      Generate all results

        generateResults(resultV, con,jdGeneral,jdGeneralWithDist);


        return resultV;

    }


	protected void generateResults
	(GenericReportResultViewVector resultV, Connection con,List jdGeneral,
	 List jdGeneralWithDist) throws Exception{

		processList(jdGeneral, resultV, "JDGeneralALLCWByEndUserGroup",getJDGeneralHeader());

		processList(jdGeneralWithDist, resultV, "JDGeneralALLCWByDist",getJDGeneralWithDistHeader());
	}

	protected void processList
	(List toProcess,
	 GenericReportResultViewVector resultV,
	 String name, GenericReportColumnViewVector header)
    {

        Iterator it = toProcess.iterator();
        if(it.hasNext()) {
            GenericReportResultView result =
		GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            while(it.hasNext()) {
                result.getTable().add(((aRecord) it.next()).toList());
            }
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setName(name);
            resultV.add(result);
        }
    }


	private GenericReportColumnViewVector getJDGeneralHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "End User Grouping", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Year", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "JAN", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "FEB", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "MAR", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "APR", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "MAY", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "JUN", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "JUL", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "AUG", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "SEP", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "OCT", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "NOV", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "DEC", 2,20,"NUMBER"));

        return header;
    }

	private GenericReportColumnViewVector getJDGeneralWithDistHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Cleanwise End User", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "End User Grouping", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Year", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "JAN", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "FEB", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "MAR", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "APR", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "MAY", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "JUN", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "JUL", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "AUG", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "SEP", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "OCT", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "NOV", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "DEC", 2,20,"NUMBER"));

        return header;
    }



	private interface aRecord{
        ArrayList toList();
    }

	protected class JDGeneralDetail implements aRecord{
		String groupName;
		String year;
		BigDecimal price1;
		BigDecimal price2;
		BigDecimal price3;
		BigDecimal price4;
		BigDecimal price5;
		BigDecimal price6;
		BigDecimal price7;
		BigDecimal price8;
		BigDecimal price9;
		BigDecimal price10;
		BigDecimal price11;
		BigDecimal price12;


		public ArrayList toList() {

    		ArrayList list = new ArrayList();
    		list.add(groupName);
    		list.add(year);
    		list.add(price1);
    		list.add(price2);
    		list.add(price3);
    		list.add(price4);
    		list.add(price5);
    		list.add(price6);
    		list.add(price7);
    		list.add(price8);
    		list.add(price9);
    		list.add(price10);
    		list.add(price11);
    		list.add(price12);

    		return list;
		}
	}

	protected class JDGeneralWithDistDetail implements aRecord{
		String distName;
		String accName;
		String groupName;
		String year;
		BigDecimal price1;
		BigDecimal price2;
		BigDecimal price3;
		BigDecimal price4;
		BigDecimal price5;
		BigDecimal price6;
		BigDecimal price7;
		BigDecimal price8;
		BigDecimal price9;
		BigDecimal price10;
		BigDecimal price11;
		BigDecimal price12;


		public ArrayList toList() {

    		ArrayList list = new ArrayList();
    		list.add(distName);
    		list.add(accName);
    		list.add(groupName);
    		list.add(year);
    		list.add(price1);
    		list.add(price2);
    		list.add(price3);
    		list.add(price4);
    		list.add(price5);
    		list.add(price6);
    		list.add(price7);
    		list.add(price8);
    		list.add(price9);
    		list.add(price10);
    		list.add(price11);
    		list.add(price12);

    		return list;
		}
	}

	static final Comparator JDGENERAL_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
	    	JDGeneralDetail c1 = (JDGeneralDetail)o1;
	    	JDGeneralDetail c2 = (JDGeneralDetail)o2;
                if(c1.groupName == null){
                    c1.groupName = "";
                }
                if(c2.groupName == null){
                    c2.groupName = "";
                }

                int val = c1.groupName.compareToIgnoreCase(c2.groupName);
                return val;
	    }
	};

	static final Comparator JDGENERALWITHDIST_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
	    	JDGeneralWithDistDetail c1 = (JDGeneralWithDistDetail)o1;
	    	JDGeneralWithDistDetail c2 = (JDGeneralWithDistDetail)o2;
                if(c1.accName == null){
                    c1.accName = "";
                }
                if(c2.accName == null){
                    c2.accName = "";
                }
                if(c1.distName == null){
                    c1.distName = "";
                }
                if(c2.distName == null){
                    c2.distName = "";
                }
                if(c1.groupName == null){
                    c1.groupName = "";
                }
                if(c2.groupName == null){
                    c2.groupName = "";
                }
                int val = c1.distName.compareToIgnoreCase(c2.distName);
                if(val != 0){
                    return val;
                }
                val = c1.accName.compareToIgnoreCase(c2.accName);
                if(val != 0){
                    return val;
                }
                val = c1.groupName.compareToIgnoreCase(c2.groupName);
                return val;
	    }
	};
}
