package com.sounder.sounderselector.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  Created by Sounder on 2016/10/27.
 */

public class MD5Util {
    //
    public static String toMD5(String url){
        String result = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(url.getBytes());
            byte[] bs = md.digest();
            int i ;
            StringBuffer sb = new StringBuffer();
            for(int offset = 0;offset<bs.length;offset++){
                i=bs[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16){
                    sb.append(0);
                }
                sb.append(Integer.toHexString(i));
            }
            result = sb.toString();
            result = result.substring(8,24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String getFileName(){
        return toMD5("SOUNDER"+System.currentTimeMillis());
    }
}
