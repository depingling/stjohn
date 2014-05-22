/*
 * StoreItemLoaderMgrLogic.java
 *
 * Created on August 18, 2005, 9:55 AM
 */

package com.cleanwise.view.logic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.ProductInformation;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.CatalogCategoryComparatorByDesc;
import com.cleanwise.service.api.util.CategoryKey;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UploadData;
import com.cleanwise.service.api.value.UploadDataVector;
import com.cleanwise.service.api.value.UploadSkuData;
import com.cleanwise.service.api.value.UploadSkuView;
import com.cleanwise.service.api.value.UploadSkuViewVector;
import com.cleanwise.service.api.value.UploadValueData;
import com.cleanwise.service.api.value.XlsTableView;
import com.cleanwise.view.forms.StoreItemLoaderMgrForm;
import com.cleanwise.view.forms.StoreItemMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.HttpClientUtil;

/**
 *
 * @author Ykupershmidt
 */
public class StoreItemLoaderMgrLogic {
    private final static String GREEN_CERTIFIED_DELIMITER = ",";
    //protected Logger log = Logger.getLogger(this.getClass());
    private static final Category log = Category.getInstance(StoreItemLoaderMgrLogic.class);
  /** Creates a new instance of StoreItemLoaderMgrLogic */
  public StoreItemLoaderMgrLogic() {
  }


  public static ActionErrors search(HttpServletRequest request,
                                    ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    UploadDataVector tables = null;
    try {
      tables =
        itemEjb.getXlsTables(storeId, pForm.getFileNameTempl(),
                             pForm.getShowProcessedFl());
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    pForm.setTableList(tables);

    return ae;
  }


  public static ActionErrors uploadFile(HttpServletRequest request,
                                        ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();

    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    FormFile file = pForm.getXlsFile();
    if (file == null) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No file found"));
      return ae;
    }

    try {
      long fileSize = file.getFileSize();
      if (fileSize == 0) {
        ae.add("Error", new ActionError("item.master.bad_upload_file",
                                        file.getFileName()));
        return ae;
      }

      if (fileSize > 0xFFFFFF) {
        String errorMess = "File is too long. File size: " + fileSize;
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;

      }

      // get the file extension (e.g. ".jpg", ".pdf", etc.)
      String fileExt = null;
      String uploadFileName = file.getFileName();
      int i = uploadFileName.lastIndexOf(".");
      if (i < 0) {
        fileExt = "";
      } else {
        fileExt = uploadFileName.substring(i);
      }
     
      if(fileExt.equalsIgnoreCase(".xlsx")){
    	  ae.add("error", new ActionError("error.badFileFormat",uploadFileName));
    	  return ae;
      }

      InputStream stream = null;
      try {
        stream = file.getInputStream();
      } catch (Exception exc) {
        ae.add("error", new ActionError("item.master.upload_file_error",
                                        exc.getMessage()));
        return ae;
      }
////////////////////////////////////////////////////////////////////////////////////////
      String fileName = uploadFileName;
      int ind = fileName.lastIndexOf('/');
      int ind1 = fileName.lastIndexOf('\\');
      if (ind1 > ind) ind = ind1;
      if (ind > 0) fileName = fileName.substring(ind + 1);
      
      WorkbookSettings ws = new WorkbookSettings();
      ws.setEncoding("ISO-8859-1");
      Workbook workbook = null;
      try{
    	  workbook = Workbook.getWorkbook(stream,ws);
      }catch(Exception e){
    	  ae.add("error", new ActionError("error.badFileFormat",uploadFileName));
    	  return ae;
      }
      pForm.setFileName(fileName);
      Sheet sheet = workbook.getSheet(0);
      int colQty = sheet.getColumns();
      boolean[] emptyColumnFl = new boolean[colQty];
      for (int ii = 0; ii < colQty; ii++) {
        emptyColumnFl[ii] = true;
      }
      int rowQty = sheet.getRows();
      if (rowQty < 1) {
        String mess = "******** Error. Empty sheet";
        throw new Exception(mess);
      }
      String[][] sourceTable = new String[rowQty][colQty];
      for (int jj = 0; jj < rowQty; jj++) {
        String[] row = new String[colQty];
        boolean emptyFl = true;
        for (int ii = 0; ii < colQty; ii++) {
          row[ii] = null;
          Cell cell = sheet.getCell(ii, jj);
          if (cell == null) {
            continue;
          }
          int ll = 0;
          CellType type = cell.getType();
          String valS = cell.getContents();

          if (!Utility.isSet(valS)) {
            continue;
          }
          emptyColumnFl[ii] = false;
          emptyFl = false;
          if (valS != null) {
              valS = valS.trim();
          }
          row[ii] = valS;
        }
        if (!emptyFl) {
          sourceTable[jj] = row;
        } else {
          sourceTable[jj] = null;
        }
      }

      String[] itemProperties = pForm.getItemProperties();
      char[][] itemPropertiesChar = new char[itemProperties.length][];
      for (int ii = 0; ii < itemProperties.length; ii++) {
        String prop = itemProperties[ii].toUpperCase();
        itemPropertiesChar[ii] = prop.toCharArray();
      }
      String[] columnTypes = new String[colQty];
      for (int jj = 0; jj < rowQty; jj++) {
        String[] row0 = sourceTable[jj];
        if (row0 == null)continue;
        for (int ii = 0; ii < row0.length; ii++) {
          int propInd = bestMatch(itemPropertiesChar, Utility.strNN(row0[ii]));
          if (propInd < 0) {
            columnTypes[ii] = "";
          } else {
            columnTypes[ii] = itemProperties[propInd];
          }
        }
        break;
      }

      pForm.setColumnTypes(columnTypes);
      pForm.setSourceTable(sourceTable);
      pForm.setEmptyColumnFl(emptyColumnFl);
      pForm.setTable(null);

      return ae;
    } finally {
      file.destroy();
    }
  }

  private static int bestMatch(char[][] pItemPropertiesChar,
                               String pColumnTitle) {
    int bestMatch = -1;
    int bestMatchNum = 0;
    char[] colTitleCh = pColumnTitle.toUpperCase().toCharArray();
    for (int ii = 0; ii < pItemPropertiesChar.length; ii++) {
      int jj = 0, kk = 0, ww = 0;
      for (; jj < colTitleCh.length && kk < pItemPropertiesChar[ii].length; ) {
        char cc1 = colTitleCh[jj];
        char cc2 = pItemPropertiesChar[ii][kk];
        if (cc1 == ' ' && cc2 == ' ') {
          jj++;
          kk++;
          continue;
        }
        if (cc1 == cc2) {
          ww++;
          jj++;
          kk++;
          continue;
        }
        if (cc1 == ' ') {
          kk++;
          continue;
        }
        if (cc2 == ' ') {
          jj++;
          continue;
        }
        jj++;
        kk++;
      }
      if (ww > 2 && bestMatchNum < ww) {
        bestMatchNum = ww;
        bestMatch = ii;
      }
    }
    return bestMatch;
  }

  public static ActionErrors fullTable(HttpServletRequest request,
                                       ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    pForm.setTable(null);
    return ae;
  }

  public static ActionErrors getTable(HttpServletRequest request,
                                      ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);

    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;

