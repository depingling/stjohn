<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<div id="catalogConfig">

    <html:form styleId="ADMIN2_USER_CATALOG_CONFIG"
               name="ADMIN2_USER_CONFIG_MGR_FORM"
               action="/admin2.0/admin2UserConfig.do"
               scope="session"
               type="com.cleanwise.view.forms.Admin2UserConfigMgrForm">

        <table width="100%" class="results">
            <tr>
                <td>&nbsp;</td>
                <td>
                    <html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confSearchField"/>

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
                    <app:storeMessage key="admin2.user.config.catalogs.text.searchNotice"/>
                    :
                </td>
            </tr>
        </table>

        <logic:present name='ADMIN2_USER_CONFIG_MGR_FORM' property="config">

            <bean:size id="rescount" name='ADMIN2_USER_CONFIG_MGR_FORM' property="config.values"/>

            <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount"/>

            <logic:greaterThan name="rescount" value="0">

                <table width="100%" class="admin2_table_sortable">

                    <thead>
                        <tr align=left>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.catalogs.resulttable.text.catalogId"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 1, false);"><app:storeMessage key="admin2.user.config.catalogs.resulttable.text.catalogName"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 2, false);"><app:storeMessage key="admin2.user.config.catalogs.resulttable.text.catalogType"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resCatalogCfgBody', 3, false);"><app:storeMessage key="admin2.user.config.catalogs.resulttable.text.catalogStatus"/></a></th>
                        </tr>
                    </thead>
                    <tbody id="resCatalogCfgBody">
                        <logic:iterate id="arrele"
                                       name='ADMIN2_USER_CONFIG_MGR_FORM'
                                       property="config.values"
                                       indexId="i"
                                       type="com.cleanwise.service.api.value.CatalogData">
                            <tr>
                                <td><bean:write name="arrele" property="catalogId"/></td>
                                <td><bean:write name="arrele" property="shortDesc"/></td>
                                <td><bean:write name="arrele" property="catalogTypeCd"/></td>
                                <td><bean:write name="arrele" property="catalogStatusCd"/></td>
                            </tr>
                        </logic:iterate>
                    </tbody

                </table>
            </logic:greaterThan>
        </logic:present>
    </html:form>
</div>
