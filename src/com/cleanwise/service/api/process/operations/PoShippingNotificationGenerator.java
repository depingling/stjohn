package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;


public class PoShippingNotificationGenerator extends DocBuilder
        implements FileGenerator
{

    private static final String className = "PoShippingNotificationGenerator";

    public String generate(Object data, String fileName)
            throws Exception {

        fileName = fileName.indexOf(".") > 0 ? fileName : fileName + ".txt";
        return generate(data, new File(fileName));
    }

    public String generate(Object data, File file)
            throws Exception {

            if (data instanceof List) {
                OrderInfoDataView orderInfo = null;
                EdiInp856View distEdi856 = null;
                List dataL = (List) data;
                for(int ii=0; ii<dataL.size(); ii++) {
                    Object obj = dataL.get(ii);
                    if(obj instanceof OrderInfoDataView) {
                        orderInfo = (OrderInfoDataView) obj;
                    } else if(obj instanceof EdiInp856View) {
                        distEdi856 = (EdiInp856View) obj;
                    }
                }
                if(orderInfo!=null && distEdi856!=null ) {
                    return generateToFile(orderInfo, distEdi856, file);
                }
                return null;
            } else {
            	throw new Exception("generate() => Unknown input data type : " + data.getClass());
            }
    }

    private String generateToFile(OrderInfoDataView orderInfo, EdiInp856View distEdi856, File file) throws Exception {

        setUp(NOTEPAD);
        String text = genTXT(orderInfo, distEdi856);
        writeToFile(text, file);

        return text;
    }

    public String genTXT(OrderInfoDataView orderInfo, EdiInp856View distEdi856) {
        StringBuffer sb = new StringBuffer();
        writeHeaderTXT(sb, orderInfo, distEdi856);
        writeBodyTXT(sb, orderInfo, distEdi856);
        writeFooterTXT(sb, orderInfo, distEdi856);
        return sb.toString();
    }

    public void writeTitle(StringBuffer sb) {}

    private void writeToFile(String text, File file) throws Exception {

        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(file));
        outStream.write(text.getBytes());
        outStream.flush();
        outStream.close();

    }

    public void writeHeaderTXT(StringBuffer sb, OrderInfoDataView orderInfo, EdiInp856View distEdi856) {
        nextLine(sb);
        nextLine(sb);
        line.append(align("Order Shipment Notification",CENTER));
        nextLine(sb);
        OrderInfoView orderHeader =  orderInfo.getOrderInfo();
        String  poNum = distEdi856.getPurchOrderNum();
        line.append(align("Order: "+orderHeader.getOrderNum()+" PO: "+poNum,CENTER));
        nextLine(sb);
        String distName = distEdi856.getDistName();
        
        String trackingNum = distEdi856.getTrackingNum();
        Date shipDate = distEdi856.getShipDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateS = sdf.format(shipDate);
        String secondStr = "Shipped on "+dateS;
        
        if(Utility.isSet(distName)) {
            secondStr += " Distributor: "+distName;
        } 
        if(Utility.isSet(trackingNum)) {
            secondStr += " Tracking Number: "+trackingNum;
        } 
        line.append(align(secondStr,CENTER));
        nextLine(sb);
        nextLine(sb);
        nextLine(sb);

    }
    public void writeBodyTXT(StringBuffer sb, OrderInfoDataView orderInfo, EdiInp856View distEdi856) {

        String headerTable = "";

        line.append(getCharLine('_',DOC_LENGTH));
        nextLine(sb);
        nextLine(sb);
        headerTable += normalizeString("Sku",' ',9,LEFT);
        headerTable += normalizeString("Product Name",' ',22,CENTER);
        headerTable += normalizeString("PO Line",' ',5,CENTER);
        headerTable += normalizeString("Uom",' ',5,CENTER);
        headerTable += normalizeString("Qty",' ',5,CENTER);
        headerTable += normalizeString("Tracking Number",' ',22,CENTER);

        line.append(align(headerTable,CENTER));
        nextLine(sb);

        line.append(getCharLine('_',DOC_LENGTH));
        nextLine(sb);
        nextLine(sb);

        PairViewVector columnParam=new PairViewVector();

        columnParam.add(new PairView(new Integer(9), new Integer(LEFT)));
        columnParam.add(new PairView(new Integer(22), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(5), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(5), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(5), new Integer(CENTER)));
        columnParam.add(new PairView(new Integer(22), new Integer(RIGTH)));
        
        ItemInfoViewVector orderItems = orderInfo.getItems();
     
        EdiInp856ItemViewVector ediItems = distEdi856.getItems();
        for (Iterator it=ediItems.iterator(); it.hasNext();) {
            EdiInp856ItemView ediItem = (EdiInp856ItemView) it.next();
            ArrayList colsArray = new ArrayList();
            String distSkuNum = ediItem.getDistSkuNum();            
            colsArray.add(parseDelim(distSkuNum, null, 10-TABLE_COLUMN_SPACE));
            String skuName = "";
            if(distSkuNum!=null) {
                for(Iterator it1=orderItems.iterator(); it1.hasNext();) {
                    ItemInfoView orderItem = (ItemInfoView) it1.next();
                    if(ediItem.getOrderItemId()==orderItem.getOrderItemId()){
                        skuName = orderItem.getItemName();
                        break;
                    }
                }
            }
            ArrayList itemNameAL = parseDelim(skuName," ", 20-TABLE_COLUMN_SPACE);
            colsArray.add(itemNameAL);
            colsArray.add(parseDelim(""+ediItem.getPurchOrderLineNum(),null, 5-TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(ediItem.getUom(),null, 5-TABLE_COLUMN_SPACE));
            colsArray.add(parseDelim(""+ediItem.getShippedQty(),null, 5-TABLE_COLUMN_SPACE));

            List trackingNumList = ediItem.getTrackingNumList();
            String trackingNums = "";
            if(trackingNumList!=null && trackingNumList.size()>0) {
                int cnt = 0;    
                for(Iterator iter1=trackingNumList.iterator(); iter1.hasNext();) {
                   String tn = distEdi856.getCarrierName() + " " +(String) iter1.next();
                   cnt++;
                   if(cnt>1) {
                       trackingNums += " " + tn;
                   } else {
                       trackingNums = tn;
                   }
                }
            }
            
            colsArray.add(parseDelim(trackingNums, " ", 22-TABLE_COLUMN_SPACE));

            writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);

            nextLine(sb);
        }
        line.append(getCharLine('_',DOC_LENGTH));
        nextLine(sb);
    }


    public void writeFooterTXT(StringBuffer sb, OrderInfoDataView orderInfo, EdiInp856View distEdi856) {
        writeAddressInfo(sb,orderInfo.getShippingAddress(), distEdi856);
    }

    private void writeAddressInfo(StringBuffer sb,  AddressInfoView shippingAddress, EdiInp856View distEdi856) {

        String headerTable = "";
        nextLine(sb);
        nextLine(sb);
        headerTable += normalizeString("Ship To Information", ' ', DOC_LENGTH/2, LEFT);
        headerTable += normalizeString("Ship From Information",' ',DOC_LENGTH/2,LEFT);

        line.append(align(headerTable,CENTER));
        nextLine(sb);
        nextLine(sb);
        ArrayList colsArray = new ArrayList();
        PairViewVector columnParam=new PairViewVector();
        columnParam.add(new PairView(new Integer(DOC_LENGTH/2-TABLE_COLUMN_SPACE), new Integer(LEFT)));
        columnParam.add(new PairView(new Integer(DOC_LENGTH/2-TABLE_COLUMN_SPACE), new Integer(LEFT)));
        colsArray.add(parseDelim(shippingAddress.getAccountErpNum()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(" ",null, DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(shippingAddress.getStreetAddress()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(" ",null, DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(shippingAddress.getCity()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(distEdi856.getShipFromCity()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(shippingAddress.getStateProvinceCd()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(distEdi856.getShipFromState()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(shippingAddress.getPostalCode()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(distEdi856.getShipFromPostalCode()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        colsArray.add(parseDelim(shippingAddress.getCountry()," ", DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        colsArray.add(parseDelim(" ",null, DOC_LENGTH/2-TABLE_COLUMN_SPACE));
        writeArrayAsTableStyle(colsArray,sb,columnParam,CENTER);
        colsArray.clear();

        nextLine(sb);
    }


}

