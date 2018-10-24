package com.lzf.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	
	// 处理缩略图
	public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {
		//随机文件名
		String realFileName = getRandomFileName();
		//获取文件扩展名
		String extension = getFileExtension(fileName);
		//创建所需目录
		makeDirPath(targetAddr);
		//生成的文件名
		String relativeAddr  = targetAddr + realFileName + extension;
		//最终存放位置及名称
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnailInputStream).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "gaoci.jpg")),0.5f)
			.outputQuality(0.8f).toFile(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return relativeAddr;
	}

	/**
	 * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg,
	 * 那么 home work xiangze 三个文件夹都要自动创建
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
	 * @param args
	 * @throws IOException
	 */
	public static String getRandomFileName() {
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		
		return nowTimeStr + rannum;
	}
	
	// 设置水印
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("F:/图片/timg6.jpg")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/sishen.jpg")), 0.25f)
				.outputQuality(0.8f).toFile("F:/图片/timg10.jpg");
	}

	
}
