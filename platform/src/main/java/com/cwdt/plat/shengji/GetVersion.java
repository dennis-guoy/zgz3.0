package com.cwdt.plat.shengji;

import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cwdt.plat.data.BaseSoftversion;
import com.cwdt.plat.data.Const;
import com.cwdt.plat.dataopt.JngsJsonBase;


public class GetVersion extends JngsJsonBase {
	public String clientversion = "";
	public String updateurl = "";
	public String softid = "";     //软件类型 根据软件类型进行更新 如内部端 公众端
	public String orgid = "";     //组织机构ID 可以用来根据组织机构的不同进行分别更新
	public String remark="";      //版本更新说明
	public int  ismust;         //是否为必须更新 0：不是必须更新 1：必须更新

	public GetVersion() {
		super();
		interfaceUrl = Const.JSON_DATA_INTERFACE_URL+"/system/softversion/list";
	}

	@Override
	public boolean ParsReturnData() {
		boolean bRet = false;
		dataMessage = new Message();
		if (outJsonObject != null) {
			try {

				JSONArray jaArray = outJsonObject.getJSONArray("rows");
				JSONObject js = jaArray.getJSONObject(0);
				clientversion = js.getString("softversion");
				updateurl=js.getString("url");
				BaseSoftversion data=js.toJavaObject(BaseSoftversion.class);
				bRet = true;
				dataMessage.arg1=0;
				dataMessage.obj=data;
			} catch (Exception e) {
				dataMessage.arg1 = 1;
				dataMessage.obj = recvString;
				Log.e(LogTAG, e.getMessage());
			}
		}
		return bRet;
	}

	@Override
	public void PacketData() {
		try {
			optData.put("softid", softid);
			optData.put("orgid", orgid);
			optData.put("dm", 0);
		} catch (JSONException e) {
			Log.e(LogTAG, e.getMessage());
		}
	}


}
