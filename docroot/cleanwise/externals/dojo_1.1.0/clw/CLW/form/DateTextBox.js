if(!dojo._hasResource["clw.CLW.form.DateTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["clw.CLW.form.DateTextBox"] = true;
 dojo.provide("clw.CLW.form.DateTextBox");

    dojo.require("clw.CLW.Calendar");
    dojo.require("clw.CLW.form.DateTimeTextBox");

    dojo.declare("clw.CLW.form.DateTextBox",
            clw.CLW.form.DateTimeTextBox,{
        popupClass: "clw.CLW.Calendar",
        _selector: "date",
        constraints: {},
        appPattern:"",
        appValue:""    
    });


}
