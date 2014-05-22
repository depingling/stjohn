package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;

import java.util.*;
import java.util.zip.*;
import java.sql.*;
import java.io.*;
import java.rmi.RemoteException;

public class USPSCatalogItemImagesSpecialReport extends ReportBase {

    private static final int BUFFER = 2048;

    private static final String OUT_ZIP_FILE = "uspsImages";
    private static final String ZIP_EXT      = "zip";
    private static final String IMG_PATH     = "en/products/images/";
    private static final String ZIP_IMG_DIR  = "";

    private ContentDataVector itemImages = new ContentDataVector();


    public USPSCatalogItemImagesSpecialReport() {
        setFileName(OUT_ZIP_FILE);
        setExt(ZIP_EXT);
        setSpecial(true);
        setUserTypesAutorized(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR+","+RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR);
    }

    protected byte[] getOutputStream() throws Exception {

        File zipFile = createZipFile();

        InputStream inputStream = new FileInputStream(zipFile);

        byte[] oStream = new byte[inputStream.available()];
        inputStream.read(oStream);
        inputStream.close();

        return oStream;

    }

/*
    private List<File> getFilesToZip() {
        List<File> filesToProcess = new ArrayList<File>();
        for (Object itemImageName : itemImageNames) {
            String fileName = ClwApiCustomizer.getCustomizeElement(IMG_PATH + itemImageName);
            File ioFile = new File(fileName);
            if (ioFile.exists() && ioFile.canRead()) {
                filesToProcess.add(ioFile);
            }
        }
        return filesToProcess;
    }

    protected File createZipFile(List<File> pFiles) throws Exception {

        File file = File.createTempFile(OUT_ZIP_FILE, "." + ZIP_EXT);
        byte[] data = new byte[BUFFER];


        FileOutputStream fos = new FileOutputStream(file);
        CheckedOutputStream csum = new CheckedOutputStream(fos, new CRC32());
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(csum));

        out.setMethod(ZipOutputStream.DEFLATED);

        out.putNextEntry(new ZipEntry(ZIP_IMG_DIR));
        if (!pFiles.isEmpty()) {
            for (File ioFile : pFiles) {
                InputStream in = new FileInputStream(ioFile);
                ZipEntry zipEntry = new ZipEntry(ZIP_IMG_DIR + ioFile.getName());
                out.putNextEntry(zipEntry);
                int count;
                while ((count = in.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                in.close();
            }
        }

        out.close();

        return file;

    } */

    protected File createZipFile() throws Exception {

        File file = File.createTempFile(OUT_ZIP_FILE, "." + ZIP_EXT);

        FileOutputStream fos = new FileOutputStream(file);
        CheckedOutputStream csum = new CheckedOutputStream(fos, new CRC32());
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(csum));
        HashSet<String> fileNameHS = new HashSet<String>();
        out.setMethod(ZipOutputStream.DEFLATED);
        out.putNextEntry(new ZipEntry(ZIP_IMG_DIR));
        if (!itemImages.isEmpty()) {
            for (int i=0; i<itemImages.size(); i++) {
                ContentData contentD = (ContentData)itemImages.get(i);
                byte[] image = contentD.getBinaryData();
                if (image != null && image.length > 0) {
                    String fileName = contentD.getPath();
                    if (fileName.lastIndexOf("/") > 0) {
                        fileName = fileName.substring(fileName.lastIndexOf("/")+1);
                    }
					if(fileNameHS.contains(fileName)) {
						continue;
					}
					fileNameHS.add(fileName);
                    ZipEntry zipEntry = new ZipEntry(ZIP_IMG_DIR + fileName);
                    out.putNextEntry(zipEntry);
                    out.write(image);
                }
            }
        }

        out.close();

