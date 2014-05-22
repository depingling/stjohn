<% { %>

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CategoryUtil" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.StoreItemLoaderMgrForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_ITEM_LOADER_FORM" type="com.cleanwise.view.forms.StoreItemLoaderMgrForm"/>
  <%
  String[] itemProperties = theForm.getItemProperties();
  int[] itemPropertiesAbcOrder = theForm.getItemPropertiesAbcOrder();
  int[] itemPropertyMap = theForm.getItemPropertyMap();
  %>
<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" CHARSET="ISO-8859-1"></SCRIPT>
<SCRIPT TYPE="text/javascript" SRC="../externals/lib.js" CHARSET="ISO-8859-1"></SCRIPT>
<SCRIPT TYPE="text/javascript" SRC="../externals/htmlTextHelper.js" CHARSET="ISO-8859-1"></SCRIPT>

<script language="JavaScript1.2">

function Submit (val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' &&
        dml[i].elements[j].value=='BBBBBBB') {
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
      dml[i].elements[j].value=val;
      dml[i].submit();
      break;
    }
  }
 }
}
function sortSubmit(val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  fieldFl = false;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' &&
        dml[i].elements[j].value=='BBBBBBB') {
          dml[i].elements[j].value="sort";
          actionFl = true;
        }
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
    if (dml[i].elements[j].name=='sortField') {
      dml[i].elements[j].value=val;
      fieldFl = true;
    }
    if(actionFl && fieldFl) {
      dml[i].submit();
      break;
    }
  }
 }
}

  function doEdit(rownum) {
      var el = event.srcElement
      //alert('el.tagName='+ el.tagName);
      if('TD'!=el.tagName) return;
      var colnum = el.cellIndex;
      var val = el.innerHTML;
      colname = document.all.xlstable.rows[0].cells[colnum].innerHTML;
      txt = getInputControl(colname,rownum, val,'');
      if(''!=txt) {
        el.innerHTML = txt;
      }
    }

  function getInputControlGreenCertified(columnName, rowNum, val,inpField) {
      var txt = '';
      if(''==inpField) inpField = 'greenCertifiedInp';
      txt = '<select name="'+inpField+'['+rowNum+']" class="text" onchange="javascript: setRedSave()">'
      txt = txt + '<option value="">&nbsp;</option>';
      txt = txt + '<option value="<<< Clear >>>"><<< Clear >>></option>';
      <%
        String companyName="";
        BusEntityDataVector beDV = theForm.getCertifiedCompanies();
        if(beDV!=null) {
          for(Iterator iter=beDV.iterator(); iter.hasNext();) {
              BusEntityData beD = (BusEntityData) iter.next();
            companyName = beD.getShortDesc();
      %>
      if(val=="<%=companyName%>") {
        txt += '<option value="<%=companyName%>" selected><%=companyName%></option>';
      } else {
        txt += '<option value="<%=companyName%>"><%=companyName%></option>';
      }
      <%
          }
        }
      %>
      txt = txt + '</select>'
      return txt;
  }
  function getInputControl(columnName, rowNum, val,inpField) {
    var txt ='';
     // alert('columnName='+ columnName);
    if("Sku Num"==columnName) {
      if(''==inpField) inpField = 'skuNumInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="10" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Short Desc"==columnName) {
      if(''==inpField) inpField = 'shortDescInp';
      txt = '<textarea cols="70" rows="2" name="'+inpField+'['+rowNum+']" onchange="javascript: setRedSave()">'+val+'</textarea>';
    }
    else if("Other Desc"==columnName) {
      if(''==inpField) inpField = 'otherDescInp';
      txt = '<textarea cols="70" rows="2" name="'+inpField+'['+rowNum+']" onchange="javascript: setRedSave()">'+val+'</textarea>';
    }
    else if("UNSPSC"==columnName) {
      if(''==inpField) inpField = 'unspscCodeInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="20" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("NSN"==columnName) {
      if(''==inpField) inpField = 'nsnInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="20" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("PSN"==columnName) {
      if(''==inpField) inpField = 'psnInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="20" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Long Desc"==columnName) {
      if(''==inpField) inpField = 'longDescInp';
      txt = '<textarea cols="70" rows="4" name="'+inpField+'['+rowNum+']" onchange="javascript: setRedSave()">'+val+'</textarea>';
    }
    else if("Color"==columnName) {
      if(''==inpField) inpField = 'skuColorInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Pack"==columnName) {
      if(''==inpField) inpField = 'skuPackInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Size"==columnName) {
      if(''==inpField) inpField = 'skuSizeInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="20" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("UOM"==columnName) {
      if(''==inpField) inpField = 'skuUomInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Category"==columnName) {
      if(''==inpField) inpField = 'categoryInp';
      txt = '<select name="'+inpField+'['+rowNum+']" class="text" onchange="javascript: setRedSave()">'
      txt = txt + '<option value="">&nbsp;</option>';
      txt = txt + '<option value="<<< Clear >>>"><<< Clear >>></option>';
      <%
        String categoryName = "";
        String categoryValue = "";
        CatalogCategoryDataVector ccDV = theForm.getStoreCategories();
        if(ccDV!=null) {
          for(Iterator iter=ccDV.iterator(); iter.hasNext();) {
            CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
            categoryName = CategoryUtil.buildFullCategoryName(
                ccD.getCatalogCategoryShortDesc(), ccD.getCatalogCategoryLongDesc());
            categoryValue = CategoryUtil.encodeCategoryNames(
                ccD.getCatalogCategoryShortDesc(), ccD.getCatalogCategoryLongDesc());
            categoryValue = StringUtils.encodeHtmlSingleAndDoubleQuotes(categoryValue);
            categoryName = StringUtils.encodeHtmlSingleAndDoubleQuotes(categoryName);
      %>
        if (compareStringsIgnoreBlankSpaces(val, "<%=categoryValue%>") == 0) {
        txt += '<option value="<%=categoryValue%>" selected><%=categoryName%></option>';
        } 
        else {
        txt += '<option value="<%=categoryValue%>"><%=categoryName%></option>';
      }
      <%
          }
        }
      %>

      txt = txt + '</select>'
    }
    else if("Manufacturer"==columnName) {
      if(''==inpField) inpField = 'manufNameInp';
      txt = '<select name="'+inpField+'['+rowNum+']" class="text" onchange="javascript: setRedSave()">'
      txt = txt + '<option value="">&nbsp;</option>';
      txt = txt + '<option value="<<< Clear >>>"><<< Clear >>></option>';
      <%
        String manufacturerName="";
        ManufacturerDataVector mDV = theForm.getStoreManufacturers();
        if(ccDV!=null) {
          for(Iterator iter=mDV.iterator(); iter.hasNext();) {
            ManufacturerData mD = (ManufacturerData) iter.next();
            String manufacturerNameDB = mD.getBusEntity().getShortDesc();
            manufacturerName = manufacturerNameDB;
            if(manufacturerName.indexOf("'")>=0) {
                manufacturerName = manufacturerName.replaceAll("'","&#39");
            }

      %>
      if(val=="<%=manufacturerNameDB%>") {
        txt += '<option value="<%=manufacturerName%>" selected><%=manufacturerName%></option>';
      } else {
        txt += '<option value="<%=manufacturerName%>"><%=manufacturerName%></option>';
      }
      <%
          }
        }
      %>

      txt = txt + '</select>'
    }
    else if("Manuf. Sku"==columnName) {
      if(''==inpField) inpField = 'manufSkuInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="30" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Manuf. Pack"==columnName) {
      if(''==inpField) inpField = 'manufPackInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Manuf. UOM"==columnName) {
      if(''==inpField) inpField = 'manufUomInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }

    else if("Distributor"==columnName) {
      if(''==inpField) inpField = 'distNameInp';
      txt = '<select name="'+inpField+'['+rowNum+']" class="text" onchange="javascript: setRedSave()">'
      txt = txt + '<option value="">&nbsp;</option>';
      txt = txt + '<option value="<<< Clear >>>"><<< Clear >>></option>';
      <%
        String distributorName="";
        DistributorDataVector dDV = theForm.getStoreDistributors();
        if(ccDV!=null) {
          for(Iterator iter=dDV.iterator(); iter.hasNext();) {
            DistributorData dD = (DistributorData) iter.next();
            String distributorNameDB = dD.getBusEntity().getShortDesc();
            distributorName = distributorNameDB;
            if(distributorName.indexOf("'")>=0) {
                distributorName = distributorName.replaceAll("'","&#39");
            }

      %>
      if(val=="<%=distributorNameDB%>") {
        txt += '<option value="<%=distributorName%>" selected><%=distributorName%></option>';
      } else {
        txt += '<option value="<%=distributorName%>"><%=distributorName%></option>';
      }
      <%
          }
        }
      %>

      txt = txt + '</select>'
    }
    else if("Dist. Sku"==columnName) {
      if(''==inpField) inpField = 'distSkuInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="30" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Dist. Pack"==columnName) {
      if(''==inpField) inpField = 'distPackInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Dist. UOM"==columnName) {
      if(''==inpField) inpField = 'distUomInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Gen. Manufacturer"==columnName) {
      if(''==inpField) inpField = 'genManufNameInp';
      txt = '<select name="'+inpField+'['+rowNum+']" class="text" onchange="javascript: setRedSave()">'
      txt = txt + '<option value="">&nbsp;</option>';
      txt = txt + '<option value="<<< Clear >>>"><<< Clear >>></option>';
      <%
        String genManufName="";
        ManufacturerDataVector gmDV = theForm.getStoreManufacturers();
        if(ccDV!=null) {
          for(Iterator iter=gmDV.iterator(); iter.hasNext();) {
            ManufacturerData mD = (ManufacturerData) iter.next();
            String manufacturerNameDB = mD.getBusEntity().getShortDesc();
            genManufName = manufacturerNameDB;
            if(genManufName.indexOf("'")>=0) {
              genManufName = genManufName.replaceAll("'","&#39");
            }

      %>
      if(val=="<%=manufacturerNameDB%>") {
        txt += '<option value="<%=genManufName%>" selected><%=genManufName%></option>';
      } else {
        txt += '<option value="<%=genManufName%>"><%=genManufName%></option>';
      }
      <%
          }
        }
      %>

      txt = txt + '</select>'
    }
    else if("Gen. Manuf. Sku"==columnName) {
      if(''==inpField) inpField = 'genManufSkuInp';
      while(val.indexOf('"')!=-1) val = val.replace('"',"&quot;");
      txt = '<input type="text" size="30" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("List Price"==columnName) {
      if(''==inpField) inpField = 'listPriceInp';
      txt = '<input type="text" size="8" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Dist. Cost"==columnName) {
      if(''==inpField) inpField = 'distCostInp';
      txt = '<input type="text" size="8" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("SPL"==columnName) {
      if(''==inpField) inpField = 'splInp';
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Dist. UOM. Mult"==columnName) {
      if(''==inpField) inpField = 'distUomMultInp';
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Tax Exempt"==columnName) {
      if(''==inpField) inpField = 'taxExemptInp';
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    } else if("Special Permission"==columnName) {
      if(''==inpField) inpField = 'specialPermissionInp';
      txt = '<input type="text" size="3" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Catalog Price"==columnName) {
      if(''==inpField) inpField = 'catalogPriceInp';
      txt = '<input type="text" size="8" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("MFCP"==columnName) {
      if(''==inpField) inpField = 'mfcpInp';
      txt = '<input type="text" size="8" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if("Base Cost"==columnName) {
      if(''==inpField) inpField = 'baseCostInp';
      txt = '<input type="text" size="8" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if ("<%=StoreItemLoaderMgrForm.SHIPPING_WEIGHT%>" == columnName) {
        if ('' == inpField) 
            inpField = 'shippingWeightInp';
        txt = '<input type="text" size="8" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    else if ("<%=StoreItemLoaderMgrForm.WEIGHT_UNIT%>" == columnName) {
        if ('' == inpField) 
            inpField = 'weightUnitInp';
        txt = '<select name="'+inpField+'['+rowNum+']" class="text" onchange="javascript: setRedSave()">'
        txt = txt + '<option value="">&nbsp;</option>';
        txt = txt + '<option value="<%=StoreItemLoaderMgrForm.EMPTY_PROPERTY_VALUE%>"><%=StoreItemLoaderMgrForm.EMPTY_PROPERTY_VALUE%></option>';
        <%
        String[] weightUnitNames = StoreItemLoaderMgrForm.getWeightUnitValues();
        if (weightUnitNames != null && weightUnitNames.length > 0) {
            String weightUnitName = "";
            for (int i = 0; i < weightUnitNames.length; ++i) {
                weightUnitName = weightUnitNames[i];
        %>
                if (val == "<%=weightUnitName%>") {
                    txt += '<option value="<%=weightUnitName%>" selected><%=weightUnitName%></option>';
                } 
                else {
                    txt += '<option value="<%=weightUnitName%>"><%=weightUnitName%></option>';
                }
        <%
            }
        }
        %>
        txt = txt + '</select>'
    }
    else if("<%=StoreItemLoaderMgrForm.GREEN_CERTIFIED%>"==columnName) {
      if(''==inpField) inpField = 'greenCertifiedInp';
      txt = '<textarea cols="70" rows="2" name="'+inpField+'['+rowNum+']" onchange="javascript: setRedSave()">'+val+'</textarea>';
    }
    else if("<%=StoreItemLoaderMgrForm.CUSTOMER_SKU%>"==columnName) {
      if(''==inpField) inpField = 'customerSkuInp';
      txt = '<input type="text" size="30" name="'+inpField+'['+rowNum+']" value="'+val+'" onchange="javascript: setRedSave()"/>';
    }
    return txt;
  }

function setRedSave() {
 document.forms[0].saveFl.value="T";
 return;
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  fieldFl = false;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' &&
        dml[i].elements[j].value=='Save') {
          // alert('value='+dml[i].elements[j].value);
          dml[i].elements[j].style.color='red';
          dml[i].elements[j].style.fontWeight = 'bold'
          //return;
        }
    }
  }
 }


