

	var imagePath='../externals/images/';

	var ie=document.all;
	var dom=document.getElementById;
	var ns4=document.layers;
	var bShow=false;
	var textCtl;

	function setTimePicker(t) {
     		textCtl.value=t;
		closeTimePicker();
	}

	function refreshTimePicker(mode) {
		if (mode==0)
			{
				suffix="am";
			}
		else
			{
				suffix="pm";
			}

		sHTML = "<table><tr><td><table cellpadding=1 cellspacing=0 bgcolor='#ffffff'>";
		for (i=0;i<12;i++) {

			sHTML+="<tr align=right style='font-family:verdana,Arial,Helvetica,Geneva,Swiss,sans-serif;font-size:11px;font-weight:bold;color:#000000;'>";

			if (i==0) {
				hr = 12;
			}
			else {
				hr=i;
			}

			for (j=0;j<4;j++) {
                                var hr24 = ((suffix=='am') ? ((hr<12) ? hr : hr-12) : ((hr<12) ? hr+12 : hr));
				sHTML+="<td width=40 style='cursor:hand;font-family:verdana,Arial,Helvetica,Geneva,Swiss,sans-serif;font-size:11px;font-weight:bold;' onmouseover='this.style.backgroundColor=\"#B0B0B0\"' onmouseout='this.style.backgroundColor=\"\"' onclick='setTimePicker(\""+
				hr24 + ":" + padZero(j*15)
				+ "\")'><a style='text-decoration:none;color:#000000' href='javascript:setTimePicker(\""+ hr24 + ":" + padZero(j*15) +
				"\")'>" + hr24 + ":"+padZero(j*15) +"&nbsp;"+ "<font color=\"#808080\">"  + "&nbsp;</font></a></td>";
			}

			sHTML+="</tr>";
		}
		sHTML += "</table></td></tr></table>";
		document.getElementById("timePickerContent").innerHTML = sHTML;
	}
