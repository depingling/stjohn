<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.view.forms.Admin2AccountConfigurationForm" %>
<%@ page import="com.cleanwise.view.utils.Admin2Tool" %>
<%@ page import="com.cleanwise.view.actions.Admin2UserMgrAction" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<jsp:include flush='true' page="admin2AccountCtx.jsp"/>
<html:html>

<table>
<tr>
<html:form styleId="ADMIN2_ACCOUNT_CONFIGURATION_FORM_ID" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" action="/admin2.0/admin2AccountConfig.do"  method="POST" scope="session" type="com.cleanwise.view.forms.Admin2AccountConfigMgrForm">

    <logic:equal name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.USERS%>">
        <tr>
        <td><b><app:storeMessage key="admin2.account.config.text.configure"/></b></td>
            <td>
                <html:select name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForType" onchange="adm2Submit('ADMIN2_ACCOUNT_CONFIGURATION_FORM_ID','action','changeConfigType');">
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.USERS%>"><app:storeMessage key="admin2.account.config.text.users"/></html:option>
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.CATALOGS%>"><app:storeMessage key="admin2.account.config.text.catalogs"/></html:option>
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.ORDER_GUIDES%>"><app:storeMessage key="admin2.account.config.text.orderGuides"/></html:option>
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.SITES%>"><app:storeMessage key="admin2.account.config.text.sites"/></html:option>
                </html:select>
            </td>
        </tr>
        <tr>
            <td align="right">
                <b><app:storeMessage key="admin2.account.config.users.label.searchUsers"/>:</b>
            </td>
            <td>
                <html:text name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForName"/>
                <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.ID%>"/><app:storeMessage key="admin2.search.criteria.label.id"/>
                <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.NAME_BEGINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
                <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.NAME_CONTAINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameContains"/>
            </td>
        </tr>

        <tr>
            <td align="right"><b><app:storeMessage key="admin2.account.config.users.label.firstName"/>:</b></td>
            <td><html:text name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="firstName"/> </td>
        </tr>

        <tr>
            <td align="right"><b><app:storeMessage key="admin2.account.config.users.label.lastName"/>:</b></td>
            <td><html:text name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="lastName"/> </td>
        </tr>

        <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
            <tr>
                <td align="right"><b><app:storeMessage key="admin2.account.config.users.label.userType"/>:</b></td>
                <td>
                    <html:select name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="userType" >
                        <html:option value=""><app:storeMessage  key="admin2.select"/></html:option>
                        <logic:present name="<%=Admin2Tool.FORM_VECTORS.USER_TYPE_CODE%>">
                            <html:options collection="<%=Admin2Tool.FORM_VECTORS.USER_TYPE_CODE%>" property="value"/>
                        </logic:present>
                    </html:select>
                </td>
            </tr>
        </logic:equal>
        
    </logic:equal>

<logic:equal name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.SITES%>">
<tr><td><b><app:storeMessage key="admin2.account.config.text.configure"/></b></td>
  <td>
    <html:select name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForType" onchange="adm2Submit('ADMIN2_ACCOUNT_CONFIGURATION_FORM_ID','action','changeConfigType');">
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.USERS%>"><app:storeMessage key="admin2.account.config.text.users"/></html:option>
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.CATALOGS%>"><app:storeMessage key="admin2.account.config.text.catalogs"/></html:option>
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.ORDER_GUIDES%>"><app:storeMessage key="admin2.account.config.text.orderGuides"/></html:option>
                    <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.SITES%>"><app:storeMessage key="admin2.account.config.text.sites"/></html:option>
                </html:select>
  </td>
</tr>
<tr>
  <td align="right"><b><app:storeMessage key="admin2.account.config.sites.label.findSite"/></b></td>
<td>
    <html:text name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForName"/>

    <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.ID%>"/><app:storeMessage key="admin2.search.criteria.label.id"/>
    <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.NAME_BEGINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
    <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.NAME_CONTAINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameContains"/>

</td>
</tr>

<tr>
<td align="right"><b><app:storeMessage key="admin2.account.config.sites.label.city"/></b></td>
<td><html:text name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="city"/> </td>
</tr>

<tr>
<td align="right"><b><app:storeMessage key="admin2.account.config.sites.label.state"/></b></td>
<td><html:text name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="state"/> </td>
</tr>
</logic:equal>

