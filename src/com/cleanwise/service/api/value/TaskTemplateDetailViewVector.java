package com.cleanwise.service.api.value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


public class TaskTemplateDetailViewVector implements java.io.Serializable{

	private static final Logger log = Logger.getLogger(TaskTemplateDetailViewVector.class);

    private ProcessData processData;
    private TaskRefDataVector taskRefs;
    private ArrayList tasks;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public TaskTemplateDetailViewVector(ProcessData processData) {
        this.processData = processData;
        taskRefs = new TaskRefDataVector();
        this.tasks = new ArrayList();
    }

    public TaskTemplateDetailViewVector(ProcessData templateProcess, TaskRefDataVector taskRefs, TaskDetailViewVector allTasks) {
        this.processData = templateProcess;
        this.taskRefs = taskRefs;
        this.tasks = new ArrayList();
        TaskDetailViewVector sortedTask = new TaskDetailViewVector();
        HashMap groupRefs = new HashMap();
        HashMap groupRefs2 = new HashMap();
        groupByLevel(taskRefs,0,groupRefs);
        groupByLevelT2(taskRefs,0,groupRefs2);

        getSortedGroup(getHashMap(allTasks),groupRefs,sortedTask,new Integer(0));
        tasks.add(null);

        for (int i = 0, index = 0; i < sortedTask.size(); i++, index++) {
            TaskDetailView task = (TaskDetailView) sortedTask.get(i);
            tasks.add(new TaskTemplateDetailView(task.getTaskData().getTaskId(), task));

        }

        //tasks.add(null);

        for (int i = 0, index = 0; i < tasks.size(); i++, index++) {
            TaskTemplateDetailView dw = ((TaskTemplateDetailView) tasks.get(i));
            if (dw != null) {
                dw.setRefTableIndexes(getRefTable(dw.getTaskId(),groupRefs,groupRefs2));
            }
        }

    }

    private HashMap getHashMap(TaskDetailViewVector allTasks) {
        HashMap map =new HashMap();
        for (int j = 0; j < allTasks.size(); j++) {
            TaskDetailView taskView = (TaskDetailView) allTasks.get(j);
            if (taskView != null) {
                map.put(new Integer(taskView.getTaskData().getTaskId()),taskView);
            }
        }
        return map;
    }

    public TaskDetailViewVector getNextTask(Integer taskId,HashMap taskMap,HashMap refMap){
        List list= (List) refMap.get(taskId);
        TaskDetailViewVector nextTasks=new TaskDetailViewVector();
        if(list!=null){
            Iterator itar=list.iterator();
            while(itar.hasNext()){
                Object id = ((PairView) itar.next()).getObject2();
                if(id!=null&&((Integer)id).intValue()>0){
                    TaskDetailView taskDetView= (TaskDetailView) taskMap.get(id);
                    nextTasks.add(taskDetView);
                }
            }
        }
        return nextTasks;
    }

    private void getSortedGroup(HashMap taskMap, HashMap groupRefs, TaskDetailViewVector list, Object key) {

        TaskDetailViewVector tasksDetView = getNextTask((Integer) key, taskMap, groupRefs);
        list.addAll(tasksDetView);
        Iterator itar2=tasksDetView.iterator();
        while(itar2.hasNext()){
            TaskDetailView detView = (TaskDetailView) itar2.next();
            getSortedGroup(taskMap,groupRefs,list,new Integer(detView.getTaskData().getTaskId()));
        }
    }


    private TaskRefData getRefById(Integer refId) {
        for (int j = 0; j < taskRefs.size(); j++) {
            TaskRefData taskRefData = (TaskRefData) taskRefs.get(j);
            if (refId.intValue() == taskRefData.getTaskId2()) {
                return  taskRefData;
            }
        }
        return null;
    }