</script>

<div class="text">

  <%
  int colQty = 0;
  for(int ii=0; ii<itemPropertyMap.length; ii++) {
    if(itemPropertyMap[ii]!=0) colQty++;
  }
  %>
  <table ID=1023 cellspacing="1" border="0" width="960" class="mainbody">

  <html:form styleId="1024"  action="/storeportal/storeitemupload.do"  scope="session">
  <tr><td colspan='<%=colQty%>' class='results'>
  <div class='tableheader'><%=theForm.getFileName()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:submit property="action" value="Save" styleClass='text'/>
  <html:submit property="action" value="Match" styleClass='text'/>
  <html:submit property="action" value="Show Matched" styleClass='text'/>
  <html:submit property="action" value="Assign Skus" styleClass='text'/>
  <html:submit property="action" value="Remove Assignment" styleClass='text'/>
  <html:submit property="action" value="Create Skus" styleClass='text'/>
  <html:submit property="action" value="Update Skus" styleClass='text'/>
  <%if(theForm.getSourceTable()!=null) { %>
  <html:submit property="action" value="Full Table" styleClass='text'/>
  <% } %>
  </div></td></tr>

  <tr><td colspan='<%=colQty%>' class='results'>
  <!-- <b>Note:</b><html:textarea name="STORE_ITEM_LOADER_FORM" property="note" cols='80' rows='2' /> -->
  <%
   String tableIdS = "NA";
   if(theForm.getTableHeader()!=null) tableIdS = ""+theForm.getTableHeader().getUploadId();
  %>
  <b>Table Id:</b>&nbsp;<%=tableIdS%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Table Status:</b><html:select name="STORE_ITEM_LOADER_FORM" property="tableStatus">
    <html:option value="<%=RefCodeNames.UPLOAD_STATUS_CD.PROCESSING%>"><%=RefCodeNames.UPLOAD_STATUS_CD.PROCESSING%></html:option>
    <html:option value="<%=RefCodeNames.UPLOAD_STATUS_CD.PROCESSED%>"><%=RefCodeNames.UPLOAD_STATUS_CD.PROCESSED%></html:option>
  </html:select>
  </td></tr>
  <%
     String[] oldValues = theForm.getUpdateTableValues();
     boolean updateValuesFl = (oldValues!=null && oldValues.length>0)?true:false;
 %>

