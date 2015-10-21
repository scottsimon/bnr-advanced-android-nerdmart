/*
  COPYRIGHT 1995-2015  ESRI

  TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
  Unpublished material - all rights reserved under the
  Copyright Laws of the United States.

  For additional information, contact:
  Environmental Systems Research Institute, Inc.
  Attn: Contracts Dept
  380 New York Street
  Redlands, California, USA 92373

  email: contracts@esri.com
*/
package com.bignerdranch.android.nerdmart.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import com.bignerdranch.android.nerdmart.R;
import com.bignerdranch.android.nerdmartservice.service.payload.Product;

import java.text.NumberFormat;

/**
 * Created by scotts on 10/21/15.
 */
public class ProductViewModel extends BaseObservable {

  private Context mContext;
  private Product mProduct;
  private int mRowNumber;

  public ProductViewModel(Context context, Product product, int rowNumber) {
    mContext = context;
    mProduct = product;
    mRowNumber = rowNumber;
  }

  public Integer getId() {
    return mProduct.getId();
  }

  public String getSKU() {
    return mProduct.getSKU();
  }

  public String getTitle() {
    return mProduct.getTitle();
  }

  public String getDescription() {
    return mProduct.getDescription();
  }

  public String getDisplayPrice() {
    return NumberFormat
        .getCurrencyInstance()
        .format(mProduct.getPriceInCents() / 100.0);
  }

  public String getProductUrl() {
    return mProduct.getProductUrl();
  }

  public String getProductQuantityDisplay() {
    return mContext.getString(R.string.quantity_display_text, mProduct.getBackendQuantity());
  }

  public int getRowColor() {
    int resId = mRowNumber % 2 == 0 ? R.color.white : R.color.light_blue;
    return mContext.getResources().getColor(resId);
  }

}
