package com.cleanwise.service.apps.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cleanwise.service.api.session.ProcessHome;
import com.cleanwise.service.api.session.Task;
import com.cleanwise.service.api.session.TaskHome;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.value.TaskData;
import com.cleanwise.service.api.value.TaskDetailView;
import com.cleanwise.service.api.value.TaskPropertyData;
import com.cleanwise.service.api.value.TaskPropertyDataVector;
import com.cleanwise.service.api.value.TaskRefData;
import com.cleanwise.service.api.value.TaskRefDataVector;
import com.cleanwise.service.api.value.TaskTemplateDetailView;
import com.cleanwise.service.api.value.TaskTemplateDetailViewVector;
/**
 *
 * @author Veronika Denega
 */
public class ProcessLoader {
  private static final Logger log = Logger.getLogger(ProcessLoader.class);

  private String PROCESS = "process";
  private String TASK = "task";
  private String REF = "ref";
  private String NAME = "name";
  private String TYPE = "type";
  private String PRIORITY = "priority";
  private String STATUS = "status";
  private String METHOD = "method";
  private String VAR_CLASS = "var_class";
  private String VAR_TYPE = "var_type";
  private String VAR_NAME = "var_name";
  private String POSITION = "position";
  private String TASK1 = "task1";
  private String TASK2 = "task2";

  private String PROCESS_LOADER = "ProcessLoader";

  public ProcessLoader() {
  }

