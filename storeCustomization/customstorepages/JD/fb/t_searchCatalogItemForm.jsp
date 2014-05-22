
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<table border="0" cellpadding="3" cellspacing="0" align="right">
  <tr>
    <td align="left"><b><app:storeMessage key="shop.catalog.text.searchBy"/>&nbsp;&nbsp;</b></td>
    <td><b><app:storeMessage key="shop.catalog.text.productName"/></b></td>
    <td><html:text name="SHOP_FORM" property="searchName" size="29" /> </td>
  </tr>
  <tr>
    <td>&nbsp</td>
    <td><b><app:storeMessage key="shop.catalog.text.description"/></b></td>
    <td><html:text name="SHOP_FORM" property="searchDesc" size="29" /></td>
  </tr>
  <tr>
    <td>&nbsp</td>
    <td><b><app:storeMessage key="shop.catalog.text.ourSku#"/></b></td>
    <td><html:text name="SHOP_FORM" property="searchSku" size="16"/>
      <html:hidden name="SHOP_FORM" property="searchSkuType" value="Cust.Sku"/>
    </td>
  </tr>
  <tr>
    <td>&nbsp</td>
    <td><b><app:storeMessage key="shop.catalog.text.upcNumber"/></b></td>
    <td><html:text name="SHOP_FORM" property="searchUPCNum" size="16" /> </td>
  </tr>
  <tr>
    <td>&nbsp</td>
    <td colspan="2">
      <input type='submit' property="action" class='catalogSearchSubmit'
              onclick="setAndSubmit('sel','command','search')"
               value='<app:storeMessage key="shop.catalog.text.submit" />'/>
    </td>
  </tr>
    <tr>
        <td></td>
                <td><html:hidden name="SHOP_FORM" property="searchGreenCertifiedFl" value=""/>
        </td>
    </tr>
</table>



