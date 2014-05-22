
var hrPost  = false; //HttpRequest
var xhrPost = false; //XMLHttpRequest

dojo.require("dojox.fx.easing");
dojo.require("dojox.fx.scroll");


function updatePage(var1, var2, var3){
    updateShoppingMsgs(var1);
};

function updateShoppingMsgs(data) {
    if(!hrPost)  {

        var errorMsgs = "";
        try {
            var root = data.getElementsByTagName("response")[0];

            var body = root.getElementsByTagName("body")[0];

            var errors = body.getElementsByTagName("errors")[0];
            for (var i = 0; i < errors.childNodes.length; i++) {
                var error = errors.childNodes[i];
                for (var j = 0; j < error.childNodes.length; j++) {
                    var errorString = error.childNodes[j]
                    var mess = errorString.firstChild.nodeValue
                    errorMsgs+=(i>0?"<br>":"")+mess;
                }
            }

            var  scerrors = document.getElementById("scerrors");
            var submitQty1 = document.getElementById("submitQty1");
            if( scerrors && submitQty1){
                if(errorMsgs!="")  {
                    scerrors.innerHTML = "<b>"+errorMsgs+"</b>";
                    scerrors.style.color="#FF0000";
                    scerrors.style.whitespace="normal";
                    submitQty1.style.visibility = "visible";
                    gotoName("buttonSection");
                }else{
                    scerrors.innerHTML = "";
                    submitQty1.style.visibility = "hidden";
                }
            }


        } catch(e) {
            //alert(e)
        }
    }

    if(hrPost){
        actionMultiSubmit("submitQty","updateCart");
    }

    xhrPost = false;

};

function gotoName(name){
    // summary; searches for a <a name=""></a> attrib, and scrolls to it
    dojo.query('a[name="'+name+'"]').forEach(function(node){
        // first one wins
        var anim = dojox.fx.smoothScroll({
            node: node,
            win:window,
            duration:300,
            easing:dojox.fx.easing.easeOut}).play();
        return;
    })
};

function actionMultiSubmit(actionDef, action) {

    var aaal = document.getElementsByName('action');
    for ( var i = 0; i < 1; i++ ) {
        var aaa = aaal[i];
        aaa.value = action;
        aaa.form.submit();
    }

    return false;
};
