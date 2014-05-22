package com.cleanwise.service.api.tree.transformer;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;

import com.cleanwise.service.api.tree.ReportItem;

public class Java2ReportItemTransformer {

    public static ReportItem transform(Object obj) {
        return transform(obj, getSimpleName(obj.getClass()));
    }

    public static ReportItem transform(Object obj, String xmlElement) {
        ReportItem item = ReportItem.createValue(xmlElement);
        Class clazz = obj.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor descriptors[] = beanInfo
                    .getPropertyDescriptors();
            for (int i = 0; descriptors != null && i < descriptors.length; i++) {
                PropertyDescriptor descriptor = descriptors[i];
                String name = descriptor.getName();
                Object value = PropertyUtils.getProperty(obj, name);
                if ("class".equals(name) == true) {
                    continue;
                }
                Class propertyType = descriptor.getPropertyType();
                if (asElement(propertyType)) {
                    if (isSimpleType(propertyType)) {
                        item.addChild(ReportItem.createValue(name).addValue(
                                value));
                    } else if (value instanceof Collection) {
                        item.addChild(Java2ReportItemTransformer
                                .transform((Collection) value));
                    }
                } else {
                    item.addAttribute(name, value);
                }
            }
            return item;
        } catch (Exception ie) {
            throw new RuntimeException(ie);
        }
    }
 
    public static ReportItem transform(Collection collection) {
        String item = null;
        Iterator it = collection.iterator();
        if (it.hasNext()) {
            item = getSimpleName(it.next().getClass());
        }
        return transform(collection, getSimpleName(collection.getClass()), item);
    }

    public static ReportItem transform(Collection colleciton,
            String xmlElementCollection, String xmlElementItem) {
        ReportItem parent = ReportItem.createValue(xmlElementCollection);
        Iterator it = colleciton.iterator();
        while (it.hasNext()) {
            parent.addChild(transform(it.next(), xmlElementItem));
        }
        return parent;
    }

    private static boolean isSimpleType(Class clazz) {
        if (clazz == Byte.TYPE || clazz == Byte.class || clazz == Short.TYPE
                || clazz == Short.class || clazz == Integer.TYPE
                || clazz == Integer.class || clazz == Long.TYPE
                || clazz == Long.class || clazz == Float.TYPE
                || clazz == Float.class || clazz == Double.class
                || clazz == Double.TYPE || clazz == Character.class
                || clazz == Character.TYPE || clazz == String.class
                || clazz == Date.class) {
            return true;
        }
        return false;
    }

    private static boolean asElement(Class clazz) {
        if (clazz == Byte.TYPE || clazz == Byte.class || clazz == Short.TYPE
                || clazz == Short.class || clazz == Integer.TYPE
                || clazz == Integer.class || clazz == Long.TYPE
                || clazz == Long.class || clazz == Float.TYPE
                || clazz == Float.class || clazz == Double.TYPE
                || clazz == Double.class || clazz == Date.class) {
            return false;
        }
        return true;
    }

    public static String getSimpleName(Class clazz) {
        String name = clazz.getName();
        int index = name.lastIndexOf('.');
        if (index >= 0) {
            return name.substring(index + 1);
        }
        return name;
    }
}
