package com.china.jwb.openplatform.entity;

import java.util.Arrays;
import java.util.List;


public class TaskInfo {
	private String id;							//task information id
	private String userName;					//appId
	private String passWord;					//app key
	private String resUrl;						//file url
	private String resName;						//title 
	private String resDesc;						//content
	private Integer resSize;					//file size 
	private Integer resDuration;				//file time length
	private Integer resWidth;					//file width
	private Integer resHeight;					//file height
	private Integer resBitRate;					//file bit rate
	private Integer targetSite;					//平台类型
	private Integer resType;					//file type,默认3为文档类型,1为视频类型.
	private String taskId;
	private String resRemark;					//简介
	private String pId;
	private String callBackUrl;
	private String  resTag;
	private String  accessToken;
	private String  accessTokenSecret;
	private String  urlContent;
	private String appId;
	private String appSecret;
	private String cmsUserId;					//第三方系统用户ID。CMS用
	//youku
	private String accountId;					//账号的ID，account表主键
	private String loginName;					//登陆名
	private String loginPwd;					//密码
	private String refreshToken;				//
	private String publishId;					//业务表主键
	private String uid;
	//CMS
	private String source;						//来源
	private String keyWord;						//关键字（多个用，隔开）
	private String criCmsCheck;					//CRI_CMS系统是否直接发布（1：是 0：否）
	private String criCmsGroupId;				//CRI_CMS系统频道ID
	private String criCmsGroupName;				//CRI_CMS系统频道ID
	private String criCmsCategoryId;			//CRI_CMS系统频道ID
	private String criCmsCategoryName;			//CRI_CMS系统频道ID
	private String criCmsOtherCategoryId;		//CRI_CMS系统频道ID
	private String criCmsOtherCategoryName;		//CRI_CMS系统频道ID
	
	//微信
	private String weixinAccountId;				//微信预览号
	//chinaPlus
	List<String> videoList;
	List<String> picList;
	List<String> audioList;
	//微博微信
	private Article Article[];//图文消息.
	private String  category;
	
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getUrlContent() {
		return urlContent;
	}
	public void setUrlContent(String urlContent) {
		this.urlContent = urlContent;
	}
	
    public String getCategory() {
		return category;
	}
    public Article[] getArticle() {
    	return Article;
    }


