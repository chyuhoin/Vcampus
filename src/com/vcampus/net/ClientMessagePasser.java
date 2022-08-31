package com.vcampus.net;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 客户端实例化消息传送器的伪单例
 * 因为客户端使用passer的情况会很多，但是一个程序只能有passer
 * 所以提供一个伪单例模式的工具，来让客户端在任何地方都能立即获得这个唯一的passer
 *
 * @author ZhongHaoyuan
 */
public class ClientMessagePasser {
    private static MessagePasser instance;

    /**
     * 获得唯一的passer实例，而不会重新new
     *
     * @return the instance
     */
    public static MessagePasser getInstance() {
        return instance;
    }

    /**
     * 初始化一个伪单例，如果这个build被胡乱调用会导致单例模式的失效
     *
     * @param input  the input
     * @param output the output
     */
    public static void build(InputStream input, OutputStream output) {
        instance = new MessagePasser(input, output);
    }
}