<logic:notEqual name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.USERS%>">
<logic:notEqual name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForType" value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.SITES%>">
<td><b><app:storeMessage key="admin2.account.config.text.configure"/></b></td>
<td>
 <html:select name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForType" onchange="adm2Submit('ADMIN2_ACCOUNT_CONFIGURATION_FORM_ID','action','changeConfigType');">
     	  <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.USERS%>"><app:storeMessage key="admin2.account.config.text.users"/></html:option>
          <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.CATALOGS%>"><app:storeMessage key="admin2.account.config.text.catalogs"/></html:option>
          <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.ORDER_GUIDES%>"><app:storeMessage key="admin2.account.config.text.orderGuides"/></html:option>
          <html:option value="<%=Admin2AccountConfigurationForm.CONF_FUNUNCTION.SITES%>"><app:storeMessage key="admin2.account.config.text.sites"/></html:option>
  </html:select>
</td>
<tr>
<td><b><app:storeMessage key="admin2.account.config.relationships.label.findRelationships"/>:</b></td>
<td>
<html:text name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchForName" size="10"/>
</td></tr>
  <tr> <td><b><app:storeMessage key="admin2.account.config.relationships.label.searchBy"/>:</b></td>
       <td colspan="3">
           <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.NAME_BEGINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
           <html:radio name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="searchType" value="<%=Constants.NAME_CONTAINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameContains"/>
       </td>
  </tr>
  </tr>
</logic:notEqual>
</logic:notEqual>


<tr>
<td></td>
<td colspan="3">
 <html:submit property="action">
         <app:storeMessage  key="global.action.label.search"/>
</html:submit>
 <app:storeMessage key="admin2.search.criteria.label.showInactive"/>
 <html:checkbox property="showInactiveFl" value="true" />
</tr>
<html:hidden styleId="hiddenAction" property="action" value=""/>
</html:form>
</table>


<logic:present name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="siteConfigs">

    <bean:size id="siteres" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="siteConfigs"/>
    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="siteres" />

    <logic:greaterThan name="siteres" value="0">

        <%boolean authorizedStoreMgrTabSite = false;%>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_SITE%>">
            <% authorizedStoreMgrTabSite = true;%>
        </app:authorizedForFunction>

        <table width="<%=Constants.TABLEWIDTH%>" class="admin2_table_sortable" >
            <thead>
                <tr align=left class=stpTH>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 0, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.siteId"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 1, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.siteName"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 2, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.streetAddress"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 3, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.city"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 4, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.state"/></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 5, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.zip"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 6, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.county"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_sites', 7, false);"><app:storeMessage key="admin2.account.config.sites.resulttable.text.status"/></a></th>
                </tr>

            </thead>
            <tbody id="tdb_sites">
                <logic:iterate id="arrele" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="siteConfigs">
                <tr class=stpTD>
                    <td class=stpTD><bean:write name="arrele" property="id"/></td>
                    <td class=stpTD>
                        <bean:define id="eleid" name="arrele" property="id"/>
                        <%if(authorizedStoreMgrTabSite){%>
                        <a href="admin2SiteDetail.do?action=sitedetail&id=<%=eleid%>">
                            <bean:write name="arrele" property="name"/>
                        </a>
                        <%}else{%>
                        <bean:write name="arrele" property="name"/>
                        <%}%>
                    </td>
                    <td class=stpTD>
                        <bean:write name="arrele" property="address"/>
                    </td>
                    <td class=stpTD>
                        <bean:write name="arrele" property="city"/>
                    </td>
                    <td class=stpTD>
                        <bean:write name="arrele" property="state"/>
                    </td>
                    <td class=stpTD><bean:write name="arrele" property="postalCode"/></td>
                    <td class=stpTD><bean:write name="arrele" property="county"/></td>

                    <td class=stpTD><bean:write name="arrele" property="status"/></td>
                </tr>
                </logic:iterate>
            <tbody>
        </table>
    </logic:greaterThan>
</logic:present>

<logic:present name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="catalogConfigs">
    <bean:size id="catres"  name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="catalogConfigs"/>
    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="catres"/>

    <logic:greaterThan name="catres" value="0">
        <table width="<%=Constants.TABLEWIDTH%>"  class="admin2_table_sortable" >
            <thead>
                <tr align=left class=stpTH>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tbd_id', 0, false);"><app:storeMessage key="admin2.account.config.catalogs.resulttable.text.catalogId"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tbd_id', 1, false);"><app:storeMessage key="admin2.account.config.catalogs.resulttable.text.catalogName"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tbd_id', 2, false);"><app:storeMessage key="admin2.account.config.catalogs.resulttable.text.catalogType"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tbd_id', 3, false);"><app:storeMessage key="admin2.account.config.catalogs.resulttable.text.catalogStatus"/></a></th>
                </tr>
            </thead>
            <tbody id="tbd_id">
                <logic:iterate id="arrele" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="catalogConfigs">
                    <tr class=stpTD>
                        <td class=stpTD><bean:write name="arrele" property="catalogId"/></td>
                        <td class=stpTD>
                            <bean:define id="eleid" name="arrele" property="catalogId"/>
                            <bean:write name="arrele" property="shortDesc"/>
                        </td>
                        <td class=stpTD><bean:write name="arrele" property="catalogTypeCd"/></td>
                        <td class=stpTD><bean:write name="arrele" property="catalogStatusCd"/></td>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
    </logic:greaterThan>
