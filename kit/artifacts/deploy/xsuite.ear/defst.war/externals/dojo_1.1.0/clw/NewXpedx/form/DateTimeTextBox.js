if(!dojo._hasResource["clw.NewXpedx.form.DateTimeTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["clw.NewXpedx.form.DateTimeTextBox"] = true;
 dojo.provide("clw.NewXpedx.form.DateTimeTextBox");

    dojo.require("dojo.date");
    dojo.require("dojo.date.locale");
    dojo.require("dojo.date.stamp");
    dojo.require("dijit.form.TextBox");


    dojo.declare("clw.NewXpedx.form.DateTimeTextBox",
            dijit._Widget, {

        popupClass: "",
        _selector: "",
        appPattern:"",
        baseClass:"",
        targetNodeIds:[],
        constraints: {},
        regExpGen: dojo.date.locale.regexp,
        compare: dojo.date.compare,
        name:"",
        textbox:null,
        format: function(/*Date*/ value, /*dojo.date.locale.__FormatOptions*/ constraints) {
            if (!value) {
                return '';
            }
            return dojo.date.locale.format(value, constraints);
        },

        parse: function(/*String*/ value, /*dojo.date.locale.__FormatOptions*/ constraints) {
            return dojo.date.locale.parse(value, constraints) || undefined;
        },

        serialize: dojo.date.stamp.toISOString,

        value:new Date(""),


        postMixInProperties: function() {
            this.inherited(arguments);

            var constraints = this.constraints;
            constraints.selector = this._selector;
            constraints.fullYear = true;
            // see #5465 - always format with 4-digit years
            var fromISO = dojo.date.stamp.fromISOString;

            if (typeof constraints.min == "string") {
                constraints.min = fromISO(constraints.min);
            }

            if (typeof constraints.max == "string") {
                constraints.max = fromISO(constraints.max);
            }

            if (typeof this.appPattern == "string" && this.appPattern.length > 0) {
                constraints.datePattern = this.appPattern;
            }

        },

        constructor: function() {
            this._bindings = [];
        },

        setValue: function(/*Date*/ value, /*Boolean?*/ priorityChange, /*String?*/ formattedValue) {
            // summary:
            //	Sets the date on this textbox.  Note that `value` must be a Javascript Date object.
            this.inherited(arguments);
            if (this._picker) {
                // #3948: fix blank date on popup only
                if (!value) {
                    value = new Date();
                }
                this._picker.setValue(value);
            }
        },


        postCreate: function() {

            this.textbox = dojo.byId(this.id);

            if (this.targetNodeIds &&
                this.targetNodeIds.length &&
                this.targetNodeIds.length >= 1) {
                if (this.targetNodeIds.length > 1) {
                    console.warn("[clw.NewXpedx.form.DateTimeTextBox.postCreate] => Multiple targets found.Targets: " + this.targetNodeIds)
                    console.warn("[clw.NewXpedx.form.DateTimeTextBox.postCreate] => Targets will be corrected to : " + this.targetNodeIds[0])
                    this.targetNodeIds = [this.targetNodeIds[0]]
                }
                dojo.forEach(this.targetNodeIds, this.bindDomNode, this);
            }

            if (this.appValue) {

                var dateObj = this.parse(this.appValue,this.constraints);

                if (dateObj) {
                    this.value = dateObj;
                    this.setDisplayedValue(this.value);
                } else {
                    this.value = undefined;
                    this.setDisplayedValueStr(this.appValue);
                }

            } else {
                this.value = undefined;
                this.setDisplayedValue(this.value);
            }

            this.inherited(arguments);

        } ,

        bindDomNode: function(/*String|DomNode*/ node) {


            node = dojo.byId(node);

            var win = dijit.getDocumentWindow(node.ownerDocument);
            if (node.tagName.toLowerCase() == "iframe") {
                win = this._iframeContentWindow(node);
                node = dojo.withGlobal(win, dojo.body);
            }

            // to capture these events at the top level,
            // attach to document, not body
            var cn = (node == dojo.body() ? dojo.doc : node);

            node[this.id] = this._bindings.push([dojo.connect(cn, "onclick", this, "_open")]);
        },

        unBindDomNode: function(/*String|DomNode*/ nodeName) {
            // summary: detach menu from given node
            var node = dojo.byId(nodeName);
            if (node) {
                var bid = node[this.id] - 1, b = this._bindings[bid];
                dojo.forEach(b, dojo.disconnect);
                delete this._bindings[bid];
            }
        },

        validator: function(/*anything*/value, /*dijit.form.ValidationTextBox.__Constraints*/constraints) {
            return  this.parse(value, constraints) !== undefined;
            // Boolean
        },

        _isValid: function() {
            // summary: Need to over-ride with your own validation code in subclasses
            return this.validator(this.textbox.value, this.constraints);
        },

        _isEmpty: function(value) {
            // summary: Checks for whitespace
            return /^\s*$/.test(value);
            // Boolean
        },
        _open: function() {
            // summary:
            //	opens the TimePicker, and sets the onValueSelected value

            if (this.disabled || this.readOnly || !this.popupClass) {
                return;
            }

            var textBox = this;

            if (!this._picker) {
                var PopupProto = dojo.getObject(this.popupClass, false);
                this._picker = new PopupProto({
                    onValueSelected: function(value) {

                        textBox.textbox.focus();
                        // focus the textbox before the popup closes to avoid reopening the popup
                        setTimeout(dojo.hitch(textBox, "_close"), 1);
                        // allow focus time to take

                        // this will cause InlineEditBox and other handlers to do stuff so make sure it's last
                        textBox.setDisplayedValue(value);
                        textBox.close();
                    },
                    lang: textBox.lang,
                    constraints: textBox.constraints,
                    isDisabledDate: function(/*Date*/ date) {
                        // summary:
                        // 	disables dates outside of the min/max of the _DateTimeTextBox
                        var compare = dojo.date.compare;
                        var constraints = textBox.constraints;
                        return constraints && (constraints.min && (compare(constraints.min, date, "date") > 0) ||
                                               (constraints.max && compare(constraints.max, date, "date") < 0));
                    }
                });
                this._picker.setValue(this.getValue() || new Date());
                this._picker.setSource(this)
            }

            if (!this._opened) {
                dijit.popup.open({
                    parent: this,
                    popup: this._picker,
                    around: dojo.byId(this.targetNodeIds[0]) || this.domNode,
                    onCancel: dojo.hitch(this, this._close),
                    onClose: function() {
                        textBox._opened = false;
                    }
                });
                this._opened = true;
                this.textbox.focus();

            }
        },

        getValue: function() {
            return this.parse(this.getDisplayedValue(), this.constraints);
        },

        _close: function() {
            if (this._opened) {
                dijit.popup.close(this._picker);
                this._opened = false;
            }
        },


        close:function() {
            this._close();
            if (this._picker) {
                this._picker.destroy();
                delete this._picker;
            }
        },

        getDisplayedValue:function() {
            return this.filter(this.textbox.value);
        },

        setDisplayedValue:function(/*String*/ value) {
            this.textbox.value=this.filter(this.format(value,this.constraints));
        },

        setDisplayedValueStr:function(/*String*/ value) {
            this.textbox.value=this.filter(value);
        },

        destroy: function() {
            if (this._picker) {
                this._picker.destroy();
                delete this._picker;
            }
            this.inherited(arguments);
        },

        uninitialize: function() {
            dojo.forEach(this.targetNodeIds, this.unBindDomNode, this);
            this.inherited(arguments);
        }
        ,

        _onKeyPress: function(/*Event*/e) {
            if (clw.NewXpedx.form.DateTimeTextBox.superclass._onKeyPress.apply(this, arguments)) {
                if (this._opened && e.keyCode == dojo.keys.ESCAPE && !e.shiftKey && !e.ctrlKey && !e.altKey) {
                    this._close();
                    dojo.stopEvent(e);
                }
            }
        } ,

        filter: function(val) {
            //	summary:
            //		Apply specified filters to textbox value

            if (val === null || val === undefined) {
                return "";
            }
            else if (typeof val != "string") {
                return val;
            }
            if (this.trim) {
                val = dojo.trim(val);
            }
            if (this.uppercase) {
                val = val.toUpperCase();
            }
            if (this.lowercase) {
                val = val.toLowerCase();
            }
            if (this.propercase) {
                val = val.replace(/[^\s]+/g, function(word) {
                    return word.substring(0, 1).toUpperCase() + word.substring(1);
                });
            }
            return val;
        }
    });


}
