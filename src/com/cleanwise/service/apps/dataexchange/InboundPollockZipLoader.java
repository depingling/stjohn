
package com.cleanwise.service.apps.dataexchange;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ErrorHolderView;
import com.cleanwise.service.api.value.ErrorHolderViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;

public class InboundPollockZipLoader extends InboundFlatFile {
   protected Logger log = Logger.getLogger(this.getClass());
	
   private final static String addBy = "pollockZipLoader";

   private Date runDate = new Date();

   public interface ITEM_META_NAME_VALUE {
       public static final String
               IMAGE = "IMAGE",
               MSDS = "MSDS",
               SPEC = "SPEC";
   }
   public interface ZIP_FILE_PATTERN {
       public static final String
               IMAGE = "image",
               MSDS = "msds",
               SPEC = "spec";
   }
   public interface DIR {
       public static final String
               IMAGE = "images",
               MSDS = "msds",
               SPEC = "spec";
   }
   private ZipFile zipFile = null;
   private String zipFileName;
   private String zipFilePattern;
   private final HashMap entryDataHM = new HashMap();
   private HashSet multipleItemsHS = new HashSet();
   private int total = 0;
   ErrorHolderViewVector errors = new ErrorHolderViewVector();


    

    private void closeZip() {
        try {
            zipFile.close();
            log.debug("zip file is closed: " + zipFile.getName());
        } catch (IOException ioe) {
            log.error("can not close zip file " + zipFile.getName());
            ioe.printStackTrace();
        }
    }
   protected void init() {
     super.init();
     entryDataHM.clear();
     errors.clear();
   }


   public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        zipFileName = getTranslator().getInputFilename();
        zipFilePattern = getTranslator().getTradingProfileConfig().getPattern();
        log.debug("translate() => BEGIN  : zipFileName = " + zipFileName + ", zipFilePattern = " + zipFilePattern);
        //------------------------------------------
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtilities.copyStream(pIn, outputStream);
        File tmpFile = File.createTempFile(zipFileName, IOUtilities.getFileExt(zipFileName),Translator.getIntegrationFileLogDirectory(true));
        log.debug("translate() => Writing output stream to tempfile: "+tmpFile.getAbsolutePath());
        FileOutputStream fOut = new FileOutputStream(tmpFile);
        outputStream.writeTo(fOut);
        zipFile = new ZipFile(tmpFile);
        //-------------------------------------
        IdVector storeIds = getStoreIds();

        HashMap<String, List<Integer>> distSkuItemIdHM = getDistSkuItemIdMap(storeIds);
        if (zipFile != null) {
          Enumeration entries = zipFile.entries();

          if (entries != null) {
            try {
              while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                if (verifyEntry(entryName, distSkuItemIdHM)){
                  entryDataHM.put(entryName, entry);
                  total++;
                }
              }
              doProcessing(distSkuItemIdHM);
            }
            catch (IOException ex) {
              log.error("translate() => FAILED.Process time at : "
                        + (System.currentTimeMillis() - startTime) + " ms", ex);
              setFail(true);
              throw ex;
            } finally{
              closeZip();
              if (outputStream!= null) outputStream.close();
              if (fOut != null){
                fOut.flush();
                fOut.close();
              }
              if (tmpFile != null) tmpFile.delete();
            }
          }

