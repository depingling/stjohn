function addSubEntityById(id,jsLevel){
        node = document.getElementById(id);
        addSubEntity(node,jsLevel);
}



function addSubEntity(node,jsLevel){
        var origNode = node;
        if(node == null){
                return;
        }
        var valueList = getChildernArrayFromNode(node,jsLevel,"childrenElement");
        var pathToProfile = valueList[0];
        var nextChild = Number(valueList[1]) + 1;
        node = valueList[2];
        var now = new Date();
        var guid = now.getTime();

        //create a contrainer for the new elements to make removal easier
        var container = document.createElement("DIV");
        container.id="DIV"+guid;

        var input0 = document.createElement("INPUT");
        input0.id="INPUT0"+guid;
        input0.type="text";
        input0.size="10";
        input0.name=pathToProfile + "childrenElement["+nextChild+"].profileOrderString";

        var input1 = document.createElement("INPUT");
        input1.id="INPUT1"+guid;
        input1.type="text";
        input1.size="10";
        input1.name=pathToProfile + "childrenElement["+nextChild+"].profileView.profileData.shortDesc";
        //input1.value=pathToProfile + "childrenElement["+nextChild+"].profileView.profileData.shortDesc";


        var input1EditLink = document.createElement("A");
        input1EditLink.id = "A3"+guid;
        input1EditLink.href='javaScript:popLocateGlobal("../general/textEdit","INPUT1'+guid+'")';
        linkText = document.createTextNode("(e)");
        input1EditLink.appendChild(linkText);


        var input2 = document.createElement("INPUT");
        input2.id="INPUT2"+guid;
        input2.type="text";
        input2.size="10";
        input2.name=pathToProfile + "childrenElement["+nextChild+"].profileView.profileData.helpText";

        var input2EditLink = document.createElement("A");
        input2EditLink.id = "A4"+guid;
        input2EditLink.href='javaScript:popLocateGlobal("../general/textEdit","INPUT2'+guid+'")';
        linkText = document.createTextNode("(e)");
        input2EditLink.appendChild(linkText);

        var select1 = document.createElement("SELECT");
        select1.id="SELECT1"+guid;
        select1.name=pathToProfile + "childrenElement["+nextChild+"].profileView.profileData.profileQuestionTypeCd";
        select1.options[0] = document.createElement("OPTION");
        select1.options[1] = document.createElement("OPTION");
        select1.options[2] = document.createElement("OPTION");
        select1.options[3] = document.createElement("OPTION");
        select1.options[0].value='FREE_FORM_TEXT';
        select1.options[1].value='MULTIPLE_CHOICE';
        select1.options[2].value='MULTIPLE_CHOICES';
        select1.options[3].value='NUMBER';
        select1.options[0].text=select1.options[0].value;
        select1.options[1].text=select1.options[1].value;
        select1.options[2].text=select1.options[2].value;
        select1.options[3].text=select1.options[3].value;
        select1.options[0].defaultSelected=1;

        var select2 = document.createElement("SELECT");
        select2.id="SELECT2"+guid;
        select2.name=pathToProfile + "childrenElement["+nextChild+"].profileView.profileData.readOnly";
        select2.options[0] = document.createElement("OPTION");
        select2.options[1] = document.createElement("OPTION");
        select2.options[0].value='false';
        select2.options[1].value='true';
        select2.options[0].text=select2.options[0].value;
        select2.options[1].text=select2.options[1].value;
        select2.options[0].defaultSelected=1;

        var removeLink = document.createElement("A");
        removeLink.id = "A"+guid;
        removeLink.href="javascript:removeElementByBaseId("+guid+")";
        linkText = document.createTextNode("(Remove)");
        removeLink.appendChild(linkText);

        var addChildLink = document.createElement("A");
        addChildLink.id = "A2"+guid;
        var newJsLevel = 1 + jsLevel;
        addChildLink.href="JavaScript:addSubEntityById('A2"+guid+"',"+ newJsLevel +")";
        addChildLink.name=pathToProfile + "childrenElement["+nextChild+"].";
        var linkText = document.createTextNode("Add Child Question");
        addChildLink.appendChild(linkText);

        var addAnsLink = document.createElement("A");
        addAnsLink.id = "A5"+guid;
        var newJsLevel = 1 + jsLevel;
        addAnsLink.href="javascript:addMetaData(this,"+newJsLevel+")";
        var linkText = document.createTextNode("Add Answer");
        addAnsLink.appendChild(linkText);


        var text = "";
        for(i=0;i<jsLevel;i++){
                text = text + "*";
        }
        var textEle = document.createTextNode(text);
        container.appendChild(document.createElement("BR"));
        //container.appendChild(getBoldedTextElement(container.id));
        container.appendChild(textEle);
        container.appendChild(getBoldedTextElement(" Q:"));
        container.appendChild(input1);
        container.appendChild(input1EditLink);
        container.appendChild(getBoldedTextElement(" Order:"));
        container.appendChild(input0);
        container.appendChild(getBoldedTextElement(" Help:"));
        container.appendChild(input2);
        container.appendChild(input2EditLink);
        container.appendChild(getBoldedTextElement(" Type:"));
        container.appendChild(select1);
        container.appendChild(getBoldedTextElement(" Read Only:"));
        container.appendChild(select2);
        container.appendChild(removeLink);
        container.appendChild(getBoldedTextElement(" "));
        container.appendChild(addChildLink);
        //container.appendChild(getBoldedTextElement(" "));
        //container.appendChild(addAnsLink);


        var myNode = origNode.parentNode;
        debug(myNode.tagName + ":" + myNode.id + ":" + myNode.name);
        myNode.appendChild(container);
}

