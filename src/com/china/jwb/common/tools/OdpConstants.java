package com.china.jwb.common.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OdpConstants {
	/** 土豆标识 */
	public static final int TUDOU_SITE_CODE=1;
	/** 新浪微博标示 */
	public static final int WEIBO_SITE_CODE=2;
	/** 优酷标示 */
	public static final int YOUKU_SITE_CODE=3;
	/** 微信标识*/
	public static final int WEIXIN_SITE_CODE=4;
	/** facebook标识*/
	public static final int FACEBOOK_SITE_CODE=5;
	/** twitter标识*/
	public static final int TWITTER_SITE_CODE=6;
	/** CRI_CMS标识*/
	public static final int CRI_CMS_SITE_CODE=7;
	/** CHINAPLUS标识*/
	public static final int CHINAPLUS_SITE_CODE=8;
	
	
	
	/** 成功状态 */
	public static final int RESULT_STATUS_OK=0;
	/** 失败状态 */
	public static final int RESULT_STATUS_ERROR=1;
	/** 处理中状态 */
	public static final int RESULT_STATUS_PROCESSING=2;
	/** 参数错误信息 */
	public static final String RESULT_ERROR_MSG_PARAMETER = "Parameter is not valid!";
	/** 系统错误信息 */
	public static final String RESULT_ERROR_MSG_SYSTEM = "Internal error!";

	public static final int WEIXINPIC_SITE_CODE=4;
	/** 临时文件存储路径标识 */
	public static final String TEMP_FILE_PATH = "TEMP_FILE_PATH";
	/** 土豆APPKEY标识 */
	public static final String TUDOU_APP_KEY = "TUDOU_APP_KEY";
	/** 微博APPKEY标识 */
	public static final String WEIBO_APP_KEY = "WEIBO_APP_KEY";
	/** 长微博合成图片路径*/
	public static final String CREATIMG_PATH = "CREATIMG_PATH";
	
	
	


	//上传微信公共平台素材类型
	public static final String TYPE_MATRIAL = "video";
	public static final String TYPE_MATRIAL_VOICE = "voice";
	public static final String TYPE_MATRIAL_THUMB = "image";

	/*土豆授权口令*/
	public static final String TUDOU_CODE="67145d5fc6c3b2ceb06e5b66257f2cf9";
	public static final String TUDOU_TOKEN="JweBemakT4omToZ54my_Bkoo445agJy_pkyeoe5kawaTBem4gpgN4NJgN_4p4_gmmpp5g5gN4g5BZ";

	/*土豆应用APP SECRET*/
	public static final String TUDOU_APP_SECRET="14d6afc56049b593e297b940ed5a9bff";
	/**/
	public static final String TUDOU_CHANNELID="1";
	public static  Map<String,Object> taskmap = new ConcurrentHashMap<String,Object>();

	
	/*优酷参数*/
	public static final String YOUKU_APP_KEY = "YOUKU_APP_KEY";
	public static final String YOUKU_CLIENT_SECRET = "YOUKU_CLIENT_SECRET";
	public static final String LOGIN_NAME = "LOGIN_NAME";
	public static final String LOGIN_PWD = "LOGIN_PWD";
	
	
	/*微信接口*/
	//# access_tocken url获取token接口
	public static final String	access_token_url_weixin ="https://api.weixin.qq.com/cgi-bin/token";
    //# follower list interface url      -weixin 获取用户列表接口
	public static final String get_user_list_weixin ="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	//#user detail info url interface    -weixin
	public static final String get_userinfo_weixin ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//#upload metrials interface url     -weixin上传视频接口（返回mediaid）
	public static final String post_upload_weixin ="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//#distribute get group media id url -weixin再次获取media id（实际上发的media id）
	public static final String post_distribute_media_id_weixin ="https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
	//#distribute to all users interface -weixin最终发送
	public static final String post_group_send_weixin ="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	//分组群发
	public static final String post_group_send_weixin_group ="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	//#upload pictures and text information -weixin
	public static final String post_pic_text_weixin ="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	
}