        return file;

    }

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception
    {
        Connection con = pCons.getMainConnection();

        String errorMess = "No error";

        int catalogId;
        try{
            catalogId = Integer.parseInt((String)pParams.get("CATALOG"));
        }catch (RuntimeException e){
            throw new RemoteException("^clw^Could not parse Catalog control: "+pParams.get("CATALOG")+"^clw^");
        }

        try {

            //pContract
            DBCriteria dbc = new DBCriteria();
            //get pContract id
            dbc.addEqualTo(ContractDataAccess.CATALOG_ID,catalogId);
            ArrayList contractStatuses = new ArrayList();
            contractStatuses.add(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            contractStatuses.add(RefCodeNames.CONTRACT_STATUS_CD.LIVE);

            dbc.addOneOf(ContractDataAccess.CONTRACT_STATUS_CD,contractStatuses);
            ContractDataVector pContractDV = ContractDataAccess.select(con,dbc);

            int contractId=0;
            if(pContractDV.size()>1) {
                String mess = "^clw^More than one pContract for catalog. Catalog Id: "+catalogId+"^clw^";
                throw new RemoteException(mess);
            }
            ContractData pContractD = null;
            if(pContractDV.size()==1) {
                pContractD = (ContractData) pContractDV.get(0);
                contractId = pContractD.getContractId();
            }

            //catalog items
            dbc = new DBCriteria();
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,catalogId);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            String catalogItemReq = CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID,dbc);


            // items image paths from item_meta
            String sql = "select * from \n" +
                        "(SELECT distinct ci.item_id, cs.customer_sku_num AS sku_num, \n" +
                        "        Nvl(service_fee_code,'C') change_cd, \n" +
                        "        trim(im.clw_value) as image_path \n" +
                        " FROM clw_contract_item ci, clw_contract c, clw_catalog cat, \n" +
						" clw_item_meta im, clw_item i, clw_catalog_structure cs \n" +
                        " WHERE ci.contract_id = c.contract_id\n" +
                        "    AND ci.item_id in (" + catalogItemReq + ") \n" +
                        "    AND c.catalog_id = cat.catalog_id\n" +
                        "    AND cat.catalog_id = " + catalogId +"\n"+
                        "    AND (im.item_id = ci.item_id and im.name_value = 'IMAGE')\n" +
						"    AND i.item_id = ci.item_id \n"+
						"    AND cs.catalog_id = 4 \n"+
						"    AND i.item_id = cs.item_id \n"+
                        " )\n" +
                        "where  change_cd in ('A')\n" +
                        " order by sku_num";

            HashMap itemImageNames = new HashMap();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String item_id = rs.getString("item_id");
				String sku_num = rs.getString("sku_num");
                String image_name = rs.getString("image_path");
                itemImageNames.put(sku_num, image_name);
            }
            rs.close();
            pstmt.close();

            // get binary data from clw_content
            sql = "SELECT c.binary_data from clw_content c where c.path like ? " +
                    "AND c.BINARY_DATA is not null";
            pstmt = con.prepareStatement(sql);

            StringBuffer err = new StringBuffer();
            Iterator i = itemImageNames.entrySet().iterator();
            while (i.hasNext()) {
                byte[] image_data = null;
                Map.Entry<String, String> itemEntry = (Map.Entry<String, String>)i.next();
                pstmt.setString(1, "%"+itemEntry.getValue());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    if (err.length() > 0) continue; // error was earlier
                    image_data = rs.getBytes("binary_data");
                }
                if (image_data == null || image_data.length == 0) {
                    if (err.length() > 0) err.append(", ");
                    err.append(itemEntry.getKey());
                } else {
                  if (err.length() == 0) {
                    ContentData contentD = ContentData.createValue();
                    contentD.setPath(itemEntry.getValue());
                    contentD.setBinaryData(image_data);
                    itemImages.add(contentD);
                  }
                }
                rs.close();
            }
            pstmt.close();

            // error if some items has no image
            if (err.length() > 0) {
                errorMess = "^clw^Image does not exists for item(s): " + err.toString() + "!^clw^";
                throw new RemoteException(errorMess);
            }

        }
        catch (SQLException exc) {
            errorMess = "Error. Report.USPSCatalogItemImagesSpecialReport() SQL Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
            errorMess = "Error. Report.USPSCatalogItemImagesSpecialReport() Api Service Access Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            throw new RemoteException(errorMess);
        }

        return super.process(pCons, pReportData, pParams);

    }


}
