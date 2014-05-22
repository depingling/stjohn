
package com.cleanwise.view.logic;

import java.util.*;
import java.util.Map.Entry;
import java.util.Set;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.SpendingEstimatorForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;

/**
 *  <code>SpendingEstimatorLogic</code>
 *
 *@author     YKupershmidt
 *@created    October 13, 2004
 */
public class SpendingEstimatorLogic {
    public static final int PAPER_PLUS_STEP_SHIFT = 0;
    public static final int FLOOR_STEP_SHIFT = 1;
    public static final int RESTROOM_STEP_SHIFT = 2;
    public static final int ALLOCATED_STEP_SHIFT = 3;
    public static final int OTHER_STEP_SHIFT = 4;
    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       EstimatorFacilityDataVector estimatorFacilityDV =
                               estimationEjb.getUserEstimatorFacilities(userId);
       EstimatorFacilityDataVector userModels = new EstimatorFacilityDataVector();
       EstimatorFacilityDataVector templates = new EstimatorFacilityDataVector();
       for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
         EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
         if("T".equals(efD.getTemplateFl())) {
           templates.add(efD);
         } else {
           userModels.add(efD);
         }
       }
      
       pForm.setFacilities(userModels);       
       pForm.setTemplates(templates);

       CatalogDataVector catalogDV = 
                               estimationEjb.getUserEstimatorCatalogs(userId);
       pForm.setCatalogs(catalogDV);

       pForm.setFacilityJoin(null);

       pForm.setSelectedPage(0);

       EstimatorFacilityData efD = estimationEjb.getDefaultFacilityProfile();
       pForm.setDefaultFacility(efD);
    }

    public static ActionErrors initNewModel(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       ae = loadTemplateModel(request,form);
       if(ae.size()>0) {
         return ae;
       }
       return ae;       
       
    }

    private static void  inintFacilityUiFields(SpendingEstimatorForm pForm,  
                 Estimation pEstimationEjb) 
    throws Exception
    {
       EstimatorFacilityData efD = pForm.getDefaultFacility();
       pForm.setName("");
       pForm.setFacilityGroup("");
       pForm.setNewFacilityGroup("");
       pForm.setTemplateFl(false);
       pForm.setFacilityQty("1");
       pForm.setWorkingDayYearQty(""+efD.getWorkingDayYearQty());
       pForm.setStationQty("0");
       pForm.setBathroomQty(""+efD.getBathroomQty());

       pForm.setToiletBathroomQty(bdToString(efD.getToiletBathroomQty()));

       pForm.setShowerQty(""+efD.getShowerQty());
       
       pForm.setVisitorBathroomPer(bdToString(efD.getVisitorBathroomPercent()));
       pForm.setVisitorToiletTissuePer(bdToString(efD.getVisitorToiletTissuePercent()));
       pForm.setVisitorQty(""+efD.getVisitorQty());
       pForm.setFacilityTypeCd(efD.getFacilityTypeCd());
       pForm.setAppearanceStandardCd(RefCodeNames.APPEARANCE_STANDARD_CD.MEDIUM);
       pForm.setPersonnelQty("");
       pForm.setGrossFootage("");
       pForm.setCleanableFootagePercent(bdToString(efD.getCleanableFootagePercent()));
       pForm.setNetCleanableFootage("");
       pForm.setBaseboardPercent(bdToString(efD.getBaseboardPercent()));
       pForm.setEstimatedItemsFactor(bdToString(efD.getEstimatedItemsFactor()));

       String[] floorTypes = pForm.getFloorTypes();
       String[] floorTypePercents = pForm.getFloorTypePercents();
       String[] floorTypePercentsHt = pForm.getFloorTypePercentsHt();
       String[] floorTypePercentsMt = pForm.getFloorTypePercentsMt();
       String[] floorTypePercentsLt = pForm.getFloorTypePercentsLt();
       for(int ii=0; ii<floorTypes.length; ii++) {
         floorTypePercents[ii] = "";
         floorTypePercentsHt[ii] = "";
         floorTypePercentsMt[ii] = "";
         floorTypePercentsLt[ii] = "";
       }
       pForm.setFloorTypePercents(floorTypePercents);
       pForm.setFloorTypePercentsHt(floorTypePercentsHt);
       pForm.setFloorTypePercentsMt(floorTypePercentsMt);
       pForm.setFloorTypePercentsLt(floorTypePercentsLt);
              
       pForm.setWashHandPerDay(bdToString(efD.getWashHandQty()));
       pForm.setTissueUsagePerDay(bdToString(efD.getTissueUsageQty()));
       pForm.setSmallLinerPerDay(bdToString(efD.getReceptacleLinerRatio()));
       pForm.setCommonAreaLinerPerDay(""+efD.getCommonAreaReceptacleQty());
       pForm.setToiletLinerPerDay(bdToString(efD.getToiletLinerRatio()));
       pForm.setLargeLinerPerDay(bdToString(efD.getLargeLinerRatio()));
       pForm.setLargeLinerCaLinerQty(bdToString(efD.getLargeLinerCaLinerQty()));

       pForm.setPaperPlusProcessStep(2);
       pForm.setFloorProcessStep(2);
       pForm.setRestroomProcessStep(2);
       pForm.setOtherProcessStep(2);
     
    }
    public static ActionErrors prepareProfilePage(HttpServletRequest request,
            ActionForm form) 
      throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       String templateFlS = facility.getTemplateFl();
       if("T".equals(templateFlS)) {
         pForm.setTemplateFl(true);
       } else {
         pForm.setTemplateFl(false);
       }
       ae = loadAllocatedCategories(request,form);
       return ae;
             
    }
    
    public static ActionErrors saveProfile(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       CatalogDataVector catalogDV = pForm.getCatalogs();
       if(catalogDV==null || catalogDV.size()==0) {
           String errorMess = "No product catalogs available to create a model";
           ae.add("error",
           new ActionError("error.simpleGenericError",errorMess));
           return ae;
       }
             
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       String userName = appUser.getUser().getUserName();

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       if(facilityJoin==null) {
         facilityJoin = EstimatorFacilityJoinView.createValue();
       }
       
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       if(facility==null) {
         facility = EstimatorFacilityData.createValue();
       }

       int paperPlusProcessStep = 2;
       int floorProcessStep = 2;
       int restroomProcessStep = 2;
       int allocatedProcessStep = 2;
       int otherProcessStep = 2;
       if(facility!=null) {
         int processStep = facility.getProcessStep();
         if(processStep!=0) {
           paperPlusProcessStep = getProcessStep(facility,PAPER_PLUS_STEP_SHIFT);
           floorProcessStep = getProcessStep(facility,FLOOR_STEP_SHIFT);
           restroomProcessStep = getProcessStep(facility,RESTROOM_STEP_SHIFT);
           allocatedProcessStep = getProcessStep(facility,ALLOCATED_STEP_SHIFT);
           otherProcessStep = getProcessStep(facility,OTHER_STEP_SHIFT);
         }
       }

       
       facilityJoin.setEstimatorFacility(facility);

       int catalogId = pForm.getCatalogId();
       if(catalogId<=0) {
         String errorMess = "No Product Catalog selected";
         ae.add("errorName",
         new ActionError("error.simpleGenericError",errorMess));
       }
       if(catalogId!=facility.getCatalogId()) {
         paperPlusProcessStep = 2;
         floorProcessStep = 2;
         restroomProcessStep = 2;
         otherProcessStep = 2;
       }

       String name = pForm.getName();
       if(name==null || name.trim().length()==0) {
         String errorMess = "Empty filed Facility Name";
         ae.add("errorName",
         new ActionError("error.simpleGenericError",errorMess));
       }
       String facilityGroupSelect = request.getParameter("facilityGroupSelect");
       String facilityGroup = null;
       if("2".equals(facilityGroupSelect)) {
         facilityGroup = Utility.strNN(pForm.getFacilityGroup());
       } else {
         facilityGroup = Utility.strNN(pForm.getNewFacilityGroup());
         pForm.setFacilityGroup(facilityGroup);
         pForm.setNewFacilityGroup("");
       }
       facilityGroup = facilityGroup.trim();

       String facilityQtyS = pForm.getFacilityQty();
       int facilityQty = stringToInt(facilityQtyS,true,ae, 
                           new Integer(0),null, "Number of Facilities",0);

       String workingDayYearQtyS = pForm.getWorkingDayYearQty();
       int workingDayYearQty = stringToInt(workingDayYearQtyS,true,ae, 
                           new Integer(0),null, "Working Days per Year",0);

       String facilityTypeCd = pForm.getFacilityTypeCd();
       if(!facilityTypeCd.equals(facility.getFacilityTypeCd())) {
         paperPlusProcessStep = 2;
       }

       String stationQtyS = pForm.getStationQty();
       boolean flag = false;
       if(RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(facilityTypeCd) ||
          RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(facilityTypeCd)) {
         flag = true;
       }       
       
       int stationQty = stringToInt(stationQtyS,flag,ae, 
                         new Integer(0),null, "Number of Stations",0);
       
       String bathroomQtyS = pForm.getBathroomQty();
       int bathroomQty = stringToInt(bathroomQtyS,true,ae, 
                         new Integer(0),null, "Number of Bathrooms",0);

       String toiletBathroomQtyS = pForm.getToiletBathroomQty();
       BigDecimal toiletBathroomQty = stringToBD(toiletBathroomQtyS,false,ae, 
                   new BigDecimal(0), null, "Number of Toilets per Bathroom",0);
       facility.setToiletBathroomQty(toiletBathroomQty);
       
       String ShowerQtyS = pForm.getShowerQty();
       int showerQty = stringToInt(ShowerQtyS,false,ae, 
                         new Integer(0),null, "Number of Showers",0);

       String commonAreaReceptacleQtyS = pForm.getCommonAreaLinerPerDay();
       int commonAreaReceptacleQty = stringToInt(commonAreaReceptacleQtyS,false,ae,
         new Integer(0), null, "Common Area Receptacals", 0 );

       String visitorBathroomPerS = pForm.getVisitorBathroomPer();
       BigDecimal visitorBathroomPer = stringToBD(visitorBathroomPerS,false,ae, 
                   new BigDecimal(0), new BigDecimal(100), "Vistors Using Bathroom",0);
       

       //String appearanceStandardCd = pForm.getAppearanceStandardCd();
       //if(!appearanceStandardCd.equals(facility.getAppearanceStandardCd())) {
       //  paperPlusProcessStep = 2);
       //}
       
       String visitorQtyS = pForm.getVisitorQty();
       int visitorQty = stringToInt(visitorQtyS,false,ae, 
                         new Integer(0),null, "Number of Visitors",0);
       
       String personnelQtyS = pForm.getPersonnelQty();
       int personnelQty = stringToInt(personnelQtyS,true,ae, 
                         new Integer(0),null, "Estimated Number of Personnel",0);

       String grossFootageS = pForm.getGrossFootage();
       int grossFootage = stringToInt(grossFootageS, false, ae, 
           new Integer(1), null, "Gross Footage", 0);

       String cleanableFootagePercentS = pForm.getCleanableFootagePercent();
       BigDecimal cleanableFootagePercent = 
         stringToBD(cleanableFootagePercentS,true,ae,  
         new BigDecimal(0), new BigDecimal(100),"Cleanable Percent",0);
       
       String netCleanableFootageS = pForm.getNetCleanableFootage();
       int netCleanableFootage = stringToInt(netCleanableFootageS,true, ae, 
           new Integer(1), null, "Net Cleanable Footage", 0);

       String baseboardPercentS = pForm.getBaseboardPercent();
       BigDecimal baseboardPercent = 
         stringToBD(baseboardPercentS,false,ae,  
         new BigDecimal(0), new BigDecimal(100), "Baseboard Percent",0);
       BigDecimal oldBaseboardPercent = facility.getBaseboardPercent();
       if(oldBaseboardPercent==null) oldBaseboardPercent = new BigDecimal(0);
       double oldBaseboardPercentDb = oldBaseboardPercent.doubleValue();
       double baseboardPercentDb = baseboardPercent.doubleValue();
       if(oldBaseboardPercentDb>=0.0001 && baseboardPercentDb<0.0001 ||
          oldBaseboardPercentDb<0.0001 && baseboardPercentDb>=0.0001
          ) {
         floorProcessStep = 2;
       }
       

       String estimatedItemsFactorS = pForm.getEstimatedItemsFactor();
       BigDecimal estimatedItemsFactor = 
         stringToBD(estimatedItemsFactorS,true,ae,  
         new BigDecimal(0), new BigDecimal(100), "Estimated Items Factor",0);

       if(ae.size()>0) {
         return ae;
       }
              
       //Floors
       String floorMachine = pForm.getFloorMachine();
       facility.setFloorMachine(floorMachine);
       
       String[] floorTypes = pForm.getFloorTypes();
       String[] floorTypePercents = pForm.getFloorTypePercents();
       String[] floorTypePercentsHt = pForm.getFloorTypePercentsHt();
       String[] floorTypePercentsMt = pForm.getFloorTypePercentsMt();
       String[] floorTypePercentsLt = pForm.getFloorTypePercentsLt();
       double floorPr = 0;
       for(int ii=0; ii<floorTypes.length; ii++) {
         double htPr = 0;
         double mtPr = 0;
         double ltPr = 0;
         if(floorTypePercents[ii] !=null && floorTypePercents[ii].trim().length()>0) {
           double dd = 0;
           boolean exceptionFl = false;
           try {
             dd = Double.parseDouble(floorTypePercents[ii]);
             floorPr += dd;
           } catch(Exception exc) {
             exceptionFl = true;
           }
           if(dd<0 || dd>100 || exceptionFl) {
             String errorMess = "Invalid value of "+floorTypes[ii]+
                 " floor percent: "+floorTypePercents[ii];
             ae.add("errorFloorTypePer"+ii,
             new ActionError("error.simpleGenericError",errorMess));
           }
           if(dd==0) {
             continue;
           }
           if(floorTypePercentsHt[ii] !=null && floorTypePercentsHt[ii].trim().length()>0) {
             exceptionFl = false;
             try {
               htPr = Double.parseDouble(floorTypePercentsHt[ii]);
             } catch(Exception exc) {
               exceptionFl = true;
             }
             if(htPr<0 || htPr>100 || exceptionFl) {
               String errorMess = "Invalid value of "+floorTypes[ii]+ 
                   " high traffic floor percent: "+floorTypePercentsHt[ii];
               ae.add("errorFloorTypePerHt"+ii,
               new ActionError("error.simpleGenericError",errorMess));
             }
           }
           if(floorTypePercentsMt[ii] !=null && floorTypePercentsMt[ii].trim().length()>0) {
             exceptionFl = false;
             try {
               mtPr = Double.parseDouble(floorTypePercentsMt[ii]);
             } catch(Exception exc) {
               exceptionFl = true;
             }
             if(mtPr<0 || mtPr>100 || exceptionFl) {
               String errorMess = "Invalid value of "+floorTypes[ii]+ 
                   " medium traffic floor percent: "+floorTypePercentsMt[ii];
               ae.add("errorFloorTypePerMt"+ii,
               new ActionError("error.simpleGenericError",errorMess));
             }
           }
           if(floorTypePercentsLt[ii] !=null && floorTypePercentsLt[ii].trim().length()>0) {
             exceptionFl = false;
             try {
               ltPr = Double.parseDouble(floorTypePercentsLt[ii]);
             } catch(Exception exc) {
               exceptionFl = true;
             }
             if(ltPr<0 || ltPr>100 || exceptionFl) {
               String errorMess = "Invalid value of "+floorTypes[ii]+ 
                   " low traffic floor percent: "+floorTypePercentsLt[ii];
               ae.add("errorFloorTypePerLt"+ii,
               new ActionError("error.simpleGenericError",errorMess));
             }
           }
           if(Math.abs(htPr + mtPr + ltPr -100)>0.0005) {
             String errorMess = "Sum percent of "+floorTypes[ii]+ 
                   " type floor should be 100";
               ae.add("errorFloorTypePerLt"+ii,
               new ActionError("error.simpleGenericError",errorMess));
           }
         }
       }
       if(Math.abs(floorPr-100)>0.0005) {
         String errorMess = "Sum of all type floors should be 100";
         ae.add("errorFloorTotalPer",
         new ActionError("error.simpleGenericError",errorMess));
       }
       
       if(ae.size()>0) {
         return ae;
       }

       
       FacilityFloorDataVector floors = facilityJoin.getFacilityFloors();
       if(floors==null){
          floors = new FacilityFloorDataVector();
          facilityJoin.setFacilityFloors(floors);
       }
       boolean floorChangeFl = false;

       for(int ii=0; ii<floorTypes.length; ii++) {
         double flTypePr = 0;
         double htPr = 0;
         double mtPr = 0;
         double ltPr = 0;
         boolean removeFl = false;
         if(floorTypePercents[ii] !=null && floorTypePercents[ii].trim().length()>0) {
           flTypePr = Double.parseDouble(floorTypePercents[ii]);
           if(flTypePr<0.0001) {
             removeFl = true;
           }
           if(floorTypePercentsHt[ii] !=null && floorTypePercentsHt[ii].trim().length()>0) {
             htPr = Double.parseDouble(floorTypePercentsHt[ii]);
           }
           if(floorTypePercentsMt[ii] !=null && floorTypePercentsMt[ii].trim().length()>0) {
             mtPr = Double.parseDouble(floorTypePercentsMt[ii]);
           }
           if(floorTypePercentsLt[ii] !=null && floorTypePercentsLt[ii].trim().length()>0) {
            ltPr = Double.parseDouble(floorTypePercentsLt[ii]);
           }
         }
         BigDecimal flTypePrBD = new BigDecimal(flTypePr);
         flTypePrBD = flTypePrBD.setScale(2,BigDecimal.ROUND_HALF_UP);
         BigDecimal htPrBD = new BigDecimal(htPr);
         htPrBD = htPrBD.setScale(2,BigDecimal.ROUND_HALF_UP);
         BigDecimal mtPrBD = new BigDecimal(mtPr);
         mtPrBD = mtPrBD.setScale(2,BigDecimal.ROUND_HALF_UP);
         BigDecimal ltPrBD = new BigDecimal(ltPr);
         ltPrBD = ltPrBD.setScale(2,BigDecimal.ROUND_HALF_UP);
         boolean notFoundFl = true;
         for(Iterator iter=floors.iterator();iter.hasNext();) {
           FacilityFloorData ffD = (FacilityFloorData) iter.next();
           String floorType = ffD.getFloorTypeCd();
           if(floorTypes[ii].equals(floorType)) {
             notFoundFl = false;
             if(removeFl) {
               iter.remove();
               break;
             }
             BigDecimal oldFlTypePrBD = ffD.getCleanablePercent();
             if(flTypePrBD.doubleValue()>0) {
               ffD.setCleanablePercent(flTypePrBD);
               if(isAddedRemoved(htPrBD,ffD.getCleanablePercentHt(),4)) {
                 floorChangeFl = true;
               }
               ffD.setCleanablePercentHt(htPrBD);
               if(isAddedRemoved(mtPrBD,ffD.getCleanablePercentMt(),4)) {
                 floorChangeFl = true;
               }
               ffD.setCleanablePercentMt(mtPrBD);
               if(isAddedRemoved(ltPrBD,ffD.getCleanablePercentLt(),4)) {
                 floorChangeFl = true;
               }
               ffD.setCleanablePercentLt(ltPrBD);
             } else {
               iter.remove();
               if(oldFlTypePrBD!=null && oldFlTypePrBD.abs().doubleValue()>0.00001) {
                 floorChangeFl = true;
               }
             }
             break;
           }
         }
         if(notFoundFl) {
           if(flTypePrBD!=null && flTypePrBD.abs().doubleValue()>0.00001) {
             FacilityFloorData ffD = FacilityFloorData.createValue();
             floors.add(ffD);
             ffD.setFloorTypeCd(floorTypes[ii]);
             ffD.setCleanablePercent(flTypePrBD);
             ffD.setCleanablePercentHt(htPrBD);
             ffD.setCleanablePercentMt(mtPrBD);
             ffD.setCleanablePercentLt(ltPrBD);
             floorChangeFl = true;
           }
         }
       }
       boolean templateFl = false;
       if(pForm.getTemplateFl()) {
         facility.setTemplateFl("T");
         templateFl = true;
       } else {
         facility.setTemplateFl("F");
       }

       if(floorChangeFl && templateFl) {          
         floorProcessStep = 2;
       }
       facility.setCatalogId(catalogId);
       facility.setName(name);
       facility.setFacilityGroup(facilityGroup);
       facility.setFacilityTypeCd(facilityTypeCd);
       facility.setWorkingDayYearQty(workingDayYearQty);
       facility.setFacilityQty(facilityQty);
       facility.setStationQty(stationQty);
       facility.setBathroomQty(bathroomQty);
       facility.setShowerQty(showerQty);
       //facility.setAppearanceStandardCd(appearanceStandardCd);
       facility.setCommonAreaReceptacleQty(commonAreaReceptacleQty);
       facility.setVisitorBathroomPercent(visitorBathroomPer);
       facility.setVisitorQty(visitorQty);
       facility.setPersonnelQty(personnelQty);       
       facility.setGrossFootage(grossFootage);
       facility.setCleanableFootagePercent(cleanableFootagePercent);
       facility.setNetCleanableFootage(netCleanableFootage);
       facility.setBaseboardPercent(baseboardPercent);
       facility.setEstimatedItemsFactor(estimatedItemsFactor);
       facility.setFacilityStatusCd(RefCodeNames.FACILITY_STATUS_CD.ACTIVE);
       setProcessStep(facility,PAPER_PLUS_STEP_SHIFT,paperPlusProcessStep);
       setProcessStep(facility,FLOOR_STEP_SHIFT,floorProcessStep);
       setProcessStep(facility,RESTROOM_STEP_SHIFT,restroomProcessStep);
       setProcessStep(facility,ALLOCATED_STEP_SHIFT,allocatedProcessStep);
       setProcessStep(facility,OTHER_STEP_SHIFT,otherProcessStep);

       facilityJoin = estimationEjb.saveEstimatorProfile(facilityJoin,userName);
       pForm.setFacilityJoin(facilityJoin);

       pForm.setPaperPlusProcessStep(paperPlusProcessStep);
       pForm.setFloorProcessStep(floorProcessStep);
       pForm.setRestroomProcessStep(restroomProcessStep);
       pForm.setOtherProcessStep(otherProcessStep);
       pForm.setPaperPlusProdResults(null);
       pForm.setFloorProdResults(null);
       pForm.setRestroomProdResults(null);
       if(paperPlusProcessStep>=3) {
         calculatePaperPlusResults(request,form);
       }
       if(floorProcessStep>=3) {
         ae = calculateFloorResults(request, form);
       }

       if(restroomProcessStep>=3) {
         ae = calculateRestroomResults(request, form);
       }
       
       if(otherProcessStep>=3) {
         ae = calculateOtherResults(request, form);
       }
       ae = calculateAllocatedAmounts(request,form);
       
       setFacilityGroups(pForm);
       return ae;       
    }
    
    public static ActionErrors createModelFromTemplate(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       CatalogDataVector catalogDV = pForm.getCatalogs();
       if(catalogDV==null || catalogDV.size()==0) {
           String errorMess = "No product catalogs available to create a model";
           ae.add("error",
           new ActionError("error.simpleGenericError",errorMess));
           return ae;
       }
             
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       String userName = appUser.getUser().getUserName();

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData efD = facilityJoin.getEstimatorFacility();
       
       String name = pForm.getName();
       if(name==null || name.trim().length()==0) {
         String errorMess = "Empty filed Facility Name";
         ae.add("errorName",
         new ActionError("error.simpleGenericError",errorMess));
       }
       if(ae.size()>0) {
         return ae;
       }
       
       String facilityGroup = "";
       
       
       int templateId = efD.getTemplateFacilityId();
       
       efD = estimationEjb.createModelFromTemplate(templateId, 
                                      name, userName); 

       int facilityId = efD.getEstimatorFacilityId();
       ae = loadModel(request,form,facilityId);
       if(ae.size()>0) {
         return ae;
       }
       pForm.setSelectedPage(1);
       return ae;       
       
    }

    public static ActionErrors loadModel(HttpServletRequest request,
            ActionForm form) 
             throws Exception {
       ActionErrors ae = new ActionErrors();
       String facilityIdS = request.getParameter("id");
       int facilityId = Integer.parseInt(facilityIdS);
       ae = loadModel(request,form,facilityId);
       return ae;
             
    }

    public static ActionErrors loadModel(HttpServletRequest request,
            ActionForm form, int pFacilityId)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       CatalogDataVector catalogDV = pForm.getCatalogs();
       
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();

       
       EstimatorFacilityJoinView facilityJoin = 
                      estimationEjb.getEstimatiorProfile(pFacilityId);
       pForm.setFacilityJoin(facilityJoin);
       EstimatorFacilityData efD = facilityJoin.getEstimatorFacility();
       ae = scatterFacilityFields(form, efD);
       if(ae.size()>0) {
         return ae;
       }
       int templateId = efD.getTemplateFacilityId();
       if(templateId>0) {
         try {
           EstimatorFacilityJoinView templateJoin = 
                      estimationEjb.getEstimatiorProfile(templateId);
           EstimatorFacilityData template = templateJoin.getEstimatorFacility();
           pForm.setDefaultFacilityJoin(templateJoin);
           pForm.setDefaultFacility(template);
         } catch(Exception exc) {}
       }

       FacilityFloorDataVector ffDV = facilityJoin.getFacilityFloors();
       setUiFloorPercentage(pForm,ffDV);
       
       int paperPlusProcessStep = getProcessStep(efD,PAPER_PLUS_STEP_SHIFT);
       int floorProcessStep = getProcessStep(efD,FLOOR_STEP_SHIFT);
       int restroomProcessStep = getProcessStep(efD,RESTROOM_STEP_SHIFT);
       int allocatedProcessStep = getProcessStep(efD,ALLOCATED_STEP_SHIFT);
       int otherProcessStep = getProcessStep(efD,OTHER_STEP_SHIFT);
       pForm.setPaperPlusProcessStep(paperPlusProcessStep);
       pForm.setFloorProcessStep(floorProcessStep);
       pForm.setRestroomProcessStep(restroomProcessStep);
       pForm.setAllocatedProcessStep(allocatedProcessStep);
       pForm.setOtherProcessStep(otherProcessStep);

       ae = loadPaperPlus(request, form);
       if(ae.size()>0) {
         pForm.setPaperPlusProcessStep(2);
       }
       if(paperPlusProcessStep>=3) {
         ae = calculatePaperPlusResults(request,form);
         if(ae.size()>0) {
           pForm.setPaperPlusProcessStep(2);
         }
       } else {
         pForm.setPaperPlusProdResults(null);
       }


       ae = loadFloor(request,form);
       if(ae.size()>0) {
         pForm.setFloorProcessStep(2);
       }
       if(floorProcessStep>=3) {
         ae = calculateFloorResults(request, form);
         if(ae.size()>0) {
           pForm.setFloorProcessStep(2);
         }
       } else {
         pForm.setFloorProdResults(null);
       }

       ae = loadRestroom(request,form);
       if(ae.size()>0) {
         pForm.setRestroomProcessStep(2);
       }
       if(restroomProcessStep>=3) {
         ae = calculateRestroomResults(request, form);
         if(ae.size()>0) {
           pForm.setRestroomProcessStep(2);
         }
       } else {
         pForm.setRestroomProdResults(null);
       }

       ae = loadOther(request, form);
       if(ae.size()>0) {
         return ae;
       }
       if(otherProcessStep>=3) {
         ae = calculateOtherResults(request,form);
         if(ae.size()>0) {
           pForm.setOtherProcessStep(2);
         }
       } else {
         pForm.setOtherProdResults(null);
       }

       ae = prepareProducts(request, form);
       if(ae.size()>0) {
         return ae;
       }

       loadAllocatedCategories(request, form);
       
       setFacilityGroups(pForm);
       return ae;       
       
    }
    
    private static void setUiFloorPercentage(SpendingEstimatorForm pForm,
      FacilityFloorDataVector pFloors)
    {
       String[] floorTypes = pForm.getFloorTypes();
       String[] floorTypePercents = pForm.getFloorTypePercents();
       String[] floorTypePercentsHt = pForm.getFloorTypePercentsHt();
       String[] floorTypePercentsMt = pForm.getFloorTypePercentsMt();
       String[] floorTypePercentsLt = pForm.getFloorTypePercentsLt();
       for(int ii=0; ii<floorTypes.length; ii++) {
         floorTypePercents[ii] = "";
         floorTypePercentsHt[ii] = "";
         floorTypePercentsMt[ii] = "";
         floorTypePercentsLt[ii] = "";
       }
       if(pFloors!=null) {
         for(Iterator iter=pFloors.iterator();iter.hasNext();) {
           FacilityFloorData ffD = (FacilityFloorData) iter.next();
           String floorType = ffD.getFloorTypeCd();
           for(int ii=0; ii<floorTypes.length; ii++) {
             if(floorTypes[ii].equals(floorType)) {
               BigDecimal total = ffD.getCleanablePercent();
               if(total!=null && total.doubleValue()>0.005) {
                 total = total.setScale(2,BigDecimal.ROUND_HALF_UP);
                 floorTypePercents[ii] = total.toString();
               }
               BigDecimal htPer = ffD.getCleanablePercentHt();
               if(htPer!=null && htPer.doubleValue()>0.005) {
                 htPer = htPer.setScale(2,BigDecimal.ROUND_HALF_UP);
                 floorTypePercentsHt[ii] = htPer.toString();
               }
               BigDecimal mtPer = ffD.getCleanablePercentMt();
               if(mtPer!=null && mtPer.doubleValue()>0.005) {
                 mtPer = mtPer.setScale(2,BigDecimal.ROUND_HALF_UP);
                 floorTypePercentsMt[ii] = mtPer.toString();
               }
               BigDecimal ltPer = ffD.getCleanablePercentLt();
               if(ltPer!=null && ltPer.doubleValue()>0.005) {
                 ltPer = ltPer.setScale(2,BigDecimal.ROUND_HALF_UP);
                 floorTypePercentsLt[ii] = ltPer.toString();
               }
               break;
             }
           }
         }
       }
       pForm.setFloorTypePercents(floorTypePercents);
       pForm.setFloorTypePercentsHt(floorTypePercentsHt);
       pForm.setFloorTypePercentsMt(floorTypePercentsMt);
       pForm.setFloorTypePercentsLt(floorTypePercentsLt);
    }

    public static ActionErrors scatterFacilityFields(
            ActionForm form, EstimatorFacilityData pFacility)
    throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
  
       pForm.setName(pFacility.getName());
       pForm.setFacilityGroup(pFacility.getFacilityGroup());
       pForm.setNewFacilityGroup("");
       String templateFlS = pFacility.getTemplateFl();
       if("T".equals(templateFlS)) {
         pForm.setTemplateFl(true);
       } else {
         pForm.setTemplateFl(false);
       }
       pForm.setFacilityQty(""+pFacility.getFacilityQty());
       pForm.setCatalogId(pFacility.getCatalogId());
       pForm.setFacilityTypeCd(pFacility.getFacilityTypeCd());
       pForm.setWorkingDayYearQty(""+pFacility.getWorkingDayYearQty());

       pForm.setPersonnelQty(""+pFacility.getPersonnelQty());
       pForm.setVisitorQty(""+pFacility.getVisitorQty());
       pForm.setStationQty(""+pFacility.getStationQty());

       pForm.setAppearanceStandardCd(pFacility.getAppearanceStandardCd());
       pForm.setBathroomQty(""+pFacility.getBathroomQty());
       pForm.setToiletBathroomQty(bdToString(pFacility.getToiletBathroomQty()));       
       pForm.setVisitorBathroomPer(bdToString(pFacility.getVisitorBathroomPercent()));
       pForm.setVisitorToiletTissuePer(bdToString(pFacility.getVisitorToiletTissuePercent()));
       pForm.setShowerQty(""+pFacility.getShowerQty());

       pForm.setGrossFootage(""+pFacility.getGrossFootage());
       pForm.setCleanableFootagePercent(bdToString(pFacility.getCleanableFootagePercent()));
       pForm.setNetCleanableFootage(""+pFacility.getNetCleanableFootage());
       pForm.setBaseboardPercent(bdToString(pFacility.getBaseboardPercent()));
       pForm.setEstimatedItemsFactor(bdToString(pFacility.getEstimatedItemsFactor()));
       
       pForm.setWashHandPerDay(bdToString(pFacility.getWashHandQty()));
       pForm.setTissueUsagePerDay(bdToString(pFacility.getTissueUsageQty()));
       pForm.setSmallLinerPerDay(bdToString(pFacility.getReceptacleLinerRatio()));
       pForm.setCommonAreaLinerPerDay(""+pFacility.getCommonAreaReceptacleQty());
       pForm.setToiletLinerPerDay(bdToString(pFacility.getToiletLinerRatio()));
       pForm.setLargeLinerPerDay(bdToString(pFacility.getLargeLinerRatio()));
       pForm.setLargeLinerCaLinerQty(bdToString(pFacility.getLargeLinerCaLinerQty()));

       return ae;
    }

  public static ActionErrors delModel(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String facilityIdS = request.getParameter("id");
       int facilityId = Integer.parseInt(facilityIdS);
       estimationEjb.deleteModelFromDb(facilityId);
       pForm.setFacilityJoin(null);
       inintFacilityUiFields(pForm,estimationEjb);
       
       EstimatorFacilityDataVector efDV = pForm.getFacilities();
       EstimatorFacilityData efD = null;
       for(Iterator iter = efDV.iterator(); iter.hasNext();) {
         efD = (EstimatorFacilityData) iter.next();
         if(facilityId==efD.getEstimatorFacilityId()) {
           iter.remove();
           break;
         }
       }
       efDV = pForm.getTemplates();
       for(Iterator iter = efDV.iterator(); iter.hasNext();) {
         efD = (EstimatorFacilityData) iter.next();
         if(facilityId==efD.getEstimatorFacilityId()) {
           iter.remove();
           break;
         }
       }
       return ae;       
       
    }
    
  public static ActionErrors copyModel(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       UserData userD = appUser.getUser();
       String userName = userD.getUserName();
       String facilityIdS = request.getParameter("id");
       int facilityId = Integer.parseInt(facilityIdS);
       EstimatorFacilityData efD = estimationEjb.copyModel(facilityId, null, userName);
       
       
       EstimatorFacilityDataVector efDV = pForm.getFacilities();
       efDV.add(0,efD);
       
       loadModel(request,form,efD.getEstimatorFacilityId());
       
       return ae;       
   }
    
    public static ActionErrors loadRestroom(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       
       int facilityId = 0;
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       if(facilityJoin!=null) {
          EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
          if(facility!=null) {
            facilityId = facility.getEstimatorFacilityId();
          }
       }
       
       ArrayList scheduleTypes = new ArrayList();
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.RESTROOM_CLEANING);
       CleaningScheduleJoinViewVector restroomCleaningSchedules = 
                estimationEjb.getCleaningSchedules (facilityId, scheduleTypes); 
       pForm.setRestroomCleaningSchedules(restroomCleaningSchedules);
       
       return ae;
    }

    public static ActionErrors saveAssumptions(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       int selectedPage = pForm.getSelectedPage();
       if(selectedPage==2) {
         ae = savePaperPlusAssumptions(request,form);
       } else if(selectedPage==3) {
         ae = saveFloorAssumptions(request,form);
       } else if(selectedPage==4) {
         ae = saveRestroomAssumptions(request,form);
       }
       return ae;
    }
    public static ActionErrors savePaperPlusAssumptions(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();

       
       ae = analyzePaperSoapLinerAssump(pForm, facility, ae);
       if(ae.size()>0) {
         return ae;
       }
       
       int paperPlusProcessStep = getProcessStep(facility,PAPER_PLUS_STEP_SHIFT);
       if(paperPlusProcessStep<=1) {
         paperPlusProcessStep=2;
       }
       pForm.setPaperPlusProcessStep(paperPlusProcessStep);
       setProcessStep(facility,PAPER_PLUS_STEP_SHIFT,paperPlusProcessStep);
       estimationEjb.saveEstimatorProfile(facilityJoin,userName);
       if(paperPlusProcessStep>=3) {
         calculatePaperPlusResults(request,form);
       }
       
       
       return ae;
    }
    
    private static ActionErrors analyzePaperSoapLinerAssump(
            SpendingEstimatorForm pForm, EstimatorFacilityData facility, ActionErrors ae)
    throws Exception {

       String washHandPerDayS = pForm.getWashHandPerDay();
       BigDecimal washHandPerDay = stringToBD(washHandPerDayS,true,ae,
         new BigDecimal(0), null, "Hand Wash Number", 0 );
       
       
       String tissueUsagePerDayS = pForm.getTissueUsagePerDay();
       BigDecimal tissueUsagePerDay = stringToBD(tissueUsagePerDayS,true,ae,
         new BigDecimal(0), null, "Toilet Tissue Usage", 0 );

       String visitorToiletTissuePerS = pForm.getVisitorToiletTissuePer();
       BigDecimal visitorToiletTissuePer = stringToBD(visitorToiletTissuePerS,false,ae, 
                   new BigDecimal(0), new BigDecimal(100), "Vistors Toilet Tissue",0);

       String smallLinerPerDayS = pForm.getSmallLinerPerDay();
       BigDecimal smallLinerPerDay = stringToBD(smallLinerPerDayS,true,ae,
         new BigDecimal(0), null, "Trash Receptacal Liner Number", 0 );

       String commonAreaReceptacleQtyS = pForm.getCommonAreaLinerPerDay();
       int commonAreaReceptacleQty = stringToInt(commonAreaReceptacleQtyS,false,ae,
         new Integer(0), null, "Common Area Receptacals", 0 );


       String toiletLinerPerDayS = pForm.getToiletLinerPerDay();
       BigDecimal toiletLinerPerDay = stringToBD(toiletLinerPerDayS,true,ae,
         new BigDecimal(0), null, "Toilet Liners", 0 );

       String facilityTypeCd = pForm.getFacilityTypeCd();
       String errorInfo = "Container Liner";
       if(RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL.equals(facilityTypeCd)||
          RefCodeNames.FACILITY_TYPE_CD.OFFICE.equals(facilityTypeCd)) {
         errorInfo = errorInfo + " - Personal Liners";
       } else if(RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(facilityTypeCd)||
            RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(facilityTypeCd)) {
         errorInfo = errorInfo + " - Station Liners";
       }
       String largeLinerPerDayS = pForm.getLargeLinerPerDay();
       BigDecimal largeLinerPerDay = stringToBD(largeLinerPerDayS,true,ae,
         new BigDecimal(0), null, errorInfo, 0 );

       String largeLinerCaLinerQtyS = pForm.getLargeLinerCaLinerQty();
       BigDecimal largeLinerCaLinerQty = stringToBD(largeLinerCaLinerQtyS,false,ae,
         new BigDecimal(0), null, "Container Liner - Common Area Liners", 0 );
       
       
       if(ae.size()>0) {
         return ae;
       }

       facility.setWashHandQty(washHandPerDay);
       facility.setTissueUsageQty(tissueUsagePerDay);
       facility.setVisitorToiletTissuePercent(visitorToiletTissuePer);
       facility.setReceptacleLinerRatio(smallLinerPerDay);
       facility.setCommonAreaReceptacleQty(commonAreaReceptacleQty);
       facility.setToiletLinerRatio(toiletLinerPerDay);
       facility.setLargeLinerRatio(largeLinerPerDay);
       facility.setLargeLinerCaLinerQty(largeLinerCaLinerQty);

       return ae;
    }
              
    public static ActionErrors saveFloorAssumptions(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
              
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();

       String cleanableFootagePercentS = pForm.getCleanableFootagePercent();
       BigDecimal cleanableFootagePercent = 
                    stringToBD(cleanableFootagePercentS,false,ae,
         new BigDecimal(0), new BigDecimal(100), "Cleanable Sq. Footage Percent", 0 );

       
       String baseboardPercentS = pForm.getBaseboardPercent();
       BigDecimal baseboardPercent = stringToBD(baseboardPercentS,true,ae,
         new BigDecimal(0), new BigDecimal(100), "Baseboard Percent", 0 );
       
       if(ae.size()>0) {
         return ae;
       }

       facility.setCleanableFootagePercent(cleanableFootagePercent);
       facility.setBaseboardPercent(baseboardPercent);
              

       int floorProcessStep = getProcessStep(facility,FLOOR_STEP_SHIFT);
       if(floorProcessStep<=2) {
         floorProcessStep=2;
       }
       pForm.setFloorProcessStep(floorProcessStep);
       setProcessStep(facility,FLOOR_STEP_SHIFT,floorProcessStep);
       estimationEjb.saveEstimatorProfile(facilityJoin,userName);
       if(floorProcessStep==3) {
         calculateFloorResults(request,form);
       }
       return ae;
    }

    public static ActionErrors saveRestroomAssumptions(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
              
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();

       String toiletBathroomQtyS = pForm.getToiletBathroomQty();
       BigDecimal toiletBathroomQty = 
                    stringToBD(toiletBathroomQtyS,false,ae,
         new BigDecimal(0), null, "Number of toilets", 0 );

       if(ae.size()>0) {
         return ae;
       }
       facility.setToiletBathroomQty(toiletBathroomQty);       
              
       int restroomProcessStep = getProcessStep(facility,RESTROOM_STEP_SHIFT);
       if(restroomProcessStep<=2) {
         restroomProcessStep=2;
       }
       pForm.setRestroomProcessStep(restroomProcessStep);
       setProcessStep(facility,RESTROOM_STEP_SHIFT,restroomProcessStep);
       estimationEjb.saveEstimatorProfile(facilityJoin,userName);
       if(restroomProcessStep==3) {
         calculateRestroomResults(request,form);
       }
       return ae;
    }

       
    public static ActionErrors loadFloor(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       FacilityFloorDataVector floors = null;
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = (facility!=null)? facility.getEstimatorFacilityId():0;
       
       boolean[] floorFl = pForm.getFloorTypeFl();
       boolean[] floorFlHt = pForm.getFloorTypeFlHt();
       boolean[] floorFlMt = pForm.getFloorTypeFlMt();
       boolean[] floorFlLt = pForm.getFloorTypeFlLt();
       boolean defShowFl = false;

       if("T".equalsIgnoreCase(facility.getTemplateFl())) {
         floors = facilityJoin.getFacilityFloors();
         defShowFl = true;
       } else {
         EstimatorFacilityJoinView defaultEfjVw = pForm.getDefaultFacilityJoin();
         if(defaultEfjVw==null) {
           String errorMess = "Model doesn't have template ";
           ae.add("errNoTemplate",
              new ActionError("error.simpleGenericError",errorMess));
           return ae;
         }
         floors = defaultEfjVw.getFacilityFloors();
       }

       for(int ii=0; ii<floorFl.length; ii++) {
         floorFl[ii] = defShowFl;
         floorFlHt[ii] = defShowFl;
         floorFlMt[ii] = defShowFl;
         floorFlLt[ii] = defShowFl;
       }

       ArrayList scheduleTypes = new ArrayList();
       for(Iterator iter = floors.iterator(); iter.hasNext(); ) {
         FacilityFloorData ffD = (FacilityFloorData) iter.next();
         BigDecimal clPr = ffD.getCleanablePercent();    
         BigDecimal htPr = ffD.getCleanablePercentHt();
         BigDecimal mtPr = ffD.getCleanablePercentMt();
         BigDecimal ltPr = ffD.getCleanablePercentLt();
         if(clPr==null || clPr.doubleValue()<0.001) {
           continue;
         }
         String floorTypeCd = ffD.getFloorTypeCd();
         String[] floorTypes = pForm.getFloorTypes();
         int index = 0;
         for(; index<floorTypes.length; index++) {
           if(floorTypes[index].equals(floorTypeCd)) {
             break;
           }
         }
         if(htPr!=null && htPr.doubleValue()>0.001) {
           floorFl[index] = true;
           floorFlHt[index] = true;
           if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING);
           }
         }
         if(mtPr!=null && mtPr.doubleValue()>0.001) {
           floorFl[index] = true;
           floorFlMt[index] = true;
           if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING);
           }
         }
         if(ltPr!=null && ltPr.doubleValue()>0.001) {
           floorFl[index] = true;
           floorFlLt[index] = true;
           if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING);
           } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
             scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING);
           }
         }
       }

       
         
       CleaningScheduleJoinViewVector floorSchedules = 
                estimationEjb.getCleaningSchedules (facilityId, scheduleTypes); 
       pForm.setFloorCleaningSchedules(floorSchedules);

       return ae;       
       
    }
    
    public static ActionErrors saveProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       int selectedPage = pForm.getSelectedPage();
       if(selectedPage==2) {
         ae=savePaperPlusProducts(request,form);
       } else if(selectedPage==3) {
         ae=saveFloorProducts(request,form);
       } else if(selectedPage==4) {
         ae=saveRestroomProducts(request,form);
       } else if(selectedPage==6) {
         ae=saveProductSelection(request,form);
       } else if(selectedPage==7) {
         ae=saveOtherProducts(request,form);
       }
       return ae;
    }

    public static ActionErrors savePaperPlusProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       CleaningScheduleJoinViewVector csjVwV = pForm.getPaperPlusSupplySchedules();
       int ind = -1;
       for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
         ind ++;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         CleaningScheduleData csD = csjVw.getSchedule();
         String cleaningScheduleCd = csD.getCleaningScheduleCd();
         CleaningSchedStructJoinViewVector cssjVwV =  csjVw.getStructure();
         int ind1 = -1;
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           ind1 ++;
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           CleaningProcData cpD = cssjVw.getProcedure();
           String procName = cpD.getShortDesc();
           BigDecimal sumPercent = new BigDecimal(0);
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           int ind2 = 0;
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ProdApplData paD = pajVw.getProdAppl();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             csjVw.setFrequency("1");
             BigDecimal sharingBD =  stringToBD (sharingS,false,ae, 
               new BigDecimal(0),new BigDecimal(100),
               "Sharing percent", (((ind*1000+ind1)*1000)+ind2));
             sumPercent = sumPercent.add(sharingBD);
             
             if(RefCodeNames.CLEANING_SCHEDULE_CD.CONTAINER_LINER_SUPPLY.
                                      equals(cleaningScheduleCd) ||
                RefCodeNames.CLEANING_SCHEDULE_CD.BATHROOM_LINER_SUPPLY.
                                      equals(cleaningScheduleCd) ||
                RefCodeNames.CLEANING_SCHEDULE_CD.OFFICE_LINER_SUPPLY.
                                      equals(cleaningScheduleCd) ||
                RefCodeNames.CLEANING_SCHEDULE_CD.COMMONON_AREA_LINER_SUPPLY.
                                      equals(cleaningScheduleCd)
               ) {
             } else {
               String usageRateS = pajVw.getUsageRate();
               BigDecimal usageRateBD =  stringToBD (usageRateS,true,ae, 
                                                 new BigDecimal(0),null,
                                   "Usage rate", (((ind*1000+ind1)*1000)+ind2));
             }
           }
           
           if(Math.abs(sumPercent.doubleValue())>0.0001 &&
              Math.abs(sumPercent.doubleValue()-100)>0.0001) {
             String errorMess = "Sum sharing percent for "+procName + " is not 100";
             ae.add("errSumPercent"+(ind*1000+ind1),
                new ActionError("error.simpleGenericError",errorMess));
           }
         }
       }
       if(ae.size()>0) {
         return ae;
       }

       ArrayList cleaningScheduleTypeCdAL = new ArrayList();
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.HAND_SOAP_SUPPLY);
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.OFFICE_LINER_SUPPLY);
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.COMMONON_AREA_LINER_SUPPLY);
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.CONTAINER_LINER_SUPPLY);
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.BATHROOM_LINER_SUPPLY);
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.PAPER_TOWEL_SUPPLY);
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.SEAT_COVER_SUPPLY);
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.TOILET_TISSUE_SUPPLY);
       estimationEjb.saveCleaningSchedules(facilityId, csjVwV, cleaningScheduleTypeCdAL, 0, userName);

       ae = loadPaperPlus(request,form);
       if(ae.size()>0) {
         return ae;
       }
       
       ae = calculatePaperPlusResults(request,form);
       if(ae.size()>0) {
         return ae;
       }
       
       //Set process step 
       setProcessStep(facility,PAPER_PLUS_STEP_SHIFT,3);
       pForm.setPaperPlusProcessStep(3);
       return ae;       
       
    }

    public static ActionErrors saveFloorProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       CleaningScheduleJoinViewVector csjVwV = pForm.getFloorCleaningSchedules();
       int ind = -1;
       for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
         ind ++;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         String frequencyS = csjVw.getFrequency();
         if(!Utility.isSet(frequencyS)) {
           continue;
         }
         BigDecimal frequencyBD = stringToBD (frequencyS,false,ae, 
           new BigDecimal(0),null,"Process Frequency", ind);

         String procTimePeriodCd = csjVw.getTimePeriodCd();
         CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
         int ind1 = -1;
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           ind1 ++;
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           CleaningProcData cpD = cssjVw.getProcedure();
           String procName = cpD.getShortDesc();
           BigDecimal sumPercent = new BigDecimal(0);
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           int ind2 = 0;
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             BigDecimal sharingBD =  stringToBD (sharingS,false,ae, 
               new BigDecimal(0),new BigDecimal(100),
               "Sharing percent", (((ind*1000+ind1)*1000)+ind2));
             sumPercent = sumPercent.add(sharingBD);
             
             String dilutionRateS = pajVw.getDilutionRate();
             if(Utility.isSet(dilutionRateS)) {              
               BigDecimal dilutionRateBD =  stringToBD (dilutionRateS,false,ae, 
                 new BigDecimal(0),null,
                 "Dilution rate", (((ind*1000+ind1)*1000)+ind2));
             }
             
             String usageRateS = pajVw.getUsageRate();
             BigDecimal usageRateBD =  stringToBD (usageRateS,true,ae, 
               new BigDecimal(0),null,
               "Usage rate", (((ind*1000+ind1)*1000)+ind2));
           }
   /*           
           if(Math.abs(sumPercent.doubleValue())>0.0001 &&
              Math.abs(sumPercent.doubleValue()-100)>0.0001) {
             String errorMess = "Sum sharing percent for "+procName + " is not 100";
             ae.add("errSumPercent"+(ind*1000+ind1),
                new ActionError("error.simpleGenericError",errorMess));
           }
    */
         }
       }

       if(ae.size()>0) {
         return ae;
       }
       
       ArrayList scheduleTypes = new ArrayList();
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING);

       estimationEjb.saveCleaningSchedules(facilityId, csjVwV, scheduleTypes, 1, userName);
       ae = loadFloor(request,form);
       if(ae.size()>0) {
         return ae;
       }
       
       ae = calculateFloorResults(request,form);
       if(ae.size()>0) {
         return ae;
       }
       //Set process step 
       setProcessStep(facility,FLOOR_STEP_SHIFT,3);
       pForm.setFloorProcessStep(3);
       return ae;       
       
    }

    public static ActionErrors saveRestroomProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       CleaningScheduleJoinViewVector csjVwV = pForm.getRestroomCleaningSchedules();
       int ind = -1;
       for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
         ind ++;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         String frequencyS = csjVw.getFrequency();
         if(!Utility.isSet(frequencyS)) {
           continue;
         }
         BigDecimal frequencyBD = stringToBD (frequencyS,false,ae, 
           new BigDecimal(0),null,"Process Frequency", ind);

         String procTimePeriodCd = csjVw.getTimePeriodCd();
         CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
         int ind1 = -1;
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           ind1 ++;
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           CleaningProcData cpD = cssjVw.getProcedure();
           String procName = cpD.getShortDesc();
           BigDecimal sumPercent = new BigDecimal(0);
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           int ind2 = 0;
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             BigDecimal sharingBD =  stringToBD (sharingS,false,ae, 
               new BigDecimal(0),new BigDecimal(100),
               "Sharing percent", (((ind*1000+ind1)*1000)+ind2));
             sumPercent = sumPercent.add(sharingBD);
             
             String dilutionRateS = pajVw.getDilutionRate();
             if(Utility.isSet(dilutionRateS)) {              
               BigDecimal dilutionRateBD =  stringToBD (dilutionRateS,false,ae, 
                 new BigDecimal(0),null,
                 "Dilution rate", (((ind*1000+ind1)*1000)+ind2));
             }
             
             String usageRateS = pajVw.getUsageRate();
             BigDecimal usageRateBD =  stringToBD (usageRateS,true,ae, 
               new BigDecimal(0),null,
               "Usage rate", (((ind*1000+ind1)*1000)+ind2));
           }
        /*           
           if(Math.abs(sumPercent.doubleValue())>0.0001 &&
              Math.abs(sumPercent.doubleValue()-100)>0.0001) {
             String errorMess = "Sum sharing percent for "+procName + " is not 100";
             ae.add("errSumPercent"+(ind*1000+ind1),
                new ActionError("error.simpleGenericError",errorMess));
           }
         */
         }
       }

       if(ae.size()>0) {
         return ae;
       }

       estimationEjb.saveRestroomCleaningSchedules(facilityId, csjVwV, userName);
       ae = loadRestroom(request,form);
       if(ae.size()>0) {
         return ae;
       }
       ae = calculateRestroomResults(request,form);
       if(ae.size()>0) {
         return ae;
       }
       //Set process step 
       setProcessStep(facility,RESTROOM_STEP_SHIFT,3);
       pForm.setRestroomProcessStep(3);
       return ae;       
       
    }

    public static ActionErrors saveOtherProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       CleaningScheduleJoinViewVector csjVwV = pForm.getOtherSchedules();
       int ind = -1;
       for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
         ind ++;
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         CleaningScheduleData csD = csjVw.getSchedule();
         String cleaningScheduleCd = csD.getCleaningScheduleCd();
         CleaningSchedStructJoinViewVector cssjVwV =  csjVw.getStructure();
         int ind1 = -1;
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           ind1 ++;
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           CleaningProcData cpD = cssjVw.getProcedure();
           String procName = cpD.getShortDesc();
           BigDecimal sumPercent = new BigDecimal(0);
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           int ind2 = 0;
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ind2++;
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ProdApplData paD = pajVw.getProdAppl();
             ItemData iD = pajVw.getItem();
             String sharingS = pajVw.getSharingPercent();
             if(!Utility.isSet(sharingS)) {
               continue;
             }
             csjVw.setFrequency("1");
             BigDecimal sharingBD =  stringToBD (sharingS,false,ae, 
               new BigDecimal(0),new BigDecimal(100),
               "Sharing percent", (((ind*1000+ind1)*1000)+ind2));
             sumPercent = sumPercent.add(sharingBD);
             
             String usageRateS = pajVw.getUsageRate();
             BigDecimal usageRateBD =  stringToBD (usageRateS,true,ae, 
                                               new BigDecimal(0),null,
                                 "Usage rate", (((ind*1000+ind1)*1000)+ind2));
           }
           /*
           if(Math.abs(sumPercent.doubleValue())>0.0001 &&
              Math.abs(sumPercent.doubleValue()-100)>0.0001) {
             String errorMess = "Sum sharing percent for "+procName + " is not 100";
             ae.add("errSumPercent"+(ind*1000+ind1),
                new ActionError("error.simpleGenericError",errorMess));
           }
           */
         }
       }
       if(ae.size()>0) {
         return ae;
       }

       ArrayList cleaningScheduleTypeCdAL = new ArrayList();
       cleaningScheduleTypeCdAL.
               add(RefCodeNames.CLEANING_SCHEDULE_CD.OTHER);
       estimationEjb.saveCleaningSchedules(facilityId, csjVwV, cleaningScheduleTypeCdAL, 
                OTHER_STEP_SHIFT, userName);

       ae = loadOther(request,form);
       if(ae.size()>0) {
         return ae;
       }
       
       ae = calculateOtherResults(request,form);
       if(ae.size()>0) {
         return ae;
       }
       
       //Set process step 
       setProcessStep(facility,OTHER_STEP_SHIFT,3);
       pForm.setOtherProcessStep(3);
       return ae;       
       
    }

    public static ActionErrors calculateFloorResults(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       //
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityQty = facility.getFacilityQty();
       BigDecimal facilityQtyBD = new BigDecimal(facilityQty);
       int facilityId = facility.getEstimatorFacilityId();
       try {
       EstimatorProdResultViewVector estimatorProdResultVw =
                                estimationEjb.calcFloorYearProducts(facilityId);
         pForm.setFloorProdResults(estimatorProdResultVw);
       } catch (Exception exc) {
         pForm.setFloorProcessStep(2);
         ae = checkUserError(exc);
       }
  
       return ae;       
       
    }
    
    public static ActionErrors calculateRestroomResults(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       //
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityQty = facility.getFacilityQty();
       BigDecimal facilityQtyBD = new BigDecimal(facilityQty);
       int facilityId = facility.getEstimatorFacilityId();
       EstimatorProdResultViewVector estimatorRestroomResultVw = null;
       try {        
          estimatorRestroomResultVw = estimationEjb.calcRestroomYearProducts(facilityId);
       } catch(Exception exc) {
         ae = checkUserError(exc);
         return ae;
       }
       pForm.setRestroomProdResults(estimatorRestroomResultVw);
  
       return ae;       
       
    }
    

    public static ActionErrors modifyProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       int selectedPage = pForm.getSelectedPage();
       if(selectedPage==2) {
          pForm.setPaperPlusProcessStep(2);
       } else if(selectedPage==3) {
          pForm.setFloorProcessStep(2);
       } else if(selectedPage==4) {
          pForm.setRestroomProcessStep(2);
       } else if(selectedPage==7) {
          pForm.setOtherProcessStep(2);
       }
       return ae;
    }

    

    public static ActionErrors calculatePaperPlusResults(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();
       
       EstimatorProdResultViewVector paperPlusProdResults = estimationEjb.calcPaperPlusYearProducts (facilityId);
       
       CleaningScheduleJoinViewVector csjVwV = pForm.getPaperPlusSupplySchedules();
       paperPlusProdResults = orderResults(csjVwV,paperPlusProdResults);
       pForm.setPaperPlusProdResults(paperPlusProdResults); 
       return ae;
    }
    
    public static ActionErrors calculateOtherResults(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();
       
       EstimatorProdResultViewVector otherProdResults = null;
       try {
          otherProdResults =  estimationEjb.calcOtherYearProducts (facilityId);
       }catch (Exception exc) {
         ae = checkUserError(exc);
         return ae;
       }
       
       CleaningScheduleJoinViewVector csjVwV = pForm.getOtherSchedules();
       otherProdResults = orderResults(csjVwV,otherProdResults);
       pForm.setOtherProdResults(otherProdResults); 
       return ae;
    }

    private static int stringToInt(String pValue, 
         boolean pErrorIfEmptyFl, 
         ActionErrors pAe,
         Integer pMin, 
         Integer pMax,
         String pNameValue,
         int pIndex) {
       int result = 0;
       if(!Utility.isSet(pValue)) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Empty "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
            new ActionError("error.simpleGenericError",errorMess));
         }
         return result;
       }
       pValue = pValue.trim();
       try {
         result = Integer.parseInt(pValue);
       } catch (Exception exc) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Invalid value of "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMin!=null) {
         if(result<pMin.intValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be less than "+pMin;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMax!=null) {
         if(result>pMax.intValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be higher than "+pMax;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       return result;
    }
    
    private static BigDecimal stringToBD(String pValue, 
         boolean pErrorIfEmptyFl, 
         ActionErrors pAe,
         BigDecimal pMin, 
         BigDecimal pMax,
         String pNameValue,
         int pIndex) {
       BigDecimal result = new BigDecimal(0);
       if(!Utility.isSet(pValue)) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Empty "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
            new ActionError("error.simpleGenericError",errorMess));
         }
         return result;
       }
       pValue = pValue.trim();
       try {
         double resultDb = Double.parseDouble(pValue);
         result = new BigDecimal(pValue);
       } catch (Exception exc) {
         if(pErrorIfEmptyFl) {
           String errorMess = "Invalid value of "+pNameValue;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMin!=null) {
         if(result.doubleValue()<pMin.doubleValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be less than "+pMin;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       if(pMax!=null) {
         if(result.doubleValue()>pMax.doubleValue()) {
           String errorMess = "Value of "+pNameValue+ " can't be higher than "+pMax;
           pAe.add("err"+pNameValue+pIndex,
              new ActionError("error.simpleGenericError",errorMess));
           return result;
         }
       }
       return result;
    }
    
    public static ActionErrors loadPaperPlus(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       ArrayList scheduleTypes = new ArrayList();
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.HAND_SOAP_SUPPLY);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.OFFICE_LINER_SUPPLY);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.COMMONON_AREA_LINER_SUPPLY);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.CONTAINER_LINER_SUPPLY);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.BATHROOM_LINER_SUPPLY);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.PAPER_TOWEL_SUPPLY);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.SEAT_COVER_SUPPLY);
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.TOILET_TISSUE_SUPPLY);
         
       CleaningScheduleJoinViewVector paperPlusSchedules = 
                estimationEjb.getCleaningSchedules (facilityId, scheduleTypes); 
       pForm.setPaperPlusSupplySchedules(paperPlusSchedules);
       return ae;       
       
    }
    
    public static ActionErrors loadOther(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       ArrayList scheduleTypes = new ArrayList();
       scheduleTypes.add(RefCodeNames.CLEANING_SCHEDULE_CD.OTHER);
         
       CleaningScheduleJoinViewVector otherSchedules = 
                estimationEjb.getCleaningSchedules (facilityId, scheduleTypes); 
       pForm.setOtherSchedules(otherSchedules);
       return ae;       
       
    }

    public static ActionErrors modifyAssumptions(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       int selectedPage = pForm.getSelectedPage();
       if(selectedPage==2) {
         pForm.setPaperPlusProcessStep(1);
       } else if(selectedPage==3) {
         pForm.setFloorProcessStep(1);
       } else if(selectedPage==4) {
         pForm.setRestroomProcessStep(1);
       }
       return ae;
    }

    
    public static ActionErrors loadAllocatedCategories(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       AllocatedCategoryViewVector acVwV = 
                              estimationEjb.getAllocatedCategories(facilityId);         

       pForm.setAllocatedCategories(acVwV);
       pForm.setAllocatedProcessStep(3);
       
       ae = calculateAllocatedAmounts(request,form);
       if(ae.size()>0) {
         return ae;
       }
       return ae;       
       
    }

    public static ActionErrors calculateAllocatedAmounts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();
       BigDecimal estimatedItemsFactorBD = facility.getEstimatedItemsFactor();
       double estimatedItemsFactorDb = (estimatedItemsFactorBD==null)? 0:
                                        estimatedItemsFactorBD.doubleValue();
       int facilityQty = facility.getFacilityQty();
       AllocatedCategoryViewVector acVwV = pForm.getAllocatedCategories();
       double sumProductAmount = 0;
       
       EstimatorProdResultViewVector floorProdResults = 
          pForm.getFloorProdResults();
       EstimatorProdResultViewVector restroomProdResults = 
          pForm.getRestroomProdResults();
       EstimatorProdResultViewVector paperPlusProdResults = 
          pForm.getPaperPlusProdResults();
       EstimatorProdResultViewVector otherProdResults = 
          pForm.getOtherProdResults();

       if(paperPlusProdResults==null || paperPlusProdResults.size()==0) { 
         int paperPlusProcessStep = getProcessStep(facility,PAPER_PLUS_STEP_SHIFT);
         if(paperPlusProcessStep>=3) {
           ae = calculatePaperPlusResults(request, form);
           if(ae.size()>0) {
             return ae;
           }
           paperPlusProdResults = pForm.getPaperPlusProdResults();
         }
       }

       
       if(floorProdResults==null || floorProdResults.size()==0) {
         int floorProcessStep = getProcessStep(facility,FLOOR_STEP_SHIFT);
         if(floorProcessStep>=3) {
           ae = calculateFloorResults(request, form);
           if(ae.size()>0) {
             return ae;
           }
           floorProdResults = pForm.getFloorProdResults();
         }
       }
       if(restroomProdResults==null || restroomProdResults.size()==0) {
         int restroomProcessStep = getProcessStep(facility,RESTROOM_STEP_SHIFT);
         if(restroomProcessStep>=3) {
           ae = calculateRestroomResults(request, form);
           if(ae.size()>0) {
             return ae;
           }
           restroomProdResults =  pForm.getRestroomProdResults();
         }
       }
       if(otherProdResults==null || otherProdResults.size()==0) {
         int otherProcessStep = getProcessStep(facility,OTHER_STEP_SHIFT);
         if(otherProcessStep>=3) {
           ae = calculateOtherResults(request, form);
           if(ae.size()>0) {
             return ae;
           }
           otherProdResults =  pForm.getOtherProdResults();
         }
       }
       
       if(floorProdResults!=null &&
          restroomProdResults!=null &&
          paperPlusProdResults!=null &&
          otherProdResults!=null     
       ) {
         for(Iterator iter=floorProdResults.iterator(); iter.hasNext();) {
           EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
           BigDecimal price = eprVw.getYearPrice();
           if(price!=null) sumProductAmount += price.doubleValue();
         }
         for(Iterator iter=restroomProdResults.iterator(); iter.hasNext();) {
           EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
           BigDecimal price = eprVw.getYearPrice();
           if(price!=null) sumProductAmount += price.doubleValue();
         }
         for(Iterator iter=paperPlusProdResults.iterator(); iter.hasNext();) {
           EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
           BigDecimal price = eprVw.getYearPrice();
           if(price!=null) sumProductAmount += price.doubleValue();
         }
         for(Iterator iter=otherProdResults.iterator(); iter.hasNext();) {
           EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
           BigDecimal price = eprVw.getYearPrice();
           if(price!=null) sumProductAmount += price.doubleValue();
         }

         BigDecimal sumProductAmountBD = 
           new BigDecimal(sumProductAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
         pForm.setSumProductAmount(sumProductAmountBD);
         double amountToAllocateDb = 0;
         if(estimatedItemsFactorDb!=0) {
           amountToAllocateDb =  sumProductAmount*(100/estimatedItemsFactorDb -1);
         }
         pForm.setEstimatedItemsAmount(new BigDecimal(amountToAllocateDb).
                                            setScale(2,BigDecimal.ROUND_HALF_UP));
         for(Iterator iter=acVwV.iterator(); iter.hasNext();) {
           AllocatedCategoryView acVw = (AllocatedCategoryView) iter.next();
           AllocatedCategoryData acD = acVw.getAllocatedCategory();
           BigDecimal percent = acD.getPercent();
           double amountDb = (percent==null)? 0: 
                          percent.doubleValue() * amountToAllocateDb /100;
           BigDecimal amountBD = 
                new BigDecimal(amountDb).setScale(2,BigDecimal.ROUND_HALF_UP);
           acVw.setAmount(amountBD);
           BigDecimal allFacilityAmountBD = 
                new BigDecimal(amountDb*facilityQty).setScale(2,BigDecimal.ROUND_HALF_UP);
           acVw.setAllLocationsAmount(allFacilityAmountBD);
           
         }
         
       } else {
         pForm.setSumProductAmount(null);
         for(Iterator iter=acVwV.iterator(); iter.hasNext();) {
           AllocatedCategoryView acVw = (AllocatedCategoryView) iter.next();
           acVw.setAmount(null);
         }
       }
       
       pForm.setAllocatedCategories(acVwV);
       
       return ae;
    }


    //Order results
    private static EstimatorProdResultViewVector 
          orderResults(CleaningScheduleJoinViewVector pSchedules, 
                       EstimatorProdResultViewVector pResults) 
    throws Exception 
    {
       IdVector itemIdV = new IdVector();
       for(Iterator iter = pSchedules.iterator(); iter.hasNext();) {
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         CleaningSchedStructJoinViewVector cssjVwV =  csjVw.getStructure();
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ProdApplData paD = pajVw.getProdAppl();
             int itemId = paD.getItemId();
             if(paD.getEstimatorFacilityId()<=0) {
               continue;
             }
             itemIdV.add(new Integer(itemId));
           }
         }
       }
      Object[] resultA = pResults.toArray();
      EstimatorProdResultViewVector orderedResults = 
                                         new EstimatorProdResultViewVector();
      for(Iterator iter=itemIdV.iterator(); iter.hasNext();) {
        Integer itemIdI = (Integer) iter.next();
        int itemId = itemIdI.intValue();
        for(int ii=0; ii<resultA.length; ii++) {
          EstimatorProdResultView eprVw = (EstimatorProdResultView) resultA[ii];
          if(eprVw!=null && itemId==eprVw.getItemId()) {
            resultA[ii] = null;
            orderedResults.add(eprVw);
            break;
          }
        }
      }
      for(int ii=0; ii<resultA.length; ii++) {
        EstimatorProdResultView eprVw = (EstimatorProdResultView) resultA[ii];
        if(eprVw!=null) {
          orderedResults.add(eprVw);
        }
      }
      return orderedResults;
    }
    
    public static ActionErrors saveAllocationPercent(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;

       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
      
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int facilityId = facility.getEstimatorFacilityId();

       String estimatedItemsFactorS = pForm.getEstimatedItemsFactor();

       BigDecimal estimatedItemsFactor = stringToBD(estimatedItemsFactorS,false,ae, 
                   new BigDecimal(0), new BigDecimal(100), "Spend Factor",0);
       
       AllocatedCategoryViewVector acVwV = pForm.getAllocatedCategories();
       int ind = -1;
       double sumPercent = 0;
       for(Iterator iter = acVwV.iterator(); iter.hasNext(); ) {
         ind++;
         AllocatedCategoryView acVw = (AllocatedCategoryView) iter.next();
         String percentS = acVw.getAllocatedPercent();
         if(Utility.isSet(percentS)) {
           BigDecimal percent = stringToBD(percentS,false,ae, 
                   new BigDecimal(0), new BigDecimal(100), "Allocated Percent",ind);
           sumPercent += percent.doubleValue(); 
            
         }
       }
       if(ae.size()>0) {
         return ae;
       }
       
       if(Math.abs(sumPercent-100)>0.001) {
         String errorMess = "Total allocated percent is not 100";
         ae.add("error",
         new ActionError("error.simpleGenericError",errorMess));
         return ae;
       }
       facility.setEstimatedItemsFactor(estimatedItemsFactor);
       estimationEjb.saveEstimatorProfile(facilityJoin, userName);
       estimationEjb.saveAllocatedCategories(facilityId, acVwV, userName);
       
       ae = loadAllocatedCategories(request, form);
       if(ae.size()>0) {
         return ae;
       }
       setProcessStep(facility,ALLOCATED_STEP_SHIFT,3);
       pForm.setAllocatedProcessStep(3);
       return ae;       
       
    }
    
    
    public static ActionErrors modifyAllocationPercent(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       pForm.setAllocatedProcessStep(2);
       return ae;
    }
    
    private static String bdToString(BigDecimal pVal) {
      String result = "";
      if(pVal==null) return result;
      result = pVal.toString();
      return result;
    }
    
  private static void setProcessStep(EstimatorFacilityData pFacility, 
                              int pPosition, int pStepNum)
  throws Exception 
  {
     int processStep = pFacility.getProcessStep();
     int[] stepA = new int[pPosition];
     for(int ii=0; ii<pPosition; ii++) {
       stepA[ii] = processStep%10;
       processStep /= 10;
     }     
     processStep /=10;
     processStep = processStep*10 + pStepNum;
     for(int ii=pPosition-1; ii>=0; ii--) {
       processStep = processStep*10 + stepA[ii];
     }
     
     pFacility.setProcessStep(processStep);
  }

  private static int getProcessStep(EstimatorFacilityData pFacility, 
                              int pPosition)
  throws Exception 
  {
     int processStep = pFacility.getProcessStep();
     for(int ii=0; ii<pPosition; ii++) {
       processStep /= 10;
     }     
     int stepNum = processStep %10;
     return stepNum;
  }
  
  private static boolean isAddedRemoved(BigDecimal pVal1, BigDecimal pVal2, int pScale) 
  {
    BigDecimal zeroBD = new BigDecimal(0);
    if(pVal1==null) pVal1 = zeroBD;
    pVal1 = pVal1.setScale(pScale,BigDecimal.ROUND_HALF_UP);
    if(pVal2==null) pVal2 = zeroBD;
    pVal2 = pVal2.setScale(pScale,BigDecimal.ROUND_HALF_UP);
    
    if(pVal1.compareTo(zeroBD)==0 && pVal2.compareTo(zeroBD)!=0 ||
       pVal1.compareTo(zeroBD)!=0 && pVal2.compareTo(zeroBD)==0 ) {
      return true;
    }
    return false;
       
  }
    public static ActionErrors loadTemplateModel(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       String templateIdS = request.getParameter("templateId");
       int templateId = 0;
       try {
         templateId = Integer.parseInt(templateIdS);
       } catch (Exception exc) {
         String errorMess = "Wrong template id: "+templateIdS;
         ae.add("error",
              new ActionError("error.simpleGenericError",errorMess));
          return ae;
       }
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       
       EstimatorFacilityJoinView efjVw = estimationEjb.getEstimatiorProfile(templateId);
       EstimatorFacilityData efD = efjVw.getEstimatorFacility();
       efD.setEstimatorFacilityId(0);
       pForm.setDefaultFacility(efD);
       efD.setName("");
       efD.setFacilityGroup("");
       efD.setTemplateFacilityId(templateId);
       efD.setTemplateFl("F");
       FacilityFloorDataVector ffDV = efjVw.getFacilityFloors();
       for(Iterator iter=ffDV.iterator(); iter.hasNext();) {
         FacilityFloorData ffD = (FacilityFloorData) iter.next();
         ffD.setEstimatorFacilityId(0);
         ffD.setFacilityFloorId(0);
       }
       setUiFloorPercentage(pForm,ffDV);
       pForm.setFacilityJoin(efjVw);

       pForm.setSelectedPage(8);

       ae = scatterFacilityFields(form,efD);
       if(ae.size()>0) {
         return ae;
       }
       
       return ae;
    }

    
    public static ActionErrors prepareProducts(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       OrderGuide orderGuideEjb = factory.getOrderGuideAPI();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int catalogId = facility.getCatalogId();
       int orderGuideId = facility.getOrderGuideId();
       
       OrderGuideItemDescDataVector ogidDV = 
              orderGuideEjb.getEstimatorItems(catalogId, orderGuideId);
       OrderGuideItemDescData[] estimatorProducts = 
                                     new OrderGuideItemDescData[ogidDV.size()];
       int ind = 0;
       int ogCount = 0;
       for(Iterator iter = ogidDV.iterator(); iter.hasNext();) {
         OrderGuideItemDescData ogidD = (OrderGuideItemDescData) iter.next();
         estimatorProducts[ind++] = ogidD;
         if(ogidD.getOrderGuideId()!=0) ogCount++;
       }
       
       for(int ii=0; ii<estimatorProducts.length-1; ii++) {
         boolean exitFl = true;
         for(int jj=0; jj<estimatorProducts.length-ii-1; jj++) {
           OrderGuideItemDescData ogidD1 = estimatorProducts[jj];
           OrderGuideItemDescData ogidD2 = estimatorProducts[jj+1];
           String category1 = ogidD1.getCategoryDesc();
           if(category1==null) {
             category1="";
           }
           String category2 = ogidD2.getCategoryDesc();
           if(category2==null) {
             category2="";
           }
           int compFl = category1.compareToIgnoreCase(category2);
           if(compFl>0) {
             estimatorProducts[jj] = ogidD2;
             estimatorProducts[jj+1] = ogidD1;
             exitFl = false;
           } else if(compFl==0) {
             String skuNumS1 = ogidD1.getCwSKU();
             String skuNumS2 = ogidD2.getCwSKU();
             int skuNum1 = Integer.parseInt(skuNumS1);
             int skuNum2 = Integer.parseInt(skuNumS2);
             if(skuNum1>skuNum2) {
               estimatorProducts[jj] = ogidD2;
               estimatorProducts[jj+1] = ogidD1;
               exitFl = false;
             }
           }
         }
         if(exitFl) {
           break;
         }
       }
       pForm.setEstimatorProducts(estimatorProducts);
       int[] productFilter = new int[ogCount];
       ind = 0;
       for(int ii=0; ii<estimatorProducts.length; ii++) {
         OrderGuideItemDescData ogidD = estimatorProducts[ii];
         if(ogidD.getOrderGuideId()>0) {
           productFilter[ind++] = ogidD.getItemId();
         }
       }
       pForm.setProductFilter(productFilter);
       CleaningScheduleJoinViewVector paperPlusSchedules = 
                               pForm.getPaperPlusSupplySchedules();
       CleaningScheduleJoinViewVector floorSchedules = 
                            pForm.getFloorCleaningSchedules();
       CleaningScheduleJoinViewVector restroomSchedules = 
                            pForm.getRestroomCleaningSchedules();
       CleaningScheduleJoinViewVector otherSchedules = 
                            pForm.getOtherSchedules();
       setUiFilter(productFilter, paperPlusSchedules);
       setUiFilter(productFilter, floorSchedules);
       setUiFilter(productFilter, restroomSchedules);
       setUiFilter(productFilter, otherSchedules);
       pForm.setPaperPlusSupplySchedules(paperPlusSchedules);
       pForm.setFloorCleaningSchedules(floorSchedules);
       pForm.setRestroomCleaningSchedules(restroomSchedules);
       pForm.setOtherSchedules(otherSchedules);
       return ae;
    }
       
    public static ActionErrors prepareProductFilter(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       
       int ogCount=0;
       OrderGuideItemDescData[] estimatorProducts = 
                                 pForm.getEstimatorProducts();
       for(int ii=0; ii<estimatorProducts.length; ii++) {
         OrderGuideItemDescData ogidD = estimatorProducts[ii];
         if(ogidD.getOrderGuideId()!=0) ogCount++;
       }
       
       int[] productFilter = new int[ogCount];
       int ind = 0;
       for(int ii=0; ii<estimatorProducts.length; ii++) {
         OrderGuideItemDescData ogidD = estimatorProducts[ii];
         if(ogidD.getOrderGuideId()>0) {
           productFilter[ind++] = ogidD.getItemId();
         }
       }
       pForm.setProductFilter(productFilter);
       return ae;
    }
       

    public static ActionErrors saveProductSelection(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       OrderGuide orderGuideEjb = factory.getOrderGuideAPI();
       
       EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
       EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
       int catalogId = facility.getCatalogId();
       int orderGuideId = facility.getOrderGuideId();
       if(orderGuideId<=0) {
         OrderGuideData ogD = OrderGuideData.createValue();
         ogD.setCatalogId(catalogId);
         ogD.setShortDesc("Spending Estimator: "+facility.getEstimatorFacilityId());
         ogD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ESTIMATOR_ORDER_GUIDE);
         ogD.setAddBy(userName);
         ogD.setModBy(userName);
         ogD = orderGuideEjb.addOrderGuide(ogD);
         orderGuideId = ogD.getOrderGuideId();
         facility.setOrderGuideId(orderGuideId);
       }
     
       OrderGuideItemDescData[] estimatorProducts = pForm.getEstimatorProducts();
       int[] productFilter = pForm.getProductFilter();
       OrderGuideItemDescDataVector ogidDV = new OrderGuideItemDescDataVector();
       for(int ii=0; ii<estimatorProducts.length; ii++) {
         OrderGuideItemDescData ogidD = estimatorProducts[ii];
         boolean foundFl = false;
         int itemId = ogidD.getItemId();
         for(int jj=0; jj<productFilter.length; jj++) {
           if(itemId==productFilter[jj]) {
             foundFl = true;
             ogidDV.add(ogidD);
             break;
           }
         }
         if(!foundFl) {
           int ogsId = ogidD.getOrderGuideStructureId();
           if(ogsId>0) {
             orderGuideEjb.removeItem(ogsId);
             ogidD.setOrderGuideId(0);
             ogidD.setOrderGuideStructureId(0);
           }
         }
       }
       
       orderGuideEjb.addItems(orderGuideId,ogidDV,userName);
       pForm.setPaperPlusProcessStep(2);
       pForm.setFloorProcessStep(2);
       pForm.setRestroomProcessStep(2);
       pForm.setAllocatedProcessStep(2);
       pForm.setOtherProcessStep(2);

       setProcessStep(facility,PAPER_PLUS_STEP_SHIFT,2);
       setProcessStep(facility,FLOOR_STEP_SHIFT,2);
       setProcessStep(facility,RESTROOM_STEP_SHIFT,2);
       setProcessStep(facility,ALLOCATED_STEP_SHIFT,2);
       setProcessStep(facility,OTHER_STEP_SHIFT,2);

       facilityJoin = estimationEjb.saveEstimatorProfile(facilityJoin,userName);
       pForm.setFacilityJoin(facilityJoin);

       pForm.setPaperPlusProdResults(null);
       pForm.setFloorProdResults(null);
       pForm.setRestroomProdResults(null);
       pForm.setOtherProdResults(null);
       
       ae = loadModel(request,form,facility.getEstimatorFacilityId());
       if(ae.size()>0) {
         return ae;
       }
       return ae;
    }

    private static void setUiFilter(int[] pProductFilter, 
                  CleaningScheduleJoinViewVector pCleaningSchedules) {
       BigDecimal bd0 = new BigDecimal(0);
       BigDecimal bd100 = new BigDecimal(100);
       for(Iterator iter = pCleaningSchedules.iterator(); iter.hasNext();) {
         CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
         CleaningSchedStructJoinViewVector cssjVwV =  csjVw.getStructure();
         for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
           CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
           CleaningProcData cpD = cssjVw.getProcedure();
           String procName = cpD.getShortDesc();
           ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
           for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
             ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
             ProdApplData paD = pajVw.getProdAppl();
             paD.setUiFilter(bd0);
             String estimatorProductCd = paD.getEstimatorProductCd();
             ItemData iD = pajVw.getItem();
             int itemId = iD.getItemId();
             for(int ii=0; ii<pProductFilter.length; ii++) {
               if(pProductFilter[ii]==itemId) {
                 paD.setUiFilter(bd100);
                 break;
               }
             }
           }
         }
       }
    }

    private static void setFacilityGroups(SpendingEstimatorForm pForm) {
      EstimatorFacilityDataVector estimatorFacilityDV = pForm.getFacilities();
      ArrayList facilityGroupAL = new ArrayList();
      int ind = -1;
      for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
        ind++;
        EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
        String fg = efD.getFacilityGroup();
        if(Utility.isSet(fg)) {
          facilityGroupAL.add(fg);
        }
      }
      
      EstimatorFacilityJoinView facilityJoin = pForm.getFacilityJoin();
      if(facilityJoin!=null) {
        EstimatorFacilityData efD = facilityJoin.getEstimatorFacility();
        String fg = efD.getFacilityGroup();
        if(Utility.isSet(fg)) {
          facilityGroupAL.add(fg);
        }
      }
      
      if(facilityGroupAL.size()>1) {
        Object[] facilityGroupA = facilityGroupAL.toArray();
        for(int ii=0; ii<facilityGroupA.length-1; ii++) {
          boolean exitFl = true;
          for(int jj=0; jj<facilityGroupA.length-ii-1; jj++) {
            String fg1 = (String) facilityGroupA[jj];
            String fg2 = (String) facilityGroupA[jj+1];            
            if(fg1.compareTo(fg2)>0) {
              facilityGroupA[jj] = fg2;
              facilityGroupA[jj+1] = fg1;
              exitFl = false;
            }
          }
          if(exitFl) {
            break;
          }
        }
        facilityGroupAL = new ArrayList();
        String prevFg = "";
        for(int ii=0; ii<facilityGroupA.length; ii++) {
          String fg = (String) facilityGroupA[ii];
          if(!fg.equals(prevFg)) {
            prevFg = fg;
            facilityGroupAL.add(fg);
          }
        }
        
      }
      pForm.setFacilityGroups(facilityGroupAL);      
    }

    //////////////// Reports ///////////////////////////////    
    public static ActionErrors initReports(HttpServletRequest request,
            ActionForm form)
             throws Exception {
       ActionErrors ae = new ActionErrors();
       SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) 
                              session.getAttribute(Constants.APP_USER);
       String userName = appUser.getUser().getUserName();
       int userId = appUser.getUser().getUserId();
       APIAccess factory = new APIAccess();
       Estimation estimationEjb = factory.getEstimationAPI();
       Report reportEjb = factory.getReportAPI();
       pForm.setReportId(0);       
       GenericReportViewVector reports = 
                   reportEjb.getReportList(userId, appUser.getUser().getUserTypeCd());
       reports = sortReportsByName(reports);
       for(Iterator iter=reports.iterator(); iter.hasNext();) {
         GenericReportView grVw = (GenericReportView) iter.next();
         String category = Utility.strNN(grVw.getReportCategory());
         if(!category.equalsIgnoreCase("Estimator")) {
            iter.remove();
         }
       }
       pForm.setReports(reports);
       return ae;              
    }
    private static GenericReportViewVector 
                     sortReportsByName(GenericReportViewVector reports) {
      if(reports.size()<=1) return  reports;
      Object[] reportA = reports.toArray();
      
      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
          GenericReportView grVw1 = (GenericReportView) reportA[jj];
          GenericReportView grVw2 = (GenericReportView) reportA[jj+1];
          String name1 = Utility.strNN(grVw1.getReportName());
          String name2 = Utility.strNN(grVw2.getReportName());
          int comp = name1.compareTo(name2);
          if(comp>0) {
            reportA[jj] = grVw2;
            reportA[jj+1] = grVw1;
            exitFl = false;
            continue;
          }
        }
        if(exitFl) break;
      }
      reports = new GenericReportViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        reports.add((GenericReportView) reportA[ii]);
      }
      return reports;
    }


    //---------------------------------------------------------------------------------------
    public static ActionErrors reportRequest(HttpServletRequest request, ActionForm form)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      String reportIdS = request.getParameter("reportId");
      int reportId = Integer.parseInt(reportIdS);
      pForm.setReportId(reportId);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportControlViewVector grcVwV = reportEjb.getGenericReportControls(reportId);
      pForm.setGenericControls(grcVwV);
      GenericReportData reportD = reportEjb.getGenericReport(reportId);
      pForm.setReport(reportD);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors runReport(HttpServletRequest request, 
                                         HttpServletResponse res, 
                                         ActionForm form)
    throws Exception
    {
        ActionErrors errors = new ActionErrors();
        SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
        try {
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            String userName = appUser.getUser().getUserName();
            int userId = appUser.getUser().getUserId();
            APIAccess factory = new APIAccess();
            Report reportEjb = factory.getReportAPI();
            GenericReportData reportD = pForm.getReport();
            Map params = new HashMap();
            String rType = reportD.getCategory();
            String rName = reportD.getName();
            //check if they are authorized,  this report should not be displayed to user,
            //so this really shouldn't happen
            if(!(appUser.isAuthorizedForReport(rName))){
                errors.add(rName, new ActionError("error.systemError", "NOT AUTHORIZED"));
                return errors;
            }
            String pstr = "Report parameters--";
            GenericReportControlViewVector grcVV = pForm.getGenericControls();
            for(int ii=0; ii<grcVV.size(); ii++) {
                GenericReportControlView grc =
                    (GenericReportControlView) grcVV.get(ii);
                String name = grc.getName();
                String label = grc.getLabel();
                if(label==null && label.trim().length()==0) label = name;
                String mf = grc.getMandatoryFl();
                if(mf!=null) mf = mf.toUpperCase();
                boolean mandatoryFl = true;
                if("N".equals(mf) || "NO".equals(mf) ||
                   "0".equals(mf) ||"F".equals(mf) ||
                   "FALSE".equals(mf)) {
                    mandatoryFl = false;
                }
                if(name!=null && name.trim().toUpperCase().endsWith("_OPT")) {
                    mandatoryFl = false;
                }
                
                String value = grc.getValue();
                if("CUSTOMER".equalsIgnoreCase(name)) {
                    mandatoryFl = false;
                    if(value==null ||value.trim().length()==0) {
                      value = ""+appUser.getUser().getUserId();
                    }
                }
                
                if(mandatoryFl && (value==null || value.trim().length()==0)) {
                    errors.add("beginDate", new ActionError
                               ("variable.empty.error",name));
                }
                String type = grc.getType();
                if(type!=null && value!=null && value.trim().length()>0) {
                    if("DATE".equalsIgnoreCase(type) || "BEG_DATE".equalsIgnoreCase(name) || "END_DATE".equalsIgnoreCase(name)) {
                        if(!isDate(value)) {
                            errors.add(name, new ActionError("error.badDateFormat", label));
                        }
                    } else if("INT".equalsIgnoreCase(type) ||
                    "ACCOUNT".equalsIgnoreCase(name) || "ACCOUNT_OPT" .equalsIgnoreCase(name) || 
                    "CONTRACT".equalsIgnoreCase(name) || "DISTRIBUTOR".equalsIgnoreCase(name) ||
                    "MANUFACTURER".equalsIgnoreCase(name) ||"ITEM".equalsIgnoreCase(name) ||
                    "CATALOG".equalsIgnoreCase(name) ||"ITEM_OPT".equalsIgnoreCase(name) ||
                    "STORE".equalsIgnoreCase(name) ||"STORE_OPT".equalsIgnoreCase(name)||
                    "CUSTOMER".equalsIgnoreCase(name))
                    {
                        if(!isInt(value)) {
                            errors.add(name, new ActionError("variable.integer.format.error", label));
                        }
                    } else if("NUMBER".equalsIgnoreCase(type)) {
                        if(!isNumber(value)) {
                            errors.add(name, new ActionError("error.invalidNumberAmount", label));
                        }
                    }
                }

                pstr += "\n" + grc.getSrcString() + "=" + value;
                if(errors.size()==0) {
                    params.put(grc.getSrcString(), value);
                }
            }
            
            String [] runForModels = pForm.getRunForModels();
            if ( null != runForModels && runForModels.length > 0 ) {
                String models = "";
                for ( int ii = 0; ii < runForModels.length; ii++ ) {
                    if ( models.length() > 0 ) models+= ", ";
                    models += runForModels[ii];
                }
                params.put("FACILITY_MULTI_OPT", models);
            }
            
            if(errors.size()>0) {
                return errors;
            }

            ReportResultData reportResultD = null;
            try {
                
                reportResultD =  reportEjb.processAnalyticReport
                    (pForm.getReportId(),params,
                     userId, userName, true);
                
            }catch (Exception exc) {
              String errorMess = exc.toString();
              int messIndBeg = errorMess.indexOf("^clw^");
              int messIndEnd = 0;
              if(messIndBeg>=0) {
                messIndEnd = errorMess.indexOf("^clw^",messIndBeg+1);
              }
              if(messIndBeg>=0 && messIndEnd>messIndBeg) {
                String userMess = errorMess.substring(messIndBeg+5,messIndEnd);
                errors.add("error",
                         new ActionError("error.simpleGenericError",userMess));
                return errors;
              } else {
                ArrayList errorAL = new ArrayList();
                for(int ii=0; ii<100 && errorMess.length()>4000; ii++) {
                  errorAL.add(errorMess.substring(0,4000));
                  errorMess = errorMess.substring(4000);
                } 
                if(errorMess.length()>0)  errorAL.add(errorMess);
                ReportResultData errD = reportEjb.
                   saveAnalyticReportError(pForm.getReportId(),0, params, userId, userName, errorAL);
                reportEjb.sendReportErrorNotification(errD,  errorAL);
                throw exc;
              }
            }
            
            int reportResultId = reportResultD.getReportResultId();
            downloadPreparedReport(request, res, form,""+reportResultId, reportResultD);
            
        }catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
        }
        return errors;
    }
    

    //---------------------------------------------------------------------------------------
    public static ActionErrors downloadPreparedReport(HttpServletRequest request, 
                                         HttpServletResponse res, 
                                         ActionForm form,
                                         String pReportResultIdS,
                                         ReportResultData reportResultD)
    throws Exception
    {
        ActionErrors errors = new ActionErrors();
        SpendingEstimatorForm pForm = (SpendingEstimatorForm) form;
        try {
          CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
          String userName = appUser.getUser().getUserName();
          int userId = appUser.getUser().getUserId();
          APIAccess factory = new APIAccess();
          Report reportEjb = factory.getReportAPI();
          String reportResultIdS = pReportResultIdS;
          int reportResultId = 0;
          try{
             reportResultId = Integer.parseInt(reportResultIdS);
          } catch (Exception exc) {
            String errorMess = "Wrong report result id: "+reportResultIdS;
            errors.add("error", new ActionError("system error",errorMess));
            return errors;
          }

          //Create report result object          
          GenericReportResultViewVector results =
                                     reportEjb.readArchiveReport(reportResultId);
          
          String fileName = "report";
          if(reportResultD!=null) {
            Date repDate = reportResultD.getAddDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String repDateS = sdf.format(repDate);
            fileName = reportResultD.getReportName()+repDateS;
          }
          fileName+=".xls";
          fileName = fileName.replaceAll(" ", "-");
          fileName = fileName.replaceAll("/", "-"); //problem with IE6
          res.setContentType("application/x-excel");
          String browser = (String)  request.getHeader("User-Agent");
          boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
          if (!isMSIE6){
	          res.setHeader("extension", "xls");
          }
          if(isMSIE6){
        	  res.setHeader("Pragma", "public");
        	  res.setHeader("Expires", "0");
        	  res.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        	  res.setHeader("Cache-Control", "public"); 
      	
          }
          res.setHeader("Content-disposition", "attachment; filename="+fileName);
          ReportWritter.writeExcelReportMulti(results, res.getOutputStream()); 
          res.flushBuffer();
          try {
            reportEjb.markReportAsRead(reportResultId,userId);
          }catch(Exception exc) { //Dump the exception (doesn't affect download) 
            exc.printStackTrace();
          }
 
        }catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
            //errors.add("system error", new ActionError("error.systemError", exc.getMessage()));
        }
        return errors;
    }

    private static Object stringToObject (String pStr) {
      Object obj = null;
      char[] charArr = pStr.toCharArray();
      byte[] byteArr = new byte[charArr.length];
      for(int ii=0; ii<byteArr.length; ii++) {
        byteArr[ii] = (byte) charArr[ii];
      }
      java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(byteArr);
      try {
        java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
        obj =  is.readObject();    
        is.close();
        iStream.close();
      } catch(Exception exc) {
        exc.printStackTrace();
      }
      return obj;
    }
    
    public static boolean isDate(String pDate) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            df.parse(pDate);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    public static boolean isInt(String pInt) {
        try {
            Integer.parseInt(pInt);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    public static boolean isNumber(String pDouble) {
        try {
            Double.parseDouble(pDouble);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    private static ActionErrors checkUserError(Exception exc) 
    throws Exception
    {
      ActionErrors ae = new ActionErrors();
      String errorMess = exc.getMessage();
      int ind = errorMess.indexOf("^clw^");
      if(ind>=0) {
        errorMess = errorMess.substring(ind+5);
        ind = errorMess.indexOf("^clw^");
        if(ind>=0) {
          errorMess = errorMess.substring(0,ind);
        }
        ae.add("error",
        new ActionError("error.simpleGenericError",errorMess));
        return ae;
      } else {
        throw exc;
      }
     
    }
    
 
    
    //                String errorMess = "Wrong expression value: "+compS;
//                ae.add("error",
//                new ActionError("error.simpleGenericError",errorMess));
//                return ae;


}



