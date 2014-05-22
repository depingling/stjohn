<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<bean:define id="AccountId" type="java.lang.String" name="pages.account.id"/>
<%CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>

<logic:notPresent name="pages.contactus.text">
    <table border="0" cellspacing="0" cellpadding="0" width="490">
        <tr>
            <td width="30%">&nbsp;</td>
            <td width="70%">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
        </tr>
      <bean:size id="size" name="<%=Constants.APP_USER%>" property="contactUsList"/>
            
            <tr>
                    <td colspan="2"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            
            
            
            
            <tr>
                    <td width="30%">&nbsp;</td>
                    <td width="70%" class="text"><br><span class="text">
<p>  

<br>

<%
String countryCode = null;
try{countryCode=Utility.parseLocaleCode(appUser.getUser().getPrefLocaleCd()).getCountry();}catch(Exception e){e.printStackTrace();}
%>

<%
if(appUser.getUser().getPrefLocaleCd() != null && "AU".equals(countryCode)){
%>
Orders that require priority or overnight shipments need to be requested by calling xpedx customer service:<BR>
+1 800 545 1175<BR>
<BR>
During (EST), please contact<BR>
xpedx National Accounts <BR>
9105 Sabal Industrial Blvd <BR>
Tampa, FL 33619<BR>
email:appleservice@xpedx.com <BR>
Phone: +1 800 545 1175 <BR>
<BR>
 <BR>
For emergency shipments when xpedx US closes due to time zones, please contact the following:<BR>
<BR>
AUSFREIGHT GLOBAL LOGISTICS PTY LTD.<BR>
UNIT 1,  6-10 DURDANS AVENUE<BR>
ROSEBERY NSW  2018<BR>
AUSTRALIA<BR>
Contact :Alan Han<BR>
Sydney hours ________<BR>
Telephone:  (61 2) 8339 1811<BR>
Fax: (61 2) 8339  1899<BR>
Email: alan@ausfreightlogistics.com.au<BR>
<BR>
Can't find what you are looking for? Need an item not listed in the catalogue? <BR>
email: retailsupply@apple.com <BR>
<BR>
<BR>
Technical issues with the website should be directed to:<BR>
<BR>
<BR>
xpedx eBusiness support desk<BR>
email: distribution.webmaster@xpedx.com<BR>
Phone: +1 877 269 1784<BR>
<BR>
<%
}else if(appUser.getUser().getPrefLocaleCd() != null && "IT".equals(countryCode)){
%>


Gli ordini che richiedono una spedizione prioritaria o con consegna il giorno lavorativo successivo devono essere effettuati chiamando il Servizio Clienti xpedx al seguente numero: +1 800 545 1175<BR>
<BR>

xpedx National Accounts <BR>
9105 Sabal Industrial Blvd <BR>
Tampa, FL 33619 <BR>
E-mail:appleservice@xpedx.com <BR>
Telefono: +1 800 545 1175 <BR>
<BR>

Per le spedizioni di emergenza durante le ore di chiusura di xpedx USA a causa del fuso orario, contattate:<BR>
Manon Van Aalst  <BR>
Orario dei Paesi Bassi 09:00 - 17:45 <BR>
Telefono: +31 10 494 5 620   
<BR>
<BR>
Non riuscite a trovare quello che state cercando? Vi serve un articolo che non èisponibile nel catalogo? <BR><BR>
Inviate un.e-mail a: retailsupply@apple.com <BR>
<BR><BR>
I problemi tecnici del sito web devono essere segnalati a:<BR>
<BR><BR>
xpedx eBusiness support desk<BR>
E-mail: distribution.webmaster@xpedx.com<BR>
Telefono: +1 877 269 1784<BR>
<BR>
<%}else if(appUser.getUser().getPrefLocaleCd() != null && "CN".equals(countryCode)){%>

要求优先装运或隔夜装运的订单需要通过致电 xpedx 客户服务，电话为:+1-800-545-1175<br>
<br>
上午 <b>8:00 至下午 6:00 (美国)</b>东部时区之间，请联系:<br>
xpedx National Accounts <br>
9105 Sabal Industrial Blvd <br>
Tampa, FL 33619<br>
电子邮件：appleservice@xpedx.com<br> 
电话： +1-800-545-1175 <br>
<br>
对紧急发货的定单，如果时区的不同,xpedx在美国已下班，请与以下联系：<br>
<b>中国仓库联系人</b><br>
<br>
姓名：Violet Gan<br>
职位：上海和苏州仓库总经理<br>
仓库地址：中国上海普陀区真沃路 348 号<br>
电话：+86-21-6627-6715 分机号802<br>
传真：+86-21-6695-0550<br>
电子邮件：violet.gan@fiege.com.cn<br>
营业时间：上午 9:00 至下午 6:00<br>
<br>
<br>
未找到你寻找的结果？ 需要没被列出的产品目录?<br>
电子邮件： retailsupply@apple.com <br>
<br>
对与网站技术问题,请与以下联系:<br>
xpedx eBusiness 支持服务台<br>
电子邮件： distribution.webmaster@xpedx.com<br>
电话：+1-877-269-1784<br>

<br>

<%}else if(appUser.getUser().getPrefLocaleCd() != null && "GB".equals(countryCode)){%>


Orders that require priority or overnight shipments need to be requested by calling xpedx customer service:<BR>
+1 800 545 1175<BR>
<BR>
Between the hours of 13:00 to 18:00, please contact<BR>
xpedx National Accounts <BR>
9105 Sabal Industrial Blvd <BR>
Tampa, FL 33619<BR>
email:appleservice@xpedx.com <BR>
Phone: +1 800 545 1175 <BR><BR>

Between the hours of 9:00 - 13:00<br>
For emergency shipments when xpedx US closes due to time zones, please contact the following:<BR>
Manon Van Aalst  <BR>
Netherlands hours 0900 - 1745  <BR>
Telephone: +31 10 494 5 620  
<BR>
<BR>
 Can't find what you are looking for? Need an item not listed in the catalogue? <BR>
email: retailsupply@apple.com <BR>
<BR><BR>
Technical issues with the website should be directed to:<BR>
<BR><BR>
xpedx eBusiness support desk<BR>
email: distribution.webmaster@xpedx.com<BR>
Phone: +1 877 269 1784<BR>
<BR>
<%}else{%>
<app:storeMessage key="template.xpedx.apple.text.comment1"/><br>
</p>
<app:storeMessage key="template.xpedx.apple.text.contactUs1"/><br>
<app:storeMessage key="template.xpedx.apple.text.contactUs.pNum"/><br>
<%
        String add = ClwI18nUtil.getMessage(request,"template.xpedx.apple.text.contactUs.add",null);
        if(add!=null && add.startsWith("???")) add = ""; 
%>
<%=add%><br>

<br>
<br>
<app:storeMessage key="template.xpedx.apple.text.contactUs2"/><br>
<%
   String retailLink = ClwI18nUtil.getMessage(request,"template.xpedx.apple.text.contactUs.retailLink",null);  
%>
<app:storeMessage key="contactus.text.email:"/>  <a href="mailto:<%=retailLink%>"><%=retailLink%></a>
<br>
<br>
<app:storeMessage key="template.xpedx.text.contactUs2"/><br>
<%
   String webmasterLink = ClwI18nUtil.getMessage(request,"template.xpedx.apple.text.contactUs.webmasterLink",null);  
   String webmasterPhone = ClwI18nUtil.getMessage(request,"template.xpedx.apple.text.contactUs.webmasterPhone",null);  
   if(webmasterPhone!=null && webmasterPhone.startsWith("???")) webmasterPhone = "";
%>
<app:storeMessage key="contactus.text.email:"/> <a href="mailto:<%=webmasterLink%>"><%=webmasterLink%></a><br>
<%if(Utility.isSet(webmasterPhone)){%>
<app:storeMessage key="contactus.text.phone:"/>  <%=webmasterPhone%><br>
<%}%>
<%}%>
                     </td>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
    </table>
</logic:notPresent>
<logic:present name="pages.contactus.text">
    <table border="0" cellspacing="0" cellpadding="0" width="480">
        <tr>
            <td width="30%">&nbsp;</td>
            <td width="70%"class="text"><app:custom pageElement="pages.contactus.text"/></td>
        </tr>
    </table>
</logic:present>
