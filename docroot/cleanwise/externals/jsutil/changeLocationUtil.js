var clCountryListDynamicBox = {

    display:false,
    countrySelectElementName:"",
    countryEmptyMessage:"",
    countrySelectElement:"",

    setCountryBody:function(array, currentValue, countryEmptyMessage, selectElementId) {

        clCountryListDynamicBox.countrySelectElementId = selectElementId;
        clCountryListDynamicBox.countryEmptyMessage = countryEmptyMessage;
        clCountryListDynamicBox.countrySelectElement = document.getElementById(clCountryListDynamicBox.countrySelectElementId);

        loadDataToSelectObject(clCountryListDynamicBox.countrySelectElement, array, currentValue, countryEmptyMessage);
    },

    updateCountryBody:function(array, currentValue) {
        loadDataToSelectObject(clCountryListDynamicBox.countrySelectElement, array, currentValue, clCountryListDynamicBox.countryEmptyMessage);
    },

    init:function(array, currentValue, countryEmptyMessage, selectElementId) {
        clCountryListDynamicBox.setCountryBody(array, currentValue, countryEmptyMessage, selectElementId);
        clCountryListDynamicBox.display = true;
    },

   populateAndReDraw:function(countryJson, thiDiv) {

        var array;
        var currentValue = "";
        if (countryJson.list && countryJson.list.length) {
            currentValue = countryJson.selected
            array = new Array();
            for (var i = 0,j = 0; i < countryJson.list.length; i++) {
                var countryVal = countryJson.list[i].val;
                array[j] = new Array();
                array[j][0] = countryVal;
                array[j][1] = countryVal;
                j++;
            }
        }
        clCountryListDynamicBox.updateCountryBody(array, currentValue);
    }
}


var clSiteListDynamicBox = {

    display:false,
    siteSelectElementId:"",
    siteEmptyMessage:"",
    siteSelectElement:"",


    setSiteBody:function(array, currentValue, siteEmptyMessage, selectElementId) {

        clSiteListDynamicBox.siteSelectElementId = selectElementId;
        clSiteListDynamicBox.siteEmptyMessage = siteEmptyMessage;
        clSiteListDynamicBox.siteSelectElement = document.getElementById(clSiteListDynamicBox.siteSelectElementId);
        loadDataToSelectObject(clSiteListDynamicBox.siteSelectElement, array, currentValue, siteEmptyMessage);

    },

    updateSiteBody:function(array, currentValue) {
        loadDataToSelectObject(clSiteListDynamicBox.siteSelectElement, array, currentValue, clSiteListDynamicBox.siteEmptyMessage);
    },

    init:function(array, currentValue, siteEmptyMessage, selectElementId) {
        clSiteListDynamicBox.setSiteBody(array, currentValue, siteEmptyMessage, selectElementId);
        clSiteListDynamicBox.display = true;
    },

     populateAndReDraw:function(siteJson, thiDiv) {

        var array;
        var currentValue = "";
        if (siteJson.list && siteJson.list.length) {
            currentValue = siteJson.selected
            array = new Array();
            for (var i = 0,j = 0; i < siteJson.list.length; i++) {
                var siteVal = siteJson.list[i].val;
                var siteId = siteJson.list[i].id;
                array[j] = new Array();
                array[j][0] = siteId;
                array[j][1] = siteVal;
                j++;
            }
        }
        clSiteListDynamicBox.updateSiteBody(array, currentValue);
    }
}


var clStateListDynamicBox = {

    display:false,
    stateSelectElementId:"",
    stateEmptyMessage:"",
    stateSelectElement:"",

    setStateBody:function(array, currentValue, stateEmptyMessage, selectElementId) {

        clStateListDynamicBox.stateSelectElementId = selectElementId;
        clStateListDynamicBox.stateEmptyMessage = stateEmptyMessage;
        clStateListDynamicBox.stateSelectElement = document.getElementById(selectElementId);
        loadDataToSelectObject(clStateListDynamicBox.stateSelectElement, array, currentValue, stateEmptyMessage);

    },


    updateStateBody:function(array, currentValue) {
        loadDataToSelectObject(clStateListDynamicBox.stateSelectElement, array, currentValue, clStateListDynamicBox.stateEmptyMessage);
    },

    init:function(array, currentValue, stateEmptyMessage, selectElementId) {
        clStateListDynamicBox.setStateBody(array, currentValue, stateEmptyMessage, selectElementId);
        clStateListDynamicBox.display = true;
    },

    populateAndReDraw:function(stateJson, thiDiv) {
        var array;
        var currentValue = "";
        if (stateJson.list && stateJson.list.length) {
            currentValue = stateJson.selected
            array = new Array();
            for (var i = 0,j = 0; i < stateJson.list.length; i++) {
                var stateVal = stateJson.list[i].val
                array[j] = new Array();
                array[j][0] = stateVal;
                array[j][1] = stateVal;
                j++;
            }
        }
        clStateListDynamicBox.updateStateBody(array, currentValue);
    }

}

var locationCriteria = {

   update:function(responseJson, thiDiv) {

        if (responseJson.response.sites && responseJson.response.sites.redraw && clSiteListDynamicBox.display) {
            clSiteListDynamicBox.populateAndReDraw(responseJson.response.sites, thiDiv);
        }

        if (responseJson.response.states && responseJson.response.states.redraw && clStateListDynamicBox.display) {
            clStateListDynamicBox.populateAndReDraw(responseJson.response.states, thiDiv);
        }

        if (responseJson.response.countries && responseJson.response.countries.redraw && clCountryListDynamicBox.display) {
            clCountryListDynamicBox.populateAndReDraw(responseJson.response.countries, thiDiv);
        }

    }
}

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
};

function removeAllSelectObjectOptions(oListbox) {
    if (oListbox.options) {
        oListbox.options.length = 0;
    }
};

function loadDataToSelectObject(object, array, currentValue, emptyMessage) {
    if (object) {
        removeAllSelectObjectOptions(object);
        addOptionToSelectObject(object, emptyMessage, "", true)
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
};
