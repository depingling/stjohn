package com.cleanwise.service.api.tree.transformer;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.ReportTransformer;
import com.cleanwise.service.api.tree.types.ReportDate;
import com.cleanwise.service.api.tree.types.ReportMessage;
import com.cleanwise.view.i18n.ClwI18nUtil;

public class HttpRequestReportTransformer implements ReportTransformer {

    private final HttpServletRequest request;

    private final static SimpleDateFormat BASE_DATE_FORMAT = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    public HttpRequestReportTransformer(HttpServletRequest request) {
        this.request = request;
    }

    public String transform(Object value) {
        if (value instanceof Number) {
            return getNumber((Number) value);
        } else if (value instanceof ReportDate) {
            ReportDate reportDate = (ReportDate) value;
            String format = getString(reportDate.getType());
            SimpleDateFormat formatter = getSimpleDateFormat(format);
            return formatter.format(reportDate);
        } else if (value instanceof ReportMessage) {
            ReportMessage reportMessage = (ReportMessage) value;
            String pattern = getString(reportMessage.getKey());
            List params = reportMessage.getParams();
            List paramsTransformed = new ArrayList();
            for (int i = 0; i < params.size(); i++) {
                paramsTransformed.add(transform(params.get(i)));
            }
            return MessageFormat.format(pattern, paramsTransformed
                    .toArray(new Object[paramsTransformed.size()]));
        } else if (value instanceof Date) {
            return BASE_DATE_FORMAT.format((Date) value);
        }
        return String.valueOf(value);
    }

    private final Map simpleDateFormats = new HashMap();

    private final SimpleDateFormat getSimpleDateFormat(final String formatString) {
        SimpleDateFormat formatter = (SimpleDateFormat) simpleDateFormats
                .get(formatString);
        if (formatter == null) {
            formatter = new SimpleDateFormat(formatString);
            simpleDateFormats.put(formatString, formatter);
        }
        return formatter;
    }

    private final String getString(String key) {
        try {
            return ClwI18nUtil.getMessage(request, key, null);
        } catch (Exception e) {
            return "^clw^";
        }
    }

    private final String getNumber(Number number) {
        return number.toString();
    }

    /**
     * 
     * @see com.cleanwise.service.api.tree.ReportTransformer#transform(java.lang.String,
     *      java.lang.Class)
     */
    public Object transform(String source, Class clazz) {
        String name = clazz.getName();
        if (clazz == Byte.TYPE || clazz == Byte.class) {
            return Byte.valueOf(source);
        } else if (clazz == Float.class || clazz == Float.TYPE) {
            return Float.valueOf(source);
        } else if (clazz == Short.TYPE || clazz == Short.class) {
            return Short.valueOf(source);
        } else if (clazz == Integer.TYPE || clazz == Integer.class) {
            return Integer.valueOf(source);
        } else if (clazz == Long.TYPE || clazz == Long.class) {
            return Long.valueOf(source);
        } else if (clazz == Float.TYPE || clazz == Float.class) {
            return Float.valueOf(source);
        } else if (clazz == Double.TYPE || clazz == Double.class) {
            return Double.valueOf(source);
        } else if (clazz == Character.class || clazz == Character.TYPE) {
            return new Character(source.charAt(0));
        } else if (clazz == String.class) {
            return source;
        } else if (clazz == Date.class) {
            try {
                return BASE_DATE_FORMAT.parse(source);
            } catch (ParseException e) {
                throw new RuntimeException("Can't parse date:" + source, e);
            }
        }
        return source;
    }

    private final static String PACKAGE_PATH = "com.cleanwise.service.api.value";

    public ReportItem transofrm(String source, ReportTransformer transformer) {
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
