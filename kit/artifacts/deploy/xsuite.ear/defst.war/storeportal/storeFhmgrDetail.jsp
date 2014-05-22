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
<bean:define id="Location" value="dist" type="java.lang.String" 
  toScope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Freight Handler Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>  

<div class="text">
<table ID=1599 cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="mainbody">

<html:form styleId="1600" action="storeportal/fhmgrDetail.do">

<tr>
<td><b>Freight Handler Id:</b></td>
<td>
<bean:write name="FH_DETAIL_FORM" property="id" />
<html:hidden property="id"/>
</td>
 <td><b>Name:</b></td>
<td>
<html:text tabindex="1" property="name"  size="30" maxlength="30" />
<span class="reqind">*</span>
</td>
  </tr>
<tr>
<td><b>Status:</b></td>
<td>
<html:select tabindex="3" property="statusCd" >
<html:option value="" ><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Dist.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>EDI Routing Code:</b></td>
<td>
<html:text tabindex="4" property="ediRoutingCd" 
  size="30" maxlength="30" />
</td>

<td><b>Accept Freight On Invoice:</b></td>
<td>
Yes&nbsp;<html:radio 
  property="acceptFreightOnInvoice" value="true" />
&nbsp;&nbsp;&nbsp;
No&nbsp;<html:radio 
  property="acceptFreightOnInvoice" value="false" />
</td>

</tr>

<tr>
<td colspan="4" class="largeheader"><br>Contact and Address</td>
</td>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text tabindex="12" property="streetAddr1" maxlength="80"/>
<span class="reqind">*</span>
</td>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text tabindex="13" property="streetAddr2" maxlength="80" />
</td>
<td><b>Country:</b></td>
<td>
<html:select tabindex="23" property="country" >
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="shortDesc" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text tabindex="14" property="streetAddr3" maxlength="80" />
</td>

<td><b>State/Province:</b></td>
<td>

<html:text tabindex="24" size="20" maxlength="80" 
  property="stateOrProv" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text tabindex="15" property="city" maxlength="40" />
<span class="reqind">*</span>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text tabindex="25" property="postalCode" maxlength="15" />
<span class="reqind">*</span>
</td>

</tr>
 
<tr>
    <td colspan=4 align=center>
      <html:hidden property="action" value="saveFreightHandler"/>
      <html:submit>
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:reset>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
    </td>
</tr>

</html:form>

</table>

</div>
</body>

</html:html>






