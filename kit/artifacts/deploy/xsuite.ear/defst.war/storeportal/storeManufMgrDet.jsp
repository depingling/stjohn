<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="manuf" type="java.lang.String" toScope="session"/>

<div class="text">

<table ID=1035 cellspacing="0" border="0" width="769"  class="mainbody">
<html:form styleId="1036" name="STORE_MANUF_DETAIL_FORM" action="storeportal/manufdet.do"
    scope="session" type="com.cleanwise.view.forms.StoreManufMgrDetailForm">
<tr>
<td class="largeheader" colspan="4">&nbsp;</td>
</tr>
<tr>
<td><b>Manufacturer&nbsp;Id:</b></td>
<td>
<bean:write name="STORE_MANUF_DETAIL_FORM" property="id"/>
<html:hidden property="id"/>
</td>
 <td><b>Name:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="name" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
  </tr>

<tr>
<td><b>Status:</b></td>
<td>
<html:select name="STORE_MANUF_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Manuf.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td><b>MSDS Plug-in:</b></td>
<td>
<html:select name="STORE_MANUF_DETAIL_FORM" property="msdsPlugin">
<html:option value="<%=RefCodeNames.MSDS_PLUGIN_CD.DEFAULT%>"><%=RefCodeNames.MSDS_PLUGIN_CD.DEFAULT%></html:option>
<html:option value="<%=RefCodeNames.MSDS_PLUGIN_CD.DIVERSEY_WEB_SERVICES%>"><%=RefCodeNames.MSDS_PLUGIN_CD.DIVERSEY_WEB_SERVICES%></html:option>
</html:select>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td colspan="4" class="largeheader"><br>Contact and Address</td>
</tr>
<tr>
<td><b>First Name:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="name1" maxlength="30"/>
</td>
<td><b>Phone:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="phone" maxlength="30"/>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="name2" maxlength="30"/>
</td>
<td><b>Fax:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="fax" maxlength="30"/>
</td>

</tr>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="streetAddr1" maxlength="80"/>
</td>
<td><b>Email:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="emailAddress" maxlength="80"/>
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="streetAddr2" maxlength="80"/>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="STORE_MANUF_DETAIL_FORM" property="country">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" labelProperty="uiName" property="shortDesc" />
</html:select>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="streetAddr3" maxlength="80"/>
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="STORE_MANUF_DETAIL_FORM"
  property="stateOrProv" />
</td>
</tr>
<tr>
<td><b>City</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="city" maxlength="40"/>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="STORE_MANUF_DETAIL_FORM" property="postalCode" maxlength="15"/>
</td>

</tr>

<tr>
<td colspan="4" class="largeheader"><br>
Specializations</td>
</tr>

<tr>
<td colspan="3"><html:checkbox name="STORE_MANUF_DETAIL_FORM" property="specialization1"/><b>Accessories</b></td>
</tr>

<tr>
<td colspan="3"> <html:checkbox name="STORE_MANUF_DETAIL_FORM" property="specialization2"/><b>Chemicals</b></td>
</tr>

<tr>
<td colspan="3"><html:checkbox name="STORE_MANUF_DETAIL_FORM" property="specialization3"/><b>Disposables and Lodging Supplies</b></td>
</tr>

<tr>
<td colspan="3"><html:checkbox name="STORE_MANUF_DETAIL_FORM" property="specialization4"/><b>Machines</b></td>
</tr>

<tr>
<td><b>Business Class:</b></td>
<td colspan="3">
<html:select name="STORE_MANUF_DETAIL_FORM" property="businessClass">
<html:options  collection="business.class.vector" property="value" />
</html:select>
</td>
</tr>

<tr>
<td><b>Woman Owned Business:</b></td>
<td colspan="3">
<html:select name="STORE_MANUF_DETAIL_FORM" property="womanOwnedBusiness">
<html:options  collection="woman.owned.business.vector" property="value" />
</html:select>
</td>
</tr>

<tr>
<td><b>Minority Owned Business:</b></td>
<td colspan="3">
<html:select name="STORE_MANUF_DETAIL_FORM" property="minorityOwnedBusiness">
<html:options  collection="minority.owned.business.vector" property="value" />
</html:select>
</td>
</tr>

<tr>
<td><b>JWOD:</b></td>
<td colspan="3">
<html:select name="STORE_MANUF_DETAIL_FORM" property="JWOD">
<html:options  collection="jwod.vector" property="value" />
</html:select>
</td>
</tr>

<tr>
<td><b>Other Business:</b></td>
<td colspan="3">
<html:select name="STORE_MANUF_DETAIL_FORM" property="otherBusiness">
<html:options  collection="other.business.vector" property="value" />
</html:select>
</td>
</tr>

<logic:equal name="STORE_MANUF_DETAIL_FORM" property="showManufEquvalentName" value="true">
<tr>
<td colspan="4" class="largeheader"><br>Other Equivalent Manufacturer Name:</td>
</tr>
<tr>
<td colspan="4">
<html:textarea name="STORE_MANUF_DETAIL_FORM" property="otherEquivalentManufNames" rows="4" cols="80"/>
</td>
<tr>
<tr>
<td colspan="4">&nbsp;</td>
</tr>
</logic:equal>  

<tr>
    <td colspan=4 align=center>
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:reset>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
    <logic:notEqual name="STORE_MANUF_DETAIL_FORM" property="id" value="0">
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.delete"/>
      </html:submit>
    </logic:notEqual>
    </td>

</tr>
</html:form>

</table>

</div>

