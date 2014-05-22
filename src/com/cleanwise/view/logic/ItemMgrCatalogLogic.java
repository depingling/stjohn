
package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.CatalogMgrSearchForm;
import com.cleanwise.view.forms.CatalogMgrStructureForm;
import com.cleanwise.view.forms.CatalogMgrDetailForm;
import com.cleanwise.view.forms.ItemMgrCatalogForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import java.rmi.RemoteException;
import java.math.BigDecimal;


import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>CatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author yuriy
 */
public class ItemMgrCatalogLogic {



  /**
   * Prepare to edit existing item
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors initEdit(HttpServletRequest request,
			  ItemMgrCatalogForm pForm)
    throws Exception
  {
    pForm.setRetAction(request.getParameter("retaction"));
    HttpSession session = request.getSession();
    CatalogMgrDetailForm catalogDetail =
	(CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
    int catalogId=catalogDetail.getDetail().getCatalogId();
    pForm.setCatalogId(catalogId);

    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    CatalogData superCatalog = catalogInfEjb.getSuperCatalog(catalogId);
    if(superCatalog == null) {
      ae.add("error", new ActionError("item.catalog.no_super_catalog"));
      return ae;
    }
    pForm.setSuperCatalogId(superCatalog.getCatalogId());

    String productIdS=request.getParameter("itemId");
    if(productIdS!=null & productIdS.trim().length()>0) {
      try{
        int productId=Integer.parseInt(productIdS);
        ProductData productD = catalogInfEjb.getCatalogClwProduct(catalogId,productId,0,0,SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
        pForm.setProduct(productD);
        Date effDate = productD.getEffDate();
        String dateString = "";
        if(effDate !=null) {
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
          dateString = simpleDateFormat.format(effDate);
        }
        pForm.setEffDate(dateString);

        Date expDate = productD.getExpDate();
        dateString = "";
        if(expDate !=null) {
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
          dateString = simpleDateFormat.format(expDate);
        }
        pForm.setExpDate(dateString);

        BusEntityData manuD = productD.getManufacturer();
        if(manuD!=null) {
          int manuId=productD.getManufacturer().getBusEntityId();
          pForm.setManufacturerId(""+manuId);
          pForm.setManufacturerName(""+productD.getManufacturer().getShortDesc());
          String manuSku = productD.getManufacturerSku();
          if(manuSku==null) manuSku="";
          pForm.setManufacturerSku(manuSku);
        } else {
          pForm.setManufacturerId("");
          pForm.setManufacturerName("");
          pForm.setManufacturerSku("");
        }
        //Distributor
        BusEntityData distD = productD.getCatalogDistributor();
        if(distD!=null) {
          int distId=distD.getBusEntityId();
          pForm.setDistributorId(""+distId);
          pForm.setDistributorName(""+distD.getShortDesc());
          String distSku = productD.getDistributorSku(distId);
          if(distSku==null) distSku="";
          pForm.setDistributorSku(distSku);
        } else {
          pForm.setDistributorId("");
          pForm.setDistributorName("");
          pForm.setDistributorSku("");
        }
        //possible distributors
        ItemMappingDataVector itemMappingDV =
	    productD.getDistributorMappings();
//        int superCatalogId = pForm.getSuperCatalogId();
        DistributorDataVector distDV = catalogInfEjb.getDistributorCollection(catalogId,null);
        IdVector possibleDistIdV = new IdVector();
        for(int ii=0; ii<itemMappingDV.size(); ii++) {
          ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);
          int id = itemMappingD.getBusEntityId();
          for(int jj=0; jj<distDV.size(); jj++) {
            DistributorData dD = (DistributorData) distDV.get(jj);
            if(dD.getBusEntity().getBusEntityId()==id) {
              possibleDistIdV.add(new Integer(id));
              break;
            }
          }
        }
        pForm.setPossibleDistributorIds(possibleDistIdV);
        //

        pForm.setNewDistributorId("");
        pForm.setNewDistributorSku("");
        pForm.setNewDistributor(null);
        pForm.setDistributorBox(new String[0]);
      }catch(DataNotFoundException exc) {
        ae.add("error", new ActionError("item.master.no_item"));
      }catch(NumberFormatException exc) {
        ae.add("error", new ActionError("item.master.no_item"));
      }
    } else {
     ae.add("error", new ActionError("item.master.no_item"));
    }
    return ae;
  }



  /**
  * Updates existing of adds new product to MASTER product
  *
  */
  public static ActionErrors saveCatalogProduct (HttpServletRequest request,
			    ItemMgrCatalogForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    String user =(String)session.getAttribute(Constants.USER_NAME);
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Distributor distributorEjb = factory.getDistributorAPI();

    ProductData productD = pForm.getProduct();

    //Set disrtibutor
    String distIdS=pForm.getDistributorId();
    if(distIdS!=null && distIdS.trim().length()>0) {
      try {
        int distId = Integer.parseInt(distIdS);
        IdVector possibleDistIdV = pForm.getPossibleDistributorIds();
        int ii=0;
        for(; ii<possibleDistIdV.size(); ii++) {
          int id = ((Integer)possibleDistIdV.get(ii)).intValue();
          if(id ==distId) {
            break;
          }
        }
        if(ii==possibleDistIdV.size()) {
          ae.add("error", new ActionError("item.catalog.no_distributor"));
          return ae;
        }
        DistributorData distD = distributorEjb.getDistributor(distId);
        productD.setCatalogDistributor(distD.getBusEntity());
      }catch(DataNotFoundException exc) {
        ae.add("error", new ActionError("item.catalog.no_distributor"));
        return ae;
      }catch(NumberFormatException exc) {
        ae.add("error", new ActionError("item.catalog.no_distributor"));
        return ae;
      }
    } else {
        productD.setCatalogDistributor(null);
    }

    int catalogId = pForm.getCatalogId();

    if(catalogId==0) {
      ae.add("error", new ActionError("item.master.no_catalog"));
      return ae;
    }
    String name = productD.getShortDesc();

    if(name ==null || name.trim().length()==0) {
      ae.add("error", new ActionError("variable.empty.error","Item Name"));
      return ae;
    }

    productD=catalogEjb.saveCatalogProduct(catalogId, productD,user);
    pForm.setProduct(productD);

    return ae;
  }

}



