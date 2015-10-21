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
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bignerdranch.android.nerdmart.databinding.FragmentLoginBinding;

/**
 * Created by scotts on 10/20/15.
 */
public class LoginFragment extends NerdMartAbstractFragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    FragmentLoginBinding fragmentLoginBinding = DataBindingUtil
        .inflate(inflater, R.layout.fragment_login, container, false);

    fragmentLoginBinding.setLoginButtonClickListener(v -> {
      String username = fragmentLoginBinding.fragmentLoginUsername.getText().toString();
      String password = fragmentLoginBinding.fragmentLoginPassword.getText().toString();
      addSubscription(mNerdMartServiceManager
              .authenticate(username, password)
              .compose(loadingTransformer())
              .subscribe(authenticated -> {
                if (!authenticated) {
                  Toast.makeText(getActivity(), R.string.auth_failure_toast, Toast.LENGTH_SHORT)
                      .show();
                  return;
                }

                Toast.makeText(getActivity(), R.string.auth_success_toast, Toast.LENGTH_SHORT)
                    .show();

                Intent intent = ProductsActivity.newIntent(getActivity());
                getActivity().finish();
                startActivity(intent);
              })
      );
    });

    return fragmentLoginBinding.getRoot();
  }

}
