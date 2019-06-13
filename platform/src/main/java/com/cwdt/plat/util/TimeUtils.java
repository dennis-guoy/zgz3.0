package com.cwdt.plat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/28.
 */

public class TimeUtils {


    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {

        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static String formatDate(Date date, SimpleDateFormat dateFormat) {
        return dateFormat.format(date);
    }
    /**
     * 日期格式化为字符串，使用默认的格式化参数 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date,DATE_FORMAT_DATE);
    }
    /**
     * 日期及时间格式化为字符串，使用默认的格式化参数 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDate(date,DEFAULT_DATE_FORMAT);
    }

    /**
     * 按照给定的格式化字符串格式化日期
     * @param date
     * @param formatStr
     * @return
     */
    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * 按照给定的格式化字符串解析日期
     * @param dateStr
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr, String formatStr) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        date = sdf.parse(dateStr);
        return date;
    }
    /**
     * 从字符串中分析日期
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr) throws ParseException {
        Date date=null;
        String[] dateArray = dateStr.split("\\D+");     //+防止多个非数字字符在一起时导致解析错误
        int dateLen = dateArray.length;
        int dateStrLen=dateStr.length();
        if(dateLen>0){
            if(dateLen==1&&dateStrLen>4){
                if(dateStrLen=="yyyyMMddHHmmss".length()){
                    //如果字符串长度为14位并且不包含其他非数字字符，则按照（yyyyMMddHHmmss）格式解析
                    date=parseDate(dateStr,"yyyyMMddHHmmss");
                }else if(dateStrLen=="yyyyMMddHHmm".length()){
                    date=parseDate(dateStr,"yyyyMMddHHmm");
                }else if(dateStrLen=="yyyyMMddHH".length()){
                    date=parseDate(dateStr,"yyyyMMddHH");
                }else if(dateStrLen=="yyyyMMdd".length()){
                    date=parseDate(dateStr,"yyyyMMdd");
                }else if(dateStrLen=="yyyyMM".length()){
                    date=parseDate(dateStr,"yyyyMM");
                }
            }else{
                String fDateStr=dateArray[0];
                for(int i=1;i<dateLen;i++){
                    //左补齐是防止十位数省略的情况
                    fDateStr+=leftPad(dateArray[i],"0",2);
                }

                if(dateStr.trim().matches("^\\d{1,2}:\\d{1,2}(:\\d{1,2})?$")){
                    //补充年月日3个字段
                    dateLen+=3;
                    fDateStr=formatDate(new Date(),"yyyyMMdd")+fDateStr;
                }

                date=parseDate(fDateStr,"yyyyMMddHHmmss".substring(0, (dateLen-1)*2+4));
            }
        }

        return date;
    }

    public static String leftPad(String str,String pad,int len){
        String newStr=(str==null?"":str);
        while(newStr.length()<len){
            newStr=pad+newStr;
        }
        if(newStr.length()>len){
            newStr=newStr.substring(newStr.length()-len);
        }
        return newStr;
    }
    /**
     * 支持多种日期类型的字符串转日期方法
     * @param strDate
     * @return
     */
    public static Date StringToDate(String strDate)
    {
        Date date = null;
        try {
            date = parseDate(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
