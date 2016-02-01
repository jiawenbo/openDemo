package com.china.jwb.common.tools.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.china.jwb.common.tools.OdpConstants;
import com.china.jwb.common.tools.config.ConfigurationUtil;
import com.china.jwb.openplatform.entity.TaskInfo;

/**
 * 文字生成图片
 * 
 * @author Administrator
 * 
 */
public class MakeImg1beifeng {
	String fontN = "微软雅黑";
	BufferedImage image;
	int fontHeight = 0 ;
	int imgW = 740; // 图片的宽度
	int numFontBS = 30; // 标题字体的大小
	int numFontMS = 20; // 日期字体的大小
	int numFontSS = 20; // 正常内容字体的大小
	int spacelH = 5;  // 两者之间的标准间距
	int spaceSH = 15; // 两者之间的小间距
	int spaceMH = 20; // 两者之间的中等间距(第二条线与标题之间的间距)
	int spaceBH = 30; // 两者之间的大间距(作者与内容之间的间距)
	int lrLineW = 25; // 划线左右的宽度,内容显示的左边距

	static final String[] arrBB = { "<", ">", "[list]", "[*]", "[/list]",
			"[img]", "[/img]", "[b]", "[/b]", "[u]", "[/u]", "[i]", "[/i]",
			"[color=\"", "[/color]", "[size=\"", "[/size]", "[url=\"",
			"[/url]", "[mail=\"", "[/mail]", "[code]", "[/code]", "[quote]",
			"[/quote]", "\"]" };
	static final String[] arrHtml = { "&lt;", "&gt;", "<ul>", "<li>", "</ul>",
			"<img src=\"", "\">", "<b>", "</b>", "<u>", "</u>", "<i>", "</i>",
			"<span style=\"color:", "</span>", "<span style=\"font-size:",
			"</span>", "<a href=\"", "</a>", "<a href=\"mailto:", "</a>",
			"<code>", "</code>",
			"<table width=100% bgcolor=lightgray><tr><td bgcolor=white>",
			"</td></tr></table>", "\">" };

