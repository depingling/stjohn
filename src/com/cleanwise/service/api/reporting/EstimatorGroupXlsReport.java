/*
 * EstimatorXlsReport.java
 *
 * Created on January, 2005
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.util.StringTokenizer;

/**
 * Compares different estimator models
 * @author  Y. Kupershmidt
 */
public class EstimatorGroupXlsReport implements GenericReportMulti {
    Map orderTotalInfo = new java.util.HashMap();
    /** Creates a new instance of InvoiceProfitReport */
    public EstimatorGroupXlsReport() {
    }
    
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) 
    throws Exception 
    {
        DBCriteria dbc = null;
        Connection con = pCons.getMainConnection();
        String errorMess = "No error";
        BigDecimal zeroAmt = new BigDecimal(0);
        
        //User
        String userIdS = (String) pParams.get("CUSTOMER");
        if(userIdS==null || userIdS.trim().length()==0){
          String mess = "^clw^No user provided^clw^";
          throw new Exception(mess);
        }
        int userId = 0;
        try {
          userId = Integer.parseInt(userIdS);
        }
        catch (Exception exc1) {
          String mess = "^clw^Wrong user id format^clw^";
          throw new Exception(mess);
        }

        dbc = new DBCriteria();
        //Get user accounts
        UserData userD = null;
        try {
          userD = UserDataAccess.select(con,userId);
        } catch(Exception exc) {
          String mess = "^clw^No user information found. User id: "+userIdS+"^clw^";
          throw new Exception(mess);
        }
        boolean checkAccountsFl = true;
        boolean customerVersionFl = true;
        IdVector userAccountIdV = new IdVector();
        if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userD.getUserTypeCd()) ||
           RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userD.getUserTypeCd())){
           checkAccountsFl = false;
           customerVersionFl = false;
        } else {
          dbc = new DBCriteria();
          dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                                            RefCodeNames.USER_ASSOC_CD.ACCOUNT);
          dbc.addEqualTo(UserAssocDataAccess.USER_ID, userId);
          userAccountIdV = 
            UserAssocDataAccess.selectIdOnly(con,UserAssocDataAccess.BUS_ENTITY_ID,dbc);

        }
        
        //Models
        IdVector estimatorFacilityIdV = new IdVector();
        String estimatorFacilityIdListS = (String) pParams.get("FACILITY_GROUP_OPT");
        if(estimatorFacilityIdListS==null) {
          String mess = "^clw^No facilities provided^clw^";
                    throw new Exception(mess);
        }
        StringTokenizer tok = new StringTokenizer(estimatorFacilityIdListS,",");
        while(tok.hasMoreTokens()){
          String efIdS = tok.nextToken();
          if(Utility.isSet(efIdS)) {
            efIdS = efIdS.trim();
            int efId = 0;
            try {
              efId = Integer.parseInt(efIdS);
            } catch(Exception exc) {
               String mess = "^clw^Invalid estimator facility id: "+efIdS+"^clw^";
                    throw new Exception(mess);
            }
            estimatorFacilityIdV.add(new Integer(efId));
          }
        }
      
        if(estimatorFacilityIdV.size()==0) {
           String mess = "^clw^No estimator facilities provided^clw^";
           throw new Exception(mess);
        }
                
        //
        dbc = new DBCriteria();
        dbc.addOneOf(EstimatorFacilityDataAccess.ESTIMATOR_FACILITY_ID,estimatorFacilityIdV);
        dbc.addOrderBy(EstimatorFacilityDataAccess.NAME);
        EstimatorFacilityDataVector estimatorFacilityDV =
                          EstimatorFacilityDataAccess.select(con,dbc);

        IdVector catalogIdV = new IdVector();
        estimatorFacilityIdV = new IdVector();
        for(Iterator iter = estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          int catalogId = efD.getCatalogId();
          catalogIdV.add(new Integer(catalogId));
          estimatorFacilityIdV.add(new Integer(efD.getEstimatorFacilityId()));
        }

        //Check account authority
        if(checkAccountsFl) {
          dbc = new DBCriteria();
          dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID, catalogIdV);
          dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                                               RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
          IdVector catalogAccountIdV = 
                 CatalogAssocDataAccess.selectIdOnly(con,CatalogAssocDataAccess.BUS_ENTITY_ID,dbc);
          for(Iterator iter=catalogAccountIdV.iterator(); iter.hasNext();) {
             Integer accountIdI = (Integer) iter.next();
             int accountId = accountIdI.intValue();
             boolean foundFl = false;
             for(Iterator iter1=userAccountIdV.iterator(); iter1.hasNext();) {
               int uaId = ((Integer) iter1.next()).intValue();
               if(accountId==uaId) {
                 foundFl = true;
                 break;
               }
             }
             if(!foundFl) {
               String mess = "^clw^No athority for some requested models^clw^";
               throw new Exception(mess);
             }
          }
        }
        
        //Check models status
        errorMess = "";
        int errorCount = 0;
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          int processStep = efD.getProcessStep();
          if(processStep!=33333 && processStep!=32333) {
            if(errorCount>0) errorMess += ", ";
              errorMess +=  efD.getName();
            errorCount++;
          }
        }
        
        if(errorCount>0) {
          errorMess = (errorCount>1)? 
             "Some models must be complited: "+errorMess:
              " Model "+errorMess+" must be completed";
             
          errorMess ="^clw^"+errorMess+"^clw^";
          throw new Exception(errorMess);
        }

        //Create profile page
        ArrayList profilePage = new ArrayList();
        int modelQty = estimatorFacilityDV.size();
        ArrayList rowFacilityTypeCd = new ArrayList();
        profilePage.add(rowFacilityTypeCd);
        rowFacilityTypeCd.add("Facility Type");
        rowFacilityTypeCd.add("F");

        ArrayList rowFacilityGroup = new ArrayList();
        profilePage.add(rowFacilityGroup);
        rowFacilityGroup.add("Facility Group");        
        rowFacilityGroup.add("F");        

        ArrayList rowCatalogName = new ArrayList();
        profilePage.add(rowCatalogName);
        rowCatalogName.add("Catalog");        
        rowCatalogName.add("F");        

        ArrayList rowFacilityQty = new ArrayList();
        profilePage.add(rowFacilityQty);
        rowFacilityQty.add("Facility Qty");        
        rowFacilityQty.add("T");         

        ArrayList rowPersonnelQty = new ArrayList();
        profilePage.add(rowPersonnelQty);
        rowPersonnelQty.add("Resident Personnel (FTE)");        
        rowPersonnelQty.add("T");                

        ArrayList rowVisitorQty = new ArrayList();
        profilePage.add(rowVisitorQty);
        rowVisitorQty.add("Visitors (per day)");        
        rowVisitorQty.add("T");                


        ArrayList rowWorkingDayYearQty = new ArrayList();
        profilePage.add(rowWorkingDayYearQty);
        rowWorkingDayYearQty.add("Working Days per Year");        
        rowWorkingDayYearQty.add("F");        

        ArrayList rowStationQty = new ArrayList();
        profilePage.add(rowStationQty);
        rowStationQty.add("Number of Retail Stations");        
        rowStationQty.add("T");        
        //String[] rowAppearanceStandardCd = new String[modelQty+2];
        
        //Bathroom
        ArrayList rowBathroomQty = new ArrayList();
        profilePage.add(rowBathroomQty);
        rowBathroomQty.add("Number of Bathrooms");        
        rowBathroomQty.add("T");        

        ArrayList rowToiletBathroomQty = new ArrayList();
        profilePage.add(rowToiletBathroomQty);
        rowToiletBathroomQty.add("Number Toilets/Urinals per Bathroom");        
        rowToiletBathroomQty.add("F");        

        ArrayList rowSinkBathroomQty = new ArrayList();
        profilePage.add(rowSinkBathroomQty);
        rowSinkBathroomQty.add("Numnber of Sinks per Bathroom");        
        rowSinkBathroomQty.add("F");        

        ArrayList rowShowerQty = new ArrayList();
        profilePage.add(rowShowerQty);
        rowShowerQty.add("Number of Showers");        
        rowShowerQty.add("T");        

        ArrayList rowWashHandQty = new ArrayList();
        profilePage.add(rowWashHandQty);
        rowWashHandQty.add("Average Number of FTE Hand Wash (per Day)");       
        rowWashHandQty.add("F");        

        ArrayList rowTissueUsageQty = new ArrayList();
        profilePage.add(rowTissueUsageQty);
        rowTissueUsageQty.add("Average Number of FTE Toilet Tissue Usage (per Day)");        
        rowTissueUsageQty.add("F");        

        ArrayList rowVisitorBathroomPercent = new ArrayList();
        profilePage.add(rowVisitorBathroomPercent);
        rowVisitorBathroomPercent.add("Percent of Visitors Using Bathroom");        
        rowVisitorBathroomPercent.add("F");        

        ArrayList rowVisitorToiletTissuePercent = new ArrayList();
        profilePage.add(rowVisitorToiletTissuePercent);
        rowVisitorToiletTissuePercent.add("Percent of Bathroom Using Visitors Who Use Toilet Tissue");
        rowVisitorToiletTissuePercent.add("F");        

        //
        //String[] rowLinerRatioBaseCd = new String[modelQty+2];

        ArrayList rowCommonAreaReceptacleQty = new ArrayList();
        profilePage.add(rowCommonAreaReceptacleQty);
        rowCommonAreaReceptacleQty.add("Number of Common Area Trash Receptacles (per Day)");
        rowCommonAreaReceptacleQty.add("T");        

        ArrayList rowFteReceptacleLinerRatio = new ArrayList();
        profilePage.add(rowFteReceptacleLinerRatio);
        rowFteReceptacleLinerRatio.add("Number of Trash Recepticals for Each FTE (per Day) ");        
        rowFteReceptacleLinerRatio.add("F");        

        ArrayList rowStationReceptacleLinerRatio = new ArrayList();
        profilePage.add(rowStationReceptacleLinerRatio);
        rowStationReceptacleLinerRatio.add("Number of Trash Recepticals for Each Retail Station (per Day)");
        rowStationReceptacleLinerRatio.add("F");        

        //String[] rowAdditionalLinerRatio = new String[modelQty+2];
        //rowAdditionalLinerRatio.add("";        

        ArrayList rowToiletLinerRatio = new ArrayList();
        profilePage.add(rowToiletLinerRatio);
        rowToiletLinerRatio.add("Number of Trash Recepticals for Each Bathroom (per Day)");
        rowToiletLinerRatio.add("F");        

        ArrayList rowFteLargeLinerRatio = new ArrayList();
        profilePage.add(rowFteLargeLinerRatio);
        rowFteLargeLinerRatio.add("Personal Trash Receptacles Hold by Container Receptacle ");        
        rowFteLargeLinerRatio.add("F");        

        ArrayList rowStationLargeLinerRatio = new ArrayList();
        profilePage.add(rowStationLargeLinerRatio);
        rowStationLargeLinerRatio.add("Retail Station Trash Receptacles Hold by Container Receptacle ");
        rowStationLargeLinerRatio.add("F");        

        ArrayList rowLargeLinerCaLinerQty = new ArrayList();
        profilePage.add(rowLargeLinerCaLinerQty);
        rowLargeLinerCaLinerQty.add("Common Area Receptacles Hold by Container Receptacle");
        rowLargeLinerCaLinerQty.add("F");        
        
        //Floors
        //String[] rowGrossFootage = new String[modelQty+2];
        //String[] rowCleanableFootagePercent = new String[modelQty+2];
        ArrayList rowNetCleanableFootage = new ArrayList();
        profilePage.add(rowNetCleanableFootage);
        rowNetCleanableFootage.add("Net Cleanable Footage (Sq F)");
        rowNetCleanableFootage.add("T");        

        ArrayList rowBaseboardPercent = new ArrayList();
        profilePage.add(rowBaseboardPercent);
        rowBaseboardPercent.add("Baseboard Percent");
        rowBaseboardPercent.add("F");        

        
        
        //Allocated categories
        ArrayList rowEstimatedItemsFactor = new ArrayList();
        profilePage.add(rowEstimatedItemsFactor);
        rowEstimatedItemsFactor.add("Allocated Items Factor (Percent)");        
        rowEstimatedItemsFactor.add("F");        

        boolean diffCatalogFl = false;
        
        int ind = 0;
        String  catalogName = "";
        int prevCatalogId = 0;
        int sumInt;
        int[] facilityQtyA = new int[modelQty];
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          ind++;
          rowFacilityTypeCd.add(Utility.strNN(efD.getFacilityTypeCd()));
          rowFacilityGroup.add(Utility.strNN(efD.getFacilityGroup()));
          int catalogId = efD.getCatalogId();
          if(prevCatalogId!=catalogId) {
            prevCatalogId = catalogId;
            if(ind>1) diffCatalogFl = true;
            try{
              CatalogData catalogD = CatalogDataAccess.select(con,catalogId);
              catalogName = catalogD.getShortDesc();
            } catch(DataNotFoundException exc) {
              catalogName = "";
            }
          }
          rowCatalogName.add(catalogName);        
          facilityQtyA[ind-1] = efD.getFacilityQty();
          rowFacilityQty.add(new Integer(efD.getFacilityQty()));  
          
          int personnelQty = efD.getPersonnelQty();
          rowPersonnelQty.add(new Integer(personnelQty));

          int visitorQty = efD.getVisitorQty();
          rowVisitorQty.add(new Integer(visitorQty));

          rowWorkingDayYearQty.add(new Integer(efD.getWorkingDayYearQty()));
          
          int stationQty = efD.getStationQty();
          rowStationQty.add(new Integer(stationQty));

          //Bathroom
          int bathroomQty = efD.getBathroomQty();
          rowBathroomQty.add(new Integer(bathroomQty));
          
          BigDecimal toiletBathroomQty = Utility.bdNN(efD.getToiletBathroomQty());
          toiletBathroomQty = toiletBathroomQty.setScale(2,BigDecimal.ROUND_HALF_UP);          
          rowToiletBathroomQty.add(toiletBathroomQty);

          BigDecimal sinkBathroomQty = Utility.bdNN(efD.getSinkBathroomQty());
          sinkBathroomQty = sinkBathroomQty.setScale(2,BigDecimal.ROUND_HALF_UP);          
          rowSinkBathroomQty.add(sinkBathroomQty);

          int showerQty = efD.getShowerQty();
          rowShowerQty.add(new Integer(showerQty));
          
          BigDecimal washHandQty = Utility.bdNN(efD.getWashHandQty());
          washHandQty = washHandQty.setScale(2,BigDecimal.ROUND_HALF_UP);          
          rowWashHandQty.add(washHandQty);

          BigDecimal tissueUsageQty = Utility.bdNN(efD.getTissueUsageQty());
          tissueUsageQty = tissueUsageQty.setScale(2,BigDecimal.ROUND_HALF_UP);          
          rowTissueUsageQty.add(tissueUsageQty);


          BigDecimal visitorBathroomPercent = Utility.bdNN(efD.getVisitorBathroomPercent());
          visitorBathroomPercent = visitorBathroomPercent.setScale(2,BigDecimal.ROUND_HALF_UP);          
          rowVisitorBathroomPercent.add(visitorBathroomPercent);

          BigDecimal visitorToiletTissuePercent = Utility.bdNN(efD.getVisitorToiletTissuePercent());
          visitorToiletTissuePercent = visitorToiletTissuePercent.setScale(2,BigDecimal.ROUND_HALF_UP);          
          rowVisitorToiletTissuePercent.add(visitorToiletTissuePercent);

          int commonAreaReceptacleQty = efD.getCommonAreaReceptacleQty();
          rowCommonAreaReceptacleQty.add(new Integer(commonAreaReceptacleQty));

          boolean retailFl = false;
          String facilityTypeCd = efD.getFacilityTypeCd();
          if(RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(facilityTypeCd) ||
             RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(facilityTypeCd)) {
            retailFl = true;
          }
          
          BigDecimal receptacleLinerRatio = Utility.bdNN(efD.getReceptacleLinerRatio());
          receptacleLinerRatio = receptacleLinerRatio.setScale(2,BigDecimal.ROUND_HALF_UP);
          if(retailFl) {
            rowStationReceptacleLinerRatio.add(receptacleLinerRatio);
            rowFteReceptacleLinerRatio.add(null);
          } else {
            rowStationReceptacleLinerRatio.add(null);
            rowFteReceptacleLinerRatio.add(receptacleLinerRatio.toString());
          }

          BigDecimal toiletLinerRatio = Utility.bdNN(efD.getToiletLinerRatio());
          toiletLinerRatio = toiletLinerRatio.setScale(2,BigDecimal.ROUND_HALF_UP);
          rowToiletLinerRatio.add(toiletLinerRatio);

          BigDecimal largeLinerRatio = Utility.bdNN(efD.getLargeLinerRatio());
          largeLinerRatio = largeLinerRatio.setScale(2,BigDecimal.ROUND_HALF_UP);
          if(retailFl) {
            rowStationLargeLinerRatio.add(largeLinerRatio);
            rowFteLargeLinerRatio.add(null);
          } else {
            rowStationLargeLinerRatio.add(null);
            rowFteLargeLinerRatio.add(largeLinerRatio.toString());
          }
  
          BigDecimal largeLinerCaLinerQty = Utility.bdNN(efD.getLargeLinerCaLinerQty());
          largeLinerCaLinerQty = largeLinerCaLinerQty.setScale(2,BigDecimal.ROUND_HALF_UP);
          rowLargeLinerCaLinerQty.add(largeLinerCaLinerQty);

          //Floors
          int netCleanableFootage = efD.getNetCleanableFootage();
          rowNetCleanableFootage.add(new Integer(netCleanableFootage));        

          BigDecimal baseboardPercent = Utility.bdNN(efD.getBaseboardPercent());
          baseboardPercent = baseboardPercent.setScale(2,BigDecimal.ROUND_HALF_UP);
          rowBaseboardPercent.add(baseboardPercent);

          //Allocated categories
          BigDecimal estimatedItemsFactor = Utility.bdNN(efD.getEstimatedItemsFactor());
          estimatedItemsFactor = estimatedItemsFactor.setScale(2,BigDecimal.ROUND_HALF_UP);
          rowEstimatedItemsFactor.add(estimatedItemsFactor);        
        }
    
        GenericReportResultView profilePageResult = GenericReportResultView.createValue();
        ArrayList profilePageTable = new ArrayList();
        profilePageResult.setTable(profilePageTable);
        for(Iterator iter=profilePage.iterator(); iter.hasNext(); ) {
          ArrayList pfls = (ArrayList) iter.next();
          ArrayList row = new ArrayList();
          profilePageTable.add(row);
          Iterator iter1 = pfls.iterator();
          String propertyName = (String) iter1.next();
          row.add(propertyName);
          String sumFlagS = (String) iter1.next();
          boolean sumFl = ("T".equals(sumFlagS))?true:false;
          boolean intFl = false;
          while(iter1.hasNext()) {
            Object propertyOb = iter1.next();
            row.add(propertyOb);
          }
          if(sumFl) {
            row.add(null);
            Integer sumI = new Integer(0);
            BigDecimal sumBD = new BigDecimal(0);
            iter1 = pfls.iterator();
            iter1.next();
            iter1.next();
            for(int ii=0; iter1.hasNext(); ii++) {
              Object propertyOb = iter1.next();
              if(propertyOb!=null) {
                if(propertyOb instanceof Integer) {
                  intFl = true;
                  Integer propertyI = (Integer) propertyOb;
                  int valInt = propertyI.intValue() * facilityQtyA[ii];
                  row.add(new Integer(valInt));
                  int sum = valInt + sumI.intValue();
                  sumI = new Integer(sum);
                } else if(propertyOb instanceof BigDecimal) {
                  BigDecimal propertyBD = (BigDecimal) propertyOb;
                  double valDb = propertyBD.doubleValue() * facilityQtyA[ii];
                  BigDecimal valBD = new BigDecimal(valDb);
                  row.add(valBD);
                  sumBD = sumBD.add(valBD);
                }
              }
            }
            if(modelQty>1) {
              if(intFl) {
                row.add(sumI);
              } else {
                row.add(sumBD);
              }
            }           
          }
        }
        GenericReportColumnViewVector profilePageHeader = new GenericReportColumnViewVector();
        profilePageResult.setHeader(profilePageHeader);
        profilePageHeader.add(ReportingUtils.
             createGenericReportColumnView("java.lang.String","Property",0,255,"VARCHAR2"));
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          String name = Utility.strNN(efD.getName());
          profilePageHeader.add(ReportingUtils.
             createGenericReportColumnView("java.lang.String",name,0,255,"VARCHAR2"));
        }
        profilePageHeader.add(ReportingUtils.
             createGenericReportColumnView("java.lang.String","",0,255,"VARCHAR2"));
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          String name = Utility.strNN(efD.getName());
          profilePageHeader.add(ReportingUtils.
             createGenericReportColumnView("java.lang.String","Total "+name,0,255,"VARCHAR2"));
        }
        if(modelQty>1) {
          profilePageHeader.add(ReportingUtils.
             createGenericReportColumnView("java.lang.String","Group Total",0,255,"VARCHAR2"));
        }
        profilePageResult.setColumnCount(profilePageHeader.size());
        profilePageResult.setName("Profile And Assumptions");
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        resultV.add(profilePageResult);

        //Get products
        com.cleanwise.service.api.APIAccess apiAccess = 
                                         new com.cleanwise.service.api.APIAccess();
        com.cleanwise.service.api.session.Estimation estimationEjb = apiAccess.getEstimationAPI();
        
        ArrayList productsUsedAL = new ArrayList();
        for(Iterator iter=estimatorFacilityIdV.iterator(); iter.hasNext();) {
          int facilityId = ((Integer) iter.next()).intValue();
          EstimatorProdResultViewVector products = new EstimatorProdResultViewVector();
          EstimatorProdResultViewVector paperProducts = 
                              estimationEjb.calcPaperPlusYearProducts(facilityId);
          products.addAll(paperProducts);
          EstimatorProdResultViewVector floorProducts = 
                              estimationEjb.calcFloorYearProducts(facilityId);
          products.addAll(floorProducts);
          EstimatorProdResultViewVector restroomProducts = 
                              estimationEjb.calcRestroomYearProducts(facilityId);
          products.addAll(restroomProducts);
          products = sortByCategorySku(products);
          productsUsedAL.add(products);
        }
        
