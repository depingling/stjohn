document.writeln(
'<style>' + 
'td.scwCellsWeekend {' + 
'    background-color:  #CCCCCC;' + 
'    color:  #000000;' + 
'}' + 
'</style>'
);
function showCalendar(id, event) {
    scwDateDisplayFormat = CalendarMetaInfo.dateFormat.toLowerCase();
    scwDateOutputFormat = CalendarMetaInfo.dateFormat.toUpperCase();
    scwWeekStart = CalendarMetaInfo.weekStart;
    scwLanguage = CalendarMetaInfo.language;
    scwSetLanguage();
    scwShow(scwID(id), event);
}
var CalendarMetaInfo = {
	weekStart: 1,
	language: '',
	dateFormat: '',
	init: function(p1, p2) {
		this.dateFormat = p2;
		this.weekStart = 1;
		switch (p1) {
			case 'ru':
			case 'ru_RU':
				this.language = 'ru';
				break;
			case 'de_DE':
				this.language = 'de';
				break;
			case 'it_IT':
				this.language = 'it';
				break;
			case 'nl_NL':
			case 'nl_BE':
				this.language = 'nl';
				break;
			case 'fr_CA':
			case 'fr_FR':
				this.language = 'fr';
				break;
			case 'es_CL':
			case 'es_ES':
			case 'es_MX':
			case 'es_US':
				this.language = 'es';
				break;
			case 'ja_JP':
			case 'zh_CN':
			case 'tr_TR':
			case 'en_CA':
			case 'en_GB':			
			case 'en_US':
			default:
				this.language = '';
				this.weekStart = 0;
				break;
		}
	}
}
function disableAlerts() {
	scwShowInvalidDateMsg       = false;
    scwShowOutOfRangeMsg        = false;
    scwShowDoesNotExistMsg      = false;
    scwShowInvalidAlert         = false;
    scwShowDateDisablingError   = false;
    scwShowRangeDisablingError	= false;
}
disableAlerts();
