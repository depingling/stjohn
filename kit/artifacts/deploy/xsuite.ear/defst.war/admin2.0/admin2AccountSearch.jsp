<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<SCRIPT TYPE="text/javascript">
    <!--
    function submitenter(myfield,e)
    {
        var keycode;
        if (window.event) keycode = window.event.keyCode;
        else if (e) keycode = e.which;
        else return true;

        if (keycode == 13) {
            var s=document.forms[0].elements['search'];
            if(s!=null) s.click();
            return false;
        } else{
            return true;
        }
    }

    //-->
</SCRIPT>

<bean:define id="theForm" name="ADMIN2_ACCOUNT_MGR_FORM" type="com.cleanwise.view.forms.Admin2AccountMgrForm"/>
<html:form  name="ADMIN2_ACCOUNT_MGR_FORM" action="/admin2.0/admin2AccountSearch"  scope="session">
    <table width="<%=Constants.TABLEWIDTH%>" border="0"  class="mainbody">

        <tr><td align="right"><b><app:storeMessage key="admin2.search.criteria.account.label.findAccound"/></b></td>
            <td>
                <html:text styleId="mainSearchField" property="searchField" onkeypress="return submitenter(this,event)"/>
                <html:radio property="searchType" value="<%=Constants.ID.toLowerCase()%>" /><app:storeMessage key="admin2.search.criteria.label.id"/>
                <html:radio property="searchType" value="<%=Constants.NAME_BEGINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
                <html:radio property="searchType" value="<%=Constants.NAME_CONTAINS%>"/><app:storeMessage key="admin2.search.criteria.label.nameContains"/>
            </td>
        </tr>

        <%
            SessionTool st = new SessionTool(request);
            CleanwiseUser appUser = st.getUserData();
            GroupDataVector accountGroups =  theForm.getAccountGroups();
            if(accountGroups!=null && accountGroups.size()>0&& (appUser != null)) {%>


        <tr><td align=right><b><app:storeMessage key="admin2.search.criteria.label.groups"/></b></td><td>

            <html:radio property="searchGroupId" value='0'><app:storeMessage key="admin2.search.criteria.label.none"/></html:radio><br>

            <% for (Iterator iter=accountGroups.iterator(); iter.hasNext();) {
                GroupData gD = (GroupData) iter.next();
                String grIdS = ""+gD.getGroupId();
                String grName = gD.getShortDesc();
            %>
            <html:radio property="searchGroupId" value='<%=grIdS%>'><%=grName%>&nbsp;
            </html:radio><br>
            <% } %>
        </td>
            <tr>
                <% }  /* end of groups */ %>
                <td></td> <td colspan="3">
                <html:submit styleId="search"  property="action">
                    <app:storeMessage  key="global.action.label.search"/>
                </html:submit>
                <html:submit property="action">
                    <app:storeMessage  key="global.action.label.create"/>
                </html:submit>
                <app:storeMessage key="admin2.search.criteria.label.showInactive"/><html:checkbox property="showInactiveFl" value="true" />
            </td>
            </tr>
    </table>
</html:form>



<script type="text/javascript" language="JavaScript">
    <!--
    var focusControl = document.getElementById("mainSearchField");
    if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled) {
        focusControl.focus();
    }
    // -->
</script>

<logic:present name="ADMIN2_ACCOUNT_MGR_FORM" property="searchResult">

    <bean:size id="rescount"   name="ADMIN2_ACCOUNT_MGR_FORM" property="searchResult"/>
    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount" />

    <% if ( rescount.intValue() >= Constants.MAX_ACCOUNTS_TO_RETURN ){ %>
    (<app:storeMessage key="admin2.search.criteria.result.text.limit"/>)
    <%}%>

    <logic:greaterThan name="rescount" value="0">

        <table class="stpTable_sortable" id="ts1">
            <thead>
                <tr class=stpTH>
                    <th class="stpTH"><app:storeMessage key="admin2.search.result.text.accountId"/></th>
                    <th class="stpTH"><app:storeMessage key="admin2.search.result.text.erpNum"/></th>
                    <th class="stpTH"><app:storeMessage key="admin2.search.result.text.accountName"/></th>
                    <th class="stpTH"><app:storeMessage key="admin2.search.result.text.city"/></th>
                    <th class="stpTH"><app:storeMessage key="admin2.search.result.text.state"/></th>
                    <th class="stpTH"><app:storeMessage key="admin2.search.result.text.accountType"/></th>
                    <th class="stpTH"><app:storeMessage key="admin2.search.result.text.accountStatus"/></th>
                </tr>
            </thead>

            <tbody>
                <logic:iterate id="arrele" name="ADMIN2_ACCOUNT_MGR_FORM" property="searchResult" type="com.cleanwise.service.api.value.AccountSearchResultView">

                    <tr class=stpTD>
                        <td class=stpTD><bean:write name="arrele" property="accountId"/></td>
                        <td class=stpTD><bean:write name="arrele" property="erpNum"/></td>
                        <td class=stpTD>

                            <bean:define id="eleid" name="arrele" property="accountId"/>
                            <a href="admin2AccountDetail.do?action=accountdetail&id=<%=eleid%>">
                                <bean:write name="arrele" property="shortDesc"/>
                            </a>
                        </td>
                        <td class=stpTD><bean:write name="arrele" property="city"/></td>
                        <td class=stpTD><bean:write name="arrele" property="stateProvinceCd"/></td>
                        <td class=stpTD><bean:write name="arrele" property="value"/></td>
                        <td class=stpTD><bean:write name="arrele" property="busEntityStatusCd"/></td>
                    </tr>

                </logic:iterate>
            </tbody>
        </table>

    </logic:greaterThan>
</logic:present>

</div>

<br><br>

