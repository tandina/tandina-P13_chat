package com.example.chatapp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.example.chatapp.db.DatabaseManager;

public class ChatClient {
    public static void main(String args[])
            throws IOException, InterruptedException
    {
        DatabaseManager dbManager = new DatabaseManager();

        DatagramSocket cs
                = new DatagramSocket(5334);
        InetAddress ip
                = InetAddress.getLocalHost();

        System.out.println("Running UnSyncChatClient.java");

        System.out.println("Client is Up....");
        System.out.println(InetAddress.getLocalHost());

        Thread csend;
        csend = new Thread(new Runnable() {
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

                            // create new message
                            DatagramPacket sp
                                    = new DatagramPacket(
                                    sd,
                                    sd.length,
                                    ip,
                                    1234);


                            // send the new packet
                            cs.send(sp);

                            String msg = new String(sd);
                            System.out.println("Client says: "
                                    + msg);
                            System.out.println("Client IP Address: " + InetAddress.getLocalHost().getHostAddress());
                            dbManager.insertMessage("Client", msg);
                            // exit condition
                            if (msg.equals("bye")) {
                                System.out.println("client "
                                        + "exiting... ");
                                break;
                            }
                            System.out.println("Waiting for "
                                    + "server response...");
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("Exception occured");
                }
            }
        });

        // create a receiver thread

        Thread creceive;
        creceive = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {

                    while (true) {
                        synchronized (this)
                        {

                            byte[] rd = new byte[1000];

                            // receive new message
                            DatagramPacket sp1
                                    = new DatagramPacket(
                                    rd,
                                    rd.length);
                            cs.receive(sp1);

                            String msg = (new String(rd)).trim();
                            System.out.println("Your Car, Your Way: " + msg);

                            // exit condition
                            if (msg.equals("bye")) {
                                System.out.println("Your Car, Your Way"
                                        + " Stopped....");
                                break;
                            }
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("Exception occured");
                }finally {
                    dbManager.closeConnection();
                }
            }
        });

        csend.start();
        creceive.start();

        csend.join();
        creceive.join();
    }
}
