/*
 * DistributorInvoiceNumTool.java
 *
 * Created on March 29, 2004, 3:02 PM
 */

package com.cleanwise.service.api.util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
/**
 *
 * @author  bstevens
 */
public class DistributorInvoiceNumTool {
    static final String WILDCARD = "*";
    
    /** Creates a new instance of DistributorInvoiceNumTool */
    public DistributorInvoiceNumTool() {
    }
    
    public boolean isAModifiedInvoiceNumber(String distInvoiceNumModifier,String pInvoice){
        if(distInvoiceNumModifier.endsWith(WILDCARD)){
            distInvoiceNumModifier = distInvoiceNumModifier.substring(0,distInvoiceNumModifier.length() -1);
            return pInvoice.startsWith(distInvoiceNumModifier);
        }else if(distInvoiceNumModifier.startsWith(WILDCARD)){
            distInvoiceNumModifier = distInvoiceNumModifier.substring(1);
            return pInvoice.endsWith(distInvoiceNumModifier);
        }
        return pInvoice.endsWith(distInvoiceNumModifier);
    }
    
    /**
     *Splits the invoice into its 3 parts, the main invoice number, the modifier, and the numeric incremt
     *if the invoice does not have all these parts the invoice number is the only part that is returned
     */
    public ParsedDistributorInvoice splitDistInvoiceIntoSuspectedParts(String pInvoiceNum, DistInvoiceModifierDefinition pDistInvoiceNumDef){
        return new ParsedDistributorInvoice(pDistInvoiceNumDef,pInvoiceNum);
    }
    
    /**
     *object in postion 0 is the string which is the distributor modifir value.
     *Object in postion 1 is a Boolean indicating wheather it is a postfix or a prefix.
     *If it is true it is a postfix, otherwise it is a prefix.
     */
    public DistInvoiceModifierDefinition getDistInvoiceNumDef(Connection conn){
        String distInvoiceNumModifier;
        try{
            PropertyUtil pu = new PropertyUtil(conn);
            distInvoiceNumModifier = pu.getProperty(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVOICE_NUM_MODIFIER);
        }catch(Exception e){
            throw new RuntimeException(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVOICE_NUM_MODIFIER +
            " property is not defined");
        }
        if(!Utility.isSet(distInvoiceNumModifier)){
            throw new RuntimeException(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVOICE_NUM_MODIFIER +
            " property is not defined");
        }
        int distInvoiceNumModifierLen = distInvoiceNumModifier.length();
        boolean postfix = true;
        if(distInvoiceNumModifier.endsWith(WILDCARD)){
            postfix = false;
            distInvoiceNumModifier = distInvoiceNumModifier.substring(0,distInvoiceNumModifierLen -1);
        }else if(distInvoiceNumModifier.startsWith(WILDCARD)){
            distInvoiceNumModifier = distInvoiceNumModifier.substring(1);
        }
        distInvoiceNumModifier = distInvoiceNumModifier.trim();
        
        return new DistInvoiceModifierDefinition(postfix, distInvoiceNumModifier);
    }
    
    /**
     *Returns a new invoice number for the supplied invoice and dist id by looking in the database
     *for similar invoices and applied the proper modification to the invoice
     *ABC --> ABC_MOD1 or if ABC_MOD1 exists --> ABC_MOD2 etc
     */
    public String getNewInvoiceNumForDistInvoiceLike(Connection conn, String pInvoice, int pDistId)
    throws SQLException{
        DistInvoiceModifierDefinition def = getDistInvoiceNumDef(conn);
        String distInvoiceNumModifier =  def.getDistInvoiceNumModifier();
        int distInvoiceNumModifierLen = distInvoiceNumModifier.length();
        boolean postfix = def.isPostfix();
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(InvoiceDistDataAccess.BUS_ENTITY_ID, pDistId);
        crit.addBeginsWith(InvoiceDistDataAccess.INVOICE_NUM,pInvoice);
        InvoiceDistDataVector idv = InvoiceDistDataAccess.select(conn, crit);
        int max = 0;
        Iterator it = idv.iterator();
        while(it.hasNext()){
            InvoiceDistData id = (InvoiceDistData) it.next();
            String inv = id.getInvoiceNum();
            String currModifierNum = null;
            if(postfix){
                int lio = inv.lastIndexOf(distInvoiceNumModifier);
                if(lio > 0){
                    currModifierNum = inv.substring(lio + distInvoiceNumModifierLen);
                }
            }else{
                int fio = inv.indexOf(distInvoiceNumModifier);
                if(fio > 0){
                    currModifierNum = inv.substring(0, fio);
                }
            }
            if(currModifierNum != null){
                try{
                    int num = Integer.parseInt(currModifierNum);
                    if(num > max){
                        max = num;
                    }
                }catch(Exception e){
                }
            }
        }//finish looping through invoices
        max++;
        if(postfix){
            return pInvoice + distInvoiceNumModifier + max;
        }else{
            return max + distInvoiceNumModifier + pInvoice;
        }
    }
    
