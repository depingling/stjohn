if (!dojo._hasResource["clw.CLW.PopupFacade"]) { //_hasResource checks added by build. Do not use _hasResource directly in your code.
    dojo._hasResource["clw.CLW.PopupFacade"] = true;
    dojo.provide("clw.CLW.PopupFacade");

    dojo.require("dijit._Widget");
    dojo.require("dijit.layout.ContentPane");

    dojo.require("clw.CLW.FascadeEvent");
    dojo.require("clw.CLW.FacadeQueueManager");
    dojo.require("clw.CLW.PopupFacadeItem");

    dojo.declare("clw.CLW.PopupFacade", [dijit._Widget], {

        nodeId: "",
        items:null,
        provider:null,
        block:false,
        descriptor:false,

        postMixInProperties: function() {
            this.nodeId = this.descriptor.id;
            this.block = this.descriptor.block;
            this.node = dojo.byId(this.nodeId);
            this._bindings = [];
            this.items = [];
            this.cursor = [];
        },

        postCreate: function() {
            this.deploy(this.descriptor);
            this.provider = new clw.CLW.FacadeQueueManager();
            this.provider.init();

        },

        addItem: function(_obj) {
            this.items.push(_obj);
        },

        open: function(e) {
            this._open = true;
        },

        close: function(e) {

            var i;

            for (i = 0; i < this.items.length; i++) {
                if (this.items[i].isOpen()) {
                    return;
                }
            }

            for (i = 0; i < this.items.length; i++) {
                if (this.items[i].isPendingClosing()) {
                    this._open = false;
                    this.closeItem(this.items[i])
                }
            }
        },

        closeItem: function(_obj) {
            _obj.closeWnd();
        },

        registerEvent:function(/*FacadeEvent*/ pEvent) {
            if (!this.block) {
                this.provider.registerEvent(pEvent);
            }
        },


        deploy:function(descriptor) {
            if (descriptor && !descriptor.unavailable) {

                if (descriptor.childes && descriptor.childes.length > 0) {
                    for (var i = 0; i < descriptor.childes.length; i++) {
                        if (descriptor.childes[i] && !descriptor.childes[i].unavailable) {
                            var facadeItem = this.parse(descriptor.childes[i], null, this, 0);
                            this.addItem(facadeItem);
                        }
                    }
                }
            }
        },

        parse:function(descriptor, _parent, _facade, _level) {

            ++_level;

            var facadeItem = new clw.CLW.PopupFacadeItem({nodeId:descriptor.id,
                open:descriptor.open,
                duration:descriptor.duration,
                parent:_parent,
                facade:_facade,
                block: descriptor.block,
                level:_level,
                hasChilds:(descriptor.childes && descriptor.childes.length > 0)});

            if (descriptor.childes && descriptor.childes.length > 0) {
                for (var i = 0; i < descriptor.childes.length; i++) {
                    if (descriptor.childes[i] && !descriptor.childes[i].unavailable) {
                        var facadeChild = this.parse(descriptor.childes[i], facadeItem, _facade, _level);
                        facadeItem.addItemChild(facadeChild)
                    }
                }
            }

            return facadeItem;

        }

    });
}


