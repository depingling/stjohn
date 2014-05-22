<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="manuf" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Manufacturers</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admManufToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" width="769"  class="mainbody">
<html:form name="MANUF_DETAIL_FORM" action="adminportal/manufmgrDetail.do"
    scope="session" type="com.cleanwise.view.forms.ManufMgrDetailForm">
<tr>
<td class="largeheader" colspan="4">&nbsp;</td>
</tr>
<tr>
<td><b>Manufacturer&nbsp;Id:</b></td>
<td>
<bean:write name="MANUF_DETAIL_FORM" property="id"/>
<html:hidden property="id"/>
</td>
 <td><b>Name:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="name" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
  </tr>
  
<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
<tr>
	<td><b>Store&nbsp;Id:</b></td>
	<td>
		<html:text tabindex="0" property="storeId" size="5"/><span class="reqind">*</span>
		<html:button property="action" onclick="popLocateGlobal('storelocate', 'storeId');" value="Locate Store"/>
	</td>
	<td colspan="2">&nbsp;</td>
</tr>
</logic:equal>
  
<tr>
<td><b>Status:</b></td>
<td>
<html:select name="MANUF_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Manuf.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td colspan="4" class="largeheader"><br>Contact and Address</td>
</tr>
<tr>
<td><b>First Name:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="name1" maxlength="30"/>
</td>
<td><b>Phone:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="phone" maxlength="30"/>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="name2" maxlength="30"/>
</td>
<td><b>Fax:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="fax" maxlength="30"/>
</td>

</tr>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="streetAddr1" maxlength="80"/>
<span class="reqind">*</span>
</td>
<td><b>Email:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="emailAddress" maxlength="80"/>
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="streetAddr2" maxlength="80"/>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="MANUF_DETAIL_FORM" property="country">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="streetAddr3" maxlength="80"/>
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="MANUF_DETAIL_FORM" 
  property="stateOrProv" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="city" maxlength="40"/>
<span class="reqind">*</span>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="MANUF_DETAIL_FORM" property="postalCode" maxlength="15"/>
<span class="reqind">*</span>
</td>

</tr>

<tr>
<td colspan="4" class="largeheader"><br>
Specializations</td>
</tr>

<tr>                  
<td colspan="3"><html:checkbox name="MANUF_DETAIL_FORM" property="specialization1"/><b>Accessories</b></td>
</tr>
                
<tr>
<td colspan="3"> <html:checkbox name="MANUF_DETAIL_FORM" property="specialization2"/><b>Chemicals</b></td>
</tr>

<tr>
<td colspan="3"><html:checkbox name="MANUF_DETAIL_FORM" property="specialization3"/><b>Disposables and Lodging Supplies</b></td>
</tr>

<tr>
<td colspan="3"><html:checkbox name="MANUF_DETAIL_FORM" property="specialization4"/><b>Machines</b></td>
</tr>

<tr>
<td><b>Business Class:</b></td>
<td colspan="3">
<html:select name="MANUF_DETAIL_FORM" property="businessClass">
<html:options  collection="business.class.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>

<tr>
<td><b>Woman Owned Business:</b></td>
<td colspan="3">
<html:select name="MANUF_DETAIL_FORM" property="womanOwnedBusiness">
<html:options  collection="woman.owned.business.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>

<tr>
<td><b>Minority Owned Business:</b></td>
<td colspan="3">
<html:select name="MANUF_DETAIL_FORM" property="minorityOwnedBusiness">
<html:options  collection="minority.owned.business.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>

<tr>
<td><b>JWOD:</b></td>
<td colspan="3">
<html:select name="MANUF_DETAIL_FORM" property="JWOD">
<html:options  collection="jwod.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>

<tr>
<td><b>Other Business:</b></td>
<td colspan="3">
<html:select name="MANUF_DETAIL_FORM" property="otherBusiness">
<html:options  collection="other.business.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>

<tr>
    <td colspan=4 align=center>
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:reset>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
    <logic:notEqual name="MANUF_DETAIL_FORM" property="id" value="0">
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.delete"/>
      </html:submit>
    </logic:notEqual>
    </td>
  
</tr>
</html:form>

</table>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>
