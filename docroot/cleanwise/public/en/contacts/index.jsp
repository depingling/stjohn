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
				<td colspan="7" width="772"><img src="/<%=storeDir%>/en/images/sec_header.gif" WIDTH="772" HEIGHT="56"></td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
				<td valign="top"><img name="sec_login" src="/<%=storeDir%>/en/images/sec_login.gif" width="144" height="27" border="0" usemap="#m_sec_login"><map name="m_sec_login"><area shape="rect" coords="93,3,133,23" href="/<%=storeDir%>/index.jsp" ></map><br>
				<a href="/<%=storeDir%>/index.jsp"><img src="/<%=storeDir%>/en/images/pub_nav1off.gif" border="0" name="pub_nav1" WIDTH="144" HEIGHT="19"><br></a>
				<a href="/<%=storeDir%>/public/en/aboutus/index.html" onmouseover="change('pub_nav2','on')" onmouseout="change('pub_nav2','off')"><img src="/<%=storeDir%>/en/images/pub_nav2off.gif" border="0" name="pub_nav2" WIDTH="144" HEIGHT="21"><br></a>
				<a href="/<%=storeDir%>/public/en/partners/index.html" onmouseover="change('pub_nav3','on')" onmouseout="change('pub_nav3','off')"><img src="/<%=storeDir%>/en/images/pub_nav3off.gif" border="0" name="pub_nav3" WIDTH="144" HEIGHT="20"><br></a>
				<a href="/<%=storeDir%>/public/en/news/index.html" onmouseover="change('pub_nav4','on')" onmouseout="change('pub_nav4','off')"><img src="/<%=storeDir%>/en/images/pub_nav4off.gif" border="0" name="pub_nav4" WIDTH="144" HEIGHT="20"><br></a>
				<a href="/<%=storeDir%>/public/en/tour/index.jsp" onmouseover="change('pub_nav5','on')" onmouseout="change('pub_nav5','off')"><img src="/<%=storeDir%>/en/images/pub_nav5off.gif" border="0" name="pub_nav5" WIDTH="144" HEIGHT="20"><br></a>
				<a href="/<%=storeDir%>/public/en/contacts/index.jsp"><img src="/<%=storeDir%>/en/images/pub_nav6on.gif" border="0" name="pub_nav6" WIDTH="144" HEIGHT="21"><br></a>
				<a href="directions.html"><img src="/<%=storeDir%>/en/images/sec_subcon1.gif" border="0" WIDTH="144" HEIGHT="22"><br></a>
				</td>
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
				<td width="449" valign="top"><img src="/<%=storeDir%>/en/images/subheader_contactusaddress.gif" WIDTH="449" HEIGHT="40">
				<div class="centermargin">
					<p>We'd love to hear from you.  If you're interested in becoming a customer or partner, or just learning more about Cleanwise, please fill out the form below.  If you're interested in joining the Cleanwise team, please send your resume to recruiter@cleanwise.com.  For all other matters, please feel free to reach us via phone, fax, or email as listed on the <a href="directions.html">Directions Page</a>.</p>
					<p>If you are interested in learning more about Cleanwise, please complete the form below, and we'll be in touch shortly.</p>					
						<html:form name="CONTACT_US_FORM" 
type="com.cleanwise.view.forms.ContactUsForm"
action="public/en/contacts/index.do">

                        <html:hidden property="action" value="send_contactus_msg"/>
                        <html:hidden name="CONTACT_US_FORM" property="toEmail" 
value="ResourceCenter@cleanwise.com"/>

<div align="center" class="errormsg">
<html:errors/>
</div>
						<table cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td class="texttext" align="right">First Name:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" name="CONTACT_US_FORM" property="firstName" 
value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Last Name:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" name="CONTACT_US_FORM" property="lastName" 
value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Title:</td>
								<td>&nbsp;</td>
								<td><html:select name="CONTACT_US_FORM" property="title">
		                              <html:option value="Executive Housekeeper"></html:option>
	                                  <html:option value="Operations Manager"></html:option>
                                      <html:option value="Supervisor"></html:option>
                                      <html:option value="Other"></html:option> 
		                            </html:select>
								</td>
							</tr>
							<tr>
								<td class="texttext" align="right">Company Name:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="100" name="CONTACT_US_FORM" property="companyName" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Industry</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="industry" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Address Line 1:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="address1" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Address Line 2:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="address2" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">City:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="city" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">State:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="state" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Zip Code:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="zip" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Phone:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="phone" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Email:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="fromEmail" value=""/></td>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr> 
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><html:link href="javascript:{document.CONTACT_US_FORM.submit();}"><input type="image" src="/<%=storeDir%>/en/images/cw_go.gif" width="33" height="18" border="0" name="Send">
</html:link> </td>
							</tr>
						</table>
						</html:form>
				</div></td>
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
				<td width="175" valign="top">
				<img src="/<%=storeDir%>/en/images/sidebar_contacthome.gif" WIDTH="175" HEIGHT="130"><br>
				<img src="/<%=storeDir%>/en/images/sec_belowquote.gif" WIDTH="175" HEIGHT="98"><br>
				</td>
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
				<td width="144"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="144"></td>
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
				<td width="449"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="449"></td>
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
				<td width="175"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="175"></td>
				<td bgcolor="#003333" width="1"><img src="/<%=storeDir%>/en/images/spacer.gif" height="1" width="1"></td>
			</tr>
			<tr>
				<td colspan="7" width="772"><img src="/<%=storeDir%>/en/images/sec_contactfooter.gif" WIDTH="772" HEIGHT="14"></td>
			</tr>
		</table>
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="769">
			<tr>
				<td class="smalltext"><br><div class="fivemargin">© 2000 Cleanwise, Inc. All Rights Reserved. All content and images contained on this web	site are the intellectual property of Cleanwise, Inc. Any party	making unauthorized use	or copies of the content or images on this web site risks prosecution under applicable federal and state	law.</div></td>
			</tr>
		</table>
	</body>
</html>
