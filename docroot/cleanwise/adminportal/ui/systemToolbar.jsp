<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir = ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script src="../externals/lib.js" language="javascript"></script>

<%
    /* Get the page being accessed. */
    String pg = request.getRequestURI();
    if (pg.indexOf("related") > 0) {
        pg = " " + request.getParameter("action");
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = appUser.getUserStore().getStoreId();
%>

<%--render the image and the menuing system--%>
<table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>" style="border-collapse: collapse;">
    <tr valign="top">
        <td align="left" valign="middle">
            <img src='<app:custom  pageElement="pages.store.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0">
        </td>
        <%--The navigation bar--%>
        <td align="right" colspan="4">
		    <jsp:include flush='true' page="/general/navMenu.jsp"/>
			<br/>
			<% java.util.Date currd = new java.util.Date(); %>
			<%= currd.toString() %><br/>
			<% try { %>
            <b>Server</b> <%=java.net.InetAddress.getLocalHost()%><br/>
            <% } catch (Exception e) { %>
             <b>Server</b> N/A<br/>
           <% }  %>
            <b>Branch</b> <%=System.getProperty("build.branch.stjohn")%><b>   Build</b> <%=System.getProperty("build.number.stjohn")%>
		</td>
        <%--END The navigation bar--%>
    </tr>
</table>
<br>
<table cellpadding="0" cellspacing="0" width="769">
    <tr align=left>


        <td class="atoff">
            <table cellpadding="3" cellspacing="0" align="left">
                <tr>
                    <%
                        String tabClass = "";
                        String linkClass = "";
                        if (pg.indexOf("tradingPartnerMgr") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>"><a class="<%=linkClass%>"
                                                 href="/<%=storeDir%>/adminportal/tradingpartnermgr.do">Trading
                        Partners</a></td>
                    <%
                        if (pg.indexOf("orderScheduleMgr") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
<%--                    <td class="<%=tabClass%>"><a class="<%=linkClass%>"
                                                 href="/<%=storeDir%>/adminportal/orderschedulemgr.do">Order
                        Scheduler</a></td>--%>
                    <%
                        if (pg.indexOf("autoOrderMgr") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>"><a class="<%=linkClass%>"
                                                 href="/<%=storeDir%>/adminportal/autoordermgr.do">Auto Orders</a></td>

                    <%
                        if (pg.indexOf("edi") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>"><a class="<%=linkClass%>" href="/<%=storeDir%>/adminportal/ediInbound.do">EDI
                        Files</a></td>

                    <%
                        if (pg.indexOf("pipelineStepsMgr") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>"><a class="<%=linkClass%>"
                                                 href="/<%=storeDir%>/adminportal/pipelineStepsMgr.do">Pipeline</a></td>

                    <%
                        if (pg.indexOf("genericRep") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>">
                        <a class="<%=linkClass%>" href="/<%=storeDir%>/reporting/genericRep.do">Generic Reports</a>
                    </td>

                    <%
                        if (pg.indexOf("eventAdm") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>">
                        <a class="<%=linkClass%>" href="/<%=storeDir%>/adminportal/eventAdm.do?action=search">Events</a>
                    </td>

                    <%
                        if (pg.indexOf("inboundFilesAdm") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>">
                        <a class="<%=linkClass%>" href="/<%=storeDir%>/adminportal/inboundFilesAdm.do?action=init">Inbound Files</a>
                    </td>

                    <%
                        if (pg.indexOf("processAdm") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>">
                        <a class="<%=linkClass%>" href="/<%=storeDir%>/adminportal/processAdm.do">Processes</a>
                    </td>

                    <%
                        if (pg.indexOf("domainAdm") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>">
                        <a class="<%=linkClass%>" href="/<%=storeDir%>/adminportal/domainAdm.do">Domains</a>
                    </td>
                </tr>
                <tr>
                    <%
                        if (pg.indexOf("quartzAdm") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>"><a class="<%=linkClass%>"
                                                 href="/<%=storeDir%>/adminportal/quartzAdm.do?action=jobSearch">Quartz</a></td>

                    <%
                        if (pg.indexOf("compassAdm") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>"><a class="<%=linkClass%>"
                                                 href="/<%=storeDir%>/adminportal/compassAdm.do">Compass</a></td>

                      <%
                        if (pg.indexOf("cachecosAdm") == -1) {
                            tabClass = "atoff";
                            linkClass = "tbar2";
                        } else {
                            tabClass = "aton";
                            linkClass = "tbar";
                        }
                    %>
                    <td class="<%=tabClass%>"><a class="<%=linkClass%>"
                                                 href="/<%=storeDir%>/adminportal/cachecosAdm.do?action=management">Cachecos</a></td>
                </tr>
            </table>
        </td>

    </tr>
    <tr>
        <td class="aton" colspan=9>
            <img src="../<%=ip%>images/cw_spacer.gif"
                 HEIGHT="2"></td>
    </tr>
    <%
        Object errors = request.getAttribute("org.apache.struts.action.ERROR");
        if (errors != null) {
    %>
    <tr>
        <td class="genericerror" align="center"><html:errors/></td>
    </tr>
    <%
        }
    %>

</table>

