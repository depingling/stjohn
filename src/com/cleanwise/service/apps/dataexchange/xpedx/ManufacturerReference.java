package com.cleanwise.service.apps.dataexchange.xpedx;


  public class ManufacturerReference extends Reference {

      public ManufacturerReference(String pManufacturer) {
          create(pManufacturer);
      }

      public String getManufacturer() {
          return (String) get(0);
      }

  }
