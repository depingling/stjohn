
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
    <td colspan="2" align="right">
      <app:storeMessage key="shop.catalog.text.ourSku#"/>&nbsp;<html:radio name="SHOP_FORM" property="searchSkuType" value="Cust.Sku"/>
      <app:storeMessage key="shop.catalog.text.mfgSku#"/>&nbsp;<html:radio name="SHOP_FORM" property="searchSkuType" value="Mfg.Sku"/>
      <html:text name="SHOP_FORM" property="searchSku" size="16"
  onchange="javascript: f_cf();"
  onblur="javascript: f_cf();"
  />
    </td>
  </tr>
  <tr>
    <td>&nbsp</td>
    <td><b><app:storeMessage key="shop.catalog.text.upcNumber"/></b></td>
    <td><html:text name="SHOP_FORM" property="searchUPCNum" size="29" /> </td>
  </tr>
  <tr>
    <td>&nbsp</td>
    <td colspan="2">
      <html:select name="SHOP_FORM" property="searchManufacturer">
        <html:option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <app:storeMessage key="shop.catalog.text.manufacturer"/></html:option>
        <html:options  name="SHOP_FORM" property="menuMfgOptions" labelName="SHOP_FORM" labelProperty="menuMfgLabels"/>
      </html:select>

      <input type='submit' property="action" class='catalogSearchSubmit'
              onclick="setAndSubmit('sel','command','search')"
               value='<app:storeMessage key="shop.catalog.text.submit" />'/>
    </td>
  </tr>
    <tr>
        <td></td>
        <td colspan="2"><app:storeMessage key="shop.catalog.text.certifiedCompany"/>
        <html:radio name="SHOP_FORM" property="searchGreenCertifiedFl" value="true"/> <app:storeMessage key="shop.catalog.search.certifiedCompanyFilter.text.yes"/>
        <html:radio name="SHOP_FORM" property="searchGreenCertifiedFl" value="false"/><app:storeMessage key="shop.catalog.search.certifiedCompanyFilter.text.no"/>
        </td>
    </tr>
</table>



