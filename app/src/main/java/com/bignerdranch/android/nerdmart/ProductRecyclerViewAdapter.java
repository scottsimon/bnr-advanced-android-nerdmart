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

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bignerdranch.android.nerdmart.databinding.ViewProductRowBinding;
import com.bignerdranch.android.nerdmart.viewmodel.ProductViewModel;
import com.bignerdranch.android.nerdmartservice.service.payload.Product;

import java.util.List;

/**
 * Created by scotts on 10/21/15.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

  private Context mContext;
  private AddProductClickEvent mAddProductClickEvent;
  private List<Product> mProducts;

  public interface AddProductClickEvent {
    void onProductAddClick(Product product);
  }

  public ProductRecyclerViewAdapter(List<Product> products, Context context, AddProductClickEvent addProductClickEvent) {
    mProducts = products;
    mContext = context;
    mAddProductClickEvent = addProductClickEvent;
  }

  @Override
  public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
    ViewProductRowBinding viewProductRowBinding = DataBindingUtil
        .inflate(layoutInflater, R.layout.view_product_row, parent, false);
    return new ProductViewHolder(viewProductRowBinding);
  }

  @Override
  public void onBindViewHolder(ProductViewHolder holder, int position) {
    Product product = mProducts.get(position);
    holder.bindHolder(product, position);
  }

  @Override
  public int getItemCount() {
    return mProducts.size();
  }

  public void setProducts(List<Product> products) {
    mProducts = products;
  }

  //region Inner classes

  public class ProductViewHolder extends RecyclerView.ViewHolder {

    private ViewProductRowBinding mDataBinding;

    public ProductViewHolder(ViewProductRowBinding dataBinding) {
      super(dataBinding.getRoot());
      mDataBinding = dataBinding;
    }

    private void bindHolder(Product product, int position) {
      mDataBinding.setBuyButtonClickListener(v -> mAddProductClickEvent.onProductAddClick(product));
      mDataBinding.setProductViewModel(new ProductViewModel(mContext, product, position));
    }
  }

  //endregion Inner classes

}
