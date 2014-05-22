package com.cleanwise.service.apps.dataexchange.xpedx;

 public class DistributorReference extends Reference {

      public DistributorReference(String pDistributor) {
          create(pDistributor);
      }

      public String getDistributor() {
          return (String) get(0);
      }

  }
