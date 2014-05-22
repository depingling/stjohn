<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.actions.Admin2UserMgrAction" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<div id="distSchConfig">


<html:form styleId="ADMIN2_SITE_DIST_SCH_CONFIG"
           name="ADMIN2_SITE_CONFIG_MGR_FORM"
           action="/admin2.0/admin2SiteConfig.do"
           scope="session"
           type="com.cleanwise.view.forms.Admin2SiteConfigMgrForm">


<table width="100%" class="results">

    <tr align="left">
        <td  align="right" width="130px"><b><app:storeMessage key="admin2.site.config.distSch.label.distSchName"/></b></td>

        <td align="left">

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

        </td>
    </tr>

</table>

<logic:present name='ADMIN2_SITE_CONFIG_MGR_FORM' property="config">

    <bean:size id="rescount" name='ADMIN2_SITE_CONFIG_MGR_FORM' property="config.values"/>

    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount"/>

    <logic:greaterThan name="rescount" value="0">

        <table width="100%" class="admin2_table_sortable">
            <thead>
                <tr align=left>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 0, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.distSchId"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 1, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.distName"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 2, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.distErp"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 3, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.schName"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 4, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.distSchStatus"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 4, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.distSchInfo"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 4, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.distSchCutoff"/></a></th>
                    <th class="stpTH"><a class="tableheader" href="#pgsort" onclick="this.blur(); return f_sortTable('resDistSchCfgBody', 4, false);"><app:storeMessage key="admin2.site.config.distSch.resulttable.text.distSchNextDelivery"/></a></th>
                </tr>
            </thead>

            <tbody id="resDistSchCfgBody">

                <logic:iterate id="arrele"
                               name='ADMIN2_SITE_CONFIG_MGR_FORM'
                               property="config.values"
                               indexId="i"
                               type="com.cleanwise.service.api.value.DeliveryScheduleView">
                    <tr>
                        <bean:define id="eleid" name="arrele" property="scheduleId"/>

                        <td><bean:write name="arrele" property="scheduleId"/></td>
                        <td><bean:write name="arrele" property="busEntityShortDesc"/></td>
                        <td><bean:write name="arrele" property="busEntityErpNum"/></td>
                        <td><bean:write name="arrele" property="scheduleName"/></td>
                        <td><bean:write name="arrele" property="scheduleStatus"/></td>
                        <td><bean:write name="arrele" property="scheduleInfo"/></td>
                        <td><bean:write name="arrele" property="cutoffInfo"/></td>

                        <% String nextDel; %>

                        <%  if (arrele.getNextDelivery() == null) {
                            nextDel = ClwI18nUtil.getMessage(request,"admin2.site.config.distSch.text.error",null);
                        } else {
                            nextDel = ClwI18nUtil.formatDateInp(request,arrele.getNextDelivery());
                        } %>

                        <td><%=nextDel%></td>
                    </tr>
                </logic:iterate>
            </tbody>

        </table>

    </logic:greaterThan>

</logic:present>

</html:form>

</div>
