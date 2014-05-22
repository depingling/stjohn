<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.File" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script language="JavaScript">
<!--
function setAndSubmit(fid, vv, value) {
  var aaa = document.forms[fid].elements[vv];
  aaa.value=value;
  aaa.form.submit();
  return false;

}
-->
</script>


<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<% String storeDir=ClwCustomizer.getStoreDir(); %>

<!-- pickup images -->
<table align="right" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="smalltext" colspan="2">
      <html:radio name="MSDS_FORM" property="docType" value="MSDS"/><b><app:storeMessage key="shop.msdsSpecs.text.msdsSheets"/></b>&nbsp;&nbsp;
      <html:radio name="MSDS_FORM" property="docType" value="SPEC"/><b><app:storeMessage key="shop.msdsSpecs.text.productInfoSheets"/></b>&nbsp;&nbsp;
    </td>
  </tr>
  <tr>
    <td><b><app:storeMessage key="shop.msdsSpecs.text.poductName"/></b></td>
    <td align="left">
      <html:text name="MSDS_FORM" property="searchDesc" size="21" />
    </td>
  </tr>
  <tr>
    <td><b><app:storeMessage key="shop.msdsSpecs.text.category"/></b></td>
    <td align="left">
      <html:select name="MSDS_FORM" property="searchCategory">
        <html:options  name="MSDS_FORM" property="menuCategoryOptions" labelName="MSDS_FORM" 
            labelProperty="menuCategoryLabels"/>
      </html:select>
    </td>
  </tr>
  <tr>
    <% if (appUser.getUserAccount().isHideItemMfg()) { %>
      <td><b><app:storeMessage key="shop.msdsSpecs.text.ourSku#"/></b></td>
      <td><html:text name="MSDS_FORM" property="searchSku" size="17" /></td>
    <% } else { %>
    <td colspan="2">
      <html:radio name="MSDS_FORM" property="searchSkuType" value="Cust.Sku"/>
         <b><app:storeMessage key="shop.msdsSpecs.text.ourSku#"/></b>
      <html:radio name="MSDS_FORM" property="searchSkuType" value="Mfg.Sku"/>
         <b><app:storeMessage key="shop.msdsSpecs.text.mfgSku#"/></b>
      <html:text name="MSDS_FORM" property="searchSku" size="17" />
    </td>
    <% } %>
  </tr>
  <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
  <tr>
    <td colspan="2">
      <b><app:storeMessage key="shop.msdsSpecs.text.manufacturer"/></b>&nbsp;
      <html:select name="MSDS_FORM" property="searchManufacturer">
        <html:options  name="MSDS_FORM" property="menuMfgOptions" labelName="MSDS_FORM" labelProperty="menuMfgLabels"/>
      </html:select>
    </td>
  </tr>
  <% } %>  
  <tr>
    <td colspan="2" align="left">
     <input type='submit' property="action" class='specSearchSubmit' 
                onclick="setAndSubmit('sel','command','search')"
                 value='<app:storeMessage key="global.action.label.submit" />'/>              
    </td>
  </tr>
</table>



