package com.cleanwise.service.api.session;



/**

 * Title:        Catalog

 * Description:  Remote Interface for Catalog Stateless Session Bean

 * Purpose:      Provides access to the methods for establishing and maintaining the catalog..

 * Copyright:    Copyright (c) 2001

 * Company:      CleanWise, Inc.

 * @author       Liang Li, CleanWise, Inc.


 */



import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;



public interface Catalog extends javax.ejb.EJBObject

{



  /**

   * Adds the catalog information values to be used by the request.

   * @param pCatalog  the catalog data.

   * @param request  the catalog request data.

   * @return new CatalogRequestData()

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogRequestData addCatalog(CatalogData pCatalog,int pStoreId, String user)

      throws RemoteException, DataNotFoundException, DuplicateNameException;



  /**

   * Updates the catalog information values to be used by the request.

   * @param pUpdateCatalogData  the catalog data.

   * @param pCatalogId the catalog identifier.

   * @return none

   * @throws            RemoteException Required by EJB 1.0

   */

  public void updateCatalog(CatalogData pUpdateCatalogData, String user)

      throws RemoteException, DuplicateNameException;



  public void updateItem(ItemData itemData) throws RemoteException;



  /**

   * Adds the catalog structure information values to be used by the request.

   * @param pCatalogStructure  the catalog structure data.

   * @param request  the catalog structure request data.

   * @return new CatalogStructureRequestData()

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogStructureRequestData addCatalogStructure(CatalogStructureData pCatalogStructure,

                CatalogStructureRequestData request, String user)

      throws RemoteException;



  /**

   * Removes the catalog from database if it does not have structure or associations

   * @param pCatalogId  the catalog id.

   * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

   */

  public void removeCatalog (int pCatalogId, String user)

    throws RemoteException, DataNotFoundException;



  /**

   * Removes the catalog from database with it stucture and associations

   * @param pCatalogId  the catalog id.

   * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

   */

  public CatalogStructureData saveCatalogStructure(CatalogStructureData csd) throws RemoteException;



  public void removeCatalogCascade (int pCatalogId, String user)

    throws RemoteException, DataNotFoundException;

  /**

   * Updates the catalog structure information values to be used by the request.

   * @param pUpdateCatalogStructureData  the catalog structure data.

   * @param pCatalogId the catalog identifier.

   * @param pCatalogStructureId the catalog structure identifier.

   * @return none

   * @throws            RemoteException Required by EJB 1.0

   */



  public void updateCatalogStructure(CatalogStructureData pUpdateCatalogStructureData,

                int pCatalogId, int pCatalogStructureId, String user)

      throws RemoteException;



  /**

   * Adds the catalog association information values to be used by the request.

   * @param pCatalogAssoc  the catalog association data.

   * @return new CatalogAssocRequestData()

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogAssocRequestData addCatalogAssoc(CatalogAssocData pCatalogAssoc, String user)

      throws RemoteException;



  /**

   * Adds the catalog association information values to be used by the request.

   * @param pCatalogId  the catalog id.

   * @param pBusEntityId  the business entity id.

   * @param user user login id

   * @return new CatalogAssocRequestData()

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogAssocRequestData addCatalogAssoc(int pCatalogId, int pBusEntityId, String user)

      throws RemoteException, DataNotFoundException;



  /**

   * Updates the catalog association information values to be used by the request.

   * @param pUpdateCatalogAssocData  the catalog association data.

   * @param pCatalogId the catalog identifier.

   * @param pBusEntityId the business entity identifier.

   * @param pCatalogAssocId the catalog association identifier.

   * @return none

   * @throws            RemoteException Required by EJB 1.0

   */

  public void updateCatalogAssoc(CatalogAssocData pUpdateCatalogAssocData,

                int pCatalogId, int pBusEntityId, int pCatalogAssocId)

      throws RemoteException;



