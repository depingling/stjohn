package com.cleanwise.service.apps.dataexchange.xpedx;

import java.util.ArrayList;


public class ComparableList extends ArrayList<Comparable> implements Comparable {

      public void create(Comparable... pKeys) {
          for (int i = 0; pKeys != null && i < pKeys.length; i++) {
              this.add(pKeys[i]);
          }
      }

      @Override
      public boolean equals(Object obj) {
          if (super.equals(obj)) {
              return true;
          }
          if (obj instanceof ComparableList) {
              ComparableList other = (ComparableList) obj;
              if (other.size() != this.size()) {
                  return false;
              }
              for (int i = 0; i < this.size(); i++) {
                  Comparable item = this.get(i);
                  if (item == null || !item.equals(other.get(i))) {
                      return false;
                  }
              }
          }
          return true;
      }

      @Override
      public int hashCode() {
          int h = 0;
          for (Object i : this) {
              h = 31 * h + i.hashCode();
          }
          return h;
      }

      public int compareTo(Object o) {
          if (o instanceof ComparableList) {
              ComparableList other = (ComparableList) o;
              int count = Math.min(other.size(), this.size());
              for (int i = 0; i < count; i++) {
                  Comparable thisItem = this.get(i);
                  Comparable otherItem = other.get(i);
                  if (thisItem == null && otherItem == null) {
                  } else if (thisItem == null) {
                      return -1;
                  } else if (otherItem == null) {
                      return 1;
                  } else {
                      int result = thisItem.compareTo(otherItem);
                      if (result != 0) {
                          return result;
                      }
                  }
              }
              return new Integer(this.size()).compareTo(other.size());
          }
          return -1;
      }
  }
