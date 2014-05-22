<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="MSDS_FORM" type="com.cleanwise.view.forms.MsdsSpecsForm"/>

<%
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURIPage(request)+"?"; 

if(query!=null){
    query="tabs="+request.getParameter("tabs")+"&display="+request.getParameter("display")+"&";
    currUri+=query;
}
%>

<!-- list of items -->
<% 
   ItemDataDocUrlsViewVector sciDV = theForm.getSpecUrls();
   if(sciDV!=null && sciDV.size()>0) {
%>
<table align="left" border="0" cellpadding="0" cellspacing="0">
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.msdsSpecs.text.mySpecifications"/></b></td>
<td class="smalltext">
  &nbsp;
</td>
</tr>
<logic:iterate id="item" name="MSDS_FORM" property="specUrls"
   indexId="kkk"
   type="com.cleanwise.service.api.value.ItemDataDocUrlsView">
  <tr>
  <td class="smalltext">
  <%
  String docUrl =item.getSpec();
  String specStorageType = item.getSpecStorageTypeCd();
  if ((specStorageType == null) || specStorageType.trim().equals("")) {
	      specStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
  }
  if(docUrl!=null && docUrl.trim().length()>0) {
  %>
  <% if (specStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) { %>
         <%    String documentTypeLinkFromDb = "/store/shop.do?action=itemDocumentFromDb&path=" + docUrl; 	%>
	      <html:link action="<%=documentTypeLinkFromDb %>" target="_blank"><bean:write name="item" property="itemName"/>&nbsp;
	      </html:link>
  <% } %>
  <% if (specStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) { %> 
    <%    String documentTypeLink = "/store/shop.do?action=itemDocument&path=" + docUrl; 	%>
	      <html:link action="<%=documentTypeLink %>" target="_blank"><bean:write name="item" property="itemName"/>&nbsp;
	      </html:link>
  <% } %>
<%} else { // docUrl = null %>
    <bean:write name="item" property="itemName"/>&nbsp;
<%} %>
  </td>
  <td class="smalltext" align="center">    
    &nbsp;
  </td>
  </tr>
</logic:iterate>
<tr><td class="smalltext">
</td>
</tr>
</table>
<% } %>
