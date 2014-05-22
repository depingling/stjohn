<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<div id="sitesConfig">


<html:form styleId="ADMIN2_USER_SITE_CONFIG"
           name="ADMIN2_USER_CONFIG_MGR_FORM"
           action="/admin2.0/admin2UserConfig.do"
           scope="session"
           type="com.cleanwise.view.forms.Admin2UserConfigMgrForm">


<table width="100%" class="results">
    <tr>
        <td colspan=2><app:storeMessage key="admin2.user.config.sites.text.searchNotice"/></td>
        <tr> <td align="right"><b><app:storeMessage key="admin2.user.config.sites.label.siteName"/></b></td>
            <td>
                <html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confSearchField"/>

                <html:radio property="confSearchType" value="id" /><app:storeMessage key="admin2.search.criteria.label.id"/>
                <html:radio property="confSearchType" value="nameBegins" /><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
                <html:radio property="confSearchType" value="nameContains" /><app:storeMessage key="admin2.search.criteria.label.nameContains"/>

            </td>
        </tr>

        <tr> <td align="right"><b><app:storeMessage key="admin2.user.config.sites.label.referenceNumber"/></b></td>
            <td>
                <html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="searchRefNum" />
                <html:radio name="ADMIN2_USER_CONFIG_MGR_FORM" property="searchRefNumType" value="nameBegins" /> (<app:storeMessage key="admin2.search.criteria.label.startsWith"/>)
                <html:radio name="ADMIN2_USER_CONFIG_MGR_FORM" property="searchRefNumType" value="nameContains" /> (<app:storeMessage key="admin2.search.criteria.label.contains"/>)
            </td>
        </tr>
        <tr>
            <td align="right"><b><app:storeMessage key="admin2.user.config.sites.label.city"/></b></td>
            <td><html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confCity"/> </td>
        </tr>

        <tr>
            <td align="right"><b><app:storeMessage key="admin2.user.config.sites.label.state"/></b></td>
            <td><html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confState"/> </td>
        </tr>

        <tr> <td>&nbsp;</td>
            <td>

                <html:submit property="action">
                    <app:storeMessage  key="global.action.label.search"/>
                </html:submit>

                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<app:storeMessage key="admin2.user.config.text.showConfiguredOnly"/>:
                <html:checkbox name = 'ADMIN2_USER_CONFIG_MGR_FORM' property='conifiguredOnlyFl' value='true'/>
            </td>
        </tr>

</table>

<logic:present name = 'ADMIN2_USER_CONFIG_MGR_FORM' property="config">

    <bean:size id="rescount"  name = 'ADMIN2_USER_CONFIG_MGR_FORM' property="config.values"/>

    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount" />

    <logic:greaterThan name="rescount" value="0">

        <table width="100%" class="admin2_table_sortable">
            <thead><tr align=left>
                <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resSiteCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.sites.resulttable.text.siteId"/></a></th>
                <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resSiteCfgBody', 1, false);"><app:storeMessage key="admin2.user.config.sites.resulttable.text.siteName"/></a></th>
                <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resSiteCfgBody', 2, false);"><app:storeMessage key="admin2.user.config.sites.resulttable.text.streetAddress"/></a></th>
                <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resSiteCfgBody', 3, false);"><app:storeMessage key="admin2.user.config.sites.resulttable.text.city"/></a></th>
                <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resSiteCfgBody', 4, false);"><app:storeMessage key="admin2.user.config.sites.resulttable.text.state"/></a></th>
                <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resSiteCfgBody', 5, false);"><app:storeMessage key="admin2.user.config.sites.resulttable.text.zip"/></a></th>
                <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resSiteCfgBody', 6, false);"><app:storeMessage key="admin2.user.config.sites.resulttable.text.status"/></a></th>
                <td class="stpTH">
                    <a href="#" onclick="f_setCheckClearArray('ADMIN2_USER_SITE_CONFIG','config.selected',<%=rescount%>,true);"><app:storeMessage key="admin2.label.checkAll"/><br></a>
                    <a href="#" onclick="f_setCheckClearArray('ADMIN2_USER_SITE_CONFIG','config.selected',<%=rescount%>,false);"><app:storeMessage key="admin2.label.uncheckAll"/></a>
                </td>
            </tr>
            </thead>
            <tbody id="resSiteCfgBody">
                <logic:iterate id="arrele"
                               name = 'ADMIN2_USER_CONFIG_MGR_FORM'
                               property="config.values"
                               indexId="i"
                               type="com.cleanwise.service.api.value.SiteView">

                    <tr>
                        <td><bean:write name="arrele" property="id"/></td>
                        <td><%String eleid = String.valueOf(arrele.getId());%>
                            <% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADMIN2_MGR_TAB_SITE)) {%>
                            <a href="admin2SiteDetail.do?action=sitedetail&id=<%=eleid%>">
                                <bean:write name="arrele" property="name"/>
                            </a>
                            <% } else { %>
                            <bean:write name="arrele" property="name"/>
                            <% } %>
                        </td>
                        <td><bean:write name="arrele" property="address"/> </td>
                        <td><bean:write name="arrele" property="city"/></td>
                        <td><bean:write name="arrele" property="state"/></td>
                        <td><bean:write name="arrele" property="postalCode"/></td>
                        <td><bean:write name="arrele" property="status"/></td>
                        <%String selectedStr = "config.selected[" + i + "]"; %>
                        <td><html:multibox name="ADMIN2_USER_CONFIG_MGR_FORM" property="<%=selectedStr%>" value="true"/></td>

                    </tr>
                </logic:iterate>
            </tbody>
            <tr>
                <td colspan="6"></td>

                <td colspan="2">
                    <html:submit property="action">
                        <app:storeMessage  key="admin2.button.saveUserSites"/>
                    </html:submit>
                </td>
            </tr>

        </table>

    </logic:greaterThan>

</logic:present>

</html:form>

</div>
