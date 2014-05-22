<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.AccountData" %>
<%@ page import="com.cleanwise.service.api.value.AccountDataVector" %>
<%@ page import="com.cleanwise.service.api.value.SiteView" %>
<%@ page import="com.cleanwise.service.api.value.SiteViewVector" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:define id="theForm" name="ADMIN2_SITE_MGR_FORM" type="com.cleanwise.view.forms.Admin2SiteMgrForm"/>

<logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
    <jsp:include flush='true' page="/admin2.0/locateAdmin2Account.jsp">
        <jsp:param name="jspFormAction" 	value="/admin2.0/admin2SiteSearch.do" />
        <jsp:param name="jspFormName" 	value="ADMIN2_SITE_MGR_FORM" />
        <jsp:param name="jspSubmitIdent" 	value="" />
        <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
    </jsp:include>
</logic:equal>

<table>
<tr>
<td>
<html:form styleId="ADMIN2_SITE_MGR_FORM_ID" name="ADMIN2_SITE_MGR_FORM" action="admin2.0/admin2SiteSearch.do">
    <table width="<%=Constants.TABLEWIDTH800%>" class="mainbody">
        <tr><td align="right"><b><app:storeMessage key="admin2.search.criteria.label.siteName"/></b></td>
            <td colspan="4">
                <html:text  property="searchField"/>
                <html:radio property="searchType" value="id" /><app:storeMessage key="admin2.search.criteria.label.id"/>
                <html:radio property="searchType" value="nameBegins" /><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
                <html:radio property="searchType" value="nameContains" /><app:storeMessage key="admin2.search.criteria.label.nameContains"/>
            </td>
            <td>
                <html:button property="action"
                                 onclick="f_reset_fields(document.ADMIN2_SITE_MGR_FORM)"
                                 styleClass='text'>
                        <app:storeMessage key="admin2.site.sitesearch.button.resetSearchFields"/>
                    </html:button>
            </td>
        </tr>

        <tr> <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.referenceNumber"/></b></td>
            <td colspan="4">
                <html:text  property="searchRefNum"/>
                <html:radio property="searchRefNumType" value="nameBegins" /> (<app:storeMessage key="admin2.search.criteria.label.startsWith"/>)
                <html:radio property="searchRefNumType" value="nameContains" /> (<app:storeMessage key="admin2.search.criteria.label.contains"/>)
            </td>

            <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
            <tr>
                <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.account(s)"/></b></td>
                <td colspan='3'>
                    <%
                        AccountDataVector accountDV = theForm.getAccountFilter();
                        if (accountDV != null && accountDV.size() > 0) {
                            for (Object anAccountDV : accountDV) {
                                AccountData accountD = (AccountData) anAccountDV;
                    %>
                    &lt;<%=accountD.getBusEntity().getShortDesc()%>&gt;
                    <% } %>
                    <html:button property="action"
                                 onclick="adm2Submit('ADMIN2_SITE_MGR_FORM_ID','action','Clear Account Filter')"
                                 styleClass='text'>
                        <app:storeMessage key="admin2.button.clearAccountFilter"/>                    </html:button>
                    <% } %>
                     <html:button property="action"
                                 onclick="adm2Submit('ADMIN2_SITE_MGR_FORM_ID','action','Locate Account')"
                                 styleClass='text'>
                        <app:storeMessage key="admin2.button.locateAccount"/>
                    </html:button>
                </td></tr>
            </logic:equal>


            <tr>
                <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.city"/></b></td>
                <td><html:text property="searchCity"/>
                </td>
                <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.county"/></b></td>
                <td><html:text property="searchCounty"/></td>
            </tr>
            <tr>
                <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.state"/></b></td>
                <td><html:text property="searchState"/> </td>
                <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.zip"/></b></td>
                <td><html:text property="searchPostalCode"/></td>
            </tr>

            <tr> <td> &nbsp;</td>
                <td colspan="3">
                    <html:submit property="action">
                        <app:storeMessage  key="global.action.label.search"/>
                    </html:submit>
                    <html:button onclick="go('../admin2.0/admin2SiteDetail.do?action=create')" property="action">
                        <app:storeMessage  key="admin2.button.createNew"/>
                    </html:button>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <app:storeMessage key="admin2.search.criteria.label.showInactive"/> <html:checkbox property="showInactiveFl" value="true" />

                </td>
            </tr>
    </table>
    <html:hidden styleId="hiddenAction" property="action"/>
</html:form>


<%
    SiteViewVector sites = (SiteViewVector) theForm.getSiteSearchResult();
    if(sites!=null){
%>
Count:<%=sites.size()%>
<table width="<%=Constants.TABLEWIDTH800%>" class="admin2_table_sortable">
    <thead>
        <tr>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 0, false);"><app:storeMessage key="admin2.search.result.text.siteId"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 1, false);"><app:storeMessage key="admin2.search.result.text.rank"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 2, false);"><app:storeMessage key="admin2.search.result.text.accountName"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 3, false);"><app:storeMessage key="admin2.search.result.text.siteName"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 4, false);"><app:storeMessage key="admin2.search.result.text.streetAddress"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 5, false);"><app:storeMessage key="admin2.search.result.text.city"/></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 6, false);"><app:storeMessage key="admin2.search.result.text.state"/><br><app:storeMessage key="admin2.search.result.text.prov"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 7, false);"><app:storeMessage key="admin2.search.result.text.zip"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 8, false);"><app:storeMessage key="admin2.search.result.text.county"/></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 9, false);"><app:storeMessage key="admin2.search.result.text.status"/></a></th>
        </tr>
    </thead>
    <tbody id="itemTblBdy">
        <% if(sites.size()==Constants.MAX_SITES_TO_RETURN){ %> (request limit)<% } %>
        <% for (Object oSite : sites) {
            SiteView siteVw = (SiteView) oSite;
            int siteId = siteVw.getId();
            String hrefDet = "admin2SiteDetail.do?action=sitedetail&id=" + siteId;
            int targetFacilityRank = siteVw.getTargetFacilityRank();
        %>
        <tr>
            <td class="stpTD"><%=siteId%></td>
            <td class="stpTD"><%=targetFacilityRank%></td>
            <td class="stpTD"><%=siteVw.getAccountName()%></td>
            <td class="stpTD"><a href="<%=hrefDet%>"><%=siteVw.getName()%></a></td>
            <td class="stpTD"><%=siteVw.getAddress()%></td>
            <td class="stpTD"><%=siteVw.getCity()%></td>
            <td class="stpTD"><%=siteVw.getState()%></td>
            <td class="stpTD"><%=siteVw.getPostalCode()%></td>
            <td class="stpTD"><%if (siteVw.getCounty() != null) {%><%=siteVw.getCounty()%><%}%></td>
            <td class="stpTD"><%=siteVw.getStatus()%></td>
        </tr>
        <% } %>
    </tbody>
</table>
<%}%>

</td>
</tr>
</table>
<script type="text/javascript" language="JavaScript">
    <!--
    var focusControl = document.forms[0]['searchField'];
    if('undefined' != typeof focusControl) {
        focusControl.focus();
    }

    function kH(e) {
        var keyCode = window.event.keyCode;
        if(keyCode==13) {
            var actionButton = document.forms[0]['action'];
            if('undefined' != typeof actionButton) {
                var len = actionButton.length;
                for(ii=0; ii<len; ii++) {
                    if('Search' == actionButton[ii].value) {
                        actionButton[ii].select();
                        actionButton[ii].click();
                        break;
                    }
                }
            }
        }
    }
    document.onkeypress = kH;
    // -->

    function go(url){
        window.location.href=url;
    }

</script>

