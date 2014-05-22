package com.cleanwise.service.api.meta;

import com.cleanwise.service.api.cachecos.ObjectMeta;
import com.cleanwise.service.api.cachecos.TableField;
import com.cleanwise.service.api.cachecos.VarMeta;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryInfoView;


public class SiteDataMeta extends ObjectMeta {

    public SiteDataMeta(SiteData pData) {

        super();

        {
            VarMeta siteVarMeta = new VarMeta();
            siteVarMeta.add(new BusEntityDataMeta(pData.getBusEntity()));
            add(siteVarMeta);
        }

        {
            VarMeta accountVarMeta = new VarMeta();
            accountVarMeta.add(new BusEntityDataMeta(pData.getAccountBusEntity()));
            add(accountVarMeta);
        }

        {
            VarMeta siteAccosVarMeta = new VarMeta();
            siteAccosVarMeta.add(new BusEntityAssocDataMeta(
                    new TableField(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pData.getBusEntity().getBusEntityId()),
                    new TableField(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pData.getAccountBusEntity().getBusEntityId())));
            add(siteAccosVarMeta);
        }

        {
            VarMeta addressVarMeta = new VarMeta();
            addressVarMeta.add(new AddressDataMeta(new TableField(AddressDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(addressVarMeta);
        }

        {
            VarMeta phoneVarMeta = new VarMeta();
            phoneVarMeta.add(new PhoneDataMeta(new TableField(PhoneDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(phoneVarMeta);
        }

        {
            VarMeta budgetVarMeta = new VarMeta();
            budgetVarMeta.add(new BudgetDataMeta(new TableField(BudgetDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            budgetVarMeta.add(new BudgetDataMeta(new TableField(BudgetDataAccess.BUS_ENTITY_ID, pData.getAccountBusEntity().getBusEntityId())));
            add(budgetVarMeta);
        }

        {
            VarMeta costCenterVarMeta = new VarMeta();
            if (pData.getSpendingInfo() != null) {
                for (Object oSpendInfo : pData.getSpendingInfo()) {
                    BudgetSpendView spendInfo = (BudgetSpendView) oSpendInfo;
                    costCenterVarMeta.add(new CostCenterDataMeta(new TableField(CostCenterDataAccess.COST_CENTER_ID, spendInfo.getCostCenterId())));
                }
                add(costCenterVarMeta);

            }
        }


        {
            VarMeta propertyVarMeta = new VarMeta();
            propertyVarMeta.add(new PropertyDataMeta(new TableField(PropertyDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            propertyVarMeta.add(new PropertyDataMeta(new TableField(PropertyDataAccess.BUS_ENTITY_ID, pData.getAccountBusEntity().getBusEntityId())));
            add(propertyVarMeta);

        }

        {
            VarMeta seteLedgerVarMeta = new VarMeta();
            seteLedgerVarMeta.add(new SiteLedgerDataMeta(new TableField(SiteLedgerDataAccess.SITE_ID, pData.getBusEntity().getBusEntityId())));
            add(seteLedgerVarMeta);
        }

        {
            VarMeta orderVarMeta = new VarMeta();
            orderVarMeta.add(new OrderDataMeta(new TableField(OrderDataAccess.SITE_ID, pData.getBusEntity().getBusEntityId())));
            orderVarMeta.add(new OrderDataMeta(new TableField(OrderDataAccess.ACCOUNT_ID, pData.getAccountBusEntity().getBusEntityId())));
            add(orderVarMeta);
        }

        {
            VarMeta contractVarMeta = new VarMeta();
            contractVarMeta.add(new ContractDataMeta(new TableField(ContractDataAccess.CONTRACT_ID, pData.getContractData().getContractId())));
            add(contractVarMeta);
        }

        {
            VarMeta catalogVarMeta = new VarMeta();
            catalogVarMeta.add(new CatalogDataMeta(new TableField(CatalogDataAccess.CATALOG_ID, pData.getContractData().getCatalogId())));
            add(catalogVarMeta);
        }

        {
            VarMeta fiscalCallendarVarMeta = new VarMeta();
            fiscalCallendarVarMeta.add(new FiscalCalenderDataMeta(new TableField(FiscalCalenderDataAccess.BUS_ENTITY_ID, pData.getAccountBusEntity().getBusEntityId())));
            add(fiscalCallendarVarMeta);
        }

        {
            VarMeta workflowVarMeta = new VarMeta();
            workflowVarMeta.add(new WorkflowDataMeta(new TableField(WorkflowDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(workflowVarMeta);
        }

        {
            VarMeta shoppingControlVarMeta = new VarMeta();
            shoppingControlVarMeta.add(new ShoppingControlDataMeta(new TableField(ShoppingControlDataAccess.SITE_ID, pData.getBusEntity().getBusEntityId())));
            shoppingControlVarMeta.add(new ShoppingControlDataMeta(new TableField(ShoppingControlDataAccess.ACCOUNT_ID, pData.getAccountBusEntity().getBusEntityId())));
            add(shoppingControlVarMeta);
        }

        {
            VarMeta blanketPoVarMeta = new VarMeta();
            blanketPoVarMeta.add(new BlanketPoNumAssocDataMeta(new TableField(BlanketPoNumAssocDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(blanketPoVarMeta);
        }

        {
            VarMeta inventoryItemsVarMeta = new VarMeta();
            inventoryItemsVarMeta.add(new InventoryLevelDataMeta(new TableField(InventoryLevelDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            inventoryItemsVarMeta.add(new InventoryItemsDataMeta(new TableField(InventoryItemsDataAccess.BUS_ENTITY_ID, pData.getAccountBusEntity().getBusEntityId())));
            if (pData.getSiteInventory() != null) {
                for (Object oSiteInventory : pData.getSiteInventory()) {
                    SiteInventoryInfoView siteInv = (SiteInventoryInfoView) oSiteInventory;
                    inventoryItemsVarMeta.add(new ProductDataMeta(siteInv.getProductData()));
                }
            }
            add(inventoryItemsVarMeta);
        }

        {
            VarMeta bscVarMeta = new VarMeta();
            if (pData.getBSC() != null) {
                bscVarMeta.add(new BusEntityDataMeta(pData.getBSC().getBusEntityData()));
                add(bscVarMeta);
            }
        }

        //schedule
        //store properties
        //workflow rule
    }
}
