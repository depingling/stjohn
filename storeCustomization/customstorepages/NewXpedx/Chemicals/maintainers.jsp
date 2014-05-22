
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
                <span class="subheaders">Floor Care:  Maintainers</span><br>
                <span class="text">The maintainer can help extend the recoat cycle of a floor and restore the gloss to that just finished look.  Since there are many types of maintainers available, choose wisely and be certain to match the maintainer to the finish and the machinery used to polish the floor.
</span>

<ul><li class="selectorbullet"><a href="#vol"><span class="text">Spray Buff products for 175-1500 rpms</span></a></li>
<li class="selectorbullet"><a href="#recyc"><span class="text">Spay Buff products for 1500+ rpms</span></a></li>
<li class="selectorbullet"><a href="#dollies"><span class="text">Mop-On Restorers</span></a></li>
<li class="selectorbullet"><a href="#out"><span class="text">Cleaner/ Maintainers</span></a></li>
</ul>

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="vol">Floor Maintainers:  Spray Buff products for 175-1500 rpms</span></a><br>
Spray buff products for use with floor machines of 175 - 1500 rpms are described as products that remove black marks, fill in light scratches and restore gloss to the floor.  Spray buffs for conventional floor care programs are usually defined as such.  These products will also perform under high speed machines.
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


<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="recyc">Floor Maintainers:  Spray Buff products for 1500+ rpms</span></a><br>
Spray buff products for use with floor machines of 1500+ rpms are also described as products that remove black marks, repair light scratches and restore gloss.  But they also are described as being appropriate for use with high-speed or UHS equipment.
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


<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="dollies">Floor Maintainers:  Mop-On Restorers  </span></a><br>
Mop on restorers are used in high-speed and UHS floor care programs.  When selecting this product, look for key words such as restores the 'wet look', 'mop on and burnish to restore gloss'.  
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


<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="out">Floor Maintainers:  Cleaner/ Maintainers</span></a><br>
Cleaner/ Maintainers are products that can clean a light soil load and will leave a wax emulsion behind that will pop the gloss on the floor once it is burnished.  Cleaner maintainers can be used in high-speed and UHS floor care programs.  When choosing these products, look for descriptions such as 'can be applied either by mop and bucket or autoscrubber', cleans and maintains in one step.  But only use a product in the autoscrubber if the product specifically suggests its use there.
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

<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>
       
          