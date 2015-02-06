/*-----
    TOC - Start eSpendwise Scripts
    
    Section 1 - eSpendwise Scripts - Force Form Submit
    Section 2 - Language Variables - This is for some pop ups
    Section 3 - Location Box
    Section 4 - Alert Message
    Section 4 - Tabbed Boxes
    Section 5 - Initiate Date Pickers
    Section 6 - Date Range Select
    Section 7 - Default Input Value - add class "default-value" to an input with a unique id and this will swap text on focus
    Section 8 - Message Slider
    Section 9 - Primary Navigation
    Section 10 - Form Interactions
    Section 11 - Flyout Scripts
    Section 12 - Pop Up Scripts
        Section 12.1 - Close Pop Up
        Section 12.2 - if pop up has iframe
        Section 12.3 - Small Add to Pop Up
        Section 12.4 - Small Select a Shopping List PopUp, requires a link tag with a class of "addToBtn"
        Section 12.5 - Small Ok/Cancel Pop Up
        Section 12.6 - Small Ok/Cancel function call, requires a link tag with a class of "popUpOkSmall"
        Section 12.7 - Medium Messaging PopUp, requires a link tag with a class of "popUpMedium"
        Section 12.8 - Large PopUp, requires a link tag with a class of "popUpLarge"
        Section 12.9 - Interstitial Messaging PopUp, requires a link tag with a class of "popUpInterstitial"
    Section 13 - Opacity Focused Layer Script
        Section 13.1 - Resize the iFrame
        Section 13.2 - Browser Resize Script
-----*/

/*-----
    Section 1 - eSpendwise Scripts
    ------*/

/*
* Method to submit a form.
*/
function submitForm(formId) {
    //if a form id was specified submit it
    if (formId) {
        document.getElementById(formId).submit();
    }
    //otherwise submit the first form
    else {
        document.forms[0].submit();
    }
}

function setActionAndSubmitForm(formId, action) {
    //Set the action.
    //if a form id was specified submit it
    if (formId) {
        document.getElementById(formId).value = action;
        document.getElementById(formId).submit();
    }
    //otherwise submit the first form
    else {
        document.forms[0].value = action;
        document.forms[0].submit();
    }
}

function setFieldsAndSubmitForm(formId, hiddenFieldId, hiddenFieldValue) {
    //Set the action.
    //if a form id was specified submit it
    if (formId) {
        document.getElementById(hiddenFieldId).value = hiddenFieldValue;
        document.getElementById(formId).submit();
    }
    //otherwise submit the first form
    else {
        document.forms[0].hiddenFieldId.value = hiddenFieldValue;
        document.forms[0].submit();
    }
}

function submitCheckOut(shoppingFormId,hiddenFieldId,hiddenFieldValue) {
	//Currently, Check Out action is called from 3 places in the application.
	//1. View Cart dropdown.
	//2 & 3. from Shopping Cart Page.
	//If Request come from Shopping Cart Page then ShoppingFormId would be Specified.
	
	if(shoppingFormId) {
		//Submit Shopping Action with Shopping Form.
		if(document.getElementById(shoppingFormId)!=null) {
			document.getElementById(hiddenFieldId).value = hiddenFieldValue;
			document.getElementById(shoppingFormId).submit();
		} else {
			document.getElementById('viewCartCheckOutFormId').submit();
		}
		
	} else {
		//Check out request has come from View Cart Dropdown.
		//Submit CheckOut Action with form.
		document.getElementById('viewCartCheckOutFormId').submit();
	}
}
function submitFormToCreateShoppingList() {
	document.getElementById('ogId').value = document.getElementById('shoppingListId').value;
    document.getElementById('operationId').value = 'createShoppingList';
    document.getElementById('shoppingFormId').submit();
}
function setPreviousShoppingList() {
	  var selectedShoppingList = document.getElementById('selectedShoppingListId').value;
	  if(selectedShoppingList != null && selectedShoppingList != "null" && selectedShoppingList != "0" && selectedShoppingList != "") {
	  
		  if(selectedShoppingList=='newList') {
			  document.getElementById('shoppingListsId').options[0].selected = true;
			  document.getElementById('shoppingListsId2').options[0].selected = true;
			  } else {
			  document.getElementById('shoppingListsId').value = selectedShoppingList;
			  document.getElementById('shoppingListsId2').value = selectedShoppingList;
			  }
	  }
	  else {
		document.getElementById('shoppingListsId').options[0].selected = true;
		document.getElementById('shoppingListsId2').options[0].selected = true;
	  }
}
/*---
* Method to auto-select a check box when qty is enetered in product catalog. 
*/
function selectCheckBox(selectBoxId,qtyField) {
	var selectBoxId = document.getElementById(selectBoxId);
	var qty = document.getElementById(qtyField);
	if(selectBoxId != null) {
		if(qty.value.length == 0 || isNaN(qty.value) || qty.value <= 0)
			selectBoxId.checked = false;
		else
			selectBoxId.checked = true;
	}
}
/*---
* Method to actuate a link.  Utilized by the the ShowInterstitialMessageTag tag.
* This method was found at 
* 		http://blog.stchur.com/2010/01/15/programmatically-clicking-a-link-in-javascript/
* and all credit for it belongs to its authors.
*/
function actuateLink(link) {
    var allowDefaultAction = true;

    if (link.click) {
        link.click();
        return;
    }
    else if (document.createEvent) {
        var e = document.createEvent('MouseEvents');
        e.initEvent(
         'click'     // event type
         , true      // can bubble?
         , true      // cancelable?
      );
        allowDefaultAction = link.dispatchEvent(e);
    }

    if (allowDefaultAction) {
        var f = document.createElement('form');
        f.action = link.href;
        document.body.appendChild(f);
        f.submit();
    }
}

/*
* Method to cover the main page with a semi-transparent layer to prevent interacting
* with any controls on the page.   Utilized by the the ShowInterstitialMessageTag tag.
*/
function showCoverLayer() {
    $('body').append('<div id=\"coverLayer\">&nbsp;</div>');
    $('body').addClass('hideSelects');
    $('div#coverLayer').css('visibility', 'visible');

    var docWidth;
    // determine the width the overlay needs to be
    if ($('body').width() > $('#headerWrapper').outerWidth()) {
        docWidth = $('body').width();
    } else {
        docWidth = $('#headerWrapper').outerWidth();
    }

    // determine the height of the page
    var wrapperHeight = $('#headerWrapper').outerHeight() + $('#contentWrapper').outerHeight() + $('#footerWrapper').outerHeight();

    var docHeight;
    // get height of the document compare it to the page's height and determine whichever is taller
    if ($(window).height() > (wrapperHeight)) {
        docHeight = $(window).height();
    } else {
        docHeight = wrapperHeight;
    }

    // set the the height and width of the cover layer
    $('div#coverLayer').width(docWidth);
    $('div#coverLayer').height(docHeight);
}

/*
* Method to replace the existing contents of the window with the landing page.
* Utilized by the the ShowInterstitialMessageTag tag.
*/
function refreshLandingPage() {
    window.location = landingPageUrl;
    return false;
}

/*
 * Method to internationalize the datepickers on a given page
 * ex: internationalizeDatePickers("fr") will have all datepickers be shown in french.
 * If no region is specified, the datepickers will use the defaults.
 */
function internationalizeDatePickers(region, format) {
    var r;
    if (!region || $.trim(region).length == 0) { r = $.datepicker.regional[''];  }
    else if ($.datepicker.regional[region]) {  r =  $.datepicker.regional[region]; }
    if(r && r.dateFormat && format)   {  r.dateFormat = format; }
    if(r){$.datepicker.setDefaults(r);}
}

