package com.cleanwise.service.api.util;

import org.apache.log4j.Logger;

public class XSQLParser {
    private static final Logger log = Logger.getLogger(XSQLParser.class);
    int segments = 1;
    String Xml;
    java.util.ArrayList mTabs = new java.util.ArrayList();

    public XSQLParser(String pXml) {
        Xml = pXml;
        try {
            int startIdx = -1,
                endIdx = -1;
            do {
                // Find the tab tag
                startIdx = Xml.indexOf("<tab>");
                endIdx = Xml.indexOf("</tab>");
                
                if ( startIdx < 0 )
                    break;
                
                if ( endIdx < 0 )
                    break;
                
                String Name = "", Sql = "",
                    PaperSize = "", PaperOrientation = "";
                
                // Find the paper size
                startIdx = Xml.indexOf("<paperSize>");
                endIdx = Xml.indexOf("</paperSize>");
                if ( startIdx < 0 )
                    log.info("<paperSize> is missing in" + Xml);
                if ( endIdx < 0 )
                    log.info("</paperSize> is missing in" + Xml);
                if ( startIdx >= 0 && endIdx > 0 )
                    PaperSize = Xml.substring(startIdx+11, endIdx);
                
//              Find the paper orientation
                startIdx = Xml.indexOf("<paperOrientation>");
                endIdx = Xml.indexOf("</paperOrientation>");
                if ( startIdx < 0 )
                    log.info("<paperOrientation> is missing in" + Xml);
                if ( endIdx < 0 )
                    log.info("</paperOrientation> is missing in" + Xml);
                if ( startIdx >= 0 && endIdx > 0 )
                    PaperOrientation = Xml.substring(startIdx+18, endIdx);
                
//              Find the name
                startIdx = Xml.indexOf("<name>");
                endIdx = Xml.indexOf("</name>");
                if ( startIdx < 0 )
                    log.info("<name> is missing in" + Xml);
                if ( endIdx < 0 )
                    log.info("</name> is missing in" + Xml);
                if ( startIdx >= 0 && endIdx > 0 )
                    Name = Xml.substring(startIdx+6, endIdx);
                
                // Find the sql
                startIdx = Xml.indexOf("<sql>");
                endIdx = Xml.indexOf("</sql>");
                if ( startIdx < 0 )
                    log.info("<sql> is missing in" + Xml);
                if ( endIdx < 0 )
                    log.info("</sql> is missing in" + Xml);
                
                if ( startIdx >= 0 && endIdx > 0 )
                    Sql = Xml.substring(startIdx+5, endIdx);
                
                XSQLParser.Tab req = new XSQLParser.Tab(Name, Sql, PaperSize,
                            PaperOrientation);
                log.info("Added Xml Tab =" + req);
                mTabs.add(req);
                Xml = Xml.substring(endIdx+5);
            }
            while ( startIdx >= 0 && endIdx > 0 );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getXml()  {
        return this.Xml;
    }

    /**
     * Sets the value of xml
     *
     * @param argXml Value to assign to this.xml
     */
    public void setXml(String argXml) {
        this.Xml = argXml;
    }

    public class Tab {
        String Name, Sql, PaperSize, PaperOrientation;
        public Tab(String pName, String pSql, String pPaperSize,
                String pPaperOrientation)
        {
            Name = pName;
            Sql = pSql;
            PaperSize = pPaperSize;
            PaperOrientation = pPaperOrientation;
        }


        public String toString() 
        {
            return "{ Tab: Name = " + Name
                + " Sql = " + Sql + " } ";
        }
        
        /**
         * Gets the value of name
         *
         * @return the value of name
         */
        public String getName()  {
            return this.Name;
        }
        
        /**
         * Sets the value of name
         *
         * @param argName Value to assign to this.name
         */
        public void setName(String argName) {
            this.Name = argName;
        }
        
        /**
         * Gets the value of sql
         *
         * @return the value of sql
         */
        public String getSql()  {
            return this.Sql;
        }
        
        /**
         * Sets the value of sql
         *
         * @param argSql Value to assign to this.sql
         */
        public void setSql(String argSql) {
            this.Sql = argSql;
        }


        public String getPaperOrientation() {
            return PaperOrientation;
        }


        public void setPaperOrientation(String paperOrientation) {
            PaperOrientation = paperOrientation;
        }


        public String getPaperSize() {
            return PaperSize;
        }


        public void setPaperSize(String paperSize) {
            PaperSize = paperSize;
        }
    }

    private int  mCurrIdx = 0;
    private XSQLParser.Tab mCurrTab = null;
    public boolean next() {
        if ( mTabs == null || mCurrIdx >= mTabs.size() ) {
            return false;
        }
        mCurrTab = (XSQLParser.Tab)mTabs.get(mCurrIdx);
        mCurrIdx++;
        return true;
    }
    public String getSql() {
        return mCurrTab.getSql();
    }
    public String getName() {
        return mCurrTab.getName();
    }
    public String getPaperSize() {
        return mCurrTab.getPaperSize();
    }
    public String getPaperOrientation() {
        return mCurrTab.getPaperOrientation();
    }
}
