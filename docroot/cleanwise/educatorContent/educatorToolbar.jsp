

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/> 




<table align="center" border="0" cellpadding="0" cellspacing="0" width="767">
  <!-- the following row contains the secondary navigation -->
  <tr>
	<td class="selectorcontentdk" colspan="2">
	  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" width="767" HEIGHT="25">
	</td>
  </tr>
  <tr><!-- the following row contains the section-specific branding -->
	<td class="changeshippingmed" valign="top" width="143">
	  <img src="/<%=storeDir%>/<%=ip%>images/cw_productselector.gif" WIDTH="143" HEIGHT="30">
	</td>
	<td align="left" class="changeshippingmed" width="624"></td>	
  </tr>
  <tr><!-- the following row contains a the border between navigation and content -->	
	<td class="selectorcontentdk" colspan="2">
	  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" WIDTH="767" HEIGHT="3"> 
	</td>	
  </tr>
</table>
