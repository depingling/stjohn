package com.cleanwise.service.api.process.operations;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class Outbound850TxtSafewayBuilder {

    public static final String DEFAULT_END_LINE_SYMBOL = "\r\n";
    public static final String DEFAULT_FINAL_LINE_SYMBOL = ".";

    public static final char DEFAULT_NUMBER_BLANK_SPACE_FILLER = '0';
    public static final char DEFAULT_STRING_BLANK_SPACE_FILLER = ' ';

    public static final int CELL_TYPE_UNKNOWN                 = 0;
    public static final int CELL_TYPE_STRING                  = 1;
    public static final int CELL_TYPE_INTEGER_NUMBER          = 2;
    public static final int CELL_TYPE_UNSIGNED_INTEGER_NUMBER = 3;
    public static final int CELL_TYPE_DECIMAL_NUMBER          = 4;

    public static boolean IsEmpty(String value) {
        if (value == null) {
            return true;
        }
        if (value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static ArrayList<ErrorInfo> AppendErrors(ArrayList<ErrorInfo> dest, 
            ArrayList<ErrorInfo> src) {
        if (dest == null || src == null) {
            return dest;
        }
        for (int i = 0; i < src.size(); ++i) {
            ErrorInfo error = (ErrorInfo)src.get(i);
            if (error != null) {
                dest.add(error);
            }
        }
        return dest;
    }

    /**
     * 
     */
    public class ErrorInfo {
        private String _itemName;
        private String _message;

        public ErrorInfo(String itemName, String message) {
            _itemName = itemName;
            _message = message;
        }
        public ErrorInfo() {
            this("", "");
        }
        public String getItemName() {
            return _itemName;
        }
        public void setItemName(String itemName) {
            _itemName = itemName;
        }
        public String getMessage() {
            return _message;
        }
        public void setMessage(String message) {
            _message = message;
        }
        public String toString() {
            StringBuilder buff = new StringBuilder();
            buff.append("");
            buff.append(_itemName);
            buff.append(": ");
            buff.append(_message);
            return buff.toString();
        }
    }

    private class DocumentCell {
        private int _index;
        private int _type;
        private int _width;
        private boolean _required;
        private String _name;
        private String _value;

        public DocumentCell(int type, int width, boolean required, 
                String name, String value, int index) {
            _index = index;
            _type = type;
            _width = width;
            _required = required;
            _name = name;
            _value = value;
        }
        public DocumentCell(int type, int width, boolean required, 
                String name, String value) {
            this(type, width, required, name, "", 0);
        }
        public DocumentCell(int type, int width, boolean required, String name) {
            this(type, width, required, name, "");
        }
        public int getIndex() {
            return _index;
        }
        public int getType() {
            return _type;
        }
        public int getWidth() {
            return _width;
        }
        public boolean getRequired() {
            return _required;
        }
        public String getName() {
            return _name;
        }
        public String getValue() {
            return _value;
        }
        public void setValue(String value) {
            _value = value;
        }
        public ArrayList<ErrorInfo> validate() {
            ArrayList<ErrorInfo> errors = new ArrayList<ErrorInfo>();
            String cellName = _name + "[" + _index + "]";
            /// Check type
            if (_type != Outbound850TxtSafewayBuilder.CELL_TYPE_STRING && 
                _type != Outbound850TxtSafewayBuilder.CELL_TYPE_INTEGER_NUMBER && 
                _type != Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER &&
                _type != Outbound850TxtSafewayBuilder.CELL_TYPE_DECIMAL_NUMBER) {
                errors.add(new ErrorInfo(cellName, "Cell type is not defined correctly"));
            }
            /// Check name
            if (Outbound850TxtSafewayBuilder.IsEmpty(_name)) {
                errors.add(new ErrorInfo(cellName, "Cell name is not defined"));
            }
            /// Check value
            if (Outbound850TxtSafewayBuilder.IsEmpty(_value)) {
                if (_required) {
                    errors.add(new ErrorInfo(cellName, "Cell value is not defined"));
                }
            } else {
                if (_type == Outbound850TxtSafewayBuilder.CELL_TYPE_STRING) {
                    if (_width < _value.length()) {
                        errors.add(new ErrorInfo(cellName, "Length of value cell is very big"));
                    }
                } else if (_type == Outbound850TxtSafewayBuilder.CELL_TYPE_INTEGER_NUMBER) {
                    boolean isValidNumber = true;
                    long numValue = 0;
                    try {
                        numValue = Long.parseLong(_value);
                    } catch (Exception ex) {
                        isValidNumber = false;
                    }
                    if (isValidNumber) {
                        String tempValue = String.valueOf(numValue);
                        if (_width < tempValue.length()) {
                            errors.add(new ErrorInfo(cellName, "Numeric (integer) value of cell is very big: " + _value));
                        }
                    } else {
                        errors.add(new ErrorInfo(cellName, "Erroneous numeric (integer) value of cell: " + _value));
                    }
                } else if (_type == Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER) {
                    boolean isValidNumber = true;
                    long numValue = 0;
                    try {
                        numValue = Long.parseLong(_value);
                    } catch (Exception ex) {
                        isValidNumber = false;
                    }
                    if (isValidNumber) {
                        if (numValue < 0) {
                            errors.add(new ErrorInfo(cellName, "Numeric value of cell should be more or equal to 0"));
                        } else {
                            String tempValue = String.valueOf(numValue);
                            if (_width < tempValue.length()) {
                                errors.add(new ErrorInfo(cellName, "Numeric (integer) value of cell is very big"));
                            }
                        }
                    } else {
                        errors.add(new ErrorInfo(cellName, "Erroneous numeric (integer) value of cell: " + _value));
                    }
                } else if (_type == Outbound850TxtSafewayBuilder.CELL_TYPE_DECIMAL_NUMBER) {
                    ///
                }
            }
            return errors;
        }
        public String toString() {
            StringBuilder buff = new StringBuilder();
            buff.append("[Cell: ");
            buff.append("index=");
            buff.append(_index);
            buff.append(", type=");
            buff.append(_type);
            buff.append(", width=");
            buff.append(_width);
            buff.append(", required=");
            buff.append(_required);
            buff.append(", name=");
            buff.append(_name);
            buff.append(", value=");
            buff.append(_value);
            buff.append("]");
            return buff.toString();
        }
    }

    private class DocumentRow {
        private int _index;
        private String _name;
        private ArrayList<DocumentCell> _cells;

        public DocumentRow(int index, String name) {
            _index = index;
            _name = name;
            _cells = null;
        }
        public DocumentRow(String name) {
            this(0, name);
        }
        public String getName() {
            return _name;
        }
        public void setName(String name) {
            _name = name;
        }
        public int getIndex() {
            return _index;
        }
        public void setIndex(int index) {
            _index = index;
        }
        public void addCell(int type, int width, boolean required, String name) {
            if (name == null) {
                return;
            }
            if (_cells == null) {
                _cells = new ArrayList<DocumentCell>();
            }
            int cellIndex = _cells.size();
            _cells.add(new DocumentCell(type, width, required, name, "", cellIndex));
        }
        public int getCellsCount() {
            if (_cells == null) {
                return 0;
            }
            return _cells.size();
        }
        public DocumentCell getCellByIndex(int index) {
            if (index >= 0 && index < getCellsCount()) {
                return (DocumentCell)_cells.get(index);
            }
            return null;
        }
        public DocumentCell getCellByName(String name) {
            if (name == null) {
                return null;
            }
            if (getCellsCount() > 0) {
                for (int i = 0; i < _cells.size(); ++i) {
                    DocumentCell cell = (DocumentCell)_cells.get(i);
                    String cellName = cell.getName();
                    if (cellName != null && cellName.equalsIgnoreCase(name)) {
                        return cell;
                    }
                }
            }
            return null;
        }
        public String getCellValueByName(String name) {
            DocumentCell cell = getCellByName(name);
            if (cell == null) {
                return null;
            }
            return cell.getValue();
        }
        public void setCellValueByName(String name, String value) {
            DocumentCell cell = getCellByName(name);
            if (cell == null) {
                return;
            }
            cell.setValue(value);
        }
        public int getWidth() {
            if (_cells == null) {
                return 0;
            }
            int width = 0;
            for (int i = 0; i < _cells.size(); ++i) {
                DocumentCell cell = (DocumentCell)_cells.get(i);
                width += cell.getWidth();
            }
            return width;
        }
        public ArrayList<ErrorInfo> validate() {
            ArrayList<ErrorInfo> errors = new ArrayList<ErrorInfo>();
            if (_cells != null) {
                for (int i = 0; i < _cells.size(); ++i) {
                    DocumentCell cell = (DocumentCell)_cells.get(i);
                    ArrayList<ErrorInfo> cellErrors = cell.validate();
                    errors = Outbound850TxtSafewayBuilder.AppendErrors(errors, cellErrors);
                }
            }
            return errors;
        }
        public String toString() {
            StringBuilder buff = new StringBuilder();
            buff.append("[Row: ");
            buff.append("index=");
            buff.append(_index);
            buff.append(", name=");
            buff.append(_name);
            if (_cells != null) {
                for (int i = 0; i < _cells.size(); ++i) {
                    DocumentCell cell = (DocumentCell)_cells.get(i);
                    buff.append(", ");
                    buff.append(cell.toString());
                }
            }
            buff.append("]");
            return buff.toString();
        }
    }

    public class CommonArea {

        public static final String CELL_CEOR_CMN_CORP    = "CEOR-CMN-CORP";
        public static final String CELL_CEOR_CMN_DIV     = "CEOR-CMN-DIV";
        public static final String CELL_CEOR_CMN_FAC     = "CEOR-CMN-FAC";
        public static final String CELL_CEOR_CMN_ORD_TYP = "CEOR-CMN-ORD-TYP";
        public static final String CELL_CEOR_CMN_ORD_NUM = "CEOR-CMN-ORD-NUM";

        private DocumentRow _row;
        
        public CommonArea() {
            _row = new DocumentRow("CEOR-COMMON-AREA");
            _row.setName("CEOR-COMMON-AREA");
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,  4, true,  CommonArea.CELL_CEOR_CMN_CORP);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,  2, false, CommonArea.CELL_CEOR_CMN_DIV);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,  4, false, CommonArea.CELL_CEOR_CMN_FAC);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,  3, false, CommonArea.CELL_CEOR_CMN_ORD_TYP);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING, 14, false, CommonArea.CELL_CEOR_CMN_ORD_NUM);
        }
        public String getCompanyCode() {
            return _row.getCellValueByName(CommonArea.CELL_CEOR_CMN_CORP);
        }
        public void setCompanyCode(String value) {
            _row.setCellValueByName(CommonArea.CELL_CEOR_CMN_CORP, value);
        }
        public String getSiteDivision() {
            return _row.getCellValueByName(CommonArea.CELL_CEOR_CMN_DIV);
        }
        public void setSiteDivision(String value) {
            _row.setCellValueByName(CommonArea.CELL_CEOR_CMN_DIV, value);
        }
        public String getStoreNumber() {
            return _row.getCellValueByName(CommonArea.CELL_CEOR_CMN_FAC);
        }
        public void setStoreNumber(String value) {
            _row.setCellValueByName(CommonArea.CELL_CEOR_CMN_FAC, value);
        }
        public String getPurchaseOrderType() {
            return _row.getCellValueByName(CommonArea.CELL_CEOR_CMN_ORD_TYP);
        }
        public void setPurchaseOrderType(String value) {
            _row.setCellValueByName(CommonArea.CELL_CEOR_CMN_ORD_TYP, value);
        }
        public String getPurchaseOrderNumber() {
            return _row.getCellValueByName(CommonArea.CELL_CEOR_CMN_ORD_NUM);
        }
        public void setPurchaseOrderNumber(String value) {
            _row.setCellValueByName(CommonArea.CELL_CEOR_CMN_ORD_NUM, value);
        }
        public ArrayList<ErrorInfo> validate() {
            return _row.validate();
        }
        public String toString() {
            return _row.toString();
        }
    }

    public class HeaderArea {

        public static final String CELL_CEOR_HDR_ORD_RECV_DT  = "CEOR-HDR-ORD-RECV-DT";
        public static final String CELL_CEOR_HDR_ORD_RECV_TM  = "CEOR-HDR-ORD-RECV-TM";
        public static final String CELL_CEOR_HDR_PROCESS_DT   = "CEOR-HDR-PROCESS-DT";
        public static final String CELL_CEOR_HDR_DELIVER_DT   = "CEOR-HDR-DELIVER-DT";
        public static final String CELL_FILLER                = "FILLER";

        private DocumentRow _row;

        public HeaderArea() {
            _row = new DocumentRow("CEOR-COMMON-AREA");
            _row.setName("CEOR-HEADER-AREA");
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,   8, true,  HeaderArea.CELL_CEOR_HDR_ORD_RECV_DT);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,   8, false, HeaderArea.CELL_CEOR_HDR_ORD_RECV_TM);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,   8, false, HeaderArea.CELL_CEOR_HDR_PROCESS_DT);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,   8, false, HeaderArea.CELL_CEOR_HDR_DELIVER_DT);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,                  142, false, HeaderArea.CELL_FILLER);
            StringBuilder filler = new StringBuilder();
            for (int i = 0; i < 142; ++i) {
                filler.append(" ");
            }
            _row.setCellValueByName(HeaderArea.CELL_FILLER, filler.toString());
        }
        public String getOrderCreationDate() {
            return _row.getCellValueByName(HeaderArea.CELL_CEOR_HDR_ORD_RECV_DT);
        }
        public void setOrderCreationDate(String value) {
            _row.setCellValueByName(HeaderArea.CELL_CEOR_HDR_ORD_RECV_DT, value);
        }
        public String getOrderCreationTime() {
            return _row.getCellValueByName(HeaderArea.CELL_CEOR_HDR_ORD_RECV_TM);
        }
        public void setOrderCreationTime(String value) {
            _row.setCellValueByName(HeaderArea.CELL_CEOR_HDR_ORD_RECV_TM, value);
        }
        public String getProcessDate() {
            return _row.getCellValueByName(HeaderArea.CELL_CEOR_HDR_PROCESS_DT);
        }
        public void setProcessDate(String value) {
            _row.setCellValueByName(HeaderArea.CELL_CEOR_HDR_PROCESS_DT, value);
        }
        public String getDeliveryDate() {
            return _row.getCellValueByName(HeaderArea.CELL_CEOR_HDR_DELIVER_DT);
        }
        public void setDeliveryDate(String value) {
            _row.setCellValueByName(HeaderArea.CELL_CEOR_HDR_DELIVER_DT, value);
        }
        public ArrayList<ErrorInfo> validate() {
            return _row.validate();
        }
        public String toString() {
            return _row.toString();
        }
    }

    public class DetailArea {

        public static final String CELL_CEOR_DTL_DATA_BATCH   = "CEOR-DTL-DATA-BATCH";
        public static final String CELL_CEOR_DTL_WH_ITEM_ID   = "CEOR-DTL-WH-ITEM-ID";
        public static final String CELL_CEOR_DTL_BR_ITEM_CD   = "CEOR-DTL-BR-ITEM-CD";
        public static final String CELL_CEOR_DTL_CORP_ITEM_CD = "CEOR-DTL-CORP-ITEM-CD";
        public static final String CELL_CEOR_DTL_MCL_BR       = "CEOR-DTL-MCL-BR";
        public static final String CELL_CEOR_DTL_ORDERING_UPC = "CEOR-DTL-ORDERING-UPC";
        public static final String CELL_CEOR_DTL_ORIGINAL_QTY = "CEOR-DTL-ORIGINAL-QTY";
        public static final String CELL_CEOR_DTL_CONFIRM_NUM  = "CEOR-DTL-CONFIRM-NUM";
        public static final String CELL_FILLER                = "FILLER";

        private DocumentRow _row;

        public DetailArea() {
            _row = new DocumentRow("CEOR-COMMON-AREA");
            _row.setName("CEOR-DETAIL-AREA");
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,                    4, false, DetailArea.CELL_CEOR_DTL_DATA_BATCH);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,  14, false, DetailArea.CELL_CEOR_DTL_WH_ITEM_ID);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,   5, false, DetailArea.CELL_CEOR_DTL_BR_ITEM_CD);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,   9, false, DetailArea.CELL_CEOR_DTL_CORP_ITEM_CD);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,                    2, false, DetailArea.CELL_CEOR_DTL_MCL_BR);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,  12, false, DetailArea.CELL_CEOR_DTL_ORDERING_UPC);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,   5, false, DetailArea.CELL_CEOR_DTL_ORIGINAL_QTY);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_UNSIGNED_INTEGER_NUMBER,  12, false, DetailArea.CELL_CEOR_DTL_CONFIRM_NUM);
            _row.addCell(Outbound850TxtSafewayBuilder.CELL_TYPE_STRING,                  111, false, DetailArea.CELL_FILLER);
            StringBuilder filler = new StringBuilder();
            for (int i = 0; i < 111; ++i) {
                filler.append(" ");
            }
            _row.setCellValueByName(DetailArea.CELL_FILLER, filler.toString());
        }
        public String getDataBatch() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_DATA_BATCH);
        }
        public void setDataBatch(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_DATA_BATCH, value);
        }
        public String getItemId() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_WH_ITEM_ID);
        }
        public void setWhItemId(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_WH_ITEM_ID, value);
        }
        public String getBrItemCd() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_BR_ITEM_CD);
        }
        public void setBrItemCd(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_BR_ITEM_CD, value);
        }
        public String getCorpItemCd() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_CORP_ITEM_CD);
        }
        public void setCorpItemCd(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_CORP_ITEM_CD, value);
        }
        public String getMclBr() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_MCL_BR);
        }
        public void setMclBr(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_MCL_BR, value);
        }
        public String getProductUpc() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_ORDERING_UPC);
        }
        public void setProductUpc(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_ORDERING_UPC, value);
        }
        public String getOrderItemQuantity() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_ORIGINAL_QTY);
        }
        public void setOrderItemQuantity(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_ORIGINAL_QTY, value);
        }
        public String getSkuNumber() {
            return _row.getCellValueByName(DetailArea.CELL_CEOR_DTL_CONFIRM_NUM);
        }
        public void setSkuNumber(String value) {
            _row.setCellValueByName(DetailArea.CELL_CEOR_DTL_CONFIRM_NUM, value);
        }
        public ArrayList<ErrorInfo> validate() {
            return _row.validate();
        }
        public String toString() {
            return _row.toString();
        }
    }

    public class OrderArea {
        private Outbound850TxtSafewayBuilder.HeaderArea _headerArea;
        private ArrayList<Outbound850TxtSafewayBuilder.DetailArea> _detailAreas;
        public OrderArea() {
            _headerArea = null;
            _detailAreas = null;
        }
        public void setHeaderArea(Outbound850TxtSafewayBuilder.HeaderArea headerArea) {
            _headerArea = headerArea;
        }
        public void addDetailArea(Outbound850TxtSafewayBuilder.DetailArea detailArea) {
            if (detailArea == null) {
                return;
            }
            if (_detailAreas == null) {
                _detailAreas = new ArrayList<Outbound850TxtSafewayBuilder.DetailArea>();
            }
            _detailAreas.add(detailArea);
        }
        public ArrayList<ErrorInfo> validate() {
            ArrayList<ErrorInfo> allErrors = new ArrayList<ErrorInfo>();
            if (_headerArea == null) {
                allErrors.add(new ErrorInfo("Header Area", "Header Area is not defined"));
            } else {
                allErrors = Outbound850TxtSafewayBuilder.AppendErrors(allErrors, _headerArea.validate());
            }
            if (_detailAreas != null) {
                for (int i = 0; i < _detailAreas.size(); ++i) {
                    Outbound850TxtSafewayBuilder.DetailArea area = (Outbound850TxtSafewayBuilder.DetailArea)_detailAreas.get(i);
                    allErrors = Outbound850TxtSafewayBuilder.AppendErrors(allErrors, area.validate());
                }
            }
            return allErrors;
        }
        public String toString() {
            StringBuilder buff = new StringBuilder();
            buff.append("[OrderArea: ");
            if (_headerArea != null) {
                buff.append(_headerArea.toString());
            }
            buff.append(", ");
            if (_detailAreas != null) {
                for (int i = 0; i < _detailAreas.size(); ++i) {
                    if (i > 0) {
                        buff.append(", ");
                    }
                    Outbound850TxtSafewayBuilder.DetailArea area = (Outbound850TxtSafewayBuilder.DetailArea)_detailAreas.get(i);
                    buff.append(area.toString());
                }
            }
            buff.append("]");
            return buff.toString();
        }
    }

    private String _documentId;
    private Outbound850TxtSafewayBuilder.CommonArea _commonArea;
    private ArrayList<Outbound850TxtSafewayBuilder.OrderArea> _orders;
    private String _endLineSymbol;
    private String _finalLineSymbol;
    private char _numberBlankSpaceFiller;
    private char _stringBlankSpaceFiller;

    public Outbound850TxtSafewayBuilder(String documentId) {
        _documentId = documentId;
        _commonArea = null;
        _orders = null;
        _endLineSymbol = DEFAULT_END_LINE_SYMBOL;
        _finalLineSymbol = DEFAULT_FINAL_LINE_SYMBOL;
        _numberBlankSpaceFiller = DEFAULT_NUMBER_BLANK_SPACE_FILLER;
        _stringBlankSpaceFiller = DEFAULT_STRING_BLANK_SPACE_FILLER;
    }
    public String getDocumentId() {
        return _documentId;
    }
    public void setEndLineSymbol(String endLineSymbol) {
        _endLineSymbol = endLineSymbol;
    }
    public void setFinalLineSymbol(String finalLineSymbol) {
        _finalLineSymbol = finalLineSymbol;
    }
    public void setNumberBlankSpaceFiller(char numberBlankSpaceFiller) {
        _numberBlankSpaceFiller = numberBlankSpaceFiller;
    }
    public void setStringBlankSpaceFiller(char stringBlankSpaceFiller) {
        _stringBlankSpaceFiller = stringBlankSpaceFiller;
    }
   public Outbound850TxtSafewayBuilder.CommonArea createCommonArea() {
        return new Outbound850TxtSafewayBuilder.CommonArea();
    }
    public Outbound850TxtSafewayBuilder.OrderArea createOrderArea() {
        return new Outbound850TxtSafewayBuilder.OrderArea();
    }
    public Outbound850TxtSafewayBuilder.HeaderArea createHeaderArea() {
        return new Outbound850TxtSafewayBuilder.HeaderArea();
    }
    public Outbound850TxtSafewayBuilder.DetailArea createDetailArea() {
        return new Outbound850TxtSafewayBuilder.DetailArea();
    }
    public void setCommonArea(Outbound850TxtSafewayBuilder.CommonArea commonArea) {
        _commonArea = commonArea;
        if (_commonArea != null) {
            _commonArea._row.setIndex(0);
        }
    }
    public void addOrderArea(Outbound850TxtSafewayBuilder.OrderArea OrderArea) {
        if (OrderArea == null) {
            return;
        }
        if (_orders == null) {
            _orders = new ArrayList<Outbound850TxtSafewayBuilder.OrderArea>();
        }
        _orders.add(OrderArea);
    }
    public ArrayList<ErrorInfo> validate() {
        ArrayList<ErrorInfo> allErrors = new ArrayList<ErrorInfo>();
        if (_commonArea == null) {
            allErrors.add(new ErrorInfo("Common Area", "Common Area is not defined"));
        } else {
            allErrors = Outbound850TxtSafewayBuilder.AppendErrors(allErrors, _commonArea.validate());
        }     
        if (_orders != null) {
            for (int i = 0; i < _orders.size(); ++i) {
                Outbound850TxtSafewayBuilder.OrderArea OrderArea = (Outbound850TxtSafewayBuilder.OrderArea)_orders.get(i);
                allErrors = Outbound850TxtSafewayBuilder.AppendErrors(allErrors, OrderArea.validate());
            }
        }
        return allErrors;
    }
    private void writeFinalLineSymbol(OutputStream out) throws IOException {
        if (_finalLineSymbol != null) {
            out.write(_finalLineSymbol.getBytes());
        }
    }
    private void writeEndLineSymbol(OutputStream out) throws IOException {
        if (_endLineSymbol != null) {
            out.write(_endLineSymbol.getBytes());
        }
    }
    private void writeCell(OutputStream out, DocumentCell cell) throws IOException {
        if (cell == null) {
            return;
        }
        final int width = cell.getWidth();
        if (width <= 0) {
            return;
        }
        int chr = _stringBlankSpaceFiller;
        if (cell.getType() != CELL_TYPE_STRING) {
        	chr = _numberBlankSpaceFiller;
        }
        if (cell.getValue() == null) {
        	for (int i = 0; i < width; ++i) {
                out.write(chr);
            }
        	return;
        }
        final int valueLength = cell.getValue().length();
        if (width > valueLength) {
            for (int i = 0; i < (width - valueLength); ++i) {
            	out.write(chr);
            }
        }
        out.write(cell.getValue().getBytes());
    }
    private void writeRow(OutputStream out, DocumentRow row) throws IOException {
        if (row == null) {
            return;
        }
        for (int i = 0; i < row.getCellsCount(); ++i) {
            DocumentCell cell = (DocumentCell)row.getCellByIndex(i);
            writeCell(out, cell);
        }
        writeFinalLineSymbol(out);
    }
    public void writeOrderArea(OutputStream out, Outbound850TxtSafewayBuilder.OrderArea OrderArea) throws IOException {
        if (OrderArea == null) {
            return;
        }
        boolean canWriteEndLine = false;
        if (OrderArea._headerArea != null) {
            writeRow(out, OrderArea._headerArea._row);
            canWriteEndLine = true;
        }
        if (OrderArea._detailAreas != null && OrderArea._detailAreas.size() > 0) {
            if (canWriteEndLine) {
                writeEndLineSymbol(out);
            }
            for (int i = 0; i < OrderArea._detailAreas.size(); ++i) {
                Outbound850TxtSafewayBuilder.DetailArea detail = (Outbound850TxtSafewayBuilder.DetailArea)OrderArea._detailAreas.get(i);
                writeRow(out, detail._row);
                if (i < OrderArea._detailAreas.size() - 1) {
                    writeEndLineSymbol(out);
                }
            }
        }
    }
    public void writeData(OutputStream out) throws IOException {
        boolean canWriteEndLine = false;
        if (_commonArea != null) {
            writeRow(out, _commonArea._row);
            canWriteEndLine = true;
        }
        if (canWriteEndLine && _endLineSymbol != null) {
            writeEndLineSymbol(out);
            canWriteEndLine = false;
        }
        if (_orders != null && _orders.size() > 0) {
            for (int i = 0; i < _orders.size(); ++i) {
                Outbound850TxtSafewayBuilder.OrderArea order = (Outbound850TxtSafewayBuilder.OrderArea)_orders.get(i);
                writeOrderArea(out, order);
                if (i < _orders.size() - 1) {
                    writeEndLineSymbol(out);
                }
            }
        }
    }
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("[document: id=");
        buff.append(_documentId);
        buff.append(", endLineSymbol=");
        buff.append(_endLineSymbol);
        buff.append(", finalLineSymbol=");
        buff.append(_finalLineSymbol);
        buff.append(", numberBlankSpaceFiller=");
        buff.append(_numberBlankSpaceFiller);
        buff.append(", stringBlankSpaceFiller=");
        buff.append(_stringBlankSpaceFiller);
        buff.append(", ");
        if (_commonArea != null) {
            buff.append(_commonArea.toString());
        }
        buff.append(", ");
        if (_orders != null) {
            for (int i = 0; i < _orders.size(); ++i) {
                if (i > 0) {
                    buff.append(", ");
                }
                Outbound850TxtSafewayBuilder.OrderArea order = (Outbound850TxtSafewayBuilder.OrderArea)_orders.get(i);
                buff.append(order.toString());
            }
        }
        buff.append("]");
        return buff.toString();
    }


}
