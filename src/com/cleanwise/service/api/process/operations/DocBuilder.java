package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class DocBuilder {

    public BufferedOutputStream os;
    public File file;
    public static final int CENTER = 0;
    public static final int RIGTH = 1;
    public static final int LEFT = 2;
    boolean viewPageFlag=false;
    public static final int TABLE_COLUMN_SPACE = 2 ;

    public static int DOC_LENGTH = 69;//for msword
    private int LINE_COUNT_PAGE = 60;  //for msword

    public StringBuffer line=new StringBuffer();
    public final int MSWORD=1;
    public final int WORDPAD=2;
    public static final int NOTEPAD =3 ;


    private int lineCount=-1;
    public int pageNumber=0;
    public String className="DocBuilder";
    private static final int MAX_LINES = 10000;

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
        /*
        if (viewPageFlag&&(lineCount % LINE_COUNT_PAGE == 0)) {
            StringBuffer tempLine = line;
            line = new StringBuffer();
            pageNumber++;
            writeTitle(sb);
            line = new StringBuffer();
            line = tempLine;
        }
        */
        /*
        if (line.length() > DOC_LENGTH) {
            ArrayList lines = parseDelim(line.toString(), " ", DOC_LENGTH);
            line = new StringBuffer();
            lineCount=lineCount-1;
            writeArray(sb,lines);
        } else {
         */
            sb.append(line);
            sb.append("\r\n");
            line = new StringBuffer();
            /*
        }
             */
    }

    private void writeArray(StringBuffer sb, ArrayList array) {
        Iterator it = array.iterator();
        while (it.hasNext()) {
            line.append(it.next());
            nextLine(sb);
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

     public void setUpOutputStream(String fileName) throws FileNotFoundException {
       os = new BufferedOutputStream(new FileOutputStream(fileName));
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

    public ArrayList parseDelim(String s, String delimStr, int lenLine) {
        ArrayList lines = new ArrayList();
        if(s==null) {
            lines.add("");
            return lines;
        }
        //s=deleteFirstSpaces(s,delimStr);
        s = removeExtraSpaces(s);
        if(delimStr==null) {            
            int partCount = s.length() / lenLine;
            int lastPartLen = s.length() % lenLine;
            for (int i = 0; i <= partCount; i++) {
                int len = ((i * lenLine) + (i == (partCount) ? lastPartLen : lenLine));
                if (len > 0) {
                    String line = s.substring(i * lenLine, len);
                    if (line.trim().length() > 0) {
                        lines.add(line);
                    }
                }
            }
        } else {
            char delim = delimStr.charAt(0);   //if the future could be modified to use more than 1 delimeter         
            char[] strA = s.toCharArray();
            int ii1 = 0;
            int ii2 = 0;
            int ll = 0;
            for(int ii=0; ii<strA.length; ii++) {
                char cc = strA[ii];
                if(ll==0) {
                    ii1 = ii;
                    ii2 = ii;
                    ll = 1;
                } else {
                   ll++;
                }
                if(cc==delim) {
                    ii2 = ii;
                }
                if(ll>=lenLine) {
                    String line;
                    if(ii2 > ii1) {
                        line = new String (strA, ii1, ii2-ii1);
                        ii = ii2;
                    } else {
                        line = new String (strA, ii1, lenLine);
                    }
                    ll = 0;
                    if(line.trim().length()>0) {
                        lines.add(line);
                    }
                }
            }
            if(lines.isEmpty()) {
                lines.add(s);
            } else {
                if(ii2!=0 && ii2<strA.length) {
                    String line = new String( strA,ii2, (strA.length-ii2));
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    private String removeExtraSpaces(String s) {
        if (s == null) return s;
        char[] strA = s.toCharArray();
        char[] str1A = new char[strA.length];
        int ll = 0;
        int spaceQty = 0;
        for (int ii = 0; ii < strA.length; ii++) {
            if(strA[ii]==' ') {
                spaceQty++;
                if(spaceQty > 1 || ii==0) {
                    continue;                   
                }
            } else {
                spaceQty = 0;
            }   
            str1A[ll++] = strA[ii];
        }
        String retstr = (ll==0)? "":(new String(str1A,0,ll));
        return retstr ;
    }

    private String deleteFirstSpaces(String s, String delimStr) {
        if (s != null && s.length() > 0&&delimStr != null) {
            char delim = 0;
            if (delimStr.length() > 0) {
                delim = delimStr.toCharArray()[0];
            }
            int j = 0;
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == delim) {
                    j++;
                } else {
                    break;
                }
            }
            s = s.substring(j);
        }
        return s;
    }

    public void writeArrayAsTableStyle(ArrayList colsArray, StringBuffer sb, PairViewVector columnParam, int alignTable) {
          int maxSize = 0;
          Iterator it1;
          // gets max size of the array from the all list
          it1 = colsArray.iterator();
          while (it1.hasNext()) {
              int size = ((List) it1.next()).size();
              if (size > maxSize) {
                  maxSize = size;
              }
          }
          String[] tableLines = new String[maxSize];

          for (int i = 0; i < tableLines.length; i++) {
              tableLines[i] = "";
          }

          it1 = colsArray.iterator();
          for (int j = 0; it1.hasNext(); j++) {
              Iterator it2 = ((List) it1.next()).iterator();
              for (int i = 0; i < maxSize; i++) {
                  if (it2.hasNext()) {
                      String s = (String) it2.next();
                      tableLines[i] += normalizeString(s,' ',
                              ((Integer)((PairView) columnParam.get(j)).getObject1()).intValue(),
                              ((Integer)((PairView) columnParam.get(j)).getObject2()).intValue());
                  } else {
                      tableLines[i] += getCharLine(' ', ((Integer)((PairView) columnParam.get(j)).getObject1()).intValue());
                  }

              }
          }

          for (int i = 0; i < tableLines.length; i++) {
              line.append(align(tableLines[i],alignTable));
              nextLine(sb);
          }

      }
    

}
