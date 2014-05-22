if (!dojo._hasResource["clw.CLW.FacadeQueueItem"]) { //_hasResource checks added by build. Do not use _hasResource directly in your code.
    dojo._hasResource["clw.CLW.FacadeQueueItem"] = true;
    dojo.provide("clw.CLW.FacadeQueueItem");

    dojo.declare("clw.CLW.FacadeQueueItem", [null], {

        source:null,
        timer:null,
        action:null,

        setSource:function( pSource) {
            this.source = pSource;
        },

        setTimer:function( pTimer) {
            this.timer = pTimer;
        },

        setAction:function(pAction) {
            this.action = pAction;
        },

        getSource:function() {
            return this.source;
        },

        getTimer:function() {
            return  this.timer;
        },

        getAction:function() {
            return this.action;
        }
    });

}
