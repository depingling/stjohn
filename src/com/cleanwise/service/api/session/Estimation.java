package com.cleanwise.service.api.session;

/**
 * Title:        Estimation
 * Description:  Remote Interface for Estimation Stateless Session Bean
 * Purpose:      Provides access to the methods for establishing and maintaining Spending Estimation Models
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;

public interface Estimation extends javax.ejb.EJBObject
{
 
  public CleaningProcDataVector getCleaningProcedures() 
  throws RemoteException;

  public ProdApplJoinViewVector getProcedureProducts(int pCleaningProcId) 
  throws RemoteException;

  public ProdApplJoinViewVector getProductProcedures(int pItemId) 
  throws RemoteException;
  
  public ProdUomPackJoinViewVector getEstimatorProducts() 
  throws RemoteException;

  public void updateEstimatorProducts(ProductUomPackDataVector pProducts, String pUser) 
  throws RemoteException;

  public ProdUomPackJoinView addEstimatorProduct(ProductUomPackData pProduct, String pUser) 
  throws RemoteException;

  public void removeEstimatorProducts(IdVector pItemIds) 
  throws RemoteException;

  public void removeEstimatorProductsCascade(IdVector pItemIds) 
  throws RemoteException;

  
  public EstimatorFacilityDataVector getUserEstimatorFacilities(int pUserId) 
  throws RemoteException;

  public CatalogDataVector getUserEstimatorCatalogs(int pUserId) 
  throws RemoteException;

  public EstimatorFacilityData getDefaultFacilityProfile() 
  throws RemoteException;
  
  public EstimatorFacilityJoinView getEstimatiorProfile(int pFacilityId) 
  throws RemoteException;

  public EstimatorFacilityJoinView saveEstimatorProfile(EstimatorFacilityJoinView pFacilityJoin,
               String pUser) 
  throws RemoteException;

  public void deleteEstimatorProfile(EstimatorFacilityData pFacility, String pUser) 
  throws RemoteException;

  public void saveRestroomCleaningSchedules (int pFacilityId, 
     CleaningScheduleJoinViewVector pRestroomCleaningProcProd, String pUser) 
  throws RemoteException;
  
  public CleaningScheduleJoinViewVector getCleaningSchedules (int pFacilityId, 
                                                     List pCleaningScheduleTypes) 
  throws RemoteException;

  public void saveCleaningSchedules (int pFacilityId, 
     CleaningScheduleJoinViewVector pFloorCleaningProcProd, 
     List pCleanigScheduleTypes, int pProcessStepShift, String pUser) 
  throws RemoteException;

  public CleaningScheduleJoinViewVector getDefaultCleaningSchedules (
                                                 List pCleaningScheduleTypes) 
  throws RemoteException;
  
  public void updateDefaultCleaningSchedules (
     CleaningScheduleJoinViewVector pCleaningSchedules, String pUser) 
  throws RemoteException;

  public void updateDefaultProdAppl (
     ProdApplDataVector pProdApplDV, String pUser) 
  throws RemoteException;

  public ProdApplData insertDefaultProdAppl (
     ProdApplData pProdApplD, String pUser) 
  throws RemoteException;

  public void deleteDefaultProdAppl(IdVector pProdApplIdsToDelete) 
  throws RemoteException;

  public EstimatorProdResultViewVector calcFloorYearProducts (int pFacilityId) 
  throws RemoteException;

  public EstimatorProdResultViewVector calcRestroomYearProducts (int pFacilityId) 
  throws RemoteException;
  
  public EstimatorProdResultViewVector calcPaperPlusYearProducts (int pFacilityId) 
  throws RemoteException;
  
  public EstimatorProdResultViewVector calcOtherYearProducts (int pFacilityId) 
  throws RemoteException;

  public AllocatedCategoryViewVector getAllocatedCategories(int pFacilityId) 
  throws RemoteException;

  public void saveAllocatedCategories(int pFacilityId,
     AllocatedCategoryViewVector pAllocatedCategoryVwV, String pUser ) 
  throws RemoteException;
  
  public EstimatorFacilityData copyModel(int pFacilityId, String pName, String pUser ) 
  throws RemoteException;

  public EstimatorFacilityData createModelFromTemplate(int pTemplateId, 
                            String pName, String pUser ) 
  throws RemoteException;
  
  public void deleteModelFromDb(int pFacilityId) 
  throws RemoteException;

}
