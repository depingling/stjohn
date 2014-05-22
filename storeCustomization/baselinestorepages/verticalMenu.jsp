<%@ page import="com.cleanwise.service.api.value.MenuItemView" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<style type="text/css">
    <!--

    .closeelem {
        display: none;
        list-style-type: none;
        border-collapse:collapse;
        margin-left:5px;
        padding:0;

    }

    .openelem, .openelemBlocked {
        display: block;
        border-collapse:collapse;
        margin-left:5px;
        padding:0;
    }

    .openli, .closeli, .openliBlocked {
        list-style-type: none;
        border-collapse:collapse;
        padding:0;
        margin:0;
    }

    .categName{
        width:100%;
        height:100%;
    }
    -->
</style>

<script language="JavaScript1.2">
<!--

function Event() {
    this.timeOutId = null;
    this.sourceId = null;
    this.action = null;
}

function EngineVarriable() {
    this.innerCursor = new Array();
    this.outerCursor = "sbcROOT"
    this.closeQueue = new Array();
    this.openQueue = new Array();
}

var engineVar = new EngineVarriable();

function removeClosedParentsEventsBySource(src) {
    var listIds = getParentsList(src);
    while (listIds.length > 0) {
        removeClosedEventsBySource(listIds.shift());
    }
}

function removeOpenedParentsEventsBySource(src) {
    var listIds = getParentsList(src);
    while (listIds.length > 0) {
        removeOpenedEventsBySource(listIds.shift());
    }
}

function getParentsList(obj) {
    var listIds = new Array();
    while (obj && !(obj.id == "sbcROOT")) {
        if (obj.nodeName == "TABLE" || obj.nodeName == "TR") {
            listIds.push(obj.id)
        }
        obj = obj.parentNode
    }
    return listIds;
}

function openItem(item, parent) {
    if ('undefined' != typeof item && 'undefined' != typeof parent) {
        if (item.className != "openliBlocked") {

            removeClosedParentsEventsBySource(item)
            removeOpenEvents();

            var event = new Event();
            event.sourceId = item.id;
            event.action = "openInnerElementById('" + item.id + "','" + parent.id + "');";
            sendOpenEvent(event)
        }
        engineVar.outerCursor = parent.id;
        engineVar.innerCursor = getParentsList(item);
    }
}

function openInnerElementById(itemId) {
    var element = document.getElementById(itemId)
    if ('undefined' != typeof element) {
        element.className = "openli";
    }
    removeOpenedEventsBySource(itemId);
}

function sendOpenEvent(event) {
    event.timeOutId = setTimeout(event.action, 250);
    removeCloseEvents();
    engineVar.openQueue.push(event);
}

function sendCloseEvent(event) {
    event.timeOutId = setTimeout(event.action, 2000);
    engineVar.closeQueue.push(event);
}


function removeOpenedEventsBySource(sourceId) {
    for (var qc = 0; engineVar.openQueue.length > qc; qc++) {
        var event = engineVar.openQueue[qc]
        if (event != null && event.sourceId == sourceId) {
            clearTimeout(event.timeOutId)
            engineVar.openQueue.splice(qc, 1);
            qc--;

        }
    }
}

function removeClosedEventsBySource(sourceId) {
    for (var qc = 0; engineVar.closeQueue.length > qc; qc++) {
        var event = engineVar.closeQueue[qc]
        if (event != null && event.sourceId == sourceId) {
            clearTimeout(event.timeOutId)
            engineVar.closeQueue.splice(qc, 1);
            qc--;

        }
    }
}
function removeOpenEvents() {
    for (var qc = 0; engineVar.openQueue.length > qc; qc++) {
        var event = engineVar.openQueue[qc]
        if (event != null && event.timeOutId != null) {
            clearTimeout(event.timeOutId)
            engineVar.openQueue.splice(qc, 1);
            qc--;

        }
    }
}

function removeCloseEvents() {
    for (var qc = 0; engineVar.closeQueue.length > qc; qc++) {
        var event = engineVar.closeQueue[qc];
        if (event != null && event.timeOutId != null) {
            clearTimeout(event.timeOutId)
            engineVar.closeQueue.splice(qc, 1);
            qc--;
        }
    }
}


function openOuterElementById(itemId, childId, parentId) {
    var outerElement = document.getElementById(itemId)
    if ('undefined' != typeof outerElement) {
        outerElement.className = "openli";
    }
    var innerElement = document.getElementById(childId);
    if ('undefined' != typeof innerElement) {
        innerElement.className = "openelem"
    }
    removeOpenedEventsBySource(itemId);
}


function openTreeLevel(item, child, parent) {
    if ('undefined' != typeof item && 'undefined' != typeof child && 'undefined' != typeof parent) {
        if (item.className != "openliBlocked") {
            removeClosedParentsEventsBySource(item)
            removeOpenEvents();
            var event = new Event();
            event.sourceId = item.id;
            event.action = "openOuterElementById('" + item.id + "','" + child.id + "');";
            sendOpenEvent(event)
            engineVar.innerCursor = getParentsList(item);
            engineVar.outerCursor = parent.id
        }
    }
}

