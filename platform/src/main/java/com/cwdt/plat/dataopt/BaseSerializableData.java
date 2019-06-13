package com.cwdt.plat.dataopt;

import java.io.Serializable;
import java.lang.reflect.Field;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaseSerializableData implements Serializable {

	public JSONObject toJsonObject() {
		JSONObject jRet = new JSONObject();
		jRet=(JSONObject)JSON.toJSON(this);
		return  jRet;
	}

	public  <T> T fromJson(JSONObject jsData) {
		try {
			return (T) jsData.toJavaObject(this.getClass());
		} catch (Exception e) {
			Log.e("BaseSerializableData", e.getMessage());
			return null;
		}
	}

	public <T> T  fromJson(String jsonString) {
		boolean bRet = false;
		try {
			return  initFromJson(jsonString);
		} catch (Exception e) {
			Log.e("BaseSerializableData", e.getMessage());
			return null;
		}
	}

	protected <T> T initFromJson(String jsonString) {
		boolean bRet = false;
		try {
			return (T) JSON.parseObject(jsonString,this.getClass());
		} catch (Exception e) {
			Log.e("BaseSerializableData", e.getMessage());
		}
		return null;
	}
	public boolean fromJsonStr(String jsonString) {
		boolean bRet = false;

		try {
			org.json.JSONObject jsdata = new org.json.JSONObject(jsonString);
			bRet = this.fromJson(jsdata);
		} catch (Exception var4) {
			Log.e("BaseSerializableData", var4.getMessage());
		}

		return bRet;
	}
	public boolean fromJson(org.json.JSONObject jsData) {
		boolean bRet = false;

		try {
			Field[] fields = this.getClass().getFields();
			Field[] var4 = fields;
			int var5 = fields.length;

			for(int var6 = 0; var6 < var5; ++var6) {
				Field field = var4[var6];
				if(!field.getType().isArray()) {
					String fname = field.getName();
					field.setAccessible(true);
					String fvalue = jsData.optString(fname);
					if(fvalue != "") {
						String strType = field.getType().toString();
						if(!strType.equals("int") && !strType.equals(Integer.class.toString())) {
							field.set(this, fvalue);
						} else {
							field.set(this, Integer.valueOf(fvalue));
						}
					}
				}
			}

			bRet = true;
		} catch (Exception var11) {
			Log.e("BaseSerializableData", var11.getMessage());
		}

		return bRet;
	}
}
