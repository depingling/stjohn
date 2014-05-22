
package com.cleanwise.view.utils;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import javax.servlet.http.HttpServletRequest;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * String-handling utilities available to all applications.
 */
public class StringUtils {


  /**
   * Takes a delimited string and returns an array of strings, one for
   * each token.
   *
   * For example, with these inputs:
   *   input     = "MAINE|VERMONT|TEXAS"
   *   delimiter = "|"
   *
   * The output array would be:
   *   result = {"MAINE", "VERMONT", "TEXAS"}
   *
   * @param input
   *  a string containing tokens and delimiters
   * @param delimiter
   *  a list of possible characters that separate tokens within the
   *   input string
   */
  public static String[] parseDelimitedString(
         String input,
         String delimiters) {

    if (input == null || delimiters == null || delimiters.equals(""))
        return null;

    input = input.trim();
    if (input.length() > 0) {
      StringTokenizer st = new StringTokenizer(input, delimiters);
      String[] tokens = new String[st.countTokens()];
      int count = 0;
      while (st.hasMoreTokens()) {
        tokens[count++] = st.nextToken();
      }
      return (tokens);
    }

    else {
      return null;
    }

  }


  /**
   * Takes an array of strings and a delimiter string and returns a single
   * string that contains the elements of the array separated by the
   * delimiter value.
   *
   * For example, with these inputs:
   *   tokens[]  = {"MAINE", "VERMONT", "TEXAS"}
   *   delimiter = "|"
   *
   * The output string would be:
   *   "MAINE|VERMONT|TEXAS"
   *
   * @param tokens
   *  an array of strings
   * @param delimiter
   *  a delimiter string that will be placed between tokens when the
   *  output string is built
   */
  public static String buildDelimitedString(
         String[] tokens,
         String delimiter) {

    if (tokens == null || tokens.length < 1) return "";

    StringBuffer buffer = new StringBuffer();
    int count = tokens.length;
    for (int i = 0; i < count; i++) {
      if (i > 0) buffer.append(delimiter);
      buffer.append(tokens[i]);
    }

    return (buffer.toString());

  }