var ieTitle = "<div id='timepicker' style='z-index:9;position:absolute;visibility:hidden;'><table style='border-width:3px;border-style:solid;border-color:#000000' bgcolor='#ffffff' cellpadding=0><tr bgcolor='#808080'><td><table cellpadding=0 cellspacing=0 width='100%' background='" + imagePath + "titleback.gif'><tr valign=bottom height=21><td style='font-family:verdana,Arial,Helvetica,Geneva,Swiss,sans-serif;font-size:11px;color:#ffffff;padding:3px' valign=center><B>&nbsp;Select&nbsp;Time </B></td><td><img id='iconAM' src='" + imagePath + "am1.gif' onclick='document.getElementById(\"iconAM\").src=\"" + imagePath + "am1.gif\";document.getElementById(\"iconPM\").src=\"" + imagePath + "pm2.gif\";refreshTimePicker(0)' style='cursor:hand'></td><td><img id='iconPM' src='" + imagePath + "pm2.gif' onclick='document.getElementById(\"iconAM\").src=\"" + imagePath + "am2.gif\";document.getElementById(\"iconPM\").src=\"" + imagePath + "pm1.gif\";refreshTimePicker(1)' style='cursor:hand'></td></tr></table></td></tr><tr><td colspan=2><span id='timePickerContent'></span></td></tr><TR><TD HEIGHT=1 BGCOLOR=#000000></TD></TR><TR><TD HEIGHT=20 ALIGN=MIDDLE><A STYLE=\"color: #336699\" HREF=\"javascript:closeTimePicker()\">Cancel</A></TD></TR></table></div>";
var otherTitle = "<div id='timepicker' style='z-index:9;position:absolute;visibility:hidden;'><table style='border-width:3px;border-style:solid;border-color:#6666CC' bgcolor='#ffffff' cellpadding=0><tr bgcolor='#6666CC'><td><table cellpadding=0 cellspacing=0 width='100%' background='" + imagePath + "titleback.gif'><tr valign=bottom height=21><td style='font-family:verdana,Arial,Helvetica,Geneva,Swiss,sans-serif;font-size:11px;color:#ffffff;padding:3px' valign=center><B>&nbsp;Select&nbsp;Time </B></td><td><img id='iconAM' src='" + imagePath + "am1.gif' onclick='document.getElementById(\"iconAM\").src=\"" + imagePath + "am1.gif\";document.getElementById(\"iconPM\").src=\"" + imagePath + "pm2a.gif\";refreshTimePicker(0)' style='cursor:hand'></td><td><img id='iconPM' src='" + imagePath + "pm2a.gif' onclick='document.getElementById(\"iconAM\").src=\"" + imagePath + "am2a.gif\";document.getElementById(\"iconPM\").src=\"" + imagePath + "pm1.gif\";refreshTimePicker(1)' style='cursor:hand'></td></tr></table></td></tr><tr><td colspan=2><span id='timePickerContent'></span></td></tr><TR><TD HEIGHT=1 BGCOLOR=#000000></TD></TR><TR><TD HEIGHT=20 ALIGN=MIDDLE><A STYLE=\"color: #336699\" HREF=\"javascript:closeTimePicker()\">Cancel</A></TD></TR></table></div>";
var title = otherTitle;
if (window.navigator.userAgent.indexOf ("MSIE") >= 0) {
 title = ieTitle;
}

        if (dom){
		document.write (title);
		refreshTimePicker(0);
	}

	var crossobj=(dom)?document.getElementById("timepicker").style : ie? document.all.timepicker : document.timepicker;
	var currentCtl

	function selectTime(ctl,ctl2) {

        var picker = document.getElementById("timepicker");

      	var leftpos=0
		var toppos=0

		textCtl=ctl2;
		currentCtl = ctl
		currentCtl.src=imagePath + "showCalendar.gif";

		aTag = ctl

        var pos = getOffset(ctl);
		leftpos	= pos.left;
	    toppos = pos.top;

        crossobj.left =	leftpos + ctl.offsetWidth;
		crossobj.top = 	toppos + ctl.offsetHeight +	2;

        fit(picker, aTag);

        crossobj.visibility=(dom||ie)? "visible" : "show"

        hideElement( 'SELECT', picker);
		hideElement( 'APPLET', picker);

        bShow = true;
	}

	// hides <select> and <applet> objects (for IE only)
	function hideElement( elmID, overDiv ){
		if( ie ){
			for( i = 0; i < document.all.tags( elmID ).length; i++ ){
				obj = document.all.tags( elmID )[i];
				if( !obj || !obj.offsetParent ){
						continue;
				}
				  // Find the element's offsetTop and offsetLeft relative to the BODY tag.
				  objLeft   = obj.offsetLeft;
				  objTop    = obj.offsetTop;
				  objParent = obj.offsetParent;
				  while( objParent.tagName.toUpperCase() != "BODY" )
				  {
					objLeft  += objParent.offsetLeft;
					objTop   += objParent.offsetTop;
					objParent = objParent.offsetParent;
				  }
				  objHeight = obj.offsetHeight;
				  objWidth = obj.offsetWidth;
				  if(( overDiv.offsetLeft + overDiv.offsetWidth ) <= objLeft );
				  else if(( overDiv.offsetTop + overDiv.offsetHeight ) <= objTop );
				  else if( overDiv.offsetTop >= ( objTop + objHeight + obj.height ));
				  else if( overDiv.offsetLeft >= ( objLeft + objWidth ));
				  else
				  {
					obj.style.visibility = "hidden";
				  }
			}
		}
	}

	//unhides <select> and <applet> objects (for IE only)
	function showElement( elmID ){
		if( ie ){
			for( i = 0; i < document.all.tags( elmID ).length; i++ ){
				obj = document.all.tags( elmID )[i];
				if( !obj || !obj.offsetParent ){
						continue;
				}
				obj.style.visibility = "";
			}
		}
	}

	function closeTimePicker() {
		crossobj.visibility="hidden"
		showElement( 'SELECT' );
		showElement( 'APPLET' );
		currentCtl.src=imagePath + "showCalendar.gif"
	}

	document.onkeypress = function hideTimePicker1 () {
		if (event.keyCode==27){
			if (!bShow){
				closeTimePicker();
			}
		}
	}

	function isDigit(c) {

		return ((c=='0')||(c=='1')||(c=='2')||(c=='3')||(c=='4')||(c=='5')||(c=='6')||(c=='7')||(c=='8')||(c=='9'))
	}

	function isNumeric(n) {

		num = parseInt(n,10);

		return !isNaN(num);
	}

	function padZero(n) {
		v="";
		if (n<10){
			return ('0'+n);
		}
		else
		{
			return n;
		}
	}

	function validateDatePicker(ctl) {

		t=ctl.value.toLowerCase();
		t=t.replace(" ","");
		t=t.replace(".",":");
		t=t.replace("-","");

		if ((isNumeric(t))&&(t.length==4))
		{
			t=t.charAt(0)+t.charAt(1)+":"+t.charAt(2)+t.charAt(3);
		}

		var t=new String(t);
		tl=t.length;

		if (tl==1 ) {
			if (isDigit(t)) {
				ctl.value=t+":00 am";
			}
			else {
				return false;
			}
		}
		else if (tl==2) {
			if (isNumeric(t)) {
				if (parseInt(t,10)<13){
					if (t.charAt(1)!=":") {
						ctl.value= t + ':00 am';
					}
					else {
						ctl.value= t + '00 am';
					}
				}
				else if (parseInt(t,10)==24) {
					ctl.value= "0:00 am";
				}
				else if (parseInt(t,10)<24) {
					if (t.charAt(1)!=":") {
						ctl.value= (t-12) + ':00 pm';
					}
					else {
						ctl.value= (t-12) + '00 pm';
					}
				}
				else if (parseInt(t,10)<=60) {
					ctl.value= '0:'+padZero(t)+' am';
				}
				else {
					ctl.value= '1:'+padZero(t%60)+' am';
				}
			}
			else
   		    {
				if ((t.charAt(0)==":")&&(isDigit(t.charAt(1)))) {
					ctl.value = "0:" + padZero(parseInt(t.charAt(1),10)) + " am";
				}
				else {
					return false;
				}
			}
		}
		else if (tl>=3) {

			var arr = t.split(":");
			if (t.indexOf(":") > 0)
			{
				hr=parseInt(arr[0],10);
				mn=parseInt(arr[1],10);

				if (t.indexOf("pm")>0) {
					mode="pm";
				}
				else {
					mode="am";
				}

				if (isNaN(hr)) {
					hr=0;
				} else {
					if (hr>24) {
						return false;
					}
					else if (hr==24) {
						mode="am";
						hr=0;
					}
					else if (hr>12) {
						mode="pm";
						hr-=12;
					}
				}

				if (isNaN(mn)) {
					mn=0;
				}
				else {
					if (mn>60) {
						mn=mn%60;
						hr+=1;
					}
				}
			} else {

				hr=parseInt(arr[0],10);

				if (isNaN(hr)) {
					hr=0;
				} else {
					if (hr>24) {
						return false;
					}
					else if (hr==24) {
						mode="am";
						hr=0;
					}
					else if (hr>12) {
						mode="pm";
						hr-=12;
					}
				}

				mn = 0;
			}

			if (hr==24) {
				hr=0;
				mode="am";
			}
			ctl.value=hr+":"+padZero(mn)+" "+mode;
		}
	}

    function fit(node, tag) {

        var pos = getOffset(node);
        var workScreen = getViewPortSize();
        var width = parseInt(node.offsetWidth);
        var height = parseInt(node.offsetHeight);

        var b = document.body;
        var d = document.documentElement;

        var scrollTop = window.pageYOffset || d.scrollTop || b.scrollTop
        var scrollLeft = window.pageXOffset || d.scrollLeft || b.scrollLeft

        if (((pos.left + width + tag.offsetWidth) - scrollLeft) > workScreen.width) {
            node.style.left = Math.max((b.scrollLeft || d.scrollLeft), pos.left - ((pos.left + width - scrollLeft) - workScreen.width) - tag.offsetWidth) + 'px';
        }

        if (((pos.top + height + tag.offsetHeight) - scrollTop) > workScreen.height) {
            node.style.top = Math.max((b.scrollTop || d.scrollTop), pos.top -  height - tag.offsetHeight) + 'px';
        }
    }

    function getViewPortSize() {
        var size = {};
        if (typeof window.innerWidth != 'undefined') {
            size.width = window.innerWidth;
            size.height = window.innerHeight;
        } else if (typeof document.documentElement != 'undefined'
                && typeof document.documentElement.clientWidth != 'undefined'
                && document.documentElement.clientWidth != 0) {
            size.width = document.documentElement.clientWidth;
            size.height = document.documentElement.clientHeight;
        } else {
            size.width = document.getElementsByTagName('body')[0].clientWidth;
            size.height = document.getElementsByTagName('body')[0].clientHeight;
        }

        return size;
    }

    function getOffsetSum(elem) {
        var top = 0, left = 0
        while (elem) {
            top = top + parseInt(elem.offsetTop)
            left = left + parseInt(elem.offsetLeft)
            elem = elem.offsetParent
        }
        return {top: top, left: left}
    }

    function getOffsetRect(elem) {
        var box = elem.getBoundingClientRect();
        var body = document.body;
        var docElem = document.documentElement;
        var scrollTop = window.pageYOffset || docElem.scrollTop || body.scrollTop
        var scrollLeft = window.pageXOffset || docElem.scrollLeft || body.scrollLeft
        var clientTop = docElem.clientTop || body.clientTop || 0
        var clientLeft = docElem.clientLeft || body.clientLeft || 0
        var top = box.top + scrollTop - clientTop
        var left = box.left + scrollLeft - clientLeft
        return { top: Math.round(top), left: Math.round(left) }
    }

    function getOffset(elem) {
        if (elem.getBoundingClientRect) {
            return getOffsetRect(elem)
        } else {
            return getOffsetSum(elem)
        }
    }


