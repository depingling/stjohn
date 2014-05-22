var locationDynamicBox = {

    streetAdressVal:"",
    cityVal:"",
    stateVal:"",
    postalCdVal:"",
    streetAdressMess:"",
    cityMess:"",
    stateMess:"",
    postalCdMess:"",
    streetAdressBody:"",
    cityBody:"",
    stateBody:"",
    postalCdBody:"",
    streetAdressHeader:"",
    cityHeader:"",
    stateHeader:"",
    postalCdHeader:"",
    body:"",
    to:"",
    initWriterFlag:"",
    header:"",
    mainBody:"",
    headerMessage:"",
    control:"",
    update:function() {
        locationDynamicBox.mainBody =
        "<TABLE width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">" +
        locationDynamicBox.header +
        locationDynamicBox.body +
        "</TABLE>"
    },

    init:function(headerMessage,
                  streetAdressMess,
                  cityMess,
                  stateMess,
                  postalCdMess,
                  streetAdress,
                  city,
                  state,
                  postalCd,
                  controlValue,
                  controlFunction,
                  controlRender)
    {
        locationDynamicBox.loadControl(controlValue, controlFunction, controlRender);
        locationDynamicBox.loadMessage(headerMessage, streetAdressMess, cityMess, stateMess, postalCdMess);
        locationDynamicBox.loadHeader();
        locationDynamicBox.loadVal(streetAdress, city, state, postalCd);
        locationDynamicBox.loadBody();

    },

    loadControl:function(controlValue, controlFunction, controlRender) {
        if (controlRender == "true") {
            locationDynamicBox.control += "<input class=\"store_fb\" type=\"button\" name=\"action\" value=\"" + controlValue + "\" onclick=\"" + controlFunction + "\"/>";
        } else {
            locationDynamicBox.control = "";
        }
    },


    loadHeader:function() {
        locationDynamicBox.streetAdressHeader = "<td class=\"shopcharthead\"><div class=\"fivemargin\" id=\"lStreetAdressMess\">" + locationDynamicBox.streetAdressMess + "</div></td>";
        locationDynamicBox.cityHeader         = "<td class=\"shopcharthead\"><div class=\"fivemargin\" id=\"lCityMess\">" + locationDynamicBox.cityMess + "</div></td>";
        locationDynamicBox.stateHeader        = "<td class=\"shopcharthead\"><div class=\"fivemargin\" id=\"lStateMess\">" + locationDynamicBox.stateMess + "</div></td>";
        locationDynamicBox.postalCdHeader     = "<td class=\"shopcharthead\"><div class=\"fivemargin\" id=\"lPostalCdMess\">" + locationDynamicBox.postalCdMess + "</div></td>";
        locationDynamicBox.header = "<tr>" +
                                    "<td class=customerltbkground vAlign=top align=middle colspan=\"4\">" +
                                    "<SPAN class=shopassetdetailtxt><B>" + locationDynamicBox.headerMessage + "</B></SPAN>" +
                                    "&nbsp;"+locationDynamicBox.control + "</tr>";


        locationDynamicBox.header+="<tr class=\"tableheaderinfo\">" + locationDynamicBox.streetAdressHeader + locationDynamicBox.cityHeader + locationDynamicBox.stateHeader + locationDynamicBox.postalCdHeader + "</tr>";
    },

    loadMessage:function(headerMessage, streetAdressMess, cityMess, stateMess, postalCdMess) {
        locationDynamicBox.headerMessage = headerMessage;
        locationDynamicBox.streetAdressMess = streetAdressMess;
        locationDynamicBox.cityMess = cityMess;
        locationDynamicBox.stateMess = stateMess;
        locationDynamicBox.postalCdMess = postalCdMess;
    },

    loadBody:function() {
        locationDynamicBox.streetAdressBody = "<td><div id=\"lStreetAdress\">" + locationDynamicBox.streetAdressVal + "</div></td>";
        locationDynamicBox.cityBody = "<td><div id=\"lCity\">" + locationDynamicBox.cityVal + "</div></td>";
        locationDynamicBox.stateBody = "<td><div id=\"lState\">" + locationDynamicBox.stateVal + "</div></td>";
        locationDynamicBox.postalCdBody = "<td><div id=\"lPostalCd\">" + locationDynamicBox.postalCdVal + "</div></td>";
        locationDynamicBox.body = "<tr>" + locationDynamicBox.streetAdressBody + locationDynamicBox.cityBody + locationDynamicBox.stateBody + locationDynamicBox.postalCdBody + "</tr>";
    } ,

    loadVal:function(streetAdress, city, state, postalCd) {
        locationDynamicBox.streetAdressVal = streetAdress;
        locationDynamicBox.cityVal = city;
        locationDynamicBox.stateVal = state;
        locationDynamicBox.postalCdVal = postalCd;
    },

    initWriter:function(theDiv) {
        locationDynamicBox.to = theDiv;
        locationDynamicBox.initWriterFlag = true;
    },

    write:function() {
        if (locationDynamicBox.initWriterFlag) {
            var to = document.getElementById(locationDynamicBox.to);
            locationDynamicBox.update();
            to.innerHTML = locationDynamicBox.mainBody;
        }
    },

    redraw:function() {
        if (locationDynamicBox.initWriterFlag) {
            var body = document.getElementById(locationDynamicBox.to);
            locationDynamicBox.loadBody();
            locationDynamicBox.update();
            body.innerHTML = locationDynamicBox.mainBody;
        }
    },

    redrawVal:function() {
        if (locationDynamicBox.initWriterFlag) {
            var body = document.getElementById(locationDynamicBox.to);
            locationDynamicBox.update();
            document.getElementById("lStreetAdress").innerHTML = locationDynamicBox.streetAdressVal;
            document.getElementById("lCity").innerHTML = locationDynamicBox.cityVal;
            document.getElementById("lState").innerHTML = locationDynamicBox.stateVal;
            document.getElementById("lPostalCd").innerHTML = locationDynamicBox.postalCdVal;
        }
    },

    populateAndReDraw:function(responseXml, thiDiv) {
        var city = "";
        var state = "";
        var postalCd = "";
        var streetAddress = "";

        if (responseXml != null) {

            var root = responseXml.getElementsByTagName("Address")[0];

            if (root != null && 'undefined' != typeof root) {

                var streetAddressObj = root.getElementsByTagName("Address1")[0];
                if (streetAddressObj != null && 'undefined' != typeof streetAddressObj) {
                    streetAddress = streetAddressObj.firstChild.nodeValue;
                }

                var cityObj = root.getElementsByTagName("City")[0];
                if (cityObj != null && 'undefined' != typeof cityObj) {
                    city = cityObj.firstChild.nodeValue;
                }

                var stateObj = root.getElementsByTagName("StateProvinceCd")[0];
                if (stateObj != null && 'undefined' != typeof stateObj) {
                    state = stateObj.firstChild.nodeValue;
                }

                var postalCdObj = root.getElementsByTagName("PostalCode")[0];
                if (postalCdObj != null && 'undefined' != typeof postalCdObj) {
                    postalCd = postalCdObj.firstChild.nodeValue;
                }
            }
        }

        if (streetAddress == null || streetAddress == "null") {
            streetAddress = ""
        }

        if (city == null || city == "null") {
            city = ""
        }

        if (state == null || state == "null") {
            state = ""
        }

        if (postalCd == null || postalCd == "null") {
            postalCd = ""
        }

        locationDynamicBox.loadVal(streetAddress, city, state, postalCd);
        locationDynamicBox.redrawVal();

    }
}
