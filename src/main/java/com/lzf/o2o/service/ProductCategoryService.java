package com.lzf.o2o.service;

import java.util.List;

import com.lzf.o2o.entity.ProductCategory;

public interface ProductCategoryService {
	List<ProductCategory> queryProductCategoryList(long shopId);
}