<tr><td colspan='<%=colQty%>' class='results'>
  <table ID=1025 cellspacing="0" border="1"  class="mainbody">
  <tr>
  <td> <!-- UI filter -->
  <table ID=1026>
  <tr><td colspan="2"><b>Filter</b></td></tr>
  <tr><td><b>Category:</b></td><td><html:text property="filterCateg"/></td></tr>
  <tr><td><b>Sku Name:</b></td><td><html:text property="filterName"/></td></tr>
  <tr><td><b>Manufacturer:</b></td><td><html:text property="filterManuf"/></td></tr>
  <tr><td><b>Disributor:</b></td><td><html:text property="filterDist"/></td></tr>
  <tr><td><b>Sku Number:</b></td><td><html:text property="filterSku"/></td></tr>
  <tr><td colspan="2">
  Store&nbsp;Sku&nbsp;<html:radio property="filterSkuType" value="storeSku"/>&nbsp;&nbsp;
  Manuf.&nbsp;Sku&nbsp;<html:radio property="filterSkuType" value="manufSku"/>&nbsp;&nbsp;
  Dist.&nbsp;Sku&nbsp;<html:radio property="filterSkuType" value="distSku"/>
  </td></tr>
  <tr><td colspan="2">
  <html:submit property="action" value="Select"/>&nbsp;&nbsp;
  All<html:radio property="filterMatched" value="All"/>&nbsp;&nbsp;
  Matched<html:radio property="filterMatched" value="Matched"/>&nbsp;&nbsp
  Unmatched<html:radio property="filterMatched" value="Unmatched"/>
  </td></tr>
  </table>
  </td>
  <td valign='top'>
     <b>Column to Update</b><br>
     <html:select name="STORE_ITEM_LOADER_FORM" property="columnToUpdate"  styleClass="text" onchange="Submit('Cancel');">
        <%
        for(int ii=0; ii<itemPropertiesAbcOrder.length; ii++) {
          int jj = itemPropertiesAbcOrder[ii];
          if(itemPropertyMap[jj]==0) continue;
        %>

        <html:option value="<%=itemProperties[jj]%>"><%=itemProperties[jj]%></html:option>
        <% } %>
     </html:select>
     <br>
     <html:text name="STORE_ITEM_LOADER_FORM" property="updateFilter" size='20'/>
     <br>
    <html:submit property="action" value="Select Update" styleClass='text'/>
    <%
     if(updateValuesFl) {
    %>
    <br>
    <br>
    <html:submit property="action" value="Update Values" styleClass='text'/>
    <br>
    <html:submit property="action" value="Cancel" styleClass='text'/>
    <% } %>
  </td>
    <%
     if(updateValuesFl) {
    %>
  <td>
    <table id="contextUpdate" cellspacing="0" border="0"  class="mainbody">
        <% 
        for(int ii=0; ii<oldValues.length; ii++) {
            String valueInp = "updateValue["+ii+"]";
            String valueShow = oldValues[ii];
            String labelShow = oldValues[ii];
            if (StoreItemLoaderMgrForm.CATEGORY_PROPERTY.equals(theForm.getColumnToUpdate())) {
                labelShow = CategoryUtil.buildFullCategoryNameByValue(labelShow);
            }
            valueShow = StringUtils.encodeHtmlSingleAndDoubleQuotes(valueShow);
        %>
        <tr>
            <td>
                <%=labelShow%>
            </td>
            <td>
                <script>
                    <% 
                    if (theForm.getColumnToUpdate().equals("Green Certified") == true) {
                    %>      
                    var txt = getInputControlGreenCertified("<%=theForm.getColumnToUpdate()%>", <%=ii%>, "<%=valueShow%>","updateValue");
                    <% 
                    } 
                    else {
                    %>
                    var txt = getInputControl("<%=theForm.getColumnToUpdate()%>", <%=ii%>, "<%=valueShow%>","updateValue");
                    <% 
                    } 
                    %>
                    document.all.contextUpdate.rows[<%=ii%>].cells[1].innerHTML=txt;
                </script>
            </td>
        </tr>
        <% 
        } 
        %>
    </table>
  </td>
    <% } else { %>
  <td valign='bootom'>
   <html:submit property="action" value="Save" styleClass='text'/>
   <html:submit property="action" value="Reload" styleClass='text'/>
  </td>
     <% } %>
  </tr>
  </table>
