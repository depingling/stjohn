<!--PAGESRCID=f_shoppingInvHeader.jsp -->

<tr>

    <app:displayProdHeader viewOptionEditCartItems="<%=editCartItems%>"
	viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false"
	viewOptionOrderGuide="false"
	viewOptionInventoryList="<%=\"inventory\".equalsIgnoreCase(itemFilter)%>"
	onClickDeleteAllCheckBox="SetCheckedGlobalRev('selectBox')"
	/>

</tr>

