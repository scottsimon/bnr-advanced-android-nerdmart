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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bignerdranch.android.nerdmart.databinding.FragmentProductsBinding;
import com.bignerdranch.android.nerdmartservice.service.payload.Product;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import java.util.ArrayList;

/**
 * Created by scotts on 10/20/15.
 */
public class ProductsFragment extends NerdMartAbstractFragment {

  private ProductRecyclerViewAdapter mAdapter;

  private FragmentProductsBinding mFragmentProductsBinding;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState); // wha?? what happens to the returned View?

    mFragmentProductsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
    ProductRecyclerViewAdapter.AddProductClickEvent addProductClickEvent = this::postProductToCart;
    mAdapter = new ProductRecyclerViewAdapter(new ArrayList<>(), getActivity(), addProductClickEvent);
    setupAdapter();

    updateUI();

    return mFragmentProductsBinding.getRoot();
  }

  private void updateUI() {
    addSubscription(mNerdMartServiceManager
        .getProducts()
        .toList()
        .compose(loadingTransformer()) // shows/hides progress dialog
        .subscribe(products -> {
          Timber.i("received products: " + products);
          mAdapter.setProducts(products);
          mAdapter.notifyDataSetChanged();
        }));
  }

  private void setupAdapter() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mFragmentProductsBinding
        .fragmentProductsRecyclerView
        .setLayoutManager(linearLayoutManager);

    mFragmentProductsBinding
        .fragmentProductsRecyclerView
        .setAdapter(mAdapter);
  }

  private void postProductToCart(Product product) {
    Observable<Boolean> cartSuccessObservable = mNerdMartServiceManager
        .postProductToCart(product)
        .compose(loadingTransformer())
        .cache(); // this will reuse the results

    Subscription cartUpdateNotificationObservable = cartSuccessObservable
        .subscribe(aBoolean -> {
          int message = aBoolean ? R.string.product_add_success_message : R.string.product_add_failure_message;
          Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });
    addSubscription(cartUpdateNotificationObservable);

    addSubscription(cartSuccessObservable
        .filter(aBoolean -> aBoolean)
        .subscribeOn(Schedulers.newThread())
        .flatMap(aBoolean1 -> mNerdMartServiceManager.getCart())
        .subscribe(cart -> {
          ((NerdMartAbstractActivity) getActivity()).updateCartStatus(cart);
          updateUI();
        }));
  }

}
