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
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import com.bignerdranch.android.nerdmartservice.service.payload.Product;
import dagger.Component;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import rx.observers.TestSubscriber;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by scotts on 10/21/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestNerdMartServiceProductRequest {

  @Inject
  NerdMartServiceManager mNerdMartServiceManager;

  @Inject
  DataStore mDataStore;

  @Inject
  NerdMartDataSourceInterface mNerdMartDataSourceInterface;

  @Singleton
  @Component(modules = { NerdMartMockApplicationModule.class })
  public interface TestNerdMartServiceProductsComponent {
    TestNerdMartServiceProductRequest inject(TestNerdMartServiceProductRequest testNerdMartServiceProductRequest);
  }

  @Before
  public void setup() {
    NerdMartMockApplicationModule nerdMartMockApplicationModule =
        new NerdMartMockApplicationModule(RuntimeEnvironment.application);

    DaggerTestNerdMartServiceProductRequest_TestNerdMartServiceProductsComponent
        .builder()
        .nerdMartMockApplicationModule(nerdMartMockApplicationModule)
        .build()
        .inject(this);

    mDataStore.setCachedUser(mNerdMartDataSourceInterface.getUser());
  }

  @Test
  public void testGetProductsReturnsExpectedProductListings() {
    TestSubscriber<List<Product>> subscriber = new TestSubscriber<>();
    mNerdMartServiceManager
        .getProducts()
        .toList()
        .subscribe(subscriber);
    subscriber.awaitTerminalEvent();

    assertThat(subscriber.getOnNextEvents().get(0)).containsAll(mNerdMartDataSourceInterface.getProducts());
  }

  @Test
  public void testGetCartReturnsCartAndCachesCartInDataStore() {
    TestSubscriber<Cart> subscriber = new TestSubscriber<>();
    mNerdMartServiceManager
        .getCart()
        .subscribe(subscriber);
    subscriber.awaitTerminalEvent();

    Cart actualCart = subscriber.getOnNextEvents().get(0);

    assertThat(actualCart).isNotEqualTo(null);
    assertThat(mDataStore.getCachedCart()).isEqualTo(actualCart);
    assertThat(mDataStore.getCachedCart().getProducts()).hasSize(0);
  }

  @Test
  public void testPostProductToCartAddsProductsToUserCart() {
    ArrayList<Product> products = Lists.newArrayList();

    TestSubscriber<Boolean> postProductsSubscriber = new TestSubscriber<>();
    products.addAll(mNerdMartDataSourceInterface.getProducts());
    mNerdMartServiceManager
        .postProductToCart(products.get(0))
        .subscribe(postProductsSubscriber);
    postProductsSubscriber.awaitTerminalEvent();

    TestSubscriber<Cart> cartSubscriber = new TestSubscriber<>();
    mNerdMartServiceManager
        .getCart()
        .subscribe(cartSubscriber);
    cartSubscriber.awaitTerminalEvent();

    Cart cart = cartSubscriber.getOnNextEvents().get(0);
    assertThat(cart.getProducts()).hasSize(1);
  }

}
