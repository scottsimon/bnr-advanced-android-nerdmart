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
package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmartservice.service.NerdMartService;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;
import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;

import javax.inject.Singleton;

/**
 * Created by scotts on 10/21/15.
 */
@Module
public class NerdMartServiceModule {

  @Provides
  @Singleton
  NerdMartServiceInterface providesNerdMartServiceInterface() {
    return new NerdMartService();
  }

  @Provides
  @Singleton
  NerdMartServiceManager providesNerdMartServiceManager(NerdMartServiceInterface serviceInterface, DataStore dataStore) {
    return new NerdMartServiceManager(serviceInterface, dataStore, AndroidSchedulers.mainThread());
  }

}
