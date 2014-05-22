<%@page import="com.cleanwise.service.api.value.*"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@page import="com.cleanwise.service.api.util.Utility" %>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<input type="hidden" name="content" value="" id="content">
<!-- required: the default dijit theme: -->
<link id="themeStyles" rel="stylesheet" href="../externals/dojo_1.1.0/dijit/themes/tundra/tundra.css">
<script type="text/javascript" src="../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
<script src="../externals/ajaxutil.js" language="JavaScript"></script>
<script src="../externals/dojo_1.1.0/dojox/xml/DomParser.js" language="JavaScript"></script>
<script>

var storeAccountShopRestrictionsAjax = {
	closeit:function(id) {
		this.clearMessages(id);
		eval("document.getElementById('panel_'+id)").style.display = 'none';	
    },
	initialize:function(showActiveNotes, itemId) {
        this.query(null, itemId);

    },
	clearMessages:function(itemId){
		var url = "action=clearItemMessages";
		url += "&itemId=" + itemId;
		ajaxconnect("accountShoppingRestrictions.do", url, null, loadData);
	},
	query: function(direction, itemId) {
        var url = "action=getSiteControls";
        if (itemId > 0) {
            url += "&itemId=" + itemId;
        }
        if (direction != null) {
            url += "&direction=" + direction;
        }
		//alert(url);
		
        ajaxconnect("accountShoppingRestrictions.do", url, null, loadData);
		
    },
	loadData: function(data) {

		 var root = data.getElementsByTagName("SiteControls")[0];
		 if (root != null && 'undefined' != typeof root) {
			var myObj = new Array();
			var itemid = parseInt(root.getAttribute("Id"));
            var txt;
            if (document.all) {  //IE
                txt = root.getElementsByTagName("SCD");
            } else {
                txt = data.getElementsByTagName("SCD");
            }
            if (txt != null && 'undefined' != typeof txt) {
                for (var i = 0; i < txt.length; i++) {
					var obj = new Array();
                    var itemMess ="";
                    var siteId = "";
                    var sName = "";
                    var maxQty = "";
                    var restDays = "";
                    var inCatalog = "";
                    if (document.all) { // IE
                        siteId = txt[i].childNodes[0].firstChild.nodeValue;
                        sName = txt[i].childNodes[1].firstChild.nodeValue;
                        maxQty = txt[i].childNodes[2].firstChild.nodeValue;
                        restDays = txt[i].childNodes[3].firstChild.nodeValue;
                        inCatalog = txt[i].childNodes[4].firstChild.nodeValue;
                        var fChild = txt[i].childNodes[5].firstChild;
                        if(fChild != null){
                            itemMess = txt[i].childNodes[5].firstChild.nodeValue;
                        }
                   } else {
                        siteId = txt[i].children[0].childNodes[0].nodeValue;
                        sName = txt[i].children[1].childNodes[0].nodeValue;
                        maxQty = txt[i].children[2].childNodes[0].nodeValue;
                        restDays = txt[i].children[3].childNodes[0].nodeValue;
                        inCatalog = txt[i].children[4].childNodes[0].nodeValue;
                        var fChild = txt[i].children[5].childNodes[5];
                        if(fChild != null){
                            itemMess = txt[i].children[5].childNodes[0].nodeValue
                        }
                    }
                    obj[0] = siteId;
                    obj[1] = sName;
                    obj[2] = maxQty;
                    obj[3] = restDays;
                    obj[4] = inCatalog;
                    obj[5] = itemMess;
                    myObj[i] = obj;
                }
            }
			if(myObj!=null && myObj.length>0){
				createTable(myObj,itemid);
			}
		 }else{
		    createTable(new Array(),itemid);
		 }
	}
}
function loadData(data, div, req) {
 
    storeAccountShopRestrictionsAjax.loadData(data);
}

