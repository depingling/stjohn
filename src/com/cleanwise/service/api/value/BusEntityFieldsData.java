package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.lang.*;
import java.util.*;
import java.io.*;
import com.cleanwise.service.api.util.Utility;

/**
 *  <code>SiteFieldsData</code> value object used to carry the various screen
 *  options for a business entity. Both the store and account business entities
 *  can specify ui options.
 *
 *@author     dvieira
 *@created    November 8, 2001
 */
public class BusEntityFieldsData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2463999242745720103L;

    private String mF1Tag = "";
    private String mF2Tag = "";
    private String mF3Tag = "";
    private String mF4Tag = "";
    private String mF5Tag = "";
    private String mF6Tag = "";
    private String mF7Tag = "";
    private String mF8Tag = "";
    private String mF9Tag = "";
    private String mF10Tag = "";
    private String mF11Tag = "";
    private String mF12Tag = "";
    private String mF13Tag = "";
    private String mF14Tag = "";
    private String mF15Tag = "";

    private boolean mF1ShowAdmin = false;
    private boolean mF2ShowAdmin = false;
    private boolean mF3ShowAdmin = false;
    private boolean mF4ShowAdmin = false;
    private boolean mF5ShowAdmin = false;
    private boolean mF6ShowAdmin = false;
    private boolean mF7ShowAdmin = false;
    private boolean mF8ShowAdmin = false;
    private boolean mF9ShowAdmin = false;
    private boolean mF10ShowAdmin = false;
    private boolean mF11ShowAdmin = false;
    private boolean mF12ShowAdmin = false;
    private boolean mF13ShowAdmin = false;
    private boolean mF14ShowAdmin = false;
    private boolean mF15ShowAdmin = false;

    private boolean mF1ShowRuntime = false;
    private boolean mF2ShowRuntime = false;
    private boolean mF3ShowRuntime = false;
    private boolean mF4ShowRuntime = false;
    private boolean mF5ShowRuntime = false;
    private boolean mF6ShowRuntime = false;
    private boolean mF7ShowRuntime = false;
    private boolean mF8ShowRuntime = false;
    private boolean mF9ShowRuntime = false;
    private boolean mF10ShowRuntime = false;
    private boolean mF11ShowRuntime = false;
    private boolean mF12ShowRuntime = false;
    private boolean mF13ShowRuntime = false;
    private boolean mF14ShowRuntime = false;
    private boolean mF15ShowRuntime = false;

    private boolean mF1Required = false;
    private boolean mF2Required = false;
    private boolean mF3Required = false;
    private boolean mF4Required = false;
    private boolean mF5Required = false;
    private boolean mF6Required = false;
    private boolean mF7Required = false;
    private boolean mF8Required = false;
    private boolean mF9Required = false;
    private boolean mF10Required = false;
    private boolean mF11Required = false;
    private boolean mF12Required = false;
    private boolean mF13Required = false;
    private boolean mF14Required = false;
    private boolean mF15Required = false;

    private ArrayList mCheckBoxNames=null;


    private boolean clwSwitch = false;

    public static BusEntityFieldsData createValue() {
        return new BusEntityFieldsData();
    }

    /**
     *  Constructor for the SiteFieldsData object
     */
    public BusEntityFieldsData() {
        try {
            clwSwitch = Utility.getClwSwitch();

        } catch (Exception exc) {
        }
        return;
    }


    public void setF1Required(boolean v) {
        mF1Required = v;
    }
    public void setF2Required(boolean v) {
        mF2Required = v;
    }
    public void setF3Required(boolean v) {
        mF3Required = v;
    }
    public void setF4Required(boolean v) {
        mF4Required = v;
    }
    public void setF5Required(boolean v) {
        mF5Required = v;
    }
    public void setF6Required(boolean v) {
        mF6Required = v;
    }
    public void setF7Required(boolean v) {
        mF7Required = v;
    }
    public void setF8Required(boolean v) {
        mF8Required = v;
    }
    public void setF9Required(boolean v) {
        mF9Required = v;
    }
    public void setF10Required(boolean v) {
        mF10Required = v;
    }
    public void setF11Required(boolean v) {
        mF11Required = v;
    }
    public void setF12Required(boolean v) {
        mF12Required = v;
    }
    public void setF13Required(boolean v) {
        mF13Required = v;
    }
    public void setF14Required(boolean v) {
        mF14Required = v;
    }
    public void setF15Required(boolean v) {
        mF15Required = v;
    }

    public boolean getF1Required() {
        return mF1Required;
    }
    public boolean getF2Required() {
        return mF2Required;
    }
    public boolean getF3Required() {
        return mF3Required;
    }
    public boolean getF4Required() {
        return mF4Required;
    }
    public boolean getF5Required() {
        return mF5Required;
    }
    public boolean getF6Required() {
        return mF6Required;
    }
    public boolean getF7Required() {
        return mF7Required;
    }
    public boolean getF8Required() {
        return mF8Required;
    }
    public boolean getF9Required() {
        return mF9Required;
    }
    public boolean getF10Required() {
        return mF10Required;
    }
    public boolean getF11Required() {
        return mF11Required;
    }
    public boolean getF12Required() {
        return mF12Required;
    }
    public boolean getF13Required() {
        return mF13Required;
    }
    public boolean getF14Required() {
        return mF14Required;
    }
    public boolean getF15Required() {
        return mF15Required;
    }


    public void setF1ShowAdmin(boolean v) {
        mF1ShowAdmin = v;
    }
    public void setF2ShowAdmin(boolean v) {
        mF2ShowAdmin = v;
    }
    public void setF3ShowAdmin(boolean v) {
        mF3ShowAdmin = v;
    }
    public void setF4ShowAdmin(boolean v) {
        mF4ShowAdmin = v;
    }
    public void setF5ShowAdmin(boolean v) {
        mF5ShowAdmin = v;
    }
    public void setF6ShowAdmin(boolean v) {
        mF6ShowAdmin = v;
    }
    public void setF7ShowAdmin(boolean v) {
        mF7ShowAdmin = v;
    }
    public void setF8ShowAdmin(boolean v) {
        mF8ShowAdmin = v;
    }
    public void setF9ShowAdmin(boolean v) {
        mF9ShowAdmin = v;
    }
    public void setF10ShowAdmin(boolean v) {
        mF10ShowAdmin = v;
    }
    public void setF11ShowAdmin(boolean v) {
        mF11ShowAdmin = v;
    }
    public void setF12ShowAdmin(boolean v) {
        mF12ShowAdmin = v;
    }
    public void setF13ShowAdmin(boolean v) {
        mF13ShowAdmin = v;
    }
    public void setF14ShowAdmin(boolean v) {
        mF14ShowAdmin = v;
    }
    public void setF15ShowAdmin(boolean v) {
        mF15ShowAdmin = v;
    }



    public void setF1ShowRuntime(boolean v) {
        mF1ShowRuntime = v;
    }
    public void setF2ShowRuntime(boolean v) {
        mF2ShowRuntime = v;
    }
    public void setF3ShowRuntime(boolean v) {
        mF3ShowRuntime = v;
    }
    public void setF4ShowRuntime(boolean v) {
        mF4ShowRuntime = v;
    }
    public void setF5ShowRuntime(boolean v) {
        mF5ShowRuntime = v;
    }
    public void setF6ShowRuntime(boolean v) {
        mF6ShowRuntime = v;
    }
    public void setF7ShowRuntime(boolean v) {
        mF7ShowRuntime = v;
    }
    public void setF8ShowRuntime(boolean v) {
        mF8ShowRuntime = v;
    }
    public void setF9ShowRuntime(boolean v) {
        mF9ShowRuntime = v;
    }
    public void setF10ShowRuntime(boolean v) {
        mF10ShowRuntime = v;
    }
    public void setF11ShowRuntime(boolean v) {
        mF11ShowRuntime = v;
    }
    public void setF12ShowRuntime(boolean v) {
        mF12ShowRuntime = v;
    }
    public void setF13ShowRuntime(boolean v) {
        mF13ShowRuntime = v;
    }
    public void setF14ShowRuntime(boolean v) {
        mF14ShowRuntime = v;
    }
    public void setF15ShowRuntime(boolean v) {
        mF15ShowRuntime = v;
    }



    public void setF1Tag(String v) {
        mF1Tag = v;
    }
    public void setF2Tag(String v) {
        mF2Tag = v;
    }
    public void setF3Tag(String v) {
        mF3Tag = v;
    }
    public void setF4Tag(String v) {
        mF4Tag = v;
    }
    public void setF5Tag(String v) {
        mF5Tag = v;
    }
    public void setF6Tag(String v) {
        mF6Tag = v;
    }
    public void setF7Tag(String v) {
        mF7Tag = v;
    }
    public void setF8Tag(String v) {
        mF8Tag = v;
    }
    public void setF9Tag(String v) {
        mF9Tag = v;
    }
    public void setF10Tag(String v) {
        mF10Tag = v;
    }
    public void setF11Tag(String v) {
        mF11Tag = v;
    }
    public void setF12Tag(String v) {
        mF12Tag = v;
    }
    public void setF13Tag(String v) {
        mF13Tag = v;
    }
    public void setF14Tag(String v) {
        mF14Tag = v;
    }
    public void setF15Tag(String v) {
        mF15Tag = v;
    }


    public boolean getF1ShowAdmin() {
        return mF1ShowAdmin;
    }
    public boolean getF2ShowAdmin() {
        return mF2ShowAdmin;
    }
    public boolean getF3ShowAdmin() {
        return mF3ShowAdmin;
    }
    public boolean getF4ShowAdmin() {
        return mF4ShowAdmin;
    }
    public boolean getF5ShowAdmin() {
        return mF5ShowAdmin;
    }
    public boolean getF6ShowAdmin() {
        return mF6ShowAdmin;
    }
    public boolean getF7ShowAdmin() {
        return mF7ShowAdmin;
    }
    public boolean getF8ShowAdmin() {
        return mF8ShowAdmin;
    }
    public boolean getF9ShowAdmin() {
        return mF9ShowAdmin;
    }
    public boolean getF10ShowAdmin() {
        return mF10ShowAdmin;
    }
    public boolean getF11ShowAdmin() {
        return mF11ShowAdmin;
    }
    public boolean getF12ShowAdmin() {
        return mF12ShowAdmin;
    }
    public boolean getF13ShowAdmin() {
        return mF13ShowAdmin;
    }
    public boolean getF14ShowAdmin() {
        return mF14ShowAdmin;
    }
    public boolean getF15ShowAdmin() {
        return mF15ShowAdmin;
    }


    public boolean getF1ShowRuntime() {
        return mF1ShowRuntime;
    }
    public boolean getF2ShowRuntime() {
        return mF2ShowRuntime;
    }
    public boolean getF3ShowRuntime() {
        return mF3ShowRuntime;
    }
    public boolean getF4ShowRuntime() {
        return mF4ShowRuntime;
    }
    public boolean getF5ShowRuntime() {
        return mF5ShowRuntime;
    }
    public boolean getF6ShowRuntime() {
        return mF6ShowRuntime;
    }
    public boolean getF7ShowRuntime() {
        return mF7ShowRuntime;
    }
    public boolean getF8ShowRuntime() {
        return mF8ShowRuntime;
    }
    public boolean getF9ShowRuntime() {
        return mF9ShowRuntime;
    }
    public boolean getF10ShowRuntime() {
        return mF10ShowRuntime;
    }
    public boolean getF11ShowRuntime() {
        return mF11ShowRuntime;
    }
    public boolean getF12ShowRuntime() {
        return mF12ShowRuntime;
    }
    public boolean getF13ShowRuntime() {
        return mF13ShowRuntime;
    }
    public boolean getF14ShowRuntime() {
        return mF14ShowRuntime;
    }
    public boolean getF15ShowRuntime() {
        return mF15ShowRuntime;
    }



    public String getF1Tag() {
        return mF1Tag;
    }
    public String getF2Tag() {
        return mF2Tag;
    }
    public String getF3Tag() {
        return mF3Tag;
    }
    public String getF4Tag() {
        return mF4Tag;
    }
    public String getF5Tag() {
        return mF5Tag;
    }
    public String getF6Tag() {
        return mF6Tag;
    }
    public String getF7Tag() {
        return mF7Tag;
    }
    public String getF8Tag() {
        return mF8Tag;
    }
    public String getF9Tag() {
        return mF9Tag;
    }
    public String getF10Tag() {
        return mF10Tag;
    }
    public String getF11Tag() {
        return mF11Tag;
    }
    public String getF12Tag() {
        return mF12Tag;
    }
    public String getF13Tag() {
        return mF13Tag;
    }
    public String getF14Tag() {
        return mF14Tag;
    }
    public String getF15Tag() {
        return mF15Tag;
    }



    /**
     *  Provide a string representation of the object.
     *
     *@return
     */
    public String toString() {
        return "[" +
                "F1ShowAdmin=" + mF1ShowAdmin +
                ", F1ShowRuntime=" + mF1ShowRuntime +
                ", F1Tag=" + mF1Tag +
                ", F1Required=" + mF1Required +
                ", F2ShowAdmin=" + mF2ShowAdmin +
                ", F2ShowRuntime=" + mF2ShowRuntime +
                ", F2Tag=" + mF2Tag +
                ", F2Required=" + mF2Required +
                ", F3ShowAdmin=" + mF3ShowAdmin +
                ", F3ShowRuntime=" + mF3ShowRuntime +
                ", F3Tag=" + mF3Tag +
                ", F3Required=" + mF3Required +
                ", F4ShowAdmin=" + mF4ShowAdmin +
                ", F4ShowRuntime=" + mF4ShowRuntime +
                ", F4Tag=" + mF4Tag +
                ", F4Required=" + mF4Required +
                ", F5ShowAdmin=" + mF5ShowAdmin +
                ", F5ShowRuntime=" + mF5ShowRuntime +
                ", F5Tag=" + mF5Tag +
                ", F5Required=" + mF5Required +
                ", F6ShowAdmin=" + mF6ShowAdmin +
                ", F6ShowRuntime=" + mF6ShowRuntime +
                ", F6Tag=" + mF6Tag +
                ", F6Required=" + mF6Required +
                ", F7ShowAdmin=" + mF7ShowAdmin +
                ", F7ShowRuntime=" + mF7ShowRuntime +
                ", F7Tag=" + mF7Tag +
                ", F7Required=" + mF7Required +
                ", F8ShowAdmin=" + mF8ShowAdmin +
                ", F8ShowRuntime=" + mF8ShowRuntime +
                ", F8Tag=" + mF8Tag +
                ", F8Required=" + mF8Required +
                ", F9ShowAdmin=" + mF9ShowAdmin +
                ", F9ShowRuntime=" + mF9ShowRuntime +
                ", F9Tag=" + mF9Tag +
                ", F9Required=" + mF9Required +
                ", F10ShowAdmin=" + mF10ShowAdmin +
                ", F10ShowRuntime=" + mF10ShowRuntime +
                ", F10Tag=" + mF10Tag +
                ", F10Required=" + mF10Required +
                ", F11ShowAdmin=" + mF11ShowAdmin +
                ", F11ShowRuntime=" + mF11ShowRuntime +
                ", F11Tag=" + mF11Tag +
                ", F11Required=" + mF11Required +
                ", F12ShowAdmin=" + mF12ShowAdmin +
                ", F12ShowRuntime=" + mF12ShowRuntime +
                ", F12Tag=" + mF12Tag +
                ", F12Required=" + mF12Required +
                ", F13ShowAdmin=" + mF13ShowAdmin +
                ", F13ShowRuntime=" + mF13ShowRuntime +
                ", F13Tag=" + mF13Tag +
                ", F13Required=" + mF13Required +
                ", F14ShowAdmin=" + mF14ShowAdmin +
                ", F14ShowRuntime=" + mF14ShowRuntime +
                ", F14Tag=" + mF14Tag +
                ", F14Required=" + mF14Required +
                ", F15ShowAdmin=" + mF15ShowAdmin +
                ", F15ShowRuntime=" + mF15ShowRuntime +
                ", F15Tag=" + mF15Tag +
                ", F15Required=" + mF15Required +
                "]";
    }

    public ArrayList getCheckBoxNames() {
        if(mCheckBoxNames!=null) return mCheckBoxNames;
        mCheckBoxNames=new ArrayList();
        mCheckBoxNames.add(0,"config.f1Required");
        mCheckBoxNames.add(1,"config.f1ShowRuntime");
        mCheckBoxNames.add(2,"config.f1ShowAdmin");
        mCheckBoxNames.add(3,"config.f2Required");
        mCheckBoxNames.add(4,"config.f2ShowRuntime");
        mCheckBoxNames.add(5,"config.f2ShowAdmin");
        mCheckBoxNames.add(6,"config.f3Required");
        mCheckBoxNames.add(7,"config.f3ShowRuntime");
        mCheckBoxNames.add(8,"config.f3ShowAdmin");
        mCheckBoxNames.add(9,"config.f4Required");
        mCheckBoxNames.add(10,"config.f4ShowRuntime");
        mCheckBoxNames.add(11,"config.f4ShowAdmin");
        mCheckBoxNames.add(12,"config.f5Required");
        mCheckBoxNames.add(13,"config.f5ShowRuntime");
        mCheckBoxNames.add(14,"config.f5ShowAdmin");
        mCheckBoxNames.add(15,"config.f6Required");
        mCheckBoxNames.add(16,"config.f6ShowRuntime");
        mCheckBoxNames.add(17,"config.f6ShowAdmin");
        mCheckBoxNames.add(18,"config.f7Required");
        mCheckBoxNames.add(19,"config.f7ShowRuntime");
        mCheckBoxNames.add(20,"config.f7ShowAdmin");
        mCheckBoxNames.add(21,"config.f8Required");
        mCheckBoxNames.add(22,"config.f8ShowRuntime");
        mCheckBoxNames.add(23,"config.f8ShowAdmin");
        mCheckBoxNames.add(24,"config.f9Required");
        mCheckBoxNames.add(25,"config.f9ShowRuntime");
        mCheckBoxNames.add(26,"config.f9ShowAdmin");
        mCheckBoxNames.add(27,"config.f10Required");
        mCheckBoxNames.add(28,"config.f10ShowRuntime");
        mCheckBoxNames.add(29,"config.f10ShowAdmin");
        mCheckBoxNames.add(30,"config.f11Required");
        mCheckBoxNames.add(31,"config.f11ShowRuntime");
        mCheckBoxNames.add(32,"config.f11ShowAdmin");
        mCheckBoxNames.add(33,"config.f12Required");
        mCheckBoxNames.add(34,"config.f12ShowRuntime");
        mCheckBoxNames.add(35,"config.f12ShowAdmin");
        mCheckBoxNames.add(36,"config.f13Required");
        mCheckBoxNames.add(37,"config.f13ShowRuntime");
        mCheckBoxNames.add(38,"config.f13ShowAdmin");
        mCheckBoxNames.add(39,"config.f14Required");
        mCheckBoxNames.add(40,"config.f14ShowRuntime");
        mCheckBoxNames.add(41,"config.f14ShowAdmin");
        mCheckBoxNames.add(42,"config.f15Required");
        mCheckBoxNames.add(43,"config.f15ShowRuntime");
        mCheckBoxNames.add(44,"config.f15ShowAdmin");

        return mCheckBoxNames;
    }

    public void setCheckBoxNames(ArrayList mCheckBoxNames) {
        this.mCheckBoxNames = mCheckBoxNames;
    }

    public void reset() {

        mF1Tag = "";
        mF2Tag = "";
        mF3Tag = "";
        mF4Tag = "";
        mF5Tag = "";
        mF6Tag = "";
        mF7Tag = "";
        mF8Tag = "";
        mF9Tag = "";
        mF10Tag = "";
        mF11Tag = "";
        mF12Tag = "";
        mF13Tag = "";
        mF14Tag = "";
        mF15Tag = "";

        mF1ShowAdmin = false;
        mF2ShowAdmin = false;
        mF3ShowAdmin = false;
        mF4ShowAdmin = false;
        mF5ShowAdmin = false;
        mF6ShowAdmin = false;
        mF7ShowAdmin = false;
        mF8ShowAdmin = false;
        mF9ShowAdmin = false;
        mF10ShowAdmin = false;
        mF11ShowAdmin = false;
        mF12ShowAdmin = false;
        mF13ShowAdmin = false;
        mF14ShowAdmin = false;
        mF15ShowAdmin = false;

        mF1ShowRuntime = false;
        mF2ShowRuntime = false;
        mF3ShowRuntime = false;
        mF4ShowRuntime = false;
        mF5ShowRuntime = false;
        mF6ShowRuntime = false;
        mF7ShowRuntime = false;
        mF8ShowRuntime = false;
        mF9ShowRuntime = false;
        mF10ShowRuntime = false;
        mF11ShowRuntime = false;
        mF12ShowRuntime = false;
        mF13ShowRuntime = false;
        mF14ShowRuntime = false;
        mF15ShowRuntime = false;

        mF1Required = false;
        mF2Required = false;
        mF3Required = false;
        mF4Required = false;
        mF5Required = false;
        mF6Required = false;
        mF7Required = false;
        mF8Required = false;
        mF9Required = false;
        mF10Required = false;
        mF11Required = false;
        mF12Required = false;
        mF13Required = false;
        mF14Required = false;
        mF15Required = false;
    }

    public void reset_cb_data() {

        mF1ShowAdmin = false;
        mF2ShowAdmin = false;
        mF3ShowAdmin = false;
        mF4ShowAdmin = false;
        mF5ShowAdmin = false;
        mF6ShowAdmin = false;
        mF7ShowAdmin = false;
        mF8ShowAdmin = false;
        mF9ShowAdmin = false;
        mF10ShowAdmin = false;
        mF11ShowAdmin = false;
        mF12ShowAdmin = false;
        mF13ShowAdmin = false;
        mF14ShowAdmin = false;
        mF15ShowAdmin = false;

        mF1ShowRuntime = false;
        mF2ShowRuntime = false;
        mF3ShowRuntime = false;
        mF4ShowRuntime = false;
        mF5ShowRuntime = false;
        mF6ShowRuntime = false;
        mF7ShowRuntime = false;
        mF8ShowRuntime = false;
        mF9ShowRuntime = false;
        mF10ShowRuntime = false;
        mF11ShowRuntime = false;
        mF12ShowRuntime = false;
        mF13ShowRuntime = false;
        mF14ShowRuntime = false;
        mF15ShowRuntime = false;

        mF1Required = false;
        mF2Required = false;
        mF3Required = false;
        mF4Required = false;
        mF5Required = false;
        mF6Required = false;
        mF7Required = false;
        mF8Required = false;
        mF9Required = false;
        mF10Required = false;
        mF11Required = false;
        mF12Required = false;
        mF13Required = false;
        mF14Required = false;
        mF15Required = false;

    }
}

