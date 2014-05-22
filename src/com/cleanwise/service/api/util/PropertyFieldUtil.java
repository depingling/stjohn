package com.cleanwise.service.api.util;


import javax.ejb.*;
import java.rmi.*;
import java.sql.*;
import java.util.Properties;
import java.util.Enumeration;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;

/**
 *  The <code>PropertyUtil</code> implements the <code>
 *
 *
 *@author     durval
 *@created    August 29, 2001
 *@see        com.cleanwise.service.api.session.PropertyService </code>
 *      interface.
 *@see        com.cleanwise.service.api.session.PropertyService
 */
public class PropertyFieldUtil extends PropertyUtil {
    private String mFieldCode;
    
    public static String getFnTag(int idx){
        return "F"+idx+"Tag";
    }
    
    public PropertyFieldUtil(Connection pDbConn,String pFieldCode) {
        super(pDbConn);
        mFieldCode = pFieldCode;
    }
    
    public BusEntityFieldsData fetchBusEntityFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException {
        
        // Read the properties needed to define the SiteFieldsData
        // object.  These are:
        // Field1 tag, value, show
        // Field2 tag, value, show
        // Field3 tag, value, show
        // Field4 tag, value, show
        // Field5 tag, value, show
        // Field6 tag, value, show
        // Field7 tag, value, show
        // Field8 tag, value, show
        // Field9 tag, value, show
        // Field10 tag, value, show
        BusEntityFieldsData sfd = new BusEntityFieldsData();
        Properties props = null;
        
        try {
            props = fetchProperties(0, pBusEntityId,
            mFieldCode
            );
        }
        catch (DataNotFoundException e) {
            return sfd;
        }
        
        if (props == null || props.size() == 0) {
            return sfd;
        }
        
        String k = "";
        // Key to search for.
        k = "F1Tag";
        if (props.containsKey(k)) {
            sfd.setF1Tag(props.getProperty(k));
        }
        k = "F2Tag";
        if (props.containsKey(k)) {
            sfd.setF2Tag(props.getProperty(k));
        }
        k = "F3Tag";
        if (props.containsKey(k)) {
            sfd.setF3Tag(props.getProperty(k));
        }
        k = "F4Tag";
        if (props.containsKey(k)) {
            sfd.setF4Tag(props.getProperty(k));
        }
        k = "F5Tag";
        if (props.containsKey(k)) {
            sfd.setF5Tag(props.getProperty(k));
        }
        k = "F6Tag";
        if (props.containsKey(k)) {
            sfd.setF6Tag(props.getProperty(k));
        }
        k = "F7Tag";
        if (props.containsKey(k)) {
            sfd.setF7Tag(props.getProperty(k));
        }
        k = "F8Tag";
        if (props.containsKey(k)) {
            sfd.setF8Tag(props.getProperty(k));
        }
        k = "F9Tag";
        if (props.containsKey(k)) {
            sfd.setF9Tag(props.getProperty(k));
        }
        k = "F10Tag";
        if (props.containsKey(k)) {
            sfd.setF10Tag(props.getProperty(k));
        }
        k = "F11Tag";
        if (props.containsKey(k)) {
            sfd.setF11Tag(props.getProperty(k));
        }
        k = "F12Tag";
        if (props.containsKey(k)) {
            sfd.setF12Tag(props.getProperty(k));
        }
        k = "F13Tag";
        if (props.containsKey(k)) {
            sfd.setF13Tag(props.getProperty(k));
        }
        k = "F14Tag";
        if (props.containsKey(k)) {
            sfd.setF14Tag(props.getProperty(k));
        }
        k = "F15Tag";
        if (props.containsKey(k)) {
            sfd.setF15Tag(props.getProperty(k));
        }

        //requiered attribute
        k = "F1Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF1Required(f.booleanValue());
        }
        k = "F2Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF2Required(f.booleanValue());
        }
        k = "F3Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF3Required(f.booleanValue());
        }
        k = "F4Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF4Required(f.booleanValue());
        }
        k = "F5Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF5Required(f.booleanValue());
        }
        k = "F6Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF6Required(f.booleanValue());
        }
        k = "F7Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF7Required(f.booleanValue());
        }
        k = "F8Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF8Required(f.booleanValue());
        }
        k = "F9Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF9Required(f.booleanValue());
        }
        k = "F10Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF10Required(f.booleanValue());
        }
        k = "F11Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF11Required(f.booleanValue());
        }
        k = "F12Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF12Required(f.booleanValue());
        }
        k = "F13Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF13Required(f.booleanValue());
        }
        k = "F14Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF14Required(f.booleanValue());
        }
        k = "F15Required";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF15Required(f.booleanValue());
        }

        k = "F1ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF1ShowAdmin(f.booleanValue());
        }
        k = "F2ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF2ShowAdmin(f.booleanValue());
        }
        k = "F3ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF3ShowAdmin(f.booleanValue());
        }
        k = "F4ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF4ShowAdmin(f.booleanValue());
        }
        k = "F5ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF5ShowAdmin(f.booleanValue());
        }
        k = "F6ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF6ShowAdmin(f.booleanValue());
        }
        k = "F7ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF7ShowAdmin(f.booleanValue());
        }
        k = "F8ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF8ShowAdmin(f.booleanValue());
        }
        k = "F9ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF9ShowAdmin(f.booleanValue());
        }
        k = "F10ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF10ShowAdmin(f.booleanValue());
        }
        k = "F11ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF11ShowAdmin(f.booleanValue());
        }
        k = "F12ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF12ShowAdmin(f.booleanValue());
        }
        k = "F13ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF13ShowAdmin(f.booleanValue());
        }
        k = "F14ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF14ShowAdmin(f.booleanValue());
        }
        k = "F15ShowAdmin";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF15ShowAdmin(f.booleanValue());
        }

        k = "F1ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF1ShowRuntime(f.booleanValue());
        }
        k = "F2ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF2ShowRuntime(f.booleanValue());
        }
        k = "F3ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF3ShowRuntime(f.booleanValue());
        }
        k = "F4ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF4ShowRuntime(f.booleanValue());
        }
        k = "F5ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF5ShowRuntime(f.booleanValue());
        }
        k = "F6ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF6ShowRuntime(f.booleanValue());
        }
        k = "F7ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF7ShowRuntime(f.booleanValue());
        }
        k = "F8ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF8ShowRuntime(f.booleanValue());
        }
        k = "F9ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF9ShowRuntime(f.booleanValue());
        }
        k = "F10ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF10ShowRuntime(f.booleanValue());
        }
        k = "F11ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF11ShowRuntime(f.booleanValue());
        }
        k = "F12ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF12ShowRuntime(f.booleanValue());
        }
        k = "F13ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF13ShowRuntime(f.booleanValue());
        }
        k = "F14ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF14ShowRuntime(f.booleanValue());
        }
        k = "F15ShowRuntime";
        if (props.containsKey(k)) {
            Boolean f = new Boolean(props.getProperty(k));
            sfd.setF15ShowRuntime(f.booleanValue());
        }

        return sfd;
    }
    
    
    public void updateSiteFieldsData
    (int pBusEntityId, BusEntityFieldsData pSiteFieldsData)
    throws RemoteException {
        String k = "";
        // Key to save.
        
        k = "F1Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF1Tag()
        );
        
        k = "F2Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF2Tag()
        );
        
        k = "F3Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF3Tag()
        );
        
        k = "F4Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF4Tag()
        );
        
        k = "F5Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF5Tag()
        );
        
        k = "F6Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF6Tag()
        );
        
        k = "F7Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF7Tag()
        );
        
        k = "F8Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF8Tag()
        );
        
        k = "F9Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF9Tag()
        );
        
        k = "F10Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF10Tag()
        );
        
        k = "F11Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF11Tag()
        );

        k = "F12Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF12Tag()
        );

        k = "F13Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF13Tag()
        );

        k = "F14Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF14Tag()
        );

        k = "F15Tag";
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, pSiteFieldsData.getF15Tag()
        );

        Boolean f;
        k = "F1ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF1ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F2ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF2ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F3ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF3ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F4ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF4ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F5ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF5ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F6ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF6ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F7ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF7ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F8ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF8ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F9ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF9ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F10ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF10ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F11ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF11ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F12ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF12ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F13ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF13ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F14ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF14ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F15ShowAdmin";
        f = new Boolean(pSiteFieldsData.getF15ShowAdmin());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );

        k = "F1ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF1ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F2ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF2ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F3ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF3ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F4ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF4ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F5ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF5ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F6ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF6ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F7ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF7ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F8ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF8ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F9ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF9ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F10ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF10ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F11ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF11ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F12ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF12ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F13ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF13ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F14ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF14ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F15ShowRuntime";
        f = new Boolean(pSiteFieldsData.getF15ShowRuntime());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );

        //Required
        k = "F1Required";
        f = new Boolean(pSiteFieldsData.getF1Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F2Required";
        f = new Boolean(pSiteFieldsData.getF2Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F3Required";
        f = new Boolean(pSiteFieldsData.getF3Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F4Required";
        f = new Boolean(pSiteFieldsData.getF4Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F5Required";
        f = new Boolean(pSiteFieldsData.getF5Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F6Required";
        f = new Boolean(pSiteFieldsData.getF6Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F7Required";
        f = new Boolean(pSiteFieldsData.getF7Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F8Required";
        f = new Boolean(pSiteFieldsData.getF8Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F9Required";
        f = new Boolean(pSiteFieldsData.getF9Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F10Required";
        f = new Boolean(pSiteFieldsData.getF10Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F11Required";
        f = new Boolean(pSiteFieldsData.getF11Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F12Required";
        f = new Boolean(pSiteFieldsData.getF12Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F13Required";
        f = new Boolean(pSiteFieldsData.getF13Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F14Required";
        f = new Boolean(pSiteFieldsData.getF14Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );
        k = "F15Required";
        f = new Boolean(pSiteFieldsData.getF15Required());
        saveValue( 0,pBusEntityId,
        mFieldCode,
        k, f.toString()
        );

        return;
    }


    public static boolean isShowInAdmin(BusEntityFieldsData pFieldsData) {
        return ((pFieldsData != null) &&
           (pFieldsData.getF1ShowAdmin() ||
            pFieldsData.getF2ShowAdmin() ||
            pFieldsData.getF3ShowAdmin() ||
            pFieldsData.getF4ShowAdmin() ||
            pFieldsData.getF5ShowAdmin() ||
            pFieldsData.getF6ShowAdmin() ||
            pFieldsData.getF7ShowAdmin() ||
            pFieldsData.getF8ShowAdmin() ||
            pFieldsData.getF9ShowAdmin() ||
            pFieldsData.getF10ShowAdmin() ||
            pFieldsData.getF11ShowAdmin() ||
            pFieldsData.getF12ShowAdmin() ||
            pFieldsData.getF13ShowAdmin() ||
            pFieldsData.getF14ShowAdmin() ||
            pFieldsData.getF15ShowAdmin())
         );
    }

    public static boolean getShowInAdmin(BusEntityFieldsData pFieldsData, int i) {
        if (pFieldsData == null) {
            return false;
        }
        if (i==1) return pFieldsData.getF1ShowAdmin();
        if (i==2) return pFieldsData.getF2ShowAdmin();
        if (i==3) return pFieldsData.getF3ShowAdmin();
        if (i==4) return pFieldsData.getF4ShowAdmin();
        if (i==5) return pFieldsData.getF5ShowAdmin();
        if (i==6) return pFieldsData.getF6ShowAdmin();
        if (i==7) return pFieldsData.getF7ShowAdmin();
        if (i==8) return pFieldsData.getF8ShowAdmin();
        if (i==9) return pFieldsData.getF9ShowAdmin();
        if (i==10) return pFieldsData.getF10ShowAdmin();
        if (i==11) return pFieldsData.getF11ShowAdmin();
        if (i==12) return pFieldsData.getF12ShowAdmin();
        if (i==13) return pFieldsData.getF13ShowAdmin();
        if (i==14) return pFieldsData.getF14ShowAdmin();
        if (i==15) return pFieldsData.getF15ShowAdmin();

        return false;
    }

    public static boolean getShowRuntime(BusEntityFieldsData pFieldsData, int i) {
        if (pFieldsData == null) {
            return false;
        }
        if (i==1) return pFieldsData.getF1ShowRuntime();
        if (i==2) return pFieldsData.getF2ShowRuntime();
        if (i==3) return pFieldsData.getF3ShowRuntime();
        if (i==4) return pFieldsData.getF4ShowRuntime();
        if (i==5) return pFieldsData.getF5ShowRuntime();
        if (i==6) return pFieldsData.getF6ShowRuntime();
        if (i==7) return pFieldsData.getF7ShowRuntime();
        if (i==8) return pFieldsData.getF8ShowRuntime();
        if (i==9) return pFieldsData.getF9ShowRuntime();
        if (i==10) return pFieldsData.getF10ShowRuntime();
        if (i==11) return pFieldsData.getF11ShowRuntime();
        if (i==12) return pFieldsData.getF12ShowRuntime();
        if (i==13) return pFieldsData.getF13ShowRuntime();
        if (i==14) return pFieldsData.getF14ShowRuntime();
        if (i==15) return pFieldsData.getF15ShowRuntime();

        return false;
    }


    public static boolean getRequired(BusEntityFieldsData pFieldsData, int i) {
        if (pFieldsData == null) {
            return false;
        }
        if (i==1) return pFieldsData.getF1Required();
        if (i==2) return pFieldsData.getF2Required();
        if (i==3) return pFieldsData.getF3Required();
        if (i==4) return pFieldsData.getF4Required();
        if (i==5) return pFieldsData.getF5Required();
        if (i==6) return pFieldsData.getF6Required();
        if (i==7) return pFieldsData.getF7Required();
        if (i==8) return pFieldsData.getF8Required();
        if (i==9) return pFieldsData.getF9Required();
        if (i==10) return pFieldsData.getF10Required();
        if (i==11) return pFieldsData.getF11Required();
        if (i==12) return pFieldsData.getF12Required();
        if (i==13) return pFieldsData.getF13Required();
        if (i==14) return pFieldsData.getF14Required();
        if (i==15) return pFieldsData.getF15Required();

        return false;
    }

    public static String getTag(BusEntityFieldsData pFieldsData, int i) {
        if (pFieldsData == null) {
            return null;
        }
        if (i==1) return pFieldsData.getF1Tag();
        if (i==2) return pFieldsData.getF2Tag();
        if (i==3) return pFieldsData.getF3Tag();
        if (i==4) return pFieldsData.getF4Tag();
        if (i==5) return pFieldsData.getF5Tag();
        if (i==6) return pFieldsData.getF6Tag();
        if (i==7) return pFieldsData.getF7Tag();
        if (i==8) return pFieldsData.getF8Tag();
        if (i==9) return pFieldsData.getF9Tag();
        if (i==10) return pFieldsData.getF10Tag();
        if (i==11) return pFieldsData.getF11Tag();
        if (i==12) return pFieldsData.getF12Tag();
        if (i==13) return pFieldsData.getF13Tag();
        if (i==14) return pFieldsData.getF14Tag();
        if (i==15) return pFieldsData.getF15Tag();

        return null;
    }

}

