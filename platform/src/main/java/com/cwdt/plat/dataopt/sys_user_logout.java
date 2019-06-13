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

public class sys_user_logout  extends JngsJsonBase{
    public sys_user_logout() {
        super();
        interfaceUrl = Const.BASE_URL+ "/mlogout";
    }
    @Override
    public boolean ParsReturnData() {
        boolean bRet = false;
        dataMessage = new Message();
        if (outJsonObject != null) {
            try {
                if (0 == outJsonObject.getInteger("code")) {

                    GlobalData.SetSharedData(Const.TOKEN_STORE_NAME,"");
                    GlobalData.SetSharedData(Const.USER_STORE_NAME,"");
                    Const.curUserInfo=null;
                    Const.baseToken=null;

                    bRet = true;
                    dataMessage.arg1 = 0;
                    dataMessage.obj = 0;
                    Tools.SendBroadCast(CwdtApplication.getInstance(),Const.BROADCAST_LOGOUT_OK);
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
//            optData.put( Const.curUserInfo.getUserId());

        } catch (Exception e) {
            Log.e(LogTAG, e.getMessage());
        }
    }


}
