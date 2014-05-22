<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

<table ID=1234 width="769" cellspacing="0" border="0" class="mainbody">
  <html:form styleId="1235" name="ADMIN2_SITE_ORDER_GUIDE_FORM" action="admin2.0/admin2OrderGuideSearch.do"
    scope="session" type="com.cleanwise.view.forms.Admin2SiteOrderGuideDetForm">
  <tr>
    <td><b>Site Id:</b> <bean:write name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="siteId"/></td>
  </tr>

  <tr>
  <td>
    <html:submit property="action">
      <app:storeMessage  key="admin.button.create"/>
    </html:submit>
  </td>
  </tr>
  </html:form>
</table>

<logic:present name="Site.OrderGuides.found.vector">
<bean:size id="ogres"  name="Site.OrderGuides.found.vector"/>
Search result count:  <bean:write name="ogres" />

<table ID=1236 width="769" cellspacing="0" border="0" class="results">
<tr>
<td><a ID=1237 class="tableheader" href="admin2OrderGuideSearch.do?action=sort&sortField=id"><app:storeMessage key="admin2.site.orderguide.label.orderGuideId"/></td>
<td><a ID=1238 class="tableheader" href="admin2OrderGuideSearch.do?action=sort&sortField=name"><app:storeMessage key="admin2.site.orderguide.label.orderGuideName"/></td>
<td><a ID=1239 class="tableheader" href="admin2OrderGuideSearch.do?action=sort&sortField=catalog"><app:storeMessage key="admin2.site.orderguide.label.orderGuideCatalogName"/></td>
<td><a ID=1240 class="tableheader" href="admin2OrderGuideSearch.do?action=sort&sortField=status"><app:storeMessage key="admin2.site.orderguide.label.orderGuideCatalogStatus"/></td>
<td><a ID=1241 class="tableheader" ><app:storeMessage key="admin2.site.orderguide.label.orderGuideType"/></td>
</tr>
<logic:iterate id="ogele" name="Site.OrderGuides.found.vector">

<tr>
<td><bean:write name="ogele" property="orderGuideId"/></td>
<td>
<bean:define id="ogid" name="ogele" property="orderGuideId"/>
<a ID=1242 href="admin2OrderGuideDet.do?action=detail&searchType=id&searchField=<%=ogid%>">
<bean:write name="ogele" property="orderGuideName"/>
</a>
</td>
<td><bean:write name="ogele" property="catalogName"/></td>
<td><bean:write name="ogele" property="status"/></td>
<td><bean:write name="ogele" property="orderGuideTypeCd"/></td>
</tr>

</logic:iterate>
</table>

</logic:present>

</div>





