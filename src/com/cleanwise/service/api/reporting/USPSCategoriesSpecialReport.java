package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;


import java.util.*;
import java.sql.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

public class USPSCategoriesSpecialReport extends ReportBase {

    private static String className = "USPSCategoriesSpecialReport";
    private String VALUE_DELIM = "|";
    private static int ACCOUNT_CATALOG_ID = 57445;

    private ArrayList resultTable = new ArrayList();
    
    
    public USPSCategoriesSpecialReport() {
		java.util.Date repDate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String repDateS = sdf.format(repDate);
		setFileName("hier_cleanwise");
		setExt("dat"+"." + repDateS);
		setSpecial(true);
        setUserTypesAutorized(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR+","+RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR);
    }

    protected byte[] getOutputStream()  throws Exception {
        StringBuffer resultBuff = new StringBuffer();
        Iterator i = resultTable.iterator();
        while(i.hasNext()) {
            ArrayList line = (ArrayList)i.next();
            Iterator j = line.iterator();
            int k = 1;
            while (j.hasNext()) {
                Object el = j.next();
                if (k > 2) {
                    resultBuff.append(VALUE_DELIM);
                }
				if(k > 1) {
					if (el == null) {
						resultBuff.append("");
					} else {
						resultBuff.append(el);
					}
				}
                k++;
            }
            resultBuff.append("\r\n");
        }
        return resultBuff.toString().getBytes();
    }


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception
    {
        Connection con = pCons.getMainConnection();
        String errorMess = "";


        ArrayList categories = new ArrayList();
/*
        int catalogId;
        try{
            catalogId = Integer.parseInt((String)pParams.get("CATALOG"));
        }catch (RuntimeException e){
            throw new RemoteException("Could not parse Catalog control: "+pParams.get("CATALOG"));
        }
*/
        String sql = "";
        String categorySelector = (String)pParams.get("CATEGORY_SELECTOR");
        // product parent category
      //  if (Utility.isSet(categorySelector) && categorySelector.equals(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY)) {
            sql =   "SELECT DECODE(SIGN(LENGTH(short_desc)-45),-1,' ',0,' ',1,'E') error_fl, item_id, short_desc as descr, short_desc " +
                    "FROM clw_item WHERE item_id IN " +
                    "   (SELECT ia.item2_id " +
                    "       FROM  clw_item_assoc ia" +
                    "       WHERE item_assoc_cd = '"+ RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY +"'" +
                    "             AND ia.catalog_id = "+ ACCOUNT_CATALOG_ID +")" +
                    "ORDER BY error_fl, item_id"
                    ;
        /*
		} else { // catalog category
            sql =    "SELECT DECODE(SIGN(LENGTH(short_desc)-45),-1,' ',0,' ',1,'E') error_fl, item_id, short_desc as descr, short_desc " +
                            "FROM clw_item WHERE item_id IN " +
                            "   (SELECT ia.item_id" +
                            "       FROM  clw_catalog_structure ia " +
                            "       WHERE catalog_structure_cd = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+ "'" +
                            "             AND ia.catalog_id = " + ACCOUNT_CATALOG_ID+ ")" +
                            " ORDER BY error_fl, item_id"
                    ;
        }
		*/

        try {

            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                CategoryView cat = new CategoryView();
                cat.errFlag = rs.getString("error_fl");
                cat.level_1_Id = rs.getString("item_id");
                cat.level_1_desc = rs.getString("descr");
                cat.level_1_short_desc = rs.getString("short_desc");
                categories.add(cat);
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException exc) {
            errorMess = "Error. Report.USPSCategoriesSpecialReport() SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
            errorMess = "Error. Report.USPSCategoriesSpecialReport() Api Service Access Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }


        ArrayList table = new ArrayList();

        for(Iterator iter = categories.iterator(); iter.hasNext();) {
            ArrayList line = new ArrayList();
            table.add(line);
            CategoryView cat = (CategoryView)iter.next();
            line.add(cat.errFlag);
            line.add(cat.level_1_Id);
            line.add(cat.level_1_desc);
            line.add(cat.level_1_short_desc);

            line.add(cat.level_2_Id);
            line.add(cat.level_2_desc);
            line.add(cat.level_2_short_desc);

            line.add(cat.level_3_Id);
            line.add(cat.level_3_desc);
            line.add(cat.level_3_short_desc);

            line.add(cat.level_4_Id);
            line.add(cat.level_4_desc);
            line.add(cat.level_4_short_desc);

            line.add(cat.level_5_Id);
            line.add(cat.level_5_desc);
            line.add(cat.level_5_short_desc);

        }
        resultTable = table;

        return super.process(pCons, pReportData, pParams);


    }



    public class CategoryView {
        String errFlag = "";
        String level_1_Id = "";
        String level_1_desc = "";
        String level_1_short_desc = "";
        String level_2_Id = "";
        String level_2_desc = "";
        String level_2_short_desc = "";
        String level_3_Id = "";
        String level_3_desc = "";
        String level_3_short_desc = "";
        String level_4_Id = "";
        String level_4_desc = "";
        String level_4_short_desc = "";
        String level_5_Id = "";
        String level_5_desc = "";
        String level_5_short_desc = "";

    }



}
