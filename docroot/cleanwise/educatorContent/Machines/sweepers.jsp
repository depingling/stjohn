
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>




<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>





      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          <td class="smalltext" valign="up" width="20%"></td>
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" class="text">
              <tr>
			    <td>
                <td class="text"><span class="subheaders">Sweepers</span>

<p>
Sweepers are available in a large variety of sizes, shapes and power sources, but they all have one thing in common; they pick up dirt!  
</p>

<p>
When selecting a sweeper, consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#manual">Manual Sweepers</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#battery">Battery vs. Propane Sweepers</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#manual"><b>Sweepers:  Manual Sweeper</b></a></span><br><br><span class="text"> 
Floor sweepers are powered by pushing them across the floor or carpet.  They are usually used in lobby or restaurant areas where silent cleaning is necessary, or where there is no power source available.  Larger, push-behind manual sweepers are available which use a combination of front and inside brushes, and can be used for inside or outdoors applications.
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#battery"><b>Sweepers:  Battery vs. Propane Sweepers</b></a></span><br><br><span class="text"> 
When purchasing a large, mechanized sweeper, first determine whether the machine will be used primarily for inside or outside use.  For an inside application, most facilities prefer battery sweepers, although sometimes propane sweepers can be used.  Large outdoor gas powered sweepers are available in a variety of sizes from walk behind to ride on models.
</p>


<br><br>
</td></tr>
</table>

          