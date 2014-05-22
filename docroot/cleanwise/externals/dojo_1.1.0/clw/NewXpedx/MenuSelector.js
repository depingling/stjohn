if (!dojo._hasResource["clw.NewXpedx.MenuSelector"]) { //_hasResource checks added by build. Do not use _hasResource directly in your code.
    dojo._hasResource["clw.NewXpedx.MenuSelector"] = true;

    dojo.provide("clw.NewXpedx.MenuSelector");
    dojo.require("dijit._Widget");

    dojo.declare("clw.NewXpedx.MenuSelector", [dijit._Widget],
    {
        targetTabStyle:"",
        target:undefined,
        postCreate: function() {
            this.inherited(arguments);

            var targetNode;
            if(this.target){
                targetNode = dojo.byId(this.target);
            }    else{
                targetNode = this.domNode;
            }

            dojo.connect(targetNode, "onmouseover", this, "_select");
            dojo.connect(targetNode, "onmouseout", this, "_unselect");
        },

        _unselect: function(event) {
            this.unselect(event.target);
        },

        unselect: function(target) {

            if (target) {
                dojo.removeClass(target, this.targetTabStyle + "Unhover");
                dojo.addClass(target, this.targetTabStyle + "Hover");
            }
        },

        _select: function(event) {
            this.select(event.target);
        },

        select:function(target) {
            var currentStack = dijit.popup.stack();
            if (currentStack) {
                while (currentStack.length > 0) {
                    var top = currentStack[currentStack.length - 1],
                            wrapper = top.wrapper,
                            iframe = top.iframe,
                            widget = top.widget,
                            onClose = top.onClose;
                    if (widget.un_hover_timer) {
                        this.stopTimer(widget.un_hover_timer)
                    }
                    dijit.popup.close(widget);
                    currentStack = dijit.popup.stack();
                }
            }

            if (target) {
                dojo.removeClass(target, this.targetTabStyle + "Hover");
                dojo.addClass(target, this.targetTabStyle + "Unhover");
            }
        },

        stopTimer: function(timer) {
            if (timer) {
                clearTimeout(timer);
                timer = null;
            }
        }
    });


}