if(!dojo._hasResource["clw.NewXpedx.form.DateTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["clw.NewXpedx.form.DateTextBox"] = true;
 dojo.provide("clw.NewXpedx.form.DateTextBox");

    dojo.require("clw.NewXpedx.Calendar");
    dojo.require("clw.NewXpedx.form.DateTimeTextBox");

    dojo.declare("clw.NewXpedx.form.DateTextBox",
            clw.NewXpedx.form.DateTimeTextBox,{
        popupClass: "clw.NewXpedx.Calendar",
        _selector: "date",
        constraints: {},
        appPattern:"",
        appValue:""    
    });


}
