package com.cwdt.plat.dataopt;

import com.cwdt.plat.dataopt.BaseSerializableData;

public class single_area_data extends BaseSerializableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1055934257976758249L;

	public String id;
	
	public String aid;
	public String OrganizationId;//OrganizationId
	public String OrganizationNo;//OrganizationNo
	public String OrganizationName;//OrganizationNo
	
	public String areaname;
	
	public String url;

	public String socketip;
	
	public String socketport;
	
	public String lng;
	
	public String lat;

	public String addrinfo;
	
	public String contactus;
	
	public String address;
	
	public String calist;
	
	
	public String remark;
	
	public String paidui;
//	public boolean fromJson(JSONObject jsData) {
//		boolean bRet = false;
//		try {
//
//			id = jsData.optString("id");
//			aid = jsData.optString("aid");
//			areaname = jsData.optString("areaname");
//			url = jsData.optString("url");
//			socketip = jsData.optString("socketip");
//			socketport = jsData.optString("socketport");
//			lng = jsData.optString("lng");
//			lat = jsData.optString("lat");
//			addrinfo = jsData.optString("addrinfo");
//			remark = jsData.optString("remark");
//
//		} catch (Exception e) {
//			Log.e("single_area_data", e.getMessage());
//		}
//		return bRet;
//	}
//
//	public boolean fromJsonStr(String jsonString) {
//		boolean bRet = false;
//		try {
//			JSONObject jsdata = new JSONObject(jsonString);
//			bRet = fromJson(jsdata);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Log.e("single_area_data", e.getMessage());
//		}
//		return bRet;
//	}
}