  public int uploadProcesses(String fileName) throws Exception {
    // Check for a properties file command option.
    String propFileName = System.getProperty("conf");
    Properties props = new Properties();
    log.info("Properties: " + propFileName);
    props.load(new FileInputStream(propFileName));
    InitialContext jndiContext = new InitialContext(props);

    Object ref = jndiContext.lookup(JNDINames.PROCESS_EJBHOME);

    ProcessHome pHome = (ProcessHome) PortableRemoteObject.narrow(ref, ProcessHome.class);
    com.cleanwise.service.api.session.Process processBean = pHome.create();

    ref = jndiContext.lookup(JNDINames.TASK_EJBHOME);
    TaskHome tHome = (TaskHome) PortableRemoteObject.narrow(ref, TaskHome.class);
    Task taskBean = tHome.create();

    File processes = new File(fileName);
    if (!processes.exists() || !processes.isFile()) {
      log.info("Error. Can't find file: " + fileName);
      return 2;
    }

    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      FileInputStream fileInputStream1 = new FileInputStream(fileName);
      Document document = documentBuilder.parse(fileInputStream1);
      NodeList nodeList = document.getElementsByTagName(PROCESS);
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node1 = nodeList.item(i);
        NamedNodeMap attr = node1.getAttributes();

        ProcessData processData = parseProcessAttributes(attr);
        TaskTemplateDetailViewVector taskTemplateDetails = new TaskTemplateDetailViewVector(processData);

        NodeList nodes = node1.getChildNodes();

        ArrayList tasks = new ArrayList();

        for (int j = 0; j < nodes.getLength(); j++) {
          Node el = nodes.item(j);
          if (el.getNodeType() == Node.ELEMENT_NODE) {

            String name = el.getNodeName();
            if (name.equals(TASK)) {

              TaskTemplateDetailView taskTemplDetailView = parseTaskElement(el, processData);
              tasks.add(taskTemplDetailView);
            }
          }
        }

        // add tasks to process
        taskTemplateDetails.setTasks(tasks);

        // save process
        taskTemplateDetails = processBean.updateTemplateProcessDetailData(taskTemplateDetails);

        // get and save refs
        boolean hasRefs = false;
        TaskRefDataVector taskRefs = new TaskRefDataVector();
        for (int j = 0; j < nodes.getLength(); j++) {
          Node el = nodes.item(j);
          if (el.getNodeType() == Node.ELEMENT_NODE) {
            String name = el.getNodeName();
            if (name.equals(REF)) {
              NamedNodeMap el_attr = el.getAttributes();
              String task1Name = getAttributeValue(el_attr, TASK1, null);
              String task2Name = getAttributeValue(el_attr, TASK2, null);
              TaskTemplateDetailView task1 = findTask(task1Name, taskTemplateDetails);
              TaskTemplateDetailView task2 = findTask(task2Name, taskTemplateDetails);
              if (task1 == null && task2 == null) {
                continue;
              }


              TaskRefData refData = TaskRefData.createValue();
              refData.setProcessId(taskTemplateDetails.getProcessData().getProcessId());
              if (task1 != null) {
                refData.setTaskId1(task1.getTask().getTaskData().getTaskId());
              }
              if (task2 != null) {
                refData.setTaskId2(task2.getTask().getTaskData().getTaskId());
              }
              hasRefs = true;
              refData.setTaskRefStatusCd(getAttributeValue(el_attr, STATUS, null));
              refData.setAddBy(PROCESS_LOADER);
              refData.setModBy(PROCESS_LOADER);
              taskRefs.add(refData);
            }
          }
        }
        if (hasRefs) {
          taskRefs = taskBean.updateTaskRefs(taskTemplateDetails.getProcessData().getProcessId(), taskRefs);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  private TaskTemplateDetailView findTask(String taskName, TaskTemplateDetailViewVector tasks) {
    if (taskName != null && tasks != null) {
      Iterator it = tasks.getTasks().iterator();
      while (it.hasNext()) {
        TaskTemplateDetailView taskView = (TaskTemplateDetailView) it.next();
        if (taskView != null) {
          if (taskView.getTask().getTaskData().getTaskName().equals(taskName)) {
            return taskView;
          }
        }
      }
    }
    return null;
  }

  private ProcessData parseProcessAttributes(NamedNodeMap attr) {
    ProcessData result = ProcessData.createValue();
    result.setProcessName(getAttributeValue(attr, NAME, null));
    result.setProcessTypeCd(getAttributeValue(attr, TYPE, null));
    result.setProcessStatusCd(getAttributeValue(attr, STATUS, null));
    result.setProcessPriority(getAttributeIntValue(attr, PRIORITY, 50));
    result.setAddBy(PROCESS_LOADER);
    result.setModBy(PROCESS_LOADER);
    return result;
  }

  private TaskTemplateDetailView parseTaskElement(Node element, ProcessData processData) {
    NamedNodeMap el_attr = element.getAttributes();
    TaskData taskData = parseTaskAttributes(el_attr);
    TaskDetailView taskDetailV = TaskDetailView.createValue();
    taskDetailV.setProcessData(processData);
    taskDetailV.setTaskData(taskData);

    TaskPropertyDataVector taskProps = parseTaskPropertiesAttributes(element);
    taskDetailV.setTaskPropertyDV(taskProps);

    return new TaskTemplateDetailView(0, taskDetailV);
  }

  private TaskData parseTaskAttributes(NamedNodeMap attr) {
    TaskData result = TaskData.createValue();
    result.setMethod(getAttributeValue(attr, METHOD, null));
    result.setTaskName(getAttributeValue(attr, NAME, null));
    result.setTaskStatusCd(getAttributeValue(attr, STATUS, null));
    result.setTaskTypeCd(getAttributeValue(attr, TYPE, null));
    result.setVarClass(getAttributeValue(attr, VAR_CLASS, null));
    result.setAddBy(PROCESS_LOADER);
    result.setModBy(PROCESS_LOADER);
    return result;
  }

  private TaskPropertyDataVector parseTaskPropertiesAttributes(Node node) {
    TaskPropertyDataVector result = new TaskPropertyDataVector();
    NodeList elements = node.getChildNodes();
    for (int j = 0; j < elements.getLength(); j++) {
      Node el = elements.item(j);
      if (el.getNodeType() == Node.ELEMENT_NODE) {
        NamedNodeMap attr = el.getAttributes();
        TaskPropertyData taskProp = TaskPropertyData.createValue();
        taskProp.setVarName(getAttributeValue(attr, VAR_NAME, null));
        taskProp.setVarType(getAttributeValue(attr, VAR_TYPE, null));
        taskProp.setPosition(getAttributeIntValue(attr, POSITION, 1));
        taskProp.setPropertyTypeCd(getAttributeValue(attr, TYPE, null));
        taskProp.setTaskPropertyStatusCd(getAttributeValue(attr, STATUS, null));
        taskProp.setAddBy(PROCESS_LOADER);
        taskProp.setModBy(PROCESS_LOADER);
        result.add(taskProp);
      }
    }
    return result;
  }


  private String getAttributeValue(NamedNodeMap attrMap, String attrName, String defaultValue) {
    Node attr = attrMap.getNamedItem(attrName);
    if (attr != null) {
      return attr.getNodeValue();
    } else {
      return defaultValue;
    }
  }

  private int getAttributeIntValue(NamedNodeMap attrMap, String attrName, int defaultValue) {
    Node attr = attrMap.getNamedItem(attrName);
    if (attr != null) {
      try {
        String val = attr.getNodeValue();
        return Integer.parseInt(val, 10);
      } catch (Exception e) {
        return defaultValue;
      }
    } else {
      return defaultValue;
    }
  }


  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    log.info("Cleanwise Process Loader.");
    String fileName = args[0];
    if ((args.length < 1) || fileName.endsWith("${tfile}")) {
        log.info("Error. File name not found");
        log.info("Use  -Dtfile=<templates.xml>");
        return;
    }
    log.info("templates file: " + fileName);

    ProcessLoader ml = new ProcessLoader();
    ml.uploadProcesses(fileName);
    return;
  }

}
