
package com.cleanwise.view.logic;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.Troubleshooter;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.KnowledgeContentData;
import com.cleanwise.service.api.value.KnowledgeContentDataVector;
import com.cleanwise.service.api.value.KnowledgeData;
import com.cleanwise.service.api.value.KnowledgeDescDataVector;
import com.cleanwise.service.api.value.KnowledgePropertyData;
import com.cleanwise.service.api.value.KnowledgePropertyDataVector;
import com.cleanwise.service.api.value.KnowledgeSearchCriteriaData;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.view.forms.KnowledgeOpContentDetailForm;
import com.cleanwise.view.forms.KnowledgeOpDetailForm;
import com.cleanwise.view.forms.KnowledgeOpNoteDetailForm;
import com.cleanwise.view.forms.KnowledgeOpSearchForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;

/**
 * <code>KnowledgeOpLogic</code> implements the logic needed to
 * manipulate knowledge records.
 *
 * @author Liang
 */
public class KnowledgeOpLogic {

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
			    ActionForm form)
	throws Exception {
            
        initConstantList(request);
	//searchAll(request, form);    
	return;
    }
  
  
    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void search(HttpServletRequest request,
			      ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	KnowledgeOpSearchForm sForm = (KnowledgeOpSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
         
	Troubleshooter troubleshooterEjb   = factory.getTroubleshooterAPI();
        
        KnowledgeSearchCriteriaData searchCriteria = KnowledgeSearchCriteriaData.createValue();
        
        if( sForm.getProductName().trim().length() > 0 ) {
            searchCriteria.setProductName(sForm.getProductName().trim());
        }
        if( sForm.getSkuNum().trim().length() > 0 ) {
            searchCriteria.setSkuNum(sForm.getSkuNum().trim());
        }
        if( sForm.getDescription().trim().length() > 0 ) {
            searchCriteria.setDescription(sForm.getDescription().trim());
        }
        if( ! "".equals(sForm.getCategoryCd())) {
            searchCriteria.setCategoryCd(sForm.getCategoryCd().trim());
        }
        if( sForm.getDistributorId().trim().length() > 0 ) {
            searchCriteria.setDistributorId(sForm.getDistributorId().trim());
        }
        if( sForm.getManufacturerId().trim().length() > 0 ) {
            searchCriteria.setManufacturerId(sForm.getManufacturerId().trim());
        }
        //if( ! "".equals(sForm.getKnowledgeStatusCd()) ) {
        //    searchCriteria.setKnowledgeStatusCd(sForm.getKnowledgeStatusCd());
        //}
        if( sForm.getDateRangeBegin().trim().length() > 0 ) {
            searchCriteria.setDateRangeBegin(sForm.getDateRangeBegin().trim());
        }
        if( sForm.getDateRangeEnd().trim().length() > 0 ) {
            searchCriteria.setDateRangeEnd(sForm.getDateRangeEnd().trim());
        }
        
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            searchCriteria.setStoreId(appUser.getUserStore().getStoreId());
        }
        
        KnowledgeDescDataVector knowledgeDesc;
        knowledgeDesc = troubleshooterEjb.getKnowledgeDescCollection(searchCriteria);
            
	sForm.setResultList(knowledgeDesc);
    }

  
    /**
     * <code>searchAll</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void searchAll(HttpServletRequest request,
				 ActionForm form)
	throws Exception {

	HttpSession session = request.getSession();
	KnowledgeOpSearchForm sForm = (KnowledgeOpSearchForm)form;
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
        
	Troubleshooter troubleshooterEjb   = factory.getTroubleshooterAPI();
        
        KnowledgeSearchCriteriaData searchCriteria = KnowledgeSearchCriteriaData.createValue();

        String userId = (String)session.getAttribute(Constants.USER_ID);                
        sForm.setKnowledgeStatusCd(RefCodeNames.KNOWLEDGE_STATUS_CD.PENDING);
        
        searchCriteria.setUserId(userId);
        searchCriteria.setKnowledgeStatusCd(RefCodeNames.KNOWLEDGE_STATUS_CD.PENDING);
        KnowledgeDescDataVector knowledgeDesc = new KnowledgeDescDataVector();
        knowledgeDesc = troubleshooterEjb.getKnowledgeDescCollection(searchCriteria);
            
	sForm.setResultList(knowledgeDesc);    
    }
  
    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */    
    public static void sort(HttpServletRequest request,
			    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	KnowledgeOpSearchForm sForm = (KnowledgeOpSearchForm)form;
	if (sForm == null) {
	    return;
	}
	KnowledgeDescDataVector knowledges = 
	    (KnowledgeDescDataVector)sForm.getResultList();
	if (knowledges == null) {
	    return;
	}
        
	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(knowledges, sortField);
        
    }


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

        if (null == session.getAttribute("Knowledge.status.vector")) {
	    RefCdDataVector statusv =
		listServiceEjb.getRefCodesCollection("KNOWLEDGE_STATUS_CD");
	    session.setAttribute("Knowledge.status.vector", statusv);
	}
        
        if (null == session.getAttribute("Knowledge.category.vector")) {
	    RefCdDataVector categoryv =
		listServiceEjb.getRefCodesCollection("KNOWLEDGE_CATEGORY_CD");
	    session.setAttribute("Knowledge.category.vector", categoryv);
	}
        
    }

    
    /**
     * <code>initUserList</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void initUserList(HttpServletRequest request,
			    ActionForm form, String formType)
	throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("Without APIAccess.");
        }    

        User userEjb = factory.getUserAPI();
        UserDataVector customerServiceUserList = userEjb.getUsersCollectionByType(
                                    RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE);
        UserDataVector crcManagerUserList = userEjb.getUsersCollectionByType(
                                    RefCodeNames.USER_TYPE_CD.CRC_MANAGER);
        customerServiceUserList.addAll(crcManagerUserList);
        
        /*
        if ("search".equals(formType)) {
            KnowledgeOpSearchForm sForm = (KnowledgeOpSearchForm)form;
            sForm.setCustomerServiceUserList(customerServiceUserList);
        }
        else if ("detail".equals(formType)) {
            KnowledgeOpDetailForm dForm = (KnowledgeOpDetailForm)form;
            dForm.setCustomerServiceUserList(customerServiceUserList);
        }
         */
    }
      

    /**
     * <code>AddKnowledgeNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */        
    public static void addKnowledgeNote(
				   HttpServletRequest request,
				   ActionForm form,
                                   String knowledgeId)
	throws Exception {
        
	KnowledgeOpNoteDetailForm dForm = (KnowledgeOpNoteDetailForm) form;
        HttpSession session = request.getSession();
        
	dForm = new KnowledgeOpNoteDetailForm();

	if( null == knowledgeId || "".equals(knowledgeId)) {
	    knowledgeId = (String)session.getAttribute("Knowledge.id");
	}

        KnowledgeOpDetailForm knowledgeForm = (KnowledgeOpDetailForm) session.getAttribute("KNOWLEDGE_OP_DETAIL_FORM");
        if (null != knowledgeForm && null != knowledgeId && ! "".equals(knowledgeId)) {
            editKnowledgeDetail(request, knowledgeForm, knowledgeId);
        }
        
        dForm.setKnowledgeId(knowledgeId);
    
        session.setAttribute("KNOWLEDGE_OP_NOTE_DETAIL_FORM", dForm);
	//session.setAttribute("Knowledge.id", "");
    
    }
 

    /**
     * <code>editKnowledgeDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param knowledgeId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static void editKnowledgeNote(
				    HttpServletRequest request,
				    ActionForm form,
				    String knowledgeId,
                                    String noteId)
	throws Exception {
            
	KnowledgeOpNoteDetailForm dForm = (KnowledgeOpNoteDetailForm) form;

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
            
	Troubleshooter  troubleshooterEjb   = factory.getTroubleshooterAPI();        
        
	if( null == knowledgeId || "".equals(knowledgeId)) {
	    knowledgeId = (String)session.getAttribute("Knowledge.id");
	}

        KnowledgeOpDetailForm knowledgeForm = (KnowledgeOpDetailForm) session.getAttribute("KNOWLEDGE_OP_DETAIL_FORM");
        if (null != knowledgeForm && null != knowledgeId && ! "".equals(knowledgeId)) {
            editKnowledgeDetail(request, knowledgeForm, knowledgeId);
        }
        
        dForm.setKnowledgeId(knowledgeId);
        
        KnowledgePropertyData noteD = troubleshooterEjb.getKnowledgeProperty(Integer.parseInt(noteId));

        dForm.setNoteDetail(noteD);
                        
        session.setAttribute("KNOWLEDGE_OP_NOTE_DETAIL_FORM", dForm);
	//session.setAttribute("Knowledge.id", knowledgeId);
    
    }

    
    
    /**
     * <code>saveKnowledgeNote</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */  
    public static ActionErrors saveKnowledgeNote(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {
	
	 ActionErrors lUpdateErrors = new ActionErrors();
         
         HttpSession session = request.getSession();
	 APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	 if (null == factory) {
            throw new Exception("Without APIAccess.");
	 }    
		  
        KnowledgeOpNoteDetailForm dForm = (KnowledgeOpNoteDetailForm) form;
		 
        if(dForm != null){
            if (dForm.getNoteDetail().getShortDesc().trim().length() == 0) {
                lUpdateErrors.add("notes",  new ActionError("variable.empty.error", "Note Description"));
            }		 
            if (dForm.getNoteDetail().getValue().trim().length() == 0) {
                lUpdateErrors.add("notes",  new ActionError("variable.empty.error", "Note Comments"));
            }		             
        }
		 
	if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }
          
        int knowledgeId = Integer.parseInt(dForm.getKnowledgeId());
		 		
        Troubleshooter troubleshooterEjb = factory.getTroubleshooterAPI();	
		
	KnowledgePropertyData noteD = dForm.getNoteDetail();				
		
	noteD.setKnowledgeId(knowledgeId);
	noteD.setKnowledgePropertyStatusCd("ACTIVE");
	noteD.setKnowledgePropertyTypeCd(RefCodeNames.KNOWLEDGE_PROPERTY_TYPE_CD.KNOWLEDGE_NOTE);

        String userName = (String)session.getAttribute(Constants.USER_NAME);                
        if ( 0 ==  noteD.getKnowledgePropertyId()) {
            noteD.setAddBy(userName);
        }
	noteD.setModBy(userName);
		
	troubleshooterEjb.addKnowledgeProperty(noteD);
	
	return lUpdateErrors;
		
    }
    

    /**
     * <code>AddKnowledgeContent</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */        
    public static void addKnowledgeContent(
				   HttpServletRequest request,
				   ActionForm form,
                                   String knowledgeId)
	throws Exception {
        
	KnowledgeOpContentDetailForm dForm = (KnowledgeOpContentDetailForm) form;
        HttpSession session = request.getSession();
        
	dForm = new KnowledgeOpContentDetailForm();

	if( null == knowledgeId || "".equals(knowledgeId)) {
	    knowledgeId = (String)session.getAttribute("Knowledge.id");
	}

        KnowledgeOpDetailForm knowledgeForm = (KnowledgeOpDetailForm) session.getAttribute("KNOWLEDGE_OP_DETAIL_FORM");
        if (null != knowledgeForm && null != knowledgeId && ! "".equals(knowledgeId)) {
            editKnowledgeDetail(request, knowledgeForm, knowledgeId);
        }
        
        dForm.setKnowledgeId(knowledgeId);
    
        session.setAttribute("KNOWLEDGE_OP_CONTENT_DETAIL_FORM", dForm);
	//session.setAttribute("Knowledge.id", "");
    
    }
 

    /**
     * <code>editKnowledgeContentDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param knowledgeId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static void editKnowledgeContent(
				    HttpServletRequest request,
				    ActionForm form,
				    String knowledgeId,
                                    String contentId)
	throws Exception {
            
	KnowledgeOpContentDetailForm dForm = (KnowledgeOpContentDetailForm) form;

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
            
	Troubleshooter  troubleshooterEjb   = factory.getTroubleshooterAPI();        
        
	if( null == knowledgeId || "".equals(knowledgeId)) {
	    knowledgeId = (String)session.getAttribute("Knowledge.id");
	}

        KnowledgeOpDetailForm knowledgeForm = (KnowledgeOpDetailForm) session.getAttribute("KNOWLEDGE_OP_DETAIL_FORM");
        if (null != knowledgeForm && null != knowledgeId && ! "".equals(knowledgeId)) {
            editKnowledgeDetail(request, knowledgeForm, knowledgeId);
        }
        
        dForm.setKnowledgeId(knowledgeId);
        
        KnowledgeContentData contentD = troubleshooterEjb.getKnowledgeContent(Integer.parseInt(contentId));

        dForm.setContentDetail(contentD);
                        
        session.setAttribute("KNOWLEDGE_OP_CONTENT_DETAIL_FORM", dForm);
    
    }

    
    
    /**
     * <code>saveKnowledgeContent</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */  
    public static ActionErrors saveKnowledgeContent(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {
	
         ActionErrors lUpdateErrors = new ActionErrors();
         
         HttpSession session = request.getSession();
	 APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	 if (null == factory) {
            throw new Exception("Without APIAccess.");
	 }    
		  
        KnowledgeOpContentDetailForm dForm = (KnowledgeOpContentDetailForm) form;
		 
        if(dForm != null){
            if (dForm.getContentDetail().getLongDesc().trim().length() == 0) {
                lUpdateErrors.add("contents",  new ActionError("variable.empty.error", "Content Description"));
            }		 
            //if (dForm.getContentDetail().getContentUrl().trim().length() == 0) {
            //    lUpdateErrors.add("contents",  new ActionError("variable.empty.error", "Content URL"));
            //}		             
        }
		 
	if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }
          
        int knowledgeId = Integer.parseInt(dForm.getKnowledgeId());
		 		
        Troubleshooter troubleshooterEjb = factory.getTroubleshooterAPI();	
		
	KnowledgeContentData contentD = dForm.getContentDetail();				
		
	contentD.setKnowledgeId(knowledgeId);

        String userName = (String)session.getAttribute(Constants.USER_NAME);                
        if( 0 == contentD.getKnowledgeContentId()) {
            contentD.setAddBy(userName);
        }
	contentD.setModBy(userName);
		
	// If inserting, do it now.  Update can wait until after any
	// files are uploaded.  Reason is that the upload needs the
	// content_id to name the uploaded files.  This is not known until
	// after insertion.  So for a new content, we'll do an insert,
	// followed by an update.  Not the most efficient, but if we
	// want to use the content_id as the basis for the filenames...
	boolean needToSave = true;
	if (contentD.getKnowledgeContentId() == 0) {
	    contentD = troubleshooterEjb.addKnowledgeContent(contentD);
	    dForm.setContentDetail(contentD);
	    needToSave = false;
	}
	
	FormFile contentFile = dForm.getContentFile();
	if (contentFile != null && !contentFile.getFileName().equals("")) {
        
	    ActionError ne = uploadFile(contentD, "contents", contentFile);
	    if (ne != null ) {
		lUpdateErrors.add("error", ne);
		return lUpdateErrors;
	    }
	    needToSave = true;
	}
        
	// if we're doing an update, or an insert with one or more
	// uploaded files
	if (needToSave) {
	    troubleshooterEjb.addKnowledgeContent(contentD);
	}
        
	return lUpdateErrors;
		
    }
    
        

    /**
     * <code>uploadFile</code> is a method that completes the action of
     * uploading a content file, PDF format or HTML or else.
     *
     * @param file a <code>FormFile</code> value  The FormFile to be
     * read.
     * @return an <code>ActionError</code> value
     */
    public static ActionError uploadFile(KnowledgeContentData contentD,
					 String fileType,
					 FormFile file)
    {
	ActionError ae = null;

	// Don't know any other way to discern if the file exists
	// or is readable, or some other problem
	if ( 0 == file.getFileSize() ) {
	    ae = new ActionError("error.simpleGenericError",  
				 file.getFileName());
	    return ae;
	}

	// get the file extension (e.g. ".jpg", ".pdf", etc.)
	String fileExt = new String ("");
	String uploadFileName = file.getFileName();
        int index = uploadFileName.lastIndexOf(".");
	if ( 0 <= index ) {
	    fileExt = uploadFileName.substring(index);
	}

	// this is the path to be saved in the database
	String basepath = 
	    "/en/knowledgebase/" + fileType + "/"
	    + String.valueOf(contentD.getKnowledgeContentId()) 
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
	    ae = new ActionError("error.simpleGenericError", 
				 ioe.getMessage());
	    return ae;
	}

	contentD.setContentUrl(basepath);

	// clear the contents???
	file.destroy();

	return ae;
    }
    
    
    /**
     * <code>AddKnowledgeDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */        
    public static void addKnowledgeDetail(
				   HttpServletRequest request,
				   ActionForm form )
	throws Exception {
        
        
	KnowledgeOpDetailForm dForm = (KnowledgeOpDetailForm) form;
        
	dForm = new KnowledgeOpDetailForm();

        HttpSession session = request.getSession();
        session.setAttribute("KNOWLEDGE_OP_DETAIL_FORM", dForm);
	session.setAttribute("Knowledge.id", "");
    
	//initialize the comstants lists 
	initConstantList(request);    
    }
 

    
    /**
     * <code>editKnowledgeDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @param knowledgeId a <code>String</code> value
     * @exception Exception if an error occurs
     */  
    public static void editKnowledgeDetail(
				    HttpServletRequest request,
				    ActionForm form,
				    String knowledgeId)
	throws Exception {

        
	KnowledgeOpDetailForm dForm = (KnowledgeOpDetailForm) form;

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
        
	Troubleshooter  troubleshooterEjb   = factory.getTroubleshooterAPI();        
        
	if( null == knowledgeId || "".equals(knowledgeId)) {
	    knowledgeId = (String)session.getAttribute("Knowledge.id");
	}        

        KnowledgeData knowledgeD = troubleshooterEjb.getKnowledge(Integer.parseInt(knowledgeId));


        KnowledgePropertyDataVector knowledgeNotesList = 
                    troubleshooterEjb.getKnowledgePropertyCollection(Integer.parseInt(knowledgeId), 
                                        RefCodeNames.KNOWLEDGE_PROPERTY_TYPE_CD.KNOWLEDGE_NOTE);
        if (null != knowledgeNotesList) {
            dForm.setKnowledgeNotesList(knowledgeNotesList);
        }

        KnowledgeContentDataVector knowledgeContentsList = 
                    troubleshooterEjb.getKnowledgeContentCollection(Integer.parseInt(knowledgeId)); 
        if (null != knowledgeContentsList) {
            dForm.setKnowledgeContentsList(knowledgeContentsList);
        }
        
        dForm.setKnowledgeDetail(knowledgeD);        
        
        if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.PRODUCT_FEATURES_BENEFITS.equals(knowledgeD.getKnowledgeCategoryCd())
            && 0 != knowledgeD.getItemId() ) {
            CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
            ProductData productD = catalogInfoEjb.getProduct(knowledgeD.getItemId());
            if (null != productD) {
                dForm.setProductDetail(productD);
                dForm.setItemId(String.valueOf(knowledgeD.getItemId()) );
                
                dForm.setProductName(productD.getShortDesc());
                dForm.setItemSize(productD.getSize());
                dForm.setItemPack(productD.getPack());
                dForm.setItemUom(productD.getUom());
                dForm.setItemManufName(productD.getManufacturerName());
                dForm.setItemCwSku(String.valueOf(productD.getSkuNum()));        
                dForm.setItemManufSku(productD.getManufacturerSku());
            }
        }
        else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.DISTRIBUTORS.equals(knowledgeD.getKnowledgeCategoryCd())
            && 0 != knowledgeD.getBusEntityId() ) {
            Distributor distEjb = factory.getDistributorAPI();
            DistributorData distributorD = distEjb.getDistributor(knowledgeD.getBusEntityId());
            if (null != distributorD) {
                dForm.setDistributorDetail(distributorD);
                dForm.setBusEntityId(String.valueOf(knowledgeD.getBusEntityId()) );
            }
        }
        else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.MANUFACTURERS.equals(knowledgeD.getKnowledgeCategoryCd())
            && 0 != knowledgeD.getBusEntityId() ) {
            Manufacturer manufEjb = factory.getManufacturerAPI();
            ManufacturerData manufD = manufEjb.getManufacturer(knowledgeD.getBusEntityId());
            if (null != manufD) {
                dForm.setManufacturerDetail(manufD);
                dForm.setBusEntityId(String.valueOf(knowledgeD.getBusEntityId()) );
            }                
        }        
        
        session.setAttribute("KNOWLEDGE_OP_DETAIL_FORM", dForm);
	session.setAttribute("Knowledge.id", knowledgeId);
    
	//initialize the comstants lists
	initConstantList(request);    
    }



    /**
     * <code>fetchOrderInfo</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */  
    public static void fetchOrderInfo(
				    HttpServletRequest request,
				    ActionForm form )
	throws Exception {

        /*
	KnowledgeOpDetailForm dForm = (KnowledgeOpDetailForm) form;

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
    
        
	Order  orderEjb   = factory.getOrderAPI();        

        String changeField =  request.getParameter("changefield");
        
        if ( null == changeField || "".equals(changeField) ) {
            return;
        }
        else {
            OrderStatusCriteriaData orderCriteriaD = OrderStatusCriteriaData.createValue();
            boolean emptyFlag = false;
            if ( "erpOrderNum".equals(changeField) ) {
                if ( dForm.getErpOrderNum().trim().length() > 0 ) {
                    orderCriteriaD.setErpOrderNum(dForm.getErpOrderNum());
                }
                else {
                    emptyFlag = true;
                }
            }
            else if ( "webOrderNum".equals(changeField) ) {
                if ( dForm.getWebOrderNum().trim().length() > 0 ) {                
                    orderCriteriaD.setWebOrderConfirmationNum(dForm.getWebOrderNum());
                }
                else {
                    emptyFlag = true;
                }
            }
            else if ( "custPoNum".equals(changeField) ) {
                if ( dForm.getCustPoNum().trim().length() > 0 ) {
                    orderCriteriaD.setCustPONum(dForm.getCustPoNum());
                }
                else {
                    emptyFlag = true;
                }
            }
            else if ( "erpPoNum".equals(changeField) ) {
                if ( dForm.getErpPoNum().trim().length() > 0 ) {
                    orderCriteriaD.setErpPONum(dForm.getErpPoNum());
                }
                else {
                    emptyFlag = true;
                }
            } 
            else {
                emptyFlag = true;
            }
            
            int orderStatusId = 0 ;
            String erpOrderNum = new String("");
            String webOrderNum = new String("");
            String custPoNum = new String("");
            String erpPoNum = new String(""); 
            if (false == emptyFlag ) {
                OrderStatusDescDataVector orderStatusDescV = orderEjb.getOrderStatusDescCollection(orderCriteriaD);
                
                if (null != orderStatusDescV && orderStatusDescV.size() > 0 ) {
                    OrderStatusDescData orderStatusDesc = (OrderStatusDescData) orderStatusDescV.get(0);
                    orderStatusId = orderStatusDesc.getOrderStatus().getOrderStatusId();
                    erpOrderNum = orderStatusDesc.getOrderStatus().getErpOrderNumber();
                    webOrderNum = String.valueOf(orderStatusDesc.getOrderStatus().getFrontEndOrderNumber());
                    custPoNum = orderStatusDesc.getOrderStatus().getCustomerPoNumber();                    
                    
                    if ( null != orderStatusDesc.getErpPoList() && orderStatusDesc.getErpPoList().size() > 0 ) {
                        ErpPoDataVector erpPoV = orderStatusDesc.getErpPoList();
                        for (int i = 0 ; i < orderStatusDesc.getErpPoList().size(); i++) {
                            ErpPoData erpPoD = (ErpPoData)erpPoV.get(i);
                            if ( null != erpPoD.getErpPoNumber() && ! "".equals(erpPoD.getErpPoNumber()) ) {  
                                erpPoNum = erpPoD.getErpPoNumber();
                                break;
                            }
                        } // end of loop for erpPoList
                    } // end of if erpPoList exists
                } // end of if orderStatusDescV exists
            } // end of if emptyFlag == false
            
            dForm.getKnowledgeDetail().setOrderStatusId(orderStatusId);
            dForm.setErpOrderNum(erpOrderNum);
            dForm.setWebOrderNum(webOrderNum);
            dForm.setCustPoNum(custPoNum);
            dForm.setErpPoNum(erpPoNum);
        }

        return;
         */
    }
    
    
    /**
     * <code>saveKnowledgeDetail</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */  
    public static ActionErrors saveKnowledgeDetail(
				    HttpServletRequest request,
				    ActionForm form)
	throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();

        KnowledgeOpDetailForm dForm = (KnowledgeOpDetailForm) form;

	int orgKnowledgeId = dForm.getKnowledgeDetail().getKnowledgeId();

	// Get a reference to the admin facade.
	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (null == factory) {
	    throw new Exception("Without APIAccess.");
	}    
        
	Troubleshooter troubleshooterEjb = factory.getTroubleshooterAPI();

	KnowledgeData detail = dForm.getKnowledgeDetail();
 
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        String role = appUser.getUser().getUserRoleCd();
        String crcRole = "";
        StringTokenizer st = new StringTokenizer(role, "^", false);
        while (st.hasMoreTokens()) {
          String permission = st.nextToken();

          if(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER.equals(permission)  ||            
             RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.PUBLISHER.equals(permission) ||  
             RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.VIEWER.equals(permission)) {

            crcRole = permission;
          }    
        }
        //all new non-approver knowledge items are defaulted to pending
        if(orgKnowledgeId == 0 && 
           !crcRole.equals(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER)){  
            
          detail.setKnowledgeStatusCd(RefCodeNames.KNOWLEDGE_STATUS_CD.PENDING);
        }

        int storeId = 0;
        
        if (detail.getKnowledgeCategoryCd().trim().length() == 0) {
            lUpdateErrors.add("knowledgedetail",  new ActionError("variable.empty.error", "Category"));
        }		 
        
        if(crcRole.equals(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER)){
          if (detail.getKnowledgeStatusCd().trim().length() == 0) {
            lUpdateErrors.add("knowledgedetail",  new ActionError("variable.empty.error", "Knowledge Status"));
          }
        }	
        	 
        if (detail.getLongDesc().trim().length() == 0) {
            lUpdateErrors.add("knowledgedetail",  new ActionError("variable.empty.error", "Knowledge Description"));
        }		 
        if (detail.getComments().trim().length() == 0) {
            lUpdateErrors.add("knowledgedetail",  new ActionError("variable.empty.error", "Knowledge Comments"));
        }		 
        if ( RefCodeNames.KNOWLEDGE_CATEGORY_CD.DISTRIBUTORS.equals(detail.getKnowledgeCategoryCd())              
              && dForm.getBusEntityId().trim().length() == 0 ) {
            lUpdateErrors.add("knowledgedetail",  new ActionError("variable.empty.error", "Distributor Id"));
        }
        if ( RefCodeNames.KNOWLEDGE_CATEGORY_CD.MANUFACTURERS.equals(detail.getKnowledgeCategoryCd())              
              && dForm.getBusEntityId().trim().length() == 0 ) {
            lUpdateErrors.add("knowledgedetail",  new ActionError("variable.empty.error", "Manufacturer Id"));
        }
         if ( RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            if(!Utility.isSet(dForm.getStoreId())) {
                lUpdateErrors.add("storeId",  new ActionError("variable.empty.error", "Store Id"));
            }else{
                try{
                    storeId = Integer.parseInt(dForm.getStoreId());
                }catch(NumberFormatException e){
                    lUpdateErrors.add("storeId",  new ActionError("error.invalidNumber", "Store Id"));
                }
            }
        }else{
            storeId = appUser.getUserStore().getStoreId();
        }
        
	if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }        
        
        // check if the itemId or busEntity Id is correct
        if ( 0 == orgKnowledgeId ) {
            
            // if the catagory is for item
            if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.PRODUCT_FEATURES_BENEFITS.equals(detail.getKnowledgeCategoryCd()) ) {
                String itemId = dForm.getItemId();
                if (null != itemId && !"".equals(itemId)) {
                    CatalogInformation catalogInfoEjb = factory.getCatalogInformationAPI();
                    ProductData productD = null;
                    try {
                        productD = catalogInfoEjb.getProduct(Integer.parseInt(itemId));                                     
                    }
                    catch (Exception e) {
                        productD = null;
                    }
                    if( null == productD) {
                        lUpdateErrors.add("knowledgedetail", new ActionError("bad.item"));
                        return lUpdateErrors;                            
                    }                    
                }                
            }
            // if the catagory is for distributor or manufacturer
            else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.DISTRIBUTORS.equals(detail.getKnowledgeCategoryCd()) 
                    || RefCodeNames.KNOWLEDGE_CATEGORY_CD.MANUFACTURERS.equals(detail.getKnowledgeCategoryCd()) ) {
                
                String busEntityId = dForm.getBusEntityId();
                if (null != busEntityId && !"".equals(busEntityId)) {
                    // for distributor
                    if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.DISTRIBUTORS.equals(detail.getKnowledgeCategoryCd())) {
                        
                        Distributor distEjb = factory.getDistributorAPI();
                        DistributorData distD = null;
                        try {
                            distD = distEjb.getDistributor(Integer.parseInt(busEntityId));                                     
                        }
                        catch (Exception e) {
                            distD = null;
                        }
                        if( null == distD) {
                            lUpdateErrors.add("knowledgedetail", new ActionError("bad.distributor"));
                            return lUpdateErrors;                            
                        }                                            
                    }
                    // for manufacturer
                    else if (RefCodeNames.KNOWLEDGE_CATEGORY_CD.MANUFACTURERS.equals(detail.getKnowledgeCategoryCd())) {
                        
                        Manufacturer manufEjb = factory.getManufacturerAPI();
                        ManufacturerData manufD = null;
                        try {
                            manufD = manufEjb.getManufacturer(Integer.parseInt(busEntityId));                                     
                        }
                        catch (Exception e) {
                            manufD = null;
                        }
                        if( null == manufD) {
                            lUpdateErrors.add("knowledgedetail", new ActionError("bad.manufacturer"));
                            return lUpdateErrors;                            
                        }                                            
                    }                    
                }    // end of if the busentity  id is existing           
            } // end of if the category is distributor or manufacturer  
            StoreData storeD = null;
            try {
                Store storeEjb = factory.getStoreAPI();
                storeD = storeEjb.getStore(storeId);                                     
            }
            catch (Exception e) {
                storeD = null;
            }
            if(storeD == null){
                lUpdateErrors.add("knowledgedetail", new ActionError("bad.store"));
                return lUpdateErrors;
            }
        }
        
        String userName = (String)session.getAttribute(Constants.USER_NAME);                
        String userId = (String)session.getAttribute(Constants.USER_ID);          
        if ( 0 == orgKnowledgeId ) {
            detail.setAddBy(userName);
            detail.setUserId(Integer.parseInt(userId));
        }
        detail.setModBy(userName);        
        detail.setStoreId(storeId);
        if( null != dForm.getBusEntityId() && ! "".equals(dForm.getBusEntityId()) ) {
            detail.setBusEntityId(Integer.parseInt(dForm.getBusEntityId()));
        }
        if( null != dForm.getItemId() && ! "".equals(dForm.getItemId()) ) {
            detail.setItemId(Integer.parseInt(dForm.getItemId()));
        }
        
        KnowledgeData newDetail = KnowledgeData.createValue();
            
        newDetail = troubleshooterEjb.addKnowledge(detail);
        session.setAttribute("Knowledge.id", String.valueOf(newDetail.getKnowledgeId()));    
                 
        return lUpdateErrors;
    }

    
}
