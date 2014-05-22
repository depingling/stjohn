

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" value="<%=\"/\"+storeDir+\"/en/images\"%>" scope="session"/>
<HTML lang=en><HEAD><TITLE>Cleanwise.com</TITLE>
  <META http-equiv=Content-Type content="text/html; charset=windows-1252"><LINK 
href="/<%=storeDir%>/externals/pubstyles.css" type=text/css rel=stylesheet>
   <script language="javascript" type="text/javascript" src="/<%=storeDir%>/externals/lib.js"></script> 
  <script language="javascript" type="text/javascript">
      // preload mouseover images for navigation
      preloadImages('tour_page',1,12,'/<%=storeDir%>/en/images/'); 
      preloadImages('sales_tour_menu',1,13,'/<%=storeDir%>/en/images/');    
  </script>
<META content="MSHTML 6.00.2600.0" name=GENERATOR></HEAD>


 <body class="body">
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="772">
    <tr>
      <td colspan="7" width="772" valign="top">
        <img src="<%=ip%>/tour_headerleft.gif" WIDTH="175" HEIGHT="67" alt="" border="0"><a href="/<%=storeDir%>/index.jsp"><img src="<%=ip%>/tour_headerlogo.gif" WIDTH="192" HEIGHT="67" alt="" border="0"></a><img src="<%=ip%>/tour_headerright.gif" WIDTH="405" HEIGHT="67" alt="" border="0"></td>
    </tr>
    <tr>
      <td class="tableoutline" width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
      <td width="145" valign="top">
	<img src="<%=ip%>/sales_corporateOverview.gif" width="145" height="24" alt="The Tour" border="0"><br>
	<table border="0" width="145" cellspacing="0" cellpadding="0">
  	  <tr>
	    <td class="tourgreen" valign="top" align="right">
	      <img src="<%=ip%>/spacer.gif" width="118" height="7" alt="" border="0"><br>
	      <a href="/<%=storeDir%>/public/en/tour/index.html" onmouseover="change('sales_tour_menu1','on');change('tour_page1','on')" onmouseout="change('sales_tour_menu1','off');change('tour_page1','off')"><img src="<%=ip%>/sales_tour_menu1off.gif" width="55" height="10" alt="Welcome" border="0" name="sales_tour_menu1"></a><a href="/<%=storeDir%>/public/en/tour/index.html" onmouseover="change('sales_tour_menu1','on');change('tour_page1','on')" onmouseout="change('sales_tour_menu1','off');change('tour_page1','off')"><img src="<%=ip%>/tour_page1off.gif" width="26" height="17" alt="Page 1" border="0" name="tour_page1"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour2.html" onmouseover="change('sales_tour_menu2','on');change('tour_page2','on')" onmouseout="change('sales_tour_menu2','off');change('tour_page2','off')"><img src="<%=ip%>/sales_tour_menu2off.gif" width="106" height="12" alt="Value Proposition" border="0" name="sales_tour_menu2"></a><a href="/<%=storeDir%>/public/en/tour/tour2.html" onmouseover="change('sales_tour_menu2','on');change('tour_page2','on')" onmouseout="change('sales_tour_menu2','off');change('tour_page2','off')"><img src="<%=ip%>/tour_page2off.gif" width="26" height="17" alt="Page 2" border="0" name="tour_page2"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour3.html" onmouseover="change('sales_tour_menu3','on');change('tour_page3','on')" onmouseout="change('sales_tour_menu3','off');change('tour_page3','off')"><img src="<%=ip%>/sales_tour_menu3off.gif" width="78" height="12" alt="Supply Chain" border="0" name="sales_tour_menu3"></a><a href="/<%=storeDir%>/public/en/tour/tour3.html" onmouseover="change('sales_tour_menu3','on');change('tour_page3','on')" onmouseout="change('sales_tour_menu3','off');change('tour_page3','off')"><img src="<%=ip%>/tour_page3off.gif" width="26" height="17" alt="Page 3" border="0" name="tour_page3"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour4.html" onmouseover="change('sales_tour_menu4','on');change('tour_page4','on')" onmouseout="change('sales_tour_menu4','off');change('tour_page4','off')"><img src="<%=ip%>/sales_tour_menu4off.gif" width="81" height="10" alt="Inefficiencies" border="0" name="sales_tour_menu4"></a><a href="/<%=storeDir%>/public/en/tour/tour4.html" onmouseover="change('sales_tour_menu4','on');change('tour_page4','on')" onmouseout="change('sales_tour_menu4','off');change('tour_page4','off')"><img src="<%=ip%>/tour_page4off.gif" width="26" height="17" alt="Page 4" border="0" name="tour_page4"></a><br>	      
	      <a href="/<%=storeDir%>/public/en/tour/tour5.html" onmouseover="change('sales_tour_menu5','on');change('tour_page5','on')" onmouseout="change('sales_tour_menu5','off');change('tour_page5','off')"><img src="<%=ip%>/sales_tour_menu5off.gif" width="92" height="10" alt="Needs & Wants" border="0" name="sales_tour_menu5"></a><a href="/<%=storeDir%>/public/en/tour/tour5.html" onmouseover="change('sales_tour_menu5','on');change('tour_page5','on')" onmouseout="change('sales_tour_menu5','off');change('tour_page5','off')"><img src="<%=ip%>/tour_page5off.gif" width="26" height="17" alt="Page 5" border="0" name="tour_page5"></a><br>
	     <a href="/<%=storeDir%>/public/en/tour/tour6.html" onmouseover="change('sales_tour_menu6','on');change('tour_page6','on')" onmouseout="change('sales_tour_menu6','off');change('tour_page6','off')"><img src="<%=ip%>/sales_tour_menu6off.gif" width="114" height="10" alt="Our Differentiators" border="0" name="sales_tour_menu6"></a><a href="/<%=storeDir%>/public/en/tour/tour6.html" onmouseover="change('sales_tour_menu6','on');change('tour_page6','on')" onmouseout="change('sales_tour_menu6','off');change('tour_page6','off')"><img src="<%=ip%>/tour_page6off.gif" width="26" height="17" alt="Page 6" border="0" name="tour_page6"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour7.html" onmouseover="change('sales_tour_menu7','on');change('tour_page7','on')" onmouseout="change('sales_tour_menu7','off');change('tour_page7','off')"><img src="<%=ip%>/sales_tour_menu7off.gif" width="84" height="10" alt="Order Process" border="0" name="sales_tour_menu7"></a><a href="/<%=storeDir%>/public/en/tour/tour7.html" onmouseover="change('sales_tour_menu7','on');change('tour_page7','on')" onmouseout="change('sales_tour_menu7','off');change('tour_page7','off')"><img src="<%=ip%>/tour_page7off.gif" width="26" height="17" alt="Page 7" border="0" name="tour_page7"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour8.html" onmouseover="change('sales_tour_menu8','on');change('tour_page8','on')" onmouseout="change('sales_tour_menu8','off');change('tour_page8','off')"><img src="<%=ip%>/sales_tour_menu8off.gif" width="71" height="10" alt="Distribution" border="0" name="sales_tour_menu8"></a><a href="/<%=storeDir%>/public/en/tour/tour8.html" onmouseover="change('sales_tour_menu8','on');change('tour_page8','on')" onmouseout="change('sales_tour_menu8','off');change('tour_page8','off')"><img src="<%=ip%>/tour_page8off.gif" width="26" height="17" alt="Page 8" border="0" name="tour_page8"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour9.html" onmouseover="change('sales_tour_menu9','on');change('tour_page9','on')" onmouseout="change('sales_tour_menu9','off');change('tour_page9','off')"><img src="<%=ip%>/sales_tour_menu9off.gif" width="101" height="10" alt="Business Review" border="0" name="sales_tour_menu9"></a><a href="/<%=storeDir%>/public/en/tour/tour9.html" onmouseover="change('sales_tour_menu9','on');change('tour_page9','on')" onmouseout="change('sales_tour_menu9','off');change('tour_page9','off')"><img src="<%=ip%>/tour_page9off.gif" width="26" height="17" alt="Page 9" border="0" name="tour_page9"></a><br>
              <a href="/<%=storeDir%>/public/en/tour/tour10.html" onmouseover="change('sales_tour_menu10','on');change('tour_page10','on')" onmouseout="change('sales_tour_menu10','off');change('tour_page10','off')"><img src="<%=ip%>/sales_tour_menu10off.gif" width="105" height="10" alt="Future Initiatives" border="0" name="sales_tour_menu10"></a><a href="/<%=storeDir%>/public/en/tour/tour10.html" onmouseover="change('sales_tour_menu10','on');change('tour_page10','on')" onmouseout="change('sales_tour_menu10','off');change('tour_page10','off')"><img src="<%=ip%>/tour_page10off.gif" width="26" height="17" alt="Page 10" border="0" name="tour_page10"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour11.html" onmouseover="change('sales_tour_menu11','on');change('tour_page11','on')" onmouseout="change('sales_tour_menu11','off');change('tour_page11','off')"><img src="<%=ip%>/sales_tour_menu11off.gif" width="77" height="12" alt="Cost Savings" border="0" name="sales_tour_menu11"></a><a href="/<%=storeDir%>/public/en/tour/tour11.html" onmouseover="change('sales_tour_menu11','on');change('tour_page11','on')" onmouseout="change('sales_tour_menu11','off');change('tour_page11','off')"><img src="<%=ip%>/tour_page11off.gif" width="26" height="17" alt="Page 11" border="0" name="tour_page11"></a><br>
	      <a href="/<%=storeDir%>/public/en/tour/tour12.html" onmouseover="change('sales_tour_menu12','on');change('tour_page12','on')" onmouseout="change('sales_tour_menu12','off');change('tour_page12','off')"><img src="/<%=storeDir%>/en/images/sales_tour_menu12off.gif" width="68" height="10" alt="You Decide" border="0" name="sales_tour_menu12"></a><a href="/<%=storeDir%>/public/en/tour/tour12.html" onmouseover="change('sales_tour_menu12','on');change('tour_page12','on')" onmouseout="change('sales_tour_menu12','off');change('tour_page12','off')"><img src="/<%=storeDir%>/en/images/tour_page12off.gif" width="26" height="17" alt="Page 12" border="0" name="tour_page12"></a><br><br>
              <a href="/<%=storeDir%>/public/en/tour/confidential.html" onmouseover="change('sales_tour_menu13','on');" onmouseout="change('sales_tour_menu13','off');"><img src="/<%=storeDir%>/en/images/sales_tour_menu13off.gif" width="64" height="10" alt="Confidential" border="0" name="sales_tour_menu13"></a><br><img src="/<%=storeDir%>/en/images/tour_undermenu.gif" width="145" height="12" alt="" border="0"></td>
	  </tr>
	</table>
	<img src="<%=ip%>/spacer.gif" width="1" height="40" alt="" border="0">
      </td>
      <td class="tableoutline" width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
      <td width="393" valign="top">
        <img src="<%=ip%>/subheader_contactusaddress.gif" WIDTH="393" HEIGHT="40">
				<div class="centermargin">
					<p><b>Thank you for registering with cleanwise.com. A Cleanwise representative will be in touch with you to answer your questions and give you more information about our services. </b></p>
						<html:form name="CONTACT_US_FORM" 
