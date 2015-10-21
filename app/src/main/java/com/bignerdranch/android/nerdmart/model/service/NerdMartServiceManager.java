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
package com.bignerdranch.android.nerdmart.model.service;

import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import com.bignerdranch.android.nerdmartservice.service.payload.Product;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.UUID;

/**
 * Created by scotts on 10/20/15.
 */
public class NerdMartServiceManager {

  private NerdMartServiceInterface mServiceInterface;
  private DataStore mDataStore;
  private Scheduler mScheduler;

  private final Observable.Transformer<Observable, Observable> mSchedulersTransformer =
      observable -> observable
          .subscribeOn(Schedulers.newThread())
//          .observeOn(AndroidSchedulers.mainThread());
          .observeOn(mScheduler);

  //region Constructors

  public NerdMartServiceManager(NerdMartServiceInterface serviceInterface, DataStore dataStore, Scheduler scheduler) {
    mServiceInterface = serviceInterface;
    mDataStore = dataStore;
    mScheduler = scheduler;
  }

  //endregion Constructors
  //region Public methods

  public Observable<Boolean> authenticate(String username, String password) {
//    // Standard implementation
//    return mServiceInterface.authenticate(username, password)
//        .map(new Func1<User, Boolean>() {
//          @Override
//          public Boolean call(User user) {
//            return user != null;
//          }
//        });

//    // Java 8 Lambda implementation -- alternate 1
//    return mServiceInterface.authenticate(username, password)
//        .doOnNext(user -> mDataStore.setCachedUser(user)) // same as 'mDataStore::setCachedUser' syntax
//        .map(user -> user != null)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread());

    // Java 8 Lambda implementation -- alternate 2
    return mServiceInterface
        .authenticate(username, password)
        .doOnNext(mDataStore::setCachedUser)
        .map(user -> user != null)
        .compose(applySchedulers()); // replace subscribeOn()/observeOn()
  }

  public Observable<Product> getProducts() {
    return getToken()
        .flatMap(mServiceInterface::requestProducts)
        .doOnNext(mDataStore::setCachedProducts)
        .flatMap(Observable::from)
        .compose(applySchedulers()); // replace subscribeOn()/observeOn()
  }

  public Observable<Cart> getCart() {
    return getToken()
        .flatMap(mServiceInterface::fetchUserCart)
        .doOnNext(mDataStore::setCachedCart)
        .compose(applySchedulers());
  }

  public Observable<Boolean> postProductToCart(final Product product) {
    return getToken()
        .flatMap(uuid -> mServiceInterface.addProductToCart(uuid, product))
        .compose(applySchedulers());
  }

  public Observable<Boolean> signout() {
    mDataStore.clearCache();
    return mServiceInterface.signout();
  }

  //endregion Public methods
  //region Private methods

  private Observable<UUID> getToken() {
    return Observable.just(mDataStore.getCachedAuthToken());
  }

  private <T> Observable.Transformer<T, T> applySchedulers() {
    return (Observable.Transformer<T, T>) mSchedulersTransformer;
  }

  //endregion Private methods

}
