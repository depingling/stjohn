
package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorItemDescData;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.view.forms.CatalogMgrDetailForm;
import com.cleanwise.view.forms.ItemMgrMasterForm;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.SessionTool;

/**
 * <code>CatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author yuriy
 */
public class ItemMgrMasterLogic {


    /**
     *  <code>initConstantList</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */
    public static void initConstantList(HttpServletRequest request)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }

        ListService listServiceEjb = factory.getListServiceAPI();

        if (null == session.getAttribute("Item.uom.vector")) {
	    RefCdDataVector uomv =
		listServiceEjb.getRefCodesCollection("ITEM_UOM_CD");
	    session.setAttribute("Item.uom.vector", uomv);
	}

    }


  /**
   * <code>init</code> method.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void initNew(HttpServletRequest request,
			     ItemMgrMasterForm pForm)
    throws Exception {

    pForm.setRetAction(request.getParameter("retaction"));

    pForm.setProduct(new ProductData());
    Date effDate = pForm.getCurrentDate();
    String dateString = "";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    dateString = simpleDateFormat.format(effDate);
    pForm.setEffDate(dateString);
    pForm.getProduct().setEffDate(effDate);
    pForm.setExpDate("");
    pForm.setManufacturerId("");
    pForm.setManufacturerName("");
    pForm.setManufacturerSku("");
    pForm.setListPrice("");
    pForm.setCostPrice("");
    pForm.setNewDistributorId("");
    pForm.setNewDistributorSku("");
    pForm.setNewDistributorUom("");
    pForm.setNewDistributorPack("");
    pForm.setUom("");
    pForm.setHazmat("false");
    pForm.setNewDistributor(null);
    pForm.setDistributorBox(new String[0]);
    pForm.setDistrListOffset(0);

    initConstantList(request);

    HttpSession session = request.getSession();
    CatalogMgrDetailForm catalogDetail = (CatalogMgrDetailForm)
	session.getAttribute("CATALOG_DETAIL_FORM");
    if (catalogDetail == null) {
	System.err.println("no catalog BEAN in the session");
	return;
    }
    int catalogId=catalogDetail.getDetail().getCatalogId();
    pForm.setCatalogId(catalogId);
      APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
      Catalog catalogEjb = factory.getCatalogAPI();
      int skuNum = catalogEjb.getNextSkuNum();
      pForm.setSkuNum(""+skuNum);
    //Jd store
    if("jd".equals(ClwCustomizer.getStoreDir())) {
      pForm.setCas70FtlPrice("");
      pForm.setCas70ListPrice("");
      pForm.setCas80FtlPrice("");
      pForm.setCas80ListPrice("");
      pForm.setFtlUsPrice("");
      pForm.setListUsPrice("");
    }
  }


  /**
   * Prepare to edit existing item
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors initEdit(HttpServletRequest request,
			  ItemMgrMasterForm pForm)
    throws Exception
  {
    initConstantList(request);

    pForm.setRetAction(request.getParameter("retaction"));
    HttpSession session = request.getSession();
    CatalogMgrDetailForm catalogDetail = (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
    int catalogId=catalogDetail.getDetail().getCatalogId();
    pForm.setCatalogId(catalogId);

    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    String productIdS=request.getParameter("itemId");
    if(productIdS!=null & productIdS.trim().length()>0) {
      try{
        int productId=Integer.parseInt(productIdS);
        ProductData productD = catalogInfEjb.getCatalogClwProduct(catalogId,productId,0,0,SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
        pForm.setProduct(productD);
        pForm.setHazmat(productD.getHazmat());
        pForm.setSkuNum(""+pForm.getProduct().getSkuNum());
        String uomCd = productD.getUom();
        RefCdDataVector uomCdV = (RefCdDataVector)session.getAttribute("Item.uom.vector");
        boolean isPredefined = false;
        for(int i = 0; i < uomCdV.size(); i++) {
            if(uomCd.equals(((RefCdData)uomCdV.get(i)).getValue())) {
                isPredefined = true;
                break;
            }
        }
        if(true == isPredefined || "".equals(uomCd)) {
            pForm.setUom(uomCd);
        }
        else {
            pForm.setUom(RefCodeNames.ITEM_UOM_CD.UOM_OTHER);
        }


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

        String listPriceS = "";
        double listPrice=productD.getListPrice();
        listPriceS=""+listPrice;
        BigDecimal listPriceBD = new BigDecimal("0.0");
	try {
	    listPriceBD = CurrencyFormat.parse(listPriceS);
	} catch (ParseException pe) {
		// this should only happen if bad value in db
	    listPriceBD = new BigDecimal("0.0");
	}
	pForm.setListPrice(CurrencyFormat.format(listPriceBD));

        String costPriceS = "";
        double costPrice=productD.getCostPrice();
        costPriceS=""+costPrice;
        BigDecimal costPriceBD = new BigDecimal("0.0");
	try {
	    costPriceBD = CurrencyFormat.parse(costPriceS);
	} catch (ParseException pe) {
		// this should only happen if bad value in db
	    costPriceBD = new BigDecimal("0.0");
	}
	pForm.setCostPrice(CurrencyFormat.format(costPriceBD));

        pForm.setNewDistributorId("");
        pForm.setNewDistributorSku("");
        pForm.setNewDistributorUom("");
        pForm.setNewDistributorPack("");
        pForm.setNewDistributor(null);
        pForm.setDistributorBox(new String[0]);
        //Jd store begib
        if("jd".equals(ClwCustomizer.getStoreDir())) {
          pForm.setCas70FtlPrice(productD.getProductAttribute("CAS70_FTL_PRICE"));
          pForm.setCas70ListPrice(productD.getProductAttribute("CAS70_LIST_PRICE"));
          pForm.setCas80FtlPrice(productD.getProductAttribute("CAS80_FTL_PRICE"));
          pForm.setCas80ListPrice(productD.getProductAttribute("CAS80_LIST_PRICE"));
          pForm.setFtlUsPrice(productD.getProductAttribute("FTL_US_PRICE"));
          pForm.setListUsPrice(productD.getProductAttribute("LIST_US_PRICE"));
        }
        //Jd store end
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
  //---------------------------------------------------------------------------------------
  /**
   * Prepare to edit existing item
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors initClone(HttpServletRequest request,
			  ItemMgrMasterForm pForm)
    throws Exception
  {

    ActionErrors ae = new ActionErrors();
    ae = initEdit(request, pForm);
    if(ae.size()>0) return ae;
    ProductData productD = pForm.getProduct();
    productD.setProductId(0);
    productD.setSkuNum(0);
    productD.setExpDate(null);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Date today = new Date();
    productD.setEffDate(today);
    pForm.setEffDate(sdf.format(today));
    pForm.setExpDate(null);
    String shortDesc = "Clone of >>>"+productD.getShortDesc();
    if(shortDesc.length()>255) shortDesc = shortDesc.substring(0,255);
    productD.setShortDesc(shortDesc);
    productD.setDistributorMappings(null);
    productD.setMappedDistributors(null);
    productD.setCatalogDistrMapping(null);
    pForm.setProduct(productD);

    return ae;
  }
 //------------------------------------------------------------------------------

  /**
  * Adds selected distributor to the list of distiributors of the item
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ItemMgrMasterForm</code> value
   * @return ActionErrors
  */

  public static ActionErrors addDistributor (HttpServletRequest request,
			    ItemMgrMasterForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
    if (factory==null) {
        throw new Exception("No APIAccess.");
    }
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    Catalog catalogEjb = factory.getCatalogAPI();
    Distributor distributorEjb = factory.getDistributorAPI();
    ProductData productD = pForm.getProduct();

    if(pForm.getNewDistributorId()!=null) {
      try {
        int distributorId=Integer.parseInt(pForm.getNewDistributorId());
        DistributorData distributorD = distributorEjb.getDistributor(distributorId);
        productD.addMappedDistributor(distributorD.getBusEntity());


        if(pForm.getNewDistributorSku()==null ||
           pForm.getNewDistributorSku().trim().length()==0) {
	     ae.add("error1",new ActionError("variable.empty.error","Sku"));
	}else{
          productD.setDistributorSku(distributorId, pForm.getNewDistributorSku());
        }

        if(pForm.getNewDistributorPack()==null ||
           pForm.getNewDistributorPack().trim().length()==0) {
	     ae.add("error1",new ActionError("variable.empty.error","Pack"));
	}else{
          productD.setDistributorPack(distributorId, pForm.getNewDistributorPack());
        }

        if(pForm.getNewDistributorUom()==null ||
           pForm.getNewDistributorUom().trim().length()==0) {
	     ae.add("error1",new ActionError("variable.empty.error","Uom"));
	}else{
          productD.setDistributorUom(distributorId, pForm.getNewDistributorUom());
        }

        pForm.setNewDistributor(null);
        pForm.setNewDistributorId("");
        pForm.setNewDistributorSku("");
        pForm.setNewDistributorUom("");
        pForm.setNewDistributorPack("");
        pForm.setDistributorBox(new String[0]);

      }catch(DataNotFoundException exc) {
        ae.add("error", new ActionError("item.master.no_distributor"));
      }catch(NumberFormatException exc) {
        ae.add("error", new ActionError("item.master.no_distributor"));
      }
    }else{
      ae.add("error", new ActionError("item.master.no_distributor"));
    }
    return ae;
  }

  /**
  * Removes a set of marked distributors from the list of distributors
  *
  */
  public static ActionErrors removeDistributor (HttpServletRequest request,
			    ItemMgrMasterForm pForm)
    throws Exception
  {
    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    String[] delete=pForm.getDistributorBox();
    ProductData productD = pForm.getProduct();
    BusEntityDataVector distrDV = productD.getMappedDistributors();
    ItemMappingDataVector distrMappingDV=productD.getDistributorMappings();
    if(delete!=null) {
      for(int ii=0; ii<delete.length; ii++) {
        int ind = Integer.parseInt(delete[ii]);
        BusEntityData distrD = (BusEntityData) distrDV.get(ind);
        int id = distrD.getBusEntityId();
        for(int jj=0; jj<distrMappingDV.size(); jj++) {
          ItemMappingData distrMappingD = (ItemMappingData)distrMappingDV.get(jj);
          if(distrMappingD.getBusEntityId()==id) {
            distrMappingDV.remove(jj);
            break;
          }
        }
        distrD.setBusEntityId(0);
      }
      for(int ii=0; ii<distrDV.size();) {
        BusEntityData distrD = (BusEntityData) distrDV.get(ii);
        if(distrD.getBusEntityId()==0) {
           distrDV.remove(ii);
        } else {
           ii++;
        }
      }
    }
    pForm.setDistributorBox(new String[0]);
    //Change offset if necessary
    int pageSize = pForm.getDistrListPageSize();
    int size = pForm.getDistrListCount();
    int endPage = (size-1)/pageSize;
    int endOffset = endPage*pageSize;
    if(pForm.getDistrListOffset()>endOffset) {
      pForm.setDistrListOffset(endOffset);
    }
    return ae;
  }

  public static ActionErrors addFromLookup (HttpServletRequest request,
			    ItemMgrMasterForm pForm)
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
    Hashtable distrSkus = (Hashtable) session.getAttribute("lookupValue");
    if(distrSkus!=null) {
      Enumeration enume = distrSkus.keys();
      while (enume.hasMoreElements()) {
        Integer key = (Integer) enume.nextElement();

        DistributorItemDescData dItemDescD = (DistributorItemDescData)distrSkus.get(key);
        //String skuNum = (String) distrSkus.get(key);
        String skuNum = dItemDescD.getItemNum();
        String uom    = dItemDescD.getItemUom();
        String pack   = dItemDescD.getItemPack();
        int distributorId = key.intValue();
        DistributorData distributorD = distributorEjb.getDistributor(distributorId);
        productD.addMappedDistributor(distributorD.getBusEntity());
        productD.setDistributorSku(distributorId, skuNum);
        productD.setDistributorUom(distributorId, uom);
        productD.setDistributorPack(distributorId, pack);
      }
      pForm.setNewDistributor(null);
      pForm.setNewDistributorId("");
      pForm.setNewDistributorSku("");
      pForm.setNewDistributorUom("");
      pForm.setNewDistributorPack("");
      pForm.setDistributorBox(new String[0]);
    }

    return ae;

  }

    /**
     * <code>uploadFile</code> is a method that completes the action of
     * uploading an image file, a MSDS file, a DED file or a product spec.
     *
     * @param productD a <code>ProductData</code> value that is used
     * to get the product item_id.  This is used to name the resulting
     * upload file (e.g. 1010.jpg, 1010.pdf, etc.)  The product image,
     * msds, ded field is filled with the resultant upload filename.
     * @param fileType a <code>String</code> value that is one of
     * "images", "msds", "ded", "spec"
     * @param file a <code>FormFile</code> value  The FormFile to be
     * read.
     * @return an <code>ActionError</code> value
     */
    public static ActionError uploadFile(ProductData productD,
					 String fileType,
					 FormFile file)
    {
	ActionError ae = null;

	// Don't know any other way to discern if the file exists
	// or is readable, or some other problem
	if (file.getFileSize() == 0) {
	    ae = new ActionError("item.master.bad_upload_file",
				 file.getFileName());
	    return ae;
	}

	// get the file extension (e.g. ".jpg", ".pdf", etc.)
	String fileExt = null;
	String uploadFileName = file.getFileName();
	int i = uploadFileName.lastIndexOf(".");
	if (i < 0) {
	    fileExt = "";
	} else {
	    fileExt = uploadFileName.substring(i);
	}

	// this is the path to be saved in the database
	String basepath =
	    "/en/products/" + fileType + "/"
	    + String.valueOf(productD.getItemData().getItemId())
	    + fileExt;

	// this is the absolute path where we will be writing
	String fileName = System.getProperty("webdeploy") + basepath;

	try {
	    //retrieve the file data
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    InputStream stream = file.getInputStream();
	    OutputStream bos = new FileOutputStream(fileName);
	    int bytesRead = 0;
	    byte[] buffer = new byte[8192];
	    while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
		bos.write(buffer, 0, bytesRead);
	    }
	    bos.close();

	    //close the stream
	    stream.close();
	}
	catch (IOException ioe) {
	    ae = new ActionError("item.master.upload_file_error",
				 ioe.getMessage());
	    return ae;
	}

	if (fileType.equals("images")) {
	    productD.setImage(basepath);
	} else if (fileType.equals("msds")) {
	    productD.setMsds(basepath);
	} else if (fileType.equals("ded")) {
	    productD.setDed(basepath);
	} else if (fileType.equals("spec")) {
	    productD.setSpec(basepath);
	}

	// clear the contents???
	file.destroy();

	return ae;
    }

    /**
     * Updates existing of adds new product to MASTER product
     *
     */
    public static ActionErrors saveMasterProduct (HttpServletRequest request,
						  ItemMgrMasterForm pForm)
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
	Manufacturer manufacturerEjb = factory.getManufacturerAPI();

	ProductData productD = pForm.getProduct();
        boolean clwSwitch = ClwCustomizer.getClwSwitch();
	//Check for mandatory fields
        if(!clwSwitch) {
          if(pForm.getSkuNum()==null || pForm.getSkuNum().trim().length()==0) {
	  ae.add("error1",new ActionError("variable.empty.error","Sku"));
	  }
        }
        if(productD.getShortDesc()==null || productD.getShortDesc().trim().length()==0) {
	    ae.add("error1",new ActionError("variable.empty.error","Item Name"));
	}
	if(productD.getSize()==null || productD.getSize().trim().length()==0) {
	    ae.add("error3",new ActionError("variable.empty.error","Item Size"));
	}
	if(productD.getPack()==null || productD.getPack().trim().length()==0) {
	    ae.add("error4",new ActionError("variable.empty.error","Pack"));
	}
	if(productD.getUom()==null || productD.getUom().trim().length()==0) {
	    ae.add("error5",new ActionError("variable.empty.error","UOM"));
	}
	if(pForm.getManufacturerSku()==null || pForm.getManufacturerSku().trim().length()==0) {
	    ae.add("error6",new ActionError("variable.empty.error","Manufacturer SKU"));
	}
	if(pForm.getManufacturerName()==null || pForm.getManufacturerName().trim().length()==0) {
	    ae.add("error7",new ActionError("variable.empty.error","Manufacturer Id"));
	}
	if(productD.getLongDesc()==null || productD.getLongDesc().trim().length()==0) {
	    ae.add("error8",new ActionError("variable.empty.error","Long Description"));
	}

        //Set sku number
        if(!clwSwitch) {
          int skuNum = 0;
          String skuNumS = pForm.getSkuNum();
          try {
           skuNum = Integer.parseInt(skuNumS);
          }catch(Exception exc) {
	    ae.add("error", new ActionError("error.simpleGenericError","Wrong Sku format. Sku: "+skuNumS));
	    return ae;
          }
          if(skuNum==0 && skuNum<0) {
	    ae.add("error", new ActionError("error.simpleGenericError","Wrong Sku value. Sku: "+skuNum));
	    return ae;
          }
          productD.setSkuNum(skuNum);
        }
        //Set effective and expiration dates
	String effDateS=pForm.getEffDate();
	if(effDateS==null || effDateS.trim().length()==0) {
	    ae.add("error", new ActionError("variable.empty.error","Active Date"));
	    return ae;
	} else {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    Date effDate = null;
	    try {
		effDate  = simpleDateFormat.parse(effDateS);
		productD.setEffDate(effDate);
	    } catch (Exception e) {
		ae.add("error", new ActionError("item.master.wrong_effdate_format"));
	    }
	}
	String expDateS=pForm.getExpDate();
	if(expDateS==null || expDateS.trim().length()==0) {
	    productD.setExpDate(null);
	} else {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    Date expDate = null;
	    try {
		expDate  = simpleDateFormat.parse(expDateS);
		productD.setExpDate(expDate);
	    } catch (Exception e) {
		ae.add("error", new ActionError("item.master.wrong_expdate_format"));
	    }
	}

	//Set manufacturer
	String manuIdS=pForm.getManufacturerId();
	if(manuIdS!=null && manuIdS.trim().length()>0) {
	    try {
		int manuId = Integer.parseInt(manuIdS);
		ManufacturerData manufacturerD= manufacturerEjb.getManufacturer(manuId);
		productD.setManufacturer(manufacturerD.getBusEntity());
		String manuSku = pForm.getManufacturerSku();
		productD.setManuMapping(manufacturerD.getBusEntity(), manuSku);

	    }catch(DataNotFoundException exc) {
		ae.add("error", new ActionError("item.master.no_manufacturer"));
	    }catch(NumberFormatException exc) {
		ae.add("error", new ActionError("item.master.no_manufacturer"));
	    }
	}

	//Set list price
	String listPriceS = pForm.getListPrice();
	if(listPriceS!=null && listPriceS.trim().length()>0) {

        BigDecimal listPriceBD = new BigDecimal("0.0");
	    try {
		listPriceBD = CurrencyFormat.parse(listPriceS);
		productD.setListPrice(listPriceBD.doubleValue());
	    } catch (ParseException pe) {
		ae.add("error", new ActionError("item.master.wrong_list_price"));
	    }

	} else {
	    productD.setListPrice(0);
	}

	//Set cost price
	String costPriceS = pForm.getCostPrice();
	if(costPriceS!=null && costPriceS.trim().length()>0) {

        BigDecimal costPriceBD = new BigDecimal("0.0");
	    try {
		costPriceBD = CurrencyFormat.parse(costPriceS);
		productD.setCostPrice(costPriceBD.doubleValue());
	    } catch (ParseException pe) {
		ae.add("error", new ActionError("item.master.wrong_cost_price"));
	    }

	} else {
	    productD.setCostPrice(0);
	}

	int catalogId = pForm.getCatalogId();
	if(catalogId==0) {
	    ae.add("error", new ActionError("item.master.no_catalog"));
	    return ae;
	}

	if(ae.size() > 0) {
	    return ae;
	}

	// If inserting, do it now.  Update can wait until after any
	// files are uploaded.  Reason is that the upload needs the
	// item_id to name the uploaded files.  This is not known until
	// after insertion.  So for a new item, we'll do an insert,
	// followed by an update.  Not the most efficient, but if we
	// want to use the item_id as the basis for the filenames...
    boolean createFlag = false;
	boolean needToSave = true;
	if (productD.getItemData().getItemId() == 0) {
          try {
	    productD=catalogEjb.saveCatalogProduct(catalogId, productD,user);
          }catch(Exception exc) {
            String mess = exc.getMessage();
            if(mess==null) mess="";
            int ind = mess.indexOf("Data error.");
            if (ind>=0) {
              mess = mess.substring(ind+"Data error.".length());
              ind = mess.indexOf(';');
              if(ind>0) mess = mess.substring(0,ind);
                ae.add("error",new ActionError("error.simpleGenericError",mess));
                return ae;
            } else {
              throw exc;
            }
          }
	    pForm.setProduct(productD);
	    needToSave = false;
        createFlag = true;
	}

	FormFile imageFile = pForm.getImageFile();
	if (imageFile != null && !imageFile.getFileName().equals("")) {
	    ActionError ne = uploadFile(productD, "images", imageFile);
	    if (ne != null) {
		ae.add("error", ne);
		return ae;
	    }
	    needToSave = true;
	}
	FormFile msdsFile = pForm.getMsdsFile();
	if (msdsFile != null && !msdsFile.getFileName().equals("")) {
	    ActionError ne = uploadFile(productD, "msds", msdsFile);
	    if (ne != null) {
		ae.add("error", ne);
		return ae;
	    }
	    needToSave = true;
	}
	FormFile dedFile = pForm.getDedFile();
	if (dedFile != null && !dedFile.getFileName().equals("")) {
	    ActionError ne = uploadFile(productD, "ded", dedFile);
	    if (ne != null) {
		ae.add("error", ne);
		return ae;
	    }
	    needToSave = true;
	}
	FormFile specFile = pForm.getSpecFile();
	if (specFile != null && !specFile.getFileName().equals("")) {
	    ActionError ne = uploadFile(productD, "spec", specFile);
	    if (ne != null) {
		ae.add("error", ne);
		return ae;
	    }
	    needToSave = true;
	}

	// if we're doing an update, or an insert with one or more
	// uploaded files
	if (needToSave) {
          try {
	    catalogEjb.saveCatalogProduct(catalogId, productD, user);
          }catch(Exception exc) {
            String mess = exc.getMessage();
            if(mess==null) mess="";
            int ind = mess.indexOf("Data error.");
            if (ind>=0) {
              mess = mess.substring(ind+"Data error.".length());
              ind = mess.indexOf(';');
              if(ind>0) mess = mess.substring(0,ind);
                ae.add("error",new ActionError("error.simpleGenericError",mess));
                return ae;
            } else {
              throw exc;
            }
          }
	}
	return ae;
    }


  /**
   * <code>Go To Page</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void goPage(HttpServletRequest request,
			    ItemMgrMasterForm pForm)
    throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    String pageNumS = request.getParameter("page");
    int pageNum = Integer.parseInt(pageNumS);
    int pageSize = pForm.getDistrListPageSize();
    pForm.setDistrListOffset(pageNum*pageSize);
    pForm.setDistributorBox(new String[0]);
  }
    /**
     * Verifies input for store specific data
     *
     */
    public static ActionErrors verifyStoreData (HttpServletRequest request,
						  ItemMgrMasterForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	ActionErrors ae = new ActionErrors();
        //Jd Store
        if("jd".equals(ClwCustomizer.getStoreDir())) {
          ProductData productD = pForm.getProduct();
          if(productD==null) {
            ae.add("error", new ActionError("error.simpleGenericError","Product data not found"));
            return ae;
          }
          String cas70FtlPriceS = pForm.getCas70FtlPrice();
          if(cas70FtlPriceS!=null && cas70FtlPriceS.trim().length()>0) {
            try {
              double cas70FtlPrice = Double.parseDouble(cas70FtlPriceS);
              productD.setProductAttribute(cas70FtlPriceS,"CAS70_FTL_PRICE");
            } catch (Exception exc) {
              ae.add("error", new ActionError("error.simpleGenericError","Wrong CAS70_FTL_PRICE format: "+cas70FtlPriceS));
              return ae;
            }
          }
          String cas70ListPriceS = pForm.getCas70ListPrice();
          if(cas70ListPriceS!=null && cas70ListPriceS.trim().length()>0) {
            try {
              double cas70ListPrice = Double.parseDouble(cas70ListPriceS);
              productD.setProductAttribute(cas70ListPriceS,"CAS70_LIST_PRICE");
            } catch (Exception exc) {
              ae.add("error", new ActionError("error.simpleGenericError","Wrong CAS70_LIST_PRICE format: "+cas70ListPriceS));
              return ae;
            }
          }
          String cas80FtlPriceS = pForm.getCas80FtlPrice();
          if(cas80FtlPriceS!=null && cas80FtlPriceS.trim().length()>0) {
            try {
              double cas80FtlPrice = Double.parseDouble(cas80FtlPriceS);
              productD.setProductAttribute(cas80FtlPriceS,"CAS80_FTL_PRICE");
            } catch (Exception exc) {
              ae.add("error", new ActionError("error.simpleGenericError","Wrong CAS80_FTL_PRICE format: "+cas80FtlPriceS));
              return ae;
            }
          }
          String cas80ListPriceS = pForm.getCas80ListPrice();
          if(cas80ListPriceS!=null && cas80ListPriceS.trim().length()>0) {
            try {
              double cas80ListPrice = Double.parseDouble(cas80ListPriceS);
              productD.setProductAttribute(cas80ListPriceS,"CAS80_LIST_PRICE");
            } catch (Exception exc) {
              ae.add("error", new ActionError("error.simpleGenericError","Wrong CAS80_LIST_PRICE format: "+cas80ListPriceS));
              return ae;
            }
          }
          String ftlUsPriceS = pForm.getFtlUsPrice();
          if(ftlUsPriceS!=null && ftlUsPriceS.trim().length()>0) {
            try {
              double ftlUsPrice = Double.parseDouble(ftlUsPriceS);
              productD.setProductAttribute(ftlUsPriceS,"FTL_US_PRICE");
            } catch (Exception exc) {
              ae.add("error", new ActionError("error.simpleGenericError","Wrong FTL_US_PRICE format: "+ftlUsPriceS));
              return ae;
            }
          }
          String listUsPriceS = pForm.getListUsPrice();
          if(listUsPriceS!=null && listUsPriceS.trim().length()>0) {
            try {
              double listUsPrice = Double.parseDouble(listUsPriceS);
              productD.setProductAttribute(listUsPriceS,"LIST_US_PRICE");
            } catch (Exception exc) {
              ae.add("error", new ActionError("error.simpleGenericError","Wrong LIST_US_PRICE format: "+listUsPriceS));
              return ae;
            }
          }

        }
        return ae;
    }

}



