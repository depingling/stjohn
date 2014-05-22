/**
 * JDReport.java
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
import com.cleanwise.service.api.reporting.JDReportGeneral.JDGeneralWithDistDetail;
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
public class JDReport implements GenericReportMulti  {


	private boolean showDist;
	private boolean jciOnly;

	public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
    throws Exception
    {

    	Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        ArrayList jdRetail = new ArrayList();
        ArrayList jdRetailWithDist = new ArrayList();
        ArrayList jdAll = new ArrayList();
        ArrayList jdJci = new ArrayList();
        HashMap retailMap = new HashMap();
        HashMap retailWithDistMap = new HashMap();
        HashMap allMap = new HashMap();
        HashMap jciMap = new HashMap();

        String forYear = (String) pParams.get("FOR_YEAR");

        String jdRetailSql=
        	"SELECT b.short_desc as acc,"+
        	"oi.dist_item_short_desc as dist, "+
        	"SUM(oi.manu_item_msrp)*0.455 as price,"+
        	"TO_CHAR(o.original_order_date,'MM/YY') as monYear "+
        	"FROM clw_order o, clw_order_item oi, clw_bus_entity b,clw_group_assoc gassoc, clw_group g,clw_item i "+
        	"WHERE "+
        	"o.order_id=oi.order_id "+
        	"AND o.order_status_cd IN ('ERP Released','Invoiced') "+
        	"AND i.item_status_cd='ACTIVE' "+
        	"AND oi.item_id=i.item_id "+
        	"AND oi.order_item_status_cd NOT IN ('CANCELLED') "+
        	"AND b.bus_entity_id IN (o.account_id,o.store_id) "+
        	"AND b.bus_entity_type_cd IN ('ACCOUNT','STORE') "+
        	"AND gassoc.bus_entity_id = b.bus_entity_id "+
        	"AND gassoc.group_id = g.group_id "+
        	"AND g.short_desc IN ('RETAIL_ACCOUNT','RETAIL_STORE') "+
        	"AND oi.item_id IN "+
        	"(SELECT item_id FROM clw_group_assoc ga, clw_item_mapping im WHERE im.bus_entity_Id =ga.bus_entity_Id "+
        	"AND ga.group_id = 412 AND im.item_mapping_cd='ITEM_MANUFACTURER') "+
        	"AND TO_CHAR(o.original_order_date,'YYYY')='"+forYear+"'"+
        	" GROUP BY TO_CHAR(o.original_order_date,'MM/YY'),b.short_desc,oi.dist_item_short_desc";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(jdRetailSql);
        while(rs.next()){

        	String account = rs.getString("acc");
        	String distributor = rs.getString("dist");
        	BigDecimal price = rs.getBigDecimal("price");
        	String my = rs.getString("monYear");

        	// Acc without distributor
        	JDRetailDetail val1 = (JDRetailDetail)retailMap.get(account);
        	if(val1 == null){
        		val1 = new JDRetailDetail();
        		val1.acctName = account;
        		retailMap.put(account, val1);
        	}

        	//mapping exists
        	if(my.startsWith("01")){
        		val1.price1 = Utility.addAmt(val1.price1,price);
        	}else if(my.startsWith("02")){
        		val1.price2 = Utility.addAmt(val1.price2,price);
        	}else if(my.startsWith("03")){
        		val1.price3 = Utility.addAmt(val1.price3,price);
        	}else if(my.startsWith("04")){
        		val1.price4 = Utility.addAmt(val1.price4,price);
        	}else if(my.startsWith("05")){
        		val1.price5 = Utility.addAmt(val1.price5,price);
        	}else if(my.startsWith("06")){
        		val1.price6 = Utility.addAmt(val1.price6,price);
        	}else if(my.startsWith("07")){
        		val1.price7 = Utility.addAmt(val1.price7,price);
        	}else if(my.startsWith("08")){
        		val1.price8 = Utility.addAmt(val1.price8,price);
        	}else if(my.startsWith("09")){
        		val1.price9 = Utility.addAmt(val1.price9,price);
        	}else if(my.startsWith("10")){
        		val1.price10 = Utility.addAmt(val1.price10,price);
        	}else if(my.startsWith("11")){
        		val1.price11 = Utility.addAmt(val1.price11,price);
        	}else if(my.startsWith("12")){
        		val1.price12 = Utility.addAmt(val1.price12,price);
        	}
        	retailMap.put(account,val1);

        	// Acc with Distributor
        	String key = account+"::"+distributor;

        	JDRetailWithDistDetail val = (JDRetailWithDistDetail)retailWithDistMap.get(key);
        	if(val == null){
        		val = new JDRetailWithDistDetail();

        		val.acctName = account;
        		val.dist = distributor;
        		retailWithDistMap.put(key, val);
        	}

        	//mapping exists
        	if(my.startsWith("01")){
        		val.price1 = price;
        	}else if(my.startsWith("02")){
        		val.price2 = price;
        	}else if(my.startsWith("03")){
        		val.price3 = price;
        	}else if(my.startsWith("04")){
        		val.price4 = price;
        	}else if(my.startsWith("05")){
        		val.price5 = price;
        	}else if(my.startsWith("06")){
        		val.price6 = price;
        	}else if(my.startsWith("07")){
        		val.price7 = price;
        	}else if(my.startsWith("08")){
        		val.price8 = price;
        	}else if(my.startsWith("09")){
        		val.price9 = price;
        	}else if(my.startsWith("10")){
        		val.price10 = price;
        	}else if(my.startsWith("11")){
        		val.price11 = price;
        	}else if(my.startsWith("12")){
        		val.price12 = price;
        	}
        	retailWithDistMap.put(key,val);


        }
        rs.close();
        stmt.close();

        // get all values from the maps

        Collection col1 = retailMap.values();
        Iterator it1 = col1.iterator();
        while(it1.hasNext()){

        	JDRetailDetail record1 = new JDRetailDetail();
        	record1 = (JDRetailDetail)it1.next();

        	jdRetail.add(record1);

        }
        Collections.sort(jdRetail,JDRETAIL_COMPARE);

        Collection col = retailWithDistMap.values();
        Iterator it = col.iterator();
        while(it.hasNext()){

        	JDRetailWithDistDetail recordWithDist = new JDRetailWithDistDetail();
        	recordWithDist = (JDRetailWithDistDetail)it.next();

        	jdRetailWithDist.add(recordWithDist);

        }
        Collections.sort(jdRetailWithDist,JDRETAILWITHDIST_COMPARE);

        // Get all accounts (retail+BGE)
        String jdAllSql=
        	"SELECT b.short_desc AS acc, "+
        	"oi.manu_item_sku_num AS manu, "+
        	"SUM(oi.manu_item_msrp)*0.455 AS price,"+
        	"TO_CHAR(o.original_order_date,'MM/YY') AS monYear "+
        	"FROM CLW_BUS_ENTITY b,CLW_GROUP g, CLW_GROUP_ASSOC ga,CLW_ORDER o, CLW_ORDER_ITEM oi,clw_item i "+
        	"WHERE "+
        	"g.group_id=411 "+
        	"AND g.group_id=ga.group_id "+
        	"AND g.group_status_cd='ACTIVE' "+
        	"AND ga.group_assoc_cd='BUS_ENTITY_OF_GROUP' "+
        	"AND ga.bus_entity_id=b.bus_entity_id "+
        	"AND b.bus_entity_type_cd IN ('ACCOUNT','STORE') "+
        	"AND b.bus_entity_id IN (o.account_id,o.store_id) "+
        	"AND TO_CHAR(o.original_order_date,'YYYY')='2007' "+
        	"AND o.order_id=oi.order_id "+
        	"AND o.order_status_cd IN ('ERP Released','Invoiced') "+
        	"AND i.item_status_cd='ACTIVE' "+
        	"AND oi.item_id=i.item_id "+
        	"AND oi.order_item_status_cd NOT IN ('CANCELLED') "+
        	"AND oi.item_id IN "+
        	"(SELECT item_id FROM CLW_GROUP_ASSOC ga, CLW_ITEM_MAPPING im "+
        	"WHERE im.bus_entity_Id =ga.bus_entity_Id "+
        	"AND ga.group_id = 412 AND im.item_mapping_cd='ITEM_MANUFACTURER') "+
        	"GROUP BY TO_CHAR(o.original_order_date,'MM/YY'),b.short_desc,oi.manu_item_sku_num "+
        	"ORDER BY b.short_desc,oi.manu_item_sku_num";

        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(jdAllSql);

        JDAllDetail grandTotRecord = new JDAllDetail();
        BigDecimal gTotal = new BigDecimal(0);
        JDJciDetail grandTotRecordJci = new JDJciDetail();
        BigDecimal gTotalJci = new BigDecimal(0);

        while(rs2.next()){

        	String account = rs2.getString("acc");
        	String manufNum= rs2.getString("manu");
        	BigDecimal price = rs2.getBigDecimal("price");
        	String my = rs2.getString("monYear");

        	JDAllDetail val2 = (JDAllDetail)allMap.get(account);
        	if(val2 == null){
        		val2 = new JDAllDetail();
        		val2.acctName = account;
        		allMap.put(account, val2);
        	}

        	//mapping exists
        	if(my.startsWith("01")){
        		val2.price1 = Utility.addAmt(val2.price1, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price1);
        		grandTotRecord.price1 = Utility.addAmt(grandTotRecord.price1, val2.price1);

        	}else if(my.startsWith("02")){
        		val2.price2 = Utility.addAmt(val2.price2, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price2);
        		grandTotRecord.price2 = Utility.addAmt(grandTotRecord.price2, val2.price2);

        	}else if(my.startsWith("03")){
        		val2.price3 = Utility.addAmt(val2.price3, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price3);
        		grandTotRecord.price3 = Utility.addAmt(grandTotRecord.price3, val2.price3);

        	}else if(my.startsWith("04")){
        		val2.price4 = Utility.addAmt(val2.price4, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price4);
        		grandTotRecord.price4 = Utility.addAmt(grandTotRecord.price4, val2.price4);

        	}else if(my.startsWith("05")){
        		val2.price5 = Utility.addAmt(val2.price5, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price5);
        		grandTotRecord.price5 = Utility.addAmt(grandTotRecord.price5, val2.price5);

        	}else if(my.startsWith("06")){
        		val2.price6 = Utility.addAmt(val2.price6, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price6);
        		grandTotRecord.price6 = Utility.addAmt(grandTotRecord.price6, val2.price6);

        	}else if(my.startsWith("07")){
        		val2.price7 = Utility.addAmt(val2.price7, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price7);
        		grandTotRecord.price7 = Utility.addAmt(grandTotRecord.price7, val2.price7);

        	}else if(my.startsWith("08")){
        		val2.price8 = Utility.addAmt(val2.price8, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price8);
        		grandTotRecord.price8 = Utility.addAmt(grandTotRecord.price8, val2.price8);

        	}else if(my.startsWith("09")){
        		val2.price9 = Utility.addAmt(val2.price9, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price9);
        		grandTotRecord.price9 = Utility.addAmt(grandTotRecord.price9, val2.price9);

        	}else if(my.startsWith("10")){
        		val2.price10 = Utility.addAmt(val2.price10, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price10);
        		grandTotRecord.price10 = Utility.addAmt(grandTotRecord.price10, val2.price10);

        	}else if(my.startsWith("11")){
        		val2.price11 = Utility.addAmt(val2.price11, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price11);
        		grandTotRecord.price11 = Utility.addAmt(grandTotRecord.price11, val2.price11);

        	}else if(my.startsWith("12")){
        		val2.price12 = Utility.addAmt(val2.price12, price);
        		val2.monthTotal = Utility.addAmt(val2.monthTotal, val2.price12);
        		grandTotRecord.price12 = Utility.addAmt(grandTotRecord.price12, val2.price12);

        	}
        	allMap.put(account,val2);
        	gTotal = Utility.addAmt(gTotal, price);

        	// Only JCI
        	if(account.startsWith("JCI")){


	        	String key = account+"::"+manufNum;
	        	JDJciDetail val3 = (JDJciDetail)jciMap.get(key);
	        	if(val3 == null){
	        		val3 = new JDJciDetail();
	        		val3.acctName = account;
	        		val3.manuNum = manufNum;
	        		jciMap.put(key, val3);
	        	}

	        	//mapping exists
	        	if(my.startsWith("01")){
	        		val3.price1 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price1);
	        		grandTotRecordJci.price1 = Utility.addAmt(grandTotRecordJci.price1, val3.price1);

	        	}else if(my.startsWith("02")){
	        		val3.price2 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price2);
	        		grandTotRecordJci.price2 = Utility.addAmt(grandTotRecordJci.price2, val3.price2);

	        	}else if(my.startsWith("03")){
	        		val3.price3 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price3);
	        		grandTotRecordJci.price3 = Utility.addAmt(grandTotRecordJci.price3, val3.price3);

	        	}else if(my.startsWith("04")){
	        		val3.price4 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price4);
	        		grandTotRecordJci.price4 = Utility.addAmt(grandTotRecordJci.price4, val3.price4);

	        	}else if(my.startsWith("05")){
	        		val3.price5 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price5);
	        		grandTotRecordJci.price5 = Utility.addAmt(grandTotRecordJci.price5, val3.price5);

	        	}else if(my.startsWith("06")){
	        		val3.price6 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price6);
	        		grandTotRecordJci.price6 = Utility.addAmt(grandTotRecordJci.price6, val3.price6);

	        	}else if(my.startsWith("07")){
	        		val3.price7 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price7);
	        		grandTotRecordJci.price7 = Utility.addAmt(grandTotRecordJci.price7, val3.price7);

	        	}else if(my.startsWith("08")){
	        		val3.price8 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price8);
	        		grandTotRecordJci.price8 = Utility.addAmt(grandTotRecordJci.price8, val3.price8);

	        	}else if(my.startsWith("09")){
	        		val3.price9 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price9);
	        		grandTotRecordJci.price9 = Utility.addAmt(grandTotRecordJci.price9, val3.price9);

	        	}else if(my.startsWith("10")){
	        		val3.price10 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price10);
	        		grandTotRecordJci.price10 = Utility.addAmt(grandTotRecordJci.price10, val3.price10);

	        	}else if(my.startsWith("11")){
	        		val3.price11 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price11);
	        		grandTotRecordJci.price11 = Utility.addAmt(grandTotRecordJci.price11, val3.price11);

	        	}else if(my.startsWith("12")){
	        		val3.price12 = price;
	        		val3.monthTotal = Utility.addAmt(val3.monthTotal, val3.price12);
	        		grandTotRecordJci.price12 = Utility.addAmt(grandTotRecordJci.price12, val3.price12);
	        	}
	        	jciMap.put(key,val3);
	        	gTotalJci = Utility.addAmt(gTotalJci, price);

        	}

        }

        grandTotRecord.acctName = "GrandTotal";
        grandTotRecord.monthTotal = gTotal;
        grandTotRecordJci.monthTotal = gTotalJci;
        rs2.close();
        stmt2.close();

        // get all values from the maps

        Collection col2 = allMap.values();
        Iterator it2 = col2.iterator();
        while(it2.hasNext()){

        	JDAllDetail record2 = new JDAllDetail();
        	record2 = (JDAllDetail)it2.next();

        	jdAll.add(record2);

        }
        Collections.sort(jdAll,JDALL_COMPARE);
        jdAll.add(grandTotRecord);

        Collection col3 = jciMap.values();
        Iterator it3 = col3.iterator();
        while(it3.hasNext()){

        	JDJciDetail record3 = new JDJciDetail();
        	record3 = (JDJciDetail)it3.next();

        	jdJci.add(record3);

        }
        Collections.sort(jdJci,JDJCI_COMPARE);
        jdJci.add(grandTotRecordJci);



        // Generate all results
        generateResults(resultV, con,jdRetail,jdRetailWithDist,jdAll,jdJci,forYear);


        return resultV;

    }


	protected void generateResults
	(GenericReportResultViewVector resultV, Connection con,List jdRetail,
	 List jdRetailWithDist,List jdAll,List jdJci, String forYear) throws Exception{

		processList(jdRetail, resultV, "JDRetailAcc",getJDRetailHeader(forYear));

		processList(jdRetailWithDist, resultV, "JDRetailAccWithDist",getJDRetailWithDistHeader(forYear));

		processList(jdAll, resultV, "JDAllAcc",getJDAllHeader(forYear));

		processList(jdJci, resultV, "JDJciAcc",getJDJciHeader(forYear));
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



	private GenericReportColumnViewVector getJDRetailHeader(String forYear) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        /*if(showDist){
        	header.add(ReportingUtils.createGenericReportColumnView("java.math.String","JWP Dist",0, 255, "VARCHAR2"));
        }*/
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "01/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "02/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "03/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "04/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "05/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "06/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "07/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "08/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "09/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "10/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "11/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "12/"+forYear, 2,20,"NUMBER"));

        return header;
    }

	private GenericReportColumnViewVector getJDRetailWithDistHeader(String forYear) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.String","JWP Dist",0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "01/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "02/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "03/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "04/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "05/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "06/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "07/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "08/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "09/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "10/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "11/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "12/"+forYear, 2,20,"NUMBER"));

        return header;
    }

	private GenericReportColumnViewVector getJDAllHeader(String forYear) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        /*if(jciOnly){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manu Num", 0, 255, "VARCHAR2"));
        }*/
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "01/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "02/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "03/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "04/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "05/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "06/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "07/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "08/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "09/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "10/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "11/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "12/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "All Months TTL", 2,20,"NUMBER"));

        return header;
    }

	private GenericReportColumnViewVector getJDJciHeader(String forYear) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manu Num", 0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "01/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "02/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "03/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "04/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "05/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "06/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "07/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "08/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "09/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "10/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "11/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "12/"+forYear, 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "All Months TTL", 2,20,"NUMBER"));

        return header;
    }

	private interface aRecord{
        ArrayList toList();
    }

	protected class JDRetailDetail implements aRecord{
		String acctName;
		//String dist;
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
    		list.add(acctName);
    		/*if(showDist){
    			list.add(dist);
    		}*/
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

	protected class JDRetailWithDistDetail implements aRecord {
		String acctName;
		String dist;
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
    		list.add(acctName);
    		list.add(dist);

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


	protected class JDAllDetail implements aRecord{
		String acctName;
		//String manuNum;
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
		BigDecimal monthTotal;

		public ArrayList toList() {

    		ArrayList list = new ArrayList();
    		list.add(acctName);
    		/*if(jciOnly){
    			list.add(manuNum);
    		}*/
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
    		list.add(monthTotal);

    		return list;
		}
	}

	protected class JDJciDetail implements aRecord{
		String acctName;
		String manuNum;
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
		BigDecimal monthTotal;

		public ArrayList toList() {

    		ArrayList list = new ArrayList();
    		list.add(acctName);
    		list.add(manuNum);
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
    		list.add(monthTotal);

    		return list;
		}
	}

	static final Comparator JDRETAIL_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
	    	JDRetailDetail c1 = (JDRetailDetail)o1;
	    	JDRetailDetail c2 = (JDRetailDetail)o2;
                if(c1.acctName == null){
                    c1.acctName = "";
                }
                if(c2.acctName == null){
                    c2.acctName = "";
                }

                int val = c1.acctName.compareToIgnoreCase(c2.acctName);

                return val;
	    }
	};

	static final Comparator JDRETAILWITHDIST_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
	    	JDRetailWithDistDetail c1 = (JDRetailWithDistDetail)o1;
	    	JDRetailWithDistDetail c2 = (JDRetailWithDistDetail)o2;
                if(c1.acctName == null){
                    c1.acctName = "";
                }
                if(c2.acctName == null){
                    c2.acctName = "";
                }
                if(c1.dist == null){
                    c1.dist = "";
                }
                if(c2.dist == null){
                    c2.dist = "";
                }

                int val = c1.acctName.compareToIgnoreCase(c2.acctName);
                if(val != 0){
                    return val;
                }
                val = c1.dist.compareToIgnoreCase(c2.dist);
                return val;
	    }
	};

	static final Comparator JDALL_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
	    	JDAllDetail c1 = (JDAllDetail)o1;
	    	JDAllDetail c2 = (JDAllDetail)o2;
                if(c1.acctName == null){
                    c1.acctName = "";
                }
                if(c2.acctName == null){
                    c2.acctName = "";
                }

                int val = c1.acctName.compareToIgnoreCase(c2.acctName);

                return val;
	    }
	};

	static final Comparator JDJCI_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
	    	JDJciDetail c1 = (JDJciDetail)o1;
	    	JDJciDetail c2 = (JDJciDetail)o2;
                if(c1.acctName == null){
                    c1.acctName = "";
                }
                if(c2.acctName == null){
                    c2.acctName = "";
                }
                if(c1.manuNum == null){
                    c1.manuNum = "";
                }
                if(c2.manuNum == null){
                    c2.manuNum = "";
                }

                int val = c1.acctName.compareToIgnoreCase(c2.acctName);
                if(val != 0){
                    return val;
                }
                val = c1.manuNum.compareToIgnoreCase(c2.manuNum);
                return val;
	    }
	};
}