/*-----
Section 2 - Language Variables - This is for some pop ups
NOTE: In order to support internationalization and runtime evaluation, these variable 
definitions have been moved to htmlHeaderIncludes.jsp.
-----*/

/**
* Global Variables
*/
var active_color = '#575757'; // Color of user provided text
var inactive_color = '#C2C2C2'; // Color of default text

// used for hiding pop ups without close buttons
var windowObject = '';

/*--- Section 12.1 - Close Pop Up
    Claude and John - I moved this function outside the document ready function so it can be called from within an iframe
---*/
function closePopUp() {
    // for ie6 hide select menus which will show through overlay
    $('body').removeClass('hideSelects');
    $('div#coverLayer, div.loader, div.popUpWindow').css('visibility', 'hidden').remove();
    $('div.flyout:visible').css('display', 'none');
    return false;
}


$(document).ready(function() {

    /*-----
    Section 1 - eSpendwise Scripts
    -----*/
	// Reject button confirmation on the Pending Orders page
	var allRejectOrderControls = $("a.rejectOrderControl");
	// for approve on buttons to also trigger the calendar pop up
	$(allRejectOrderControls).click(function() {
		// check for the number of orders selected
	    var totalSelected = $(this).parents('div.content').contents().find('input:checked').size();
	    if (totalSelected == 0) {
	    	// if none are selected, show none selected message and hide save button
	    	smallApprovePopUp($('a.rejectBtn'), '#', noOrderSelectedForRejection);
	    } else if (totalSelected == 1) {
	    	// if one is selected, show single order message and show save button
	    	smallApprovePopUp($('a.rejectBtn'), $('a.rejectBtn').attr('href'), singleOrderRejection);
	    } else {
	    	// if multiple items were selected show multiple selected message and show save button
	    	smallApprovePopUp($('a.rejectBtn'), $('a.rejectBtn').attr('href'), orderRejectionPreSize + totalSelected + orderRejectionPostSize);
	    }
	    // don't allow default link action
	    return false;
	});
	
    /*-----
    Section 3 - Location Box & Switch Modules
    -----*/
    // onclick open and close the location box
    $('div.location a.btnRight, div.location a.locationsBtn, div.location a.closeBtn').click(function() {
        $('div.location a.btnRight, div.location a.locationsBtn').toggleClass('expanded');
        // for ie6 hide select menus which will show through overlay
        //$('body').toggleClass('hideSelects'); //removed to prevent dropdowns from disappearing
        $('div.location div.expand').slideToggle('750');
        return false;
    });

    $('div.switchModules > a, div.switchModules a.closeBtn').click(function() {
        if ($('div.switchModules').hasClass('collapsed')) {
            $('div.switchModules').removeClass('collapsed');
            $('div.switchModules div.expand').hide().slideDown('250');
        } else {
            $('div.switchModules').addClass('collapsed');
            $('div.switchModules div.expand').slideUp('250');
        }
    });

    /*-----
    Section 3.5 - Location Menu
    -----*/
    /*- add expand class to li to collapse the menu and allow collase/expand interaction -*/
    /*- any node under it without class expand will be the lowest level even if deeper children exist -*/
    $('div.leftColumn div.whiteBox ul > li.expand > a').live("click", function() {
        $(this).parent().toggleClass('expanded').children('ul').toggleClass('show');
        return false;
    });

    /*-----
    Section 4 - Alert Message
    -----*/
    $('div.alertBar a.closeBtn').click(function() {
        $('div.alertBar').remove();
        $('body.alertMessage').removeClass('alertMessage');
    });

    /*-----
    Section 4 - Tabbed Boxes
    -----*/
    // pull all tabbed buttons & matching tabbed content
    var allTabBtns = $('div.tabbed div.tabs ul li a');
    var allTabbedContent = $('div.tabbed div.content');

    // on click of a tab
    $(allTabBtns).click(function() {
        // find which tab was clicked
        var currentTab = $(allTabBtns).index($(this));
        // remove selected states and hide content
        $('div.boxWrapper div.tabs ul li').removeClass('selected');
        $(allTabbedContent).addClass('hide');
        // add selected states and show the current tabbed content
        $(allTabBtns[currentTab]).parent().addClass('selected');
        $(allTabbedContent[currentTab]).removeClass('hide');
        // prevent links default action from occuring
        return false;
    });


    /*-----
    Section 5 - Initiate Date Pickers
    -----*/
    var allCalendarBtns = $("a.calendarBtn");
    var allStandardCalendars = $("input.standardCal");
    var allApproveCalendars = $("input.approveCal");
    var allSpecificDatesCalendars = $("input.specificDatesCal");
    
    // For all calendars requiring approval use class "approveCal" on the calendar input
    $(allApproveCalendars).datepicker({
        numberOfMonths: 2,
        showButtonPanel: true,
        onClose: function(dateText, inst) {
            if (mouseWithinCalendar) {
            	$('input.approveCal').attr('value', dateText).css('color', '#575757');
            }
            //copy the value of the current control to all other approve calendar controls on the page 
            //to keep them in sync. This is done independently of whether or not the mouse was
            //within the calender to make sure all controls are in sync.
            allApproveCalendars.each(function() {
            	this.value = dateText;
            });
            // don't allow default link action
            return false;
        }
    });

    // for approve on buttons to also trigger the calendar pop up
    $(allCalendarBtns).click(function() {
        var currentCalendar = $(allCalendarBtns).index($(this));
        var singleOrderApprove = $(this).parents('p.approveRow');
        $(this).css("color", active_color).attr('value');
        // if on close the mouse was within the calendar check for the number of orders selected
        var totalSelected = $(this).parents('div.content').contents().find('input:checked').size();
        if (singleOrderApprove.length > 0) {
            // if one is selected, show single order message and show save button
            // smallApprovePopUp($('a.approveBtn'), $('a.approveBtn').attr('href'), singleOrderApproval);
        } else {
            if (totalSelected == 0) {
                // if none are selected, show none selected message and hide save button
                smallApprovePopUp($('a.approveBtn'), '#', noOrderSelectedForApproval);
            } else if (totalSelected == 1) {
                // if one is selected, show single order message and show save button
                smallApprovePopUp($('a.approveBtn'), $('a.approveBtn').attr('href'), singleOrderApproval);
            } else {
                // if multiple items were selected show multiple selected message and show save button
                smallApprovePopUp($('a.approveBtn'), $('a.approveBtn').attr('href'), orderApprovalPreSize + totalSelected + orderApprovalPostSize);
            }
        }
        return false;
    });

	// Check for Primary Category  CP: NEW
    $('p.primaryCategory').change(function() {	
		if ($(this).find('option:selected').attr('value') == 'defaultCategory') {		
			$('p.secondaryType').addClass('hide');
		} else {
			$('p.secondaryType').removeClass('hide');
		}		
    });
	
	// Check for Status Type  CP: NEW 
    $('td.statusType1').change(function() {
		if ($(this).find('option:selected').attr('value') == 'closeStatus') {	
			$('td.qualityType1').removeClass('hide');
			$('td.reasonType1').addClass('hide');
			$('td.otherType1').addClass('hide');
			$('td.optionClosed1').attr('disabled', true);
		} else {
			if ($(this).find('option:selected').attr('value') == 'rejectStatus') {			
				$('td.qualityType1').addClass('hide');
				$('td.reasonType1').removeClass('hide');
				$('td.otherType1').addClass('hide');
				$('td.optionClosed1').removeAttr('disabled');
			} else {
				$('td.qualityType1').addClass('hide');
				$('td.reasonType1').addClass('hide');
				$('td.otherType1').addClass('hide');
				$('td.optionClosed1').removeAttr('disabled');
			}
		}		
    });
	// Check for Status Type  CP: NEW
    $('td.statusType2').change(function() {
		if ($(this).find('option:selected').attr('value') == 'closeStatus') {	
			$('td.qualityType2').removeClass('hide');
			$('td.reasonType2').addClass('hide');
			$('td.otherType2').addClass('hide');
			$('td.optionClosed2').attr('disabled', true);
		} else {
			if ($(this).find('option:selected').attr('value') == 'rejectStatus') {			
				$('td.qualityType2').addClass('hide');
				$('td.reasonType2').removeClass('hide');
				$('td.otherType2').addClass('hide');
				$('td.optionClosed2').removeAttr('disabled');
			} else {
				$('td.qualityType2').addClass('hide');
				$('td.reasonType2').addClass('hide');
				$('td.otherType2').addClass('hide');
				$('td.optionClosed2').removeAttr('disabled');
			}
		}		
    });
	// Check for Status Type  CP: NEW
    $('td.statusType3').change(function() {
		if ($(this).find('option:selected').attr('value') == 'closeStatus') {	
			$('td.qualityType3').removeClass('hide');
			$('td.reasonType3').addClass('hide');
			$('td.otherType3').addClass('hide');
			$('td.optionClosed3').attr('disabled', true);
		} else {
			if ($(this).find('option:selected').attr('value') == 'rejectStatus') {			
				$('td.qualityType3').addClass('hide');
				$('td.reasonType3').removeClass('hide');
				$('td.otherType3').addClass('hide');
				$('td.optionClosed3').removeAttr('disabled');
			} else {
				$('td.qualityType3').addClass('hide');
				$('td.reasonType3').addClass('hide');
				$('td.otherType3').addClass('hide');
				$('td.optionClosed3').removeAttr('disabled');
			}
		}		
    });
	// Check for Status Type  CP: NEW
    $('td.statusType4').change(function() {
		if ($(this).find('option:selected').attr('value') == 'closeStatus') {	
			$('td.qualityType4').removeClass('hide');
			$('td.reasonType4').addClass('hide');
			$('td.otherType4').addClass('hide');
			$('td.optionClosed4').attr('disabled', true);
		} else {
			if ($(this).find('option:selected').attr('value') == 'rejectStatus') {			
				$('td.qualityType4').addClass('hide');
				$('td.reasonType4').removeClass('hide');
				$('td.otherType4').addClass('hide');
				$('td.optionClosed4').removeAttr('disabled');
			} else {
				$('td.qualityType4').addClass('hide');
				$('td.reasonType4').addClass('hide');
				$('td.otherType4').addClass('hide');
				$('td.optionClosed4').removeAttr('disabled');
			}
		}		
    });
	// Check for Status Type  CP: NEW
    $('td.statusType5').change(function() {
		if ($(this).find('option:selected').attr('value') == 'closeStatus') {	
			$('td.qualityType5').removeClass('hide');
			$('td.reasonType5').addClass('hide');
			$('td.otherType5').addClass('hide');
			$('td.optionClosed5').attr('disabled', true);
		} else {
			if ($(this).find('option:selected').attr('value') == 'rejectStatus') {			
				$('td.qualityType5').addClass('hide');
				$('td.reasonType5').removeClass('hide');
				$('td.otherType5').addClass('hide');
				$('td.optionClosed5').removeAttr('disabled');
			} else {
				$('td.qualityType5').addClass('hide');
				$('td.reasonType5').addClass('hide');
				$('td.otherType5').addClass('hide');
				$('td.optionClosed5').removeAttr('disabled');
			}
		}		
    });
	// Check for Status Type  CP: NEW
    $('td.statusType6').change(function() {
		if ($(this).find('option:selected').attr('value') == 'closeStatus') {	
			$('td.qualityType6').removeClass('hide');
			$('td.reasonType6').addClass('hide');
			$('td.otherType6').addClass('hide');
			$('td.optionClosed6').attr('disabled', true);
		} else {
			if ($(this).find('option:selected').attr('value') == 'rejectStatus') {			
				$('td.qualityType6').addClass('hide');
				$('td.reasonType6').removeClass('hide');
				$('td.otherType6').addClass('hide');
				$('td.optionClosed6').removeAttr('disabled');
			} else {
				$('td.qualityType6').addClass('hide');
				$('td.reasonType6').addClass('hide');
				$('td.otherType6').addClass('hide');
				$('td.optionClosed6').removeAttr('disabled');
			}
		}		
    });
	// Check for Reason Type  CP: NEW
    $('td.reasonType1').change(function() {
		if ($(this).find('option:selected').attr('value') == 'otherReason') {	
			$('td.otherType1').removeClass('hide');
		} else {
			$('td.otherType1').addClass('hide');			
		}		
    });
	// Check for Reason Type  CP: NEW
    $('td.reasonType2').change(function() {
		if ($(this).find('option:selected').attr('value') == 'otherReason') {	
			$('td.otherType2').removeClass('hide');
		} else {
			$('td.otherType2').addClass('hide');			
		}		
    });// Check for Reason Type  CP: NEW
    $('td.reasonType3').change(function() {
		if ($(this).find('option:selected').attr('value') == 'otherReason') {	
			$('td.otherType3').removeClass('hide');
		} else {
			$('td.otherType3').addClass('hide');			
		}		
    });// Check for Reason Type  CP: NEW
    $('td.reasonType4').change(function() {
		if ($(this).find('option:selected').attr('value') == 'otherReason') {	
			$('td.otherType4').removeClass('hide');
		} else {
			$('td.otherType4').addClass('hide');			
		}		
    });// Check for Reason Type  CP: NEW
    $('td.reasonType5').change(function() {
		if ($(this).find('option:selected').attr('value') == 'otherReason') {	
			$('td.otherType5').removeClass('hide');
		} else {
			$('td.otherType5').addClass('hide');			
		}		
    });
	// Check for Reason Type  CP: NEW
    $('td.reasonType6').change(function() {
		if ($(this).find('option:selected').attr('value') == 'otherReason') {	
			$('td.otherType6').removeClass('hide');
		} else {
			$('td.otherType6').addClass('hide');			
		}		
    });
    
    // For standard calendars that do not require an approval pop up
    $(allStandardCalendars).datepicker({
        numberOfMonths: 2,
        showButtonPanel: true,
        onClose: function() {
            $(this).css("color", active_color).attr('value');
            return false;
        }
    });
    
    $(allSpecificDatesCalendars).datepicker({
        numberOfMonths: 2,
        showButtonPanel: true,
        beforeShowDay: enableSpecificDates,
        onClose: function() {
            $(this).css("color", active_color).attr('value');
            return false;
        }
    });

    // test if mouse is within calendar so clicking outside the calendar doesn't trigger popups
    var mouseWithinCalendar = false;
    $('div#ui-datepicker-div').hover(function() {
        mouseWithinCalendar = true;
    }, function() {
        mouseWithinCalendar = false;
    });

//*** Order schedule additional dates calendar begin ***
    
    $("div.orderScheduleAdditionalDays").datepicker({
        numberOfMonths: 3,
        showButtonPanel: true,
        beforeShowDay: orderScheduleAdditionalDaysBeforeShowDay,
        onSelect: orderScheduleAdditionalDaysOnSelect,
        onClose: function() {
              $(this).css("color", active_color).attr('value');
              return false;
        }
    });

    function orderScheduleAdditionalDaysBeforeShowDay(date) {
        // Get additional days from text field
        var additionalDays = $("#alsoDates").val().split(dateSeperator);
        var dateFormat = $("div.orderScheduleAdditionalDays").datepicker( "option", "dateFormat" );
        var dateString = $.datepicker.formatDate(dateFormat, date);
        //var dateString = ((date.getMonth() + 1 < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "/" + date.getFullYear();
        var dateIndex = $.inArray(dateString, additionalDays);
        var cssClass = dateIndex > -1 ? "ui-datepicker-selected-day" : "ui-datepicker-not-selected-day";
        var isSelectable = true;
        return [isSelectable, cssClass, null];
    }
      
    function orderScheduleAdditionalDaysOnSelect(selectedDate, calendarInstance) {
        // Get additional days from text field
    	var currentDays = $.trim($("#alsoDates").val());
    	var additionalDays = null;
    	if (currentDays && currentDays.length > 0) {
    		additionalDays = currentDays.split(dateSeperator);
    	}
    	else {
    		additionalDays = new Array(0);
    	}
        var dateIndex = $.inArray(selectedDate, additionalDays);
        // if the selected day has not already been chosen, add it to the list
        if (dateIndex > -1) {
         	additionalDays.splice(dateIndex, 1);
        }
        // otherwise, it was previously chosen so remove it from the list
        else {
          	additionalDays.push(selectedDate);
        }
        $("#alsoDates").val(additionalDays.toString());
    }
//*** Order schedule additional dates calendar end ***

//*** Order schedule exclusion dates calendar begin ***
    $("div.orderScheduleExcludedDays").datepicker({
        numberOfMonths: 3,
        showButtonPanel: true,
        beforeShowDay: orderScheduleExcludedDaysBeforeShowDay,
        onSelect: orderScheduleExcludedDaysOnSelect,
        onClose: function() {
              $(this).css("color", active_color).attr('value');
              return false;
        }
    });

    function orderScheduleExcludedDaysBeforeShowDay(date) {
        // Get excluded days from text field
        var excludedDays = $("#excludeDates").val().split(dateSeperator);
        var dateFormat = $("div.orderScheduleExcludedDays").datepicker( "option", "dateFormat" );
        var dateString = $.datepicker.formatDate(dateFormat, date);
        //var dateString = ((date.getMonth() + 1 < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "/" + date.getFullYear();
        var dateIndex = $.inArray(dateString, excludedDays);
        var cssClass = dateIndex > -1 ? "ui-datepicker-selected-day" : "ui-datepicker-not-selected-day";
        var isSelectable = true;
        return [isSelectable, cssClass, null];
    }
      
    function orderScheduleExcludedDaysOnSelect(selectedDate, calendarInstance) {
        // Get excluded days from text field
    	var currentDays = $.trim($("#excludeDates").val());
    	var excludedDays = null;
    	if (currentDays && currentDays.length > 0) {
    		excludedDays = currentDays.split(dateSeperator);
    	}
    	else {
    		excludedDays = new Array(0);
    	}
        var dateIndex = $.inArray(selectedDate, excludedDays);
        // if the selected day has not already been chosen, add it to the list
        if (dateIndex > -1) {
        	excludedDays.splice(dateIndex, 1);
        }
        // otherwise, it was previously chosen so remove it from the list
        else {
        	excludedDays.push(selectedDate);
        }
        $("#excludeDates").val(excludedDays.toString());
    }
//*** Order schedule exclusion dates calendar end ***

    /*-----
    Section 6 - Date Range Select
    -----*/
    // on change of the the date range select
    $('select.dateRange').change(function() {
        // if the value is set to custom (indicating the user would like to enter a custom date range)
        if ($(this).children('option:selected').attr('value') == 'custom') {
            // show the associated calendar table
            $(this).parent('p').next('table.calendar').removeClass('hide');
        } else {
            // hide the associated calendar table
            $(this).parent('p').next('table.calendar').addClass('hide');
        }
    });
    if ($('select.dateRange')) {
        if ($('select.dateRange').children('option:selected').attr('value') == 'custom') {
            $('select.dateRange').parent('p').next('table.calendar').removeClass('hide');
        }
    }

    var dateInputs = $('input.hasDatepicker');

    if (dateInputs.length > 0) {
        for (var i = 0; i < dateInputs.length; i++) {
            if ($(dateInputs[i]).attr('value') != date_default) {
                $(dateInputs[i]).css("color", active_color);
            }
        }
    }

    /*-----
    Section 7 - Default Input Value - add class "default-value" to an input with a unique id and this will swap text on focus
    -----*/
    /*** Written by Rob Schmitt, The Web Developer's Blog, http://webdeveloper.beforeseven.com/ */
   
    $("input.default-value").css("color", inactive_color);
    /*--
     * STJ-5763 - if the input field is a calendar control and has a non-default value, set it's color to be
     * the active color
     */
    $("input.default-value").each(function(index,element) {
    	if ($(this).hasClass("standardCal")) {
    		if ($(this).val()) {
        		var monthIndex = $(this).val().toUpperCase().indexOf('MM');
        		if (monthIndex < 0 ) {
        			this.style.color = active_color;
        		}    			
    		}
    	}
    });    
    /*--
     * End of STJ-5763
     */
    var default_values = new Array();
    $("input.default-value").focus(function() {
        if (!default_values[this.id]) {
            default_values[this.id] = this.value;
        }
        if (this.value == default_values[this.id] && default_values[this.id].match(/^\D/)) {
            this.value = '';
            this.style.color = active_color;
        }
        $(this).blur(function() {
            if (this.value == '') {
                this.style.color = inactive_color;
                this.value = default_values[this.id];
            }
        });
    });


    /*-----
    Section 8 - Message Slider
    -----*/
    // width of each element in the carousel
    var widthIncrement = 280;
    // easing equation
    var easingType = 'easeOutExpo';
    // Speed of the lower carousel animations
    var animationSpeed = 1000;
    // Store all lower thumbnails
    var allMessages = $('div.leftColumn div.slidingItem');
    // get slider length
    var messageNumber = allMessages.length;
    // set width of slider
    $('div.slider div.slidingWrapper').width(widthIncrement * messageNumber);

    // prevent double clicks
    var sliderMoving = false;

    // build sliding navigation
    var sliderNavLinks = '';
    for (i = 0; i < messageNumber; i++) {
        if (i == 0) {
            sliderNavLinks += "<a href='#' class='selected'>slide</a>";
        } else {
            sliderNavLinks += "<a href='#'>slide</a>";
        }
    }
    $('p.sliderNav').html(sliderNavLinks);

    sliderBtns = $('p.sliderNav a');

    $(sliderBtns).click(function() {
        // check if sliding
        if (!sliderMoving) {
            sliderMoving = true;
            var currentSliderBtn = $(sliderBtns).index($(this));
            $(sliderBtns).removeClass('selected');
            $(sliderBtns[currentSliderBtn]).addClass('selected');
            $('div.slider div.slidingWrapper').animate({ left: -(widthIncrement * currentSliderBtn) }, animationSpeed, easingType, function() {
                sliderMoving = false;
            });
        }
        return false;
    });


    /*-----
    Section 9 - Primary Navigation
    -----*/
    
    $('div.tabbedHeader ul').superfish({
        hoverClass: 'jHover',    // class for hover
        delay: 5000,            // 5 second delay on mouseout
        animation: { opacity: 'show' },  // fade-in and slide-down animation 
        speed: 'fast',          // faster animation speed 
        autoArrows: false,      // disable generation of arrow mark-up 
        dropShadows: false      // disable drop shadows
    });

    $('div.tabbedHeader > ul > li > ul').hover(function() {
        //$(this).parents().eq(0).addClass('over');
    }, function() {

    });

    /*-----
    Section 9 - Zone Interaction (T22)
    -----*/
    $('p.expandCollapse a.expandZones').click(function() {
        $('div.zone').removeClass('collapsed');
        return false;
    });
    $('p.expandCollapse a.collapseZones').click(function() {
        $('div.zone').addClass('collapsed');
        return false;
    });


    /*-----
    Section 10 - Form Interactions 
    -----*/
    // Select all checkboxes
    $('span.selectNav a.all, span.selectNavAction a.all').click(function() {
        $(this).parents('div.left').find("table input[type='checkbox']:not(:disabled)").attr('checked', true);
        return false;
    });

    // Deselect all checkboxes
    $('span.selectNav a.none, span.selectNavAction a.none').click(function() {
        $(this).parents('div.left').find("table input[type='checkbox']:not(:disabled)").attr('checked', false);
        return false;
    });

    // Show comment row on click of the standard button in the comment row
    $('p.addComment a.smallBtn').click(function() {
        $(this).parents('p.addComment').addClass('hide').siblings('p.commentEntry').removeClass('hide');
        return false;
    });

    // When entering comment hide the comment entry row if cancel is clicked
    $('p.commentEntry a.cancel').click(function() {
        $(this).parents('p.commentEntry').addClass('hide').siblings('p.addComment').removeClass('hide');
        return false;
    });

    // Confirmation Fields
    $('input.confirm').click(function() {
		var checked = $(this).is(':checked');
        if (checked) {
            $(this).parents('tr').siblings('tr.confirm').removeClass('hide');
        } else {
            $(this).parents('tr').siblings('tr.confirm').addClass('hide');
		}
		$("#confirmOrderId").val(checked);
    });

    // New shopping list button
    $('a.newListBtn').click(function() {
        $(this).addClass('hide').siblings('div.addNewList').removeClass('hide').css("display", "block").siblings('a.editBtn').addClass('hide');
        return false;
    });

    // New shopping list cancel button
    $('div.addNewList a.cancel').click(function() {
        $(this).parents('div.addNewList').addClass('hide').siblings('a.newListBtn').removeClass('hide').siblings('a.editBtn').removeClass('hide');
        return false;
    });


    // New shopping list cancel button
    //STJ-5786
    /*$('div.shoppingLists a.cancelBtn, div.scheduleWidget div.leftColumn a.cancelBtn').click(function() {
        $(this).siblings('ul').children('li').css("display", "block");
        $(this).siblings('ul').children('li.edit').css("display", "none");
        $(this).siblings('a.editBtn').css("display", "block");
        $(this).siblings('a.saveBtn').addClass('hide');
        $(this).addClass('hide');
        $(this).siblings('a.newListBtn').css("display", "block");
        return false;
    });*/


    // Edit Shopping Lists
    $('div.shoppingLists a.editBtn, div.scheduleWidget div.leftColumn a.editBtn').click(function() {
        $(this).hide();
        $('div.shoppingLists a.newListBtn, div.scheduleWidget div.leftColumn a.newListBtn').hide();
        $('div.shoppingLists a.saveBtn, div.shoppingLists a.cancelBtn, div.scheduleWidget div.leftColumn a.saveBtn, div.scheduleWidget div.leftColumn a.cancelBtn').removeClass('hide');
        $('div.shoppingLists ul li, div.scheduleWidget div.leftColumn ul li').hide();
        $('div.shoppingLists ul li.edit, div.scheduleWidget div.leftColumn ul li.edit').show();
        return false;
    });

    // action link hover will trigger action popUp T-6 Shopping Cart
    $('a.actionBtn').hover(function() {
        $(this).siblings('div.alertNotice').toggle();
    }, function() {
        $(this).siblings('div.alertNotice').toggle();
    });

    // On click of the approve link trigger the confirmation pop up
    $('a.approveBtn').click(function() {
        var singleOrderApprove = $(this).parents('p.approveRow');
        var totalSelected = $(this).parents('div.content').contents().find('input:checked').size();
        // if on a single order page instantly trigger the pop up without quantity considerations
        if (singleOrderApprove.length > 0) {
            // if one is selected, show single order message and show save button
            smallApprovePopUp($('a.approveBtn'), $('a.approveBtn').attr('href'), singleOrderApproval);
        } else {
            if (totalSelected == 0) {
                smallApprovePopUp($('a.approveBtn'), '#', noOrderSelectedForApproval);
            } else if (totalSelected == 1) {
                smallApprovePopUp($('a.approveBtn'), $('a.approveBtn').attr('href'), singleOrderApproval);
            } else {
                smallApprovePopUp($('a.approveBtn'), $('a.approveBtn').attr('href'), orderApprovalPreSize + totalSelected + orderApprovalPostSize);
            }
        }
        return false;
    });

    $('a.clearForm').click(function() {
        $(':input', $(this).parent('form')).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
    });

    /*- Add to button trigger to select order -*/
    var allInputTriggers = $('input.triggerSelect');
    var allSelectBoxes = $('label.chkBox input');
    // on focus trigger select checkbox in the same row
    $(allInputTriggers).focus(function() {
        var currentInput = $(allInputTriggers).index($(this));
        $(allSelectBoxes[currentInput]).attr('checked', true);
    });

    // on blur deselect checkbox in the same row if the value is empty
    $(allInputTriggers).blur(function() {
        var currentInput = $(allInputTriggers).index($(this));
        if ($(allInputTriggers[currentInput]).attr('value') == "") {
            $(allSelectBoxes[currentInput]).attr('checked', false);
        }
    });

    $('a.addToBtn').hover(function() {
        $('div.flyoutHorz').hide();
        $(this).siblings('div.flyoutHorz').show();
        $(this).parent('div.btns').siblings('div.flyoutHorz').show();
        $($(this).siblings('div.flyoutHorz')).hover(function() { }, function() {
            $(this).hide();
        });
    }, function() {
    });
    
    function renderRecurrenceControls() {
        if ($('select.recurrence').children('option:selected').attr('value') == scheduleRecurrenceWeekly) {
            $('select.recurrence').siblings('div').addClass('hide');
            $('select.recurrence').siblings('div.weekly').removeClass('hide');
            $('#customScheduleControls').hide();
            $('#monthlyScheduleControls').hide();
            $('#weeklyScheduleControls').show();
        } else if ($('select.recurrence').children('option:selected').attr('value') == scheduleRecurrenceMonthly) {
        	$('select.recurrence').siblings('div').addClass('hide');
        	$('select.recurrence').siblings('div.monthly').removeClass('hide');
            $('#customScheduleControls').hide();
            $('#weeklyScheduleControls').hide();
            $('#monthlyScheduleControls').show();
        } else if ($('select.recurrence').children('option:selected').attr('value') == scheduleRecurrenceCustom) {
        	$('select.recurrence').siblings('div').addClass('hide');
        	$('select.recurrence').siblings('div.custom').removeClass('hide');
            $('#weeklyScheduleControls').hide();
            $('#monthlyScheduleControls').hide();
            $('#customScheduleControls').show();
        }
        threeColCalendar_SetValues();
    }

    // On change of the recurrence drop down show the correct options
    $('select.recurrence').change(function() {
    	renderRecurrenceControls();
    });

    // On page load depending on what recurrence is set to on order schedule page show the correct options
    renderRecurrenceControls();

    // This needs to be called after every postback to update the calendar (start/end date selection change,
    // recurrence change, etc.)  Try/catch block is here to handle an issue in IE.
    function threeColCalendar_SetValues() {
    	try {
	        $('div.threeColCalendar').datepicker({
	            numberOfMonths: 3,
	            beforeShowDay: threeColCalendar_BeforeShowDay
	        });
	        //alert("Original dayNamesMin: " + $('div.threeColCalendar').datepicker("option", "dayNamesMin"));
	        //call the date picker initialization method (defined in htmlHeaderIncludes.jsp)
	        initializeDatePickers();
	        //for some reason, without the next line the calendar initially appears using Japanese characters (although
	        //as soon as the user clicks on the forward/back buttons it displays correctly.  All the next line is doing
	        //is setting the dayNamesMin property to itself, but it fixes the problem.
	        $('div.threeColCalendar').datepicker("option", "dayNamesMin", $('div.threeColCalendar').datepicker("option", "dayNamesMin"));
	        //alert("Modified dayNamesMin: " + $('div.threeColCalendar').datepicker("option", "dayNamesMin"));
    	}
    	catch (e) {
    		//do nothing
    	}
    }

    function threeColCalendar_BeforeShowDay(date) {
        // Get selected days from hidden field, populated server-side
        selectedDays = $("input.selectedDays").val().split(dateSeperator);

        //var dateString = ((date.getMonth() + 1 < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "/" + date.getFullYear();
        var dateFormat = $("div.threeColCalendar").datepicker( "option", "dateFormat" );
        var dateString = $.datepicker.formatDate(dateFormat, date);

        var dateIndex = $.inArray(dateString, selectedDays);

        var cssClass = (dateIndex > -1 ? "ui-datepicker-selected-day" : "ui-datepicker-not-selected-day");
        var isSelectable = false;

        return [isSelectable, cssClass, null];
    }
 
    function enableSpecificDates(date) {
        
        var specificDays = $("input.specificDays").val().split(dateSeperator);

        var dateFormat = $("#specificDates").datepicker( "option", "dateFormat" );
 
        var dateString = $.datepicker.formatDate(dateFormat, date);
        
        for (i = 0; i < specificDays.length; i++) {
        	if($.inArray(dateString, specificDays) != -1){
        		return [true];
        	}
        }
        
        return [false];
    }

    $('input.setEndDate, input.noEndDate').change(function () {
        $('input.setEndDate').siblings('input').toggle();
    });

    /*- Zone sections (Service Tickets) -*/
    $('div.zone div.title a').click(function() {
        $(this).parents('div.zone').toggleClass('collapsed');
        return false;
    });

    /*-----
    Section 11 - Flyout Scripts
    -----*/
    // store all flyout pairings
    var allFlyoutCalls = $('input.flyout');
    var allFlyouts = $('div.flyout');
    // When the flyout element is clicked
    $(allFlyoutCalls).click(function() {
        // find which flyout was clicked on
        var currentFlyout = $(allFlyoutCalls).index($(this));
        // find position of element clicked
        var thisOffsetPos = $(this).offset();
        // position flyout and show
        if ($(allFlyouts[currentFlyout]).is(':hidden')) {
            $(allFlyouts[currentFlyout])
                .show()
                .css('top', thisOffsetPos.top - $(allFlyouts[currentFlyout]).height() / 2 + 12)
                .css('left', thisOffsetPos.left + $(this).width() + 7);
            // for ie6 hide select menus which will show through overlay
            $('body').addClass('hideSelects');
        } else {
            $(allFlyouts[currentFlyout]).hide();
            $('body').removeClass('hideSelects');
        }
    });

    // test if mouse is within calendar so clicking outside the calendar doesn't trigger popups
    var mouseWithinFlyout = false;
    $('div.flyout').hover(function() {
        mouseWithinFlyout = true;
    }, function() {
        mouseWithinFlyout = false;
    });

    $('div.flyoutHorz').hover(function() {
        mouseWithinFlyout = true;
    }, function() {
        mouseWithinFlyout = false;
        $('div.flyoutHorz').hide();
    });

    // when the element is blurred hide all flyouts
    $(allFlyoutCalls).blur(function() {
        if (!mouseWithinFlyout) {
            $(allFlyouts).hide();
            $('body').removeClass('hideSelects');
        }
    });

    /*-----
    Section 12 - Pop Up Scripts
    -----*/

    /*--- Section 12.2 - if pop up has iframe  ---*/
    function iFrameLoad() {
        // wait for the iframe to load
        $('#iFrameHolder').load(function() {
            // The height of the loaded content in the iframe
            iFrameHeight = this.contentWindow.document.body.offsetHeight;
            // check if iFrame has loaded with real content
            // blank.html used for ssl to prevent security issues in ie6
            if ($('div.popUpWindow iframe').attr('src') != "/blank.html") {
                // hide loading popup
                $('div.loader').css('display', 'none');
                loadingPopUp = false;
                // show popup and coverlayer
                $('div.popUpWindow').css('visibility', 'visible');
                // for ie6 hide select menus which will show through overlay
                $('body').addClass('hideSelects');
                $('div#coverLayer').css('visibility', 'visible');
            } else {
                // if the iframes source is blank.html hide the popup
                $('div.popUpWindow').css('visibility', 'hidden');
                $('div#coverLayer').css('visibility', 'hidden');
            }
            // call resize script
            resizeIframe();
        });
    }

    /*--- Section 12.3 - Small Add to Pop Up ---*/
    function smallAddToPopUp(linkObject) {
        var title = $(linkObject).attr('title');
        // Generate html for Small PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpSmall"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix">' +
           $('div.shoppingList').html()
           + '<input type="text" name="shoppingList" id="shoppingListId" maxlength="30" /><div class="buttonRow"><a href="#"  onclick="setPreviousShoppingList()" class="blueBtnLarge closePopUpBtn"><span>' + (title == noOrderSelectedForApproval ? cancelBtnText : cancelBtnText) + '</span></a><a href="javascript:submitFormToCreateShoppingList();" class="blueBtnLarge"><span>' + saveBtnText + '</span></a></div></div><div class="popUpBottom">&nbsp;</div></div>');
        // CP - changed okBtnText to cancelBtnText
        $('div.popUpContent select').change(function() {
            if ($(this).children('option:selected').attr('value') == 'newList') {

                $('div.listName').show();
            } else {
                $('div.listName').hide();
            }
        });
        loadingPopUp = false;
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // for ie6 hide select menus which will show through overlay
        $('body').addClass('hideSelects');
        $('div.flyout').hide();
        // show popUp Instantly
        $('div#coverLayer').css('visibility', 'visible');
        $('div.popUpWindow').css('visibility', 'visible');
        // run resize script to center popUp
        setWindowSize();
        // prevent default button action
        return false;
    }

    /*--- Section 12.4 - CP - Removed popup and added dropdown for Add to Shopping ---*/
    $('a.addToShoppingList').click(function() {
		// smallAddToPopUp($(this));
        return false;
    });
	
	$('select.span').change(function() {
		var index = $(this).children('option:selected').attr('index');
		if ($(this).children('option:selected').attr('value') == 'newList') {		
            smallAddToPopUp($(this));
			$('div.listName').show();
        }	
        $('select.span').each(function() { 		
			this.selectedIndex = index;			
		});	
		
        return false;
    });	

    /*--- Section 12.5 - Small Ok/Cancel Pop Up ---*/
    function smallApprovePopUp(callingObject, approveLink, title) {
        // Generate html for Small PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpSmall"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><p>' +
           title
        + '</p><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span>' + cancelBtnText + '</span></a><a href="' + approveLink + '" class="blueBtnLarge ' + (approveLink == "#" ? 'hide' : '') + '"><span>' + saveBtnText + '</span></a></div></div><div class="popUpBottom">&nbsp;</div></div>');
        loadingPopUp = false;
        // add click event to close button
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // for ie6 hide select menus which will show through overlay
        $('body').addClass('hideSelects');
        // show popUp Instantly
        $('div#coverLayer').css('visibility', 'visible');
        $('div.popUpWindow').css('visibility', 'visible');
        // run resize script to center popUp
        setWindowSize();
        // prevent default button action
        return false;
    }

    /*--- Section 12.6 - Small Ok/Cancel function call, requires a link tag with a class of "popUpOkSmall" ---*/
    $('a.popUpOkSmall').click(function() {
        smallApprovePopUp($(this), $(this).attr('href'), $(this).attr('title'));
        return false;
    });

    /*--- Section 12.7 - Medium Messaging PopUp, requires a link tag with a class of "popUpMedium" ---*/
    $('a.popUpMedium').click(function() {
        // Generate html for Medium PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpMedium"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><h1>'
        // Take title from clicked link on page
            + $(this).attr('title')
            + '</h1><hr /><iframe src="'
        // Take iframe path from clicked link on page
            + $(this).attr('href')
            + '" id="iFrameHolder" frameborder="0" height="1"></iframe><hr /><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span>' + closeBtnText + '</span></a></div></div><div class="popUpBottom">&nbsp;</div></div><div class="loader"><p>' + loadingText + '...</p><img src="' + loadingImageUrl + '" alt="' + loadingText + '" /></div>');
        // Set the iframe height 
        $('div.popUpWindow iframe').height('325');
        // Disable iframe resizer, on load the iframe will have a scrollbar if content inside is too long
        iframeResizeDisabled = true;
        // this item requires a loader due to iframe
        loadingPopUp = true;
        // show loader
        $('div.loader').css('display', 'block');
        // run resize script to center loader
        setWindowSize();
        // bind click event to close button calling the closePopUp function
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // load the iframe
        iFrameLoad();
        // prevent default button action
        return false;
    });

    /*--- Section 12.7.1 - Medium Saveable PopUp, requires a link tag with a class of "popUpMediumSave" ---*/
    $('a.popUpMediumSave').click(function() {
        // Generate html for Medium PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpMediumSave"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><h1>'
        // Take title from clicked link on page
            + $(this).attr('title')
            + '</h1><hr /><iframe src="'
        // Take iframe path from clicked link on page
            + $(this).attr('href')
            + '" id="iFrameHolder" frameborder="0" height="1"></iframe></div><div class="popUpBottom">&nbsp;</div></div><div class="loader"><p>' + loadingText + '...</p><img src="' + loadingImageUrl + '" alt="' + loadingText + '" /></div>');
        // Disable iframe resizer, on load the iframe will have a scrollbar if content inside is too long
        iframeResizeDisabled = false;
        // this item requires a loader due to iframe
        loadingPopUp = true;
        // show loader
        $('div.loader').css('display', 'block');
        // run resize script to center loader
        setWindowSize();
        // bind click event to close button calling the closePopUp function
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // load the iframe
        iFrameLoad();
        // prevent default button action
        return false;
    });

    /*--- Section 12.8 - Large PopUp, requires a link tag with a class of "popUpLarge" ---*/
    $('a.popUpLarge').click(function() {
        // Generate html for Large PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpLarge"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><h1>'
        // Take title from clicked link on page
            + $(this).attr('title')
            + '</h1><div class="buttonRow"><a href="#" class="closePopUpBtn"><span>' + closeBtnText + '</span></a></div><hr /><div class="clearfix">&nbsp;</div><iframe src="'
        // Take iframe path from clicked link on page
            + $(this).attr('href')
            + '" id="iFrameHolder" frameborder="0" scrolling="no" height="1"></iframe></div><div class="popUpBottom">&nbsp;</div></div><div class="loader"><p>' + loadingText + '...</p><img src="' + loadingImageUrl + '" alt="' + loadingText + '" /></div>');
        // do not disable iframe resizer, on load the iframe will be sized to fit content
        iframeResizeDisabled = false;
        // this item requires a loader due to iframe
        loadingPopUp = true;
        // show loader
        $('div.loader').css('display', 'block');
        // run resize script to center loader
        setWindowSize();
        // bind click event to close button calling the closePopUp function
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // load the iframe
        iFrameLoad();
        // prevent default button action
        return false;
    });

    /*--- Section 12.8 - Large PopUp, requires a link tag with a class of "popUpWide" ---*/
    $('a.popUpWide').click(function() {
        // Generate html for Large PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpWide"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix">'
        + '<a href="#" class="closePopUpBtn"><span>' + closeBtnText + '</span></a><div class="clearfix">&nbsp;</div><iframe src="'
        // Take iframe path from clicked link on page
            + $(this).attr('href')
            + '" id="iFrameHolder" frameborder="0" scrolling="no" height="1"></iframe></div><div class="popUpBottom">&nbsp;</div></div><div class="loader"><p>' + loadingText + '...</p><img src="' + loadingImageUrl + '" alt="' + loadingText + '" /></div>');
        // do not disable iframe resizer, on load the iframe will be sized to fit content
        iframeResizeDisabled = false;
        // this item requires a loader due to iframe
        loadingPopUp = true;
        // show loader
        $('div.loader').css('display', 'block');
        // run resize script to center loader
        setWindowSize();
        // bind click event to close button calling the closePopUp function
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // load the iframe
        iFrameLoad();
        // prevent default button action
        return false;
    });
    $('a.popUpMessage').click(function() {
        // Generate html for Medium PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpMessage"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><h1>'
        // Take title from clicked link on page
            //+ $(this).attr('title')
            + '</h1><iframe src="'
        // Take iframe path from clicked link on page
            + $(this).attr('href')
            + '" id="iFrameHolder" frameborder="0" height="1"></iframe><hr /><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span>' + closeBtnText + '</span></a></div></div><div class="popUpBottom">&nbsp;</div></div><div class="loader"><p>' + loadingText + '...</p><img src="' + loadingImageUrl + '" alt="' + loadingText + '" /></div>');
        // Set the iframe height 
        $('div.popUpWindow iframe').height('525');
        // Disable iframe resizer, on load the iframe will have a scrollbar if content inside is too long
        iframeResizeDisabled = true;
        // this item requires a loader due to iframe
        loadingPopUp = true;
        // show loader
        $('div.loader').css('display', 'block');
        // run resize script to center loader
        setWindowSize();
        // bind click event to close button calling the closePopUp function
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // load the iframe
        iFrameLoad();
        // prevent default button action
        return false;
    });

                $('a.popUpMessageSave').click(function() {
        // Generate html for Medium PopUp
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpMessageSave"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><h1>'
        // Take title from clicked link on page
            + $(this).attr('title')
            + '</h1><hr /><iframe src="'
        // Take iframe path from clicked link on page
            + $(this).attr('href')
            + '" id="iFrameHolder" frameborder="0" height="1"></iframe></div><div class="popUpBottom">&nbsp;</div></div><div class="loader"><p>' + loadingText + '...</p><img src="' + loadingImageUrl + '" alt="' + loadingText + '" /></div>');
        // Disable iframe resizer, on load the iframe will have a scrollbar if content inside is too long
        iframeResizeDisabled = false;
        // this item requires a loader due to iframe
        loadingPopUp = true;
        // show loader
        $('div.loader').css('display', 'block');
        // run resize script to center loader
        setWindowSize();
        // bind click event to close button calling the closePopUp function
        $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
        // load the iframe
        iFrameLoad();
        // prevent default button action
        return false;
    });

    /*--- Section 12.9 - Interstitial Messaging PopUp, requires a link tag with a class of "popUpInterstitial" ---*/
    $('a.popUpInterstitial').click(function() {
    	// Generate html for Medium PopUp
        //next line is commented out because the cover layer will already be in place via a call to
        //showCoverLayer() and adding a second layer causes issues.
        //$('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpMessage"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><h1>'
        // Take title from clicked link on page
            //+ $(this).attr('title')
            + '</h1><iframe src="'
        // Take iframe path from clicked link on page
            + $(this).attr('href')
            + '" id="iFrameHolder" frameborder="0" height="1"></iframe><hr /><div class="buttonRow">'
            +'<a id="closeBtnId" href="#" class="'+closeBtnClass+'"><span>' + closeBtnText + '</span></a>'
            +'<div id="toggleDiv" style="visibility:'+ackVisibility+';float:right">'
            +'<input type="checkbox" name="iAccept" id="iAccept" value="yes"'
            +'onClick="if($(\'#iAccept\').attr(\'checked\')){'
            +'$(\'#closeBtnId\').removeClass(\'blueBtnMedDisabled\'); '  
            +'$(\'div.popUpWindow a.closePopUpBtn\').bind(\'click\', refreshLandingPage);}' 
            +'else{ $(\'#closeBtnId\').addClass(\'blueBtnMedDisabled\'); '  
            +'$(\'div.popUpWindow a.closePopUpBtn\').unbind(\'click\');}">' +acceptChkBoxText +'<br>'
            +'</div>'
            +'</div></div><div class="popUpBottom">&nbsp;</div></div><div class="loader"><p>' + loadingText + '...</p><img src="' + loadingImageUrl + '" alt="' + loadingText + '" /></div>');
        // Set the iframe height 
        $('div.popUpWindow iframe').height('525');
        // Disable iframe resizer, on load the iframe will have a scrollbar if content inside is too long
        iframeResizeDisabled = true;
        // this item requires a loader due to iframe
        loadingPopUp = true;
        // show loader
        $('div.loader').css('display', 'block');
        // run resize script to center loader
        setWindowSize();
        // bind click event to close button calling the refreshLandingPage function
        if(ackVisibility == 'hidden'){
        	$('div.popUpWindow a.closePopUpBtn').bind('click', refreshLandingPage);
        }
        // load the iframe
        iFrameLoad();
        // prevent default button action
        return false;
    });
});


