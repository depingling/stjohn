package com.cleanwise.service.api.meta;

import com.cleanwise.service.api.cachecos.ObjectMeta;
import com.cleanwise.service.api.cachecos.VarMeta;
import com.cleanwise.service.api.cachecos.TableField;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.RefCodeNames;

public class AccountDataMeta extends ObjectMeta {

    public AccountDataMeta(AccountData pData) {

        super();

        {
            VarMeta accountVarMeta = new VarMeta();
            accountVarMeta.add(new BusEntityDataMeta(pData.getBusEntity()));
            add(accountVarMeta);
        }

        {
            VarMeta propertyVarMeta = new VarMeta();
            propertyVarMeta.add(new PropertyDataMeta(new TableField(PropertyDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(propertyVarMeta);
        }

        {
            VarMeta emailVarMeta = new VarMeta();
            emailVarMeta.add(new EmailDataMeta(new TableField(EmailDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(emailVarMeta);
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
            VarMeta assocVarMeta = new VarMeta();
            assocVarMeta.add(new BusEntityAssocDataMeta(new TableField(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pData.getBusEntity().getBusEntityId()),new TableField(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE)));
            add(assocVarMeta);
        }


        {
            VarMeta beParamVarMeta = new VarMeta();
            beParamVarMeta.add(new BusEntityParameterDataMeta(new TableField(BusEntityParameterDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(beParamVarMeta);
        }

        {
            VarMeta productViewDefVarMeta = new VarMeta();
            productViewDefVarMeta.add(new ProductViewDefDataMeta(new TableField(ProductViewDefDataAccess.ACCOUNT_ID, pData.getBusEntity().getBusEntityId())));
            add(productViewDefVarMeta);
        }

        {
            VarMeta inventoryItemsVarMeta = new VarMeta();
            inventoryItemsVarMeta.add(new InventoryItemsDataMeta(new TableField(InventoryItemsDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            if (pData.getInventoryItemsData() != null) {
                for (Object oSiteInventory : pData.getInventoryItemsData()) {
                    InventoryItemDataJoin siteInv = (InventoryItemDataJoin) oSiteInventory;
                    inventoryItemsVarMeta.add(new ProductDataMeta(siteInv.getProductData()));
                }
            }
            add(inventoryItemsVarMeta);
        }

        {
            VarMeta shoppingOptionsVarMeta = new VarMeta();
            shoppingOptionsVarMeta.add(new ShoppingOptionsDataMeta(new TableField(ShoppingOptionsDataAccess.ACCOUNT_ID, pData.getBusEntity().getBusEntityId())));
            shoppingOptionsVarMeta.add(new ShoppingOptConfigDataMeta(new TableField(ShoppingOptConfigDataAccess.ACCOUNT_ID, pData.getBusEntity().getBusEntityId())));
            add(shoppingOptionsVarMeta);
        }

        {
            VarMeta shoppingControlVarMeta = new VarMeta();
            shoppingControlVarMeta.add(new ShoppingControlDataMeta(new TableField(ShoppingControlDataAccess.ACCOUNT_ID, pData.getBusEntity().getBusEntityId()),new TableField(ShoppingControlDataAccess.SITE_ID, 0)));
            add(shoppingControlVarMeta);
        }

        {
            VarMeta fiscalCalVarMeta = new VarMeta();
            fiscalCalVarMeta.add(new FiscalCalenderDataMeta(new TableField(FiscalCalenderDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            add(fiscalCalVarMeta);
        }

        {
            VarMeta noteVarMeta = new VarMeta();
            noteVarMeta.add(new NoteDataMeta(new TableField(NoteDataAccess.BUS_ENTITY_ID, pData.getBusEntity().getBusEntityId())));
            if (pData.getNoteList() != null && !noteVarMeta.isEmpty()) {
                for (Object oNote : pData.getNoteList()) {
                    noteVarMeta.add(new NoteTextDataMeta(new TableField(NoteTextDataAccess.NOTE_ID, ((NoteView) oNote).getNoteId())));
                }
            }
            add(noteVarMeta);
        }

        {
            VarMeta billToVarMeta = new VarMeta();
            billToVarMeta.add(new BusEntityAssocDataMeta(new TableField(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pData.getBusEntity().getBusEntityId()),new TableField(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.BILLTO_FOR_ACCOUNT)));
            if (pData.getAccountBillTos() != null && !pData.getAccountBillTos().isEmpty()) {
                for (Object oBillto : pData.getAccountBillTos()) {
                    BillToData billTo = (BillToData) oBillto;
                    billToVarMeta.add(new BusEntityDataMeta(new TableField(BusEntityDataAccess.BUS_ENTITY_ID, billTo.getBillToId())));
                    billToVarMeta.add(new AddressDataMeta(new TableField(AddressDataAccess.BUS_ENTITY_ID, billTo.getBillToId())));
                }
            }
            add(billToVarMeta);
        }
    }
}
