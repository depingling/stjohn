package com.cleanwise.service.api.session;

/**
 * Title:        ContentBean
 * Description:  Bean implementation for Content Stateless Session Bean
 * Purpose:      Provides access to Content add and retrieval of information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import org.apache.log4j.Category;
import java.rmi.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;

import java.util.Iterator;
//import com.cleanwise.view.utils.ClwCustomizer;
import java.io.*;

import oracle.sql.BLOB;

import java.sql.*;

//imports for E3 Storage System: Begin
//import com.espendwise.ocean.util.*;
import com.espendwise.ocean.util.StorageSystemBackedContent;
import com.espendwise.ocean.util.StorageSystemE3;
import com.espendwise.ocean.util.StorageSystem;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
//imports for E3 Storage System: End

public class ContentBean extends ValueAddedServicesAPI
{
 private static final Category log = Category.getInstance(ContentBean.class);
  /**
   *
   */
  public ContentBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}


  /**
   * Gets the content vector information values to be used by the request.
   * @param pBusEntityId the business entity identifier.
   * @return ContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentDataVector getContentValuesCollectionByBusEntity(int pBusEntityId)
      throws RemoteException
  {
    return new ContentDataVector();
  }

  /**
   * Gets the content vector information values to be used by the request.
   * @param pItemId the item identifier.
   * @return ContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentDataVector getContentValuesCollectionByItem(int pItemId)
      throws RemoteException
  {
    return new ContentDataVector();
  }

  /**
   * Gets the content vector information values to be used by the request.
   * @param pContentTypeCd the content type code.
   * @return ContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentDataVector getContentValuesCollectionByType(String pContentTypeCd)
      throws RemoteException
  {
    return new ContentDataVector();
  }

  /**
   * Gets the content value information values to be used by the request.
   * @param pContentId the content identifier.
   * @return ContentValueData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentData getContentValue(int pContentId)
      throws RemoteException
  {
    return ContentData.createValue();
  }


   public ContentDetailView getContentDetailView(int contentId) throws RemoteException{
          Connection conn = null;
        try {
            conn = getConnection();
            return getContentDetailView(conn,contentId);        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public ContentDetailViewVector getContentDetailViewVector(IdVector contentIds) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getContentDetailViewVector(conn, contentIds);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private ContentDetailViewVector getContentDetailViewVector(Connection conn, IdVector contentIds) throws SQLException {

        ContentDetailViewVector result = new ContentDetailViewVector();

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(ContentDataAccess.CONTENT_ID, contentIds);

        ContentDataVector contents = ContentDataAccess.select(conn, dbCriteria);

        if (!contents.isEmpty()) {
            Iterator it = contents.iterator();
            while (it.hasNext()) {
                ContentData contentData = (ContentData) it.next();
                if (Utility.isEqual(contentData.getStorageTypeCd(),RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
                	try{
        	        	InputStream is = getContentDataFromE3StorageSystem(contentData.getContentSystemRef());
        	        	byte[] data = IOUtilities.toBytes(is);
        	        	contentData.setBinaryData(data);
                	}catch(Exception e){}
                }
                result.add(convertToContentDetailView(contentData, contentData.getBinaryData()));
            }
        }

        return result;
    }

    private ContentDetailView getContentDetailView(Connection conn, int contentId) throws SQLException, DataNotFoundException {
        ContentData content = ContentDataAccess.select(conn, contentId);
        if (Utility.isEqual(content.getStorageTypeCd(),RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
        	try{
	        	InputStream is = getContentDataFromE3StorageSystem(content.getContentSystemRef());
	        	byte[] data = IOUtilities.toBytes(is);
	        	content.setBinaryData(data);
        	}catch(Exception e){}
        	
        }
        return convertToContentDetailView(content, content.getBinaryData());
    }



    public ContentDetailView updateContent(ContentDetailView contentDetail,UserData user) throws RemoteException {
    	String storageType = Utility.strNN(System.getProperty("storage.system.item"));
        Connection conn = null;
        try {
            conn = getConnection();
            ContentData contentData = convertToContentData(conn,  contentDetail);
            if (Utility.isEqual(storageType,RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
            	String contentSystemRef = writeContentToE3StorageSystem(contentData.getBinaryData(), "IMAGE_");
            	contentData.setBinaryData(null);
        		String storageSystemServername = System.getProperty("storage.system.servername");
        		log.info(".addContentSaveImageE3Storage(): storage.system.servername = " + storageSystemServername);
        		contentData.setContentServer(storageSystemServername); //server name; type=String
        		contentData.setContentSystemRef(contentSystemRef); //value of Content system reference = content name; type=String
        		contentData.setStorageTypeCd(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE);
            }else{
            	contentData.setContentServer(null); //server name; type=String
        		contentData.setContentSystemRef(null); //value of Content system reference = content name; type=String
        		contentData.setStorageTypeCd(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE);
            }
            
            if (contentData.getContentId() <= 0) {
                contentData.setAddBy(user.getUserName());
                contentData.setModBy(user.getUserName());
                contentData = ContentDataAccess.insert(conn, contentData);
            } else {
                contentData.setModBy(user.getUserName());
                contentData.setModDate(new Date());
                ContentDataAccess.update(conn, contentData);
            }
            return convertToContentDetailView(contentData, contentDetail.getData());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

    }

    private ContentData convertToContentData(Connection conn, ContentDetailView contentDetailView) throws Exception {
        ContentData contentData = ContentData.createValue();

        if (contentDetailView != null) {
            contentData.setContentId(contentDetailView.getContentId());
            contentData.setBusEntityId(contentDetailView.getBusEntityId());
            contentData.setItemId(contentDetailView.getItemId());
            contentData.setContentStatusCd(contentDetailView.getContentStatusCd());
            contentData.setContentTypeCd(contentDetailView.getContentTypeCd());
            contentData.setLocaleCd(contentDetailView.getLocaleCd());
            contentData.setLanguageCd(contentDetailView.getLanguageCd());
            contentData.setLongDesc(contentDetailView.getLongDesc());
            contentData.setPath(contentDetailView.getPath());
            contentData.setShortDesc(contentDetailView.getShortDesc());
            contentData.setSourceCd(contentDetailView.getSourceCd());
            contentData.setVersion(contentDetailView.getVersion());
            contentData.setEffDate(contentDetailView.getEffDate());
            contentData.setExpDate(contentDetailView.getExpDate());
            contentData.setAddDate(contentDetailView.getAddDate());
            contentData.setAddBy(contentDetailView.getAddBy());
            contentData.setModDate(contentDetailView.getModDate());
            contentData.setModBy(contentDetailView.getModBy());
            contentData.setContentUsageCd(contentDetailView.getContentUsageCd());
            contentData.setBinaryData(contentDetailView.getData());

        }

        return contentData;
    }

    private ContentDetailView convertToContentDetailView(ContentData contentData, byte[] data) throws SQLException {
        ContentDetailView contentDetailView = ContentDetailView.createValue();

        if (contentData != null) {
            contentDetailView.setContentId(contentData.getContentId());
            contentDetailView.setBusEntityId(contentData.getBusEntityId());
            contentDetailView.setItemId(contentData.getItemId());
            contentDetailView.setContentStatusCd(contentData.getContentStatusCd());
            contentDetailView.setContentTypeCd(contentData.getContentTypeCd());
            contentDetailView.setLocaleCd(contentData.getLocaleCd());
            contentDetailView.setLanguageCd(contentData.getLanguageCd());
            contentDetailView.setLongDesc(contentData.getLongDesc());
            contentDetailView.setPath(contentData.getPath());
            contentDetailView.setShortDesc(contentData.getShortDesc());
            contentDetailView.setSourceCd(contentData.getSourceCd());
            contentDetailView.setVersion(contentData.getVersion());
            contentDetailView.setEffDate(contentData.getEffDate());
            contentDetailView.setExpDate(contentData.getExpDate());
            contentDetailView.setAddDate(contentData.getAddDate());
            contentDetailView.setAddBy(contentData.getAddBy());
            contentDetailView.setModDate(contentData.getModDate());
            contentDetailView.setModBy(contentData.getModBy());
            contentDetailView.setContentUsageCd(contentData.getContentUsageCd());
            if(data==null){
            	data =new byte[0];
            }
            contentDetailView.setData(data);

        }

        return contentDetailView;
    }

    /**
   * Adds the content information values to be used by the request.
   * @param pContent  the content data.
   * @param request  the content request data.
   * @return new ContentRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */

    public ContentData addContent(String pSourceHost, String pPath )
	throws RemoteException
    {
	ContentData cdata = null;
	logDebug ("start addContent pSourceHost=" + pSourceHost
		  + " pPath=" + pPath );
	Connection con = null;
	try {
	    // See if this content has already been added.
	    con = getConnection();
	    DBCriteria dbc = new DBCriteria();
	    dbc.addEqualToIgnoreCase(ContentDataAccess.PATH, pPath);

	    ContentDataVector cdv =
	    ContentDataAccess.select(con,dbc);
	    if ( null != cdv && cdv.size() > 0 ) {
		logDebug ("found content pSourceHost=" + pSourceHost
			  + " pPath=" + pPath );
		// the latest update always wins
		ContentDataAccess.remove(con,dbc);
	    }

	    cdata = ContentData.createValue();
	    cdata.setShortDesc("ContentLog");
	    cdata.setContentTypeCd("ContentLog");
	    cdata.setContentStatusCd("ACTIVE");
	    cdata.setLocaleCd("x");
	    cdata.setLanguageCd("x");
	    cdata.setContentUsageCd("ContentLog");
	    cdata.setSourceCd("xsuite-app");
	    cdata.setAddBy(pSourceHost);
	    cdata.setModBy(pSourceHost);
	    cdata.setPath(pPath);
	    logDebug ("insert content pSourceHost=" + pSourceHost
		      + " pPath=" + pPath );
	    return ContentDataAccess.insert(con,cdata);
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RemoteException("addContent error 2005-8-9, " +
				      e.getMessage() );
	}
	finally {
	    closeConnection(con);
	}

    }

  /**
   * Adds the content information values to be used by the request.
   * @param pContent  the content data.
   * @param request  the content request data.
   * @return new ContentRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */

    public ContentData addContentSaveImage(String pSourceHost, String pPath, String imageType)
	throws RemoteException
    {
    	final String CONTENT_TYPE = "Image";
    	ContentData cdata = null;
	logDebug ("start addContent pSourceHost=" + pSourceHost
		  + " pPath=" + pPath );
	Connection con = null;

	try {
		// See if this content has already been added.
        String basename = System.getProperty("webdeploy");
	    con = getConnection();
	    DBCriteria dbc = new DBCriteria();
	    dbc.addEqualToIgnoreCase(ContentDataAccess.PATH, pPath);

	    ContentDataVector cdv =
	    ContentDataAccess.select(con,dbc);
	    if ( null != cdv && cdv.size() > 0 ) {
		logDebug ("found content pSourceHost=" + pSourceHost
			  + " pPath=" + pPath );
		// the latest update always wins
		ContentDataAccess.remove(con,dbc);
	    }

	    cdata = ContentData.createValue();
	    cdata.setShortDesc(imageType);
	    cdata.setContentTypeCd(CONTENT_TYPE);
	    cdata.setContentStatusCd("ACTIVE");
	    cdata.setLocaleCd("x");
	    cdata.setLanguageCd("x");
	    cdata.setContentUsageCd(imageType);
	    cdata.setSourceCd("xsuite-app");
	    cdata.setAddBy(pSourceHost);
	    cdata.setModBy(pSourceHost);
	    cdata.setPath(pPath);

	    String pFullPath = pPath.substring(1);
	    pFullPath = basename + "/" + pFullPath;
	    File imageFile = new File(pFullPath);
	    int imageFileLen = (int) imageFile.length();
	    InputStream imageStream = new FileInputStream(imageFile);


	    cdata.setBinaryData(null);
	    logDebug ("insert content pSourceHost=" + pSourceHost
		      + " pPath=" + pPath );

	    ContentDataAccess.insert(con,cdata);
	    cdv = ContentDataAccess.select(con, dbc);
	    PreparedStatement pStmt = con.prepareStatement("UPDATE CLW_CONTENT SET binary_data=? WHERE path=?");
	    pStmt.setBinaryStream(1, imageStream, imageFileLen);
	    pStmt.setString(2, pPath);
	    int res = pStmt.executeUpdate();
	    pStmt.close();

	    return null;
	}
	catch (Exception e) {
	    e.printStackTrace();
	    throw new RemoteException("addContent error 2005-8-9, " +
				      e.getMessage() );
	}
	finally {
	    closeConnection(con);
	}

    }

    public ContentData addContentSaveImageE3Storage(String pSourceHost, String pPath, String imageType)
    		throws RemoteException
    		{
    	final String CONTENT_TYPE = "Image";
    	ContentData cdata = null;
    	log.info ("start addContent pSourceHost=" + pSourceHost
    			+ " pPath=" + pPath );
    	Connection con = null;	

    	try {
    		// See if this content has already been added
    		String basename = System.getProperty("webdeploy");
    		con = getConnection();
    		DBCriteria dbc = new DBCriteria();
    		dbc.addEqualToIgnoreCase(ContentDataAccess.PATH, pPath);

    		ContentDataVector cdv =
    				ContentDataAccess.select(con,dbc);
    		if ( null != cdv && cdv.size() > 0 ) {
    			log.info ("found content pSourceHost=" + pSourceHost
    					+ " pPath=" + pPath );
    			// the latest update always wins;
    			// we remove record in the db table, NOT the image in the E3 Storage System =>
    			// => image stays in the E3 Storage System
    			ContentDataAccess.remove(con,dbc);
    		}

    		// write Content (binary data) to E3 Storage System: Begin
    		String pFullPath = pPath.substring(1);
    		pFullPath = basename + "/" + pFullPath;
    		File imageFile = new File(pFullPath);

    		String contentSystemRef = writeContentToE3StorageSystem(imageFile);
    		log.info(".addContentSaveImageE3Storage() = " + contentSystemRef);
    		// write Content (binary data) to E3 Storage System: End

    		cdata = ContentData.createValue();
    		cdata.setShortDesc(imageType);
    		cdata.setContentTypeCd(CONTENT_TYPE);
    		cdata.setContentStatusCd("ACTIVE");
    		cdata.setLocaleCd("x");
    		cdata.setLanguageCd("x");
    		cdata.setContentUsageCd(imageType);
    		cdata.setSourceCd("xsuite-app");
    		cdata.setAddBy(pSourceHost);
    		cdata.setModBy(pSourceHost);
    		cdata.setPath(pPath);

    		cdata.setBinaryData(null);
    		String storageSystemServername = System.getProperty("storage.system.servername");
    		log.info(".addContentSaveImageE3Storage(): storage.system.servername = " + storageSystemServername);
    		cdata.setContentServer(storageSystemServername); //server name; type=String
    		cdata.setContentSystemRef(contentSystemRef); //value of Content system reference = content name; type=String
    		cdata.setStorageTypeCd(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE);
    		ContentDataAccess.insert(con,cdata);

    		return null;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		throw new RemoteException("addContentSaveImageE3Storage error, " +
    				e.getMessage() );
    	}
    	finally {
    		closeConnection(con);
    	}			

    }

    public ContentData updateContent(ContentData cdata)
	throws RemoteException
	{    	
    	Connection con = null;	

    	try {
    		String basename = System.getProperty("webdeploy");
    		con = getConnection();		    

    		// write Content (binary data) to E3 Storage System: Begin
    		String path = cdata.getPath();
    		log.info("updateContentSaveImageE3Storage(): path = " + path);
    		String pFullPath = path.substring(1);
    		pFullPath = basename + "/" + pFullPath;
    		log.info("updateContentSaveImageE3Storage(): pFullPath = " + pFullPath);
    		File imageFile = new File(pFullPath);
    		
    		String contentSystemRef = writeContentToE3StorageSystem(imageFile);
    		log.info("updateContentSaveImageE3Storage() = " + contentSystemRef);
    		// write Content (binary data) to E3 Storage System: End

    		String storageSystemServername = System.getProperty("storage.system.servername");
    		log.info("addContentSaveImageE3Storage(): storage.system.servername = " + storageSystemServername);
    		cdata.setContentServer(storageSystemServername); //server name; type=String
    		cdata.setContentSystemRef(contentSystemRef);     //value of Content system reference = content name; type=String
    		cdata.setStorageTypeCd(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE);
    		ContentDataAccess.update(con,cdata);

    		return null;
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		throw new RemoteException("updateContentSaveImageE3Storage error, " +
    				e.getMessage() );
    	}
    	finally {
    		closeConnection(con);
    	}
    }
    
    public ContentData updateContentSaveImageE3Storage(ContentData cdata)
	throws RemoteException
    {    	
	   Connection con = null;	
	    
	   try {
	        String basename = System.getProperty("webdeploy");
		    con = getConnection();		    

		    // write Content (binary data) to E3 Storage System: Begin
		    String path = cdata.getPath();
		    log.info("updateContentSaveImageE3Storage(): path = " + path);
		    String pFullPath = path.substring(1);
		    pFullPath = basename + "/" + pFullPath;
		    log.info("updateContentSaveImageE3Storage(): pFullPath = " + pFullPath);
		    File imageFile = new File(pFullPath);
		    
		    String contentSystemRef = writeContentToE3StorageSystem(imageFile);
		    log.info("updateContentSaveImageE3Storage() = " + contentSystemRef);
		    // write Content (binary data) to E3 Storage System: End
		    
		    String storageSystemServername = System.getProperty("storage.system.servername");
		    log.info("addContentSaveImageE3Storage(): storage.system.servername = " + storageSystemServername);
		    cdata.setContentServer(storageSystemServername); //server name; type=String
		    cdata.setContentSystemRef(contentSystemRef);     //value of Content system reference = content name; type=String
		    cdata.setStorageTypeCd(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE);
		    ContentDataAccess.update(con,cdata);
		    		    
		    return null;
		}
		catch (Exception e) {
		    e.printStackTrace();
		    throw new RemoteException("updateContentSaveImageE3Storage error, " +
					      e.getMessage() );
		}
		finally {
		    closeConnection(con);
		}			

    }
    
    public void saveImage(String pSourceHost, String pPath, String imageType) throws RemoteException {
		ContentData cdata = null;
		final String SUBPATH = "en/images/";
		final String CONTENT_TYPE = "Image";
		logDebug ("start saveImage pSourceHost=" + pSourceHost
			  + " pPath=" + pPath );
		Connection con = null;
		boolean pathWithDot = false;
		if (imageType.equalsIgnoreCase("ItemImage")) {
			pathWithDot = true;
		} else {
			if (imageType.equalsIgnoreCase("LogoImage") || imageType.equalsIgnoreCase("TipsImage")) {
				pathWithDot = false;
			} else {
				throw new RemoteException("Unknown image type: " + imageType);
			}
		}

		try {
	        String basename = System.getProperty("webdeploy");
	        if (pathWithDot) {
	        	pPath = "." + pPath;
	        }
		    con = getConnection();
		    DBCriteria dbc = new DBCriteria();
		    dbc.addEqualToIgnoreCase(ContentDataAccess.PATH, pPath);

		    ContentDataVector cdv = ContentDataAccess.select(con,dbc);

		    cdata = ContentData.createValue();
		    cdata.setShortDesc(imageType);
		    cdata.setContentTypeCd(CONTENT_TYPE);
		    cdata.setContentStatusCd("ACTIVE");
		    cdata.setLocaleCd("x");
		    cdata.setLanguageCd("x");
		    cdata.setContentUsageCd(imageType);
		    cdata.setSourceCd("xsuite-app");
		    cdata.setAddBy(pSourceHost);
		    cdata.setModBy(pSourceHost);
		    cdata.setPath(pPath);

		    String pFullPath = "";
	        if (pathWithDot) {
			    cdata.setPath(pPath);
	        	pFullPath = pPath.substring(1);
	        } else {
	        	pFullPath = SUBPATH + pPath;
	        	pPath = "./" + pFullPath;
			    cdata.setPath(pPath);
	        }
		    pFullPath = basename + "/" + pFullPath;
		    File imageFile = new File(pFullPath);
		    int imageFileLen = (int) imageFile.length();
		    InputStream imageStream;
			try {
				imageStream = new FileInputStream(imageFile);
			} catch (FileNotFoundException e) {
				throw new RemoteException(pFullPath + " not found.", e);
			}

			ContentDataAccess.remove(con,dbc);
		    cdata.setBinaryData(null);
		    ContentDataAccess.insert(con,cdata);
		    cdv = ContentDataAccess.select(con, dbc);
		    PreparedStatement pStmt = con.prepareStatement("UPDATE CLW_CONTENT SET binary_data=? WHERE path=?");
		    pStmt.setBinaryStream(1, imageStream, imageFileLen);
		    pStmt.setString(2, pPath);
		    int res = pStmt.executeUpdate();
		    pStmt.close();

		}
		catch (SQLException e) {
		    e.printStackTrace();
		    throw new RemoteException("saveImage error, " + e.getMessage() );
		}
		catch (NamingException ne) {
		    ne.printStackTrace();
		    throw new RemoteException("saveImage error, " + ne.getMessage() );
		}
		finally {
		    closeConnection(con);
		}
    }

  /**
   * Updates the content information values to be used by the request.
   * @param pUpdateContentData  the content data.
   * @param pContentId the content identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateContent(ContentData pUpdateContentData,
                int pContentId)
      throws RemoteException
  {

  }




  public ContentDataVector getContentToSynchronize
	(String pSourceHost, String pContentUpdateTime )
	throws RemoteException
    {
	logDebug ("start getContentToSynchronize pSourceHost=" + pSourceHost
		  + " pContentUpdateTime=" + pContentUpdateTime );
	Connection con = null;
	ContentDataVector cdv = null;

	try {

	    con = getConnection();
	    DBCriteria dbc = new DBCriteria();
	    //dbc.addNotEqualTo(ContentDataAccess.ADD_BY, pSourceHost);
	    dbc.addCondition(ContentDataAccess.ADD_DATE + " >= " +
				  pContentUpdateTime);
	    dbc.addOrderBy(ContentDataAccess.ADD_DATE);

	    cdv = ContentDataAccess.select(con,dbc);
	    if ( null != cdv && cdv.size() > 0 ) {
			logDebug ("found synch content pSourceHost=" + pSourceHost);
			Iterator it = cdv.iterator();
			while(it.hasNext()){
				ContentData c = (ContentData) it.next();
				c.setBinaryData(null);//not serializable
			}
	    }



	    return cdv;

	}catch (Exception e) {
	    throw processException(e);
	}finally {
	    closeConnection(con);
	}
    }




    public Map<Integer, String> refreshBinaryData(boolean forceRewrite) throws RemoteException {
		log.info("refreshBinaryData() ====> Begin");
		String basename = System.getProperty("webdeploy");
		Map<Integer, String> refreshStatusMap= new HashMap<Integer, String>();

		DBCriteria crit = new DBCriteria();
		if (forceRewrite) {
			crit.addCondition("1=1");// dummy condition as we awant everything
		} else {
			crit.addGreaterOrEqual(ContentDataAccess.MOD_DATE, Utility.addDays(new Date(), -1));
		}
		Connection con = null;
		try {
			con = getConnection();
			IdVector ids = ContentDataAccess.selectIdOnly(con, crit);
			log.info("refreshBinaryData() ====> ids=" + ids);
			if (ids != null){
				IdVector bufferIds = new IdVector();
				Iterator it = ids.iterator();
				int CHUNCK_SIZE = 800; // picked arbitrarilty
				int counter = 0;
				while (it.hasNext()) {
					bufferIds.add(it.next());
	                counter ++;
					if (counter > CHUNCK_SIZE) {
						ContentDataVector contentDV = ContentDataAccess.select(con,
								bufferIds);
						refreshStatusMap = refreshBLOB(contentDV, basename, forceRewrite);
						counter = 0;
						bufferIds.clear();
					}
				}
				if (!bufferIds.isEmpty()) {
					ContentDataVector contentDV = ContentDataAccess.select(con,
							bufferIds);
					refreshStatusMap = refreshBLOB(contentDV, basename, forceRewrite);
				}
			}
        } catch(Exception e){
               throw processException(e);
		} finally {
			closeConnection(con);
		}
		return refreshStatusMap;
	}

	private Map<Integer, String> refreshBLOB(ContentDataVector contentDV, String basename,
//	private void refreshBLOB(ContentDataVector contentDV, String basename,
			boolean forceRewrite) {
		Map<Integer, String> refreshStatusMap= new HashMap<Integer, String>();

		Iterator iter = contentDV.iterator();
		while (iter.hasNext()) {
			// boolean rewrite = false;
			// boolean erase = true;
			ContentData contentData = (ContentData) iter.next();
			try {
				refreshStatusMap.put(new Integer(contentData.getContentId()),refreshBLOB(contentData, basename, forceRewrite));
			} catch (Exception e) {
				refreshStatusMap.put(new Integer(contentData.getContentId()), e.getMessage());
				e.printStackTrace();
			}
		}
		return refreshStatusMap;
	}
    
    private String refreshBLOB(ContentData contentData, String basename, boolean forceRewrite)
//    private void refreshBLOB(ContentData contentData, String basename, boolean forceRewrite)
    	throws Exception {
    	String refreshStatus = "";
    	byte[] image = contentData.getBinaryData();    	
    	String storageType = Utility.strNN(System.getProperty("storage.system.item"));
        log.info("refreshBLOB(): storageType = " + storageType);
        if (Utility.isEqual(storageType,RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
        	try{
	        	InputStream is = getContentDataFromE3StorageSystem(contentData.getContentSystemRef());
	        	image = IOUtilities.toBytes(is);
        	}catch(Exception e){}
        }
        boolean rewrite = false;
        boolean erase = true;
    	Date recordModDate = contentData.getModDate();
    	Date modDate = contentData.getModDate();
    	String filePath = contentData.getPath();
		if(!Utility.isSet(filePath)) {
			return null;
		}
		if(image==null || image.length==0) {
			return null;
		}
    	filePath = basename + "/" + filePath.substring(1);
    	File imageFile = new File(filePath);
    	if (!imageFile.exists()) {
    		rewrite = true;
    		erase = false;
    		String dir = "";
    		int lastSlashPosition = filePath.lastIndexOf("/");
    		int lastBackslashPosition = filePath.lastIndexOf("\\");
    		if ((lastSlashPosition >= 0) || lastBackslashPosition >= 0) {
    			if (lastSlashPosition > lastBackslashPosition) {
    				dir = filePath.substring(0, lastSlashPosition + 1);
    			} else {
    				dir = filePath.substring(0, lastBackslashPosition + 1);
    			}
    			File directory = new File(dir);
    			if (!directory.exists()) {
    				boolean cSuccess = new File(dir).mkdirs();
    				if (!cSuccess) {
    					throw new Exception("Directory " + dir + " cannot be created.");
    				}
    			}
    		}

    	} else {
    		modDate = new Date(imageFile.lastModified());
        	try {
        		long diff = modDate.getTime() - recordModDate.getTime();
        		if(diff < 0){
        			diff = diff *-1;
        		}
        		//if the record date is after our file date, we are doing a force re-write, or
        		//the difference is 10 minutes or less (accounts for differences in clock time).
				if (recordModDate.before(modDate) || forceRewrite || diff < 600000 ) {
    				if ((image!=null) && (image.length > 0)) {
    					rewrite = true;
    				}
				} else {
					erase = false;
				}
			} catch (Exception e) {
				refreshStatus = "Error:"+e.getMessage();
				e.printStackTrace();
			}
    	}

    	try {
			if ((image==null) || (image.length==0)) {
				rewrite = false;
			}
		} catch (Exception e1) {
			refreshStatus = "Error:"+e1.getMessage();
			e1.printStackTrace();
		}

        if (rewrite) {
        	if (erase) {
        		if (!imageFile.canWrite()) {
        			throw new Exception("image file " + filePath + " write protected, cannot be rewritten");
        		}
        		boolean successDeleting = imageFile.delete();
        		if (!successDeleting) {
        			throw new Exception("image file " + filePath + " cannot be rewritten");
        		}
        	}

            try {
                OutputStream fwriter = new FileOutputStream(filePath);
                fwriter.write(image);
            	fwriter.close();
            } catch (FileNotFoundException e) {
            	refreshStatus = "Error:"+e.getMessage();
				e.printStackTrace();
            } catch (IOException e) {
            	refreshStatus = "Error:"+e.getMessage();
				e.printStackTrace();
			}
        }
		return refreshStatus;

    }

    private int readFromBlob(Blob blob, OutputStream out) throws Exception {
        int read;
		try {
			InputStream in = blob.getBinaryStream();
			int length = -1;
			read = 0;
			int bBufLen = (int) blob.length();
			byte[] buf = new byte[bBufLen];
			while ((length = in.read(buf)) != -1) {
			    out.write(buf, 0, length);
			    read += length;
			}
			in.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception();
		}
        return read;
    }

	
    public void execSequenceOfSql(List sqlList) throws RemoteException {
        log.info("Start of performing the sequence of sql.");

        if (sqlList == null) {
            log.info("The sequence of sql is not defined.");
            return;
        }
        if (sqlList.size() == 0) {
            log.info("The sequence of sql is empty.");
            return;
        }
        Connection connection = null;

        try {
            connection = getConnection();

            final int listSize = sqlList.size();
            for (int i = 0; i < listSize; ++i) {
                String sql = (String) sqlList.get(i);
                execOneSql(connection, sql, i + 1);
            }
        } catch (Exception ex) {		 
            throw new RemoteException(ex.getMessage(),ex);
        } finally {
            closeConnection(connection);
        }

        log.info("Finish of performing the sequence of sql.");
    }

    private void execOneSql(Connection connection, String sql, int sqlIndex) 
	throws Exception {
        log.info("Start of performing the sql " + sqlIndex);

        StringBuilder msg = new StringBuilder();
        try {
            msg.setLength(0);
            msg.append("Try to perform sql: ");
            if (sql.length() > 128) {
                msg.append(sql.substring(0, 128));
                msg.append(" ...");
            } else {
                msg.append(sql);
            }
            log.info(msg.toString());

            Statement stmt = connection.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        } catch (Exception ex) {
            msg.setLength(0);
            msg.append("An error occurred at performing of sql with index ");
            msg.append(sqlIndex);
            msg.append(". Sql: ");
            if (sql.length() > 128) {
                msg.append(sql.substring(0, 128));
                msg.append("...");
            } else {
                msg.append(sql);
            }
            msg.append(". Error: ");
            msg.append(ex.getMessage());
            throw new Exception(msg.toString());
        }

        log.info("Finish of performing the sql " + sqlIndex);
    }
    
    public String writeContentToE3StorageSystem(File imageFile) throws IOException, RemoteException {
    	return writeContentToE3StorageSystem(new FileInputStream(imageFile), "IMAGE_");
        // write image Content (binary data) to E3 Storage System
        /*String storageSystemUrl = System.getProperty("storage.system.url");
        String storageSystemUsername = System.getProperty("storage.system.username");
        String storageSystemPassword = System.getProperty("storage.system.password");
        
        log.info(".writeContentToE3StorageSystem(): storage.system.url = " + storageSystemUrl);
        log.info(".writeContentToE3StorageSystem(): storage.system.username = " + storageSystemUsername);
        log.info(".writeContentToE3StorageSystem(): storage.system.password = " + storageSystemPassword);
                
        StorageSystem store = new StorageSystemE3(storageSystemUrl, storageSystemUsername, storageSystemPassword);
	    Properties metaData = new Properties();
	    metaData.setProperty("image key", "image value");
	
	    StorageSystemBackedContent content = StorageSystemBackedContent.createContentFromInputStream(store, "filename",metaData, new FileInputStream(imageFile), "IMAGE_");
	    log.info(".writeContentToE3StorageSystem(): Sending content: "+content.getContentName());
	    
	    content.saveToStore();
	    String cName = content.getContentName();
	    log.info(".writeContentToE3StorageSystem(): Saved content with the name: "+cName);	    
    
	    /***
	    //for testing only: Begin
		BufferedOutputStream out0 = new BufferedOutputStream(new FileOutputStream(file));
		 
		byte[] buf = new byte[1024];
 		int len;
		while ((len = input.read(buf)) > 0) {
			out0.write(buf, 0, len);
		}
		out0.flush();
		out0.close();
		//for testing only: End
		***/
	    
	    //getContentImageFromE3StorageSystem(content.getContentName());
	    
	    //return cName;
    }
    
    
    public String writeContentToE3StorageSystem(byte[] pDataContents, String contentPrefix) throws IOException, RemoteException {
    	ByteArrayInputStream input = new ByteArrayInputStream((byte[])pDataContents); 
    	return writeContentToE3StorageSystem(input, contentPrefix);
    }
    
    public String writeContentToE3StorageSystem(InputStream input, String contentPrefix) throws IOException, RemoteException {
    	// write image Content (binary data) to E3 Storage System
        String storageSystemUrl = System.getProperty("storage.system.url");
        String storageSystemUsername = System.getProperty("storage.system.username");
        String storageSystemPassword = System.getProperty("storage.system.password");
        
        log.info(".writeContentToE3StorageSystem(): storage.system.url = " + storageSystemUrl);
        log.info(".writeContentToE3StorageSystem(): storage.system.username = " + storageSystemUsername);
        log.info(".writeContentToE3StorageSystem(): storage.system.password = " + storageSystemPassword);
                
        StorageSystem store = new StorageSystemE3(storageSystemUrl, storageSystemUsername, storageSystemPassword);
	    Properties metaData = new Properties();
	    metaData.setProperty("image key", "image value");
	
	    StorageSystemBackedContent content = StorageSystemBackedContent.createContentFromInputStream(store, "filename",metaData, input, contentPrefix);
	    
	    content.saveToStore();
	    String cName = content.getContentName();
	    	    
	    return cName;    	
    }  
    
    public InputStream getContentDataFromE3StorageSystem(String contentName) throws IOException, RemoteException {
    	// retrieve IMAGE content (binary data) from E3 Storage System
    	String storageSystemUrl = System.getProperty("storage.system.url");
        String storageSystemUsername = System.getProperty("storage.system.username");
        String storageSystemPassword = System.getProperty("storage.system.password");   
        
        log.info(".getContentToE3StorageSystem(): storage.system.url = " + storageSystemUrl);
        log.info(".getContentToE3StorageSystem(): storage.system.username = " + storageSystemUsername);
        log.info(".getContentToE3StorageSystem(): storage.system.password = " + storageSystemPassword);
                
        StorageSystem store = new StorageSystemE3(storageSystemUrl, storageSystemUsername, storageSystemPassword);
	    
        StorageSystemBackedContent content = StorageSystemBackedContent.createContentFromStore(store, "filename", null, contentName);	    
	    log.info(".getContentImageFromE3StorageSystem(): Retrieved content with the name: "+content.getContentName());
	    InputStream input = null;
	    try {
	       input = content.getInputStream();
	       if(input == null){			 
				 throw new IOException("NO IO STREAM from stored in E3 Storage System image content!");
		   }			
	    } catch (IOException e){
	    	log.info(".getContentImageFromE3StorageSystem(): NO IO STREAM from stored in E3 Storage System image content!");
	    }
	    
	    return input;
    }
    
    public ContentData getContent(String pPath)
	throws RemoteException
    {
	ContentData cdata = null;
	log.info ("start getContent: pPath=" + pPath);
	Connection con = null;
	try {
	    con = getConnection();
	    DBCriteria dbc = new DBCriteria();
	    dbc.addEqualToIgnoreCase(ContentDataAccess.PATH, pPath);

	    ContentDataVector cdv =
	    ContentDataAccess.select(con,dbc);
	    if ( null != cdv && cdv.size() > 0 ) {
		   log.info ("found content pPath=" + pPath );
		   cdata = (ContentData) cdv.get(0);
		}
	}
	catch (Exception e) {
		    e.printStackTrace();
		    throw new RemoteException("addContent error 2005-8-9, " +
					      e.getMessage() );
	}
	finally {
		    closeConnection(con);
	}

	return cdata;
	
    }
	

}
