package com.cleanwise.service.api.process.operations;

import java.io.File;


public interface FileGenerator {
    public static final String TXT_FILE = "TXT_FILE";
    public static final String XML_FILE = "XML_FILE";
    public static final String HTML_FILE = "HTML_FILE";
    public static final String PDF_FILE = "PDF_FILE";

    public String generate(Object data,String fileName) throws Exception;
 	public String generate(Object data, File file) throws Exception;

}