    /**
     *Contains the definition for how a distributor invoice nmber should be treated when dealing
     *with the distributor modifier prefix postfix logic
     */
    public class DistInvoiceModifierDefinition{
        private boolean isPostfix;
        private String distInvoiceNumModifier;
        private DistInvoiceModifierDefinition(boolean pPostfix, String pDistInvoiceNumModifier){
            isPostfix = pPostfix;
            distInvoiceNumModifier = pDistInvoiceNumModifier;
        }
        
        public boolean isPostfix(){
            return isPostfix;
        }
        public String getDistInvoiceNumModifier(){
            return distInvoiceNumModifier;
        }
    }
    
    /**
     *Contains a parsed distributor invoice and does validity checking.  It is possible to have an
     *parsed invoice, so it is important to use the isValid method.
     */
    public class ParsedDistributorInvoice{
        private String invoice;
        private String modifier;
        private Integer numeric;
        
        public String getInvoiceBase(){
            return invoice;
        }
        public String getModifier(){
            return modifier;
        }
        public Integer getNumeric(){
            return numeric;
        }
        
        private ParsedDistributorInvoice(DistInvoiceModifierDefinition def, String pInvoiceNum){
            String myModifier = def.getDistInvoiceNumModifier();
            boolean postfix = def.isPostfix();

            int modLength = myModifier.length();
            String shouldBeModifier = null;
            String shouldBeNumeric = null;
            String shouldBeInvoice = null;
            if(postfix){
                int modPos = pInvoiceNum.lastIndexOf(myModifier);
                if(modPos >= 0){
                    shouldBeModifier = pInvoiceNum.substring(modPos,modPos + modLength);
                    shouldBeNumeric  = pInvoiceNum.substring(modPos + modLength);
                    shouldBeInvoice  = pInvoiceNum.substring(0, modPos);
                }else{
                    shouldBeInvoice  = pInvoiceNum;
                }
            }else{
                int modPos = pInvoiceNum.indexOf(myModifier);
                if(modPos >= 0){
                    shouldBeModifier = pInvoiceNum.substring(modPos,modPos + modLength);
                    shouldBeNumeric  = pInvoiceNum.substring(0,modPos);
                    shouldBeInvoice  = pInvoiceNum.substring(modPos + modLength);
                }else{
                    shouldBeInvoice  = pInvoiceNum;
                }
            }
            
            try{
                if(shouldBeNumeric != null){
                    numeric = new Integer(shouldBeNumeric);
                }
            }catch(Exception e){}
            invoice = shouldBeInvoice;
            if(myModifier.equals(shouldBeModifier)){
                modifier = shouldBeModifier;
            }
        }
        
        public boolean isValid(){
            if(Utility.isSet(invoice)){
                if(modifier == null && numeric == null){
                    return true;
                }
                if(modifier == null){
                    return false;
                }
                if(numeric == null){
                    return false;
                }
                return true;
            }
            return false;
        }
        
        public String toString(){
            return "Modifier: "+modifier+"\nNumeric: " +numeric+ "\nInvoice_Base: " +invoice;
        }
    }
    
    public DistributorInvoiceNumTool(String t){
        
        DistInvoiceModifierDefinition def = new DistInvoiceModifierDefinition(false, "RP_");
        ParsedDistributorInvoice pi = splitDistInvoiceIntoSuspectedParts("ABC",def);
    }
    
    public static void main(String[] args){
        DistributorInvoiceNumTool me = new DistributorInvoiceNumTool("test");
    }
}
