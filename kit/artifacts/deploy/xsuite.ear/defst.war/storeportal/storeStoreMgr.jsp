<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<html:html>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
<html:form styleId="1311" name="STORE_STORE_SEARCH_FORM" action="storeportal/storeStoreMgr.do"
    scope="session" type="com.cleanwise.view.forms.SPStoreMgrAction">
<table ID=1312 width="769" cellspacing="0" border="0"  class="mainbody">

  <tr> <td><b>Find Store:</b></td>
       <td colspan="3">
                        <html:text name="STORE_STORE_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="STORE_STORE_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="STORE_STORE_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_STORE_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
			<html:submit property="action">
					<app:storeMessage  key="admin.button.create"/>
			</html:submit>
		</logic:equal>

    </td>
  </tr>
</table>
</html:form>
<logic:present name="Store.found.vector">
<bean:size id="rescount"  name="Store.found.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">



<table class="stpTable_sortable" id="ts1">
<thead>
<tr class=stpTH>
<th class="stpTH">Store Id</th>
<th class="stpTH">Name </th>
<th class="stpTH">City</th>
<th class="stpTH">State/Province</th>
<th class="stpTH">Type</th>
</tr>
</thead>
<tbody>
<logic:iterate id="arrele" name="Store.found.vector">
<tr>
<td class=stpTD><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td class=stpTD>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a ID=1313 href="storeStoreMgr.do?action=storedetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td class=stpTD><bean:write name="arrele" property="primaryAddress.city"/></td>
<td class=stpTD><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td class=stpTD><bean:write name="arrele" property="storeType.value"/></td>
</tr>
</logic:iterate>
</tbody>
</table>

</logic:greaterThan>
</logic:present>

</div>

 </html:html>
