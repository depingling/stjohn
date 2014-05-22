<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<div id="acctConfig">

<html:form styleId="ADMIN2_USER_ACCT_CONFIG"
           name="ADMIN2_USER_CONFIG_MGR_FORM"
           action="/admin2.0/admin2UserConfig.do"
           scope="session"
           type="com.cleanwise.view.forms.Admin2UserConfigMgrForm">

<html:hidden property="confFunction" value="<%=Admin2UserConfigMgrForm.CONF_FUNUNCTION.ACCOUNTS%>"/>

<table width="100%" class="results">
    <tr>
        <td>&nbsp;</td>
        <td>

            <html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confSearchField"/>

            <html:radio property="confSearchType" value="id" /><app:storeMessage key="admin2.search.criteria.label.id"/>
            <html:radio property="confSearchType" value="nameBegins" /><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
            <html:radio property="confSearchType" value="nameContains" /><app:storeMessage key="admin2.search.criteria.label.nameContains"/>

        </td>
        </tr>
    <tr>
        <td>&nbsp;</td>
        <td>

            <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
            </html:submit>

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <app:storeMessage key="admin2.user.config.text.showConfiguredOnly"/>:
            <html:checkbox name = 'ADMIN2_USER_CONFIG_MGR_FORM' property='conifiguredOnlyFl' value='true'/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<%--           <logic:notEqual name="<%=Constants.APP_USER%>" property="aAccountAdmin" value="true">
                <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
--%>
                  <html:submit property="action">
                        <app:storeMessage key="admin2.user.config.accounts.text.configureAllAccounts"/>
                    </html:submit>
                    <html:submit property="action">
                        <app:storeMessage key="admin2.user.config.accounts.text.configureAllSites"/>
                    </html:submit>
<%--             </logic:equal>
            </logic:notEqual>
--%>

        </td>
    </tr>
<%--     <logic:notEqual name="<%=Constants.APP_USER%>" property="aAccountAdmin" value="true">
        <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
 --%>
           <tr>
                <td>&nbsp;</td>
                <td>
                    <app:storeMessage key="admin2.user.config.accounts.text.removeSiteAssocWhenRemoveAcctAssoc"/>
                    <html:checkbox name = 'ADMIN2_USER_CONFIG_MGR_FORM' property='removeSiteAssocFl' value='true'/>
                </td>
            </tr>
<%--         </logic:equal>
    </logic:notEqual>
--%>
</table>

<logic:present name = 'ADMIN2_USER_CONFIG_MGR_FORM' property="config">

    <bean:size id="rescount"  name = 'ADMIN2_USER_CONFIG_MGR_FORM' property="config.values"/>

    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount" />

    <logic:greaterThan name="rescount" value="0">

        <table width="100%" class="admin2_table_sortable">

            <thead>
                <tr align=left>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resAcctCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.accounts.resulttable.text.accountId"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resAcctCfgBody', 1, false);"><app:storeMessage key="admin2.user.config.accounts.resulttable.text.name"/></a</th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resAcctCfgBody', 2, false);"><app:storeMessage key="admin2.user.config.accounts.resulttable.text.status"/></a></th>
                    <th class="stpTH">
                        <a href="#" onclick="f_setCheckClearArray('ADMIN2_USER_ACCT_CONFIG','config.selected',<%=rescount%>,true);"><app:storeMessage key="admin2.label.checkAll"/><br></a>
                        <a href="#" onclick="f_setCheckClearArray('ADMIN2_USER_ACCT_CONFIG','config.selected',<%=rescount%>,false);"><app:storeMessage key="admin2.label.uncheckAll"/></a>
                    </th>
                </tr>
            </thead>

            <tbody id="resAcctCfgBody">
                <logic:iterate id="arrele"
                               name = 'ADMIN2_USER_CONFIG_MGR_FORM'
                               property="config.values"
                               indexId="i">

                    <tr>
                        <td><bean:write name="arrele" property="busEntityId"/></td>
                        <td><bean:write name="arrele" property="shortDesc"/></td>
                        <td><bean:write name="arrele" property="busEntityStatusCd"/></td>
                        <%String selectedStr = "config.selected[" + i + "]"; %>
                        <td><html:multibox name="ADMIN2_USER_CONFIG_MGR_FORM" property="<%=selectedStr%>" value="true"/></td>
                    </tr>
                </logic:iterate>
            </tbody>

            <tr>
                <td colspan="3"></td>
                <td>
                    <html:submit property="action">
                        <app:storeMessage  key="admin2.button.saveUserAccounts"/>
                    </html:submit>
                </td>
            </tr>

        </table>
    </logic:greaterThan>

</logic:present>
</html:form>
</div>
