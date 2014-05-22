<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
<html:form name="STORE_SEARCH_FORM" action="adminportal/storemgr.do"
    scope="session" type="com.cleanwise.view.forms.StoreMgrSearchForm">
<table width="769" cellspacing="0" border="0"  class="mainbody">

  <tr> <td><b>Find Store:</b></td>
       <td colspan="3">
                        <html:text name="STORE_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="STORE_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="STORE_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage key="admin.button.viewall"/>
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

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table width="769" cellspacing="0" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="storemgr.do?action=sort&sortField=id">Store Id</td>
<td><a class="tableheader" href="storemgr.do?action=sort&sortField=name">Name </td>
<td><a class="tableheader" href="storemgr.do?action=sort&sortField=city">City</td>
<td><a class="tableheader" href="storemgr.do?action=sort&sortField=state">State/Province</td>
<td><a class="tableheader" href="storemgr.do?action=sort&sortField=type">Type</td>
</tr>

<logic:iterate id="arrele" name="Store.found.vector">
<pg:item>
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="storemgr.do?action=storedetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="storeType.value"/></td>
</tr>
</pg:item>

</logic:iterate>
</table>
  <pg:index>
    Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) {
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) {
        %><b><%= pageNumber %></b><%
      } else {
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>
  </pg:index>
</pg:pager>
</logic:greaterThan>
</logic:present>

</div>


