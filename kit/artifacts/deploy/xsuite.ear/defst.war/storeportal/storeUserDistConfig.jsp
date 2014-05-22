<div id="distConfig">
    <html:form styleId="1418" name="STORE_ADMIN_USER_FORM"  action="<%=f1sub%>" scope="session" 
                           type="com.cleanwise.view.forms.StoreUserMgrForm"> 
    <html:hidden property="distconfig" value="true"/>
      <table ID=1419 width="769" class="results">
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
        </td>
        </tr>
        <tr> <td>&nbsp;</td>
          <td>
            <html:submit property="action">
              <app:storeMessage  key="global.action.label.search"/>
            </html:submit>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Configured Only:
            <html:checkbox name = 'STORE_ADMIN_USER_FORM' property='conifiguredOnlyFl' value='true'/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
    
          </td>
        </tr>

      </table>

      <logic:present name="user.distributor.vector">
      <bean:size id="rescount"  name="user.distributor.vector"/>
      Search result count:  <bean:write name="rescount" />
      <logic:greaterThan name="rescount" value="0">
    <table ID=1420 width="769"  class="results">
      <tr align=left>
      <td><a ID=1421 class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Distr.&nbsp;Id</td>
      <td><a ID=1422 class="tableheader" href="#pgsort" onclick="sortSubmit('erpNum');">ERP&nbsp;Num</td>
      <td><a ID=1423 class="tableheader" href="#pgsort" onclick="sortSubmit('name');">Name</td>
      <td><a ID=1424 class="tableheader" href="#pgsort" onclick="sortSubmit('address');">Address 1</td>
      <td><a ID=1425 class="tableheader" href="#pgsort" onclick="sortSubmit('city');">City</td>
      <td><a ID=1426 class="tableheader" href="#pgsort" onclick="sortSubmit('state');">State</td>
      <td><a ID=1427 class="tableheader" href="#pgsort" onclick="sortSubmit('status');">Status</td>
      <td class="tableheader">Select</td>
      </tr>
     <logic:iterate id="arrele" name="user.distributor.vector">
     <tr>

     <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
     <td><bean:write name="arrele" property="busEntity.erpNum"/></td>
     <td>
      <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
      <a ID=1428 href="distmgr.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
      <bean:write name="arrele" property="busEntity.shortDesc"/></a>
     </td>
      <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
      <td><bean:write name="arrele" property="primaryAddress.city"/></td>
      <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
      <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>

       <td><html:multibox name="STORE_ADMIN_USER_FORM" property="confSelectIds" 
            value="<%=(\"\"+eleid)%>" />
          </td>
          <html:hidden name="STORE_ADMIN_USER_FORM" property="confDisplayIds" 
          value="<%=(\"\"+eleid)%>" />


       </tr>
       </logic:iterate>
        <td colspan="6"></td>
        <td>
        <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
        </td>
      </table>
      </logic:greaterThan>
      </logic:present>

   <html:hidden  property="action" value="BBBBBBB"/>
   <html:hidden  property="sortField" value="BBBBBBB"/>

</html:form>
</div>