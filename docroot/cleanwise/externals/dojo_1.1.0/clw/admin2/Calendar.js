if(!dojo._hasResource["clw.admin2.Calendar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["clw.admin2.Calendar"] = true;
 dojo.provide("clw.admin2.Calendar");

    dojo.require("dijit._Calendar");

    dojo.requireLocalization("dijit", "common", null, "ROOT,cs,de,es,gr,hu,it,ja,ko,pl,pt,fr,ru,sv,zh,zh-tw");

    dojo.declare(
            "clw.admin2.Calendar",
            dijit._Calendar,
    {
        templateString:"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"dijitCalendarMainContainer\"><tr><td class=\"dijitCalendarPaddingLeft\"></td><td align=\"center\"><table cellspacing=\"0\" cellpadding=\"0\" class=\"dijitCalendarContainer\">\n\t" +
                       "<thead>\n\t\t" +
                       "<tr class=\"dijitReset dijitCalendarMonthContainer\" valign=\"top\">\n\t\t\t" +
                       "<th class='dijitReset' dojoAttachPoint=\"decrementMonth\">\n\t\t\t\t" +
                       "<div class=\"dijitInline dijitCalendarIncrementControl dijitCalendarDecrease\">" +
                       "<span dojoAttachPoint=\"decreaseArrowNode\" class=\"dijitCalendarIncrementControl dijitCalendarDecreaseInner\">-</span>" +
                       "</div>\n\t\t\t</th>\n\t\t\t" +
                       "<th class='dijitReset dijitCalendarMonthContainer' colspan=\"5\">\n\t\t\t\t" +
                       "<div dojoAttachPoint=\"monthLabelSpacer\" class=\"dijitCalendarMonthLabelSpacer\"></div>\n\t\t\t\t" +
                       "<div dojoAttachPoint=\"monthLabelNode\" class=\"dijitCalendarMonthLabel\"></div>\n\t\t\t" +
                       "</th>\n\t\t\t" +
                       "<th class='dijitReset' dojoAttachPoint=\"incrementMonth\">\n\t\t\t\t" +
                       "<div class=\"dijitInline dijitCalendarIncrementControl dijitCalendarIncrease\">" +
                       "<span dojoAttachPoint=\"increaseArrowNode\" class=\"dijitCalendarIncrementControl dijitCalendarIncreaseInner\">+</span>" +
                       "</div>\n\t\t\t" +
                       "</th>\n\t\t" +
                       "</tr>\n\t\t" +
                       "<tr>\n\t\t\t" +
                       "<th class=\"dijitReset dijitCalendarDayLabelTemplate\">" +
                       "<span class=\"dijitCalendarDayLabel\"></span>" +
                       "</th>\n\t\t" +
                       "</tr>\n\t" + "</thead>\n\t" +
                       "<tbody dojoAttachEvent=\"onclick: _onDayClick\" class=\"dijitReset dijitCalendarBodyContainer\">\n\t\t" +
                       "<tr class=\"dijitReset dijitCalendarWeekTemplate\">\n\t\t\t" +
                       "<td class=\"dijitReset dijitCalendarDateTemplate\">" +
                       "<span class=\"dijitCalendarDateLabel\"></span>" +
                       "</td>\n\t\t" +
                       "</tr>\n\t" +
                       "</tbody>\n\t" +
                       "<tfoot class=\"dijitReset dijitCalendarFootContainer\">\n\t\t<tr>\n\t\t\t<td class='dijitReset dijitCalendarFootContent' valign=\"top\" colspan=\"7\"><span class=\"dijitCalendarFooterLabel\" dojoAttachEvent=\"onclick:close\">${itemClose}</span>\n\t\t\t</td>\n\t\t</tr>\n\t</tfoot>\n</table>\t\n</td><td class=\"dijitCalendarPaddingRight\"></td></tr></table>"
        ,
        itemClose:"",
        _source:undefined,

        _populateGrid: function() {

            var month = this.displayMonth;
            month.setDate(1);
            var firstDay = month.getDay();
            var daysInMonth = dojo.date.getDaysInMonth(month);
            var daysInPreviousMonth = dojo.date.getDaysInMonth(dojo.date.add(month, "month", -1));
            var today = new Date();
            var selected = this.value;

            var dayOffset = dojo.cldr.supplemental.getFirstDayOfWeek(this.lang);
            if (dayOffset > firstDay) {
                dayOffset -= 7;
            }

            // Iterate through dates in the calendar and fill in date numbers and style info
            dojo.query(".dijitCalendarDateTemplate", this.domNode).forEach(function(template, i) {
                i += dayOffset;
                var date = new Date(month);
                var number, clazz = "dijitCalendar", adj = 0;

                if (i < firstDay) {
                    number = daysInPreviousMonth - firstDay + i + 1;
                    adj = -1;
                    clazz += "Previous";
                } else if (i >= (firstDay + daysInMonth)) {
                    number = i - firstDay - daysInMonth + 1;
                    adj = 1;
                    clazz += "Next";
                } else {
                    number = i - firstDay + 1;
                    clazz += "Current";
                }

                if (adj) {
                    date = dojo.date.add(date, "month", adj);
                }
                date.setDate(number);

                if (!dojo.date.compare(date, today, "date")) {
                    clazz = "dijitCalendarCurrentDate " + clazz;
                }

                if (!dojo.date.compare(date, selected, "date")) {
                    clazz = "dijitCalendarSelectedDate " + clazz;
                }

                if (this.isDisabledDate(date, this.lang)) {
                    clazz = "dijitCalendarDisabledDate " + clazz;
                }

                var clazz2 = this.getClassForDate(date, this.lang);
                if (clazz2) {
                    clazz += clazz2 + " " + clazz;
                }
                template.className = clazz + "Month dijitCalendarDateTemplate";
                template.dijitDateValue = date.valueOf();
                var label = dojo.query(".dijitCalendarDateLabel", template)[0];
                this._setText(label, date.getDate());
            }, this);

            var y = month.getFullYear();
            var d = new Date();
            d.setFullYear(y)

            // Fill in localized month name
            var monthNames = dojo.date.locale.getNames('months', 'wide', 'standAlone', this.lang);
            this._setText(this.monthLabelNode, monthNames[month.getMonth()] + " " + dojo.date.locale.format(d, {selector:'year', locale:this.lang}));

            // Set up repeating mouse behavior
            var _this = this;
            var typematic = function(nodeProp, dateProp, adj) {
                dijit.typematic.addMouseListener(_this[nodeProp], _this, function(count) {
                    if (count >= 0) {
                        _this._adjustDisplay(dateProp, adj);
                    }
                }, 0.8, 500);
            };
            typematic("incrementMonth", "month", 1);
            typematic("decrementMonth", "month", -1);

        },

        setSource:function(source) {
            this._source = source;
        } ,

        postMixInProperties: function(){
            this.inherited('postMixInProperties', arguments);
            this.messages = dojo.i18n.getLocalization("dijit", "common", this.lang);
            dojo.forEach(["itemClose"], function(prop){
                if(!this[prop]){ this[prop] = this.messages[prop]; }
            }, this);
        },

        close:function() {
            if (this._source) {
                if(!this._source.textbox.focus()){
                    this._source.textbox.focus();
                }
                this._source.close()
                this.destroy();
            } else{
                dojo.popup.close(this)
                this.destroy();
            }
        }
    }
);


}
