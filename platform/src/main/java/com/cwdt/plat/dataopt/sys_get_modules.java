package com.cwdt.plat.dataopt;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.cwdt.plat.data.BaseModule;
import com.cwdt.plat.data.BaseToken;
import com.cwdt.plat.data.BaseUserInfo;
import com.cwdt.plat.data.Const;
import com.cwdt.plat.data.GlobalData;
import com.cwdt.plat.data.Tree;
import com.cwdt.plat.util.CwdtApplication;
import com.cwdt.plat.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class sys_get_modules extends JngsJsonBase {

    public List<Tree<BaseModule>> modules=new ArrayList<>();

    public sys_get_modules() {
        super();
        interfaceUrl = Const.BASE_URL+ "/sys/menu/modules";
    }
    @Override
    public boolean ParsReturnData() {
        boolean bRet = false;
        dataMessage = new Message();
        if (outJsonObject != null) {
            try {
                if (0 == outJsonObject.getInteger("code")) {

                    bRet = true;
                    dataMessage.arg1 = 0;
                    dataMessage.obj = modules;
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


        } catch (Exception e) {
            Log.e(LogTAG, e.getMessage());
        }
    }
}
