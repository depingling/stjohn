package com.cleanwise.service.apps.loaders;

import com.cleanwise.service.apps.dataexchange.StreamedInboundTransaction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;


public class TabFileParser {
    private ArrayList parsedStrings_ = new ArrayList();
    private static final Logger log = Logger.getLogger(TabFileParser.class);
    
    private boolean fileOK(File file) {
        if (!file.exists()) {
            log.info("The input file: (" + file.getPath() + ") doesn't exist.");
            return false;
        }
        if (file.length() == 0) {
            log.info("The input file: (" + file.getPath() + ") is empty.");
            return false;
        }
        if (!file.canRead()) {
            log.info("The input file: (" + file.getPath() + ") can't be read.");
            return false;
        }
        return true;
    }
    
    public void parse(String fileName) throws IOException {
        try {
            File file = new File(fileName);
        
            if (fileOK(file)) {
                parse(new FileInputStream(file));
            } else {
                throw new IOException("File is not OK.");
            }
        } catch (NullPointerException e) {
            throw new IOException("File name is NULL.");
        } catch (FileNotFoundException e) {
            throw new IOException("File not found.");
        } catch (SecurityException e) {
            throw new IOException("Security violation while attempting to read file.");
        }
    }
    
    public void parse(File file) throws IOException {
        try {
            if (fileOK(file)) {
                parse(new FileInputStream(file));
            } else {
                throw new IOException("File is not OK.");
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found.");
        } catch (SecurityException e) {
            throw new IOException("Security violation while attempting to read file.");
        }
    }
    
    public void parse(InputStream iStream) throws IOException {
    	parse(iStream, null);
    }
    
    public void parse(InputStream iStream, String encoding) throws IOException {
        log.info("[Tab Delimeted Parser] Parsing strings.");        
        BufferedReader br = null;
        String tokens[] = null;
        String buf = "";
              
        if (iStream != null) {
        	if(encoding != null){
        		log.info("Encoding specified: "+encoding);
        		br = new BufferedReader(new InputStreamReader(iStream,encoding));
        	}else{
        		br = new BufferedReader(new InputStreamReader(iStream));
        	}
            // Parse line
            while ((buf = br.readLine()) != null) {
                if (buf.trim().equals("")) {
                    continue;
                }
                tokens = buf.split("\t", -1);
                ArrayList tokensList = new ArrayList();
                for (int i = 0; i < tokens.length; i++) {    
                    tokensList.add(tokens[i]);
                }
                parsedStrings_.add(tokensList); 
            }
            br.close();
        } else {
            throw new IOException("Error while parsing. (Input stream is NULL)");
        }
    }
    
    public List cleanUpResult() {
        log.info("[Tab Delimeted Parser] Cleaning up result.");
        Hashtable weightTable = null;
        ArrayList result = null;
        
        if (parsedStrings_ != null) {
            weightTable = new Hashtable();
            result = new ArrayList();
            int size = parsedStrings_.size();
            int len;
            for (int i = 0; i < size; i++) {
                Integer weight;
                Integer length = new Integer(((List)parsedStrings_.get(i)).size());
                if ((weight = (Integer)weightTable.get(length)) == null) {
                    weightTable.put(length, new Integer(1));
                } else {
                    weightTable.put(length, new Integer(weight.intValue() + 1));
                }
            }

            int max = 0;
            int key = 0;
            Enumeration keys = weightTable.keys();
            while(keys.hasMoreElements()) {
                int value;
                Integer objKey = (Integer)keys.nextElement();

                if ((value = ((Integer)weightTable.get(objKey)).intValue()) > max) {
                    max = value;
                    key = objKey.intValue();
                }
            }

            for (int i = 0; i < size; i++) {
                ArrayList row = (ArrayList)parsedStrings_.get(i);
                if (row.size() == key) {
                    result.add(row);
                }
            }
            parsedStrings_ = result;
        }
        return result;
    }
    
    public void processParsedStrings(StreamedInboundTransaction pListener) throws Exception {
        log.info("[Tab Delimeted Parser] Processing parsed strings.");
        if (parsedStrings_ != null) {
            int size = parsedStrings_.size();
            for (int i = 0; i < size; i++) {
                pListener.parseLine((ArrayList)parsedStrings_.get(i));
            }
            log.info("[Tab Delimeted Parser] Done processing parsed strings.");
        } else {
            throw new IOException("Error while processing parsed strings. (No parsed strings found)");
        }
    }
    
    public List getResultAsColumns() {
        log.info("[Tab Delimeted Parser] Getting parsed strings as columns.");
        ArrayList result = null;
        ArrayList temp = null;
        
        temp = (ArrayList)cleanUpResult();
        if (temp != null) {
            if (temp.size() > 0) {
                result = new ArrayList();
                
                int x = temp.size();
                int y = ((List)temp.get(0)).size();
                
                
                for (int j = 0; j < y; j++) {
                    ArrayList column = new ArrayList();
                    for (int i = 0; i < x; i++) {
                        column.add(((ArrayList)temp.get(i)).get(j));
                    }
                    result.add(column);
                }
            }
        }
        return result;
    }
    
    public List getParsedStrings() {
        return parsedStrings_;
    }
    
}
