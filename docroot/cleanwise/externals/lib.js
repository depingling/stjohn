var clickedButton = false;

function check() {
if (clickedButton) {
clickedButton = false;
return true;
}

else
return false;

}



function preloadImages(preFix, sTart, fInish, pAth) {
        if (document.images) {
                for (var i=sTart; i<=fInish; i++) {
                        //preload on images
                        newImage = preFix + i + "on = new Image()";
                        loCation = pAth + preFix + i + "on.gif";
                        newImageSRC = preFix + i + "on.src = \"" + loCation + "\"";
                        eval(newImage);
                        eval(newImageSRC);

                        //preload off images
                        newImage = preFix + i + "off = new Image()";
                        loCation = pAth + preFix + i + "off.gif";
                        newImageSRC = preFix + i + "off.src = \"" + loCation + "\"";
                        eval(newImage);
                        eval(newImageSRC);
                };
        };
};

function preloadSingleImages(preFix, sTart, fInish, pAth) {
  if (document.images) {
    for (var i=sTart; i<=fInish; i++) {
      //preload on images
      newImage = preFix + i + " = new Image()";
      loCation = pAth + preFix + i + ".gif";
      newImageSRC = preFix + i + ".src = \"" + loCation + "\"";
      eval(newImage);
      eval(newImageSRC);
    };
  };
};

// "change" swaps menu images with file names defined above
// and "name=" values set to mX where X is the corresponding number
function change(Name,OnOff,changeTo) {
  if (document.images) {
    document [Name].src = eval(Name + OnOff + ".src");

    if(document["changeme"]){
      document ["changeme"].src = eval("pub_message" + changeTo + ".src");
    }
  };
};
 

//opens a new window with supplied args
function opennewwindow(theURL,winName,features) {
  window.open(theURL,winName,features);
  return false;
}

// popup window script to enable popup windows
function popWindow(popURL,popWidth,popHeight) {
                screenShot=window.open(
                popURL,
                "screenShot",
                "toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0,width="+popWidth+",height="+popHeight
                );
        screenShot.opener=self;
        setTimeout("screenShot.focus();",200);
};

// popup window script to enable popup windows
function popWindow(popURL,popTitle) {
                screenShot=window.open(
                popURL,
                popTitle,
                "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165"
                );
        screenShot.opener=self;
        setTimeout("screenShot.focus();",200);
};


//checks or unchecks all checkboxes on a page
function checkAllBoxes()
  {
    len = document.fin.elements.length;

    var i=0;
        if(document.fin.checkAllBox.checked==true){
          for( i=0; i<len; i++)
        document.fin.elements[i].checked=true;
        }
        else{
          for( i=0; i<len; i++)
        document.fin.elements[i].checked=false;
    }
}


function popLocateGlobal(pLoc, parm) {
  var loc = pLoc + ".jsp?parm=" + parm;
  if(parm != "print"){
    locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }else{
    locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }
  locatewin.focus();
}

