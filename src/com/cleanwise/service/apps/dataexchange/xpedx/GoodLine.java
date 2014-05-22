package com.cleanwise.service.apps.dataexchange.xpedx;

import java.util.ArrayList;


public class GoodLine<T> extends Line<T> {
      public GoodLine(int pLine, T pItem) {
          super(pLine, pItem, new ArrayList<String>());
      }
  }

