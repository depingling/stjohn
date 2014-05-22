<div id="catalogConfig">
    <html:form styleId="1389" name="STORE_ADMIN_USER_FORM"  action="<%=f1sub%>" scope="session" 
                           type="com.cleanwise.view.forms.StoreUserMgrForm"> 
    <html:hidden property="catalogconfig" value="true"/>
      <table ID=1390 width="769" class="results">
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
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This Page Shows Only Related Catalogs:
        </tr>

      </table>

      <logic:present name="user.catalog.vector">
      <bean:size id="rescount"  name="user.catalog.vector"/>
      Search result count:  <bean:write name="rescount" />
      <logic:greaterThan name="rescount" value="0">
    <table ID=1391 width="769"  class="results">
      <tr align=left>
      <td><a ID=1392 class="tableheader" href="#pgsort" onclick="sortSubmit('id');">Catalog.&nbsp;Id</td>
      <td><a ID=1393 class="tableheader" href="#pgsort" onclick="sortSubmit('name');">Name</td>
      <td><a ID=1394 class="tableheader" href="#pgsort" onclick="sortSubmit('type');">Type</td>
      <td><a ID=1395 class="tableheader" href="#pgsort" onclick="sortSubmit('status');">Status</td>
      </tr>
     <logic:iterate id="arrele" name="user.catalog.vector">
     <tr>

     <td><bean:write name="arrele" property="catalogId"/></td>
     <td><bean:write name="arrele" property="shortDesc"/></td>
      <td><bean:write name="arrele" property="catalogTypeCd"/></td>
      <td><bean:write name="arrele" property="catalogStatusCd"/></td>
       </tr>
       </logic:iterate>
      </table>
      </logic:greaterThan>
      </logic:present>
 
   <html:hidden  property="action" value="BBBBBBB"/>
   <html:hidden  property="sortField" value="BBBBBBB"/>

</html:form>
</div>
