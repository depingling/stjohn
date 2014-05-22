<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table align=center CELLSPACING=0 CELLPADDING=0 width="840">

  <tr>
    <td width="150" valign="top"> <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="ogMenu.jsp"/>
                        <jsp:param name="width" value="150"/>
                        <jsp:param name="height" value="200"/>
                    </jsp:include>
</td>
    <td valign="top" align ="left" width="100%">
        <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"t_homePageFramePreview.jsp")%>'/>
        </td>
    <td align="right" valign="top"><jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="autoDistroInfo.jsp"/>
                        <jsp:param name="width" value="200"/>
                        <jsp:param name="height" value="100"/>
                    </jsp:include></td>
  </tr>
  <tr>
    <td colspan="2" valign="top">
                    <br>
                    <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="verticalMenu.jsp"/>
                        <jsp:param name="width" value="150"/>
                        <jsp:param name="height" value="400"/>
                    </jsp:include></td>
    <td  align="right" valign="top" >
                    <br>
                    <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"xpdexGradientCantainer.jsp")%>'>
                        <jsp:param name="content" value="appleNews.jsp"/>
                        <jsp:param name="width" value="200"/>
                        <jsp:param name="height" value="400"/>
                    </jsp:include></td>
  </tr>
</table>

