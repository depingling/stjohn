
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


<tr>
		<td class="smalltext" valign="up" width="20%">
          
            <div class="twotopmargin">
		      <p>

              </p>
          
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" class="text">
              <tr>
			    <td class="text"><span class="subheaders">Finish:  Appearance standards</span>

<p>
The appearance standard is the minimum acceptable look for your facility, or in this case, the floors.  The cleanliness of the floors, their shiny appearance and depth of gloss all make a statement about the appearance standard and your hard work.  But not all appearance standards require a wet looking floor.  Appearance standards are often based on the requirements of your particular facility as well as the type of business you work in.  For example, most hospitals have a higher appearance standard than a public school.  The hospital is driven by infection control requirements, state regulations and patient/visitor expectations.   As a result, a hospital tends to have a larger budget and available labor to keep floors at a very high appearance than the public school.  Sometimes a floor is simply coated with finish to protect the tile from abuse.
</p>
<HR class="selectorrulecolor"></HR>
<p>
Remember that wanting a 'wet looking' floor is one thing, investing the labor and equipment to achieve it is another.  When selecting your floor care program, consider how the maintenance of the floor will affect the cleaning and maintenance needs for your entire building(s).  How much time is available to maintain a particular floor?  How much time are you willing to spend on a floor?  If your floor care program requires so much labor that other areas of the building suffer, it will create a problem for you.  
</p>

<p>
It may be helpful to think of your facility in terms of priorities: 
<ul>
	  <li class="selectorbullet"><span class="text">'A' areas require the highest priority and will get attention at least daily or a couple times a week depending on the traffic.  'A' areas could be lobbies, the first floor corridor and in front of the Presidents office.</span></span></li>
  <li class="selectorbullet"><span class="text">'B' areas require high priority and get attention once a week.  These are highly visible areas of your facility such as the corridors or the perimeter and high traffic areas of a retail store.</span></span></li>
  <li class="selectorbullet"><span class="text">'C' areas are lower in priority and will get attention once every 7-10 days:  these areas need to look good but not at the cost of the 'A & B' areas.  'C' areas may be lower in traffic and soil load making them a little more forgiving to a lower maintenance standard.</span></span></li>
</ul>
</p>

<p>
Of course traffic, soil loads and weather conditions may require an adjustment to the maintenance schedule for any floor care program.
</p>


<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>

          