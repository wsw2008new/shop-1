package cn.my360.shop.entity;

import com.alibaba.fastjson.JSON;


/**
 * �汾��Ϣ
 * */
public class Version {
	public String content="";//��������
	public String versionCode="1";//�汾CODE
	public String apk_name="";//apk����
	public String url="";//apk���ص�ַ
	public String versionName ="";
	
	public static Version parse(String result){
		return JSON.parseObject(result, Version.class);
	}

}