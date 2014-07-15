package com.cleanwise.service.apps;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
/*
//java -classpath \espendwise\xapp\webapp\stjohn\kit;\espendwise\xapp\webapp\stjohn\tools\thumbnails\Thumbnailator-0.4.0.jar;\espendwise\xapp\webapp\stjohn\installation/jdbc/lib/ojdbc14.jar;\espendwise\xapp\webapp\EJBServer\common\lib\log4j.jar;\espendwise\xapp\webapp\EJBServer\server\defst\lib\jdbcappender.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-logging-log4j.jar;\espendwise\xapp\webapp\EJBServer\xsuite\lib\xsuite-apps.jar;\espendwise\xapp\webapp\stjohn\kit\artifacts\conf;\espendwise\xapp\webapp\EJBServer\server\defst\lib\esapi-2.0_RC7-SNAPSHOT.jar;\espendwise\xapp\webapp\EJBServer\client\jnp-client.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-logging-spi.jar;\espendwise\xapp\webapp\EJBServer\common\lib\jboss-javaee.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-client.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-security-spi.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-serialization.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-common-core.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-remoting.jar;\espendwise\xapp\webapp\EJBServer\client\concurrent.jar;\espendwise\xapp\webapp\EJBServer\client\jbosssx-client.jar;\espendwise\xapp\webapp\EJBServer\client\jboss-integration.jar;  com.cleanwise.service.apps.GenerateThumbnails 1,2 Items 2135		
[ykupershmidt@stjohnqa01 util]$ java -classpath /espendwise/xapp/webapp/EJBServer/xsuite/util:/espendwise/xapp/webapp/EJBServer/xsuite/lib/Thumbnailator-0.4.0.jar:/espendwise/xapp/webapp/stjohn/installation/jdbc/lib/ojdbc14.jar:/espendwise/xapp/webapp/EJBServer/common/lib/log4j.jar:/espendwise/xapp/webapp/EJBServer/server/defst/lib/jdbcappender.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-logging-log4j.jar:/espendwise/xapp/webapp/EJBServer/xsuite/lib/xsuite-apps.jar:/espendwise/xapp/webapp/EJBServer/server/defst/lib/esapi-2.0_RC7-SNAPSHOT.jar:/espendwise/xapp/webapp/EJBServer/client/jnp-client.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-logging-spi.jar:/espendwise/xapp/webapp/EJBServer/common/lib/jboss-javaee.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-client.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-security-spi.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-serialization.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-common-core.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-remoting.jar:/espendwise/xapp/webapp/EJBServer/client/concurrent.jar:/espendwise/xapp/webapp/EJBServer/client/jbosssx-client.jar:/espendwise/xapp/webapp/EJBServer/client/jboss-integration.jar:/espendwise/xapp/webapp/EJBServer/xsuite/lib/xsuite-ejb.jar:  -DdbDriver=oracle.jdbc.driver.OracleDriver -DdbUrl=jdbc:oracle:thin:@166.78.67.76:1521:cwdev -DdbUser=qa01 -DdbPassword=qa01 com.cleanwise.service.apps.GenerateThumbnails 1,2 Items 90420,91040,91060,91181,91201,91220,91221,92472,99495,101771,107728,107729,107731,107732,112456,143199,144372,999463,999464,1169476,1197064,1197065,1197066,1197067,1197068,1197069,1207884,1251190,1264343,1268577,1268578,1268579,1268580,1268581,1268582,1268583,1268584,1268585,1268587,1268589,1268591,1268592,1268593,1268594,1268595,1268596,1268597,1268598,1269990,1286330,1286331,1286332,1286333,1286392,1286393,1286394,1286395,1308054,1320491,1320492,1320641,1330144,1330235,1340073,1340074,1340075,1348628,1348881,1353368,1353369,1353371,1353372,1353373,1353374,1353375,1353376,1353380,1353382,1353383,1353384
*/
public class GenerateThumbnails extends ClientServicesAPI{
    private static final Logger log = Logger.getLogger(GenerateThumbnails.class);
	static String jbossRoot = "/espendwise/xapp/webapp/EJBServer/server/defst/deploy/xsuite.ear/defst.war";
	private static final String thumbPath = "en/products/thumbnails";
	private static final String signature = "Thumbnails Generator";
	private Content contentBean = null;

    public static void main(String arg[]) throws Exception {
	    log.info("Started");
		String accounts = arg[0];
		log.info("Create Thumbnails for accounts: "+accounts);
		String strategy = arg[1]; //Interval, New, Items
        GenerateThumbnails gt = new GenerateThumbnails();
        gt.getAPIAccess();
        jbossRoot = System.getProperty ("webdeploy");
		if("Interval".equalsIgnoreCase(strategy)) {
			//Not done yet
			log.info("Strategy 'Interval' not implemented yet");
		} else if("New".equalsIgnoreCase(strategy)) {
			HashMap params = new HashMap();
			params.put("accounts", accounts);
			params.put("strategy", strategy);
			gt.process(params);
		} else if("Items".equalsIgnoreCase(strategy)) {
			log.info("Strategy 'Items' selected");
			HashMap params = new HashMap();
			params.put("accounts", accounts);
			params.put("strategy", strategy);
			String items = arg[2];
			params.put("items", items);
			gt.process(params);
		} else {
			throw new Exception("Unknown strategy. Possible values: Interval, New, Items");
		}
    }
	private IdVector parceAccountIds (String accountIds) throws Exception{
		String[] accuntIdSA = accountIds.split(",");
		IdVector accountIdV = new IdVector();
		for(int ii=0; ii<accuntIdSA.length; ii++) {
			try {
				int acctId = Integer.parseInt(accuntIdSA[ii].trim());
				accountIdV.add (acctId);
			} catch (Exception exc) {
				exc.printStackTrace();
				throw new Exception ("Wrong account id: "+accuntIdSA[ii].trim());				
			}
		}
		return accountIdV;
	}
	
