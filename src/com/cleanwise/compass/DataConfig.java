package com.cleanwise.compass;

import java.util.*;

public class DataConfig {
  private HashMap gpsConfig = new HashMap();
  public DataConfig() {
  }

  public void putGps(Gps gps) {
    gpsConfig.put(gps.id, gps);
  }
  public Gps getGps(String id) {
    return (Gps)gpsConfig.get(id);
  }
  public HashMap getGpss() {
    return gpsConfig;
  }


  public static class Gps {
    String id = null;
    String type = null;
    String dataSource = null;
    private HashMap mappings = new HashMap();
    public Gps() {
    }
    public Gps(String id) {
      this.id = id;
    }
    public String getId() {
      return this.id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getType() {
      return this.type;
    }
    public void setType(String type) {
      this.type = type;
    }
    public String getDataSource() {
      return this.dataSource;
    }
    public void setDataSource(String dataSource) {
      this.dataSource = dataSource;
    }
    public void putMapping(Mapping mapping) {
      mappings.put(mapping.alias, mapping);
    }
    public Mapping getMapping(String id) {
      return (Mapping)mappings.get(id);
    }
    public HashMap getMappings() {
      return mappings;
    }
  }

  public static class Mapping {
    String alias = null;
    String type = null;
    boolean indexUnMappedColumns = false;
    String selectQuery = null;
    String versionQuery = null;
    HashMap idMappings = new HashMap();
    HashMap dataMappings = new HashMap();
    HashMap versionMappings = new HashMap();
    public Mapping() {
    }
    public Mapping(String alias) {
      this.alias = alias;
    }
    public String getAlias() {
      return this.alias;
    }
    public void setAlias(String alias) {
      this.alias = alias;
    }
    public String getType() {
      return this.type;
    }
    public void setType(String type) {
      this.type = type;
    }
    public boolean getIndexUnMappedColumns() {
      return this.indexUnMappedColumns;
    }
    public void setIndexUnMappedColumns(boolean indexUnMappedColumns) {
      this.indexUnMappedColumns = indexUnMappedColumns;
    }
    public String getSelectQuery() {
      return this.selectQuery;
    }
    public void setSelectQuery(String selectQuery) {
      this.selectQuery = selectQuery;
    }
    public String getVersionQuery() {
      return this.versionQuery;
    }
    public void setVersionQuery(String versionQuery) {
      this.versionQuery = versionQuery;
    }
    public void putIdMapping(ColumnMapping columnMapping) {
      idMappings.put(columnMapping.columnName, columnMapping);
    }
    public ColumnMapping getIdMapping(String columnName) {
      return (ColumnMapping)idMappings.get(columnName);
    }
    public HashMap getIdMappings() {
      return idMappings;
    }
    public void putDataMapping(ColumnMapping columnMapping) {
      dataMappings.put(columnMapping.columnName, columnMapping);
    }
    public ColumnMapping getDtaMapping(String columnName) {
      return (ColumnMapping)dataMappings.get(columnName);
    }
    public HashMap getDataMappings() {
      return dataMappings;
    }
    public void putVersionMapping(ColumnMapping columnMapping) {
      versionMappings.put(columnMapping.columnName, columnMapping);
    }
    public ColumnMapping getVersionMapping(String columnName) {
      return (ColumnMapping)versionMappings.get(columnName);
    }
    public HashMap getVersionMappings() {
      return versionMappings;
    }
  }

  public static class ColumnMapping {
    String columnName = null;
    String propertyName = null;
    String columnNameForVersion = null;
    public ColumnMapping() {
    }
    public ColumnMapping(String columnName) {
      this.columnName = columnName;
    }
    public String getColumnName() {
      return this.columnName;
    }
    public void setColumnName(String columnName) {
      this.columnName = columnName;
    }
    public String getPropertyName() {
      return this.propertyName;
    }
    public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
    }
    public String getColumnNameForVersion() {
      return this.columnNameForVersion;
    }
    public void setColumnNameForVersion(String columnNameForVersion) {
      this.columnNameForVersion = columnNameForVersion;
    }
  }
}
