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

<bean:define id="theForm" name="STORE_SVCPROV_DETAIL_FORM" type="com.cleanwise.view.forms.StoreServiceProviderMgrDetailForm"/>

<table ID=1581 width="769">
  <tr bgcolor="#000000">
    <app:renderStatefulButton link="sprdet.do?action=returnToDetailPage" name="Detail" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="sprdet"/>
        
		<app:renderStatefulButton link="sprconfig.do?initconfig=true" name="Configuration" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="sprconfig"/>
		
	</tr>
</table>

<!-- 
<table border="0"cellpadding="0" cellspacing="0" width="769">
=======
<table ID=1581 border="0"cellpadding="0" cellspacing="0" width="769">
>>>>>>> 1.2
  <tr bgcolor="#000000">  width="<%=Constants.TABLEWIDTH%>"
    <td class="tbartext">
      <b>Service Provider Detail</b>
    </td>
  </tr>
</table>
-->
