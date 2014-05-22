<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" 
  CHARSET="ISO-8859-1"></SCRIPT>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
  <table width="769" cellspacing="0" border="0" class="mainbody">

<bean:parameter id="bscid" name="searchField" value="-1"/>
<bean:define id="bscid2" name="BSC_DETAIL_FORM" property="id"
  type="java.lang.String" />
<%
if ( bscid.equals("-1")) bscid = bscid2; 
%>

<bean:parameter id="respg" name="res" value="-"/>
<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>
<html:form name="BSC_DETAIL_FORM" scope="session"
	action="adminportal/bscmgr.do?res=det"
	type="com.cleanwise.view.forms.BSCDetailForm">

<html:hidden property="id" value="<%=bscid%>"/>

<% if ( bscid.equals("0") 
	&& respg.equals("-") ) { %>
 <tr>
  <td colspan=4>
  <b>Define a new Building Services Contractor</b>
  </td>
 </tr>
 <logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
	<tr>
		<td colspan=2>
		<td><b>Store Id:</b></td>
		<td>
			<html:text tabindex="0" name="BSC_DETAIL_FORM" property="storeId" size="10"/><span class="reqind">*</span>
			<html:button property="action" onclick="popLocateGlobal('storelocate', 'storeId');" value="Locate Store"/>
		</td>
		
	</tr>
</logic:equal>
 <tr>
  <td>
      <html:hidden property="action" value="bsc_add"/>
   <b>Name:</b>
  </td>
  <td>
<html:text tabindex="1" name="BSC_DETAIL_FORM" property="name" 
  size="30" maxlength="30" value=""/>
  </td>
  <td>
   <b>  Order Fax Number:</b>
  </td>
  <td>
<html:text tabindex="2" name="BSC_DETAIL_FORM" property="faxPhoneNumber" 
  size="30" maxlength="30" value=""/>
  </td>
 </tr>
<tr>
  <td> <b> Description:</b>  </td>
  <td colspan=3>
<html:text tabindex="3" name="BSC_DETAIL_FORM" property="description" 
  size="100" maxlength="250" value=""/>
  </td>
 </tr>
<% } else { %>
      <html:hidden property="action" value="bsc_modify"/>
 <tr>
  <td colspan="4"><b>  Update Building Services Contractor</b></td>
</tr>
<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
	<tr>
		<td colspan=2>
		<td><b>Store Id:</b></td>
		<td>
			<html:text tabindex="0" name="BSC_DETAIL_FORM" property="storeId" size="10"/><span class="reqind">*</span>
			<html:button property="action" onclick="globalPopLocate('storelocate', 'storeId', 'storeName');" value="Locate Store"/>
		</td>
		
	</tr>
</logic:equal>
<tr>
    <td colspan="2">
	<td><b>Id:</b></td>
	<td><%=bscid%></td>
</tr>

<logic:iterate id="arrele" name="list.all.bsc"
  type="com.cleanwise.service.api.value.BuildingServicesContractorView">
<% if ( arrele.getBusEntityData().getBusEntityId() ==
	Integer.parseInt(bscid) ) { %>
 <tr>
  <td>
   <b>  Name:</b>
  </td>
  <td>

<html:text tabindex="1" name="BSC_DETAIL_FORM" property="name" 
  size="30" maxlength="30" 
    value="<%=arrele.getBusEntityData().getShortDesc()%>"
/>
  </td>
<%
String faxnum = "";
if (arrele.getFaxNumber() != null ) {
  faxnum = arrele.getFaxNumber().getPhoneNum();
  if ( faxnum == null ) faxnum = "";
}
%>

  <td> <b> Order Fax Number:</b>  </td>
  <td>
<html:text tabindex="2" name="BSC_DETAIL_FORM" property="faxPhoneNumber" 
  size="30" maxlength="30"  value="<%=faxnum%>"/>
  </td>
 </tr>
<tr>
<%
String desc = arrele.getBusEntityData().getLongDesc();
if ( desc == null ) desc = "";

%>
  <td> <b> Description:</b>  </td>
  <td colspan=3>
<html:text tabindex="3" name="BSC_DETAIL_FORM" property="description" 
  size="100" maxlength="250" value="<%=desc%>"/>
  </td>
 </tr>

<% } %>
</logic:iterate>
<% } %>
<tr><td colspan=4 align=center>
      <html:submit>
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
</td></tr>

</html:form>

</table>

</div>





