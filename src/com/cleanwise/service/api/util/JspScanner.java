package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.PairView;

import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 */
public class JspScanner {

    private static final Logger log = Logger.getLogger(JspScanner.class);

    private BigDecimal id = new BigDecimal(100000);

    private static final String SPACE = " ";
    private static final String ID = "ID";
    private static final String STYLE_ID = "styleId";
    private ArrayList fileArray = null;
    private StringBuffer contentConfig;
    private String configFile;

    private static final Collection TAGS = new ArrayList(8);
    private static final String[] HTML_TAGS_STR = new String[]{"IMG", "TABLE", "A", "FORM"};
    private static final String[] STRUTS_TAGS_STR = new String[]{"HTML:IMG", "HTML:FORM"};

    public JspScanner(String configFile) throws IOException {
        this.fileArray = new ArrayList();
        this.configFile = configFile;

        getParamsFromConfig(configFile);

        TAGS.addAll(Arrays.asList(HTML_TAGS_STR));
        TAGS.addAll(Arrays.asList(STRUTS_TAGS_STR));
    }

    /**
     * Get params from config file
     * Params should be presented as:
     * a1,a2,a3;n
     * a1-an - the name of JSP files
     * n - current number of ID
     *
     * @param confFile configuration File
     * @throws IOException if any troubeks whith the file content processing
     */
    private void getParamsFromConfig(String confFile) throws IOException {

        contentConfig = getContent(confFile);
        String jspFiles;
        String id;

        StringTokenizer st = new StringTokenizer(contentConfig.toString(), ";");
        if (st.countTokens() == 2) {
            jspFiles = st.nextToken();
            id = st.nextToken();
        } else {
            log.info("getParamsFromConfig() => Error during the getting the params from config File");
            return;
        }

        //get list of JSP's
        fileArray = getJspList(jspFiles);
        //get current ID
        this.id = new BigDecimal(id.trim());
    }

    private StringBuffer getContent(String confFile) throws IOException {
        File file = new File(confFile);
        return getContent(file);
    }


    public ArrayList getJspList(String jspFiles) {
        String[] inFileArray = Utility.parseStringToArray(jspFiles, ",");
        File[] files = new File[inFileArray.length];
        for (int i = 0; i < inFileArray.length; i++) {
            files[i] = new File(inFileArray[i]);
        }
        return getJspList(files, new ArrayList());
    }

