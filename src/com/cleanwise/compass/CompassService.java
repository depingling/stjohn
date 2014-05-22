package com.cleanwise.compass;

import java.util.*;
import javax.naming.*;
import javax.sql.*;

import org.apache.log4j.Logger;
import org.compass.core.*;
import org.compass.core.config.*;
import org.jboss.naming.*;
import org.jboss.system.*;

public class CompassService extends ServiceMBeanSupport implements CompassServiceMBean {
      protected static final Logger log = Logger.getLogger(CompassService.class);

    private String jndiName;
    private Compass compass;
    private ClwResultSetJdbcGpsDevice[] gpsDevice;
    private ScheduledMirroringGpsDevice scheduledDevice;
    private CompassConfiguration config;
    private LargeIndexCompassGps gps;
    private HashMap compassMap = new HashMap();

    public CompassService() {
        // default JNDI name for Compass
        jndiName = "Compass";
    }

    public void setJndiName(String jndiName) throws Exception {
        String oldName = this.jndiName;
        this.jndiName = jndiName;

        if (super.getState() == STARTED) {
            unbind(oldName);

            try {
                rebind();
            } catch (NamingException ne) {
                log.error("Failed to rebind Compass", ne);

                throw new CompassException(
                        "Failed to rebind Compass - ", ne);
            }
        }
    }

    public String getJndiName() {
        return jndiName;
    }

    public String getName() {
        return "CompassService(" + jndiName + ")";
    }

    public Object getObject() throws Exception {
        return this.compassMap;
    }

    public void createService() throws Exception {
        log.info("Create CompassService(" + jndiName + ")...");


        log.info("CompassService(" + jndiName + ") created.");
    }

    public void destroyService() throws Exception {
        log.info("Destroy CompassService(" + jndiName + ")...");
        compass.close();
        log.info("CompassService(" + jndiName + ") destroyed.");
    }

    public void startService() throws Exception {
        log.info("Start CompassService(" + jndiName + ")...");
        try {

          config = new CompassConfiguration().configure("/compass.cfg.xml");

          XmlConfigurationBuilder builder = new XmlConfigurationBuilder();
          DataConfig dataConfig = new DataConfig();
          builder.configure("mapping.xml", dataConfig);
          gpsDevice = CompassUtil.setMapping(gpsDevice, dataConfig, config);

//          config.setSetting(LuceneEnvironment.JdbcStore.DIALECT, ClwMySQLDialect.class.getName());
          config.setSetting("compass.engine.useCompoundFile","false");
          config.setSetting("compass.engine.queryParser.default.allowLeadingWildcard","true");
          config.setSetting("compass.engine.queryParser.default.type","org.compass.core.lucene.engine.queryparser.DefaultLuceneQueryParser");

          compass = config.buildCompass();

          // temporary, one device only
          scheduledDevice = new ScheduledMirroringGpsDevice(gpsDevice[0]);
          scheduledDevice.setPeriod(300000);

          gps = new LargeIndexCompassGps(compass);
          gps.addGpsDevice(scheduledDevice);

          compassMap.put("compass", compass);
          compassMap.put("gps", gps);
        } catch (Exception e) {
            log.error("Failed to start Compass", e);
            throw new CompassException("Failed to start Compass - ", e);
        }
        try {
            rebind();
        } catch (NamingException ne) {
            log.error("Failed to rebind Compass", ne);
            throw new CompassException("Failed to rebind Compass - ", ne);
        }
        log.info("CompassService(" + jndiName + ") started.");
    }

    public void stopService() throws Exception {
        log.info("Stop CompassService(" + jndiName + ")...");
        try {
          gps.stop();
        } catch (Exception e) {
            log.error("Failed to stop Compass", e);
            throw new CompassException(
                    "Failed to stop Compass - ", e);
        }
        unbind(jndiName);
        log.info("CompassService(" + jndiName + ") stopped.");
    }

    private void rebind() throws NamingException {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            Name fullName = rootCtx.getNameParser("").parse(jndiName);
            NonSerializableFactory.rebind(fullName, compassMap, true);
        } finally {
            if (rootCtx != null) {
                try {
                    rootCtx.close();
                } catch (NamingException ignore) {}
            }
        }
    }

    private void unbind(String jndiName) {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            rootCtx.unbind(jndiName);
            NonSerializableFactory.unbind(jndiName);
        } catch (NamingException e) {
            log.warn("Failed to unbind compass with jndiName: " + jndiName, e);
        } finally {
            if (rootCtx != null) {
                try {
                    rootCtx.close();
                } catch (NamingException ignore) {}
            }
        }
    }

    private DataSource getDataSource(String dsName) throws  NamingException    {
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



