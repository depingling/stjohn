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
        <app:renderStatefulButton link="masterAssetProfile.do?action=storeMasterAssetDetail" name="Detail" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="masterAssetProfile,masterAssetContentDetail"/>
    </tr>
</table>
