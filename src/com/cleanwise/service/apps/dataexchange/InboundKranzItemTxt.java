package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.*;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;

import com.cleanwise.service.apps.loaders.TabFileParser;

import java.rmi.RemoteException;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

public class InboundKranzItemTxt extends InboundItemTxt {
    protected Logger log = Logger.getLogger(this.getClass());

    private static boolean updateItemDescrFl = false;
    private static boolean updateUpcFl = false;

    protected void doPostProcessing() throws Exception {
log.info("doPostProcessing started. Items qty: "+items.size());
        int lineNum = 1;
        for(Iterator iter = items.iterator(); iter.hasNext(); lineNum++) {
            Integer lineNumI = new Integer(lineNum);
            NscPcView nscProdVw = (NscPcView) iter.next();
            String shortDesc1 = nscProdVw.getItemDescription1();
            if(!Utility.isSet(shortDesc1)) {
               missingShortDesc1HS.add(lineNumI);
            }
            if(shortDesc1.trim().toUpperCase().startsWith("DELETED")) {
                iter.remove();
                continue;
            }
            if(shortDesc1.trim().toUpperCase().startsWith("FUEL SURCHARGE")) {
                fuelSurchargeHS.add(nscProdVw);
                iter.remove();
                continue;
            }


            String catalogName = Utility.strNN(nscProdVw.getCatalogName()).trim();
            if(!catalogHM.containsKey(catalogName) &&
               !missingCatalogNameHS.contains(catalogName)) {
               missingCatalogNameHS.add("'"+catalogName+"' ("+lineNumI+")");
            }
            String manufName = Utility.strNN(nscProdVw.getManufacturer()).trim();
            if(!manufBeHM.containsKey(manufName) &&
               !missingManufNameHS.contains(manufName)) {
               missingManufNameHS.add(manufName);
            }
            String category = nscProdVw.getCategory();
            if(!Utility.isSet(category)) {
                missingCategoryHS.add(lineNumI);
            }
            if(!categoryHS.contains(category)) {
                categoryHS.add(category);
            }
            String uom = nscProdVw.getDefaultUom();
            if(!Utility.isSet(uom)) {
                missingUomHS.add(lineNumI);
            }
//            String longDesc = nscProdVw.getExtendetItemDescription(); // for Kranz
            String longDesc = nscProdVw.getItemDescription1()+" "+ nscProdVw.getItemDescription2();
            if(!Utility.isSet(longDesc)) {
                missingLongDescHS.add(lineNumI);
            }
            String price = nscProdVw.getListPrice1();
            if(!Utility.isSet(price)) {
                missingPriceHS.add(lineNumI);
            }
            try {
                Double.parseDouble(price);
            } catch (Exception exc) {
                missingPriceHS.add(lineNumI);
            }
            String manufSku = nscProdVw.getMfgSku();
            if(!Utility.isSet(manufSku)) {
                missingManufSkuHS.add(lineNumI);
            }
//            String distSku = nscProdVw.getNscItemNumber();
            String distSku = nscProdVw.getDistItemNumber();   // for Kranz
            if(!Utility.isSet(distSku)) {
                missingDistSkuHS.add(lineNumI);
            }
            String pack = nscProdVw.getStandartPack();
            if(!Utility.isSet(pack)) {
                missingPackHS.add(lineNumI);
            }
/* Optional field
            String upcCode = nscProdVw.getUpc();
            if(!Utility.isSet(upcCode)) {
                missingUpcCodeHS.add(lineNumI);
            }
*/

        }
        //Creat missing manufacturers
        if(missingManufNameHS.size()>0) {
            for(Iterator iter=missingManufNameHS.iterator(); iter.hasNext();) {
                String manufName = (String) iter.next();
                if(!manufBeHM.containsKey(manufName)) {
    log.info("Creating manufacturer: "+ manufName);
                    BusEntityData mBeD = new BusEntityData();
                    mBeD.setShortDesc(manufName);
                    mBeD.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                    mBeD.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
                    mBeD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
                    mBeD.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
                    mBeD.setAddBy("Nsc Item Loader");
                    mBeD.setModBy("Nsc Item Loader");
                    ManufacturerData mD = new ManufacturerData(
                        mBeD,
                        storeId,
                        null,
                        null,
                        null,
                        null,
                        null, // pBusinessClass,
                        null, // PropertyData pWomanOwnedBusiness,
                        null, // PropertyData pMinorityOwnedBusiness,
                        null, // PropertyData pJWOD,
                        null, // PropertyData pOtherBusiness,
                        null, // PropertyDataVector pSpecializations,
                        null, // PropertyDataVector pMiscProperties,
                        null // PropertyData pOtherNames
                        );
                    mD = manufacturerEjb.addManufacturer(mD);
                    manufBeHM.put(manufName,mD.getBusEntity());
                }
            }
        }

        if(missingCatalogNameHS.size()>0 ||
           missingCategoryHS.size()>0 ||
           missingUomHS.size()>0 ||
           missingLongDescHS.size()>0 ||
           missingShortDesc1HS.size()>0 ||
           missingPriceHS.size()>0 ||
           missingManufSkuHS.size()>0 ||
           missingDistSkuHS.size()>0 ||
           missingPackHS.size()>0 ||
           missingUpcCodeHS.size()>0
           ) {
            String errorMess = "Error(s): ";
            if(missingCatalogNameHS.size()>0) {
                errorMess += "\r\n Not found catalogs: ";
                for(Iterator iter=missingCatalogNameHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }

            if(missingCategoryHS.size()>0) {
                errorMess += "\r\n Empty Category. Line numbers: ";
                for(Iterator iter=missingCategoryHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
            if(missingUomHS.size()>0) {
                errorMess += "\r\n Empty Standard UOM. Line numbers: ";
                for(Iterator iter=missingUomHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
            if(missingLongDescHS.size()>0 ) {
                errorMess += "\r\n Empty Product and Item Descriptions. Line numbers: ";
                for(Iterator iter=missingLongDescHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
            if(missingShortDesc1HS.size()>0) {
                errorMess += "\r\n Empty Product Description. Line numbers: ";
                for(Iterator iter=missingShortDesc1HS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
            if(missingPriceHS.size()>0) {
                errorMess += "\r\n Empty or invalid Price. Line numbers: ";
                for(Iterator iter=missingPriceHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
            if(missingManufSkuHS.size()>0) {
                errorMess += "\r\n Empty Manufacturer Item Number (SKU). Line numbers: ";
                for(Iterator iter=missingManufSkuHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
            if(missingDistSkuHS.size()>0) {
                errorMess += "\r\n Empty DIST Item Number. Line numbers: ";
                for(Iterator iter=missingDistSkuHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
            if(missingPackHS.size()>0) {
                errorMess += "\r\n Empty Pack. Line numbers: ";
                for(Iterator iter=missingPackHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
 /*           if(missingUpcCodeHS.size()>0) {
                errorMess += "\r\n Empty upc code property. Line numbers: ";
                for(Iterator iter=missingUpcCodeHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }
*/
            log.info("BBBBBBBBBB"+errorMess);
            Logger log = Logger.getLogger(this.getClass());
            log.info("AAAAAAAAAAAA "+errorMess);

            throw new Exception(errorMess);
        }
        //Request-create categories
        categoryHM = catalogEjb.createOrRequestStoreCategories(storeCatalogId,categoryHS,"Item Loader" );


        /*
        */
        ProductDataVector productDV = new ProductDataVector();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date effDate = sdf.parse(sdf.format(new Date()));
        HashMap nscItemNumHM = new HashMap();
        for(Iterator iter = items.iterator(); iter.hasNext(); ) {
            NscPcView nscProdVw = (NscPcView) iter.next();
            ProductData pD = processItemRecord(nscProdVw, storeId, effDate);
            productDV.add(pD);
            if (Utility.isSet(nscProdVw.getDistItemNumber()) && Utility.isSet(nscProdVw.getNscItemNumber()) ){
              nscItemNumHM.put(nscProdVw.getDistItemNumber(), nscProdVw.getNscItemNumber());
            }
        }

        //Create or udpate items, adjust catalogs
        catalogEjb.createOrUpdateNscProducts(storeId, storeCatalogId, distributorId,productDV, "Item Loader", nscItemNumHM, updateItemDescrFl, updateUpcFl);

    }


    public ProductData processItemRecord(NscPcView nscProdVw, int storeId, Date effDate)
        throws Exception {
           ProductData productD= super.processItemRecord(nscProdVw, storeId, effDate);
           String distSku = Utility.strNN(nscProdVw.getDistItemNumber()).trim();
           String longDesc = Utility.strNN(nscProdVw.getItemDescription1()+" "+ nscProdVw.getItemDescription2()).trim();
           if (Integer.parseInt(nscProdVw.getCompany())!= 1){
             /** @todo set Company */
           }
           productD.setDistributorSku(distributorId, distSku);
           ItemData itemD = productD.getItemData();
           itemD.setLongDesc(longDesc);
        return productD;
    }

}
