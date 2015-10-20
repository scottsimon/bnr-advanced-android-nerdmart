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
import com.bignerdranch.android.nerdmartservice.service.NerdMartService;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;
import dagger.Module;
import dagger.Provides;

/**
 * Created by scotts on 10/20/15.
 */
@Module
public class NerdMartApplicationModule {

  private Context mApplicationContext;

  public NerdMartApplicationModule(Context applicationContext) {
    mApplicationContext = applicationContext;
  }

  @Provides
  NerdMartServiceInterface providesNerdMartServiceInterface() {
    return new NerdMartService();
  }

}