    private Object[] getRefTable(int taskIdd, HashMap groupRefs,HashMap groupRefs2 ) {

        ArrayList ref1Array = new ArrayList();
        ArrayList ref2Array = new ArrayList();

        IdVector ids= new IdVector();
        List list= (List) groupRefs.get(new Integer(taskIdd));
        for(int i=0;list!=null&&list.size()>i;i++){
            PairView pairView = (PairView) list.get(i);
            ids.add(pairView.getObject2()!=null?pairView.getObject2():new Integer(0));
        }
        IdVector ids2= new IdVector();
        List list2= (List) groupRefs2.get(new Integer(taskIdd));
        for(int i=0;list2!=null&&list2.size()>i;i++){
            PairView pairView = (PairView) list2.get(i);
            ids2.add(pairView.getObject1()!=null?pairView.getObject1():new Integer(0));
        }

        if(list==null||list.size()==0){
            ids.add(new Integer(0));
        }
        if(list2==null||list2.size()==0){
            ids2.add(new Integer(0));
        }

        for(int x=0;x<ids.size();x++){
            Integer taskId =((Integer)ids.get(x));
            for (int j = 0; j < tasks.size(); j++) {
                TaskTemplateDetailView taskRefData = (TaskTemplateDetailView) tasks.get(j);
                if(taskId!=null&&taskId.intValue()==0){
                    if(!ref1Array.contains(new Integer(0))){
                        ref1Array.add(new Integer(0));
                    }
                } else if(taskId!=null&&taskRefData!=null){
                    if (taskId.intValue() == taskRefData.getTask().getTaskData().getTaskId()) {
                        if(!ref1Array.contains(new Integer(j))){ref1Array.add(new Integer(j));}
                    }
                }
            }
        }

        for(int x=0;x<ids2.size();x++){
            Integer taskId =((Integer)ids2.get(x));
            for (int j = 0; j < tasks.size(); j++) {
                TaskTemplateDetailView taskRefData = (TaskTemplateDetailView) tasks.get(j);
                if(taskId!=null&&taskId.intValue()==0){
                    if(!ref2Array.contains(new Integer(0))){
                        ref2Array.add(new Integer(0));
                    }
                } else if(taskId!=null&&taskRefData!=null){
                    if (taskId.intValue() == taskRefData.getTask().getTaskData().getTaskId()) {
                        if(!ref2Array.contains(new Integer(j))){ref2Array.add(new Integer(j));}
                    }
                }
            }
        }


        return new Object[]{ref2Array, ref1Array};
    }

    private int getIndex(int taskId) {
        if (taskId <= 0) return 0;
        for (int j = 0; j < tasks.size(); j++) {
            TaskTemplateDetailView taskView = (TaskTemplateDetailView) tasks.get(j);
            if(taskView != null &&taskView.getTask()!=null) {
                if (taskId == taskView.getTask().getTaskData().getTaskId()) {
                    return j;
                }
            }
        }
        return -1;
    }


    private TaskDetailView getTaskDetailView(TaskDetailViewVector allTasks, int taskId) {
        for (int j = 0; j < allTasks.size(); j++) {
            TaskDetailView taskView = (TaskDetailView) allTasks.get(j);
            if (taskView != null) {
                if (taskId == taskView.getTaskData().getTaskId()) {
                    return taskView;
                }
            }
        }
        return null;
    }

    // only for first  reference power
    //example   0-1 1-2 4-5 3-4 5-0  result 0-1 1-2 3-4 4-5 5-0
    private void sortByRef(TaskRefDataVector taskRefs, TaskRefDataVector sorted, int taskId) {
        TaskRefDataVector temp = new TaskRefDataVector();
        for (int i = 0; i < taskRefs.size(); i++) {
            TaskRefData taskRef = (TaskRefData) taskRefs.get(i);
            if (taskRef.getTaskId1() == taskId) {
                sorted.add(taskRef);
                temp.add(taskRef);
            }
        }
        for (int j = 0; j < temp.size(); j++) {
            int tId2 = ((TaskRefData) temp.get(j)).getTaskId2();
            if (tId2 > 0) {
                sortByRef(taskRefs, sorted, tId2);
            }
        }
    }
    private boolean existZeroTaskLink(List tabIndxs) {
        if(!tabIndxs.isEmpty()){
            Iterator itar = tabIndxs.iterator();
            while(itar.hasNext()){
                if(((Integer)itar.next()).intValue()<=0){
                    return true;
                }
            }
        }
        return false;
    }

