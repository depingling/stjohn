package com.cleanwise.tools;

/**
 * Title:        RefCodeToDB
 * Description:  Using reflection, get the reference code constants and
 *               create a SQL file of INSERT statements to populate the
 *               CLW_REF_CD table
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 * @version      $Revision: 1.5 $
 */

import java.lang.reflect.*;
import java.io.*;

import com.cleanwise.service.api.util.RefCodeNames;

class RefCodeToDB {
    private String outfile;

    public void setOutfile(String outfile) {
	this.outfile = outfile;
    }

    private void parseArgs(String args[]) {
	int i = 0;
	while (i < args.length) {
	    String s = args[i];
	    if (s.startsWith("-outfile")) {
		setOutfile(s.substring(8).trim());
		i++;
	    } else {
		System.err.println(args[i]);
		printUsage();
	    }
	}
    }

    static void printUsage() {
	String string = "RefCodeToDB -outfile <output file>\n";
	System.err.println(string);
	System.exit(1);
    }

    public void execute() {
	PrintStream out = null;

	try {
	    if (outfile != null) {
		out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outfile)));
	    } else {
		out = System.out;
	    }

	    out.println("TRUNCATE TABLE CLW_REF_CD;");

	    Class c = RefCodeNames.class;
	    Class[] classes = c.getClasses();
	    for (int i = 0; i < classes.length; i++) {
		Class ic = classes[i];
		String cname = ic.getName();
		String refCd = cname.substring(cname.indexOf('$')+1);
		Field[] publicFields = ic.getFields();
		for (int j = 0; j < publicFields.length; j++) {
		    String value = (String)publicFields[j].get(null);
                    value = value.replace("'","''");
		    String shortDesc = publicFields[j].getName();
		    out.println("INSERT INTO CLW_REF_CD (REF_CD_ID,REF_CD,SHORT_DESC,CLW_VALUE,REF_CD_TYPE,REF_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES (CLW_REF_CD_SEQ.NEXTVAL,'"+refCd+"','"+shortDesc+"','"+value+"','UNKNOWN','ORDERED',TO_DATE('2001-07-27','YYYY-MM-DD'),'RefCodeToDB',TO_DATE('2001-07-27','YYYY-MM-DD'),'RefCodeToDB');");
		}
	    }
	} catch (Exception e) {
	    System.err.println(e);
	} finally {
	    if (out != null && out != System.out) {
		out.close();
	    }
	}
    }

    public static void main(String args[]) {
	RefCodeToDB codeProcess = new RefCodeToDB(); 
	codeProcess.parseArgs(args);
	codeProcess.execute();
    }
}


