<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
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
   ItemDataDocUrlsViewVector sciDV = theForm.getMsdsUrls();
   if(sciDV!=null && sciDV.size()>0) {
%>
<table align="left" border="0" cellpadding="0" cellspacing="0">
<tr>
<td class="smalltext"><b><app:storeMessage key="shop.msdsSpecs.text.myMsds"/></b></td>
<td width="14%">&nbsp;</td>
<td class="smalltext">
  &nbsp;
</td>
</tr>
<logic:iterate id="item" name="MSDS_FORM" property="msdsUrls"
   indexId="kkk"
   type="com.cleanwise.service.api.value.ItemDataDocUrlsView">
  <tr>
  <td class="smalltext">
<%  
  String docUrl = null;
  String documentTypeLink = null;

  String msdsStorageType = item.getMsdsStorageTypeCd();
  if ((msdsStorageType == null) || msdsStorageType.trim().equals("")) {
	      msdsStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
  }

  if (msdsStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) { 	
      docUrl =item.getMsds();
      if(docUrl!=null && docUrl.trim().length()>0) {
      %>
      <% 
         documentTypeLink = "/store/shop.do?action=itemDocumentFromDb&path=" + docUrl; 
 	  %>

   <% } %>
<% } %>
<% if (msdsStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {  
      docUrl =item.getMsds();
      if(docUrl!=null && docUrl.trim().length()>0) {
      %>
      <% 
         documentTypeLink = "/store/shop.do?action=itemDocumentFromE3Storage&path=" + docUrl; 
 	  %>

   <% } %>
<% } %>
    <%     
           int fl_msds_plugin = 0;
          
           String msds=item.getMsds();
           String msdsJdUrl = item.getMsdsViaJDWebService(); 
           if ((msds!=null && msds.trim().length()>0)
              && (msdsJdUrl==null || msdsJdUrl.trim().length()==0)) 
           {   //ONLY MSDS Pdf image exists, NO MSDS Plug-in
        	   docUrl = msds;
           }   
           if ((msds!=null && msds.trim().length()>0) 
          	  &&(msdsJdUrl!=null && msdsJdUrl.trim().length()>0)) 
           {  //MSDS Plug-in IS set
          		 docUrl = msdsJdUrl;
          		 fl_msds_plugin = 1;
           }
           if(docUrl!=null && docUrl.trim().length()>0) {
         	  if (fl_msds_plugin == 1) { //msds Plugin
 	 %>  
 	              <a href="/<%=storeDir%>/<%=docUrl%>" target="_blank">
                     <bean:write name="item" property="itemName"/>&nbsp;
                  </a> 
           <% } else {          	      
 	      %>
 	              <html:link action="<%=documentTypeLink %>" target="_blank">
 	                 <bean:write name="item" property="itemName"/>&nbsp;
 	              </html:link>
 	      <%  } %>	                       
        <% } else { %>
              <bean:write name="item" property="itemName"/>&nbsp;
        <% } %>	   
  </td>
  <td>&nbsp;</td>
  <td class="smalltext" align="center">
   <% 
    String clickToRemove = ClwI18nUtil.getMessage(request,"shop.msdsSpecs.text.clickHereToRemoveFromMyList",null);
    String removeDocAction =currUri+"action=removeDoc&itemId="+item.getItemId()+"&docType=MSDS";
   %>
     &nbsp;
  </td>
  </tr>
</logic:iterate>
<tr><td class="smalltext">
</td>
</tr>
</table>
<% } %>


