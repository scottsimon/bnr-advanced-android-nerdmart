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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;

/**
 * Created by scotts on 10/20/15.
 */
public abstract class NerdMartAbstractFragment extends Fragment {

  @Inject
  NerdMartServiceManager mNerdMartServiceManager;

  private CompositeSubscription mCompositeSubscription;
  private ProgressDialog mDialog;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    NerdMartApplication.component(getActivity()).inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mCompositeSubscription = new CompositeSubscription();
    setupLoadingDialog();
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mCompositeSubscription.clear();
  }

  protected void addSubscription(Subscription subscription) {
    mCompositeSubscription.add(subscription);
  }

  protected <T> Observable.Transformer<T, T> loadingTransformer() {
    return observable -> observable.doOnSubscribe(mDialog::show)
        .doOnCompleted(() -> {
          if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
          }
        });
  }

  private void setupLoadingDialog() {
    mDialog = new ProgressDialog(getActivity());
    mDialog.setIndeterminate(true);
    mDialog.setMessage(getString(R.string.loading_text));
  }

}
