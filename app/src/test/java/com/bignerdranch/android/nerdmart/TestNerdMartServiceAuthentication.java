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

import com.bignerdranch.android.nerdmart.inject.NerdMartMockApplicationModule;
import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmartservice.model.NerdMartDataSourceInterface;
import dagger.Component;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import rx.observers.TestSubscriber;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by scotts on 10/21/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestNerdMartServiceAuthentication {

  @Inject
  NerdMartServiceManager mNerdMartServiceManager;

  @Inject
  DataStore mDataStore;

  @Inject
  NerdMartDataSourceInterface mNerdMartDataSourceInterface;

  @Singleton
  @Component(modules = { NerdMartMockApplicationModule.class })
  public interface TestNerdMartServiceAuthenticationComponent {
    TestNerdMartServiceAuthentication inject(TestNerdMartServiceAuthentication testNerdMartServiceAuthentication);
  }

  @Before
  public void setup() {
    NerdMartMockApplicationModule nerdMartMockApplicationModule =
        new NerdMartMockApplicationModule(RuntimeEnvironment.application);

    DaggerTestNerdMartServiceAuthentication_TestNerdMartServiceAuthenticationComponent
        .builder()
        .nerdMartMockApplicationModule(nerdMartMockApplicationModule)
        .build()
        .inject(this);
  }

//  // Dependency Injection sanity test
//  @Test
//  public void testDependencyInjectionWorked() {
//    assertThat(mNerdMartServiceManager).isNotNull();
//    assertThat(mDataStore).isNotNull();
//    assertThat(mNerdMartDataSourceInterface).isNotNull();
//  }

  @Test
  public void testAuthenticateMethodReturnsFalseWithInvalidCredentials() {
    TestSubscriber<Boolean> subscriber = new TestSubscriber<>();
    mNerdMartServiceManager
        .authenticate("johnnydoe", "WRONGPASSWORD")
        .subscribe(subscriber);

    subscriber.awaitTerminalEvent();

    assertThat(subscriber.getOnNextEvents().get(0)).isEqualTo(false);
    assertThat(mDataStore.getCachedUser()).isEqualTo(null);
  }

  @Test
  public void testAuthenticateMethodReturnsTrueWithValidCredentials() {
    TestSubscriber<Boolean> subscriber = new TestSubscriber<>();
    mNerdMartServiceManager
        .authenticate("johnnydoe", "pizza")
        .subscribe(subscriber);

    subscriber.awaitTerminalEvent();

    assertThat(subscriber.getOnNextEvents().get(0)).isEqualTo(true);
    assertThat(mDataStore.getCachedUser()).isEqualTo(mNerdMartDataSourceInterface.getUser());
  }

  @Test
  public void testSignoutRemovesUserObjects() {
    TestSubscriber<Boolean> subscriber = new TestSubscriber<>();
    mNerdMartServiceManager
        .authenticate("johnnydoe", "pizza")
        .subscribe(subscriber);
    subscriber.awaitTerminalEvent();

    TestSubscriber<Boolean> signoutSubscriber = new TestSubscriber<>();
    mNerdMartServiceManager
        .signout()
        .subscribe(signoutSubscriber);
    signoutSubscriber.awaitTerminalEvent();

    assertThat(mDataStore.getCachedUser()).isEqualTo(null);
    assertThat(mDataStore.getCachedCart()).isEqualTo(null);
  }

}
