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

import android.app.Application;
import android.content.Context;
import com.bignerdranch.android.nerdmart.inject.DaggerNerdMartComponent;
import com.bignerdranch.android.nerdmart.inject.NerdMartApplicationModule;
import com.bignerdranch.android.nerdmart.inject.NerdMartComponent;
import timber.log.Timber;

/**
 * Created by scotts on 10/20/15.
 */
public class NerdMartApplication extends Application {

  private NerdMartComponent mComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());
    buildComponentAndInject();
  }

  public void buildComponentAndInject() {
    mComponent = DaggerComponentInitializer.init(this);
  }

  public static NerdMartComponent component(Context context) {
    return ((NerdMartApplication) context.getApplicationContext()).getComponent();
  }

  public NerdMartComponent getComponent() {
    return mComponent;
  }

  public final static class DaggerComponentInitializer {
    public static NerdMartComponent init(NerdMartApplication application) {
      return DaggerNerdMartComponent.builder()
          .nerdMartApplicationModule(new NerdMartApplicationModule(application)) // is this magic? voodoo? witchcraft?
          .build();
    }
  }

}
