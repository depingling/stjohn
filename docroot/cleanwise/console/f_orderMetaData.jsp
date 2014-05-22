<table>
<% 
 OrderMetaDataVector omDV = orderStatusDetail.getOrderMetaData();
 if(omDV!=null) {
   for(Iterator iter = omDV.iterator(); iter.hasNext(); ) {
   OrderMetaData omD = (OrderMetaData) iter.next();
   String metaName = omD.getName();
   String metaValue = omD.getValue();   
   if("DISCOUNT".equals(metaName)) {
	   if(metaValue ==null) continue;   
	   try {
	      double damt = Double.parseDouble(metaValue);		  
		  if(damt>-0.001 && damt < 0.001) continue;
	   } catch (Exception exc) {
		  continue;
	   }
   }
%>
<tr>
<td><b> <%=metaName%>: </b></td>
<td> <%=metaValue%> </td>
</tr>
<% }} %>

<!-- SiteSpecific properties. -->
<% 
 OrderPropertyDataVector opDV = orderStatusDetail.getReferenceNumList();
 if(opDV!=null) {
 for(Iterator iter=opDV.iterator(); iter.hasNext(); ) {
   OrderPropertyData opD = (OrderPropertyData) iter.next();
   pageContext.setAttribute("opD",opD);
   String propType = opD.getOrderPropertyTypeCd();
   String propName = opD.getShortDesc();
   String propValue = opD.getValue();
%>

 <tr>
 <td> <b><bean:write name="opD" property="orderPropertyTypeCd" filter="true"/>.<bean:write name="opD" property="shortDesc" filter="true"/></b></td>
 <td><bean:write name="opD" property="value" filter="true"/> </td>
</tr>
<% }} %>
<!-- SiteSpecific properties. -->
<% 
 SiteData siteD = orderStatusDetail.getOrderSiteData();
 if(siteD!=null) {
 Integer siteRank = siteD.getTargetFacilityRank();
 String siteRankS = (siteRank==null)?"":siteRank.toString();
 if ( siteD.getIsaTargetFacility() == true ) {
%>
  <tr class="rowa">
<% } else { %>
  <tr class="rowb">
<% } %>
<td><b>Site Rank:</b></td>
<td><%=siteRankS%></td>
</tr>
<% } %>
</table>

