
  <div id="acctConfig">
    <html:form styleId="1381" name="STORE_SVCPROV_DETAIL_FORM"  action="<%=f1sub%>" scope="session" 
      type="com.cleanwise.view.forms.StoreServiceProviderMgrDetailForm"> 
      <html:hidden property="acctconfig" value="true"/>
 
      <table ID=1382 width="769" class="results">
        <tr>
         <td>&nbsp;</td>
         <td> 
          <html:text name="STORE_SVCPROV_DETAIL_FORM" property="confSearchField" 
          onfocus="javascript:f_check_search();" />
          <html:radio name="STORE_SVCPROV_DETAIL_FORM" property="confSearchType" 
          value="id" />  ID
          <html:radio name="STORE_SVCPROV_DETAIL_FORM" property="confSearchType" 
          value="nameBegins" />  Name(starts with)
          <html:radio name="STORE_SVCPROV_DETAIL_FORM" property="confSearchType" 
          value="nameContains" />  Name(contains)
        </td></tr>

        <tr> <td>&nbsp;</td>
          <td>
            <html:submit property="action">
              <app:storeMessage  key="global.action.label.search"/>
            </html:submit>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Configured Only:
            <html:checkbox name = 'STORE_SVCPROV_DETAIL_FORM' property='conifiguredOnlyFl' value='true'/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
        </tr>


      </table>
      
      <logic:present name="user.account.vector">
      <bean:size id="rescount"  name="user.account.vector"/>
      Search result count:  <bean:write name="rescount" />
      <logic:greaterThan name="rescount" value="0">

      <table ID=1383>
      <tr align=left>
        <td><a ID=1384 class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Account Id</td>
        <td><a ID=1385 class="tableheader" href="#pgsort" onclick="sortSubmit('name');">Name</td>
        <td><a ID=1386 class="tableheader" href="#pgsort" onclick="sortSubmit('status');">Status</td>
        <td class="tableheader">
          <a ID=1387 href="javascript:f_check_boxes();">[ Select All ]<br></a>
          <a ID=1388 href="javascript:f_uncheck_boxes();">[ Clear All ]</a>
        </td>
      </tr>

      <logic:iterate id="arrele" name="user.account.vector" 
        type="com.cleanwise.service.api.value.AccountView">
            <%
              String eleid = String.valueOf(arrele.getAcctId());
            %>

        <tr>
          <td><bean:write name="arrele" property="acctId"/></td>
          <td><bean:write name="arrele" property="acctName"/></td>
          <td><bean:write name="arrele" property="acctStatusCd"/></td>
          <td><html:multibox name="STORE_SVCPROV_DETAIL_FORM" property="confSelectIds" 
            value="<%=eleid%>" />
          </td>
          <html:hidden name="STORE_SVCPROV_DETAIL_FORM" property="confDisplayIds" 
          value="<%=eleid%>" />
        </tr>

      </logic:iterate>
      <tr>
      <td colspan="3"></td>
      <td>
        <html:submit property="action">
          <app:storeMessage  key="admin.button.saveServiceProviderAccounts"/>
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
