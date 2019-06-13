/**
 *
 */
package com.cwdt.plat.dataopt;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cwdt.plat.data.Const;
import com.cwdt.plat.util.Tools;

/**
 * @author AIGO
 */
public class JngsJsonBase extends CustomJsonBase {


    /**
     * 当前用户所属区域的分区ID
     */
    public String strAreaID = "0";

    /**
     * @param
     */
//    public JngsJsonBase(String opt) {
//        super(opt);
//        interfaceUrl = Const.JSON_DATA_INTERFACE_URL;
//        strAreaID = Const.curUserInfo.getDeptId().toString();
//    }
    public JngsJsonBase() {
        super();
//        interfaceUrl = Const.JSON_DATA_INTERFACE_URL;
//        if (Const.curUserInfo.getOrgid()!=null) {
//            strAreaID = Const.curUserInfo.getOrgid().toString();
//        }
//        else {
//            strAreaID="0";
//        }
//        strAreaID = Const.curUserInfo.getOrgid().toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cwdt.plat.dataopt.CustomJsonBase#prepareCustomData()
     */
    @Override
    public void prepareCustomData() {
        // TODO Auto-generated method stub
//        if (Const.curUserInfo!=null) {
//            strUserId = Const.curUserInfo.getUserId().toString();
//            try {
//                optData.put("userarea", Const.curUserInfo.getOrgid());
//            } catch (Exception e) {
//                Log.e(LogTAG, e.getMessage());
//            }
//        }
//        else
//        {
//            strUserId="0";
//        }

        super.prepareCustomData();
    }


    @Override
    public boolean ParsReturnData() {

        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cwdt.plat.dataopt.JsonBase#PacketData()
     */
    @Override
    public void PacketData() {
        // TODO Auto-generated method stub

    }

}
