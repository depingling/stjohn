/*-----
    TOC - Start eSpendwise Mobile Scripts

    Section 1: Language Variables - This is for some pop ups
    Section 2 - Form Interactions
    Section 3: Default Input Value - add class "default-value" to an input with a unique id and this will swap text on focus
    Section 4 - Pop Up Scripts
        Section 4.1 - Close Pop Up
        Section 4.2 - Approve Pop Up
        Section 4.3 - Approve Button (Triggers Pop Up)
        Section 4.4 - Small Ok/Cancel function call, requires a link tag with a class of "popUpOkSmall"
    Section 5 - Zone Collapse/Expand
    Section 10 - Opacity Focused Layer Script
        Section 10.1 - Resize the iFrame
        Section 10.2 - Browser Resize Script

-----*/

/*-----
Section 1: Language Variables - This is for some pop ups
-----*/
var active_color = '#575757'; // Color of user provided text
var inactive_color = '#C2C2C2'; // Color of default text

function submitFormToApproveOrders(operation) {
	strLength = operation.length;
	var op = operation.slice(11,strLength - 2);
    document.getElementById('orderFormOperation').value = op;
    document.getElementById('orderForm').submit();
}

$(document).ready(function() {
    /*-----
    Section 2 - Form Interactions 
    -----*/
    // Select all checkboxes
    $('span.selectNav a.all').click(function() {
        $(this).parents('div#mobileWrapper').find("input[type='checkbox']:not([disabled='disabled'])").attr('checked', true);
        return false;
    });

    // Deselect all checkboxes
    $('span.selectNav a.none').click(function() {
        $(this).parents('div#mobileWrapper').find("input[type='checkbox']:not([disabled='disabled'])").attr('checked', false);
        return false;
    });

    $('a.clearForm').click(function() {
        $(':input', $(this).parent('form')).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
    });

    // Confirmation Fields
    $('input.confirm').click(function() {
        if ($(this).is(':checked')) {
            $(this).parents('tr').siblings('tr.confirm').removeClass('hide');
        } else {
            $(this).parents('tr').siblings('tr.confirm').addClass('hide');
        }
    });
	
	// Check for Status Type  CP: NEW HACK children
    $('td.statusType').change(function() {	
		if ($(this).find('option:selected').attr('value') == 'closeStatus') {	
			$(this).parents('td.statusType').children('td.qualityType').removeClass('hide');
			$(this).parents('td.statusType').children('td.reasonType').addClass('hide');
			$(this).parents('td.statusType').children('td.otherType').addClass('hide');
		} else {
			if ($(this).find('option:selected').attr('value') == 'rejectStatus') {	
				$('td.qualityType').removeClass('hide');
				$('td.reasonType').removeClass('hide');
				$('td.otherType').removeClass('hide');
				$(this).parents('div.zoneContent').children('td.qualityType').addClass('hide');
				$(this).parents('div.zoneContent').children('td.reasonType').removeClass('hide');
				$(this).parents('div.zoneContent').children('td.otherType').addClass('hide');				
				$('td.optionClosed').removeAttr('disabled');
			} else {	
				$(this).parents('td.statusType').children('td.qualityType').addClass('hide');
				$(this).parents('td.statusType').children('td.reasonType').addClass('hide');
				$(this).parents('td.statusType').children('td.otherType').addClass('hide');
				$('td.optionClosed').removeAttr('disabled');
			}
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
	// Check for Primary Category  CP: NEW
    $('td.primaryCategory').change(function() {	
		if ($(this).find('option:selected').attr('value') == 'defaultCategory') {		
			$('tr.secondaryType').addClass('hide');
		} else {
			$('tr.secondaryType').removeClass('hide');
		}		
    });
	$('select.span').change(function() {
		var index = $(this).children('option:selected').attr('index')
		if ($(this).children('option:selected').attr('value') == 'newList') {	
			$('td.qualityType').show();
        }			
        return false;
    });
    /*-----
    Section 3: Default Input Value - add class "default-value" to an input with a unique id and this will swap text on focus
    -----*/
    /**
    * Written by Rob Schmitt, The Web Developer's Blog
    * http://webdeveloper.beforeseven.com/
    */
    $("input.default-value").css("color", active_color);
    var default_values = new Array();
    $("input.default-value").focus(function() {
        if (!default_values[this.id]) {
            default_values[this.id] = this.value;
        }
        if (this.value == default_values[this.id]) {
            this.value = '';
            this.style.color = active_color;
        }
        $(this).blur(function() {
            if (this.value == '') {
                this.style.color = active_color;
                this.value = default_values[this.id];
            }
        });
    });


    /*-----
    Section 4 - Pop Up Scripts
    -----*/

    /*--- Section 4.1 - Close Pop Up ---*/
    function closePopUp() {
        // for ie6 hide select menus which will show through overlay
        $('body').removeClass('hideSelects');
        $('div#coverLayer, div.loader, div.popUpWindow').css('visibility', 'hidden').remove();
    }

    /*--- Section 4.2 - Approve Pop Up ---*/
    function smallApprovePopUp(callingObject, approveLink, title) {
        // Generate the pop up window and append it to the page
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpSmall"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><p>' +
           title
        + '</p><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span>' + (title == noOrderSelectedForApproval ? okBtnText : cancelBtnText) + '</span></a><a href="javascript:submitFormToApproveOrders(\''+ approveLink +'\')" class="blueBtnLarge ' + (approveLink == "#" ? 'hide' : '') + '" ><span>' + saveBtnText + '</span></a></div></div><div class="popUpBottom">&nbsp;</div></div>');

        // Small pop ups do not require loading
        loadingPopUp = false;

        // Bind the click function to the pop up's close button
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

    /*--- Section 4.3 - Approve Button (Triggers Pop Up) ---*/
    // On click of the approve link trigger the confirmation pop up
    $('a.approveBtn').click(function() {
        // check how many items are selected
        var totalSelected = $(this).parents('div#mobileWrapper').contents().find('input:checked').size();
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
        // don't allow default link action
        return false;
    });
    
    /*--- Section 4.2 - Reject Pop Up ---*/ //STJ-4920
    function smallRejectPopUp(callingObject, rejectBtn, title) {
        // Generate the pop up window and append it to the page
        $('body').append('<div id="coverLayer">&nbsp;</div>');
        $('body').append('<div class="popUpWindow popUpSmall"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><p>' +
           title
        + '</p><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span>' + (title == noOrderSelectedForRejecting ? okBtnText : cancelBtnText) + '</span></a><a href="javascript:submitFormToApproveOrders(\''+ rejectBtn +'\')" class="blueBtnLarge ' + (rejectBtn == "#" ? 'hide' : '') + '" ><span>' + saveBtnText + '</span></a></div></div><div class="popUpBottom">&nbsp;</div></div>');

        // Small pop ups do not require loading
        loadingPopUp = false;

        // Bind the click function to the pop up's close button
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
    /*--- Section 4.3 - Reject Button (Triggers Pop Up) ---*/ //STJ-4920
    // On click of the approve link trigger the confirmation pop up
    $('a.rejectBtn').click(function() {
        // check how many items are selected
        var totalSelected = $(this).parents('div#mobileWrapper').contents().find('input:checked').size();
        if (totalSelected == 0) {
            // if none are selected, show none selected message and hide save button
        	smallRejectPopUp($('a.rejectBtn'), '#', noOrderSelectedForRejecting );
        } else if (totalSelected == 1) {
            // if one is selected, show single order message and show save button
        	smallRejectPopUp($('a.rejectBtn'), $('a.rejectBtn').attr('href'), singleOrderRejection );
        } else {
            // if multiple items were selected show multiple selected message and show save button
        	smallRejectPopUp($('a.rejectBtn'), $('a.rejectBtn').attr('href'), orderRejectionPreSize + totalSelected + orderRejectionPostSize );
        }
        // don't allow default link action
        return false;
    });
    
   

    
    /*--- Section 4.4 - Small Ok/Cancel function call, requires a link tag with a class of "popUpOkSmall" ---*/
    $('a.popUpOkSmall').click(function() {
        smallApprovePopUp($(this), $(this).attr('href'), $(this).attr('title'));
        return false;
    });

    /*-----
    Section 5 - Zone Collapse/Expand 
    -----*/	
   
    $('div.zone div.title a').click(function() {
        // when div.title is clicked toggle the visibility of div.zoneContent
		
		
		/* CP: THIS WAS THE ORIGINAL VERSION FOR 'div.zone div.title').click(function() */
		/* $(this).parents('div.zone').toggleClass('collapsed'); */	
		
		$(this).parents('div.zone').children('div.zoneContent').toggle();
		$(this).parents('div.zone').toggleClass('collapsed');
		
		if ($(this).parents('div.zone').children('div.zoneContent').is(':hidden')) {
			$(this).parents('div.zone').children('div.zoneScheduled').removeClass('hide');
		} else {
			$(this).parents('div.zone').children('div.zoneScheduled').addClass('hide');
		}
		
		/* CP: THIS WAS THE PREVIOUS VERSION FOR 'div.zone div.title').click(function() */
		/*
			$(this).siblings('div.zoneContent').toggle().parent('div.zone').toggleClass('collapsed');
		
			if ($(this).siblings('div.zoneContent').is(':hidden')) {
				$(this).siblings('div.zoneScheduled').removeClass('hide');
			} else {
				$(this).siblings('div.zoneScheduled').addClass('hide');
			}
		*/
		
    });
	$('a.collapse').click(function() {
        // CP: Collapse All Zones  
		$('div.zone div.title').each(function() {
			$(this).siblings('div.zoneContent').toggle(false).parent('div.zone').addClass('collapsed');
			$(this).siblings('div.zoneScheduled').removeClass('hide')
		});	
    });
    $('a.expand').click(function() { 
		// CP: Expand All Zones		
		$('div.zone div.title').each(function() { 	
			$(this).siblings('div.zoneContent').toggle(true).parent('div.zone').removeClass('collapsed');
			$(this).siblings('div.zoneScheduled').addClass('hide')
		});
    });
	$('a.selectAll').click(function() {
        // CP: Select All   
		$('div.zone div.title').each(function() {
			$(this).parent('div.zone').find("span input[type='checkbox']:not([disabled='disabled'])").attr('checked', true);
		});	
    });
    $('a.selectNone').click(function() { 
		// CP: Select None		
		$('div.zone div.title').each(function() { 
		$(this).parent('div.zone').find("span input[type='checkbox']:not([disabled='disabled'])").attr('checked', false);
		});	
    });
	
    // Match dates when approve on fields are blurred
    $('input.dateMatch').blur(function() {
        var enteredValue = $(this).attr('value');
        $('input.dateMatch').attr('value',enteredValue);
    });

});



/*-----
    Section 10 - Opacity Focused Layer Script
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
    if(document.documentElement.scrollTop == 0){
        currentScrollPos = document.body.scrollTop;
    } else {
        currentScrollPos = document.documentElement.scrollTop
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
	if ($(window).height() > (wrapperHeight)) {
	    docHeight = $(window).height()
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
        if(docWidth > loaderWidth){
            marginWidth = Math.round((docWidth - loaderWidth)/2);
        }

        // Determine the distance the window should be from the top
        if(($(window).height()-loaderHeight)>0){
            marginTop = ($(window).height()-loaderHeight)/2 + currentScrollPos;
        } else {
            marginTop = currentScrollPos;
        }
        
        // move the loader div to the center
        $('div.loader').css("margin-left", marginWidth);
        $('div.loader').css("margin-top", marginTop);
    } else {
        // Determine the distance the window should be from the left
        if(docWidth > popUpWidth){
            marginWidth = Math.round((docWidth - popUpWidth)/2);
        }

        // Determine the distance the window should be from the top
        if(($(window).height()-popUpHeight)>0){
            marginTop = ($(window).height()-popUpHeight)/2 + currentScrollPos;
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
	if(totalHeight > docHeight){
	    docHeight = totalHeight;
        $('div#coverLayer').height(docHeight);
	}
}

/*--- Section 10.1 - Resize the iFrame ---*/
// function to call set windowsize if the 
function resizeIframe(){
    //change the height of the iframe if the iframe should not have scrollbars
    if (!iframeResizeDisabled) {
        $('div.popUpWindow iframe').height(iFrameHeight);
    }
    // call setWindowSize
    setWindowSize();
}

/*--- Section 10.2 - Browser Resize Script ---*/
// on resize of the browser resize the window
var resizeTimer = null;
$(window).bind('resize', function() {
    // if the browser is already being resized clear the timeout
    if (resizeTimer) clearTimeout(resizeTimer);    
    //
    resizeTimer = setTimeout(setWindowSize, 50);
});
