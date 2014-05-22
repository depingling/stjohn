
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
                <td class="text"><span class="subheaders">Wet/Dry Vacuums</span>

<p>
Wet/Dry Vacuums are an essential tool for any type of facility.  Wet/dry vacuums are tank-type, vacuums that can be used for picking up wet or dry soils.  They are typically used for flood clean up, stripping floors and anywhere else needed to remove large amounts of wet material.  
</p>

<p>
When selecting a wet/dry vacuum, consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#size">Size of the tank</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#acc">Accessories</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#size"><b>Wet/ Dry Vacuums:  Size of the tank</b></a></span><br><br><span class="text"> 
The size of the tank is typically the first consideration.  Tanks range in size form 6 to 20 gallons.  Make sure that the tank size is large enough to contain the typical size of cleanup necessary.  They are made of a variety of materials, including polyethylene (plastic) and stainless steel.
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
<a name="#acc"><b>Accessories</b></a></span><br><br><span class="text"> 
For picking up liquid material from floors quickly, many wet/dry vacuums can be fitted with a front-mount squeegee, which allows the worker maximum efficiency.  Many models are sold complete with everything you will need to operate the machine, but with some models, wet/dry tools, such as wands, floor tools, hoses and squeegees may be sold separately or in kits.
</p>

<br><br>
</td></tr>
</table>

          