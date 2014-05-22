package com.cleanwise.compass;

import java.sql.*;
import java.text.*;
import java.util.*;

import org.compass.core.*;
import org.compass.core.config.*;
import org.compass.core.mapping.*;
import org.compass.core.spi.*;
import org.compass.gps.*;
import org.compass.gps.device.jdbc.*;
import org.compass.gps.device.jdbc.mapping.*;
import org.compass.gps.device.jdbc.snapshot.*;

public class ClwResultSetJdbcGpsDevice extends AbstractJdbcActiveMirrorGpsDevice {

      final String STOP_INDEXING = "stop";
      final String STOPPING_INDEXING = "stopping";
      final double INDEX_MAX_SIZE = 30000;
      final double MIRROR_MAX_SIZE = 5000;
      protected NumberFormat nf = new DecimalFormat("0.0");
      protected NumberFormat nfi = new DecimalFormat("0");

      protected List mappings = new ArrayList();

      private JdbcSnapshot snapshot;

      private boolean autoDetectVersionColumnSqlType = true;

      /**
       * performs operations on startup, such as auto generation of mappings for
       * mappings that implement the {@link AutoGenerateMapping}, auto detection
       * of version column jdbc type, and {@link JdbcSnapshot} loading (using the
       * {@link org.compass.gps.device.jdbc.snapshot.JdbcSnapshotPersister}).
       */
      protected void doStart() throws CompassGpsException {
          super.doStart();
          // call auto generate for mappings that implement the AutoGenerate
          // interface
          for (Iterator it = mappings.iterator(); it.hasNext();) {
              ResultSetToResourceMapping rsMapping = (ResultSetToResourceMapping) it.next();
              if (rsMapping instanceof AutoGenerateMapping) {
                  ((AutoGenerateMapping) rsMapping).generateMappings(dataSource);
              }
          }
          // support for meta data lookup
          CommonMetaDataLookup commonMetaDataLookup = new CommonMetaDataLookup(((InternalCompass) compassGps
                  .getIndexCompass()).getMetaData());
          for (Iterator it = mappings.iterator(); it.hasNext();) {
              ResultSetToResourceMapping rsMapping = (ResultSetToResourceMapping) it.next();
              rsMapping.setAlias(commonMetaDataLookup.lookupAliasName(rsMapping.getAlias()));
              for (Iterator it1 = rsMapping.mappingsIt(); it1.hasNext();) {
                  List columns = (List) it1.next();
                  for (Iterator it2 = columns.iterator(); it2.hasNext();) {
                      ColumnMapping columnMapping = (ColumnMapping) it2.next();
                      if (columnMapping instanceof ColumnToPropertyMapping) {
                          ColumnToPropertyMapping columnToPropertyMapping = (ColumnToPropertyMapping) columnMapping;
                          columnToPropertyMapping.setPropertyName(commonMetaDataLookup
                                  .lookupMetaDataName(columnToPropertyMapping.getPropertyName()));
                      }
                  }
              }
          }
          // double check that all the result set mapping have Compass::Core
          // resource mapping
          for (Iterator it = mappings.iterator(); it.hasNext();) {
              ResultSetToResourceMapping rsMapping = (ResultSetToResourceMapping) it.next();
              if (!compassGps.hasMappingForEntityForMirror(rsMapping.getAlias(), CascadeMapping.Cascade.ALL)) {
                  throw new IllegalStateException(
                          buildMessage("No resource mapping defined in gps mirror compass for alias ["
                                  + rsMapping.getAlias() + "]. Did you defined a jdbc mapping builder?"));
              }
              if (!compassGps.hasMappingForEntityForIndex(rsMapping.getAlias())) {
                  throw new IllegalStateException(
                          buildMessage("No resource mapping defined in gps index compass for alias ["
                                  + rsMapping.getAlias() + "]. Did you defined a jdbc mapping builder?"));
              }
          }
          if (isAutoDetectVersionColumnSqlType()) {
              if (log.isInfoEnabled()) {
                  log.info(buildMessage("Auto detecting version column sql types"));
              }
              // set the version databse type
              Connection connection = JdbcUtils.getConnection(dataSource);
              PreparedStatement ps = null;
              ResultSet rs = null;
              try {
                  for (Iterator it = mappings.iterator(); it.hasNext();) {
                      ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) it.next();
                      if (!mapping.supportsVersioning()) {
                          continue;
                      }
                      ps = connection.prepareStatement(mapping.getVersionQuery());
                      ps.setFetchSize(1);
                      rs = ps.executeQuery();
                      ResultSetMetaData metaData = rs.getMetaData();
                      for (Iterator verIt = mapping.versionMappingsIt(); verIt.hasNext();) {
                          VersionColumnMapping versionMapping = (VersionColumnMapping) verIt.next();
                          int columnIndex;
                          if (versionMapping.isUsingColumnIndex()) {
                              columnIndex = versionMapping.getColumnIndex();
                          } else {
                              columnIndex = JdbcUtils.getColumnIndexFromColumnName(metaData, versionMapping
                                      .getColumnName());
                          }
                          versionMapping.setSqlType(metaData.getColumnType(columnIndex));
                      }
                  }
              } catch (SQLException e) {
                  throw new JdbcGpsDeviceException(buildMessage("Failed to find version column type"), e);
              } finally {
                  JdbcUtils.closeResultSet(rs);
                  JdbcUtils.closeStatement(ps);
                  JdbcUtils.closeConnection(connection);
              }
          }
          if (isMirrorDataChanges()) {
              if (log.isInfoEnabled()) {
                  log.info(buildMessage("Using mirroring, loading snapshot data"));
              }
              // set up the snapshot
              snapshot = getSnapshotPersister().load();
              for (Iterator it = mappings.iterator(); it.hasNext();) {
                  ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) it.next();
                  if (mapping.supportsVersioning() && snapshot.getAliasSnapshot(mapping.getAlias()) == null) {
                      if (log.isDebugEnabled()) {
                          log.debug(buildMessage("Alias [" + mapping.getAlias() + "] not found in snapshot data, creating..."));
                      }
                      JdbcAliasSnapshot aliasSnapshot = new JdbcAliasSnapshot(mapping.getAlias());
                      snapshot.putAliasSnapshot(aliasSnapshot);
                  }
              }
              // configure the snapshot event listener
              Connection connection = JdbcUtils.getConnection(dataSource);
              try {
                  getSnapshotEventListener().configure(new ConfigureSnapshotEvent(connection, dialect, mappings));
              } finally {
                  JdbcUtils.closeConnection(connection);
              }
          }
          if (log.isDebugEnabled()) {
              for (Iterator it = mappings.iterator(); it.hasNext();) {
                  log.debug(buildMessage("Using DB Mapping " + it.next()));
              }
          }
      }

      /**
       * Saves the {@link JdbcSnapshot}.
       */
      protected void doStop() throws CompassGpsException {
          getSnapshotPersister().save(snapshot);
          super.doStop();
      }

    protected void doIndex(CompassSession session) throws CompassGpsException {
        // reset the snapshot data before we perform the index operation
        log.info("doIndex() is started. this.isPerformingIndexOperation() = " + this.isPerformingIndexOperation());
        if (log.isInfoEnabled()) {
            log.info("{" + getName() + "}: Indexing the database with fetch size [" + this.getFetchSize() + "]");
        }
        snapshot = getSnapshotPersister().load();
        if (snapshot == null) {
          log.info("Snapshot not found, creating...");
          snapshot = new JdbcSnapshot();
        }
        Connection connection = JdbcUtils.getConnection(dataSource);
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement countPs = null;
        ResultSet countRs = null;
        try {
          double integralSize = 0;
          double integralSizeMax = INDEX_MAX_SIZE;
          for (Iterator it = mappings.iterator(); it.hasNext();) {
            ResultSetToResourceMapping mapping = (ResultSetToResourceMapping)it.next();
            if (!mapping.supportsVersioning()) {
              continue;
            }
            String countQuery = "select count(*) " + mapping.getVersionQuery().substring(mapping.getVersionQuery().indexOf("from"));
            log.info(countQuery);
            countPs = connection.prepareStatement(countQuery);
            countRs = countPs.executeQuery();
            int rowCount = 0;
            if (countRs.next()) {
              rowCount = countRs.getInt(1);
            }
            integralSize += rowCount;
          }
          double integralCount = 0;
          int show = -1;
          log.info("Start. All rows: " + nfi.format(integralSize));

        for (Iterator it = mappings.iterator(); it.hasNext();) {
          ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) it.next();
          JdbcAliasSnapshot oldAliasSnapshot = null;
          if (mapping.supportsVersioning()) {
            oldAliasSnapshot = snapshot.getAliasSnapshot(mapping.getAlias());
            if (oldAliasSnapshot == null) {
              log.info("Alias [" + mapping.getAlias() + "] not found in snapshot data, creating...");
              oldAliasSnapshot = new JdbcAliasSnapshot(mapping.getAlias());
              snapshot.putAliasSnapshot(oldAliasSnapshot);
            }
          }

          Iterator iter = oldAliasSnapshot.rowSnapshotIt();
          int snapshotRows = 0;
          while (iter.hasNext()) {
            iter.next();
            snapshotRows++;
          }
          log.info("Start. Alias [" + mapping.getAlias() + "]. Indexed rows: " + snapshotRows);

          ps = connection.prepareStatement(mapping.getSelectQuery());
          if (ps == null) {
            if (log.isDebugEnabled()) {
              log.debug("{" + getName() + "} Executing select query ["
                        + mapping.getSelectQuery() + "]");
            }
            ps = connection.prepareStatement(mapping.getSelectQuery());
          }
          if (getFetchSize() > 0) {
            ps.setFetchSize(getFetchSize());
          }

          rs = ps.executeQuery();
          while (rs.next()) {
            JdbcAliasRowSnapshot newRowSnapshot = new JdbcAliasRowSnapshot();
            ResultSetRowMarshallHelper marshallHelper = new ResultSetRowMarshallHelper(mapping,
                                                                                       dialect,
                                                                                       newRowSnapshot,
                                                                                       compassGps.getMirrorCompass());
            marshallHelper.marshallResultSet(rs);
            // new and old have the same ids
            JdbcAliasRowSnapshot oldRowSnapshot = oldAliasSnapshot.getRow(newRowSnapshot);
            // new row or updated row
            if (oldRowSnapshot == null) {
              oldAliasSnapshot.putRow(newRowSnapshot);
              processRow(mapping, rs, session);
              integralCount++;
              int perc = (new Integer(nfi.format((integralCount/integralSizeMax)*100))).intValue();
              if (perc % 5 == 0 && perc != show) {
                log.info("Indexing (" + nf.format((integralCount/integralSizeMax)*100) + "% is done)");
                show = perc;
              }
              System.setProperty("compass.index.percentage", nf.format((integralCount/integralSizeMax)*100));
              if (integralCount >= integralSizeMax || STOP_INDEXING.equals(System.getProperty("compass.index.process"))) {
                System.setProperty("compass.index.process", STOPPING_INDEXING);
                break; // temp
              }
            }
          }
          iter = oldAliasSnapshot.rowSnapshotIt();
          snapshotRows = 0;
          while (iter.hasNext()) {
            iter.next();
            snapshotRows++;
          }
          log.info("End. Alias [" + mapping.getAlias() + "]. Indexed rows: " + snapshotRows);
        }
      } catch (CompassException e) {
        log.error("Failed to index database", e);
        throw e;
      } catch (Exception e) {
        log.error("Failed to index database", e);
        throw new JdbcGpsDeviceException("Failed to index database", e);
      } finally {
        JdbcUtils.closeResultSet(countRs);
        JdbcUtils.closeStatement(countPs);
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(ps);
        JdbcUtils.closeConnection(connection);
      }

      if (log.isInfoEnabled()) {
        log.info("{" + getName() + "}: Finished indexing the database");
      }

      // save the sanpshot data
      getSnapshotPersister().save(snapshot);
    }

    /**
     * Returns the array of index execution with a size of the number of
     * mappings.
     */
    protected IndexExecution[] doGetIndexExecutions(Connection connection) throws SQLException, JdbcGpsDeviceException {
        IndexExecution[] indexExecutions = new IndexExecution[mappings.size()];
        for (int i = 0; i < indexExecutions.length; i++) {
            ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) mappings.get(i);
            indexExecutions[i] = new IndexExecution(mapping, mapping.getSelectQuery());
        }
        return indexExecutions;
    }

    /**
     * Index the given <code>ResultSet</code> row into a Compass
     * <code>Resource</code>.
     */
    protected Object processRowValue(Object description, ResultSet rs, CompassSession session) throws SQLException,
            CompassException {

        if (log.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer();
            sb.append(buildMessage("Indexing data row with values "));
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                sb.append("[").append(metaData.getColumnName(i)).append(":");
                String value = rs.getString(i);
                if (rs.wasNull()) {
                    value = "(null)";
                }
                sb.append(value);
                sb.append("] ");
            }
            log.debug(sb.toString());
        }

        ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) description;

        JdbcAliasRowSnapshot rowSnapshot = null;
        if (shouldMirrorDataChanges() && mapping.supportsVersioning()) {
            rowSnapshot = new JdbcAliasRowSnapshot();
        }
        Resource resource = session.createResource(mapping.getAlias());
        ResultSetRowMarshallHelper marshallHelper = new ResultSetRowMarshallHelper(mapping, session, dialect, resource,
                rowSnapshot);
        marshallHelper.marshallResultSet(rs);

        if (shouldMirrorDataChanges() && mapping.supportsVersioning()) {
            snapshot.getAliasSnapshot(mapping.getAlias()).putRow(rowSnapshot);
        }

        return resource;
    }

    /**
     * Performs the data change mirroring operation.
     */
    public synchronized void performMirroring() throws JdbcGpsDeviceException {
        log.info("performMirroring(). start.");
        if (!shouldMirrorDataChanges() || isPerformingIndexOperation()) {
          log.info("performMirroring(). return.");
            return;
        }
        log.info("performMirroring(). continue.");
        if (snapshot == null) {
            throw new IllegalStateException(
                    buildMessage("Versioning data was not properly initialized, did you index the device or loaded the data?"));
        }
        Connection connection = JdbcUtils.getConnection(dataSource);
        PreparedStatement countPs = null;
        ResultSet countRs = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
          double integralSizeMax = MIRROR_MAX_SIZE;
          double integralCount = 0;

            for (Iterator it = mappings.iterator(); it.hasNext();) {
                ResultSetToResourceMapping mapping = (ResultSetToResourceMapping) it.next();
                if (!mapping.supportsVersioning()) {
                    continue;
                }
                JdbcAliasSnapshot oldAliasSnapshot = snapshot.getAliasSnapshot(mapping.getAlias());
                if (oldAliasSnapshot == null) {
                    log.warn(buildMessage("No snapshot for alias [" + mapping.getAlias()
                            + "] even though there should be support for versioning ignoring the alias"));
                    continue;
                }
                JdbcAliasSnapshot newAliasSnapshot = new JdbcAliasSnapshot(mapping.getAlias());
                ArrayList createdRows = new ArrayList();
                ArrayList updatedRows = new ArrayList();
                ArrayList deletedRows = new ArrayList();
//                log.info(buildMessage("Executing version query [" + mapping.getVersionQuery() + "]"));
                ps = connection.prepareStatement(mapping.getVersionQuery());
                if (getFetchSize() > 0) {
                    ps.setFetchSize(getFetchSize());
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                  integralCount++;
                  if (integralCount % 50000 == 0) {
                    log.info("Mirroring (" + integralCount + " rows is processed)");

                  }

                    if (log.isDebugEnabled()) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(buildMessage("Version row with values "));
                        ResultSetMetaData metaData = rs.getMetaData();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            sb.append("[").append(metaData.getColumnName(i)).append(":");
                            String value = rs.getString(i);
                            if (rs.wasNull()) {
                                value = "(null)";
                            }
                            sb.append(value);
                            sb.append("] ");
                        }
                        log.debug(sb.toString());
                    }

                    JdbcAliasRowSnapshot newRowSnapshot = new JdbcAliasRowSnapshot();
                    ResultSetRowMarshallHelper marshallHelper = new ResultSetRowMarshallHelper(mapping, dialect,
                            newRowSnapshot, compassGps.getMirrorCompass());
                    marshallHelper.marshallResultSet(rs);

                    // new and old have the same ids
                    JdbcAliasRowSnapshot oldRowSnapshot = oldAliasSnapshot.getRow(newRowSnapshot);

                    // new row or updated row
                    if (oldRowSnapshot == null) {
                        createdRows.add(newRowSnapshot);
                    } else if (oldRowSnapshot.isOlderThan(newRowSnapshot)) {
                        updatedRows.add(newRowSnapshot);
                    }

                    newAliasSnapshot.putRow(newRowSnapshot);
                    if (createdRows.size() + updatedRows.size() + deletedRows.size() > integralSizeMax) {
                      break; // temp
                    }
                }
                for (Iterator oldRowIt = oldAliasSnapshot.rowSnapshotIt(); oldRowIt.hasNext();) {
                    JdbcAliasRowSnapshot tmpRow = (JdbcAliasRowSnapshot) oldRowIt.next();
                    // deleted row
                    if (newAliasSnapshot.getRow(tmpRow) == null) {
                        deletedRows.add(tmpRow);
                    }
                }
                if (!createdRows.isEmpty() || !updatedRows.isEmpty()) {
                    getSnapshotEventListener().onCreateAndUpdate(
                            new CreateAndUpdateSnapshotEvent(connection, dialect, mapping, createdRows, updatedRows,
                                    compassGps));
                }
                if (!deletedRows.isEmpty()) {
                    getSnapshotEventListener().onDelete(
                            new DeleteSnapshotEvent(connection, dialect, mapping, deletedRows, compassGps));
                }
                snapshot.putAliasSnapshot(newAliasSnapshot);
                if (integralCount > integralSizeMax) {
                  break; // temp
                }
            }
        } catch (SQLException e) {
            throw new JdbcGpsDeviceException(buildMessage("Failed while mirroring data changes"), e);
        } finally {
            JdbcUtils.closeResultSet(countRs);
            JdbcUtils.closeStatement(countPs);
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(connection);
        }
        if (isSaveSnapshotAfterMirror()) {
            getSnapshotPersister().save(snapshot);
        }
    }

    /**
     * Adds a mapping to be indexed and mirrored.
     */
    public void addMapping(ResultSetToResourceMapping mapping) {
        this.mappings.add(mapping);
    }

    /**
     * Adds an array of mappings to be indexed and mirrored.
     */
    public void setMappings(ResultSetToResourceMapping[] mappingsArr) {
        for (int i = 0; i < mappingsArr.length; i++) {
            addMapping(mappingsArr[i]);
        }
    }

    /**
     * Should the device auto detect the version columns jdbc type.
     */
    public boolean isAutoDetectVersionColumnSqlType() {
        return autoDetectVersionColumnSqlType;
    }

    /**
     * Sets if the device auto detect the version columns jdbc type.
     */
    public void setAutoDetectVersionColumnSqlType(boolean autoDetectVersionColumnSqlType) {
        this.autoDetectVersionColumnSqlType = autoDetectVersionColumnSqlType;
    }
}
