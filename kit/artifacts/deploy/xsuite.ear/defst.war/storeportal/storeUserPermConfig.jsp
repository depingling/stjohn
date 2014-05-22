<div id="permConfig">
  <html:form styleId="1433" name="STORE_ADMIN_USER_FORM" 
            action="<%=f1sub%>" scope="session" 
            type="com.cleanwise.view.forms.StoreUserMgrForm">
    <html:hidden property="permconfig" value="true"/>
    <table ID=1434 width="769" class="results">
      <tr>
      <td>&nbsp;</td>
      <td> 
        <html:text name="STORE_ADMIN_USER_FORM" property="confSearchField" 
        onfocus="javascript:f_check_search();" />
        <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
        value="id" />  ID
        <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
        value="nameBegins" />  Name(starts with)
        <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
        value="nameContains" />  Name(contains)
      </td></tr>

      <tr> <td>&nbsp;</td>
        <td>
          <html:submit property="action">
            <app:storeMessage  key="global.action.label.search"/>
          </html:submit>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Merge Account Permissions:&nbsp;&nbsp; 
          <html:radio name="STORE_ADMIN_USER_FORM" property="mergeAccountPermFl" value = "true"/> Yes
          &nbsp;&nbsp;
          <html:radio name="STORE_ADMIN_USER_FORM" property="mergeAccountPermFl" value = "false"/> No
        </td>
      </tr>

    </table>

    <%
     LinkedList permKeys = theForm.getConfPermKeys();
     HashMap permHM = theForm.getConfPermissions();
     HashMap acctHM = theForm.getConfPermAccounts();
     
     boolean showWholeAcctListFl = theForm.getShowAllAcctFl();
     if(permHM!=null) {
       for(Iterator iter=permKeys.iterator(); iter.hasNext();) {
         Integer key = (Integer) iter.next();
    %>
      <b>Account specific rights</b>
    <table ID=1435 class="results" cellspacing="0" width='750'>
    <tr><td>
    <%
         BusEntityDataVector acctDV = (BusEntityDataVector) acctHM.get(key);
         for(int ii=0; ii<acctDV.size(); ii++) {
           if(!showWholeAcctListFl && ii>=3) {
             break;
           }
           BusEntityData beD = (BusEntityData) acctDV.get(ii);
    %>
      &lt;<%=beD.getShortDesc()%>&nbsp;&gt;&nbsp;&nbsp;
    
    
    <% } %>
    <% if(acctDV.size()>3 && !showWholeAcctListFl) { %>
      ... &nbsp;&nbsp;&nbsp; 
     <a ID=1436 href="userconfig.do?action=showWholeAcctList&tab=permConfig">Show All</a>
    <% } %>
    </td></tr>
    <tr><td>

       <%@ include file="storeUserRightsConfig.jsp" %>
    <% } %>
    <% } %>

<!-- 000 end of user rights -->
    </td></tr>
    </table>
    

    <table ID=1437 width="769"  class="results">
      <tr>
        <td>&nbsp;</td>
        <td align="center">
          <html:submit property="action">
            <app:storeMessage  key="admin.button.saveUserPermissions"/>
          </html:submit>
          
          <html:reset>
            <app:storeMessage  key="admin.button.reset"/>
          </html:reset>&nbsp;
        </td>
      </tr>
    </table>
  </html:form>
</div>
