/*
 * Inbound810Checker.java
 *
 * Created on November 13, 2003, 10:17 AM
 */

package com.cleanwise.service.apps.edi;

import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.ClientServicesAPI;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
/**
 * Checks the file System for invoices in an edi file and will check that they exist in the database.
 * @author  bstevens
 */
public class Inbound810Checker extends ClientServicesAPI{
    //add distributors here you are interested in.  Leave empty if you want to check everything
    //filtering only works for complete trading partner references.  I.e. all distributors related
    //to a trading partners.

    private static final Logger log = Logger.getLogger(Inbound810Checker.class);

    public static ArrayList distFilterList = new ArrayList();
        static {
            //distFilterList.add(new Integer(87117));
        }
    //directory to scan for EDI files
    private static File baseDir;
    
    private static File copyBadFilesToDir;
    
    private static String INTERCHANGE_SQL = "SELECT tpa.bus_entity_id, tpp.interchange_receiver FROM "+
    "CLW_TRADING_PARTNER tp, CLW_TRADING_PROFILE tpp, CLW_TRADING_PARTNER_ASSOC tpa "+
    "WHERE tp.trading_partner_id = tpp.trading_partner_id AND tp.trading_partner_id = tpa.trading_partner_id";
    
    
    private boolean debug = false;
    private Connection con; //the connection
    private HashMap distMap; //hashmap of edi identifiers to dist ids
    private ArrayList missedInvoice = new ArrayList();
    private ArrayList notMissedInvoice = new ArrayList();
    private boolean filtering;
    private HashMap distributorInvoiceMap = new HashMap();
    

    // Filter class is a file name is specified.
    class CheckFileChooser implements FileFilter {
	public boolean accept(java.io.File f) {
	    if (null == mFilter || mFilter.length() == 0 ) {
		return true;
	    }
	    return f.getName().indexOf(mFilter) > -1 ;
	}

	private String mFilter = null;
	public CheckFileChooser(String pFilter) {
	    mFilter = pFilter;
	}
    }

