package com.shopping.utils;

import org.joda.time.DateTime;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by lany on 2019/4/22.
 */
public class TimeUtils {

    /**
     * 获取当前事件
     * @return
     */
    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        DateTime dateTime = new DateTime(calendar);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     *
     * @param time '2019-03-27T16:00:00.012Z'
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String transTime(String time){
        DateTime datetime = new DateTime(time);
        return datetime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static void main(String[] args){
        System.out.println(TimeUtils.getCurrentTime());
//        System.out.println(TimeUtils.transTime("2019-03-27T16:00:00.012Z"));
    }

}
