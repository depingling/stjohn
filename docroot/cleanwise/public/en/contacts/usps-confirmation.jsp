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
  <link rel="stylesheet" type="text/css" href="/<%=storeDir%>/externals/pubstyles.css">
    <script language="javascript" type="text/javascript" src="/<%=storeDir%>/externals/lib.js"></script>
    <script language="javascript" type="text/javascript">
      // preload mouseover images for navigation
 	 preloadImages('pub_nav',1,6,'/<%=storeDir%>/en/images/');
	 preloadSingleImages('pub_message',0,6,'/<%=storeDir%>/en/images/');
      // -->
    </script>
<META content="MSHTML 6.00.2600.0" name=GENERATOR></HEAD>
<BODY class=bodyalt>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="772">
			<tr>
				<td colspan="7" width="772"><img src="<%=ip%>/sec_header_usps.gif" WIDTH="772" HEIGHT="56"></td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td bgcolor="#003333" width="1"><img src="<%=ip%>/spacer.gif" height="1" width="1"></td>
				<td valign="top"><img name="sec_login" src="<%=ip%>/sec_login.gif" width="144" height="27" border="0" usemap="#m_sec_login"><map name="m_sec_login"><area shape="rect" coords="93,3,133,23" href="/<%=storeDir%>/index.jsp" ></map><br>
				<a href="/<%=storeDir%>/index.jsp"><img src="<%=ip%>/pub_nav1off.gif" border="0" name="pub_nav1" WIDTH="144" HEIGHT="19"><br></a>
				<a href="/<%=storeDir%>/public/en/aboutus/index.html" onmouseover="change('pub_nav2','on')" onmouseout="change('pub_nav2','off')"><img src="<%=ip%>/pub_nav2off.gif" border="0" name="pub_nav2" WIDTH="144" HEIGHT="21"><br></a>
				<a href="/<%=storeDir%>/public/en/partners/index.html" onmouseover="change('pub_nav3','on')" onmouseout="change('pub_nav3','off')"><img src="<%=ip%>/pub_nav3off.gif" border="0" name="pub_nav3" WIDTH="144" HEIGHT="20"><br></a>
				<a href="/<%=storeDir%>/public/en/news/index.html" onmouseover="change('pub_nav4','on')" onmouseout="change('pub_nav4','off')"><img src="<%=ip%>/pub_nav4off.gif" border="0" name="pub_nav4" WIDTH="144" HEIGHT="20"><br></a>
				<a href="/<%=storeDir%>/public/en/tour/index.jsp" onmouseover="change('pub_nav5','on')" onmouseout="change('pub_nav5','off')"><img src="<%=ip%>/pub_nav5off.gif" border="0" name="pub_nav5" WIDTH="144" HEIGHT="20"><br></a>
				<a href="/<%=storeDir%>/public/en/contacts/index.jsp" onmouseover="change('pub_nav6','on')" onmouseout="change('pub_nav6','off')"><img src="<%=ip%>/pub_nav6off.gif" border="0" name="pub_nav6" WIDTH="144" HEIGHT="21"></a>
				
				</td>
				<td bgcolor="#003333" width="1"><img src="<%=ip%>/spacer.gif" height="1" width="1"></td>
				<td width="449" valign="top" colspan="3">
				<div class="centermargin">
					<p><br><b>Your request has been sent to Cleanwise.  We will contact you shortly.</b></p>
						<form method="post" action="/<%=storeDir%>/index.html">
						<table cellspacing="0" cellpadding="0" border="0">
                          <tr>
								<td class="texttext" align="right">First Name:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="firstName"/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Last Name:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="lastName"/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Address Line 1:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="address1"/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Address Line 2:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="address2" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">City:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="city" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">State:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="state" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Zip Code:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="zip" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Phone:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="phone" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Fax:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="fax" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Email:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="fromEmail" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">FEDSTRIP #:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="fedstrip" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Type of Facility:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="facility" /></td>
							</tr>
							<tr>
								<td class="texttext" align="right">How Can We Help You?:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="help" /></td>
							</tr>													
							<tr>
								<td class="texttext" align="right">Comments:</td>
								<td>&nbsp;</td>
								<td><bean:write name="CONTACT_US_FORM" property="comments" /></td>
							</tr>			
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr> 
							<tr>
								<td class="texttext" align="right"><a href="/<%=storeDir%>/public/en/aboutus/usps-faq.html">Return to Faq</a><br>
                                </td>
								<td>&nbsp;</td>
								<td class="texttext"><a href="/<%=storeDir%>/index.jsp">Return to Cleanwise Home page</a></td>
							</tr>
						</table>
						</form>
				</div></td>
				<td bgcolor="#003333" width="1"><img src="<%=ip%>/spacer.gif" height="1" width="1"></td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td bgcolor="#003333" width="1"><img src="<%=ip%>/spacer.gif" height="1" width="1"></td>
				<td width="144"><img src="<%=ip%>/spacer.gif" height="1" width="144"></td>
				<td bgcolor="#003333" width="1"><img src="<%=ip%>/spacer.gif" height="1" width="1"></td>
				<td width="449"><img src="<%=ip%>/spacer.gif" height="1" width="449"></td>
				<td bgcolor="#003333" width="1"><img src="<%=ip%>/spacer.gif" height="1" width="1"></td>
				<td width="175"><img src="<%=ip%>/spacer.gif" height="1" width="175"></td>
				<td bgcolor="#003333" width="1"><img src="<%=ip%>/spacer.gif" height="1" width="1"></td>
			</tr>
			<tr>
				<td colspan="7" width="772"><img src="<%=ip%>/sec_contactfooter_usps.gif" WIDTH="772" HEIGHT="14"></td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="769">
			<tr>
				<td class="smalltext"><br><div class="fivemargin">© 2000 Cleanwise, Inc. All Rights Reserved. All content and images contained on this web	site are the intellectual property of Cleanwise, Inc. Any party	making unauthorized use	or copies of the content or images on this web site risks prosecution under applicable federal and state	law.</div></td>
			</tr>
		</table>
	</body>
</html>
