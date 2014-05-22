<%@ page language="java" %>
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
    <table border="0" cellspacing="0" cellpadding="0" width="480">
        <tr>
            <td width="30%">&nbsp;</td>
            <td width="70%">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
        </tr>
      <bean:size id="size" name="<%=Constants.APP_USER%>" property="contactUsList"/>
      <logic:iterate id="contact" indexId="idx" name="<%=Constants.APP_USER%>" property="contactUsList" type="com.cleanwise.view.utils.ContactUsInfo">
         <%
            String email  = contact.getEmail();
            String busName = contact.getNickName();
            AddressData address = contact.getAddress();
            String pNum = contact.getPhone();
            String fNum = contact.getFax();
            String callHours = contact.getCallHours();
            String name = contact.getContactName();
          %>
            <tr>
                 <td width="30%">&nbsp;</td>
                 <td width="70%" class="text"><span class="subheadergeneric"><%=busName%></span><br>
                    <%if(Utility.isSet(address.getAddress1())){%>
                       <%=address.getAddress1()%><br>
                    <%}%>
                    <%if(Utility.isSet(address.getAddress2())){%>
                       <%=address.getAddress2()%><br>
                    <%}%>
                    <%if(Utility.isSet(address.getAddress3())){%>
                       <%=address.getAddress3()%><br>
                    <%}%>
                    <%if(Utility.isSet(address.getAddress4())){%>
                       <%=address.getAddress4()%><br>
                    <%}%>
                    <%=address.getCity()%>,
                    <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
                    <%=address.getStateProvinceCd()!=null?address.getStateProvinceCd():""%>
                    <%} %>
                    <%=address.getPostalCode()%><br>
                    <%if(Utility.isSet(email)){%>
                       <app:storeMessage key="contactus.text.email:"/> <a href="mailto:<%=email%>"><%=email%></a>
                    <%}%>
                 </td>
            </tr>
            <tr>
                    <td colspan="2"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            <%-- put this bar in the center if there is only one contact us segment, otherwise  it muddles the UI --%>
            <logic:equal name="size" value="1">
            <tr>
                <td>&nbsp;</td>
                <td><img SRC="<%=IMGPath%>/cw_contactbar.gif" WIDTH="100%" HEIGHT="1"></td>
            </tr>
            </logic:equal>
            <tr>
                    <td width="30%">&nbsp;</td>
                    <td width="70%" class="text"><br><span class="subheadergeneric">
                        <%if(name == null){%>
                            <app:custom pageElement="pages.customer.service.alias"/>
                        <%}else{%>
                            <%=name%>
                        <%}%>
                    </span><br>
                    <app:storeMessage key="contactus.text.phone:"/> <%=pNum%><br>
                    <app:storeMessage key="contactus.text.fax:"/>  <%=fNum%><br>
                    <app:storeMessage key="pages.contact.msg" arg0="<%=callHours%>"/><br></td>


            </tr>

            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>

            <%-- put this bar in if we are displaying multiple contactus records and this is not the last entry in the list (i.e. between every one of the entries) --%>
            <logic:greaterThan name="size" value="1">
                <% String scratch = "" + (idx.intValue() +1);%>
                <logic:notEqual name="size" value="<%=scratch%>">
                <tr>
                    <td width="30%">&nbsp;</td>
                    <td width="70%"><img SRC="<%=IMGPath%>/cw_contactbar.gif" WIDTH="100%" HEIGHT="1"></td>
                </tr>
                </logic:notEqual>
            </logic:greaterThan>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
            <tr>
                <td width="30%">&nbsp;</td>
                <td width="70%"><img SRC="<%=IMGPath%>/spacer.gif" WIDTH="100%" HEIGHT="5"></td>
            </tr>
        </logic:iterate>
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


