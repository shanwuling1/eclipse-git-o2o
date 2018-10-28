package com.lzf.o2o.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzf.o2o.BaseTest;
import com.lzf.o2o.entity.ProductCategory;

public class ProductCategoryServiceTest extends BaseTest{
	@Autowired 
	private ProductCategoryService productCategoryService;
	@Test public void testQueryProductCategoryList() { 
		long shopId = 1; 
		List<ProductCategory> productCategories = productCategoryService.queryProductCategoryList(shopId); 
		System.out.println(productCategories.size());
		
	}
	
}