    public void  groupByLevel(TaskRefDataVector taskRefs, int taskId,HashMap pow){
        ArrayList lineIds= new ArrayList();
        for (int i = 0; i < taskRefs.size(); i++) {
            TaskRefData taskRef = (TaskRefData) taskRefs.get(i);
            if (taskRef.getTaskId1() == taskId) {
                lineIds.add(new PairView(new Integer(taskRef.getTaskId1()),new Integer(taskRef.getTaskId2())));
            }
        }
        if(lineIds.isEmpty()){
            lineIds.add(new PairView(new Integer(0),null));
            pow.put(new Integer(taskId),lineIds);
        }else{
            pow.put(new Integer(taskId),lineIds);}
        for (int j = 0; j < lineIds.size(); j++) {
            Integer tId2 = ((Integer)((PairView) lineIds.get(j)).getObject2());
            if (tId2!=null&&tId2.intValue() > 0) {
                groupByLevel(taskRefs,tId2.intValue(),pow);
            }
        }
    }

    public void  groupByLevelT2(TaskRefDataVector taskRefs, int taskId,HashMap pow){
        ArrayList lineIds= new ArrayList();
        for (int i = 0; i < taskRefs.size(); i++) {
            TaskRefData taskRef = (TaskRefData) taskRefs.get(i);
            if (taskRef.getTaskId2() == taskId) {
                lineIds.add(new PairView(new Integer(taskRef.getTaskId1()),new Integer(taskRef.getTaskId2())));
            }
        }
        if(lineIds.isEmpty()){
            lineIds.add(new PairView(new Integer(0),null));
            pow.put(new Integer(taskId),lineIds);
        }else{
            pow.put(new Integer(taskId),lineIds);}
        for (int j = 0; j < lineIds.size(); j++) {
            Integer tId1 = ((Integer)((PairView) lineIds.get(j)).getObject1());
            if (tId1!=null&&tId1.intValue() > 0) {
                groupByLevelT2(taskRefs,tId1.intValue(),pow);
            }
        }
    }



    public ProcessData getProcessData() {
        return processData;
    }


    public void setProcessData(ProcessData processData) {
        this.processData = processData;
    }

    public TaskRefDataVector getRefs() {
        PairViewVector refs = new PairViewVector();

        if (tasks.size() > 0) {
            for (int i = refs.size(); i < tasks.size(); i++) {
                TaskTemplateDetailView taskTemplDetail = ((TaskTemplateDetailView) tasks.get(i));
                if(taskTemplDetail!=null){
                  if (taskTemplDetail.getRefTableIndexes() != null) {
                    List ref0Idxs = (List) taskTemplDetail.getRefTableIndexes()[0];
                    List ref1Idxs = (List) taskTemplDetail.getRefTableIndexes()[1];
                    Iterator it = ref1Idxs.iterator();
                    while (it.hasNext()) {
                      int idx = ((Integer) it.next()).intValue();
                      int taskId = ((TaskTemplateDetailView) tasks.get(i)).getTask().getTaskData().getTaskId();
                      int taskId2 = 0;
                      TaskTemplateDetailView tdw = ((TaskTemplateDetailView) tasks.get(idx));
                      if (tdw != null) {
                        taskId2 = tdw.getTask().getTaskData().getTaskId();
                      }
                      PairView taskRefPair = new PairView(new Integer(taskId), new Integer(taskId2));
                      refs.add(taskRefPair);
                    }

                    it = ref0Idxs.iterator();
                    while (it.hasNext()) {
                      int idx = ((Integer) it.next()).intValue();
                      int taskId = ((TaskTemplateDetailView) tasks.get(i)).getTask().getTaskData().getTaskId();
                      int taskId2 = 0;
                      TaskTemplateDetailView tdw = ((TaskTemplateDetailView) tasks.get(idx));
                      if (tdw != null) {
                        taskId2 = tdw.getTask().getTaskData().getTaskId();
                      }
                      PairView taskRefPair = new PairView(new Integer(taskId2), new Integer(taskId));
                      if (!existConj(refs, taskRefPair)) {
                        refs.add(taskRefPair);
                      }
                    }
                  }
                }
            }
        }
        return preparedRefs(refs);

    }

