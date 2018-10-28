package com.lzf.o2o.service;

import java.io.IOException;
import java.util.List;

import com.lzf.o2o.entity.ShopCategory;

public interface ShopCategoryService {

	/**
	 * 
	 * @param parentId
	 * @return
	 * @throws IOException
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopcategoryCondition) throws IOException;

}
