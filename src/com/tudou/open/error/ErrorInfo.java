package com.tudou.open.error;

import yao.util.type.BytesUtil;
import yao.util.type.ShortUtil;

/**
 * 错误信息类
 * 
 * @author myao
 */
public class ErrorInfo {

	private int error_code; // 错误码
	private String error_info; // 错误信息

	/**
	 * 构造一个错误信息
	 * 
	 * @param moudle
	 *            模块
	 * @param type
	 *            错误类型
	 * @param code
	 *            错误码
	 * @param info
	 *            错误信息
	 */
	public ErrorInfo(byte moudle, byte type, short code, String info) {
		byte[] valueBs = ShortUtil.toBytes(code);
		this.error_code = BytesUtil.toInt(new byte[] { valueBs[0], valueBs[1], type, moudle });
		this.error_info = info;
	}

	protected ErrorInfo(int code, String info) {
		this.error_code = code;
		this.error_info = info;
	}

	public int getError_code() {
		return error_code;
	}

	public String getError_info() {
		return this.error_info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + error_code;
		result = prime * result + ((error_info == null) ? 0 : error_info.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorInfo other = (ErrorInfo) obj;
		if (error_code != other.error_code)
			return false;
		if (error_info == null) {
			if (other.error_info != null)
				return false;
		} else if (!error_info.equals(other.error_info))
			return false;
		return true;
	}

	public String toString() {
		return toErrorString();
	}

	public String toErrorString() {
		return "[" + error_code + "]" + error_info;
	}
}
