package com.tencent.sgz.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.tencent.sgz.AppException;
import com.tencent.sgz.common.FileUtils;
import com.tencent.sgz.common.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

/**
 * 二维码扫描实体类
 * @author lv (http://t.qq.com/badstyle)
 * @version 1.0
 * @created 2014-4-17
 */
@SuppressWarnings("serial")
public class Barcode extends Entity implements Serializable{
	
	public final static String NODE_REQURE_LOGIN = "require_login";
	public final static String NODE_TYPE = "type"; 
	public final static String NODE_URL = "url";
	public final static String NODE_TITLE = "title";
	
	public final static byte LOGIN_IN = 0x00;// 登录
	public final static byte SIGN_IN = 0x01;// 签到
	
	private boolean requireLogin;// 是否需要登录
	private int type;// 类型
	private String url;// url地址
	private String title;// 标题
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isRequireLogin() {
		return requireLogin;
	}

	public void setRequireLogin(boolean requireLogin) {
		this.requireLogin = requireLogin;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public static Barcode parse(String barCodeString) throws AppException {
		Barcode barCode = new Barcode();
		try {
			// 由字符串创建json对象
			JSONObject jsonObject = new JSONObject(barCodeString);
			// 取数据操作
			if (!jsonObject.isNull(NODE_REQURE_LOGIN)) {
				barCode.setRequireLogin(jsonObject.getBoolean(NODE_REQURE_LOGIN));
			} else {
				barCode.setUrl("www.3gz.qq.com");
			}
			if (!jsonObject.isNull(NODE_TYPE)) {
				barCode.setType(jsonObject.getInt(NODE_TYPE));
			} else {
				barCode.setType(1);
			}
			if (!jsonObject.isNull(NODE_URL)) {
				barCode.setUrl(jsonObject.getString(NODE_URL));
			} else {
				barCode.setUrl("www.3gz.qq.com");
			}
			if (!jsonObject.isNull(NODE_TITLE)) {
				barCode.setTitle(jsonObject.getString(NODE_TITLE));
			} else {
				barCode.setTitle("");
			}
		} catch (JSONException e) {
			// 抛出一个json解析错误的异常
			throw AppException.json(e);
		} 
        return barCode;       
	}
	
	@Override
	public String toString() {
		return "Barcode [requireLogin=" + requireLogin + ", type=" + type
				+ ", url=" + url + "]";
	}
}
