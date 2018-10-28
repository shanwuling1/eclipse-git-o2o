package com.lzf.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = { RequestMethod.GET, RequestMethod.POST })
public class AdminController {

	@RequestMapping(value = "/shopoperation", method = RequestMethod.GET)
	private String shopEdit() {
		return "shop/shopoperation";
	}

	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	public String shopList() {
		return "shop/shoplist";
	}

	@RequestMapping(value = "/shopmanagement", method = RequestMethod.GET)
	public String shopManagement() {
		return "shop/shopmanagement";
	}

	@RequestMapping(value = "/productcategorymanage", method = RequestMethod.GET)
	public String productcategoryManage() {
		return "shop/productcategorymanage";
	}

}
