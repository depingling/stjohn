package com.cleanwise.service.api.tree.xml;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.cleanwise.service.api.tree.ReportFactory;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.ReportTransformer;

public class ReportXmlFactory implements ReportFactory {
    public String transform(ReportItem root, ReportTransformer transformer) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputFormat format = new OutputFormat("    ", true);
        Document doc = DocumentFactory.getInstance().createDocument();
        createRoot(root, doc, transformer);
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(doc);
            writer.flush();
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new String(out.toByteArray());
    }

    private static void createRoot(ReportItem tag, Document doc,
            ReportTransformer transformer) {
        Element child = doc.addElement(tag.getName());
        fill(tag, child, transformer);
    }

    private static void createChild(ReportItem tag, Element parent,
            ReportTransformer transformer) {
        Element child = parent.addElement(tag.getName());
        fill(tag, child, transformer);
    }

    private static void fill(ReportItem tag, Element element,
            ReportTransformer transformer) {
        Iterator it = tag.getAttributes().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            element.addAttribute((String) entry.getKey(), transformer
                    .transform(entry.getValue()));
        }
        if (tag.getValue() != null) {
            element.addText(transformer.transform(tag.getValue()));
        }
        List list = tag.getChildren();
        for (int i = 0; list != null && i < list.size(); i++) {
            createChild((ReportItem) list.get(i), element, transformer);
        }
    }

    private final static String PACKAGE_PATH = "com.cleanwise.service.api.value";

    /**
     * 
     * @see com.cleanwise.service.api.tree.ReportFactory#transform(java.lang.String,
     *      com.cleanwise.service.api.tree.ReportTransformer)
     */
    public ReportItem transform(String source, ReportTransformer transformer) {
        try {
            Document doc = DocumentHelper.parseText(source);
            return createReportItem(doc.getRootElement(), transformer);
        } catch (DocumentException de) {
            de.printStackTrace();
            throw new RuntimeException("Can't parse XML:" + de.getMessage(), de);
        }
    }

    private static ReportItem createReportItem(Element element,
            ReportTransformer transformer) {
        ReportItem item = ReportItem.createValue(element.getName());
        Class type = getObject(element).getClass();
        if (element.elements().size() == 0) {
            item.addValue(transformer.transform(element.getText(), type));
        }
        for (int i = 0; i < element.attributeCount(); i++) {
            Attribute attr = element.attribute(i);
            Class typeAttr = getObject(attr).getClass();
            item.addAttribute(attr.getName(), transformer.transform(attr
                    .getValue(), typeAttr));
        }
        List elements = element.elements();
        for (int i = 0; i < elements.size(); i++) {
            Element child = (Element) elements.get(i);
            item.addChild(createReportItem(child, transformer));
        }
        return item;
    }

    private static Object getObject(Element element) {
        return getObject(element.getPath());
    }

    private static Object getObject(Attribute attr) {
        String path = attr.getPath().replaceAll("@", "");
        return getObject(path);
    }

    private static Object getObject(String path) {
        try {
            String after = getAfter(path, '/');
            String before = getBefore(path, '/');
            Class clazz = null;
            try {
                clazz = Class.forName(PACKAGE_PATH + "." + after);
                try {
                    return clazz.newInstance();
                } catch (Throwable t) {
                    Method method = clazz
                            .getMethod("createValue", new Class[0]);
                    return method.invoke(clazz, new Object[0]);
                }
            } catch (Throwable t2) {
                Object obj = getObject(before);
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor descriptors[] = beanInfo
                        .getPropertyDescriptors();
                for (int i = 0; i < descriptors.length; i++) {
                    if (descriptors[i].getName().equals(after)) {
                        Class type = descriptors[i].getPropertyType();
                        String name = type.getName();
                        if (descriptors[i].getPropertyType().isPrimitive()) {
                            return transformPrimitive(descriptors[i]
                                    .getPropertyType());
                        } else {
                            return descriptors[i].getPropertyType()
                                    .newInstance();
                        }
                    }
                }
                throw new RuntimeException("Not found property:" + after);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private static String getAfter(String source, char delimiter) {
        int index = source.lastIndexOf(delimiter);
        if (index != 1) {
            return source.substring(index + 1);
        }
        return source;
    }

    private static String getBefore(String source, char delimiter) {
        int index = source.lastIndexOf(delimiter);
        if (index != 1) {
            return source.substring(0, index);
        }
        return source;
    }

    private final static Object transformPrimitive(Class clazz) {
        if (clazz == Byte.TYPE) {
            return new Byte((byte) 0);
        } else if (clazz == Short.TYPE) {
            return new Short((short) 0);
        } else if (clazz == Character.TYPE) {
            return new Character('\0');
        } else if (clazz == Integer.TYPE) {
            return new Integer(0);
        } else if (clazz == Long.TYPE) {
            return new Long(0);
        } else if (clazz == Float.TYPE) {
            return new Float(0);
        } else if (clazz == Double.TYPE) {
            return new Double(0);
        }
        throw new RuntimeException("Not found primitive for " + clazz);
    }

}