	private IdVector parceItemIds (String itemIds) throws Exception{
		String[] accuntIdSA = itemIds.split(",");
		IdVector itemIdV = new IdVector();
		for(int ii=0; ii<accuntIdSA.length; ii++) {
			try {
				int acctId = Integer.parseInt(accuntIdSA[ii].trim());
				itemIdV.add (acctId);
			} catch (Exception exc) {
				exc.printStackTrace();
				throw new Exception ("Wrong item id: "+accuntIdSA[ii].trim());				
			}
		}
		return itemIdV;
	}
	
	private void process (HashMap params) throws Exception
	{
		Connection conn = null;
		try {
			conn = getConnection();
			String strategy = (String) params.get("strategy");
			String accounts = (String) params.get("accounts");
			IdVector accountIdV = parceAccountIds(accounts);
			String items = (String) params.get("items");
			if(items!=null) {
				IdVector itemIdV = parceItemIds(items);
				createForItems(conn,itemIdV);
			} else {	
				createForNew(conn, accountIdV);
			}
			
		} finally {
            closeConnection(conn);
		}
	}
	
	private void createForNew(Connection conn, IdVector accountIdV) 
	throws Exception {
		//picking files to resize
		
			String sql = 
				"select distinct c.catalog_id from clw_catalog c, clw_catalog_assoc ca "+
				" where ca.bus_entity_id in ("+IdVector.toCommaString(accountIdV)+")"+
				" and c.catalog_id = ca.catalog_id"+
				" and c.catalog_type_cd = 'ACCOUNT'";
			log.info("select catalogs: "+sql);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			IdVector catalogIdV = new IdVector();
			while (rs.next()) {
				int cId = rs.getInt(1);
				catalogIdV.add(cId);
			}
			rs.close();
			if(catalogIdV.size()==0) {
				log.info("No account catalogs found");
				throw new Exception("No account catalogs found");
			}
		
			sql = 
				"select * from ( "+
				" select cs.catalog_id, im.item_id, im.clw_value, im1.clw_value as thumb "+
				" from clw_catalog_structure cs, clw_item_meta im, clw_item_meta im1"+
				" where cs.catalog_id in ("+IdVector.toCommaString(catalogIdV)+")"+
				" and cs.item_id = im.item_id"+
				" and im.name_value = 'IMAGE'"+
				" and im1.item_id(+) = im.item_id"+
				" and im1.name_value(+) = 'THUMBNAIL'"+
				" ) where thumb is null";
			
			rs = stmt.executeQuery(sql);
			HashMap<Integer,String> itemImageHM = new HashMap<Integer,String>();
			while (rs.next()) {
				//int cId = rs.getInt(1);
				int itemId = rs.getInt(2);
				String imagePath = rs.getString(3);
				itemImageHM.put(itemId,imagePath);
			}
			rs.close();	
			createThumbnails(conn, itemImageHM);
	}
	

	private void createForItems(Connection conn, IdVector itemIdV) 
	throws Exception {
		
			String sql = 
				" select im.item_id, im.clw_value "+
				" from clw_item_meta im"+
				" where im.item_id in ("+IdVector.toCommaString(itemIdV)+")"+
				" and im.name_value = 'IMAGE'";
			
			log.info("Pick item images sql: "+sql);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			HashMap<Integer,String> itemImageHM = new HashMap<Integer,String>();
			while (rs.next()) {
				//int cId = rs.getInt(1);
				int itemId = rs.getInt(1);
				String imagePath = rs.getString(2);
				itemImageHM.put(itemId,imagePath);
			}
			rs.close();	
			createThumbnails(conn, itemImageHM);
	}
 	
	private void createThumbnails(Connection conn, HashMap<Integer,String> imagePaths) 
	throws Exception {
		Set<Map.Entry<Integer,String>> entries = imagePaths.entrySet();
		for(Iterator iter = entries.iterator(); iter.hasNext();) {
			Map.Entry<Integer,String> entry = (Map.Entry<Integer,String>) iter.next(); 
			Integer itemIdI = entry.getKey();
			String filePath = entry.getValue();
			ByteArrayInputStream bais = getContent(filePath);
			if(bais==null) continue; //skipping the file
			BufferedImage image = Thumbnails.of(bais).size(50,50).asBufferedImage();		
            String fileName = saveThumbnail(itemIdI.intValue(), "JPG", true, image);
			saveContent(fileName);
			saveItemMeta(conn, itemIdI.intValue(),fileName);			
		}	
	}
	
