package com.cleanwise.service.api.value;

public class TaskTemplateDetailView implements java.io.Serializable{

    private int taskId;
    private TaskDetailView task;
    private Object[] refTableIndexes;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public TaskDetailView getTask() {
        return task;
    }

    public void setTask(TaskDetailView task) {
        this.task = task;
    }

    public TaskTemplateDetailView(int taskId, TaskDetailView task) {
        this.taskId = taskId;
        this.task = task;
    }

    public Object[] getRefTableIndexes() {
        return refTableIndexes;
    }

    public void setRefTableIndexes(Object[] refTableIndexes) {
        this.refTableIndexes = refTableIndexes;
    }

    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        return "taskId: " + taskId + " task: " + task + " refTablePrevp: " + refTableIndexes[0] + " refTableNextp: " + refTableIndexes[1];
    }
}
