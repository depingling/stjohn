package com.cleanwise.service.apps.dataexchange.xpedx;

import java.util.List;

public class BadLine<T> extends Line<T> {
      public BadLine(int pLine, T pItem, List<String> pErrors) {
          super(pLine, pItem, pErrors);
      }
  }
