package com.lzf.o2o.dao;

import java.util.List;

import com.lzf.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
     * 
     * 
     * @Title: batchInsertProductCategory
     * 
     * @Description: 批量增加roductCategory
     * 
     * @param productCategoryList
     * 
     * @return: int
     */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	List<ProductCategory> queryProductCategoryList(long shopId);
}
