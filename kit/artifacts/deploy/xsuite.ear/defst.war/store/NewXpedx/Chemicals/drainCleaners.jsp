
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
                <span class="subheaders">Drain Cleaners</span><br>
                <span class="text">Drain cleaners are used to open up drains with a variety of clogs such as hair and grease. They are available in a choice of strengths. Heavy-duty cleaners are used to free drains of bigger clogs while drain maintainers address low-level clogs and keep drains clean and odor free.  Some products are formulated with either 'bacteria' or 'enzymes' to help boost performance.  Both work well, however, some studies have shown that bacteria-based products are more effective.  Here's why.  Bacteria continue to multiply as long as the food supply remains.  When enzymes are used, there is a finite amount of enzymes in the bottle and enzymes cannot reproduce like bacteria do.  Regardless of which product you choose, to keep restrooms and kitchens smelling clean it will help to schedule use of a drain maintainer once a week.  <br>

In order to effectively unblock drains and keep them working properly you will need to assess the situation and decide how severe the clog is and determine if you need to prevent future clogs and eliminate odors.  Consider the following:
</span>

<ul><li class="selectorbullet"><a href="#vol"><span class="text">Low-level to moderate clogs</span></a></li>
<li class="selectorbullet"><a href="#recyc"><span class="text">Tough stubborn clogs</span></li>
<li class="selectorbullet"><a href="#dollies"><span class="text">Preventing future clogs</span></li>
<li class="selectorbullet"><a href="#out"><span class="text">Eliminating drain odors</span></li>
<li class="selectorbullet"><a href="#in"><span class="text">Toilet clogs</span></li>
  
</ul>

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="vol">Drain Cleaners:  Low-level to moderate clog</a><br></span><span class="text">
If your clog is not extensive, and water still proceeds down the drain but at a slow pace, you can use a less powerful cleaner. For moderate clogs you can also use a standard strength cleaner, however; if the clog still persists after an initial application, you may need to use the cleaner again to open the drain.  </span></li></ul>
</p>
</td></tr>
<tr><td align="right">
<div class="searchmargin">
  <a href="#">[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top</a>
</div>
</td></tr>
<tr><td>

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="recyc">Drain Cleaners:  Tough stubborn clogs</a><br></span><span class="text">
When water is backed up in the sink or on the floor, it is time to introduce the heavy-duty drain cleaner. These liquid products power through even the toughest blockages in sinks and drains. When using bleached-based cleaners make sure to follow the directions carefully. You do not want to mix any other chemicals with these chemicals because an unexpected result could occur, creating a dangerous situation.  Check the product information to determine if the product can be used without harming pipes, even if used over night.  If clogged drains are an ongoing challenge, consider establishing an ongoing drain maintenance program by treating all the drains with a drain maintainer product.  It will prevent some future headaches.</span></li></ul>
</p>
</td></tr>
<tr><td align="right">
<div class="searchmargin">
  <a href="#">[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top</a>
</div>
</td></tr>
<tr><td>

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="dollies">Drain Cleaners:  Preventing future clogs </a><br></span><span class="text">
Drain maintainers are good companion products to the heavy-duty cleaners; they help clear low-level obstructions and will also help to prevent future clogs. As a part of a good drain maintenance program, drain maintainers should regularly be added to the drain to keep the restroom smelling clean and keep the drain free flowing.   To help keep restrooms smelling fresh, be sure to include regular drain maintenance as part of the ongoing restroom program.</span></li></ul>
</p>
</td></tr>
<tr><td align="right">
<div class="searchmargin">
  <a href="#">[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top</a>
</div>
</td></tr>
<tr><td>

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="out">Drain Cleaners:  Eliminating drain odors</a><br></span><span class="text">
Some drain maintainers can also be used to eliminate odors in drains and keep them running freely.  However some drain maintainers can also be used to clean areas such as restrooms and dumpsters and do a good job correcting odors caused by vomit, feces, blood, urine, food spills, garbage and grease.  These products work by putting millions of bacteria or enzymes to work digesting the soil load causing the odor. To achieve best results these organisms need to be handled correctly.  Avoid use of hot water to dilute the product, follow the directions for contact time exactly and be careful not to mix these products with any other chemicals otherwise you will kill the enzymes or bacteria and render the product useless.  Use drain maintainers to maintain restroom and kitchen drains, mop sinks, floor and laundry drains, and dishwasher and garbage disposals drains.  It's best to treat the drains at the end of the day or when the water flow is lowest in the facility.</span></li></ul>
</p>
</td></tr>
<tr><td align="right">
<div class="searchmargin">
  <a href="#">[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top</a>
</div>
</td></tr>
<tr><td>

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="in">Drain Cleaners:  Toilet Clogs</a><br></span><span class="text">
If you have a clogged toilet to deal with check with the manufacturer's instructions to be sure the product is safe to use in the toilet.  Some products, typically the more heavy-duty products are not safe to use in the toilet. </span></li></ul>
</p>

</td></tr>
<tr><td align="right">
<div class="searchmargin">
  <a href="#">[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top</a>
</div>
</td></tr>
<tr><td>

<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>
          
