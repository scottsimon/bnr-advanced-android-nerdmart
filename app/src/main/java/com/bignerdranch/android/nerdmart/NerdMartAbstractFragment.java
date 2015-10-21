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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;

import javax.inject.Inject;

/**
 * Created by scotts on 10/20/15.
 */
public abstract class NerdMartAbstractFragment extends Fragment {

  @Inject
  NerdMartServiceInterface mNerdMartServiceInterface;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    NerdMartApplication.component(getActivity()).inject(this);
  }

}
