if (!dojo._hasResource["clw.CLW.PopupFacadeItem"]) { //_hasResource checks added by build. Do not use _hasResource directly in your code.
    dojo._hasResource["clw.CLW.PopupFacadeItem"] = true;
    dojo.provide("clw.CLW.PopupFacadeItem");

    dojo.require("dojo.fx");

    dojo.require("dijit._Templated");
    dojo.require("dijit.layout.ContentPane");

    dojo.require("clw.CLW.FascadeEvent");
    dojo.require("clw.CLW.FacadeQueueManager");

    dojo.declare(
            "clw.CLW.PopupFacadeItem",
            [dijit.layout.ContentPane, dijit._Templated],
    {

        title: "",
        nodeId: "",

        open: true,

        duration: 250,
        facade: null,
        templateNode: null,
        parent: null,
        childs: [],
        pendingClosing: true,
        block: false,
        level: 0,
        hasChilds:false,

        baseClass: "clwPopupFacade",

        templateString:null,

        constructor: function() {
            this._bindings = [];
            this.childs = [];
        },

        postMixInProperties: function() {
            this.templateNode = dojo.byId(this.nodeId);
            //  this.conte
            if (!dojo.isIE) {
                this.rootNode = this.templateNode.childNodes.item(0);
                this.titleBarNode = this.rootNode.childNodes.item(0);
                this.focusNode = this.rootNode.childNodes.item(0);
                this.arrowNode = this.titleBarNode.childNodes.item(1);
                this.arrowNodeInner = this.arrowNode.childNodes.item(1);
                this.titleNode = this.titleBarNode.childNodes.item(3);
                this.hideNode = this.rootNode.childNodes.item(1);
                this.wipeNode = this.hideNode.childNodes.item(1);
                this.containerNode = this.wipeNode.childNodes.item(0);
            } else {
                this.rootNode = this.templateNode.childNodes[0];
                this.titleBarNode = this.rootNode.childNodes[0];
                this.focusNode = this.rootNode.childNodes[0];
                this.arrowNode = this.titleBarNode.childNodes[0];
                this.arrowNodeInner = this.arrowNode.childNodes[0];
                this.hideNode = this.rootNode.childNodes[1];
                
                if (isIE () >= 10) {
                	this.titleNode = this.titleBarNode.childNodes[3];
                	this.wipeNode = this.hideNode.childNodes[1];
                } else {
	            	this.titleNode = this.titleBarNode.childNodes[1];
	            	this.wipeNode = this.hideNode.childNodes[0];
                }
                
                this.containerNode = this.wipeNode.childNodes[0];
            }
            this.title = ""+this.titleNode.innerHTML;
            this.content = ""+this.containerNode.innerHTML;

            if (this.title != null && (dojo.trim(this.title).length == 0)) {
                this.title = null;
            }

            if (this.content != null && (dojo.trim(this.content).length == 0)) {
                this.content = null;
            }

        },

        postCreate: function() {

            this.bindDomNode(this.templateNode);
            this.bindDomNode(this.wipeNode);
            this.bindFocus(this.templateNode);

            if (!this.open) {
                this.hideNode.style.display = this.wipeNode.style.display = "none";
            }

            dijit.setWaiRole(this.rootNode, "button");
            dijit.setWaiRole(this.containerNode, "region");

            this._setCss();

            dojo.setSelectable(this.titleNode, false);
            this.inherited(arguments);
            dijit.setWaiState(this.containerNode, "labelledby", this.titleNode.id);
            dijit.setWaiState(this.focusNode, "haspopup", "true");

            // setup open/close animations
            var hideNode = this.hideNode, wipeNode = this.wipeNode;
            this._wipeIn = dojo.fx.wipeIn({
                node: this.wipeNode,
                duration: this.duration,
                beforeBegin: function() {
                    hideNode.style.display = "";
                }
            });
            this._wipeOut = dojo.fx.wipeOut({
                node: this.wipeNode,
                duration: this.duration,
                onEnd: function() {
                    hideNode.style.display = "none";
                }
            });

        },

        bindDomNode: function(node) {

            this._bindings.push([
                    dojo.connect(node, "onmouseover", this, "toggleOn"),
                    dojo.connect(node, "onmouseout", this, "toggleOff")]);

        },      bindFocus: function(node) {

        this._bindings.push([
                dojo.connect(node, "onfocus", this, "_handleFocus"),
                dojo.connect(node, "onblur", this, "_handleFocus")]);

    },

        setContent: function(content) {
            // summary:
            // 		Typically called when an href is loaded.  Our job is to make the animation smooth
            if (!this.open || this._wipeOut.status() == "playing") {
                // we are currently *closing* the pane (or the pane is closed), so just let that continue
                this.inherited(arguments);
            } else {
                if (this._wipeIn.status() == "playing") {
                    this._wipeIn.stop();
                }

                // freeze container at current height so that adding new content doesn't make it jump
                dojo.marginBox(this.wipeNode, { h: dojo.marginBox(this.wipeNode).h });

                // add the new content (erasing the old content, if any)
                this.inherited(arguments);

                // call _wipeIn.play() to animate from current height to new height
                this._wipeIn.play();
            }
        },

        toggleOn: function(e) {
            var event = new clw.CLW.FascadeEvent();
            event.setSource(this);
            event.setEventData(e);
            event.setAction("openWnd");
            event.setTimeout(200);
            this.pendingClosing = false;
            this.registerEvent(event);
        },

        toggleOff: function(e) {

            var event = new clw.CLW.FascadeEvent();
            event.setSource(this.facade);
            event.setEventData(e);
            event.setAction("close");
            event.setTimeout(4000);
            this.pendingClosing = true;
            this.registerEvent(event);

        },

        registerEvent: function(event) {
            if (!this.block) {
                this.facade.registerEvent(event);
            }
        },

        openWnd: function() {
            // summary: switches between opened and closed state
            if (!this.open && this.content!=null && !this.pendingClosing) {


                dojo.forEach([this._wipeIn, this._wipeOut], function(animation) {
                    if (animation.status() == "playing") {
                        animation.stop();
                    }
                });

                this["_wipeIn"].play();
                this.open = true;

                // load content (if this is the first time we are opening the TitlePane
                // and content is specified as an href, or we have setHref when hidden)
                this._loadCheck();

                this._setCss();

            }


        },


        closeWnd: function() {
            // summary: switches between opened and closed state
            if (this.open) {

                dojo.forEach(this.childs, this._closeChildWnd, this);
                if (!this.block) {
                    dojo.forEach([this._wipeIn, this._wipeOut], function(animation) {
                        if (animation.status() == "playing") {
                            animation.stop();
                        }
                    });

                    this["_wipeOut"].play();
                    this.open = false;
                    this._setCss()
                }
            }

        },

        isOpen: function() {
            if (!this.pendingClosing) {
                return true;
            } else {
                for (var i = 0; i < this.childs.length; i++) {
                    if (this.childs[i].isOpen()) {
                        return true;
                    }
                }
            }
            return false;
        },

        isPendingClosing: function() {
            if (this.pendingClosing) {
                return true;
            } else {
                for (var i = 0; i < this.childs.length; i++) {
                    if (this.childs[i].isPendingClosing()) {
                        return true;
                    }
                }
            }
            return false;
        },

        close: function() {
            // load content (if this is the first time we are opening the TitlePane
            // and content is specified as an href, or we have setHref when hidden)
            this.closeWnd();
        },


        _closeChildWnd: function(child) {
            child.close();
        },

        _setCss: function() {
            // summary: set the open/close css state for the TitlePane
            if(this.hasChilds) {
                var classes = ["dijitClosed", "dijitOpen"];
                var boolIndex = this.open;
                var node = this.titleBarNode || this.focusNode
                dojo.removeClass(node, classes[!boolIndex + 0]);
                node.className += " " + classes[boolIndex + 0];
            }

        },

        _onTitleKey: function(/*Event*/ e) {
            // summary: callback when user hits a key
            if (e.keyCode == dojo.keys.ENTER || e.charCode == dojo.keys.SPACE) {
                //this.toggle();
            } else if (e.keyCode == dojo.keys.DOWN_ARROW && this.open) {
                this.containerNode.focus();
                e.preventDefault();
            }
        },

        _handleFocus: function(/*Event*/ e) {
            // summary: handle blur and focus for this widget

            // add/removeClass is safe to call without hasClass in this case
            dojo[(e.type == "focus" ? "addClass" : "removeClass")](this.focusNode, this.baseClass + "Focused");
        },

        setTitle: function(/*String*/ title) {
            // summary: sets the text of the title
            this.titleNode.innerHTML = title;
        },

        addItemChild: function(/*String*/ child) {
            // summary: sets the text of the title
            this.childs.push(child);
        }

    });

    function isIE() {
		var myNav = navigator.userAgent.toLowerCase();
		return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
	}


}
