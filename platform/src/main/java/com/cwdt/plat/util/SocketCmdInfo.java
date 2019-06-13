package com.cwdt.plat.util;

import java.util.ArrayList;

import android.util.Log;

import com.cwdt.plat.dataopt.SocketDataInfo;
public class SocketCmdInfo {
	protected static final String LogTag="SocketCmdInfo";
	public static final String CMD_SPLIT = "#";
	public static final String DTA_SPLIT = "|";
	
	
	// ----命令分组
	public static final int HEARTCOMMAND = 0;
	public static final int CREATESOCKCOMMAND = 1;
	public static final int CLOSESOCKCOMMAND = 2;
	public static final int RECEIVEOKCOMMAND = 3;
	public static final int SENDDATACOMMAND = 4;
	public static final int SETTAGS = 5;
	public static final int DELETETAGS = 6;
	
	public static final String COMMANDERR = "1";
	public static final String COMMANDOK = "0";

	/**
	 * 创建Socket信息发送指令
	 * 
	 * @param cmd
	 * @param targetuid
	 * @param typestring
	 * @param artid
	 * @return
	 */
	public static String makeCmd(int cmd, String targetuid, String typestring,
			String artid) {
		String strRet = String.valueOf(cmd) + CMD_SPLIT + targetuid + CMD_SPLIT
				+ typestring + CMD_SPLIT + artid;
		return strRet;
	}

	public static String makeReceiveString(SocketDataInfo sdi) {
		return String.valueOf(RECEIVEOKCOMMAND)+CMD_SPLIT+sdi.Cmdstatus+CMD_SPLIT+COMMANDOK;
	}
	/**
	 * 创建心跳包字符串
	 * 
	 * @param uid
	 * @return
	 */
	public static String getHeartBeatStr(String uid) {
		return makeCmd(HEARTCOMMAND, uid, "", "");
	}

	/**
	 * 创建Socket建立字符串
	 * 
	 * @param uid
	 * @return
	 */
	public static String getCreateSocketStr(String uid) {
		return makeCmd(CREATESOCKCOMMAND, uid, "", "");
	}
	/**
	 * 创建设置监听频道列表的命令字符串
	 * @param uid
	 * @param list
	 * @return
	 */
	public static String getSetTagsSocketStr(String uid,ArrayList<String> list)
	{
		String strRet="";
		if (list.size()>0) {
			String strtags="";
			for (String strTag : list) {
				if (strTag=="") {
					continue;
				}
				strtags+=strTag+DTA_SPLIT;
			}
			strRet=makeCmd(SETTAGS, uid, strtags, "0");
		}
		return strRet;
	}
	/**
	 * 创建删除监听频道列表的命令字符串
	 * @param uid
	 * @param list
	 * @return
	 */
	public static String getDeleteTagsSocketStr(String uid,ArrayList<String> list)
	{
		String strRet="";
		if (list.size()>0) {
			String strtags="";
			for (String strTag : list) {
				if (strTag=="") {
					continue;
				}
				strtags+=strTag+DTA_SPLIT;
			}
			strRet=makeCmd(DELETETAGS, uid, strtags, "");
		}
		return strRet;
	}
	
	/**
	 * 创建Socket断开指令字符串
	 * @param uid
	 * @return
	 */
	public static String getCloseSocketStr(String uid) {
		return makeCmd(CLOSESOCKCOMMAND, uid, "", "");
	}
	public static SocketDataInfo parseSocketDataInfo(String socketStr) {
			SocketDataInfo sdi=new SocketDataInfo();
			String[] dtas=socketStr.split(CMD_SPLIT);
			try {
				if (dtas.length>3) {
					sdi.SocketCommand=Integer.valueOf(dtas[0]);
					sdi.ArtData=dtas[1];
					sdi.Cmdstatus=dtas[3];
				}
				else {
					sdi.SocketCommand=Integer.valueOf(dtas[0]);
					sdi.ArtData=dtas[1];
					sdi.Cmdstatus=dtas[2];
				}
			} catch (Exception e) {
				Log.e(LogTag, e.getMessage());
			}
			return sdi;
		}
}
