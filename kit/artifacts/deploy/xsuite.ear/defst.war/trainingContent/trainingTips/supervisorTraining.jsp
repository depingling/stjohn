
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
currUri = SessionTool.removeRequestParameter(currUri,"section");
%>
<bean:define id="section" value="training_tips.jsp" type="java.lang.String" toScope="session"/>




      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          
          <td>
            <table align="center" border="0" cellpadding="15" cellspacing="0" width="100%" class="text"><tr>
 <td>

<span class="trainingsubhead">Training for the Supervisor</span><br>
As a supervisor, not only are you responsible for the day-to-day operation of your area, you also have responsibilities for other areas that will ensure a smooth running operation.  While some of these topics are unpleasant they do require attention.  Here are some areas you may want more information on:
<ul>
<li class="trainingbullet"><span class="text">Coaching Employees to Desired Job Performance</span></li>
<li class="trainingbullet"><span class="text">Firing Employees</span></li>
<li class="trainingbullet"><span class="text">Hiring New Employees</span></li>
<li class="trainingbullet"><span class="text">Train the Trainer:  Tips for Training Employees</span></li>
</ul>

<p class="text">Need more information and tools for these important topics?  Return to the Training Center and select the Step-by-Step Cleaning Practices button for a variety of training information and support.
</p>
<p class="text">Still looking for more information, click here to visit the Building Service Contractors Association International's online store for training tools: <a href="http://www.bscai.org">www.bscai.org.</a>
</p>

 <table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Note:  If you decide to visit the BSCAI web site, you will be leaving our site.  Why not put us on your favorites list now so it will be easy to return!</span></td></tr>
          </table>

</td></tr>
</table>
		

