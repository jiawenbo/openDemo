package com.youku.uploader;

public class Config {

	protected static final String LOGIN_URL = "https://openapi.youku.com/v2/oauth2/token";
	protected static final String CREATE_URL = "https://openapi.youku.com/v2/uploads/create.json";
	protected static final String CREATE_FILE_URL = "http://upload_server_uri/gupload/create_file";
	protected static final String NEW_SLICE_URL = "http://upload_server_uri/gupload/new_slice";
	protected static final String UPLOAD_SLICE_URL = "http://upload_server_uri/gupload/upload_slice";
	protected static final String CHECK_URL = "http://upload_server_uri/gupload/check";
	protected static final String COMMIT_URL = "https://openapi.youku.com/v2/uploads/commit.json";
	protected static final String CANCEL_URL = "https://openapi.youku.com/v2/uploads/cancel.json";
	protected static final String VERSION_UPADATE_URL = "http://open.youku.com/sdk/version_update";

	protected static final String CATEGORY = "upload";
	protected static final String TYPE = "java";
	protected static final String VERSION = "2015081811";
	// 检查各分片状态，然后重置
	// private final String SLICES_URL = "http://upload_server_uri/slices";
	// private final String RESET_SLICE_URL =
	// "http://upload_server_uri/reset_slice";

	protected static Boolean DEBUG = false;

	/**
	 * check 2、3时 sleep
	 */
	protected static final int SLEEPTIME = 2000;

	/**
	 * 分片最大长度KB
	 */
	protected static final int SLICE_LENGTH = 10240;

	/**
	 * 一般接口请求 timeout
	 */
	protected static final int TIMEOUT = 200 * 1000;

	/**
	 * 数据传输 timeout
	 */
	protected static final int DATA_TIMEOUT = 200 * 1000;

	/**
	 * error code ( 仅以下特殊code 返回JSONObject，其他均通过接口返回，更多查看主站提供error code 文档 )
	 */
	protected static final String ERROR_10001 = "System error ";
	protected static final String ERROR_10003 = "Remote service exception";
	protected static final String ERROR_10017 = "The request is missing a required parameter";
	protected static final String ERROR_10018 = "The request parameter is invalid";
	protected static final String ERROR_10023 = "The username or password is invalid";
	protected static final String ERROR_20028 = "Video file cannot be found";

	/**
	 * 自定义 custom
	 */
	protected static final String ERROR_50001 = "upload task only one thread";
	protected static final String ERROR_50002 = "connect exception";

	protected static final String ERROR_TYPE_FILE_NOT_FOUND = "FileNotFoundException";
	protected static final String ERROR_TYPE_SYSTEM = "SystemException";
	protected static final String ERROR_TYPE_UPLOAD_TASK = "UploadTaskException";
	protected static final String ERROR_TYPE_CONNECT = "ConnectException";
}
