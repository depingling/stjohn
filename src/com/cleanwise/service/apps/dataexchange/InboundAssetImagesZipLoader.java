
package com.cleanwise.service.apps.dataexchange;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.AssetContentDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ResizeImage;
import com.cleanwise.service.api.value.AssetContentData;
import com.cleanwise.service.api.value.AssetContentDetailView;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.AssetSearchCriteria;
import com.cleanwise.service.api.value.ContentDetailView;
import com.cleanwise.service.api.value.ErrorHolderView;
import com.cleanwise.service.api.value.ErrorHolderViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.UserData;

public class InboundAssetImagesZipLoader extends InboundFlatFile {

   private final static String addBy = "AssetImagesZipLoader";

   private Date runDate = new Date();

   public interface ITEM_META_NAME_VALUE {
       public static final String
               IMAGE = "IMAGE",
               MSDS = "MSDS",
               SPEC = "SPEC";
   }
   public interface ZIP_FILE_PATTERN {
       public static final String
               MASTER_IMAGE = "MasterImage",
               ASSET_IMAGE = "PhysicalAssetImage",
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


    protected Logger log = Logger.getLogger(this.getClass());

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
        log.info("translate() => BEGIN  : zipFileName = " + zipFileName + ", zipFilePattern = " + zipFilePattern);
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

        HashMap entryNameToIdHM = getEntryNameToIdMap(storeIds);

        if (zipFile != null) {
          Enumeration entries = zipFile.entries();

          if (entries != null) {
            try {
              while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                if (verifyEntry(entryName, entryNameToIdHM)){
                  entryDataHM.put(entryName, zipFile.getInputStream(entry));
                  total++;
                }
              }
              doProcessing(entryNameToIdHM);
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

          log.info("translate() => END. Loaded: " + total+" files. Process time at : "
                + (System.currentTimeMillis() - startTime) + " ms");
        }


    }