	void createImage(String fileLocation) {
		try {
			
			/* BufferedImage buffImage =  ImageIO.read(image);  
	            Graphics g = buffImage.getGraphics();  
	            Font font = new Font("宋体",Font.PLAIN,12);  
	            g.setFont(font);  
	            g.drawString("测试测试", 3, 50);  */
	          
	            
	            FileOutputStream outImg = new FileOutputStream(new File(ConfigurationUtil.getConfigValue(OdpConstants.CREATIMG_PATH)));   
	            ImageIO.write(image,"jpg", outImg);  
	            outImg.flush();  
	            outImg.close();  
			/*FileOutputStream fos = new FileOutputStream(fileLocation);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void graphicsGeneration(String imgurl, String imgurl2,TaskInfo taslInfo) {

		int tempSpaceH = 0; // 高度的旧时变量
		int imageWidth = 740; // 图片的宽度
		int imageHeight = 30;   // 图片的高度
		int imgFH = -20;  // 第一张图的高度
		int imgSH = 30;  // 第二张图的高度
		int titleW = 0; // 第一张图与内容之间的高度
		int cxtHeight = 0 ; // 内容的高度
		int cxtSize = 0;    // 内容数组的size
		BufferedImage bimg = null;  // 第一张图片
		BufferedImage bimg1 = null; // 第二张图片
        
		try {
			CxtToImgEntity entCxts = getData( taslInfo); // 获取指定的数据
			// 获取内容字符数组
			ArrayList<CxtMsgEntity> entCxt = entCxts.getEntCxts();
			cxtSize = entCxt.size();
			// 取得内容的高度
			cxtHeight = cxtSize * fontHeight;
/*
			// 获取第一张图片
			bimg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
			if (bimg != null) {
				int width = bimg.getWidth();
				double Ratio = (double) imgW / width;
				imgFH = Integer.parseInt(new java.text.DecimalFormat("0")
						.format(bimg.getHeight() * Ratio));
			}

			// 获取第二张图片
			bimg1 = javax.imageio.ImageIO.read(new java.io.File(imgurl2));
			if (bimg1 != null) {
				int width = bimg1.getWidth();
				double Ratio = (double) imgW / width;
				imgSH = Integer.parseInt(new java.text.DecimalFormat("0")
						.format(bimg1.getHeight() * Ratio));
			}*/

			/*
			 * 取得除每一张与每二张图片与内容高度以外的高度( 一个标准间距 + 一个标准间距:第一条线与字体的间距 + 日期字体的大小 +
			 * 一个标准间距:第一条线与字体的间距 + 线与标题之间的间距 + 标题字体的大小) + 标题与作者间的间距 + 作者字体的大小 +
			 * 大间距)
			 */
			titleW = spacelH + spacelH + numFontMS + spacelH + spacelH
					+ spaceMH + numFontBS + spaceSH + numFontSS + spaceBH;
			// 获取图片的高度(第一张图的高度 + 内容与第一张图之间的间距 + 内容的高度 + 第二张图的高度)
			imageHeight = imgFH + titleW + cxtHeight + imgSH;

			image = new BufferedImage(imageWidth, imageHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics graphics = image.getGraphics();
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, imageWidth, imageHeight);

		/*	// 画每一张图
			if (bimg != null) {
				graphics.drawImage(bimg, 0, 0, imgW, imgFH, null);
			}*/

			// 划第一条线
			//graphics.setColor(Color.GRAY);
			// 获取每个横线的Y坐标(第一张图片的高度加一个标准间距)
			  tempSpaceH = imgFH + spacelH;
			//graphics.drawLine(lrLineW, tempSpaceH, imgW - lrLineW, tempSpaceH);

			// 写入日期
			String setTime = entCxts.getInputDate();
			// 设置日期的写入的相关属性
			Font font = new Font(fontN, Font.BOLD, numFontMS);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			// 获取每个横线的Y坐标(tempSpaceH(第一张图片的高度 + 一个标准间距) + 一个标准间距:第一条线与字体的间距 +
			// 日期字体的大小)
			tempSpaceH = tempSpaceH + spacelH + numFontMS;
			// 写入日期字符
			//graphics.drawString(setTime, lrLineW, tempSpaceH);

			// 划第二条线
			//graphics.setColor(Color.GRAY);
			// 获取每个横线的Y坐标(tempSpaceH(第一张图片的高度 + 一个标准间距 + 一个标准间距:第一条线与字体的间距 +
			// 日期字体的大小)+一个标准间距:第一条线与字体的间距)
			tempSpaceH = tempSpaceH + spacelH;
		//	graphics.drawLine(lrLineW, tempSpaceH, imgW - lrLineW, tempSpaceH);

			// 标题的插入
			String strTitle = entCxts.getStrTitle();
			font = new Font(fontN, Font.BOLD, numFontBS);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			/*
			 * 获取每个横线的Y坐标(tempSpaceH(第一张图片的高度 + 一个标准间距 + 一个标准间距:第一条线与字体的间距 +
			 * 日期字体的大小)+一个标准间距:第一条线与字体的间距 + 线与标题之间的间距 + 标题字体的大小)
			 */
			tempSpaceH = tempSpaceH + spacelH + spaceMH + numFontBS;
			int with=(taslInfo.getResName().length()-2)*15;
			
			graphics.drawString(strTitle.toString(), lrLineW+300-with, tempSpaceH);
			// 作者的插入
			String strAuthor = entCxts.getStrAuthor();
			font = new Font(fontN, Font.PLAIN, numFontSS);
			graphics.setFont(font);
			/*
			 * 获取每个横线的Y坐标(tempSpaceH(第一张图片的高度 + 一个标准间距 + 一个标准间距:第一条线与字体的间距 +
			 * 日期字体的大小+一个标准间距:第一条线与字体的间距 + 线与标题之间的间距 +
			 * 标题字体的大小)+标题与作者间的间距+作者字体的大小)
			 */
			tempSpaceH = tempSpaceH + spaceSH + numFontSS;
			//graphics.drawString(strAuthor.toString(), lrLineW, tempSpaceH);

			// 取得内容开始的Y坐标(作者的Y坐标+大间距)
			tempSpaceH = tempSpaceH-20 ;
			// 画文章内容
			graphics.setColor(Color.black);
			for (int i = 0; i < cxtSize; i++) {
				CxtMsgEntity entAd = new CxtMsgEntity();
				entAd = entCxt.get(i);
				graphics.drawString(entAd.getStrCxt(), entAd.getNumX(), entAd
						.getNumY() + tempSpaceH);
				
			}

			/*// 画每二张图
			if (bimg1 != null) {
				graphics.drawImage(bimg1, 0, imgFH + titleW + cxtHeight, imgW,
						imgSH, null);
			}*/

			graphics.dispose();
			createImage(ConfigurationUtil.getConfigValue(OdpConstants.CREATIMG_PATH));
		} catch (Exception e) {
		}

	}

