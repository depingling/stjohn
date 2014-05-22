package com.cleanwise.service.api.cachecos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class CacheDescriptor implements Serializable {

    private CacheKeyDescriptor keyDescriptor;
    private CacheTableDescriptor tableDescriptor;
    private CacheTableLinkDescriptor tableLinkDescriptor;

    public CacheDescriptor() {

        keyDescriptor = new CacheKeyDescriptor();
        tableDescriptor = new CacheTableDescriptor();
        tableLinkDescriptor = new CacheTableLinkDescriptor();
    }

    public CacheKeyDescriptor getKeyDescriptor() {
        return keyDescriptor;
    }

    public void setKeyDescriptor(CacheKeyDescriptor descriptor) {
        this.keyDescriptor = descriptor;
    }

    public CacheTableDescriptor getTableDescriptor() {
        return tableDescriptor;
    }

    public void setTableDescriptor(CacheTableDescriptor tableDescriptor) {
        this.tableDescriptor = tableDescriptor;
    }

    public CacheTableLinkDescriptor getTableLinkDescriptor() {
        return tableLinkDescriptor;
    }

    public String toJson() {

        StringBuffer jsonStr = new StringBuffer();
        jsonStr.append("{");
        jsonStr.append(" TableLinkDescription:").append(toJson(tableLinkDescriptor));
        jsonStr.append(",");
        jsonStr.append("TableDescription:").append(toJson(tableDescriptor));
        jsonStr.append(",");
        jsonStr.append("KeyDescription:").append(toJson(keyDescriptor));
        jsonStr.append("}");

        return jsonStr.toString();
    }

    private String toJson(CacheTableLinkDescriptor tableLinkDesc) {
        StringBuffer jsonStr = new StringBuffer();
        jsonStr.append("{");
        if (tableLinkDesc != null && !tableLinkDesc.isEmpty()) {
            Set<String> tableKeys = tableLinkDesc.keySet();
            int i = 0;
            jsonStr.append("values: [");
            for (String tableKey : tableKeys) {
                i++;
                HashMap<CacheKey, HashSet<HashSet<String>>> cacheKeyMap = tableLinkDesc.get(tableKey);
                jsonStr.append("{");
                jsonStr.append("id:");
                jsonStr.append("'").append(i).append("'");
                jsonStr.append(",name:");
                jsonStr.append("'").append(tableKey).append("'");
                if (cacheKeyMap != null && !cacheKeyMap.isEmpty()) {
                    jsonStr.append(", children:[");
                    Set<CacheKey> cacheKeys = cacheKeyMap.keySet();
                    int i1 = 0;
                    for (CacheKey cacheKey : cacheKeys) {
                        i1++;
                        jsonStr.append("{");
                        jsonStr.append("id:");
                        jsonStr.append("'").append(i).append("@").append(i1).append("'");
                        jsonStr.append(",name:");
                        jsonStr.append("'").append(cacheKey).append("'");
                        HashSet<HashSet<String>> criteries = cacheKeyMap.get(cacheKey);
                        if (criteries != null && !criteries.isEmpty()) {
                            jsonStr.append(", children:[");
                            int i2 = 0;
                            for (HashSet<String> criteria : criteries) {
                                i2++;
                                jsonStr.append("{");
                                jsonStr.append("id:");
                                jsonStr.append("'").append(i).append("@").append(i1).append("@").append(i2).append("'");
                                jsonStr.append(",name:");
                                jsonStr.append("'").append(i2).append("'");
                                if (criteria != null && !criteria.isEmpty()) {
                                    jsonStr.append(", children:[");
                                    int i3 = 0;
                                    for (String field : criteria) {
                                        i3++;
                                        jsonStr.append("{");
                                        jsonStr.append("id:");
                                        jsonStr.append("'").append(i).append("@").append(i1).append("@").append(i2).append("@").append(i3).append("'");
                                        jsonStr.append(",name:");
                                        jsonStr.append("'").append(field).append("'");
                                        jsonStr.append("}");
                                        if (i3 < criteria.size()) {
                                            jsonStr.append(",");
                                        }
                                    }
                                    jsonStr.append("]");
                                }
                                jsonStr.append("}");
                                if (i2 < criteries.size()) {
                                    jsonStr.append(",");
                                }
                            }
                            jsonStr.append("]");
                        }
                        jsonStr.append("}");
                        if (i1 < cacheKeys.size()) {
                            jsonStr.append(",");
                        }
                    }
                    jsonStr.append("]");
                }
                jsonStr.append("}");
                if (i < tableKeys.size()) {
                    jsonStr.append(",");
                }
            }
            jsonStr.append("]");
        }
        jsonStr.append("}");
        return jsonStr.toString();
    }

    private String toJson(CacheKeyDescriptor keyDescriptor) {
        StringBuffer jsonStr = new StringBuffer();
        jsonStr.append("{ ");
        if (keyDescriptor != null && !keyDescriptor.isEmpty()) {
            Set<CacheKey> cacheKeys = keyDescriptor.keySet();
            jsonStr.append("values: [");
            int i = 0;
            for (CacheKey cacheKey : cacheKeys) {
                i++;
                jsonStr.append("{");
                jsonStr.append("id:");
                jsonStr.append("'").append(i).append("'");
                jsonStr.append(",name:");
                jsonStr.append("'").append(cacheKey).append("'");
                HashMap<String, HashMap<String, HashSet<Object>>> tables = keyDescriptor.get(cacheKey);
                if (tables != null && !tables.isEmpty()) {
                    jsonStr.append(", children:[");
                    Set<String> tableKeys = tables.keySet();
                    int i1 = 0;
                    for (String tableKey : tableKeys) {
                        i1++;
                        jsonStr.append("{");
                        jsonStr.append("id:");
                        jsonStr.append("'").append(i).append("@").append(i1).append("'");
                        jsonStr.append(",name:");
                        jsonStr.append("'").append(tableKey).append("'");
                        HashMap<String, HashSet<Object>> fields = tables.get(tableKey);
                        if (fields != null && !fields.isEmpty()) {
                            jsonStr.append(", children:[");
                            Set<String> fieldKeys = fields.keySet();
                            int i2 = 0;
                            for (String fieldKey : fieldKeys) {
                                i2++;
                                jsonStr.append("{");
                                jsonStr.append("id:");
                                jsonStr.append("'").append(i).append("@").append(i1).append("@").append(i2).append("'");
                                jsonStr.append(",name:");
                                jsonStr.append("'").append(fieldKey).append("'");
                                HashSet<Object> values = fields.get(fieldKey);
                                if (values != null && !values.isEmpty()) {
                                    jsonStr.append(", children:[");
                                    int i3 = 0;
                                    for (Object value : values) {
                                        i3++;
                                        jsonStr.append("{");
                                        jsonStr.append("id:");
                                        jsonStr.append("'").append(i).append("@").append(i1).append("@").append(i2).append("@").append(i3).append("'");
                                        jsonStr.append(",name:");
                                        jsonStr.append("'").append(value).append("'");
                                        jsonStr.append("}");
                                        if (i3 < values.size()) {
                                            jsonStr.append(",");
                                        }
                                    }
                                    jsonStr.append("]");
                                }
                                jsonStr.append("}");
                                if (i2 < fieldKeys.size()) {
                                    jsonStr.append(",");
                                }
                            }
                            jsonStr.append("]");
                        }
                        jsonStr.append("}");
                        if (i1 < tableKeys.size()) {
                            jsonStr.append(",");
                        }
                    }
                    jsonStr.append("]");
                }
                jsonStr.append("}");
                if (i < cacheKeys.size()) {
                    jsonStr.append(",");
                }
            }
            jsonStr.append("]");
        }
        jsonStr.append("}");
        return jsonStr.toString();
    }

    private String toJson(CacheTableDescriptor tableDescriptor) {
        StringBuffer jsonStr = new StringBuffer();
        jsonStr.append("{ ");
        if (tableDescriptor != null && !tableDescriptor.isEmpty()) {
            Set<String> tableKeys = tableDescriptor.keySet();
            jsonStr.append("values: [");
            int i = 0;
            for (String tableKey : tableKeys) {
                i++;
                jsonStr.append("{");
                jsonStr.append("id:");
                jsonStr.append("'").append(i).append("'");
                jsonStr.append(",name:");
                jsonStr.append("'").append(tableKey).append("'");
                CacheTableFieldMap fields = tableDescriptor.get(tableKey);
                if (fields != null && !fields.isEmpty()) {
                    jsonStr.append(", children:[");
                    Set<String> fieldKeys = fields.keySet();
                    int i1 = 0;
                    for (String fieldKey : fieldKeys) {
                        i1++;
                        jsonStr.append("{");
                        jsonStr.append("id:");
                        jsonStr.append("'").append(i).append("@").append(i1).append("'");
                        jsonStr.append(",name:");
                        jsonStr.append("'").append(fieldKey).append("'");
                        CacheFieldDescription values = fields.get(fieldKey);
                        if (values != null && !values.isEmpty()) {
                            jsonStr.append(", children:[");
                            Set<Object> valueKeys = values.keySet();
                            int i2 = 0;
                            for (Object valueKey : valueKeys) {
                                i2++;
                                jsonStr.append("{");
                                jsonStr.append("id:");
                                jsonStr.append("'").append(i).append("@").append(i1).append("@").append(i2).append("'");
                                jsonStr.append(",name:");
                                jsonStr.append("'").append(valueKey).append("'");
                                HashSet<CacheKey> cacheKeys = values.get(valueKey);
                                if (cacheKeys != null && !cacheKeys.isEmpty()) {
                                    jsonStr.append(", children:[");
                                    int i3 = 0;
                                    for (CacheKey cacheKey : cacheKeys) {
                                        i3++;
                                        jsonStr.append("{");
                                        jsonStr.append("id:");
                                        jsonStr.append("'").append(i).append("@").append(i1).append("@").append(i2).append("@").append(i3).append("'");
                                        jsonStr.append(",name:");
                                        jsonStr.append("'").append(cacheKey).append("'");
                                        jsonStr.append("}");
                                        if (i3 < cacheKeys.size()) {
                                            jsonStr.append(",");
                                        }
                                    }
                                    jsonStr.append("]");
                                }
                                jsonStr.append("}");
                                if (i2 < values.size()) {
                                    jsonStr.append(",");
                                }
                            }
                            jsonStr.append("]");
                        }
                        jsonStr.append("}");
                        if (i1 < fields.size()) {
                            jsonStr.append(",");
                        }
                    }
                    jsonStr.append("]");
                }
                jsonStr.append("} ");
                if (i < tableKeys.size()) {
                    jsonStr.append(",");
                }
            }
            jsonStr.append("]");
        }
        jsonStr.append("}");
        return jsonStr.toString();
    }

    public void clear() {
        keyDescriptor.clear();
        tableDescriptor.clear();
        tableLinkDescriptor.clear();
    }

    public boolean isEmpty() {
        return keyDescriptor.isEmpty() && tableDescriptor.isEmpty() && tableLinkDescriptor.isEmpty();
    }
}
