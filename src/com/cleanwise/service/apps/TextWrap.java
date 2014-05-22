package com.cleanwise.service.apps;

import java.io.*;

/**
 * A stand-alone utility class to wrap lines (via a
 * currently hard-coded line feed) in a file.
 *
 * Usage:
 *  java com.cleanwise.service.apps.TextWrap <column> <infile> <outfile>
 */
public class TextWrap {

    public  static char WRAP_CHAR = '\n';//System.getProperty("platform line.separator");
    public  static char[] NEW_WRAP_CHARS = {'\r','\n'};
    
    private static int column = -1;
    private static String infilename = null;
    private static String outfilename = null;
    private static File infile = null;
    private static File outfile = null;

    private TextWrap() {}

    private static void exit(String errorMsg)
    {
        if (errorMsg != null) {
            System.err.println("FAIL: " + errorMsg);
        }
	    System.err.println(
               "Usage: java Wrap <column position> <infile> <outfile>");
        System.exit(1);
    }


    public static void main(String[] args) 
    throws IOException {
	if (args.length != 3) {
        exit("Wrong number of arguments");
	}

    try {
        column = Integer.parseInt(args[0]);
    } catch (Exception e) {
        exit("Non-numeric column number: " + args[0]);
    }
    if (column <= 0) {
        exit("Column number is negative or zero: " + column);
    }

    try {
        infilename = new String(args[1]);
        infile = new File(infilename);
    } catch (Exception e) {
        exit("Problem with input filename argument: " + args[1]);
    }

    if (!infile.exists()) {
        exit("Input file " + infile.getCanonicalPath() + " doesn't exist");
    }
    if (!infile.canRead()) {
        exit("Input file " + infile.getCanonicalPath() + " not readable");
    }

    try {
        outfilename = new String(args[2]);
        outfile = new File(outfilename);
    } catch (Exception e) {
        exit("Problem with output filename argument: " + args[2]);
    }

    if (outfile.exists() && !outfile.canWrite()) {
        exit("Output file " + outfile.getCanonicalPath() + " not writeable");
    }

    FileInputStream in = null;
    FileOutputStream out = null;
    try {
        in = new FileInputStream(infile);
        out = new FileOutputStream(outfile);
        int bytesRead = 0;
        int bytesWritten = 0;
        int lineBytesWritten = 0;
        int numWrapsInserted = 0;
        int existingWraps = 0;

        int lastChr = -1;
        while (true) {
            int chr = in.read();
            if (chr == -1) break;

            bytesRead++;
            if (chr != WRAP_CHAR) {
                out.write(chr);
                lineBytesWritten++; bytesWritten++;
            }

            // Keep track of any line break characters read in,
            // reset the counter whenever one is encountered and
            // replace it with the contents of the NEW_LINE_WRAP_CHARS
            // array.
            if (chr == WRAP_CHAR) {
                for(int i=0;i<NEW_WRAP_CHARS.length;i++){
                    out.write((int) NEW_WRAP_CHARS[i]);
                }
                lineBytesWritten = 0;
                existingWraps++;
            } else if (lineBytesWritten % column == 0) {
            //if (bytesWritten % column == 0 && chr != WRAP_CHAR) {
                for(int i=0;i<NEW_WRAP_CHARS.length;i++){
                    out.write((int) NEW_WRAP_CHARS[i]);
                }
                bytesWritten++;
                lineBytesWritten = 0;
                numWrapsInserted++;
            } 
            lastChr = chr;
        }
        // For reporting purposes ignore any line wrap character at the
        // end of the file
        if (lastChr == WRAP_CHAR) {
            existingWraps--;
        }
        if (bytesRead == 0) {
            throw new java.text.ParseException("Empty file", 0);
        }
        if (bytesWritten != (bytesRead + numWrapsInserted)) {
            throw new java.text.ParseException
                      ( "Bytes written (" + bytesWritten
                      + ") doesn't equal bytes read (" + bytesRead
                      + ") + wraps (" + numWrapsInserted + ")",
                      bytesRead);
        }
        in.close();
        out.close();
        String logStr = "SUCCESS:"
           + " Input file " + infilename + " with " + bytesRead + " bytes"
           + " was saved to " + outfilename + " with " + bytesWritten + " bytes"
           + " (" + existingWraps + " existing line wraps"
           + ", " + numWrapsInserted + " line wraps inserted)";

    } catch (Exception e) {
        exit("Exception reading/writing " + infilename + ": " + e.getMessage());
        if (in != null) in.close();
        if (out != null) out.close();
        outfile.delete();
    }
  }
}