	/*
	private ContentDataVector getContent (Connection conn, HashMap<Integer,String> itemImage) 
	throws Exception{
		ListArray fileNamesV = new ListArray();
		Collection<String> files = itemImage.values();
		for(Iterator iter=files.iterator(); iter.hasNext();) {
			String val = (String) iter.next();
			if(!val.startsWith(".")) val = "."+val;
			fileNamesV.add(val);
		}
	    DBCriteria dbc = new DBCriteria();
	    dbc.addOneToIgnoreCase(ContentDataAccess.PATH, fileNamesV);
	    ContentDataVector cdv = ContentDataAccess.select(con,dbc);
		int count = fileNamesV.size() - cdv.size();
		if(count>0)) {
			String errMess = "";
			int ii=0;
			for(Iterator iter = fileNamesV.iterator(); iter.hasNext(); ) {
				String fn = (String) iter.next();
				boolean found = false;
				for(Iterator iter1 = cdv.iterator(); iter1.hasNext(); ) {
					ContentData cd = (ConentData) iter1.next();
					if(fn.equalsIgnoreCase(cd.getPath())) {
						found = true;
						break;
					}
				}
				if(!found) {
					ii++;
					if(errMess==null) {
						errMess = fn;
					} else {
						errMess += ", "+fn;
					}
					if(ii >= count) {
						errMess = "Some itemImages not found: "+ errMess;
						throw new Exception(errMess);
					}
				}
			}
		}
		return cdv;
	}
	*/
	
	
    private String saveThumbnail(int itemId, String fileType, boolean overwrite, BufferedImage image) 
	throws Exception
	{

		String fileExt = null;
		String fileName = itemId+"."+fileType;
		log.info("saving file : "+fileName);

		String filePath = jbossRoot+"/"+thumbPath+"/"+fileName;
		File file = new File(filePath);
		if(file.exists()) {
			if(overwrite) {
				log.info("Deleting file "+filePath);
				file.delete();
			} else {
				String errMess = "File exists: "+filePath;
				throw new Exception(errMess);
			}
			
		} 
		file.createNewFile();
		ImageIO.write(image, fileType, file);
		return fileName;
    }

    private void saveFullImage(String filePath, boolean overwrite, byte[] contents) 
	throws Exception
	{

		if(filePath.startsWith(".")) filePath = filePath.substring(1);
		if(filePath.startsWith("/")) {
			filePath = jbossRoot+filePath;
		} else {
			filePath = jbossRoot+"/"+filePath;
		}
		
		File file = new File(filePath);
		if(file.exists()) {
			if(overwrite) {
				log.info("Deleting file "+filePath);
				file.delete();
			} else {
				return;
			}
			
		} 
		log.info("Saving full image : "+filePath);
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(contents);
		fos.close();
    }
	
	private void saveItemMeta(Connection conn, int itemId, String fileName) 
	throws Exception {
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemId);
		dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, "THUMBNAIL");
		ItemMetaDataVector imDV = ItemMetaDataAccess.select(conn,dbc);
		String clwValue = "/"+thumbPath+"/"+fileName;
		if(imDV.size()>0) {
			ItemMetaData imD = (ItemMetaData) imDV.get(0);
			if(!clwValue.equals(imD.getValue())) {
				imD.setValue(clwValue);
				imD.setModBy(signature);
				ItemMetaDataAccess.update(conn,imD);
			}
			for(int ii=1; ii<imDV.size();ii++) { //removes extra thumbnail records. Just in case
				imD = (ItemMetaData) imDV.get(ii);
				ItemMetaDataAccess.remove(conn,imD.getItemMetaId());
			}
		} else { // insert new 
			ItemMetaData imD = ItemMetaData.createValue();
			imD.setItemId(itemId);
			imD.setValue(clwValue);
			imD.setAddBy(signature);
			imD.setModBy(signature);
			imD.setNameValue("THUMBNAIL");
            ItemMetaDataAccess.insert(conn,imD);
		}
	}
		

	private  ByteArrayInputStream getContent (String filePath) 
	throws Exception {

		initContentApi();
		if(!filePath.startsWith(".")) filePath = "."+filePath;
		ContentData cD = contentBean.getContent(filePath);
		if(cD==null)  {
			log.info("ERROR!!! Can't find content for the path: "+filePath);
			return null;
		}
		log.info("@@@@@@@ image found content id: "+cD.getContentId());
		byte[] image = cD.getBinaryData();
		saveFullImage(filePath, true, image);
		ByteArrayInputStream bais = new ByteArrayInputStream(image);
		return bais;
 
	}
	
	private  void saveContent (String fileName) 
	throws Exception {
		initContentApi();
		String basepath = "./"+thumbPath+"/"+fileName;
		contentBean.addContentSaveImage(signature, basepath, "ItemImage");
	}
	private void initContentApi() 
	throws Exception {
		if(contentBean!=null) {
			return;
		}
        contentBean = getAPIAccess().getContentAPI();
	}

}
