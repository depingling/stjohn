if(!dojo._hasResource["clw.Swiss2.form.DateTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["clw.Swiss2.form.DateTextBox"] = true;
 dojo.provide("clw.Swiss2.form.DateTextBox");

    dojo.require("clw.Swiss2.Calendar");
    dojo.require("clw.Swiss2.form.DateTimeTextBox");

    dojo.declare("clw.Swiss2.form.DateTextBox",
            clw.Swiss2.form.DateTimeTextBox,{
        popupClass: "clw.Swiss2.Calendar",
        _selector: "date",
        constraints: {},
        appPattern:"",
        appValue:""    
    });


}
