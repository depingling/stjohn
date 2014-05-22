package com.cleanwise.view.forms;

import java.util.Hashtable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Enumeration;

/**
 *
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 19:30:10
 *
 */
public class LocateForm {
    public static String SHOW_DISPLAY="display:block";
    public static String HIDE_DISPLAY="display:none";
    public Hashtable form;
    public Hashtable getForm() {
        /*  if(form!=null)
       {  Collection val = form.values();
           Iterator it = val.iterator();

      Enumeration key = form.keys();
       }
        */
        return form;
    }
    public void setForm(Hashtable form) {
        this.form = form;
    }

    public LocateForm() throws Exception {
        form=new Hashtable();

    }
    public void initForm(String page) throws Exception {
        if (page.equals(StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE))
        {
            form.put(StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,new Locate());
        }
        else throw new Exception("!!!!!!!!!!!!!!! Unknown page portal"+" !!!!!!!!!!!!!!!!!!");
    }


    public class  Contract implements LocateData
    {
        String statusLocateContract;
        String selectContract;


        public String getStatusLocate() {
            return statusLocateContract;
        }

        public void setStatusLocate(String statusLocateContract) {
            this.statusLocateContract = statusLocateContract;
        }

        public String getSelect() {
            return selectContract;
        }

        public void setSelect(String selectContract) {
            this.selectContract = selectContract;
        }



        public Contract() {
            this.selectContract="";
            this.statusLocateContract=HIDE_DISPLAY;
        }
    }
    public class  FreightHandler  implements LocateData
    {


        String statusLocateFreightHandler;
        String selectLocateFreightHandler;

        public FreightHandler() {
            this.statusLocateFreightHandler=HIDE_DISPLAY;
            this.selectLocateFreightHandler="";
        }

        public String getStatusLocate() {
            return statusLocateFreightHandler;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setStatusLocate(String statusFreightHandler) {
            this.statusLocateFreightHandler=statusFreightHandler;
        }

        public String getSelect() {
            return selectLocateFreightHandler;
        }

        public void setSelect(String selectFreightHandler) {
            this.selectLocateFreightHandler=selectFreightHandler;
        }
    }
    public class  Distributor   implements LocateData
    {
        String selectDistributor;
        String statusLocateDistributor;
        public Distributor() {
            this.selectDistributor="";
            this.statusLocateDistributor=HIDE_DISPLAY;
        }

        public String getSelect() {
            return selectDistributor;
        }

        public void setSelect(String selectDistributor) {
            this.selectDistributor = selectDistributor;
        }

        public String getStatusLocate() {
            return statusLocateDistributor;
        }

        public void setStatusLocate(String statusLocateDistributor) {
            this.statusLocateDistributor = statusLocateDistributor;
        }

    }
    public class Locate
    {
        Contract locateContract;
        FreightHandler  locateFreightHandler;
        Distributor locateDistributor;

        public Locate() {
            this.locateContract=new Contract();
            this.locateDistributor=new Distributor();
            this.locateFreightHandler=new FreightHandler();
        }

        public Contract getLocateContract() {
            return locateContract;
        }

        public void setLocateContract(Contract locateContract) {
            this.locateContract = locateContract;
        }

        public FreightHandler getLocateFreightHandler() {
            return locateFreightHandler;
        }

        public void setLocateFreightHandler(FreightHandler locateFreightHandler) {
            this.locateFreightHandler = locateFreightHandler;
        }

        public Distributor getLocateDistributor() {
            return locateDistributor;
        }

        public void setLocateDistributor(Distributor locateDistributor) {
            this.locateDistributor = locateDistributor;
        }
    }
    public interface LocateData {

        public String getStatusLocate();
        public void setStatusLocate(String status);
        public String getSelect();
        public void setSelect(String select );

    }

}
