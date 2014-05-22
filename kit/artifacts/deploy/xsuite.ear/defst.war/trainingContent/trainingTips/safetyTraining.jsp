
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
            <table border="0" cellpadding="0" cellspacing="15" width="100%" class="text"><tr>
 <tr><td>
<span class="trainingsubhead">Safety Training</span>
<p class="text">As an employer you are obligated to provide your employees with a safe workplace.  In addition, in the event that an accident should occur, your employee must know how to react.  Safety training will help ensure a safe environment and it will demonstrate that you care about what happens to your employees.  In some cases OSHA (Occupational Safety and Health Administration) regulates training on a particular safety topic.  Each employee should be familiar with:
</p>
<ul><li class="trainingbullet"><span class="text">Basic First Aid</span></li>
<li class="trainingbullet"><span class="text">The Hazard Communication Standard and the safe use of chemicals</span></li>
<li class="trainingbullet"><span class="text">Personal Protective Equipment - where to get it and how to use it</span></li>
<li class="trainingbullet"><span class="text">Safe Handling of Sharps</span></li>
<li class="trainingbullet"><span class="text">Back Injury Prevention</span></li>
<li class="trainingbullet"><span class="text">The Bloodborne Pathogens Standard and the safe handling of bodily fluids and blood</span></li>
<li class="trainingbullet"><span class="text">Working Safely With Electricity</span></li>
<li class="trainingbullet"><span class="text">Trips, Slips and Falls</span></li>
<li class="trainingbullet"><span class="text">Safe Use of Ladders (rather than furniture)</span></li>
<li class="trainingbullet"><span class="text">When to Use Respiratory Protection - dealing with fumes and safe use of equipment</span></li>
</ul>
<p class="text">Need more information and tools for these important topics?  Return to the Training Center and select the Safety and Regulatory button for a variety of training information and support.
</p>
<p class="text">Still looking for more information, click here to visit the Building Service Contractors Association International's online store for training tools: <a href="http://www.bscai.org">www.bscai.org.</a>
</p>
<p class="text">For a complete list of OSHA Standards, visit The National AgSafety Database at 
<a href="http://www.cdc.gov/niosh/nasd/docs2/oacomp.html"> 
http://www.cdc.gov/niosh/nasd/docs2/oacomp.html </a>
</p>
 <table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Note:  If you decide to visit the BSCAI web site, you will be leaving our site.  Why not put us on your favorites list now so it will be easy to return!</span></td></tr>
          </table>
</td></tr>
</table>
		

