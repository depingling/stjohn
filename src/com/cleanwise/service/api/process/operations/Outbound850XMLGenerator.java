package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.io.File;
import java.math.BigDecimal;


public class Outbound850XMLGenerator extends com.cleanwise.service.api.process.operations.OutboundOrderXML implements FileGenerator {



    private String generateXMLForEmail(int orderId, File file) throws Exception {

      OrderInfoDataView data = getData(orderId);
      Document doc = genXML(data);
      writeToFile(doc,file);
      return file.getAbsolutePath();

    }

    private void writeToFile(Document doc, File file) throws Exception {

        BufferedWriter outStream = new BufferedWriter(new FileWriter(file));
        outStream.write(doc.asXML());
        outStream.flush();
        outStream.close();

    }

    public OrderInfoDataView getData(int orderId) throws Exception {
        APIAccess factory = new APIAccess();
        Order orderEjb = factory.getOrderAPI();
        return orderEjb.getOrderInfoData(orderId);
    }

    private  Document genXML(OrderInfoDataView orderInfoData) {
        Document theXmlDoc = DocumentFactory.getInstance().createDocument();
        Element root = theXmlDoc.addElement("root");
        addElementsToRoot(root,orderInfoData);
        return theXmlDoc;
    }

    private  void addElementsToRoot(Element root,OrderInfoDataView orderInfoData) {
        Element order = root.addElement("Order");
        addDataToOrder(order,orderInfoData);
        addElementsToOrder(order,orderInfoData);
    }

    private  void addDataToOrder(Element orderEl, OrderInfoDataView orderInfoData) {

        OrderInfoView orderInfoView = orderInfoData.getOrderInfo();

        createElementIfTextNotEmpty(orderEl,"ORDER_NUM",orderInfoView.getOrderNum());
        createElementIfTextNotEmpty(orderEl,"ORDER_DATE",orderInfoView.getOrderDate().toString());
        createElementIfTextNotEmpty(orderEl,"PO_NUMBER",orderInfoView.getPoNumber());
        createElementIfTextNotEmpty(orderEl,"SOURCE",orderInfoView.getSource());
        createElementIfTextNotEmpty(orderEl,"CONTACT_NAME",orderInfoView.getContactName());
        createElementIfTextNotEmpty(orderEl,"CONTACT_PHONE",orderInfoView.getContactPhone());
        createElementIfTextNotEmpty(orderEl,"CONTACT_EMAIL",orderInfoView.getContactEmail());
        createElementIfTextNotEmpty(orderEl,"PLACED_BY",orderInfoView.getPlacedBy());
        createElement(orderEl,"COMMENTS",orderInfoView.getComments());
        createElementIfTextNotEmpty(orderEl,"SUBTOTAL",orderInfoView.getSubtotal().toString());
        createElementIfTextNotEmpty(orderEl,"FREIGHT",orderInfoView.getFreight().toString());
        createElementIfTextNotEmpty(orderEl,"MISC_CHARGE",orderInfoView.getMiscCharge().toString());
        createElementIfTextNotEmpty(orderEl,"TAX",orderInfoView.getTax().toString());
        createElementIfTextNotEmpty(orderEl,"TOATAL",orderInfoView.getToatal().toString());

    }

    private  void addElementsToOrder(Element order, OrderInfoDataView orderInfoData) {
        Element address = order.addElement("Address");
        addElementsToAddress(address,orderInfoData);
        Element itemsEl = order.addElement("Items");
        addElementsToItems(itemsEl,orderInfoData.getItems());

    }

    private  void addElementsToItems(Element itemsEl, ItemInfoViewVector items) {
        if(items!=null)
        {
            Iterator it = items.iterator();
            while(it.hasNext()) {
            Element itemLinesEl = itemsEl.addElement("Items_lines");
            Object item = it.next();
            addDataToItemLines(itemLinesEl,(ItemInfoView)item);
            }
        }
    }

    private  void addDataToItemLines(Element itemLinesEl, ItemInfoView itemInfo) {


        createElementIfTextNotEmpty(itemLinesEl,"SKU_NUM",itemInfo.getSkuNum());
        createElementIfTextNotEmpty(itemLinesEl,"ITEM_NAME_",itemInfo.getItemName());
        createElementIfTextNotEmpty(itemLinesEl,"ITEM_SIZE",itemInfo.getItemSize());
        createElementIfTextNotEmpty(itemLinesEl,"UOM",itemInfo.getUom());
        createElementIfTextNotEmpty(itemLinesEl,"PACK",itemInfo.getPack());
        createElementIfTextNotEmpty(itemLinesEl,"COST",getActualCost(itemInfo).toString());
        createElementIfTextNotEmpty(itemLinesEl,"QTY",itemInfo.getQty().toString());
        createElementIfTextNotEmpty(itemLinesEl,"MANUFACTURER",itemInfo.getManufacturer());



    }

    private  void addElementsToAddress(Element address, OrderInfoDataView orderInfoData) {

        Element shippingAddressLine = address.addElement("Address_Line");
        addDataToAddressLine(shippingAddressLine,orderInfoData.getShippingAddress());
        Element billingAddressLine = address.addElement("Address_Line");
        addDataToAddressLine(billingAddressLine,orderInfoData.getBillingAddress());

    }

    private  void addDataToAddressLine(Element addressLine, AddressInfoView address) {

        createElementIfTextNotEmpty(addressLine,"ADDRESS_TYPE_CD",address.getAddressTypeCd());
        createElementIfTextNotEmpty(addressLine,"NAME_NUM",address.getAccountErpNum());
        createElementIfTextNotEmpty(addressLine,"STREET_ADDRESS",address.getStreetAddress());
        createElementIfTextNotEmpty(addressLine,"CITY",address.getCity());
        createElementIfTextNotEmpty(addressLine,"STATE_PROVINCE_CD",address.getStateProvinceCd());
        createElementIfTextNotEmpty(addressLine,"POSTAL_CODE",address.getPostalCode());
        createElementIfTextNotEmpty(addressLine,"COUNTRY",address.getCountry());

     }

    public String generate(Object data, String fileName) throws Exception {
        return generate(((Integer) data).intValue(),
                                       new File(fileName.indexOf(".")>0?fileName:fileName+".xml"));
    }

	public String generate(Object data, File file) throws Exception {
		return generateXMLForEmail(((Integer) data).intValue(), file);
	}
	
	protected BigDecimal getActualCost(ItemInfoView pItem) {
        if (pItem.getCustCost() != null) {
            return pItem.getCustCost();
        } else {
            return Utility.bdNN(pItem.getCost());
        }
    }


}





