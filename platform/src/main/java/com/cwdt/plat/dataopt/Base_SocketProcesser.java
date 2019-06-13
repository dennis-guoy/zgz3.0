package com.cwdt.plat.dataopt;

import com.cwdt.plat.dataopt.JsonBase;
import com.cwdt.plat.dataopt.SocketDataInfo;

import android.util.Log;

public class Base_SocketProcesser extends JsonBase {
	/**
	 * 通过Socket接收到的ID信息
	 */
	public String IdData = "";
	public static String EXT_SOCKETCMD_DATA="EXT_SOCKETCMD_DATA";
	public boolean isNeedReply = false;
	public SocketDataInfo socketDataInfo;
	public Base_SocketProcesser(String opt) {
//		super(opt);
		super();
	}

	@Override
	public boolean ParsReturnData() {
		boolean bRet = false;
		// dataMessage = new Message();
		// if (outJsonObject != null) {
		// try {
		// super.dataMessage = new Message() ;
		// JSONObject jo = outJsonObject.getJSONObject("result");
		// int iRet=jo.optInt("id");
		// bRet = iRet>0;
		// if (bRet) {
		// dataMessage.arg1 = 0;
		// }
		// else {
		// dataMessage.arg1 = 1;
		// }
		// dataMessage.obj = retRows;
		// } catch (Exception e) {
		// dataMessage.arg1 = 1;
		// dataMessage.obj = recvString;
		// Log.e(LogTAG, e.getMessage());
		// }
		// }
		return bRet;
	}

	@Override
	public void PacketData() {
		try {
			// optData.put("userinfo", retRows.toJsonObject());
		} catch (Exception e) {
			Log.e(LogTAG, e.getMessage());
		}
	}

	@Override
	public void prepareCustomData() {
		// TODO Auto-generated method stub

	}
	/**
	 * 执行数据处理过程
	 * @param param1
	 */
	public void doProcessData(SocketDataInfo param1) {
		RunDataAsync();
	}
	/**
	 * 当数据获取成功，并成功执行相应操作后执行
	 */
	public void doOnParseOK() {

	}

	/**
	 * 当数据获取出现错误，或者数据处理过程中出现错误时执行
	 */
	public void doOnParseErr() {

	}
}
