package com.vcampus.dao.utils;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 字符串与图片互转
 *
 * @author 刘骐
 * @date 2022/09/01
 *//*
用blob类型存储图片实在是太难了，我不知道咋操作，于是就用一个超长的String，把图片转为字符串再操作
 */
public class StringAndImage {
    /**
     * 图像转字符串
     *
     * @param path 图片路径
     * @return {@link String}
     * @throws IOException ioexception
     */
    public static String  ImageToString(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = fis.read(buff)) != -1) {
            bos.write(buff, 0, len);
        }
// 得到图片的字节数组
        byte[] result = bos.toByteArray();
// 将数组转为字符串
        BASE64Encoder encoder = new BASE64Encoder();
        String str = encoder.encode(result).trim();
       // String str = new String(result);
        return str;
    }

    /**
     * 字符串转图像
     *
     * @param str 字符串
     * @return {@link byte[]}
     * @throws IOException ioexception
     */
    public static byte[] StringToImage(String str) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgbyte = decoder.decodeBuffer(str);
        return  imgbyte;
    }
}