function closeItem(item, parent) {
   /* if ('undefined' != typeof item && 'undefined' != typeof parent) {
        if (item.className != "openliBlocked") {
            removeOpenedParentsEventsBySource(item)
            //if (!childOpen(item)) {

            engineVar.outerCursor = "";
            engineVar.innerCursor = new Array();
            var event = new Event();
            event.sourceId = item.id;
            event.action = "closeElementById('" + item.id + "');";
            sendCloseEvent(event)
            // }
        }
    }*/    closeAll();
}

function closeAll() {
            var root  = document.getElementById("sbcROOT");
            removeCloseEvents();
            engineVar.outerCursor = "";
            engineVar.innerCursor = new Array();
            var event = new Event();
            event.sourceId = root.id;
            event.action = "closeChilds('" + root.id + "');";
            sendCloseEvent(event);

}

function closeChilds(objId) {
    var obj = document.getElementById(objId)
    if (obj.nodeName == "TABLE" && obj.tBodies && obj.tBodies[0]) {
        for (var i = 0; i < obj.tBodies[0].rows.length; i++) {
            if (obj.tBodies[0].rows[i].childNodes && obj.tBodies[0].rows[i].childNodes.length > 0) {
                var childRootId = getChildTableId(obj.tBodies[0].rows[i]);
                if (childRootId) {
                    closeChilds(childRootId);
                }
            }
            if(obj.tBodies[0].rows[i].className!="openliBlocked"){
                obj.tBodies[0].rows[i].className = "closeli";
            }
        }
        if (obj.id != "sbcROOT") {
            obj.className = "closeelem";
        }
    }
}

function getChildTableId(obj){
    var table = obj.getElementsByTagName("table");
    if(table && table[0]) {
        return table[0].id;
    }
    return null;
}

/*function closeElementById(itemId, childId) {

    if ('undefined' != typeof itemId) {

        var outerElement = document.getElementById(itemId)
        if ('undefined' != typeof outerElement) {
            outerElement.className = "closeli";
        }

        if ('undefined' != typeof childId) {
            var innerElement = document.getElementById(childId);
            if ('undefined' != typeof innerElement) {
                innerElement.className = "closeelem"
            }
        }
        setCloseStatus(outerElement);
        removeClosedEventsBySource(itemId)
    }

}*/

function closeTreeLevel(item, child, parent) {

   /* if ('undefined' != typeof item && 'undefined' != typeof child && 'undefined' != typeof parent) {
        if (item.className != "openliBlocked" && child.className != "openelemBlocked") {
            // if (!childOpen(child)) {
            removeOpenedParentsEventsBySource(item)
            engineVar.outerCursor = "";
            engineVar.innerCursor = new Array();
            var event = new Event();
            event.sourceId = item.id;
            event.action = "closeElementById('" + item.id + "','" + child.id + "');";
            sendCloseEvent(event)
            //   }
        }
    }*/    closeAll();
}

function getNextParent(obj) {
    obj = obj.parentNode
    while (obj && !(obj.nodeName == "TABLE")) {
        obj = obj.parentNode
    }
    return obj
}

function childOpen(obj) {
    var childes = obj.childNodes;
    for (var ii = 0; ii < childes.length; ii++) {
        var child = childes.item(ii);
        if (child.nodeName == "TR" && child.className == "openli") {
            return true;
        } else {
            if (childOpen(child)) {
                return true;
            }
        }
    }
    return false;
}

function setCloseStatus(obj) {
    while (obj && !(obj.nodeName == "TABLE" && (obj.id == "sbcROOT" || onTree(obj, engineVar) || obj.className == "openelemBlocked"))) {
        if (obj.nodeName == "TABLE") {
            obj.className = "closeelem";
        }
        obj = obj.parentNode;
    }
}

function onTree(obj, engVar) {
    if (obj.id == engVar.outerCursor) {
        return true;
    } else {
        if (engVar.innerCursor) {
            for (var tt = 0; tt < engVar.innerCursor.length; tt++) {
                if (engVar.innerCursor[tt] == obj.id) {
                    return true;
                }
            }
        }
    }
    return false;
}
//-->
</script>

<%
    String name = "SHOP_FORM";
    String property = "catalogMenu";
    StringBuffer menuContent = new StringBuffer();
%>

<logic:present name="<%=name%>" property="<%=property%>">
    <bean:define id="menuData"
                 name="<%=name%>"
                 property="<%=property%>"
                 type="com.cleanwise.service.api.value.MenuItemView"/>
    <table width="100%" align="left">
        <tr>
            <td class="xpdexMenuHeader" valign="top" align="left"><app:storeMessage key="shop.menu.main.shopByCategory"/></td>
        </tr>
        <tr>
            <td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="4"></td>
        </tr>
        <tr>
            <td align="left">
                <%ClwCustomizer.buildMenuStr(menuData, menuContent, "sbc",0);%>
                <%=menuContent.toString()%>
            </td>
        </tr>
    </table>
</logic:present>
