<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="manuf" type="java.lang.String" toScope="session"/>

<div class="text">

<table ID=1579 cellspacing="0" border="0" width="769"  class="mainbody">
<html:form styleId="1580" name="STORE_SVCPROV_DETAIL_FORM" action="storeportal/sprdet.do"
    scope="session" type="com.cleanwise.view.forms.StoreServiceProviderMgrDetailForm">
<tr>
<td class="largeheader" colspan="4">&nbsp;</td>
</tr>
<tr>
<td><b>Service&nbsp;Provider&nbsp;Id:</b></td>
<td>
<bean:write name="STORE_SVCPROV_DETAIL_FORM" property="id"/>
<html:hidden property="id"/>
</td>
 <td><b>Name:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="name" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
</tr>
<tr><td><br></td></tr>
<tr valign="top">
<td><b>Status:</b></td>
<td>
<html:select name="STORE_SVCPROV_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="SvcProv.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td>
    <b>Type:</b>&nbsp;
    <span class="reqind">*</span>
</td>

<td>
<!--
<html:select name="STORE_SVCPROV_DETAIL_FORM" property="serviceProviderType">

<html:option value="0"><app:storeMessage  key="admin.select"/></html:option>

<html:options  collection="SvcProv.type.vector" labelProperty="shortDesc" property="busEntityId" />

</html:select>
-->
    <logic:present name="SvcProv.type.vector">
        <bean:size id="typescount"  name="SvcProv.type.vector"/>
        <logic:greaterThan name="typescount" value="0">
        <table cellspacing="0" border="0" class="mainbody">
            <logic:iterate id="arrele" name="SvcProv.type.vector" type="com.cleanwise.service.api.value.BusEntityData">
            <%
              String busEntityId = String.valueOf(arrele.getBusEntityId());
            %>
            <tr>
                <td>
                    <bean:write name="arrele" property="shortDesc"/>
                </td>
                <td>
                    <html:multibox name="STORE_SVCPROV_DETAIL_FORM" property="serviceProviderType" value="<%=busEntityId%>" />
                </td>
            </tr>
            </logic:iterate>
        </table>
        </logic:greaterThan>
    </logic:present>
</td>

</tr>
<tr>
<td colspan="4" class="largeheader"><br>Contact and Address</td>
</tr>
<tr>
<td><b>First Name:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="name1" maxlength="30"/>
</td>
<td><b>Phone:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="phone" maxlength="30"/>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="name2" maxlength="30"/>
</td>
<td><b>Fax:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="fax" maxlength="30"/>
</td>

</tr>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="streetAddr1" maxlength="80"/>
</td>
<td><b>Email:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="emailAddress" maxlength="80"/>
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="streetAddr2" maxlength="80"/>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="STORE_SVCPROV_DETAIL_FORM" property="country">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" labelProperty="uiName" property="shortDesc" />
</html:select>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="streetAddr3" maxlength="80"/>
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="STORE_SVCPROV_DETAIL_FORM"
  property="stateOrProv" />
</td>
</tr>
<tr>
<td><b>City</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="city" maxlength="40"/>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="STORE_SVCPROV_DETAIL_FORM" property="postalCode" maxlength="15"/>
</td>

</tr>
<tr>
</tr>
<td>&nbsp;</td>
<tr>
    <td colspan=4 align=center>
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:reset>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
    <logic:notEqual name="STORE_SVCPROV_DETAIL_FORM" property="id" value="0">
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.delete"/>
      </html:submit>
    </logic:notEqual>
    </td>

</tr>
</html:form>

</table>

</div>

