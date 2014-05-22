package com.cleanwise.service.api.tree.transformer;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;

import com.cleanwise.service.api.tree.ReportItem;

public class ReportItem2JavaTransformer {

    public static void fill(Object obj, ReportItem item) {
        Class clazz = obj.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor descriptors[] = beanInfo
                    .getPropertyDescriptors();
            for (int i = 0; descriptors != null && i < descriptors.length; i++) {
                PropertyDescriptor descriptor = descriptors[i];
                String name = descriptor.getName();
                if ("class".equals(name) == true) {
                    continue;
                }
                Object value = getValue(item, name);
                BeanUtils.setProperty(obj, name, value);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private final static Object getValue(ReportItem item, String property) {
        Object value = item.getAttribute(property);
        if (value != null) {
            return value;
        }
        List children = item.getChildren();
        for (int i = 0; i < children.size(); i++) {
            ReportItem child = (ReportItem) children.get(i);
            if (child.getName().equals(property)) {
                return child.getValue();
            }
        }
        return value;
    }
}
