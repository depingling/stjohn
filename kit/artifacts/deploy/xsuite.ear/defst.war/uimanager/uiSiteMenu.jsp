<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="<%=Constants.TABLEWIDTH800%>">
  <tr bgcolor="#000000">
    <app:renderStatefulButton link="sitedet.do?action=sitedetail" name="Detail" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="uiSiteDetail"/>
  </tr>
</table>
