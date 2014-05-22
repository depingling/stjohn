package com.cleanwise.view.logic;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.ProcessAdmMgrForm;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         09.08.2007
 * Time:         1:36:26
 *
 * @author Vlasov Evgeny, TrinitySoft, Inc.
 */

public class ProcessAdmMgrLogic {

    private static String className="ProcessAdmMgrLogic";

    public static ActionErrors createNewTemplateProcess(HttpServletRequest request, ActionForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        pForm = new ProcessAdmMgrForm();
        ((ProcessAdmMgrForm) pForm).setTemplateProcess(ProcessData.createValue());
        session.setAttribute("PROCESS_ADM_CONFIG_FORM", pForm);

        return ae;

    }

    public static ActionErrors saveNewTemplateProcess(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
        }

        Process processEjb = factory.getProcessAPI();
        if (processEjb == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Api access"));
            return ae;
        }

        ae = validate(form.getTemplateProcess());
        if (ae.size() > 0) {
            return ae;
        }

        try {
            ProcessData processData = processEjb.updateProcessData(form.getTemplateProcess());
            form.setTemplateProcess(processData);
            session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);
        } catch (RemoteException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
                return ae;
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("error.simpleGenericError", "Problem updating the process for adminportal:" +
                                " Error: " + e));

            }
        }
        return ae;
    }

    private static ActionErrors validate(ProcessData processData) {

        ActionErrors ae = new ActionErrors();

        if (processData == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "process data is null"));
            return ae;
        }

        if (!Utility.isSet(Utility.strNN(processData.getProcessName()).trim())) {
            ae.add("error", new ActionError("variable.empty.error", "process name"));
        }

        if (!Utility.isSet(Utility.strNN(processData.getProcessStatusCd()).trim())) {
            ae.add("error", new ActionError("variable.empty.error", "process status cd"));
        }

        if (!Utility.isSet(Utility.strNN(processData.getProcessTypeCd()).trim())) {
            ae.add("error", new ActionError("variable.empty.error", "process type cd"));
        }

        return ae;
    }

    public static ActionErrors init(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        pForm=new ProcessAdmMgrForm();

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
        }

        ListService lsvc = factory.getListServiceAPI();
        if (lsvc == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Api access"));
            return ae;
        }

        try {
            RefCdDataVector processStatucCds    = new RefCdDataVector();// lsvc.getRefCodesCollection("PROCESS_STATUS_CD");
            RefCdDataVector taskPropertyTypeCds = new RefCdDataVector();//lsvc.getRefCodesCollection("TASK_PROPERTY_TYPE_CD");

            RefCdData refcd1 = RefCdData.createValue();
            RefCdData refcd2 = RefCdData.createValue();
            RefCdData refcd3= RefCdData.createValue();
            RefCdData refcd4 = RefCdData.createValue();
            RefCdData refcd5 = RefCdData.createValue();
            refcd1.setValue("ACTIVE");
            processStatucCds.add(refcd1);
            refcd2.setValue("MANDATORY");
            taskPropertyTypeCds.add(refcd2);
            refcd3.setValue("INPUT");
            taskPropertyTypeCds.add(refcd3);
            refcd4.setValue("OUTPUT");
            taskPropertyTypeCds.add(refcd4);
            refcd5.setValue("OPTIONAL");
            taskPropertyTypeCds.add(refcd5);
            session.setAttribute("Process.status.cds", processStatucCds);
            session.setAttribute("Task.property.type.status.cds", taskPropertyTypeCds);
            session.setAttribute("PROCESS_ADM_CONFIG_FORM", pForm);

        } catch (Exception e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
                return ae;
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("error.simpleGenericError", "Problem init the process for adminportal:" +
                                " Error: " + e));

            }
        }
        return ae;
    }

    public static ActionErrors getDetails(HttpServletRequest request,ActionForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;
        if(Integer.parseInt(form.getCurrentTemplateProcessId())<=0){
            ae.add("error",new ActionError("error.simpleGenericError", "Invalid template process id"));
            return ae;
        }

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
        }

        Task taskEjb = factory.getTaskAPI();
        Process processEjb=factory.getProcessAPI();

        ProcessData templateProcess = processEjb.getProcess(Integer.parseInt(form.getCurrentTemplateProcessId()));
        TaskSearchCriteria criteria =new  TaskSearchCriteria();
        IdVector processIds= new IdVector();
        processIds.add(new Integer(templateProcess.getProcessId()));
        criteria.setProcessIds(processIds);
        criteria.setTaskTypeCd(RefCodeNames.TASK_TYPE_CD.TEMPLATE);
        TaskDetailViewVector taskDetails= taskEjb.getTaskDetailViewVector(criteria);
        TaskRefDataVector refs=taskEjb.getTaskRefs(Integer.parseInt(form.getCurrentTemplateProcessId()));

        TaskTemplateDetailViewVector taskTemplateDetails = new TaskTemplateDetailViewVector(templateProcess,refs, taskDetails);
        initIdxStyles(form,taskTemplateDetails.getTasks().size());
        initPropertyTaskSelectBox(form,taskTemplateDetails.getTasks());
        form.setTaskDetails(taskDetails);
        form.setTemplateProcess(templateProcess);
        form.setRefs(refs);
        form.setTaskTemplateDetails(taskTemplateDetails);
        form.getTaskTemplateDetails().printTableIndexes();

        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);

        return ae;

    }

    private static void initPropertyTaskSelectBox(ProcessAdmMgrForm form,ArrayList tasks) {
        Iterator itar =tasks.iterator();
        Object[] taskPropBoxes = new Object[tasks.size()];
        int i=0;
        while(itar.hasNext()){
            TaskTemplateDetailView templateDetail = (TaskTemplateDetailView) itar.next();
            if(templateDetail!=null) {
                SelectableObjects objects = new SelectableObjects(null,templateDetail.getTask().getTaskPropertyDV(),null);
                taskPropBoxes[i]=objects;
            } else{
                taskPropBoxes[i]=new SelectableObjects(null,new ArrayList(),null);
            }
            i++;
        }
        form.setPropertyTaskSelectBox(taskPropBoxes);
    }

    private static void initIdxStyles(ProcessAdmMgrForm form, int size) {
        if(size>0){
            String[] styles=new String[size];
            for(int i=0;i<size;i++){
                styles[i]="display:none";
            }
            form.setIndexStyle(styles);
        } else {
            form.setIndexStyle(new String[0]);
        }
    }

    public static ActionErrors initDetails(HttpServletRequest request,ActionForm pForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
        }

        Process processEjb=factory.getProcessAPI();

        addProcessTemplatesToSession(session,processEjb);
        form.setCurrentTemplateProcessId("");
        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);

        return ae;
    }

    private static void addProcessTemplatesToSession(HttpSession session,Process processEjb) throws Exception {
        ProcessDataVector processes=processEjb.getAllTemplateProcesses();
        session.setAttribute("Process.template.collection",processes);
    }


    public static ActionErrors saveDetails(HttpServletRequest request, ActionForm pForm) throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Ejb access"));
            return ae;
        }

        Process processEjb = factory.getProcessAPI();
        if (processEjb == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No Api access"));
            return ae;
        }

        form.setTaskTemplateDetails(processEjb.updateTemplateProcessDetailData(form.getTaskTemplateDetails()));
        form.setCurrentTemplateProcessId(String.valueOf(form.getTaskTemplateDetails().getProcessData().getProcessId()));

        return getDetails(request,form);

    }

    public static ActionErrors addNewTask(HttpServletRequest request, ActionForm pForm) throws Exception{
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        TaskData newTaskData = TaskData.createValue();
        newTaskData.setProcessId(form.getTemplateProcess().getProcessId());
        newTaskData.setTaskTypeCd(RefCodeNames.TASK_TYPE_CD.TEMPLATE);
        newTaskData.setTaskStatusCd(RefCodeNames.TASK_STATUS_CD.ACTIVE);
        newTaskData.setAddBy(appUser.getUser().getUserName());
        newTaskData.setModBy(appUser.getUser().getUserName());
        TaskDetailView taskDetail = new TaskDetailView(newTaskData,new TaskPropertyDataVector(), form.getTemplateProcess(), null);
        TaskTemplateDetailViewVector taskTemplateDetails = new TaskTemplateDetailViewVector(form.getTemplateProcess());
        TaskTemplateDetailView newtd = new TaskTemplateDetailView(0, taskDetail);
        ArrayList a = new ArrayList();
        a.add(new Integer(0));
        newtd.setRefTableIndexes(new Object[]{a,a});
        taskTemplateDetails.getTasks().add(null);
        taskTemplateDetails.getTasks().add(newtd);

        form.setTaskTemplateDetails(taskTemplateDetails);
        form.setIndexStyle(new String[]{"display:none"});
        addStylesforIndex(form,1);
        initPropertyTaskSelectBox(form,form.getTaskTemplateDetails().getTasks());
        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);
        return ae;
    }

    public static ActionErrors addTaskWithDownLink(HttpServletRequest request, ActionForm pForm) throws Exception{

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        form.getTaskTemplateDetails().printTableIndexes();

        int taskIndex = getTaskIndex(request);
        replaceTabIndxsVar2(form.getTaskTemplateDetails().getTasks(),taskIndex,1);
        TaskTemplateDetailView ttDetView= (TaskTemplateDetailView) form.getTaskTemplateDetails().getTasks().get(taskIndex);
        ArrayList equals = getTasksByTemplate(form.getTaskTemplateDetails().getTasks(), ttDetView);

        ArrayList newUpLink = new ArrayList();
        ArrayList newDownLink = new ArrayList();
        newDownLink.addAll((List) ttDetView.getRefTableIndexes()[0]);
        newUpLink.add(new Integer(taskIndex+1));
        Object[] newReferences = new Object[]{newDownLink, newUpLink};

        for(int i=0;i<equals.size();i++){
            ((TaskTemplateDetailView)equals.get(i)).getRefTableIndexes()[0]=new ArrayList();
            ((List)((TaskTemplateDetailView)equals.get(i)).getRefTableIndexes()[0]).add(new Integer(taskIndex));
        }

        TaskTemplateDetailViewVector newTemplateDetails = new TaskTemplateDetailViewVector(form.getTemplateProcess());
        newTemplateDetails.getTasks().addAll(form.getTaskTemplateDetails().getTasks().subList(0,taskIndex));

        TaskData newTaskData = TaskData.createValue();
        newTaskData.setProcessId(form.getTemplateProcess().getProcessId());
        newTaskData.setTaskTypeCd(RefCodeNames.TASK_TYPE_CD.TEMPLATE);
        newTaskData.setTaskStatusCd(RefCodeNames.TASK_STATUS_CD.ACTIVE);
        newTaskData.setAddBy(appUser.getUser().getUserName());
        newTaskData.setModBy(appUser.getUser().getUserName());
        TaskDetailView newtaskDetail = new TaskDetailView(newTaskData, new TaskPropertyDataVector(), form.getTemplateProcess(), null);
        TaskTemplateDetailView newTaskTemplateData = new TaskTemplateDetailView(0, newtaskDetail);
        newTaskTemplateData .setRefTableIndexes(newReferences);

        newTemplateDetails.getTasks().add(newTaskTemplateData);

        newTemplateDetails.getTasks().addAll(form.getTaskTemplateDetails().getTasks().subList(taskIndex,form.getTaskTemplateDetails().getTasks().size()));

        form.setTaskTemplateDetails(newTemplateDetails);

        addStylesforIndex(form,taskIndex);
        initPropertyTaskSelectBox(form,form.getTaskTemplateDetails().getTasks());

        form.getTaskTemplateDetails().printTableIndexes();

        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);
        return ae;
    }

    private static ArrayList getTasksByTemplate(ArrayList tasks, TaskTemplateDetailView ttDetView) {
        ArrayList equalsTasks = new ArrayList();
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i)!=null) {
                if(((TaskTemplateDetailView)tasks.get(i)).getTask().getTaskData().getTaskId() == ttDetView.getTask().getTaskData().getTaskId() &&
                        equalsRefs(((TaskTemplateDetailView)tasks.get(i)).getRefTableIndexes(),ttDetView.getRefTableIndexes())){
                    equalsTasks.add(tasks.get(i));
                }
            }
        }
        return equalsTasks;
    }

    private static void addStylesforIndex(ProcessAdmMgrForm form, int taskIndex) {
        String[] currentArray=form.getIndexStyle();
        String[] newArray=new String[currentArray.length+1];
        System.arraycopy(currentArray,0,newArray,0,taskIndex);
        newArray[taskIndex]="display:block";
        System.arraycopy(currentArray,taskIndex,newArray,taskIndex+1,currentArray.length-taskIndex);
        form.setIndexStyle(newArray);
    }

    private static void removeStylesforIndex(ProcessAdmMgrForm form, int taskIndex) {
        String[] currentArray=form.getIndexStyle();
        String[] newArray=new String[currentArray.length-1];
        System.arraycopy(currentArray,0,newArray,0,taskIndex);
        if(currentArray.length-taskIndex>0){
            System.arraycopy(currentArray,taskIndex+1,newArray,taskIndex,currentArray.length-taskIndex-1);
        }
        form.setIndexStyle(newArray);
    }

    public static ActionErrors addTaskWithUpLink(HttpServletRequest request, ActionForm pForm) throws Exception{

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        form.getTaskTemplateDetails().printTableIndexes();

        int taskIndex = getTaskIndex(request);
        replaceTabIndxsVar2(form.getTaskTemplateDetails().getTasks(),taskIndex,1);
        TaskTemplateDetailView ttDetView= (TaskTemplateDetailView) form.getTaskTemplateDetails().getTasks().get(taskIndex);
        ArrayList equals = getTasksByTemplate(form.getTaskTemplateDetails().getTasks(), ttDetView);

        ArrayList newUpLink = new ArrayList();
        ArrayList newDownLink = new ArrayList();
        newUpLink.addAll((List) ttDetView.getRefTableIndexes()[1]);
        newDownLink.add(new Integer(taskIndex));
        Object[] newReferences = new Object[]{newDownLink, newUpLink};

        for(int i=0;i<equals.size();i++){
            ((TaskTemplateDetailView)equals.get(i)).getRefTableIndexes()[1]=new ArrayList();
            ((List)((TaskTemplateDetailView)equals.get(i)).getRefTableIndexes()[1]).add(new Integer(taskIndex+1));
        }

        TaskTemplateDetailViewVector newTemplateDetails = new TaskTemplateDetailViewVector(form.getTemplateProcess());
        newTemplateDetails.getTasks().addAll(form.getTaskTemplateDetails().getTasks().subList(0,taskIndex+1));
        TaskData newTaskData = TaskData.createValue();
        newTaskData.setProcessId(form.getTemplateProcess().getProcessId());
        newTaskData.setTaskTypeCd(RefCodeNames.TASK_TYPE_CD.TEMPLATE);
        newTaskData.setTaskStatusCd(RefCodeNames.TASK_STATUS_CD.ACTIVE);
        newTaskData.setAddBy(appUser.getUser().getUserName());
        newTaskData.setModBy(appUser.getUser().getUserName());
        TaskDetailView newtaskDetail = new TaskDetailView(newTaskData, new TaskPropertyDataVector(), form.getTemplateProcess(), null);
        TaskTemplateDetailView newTaskTemplateDetail = new TaskTemplateDetailView(0, newtaskDetail);
        newTaskTemplateDetail.setRefTableIndexes(newReferences);
        newTemplateDetails.getTasks().add(newTaskTemplateDetail);

        newTemplateDetails.getTasks().addAll(form.getTaskTemplateDetails().getTasks().subList(taskIndex+1,form.getTaskTemplateDetails().getTasks().size()));

        form.setTaskTemplateDetails(newTemplateDetails);
        addStylesforIndex(form,taskIndex);
        initPropertyTaskSelectBox(form,form.getTaskTemplateDetails().getTasks());

        form.getTaskTemplateDetails().printTableIndexes();

        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);
        return ae;
    }

    private static void replaceTabIndxsVar2(List tasks, int taskIndex,int dir) {
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i)!=null) {
                List prevNext= (List) ((TaskTemplateDetailView)tasks.get(i)).getRefTableIndexes()[0];
                List nextNext= (List) ((TaskTemplateDetailView)tasks.get(i)).getRefTableIndexes()[1];
                for(int j=0;j<prevNext.size();j++){
                    Integer pIdxInt = (Integer) prevNext.get(j);
                    if(taskIndex<=pIdxInt.intValue())  {
                        prevNext.set(j,new Integer(pIdxInt.intValue()+dir));
                    }
                }
                for(int j=0;j<nextNext.size();j++){
                    Integer nIdxInt = (Integer) nextNext.get(j);
                    if(taskIndex<nIdxInt.intValue()){
                        nextNext.set(j,new Integer(nIdxInt.intValue()+dir));
                    }
                }
                ((TaskTemplateDetailView)tasks.get(i)).setRefTableIndexes(new Object[]{prevNext,nextNext});
            }
        }
    }

    public static ActionErrors addMandatoryTaskProperty(HttpServletRequest request, ActionForm pForm) throws Exception{
        return addTaskProperty(request,pForm,RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY);
    }

    public static ActionErrors addInputTaskProperty(HttpServletRequest request, ActionForm pForm) throws Exception{
        return addTaskProperty(request,pForm,RefCodeNames.TASK_PROPERTY_TYPE_CD.INPUT);
    }

    public static ActionErrors addOutputTaskProperty(HttpServletRequest request, ActionForm pForm) throws Exception{
        return addTaskProperty(request,pForm,RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);
    }

    public static ActionErrors addTaskProperty(HttpServletRequest request, ActionForm pForm,String propertyTypeCd) throws Exception{

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        int index=getTaskIndex(request);
        if(index>=0){
            TaskTemplateDetailView taskTemplDetView = (TaskTemplateDetailView) form.getTaskTemplateDetails().getTasks().get(index);
            TaskPropertyData prop = TaskPropertyData.createValue();
            prop.setPropertyTypeCd(propertyTypeCd);
            taskTemplDetView.getTask().getTaskPropertyDV().add(prop);
        }
        initPropertyTaskSelectBox(form,form.getTaskTemplateDetails().getTasks());
        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);
        return ae;
    }

    private static int getTaskIndex(HttpServletRequest request) {
        String taskIdx = request.getParameter("taskIdx");
        try {
            return Integer.parseInt(taskIdx);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ActionErrors deleteProps(HttpServletRequest request, ActionForm pForm) {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        form.getTaskTemplateDetails().printTableIndexes();
        Object[] propSelecBox = form.getPropertyTaskSelectBox();
        int index=getTaskIndex(request);

        if(index>=0){
            int length = propSelecBox.length;
            TaskTemplateDetailView taskTemplDetView = (TaskTemplateDetailView) form.getTaskTemplateDetails().getTasks().get(index);
            for(int i=0;i<length;i++){
                SelectableObjects sobjs = ((SelectableObjects) propSelecBox[index]);
                Iterator it=sobjs.getNewlySelected().iterator();
                while(it.hasNext()){
                    taskTemplDetView.getTask().getTaskPropertyDV().remove(it.next());
                }
            }
        }
        initPropertyTaskSelectBox(form,form.getTaskTemplateDetails().getTasks());
        form.getTaskTemplateDetails().printTableIndexes();
        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);
        return ae;
    }

    public static ActionErrors deleteTask(HttpServletRequest request, ActionForm pForm) {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        if (!(pForm instanceof ProcessAdmMgrForm)) {
            ae.add("error",
                    new ActionError("error.simpleGenericError", "createNewTemplateProcess  not supporting form class:  " + pForm.getClass()));
            return ae;
        }

        ProcessAdmMgrForm form = (ProcessAdmMgrForm) pForm;

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user info"));
            return ae;
        }

        int index=getTaskIndex(request);
        form.getTaskTemplateDetails().printTableIndexes();
        if(index>=0 ){
            TaskTemplateDetailView taskTemplateDetail = (TaskTemplateDetailView)form.getTaskTemplateDetails().getTasks().remove(index);

            List prevIndx= (List) taskTemplateDetail.getRefTableIndexes()[0];
            List nextIndx= (List) taskTemplateDetail.getRefTableIndexes()[1];
            if(prevIndx.size()>0){
                replaceTabIndxsVar1(index,prevIndx,form.getTaskTemplateDetails().getTasks(),0);
            }
            if(nextIndx.size()>0){
                replaceTabIndxsVar1(index,nextIndx,form.getTaskTemplateDetails().getTasks(),1);
            }

        }

        form.getTaskTemplateDetails().printTableIndexes();
        replaceTabIndxsVar2(form.getTaskTemplateDetails().getTasks(),index,-1);
        form.getTaskTemplateDetails().printTableIndexes();
        removeStylesforIndex(form,index);
        initPropertyTaskSelectBox(form,form.getTaskTemplateDetails().getTasks());

        session.setAttribute("PROCESS_ADM_CONFIG_FORM", form);
        return ae;
    }

    private static void replaceTabIndxsVar1(int index, List prevIndx,List tasks,int dir) {
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i)!=null){
                List current= (List) ((TaskTemplateDetailView)tasks.get(i)).getRefTableIndexes()[dir];
                replaceIndex(index,prevIndx,current);
            }
        }
    }

    private static void replaceIndex(int index, List replIndx, List prevCurrent) {

        HashSet idxSet=new HashSet();
        if(!prevCurrent.isEmpty()){
            Iterator itar = prevCurrent.iterator();
            while(itar.hasNext()){
                Integer idx = (Integer) itar.next();
                if(idx.intValue()==index){
                    Iterator itar2=replIndx.iterator();
                    while(itar2.hasNext()){
                        Integer replIdx = (Integer) itar2.next();
                        if(prevCurrent.size()>1 && replIdx.intValue()==0){
                        }else{
                            if(!idxSet.contains(replIdx)){
                                idxSet.add(replIdx);
                            }
                        }
                    }
                } else{
                    if(!idxSet.contains(idx)){
                        idxSet.add(idx);
                    }
                }
            }
        }
        prevCurrent.clear();
        prevCurrent.addAll(idxSet);
    }

    private static boolean equalsRefs(Object[] refTableIndexes, Object[] refTableIndexes1) {

        HashSet ref00PrevSet=new HashSet();
        HashSet ref01PrevSet=new HashSet();
        HashSet ref10PrevSet=new HashSet();
        HashSet ref11PrevSet=new HashSet();

        for(int i=0;i<refTableIndexes.length;i++){
            if(!ref00PrevSet.contains(refTableIndexes[0])){
                ref00PrevSet.add(refTableIndexes[0]);
            }
            if(!ref01PrevSet.contains(refTableIndexes[1])){
                ref01PrevSet.add(refTableIndexes[1]);
            }
        }

        for(int i=0;i<refTableIndexes1.length;i++){
            if(!ref10PrevSet.contains(refTableIndexes1[0])){
                ref10PrevSet.add(refTableIndexes1[0]);
            }
            if(!ref11PrevSet.contains(refTableIndexes1[1])){
                ref11PrevSet.add(refTableIndexes1[1]);
            }
        }

        boolean prevFl=false;
        boolean nextFl=false;

        if(!ref00PrevSet.isEmpty()){
            ref00PrevSet.removeAll(ref10PrevSet);
            prevFl=ref00PrevSet.isEmpty();
        } else if (!ref10PrevSet.isEmpty()){
            ref01PrevSet.removeAll(ref00PrevSet);
            prevFl=ref01PrevSet.isEmpty();
        }

        if(!ref01PrevSet.isEmpty()){
            ref01PrevSet.removeAll(ref11PrevSet);
            nextFl=ref01PrevSet.isEmpty();
        } else if (!ref11PrevSet.isEmpty()){
            ref11PrevSet.removeAll(ref01PrevSet);
            nextFl=ref11PrevSet.isEmpty();
        }

        return prevFl&&nextFl;
    }
}
