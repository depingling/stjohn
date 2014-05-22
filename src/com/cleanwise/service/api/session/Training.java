package com.cleanwise.service.api.session;

/**
 * Title:        Training
 * Description:  Remote Interface for Training Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Training information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;

public interface Training extends javax.ejb.EJBObject
{

  /**
   * Adds the Training information values to be used by the request.
   * @param pTraining  the training data.
   * @param request  the training request data.
   * @return new TrainingRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingRequestData addTraining(TrainingData pTraining,
                TrainingRequestData request)
      throws RemoteException;

  /**
   * Updates the training information values to be used by the request.
   * @param pUpdateTrainingData  the training data.
   * @param pProgramId the training program identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateTraining(TrainingData pUpdateTrainingData,
                int pProgramId)
      throws RemoteException;

  /**
   * Adds the Training detail information values to be used by the request.
   * @param pTrainingDetail  the training detail data.
   * @param request  the training detail request data.
   * @return new TrainingDetailRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingDetailRequestData addTrainingDetail(TrainingDetailData pTrainingDetail,
                TrainingDetailRequestData request)
      throws RemoteException;

  /**
   * Updates the training detail information values to be used by the request.
   * @param pUpdateTrainingDetailData  the training detail data.
   * @param pProgramId the training program identifier.
   * @param pProgramItemId the training program item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateTrainingDetail(TrainingDetailData pUpdateTrainingDetailData,
                int pProgramId, int pProgramItemId)
      throws RemoteException;

  /**
   * Adds the business entity raining information values to be used by the request.
   * @param pBusEntityTraining  the business entity training data.
   * @param request  the bus entity training request data.
   * @return new BusEntityTrainingRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public BusEntityTrainingRequestData addBusEntityTraining(BusEntityTrainingData pBusEntityTraining,
                BusEntityTrainingRequestData request)
      throws RemoteException;

  /**
   * Updates the business entity training information values to be used by the request.
   * @param pUpdateBusEntityTrainingData  the business entity training data.
   * @param pProgramId the training program identifier.
   * @param pProgramItemId the training program item identifier.
   * @param pBusEntityId the business entity identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateBusEntityTraining(BusEntityTrainingData pUpdateBusEntityTrainingData,
                int pProgramId, int pProgramItemId, int pBusEntityId)
      throws RemoteException;

  /**
   * Gets the training vector information values to be used by the request.
   * @param pStoreId the store identifier.
   * @return TrainingDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingDataVector getTrainingCollection(int pStoreId)
      throws RemoteException;

  /**
   * Gets store program training detail information values to be used by the request.
   * @param pStoreId the store identifier.
   * @param pProgramId the training program identifier.
   * @param pProgramItemId the training program item identifier.
   * @return TrainingDetailData
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingDetailData getTrainingDetail(int pStoreId,
                int pProgramId, int pProgramItemId)
      throws RemoteException;

  /**
   * Gets the business entity training vector information values to be used by the request.
   * @param pBusEntityId the business entity identifier.
   * @return BusEntityTrainingDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public BusEntityTrainingDataVector getBusEntityTrainingCollection(int pBusEntityId)
      throws RemoteException;

  /**
   * Adds the Training content information values to be used by the request.
   * @param pTrainingContent  the training content data.
   * @param request  the training content request data.
   * @return new TrainingContentRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingContentRequestData addTrainingContent(TrainingContentData pTrainingContent,
                TrainingContentRequestData request)
      throws RemoteException;

  /**
   * Updates the training content information values to be used by the request.
   * @param pUpdateTrainingContentData  the training content data.
   * @param pProgramId the training program identifier.
   * @param pProgramItemId the training program item identifier.
   * @param pContentId the content identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateTrainingContent(TrainingContentData pUpdateTrainingContentData,
                int pProgramId, int pProgramItemId, int pContentId)
      throws RemoteException;

  /**
   * Gets business entity training detail information values to be used by the request.
   * @param pBusEntityId the business entity identifier.
   * @param pProgramId the training program identifier.
   * @param pProgramItemId the training program item identifier.
   * @return BusEntityTrainingDetailData
   * @throws            RemoteException Required by EJB 1.0
   */
  public BusEntityTrainingDetailData getBusEntityTrainingDetail(int pBusEntityId,
                int pProgramId, int pProgramItemId)
      throws RemoteException;

  /**
   * Gets the training vector information values to be used by the request.
   * @param pProductId the product identifier.
   * @return TrainingDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingDataVector getTrainingCollectionByProduct(int pProductId)
      throws RemoteException;

  /**
   * Gets the training vector information values to be used by the request.
   * @param pCatalogId the catalog identifier.
   * @return TrainingDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingDataVector getTrainingCollectionByCatalog(int pCatalogId)
      throws RemoteException;

  /**
   * Gets the training vector information values to be used by the request.
   * @param pProgramTypeCd the program type code.
   * @return TrainingDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingDataVector getTrainingCollectionByType(int pProgramTypeCd)
      throws RemoteException;

  /**
   * Gets the training vector information values to be used by the request.
   * @param pBusEntityId the business entity identifier.
   * @return TrainingDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public TrainingDataVector getTrainingCollectionByBusEntity(int pBusEntityId)
      throws RemoteException;



}
