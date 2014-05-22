package com.cleanwise.compass;

import java.util.*;
import javax.naming.*;

import org.apache.log4j.Logger;
import org.compass.core.*;
import org.compass.gps.*;

public class CompassSearchHelper {
  protected static final Logger log = Logger.getLogger(CompassSearchHelper.class);
  private static CompassGps gps = null;
  private static Compass compass = null;
  private static CompassGpsDevice mirror = null;

  public static final String EVENT_INDEX = "Event";

  private static Compass getCompass() {
    if (compass == null) {
      try {
        InitialContext ctx = new InitialContext();
        HashMap compassMap = (HashMap) ctx.lookup("Compass");
        compass = (Compass) compassMap.get("compass");
        gps = (CompassGps) compassMap.get("gps");
        mirror = (CompassGpsDevice) compassMap.get("mirror");
      } catch (NamingException ne) {
        log.error("Failed to lookup Compass", ne);
        throw new CompassException("Failed to lookup Compass", ne);
      }
    }
    return compass;
  }

  public static CompassDetachedHits search(String query, String subindex, String dateFrom, String dateTo) {
    CompassSession session = getCompass().openSession();
    session.beginTransaction();
    CompassQueryBuilder queryBuilder = session.queryBuilder();
    CompassQueryFilterBuilder queryFilterBuilder = session.queryFilterBuilder();
    CompassHits hits = null;
    String filter = "EVENT_MOD_DATE:[" + dateFrom + " TO " + dateTo + "]";
      hits = queryBuilder.queryString(query).toQuery().setAliases(new String[] {subindex}).
          setFilter(queryFilterBuilder.query(queryBuilder.queryString(filter).toQuery())).hits();
//      hits = queryBuilder.queryString(query).toQuery().setAliases(new String[] {subindex}).hits();

//    if (dateFrom != null && dateTo != null) {
//      hits = queryBuilder.queryString(query).toQuery().setAliases(new String[] {subindex}).
//          setFilter(queryFilterBuilder.between("event_mod_date", dateFrom, dateTo, true, true)).hits();
//      queryBuilder.alias("").setFilter(queryFilterBuilder.query(queryBuilder.queryString("").toQuery())).queryString(query).toQuery().alias(new String[] {subindex}).
//          setFilter(queryFilterBuilder.queryString("event_mod_date")).hits();
//    } else if (dateFrom != null && dateTo == null) {
//      hits = queryBuilder.queryString(query).toQuery().setAliases(new String[] {subindex}).
//          setFilter(queryFilterBuilder.ge("event_mod_date", dateTo)).hits();
//    } else if (dateFrom == null && dateTo != null) {
//      hits = queryBuilder.queryString(query).toQuery().setAliases(new String[] {subindex}).
//          setFilter(queryFilterBuilder.le("event_mod_date", dateTo)).hits();
//    } else {
//      hits = queryBuilder.queryString(query).toQuery().setAliases(new String[] {subindex}).hits();
//    }
    log.info("found [" + hits.getLength() + "] hits for [" + query +"] query with filter " + filter + " from [" + subindex + "] subindex");
    CompassDetachedHits detachedHits = hits.detach();
    log.info("detach.ended");
    session.close();
    log.info("close.ended");

    return detachedHits;
  }

  public static CompassDetachedHits freeSearch(String query, String subindex) {
    CompassSession session = getCompass().openSession();
    session.beginTransaction();
    CompassQueryBuilder queryBuilder = session.queryBuilder();
    CompassQueryFilterBuilder queryFilterBuilder = session.queryFilterBuilder();
    CompassHits hits = null;
      hits = queryBuilder.queryString(query).toQuery().setAliases(new String[] {subindex}).hits();
    log.info("found [" + hits.getLength() + "] hits for [" + query +"] query from [" + subindex + "] subindex");
    CompassDetachedHits detachedHits = hits.detach();
    log.info("detach.ended");
    session.close();
    log.info("close.ended");

    return detachedHits;
  }



  public static void index() {
    getCompass();
    if (gps != null && gps.isRunning()) {
      log.info("indexing is started");
      gps.index();
      log.info("indexing is finished");
    }
  }

  public static void startGps() {
    getCompass();
    if (gps != null && !gps.isRunning()) {
      gps.start();
      log.info("gps is started");
    }
  }

  public static void stopGps() {
    getCompass();
    if (gps != null && gps.isRunning()) {
      gps.stop();
      log.info("gps is stopped");
    }
  }

  public static boolean isPresentCompass() {
    getCompass();
    return compass != null;
  }

  public static boolean isPresentGps() {
    getCompass();
    return gps != null;
  }


  public static boolean isRunnigGps() {
    getCompass();
    if (gps != null) {
      return gps.isRunning();
    } else {
      return false;
    }
  }

}
