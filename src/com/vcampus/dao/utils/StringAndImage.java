package com.vcampus.dao.utils;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.StringTokenizer;
/*
用blob类型存储图片实在是太难了，我不知道咋操作，于是就用一个超长的String，把图片转为字符串再操作
 */
public class StringAndImage {
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
        return str;
    }
    public static byte[] StringToImage(String str) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgbyte = decoder.decodeBuffer(str);
        return  imgbyte;
//        OutputStream os = new FileOutputStream(path);
//        os.write(imgbyte, 0, imgbyte.length);
//        os.flush();
//        os.close();
    }
//    @Test
//    public void test() throws IOException {
//        String str = ImageToString("src/os.png");
//        System.out.println(str.length());
//    }
    @Test
    public void test() throws IOException {
        System.out.println(ImageToString("D:\\Code\\JavaWebCode\\Vcampus\\idea.png"));
    }
}
