var menuBox = {
    defineheader: "",
    tableHeader: "",
    tableBody: "",
    fix:false,
    ajaxbustcache: true,
    disablescrollbars: false,
    autohidetimer: 0,
    ie7: window.XMLHttpRequest && document.all && !window.opera,
    ie7offline: this.ie7 && window.location.href.indexOf("http") == -1, //check for IE7 and offline
    launch:false,
    init:false,
    scrollbarwidth: 16,
    targetElementName: "",
    rows: new Array(),

    loadpage:function(page_request, thediv) {


        menuBox.hidescrollbar();
        menuBox.getscrollbarwidth();
        menuBox.showcontainer();


    },

    hidebugcontrol:function() {
        if (this.bugelements != "undefined") {
            for (var i = 0; i < this.bugelements.length; i++) {
                this.bugelements[i].style.visibility = 'hidden';
            }
        }
    },

    restorebugcontrol:function() {

        if (this.bugelements != "undefined") {
            for (var i = 0; i < this.bugelements.length; i++) {
                this.bugelements[i].style.visibility = 'visible';
            }
        }
    },

    createcontainer:function() {
    	var boxHTML =	"<div id='interContainer'>" + 
    						this.defineheader + 
    						"<div id='interContent'>" + 
    							"<table width='100%'>" +
    								this.tableHeader +
    								this.tableBody +	
								"</table>" +    								
    						"</div>" + 
    					"</div>" + 
    					"<div id='interVeil'></div>"
        document.write(boxHTML)
        this.interContainer = document.getElementById("interContainer") //reference interstitial container
        this.interContent = document.getElementById("interContent") //reference interstitial content
        this.interVeil = document.getElementById("interVeil") //reference veil
        this.standardbody = (document.compatMode == "CSS1Compat")? document.documentElement : document.body //create reference to common "body" across doctypes
        this.bugelements = document.getElementsByTagName("select");
    }
    ,

    showcontainer:function() {

        if (!this.launch && !this.init) return //if interstitial box has already closed, just exit (window.onresize event triggers function)

        this.interContent.style.display   = "block";
        this.interContainer.style.display = "block";
        this.interVeil.style.display      = "block";


        this.hidebugcontrol();

        var ie = document.all && !window.opera
        var dom = document.getElementById
        var docwidth = (ie)? this.standardbody.clientWidth : window.innerWidth - this.scrollbarwidth
        var docheight = (ie)? this.standardbody.clientHeight: window.innerHeight

        this.interContent.style.height = Math.floor(parseInt(0.4 * docheight));
        this.interContent.style.overflow = 'auto';

        var scroll_top = (ie)? this.standardbody.scrollTop : window.pageYOffset
        var scroll_left = (ie)? this.standardbody.scrollLeft : window.pageXOffset
        var docheightcomplete = (this.standardbody.offsetHeight > this.standardbody.scrollHeight)? this.standardbody.offsetHeight : this.standardbody.scrollHeight
        var objwidth = this.interContainer.offsetWidth
        var objheight = this.interContainer.offsetHeight
        this.interVeil.style.width = docwidth + "px" //set up veil over page
        this.interVeil.style.height = docheightcomplete + "px" //set up veil over page
        this.interVeil.style.left = 0 //Position veil over page
        this.interVeil.style.top = 0 //Position veil over page
        this.interVeil.style.visibility = "visible" //Show veil over page
        this.interContainer.style.left = docwidth / 2 - objwidth / 2 + "px" //Position interstitial box
        var topposition = (docheight > objheight)? scroll_top + docheight / 2 - objheight / 2 + "px" : scroll_top + 5 + "px" //Position interstitial box
        this.interContainer.style.top = Math.floor(parseInt(topposition)) + "px"
        this.interContainer.style.visibility = "visible" //Show interstitial box
        if (this.autohidetimer && parseInt(this.autohidetimer) > 0 && typeof this.timervar == "undefined")
            this.timervar = setTimeout("menuBox.closeit()", this.autohidetimer * 1000)
    }
    ,


    closeit:function() {
        this.interVeil.style.display = "none"
        this.interContainer.style.display = "none"
        this.interContent.style.display = "none"
        if (this.disablescrollbars && window.XMLHttpRequest) //if disablescrollbars enabled and modern browsers- IE7, Firefox, Safari, Opera 8+ etc
            this.standardbody.style.overflow = "auto"
        if (typeof this.timervar != "undefined") clearTimeout(this.timervar)
        this.restorebugcontrol();
        menuBox.launch = false;
    }
    ,

    getscrollbarwidth:function() {
        var scrollbarwidth = window.innerWidth - (this.interVeil.offsetLeft + this.interVeil.offsetWidth) //http://www.howtocreate.co.uk/emails/BrynDyment.html
        this.scrollbarwidth = (typeof scrollbarwidth == "number")? scrollbarwidth : this.scrollbarwidth
    }
    ,

    hidescrollbar:function() {
        if (this.disablescrollbars) { //if disablescrollbars enabled
            if (window.XMLHttpRequest) //if modern browsers- IE7, Firefox, Safari, Opera 8+ etc
                this.standardbody.style.overflow = "hidden"
            else //if IE6 and below, just scroll to top of page to ensure interstitial is in focus
                window.scrollTo(0, 0)
        }
    }
    ,

    dotask:function(target, functionref, tasktype) { //assign a function to execute to an event handler (ie: onunload)
        tasktype = (window.addEventListener)? tasktype : "on" + tasktype
        if (target.addEventListener)
            target.addEventListener(tasktype, functionref, false)
        else if (target.attachEvent)
            target.attachEvent(tasktype, functionref)
    }
    ,

    initialize:function(title, columnHeaders, selectableColumn, evenRowColor, oddRowColor, rows) {
    	
    	this.rows = rows
    
    	this.defineheader = "<div>" +
                  "<div align='left' class='mediumheader'>" + title + "</div>" + 
                  "<table width='100%'  border='0'  class='mainbody'>" +

                  "<tr>" +
                  "<td>&nbsp;" +
                  "</td>" +
                  "<td align=right>" +
                  "<input type='button' name='action' value='Cancel' onclick='menuBox.closeit()'>" +
                  "</td>" +
                  "<td>&nbsp;" +
                  "</td>" +
                  "</tr>" +

                  "</table>" +
                  "</div>"
    
    
    	this.tableHeader =	"<thead>" + "<tr align='left'>";
    	var i = 0
    	for(var i = 0; i < columnHeaders.length; i++) {		
    		this.tableHeader += "<td class='shopcharthead'><div class='fivemargin'>" + columnHeaders[i] + "</div></td>"
		}
		this.tableHeader += "</tr></thead>"
		
    	this.tableBody = "<tbody>";
    	
    	var row = new Array()

    	for(var i = 0; i < rows.length; i++) {		
	        var bgcolor;
	        if (i % 2 == 0) {
	            bgcolor = evenRowColor;
	        } else {
	            bgcolor = oddRowColor;
	        }
			this.tableBody += "<tr  bgcolor='" + bgcolor + "'>"
			row = rows[i]

			for(var j = 0; j < row.length; j++) {
				if(j == selectableColumn) {
					this.tableBody += "<td><a id='menuBoxItem" + i + "' href='#xxx' onclick='menuBox.clicked(this)'>" + row[j] + "</a></td>"
				} else {
					this.tableBody += "<td>" + row[j] + "</td>"
				}	
			}	
			this.tableBody += "</tr>"
		}

    	this.tableBody += "</tbody>"
    
        this.createcontainer()
        this.init = true;
    },
    
	clicked:function(element) {
		document.getElementsByName(menuBox.targetElementName)[0].value = element.innerHTML
		menuBox.closeit()
	}
    
}


function launchMenuBox(targetElementName) {
	menuBox.targetElementName = targetElementName
    menuBox.launch = true
    menuBox.showcontainer()
}
