<div id="groupConfig">
  <html:form styleId="1429" name="STORE_ADMIN_USER_FORM" 
    action="<%=f2sub%>"
    scope="session" type="com.cleanwise.view.forms.UserMgrDetailForm">

    <table ID=1430 width="769"  class="results">
    <tr> 
        <td align="right"><b>Group Name</b></td>
        <td>
          <html:text name="STORE_ADMIN_USER_FORM" property="confSearchField" 
          onfocus="javascript:f_check_search();" />
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="nameBegins" />  Name(starts with)
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="nameContains" />  Name(contains)
        </td>
      </tr>
      <tr> <td>&nbsp;</td>
        <td>
          <html:hidden property="groupconfig" value="true"/>
        
          <html:submit property="action">
            <app:storeMessage  key="global.action.label.search"/>
          </html:submit>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Configured Only:
          <html:checkbox name = 'STORE_ADMIN_USER_FORM' property='conifiguredOnlyFl' value='true'/>
        </td>
      </tr>
      <tr>
        <td><b>Member of Group:</b></td>
        <td valign="top">
          <%
            java.util.List groups = (java.util.List) request.getSession().getAttribute("Users.groups.list");
            if ( null != groups ) {
              java.util.Iterator itr = groups.iterator();
              while ( itr != null && itr.hasNext() ) {
                String gname = (String)itr.next();
            %>
          <br><html:multibox name="STORE_ADMIN_USER_FORM" property="memberOfGroups" 
          value="<%=gname%>"/><%=gname%>
          <% 
            } // end of while
            } // if on groups
          %>
        </td>
      </tr>

      <tr>
        <td colspan="4" align="center">
          <html:submit property="action">
            <app:storeMessage  key="admin.button.saveUserGroups"/>
          </html:submit>
          <html:reset>
            <app:storeMessage  key="admin.button.reset"/>
          </html:reset>&nbsp;
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
          <logic:iterate id="ugrep" name="STORE_ADMIN_USER_FORM" 
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
