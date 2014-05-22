package com.cleanwise.compass;

import java.util.*;
import javax.naming.*;
import javax.sql.*;

import org.apache.log4j.Logger;
import org.compass.core.*;
import org.compass.core.config.*;
import org.compass.core.lucene.engine.store.*;
import org.compass.gps.device.jdbc.*;
import org.compass.gps.device.jdbc.mapping.*;
import com.cleanwise.service.api.util.*;

public class CompassUtil {
  protected static final Logger log = Logger.getLogger(CompassUtil.class);

  public static ClwResultSetJdbcGpsDevice[] setMapping(ClwResultSetJdbcGpsDevice[] gpsDevice, DataConfig dataConfig, CompassConfiguration config) throws Exception{
    gpsDevice = new ClwResultSetJdbcGpsDevice[dataConfig.getGpss().size()];
    int i = 0;
    for (Iterator gpsIter = dataConfig.getGpss().values().iterator(); gpsIter.hasNext();) {
      DataConfig.Gps gpsConf = (DataConfig.Gps) gpsIter.next();
      if ("resultSet".equals(gpsConf.getType())) {
        gpsDevice[i] = new ClwResultSetJdbcGpsDevice();
      } else {
        throw new CompassException("bad gps type:" + gpsConf.getType());
      }
      log.info("dataSource: " + gpsConf.getDataSource());
      gpsDevice[i].setDataSource(getDataSource(gpsConf.getDataSource()));
      log.info("gpsName: " + gpsConf.getId());
      gpsDevice[i].setName(gpsConf.getId());
      // to do
      gpsDevice[i].setAutoDetectVersionColumnSqlType(true);
      gpsDevice[i].setFetchSize(1000);

      ArrayList map = setMapProperty(gpsConf, config);
      for (Iterator iter = map.iterator(); iter.hasNext();) {
        gpsDevice[i].addMapping((ResultSetToResourceMapping)iter.next());
      }
      gpsDevice[i].setSnapshotPersister(new DBJdbcSnapshotPersister(getDataSource(getLuceneDsJndiName(config.getSettings())), gpsDevice[i].mappings));
      gpsDevice[i].setSaveSnapshotAfterMirror(true);
      gpsDevice[i].setDialect(new ClwJdbcDialect());
      i++;
    }
    log.info("GPS_NAME: " + gpsDevice[0].getName());
    return gpsDevice;
  }

  public static void setSimpleMapping(DataConfig dataConfig, CompassConfiguration config) throws Exception{
    for (Iterator gpsIter = dataConfig.getGpss().values().iterator(); gpsIter.hasNext();) {
      DataConfig.Gps gpsConf = (DataConfig.Gps) gpsIter.next();
      setMapProperty(gpsConf, config);
    }
  }


private static ArrayList setMapProperty(DataConfig.Gps gpsConf, CompassConfiguration config)  throws Exception{
  ArrayList map = new ArrayList();
  for (Iterator mapIter = gpsConf.getMappings().values().iterator(); mapIter.hasNext();) {
    DataConfig.Mapping mapConf = (DataConfig.Mapping) mapIter.next();
    ResultSetToResourceMapping mapping;
    if ("resultSet".equals(mapConf.getType())) {
      mapping = new ResultSetToResourceMapping();
    } else {
      throw new CompassException("bad mapping type:" + mapConf.getType());
    }
    log.info("alias: " + mapConf.getAlias());
    mapping.setAlias(mapConf.getAlias());
    log.info("selectQuery: " + mapConf.getSelectQuery());
    mapping.setSelectQuery(mapConf.getSelectQuery());
    log.info("versionQuery: " + mapConf.getVersionQuery());
    mapping.setVersionQuery(mapConf.getVersionQuery());
    log.info("indexUnMappedColumns: " + mapConf.getIndexUnMappedColumns());
    mapping.setIndexUnMappedColumns(mapConf.getIndexUnMappedColumns());
    for (Iterator iter = mapConf.getIdMappings().values().iterator(); iter.hasNext();) {
      DataConfig.ColumnMapping column = (DataConfig.ColumnMapping)iter.next();
      log.info("columnName: " + column.getColumnName());
      log.info("  propertyName: " + column.getPropertyName());
      log.info("    columnNameForVersion: " + column.getColumnNameForVersion());
      mapping.addIdMapping(new IdColumnToPropertyMapping(column.getColumnName(),
                                                         column.getPropertyName(),
                                                         column.getColumnNameForVersion()));
    }
    for (Iterator iter = mapConf.getDataMappings().values().iterator(); iter.hasNext();) {
      DataConfig.ColumnMapping column = (DataConfig.ColumnMapping)iter.next();
      log.info("columnName: " + column.getColumnName());
      log.info("  propertyName: " + column.getPropertyName());
      mapping.addDataMapping(new DataColumnToPropertyMapping(column.getColumnName(),
                                                             column.getPropertyName(),
                                                             Property.Index.TOKENIZED,
                                                             Property.Store.NO,
                                                             Property.TermVector.NO));
    }
    for (Iterator iter = mapConf.getVersionMappings().values().iterator(); iter.hasNext();) {
      DataConfig.ColumnMapping column = (DataConfig.ColumnMapping)iter.next();
      log.info("columnName: " + column.getColumnName());
      mapping.addVersionMapping(new VersionColumnMapping(column.getColumnName()));
    }
//        gpsDevice[i].addMapping(mapping);
    map.add(mapping);
    config.addMappingResover(new ResultSetResourceMappingResolver(mapping, getDataSource(JNDINames.DATA_SOURCE)));
  }
  return map;
}

public static String getLuceneDsJndiName(CompassSettings settings) {
  String luceneDsJndiName = settings.getSetting(CompassEnvironment.CONNECTION);
  if (luceneDsJndiName.startsWith(LuceneSearchEngineStoreFactory.JDBC_PREFIX)) {
    luceneDsJndiName = luceneDsJndiName.substring(LuceneSearchEngineStoreFactory.JDBC_PREFIX.length());
  }
  return luceneDsJndiName;
}


  private static DataSource getDataSource(String dsName) throws  NamingException    {
    InitialContext ctx = null;
    DataSource ds = null;
    try {
      ctx = new InitialContext();
      ds = (DataSource) ctx.lookup(dsName);
      return ds;
    } catch (NamingException ne) {
      log.error("CompassService: unable to lookup DS: " + dsName, ne);
      throw ne;
    } finally {
      if (ctx != null) {
        try {
          ctx.close();
        } catch (NamingException ignore) {}
      }
    }
  }

}
