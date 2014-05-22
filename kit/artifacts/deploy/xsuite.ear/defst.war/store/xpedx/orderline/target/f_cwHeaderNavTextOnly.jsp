<%@ page language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<%
String displayHelpSection = request.getParameter("displayHelpSection");
String enableShop = request.getParameter("enableShop");
String shopUrl = request.getParameter("shopUrl");
%>

<%
  String pictureBar = "<table width=" + Constants.TABLEWIDTH +
        " cellpadding=4 cellspacing=0><tr>";

   pictureBar = "<td><img src=\""+IMGPath+"/cw_globalleft_s.gif\" border=0 ></td>";

  pictureBar +=
   "<td width=110 class=\"textToolBar_cl\"><a "+
   "\" href=\"" + shopUrl + "\" >SHOP</a></td>"+
   "<td class=\"textToolBar_cl\" ><a " +
   "\" href=\"../trainingContent/training.do\"  >TRAINING</a></td>"+
   "<td width=150 class=\"textToolBar_cl\"><a " +
   "\" href=\"../educatorContent/educator.jsp\"  >PRODUCT SELECTOR</a></td>"+
   "<td class=\"textToolBar_cl\"><a " +
   "\" href=\"../troubleshooting/troubleshooting.jsp\"  >TROUBLESHOOTER</a></td>"+
   "<td class=\"textToolBar_cl\"><a " +
   "\" href=\"../userportal/msdsTemplate.do?tabs=safetyToolBar&display=msdsTemplate\"  >MSDS &amp; SPECS</a></td>" +
   "<td ><img src=\""+IMGPath+"/cw_globalright_s.gif\" border=\"0\" ></td>" +
   "</tr></table>";
%>

<table id="HelpTable" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr><td><%=pictureBar%></td></tr>
</table>