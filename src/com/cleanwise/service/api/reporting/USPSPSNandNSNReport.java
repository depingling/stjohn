package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.PropertyService;

import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.rmi.RemoteException;

/**
 * @author bstevens
 */
public class USPSPSNandNSNReport implements GenericReportMulti {


    protected boolean mDisplaySalesTaxInfo;
    protected boolean mDisplayDistInfo;
    protected HashMap storeMap = new HashMap();
    protected HashMap masterItemCategoryMap = new HashMap();
    Connection con;
    protected boolean multiAccounts = false;
    protected boolean includeDist = false;
    protected HashMap accountMap = new HashMap();
    protected boolean showPrice;
    protected boolean displayPONum = false;
    protected boolean includeState = false;

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
            throws Exception {
        APIAccess factory = new APIAccess();

        con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV =
                new GenericReportResultViewVector();

        //Lists of report results
        ArrayList totals = new ArrayList();

        int catalogId;
        try{
            catalogId = Integer.parseInt((String)pParams.get("CATALOG"));
        } catch (RuntimeException e){
            throw new RemoteException("Could not parse Catalog control: "+pParams.get("CATALOG"));
        }
        String catalogCond = " catalog_id = " + catalogId;

        // store master item data
        int storeId = 0;
        String catStoreSql = "select bus_entity_id from clw_catalog_assoc where catalog_id = ? " +
                "and catalog_assoc_cd = ?";
        PreparedStatement stmt = con.prepareStatement(catStoreSql);
        stmt.setInt(1, catalogId);
        stmt.setString(2, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        ResultSet res = stmt.executeQuery();

        if (res.next()) {
            storeId = res.getInt(1);
        }
        res.close();
        stmt.close();
        if (storeId == 0) {
           throw new RemoteException("Could not get Store for the catalog id "+catalogId);
        }

        // get catalog name
        String catNameSql = "select short_desc from clw_catalog where catalog_id = ?";
        String catalogName = "";
        stmt = con.prepareStatement(catNameSql);
        stmt.setInt(1, catalogId);
        res = stmt.executeQuery();
        if (res.next()) {
            catalogName = res.getString(1);
        }
        res.close();
        stmt.close();


        PropertyService psvcBean = factory.getPropertyServiceAPI();
        BusEntityFieldsData fieldsData = psvcBean.fetchMasterItemFieldsData(storeId);

        String fieldsFromStr = "";
        String fieldsCondStr1 = "";
        String fieldsCondStr2 = "";
        if (fieldsData != null && PropertyFieldUtil.isShowInAdmin(fieldsData)) {
          for (int i=1; i<=15; i++) {
              if (PropertyFieldUtil.getShowInAdmin(fieldsData, i)) {
                  String tagName = PropertyFieldUtil.getTag(fieldsData, i);
                  if (Utility.isSet(tagName)) {
                      String fName = "f_" + i;
                      fieldsFromStr += ", clw_item_meta "+fName+"\n";
                      fieldsCondStr1 += " and " + fName + ".item_id(+) = i.item_id \n" +
                                       " and " + fName + ".name_value(+) = '" + tagName + "'\n";
                      fieldsCondStr2 += " or upper(" + fName + ".clw_value) = 'Y'\n";
                  }
              }
          }
        }



        String totalSql =
                    "select (case when certcomp.counts > 0 \n" +
                                fieldsCondStr2 +
                                "then 'Y' else '' end) as cert, \n" +
                            "store_skus.sku, i.short_desc name, psn.clw_value PSN, \n" +
                            " nsn.clw_value NSN, uom.clw_value UOM, pack.clw_value PACK, sz.clw_value \"SIZE\" \n" +
                   " from      clw_catalog_structure cs, clw_item i, clw_item_meta psn, \n" +
                            "  clw_item_meta nsn, clw_item_meta uom, clw_item_meta pack, clw_item_meta sz, \n" +
                            " (select item_id,count(item_id) as counts from clw_item_mapping  \n" +
                            "     where item_mapping_cd='" +RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY + "' group by item_id) certcomp,\n" +
                            "(" +
                            "select cs.item_id, cs.customer_sku_num as sku from clw_catalog_structure cs where cs.catalog_id =\n" +
                            "(select c.catalog_id from clw_catalog c where c.catalog_id in (\n" +
                            "   select ca1.catalog_id from clw_catalog_assoc ca1 where ca1.bus_entity_id = (\n" +
                            "       select ca.bus_entity_id from clw_catalog_assoc ca " +
                            "           where "+ catalogCond +" and " +
                            "               ca.catalog_assoc_cd = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE+"')\n" +
                            "       ) and c.catalog_type_cd = '"+RefCodeNames.CATALOG_TYPE_CD.STORE+"'" +
                            "   )\n" +
                            "and cs.catalog_structure_cd = '"+RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT+"'\n" +
                            ") store_skus\n" +
                            fieldsFromStr +
                  " where " +
                            catalogCond + "\n" +
                            "   and i.item_id = store_skus.item_id\n"+
                            "   and catalog_structure_cd = '"+RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT+"'\n" +
                            "   and i.item_id = cs.item_id\n" +
                            "   and psn.item_id(+) = i.item_id\n" +
                            "   and psn.name_value(+) = 'PSN'\n" +
                            "   and nsn.item_id(+) = i.item_id\n" +
                            "   and nsn.name_value(+) = 'NSN'\n" +
                            "   and uom.item_id(+) = i.item_id\n" +
                            "   and uom.name_value(+) = 'UOM'\n" +
                            "   and pack.item_id(+) = i.item_id\n" +
                            "   and pack.name_value(+) = 'PACK'\n" +
                            "   and sz.item_id(+) = i.item_id\n" +
                            "   and sz.name_value(+) = 'SIZE'\n" +
                            "   and certcomp.item_id(+) = i.item_id\n" +
                            fieldsCondStr1 +
                            "   and (nsn.clw_value is not null or psn.clw_value is not null)\n" +
                    " order by sku";



            stmt = con.prepareStatement(totalSql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PSNandNSNRecord record = new PSNandNSNRecord();
                record.greenEPP = rs.getString("cert");
                record.storeSku = rs.getString("sku");
                record.name = rs.getString("name");
                record.PSN = rs.getString("PSN");
                record.NSN = rs.getString("NSN");
                record.UOM = rs.getString("UOM");
                record.pack = rs.getString("PACK");
                record.size = rs.getString("SIZE");

                totals.add(record);
            }
            rs.close();
            stmt.close();


        Iterator it = totals.iterator();
        GenericReportResultView result =
                    GenericReportResultView.createValue();
        result.setTable(new ArrayList());
        while (it.hasNext()) {
           result.getTable().add(((PSNandNSNRecord) it.next()).toList());
        }
        GenericReportColumnViewVector header = getReportHeader();

        result.setColumnCount(header.size());
        result.setTitle(getReportTitle(pReportData.getName(), catalogName));
        result.setHeader(header);
        result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
        result.setName("PSN and NSN");

        resultV.add(result);

        for (int i = 0; i < resultV.size(); i++) {
            GenericReportResultView report = (GenericReportResultView) resultV.get(i);
            report.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            report.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            report.setFreezePositionRow(4);
        }
        return resultV;

    }






    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Green/EPP", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Store SKU", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Name",0,255,"VARCHAR2","10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PSN",0,255,"VARCHAR2","10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "NSN", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "5", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "7", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Size", 0, 255, "VARCHAR2", "7", false));

        return header;
    }


    protected GenericReportColumnViewVector getReportTitle(String pReportName, String pCatalogName) {

        GenericReportColumnViewVector title = new GenericReportColumnViewVector();

        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Report Name: " + pReportName, 0, 255, "VARCHAR2"));
      	title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Catalog Name: " + pCatalogName, 0, 255, "VARCHAR2"));
        return title;
    }




    private class PSNandNSNRecord {
        String greenEPP;
        String storeSku;
        String name;
        String PSN;
        String NSN;
        String UOM;
        String pack;
        String size;

        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(greenEPP);
            list.add(storeSku);
            list.add(name);
            list.add(PSN);
            list.add(NSN);
            list.add(UOM);
            list.add(pack);
            list.add(size);
            return list;
        }
    }






}
