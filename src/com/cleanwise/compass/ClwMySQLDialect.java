package com.cleanwise.compass;

import org.apache.lucene.store.jdbc.dialect.*;

public class ClwMySQLDialect extends MySQLInnoDBDialect {

  public String openBlobSelectQuote() {
    return "`";
  }

  public String closeBlobSelectQuote() {
    return "`";
  }

}

