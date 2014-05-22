function  BudgetSpentInfo(){
    this.ccName="";
    this.currentBudgetValue="";
    this.allocateValue="";
    this.spentValue="";
    this.differenceValue="";
    this.thisAmountValue="";
    this.budgetWithOrderValue="";
}


var budgetSpentDynamicBox = {
    initWriterFlag:false,
    to:"",
    display:true,
    currentBudgetMessage:"",
    allocatedMessage:"",
    spentMessage:"",
    differenceMessage:"",
    thisAmountMessage:"",
    budgetWithOrderMessage:"",
    headerBody:"",
    bsInfoBody:"",
    mainBody:"",

    initWriter:function(theDiv) {
        budgetSpentDynamicBox.to = theDiv;
        budgetSpentDynamicBox.initWriterFlag = true;
    },

    init:function(currentBudgetMessage, costCenterMessage, allocatedMessage,
                  spentMessage, differenceMessage, thisAmountMessage,
                  budgetWithOrderMessage, budgetSpentInfoValues, authorized, showModeLink) {

        budgetSpentDynamicBox.authorized = authorized;
        if (authorized == 'true') {
            budgetSpentDynamicBox.setHeaderBody(currentBudgetMessage,
                    costCenterMessage,
                    allocatedMessage,
                    spentMessage,
                    differenceMessage,
                    thisAmountMessage,
                    budgetWithOrderMessage,
                    showModeLink);

            budgetSpentDynamicBox.setBudgetSpentBody(budgetSpentInfoValues, budgetSpentDynamicBox.authorized);

            budgetSpentDynamicBox.mainBody = "<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=1 style=\"border:#000000 1px solid\">" +
                                             budgetSpentDynamicBox.headerBody +
                                             budgetSpentDynamicBox.bsInfoBody +
                                             "</table>"

        }

    },

    setHeaderBody:function(currentBudgetMessage,costCenterMessage, allocatedMessage, spentMessage, differenceMessage, thisAmountMessage, budgetWithOrderMessage, showModeLink) {
        budgetSpentDynamicBox.headerBody = "<tr>" +
                                           "<td class=customerltbkground valign=top align=center colSpan=6>" +
                                           "<span style='float: right'>" + showModeLink + "&nbsp;</span><span class=shopassetdetailtxt><b>" + currentBudgetMessage + "</b></span>" +
                                           "</td>" +
                                           "</tr>" +
                                           "<tr>" +
                                           "<td><b>" + costCenterMessage + "</b></td>" +
                                           "<td><b>" + allocatedMessage + "</b></td>" +
                                           "<td><b>" + spentMessage + "</b></td>" +
                                           "<td><b>" + differenceMessage + "</b></td>" +
                                           "<td><b>" + thisAmountMessage + "</b></td>" +
                                           "<td><b>" + budgetWithOrderMessage + "</b></td>" +
                                           "</tr>";
    },

    setBudgetSpentBody:function(budgetSpentInfoValues) {
        if (budgetSpentInfoValues != null && 'undefined' != typeof budgetSpentInfoValues
                && budgetSpentInfoValues.length != null && 'undefined' != typeof budgetSpentInfoValues.length) {
            if(budgetSpentInfoValues.length>0){
                for (var i = 0; i < budgetSpentInfoValues.length; i++) {
                    var bsi = budgetSpentInfoValues[i];
                    budgetSpentDynamicBox.bsInfoBody += "<tr>"
                    budgetSpentDynamicBox.bsInfoBody += "<td>" + bsi.ccName + "</td>";
                    budgetSpentDynamicBox.bsInfoBody += "<td>" + bsi.allocateValue + "</td>";
                    budgetSpentDynamicBox.bsInfoBody += "<td>" + bsi.spentValue + "</td>";
                    budgetSpentDynamicBox.bsInfoBody += "<td>" + bsi.differenceValue + "</td>";
                    budgetSpentDynamicBox.bsInfoBody += "<td>" + bsi.thisAmountValue + "</td>";
                    budgetSpentDynamicBox.bsInfoBody += "<td>" + bsi.budgetWithOrderValue + "</td>";
                    budgetSpentDynamicBox.bsInfoBody += "</tr>";

                }
            }
        }
    },

    write:function() {
        if (budgetSpentDynamicBox.initWriterFlag) {
            var body = document.getElementById(budgetSpentDynamicBox.to);
            if(budgetSpentDynamicBox.display) {
                body.innerHTML = budgetSpentDynamicBox.mainBody;
            } else {
                body.innerHTML = "";
                body.style.visibility="hidden";
            }
        }
    }
}

