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
package com.bignerdranch.android.nerdmart.model;

import com.bignerdranch.android.nerdmartservice.service.payload.Product;
import com.bignerdranch.android.nerdmartservice.service.payload.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by scotts on 10/20/15.
 */
public class DataStore {

  private User mCachedUser;
  private List<Product> mCachedProducts;

  public UUID getCachedAuthToken() {
    return mCachedUser.getAuthToken();
  }

  public void setCachedUser(User user) {
    mCachedUser = user;
  }

  public User getCachedUser() {
    return mCachedUser;
  }

  public void setCachedProducts(List<Product> products) {
    mCachedProducts = products;
  }

}
