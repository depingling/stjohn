<%@ page import="com.cleanwise.service.api.dao.UniversalDAO" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<div id="groupConfig">

    <html:form styleId="ADMIN2_USER_OG_CONFIG"
               name="ADMIN2_USER_CONFIG_MGR_FORM"
               action="/admin2.0/admin2UserConfig.do"
               scope="session"
               type="com.cleanwise.view.forms.Admin2UserConfigMgrForm">

        <table width="100%" class="results">
            <tr>
                <td align="right"><b><app:storeMessage key="admin2.user.config.groups.label.groupName"/></b></td>
                <td>
                    <html:text name="ADMIN2_USER_CONFIG_MGR_FORM" property="confSearchField"/>

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
                    <app:storeMessage key="admin2.user.config.text.showConfiguredOnly"/>:<html:checkbox name='ADMIN2_USER_CONFIG_MGR_FORM' property='conifiguredOnlyFl' value='true'/>

                </td>
            </tr>

            <logic:present name='ADMIN2_USER_CONFIG_MGR_FORM' property="config">

                <bean:size id="rescount" name='ADMIN2_USER_CONFIG_MGR_FORM' property="config.values"/>
                 <tr>
                <td colspan="2"><app:storeMessage key="admin2.search.criteria.result.text.searchResultCount"/>:<bean:write name="rescount" /></td>
                </tr>

                  <logic:greaterThan name="rescount" value="0">
                    <tr>
                        <td><b><app:storeMessage key="admin2.user.config.groups.text.memberOfGroup"/>:</b></td>
                        <td valign="top">
                            <logic:iterate id="arrele"
                                           name='ADMIN2_USER_CONFIG_MGR_FORM'
                                           property="config.values"
                                           indexId="i">
                                <%String selectedStr = "config.selected[" + i + "]"; %>
                                <br><html:multibox name="ADMIN2_USER_CONFIG_MGR_FORM" property="<%=selectedStr%>" value="true"/><bean:write name="arrele" property="object2"/>
                            </logic:iterate>

                        </td>
                    </tr>
                </logic:greaterThan>

            </logic:present>
            <tr>
                <td colspan="4" align="center">
                    <html:submit property="action">
                        <app:storeMessage  key="admin2.button.saveUserGroups"/>
                    </html:submit>
                    <html:reset>
                        <app:storeMessage  key="admin2.button.resetFields"/>
                    </html:reset>
                    &nbsp;
                </td>
            </tr>
        </table>
      
    <table ID=1431 cellpadding=4 class="adm_panel">
      <tr>
        <td colspan=6 class="aton"><b>Groups Information</b></td>
      </tr>
      <tr>
        <td>
          <% boolean headerDone = false; %>
          <logic:iterate id="ugrep" name="ADMIN2_USER_CONFIG_MGR_FORM" 
            			property="groupsReport"
            			type="UniversalDAO.dbrow">

            <% if (!headerDone) { headerDone = true; %>
            <%=ugrep.toHtmlTableHeader()%>
            <% } %>

            <%=ugrep.toHtmlTableRow()%>
          </logic:iterate>
        </td>
      </tr>


    </table>
        

    </html:form>

</div>
