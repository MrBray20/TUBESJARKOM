package com.serverjarkom;

import java.io.*;
import java.net.*;

public class Server {
    public static final int serverPort = 6789;


    public static void main (String[] args) throws Exception{
        ServerSocket welcomSocket = new ServerSocket(serverPort);
        System.out.println("TCP Server Up...");
        while (true) {
        }
    }
}
