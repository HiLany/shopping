package com.shopping.utils;


import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


/**
 * MD5 加密工具
 * Created by lany on 2019/4/3.
 */
public class MD5Util {


    //基于DigestUtils的不可逆的加密算法
    public static String getMD5(InputStream inputStream){
        if(null != inputStream){
            return null;
        }
//        byte[] md5_giest = null;
        String md5_string = null;
        try {
//            md5_giest = DigestUtils.md5Digest(inputStream);
            md5_string = DigestUtils.md5DigestAsHex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return md5_string;
        }
    }


    //基于base64的可逆的加密算法
    public static String encry(Object obj){
        if(null == obj){
            return null;
        }
        if(!(obj instanceof byte[]) || !(obj instanceof String)){
            return null;
        }
        byte[] target = null;
        if(obj instanceof byte[]){
            target = (byte[])obj;
        }
        if(obj instanceof String){
            target = ((String) obj).getBytes();
        }
        return Base64.getEncoder().encodeToString(target);
    }

    //基于base64的可逆的解密算法
    public static byte[] decode(Object obj){
        if(null == obj){
            return null;
        }
        if(!(obj instanceof byte[]) || !(obj instanceof String)){
            return null;
        }
        byte[] decode_byte = null;

        if(obj instanceof byte[]){
            decode_byte = Base64.getDecoder().decode((byte[]) obj);
        }
        if(obj instanceof String){
            decode_byte = Base64.getDecoder().decode((String) obj);
        }
        return decode_byte;

    }

}
