<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>
<style type="text/css">
    @import "../externals/dojo_1.1.0/dojo/resources/dojo.css";
</style>

<script>
    var djConfig = {parseOnLoad: true, isDebug: false, usePlainJson: true};
</script>
<script type="text/javascript" src="../externals/dojo_1.1.0/dojo/dojo.js"></script>
<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="ADM_CACHECOS_MGR_FORM" type="com.cleanwise.view.forms.AdmCachecosMgrForm"/>
<script language="JavaScript" someProperty="text/javascript">

    dojo.require("dojox.data.jsonPathStore");
    dojo.require("dijit.Tree");
    dojo.require("dojo.parser");

    var tableDescStore;
    var keyDescStore;
    var linkDescStore;
    var dataStore = eval(<%=theForm.getCacheDescription()%>);
    var d = dojo;
    var theme;

    if (!theme) {
        theme = 'tundra';
    }

    var themeCss = d.moduleUrl("dijit.themes", theme + "/" + theme + ".css");
    document.write('<link rel="stylesheet" type="text/css" href="' + themeCss + '"/>');

    function createJsonStore() {
        tableDescStore = new dojox.data.jsonPathStore({jsId:"tableDescStore",idAttribute:"id",labelAttribute:"name",data:dataStore.TableDescription.values});
        keyDescStore = new dojox.data.jsonPathStore({jsId:"keyDescStore",idAttribute:"id",labelAttribute:"name",data:dataStore.KeyDescription.values});
        linkDescStore = new dojox.data.jsonPathStore({jsId:"tableLinkDescStore",idAttribute:"id",labelAttribute:"name",data:dataStore.TableLinkDescription.values});
    }

    function createTree() {

        var tableDescTree = new dijit.Tree({
            labelAttr:"name",
            somePropertyAttr:"property",
            query:{query: '$[*]'} ,
            store: tableDescStore
        });

        var keyDescTree = new dijit.Tree({
            labelAttr:"name",
            somePropertyAttr:"property",
            query:{query: '$[*]'} ,
            store: keyDescStore
        });

        var tableLinkDescTree = new dijit.Tree({
            labelAttr:"name",
            somePropertyAttr:"property",
            query:{query: '$[*]'} ,
            store: linkDescStore
        });

        dojo.byId('tableDesc').appendChild(tableDescTree.domNode);
        dojo.byId('keyDesc').appendChild(keyDescTree.domNode);
        dojo.byId('tLinkDesc').appendChild(tableLinkDescTree.domNode);

        tableDescTree.startup();
        keyDescTree.startup();
        tableLinkDescTree.startup();
    }


    d.addOnLoad(function() {
        if (!d.hasClass(d.body(), theme)) {
            d.addClass(d.body(), theme);
        }
       createJsonStore();
       createTree();
    });

</script>
<html:html>
    <head>
        <link rel="stylesheet" href="../externals/styles.css">
        <style>
            .tt {
                color: white;
                background-color: black;
            }

            .tt1 {
                border-right: solid 1px black;
            }
        </style>
        <title>Cachecos Administrator</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    </head>

    <body>

    <table border=0 width="769" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <jsp:include flush='true' page="ui/systemToolbar.jsp"/>
            </td>
        </tr>
        <tr>
            <td>
                <jsp:include flush='true' page="ui/loginInfo.jsp"/>
            </td>
        </tr>
        <tr>
            <td>
                <jsp:include flush='true' page="ui/admCachecosToolbar.jsp"/>
            </td>
        </tr>
    </table>

    <table bgcolor="#cccccc" width="769">
        <tr>
            <td valign="top">
                <div id="keyDesc"></div>
            </td>
            <td valign="top">
                <div id="tableDesc"></div>
            </td>
            <td valign="top">
                <div id="tLinkDesc"></div>
            </td>
        </tr>
    </table>

    <jsp:include flush='true' page="ui/admFooter.jsp"/>
    </body>
</html:html>
