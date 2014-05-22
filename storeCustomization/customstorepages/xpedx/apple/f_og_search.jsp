<td class="smalltext" valign="top">
<html:form styleId="sel" action="/store/shop.do?action=og_control">
  <table border="0" cellpadding="3" cellspacing="0" align="right">
    <tr>
      <td align="left" colspan="2"><b><app:storeMessage key="shop.og.text.searchBy"/></b></td>
    </tr>
    <tr>
      <td><b><app:storeMessage key="shop.og.text.productName"/></b></td>
      <td><html:text name="SHOP_FORM" property="ogSearchName" size="29" /> </td>
    </tr>
    <tr>
      <td>&nbsp</td>
      <td>
        <input type='submit' property="action" class='catalogSearchSubmit'
                 value='<app:storeMessage key="shop.og.text.submit" />'/>
      </td>
    </tr>
  </table>
<html:hidden property="command" value="search"/>
</html:form>
</td>
