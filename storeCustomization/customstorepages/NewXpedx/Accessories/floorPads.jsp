
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
			    <td class="text"><span class="subheaders">Floor Pads</span>

<p>
A floor pad is a floor pad.  Just slop one on the machine and you're off, right?  Wrong!  The right floor pads, believe it or not, can be the key to success in your floor maintenance program.  In fact, floor finish manufacturers often have pad recommendations to help ensure the success of the floor finish.  Whatever you do, when selecting pads, there are a couple of things to consider:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#type">Type of finish</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#speed">Floor machine speed</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#power">Floor machine power type</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#rough">Rough surfaces and grouted surfaces</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#pad">Pad sizes</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#strip">Stripping and scrubbing pads</a></span></li>
    <ul>
      <li class="selectorbullet"><span class="text"><a href="#high">High Productivity Pads</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#black">Black Stripping Pads</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#brown">Brown Pads</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#dark">Dark Blue and dark green pads</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#red">Red Pads</a></span></li>
    </ul>
  <li class="selectorbullet"><span class="text"><a href="#buff">Buffing pads</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#burnish">Burnishing pads</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#type"><b>Floor Pads:  Type of finish</b></a></span><br><br><span class="text">
All finishes respond differently to different pads.  What works for finish X may not work on finish Z.  Not to say that the pad will not be effective but it more than likely will not give you optimum results and in turn make you work harder.  So, know what type of finish you are working on!   Hard finishes and soft finishes require different pads to bring the gloss back.  You can identify a hard finish by its tendency to scratch easily.  Hard finishes will respond better to hair pads.  You can identify a soft finish by its tendency to scuff easily.  Soft finishes will respond better to synthetic pads.  Keep in mind that these are generalizations and may not be true for every finish.
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
<a name="#speed"><b>Floor Pads:  Floor Machine Speed  </b></a></span><br><br><span class="text">
This is any easy one.  Low-speed use red.  High-speed don't use red.   Questions?  
Actually this is basically true.  When using low-speed machines of 175-300 rpms to bring gloss back to your finish, you will almost always use a red pad and a spray buff maintainer.  Machines of speeds 1000 rpms and up are a different story.  Using a red pad with high-speed and ultra high-speed machines to bring gloss back may cause finish to turn pink, the whole dye transfer issue.  In addition, it will be too aggressive on the floor at higher rpms.  So unless pink is the desired look, stay away from red pads with these machines.  For spray buffing with machine speeds of 1000-1500 rpms,  a beige or tan buffing pad is the best option.
</p>

<p>
Ultra high-speed machines are not as clear cut when it comes to choosing the right burnishing pad.  The most popular choices are between synthetic, hair or a combination of both, although there are some coconut and walnut pads on the market too.  Keep in mind that usually, but not always, the lighter the color of the pad, the less aggressive the pad is.  Aggressive pads are not always productive when trying to restore gloss because they can cause scratching and excessive pad loading if used with the wrong machine or finish.  
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
<a name="#power"><b>Floor Pads:  Floor machine power type</b></a></span><br><br><span class="text">
Propane machines are the most efficient of all the floor machines.  However, that efficiency comes with strings attached.  Floor finish powders under propane machines; it's a fact of life.  If there is significant powdering, scratching or pad loading, then try to go to a less aggressive pad.  You may also try to adjust the pad pressure on the machine. 
</p>

<p>
Battery-powered machines are not quite as aggressive as propane machines.  Finish can still powder under battery-powered machines so you may want to consider a less aggressive pad if powdering is a problem.  To reduce powdering with these machines, it may help to reduce the pad pressure.
</p>

