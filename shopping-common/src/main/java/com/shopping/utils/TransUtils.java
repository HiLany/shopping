package com.shopping.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by lany on 2019/4/22.
 */
public class TransUtils {

    /**
     *
     * @param str '["2019-04-21T16:35:31.558Z","2019-04-22T08:35:31.558Z"]'
     * @return
     */
    public static String[] strToArray(String str){
        JSONArray json = JSONArray.fromObject(str);
        String[] result = new String[json.size()];
        for(int i=0;i<json.size();i++){
            result[i] = (String)json.get(i);
        }
        return result;
    }

    public static void main(String[] args){
        TransUtils.strToArray("[\"2019-04-21T16:35:31.558Z\",\"2019-04-22T08:35:31.558Z\"]");
    }
}
