<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_SITE_FORM" type="com.cleanwise.view.forms.StoreSiteMgrForm"/>
<%
  int siteId = theForm.getIntId();
%>
<table ID=1211 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
    <% if(siteId>0) { %>
    <app:renderStatefulButton link="sitedet.do?action=sitedetail" name="Detail" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="sitedet"/>
    <app:renderStatefulButton link="siteconfig.do?siteconfig=true" name="Configuration" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="siteconfig"/>
    <app:renderStatefulButton link="siteworkflow.do?action=site_workflow" name="Workflow" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="siteprofile"/>
    <app:renderStatefulButton link="sitebudgets.do?action=sitebudgets" name="Budgets" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="sitebudgets"/>
    <app:renderStatefulButton link="storeSiteAdjustmentLedger.do?action=init" name="Site Ledger" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="storesiteadjustmentledger"/>
      <app:renderStatefulButton link="siteprofiling.do?" name="Profiling" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="siteprofiling"/>
    <app:renderStatefulButton link="orderguidesearch.do" name="Order Guides" tabClassOff="tbar"
        tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="orderguidesearch,orderguidedet,orderguidefinditems,orderguidenew"/>
<%--<app:renderStatefulButton link="sitenote.do?" name="Notes" tabClassOff="tbar" tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="sitenote"/>--%>
    <% }else{ %>
            <td class="tbartext">&nbsp;</td>
        <% } %>
  </tr>
</table>
