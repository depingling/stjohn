<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:setLocaleAndImages/>
<style>
    .Text
    {
        font-family:arial;
        font-size: 11px;
        color: #FFFFFF;
        font-weight:bold;
    }
    .TextMsg
    {
        font-family:arial;
        font-size: 11px;
        color: #AF0C01;
        font-weight:bold;
    }
    .pageBG
    {
        background-image:url(<%=ClwCustomizer.getSIP(request,"loginBG.jpg")%>);
        background-repeat:no-repeat;
    }
    .TextBox
    {
        font-family:arial;
        font-size: 11px;
        color: #000000;
        border:1px solid #999999;
    }
    .Footer
    {
        font-family:arial;
        font-size: 10px;
        color: #000000;
    }
</style>
<script language="javascript">
    function doLogin() {
        var sUser = "";
        var sPass = "";
        var errList = '';
        var bErr = false;
        var errIntro = 'Please complete the following required field(s):\n\n';
        sUser = document.getElementById("j_username").value;
        sPass = document.getElementById("j_password").value;
        if (sUser == '') {
            ErrList = errList + 'User Name\n';
        }
        if (sPass == '') {
            errList = errList + 'Password\n';
        }
        //process results
        if (errList != '') {
            alert(errIntro + errList);
        }
        else {
            document.getElementById("frmLogin").action = "logon.do"
            document.getElementById("frmLogin").submit();
        }
    }
</script>
<center>
    <html:form action="userportal/logon.do" focus="j_username" >
        <table border="0" cellpadding="0" cellspacing="0" id="TableMain" width="783" height="478">
            <tr valign="top">
                <td align="left" class="pageBG">
                    <table border="0" cellpadding="0" cellspacing="0" id="TableContent">
                        <tr valign="top">
                            <td align="left" colspan="2"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="240" width="1" /></td>
                        </tr>
                        <tr valign="top">
                            <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="1" width="390" /></td>
                            <td align="left">
                                <table border="0" cellpadding="0" cellspacing="0" ID="TableControls">
                                    <tr valign="middle">
                                        <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="1" width="1" /></td>
                                        <td align="left" class="Text">User Name:</td>
                                        <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="1" width="10" /></td>
                                        <td align="left"><html:text name="LOGON" property="j_username" maxlength="255" style="TextBox" tabindex="1"/></td>
                                        <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="1" width="60" /></td>
                                    </tr>
                                    <tr valign="top">
                                        <td align="left" colspan="5"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="10" width="1" /></td>
                                    </tr>
                                    <tr valign="middle">
                                        <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="1" width="1" /></td>
                                        <td align="left" class="Text">Password:</td>
                                        <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="1" width="10" /></td>
                                        <td align="left"><html:password name="LOGON" property="j_password" maxlength="255" style="TextBox" tabindex="2"/></td>
                                        <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="1" width="50" /></td>
                                    </tr>
                                    <tr valign="top">
                                        <td align="left" colspan="5"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="" border="0" height="20" width="1" /></td>
                                    </tr>
                                    <tr valign="top">
                                        <td align="left" colspan="5">
                                            <!--<a href="#" onclick="doLogin();"><img src='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' alt="Submit" border="0" height="25" width="75" /></a>-->
                                            <INPUT TYPE="image" SRC='<%=ClwCustomizer.getSIP(request,"spacer.gif")%>' HEIGHT="25" WIDTH="95" BORDER="0" ALT="Submit" tabIndex="3">
                                            <input type="hidden" name="PageVisitTime" value="<%=new java.util.Date()%> ">
                                        </td>
                                    </tr>
                                    <tr height="25"><td align="left" colspan="5">&nbsp;</td></tr>
                                    <tr valign="middle" align="center">
                                        <td valign="middle" align="center" colspan="5" class="TextMsg">
                                            <html:errors/>
                                        </td>
                                    </tr>
                                    <tr><td align="left" colspan="5">&nbsp;</td></tr>
                                    <tr valign="middle" align="center">
                                        <td valign="middle" align="center" colspan="5" class="TextMsg">
                                            <bean:define id="currUser" type="java.lang.String" name="LOGON" property="j_username"/>
                                            <%
                                            if (currUser.length() > 0)
                                            {
                                            %>
                                            <logic:present name="LoginFailureCount">
                                                <logic:greaterThan name="LoginFailureCount" value="0">
                                                    <a href="pwdAccess.do?userName=<%=Utility.encodeForURL(currUser)%>&action=send" tabIndex="99"><font class="TextMsg">[<app:storeMessage key="login.text.forgotPasswordQst"/>]</font></a>
                                                </logic:greaterThan>
                                            </logic:present>
                                            <%
                                            }
                                            %>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <!--
        <table border="0" cellpadding="0" cellspacing="0" ID="Table3" width="783">
            <tr valign="top">
                <td align="left" class="Footer">&copy; 2008 Connexion. all Rights Reserved. All content and images contained on this web site are the intellectual property of Connexion. Any party making unauthorized use or copies of the content or images on this web site risks prosecution under applicable federal and state law. <a href="http://www.cleanwise.com/privacy.html" class="Footer">Click here</a> to view our privacy policy.</td>
            </tr>
        </table>
        -->
    </html:form>
</center>
