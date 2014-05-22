package com.cleanwise.service.api.value;


public class TaskSearchCriteria implements java.io.Serializable{

   private String[]   taskNames;
   private String[]   processNames;
   private IdVector processIds;
   private IdVector taskIds;
   private String[] taskStatusCds;
   private String[] processStatusCds;
   private String   processTypeCd;
   private String   taskTypeCd;


    public IdVector getProcessIds() {
        return processIds;
    }

    public void setProcessIds(IdVector processIds) {
        this.processIds = processIds;
    }


    public IdVector getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(IdVector taskIds) {
        this.taskIds = taskIds;
    }

    public String[] getTaskStatusCds() {
        return taskStatusCds;
    }

    public void setTaskStatusCds(String[] taskStatusCds) {
        this.taskStatusCds = taskStatusCds;
    }

    public String[] getProcessStatusCds() {
        return processStatusCds;
    }

    public void setProcessStatusCds(String[] processStatusCds) {
        this.processStatusCds = processStatusCds;
    }


    public String getProcessTypeCd() {
        return processTypeCd;
    }

    public void setProcessTypeCd(String processTypeCd) {
        this.processTypeCd = processTypeCd;
    }

    public String getTaskTypeCd() {
        return taskTypeCd;
    }

    public void setTaskTypeCd(String taskTypeCd) {
        this.taskTypeCd = taskTypeCd;
    }


    public String[] getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(String[] taskNames) {
        this.taskNames = taskNames;
    }

    public String[] getProcessNames() {
        return processNames;
    }

    public void setProcessNames(String[] processNames) {
        this.processNames = processNames;
    }
}
