package com.cleanwise.service.api.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cleanwise.service.api.tree.types.ReportDate;
import com.cleanwise.service.api.tree.types.ReportMessage;

public class ReportItem implements Serializable {
    private static final long serialVersionUID = 5120046712836177207L;

    private String name;

    private Object value;

    private final List children = new ArrayList();

    private final Map attributes = new TreeMap();

    private ReportItem(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the value of <code>name</code> property.
     * 
     * @return The value of <code>name</code> property.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of <code>value</code> property.
     * 
     * @return The value of <code>value</code> property.
     */
    public Object getValue() {
        return value;
    }

    public ReportItem addValue(Object value) {
        this.value = value;
        return this;
    }

    public void setAttribute(String name, Object value) {
        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public ReportItem addChild(ReportItem child) {
        children.add(child);
        return this;
    }

    public ReportItem removeChild(ReportItem item) {
        children.remove(item);
        return this;
    }

    public ReportItem addAttribute(String name, Object value) {
        setAttribute(name, value);
        return this;
    }

    public ReportItem removeAttribute(String name) {
        setAttribute(name, null);
        return this;
    }

    /**
     * Returns the value of <code>attributes</code> property.
     * 
     * @return The value of <code>attributes</code> property.
     */
    public Map getAttributes() {
        return attributes;
    }

    /**
     * Returns the value of <code>childs</code> property.
     * 
     * @return The value of <code>childs</code> property.
     */
    public List getChildren() {
        return children;
    }

    private final static Object NULL = null;

    public final static ReportItem createValue(String name) {
        return createValue(name, NULL);
    }

    public final static ReportItem createValue(String name, Object value) {
        return new ReportItem(name, value);
    }

    public final static ReportItem createValue(String name, byte value) {
        return createValue(name, new Byte(value));
    }

    public final static ReportItem createValue(String name, short value) {
        return createValue(name, new Short(value));
    }

    public final static ReportItem createValue(String name, char value) {
        return createValue(name, new Character(value));
    }

    public final static ReportItem createValue(String name, int value) {
        return createValue(name, new Integer(value));
    }

    public final static ReportItem createValue(String name, long value) {
        return createValue(name, new Long(value));
    }

    public final static ReportItem createValue(String name, float value) {
        return createValue(name, new Float(value));
    }

    public final static ReportItem createValue(String name, double value) {
        return createValue(name, new Double(value));
    }

    public final static ReportItem createValue(String name, ReportDate value) {
        return createValue(name, (Object) value);
    }

    public final static ReportItem createValue(String name, ReportMessage value) {
        return createValue(name, (Object) value);
    }

    public ReportItem addAttribute(String name, byte value) {
        setAttribute(name, new Byte(value));
        return this;
    }

    public ReportItem addAttribute(String name, short value) {
        setAttribute(name, new Short(value));
        return this;
    }

    public ReportItem addAttribute(String name, char value) {
        setAttribute(name, new Character(value));
        return this;
    }

    public ReportItem addAttribute(String name, int value) {
        setAttribute(name, new Integer(value));
        return this;
    }

    public ReportItem addAttribute(String name, long value) {
        setAttribute(name, new Long(value));
        return this;
    }

    public ReportItem addAttribute(String name, float value) {
        setAttribute(name, new Float(value));
        return this;
    }

    public ReportItem addAttribute(String name, double value) {
        setAttribute(name, new Double(value));
        return this;
    }

    public ReportItem addAttribute(String name, ReportDate value) {
        setAttribute(name, value);
        return this;
    }

    public ReportItem addAttribute(String name, ReportMessage value) {
        setAttribute(name, value);
        return this;
    }

    public ReportItem addValue(byte value) {
        this.value = new Byte(value);
        return this;
    }

    public ReportItem addValue(short value) {
        this.value = new Short(value);
        return this;
    }

    public ReportItem addValue(char value) {
        this.value = new Character(value);
        return this;
    }

    public ReportItem addValue(int value) {
        this.value = new Integer(value);
        return this;
    }

    public ReportItem addValue(long value) {
        this.value = new Long(value);
        return this;
    }

    public ReportItem addValue(float value) {
        this.value = new Float(value);
        return this;
    }

    public ReportItem addValue(double value) {
        this.value = new Double(value);
        return this;
    }

    public ReportItem addDate(ReportDate value) {
        this.value = value;
        return this;
    }

    public ReportItem addMessage(ReportMessage value) {
        this.value = value;
        return this;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        StringBuffer sb = new StringBuffer();
        Iterator it = getAttributes().entrySet().iterator();
        String spaces = space(indent);
        sb.append(spaces + getName());
        if (getValue() != null) {
            sb.append(" : " + getValue() + ":" + getValue().getClass());
        }
        sb.append("\n");
        if (it != null && it.hasNext()) {
            sb.append(spaces + "ATTRIBUTES:\n");
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                sb.append(spaces + entry.getKey() + " : " + entry.getValue()
                        + ":" + entry.getValue().getClass() + "\n");
            }
        }
        List children = getChildren();
        if (children.size() > 0) {
            sb.append(spaces + "ELEMENTS:\n");
            for (int i = 0; i < children.size(); i++) {
                ReportItem item = (ReportItem) children.get(i);
                sb.append(item.toString(indent + 4));// + "\n");
            }
        }
        return sb.toString();
    }

    private String space(int count) {
        StringBuffer sb = new StringBuffer(count);
        for (int i = 0; i < count; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
