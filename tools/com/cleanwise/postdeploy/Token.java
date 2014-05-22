package com.cleanwise.useractivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
    private final Type _type;
    private final String _beginMark;
    private final String _endMark;
    private String _value;
    private boolean _isFilled;

    public final static String REQUEST_START_PATTERN = "@@##@@S";
    public final static String REQUEST_END_PATTERN = "@@##@@E";
    public final static String REQUEST_COMPLETE_PATTERN = "@@##@@";
    public final static String ACTION_START_PATTERN = "@@@@S";
    public final static String ACTION_END_PATTERN = "@@@@E";
    public final static String ACTION_COMPLETE_PATTERN = "@@@@";
    
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String USER_REGEX = "User: <(.*?)>";
    public final static String REQUEST_ID_REGEX = "RequestId: <(.*?)>";
    public final static String SESSION_ID_REGEX = "Session id : <(.*?)>";
    public final static String STARTED_AT_REGEX = "Started at: <(.*?)>";
    public final static String REFERER_REGEX = "Referer: <(.*?)>";
    public final static String CLASS_REGEX = "Class: <(.*?)>";
    public final static String ACTION_REGEX = "Action: <(.*?)>";
    public final static String ENDET_AT_REGEX = "Ended at: <(.*?)>";
    public final static String RESULT_REGEX = "Result: <(.*?)>";
    public final static String DURATION_REGEX = "Duration: <(.*?)>";
    public final static String PARAMS_REGEX = "Params: \\{(.*?)\\}";
    
    public static enum Type {
        REQUEST_START,
        REQUEST_END,
        ACTION_START,
        ACTION_END
    }
    
    public Token(Type type, String beginMark, String endMark) {
        _type = type;
        _beginMark = beginMark;
        _endMark = endMark;
    }

    public void clear() {
        _value = null;
        _isFilled = false;
    }

    public boolean isFilled() {
        return _isFilled;
    }

    public String getValue() {
        return _value;
    }

    public Type getType() {
        return _type;
    }

    public String processLine(String line) {
        if (line != null && line.length() > 0) {
            int index = line.indexOf(_endMark);
            if (_value == null) {
                index = line.indexOf(_beginMark);
                if (index > -1) {
                    int indexStart = index + _beginMark.length();
                    int indexEnd = line.indexOf(_endMark, indexStart);
                    if (indexEnd > -1) {
                        _value = line.substring(indexStart, indexEnd);
                        _isFilled = true;
                        return line.substring(indexEnd + _endMark.length());
                    } else {
                        if (indexStart > line.length()){
                            _value = LINE_SEPARATOR;
                        } else {
                            _value = line.substring(indexStart) + LINE_SEPARATOR;
                        }
                    }
                }
            } else if (index > -1) {
                _value += line.substring(0, index);
                _isFilled = true;
                return line.substring(index + _endMark.length());
            } else {
                _value += line + LINE_SEPARATOR;
            }
        }
        return null;
    }
    
    public String parseValue(String pattern) {
        return parse(_value, pattern);
    }
    
    public static String parse(String source, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
        try {
            Matcher m = p.matcher(source);
            if (m.find()) {
                String result = m.group(1);
                return ("null".equals(result)) ? null : result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}