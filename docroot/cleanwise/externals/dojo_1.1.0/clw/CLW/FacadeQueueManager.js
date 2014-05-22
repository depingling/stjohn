if (!dojo._hasResource["clw.CLW.FacadeQueueManager"]) { //_hasResource checks added by build. Do not use _hasResource directly in your code.
    dojo._hasResource["clw.CLW.FacadeQueueManager"] = true;
    dojo.provide("clw.CLW.FacadeQueueManager");

    dojo.require("clw.CLW.FacadeQueueItem");

    dojo.declare("clw.CLW.FacadeQueueManager", [null], {

        queue:null,
        queueToProcess:null,

        init:function() {
            this.queue = new Array();
            this.queueToProcess = new Array();
        },

        registerEvent:function(/*FacadeEvent*/ pEvent) {
            this.queue.push(pEvent);
            this.processQueue();
        },

        processQueue:function() {
            for (var qc = 0; this.queue.length > qc; qc++) {
                var event = this.queue[qc];
                this.queue.splice(qc, 1);
                qc--;
                this.processEvent(event);
            }
        },

        processEvent:function(/*FacadeEvent*/ event) {
            

            this.removeSourceEvents(event);

            if(!this.queueToProcessContains(event)) {

                var timer = setTimeout(dojo.hitch(this, "fireEvent", event), event.getTimeout());

                var queueToProcessObj = new clw.CLW.FacadeQueueItem();
                queueToProcessObj.setSource(event.getSource());
                queueToProcessObj.setAction(event.getAction());
                queueToProcessObj.setTimer(timer);

                this.queueToProcess.push(queueToProcessObj);

            }

        },

        fireEvent:function(/*Array[0][1][2]*/ pEvent){
            this.removeSourceActionEvents(pEvent);
            dojo.hitch(pEvent.getSource(),pEvent.getAction())();
        },

        queueToProcessContains:function(/*FacadeEvent*/ pEvent) {
            for (var qc = 0; this.queueToProcess.length > qc; qc++) {
                var queueObj = this.queueToProcess[qc];
                if(queueObj.getSource() == pEvent.getSource()) {
                    if(queueObj.getAction() == pEvent.getAction()) {
                        return true;
                    }
                }
            }
            return false
        },

        removeSourceEvents:function(/*FacadeEvent*/ pEvent) {
            for (var qc = 0; this.queueToProcess.length > qc; qc++) {
                var queueObj = this.queueToProcess[qc];
                if (queueObj.getSource() == pEvent.getSource()) {
                    //if (queueObj.getAction() != pEvent.getAction()) {
                        clearTimeout(queueObj.getTimer());
                        this.queueToProcess.splice(qc, 1);
                        qc--;
                  //  }
                }
            }
        },

        removeSourceActionEvents:function(pQObj) {
            for (var qc = 0; this.queueToProcess.length > qc; qc++) {
                var queueObj = this.queueToProcess[qc];
                if (queueObj.getSource() == pQObj.getSource()) {
                    if (queueObj.getAction() == pQObj.getAction()) {
                        if (queueObj.getTimer()) {
                            clearTimeout(queueObj.getTimer());
                        }
                        this.queueToProcess.splice(qc, 1);
                        qc--;
                    }
                }
            }
        }

    });

}
