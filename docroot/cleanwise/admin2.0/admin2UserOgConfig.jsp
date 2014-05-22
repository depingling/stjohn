<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
'
<div id="distConfig">

    <html:form styleId="ADMIN2_USER_OG_CONFIG"
               name="ADMIN2_USER_CONFIG_MGR_FORM"
               action="/admin2.0/admin2UserConfig.do"
               scope="session"
               type="com.cleanwise.view.forms.Admin2UserConfigMgrForm">

        <table width="100%" class="results">
            <tr>
                <td>&nbsp;</td>
                <td>
                    <html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confSearchField"/>

                    <html:radio property="confSearchType" value="id"/> <app:storeMessage key="admin2.search.criteria.label.id"/>
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
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<app:storeMessage key="admin2.user.config.og.text.searchNotice"/>:
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
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resOgCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.og.resulttable.text.orderGuideId"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resOgCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.og.resulttable.text.orderGuideName"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resOgCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.og.resulttable.text.orderGuideType"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resOgCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.og.resulttable.text.catalogId"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resOgCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.og.resulttable.text.catalogName"/></a></th>
                            <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resOgCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.og.resulttable.text.catalogStatus"/></a></th>
                        </tr>
                    </thead>
                    <tbody id="resOgCfgBody">
                        <logic:iterate id="arrele"
                                       name='ADMIN2_USER_CONFIG_MGR_FORM'
                                       property="config.values"
                                       indexId="i">
                            <tr>
                                <td><bean:write name="arrele" property="orderGuideId"/></td>
                                <td><bean:write name="arrele" property="orderGuideName"/></td>
                                <td><bean:write name="arrele" property="orderGuideTypeCd"/></td>
                                <td><bean:write name="arrele" property="catalogId"/></td>
                                <td><bean:write name="arrele" property="catalogName"/></td>
                                <td><bean:write name="arrele" property="status"/></td>
                            </tr>
                        </logic:iterate>
                    </tbody>
                </table>
            </logic:greaterThan>
        </logic:present>

    </html:form>
</div>