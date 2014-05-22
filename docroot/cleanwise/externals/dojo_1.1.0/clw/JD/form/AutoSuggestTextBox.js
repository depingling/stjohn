if(!dojo._hasResource["clw.JD.form.AutoSuggestTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["clw.JD.form.AutoSuggestTextBox"] = true;
    dojo.provide("clw.JD.form.AutoSuggestTextBox");

    dojo.require("dijit.form.TextBox");

    dojo.requireLocalization("clw.JD.form", "AutoSuggestTextBox", null, "ROOT", "", "", "", "", "", "");

    dojo.declare(
        "clw.JD.form.AutoSuggestTextBoxMixin",
        null,
        {
            // item: Object
            //		This is the item returned by the dojo.data.store implementation that
            //		provides the data for this cobobox, it's the currently selected item.
            item: null,

            // pageSize: Integer
            //		Argument to data provider.
            //		Specifies number of search results per page (before hitting "next" button)
            pageSize: Infinity,

            // store: Object
            //		Reference to data provider object used by this ComboBox
            store: null,

            viewModel:null,

            // query: Object
            //		A query that can be passed to 'store' to initially filter the items,
            //		before doing further filtering based on `searchAttr` and the key.
            //		Any reference to the `searchAttr` is ignored.
            query: {},

            // autoComplete: Boolean
            //		If you type in a partial string, and then tab out of the `<input>` box,
            //		automatically copy the first entry displayed in the drop down list to
            //		the `<input>` field
            autoComplete: true,

            // searchDelay: Integer
            //		Delay in milliseconds between when user types something and we start
            //		searching based on that value
            searchDelay: 800,

            // searchAttr: String
            //		Searches pattern match against this field
            searchAttr: "name",

            // queryExpr: String
            //		dojo.data query expression pattern.
            //		`${0}` will be substituted for the user text.
            //		`*` is used for wildcards.
            //		`${0}*` means "starts with", `*${0}*` means "contains", `${0}` means "is"
            queryExpr: "${0}*",

            // ignoreCase: Boolean
            //		Set true if the ComboBox should ignore case when matching possible items
            ignoreCase: true,

            //input field name
            iName:null,

            displayAttr:[],


            templateString:null,

            baseClass: "dijitTextBox",

            _getCaretPos: function(/*DomNode*/ element){
                // khtml 3.5.2 has selection* methods as does webkit nightlies from 2005-06-22
                var pos = 0;
                if(typeof(element.selectionStart)=="number"){
                    // FIXME: this is totally borked on Moz < 1.3. Any recourse?
                    pos = element.selectionStart;
                }else if(dojo.isIE){
                    // in the case of a mouse click in a popup being handled,
                    // then the dojo.doc.selection is not the textarea, but the popup
                    // var r = dojo.doc.selection.createRange();
                    // hack to get IE 6 to play nice. What a POS browser.
                    var tr = dojo.doc.selection.createRange().duplicate();
                    var ntr = element.createTextRange();
                    tr.move("character",0);
                    ntr.move("character",0);
                    try{
                        // If control doesnt have focus, you get an exception.
                        // Seems to happen on reverse-tab, but can also happen on tab (seems to be a race condition - only happens sometimes).
                        // There appears to be no workaround for this - googled for quite a while.
                        ntr.setEndPoint("EndToEnd", tr);
                        pos = String(ntr.text).replace(/\r/g,"").length;
                    }catch(e){
                        // If focus has shifted, 0 is fine for caret pos.
                    }
                }
                return pos;
            },

            _setCaretPos: function(/*DomNode*/ element, /*Number*/ location){
                location = parseInt(location);
                dijit.selectInputText(element, location, location);
            },

            _setAttribute: function(/*String*/ attr, /*anything*/ value){
                // summary: additional code to set disablbed state of combobox node
                if (attr == "disabled"){
                    dijit.setWaiState(this.comboNode, "disabled", value);
                }
            },

            _onKeyPress: function(/*Event*/ evt){
                // summary: handles keyboard events

                //except for pasting case - ctrl + v(118)
                if(evt.altKey || (evt.ctrlKey && evt.charCode != 118)){
                    return;
                }
                var doSearch = false;
                var pw = this._popupWidget;
                var dk = dojo.keys;
                if(this._isShowingNow){
                    pw.handleKey(evt);
                }
                switch(evt.keyCode){
                    case dk.PAGE_DOWN:
                    case dk.DOWN_ARROW:
                        if(!this._isShowingNow||this._prev_key_esc){
                            doSearch=true;
                        }else{
                            this._announceOption(pw.getHighlightedOption());
                        }
                        dojo.stopEvent(evt);
                        this._prev_key_backspace = false;
                        this._prev_key_esc = false;
                        break;

                    case dk.PAGE_UP:
                    case dk.UP_ARROW:
                        if(this._isShowingNow){
                            this._announceOption(pw.getHighlightedOption());
                        }
                        dojo.stopEvent(evt);
                        this._prev_key_backspace = false;
                        this._prev_key_esc = false;
                        break;

                    case dk.ENTER:
                        // prevent submitting form if user presses enter. Also
                        // prevent accepting the value if either Next or Previous
                        // are selected
                        var highlighted;
                        if(	this._isShowingNow &&
                            (highlighted = pw.getHighlightedOption())
                        ){
                            // only stop event on prev/next
                            if(highlighted == pw.nextButton){
                                this._nextSearch(1);
                                dojo.stopEvent(evt);
                                break;
                            }else if(highlighted == pw.previousButton){
                                this._nextSearch(-1);
                                dojo.stopEvent(evt);
                                break;
                            } else{
                                this.setDisplayedValue(this.getDisplayedValue());
                            }
                        }else{
                            this.setDisplayedValue(this.getDisplayedValue());
                        }
                        // default case:
                        // prevent submit, but allow event to bubble
                        evt.preventDefault();
                        // fall through

                    case dk.TAB:
                        var newvalue = this.getDisplayedValue();
                        // #4617:
                        //		if the user had More Choices selected fall into the
                        //		_onBlur handler
                        if(pw && (
                            newvalue == pw._messages["previousMessage"] ||
                            newvalue == pw._messages["nextMessage"])
                        ){
                            break;
                        }
                        if(this._isShowingNow){
                            this._prev_key_backspace = false;
                            this._prev_key_esc = false;
                            if(pw.getHighlightedOption()){
                                pw.setValue({ target: pw.getHighlightedOption() }, true);
                            }
                            this._hideResultList();
                        }
                        break;

                    case dk.SPACE:
                        this._prev_key_backspace = false;
                        this._prev_key_esc = false;
                        if(this._isShowingNow && pw.getHighlightedOption()){
                            dojo.stopEvent(evt);
                            this._selectOption();
                            this._hideResultList();
                        }else{
                            doSearch = true;
                        }
                        break;

                    case dk.ESCAPE:
                        this._prev_key_backspace = false;
                        this._prev_key_esc = true;
                        if(this._isShowingNow){
                            dojo.stopEvent(evt);
                            this._hideResultList();
                        }
                        this.inherited(arguments);
                        break;

                    case dk.DELETE:
                    case dk.BACKSPACE:
                        this._prev_key_esc = false;
                        this._prev_key_backspace = true;
                        doSearch = true;
                        break;

                    case dk.RIGHT_ARROW: // fall through
                    case dk.LEFT_ARROW:
                        this._prev_key_backspace = false;
                        this._prev_key_esc = false;
                        break;

                    default: // non char keys (F1-F12 etc..)  shouldn't open list
                        this._prev_key_backspace = false;
                        this._prev_key_esc = false;
                        if(dojo.isIE || evt.charCode != 0){
                            doSearch = true;
                        }
                }
                if(this.searchTimer){
                    clearTimeout(this.searchTimer);
                }
                if(doSearch){
                    // need to wait a tad before start search so that the event
                    // bubbles through DOM and we have value visible
                    setTimeout(dojo.hitch(this, "_startSearchFromInput"),1);
                }
            },

            _autoCompleteText: function(/*String*/ text){
                // summary:
                // 		Fill in the textbox with the first item from the drop down
                // 		list, and highlight the characters that were
                // 		auto-completed. For example, if user typed "CA" and the
                // 		drop down list appeared, the textbox would be changed to
                // 		"California" and "ifornia" would be highlighted.

                var fn = this.focusNode;

                // IE7: clear selection so next highlight works all the time
                dijit.selectInputText(fn, fn.value.length);
                // does text autoComplete the value in the textbox?
                var caseFilter = this.ignoreCase? 'toLowerCase' : 'substr';
                if(text[caseFilter](0).indexOf(this.focusNode.value[caseFilter](0)) == 0){
                    var cpos = this._getCaretPos(fn);
                    // only try to extend if we added the last character at the end of the input
                    if((cpos+1) > fn.value.length){
                        // only add to input node as we would overwrite Capitalisation of chars
                        // actually, that is ok
                        fn.value = text;//.substr(cpos);
                        // visually highlight the autocompleted characters
                        dijit.selectInputText(fn, cpos);
                    }
                }else{
                    // text does not autoComplete; replace the whole value and highlight
                    fn.value = text;
                    dijit.selectInputText(fn);
                }
            },

            _openResultList: function(/*Object*/ results, /*Object*/ dataObject){
                if(	this.disabled ||
                    this.readOnly ||
                    (dataObject.query[this.searchAttr] != this._lastQuery)
                ){
                    return;
                }
                this._popupWidget.clearResultList();
                if(!results.length){
                    this._hideResultList();
                    return;
                }

                // Fill in the textbox with the first item from the drop down list,
                // and highlight the characters that were auto-completed. For
                // example, if user typed "CA" and the drop down list appeared, the
                // textbox would be changed to "California" and "ifornia" would be
                // highlighted.

                var zerothvalue = new String(this.store.getValue(results[0], this.searchAttr));
                if(zerothvalue && this.autoComplete && !this._prev_key_backspace &&
                    (dataObject.query[this.searchAttr] != "*")){
                    // when the user clicks the arrow button to show the full list,
                    // startSearch looks for "*".
                    // it does not make sense to autocomplete
                    // if they are just previewing the options available.
                    this._autoCompleteText(zerothvalue);
                }
                this._popupWidget.createOptions(
                    results,
                    dataObject,
                    dojo.hitch(this, "_getMenuLabelFromItem")
                );

                // show our list (only if we have content, else nothing)
                this._showResultList();

                // #4091:
                //		tell the screen reader that the paging callback finished by
                //		shouting the next choice
                if(dataObject.direction){
                    if(1 == dataObject.direction){
                        this._popupWidget.highlightFirstOption();
                    }else if(-1 == dataObject.direction){
                        this._popupWidget.highlightLastOption();
                    }
                    this._announceOption(this._popupWidget.getHighlightedOption());
                }
            },

            _showResultList: function(){
                this._hideResultList();
                var items = this._popupWidget.getItems(),
                    visibleCount = Math.min(items.length,this.maxListLength);
                // hide the tooltip  if extedned ValidateTextBox
                //this.displayMessage("");

                // Position the list and if it's too big to fit on the screen then
                // size it to the maximum possible height
                // Our dear friend IE doesnt take max-height so we need to
                // calculate that on our own every time

                // TODO: want to redo this, see
                //		http://trac.dojotoolkit.org/ticket/3272
                //	and
                //		http://trac.dojotoolkit.org/ticket/4108

                with(this._popupWidget.domNode.style){
                    // natural size of the list has changed, so erase old
                    // width/height settings, which were hardcoded in a previous
                    // call to this function (via dojo.marginBox() call)
                    width = "";
                    height = "";
                }
                var best = this.open();
                // #3212:
                //		only set auto scroll bars if necessary prevents issues with
                //		scroll bars appearing when they shouldn't when node is made
                //		wider (fractional pixels cause this)
                var popupbox = dojo.marginBox(this._popupWidget.domNode);
                this._popupWidget.domNode.style.overflow =
                    ((best.h==popupbox.h)&&(best.w==popupbox.w)) ? "hidden" : "auto";
                // #4134:
                //		borrow TextArea scrollbar test so content isn't covered by
                //		scrollbar and horizontal scrollbar doesn't appear
                var newwidth = best.w;
                if(best.h < this._popupWidget.domNode.scrollHeight){
                    newwidth += 16;
                }
                dojo.marginBox(this._popupWidget.domNode, {
                    h: best.h,
                    w: Math.max(newwidth, this.domNode.offsetWidth)
                });
                dijit.setWaiState(this.comboNode, "expanded", "true");
            },

            _hideResultList: function(){
                if(this._isShowingNow){
                    dijit.popup.close(this._popupWidget);
                    this._isShowingNow=false;
                    dijit.setWaiState(this.comboNode, "expanded", "false");
                    dijit.removeWaiState(this.focusNode,"activedescendant");
                }
            },

            _setBlurValue: function(){
                // if the user clicks away from the textbox OR tabs away, set the
                // value to the textbox value
                // #4617:
                //		if value is now more choices or previous choices, revert
                //		the value
                var newvalue=this.getDisplayedValue();
                var pw = this._popupWidget;
                if(pw && (
                    newvalue == pw._messages["previousMessage"] ||
                    newvalue == pw._messages["nextMessage"]
                    )
                ){
                    this.setValue(this._lastValueReported, true);
                }else{
                    this.setDisplayedValue(newvalue);
                }
            },

            _onBlur: function(){
                // summary: called magically when focus has shifted away from this widget and it's dropdown
                this._hideResultList();
                this.inherited(arguments);
            },

            _announceOption: function(/*Node*/ node){
                // summary:
                //		a11y code that puts the highlighted option in the textbox
                //		This way screen readers will know what is happening in the
                //		menu

                if(node == null){
                    return;
                }
                // pull the text value from the item attached to the DOM node
                var newValue;
                if( node == this._popupWidget.nextButton ||
                    node == this._popupWidget.previousButton){
                    newValue = node.innerHTML;
                }else{
                    newValue = this.store.getValue(node.item, this.searchAttr);
                }
                // get the text that the user manually entered (cut off autocompleted text)
                this.focusNode.value = this.focusNode.value.substring(0, this._getCaretPos(this.focusNode));
                //set up ARIA activedescendant
                dijit.setWaiState(this.focusNode, "activedescendant", dojo.attr(node, "id"));
                // autocomplete the rest of the option to announce change
                this._autoCompleteText(newValue);
            },

            _selectOption: function(/*Event*/ evt){
                var tgt = null;
                if(!evt){
                    evt ={ target: this._popupWidget.getHighlightedOption()};
                }
                    // what if nothing is highlighted yet?
                if(!evt.target){
                    // handle autocompletion where the the user has hit ENTER or TAB
                    this.setDisplayedValue(this.getDisplayedValue());
                    return;
                // otherwise the user has accepted the autocompleted value
                }else{
                    tgt = evt.target;
                }
                if(!evt.noHide){
                    this._hideResultList();
                    this._setCaretPos(this.focusNode, this.store.getValue(tgt.item, this.searchAttr).length);
                }
                this._doSelect(tgt);
            },

            _doSelect: function(tgt){
                this.item = tgt.item;
                this.setValue(this.store.getValue(tgt.item, this.searchAttr), true);
            },

            _onArrowMouseDown: function(evt){
                // summary: callback when arrow is clicked
                if(this.disabled || this.readOnly){
                    return;
                }
                dojo.stopEvent(evt);
                this.focus();
                if(this._isShowingNow){
                    this._hideResultList();
                }else{
                    // forces full population of results, if they click
                    // on the arrow it means they want to see more options
                    this._startSearch("");
                }
            },

            _startSearchFromInput: function(){
                this._startSearch(this.focusNode.value);
            },

            _getQueryString: function(/*String*/ text){
                return dojo.string.substitute(this.queryExpr, [text]);
            },

            _startSearch: function(/*String*/ key){
                if(!this._popupWidget){
                    var popupId = this.id + "_popup";
                    this._popupWidget = new clw.JD.form.AutoSuggestTextBoxMenu({
                        onChange: dojo.hitch(this, this._selectOption),
                        id:popupId,viewModel:this.viewModel,parent:this
                    });
                    dijit.removeWaiState(this.focusNode,"activedescendant");
                    dijit.setWaiState(this.textbox,"owns",popupId); // associate popup with textbox
                }
                // create a new query to prevent accidentally querying for a hidden
                // value from FilteringSelect's keyField
                this.item = null; // #4872
                var query = dojo.clone(this.query); // #5970
                this._lastQuery = query[this.searchAttr] = this._getQueryString(key);
                // #5970: set _lastQuery, *then* start the timeout
                // otherwise, if the user types and the last query returns before the timeout,
                // _lastQuery won't be set and their input gets rewritten
                this.searchTimer=setTimeout(dojo.hitch(this, function(query, _this){
                    dojo.xhrPost({load:dojo.hitch(_this, function(data){

                        _this.store._jsonData = data
                        _this.store._forceLoad();

                        var dataObject = _this.store.fetch({
                            queryOptions: {
                                ignoreCase: _this.ignoreCase,
                                deep: true
                            },
                            query: query,
                            onComplete: dojo.hitch(_this, "_openResultList"),
                            onError: function(errText){
                                dojo.hitch(_this, "_hideResultList")();
                            },
                            start:0,
                            count:_this.pageSize
                        });

                        var nextSearch = function(dataObject, direction){
                            dataObject.start += dataObject.count*direction;
                            // #4091:
                            //		tell callback the direction of the paging so the screen
                            //		reader knows which menu option to shout
                            dataObject.direction = direction;
                            _this.store.fetch(dataObject);
                        }

                        _this._nextSearch = _this._popupWidget.onPage = dojo.hitch(_this, nextSearch, dataObject);

                    }),form:_this.name,handleAs:'json',content:dojo.fromJson("{action:\""+_this.action+"\",requestType:\"async\",\""+_this.iName+"\":"+this.normalizeValue(this.textbox.value)+"}")});


                }, query, this), this.searchDelay);
            },

            normalizeValue: function(val) {
                //	summary:
                //		Apply specified filters to textbox value

                if (val === null || val === undefined) {
                    return "";
                }

                else if (typeof val != "string") {
                    return val;
                }

                val = dojo._escapeString(val);

                return val;

            },

            _getValueField:function(){
                return this.searchAttr;
            },

            /////////////// Event handlers /////////////////////


            // FIXME:
            //		this is public so we can't remove until 2.0, but the name
            //		SHOULD be "compositionEnd"

            compositionend: function(/*Event*/ evt){
                //	summary:
                //		When inputting characters using an input method, such as
                //		Asian languages, it will generate this event instead of
                //		onKeyDown event Note: this event is only triggered in FF
                //		(not in IE)
                this.onkeypress({charCode:-1});
            },

            //////////// INITIALIZATION METHODS ///////////////////////////////////////

            constructor: function(){
                this.query={};
            },

            postMixInProperties: function(){

            },

            _postCreate:function(){

            },

            uninitialize:function(){
                if(this._popupWidget){
                    this._hideResultList();
                    this._popupWidget.destroy()
                }
            },


            _getMenuLabelFromItem:function(/*Item*/ item) {

                var value;

                if (this.displayAttr && this.displayAttr.length > 0) {

                    value = new Array(this.displayAttr.length);

                    for (var i = 0; i < this.displayAttr.length; i++) {
                        value[i] = this.store.getValue(item, this.displayAttr[i].label)
                    }

                } else {
                    value = this.store.getValue(item, this.searchAttr);
                }


                return {
                    html: false,
                    value: value
                };

            },

            open:function(){
                this._isShowingNow=true;
                 if(dojo.isIE){
                   return dijit.popup.open({
                    popup: this._popupWidget,
                    padding:{top:0,right:0,bottom:2,left:2},
                    around: this.domNode,
                    parent: this
                });
                } else{return dijit.popup.open({
                    popup: this._popupWidget,
                    around: this.domNode,
                    parent: this
                });
                         }

            },

            reset:function(){
                //	summary:
                //		Additionally reset the .item (to clean up).
                this.item = null;
                this.inherited(arguments);
            }

        }
    );

    dojo.declare(
        "clw.JD.form.AutoSuggestTextBoxMenu",
        [dijit._Widget, dijit._Templated],

        {

            templateString: null,

            _messages: null,

            viewModel:null,

            parent:null,

            postMixInProperties: function() {
                this.inherited("postMixInProperties", arguments);
                if(!this.viewModel){
                    this.viewModel = new clw.JD.form.ListModel();
                }

                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    this.templateString = "<table class='dijitMenu' dojoAttachEvent='onmousedown:_onMouseDown,onmouseup:_onMouseUp,onmouseover:_onMouseOver,onmouseout:_onMouseOut' tabIndex='-1' style='overflow:\"auto\";'>"
                            + "<thead><tr class='dijitMenuItem dijitMenuHeader' dojoAttachPoint='header'>";
                    for (var i = 0; i < this.parent.displayAttr.length; i++) {
                        this.templateString += "<th>" + this.parent.displayAttr[i].displayLabel + "</th>";
                    }
                    this.templateString += "</tr></thead><tbody dojoAttachPoint='body'></tbody>"
                    this.templateString +="</table>";
                } else {
                    this.templateString = "<ul class='dijitMenu' dojoAttachEvent='onmousedown:_onMouseDown,onmouseup:_onMouseUp,onmouseover:_onMouseOver,onmouseout:_onMouseOut' tabIndex='-1' style='overflow:\"auto\";'>"
                            + "<li class='dijitMenuItem dijitMenuPreviousButton' dojoAttachPoint='previousButton'></li>"
                            + "<li class='dijitMenuItem dijitMenuNextButton' dojoAttachPoint='nextButton'></li>"
                            + "</ul>";
                }

               this._messages = dojo.i18n.getLocalization("clw.JD.form", "AutoSuggestTextBox", this.lang);
        },

            setValue: function(/*Object*/ value){
                this.value = value;
                this.onChange(value);
            },

            // stubs
            onChange: function(/*Object*/ value){},
            onPage: function(/*Number*/ direction){},

            constructor:function(){

            },


            postCreate:function() {
                // fill in template with i18n messages
                if (this.viewModel instanceof clw.JD.form.TableModel) {
                } else {
                    this.previousButton.innerHTML = this._messages["previousMessage"];
                    this.nextButton.innerHTML = this._messages["nextMessage"];
                }
                this.inherited("postCreate", arguments);

            },

            onClose:function(){
                this._blurOptionNode();
            },

            _createOption:function(/*Object*/ item, labelFunc){

                var labelObject = labelFunc(item);
                var menuitem;
                if (this.viewModel instanceof clw.JD.form.ListModel) {
                    menuitem = dojo.doc.createElement("li");
                    dijit.setWaiRole(menuitem, "option");
                    menuitem.appendChild(dojo.doc.createTextNode(labelObject.value));
               } else if (this.viewModel instanceof clw.JD.form.TableModel) {
                    menuitem = dojo.doc.createElement("tr");
                    dijit.setWaiRole(menuitem, "option");
                    for (var i = 0; i < labelObject.value.length; i++) {
                        var tdEl = dojo.doc.createElement("td");
                        tdEl.appendChild(dojo.doc.createTextNode(labelObject.value[i]));
                        menuitem.appendChild(tdEl)
                    }
                }

                // #3250: in blank options, assign a normal height
                if(menuitem.innerHTML == ""){
                    menuitem.innerHTML = "&nbsp;";
                }
                menuitem.item=item;
                return menuitem;
            },

            createOptionsListModel: function(results, dataObject, labelFunc){

                //this._dataObject=dataObject;
                //this._dataObject.onComplete=dojo.hitch(comboBox, comboBox._openResultList);
                // display "Previous . . ." button
                this.previousButton.style.display = (dataObject.start == 0) ? "none" : "";
                dojo.attr(this.previousButton, "id", this.id + "_prev");
                // create options using _createOption function defined by parent
                //  (or FilteringSelect) class
                // #2309:
                //		iterate over cache nondestructively
                dojo.forEach(results, function(item, i){
                    var menuitem = this._createOption(item, labelFunc);
                    menuitem.className = "dijitMenuItem";
                    dojo.attr(menuitem, "id", this.id + i);
                    this.domNode.insertBefore(menuitem, this.nextButton);
                }, this);
                // display "Next . . ." button
                this.nextButton.style.display = (dataObject.count == results.length) ? "" : "none";
                dojo.attr(this.nextButton,"id", this.id + "_next")
            },

            createOptionsTableModel: function(results, dataObject, labelFunc){

                //this._dataObject=dataObject;
                //this._dataObject.onComplete=dojo.hitch(comboBox, comboBox._openResultList);
                // display "Previous . . ." button
                //this.header.style.display = (dataObject.start == 0) ? "none" : "";
                dojo.attr(this.header, "id", this.id + "_tableHead");
                // create options using _createOption function defined by parent
                //  (or FilteringSelect) class
                // #2309:
                //		iterate over cache nondestructively
                dojo.forEach(results, function(item, i){
                    var menuitem = this._createOption(item, labelFunc);
                    menuitem.className = "dijitMenuItem";
                    dojo.attr(menuitem, "id", this.id + i);
                    this.body.appendChild(menuitem);
                }, this);

            },

            createOptions: function(results, dataObject, labelFunc){
                if(this.viewModel instanceof clw.JD.form.TableModel){
                     this.createOptionsTableModel(results, dataObject, labelFunc)
                 } else if(this.viewModel instanceof clw.JD.form.ListModel){
                     this.createOptionsListModel(results, dataObject, labelFunc)
                 }

            },

            clearResultList: function() {
                // keep the previous and next buttons of course
                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    while (this.body.childNodes.length > 0) {
                        this.body.removeChild(this.body.childNodes[this.body.childNodes.length - 1]);
                    }
                } else if (this.viewModel instanceof clw.JD.form.ListModel) {
                    while (this.domNode.childNodes.length > 2) {
                        this.domNode.removeChild(this.domNode.childNodes[this.domNode.childNodes.length - 2]);
                    }
                }

            },

        // these functions are called in showResultList
            getItems: function() {
                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    return this.body.childNodes;
                } else if (this.viewModel instanceof clw.JD.form.ListModel) {
                    return this.domNode.childNodes;
                }
            },

            getListLength: function() {
                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    return this.body.childNodes.length;
                } else if (this.viewModel instanceof clw.JD.form.ListModel) {
                    return this.domNode.childNodes.length - 2;
                }
            },

            _onMouseDown: function(/*Event*/ evt){
                dojo.stopEvent(evt);
            },

            _onMouseUp: function(/*Event*/ evt) {
                if (evt.target === this.domNode || evt.target == this.header) {
                    return;
                } else if (evt.target == this.previousButton) {
                    this.onPage(-1);
                } else if (evt.target == this.header) {
                    this.onPage(-1);
                } else if (evt.target == this.nextButton) {
                    this.onPage(1);
                } else {
                    var tgt = evt.target;
                    // while the clicked node is inside the div
                    while (!tgt.item && tgt != this.domNode && tgt != this.header) {
                        // recurse to the top
                        tgt = tgt.parentNode;

                    }
                    if (!(tgt === this.domNode || tgt == this.header)) {
                        this.setValue({ target: tgt }, true);
                    }
                }
            },

            _onMouseOver: function(/*Event*/ evt){
               if(evt.target == this.domNode || evt.target == this.header){ return; }
                var tgt = evt.target;
                if(!(tgt == this.previousButton || tgt == this.nextButton || tgt == this.header)){
                    // while the clicked node is inside the div
                    while(!tgt.item && tgt!=this.domNode && tgt != this.header){
                        // recurse to the top
                        tgt = tgt.parentNode;
                    }
                }

                if(tgt == this.domNode || tgt == this.header){
                    return;
                }
                this._focusOptionNode(tgt);
            },

            _onMouseOut:function(/*Event*/ evt){
                if(evt.target === this.domNode){ return; }
                this._blurOptionNode();
            },

            _focusOptionNode:function(/*DomNode*/ node){
                // summary:
                //	does the actual highlight
                if(this._highlighted_option != node){
                    this._blurOptionNode();
                    this._highlighted_option = node;
                    dojo.addClass(this._highlighted_option, "dijitMenuItemHover");
                }
            },

            _blurOptionNode:function(){
                // summary:
                //	removes highlight on highlighted option
                if(this._highlighted_option){
                    dojo.removeClass(this._highlighted_option, "dijitMenuItemHover");
                    this._highlighted_option = null;
                }
            },

            _highlightNextOption:function(){
                //	summary:
                // 		Highlight the item just below the current selection.
                // 		If nothing selected, highlight first option

                // because each press of a button clears the menu,
                // the highlighted option sometimes becomes detached from the menu!
                // test to see if the option has a parent to see if this is the case.
                var fc

                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    fc = this.body.firstChild;
                } else if (this.viewModel instanceof clw.JD.form.ListModel) {
                    fc = this.domNode.firstChild;
                }
                if(!this.getHighlightedOption()){
                    this._focusOptionNode(fc.style.display=="none" ? fc.nextSibling : fc);
                }else{
                    var ns = this._highlighted_option.nextSibling;
                    if(ns && ns.style.display!="none"){
                        this._focusOptionNode(ns);
                    }
                }
                // scrollIntoView is called outside of _focusOptionNode because in IE putting it inside causes the menu to scroll up on mouseover
                dijit.scrollIntoView(this._highlighted_option);
            },

            highlightFirstOption:function(){
                //	summary:
                // 		Highlight the first real item in the list (not Previous Choices).
                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    this._focusOptionNode(this.body.firstChild.nextSibling);
                } else if (this.viewModel instanceof clw.JD.form.ListModel) {
                    this._focusOptionNode(this.domNode.firstChild.nextSibling);
                }

                dijit.scrollIntoView(this._highlighted_option);
            },

            highlightLastOption:function(){
                //	summary:
                // 		Highlight the last real item in the list (not More Choices).
                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    this._focusOptionNode(this.body.lastChild.nextSibling);
                } else if (this.viewModel instanceof clw.JD.form.ListModel) {
                    this._focusOptionNode(this.domNode.lastChild.nextSibling);
                }
                dijit.scrollIntoView(this._highlighted_option);
            },

            _highlightPrevOption:function(){
                //	summary:
                // 		Highlight the item just above the current selection.
                // 		If nothing selected, highlight last option (if
                // 		you select Previous and try to keep scrolling up the list)
                var lc;


                if (this.viewModel instanceof clw.JD.form.TableModel) {
                    lc = this.body.lastChild
                } else if (this.viewModel instanceof clw.JD.form.ListModel) {
                    lc = this.domNode.lastChild;
                }

                if(!this.getHighlightedOption()){
                    this._focusOptionNode(lc.style.display == "none" ? lc.previousSibling : lc);
                }else{
                    var ps = this._highlighted_option.previousSibling;
                    if(ps && ps.style.display != "none"){
                        this._focusOptionNode(ps);
                    }
                }
                dijit.scrollIntoView(this._highlighted_option);
            },

            _page:function(/*Boolean*/ up){
                var scrollamount = 0;
                var oldscroll = this.domNode.scrollTop;
                var height = dojo.style(this.domNode, "height");
                // if no item is highlighted, highlight the first option
                if(!this.getHighlightedOption()){
                    this._highlightNextOption();
                }
                while(scrollamount<height){
                    if(up){
                        // stop at option 1
                        if(!this.getHighlightedOption().previousSibling ||
                            this._highlighted_option.previousSibling.style.display == "none"){
                            break;
                        }
                        this._highlightPrevOption();
                    }else{
                        // stop at last option
                        if(!this.getHighlightedOption().nextSibling ||
                            this._highlighted_option.nextSibling.style.display == "none"){
                            break;
                        }
                        this._highlightNextOption();
                    }
                    // going backwards
                    var newscroll=this.domNode.scrollTop;
                    scrollamount+=(newscroll-oldscroll)*(up ? -1:1);
                    oldscroll=newscroll;
                }
            },

            pageUp: function(){ this._page(true); },

            pageDown: function(){ this._page(false); },

            getHighlightedOption: function(){
                //	summary:
                //		Returns the highlighted option.
                var ho = this._highlighted_option;
                return (ho && ho.parentNode) ? ho : null;
            },

            handleKey: function(evt){
                switch(evt.keyCode){
                    case dojo.keys.DOWN_ARROW:
                        this._highlightNextOption();
                        break;
                    case dojo.keys.PAGE_DOWN:
                        this.pageDown();
                        break;
                    case dojo.keys.UP_ARROW:
                        this._highlightPrevOption();
                        break;
                    case dojo.keys.PAGE_UP:
                        this.pageUp();
                        break;
                }
            }
        }
    );

    dojo.declare(
        "clw.JD.form.AutoSuggestTextBox",
        [dijit.form.TextBox, clw.JD.form.AutoSuggestTextBoxMixin],
        {
            //	summary:
            //		Auto-completing text box, and base class for dijit.form.FilteringSelect.
            //
            //	description:
            //		The drop down box's values are populated from an class called
            //		a data provider, which returns a list of values based on the characters
            //		that the user has typed into the input box.
            //
            //		Some of the options to the ComboBox are actually arguments to the data
            //		provider.
            //
            //		You can assume that all the form widgets (and thus anything that mixes
            //		in dijit.formComboBoxMixin) will inherit from dijit.form._FormWidget and thus the `this`
            //		reference will also "be a" _FormWidget.

            postMixInProperties: function(){
                // this.inherited(arguments); // ??
                clw.JD.form.AutoSuggestTextBoxMixin.prototype.postMixInProperties.apply(this, arguments);
                dijit.form.TextBox.prototype.postMixInProperties.apply(this, arguments);
            },

            postCreate: function(){
                clw.JD.form.AutoSuggestTextBoxMixin.prototype._postCreate.apply(this, arguments);
                dijit.form.TextBox.prototype.postCreate.apply(this, arguments);
            },
            setAttribute: function(/*String*/ attr, /*anything*/ value){
                dijit.form.TextBox.prototype.setAttribute.apply(this, arguments);
                clw.JD.form.AutoSuggestTextBoxMixin.prototype._setAttribute.apply(this, arguments);
            }

        }
    );

        dojo.declare("clw.JD.form.TableModel", null, {});

        dojo.declare("clw.JD.form.ListModel", null, {} );

}
