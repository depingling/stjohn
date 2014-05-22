<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<div id="distConfig">

    <html:form styleId="ADMIN2_USER_DIST_CONFIG"
               name="ADMIN2_USER_CONFIG_MGR_FORM"
               action="/admin2.0/admin2UserConfig.do"
               scope="session"
               type="com.cleanwise.view.forms.Admin2UserConfigMgrForm">

        <table width="100%" class="results">
            <tr>
                <td>&nbsp;</td>
                <td>

                    <html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confSearchField"/>

                    <html:radio property="confSearchType" value="id"/>
                    <app:storeMessage key="admin2.search.criteria.label.id"/>
                    <html:radio property="confSearchType" value="nameBegins"/>
                    <app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
                    <html:radio property="confSearchType" value="nameContains"/>
                    <app:storeMessage key="admin2.search.criteria.label.nameContains"/>

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
                    <html:checkbox name='ADMIN2_USER_CONFIG_MGR_FORM' property='conifiguredOnlyFl' value='true'/>
            </tr>
        </table>

        <logic:present name='ADMIN2_USER_CONFIG_MGR_FORM' property="config">

            <bean:size id="rescount" name='ADMIN2_USER_CONFIG_MGR_FORM' property="config.values"/>

            <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount"/>

            <logic:greaterThan name="rescount" value="0">

                <table width="100%" class="admin2_table_sortable">

                    <thead>

                        <tr align=left>

                        <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistrCfgBody', 0, false);"><app:storeMessage key="admin2.user.config.distr.resulttable.text.distrId"/></a></th>
                        <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistrCfgBody', 1, false);"><app:storeMessage key="admin2.user.config.distr.resulttable.text.erpNum"/></a></th>
                        <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistrCfgBody', 2, false);"><app:storeMessage key="admin2.user.config.distr.resulttable.text.name"/></a></th>
                        <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistrCfgBody', 3, false);"><app:storeMessage key="admin2.user.config.distr.resulttable.text.address1"/></a></th>
                        <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistrCfgBody', 4, false);"><app:storeMessage key="admin2.user.config.distr.resulttable.text.city"/></a></th>
                        <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistrCfgBody', 5, false);"><app:storeMessage key="admin2.user.config.distr.resulttable.text.state"/></a></th>
                        <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistrCfgBody', 6, false);"><app:storeMessage key="admin2.user.config.distr.resulttable.text.status"/></a></th>
                        <th class="stpTH"><a class="tableheader" href="#pgsort"><app:storeMessage key="admin2.user.config.distr.resulttable.text.select"/></a></th>

                        </tr>

                    </thead>

                    <tbody id="resDistrCfgBody">

                    <logic:iterate id="arrele"
                                   name='ADMIN2_USER_CONFIG_MGR_FORM'
                                   property="config.values"
                                   indexId="i">
                      <tr>

                            <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
                            <td><bean:write name="arrele" property="busEntity.erpNum"/></td>
                            <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
                            <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
                            <td><bean:write name="arrele" property="primaryAddress.city"/></td>
                            <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
                            <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
                            <%String selectedStr = "config.selected[" + i + "]"; %>
                            <td><html:multibox name="ADMIN2_USER_CONFIG_MGR_FORM" property="<%=selectedStr%>" value="true"/></td>


                        </tr>

                    </logic:iterate>

                    </tbody>

                    <td colspan="6"></td>

                    <td>
                        <html:submit property="action">
                            <app:storeMessage  key="admin2.button.saveUserDistr"/>
                        </html:submit>
                    </td>

                </table>

            </logic:greaterThan>

        </logic:present>
    </html:form>

</div>