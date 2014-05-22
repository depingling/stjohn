<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="TRADING_PARTNER_FORM" type="com.cleanwise.view.forms.TradingPartnerMgrForm"/>

<div class="text">
  <table width="769" cellspacing="0" border="0" class="mainbody">
  <html:form name="TRADING_PARTNER_FORM" action="adminportal/tradingpartnermgr.do"
    scope="session" type="com.cleanwise.view.forms.TradingPartnerMgrForm" enctype="multipart/form-data">
   <tr>
        <td><b>Trading Partner Id:</b></td>
        <td><html:text name="TRADING_PARTNER_FORM" property="searchPartnerId"/></td>
        <td><b>Partner Status:</b></td>
        <td colspan=3>
           <html:select name="TRADING_PARTNER_FORM" property="searchPartnerStatus">
           <html:option value=" "></html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_STATUS_CD.ACTIVE%>">Active</html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_STATUS_CD.INACTIVE%>">Inactive</html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_STATUS_CD.LIMITED%>">Limited</html:option>
           </html:select>
       </td>
  </tr>
   <tr>
        <td><b>Trading Partner Name:</b></td>
        <td><html:text name="TRADING_PARTNER_FORM" property="searchPartnerName"/></td>
        <td><b>Partner Type:</b></td>
        <td>
           <html:select name="TRADING_PARTNER_FORM" property="searchPartnerType">
           <html:option value=""></html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER%>">Customer</html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR%>">Distributor</html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.MANUFACTURER%>">Manufacturer</html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.APPLICATION%>">Application</html:option>
           <html:option value="<%=RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE%>">Store</html:option>
           </html:select>
       </td>
  </tr>
   <tr>
        <td><b>Customer/Distributor Name:</b></td>
        <td><html:text name="TRADING_PARTNER_FORM" property="searchBusEntityName"/></td>
   </tr>
   <tr>    
        <td><b>Trading Type:</b></td>
        <td>
           <html:select name="TRADING_PARTNER_FORM" property="searchTraidingTypeCD">
           <html:option value=""></html:option>
           <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.PAPER%>">Non electronic</html:option>
           <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.EDI%>">EDI</html:option>
           <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.XML%>">XML</html:option>
           <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.FAX%>">FAX</html:option>
           <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.OTHER%>">OTHER</html:option>
           <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.EMAIL%>">EMAIL</html:option>
           <html:option value="<%=RefCodeNames.TRADING_TYPE_CD.PUNCHOUT%>">PUNCHOUT</html:option>
           </html:select>
       </td> 
  </tr>                  
  <tr>
        <td>&nbsp;</td>
        <td colspan="3">
             <html:radio name="TRADING_PARTNER_FORM" property="searchType" value="<%=\"\"+SearchCriteria.BEGINS_WITH_IGNORE_CASE%>" />
             Starts with
             <html:radio name="TRADING_PARTNER_FORM" property="searchType" value="<%=\"\"+SearchCriteria.CONTAINS_IGNORE_CASE%>" />
             Contains
         </td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td colspan="3">
            <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
            </html:submit>
<!--
            <html:submit property="action">
                    <app:storeMessage  key="admin.button.viewall"/>
            </html:submit>
-->
            <html:submit property="action">
                    <app:storeMessage  key="admin.button.create"/>
            </html:submit>
        </td>
  </tr>    
  <tr>
        <td>&nbsp;</td>
        <td colspan="3">
            <html:file name="TRADING_PARTNER_FORM" property="importFile" size="40" accept="text/xml"/>
            <html:submit property="action">
                    <app:storeMessage  key="admin.button.import"/>
            </html:submit>
       </td>
  </tr>
</html:form>
</table>
<logic:present name="TRADING_PARTNER_FORM" property="tradingPartners">
<bean:size id="rescount"  name="TRADING_PARTNER_FORM" property="tradingPartners"/>
<% Integer n = (Integer)rescount; %>
<% if (n.intValue() < Constants.MAX_SITES_TO_RETURN) { %>
Search result count:  <bean:write name="rescount" />
<% } else { %>
Search results restricted to maximum of <bean:write name="rescount" />.
Narrow search criteria.
<% } %>


<table cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a class="tableheader" href="tradingpartnermgr.do?action=sort&sortField=id">Id</td>
<td width="50%"><a class="tableheader" href="tradingpartnermgr.do?action=sort&sortField=busEntityShortDesc">Account/Distributor Name</td>
<td width="25%"><a class="tableheader" href="tradingpartnermgr.do?action=sort&sortField=shortDesc">Trading Partner Name</td>
<td><a class="tableheader" href="tradingpartnermgr.do?action=sort&sortField=type">Type</td>
<td><a class="tableheader" href="tradingpartnermgr.do?action=sort&sortField=status">Status</td>
<td width="15%"><a class="tableheader" href="tradingpartnermgr.do?action=sort&sortField=traidingTypeCD">Trading Type</td>
<td><a class="tableheader" href="tradingpartnermgr.do?action=sort&sortField=status">&nbsp;</td>

</tr>
<logic:iterate id="arrele" name="TRADING_PARTNER_FORM" property="tradingPartners"
 type="com.cleanwise.service.api.value.TradingPartnerView">
<bean:define id="eleid" name="arrele" property="id"/>
<tr>
<td><bean:write name="arrele" property="id"/></td>
<td><bean:write name="arrele" property="busEntityShortDesc"/></td>
<td>
<a href="tradingpartnermgr.do?action=detail&partnerId=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="type"/></td>
<td><bean:write name="arrele" property="status"/></td>
<td><bean:write name="arrele" property="traidingTypeCD"/></td>
<td>
<a href="tradingpartnermgr.do?action=Export&partnerId=<%=eleid%>">
<app:storeMessage  key="global.action.label.export"/>
</a>
</td>
</tr>

</logic:iterate>
</table>
</logic:present>
</div>


