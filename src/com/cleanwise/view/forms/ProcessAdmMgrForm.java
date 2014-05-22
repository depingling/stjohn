package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.value.TaskDetailViewVector;
import com.cleanwise.service.api.value.TaskRefDataVector;
import com.cleanwise.service.api.value.TaskTemplateDetailViewVector;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


public class ProcessAdmMgrForm extends ActionForm {

    ProcessData templateProcess;
    private TaskDetailViewVector taskDetails;
    public String currentTemplateProcessId = "";
    private TaskRefDataVector refs;
    private TaskTemplateDetailViewVector taskTemplateDetails;
    private int taskIndex;
    public String indexStyle[] = new String[0];
    public Object[] propertyTaskSelectBox = new Object[0];

    public ProcessData getTemplateProcess() {
        return templateProcess;
    }

    public void setTemplateProcess(ProcessData templateProcess) {
        this.templateProcess = templateProcess;
    }

    public void setTaskDetails(TaskDetailViewVector taskDetails) {
        this.taskDetails = taskDetails;
    }

    public TaskDetailViewVector getTaskDetails() {
        return taskDetails;
    }

    public String getCurrentTemplateProcessId() {
        return currentTemplateProcessId;
    }

    public void setCurrentTemplateProcessId(String currentTemplateProcessId) {
        this.currentTemplateProcessId = currentTemplateProcessId;
    }

    public void setRefs(TaskRefDataVector refs) {
        this.refs = refs;
    }

    public TaskRefDataVector getRefs() {
        return refs;
    }

    public void setTaskTemplateDetails(TaskTemplateDetailViewVector taskTemplateDetails) {
        this.taskTemplateDetails = taskTemplateDetails;
    }

    public TaskTemplateDetailViewVector getTaskTemplateDetails() {
        return taskTemplateDetails;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(int taskIndex) {
        this.taskIndex = taskIndex;
    }


    public String[] getIndexStyle() {
        return indexStyle;
    }

    public void setIndexStyle(String[] indexStyle) {
        this.indexStyle = indexStyle;
    }

    public Object[] getPropertyTaskSelectBox() {
        return propertyTaskSelectBox;
    }

    public void setPropertyTaskSelectBox(Object[] propertyTaskSelectBox) {
        this.propertyTaskSelectBox = propertyTaskSelectBox;
    }

    public void reset(ActionMapping actionMapping, ServletRequest servletRequest) {
        reset();
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        reset();
    }

    public void reset() {
        //currentTemplateProcessId="";
        // indexStyle=new String[0];
        if (propertyTaskSelectBox != null) {
            for (int i = 0; i < propertyTaskSelectBox.length; i++) {
                if (propertyTaskSelectBox[i] != null) {
                    ((SelectableObjects) propertyTaskSelectBox[i]).handleStutsFormResetRequest();
                }
            }
        }
    }

}
