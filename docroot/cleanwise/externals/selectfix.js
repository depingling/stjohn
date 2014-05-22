var IEHTMLSelectElement = (function() {

    var p_isIE = !!(document.uniqueID && document.expando);
    var p_isIE6 = !!(p_isIE && !window.XMLHttpRequest);
    var p_n = 0 * Date.parse(new Date);

    var oApi = {



        _onResize: function() {

            var e = window.event;
            var dHTMLSel = e.srcElement;
            IEHTMLSelectElement.fix(dHTMLSel);
        },


        VBonActiveXChangeForJS: function(dActiveXSel) {

            var sId = dActiveXSel.__CLONE_PARENT_ID;
            if ( ! sId)
                return;
            var dHTMLSel = document.getElementById(sId);
            dHTMLSel.selectedIndex = dActiveXSel.listindex;
            dHTMLSel.fireEvent('onChange');

        },

        _onPropertyChange: function() {

            var e = window.event;
            var sN = e.propertyName;
            switch(sN) {

                case 'value': ;
                case 'selectedIndex': ;

                var dHTMLSel = e.srcElement;
                var dActiveXSel = document.getElementById(dHTMLSel.__CLONE_CHILD_ID);
                if (dHTMLSel.selectedIndex < 0)
                    dHTMLSel.selectedIndex = 0;

                if (dHTMLSel.selectedIndex != dActiveXSel.listindex) {
                    dActiveXSel.listindex = dHTMLSel.selectedIndex;
                };

                break;
                default: ;
                if (sN.indexOf('style') === 0) {
                    dActiveXSel.style.display = dActiveXSel.currentStyle.display;
                } else if (sN === 'focus') {
                    } else {
                    };
                return;


            };
        },




        find: function(x) {
            var dEl;
            if (typeof(x) == 'string')
                return document.getElementById(x);
            if ( ! x || !x.tagName || x.tagName != 'SELECT')
                return null;
            return x;
        },

        unfix: function(x) {
            if ( ! p_isIE6)
                return false;
            var dSel;
            if (x.length && x.namedItem && !x.tagName) {

                //nodeList
                var a = [];
                for (var i = 0, dEl; dEl = x[i]; i ++ ) {
                    try {
                        a.push(this.unfix(dEl));
                    }
                    catch(err) {
                        alert(dEl.tagName + err.message);
                    }
                };
                return a;

            } else {
                dSel = this.find(x);
            };

            if ( ! dSel)
                return null;
            if (dSel.__CLONE_CHILD_ID) {

                var dActiveXSel = document.getElementById(dSel.__CLONE_CHILD_ID);
                dActiveXSel.removeNode(true);
                dSel.detachEvent('onpropertychange', this._onPropertyChange);
                dSel.detachEvent('onresize', this._onResize);
                dSel.style.cssText = dSel.__defaultCssText;
                dSel.__CLONE_CHILD_ID = undefined;
                dSel.__defaultCssText = undefined;

            };

        },


        fix: function(x) {


            if ( ! p_isIE6)
                return false;
            var dSel;

            if (x.length && x.namedItem && !x.tagName) {

                //nodeList
                var a = [];
                for (var i = 0, dEl; dEl = x[i]; i ++ ) {
                    try {
                        a.push(this.fix(dEl));
                    }
                    catch(err) {
                        alert(dEl.tagName + err.message);
                    }
                };
                return a;

            } else {
                dSel = this.find(x);
            };

            if ( ! dSel)
                return null;





            if (dSel.__CLONE_CHILD_ID) {
                this.unfix(dSel);
            };


            var sId = (dSel.id = dSel.id || ('IEHTMLSelectElement' + (p_n ++ )));
            var sId2 = 'IEHTMLSelectElement' + (p_n ++ );

            var dStyle = dSel.currentStyle;
            var sW = dSel.offsetWidth + 'px';
            //dStyle['width'];
            var sH = dSel.offsetHeight + 'px';
            //dStyle['height'];
            //sW = (sW=='auto')?dSel.offsetWidth  + 'px' : sW;
            //sH = (sH=='auto')?dSel.offsetHeight + 'px' : sH;

            if (typeof(dSel.__defaultCssText) != 'string') {
                dSel.__defaultCssText = dSel.style.cssText;
                dSel.hideFocus = true;
                dSel.tabIndex = -1;
            };






            var s = ['<object classid="clsid:8BD21D30-EC42-11CE-9E0D-00AA006002F3"  id="', sId2, '"  style="width:', sW, ';height:', sH, ';margin:0;vertical-align:bottom;">', '<param name="VariousPropertyBits" value="746604571" />', '<param name="BackColor" value="2147483653" />', '<param name="ForeColor" value="2147483656" />', '<param name="MaxLength" value="0" />', '<param name="BorderStyle" value="0" />', '<param name="ScrollBars" value="0" />', '<param name="DisplayStyle" value="7" />', '<param name="MousePointer" value="0" />', '<param name="Size" value="1588;635" />', '<param name="PasswordChar" value="0" />', '<param name="ListWidth" value="0" />', '<param name="BoundColumn" value="0" />', '<param name="TextColumn" value="65535" />', '<param name="ColumnCount" value="1" />', '<param name="ListRows" value="8" />', '<param name="ColumnInfo" value="0" />', '<param name="MatchEntry" value="1" />', '<param name="ListStyle" value="0" />', '<param name="ShowDropButtonWhen" value="2" />', '<param name="ShowListWhen" value="0" />', '<param name="DropButtonStyle" value="1" />', '<param name="MultiSelect" value="0" />', '<param name="Value" value="', dSel.options[dSel.selectedIndex].innerText, '" />',
            '<param name="Caption" value="" />', '<param name="PicturePosition" value="458753" />', '<param name="BorderColor" value="2147483654" />', '<param name="SpecialEffect" value="2" />', '<param name="Accelerator" value="0" />', '<param name="GroupName" value="" />', '<param name="FontName" value="Tahoma" />', '<param name="FontEffects" value="1073741824" />', '<param name="FontHeight" value="', Math.round(dSel.offsetHeight * 8), '" />', '<param name="FontOffset" value="0" />', '<param name="FontCharSet" value="0" />', '<param name="FontPitchAndFamily" value="0" />', '<param name="ParagraphAlign" value="0" />', '<param name="FontWeight" value="0" />',
            '</object>'
            ];


            dSel.insertAdjacentHTML('BeforeBegin', s.join(''));
            var dSel2 = document.getElementById(sId2);

            var nCharLength = 0;

            for (var i = 0, dOpt; dOpt = dSel.options[i]; i ++ ) {
                var sT = dOpt.innerText;
                var sVB = 'document.getElementById("' + sId2 + '").additem("' + sT + '")';
                nCharLength = Math.max(nCharLength, sT.length);
                try{
                    window.execScript(sVB, "VBSCRIPT");
                }catch(err){
                    //Object is not supported
                    dSel2.removeNode(true);
                    IEHTMLSelectElement.fix = function(){return false;}
                    IEHTMLSelectElement.unfix = function(){return false;}
                    return;
                };
            };

            with(dSel.style) {
                display = 'block';
                //'none';
                position = 'absolute';
                top = 0;
                left = '-5000px';
            };


            dSel.__CLONE_CHILD_ID = sId2;
            dSel2.__CLONE_PARENT_ID = sId;
            dSel2.ListWidth = nCharLength * dSel.offsetHeight / 5 + 20;

            dSel.attachEvent('onpropertychange', this._onPropertyChange);
            dSel.attachEvent('onresize', this._onResize);

            dSel.focus = function() {
                document.getElementById(this.__CLONE_CHILD_ID).focus();
            };



            var sVB = [
            'Private Sub ' + sId2 + '_Change()', 'window.IEHTMLSelectElement.VBonActiveXChangeForJS(document.getElementById("' + sId2 + '"))', 'End Sub'
            ].join('\n');


            window.execScript(sVB, "VBSCRIPT");
            return dSel2;
        }


    };

    return oApi;

})();