//////////////////
        
        //Floor Page
        dbc = new DBCriteria();
        dbc.addOneOf(FacilityFloorDataAccess.ESTIMATOR_FACILITY_ID, estimatorFacilityIdV);
        dbc.addCondition("nvl("+FacilityFloorDataAccess.CLEANABLE_PERCENT+",0)>0");
        dbc.addOrderBy(FacilityFloorDataAccess.FLOOR_TYPE_CD);
        
        FacilityFloorDataVector floorDV =
                             FacilityFloorDataAccess.select(con,dbc);
        ArrayList[] facilityFloorA  = new ArrayList[modelQty+1];
        for(int ii=0; ii<modelQty+1; ii++) {
          facilityFloorA[ii] = new ArrayList();
        }
        int[] facilityIdA = new int[modelQty];
        int[] squareFootageA = new int[modelQty];
        ind=-1;
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          ind++;
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          facilityIdA[ind] = efD.getEstimatorFacilityId();
          squareFootageA[ind] = efD.getNetCleanableFootage();
        }
        for(Iterator iter=floorDV.iterator(); iter.hasNext();) {
          FacilityFloorData ffD = (FacilityFloorData) iter.next();
          int facilityId = ffD.getEstimatorFacilityId();
          for(int ii=0; ii<modelQty; ii++) {
            if(facilityId==facilityIdA[ii]) {
              facilityFloorA[ii].add(ffD);
              break;
            }
          }
        }
        GenericReportResultView floorPageResult = GenericReportResultView.createValue();
        resultV.add(0,floorPageResult);
        GenericReportColumnViewVector fpHeader = new GenericReportColumnViewVector();
        floorPageResult.setHeader(fpHeader);
        floorPageResult.setName("Floor Profile");
        ArrayList floorTable = new ArrayList();
        floorPageResult.setTable(floorTable);
  
        fpHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Floor Type",0,255,"VARCHAR2"));
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          String text = efD.getName();
          fpHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",text,0,255,"VARCHAR2"));
        }
        fpHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","",0,255,"VARCHAR2"));
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          String text = "Total "+ efD.getName();
          fpHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String",text,0,255,"VARCHAR2"));
        }
        if(modelQty>1) {
          fpHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Total",0,255,"VARCHAR2"));
        }
        floorPageResult.setColumnCount(fpHeader.size());
        
        ArrayList allFloorsRowSqf = new ArrayList();
        floorTable.add(allFloorsRowSqf);
        allFloorsRowSqf.add("All Floors (Sq.F)");        
        int squareFootageTotal = 0;
        for(int ii=0; ii<modelQty; ii++) {
          allFloorsRowSqf.add(new BigDecimal(squareFootageA[ii]));
        }
        allFloorsRowSqf.add(null);
        for(int ii=0; ii<modelQty; ii++) {
          squareFootageTotal += squareFootageA[ii]*facilityQtyA[ii];
          allFloorsRowSqf.add(new BigDecimal(squareFootageA[ii]*facilityQtyA[ii]));
        }
        allFloorsRowSqf.add(new BigDecimal(squareFootageTotal));
        
        
        
        FacilityFloorData[] wrkFloorA = new FacilityFloorData[modelQty];
        for(int ii=0; ii<modelQty; ii++) {
          wrkFloorA[ii] = null;
        }
        Iterator[] floorIterA = new Iterator[modelQty];
        for(int ii=0; ii<modelQty; ii++) {
          floorIterA[ii] = facilityFloorA[ii].iterator();
        }
        while(true) {
          boolean exitFl = true;
          for(int ii=0; ii<modelQty; ii++) {
            if(wrkFloorA[ii]!=null || floorIterA[ii].hasNext()) {
              exitFl = false;
              if(wrkFloorA[ii]==null) {
                wrkFloorA[ii] = (FacilityFloorData) floorIterA[ii].next();
              }
            }
          }
          if(exitFl) {
            break;
          }
          String minFloorType = null;
          for(int ii=0; ii<modelQty; ii++) {
            if(wrkFloorA[ii]==null) continue;
            String floorType = Utility.strNN(wrkFloorA[ii].getFloorTypeCd());
            if(minFloorType==null) {
              minFloorType = floorType;
            } else {
              int comp = minFloorType.compareTo(floorType);
              if(comp>0) {
                minFloorType = floorType;
              }
            }
          }
          BigDecimal[] floorPercentA = new BigDecimal[modelQty];
          BigDecimal[] floorSqfA = new BigDecimal[modelQty];
          BigDecimal[] ltFloorPercentA = new BigDecimal[modelQty];
          BigDecimal[] ltFloorSqfA = new BigDecimal[modelQty];
          BigDecimal[] mtFloorPercentA = new BigDecimal[modelQty];
          BigDecimal[] mtFloorSqfA = new BigDecimal[modelQty];
          BigDecimal[] htFloorPercentA = new BigDecimal[modelQty];
          BigDecimal[] htFloorSqfA = new BigDecimal[modelQty];
          for(int ii=0; ii<modelQty; ii++) {
            floorPercentA[ii] = null;
            floorSqfA[ii] = null;
            ltFloorPercentA[ii] = null;
            ltFloorSqfA[ii] = null;
            mtFloorPercentA[ii] = null;
            mtFloorSqfA[ii] = null;
            htFloorPercentA[ii] = null;
            htFloorSqfA[ii] = null;
            if(wrkFloorA[ii]!=null &&
               minFloorType.equals(wrkFloorA[ii].getFloorTypeCd())) {
              floorPercentA[ii] = wrkFloorA[ii].getCleanablePercent();
              if(floorPercentA[ii]!=null && floorPercentA[ii].abs().doubleValue()<0.00001) {
                floorPercentA[ii]=null;
                continue;
              } else {
                floorSqfA[ii] = 
                  new BigDecimal(squareFootageA[ii] * floorPercentA[ii].doubleValue()/100);
              } 
              ltFloorPercentA[ii] = wrkFloorA[ii].getCleanablePercentLt();
              if(ltFloorPercentA[ii]!=null && ltFloorPercentA[ii].abs().doubleValue()<0.00001){  
                ltFloorPercentA[ii] = null;
              } else {
                ltFloorSqfA[ii] = new BigDecimal(
                   floorSqfA[ii].doubleValue()*ltFloorPercentA[ii].doubleValue()/100);
              }
              mtFloorPercentA[ii] = wrkFloorA[ii].getCleanablePercentMt();
              if(mtFloorPercentA[ii]!=null && mtFloorPercentA[ii].abs().doubleValue()<0.00001){  
                mtFloorPercentA[ii] = null;
              } else {
                mtFloorSqfA[ii] = new BigDecimal(
                   floorSqfA[ii].doubleValue()*mtFloorPercentA[ii].doubleValue()/100);
              }

              htFloorPercentA[ii] = wrkFloorA[ii].getCleanablePercentHt();
              if(htFloorPercentA[ii]!=null && htFloorPercentA[ii].abs().doubleValue()<0.00001){  
                htFloorPercentA[ii] = null;
              } else {
                htFloorSqfA[ii] = new BigDecimal(
                   floorSqfA[ii].doubleValue()*htFloorPercentA[ii].doubleValue()/100);
              }
              wrkFloorA[ii] = null;
            }
          }
          ArrayList floorRowPr = new ArrayList();
          floorTable.add(floorRowPr);
          floorRowPr.add(minFloorType+" (%)");
          double val;
          double totalVal = 0;
          for(int ii=0; ii<modelQty; ii++) {
            floorRowPr.add(floorPercentA[ii]);
          }
          
          ArrayList floorRowSqF = new ArrayList();
          floorTable.add(floorRowSqF);
          floorRowSqF.add(minFloorType+" (Sq.F)");
          for(int ii=0; ii<modelQty; ii++) {
            floorRowSqF.add(floorSqfA[ii]);
          }
          floorRowSqF.add(null);
          totalVal = 0;
          for(int ii=0; ii<modelQty; ii++) {
            val = (floorSqfA[ii]!=null)?floorSqfA[ii].doubleValue()*facilityQtyA[ii]:0;
            totalVal += val;
            floorRowSqF.add(new BigDecimal(val));
          }
          floorRowSqF.add(new BigDecimal(totalVal));
          
          //Low Traffic
          boolean ltFlag = false;
          for(int ii=0; ii<modelQty; ii++) {
            if(ltFloorPercentA[ii]!=null) ltFlag = true;;
          }
          if(ltFlag) {
            //ArrayList ltFloorRowPr = new ArrayList();
            //floorTable.add(ltFloorRowPr);
            //ltFloorRowPr.add(minFloorType+". Low Traffic (%)");
            //for(int ii=0; ii<modelQty; ii++) {
            //  ltFloorRowPr.add(ltFloorPercentA[ii]);
            //}
            ArrayList ltFloorRowSqF = new ArrayList();
            floorTable.add(ltFloorRowSqF);
            ltFloorRowSqF.add(minFloorType+". Low Traffic (Sq.F)");
            for(int ii=0; ii<modelQty; ii++) {
              ltFloorRowSqF.add(ltFloorSqfA[ii]);
            }
            ltFloorRowSqF.add(null);
            totalVal = 0;
            for(int ii=0; ii<modelQty; ii++) {
              val = (ltFloorSqfA[ii]!=null)?ltFloorSqfA[ii].doubleValue()*facilityQtyA[ii]:0;
              totalVal += val;
              ltFloorRowSqF.add(new BigDecimal(val));
            }
            ltFloorRowSqF.add(new BigDecimal(totalVal));
          }
          //Medium Traffic
          boolean mtFlag = false;
          for(int ii=0; ii<modelQty; ii++) {
            if(mtFloorPercentA[ii]!=null) mtFlag = true;;
          }
          if(mtFlag) {
            //ArrayList mtFloorRowPr = new ArrayList();
            //floorTable.add(mtFloorRowPr);
            //mtFloorRowPr.add(minFloorType+". Medium Traffic (%)");
            //for(int ii=0; ii<modelQty; ii++) {
            //  mtFloorRowPr.add(mtFloorPercentA[ii]);
            //}
            ArrayList mtFloorRowSqF = new ArrayList();
            floorTable.add(mtFloorRowSqF);
            mtFloorRowSqF.add(minFloorType+". Medium Traffic (Sq.F)");
            for(int ii=0; ii<modelQty; ii++) {
              mtFloorRowSqF.add(mtFloorSqfA[ii]);
            }
            mtFloorRowSqF.add(null);
            totalVal = 0;
            for(int ii=0; ii<modelQty; ii++) {
              val = (mtFloorSqfA[ii]!=null)?mtFloorSqfA[ii].doubleValue()*facilityQtyA[ii]:0;
              totalVal += val;
              mtFloorRowSqF.add(new BigDecimal(val));
            }
            mtFloorRowSqF.add(new BigDecimal(totalVal));
          }
          // High Traffic
          boolean htFlag = false;
          for(int ii=0; ii<modelQty; ii++) {
            if(htFloorPercentA[ii]!=null) htFlag = true;;
          }
          if(htFlag) {
            //ArrayList htFloorRowPr = new ArrayList();
            //floorTable.add(htFloorRowPr);
            //htFloorRowPr.add(minFloorType+". High Traffic (%)");
            //for(int ii=0; ii<modelQty; ii++) {
            //  htFloorRowPr.add(htFloorPercentA[ii]);
            //}
            ArrayList htFloorRowSqF = new ArrayList();
            floorTable.add(htFloorRowSqF);
            htFloorRowSqF.add(minFloorType+". High Traffic (Sq.F)");
            for(int ii=0; ii<modelQty; ii++) {
              htFloorRowSqF.add(htFloorSqfA[ii]);
            }
            htFloorRowSqF.add(null);
            totalVal = 0;
            for(int ii=0; ii<modelQty; ii++) {
              val = (htFloorSqfA[ii]!=null)?htFloorSqfA[ii].doubleValue()*facilityQtyA[ii]:0;
              totalVal += val;
              htFloorRowSqF.add(new BigDecimal(val));
            }
            htFloorRowSqF.add(new BigDecimal(totalVal));
          }
        }

        
        //Create product page 
        GenericReportResultView productPageResult = 
           generateProductPage(productsUsedAL, diffCatalogFl, modelQty);
        
        //add legend
        ArrayList legend = new ArrayList();
        ArrayList rowBlank = new ArrayList();
        rowBlank.add(null);
        legend.add(rowBlank);
        ArrayList rowLegendTitle = new ArrayList();
        rowLegendTitle.add("Facilities:");
        legend.add(rowLegendTitle);
        ind = 1;
        for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          ArrayList rowModelName = new ArrayList();
          String modelName = ind + ". "+Utility.strNN(efD.getName());
          rowModelName.add(modelName);
          legend.add(rowModelName);
        }
        productPageResult.getTable().addAll(legend);
        resultV.add(0,productPageResult);

        
        
        //Create major category page
        GenericReportResultView mjCategoryPageResult = 
           generateMajorCategoryPage(productsUsedAL, diffCatalogFl, modelQty);
        mjCategoryPageResult.getTable().addAll(legend);
        resultV.add(1,mjCategoryPageResult);
        
        //Create category page
        GenericReportResultView categoryPageResult = 
           generateProductCategoryPage(productsUsedAL, diffCatalogFl, modelQty);
        categoryPageResult.getTable().addAll(legend);
        resultV.add(1,categoryPageResult);
        
        //Allocated Categories
        BigDecimal[] sumProductAmountA = new BigDecimal[modelQty];
        BigDecimal[] amountToAllocateA = new BigDecimal[modelQty];
        BigDecimal[] estimatedItemsFactorA = new BigDecimal[modelQty];

        ArrayList allocatedCategoriesAL = new ArrayList();
        ind = -1;
        for(Iterator iter=estimatorFacilityDV.iterator(),
                     iter1=productsUsedAL.iterator();
                     iter.hasNext();) {
          ind++;
          EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
          int facilityId = efD.getEstimatorFacilityId();
          AllocatedCategoryViewVector allocatedCategoryVwV = 
                                estimationEjb.getAllocatedCategories(facilityId);
          allocatedCategoryVwV = sortAllocCategoriesByName(allocatedCategoryVwV);
          allocatedCategoriesAL.add(allocatedCategoryVwV);
          EstimatorProdResultViewVector eprVwV = (EstimatorProdResultViewVector) iter1.next();
          double sumProductAmount = 0;
          
          for(Iterator iter2=eprVwV.iterator(); iter2.hasNext();) {
             EstimatorProdResultView eprVw = (EstimatorProdResultView) iter2.next();
             BigDecimal yearPrice = Utility.bdNN(eprVw.getYearPrice());
             if(yearPrice!=null) sumProductAmount += yearPrice.doubleValue();
          }
          BigDecimal estimatedItemsFactorBD = Utility.bdNN(efD.getEstimatedItemsFactor());
          estimatedItemsFactorA[ind] = estimatedItemsFactorBD;
          double estimatedItemsFactor = estimatedItemsFactorBD.doubleValue();
          double amountToAllocate = 0;
          if(estimatedItemsFactor >0.001) {
            amountToAllocate =  sumProductAmount*(100/estimatedItemsFactor -1);
          }

          sumProductAmountA[ind] = new BigDecimal(sumProductAmount);
          amountToAllocateA[ind] = new BigDecimal(amountToAllocate);
          for(Iterator iter2=allocatedCategoryVwV.iterator(); iter2.hasNext();) {
            AllocatedCategoryView acVw = (AllocatedCategoryView) iter2.next();
            AllocatedCategoryData acD = acVw.getAllocatedCategory();
            double percent = (Utility.bdNN(acD.getPercent())).doubleValue();
            double amountDb = percent * amountToAllocate /100;
            BigDecimal amountBD = 
                new BigDecimal(amountDb).setScale(2,BigDecimal.ROUND_HALF_UP);
            acVw.setAmount(amountBD);
            BigDecimal allFacilityAmountBD = 
                new BigDecimal(amountDb*efD.getFacilityQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
            acVw.setAllLocationsAmount(allFacilityAmountBD);
          }
        }
        GenericReportResultView allocCategoryPageResult = 
           generateAllocatedCategoryPage(allocatedCategoriesAL, diffCatalogFl, modelQty);

        ArrayList allocSummary = new ArrayList();
        allocSummary.add(rowBlank);
        ArrayList rowEstimatedItemsFactorBD = new ArrayList();
        ArrayList rowSumProductAmount = new ArrayList();
        ArrayList rowAmountToAllocate = new ArrayList();
        rowEstimatedItemsFactorBD.add("Allocated Items Factor (%)");
        rowSumProductAmount.add("All Products Year Amount ($)");
        rowAmountToAllocate.add("Amount to Allocate ($)");
        for(int ii=0; ii<modelQty; ii++) {
          rowEstimatedItemsFactorBD.add(estimatedItemsFactorA[ii]);
          rowSumProductAmount.add(sumProductAmountA[ii]);
          rowAmountToAllocate.add(amountToAllocateA[ii]);
        }
        allocSummary.add(rowEstimatedItemsFactorBD);
        allocSummary.add(rowSumProductAmount);
        allocSummary.add(rowAmountToAllocate);
        allocCategoryPageResult.getTable().addAll(allocSummary);

        allocCategoryPageResult.getTable().addAll(legend);
        resultV.add(0,allocCategoryPageResult);

        return resultV;
    }
