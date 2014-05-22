<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" class="mainbody" width="769">
<!-- html:form action="/storeportal/storeBlanketPoDetail.do"-->
<html:form action="/storeportal/blanketPoDetail.do">
  <tr>
  	<td>&nbsp;</td>
	<td><b>id</b></td><td><bean:write name="STORE_BLANKET_PO_FORM" property="blanketPoNumDescData.blanketPoNumData.blanketPoNumId"/></td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
	<td><b>Name:</b></td><td><html:text name="STORE_BLANKET_PO_FORM" property="blanketPoNumDescData.blanketPoNumData.shortDesc"/></td>
	<td><b>Status:</b></td>
	<td>
		<html:select name="STORE_BLANKET_PO_FORM" property="blanketPoNumDescData.blanketPoNumData.statusCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="simple.status" property="value" />
		</html:select>
	</td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
	<td><b>Type:</b></td>
	<td>
		<html:select name="STORE_BLANKET_PO_FORM" property="blanketPoNumDescData.blanketPoNumData.blanketCustPoNumberTypeCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="blanket.req.po.num.type.cd" property="value" />
		</html:select>
	</td>
	<td><b>Current Release:</b></td><td><html:text name="STORE_BLANKET_PO_FORM" property="currentRelease"/></td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
	<td><b>Po Number:</b></td><td><html:text name="STORE_BLANKET_PO_FORM" property="blanketPoNumDescData.blanketPoNumData.poNumber"/></td>
	<td><b>Seperator:</b></td><td><html:text name="STORE_BLANKET_PO_FORM" property="blanketPoNumDescData.blanketPoNumData.seperator"/></td>
  </tr>
  <tr>
     <td colspan="5" align="center">
        <html:submit property="action">
		<app:storeMessage  key="global.action.label.save"/>
	</html:submit>
    </td>
  </tr>
</html:form>
</table>
</div>
