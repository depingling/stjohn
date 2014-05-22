package com.cleanwise.service.api.value;

import java.util.Collection;

/**
 * Title:        AssetDetailDataVector
 * Description:  Container object for AssetDetailData objects
 * Purpose:      Provides container storage for AssetDetailData objects.
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date: 21.12.2006
 * Time: 9:41:15
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class AssetDetailDataVector extends java.util.ArrayList{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -6769332195386358411L;

    public AssetDetailDataVector() {
    }

    public AssetDetailDataVector(int initialCapacity) {
        super(initialCapacity);
    }

    public AssetDetailDataVector(Collection c) {
        super(c);
    }
}
