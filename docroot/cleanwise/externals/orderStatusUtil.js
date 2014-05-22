var osStateListDynamicBox = {

    display:false,
    selectElementId:"",
    selectElement:"",
    stateEmptyMessage:"",

    setStateBody:function(array, currentValue, stateEmptyMessage,selectElementId) {
        osStateListDynamicBox.selectElementId = selectElementId;
        osStateListDynamicBox.stateEmptyMessage=stateEmptyMessage;
        osStateListDynamicBox.selectElement = document.getElementById(selectElementId);
        loadDataToSelectObject(osStateListDynamicBox.selectElement, array, currentValue);
    },


    updateStateBody:function(array, currentValue) {
        removeAllSelectObjectOptions(osStateListDynamicBox.selectElement);
        loadDataToSelectObject(osStateListDynamicBox.selectElement, array, currentValue);
    },

    init:function(array, currentValue,stateEmptyMessage, selectElementId) {
        osStateListDynamicBox.setStateBody(array, currentValue,stateEmptyMessage, selectElementId);
        osStateListDynamicBox.display = true;
    },

    populateAndReDraw:function(stateJson, thiDiv) {

        var array = new Array();
        var currentValue = "";

        array[0] =  new Array();
        array[0][0] = "";
        array[0][1] = osStateListDynamicBox.stateEmptyMessage

        if (stateJson.list && stateJson.list.length) {
            currentValue = stateJson.selected
            for (var i = 0,j = 1; i < stateJson.list.length; i++) {
                var stateVal = stateJson.list[i].val
                array[j] = new Array();
                array[j][0] = stateVal;
                array[j][1] = stateVal;
                j++;
            }
        }
        osStateListDynamicBox.updateStateBody(array, currentValue);
    } ,

    onChange:function() {
        if (osStateListDynamicBox.display) {
            setDisabledFlagToSelectObject(osSiteListDynamicBox, true)
            dojo.xhrGet({load:locationCriteria.update,url:'orderstatus.do?action=changeState&newState='+osStateListDynamicBox.selectElement.value,handleAs:'json'});
        }
    }
}

var osSiteListDynamicBox = {

    display:false,
    selectElementId:"",
    selectElement:"",
    siteEmptyMessage:"",

    setSiteBody:function(array, currentValue, siteEmptyMessage,selectElementId) {
        osSiteListDynamicBox.selectElementId = selectElementId;
        osSiteListDynamicBox.siteEmptyMessage = siteEmptyMessage;
        osSiteListDynamicBox.selectElement = document.getElementById(osSiteListDynamicBox.selectElementId);
        loadDataToSelectObject(osSiteListDynamicBox.selectElement, array, currentValue);

    },

    updateSiteBody:function(array, currentValue) {
        removeAllSelectObjectOptions(osSiteListDynamicBox.selectElement);
        loadDataToSelectObject(osSiteListDynamicBox.selectElement, array, currentValue);
    },

    init:function(array, currentValue,siteEmptyMessage, selectElementId) {
        osSiteListDynamicBox.setSiteBody(array, currentValue,siteEmptyMessage, selectElementId);
        osSiteListDynamicBox.display = true;
    },

    populateAndReDraw:function(siteJson, thiDiv) {

        var array = new Array();
        var currentValue = "";

        array[0] =  new Array();
        array[0][0] = "";
        array[0][1] = osSiteListDynamicBox.siteEmptyMessage;

        if (siteJson.list && siteJson.list.length) {
            currentValue = siteJson.selected
            for (var i = 0,j = 1; i < siteJson.list.length; i++) {
                var siteVal = siteJson.list[i].val;
                var siteId = siteJson.list[i].id;
                array[j] = new Array();
                array[j][0] = siteId;
                array[j][1] = siteVal;
                j++;
            }
        }
        osSiteListDynamicBox.updateSiteBody(array, currentValue);
    },

    onChange:function() {
        if (osSiteListDynamicBox.display) {
           dojo.xhrGet({load:locationCriteria.update,url:'orderstatus.do?action=changeSite&newSite=' + osSiteListDynamicBox.selectElement.value,handleAs:'json'});
        }
    }
}