  /**

   * Removes the catalog association information values

   * @param pCatalogId  the catalog id.

   * @param pBusEntityId  the business entity id.

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogAssoc(int pCatalogId, int pBusEntityId, String user)

      throws RemoteException;


  public void removeCatalogAccountAssoc(int pCatalogId, int pAccountId, String user) throws

      RemoteException ;


    /**

     * Removes the catalog site association information values

     * @param pCatalogId  the catalog id.

     * @param pBusEntityId  the business entity id.

     * @throws            RemoteException Required by EJB 1.0

     */

    public void removeCatalogSiteAssoc(int pCatalogId, int pBusEntityId, String user)

  throws RemoteException;



    /**

     * Adds or updates product for catalog- without deleting existing item meta info

     * @param pCatalogId  the catalog id.

     * @param productD  ProductData object

     * @param user user login id

     * @return ProductData

     * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

     */

    public ProductData saveMasterCatalogProduct(int pCatalogId, ProductData productD,

            String user) throws RemoteException,DataNotFoundException;

   /**

   * Adds or updates product for catalog

   * @param pCatalogId  the catalog id.

   * @param productD  ProductData object

   * @param user user login id

   * @return ProductData

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public ProductData saveCatalogProduct(int pCatalogId, ProductData productD, String user)

    throws RemoteException, DataNotFoundException;



    /**

     * The distributor of every product in the catalog will be updated

     * to the given distributor.  The distributor must be a distributor

     * for the catalog and a distributor of the product.  If it is not,

     * the product distributor will not be changed.

     * @param pCatalogId  the catalog id.

     * @param pDistributorId the distributor id.

     * @param pOldDistId distributor to be replaced or 0 (item should not have addegned

     * distibutor) or -1 (all distributors)

     * @param pUser the login user name

     * @return items, which the distributor does not carry

     * @throws            RemoteException

     */

    public ItemDataVector updateProductDistributor

        (int pCatalogId,

         int pDistributorId,

         int pOldDistId,

         String pUser)

  throws RemoteException, DataNotFoundException;



    // Same as above, except only update one item.

    public void updateProductDistributor

        (int pCatalogId,

         int pNewDistributorId,

         int pOldDistId,

         int pItemId,

         String pUser)

        throws RemoteException, DataNotFoundException;



   /**

   * Adds or updates category for catalog

   * @param pCatalogId  the catalog id.

   * @param catalogCategoryD  CatalogCategoryData object

   * @param user user login id

   * @return CatalogCategoryData

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public CatalogCategoryData saveCatalogCategory(int pCatalogId, CatalogCategoryData catalogCategoryD, String user)

    throws RemoteException, DataNotFoundException;



   /**

   * Removes category from catalog

   * @param pCatalogId  the catalog id.

   * @param pCatalogCategoryId  the category id

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogCategory(int pCatalogId, int pCatalogCategoryId, String user)

    throws RemoteException;



   /**

   * Removes product from catalog

   * @param pCatalogId  the catalog id.

   * @param pProductId  the product id

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */



  public void removeCatalogProduct(int pCatalogId, int pProductId, String user)

    throws RemoteException;



   /**

   * Removes category-product subtree from catalog. No structure loops assumed

   * @param pCatalogId  the catalog id.

   * @param pItemIdV  a set of ids of removing subtrees root elements

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogSubTrees(int pCatalogId, IdVector pItemIdV, String user)

    throws RemoteException;



   /**

   * Removes all existing parents for the sub tree (moves to the catalog top)

   * @param pCatalogId  the catalog id.

   * @param pItemId  the subtree root item id

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */



  public void untieCatalogSubTree(int pCatalogId, int pItemId, String user)

    throws RemoteException, DataNotFoundException;