function addMetaData(node,jsLevel){
        var origNode = node;
        if(node == null){
                return;
        }
        var valueList = getChildernArrayFromNode(node,jsLevel,"profileMetaDataVectorElement");
        var pathToProfile = valueList[0];
        var nextChild = Number(valueList[1]) + 1;
        node = valueList[2];
        var now = new Date();
        var guid = now.getTime();

        //create a contrainer for the new elements to make removal easier
        var container = document.createElement("DIV");
        container.id="DIV"+guid;
        container.name = "metaData";
        //container.name = pathToProfile + "profileView.profileMetaDataVectorElement["+nextChild+"].container";

        var input1 = document.createElement("INPUT");
        input1.id="INPUTANS1"+guid;
        input1.type="text";
        input1.size="10";
        input1.name=pathToProfile + "profileView.profileMetaDataVectorElement["+nextChild+"].value";
        //input1.value=pathToProfile + "profileView.profileMetaDataVectorElement["+nextChild+"].value";

        var input2 = document.createElement("INPUT");
        input2.id="INPUTANS2"+guid;
        input2.type="text";
        input2.size="10";
        input2.name=pathToProfile + "profileView.profileMetaDataVectorElement["+nextChild+"].helpText";

        var select1 = document.createElement("SELECT");
        select1.id="SELECTANS1"+guid;
        select1.name=pathToProfile + "profileView.profileMetaDataVectorElement["+nextChild+"].profileMetaTypeCd";
        select1.options[0] = document.createElement("OPTION");
        select1.options[1] = document.createElement("OPTION");
        select1.options[2] = document.createElement("OPTION");
        select1.options[3] = document.createElement("OPTION");
        select1.options[0].value='CHOICE';
        select1.options[1].value='OTHER_CHOICE';
        select1.options[2].value='CHOICE_SHOW_CHILDREN';
        select1.options[3].value='OTHER_CHOICE_SHOW_CHILDREN';
        select1.options[0].text=select1.options[0].value;
        select1.options[1].text=select1.options[1].value;
        select1.options[2].text=select1.options[2].value;
        select1.options[3].text=select1.options[3].value;
        select1.options[0].defaultSelected=1;

        var removeLink = document.createElement("A");
        removeLink.id = "AANS"+guid;
        removeLink.href="javascript:removeElementByBaseId("+guid+")";
        var linkText = document.createTextNode("(Remove)");
        removeLink.appendChild(linkText);

        var text = "";
        for(i=1;i<jsLevel;i++){
                text = text + "*";
        }


        container.appendChild(document.createElement("BR"));
        container.appendChild(getBoldedTextElement(text));
        container.appendChild(getBoldedTextElement("Answer:"));
        container.appendChild(input1);
        container.appendChild(getBoldedTextElement("Help:"));
        container.appendChild(input2);
        container.appendChild(getBoldedTextElement("Type:"));
        container.appendChild(select1);
        container.appendChild(removeLink);

        //append it after the last exsisting meta data (maintains order user expects)
        var myNode = origNode.parentNode;
        if(myNode == null || myNode == undefined){
                myNode = origNode;
        }
        var myNodeToInsertAt = origNode;
        while(myNodeToInsertAt.nextSibling != undefined && myNodeToInsertAt.nextSibling.name != undefined && myNodeToInsertAt.nextSibling.name == "metaData"){
                myNodeToInsertAt = myNodeToInsertAt.nextSibling;
        }
        appendChildAt(myNode,container,myNodeToInsertAt);
}



