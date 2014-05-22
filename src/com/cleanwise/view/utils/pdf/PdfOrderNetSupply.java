/*
 * PdfOrder.java
 *
 * Created on July 15, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;

import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;

import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.*;

import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

/**
 *Constructs a pdf document in the form of a pdf document.  It understands the various
 *propriatary objects, and uses them to construct a purchase order document.
 * @author  bstevens
 */
public class PdfOrderNetSupply extends PdfOrder {
    private static int SKU_COLUMN_WDTH_DEFAULT = 8;
    private static int MFG_SKU_COLUMN_WDTH_DEFAULT = 9;

  /** Creates a new instance of PdfOrder */
  public PdfOrderNetSupply() {
      super();
  }

  public void drawOrderHeader
    (Document document, CleanwiseUser appUser, CheckoutForm sForm,
     String pImageName, OrderJoinData pOrder) throws DocumentException {

    //add the logo
    try {
      Image i = Image.getInstance(pImageName);
      float x = document.leftMargin();
      float y = document.getPageSize().getHeight() - i.getHeight() - 5;
      i.setAbsolutePosition(x, y);
      document.add(i);
    } catch (Exception e) {
      e.printStackTrace();
    }

    PdfPTable header = new PdfPTable(1);
    header.setWidthPercentage(100);
    header.getDefaultCell().setBorder(borderType);
    header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

    //Display the contact info
    PdfPTable orderInfo = new PdfPTable(2);
    orderInfo.setWidthPercentage(100);
    orderInfo.setWidths(new int[] {30,70});
    orderInfo.getDefaultCell().setBorder(borderType);
    orderInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

    ProcessOrderResultData pod = sForm.getOrderResult();
    if (pod.getNext() == null) {
      String orderNumberStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.orderStatus.text.orderNumber:", null);
      orderInfo.addCell(makePhrase(orderNumberStr + " ", normal, false));
    } else {
      String orderNumbersStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.orderStatus.text.orderNumbers:", null);
      orderInfo.addCell(makePhrase(orderNumbersStr + " ", normal, false));
    }
    StringBuffer orderNumbers = new StringBuffer(pod.getOrderNum());
    while (pod.getNext() != null) {
      pod = pod.getNext();
      if (pod.getOrderNum() != null) { //should never be null
        orderNumbers.append(',');
        orderNumbers.append(pod.getOrderNum());
      }
    }
    orderInfo.addCell(makePhrase(orderNumbers.toString(), normal, true));

      // account number (customer major and customer minor)
      String custMaj = (String)getMiscProperty(RefCodeNames.PROPERTY_TYPE_CD.CUST_MAJ, "na");
      String locationNumber = "";
      PropertyData prop = appUser.getSite().getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
      if (prop != null) {
          locationNumber = prop.getValue();
      }
      String accountNumber = custMaj + "_" + locationNumber;
      String accountNumberStr =
              ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.accountNumber:",null);
      orderInfo.addCell(makePhrase(accountNumberStr+" ",normal,false));
      orderInfo.addCell(makePhrase(accountNumber,normal,true));


      // account name (site name)
      SiteData siteD = appUser.getSite();
      String siteName = siteD.getBusEntity().getShortDesc();
      String siteNameStr = ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.accountName:",null);
      orderInfo.addCell(makePhrase(siteNameStr+" ",normal,false));
      orderInfo.addCell(makePhrase(siteName,normal,true));

      // distributor name (member name, member major and member minor)
      String distrName = (String)getMiscProperty(Constants.DISTRIBUTOR_NAME, "");
      String  distrNameStr =
                ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.distributorName:",null);
      orderInfo.addCell(makePhrase(distrNameStr+" ",normal,false));
      orderInfo.addCell(makePhrase(distrName,normal,true));

      // po number
      String  poNumberStr =
                ClwI18nUtil.getMessage(getRequest(),"shop.orderStatus.text.poNumber:",null);
      orderInfo.addCell(makePhrase(poNumberStr+" ",normal,false));
      orderInfo.addCell(makePhrase(sForm.getPoNumber(), normal,true));

      // order date
      TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
      String pattern = ClwI18nUtil.getDatePattern(getRequest());
      SimpleDateFormat sdf = new SimpleDateFormat(pattern + " " + Constants.SIMPLE_TIME_PATTERN);
      if (timeZone != null){
        sdf.setTimeZone(timeZone);
      }

      Date orderDate;
      Date orderTime;
      OrderData order = pOrder.getOrder();
      if (order.getRevisedOrderDate() != null) {
          orderDate = order.getRevisedOrderDate();
          if (order.getRevisedOrderTime() != null) {
            orderTime = order.getRevisedOrderTime();
          } else {
            orderTime = order.getRevisedOrderDate();
          }
      } else {
          orderDate = order.getOriginalOrderDate();
          orderTime = order.getOriginalOrderTime();
      }
      orderDate = Utility.getDateTime(orderDate, orderTime);
      String orderDateS = sdf.format(orderDate);

      String orderDateStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.orderStatus.text.orderDate:", null);
      orderInfo.addCell(makePhrase(orderDateStr + " ", normal, false));
      orderInfo.addCell(makePhrase(orderDateS, normal, true));

      // delivery date
      String processOrderOnStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.orderStatus.text.deliveryDate:", null);
      OrderMetaDataVector orderMetaV = pOrder.getOrderMeta();

        String deliveryDateS = null;
        BigDecimal fuelSurcharge = new BigDecimal(0);
        BigDecimal smallOrderFee = new BigDecimal(0);
        if(orderMetaV!=null) {
            for(Iterator iter=orderMetaV.iterator(); iter.hasNext();) {
                OrderMetaData omD = (OrderMetaData) iter.next();
                if("Requested Ship Date".equals(omD.getName())) {
                    deliveryDateS = omD.getValue();
                }
                /*
                if("FUEL_SURCHARGE".equals(omD.getName())) {
                    String sss = omD.getValue();
                    if(sss!=null) {
                        try {
                            double amt = Double.parseDouble(sss);
                            fuelSurcharge = (new BigDecimal(amt)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        } catch (Exception exc){}
                    }
                }
                if("SMALL_ORDER_FEE".equals(omD.getName())) {
                    String sss = omD.getValue();
                    if(sss!=null) {
                        try {
                            double amt = Double.parseDouble(sss);
                            smallOrderFee = (new BigDecimal(amt)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        } catch (Exception exc){}
                    }
                }
                */
            }
        }

      orderInfo.addCell(makePhrase(processOrderOnStr + " ", normal, false));
      if(deliveryDateS==null) deliveryDateS = " ";
      orderInfo.addCell(makePhrase(deliveryDateS, normal, true));

      String shippingCommentsStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.orderStatus.text.shippingComments:", null);
      orderInfo.addCell(makePhrase(shippingCommentsStr + " ", normal, false));
      orderInfo.addCell(makePhrase(sForm.getComments(), normal, true));

    header.addCell(orderInfo);
    document.add(header);

  }