    /** Creates a new instance of Inbound810Checker */
    public Inbound810Checker(boolean pDebugFlag) {
	debug = pDebugFlag;

        try{
            if(distFilterList.isEmpty()){
                filtering = false;
            }else{
                filtering = true;
            }
            con = getConnection();
            Statement s = con.createStatement();
            s.execute(INTERCHANGE_SQL);
            ResultSet rs = s.getResultSet();
            distMap = new HashMap();
            while(rs.next()){
                String receiver = rs.getString("interchange_receiver");
                List l = (List) distMap.get(receiver);
                if(l == null){
                    l = new ArrayList();
                    distMap.put(receiver,l);
                }
                l.add(new Integer(rs.getInt("bus_entity_id")));
            }
            File[] files = null;

	    log.debug(" List files for " + baseDir.getName());
	    if ( null != filenameFilter && filenameFilter.length() > 0 ) {
		CheckFileChooser fileChooser =
		    new CheckFileChooser(filenameFilter);
		files = baseDir.listFiles(fileChooser);
	    } else {
		files = baseDir.listFiles();
	    }

            for(int i=0;null != files && i<files.length;i++){
		log.debug(" scan file " + files[i].getName());
                scan(files[i]);
            }
            check();
            
            Iterator it = missedInvoice.iterator();
            if(it.hasNext()){
                char delim = '\t';
            }
            while(it.hasNext()){
                Invoice invoice = (Invoice) it.next();
                File toFile = new File(copyBadFilesToDir + "/" + invoice.mFile.getName());
                if(!toFile.exists()){
                    IOUtilities.copyStream(new FileInputStream(invoice.mFile),new FileOutputStream(toFile));
                }
            }
            
            if(debug){
                log.debug("*********************NOT MISSED********************");
                it = notMissedInvoice.iterator();
                while(it.hasNext()){
                    log.debug(it.next());
                }
            }
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    
    /**
     *Scans the file for invoices
     */
    private void scan(File pFile){
        if(!pFile.isFile()){
            return;
        }
        log.debug("Scaning file: "+pFile.getName());
        try{
            BufferedReader rdr = new BufferedReader(new FileReader(pFile));
            String line = rdr.readLine();
            //if there is nothing in the file return
            if(line == null || line.trim().length() == 0){
                return;
            }
            //if this file is not an edi file return
            if(!line.startsWith("ISA")){
                return;
            }
            String distInt = line.substring(35, 50);
            distInt = distInt.trim();
            List distIds = (List) distMap.get(distInt);
            //Integer distId = (Integer) distMap.get(distInt);
            String distIdStr = "";
            if(distIds != null){
            Iterator it = distIds.iterator();
                while(it.hasNext()){
                    distIdStr = distIdStr + (Integer) it.next();
                    if(it.hasNext()){
                        distIdStr = distIdStr + ",";
                    }
                }
            }
            char seperator = line.charAt(3);
            if(filtering){
                if(!distFilterList.containsAll(distIds)){
                    //log.debug.println("Skipping file: " +pFile.getName()+" as dist id " +distId+" is not in filter");
                    return;
                }
            }
            List invoices;
            if(distributorInvoiceMap.containsKey(distIdStr)){
                invoices = (List) distributorInvoiceMap.get(distIdStr);
            }else{
                invoices = new ArrayList();
                distributorInvoiceMap.put(distIdStr,invoices);
            }
            String tok810 = "ST"+seperator+"810"+seperator;
            log.debug("Using seperator: "+tok810);
            Character sepChar = new  Character(seperator);
            String seperatorStr = sepChar.toString();
            int tok810Len = tok810.length();
            StringBuffer strBuf = new StringBuffer();
            while(line != null){
                strBuf.append(line);
                line = rdr.readLine();
            }
            String buf = strBuf.toString();

            int startIdx = buf.indexOf(tok810);
            while(startIdx > 0){
                buf = buf.substring(startIdx + tok810Len);
                StringTokenizer tok = new StringTokenizer(buf,seperatorStr);
                tok.nextToken();
                tok.nextToken();
                String invoice = tok.nextToken();
                log.debug("Adding invoice: "+invoice);
                invoice = invoice.trim();
                invoices.add(new Invoice(distIdStr,invoice,pFile));
                startIdx = buf.indexOf(tok810);
            }
                
            
        }catch(Exception e){
            log.info("Exception found for file: "+pFile.getName());
            e.printStackTrace(System.out);
        }
    }
    
    /**
     *Preforms the database interaction to determine if the invoice actually exists in the db
     */
    private void check() throws Exception{
        String sql = "select invoice_num from clw_invoice_dist where invoice_num = ? and add_date between trunc(? -7) and trunc(? +7)";
        PreparedStatement ps = con.prepareStatement(sql);
        String sql2 = "select invoice_num from clw_invoice_dist where invoice_num = ?";
        PreparedStatement ps2 = con.prepareStatement(sql2);
        String sql3 = "select p.clw_value from clw_order_property p, clw_invoice_dist id where id.bus_entity_id = ? and p.clw_value like ? and id.invoice_dist_id = p.invoice_dist_id";
        PreparedStatement ps3 = con.prepareStatement(sql3);
        String sql4 = "select clw_value from clw_order_property where clw_value like ? and invoice_dist_id > 0";
        PreparedStatement ps4 = con.prepareStatement(sql4);
        Iterator kit = distributorInvoiceMap.keySet().iterator();
        while(kit.hasNext()){
            String distIdStr = (String) kit.next();
            List invoices = (List) distributorInvoiceMap.get(distIdStr);
            Iterator eit = invoices.iterator();
            while(eit.hasNext()){
                Invoice invoiceObj = (Invoice) eit.next();
                java.sql.Date invoice_Date = new java.sql.Date(invoiceObj.mFile.lastModified());
                String invoice = invoiceObj.mInvoice;
                ResultSet rs = null;
                if(!Utility.isSet(distIdStr)){
                    log.debug("distIdStr is null");
                    ps2.setString(1, invoice);
                    ps2.execute();
                    rs = ps2.getResultSet();
                    boolean found = false;
                    if(!rs.next()){
                        ps4.setString(1, "%ref invoice: "+invoice);
                        ps4.execute();
                        rs = ps4.getResultSet();
                        if(!rs.next()){
                            found = false;
                        }else{
                            found = true;
                        }
                    }else{
                        found = true;
                    }
                    if(found){
                        notMissedInvoice.add(invoiceObj);
                    }else{
                        missedInvoice.add(invoiceObj);
                    }
                }else{
                    log.debug("distIdStr is NOT null: "+distIdStr);
                    StringTokenizer tok = new StringTokenizer(distIdStr,",");
                    boolean found = false;
                    while(tok.hasMoreTokens()){
                        int distId = Integer.parseInt(tok.nextToken().trim());
                        ps.setString(1, invoice);
                        ps.setDate(2, invoice_Date);
                        ps.setDate(3, invoice_Date);
                        ps.execute();
                        rs = ps.getResultSet();
                        if(rs.next()){
                            found = true;
                            break;
                        }else{
                            ps3.setInt(1, distId);
                            ps3.setString(2, "%ref invoice: "+invoice);
                            ps3.execute();
                            rs = ps3.getResultSet();
                            if(rs.next()){
                                found = true;
                            }
                        }
                    }
                    if(!found){
                        missedInvoice.add(invoiceObj);
                    }else{
                        notMissedInvoice.add(invoiceObj);
                    }
                }
                if(rs != null){
                    rs.close();
                }
                
            }
            
        }
    }
    
    
    private static String filenameFilter = null;
    
    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String baseDirStr = System.getProperty("processDir");
        String badDirStr = System.getProperty("problemDir");
        filenameFilter = System.getProperty("checkFile");

	boolean debug = false;
	String tdbg = System.getProperty("debug");
	if ( null != tdbg && tdbg.equals("y")) {
	    debug = true;
	}
        if((baseDirStr == null || baseDirStr.trim().length() == 0)){
            baseDirStr = ".";
        }
        if((badDirStr == null || badDirStr.trim().length() == 0)){
            badDirStr = baseDirStr + "/bad";
        }
        copyBadFilesToDir = new File(badDirStr);
        baseDir = new File(baseDirStr);
        if(!copyBadFilesToDir.exists()){
            copyBadFilesToDir.mkdir();
        }
        new Inbound810Checker(debug);
    }
    
    /**
     *Representation of an invoice
     */
    private class Invoice{
        String mInvoice;
        //Integer mDistId;
        String mDistIdStr;
        File mFile;
        Invoice(String pDistIdStr,String pInvoice, File pFile){
            mDistIdStr = pDistIdStr;
            mInvoice = pInvoice;
            mFile = pFile;
        }
        
        public String toString(){
            char delim = '\t';
            if(mFile == null){
                return "unavailable" + delim + "unavailable" + delim + "[" + mDistIdStr + "]" + delim + mInvoice;
            }else{
                java.util.Date lastMod = new java.util.Date(mFile.lastModified());
                SimpleDateFormat f = new SimpleDateFormat();
                
                return f.format(lastMod) + delim + mFile.getName() + delim + "[" + mDistIdStr + "]" + delim + mInvoice;
            }
        }
    }
}
