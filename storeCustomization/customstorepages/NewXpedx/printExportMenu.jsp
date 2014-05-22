
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
function viewPrinterFriendly() {
  var loc = "../store/printerFriendlyOrderGuidePersonalized.do?action=pdfPrintCatalogCatalogPers";
  prtwin = window.open(loc,"cat_print",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();
  return false;
}

function viewPrinterFriendlyWithoutAddress() {
  var loc = "../store/printerFriendlyOrderGuide.do?action=pdfPrintCatalogCatalog";
  prtwin = window.open(loc,"cat1_print",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();
  return false;
}

function viewExcelFormat() {
  var loc = "../store/printerFriendlyOrderGuidePersonalized.do?action=excelPrintCatalogCatalogPers";
  prtwin = window.open(loc,"excel_format",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function viewPrinterFriendlyPhysicalInv() {
	  var loc = "../store/printerFriendlyOrderGuide.do?action=pdfPrintCatalogCatalogInventory";
	  prtwin = window.open(loc,"cat_print",
	    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
	  prtwin.focus();
	  return false;
	}


function viewExcelFormatPhysicalInv() {
	  var loc = "../store/printerFriendlyOrderGuide.do?action=excelPrintCatalogCatalogInventory";
	  prtwin = window.open(loc,"excel_format",
	    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
	  prtwin.focus();

	  return false;
	}


//-->

</script>


<table width="100%">
<tr><td class="xpdexMenuHeader"><app:storeMessage key="template.xpedx.homepage.header.printOrExportCatalog"/></td></tr>
<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="4"></td></tr>
	<tr>
		<td align="left">	
			<!-- <a ID=167 href="#" class="linkButton" onclick="viewPrinterFriendly();" -->
			<!-- <a class ="categorymenulevel_1" href="#" onclick="viewPrinterFriendly();"> -->
			<a class ="categorymenulevel_1" href="../store/printerFriendlyOrderGuidePersonalized.do?action=pdfPrintCatalogCatalogPers" > 
				<app:storeMessage key="template.xpedx.apple.text.printWithLocation"/>
			</a>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<!-- <a class ="categorymenulevel_1" href="#" onclick="viewPrinterFriendlyWithoutAddress();"> -->
			<a class ="categorymenulevel_1" href="../store/printerFriendlyOrderGuide.do?action=pdfPrintCatalogCatalog" >
				<app:storeMessage key="template.xpedx.apple.text.printWithoutLocation"/>
			</a>
		</td>
	</tr>
	<tr><td align="left">
	<a class ="categorymenulevel_1" href="../store/printerFriendlyOrderGuidePersonalized.do?action=excelPrintCatalogCatalogPers"><app:storeMessage key="template.xpedx.apple.text.exportCatalogToExcel"/></a>
	</td></tr>


<%if (ShopTool.isUsedPhysicalInventoryAlgorithm(request.getSession())) {%>
<tr><td align="left">
	<a class ="categorymenulevel_1" href="#" onclick="viewPrinterFriendlyPhysicalInv();"><app:storeMessage key="template.xpedx.text.printPhysicalInventory"/></a>
	</td></tr>
	<tr><td align="left">
	<a class ="categorymenulevel_1" href="../store/printerFriendlyOrderGuide.do?action=excelPrintCatalogCatalogInventory"><app:storeMessage key="template.xpedx.text.exportPhysicalInventory"/></a>
	</td></tr>
<%} %>
</table>


