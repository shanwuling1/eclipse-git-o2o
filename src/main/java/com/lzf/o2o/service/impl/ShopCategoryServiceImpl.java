package com.lzf.o2o.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzf.o2o.dao.ShopCategoryDao;
import com.lzf.o2o.entity.ShopCategory;
import com.lzf.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition)
			throws IOException {
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}
}
