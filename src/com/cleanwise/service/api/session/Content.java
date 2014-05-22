package com.cleanwise.service.api.session;

/**
 * Title:        Content
 * Description:  Remote Interface for Content Stateless Session Bean
 * Purpose:      Provides access to Content add and retrieval of information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;

import java.io.IOException;
import java.io.OutputStream;
import java.rmi.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cleanwise.service.api.value.*;

import java.io.*;

public interface Content extends javax.ejb.EJBObject
{

  /**
   * Gets the content vector information values to be used by the request.
   * @param pBusEntityId the business entity identifier.
   * @return ContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentDataVector getContentValuesCollectionByBusEntity(int pBusEntityId)
      throws RemoteException;

  /**
   * Gets the content vector information values to be used by the request.
   * @param pItemId the item identifier.
   * @return ContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentDataVector getContentValuesCollectionByItem(int pItemId)
      throws RemoteException;

  /**
   * Gets the content vector information values to be used by the request.
   * @param pContentTypeCd the content type code.
   * @return ContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentDataVector getContentValuesCollectionByType(String pContentTypeCd)
      throws RemoteException;

  /**
   * Gets the content value information values to be used by the request.
   * @param pContentId the content identifier.
   * @return ContentValueData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentData getContentValue(int pContentId)
      throws RemoteException;

  /**
   * Adds the content information values to be used by the request.
   * @param pContent  the content data.
   * @param request  the content request data.
   * @return new ContentRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
    public ContentData addContent(String pSourceHost, String pPath)
	    throws RemoteException;

    public ContentData addContentSaveImage(String pSourceHost, String pPath, String imageType)
	    throws RemoteException;

    public ContentData addContentSaveImageE3Storage(String pSourceHost, String pPath, String imageType)
    throws RemoteException;
    
    public void writeContentToE3StorageSystem(File imageFile) throws IOException, RemoteException;
    
    public String writeContentToE3StorageSystem(byte[] pDataContents, String contentPrefix) throws IOException, RemoteException;
    
    public InputStream getContentDataFromE3StorageSystem(String contentName) throws IOException, RemoteException;
    
    public void saveImage(String pSourceHost, String pPath, String imageType) throws RemoteException;

    
    public Map<Integer,String> refreshBinaryData(boolean forceRewrite) throws RemoteException;

    
  /**
   * Updates the content information values to be used by the request.
   * @param pUpdateContentData  the content data.
   * @param pContentId the content identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateContent(ContentData pUpdateContentData,
                int pContentId)
      throws RemoteException;

    public ContentDataVector getContentToSynchronize
	(String pSourceHost, String pContentUpdateTime )
	throws RemoteException;


    public ContentDetailView updateContent(ContentDetailView content,UserData user) throws RemoteException;

    public ContentDetailView getContentDetailView(int contentId) throws RemoteException;

    public ContentDetailViewVector getContentDetailViewVector(IdVector contentIds) throws RemoteException;

    public void execSequenceOfSql(List sqlList) throws RemoteException;  
    
    public ContentData getContent(String pPath) throws RemoteException;
    
    public ContentData updateContentSaveImageE3Storage(ContentData cdata) throws RemoteException;
	
}
