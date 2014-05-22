package com.cleanwise.service.api.session;

/**
 * Title:        ProductBean
 * Description:  Bean implementation for Product Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving product information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Calendar;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;

public class ProductBean extends CatalogServicesAPI
{
  /**
   *
   */
  public ProductBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   * Adds the product information values to be used by the request.
   * @param pProduct  the ProductData product data.
   * @param request  the ProductRequestData product request data.
   * @return new ProductRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductRequestData addProduct(ProductData pProduct,
                ProductRequestData request)
      throws RemoteException
  {
    Connection con=null;
    ItemData itemD = pProduct.getItemData();
    ProductRequestData productRD;
    try {
      con = getConnection();
      Calendar calendar=Calendar.getInstance();
      Date date = calendar.getTime();
      itemD.setAddBy("unknown");
      itemD.setAddDate(date);
      itemD.setModBy("unknown");
      itemD.setModDate(date);

      ItemData newItemData = ItemDataAccess.insert(con,itemD);
      int productId=itemD.getItemId();
      productRD= new ProductRequestData(productId);
      ItemMetaDataVector imDV =  pProduct.getProductAttributes();
      for(int ii=0; ii<imDV.size(); ii++) {
          ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);
          if(itemMetaD!=null) {
              itemMetaD.setAddDate(date);
              itemMetaD.setModDate(date);
              itemMetaD = ProductDAO.saveItemMetaInfo(con, productId, "PB", itemMetaD);
              pProduct.setItemMeta(itemMetaD,itemMetaD.getNameValue());
          }
      }
    }catch(NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. ProductBean.addProduct() Naming Exception happened");
    }catch(SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. ProductBean.addProduct() SQL Exception happened");
    }catch(Exception exc) {
      logError("2005-8-15, exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. ProductBean.addProduct() problem");
    }finally{
          closeConnection(con);
    }

    return productRD;
  }

  /**
   * Updates the product information values to be used by the request.
   * @param pUpdateProductData  the ProductData product data.
   * @param pProductId the product identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateProduct(ProductData pUpdateProductData,
                int pProductId)
      throws RemoteException
  {
  }

  /**
   * Adds the product content information values to be used by the request.
   * @param pProductContent  the ProductContentData product content data.
   * @param request  the ProductContentRequestData product content request data.
   * @return new ProductContentRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductContentRequestData addProductContent(ProductContentData pProductContent,
                ProductContentRequestData request)
      throws RemoteException
  {
    return new ProductContentRequestData();
  }

  /**
   * Updates the product content information values to be used by the request.
   * @param pUpdateProductContentData  the ProductContentData product content data.
   * @param pProductId the product identifier.
   * @param pContentId the content identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateProductContent(ProductContentData pUpdateProductContentData,
                int pProductId, int pContentId)
      throws RemoteException
  {
  }

  /**
   * Adds the product meta information values to be used by the request.
   * @param pProductMeta  the product meta data.
   * @param request  the product meta request data.
   * @return new ProductMetaRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductMetaRequestData addProductMeta(ProductMetaData pProductMeta,
                ProductMetaRequestData request)
      throws RemoteException
  {
    return new ProductMetaRequestData();
  }

  /**
   * Updates the product meta information values to be used by the request.
   * @param pUpdateProductMetaData  the ProductMetaData product meta data.
   * @param pProductId the product identifier.
   * @param pNameId the name identifier.
   * @param pValueId the value identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateProductMeta(ProductMetaData pUpdateProductMetaData,
                int pProductId, int pNameId, int pValueId)
      throws RemoteException
  {
  }

  /**
   * Adds the meta information values to be used by the request.
   * @param pMeta  the meta data.
   * @param request  the meta request data.
   * @return new MetaRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public MetaRequestData addMeta(MetaData pMeta,
                MetaRequestData request)
      throws RemoteException
  {
    return new MetaRequestData();
  }

  /**
   * Updates the meta information values to be used by the request.
   * @param pUpdateMetaData  the MetaData meta data.
   * @param pNameId the name identifier.
   * @param pValueId the value identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateMeta(MetaData pUpdateMetaData,
                int pNameId, int pValueId)
      throws RemoteException
  {
  }

  /**
   * Adds the product price information values to be used by the request.
   * @param pProductPrice  the product price data.
   * @param request  the product price request data.
   * @return new ProductPriceRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductPriceRequestData addProductPrice(ProductPriceData pProductPrice,
                ProductPriceRequestData request)
      throws RemoteException
  {
    return new ProductPriceRequestData();
  }

  /**
   * Updates the product price information values to be used by the request.
   * @param pUpdateProductPriceData  the product price data.
   * @param pProductId the product identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateProductPrice(ProductPriceData pUpdateProductPriceData,
                int pProductId)
      throws RemoteException
  {
  }

  /**
   * Adds the product mapping information values to be used by the request.
   * @param pProductMapping  the product mapping data.
   * @param request  the product mapping request data.
   * @return new ProductMappingRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductMappingRequestData addProductMapping(ProductMappingData pProductMapping,
                ProductMappingRequestData request)
      throws RemoteException
  {
    return new ProductMappingRequestData();
  }

  /**
   * Updates the product mapping information values to be used by the request.
   * @param pUpdateProductMappingData  the product mapping data.
   * @param pProductId the product identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateProductMapping(ProductMappingData pUpdateProductMappingData,
                int pProductId)
      throws RemoteException
  {
  }

  /**
   * Adds the product SKU information values to be used by the request.
   * @param pProductSKU  the product SKU data.
   * @param request  the product SKU request data.
   * @return new ProductSKURequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductSKURequestData addProductSKU(ProductSKUData pProductSKU,
                ProductSKURequestData request)
      throws RemoteException
  {
    return new ProductSKURequestData();
  }

  /**
   * Updates the product SKU information values to be used by the request.
   * @param pUpdateProductSKUData  the product SKU data.
   * @param pProductId the product identifier.
   * @param pSKUId the sku identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateProductSKU(ProductSKUData pUpdateProductSKUData,
                int pProductId, int pSKUId)
      throws RemoteException
  {
  }


}