   /**

   * Creates a copy of exitsting Catalog

   * @param pCatalogId  the source catalog id

   * @param pShortDesc new catalog short description

   * @param pCatalogType new catalog type code

   * @param pCatalogStatus new catalog status code

   * @param pStoreId new catalog store identificator

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogData createCatalogFromCatalog(int pCatalogId,

    String pShortDesc, String pCatalogType, String pCatalogStatus, int pStoreId, String user)

    throws RemoteException, DataNotFoundException, DuplicateNameException;



   /**

   * Adds catalog relation

   * @param pCatalogId  the catalog id.

   * @param pParenId  Product or Category id

   * @param pChildIdV  IdVector of Product or Category ids

   * @param user user login id

   * @return IdVector id vector of really inserted items

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

   public IdVector addCatalogElements(int pCatalogId, int pParentId, IdVector pChildIdV, String user)

     throws RemoteException, DataNotFoundException;



   /**

   * Adds catalog relation

   * @param pCatalogId  the catalog id.

   * @param pParenId  Product or Category id

   * @param pChildId  Product or Category id

   * @param user user login id

   * @return ItemAssocDate

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public ItemAssocData addCatalogTreeNode(int pCatalogId, int pParentId, int pChildId, String user)

    throws RemoteException, DataNotFoundException;

  public ItemAssocData addCatalogTreeNode(int pCatalogId, int pParentId, int pChildId, String user, boolean allowMixedCategoryAndItemUnderSameParent)
  throws RemoteException, DataNotFoundException;

    public void removeCategoryFromItem(int pCatalogId,

               int pCatalogCategoryId,

               int pItemId,

               String user)

  throws RemoteException;



    public ProductData addProductCategory(int pCatalogId,

          int pCategoryId,

          int pItemId,

          String user)

      throws RemoteException, DataNotFoundException;
  public ProductData addProductCategory(int pCatalogId,
        int pCategoryId,
        int pItemId,
        String user,
       AccCategoryToCostCenterView pCategToCostCenterView)

    throws RemoteException, DataNotFoundException;



    public void resetCatalogAssoc(int pCatalogId,

          int pBusEntityId,

          String user)

  throws RemoteException, DataNotFoundException;



    /**

     * Gets next available sku number

     * @return max sku number plus 1

     * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

     */

    public int getNextSkuNum()

    throws RemoteException;



    /**

     * Adds or updates product of store catalog

     * @param pStoreId  the store id.

     * @param pCatalogId  the catalog id.

     * @param productD  ProductData object

     * @param user user login id

     * @return ProductData

     * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

     */

    public ProductData saveStoreCatalogProduct(int pStoreId, int pCatalogId, ProductData productD, String user)

       throws RemoteException;



     /**

      * Adds or updates distributor mappings of the product

      * @param productD  ProductData object

      * @param user user login id

      * @return ProductData

      * @throws            RemoteException Required by EJB 1.0

      */

     public ProductData saveDistributorMapping(ProductData productD, String user)

        throws RemoteException;



    /**

     *Saves store category

     * @param pStoreCatalogId store catalog id

     * @param pMajorCategoryId major category id

     * @param pMajorCCategoryName category name

     * @param pUser user name

     *@throws RemoteException

     */

    public ItemData saveStoreMajorCategory(int pStoreCatalogId,

             int pMajorCategoryId, String pMajorCategoryName, String pUser)

    throws RemoteException;



    /**

     *Saves store category

     * @param pStoreCatalogId store catalog id

     * @param pCategoryId category id

     * @param pCategoryName category name

     * @param pMajorCategoryId major category id

     * @param pUser user name

     *@throws RemoteException

     */

    public CatalogCategoryData saveStoreCategory(int pStoreCatalogId,

            int pCategoryId, String pCategoryName,

            int pMajorCategoryId, String pUser)

    throws RemoteException;



    public CatalogCategoryData saveStoreCategory(int storeCatalogId, int categoryId,

        String categoryName, String adminCategoryName, int majorCategoryId,

            String user) throws RemoteException;



    /**

     *Removes major category

     * @param pStoreCatalogId store catalog id

     * @param pMajorCategoryId major category id

     *@throws RemoteException

     */

    public void deleteStoreMajorCategory(int pStoreCatalogId, int pMajorCategoryId)

    throws RemoteException;



    /**

     *Removed store category

     * @param pStoreCatalogId store catalog id

     * @param pCategoryId major category id

     *@throws RemoteException

     */