//////////////////////////////////////////////////////////////////////
   private GenericReportResultView generateProductPage(ArrayList pProductsUsed, 
              boolean pDiffCatalogFl, int pModelQty)
   throws Exception 
   {
        //Create product page 
        GenericReportResultView productPageResult = GenericReportResultView.createValue();
        GenericReportColumnViewVector ppHeader = new GenericReportColumnViewVector();
        productPageResult.setHeader(ppHeader);
        productPageResult.setName("Products");
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Product Name",0,255,"VARCHAR2"));
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Size",2,20,"NUMBER"));
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Unit Code",0,255,"VARCHAR2"));
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Pack",15,15,"NUMBER"));
        if(!pDiffCatalogFl) {
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Price",2,20,"NUMBER"));
        } else {
          for(int ii=1; ii<=pModelQty; ii++) { 
             String hd = "Price "+ii;
             ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
          }
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Price Diff",0,1,"VARCHAR2"));
        }
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Year Quanity "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Year Price "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","",0,255,"VARCHAR2"));
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Total Qty "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        if(pModelQty>1) {
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Qty",2,20,"NUMBER"));
        }
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Total Price "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        if(pModelQty>1) {
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Total Price",2,20,"NUMBER"));
        }
        productPageResult.setColumnCount(ppHeader.size());
        
        ArrayList productPageTable = new ArrayList();
        productPageResult.setTable(productPageTable);
        Iterator[] iteratorA = new Iterator[pModelQty];
        int ind=-1;
        for(Iterator iter=pProductsUsed.iterator(); iter.hasNext();) {
          ind++;
          ArrayList prodL = (ArrayList) iter.next();
          Iterator it = prodL.iterator();
          iteratorA[ind] = it;
        }

        EstimatorProdResultView[] wrkProdA = new EstimatorProdResultView[pModelQty];
        for(int ii=0; ii<pModelQty; ii++) {
          wrkProdA[ii]=null;
        }
        while(true) {
          boolean exitFl = true;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null ||
                  iteratorA[ii].hasNext()) {
              exitFl = false;
              if(wrkProdA[ii]==null) wrkProdA[ii] = 
                                 (EstimatorProdResultView) iteratorA[ii].next();
            }
          }
          if(exitFl) break;
          String categMin = "";
          int skuMin = 0;
          boolean flag = false;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null) {
              String cc = Utility.strNN(wrkProdA[ii].getCategory());
              int sku = Integer.parseInt(wrkProdA[ii].getSkuNum());
              if(!flag) {
                categMin = cc;
                skuMin = sku;
                flag = true;
              } else {
                int comp = cc.compareTo(categMin);
                if(comp<0) {
                  categMin = cc;
                  skuMin = sku;
                } else if (comp==0) {
                  if(sku<skuMin) {
                    skuMin = sku;
                  }
                }
              }
            } 
          }
          flag = false;
          String category = null;
          String skuNum = null;
          String productName = null;
          BigDecimal size = null;
          String unitCd = null;
          Integer pack = null;
          BigDecimal[] priceA = new BigDecimal[(pDiffCatalogFl)?pModelQty:1];
          BigDecimal[] yearQtyA = new BigDecimal[pModelQty];
          BigDecimal[] yearPriceA = new BigDecimal[pModelQty];
          BigDecimal[] allFacilityYearQtyA = new BigDecimal[pModelQty];
          BigDecimal[] allFacilityYearPriceA = new BigDecimal[pModelQty];

          BigDecimal priceDiff = new BigDecimal(0);
          BigDecimal yearTotalQtySum = new BigDecimal(0);
          BigDecimal yearTotalPriceSum = new BigDecimal(0);
          boolean priceDiffFl = false;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null) {
              String cc = Utility.strNN(wrkProdA[ii].getCategory());
              int sku = Integer.parseInt(wrkProdA[ii].getSkuNum());
              BigDecimal price = Utility.bdNN(wrkProdA[ii].getProductPrice());
              BigDecimal yearQty = Utility.bdNN(wrkProdA[ii].getYearQty());
              BigDecimal yearPrice = Utility.bdNN(wrkProdA[ii].getYearPrice());
              if(categMin.equals(cc) && skuMin==sku) {
                if(!flag) {
                  flag = true;
                  if(pModelQty>1) {
                    priceDiff = price;
                  }
                  category = wrkProdA[ii].getCategory();
                  skuNum = wrkProdA[ii].getSkuNum();
                  productName =  wrkProdA[ii].getProductName();
                  size =  wrkProdA[ii].getUnitSize();
                  unitCd = wrkProdA[ii].getUnitCd();
                  pack = new Integer(wrkProdA[ii].getPackQty());
                  if(priceA.length==1) priceA[0] = wrkProdA[0].getProductPrice();                  
                } else {
                  if(priceDiff.subtract(price).abs().doubleValue()>0.000001) {
                    priceDiffFl = true;
                  }
                }
                if(priceA.length>1) priceA[ii] = price;
                yearQtyA[ii] = yearQty;
                yearPriceA[ii] = yearPrice;
                allFacilityYearQtyA[ii] = wrkProdA[ii].getAllFacilityYearQty();
                yearTotalQtySum = yearTotalQtySum.add(allFacilityYearQtyA[ii]);
                allFacilityYearPriceA[ii] = wrkProdA[ii].getAllFacilityYearPrice();
                yearTotalPriceSum = yearTotalPriceSum.add(allFacilityYearPriceA[ii]);
                wrkProdA[ii] = null;
              }
            }
          }
          //Create row
          ArrayList tableRow = new ArrayList();
          productPageTable.add(tableRow);
          tableRow.add(category);
          tableRow.add(skuNum);
          tableRow.add(productName);
          tableRow.add(size);
          tableRow.add(unitCd);
          tableRow.add(pack);
          if(!pDiffCatalogFl) {
            tableRow.add(priceA[0]);
          } else {
            for(int ii=0; ii<pModelQty; ii++) {
              tableRow.add(priceA[ii]);
            }
            tableRow.add(priceDiffFl?"+":null);
          }
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(yearQtyA[ii]);
          }
          
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(yearPriceA[ii]);
          }
          tableRow.add(null);          
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(allFacilityYearQtyA[ii]);
          }
          if(pModelQty>1) {
            tableRow.add(yearTotalQtySum);            
          }
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(allFacilityYearPriceA[ii]);
          }
          if(pModelQty>1) {
            tableRow.add(yearTotalPriceSum);            
          }
        }
     return productPageResult;    
   }