$(window).load(function() {
    //call the date picker initialization method (defined in htmlHeaderIncludes.jsp)
	initializeDatePickers();
});


/*-----
    Section 13 - Opacity Focused Layer Script
    -----*/
// when loading pop up show loader
var loadingPopUp = true;
// If the iframe should not size to fit content
var iframeResizeDisabled = false;
// global iframe height for height fix in Chrome and Safari
var iframeHeight = 1;
// is the popup Visible
var popUpVisible = false;
// store the iframe's height
var iFrameHeight = 0;


// Opacity Focused Layer
function setWindowSize() {

    // determine the width and height of the pop up window for centering
    var popUpWidth = $('div.popUpWindow').width();
    var popUpHeight = $('div.popUpWindow').height();
    // reset size and positioning variables
    var docWidth = 0;
    var docHeight = 0;
    var marginWidth = 0;
    var marginTop = 0;
    var currentScrollPos = 0;

    // get the window's scrolled position
    if (document.documentElement.scrollTop == 0) {
        currentScrollPos = document.body.scrollTop;
    } else {
        currentScrollPos = document.documentElement.scrollTop;
    }

    // returns width of window $('body').width();
    // returns width of contents minimum and widow width max $(document).width();

    // determine the width the overlay needs to be
    if ($('body').width() > $('#headerWrapper').outerWidth()) {
        docWidth = $('body').width();
    } else {
        docWidth = $('#headerWrapper').outerWidth();
    }

    // determine the height of the page
    var wrapperHeight = $('#headerWrapper').outerHeight() + $('#contentWrapper').outerHeight() + $('#footerWrapper').outerHeight();

    // get height of the document compare it to the page's height and determine whichever is taller
    if ($(document).height() > (wrapperHeight)) {
        docHeight = $(document).height();
    } else {
        docHeight = wrapperHeight;
    }

    // set the the width of the coverlayer
    $('div#coverLayer').width(docWidth);
    $('div#coverLayer').height(docHeight);

    // if the pop up is loading
    if (loadingPopUp) {
        // determine the loader width
        var loaderHeight = $('div.loader').height();
        var loaderWidth = $('div.loader').width();

        // Determine the distance the window should be from the left
        if (docWidth > loaderWidth) {
            marginWidth = Math.round((docWidth - loaderWidth) / 2);
        }

        // Determine the distance the window should be from the top
        if (($(window).height() - loaderHeight) > 0) {
            marginTop = ($(window).height() - loaderHeight) / 2 + currentScrollPos;
        } else {
            marginTop = currentScrollPos;
        }

        // move the loader div to the center
        $('div.loader').css("margin-left", marginWidth);
        $('div.loader').css("margin-top", marginTop);
    } else {
        // Determine the distance the window should be from the left
        if (docWidth > popUpWidth) {
            marginWidth = Math.round((docWidth - popUpWidth) / 2);
        }

        // Determine the distance the window should be from the top
        if (($(window).height() - popUpHeight) > 0) {
            marginTop = ($(window).height() - popUpHeight) / 2 + currentScrollPos;
        } else {
            marginTop = currentScrollPos;
        }

        // move the pop up div to the center
        $('div.popUpWindow').css("margin-left", marginWidth);
        $('div.popUpWindow').css("margin-top", marginTop);
    }

    // get the total height of the pop up including the distance to the top of the browser.
    var totalHeight = $('div.popUpWindow').height() + marginTop;

    // if the total height is greater than the height of the document adjust the height of the coverlayer
    if (totalHeight > docHeight) {
        docHeight = totalHeight;
        $('div#coverLayer').height(docHeight);
    }
}

