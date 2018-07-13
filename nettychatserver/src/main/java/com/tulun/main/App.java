package com.tulun.main;

import java.util.Properties;

import com.tulun.web.netty.ChatServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "start chat server..." );
        Properties pt = new Properties(); 
//        pt.load(App.class.getClassLoader().getResourceAsStream("server.properties"));
        String ip = pt.getProperty("ip", "127.0.0.1");
        int port = Integer.parseInt(pt.getProperty("port", "9000"));
        
        ChatServer cs = new ChatServer();
        cs.startServer(ip, port);
    }
}
