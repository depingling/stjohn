<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:setLocaleAndImages/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<style type="text/css">
td.mainheadernavoveride {

    background-color: #FFFFFF;

    border-top: solid 1px #000000;

    padding-left: 0em;

    padding-right: 0em;

    padding-top: 0em;

    padding-bottom: 0em;

}





a.mainheadernavoveride:link, a.mainheadernavoveride:visited, a.mainheadernavoveride:active {

    TEXT-DECORATION: none;

    font-size: 10px;

    font-weight: bold;

    letter-spacing: -0.05em;

    color: #006699;

}





img.mainheadernavoveride {

    border-bottom: solid 1px #000000;

    padding-left: 0em;

    padding-right: 0em;

}



img.mainheadernavoveridespacer, td.mainheadernavoveridespacer {

    border-top: solid 1px #69698E;

    padding-left: 0em;

    padding-right: 0em;

    padding-top: 0em;

    padding-bottom: 0em;

}



table.mainheadernavoveride {

    border-collapse: collapse;

}
</style>

<script language="JavaScript" type="text/JavaScript">
<!--

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

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>



  <html:form action="userportal/logon.do" focus="j_username" onsubmit="return validate(this)">


</head>

<table align="center" border="0" cellpadding="3" cellspacing="0" width="800">
  <tbody>
    <tr align="center">
      <td colspan="3" bgcolor="#ff9900" align="right">
        <table>
        <tr>
        <td width="80">&nbsp;</td>
        <td align="Center" width="490">
        <img src='<%=ClwCustomizer.getSIP(request,"select_supplies_ani.gif")%>' border="0">
        </td>
        <td align="right" width="80">
        <app:storeMessage key="login.label.username"/><br>
        <html:text name="LOGON" property="j_username" size="15" maxlength="255" tabindex="1"/><br>
        <app:storeMessage  key="login.label.password"/><br>
        <html:password property="j_password" size="15" maxlength="255" tabindex="2"/><br>
        <input type="hidden" name="PageVisitTime"
                value="<%=new java.util.Date()%> ">
        <input type='submit' property="action" class='loginSubmit'
                 value='<app:storeMessage key="global.action.label.submit" />' tabIndex="3"/>
        </td></tr></table>
      </td>
    </tr>
    <tr align="center">
      <td colspan="3" align="left" bgcolor="#FFFFFF"> <table width="650" border="0" align="center" cellpadding="5" cellspacing="0">
          <tr>
            <td><p align="center"><font size="3" face="Arial, Helvetica, sans-serif">
                <strong><font color="red"><html:errors/><br></font></strong>
                <bean:define id="currUser"  type="java.lang.String" name="LOGON" property="j_username"/>



                <strong>A
                web-based procurement tool designed to help Building Service Contractors<br>
                better manage their business and reduce overall product spend.</strong></font></p>
              </td>
          </tr>
          <tr>
            <td><p align="center"><font size="2" face="Arial, Helvetica, sans-serif"><br>
                Select Supplies was built to address the specific needs of Building
                Service Contractors (BSCs).</font></p>
              <ul>
                <li><font size="2" face="Arial, Helvetica, sans-serif">Enjoy the
                  benefits of product standardization across your operation -
                  such as facilitated training, improved regulatory compliance,
                  and consistent performance results.</font></li>
                <li><font size="2" face="Arial, Helvetica, sans-serif">Stay under
                  budget by putting in place the spending rules that are important
                  to you, and then manage only the exceptions.</font></li>
                <li><font size="2" face="Arial, Helvetica, sans-serif">Look at
                  your spending habits from a variety of levels - by job site,
                  by manufacturer, by product, versus budget - and find more ways
                  to save money.</font></li>
              </ul>
              <table class="mainheadernavoveride" id="HelpTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr valign="top" align="center">
    <td><img src="<%=ClwCustomizer.getSIP(request,"/cw_globalleft.gif")%>" border="0" WIDTH="8" HEIGHT="78"></td>

      <td class="mainheadernavoveride">

              <img  src="<%=ClwCustomizer.getSIP(request,"/ss_order.jpg" )%>" WIDTH="100" HEIGHT="50"  border="0" name="cw_globalspacer"/>
               <app:storeMessage key="template.xpedx.apple.menu.main.placeOrder"/>

      </td>

   <td class=mainheadernavoveride >

            <img src="<%=ClwCustomizer.getSIP(request,"/ss_reporting.jpg")%>" border="0" name="cw_global3" WIDTH="96" HEIGHT="49">
            <app:storeMessage key="template.xpedx.apple.menu.main.report"/>

    </td>

     <td class=mainheadernavoveride>

            <img src="<%=ClwCustomizer.getSIP(request,"/ss_product.jpg")%>" border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
            <app:storeMessage key="template.xpedx.apple.menu.main.productInfo"/>

    </td>



      <td class=mainheadernavoveride>

          <img src="<%=ClwCustomizer.getSIP(request,"/ss_green.jpg")%>" border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
              <app:storeMessage key="template.xpedx.apple.menu.main.green"/>

      </td>


    <td class=mainheadernavoveride>

            <img  src="<%=ClwCustomizer.getSIP(request,"/ss_training.jpg")%>" border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
            <app:storeMessage key="template.xpedx.apple.menu.main.training"/></a>
    </td>


      <td class=mainheadernavoveride>
               <img  src="<%=ClwCustomizer.getSIP(request,"/ss_resources.jpg")%>" border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
               <app:storeMessage key="template.xpedx.apple.menu.main.companyResources"/>
       </td>

   <td class=mainheadernavoveride>

        <img  src="<%=ClwCustomizer.getSIP(request,"/ss_msds.jpg")%>" border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
        <app:storeMessage key="template.xpedx.apple.menu.main.msds"/>
    </td>

    <td><img src="<%=ClwCustomizer.getSIP(request,"/cw_globalright.gif")%>" border="0" WIDTH="8" HEIGHT="78"/></td>
  </tr>

                  <tr align="center" valign="top">
                    <td>&nbsp;</td>
                    <td bgcolor="#E6E6E6"><font color="#000000" size="1" face="Arial, Helvetica, sans-serif" align="left"><strong><font color="#FF9900"><br>
                      <font color="#000000">Place an order</font></font></strong>
                      for the items your company needs on a regular basis</font></td>

                    <td bgcolor="#E6E6E6"><font color="#000000" size="1" face="Arial, Helvetica, sans-serif" align="left"><strong><font color="#FF9900"><br>
                      <font color="#000000">Generate reports</font></font></strong>
                      to maintain your budget and monitor product usage</font></td>

                    <td bgcolor="#E6E6E6"><font color="#000000" size="1" face="Arial, Helvetica, sans-serif"><br>
                      Research <strong>product information</strong> and specifications
                      to determine proper product usage and selection</font></td>

                    <td bgcolor="#E6E6E6"><font color="#000000" size="1" face="Arial, Helvetica, sans-serif"><br>
                      Learn how <strong>green cleaning</strong> can work for your
                      facility&#8211;and the environment</font></td>

                    <td bgcolor="#E6E6E6"><font color="#000000" size="1" face="Arial, Helvetica, sans-serif"><br>
                      Access <strong>tools and training materials</strong> to
                      permit the proper and efficient use of your supplies.
                      </font></td>

                    <td bgcolor="#E6E6E6"><font color="#000000" size="1" face="Arial, Helvetica, sans-serif"><br>
                      Refer to <strong>company resources</strong> specific to
                      your organization</font></td>

                    <td bgcolor="#E6E6E6"><font color="#000000" size="1" face="Arial, Helvetica, sans-serif"><strong><font color="#FF9900"><br>
                      <font color="#000000">Material Safety Data Sheets</font></font></strong><font color="#000000">
                      </font>and relevant information that you and your employees
                      require to stay safe </font></td>

                  </tr>
                </tbody>
              </table>
              <p align="center"><font size="2" face="Arial, Helvetica, sans-serif"><strong>Contact
                your local xpedx Facility Supplies sales professional to learn
                more about how <br>
                Select Supplies can help your operation be more profitable. </strong></font></p>
              <p align="center"><font size="2" face="Arial, Helvetica, sans-serif">
                Need a local contact? Call Michael Feenan, Vice President - Marketing, at 513 965-2933.
                </font><font size="2" face="Arial, Helvetica, sans-serif"><br>
                </font> </p>
              </td>
          </tr>
        </table></td>
    </tr>
    <tr align="center">
      <td colspan="3" bgcolor="#FFFFFF"></td>
    </tr>
    <tr align="center" valign="middle">
      <td width="400" align="center" bgcolor="#000000"> <p><font color="#CCCCCC" face="Arial, Helvetica, sans-serif" size="1">
          &copy; 2007 xpedx, an International Paper Company. All Rights Reserved.</font>
        </p></td>
      <td bgcolor="#000000">&nbsp;</td>
      <td width="200" align="center" bgcolor="#000000"><img src="<%=ClwCustomizer.getSIP(request,"xpedx_white.jpg")%>" ></td>
    </tr>
</table>

  </html:form>
</div>
