package com.lzf.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzf.o2o.BaseTest;
import com.lzf.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest {
	@Autowired
	ProductCategoryDao productCategoryDao;

	@Test
	public void testQueryProductCategoryList() {
		long shopId = 1;
		List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("" + productCategories.size());
	}
}
