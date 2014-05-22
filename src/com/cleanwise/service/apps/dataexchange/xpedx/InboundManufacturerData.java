package com.cleanwise.service.apps.dataexchange.xpedx;

import java.io.Serializable;


public class InboundManufacturerData implements Serializable {

      private String mManufacturerName;

      public String getManufacturerName() {
          return mManufacturerName;
      }

      public void setManufacturerName(String pManufacturerName) {
          this.mManufacturerName = pManufacturerName;
      }

      public String toString() {
          return "[mManufacturerName: " + mManufacturerName + "]";
      }
  }

