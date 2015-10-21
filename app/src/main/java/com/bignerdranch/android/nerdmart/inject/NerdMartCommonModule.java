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

import android.content.Context;
import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmart.viewmodel.NerdMartViewModel;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by scotts on 10/21/15.
 */
@Module
public class NerdMartCommonModule {

  @Provides
  @Singleton
  DataStore providesDataStore() {
    return new DataStore();
  }

  @Provides
  NerdMartViewModel providesNerdMartViewModel(Context context, DataStore dataStore) {
    return new NerdMartViewModel(context, dataStore.getCachedCart(), dataStore.getCachedUser());
  }

}