//////////////////////////////////////////////////////////////////////
   private GenericReportResultView generateProductCategoryPage(ArrayList pProductsUsed, 
              boolean pDiffCatalogFl, int pModelQty)
   throws Exception 
   {
        ArrayList categoriesUsedAL = new ArrayList();
        for(Iterator iter=pProductsUsed.iterator(); iter.hasNext();) {
          EstimatorProdResultViewVector categories = new EstimatorProdResultViewVector();
          categoriesUsedAL.add(categories);
          EstimatorProdResultViewVector products = (EstimatorProdResultViewVector) iter.next();
          EstimatorProdResultView categoryEprVw = null;
          String prevCategory = "";
          for(Iterator iter1=products.iterator(); iter1.hasNext();) {
            EstimatorProdResultView eprVw = (EstimatorProdResultView) iter1.next();
            String category = Utility.strNN(eprVw.getCategory());
            if(categoryEprVw==null || !prevCategory.equals(category)) {
              categoryEprVw = eprVw.copy();
              categories.add(categoryEprVw);
              prevCategory = category;
              continue;
            }
            BigDecimal yearPrice = categoryEprVw.getYearPrice().add(eprVw.getYearPrice());
            BigDecimal allFacilityYearPrice = 
              categoryEprVw.getAllFacilityYearPrice().add(eprVw.getAllFacilityYearPrice());
            categoryEprVw.setYearPrice(yearPrice);
            categoryEprVw.setAllFacilityYearPrice(allFacilityYearPrice);
          }
          
        }
        GenericReportResultView categoryPageResult = GenericReportResultView.createValue();
        GenericReportColumnViewVector ppHeader = new GenericReportColumnViewVector();
        categoryPageResult.setHeader(ppHeader);
        categoryPageResult.setName("Product Categories");
        String difColumnType = (pModelQty>2)? "java.lang.String":"java.math.BigDecimal";
        int difColumnDec = (pModelQty>2)? 0:2;
        int difColumnLen = (pModelQty>2)? 1:20;
        String difColumnBdType = (pModelQty>2)? "VARCHAR2":"NUMBER";
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Year Price "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        ppHeader.add(ReportingUtils.createGenericReportColumnView(difColumnType,"",difColumnDec,difColumnLen,difColumnBdType));
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "All Facility Price "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        if(pModelQty>1) {
          ppHeader.add(ReportingUtils.createGenericReportColumnView(difColumnType,"Total Price",difColumnDec,difColumnLen,difColumnBdType));
        }
        categoryPageResult.setColumnCount(ppHeader.size());
        
        ArrayList categoryPageTable = new ArrayList();
        categoryPageResult.setTable(categoryPageTable);
        Iterator[] iteratorA = new Iterator[pModelQty];
        int ind=-1;
        for(Iterator iter=categoriesUsedAL.iterator(); iter.hasNext();) {
          ind++;
          ArrayList categL = (ArrayList) iter.next();
          Iterator it = categL.iterator();
          iteratorA[ind] = it;
        }

        EstimatorProdResultView[] wrkProdA = new EstimatorProdResultView[pModelQty];
        for(int ii=0; ii<pModelQty; ii++) {
          wrkProdA[ii]=null;
        }
        while(true) {
          boolean exitFl = true;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null ||
                  iteratorA[ii].hasNext()) {
              exitFl = false;
              if(wrkProdA[ii]==null) wrkProdA[ii] = 
                                 (EstimatorProdResultView) iteratorA[ii].next();
            }
          }
          if(exitFl) break;
          String categMin = "";
          boolean flag = false;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null) {
              String cc = Utility.strNN(wrkProdA[ii].getCategory());
              if(!flag) {
                categMin = cc;
                flag = true;
              } else {
                int comp = cc.compareTo(categMin);
                if(comp<0) {
                  categMin = cc;
                }
              }
            } 
          }
          flag = false;
          String category = null;
          BigDecimal[] yearPriceA = new BigDecimal[pModelQty];
          BigDecimal[] allFacilityYearPriceA = new BigDecimal[pModelQty];
          BigDecimal yearPriceTotal = new BigDecimal(0);
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null) {
              String cc = Utility.strNN(wrkProdA[ii].getCategory());
              BigDecimal yearPrice = Utility.bdNN(wrkProdA[ii].getYearPrice());
              if(categMin.equals(cc)) {
                if(!flag) {
                  flag = true;
                  category = wrkProdA[ii].getCategory();
                }
                yearPriceA[ii] = yearPrice;
                allFacilityYearPriceA[ii] = wrkProdA[ii].getAllFacilityYearPrice();
                yearPriceTotal = yearPriceTotal.add(allFacilityYearPriceA[ii]);
                wrkProdA[ii] = null;
              }
            }
          }
          //Create row
          ArrayList tableRow = new ArrayList();
          categoryPageTable.add(tableRow);
          tableRow.add(category);
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(yearPriceA[ii]);
          }
          tableRow.add(null);
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(allFacilityYearPriceA[ii]);
          }
          if(pModelQty>1) {
            tableRow.add(yearPriceTotal);
          }
        }
     return categoryPageResult;    
   }