function popLocateFeedGlobal(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popPrintGlobal(pLoc) {
  var loc = pLoc;
  locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popLinkGlobal(pLoc) {
  var loc = pLoc;
  locatewin = window.open(loc,"Note", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popUpdateGlobal(pLoc, parm) {
  var loc = pLoc + parm;
  locatewin = window.open(loc,"Update", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//if pNode is not supplied then the new child will be prepended
function appendChildAt(pParentNode, pNewChildNode,pNode){
        var myKids;
        if(pParentNode.childNodes){
                myKids = pParentNode.childNodes;
        }else{
                myKids = new Array();
        }
        var myLength = myKids.length;
        var myNewKids = new Array();
        var lastOccurance = 0;

        for(var i=0;i<myKids.length;i++){
                myNewKids[i+1] = myKids[i];
                if(pNode != null){
                        if(myKids[i] == pNode){
                                lastOccurance = i + 1;
                        }
                }
        }
        if(null == pNode){
                lastOccurance = 0;
        }
        myNewKids[lastOccurance] = pNewChildNode;
        for(var i=lastOccurance;i<myKids.length;i++){
                pParentNode.replaceChild(myNewKids[i],myKids[i]);
        }
        pParentNode.appendChild(myNewKids[myNewKids.length-1]);
}

function getBoldedTextElement(message){
        var boldEle = document.createElement("B");
        text = document.createTextNode(message);
        boldEle.appendChild(text);
        return boldEle;
}

var getAllElementByNameRegexCache = new Array();
var getAllElementByNameRegexCacheIndex = new Array();
var getAllElementByNameRegexCacheCurrentCahcedNode;

function getAllElementByNameRegex(pNode, pList, pPattern){
        if(getAllElementByNameRegexCacheCurrentCahcedNode != pNode){
                getAllElementByNameRegexCacheCurrentCahcedNode = pNode;
                getAllElementByNameRegexCache = new Array();
                getAllElementByNameRegexCacheIndex = new Array();
        }
        if(getAllElementByNameRegexCache.length == 0){
                cacheAllElementByNameRegex(pNode);
        }
        if(!pList){
                pList = new Array();
        }
        for(var i=0,len=getAllElementByNameRegexCacheIndex.length;i<len;i++){
                var myMatch = getAllElementByNameRegexCacheIndex[i].match(pPattern);
                if(myMatch){
                         pList[pList.length] = getAllElementByNameRegexCache[i];
                 }
         }

         return pList;
}

function cacheAllElementByNameRegex(pNode){

        var myNode = pNode;
        var myMatch = null;
        var myId = myNode.name;
        if(myId){
                getAllElementByNameRegexCacheIndex[getAllElementByNameRegexCacheIndex.length] = myId;
                getAllElementByNameRegexCache[getAllElementByNameRegexCache.length] = myNode;
        }

        var myKids;
        myKids = myNode.childNodes;
        if(myKids){
                for(var i=0,len=myKids.length;i<len;i++){
                        cacheAllElementByNameRegex(myKids[i]);
                }
        }
}

function contains(pArray,pValue){
        if(pArray){
         for(var i=0,len=pArray.length;i<len;i++){
                 if(pArray[i] == pValue){
                         return true;
                 }
         }
        }
        return false;
}

//from http://www.faqs.org/docs/htmltut/forms/index_famsupp_157.html
//submits the form the supplied field is attached to.  Optionally will set the forms f_action parameter if it is supplied
function submitenter(myfield,e,optAction)
{
        var keycode;
        if (window.event){
                keycode = window.event.keyCode;
        }else if (e){
                keycode = e.which;
        }else{
                return true;
        }
        if (keycode == 13){
                if(optAction != null){
                        myfield.form.f_action.value=optAction;
                }
                myfield.form.submit();
                return false;
        }else{
                return true;
        }
}

/*Finds the HeaderDeleteAllCheckBox and uncheckes it.
*/
function UncheckDeleteAll() {
  var cb = document.getElementById('HeaderDeleteAllCheckBox');
  if(cb!=null && cb.checked) {
	  cb.checked=false;
  }
}

/*sets all values in a form to the value that start with the supplied checkFormEle param*/
function SetCheckedGlobal(val,checkFormEle) {
 if (checkFormEle == null || checkFormEle.length == 0) return;
 var re = new RegExp(checkFormEle);
 for( var fi=0 ; fi<document.forms.length ; fi++) {
  var dml=document.forms[fi];
  var len = dml.elements.length;
  for( var i=0 ; i<len ; i++) {
    if (re.test(dml.elements[i].name)) {
      dml.elements[i].checked=val;
    }
  }
 }
}

/*sets all values in a form to the value that start with the supplied checkFormEle param*/
function SetCheckedGlobalRev(checkFormEle) {
 for( fi=0 ; fi<document.forms.length ; fi++) {
  dml=document.forms[fi];
  len = dml.elements.length;
  var valToSetTo=0;
  var i=0;
  //if anything is unchecked then we want to check mark everything
  //loop through list to determine this.
  for( i=0 ; i<len ; i++) {
    if (dml.elements[i].name.match(checkFormEle)==checkFormEle) {
      if (dml.elements[i].checked==0){
        valToSetTo=1;
        break;
      }
    }
  }
  for( i=0 ; i<len ; i++) {
    if (dml.elements[i].name.match(checkFormEle)==checkFormEle) {
      dml.elements[i].checked=valToSetTo;
    }
  }
 }
}


/*sets all values in a form to the value that start with the supplied checkFormEle param*/
function SetValuesGlobal(val,elementName) {
 for( fi=0 ; fi<document.forms.length ; fi++) {
  dml=document.forms[fi];
  len = dml.elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (dml.elements[i].name.match(elementName)==elementName) {
      dml.elements[i].value=val;
    }
  }
 }
}

function f_reset_fields(pForm) {

ele = pForm.elements;
var s = '';
for ( i = 0; i < ele.length; i++ ) {
 if ( ele[i].type == "text" &&
       ele[i].name != "orderDateRangeBegin" ) {
   ele[i].value = "";
 }
 s+= '<br> i=' + i + ' name=' + ele[i].name;
}

// uncomment to debug
// alert('reset fields for forms ' + document.forms.length + " has ele " + ele.length + '<br>s=' + s );

}


function f_resetFormById(pFormId) {

 var dml = document.getElementById(pFormId);
 if ( null == dml ) {
   alert('no form found for pFormId=' + pFormId);
   return;
 }

 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].type == "text" ) {
     dml.elements[i].value = "";
   }
 }
}

function f_setAllDistValue(pForm, elName, suff) {
  var newValue = pForm.elements[elName].value;
  var pattern = "[distributor|newMapping].*" + suff;
  for (var i=0; i<pForm.elements.length; i++) {
    var name = pForm.elements[i].name;
    if (name == null) continue;
    if (name.match(pattern)) {
      pForm.elements[i].value = newValue;
    }
  }
  return false;
}

function f_setAllPriceValue(pForm, newValElName, elName) {
  var newValue = pForm.elements[newValElName].value;
  var pattern = elName + ".*";
  for (var i=0; i<pForm.elements.length; i++) {
    var name = pForm.elements[i].name;
    if (name == null) continue;
    if (name.match(pattern)) {
      pForm.elements[i].value = newValue;
    }
  }
  return false;
}



function f_setCheckClearAll(pForm, elName, onOff) {
  //alert(pForm.elements[elName].length);
  for (var i=0; i<pForm.elements.length; i++) {
    if (pForm.elements[i].name == elName) {
      pForm.elements[i].checked = onOff;
    }
  }
  return false;
}

function f_setCheckClearArray(pForm, arrayName, count, onOff) {
    for (var i = 0; i < count; i++) {
        var element = document.forms[pForm][(arrayName + "[" + i + "]")];
        if (element != null && 'undefined' != typeof element) {
            element.checked = onOff;
        }
    }
    return false;
}

function addLoadEvent(func) {   
   var oldonload = window.onload;   
   if (typeof window.onload != 'function') {   
     window.onload = func;   
   } else {   
     window.onload = function() {   
       if (oldonload) {   
         oldonload();   
       }   
       func();   
     }   
   }   
}

function adm2Submit(formId, action, value) {
    var actions;
    actions = document.forms[formId][action];
    var forms = document.forms;
    if ('undefined' != typeof actions.length) {
        for (var i = actions.length - 1; i >= 0; i--) {
            if (actions[i].id == 'hiddenAction') {
                actions[i].value = value;
                document.forms[formId].submit();
                break;
            }
        }
    } else {
        document.forms[formId][action].value = value;
        document.forms[formId].submit();
    }
    return false;
}

