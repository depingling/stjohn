<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.actions.Admin2UserMgrAction" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<div id="catalogConfig">


<html:form styleId="ADMIN2_SITE_CATALOG_CONFIG"
           name="ADMIN2_SITE_CONFIG_MGR_FORM"
           action="/admin2.0/admin2SiteConfig.do"
           scope="session"
           type="com.cleanwise.view.forms.Admin2SiteConfigMgrForm">


<table width="100%" class="results">

    <tr align="left">
        <td align="right" width="130px"><b><app:storeMessage key="admin2.site.config.catalog.label.catalogName"/></b></td>

        <td>

            <html:text name="ADMIN2_SITE_CONFIG_MGR_FORM" property="confSearchField"/>

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

    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount"/>

    <logic:greaterThan name="rescount" value="0">

        <table width="100%" class="admin2_table_sortable">
            <thead width="100%">
                <tr align=left>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 0, false);"><app:storeMessage key="admin2.site.config.catalog.resulttable.text.catalogId"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 1, false);"><app:storeMessage key="admin2.site.config.catalog.resulttable.text.catalogName"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 2, false);"><app:storeMessage key="admin2.site.config.catalog.resulttable.text.catalogStatus"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 3, false);"><app:storeMessage key="admin2.site.config.catalog.resulttable.text.catalogType"/></a></th>
                    <th class="stpTH"><app:storeMessage key="global.action.label.select"/></a></th>
                </tr>
            </thead>

            <tbody id="resCatalogCfgBody">

                <logic:iterate id="arrele"
                               name='ADMIN2_SITE_CONFIG_MGR_FORM'
                               property="config.values"
                               indexId="i"
                               type="com.cleanwise.service.api.value.CatalogData">
                    <tr>
                        <td><bean:write name="arrele" property="catalogId"/></td>
                        <td><bean:write name="arrele" property="shortDesc"/></td>
                        <td><bean:write name="arrele" property="catalogStatusCd"/></td>
                        <td><bean:write name="arrele" property="catalogTypeCd"/></td>
                        <%String selectedStr = "config.selected[" + i + "]"; %>
                        <td><html:radio name="ADMIN2_SITE_CONFIG_MGR_FORM" property="catalogId" value="<%=String.valueOf(arrele.getCatalogId())%>"/></td>
                    </tr>
                </logic:iterate>
            </tbody>

            <tr>
                <td colspan="6"></td>
                <td colspan="2">
                    <html:submit property="action">
                        <app:storeMessage  key="admin2.button.saveSiteCatalog"/>
                    </html:submit>
                </td>
            </tr>

        </table>

    </logic:greaterThan>

</logic:present>

</html:form>

</div>
