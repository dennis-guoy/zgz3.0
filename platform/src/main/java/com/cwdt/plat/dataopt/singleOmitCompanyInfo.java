package com.cwdt.plat.dataopt;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 漏报企业信息
 * @author Administrator
 *
 */
public class singleOmitCompanyInfo implements Serializable {
	private static final long serialVersionUID = -3893411753137068209L;

	/**
	 * 
	 */
	public String id;
	/**
	 * 上报税管员ID
	 */
	public String sgyid;
	/**
	 * 企业名称
	 */
	public String company_name;
	/**
	 * 企业地址
	 */
	public String company_address;
	/**
	 * 联系人姓名
	 */
	public String faren_name;
	/**
	 * 联系人电话
	 */
	public String faren_phone;
	/**
	 * 
	 */
	public String sgy_lng;
	/**
	 * 
	 */
	public String sgy_lat;
	/**
	 * 备注信息
	 */
	public String remark;
	/**
	 * 上报类型
	 */
	public String omittype;
	
	public JSONObject toJsonObject()
	{
		JSONObject jRet=new JSONObject();
		try {
			jRet.put("id", id);
			jRet.put("sgyid", sgyid);
			jRet.put("company_name", company_name);
			jRet.put("company_address", company_address);
			jRet.put("faren_name", faren_name);
			jRet.put("faren_phone", faren_phone);
			jRet.put("sgy_lng", sgy_lng);
			jRet.put("sgy_lat", sgy_lat);
			jRet.put("remark", remark);
			jRet.put("omittype", omittype);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jRet;
	}
	
}
