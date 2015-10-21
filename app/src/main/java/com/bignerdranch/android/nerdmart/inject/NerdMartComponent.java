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

import com.bignerdranch.android.nerdmart.NerdMartAbstractActivity;
import com.bignerdranch.android.nerdmart.NerdMartAbstractFragment;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by scotts on 10/20/15.
 */
@Singleton
@Component(modules = { NerdMartApplicationModule.class })
public interface NerdMartComponent {

  void inject(NerdMartAbstractActivity activity);
  void inject(NerdMartAbstractFragment fragment);

}
