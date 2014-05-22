<!--PAGESRCID=f_shoppingPhysHeader.jsp -->

<tr>

    <app:displayProdHeader
        viewOptionEditCartItems="<%=editCartItems%>"
        viewOptionQuickOrderView="<%=quickOrderView%>"
        viewOptionAddToCartList="false"
        viewOptionOrderGuide="false"
        viewOptionInventoryList='<%="inventory".equalsIgnoreCase(itemFilter)%>'
        physicalInvCart="true"
        onClickDeleteAllCheckBox="SetCheckedGlobalRev('selectBox')"/>

</tr>

