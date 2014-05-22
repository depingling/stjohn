package com.cleanwise.service.apps.dataexchange.xpedx;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class AXMasterData implements Serializable {

      private List<MasterAppData> mMasterAppDataList;
      private ArrayList<String> mErrors;

      public AXMasterData() {
          this.mMasterAppDataList = new ArrayList<MasterAppData>();
          this.mErrors = new ArrayList<String>();
      }

      public List<MasterAppData> getMasterAppDataList() {
          return mMasterAppDataList;
      }

      public void setMasterAppDataList(List<MasterAppData> pMasterAppDataList) {
          this.mMasterAppDataList = pMasterAppDataList;
      }

      public List<String> getErrors() {
          return mErrors;
      }

      public boolean hasErrors() {
          return !mErrors.isEmpty();
      }

  }
