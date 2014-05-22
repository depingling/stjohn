if (!dojo._hasResource["clw.CLW.FascadeEvent"]) { //_hasResource checks added by build. Do not use _hasResource directly in your code.
    dojo._hasResource["clw.CLW.FascadeEvent"] = true;
    dojo.provide("clw.CLW.FascadeEvent");

    dojo.declare("clw.CLW.FascadeEvent", [null], {

        source:null,
        eventData:null,
        action:null,
        timeout:200,

        setSource:function(/*FascadeItem*/ pSource) {
            this.source = pSource;
        },

        setEventData:function(/*Event*/ pEventData) {
            this.eventData = pEventData;
        },

        setAction:function(/*method*/ pAction) {
            this.action = pAction;
        },

        getSource:function() {
            return this.source;
        },

        getEventData:function() {
            return  this.eventData;
        },

        getAction:function() {
            return this.action;
        },

         setTimeout:function(pTimeout) {
          this.timeout = pTimeout;
        },

        getTimeout:function() {
            return this.timeout;
        }
    });

}