    int[] selectedRows = pForm.getSelectedRows();
    orderIntArray(selectedRows);
    String[][] sourceTable = pForm.getSourceTable();
    int rowQty = selectedRows.length;
    if (rowQty == 0) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No rows selected"));
      return ae;
    }

    String[] columnTypes = pForm.getColumnTypes();
    String[] itemProperties = pForm.getItemProperties();
    int[] itemPropertyMap = pForm.getItemPropertyMap();
    for (int ii = 0; ii < itemPropertyMap.length; ii++) {
      itemPropertyMap[ii] = 0;
    }
    int columnQty = 0;
    for (int ii = 0; ii < columnTypes.length; ii++) {
      String columnType = columnTypes[ii];
      if (!Utility.isSet(columnType)) {
        continue;
      }
      columnQty++;
      for (int jj = 0; jj < ii; jj++) {
        if (columnType.equals(columnTypes[jj])) {
          ae.add("error", new ActionError("error.simpleGenericError",
                                          "Duplicated column: " + columnType));
          return ae;
        }
      }
      for (int jj = 0; jj < itemProperties.length; jj++) {
        if (columnType.equals(itemProperties[jj])) {
          itemPropertyMap[jj] = 1;
        }
      }
    }

    if (columnQty < 1) {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No columns selected"));
      return ae;
    }
    int[] selectedColumns = new int[columnQty];
    UploadValueData[] headerRow = new UploadValueData[columnQty];
    String[] row0 = (String[]) sourceTable[0];

    for (int ii = 0, jj = 0; ii < columnTypes.length; ii++) {
      String columnType = columnTypes[ii];
      if (!Utility.isSet(columnType)) {
        continue;
      }
      UploadValueData uvD = UploadValueData.createValue();
      uvD.setColumnNum(jj);
      uvD.setColumnNumOrig(ii);
      uvD.setRowNum(0);
      uvD.setRowNumOrig(0);
      uvD.setUploadValue(columnType);
      uvD.setUploadValueOrig(row0[ii]);
      headerRow[jj] = uvD;
      selectedColumns[jj++] = ii;
    }

    UploadValueData[][] table = new UploadValueData[rowQty + 1][columnQty];
    UploadSkuView[] uploadSkusVw = new UploadSkuView[rowQty];
    table[0] = headerRow;
    for (int ii = 0; ii < rowQty; ii++) {
      String[] row = (String[]) sourceTable[selectedRows[ii]];
      UploadValueData[] subRow = new UploadValueData[columnQty];
      table[ii + 1] = subRow;
      UploadSkuView usVw = UploadSkuView.createValue();
      uploadSkusVw[ii] = usVw;
      UploadSkuData usD = UploadSkuData.createValue();
      usVw.setUploadSku(usD);
      for (int jj = 0; jj < columnQty; jj++) {
        String valS = row[selectedColumns[jj]];
        UploadValueData uvD = UploadValueData.createValue();
        uvD.setColumnNum(jj);
        uvD.setColumnNumOrig(selectedColumns[jj]);
        uvD.setRowNum(ii + 1);
        uvD.setRowNumOrig(selectedRows[ii]);
        uvD.setUploadValue(valS);
        uvD.setUploadValueOrig(valS);

        subRow[jj] = uvD;
        usD.setRowNum(selectedRows[ii]);
        String columnType = columnTypes[selectedColumns[jj]];
        pForm.setSkuProperty(usD, columnType, valS);
      }
    }

    UploadData uD = UploadData.createValue();
    uD.setStoreId(storeId);
    uD.setRowQty(rowQty);
    uD.setCoulumnQty(columnQty);
    uD.setFileName(pForm.getFileName());
    uD.setUploadStatusCd(RefCodeNames.UPLOAD_STATUS_CD.PROCESSING);
    pForm.setTableHeader(uD);
    pForm.setColumnTypes(columnTypes);
    pForm.setTable(table);
    pForm.setUploadSkus(uploadSkusVw);
    pForm.setItemPropertyMap(itemPropertyMap);
    pForm.setItemsToMatch(null);
    return ae;
  }


  public static ActionErrors getToUpdate(HttpServletRequest request,
                                         ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    String itemProperty = pForm.getColumnToUpdate();
    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();

    HashSet valueSet = new HashSet();
    String filter = Utility.strNN(pForm.getUpdateFilter());
    filter = filter.toLowerCase();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
      String valS = pForm.getSkuProperty(usD, itemProperty);
      String valSs[] = new String[]{valS};
      if (StoreItemLoaderMgrForm.GREEN_CERTIFIED.equals(itemProperty)) {
          valSs = valS.split(GREEN_CERTIFIED_DELIMITER);
          for (int i = 0; valSs != null && i < valSs.length; i++) {
              valSs[i] = valSs[i].trim();
          }
      }
      for (int i = 0; valSs != null && i < valSs.length; i++) {
          valS = valSs[i];
          if (!Utility.isSet(valS)) {
              valS = "<<Empty>>";
          }
          if (valS.toLowerCase().indexOf(filter) >= 0) {
              if (!valueSet.contains(valS)) {
                  valueSet.add(valS);
              }
          }
      }
    }
    String[] values = ((String[]) valueSet.toArray(new String[valueSet.size()]));
    orderStringArray(values);
    String[] newValues = new String[values.length];
    for (int ii = 0; ii < newValues.length; ii++) {
      newValues[ii] = values[ii];
    }
    pForm.setUpdateTableValues(values);
    pForm.setUpdateValues(newValues);
    return ae;
  }

  public static ActionErrors updateValues(HttpServletRequest request,
                                          ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    String columnToUpdate = pForm.getColumnToUpdate();
    String[] valuesToUpdate = pForm.getUpdateTableValues();
    String[] newValues = pForm.getUpdateValues();
    HashMap updateHM = new HashMap();
    for (int ii = 0; ii < newValues.length; ii++) {
      String newVal = newValues[ii];
      if ("Manufacturer".equals(columnToUpdate) ||
          "Category".equals(columnToUpdate) ||
          "Distributor".equals(columnToUpdate) ||
          "Gen. Manufacturer".equals(columnToUpdate)) {

        if (!Utility.isSet(newVal)) {
          newVal = valuesToUpdate[ii];
        }
        if ("<<< Clear >>>".equals(newVal)) {
          newVal = "";
        }
      }
      if (!valuesToUpdate[ii].equals(newVal)) {
        updateHM.put(valuesToUpdate[ii], newVal);
      }
    }

    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
      String value = pForm.getSkuProperty(usD, columnToUpdate);
      if (StoreItemLoaderMgrForm.GREEN_CERTIFIED.equals(columnToUpdate)) {
//          if (updateHM.containsKey(value)) {
              String oldValue = pForm.getSkuProperty(usD, columnToUpdate);
              if (oldValue != null && oldValue.length() > 0) {
                  String [] oldValues = oldValue.split(GREEN_CERTIFIED_DELIMITER);
                  for (int ovi = 0; oldValues != null && ovi < oldValues.length; ovi++) {
                      oldValues[ovi] = oldValues[ovi].trim();
                  }
                  StringBuffer sb = new StringBuffer();
                  for (int i = 0; oldValues != null && i < oldValues.length; i++) {
                      String newValue = (String) updateHM.get(oldValues[i]);
                      if (sb.length() > 0) {
                          sb.append(GREEN_CERTIFIED_DELIMITER);
                      }
                      sb.append((newValue != null) ? newValue : oldValues[i]);
                  }
                  pForm.setSkuProperty(usD, columnToUpdate, sb.toString());
              }
//          }
      } else {
          if (!Utility.isSet(value)) {
              value = "<<Empty>>";
          }
          if (updateHM.containsKey(value)) {
              pForm.setSkuProperty(usD, columnToUpdate, (String) updateHM.get(value));
          }
      }
    }
    pForm.setUploadSkus(uploadSkusVw);
    pForm.setUpdateTableValues(null);
    pForm.setUpdateValues(null);
    return ae;
  }

  public static ActionErrors clearValues(HttpServletRequest request,
                                         ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    pForm.setUpdateTableValues(null);
    pForm.setUpdateValues(null);
    return ae;
  }

  public static ActionErrors saveTable(HttpServletRequest request,
                                       ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    
    checkDistCostAndCatalogPrice(ae, factory.getStoreAPI(), storeId, pForm, uploadSkusVw); // bug #852, 2572 fix
    
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
      //

      String value = usD.getListPrice();
      if (Utility.isSet(value)) {
        try {
          double doubleVal = Double.parseDouble(value);
        } catch (Exception exc) {
          String errorMess = "Invalid List Price: " + value;
          ae.add("errorlp" + ii,
                 new ActionError("error.simpleGenericError", errorMess));
        }
      }
      value = usD.getDistCost();
      if (Utility.isSet(value)) {
        try {
          double doubleVal = Double.parseDouble(value);
        } catch (Exception exc) {
          String errorMess = "Invalid Distr. Cost: " + value;
          ae.add("errordc" + ii,
                 new ActionError("error.simpleGenericError", errorMess));
        }
      }
      value = usD.getCatalogPrice();
      if (Utility.isSet(value)) {
        try {
          double doubleVal = Double.parseDouble(value);
        } catch (Exception exc) {
          String errorMess = "Invalid Catalog Price: " + value;
          ae.add("errorcp" + ii,
                 new ActionError("error.simpleGenericError", errorMess));
        }
      }
      value = usD.getMfcp();
      if (Utility.isSet(value)) {
        try {
          double doubleVal = Double.parseDouble(value);
        } catch (Exception exc) {
          String errorMess = "Invalid MFCP: " + value;
          ae.add("errormfcp" + ii,
                 new ActionError("error.simpleGenericError", errorMess));
        }
      }
      value = usD.getBaseCost();
      if (Utility.isSet(value)) {
        try {
          double doubleVal = Double.parseDouble(value);
        } catch (Exception exc) {
          String errorMess = "Invalid Base Cost: " + value;
          ae.add("errorbc" + ii,
                 new ActionError("error.simpleGenericError", errorMess));
        }
      }
      value = usD.getDistUomMult();
      if (Utility.isSet(value)) {
        try {
          int doubleVal = Integer.parseInt(value);
        } catch (Exception exc) {
          String errorMess =
            "Invalid Dist. UOM Multiplier (should be integer): " + value;
          ae.add("errorbc" + ii,
                 new ActionError("error.simpleGenericError", errorMess));
        }
      }
      value = usD.getSpl();
      if (Utility.isSet(value) &&
          !"yes".equalsIgnoreCase(value) &&
          !"no".equalsIgnoreCase(value)) {
        String errorMess = "Invalid SPL value: " + value;
        ae.add("errorspl" + ii,
               new ActionError("error.simpleGenericError", errorMess));
      }
      value = usD.getTaxExempt();
      if (Utility.isSet(value) &&
          !"yes".equalsIgnoreCase(value) &&
          !"no".equalsIgnoreCase(value)) {
        String errorMess = "Invalid Tax Exempt value: " + value;
        ae.add("errortaxexempt" + ii,
               new ActionError("error.simpleGenericError", errorMess));
      }
      value = usD.getShipWeight();
      if (Utility.isSet(value)) {
        try {
          double doubleVal = Double.parseDouble(value);
        } catch (Exception exc) {
          String errorMess = "Invalid Shipping Weight: " + value;
          ae.add("errorcp" + ii,
               new ActionError("error.simpleGenericError", errorMess));
        }
      }
      value = usD.getWeightUnit();
      ListService lsvc = factory.getListServiceAPI();
      RefCdDataVector weightUnitV = lsvc.getRefCodesCollection("WEIGHT_UNIT");
      if (Utility.isSet(value) &&
          !Utility.refCdDataVectorContains(weightUnitV, value) ) {
          String errorMess = "Invalid Weight Unit value: " + value;
          ae.add("errortaxexempt" + ii,
                 new ActionError("error.simpleGenericError", errorMess));
      }
    }
    if (ae.size() > 0) {
      return ae;
    }

    ItemInformation itemEjb = factory.getItemInformationAPI();
    XlsTableView xlsTableVw = XlsTableView.createValue();
    UploadData uD = pForm.getTableHeader();
    uD.setNote(pForm.getNote());
    uD.setUploadStatusCd(pForm.getTableStatus());
    xlsTableVw.setHeader(uD);        
    xlsTableVw.setContent(uploadSkusVw);
    int uploadId = uD.getUploadId();
    int newUploadId = 0;
    try {
      newUploadId = itemEjb.saveSkuXlsTable(storeId, xlsTableVw, true, userName);
      ae = loadTable(request, form, newUploadId);
      if (ae.size() > 0) {
        return ae;
      }
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }
    /*
           uD = pForm.getTableHeader();
           int ind = 0;
           if(uploadId>0 && uploadId!=newUploadId) {
      UploadDataVector tableList = pForm.getTableList();
      if(tableList!=null) {
        for(Iterator iter = tableList.iterator(); iter.hasNext();ind++) {
          UploadData uD1 = (UploadData) iter.next();
          if(uD1.getUploadId()==uploadId) {
            iter.remove();
            tableList.add(ind, uD);
            break;
          }
        }
      }
           }

           pForm.setUploadSkus(xlsTableVw.getContent());
     */
    return ae;
  }

  public static ActionErrors match(HttpServletRequest request,
                                   ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    UploadData uD = pForm.getTableHeader();
    int uploadId = uD.getUploadId();
    UploadSkuViewVector itemsToMatch = null;
    IdVector uploadSkuIdV = getUploadSkuIds(form);

    try {
      itemsToMatch = itemEjb.matchSkuXlsTable(storeId, uploadId, uploadSkuIdV);
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    if (itemsToMatch == null) itemsToMatch = new UploadSkuViewVector();
    Iterator iter = itemsToMatch.iterator();
    UploadSkuView usMatchVw = null;

    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      UploadSkuData usD = usVw.getUploadSku();
      int rowNum = usD.getRowNum();
      String skuNum = usVw.getSkuNum();
      boolean newSkuFl = true;
      boolean createFl = Utility.isSet(skuNum) ? false : true;
      usVw.setSelectFlag(createFl);

      while (usMatchVw != null || iter.hasNext()) {
        if (usMatchVw == null) {
          usMatchVw = (UploadSkuView) iter.next();
          usMatchVw.setSelectFlag(false);
        }
        UploadSkuData usMatchD = usMatchVw.getUploadSku();
        int rowNumMatch = usMatchD.getRowNum();
        if (rowNumMatch < rowNum) {
          usMatchVw = null;
          continue;
        }
        if (rowNumMatch > rowNum) {
          break;
        }
        if (newSkuFl && createFl) {
          usMatchVw.setSelectFlag(true);
          usVw.setSelectFlag(false);
        }
        newSkuFl = false;
        usMatchVw = null;
      }
    }
    pForm.setItemsToMatch(itemsToMatch);
    return ae;
  }

  private static IdVector getUploadSkuIds(ActionForm form) {
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    if (uploadSkusVw == null) {
      return null;
    }
    IdVector uploadSkuIdV = new IdVector();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
      int uploadSkuId = usD.getUploadSkuId();
      if (uploadSkuId > 0) {
        uploadSkuIdV.add(new Integer(uploadSkuId));
      }
    }
    return uploadSkuIdV;
  }

  public static ActionErrors showMatched(HttpServletRequest request,
                                         ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    UploadData uD = pForm.getTableHeader();
    int uploadId = uD.getUploadId();
    UploadSkuViewVector itemsToMatch = null;
    try {
      itemsToMatch = itemEjb.getMatchedItems(storeId, uploadId,
                                             getUploadSkuIds(form));
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    if (itemsToMatch == null) itemsToMatch = new UploadSkuViewVector();
    Iterator iter = itemsToMatch.iterator();
    UploadSkuView usMatchVw = null;

    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      UploadSkuData usD = usVw.getUploadSku();
      int rowNum = usD.getRowNum();
      String skuNum = usVw.getSkuNum();
      boolean newSkuFl = true;
      boolean createFl = Utility.isSet(skuNum) ? false : true;
      usVw.setSelectFlag(createFl);

      while (usMatchVw != null || iter.hasNext()) {
        if (usMatchVw == null) {
          usMatchVw = (UploadSkuView) iter.next();
          usMatchVw.setSelectFlag(false);
        }
        UploadSkuData usMatchD = usMatchVw.getUploadSku();
        int rowNumMatch = usMatchD.getRowNum();
        if (rowNumMatch < rowNum) {
          usMatchVw = null;
          continue;
        }
        if (rowNumMatch > rowNum) {
          break;
        }
        if (newSkuFl && createFl) {
          usMatchVw.setSelectFlag(true);
          usVw.setSelectFlag(false);
        }
        newSkuFl = false;
        usMatchVw = null;
      }
    }
    pForm.setItemsToMatch(itemsToMatch);
    return ae;
  }

  public static ActionErrors assignSkus(HttpServletRequest request,
                                        ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    UploadData uD = pForm.getTableHeader();
    int uploadId = uD.getUploadId();
    UploadSkuViewVector itemsToMatch = pForm.getItemsToMatch();

    if (itemsToMatch == null) itemsToMatch = new UploadSkuViewVector();
    int matchedQty = 0;

    Iterator iter = itemsToMatch.iterator();
    UploadSkuView usMatchVw = null;

    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      UploadSkuData usD = usVw.getUploadSku();
      int rowNum = usD.getRowNum();
      String skuNum = usVw.getSkuNum();
      boolean createFl = usVw.getSelectFlag();
      boolean setFl = false;

      while (usMatchVw != null || iter.hasNext()) {
        if (usMatchVw == null) {
          usMatchVw = (UploadSkuView) iter.next();
        }
        UploadSkuData usMatchD = usMatchVw.getUploadSku();
        int rowNumMatch = usMatchD.getRowNum();
        if (rowNumMatch < rowNum) {
          usMatchVw = null;
          continue;
        }
        if (rowNumMatch > rowNum) {
          break;
        }
        boolean assignFl = usMatchVw.getSelectFlag();
        if (assignFl) {
          if (createFl) {
            String errorMess =
              "Can't assign sku when selected to create. Row num: " + rowNum;
            ae.add("error",
                   new ActionError("error.simpleGenericError", errorMess));
            return ae;
          } else if (setFl) {
            String errorMess = "Multiple skus selected to assign. Row num: " +
                               rowNum;
            ae.add("error",
                   new ActionError("error.simpleGenericError", errorMess));
            return ae;
          } else {
            setFl = true;
            matchedQty++;
            usD.setItemId(usMatchVw.getUploadSku().getItemId());
            usVw.setSkuNum(usMatchVw.getSkuNum());
          }
        }
        usMatchVw = null;
      }
    }
    if (matchedQty == 0) {
      String errorMess = "No sku to assign selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    try {
      itemEjb.updateUploadSkus(uploadSkusVw, userName);
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    pForm.setUploadSkus(uploadSkusVw);
    pForm.setItemsToMatch(null);
    return ae;
  }


  public static ActionErrors removeAssignment(HttpServletRequest request,
                                              ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    UploadData uD = pForm.getTableHeader();
    int uploadId = uD.getUploadId();
    UploadSkuViewVector itemsToMatch = pForm.getItemsToMatch();

    if (itemsToMatch == null) itemsToMatch = new UploadSkuViewVector();
    int matchedQty = 0;

    Iterator iter = itemsToMatch.iterator();
    UploadSkuView usMatchVw = null;

    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    int qty = 0;
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      UploadSkuData usD = usVw.getUploadSku();
      if (usVw.getSelectFlag()) {
        usD.setItemId(0);
        usVw.setSkuNum(null);
        qty++;
      }
    }
    if (qty == 0) {
      String errorMess = "No sku selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    try {
      itemEjb.updateUploadSkus(uploadSkusVw, userName);
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    pForm.setUploadSkus(uploadSkusVw);
    pForm.setItemsToMatch(null);
    return ae;
  }

  public static ActionErrors createSkus(HttpServletRequest request,
                                        ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufEjb = factory.getManufacturerAPI();
    Distributor distEjb = factory.getDistributorAPI();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    PropertyDataVector propertyDV = storeD.getMiscProperties();
    String erpSystem = null;
    String autoSkuFlagS = null;
    for (Iterator iter = propertyDV.iterator(); iter.hasNext(); ) {
      PropertyData pD = (PropertyData) iter.next();
      if (RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM.equals(pD.getPropertyTypeCd())) {
        erpSystem = pD.getValue();
      }
      if (RefCodeNames.PROPERTY_TYPE_CD.AUTO_SKU_ASSIGN.equals(pD.
        getPropertyTypeCd())) {
        autoSkuFlagS = pD.getValue();
      }
    }
    int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);

    if (autoSkuFlagS == null) autoSkuFlagS = "";
    boolean autoSkuFlag = false;
    if (Utility.isTrue(autoSkuFlagS)) {
      autoSkuFlag = true;
    }
    if (!autoSkuFlag) {
      String errorMess =
        "The store doesn't allow automatic sku number generating";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
//      return ae;
    }
    CatalogCategoryDataVector categoryDV =
      catalogInfEjb.getAllStoreCatalogCategories(storeCatalogId);
    HashMap categoryHM = new HashMap();
    for (Iterator iter = categoryDV.iterator(); iter.hasNext(); ) {
      CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
      categoryHM.put(new CategoryKey(ccD.getCatalogCategoryShortDesc(), 
          ccD.getCatalogCategoryLongDesc()), ccD);
      if(ccD.getCatalogCategoryLongDesc() != null){
	      categoryHM.put(new CategoryKey(ccD.getCatalogCategoryShortDesc() + "("+ccD.getCatalogCategoryLongDesc()+")", null), ccD);
	      categoryHM.put(new CategoryKey(ccD.getCatalogCategoryShortDesc() + " ("+ccD.getCatalogCategoryLongDesc()+")", null), ccD);
	      categoryHM.put(new CategoryKey(ccD.getCatalogCategoryShortDesc() + "-"+ccD.getCatalogCategoryLongDesc(), null), ccD);
      }
    }
    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    IdVector storeIdAsV = new IdVector();
    storeIdAsV.add(new Integer(storeId));
    besc.setStoreBusEntityIds(storeIdAsV);
    ManufacturerDataVector manufDV = manufEjb.getManufacturerByCriteria(besc);

    HashMap manufHM = new HashMap();
    for (Iterator iter = manufDV.iterator(); iter.hasNext(); ) {
      ManufacturerData mD = (ManufacturerData) iter.next();
      manufHM.put(mD.getBusEntity().getShortDesc(), mD);
    }
    DistributorDataVector distDV = distEjb.getDistributorByCriteria(besc);

    HashMap distHM = new HashMap();
    for (Iterator iter = distDV.iterator(); iter.hasNext(); ) {
      DistributorData dD = (DistributorData) iter.next();
      distHM.put(dD.getBusEntity().getShortDesc(), dD);
    }
    checkItemsForCreate(pForm, ae, categoryHM, manufHM, distHM, storeCatalogId);
    if (ae.size() > 0) {
        return ae;
    }
    UploadData uD = pForm.getTableHeader();
    int uploadId = uD.getUploadId();
    UploadSkuViewVector itemsToMatch = pForm.getItemsToMatch();

    //manufacturerEjb.get


    //Check errors
    if (itemsToMatch == null) itemsToMatch = new UploadSkuViewVector();
    int qty = 0;

    Iterator iter = itemsToMatch.iterator();
    UploadSkuView usMatchVw = null;
    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      UploadSkuData usD = usVw.getUploadSku();
      int rowNum = usD.getRowNum();
      String skuNum = usVw.getSkuNum();
      boolean createFl = usVw.getSelectFlag();
      if (createFl) qty++;
      if (Utility.isSet(skuNum) && createFl) {
        String errorMess =
          "Sku already assigned. Remove assigment first. Row num: " + rowNum;
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      } while (usMatchVw != null || iter.hasNext()) {
        if (usMatchVw == null) {
          usMatchVw = (UploadSkuView) iter.next();
        }
        UploadSkuData usMatchD = usMatchVw.getUploadSku();
        int rowNumMatch = usMatchD.getRowNum();
        if (rowNumMatch < rowNum) {
          usMatchVw = null;
          continue;
        }
        if (rowNumMatch > rowNum) {
          break;
        }
        boolean assignFl = usMatchVw.getSelectFlag();
        if (assignFl) {
          if (createFl) {
            String errorMess =
              "Can't create sku when sku to assign is selected. Row num: " +
              rowNum;
            ae.add("error",
                   new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }
        }
        usMatchVw = null;
      }
    }
    if (qty == 0) {
      String errorMess = "No sku selected";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    //Create items
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      UploadSkuData usD = usVw.getUploadSku();
      int rowNum = usD.getRowNum();
      boolean createFl = usVw.getSelectFlag();
      if (!createFl)continue;
      ProductData productD = new ProductData();
      ae = generateProductData(productD, usVw, manufHM, distHM, categoryHM,
                               storeCatalogId, pForm.getAllowMixedCategoryAndItemUnderSameParent());
      if (ae.size() > 0) {
        return ae;
      }
      try {
          Map<String, byte[]> externalData = loadExternalData(usD, ae, rowNum);
          trySearchAndStoreGreenCertified(productD, usD, ae, factory.getProductInformationAPI());
          if (ae.size() > 0) {
              return ae;
            }
          productD = catalogEjb.saveStoreCatalogProduct(storeId,
                  storeCatalogId, productD, userName);
          if (externalData != null && externalData.isEmpty() == false) {
            storeExternalData(productD, usD, externalData, ae);
            productD = catalogEjb.saveStoreCatalogProduct(storeId,
                          storeCatalogId, productD, userName);
          }
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
      try {
        UploadSkuView[] usVwA = new UploadSkuView[1];
        usD.setItemId(productD.getProductId());
        usVw.setSkuNum(productD.getCatalogSkuNum());
        usVwA[0] = usVw;
        itemEjb.updateUploadSkus(usVwA, userName);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
    }
    pForm.setUploadSkus(uploadSkusVw);
    pForm.setItemsToMatch(null);
    return ae;
  }


  public static ActionErrors updateSkus(HttpServletRequest request,
                                        ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();

    UploadData uD = pForm.getTableHeader();
    int uploadId = uD.getUploadId();
    UploadSkuViewVector itemsToMatch = null;

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    try {
      itemsToMatch = itemEjb.getMatchedItems(storeId, uploadId, getUploadSkuIds(form));
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    if (itemsToMatch == null) itemsToMatch = new UploadSkuViewVector();

    Iterator iter = itemsToMatch.iterator();
    UploadSkuView usMatchVw = null;

    UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();

    int qty = 0;
    Set<Integer> notAssigned = new TreeSet<Integer>();
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      UploadSkuData usD = usVw.getUploadSku();
      int rowNum = usD.getRowNum();
      String skuNum = usVw.getSkuNum();
      boolean updateFl = usVw.getSelectFlag();
      if (!updateFl) continue;
      if (updateFl) qty++;
      if (!Utility.isSet(skuNum)) {
        String errorMess =
          "Sku not yet assigned. Assign it first. Row num: " + rowNum;
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        notAssigned.add(ii);
//        return ae;
      }
      while (usMatchVw != null || iter.hasNext()) {
        if (usMatchVw == null) {
          usMatchVw = (UploadSkuView) iter.next();
        }
        UploadSkuData usMatchD = usMatchVw.getUploadSku();
        int rowNumMatch = usMatchD.getRowNum();
        if (rowNumMatch < rowNum) {
          usMatchVw = null;
          continue;
        }
        if (rowNumMatch > rowNum) {
          break;
        }

        boolean assignFl = usMatchVw.getSelectFlag();
        if (assignFl && updateFl) {
          String errorMess = "Can't create sku when sku to assign is selected. Row num: " + rowNum;
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
//          return ae;
        }
        usMatchVw = null;
      }
    }
    if (qty == 0) {
      String errorMess = "No sku selected for update";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
//      return ae;
    }


    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Manufacturer manufEjb = factory.getManufacturerAPI();
    Distributor distEjb = factory.getDistributorAPI();
    int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);


    CatalogCategoryDataVector categoryDV =
      catalogInfEjb.getAllStoreCatalogCategories(storeCatalogId);
    HashMap categoryHM = new HashMap();
    for (Iterator it = categoryDV.iterator(); it.hasNext(); ) {
      CatalogCategoryData ccD = (CatalogCategoryData) it.next();
      categoryHM.put(new CategoryKey(ccD.getCatalogCategoryShortDesc(), 
          ccD.getCatalogCategoryLongDesc()), ccD);
      if(ccD.getCatalogCategoryLongDesc() != null){
	      categoryHM.put(new CategoryKey(ccD.getCatalogCategoryShortDesc() + "("+ccD.getCatalogCategoryLongDesc()+")", null), ccD);
	      categoryHM.put(new CategoryKey(ccD.getCatalogCategoryShortDesc() + "-"+ccD.getCatalogCategoryLongDesc(), null), ccD);
      }
    }

    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    IdVector storeIdAsV = new IdVector();
    storeIdAsV.add(new Integer(storeId));
    besc.setStoreBusEntityIds(storeIdAsV);
    ManufacturerDataVector manufDV = manufEjb.getManufacturerByCriteria(besc);

    HashMap manufHM = new HashMap();
    for (Iterator it = manufDV.iterator(); it.hasNext(); ) {
      ManufacturerData mD = (ManufacturerData) it.next();
      manufHM.put(mD.getBusEntity().getShortDesc(), mD);
    }

    DistributorDataVector distDV = distEjb.getDistributorByCriteria(besc);

    HashMap distHM = new HashMap();
    for (Iterator it = distDV.iterator(); it.hasNext(); ) {
      DistributorData dD = (DistributorData) it.next();
      distHM.put(dD.getBusEntity().getShortDesc(), dD);
    }
    checkItemsForUpdate(ae, pForm, categoryHM, manufHM, distHM, storeCatalogId, itemEjb, catalogInfEjb, storeD, notAssigned);
    if (ae.size() > 0) {
        return ae;
    }
    // get erp system
    PropertyDataVector propertyDV = storeD.getMiscProperties();
    String erpSystem = null;
    for (Iterator it = propertyDV.iterator(); it.hasNext(); ) {
      PropertyData pD = (PropertyData) it.next();
      if (RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM.equals(pD.getPropertyTypeCd())) {
        erpSystem = pD.getValue();
      }
    }

    //update items
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuView usVw = uploadSkusVw[ii];
      boolean updateFl = usVw.getSelectFlag();
      if (!updateFl) continue;
      UploadSkuData usD = usVw.getUploadSku();
      int itemId = usD.getItemId();
      ProductData productD = null;
      try {
        productD = catalogInfEjb.getStoreProduct(storeId, itemId);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
      ae = generateProductDataForUpdate(productD, usVw, manufHM, distHM, categoryHM, 
    		  storeCatalogId, pForm.getAllowMixedCategoryAndItemUnderSameParent());
      if (ae.size() > 0) {
        return ae;
      }
      try {
        Map<String, byte[]> externalData = loadExternalData(usD, ae, usD.getRowNum());
        if (ae.size() > 0) {
            return ae;
        }  
        productD = catalogEjb.saveStoreCatalogProduct(storeId,
                storeCatalogId, productD, userName);
        if (externalData != null && externalData.isEmpty() == false) {
            storeExternalData(productD, usD, externalData, ae);
            productD = catalogEjb.saveStoreCatalogProduct(storeId,
                    storeCatalogId, productD, userName);
        }
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
      try {
        UploadSkuView[] usVwA = new UploadSkuView[1];
        usD.setItemId(productD.getProductId());
        usVw.setSkuNum(productD.getCatalogSkuNum());
        usVwA[0] = usVw;
        itemEjb.updateUploadSkus(usVwA, userName);
      } catch (Exception exc) {
        String mess = exc.getMessage();
        if (mess == null) mess = "";
        int ind = mess.indexOf("^clw^");
        if (ind >= 0) {
          mess = mess.substring(ind + "^clw^".length());
          ind = mess.indexOf("^clw^");
          if (ind > 0) mess = mess.substring(0, ind);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          return ae;
        } else {
          throw exc;
        }
      }
    }
    pForm.setUploadSkus(uploadSkusVw);
    pForm.setItemsToMatch(null);
    return ae;
  }


  static private ActionErrors generateProductData(ProductData productD,
                                                  UploadSkuView uploadSkuVw,
                                                  HashMap manufHM,
                                                  HashMap distHM,
                                                  HashMap categoryHM,
                                                  int storeCatalogId,
                                                  boolean allowMixedCategoryAndItemUnderSameParent) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    UploadSkuData usD = uploadSkuVw.getUploadSku();
    String shortDesc = usD.getShortDesc();
    int rowNum = usD.getRowNum();

    ItemData iD = ItemData.createValue();
    iD.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.PRODUCT);
    iD.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
    productD.setItemData(iD);

    CatalogStructureData csD = CatalogStructureData.createValue();
    csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
    csD.setCatalogId(storeCatalogId);

    productD.setCatalogStructure(csD);

    productD.setCustomerSkuNum(usD.getCustomerSkuNum());
    productD.setSkuNum(0);

    if (!Utility.isSet(shortDesc)) {
      String errorMess = "No short description found. Row number: " + rowNum;
      ae.add("errorShortDesc",
             new ActionError("error.simpleGenericError", errorMess));
    }
    productD.setShortDesc(shortDesc);

    String longDesc = usD.getLongDesc();
    if (!Utility.isSet(longDesc)) longDesc = shortDesc;
    productD.setLongDesc(longDesc);

    //set other desc
    String otherDesc = usD.getOtherDesc();
    if (Utility.isSet(otherDesc)) {
      productD.setOtherDesc(otherDesc);
    }

    //set size
    String size = usD.getSkuSize();
    if (!Utility.isSet(size)) {
      String errorMess = "No sku size found. Row number: " + rowNum;
      ae.add("errorSkuSize",
             new ActionError("error.simpleGenericError", errorMess));
    }
    productD.setSize(size);

    //set pack
    String pack = usD.getDistPack();
    if (!Utility.isSet(pack)) {
      pack = usD.getSkuPack();
    }
    if (!Utility.isSet(pack)) {
      pack = usD.getManufPack();
    }
    if (!Utility.isSet(pack)) {
      String errorMess = "No sku pack info found. Row number: " + rowNum;
      ae.add("errorSkuPack",
             new ActionError("error.simpleGenericError", errorMess));
    }
    productD.setPack(pack);

    //set uom
    String uom = usD.getDistUom();
    if (!Utility.isSet(uom)) {
      uom = usD.getSkuUom();
    }
    if (!Utility.isSet(uom)) {
      uom = usD.getManufUom();
    }
    if (!Utility.isSet(uom)) {
      String errorMess = "No sku uom found. Row number: " + rowNum;
      ae.add("errorSkuUom",
             new ActionError("error.simpleGenericError", errorMess));
    }
    productD.setUom(uom);

    //set color
    String color = usD.getSkuColor();
    if (Utility.isSet(color)) {
      productD.setColor(color);
    }

    //set unspsc
    String unspscCode = usD.getUnspscCode();
    if (Utility.isSet(unspscCode)) {
      productD.setUnspscCd(unspscCode);
    }

    //set nsn
    String nsn = usD.getNsn();
    if (Utility.isSet(nsn)) {
      productD.setNsn(nsn);
    }

    //set psn
    String psn = usD.getPsn();
    if (Utility.isSet(psn)) {
      productD.setPsn(psn);
    }

    //set manuf
    String manufName = usD.getManufName();
    ManufacturerData manufD = (ManufacturerData) manufHM.get(manufName);
    if (manufD == null) {
      String errorMess = "No matched system manufacturer found. Row number: " +
                         rowNum;
      ae.add("errorManufName",
             new ActionError("error.simpleGenericError", errorMess));
    }

    String manufSku = usD.getManufSku();
    if (!Utility.isSet(manufSku)) {
      String errorMess = "No manufacturer sku found. Row number: " + rowNum;
      ae.add("errorManufSku",
             new ActionError("error.simpleGenericError", errorMess));
    }
    if (manufD != null && Utility.isSet(manufSku)) {
      productD.setManufacturer(manufD.getBusEntity());
      productD.setManuMapping(manufD.getBusEntity(), manufSku);
    }

    //set distributor
    String distName = usD.getDistName();
    if (Utility.isSet(distName)) {
      distName = distName.trim();
      DistributorData distD = (DistributorData) distHM.get(distName);
      if (distD == null) {
        String errorMess = "No matched system distributor found. Distributor: " +
                           distName + " Row number: " + rowNum;
        ae.add("errorDistName",
               new ActionError("error.simpleGenericError", errorMess));
      } else {
        int distId = distD.getBusEntity().getBusEntityId();
        String distSku = usD.getDistSku();
        String distPack = usD.getDistPack();
        String distUom = usD.getDistUom();
        if (Utility.isSet(distSku)) {
          ItemMappingData distItemMapD = ItemMappingData.createValue();
          distItemMapD.setBusEntityId(distId);
          distItemMapD.setItemId(productD.getItemData().getItemId());
          distItemMapD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.
                                        ITEM_DISTRIBUTOR);
          distItemMapD.setItemNum(distSku);
          distItemMapD.setItemPack(distPack);
          distItemMapD.setItemUom(distUom);
          distItemMapD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
          String distUomMultS = usD.getDistUomMult();
          if (distUomMultS == null) distUomMultS = "1";
          distItemMapD.setUomConvMultiplier(new BigDecimal(distUomMultS));
          distItemMapD.setStandardProductList(usD.getSpl());
          productD.addMappedDistributor(distD.getBusEntity());
          productD.addDistributorMapping(distItemMapD);
        }
      }
    }

    //set category
    String skuCateg = usD.getCategory();
    String skuAdminCateg = usD.getAdminCategory();
    if (!Utility.isSet(skuCateg)) {
      String errorMess = "No item category found. Row number: " + rowNum;
      ae.add("errorSkuCategory",
             new ActionError("error.simpleGenericError", errorMess));
    } else {
	  skuCateg = skuCateg.trim();
	  if(skuAdminCateg != null){
	    skuAdminCateg=skuAdminCateg.trim();
	  }
      CatalogCategoryData categoryD = (CatalogCategoryData) categoryHM.get(new CategoryKey(skuCateg, skuAdminCateg));
      if (categoryD == null) {
	    
        String errorMess = "No store category found. Category: '" + skuCateg +
            "', admin category: '" + skuAdminCateg + "'. Row number: " + rowNum;
        ae.add("errorStoreCategory",
               new ActionError("error.simpleGenericError", errorMess));
      } else {
    	  if (!allowMixedCategoryAndItemUnderSameParent && !categoryD.isLowestLevel()) {
    		  String errorMess = "Category is a parent category. A parent category cannot be assigned to a Master Item. Select the appropriate child category. Row number: " + rowNum;
    		  ae.add("errorCategory", new ActionError("error.simpleGenericError", errorMess));
    	  }else{
    		  CatalogCategoryDataVector productCcDV = new CatalogCategoryDataVector();
    		  productCcDV.add(categoryD);
    		  productD.setCatalogCategories(productCcDV);
    	  }
      }
    }

    //Set effective and expiration dates
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date effDate = sdf.parse(sdf.format(new Date()));
    productD.setEffDate(effDate);
    productD.setExpDate(null);

    //Set list price
    String listPriceS = usD.getListPrice();
    if (Utility.isSet(listPriceS)) {
      try {
        double listPriceDb = Double.parseDouble(listPriceS);
        productD.setListPrice(listPriceDb);
      } catch (Exception exc) {
        String errorMess = "Invalid list price: " + listPriceS +
                           " Row number: " + rowNum;
        ae.add("errorListPrice",
               new ActionError("error.simpleGenericError", errorMess));
      }
    } else {
      productD.setListPrice(0);
    }

    //Set mfcp (cost price)
    String mfcpS = usD.getMfcp();
    if (Utility.isSet(mfcpS)) {
      try {
        double mfcpDb = Double.parseDouble(mfcpS);
        productD.setCostPrice(mfcpDb);
      } catch (Exception exc) {
        String errorMess = "Invalid mfcp: " + mfcpS + " Row number: " + rowNum;
        ae.add("errorDistCost",
               new ActionError("error.simpleGenericError", errorMess));
      }
    } else {
      productD.setCostPrice(0);
    }

    // set customer description
    String custDesc = usD.getCustomerDesc();
    if (Utility.isSet(custDesc)) {
      productD.setCustomerProductShortDesc(custDesc);
    }
      
    // set shipping weight and weight unit
    String shipWeight = usD.getShipWeight();
    if (Utility.isSet(shipWeight)) {
        productD.setShipWeight(shipWeight);
    }
    String weightUnit = usD.getWeightUnit();
    if (Utility.isSet(weightUnit)) {
        productD.setWeightUnit(weightUnit);
    }

    return ae;
  }


  static private ActionErrors generateProductDataForUpdate(ProductData productD,
                                                  UploadSkuView uploadSkuVw,
                                                  HashMap manufHM,
                                                  HashMap distHM,
                                                  HashMap categoryHM,
                                                  int storeCatalogId, 
                                                  boolean allowMixedCategoryAndItemUnderSameParent) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    UploadSkuData usD = uploadSkuVw.getUploadSku();
    String shortDesc = usD.getShortDesc();
    int rowNum = usD.getRowNum();

    if (Utility.isSet(shortDesc)) {
      productD.setShortDesc(shortDesc);
    }

    String longDesc = usD.getLongDesc();
    if (Utility.isSet(longDesc)) {
      productD.setLongDesc(longDesc);
    }

    String otherDesc = usD.getOtherDesc();
    if (Utility.isSet(otherDesc)) {
      productD.setOtherDesc(otherDesc);
    }

    String size = usD.getSkuSize();
    if (Utility.isSet(size)) {
      productD.setSize(size);
    }

    String pack = usD.getSkuPack();
    if (Utility.isSet(pack)) {
      productD.setPack(pack);
    }

    String uom = usD.getSkuUom();
    if (Utility.isSet(uom)) {
      productD.setUom(uom);
    }

    String color = usD.getSkuColor();
    if (Utility.isSet(color)) {
      productD.setColor(color);
    }

    String unspscCode = usD.getUnspscCode();
    if (Utility.isSet(unspscCode)) {
      productD.setUnspscCd(unspscCode);
    }

    String nsn = usD.getNsn();
    if (Utility.isSet(nsn)) {
      productD.setNsn(nsn);
    }

    String psn = usD.getPsn();
    if (Utility.isSet(psn)) {
      productD.setPsn(psn);
    }

    String manufName = usD.getManufName();
    ManufacturerData manufD = (ManufacturerData) manufHM.get(manufName);
    String manufSku = usD.getManufSku();
    if (manufD != null && Utility.isSet(manufSku)) {
      productD.setManufacturer(manufD.getBusEntity());
      productD.setManuMapping(manufD.getBusEntity(), manufSku);
    }

    String distName = usD.getDistName();
    if (Utility.isSet(distName)) {
      distName = distName.trim();
      DistributorData distD = (DistributorData) distHM.get(distName);
      if (distD != null) {
        int distId = distD.getBusEntity().getBusEntityId();
        String distSku = usD.getDistSku();
        String distPack = usD.getDistPack();
        String distUom = usD.getDistUom();
        if (Utility.isSet(distSku)) {
          productD.addMappedDistributor(distD.getBusEntity());
          ItemMappingDataVector distMappingV = productD.getDistributorMappings();

          // add/update dist mapping
          String distUomMultS = usD.getDistUomMult();
          if (distUomMultS == null) distUomMultS = "1";

          boolean found = false;
          for (int i=0; i<distMappingV.size(); i++) {
            ItemMappingData baseDistItemMapD = (ItemMappingData)distMappingV.get(i);
            if (null == baseDistItemMapD.getItemNum()) {
                baseDistItemMapD.setItemNum("");
            }
            if (baseDistItemMapD.getBusEntityId() == (distId)) {
                // update dist mapping
                baseDistItemMapD.setItemNum(distSku);
                baseDistItemMapD.setItemPack(distPack);
                baseDistItemMapD.setItemUom(distUom);
                baseDistItemMapD.setUomConvMultiplier(new BigDecimal(distUomMultS));
                baseDistItemMapD.setStandardProductList(usD.getSpl());
                found = true;
                break;
            }
          }
          if ( !found ) {
             // add dist mapping
             ItemMappingData distItemMapD = ItemMappingData.createValue();
             distItemMapD.setBusEntityId(distId);
             distItemMapD.setItemId(productD.getItemData().getItemId());
             distItemMapD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
             distItemMapD.setItemNum(distSku);
             distItemMapD.setItemPack(distPack);
             distItemMapD.setItemUom(distUom);
             distItemMapD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
             distItemMapD.setUomConvMultiplier(new BigDecimal(distUomMultS));
             distItemMapD.setStandardProductList(usD.getSpl());
             productD.addDistributorMapping(distItemMapD);
          }
        }
      }
    }

    String skuCateg = usD.getCategory();
    String skuAdminCateg = usD.getAdminCategory();
    if (Utility.isSet(skuCateg)) {
      CatalogCategoryData categoryD = (CatalogCategoryData) categoryHM.get(
          new CategoryKey(skuCateg, skuAdminCateg));
      if (categoryD != null) {
    	  if (!allowMixedCategoryAndItemUnderSameParent && !categoryD.isLowestLevel()) {
    		  String errorMess = "Category is a parent category. A parent category cannot be assigned to a Master Item. Select the appropriate child category. Row number: " + rowNum;
    		  ae.add("errorCategory", new ActionError("error.simpleGenericError", errorMess));
    	  }else{
    		  CatalogCategoryDataVector productCcDV = new CatalogCategoryDataVector();
    		  productCcDV.add(categoryD);
    		  productD.setCatalogCategories(productCcDV);
    	  }
      }else{
    	  String errorMess = "Invalid Category: " + skuCateg + " Row number: " + rowNum;
    	  ae.add("errorCategory", new ActionError("error.simpleGenericError", errorMess));
      }
    }

    //Set effective and expiration dates
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date effDate = sdf.parse(sdf.format(new Date()));
    productD.setEffDate(effDate);

    //Set list price
    String listPriceS = usD.getListPrice();
    if (Utility.isSet(listPriceS)) {
      try {
        double listPriceDb = Double.parseDouble(listPriceS);
        productD.setListPrice(listPriceDb);
      } catch (Exception exc) {
        String errorMess = "Invalid list price: " + listPriceS +
                           " Row number: " + rowNum;
        ae.add("errorListPrice",
               new ActionError("error.simpleGenericError", errorMess));
      }
    }

    //Set mfcp (cost price)
    String mfcpS = usD.getMfcp();
    if (Utility.isSet(mfcpS)) {
      try {
        double mfcpDb = Double.parseDouble(mfcpS);
        productD.setCostPrice(mfcpDb);
      } catch (Exception exc) {
        String errorMess = "Invalid mfcp: " + mfcpS + " Row number: " + rowNum;
        ae.add("errorDistCost",
               new ActionError("error.simpleGenericError", errorMess));
      }
    }

    // set customer description
    String custDesc = usD.getCustomerDesc();
    if (Utility.isSet(custDesc)) {
      productD.setCustomerProductShortDesc(custDesc);
    }

    // set shipping weight and weight unit
    String shipWeight = usD.getShipWeight();
    if (Utility.isSet(shipWeight)) {
        productD.setShipWeight(shipWeight);
    }
    String weightUnit = usD.getWeightUnit();
    if (Utility.isSet(weightUnit)) {
        productD.setWeightUnit(weightUnit);
    }
      
    return ae;
  }



  public static ActionErrors edit(HttpServletRequest request,
                                  ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    String uploadIdS = request.getParameter("id");
    int uploadId = 0;
    try {
      uploadId = Integer.parseInt(uploadIdS);
    } catch (Exception exc) {}
    if (uploadId <= 0) {
      String errorMess = "Invalid table id: " + uploadIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    ae = loadTable(request, form, uploadId);
    return ae;
  }

  public static ActionErrors reloadTable(HttpServletRequest request,
                                         ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    UploadData header = pForm.getTableHeader();
    if (header == null) {
      String errorMess = "No table loaded";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    int uploadId = header.getUploadId();
    ae = loadTable(request, form, uploadId);
    return ae;
  }

  public static ActionErrors loadTable(HttpServletRequest request,
                                       ActionForm form, int pUploadId) throws
    Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();
    Distributor distEjb = factory.getDistributorAPI();
    Manufacturer manufEjb = factory.getManufacturerAPI();
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

    int storeCatalogId = catalogInfEjb.getStoreCatalogId(storeId);
    ae = readStoreDistributors(distEjb, pForm, storeId);
    if (ae.size() > 0) {
      return ae;
    }
    ae = readStoreManufacturers(manufEjb, pForm, storeId);
    if (ae.size() > 0) {
      return ae;
    }
    ae = readStoreCategories(catalogInfEjb, pForm, storeCatalogId);
    if (ae.size() > 0) {
      return ae;
    }
    readCertifiedCompanies(factory.getProductInformationAPI(), pForm);
    pForm.setFileName(null);
    pForm.setTable(null);
    pForm.setTableHeader(null);
    pForm.setSourceTable(null);
    pForm.setColumnTypes(null);
    XlsTableView xlsTableVw = null;
    int uploadId = pUploadId;
    try {
      xlsTableVw = itemEjb.getSkuXlsTableById(storeId, uploadId, null);
    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      int ind1 = -1;
      int ind2 = -1;
      if (errorMess == null)throw exc;
      ind1 = errorMess.indexOf("^clw^");
      if (ind1 >= 0) {
        ind2 = errorMess.indexOf("^clw^", ind1 + 3);
        if (ind2 > 0) {
          errorMess = errorMess.substring(ind1 + 5, ind2);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      throw exc;
    }

    UploadData header = xlsTableVw.getHeader();

    pForm.setTableHeader(header);
    pForm.setTableStatus(header.getUploadStatusCd());
    UploadSkuView[] uploadSkusVw = xlsTableVw.getContent();
    //set UI filter
    boolean matchedOnlyFl = ("Matched".equals(pForm.getFilterMatched())) ? true : false;
    boolean unMatchedOnlyFl = ("Unmatched".equals(pForm.getFilterMatched())) ? true : false;
    boolean storeSkuFilterFl = false;
    boolean distSkuFilterFl = false;
    boolean manufSkuFilterFl = false;
    String skuFilter = pForm.getFilterSku();
    if (Utility.isSet(skuFilter)) {
      if ("distSku".equals(pForm.getFilterSkuType())) distSkuFilterFl = true;
      else if ("manufSku".equals(pForm.getFilterSkuType())) manufSkuFilterFl = true;
      else if ("storeSku".equals(pForm.getFilterSkuType())) storeSkuFilterFl = true;
      skuFilter = skuFilter.trim().toUpperCase();
    }
    String manufFilter = pForm.getFilterManuf();
    boolean manufFilterFl = false;
    if (Utility.isSet(manufFilter)) {
      manufFilterFl = true;
      manufFilter = manufFilter.trim().toUpperCase();
    }
    String distFilter = pForm.getFilterDist();
    boolean distFilterFl = false;
    if (Utility.isSet(distFilter)) {
      distFilterFl = true;
      distFilter = distFilter.trim().toUpperCase();
    }
    String nameFilter = pForm.getFilterName();
    boolean nameFilterFl = false;
    if (Utility.isSet(nameFilter)) {
      nameFilterFl = true;
      nameFilter = nameFilter.trim().toUpperCase();
    }
    String categFilter = pForm.getFilterCateg();
    boolean categFilterFl = false;
    if (Utility.isSet(categFilter)) {
      categFilterFl = true;
      categFilter = categFilter.trim().toUpperCase();
    }

    UploadSkuViewVector uploadSkuVwV = new UploadSkuViewVector();
    if (matchedOnlyFl || unMatchedOnlyFl ||
        storeSkuFilterFl || manufSkuFilterFl || distSkuFilterFl ||
        manufFilterFl || distFilterFl ||
        nameFilterFl || categFilterFl) {
      for (int ii = 0; ii < uploadSkusVw.length; ii++) {
        UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
        if (matchedOnlyFl) {
          if (usD.getItemId() <= 0)continue;
        }
        if (unMatchedOnlyFl) {
          if (usD.getItemId() > 0)continue;
        }
        if (storeSkuFilterFl) {
          String skuNum = usD.getSkuNum();
          String matchedSkuNum = uploadSkusVw[ii].getSkuNum();
          if ((!Utility.isSet(skuNum) ||
               !skuNum.equalsIgnoreCase(skuFilter)) &&
              (!Utility.isSet(matchedSkuNum) ||
               !matchedSkuNum.equalsIgnoreCase(skuFilter))) {
            continue;
          }
        }
        if (manufSkuFilterFl) {
          String manufSku = usD.getManufSku();
          if (!Utility.isSet(manufSku) ||
              manufSku.toUpperCase().indexOf(skuFilter) < 0) {
            continue;
          }
        }
        if (distSkuFilterFl) {
          String distSku = usD.getDistSku();
          if (!Utility.isSet(distSku) ||
              distSku.toUpperCase().indexOf(skuFilter) < 0) {
            continue;
          }
        }
        if (manufFilterFl) {
          String manufName = usD.getManufName();
          if (!Utility.isSet(manufName) ||
              manufName.toUpperCase().indexOf(manufFilter) < 0) {
            continue;
          }
        }
        if (distFilterFl) {
          String distName = usD.getDistName();
          if (!Utility.isSet(distName) ||
              distName.toUpperCase().indexOf(distFilter) < 0) {
            continue;
          }
        }
        if (nameFilterFl) {
          String skuName = usD.getShortDesc();
          if (!Utility.isSet(skuName) ||
              skuName.toUpperCase().indexOf(nameFilter) < 0) {
            continue;
          }
        }
        if (categFilterFl) {
          String categ = usD.getCategory();
          if (!Utility.isSet(categ) ||
              categ.toUpperCase().indexOf(categFilter) < 0) {
            continue;
          }
        }
        uploadSkuVwV.add(uploadSkusVw[ii]);
      }
      uploadSkusVw = (UploadSkuView[]) uploadSkuVwV.toArray(new UploadSkuView[0]);
      xlsTableVw.setContent(uploadSkusVw);
    }

    String[] itemProperties = pForm.getItemProperties();
    int[] itemPropertyMap = new int[itemProperties.length];
    for (int ii = 0; ii < itemPropertyMap.length; ii++) {
      itemPropertyMap[ii] = 0;
    }
    for (int ii = 0; ii < uploadSkusVw.length; ii++) {
      UploadSkuData usD = uploadSkusVw[ii].getUploadSku();
      for (int jj = 0; jj < itemPropertyMap.length; jj++) {
        if (itemPropertyMap[jj] != 0)continue;
        String valS = pForm.getSkuProperty(usD, itemProperties[jj]);                
        if (Utility.isSet(valS)) {
          itemPropertyMap[jj] = 1;
        }
      }
    }    
    
    pForm.setItemPropertyMap(itemPropertyMap);
    pForm.setUploadSkus(uploadSkusVw);
    pForm.setItemsToMatch(null);

    pForm.setFileName(header.getFileName());

    pForm.setColumnTypes(null);
    

    checkDistCostAndCatalogPrice(ae, factory.getStoreAPI(), storeId, pForm, uploadSkusVw); // bug #852, 2572 fix

    /***
    // bug #852 fix: Begin    
    Store storeEjb = factory.getStoreAPI();
    StoreData sd = storeEjb.getStore(storeId);
    PropertyDataVector prop = sd.getMiscProperties();
    String propValue = "false";
    for (int ii = 0; ii < prop.size(); ii++) {
        PropertyData pD = (PropertyData) prop.get(ii);
        String propType = pD.getPropertyTypeCd();
        propValue = pD.getValue();
        if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
            pForm.setShowDistCostFl(Utility.isTrue(propValue));
            break;
        }
    }
    // if "Set Cost and Price Equal" check box is checked on the Store Detail screen,
    // compare Catalog Price and Dist. Cost for each sku#
    if (propValue.trim().equals("true")) { //"Set Cost and Price Equal" check box is checked on the Store Detail screen
        StringBuffer sbSkuNums = new StringBuffer();
        boolean catPriceNeDiscSku = false;
    	for (int i1 = 0; i1 < uploadSkusVw.length; i1++) {
            UploadSkuData usD = uploadSkusVw[i1].getUploadSku();
            String skuNum = usD.getSkuNum();
            String distCost = usD.getDistCost();
            String catalogPrice = usD.getCatalogPrice();
            if (distCost == null || catalogPrice == null) {
                sbSkuNums.append("<");
                sbSkuNums.append(skuNum);
                sbSkuNums.append(">");
                catPriceNeDiscSku = true;
            } else {
                if (!distCost.equals(catalogPrice)) {
                    sbSkuNums.append("<");
                    sbSkuNums.append(skuNum);
                    sbSkuNums.append(">");
                    catPriceNeDiscSku = true;
                    //pForm.setShowDistCostFl(Utility.isTrue("false"));                
                }
            }
        }
        if (catPriceNeDiscSku) {
           String errorMess = "Price and Cost are different in this spreadsheet for Sku Num(s) " + sbSkuNums.toString() + "."
                         + "Please, delete the Dist. Cost column or correct to make it the same as the Catalog Price."; 
           ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }
    }
    // bug #852 fix: End
    ***/
    return ae;
  }

  private static void orderIntArray(int[] intArray) {
    if (intArray == null || intArray.length <= 1)return;
    for (int ii = 0; ii < intArray.length - 1; ii++) {
      boolean exitFl = true;
      for (int jj = 0; jj < intArray.length - ii - 1; jj++) {
        if (intArray[jj] > intArray[jj + 1]) {
          exitFl = false;
          int wrk = intArray[jj + 1];
          intArray[jj + 1] = intArray[jj];
          intArray[jj] = wrk;
        }
      }
      if (exitFl)break;
    }
  }

  private static void orderStringArray(String[] strArray) {
    if (strArray == null || strArray.length <= 1)return;
    for (int ii = 0; ii < strArray.length - 1; ii++) {
      boolean exitFl = true;
      for (int jj = 0; jj < strArray.length - ii - 1; jj++) {
        if (strArray[jj].compareTo(strArray[jj + 1]) > 0) {
          exitFl = false;
          String wrk = strArray[jj + 1];
          strArray[jj + 1] = strArray[jj];
          strArray[jj] = wrk;
        }
      }
      if (exitFl)break;
    }
  }

  public static ActionErrors editSku(HttpServletRequest request,
                                     ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.
      APP_USER);
    String userName = appUser.getUser().getUserName();
    StoreData storeD = appUser.getUserStore();
    int storeId = storeD.getBusEntity().getBusEntityId();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    ItemInformation itemEjb = factory.getItemInformationAPI();

    String itemIdS = request.getParameter("itemId");
    int itemId = 0;
    try {
      itemId = Integer.parseInt(itemIdS);
    } catch (Exception exc) {
      String errorMess = "Invalid item id: " + itemIdS;
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    pForm.setEditItemId(itemId);
    return ae;
  }

  public static ActionErrors updateSku(HttpServletRequest request,
                                       ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    HttpSession session = request.getSession();
    StoreItemMgrForm itemForm = (StoreItemMgrForm) session.getAttribute(
      "STORE_ADMIN_ITEM_FORM");
    ProductData productD = itemForm.getProduct();

    if (productD != null) {
      int itemId = productD.getProductId();
      String skuNum = productD.getCustomerSkuNum();
      UploadSkuView[] uploadSkus = pForm.getUploadSkus();
      if (uploadSkus != null) {
        for (int ii = 0; ii < uploadSkus.length; ii++) {
          UploadSkuView usVw = uploadSkus[ii];
          if (usVw.getUploadSku().getItemId() == itemId) {
            if (!skuNum.equals(usVw.getSkuNum())) {
              usVw.setSkuNum(skuNum);
            }
          }
        }
      }
      UploadSkuViewVector itemsToMatch = pForm.getItemsToMatch();
      if (itemsToMatch != null) {
        for (Iterator iter = itemsToMatch.iterator(); iter.hasNext(); ) {
          UploadSkuView usVw = (UploadSkuView) iter.next();
          if (usVw.getUploadSku().getItemId() == itemId) {
            usVw.setSkuNum(skuNum);
            UploadSkuData usD = usVw.getUploadSku();
            usD.setShortDesc(productD.getShortDesc());
            usD.setLongDesc(productD.getLongDesc());
            usD.setSkuColor(productD.getColor());
            usD.setSkuPack(productD.getPack());
            usD.setSkuSize(productD.getSize());
            usD.setSkuUom(productD.getUom());
            CatalogCategoryDataVector ccDV = productD.getCatalogCategories();
            String category = "";
            String adminCategory = "";
            if (ccDV != null && ccDV.size() > 0) {
              CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
              category = ccD.getCatalogCategoryShortDesc();
              adminCategory = ccD.getCatalogCategoryLongDesc();
            }
            usD.setCategory(category);
            usD.setAdminCategory(adminCategory);

            int manufId = 0;
            String manufName = "";
            BusEntityData manufBusEntD = productD.getManufacturer();
            if (manufBusEntD != null) {
              manufId = manufBusEntD.getBusEntityId();
              manufName = manufBusEntD.getShortDesc();
            }
            usD.setManufId(manufId);
            usD.setManufName(manufName);

            String manufPack = "";
            String manufUom = "";
            String manufSku = "";
            ItemMappingData imD = productD.getManuMapping();
            if (imD != null) {
              manufPack = Utility.strNN(imD.getItemPack());
              manufUom = Utility.strNN(imD.getItemUom());
              manufSku = Utility.strNN(imD.getItemNum());
            }
            usD.setManufPack(manufPack);
            usD.setManufSku(manufSku);
            usD.setManufUom(manufUom);

            double listPriceDb = productD.getListPrice();
            BigDecimal listPriceBD = new BigDecimal(listPriceDb);
            listPriceBD = listPriceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
            usD.setListPrice(listPriceBD.toString());

            usD.setCustomerDesc(productD.getCustomerProductShortDesc());
            usD.setShipWeight(productD.getShipWeight());
            usD.setWeightUnit(productD.getWeightUnit());
          }
        }
      }

    }

    pForm.setEditItemId(0);
    return ae;
  }

  private static ActionErrors readStoreDistributors(Distributor distEjb,
    StoreItemLoaderMgrForm pForm, int storeId) throws Exception {

    ActionErrors ae = new ActionErrors();

    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    IdVector storeIdAsV = new IdVector();
    storeIdAsV.add(new Integer(storeId));
    besc.setStoreBusEntityIds(storeIdAsV);

    DistributorDataVector distDV = distEjb.getDistributorByCriteria(besc);
    if (distDV.size() > 1) {
      Object[] distA = distDV.toArray();
      for (int ii = 0; ii < distA.length - 1; ii++) {
        boolean exitFl = true;
        for (int jj = 0; jj < distA.length - ii - 1; jj++) {
          DistributorData dD1 = (DistributorData) distA[jj];
          DistributorData dD2 = (DistributorData) distA[jj + 1];
          int comp = dD1.getBusEntity().getShortDesc().compareToIgnoreCase(dD2.
            getBusEntity().getShortDesc());
          if (comp > 0) {
            distA[jj] = dD2;
            distA[jj + 1] = dD1;
            exitFl = false;
          }
        }
        if (exitFl)break;
      }
      distDV = new DistributorDataVector();
      for (int ii = 0; ii < distA.length; ii++) {
        DistributorData dD1 = (DistributorData) distA[ii];
        distDV.add(dD1);
      }
    }

    pForm.setStoreDistributors(distDV);

    return ae;
  }

  private static ActionErrors readStoreManufacturers(Manufacturer manufEjb,
    StoreItemLoaderMgrForm pForm, int storeId) throws Exception {

    ActionErrors ae = new ActionErrors();

    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    IdVector storeIdAsV = new IdVector();
    storeIdAsV.add(new Integer(storeId));
    besc.setStoreBusEntityIds(storeIdAsV);

    ManufacturerDataVector manufDV = manufEjb.getManufacturerByCriteria(besc);
    if (manufDV.size() > 1) {
      Object[] manufA = manufDV.toArray();
      for (int ii = 0; ii < manufA.length - 1; ii++) {
        boolean exitFl = true;
        for (int jj = 0; jj < manufA.length - ii - 1; jj++) {
          ManufacturerData mD1 = (ManufacturerData) manufA[jj];
          ManufacturerData mD2 = (ManufacturerData) manufA[jj + 1];
          int comp = mD1.getBusEntity().getShortDesc().compareToIgnoreCase(mD2.
            getBusEntity().getShortDesc());
          if (comp > 0) {
            manufA[jj] = mD2;
            manufA[jj + 1] = mD1;
            exitFl = false;
          }
        }
        if (exitFl)break;
      }
      manufDV = new ManufacturerDataVector();
      for (int ii = 0; ii < manufA.length; ii++) {
        ManufacturerData mD1 = (ManufacturerData) manufA[ii];
        manufDV.add(mD1);
      }
    }

    pForm.setStoreManufacturers(manufDV);

    return ae;
  }

  private static ActionErrors readStoreCategories(CatalogInformation catInfEjb,
                                                  StoreItemLoaderMgrForm pForm,
                                                  int storeCatalogId) throws
    Exception {

    ActionErrors ae = new ActionErrors();
    CatalogCategoryDataVector storeCategories = null;
    if (pForm.getAllowMixedCategoryAndItemUnderSameParent()){
    	storeCategories = catInfEjb.getAllStoreCatalogCategories(storeCatalogId);
    }else{
    	storeCategories = catInfEjb.getLowestStoreCatalogCategories(storeCatalogId);
    }
    storeCategories = sortCategories(storeCategories);
    pForm.setStoreCategories(storeCategories);

    return ae;
  }
  
  private static void readCertifiedCompanies(
            ProductInformation productInfoEjb, StoreItemLoaderMgrForm pForm)
            throws Exception {
      BusEntityDataVector certifiedCompanies = productInfoEjb
                .getBusEntityByCriteria(new BusEntitySearchCriteria(),
                        RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
      pForm.setCertifiedCompanies(certifiedCompanies);
  }

    private static CatalogCategoryDataVector sortCategories(
        CatalogCategoryDataVector categories) {
    if (categories == null || categories.size() < 2) {
      return categories;
    }
        Collections.sort(categories, new CatalogCategoryComparatorByDesc());
    return categories;
  }

    private final static boolean trySearchAndStoreGreenCertified(
            ProductData productData, UploadSkuData skuData,
            ActionErrors errors, ProductInformation productInformation) {
        boolean needUpdate = false;
        try {
            String greenCertified = skuData.getGreenCertified();
            if (greenCertified != null && greenCertified.length() > 0) {
                String items[] = greenCertified.split(GREEN_CERTIFIED_DELIMITER);
                BusEntityDataVector certifiedCompanies = productInformation.
                getBusEntityByCriteria(new BusEntitySearchCriteria(),
                        RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
                Iterator it = certifiedCompanies.iterator();
                Map checkNames = new HashMap();
                while (it.hasNext()) {
                    BusEntityData item = (BusEntityData) it.next();
                    checkNames.put(item.getShortDesc(), item);
                }
                List list = new ArrayList();
                for (int i = 0; items != null && i < items.length; i++) {
                    items[i] = items[i].trim();
                    if (checkNames.containsKey(items[i]) == false) {
                        errors.add("error", new ActionError("error.simpleGenericError",
                                "Not found Ceritified (Green) Company:" + items[i]));
                    } else {
                        list.add(checkNames.get(items[i]));
                    }
                }
                if (errors.size() == 0) {
                    StoreItemMgrLogic.addCertCompanies(productData, list.iterator());   
                }
                if (items != null && items.length > 0) {
                    needUpdate = true;
                }
            }
            return needUpdate;
        } catch (Exception e) {
            errors.add("error", new ActionError("error.simpleGenericError", e
                    .getMessage()));
            return false;
        }
    }

    private final static byte[] loadData(final String pUrl, String pRequiredMimeType) throws Exception {
		if (pUrl == null || pUrl.length() == 0) {
			return null;
		}
		try {
			return HttpClientUtil.load(pUrl, pRequiredMimeType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Cann't load data from '" + pUrl + "'!");
		}
	}

	private final static String TYPE_IMAGES = "images";

	private final static String TYPE_MSDS = "msds";

	private final static String TYPE_DED = "ded";

	private final static String TYPE_SPEC = "spec";

    private final static String TYPE_THUMBNAIL = "thumbnails";

	private final static String getExtension(final String pUrl) {
		final int index = (pUrl == null) ? -1 : pUrl.lastIndexOf(".");
		if (index < 0) {
			return "";
		} else {
			return pUrl.substring(index);
		}
	}
	
    public final static Map<String, byte[]> loadExternalData(
            final UploadSkuData pSkuData, final ActionErrors pErrors, int rowNum) {
        Map<String, byte[]> result = new TreeMap<String, byte[]>();
        loadData(result, pSkuData.getImageUrl(), TYPE_IMAGES, pErrors, rowNum);
        loadData(result, pSkuData.getThumbnailUrl(), TYPE_THUMBNAIL, pErrors, rowNum);
        loadData(result, pSkuData.getMsdsUrl(), TYPE_MSDS, pErrors, rowNum);
        loadData(result, pSkuData.getDedUrl(), TYPE_DED, pErrors, rowNum);
        loadData(result, pSkuData.getProdSpecUrl(), TYPE_SPEC, pErrors, rowNum);
        return result;
    }
    
    private final static void loadData(final Map<String, byte[]> pExternalDataMap,
            final String pUrl, final String pFileType, final ActionErrors pErrors, int rowNum) {
        String requiredMimeType = null;
        if (TYPE_IMAGES.equals(pFileType) || TYPE_THUMBNAIL.equals(pFileType)) {
            requiredMimeType = "image";
        }
        try {
            byte[] data = loadData(pUrl, requiredMimeType);
            if (data != null) {
                pExternalDataMap.put(pFileType, data);
            }
        } catch (Exception e) {
            pErrors.add("error", new ActionError("error.simpleGenericError", e.getMessage() + " Row number: " + rowNum));
        }
    }
    
    public static void storeExternalData(final ProductData pProductD,
            final UploadSkuData pSkuData,
            final Map<String, byte[]> pExternalDataMap,
            final ActionErrors pErrors) throws Exception {
        if (pExternalDataMap == null) {
            return;
        }
        if (pExternalDataMap.get(TYPE_IMAGES) != null) {
            storeExternalData(pProductD, TYPE_IMAGES, pSkuData.getImageUrl(),
                    pExternalDataMap.get(TYPE_IMAGES), pErrors);
            try {
                storeContentData(pProductD, TYPE_IMAGES, pSkuData.getImageUrl());
            } catch (Exception e) {
            	throw new Exception("Could not store Content binary data for IMAGE");
            }
        }
        if (pExternalDataMap.get(TYPE_THUMBNAIL) != null) {
            storeExternalData(pProductD, TYPE_THUMBNAIL, pSkuData.getThumbnailUrl(),
                    pExternalDataMap.get(TYPE_THUMBNAIL), pErrors);
            try {
                storeContentData(pProductD, TYPE_THUMBNAIL, pSkuData.getImageUrl());
            } catch (Exception e) {
            	throw new Exception("Could not store Content binary data for THUMBNAIL document");
            }
        }
        if (pExternalDataMap.get(TYPE_MSDS) != null) {
            storeExternalData(pProductD, TYPE_MSDS, pSkuData.getMsdsUrl(),
                    pExternalDataMap.get(TYPE_MSDS), pErrors);
            try {
                storeContentData(pProductD, TYPE_MSDS, pSkuData.getImageUrl());
            } catch (Exception e) {
            	throw new Exception("Could not store Content binary data for MSDS document");
            }
        }
        if (pExternalDataMap.get(TYPE_DED) != null) {
            storeExternalData(pProductD, TYPE_DED, pSkuData.getDedUrl(),
                    pExternalDataMap.get(TYPE_DED), pErrors);
            try {
                storeContentData(pProductD,  TYPE_DED, pSkuData.getImageUrl());
            } catch (Exception e) {
            	throw new Exception("Could not store Content binary data for DED document");
            }
        }
        if (pExternalDataMap.get(TYPE_SPEC) != null) {
            storeExternalData(pProductD, TYPE_SPEC, pSkuData.getProdSpecUrl(),
                    pExternalDataMap.get(TYPE_SPEC), pErrors);
            try {
                storeContentData(pProductD, TYPE_SPEC, pSkuData.getImageUrl());
            } catch (Exception e) {
            	throw new Exception("Could not store Content binary data for SPEC document");
            }
        }
    }
    
    private static boolean storeExternalData(final ProductData pProductD,
            final String pFileType, final String pUrl,
            final byte[] pExternalData, final ActionErrors pErrors) {
        // this is the path to be saved in the database
        final String basepath = "/en/products/" + pFileType + "/"
                + String.valueOf(pProductD.getItemData().getItemId())
                + getExtension(pUrl);
        // this is the absolute path where we will be writing
        String fileName = System.getProperty("webdeploy") + basepath;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            fos.write(pExternalData);
            if (TYPE_IMAGES.equals(pFileType)) {
                pProductD.setImage(basepath);
            } else if (TYPE_MSDS.equals(pFileType)) {
                pProductD.setMsds(basepath);
            } else if (TYPE_DED.equals(pFileType)) {
                pProductD.setDed(basepath);
            } else if (TYPE_SPEC.equals(pFileType)) {
                pProductD.setSpec(basepath);
            } else if (TYPE_THUMBNAIL.equals(pFileType)) {
                pProductD.setThumbnail(basepath);
            }
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            pErrors.add("error", new ActionError("error.simpleGenericError", "Can't store file:"
                    + fileName + ":" + ioe.getMessage()));
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    pErrors.add("error", new ActionError("error.simpleGenericError", "Can't close file:"
                            + fileName + ":" + ioe.getMessage()));
                }
            }
        }
    }
    
    private static void checkItemsForCreate(StoreItemLoaderMgrForm pForm, ActionErrors ae,
            HashMap categoryHM, HashMap manufHM, HashMap distHM,
            int storeCatalogId) {
        UploadData uD = pForm.getTableHeader();
        int uploadId = uD.getUploadId();
        UploadSkuViewVector itemsToMatch = pForm.getItemsToMatch();
        // Check errors
        if (itemsToMatch == null)
            itemsToMatch = new UploadSkuViewVector();
        int qty = 0;

        Iterator iter = itemsToMatch.iterator();
        UploadSkuView usMatchVw = null;
        UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
        for (int ii = 0; ii < uploadSkusVw.length; ii++) {
            UploadSkuView usVw = uploadSkusVw[ii];
            UploadSkuData usD = usVw.getUploadSku();
            int rowNum = usD.getRowNum();
            String skuNum = usVw.getSkuNum();
            boolean createFl = usVw.getSelectFlag();
            if (createFl)
                qty++;
            if (Utility.isSet(skuNum) && createFl) {
                String errorMess = "Sku already assigned. Remove assigment first. Row num: "
                        + rowNum;
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
            while (usMatchVw != null || iter.hasNext()) {
                if (usMatchVw == null) {
                    usMatchVw = (UploadSkuView) iter.next();
                }
                UploadSkuData usMatchD = usMatchVw.getUploadSku();
                int rowNumMatch = usMatchD.getRowNum();
                if (rowNumMatch < rowNum) {
                    usMatchVw = null;
                    continue;
                }
                if (rowNumMatch > rowNum) {
                    break;
                }
                boolean assignFl = usMatchVw.getSelectFlag();
                if (assignFl) {
                    if (createFl) {
                        String errorMess = "Can't create sku when sku to assign is selected. Row num: "
                                + rowNum;
                        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    }
                }
                usMatchVw = null;
            }
        }
        if (qty == 0) {
            String errorMess = "No sku selected";
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }
        for (int ii = 0; ii < uploadSkusVw.length; ii++) {
            UploadSkuView usVw = uploadSkusVw[ii];
            UploadSkuData usD = usVw.getUploadSku();
            int rowNum = usD.getRowNum();
            boolean createFl = usVw.getSelectFlag();
            if (!createFl)
                continue;
            ProductData productD = new ProductData();
            try {
                ActionErrors buffer = generateProductData(productD, usVw, manufHM, distHM, categoryHM, 
                		storeCatalogId, pForm.getAllowMixedCategoryAndItemUnderSameParent());
                ae.add(buffer);
            } catch (Exception e) {
                ae.add("error", new ActionError("error.simpleGenericError", "Error generate "
                        + rowNum + ":" + e.getMessage()));
            }
            Map<String, byte[]> externalData = loadExternalData(usD, ae, rowNum);
            // trySearchAndStoreGreenCertified(productD, usD, ae,
            // factory.getProductInformationAPI());
        }
    }
    
    private static void checkItemsForUpdate(ActionErrors ae,
            StoreItemLoaderMgrForm pForm, HashMap categoryHM, HashMap manufHM,
            HashMap distHM, int storeCatalogId, ItemInformation itemEjb,
            CatalogInformation catalogInfEjb, StoreData storeD, Set<Integer> notAssigned)
            throws Exception {
        int storeId = storeD.getBusEntity().getBusEntityId();
        UploadSkuView[] uploadSkusVw = pForm.getUploadSkus();
        PropertyDataVector propertyDV = storeD.getMiscProperties();
        for (int ii = 0; ii < uploadSkusVw.length; ii++) {
            UploadSkuView usVw = uploadSkusVw[ii];
            boolean updateFl = usVw.getSelectFlag();
            UploadSkuData usD = usVw.getUploadSku();
            Map<String, byte[]> externalData = loadExternalData(usD, ae, usD.getRowNum());
            if (updateFl == false || notAssigned.contains(ii) == true) {
                continue;
            }
            int itemId = usD.getItemId();
            ProductData productD = null;
            try {
                productD = catalogInfEjb.getStoreProduct(storeId, itemId);
                ActionErrors buffer = generateProductDataForUpdate(productD, usVw, manufHM, distHM, categoryHM, 
                		storeCatalogId, pForm.getAllowMixedCategoryAndItemUnderSameParent());
                ae.add(buffer);
            } catch (Exception exc) {
                String mess = exc.getMessage();
                if (mess == null)
                    mess = "";
                int ind = mess.indexOf("^clw^");
                if (ind >= 0) {
                    mess = mess.substring(ind + "^clw^".length());
                    ind = mess.indexOf("^clw^");
                    if (ind > 0)
                        mess = mess.substring(0, ind);
                    ae.add("error", new ActionError("error.simpleGenericError", mess));
                } else {
                    throw exc;
                }
            }
        }
    }
    
    private static void checkDistCostAndCatalogPrice(ActionErrors ae, Store storeEjb, int storeId, StoreItemLoaderMgrForm pForm, UploadSkuView[] uploadSkusVw)
            throws Exception {
        // // bug #852, 2572 fix: Begin    
        //Store storeEjb = factory.getStoreAPI();
        StoreData sd = storeEjb.getStore(storeId);
        PropertyDataVector prop = sd.getMiscProperties();
        String propValue = "false";
        for (int ii = 0; ii < prop.size(); ii++) {
            PropertyData pD = (PropertyData) prop.get(ii);
            String propType = pD.getPropertyTypeCd();
            propValue = pD.getValue();
            if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
            	pForm.setShowDistCostFl(Utility.isTrue(propValue));
                break;
            }
        }
        // if "Set Cost and Price Equal" check box is checked on the Store Detail screen,
        // compare Catalog Price and Dist. Cost for each sku#
        if (propValue.trim().equals("true")) { //"Set Cost and Price Equal" check box is checked on the Store Detail screen
            StringBuffer sbSkuNums = new StringBuffer();
            boolean catPriceNeDiscSku = false;
        	for (int i1 = 0; i1 < uploadSkusVw.length; i1++) {
                UploadSkuData usD = uploadSkusVw[i1].getUploadSku();
                String skuNum = usD.getSkuNum();
                String distCost = usD.getDistCost();
                String catalogPrice = usD.getCatalogPrice();
                if (Utility.isSet(distCost) && Utility.isSet(catalogPrice)) { 
                	if(!distCost.equals(catalogPrice)){ // Catalog Price != Dist. Cost
                	   sbSkuNums.append("<");
                	   sbSkuNums.append(skuNum);
                	   sbSkuNums.append(">");
                	   catPriceNeDiscSku = true;               
                   } 
                } else if (!Utility.isSet(distCost) && Utility.isSet(catalogPrice)) { // catalogPrice != NULL(column in the spreadsheet exists) and != "", but distCost either DOS NOT exist or = "" =>	                                                              
                //} else if (null == distCost && Utility.isSet(catalogPrice)) { // catalogPrice != NULL (exists) and != "", but distCost (column in the spreadsheet) DOES NOT exist
                    usD.setDistCost(catalogPrice); // Catalog Price => Dist. Cost (to save in the Database)
                }
            }
            if (catPriceNeDiscSku && !sd.isSetCostAndPriceEqual()) {
               String errorMess = "Price and Cost are different in this spreadsheet for Sku Num(s) " + sbSkuNums.toString() + "."
                             + "Please, delete the Dist. Cost column or correct to make it the same as the Catalog Price."; 
               ae.add("error", new ActionError("error.simpleGenericError", errorMess));
	            }
        }
        // // bug #852, 2572 fix: End
    }
    
    private static void storeContentData(final ProductData pProductD,
    		final String pFileType ,final String pUrl) throws Exception {
    	
    	// this is the path to be saved in the database
        final String basepath = "./en/products/" + pFileType + "/"
                + String.valueOf(pProductD.getItemData().getItemId())
                + getExtension(pUrl);
        //log.info(".storeContentData(): basepath = " + basepath);
        
        APIAccess factory = APIAccess.getAPIAccess();
        
        // based off the value of the "storage.system.item" system property
        // either add records to CLW_CONTENT DB Table or add binary data to E3 Storage
        String storageType = Utility.strNN(System.getProperty("storage.system.item"));
        //log.info(".storeContentData(): storageType1 = " + storageType);
        if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
        	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE;
        } else {
        	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE;
        }
        //log.info(".storeContentData(): storageType2 = " + storageType);
        if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
     	   factory.getContentAPI().addContentSaveImageE3Storage(InetAddress.getLocalHost().getHostName(), basepath, "ItemImage");
        } else {   
    	       factory.getContentAPI().addContentSaveImage(InetAddress.getLocalHost().getHostName(), basepath, "ItemImage");
        }
    	//cont.addContentSaveImage(host, basepath, "ItemImage");
    	
    }


    public static ActionErrors init(HttpServletRequest request,	StoreItemLoaderMgrForm form) 
    throws Exception {
    	ActionErrors ae = new ActionErrors();
    	StoreItemLoaderMgrForm pForm = (StoreItemLoaderMgrForm) form;
    	HttpSession session = request.getSession();
    	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    	APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    	Store storeEjb = factory.getStoreAPI();
        int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
        StoreData storeD = storeEjb.getStore(storeId);
        
    	PropertyDataVector prop = storeD.getMiscProperties();
    	for (int ii = 0; ii < prop.size(); ii++) {
    		PropertyData pD = (PropertyData) prop.get(ii);
    		String propType = pD.getPropertyTypeCd();
    		String propValue = pD.getValue();
    		if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM.equals(propType)) {
    			pForm.setAllowMixedCategoryAndItemUnderSameParent(Utility.isTrue(propValue));
    		}
    	}
    	return ae;
    }
}
