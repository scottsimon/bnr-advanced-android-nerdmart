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
package com.bignerdranch.android.nerdmart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import timber.log.Timber;

/**
 * Created by scotts on 10/20/15.
 */
public class ProductsFragment extends NerdMartAbstractFragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState); // wha?? what happens to the returned View?

    View view = inflater.inflate(R.layout.fragment_products, container, false);
    updateUI();
    return view;
  }

  private void updateUI() {
    addSubscription(mNerdMartServiceManager
        .getProducts()
        .compose(loadingTransformer()) // shows/hides progress dialog
        .subscribe(products -> {
          Timber.i("received products: " + products);
        }));
  }

}