    public int getSkuColumnLength(CleanwiseUser appUser, List items){
        int result = SKU_COLUMN_WDTH_DEFAULT;
        if (items != null) {
            for (int j=0; j<items.size(); j++) {
              ShoppingCartItemData item = (ShoppingCartItemData)items.get(j);
              String sku = item.getActualSkuNum();
              int len = sku.length();
              if (len > result) {
                  result = len;
              }
            }
        }
        return result;
    }

    public int getMfgSkuColumnLength(CleanwiseUser appUser, List items){
        int result = MFG_SKU_COLUMN_WDTH_DEFAULT;
        if (items != null) {
            for (int j=0; j<items.size(); j++) {
              ShoppingCartItemData item = (ShoppingCartItemData)items.get(j);
              String sku = item.getProduct().getManufacturerSku();
              int len = sku.length();
              if (len > result) {
                result = len;
              }
            }
        }
        return result;
    }


    public void sortItems(List items) {
        // sort by clw_order_item.CUST_LINE_NUM
        Collections.sort(items, CUST_LINE_NUM);

    }

    static final Comparator CUST_LINE_NUM = new Comparator() {
            public int compare(Object o1, Object o2)
            {
                int num1 = ((ShoppingCartItemData)o1).getOrderNumber();
                int num2 = ((ShoppingCartItemData)o2).getOrderNumber();
                return num1 - num2;
            }
        };

    public boolean isShowCategories() {
        return false;
    }

}

