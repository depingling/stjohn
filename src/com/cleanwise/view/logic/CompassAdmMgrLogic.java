package com.cleanwise.view.logic;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.value.IdVector;
// uncomment this
import com.cleanwise.compass.*;
import org.compass.core.*;


public class CompassAdmMgrLogic {
  private static final String INDEX_THREAD_NAME = "Compass Manual Indexing";

  public static ActionErrors startGps(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors errors = new ActionErrors();
    try {
// uncomment this
      CompassSearchHelper.startGps();
      System.setProperty("compass.mirroring","disable");
    }
    catch (Exception e) {
      e.printStackTrace();
      errors.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return errors;
  }

  public static ActionErrors stopGps(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors errors = new ActionErrors();
    try {
// uncomment this
      CompassSearchHelper.stopGps();
      System.setProperty("compass.mirroring","disable");
    }
    catch (Exception e) {
      e.printStackTrace();
      errors.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return errors;
  }

  public static ActionErrors enableMirroring(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors errors = new ActionErrors();
    CompassAdmMgrForm compassForm = (CompassAdmMgrForm) form;
    try {
      System.setProperty("compass.mirroring","enable");
      compassForm.setMirroring(true);
    }
    catch (Exception e) {
      e.printStackTrace();
      errors.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return errors;
  }

  public static ActionErrors disableMirroring(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors errors = new ActionErrors();
    CompassAdmMgrForm compassForm = (CompassAdmMgrForm) form;
    try {
      System.setProperty("compass.mirroring","disable");
      compassForm.setMirroring(false);
    }
    catch (Exception e) {
      e.printStackTrace();
      errors.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return errors;
  }

  public static ActionErrors startIndexing(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors errors = new ActionErrors();
    try {
//      CompassSearchHelper.stopMirror();
      IndexingThread thread = new IndexingThread(INDEX_THREAD_NAME);
      thread.start();
    }
    catch (Exception e) {
      e.printStackTrace();
      errors.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return errors;
  }

  public static ActionErrors stopIndexing(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors errors = new ActionErrors();
    try {
      System.setProperty("compass.index.process", "stop");
    }
    catch (Exception e) {
      e.printStackTrace();
      errors.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return errors;
  }


  public static ActionErrors init(HttpServletRequest request, ActionForm form) throws
      Exception {
    ActionErrors errors = new ActionErrors();
    CompassAdmMgrForm compassForm = (CompassAdmMgrForm) form;
    compassForm.setRunningGps(false);
    ArrayList threads = new ArrayList();

    if ("enable".equals(System.getProperty("compass.mirroring"))) {
      compassForm.setMirroring(true);
    }

// uncomment this block

      if (CompassSearchHelper.isPresentCompass()) {
        compassForm.setStatus("Started");
        if (CompassSearchHelper.isPresentGps()) {
          compassForm.setPresentGps(true);
        }
        if (CompassSearchHelper.isRunnigGps()) {
          compassForm.setRunningGps(true);
        }
        compassForm.setManualIndexing(false);
        ThreadGroup top = Thread.currentThread().getThreadGroup();
        while (true) {
          if (top.getParent() != null)
            top = top.getParent();
          else
            break;
        }
        Thread[] theThreads = new Thread[top.activeCount()];
        top.enumerate(theThreads);
        for (int i = 0; i < theThreads.length; i++) {
          if (theThreads[i].getName() != null && theThreads[i].getName().startsWith("Compass")) {
            threads.add(theThreads[i].getName());
            if (INDEX_THREAD_NAME.equals(theThreads[i].getName())) {
              compassForm.setManualIndexing(true);
              compassForm.setPercentage(System.getProperty("compass.index.percentage","0.0"));
            } else {
              compassForm.setPercentage(System.getProperty("compass.mirroring.percentage","0.0"));
            }
          }
        }
        compassForm.setThreads(threads);
      } else {
        compassForm.setStatus("not avalable");
      }

//end
    return errors;
  }

  public static ActionErrors search(HttpServletRequest request,
                                           ActionForm form) throws Exception {
    ActionErrors errors = new ActionErrors();
    CompassAdmMgrForm compassForm = (CompassAdmMgrForm) form;
    try {
// uncomment this block

    IdVector ids = new IdVector();
    if (compassForm.getQueryAliase() != null &&
        !"".equals(compassForm.getQueryAliase()) &&
        compassForm.getQueryStr() != null &&
        !"".equals(compassForm.getQueryStr()) &&
        compassForm.getQueryId() != null &&
        !"".equals(compassForm.getQueryId())) {
      CompassDetachedHits hits = CompassSearchHelper.freeSearch(compassForm.getQueryStr(),
                                                                compassForm.getQueryAliase());
      HashMap map = new HashMap();
      if (hits.getLength() != 0) {
        for (int i = 0; i < hits.getLength(); i++) {
          Integer id = new Integer(hits.resource(i).getValue(compassForm.
              getQueryId()));
          if (map.get(id) == null) {
            ids.add(id);
            map.put(id, id);
          }
        }
        if (ids.size() > 1000) {
          errors.add("Checking parameters", new ActionMessage("error.simpleGenericError", "Results size too large"));
          return errors;
        }
      }
    } else {
      errors.add("Checking parameters", new ActionMessage("error.simpleGenericError", "Query parameters can not be emtpy"));
    }
    compassForm.setResultIds(ids);

//end
  } catch (Exception e) {
      e.printStackTrace();
      errors.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return errors;
  }


  private static class IndexingThread extends Thread {
    public IndexingThread(String name) {
      super.setName(name);
    }
    public void run() {
      try {
        System.setProperty("compass.index.percentage","0.0");
// uncomment this
        CompassSearchHelper.index();
        System.setProperty("compass.index.percentage","0.0");
        this.interrupt();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
