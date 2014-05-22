package com.cleanwise.compass;

import java.sql.*;

import org.apache.log4j.Logger;
import org.compass.gps.device.jdbc.dialect.*;
import org.compass.gps.device.jdbc.mapping.*;

public class ClwJdbcDialect extends DefaultJdbcDialect {
  private static final Logger log = Logger.getLogger(ClwJdbcDialect.class);
  public ClwJdbcDialect() {
  }

  public void setParameter(PreparedStatement ps, int paramIndex, String value) throws SQLException {
          int iValue = Integer.parseInt(value);
          ps.setInt(paramIndex, iValue);
  }


  public String getStringValue(ResultSet rs, ColumnMapping mapping) throws SQLException {
    String value = null;
    if (mapping.isUsingColumnIndex()) {
      value = rs.getString(mapping.getColumnIndex());
    } else {
      value = rs.getString(mapping.getColumnName());
      if (value == null) {
        byte[] bytes = rs.getBytes(mapping.getColumnName());
        if (bytes == null) {
          return null;
        } else {
          value = new String(bytes);
        }
      }
    }
    if (rs.wasNull()) {
      return null;
    }
    return value;
}


}
