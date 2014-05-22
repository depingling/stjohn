<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="theForm" name="TRADING_PARTNER_FORM" type="com.cleanwise.view.forms.TradingPartnerMgrForm"/>
<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>System Administrator: Trading Partners</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include flush='true' page="ui/systemToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<bean:parameter id="reqAction" name="action" value="---" />
<bean:define id="trpid" name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" type="java.lang.Integer"/>
<table width="100%" cellspacing="0" border="0" class="mainbody">
<html:form name="TRADING_PARTNER_FORM" action="adminportal/tradingprofilemgr.do" type="com.cleanwise.view.forms.TradingPartnerMgrForm">

    <html:hidden name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" value="<%=trpid.toString()%>"/>
   <tr><td colspan="4">&nbsp;</td>
  </tr>
   <tr>
     <td colspan="4" class="mediumheader"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<% if ( reqAction.equals("edit_profile") ) { %>
Edit Trading Profile
<% } else { %>
New Trading Profile
<% } %>

</b></td>
  </tr>
   <tr>
     <td><b>Authorization Qualifier:</b></td>
     <td>
       <html:select name="TRADING_PARTNER_FORM" property="tradingProfile.authorizationQualifier">
       <html:option value="00">00 - No Authorization Information Present</html:option>
       <html:option value="01">01 - UCS Communications ID</html:option>
       </html:select>
     </td>
     <td><b>Authorization:</b></td>
     <td align="left"><html:text name="TRADING_PARTNER_FORM" property="tradingProfile.authorization" size="10"/>
     </td>
  </tr>
   <tr>
     <td><b>Security Info Qualifier:</b></td>
     <td>
       <html:select name="TRADING_PARTNER_FORM" property="tradingProfile.securityInfoQualifier">
       <html:option value="00">00 - No Security Information Present</html:option>
       <html:option value="01">01 - Password</html:option>
       </html:select>
     </td>
     <td><b>Security Info:</b></td>
     <td align="left"><html:text name="TRADING_PARTNER_FORM" property="tradingProfile.securityInfo" size="10"/>
     </td>
  </tr>
   <tr>
     <td colspan="4" class="subheaders"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Interchange</b></td>
  </tr>
   <tr valign="top">
     <td><b>Interchange Sender Qualifier:</b></td>
     <td>
       01 - Duns Number <br>
       02 - SCAC<br>
       08 - UCC EDI Communications ID<br>
       12 - Telephone Number<br>
       ZZ - Mutually Defined<br>
       <html:text name="TRADING_PARTNER_FORM" property="tradingProfile.interchangeSenderQualifier" size="25" maxlength="25"/>
     </td>
     <td><b>Interchange Sender:</b></td>
     <td><html:text name="TRADING_PARTNER_FORM" property="tradingProfile.interchangeSender" size="25"/>
     </td>
  </tr>
   <tr valign="top">
     <td><b>Interchange Receiver Qualifier:</b></td>
     <td>
       01 - Duns Number <br>
       02 - SCAC<br>
       08 - UCC EDI Communications ID<br>
       12 - Telephone Number<br>
       ZZ - Mutually Defined<br>
       <html:text name="TRADING_PARTNER_FORM" property="tradingProfile.interchangeReceiverQualifier" size="25" maxlength="25"/>
     </td>
     <td><b>Interchange Receiver:</b></td>
     <td><html:text name="TRADING_PARTNER_FORM" property="tradingProfile.interchangeReceiver" size="25" maxlength="25"/>
     </td>
  </tr>
   <tr>
     <td><b>Interchange Standarts ID:</b></td>
     <td>
       <html:select name="TRADING_PARTNER_FORM" property="tradingProfile.interchangeStandardsId">
       <html:option value="U">U - U.S. EDI Community</html:option>
       </html:select>
     </td>
     <td><b>Interchange Version Number:</b></td>
     <td><html:text name="TRADING_PARTNER_FORM" property="tradingProfile.interchangeVersionNum" size="5" maxlength="5"/>
     </td>
  </tr>
   <tr>
     <td><b>Acknowledgment Requested:</b></td>
     <td><html:checkbox name="TRADING_PARTNER_FORM" property="acknowledgment"/>
     </td>
     <td><b>Interchange Control Number:</b></td>
     <td><bean:write name="TRADING_PARTNER_FORM" property="tradingProfile.interchangeControlNum"/>
     </td>
  </tr>
   <tr>
     <td colspan="4" class="subheaders"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Terminator</b></td>
  </tr>
   <tr>
   <td colspan="4">
    <table width="100%" cellspacing="0" border="0" class="mainbody">
    <tr>
     <td><b>Segment Terminator:</b>
     <html:text name="TRADING_PARTNER_FORM" property="segmentTerminator" size="4" maxlength="4"/>(Note: blank here will be interpreted as CRLF)
     </td>
     <td><b>Element Terminator:</b>
     <html:text name="TRADING_PARTNER_FORM" property="elementTerminator" size="4" maxlength="4"/>
     <font color="red">*</font>
     </td>
     <td><b>Sub Element Terminator:</b>
     <html:text name="TRADING_PARTNER_FORM" property="subElementTerminator" size="4" maxlength="4"/>
     <font color="red">*</font>
     </td>
    </tr>
    <tr><td colspan="3">(use any character or CR, LF, TAB or \ followed by character code)</td>
    </tr>
    </table>
    <td>
    </tr>
   <tr>
     <td colspan="4" class="subheaders"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Group</b></td>
  </tr>
   <tr>
   <td colspan="4">
    <table width="100%" cellspacing="0" border="0" class="mainbody">
    <tr>
     <td><b>Group Sender:</b>
     <html:text name="TRADING_PARTNER_FORM" property="tradingProfile.groupSender" size="25" maxlength="25"/>
     </td>
     <td><b>Group Receiver:</b>
     <html:text name="TRADING_PARTNER_FORM" property="tradingProfile.groupReceiver" size="25" maxlength="25"/>
     </td>
     <td><b>Group Control Number:</b>
     <bean:write name="TRADING_PARTNER_FORM" property="tradingProfile.groupControlNum"/>
     </td>
     </tr>
     </table>
     </td>
  </tr>
   <tr>
     <td colspan="4" class="subheaders"><b>&nbsp;</b></td>
  </tr>
   <tr>
     <td><b>Responsible Agency Code:</b></td>
     <td>
       <html:select name="TRADING_PARTNER_FORM" property="tradingProfile.responsibleAgencyCode">
       <html:option value="X">X12</html:option>
       </html:select>
     </td>
     <td><b>Version Number:</b></td>
     <td><html:text name="TRADING_PARTNER_FORM" property="tradingProfile.versionNum" size="12" maxlength="12"/>
     </td>
  </tr>
   <tr>
     <td><b>TimeZone:</b></td>
     <td>
       <html:select name="TRADING_PARTNER_FORM" property="tradingProfile.timeZone">
          <html:options  name="TRADING_PARTNER_FORM" property="timeZoneIDs"/>
       </html:select>

     </td>
  </tr>
  <tr><td>
  	<logic:notEqual name="TRADING_PARTNER_FORM" property="tradingProfile.tradingProfileId" value="0">
        	<html:submit property="action" value="Update Profile" />
	</logic:notEqual>
	<logic:equal name="TRADING_PARTNER_FORM" property="tradingProfile.tradingProfileId" value="0">
        	<html:submit property="action" value="Add Profile" />
	</logic:equal>
	<html:submit property="action">
                <app:storeMessage  key="admin.button.back"/>
        </html:submit>
    </td></tr>

</table>

</html:form>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>