    protected void doProcessing(HashMap pEntryNameToIdHM) throws Exception {
      // process Data
      Set keySet = entryDataHM.keySet();
      if ( isNoErrors()){
        for (Iterator iter = keySet.iterator(); iter.hasNext(); ) {
          String entryName = (String) iter.next();
          String entryKey = entryName.substring(0,entryName.lastIndexOf("."));

          Integer entryId = (Integer) pEntryNameToIdHM.get(entryKey);
          if (entryId != null && entryId.intValue() > 0) {
            uploadEntry(entryId.intValue(), entryName, (InputStream) entryDataHM.get(entryName));
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
    private boolean verifyEntry(String entryName, HashMap pEntryNameToIdHM){
       String entryType =(getItemMetaNameValueByPattern().length() == 0)? "assets" : "items";
       String entryKey = entryName.substring(0,entryName.lastIndexOf("."));
       Integer id = (Integer) pEntryNameToIdHM.get(entryKey);
       if (id == null || id.intValue() == 0) {
         processError("ERROR", "No " +entryType +" found for Entry: '" + entryName + "'");
       } else if (multipleItemsHS.contains(entryKey)){
         processError("ERROR", "Multiple " +entryType +" found for Entry: '" + entryName + "'");
       }

       return isNoErrors();
    }
    private void processError ( String pErrType, String pMessage){
       errors.add(new ErrorHolderView(pErrType, pMessage));
    }

    private boolean isNoErrors() {
      return errors.size() ==0;
    }

    public void uploadAssetEntry(int pAssetId, String pEntryName, InputStream pStream) throws Exception {
      if (pStream== null){
        processError("ERROR", "Data Content is null for File Name: " + pEntryName);
      }
      String fileExt = pEntryName.substring(pEntryName.lastIndexOf("."));
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        IOUtilities.copyStream(pStream, outputStream);
        //-----------------------------------------------
        ResizeImage.resizeToSquare(pStream,200,outputStream,fileExt.substring(1));
//log.info("uploadAssetEntry() => outputStream.size() = "+ outputStream.size());
        byte[] buffer = outputStream.toByteArray();
        pStream.close();
        //--------------------------
        Asset assetEJB = APIAccess.getAPIAccess().getAssetAPI();

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(AssetContentDataAccess.ASSET_ID, pAssetId);
        dbc.addEqualTo(AssetContentDataAccess.TYPE_CD, RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE);

        AssetContentDetailView mainAssetImage = assetEJB.getAssetContentDetails(dbc);
        UserData user = UserData.createValue();
        user.setAddBy(this.addBy);
        user.setModBy(this.addBy);
        log.info("uploadEntry => begin");

        if (mainAssetImage == null) {
            log.info("uploadEntry => createNew");
            mainAssetImage = createAssetImage(pAssetId, addBy);
            if (buffer!= null && buffer.length > 0 ) {
                mainAssetImage.getContent().setPath(pEntryName);
                mainAssetImage.getContent().setData(buffer);
                mainAssetImage.getContent().setContentTypeCd("image/pjpeg");

                log.info("uploadEntry =>  mainAssetImage:" + mainAssetImage);
                mainAssetImage = assetEJB.updateAssetContent(mainAssetImage, user);
            }
        } else {
            log.info("uploadEntry => update");
            if (buffer!= null && buffer.length > 0 ) {
                mainAssetImage.getContent().setPath(pEntryName);
                mainAssetImage.getContent().setData(buffer);
                mainAssetImage.getContent().setContentTypeCd("image/pjpeg");

                log.info("uploadEntry =>  mainAssetImage:" + mainAssetImage);
                mainAssetImage = assetEJB.updateAssetContent(mainAssetImage, user);
            }
        }

    }
    private  AssetContentDetailView createAssetImage(int pAssetId, String pAddBy) throws Exception {

         AssetContentData assetContentData = AssetContentData.createValue();

         assetContentData.setAssetId(pAssetId);
         assetContentData.setAssetContentId(0);
         assetContentData.setUrl("");
         assetContentData.setAddBy(pAddBy);
         assetContentData.setModBy(pAddBy);
         assetContentData.setAddDate(null);
         assetContentData.setModDate(null);
         assetContentData.setTypeCd(RefCodeNames.ASSET_CONTENT_TYPE_CD.ASSET_IMAGE);

         ContentDetailView content = ContentDetailView.createValue();
         content.setContentId(0);
         content.setBusEntityId(0);
         content.setItemId(0);

         content.setContentStatusCd(RefCodeNames.CONTENT_STATUS_CD.ACTIVE);
         content.setLanguageCd("x");
         content.setLocaleCd("x");
         content.setContentUsageCd("N/A");
         content.setSourceCd("N/A");
         content.setLongDesc(null);
         content.setShortDesc("Asset Image");
         content.setAddBy(pAddBy);
         content.setModBy(pAddBy);
         content.setAddDate(null);
         content.setModDate(null);

         content.setEffDate(null);
         content.setExpDate(null);
         content.setVersion(1);

         return new AssetContentDetailView(content, assetContentData);
     }

    public  void uploadItemEntry(int pItemId, String pEntryName, InputStream pStream) throws Exception {

      String fileExt = pEntryName.substring(pEntryName.lastIndexOf("."));
      if (pStream== null){
        processError("ERROR", "Data Content is null for File Name: " + pEntryName);
      }
      log.info("-> uploadEntry() BEGIN : uploadFileName = " + pEntryName + " for Item Id= " + pItemId );

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

           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           //        IOUtilities.copyStream(pStream, outputStream);
           //-----------------------------------------------
           ResizeImage.resizeToSquare(pStream,200,outputStream,fileExt.substring(1) );
           byte[] buffer = outputStream.toByteArray();
           OutputStream bos = new FileOutputStream(file);
           bos.write(buffer);

           bos.close();
           bos.flush();
           bos = null;
           pStream.close();

/*           OutputStream bos = new FileOutputStream(file);
           int bytesRead = 0;
           byte[] buffer = new byte[8192];
           while ( (bytesRead = pStream.read(buffer, 0, 8192)) != -1) {
             bos.write(buffer, 0, bytesRead);
           }
           bos.close();
           bos.flush();
           bos = null;
           //close the stream
           pStream.close();
*/
           // updating CLW_ITEM_META
           updateItemMeta(pItemId, getItemMetaNameValueByPattern(), basepath);
           log.debug("->uploadEntry()=> END : basepath = " + basepath);
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

      private void uploadEntry(int pEntryId, String pEntryName, InputStream pStream) throws Exception {
        if (this.getItemMetaNameValueByPattern().length() == 0) {
          uploadAssetEntry(pEntryId, pEntryName, pStream);
        } else {
          uploadItemEntry(pEntryId, pEntryName, pStream);
        }
      }

       private HashMap getEntryNameToIdMap(IdVector pStoreIds) throws Exception  {
         if (this.getItemMetaNameValueByPattern().length() == 0) {
           return getNumAssetIdMap(pStoreIds);
         } else {
           return getDistSkuItemIdMap(pStoreIds);
         }
       }

       private HashMap getNumAssetIdMap(IdVector pStoreIds) throws Exception  {
         Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();
         int storeId =0;
         if (pStoreIds != null){
           storeId = ((Integer)pStoreIds.get(0)).intValue();
         }

         HashMap assocCds = new HashMap();
         assocCds.put(RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);

         AssetSearchCriteria adbc = new AssetSearchCriteria();
         adbc.setBusEntityIds(pStoreIds);
         adbc.setAssetAssocCds(assocCds);
         adbc.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.ASSET);

         AssetDataVector aDV = assetEjb.getAssetDataByCriteria(adbc);
         if (aDV == null || aDV.size() ==0 ){
             processError("ERROR", "No Assets found for store id : " + storeId);
         }


         HashMap numAssetIdHM = new HashMap();
         for (int i = 0; i < aDV.size(); i++) {
           AssetData aD = (AssetData)aDV.get(i);
           if (!numAssetIdHM.containsKey(aD.getAssetNum()) ) {
//             numAssetIdHM.put(aD.getAssetNum(), new Integer(aD.getAssetId()));
             numAssetIdHM.put(aD.getManufSku(), new Integer(aD.getAssetId()));
           } else {
             multipleItemsHS.add(aD.getAssetNum());
           }
         }
          return numAssetIdHM;

       }

       private HashMap getDistSkuItemIdMap(IdVector pStoreIds) throws Exception  {
          Distributor distEjb = APIAccess.getAPIAccess().getDistributorAPI();
          int storeId =0;
          if (pStoreIds != null){
            storeId = ((Integer)pStoreIds.get(0)).intValue();
          }
          IdVector distrIds = distEjb.getDistributorIdsForStore(storeId);
          if (distrIds == null) {
            processError("ERROR", "No distributors for store id : " + storeId);
          } else if (distrIds.size() >1){
            processError("ERROR", "Multiple distributors for store id : " + storeId);
          }
          ItemMappingDataVector imDV = new ItemMappingDataVector();
          if (distrIds != null && distrIds.size() >0) {
            int distrId = ( (Integer) distrIds.get(0)).intValue();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, distrId);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                           RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
            ItemInformation itemInfoEjb = APIAccess.getAPIAccess().getItemInformationAPI();
            imDV = itemInfoEjb.getItemMappingsCollection(dbc);
          }
          if (imDV == null || imDV.size() ==0 ){
             processError("ERROR", "No Items found for distributor of store id : " + storeId);
          }

          HashMap distItemIdHM = new HashMap();
          for (int i = 0; i < imDV.size(); i++) {
            ItemMappingData imD = (ItemMappingData)imDV.get(i);
            if (!distItemIdHM.containsKey(imD.getItemNum()) ) {
              distItemIdHM.put(imD.getItemNum(), new Integer(imD.getItemId()));
            } else {
              multipleItemsHS.add(imD.getItemNum());
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
         if (ZIP_FILE_PATTERN.MASTER_IMAGE.equals(zipFilePattern)){
           return DIR.IMAGE;
         } else if (ZIP_FILE_PATTERN.MSDS.equals(zipFilePattern)){
           return DIR.MSDS;
         } else if (ZIP_FILE_PATTERN.SPEC.equals(zipFilePattern)){
           return DIR.SPEC;
         }
         return "";
       }
       private String getItemMetaNameValueByPattern(){
         if (ZIP_FILE_PATTERN.MASTER_IMAGE.equals(zipFilePattern)){
          return ITEM_META_NAME_VALUE.IMAGE;
        } else if (ZIP_FILE_PATTERN.MSDS.equals(zipFilePattern)){
          return ITEM_META_NAME_VALUE.MSDS;
        } else if (ZIP_FILE_PATTERN.SPEC.equals(zipFilePattern)){
          return ITEM_META_NAME_VALUE.SPEC;
        } else if (ZIP_FILE_PATTERN.ASSET_IMAGE.equals(zipFilePattern)){
          return "";
        }

        return "";
       }
}