var osCountryListDynamicBox = {

    display:false,
    selectElementName:"",
    selectElement:"",
    countryEmptyMessage:"",

    setCountryBody:function(array, currentValue, countryEmptyMessage, selectElementId) {

        osCountryListDynamicBox.selectElementId = selectElementId;
        osCountryListDynamicBox.countryEmptyMessage = countryEmptyMessage;
        osCountryListDynamicBox.selectElement = document.getElementById(osCountryListDynamicBox.selectElementId);
        loadDataToSelectObject(osCountryListDynamicBox.selectElement, array, currentValue);
    },

    updateCountryBody:function(array, currentValue) {
        removeAllSelectObjectOptions(osCountryListDynamicBox.selectElement);
        removeAllSelectObjectDataOptions(osCountryListDynamicBox.selectElement);
        loadDataToSelectObject(osCountryListDynamicBox.selectElement, array, currentValue);
    },

    init:function(array, currentValue, countryEmptyMessage,selectElementId) {
        osCountryListDynamicBox.setCountryBody(array, currentValue, countryEmptyMessage, selectElementId);
        osCountryListDynamicBox.display = true;
    },

    populateAndReDraw:function(countryJson, thiDiv) {

        var array = new Array();
        var currentValue = "";

        array[0] =  new Array();
        array[0][0] = "";
        array[0][1] = osCountryListDynamicBox.countryEmptyMessage;

        if (countryJson.list && countryJson.list.length) {
            currentValue = countryJson.selected
            for (var i = 0,j = 1; i < countryJson.list.length; i++) {
                var countryVal = countryJson.list[i].val;
                array[j] = new Array();
                array[j][0] = countryVal;
                array[j][1] = countryVal;
                j++;
            }
        }

        osCountryListDynamicBox.updateCountryBody(array, currentValue);
    },

    onChange:function(){
        if (osCountryListDynamicBox.display) {
            setDisabledFlagToSelectObject(osStateListDynamicBox, true);
            setDisabledFlagToSelectObject(osSiteListDynamicBox, true)
            dojo.xhrGet({load:locationCriteria.update,url:'orderstatus.do?action=changeCountry&newCountry=' + osCountryListDynamicBox.selectElement.value,handleAs:'json'});
        }
    }
}

var locationCriteria = {

    update:function(responseJson, thiDiv) {

        if (responseJson.response.sites && responseJson.response.sites.redraw && osSiteListDynamicBox.display) {
            osSiteListDynamicBox.populateAndReDraw(responseJson.response.sites, thiDiv);
        }

        if (responseJson.response.states && responseJson.response.states.redraw && osStateListDynamicBox.display) {
            osStateListDynamicBox.populateAndReDraw(responseJson.response.states, thiDiv);
        }

        if (responseJson.response.countries && responseJson.response.countries.redraw && osCountryListDynamicBox.display) {
            osCountryListDynamicBox.populateAndReDraw(responseJson.response.countries, thiDiv);
        }

        setDisabledFlagToSelectObject(osCountryListDynamicBox, false);
        setDisabledFlagToSelectObject(osStateListDynamicBox, false);
        setDisabledFlagToSelectObject(osSiteListDynamicBox, false);

    }}

function addOptionToSelectObject(oListbox, text, value, isDefaultSelected, isSelected) {

    var oOption = document.createElement("option");

    oOption.appendChild(document.createTextNode(text));
    oOption.setAttribute("value", value);

    if (isDefaultSelected) {
        oOption.defaultSelected = true;
    } else if (isSelected) {
        oOption.selected = true;
    }

    oListbox.appendChild(oOption);
}
;

function removeAllSelectObjectOptions(oListbox) {
    if (oListbox.options) {
        oListbox.options.length = 0;
    }
}

function loadDataToSelectObject(object, array, currentValue) {

    if (object) {
        if (array != null && 'undefined' != typeof array
                && array.length != null && 'undefined' != typeof array.length) {
            for (var i = 0; i < array.length; i++) {
                if (currentValue == array[i][0]) {
                    addOptionToSelectObject(object, array[i][1], array[i][0], false, true);
                } else {
                    addOptionToSelectObject(object, array[i][1], array[i][0]);
                }
            }
        }
    }
}

function setDisabledFlagToSelectObject(object, flag) {
    if(object && object.display && object.selectElement){
        if(object.selectElement.disabled!= flag){
            object.selectElement.disabled = flag;
        }
    }
}


