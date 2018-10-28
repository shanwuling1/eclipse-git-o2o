package com.lzf.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzf.o2o.dto.ShopExecution;
import com.lzf.o2o.entity.Area;
import com.lzf.o2o.entity.PersonInfo;
import com.lzf.o2o.entity.Shop;
import com.lzf.o2o.entity.ShopCategory;
import com.lzf.o2o.enums.ShopStateEnum;
import com.lzf.o2o.exception.ShopOperationException;
import com.lzf.o2o.service.AreaService;
import com.lzf.o2o.service.ShopCategoryService;
import com.lzf.o2o.service.ShopService;
import com.lzf.o2o.util.CodeUtil;
import com.lzf.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	/**
	 * 
	 * 
	 * @Title: getShopList
	 * 
	 * @Description: 从session中获取当前person拥有的商铺列表
	 * 
	 * @param request
	 * @return
	 * 
	 * @return: Map<String,Object>
	 */
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 现在还没有做登录模块，因此session中并没有用户的信息，先模拟一下登录 要改造TODO
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		personInfo.setName("liuzifan");
		request.getSession().setAttribute("user", personInfo);
		// 从session中获取user信息
		personInfo = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(personInfo);
			ShopExecution se = shopService.getShopList(shopCondition, 1, 99);
			modelMap.put("success", true);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", personInfo);
		} catch (ShopOperationException e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * 
	 * 
	 * @Title: shopManagement
	 * 
	 * @Description: 从商铺列表页面中，点击“进入”按钮进入
	 *               某个商铺的管理页面的时候，对session中的数据的校验从而进行页面的跳转，是否跳转到店铺列表页面或者可以直接操作该页面
	 * 
	 *               访问形式如下 http://ip:port/o2o/shopadmin/shopmanagement?shopId=xxx
	 * 
	 * @return
	 * 
	 * @return: Map<String,Object>
	 */
	@RequestMapping(value = "/getshopmanageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopManageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取shopId
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		// 如果shopId不合法
		if (shopId < 0) {
			// 尝试从当前session中获取
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			if (currentShop == null) {
				// 如果当前session中也没有shop信息,告诉view层 重定向
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			} else {
				// 告诉view层 进入该页面
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		} else {
			// shopId合法的话
			Shop shop = new Shop();
			shop.setShopId(shopId);
			// 将currentShop放到session中
			request.getSession().setAttribute("currentShop", shop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}

	/*
	 * 根据相应的店铺id获取相应的店铺信息
	 *
	 */
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopById(HttpServletRequest request) {
		// 存放数据的格式
		Map<String, Object> modelmap = new HashMap<String, Object>();
		// 先将前台获取的id转为Long型，才能操作
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		// 我们需要查询店铺信息和地域列表信息
		if (shopId > -1) {
			try {
				// 查询店铺信息
				Shop shop = shopService.getByShopId(shopId);
				// 获取店铺列表
				List<Area> areaList = areaService.getAreaList();
				// 存入shop
				modelmap.put("shop", shop);
				// 存入区域列表
				modelmap.put("areaList", areaList);
				modelmap.put("success", true);
			} catch (Exception e) {
				modelmap.put("success", false);
				modelmap.put("errMsg", e.toString());
			}
		} else {
			modelmap.put("success", false);
			modelmap.put("errMsg", "emptyshopId");
		}
		return modelmap;
	}

	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * @Title: registerShop
	 * @Description:
	 * @param request 因为是接收前端的请求，而前端的信息都封装在HttpServletRequest中，所以需要解析HttpServletRequest，获取必要的参数
	 *                1. 接收并转换相应的参数，包括shop信息和图片信息 2. 注册店铺 3. 返回结果给前台
	 * @return: Map<String,Object>
	 */
	@RequestMapping(value = "/registshop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 1. 接收并转换相应的参数，包括shop信息和图片信息
		// 1.1 shop信息 shopStr 是和前端约定好的参数值，后端从request中获取request这个值来获取shop的信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// 使用jackson-databind 将json转换为pojo
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// 将json转换为pojo
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		// 1.2 图片信息 基于Apache Commons FileUpload的文件上传
		// Spring MVC中的 图片存在CommonsMultipartFile中
		CommonsMultipartFile shopImg = null;
		// 从request的本次会话中的上线文中获取图片的相关内容
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// shopImg是和前端约定好的变量名
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		} else {
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", "图片不能为空");
		}
		// 2. 注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			// 注册店铺
			ShopExecution se = null;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				e.printStackTrace();
				modelMap.put("success", false);
				modelMap.put("errMsg", "addShop Error");
			}
		} else {
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 1. 接收并转换相应的参数，包括shop信息和图片信息
		// 1.1 shop信息
		// shopStr 是和前端约定好的参数值，后端从request中获取request这个值来获取shop的信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// 使用jackson-databind 将json转换为pojo
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// 将json转换为pojo
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			e.printStackTrace();
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		// 1.2 图片信息 基于Apache Commons FileUpload的文件上传
		// Spring MVC中的 图片存在CommonsMultipartFile中
		CommonsMultipartFile shopImg = null;
		// 从request的本次会话中的上线文中获取图片的相关内容
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// shopImg是和前端约定好的变量名
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		}
		// 2. 修改店铺信息
		if (shop != null && shop.getShopId() != null) {
			// 修改店铺
			ShopExecution se = null;
			try {
				if (shopImg == null) {
					se = shopService.modifyShop(shop, null, null);
				} else {
					se = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				}

				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("errMsg", "修改成功");
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				e.printStackTrace();
				modelMap.put("success", false);
				modelMap.put("errMsg", "addShop Error");
			}
		} else {
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺Id");
		}
		return modelMap;
	}
}
