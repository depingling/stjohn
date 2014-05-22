var ie7 = window.XMLHttpRequest && document.all && !window.opera;
var ie7offline = ie7 && window.location.href.indexOf("http") == -1;
var firefox = navigator.userAgent.toLowerCase().indexOf("firefox") != -1;

function ajaxconnect(url, paramStr, thediv, responseXmlHandler) {
  ajaxconnecttype(url, paramStr, thediv, responseXmlHandler, true);  // true - async, false - sync
}
function ajaxconnecttype(url, paramStr, thediv, responseXmlHandler, syncType) {
    var page_request = false;
    var bustcacheparameter = "";
    if (window.XMLHttpRequest && !this.ie7offline) // if Mozilla, IE7 online, Safari etc
        page_request = new XMLHttpRequest()
    else if (window.ActiveXObject) { // if IE6 or below, or IE7 offline (for testing purposes)
        try {
            page_request = new ActiveXObject("Msxml2.XMLHTTP")
        } catch (e) {
            try {
                page_request = new ActiveXObject("Microsoft.XMLHTTP")
            }
            catch (e) {
            }
        }
    }
    else {
        return
    }
   if (firefox){
      page_request.onload = page_request.onerror = page_request.onabort = function(){
           readyStateHandler(page_request, responseXmlHandler, thediv)
     };
   } else {
     page_request.onreadystatechange = function() {
        readyStateHandler(page_request, responseXmlHandler, thediv)
      };
   }
    page_request.open('POST', url, syncType)
    page_request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    page_request.send(paramStr)
}

function readyStateHandler(req, responseXmlHandler, thediv) {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var root = req.responseXML.getElementsByTagName("Error")[0];
            if (root != null && 'undefined' != typeof root) {
                alert(root.firstChild.nodeValue);
		    } else {
		    	responseXmlHandler(req.responseXML, thediv);
		    }
		} else {
		    alert("HTTP error:" + req.status);
		}
	}
}

var state = '';
var checkReport = {
  reportState : function (data, thediv ) {
      var root = data.getElementsByTagName("State")[0];
      state ='unlocked';
      if  (root != null && 'undefined' != typeof root ) {
        state = root.getAttribute("Error");
     }
   },

   isLocked : function (ajaxAction, ajaxParamStr){
      ajaxconnecttype(ajaxAction, ajaxParamStr, null, this.reportState, false);
      return state
   }

}
