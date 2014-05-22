package com.cleanwise.service.api.cachecos;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.PairView;

import java.io.Serializable;

public class CacheKey implements Serializable {

    public final static String DELIM = "#";
    private final static String DEFAULT_NAME = "CachedObject";
    private String key;
    public static long defObjCounter = 0;

    public CacheKey(String name, PairViewVector argv) {

        if (Utility.isSet(name)) {
            this.key = name;
        } else {
            this.key = DEFAULT_NAME+"_"+(defObjCounter++);
        }

        if (argv != null) {
            for (Object oArg : argv) {
                PairView arg = (PairView) oArg;
                this.key += DELIM;
                this.key += arg.getObject1()+":"+arg.getObject2();
            }
        }

    }

    public int hashCode() {
        return key.hashCode();
    }

    public boolean equals(Object r) {
        return (r instanceof CacheKey)
                && key.equals(((CacheKey) r).key);
    }



    public String toString() {
        return key;
    }


}
