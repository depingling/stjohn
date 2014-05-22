<%@ page language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%
String displayHelpSection = request.getParameter("displayHelpSection");
String enableShop = "true";//request.getParameter("enableShop");
String shopUrl = request.getParameter("shopUrl");
%>
<script language="JavaScript">
    function goto(url) {
      document.location.href = url;
    }
    
    function paint(obj,bgcolor,linkcolor)
    {
    var objA = document.getElementById(obj);
    var objTD = objA.parentElement;
    objTD.style.background = bgcolor;
    objA.style.background = bgcolor;
    objA.style.color=linkcolor;
    }

 </script>

<STYLE>
td.mainheadernav  {
        background-color: #FFFFFF;
        border-top: solid 1px #69698E;
        padding-left: 0em;
        padding-right: 0em;
        padding-top: 0em;
        padding-bottom: 0em;
}
td.mainheadernav:hover     {
        background-color: #FFFFFF;
        bgcolor: #006699;
        border-top: solid 1px #69698E;
        color: #FFFFFF;
        padding-left: 0em;
        padding-right: 0em;
        padding-top: 0em;
        padding-bottom: 0em;
}
a.mainheadernav:link,a.mainheadernav:visited,a.mainheadernav:active       {
        TEXT-DECORATION: none;
        font-size: 10px;
        font-weight: bold;
        letter-spacing: -0.05em;
        color: #006699;
}
a.mainheadernav:hover       {
        TEXT-DECORATION: none;
        font-size: 10px;
        font-weight: bold;
        letter-spacing: -0.05em;
        color: #D62931;
        background-color: #FFFFFF;
}

img.mainheadernav{
    border-bottom: solid 1px #000000;
    padding-left: 0em;
}
img.mainheadernavspacer,td.mainheadernavspacer{
    border-top: solid 1px #69698E;
    border-bottom: solid 1px #000000;
    padding-left: 0em;
    padding-right: 0em;
    padding-top: 0em;
    padding-bottom: 0em;
}
table.mainheadernav{
        border-collapse: collapse;
}

<!--
 /* Font Definitions */
 @font-face
	{font-family:Helv;
	panose-1:2 11 6 4 2 2 2 3 2 4;
	mso-font-charset:0;
	mso-generic-font-family:swiss;
	mso-font-format:other;
	mso-font-pitch:variable;
	mso-font-signature:3 0 0 0 1 0;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;
	mso-font-charset:204;
	mso-generic-font-family:roman;
	mso-font-pitch:variable;
	mso-font-signature:-1610611985 1107304683 0 0 159 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-unhide:no;
	mso-style-qformat:yes;
	mso-style-parent:"";
	margin:0in;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:"Arial","sans-serif";
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";}
@page Section1
	{size:8.5in 11.0in;
	margin:1.0in 1.25in 1.0in 1.25in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;
	mso-paper-source:0;}
div.Section1
	{page:Section1;}
-->

<!--
 /* Font Definitions */
 @font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
	
 @font-face
	{font-family:Wingdings;
	panose-1:5 0 0 0 0 0 0 0 0 0;}
@font-face
	{font-family:Verdana;
	panose-1:2 11 6 4 3 5 4 4 2 4;}	
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0in;
	margin-bottom:.0001pt;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";}
h2
	{mso-style-link:"Heading 2 Char";
	margin:0in;
	margin-bottom:.0001pt;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";}
h3
	{mso-style-link:"Heading 3 Char";
	margin:0in;
	margin-bottom:.0001pt;
	text-align:center;
	page-break-after:avoid;
	font-size:16.0pt;
	font-family:"Times New Roman","serif";
	font-weight:normal;}
h4
	{mso-style-link:"Heading 4 Char";
	margin:0in;
	margin-bottom:.0001pt;
	text-align:right;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Times New Roman","serif";}
span.Heading2Char
	{mso-style-name:"Heading 2 Char";
	mso-style-link:"Heading 2";
	font-family:"Times New Roman","serif";
	font-weight:bold;}
span.Heading3Char
	{mso-style-name:"Heading 3 Char";
	mso-style-link:"Heading 3";
	font-family:"Times New Roman","serif";}
span.Heading4Char
	{mso-style-name:"Heading 4 Char";
	mso-style-link:"Heading 4";
	font-family:"Times New Roman","serif";
	font-weight:bold;}
.MsoPapDefault
	{margin-bottom:10.0pt;
	line-height:115%;}
@page Section1
	{size:8.5in 11.0in;
	margin:56.7pt 42.5pt 56.7pt 85.05pt;}
div.Section1
	{page:Section1;}
 /* List Definitions */
 ol
	{margin-bottom:0in;}
ul
	{margin-bottom:0in;}
-->

</STYLE>
 <table class="mainheadernav" id="HelpTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr valign="top" align="center">
    <td><img src='<%=ClwCustomizer.getSIP(request,"cw_globalleft.gif")%>' border="0" ></td>
        <td class=mainheadernav>
        <% if (Utility.isTrue(enableShop)){%>
            <a class=mainheadernav href="<%=shopUrl%>">
                <img src='<%=ClwCustomizer.getSIP(request,"purchase3.jpg")%>' border="0" name="cw_global1"  HEIGHT="50"
			    ><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
                ><br><app:storeMessage  key="template.jd.bsc.menu.main.purchuse"/></a>
        <%}else{%>
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global_noshop.gif")%>' border="0" name="cw_global_noshop"  HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br>
		<% } %>	
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/customerAccountManagement.do">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global6off.gif")%>' border="0" name="cw_global6"  HEIGHT="50"/
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><app:storeMessage key="template.jd.bsc.menu.main.report"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/msdsTemplate.do?tabs=productSupportToolBar&display=msdsTemplate">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global3off.gif")%>' border="0" name="cw_global3"  HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><nobr><app:storeMessage key="template.jd.bsc.menu.main.productSupport"/></nobr></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?tabs=trainingToolBar&display=customTechTips">
            <img src='<%=ClwCustomizer.getSIP(request,"training.jpg")%>' border="0" name="cw_global3" HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><app:storeMessage key="template.jd.bsc.menu.main.training"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?tabs=safetyToolBar&display=customsafeprograms">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global5off.gif")%>' border="0" name="cw_global3"  HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><app:storeMessage key="template.jd.bsc.menu.main.safety"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?display=services&tabs=marketingToolBar">
            <img src='<%=ClwCustomizer.getSIP(request,"services.jpg")%>' border="0" name="cw_global3"  HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><app:storeMessage key="template.jd.bsc.menu.main.services"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?display=contacts&tabs=jdSupportToolBar">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global0off.gif")%>' border="0" name="cw_global3"  HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><app:storeMessage key="template.jd.bsc.menu.main.jdSupport"/></a>
    </td>
    <td><img src='<%=ClwCustomizer.getSIP(request,"cw_globalright.gif")%>' border="0" /></td>
  </tr>
</table>
