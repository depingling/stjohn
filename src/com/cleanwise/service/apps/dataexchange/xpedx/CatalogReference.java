package com.cleanwise.service.apps.dataexchange.xpedx;


  public class CatalogReference extends Reference {

      public CatalogReference(String pAccountNumber, String pCatalogNumber) {
          create(pAccountNumber, pCatalogNumber);
      }

      public String getAccountNumber() {
          return (String) get(0);
      }

      public String getCatalogNumber() {
          return (String) get(1);
      }

  }