function removeElementByBaseId(eleIdBase){
        eleId = "DIV"+eleIdBase;
        if(eleId == null){
                return;
        }
        ele=document.getElementById(eleId);
        if(ele == null || ele.nodeType != 1){
                return;
        }
        parentEle=ele.parentNode;
        parentEle.removeChild(ele);
}

function stripToPathToProfile(myString){
        return myString.split(/profileView/)[0].split(/profileData/)[0].split(/profileMetaDataVectorElement/)[0].split(/profileDetailDataVector/)[0].split(/container/)[0];
}

function debug(message){
        //alert(message);
}

function getChildernArrayFromNode(node,jsLevel,type){
        var myOriginalId = node.name;
        var myId;
        if(node.name == null || node.name == "" || node.name == undefined){
                myId = "profile.";
                myOriginalId = "profile.";
        }else{
                myId = node.name.replace(/\[/g,"\\[").replace(/\]/g,"\\]");
                //strip out the profile view if we are searching from the main trunk (i.e. not arbitrary other properties)
                if(type == "childrenElement"){
                        myId = myId.replace(/.profileView/,"");
                }
        }
        myId = myId + type;
        var pattern = new RegExp("^"+myId+"");
        debug(pattern);
        myRootNode = document.getElementById('questions');
        var mySiblings = getAllElementByNameRegex(myRootNode,null,pattern);
        var returnArray = new Array();
        //nothing found, assuming adding first question root.
        if(mySiblings == null || mySiblings == undefined || mySiblings.length==0){
                returnArray[0] = stripToPathToProfile(myOriginalId);//new path to use
                returnArray[1] = -1; //last child counter
                returnArray[2] = node;  //reference to the input node we are dealing with
        }else{
                var reg = new RegExp(type);
                var myDepth = myId.split(reg).length;
                var myMaxChildIndex  = -1;
                var myMaxPathToProfile = "";
                var myMaxNode;
                for(var i=0;i<mySiblings.length;i++){

                        var tempPathToProfile = mySiblings[i].name;
                        if (tempPathToProfile != null && tempPathToProfile != "" && tempPathToProfile != "Undefined"){
                                //strip out detail data
                                //tempPathToProfile = stripToPathToProfile(tempPathToProfile);
                                var toParse = tempPathToProfile;
                                var pattern = new RegExp(type + "\\[");
                                var splitArray = toParse.split(pattern);
                                if(splitArray[myDepth] != undefined){
                                        var myIndexAtDepth = splitArray[myDepth].match(/^\d*/);
                                        if(new Number(myIndexAtDepth)>new Number(myMaxChildIndex)){
                                                myMaxChildIndex = myIndexAtDepth;
                                                myMaxNode = mySiblings[i];
                                        }
                                }

                        }

                }
                returnArray[0] = stripToPathToProfile(myOriginalId); //new path to use
                returnArray[1] = myMaxChildIndex; //last child counter
                returnArray[2] = myMaxNode;  //reference to the input node we are dealing with
        }

        return returnArray;
}