    public void deleteStoreCategory(int pStoreCatalogId, int pCategoryId)

    throws RemoteException;



    public MultiproductView saveStoreMultiproduct(int pStoreCatalogId,

        int pMultiproductId, String pMultiproductName, String pUser)

            throws RemoteException;



    public void deleteStoreMultiproduct(int pStoreCatalogId, int pMultiproductId)

        throws RemoteException;



    /**

     * Creates new catalog-contract pair

     * @param pCatalog  catalog data.

     * @param pContract  contract data.

     * @param pCopyContractItemFl copies contract items if true and parent contract exists

     * @param pOrderGuids set of OrderGuideDescData objects

     * @param pStoreId  store id

     * @parem pParentCatalogId template catalog id

     * @param user  user login name

     * @return catalog and contract objects

     * @throws   RemoteException Required by EJB 1.0

     */

    public CatalogContractView addCatalogContract(CatalogData pCatalog,

       ContractData pContract, boolean pCopyContractItemFl,

       OrderGuideDescDataVector pOrderGuides, int pStoreId,

       int pParentCatalogId, String user, boolean pUpdatePriceFromLoader)

     throws RemoteException, DataNotFoundException, DuplicateNameException;



    /**

     * Updates catalog-contract pair

     * @param pCatalog  catalog data.

     * @param pContract  contract data.

     * @param pOrderGuides set of OrderGuideDescData objects

     * @param pStoreId  store id

     * @param user  user login name

     * @return catalog and contract objects

     * @throws   RemoteException, DataNotFoundException, DuplicateNameException

     */

    public CatalogContractView updateCatalogContract(CatalogData pCatalog,

       ContractData pContract, OrderGuideDescDataVector pOrderGuides, int pStoreId, String user, boolean pUpdatePriceFromLoader)

     throws RemoteException, DataNotFoundException, DuplicateNameException;



  /**

   * Removes the catalog from database with it stucture and associations

   * @param pCatalogId  the catalog id.

   * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

   */



  public void removeCatalogContract (int pCatalogId, String user)

  throws RemoteException, DataNotFoundException;



  /**

   * Saves item - catalog,item - contract, item  - distributor,

   * item - distributor - genenric manufacture, item - contract data

   * @param itemAggrSet set of ItemCatalogAggrView objects

   * @param user - user login name

   * @throws            RemoteException

   */

//  public void saveItemCatalogMgrSet (ItemCatalogAggrViewVector itemAggrSet, String user)

//  throws RemoteException;



    /**

     * Saves item - catalog,item - contract, item  - distributor,

     * item - distributor - genenric manufacture, item - contract data

     *

     * @param itemAggrSet set of ItemCatalogAggrView objects

     * @param user        - user login name

     * @param pItemTypeCd - item type

     * @throws java.rmi.RemoteException Required by EJB 1.0 and DataNotFoundException

     */

    public void saveItemCatalogMgrSet1(ItemCatalogAggrViewVector itemAggrSet,String user, String pItemTypeCd)

    throws RemoteException;





    /**

     * Addes category to clw_catalog_structure table

   * @param pCategoryId  the catalog id.

   * @param pCatalogIds  the catalog id vector.

   * @param user - user login name

   * @throws            RemoteException

   */

  public void addCategoryToCatalogs (int pCategoryId, IdVector pCatalogIds, String user)

  throws RemoteException;



    /**

     * Addes category to clw_catalog_structure table

     *

     * @param pCategoryId the catalog id.

     * @param pCatalogIds the catalog id vector.

     * @param pItemTypeCd the item type (Service or product).

     * @throws RemoteException

     */

    public void removeCategoryFromCatalogs(int pCategoryId, IdVector pCatalogIds, String pItemTypeCd) throws RemoteException;





    public void resetCostCenters(int pCatalogId, String user)

  throws RemoteException;

   public void resetAccountCostCenters(int pCatalogId, String user)
		throws RemoteException;



  public void setCategoryCostCenter(int pCatalogId,

                                    String pCatalogCategoryName,

                                    int pNewCostCenterId,

                                    String user)

