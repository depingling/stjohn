/**
 * 
 */
package com.cleanwise.view.taglibs;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.dto.LocationBudgetChartDto;
import com.cleanwise.service.api.dto.OrdersAtAGlanceDto;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

public class OrdersReportTag extends TagSupport{
	private static final Logger log = Logger.getLogger(OrdersReportTag.class);
	private OrdersAtAGlanceDto _pieChartDto;
	private String _pieChartContainer;
	
	public OrdersReportTag(){
	}
	public String getPieChartContainer() {
		return _pieChartContainer;
	}
	public void setPieChartContainer(String pieChartContainer) {
		_pieChartContainer = pieChartContainer;
	}
	
	public OrdersAtAGlanceDto getPieChartDto() {
		return _pieChartDto;
	}
	public void setPieChartDto(OrdersAtAGlanceDto pieChartDto) {
		_pieChartDto = pieChartDto;
	}
	
	public int doStartTag() throws JspException{
		try
		{
			JspWriter out = pageContext.getOut();
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			Map<String,BigDecimal> categoryAmountMap = _pieChartDto.getCategoryAmountMap();
			String chartTitle = _pieChartDto.getTitle();
			boolean showCurrency = _pieChartDto.isShowCurrency();
			String [] currencyFormatter = ClwI18nUtil.getCurrencyAlignment(request,null,null," ");
			// for STJ-6112: when currency local_code is &nbsp; replace this by space
			if (Utility.isSet(currencyFormatter[1])&&currencyFormatter[1].equals("&nbsp;")) {
                currencyFormatter[1] = " ";
            }
			ArrayList<Character> numberSeparators = ClwI18nUtil.getDecimalAndGroupingSeparator(request,null);
			Character decimalSeparator = numberSeparators.get(0);
			Character groupingSeparator = numberSeparators.get(1);
			StringBuffer htmlBuffer  = new StringBuffer();
			htmlBuffer.append("<script type=\"text/javascript\">");
			if(currencyFormatter[2].equals(Constants.POLLOCK_STORE_GLOBAL_CODE)){// For pollock store
				if(currencyFormatter[0].equals(Constants.LOCALE_CODE_OR_PRICE_CURRENCY)){
					htmlBuffer.append(" function formatChartDataLabel(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length);  return '<b>'+ categoryName + '</b>: '+ Highcharts.numberFormat(value, 4)+'"+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");
					htmlBuffer.append(" function formatChartTooltip(name,value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName +'</b> :'+ Highcharts.numberFormat(value, 4)+'"+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");
				}
				else
					if(currencyFormatter[0].equals(Constants.DISPLAY_CURRENCY_LEFT)){
					htmlBuffer.append(" function formatChartDataLabel(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return  '<b>' + categoryName +'</b>: "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 4)+ ' (' + Math.round(percentage) +'%)';}");
					htmlBuffer.append(" function formatChartTooltip(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return  '<b>' + categoryName + '</b> : "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 4)+ ' (' + Math.round(percentage) +'%)';}");
					}
					else{
						htmlBuffer.append(" function formatChartDataLabel(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName+'</b>: '+ Highcharts.numberFormat(value, 4)+'"+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");
						htmlBuffer.append(" function formatChartTooltip(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName +'</b>: '+Highcharts.numberFormat(value, 4)+': "+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");					
					}
				}
				else{
					if(currencyFormatter[0].equals(Constants.LOCALE_CODE_OR_PRICE_CURRENCY)){
						htmlBuffer.append(" function formatChartDataLabel(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName+'</b>: '+ Highcharts.numberFormat(value, 2)+'"+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");
						htmlBuffer.append(" function formatChartTooltip(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName +'</b>: '+ Highcharts.numberFormat(value, 2)+'"+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");
					}
					else
						if(currencyFormatter[0].equals(Constants.DISPLAY_CURRENCY_LEFT)){
						htmlBuffer.append(" function formatChartDataLabel(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName + '</b>: "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 2)+ ' (' + Math.round(percentage) +'%)';}");
						htmlBuffer.append(" function formatChartTooltip(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName +'</b>: "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 2)+ ' (' + Math.round(percentage) +'%)';}");
						}
						else{
							htmlBuffer.append(" function formatChartDataLabel(name, value,percentage) { var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length);	 return '<b>' + categoryName +'</b>: '+Highcharts.numberFormat(value, 2)+'"+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");
							htmlBuffer.append(" function formatChartTooltip(name, value,percentage) {var index = name.indexOf('-');var categoryName = name.substring(index+1,name.length); return '<b>' + categoryName +'</b>: '+Highcharts.numberFormat(value, 2)+': "+currencyFormatter[1]+" ('+ Math.round(percentage) +'%)' ;}");			
						}
				}
			htmlBuffer.append("function formatLegend(name){");
			htmlBuffer.append(" var categoryName = name.split('-',2);");
			htmlBuffer.append("return categoryName;");
			htmlBuffer.append("}");
			htmlBuffer.append("$(document).ready(function() {new Highcharts.Chart({chart: {");
			htmlBuffer.append("renderTo: '"+getPieChartContainer()+"',");
			htmlBuffer.append("plotBackgroundColor: null,");
			htmlBuffer.append("plotBorderWidth: null,");
			htmlBuffer.append("plotShadow: false");
			htmlBuffer.append("},");
			htmlBuffer.append("colors: [");
			htmlBuffer.append("'#7BAE2B',"); 
			htmlBuffer.append("'#C84B4B',"); 
			htmlBuffer.append("'#285B8C',"); 
			htmlBuffer.append("'#B6B6B6',"); 
			htmlBuffer.append("'#6C6C6C',"); 
			htmlBuffer.append("'#EBEBEB',");
			htmlBuffer.append("'#7BAE2B',"); 
			htmlBuffer.append("'#C84B4B',"); 
			htmlBuffer.append("'#285B8C',"); 
			htmlBuffer.append("'#B6B6B6',"); 
			htmlBuffer.append("'#6C6C6C',"); 
			htmlBuffer.append("'#EBEBEB'");
			htmlBuffer.append("],");
			htmlBuffer.append("credits: {");
			htmlBuffer.append("enabled: false");
			htmlBuffer.append("},");
			htmlBuffer.append("title: {");
			htmlBuffer.append("text: ''");
			htmlBuffer.append("},");
			htmlBuffer.append("legend: {");
			htmlBuffer.append("enabled: false,");
			htmlBuffer.append("labelFormatter: function() {");
			htmlBuffer.append("return formatLegend(this.name);}},");
			htmlBuffer.append("tooltip: {");
			htmlBuffer.append("formatter: function() {return formatChartTooltip(this.point.name,this.y,this.percentage);}},");
			htmlBuffer.append("plotOptions: {");
			htmlBuffer.append("column: {");
			htmlBuffer.append("stacking: 'percent'");
			htmlBuffer.append("},");
			htmlBuffer.append("series: {");
			htmlBuffer.append("cursor: 'pointer',");
			htmlBuffer.append("events: {");
			htmlBuffer.append("click: function(event) {");
			htmlBuffer.append("if(event.point.name != '0-Other') {");
			htmlBuffer.append("javascript:setLevelAndSubmitForm(event.point.name)}}}},");
			htmlBuffer.append("pie: {");
			htmlBuffer.append("size: \"60%\",");
			htmlBuffer.append("allowPointSelect: true,");
			htmlBuffer.append("cursor: 'pointer',");
			htmlBuffer.append("dataLabels: {");
			htmlBuffer.append("enabled: true,");
			htmlBuffer.append("color: '#000000',");
			htmlBuffer.append("connectorColor: '#000000',");
			htmlBuffer.append("formatter: function() {");
			htmlBuffer.append("return formatChartDataLabel(this.point.name,this.y,this.percentage);");
			htmlBuffer.append("}");
			htmlBuffer.append("},");
			htmlBuffer.append("showInLegend: true");
			htmlBuffer.append("}");
			htmlBuffer.append("},");
			htmlBuffer.append("series: [{");
			htmlBuffer.append("type: 'pie',");
			htmlBuffer.append("data: [");
			//categories should be in the format categoryKey-categoryName (12345-Cleaning Supplies)
			ArrayList categories = new ArrayList(categoryAmountMap.keySet());
			ArrayList amounts = new ArrayList(categoryAmountMap.values());
			for(int i = 0;i < categories.size();i++)
			{
				String categoryName = Utility.escapeSpecialCharsForPieChart((String)categories.get(i));
				htmlBuffer.append("['"+ categoryName +"',"+amounts.get(i)+"]");
				if((i+1) < categories.size()){
					htmlBuffer.append(",");
				}
			}
			htmlBuffer.append("]");
			htmlBuffer.append("}]});});");
			htmlBuffer.append("Highcharts.setOptions({lang: {decimalPoint: '"+decimalSeparator+"',thousandsSep: '\\"+groupingSeparator+"'}});");
			htmlBuffer.append(" </script> ");
			out.print(htmlBuffer.toString());
			
			}
		catch(IOException ioe)
		{
			log.error("Unexpected exception in OrdersTag: ", ioe);
		}
		return SKIP_BODY;
	}
}
