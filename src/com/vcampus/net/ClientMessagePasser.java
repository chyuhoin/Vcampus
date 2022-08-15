package com.vcampus.net;

import java.io.InputStream;
import java.io.OutputStream;

public class ClientMessagePasser {
    private static MessagePasser instance;

    public static MessagePasser getInstance() {
        return instance;
    }

    public static void build(InputStream input, OutputStream output) {
        instance = new MessagePasser(input, output);
    }
}
