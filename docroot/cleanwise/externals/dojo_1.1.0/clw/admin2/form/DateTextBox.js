if(!dojo._hasResource["clw.admin2.form.DateTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["clw.admin2.form.DateTextBox"] = true;
 dojo.provide("clw.admin2.form.DateTextBox");

    dojo.require("clw.admin2.Calendar");
    dojo.require("clw.admin2.form.DateTimeTextBox");

    dojo.declare("clw.admin2.form.DateTextBox",
            clw.admin2.form.DateTimeTextBox,{
        popupClass: "clw.admin2.Calendar",
        _selector: "date",
        constraints: {},
        appPattern:"",
        appValue:""
    });


}
