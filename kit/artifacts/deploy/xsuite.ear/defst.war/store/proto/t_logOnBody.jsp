<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:setLocaleAndImages/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script language="JavaScript1.2">
  function validate(frm) {
  if (frm.j_username.value == "") {
  alert('<app:storeMessage  key="login.errors.username"/>');
  frm.j_username.focus();
  return false;
  }
  if (frm.j_password.value == "") {
  alert('<app:storeMessage  key="login.errors.password"/>');
  frm.j_password.focus();
  return false;
  }
  return true;
  }
</script>

<div class="bodyalt">
  <!-- copied (with some changes) from f_login.jhtml -->

<%--Taken from http://detectmobilebrowser.com/ --%>
<%
String ua=request.getHeader("User-Agent").toLowerCase();
if(ua.matches(".*(android|avantgo|blackberry|blazer|compal|elaine|fennec|hiptop|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile|o2|opera m(ob|in)i|palm( os)?|p(ixi|re)\\/|plucker|pocket|psp|smartphone|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce; (iemobile|ppc)|xiino).*")||ua.substring(0,4).matches("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-")){
 %>
  <br><a href="logon.do?mobile=true">View mobile version</a><br>
<%} %>

  <html:form action="userportal/logon.do" focus="j_username" onsubmit="return validate(this)">


    <!--  <input type=hidden name="portalCode" value="1">  -->
    <br><br>

    <table align="center" border="0" cellspacing="0" cellpadding="0" width="422">
      <tr>
        <td height="1" width="7"><img src="../<%=ip%>images/cw_logintopleft.gif" WIDTH="7" HEIGHT="1"></td>
        <td class="changeshippingdk" height="1" width="408"><img src="../<%=ip%>images/spacer.gif" height="1" width="408"></td>
        <td height="1" width="7"><img src="../<%=ip%>images/cw_logintopright.gif" WIDTH="7" HEIGHT="1"></td>
      </tr>
      <tr>
        <td width="7"><img src="../<%=ip%>images/cw_loginleftlogo.gif" WIDTH="7" HEIGHT="70"></td>
        <td align="left"  width="408">
        <table align="left" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td colspan="2"><img src='<app:custom  pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0"></td>
        </tr>
        </table>
        </td>
        <td width="7"><img src="../<%=ip%>images/cw_loginrightlogo.gif" WIDTH="7" HEIGHT="70"></td>
      </tr>
      <tr>
        <td width="7"><img src="../<%=ip%>images/cw_logincontentleft.gif" WIDTH="7" HEIGHT="204"></td>
        <td width="408" valign="middle">
          <table align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2"></td>
            </tr>
            <tr>
              <td colspan="2"><span class="subheadergeneric">
                <app:storeMessage key="login.label.title"/>
              </td>
            </tr>
            <tr>
              <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
              <td class="text"><app:storeMessage key="login.label.username"/></td>
              <td><html:text name="LOGON" property="j_username" size="15" maxlength="45" tabindex="1"/></td>
            </tr>
            <tr>
              <td class="text"><app:storeMessage  key="login.label.password"/></td>
              <td><html:password property="j_password" size="15" maxlength="20" tabindex="2"/></td>
            </tr>
            <tr>
              <td colspan="2">&nbsp;</td>
            </tr>
            <tr>

              <td colspan="2">
              <input type='submit' property="action" class='loginSubmit'
                 value='<app:storeMessage key="global.action.label.submit" />' tabIndex="3"/>              

                <input type="hidden" name="PageVisitTime" 
                value="<%=new java.util.Date()%> ">
              </td>
            </tr>

              
            <tr><td colspan=2>
              <br><html:errors/> <br>
              <bean:define id="currUser"  type="java.lang.String" name="LOGON" property="j_username"/>
              <% if (currUser.length() > 0) {  %>
              <logic:present name="LoginFailureCount">
                <logic:greaterThan name="LoginFailureCount" value="0">
                  <a href="pwdAccess.do?userName=<%=Utility.encodeForURL(currUser)%>&action=send" tabIndex="99">[<app:storeMessage key="login.text.forgotPasswordQst"/>]</a><br><br>
                </logic:greaterThan>
              </logic:present>
              <% } // End of user name length check. %>
            </td>
            </tr>

            <tr>
              <td colspan="2"></td>
            </tr>
          </table><br>
        </td>
        <td width="7"><img src="../<%=ip%>images/cw_logincontentright.gif" WIDTH="7" HEIGHT="204"></td>
      </tr>
      <tr>
    <tr>
    <bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
    <td>
     <img src="<%=IMGPath%>/cw_left_footer_shop.gif" ALIGN="top">
    </td>
    <td>
     <img src="<%=IMGPath%>/cw_middle_footer_shop.gif" ALIGN="top" width="408" height="8">
    </td>
    <td>
     <img src="<%=IMGPath%>/cw_right_footer_shop.gif" ALIGN="top">
    </td>
    </tr>
      </tr>
      <tr>
        <td class="smalltext" colspan="3"><br><div class="fivemargin"><app:custom pageElement="pages.footer.msg"/></div></td>
      </tr>
    </table>

  </html:form>
</div>
