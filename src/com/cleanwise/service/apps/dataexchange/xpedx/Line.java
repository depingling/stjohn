package com.cleanwise.service.apps.dataexchange.xpedx;

import java.io.Serializable;
import java.util.List;


public class Line<T> implements Serializable {

      private int mLine;
      private T mItem;
      private List<String> mErrors;

      public Line(int pLine, T pItem, List<String> pErrors) {
          this.mLine = pLine;
          this.mItem = pItem;
          this.mErrors = pErrors;
      }


      public T getItem() {
          return mItem;
      }

      public void setItem(T pItem) {
          this.mItem = pItem;
      }

      public int getLine() {
          return mLine;
      }

      public void setmLine(int pLine) {
          this.mLine = pLine;
      }

      public List<String> getErrors() {
          return mErrors;
      }

      public void setErrors(List<String> mErrors) {
          this.mErrors = mErrors;
      }
  }


