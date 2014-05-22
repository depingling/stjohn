
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
			    <td>
                <span class="subheaders">Finish:  Machines</span><br>
                <span class="text">Before any chemicals are selected, inventory the floor care equipment and be certain each machine works properly.  Include in the inventory your rotary machines, auto scrubbers and burnishers by rpm and type.  For example:  1500 cord electric, 2000 battery burnishes.  The equipment available to maintain the floors will determine which maintenance techniques can be used.  This will be important since some finishes such as UHS finishes require specific equipment and maintenance in order to achieve a desired look.  If you are interested in increasing the appearance of the floors, it may be necessary to purchase new equipment or learn new techniques.  What ever your decision, your program will fall into the following key maintenance techniques:
</span>

<ul><li class="selectorbullet"><span class="text">UHS burnishing, using a 1500+ rpm machine.</span></li>
<li class="selectorbullet"><span class="text">High-speed burnishing using a 1000-1500 rpm machine.</span></li>
<li class="selectorbullet"><span class="text">Spray buffing, using a 175 -1500+ rpm machine.</span></li>  
<li class="selectorbullet"><span class="text">No burnishing or buffing, a conventional, low maintenance program, requires a 175-swing machine.</span></li> 
<li class="selectorbullet"><span class="text">A combination of techniques throughout the facility depending on the appearance standards and available labor.</span></li> 
</ul>
</p>

<p>Whatever program you use, be sure your floor crew knows how to correctly use both the products and the equipment.  
</p>

<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>
       
          