function createTable(o, itemid){

	var outerTable = document.createElement("TABLE");
	var outer_tbody = document.createElement("TBODY");
	//outerTable.width="100%";
	var outerTR = document.createElement("TR");
	var outerTD1 = document.createElement("TD");
	
	//var buttons = document.createElement('div');
    //var closeButton = new dijit.form.Button({id: "close",label: "CLOSE"});
    //closeButton.onClick = function(){
    //   storeAccountShopRestrictionsAjax.closeit(itemid);
    //};
    //buttons.appendChild(closeButton.domNode);
    //outerTD1.appendChild(buttons); 
	
	var outerTD2 = document.createElement("TD");
	//outerTD2.setAttribute("colSpan",7);
	var tab = document.createElement("TABLE");
	tab.id = "TableId"+itemid;
	//tab.width=500;
	
	//header
	var t_head = document.createElement("THEAD");

	var headerRow = document.createElement("TR");
	
	var cell0 = document.createElement("TD");
	var cell1 = document.createElement("TD");
	var cell2 = document.createElement("TD");
	var cell3 = document.createElement("TD");
	var cell4 = document.createElement("TD");
	var cell5 = document.createElement("TD");
	
	var site = document.createTextNode("SITE_ID");
	cell0.appendChild(site);
	
	var siteName = document.createTextNode("SITE_NAME");
	cell1.appendChild(siteName);
	
	var max = document.createTextNode("MAX QTY");
	cell2.appendChild(max);
	
	var restd = document.createTextNode("RESTRICTION DAYS");
	cell3.appendChild(restd);
	
	var del = document.createTextNode("DELETE");
	cell4.size = 5;
	cell4.appendChild(del);
	
	var delAllLink=document.createElement('a');
	var delAllText=document.createTextNode('DELETE ALL');
	delAllLink.appendChild(delAllText);

	delAllLink.setAttribute('href','#');
	delAllLink.onclick=function(){
		if (confirm('Are you sure you want to delete all site controls?')){
				
				var o2= o[this.id];
				var url = "action=deleteAllSiteControls";
			
				if (itemid> 0) {
					url += "&itemId=" + itemid;
				}
				
				ajaxconnect("accountShoppingRestrictions.do", url, null, loadData);
				
				var t = document.getElementById( "TableId"+itemid );
				
				for(var i=t.rows.length-1; i>=0; i--){
					//alert(i);
					t.deleteRow(i);					
				}
				//storeAccountShopRestrictionsAjax.closeit(itemid);
				storeAccountShopRestrictionsAjax.query(null, itemid);
				
		}
	};
	cell5.appendChild(delAllLink);
	
	t_head.appendChild(headerRow);
	headerRow.appendChild(cell0);
	headerRow.appendChild(cell1);
	headerRow.appendChild(cell2);
	headerRow.appendChild(cell3);
	headerRow.appendChild(cell4);
	headerRow.appendChild(cell5);
	tab.appendChild(t_head);
	
	//body
	var t_body = document.createElement("TBODY");
	
	for(var j=0; j<o.length; j++){
		
		var thistr = document.createElement("TR");
		var thisObj = o[j];
		
		var cSiteId = document.createElement("TD");
		var s  = document.createTextNode(thisObj[0]);
		cSiteId.appendChild(s);
		
		var cSiteName = document.createElement("TD");
		var sn  = document.createTextNode(thisObj[1]);
		cSiteName.appendChild(sn);

if (thisObj[4] == 'Y') {
		var cMax = document.createElement("TD");
		var m = document.createElement("input");
		m.id = j;
		m.size=3;
		if(thisObj[2]=='-1'){
			m.value="*";
		}else{
			m.value=thisObj[2];
		}
		m.onchange = function()
		{
			var o1= o[this.id];
			var url = "action=updateSiteControls";
			
			if (itemid > 0) {
				url += "&itemId=" + itemid;
			}
			url += "&siteId=" + o1[0];
			if(this.value=='*'){
				url += "&maxQty=" + "-1";
			}else{
				url += "&maxQty=" + this.value;
			}
			url += "&restDays=" + o1[3];
			//alert(url);
			ajaxconnect("accountShoppingRestrictions.do", url, null, loadData);
			//alert(loadData);
			storeAccountShopRestrictionsAjax.query(false,itemid);
		};
		cMax.appendChild(m);

		var cRestD = document.createElement("TD");
		var r = document.createElement("input");
		r.id = j;
		r.size=3;
		if(thisObj[3]=='-1'){
			r.value="*";
		}else{
			r.value=thisObj[3];
		}
		
		r.onchange = function()
		{
			var o2= o[this.id];
			var url = "action=updateSiteControls";
			
			if (itemid> 0) {
				url += "&itemId=" + itemid;
			}
			url += "&siteId=" + o2[0];
			url += "&maxQty=" + o2[2];
			if(this.value=='*'){
				url += "&restDays=" + "-1";
			}else{
				url += "&restDays=" + this.value;
			}

			//alert(url);
			ajaxconnect("accountShoppingRestrictions.do", url, null, loadData);
			storeAccountShopRestrictionsAjax.query(false,itemid);
		};
		cRestD.appendChild(r);

		var cDel = document.createElement("TD");
		var delBox = document.createElement("input");
		delBox.id = j;
		delBox.type = "checkbox";
		delBox.onclick = function()
		{
			
			if (confirm('Are you sure you want to delete ?')){
				//alert("deleting");
				var o2= o[this.id];
				var url = "action=deleteSiteControls";
			
				if (itemid> 0) {
					url += "&itemId=" + itemid;
				}
				url += "&siteId=" + o2[0];
				
				ajaxconnect("accountShoppingRestrictions.do", url, null, loadData);
				
				t_body.deleteRow(this.id);
				
			}else{
				//alert("not deleting");
				this.checked = false;
			}

		};
		cDel.appendChild(delBox);
		
		var cMessage = document.createElement("TD");
		var mess  = document.createTextNode(thisObj[5]);
		cMessage.appendChild(mess);
		var err = "Error";
		
		//cMessage.style.fontWeight = 'bold';
		if(thisObj[5].indexOf(err)>=0){
			cMessage.style.color = 'red';
		}else{
			cMessage.style.color = 'blue';
		}
		thistr.appendChild(cSiteId);
        thistr.appendChild(cSiteName);
        thistr.appendChild(cMax);
        thistr.appendChild(cRestD);
        thistr.appendChild(cDel);
		thistr.appendChild(cMessage);
} else {
    var cInfo = document.createElement("TD");
    var s  = document.createTextNode("Not in Shopping Catalog");
    cInfo.appendChild(s);
    thistr.appendChild(cSiteId);
    thistr.appendChild(cSiteName);
    thistr.appendChild(cInfo);
    cInfo.setAttribute("colSpan", 4);
    cInfo.style.fontWeight = 'bold';
}	
		t_body.appendChild(thistr);
	} 
	tab.appendChild(t_body);
	
	outerTD2.appendChild(tab);

	outer_tbody.appendChild(outerTR);
	outerTR.appendChild(outerTD1);
	outerTR.appendChild(outerTD2);
	outerTable.appendChild(outer_tbody);
	var panel = document.getElementById('panel_td_'+itemid);
	while (panel.childNodes.length > 0) {
		panel.removeChild(panel.lastChild);
	}
	panel.appendChild(outerTable);

}

</script>