package com.cleanwise.service.apps.email;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public abstract class DocBuilder {

    public BufferedOutputStream os;
    public File file;
    public static final int CENTER = 0;
    public static final int RIGTH = 1;
    private static final int LEFT = 2;

    public static final int TABLE_COLUMN_SPACE = 2 ;

    public static int DOC_LENGTH = 75;//for msword
    private int LINE_COUNT_PAGE=69;  //for msword

    public StringBuffer line=new StringBuffer();
    public final int MSWORD=1;
    public final int WORDPAD=2;
    public static final int NOTEPAD =3 ;


    private int lineCount=-1;
    public int pageNumber=0;
    public String className="DocBuilder";

    public String align(String s,int allign) {
        switch(allign)
        {
            case 0:
                s=getCharLine(' ', ((DOC_LENGTH-s.length())/2))+s;
                return s;
            case 1: s=getCharLine(' ',(DOC_LENGTH-s.length())-line.length())+s;
                return s;
            default: return s;
        }
    }


    public void nextLine(StringBuffer sb) {
        lineCount++;
        if (lineCount % LINE_COUNT_PAGE == 0) {
            StringBuffer tempLine = line;
            line = new StringBuffer();
            pageNumber++;
            writeTitle(sb);
            line = new StringBuffer();
            line = tempLine;
        }

        if (line.length() > DOC_LENGTH) {
           ArrayList lines = parseDelim(line.toString(), ' ', DOC_LENGTH);
            line = new StringBuffer();
            lineCount=lineCount-1;
            Iterator it = lines.iterator();
           while (it.hasNext()) {
              line.append(it.next());
               nextLine(sb);
            }
        } else {
            sb.append(line);
            sb.append("\r\n");
            line = new StringBuffer();
        }
    }

    public abstract void writeTitle(StringBuffer sb);


    public String getCharLine(char c, int i) {
        String s = "";
        for (int idx = 0; idx < i; idx++)
            s += c;
        return s;
    }

    public void setUpOutputStream(File file) throws FileNotFoundException {
        this.file = file;
        os = new BufferedOutputStream(new FileOutputStream(this.file));
    }

    public void setUp(int format) throws FileNotFoundException {
        switch (format) {
            case MSWORD:
                DOC_LENGTH = 75;
                LINE_COUNT_PAGE = 64;
                break;
            case WORDPAD:
                DOC_LENGTH = 69;
                LINE_COUNT_PAGE = 64;
                break;
            case NOTEPAD:
                DOC_LENGTH = 69;
                LINE_COUNT_PAGE = 60;
                break;
            default:
                DOC_LENGTH = 69;
                LINE_COUNT_PAGE = 60;
                break;
        }

    }

    public void closeOutputStream() throws IOException {
        if (os != null) os.close();
    }
    public String setPageNumberCount(String str) {
        return replaceStr(str,  "$pnc$",String.valueOf(pageNumber));
    }

    public ArrayList normalizeArrray(ArrayList inputArray, char c, int i, int type) {
        ArrayList outputArray = new ArrayList(inputArray.size());
        Iterator it = inputArray.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();
            outputArray.add(normalizeString(s,c,i,type));
        }
        return outputArray;

    }

    public  String replaceStr(
           String str, String replace, String with ) {

           StringBuffer sb = null;
           String temp = str;
           boolean found = false;

           int start = 0;
           int stop  = 0;

           while( ( start = temp.indexOf( replace , stop )) != -1 ) {
               found = true;
               stop = start + replace.length();
               sb = new StringBuffer( temp.length() + with.length() - replace.length() );
               sb.append( temp.substring( 0 , start ) );
               sb.append( with );
               sb.append( temp.substring( stop , temp.length() ));
               temp = sb.toString();
               stop += with.length() - replace.length();
           }

           if( ! found ){
               return str;
           }
           else {
               return sb.toString();
           }
       }


    public String normalizeString(String s, char c, int i, int type) {
        if (s != null) {
            switch (type) {
                case CENTER:
                    int center_pos = Math.round(i / 2);
                    if (center_pos == 0) center_pos = 1;
                    int fadd = center_pos;
                    int eadd = i - center_pos;
                    int strLen = s.length();
                    int centerStr = strLen / 2;
                    String fpartStr = "";
                    String fStr = s.substring(0, centerStr);
                    if (fadd > 0) fpartStr = normalizeString(fStr, c, fadd, RIGTH);
                    String epartStr = "";
                    String eStr = s.substring(centerStr, strLen);
                    if (eadd > 0) epartStr = normalizeString(eStr, c, eadd, LEFT);
                    s = fpartStr + epartStr;
                    break;
                case LEFT:
                    if (s.length() < i) {
                        s = s+getCharLine(c, i - s.length());
                    }
                    break;
                case RIGTH:
                    if (s.length() < i) {
                        s = getCharLine(c, i - s.length()) + s;
                    }
                    break;
                default:
                    s = normalizeString(s, c, i, RIGTH);
                    break;
            }

        }
        return s;
    }



    public  ArrayList parseDelim(String s, char delim, int lenLine) {

        int partCount = s.length() / lenLine;
        int lastPartLen = s.length() % lenLine;
        ArrayList lines = new ArrayList();
        for (int i = 0; i <= partCount; i++) {
            int len = ((i * lenLine) + (i == (partCount) ? lastPartLen : lenLine));
            if (len > 0) {
                String line = s.substring(i * lenLine, len);
                if (line.trim().length() > 0) {
                    lines.add(line);
                }
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            String str = (String) lines.get(i);
            int lastSpace = str.lastIndexOf(delim);
            if (lastSpace == str.length() || lastSpace == -1) {
                lines.set(i, str);
            } else {
                for (int j = lines.size() - 1; i < j; j--) {
                    String cs = (String) lines.get(j);
                    String prevStr = ((String) lines.get(j - 1)).substring(lastSpace);
                    if (prevStr.length() > 0) {
                        cs = prevStr + cs;
                        if (cs.length() <= lenLine) {
                            lines.set(j, cs);
                            String prevReplace = ((String) lines.get(j - 1)).substring(0, lastSpace);
                            lines.set(j - 1, prevReplace);
                        } else {
                            String newS = cs.substring(lenLine, lenLine + (cs.length() % lenLine));
                            lines.set(j, cs.substring(0, lenLine));
                            String prevReplace = ((String) lines.get(j - 1)).substring(0, lastSpace);
                            lines.set(j - 1, prevReplace);
                            lines.add(newS);
                        }
                    }
                }

            }
        }
        for (int i = 0; i < lines.size(); i++) {
            String lineStr = (String) lines.get(i);
            if (lineStr.startsWith(String.valueOf(delim)))
                lineStr = lineStr.substring(1);
            lines.set(i, lineStr);
        }
        return lines;
    }

}
