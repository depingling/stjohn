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
				<td valign="top"><img name="sec_login" src="<%=ip%>/sec_login.gif" width="144" height="27" border="0" usemap="#m_sec_login"><map name="m_sec_login"><area shape="rect" coords="93,3,133,23" href="/<%=storeDir%>/userportal/index.jsp" ></map><br>
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
					<p>Need help or have a specific question? Fill out this form, click "GO" and a Customer Service Representative will be in touch with you shortly. </p>					
						<html:form name="CONTACT_US_FORM" 
type="com.cleanwise.view.forms.ContactUsForm"
action="public/en/contacts/uspshelp.do">

                        <html:hidden property="action" value="send_uspscontactus_msg"/>
                        <html:hidden name="CONTACT_US_FORM" property="toEmail" 
value="resourcecenter@cleanwise.com"/>
						<table cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td class="texttext" align="right">First&nbsp;Name:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" name="CONTACT_US_FORM" property="firstName" 
value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Last&nbsp;Name:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" name="CONTACT_US_FORM" property="lastName" 
value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Address&nbsp;Line&nbsp;1:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="address1" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Address&nbsp;Line&nbsp;2:</td>
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
								<td class="texttext" align="right">Zip&nbsp;Code:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="zip" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Phone:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="phone" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Fax:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="fax" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Email:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="fromEmail" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">FEDSTRIP&nbsp;#:</td>
								<td>&nbsp;</td>
								<td><html:text  size="30" maxlength="40" name="CONTACT_US_FORM" property="fedstrip" value=""/></td>
							</tr>
							<tr>
								<td class="texttext" align="right">Type&nbsp;of&nbsp;Facility:</td>
								<td>&nbsp;</td>
								<td><html:select name="CONTACT_US_FORM" property="facility">
								      <html:option value="-- Select --"></html:option>
		                              <html:option value="Bulk Mail Facility"></html:option>
	                                  <html:option value="Processing Distribution Center"></html:option>
                                      <html:option value="Air Mail Center"></html:option>
                                      <html:option value="Vehicle Maintenance Facility"></html:option> 
                                      <html:option value="General Mail Facility"></html:option> 
                                      <html:option value="Area Offices  (AO)"></html:option> 
		                            </html:select>
								</td>
							</tr>
							<tr>
								<td class="texttext" align="right">How&nbsp;Can&nbsp;We&nbsp;Help&nbsp;You?:</td>
								<td>&nbsp;</td>
								<td><html:select name="CONTACT_US_FORM" property="help">
		                              <html:option value="-- Select --"></html:option>
									  <html:option value="I forgot my username and password"></html:option>
	                                  <html:option value="I want to change my password"></html:option>
                                      <html:option value="I need a username and password"></html:option>
                                      <html:option value="I have a new product request"></html:option>
                                      <html:option value="I need an order guide"></html:option>
                                      <html:option value="I need a catalog"></html:option>
                                      <html:option value="I have a product on backorder, when will it deliver"></html:option>
                                      <html:option value="I would like to order a product that is not on my order guide/catalog"></html:option>
		                            </html:select>
								</td>
							</tr>													
							<tr>
								<td class="texttext" align="right">Comments:</td>
								<td>&nbsp;</td>
								<td><html:textarea cols="30" rows="5" name="CONTACT_US_FORM" property="comments" value=""/></td>
							</tr>							
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr> 
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><html:link href="javascript:{document.CONTACT_US_FORM.submit();}"><input type="image" src="<%=ip%>/cw_go.gif" width="33" height="18" border="0" name="Send">
</html:link> </td>
							</tr>
						</table>
						</html:form>
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
