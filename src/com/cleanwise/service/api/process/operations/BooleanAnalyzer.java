package com.cleanwise.service.api.process.operations;

/**
 */
public class BooleanAnalyzer {

    public void analyze(Boolean b) throws Exception {
        if (b.booleanValue()) {
            throw new Exception("The previous problem stops process");
        }
    }
}
