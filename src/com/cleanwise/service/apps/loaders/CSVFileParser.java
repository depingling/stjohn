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
import com.cleanwise.service.apps.loaders.DelimitedParser;

public class CSVFileParser {
    private ArrayList parsedStrings_ = new ArrayList();
    
    private boolean fileOK(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.length() == 0) {
            return false;
        }
        if (!file.canRead()) {
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
        BufferedReader br = null;
        String tokens[] = null;
        String buf = "";
              
        if (iStream != null) {
            br = new BufferedReader(new InputStreamReader(iStream));
            
            // Parse line
            while ((buf = br.readLine()) != null) {
                if (buf.trim().equals("")) {
                    continue;
                }
				List l = DelimitedParser.parse(buf, ',');
//                tokens = buf.split(",", -1);
				ArrayList tokensList = new ArrayList(l);
//				ArrayList tokensList = l;
//                ArrayList tokensList = new ArrayList();
//                for (int i = 0; i < tokens.length; i++) {    
//                    tokensList.add(tokens[i]);
//                }
                parsedStrings_.add(tokensList); 
            }
            br.close();
        } else {
            throw new IOException("Error while parsing. (Input stream is NULL)");
        }
    }
    
    public List cleanUpResult() {
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
        if (parsedStrings_ != null) {
            int size = parsedStrings_.size();
            for (int i = 0; i < size; i++) {
                pListener.parseLine((ArrayList)parsedStrings_.get(i));
            }
        } else {
            throw new IOException("Error while processing parsed strings. (No parsed strings found)");
        }
    }
    
    public List getResultAsColumns() {
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

