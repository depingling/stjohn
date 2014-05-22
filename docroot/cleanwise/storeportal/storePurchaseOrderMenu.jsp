<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table ID=991 width="<%=Constants.TABLEWIDTH%>">
    <tr bgcolor="#000000">
        <app:renderStatefulButton 
                link="storePurchaseOrderMgr.do" 
                name="Search PO" 
                tabClassOff="tbar"
                tabClassOn="tbarOn" 
                linkClassOff="tbar" 
                linkClassOn="tbarOn" 
                contains="storePurchaseOrderMgr"/>
        <app:renderStatefulButton 
                link="storePurchaseOrderLineTrackerOp.do" 
                name="Track Open PO Lines" 
                tabClassOff="tbar"
                tabClassOn="tbarOn" 
                linkClassOff="tbar" 
                linkClassOn="tbarOn" 
                contains="storePurchaseOrderLineTrackerOp,
                          storePurchaseOrderMgrOpDetail,
                          storeOrderOpNote,
                          storeOrderOpDetailPrint,
                          storeOrderOpItemDetail"/>
    </tr>
</table>
