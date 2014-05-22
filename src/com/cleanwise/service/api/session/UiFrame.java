package com.cleanwise.service.api.session;

/**
 * Title:        UiFrame
 * Description:  Remote Interface for UiFrame Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving frames information.
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import com.cleanwise.service.api.value.*;

public interface UiFrame extends javax.ejb.EJBObject
{

  public UiFrameView updateFrame(UiFrameView pFrame)      throws RemoteException;

  public UiFrameView getFrameView(int pFrameId)      throws RemoteException;
  public UiFrameView getActiveFrameView(int pAccountId)      throws RemoteException;

  public UiFrameViewVector getTemplates(int pAccountId) throws RemoteException;
  public UiFrameViewVector getFrames(int pAccountId) throws RemoteException;

  public UiFrameData getFrame(int pFrameId)      throws RemoteException;
  public UiFrameSlotViewVector getFrameSlots(int pFrameId) throws RemoteException;
  public UiFrameView getLocaleFrameView(int pAccountId, String pLocaleCd) throws RemoteException ;

}
