<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.actions.Admin2UserMgrAction" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<div id="usersConfig">


<html:form styleId="ADMIN2_SITE_USER_CONFIG"
           name="ADMIN2_SITE_CONFIG_MGR_FORM"
           action="/admin2.0/admin2SiteConfig.do"
           scope="session"
           type="com.cleanwise.view.forms.Admin2SiteConfigMgrForm">


<table width="100%" class="results">

    <tr align="left">
        <td  align="right" width="130px"><b><app:storeMessage key="admin2.site.config.users.label.userName"/></b></td>

        <td align="left">

            <html:text name="ADMIN2_SITE_CONFIG_MGR_FORM" property="confSearchField"/>

            <html:radio property="confSearchType" value="id"/><app:storeMessage key="admin2.search.criteria.label.id"/>
            <html:radio property="confSearchType" value="nameBegins"/><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
            <html:radio property="confSearchType" value="nameContains"/><app:storeMessage key="admin2.search.criteria.label.nameContains"/>

        </td>
    </tr>

    <tr>
        <td>&nbsp;</td>
        <td>

            <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
            </html:submit>

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <app:storeMessage key="admin2.site.config.text.showConfiguredOnly"/>:<html:checkbox name='ADMIN2_SITE_CONFIG_MGR_FORM' property='conifiguredOnlyFl' value='true'/>

        </td>
    </tr>

</table>

<logic:present name='ADMIN2_SITE_CONFIG_MGR_FORM' property="config">

    <bean:size id="rescount" name='ADMIN2_SITE_CONFIG_MGR_FORM' property="config.values"/>

    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>
    :
    <bean:write name="rescount"/>

    <logic:greaterThan name="rescount" value="0">

        <table width="100%" class="admin2_table_sortable">
            <thead>
                <tr align=left>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resUserCfgBody', 0, false);"><app:storeMessage key="admin2.site.config.users.resulttable.text.userId"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resUserCfgBody', 1, false);"><app:storeMessage key="admin2.site.config.users.resulttable.text.loginName"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resUserCfgBody', 2, false);"><app:storeMessage key="admin2.site.config.users.resulttable.text.firstName"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resUserCfgBody', 3, false);"><app:storeMessage key="admin2.site.config.users.resulttable.text.lastName"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resUserCfgBody', 4, false);"><app:storeMessage key="admin2.site.config.users.resulttable.text.userType"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resUserCfgBody', 5, false);"><app:storeMessage key="admin2.site.config.users.resulttable.text.status"/></a></th>
                    <th class="stpTH">
                        <a href="#"onclick="f_setCheckClearArray('ADMIN2_SITE_USER_CONFIG','config.selected',<%=rescount%>,true);"> <app:storeMessage key="admin2.label.checkAll"/><br></a>
                        <a href="#"   onclick="f_setCheckClearArray('ADMIN2_SITE_USER_CONFIG','config.selected',<%=rescount%>,false);"><app:storeMessage key="admin2.label.uncheckAll"/></a>
                    </th>
                </tr>
            </thead>

            <tbody id="resUserCfgBody">

                <logic:iterate id="arrele"
                               name='ADMIN2_SITE_CONFIG_MGR_FORM'
                               property="config.values"
                               indexId="i"
                               type="com.cleanwise.service.api.value.UserData">
                    <tr>
                        <td><bean:write name="arrele" property="userId"/></td>
                        <td>
                            <% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_USER)) {%>
                            <bean:define id="eleid" name="arrele" property="userId"/>
                            <bean:define id="eletype" name="arrele" property="userTypeCd"/>
                            <a href="admin2UserDetail.do?action=<%=Admin2UserMgrAction.DETAIL%>&id=<%=eleid%>&type=<%=eletype%>">
                                <bean:write name="arrele" property="userName"/>
                            </a>
                            <% } else {%>
                            <bean:write name="arrele" property="userName"/>
                            <% } %>
                        </td>
                        <td><bean:write name="arrele" property="firstName"/></td>
                        <td><bean:write name="arrele" property="lastName"/></td>
                        <td><bean:write name="arrele" property="userTypeCd"/></td>
                        <td><bean:write name="arrele" property="userStatusCd"/></td>
                        <%String selectedStr = "config.selected[" + i + "]"; %>
                        <td><html:multibox name="ADMIN2_SITE_CONFIG_MGR_FORM" property="<%=selectedStr%>" value="true"/></td>
                    </tr>

                </logic:iterate>
            </tbody>

            <tr>
                <td colspan="6"></td>
                <td colspan="2">
                    <html:submit property="action">
                        <app:storeMessage  key="admin2.button.saveSiteUsers"/>
                    </html:submit>
                </td>
            </tr>

        </table>

    </logic:greaterThan>

</logic:present>

</html:form>

</div>