</td></tr>

  <table id="xlstable" cellspacing="1" border="0" width="960" class="mainbody">
<!-- header -->
<%
  UploadSkuViewVector itemsToMatch = theForm.getItemsToMatch();
  if(itemsToMatch==null) itemsToMatch = new UploadSkuViewVector();
%>
<tr>
<td>
<a ID=1027 class="text" href="javascript:SetCheckedGlobal(1,'selectToCreate')">[Chk.&nbsp;All]</a>
<br>
<a ID=1028 class="text" href="javascript:SetCheckedGlobal(0,'selectToCreate')">[&nbsp;Clear]</a>
</td>
<td><b>Row&nbsp;#</b><br>
<% if(itemsToMatch.size()>0) { %>
<a ID=1029 class="text" href="javascript:SetCheckedGlobal(1,'selectToAssign')">[Chk.&nbsp;All]</a>
<br>
<a ID=1030 class="text" href="javascript:SetCheckedGlobal(0,'selectToAssign')">[&nbsp;Clear]</a>
<% } %>
</td>
<td><b>Matched Sku&nbsp;Num</b></td>
<%
  for(int ii=0; ii<itemPropertyMap.length; ii++) {
    if(itemPropertyMap[ii]==0) continue;
    if (itemProperties[ii].equals("Dist. Cost") && theForm.getShowDistCostFl()) { // bug# 852 fix
    	continue;
    }    	
%>
 <td class='tableheader'><%=itemProperties[ii]%></td>
<%
  }
