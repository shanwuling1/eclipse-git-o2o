package com.lzf.o2o.service;

import java.io.InputStream;

import com.lzf.o2o.dto.ShopExecution;
import com.lzf.o2o.entity.Shop;
import com.lzf.o2o.exception.ShopOperationException;

public interface ShopService {

	/**
	 * 创建商铺
	 * 
	 * @param Shop
	 *            shop
	 * @return ShopExecution shopExecution
	 * @throws Exception
	 */
	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
	
}
