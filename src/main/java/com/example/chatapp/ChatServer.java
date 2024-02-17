package com.example.chatapp;

import java.net.*;
import java.io.*;
import java.util.*;

import com.example.chatapp.db.DatabaseManager;
public class ChatServer {
    public static void main(String args[])
            throws IOException, InterruptedException
    {
        DatabaseManager dbManager = new DatabaseManager();

        DatagramSocket ss = new DatagramSocket(1234);
        InetAddress ip = InetAddress.getLocalHost();

        System.out.println("Running UnSyncChatServer.java");

        System.out.println("Server is Up....");
        System.out.println(InetAddress.getLocalHost());
        // Create a sender thread
        Thread ssend;
        ssend = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Scanner sc = new Scanner(System.in);
                    while (true) {
                        synchronized (this)
                        {
                            byte[] sd = new byte[1000];

                            // scan new message to send
                            sd = sc.nextLine().getBytes();
                            DatagramPacket sp
                                    = new DatagramPacket(
                                    sd,
                                    sd.length,
                                    ip,
                                    5334);
                                    ;

                            ss.send(sp);

                            String msg = new String(sd);
                            System.out.println("Your Car, Your Way: "
                                    + msg);
                            System.out.println("server IP Address: " + InetAddress.getLocalHost().getHostAddress());
                            dbManager.insertMessage("Your Car, Your Way", msg);

                            // exit condition
                            if ((msg).equals("bye")) {
                                System.out.println("Server"
                                        + " exiting... ");
                                break;
                            }
                            System.out.println("Waiting for"
                                    + " client response... ");
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Exception occured");
                }
            }
        });

        Thread sreceive;
        sreceive = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    while (true) {
                        synchronized (this)
                        {

                            byte[] rd = new byte[1000];

                            // Receive new message
                            DatagramPacket sp1
                                    = new DatagramPacket(
                                    rd,
                                    rd.length);
                            ss.receive(sp1);


                            String msg
                                    = (new String(rd)).trim();
                            System.out.println("Client ("
                                    + sp1.getPort()
                                    + "):"
                                    + " "
                                    + msg);

                            // Exit condition
                            if (msg.equals("bye")) {
                                System.out.println("Client"
                                        + " connection closed.");
                                break;
                            }
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Exception occured");
                } finally {
                    dbManager.closeConnection();
                }
            }
        });

        ssend.start();
        sreceive.start();

        ssend.join();
        sreceive.join();
    }
}
