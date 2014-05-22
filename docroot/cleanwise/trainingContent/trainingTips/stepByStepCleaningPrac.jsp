
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
 <tr><td>

<span class="trainingsubhead">How to Clean</span>
<p class="text">Industry statistics say that as much as 90% of your cleaning budget can be spent on labor.  Anything that can make your cleaning staff more efficient while adhering to your cleanliness standards drops money to your bottom line.  To be more efficient, you need standard cleaning practices and a solid training/mentoring program.  Your training program should include:
</p>
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>

<span class="trainingsubhead"><b>General Office Cleaning</b></span></hr>
<ul><li class="trainingbullet"><span class="text">Zone Cleaning v. Team Cleaning</span></li>
<li class="trainingbullet"><span class="text">Trash Removal</span></li>
<li class="trainingbullet"><span class="text">Dust Removal</span></li>
<li class="trainingbullet"><span class="text">Vacuuming</span></li>
<li class="trainingbullet"><span class="text">Restroom Cleaning</span></li>
<li class="trainingbullet"><span class="text">Floor Cleaning</span></li>
<li class="trainingbullet"><span class="text">Specialty Tag On Services You Provide (may be for a special task force)</span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>

<span class="trainingsubhead"><b>Retail Store Cleaning (in addition to above)</b></span>
<ul><li class="trainingbullet"><span class="text">Cleaning Mats</span></li>
<li class="trainingbullet"><span class="text">Stripping and Finishing Floors</span></li>
<li class="trainingbullet"><span class="text">Maintaining Floors</span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>

<span class="trainingsubhead"><b>Healthcare (in addition to above)</b></span>
<ul><li class="trainingbullet"><span class="text">Patient Room Cleaning</span></li>
<li class="trainingbullet"><span class="text">Intensive Care Unit Cleaning</span></li>
<li class="trainingbullet"><span class="text">Emergency Room Cleaning</span></li>
</ul>

<p class="text">Need more information and tools for these important topics?  Return to the Training Center and select the Step-by-Step Cleaning Practices button for a variety of training information and support.
</p>
<p>Still looking for more information, click here to visit the Building Service Contractors Association International's online store for training tools: <a href="http://www.bscai.org">www.bscai.org.</a>
</p>

 <table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Note:  If you decide to visit the BSCAI web site, you will be leaving our site.  Why not put us on your favorites list now so it will be easy to return!</span></td></tr>
          </table>
</td></tr>
</table>