var serviceProviderDynamicBox ={
    initWriterFlag:false,
    contactNameValue:"",
    contactNameMessage:"",
    contactPhoneValue:"",
    contactPhoneMessage:"",
    contactEmailValue:"",
    contactEmailMessage:"",
    contactFaxValue:"",
    contactFaxMessage:"",
    contactNameBody:"",
    contactPhoneBody:"",
    contactEmailBody:"",
    contactFaxBody:"",
    headerBody:"",
    mainBody:"",
    to:"",
    providerListBody:"",
    providerMessageBody:"",
    editingAuthorized:"",
    providerSelectElementName:"",
    providerBody:"",
    providerSelectElement:"",

    setHeaderBody:function(headerMessage) {
        serviceProviderDynamicBox.headerBody =
        "<tr><td class=customerltbkground vAlign=top align=middle colSpan=4>" +
        "<span class=shopassetdetailtxt>" +
        "<b>" +
        headerMessage +
        "</b>" +
        "</span>" +
        "</td></tr>";
    },

    loadProviderData:function(array, currentValue) {
        if (serviceProviderDynamicBox.editingAuthorized == 'true') {
            if (currentValue == "") {
                serviceProviderDynamicBox.providerListBody = "<option selected=\"true\" value=\"\">Select</option>";
            } else {
                serviceProviderDynamicBox.providerListBody = "<option value=\"\">Select</option>";
            }
            if (array != null && 'undefined' != typeof array
                    && array.length != null && 'undefined' != typeof array.length) {
                for (var i = 0; i < array.length; i++) {
                    if (currentValue == array[i][0]) {
                        serviceProviderDynamicBox.providerListBody += "<option selected=\"true\" value=\"" + array[i][0] + "\">";
                    } else {
                        serviceProviderDynamicBox.providerListBody += "<option value=\"" + array[i][0] + "\">";
                    }
                    serviceProviderDynamicBox.providerListBody += array[i][1];
                    serviceProviderDynamicBox.providerListBody += "</option>";
                }
            }
        } else {
            if (array != null && 'undefined' != typeof array
                    && array.length != null && 'undefined' != typeof array.length) {
                for (var i = 0; i < array.length; i++) {
                    if (currentValue == array[i][0]) {
                        serviceProviderDynamicBox.providerListBody = array[i][1];
                        break;
                    }
                }
            }
        }
    },

    setContactNameBody:function(contactNameMess, contactNameVal) {
        serviceProviderDynamicBox.contactNameMessage = contactNameMess;
        serviceProviderDynamicBox.contactNameValue = contactNameVal;
        serviceProviderDynamicBox.contactNameBody = "<td width=\"30%\"><span class=\"shopassetdetailtxt\"><b><div id=\"spContactNameMessage\">" + serviceProviderDynamicBox.contactNameMessage + "</div></b></span></td><td width=\"20%\"><div id=\"spContactNameValue\">" + serviceProviderDynamicBox.contactNameValue + "</div></td>";
    },

    setContactPhoneBody:function(contactPhoneMess, contactPhoneVal) {
        serviceProviderDynamicBox.contactPhoneMessage = contactPhoneMess;
        serviceProviderDynamicBox.contactPhoneValue = contactPhoneVal;
        serviceProviderDynamicBox.contactPhoneBody = "<td width=\"30%\"><span class=\"shopassetdetailtxt\"><b><div id=\"spContactPhoneMessage\">" + serviceProviderDynamicBox.contactPhoneMessage + "</div></b></span></td><td width=\"20%\"><div id=\"spContactPhoneValue\">" + serviceProviderDynamicBox.contactPhoneValue + "</div></td>"
    },

    setContactEmailBody:function(contactEmailMess, contactEmailVal){
        serviceProviderDynamicBox.contactEmailMessage = contactEmailMess;
        serviceProviderDynamicBox.contactEmailValue = contactEmailVal;
        serviceProviderDynamicBox.contactEmailBody = "<td><span class=\"shopassetdetailtxt\"><b><div id=\"spContactEmailMessage\">" + serviceProviderDynamicBox.contactEmailMessage + "</div></b></span></td><td><div id=\"spContactEmailValue\">" + serviceProviderDynamicBox.contactEmailValue + "</div></td>"
    },

    setContactFaxBody:function(contactFaxMess, contactFaxVal){
        serviceProviderDynamicBox.contactFaxMessage = contactFaxMess;
        serviceProviderDynamicBox.contactFaxValue = contactFaxVal;
        serviceProviderDynamicBox.contactFaxBody = "<td><span class=\"shopassetdetailtxt\"><b><div id=\"spContactFaxMessage\">" + serviceProviderDynamicBox.contactFaxMessage + "</div></b></span></td><td><div id=\"spContactFaxValue\">" + serviceProviderDynamicBox.contactFaxValue + "</div></td>"
    },


    setContactBody:function (contactNameMess, contactNameVal,
                             contactPhoneMess, contactPhoneVal,
                             contactEmailMess, contactEmailVal,
                             contactFaxMess, contactFaxVal) {

        serviceProviderDynamicBox.setContactNameBody(contactNameMess, contactNameVal);
        serviceProviderDynamicBox.setContactPhoneBody(contactPhoneMess, contactPhoneVal);
        serviceProviderDynamicBox.setContactEmailBody(contactEmailMess, contactEmailVal);
        serviceProviderDynamicBox.setContactFaxBody(contactFaxMess, contactFaxVal);
        serviceProviderDynamicBox.contactBody =
        "<tr>"+
        serviceProviderDynamicBox.contactNameBody+
        serviceProviderDynamicBox.contactPhoneBody+
        "</tr>"+
        "<tr>"+
        serviceProviderDynamicBox.contactFaxBody+
        serviceProviderDynamicBox.contactEmailBody+
        "</tr>";
    },

    setProviderBody:function(providerMessage,array,currentValue,selectElementName,editingAuthorized){
        serviceProviderDynamicBox.loadProviderData(array,currentValue);
        serviceProviderDynamicBox.providerSelectElementName = selectElementName;
        serviceProviderDynamicBox.createProviderElement(selectElementName);
        serviceProviderDynamicBox.providerMessageBody ="<tr><td><span class=\"shopassetdetailtxt\"><b><div id=\"spContactEmailMessage\">" + providerMessage + "&nbsp;<span class=\"reqind\">*</span></div></b></span></td>";
        serviceProviderDynamicBox.providerBody=serviceProviderDynamicBox.providerMessageBody;
        serviceProviderDynamicBox.providerBody+="<td colspan=\"3\"><div id=\"spProviderValue\">"+
                                                serviceProviderDynamicBox.providerSelectElement+
                                                "</div></td></tr>";
    },

    createProviderElement:function() {
        if (serviceProviderDynamicBox.editingAuthorized == 'true') {
            serviceProviderDynamicBox.providerSelectElement = "<select  name=\"" + serviceProviderDynamicBox.providerSelectElementName + "\" onchange=\"ajaxconnect('userWorkOrderDetail.do', 'action=changeServiceProvider&newServiceProviderId='+this.value, '', serviceProviderDynamicBox.populateAndReDrawContact);\">";
            serviceProviderDynamicBox.providerSelectElement += serviceProviderDynamicBox.providerListBody;
            serviceProviderDynamicBox.providerSelectElement += "</select>";
        } else {
            serviceProviderDynamicBox.providerSelectElement += serviceProviderDynamicBox.providerListBody;
        }
    },

    emptyFunction:function(){

    },

    updateProviderBody:function(array,editingAuthorized){
        serviceProviderDynamicBox.loadProviderData(array,"",editingAuthorized);
    },

    init:function(headerMessage,providerMessage,array,currentValue,
                  selectElementName,contactNameMess, contactNameVal, contactPhoneMess,
                  contactPhoneVal, contactEmailMess, contactEmailVal, contactFaxMess,
                  contactFaxVal,editingAuthorized) {

        serviceProviderDynamicBox.editingAuthorized = editingAuthorized;
        serviceProviderDynamicBox.setHeaderBody(headerMessage)
        serviceProviderDynamicBox.setProviderBody(providerMessage,array,currentValue,selectElementName,editingAuthorized);
        serviceProviderDynamicBox.setContactBody(contactNameMess, contactNameVal,
                contactPhoneMess, contactPhoneVal,
                contactEmailMess, contactEmailVal,
                contactFaxMess, contactFaxVal);
        serviceProviderDynamicBox.mainBody = "<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=1 style=\"border:#000000 1px solid\">"+
                                             serviceProviderDynamicBox.headerBody+
                                             serviceProviderDynamicBox.providerBody+
                                             serviceProviderDynamicBox.contactBody+
                                             "</table>"
    },

    initWriter:function(theDiv) {
        serviceProviderDynamicBox.to = theDiv;
        serviceProviderDynamicBox.initWriterFlag = true;
    },

    write:function() {
        if (serviceProviderDynamicBox.initWriterFlag) {
            var body = document.getElementById(serviceProviderDynamicBox.to);
            body.innerHTML = serviceProviderDynamicBox.mainBody;
        }
    },

    redrawContactBody:function() {
        if (serviceProviderDynamicBox.initWriterFlag) {
            var body = document.getElementById(serviceProviderDynamicBox.to);
            document.getElementById("spContactNameValue").innerHTML    = serviceProviderDynamicBox.contactNameValue;
            document.getElementById("spContactPhoneValue").innerHTML   = serviceProviderDynamicBox.contactPhoneValue;
            document.getElementById("spContactEmailValue").innerHTML   = serviceProviderDynamicBox.contactEmailValue;
            document.getElementById("spContactFaxValue").innerHTML     = serviceProviderDynamicBox.contactFaxValue;
        }
    },


    redrawProviderBody:function(){
        if (serviceProviderDynamicBox.initWriterFlag) {
            var body = document.getElementById(serviceProviderDynamicBox.to);
            serviceProviderDynamicBox.createProviderElement();
            document.getElementById("spProviderValue").innerHTML = serviceProviderDynamicBox.providerSelectElement;
        }
    },

    populateAndReDrawContact:function(responseXml, thiDiv) {

        var root = responseXml.getElementsByTagName("ServiceProvider")[0];
        var phone = "";
        var contactName = ""
        var email = "";
        var fax = ""

        if (root != null && 'undefined' != typeof root) {

            var serviceProviderId = root.getAttribute("Id");

            if (serviceProviderId != null && 'undefined' != typeof serviceProviderId) {

                var contactObj = root.getElementsByTagName("ContactInfo")[0];
                var contactItemObj = root.getElementsByTagName("ContactInfo").item(0);

                if (contactObj != null && 'undefined' != typeof contactObj) {

                    var phoneObj = contactItemObj.getElementsByTagName("ContactPhone")[0];
                    if (phoneObj != null && 'undefined' != typeof phoneObj) {
                        phone = phoneObj.firstChild.nodeValue;
                    }

                    var contactNameObj = contactItemObj.getElementsByTagName("ContactName")[0];
                    if (contactNameObj != null && 'undefined' != typeof contactNameObj) {
                        contactName = contactNameObj.firstChild.nodeValue;
                    }

                    var emailObj = contactItemObj.getElementsByTagName("ContactEmail")[0];
                    if (emailObj != null && 'undefined' != typeof emailObj) {
                        email = emailObj.firstChild.nodeValue;
                    }

                    var faxObj = contactItemObj.getElementsByTagName("ContactFax")[0];
                    if (faxObj != null && 'undefined' != typeof faxObj) {
                        fax = faxObj.firstChild.nodeValue;
                    }
                }
            }
        }

        if (phone == null || phone == "null") {
            phone = ""
        }

        if (contactName == null || contactName == "null") {
            contactName = ""
        }

        if (email == null || email == "null") {
            email = ""
        }

        if (fax == null || fax == "null") {
            fax = ""
        }

        serviceProviderDynamicBox.contactNameValue  = contactName;
        serviceProviderDynamicBox.contactPhoneValue = phone;
        serviceProviderDynamicBox.contactFaxValue   = fax;
        serviceProviderDynamicBox.contactEmailValue = email;

        serviceProviderDynamicBox.redrawContactBody();
    },


    populateAndReDrawSP:function(responseXml, thiDiv) {

        var array;

        var root = responseXml.getElementsByTagName("ActualServiceProviders")[0];
        if (root != null && 'undefined' != typeof root) {
            var providersObj = root.getElementsByTagName("ServiceProvider");
            if(providersObj != null && 'undefined' != typeof providersObj){
                array = new Array();
                for(var i=0;i<providersObj.length;i++){
                    var providerNameObj = providersObj.item(i).getElementsByTagName("ProviderName")[0];
                    if(providerNameObj != null && 'undefined' != typeof providerNameObj){
                        var providerName = providerNameObj.firstChild.nodeValue;
                        var providerId   = providersObj.item(i).getAttribute("Id");

                        if(providerName != null && providerName!="null"
                                && providerId != null && providerId!="null" ) {
                            array[i] = new Array();
                            array[i][0] = providerId;
                            array[i][1] = providerName;
                        }

                    }
                }
            }

            serviceProviderDynamicBox.updateProviderBody(array,'true');
            serviceProviderDynamicBox.redrawProviderBody();

            serviceProviderDynamicBox.contactNameValue   = "";
            serviceProviderDynamicBox.contactPhoneValue  = "";
            serviceProviderDynamicBox.contactFaxValue    = "";
            serviceProviderDynamicBox.contactEmailValue  = "";

            serviceProviderDynamicBox.redrawContactBody()
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////

var warrantyDynamicBox = {
    initWriterFlag:false,
    tableHeaderFields:"",
	divObjectInsertInto:"",
    editingAuthorized:"",
	
	populateWarrantyAndRedraw:function(array) {
		if (warrantyDynamicBox.initWriterFlag) {
            var body;
            var tableHeader;
        
            if (warrantyDynamicBox.divObjectInsertInto != null &&
                'undefined' != typeof warrantyDynamicBox.divObjectInsertInto &&
                warrantyDynamicBox.tableHeaderFields != null &&
                'undefined' != typeof warrantyDynamicBox.tableHeaderFields &&
                warrantyDynamicBox.tableHeaderFields.length != null &&
                'undefined' != typeof warrantyDynamicBox.tableHeaderFields.length) {
                
                body = warrantyDynamicBox.divObjectInsertInto;

                tableHeader =   "<table width='100%' cellpadding='1' cellspacing='1' border='0' style='border:#D3D3D3 1px solid'>" +
                                    "<tr class='customerltbkground' align='center'>" +
                                        "<td colspan='4' valign='top'>" +
                                            "<span class='shopassetdetailtxt'>" +
                                                "<b>" + warrantyDynamicBox.tableHeaderFields[0] + "</b>" +
                                            "</span>" +
                                        "</td>" +
                                    "</tr>" +
                                    "<tr class='shopcharthead' align='center'>" +
                                        "<td width='5%'>" +
                                            "<div class='fivemargin'>" + warrantyDynamicBox.tableHeaderFields[1] + "</div>" +
                                        "</td>" +
                                        "<td width='55%'>" +
                                            "<div class='fivemargin'>" + warrantyDynamicBox.tableHeaderFields[2] + "</div>" +
                                        "</td>" +
                                        "<td width='20%'>" +
                                            "<div class='fivemargin'>" + warrantyDynamicBox.tableHeaderFields[6] + "</div>" +
                                        "</td>" +
                                        "<td width='20%'>" +
                                            "<div class='fivemargin'>" + warrantyDynamicBox.tableHeaderFields[7] + "</div>" +
                                        "</td>" +
                                    "</tr>";

                var tableContent = '';
                if ( array != null &&
                    'undefined' != typeof array &&
                    array.length != null &&
                    'undefined' != typeof array.length) {
                    
                    var warrInfoTxt = '';
                    var rowColor = '';
                    for (var i = 0; i < array.length; i++) {
                        if ((i % 2) == 0) {
                            rowColor = "#F0F0F0";
                        } else {
                            rowColor = "#D8D8D8";
                        }
                        warrInfoTxt =  "<table>" +
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[1] + "</b></td>" +
                                            "<td>" + array[i][1] + "</td>" +
                                            "</tr>" +
                                            
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[2] + "</b></td>" +
                                            "<td>" + array[i][2] + "</td>" +
                                            "</tr>" +
                                            
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[3] + "</b></td>" +
                                            "<td>" + array[i][3] + "</td>" +
                                            "</tr>" +
                                            
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[4] + "</b></td>" +
                                            "<td>" + array[i][4] + "</td>" +
                                            "</tr>" +
                                            
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[5] + "</b></td>" +
                                            "<td>" + array[i][5] + "</td>" +
                                            "</tr>" +
                                            
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[6] + "</b></td>" +
                                            "<td>" + array[i][6] + "</td>" +
                                            "</tr>" +
                                            
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[7] + "</b></td>" +
                                            "<td>" + array[i][7] + "</td>" +
                                            "</tr>" +
                                            
                                            "<tr>" +
                                            "<td><b>" + warrantyDynamicBox.tableHeaderFields[8] + "</b></td>" +
                                            "<td>" + array[i][8] + "</td>" +
                                            "</tr>" +
                                        "</table>";
        
                        tableContent += "<tr align='center' style='background-color:" + rowColor +"'>" +
                                            "<td>" +
                                                array[i][1] +
                                            "</td>" +
                                            "<td>" +
                                                "<div id=\"TableIdx(" + i + ")WID" + array[i][0] + "\"" +
                                                     " style=\"cursor: pointer;\"" +
                                                     " onMouseover=\"showtext('Idx(" + i + ")WID" + array[i][0] + "','" + warrInfoTxt + "'" + ");\"" +
                                                     " onMouseout=\"hidetext('Idx(" + i + ")WID" + array[i][0] + "'" + ");\">" + array[i][2] +
												"</div>" +
												"<div id=\"Idx(" + i + ")WID" + array[i][0] + "\"" +
                                                     " style=\"visibility: hidden;position:absolute;width: 300px;background-color:#FFFFAE;height: 150px;opacity:0.4;filter:alpha(opacity=40);\">" +
												"</div>" +
                                            "</td>" +
                                            "<td>" +
                                                array[i][6] +
                                            "</td>" +
                                            "<td>" +
                                                array[i][7] +
                                            "</td>" +
                                        "</tr>";
                    }
                }
                tableContent += "</table>";
                body.innerHTML = tableHeader + tableContent;
            }
 		}
	},
	
	//selectElementName MUST BE UNIQUE
	//select element must be presented on the page
    init:function(divInsertInto, header, array, editingAuthorized) {
		warrantyDynamicBox.editingAuthorized = editingAuthorized;
		warrantyDynamicBox.divObjectInsertInto = document.getElementById(divInsertInto);
        warrantyDynamicBox.tableHeaderFields = header;
		warrantyDynamicBox.initWriterFlag = true;
		
		warrantyDynamicBox.populateWarrantyAndRedraw(array);
    },
    
    populateAndReDraw:function(responseXml, thiDiv) {
        var array;
        var root = responseXml.getElementsByTagName("WOIResponseXml")[0];
        if (root != null && 'undefined' != typeof root) {
            var warrantyInfoObj = root.getElementsByTagName("WarrantyInfo")[0];
            if (warrantyInfoObj != null && 'undefined' != typeof warrantyInfoObj) {
                var warrantiesObj = warrantyInfoObj.getElementsByTagName("Warranty");
                if (warrantiesObj != null && 'undefined' != typeof warrantiesObj) {
                    array = new Array();
                    for (var i=0;i<warrantiesObj.length;i++){
                        var warrantyNumberObj = warrantiesObj.item(i).getElementsByTagName("WarrantyNumber")[0];
                        var warrantyNameObj = warrantiesObj.item(i).getElementsByTagName("WarrantyName")[0];
                        var warrantyLongDescObj = warrantiesObj.item(i).getElementsByTagName("WarrantyLongDesc")[0];
                        var warrantyCostObj = warrantiesObj.item(i).getElementsByTagName("WarrantyCost")[0];
                        var warrantyTypeObj = warrantiesObj.item(i).getElementsByTagName("WarrantyType")[0];
                        var warrantyDurationTypeObj = warrantiesObj.item(i).getElementsByTagName("WarrantyDurationType")[0];
                        var warrantyDurationObj = warrantiesObj.item(i).getElementsByTagName("WarrantyDuration")[0];
                        var warrantyAddDateObj = warrantiesObj.item(i).getElementsByTagName("WarrantyAddDate")[0];
                        
                        if (warrantyNumberObj != null && 'undefined' != typeof warrantyNumberObj &&
                            warrantyNameObj != null && 'undefined' != typeof warrantyNameObj &&
                            warrantyLongDescObj != null && 'undefined' != typeof warrantyLongDescObj &&
                            warrantyCostObj != null && 'undefined' != typeof warrantyCostObj &&
                            warrantyTypeObj != null && 'undefined' != typeof warrantyTypeObj &&
                            warrantyDurationTypeObj != null && 'undefined' != typeof warrantyDurationTypeObj &&
                            warrantyDurationObj != null && 'undefined' != typeof warrantyDurationObj &&
                            warrantyAddDateObj != null && 'undefined' != typeof warrantyAddDateObj
                            ) {
                            var warrantyId = warrantiesObj.item(i).getAttribute("Id");
                            
                            var warrantyName = null;
                            if (warrantyNameObj.firstChild != null && 
							   "null" != warrantyNameObj.firstChild.value &&
							    "" != warrantyNameObj.firstChild.value ) {
                                warrantyName = warrantyNameObj.firstChild.nodeValue;
                            }
                            var warrantyNumber = null;
                            if (warrantyNumberObj.firstChild != null  && 
							    "null" != warrantyNumberObj.firstChild.value &&
								"" != warrantyNumberObj.firstChild.value) {
                                warrantyNumber = warrantyNumberObj.firstChild.nodeValue;
                            }
                            var warrantyLongDesc = null;
                            if (warrantyLongDescObj.firstChild != null && 
							    "null" != warrantyLongDescObj.firstChild.value &&
								"" != warrantyLongDescObj.firstChild.value) {
                                warrantyLongDesc = warrantyLongDescObj.firstChild.nodeValue;
                            }
                            var warrantyCost = null;
                            if (warrantyCostObj.firstChild != null && 
							    "null" != warrantyCostObj.firstChild.value &&
								"" != warrantyCostObj.firstChild.value) {
                                warrantyCost = warrantyCostObj.firstChild.nodeValue;
                            }
                            var warrantyType = null;
                            if (warrantyTypeObj.firstChild != null && 
							    "null" != warrantyTypeObj.firstChild.value &&
								"" != warrantyTypeObj.firstChild.value) {
                                warrantyType = warrantyTypeObj.firstChild.nodeValue;
                            }
                            var warrantyDurationType = null;
                            if (warrantyDurationTypeObj.firstChild != null && 
							    "null" != warrantyDurationTypeObj.firstChild.value &&
								"" != warrantyDurationTypeObj.firstChild.value) {
                                warrantyDurationType = warrantyDurationTypeObj.firstChild.nodeValue;
                            }
                            var warrantyDuration = null;
                            if (warrantyDurationObj.firstChild != null && 
							    "null" != warrantyDurationObj.firstChild.value &&
								"" != warrantyDurationObj.firstChild.value) {
                                warrantyDuration = warrantyDurationObj.firstChild.nodeValue;
                            }
                            var warrantyAddDate = null;
                            if (warrantyAddDateObj.firstChild != null && 
							    "null" != warrantyAddDateObj.firstChild.value &&
								"" != warrantyAddDateObj.firstChild.value) {
                                warrantyAddDate = warrantyAddDateObj.firstChild.nodeValue;
                            }

                            if (warrantyName != null && warrantyName != "null" &&
                                warrantyId != null && warrantyId != "null" ) {
                                array[i] = new Array();
                                array[i][0] = warrantyId;
                                array[i][2] = warrantyName;
                                
                                if (warrantyNumber != null && warrantyNumber != "null") {
                                    array[i][1] = warrantyNumber;
                                } else {
                                    array[i][1] = "";
                                }
                                if (warrantyLongDesc != null && warrantyLongDesc != "null") {
                                    array[i][3] = warrantyLongDesc;
                                } else {
                                    array[i][3] = "";
                                }
                                if (warrantyCost != null && warrantyCost != "null") {
                                    array[i][4] = warrantyCost;
                                } else {
                                    array[i][4] = "";
                                }
                                if (warrantyType != null && warrantyType != "null") {
                                    array[i][5] = warrantyType;
                                } else {
                                    array[i][5] = "";
                                }
                                if (warrantyDurationType != null && warrantyDurationType != "null") {
                                    array[i][6] = warrantyDurationType;
                                } else {
                                    array[i][6] = "";
                                }
                                if (warrantyDuration != null && warrantyDuration != "null") {
                                    array[i][7] = warrantyDuration;
                                } else {
                                    array[i][7] = "";
                                }
                                if (warrantyAddDate != null && warrantyAddDate != "null") {
                                    array[i][8] = warrantyAddDate;
                                } else {
                                    array[i][8] = "";
                                }
                            }
                        }
                    }
                }
            }
        }
        warrantyDynamicBox.populateWarrantyAndRedraw(array);
    }
}

////////////////////////////////////////////////////////////////////////////////////////

var assetDynamicBox = {
    initWriterFlag:false,
    modelFieldId:"",
	serialNumFieldId:"",
    modelBody:"",
	serialNumBody:"",

    init:function(modelFieldId, modelFieldValue, serialNumFieldId, serialNumFieldValue) {
        assetDynamicBox.modelFieldId = modelFieldId;
		assetDynamicBox.modelBody = modelFieldValue;
		
		assetDynamicBox.serialNumFieldId = serialNumFieldId;
		assetDynamicBox.serialNumBody = serialNumFieldValue;
		
		assetDynamicBox.initWriterFlag = true;
    },

    redraw:function() {
        if (assetDynamicBox.initWriterFlag) {
            var body = document.getElementById(assetDynamicBox.modelFieldId);
            body.innerHTML = '';
			body.innerHTML += assetDynamicBox.modelBody;
			
			body = document.getElementById(assetDynamicBox.serialNumFieldId);
            body.innerHTML = '';
			body.innerHTML += assetDynamicBox.serialNumBody;
        }
    },
	
	populateAndReDraw:function(responseXml, thiDiv) {
        var root = responseXml.getElementsByTagName("WOIResponseXml")[0];
        var modelNumber = "";
		var serialNumber = "";

        if (root != null && 'undefined' != typeof root) {

            var assetInfoObj = root.getElementsByTagName("AssetInfo")[0]

            if (assetInfoObj != null && 'undefined' != typeof assetInfoObj) {

                var assetObj = assetInfoObj.getElementsByTagName("Asset")[0];

                if (assetObj != null && 'undefined' != typeof assetObj) {

                    var modelNumberObj = assetObj.getElementsByTagName("ModelNumber")[0];
                    if (modelNumberObj != null && 'undefined' != typeof modelNumberObj) {
                        modelNumber = modelNumberObj.firstChild.nodeValue;
                    }

                    var serialNumberObj = assetObj.getElementsByTagName("SerialNumber")[0];
                    if (serialNumberObj != null && 'undefined' != typeof serialNumberObj) {
                        serialNumber = serialNumberObj.firstChild.nodeValue;
                    }
                }
            }
        }

        if (serialNumber == null || serialNumber == "null") {
            serialNumber = "";
        }

        if (modelNumber == null || modelNumber == "null") {
            modelNumber = "";
        }
		
		assetDynamicBox.modelBody = modelNumber;
        assetDynamicBox.serialNumBody  = serialNumber;
        assetDynamicBox.redraw();
    }
}

////////////////////////////////////////////////////////////////////////////////////////

var woiDynamicBoxes = {
    asset:assetDynamicBox,
    warranty:warrantyDynamicBox,

    populateAndReDraw:function(responseXml, thiDiv) {
        woiDynamicBoxes.asset.populateAndReDraw(responseXml, thiDiv);
        woiDynamicBoxes.warranty.populateAndReDraw(responseXml, thiDiv);
        
    }
}

var woDynamicBoxes = {
    serviceProvider:serviceProviderDynamicBox
}

////////////////////////////////////////////////////////////////////////////////////////