/*--- Section 13.1 - Resize the iFrame ---*/
// function to call set windowsize if the 
function resizeIframe() {
    //change the height of the iframe if the iframe should not have scrollbars
    if (!iframeResizeDisabled) {
        $('div.popUpWindow iframe').height(iFrameHeight);
    }
    // call setWindowSize
    setWindowSize();
}

/*--- Section 13.2 - Browser Resize Script ---*/
// on resize of the browser resize the window
var resizeTimer = null;
$(window).bind('resize', function() {
    // if the browser is already being resized clear the timeout
    if (resizeTimer) clearTimeout(resizeTimer);
    //
    resizeTimer = setTimeout(setWindowSize, 50);
});

/*--- Section 13.1 - Resize the iFrame ---*/
function setOrderConfirmation(){
	// add multiple select / deselect functionality
	$("#confirmOrderId").click(function () {
		var checked = $('input[type=checkbox]').is(':checked');
		if(!checked){
			$("#confirmOrderId").removeAttr("checked");
		} 
		$("#confirmOrderId").val(checked);
	});
}

function showMenu(panelOperation){
	if(panelOperation=='EXPAND_PANEL'){
		expandPanel();
	}else{
		collapsePanel();
	}
}

function collapsePanel(){
	$('td.expandPanel').removeClass('hide');
	$('td.collapsePanel').addClass('hide');
	$('div.collapsePanelLeftCol').addClass('hide');
	$('div.collapsePanelRightCol').removeClass('rightColumnIndent');
	$('div.collapsePanelRightCol').addClass('rightColumnIndentCollapse');
	$('div.collapsePanelWide').removeClass('wide');			
}
function expandPanel(){
	$('td.expandPanel').addClass('hide');
	$('td.collapsePanel').removeClass('hide');
	$('div.collapsePanelLeftCol').removeClass('hide');
	$('div.collapsePanelRightCol').removeClass('rightColumnIndentCollapse');
	$('div.collapsePanelRightCol').addClass('rightColumnIndent');
	$('div.collapsePanelWide').addClass('wide');
}


