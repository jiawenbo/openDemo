package com.china.jwb.common.tools.img;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.awt.*;
import java.io.File;

import org.apache.log4j.Logger;


/**
 * Created by liuzhixian on 2015/10/2.
 */
public class ImgUtil {
	private static final Logger logger = Logger.getLogger(ImgUtil.class);
	
	
    public static  void Html2Image(String file,String content){
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        Dimension ds = new Dimension(400,4000);
        imageGenerator.setSize(ds);
        String htmlstr = content;
        imageGenerator.loadHtml(htmlstr);
        imageGenerator.getBufferedImage();
        try {
        	ImgUtil.mkdirPath(file);
        	imageGenerator.saveAsImage(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
        imageGenerator.saveAsHtmlWithMap("hello-world.html", file);
    }
    
    /***
     * 帶文件名的創建
     * @param filePath
     */
    public static void mkdirPath(String filePath) {
    	 String paths[] = filePath.split("/");
    	 String dir = paths[0];
    	 for (int i = 0; i < paths.length - 2; i++) {//注意此处循环的长度
	    	 try {
		    	 dir = dir +"/"+ paths[i + 1];
		    	 File dirFile = new File(dir);
		    	 if (!dirFile.exists()) {
		    		 dirFile.mkdir();
		    		 System.out.println("创建目录为："+ dir);
		    	 }
	    	 } catch (Exception err) {
	    		 System.err.println("ELS - Chart : 文件夹创建发生异常");
	    	 }
    	 }
    }
    public static void main(String[] args) {
    	String filePath = "/www/huayun.china.com/uploadFile/tempFile/weiBo/hehe.png";
    	ImgUtil.mkdirPath(filePath);
	}
}