    /**
     * Replaces some text from the string with another text
     *
     * For example, with these inputs:
     *   input     = "MAINE tt VERMONT tt TEXAS"
     *   replace = "tt"
     *   with = "dd"
     *
     * The output String would be:
     *   result = "MAINE dd VERMONT dd TEXAS"
     *
     * @param str
     *  a string containing the text need be replaced
     * @param replace
     *  the text need be replaced
     * @param with
     *  the text need be used to replace
     *
     */
    public static String replaceString(
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

    // helper method to render strings without having to
    // worry about the null string being displayed.
    public static String toNonNull( String str ) {
    if (null == str) return "";
    return str;
    }


    //parsing ecxeption if message format is ^clw^Mesage^clw^
     public static ActionErrors  getUiErrorMess(Exception e) throws Exception {
        int ind1 = -1;
        int ind2 = -1;
        String errorMessage=e.getMessage();
        if(errorMessage==null) throw e;
        ind1 = errorMessage.indexOf("^clw^");
        if(ind1>=0) {
            ind2 = errorMessage.indexOf("^clw^", ind1+3);
            if(ind2>0) {
                errorMessage = errorMessage.substring(ind1+5,ind2);
                ActionErrors aErrors = new ActionErrors();
                aErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.simpleGenericError",errorMessage));
                return aErrors;
            }

        }
        throw e;
      }
     
    public static String getUiErrorMessage(Exception e) throws Exception {
        if (e != null) {
            return getUiErrorMessage(e.getMessage());
        }
        return "";
    }
    
    public static String getUiErrorMessage(String errorMessage) throws Exception {
        int ind1 = -1;
        int ind2 = -1;
        if (errorMessage != null) {
            ind1 = errorMessage.indexOf("^clw^");
            if (ind1 >= 0) {
                ind2 = errorMessage.indexOf("^clw^", ind1+3);
                if (ind2 > 0) {
                    return errorMessage.substring(ind1+5, ind2);
                }
            }
        }
        return "";
    }

    public static String prepareUIMessage(HttpServletRequest request, Exception e) throws Exception {
        if (e != null) {
            return prepareUIMessage(request, e.getMessage());
        }
        return "";
    }
    
    // parses exception:
    // in case the message format is ^clw^Mesage^clw^ calls getUiErrorMess()
    // or on case the message contains tags ^clwKey^Key^clwKey^ or ^clwParam^Params^clwParam^ calls ClwI18nUtil.formatEjbError() 
    public static String prepareUIMessage(HttpServletRequest request, String errorMessage) throws Exception {
        if (errorMessage != null) {
            if (errorMessage.indexOf("^clw^") != -1) {
                return getUiErrorMessage(errorMessage);
            } else if ((errorMessage.indexOf("^clwKey^") != -1) ||
                       (errorMessage.indexOf("^clwParam^") != -1)) {
                return ClwI18nUtil.formatEjbError(request, errorMessage);
            } else {
                return errorMessage;
            }
        }
        return "";
    }

    public static ArrayList parseDelim(String s, char delim, int lenLine) {
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

    public static ActionErrors validatePoNumFourDigits (HttpServletRequest request, String pPoNum) {
      ActionErrors ae = new ActionErrors();
      if(Utility.isSet(pPoNum)) {
        char[] poNumChA = pPoNum.trim().toCharArray();
        if(poNumChA.length < 4) {
          String errorMess =
            ClwI18nUtil.getMessage(request, "shop.errors.poNumberMustMustStartWithFourDigits", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
              for(int ii=0; ii<4; ii++) {
          char cc = poNumChA[ii];
          if(cc<'0'||cc>'9') {
            String errorMess =
              ClwI18nUtil.getMessage(request, "shop.errors.poNumberMustMustStartWithFourDigits", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }
              }
      } else {
        String errorMess =
          ClwI18nUtil.getMessage(request, "shop.errors.requestPoNumberRequered", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;

          }
      return ae;

    }

    public static String escapeHtml(String pStr) {
        if(pStr==null) return null;
        String str =
                StringEscapeUtils.escapeHtml(pStr).replaceAll("\r\n|\r|\n","<br>");
        return str;
    }

    public static ArrayList parseActionErrors(HttpServletRequest request) {
        return parseActionErrors(request, "Error");
    }

    public static ArrayList parseActionErrors(HttpServletRequest request, String key) {
        ArrayList errors = new ArrayList();
        ActionErrors ae =
                (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
        if (ae != null && ae.size() > 0) {
            for (Iterator iterProp = ae.properties(); iterProp.hasNext();) {
                String aa = (String) iterProp.next();
                if (key.equalsIgnoreCase(aa)) {
                    for (Iterator iterAE = ae.get(aa); iterAE.hasNext();) {
                        ActionError mess = (ActionError) iterAE.next();
                        Object[] values = mess.getValues();
                        for (int ii = 0; ii < values.length; ii++) {
                            if (values[ii] instanceof String) {
                                errors.add(values[ii]);
                            }
                        }
                    }
                }
            }
        }
        return errors;
    }
    /* alignment numerical strings for using in sort methods
     * leading 0 will be added in front of string with less length
     * @param value - numerical
     * @param zeroCount - count of zeros to be added
   */
    public static String fillingZero (String value, int zeroCount) {
      String result = value;
       if (Utility.parseInt(value)!=0  && zeroCount < 0) {
         char[] cc = new char[Math.abs(zeroCount)];
         for (int i = 0; i < Math.abs(zeroCount); i++) {
           cc[i] = '0';
         }
         String s0 = String.valueOf(cc);
         result = s0 + value;
       }
       return result;
     }


     /**
     * Will place a Desc at the end of a sort order if this was the last sort that
     * was done...this allows for ascending then decending sorts.
     */
    public static String getSortField(String sortField, HttpServletRequest request){
    	if(sortField.equals(request.getParameter("sortField"))){
    		return sortField+"Desc";
    	}
        return sortField;
    }

    /**
     * Replace the all single and double quotes in the string by proper html-keys.
     */
    public static String encodeHtmlSingleAndDoubleQuotes(String stringToEncode) {
        if (stringToEncode == null || stringToEncode.length() == 0) {
            return stringToEncode;
        }
        String encodedString = stringToEncode;
        if (encodedString.indexOf("'") >= 0) {
            encodedString = encodedString.replaceAll("'", "&#39");
        }
        if (encodedString.indexOf("\"") >= 0) {
            encodedString = encodedString.replaceAll("\"", "&quot;");
        }
        return encodedString;
    }

    /**
     * Extract the last token divided by specified delimiter.
     */
    public static String extractLastDelimitedString(String input, String delimiters) {
        if (input == null || delimiters == null) {
            return input;
        }
        if (input.length() == 0 || delimiters.length() == 0) {
            return input;
        }
        if (input.length() == delimiters.length()) {
            return input;
        }
        if (input.length() < delimiters.length()) {
            return "";
        }
        String result = input;
        int pos = input.lastIndexOf(delimiters);
        if (pos >= 0) {
            result = input.substring(pos + delimiters.length(), input.length());
        }
        return result;
    }

    /**
     * Extract the file name only from string with full name of file.
     */
    public static String extractFileName(String fullPath) {
        if (fullPath == null) {
            return fullPath;
        }
        String result = extractLastDelimitedString(fullPath, "/");
        result = extractLastDelimitedString(result, "\\");
        return result;
    }
    public static int getDecimalPoints(String valueS){
      int decPoints = 0;
      String delimiter = ".";
      String scaleS = "";
      if (Utility.isSet(valueS) ){
        int pos = valueS.lastIndexOf(delimiter);
        if (pos >= 0) {
            scaleS = valueS.substring(pos + delimiter.length(), valueS.length());
        }
      }
      return scaleS.length();
    }
}

