package com.cleanwise.service.api.tree.transformer;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.cleanwise.service.api.tree.ReportTransformer;
import com.cleanwise.service.api.tree.types.ReportDate;
import com.cleanwise.service.api.tree.types.ReportMessage;

public class ExtendedReportTransformer implements ReportTransformer {

    private final ResourceBundle resourceBundle;

    public ExtendedReportTransformer(String baseName, Locale locale) {
        resourceBundle = ResourceBundle.getBundle(baseName, locale);
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
            return resourceBundle.getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    private final String getNumber(Number number) {
        return number.toString();
    }

    /**
     * @see com.cleanwise.service.api.tree.ReportTransformer#transform(java.lang.String,
     *      java.lang.Class)
     */
    public Object transform(String value, Class type) {
        // TODO Auto-generated method stub
        return null;
    }

}