          log.debug("translate() => END. Loaded: " + total+" files. Process time at : "
                + (System.currentTimeMillis() - startTime) + " ms");
        }


    }

    protected void doProcessing(HashMap<String, List<Integer>> pDistSkuItemIdHM) throws Exception {
      // process Data
      Set distSkuSet = entryDataHM.keySet();
      if ( isNoErrors()){
        for (Iterator iter = distSkuSet.iterator(); iter.hasNext(); ) {
          String entryName = (String) iter.next();
          String distSku = entryName.substring(0,entryName.lastIndexOf("."));

          //Integer itemid = (Integer) pDistSkuItemIdHM.get(distSku);
          List<Integer> ids = pDistSkuItemIdHM.get(distSku);
          if(ids!=null){
	          Iterator<Integer> it = ids.iterator();
	          while(it.hasNext()){
	        	  Integer itemid = it.next();
		          if (itemid != null && itemid.intValue() > 0) {
		            uploadEntry(itemid.intValue(), entryName, (ZipEntry) entryDataHM.get(entryName));
		          }
	          }
          }
        }
      }
      for (int i = 0; errors != null && i < errors.size(); i++) {

          ErrorHolderView error = (ErrorHolderView) errors.get(i);
          if ("ERROR".equals(error.getType())) {
              total = 0;
              setFail(true);
              log.error(error.getType() + ":" + error.getMessage());
          }
      }

    }
    private boolean verifyEntry(String entryName, HashMap<String, List<Integer>> pDistSkuItemIdHM){
       if(entryName.startsWith(".")){
    	   //hidden file
    	   return false;
       }
       if(entryName.indexOf(".") < 0 ){
    	   processError("ERROR", "File name does not have a '.' to determine dist sku from");
       }else{
	       String distSku = entryName.substring(0,entryName.lastIndexOf("."));
	       //Integer id = (Integer) pDistSkuItemIdHM.get(distSku);
	       List<Integer> ids = pDistSkuItemIdHM.get(distSku);
	       if (ids != null) {
	    	   Iterator<Integer> it = ids.iterator();
	    	   while(it.hasNext()){
	    		   Integer anId = it.next();
	    		   if(anId == null || anId.intValue() == 0){
	    			   it.remove();
	    		   }
	    	   }
	       }
	       if (ids ==null || ids.size() == 0) {
	    	   //processError("ERROR", "No items found for Dist SKU: '" + distSku + "'");
	    	   log.error("No items found for Dist SKU: '" + distSku + "'");
	       }
       }
       return isNoErrors();
    }
    private void processError ( String pErrType, String pMessage){
       errors.add(new ErrorHolderView(pErrType, pMessage));
    }

    private boolean isNoErrors() {
      return errors.size() ==0;
    }

    public  void uploadEntry(int pItemId, String pEntryName, ZipEntry pZipEntry) throws Exception {

     InputStream lStream = zipFile.getInputStream(pZipEntry);
      String fileExt = pEntryName.substring(pEntryName.lastIndexOf("."));
      fileExt = fileExt.toLowerCase();
      if (lStream== null){
        processError("ERROR", "Data Content is null for File Name: " + pEntryName);
      }
      log.debug("-> uploadEntry() BEGIN : uploadFileName = " + pEntryName + " for Item Id= " + pItemId );

      String basepath =
          "/en/products/" + getDirByPattern() + "/"
          + String.valueOf(pItemId)
          + fileExt;
      // this is the absolute path where we will be writing
      String fileName = System.getProperty("webdeploy") + basepath;
      File file = new File(fileName);

      try {
           //retrieve the file data
           log.debug("->uploadFile()=> fileName= " + fileName);
           boolean boo = file.exists();
           log.debug("->uploadFile()=> exists= " + boo);
           boo = file.canRead();
           log.debug("->uploadFile()=> can read= " + boo);
           boo = file.canWrite();
           log.debug("->uploadFile()=> can write= " + boo);
           if (boo) {
             boo = file.delete();
             log.debug("->uploadFile()=> deleted= " + boo);
           }
           boo = file.createNewFile();
           log.debug("uploadFile()=> boo2= " + boo);
           OutputStream bos = new FileOutputStream(file);
           int bytesRead = 0;
           byte[] buffer = new byte[8192];
           while ( (bytesRead = lStream.read(buffer, 0, 8192)) != -1) {
             bos.write(buffer, 0, bytesRead);
           }
           bos.flush();
           bos.close();
           bos = null;
           //close the stream
           lStream.close();
           // updating CLW_ITEM_META
           updateItemMeta(pItemId, getItemMetaNameValueByPattern(), basepath);
           log.debug("->uploadEntry()=> END : basepath = " + basepath);
           
           APIAccess factory = APIAccess.getAPIAccess();
           
           // based off the value of the "storage.system.item" system property:
           // either add records to CLW_CONTENT DB Table or add binary data to E3 Storage
           String storageType = Utility.strNN(System.getProperty("storage.system.item"));
           log.debug("uploadEntry(): storageType1 = " + storageType);
           if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
           	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE;
           } else {
           	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE;
           }
           log.debug("uploadEntry(): storageType2 = " + storageType);
           log.debug("uploadEntry(): basepath1 = " + basepath);
           basepath = "." + basepath;
           log.debug("uploadEntry(): basepath2 = " + basepath);
           if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
        	   factory.getContentAPI().addContentSaveImageE3Storage(InetAddress.getLocalHost().getHostName(), basepath,getItemMetaNameValueByPattern());
           } else {   
       	       factory.getContentAPI().addContentSaveImage(InetAddress.getLocalHost().getHostName(), basepath,getItemMetaNameValueByPattern());
           }
       	   //cont.addContentSaveImage(host, basepath, "ItemImage");
         }  catch (IOException ioe) {
           ioe.printStackTrace();
           processError( "ERROR", "Error uploading file = " + fileName);
         }

       }
    
    
    
    

       private void updateItemMeta(int pItemId, String pName, String pValue) throws Exception {
         ItemMetaDataVector imDV = new ItemMetaDataVector();
         ItemMetaData imD = ItemMetaData.createValue();
         imD.setItemId(pItemId);
         imD.setNameValue(pName);
         imD.setValue(pValue);
         imDV.add(imD);
         Catalog catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();
         try {
           catalogEjb.updateItemMeta(pItemId, imDV, addBy);
         }
         catch (Exception ex) {
           ex.printStackTrace();
           log.error("ERROR:" + ex.getMessage());
           throw ex;
         }
       }

       private HashMap<String, List<Integer>> getDistSkuItemIdMap(IdVector pStoreIds) throws Exception  {
         Distributor distEjb = APIAccess.getAPIAccess().getDistributorAPI();
         int storeId =0;
         if (pStoreIds != null){
           storeId = ((Integer)pStoreIds.get(0)).intValue();
         }
         IdVector distrIds = distEjb.getDistributorIdsForStore(storeId, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
         if (distrIds == null) {
           processError("ERROR", "No distributors for store id : " + storeId);
         } else if (distrIds.size() >1){
           processError("ERROR", "Multiple distributors for store id : " + storeId);
         }
         int distrId = ((Integer)distrIds.get(0)).intValue();
         DBCriteria dbc = new DBCriteria();
         dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, distrId);
         dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

         ItemInformation itemInfoEjb = APIAccess.getAPIAccess().getItemInformationAPI();
         ItemMappingDataVector imDV = itemInfoEjb.getItemMappingsCollection(dbc);
         if (imDV == null || imDV.size() ==0 ){
            processError("ERROR", "No Items found for distributor of store id : " + storeId);
         }

         HashMap<String, List<Integer>> distItemIdHM = new HashMap<String, List<Integer>>();
         for (int i = 0; i < imDV.size(); i++) {
           ItemMappingData imD = (ItemMappingData)imDV.get(i);
           if (!distItemIdHM.containsKey(imD.getItemNum()) ) {
        	   List<Integer> ids = new ArrayList<Integer>();
        	   ids.add(imD.getItemId());
        	   distItemIdHM.put(imD.getItemNum(), ids);
           } else {
        	   List<Integer> ids = distItemIdHM.get(imD.getItemNum());
        	   ids.add(imD.getItemId());
           }
         }
          return distItemIdHM;

       }

       private IdVector getStoreIds() throws Exception {
            TradingPartner partnerEjb = APIAccess.getAPIAccess().getTradingPartnerAPI();
            TradingPartnerData partner = translator.getPartner();
            if (partner == null) {
                    throw new IllegalArgumentException(
                                    "Trading Partner ID cann't be determined");
            }
            HashMap assocMap = partnerEjb.getMapTradingPartnerAssocIds(partner
                            .getTradingPartnerId());
            if (assocMap != null) {
                    return (IdVector) assocMap
                                    .get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            }
            return null;
       }

       private String getDirByPattern(){
         if (ZIP_FILE_PATTERN.IMAGE.equals(zipFilePattern)){
           return DIR.IMAGE;
         } else if (ZIP_FILE_PATTERN.MSDS.equals(zipFilePattern)){
           return DIR.MSDS;
         } else if (ZIP_FILE_PATTERN.SPEC.equals(zipFilePattern)){
           return DIR.SPEC;
         }
         return "";
       }
       private String getItemMetaNameValueByPattern(){
         if (ZIP_FILE_PATTERN.IMAGE.equals(zipFilePattern)){
          return ITEM_META_NAME_VALUE.IMAGE;
        } else if (ZIP_FILE_PATTERN.MSDS.equals(zipFilePattern)){
          return ITEM_META_NAME_VALUE.MSDS;
        } else if (ZIP_FILE_PATTERN.SPEC.equals(zipFilePattern)){
          return ITEM_META_NAME_VALUE.SPEC;
        }
        return "";
       }
}