    private ArrayList getJspList(File[] files, ArrayList fileArray) {
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fileArray.addAll(getJspList(files[i].listFiles(new JspFileFilter()), new ArrayList()));
            } else {
                fileArray.add(files[i]);
            }
        }
        return fileArray;
    }

    public void updateConfigParams() throws IOException {
        int idx = contentConfig.indexOf(";");
        contentConfig.replace(idx + 1, contentConfig.length(), this.id.toString());
        rewriteFile(configFile, contentConfig);
    }

    public void scan() throws IOException {

        if ((this.fileArray == null) || (this.fileArray.size() == 0)) {
            log.info("scan() => JSP files collection is empty");
        } else {

            for (int i = 0; i < this.fileArray.size(); i++) {

                log.info("scan() => START Scan JSP = " + this.fileArray.get(i));

                try {
                    //get Content from file
                    StringBuffer content = getContent((File) this.fileArray.get(i));
                    //parse file
                    this.parse(content);
                    //rewrite Content in JSP to parsed content
                    this.rewriteFile((File) this.fileArray.get(i), content);

                    log.info("scan() => FINISH Scan JSP = " + this.fileArray.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            updateConfigParams();
        }

    }

    private void rewriteFile(File file, StringBuffer content) throws IOException {
        log.info("rewriteFile() => BEGIN file=" + file);

        BufferedWriter bos = new BufferedWriter(new FileWriter(file));
        bos.write(new String(content));
        bos.flush();
        bos.close();

        log.info("rewriteFile => END");
    }

    private void rewriteFile(String fileName, StringBuffer content) throws IOException {
        File file = new File(fileName);
        rewriteFile(file, content);
    }

    private void parse(StringBuffer content) throws IOException {
        log.info("parse() => BEGIN");

        String str = content.toString();

        int cursor = 0;
        int beginElIdx;
        int endElIdx = 0;

        int countParsedElements = 0;

        while (true) {

            str = str.substring(endElIdx);

            PairView pos = getNextTagPosition(str);

            beginElIdx = ((Integer) pos.getObject1()).intValue();
            if (beginElIdx == -1) {
                break;
            }

            endElIdx = ((Integer) pos.getObject2()).intValue();
            if (endElIdx == -1) {
                break;
            }

            String elContent = str.substring(beginElIdx, endElIdx);

            PairView parsedElemnt = parseElement(elContent);
            if (parsedElemnt != null) {

                String name = (String) parsedElemnt.getObject1();
                String params = (String) parsedElemnt.getObject2();

                if (isTagSupport(name)) {
                    if (!Utility.isSet(params) || !hasId(parsedElemnt)) {
                        BigDecimal id = getNextId();
                        String insertStr = SPACE + (isStruts(name) ? STYLE_ID : ID) + "="+(isStruts(name)?"\"":"")+ id +(isStruts(name)?"\"":"");
                        content.insert(cursor + beginElIdx +name.length(), insertStr);
                        cursor += insertStr.length();
                        countParsedElements++;
                    }
                }                                                            
            }

            endElIdx = endElIdx + 1;
            cursor += endElIdx;

        }

        log.info("parse() => END count of parsed tags is " + countParsedElements + ".Last Id: " + this.id);
    }

    private boolean isStruts(String name) {
        return Arrays.asList(STRUTS_TAGS_STR).contains(name.trim().toUpperCase());
    }

    private boolean hasId(PairView parsedElement) {
        String params = (String) parsedElement.getObject2();
        String name = (String) parsedElement.getObject1();
        if (Utility.isSet(params)) {
            int idIdx = getIdIdx(name, params, 0);
            if (idIdx >= 0) {
                return true;
            }
        }
        return false;
    }

    private int getIdIdx(String name, String params, int begIdx) {
        int idIdx = params.toUpperCase().indexOf((isStruts(name) ? STYLE_ID : ID).toUpperCase(), begIdx);
        if (idIdx >= 0) {
            if (idIdx == 0 || (idIdx > 0 && Character.isWhitespace(params.charAt(idIdx - 1)) && !isExp(params, 0, idIdx))) {
                int eqAtt = params.indexOf("=", idIdx);
                if (eqAtt > idIdx) {
                    String idStr = params.substring(idIdx, eqAtt).toUpperCase();
                    if (!idStr.replaceAll(SPACE, "").equals((isStruts(name) ? STYLE_ID : ID).toUpperCase())) {
                        idIdx = getIdIdx(name, params, idIdx + 1);
                    }
                } else {
                    idIdx = getIdIdx(name, params, idIdx + 1);
                }
            } else {
                idIdx = getIdIdx(name, params, idIdx + 1);
            }
        }
        return idIdx;
    }


    private boolean isExp(String str, int bIdx, int eIdx) {
        String s = str.substring(bIdx, eIdx);
        boolean expCd34 = false;// "
        boolean expCd39 = false;// '
        boolean expCd92 = false;// /
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == 34 && !expCd39) {
                expCd34 = !expCd34;
            } else if (ch == 39 && !expCd34) {
                expCd39 = !expCd39;
            }
        }
        return expCd34 || expCd39;
    }


    public PairView getNextTagPosition(String str) throws IOException {

        int fbIdx = getNextBeginIndex(str);
        int feIdx = getEndIndexOf(str, fbIdx);

        return new PairView(new Integer(fbIdx), new Integer(feIdx));
    }

    private int getEndIndexOf(String str, int begIdx) {
        int curBegIdx = begIdx;
        int curEndIdx = -1;
        if (begIdx >= 0) {
            while (true) {
                curEndIdx = str.indexOf(">", curBegIdx);
                if (curEndIdx > 0 && (isExp(str, begIdx, curEndIdx) || str.charAt(curEndIdx - 1) == '%')) {
                    curBegIdx = curEndIdx + 1;
                } else {
                    break;
                }
            }
        }
        return curEndIdx;
    }

    public int getNextBeginIndex(String s) throws IOException {
        int b;
        boolean tagAttrOpen = false;
        String tagName = "";
        int idx = -1;
        int i = 0;
        ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
        while ((b = bais.read()) != -1) {
            char ch = (char) b;
            if (ch == '<') {
                tagAttrOpen = true;
            } else {
                if (tagAttrOpen) {
                    if (Character.isLetter(ch) || ch == ':' || Character.isWhitespace(ch) || ch == '>') {
                        if (Utility.isSet(tagName) && (Character.isWhitespace(ch) || ch == '>')) {
                            if (isTagSupport(tagName)) {
                                idx = i - tagName.length();
                                break;
                            } else {
                                tagAttrOpen = false;
                                tagName = "";
                            }
                        } else if (Character.isLetter(ch) || ch == ':') {
                            tagName += ch;
                        } else {
                            log.info("getNextBeginIndex => Error in html sytax. Invalid symbol(" + b + ") has been ignored");
                        }
                    } else {
                        tagAttrOpen = false;
                        tagName = "";
                    }
                }
            }
            i++;
        }
        bais.close();
        return idx;
    }

    /**
     * Checks the tag existing in collection
     *
     * @param name the name of the tag from a file
     * @return true if tag exists in collection
     */
    private boolean isTagSupport(String name) {
        return TAGS.contains(name.trim().toUpperCase());
    }

    private BigDecimal getNextId() {
        this.id = this.id.add(new BigDecimal(1));
        return this.id;
    }

    private PairView parseElement(String elContent) {
        PairView parsedElement = null;
        log.info("parseElement() => BEGIN");

        log.info("parseElement() => elContent=" + elContent);
        elContent = ignoreFirstSpaces(elContent);

        if (Character.isLetter((elContent.charAt(0)))) {
            int idx = elContent.indexOf(SPACE);
            if (idx >= 0) {
                String elName = elContent.substring(0, idx);
                String elParametersString = elContent.substring(idx + 1, elContent.length());
                parsedElement = new PairView(elName, elParametersString);
            } else {
                parsedElement = new PairView(elContent, "");
            }
            log.info("parseElement => parsedElement: " + parsedElement);

        }

        log.info("parseElement => END");
        return parsedElement;
    }


    private String ignoreFirstSpaces(String elContent) {

        if (elContent.startsWith(SPACE)) {
            elContent = ignoreFirstSpaces(elContent.substring(1));
        }

        return elContent;
    }

    private StringBuffer getContent(File file) throws IOException {

        StringBuffer content = new StringBuffer();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        int b;

        while ((b = bis.read()) != -1) {
            content.append((char) b);
        }
        bis.close();

        return content;
    }


    class JspFileFilter implements FilenameFilter {
        public boolean accept(File pDir, String pFilename) {
            return pFilename.endsWith(".jsp") || pFilename.endsWith(".JSP") || new File(pDir.getAbsolutePath()+File.separator+pFilename).isDirectory();
        }
    }

    public static void main(String argv[]) {
        JspScanner scanner;

        try {
            scanner = new JspScanner(System.getProperty("scanConf"));
            scanner.scan();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

