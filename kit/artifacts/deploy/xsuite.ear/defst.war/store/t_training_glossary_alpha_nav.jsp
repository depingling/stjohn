
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.File" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
currUri = SessionTool.removeRequestParameter(currUri,"sub");
%>

<a class="secondaryNavButtonCurr" href="<%=currUri%>sub=A">A</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=B">B</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=C">C</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=D">D</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=E">E</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=F">F</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=G">G</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=H">H</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=I">I</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=J">J</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=K">K</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=L">L</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=M">M</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=N">N</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=O">O</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=P">P</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=Q">Q</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=R">R</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=S">S</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=T">T</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=U">U</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=V">V</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=W">W</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=X">X</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=Y">Y</a><a class="secondaryNavButtonCurr" href="<%=currUri%>sub=Z">Z</a>