<p>
Electrically powered floor machines, often referred to as cord electric, are the easiest of the high-speed machines to match a pad to.  Hair or synthetic pads can be used with the electric machines with minimal risk of problems.  Simply run with the pad that delivers the best results.
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
<a name="#rough"><b>Floor Pads:  Rough surfaces and grouted surfaces</b></a></span><br><br><span class="text">
How smooth is the floor?  If the floor has grout lines, is rough, or has an uneven surface, consider a brush instead of a pad to do scrubbing and stripping jobs.  Just looking to restore gloss?  Then go ahead with a pad matched to the finish and the machine.
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
<a name="#pad"><b>Floor Pads:  Pad sizes</b></a></span><br><br><span class="text">
Always purchase your pads in the same size as your machine.  If you have a 20" swing machine then purchase a 20" pad.  Using the correct size pad is important since using too large a pad will result in marking the baseboards, while using too small a pad will result in marking the floor finish with the pad holder.  Also, when selecting a pad, consider the size of the pad hole.  Most machines use the standard 1" bolt hole.  However, some machines require special sizes like a ¾" bolt hole.  
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
<a name="#strip"><b>Floor Pads:  Stripping and scrubbing pads</b></a></span><br><br><span class="text">
The type of job being performed will determine the pad to use.  The following pads are standard throughout the pad industry and will perform the same job.  Some pad manufacturers have better quality pads that can be rinsed and reused while others should be thrown away after one use.  Take note that some flooring manufacturers have guidelines for what pad can be used on the floor.  Make sure to understand what types of pads the flooring manufacturer recommends prior to making your final pad selections, especially if you have a new floor that is still under warranty.


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


<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#high"><b>Stripping and scrubbing pads:  High Productivity Pads</b></a></span><br><br><span class="text">
High productivity pads are the most aggressive floor pads.  These should be reserved for the tough stripping jobs where built up finish is a concern and speed is a must.  These pads will blast through most finishes, and their open weave helps limit finish loading on the pad surface.  However, be careful with these pads because they can damage some floor surfaces like asphalt, asbestos, marble and rubber floors to name a few.  High productivity pads are for use with low speed swing machines.
</span></li></ul></ul><table width="100%">
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





<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#black"><b>Stripping and scrubbing pads:   Black stripping pads</b></a></span><br><br><span class="text">
Black stripping pads are the standard stripping pads.  Black pads are typically used with the low-speed swing machines for removing floor finish.   Avoid using these pads on some flooring types like polished marble and asbestos.
</span></li></ul></ul><table width="100%">
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




<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#brown"><b>Stripping and scrubbing pads:  Brown pads</b></a></span><br><br><span class="text">
Brown pads are also used for stripping the not so tough stripping jobs.  These are not quite as popular as the black stripping pads.
</span></li></ul></ul><table width="100%">
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




<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#dark"><b>Stripping and scrubbing pads:  Dark blue and dark green pads</b></a></span><br><br><span class="text">
Dark blue and dark green pads are used for scrubbing floor finish in preparation for recoating.  When used with a general purpose cleaner, these pads will remove some but not all of the finish.  These pads are typically used with the low-speed swing machines or possibly autoscrubbers if used to scrub prior to a recoat.
</span></li></ul></ul><table width="100%">
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




<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#red"><b>Stripping and scrubbing pads:  Red pads</b></a></span><br><br><span class="text">
Red pads, when used with a low-speed swing machine and a spray buff maintainer will buff a floor finish and remove black marks and light scratches.  When used on an autoscrubber with a good neutral cleaner, red pads will clean a floor without removing any finish.
</span></li></ul></ul><table width="100%">
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
<a name="#buff"><b>Floor Pads:  Buffing pads</b></a></span><br><br><span class="text">
Red is the most common pad used for buffing with a low-speed machine (175-300 rpms) and a spray buff maintainer.  Also in the class of buffing pads are the beige or tan buffing pads that can be used with 1000-1500 rpm machines to restore gloss to a finish.  Unless a red pad is specified as a burnishing pad, never use a red pad on any machine over 300 rpms unless you feel like changing the color of your finish.
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
<a name="#burnish"><b>Floor Pads:  Burnishing pads</b></a></span><br><br><span class="text">
Burnishing pads are a little more complicated because there is no industry standard as far as color is concerned.  Burnishing pads will respond differently on every finish so you should use what works for you with your facility's machine and maintenance frequency.  Usually the darker the pad, the more aggressive the pad is.  The lighter the pad, the less aggressive the pad is.  Hair pads tend to be slightly more aggressive then their synthetic counterpart.  If you find that you are having a problem with scratching, swirl marks, powdering, or pad loading then try going to a slightly less aggressive pad until you find one that works.  If you find that you are not getting a gloss on your finish then try going to a slightly more aggressive pad.  Remember that burnishing pads should be used with floor machines 1500 rpm and above.  
</p>


			  
</td></tr>

</div></td>
</tr>
          