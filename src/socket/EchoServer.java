/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas
 */
public class EchoServer implements Runnable {

    Socket s;
    BufferedReader in;
    String echo;
    PrintWriter out;

    public EchoServer(Socket soc) {

        s = soc;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);

            while (true) {
                echo = in.readLine();
                switch (echo) {
                    case "UPPER#Hello World":
                        out.println("HELLO WORLD");
                        break;
                    case "LOWER#Hello World":
                        out.println("hello world");
                        break;
                    case "reverse#abcd":
                        out.println("dcba");
                        break;
                    case "TRANSLATE#hund":
                        out.println("dog");
                        break;
                    case "TRANSLATE#kat":
                        out.println("cat");
                        break;
                    default:
                        s.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
        String ip = "localhost";
        int port = 1234;
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, port));

        while (true) {
            EchoServer e = new EchoServer(ss.accept());
            Thread t1 = new Thread(e);
            t1.start();

        }
    }

}
