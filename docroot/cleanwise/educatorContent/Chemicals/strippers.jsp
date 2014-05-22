
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
                <span class="subheaders">Floor Care:  Strippers</span><br>
                <p>
Floor strippers come in a variety of formulations.  Since some stripper formulations can damage certain floors, be certain to match the stripper to the job and the floor surface being stripped.
<ul>
  <li class="selectorbullet"><span class="text"><a href="#solv">Solvenated</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#non">Non-solvenated</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#no">No-rinse</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#ammo">Ammoniated</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#low">Low Odor</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#spec">Specialty</a></span></li>   
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#solv"><b>Strippers:  Solvenated</b></a></span><br><br><span class="text"> 
These products should not be used on rubber, asphalt, linoleum or asbestos floors since they can cause bleeding and cracking of the flooring surface.  Solvenated strippers can often (but not always) be identified by the words, 'solvenated' in the product description.  
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#non"><b>Strippers:  Non-solvenated</b></a></span><br><br><span class="text"> 
These products can usually be used on almost any floor that is safe to strip.  When choosing these products, look for products that contain the words 'non-solvenated' in the product description.  
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#no"><b>Strippers:  No-Rinse</b></a></span><br><br><span class="text"> 
These strippers can save labor since they are marketed as not requiring the usual flood rinse.  They do require a damp mop rinse and most folks will flood rinse as well.  A no-rinse stripper is easily identified by the words, 'no-rinse' in the product description as well as other words such as 'speed stripper', 'labor saver'.  
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#ammo"><b>Strippers:  Ammoniated</b></a></span><br><br><span class="text"> 
These are strippers that obviously have ammonia in them.  Remember the good old days when a stripper had to have a strong ammonia smell if it was going to have any chance of getting the job done?  Well, those days are gone.  Ammoniated strippers can still be used.  But, there are many strippers on the market today that will do just as good, if not a better job at stripping the finish, without the ammonia and the smell that accompanies it.
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#low"><b>Strippers:  Low Odor</b></a></span><br><br><span class="text"> 
These are strippers that are ideal for hospitals, nursing home, or any other facility where the floors are being stripped while patients are around.  Low odor strippers are not odor free but they tend to be much more pleasant to use, not only for the worker, but also for everyone else around.  However, be prepared to pay a higher price for the low odor.
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#spec"><b>Strippers:  Specialty</b></a></span><br><br><span class="text"> 
These are strippers that fall into one of the above categories but are also formulated for a special task such as removing finish that has been ultra high-speed (UHS) burnished.  Specialty strippers are also available for sensitive floors like linoleum.  When choosing from these types of products, determine if the special use for the product will fit your application.  For example, is the stripper made for UHS floors and is the floor being stripped typically burnished with a UHS machine?  If so, then determine if the product is a solvenated stripper, or a non-solvenated stripper and select it only if it is compatible with the floor you need to strip.  
</p>

<br><br>
</td></tr>
</table>

          