<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.Admin2Tool" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="theForm" name="ADMIN2_USER_MGR_FORM" type="com.cleanwise.view.forms.Admin2UserMgrForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
    String feedField = Utility.strNN(request.getParameter("feedField"));
    String feedDesc = Utility.strNN(request.getParameter("feedDesc"));
%>

<div class="text">

<logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
    <jsp:include flush='true' page="/admin2.0/locateAdmin2Account.jsp">
        <jsp:param name="jspFormAction" value="/admin2.0/admin2UserSearch.do"/>
        <jsp:param name="jspFormName" value="ADMIN2_USER_MGR_FORM"/>
        <jsp:param name="jspSubmitIdent" value=""/>
        <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
    </jsp:include>
</logic:equal>

<table cellspacing="0" border="0" class="mainbody" width="<%=Constants.TABLEWIDTH800%>">

    <html:form styleId="ADMIN2_USER_MGR_FORM_ID"
               name="ADMIN2_USER_MGR_FORM"
               action="/admin2.0/admin2UserSearch.do"
               scope="session"
               type="com.cleanwise.view.forms.Admin2UserMgrForm">


    <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
        <tr>
            <td align="right"><b>
                <app:storeMessage key="admin2.search.criteria.label.account(s)"/>
            </b></td>
            <td>
                <%
                    AccountDataVector accountDV = theForm.getAccountFilter();
                    if (accountDV != null && accountDV.size() > 0) {
                        for (int i = 0; i < accountDV.size(); i++) {
                            AccountData accountD = (AccountData) accountDV.get(i);
                %>
                &lt;<%=accountD.getBusEntity().getShortDesc()%>&gt;
                <%
                    }
                %>
                <html:button property="action"
                             onclick="adm2Submit('ADMIN2_SITE_MGR_FORM_ID','action','Clear Account Filter')"
                             styleClass='text'>
                    <app:storeMessage key="admin2.button.clearAccountFilter"/>
                </html:button>
                <% } %>
                <html:button property="action"
                             onclick="adm2Submit('ADMIN2_SITE_MGR_FORM_ID','action','Locate Account')"
                             styleClass='text'>
                    <app:storeMessage key="admin2.button.locateAccount"/>
                </html:button>
            </td>
        </tr>
    </logic:equal>

    <tr>
        <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.searchUsers"/>:</b>
            <input type="hidden" name="feedField" value="<%=feedField%>">
            <input type="hidden" name="feedDesc" value="<%=feedDesc%>">
        </td>
        <td>
            <html:text  name="ADMIN2_USER_MGR_FORM" property="searchField"/>
            <html:radio name="ADMIN2_USER_MGR_FORM" property="searchType" value="id" /><app:storeMessage key="admin2.search.criteria.label.id"/>
            <html:radio name="ADMIN2_USER_MGR_FORM" property="searchType" value="nameBegins" /><app:storeMessage key="admin2.search.criteria.label.nameStartsWith"/>
            <html:radio name="ADMIN2_USER_MGR_FORM" property="searchType" value="nameContains" /><app:storeMessage key="admin2.search.criteria.label.nameContains"/>
        </td>
    </tr>

    <tr>
        <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.userFirstName"/>:</b></td>
        <td><html:text name="ADMIN2_USER_MGR_FORM" property="firstName"/></td>
    </tr>

    <tr>
        <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.userLastName"/>:</b></td>
        <td><html:text name="ADMIN2_USER_MGR_FORM" property="lastName"/></td>
    </tr>

    <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
        <tr>
            <td align="right"><b><app:storeMessage key="admin2.search.criteria.label.userType"/>:</b></td>
            <td>
                <html:select name="ADMIN2_USER_MGR_FORM" property="userType">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.USER_TYPE_CODE%>">
                        <html:options collection="<%=Admin2Tool.FORM_VECTORS.USER_TYPE_CODE%>" property="value"/>
                    </logic:present>
                </html:select>
            </td>
        </tr>
    </logic:equal>

    <tr>
        <td>&nbsp;</td>
        <td>
            <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
            </html:submit>
            <html:submit property="action">
                <app:storeMessage  key="global.action.label.create"/>
            </html:submit>
            <html:submit property="action">
                <app:storeMessage  key="admin2.button.createClone"/>
            </html:submit>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <app:storeMessage key="admin2.search.criteria.label.showInactive"/>:
            <html:checkbox name="ADMIN2_USER_MGR_FORM" property="searchShowInactiveFl"/>

        </td>
    </tr>
</table>


<logic:present name="ADMIN2_USER_MGR_FORM" property="searchResult">
    <bean:size id="rescount" name="ADMIN2_USER_MGR_FORM" property="searchResult"/>
    <app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:
    <bean:write name="rescount"/>
    <% if (rescount.intValue() >= Constants.MAX_USERS_TO_RETURN) { %>
    (<app:storeMessage key="admin2.search.criteria.result.text.limit"/>)
    <% } %>


    <table width="<%=Constants.TABLEWIDTH800%>"  class="admin2_table_sortable" >
        <thead><tr align=left>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);"><b><app:storeMessage key="admin2.search.result.text.userId"/></b></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);"><b><app:storeMessage key="admin2.search.result.text.userLoginName"/></b></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);"><b><app:storeMessage key="admin2.search.result.text.userFirstName"/></b></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);"><b><app:storeMessage key="admin2.search.result.text.userLastName"/></b></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);"><b><app:storeMessage key="admin2.search.result.text.userType"/></b></a></th>
            <th class="stpTH"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);"><b><app:storeMessage key="admin2.search.result.text.userStatus"/></b></a></th>
            <th class="stpTH"><b><app:storeMessage key="global.action.label.select"/></b></th>
        </tr>
       </thead>
        <tbody id="resTblBdy">

            <logic:iterate id="arrele" name="ADMIN2_USER_MGR_FORM" property="searchResult">

                <tr>

                    <td class="stpTD"><bean:write name="arrele" property="userId"/></td>
                    <td class="stpTD">
                        <bean:define id="eleid" name="arrele" property="userId"/>
                        <bean:define id="eletype" name="arrele" property="userTypeCd"/>
                        <a href="admin2UserDetail.do?action=userdetail&id=<%=eleid%>&type=<%=eletype%>">
                            <bean:write name="arrele" property="userName"/>
                        </a>
                    </td>
                    <td class="stpTD"><bean:write name="arrele" property="firstName"/></td>
                    <td class="stpTD"><bean:write name="arrele" property="lastName"/></td>
                    <td class="stpTD"><bean:write name="arrele" property="userTypeCd"/></td>
                    <td class="stpTD"><bean:write name="arrele" property="userStatusCd"/></td>
                    <td class="stpTD"><html:radio name="ADMIN2_USER_MGR_FORM" property="selectedId" value="<%=\"\"+eleid%>"/></td>

                </tr>

            </logic:iterate>

        </tbody>
    </table>

</logic:present>

<html:hidden styleId="hiddenAction" property="action" value=""/>

</html:form>

</div>

