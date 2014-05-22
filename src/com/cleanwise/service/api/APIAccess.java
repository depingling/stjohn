
/* DO NOT EDIT - Generated code from XSL file APIAcess.xsl */

package com.cleanwise.service.api;

/**
 * Title:        APIAcess
 * Description:  Access to EJB API
 * Purpose:
 * Copyright:    Copyright (c) 2003
 * Company:      Cleanwise, Inc.
 * @author       Generated Code from XSL file APIAcess.xsl
 */

import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.Utility;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class APIAccess {

    protected InitialContext ctx = null;
    protected static Object lock = new Object();
    protected static APIAccess apiAccess = null;

     /**
     *  Constructor for the APIAccess object
     *
     *@exception  javax.naming.NamingException  Description of Exception
     */
    public APIAccess() throws javax.naming.NamingException {
        ctx = getInitialContext();
    }

     /**
     *  Retrieve access to the singleton APIAccess object.
     *
     *@return                                   The APIAccess value
     *@exception  javax.naming.NamingException  Description of Exception
     *@throws  javax.namingNamingException      Thrown when the EJBFactory
     *      object can not be initialized.
     */
    public static APIAccess getAPIAccess()
             throws javax.naming.NamingException {
        synchronized (lock) {
            if (null == apiAccess) {
                apiAccess = new APIAccess();
            }
            return apiAccess;
        }
    }


    private ShoppingServicesHome mShoppingServicesHome;

    /**
     *  Obtains a reference to the remote interface for the ShoppingServices.
     *
     *@return                                The ShoppingServicesAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ShoppingServices getShoppingServicesAPI()
             throws APIServiceAccessException {
        ShoppingServices rtn = null;
        try {
            if (null == mShoppingServicesHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mShoppingServicesHome= (ShoppingServicesHome) ctx.lookup(JNDINames.SHOPPING_SERVICES_EJBHOME);
            }
            rtn = mShoppingServicesHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mShoppingServicesHome = (ShoppingServicesHome) ctx.lookup(JNDINames.SHOPPING_SERVICES_EJBHOME);
                rtn = mShoppingServicesHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private AccountHome mAccountHome;

    /**
     *  Obtains a reference to the remote interface for the Account.
     *
     *@return                                The AccountAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Account getAccountAPI()
             throws APIServiceAccessException {
        Account rtn = null;
        try {
            if (null == mAccountHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mAccountHome= (AccountHome) ctx.lookup(JNDINames.ACCOUNT_EJBHOME);
            }
            rtn = mAccountHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mAccountHome = (AccountHome) ctx.lookup(JNDINames.ACCOUNT_EJBHOME);
                rtn = mAccountHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private AuditHome mAuditHome;

    /**
     *  Obtains a reference to the remote interface for the Audit.
     *
     *@return                                The AuditAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Audit getAuditAPI()
             throws APIServiceAccessException {
        Audit rtn = null;
        try {
            if (null == mAuditHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mAuditHome= (AuditHome) ctx.lookup(JNDINames.AUDIT_EJBHOME);
            }
            rtn = mAuditHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mAuditHome = (AuditHome) ctx.lookup(JNDINames.AUDIT_EJBHOME);
                rtn = mAuditHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private EventHome mEventHome;

    /**
     *  Obtains a reference to the remote interface for the Event.
     *
     *@return                                The EventAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Event getEventAPI()
             throws APIServiceAccessException {
        Event rtn = null;
        try {
            if (null == mEventHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mEventHome= (EventHome) ctx.lookup(JNDINames.EVENT_EJBHOME);
            }
            rtn = mEventHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mEventHome = (EventHome) ctx.lookup(JNDINames.EVENT_EJBHOME);
                rtn = mEventHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private AutoOrderHome mAutoOrderHome;

    /**
     *  Obtains a reference to the remote interface for the AutoOrder.
     *
     *@return                                The AutoOrderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public AutoOrder getAutoOrderAPI()
             throws APIServiceAccessException {
        AutoOrder rtn = null;
        try {
            if (null == mAutoOrderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mAutoOrderHome= (AutoOrderHome) ctx.lookup(JNDINames.AUTO_ORDER_EJBHOME);
            }
            rtn = mAutoOrderHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mAutoOrderHome = (AutoOrderHome) ctx.lookup(JNDINames.AUTO_ORDER_EJBHOME);
                rtn = mAutoOrderHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private CWLocaleHome mCWLocaleHome;

    /**
     *  Obtains a reference to the remote interface for the CWLocale.
     *
     *@return                                The CWLocaleAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public CWLocale getCWLocaleAPI()
             throws APIServiceAccessException {
        CWLocale rtn = null;
        try {
            if (null == mCWLocaleHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mCWLocaleHome= (CWLocaleHome) ctx.lookup(JNDINames.C_W_LOCALE_EJBHOME);
            }
            rtn = mCWLocaleHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mCWLocaleHome = (CWLocaleHome) ctx.lookup(JNDINames.C_W_LOCALE_EJBHOME);
                rtn = mCWLocaleHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private SiteHome mSiteHome;

    /**
     *  Obtains a reference to the remote interface for the Site.
     *
     *@return                                The SiteAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Site getSiteAPI()
             throws APIServiceAccessException {
        Site rtn = null;
        try {
            if (null == mSiteHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mSiteHome= (SiteHome) ctx.lookup(JNDINames.SITE_EJBHOME);
            }
            rtn = mSiteHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mSiteHome = (SiteHome) ctx.lookup(JNDINames.SITE_EJBHOME);
                rtn = mSiteHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private CatalogHome mCatalogHome;

    /**
     *  Obtains a reference to the remote interface for the Catalog.
     *
     *@return                                The CatalogAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Catalog getCatalogAPI()
             throws APIServiceAccessException {
        Catalog rtn = null;
        try {
            if (null == mCatalogHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mCatalogHome= (CatalogHome) ctx.lookup(JNDINames.CATALOG_EJBHOME);
            }
            rtn = mCatalogHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mCatalogHome = (CatalogHome) ctx.lookup(JNDINames.CATALOG_EJBHOME);
                rtn = mCatalogHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private CatalogInformationHome mCatalogInformationHome;

    /**
     *  Obtains a reference to the remote interface for the CatalogInformation.
     *
     *@return                                The CatalogInformationAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public CatalogInformation getCatalogInformationAPI()
             throws APIServiceAccessException {
        CatalogInformation rtn = null;
        try {
            if (null == mCatalogInformationHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mCatalogInformationHome= (CatalogInformationHome) ctx.lookup(JNDINames.CATALOG_INFORMATION_EJBHOME);
            }
            rtn = mCatalogInformationHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mCatalogInformationHome = (CatalogInformationHome) ctx.lookup(JNDINames.CATALOG_INFORMATION_EJBHOME);
                rtn = mCatalogInformationHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ChangeLogHome mChangeLogHome;

    /**
     *  Obtains a reference to the remote interface for the ChangeLog.
     *
     *@return                                The ChangeLogAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ChangeLog getChangeLogAPI()
             throws APIServiceAccessException {
        ChangeLog rtn = null;
        try {
            if (null == mChangeLogHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mChangeLogHome= (ChangeLogHome) ctx.lookup(JNDINames.CHANGE_LOG_EJBHOME);
            }
            rtn = mChangeLogHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mChangeLogHome = (ChangeLogHome) ctx.lookup(JNDINames.CHANGE_LOG_EJBHOME);
                rtn = mChangeLogHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private StoreHome mStoreHome;

    /**
     *  Obtains a reference to the remote interface for the Store.
     *
     *@return                                The StoreAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Store getStoreAPI()
             throws APIServiceAccessException {
        Store rtn = null;
        try {
            if (null == mStoreHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mStoreHome= (StoreHome) ctx.lookup(JNDINames.STORE_EJBHOME);
            }
            rtn = mStoreHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mStoreHome = (StoreHome) ctx.lookup(JNDINames.STORE_EJBHOME);
                rtn = mStoreHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ContentHome mContentHome;

    /**
     *  Obtains a reference to the remote interface for the Content.
     *
     *@return                                The ContentAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Content getContentAPI()
             throws APIServiceAccessException {
        Content rtn = null;
        try {
            if (null == mContentHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mContentHome= (ContentHome) ctx.lookup(JNDINames.CONTENT_EJBHOME);
            }
            rtn = mContentHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mContentHome = (ContentHome) ctx.lookup(JNDINames.CONTENT_EJBHOME);
                rtn = mContentHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ContractHome mContractHome;

    /**
     *  Obtains a reference to the remote interface for the Contract.
     *
     *@return                                The ContractAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Contract getContractAPI()
             throws APIServiceAccessException {
        Contract rtn = null;
        try {
            if (null == mContractHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mContractHome= (ContractHome) ctx.lookup(JNDINames.CONTRACT_EJBHOME);
            }
            rtn = mContractHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mContractHome = (ContractHome) ctx.lookup(JNDINames.CONTRACT_EJBHOME);
                rtn = mContractHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ContractInformationHome mContractInformationHome;

    /**
     *  Obtains a reference to the remote interface for the ContractInformation.
     *
     *@return                                The ContractInformationAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ContractInformation getContractInformationAPI()
             throws APIServiceAccessException {
        ContractInformation rtn = null;
        try {
            if (null == mContractInformationHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mContractInformationHome= (ContractInformationHome) ctx.lookup(JNDINames.CONTRACT_INFORMATION_EJBHOME);
            }
            rtn = mContractInformationHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mContractInformationHome = (ContractInformationHome) ctx.lookup(JNDINames.CONTRACT_INFORMATION_EJBHOME);
                rtn = mContractInformationHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private SystemAgentHome mSystemAgentHome;

    /**
     *  Obtains a reference to the remote interface for the SystemAgent.
     *
     *@return                                The SystemAgentAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public SystemAgent getSystemAgentAPI()
             throws APIServiceAccessException {
        SystemAgent rtn = null;
        try {
            if (null == mSystemAgentHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mSystemAgentHome= (SystemAgentHome) ctx.lookup(JNDINames.SYSTEM_AGENT_EJBHOME);
            }
            rtn = mSystemAgentHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mSystemAgentHome = (SystemAgentHome) ctx.lookup(JNDINames.SYSTEM_AGENT_EJBHOME);
                rtn = mSystemAgentHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private CurrencyHome mCurrencyHome;

    /**
     *  Obtains a reference to the remote interface for the Currency.
     *
     *@return                                The CurrencyAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Currency getCurrencyAPI()
             throws APIServiceAccessException {
        Currency rtn = null;
        try {
            if (null == mCurrencyHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mCurrencyHome= (CurrencyHome) ctx.lookup(JNDINames.CURRENCY_EJBHOME);
            }
            rtn = mCurrencyHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mCurrencyHome = (CurrencyHome) ctx.lookup(JNDINames.CURRENCY_EJBHOME);
                rtn = mCurrencyHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private DistributorHome mDistributorHome;

    /**
     *  Obtains a reference to the remote interface for the Distributor.
     *
     *@return                                The DistributorAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Distributor getDistributorAPI()
             throws APIServiceAccessException {
        Distributor rtn = null;
        try {
            if (null == mDistributorHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mDistributorHome= (DistributorHome) ctx.lookup(JNDINames.DISTRIBUTOR_EJBHOME);
            }
            rtn = mDistributorHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mDistributorHome = (DistributorHome) ctx.lookup(JNDINames.DISTRIBUTOR_EJBHOME);
                rtn = mDistributorHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private EmailClientHome mEmailClientHome;

    /**
     *  Obtains a reference to the remote interface for the EmailClient.
     *
     *@return                                The EmailClientAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public EmailClient getEmailClientAPI()
             throws APIServiceAccessException {
        EmailClient rtn = null;
        try {
            if (null == mEmailClientHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mEmailClientHome= (EmailClientHome) ctx.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
            }
            rtn = mEmailClientHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mEmailClientHome = (EmailClientHome) ctx.lookup(JNDINames.EMAIL_CLIENT_EJBHOME);
                rtn = mEmailClientHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private FreightTableHome mFreightTableHome;

    /**
     *  Obtains a reference to the remote interface for the FreightTable.
     *
     *@return                                The FreightTableAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public FreightTable getFreightTableAPI()
             throws APIServiceAccessException {
        FreightTable rtn = null;
        try {
            if (null == mFreightTableHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mFreightTableHome= (FreightTableHome) ctx.lookup(JNDINames.FREIGHT_TABLE_EJBHOME);
            }
            rtn = mFreightTableHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mFreightTableHome = (FreightTableHome) ctx.lookup(JNDINames.FREIGHT_TABLE_EJBHOME);
                rtn = mFreightTableHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private TradingPartnerHome mTradingPartnerHome;

    /**
     *  Obtains a reference to the remote interface for the TradingPartner.
     *
     *@return                                The TradingPartnerAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public TradingPartner getTradingPartnerAPI()
             throws APIServiceAccessException {
        TradingPartner rtn = null;
        try {
            if (null == mTradingPartnerHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mTradingPartnerHome= (TradingPartnerHome) ctx.lookup(JNDINames.TRADING_PARTNER_EJBHOME);
            }
            rtn = mTradingPartnerHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mTradingPartnerHome = (TradingPartnerHome) ctx.lookup(JNDINames.TRADING_PARTNER_EJBHOME);
                rtn = mTradingPartnerHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    
    private HistoryHome mHistoryHome;

    /**
     *  Obtains a reference to the remote interface for the History.
     *
     *@return                                The HistoryAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public History getHistoryAPI()
             throws APIServiceAccessException {
        History rtn = null;
        try {
            if (null == mHistoryHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mHistoryHome= (HistoryHome) ctx.lookup(JNDINames.HISTORY_EJBHOME);
            }
            rtn = mHistoryHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mHistoryHome = (HistoryHome) ctx.lookup(JNDINames.HISTORY_EJBHOME);
                rtn = mHistoryHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private IntegrationServicesHome mIntegrationServicesHome;

    /**
     *  Obtains a reference to the remote interface for the IntegrationServices.
     *
     *@return                                The IntegrationServicesAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public IntegrationServices getIntegrationServicesAPI()
             throws APIServiceAccessException {
        IntegrationServices rtn = null;
        try {
            if (null == mIntegrationServicesHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mIntegrationServicesHome= (IntegrationServicesHome) ctx.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
            }
            rtn = mIntegrationServicesHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mIntegrationServicesHome = (IntegrationServicesHome) ctx.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
                rtn = mIntegrationServicesHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private TrainingHome mTrainingHome;

    /**
     *  Obtains a reference to the remote interface for the Training.
     *
     *@return                                The TrainingAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Training getTrainingAPI()
             throws APIServiceAccessException {
        Training rtn = null;
        try {
            if (null == mTrainingHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mTrainingHome= (TrainingHome) ctx.lookup(JNDINames.TRAINING_EJBHOME);
            }
            rtn = mTrainingHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mTrainingHome = (TrainingHome) ctx.lookup(JNDINames.TRAINING_EJBHOME);
                rtn = mTrainingHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ItemInformationHome mItemInformationHome;

    /**
     *  Obtains a reference to the remote interface for the ItemInformation.
     *
     *@return                                The ItemInformationAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ItemInformation getItemInformationAPI()
             throws APIServiceAccessException {
        ItemInformation rtn = null;
        try {
            if (null == mItemInformationHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mItemInformationHome= (ItemInformationHome) ctx.lookup(JNDINames.ITEM_INFORMATION_EJBHOME);
            }
            rtn = mItemInformationHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mItemInformationHome = (ItemInformationHome) ctx.lookup(JNDINames.ITEM_INFORMATION_EJBHOME);
                rtn = mItemInformationHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private KeywordHome mKeywordHome;

    /**
     *  Obtains a reference to the remote interface for the Keyword.
     *
     *@return                                The KeywordAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Keyword getKeywordAPI()
             throws APIServiceAccessException {
        Keyword rtn = null;
        try {
            if (null == mKeywordHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mKeywordHome= (KeywordHome) ctx.lookup(JNDINames.KEYWORD_EJBHOME);
            }
            rtn = mKeywordHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mKeywordHome = (KeywordHome) ctx.lookup(JNDINames.KEYWORD_EJBHOME);
                rtn = mKeywordHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    
    private LanguageHome mLanguageHome;

    /**
     *  Obtains a reference to the remote interface for the Language.
     *
     *@return                                The LanguageAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Language getLanguageAPI()
             throws APIServiceAccessException {
        Language rtn = null;
        try {
            if (null == mLanguageHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mLanguageHome= (LanguageHome) ctx.lookup(JNDINames.LANGUAGE_EJBHOME);
            }
            rtn = mLanguageHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mLanguageHome = (LanguageHome) ctx.lookup(JNDINames.LANGUAGE_EJBHOME);
                rtn = mLanguageHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private TroubleshooterHome mTroubleshooterHome;

    /**
     *  Obtains a reference to the remote interface for the Troubleshooter.
     *
     *@return                                The TroubleshooterAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Troubleshooter getTroubleshooterAPI()
             throws APIServiceAccessException {
        Troubleshooter rtn = null;
        try {
            if (null == mTroubleshooterHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mTroubleshooterHome= (TroubleshooterHome) ctx.lookup(JNDINames.TROUBLESHOOTER_EJBHOME);
            }
            rtn = mTroubleshooterHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mTroubleshooterHome = (TroubleshooterHome) ctx.lookup(JNDINames.TROUBLESHOOTER_EJBHOME);
                rtn = mTroubleshooterHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private GroupHome mGroupHome;

    /**
     *  Obtains a reference to the remote interface for the Group.
     *
     *@return                                The GroupAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Group getGroupAPI()
             throws APIServiceAccessException {
        Group rtn = null;
        try {
            if (null == mGroupHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mGroupHome= (GroupHome) ctx.lookup(JNDINames.GROUP_EJBHOME);
            }
            rtn = mGroupHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mGroupHome = (GroupHome) ctx.lookup(JNDINames.GROUP_EJBHOME);
                rtn = mGroupHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ListServiceHome mListServiceHome;

    /**
     *  Obtains a reference to the remote interface for the ListService.
     *
     *@return                                The ListServiceAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ListService getListServiceAPI()
             throws APIServiceAccessException {
        ListService rtn = null;
        try {
            if (null == mListServiceHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mListServiceHome= (ListServiceHome) ctx.lookup(JNDINames.LIST_SERVICE_EJBHOME);
            }
            rtn = mListServiceHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mListServiceHome = (ListServiceHome) ctx.lookup(JNDINames.LIST_SERVICE_EJBHOME);
                rtn = mListServiceHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ManufacturerHome mManufacturerHome;

    /**
     *  Obtains a reference to the remote interface for the Manufacturer.
     *
     *@return                                The ManufacturerAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Manufacturer getManufacturerAPI()
             throws APIServiceAccessException {
        Manufacturer rtn = null;
        try {
            if (null == mManufacturerHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mManufacturerHome= (ManufacturerHome) ctx.lookup(JNDINames.MANUFACTURER_EJBHOME);
            }
            rtn = mManufacturerHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mManufacturerHome = (ManufacturerHome) ctx.lookup(JNDINames.MANUFACTURER_EJBHOME);
                rtn = mManufacturerHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ContractorHome mContractorHome;
    /**
     *  Obtains a reference to the remote interface for the Contractor.
     *
     *@return                                The ContractorAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Contractor getContractorAPI()
             throws APIServiceAccessException {
        Contractor rtn = null;
        try {
            if (null == mContractorHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mContractorHome= (ContractorHome) ctx.lookup(JNDINames.CONTRACTOR_EJBHOME);
            }
            rtn = mContractorHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mContractorHome = (ContractorHome) ctx.lookup(JNDINames.CONTRACTOR_EJBHOME);
                rtn = mContractorHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    
    private ServiceHome mServiceHome;

    public Service getServiceAPI()
    throws APIServiceAccessException {
		Service rtn = null;
		try {
		   if (null == mServiceHome) {
		       if (null == ctx) {
		           ctx = getInitialContext();
		       }
		       mServiceHome= (ServiceHome) ctx.lookup(JNDINames.SERVICE_EJBHOME);
		   }
		   rtn = mServiceHome.create();
		}
		catch (Exception e) {
		   try{
		       ctx = getInitialContext();
		       mServiceHome = (ServiceHome) ctx.lookup(JNDINames.SERVICE_EJBHOME);
		       rtn = mServiceHome.create();
		   }catch (Exception e2) {
		       e2.printStackTrace();
		       throw new APIServiceAccessException();
		   }
		}
		return rtn;
    }

    private MsdsSpecsHome mMsdsSpecsHome;

    /**
     *  Obtains a reference to the remote interface for the MsdsSpecs.
     *
     *@return                                The MsdsSpecsAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public MsdsSpecs getMsdsSpecsAPI()
             throws APIServiceAccessException {
        MsdsSpecs rtn = null;
        try {
            if (null == mMsdsSpecsHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mMsdsSpecsHome= (MsdsSpecsHome) ctx.lookup(JNDINames.MSDS_SPECS_EJBHOME);
            }
            rtn = mMsdsSpecsHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mMsdsSpecsHome = (MsdsSpecsHome) ctx.lookup(JNDINames.MSDS_SPECS_EJBHOME);
                rtn = mMsdsSpecsHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private UserActivityHome mUserActivityHome;

    /**
     *  Obtains a reference to the remote interface for the UserActivity.
     *
     *@return                                The UserActivityAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public UserActivity getUserActivityAPI()
             throws APIServiceAccessException {
        UserActivity rtn = null;
        try {
            if (null == mUserActivityHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mUserActivityHome= (UserActivityHome) ctx.lookup(JNDINames.USER_ACTIVITY_EJBHOME);
            }
            rtn = mUserActivityHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mUserActivityHome = (UserActivityHome) ctx.lookup(JNDINames.USER_ACTIVITY_EJBHOME);
                rtn = mUserActivityHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private OrderAnalysisHome mOrderAnalysisHome;

    /**
     *  Obtains a reference to the remote interface for the OrderAnalysis.
     *
     *@return                                The OrderAnalysisAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public OrderAnalysis getOrderAnalysisAPI()
             throws APIServiceAccessException {
        OrderAnalysis rtn = null;
        try {
            if (null == mOrderAnalysisHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mOrderAnalysisHome= (OrderAnalysisHome) ctx.lookup(JNDINames.ORDER_ANALYSIS_EJBHOME);
            }
            rtn = mOrderAnalysisHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mOrderAnalysisHome = (OrderAnalysisHome) ctx.lookup(JNDINames.ORDER_ANALYSIS_EJBHOME);
                rtn = mOrderAnalysisHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private OrderGuideHome mOrderGuideHome;

    /**
     *  Obtains a reference to the remote interface for the OrderGuide.
     *
     *@return                                The OrderGuideAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public OrderGuide getOrderGuideAPI()
             throws APIServiceAccessException {
        OrderGuide rtn = null;
        try {
            if (null == mOrderGuideHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mOrderGuideHome= (OrderGuideHome) ctx.lookup(JNDINames.ORDER_GUIDE_EJBHOME);
            }
            rtn = mOrderGuideHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mOrderGuideHome = (OrderGuideHome) ctx.lookup(JNDINames.ORDER_GUIDE_EJBHOME);
                rtn = mOrderGuideHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private OrderHome mOrderHome;

    /**
     *  Obtains a reference to the remote interface for the Order.
     *
     *@return                                The OrderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Order getOrderAPI()
             throws APIServiceAccessException {
        Order rtn = null;
        try {
            if (null == mOrderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mOrderHome= (OrderHome) ctx.lookup(JNDINames.ORDER_EJBHOME);
            }
            rtn = mOrderHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mOrderHome = (OrderHome) ctx.lookup(JNDINames.ORDER_EJBHOME);
                rtn = mOrderHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private UserHome mUserHome;

    /**
     *  Obtains a reference to the remote interface for the User.
     *
     *@return                                The UserAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public User getUserAPI()
             throws APIServiceAccessException {
        User rtn = null;
        try {
            if (null == mUserHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mUserHome= (UserHome) ctx.lookup(JNDINames.USER_EJBHOME);
            }
            rtn = mUserHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mUserHome = (UserHome) ctx.lookup(JNDINames.USER_EJBHOME);
                rtn = mUserHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private WorkflowHome mWorkflowHome;

    /**
     *  Obtains a reference to the remote interface for the Workflow.
     *
     *@return                                The WorkflowAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Workflow getWorkflowAPI()
             throws APIServiceAccessException {
        Workflow rtn = null;
        try {
            if (null == mWorkflowHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mWorkflowHome= (WorkflowHome) ctx.lookup(JNDINames.WORKFLOW_EJBHOME);
            }
            rtn = mWorkflowHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mWorkflowHome = (WorkflowHome) ctx.lookup(JNDINames.WORKFLOW_EJBHOME);
                rtn = mWorkflowHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private OrderNotificationHome mOrderNotificationHome;

    /**
     *  Obtains a reference to the remote interface for the OrderNotification.
     *
     *@return                                The OrderNotificationAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public OrderNotification getOrderNotificationAPI()
             throws APIServiceAccessException {
        OrderNotification rtn = null;
        try {
            if (null == mOrderNotificationHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mOrderNotificationHome= (OrderNotificationHome) ctx.lookup(JNDINames.ORDER_NOTIFICATION_EJBHOME);
            }
            rtn = mOrderNotificationHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mOrderNotificationHome = (OrderNotificationHome) ctx.lookup(JNDINames.ORDER_NOTIFICATION_EJBHOME);
                rtn = mOrderNotificationHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private OrderProcessingServiceHome mOrderProcessingServiceHome;

    /**
     *  Obtains a reference to the remote interface for the OrderProcessingService.
     *
     *@return                                The OrderProcessingServiceAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public OrderProcessingService getOrderProcessingServiceAPI()
             throws APIServiceAccessException {
        OrderProcessingService rtn = null;
        try {
            if (null == mOrderProcessingServiceHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mOrderProcessingServiceHome= (OrderProcessingServiceHome) ctx.lookup(JNDINames.ORDER_PROCESSING_SERVICE_EJBHOME);
            }
            rtn = mOrderProcessingServiceHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mOrderProcessingServiceHome = (OrderProcessingServiceHome) ctx.lookup(JNDINames.ORDER_PROCESSING_SERVICE_EJBHOME);
                rtn = mOrderProcessingServiceHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private PipelineHome mPipelineHome;

    /**
     *  Obtains a reference to the remote interface for the Pipeline.
     *
     *@return                                The PipelineAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Pipeline getPipelineAPI()
             throws APIServiceAccessException {
        Pipeline rtn = null;
        try {
            if (null == mPipelineHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mPipelineHome= (PipelineHome) ctx.lookup(JNDINames.PIPELINE_EJBHOME);
            }
            rtn = mPipelineHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mPipelineHome = (PipelineHome) ctx.lookup(JNDINames.PIPELINE_EJBHOME);
                rtn = mPipelineHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ProfilingHome mProfilingHome;

    /**
     *  Obtains a reference to the remote interface for the Profiling.
     *
     *@return                                The ProfilingAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Profiling getProfilingAPI()
             throws APIServiceAccessException {
        Profiling rtn = null;
        try {
            if (null == mProfilingHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mProfilingHome= (ProfilingHome) ctx.lookup(JNDINames.PROFILING_EJBHOME);
            }
            rtn = mProfilingHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mProfilingHome = (ProfilingHome) ctx.lookup(JNDINames.PROFILING_EJBHOME);
                rtn = mProfilingHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ProductHome mProductHome;

    /**
     *  Obtains a reference to the remote interface for the Product.
     *
     *@return                                The ProductAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Product getProductAPI()
             throws APIServiceAccessException {
        Product rtn = null;
        try {
            if (null == mProductHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mProductHome= (ProductHome) ctx.lookup(JNDINames.PRODUCT_EJBHOME);
            }
            rtn = mProductHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mProductHome = (ProductHome) ctx.lookup(JNDINames.PRODUCT_EJBHOME);
                rtn = mProductHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ProductInformationHome mProductInformationHome;

    /**
     *  Obtains a reference to the remote interface for the ProductInformation.
     *
     *@return                                The ProductInformationAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ProductInformation getProductInformationAPI()
             throws APIServiceAccessException {
        ProductInformation rtn = null;
        try {
            if (null == mProductInformationHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mProductInformationHome= (ProductInformationHome) ctx.lookup(JNDINames.PRODUCT_INFORMATION_EJBHOME);
            }
            rtn = mProductInformationHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mProductInformationHome = (ProductInformationHome) ctx.lookup(JNDINames.PRODUCT_INFORMATION_EJBHOME);
                rtn = mProductInformationHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private PropertyServiceHome mPropertyServiceHome;

    /**
     *  Obtains a reference to the remote interface for the PropertyService.
     *
     *@return                                The PropertyServiceAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public PropertyService getPropertyServiceAPI()
             throws APIServiceAccessException {
        PropertyService rtn = null;
        try {
            if (null == mPropertyServiceHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mPropertyServiceHome= (PropertyServiceHome) ctx.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
            }
            rtn = mPropertyServiceHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mPropertyServiceHome = (PropertyServiceHome) ctx.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
                rtn = mPropertyServiceHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private PurchaseOrderHome mPurchaseOrderHome;

    /**
     *  Obtains a reference to the remote interface for the PurchaseOrder.
     *
     *@return                                The PurchaseOrderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public PurchaseOrder getPurchaseOrderAPI()
             throws APIServiceAccessException {
        PurchaseOrder rtn = null;
        try {
            if (null == mPurchaseOrderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mPurchaseOrderHome= (PurchaseOrderHome) ctx.lookup(JNDINames.PURCHASE_ORDER_EJBHOME);
            }
            rtn = mPurchaseOrderHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mPurchaseOrderHome = (PurchaseOrderHome) ctx.lookup(JNDINames.PURCHASE_ORDER_EJBHOME);
                rtn = mPurchaseOrderHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private RemittanceHome mRemittanceHome;

    /**
     *  Obtains a reference to the remote interface for the Remittance.
     *
     *@return                                The RemittanceAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Remittance getRemittanceAPI()
             throws APIServiceAccessException {
        Remittance rtn = null;
        try {
            if (null == mRemittanceHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mRemittanceHome= (RemittanceHome) ctx.lookup(JNDINames.REMITTANCE_EJBHOME);
            }
            rtn = mRemittanceHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mRemittanceHome = (RemittanceHome) ctx.lookup(JNDINames.REMITTANCE_EJBHOME);
                rtn = mRemittanceHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ThruStoreErpHome mThruStoreErpHome;

    /**
     *  Obtains a reference to the remote interface for the ThruStoreErp.
     *
     *@return                                The ThruStoreErpAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ThruStoreErp getThruStoreErpAPI()
             throws APIServiceAccessException {
        ThruStoreErp rtn = null;
        try {
            if (null == mThruStoreErpHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mThruStoreErpHome= (ThruStoreErpHome) ctx.lookup(JNDINames.THRU_STORE_ERP_EJBHOME);
            }
            rtn = mThruStoreErpHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mThruStoreErpHome = (ThruStoreErpHome) ctx.lookup(JNDINames.THRU_STORE_ERP_EJBHOME);
                rtn = mThruStoreErpHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ReportOrderHome mReportOrderHome;

    /**
     *  Obtains a reference to the remote interface for the ReportOrder.
     *
     *@return                                The ReportOrderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ReportOrder getReportOrderAPI()
             throws APIServiceAccessException {
        ReportOrder rtn = null;
        try {
            if (null == mReportOrderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mReportOrderHome= (ReportOrderHome) ctx.lookup(JNDINames.REPORT_ORDER_EJBHOME);
            }
            rtn = mReportOrderHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mReportOrderHome = (ReportOrderHome) ctx.lookup(JNDINames.REPORT_ORDER_EJBHOME);
                rtn = mReportOrderHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private RequestHome mRequestHome;

    /**
     *  Obtains a reference to the remote interface for the Request.
     *
     *@return                                The RequestAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Request getRequestAPI()
             throws APIServiceAccessException {
        Request rtn = null;
        try {
            if (null == mRequestHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mRequestHome= (RequestHome) ctx.lookup(JNDINames.REQUEST_EJBHOME);
            }
            rtn = mRequestHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mRequestHome = (RequestHome) ctx.lookup(JNDINames.REQUEST_EJBHOME);
                rtn = mRequestHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private SKUHome mSKUHome;

    /**
     *  Obtains a reference to the remote interface for the SKU.
     *
     *@return                                The SKUAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public SKU getSKUAPI()
             throws APIServiceAccessException {
        SKU rtn = null;
        try {
            if (null == mSKUHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mSKUHome= (SKUHome) ctx.lookup(JNDINames.S_K_U_EJBHOME);
            }
            rtn = mSKUHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mSKUHome = (SKUHome) ctx.lookup(JNDINames.S_K_U_EJBHOME);
                rtn = mSKUHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private SKUInformationHome mSKUInformationHome;

    /**
     *  Obtains a reference to the remote interface for the SKUInformation.
     *
     *@return                                The SKUInformationAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public SKUInformation getSKUInformationAPI()
             throws APIServiceAccessException {
        SKUInformation rtn = null;
        try {
            if (null == mSKUInformationHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mSKUInformationHome= (SKUInformationHome) ctx.lookup(JNDINames.S_K_U_INFORMATION_EJBHOME);
            }
            rtn = mSKUInformationHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mSKUInformationHome = (SKUInformationHome) ctx.lookup(JNDINames.S_K_U_INFORMATION_EJBHOME);
                rtn = mSKUInformationHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private CustomerRequestPoNumberHome mCustomerRequestPoNumberHome;

    /**
     *  Obtains a reference to the remote interface for the CustomerRequestPoNumber.
     *
     *@return                                The CustomerRequestPoNumberAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public CustomerRequestPoNumber getCustomerRequestPoNumberAPI()
             throws APIServiceAccessException {
        CustomerRequestPoNumber rtn = null;
        try {
            if (null == mCustomerRequestPoNumberHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mCustomerRequestPoNumberHome= (CustomerRequestPoNumberHome) ctx.lookup(JNDINames.CUSTOMER_REQUEST_PO_NUMBER_EJBHOME);
            }
            rtn = mCustomerRequestPoNumberHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mCustomerRequestPoNumberHome = (CustomerRequestPoNumberHome) ctx.lookup(JNDINames.CUSTOMER_REQUEST_PO_NUMBER_EJBHOME);
                rtn = mCustomerRequestPoNumberHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private BudgetHome mBudgetHome;

    /**
     *  Obtains a reference to the remote interface for the Budget.
     *
     *@return                                The BudgetAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Budget getBudgetAPI()
             throws APIServiceAccessException {
        Budget rtn = null;
        try {
            if (null == mBudgetHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mBudgetHome= (BudgetHome) ctx.lookup(JNDINames.BUDGET_EJBHOME);
            }
            rtn = mBudgetHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mBudgetHome = (BudgetHome) ctx.lookup(JNDINames.BUDGET_EJBHOME);
                rtn = mBudgetHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

   // private AddressValidatorHome mAddressValidatorHome;

    /**
     *  Obtains a reference to the remote interface for the AddressValidator.
     *
     *@return                                The AddressValidatorAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
  /*  public AddressValidator getAddressValidatorAPI()
             throws APIServiceAccessException {
        AddressValidator rtn = null;
        try {
            if (null == mAddressValidatorHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mAddressValidatorHome= (AddressValidatorHome) ctx.lookup(JNDINames.ADDRESS_VALIDATOR_EJBHOME);
            }
            rtn = mAddressValidatorHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mAddressValidatorHome = (AddressValidatorHome) ctx.lookup(JNDINames.ADDRESS_VALIDATOR_EJBHOME);
                rtn = mAddressValidatorHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }*/

    private ReportHome mReportHome;

    /**
     *  Obtains a reference to the remote interface for the Report.
     *
     *@return                                The ReportAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Report getReportAPI()
             throws APIServiceAccessException {
        Report rtn = null;
        try {
            if (null == mReportHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mReportHome= (ReportHome) ctx.lookup(JNDINames.REPORT_EJBHOME);
            }
            rtn = mReportHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mReportHome = (ReportHome) ctx.lookup(JNDINames.REPORT_EJBHOME);
                rtn = mReportHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private NoteHome mNoteHome;

    /**
     *  Obtains a reference to the remote interface for the Note.
     *
     *@return                                The NoteAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Note getNoteAPI()
             throws APIServiceAccessException {
        Note rtn = null;
        try {
            if (null == mNoteHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mNoteHome= (NoteHome) ctx.lookup(JNDINames.NOTE_EJBHOME);
            }
            rtn = mNoteHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mNoteHome = (NoteHome) ctx.lookup(JNDINames.NOTE_EJBHOME);
                rtn = mNoteHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private EstimationHome mEstimationHome;

    /**
     *  Obtains a reference to the remote interface for the Estimation.
     *
     *@return                                The EstimationAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Estimation getEstimationAPI()
             throws APIServiceAccessException {
        Estimation rtn = null;
        try {
            if (null == mEstimationHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mEstimationHome= (EstimationHome) ctx.lookup(JNDINames.ESTIMATION_EJBHOME);
            }
            rtn = mEstimationHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mEstimationHome = (EstimationHome) ctx.lookup(JNDINames.ESTIMATION_EJBHOME);
                rtn = mEstimationHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }


    private CountryHome mCountryHome;
    /**
     *  Obtains a reference to the remote interface for the Country.
     *
     *@return                                The CountryAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Country getCountryAPI()
             throws APIServiceAccessException {
        Country rtn = null;
        try {
            if (null == mCountryHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mCountryHome= (CountryHome) ctx.lookup(JNDINames.COUNTRY_EJBHOME);
            }
            rtn = mCountryHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mCountryHome = (CountryHome) ctx.lookup(JNDINames.COUNTRY_EJBHOME);
                rtn = mCountryHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private StoreOrderHome mStoreOrderHome;

    /**
     *  Obtains a reference to the remote interface for the Order.
     *
     *@return                                The OrderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public StoreOrder getStoreOrderAPI()
             throws APIServiceAccessException {
        StoreOrder rtn = null;
        try {
            if (null == mStoreOrderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mStoreOrderHome= (StoreOrderHome) ctx.lookup(JNDINames.STORE_ORDER_EJBHOME);
            }
            rtn = mStoreOrderHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mStoreOrderHome = (StoreOrderHome) ctx.lookup(JNDINames.STORE_ORDER_EJBHOME);
                rtn = mStoreOrderHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private AssetHome mAssetHome;

    /**
     * Obtains a reference to the remote interface for the Asset.
     *
     * @return The AssetAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public Asset getAssetAPI() throws APIServiceAccessException {
        Asset object = null;
        try {
            if (null == mAssetHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mAssetHome = (AssetHome) ctx.lookup(JNDINames.ASSET_EJBHOME);
            }
            object = mAssetHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mAssetHome = (AssetHome) ctx.lookup(JNDINames.ASSET_EJBHOME);
                object = mAssetHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }



    private WarrantyHome mWarrantyHome;

    /**
     * Obtains a reference to the remote interface for the Warranty.
     *
     * @return The WarrantyAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public Warranty getWarrantyAPI() throws APIServiceAccessException {
        Warranty object = null;
        try {
            if (null == mWarrantyHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mWarrantyHome = (WarrantyHome) ctx.lookup(JNDINames.WARRANTY_EJBHOME);
            }
            object = mWarrantyHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mWarrantyHome = (WarrantyHome) ctx.lookup(JNDINames.WARRANTY_EJBHOME);
                object = mWarrantyHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }

    private WorkOrderHome mWorkOrderHome;

    /**
     * Obtains a reference to the remote interface for the WorkOrder.
     *
     * @return The WorkOrderAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public WorkOrder getWorkOrderAPI() throws APIServiceAccessException {
        WorkOrder object = null;
        try {
            if (null == mWorkOrderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mWorkOrderHome = (WorkOrderHome) ctx.lookup(JNDINames.WORK_ORDER_EJBHOME);
            }
            object = mWorkOrderHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mWorkOrderHome = (WorkOrderHome) ctx.lookup(JNDINames.WORK_ORDER_EJBHOME);
                object = mWorkOrderHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }

    private DispatchHome mDispatchHome;

    /**
     * Obtains a reference to the remote interface for the Dispatch.
     *
     * @return The DispatchAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public Dispatch getDispatchAPI() throws APIServiceAccessException {
        Dispatch object = null;
        try {
            if (null == mDispatchHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mDispatchHome = (DispatchHome) ctx.lookup(JNDINames.DISPATCH_EJBHOME);
            }
            object = mDispatchHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mDispatchHome = (DispatchHome) ctx.lookup(JNDINames.DISPATCH_EJBHOME);
                object = mDispatchHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }

    private ProcessHome mProcessHome;

    /**
     * Obtains a reference to the remote interface for the Process.
     *
     * @return The ProcessAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public Process getProcessAPI() throws APIServiceAccessException {
        Process object = null;
        try {
            if (null == mProcessHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mProcessHome = (ProcessHome) ctx.lookup(JNDINames.PROCESS_EJBHOME);
            }
            object = mProcessHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mProcessHome = (ProcessHome) ctx.lookup(JNDINames.PROCESS_EJBHOME);
                object = mProcessHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }

    private TaskHome mTaskHome;

    /**
     * Obtains a reference to the remote interface for the Task.
     *
     * @return The TaskAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public Task getTaskAPI() throws APIServiceAccessException {
        Task object = null;
        try {
            if (null == mTaskHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mTaskHome = (TaskHome) ctx.lookup(JNDINames.TASK_EJBHOME);
            }
            object = mTaskHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mTaskHome = (TaskHome) ctx.lookup(JNDINames.TASK_EJBHOME);
                object = mTaskHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }

    
    private TemplateHome mTemplateHome;

    /**
     *  Obtains a reference to the remote interface for the Template.
     *
     *@return                                The TemplateAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Template getTemplateAPI()
             throws APIServiceAccessException {
        Template rtn = null;
        try {
            if (null == mTemplateHome) {
                if (null == ctx) {
                    ctx = new InitialContext();
                }
                mTemplateHome= (TemplateHome) ctx.lookup(JNDINames.TEMPLATE_EJBHOME);
            }
            rtn = mTemplateHome.create();
        }
        catch (Exception e) {
            try{
                ctx = new InitialContext();
                mTemplateHome = (TemplateHome) ctx.lookup(JNDINames.TEMPLATE_EJBHOME);
                rtn = mTemplateHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ProcessEngineHome mProcessEngineHome;

    /**
     * Obtains a reference to the remote interface for the ProcessEngine.
     *
     * @return The ProcessEngineAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public ProcessEngine getProcessEngineAPI() throws APIServiceAccessException {
        ProcessEngine object = null;
        try {
            if (null == mProcessEngineHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mProcessEngineHome = (ProcessEngineHome) ctx.lookup(JNDINames.PROCESS_ENGINE_EJBHOME);
            }
            object = mProcessEngineHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mProcessEngineHome = (ProcessEngineHome) ctx.lookup(JNDINames.PROCESS_ENGINE_EJBHOME);
                object = mProcessEngineHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }

    private InterchangeHome mInterchangeHome;

    /**
     * Obtains a reference to the remote interface for the Interchange.
     *
     * @return The InterchangeAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public Interchange getInterchangeAPI() throws APIServiceAccessException {
        Interchange object = null;
        try {
            if (null == mInterchangeHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mInterchangeHome = (InterchangeHome) ctx.lookup(JNDINames.INTERCHANGE_EJBHOME);
            }
            object = mInterchangeHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mInterchangeHome = (InterchangeHome) ctx.lookup(JNDINames.INTERCHANGE_EJBHOME);
                object = mInterchangeHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return object;
    }

    private UiFrameHome mUiFrameHome;

    /**
     *  Obtains a reference to the remote interface for the Site.
     *
     *@return                                The SiteAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public UiFrame getUiFrameAPI()
             throws APIServiceAccessException {
        UiFrame rtn = null;
        try {
            if (null == mUiFrameHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mUiFrameHome= (UiFrameHome) ctx.lookup(JNDINames.UI_FRAME_EJBHOME);
            }
            rtn = mUiFrameHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mUiFrameHome = (UiFrameHome) ctx.lookup(JNDINames.UI_FRAME_EJBHOME);
                rtn = mUiFrameHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private ServiceProviderTypeHome mServiceProviderTypeHome;

    /**
     *  Obtains a reference to the remote interface for the ServiceProviderType.
     *
     *@return                                The ServiceProviderTypeAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public ServiceProviderType getServiceProviderTypeAPI()
             throws APIServiceAccessException {
        ServiceProviderType rtn = null;
        try {
            if (null == mServiceProviderTypeHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mServiceProviderTypeHome = (ServiceProviderTypeHome) ctx.lookup(JNDINames.SERVICE_PROVIDER_TYPE_EJBHOME);
            }
            rtn = mServiceProviderTypeHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mServiceProviderTypeHome = (ServiceProviderTypeHome) ctx.lookup(JNDINames.SERVICE_PROVIDER_TYPE_EJBHOME);
                rtn = mServiceProviderTypeHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private JanPakSiteLoaderHome mJanPakSiteLoaderHome;

    /**
     *  Obtains a reference to the remote interface for the JanPakSiteLoader.
     *
     *@return                                The JanPakSiteLoaderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public JanPakSiteLoader getJanPakSiteLoaderAPI() throws APIServiceAccessException {
        JanPakSiteLoader rtn = null;
        try {
            if (null == mJanPakSiteLoaderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mJanPakSiteLoaderHome = (JanPakSiteLoaderHome) ctx.lookup(JNDINames.JAN_PACK_SITE_LOADER_EJBHOME);
            }
            rtn = mJanPakSiteLoaderHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mJanPakSiteLoaderHome = (JanPakSiteLoaderHome) ctx.lookup(JNDINames.JAN_PACK_SITE_LOADER_EJBHOME);
                rtn = mJanPakSiteLoaderHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

     private JanPakItemLoaderHome mJanPakItemLoaderHome;

    /**
     *  Obtains a reference to the remote interface for the JanPakItemLoader.
     *
     *@return                                The JanPakItemLoaderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public JanPakItemLoader getJanPakItemLoaderAPI() throws APIServiceAccessException {
        JanPakItemLoader rtn = null;
        try {
            if (null == mJanPakItemLoaderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mJanPakItemLoaderHome = (JanPakItemLoaderHome) ctx.lookup(JNDINames.JAN_PACK_ITEM_LOADER_EJBHOME);
            }
            rtn = mJanPakItemLoaderHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mJanPakItemLoaderHome = (JanPakItemLoaderHome) ctx.lookup(JNDINames.JAN_PACK_ITEM_LOADER_EJBHOME);
                rtn = mJanPakItemLoaderHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private JanPakInvoiceLoaderHome mJanPakInvoiceLoaderHome;

    /**
     *  Obtains a reference to the remote interface for the JanPakInvoiceLoader.
     *
     *@return                                The JanPakInvoiceLoaderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public JanPakInvoiceLoader getJanPakInvoiceLoaderAPI() throws APIServiceAccessException {
        JanPakInvoiceLoader rtn = null;
        try {
            if (null == mJanPakInvoiceLoaderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mJanPakInvoiceLoaderHome = (JanPakInvoiceLoaderHome) ctx.lookup(JNDINames.JAN_PACK_INVOICE_LOADER_EJBHOME);
            }
            rtn = mJanPakInvoiceLoaderHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mJanPakInvoiceLoaderHome = (JanPakInvoiceLoaderHome) ctx.lookup(JNDINames.JAN_PACK_INVOICE_LOADER_EJBHOME);
                rtn = mJanPakInvoiceLoaderHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    private DWOperationHome mDWOperationHome;

     /**
      *  Obtains a reference to the remote interface for the DWDim.
      *
      *@return     The DWDimAPI value
      *@exception  APIServiceAccessException  Description of Exception
      */
     public DWOperation getDWOperationAPI() throws APIServiceAccessException {
         DWOperation rtn = null;
         try {
             if (null == mDWOperationHome) {
                 if (null == ctx) {
                     ctx = getInitialContext();
                 }
                 mDWOperationHome = (DWOperationHome) ctx.lookup(JNDINames.DW_OPERATION_EJBHOME);
             }
             rtn = mDWOperationHome.create();
         }
         catch (Exception e) {
             try {
                 ctx = getInitialContext();
                 mDWOperationHome = (DWOperationHome) ctx.lookup(JNDINames.DW_OPERATION_EJBHOME);
                 rtn = mDWOperationHome.create();
             } catch (Exception e2) {
                 e2.printStackTrace();
                 throw new APIServiceAccessException();
             }
         }
         return rtn;
     }


    private DataWarehouseHome mDataWarehouseHome;

    /**
     * Obtains a reference to the remote interface for the DataWarehouse.
     * 
     * @return The DataWarehouseAPI value
     * @exception APIServiceAccessException
     *                Description of Exception
     */
    public DataWarehouse getDataWarehouseAPI() throws APIServiceAccessException {
        DataWarehouse rtn = null;
        try {
            if (null == mDataWarehouseHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mDataWarehouseHome = (DataWarehouseHome) ctx
                        .lookup(JNDINames.DATA_WAREHOUSE_EJBHOME);
            }
            rtn = mDataWarehouseHome.create();
        } catch (Exception e) {
            try {
                ctx = getInitialContext();
                mDataWarehouseHome = (DataWarehouseHome) ctx
                        .lookup(JNDINames.DATA_WAREHOUSE_EJBHOME);
                rtn = mDataWarehouseHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private SelfServiceErpHome mSelfServiceErpHome;

    /**
     * Obtains a reference to the remote interface for the SelfService.
     *
     * @return The SelfServiceAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public SelfServiceErp getSelfServiceErpAPI() throws APIServiceAccessException {
        SelfServiceErp rtn = null;
        try {
            if (null == mSelfServiceErpHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mSelfServiceErpHome = (SelfServiceErpHome) ctx.lookup(JNDINames.SELF_SERVICE_ERP_EJBHOME);
            }
            rtn = mSelfServiceErpHome.create();
        } catch (Exception e) {
            try {
                ctx = getInitialContext();
                mSelfServiceErpHome = (SelfServiceErpHome) ctx.lookup(JNDINames.SELF_SERVICE_ERP_EJBHOME);
                rtn = mSelfServiceErpHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private InboundFilesHome mInboundFilesHome;

    /**
     * Obtains a reference to the remote interface for the InboundFiles.
     *
     *@return                                The InboundFilesAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public InboundFiles getInboundFilesAPI() throws APIServiceAccessException {
        InboundFiles rtn = null;
        try {
            if (null == mInboundFilesHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mInboundFilesHome = (InboundFilesHome) ctx.lookup(JNDINames.INBOUND_FILES_EJBHOME);
            }
            rtn = mInboundFilesHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mInboundFilesHome = (InboundFilesHome) ctx.lookup(JNDINames.INBOUND_FILES_EJBHOME);
                rtn = mInboundFilesHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private UiHome mUiHome;

    /**
     * Obtains a reference to the remote interface for the Ui.
     *
     * @return The UiAPI value
     * @throws APIServiceAccessException Description of Exception
     */
    public Ui getUiAPI() throws APIServiceAccessException {
        Ui rtn = null;
        try {
            if (null == mUiHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mUiHome = (UiHome) ctx.lookup(JNDINames.UI_EJBHOME);
            }
            rtn = mUiHome.create();
        }
        catch (Exception e) {
            try {
                ctx = getInitialContext();
                mUiHome = (UiHome) ctx.lookup(JNDINames.UI_EJBHOME);
                rtn = mUiHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

	private DataUploaderHelperHome mDataUploaderHelperHome;

    /**
	* Obtains a reference to the remote interface for the DataUploaderHelper.
	* 
	* @return The DataUploaderHelperAPI value
	* @exception APIServiceAccessException
	*                Description of Exception
	*/
    public DataUploaderHelper getDataUploaderHelperAPI() throws APIServiceAccessException {
        DataUploaderHelper rtn = null;
        try {
            if (null == mDataUploaderHelperHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mDataUploaderHelperHome = (DataUploaderHelperHome) ctx
                        .lookup(JNDINames.DATA_UPLOADER_HELPER_EJBHOME);
            }
            rtn = mDataUploaderHelperHome.create();
        } catch (Exception e) {
            try {
                ctx = getInitialContext();
                mDataUploaderHelperHome = (DataUploaderHelperHome) ctx
                        .lookup(JNDINames.DATA_UPLOADER_HELPER_EJBHOME);
                rtn = mDataUploaderHelperHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    
    private SOAPHome soapHome;

    public SOAP getSOAPAPI() throws APIServiceAccessException {
        SOAP rtn = null;
        try {
            if (null == soapHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                soapHome= (SOAPHome) ctx.lookup(JNDINames.SOAP_EJBHOME);
            }
            rtn = soapHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                soapHome = (SOAPHome) ctx.lookup(JNDINames.SOAP_EJBHOME);
                rtn = soapHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }


    private StoreMessageHome storeMessageHome;
    /**
     * Obtains a reference to the remote interface for the Store Messages.
     * 
     *@return The StoreMessageAPI value
     *@exception APIServiceAccessException
     *                Description of Exception
     */
    public StoreMessage getStoreMessageAPI() throws APIServiceAccessException {
        StoreMessage rtn = null;
        try {
            if (null == storeMessageHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                storeMessageHome = (StoreMessageHome) ctx
                        .lookup(JNDINames.STORE_MESSAGE_EJBHOME);
            }
            rtn = storeMessageHome.create();
        } catch (Exception e) {
            try {
                ctx = getInitialContext();
                storeMessageHome = (StoreMessageHome) ctx
                        .lookup(JNDINames.STORE_MESSAGE_EJBHOME);
                rtn = storeMessageHome.create();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    
    private ScheduleHome mScheduleHome;

    /**
     *  Obtains a reference to the remote interface for the Schedule.
     *
     *@return                                The ScheduleAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public Schedule getScheduleAPI()
             throws APIServiceAccessException {
        Schedule rtn = null;
        try {
            if (null == mScheduleHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mScheduleHome= (ScheduleHome) ctx.lookup(JNDINames.SCHEDULE_EJBHOME);
            }
            rtn = mScheduleHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mScheduleHome = (ScheduleHome) ctx.lookup(JNDINames.SCHEDULE_EJBHOME);
                rtn = mScheduleHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }
    
    private MainDbHome mMainDbHome;

    /**
     *  Obtains a reference to the remote interface for the Main Db.
     *
     *@return                                The MainDbAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public MainDb getMainDbAPI()
             throws APIServiceAccessException {
    	MainDb rtn = null;
        try {
            if (null == mMainDbHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mMainDbHome= (MainDbHome) ctx.lookup(JNDINames.MAIN_DB_EJBHOME);
            }
            rtn = mMainDbHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mMainDbHome = (MainDbHome) ctx.lookup(JNDINames.MAIN_DB_EJBHOME);
                rtn = mMainDbHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }

    private CorporateOrderHome mCorporateOrderHome;

    /**
     *  Obtains a reference to the remote interface for the CorporateOrder.
     *
     *@return  The CorporateOrderAPI value
     *@exception  APIServiceAccessException  Description of Exception
     */
    public CorporateOrder getCorporateOrderAPI()
             throws APIServiceAccessException {
        CorporateOrder rtn = null;
        try {
            if (null == mCorporateOrderHome) {
                if (null == ctx) {
                    ctx = getInitialContext();
                }
                mCorporateOrderHome= (CorporateOrderHome) ctx.lookup(JNDINames.CORPORATE_ORDER_EJBHOME);
            }
            rtn = mCorporateOrderHome.create();
        }
        catch (Exception e) {
            try{
                ctx = getInitialContext();
                mCorporateOrderHome = (CorporateOrderHome) ctx.lookup(JNDINames.CORPORATE_ORDER_EJBHOME);
                rtn = mCorporateOrderHome.create();
            }catch (Exception e2) {
                e2.printStackTrace();
                throw new APIServiceAccessException();
            }
        }
        return rtn;
    }


    private InitialContext getInitialContext() throws NamingException {
      if(ctx!=null) return ctx;
      ctx = Utility.getEjbNamingContext();
      return ctx;
        //environment.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        //environment.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        //environment.put(Context.PROVIDER_URL, "jnp://192.168.0.2:1099");
        //66.238.208.105:1099
  }


}