                             throws RemoteException;


  public void setAccountCategoryCostCenter(int pCatalogId,
                                    String pCatalogCategoryName,
                                    int pNewCostCenterId,
                                    String user)
                             throws RemoteException;



    public CatalogAssocRequestData addMainDistributorAssocCd(int pCatalogId,int pDistrId, String user)

    throws DataNotFoundException, RemoteException;



     public void removeCatalogAssoc(int pCatalogAssocId, String user) throws

     RemoteException;



    /**

     * Adds or updates service of store catalog

     * @param pStoreId  the store id.

     * @param pCatalogId  the catalog id.

     * @param pService  ItemData object

     * @param user user login id

     * @return ItemData

     * @throws RemoteException Required by EJB 1.0, DataNotFoundException

     */

    public ItemData updateServiceData(int pStoreId, int pCatalogId,ItemData pService, String user) throws RemoteException ;



    public void removeCatalogStructure(int pCatalogId, String user) throws RemoteException;



    /**

     * Verifies whether categroy exists and creates it if it doesn't exist

     * @param pCatalogId  the store catalog id.

     * @param pCategoryHS set of category names

     * @param pUserName user name for add_by and mod_by fields

     * @return Returns HashMap of category CatalogCategoryData objects whith category names as keys

     * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

     */

   public HashMap createOrRequestStoreCategories(int pStoreId, HashSet pCategoryHS, String pUserName)

    throws RemoteException;



     /**

     * Adds, updates and removes NSC items

     * @param pStoreId  NSC store id.

     * @param pStoreCatalogId  the store catalog id.

     * @param pDistrId NSC distirbutor id (only one for the store)

     * @param pProductDV set of ProductData object populated by loader

     * @param pUserName user name for add_by and mod_by fields

     * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

     */

    public void createOrUpdateNscProducts(int pStoreId, int pStoreCatalogId, int pDistId,

           ProductDataVector pProductDV, String pUserName)

   throws RemoteException;



    public void createOrUpdateNscMasterProducts(int pStoreId, int pAcctId, int pStoreCatalogId, int pDistId,

 		   ProductDataVector pProductDV, String pUserName)

    throws RemoteException;



    public CatalogAssocDataVector getCatalogAssocCollection(IdVector pBusEntityIds,

            String pCatalogAssocCd) throws RemoteException ;



    public CatalogDataVector getCatalogCollection(DBCriteria pCrit) throws

    RemoteException ;



    public int createCatalog( String pCatalogName, String pCatalogType, String pCatalogStatus, String pAddBy) throws RemoteException, DuplicateNameException;



    public void createCatalogAssoc( int pCatalogId, int pBusEntityId, String pAssocType, String pAddBy)throws

    RemoteException ;

    public void createOrUpdateNscProducts(int pStoreId, int pStoreCatalogId, int pDistId,

                  ProductDataVector pProductDV,  String pUserName, HashMap nscItemNumHM, boolean updateItemDescrFl, boolean updateUpcFl) throws RemoteException ;

    public void createCatalogContractAndOGAssociation(int pAccountId,  int pCatalogId, String pCatalogName, String pCatalogStatusCd, String pUserName ) throws RemoteException ;
    public void updateItemMeta(int pItemId, ItemMetaDataVector imDV, String pAddBy) throws RemoteException ;

    public CatalogStructureData getCatalogStructureData(int pCatalogId, int pItemId) throws RemoteException, DataNotFoundException ;

    public IdVector getSubCatalogs(int pCatalogId, int pItemId) throws RemoteException;

	public int checkCatalogAssocExist(int pAccountId) throws RemoteException;

    public void updateProductCategory(ProductDataVector pProducts, String pUserName) throws RemoteException;
    
    public BusEntityData getStoreForCatalog(int pCatalogId) throws RemoteException;
    
    public void addCategoryWithParentCategoriesToCatalogs(IdVector pCatalogIdV,
            int pCategoryId, String pUserName) throws RemoteException;
}

