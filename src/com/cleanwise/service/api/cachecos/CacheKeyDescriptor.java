package com.cleanwise.service.api.cachecos;

import java.util.HashMap;
import java.util.HashSet;

public class CacheKeyDescriptor extends HashMap<CacheKey, HashMap<String, HashMap<String, HashSet<Object>>>> {

    public void add(CacheKey key, String table, String fieldName, Object fieldVal) {

        HashMap<String, HashMap<String, HashSet<Object>>> tp = this.get(key);
        if (tp == null) {
            tp = new HashMap<String, HashMap<String, HashSet<Object>>>();
        }

        HashMap<String, HashSet<Object>> fp = tp.get(table);
        if (fp == null) {
            fp = new HashMap<String, HashSet<Object>>();
        }

        HashSet<Object> vp = fp.get(fieldName);
        if(vp == null){
          vp = new HashSet<Object>();
        }

        vp.add(fieldVal);

        fp.put(fieldName, vp);
        tp.put(table, fp);

        this.put(key, tp);
    }
}
