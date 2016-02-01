package com.china.jwb.common.tools.img;

import java.io.Serializable;

/**
 * CxtMsgEntity
* @ClassName: CxtMsgEntity 
* @Description:  
* @author wu
* @date 2013-12-27
 */
public class CxtMsgEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/* ���� */
	private String strCxt;
	/* ��� */
	private int numX;
	/* �߶� */
	private int numY;
	public String getStrCxt() {
		return strCxt;
	}
	public void setStrCxt(String strCxt) {
		this.strCxt = strCxt;
	}
	public int getNumX() {
		return numX;
	}
	public void setNumX(int numX) {
		this.numX = numX;
	}
	public int getNumY() {
		return numY;
	}
	public void setNumY(int numY) {
		this.numY = numY;
	}
	 
}
