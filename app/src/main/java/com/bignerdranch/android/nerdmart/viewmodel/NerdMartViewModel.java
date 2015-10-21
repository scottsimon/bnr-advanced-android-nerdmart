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
import android.databinding.Bindable;
import com.bignerdranch.android.nerdmart.R;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import com.bignerdranch.android.nerdmartservice.service.payload.User;
import com.bignerdranch.android.nerdmart.BR;

/**
 * Created by scotts on 10/21/15.
 */
public class NerdMartViewModel extends BaseObservable {

  private Context mContext;
  private Cart mCart;
  private User mUser;

  public NerdMartViewModel(Context context, Cart cart, User user) {
    mContext = context;
    mCart = cart;
    mUser = user;
  }

  public String formatCartItemsDisplay() {
    int numItems = 0;
    if (mCart != null && mCart.getProducts() != null) {
      numItems = mCart.getProducts().size();
    }
    return mContext.getResources().getQuantityString(R.plurals.cart, numItems, numItems);
  }

  public String getUserGreeting() {
    return mContext.getString(R.string.user_greeting, mUser.getName());
  }

  @Bindable
  public String getCartDisplay() {
    return formatCartItemsDisplay();
  }

  public void updateCartStatus(Cart cart) {
    mCart = cart;
    notifyPropertyChanged(BR.cartDisplay);
  }

}
