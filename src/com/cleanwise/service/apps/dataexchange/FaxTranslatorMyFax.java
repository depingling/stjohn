/*
 * FaxTranslatorMyFax.java
 *
 * Created on January 19, 2009, 9:28 AM
 * 
 * @scher
 * 
 */

package com.cleanwise.service.apps.dataexchange;
//import RightFAX.RFDoc; //comment in future

import com.cleanwise.service.apps.dataexchange.PdfPurchaseOrderMyFax1;

import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.StoreData;
//import com.cleanwise.view.utils.ClwCustomizer; //commented by SVC 2/24/2009

import com.cleanwise.service.api.util.Utility;

//import RightFAX.RFEncode64; //comment in future

import org.apache.axis.encoding.Base64; //new by SVC 2/15/2009

import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;


/**
 * Designed to translate our proprietary objects into the MyFax proprietary objects.  
 * Utilizes the PDF package to construct it's documents.  Changes to the formating should
 * be made in the com.cleanwise.view.utils.pdf package.
 * @author  scher
 */
public class FaxTranslatorMyFax{        
    private FaxConfigMyFax mConfig;
    
    /** 
     *constructs a new FaxTranslator with the given configuration settings
     *@Param pConfig The configuration object to use.
     */
    public FaxTranslatorMyFax (FaxConfigMyFax pConfig){
        mConfig = pConfig;
    }
    
    // added by Sergei V. Cher: new empty constructor
    public FaxTranslatorMyFax (){
  
    }
    
    /**
     *Takes in a file name and constructs a FAX document out of it.  Must be one of the
     *supported document types: pdf, rtf, txt.  Determines the file type by the extension,
     *if the document type extension does not match the doc type this extension mapping
     *can be overidden using the overloaded method:<br>
     *constructFromFileName(pFileName, pFileType)
     *@Param pFile The file to convert.
     *@throws FaxException if the file extension is unrecognized.
     *@throws IOException if there is a problem reading in the file.
     *@throws FileNotFoundException if the file could not be found, or was not a file.
     */
    //public FaxDocument constructFromFileName(File pFile)
    public String constructFromFileName(File pFile)
    throws FaxExceptionMyFax,IOException,FileNotFoundException{
        String lFileName = pFile.getName();
        String ext = lFileName.substring(lFileName.lastIndexOf('.') + 1);
        return constructFromFileName(pFile,ext);
    }
    
    /**
     *Takes in a file name and constructs a FAX document out of it using the fileType supplied.
     *Must be one of the supported document types: PDF, RTF, TXT. 
     *@Param pFile The file to convert.
     *@Param pFileType The file type, roughly equivalent to the extension of the file.
     *@throws FaxException if the file extension is unrecognized.
     *@throws IOException if there is a problem reading in the file.
     *@throws FileNotFoundException if the file could not be found, or was not a file.
     */
    //public FaxDocument constructFromFileName(File pFile, String pFileType)
    public String constructFromFileName(File pFile, String pFileType)
    throws FaxExceptionMyFax,IOException,FileNotFoundException{
        String[] noConversionRequieredFileTypes = {"txt","pdf","rtf"};
        boolean foundType = false;
        for (int i=0;i<noConversionRequieredFileTypes.length;i++){
            if (pFileType.equalsIgnoreCase(noConversionRequieredFileTypes[i])){
                foundType = true;
            }
        }
        if (foundType == false){
            throw new FaxExceptionMyFax ("File type supplied: " + pFileType + " is not a known " +
                "file type.");
        }
        
        
        FileInputStream fis = new FileInputStream(pFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        int size = bis.available();
        byte[] byteArray = new byte[size];
        bis.read(byteArray);
        
        //String body = RFEncode64.encode64(byteArray,byteArray.length); // old piece of code: commented by SVC
        String body = org.apache.axis.encoding.Base64.encode(byteArray); // new: SVC
        
        /***
        FaxDocument lRetVal = new FaxDocument();
        RFDoc lDoc = lRetVal.getFaxDocument();
        lDoc.setBody(body,pFileType.toUpperCase());
        lDoc.setBodyEncoding(RFDoc.ENCODING_BASE64);
        return lRetVal;
        ***/
        return body;
    }
    
    /**
     *Takes in an instance of @see OutboundEDIRequestData, @see DistributorData, and @see StoreData
     *and converts them into an out bound purchase order FAX data.  This method uses the PDF
     *conversion classes to generate a PDF document which the FAX server understands.
     *@Param pDist the distributor this FAX is going to.
     *@Param p850s the purchase orders to translate.
     *@Param pStore the store this PO is coming from.
     *@Param pSender the sender for this FAX.
     *@Throws FaxException if there were errors constructing the document.
     *@Returns FaxDocument containing the constructed FAX ready to be sent by @see FaxAdaptor.
     */
    public String constructOutboundPurchaseOrder(DistributorData pDist, 
        OutboundEDIRequestDataVector p850s, StoreData pStore, String pSender) throws FaxExceptionMyFax{
        //FaxDocument lRetVal = new FaxDocument(); // change(?)/delete(?) in future
        String doc = new String();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfPurchaseOrderMyFax1 pdf = new PdfPurchaseOrderMyFax1();
            
            String localeCd = pDist.getBusEntity().getLocaleCd();
            if (localeCd == null || localeCd.equals("")){
                localeCd = pStore.getBusEntity().getLocaleCd();
                if (localeCd == null || localeCd.equals("")){
                    //if all else fails default to en_US locale
                    localeCd = "en_US";
                }
            }
            //gets the language code to use when constructing the image name
            String lImageName = ClwCustomizerMyFax.getLogoPathForPrinterDisplay(pStore);
            pdf.constructPdfPO(pDist,p850s,pStore,out,lImageName);
            doc = new sun.misc.BASE64Encoder().encodeBuffer(out.toByteArray());
            byte[] outArray = out.toByteArray();
            //doc = RFEncode64.encode64(outArray,outArray.length); //commented by SVC
            doc = org.apache.axis.encoding.Base64.encode(outArray); //new by SVC
            
            //uncomment to write PDF to a file on hard disk for debugging
            //java.io.FileOutputStream out2 = new java.io.FileOutputStream("C:\\test.pdf");
            //out2.write(new sun.misc.BASE64Decoder().decodeBuffer(doc));
            //out2.flush();
            //out2.close();
            
        }catch (Exception e){
            //lRetVal.setSuccess(Boolean.FALSE);
        	e.printStackTrace();
        }
        
        /***
        RFDoc lDoc = lRetVal.getFaxDocument();
        lDoc.setBody(doc,"PDF");
        lDoc.setBodyEncoding(RFDoc.ENCODING_BASE64);
        //name,empId,company
        //dept,phone,e-mail
        //bill1,bill2,replyURL,RFUser
        lDoc.setSenderInfo(pStore.getStoreBusinessName().getValue(),"",pStore.getStoreBusinessName().getValue(),
            "",pStore.getPrimaryPhone().getPhoneNum(),"",
            "","","",pSender);
        return lRetVal;
        ***/
        return doc;
    }
    
}
