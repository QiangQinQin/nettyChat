package com.tulun.netty.client;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	System.out.println( "start chat client..." );
        Properties pt = new Properties(); 
//        pt.load(App.class.getClassLoader().getResourceAsStream("server.properties"));
        String ip = pt.getProperty("ip", "127.0.0.1");
        int port = Integer.parseInt(pt.getProperty("port", "9000"));
        
        ChatClient cs = new ChatClient();
        cs.startClient(ip, port);
    }
}
