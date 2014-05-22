package com.cleanwise.service.api.session;

/**
 * Title:        ProductBean
 * Description:  Bean implementation for Product Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving product information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Date;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;

public class UiFrameBean extends ApplicationServicesAPI
{
  public UiFrameBean() {}

  public void ejbCreate() throws CreateException, RemoteException {}


  public UiFrameView updateFrame(UiFrameView pFrame)      throws RemoteException {
      Connection con=null;
      try {
          con = getConnection();
          UiFrameData frameD = pFrame.getFrameData();
          UiFrameSlotViewVector slots = pFrame.getFrameSlotViewVector();
          int id = frameD.getUiFrameId();
          if (id <= 0) {
              frameD = UiFrameDataAccess.insert(con, frameD);
          } else {
              UiFrameDataAccess.update(con, frameD);
          }
          Iterator i = slots.iterator();
          while (i.hasNext()) {
              UiFrameSlotView slot = (UiFrameSlotView)i.next();
              slot.setUiFrameId(frameD.getUiFrameId());
              UiFrameSlotData slotD = convertSlotViewToData(con, slot);
              if (id <= 0) {
                  slotD = UiFrameSlotDataAccess.insert(con, slotD);
              } else {
                  UiFrameSlotDataAccess.update(con, slotD);
              }
          }
      } catch(NamingException exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.updateFrame() Naming Exception happened");
      } catch(SQLException exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.updateFrame() SQL Exception happened");
      } catch(Exception exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.updateFrame() problem");
      }finally{
          closeConnection(con);
      }
      return pFrame;

  }

    public UiFrameData getFrame(int pFrameId)      throws RemoteException {
        Connection con=null;
        UiFrameData result = UiFrameData.createValue();
        try {
            con = getConnection();
            result = getFrame(con, pFrameId);
        } catch(NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrame() Naming Exception happened");
        } catch(SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrame() SQL Exception happened");
        } catch(Exception exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrame() problem");
        }finally{
            closeConnection(con);
        }
        return result;

    }

    private UiFrameData getFrame(Connection pCon, int pFrameId) throws SQLException, DataNotFoundException {
        return UiFrameDataAccess.select(pCon, pFrameId);
    }


    public UiFrameView getFrameView(int pFrameId)      throws RemoteException {
        Connection con=null;
        UiFrameView result = UiFrameView.createValue();
        try {
            con = getConnection();
            result = getFrameView(con, pFrameId);
        } catch(NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrame() Naming Exception happened");
        } catch(SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrame() SQL Exception happened");
        } catch(Exception exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrame() problem");
        }finally{
            closeConnection(con);
        }
        return result;
    }


    private UiFrameView getFrameView(Connection pCon, int pFrameId) throws SQLException, DataNotFoundException, Exception {
        UiFrameView result = UiFrameView.createValue();
        UiFrameData frame = UiFrameDataAccess.select(pCon, pFrameId);
        if (frame != null) {
            UiFrameSlotViewVector slots = getFrameSlots(pCon, pFrameId);
            result.setFrameData(frame);
            result.setFrameSlotViewVector(slots);
        }
        return result;
    }



    public UiFrameView getActiveFrameView(int pAccountId) throws RemoteException {
        Connection con=null;
        UiFrameView result = null;
        if (pAccountId <= 0) {
          throw new RemoteException("Error. UiFrameBean.getActiveFrameView(): no accountId");
        }
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UiFrameDataAccess.BUS_ENTITY_ID, pAccountId);
            crit.addEqualTo(UiFrameDataAccess.FRAME_TYPE_CD, RefCodeNames.FRAME_TYPE_CD.ACCOUNT_UI_FRAME);
            crit.addEqualTo(UiFrameDataAccess.STATUS_CD, RefCodeNames.FRAME_STATUS_CD.ACTIVE);
            IdVector framesIds = UiFrameDataAccess.selectIdOnly(con, crit);
            if (framesIds.size() > 0) {
                int id = ((Integer)framesIds.get(0)).intValue();
                result = getFrameView(con, id);
            }
        } catch(NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getTemplates() Naming Exception happened");
        } catch(SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getTemplates() SQL Exception happened");
        } catch(Exception exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getTemplates() problem");
        }finally{
            closeConnection(con);
        }
        return result;
    }
    public UiFrameView getLocaleFrameView(int pAccountId, String pLocaleCd) throws RemoteException {
        Connection con=null;
        UiFrameView result = null;
        if (pAccountId <= 0) {
          throw new RemoteException("Error. UiFrameBean.getActiveFrameView(): no accountId");
        }
        try {
            con = getConnection();
            int id = -1;
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UiFrameDataAccess.BUS_ENTITY_ID, pAccountId);
            crit.addEqualTo(UiFrameDataAccess.FRAME_TYPE_CD, RefCodeNames.FRAME_TYPE_CD.ACCOUNT_UI_FRAME);
            crit.addEqualTo(UiFrameDataAccess.STATUS_CD, RefCodeNames.FRAME_STATUS_CD.ACTIVE);
            crit.addOrderBy(UiFrameDataAccess.LOCALE_CD, true);
            UiFrameDataVector frames = UiFrameDataAccess.select(con, crit);
            if (frames.size() > 0) {
              String localeCd = pLocaleCd;
              id = findFrameByLocale( frames, localeCd ) ;
              if (id >= 0) {
                result = getFrameView(con, id);
              }
            }
        } catch(NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getTemplates() Naming Exception happened");
        } catch(SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getTemplates() SQL Exception happened");
        } catch(Exception exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getTemplates() problem");
        }finally{
            closeConnection(con);
        }
        return result;
    }

  private int findFrameByLocale(UiFrameDataVector frames, String pLocaleCd ) {
    int id = -1;
    for (int i = 0; i < frames.size(); i++) {
      UiFrameData frame = (UiFrameData) frames.get(i);
      String frameLocaleCd = frame.getLocaleCd();
      if (Utility.isSet(pLocaleCd)){
        if (id < 0 && !Utility.isSet(frameLocaleCd)) {
          id = frame.getUiFrameId(); // Default value
        //} else if (id < 0 && Utility.isSet(frameLocaleCd) && frameLocaleCd.startsWith(pLocaleCd.substring(0, 2))) {
        //  id = frame.getUiFrameId(); // match for language part
        }
        if (Utility.isSet(frameLocaleCd) && Utility.isSet(pLocaleCd) && frameLocaleCd.equals(pLocaleCd)) {
          id = frame.getUiFrameId(); // equals
          break;
        }
      } else {
        if (!Utility.isSet(frameLocaleCd)) {
          id = frame.getUiFrameId();
          break;
        }
      }
    }
    return id;
  }


  public UiFrameViewVector getTemplates(int pAccountId) throws RemoteException {
      Connection con=null;
      UiFrameViewVector result = new UiFrameViewVector();
      if (pAccountId <= 0) {
        throw new RemoteException("Error. UiFrameBean.getTemplates(): no accountId");
      }
      try {
          con = getConnection();
          result = getFramesByType(con, pAccountId, RefCodeNames.FRAME_TYPE_CD.ACCOUNT_TEMPLATE);
      } catch(NamingException exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.getTemplates() Naming Exception happened");
      } catch(SQLException exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.getTemplates() SQL Exception happened");
      } catch(Exception exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.getTemplates() problem");
      }finally{
          closeConnection(con);
      }
      return result;
  }


    public UiFrameViewVector getFrames(int pAccountId) throws RemoteException {
        Connection con=null;
        UiFrameViewVector result = new UiFrameViewVector();
        if (pAccountId <= 0) {
          throw new RemoteException("Error. UiFrameBean.getFrames(): no accountId");
        }
        try {
            con = getConnection();
            result = getFramesByType(con, pAccountId, RefCodeNames.FRAME_TYPE_CD.ACCOUNT_UI_FRAME);
        } catch(NamingException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrames() Naming Exception happened");
        } catch(SQLException exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrames() SQL Exception happened");
        } catch(Exception exc) {
            exc.printStackTrace();
            throw new RemoteException("Error. UiFrameBean.getFrames() problem");
        }finally{
            closeConnection(con);
        }
        return result;
    }

    private UiFrameViewVector getFramesByType(Connection pCon, int pAccountId, String frameType) throws Exception {
        UiFrameViewVector result = new UiFrameViewVector();
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(UiFrameDataAccess.BUS_ENTITY_ID, pAccountId);
        crit.addEqualTo(UiFrameDataAccess.FRAME_TYPE_CD, frameType);
        crit.addOrderBy(UiFrameDataAccess.UI_FRAME_ID);
        UiFrameDataVector frames = UiFrameDataAccess.select(pCon, crit);
        Iterator i = frames.iterator();
        while (i.hasNext()) {
            UiFrameData frame = (UiFrameData)i.next();
            UiFrameSlotViewVector slots = getFrameSlots(pCon, frame.getUiFrameId());
            UiFrameView frameView = UiFrameView.createValue();
            frameView.setFrameData(frame);
            frameView.setFrameSlotViewVector(slots);
            result.add(frameView);
        }
        return result;
    }


  public UiFrameSlotViewVector getFrameSlots(int pFrameId) throws RemoteException {
      Connection con=null;
      UiFrameSlotViewVector result = new UiFrameSlotViewVector();
      if (pFrameId <= 0) {
        throw new RemoteException("No frame id in the request");
      }
      try {
          con = getConnection();
          result = getFrameSlots(con, pFrameId);
      } catch(NamingException exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.getFrameSlots() Naming Exception happened");
      } catch(SQLException exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.getFrameSlots() SQL Exception happened");
      } catch(Exception exc) {
          exc.printStackTrace();
          throw new RemoteException("Error. UiFrameBean.getFrameSlots() problem");
      }finally{
          closeConnection(con);
      }
      return result;

  }

    private UiFrameSlotViewVector getFrameSlots(Connection pCon, int pFrameId) throws Exception {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(UiFrameSlotDataAccess.UI_FRAME_ID, pFrameId);
        crit.addOrderBy(UiFrameSlotDataAccess.UI_FRAME_SLOT_ID);
        return convertSlotDataVectorToViewVector(UiFrameSlotDataAccess.select(pCon, crit));

    }


    private UiFrameSlotViewVector convertSlotDataVectorToViewVector(UiFrameSlotDataVector pSlotDV)  throws SQLException {
        UiFrameSlotViewVector result = new UiFrameSlotViewVector();
        if (pSlotDV != null) {
            Iterator i = pSlotDV.iterator();
            while (i.hasNext()) {
                UiFrameSlotData slotD = (UiFrameSlotData)i.next();
                result.add(convertSlotDataToView(slotD));
            }
        }
        return result;
    }


    private UiFrameSlotView convertSlotDataToView(UiFrameSlotData pSlotD)  throws SQLException {
        UiFrameSlotView result = UiFrameSlotView.createValue();
        if (pSlotD == null) {
            return result;
        }
        result.setUiFrameSlotId(pSlotD.getUiFrameSlotId());
        result.setUiFrameId(pSlotD.getUiFrameId());
        result.setSlotTypeCd(pSlotD.getSlotTypeCd());
        result.setValue(pSlotD.getValue());
        result.setUrl(pSlotD.getUrl());
        result.setUrlTargetBlank(pSlotD.getUrlTargetBlank());
        result.setAddDate(pSlotD.getAddDate());
        result.setAddBy(pSlotD.getAddBy());
        result.setModDate(pSlotD.getModDate());
        result.setModBy(pSlotD.getModBy());
        byte[] data =new byte[0];
        if(pSlotD.getBlob()!=null){
            data = pSlotD.getBlob();
        }
        result.setImageData(data);

        return result;
    }


    private UiFrameSlotData convertSlotViewToData(Connection pCon, UiFrameSlotView pSlotV)  throws Exception {
        UiFrameSlotData result = UiFrameSlotData.createValue();
        if (pSlotV == null) {
            return result;
        }
        result.setUiFrameSlotId(pSlotV.getUiFrameSlotId());
        result.setUiFrameId(pSlotV.getUiFrameId());
        result.setSlotTypeCd(pSlotV.getSlotTypeCd());
        result.setValue(pSlotV.getValue());
        result.setUrl(pSlotV.getUrl());
        result.setUrlTargetBlank(pSlotV.getUrlTargetBlank());
        result.setAddDate(pSlotV.getAddDate());
        result.setAddBy(pSlotV.getAddBy());
        result.setModDate(pSlotV.getModDate());
        result.setModBy(pSlotV.getModBy());
        result.setBlob(pSlotV.getImageData());

        return result;
    }

}