    private boolean existConj(PairViewVector refs, PairView taskRefPair) {
        Iterator itar=refs.iterator();
        while(itar.hasNext()){
            PairView pair = (PairView) itar.next();
            if(((Integer)pair.getObject1()).intValue()==((Integer)taskRefPair.getObject1()).intValue() &&
                    ((Integer)pair.getObject2()).intValue()==((Integer)taskRefPair.getObject2()).intValue()){
                return true;
            }
        }
        return false;
    }

    private TaskRefDataVector preparedRefs(PairViewVector refs) {
        TaskRefDataVector res= new TaskRefDataVector();
        Iterator itar=refs.iterator();
        while(itar.hasNext()){
            res.add(getRef((PairView) itar.next()));
        }
        return res;
    }


    private ArrayList getBeginTasks() {
        ArrayList list =new ArrayList();
        for (int i = 0; i < tasks.size(); i++) {
            TaskTemplateDetailView taskTemplDetail = ((TaskTemplateDetailView) tasks.get(i));
            if(taskTemplDetail!=null){
                if(existZeroTaskLink((List) taskTemplDetail.getRefTableIndexes()[0])){
                    list.add(getRef(new PairView(new Integer(0),new Integer(taskTemplDetail.getTask().getTaskData().getTaskId()))));}
            }
        }
        return list;
    }

    private TaskRefData getRef(PairView taskRefPair) {
        Iterator it = this.taskRefs.iterator();
        while (it.hasNext()) {
            TaskRefData refData = (TaskRefData) it.next();
            if (refData.getTaskId1() == ((Integer) taskRefPair.getObject1()).intValue()
                    && refData.getTaskId2() == ((Integer) taskRefPair.getObject2()).intValue()) {
                return refData;
            }
        }
        return createNewRef(taskRefPair);
    }

    private TaskRefData createNewRef(PairView taskRefPair) {
        TaskRefData refData = TaskRefData.createValue();
        refData.setProcessId(getProcessData().getProcessId());
        refData.setTaskId1(((Integer) taskRefPair.getObject1()).intValue());
        refData.setTaskId2(((Integer) taskRefPair.getObject2()).intValue());
        return refData;
    }

    public void setTasRefs(TaskRefDataVector taskRefs) {
        this.taskRefs = taskRefs;
    }


    public ArrayList getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList tasks) {
        this.tasks = tasks;
    }


    public void printTableIndexes() {
        StringBuffer str =new StringBuffer();
        Iterator it = this.tasks.iterator();
        int zeroTaskCount=0;
        while (it.hasNext()) {
            TaskTemplateDetailView ttDetail = (TaskTemplateDetailView) it.next();
            if(ttDetail!=null){
                str.append(ttDetail.getTaskId());
                str.append("{(");
                str.append(ttDetail.getRefTableIndexes()[0]);
                str.append(")(");
                str.append(ttDetail.getRefTableIndexes()[1]);
                str.append(")}\n");
            }else{
                if(zeroTaskCount==0){
                    str.append("== begin task ==\n");
                }
                else if(zeroTaskCount==1){
                    str.append("== end task ==\n");
                }
                else {
                    str=new StringBuffer("printTableIndexes=>error");
                    log.info(str.toString());
                    return;
                }
                zeroTaskCount++;
            }
        }
        log.info("printTableIndexes=>"+str.toString());
    }
}
