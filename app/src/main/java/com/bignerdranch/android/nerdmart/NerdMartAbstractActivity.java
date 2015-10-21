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

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.bignerdranch.android.nerdmart.databinding.ActivityAbstractNerdmartBinding;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmart.viewmodel.NerdMartViewModel;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;

/**
 * Created by scotts on 10/20/15.
 */
public abstract class NerdMartAbstractActivity extends AppCompatActivity {

  private CompositeSubscription mCompositeSubscription;

  @Inject
  NerdMartServiceManager mNerdMartServiceManager;

  @Inject
  NerdMartViewModel mNerdMartViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mCompositeSubscription = new CompositeSubscription();

    NerdMartApplication.component(this).inject(this);

    ActivityAbstractNerdmartBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_abstract_nerdmart);
    binding.setLogoutButtonClickListener(v -> signout());
    binding.setNerdMartViewModel(mNerdMartViewModel);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(binding.activityAbstractNerdmartFragmentFrame.getId(), getFragment())
          .commit();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mCompositeSubscription.clear();
  }

  public void updateCartStatus(Cart cart) {
    mNerdMartViewModel.updateCartStatus(cart);
  }

  protected abstract Fragment getFragment();

  protected void addSubscription(Subscription subscription) {
    mCompositeSubscription.add(subscription);
  }

  private void signout() {
    addSubscription(mNerdMartServiceManager
        .signout()
        .subscribe(aBoolean -> {
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
        }));
  }

}
