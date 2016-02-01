package com.china.jwb.openplatform.entity;

/**
 * 图文的bean,一个图文消息支持1到10条图文.
 * @author jiawenbo
 *
 */
public class Article {


	//缩略图id.
	private String thurmbMediaId;
	//缩略图原始地址.
	private String thurmbMediaUrl;
	//图文消息作者.(不是必选项)
	private String author;
	//图文消息的标题.
	private String title;
	//图文消息点击"阅读原文"后的页面(不是必选项)
	private String contentSourceUrl;
	//图文消息页面文字内容,支持html标签.
	private String content;
	//图文消息的描述.(不是必选项)
	private String digest;
	//图文显示封面,1 为显示,0 为不显示.(不是必选项)
	private String showCoverPic;


	//缩略图id.
	private String display_name;
	//缩略图原始地址.
	private String summary;
	//图文消息作者.(不是必选项)
	private String image;
	//图文消息的标题.
	private String url;

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThurmbMediaUrl() {
		return thurmbMediaUrl;
	}
	public void setThurmbMediaUrl(String thurmbMediaUrl) {
		this.thurmbMediaUrl = thurmbMediaUrl;
	}
	
	public String getThurmbMediaId() {
		return thurmbMediaId;
	}
	public void setThurmbMediaId(String thurmbMediaId) {
		this.thurmbMediaId = thurmbMediaId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentSourceUrl() {
		return contentSourceUrl;
	}
	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getShowCoverPic() {
		return showCoverPic;
	}
	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic = showCoverPic;
	}
	
}