</logic:present>


<logic:present name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="userConfigs">

    <bean:size id="userres" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="userConfigs"/>
    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="userres" />

    <logic:greaterThan name="userres" value="0">

        <% boolean authorizedStoreMgrTabUser = false;%>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_USER%>">
            <% authorizedStoreMgrTabUser = true;%>
        </app:authorizedForFunction>

        <table width="<%=Constants.TABLEWIDTH%>"  class="admin2_table_sortable" >
            <thead>
                <tr class=stpTH align=left>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_user', 0, false);"><app:storeMessage key="admin2.account.config.users.resulttable.text.userId"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_user', 1, false);"><app:storeMessage key="admin2.account.config.users.resulttable.text.loginName"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_user', 2, false);"><app:storeMessage key="admin2.account.config.users.resulttable.text.firstName"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_user', 3, false);"><app:storeMessage key="admin2.account.config.users.resulttable.text.lastName"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_user', 4, false);"><app:storeMessage key="admin2.account.config.users.resulttable.text.userType"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_user', 5, false);"><app:storeMessage key="admin2.account.config.users.resulttable.text.status"/></a></th>
                </tr>
            </thead>
            <tbody id="tdb_user">
                <logic:iterate id="arrele" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="userConfigs">
                    <tr class=stpTD >
                        <td class=stpTD><bean:write name="arrele" property="userId"/></td>
                        <td class=stpTD>
                            <bean:define id="eleid" name="arrele" property="userId"/>
                            <bean:define id="eletype" name="arrele" property="userTypeCd"/>
                            <%if(authorizedStoreMgrTabUser){%>
                            <a href="admin2UserDetail.do?action=<%=Admin2UserMgrAction.DETAIL%>&id=<%=eleid%>&type=<%=eletype%>">
                                <bean:write name="arrele" property="userName"/>
                            </a>
                            <%}else{%>
                            <bean:write name="arrele" property="userName"/>
                            <%}%>
                        </td>
                        <td class=stpTD><bean:write name="arrele" property="firstName"/></td>
                        <td class=stpTD><bean:write name="arrele" property="lastName"/></td>
                        <td class=stpTD><bean:write name="arrele" property="userTypeCd"/></td>
                        <td class=stpTD><bean:write name="arrele" property="userStatusCd"/></td>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
    </logic:greaterThan>
</logic:present>

<logic:present name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="orderGuideConfigs">

    <bean:size id="ogres" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="orderGuideConfigs"/>
    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="ogres"/>

    <logic:greaterThan name="ogres" value="0">
        <table width="<%=Constants.TABLEWIDTH%>" class="admin2_table_sortable">
            <thead>
                <tr class=stpTH>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_g', 0, false);"><app:storeMessage key="admin2.account.config.orderGuides.resulttable.text.ogId"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_g', 1, false);"><app:storeMessage key="admin2.account.config.orderGuides.resulttable.text.ogName"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_g', 2, false);"><app:storeMessage key="admin2.account.config.orderGuides.resulttable.text.catalogName"/></a></th>
                    <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('tdb_g', 3, false);"><app:storeMessage key="admin2.account.config.orderGuides.resulttable.text.ogStatus"/></a></th>
                </tr>
            </thead>
            <tbody id="tdb_g">
                <logic:iterate id="ogele" name="ADMIN2_ACCOUNT_CONFIGURATION_FORM" property="orderGuideConfigs">
                    <tr class=stpTD>
                        <td class=stpTD><bean:write name="ogele" property="orderGuideId"/> </td>
                        <td class=stpTD>
                            <bean:define id="ogid" name="ogele" property="orderGuideId"/>
                            <bean:write name="ogele" property="orderGuideName"/>
                        </td>
                        <td class=stpTD><bean:write name="ogele" property="catalogName"/></td>
                        <td class=stpTD><bean:write name="ogele" property="status"/></td>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
    </logic:greaterThan>
</logic:present>

</body>
</div>
</html:html>