function showMyImediateChildren(pNode,multiCorrectAns,multiOtherAns){
        if(!pNode){
                return;
        }
        var newStyle = "";
        //if this is an input tag and nothing was entered don't show the children
        if(pNode.tagName.toLowerCase() == "input"){
                var pattern = RegExp(" *");
                if(pNode.value.replace(pattern,"") == ""){
                        newStyle = 'none';
                }
        }else if(pNode.tagName.toLowerCase() == "select"){

        }
        var showNumber = -1;
        if(pNode.getAttribute("profileTypeCd") == "NUMBER"){
                //show the number of children they entered for the value
                showNumber = new Number(pNode.value);
        }
	else if(pNode.getAttribute("profileTypeCd") == "MULTIPLE_CHOICE" || pNode.getAttribute("profileTypeCd") == "MULTIPLE_CHOICES"){
                if(contains(multiCorrectAns,pNode.value)){
                        showNumber = 1;
                }else{
                        showNumber = 0;
                }

                //deal with the "other" multiple choice values
                var myOtherElementId = pNode.getAttribute("name").replace("value","shortDesc");
                myOtherNode = document.getElementById(myOtherElementId );
                if(myOtherNode){
                   if(contains(multiOtherAns,pNode.value)){
                           myOtherNode.style.display="";
                   }else{
                           myOtherNode.style.display="none";
                   }
                }
        }
	else{ //Free form text, multiple choices etc.
                var pattern = RegExp(" *");
                if(pNode.value.replace(pattern,"") == ""){
                        showNumber = 0;
                }else{
                        showNumber = 1;
                }
        }
        if(isNaN(showNumber)){
                showNumber = 0;
        }
        var myPath = stripToPathToProfile(pNode.name);
        var myTmp = myPath.replace(/\[/g,"\\[").replace(/\]/g,"\\]");
        var pattern = new RegExp("^" + myTmp + "childrenElement*");
        var myRootNode = document.getElementById('questions');
        var myRelatives = getAllElementByNameRegex(myRootNode,null,pattern);


	/*        var t;
        for(var i=0,len=myRelatives.length;i<len;i++){
                 t = t + myRelatives[i].id + "\n";
         }
         alert(myRelatives.length+"\n"+t);
         */

        var myCurDepth = myPath.split("children").length;
        var uniqueCounter = 0;
        var uniqueEleArray = [];
        var lastNum = -1;
        for(var i=0;i<myRelatives.length;i++){
                var myNode = myRelatives[i];
                if(myNode.name != null && myNode.name != undefined){
                        var theName = myNode.name.split("childrenElement");
                        var myNewDepth = theName.length;
                        if(myNewDepth == (myCurDepth + 1))
			{
                                var tKey = new String(myNode.getAttribute("id"));
				var keyVals = tKey.split('.', 2);
				if ( keyVals.length > 1 ) {
				   tKey = keyVals[1];
                                }
				var uKey = new String(tKey);
				uKey = uKey.replace('<b>', '');
				uKey = uKey.replace('</b>', '');
				var uniqueKey = new Number(uKey);

                                if(!isNaN(uniqueKey) && uniqueKey > 0){
                                    if(lastNum + 1 != uniqueKey + 1){
                                        uniqueCounter++;
                                        lastNum = uniqueKey;
                                    }
                                }
                                if(showNumber>=0 && uniqueCounter>showNumber){
                                        newStyle = "none";
                                }
                                myNode.style.display=newStyle;
                        }

                }
        }
}