%>
</tr>
<!-- Content -->
<%  UploadSkuView[] uploadSkusVw = theForm.getUploadSkus(); %>
Count: <%=uploadSkusVw.length%>
<%
  java.util.Iterator iter = itemsToMatch.iterator();
  UploadSkuView usMatchVw = null;
  int pos = -1;

  for(int ii=0; ii<uploadSkusVw.length; ii++) {
     UploadSkuView usVw = uploadSkusVw[ii];
     UploadSkuData usD =  usVw.getUploadSku();
     int rowNum = usD.getRowNum();
     String skuNum = usVw.getSkuNum();
     if(skuNum==null) skuNum = "";
     String skuRefLink = "storeitemupload.do?action=edit-item&itemId="+usD.getItemId();
     String selectCheckbox = "selectToCreate["+ii+"]";
     String onClick = "javascript: doEdit("+ii+")";
%>
<tr    ondblclick="<%=onClick%>">
<td class='results'><html:checkbox name="STORE_ITEM_LOADER_FORM" property='<%=selectCheckbox%>'/></td>
<td class='results'><%=rowNum%></td>
<td class='results'><a ID=1031 class="results" href="<%=skuRefLink%>"><%=skuNum%></a></td>
<%
       	int shortDescColumn = -1;
       	for(int jj=0; jj<itemPropertyMap.length; jj++) {
   	      	if(itemPropertyMap[jj]==0) continue;
          	String itemProperty = itemProperties[jj];
          	if("Short Desc".equals(itemProperty)) shortDescColumn = jj;
          	if("Dist. Cost".equals(itemProperty) && theForm.getShowDistCostFl()) continue;//bug#852 fix
          	String valS = theForm.getSkuProperty(usD,itemProperty);
          	if (valS==null) valS = "";
          	if ("Imagex URL".equals(itemProperty)) {
          	%>
<td class='results'><%if(valS!=null&&valS.length()>0){%><image src="<%=valS%>"/><%}%></td>
<%
          	} else if (itemProperty != null &&  itemProperty.indexOf("URL") != -1) {
%>
<td class='results'><a ID=1032 href="<%=valS%>" target="_blank"><%=valS%></a></td>
<%
            } 
            else {
                if (StoreItemLoaderMgrForm.CATEGORY_PROPERTY.equals(itemProperty)) {
                    valS = CategoryUtil.buildFullCategoryNameByValue(valS);
                    valS = StringUtils.encodeHtmlSingleAndDoubleQuotes(valS);
                }
%>
 <td class='results'><%=valS%></td>
<%
            }
        }
%>
</tr>
<%
        while(usMatchVw!=null || iter.hasNext()) {
         if(usMatchVw==null) {
             usMatchVw = (UploadSkuView) iter.next();
             pos++;
         }

         UploadSkuData usMatchD = usMatchVw.getUploadSku();
         int rowNumMatch = usMatchD.getRowNum();
         if(rowNumMatch<rowNum) {
             usMatchVw = null;
             continue;
         }
         if(rowNumMatch>rowNum) {
             break;
         }
         String selectToAssign = "selectToAssign["+pos+"]";
         skuRefLink = "storeitemupload.do?action=edit-item&itemId="+usMatchVw.getUploadSku().getItemId();
%>
<tr>
<td class='rowb'>&nbsp</td>
<td class='rowb'><html:checkbox  name="STORE_ITEM_LOADER_FORM" property='<%=selectToAssign%>'/></td>
<td class='rowb'><%=usMatchVw.getSkuNum()%></td>
<%
       for(int jj=0; jj<itemPropertyMap.length; jj++) {
          if(itemPropertyMap[jj]==0) continue;
          String itemProperty = itemProperties[jj];
          String valS = theForm.getSkuProperty(usMatchD,itemProperty);
          if(valS==null) valS = "";
          if(shortDescColumn==jj) {
%>
 <td class='rowb'><a ID=1033 class="rowb" href="<%=skuRefLink%>"><%=valS%></a></td>
<% 	
    } 
    else { 
        if (StoreItemLoaderMgrForm.CATEGORY_PROPERTY.equals(itemProperty)) {
            valS = CategoryUtil.buildFullCategoryNameByValue(valS);
            valS = StringUtils.encodeHtmlSingleAndDoubleQuotes(valS);
        }
%>
 <td class='rowb'><%=valS%></td>
<%
        }}
%>
</tr>

<%
         usMatchVw = null;
       }
%>


<%
     }
%>


  </table>

  <input type="hidden" name="action" value="BBBBBBB"/>
  <input type="hidden" name="saveFl" value="F"/>
  <input type="hidden" name="sortField" value="BBBBBBB"/>
  <input type="hidden" name="catalogCpoId" value="BBBBBBB"/>
  <input type="hidden" name="ogCpoId" value="BBBBBBB"/>

</html:form>

</div>
<% } %>
