package com.cleanwise.service.api.tree;

public interface ReportFactory {
    public String transform(ReportItem root, ReportTransformer transformer);

    public ReportItem transform(String source, ReportTransformer transformer);
}