	public void setCategory(String category) {
		this.category = category;
	}
	public void setArticle(Article[] article) {
		Article = article;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getResTag() {
		return resTag;
	}
	public void setResTag(String resTag) {
		this.resTag = resTag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResDesc() {
		return resDesc;
	}
	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}
	public Integer getResSize() {
		return resSize;
	}
	public void setResSize(Integer resSize) {
		this.resSize = resSize;
	}
	public Integer getResDuration() {
		return resDuration;
	}
	public void setResDuration(Integer resDuration) {
		this.resDuration = resDuration;
	}
	public Integer getResWidth() {
		return resWidth;
	}
	public void setResWidth(Integer resWidth) {
		this.resWidth = resWidth;
	}
	public Integer getResHeight() {
		return resHeight;
	}
	public void setResHeight(Integer resHeight) {
		this.resHeight = resHeight;
	}
	public Integer getResBitRate() {
		return resBitRate;
	}
	public void setResBitRate(Integer resBitRate) {
		this.resBitRate = resBitRate;
	}
	public Integer getTargetSite() {
		return targetSite;
	}
	public void setTargetSite(Integer targetSite) {
		this.targetSite = targetSite;
	}
	public Integer getResType() {
		return resType;
	}
	public void setResType(Integer resType) {
		this.resType = resType;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getResRemark() {
		return resRemark;
	}
	public void setResRemark(String resRemark) {
		this.resRemark = resRemark;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPublishId() {
		return publishId;
	}
	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}
	
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getCriCmsCheck() {
		return criCmsCheck;
	}

	public void setCriCmsCheck(String criCmsCheck) {
		this.criCmsCheck = criCmsCheck;
	}

	public String getCriCmsGroupId() {
		return criCmsGroupId;
	}

	public void setCriCmsGroupId(String criCmsGroupId) {
		this.criCmsGroupId = criCmsGroupId;
	}

	public String getCriCmsGroupName() {
		return criCmsGroupName;
	}

	public void setCriCmsGroupName(String criCmsGroupName) {
		this.criCmsGroupName = criCmsGroupName;
	}

	public String getCriCmsCategoryId() {
		return criCmsCategoryId;
	}

	public void setCriCmsCategoryId(String criCmsCategoryId) {
		this.criCmsCategoryId = criCmsCategoryId;
	}

	public String getCriCmsCategoryName() {
		return criCmsCategoryName;
	}

	public void setCriCmsCategoryName(String criCmsCategoryName) {
		this.criCmsCategoryName = criCmsCategoryName;
	}

	public String getCriCmsOtherCategoryId() {
		return criCmsOtherCategoryId;
	}

	public void setCriCmsOtherCategoryId(String criCmsOtherCategoryId) {
		this.criCmsOtherCategoryId = criCmsOtherCategoryId;
	}

	public String getCriCmsOtherCategoryName() {
		return criCmsOtherCategoryName;
	}

	public void setCriCmsOtherCategoryName(String criCmsOtherCategoryName) {
		this.criCmsOtherCategoryName = criCmsOtherCategoryName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public List<String> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<String> videoList) {
		this.videoList = videoList;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public List<String> getAudioList() {
		return audioList;
	}

	public void setAudioList(List<String> audioList) {
		this.audioList = audioList;
	}

	public String getCmsUserId() {
		return cmsUserId;
	}

	public void setCmsUserId(String cmsUserId) {
		this.cmsUserId = cmsUserId;
	}

	public String getWeixinAccountId() {
		return weixinAccountId;
	}

	public void setWeixinAccountId(String weixinAccountId) {
		this.weixinAccountId = weixinAccountId;
	}

	@Override
	public String toString() {
		return "TaskInfo [Article=" + Arrays.toString(Article)
				+ ", accessToken=" + accessToken + ", accessTokenSecret="
				+ accessTokenSecret + ", accountId=" + accountId + ", appId="
				+ appId + ", appSecret=" + appSecret + ", audioList="
				+ audioList + ", callBackUrl=" + callBackUrl + ", category="
				+ category + ", cmsUserId=" + cmsUserId + ", criCmsCategoryId="
				+ criCmsCategoryId + ", criCmsCategoryName="
				+ criCmsCategoryName + ", criCmsCheck=" + criCmsCheck
				+ ", criCmsGroupId=" + criCmsGroupId + ", criCmsGroupName="
				+ criCmsGroupName + ", criCmsOtherCategoryId="
				+ criCmsOtherCategoryId + ", criCmsOtherCategoryName="
				+ criCmsOtherCategoryName + ", id=" + id + ", keyWord="
				+ keyWord + ", loginName=" + loginName + ", loginPwd="
				+ loginPwd + ", pId=" + pId + ", passWord=" + passWord
				+ ", picList=" + picList + ", publishId=" + publishId
				+ ", refreshToken=" + refreshToken + ", resBitRate="
				+ resBitRate + ", resDesc=" + resDesc + ", resDuration="
				+ resDuration + ", resHeight=" + resHeight + ", resName="
				+ resName + ", resRemark=" + resRemark + ", resSize=" + resSize
				+ ", resTag=" + resTag + ", resType=" + resType + ", resUrl="
				+ resUrl + ", resWidth=" + resWidth + ", source=" + source
				+ ", targetSite=" + targetSite + ", taskId=" + taskId
				+ ", uid=" + uid + ", urlContent=" + urlContent + ", userName="
				+ userName + ", videoList=" + videoList + ", weixinAccountId="
				+ weixinAccountId + ", getAccessToken()=" + getAccessToken()
				+ ", getAccessTokenSecret()=" + getAccessTokenSecret()
				+ ", getAccountId()=" + getAccountId() + ", getAppId()="
				+ getAppId() + ", getAppSecret()=" + getAppSecret()
				+ ", getArticle()=" + Arrays.toString(getArticle())
				+ ", getAudioList()=" + getAudioList() + ", getCallBackUrl()="
				+ getCallBackUrl() + ", getCategory()=" + getCategory()
				+ ", getCmsUserId()=" + getCmsUserId()
				+ ", getCriCmsCategoryId()=" + getCriCmsCategoryId()
				+ ", getCriCmsCategoryName()=" + getCriCmsCategoryName()
				+ ", getCriCmsCheck()=" + getCriCmsCheck()
				+ ", getCriCmsGroupId()=" + getCriCmsGroupId()
				+ ", getCriCmsGroupName()=" + getCriCmsGroupName()
				+ ", getCriCmsOtherCategoryId()=" + getCriCmsOtherCategoryId()
				+ ", getCriCmsOtherCategoryName()="
				+ getCriCmsOtherCategoryName() + ", getId()=" + getId()
				+ ", getKeyWord()=" + getKeyWord() + ", getLoginName()="
				+ getLoginName() + ", getLoginPwd()=" + getLoginPwd()
				+ ", getPassWord()=" + getPassWord() + ", getPicList()="
				+ getPicList() + ", getPublishId()=" + getPublishId()
				+ ", getRefreshToken()=" + getRefreshToken()
				+ ", getResBitRate()=" + getResBitRate() + ", getResDesc()="
				+ getResDesc() + ", getResDuration()=" + getResDuration()
				+ ", getResHeight()=" + getResHeight() + ", getResName()="
				+ getResName() + ", getResRemark()=" + getResRemark()
				+ ", getResSize()=" + getResSize() + ", getResTag()="
				+ getResTag() + ", getResType()=" + getResType()
				+ ", getResUrl()=" + getResUrl() + ", getResWidth()="
				+ getResWidth() + ", getSource()=" + getSource()
				+ ", getTargetSite()=" + getTargetSite() + ", getTaskId()="
				+ getTaskId() + ", getUid()=" + getUid() + ", getUrlContent()="
				+ getUrlContent() + ", getUserName()=" + getUserName()
				+ ", getVideoList()=" + getVideoList()
				+ ", getWeixinAccountId()=" + getWeixinAccountId()
				+ ", getpId()=" + getpId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