	public static void main(String[] args) {
		/*makeImg1beifeng cg = new makeImg1beifeng();
		try {
			cg.graphicsGeneration("E:11.jpg",
					"E:22.jpg",String tempValue);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		TaskInfo taslInfo=new TaskInfo();
		MakeImg1beifeng cg = new MakeImg1beifeng();
		taslInfo.setResName("测试");
		taslInfo.setResDesc("布雷顿说：“世界上没有比快乐更能使人美丽的化妆品。”微笑是快乐的象征，笑口常开，人见人爱；愁眉苦脸，人见人烦。因此，人生路上要少一点悲伤，多一点快乐，面对现实，笑对人生。笑代表心灵与心境的年轻；笑是乐观自信的体现；笑是积极向上的人生态度。笑是沮丧者的白天；笑是悲伤者的阳光；笑是美的姐妹；笑是艺术家的娇儿。从古至今，有回眸一笑百媚生的柔媚；有仰天大笑出门去的豪迈；有我自横刀向天笑的磅礴。一幅油画《蒙娜丽莎》流芳千古；一首歌曲《笑比哭好》至今传唱。朋友！如果你笑对人生，你会成为生活的强者；如果你以笑对人，你会成为众人的朋友。如果大家现在还不会笑的话，我可以告诉你们：我们这个美丽的星球充满了笑，有微笑，美笑，含笑……既然有这么多的笑，为什么不把它一一实践呢我可以告诉你们：我们这个美丽的星球充满了笑，有微笑，美笑，含笑……既然有我可以告诉你们：我们这个美丽的星球充满了笑，有微笑，美笑，含笑……既然有我可以告诉你们：我们这个美丽的星球充满了笑，有微笑，美笑，含笑……既然有我可以告诉你们：我们这个美丽的星球充满了笑，有微笑，美笑，含笑……既然有我可以告诉你们：我们这个美丽的星球充满了笑，有微笑，美笑，含笑……既然有我可以告诉你们：我们这个美丽的星球充满了笑，有微笑，美笑，含笑……既然有我可以告诉你们：我们这个美丽的星球充满了笑【，有微笑，美笑，含】笑……既然有你您你你你？");
		cg.graphicsGeneration("",
				"", taslInfo);
		
		
		

	}
  public  static void createImge(String imgs,String imgx, TaskInfo taslInfo){
	  MakeImg1beifeng cg = new MakeImg1beifeng();
		try {
			cg.graphicsGeneration(ConfigurationUtil.getConfigValue("Imges"),
					ConfigurationUtil.getConfigValue("Imges"), taslInfo);
			System.out.println("taslInfo"+taslInfo.getResDesc());
		} catch (Exception e) {
			e.printStackTrace();
		}
		

  }
	public CxtToImgEntity getData(TaskInfo taskInfo ) {
		CxtToImgEntity entCxts = new CxtToImgEntity();
		try {
			entCxts.setEntCxts(getCxtInfo(cvHtmlTBB(taskInfo.getResDesc())));
			System.out.println("taskInfo.getResDesc()"+taskInfo.getResDesc());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		entCxts.setStrAuthor("");
		entCxts.setStrTitle(taskInfo.getResName());
		entCxts.setInputDate(new Date().toString());
		return entCxts;
	}

	public static String cvHtmlTBB(String strText) {
		for (int i = 0; i < arrHtml.length; i++) {
			strText = strText.replace(arrHtml[i], arrBB[i]);
		}
		strText = strText.replaceAll("<img", "<img width=\"280px\"");
		Pattern CRLF = Pattern.compile("<br>");
		Matcher m = CRLF.matcher(strText);
		if (m.find()) {
			strText = m.replaceAll("\r\n");
		}
		return strText;
	}

	/**
	 * 内容数组
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<CxtMsgEntity> getCxtInfo(String cxt) throws SQLException,
			Exception {
		Font font = new Font("微软雅黑", Font.BOLD, 20);
		BufferedImage image = new BufferedImage(imgW, 500,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		FontMetrics fm = graphics.getFontMetrics(font);
		fontHeight = fm.getHeight()+5; // 字符的高度
		ArrayList<CxtMsgEntity> lstEntAd = new ArrayList<CxtMsgEntity>();

		int offsetLeft = 10;
		int rowIndex = 1;
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < cxt.length(); i++) {
			char c = cxt.charAt(i);
			int charWidth = fm.charWidth(c); // 获取当前字符的宽度
			// 另起一行
			if (Character.isISOControl(c)
					|| offsetLeft >= (imgW - (lrLineW * 2))) {
				rowIndex++;
				offsetLeft = 10;
				CxtMsgEntity entAd = new CxtMsgEntity();
				entAd.setStrCxt(sb.toString());
				entAd.setNumX(lrLineW);
				entAd.setNumY((rowIndex-1) * fontHeight);
				lstEntAd.add(entAd);
				sb = new StringBuffer("");
			}
			sb.append(String.valueOf(c));
			offsetLeft += (charWidth); // 这里不知道怎么取了来的字符跟拼起来的有误差大概有3个单位,这里减一下
			//System.out.println(offsetLeft);
			
			
		}
		if (offsetLeft > 10 && offsetLeft < (imgW - (lrLineW * 2))) {
			
			CxtMsgEntity entAd = new CxtMsgEntity();
			entAd.setStrCxt(sb.toString());
			entAd.setNumX(lrLineW);
			entAd.setNumY((rowIndex) * fontHeight-5);
			//System.out.println("高"+entAd.getNumY());
			lstEntAd.add(entAd);
		}
		 
		return lstEntAd;
	}

	/**
	 * 内容和问题页面上的时间设定
	 * 
	 * */
	public static String contentPageChoiseMonth(String getmonth) {
		if ("01".equals(getmonth)) {
			getmonth = "JANUARY";
		} else if ("02".equals(getmonth)) {
			getmonth = "FEBRUARY";
		} else if ("03".equals(getmonth)) {
			getmonth = "MARCH";
		} else if ("04".equals(getmonth)) {
			getmonth = "APRIL";
		} else if ("05".equals(getmonth)) {
			getmonth = "MAY";
		} else if ("06".equals(getmonth)) {
			getmonth = "JUNE";
		} else if ("07".equals(getmonth)) {
			getmonth = "JULY";
		} else if ("08".equals(getmonth)) {
			getmonth = "AUGUST";
		} else if ("09".equals(getmonth)) {
			getmonth = "SEPTEMBER";
		} else if ("10".equals(getmonth)) {
			getmonth = "OCTORBER";
		} else if ("11".equals(getmonth)) {
			getmonth = "NOVEMBER";
		} else if ("12".equals(getmonth)) {
			getmonth = "DECEMBER";
		}
		return getmonth;
	}

}
