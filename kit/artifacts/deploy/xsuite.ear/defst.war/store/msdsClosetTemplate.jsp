
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>


<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="MSDS_FORM" type="com.cleanwise.view.forms.MsdsSpecsForm"/>

<%
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURIPage(request)+"?"; 
String submitBase=SessionTool.getActualRequestedURIStrutsMapping(request)+"?"; 

if(query!=null){
    query="tabs="+request.getParameter("tabs")+"&display="+request.getParameter("display")+"&";
    currUri+=query;
    submitBase+=query;
}
String submitUrl1 = submitBase+"action=control";
%>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<% if(request.getAttribute("org.apache.struts.action.ERROR")!=null) { %>
<tr>
<td class="tableoutline" width="1" bgcolor="black"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
<td class="genericerror" align="center"><html:errors/></td>
<td class="tableoutline" width="1" bgcolor="black"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<% } %>
<tr>
<td class="tableoutline" width="1">
<img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
<td class="msdsdk"><jsp:include flush='true' page="t_msdsToolbar.jsp"/></td>
<td class="tableoutline" width="1"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<!-- content, shopping catalog -->
<tr>
<td class="tableoutline" width="1"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
<td>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
  <%String submitUrl1=submitUrl1+"action=control";%>
  <html:form action="<%=submitUrl1%>">
  <tr>
  <td class="smalltext" valign="up" width="60%">
   <table align="left" border="0" cellpadding="0" cellspacing="0">
   <tr><td><jsp:include flush='true' page="t_msdsMsdsList.jsp"/></td></tr>
   <tr><td>&nbsp;</td></tr>
   <tr><td><jsp:include flush='true' page="t_msdsSpecList.jsp"/></td></tr>
   <tr><td>&nbsp;</td></tr>
   <tr><td><jsp:include flush='true' page="t_msdsDedList.jsp"/></td></tr>
   </table>
  </td>
  <!-- search -->
  <td align="left" valign="top" class="smalltext" width="40%">
    <jsp:include flush='true' page="t_msdsSearch.jsp"/>
  </td>
  </tr>
  </html:form>
</table>
</td>
<td class="tableoutline" width="1"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>

</table>
</td>
</tr>
<tr>
<td>
</table>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_left_footer_shop.gif" ALIGN="top">
</td>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_middle_footer_shop.gif" ALIGN="top" width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
</td>
<td>
 <img src="/<%=storeDir%>/<%=ip%>images/cw_right_footer_shop.gif" ALIGN="top">
</td>
</tr>
</table>






