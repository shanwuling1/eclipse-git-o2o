package com.lzf.o2o.util;

public class PathUtil {
	private static String separator = System.getProperty("file.separator");
	//店铺图片定的存储根路径
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "F:/图片/images/";
		}else {
			basePath ="/home/xiangze/images/";
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}
	//店铺图片的存储子路径
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop" + shopId + "/";
		return imagePath.replace("/", separator);
	}
}
