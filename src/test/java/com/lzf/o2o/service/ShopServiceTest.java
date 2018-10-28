package com.lzf.o2o.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzf.o2o.BaseTest;
import com.lzf.o2o.dto.ShopExecution;
import com.lzf.o2o.entity.PersonInfo;
import com.lzf.o2o.entity.Shop;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		shopCondition.setOwner(personInfo);
		shopCondition.setShopName("咖啡");
		// 符合 shop_name like '%咖啡%' 且 owner_id =1 有3条数据，
		// 第二个参数 和 第三个参数 从pageIndex=1 第一页取数据，取2条 pageSize=2
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		// 按照tb_shop中的数据筛选 符合条件的数据3条， 从第一页开始取2条，se.getShopList().size() 应该有2条数据，
		Assert.assertNotNull(se);
		Assert.assertEquals(2, se.getShopList().size());
		Assert.assertEquals(3, se.getCount());
		// 按照tb_shop中的数据筛选 符合条件的数据3条， 从第2页开始取2条，
		se.getShopList().size();
		// 应该只有1条数据，总数仍为3
		se = shopService.getShopList(shopCondition, 2, 2);
		Assert.assertNotNull(se);
		Assert.assertEquals(1, se.getShopList().size());
		Assert.assertEquals(3, se.getCount());
	}

//	@Test 
//	public void testModifyShop() throws ShopOperationException,FileNotFoundException{ 
//		Shop shop = new Shop();
//		shop.setShopId(1L);
//		shop.setShopName("修改后的名字");
//		File shopImg = new File("F:/图片/timg7.jpg");
//		InputStream iStream = new FileInputStream(shopImg);
//		ShopExecution shopExecution = shopService.modifyShop(shop, iStream, "timg7.jpg");
//		System.out.println(shopExecution.getShop().getShopImg());
//	}

//	@Test
//	public void testAddShop() throws Exception {
//		Shop shop = new Shop();
//		PersonInfo owner = new PersonInfo();
//		Area area = new Area();
//		ShopCategory shopCategory = new ShopCategory();
//		owner.setUserId(1L);
//		area.setAreaId(2);
//		shopCategory.setShopCategoryId(1L);
//		shop.setOwner(owner);
//		shop.setArea(area);
//		shop.setShopCategory(shopCategory);
//		shop.setShopName("咖啡店Improve");
//		shop.setShopDesc("小工匠的咖啡店Improve");
//		shop.setShopAddr("NanJing-Improve");
//		shop.setPhone("9876553");
//		shop.setPriority(99);
//		shop.setCreateTime(new Date());
//		shop.setLastEditTime(new Date());
//		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
//		shop.setAdvice("审核中Improve");
//		File shopImg = new File("F:/图片/timg5.jpg");
//		InputStream iStream = new FileInputStream(shopImg);
//		ShopExecution se = shopService.addShop(shop, iStream, shopImg.getName());
//		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
//	}
}