//////////////////////////////////////////////////////////////////////
   private GenericReportResultView generateMajorCategoryPage(ArrayList pProductsUsed, 
              boolean pDiffCatalogFl, int pModelQty)
   throws Exception 
   {
        ArrayList categoriesUsedAL = new ArrayList();
        for(Iterator iter=pProductsUsed.iterator(); iter.hasNext();) {
          EstimatorProdResultViewVector categories = new EstimatorProdResultViewVector();
          categoriesUsedAL.add(categories);
          EstimatorProdResultViewVector products = (EstimatorProdResultViewVector) iter.next();
          EstimatorProdResultView categoryEprVw = null;
          String prevCategory = "";
          for(Iterator iter1=products.iterator(); iter1.hasNext();) {
            EstimatorProdResultView eprVw = (EstimatorProdResultView) iter1.next();
            String category = Utility.strNN(eprVw.getMajorCategory());
            if(categoryEprVw==null || !prevCategory.equals(category)) {
              categoryEprVw = eprVw.copy();
              categories.add(categoryEprVw);
              prevCategory = category;
              continue;
            }
            BigDecimal yearPrice = categoryEprVw.getYearPrice().add(eprVw.getYearPrice());
            BigDecimal allFacilityYearPrice = 
              categoryEprVw.getAllFacilityYearPrice().add(eprVw.getAllFacilityYearPrice());
            categoryEprVw.setYearPrice(yearPrice);
            categoryEprVw.setAllFacilityYearPrice(allFacilityYearPrice);
          }
          
        }
        GenericReportResultView categoryPageResult = GenericReportResultView.createValue();
        GenericReportColumnViewVector ppHeader = new GenericReportColumnViewVector();
        categoryPageResult.setHeader(ppHeader);
        categoryPageResult.setName("Major Categories");
        String difColumnType = (pModelQty>2)? "java.lang.String":"java.math.BigDecimal";
        int difColumnDec = (pModelQty>2)? 0:2;
        int difColumnLen = (pModelQty>2)? 1:20;
        String difColumnBdType = (pModelQty>2)? "VARCHAR2":"NUMBER";
        ppHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Year Price "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        ppHeader.add(ReportingUtils.createGenericReportColumnView(difColumnType,"",difColumnDec,difColumnLen,difColumnBdType));
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "All Facility Price "+ii;
          ppHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        if(pModelQty>1) {
          ppHeader.add(ReportingUtils.createGenericReportColumnView(difColumnType,"Total Price",difColumnDec,difColumnLen,difColumnBdType));
        }
        categoryPageResult.setColumnCount(ppHeader.size());
        
        ArrayList categoryPageTable = new ArrayList();
        categoryPageResult.setTable(categoryPageTable);
        Iterator[] iteratorA = new Iterator[pModelQty];
        int ind=-1;
        for(Iterator iter=categoriesUsedAL.iterator(); iter.hasNext();) {
          ind++;
          ArrayList categL = (ArrayList) iter.next();
          Iterator it = categL.iterator();
          iteratorA[ind] = it;
        }

        EstimatorProdResultView[] wrkProdA = new EstimatorProdResultView[pModelQty];
        BigDecimal[] totalYearPriceA = new BigDecimal[pModelQty];
        BigDecimal[] totalAllFacilityYearPriceA = new BigDecimal[pModelQty];

        for(int ii=0; ii<pModelQty; ii++) {
          wrkProdA[ii]=null;
          totalYearPriceA[ii] = new BigDecimal(0);
          totalAllFacilityYearPriceA[ii] = new BigDecimal(0);
        }

        
        while(true) {
          boolean exitFl = true;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null ||
                  iteratorA[ii].hasNext()) {
              exitFl = false;
              if(wrkProdA[ii]==null) wrkProdA[ii] = 
                                 (EstimatorProdResultView) iteratorA[ii].next();
            }
          }
          if(exitFl) break;
          String categMin = "";
          boolean flag = false;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null) {
              String cc = Utility.strNN(wrkProdA[ii].getMajorCategory());
              if(!flag) {
                categMin = cc;
                flag = true;
              } else {
                int comp = cc.compareTo(categMin);
                if(comp<0) {
                  categMin = cc;
                }
              }
            } 
          }
          flag = false;
          String category = null;
          BigDecimal[] yearPriceA = new BigDecimal[pModelQty];
          BigDecimal[] allFacilityYearPriceA = new BigDecimal[pModelQty];
          BigDecimal yearPriceTotal = new BigDecimal(0);
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkProdA[ii]!=null) {
              String cc = Utility.strNN(wrkProdA[ii].getMajorCategory());
              BigDecimal yearPrice = Utility.bdNN(wrkProdA[ii].getYearPrice());
              if(categMin.equals(cc)) {
                if(!flag) {
                  flag = true;
                  category = wrkProdA[ii].getMajorCategory();
                }
                yearPriceA[ii] = yearPrice;
                allFacilityYearPriceA[ii] = wrkProdA[ii].getAllFacilityYearPrice();
                yearPriceTotal = yearPriceTotal.add(allFacilityYearPriceA[ii]);
                wrkProdA[ii] = null;
              }
            }
          }
          //Create row
          ArrayList tableRow = new ArrayList();
          categoryPageTable.add(tableRow);
          tableRow.add(category);
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(yearPriceA[ii]);
            if(yearPriceA[ii]!=null) {
              totalYearPriceA[ii] = totalYearPriceA[ii].add(yearPriceA[ii]);
            }
          }
          tableRow.add(null);
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(allFacilityYearPriceA[ii]);
            if(allFacilityYearPriceA[ii]!=null) {
              totalAllFacilityYearPriceA[ii] = 
                      totalAllFacilityYearPriceA[ii].add(allFacilityYearPriceA[ii]);
            }
          }
          if(pModelQty>1) {
            tableRow.add(yearPriceTotal);
          }
        }
        //Create total row
        BigDecimal totalYearPriceTotal = new BigDecimal(0);
        ArrayList tableRow = new ArrayList();
        categoryPageTable.add(tableRow);
        tableRow.add(null);

        tableRow = new ArrayList();
        categoryPageTable.add(tableRow);
        tableRow.add("Total");
        for(int ii=0; ii<pModelQty; ii++) {
          tableRow.add(totalYearPriceA[ii]);
        }
        tableRow.add(null);
        for(int ii=0; ii<pModelQty; ii++) {
          tableRow.add(totalAllFacilityYearPriceA[ii]);
          totalYearPriceTotal = 
                  totalYearPriceTotal.add(totalAllFacilityYearPriceA[ii]);
        }
        if(pModelQty>1) {
          tableRow.add(totalYearPriceTotal);
        }
     return categoryPageResult;    
   }
