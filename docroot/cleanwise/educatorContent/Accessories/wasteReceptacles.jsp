
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
			    <td class="text"><span class="subheaders">Waste Receptacles</span>

<p>
In order to manage waste efficiently, you need to have a system that accommodates your specific volume, area, budget and type of waste. With evolving technology and laws governing waste storage and removal, purchasing the right receptacle is important. Today there are many choices in waste receptacles ranging from indoor/outdoor, round/square, different colors, different lids, wheels versus no wheels, recycling, and the list goes on.  Consider the following when choosing your waste receptacles:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#volume">Volume</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#recyc">Recycling</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#dollie">Dollies & wheels</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#outdoor">Outdoor use</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#indoor">Indoor use</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#smoking">Smoking material disposal</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#materials">Materials</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#theft">Theft</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#volume"><b>Waste Receptacles:  Volume</b></a></span><br><br><span class="text">
If your facility generates a considerable amount of refuse, it is recommended to use larger capacity waste receptacles rather than small volume containers. The maintenance and cost associated with smaller receptacles, e.g. the constant emptying and relining in high volume areas can be burdensome and easily avoided by using the correct size container for the job. 
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
<a name="#recyc"><b>Waste Receptacles:  Recycling</b></a></span><br><br><span class="text">
As more and more companies begin to adopt recycling programs, there is a strong demand for containers that make the job easier. There is now an assortment of recycling containers, boxes, and other receptacles designed for the small business and the large corporation. Consider large 41-quart containers for areas near a copy machine that sees heavy volume. Think about medium 28-quart containers near computer printers. For desk side recycling a small 13-quart container makes it simple to separate waste paper. Big companies that perform their own central collection or shipping should consider using large capacity containers e.g. 44-gallon models with dollies or cube trucks (large wheeled-containers that have ½ a cubic yard to 16 cubic yard capacities) for better maneuverability.  Remember when establishing a recycling program to select containers that are specifically designed for recycling the specific waste in question.  For example, the blue containers, with the 'recycle logo' are clearly marked and are easily recognized by both workers and customers as being for plain paper.  Taking advantage of these conventions will limit contamination of the wrong types of recyclables in a particular container.  This will save labor and cost.
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
<a name="#dollie"><b>Waste Receptacles:  Dollies & wheels</b></a></span><br><br><span class="text">
There are a host of different receptacles for every job. If you need large capacity utility containers consider 55 gallon or larger receptacles. Depending upon the waste you collect, these larger containers can be unwieldy to move around. To increase mobility, consider a dolly that will enable the custodian to freely transport the receptacle to a refuse dumping site without risking injury. Dollies are typically round-wheeled platforms that thread up into the bottom of the receptacle allowing it to be easily moved from point to point with a minimum of effort. For even larger loads and material handling, consider containers with integrated wheels and hinged lids for ease of unloading and securing contents.
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
<a name="#outdoor"><b>Waste Receptacles:  Outdoor use</b></a></span><br><br><span class="text">
If your requirements dictate the use of outdoor receptacles, consider products that are built to withstand extreme weather conditions for your given locale. Look for containers that are made of man-made materials that will not rust and are abuse resistant. Some models offer specially designed bases that can be filled with either sand or water to help anchor the container to the ground. This feature is especially helpful in windy areas and adds a level of security.
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
<a name="#indoor"><b>Waste Receptacles:  Indoor use</b></a></span><br><br><span class="text">
For inside areas such office building lobbies, restrooms and shopping malls, consider sleeker models that fit the facility image.  Some containers are available with stylish finishes such as stainless steel, and powder coat.  There are many different styles of containers ranging from square, to round, to half round. Just as there many types receptacles, there is also a wide range of choices when it comes to lids. In this day and age of the war on germs, there are no-touch lids that allow refuse to be discarded without having to physically touch the receptacle, minimizing the transmission of bacteria. Make sure to check the guidelines regarding compliance with local fire codes.
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
<a name="#smoking"><b>Waste Receptacles:  Smoking material disposal</b></a></span><br><br><span class="text">
While the number of smokers and designated smoking areas has diminished over the years, there is still a demand for receptacles that provide proper disposal of smoking materials. There are a variety of styles for indoor and outdoor use. There are containers made exclusively for smoking materials and also hybrid models that incorporate built-in ashtrays as well as space for conventional refuse. For inconspicuous low profile models in hallways and lobbies consider wall mounted self-closing units. 
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
<a name="#materials"><b>Waste Receptacles:  Materials</b></a></span><br><br><span class="text">
Waste receptacles are available in a variety of materials including fiberglass, stainless steel, aluminum, plastic, and steel with stone panels.  Most of these are for use both indoors and outdoors with the exception of aluminum and plastic, which will last longer if reserved for indoor use.  If using a waster receptacle for outdoor use, make sure that it is corrosion resistant for longer life.
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
<a name="#theft"><b>Waste Receptacles:  Theft</b></a></span><br><br><span class="text">
Be certain to consider issues such as potential vandalism and theft if containers need to remain in areas open to public access.  Many waste receptacles have locking mechanisms.  
</p>


</td></tr>
</table>

          