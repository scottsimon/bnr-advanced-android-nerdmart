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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by scotts on 10/20/15.
 */
public class LoginFragment extends NerdMartAbstractFragment {

  @Bind(R.id.fragment_login_username)
  EditText mUsernameEditText;

  @Bind(R.id.fragment_login_password)
  EditText mPasswordEditText;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    View view = inflater.inflate(R.layout.fragment_login, container, false);
    ButterKnife.bind(this, view);

    return view;
  }

  @OnClick(R.id.fragment_login_login_button)
  public void handleLoginClick() {
    String username = mUsernameEditText.getText().toString();
    String password = mPasswordEditText.getText().toString();
    addSubscription(mNerdMartServiceManager
        .authenticate(username, password)
        .compose(loadingTransformer()) // shows/hides progress dialog
        .subscribe(authenticated -> {
          Toast.makeText(getActivity(), R.string.auth_success_toast, Toast.LENGTH_SHORT).show();

          if (authenticated) {
            Timber.i("Authenticated successfully!");
          } else {
            Timber.i("Authenticated failed :(");
          }

          Intent intent = ProductsActivity.newIntent(getActivity());
          startActivity(intent);
          getActivity().finish();
        }));
  }
}
