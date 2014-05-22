
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>




<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>





      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          <td class="smalltext" valign="up" width="20%"></td>
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" class="text">
              <tr>
			    <td>
                <span class="subheaders">Floor Care Products:  Finish</span><br>
				<p>
				  We all want our floors to have a mirror-like gloss we can see ourselves in.  But creating a floor care program and maintaining a great looking floor involves developing a solid ongoing maintenance program backed by the right combination of products that perform.  The success of the program will depend on how well each component fits within the entire system.  To create the right floor care system for a particular floor, be sure to consider each of the following areas:  
				</p>
				<div class="indent">
                  <a href="<%=currUri%>section=appearanceStandards">Appearance standards</a><br>
                  <a href="<%=currUri%>section=machines">Machines</a><br>
                  <a href="<%=currUri%>section=maintenanceFreq">Maintenance frequency</a><br>
				  <a href="<%=currUri%>section=typeFlooring">Types of flooring</a><br>
				  <a href="<%=currUri%>section=choosing">Choosing the right system</a><br>
				  <a href="<%=currUri%>section=typeFinish">Types of finish</a>
				</div>
<br><br>
</td></tr>
</table>

          