package com.cwdt.plat.dataopt;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.cwdt.plat.data.BaseToken;
import com.cwdt.plat.data.BaseUserInfo;
import com.cwdt.plat.data.Const;
import com.cwdt.plat.data.GlobalData;
import com.cwdt.plat.util.CwdtApplication;
import com.cwdt.plat.util.Tools;


public class sys_user_login extends JngsJsonBase {
    public BaseUserInfo basedata = new BaseUserInfo();
    public BaseToken baseToken=new BaseToken();
    public String account;
    public String pass;

    public sys_user_login() {
        super();
        interfaceUrl = Const.BASE_URL+ "/mlogin";
    }

    @Override
    public boolean ParsReturnData() {
        boolean bRet = false;
        dataMessage = new Message();
        if (outJsonObject != null) {
            try {
                if (0 == outJsonObject.getInteger("code")) {
                    basedata=outJsonObject.getJSONObject("userinfo").toJavaObject(BaseUserInfo.class);
                    baseToken=outJsonObject.getJSONObject("token").toJavaObject(BaseToken.class);

                    Bundle nBundle = new Bundle();
                    nBundle.putSerializable("basedata", basedata);
                    nBundle.putSerializable("token", baseToken);
                    GlobalData.SetSharedData(Const.TOKEN_STORE_NAME,outJsonObject.getJSONObject("token").toJSONString());
                    GlobalData.SetSharedData(Const.USER_STORE_NAME,outJsonObject.getJSONObject("userinfo").toJSONString());
                    Const.curUserInfo=basedata;
                    Const.baseToken=baseToken;
                    
                    bRet = true;
                    dataMessage.arg1 = 0;
                    dataMessage.obj = nBundle;
                    Tools.SendBroadCast(CwdtApplication.getInstance(),Const.BROADCAST_LOGIN_OK);
                }

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
            optData.put("username", account);
            optData.put("password", pass);

        } catch (Exception e) {
            Log.e(LogTAG, e.getMessage());
        }
    }


}
