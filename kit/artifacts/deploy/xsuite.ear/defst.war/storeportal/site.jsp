<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<bean:define id="theForm" name="STORE_ADMIN_SITE_FORM" type="com.cleanwise.view.forms.StoreSiteMgrForm"/>

<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/sitesearch.do" />
   <jsp:param name="jspFormName" 	value="STORE_ADMIN_SITE_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>
<table ID=448>
<tr>
<td>
<html:form styleId="449" name="STORE_ADMIN_SITE_FORM" action="storeportal/sitesearch.do"  >
<table ID=450 width="<%=Constants.TABLEWIDTH%>" class="mainbody">
	<tr> <td align="right"><b>Site Name</b></td>
	<td colspan="4">
		<html:text  property="searchField"/>
		<html:radio property="searchType" value="id" />  ID
		<html:radio property="searchType" value="nameBegins" />  Name(starts with)
		<html:radio property="searchType" value="nameContains" />  Name(contains)
	</td>
<td><a ID=451 href="#" onclick="f_reset_fields(document.STORE_ADMIN_SITE_FORM);">
<input type="button" value="Reset search fields"></a> 
</td>
	</tr>
	
	<tr> <td align="right"><b>Reference Number</b></td>
	<td colspan="4">
		<html:text  property="searchRefNum"/>
		<html:radio property="searchRefNumType" value="nameBegins" /> (starts with)
		<html:radio property="searchRefNumType" value="nameContains" /> (contains)
	</td>
	
  <tr> 
   <td align="right"><b>Account(s)</b></td>
   <td colspan='3'>
  <%
   AccountDataVector accountDV = theForm.getAccountFilter();
   if(accountDV!=null && accountDV.size()>0) {
     for(int ii=0; ii<accountDV.size(); ii++) {
        AccountData accountD = (AccountData) accountDV.get(ii);
  %>
   &lt;<%=accountD.getBusEntity().getShortDesc()%>&gt;
   <% } %>
  <html:submit property="action" value="Clear Account Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Account" styleClass='text' />
  </td></tr>

	
	
	<tr>
	   <td align="right"><b>City</b></td>
	   <td><html:text property="searchCity"/>
	</td>
	<td align="right"><b>County</b></td>
	<td><html:text property="searchCounty"/></td>
	</tr>
	<tr>
	   <td align="right"><b>State</b></td>
	   <td><html:text property="searchState"/> </td>
	<td align="right"><b>Zip</b></td>
	<td><html:text property="searchPostalCode"/></td>
	</tr>
	
	  <tr> <td> &nbsp;</td>
	       <td colspan="3">
		<html:submit property="action">
			<app:storeMessage  key="global.action.label.search"/>
		</html:submit>
  	<html:submit property="action">
	  	<app:storeMessage  key="admin.button.create"/>
	  </html:submit>
    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Show Inactive <html:checkbox property="showInactiveFl" value="true" />
	     
	    </td>
	  </tr>
</table>
</html:form>

<% SiteViewVector inactiveAccountSiteV = (SiteViewVector) session.getAttribute("Site.inactiveAccountSite.vector"); %>
<% 
  SiteViewVector siteVwV = (SiteViewVector) session.getAttribute("Site.found.vector");
  if(siteVwV!=null){    
%>
Count:<%=siteVwV.size()%>
<table width="<%=Constants.TABLEWIDTH%>" class="stpTable_sortable" id="ts1"> 
<thead>
	<tr>
		<th class="stpTH">&nbsp;</th>
		<th class="stpTH">Site&nbsp;Id</th>
		<th class="stpTH">Rank</th>
		<th class="stpTH">Account Name</th>
    	<th class="stpTH">Site Name</th>
    	<th class="stpTH">Street Address</th>
    	<th class="stpTH">City</th>
    	<th class="stpTH">State<br>Prov</th>
		<th class="stpTH">Zip</th>
		<th class="stpTH">County</th>
		<th class="stpTH">Status</th>
	</tr>
</thead>
    <tbody id="itemTblBdy">
  <% int found_inactive_acct; %>
  <% if(siteVwV.size()==1000){ %> (request limit)<% } %>
	<% for(Iterator iter=siteVwV.iterator(); iter.hasNext();) {
	 found_inactive_acct = 0;	
     SiteView siteVw = (SiteView) iter.next();
     int siteId = siteVw.getId();
     String hrefDet = "sitedet.do?action=sitedetail&searchType=id&searchField="+siteId;
	   int targetFacilityRank = siteVw.getTargetFacilityRank();
     String rowClass = 
       (targetFacilityRank>=com.cleanwise.service.api.util.Utility.TARGET_FACILITY_THRESHOLD.intValue())?
       "rowa":"stpTD";
     for(Iterator iter1=inactiveAccountSiteV.iterator(); iter1.hasNext();) {
         SiteView iAccountSite = (SiteView) iter1.next();
         if(siteVw.getAccountId() == iAccountSite.getAccountId()){
            found_inactive_acct = 1;
            break;
         }                
     }
  %>
     <tr>				
		<td class="stpTD">
      <% if(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(siteVw.getStatus())
    		&& (found_inactive_acct == 0)) { %>
			<a ID=452 href="../userportal/msbsites.do?action=shop_site&start_point=shop_quick_order&siteId=<%=siteId%>">Shop</a>
      <% } else { %>
       &nbsp;
      <% } %>
		</td>
		<td class="stpTD"><%=siteId%></td>
		<td class="stpTD"><%=targetFacilityRank%></td>
		<td class="stpTD"><%=siteVw.getAccountName()%></td>
        <td class="stpTD"><a ID=453 href="<%=hrefDet%>"><%=siteVw.getName()%></a></td>
 		<td class="stpTD"><%=siteVw.getAddress()%></td>
 		<td class="stpTD"><%=siteVw.getCity()%></td>
 		<td class="stpTD"><%=siteVw.getState()%></td>
 		<td class="stpTD"><%=siteVw.getPostalCode()%></td>
 		<td class="stpTD"><%if(siteVw.getCounty()!=null){%><%=siteVw.getCounty()%><%}%></td>
 		<td class="stpTD"><%=siteVw.getStatus()%></td>
		</tr>
<% } %>
    </tbody>
</table>
<%}%>

</td>
</tr>
</table>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms[0]['searchField'];
  if('undefined' != typeof focusControl) {
     focusControl.focus();
  }
  
function kH(e) {
  var keyCode = window.event.keyCode;
  if(keyCode==13) {
    var actionButton = document.forms[0]['action'];
    if('undefined' != typeof actionButton) {
      var len = actionButton.length;
      for(ii=0; ii<len; ii++) {
        if('Search' == actionButton[ii].value) {
          actionButton[ii].select();
          actionButton[ii].click();
          break;
        }      
      }
    }
  }
}
document.onkeypress = kH;
  

  
  // -->
</script>