//////////////////////////////////////////////////////////////////////
   private GenericReportResultView generateAllocatedCategoryPage(ArrayList pAllocatedCategoriesAL, 
              boolean pDiffCatalogFl, int pModelQty)
   throws Exception 
   {
        GenericReportResultView allocCategoryPageResult = GenericReportResultView.createValue();
        GenericReportColumnViewVector acHeader = new GenericReportColumnViewVector();
        allocCategoryPageResult.setHeader(acHeader);
        allocCategoryPageResult.setName("Allocated Categories");
        acHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Allocated Category",0,255,"VARCHAR2"));
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Allocated Percent "+ii;
          acHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "Year Amount"+ii;
          acHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        acHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","",0,255,"VARCHAR2"));
        for(int ii=1; ii<=pModelQty; ii++) { 
          String hd = "All Facility Year Amount"+ii;
          acHeader.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",hd,2,20,"NUMBER"));
        }
        if(pModelQty>1) {
          acHeader.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Total",0,255,"VARCHAR2"));
        }

        allocCategoryPageResult.setColumnCount(acHeader.size());
        
        ArrayList allocCategoryPageTable = new ArrayList();
        allocCategoryPageResult.setTable(allocCategoryPageTable);
        Iterator[] iteratorA = new Iterator[pModelQty];
        int ind=-1;
        for(Iterator iter=pAllocatedCategoriesAL.iterator(); iter.hasNext();) {
          ind++;
          ArrayList categL = (ArrayList) iter.next();
          Iterator it = categL.iterator();
          iteratorA[ind] = it;
        }

        AllocatedCategoryView[] wrkCatA = new AllocatedCategoryView[pModelQty];
        for(int ii=0; ii<pModelQty; ii++) {
          wrkCatA[ii]=null;
        }
        while(true) {
          boolean exitFl = true;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkCatA[ii]!=null ||
                  iteratorA[ii].hasNext()) {
              exitFl = false;
              if(wrkCatA[ii]==null) wrkCatA[ii] = 
                                 (AllocatedCategoryView) iteratorA[ii].next();
            }
          }
          if(exitFl) break;
          String categMin = "";
          boolean flag = false;
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkCatA[ii]!=null) {
              AllocatedCategoryData acD = wrkCatA[ii].getAllocatedCategory();
              String cc = Utility.strNN(acD.getName());
              if(!flag) {
                categMin = cc;
                flag = true;
              } else {
                int comp = cc.compareTo(categMin);
                if(comp<0) {
                  categMin = cc;
                }
              }
            } 
          }
          flag = false;
          String category = null;
          BigDecimal[] allocPercentA = new BigDecimal[pModelQty];
          BigDecimal[] yearAmountA = new BigDecimal[pModelQty];
          BigDecimal[] allLocationYearAmountA = new BigDecimal[pModelQty];
          BigDecimal yearAmountTotal = new BigDecimal(0);
          for(int ii=0; ii<pModelQty; ii++) {
            if(wrkCatA[ii]!=null) {
              AllocatedCategoryData acD = wrkCatA[ii].getAllocatedCategory();
              String cc = Utility.strNN(acD.getName());
              BigDecimal allocPercent = Utility.bdNN(acD.getPercent());
              BigDecimal yearAmount = Utility.bdNN(wrkCatA[ii].getAmount());
              BigDecimal allLocationYearAmount = 
                            Utility.bdNN(wrkCatA[ii].getAllLocationsAmount());
              wrkCatA[ii] = null;
              if(categMin.equals(cc)) {
                allocPercentA[ii] = allocPercent;
                yearAmountA[ii] = yearAmount;
                allLocationYearAmountA[ii] = allLocationYearAmount;
                yearAmountTotal = yearAmountTotal.add(allLocationYearAmount);              
              }
            }
          }
          //Create row
          ArrayList tableRow = new ArrayList();
          allocCategoryPageTable.add(tableRow);
          tableRow.add(categMin);
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(allocPercentA[ii]);
          }
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(yearAmountA[ii]);
          }
          tableRow.add(null);
          for(int ii=0; ii<pModelQty; ii++) {
            tableRow.add(allLocationYearAmountA[ii]);
          }
          if(pModelQty>1) {
            tableRow.add(yearAmountTotal);
          }
        }
     return allocCategoryPageResult;    
   }
  ///////////////////////////////////////////////////////////////////////    
  //      header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer Num",0,255,"VARCHAR2"));
  //      header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Billing Qty",15,15,"NUMBER"));
  //      header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Table Base Cost",2,20,"NUMBER"));
  //      header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Base Cost Eff Date",0,0,"DATE"));
  ///////////////////////////////////////////////////////////////////////    

    
    private Map mBusEntMap;


    public com.cleanwise.service.api.APIAccess getAPIAccess()
	throws javax.naming.NamingException
    {

        return new com.cleanwise.service.api.APIAccess();
    }

    private static EstimatorProdResultViewVector
                      sortByCategorySku(EstimatorProdResultViewVector pProducts) 
    {
       Object[] estProdResVwA = pProducts.toArray();
       if(estProdResVwA.length<=1) {
         return pProducts;
       }
       for(int ii=0; ii<estProdResVwA.length-1; ii++) {
         boolean exitFl = true;
         for(int jj=0; jj<estProdResVwA.length-ii-1; jj++) {
           EstimatorProdResultView eprVw1 = (EstimatorProdResultView) estProdResVwA[jj];
           EstimatorProdResultView eprVw2 = (EstimatorProdResultView) estProdResVwA[jj+1];
           String mjCat1 = Utility.strNN(eprVw1.getMajorCategory());
           String mjCat2 = Utility.strNN(eprVw2.getMajorCategory());
           int mjCatComFl = mjCat1.compareToIgnoreCase(mjCat2);
           if(mjCatComFl>0) {
             estProdResVwA[jj] = eprVw2;
             estProdResVwA[jj+1] = eprVw1;
             exitFl = false;
           } else if(mjCatComFl==0) {
             String category1 = Utility.strNN(eprVw1.getCategory());
             String category2 = Utility.strNN(eprVw2.getCategory());
             int compFl = category1.compareToIgnoreCase(category2);
             if(compFl>0) {
               estProdResVwA[jj] = eprVw2;
               estProdResVwA[jj+1] = eprVw1;
               exitFl = false;
             } else if(compFl==0) {
               String skuNumS1 = eprVw1.getSkuNum();
               String skuNumS2 = eprVw2.getSkuNum();
               int skuNum1 = Integer.parseInt(skuNumS1);
               int skuNum2 = Integer.parseInt(skuNumS2);
               if(skuNum1>skuNum2) {
                 estProdResVwA[jj] = eprVw2;
                 estProdResVwA[jj+1] = eprVw1;
                 exitFl = false;
               }
             }
           }
         }
         if(exitFl) {
           break;
         }
       }
       pProducts = new EstimatorProdResultViewVector();
       for(int ii=0; ii<estProdResVwA.length; ii++) {
         EstimatorProdResultView eprVw = (EstimatorProdResultView) estProdResVwA[ii];
         pProducts.add(eprVw);
       }
       return pProducts;
    }

    private static AllocatedCategoryViewVector
                      sortAllocCategoriesByName(AllocatedCategoryViewVector pAllocatedCategories) 
    {
       Object[] allocCatVwA = pAllocatedCategories.toArray();
       if(allocCatVwA.length<=1) {
         return pAllocatedCategories;
       }
       for(int ii=0; ii<allocCatVwA.length-1; ii++) {
         boolean exitFl = true;
         for(int jj=0; jj<allocCatVwA.length-ii-1; jj++) {
           AllocatedCategoryView acVw1 = (AllocatedCategoryView) allocCatVwA[jj];
           AllocatedCategoryView acVw2 = (AllocatedCategoryView) allocCatVwA[jj+1];
           AllocatedCategoryData acD1 = acVw1.getAllocatedCategory();
           AllocatedCategoryData acD2 = acVw2.getAllocatedCategory();
           String category1 = Utility.strNN(acD1.getName());
           String category2 = Utility.strNN(acD2.getName());
           int compFl = category1.compareToIgnoreCase(category2);
           if(compFl>0) {
             allocCatVwA[jj] = acVw2;
             allocCatVwA[jj+1] = acVw1;
             exitFl = false;
           }
         }
         if(exitFl) {
           break;
         }
       }
       pAllocatedCategories = new AllocatedCategoryViewVector();
       for(int ii=0; ii<allocCatVwA.length; ii++) {
         AllocatedCategoryView acVw = (AllocatedCategoryView) allocCatVwA[ii];
         pAllocatedCategories.add(acVw);
       }
       return pAllocatedCategories;
    }

    
    
}
