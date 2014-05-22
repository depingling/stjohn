<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="theForm" name="STORE_DIST_DETAIL_FORM" type="com.cleanwise.view.forms.StoreDistMgrDetailForm"/>

<table ID=729 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
    <logic:equal name="STORE_DIST_DETAIL_FORM" property="id" value="0">
      <td class="tbartext"><b>Distributor Detail</b></td>
    </logic:equal>
    <logic:greaterThan name="STORE_DIST_DETAIL_FORM" property="id" value="0">
    <app:renderStatefulButton link="distdet.do" name="Distributor Detail" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="distmgr,distdet"/>
    <app:renderStatefulButton link="distconf.do" name="Configuration" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="distconf"/>
        <app:renderStatefulButton link="distitem.do" name="Edit Item" tabClassOff="tbar"
            tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="distitem"/>
    <app:renderStatefulButton link="distterr.do" name="Territory" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="distterr"/>
    <app:renderStatefulButton link="dlschdlmgr.do" name="Delivery Schedule" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="dlschdlmgr,dlschdldet"/>
	<%-- <app:renderStatefulButton link="distnotemgr.do" name="Notes" tabClassOff="tbar" tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="distnotemgr"/> --%>
    </logic:greaterThan>
  </tr>
</table>



