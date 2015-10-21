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
import com.bignerdranch.android.nerdmartservice.model.NerdDataSource;
import com.bignerdranch.android.nerdmartservice.model.NerdMartDataSourceInterface;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by scotts on 10/21/15.
 */
@Module(includes = { NerdMartMockServiceModule.class, NerdMartCommonModule.class })
public class NerdMartMockApplicationModule {

  private Context mApplicationContext;

  public NerdMartMockApplicationModule(Context applicationContext) {
    mApplicationContext = applicationContext;
  }

  @Provides
  @Singleton
  public NerdMartDataSourceInterface providesNerdMartDataSourceInterface() {
    return new NerdDataSource();
  }

  @Provides
  public Context providesContext() {
    return mApplicationContext;
  }

}
