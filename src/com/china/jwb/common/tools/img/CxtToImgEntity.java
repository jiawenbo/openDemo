package com.china.jwb.common.tools.img;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CxtMsgEntity
* @ClassName: CxtMsgEntity 
* @Description:  
* @author wu
* @date 2013-12-27
 */
public class CxtToImgEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/* �������� */
	private String inputDate;
	/* ���� */
	private String strTitle;
	/* ���� */
	private String strAuthor;
	/* �������ݽ�� */
	private ArrayList<CxtMsgEntity> entCxts;
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public String getStrAuthor() {
		return strAuthor;
	}
	public void setStrAuthor(String strAuthor) {
		this.strAuthor = strAuthor;
	}
	public ArrayList<CxtMsgEntity> getEntCxts() {
		return entCxts;
	}
	public void setEntCxts(ArrayList<CxtMsgEntity> entCxts) {
		this.entCxts = entCxts;
	}
}
