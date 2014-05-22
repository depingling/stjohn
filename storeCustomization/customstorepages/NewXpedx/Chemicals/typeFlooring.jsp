
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
                <span class="subheaders">Finish:  Types of Flooring </span><br>
                <span class="text">Before you determine your floor care program, survey the types of flooring in your facility that will need to be maintained.  There are 2 major classes of flooring that are coated with finish: resilient flooring and hard or non-resilient floors.  Resilient floors are a large class of flooring made up of floor tiles and sheet goods such as:  vinyl composition tiles (VCT), asphalt tiles, linoleum, rubber, vinyl asbestos tiles (VAT), and vinyl or sheet vinyl.  Some floors such as asphalt, asbestos and rubber floors have specific needs.  For example, a solvenated stripper can harm some rubber floors so be sure to consult the 'Troubleshooter' for information on caring for this floor.
</span>

<p>
Hard or non-resilient floors refer to concrete and stone floors such as marble, terrazzo and slate.  Hard floors often have issues such as rough or cracked surfaces as with concrete, or very smooth surfaces, as is the case with polished marble.  Hard floors have no 'give' as the resilient floors do and require products specifically designed to adhere to them.  Once coated with a hard floor sealer, these types of floors can be coated with finish and maintained like a resilient floor.  
</p>


<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>

          