type="com.cleanwise.view.forms.ContactUsForm"
action="cleanwise/index.jsp">
					  <table cellspacing="0" cellpadding="0" border="0" align="center">                        
						<tr>
						  <td class="texttext" align="left" valign="top"><b>First Name:</b></td>
						  <td width="15%">&nbsp;</td>
						  <td class="texttext"><bean:write name="CONTACT_US_FORM" property="firstName"/></td>
						</tr>
						<tr>
						  <td class="texttext" align="left" valign="top"><b>Last Name:</b></td>
						  <td>&nbsp;</td>
						  <td class="texttext"><bean:write name="CONTACT_US_FORM" property="lastName"/></td>
						</tr>
						<tr>
						  <td class="texttext" align="left" valign="top"><b>Title:</b></td>
						  <td>&nbsp;</td>
						  <td class="texttext"><bean:write name="CONTACT_US_FORM" property="title"/></td>
						</tr>
						<tr>
						  <td class="texttext" align="left" valign="top"><b>Company Name:</b></td>
						  <td>&nbsp;</td>
						  <td class="texttext"><bean:write name="CONTACT_US_FORM" property="companyName"/></td>
						</tr>
						<tr>
						  <td class="texttext" align="left" valign="top"><b>Industry</b></td>
						  <td>&nbsp;</td>
						  <td class="texttext"><bean:write name="CONTACT_US_FORM" property="industry"/></td>
						</tr>
						<tr>
						  <td class="texttext" align="left" valign="top"><b>Address:</b></td>
						  <td>&nbsp;</td>
						  <td class="texttext">
							<bean:write name="CONTACT_US_FORM" property="address1"/><br>
                            <bean:write name="CONTACT_US_FORM" property="address2"/><br>
                            <bean:write name="CONTACT_US_FORM" property="city"/>,&nbsp;
							<bean:write name="CONTACT_US_FORM" property="state"/>
							<bean:write name="CONTACT_US_FORM" property="zip"/>
						  </td>
						</tr>
						<tr>
						  <td class="texttext" align="left" valign="top"><b>Phone:</b></td>
						  <td>&nbsp;</td>
						  <td class="texttext"><bean:write name="CONTACT_US_FORM" property="phone"/></td>
						</tr>
						<tr>
						  <td class="texttext" align="left" valign="top"><b>Email:</b></td>
						  <td>&nbsp;</td>
						  <td class="texttext"><bean:write name="CONTACT_US_FORM" property="fromEmail"/></td>
						</tr>
						<tr>
						  <td colspan="3">&nbsp;</td>
						</tr> 
						<tr>
						  <td colspan="3">&nbsp;</td>
						</tr> 
					  </table>
					  </html:form>
				</div>
      </td>
      <td class="tableoutline"width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
      <td width="230" valign="top"><img src="/<%=storeDir%>/en/images/tour_rightboxtop.gif" width="230" height="37" alt="" border="0"><br><img src="/<%=storeDir%>/en/images/confirmGreenbox.gif" width="230" height="232" alt="" border="0">
      </td>					
      <td class="tableoutline" width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
    </tr>
    <tr>
      <td class="tableoutline" width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
      <td width="145">
        <img src="<%=ip%>/spacer.gif" height="1" width="145" alt="" border="0">
      </td>
      <td class="tableoutline" width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
      <td width="393">
        <img src="<%=ip%>/spacer.gif" height="1" width="393" alt="" border="0">
      </td>
      <td class="tableoutline" width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
      <td width="230">
        <img src="<%=ip%>/spacer.gif" height="1" width="230" alt="" border="0">
      </td>				
      <td class="tableoutline" width="1">
        <img src="<%=ip%>/spacer.gif" height="1" width="1" alt="" border="0">
      </td>
    </tr>
    <tr>
      <td colspan="7" width="772">
        <img src="<%=ip%>/tour_footer.gif" WIDTH="772" HEIGHT="15" alt="" border="0">
      </td>
    </tr>
  </table>
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="769">
    <tr>
      <td class="smalltext"><br><div class="fivemargin">&copy; 2000 Cleanwise, Inc. All Rights Reserved. All content and images contained on this web site are the intellectual property of Cleanwise, Inc. Any party making unauthorized use or copies of the content or images on this web site risks prosecution under applicable federal and state law.</div></td>
    </tr>
  </table>
  </body>
</html>
