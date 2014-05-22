
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
  <tr>
    <td align="right" class="text">
	  <a href="#">
	    [<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>    
	</td>
	<td width="20%">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">
	  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="138">
    </td>	
  </tr>
</table>
</td>
<td class="tableoutline" width="1">
  <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1">
</td>
</tr>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
  <tr>

	<td class="selectorcontentdk"><img src="/<%=storeDir%>/<%=ip%>images/cw_productshortfooter.gif" width="<%=Constants.TABLEWIDTH1%>" HEIGHT="23"></td>
  </tr>
</table>
