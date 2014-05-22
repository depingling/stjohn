<!--  -->
<% { %>

<% 
   CleaningScheduleJoinViewVector csjVwV = theForm.getFloorCleaningSchedules();
   if(csjVwV!=null && csjVwV.size()>0) {
%>
<tr><td><b class="tableheader">Select Floor Cleaning Products</b></td></tr>
<tr>
<td>
<table border='2'>
<%
/////////////////////////////////
   FacilityFloorDataVector facilityFloorDV = theForm.getFacilityJoin().getFacilityFloors();
      HashMap floorTypeHM = new HashMap();
      for(Iterator iter=facilityFloorDV.iterator(); iter.hasNext();) {
        FacilityFloorData ffD = (FacilityFloorData) iter.next();
        String floorTypeCd = ffD.getFloorTypeCd();
        BigDecimal clPr = ffD.getCleanablePercent();    
        if(clPr==null || clPr.doubleValue()<0.0001) continue;
        boolean floorFlHt = false;
        boolean floorFlMt = false;
        boolean floorFlLt = false;
          BigDecimal htPr = ffD.getCleanablePercentHt();
          if(htPr!=null && htPr.doubleValue() > 0.0001) floorFlHt = true;                   
          BigDecimal mtPr = ffD.getCleanablePercentMt();
          if(mtPr!=null && mtPr.doubleValue() > 0.0001) floorFlMt = true;                            
          BigDecimal ltPr = ffD.getCleanablePercentLt();
          if(ltPr!=null && ltPr.doubleValue() > 0.0001) floorFlLt = true;                            

          if(floorFlHt) {
          if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING,"Carpet Floor. High Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd) ) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING,"Ceramic Floor. High Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING,"Concrete Floor. High Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING,"Terrazzo Floor. High Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING,"VCT Tile Floor. High Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING,"Wood Floor. High Traffic");
          }
        }
        if(floorFlMt) {
          if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING,"Carpet Floor. Medium Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING,"Ceramic Floor. Medium Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING,"Concrete Floor. Medium Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING,"Terrazzo Floor. Medium Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING,"VCT Tile Floor. Medium Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING,"Wood Floor. Medium Traffic");
          }
        }
        if(floorFlLt) {
          if(RefCodeNames.FLOOR_TYPE_CD.CARPET.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING,"Carpet Floor. Low Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING,"Ceramic Floor. Low Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.CONCRETE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING,"Concrete Floor. Low Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.TERRAZZO.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING,"Terrazzo Floor. Low Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.VCT_TILE.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING,"VCT Tile Floor. Low Traffic");
          } else if (RefCodeNames.FLOOR_TYPE_CD.WOOD.equals(floorTypeCd)) {
            floorTypeHM.put(RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING,"Wood Floor. Low Traffic");
          }
        }
      }
/////////////////////////////////


   int ind = -1;
   String cleaningScheduleCdPrev = "";
   String cleaningTitle = "";
   String profileFloorMachine = theForm.getFacilityJoin().getEstimatorFacility().getFloorMachine();
   
   for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
     ind ++;
     CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
     BigDecimal frequencyBD = csjVw.getSchedule().getFrequency();
     if(frequencyBD==null || frequencyBD.abs().doubleValue()<0.00001) {
        continue;
     }
     String floorMachine = csjVw.getFloorMachine();
     if(floorMachine!=null && floorMachine.trim().length()>0 && !floorMachine.equals(profileFloorMachine)) {
        continue;
     }
     
     String procFrequencyInp = "floorCleaningProcFrequency["+ind+"]";     
     String procTimePeriodCdInp = "floorCleaningProcTimePeriodCd["+ind+"]";     
     CleaningScheduleData csD = csjVw.getSchedule();
     
     String cleaningScheduleCd = csD.getCleaningScheduleCd();
     cleaningTitle = (String) floorTypeHM.get(cleaningScheduleCd);
     if(cleaningTitle==null) continue;
    
     boolean newCleaningTitleFl = false;
     if(!cleaningScheduleCdPrev.equals(cleaningScheduleCd)) {
       newCleaningTitleFl = true;
       cleaningScheduleCdPrev = cleaningScheduleCd;
     }
     BigDecimal procFrequency = csD.getFrequency();
     String procTimePeriodCd = csD.getTimePeriodCd();
     CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
     if(newCleaningTitleFl) {
     %>
<tr>
<td colspan ='8' align='center' class='tableheader' bgcolor='#cccccc'>
 <b><%=cleaningTitle%></b>
</tr>
<tr bgcolor='#cccccc'>
<td align='center'><b>Procedure</b></td>
<td align='center'><b>Sku #</b></td>
<td align='center'><b>Product</b></td>
<td align='center'><b>Size</b></td>
<td align='center'><b>Pack</b></td>
<td align='center'><b>Dilution</b></td>
<td align='center'><b>Usage<br>Rate</b></td>
<td align='center'><b>Sharing Percent</b></td>
</tr>
     <% } %>

<tr>
<td colspan ='8'  bgcolor='#eeeeee'><b><%=csD.getName()%></b>&nbsp;&nbsp;
  Perform:
  <bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=procFrequencyInp%>'/> 
  time(s) per
  <bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=procTimePeriodCdInp%>' />
</td>
</tr>
  <%
   int ind1 = -1;
   for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
     ind1 ++;
     CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
     CleaningSchedStructData cssD = cssjVw.getScheduleStep();
     CleaningProcData cpD = cssjVw.getProcedure();
     ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
     int prodQty = 0;
     for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
       ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
       ProdApplData paD = pajVw.getProdAppl();
       if(paD.getEstimatorFacilityId()<=0) {
         continue;
       }
       prodQty++;
     }
     if(prodQty==0) {
       continue;
     }

     int ind2 = -1;
     for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
       ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
       ItemData iD = pajVw.getItem();
       ProdApplData paD = pajVw.getProdAppl();
       if(paD.getEstimatorFacilityId()<=0) {
         continue;
       }
       ind2++;
       ProductUomPackData pupD = pajVw.getProductUomPack();
       String dilutionRateS = pajVw.getDilutionRate();
       String usagRateS = pajVw.getUsageRate();
       String numeratorS = pajVw.getUnitCdNumerator();
       String denominatorS = pajVw.getUnitCdDenominator();
       String sharingS = pajVw.getSharingPercent();
%>
<tr>
<% if(ind2==0) { %>
<td rowspan='<%=prodQty%>'><%=cpD.getShortDesc()%></td>
<% } %>
<td><%=iD.getSkuNum()%></td>
<td><%=iD.getShortDesc()%></td>
<td><%=pupD.getUnitSize()%>&nbsp;<%=pupD.getUnitCd()%></td>
<td><%=pupD.getPackQty()%></td>
<td><%if(dilutionRateS!=null && dilutionRateS.trim().length()>0) {%>
1:<%=dilutionRateS%>
<% } %>&nbsp;
</td>
<td><%=usagRateS%> 
   &nbsp;<%=numeratorS%>&nbsp;per&nbsp;
   <%=denominatorS%>
<td> <%=sharingS%></td>
</tr>
<% } %>
<% } %>
<% } %>
  <tr>
  <td colspan='8'>
  <html:submit property="action" value="Modify Products"/>
  </td>
</tr>
</table>
</td>
</tr>
<% } %>
<% } %>

