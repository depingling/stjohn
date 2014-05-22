  <div id="sitesConfig">
  <html:form styleId="1454" name="STORE_ADMIN_USER_FORM"  action="<%=f1sub%>" scope="session" 
  type="com.cleanwise.view.forms.StoreUserMgrForm">


  <table ID=1455 width="769"  class="results">
  <tr><td colspan=2>Search for sites belonging to the accounts configured for 
  this user.</td>
</td>
      <tr> <td align="right"><b>Site Name</b></td>
        <td>
          <html:text name="STORE_ADMIN_USER_FORM" property="confSearchField" 
          onfocus="javascript:f_check_search();" />
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="id" />  ID
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="nameBegins" />  Name(starts with)
          <html:radio name="STORE_ADMIN_USER_FORM" property="confSearchType" 
          value="nameContains" />  Name(contains)
        </td>
      </tr>

	  <tr> <td align="right"><b>Reference Number</b></td>
        <td>
          <html:text name="STORE_ADMIN_USER_FORM" property="searchRefNum" 
          onfocus="javascript:f_check_search();" />
          <html:radio name="STORE_ADMIN_USER_FORM" property="searchRefNumType" 
          value="nameBegins" /> (starts with)
          <html:radio name="STORE_ADMIN_USER_FORM" property="searchRefNumType" 
          value="nameContains" /> (contains)
        </td>
      </tr>
      <tr> 
        <td align="right"><b>City</b></td>
        <td><html:text name="STORE_ADMIN_USER_FORM" property="confCity"/> </td>
      </tr>

      <tr> 
        <td align="right"><b>State</b></td>
        <td><html:text name="STORE_ADMIN_USER_FORM" property="confState"/> </td>
      </tr>
    
      <tr> <td>&nbsp;</td>
        <td>
          <html:hidden property="siteconfig" value="true"/>
        
          <html:submit property="action">
            <app:storeMessage  key="global.action.label.search"/>
          </html:submit>

          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Configured Only:
          <html:checkbox name = 'STORE_ADMIN_USER_FORM' property='conifiguredOnlyFl' value='true'/>
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Inactive:
          <html:checkbox name = 'STORE_ADMIN_USER_FORM' property='confShowInactiveFl' value='true'/>
        </td>
      </tr>
  
    </table>

      <logic:present name="user.siteview.vector">
      <bean:size id="rescount"  name="user.siteview.vector"/>
      Search result count:  <bean:write name="rescount" />
      <logic:greaterThan name="rescount" value="0">

      <table ID=1456>
      <tr align=left>
        <td><a ID=1457 class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Site Id</td>
        <td><a ID=1458 class="tableheader" href="#pgsort" onclick="sortSubmit('name');">Name</td>
        <td><a ID=1459 class="tableheader" href="#pgsort" onclick="sortSubmit('address');">Street Address</td>
        <td><a ID=1460 class="tableheader" href="#pgsort" onclick="sortSubmit('city');">City</td>
        <td><a ID=1461 class="tableheader" href="#pgsort" onclick="sortSubmit('state');">State</td>
        <td><a ID=1462 class="tableheader" href="#pgsort" onclick="sortSubmit('zipcode');">Zip</td>
        <td><a ID=1463 class="tableheader" href="#pgsort" onclick="sortSubmit('status');">Status</td>
	
        <td class="tableheader">
          <a ID=1464 href="javascript:f_check_boxes();">[ Select All ]<br></a>
          <a ID=1465 href="javascript:f_uncheck_boxes();">[ Clear All ]</a>
        </td>
	
	<td><a ID=1466 class="tableheader" href="#pgsort" onclick="sortSubmit('defaultsite');">Default Site</td>
      </tr>

      <logic:iterate id="arrele" name="user.siteview.vector" 
        type="com.cleanwise.service.api.value.SiteView">

        <tr>
          <td><bean:write name="arrele" property="id"/></td>
          <td>
            <%
              String eleid = String.valueOf(arrele.getId());
            %>
            <% if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_SITE)) {%>
            <a ID=1466 href="sitedet.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
              <bean:write name="arrele" property="name"/>
            </a>
            <% } else { %>
              <bean:write name="arrele" property="name"/>
            <% } %>
          </td>
          <td>
            <bean:write name="arrele" property="address"/>
          </td>
          <td>
            <bean:write name="arrele" property="city"/>
          </td>
          <td>
            <bean:write name="arrele" property="state"/>
          </td>
          <td>
            <bean:write name="arrele" property="postalCode"/>
          </td>
          <td><bean:write name="arrele" property="status"/></td>
          <td>
            <html:multibox name="STORE_ADMIN_USER_FORM" property="confSelectIds" 
            value="<%=eleid%>" />
          </td>
	  <%
	    if(arrele.getStatus().equals(RefCodeNames.STATUS_CD.ACTIVE)){
	  %>
	  <td>
	    <html:radio name="STORE_ADMIN_USER_FORM" property="defaultSite" 
	    value="<%=eleid%>" />
	  </td>
	  <% }else{ %>
	  <td>&nbsp;</td>
	  <% } %>
          <html:hidden name="STORE_ADMIN_USER_FORM" property="confDisplayIds" 
          value="<%=eleid%>" />
        </tr>

      </logic:iterate>
      <td colspan="6"></td>
      <td>
        <html:submit property="action">
          <app:storeMessage  key="admin.button.saveUserSites"/>
        </html:submit>
      </td>
    </tr>

        </table>

      </logic:greaterThan>
    </logic:present>

  <html:hidden  property="action" value="BBBBBBB"/>
  <html:hidden  property="sortField" value="BBBBBBB"/>

  </html:form>

</div>
